/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.atrzmng.service.WorkChgMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "WorkChgMngController Controller")
@RequestMapping("/atrzmng")
public class WorkChgMngController extends IsryBaseController {

    @Autowired
    private WorkChgMngService workChgMngService;
    
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
	 * @Method설명 :	소속기관 콤보박스 조회(나중에 부서명으로 변경 될 예정)
	 */
    @RequestMapping("/sampleSearchOptionWorkChg.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
	   	///조회 조건 검색 (부서)
    	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	//조회 조건 검색 (승인상태구분코드)
	   	List<Map<String, Object>> searchComboAprvList = workChgMngService.searchComboBoxAprv(null);
	   	
	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	dataRequest.setResponse("dsSearchComboAprv", searchComboAprvList);
	   	
	   	return new JSONDataView();
   }
    
    @RequestMapping("/selectWorkChgMngList.do")
    public View selectWorkChgMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup pageParam = dataRequest.getParameterGroup("dmPage");
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param
    	String deptCd = searchParam.getValue("DEPT_CD");
    	String prcsSttsSeCd = searchParam.getValue("PRCS_STTS_SE_CD_PARAM");
    	String bgngYmd = searchParam.getValue("BGNG_YMD");
    	String endYmd = searchParam.getValue("END_YMD");
    	String userInfo = searchParam.getValue("CONSTT_ID_NM");
    	String consttCkb = searchParam.getValue("CONSTT_CKB");
    	if(consttCkb.equalsIgnoreCase("ID")) {
    		mapParam.put("userId", userInfo);
    	}else if(consttCkb.equalsIgnoreCase("NAME")) {
    		mapParam.put("userNm", userInfo);
    	}
    	
    	mapParam.put("deptCd",deptCd);
    	mapParam.put("prcsSttsSeCd",prcsSttsSeCd);
    	mapParam.put("bgngYmd",bgngYmd.replace("-", ""));
    	mapParam.put("endYmd",endYmd.replace("-", ""));
    	
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) pageParam.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) pageParam.getValue("pageRowCount")); // 15
		int startIndex = (pageIdx - 1) * rowSize; 
		int totalCount = 0;
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		List<Map<String, Object>> dsList = workChgMngService.selectWorkChgMngList(request, mapParam);
		try {
			totalCount = Integer.parseInt(dsList.get(0).get("TOTAL_COUNT").toString());
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> dmPage = new HashMap<String, Object>();
		//System.out.println("totalCount: "+totalCount);
		dmPage.put("totalCount", totalCount);
		dmPage.put("pageNo", pageIdx);
		dmPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsList", dsList);
		dataRequest.setResponse("dmPage", dmPage);
		
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectWorkChgMngDetail.do")
    public View selectWorkChgMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param
    	String userId = searchParam.getValue("CNSLTNT_ID");
    	String indexSn = searchParam.getValue("INDEX_SN");
    	
    	mapParam.put("userId",userId);
    	mapParam.put("indexSn",indexSn);
    	List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
    	if(indexSn != null && !indexSn.isEmpty()) {
   			dsList = workChgMngService.selectWorkChgMngDetail(mapParam);
    	}
		for (Map<String, Object> map : dsList) {
			
			if ("Y".equals(map.get("APRV_PSBLTY_YN"))) {
				map.replace("APRV_PSBLTY_YN", "승인가능");
			} else {
				map.replace("APRV_PSBLTY_YN", "승인불가");
			}
		}
//System.out.println(" DDD : "+ dsList.toString());    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    @RequestMapping("/processWorkChgMng.do")
    public View processWorkChgMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	ParameterGroup searchListParam = dataRequest.getParameterGroup("dsList");
    	
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		loginId	= loginVO.getId();
		
    	String demandId = searchListParam.getValue(0, "USER_ID_DEMAND");
    	String targetId = searchListParam.getValue(0, "USER_ID_TARGET");
    	String demandInstNo	= searchListParam.getValue(0, "TASKWK_TYPE_DEPT_CD_DEMAND");
    	String targetInstNo	= searchListParam.getValue(0, "TASKWK_TYPE_DEPT_CD_TARGET");
    	String rjctCsCn		= searchListParam.getValue(0,"RJCT_CS_CN");
    			
    	String prePrcsSttsSeCd = searchListParam.getValue(0, "APRV_STTS_SE_CD");
    	String prcsSttsSeCd = searchParam.getValue("PRCS_STTS_SE_CD_PARAM");
    	String indexSn		= searchParam.getValue("INDEX_SN");
    	//변경요청 일련번호
    	String demandSn		= searchListParam.getValue(0, "CHG_DMND_SN");
    	//변경대상 일련번호
    	String targetSn		= searchListParam.getValue(0, "CHG_TRGT_SN");
    	//AYC100 일일 근무배정표에서 변경요청 일련번호 (for valid)
    	String ckDemandSn	= searchListParam.getValue(0, "TASKWK_SCHDL_SN_DEMAND");
    	//AYC100 일일 근무배정표에서 변경대상 일련번호 (for valid)
    	String ckTargetSn	= searchListParam.getValue(0, "TASKWK_SCHDL_SN_TARGET");
    	if(demandSn != null && targetSn != null) {
    		if(demandSn.equalsIgnoreCase(ckDemandSn) && targetSn.equalsIgnoreCase(ckTargetSn)) {
    			mapParam.put("demandSn",demandSn);
    			mapParam.put("targetSn",targetSn);
    		}
    	}
    	mapParam.put("loginId", loginId);
    	
    	mapParam.put("rjctCsCn", rjctCsCn);
    	mapParam.put("demandInstNo", demandInstNo);
    	mapParam.put("targetInstNo", targetInstNo);
    	mapParam.put("demandId",demandId);
    	mapParam.put("targetId",targetId);
    	mapParam.put("prePrcsSttsSeCd", prePrcsSttsSeCd);
    	mapParam.put("prcsSttsSeCd",prcsSttsSeCd);
    	mapParam.put("indexSn", indexSn);
    	if(indexSn != null && !indexSn.isEmpty()) {
    		workChgMngService.processWorkChgMng(mapParam);
    	}
    	//System.out.println("processWorkChgMng mapParam : "+mapParam.toString());
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/processWorkChgMngBatch.do") //////////////////////////////
    public View processWorkChgMngBatch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+searchParam.toString());    	
    	//조회 조건 param
    	String bgngYmd = searchParam.getValue("BGNG_YMD");
    	String endYmd = searchParam.getValue("END_YMD");

    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		String retVal = "";
		loginId	= loginVO.getId();
    	
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	
    	mapParam.put("bgngYmd",bgngYmd.replace("-", ""));
    	mapParam.put("endYmd",endYmd.replace("-", ""));
    	mapParam.put("loginId", loginId);
//    	mapParam.put("retVal", retVal);
    	try {
    		retVal = workChgMngService.processWorkChgMngBatch(mapParam);
		} catch (Exception e) {
			// TODO: handle exception
			retVal = "-1";
		}
//System.out.println("retVal : "+mapParam.get("retVal")+"");    	
//    	retVal = mapParam.get("retVal")+"";
    	retMap.put("MSG", retVal);
    	dataRequest.setMetadata(retVal!="-1", retMap);
    	return new JSONDataView();
    }
    
    
    @RequestMapping("/processWorkChgMngSms.do")
    public View processWorkChgMngSms(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	//조회 조건 param
    	String bgngYmd = searchParam.getValue("BGNG_YMD");
    	String endYmd = searchParam.getValue("END_YMD");
    	
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		int retVal = 0;
		loginId	= loginVO.getId();
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	mapParam.put("loginId", loginId);
    	mapParam.put("bgngYmd",bgngYmd.replace("-", ""));
    	mapParam.put("endYmd",endYmd.replace("-", ""));
    	
    	List<Map<String, Object>> lmsList = new ArrayList<Map<String,Object>>();
    	
    	lmsList = workChgMngService.selectWorkChgMngSms(mapParam);
    	//System.out.println("일괄문자발송 목록 건수 === " + lmsList.size());
    	
    	int demandVal = 0;
    	int targetVal = 0;
    	if(lmsList.size() > 0) {
    		// 근무변경 요청자 목록
    		Map<String, Object> DemandMap = new HashMap<String, Object>();
    		// 근무변경 대상자 목록
    		Map<String, Object> TargetMap = new HashMap<String, Object>();
    		
    		int demandContentsInfoSeq = 0;
    		int demandMsgResult = 0;
    		int targetContentsInfoSeq = 0;
    		int targetMsgResult = 0;
    		
        	for (Map<String, Object> map : lmsList) {
    			try {
    				//System.out.println("map.toString()"+map.toString());
    				map.put("loginId", loginId);
    				
    				if(map.get("DEMAND_CALL_TO") == null) {
    					demandVal++;
//    					continue;
    				} else {
    					if(map.get("DEMAND_CALL_TO") != null || map.get("DEMAND_CALL_TO").toString().isEmpty()) {
    						
//    						if(map.get("DEMAND_CALL_TO").toString().contains("-")) {
//    							// 현재 TO-BE 데이터가 부적합하기 때문에 - 나중에 없애도 된다.
//    							continue;
//    						} else {
    							DemandMap.put("CALL_TO", map.get("DEMAND_CALL_TO").toString().replace("-", ""));
    							DemandMap.put("MSG_CONTENTS", map.get("DEMAND_MSG_CONTENTS"));
    							DemandMap.put("MSG_SUBJECT", map.get("MSG_SUBJECT"));
    							DemandMap.put("loginId", loginId);
    							
    							demandContentsInfoSeq = workChgMngService.processWorkChgMngSms1(DemandMap);
    							//System.out.println("demandContentsInfoSeq == " + DemandMap.get("CONT_SEQ"));
    							DemandMap.put("CONT_SEQ", DemandMap.get("CONT_SEQ"));
    							
    							demandMsgResult = workChgMngService.processWorkChgMngSms2(DemandMap);
    							//System.out.println("demandMsgResult == " + demandMsgResult);
    							
//    						}
    						
    					} else {
    						continue;
    					}
    				}
    				
    				if(map.get("TARGET_CALL_TO") == null) {
    					targetVal++;
//    					continue;
    				} else {
    					if(map.get("TARGET_CALL_TO") != null || !map.get("TARGET_CALL_TO").toString().isEmpty()) {
    						
//    						if(map.get("TARGET_CALL_TO").toString().contains("-")) {
//    							// 현재 TO-BE 데이터가 부적합하기 때문에 - 나중에 없애도 된다.
//    							continue;
//    						} else {
    							TargetMap.put("CALL_TO", map.get("TARGET_CALL_TO").toString().replace("-", ""));
        						TargetMap.put("MSG_CONTENTS", map.get("TARGET_MSG_CONTENTS"));
        						TargetMap.put("MSG_SUBJECT", map.get("MSG_SUBJECT"));
        						TargetMap.put("loginId", loginId);
        						
        						targetContentsInfoSeq = workChgMngService.processWorkChgMngSms1(TargetMap);
    							//System.out.println("targetContentsInfoSeq == " + TargetMap.get("CONT_SEQ"));
    							DemandMap.put("CONT_SEQ", TargetMap.get("CONT_SEQ"));
    							
    							targetMsgResult = workChgMngService.processWorkChgMngSms2(TargetMap);
    							//System.out.println("targetMsgResult == " + targetMsgResult);
//    						}
    						
    					} else {
    						continue;
    					}
    				}
    				
//    				int ret1 = workChgMngService.processWorkChgMngSms1(map);
//    				System.out.println("ret1 == " + ret1);
//    				int ret2 = workChgMngService.processWorkChgMngSms2(map);
//    				int ret3 = workChgMngService.processWorkChgMngSms3(map);
//    				retVal = 1;
//    				System.out.println(ret1+""+ret2+""+ret3);
    				int smsSndngYnResult = workChgMngService.processWorkChgMngSms3(map);
					//System.out.println("smsSndngYnResult == " + smsSndngYnResult);
					
					if(demandVal == 0 && targetVal == 0) {
						retVal = 1;
					} else {
						retVal = -2;
					}
					
    			} catch (Exception e) {
    				retVal = -1;
    				break;
    			}
    		}
    	} else {
    		retVal = 0;
    	}
    	
    	mapParam.put("retVal", retVal);
    	mapParam.put("demandVal", demandVal);
    	mapParam.put("targetVal", targetVal);
    	dataRequest.setMetadata(true, mapParam);
    	return new JSONDataView();
    }
    
   
    
}