/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
//import isry.couns.mngr.taskwkaltmntmng.service.CnnctChatReqstdService;
import isry.couns.mngr.workaltmntmng.service.MnthySchdlRegInfoMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "MnthySchdlRegInfoMngController Controller")
@RequestMapping("/workaltmntmng")
public class MnthySchdlRegInfoMngController extends IsryBaseController {

    @Autowired
    private MnthySchdlRegInfoMngService mnthySchdlRegInfoMngService;
    
    @Resource(name = "counsService")
    private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    /**
	 * @Method명   : sampleSearchOption
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 : 부서 콤보박스 조회
	 */
    @RequestMapping("/sampleSearchOption.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
   	log.info("MnthySchdlRegInfoMngController sampleSearchOption");
   	
   	///조회 조건 검색 (부서)
   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
   	
   	dataRequest.setResponse("dsSearchCombo", searchComboList);
   	
   	return new JSONDataView();
   }
    
    /**
	 * @Method명   : selectMnthySchdlRegInfoMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 : 월별 일정등록정보 관리 조회
	 */
    @RequestMapping("/selectMnthySchdlRegInfoMngList.do")
    public View selectMnthySchdlRegInfoMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	log.info("MnthySchdlRegInfoMngController selectMnthySchdlRegInfoMngList");
    	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		String deptCd = searchParam.getValue("DEPT_CD"); //부서코드
		String crtrYm = searchParam.getValue("CRTR_YM"); //기준년도
		mapParam.put("crtrYm",crtrYm);
		mapParam.put("deptCd",deptCd);
		
		List<Map<String, Object>> list = mnthySchdlRegInfoMngService.selectMnthySchdlRegInfoMngList(mapParam);
		
		dataRequest.setResponse("dsList", list);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectMnthySchdlRegModAsgnNocs.do")
    public View selectMnthySchdlRegModAsgnNocs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
//    	Map<String, Object> mapParam = new HashMap<String, Object>();
//		
//		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//		String deptCd = searchParam.getValue("DEPT_CD"); //부서코드
//		String crtrYm = searchParam.getValue("YEAR_MONTH"); //기준년도
//		mapParam.put("yearMonth",crtrYm);
//		mapParam.put("deptCd",deptCd);
//    	
//		List<Map<String, Object>> dsListBoard = mnthySchdlRegInfoMngService.selectMnthySchdlRegModAsgnNocs(mapParam);
		
//		mnthySchdlRegInfoMngService.selectChcMnthySchdlAsgnInfo(dataRequest);
		
//		dataRequest.setResponse("dmSearch", mnthySchdlRegInfoMngService.selectChcMnthySchdlAsgnInfo(dataRequest));
		
//		dataRequest.setResponse("dsListBoard", dsListBoard);
    	
    	dataRequest.setResponse("dsMonthWorkTime", mnthySchdlRegInfoMngService.selectChcMnthySchdlList(request, dataRequest));
		
		dataRequest.setResponse("dmSearch", mnthySchdlRegInfoMngService.selectChcMnthySchdlAsgnInfo(dataRequest));
    	
    	return new JSONDataView();
    	
    }
    
    @RequestMapping("/processMnthySchdlRegInfoMng.do")
    public View processMnthySchdlRegInfoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
//    	HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//		String loginId = "";
//		
//		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
//			loginId = loginVO.getId();
//		}
//    	
//    	ParameterGroup searchParamList = dataRequest.getParameterGroup("dsList");
//    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//    	
//    	String deptCd = searchParam.getValue("DEPT_CD"); //부서 코드
//		String yearMonth = searchParam.getValue("CRTR_YM");
//		String ntabrdAsgnNocs = searchParam.getValue("NTABRD_ASGN_NOCS");
//		String cmntAsgnNocs = searchParam.getValue("CMNT_ASGN_NOCS");
//		
//    	Iterator<ParameterRow> processRows = searchParamList.getAllRows();
//    	while (processRows.hasNext()) {
//			Map<String, String> mapPrc = processRows.next().toMap();
//			mapPrc.put("loginId", loginId);
//			//System.out.println("mapUpd = "+mapUpd);
//			if(mapPrc.get("MBR_NOCS") != null && !mapPrc.get("MBR_NOCS").isEmpty()) {
//				if(mapPrc.get("BIZ_PLAN_MNG_NO") == null || mapPrc.get("BIZ_PLAN_MNG_NO").toString() == "") {
//					//mapPrc.replace("BIZ_PLAN_MNG_NO", "newOne");
//					mapPrc.replace("BIZ_PLAN_MNG_NO", "0");
//				}
//				//여기에서 디비 처리가 진행이 됨.
//				try {
//					mapPrc.put("deptCd", deptCd);
//					mnthySchdlRegInfoMngService.processMnthySchdlRegInfoMng(mapPrc);
//				} catch (Exception e) {
//					// TODO: handle exception
//					return new JSONDataView();
//					
//				}
//			}
//		}
//    	Map<String, String> mapParam = new HashMap<String, String>();
//    	
//    	mapParam.put("loginId", loginId);
//    	mapParam.put("deptCd", deptCd);
//    	mapParam.put("yearMonth", yearMonth);
//    	mapParam.put("ntabrdAsgnNocs", ntabrdAsgnNocs);
//    	mapParam.put("cmntAsgnNocs", cmntAsgnNocs);
    	
//    	mnthySchdlRegInfoMngService.processMnthySchdlRegInfoMng2(mapParam);
    	
    	mnthySchdlRegInfoMngService.processChcMnthySchdlMng(request, dataRequest);
    	
    	return new JSONDataView();
    	
    }
    
    @RequestMapping("/deleteMnthySchdlRegInfoMng.do")
    public View deleteMnthySchdlRegInfoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	Map<String, Object> mapParam = new HashMap<String, Object>();
		
    	String deptCd = searchParam.getValue("DEPT_CD"); //부서코드
		String yearMonth = searchParam.getValue("YEAR_MONTH"); //부서코드
		
		mapParam.put("loginId",loginId);
		mapParam.put("yearMonth",yearMonth);
		mapParam.put("deptCd",deptCd);
		
		mnthySchdlRegInfoMngService.deleteProcessChcMnthySchdlMng(request, dataRequest);
		
//		try {
//			mnthySchdlRegInfoMngService.deleteMnthySchdlRegInfoMng(mapParam);
//			mnthySchdlRegInfoMngService.deleteMnthySchdlRegInfoMng2(mapParam);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return new JSONDataView();
//		}
		
    	return new JSONDataView();
    }
    
//    @RequestMapping("/insertMnthySchdlRegInfoMng.do")
//    public View insertMnthySchdlRegInfoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//    	
//    	HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//		String loginId = "";
//		
//		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
//			loginId = loginVO.getId();
//		}
//    	
//    	ParameterGroup searchParamList = dataRequest.getParameterGroup("dsList");
//    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//    	
//		String deptCd = searchParam.getValue("DEPT_CD"); //부서 코드
//		String yearMonth = searchParam.getValue("YEAR_MONTH"); //해당 연월
//		
//		String ntabrdAsgnNocs = searchParam.getValue("NTABRD_ASGN_NOCS");
//		String cmntAsgnNocs = searchParam.getValue("CMNT_ASGN_NOCS");
//    	Iterator<ParameterRow> insertRows = searchParamList.getAllRows();
//    	while (insertRows.hasNext()) {
//			Map<String, String> mapIns = insertRows.next().toMap();
//			if(mapIns.get("MBR_NOCS") != null && !mapIns.get("MBR_NOCS").isEmpty()) {
//				mapIns.put("loginId", loginId);
//				mapIns.put("deptCd", deptCd);
//				mapIns.put("yearMonth", yearMonth);
//				// 등록 처리를 여기에서 해준다.
//				mnthySchdlRegInfoMngService.insertMnthySchdlRegInfoMng(mapIns);
//			}
//    	}
//    	Map<String, Object> mapParam = new HashMap<String, Object>();
//    	mapParam.put("loginId", loginId);
//    	mapParam.put("deptCd", deptCd);
//    	mapParam.put("yearMonth", yearMonth);
//    	mapParam.put("ntabrdAsgnNocs", ntabrdAsgnNocs);
//    	mapParam.put("cmntAsgnNocs", cmntAsgnNocs);
//    	
//    	mnthySchdlRegInfoMngService.insertMnthySchdlRegInfoMng2(mapParam);
//    	
//    	return new JSONDataView();
//    }
    
    @RequestMapping("/insertMnthySchdlRegInfoMng.do")
    public View insertMnthySchdlRegInfoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	mnthySchdlRegInfoMngService.insertMnthySchdlRegInfoMng(request, dataRequest);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/insertMnthySchdlRegInfoMngCopy.do")
    public View insertMnthySchdlRegInfoMngCopy(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
		String deptCd = searchParam.getValue("DEPT_CD"); //부서 코드
		String yearMonth = searchParam.getValue("YEAR_MONTH"); //대상 연월
		String exYearMonth = searchParam.getValue("EX_YEAR_MONTH"); //전 연월
		
		Map<String, String> mapParam = new HashMap<String, String>();
		String retVal = "0";
		mapParam.put("deptCd", deptCd);
		mapParam.put("yearMonth", yearMonth);
		mapParam.put("exYearMonth", exYearMonth);
		mapParam.put("loginId", loginId);
		mapParam.put("retVal", retVal);
		
		Map<String, String> mapRet = new HashMap<String, String>();
		mapRet = mnthySchdlRegInfoMngService.insertMnthySchdlRegInfoMngCopy(mapParam);
		
		Map<String, String> mapParam2 = new HashMap<String, String>();
		mapParam2.put("YEAR_MONTH", exYearMonth);
		mapParam2.put("DEPT_CD", deptCd);
		mapParam2.put("loginId", loginId);
		
		if ("1".equals(mapRet.get("retVal"))) {
			List<Map<String, Object>> resultMap = mnthySchdlRegInfoMngService.selectMnthySchdlRegModAsgnNocs(mapParam2);
			
			if (resultMap != null) {
				
				//System.out.println(resultMap);
				for (Map<String, Object> map : resultMap) {
					
					mapParam2.put("NTABRD_ASGN_NOCS", String.valueOf(map.get("NTABRD_ASGN_NOCS")));
					mapParam2.put("CMNT_ASGN_NOCS", String.valueOf(map.get("CMNT_ASGN_NOCS")));
					mapParam2.replace("YEAR_MONTH", yearMonth);
					
					mnthySchdlRegInfoMngService.insertMnthySchdlRegInfoMng2(mapParam2);
				}
			}
			
		}
    	
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("msg", mapRet.get("retVal"));
    	dataRequest.setMetadata(true, message);
    	
    	return new JSONDataView();
    }
    
    
}