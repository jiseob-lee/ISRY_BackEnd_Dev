/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consultantabltymng.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.consultantabltymng.service.DscsnAbilitMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : DscsnAbilitMngController.java
 * @프로그램 설명 : 상담원 역량관리
 * - 
 * - CnterPreconController
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 01. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 01. 
 * @수정내용      : 상담원 역량관리
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/couns/mngr/consultantabltymng")
public class DscsnAbilitMngController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Resource(name = "dscsnAbilitMngService")
	private DscsnAbilitMngService dscsnAbilitMngService;
	
	@Resource(name = "counsService")
	private CounsService counsService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 평가구성 조회
	 * @Method명   : selectEvlCnsttnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 01. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlCnsttnList.do")
	public View selectEvlCnsttnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가구성 조회
		dataRequest.setResponse("dsEvlCnsttnList", dscsnAbilitMngService.selectEvlCnsttnList(dataRequest));
	
	return new JSONDataView();
	}
	
	/**
	 * 평가구성 등록
	 * @Method명   : evlCnsttnSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 01. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subEvlCnsttnInsert.do")
	@ResponseBody
	public View evlCnsttnInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evlCnsttnInsert(request, dataRequest);		
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value="/subCmmnsCd.do")
	public View selectRegion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsEvlTmeNo", mgmtCmmnCodeService.selectCommonCodeUnit("EVL_TME_SE_CD", userVo.getUntTaskwk()));  		// 평가회차번호	
		dataRequest.setResponse("dsGroupTypeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EVL_GROUP_SE_CD", userVo.getUntTaskwk()));  // 평가그룹
		dataRequest.setResponse("dsChklstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CHKLST_SE_CD", userVo.getUntTaskwk()));  // 체크리스트구분코드
		dataRequest.setResponse("dsEvlGroupTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EVL_GROUP_TYPE_SE_CD", userVo.getUntTaskwk()));  // 평가그룹유형구분코드
		dataRequest.setResponse("dsEduTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EDU_TYPE_SE_CD", userVo.getUntTaskwk()));  // 교육유형구분코드
		dataRequest.setResponse("dsDeptSeCd", counsService.selectOrgDeptCombo(request));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subEvlCnsttnUpdateInq.do")
	public View selectEvlCnsttnUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 평가구성수정 조회
		dataRequest.setResponse("dmEvlCnsttnReg" , dscsnAbilitMngService.selectEvlCnsttnUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가구성 수정
	 * @Method명   : evlCnsttnUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 02. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subEvlCnsttnUpdate.do")
	@ResponseBody
	public View evlCnsttnUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evlCnsttnUpdate(request, dataRequest);		
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 확정 수정
	 * @Method명   : cfmtnSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 02. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subCfmtnSave.do")
	@ResponseBody
	public View cfmtnSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.cfmtnSave(request, dataRequest);
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 우수사례로우삭제
	 * @Method명   : exclncCaseRowDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subExclncCaseRowDelete.do")
	@ResponseBody
	public View exclncCaseRowDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.exclncCaseRowDelete(request, dataRequest);
		log.debug("exclncCaseRowDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 우수사례전체삭제
	 * @Method명   : exclncCaseAllDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subExclncCaseAllDelete.do")
	@ResponseBody
	public View exclncCaseAllDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.exclncCaseAllDelete(request, dataRequest);
		log.debug("exclncCaseAllDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 수퍼비전전체삭제
	 * @Method명   : superVisionAllDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSuperVisionAllDelete.do")
	@ResponseBody
	public View superVisionAllDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.superVisionAllDelete(request, dataRequest);
		log.debug("superVisionAllDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 수퍼비전로우삭제
	 * @Method명   : superVisionRowDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSuperVisionRowDelete.do")
	@ResponseBody
	public View superVisionRowDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.superVisionRowDelete(request, dataRequest);
		log.debug("superVisionRowDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 확정 취소수정
	 * @Method명   : cfmtnRtrcnSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subCfmtnRtrcnSave.do")
	@ResponseBody
	public View cfmtnRtrcnSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.cfmtnRtrcnSave(request, dataRequest);
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가지관리 조회
	 * @Method명   : selectEvfoMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoMngList.do")
	public View selectEvfoMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 평가지관리 조회
	dataRequest.setResponse("dsEvfoMngList", dscsnAbilitMngService.selectEvfoMngList(dataRequest));
	
	return new JSONDataView();
	}

	/**
	 * 평가지관리 기본정보 조회
	 * @Method명   : selectEvfoMngBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoMngBassInfo.do")
	public View selectEvfoMngBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 평가지관리 기본정보 조회
		dataRequest.setResponse("dmEvfoMngBassInfo" , dscsnAbilitMngService.selectEvfoMngBassInfo(dataRequest));
		
		// 평가지관리 목록 조회
		dataRequest.setResponse("dsEvfoMngInfoList", dscsnAbilitMngService.selectEvfoMngInfoList(dataRequest));
			
		
		return new JSONDataView();
		
	}
	
	/**
	 * 교육관리 기본정보 조회
	 * @Method명   : selectEduMngBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEduMngBassInfo.do")
	public View selectEduMngBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 교육관리 기본정보 조회
		dataRequest.setResponse("dmEduMngBassInfo" , dscsnAbilitMngService.selectEduMngBassInfo(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가지 추가 등록
	 * @Method명   : evfoAddingInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvfoAdding.do")
	@ResponseBody
	public View evfoAddingInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoAddingInsert(request, dataRequest);		
		log.debug("evfoAddingInsert retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가지 추가 조회
	 * @Method명   : evfoAddingInq
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvfoAddingInq.do")
	@ResponseBody
	public View evfoAddingInq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 평가지 추가 조회
		dataRequest.setResponse("dmEvfoAdding" , dscsnAbilitMngService.selectEvfoAdding(dataRequest));
				
		return new JSONDataView();
	}
	
	/**
	 * 평가지관리 역량관리 기본정보 조회
	 * @Method명   : selectEvfoMngBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoAbilitMngBassInfo.do")
	public View selectEvfoAbilitMngBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 평가지관리 역량관리 기본정보 조회
		dataRequest.setResponse("dmEvfoAbilitMngBassInfo" , dscsnAbilitMngService.selectEvfoAbilitMngBassInfo(dataRequest));
		
		// 평가지관리 역량관리 목록 조회
		dataRequest.setResponse("dsEvfoAbilitMngList", dscsnAbilitMngService.selectEvfoAbilitMngList(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가지관리 기준관리 기본정보 조회
	 * @Method명   : selectEvfoCrtrMngBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoCrtrMngBassInfo.do")
	public View selectEvfoCrtrMngBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 평가지관리 기준관리 기본정보 조회
		dataRequest.setResponse("dmEvfoCrtrMngBassInfo" , dscsnAbilitMngService.selectEvfoCrtrMngBassInfo(dataRequest));
		
		// 평가지관리 기준관리 목록 조회
		dataRequest.setResponse("dsEvfoCrtrMngList", dscsnAbilitMngService.selectEvfoCrtrMngList(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가지 역량등록
	 * @Method명   : evfoAbilitMngInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvfoAbilitMng.do")
	@ResponseBody
	public View evfoAbilitMngInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoAbilitMngInsert(request, dataRequest);		
		log.debug("evfoAddingInsert retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가자 관리 조회
	 * @Method명   : selectApraiMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subApraiMngList.do")
	public View selectApraiMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 평가자 관리 조회
	dataRequest.setResponse("dsApraiMngList", dscsnAbilitMngService.selectApraiMngList(dataRequest));
	
	return new JSONDataView();
	}
	
	/**
	 * 평가 대상자 관리 조회
	 * @Method명   : selectEvlTrprMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlTrprMngList.do")
	public View selectEvlTrprMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 평가자 관리 조회
	dataRequest.setResponse("dsEvlTrprMngList", dscsnAbilitMngService.selectEvlTrprMngList(dataRequest));
	
	return new JSONDataView();
	}
	
	/**
	 * 평가대상자 선정 조회
	 * @Method명   : selectEvlTrprSlctnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlTrprSlctnList.do")
	public View selectEvlTrprSlctnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가대상자 선정 조회
		dataRequest.setResponse("dsEvlTrprSlctnList", dscsnAbilitMngService.selectEvlTrprSlctnList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 평가대상가 매칭 목록 조회
	 * @Method명   : selectEvlTrprMatchingList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlTrprMatchingList.do")
	public View selectEvlTrprMatchingList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가대상가 매칭 목록 조회
		dataRequest.setResponse("dsEvlTrprMatchingList", dscsnAbilitMngService.selectEvlTrprMatchingList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 평가대상자선택 목록 조회
	 * @Method명   : selectEvlTrprChcList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlTrprChcList.do")
	public View selectEvlTrprChcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가대상가 매칭 목록 조회
		dataRequest.setResponse("dsEvlTrprChcList", dscsnAbilitMngService.selectEvlTrprChcList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 평가대상자선정 등록
	 * @Method명   : evlTrprSlctnInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subEvlTrprSlctnInsert.do")
	@ResponseBody
	public View evlTrprSlctnInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.evlTrprSlctnInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가대상자선택 등록
	 * @Method명   : evlTrprChcInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subEvlTrprChcInsert.do")
	@ResponseBody
	public View evlTrprChcInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.evlTrprChcInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가서관리 조회
	 * @Method명   : selectEvlTrprMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlSeMngList.do")
	public View selectEvlSeMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 평가서관리 조회
	dataRequest.setResponse("dsEvlSeMngList", dscsnAbilitMngService.selectEvlSeMngList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 평가서관리 평가자목록 조회
	 * @Method명   : selectEvlTrprMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlSeMngInqList.do")
	public View selectEvlSeMngInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가서관리 조회
		dataRequest.setResponse("dsEvlSeMngInqList", dscsnAbilitMngService.selectEvlSeMngInqList(dataRequest));
	
		return new JSONDataView();
	
	}
	
	/**
	 * 역량수정 조회
	 * @Method명   : selectEvlCnsttnUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoAbilitUpdateInq.do")
	public View selectEvfoAbilitUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 역량수정 조회
		dataRequest.setResponse("dmEvfoAbilitUpdate" , dscsnAbilitMngService.selectEvfoAbilitUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 역량수정
	 * @Method명   : evfoAbilitUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvfoAbilitUpdate.do")
	@ResponseBody
	public View evfoAbilitUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoAbilitUpdate(request, dataRequest);
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 역량삭제
	 * @Method명   : evfoAbilitMngDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subEvfoAbilitMngDelete.do")
	@ResponseBody
	public View evfoAbilitMngDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoAbilitMngDelete(request, dataRequest);
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가기준 추가 조회
	 * @Method명   : selectEvfoCrtrAddingIngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoCrtrAddingIng.do")
	public View selectEvfoCrtrAddingIngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 평가기준 추가 조회
	dataRequest.setResponse("dsEvfoCrtrAddingList", dscsnAbilitMngService.selectEvfoCrtrAddingIngList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 평가기준추가 수정
	 * @Method명   : evfoCrtrAdding
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvfoCrtrAdding.do")
	@ResponseBody
	public View evfoCrtrAdding(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoCrtrAdding(request, dataRequest);
		log.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가기준수정 조회
	 * @Method명   : selectEvfoCrtrUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvfoCrtrUpdateInq.do")
	public View selectEvfoCrtrUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 평가기준수정 조회
		dataRequest.setResponse("dmEvfoCrtrUpdate" , dscsnAbilitMngService.selectEvfoCrtrUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가기준수정 
	 * @Method명   : evfoCrtr
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvfoCrtrUpdate.do")
	@ResponseBody
	public View evfoCrtr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoCrtr(request, dataRequest);
		log.debug("evfoCrtr retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 역량삭제
	 * @Method명   : evfoCrtrMngDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subEvfoCrtrMngDelete.do")
	@ResponseBody
	public View evfoCrtrMngDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evfoCrtrMngDelete(request, dataRequest);
		log.debug("evfoCrtrMngDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 관리자평가자관리 조회 
	 * @Method명   : selectMngrApraiMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMngrApraiMngList.do")
	public View selectMngrApraiMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 관리자평가자관리 기본정보 조회
		dataRequest.setResponse("dmMngrApraiMngBassInfo" , dscsnAbilitMngService.selectMngrApraiMngBassInfo(dataRequest));
		
		// 관리자평가자관리 목록 조회
		dataRequest.setResponse("dsMngrApraiMngList", dscsnAbilitMngService.selectMngrApraiMngList(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 동료상담원 평가자관리 목록 조회
	 * @Method명   : selectMngrApraiMngCoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMngrApraiMngCoList.do")
	public View selectMngrApraiMngCoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsEvlGroupTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EVL_GROUP_TYPE_SE_CD", userVo.getUntTaskwk()));  // 평가그룹유형구분코드
		
		// 동료상담원 평가자관리 목록 조회
		dataRequest.setResponse("dsMngrApraiMngCoList", dscsnAbilitMngService.selectMngrApraiMngCoList(dataRequest));
		
		return new JSONDataView();		
	}
	
	/**
	 * 동료상담원 결과 목록조회
	 * @Method명   : selectMngrApraiMngCoResultList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMngrApraiMngCoResultList.do")
	public View selectMngrApraiMngCoResultList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 동료상담원 결과 목록조회
		dataRequest.setResponse("dsMngrApraiMngCoResultList", dscsnAbilitMngService.selectMngrApraiMngCoResultList(dataRequest));
		
		return new JSONDataView();		
	}
	
	/**
	 * 평가자목록 조회
	 * @Method명   : selectApraiList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subApraiList.do")
	public View selectApraiList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가자목록 조회
		dataRequest.setResponse("dsApraiList", dscsnAbilitMngService.selectApraiList(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가자추가
	 * @Method명   : araiInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveAprai.do")
	@ResponseBody
	public View araiInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.araiInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 관리자평가자 삭제
	 * @Method명   : mngrApraiDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subMngrApraiDelete.do")
	@ResponseBody
	public View mngrApraiDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.mngrApraiDelete(request, dataRequest);
		log.debug("mngrApraDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 관리자평가자수정 조회
	 * @Method명   : selectMngrApraiUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMngrApraiUpdateInq.do")
	public View selectMngrApraiUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 관리자평가자수정 조회
		dataRequest.setResponse("dmMngrApraiUpdate" , dscsnAbilitMngService.selectMngrApraiUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 관리자평가자 수정
	 * @Method명   : mngrApraiUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveMngrAprai.do")
	@ResponseBody
	public View mngrApraiUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.mngrApraiUpdate(request, dataRequest);
		log.debug("mngrApraiUpdate retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 본원평가위원평가자관리기본정보 조회
	 * @Method명   : selectEvlMfcmmApraiMngBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlMfcmmApraiMngBassInfo.do")
	public View selectEvlMfcmmApraiMngBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 본원평가위원평가자관리기본정보 조회
		dataRequest.setResponse("dmEvlMfcmmApraiMngBassInfo" , dscsnAbilitMngService.selectEvlMfcmmApraiMngBassInfo(dataRequest));
		
		// 본원평가위원평가자관리기본정보 목록 조회
		dataRequest.setResponse("dsEvlMfcmmApraiMngBassInfoList", dscsnAbilitMngService.selectEvlMfcmmApraiMngBassInfoList(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 평가서관리 조회
	 * @Method명   : selectEvlSeMngInq
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlSeMngInq.do")
	public View selectEvlSeMngInq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가서관리 조회
		dataRequest.setResponse("dsEvlSeMngInq", dscsnAbilitMngService.selectEvlSeMngInq(dataRequest));
		
		return new JSONDataView();
		
	}

	/**
	 * 상담원 - 평가대상 여부 조회
	 * @Method명   : selectApraiIdnty
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : 
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 평가대상 여부 조회
	 */
	@RequestMapping(value = "/subApraiIdntyInq.do")
	public View selectApraiIdntyInq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmReturn", dscsnAbilitMngService.selectApraiIdnty(request));
		
		return new JSONDataView();
	}
	
	/**
	 * 평가서관리구분 조회
	 * @Method명   : selectEvlSeMngSeInq
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlSeMngSeInq.do")
	public View selectEvlSeMngSeInq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가서관리구분 조회
		dataRequest.setResponse("dsEvlSeMngList", dscsnAbilitMngService.selectEvlSeMngSeInq(dataRequest));
		
		// 평가서 작성 대상자 목록 조회
		dataRequest.setResponse("dsEvlWrtList", dscsnAbilitMngService.selectEvlWrtTrprList(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 수행 적절성평가표 기본정보 조회
	 * @Method명   : selectRelevaEvlBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subRelevaEvlBassInfo.do")
	public View selectRelevaEvlBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 수행 적절성평가표 기본정보 조회
		dataRequest.setResponse("dmRelevaEvlBassInfo" , dscsnAbilitMngService.selectRelevaEvlBassInfo(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 수행 적절성평가표 목록 조회
	 * @Method명   : selectRelevaEvlList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subRelevaEvlList.do")
	public View selectRelevaEvlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 수행 적절성평가표 목록 조회
		dataRequest.setResponse("dsRelevaEvlList", dscsnAbilitMngService.selectRelevaEvlList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 평가관리 저장
	 * @Method명   : evlMngSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvlMng.do")
	@ResponseBody
	public View evlMngSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.evlMngSave(request, dataRequest);
		log.debug("evlMngSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 우수사례양식 저장
	 * @Method명   : exclncCaseMmSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveExclncCaseMm.do")
	@ResponseBody
	public View exclncCaseMmSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.exclncCaseMmSave(request, dataRequest);
		log.debug("exclncCaseMmSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 우수사례 제출 자료 삭제
	 * @Method명   : exclncCaseMmDelete
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 : 해당 월의 제출한 자료(우수사례)에 대해 삭제 처리
	 */
	@RequestMapping(value = "/subDeleteExclncCasMm.do")
	public View exclncCaseMmDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dscsnAbilitMngService.exclncCaseMmDelete(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 수퍼비전양식 저장
	 * @Method명   : superVisionMmSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveSuperVisionMm.do")
	@ResponseBody
	public View superVisionMmSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = dscsnAbilitMngService.superVisionMmSave(request, dataRequest);
		log.debug("superVisionMmSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 수퍼비전 제출 자료 삭제
	 * @Method명   : superVisionMmDelete
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 해당 월의 제출한 자료(수퍼비전)에 대해 삭제 처리
	 */
	@RequestMapping(value = "/subDeleteSuperVisionMm.do")
	public View superVisionMmDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dscsnAbilitMngService.superVisionMmDelete(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가관리목록 저장
	 * @Method명   : evlMngListInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveEvlMngList.do")
	@ResponseBody
	public View evlMngListInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.evlMngListInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가자 동료상담원 저장
	 * @Method명   : mngrApraiMngCoListInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveMngrApraiMngCoList.do")
	@ResponseBody
	public View mngrApraiMngCoListInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.mngrApraiMngCoListInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 평가점수관리 목록조회
	 * @Method명   : selectEvlScoreMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 19. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEvlScoreMngList.do")
	public View selectEvlScoreMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 평가점수관리 목록조회
		dataRequest.setResponse("dsEvlScoreMngList", dscsnAbilitMngService.selectEvlScoreMngList(request, dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 소속기관 목록조회
	 * @Method명   : selectOgdpInstList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subOgdpInstList.do")
	public View selectOgdpInstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 소속기관 목록조회
//		dataRequest.setResponse("dsOgdpInstList", dscsnAbilitMngService.selectOgdpInstList(request, dataRequest));
		dataRequest.setResponse("dsOgdpInstList", counsService.selectOrgDeptCombo(request));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 우수사례관리 목록 조회
	 * @Method명   : selectOgdpInstList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subExclncCaseMngList.do")
	public View selectExclncCaseMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 우수사례관리 목록 조회
		dataRequest.setResponse("dsExclncCaseMngList", dscsnAbilitMngService.selectExclncCaseMngList(request, dataRequest));
		
		// 우수사례 양식 조회
		dataRequest.setResponse("dmModeInfo", dscsnAbilitMngService.selectExclncModeInfo());
	
	return new JSONDataView();
	
	}
	
	/**
	 * 수퍼비전관리 목록 조회
	 * @Method명   : selectSuperVisionMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subSuperVisionMngList.do")
	public View selectSuperVisionMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 수퍼비전관리 목록 조회
	dataRequest.setResponse("dsSuperVisionMngList", dscsnAbilitMngService.selectSuperVisionMngList(request, dataRequest));
	
	// 수퍼비전 양식 조회
	dataRequest.setResponse("dmModeInfo", dscsnAbilitMngService.selectSuperVisionModeInfo());
	
	return new JSONDataView();
	
	}
	
	/**
	 * 우수사례관리 상담자 조회
	 * @Method명   : selectExclncCaseConsttList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subExclncCaseConsttList.do")
	public View selectExclncCaseConsttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 우수사례관리 상담자 조회
		dataRequest.setResponse("dsExclncCaseConsttList", dscsnAbilitMngService.selectExclncCaseConsttList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 수퍼비전관리 상담자 조회
	 * @Method명   : selectSuperVisionConsttList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subSuperVisionConsttList.do")
	public View selectSuperVisionConsttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 수퍼비전관리 상담자 조회
		dataRequest.setResponse("dsSuperVisionConsttList", dscsnAbilitMngService.selectSuperVisionConsttList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 우수사례관리 상담자 저장
	 * @Method명   : exclncCaseConsttInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveExclncCaseConstt.do")
	@ResponseBody
	public View exclncCaseConsttInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.exclncCaseConsttInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 수퍼비전관리 상담자 저장
	 * @Method명   : superVisionConsttInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subSaveSuperVisionConstt.do")
	@ResponseBody
	public View superVisionConsttInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		dscsnAbilitMngService.superVisionConsttInsert(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : processEnfoMngExcelUpload
	 * @Method설명 : 평자지관리 엑셀업로드
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 09. 27. 
 	 */	
	@RequestMapping(value = "/processEnfoMngExcelUpload.do")
	public View processEnfoMngExcelUpload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dscsnAbilitMngService.processEnfoMngExcelUpload(request, dataRequest); 
		
		return new JSONDataView();
	}

//-------------------------------------------------------------------------------------------------------------------------------
// 업무지원 & 관리자 - 상담원 역량관리 - 교육관리
//-------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @Method명   : selectEduHstrList
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 업무지원 - 교육이력 및 수료증 출력 목록 조회
	 */
	@RequestMapping(value = "/subEduHstrList.do")
	public View selectEduHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsEduHstrList", dscsnAbilitMngService.selectEduHstrList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : updateEduCtcplNo
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 출력버튼 클릭시 이수증출력번호가 없을 경우 최대값 + 1의 값으로 Update
	 */
	@RequestMapping(value = "/subCtcplNoUpdate.do")
	public View updateEduCtcplNo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dscsnAbilitMngService.updateEduCtcplNo(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 교육관리 조회
	 * @Method명   : selectEduMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 19. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subEduMngList.do")
	public View selectEduMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
	// 교육관리 조회
	dataRequest.setResponse("dsEduMngList", dscsnAbilitMngService.selectEduMngList(dataRequest));
	
	return new JSONDataView();
	
	}

	/**
	 * @Method명   : selectEduCreateDscsnList
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육관리에서 교육 개설 버튼을 통해 들어온 등록페이지에 필요한 조회 List
	 */
	@RequestMapping(value = "/subCreateEduList.do")
	public View selectEduCreateDscsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사이버상담 교육인원 목록 조회
		dataRequest.setResponse("dsCyberNonAtndList", dscsnAbilitMngService.selectCreateCyberDscsnList(dataRequest));
		
		// 모바일상담 교육인원 목록 조회
		dataRequest.setResponse("dsMblaNonAtndList", dscsnAbilitMngService.selectCreateMblaDscsnList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectEduDetailDscsnList
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육관리에서 cell-click을 통해 들어온 상세페이지에 필요한 조회 List
	 */
	@RequestMapping(value = "subDetailEduList.do")
	public View selectEduDetailDscsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 교육 정보
		dataRequest.setResponse("dmEduMngBassInfo", dscsnAbilitMngService.selectEduMngBassInfo(dataRequest));
		
		// 사이버 미참석 목록
		dataRequest.setResponse("dsCyberNonAtndList", dscsnAbilitMngService.selectCyberNonAtndList(dataRequest));
		
		// 사이버 참석 목록
		dataRequest.setResponse("dsCyberAtndList", dscsnAbilitMngService.selectCyberAtndList(dataRequest));
		
		// 모바일 미참석 목록
		dataRequest.setResponse("dsMblaNonAtndList", dscsnAbilitMngService.selectMblaNonAtndList(dataRequest));
		
		// 모바일 참석 목록
		dataRequest.setResponse("dsMblaAtndList", dscsnAbilitMngService.selectMblaAtndList(dataRequest));
		
		return new JSONDataView();
	}

//	/**
//	 * 교육관리 저장
//	 * @Method명   : eduMngSave
//	 * @param request
//	 * @param response
//	 * @param dataRequest
//	 * @return
//	 * @throws Exception
//	 * @작성자     : Lee.Tae.Ho
//	 * @작성일     : 2022. 9. 16. 
//	 * @Method설명 :
//	 */
//	@RequestMapping(value="/subSaveEduMng.do")
//	@ResponseBody
//	public View eduMngSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
//			throws Exception {
//				
//		String sEduEstblSn = ""; // 교육개설일련번호_EDU_ESTBL_SN
//		
//		Map<String, String> retMap  = dscsnAbilitMngService.eduMngSave(request, dataRequest);
//		log.debug("eduMngSave retMap 111 ==>> " + retMap);
//		log.debug("eduMngSave retMap 222 ==>> " + retMap.get("EDU_ESTBL_SN"));
//
//		sEduEstblSn = retMap.get("ESTBL_SN");
//		
//		log.debug("iEduEstblSn retMap ==>> " + sEduEstblSn);
//		
//		// 교육관리목록 저장
//		dscsnAbilitMngService.eduMngSaveListInsert(request, dataRequest, sEduEstblSn);
//				
//		return new JSONDataView();
//	}
	
	/**
	 * @Method명   : insertEduMngSave
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육관리 Insert 
	 */
	@RequestMapping(value="/subSaveEduMng.do")
	@ResponseBody
	public View insertEduMngSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		dscsnAbilitMngService.insertEduMngSave(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : updateEduMng
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 : 교육관리 Update
	 */
	@RequestMapping(value = "/subUpdateEduMng.do")
	public View updateEduMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dscsnAbilitMngService.updateEduMng(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : deleteEduMng
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 : 교육관리 Delete
	 */
	@RequestMapping(value = "/subDeleteEduMng.do")
	public View deleteEduMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dscsnAbilitMngService.deleteEduMng(request, dataRequest);
		
		return new JSONDataView();
	}
	
//	/**
//	 * 교육관리목록 저장
//	 * @Method명   : eduMngSaveList
//	 * @param request
//	 * @param response
//	 * @param dataRequest
//	 * @return
//	 * @throws Exception
//	 * @작성자     : Lee.Tae.Ho
//	 * @작성일     : 2022. 9. 19. 
//	 * @Method설명 :
//	 */
//	@RequestMapping(value="/subSaveEduMngList.do")
//	@ResponseBody
//	public View eduMngSaveList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
//			throws Exception {
//		
//		log.debug("교육관리목록 저장 시작 ==>> ");
//		
//		// 교육관리목록 저장
//		dscsnAbilitMngService.eduMngSaveListInsert(request, dataRequest);
//		
//		return new JSONDataView();
//	}
	
	/**
	 * 사이버상담 교육인원 목록 조회
	 * @Method명   : selectRelevaEvlList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subCyberDscsnList.do")
	public View selectCyberDscsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사이버상담 교육인원 목록 조회
		dataRequest.setResponse("dsCyberNonAtndList", dscsnAbilitMngService.selectCreateCyberDscsnList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 모바일상담 교육인원 목록 조회
	 * @Method명   : selectMblaDscsnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMblaDscsnList.do")
	public View selectMblaDscsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 모바일상담 교육인원 목록 조회
		dataRequest.setResponse("dsMblaNonAtndList", dscsnAbilitMngService.selectCreateMblaDscsnList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 모바일상담 교육인원수정 목록조회
	 * @Method명   : selectMblaDscsnUpdateList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMblaDscsnUpdateList.do")
	public View selectMblaDscsnUpdateList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 모바일상담 교육인원 목록 조회
		dataRequest.setResponse("dsMblaAtndList", dscsnAbilitMngService.selectMblaDscsnUpdateList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 사이버상담 교육인원수정 목록조회
	 * @Method명   : selectCyberDscsnUpdateList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subCyberDscsnUpdateList.do")
	public View selectCyberDscsnUpdateList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사이버상담 교육인원수정 목록조회
		dataRequest.setResponse("dsCyberAtndList", dscsnAbilitMngService.selectCyberDscsnUpdateList(dataRequest));
		
		return new JSONDataView();
	}
	
}
