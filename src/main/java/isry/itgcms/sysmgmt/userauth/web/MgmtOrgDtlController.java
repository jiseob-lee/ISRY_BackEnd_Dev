/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgDtlService;

/**
 * @파일명        : MgmtOrgDtlController.java
 * @프로그램 설명 : 기관 상세 정보 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "MgmtOrgDtl web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class MgmtOrgDtlController extends IsryBaseController {

	@Resource(name = "mgmtOrgDtlService")
	private MgmtOrgDtlService mgmtOrgDtlService;

	@Autowired
	private MgmtFileService mgmtFileService;

	//@ApiOperation(value = "/saveOrgDtl.do", notes = "기관 정보 저장 [공통] 이지섭")
	@RequestMapping(value = "/saveOrgDtl.do")
	public View saveOrgDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		log.debug("############# saveOrgDtl.do ############");
		String strFileStorePath = EgovProperties.getProperty("globals", "isry.globals.wasupload.file.folder");

		List<Map<String, String>> fileInfoList = mgmtFileService.uploadCmnFileSeperate(request, dataRequest, strFileStorePath);
		
//		mgmtOrgDtlService.saveOrgDtl(request, dataRequest, fileInfoList);
		dataRequest.setResponse("dmOrgCode", mgmtOrgDtlService.saveOrgDtl(request, dataRequest, fileInfoList));
				
		// 추가정보 저장 2022.09.20 Hee Sung Yoon
		// 추가정보 tab
		mgmtOrgDtlService.saveAddtngBassInfo(request, dataRequest);
		// 설치및위탁정보 tab
		mgmtOrgDtlService.saveInstlCnsgnInfo(request, dataRequest);
		// 시설정보 tab
		mgmtOrgDtlService.saveFcltyInfo(request, dataRequest);
		// 운영정보 tab
		mgmtOrgDtlService.saveOperInfo(request, dataRequest);
		// 청소년상담전화1388 tab
		mgmtOrgDtlService.saveYngbgs1388(request, dataRequest);
		// 추가정보 저장 끝
		return new JSONDataView();

	}
	
	@RequestMapping(value = "/deleteOrganization.do")
	public View deleteOrganization(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		mgmtOrgDtlService.deleteOrganization(request, dataRequest);

		return new JSONDataView();

	}
	
	/**
	 * 추가기본정보TAP 저장
	 * @Method명   : saveAddtngBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 19. 
	 * @Method설명 : 추가기본정보TAP 저장
	 */
	@RequestMapping(value = "/saveAddtngBassInfo.do")
	public View saveAddtngBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		mgmtOrgDtlService.saveAddtngBassInfo(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 설치및위탁정보TAP 저장
	 * @Method명   : saveInstlCnsgnInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 19. 
	 * @Method설명 : 설치및위탁정보TAP 저장
	 */
	@RequestMapping(value = "/saveInstlCnsgnInfo.do")
	public View saveInstlCnsgnInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		log.debug("설치및위탁정보TAP 저장 시작 :::::::::::::::::::::::::: ");
		
		mgmtOrgDtlService.saveInstlCnsgnInfo(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 운영정보TAP 저장
	 * @Method명   : saveOperInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 19. 
	 * @Method설명 : 운영정보TAP 저장
	 */
	@RequestMapping(value = "/saveOperInfo.do")
	public View saveOperInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		log.debug("운영정보TAP 저장 시작 :::::::::::::::::::::::::: ");
		
		mgmtOrgDtlService.saveOperInfo(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 청소년상담전화1388TAP 저장
	 * @Method명   : saveYngbgs1388
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 19. 
	 * @Method설명 : 청소년상담전화1388TAP 저장
	 */
	@RequestMapping(value = "/saveYngbgs1388.do")
	public View saveYngbgs1388(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		log.debug("청소년상담전화1388TAP 저장 시작 :::::::::::::::::::::::::: ");
		
		mgmtOrgDtlService.saveYngbgs1388(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 시설정보 저장
	 * @Method명   : saveFcltyInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 시설정보 저장
	 */
	@RequestMapping(value = "/saveFcltyInfo.do")
	public View saveFcltyInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		log.debug("시설정보 저장 시작 :::::::::::::::::::::::::: ");
		
		mgmtOrgDtlService.saveFcltyInfo(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 추가정보 조회
	 * @Method명   : saveFcltyInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 추가정보 조회
	 */
	@RequestMapping(value = "/subYngbgsSheltr.do")
	public View selectYngbgsSheltr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		log.debug("추가정보 조회 시작 :::::::::::::::::::::::::: ");
		
		Map<String, Object> addtngBassInfo = mgmtOrgDtlService.selectYngbgsSheltr(dataRequest);
		
		log.debug("추가정보 조회 결과 :::::::::::::::::::::::::: " + (addtngBassInfo == null ? "" : addtngBassInfo.toString()));
		
		dataRequest.setResponse("dmAddtngBassInfo", addtngBassInfo);
		
		return new JSONDataView();
	}
	
	/**
	 * 운영정보 조회
	 * @Method명   : subOperInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 운영정보 조회
	 */
	@RequestMapping(value = "/subOperInfo.do")
	public View selectOperInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		log.debug("운영정보 조회 시작 :::::::::::::::::::::::::: ");
		
		// AKA510 /* 센터현황-운영시간 */
		dataRequest.setResponse("dsOperHour", mgmtOrgDtlService.selectOperHour(dataRequest));
		
		// AKA520 /* 센터현황-분소운영 */
		dataRequest.setResponse("dsBrofaOper", mgmtOrgDtlService.selectBrofaOper(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 청소년상담전화1388 조회
	 * @Method명   : subOperInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 청소년상담전화1388 조회
	 */
	@RequestMapping(value = "/subYngbgs1388.do")
	public View selectYngbgs1388(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		log.debug("청소년상담전화1388 조회 시작 :::::::::::::::::::::::::: ");
		
		Map<String, Object> yngbgs1388 = mgmtOrgDtlService.selectYngbgs1388(dataRequest);
		dataRequest.setResponse("dmYngbgs1388", yngbgs1388);
		
		// AKA530_센터현황-1388전화운영시간
		dataRequest.setResponse("dsOperHour1388", mgmtOrgDtlService.selectOperHour1388(dataRequest));
		
		// AKA540_센터현황-1388전화근무현황
		dataRequest.setResponse("dsTpriRcvr1388", mgmtOrgDtlService.selectTpriRcvr1388(dataRequest));
		
		// AKA630_센터현황-1388전담요원현황
		dataRequest.setResponse("dsEcshgStaff1388", mgmtOrgDtlService.selectEcshgStaff1388(dataRequest));
		
		// AKA550_센터현황-1388운영인력
		dataRequest.setResponse("dsOperHnf1388", mgmtOrgDtlService.selectOperHnf1388(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 시설정보 조회
	 * @Method명   : subFcltyInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 시설정보 조회
	 */
	@RequestMapping(value = "/subFcltyInfo.do")
	public View selectFcltyInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		log.debug("시설정보 조회 시작 :::::::::::::::::::::::::: ");
		
		Map<String, Object> fcltyInfo = mgmtOrgDtlService.selectFcltyInfo(dataRequest); // AKA550
		List<Map<String, String>> yngbgsFclty = mgmtOrgDtlService.selectYngbgsFclty(dataRequest); // AKA560
		
		dataRequest.setResponse("dmFcltyInfo", fcltyInfo);
		
		//AKA560_센터현황-청소년시설
		dataRequest.setResponse("dsYngbgsFclty", yngbgsFclty);
		
		// AKA570_센터현황-사용공간세부
		dataRequest.setResponse("dsUseSpce", mgmtOrgDtlService.selectUseSpce(dataRequest));
		
		// AKA580_센터현황-이동형일시쉼터용차량
		dataRequest.setResponse("dsUseSpceInfo", mgmtOrgDtlService.selectUseSpceInfo(dataRequest));
		
		// AKA590_센터현황-학교밖청소년전용공간
		dataRequest.setResponse("dsOschlYngbgsPrvuseSpace", mgmtOrgDtlService.selectOschlYngbgsPrvuseSpace(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 설치및위탁정보 조회
	 * @Method명   : subFcltyInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 설치및위탁정보 조회
	 */
	@RequestMapping(value = "/subInstlCnsgnInfo.do")
	public View selectInstlCnsgnInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		log.debug("설치및위탁정보 조회 시작 :::::::::::::::::::::::::: ");
		
		Map<String, Object> instlCnsgnInfo = mgmtOrgDtlService.selectInstlCnsgnInfo(dataRequest); 
		dataRequest.setResponse("dmInstlCnsgnInfo", instlCnsgnInfo);
		
		return new JSONDataView();
	}

}
