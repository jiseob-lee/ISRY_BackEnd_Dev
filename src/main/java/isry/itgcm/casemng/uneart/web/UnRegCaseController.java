/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.casemng.uneart.service.UnRegCaseService;
import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.uneartmng.policelinkaply.service.PicMngService;

/**
* @Class Name  : UnRegCaseController.java
* @Description : 미등록사례지원 Class
*
* @author  : Hee Sung Yoon
* @since   : 2023. 01. 10.
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2023. 01. 10.  Hee Sung Yoon   최초작성
*/
@Controller
@RequestMapping("/isry/itgcm/casemng/uneart")
public class UnRegCaseController {
	
	
	@Resource(name = "unRegCaseService")
	private UnRegCaseService unRegCaseService;
	
	@Resource(name = "reqUserJoinService")
	private ReqUserJoinService reqUserJoinService;
	
	@Resource(name = "picMngService")
	private PicMngService picMngService;
	/**
	 * @Method     : selectUnRegCaseList
	 * @Method설명 : 미등록사례지원 목록 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/selectUnRegCaseList.do")
	public View selectUnRegCaseList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = unRegCaseService.selectUnRegCaseList(request, dataRequest);
		
		// 미등록사례지원 목록조회
		dataRequest.setResponse("dsList",   retMap.get("dsList"));
		dataRequest.setResponse("dmPage",   retMap.get("dmPage"));

		return new JSONDataView();
	}

	/**
	 * @Method     : insertUnRegCase
	 * @Method설명 : 미등록사례지원 등록
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/insertUnRegCase.do")
	public View insertUnRegCase(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dmParam",   unRegCaseService.insertUnRegCase(request, dataRequest));
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectBizList
	 * @Method설명 : 사업목록 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/selectBizList.do")
	public View selectBizList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList = unRegCaseService.selectBizList(dataRequest);
		List<Map<String, Object>> excnDetaiaBizList = unRegCaseService.selectExcnDetaiaBizList(dataRequest);
		
		// 미등록사례지원 목록조회
		dataRequest.setResponse("dsBizReg",   retList);
		dataRequest.setResponse("dsExcnSrvcBizClList",   excnDetaiaBizList);

		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectCnterSprtList
	 * @Method설명 : 시군구센터지원 목록 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/selectCnterSprtList.do")
	public View selectCnterSprtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> result = unRegCaseService.selectCnterSprtList(request, dataRequest);
		
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectSidoInst
	 * @Method설명 : 시도, 시군구, 기관 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/selectSidoInst.do")
	public View selectSidoInst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsRegionSido", picMngService.selectRegion());
		dataRequest.setResponse("dsRegionSgg", picMngService.selectRegion2());
		
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		dataRequest.setResponse("dsInst", unRegCaseService.selectInstList(dataRequest));
		return new JSONDataView();
	}
	
	/**
	 * @Method     : insertCnterSprt
	 * @Method설명 : 시군구센터지원 등록
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/insertCnterSprt.do")
	public View insertCnterSprt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dmParam",   unRegCaseService.insertCnterSprt(request, dataRequest));
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectSidoInst
	 * @Method설명 : 실적컨설팅센터 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/selectCnstnCnterInst.do")
	public View selectCnstnCnterInst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> retList = unRegCaseService.selectCnstnCnterInst(dataRequest);
		dataRequest.setResponse("dsCnterSprtInst",   retList);

		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectCnterBizList
	 * @Method설명 : 시군구센터지원 사업목록 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	@RequestMapping(value = "/selectCnterBizList.do")
	public View selectCnterBizList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList = unRegCaseService.selectCnterBizList(dataRequest);
		List<Map<String, Object>> excnDetaiaBizList = unRegCaseService.selectCnterExcnDetaiaBizList(dataRequest);
		dataRequest.setResponse("dsBizReg",   retList);
		dataRequest.setResponse("dsExcnSrvcBizClList",   excnDetaiaBizList);
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectUnRegCasePicList
	 * @Method설명 : 미등록사례지원 담당자 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 12. 
 	 */	
	@RequestMapping(value = "/selectUnRegCasePicList.do")
	public View selectUnRegCasePicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> retList = unRegCaseService.selectUnRegCasePic(dataRequest);
		
		dataRequest.setResponse("dsPic", retList);
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectUnRegCaseTrprList
	 * @Method설명 : 미등록사례지원 사례대상자 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */	
	@RequestMapping(value = "/selectUnRegCaseTrprList.do")
	public View selectUnRegCaseTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> retList = unRegCaseService.selectUnRegCaseTrpr(dataRequest);
		
		dataRequest.setResponse("dsCaseTrprList", retList);
		return new JSONDataView();
	}
}
