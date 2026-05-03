/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.taskwkschmng.schprecon.service.MnthngSchdlService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;



@Controller
@Api(value = "MnthngSchdlController Controller")
@RequestMapping("/mnthngSchdl") 
public class MnthngSchdlController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

    @Resource(name = "mnthngSchdlService")
	private MnthngSchdlService svc;
    
    @Resource(name = "counsService")
    private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : consultantSrch
	 * @수정자     : Jeong.Won.Je
	 * @수정일     : 2023. 7. 17. 
	 * @수정내용   : 사용하지 않는 메소드
	 */
    @RequestMapping("/consultantSrch.do")
    public View consultantSrch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	String searchUserIdNm = searchParam.getValue("SEARCH_USER_ID_NM");
    	String deptCd = searchParam.getValue("DEPT_CD");
    	mapParam.put("searchUserIdNm", searchUserIdNm);
    	mapParam.put("deptCd", deptCd);
    	
    	List<Map<String, Object>> dsList = svc.consultantSrch(mapParam);
    	
    	for (Map<String, Object> tmp : dsList) {
    		tmp.put("RAW_USER_NM", tmp.get("USER_NM"));
			tmp.replace("USER_NM", tmp.get("USER_NM"));
		}
    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }

    @RequestMapping("/searchComboOptionMnthng.do")
    public View searchComboOptionMnthng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
    	String userGroupAuthrtSeCd = "";		// session의 그룹권한구분코드
    	
        Map<String, Object> mapParam = new HashMap<String, Object>();

        List<Map<String, Object>> dsSearchComboMnthng = svc.selectCombo1ListMnthng(mapParam);
        List<Map<String, Object>> dsSearchCombo = counsService.selectOrgDeptCombo(request);
        
        Map<String, Object> dmSearchMap = new HashMap<>();
        dmSearchMap.put("IS_ADMIN", "N");
        HttpSession session = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        if (loginVO != null && !"".equals(loginVO.getGroupAuthrtSeCd())) {
        	userGroupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();
        }
        
        LOGGER.debug("dmSearch ::: " + dmSearchMap);
        LOGGER.debug("userGroupAuthrtSeCd.charAt(0) ::: " + userGroupAuthrtSeCd.charAt(0));
        LOGGER.debug("userGroupAuthrtSeCd.substring(1) ::: " + userGroupAuthrtSeCd.substring(1));
        
        // 1xx : 여성가족부, 2xx : 중앙관리기관
        if (userGroupAuthrtSeCd.charAt(0) == '1' || userGroupAuthrtSeCd.charAt(0) == '2') {
        	dmSearchMap.put("IS_ADMIN", "Y");
        } else if (userGroupAuthrtSeCd.charAt(0) == '3') {
        	// x10 : 총괄관리자, x20 : 기관관리자
        	if ("10".equals(userGroupAuthrtSeCd.substring(1)) || "20".equals(userGroupAuthrtSeCd.substring(1))) {
        		dmSearchMap.put("IS_ADMIN", "Y");
        	
        	// x30 : 사업담당자, x40 : 담당자
        	} else {
        		dmSearchMap.put("IS_ADMIN", "N");
        	}
        	
        } else {
        	throw new AppWorksException("접근 권한이 없습니다. 권한 신청을 해주세요.", Alert.ERROR);
        }
        
        dataRequest.setResponse("dmSearch", dmSearchMap);
        dataRequest.setResponse("dsSearchComboMnthng", dsSearchComboMnthng);
        dataRequest.setResponse("dsSearchCombo", dsSearchCombo);
        
        return new JSONDataView();

    }

	@RequestMapping("/selectMnthngSchdlList.do")
	public View selectMnthngSchdlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String crtrYm = searchParam.getValue("CRTR_YM");
		String deptCd = searchParam.getValue("DEPT_CD");
		String searchOpt = searchParam.getValue("GB");	// 조회 구분 : 01-일정, 02-휴가, 03-일정+휴가
		
		mapParam.put("crtrYm", crtrYm);
		mapParam.put("deptCd", deptCd);
		
		//사용자정보	
//		HttpSession session = request.getSession();
// 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//
// 		String varId = loginVO.getId();
//		
//		mapParam.put("USERID", varId);
		
		if(!crtrYm.isEmpty()) {
			List<Map<String, Object>> timeList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> memList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dsListCnslt = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dsListOutreach = new ArrayList<Map<String,Object>>();
			ArrayList<String> times =  new ArrayList<String>();
			
			try {
				timeList = svc.getTimesForExclDown(mapParam);
				for (Map<String, Object> temp : timeList) {
					times.add(temp.get("TIMES").toString());
				}
				mapParam.put("times", times);
			} catch (Exception e) {
				// TODO: handle exception
				mapParam.put("times", null);
			}
			
			if(mapParam.get("times") != null) {
				try {
					if(deptCd == "325") { //사이버아웃리치
						dsListOutreach = svc.selectMnthForExclDown(mapParam);
						if(dsListOutreach.size()<1) {
							throw new Exception("dsListOutreach size zero");
						}
						for (Map<String, Object> map : dsListOutreach) {
							//convertOutReachVal(map);
						}
					}else { // 그 외의 경우 (사이버상담,모바일상담)
						dsListCnslt = svc.selectMnthForExclDown(mapParam);
						if(dsListCnslt.size()<1) {
							throw new Exception("dsListCnslt size zero");
						}
						
						for (Map<String, Object> map : dsListCnslt) {
							if(deptCd.equals("325")) {								
								convertOutReachVal(map);								
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					memList = svc.selectAllMemberDeptcd(mapParam);
					if(deptCd == "325") {
						for (Map<String, Object> tmp : memList) {
							dsListOutreach.add(tmp);
						}
					}else {
						for (Map<String, Object> tmp : memList) {
							dsListCnslt.add(tmp);
						}
					}
				}
			}
			
			switch (searchOpt) {
			case "01":	//일정
				dsList = svc.selectMnthngSchdlList01(mapParam);
				break;
			case "02":	//휴가
				dsList = svc.selectMnthngSchdlList02(mapParam);
				break;
			case "03":	//일정+휴가
				dsList = svc.selectMnthngSchdlList(mapParam);
				break;
			default:
				dsList = svc.selectMnthngSchdlList(mapParam);
				break;
			}
			
			dataRequest.setResponse("dsList", dsList);
			dataRequest.setResponse("dsTimes", timeList);
			if(mapParam.get("times") != null) {
				dataRequest.setResponse("dsListCnslt", dsListCnslt);
				dataRequest.setResponse("dsListOutreach", dsListOutreach);
			}
			
		}
		return new JSONDataView();
	}
	@RequestMapping("/searchComboOptionTimes.do")
	public View searchComboOptionTimes(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String deptCd = searchParam.getValue("DEPT_CD");
		String workYmd = searchParam.getValue("CNSLTNT_YMD");
		
		mapParam.put("deptCd", deptCd);
		mapParam.put("workYmd", workYmd);
		if(!deptCd.isEmpty() && workYmd.length() == 8) { //부서코드 존재, 근무일자 8자리
			List<Map<String, Object>> dsComboTimes = svc.searchComboOptionTimes(mapParam);
			
			dataRequest.setResponse("dsComboTimes", dsComboTimes);
		}
		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateMnthngSchdlDetail.do")
	public View updateMnthngSchdlDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String userId = searchParam.getValue("CNSLTNT_ID");
		String taskwkSchdlSn = searchParam.getValue("TASKWK_SCHDL_SN"); //업무일정일련번호
		String bizPlanMngNo = searchParam.getValue("SEL_TIMES").split("-")[1]; //사업계획관리번호
		
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
 		String loginId = loginVO.getId();
 		
 		mapParam.put("loginId", loginId);
		mapParam.put("userId", userId);
		mapParam.put("taskwkSchdlSn", taskwkSchdlSn);
		mapParam.put("bizPlanMngNo", bizPlanMngNo);
		
		if(!taskwkSchdlSn.isEmpty() && !bizPlanMngNo.isEmpty()) {
			svc.updateMnthngSchdlDetail(mapParam);
		}
		
		return new JSONDataView();
	}
	
	@RequestMapping("/deleteMnthngSchdlDetail.do")
	public View deleteMnthngSchdlDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String userId = searchParam.getValue("CNSLTNT_ID");
		String taskwkSchdlSn = searchParam.getValue("TASKWK_SCHDL_SN"); //업무일정일련번호
		
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
 		String loginId = loginVO.getId();
		
		mapParam.put("loginId", loginId);
		mapParam.put("userId", userId);
		mapParam.put("taskwkSchdlSn", taskwkSchdlSn);
		
		svc.deleteMnthngSchdlDetail(mapParam);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/getRowCnt.do")
	public View getRowCnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String deptCd = searchParam.getValue("DEPT_CD");
		String workYmd = searchParam.getValue("WORK_YMD");
		
		mapParam.put("deptCd", deptCd);
		mapParam.put("workYmd", workYmd);
		
		List<Map<String, Object>> dsRowCnt = svc.getRowCnt(mapParam);
		List<Map<String, Object>> dsComboOptionHrWork = svc.searchComboHrWork(mapParam);
		
		dataRequest.setResponse("dsRowCnt", dsRowCnt);
		dataRequest.setResponse("dsComboOptionHrWork", dsComboOptionHrWork);
		
		return new JSONDataView();
	}
	@RequestMapping("/selectYmdSchdlExmpl.do")
	public View selectYmdSchdlExmpl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String workYmd = searchParam.getValue("WORK_YMD");
		String deptCd = searchParam.getValue("DEPT_CD");
		
		mapParam.put("workYmd", workYmd);
		mapParam.put("deptCd", deptCd);
		
		List<Map<String, Object>> dsList = svc.selectYmdSchdlExmpl(mapParam);
		
       	for (Map<String, Object> map : dsList) {
       		StringBuffer sb = new StringBuffer();
       		sb.append(map.get("WORK_YMD").toString());
       		sb.insert(4, "년 ");
       		sb.insert(8, "월 ");
       		sb.insert(12, "일");
       		map.replace("WORK_YMD", sb.toString());
		}
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	@RequestMapping("/insertMnthngSchdl.do")
	public View insertMnthngSchdl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup searchParamList = dataRequest.getParameterGroup("dsCnlsnt");
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//Map<String, Object> mapParam = new HashMap<String, Object>();
//    	System.out.println("searchParamList.toString()"+searchParamList.toString());
//    	System.out.println("searchParam.toString()"+searchParam.toString());
		
		String deptCd = searchParam.getValue("DEPT_CD");
		String workYmd = searchParam.getValue("WORK_YMD");
		String crtrYm = workYmd.substring(0, 6);
		
		
		Iterator<ParameterRow> processRows = searchParamList.getAllRows();
		while (processRows.hasNext()) {
			Map<String, String> mapPrc = processRows.next().toMap();
			mapPrc.put("loginId", loginId);
			mapPrc.put("workYmd", workYmd);
			mapPrc.put("deptCd", deptCd);
			mapPrc.put("crtrYm", crtrYm);
			
			try {
				svc.insertMnthngSchdl(mapPrc);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	
		return new JSONDataView();
		
	}
	@RequestMapping("/insertMnthngSchdlOut.do")
	public View insertMnthngSchdlOut(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup searchParamList = dataRequest.getParameterGroup("dsOutReach");
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//Map<String, Object> mapParam = new HashMap<String, Object>();
//    	System.out.println("searchParamList.toString()"+searchParamList.toString());
//    	System.out.println("searchParam.toString()"+searchParam.toString());
		
		String deptCd = searchParam.getValue("DEPT_CD");
		String workYmd = searchParam.getValue("WORK_YMD");
		String crtrYm = workYmd.substring(0, 6);
		
		Iterator<ParameterRow> processRows = searchParamList.getAllRows();
		while (processRows.hasNext()) {
			Map<String, String> mapPrc = processRows.next().toMap();
			mapPrc.put("loginId", loginId);
			mapPrc.put("workYmd", workYmd);
			mapPrc.put("deptCd", deptCd);
			mapPrc.put("crtrYm", crtrYm);
			
			try {
				svc.insertMnthngSchdl(mapPrc);
				svc.insertMnthngSchdlOut(mapPrc);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBatchCrt.do")
	public View insertBatchCrt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup searchParamList = dataRequest.getParameterGroup("dsListCnslt");
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	
//    	System.out.println("insertBatchCrt"+searchParamList.toString());
//		System.out.println("insertBatchCrt"+searchParam.toString());
		
		
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		String deptCd = searchParam.getValue("DEPT_CD");
		String crtrYm = searchParam.getValue("CRTR_YM");
		//deleteDaySchdl	-	deptCd,crtrYm
		//selectMthBizPlanNo	-	crtrYm,deptCd,bgngHr,endHr
		//insertDaySchdl	-	taskwkSchdlSn,bizPlanMngNo,workYmd,deptCd,cnsltntId,loginId
		mapParam.put("deptCd", deptCd);
		mapParam.put("crtrYm", crtrYm);
		
		try {
			svc.deleteDaySchdl(mapParam);
		}catch (Exception e) {
			// TODO: handle exception
			return new JSONDataView();
		}
		
		List<Map<String, Object>> timeList = svc.getTimesForExclDown(mapParam);
		
		Iterator<ParameterRow> processRows = searchParamList.getAllRows();
		
		while (processRows.hasNext()) {
			Map<String, String> mapPrc = processRows.next().toMap();	
			
			mapPrc.put("loginId", loginId);
			mapPrc.put("deptCd", deptCd);
			mapPrc.put("crtrYm", crtrYm);
			
			if(mapPrc.get("WORK_YMD").contains("-"))
				mapPrc.replace("WORK_YMD", mapPrc.get("WORK_YMD").replace("-", ""));
			
			if(deptCd.equals("325")) {
				for(String key : mapPrc.keySet()) {
					if(mapPrc.get(key).equalsIgnoreCase("t")
						|| mapPrc.get(key).equalsIgnoreCase("i")
						|| mapPrc.get(key).equalsIgnoreCase("f")
						|| mapPrc.get(key).equalsIgnoreCase("c") ) {
						
						String temp = "";
						temp = key.replace("TIME", "");
						int nTemp = Integer.parseInt(temp);
						if(nTemp % 2 == 0) {
							mapPrc.put("bgngHr", (String) timeList.get(nTemp/2).get("TIMES"));
							mapPrc.put("firstCd", mapPrc.get(key));
							mapPrc.put("secondCd", mapPrc.get("TIME"+(nTemp+1)));
						}else {
							mapPrc.put("bgngHr", (String) timeList.get((nTemp-1)/2).get("TIMES"));
							mapPrc.put("firstCd", mapPrc.get("TIME"+(nTemp-1)));
							mapPrc.put("secondCd", mapPrc.get(key));
						}
						//System.out.println("1111111111111111111111111111111"+key+mapPrc.get("bgngHr"));
						//System.out.println(mapPrc.toString());
						break;
					}
				}
			}else {
				for (String key : mapPrc.keySet()) {
					if(mapPrc.get(key).equals("1")) {
						String temp = "";
						temp = key.replace("TIME", "");
						mapPrc.put("bgngHr", (String) timeList.get(Integer.parseInt(temp)).get("TIMES"));
						//System.out.println("1111111111111111111111111111111"+key+mapPrc.get("bgngHr"));
						break;
					}
				}
			}
			//여기에서 DB처리를 함.			
			try {
				if(deptCd.equals("325")) {
					svc.insertDaySchdl(mapPrc);
					//System.out.println(mapPrc.get("TASKWK_SCHDL_SN"));
					svc.insertDaySchdlOut(mapPrc);
				}else {
					svc.insertDaySchdl(mapPrc);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				return new JSONDataView();
			}
			//System.out.println("mapPrc.toString() : " + mapPrc.toString());
    	}
		
		return new JSONDataView();
		
	}
	
	private boolean convertOutReachVal(Map<String , Object> mapParam) {
		int MAX_TIME_VAL = 6;
		try {
			if(mapParam.get("HR_WORK_TYPE_SE_CD1") != null &&
					mapParam.get("HR_WORK_TYPE_SE_CD2") != null) {
				for (int i = 0; i < MAX_TIME_VAL; i++) {
					try {
						String tempColName = "TIME";
						tempColName = tempColName.concat(i+"");
						if(mapParam.get(tempColName).toString().equalsIgnoreCase("1")) {
							//break;
							int tempColTime1 = i*2;
							int tempColTime2 = (i*2)+1;
							//String idx = i%2 == 0 ? "1":"2";
							for (int j = 0; j < MAX_TIME_VAL; j++) {
								mapParam.remove("TIME".concat(j+""));
							}
							try {
								if(mapParam.containsKey("TIME".concat(tempColTime1+""))) {
									mapParam.replace("TIME".concat(tempColTime1+""), mapParam.get("HR_WORK_TYPE_SE_CD1").toString());									
								}else {
									mapParam.put("TIME".concat(tempColTime1+""), mapParam.get("HR_WORK_TYPE_SE_CD1").toString());
								}
							} catch (Exception e) {
								// TODO: handle exception
								mapParam.put("TIME".concat(tempColTime1+""), mapParam.get("HR_WORK_TYPE_SE_CD1").toString());
							}
							
							try {
								if(mapParam.containsKey("TIME".concat(tempColTime2+""))) {
									mapParam.replace("TIME".concat(tempColTime2+""), mapParam.get("HR_WORK_TYPE_SE_CD2").toString());
								}else {
									mapParam.put("TIME".concat(tempColTime2+""), mapParam.get("HR_WORK_TYPE_SE_CD2").toString());
								}
							} catch (Exception e) {
								// TODO: handle exception
								mapParam.put("TIME".concat(tempColTime2+""), mapParam.get("HR_WORK_TYPE_SE_CD2").toString());
								//listParam.add(mapParam);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else {
				for (int i = 0; i < MAX_TIME_VAL; i++) {
					String tempColName = "TIME";
					tempColName = tempColName.concat(i+"");
					try {
						if(mapParam.containsKey(tempColName))
							mapParam.replace(tempColName, "");
						else
							mapParam.put(tempColName, "");
					} catch (Exception e) {
						// TODO: handle exception
						mapParam.put(tempColName, "");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
		return true;
		
	}
	
	
	
	
	
}