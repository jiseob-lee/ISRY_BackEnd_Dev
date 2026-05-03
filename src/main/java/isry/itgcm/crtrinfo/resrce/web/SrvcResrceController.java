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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcm.crtrinfo.resrce.service.SrvcResrceService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.cmmn.service.SubmsService;


/**
* @Class Name  : SrvcResrceController.java
* @Description : 자원정보 Controller Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 24.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 24.  Kwon.Min.Seo    최초작성
* </pre>
*/
@Controller
@RequestMapping(value = "/isry/itgcm/crtrinfo/resrce")
public class SrvcResrceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "srvcResrceService")
	private SrvcResrceService srvcResrceService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;
	
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "submsService")
	private SubmsService submsService;
	
	/**
	 * @Method     : selectSrvcResrceOnLoad
	 * @Method설명 : 자원조회 OnLoad
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
	 * ******************************
	 * 공통코드 조회 조건 (dsCodeParam)
	 * 1.CMMNS_CD_ID       : 공통코드아이디 (필수)  - ex) SRVC_RESRCE_LCLAS_SE_CD
	 * 2.DS_SET_NM         : RETURN 데이터셋 (필수) - ex) dsSrvcResrceLclasSeCd
	 * 3.CMMNS_CD_VALUE    : 공통코드값
	 * 4.CMMNS_CD_VALUE_NM : 공통코드값명
	 * 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 * 6.ADDTNG_MNG_VALUE2 : 추가관리값2
	 * 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 * 8.ADDTNG_MNG_VALUE4 : 추가관리값4
	 * 9.ADDTNG_MNG_VALUE5 : 추가관리값5
	 *10.USE_YN            : 사용여부
	 */	
	@RequestMapping(value = "/selectSrvcResrceOnLoad.do")
	public View selectSrvcResrceOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
        UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		LOGGER.debug("selectSrvcResrceOnLoad.paramGroup=[" + paramGroup + "]");

		List<Map<String, String>> paramList = paramGroup.getAllRowList();
			
		for (Map<String, String> rowMap : paramList) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			
			sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
			
			LOGGER.debug("CMMNS_CD_ID : " + rowMap.get("CMMNS_CD_ID"));
			LOGGER.debug("UNIT_CODE : " + rowMap.get("UNIT_CODE"));
			
			if("SRVC_TYPE_SE_CD".equals(rowMap.get("CMMNS_CD_ID"))) {
				list = mgmtCmmnCodeService.selectCommonCodeUnit(rowMap.get("CMMNS_CD_ID"), rowMap.get("UNIT_CODE"));				
			} else {
				rowMap.put("unitCode", userVo.getUntTaskwk());
				list = comCodeService.selectCommonCodeUnit(rowMap);
			}

			dataRequest.setResponse(sRetDsSet, list);
			
		}

		return new JSONDataView();
	}

	/**
	 * @Method     : selectResrceList
	 * @Method설명 : 자원 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrceList.do")
	public View selectResrceList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//List<Map<String, Object>> list = srvcResrceService.selectResrceList(dataRequest, request);
		
		Map<String, Object> result = srvcResrceService.selectResrcePagingList(dataRequest, request);
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPageInfo", result.get("dmPageInfo"));

		return new JSONDataView();
	}

	/**
	 * @Method     : selectResrceDetail
	 * @Method설명 : 자원 상세조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrceDetail.do")
	public View selectResrceDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap    = srvcResrceService.selectResrceDetail(dataRequest);
		
		// 자원 상세조회
		dataRequest.setResponse("dsDetail",      retMap.get("dsDetail"));
		
		// 자원 프로그램조회
		dataRequest.setResponse("dsProgrmList",  retMap.get("dsProgrmList"));
		
		// 자원 프로그램상세일정
		dataRequest.setResponse("dsSchdlList",   retMap.get("dsSchdlList"));
		
		// 자원 프로그램강사
		dataRequest.setResponse("dsInstrList",   retMap.get("dsInstrList"));
		
		// 자원 담당자조회
		dataRequest.setResponse("dsPicList",     retMap.get("dsPicList"));
		
		// 자원 변경이력
		dataRequest.setResponse("dsChgHstrList", retMap.get("dsChgHstrList"));
		
		// 입교일정 조회
//		dataRequest.setResponse("dsEntscList", retMap.get("dsEntscList"));
		
		return new JSONDataView();
	}
	/**
	 * @Method     : selectResrceProgrmList
	 * @Method설명 : 자원 프로그램조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrceProgrmList.do")
	public View selectResrceProgrmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcResrceService.selectResrceProgrmList(dataRequest);

		// 자원 프로그램조회
		dataRequest.setResponse("dsProgrmList",  list);

		return new JSONDataView();
	}
	/**
	 * @Method     : selectResrceProgrmSchdlList
	 * @Method설명 : 자원 프로그램상세일정
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrceProgrmSchdlList.do")
	public View selectResrceProgrmSchdlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcResrceService.selectResrceProgrmSchdlList(dataRequest);

		// 자원 프로그램상세일정
		dataRequest.setResponse("dsSchdlList", list);

		return new JSONDataView();
	}
	/**
	 * @Method     : selectResrceProgrmInstrList
	 * @Method설명 : 자원 프로그램강사
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrceProgrmInstrList.do")
	public View selectResrceProgrmInstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcResrceService.selectResrceProgrmInstrList(dataRequest);

		// 자원 프로그램강사
		dataRequest.setResponse("dsInstrList", list);

		return new JSONDataView();
	}
	/**
	 * @Method     : selectResrcePicList
	 * @Method설명 : 자원 담당자조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrcePicList.do")
	public View selectResrcePicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcResrceService.selectResrcePicList(dataRequest);

		// 자원 담당자조회
		dataRequest.setResponse("dsPicList", list);

		return new JSONDataView();
	}
	/**
	 * @Method     : selectResrceChgHstrList
	 * @Method설명 : 자원 변경이력
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/selectResrceChgHstrList.do")
	public View selectResrceChgHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcResrceService.selectResrceChgHstrList(dataRequest);

		// 자원 변경이력
		dataRequest.setResponse("dsChgHstrList", list);
		
		return new JSONDataView();
	}

	/**
	 * @Method     : processResrceDetail
	 * @Method설명 : 자원 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/processResrceDetail.do")
	public View processResrceDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = srvcResrceService.processResrceDetail(request, dataRequest);

		// 재조회시 자원번호 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("RESRCE_NO", retMap.get("RESRCE_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

	/**
	 * @Method     : processDscsnOutrcDetail
	 * @Method설명 : 자원 승인(반려)처리
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	@RequestMapping(value = "/processAprvPrcs.do")
	public View processAprvPrcs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		srvcResrceService.processAprvPrcs(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectEduSchdlCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 6. 13.
	 * @Method설명 : 교육시간표 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectEduSchdlCombo.do")
	public View selectEduSchdlCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		// 사업연도
		List<Map<String, Object>> listBizYr			= srvcResrceService.selectBizYrCombo   (request);
		// 교육과정
		List<Map<String, Object>> listResrce        = srvcResrceService.selectResrceNmCombo(request);
		// 교육기관
//		List<Map<String, Object>> listInst          = srvcResrceService.selectInstNmCombo  (request);
		
		// 교육기관(신규) - 이승재 2023.02.27 기존의 기관콤보 어디서 사용하는지 파악어려워 신규로 작성.
		
		dataRequest.setResponse("dsInst", submsService.selectInstNmCombo(request));

//		Map<String, Object> map = new HashMap<>();
//		for(int i=0; i<listInst.toArray().length; i++) {
//			map = listInst.get(i);
			
//			if("5".equals(listInst.get(0).get("INST_TYPE_SE_CD").toString())) { // 5:시도수행기관
//			if("8".equals(listInst.get(0).get("INST_TYPE_SE_CD").toString())) { // 8:시군구수행기관
//				List<Map<String, Object>> listInst1          = srvcResrceService.selectInstNmCombo1  (request);
//				listInst.set(i, map);
//				dataRequest.setResponse("dsInst"  		 , listInst1);
//			}else if("6".equals(listInst.get(0).get("INST_TYPE_SE_CD").toString())) { // 4:중앙관리기관
//				List<Map<String, Object>> listInst2          = srvcResrceService.selectInstNmCombo3  (request);
//				listInst2.set(i, map);
//				dataRequest.setResponse("dsInst"  		 , listInst2);
//			}else if("1".equals(listInst.get(0).get("INST_TYPE_SE_CD").toString())) { // 1:여가부
//				List<Map<String, Object>> listInst3          = srvcResrceService.selectInstNmCombo2  (request);
//				listInst3.set(i, map);
//				dataRequest.setResponse("dsInst"  		 , listInst3);
//			}
//		}
		
		List<Map<String, Object>> listMmSeCd        = mgmtCmmnCodeService.selectCommonCodeUnit("MM_SE_CD", userVo.getUntTaskwk());


//		List<Map<String, Object>> listEduProgrmSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> listEduProgrmSeCd = srvcResrceService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", userVo.getUntTaskwk());
		if(listEduProgrmSeCd.size() == 0) {
			listEduProgrmSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", userVo.getUntTaskwk());
//			listEduProgrmSeCd = srvcResrceService.selectCommonCode("EDU_PROGRM_SE_CD");	
		}

		dataRequest.setResponse("dsBizYr"  		 , listBizYr);
		dataRequest.setResponse("dsResrce"		 , listResrce);
//		dataRequest.setResponse("dsInst"  		 , listInst);
		dataRequest.setResponse("dsMmSeCd"  	 , listMmSeCd);
		dataRequest.setResponse("dsEduProgrmSeCd", listEduProgrmSeCd);

		return new JSONDataView();
	}
	
	/**
	 * 교육시간표 상세 목록 조회
	 * @Method명   : selectEduSchdlDtlList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 교육시간표 상세 목록 조회
	 */
	@RequestMapping(value = "/selectEduSchdlDtlList.do")
	public View selectEduSchdlDtlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//교육시간표 상세 목록 조회
		List<Map<String, Object>> list = srvcResrceService.selectEduSchdlDtlList(dataRequest);
		dataRequest.setResponse("dsEduSchdlDtlList", list);

		return new JSONDataView();
	}

	/**
	 * 교육시간표 상세 일괄등록 조회
	 * @Method명   : selectEduHrDtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 교육시간표 상세 일괄등록 조회
	 */	
	@RequestMapping(value = "/selectEduHrDtList.do")
	public View selectEduHrDtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		dataRequest.setResponse("dsEduHrDtList", srvcResrceService.selectEduHrDt(dataRequest)); 
		return new JSONDataView();
	}
	
	/**
	 * @Method     : processExcelUpload
	 * @Method설명 : 교육시간표상세 일괄등록 엑셀업로드
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	@RequestMapping(value = "/processEduHrDtlRegExcelUpload.do")
	public View processEduHrDtlRegExcelUpload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		srvcResrceService.processEduHrDtlRegExcelUpload(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : processExcelUpload
	 * @Method설명 : 교육시간표상세 일괄등록 전체삭제
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	@RequestMapping(value = "/processAllDel.do")
	public View processAllDel(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		srvcResrceService.processAllDel(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : processAplcn
	 * @Method설명 : 스케쥴 적용
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	@RequestMapping(value = "/processAplcn.do")
	public View processAplcn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		srvcResrceService.processAplcnDel(request, dataRequest);
		
		srvcResrceService.processAplcn(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	 * @Method     : selectInstrInfo
	 * @Method설명 : 강사조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il.
	 * @작성일     : 2022. 07. 28. 
 	 */	
	@RequestMapping(value = "/selectInstrInfo.do")
	public View selectInstrInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		dataRequest.setResponse("dsInstrList", srvcResrceService.selectInstr(dataRequest)); 
		return new JSONDataView();
	} 
	
	/**
	 * 교육시간표 상세 전제삭제
	 * @Method명   : processAllDelDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : processAllDelDtl
	 */
	@RequestMapping(value = "/processAllDelDtl.do")
	public View processAllDelDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		srvcResrceService.processAplcnDel(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectResrceNmChk
	 * @Method설명 : 자원명 중복조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 11. 08. 
		 */	
	@RequestMapping(value = "/selectResrceNmChk.do")
	public View selectResrceNmChk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = srvcResrceService.selectResrceNmChk(request, dataRequest);
	
		// 자원 담당자조회
		dataRequest.setResponse("dmChkNm", map);
	
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectRsfrMbyInstChk
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 28. 
	 * @Method설명 : 자원제공 주체 조회
	 */
	@RequestMapping(value = "/selectRsfrMbyInstChk.do")
	public View selectRsfrMbyInstChk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = srvcResrceService.selectRsfrMbyInstChk(request, dataRequest);
		
		// 자원제공주체조회
		dataRequest.setResponse("dmChkNm", map);
		
		
		return new JSONDataView();
	}		
	
	/**
	 * @Method     : selectEduCrseChk
	 * @Method설명 : 교육과정확인 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 11. 18. 
		 */	
	@RequestMapping(value = "/subEduCrseChk.do")
	public View selectEduCrseChk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
//		dataRequest.setResponse("dmEduCrseChk" , srvcResrceService.selectEduCrseChk(request, dataRequest));
		
		Map<String, Object> map = srvcResrceService.selectEduCrseChk(request, dataRequest);
		
		if(map != null) {
//			LOGGER.debug("dmEduCrseChk :::::::::" + map.toString());
			
			if(!"0".equals(map.get("CHK"))) {
				Map<String, Object> map1 = srvcResrceService.selectEduCrseChk1(request, dataRequest);
				Map<String, Object> map2 = srvcResrceService.selectEduCrseChk2(request, dataRequest);
				LOGGER.debug("111 :::::::::" + map1.toString());
				LOGGER.debug("222 :::::::::" + map2.toString());
				map.putAll(map1);
				map.putAll(map2);
			}
		}
		
		dataRequest.setResponse("dmEduCrseChk", map);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectResrceHistoryDetail
	 * @Method설명 : 이력 상세조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 11. 29. 
 	 */	
	@RequestMapping(value = "/selectResrceHistoryDetail.do")
	public View selectResrceHistoryDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap    = srvcResrceService.selectResrceHistory(dataRequest);
		
		// 자원 상세조회
		dataRequest.setResponse("dsDetail",      retMap.get("dsDetail"));
		
		// 자원 프로그램조회
		dataRequest.setResponse("dsProgrmList",  retMap.get("dsProgrmList"));
		
		// 자원 프로그램상세일정
		dataRequest.setResponse("dsSchdlList",   retMap.get("dsSchdlList"));
		
		// 자원 프로그램강사
		dataRequest.setResponse("dsInstrList",   retMap.get("dsInstrList"));
		
		// 자원 담당자조회
		dataRequest.setResponse("dsPicList",     retMap.get("dsPicList"));
		
		//파일 이력
		dataRequest.setResponse("dsValue",     retMap.get("dsValue"));
		
		// 자원 변경이력
//		dataRequest.setResponse("dsChgHstrList", retMap.get("dsChgHstrList"));
		
		// 입교일정 조회
//		dataRequest.setResponse("dsEntscList", retMap.get("dsEntscList"));
		
		return new JSONDataView();
	}	
}
