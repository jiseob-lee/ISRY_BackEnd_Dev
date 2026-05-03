/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stdnt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.drmgs.stdnt.service.YouthLifeRecodeDetailService;

/**
 * @파일명        : YouthLifeRecodeDetailController.java
 * @프로그램 설명 : 생활기록부 상세
 * - 
 * - YouthLifeRecodeDetailController
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 14. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 14.
 * @수정내용      : 생활기록부 상세
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/stdnt")
public class YouthLifeRecodeDetailController extends IsryBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "youthLifeRecodeDetailService")
	private YouthLifeRecodeDetailService youthLifeRecodeDetailService;
	
	//@Resource(name = "drmgsCaseRegService")
	//private DrmgsCaseRegService drmgsCaseRegService;
	
	//@Resource(name = "caseExcnService")
	//private CaseExcnService caseExcnService;
	
	@RequestMapping(value = "/selectYngbsInfo.do")
	public View selectYngbsInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 청소년정보 조회
		Map<String, Object> yngbsInfo = youthLifeRecodeDetailService.selectYngbsInfo(dataRequest);
		dataRequest.setResponse("dmYngbsInfo", yngbsInfo);
		
		 // 담당자 및 사진 조회
		dataRequest.setResponse("dsPicPhoto", youthLifeRecodeDetailService.selectPicPhoto(dataRequest));
		
		// 청소년인적사항 조회
		Map<String, String> yngbsMatter = youthLifeRecodeDetailService.selectYngbsMatter(dataRequest);
		dataRequest.setResponse("dmYngbsMatter", yngbsMatter);
		
		// 출결 상황 조회
		dataRequest.setResponse("dsAtncSittn", youthLifeRecodeDetailService.selectAtncSittn(dataRequest));
		
		// 수상경력 조회
		dataRequest.setResponse("dsArprCareer", youthLifeRecodeDetailService.selectArprCareer(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 청소년정보 저장
	 * @Method명   : yngbsInfoSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/yngbsInfoSave.do")
	@ResponseBody
	public View yngbsInfoSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = youthLifeRecodeDetailService.yngbsInfoSave(request, dataRequest);
		
		LOGGER.debug("yngbsInfoSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 생활기록부 상세_상황관리정보 조회
	 * @Method명   : selectSittnMngInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 생활기록부 상세_상황관리정보 조회
	 */
	@RequestMapping(value = "/selectSittnMngInfo.do")
	public View selectSittnMngInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  상황관리정보 조회
		 *  1. 자격증 및 인증 취득상황
		 *  2. 창의적 체험 활동 상황
		 *  3. 봉사활동상황
		 *  4. 학업 노력 상황
		 */
		List<Map<String, Object>> certiList    = youthLifeRecodeDetailService.selectCertiList(dataRequest);
		List<Map<String, Object>> creativeList = youthLifeRecodeDetailService.selectCreativeList(dataRequest);
		List<Map<String, Object>> schulwList   = youthLifeRecodeDetailService.selectSchulwList(dataRequest);
		List<Map<String, Object>> svcb = youthLifeRecodeDetailService.selectSvcb(dataRequest);

		dataRequest.setResponse("dsCertiList"   , certiList);
		dataRequest.setResponse("dsCreativeList", creativeList);
		dataRequest.setResponse("dsSchulwList"  , schulwList);
		dataRequest.setResponse("dsSvcb", svcb);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectEtcInfo.do")
	public View selectEtcInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 독서활동상황 조회
		dataRequest.setResponse("dsRead", youthLifeRecodeDetailService.selectRead(dataRequest)); 
		// 봉사활동상황 조회
		//dataRequest.setResponse("dsSvcb", youthLifeRecodeDetailService.selectSvcb(dataRequest)); 
		// 행동특성 및 종합의견 조회
		dataRequest.setResponse("dsOpnn", youthLifeRecodeDetailService.selectOpnn(dataRequest)); 		
		return new JSONDataView();
	} 
	
	/**
	 * 생활기록부 상세_자격증 및 인증 취득상황 정보 저장
	 * @Method명   : saveCertiInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 생활기록부 상세_자격증 및 인증 취득상황 정보 저장
	 */
	@RequestMapping(value = "/saveCertiInfo.do")
	public View saveCertiInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  자격증 및 인증 취득상황 정보 저장(등록/수정/삭제)
		 */		
		youthLifeRecodeDetailService.saveCertiInfo(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 생활기록부 상세_창의적 체험활동 상황 정보 저장
	 * @Method명   : saveCreativeInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 : 생활기록부 상세_창의적 체험활동 상황 정보 저장
	 */
	@RequestMapping(value = "/saveCreativeInfo.do")
	public View saveCreativeInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  창의적 체험활동 상황 정보 저장(등록/수정/삭제)
		 */		
		youthLifeRecodeDetailService.saveCreativeInfo(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	 * 생활기록부 상세_학업 노력 상황 정보 저장
	 * @Method명   : saveSchulwInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 : 생활기록부 상세_학업 노력 상황 정보 저장
	 */
	@RequestMapping(value = "/saveSchulwInfo.do")
	public View saveSchulwInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  학업 노력 상황 정보 저장(등록/수정/삭제)
		 */		
		youthLifeRecodeDetailService.saveSchulwInfo(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectArprCareerUpdate.do")
	public View selectArprCareerUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 수상경력수정 조회
		dataRequest.setResponse("dmArprCareer"	  , youthLifeRecodeDetailService.selectArprCareerUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 수상경력 저장
	 * @Method명   : arprCareerSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/arprCareerSave.do")
	@ResponseBody
	public View arprCareerSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = youthLifeRecodeDetailService.arprCareerSave(request, dataRequest);
		
		LOGGER.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 수상경력 삭제
	 * @Method명   : arprCareerSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/deleteArprCareer.do")
	@ResponseBody
	public View deleteArprCareer(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = youthLifeRecodeDetailService.deleteArprCareer(request, dataRequest);
		
		LOGGER.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectAtncSittnUpdate.do")
	public View selectAtncSittnUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 출결 상황 수정 조회
		dataRequest.setResponse("dmAtncSittn"	  , youthLifeRecodeDetailService.selectAtncSittnUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 출결 상황 저장
	 * @Method명   : atncSittnSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/atncSittnSave.do")
	@ResponseBody
	public View atncSittnSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = youthLifeRecodeDetailService.atncSittnSave(request, dataRequest);
		
		LOGGER.debug("atncSittnSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/saveReadInfo.do")
	public View saveReadInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  독서 활동 상황 정보 저장(등록/수정/삭제)
		 */		
		youthLifeRecodeDetailService.saveReadInfo(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	 * 출결 상황 삭제
	 * @Method명   : deleteAtncSittn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/deleteAtncSittn.do")
	@ResponseBody
	public View deleteAtncSittn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = youthLifeRecodeDetailService.deleteAtncSittn(request, dataRequest);
		
		LOGGER.debug("arprCareerSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 종사자 저장
	 * @Method명   : enfsnSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/enfsnSave.do")
	@ResponseBody
	public View enfsnSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = youthLifeRecodeDetailService.enfsnSave(request, dataRequest);
		
		LOGGER.debug("enfsnSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveSvcbInfo.do")
	public View saveSvcbInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  봉사 활동 상황 정보 저장(등록/수정/삭제)
		 */		
		youthLifeRecodeDetailService.saveSvcbInfo(request, dataRequest);
		
		return new JSONDataView();
	} 
 
	@RequestMapping(value = "/saveOpnnInfo.do")
	public View saveOpnnInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 *  봉사 활동 상황 정보 저장(등록/수정/삭제)
		 */		
		youthLifeRecodeDetailService.saveOpnnInfo(request, dataRequest);
		
		return new JSONDataView();
	} 
	
	/**
	 * 지원서비스 조회
	 * @Method명   : selectSprtSrvc
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectSprtSrvc.do")
	public View selectSprtSrvc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 지원서비스 조회
		dataRequest.setResponse("dsList", youthLifeRecodeDetailService.selectSprtSrvcList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 담당자 및 사진 저장
	 * @Method명   : subPicPhotoSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 20. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subPicPhotoSave.do")
	@ResponseBody
	public View subPicPhotoSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		
		Map<String, String> retMap  = youthLifeRecodeDetailService.subPicPhotoSave(request, dataRequest);
		LOGGER.debug("retMap ==>> " + retMap);
		
		dataRequest.setResponse("dsPicPhoto", youthLifeRecodeDetailService.subPicPhotoPicSave(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectPicPhoto.do")
	public View selectPicPhotoNmNow(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
//		// 사진파일명(현재) 조회		
//		Map<String, String> picPhotoNmNow = youthLifeRecodeDetailService.selectPicPhotoNmNow(dataRequest);
//		dataRequest.setResponse("dmPicPhoto", picPhotoNmNow);
		
		// 사진파일명(이전) 조회
		Map<String, String> picPhotoNmBf = youthLifeRecodeDetailService.selectPicPhotoNmBf(dataRequest);
		dataRequest.setResponse("dmPicPhoto", picPhotoNmBf);
		
		return new JSONDataView();
		
	}
	
	/**
	 * 출결상황 수정조회
	 * @Method명   : selectAtncSittnModify
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 09. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/atncSittnModify.do")
	public View selectAtncSittnModify(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		 // 출결상황 수정조회
		dataRequest.setResponse("dsPrdList", youthLifeRecodeDetailService.selectAtncSittnModify(dataRequest));
		
		return new JSONDataView();
	}
	
	
}
