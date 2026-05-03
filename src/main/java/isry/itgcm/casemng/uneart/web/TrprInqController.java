/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import com.dreamsecurity.magice2e.util.Log;

import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;


/**
* @Class Name  : TrprInqController.java
* @Description : 대상자정보 Controller Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 18.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 18.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Controller						 
@RequestMapping(value = "/isry/itgcm/casemng/uneart")
public class TrprInqController {
	
	@Resource(name = "trprInqService")
	private TrprInqService trprInqService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	/**
	 * @Method     : selectTrprInqList
	 * @Method설명 : 대상자 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	@RequestMapping(value = "/selectTrprInqList.do")
	public View selectTrprInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = trprInqService.selectTrprInqList(request, dataRequest);
		
		dataRequest.setResponse("dsList", retMap.get("dsList"));
		dataRequest.setResponse("dmPage", retMap.get("dmPage"));

		return new JSONDataView();
	}

	/**
	 * @Method     : selectTrprInqDetail
	 * @Method설명 : 대상자 상세조회, 본인인증정보조회, 개인식별목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	@RequestMapping(value = "/selectTrprInqDetail.do")
	public View selectTrprInqDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 대상자 상세조회
		Map<String, Object> retMap    = trprInqService.selectTrprInqDetail(dataRequest); 
		dataRequest.setResponse("dmDetail",     retMap.get("dmDetail"));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectPersonalInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 1. 
	 * @Method설명 : 발굴대상자개인정보조회
	 */
	@RequestMapping(value = {"/selectPersonalInfo.do", "/selectPersonalInfoHistory.do"})
	public View selectPersonalInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		Log.info("발굴대상자개인정보조회=[" + requestUrl);
		
		if(requestUrl.endsWith("/selectPersonalInfo.do")) {
			dataRequest.setResponse("dsList",     trprInqService.selectPersonalInfo(request, dataRequest));
		}
		if(requestUrl.endsWith("/selectPersonalInfoHistory.do")) {
			dataRequest.setResponse("dsList",     trprInqService.selectPersonalInfoHistory(request, dataRequest));
		}
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : processTrprInqDetail
	 * @Method설명 : 대상자 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	@RequestMapping(value = "/processTrprInqDetail.do")
	public View processTrprInqDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = trprInqService.processTrprInqDetail(request, dataRequest);
		
		// 재조회시 대상자정보번호(TR) 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("TRPR_INFO_NO", retMap.get("TRPR_INFO_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : deleteTrprInqDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 18. 
	 * @Method설명 : 대삭자삭제
	 */
	@RequestMapping(value = "/deleteTrprInqDetail.do")
	public View deleteTrprInqDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		trprInqService.deleteTrprInqDetail(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명   : selectCaseTrprFamList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례가족대상자 목록 조회
	 */
	@RequestMapping(value = "/selectCaseTrprFamList.do")
	public View selectCaseFamInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = trprInqService.selectCaseTrprFamList(dataRequest);
		dataRequest.setResponse("dsFamInfo", list);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectCaseFamInfoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태 목록 조회
	 */
	@RequestMapping(value = "/selectAcbgSttsList.do")
	public View selectAcbgSttsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = trprInqService.selectAcbgSttsList(dataRequest);
		dataRequest.setResponse("dsAcbgStts", list);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectSchulwDscntcList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단 목록 조회
	 */
	@RequestMapping(value = "/selectSchulwDscntcList.do")
	public View selectSchulwDscntcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = trprInqService.selectSchulwDscntcList(dataRequest);
		dataRequest.setResponse("dsSchulwDscntc", list);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectEmpymnInfoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 취업정보 목록 조회
	 */
	@RequestMapping(value = "/selectEmpymnInfoList.do")
	public View selectEmpymnInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = trprInqService.selectEmpymnInfoList(dataRequest);
		dataRequest.setResponse("dsEmpymnInfo", list);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectTrprQlfcInfoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 대상자자격정보 목록 조회
	 */
	@RequestMapping(value = "/selectTrprQlfcInfoList.do")
	public View selectQlfcInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = trprInqService.selectTrprQlfcInfoList(dataRequest);
		dataRequest.setResponse("dsQlfcInfo", list);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectPrvcHistoryList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 개인정보이력 목록 조회
	 */
	@RequestMapping(value = "/selectPrvcHistoryList.do")
	public View selectPrvcHistoryList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = trprInqService.selectPrvcHistoryList(dataRequest);
		dataRequest.setResponse("dsPrvcChgHstr", list);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : getTrprCaseDpcnInq
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 : 대상자 사례진행여부 확인( 사례관리구분코드가 미선정이나 사례대상자신청(대기상태)로 들어오면 저장전 확인)
	 */
	@RequestMapping(value = "/selectTrprCaseDpcnInq.do")
	public View selectTrprCaseDpcnInq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = trprInqService.selectTrprCaseDpcnInq(dataRequest);
		
//		Map<String, Object> message = new HashMap<String, Object>();
//		message.put("TRPR_CNT", retMap.get("TRPR_CNT"));		
//		dataRequest.setMetadata(true, message);		
		
		
		dataRequest.setMetadata(true, retMap);
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectTrprRegCnt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 대상자등록 확인(발굴상담, 아웃리치, 긴급구조, 복지부연계, 1338상담)
	 */
	@RequestMapping(value = "/selectTrprRegCnt.do")
	public View selectTrprRegCnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = trprInqService.selectTrprRegCnt(dataRequest);
		dataRequest.setResponse("dmRegCntNm", retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectAEB100List
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 면접심사자 (청소년자립지원관 면접심사 조회 - 고유)
	 */
	@RequestMapping(value = "/selectAEB100List.do")
	public View selectAEB100List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list  = trprInqService.selectAEB100List(dataRequest);
		dataRequest.setResponse("dsIntrvwSrng", list);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectPicList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 04. 12. 
	 * @Method설명 : 총괄 담당자, 기관 담당자 조회
	 */
	@RequestMapping(value = "/selectPicList.do")
	public View selectPicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list  = trprInqService.selectPicList(dataRequest);
		dataRequest.setResponse("dsPicSearch", list);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectCaseCnt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 05. 17. 
	 * @Method설명 : 대상자 진행중인 사례건수 조회
	 */
	@RequestMapping(value = "/selectCaseCnt.do")
	public View selectCaseCnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> rtnMap  = trprInqService.selectCaseCnt(dataRequest);
		dataRequest.setResponse("dmCaseCnt", rtnMap);
		
		return new JSONDataView();
	}
}
