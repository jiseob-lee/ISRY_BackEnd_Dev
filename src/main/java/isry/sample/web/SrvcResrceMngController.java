/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.web;

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

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.sample.service.SrvcResrceMngService;

/**
 * @파일명        : SrvcResrceMngController.java
 * @프로그램 설명 : 서비스 자원관리
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2022. 4. 29. 
 * @수정자        : You Minsang
 * @수정일        : 2022. 4. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/sample/srvcResrceMng")
public class SrvcResrceMngController {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "msg")
	protected EgovMessageSource msg;

	@Resource(name = "prop")
	protected EgovProperties prop;
	
	@Autowired
	private SrvcResrceMngService srvcResrceMngService;
	
	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : onLoadSrvcResrceMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 29. 
	 * @Method설명 : 서비스 자원관리 화면 공통 조회
	 */
	@RequestMapping("/onLoadSrvcResrceMng.do")
	public View onLoadSrvcResrceMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> mapDate = new HashMap<String, String>();
		
		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다. 
		 */
		
		// 현재 일자 조회
		mapDate.put("strSysDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));		
		dataRequest.setResponse("dmTime", mapDate);
		
		// 서비스 유형 코드
		dataRequest.setResponse("dsCmbServiceType", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_TYPE_SE_CD", userVo.getUntTaskwk()));
				
		// 승인 구분코드
		dataRequest.setResponse("dsCmbAprvSttsCd", mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", userVo.getUntTaskwk()));
		
		// 서비스 자원 분류 코드		
		dataRequest.setResponse("dsCmbServiceLv1", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_LCLAS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsCmbServiceLv2", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_MLSFC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsCmbServiceLv3", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_SCLAS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsCmbServiceLv4", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_DTL_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : listSrvcResrceMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 29. 
	 * @Method설명 : 서비스 자원관리 화면 목록 조회
	 */
	@RequestMapping("/listSrvcResrceMng.do")
	public View listSrvcResrceMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		/*
		 * 쿼리에서 사용할 데이터를 hashMap 파라미터로 전달하여 조회합니다.
		 */
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		
		mapParam.put("SRVC_YEAR", searchParam.getValue("SRVC_YEAR"));
		mapParam.put("PVSN_RESRCE_NM", searchParam.getValue("PVSN_RESRCE_NM"));
		mapParam.put("SRVC_TYPE_SE_CD", searchParam.getValue("SRVC_TYPE_SE_CD"));
		mapParam.put("RSFR_INST_CD", searchParam.getValue("RSFR_INST_CD"));
		mapParam.put("APRV_CD", searchParam.getValue("APRV_CD"));
		mapParam.put("SRVC_RESRCE_LCLAS_SE_CD", searchParam.getValue("SRVC_RESRCE_LCLAS_SE_CD"));
		mapParam.put("SRVC_RESRCE_MLSFC_SE_CD", searchParam.getValue("SRVC_RESRCE_MLSFC_SE_CD"));
		mapParam.put("SRVC_RESRCE_SCLAS_SE_CD", searchParam.getValue("SRVC_RESRCE_SCLAS_SE_CD"));
		mapParam.put("SRVC_RESRCE_DTL_SE_CD", searchParam.getValue("SRVC_RESRCE_DTL_SE_CD"));
				
		// 서비스 유형 코드
		List<Map<String, Object>> dsSrvcResrceMngList = srvcResrceMngService.selectSrvcResrceMngList(mapParam);
		dataRequest.setResponse("dsSrvcResrceMngList", dsSrvcResrceMngList);
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : onLoadSrvcResrceDtlMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 : 자원제공서비스 상세 공통조회
	 */
	@RequestMapping("/onLoadSrvcResrceDtlMng.do")
	public View onLoadSrvcResrceDtlMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, String> mapDate = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		/*
		 * 상세 화면 구성시 필요한 정보를 조회합니다. 
		 */
		
		// 현재 일자 조회
		mapDate.put("strSysDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));		
		dataRequest.setResponse("dmTime", mapDate);
		
		// 서비스 유형 코드
		dataRequest.setResponse("dsCmbServiceType", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_TYPE_SE_CD", userVo.getUntTaskwk()));
		
		// 사용여부 코드
		dataRequest.setResponse("dsCmbUseYn", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		
		// 서비스 자원 분류 코드		
		dataRequest.setResponse("dsCmbServiceLv1", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_LCLAS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsCmbServiceLv2", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_MLSFC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsCmbServiceLv3", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_SCLAS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsCmbServiceLv4", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_DTL_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : listSrvcResrceMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 29. 
	 * @Method설명 : 서비스 자원관리 화면 목록 조회
	 */
	@RequestMapping("/listSrvcResrceDtlMng.do")
	public View listSrvcResrceDtlMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("RESRCE_NO", dtlParam.getValue("RESRCE_NO"));
						
		// 자원제공서비스 상세 조회
		List<Map<String, Object>> dsSrvcResrceMngList = srvcResrceMngService.selectSrvcResrceDtlMngList(mapParam);
		dataRequest.setResponse("dsSrvcResrceMngList", dsSrvcResrceMngList);
		
		String srvcTypeSeCD = dtlParam.getValue("SRVC_TYPE_SE_CD");
		
		// 서비스 유형이 프로그램일 경우 프로그램 목록 조회 
		if(srvcTypeSeCD.equals("04")) {
			// 프로그램 목록 조회
			List<Map<String, Object>> dsProgramList = srvcResrceMngService.selectSrvcResrceDtlProgramList(mapParam);
			dataRequest.setResponse("dsProgramList", dsProgramList);
		}
		
				
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : onLoadSrvcResrceDtlMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 : 자원제공서비스 상세 공통조회
	 */
	@RequestMapping("/saveSrvcResrceMng.do")
	public View saveSrvcResrceMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 목록화면에서 자원정보 삭제, 상세화면에서 자원상세 저장,수정 등 하나의 save로 활용하기 위해 
		// UI화면에서 데이터셋명을 동일하게 설정하여 하나의 서비스로 구현한다.(delete 동작은 미구현)
		
		Map<String, Object> returnParam = srvcResrceMngService.saveSrvcResrceMngList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		
		// 재조회시 자원번호 매핑을 위해 화면에 내려준다
		message.put("RESRCE_NO", returnParam.get("RESRCE_NO"));		

		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();

	}
}
