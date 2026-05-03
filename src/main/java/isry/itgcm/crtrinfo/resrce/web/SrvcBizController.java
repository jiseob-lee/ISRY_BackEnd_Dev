/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.crtrinfo.resrce.service.SrvcBizService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.uneartmng.policelinkaply.service.PicMngService;

/**
* @Class Name  : SrvcBizController.java
* @Description : 서비스사업 Controller Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 07. 18.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 
* </pre>
*/
@Controller
@RequestMapping("/isry/itgcm/crtrinfo/resrce")
public class SrvcBizController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "srvcBizService")
	private SrvcBizService srvcBizService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "picMngService")
	private PicMngService picMngService;
	
	/**
	 * @Method명   : selectSrvcBizOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
	 * @Method설명 : 서비스사업 공통코드 조회
	 */
	@RequestMapping(value = "/selectSrvcBizOnload.do")
	public View selectInstInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		String codeId = "";				// 공통코드 아이디
		 String dataSetNm = "";				// 데이터셋
		 ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		 
		 HttpSession session = request.getSession();
		 UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		 
		 LOGGER.debug("selectSrvcBizOnload.paramGroup=[" + paramGroup + "]" );
		 
		 if (paramGroup != null) {
			 
			 List<Map<String, String>> paramList = paramGroup.getAllRowList();
			 
			 for (Map<String, String> rowMap : paramList) {
				 
				 dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); 		// 응답 데이터셋 
				 codeId = String.valueOf(rowMap.get("CMMNS_CD_ID"));		// 요청 공통코드 아이디
				 LOGGER.debug("selectSrvcBizOnload.dataSetNm=[" + dataSetNm + "]" );
				 LOGGER.debug("selectSrvcBizOnload.codeId   =[" + codeId + "]" );
				 // 시스템 공통코드 조회 서비스 요청
				 List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, userVo.getUntTaskwk());
				 // 응답객체 셋팅
				 dataRequest.setResponse(dataSetNm, list);
			 }
		 }		

		return new JSONDataView();
	}

	/**
	 * @Method     : selectSrvcBizList
	 * @Method설명 : 서비스사업 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@RequestMapping(value = "/selectSrvcBizList.do")
	public View selectTrprInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//List<Map<String, Object>> list = srvcBizService.selectSrvcBizList(request, dataRequest);
		//dataRequest.setResponse("dsServiceMainList", list);

		Map<String, Object> result =  srvcBizService.selectSrvcBizList(request, dataRequest);
		
		dataRequest.setResponse("dsServiceMainList", result.get("dsServiceMainList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectSrvcBizDetail
	 * @Method설명 : 서비스사업 상세조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@RequestMapping(value = "/selectSrvcBizDetail.do")
	public View selectTrprInqDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list    = srvcBizService.selectSrvcBizDetail(dataRequest);
		
		// 서비스사업 상세조회
		dataRequest.setResponse("dsDetail",     list);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectSrvcOrgRegionSgg.do")
	public View selectSrvcOrgRegionSgg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsOrgRegionSgg", picMngService.selectRegion2());
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectSrvcExcnList
	 * @Method설명 : 서비스실행사업 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectSrvcExcnList.do")
	public View selectResrceProgrmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcBizService.selectSrvcExcnBizList(dataRequest);

		// 서비스실행사업 목록조회
		dataRequest.setResponse("dsServiceSubList",  list);
							

		return new JSONDataView();
	}

	/**
	 * @Method     : processSrvcBizDetail
	 * @Method설명 : 서비스사업 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@RequestMapping(value = "/processSrvcBizDetail.do")
	public View processSrvcBizDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = srvcBizService.processSrvcBizDetail(request, dataRequest);

		// 재조회시 서비스사업번호 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("SRVC_BIZ_NO", retMap.get("SRVC_BIZ_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

	/**
	 * @Method     : selectExcnSrvcBizList
	 * @Method설명 : 실행서비스사업 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@RequestMapping(value = "/selectExcnSrvcBizList.do")
	public View selectExcnSrvcBizList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcBizService.selectExcnSrvcBizList(dataRequest);

		// 서비스실행사업 목록조회
		dataRequest.setResponse("dsExcnSrvcBizClList",  list);
							

		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectExcnSrvcDetList
	 * @Method설명 : 실행서비스세부사업 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@RequestMapping(value = "/selectExcnSrvcDetList.do")
	public View selectExcnSrvcDetList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcBizService.selectExcnSrvcDetList(dataRequest);

		// 서비스실행사업 목록조회
		dataRequest.setResponse("dsExcnSrvcDetaiaBizList",  list);
							

		return new JSONDataView();
	}
	
	/**
	 * @Method     : saveExcnSrvcBiz
	 * @Method설명 : 실행서비스세부사업 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@RequestMapping(value = "/saveExcnSrvcBiz.do")
	public View saveExcnSrvcBiz(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = srvcBizService.saveExcnSrvcBiz(request, dataRequest);

		// 재조회시 서비스사업번호 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("SRVC_BIZ_NO", retMap.get("SRVC_BIZ_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectExcnDetaiaList
	 * @Method설명 : 실행서비스 세부사업 목록 팝업 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@RequestMapping(value = "/selectExcnDetaiaList.do")
	public View selectExcnDetaiaList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcBizService.selectExcnDetaiaList(dataRequest);

		// 서비스실행사업 목록조회
		dataRequest.setResponse("dsExcnSrvcBizClList",  list);
							

		return new JSONDataView();
	}
	
	/**
	 * @Method     : deleteExcnSrvcBiz
	 * @Method설명 : 실행서비스세부사업 삭제(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 08. 
 	 */	
	@RequestMapping(value = "/deleteExcnSrvcBiz.do")
	public View deleteExcnSrvcBiz(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		srvcBizService.deleteExcnSrvcBiz(request, dataRequest);
		
		return new JSONDataView();
	}
}
