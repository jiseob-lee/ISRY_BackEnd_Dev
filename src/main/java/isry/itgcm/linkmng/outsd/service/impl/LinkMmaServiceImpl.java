/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springsource.loaded.Log;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.HttpWebUtil;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.uneart.mapper.TrprInqMapper;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcm.linkmng.outsd.mapper.LinkMmaRcptJobMapper;
import isry.itgcm.linkmng.outsd.service.LinkMmaService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.StringUtil;
/**
 * @파일명        : LinkTrprRqstServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Choi.Doo.Il
 * @작성일        : 2022. 10. 4. 
 * @수정자        : Choi.Doo.Il
 * @수정일        : 2022. 10. 4.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("linkMmaService")
public class LinkMmaServiceImpl extends EgovAbstractServiceImpl implements LinkMmaService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	private String strWasFileBasePath = EgovProperties.getProperty("globals", "isry.globals.wasupload.file.folder");	
	
	@Resource(name = "linkMmaRcptJobMapper")
	private LinkMmaRcptJobMapper linkMmaRcptJobMapper;
	
	@Resource(name = "trprInqService")
	private TrprInqService trprInqService;
	
	@Resource(name = "trprInqMapper")
	private TrprInqMapper trprInqMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper; 
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name="userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	//병무청 연계접수목록 조회
	public List<Map<String, Object>> selectLinkMmaList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String userId = "";
		Integer authMenuNo = 128;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		
		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		
		return linkMmaRcptJobMapper.selectLinkMmaList(paramMap2);
	}
	
	/**
	 * @Method명   : saveLinkMma
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 10. 04. 
	 * @Method설명 : 병무청 의뢰접수처리 등록
	 */
	@Override
	public Map<String, String> saveLinkMma(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dsDetail");
			
		List<Map<String, String>> dsDetail = searchParam.getAllRowList();
		if(dsDetail.size() > 0) {
			Map<String, String> map = dsDetail.get(0);
			LOGGER.debug("대상자맵등록::::::" + map.toString());
			
			//연계상태 확인
			String sSrvcRqstAplyNo = map.get("SRVC_RQST_APLY_NO");
			String sEsbStatus = map.get("ESB_STATUS");
			if(sSrvcRqstAplyNo != null && !"".equals(sSrvcRqstAplyNo) && !"null".equals(sSrvcRqstAplyNo)) {
				
				String stnEsb = linkMmaRcptJobMapper.selectEsbStatus(map);
				if("S".equals(stnEsb) || "Y".equals(stnEsb)) {
					throw new AppWorksException("해당 의뢰신청정보는 수정 할 수 없습니다.", Alert.ERROR);
				}
			}
			
			String sRcptSeCd = map.get("RCPT_SE_CD");//접수구분코드(21=승인, 30=반려)	
			LOGGER.debug("대상자등록::::::" + sRcptSeCd);
			if(sRcptSeCd.equals("21")) {
				
				//주민등록번호 유무 체크
				String sTrprRrno = map.get("TRPR_RRNO");
				if(sTrprRrno != null && !"".equals(sTrprRrno) && !"null".equals(sTrprRrno)) {
					map.put("RRNO_ENCPT", sTrprRrno);
					int cnt = linkMmaRcptJobMapper.selectTrprInfoNo(map);
					if(cnt > 0) {
						throw new AppWorksException("해당 단위업무에 이미 대상자로 등록되어 있습니다. 반려 처리 바랍니다.", Alert.ERROR);
					}
				}
					
				// 대상자정보번호(TR) 채번
				String sTrprInfoNo     	= "";	// 대상자정보번호
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();
				seqMap.put("USER_ID",       sUserId); // 
				seqMap.put("RENU_NO_SE_CD", "TR");					// 대상자정보번호 채번코드
				seqMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sTrprInfoNo = String.valueOf(valMap.get("RENU_NO"));	// 대상자정보번호(TR) 발번
				LOGGER.debug("대상자정보번호(TR) 발번	 ::::::::"          + sTrprInfoNo);
				
				
				Map<String, Object> infoMap = new HashMap<String, Object>();
				
				String sTrptName   = map.get("TRPR_NM");
				String sTrptBrth   = map.get("BRDT");
				String sSxdc       = map.get("SXDC_SE");
				String sRrno       = map.get("TRPR_RRNO");
				String sMblTel     = map.get("TRPR_MBL_TELNO");
				String sEml        = "";
				String sMsngr      = "";
				
				infoMap.put("TRPR_NM", sTrptName);
				infoMap.put("TRPR_BRTH_YMD", sTrptBrth);
				infoMap.put("SXDC_SE_CD", "M");
				infoMap.put("RRNO", sRrno);
				infoMap.put("MBL_TELNO", sMblTel.replace("-", ""));
				infoMap.put("EML_ADDR", sEml);
				infoMap.put("MSNGR_ID", sMsngr);
				
				//대상자등록 
				Map<String, String> trprMap = new HashMap<>();
				trprMap = searchParam.getSingleValueMap();
				
				 /* 2023-01-17 주민번호가 있는경우 개인식별번호 채번*/
				if(! "".equals(sRrno) || ! "null".equals(sRrno) && sRrno.length() == 13) {
					
					// 개인식별번호 채번
					String personalInfoId = trprInqService.setPersonal(request, infoMap);
					trprMap.put("INDV_IDNTFC_NO", personalInfoId);
				}				
				
				String sUnt = map.get("UNT_TASKWK_SE_CD");
				String sTrptNm = map.get("TRPR_NM");
				String sBrdt = map.get("BRDT");
				
				LOGGER.debug("sUserId : " + sUserId);
				LOGGER.debug("담당자번호는 : " + map.get("PRCS_PIC_NO"));
				trprMap.put("TRPR_INFO_NO"			  , sTrprInfoNo);     						// 대상자정보
				trprMap.put("RCPT_INST_NO"			  , map.get("RQST_PRCS_SRVC_INST_NO"));     // 접수기관번호
				trprMap.put("RCPT_PIC_NO"			  , map.get("PRCS_PIC_NO"));  				// 접수담당자번호
				trprMap.put("UNT_TASKWK_SE_CD"		  , sUnt);    								// 단위업무
				trprMap.put("TRPR_NM"			      , sTrptNm);    							// 대상자명암호화
				trprMap.put("TRPR_BRTH_YMD"			  , sBrdt);      							// 대상자생년월일
				trprMap.put("SXDC_SE_CD"			  , "M");           						// 성별구분코드(병역의무자)
				trprMap.put("SESS_USER_ID" 			  , sUserId);          						// 최초등록자아이디
				trprMap.put("CASE_TRPR_TYPE_SE_CD"    ,  "03");
				trprMap.put("CASE_MNG_SE_CD"	      , "02");
				trprMap.put("LINK_ADDTNG_DATAA_VALUE1", map.get("SRVC_RQST_APLY_NO"));
//				trprMap.put("LINK_ADDTNG_DATAA_VALUE2", map.get("PRCS_PIC_NO"));
//				trprMap.put("LINK_ADDTNG_DATAA_VALUE3", map.get("RQST_PRCS_SRVC_INST_NO"));
				trprMap.put("RCPT_RQST_COURS_SE_CD"	  , "02100101");  // 접수의뢰경로구분코두
				
				/* 문제상태및원인 : SEB130 */
				trprMap.put("LINK_ADDTNG_DATAA_VALUE2"		  , map.get("PROBM_STTS_LCLAS_SE_CD")); 		/* 문제상태 대분류 */
				trprMap.put("LINK_ADDTNG_DATAA_VALUE3"		  , map.get("PROBM_STTS_MLSFC_SE_CD")); 		/* 문제상태 중분류 */
				trprMap.put("LINK_ADDTNG_DATAA_VALUE4"		  , map.get("PROBM_STTS_SCLAS_SE_CD"));			/* 문제상태 소분류 */
				trprMap.put("LINK_ADDTNG_DATAA_VALUE5" 		  , map.get("PROBM_CAS_LCLAS_SE_CD")); 			/* 문제원인 대분류 */
				trprMap.put("LINK_ADDTNG_DATAA_VALUE6" 		  , map.get("PROBM_CAS_SCLAS_SE_CD")); 			/* 문제원인 소분류 */
				trprMap.put("LINK_ADDTNG_DATAA_VALUE7"        , map.get("PROBM_CAS_ETC_CN")); 				/* 문제원인 기타내용 */
				/* 청소년상태구분 : SEB100 */ 
				trprMap.put("LINK_ADDTNG_DATAA_VALUE8"		  , map.get("YNGBGS_STTS_LCLAS_SE_CD"));		/* 청소년상태구분 */		
				/* 연계추가데이터값*/
				trprMap.put("LINK_ADDTNG_DATAA_VALUE9"		  , null);			/* 연계추가데이터값9*/				
				trprMap.put("LINK_ADDTNG_DATAA_VALUE10"		  , null);			/* 연계추가데이터값10*/				
				
				//대상자등록 공통맵퍼 
//				trprInfoMapper.insertTrprInfo(trprMap);
				trprInqMapper.insertTrprInqDetail(trprMap);
			}else {
				Map<String, String> saveMap = new HashMap<>();

				saveMap.put("CASE_MNG_SE_CD"	        , "01"); 	/* 사례관리구분코드 CASE_MNG_SE_CD */			 
				saveMap.put("CASE_TRPR_NOAP_CS_SE_CD"	, "99"); 	/* 사례대상자미신청사유구분코드 CASE_TRPR_NOAP_CS_SE_CD */			 
				saveMap.put("CASE_TRPR_UNSL_CS_CN"		, map.get("RJCT_CS_CN"));  /* 반려사유내용 CASE_TRPR_UNSL_CS_CN */
				
				
				// 수정된부분
//				Map<String, String > rsltMap = linkMohwSrvcRqstMapper.selectTrprInfoNo(map.get("SRVC_RQST_APLY_NO"));
				Map<String, String > rsltMap = linkMmaRcptJobMapper.selectSeqTrprInfoNo(map.get("SRVC_RQST_APLY_NO"));
				saveMap.put("TRPR_INFO_NO"  		    , rsltMap.get("TRPR_INFO_NO"));				/* 대상자번호 */
				saveMap.put("TRPR_NM_ENCPT"  		    , rsltMap.get("TRPR_NM_ENCPT"));				/* 대상자명암호화 TRPR_NM_ENCPT */
				saveMap.put("TRPR_BRTH_YMD"  		    , rsltMap.get("TRPR_BRTH_YMD"));				/* 대상자출생일자 TRPR_BRTH_YMD */
				saveMap.put("SXDC_SE_CD"  		    	, rsltMap.get("SXDC_SE_CD"));				/* 성별구분코드 SXDC_SE_CD*/
				saveMap.put("LINK_ADDTNG_DATAA_VALUE2"  , map.get("PRCS_PIC_NO"));
				saveMap.put("SESS_USER_ID"	            , sUserId); 										/* 최초수정자아이디 LAST_MDFR_ID*/
				
				trprInqMapper.updateTrprInqDetail(saveMap);
			}
			
			linkMmaRcptJobMapper.updateLinkMma(map);
			linkMmaRcptJobMapper.updateEsbStatus(map);
		}
		return null;
	}
	
	/**
	 * @Method명   : selectMmaRqstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 27. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMmaRqstList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 병역의무자 상담의뢰 목록이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("LinkMmaServiceImpl.selectMmaRqstList.paramGroup=[" + paramGroup + "]");
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/

		return linkMmaRcptJobMapper.selectMmaRqstList(paramMap);	
	}	

	/**
	 * @Method명   : selectMmaRqstInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMmaRqstInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 병역의무자 상담의뢰 목록이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("LinkMmaServiceImpl.selectMmaRqstInfo.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		retList = linkMmaRcptJobMapper.selectMmaRqstInfo(paramMap);

		return retList;
	}
	
	/**
	 * @Method명   : selectMmaRqstInfoResult
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 19. 
	 * @Method설명 : 
	 */
	@Override
	public List<Map<String, Object>> selectMmaRqstInfoResult(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 병역의무자 상담의뢰 목록이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("LinkMmaServiceImpl.selectMmaRqstInfoResult.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		retList = linkMmaRcptJobMapper.selectMmaRqstInfoResult(paramMap);	
		
		return retList;
	}	

	/**
	 * @Method명   : processMmaRqstResult
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 5. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> processMmaRqstResult(HttpServletRequest request, DataRequest dataRequest)	throws Exception {
			
		int iUdtCnt  = 0; /* 수정*/
		int iIntCnt  = 0; /* 저장*/
		int iregCnt  = 0; /* 등록여부*/
		int irrNoCnt = 0; /* 주민등록번호없는대상자*/
		
		final String ESB_IF_ID  = "INFIF_IR_MMA_FR_01";		/* 13. 연계인터페이스ID ESB_IF_ID*/
		final String ESB_STATUS = "N";						/* 20. 연계상태 ESB_STATUS default*/
//		final String SND_CD     = "MOG";  					/* 14. 송신기관코드ID SND_CD*/
//		final String RCV_CD     = "SSI";  					/* 15. 수신기관코드ID RCV_CD*/

		ParameterGroup paramCAA130 = dataRequest.getParameterGroup("dsCAA130");
		ParameterGroup paramCAA100 = dataRequest.getParameterGroup("dsCAA100");

		LOGGER.debug("========== 병무청 서비스의뢰 접수 START ==========");
		LOGGER.debug("processMmaRqstResult.paramCAA130=[" + paramCAA130 + "]");
		LOGGER.debug("processMmaRqstResult.paramCAA100=[" + paramCAA100 + "]");

		if (paramCAA130 == null || paramCAA100 == null) {
			throw new AppWorksException("서비스의뢰 접수를 처리할 정보가 없습니다.");
		}
		
		// 세션정보
		String sUserId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}		
		
		List<Map<String, String>> dsCAA130List  = paramCAA130.getAllRowList(); // 서비스의뢰접수
		List<Map<String, String>> dsCAA100List  = paramCAA100.getAllRowList(); // 서비스의뢰접수결과 저장data
		
		String sSrvcRqstAplyNo  = String.valueOf(dsCAA100List.get(0).get("SRVC_RQST_APLY_NO")); 	/* 서비스의뢰신청번호*/
		String sEsbSeq 	    	= String.valueOf(dsCAA100List.get(0).get("ESB_SEQ"));      			/* 연계시퀀스 */
		String sEsbStatus 		= String.valueOf(dsCAA100List.get(0).get("ESB_STATUS"));      		/* 연계상태 */
		
		LOGGER.debug("========== processMmaRqstResult.SRVC_RQST_APLY_NO[" + sSrvcRqstAplyNo + "]==========");
		LOGGER.debug("========== processMmaRqstResult.ESB_SEQ          [" + sEsbSeq + "        ]==========");
		LOGGER.debug("========== processMmaRqstResult.ESB_STATUS       [" + sEsbStatus + "     ]==========");
		if(sSrvcRqstAplyNo != null && !"".equals(sSrvcRqstAplyNo) && !"null".equals(sSrvcRqstAplyNo)) {
			if("S".equals(sEsbStatus)) {
				throw new AppWorksException("연계접수가 완료된 대상자입니다.", Alert.ERROR);
			}
		}			
		
		// 키값 저장
		Map<String, String> saveMap  = new HashMap<>();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("SRVC_RQST_APLY_NO", sSrvcRqstAplyNo);  		/* 서비스의뢰신청번호*/
		paramMap.put("ESB_SEQ"     , sEsbSeq); 						/* 연계시퀀스 */		
		Map<String, Object> retMap   = new HashMap<>();				/* 화면 재조회용 returnMap*/
		retMap.put("SRVC_RQST_APLY_NO"  , dsCAA130List.get(0).get("SRVC_RQST_APLY_NO"));
		retMap.put("ESB_SEQ"  	        , dsCAA130List.get(0).get("ESB_SEQ"));
		
		/* 대상자테이블 insert column*/
		String sTrprRrno		= (paramCAA130.getValue("TRPR_RRNO") 		   == "" ? "" : paramCAA130.getValue("TRPR_RRNO"));				/* 주민등록번호*/
		String sTrprBrthYmd     = (paramCAA100.getValue("TRPR_BRTH_YMD")       == "" ? "" : paramCAA100.getValue("TRPR_BRTH_YMD")); 		/* 대상자생년월일*/			
		String sTrprSxdcSeCd    = (paramCAA100.getValue("SXDC_SE_CD")          == "" ? "" : paramCAA100.getValue("SXDC_SE_CD")); 		    /* 대상자성별*/			
		String sTrprnm 		    = (paramCAA130.getValue("TRPR_NM")             == "" ? "" : paramCAA130.getValue("TRPR_NM"));				/* 대상자명*/
		String sTrprTelno		= (paramCAA130.getValue("TRPR_TELNO") 		   == "" ? "" : paramCAA130.getValue("TRPR_TELNO"));		    /* 전화번호*/
		String sTrprMblTelno	= (paramCAA130.getValue("TRPR_MBL_TELNO") 	   == "" ? "" : paramCAA130.getValue("TRPR_MBL_TELNO"));		/* 휴대전화번호*/
		String sTrprRoadRdnmZip	= (paramCAA130.getValue("TRPR_ROAD_NM_ZIP")    == "" ? "" : paramCAA130.getValue("TRPR_ROAD_NM_ZIP"));		/* 우편번호*/
		String sTrprRdnmadr	 	= (paramCAA130.getValue("TRPR_ROAD_NM_ADDR")   == "" ? "" : paramCAA130.getValue("TRPR_ROAD_NM_ADDR"));		/* 우편주소*/
		String sTrprRdnmDtladr  = (paramCAA130.getValue("TRPR_ROAD_NM_DADDR")  == "" ? "" : paramCAA130.getValue("TRPR_ROAD_NM_DADDR"));	/* 상세주소*/
		String sUntTaskwkSeCd   = (paramCAA130.getValue("UNT_TASKWK_SE_CD")    == "" ? "" : paramCAA130.getValue("UNT_TASKWK_SE_CD"));		/* 단위업무구분코드*/
//		String sTrprRrnoENncpt  = (paramCAA130.getValue("TRPR_RRNO_ENCPT")     == "" ? "" : paramCAA130.getValue("TRPR_RRNO_ENCPT")); 		/* 대상자주민등록번호암호화*/			
		
		// 저장, 수정, 삭제 시작
		LOGGER.debug(":::::::::: 서비스의뢰접수결과 저장, 수정, 삭제 시작 ::::::::::");
		Iterator<ParameterRow> insertedRows = paramCAA100.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = paramCAA100.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = paramCAA100.getDeletedRows();		
		
		LOGGER.debug("==========paramCAA100.getRowState(0)=[" + paramCAA100.getRowState(0) + "]");
		
		while (insertedRows.hasNext()) {
			String sts = "I";
			Map<String, String> mapIns 	      = insertedRows.next().toMap();
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			// 접수구분코드 ( 승인 : 21, 반려 : 30 )
			String sRcptSeCd = mapIns.get("RCPT_SE_CD");
			
			LOGGER.debug("============접수승인구분코드.sRcptSeCd=[" + sRcptSeCd + "]==========");
			
			if (sRcptSeCd.isEmpty()) {
				throw new AppWorksException("접수승인구분코드가 입력되지 않았습니다.\n확인 후 다시 저장 바랍니다.");
			}
			
			mapIns.put("ESB_IF_ID" , ESB_IF_ID);				/*연계인터페이스ID ESB_IF_ID */
			mapIns.put("SND_CD"    , SND_CD);					/* 송신기관코드ID SND_CD */
			mapIns.put("RCV_CD"    , RCV_CD);					/* 수신기관코드ID RCV_CD */			
			mapIns.put("ESB_STATUS", ESB_STATUS);				/* 연계상태 ESB_STATUS */
			
			/*
			 * 접수 -> 반려
			 * 반려 -> 접수
			 * 
			 * 대상자 등록여부를 확인 후에 대상자가 등록되어있지 않은경우 대상자를 등록한다. 접수 ( 사례신청대기 2, 반려시 미신청 1 )
			 */
			
			// 주민등록번호 유무 체크 후 주민번호 없는경우 반려처리
			if(sTrprRrno != null && !"".equals(sTrprRrno) && !"null".equals(sTrprRrno)) {
				mapIns.put("RRNO_ENCPT"     , sTrprRrno);
				mapIns.put("TRPR_RRNO_ENCPT", sTrprRrno);
				
				iregCnt = linkMmaRcptJobMapper.selectTrprInfoNo(mapIns); /* 단위업무에 대상자등록여부 확인*/
				
				LOGGER.debug("==========주민번호.단위업무에 대상자등록여부 확인.RETURN=[" + iregCnt + "]==========");
				/* 주민번호로 생년월일 get*/	
				/* 주민번호로 성별 get*/	
				
				 String sIndbIdntfcNo = "";
				 Map<String, Object> infoMap = new HashMap<>();
				 
				 /* SCA300 개인정보 등록*/
				 infoMap.put("TRPR_NM"		  , sTrprnm);
				 infoMap.put("TRPR_BRTH_YMD"  , sTrprBrthYmd);  					
				 infoMap.put("SXDC_SE_CD"	  , sTrprSxdcSeCd); 			
				 infoMap.put("RRNO"			  , sTrprRrno) ;		
				 infoMap.put("WRD_TELNO"	  , sTrprTelno); 
				 infoMap.put("MBL_TELNO"	  , sTrprMblTelno); 
				 infoMap.put("ZIP"			  , sTrprRoadRdnmZip); 
				 infoMap.put("PST_ADDR"		  , sTrprRdnmadr); 
				 infoMap.put("DADDR"		  , sTrprRdnmDtladr); 
				 
				 sIndbIdntfcNo = trprInqService.setPersonal(request, infoMap);				
				 LOGGER.debug("==========주민번호.개인식별번호.RETURN=[" + sIndbIdntfcNo + "]==========");
				 
				 saveMap.put("FAM_SHAPE_SE_CD" 		   , "99"); 					/* 가족구성형태 FAM_SHAPE_SE_CD */
				 saveMap.put("FAM_SHAPE_ETC_CN"   	   , "병무청연계대상자"); 	    /* 가족구성형태 기타내용 FAM_SHAPE_ETC_CN */
				 saveMap.put("RESIDE_SHAPE_SE_CD"      , "99"); 					/* 주거형태 RESIDE_SHAPE_SE_CD */
				 saveMap.put("RESIDE_SHAPE_ETC_CN"     , "병무청연계대상자"); 		/* 주거형태 기타내용 RESIDE_SHAPE_ETC_CN */
				 saveMap.put("PBLAST_SE_CD" 		   , "05"); 					/* 사회보장구분 PBLAST_SE_CD */
				 saveMap.put("RCPT_RQST_COURS_SE_CD"   , "02100101"); 				/* 접수의뢰경로구분코드   ( 병무청 및 소관 기관·시설 ) */
				 saveMap.put("CASE_TRPR_TYPE_SE_CD"    , "03");						/* 사례대상자유형구분코드 (병무청연계대상자) */		 
				 
				 
				 /* 개인정보 생성후 개인식번호 발급*/
				 /* 대상자생성 및 개인식별번호 연결*/
				 
				 String sTrprInfoNo = selectRenuNo(sUserId, "TR"); // 대상자번호(TR) 발번
				 
				 // 연락처 정보가 없는경우 연락처미입력 사유 입력 (대상자 등록시)
				 /* 개인식별정보 미입력사유내용 INDV_IDNTFC_INFO_UNIPT_CS_CN*/
				 if (sTrprTelno == null || "".equals(sTrprTelno) && sTrprMblTelno == null || "".equals(sTrprTelno)) {
				 	saveMap.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "병무청연계대상자로 연락처정보 미입력");
				 }else if(sTrprTelno != null || ! "".equals(sTrprTelno) && sTrprMblTelno != null || ! "".equals(sTrprMblTelno)) {
				 	saveMap.put("TRPR_TELNO"          , sTrprTelno);
				 	saveMap.put("MBL_TELNO"           , sTrprMblTelno);
				 }					
				 
				  /* SEA200 대상자등록*/
				 saveMap.put("TRPR_INFO_NO"			    , sTrprInfoNo);
				 saveMap.put("INDV_IDNTFC_NO"           , sIndbIdntfcNo);
				 saveMap.put("RCPT_INST_NO"			    , mapIns.get("RQST_PRCS_SRVC_INST_NO"));         // 접수기관번호
				 saveMap.put("RCPT_PIC_NO"			    , mapIns.get("PRCS_PIC_NO"));  			         // 접수담당자번호
				 saveMap.put("UNT_TASKWK_SE_CD"		    , sUntTaskwkSeCd);    					         // 단위업무
				 saveMap.put("TRPR_NM"		            , sTrprnm);    		   	     // 대상자명암호화
				 saveMap.put("TRPR_BRTH_YMD"		    , sTrprBrthYmd);      					         // 대상자생년월일
				 saveMap.put("SXDC_SE_CD"			    , sTrprSxdcSeCd);           			   	     // 성별구분코드(병역의무자)
				 saveMap.put("SESS_USER_ID" 		    , sUserId);          					         // 최초등록자아이디
				 saveMap.put("ZIP" 		      		  	, sTrprRoadRdnmZip);          			         // 우편번호
				 saveMap.put("PST_ADDR" 		      	, sTrprRdnmadr);          				         // 도로명우편주소
				 saveMap.put("DADDR" 		      	  	, sTrprRdnmDtladr);          			         // 도로명우편상세주소
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE1"	, dsCAA130List.get(0).get("SRVC_RQST_APLY_NO")); // CAA100 KEY값 저장	
					
				 /* 사례담당자 : SEB150 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE9"		  , (mapIns.get("PRCS_PIC_NO") == null ? String.valueOf(loginVO.getEnfsnNo()) : mapIns.get("PRCS_PIC_NO")) );			      /* 사례담당자번호*/	
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE10"	  , (mapIns.get("RQST_PRCS_SRVC_INST_NO") == null ? String.valueOf(mapIns.get("RQST_PRCS_SRVC_INST_NO")): mapIns.get("RQST_PRCS_SRVC_INST_NO")));	  /* 담당자기관번호*/ 
				
				if("21".equals(sRcptSeCd) || "21" == sRcptSeCd) {
					LOGGER.debug("============접수승인구분코드.승인=[" + sRcptSeCd + "]==========");	
					
					LOGGER.debug("담당자번호는 : " + mapIns.get("PRCS_PIC_NO"));
   					// 대상자정보
					saveMap.put("CASE_MNG_SE_CD"	              , "02");								 // 사례관리구분코드 ( 01 : 사례대상자미선정 , 02 : 사례대상자신청(대기상태) )		
					
					/* 문제상태및원인 : SEB130 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE2"		  , mapIns.get("PROBM_STTS_LCLAS_SE_CD")); 		/* 문제상태 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE3"		  , mapIns.get("PROBM_STTS_MLSFC_SE_CD")); 		/* 문제상태 중분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE4"		  , mapIns.get("PROBM_STTS_SCLAS_SE_CD"));		/* 문제상태 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE5" 		  , mapIns.get("PROBM_CAS_LCLAS_SE_CD")); 		/* 문제원인 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE6" 		  , mapIns.get("PROBM_CAS_SCLAS_SE_CD")); 		/* 문제원인 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE7"        , mapIns.get("PROBM_CAS_ETC_CN")); 			/* 문제원인 기타내용 */
					/* 청소년상태구분 : SEB100 */ 
					saveMap.put("LINK_ADDTNG_DATAA_VALUE8"		  , mapIns.get("YNGBGS_STTS_LCLAS_SE_CD"));		/* 청소년상태구분 */		
					/* 연계추가데이터값*/
//					saveMap.put("LINK_ADDTNG_DATAA_VALUE9"		  , null);			/* 연계추가데이터값9*/				
//					saveMap.put("LINK_ADDTNG_DATAA_VALUE10"		  , null);			/* 연계추가데이터값10*/				
					
				}else if("30".equals(sRcptSeCd) || "30" == sRcptSeCd) {
					LOGGER.debug("============접수승인구분코드.반려=[" + sRcptSeCd + "]==========");	
					
					LOGGER.debug("담당자번호는 : " + mapIns.get("PRCS_PIC_NO"));
					// 대상자정보
					saveMap.put("CASE_MNG_SE_CD"	      		  , "01");	   									 // 사례관리구분코드 ( 01 : 사례대상자미선정 , 02 : 사례대상자신청(대기상태) )		
					saveMap.put("CASE_TRPR_NOAP_CS_SE_CD"		  , "99"); 	       									 /* 사례대상자미신청사유구분코드 CASE_TRPR_NOAP_CS_SE_CD */					
					saveMap.put("CASE_TRPR_UNSL_CS_CN"			  , "병무청연계=[" + String.valueOf(mapIns.get("RJCT_CS_CN")) + "]");  /* 반려사유내용 CASE_TRPR_UNSL_CS_CN */						
					
					/* 문제상태및원인 : SEB130 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE2"		  , null); 		/* 문제상태 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE3"		  , null); 		/* 문제상태 중분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE4"		  , null);		/* 문제상태 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE5" 		  , null); 		/* 문제원인 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE6" 		  , null); 		/* 문제원인 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE7"        , null); 		/* 문제원인 기타내용 */
					/* 청소년상태구분 : SEB100 */ 
					saveMap.put("LINK_ADDTNG_DATAA_VALUE8"		  , null);		/* 청소년상태구분 */		
					/* 연계추가데이터값*/
//					saveMap.put("LINK_ADDTNG_DATAA_VALUE9"		  , null);		/* 연계추가데이터값9*/				
//					saveMap.put("LINK_ADDTNG_DATAA_VALUE10"		  , null);		/* 연계추가데이터값10*/							
					
				}else {
					LOGGER.debug("============접수승인구분코드.기타=[" + sRcptSeCd + "]==========");					
				}
				
				LOGGER.debug("============대상자등록Map.trprMap=[" + saveMap + "]==========");					
				
			}else if(sTrprRrno == null && "".equals(sTrprRrno) && "null".equals(sTrprRrno)) {
				
				String sRjctCsCn = "주민번호가 없는 대상자로 반려처리 되었습니다.";
				
				/* 주민번호 없는경우 반려처리*/
				mapIns.put("RCPT_SE_CD", "30");
				mapIns.put("RJCT_CS_CN", sRjctCsCn);
				
				irrNoCnt ++;	/* 주민번호 없음*/
			}
			
			LOGGER.debug("============병역의무자 상담지원의뢰 접수 결과Map.mapIns=[" + mapIns + "]==========");					
			iIntCnt = linkMmaRcptJobMapper.insertMmaRqstResult(mapIns);
			
			LOGGER.debug("============대상자등록=[" + iIntCnt + "], [" + irrNoCnt + "]==========");					
			if(iIntCnt == 1 && irrNoCnt == 0) {
				
				linkMmaRcptJobMapper.updateEsbStatus(mapIns);
				
				//대상자등록 공통맵퍼 
				trprInqMapper.insertTrprInqDetail(saveMap);
				saveMap.put("DATAA_CHG_SE_CD", sts);
			}
		}
		while (updatedRows.hasNext()) {
			String sts = "U";
			Map<String, String> mapUpd        = updatedRows.next().toMap();
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			
			// 접수구분코드 ( 승인 : 21, 반려 : 30 )
			String sRcptSeCd = mapUpd.get("RCPT_SE_CD");			
			iUdtCnt = linkMmaRcptJobMapper.updateMmaRqstResult(mapUpd);
			
			if(iUdtCnt >= 1) {
				 saveMap.put("FAM_SHAPE_SE_CD" 		   , "99"); 					/* 가족구성형태 FAM_SHAPE_SE_CD */
				 saveMap.put("FAM_SHAPE_ETC_CN"   	   , "병무청연계대상자"); 	    /* 가족구성형태 기타내용 FAM_SHAPE_ETC_CN */
				 saveMap.put("RESIDE_SHAPE_SE_CD"      , "99"); 					/* 주거형태 RESIDE_SHAPE_SE_CD */
				 saveMap.put("RESIDE_SHAPE_ETC_CN"     , "병무청연계대상자"); 		/* 주거형태 기타내용 RESIDE_SHAPE_ETC_CN */
				 saveMap.put("PBLAST_SE_CD" 		   , "05"); 					/* 사회보장구분 PBLAST_SE_CD */
				 saveMap.put("RCPT_RQST_COURS_SE_CD"   , "02100101"); 				/* 접수의뢰경로구분코드   ( 병무청 및 소관 기관·시설 ) */
				 saveMap.put("CASE_TRPR_TYPE_SE_CD"    , "03");						/* 사례대상자유형구분코드 (병무청연계대상자) */	
				 
				 // 연락처 정보가 없는경우 연락처미입력 사유 입력 (대상자 등록시)
				 /* 개인식별정보 미입력사유내용 INDV_IDNTFC_INFO_UNIPT_CS_CN*/
				 if (sTrprTelno == null || "".equals(sTrprTelno) && sTrprMblTelno == null || "".equals(sTrprTelno)) {
				 	saveMap.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "병무청연계대상자로 연락처정보 미입력");
				 }else if(sTrprTelno != null || ! "".equals(sTrprTelno) && sTrprMblTelno != null || ! "".equals(sTrprMblTelno)) {
				 	saveMap.put("TRPR_TELNO"          , sTrprTelno);
				 	saveMap.put("MBL_TELNO"           , sTrprMblTelno);
				 }					
					
					saveMap.put("TRPR_INFO_NO"			  , mapUpd.get("TRPR_INFO_NO"));
					 /* SEA200 대상자등록*/
					saveMap.put("RCPT_INST_NO"			  , (mapUpd.get("RQST_PRCS_SRVC_INST_NO") == null ? String.valueOf(loginVO.getInstNo()) : mapUpd.get("RQST_PRCS_SRVC_INST_NO")));   // 접수기관번호
					saveMap.put("RCPT_PIC_NO"			  , (mapUpd.get("PRCS_PIC_NO") == null ? String.valueOf(loginVO.getEnfsnNo()) : mapUpd.get("PRCS_PIC_NO")));  			             // 접수담당자번호
					saveMap.put("UNT_TASKWK_SE_CD"		  , ((sUntTaskwkSeCd == "" || sUntTaskwkSeCd == null) ?   String.valueOf(loginVO.getUntTaskwk()) : sUntTaskwkSeCd));    	         // 단위업무
					saveMap.put("TRPR_NM"		          , sTrprnm);    			 // 대상자명암호화
					saveMap.put("TRPR_BRTH_YMD"		      , sTrprBrthYmd);      					 // 대상자생년월일
					saveMap.put("SXDC_SE_CD"			  , sTrprSxdcSeCd);           				 // 성별구분코드(병역의무자)
					saveMap.put("SESS_USER_ID" 		      , sUserId);          					     // 최초등록자아이디
					saveMap.put("LINK_ADDTNG_DATAA_VALUE1", dsCAA130List.get(0).get("SRVC_RQST_APLY_NO"));  // CAA100 KEY값 저장
					saveMap.put("ZIP" 		      		  , sTrprRoadRdnmZip);          			 // 우편번호
					saveMap.put("PST_ADDR" 		      	  , sTrprRdnmadr);          				 // 도로명우편주소
					saveMap.put("DADDR" 		      	  , sTrprRdnmDtladr);          				 // 도로명우편상세주소				
				}
				if("21".equals(sRcptSeCd) || "21" == sRcptSeCd) {
					LOGGER.debug("============접수승인구분코드.승인=[" + sRcptSeCd + "]==========");	
					
					// 대상자정보
					saveMap.put("CASE_MNG_SE_CD"	              , "02");								 // 사례관리구분코드 ( 01 : 사례대상자미선정 , 02 : 사례대상자신청(대기상태) )		
					
					/* 문제상태및원인 : SEB130 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE2"		  , mapUpd.get("PROBM_STTS_LCLAS_SE_CD")); 		/* 문제상태 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE3"		  , mapUpd.get("PROBM_STTS_MLSFC_SE_CD")); 		/* 문제상태 중분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE4"		  , mapUpd.get("PROBM_STTS_SCLAS_SE_CD"));		/* 문제상태 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE5" 		  , mapUpd.get("PROBM_CAS_LCLAS_SE_CD")); 		/* 문제원인 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE6" 		  , mapUpd.get("PROBM_CAS_SCLAS_SE_CD")); 		/* 문제원인 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE7"        , mapUpd.get("PROBM_CAS_ETC_CN")); 			/* 문제원인 기타내용 */
					/* 청소년상태구분 : SEB100 */ 
					saveMap.put("LINK_ADDTNG_DATAA_VALUE8"		  , mapUpd.get("YNGBGS_STTS_LCLAS_SE_CD"));		/* 청소년상태구분 */		
					 /* 사례담당자 : SEB150 */                                                                                                                                                                                         
					saveMap.put("LINK_ADDTNG_DATAA_VALUE9"		  , (mapUpd.get("PRCS_PIC_NO") == null ? String.valueOf(loginVO.getEnfsnNo()) : mapUpd.get("PRCS_PIC_NO")) );			      /* 사례담당자번호*/	                      
					saveMap.put("LINK_ADDTNG_DATAA_VALUE10"	      , (mapUpd.get("RQST_PRCS_SRVC_INST_NO") == null ? String.valueOf(mapUpd.get("RQST_PRCS_SRVC_INST_NO")): mapUpd.get("RQST_PRCS_SRVC_INST_NO")));	  /* 담당자기관번호*/				 

				}else if("30".equals(sRcptSeCd) || "30" == sRcptSeCd) {
					LOGGER.debug("============접수승인구분코드.반려=[" + sRcptSeCd + "]==========");	
					
					// 대상자정보
					saveMap.put("CASE_MNG_SE_CD"	      		  , "01");	   									 // 사례관리구분코드 ( 01 : 사례대상자미선정 , 02 : 사례대상자신청(대기상태) )		
					saveMap.put("CASE_TRPR_NOAP_CS_SE_CD"		  , "99"); 	       									 /* 사례대상자미신청사유구분코드 CASE_TRPR_NOAP_CS_SE_CD */					
					saveMap.put("CASE_TRPR_UNSL_CS_CN"			  , "병무청연계=[" + String.valueOf(mapUpd.get("RJCT_CS_CN")) + "]");  /* 반려사유내용 CASE_TRPR_UNSL_CS_CN */						
					
					/* 문제상태및원인 : SEB130 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE2"		  , null); 		/* 문제상태 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE3"		  , null); 		/* 문제상태 중분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE4"		  , null);		/* 문제상태 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE5" 		  , null); 		/* 문제원인 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE6" 		  , null); 		/* 문제원인 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE7"        , null); 		/* 문제원인 기타내용 */
					/* 청소년상태구분 : SEB100 */ 
					saveMap.put("LINK_ADDTNG_DATAA_VALUE8"		  , null);		/* 청소년상태구분 */		
					/* 사례담당자 : SEB150 */                                                                                                                                                                                         
					saveMap.put("LINK_ADDTNG_DATAA_VALUE9"		  , (mapUpd.get("PRCS_PIC_NO") == null ? String.valueOf(loginVO.getEnfsnNo()) : mapUpd.get("PRCS_PIC_NO")) );			      /* 사례담당자번호*/	                      
					saveMap.put("LINK_ADDTNG_DATAA_VALUE10"	      , (mapUpd.get("RQST_PRCS_SRVC_INST_NO") == null ? String.valueOf(mapUpd.get("RQST_PRCS_SRVC_INST_NO")): mapUpd.get("RQST_PRCS_SRVC_INST_NO")));	  /* 담당자기관번호*/				 
				}else {
					LOGGER.debug("============접수승인구분코드.기타=[" + sRcptSeCd + "]==========");					
				}
				
				LOGGER.debug("============대상자수정Map.trprMap=[" + saveMap + "]==========");				 
					
				iUdtCnt = trprInqMapper.updateTrprInqDetail(saveMap);
				saveMap.put("DATAA_CHG_SE_CD", sts);				
			}
		while (deletedRows.hasNext()) {
			String sts = "D";
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("DATAA_CHG_SE_CD", sts);
			saveMap.put("DATAA_CHG_SE_CD", sts);			
		}		
		LOGGER.debug("========== 화면으로 전달한 retMap" + retMap + "==========");			
		
		/* 대상자정보 이력 SEA201*/
		trprInqMapper.insertTrprInqHistory(saveMap);		
		return retMap;
	}
	
	
	/**
	 * @Method명 : selectRenuNo
	 * @param sessionUserId(세션정보), RenuNoSeCd(채번코드)
	 * @return
	 * @throws Exception 
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 1.
	 * @Method설명 : 식별번호 채번
	 */
	private String selectRenuNo(String sessionUserId, String RenuNoSeCd) throws Exception {
		Log.log("::::: 식별번호 채번 시작 :::::");
		String sIdntfcNo = "";
		// 식별번호 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();			
		
		seqMap.put("USER_ID",       sessionUserId);
		seqMap.put("RENU_NO_SE_CD", RenuNoSeCd);			// 채번코드
		seqMap.put("RENU_YMD",      DateUtil.getToday());	// 현재일자

		// 채번서비스 호출
		valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
		
		sIdntfcNo = String.valueOf(valMap.get("RENU_NO"));	// 식별번호 채번
		Log.log("::::: 채번테스트 :::::");
		Log.log("::::: 채번리턴값 확인=[" + sIdntfcNo + "] :::::");
		return sIdntfcNo;
	}

	/**
	 * @Method명   : linkMmaFilesDown
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 26. 
	 * @Method설명 :
	 */
	@Override
	public void linkMmaFilesDown(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup =  dataRequest.getParameterGroup("dmFile");
		
		LOGGER.debug("==========paramGroup[" + paramGroup + "]");
		
		Map<String, String> map = new HashMap<>();
		if(paramGroup != null) {
			String strSrvcRqstAplyNo =  paramGroup.getValue("SRVC_RQST_APLY_NO");
			String strEsbSeq         =  paramGroup.getValue("ESB_SEQ");
//			String strRqstAtcflNm    =  paramGroup.getValue("RQST_ATCFL_NM");
//			String strRqstAtcflPath  =  paramGroup.getValue("RQST_ATCFL_PATH");
//			String strRqstAtcflSynyN =  paramGroup.getValue("RQST_ATCFL_SYN_YN");
//			String strRqstAtcflYmd   =  paramGroup.getValue("RQST_ATCFL_YMD");
			
			map.put("SRVC_RQST_APLY_NO", (strSrvcRqstAplyNo != "" ? strSrvcRqstAplyNo : ""));
			map.put("ESB_SEQ", (strEsbSeq != "" ? strEsbSeq : ""));
		}
		
		Map<String, String> getMap = new HashMap<>();
		getMap = linkMmaRcptJobMapper.selectLinkMmaFileInfo(map);
		
		String strRqstAtcflNm = "";
		String strAtcflPath   = "";
		String strAtcflSynYN  = "";
		
		if(getMap != null) {
			strRqstAtcflNm = getMap.get("RQST_ATCFL_NM");
			strAtcflPath   = getMap.get("RQST_ATCFL_PATH");
			strAtcflSynYN  = getMap.get("RQST_ATCFL_SYN_YN");
			
		}
		
		if("Y".equals(strAtcflSynYN)) {
			
			LOGGER.debug("==========[" + " 병무청연계수신파일 다운로드 " +"]");
			
			String filePath      = strAtcflPath;
			String fileName      = strRqstAtcflNm;
			String fileFullPath  = (strWasFileBasePath + filePath + "/" + fileName);
			final String resCharset = "UTF-8";
			
			File downloadFile = null;
			
			if(! filePath.isEmpty() && ! fileName.isEmpty()) {
				
				downloadFile = new File(fileFullPath);
				
				if(! downloadFile.exists()) {
					throw new FileNotFoundException();
				}
			}
			
			try {
				FileInputStream in = new FileInputStream(downloadFile);
				
				fileName = HttpWebUtil.getUrlEncodedFileName(request, fileName);
				
				// res
				response.setContentType("application/x-msdownload" + ";charset=" + resCharset);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");		
				
				OutputStream out = response.getOutputStream();
				// res
				int data;
				
				while ((data = in.read()) != -1) {   
					out.write(data);
				}		
				
				out.flush();
				out.close();
				in.close();
				
			} catch(IOException ex) {
				ex.printStackTrace();
				
			}		
			
		}
		
	}	

}
