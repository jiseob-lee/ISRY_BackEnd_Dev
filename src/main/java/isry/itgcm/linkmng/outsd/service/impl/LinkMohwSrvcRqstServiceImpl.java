/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springsource.loaded.Log;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.ibm.icu.util.Calendar;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseExcnMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.uneart.mapper.TrprInqMapper;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcm.linkmng.outsd.mapper.LinkMohwSrvcRqstMapper;
import isry.itgcm.linkmng.outsd.service.LinkMohwSrvcRqstService;
import isry.itgcms.syscmmn.rest.service.RestService;
import isry.itgcms.sysmgmt.userauth.mapper.InqOrgListMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Formatter;

/**
 * @파일명 : LinkMohwSrvcRqstServiceImpl.java
 * @프로그램 설명 : 복지부 연계서비스 의뢰
 * @작성자 : Yoo.Chi.Hoon
 * @작성일 : 2022. 9. 29.
 * @수정자 : Yoo.Chi.Hoon
 * @수정일 : 2022. 9. 29.
 * @수정내용 : - -
 */
@Service("linkMohwSrvcRqstService")
public class LinkMohwSrvcRqstServiceImpl extends EgovAbstractServiceImpl implements LinkMohwSrvcRqstService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
//	private static final String REQUEST_URL = "http://10.188.131.225:25000/WS/";
//	private static final String REQUEST_URL = "http://10.188.131.226:25000/WS/";
	private static final String REQUEST_URL = "http://10.188.131.156:25000/WS/"; //연계 L4 IP

	@Resource(name = "linkMohwSrvcRqstMapper")
	public LinkMohwSrvcRqstMapper linkMohwSrvcRqstMapper;   /* 복지부 서비스의뢰 Mapper*/

	@Resource(name="restService")
	private RestService restService;
	
	@Resource(name = "trprInqService")
	private TrprInqService trprInqService;	

	@Resource(name = "trprInqMapper")
	private TrprInqMapper trprInqMapper; 					/* 대상자 Mapper*/

	@Resource(name = "caseRegMapper")
	private CaseRegMapper caseRegMapper; 				    /* 사례기본등록 Mapper*/
	
	@Resource(name = "caseExcnMapper")
	private CaseExcnMapper caseExcnMapper; 				    /* 사례실행등록 Mapper*/

	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper; 						/* 채번 Mapper*/
	
	@Resource(name = "inqOrgListMapper")
	private InqOrgListMapper inqOrgListMapper;			    /* 기관 조회 Mapper */

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
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
	 * @Method명 : selectMohwSrvcRqstRcptList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 복지부 서비스의뢰접수 목록
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstRcptList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		LOGGER.debug("::::: 서비스의뢰접수목록 selectMohwSrvcRqstRcptList :::::");
		LOGGER.debug("selectMohwSrvcRqstRcptList.paramGroup=[" + paramGroup + "]");
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		

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
		
		/* 서비스의뢰접수 목록 Mapper 호출 */
		
		return linkMohwSrvcRqstMapper.selectMohwSrvcRqstRcptList(paramMap2);
	}

	/**
	 * @Method명 : selectMohwSrvcRqstRcptInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 30.
	 * @Method설명 : 복지부 서비스의뢰접수 정보 조회
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		int totCnt = 0;

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 의뢰접수 정보가 없습니다.");
		}
		LOGGER.debug("========== 복지부 서비스의뢰 접수정보 조회 START ==========");
		LOGGER.debug("selectMohwSrvcRqstRcptInfo.paramGroup=[" + paramGroup + "]");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		paramMap.get("SRV_CNSRQ_ID"); 	  /* 서비스상담의뢰ID */
		paramMap.get("ESB_SEQ"); 	  	  /* 연계시퀀스 */
		paramMap.get("TRPR_INFO_NO"); 	  /* 대상자번호 */

		List<Map<String, Object>> retList = new ArrayList<>();
		retList = linkMohwSrvcRqstMapper.selectMohwSrvcRqstRcptInfo(paramMap);    /* 서비스의뢰 접수정보 조회 */
		
		for (int idx = 0; idx < retList.size(); idx++) {
			
			String sRqstInstPicTelNo = String.valueOf(retList.get(idx).get("RQST_INST_PIC_TELNO"));
			String sRqstInstPicMpno  = String.valueOf(retList.get(idx).get("RQST_INST_PIC_MPNO"));
			String sTrprTelno 		 = String.valueOf(retList.get(idx).get("TRPR_TELNO"));
			String sTrprMpno         = String.valueOf(retList.get(idx).get("TRPR_MPNO"));
			
			if (! "".equals(sRqstInstPicTelNo) || "null".equals(sRqstInstPicTelNo)) retList.get(idx).put("RQST_INST_PIC_TELNO", Formatter.phoneFormat(sRqstInstPicTelNo, 1));
			if (! "".equals(sRqstInstPicMpno)  || "null".equals(sRqstInstPicTelNo)) retList.get(idx).put("RQST_INST_PIC_MPNO" , Formatter.phoneFormat(sRqstInstPicTelNo, 1));
			if (! "".equals(sTrprTelno)  	   || "null".equals(sRqstInstPicTelNo)) retList.get(idx).put("TRPR_TELNO"		  , Formatter.phoneFormat(sRqstInstPicTelNo, 1));
			if (! "".equals(sTrprMpno)  	   || "null".equals(sRqstInstPicTelNo)) retList.get(idx).put("TRPR_MPNO"		  , Formatter.phoneFormat(sRqstInstPicTelNo, 1));
		}
		return retList;
	}

	/**
	 * @Method명 : selectMohwSrvcRqstRcptInfoResultList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 30.
	 * @Method설명 : 복지부 서비스의뢰접수정보결과 목록
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfoResultList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 서비스의뢰접수정보결과가 없습니다.");
		}
		LOGGER.debug("========== 복지부 서비스의뢰접수정보결과 목록 START ==========");
		LOGGER.debug("selectMohwSrvcRqstRcptInfoResultList.paramGroup=[" + paramGroup + "]");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		paramMap.get("SRV_CNSRQ_ID"); /* 서비스상담의뢰ID */

		List<Map<String, Object>> retList = new ArrayList<>();
		retList = linkMohwSrvcRqstMapper.selectMohwSrvcRqstRcptInfoResultList(paramMap); /* 서비스의뢰 접수정보 조회 Mapper */

		LOGGER.debug("========== 복지부 서비스의뢰접수정보결과 목록 retList + " + retList + "==========");

		return retList;
	}
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfoResultInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 : 복지부 서비스의뢰접수결과정보 조회
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfoResultInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 서비스의뢰접수결과정보가 없습니다.");
		}
		LOGGER.debug("========== 복지부 서비스의뢰접수정보결과 목록 START ==========");
		LOGGER.debug("selectMohwSrvcRqstRcptInfoResultInfo.paramGroup=[" + paramGroup + "]");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		paramMap.get("SRV_CNSRQ_ID"); /* 서비스상담의뢰ID */

		List<Map<String, Object>> retList = new ArrayList<>();
		retList = linkMohwSrvcRqstMapper.selectMohwSrvcRqstRcptInfoResultInfo(paramMap); /* 서비스의뢰접수결과정보 조회 Mapper */
		
		return retList;
	}	

	/**
	 * @Method명 : processMohwSrvcRqstRcpt
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 30.
	 * @Method설명 : 복지부 서비스의뢰접수 처리
	 */
	@Override
	public Map<String, Object> processMohwSrvcRqstRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		int udtCnt = 0; /* 수정*/
		int intCnt = 0; /* 저장*/
		
		final String ESB_IF_ID  = "INFIF_IR_SSI_FS_02";		/* 52. 연계인터페이스ID ESB_IF_ID*/
		final String ESB_STATUS = "N";						/* 59. 연계상태 ESB_STATUS default*/
//		final String SND_CD     = "MOG";  					/* 53. 송신기관코드ID SND_CD*/
//		final String RCV_CD     = "SSI";  					/* 54. 수신기관코드ID RCV_CD*/

		ParameterGroup paramCAB100 = dataRequest.getParameterGroup("dsCAB100");
		ParameterGroup paramCAB110 = dataRequest.getParameterGroup("dsCAB110Save");

		LOGGER.debug("========== 복지부 서비스의뢰 접수 START ==========");
		LOGGER.debug("processMohwSrvcRqstRcpt.paramCAB100=[" + paramCAB100 + "]");
		LOGGER.debug("processMohwSrvcRqstRcpt.paramCAB110=[" + paramCAB110 + "]");

		if (paramCAB100 == null || paramCAB110 == null) {
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
		
		List<Map<String, String>> dsCAB100List = paramCAB100.getAllRowList(); // 서비스의뢰접수
		List<Map<String, String>> dsCAB110List = paramCAB110.getAllRowList(); // 서비스의뢰접수결과 저장data

		String sSrvCnsroId = String.valueOf(dsCAB110List.get(0).get("SRV_CNSRQ_ID")); /* 서비스상담의뢰ID */
		String sEsbSeq 	   = String.valueOf(dsCAB110List.get(0).get("ESB_SEQ"));      /* 연계시퀀스 */
		
		// 키값 저장
		Map<String, String> saveMap  = new HashMap<>();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("SRV_CNSRQ_ID", sSrvCnsroId);  		/* 서비스상담의뢰ID */
		paramMap.put("ESB_SEQ"     , sEsbSeq); 				/* 연계시퀀스 */		
		Map<String, Object> retMap   = new HashMap<>();		/* 화면 재조회용 returnMap*/
		retMap.put("SRV_CNSRQ_ID"  , sSrvCnsroId);
		retMap.put("ESB_SEQ"       , sEsbSeq);
		
		/* 대상자테이블 insert column*/ 
		String sTrprFlnm 		= (paramCAB100.getValue("TRPR_FLNM")          == "" ? "" : paramCAB100.getValue("TRPR_FLNM"));			/* 대상자명*/
		String sTrprRrnoSexCd   = (paramCAB100.getValue("TRPR_RRNO_SEX_CD")   == "" ? "" : paramCAB100.getValue("TRPR_RRNO_SEX_CD"));	/* 성별*/	    
		String sTrprBrdt       	= (paramCAB100.getValue("TRPR_BRDT")          == "" ? "" : paramCAB100.getValue("TRPR_BRDT"));			/* 생년월일*/
		String sTrprRrno		= (paramCAB100.getValue("TRPR_RRNO") 		  == "" ? "" : paramCAB100.getValue("TRPR_RRNO"));			/* 주민등록번호*/
		String sTrprTelno		= (paramCAB100.getValue("TRPR_TELNO") 		  == "" ? "" : paramCAB100.getValue("TRPR_TELNO"));		    /* 전화번호*/
		String sTrprMpno		= (paramCAB100.getValue("TRPR_MPNO") 		  == "" ? "" : paramCAB100.getValue("TRPR_MPNO"));		    /* 휴대전화번호*/
		String sTrprRdnmZip	 	= (paramCAB100.getValue("TRPR_RDNM_ZIP") 	  == "" ? "" : paramCAB100.getValue("TRPR_RDNM_ZIP"));		/* 우편번호*/
		String sTrprRdnmadr	 	= (paramCAB100.getValue("TRPR_RDNMADR") 	  == "" ? "" : paramCAB100.getValue("TRPR_RDNMADR"));		/* 우편주소*/
		String sTrprRdnmDtladr  = (paramCAB100.getValue("TRPR_RDNM_DTLADR")   == "" ? "" : paramCAB100.getValue("TRPR_RDNM_DTLADR"));	/* 상세주소*/
		String sTrprPrvcPvagrYn = (paramCAB100.getValue("TRPR_PRVC_PVAGR_YN") == "" ? "" : paramCAB100.getValue("TRPR_PRVC_PVAGR_YN")); /* 정보제공동의여부*/			
		
		String sInstNo  = (paramCAB100.getValue("RQST_PRCS_SRV_INST_ID")    == "" ? "" : paramCAB100.getValue("RQST_PRCS_SRV_INST_ID")); /* 기관번호*/			
		String sInstNm  = (paramCAB100.getValue("RQST_PRCS_SRV_INST_NM")    == "" ? "" : paramCAB100.getValue("RQST_PRCS_SRV_INST_NM")); /* 기관명*/			
		String sEnfsnNo = (paramCAB100.getValue("PRCS_RQST_PIC_ENFSN_NO")   == "" ? "" : paramCAB100.getValue("PRCS_RQST_PIC_ENFSN_NO")); /* 종사자번호*/			
		String sEnfsnnm = (paramCAB100.getValue("RQST_PRCS_INST_PRCR_FLNM") == "" ? "" : paramCAB100.getValue("RQST_PRCS_INST_PRCR_FLNM")); /* 종사자명*/			
		
		// 저장, 수정, 삭제 시작
		LOGGER.debug(":::::::::: 서비스의뢰접수결과 저장, 수정, 삭제 시작 ::::::::::");
		Iterator<ParameterRow> insertedRows = paramCAB110.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = paramCAB110.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = paramCAB110.getDeletedRows();		
		while (insertedRows.hasNext()) {
			String sts = "I";
			Map<String, String> mapIns 	      = insertedRows.next().toMap();
			
			// 의뢰처리구분 ( 결정 : 001, 반려 : 002 )
			String sSrvRqstPrcsDcd = mapIns.get("SRV_RQST_PRCS_DCD");
			/* 의뢰처리결과상세코드*/
			String sRqstPrsltDtlCd = mapIns.get("RQST_PRSLT_DTL_CD"); 
			if (sSrvRqstPrcsDcd.isEmpty()) {
				throw new AppWorksException("의뢰처리구분이 선택되지 않았습니다.\n확인 후 다시 저장 바랍니다.");
			}
			if (sRqstPrsltDtlCd.isEmpty()) {
				throw new AppWorksException("의뢰처리결과구분이 선택되지 않았습니다.\n확인 후 다시 저장 바랍니다.");
			}
//			LOGGER.debug("의뢰처리기관담당자전화번호");
//			LOGGER.debug(mapIns.get("RQST_PRCS_INST_PRCR_TELNO") );
			/* 사보정송신기과정보 복지부 전달시 휴대전화번호 OR 전화번호 둘중 하나는 반드시 들어가야 넘어감(서비스접스의뢰결과 담당자 전화번호 NOT NULL 전화번호 둘중 하나들어오면 입력*/
//			mapIns.put("RQST_PRCS_INST_PRCR_TELNO"		  , (mapIns.get("RQST_PRCS_INST_PRCR_TELNO") == "") ? mapIns.get("RQST_INST_PIC_MPNO") : mapIns.get("RQST_PRCS_INST_PRCR_TELNO"));
			
			/* 12. 의뢰처리기관처리자전화번호 RQST_PRCS_INST_PRCR_TELNO */
			if(mapIns.get("RQST_PRCS_INST_PRCR_TELNO").isEmpty()) {
				throw new AppWorksException("의뢰처리기관 담당자의 전화번호 또는 휴대전화번호가 등록되지 않았습니다.");
			}			
			
			/* 화면에서 입력받은 서비스의뢰접수결과 정보 */
			/* 1. 서비스의뢰연계구분코드 SRV_RQST_LINK_DCD*/
			/* 2. 서비스의뢰처리구분코드 SRV_RQST_PRCS_DCD*/
			/* 3. 서비스상담의뢰ID SRV_CNSRQ_ID(복지부에서 접수시 보낸 ID, 예) S00000001944167)*/
			/* 4. 서비스의뢰처리ID SRV_RQST_PRCS_ID (위기청소년에서 보낼 ID)*/
			String sSrvRqstPrcsId = selectRenuNo(sUserId, "SC");
			mapIns.put("SRV_RQST_PRCS_ID", sSrvRqstPrcsId);
			saveMap.put("SRV_RQST_PRCS_ID"			  , mapIns.get("SRV_RQST_PRCS_ID"));
			
			/* 저장후 화면 조회용*/
			retMap.put("SRV_RQST_PRCS_ID", sSrvRqstPrcsId);
			
			/* 5. 의뢰업무처리구분코드 RQST_TSKPRC_DCD*/
			/* 6. 의뢰업무처리구분명 RQST_TSKPRC_DNM*/
			/* 7. 의뢰기관유형상세코드 RQST_INST_TYP_DTL_CD*/
			/* 8. 의뢰기관유형상세명 RQST_INST_TYP_DTL_NM*/
			/* 9. 의뢰처리서비스기관ID RQST_PRCS_SRV_INST_ID ( 연계자원으로 수신된 원천주체ID ) 서비스접수 , (청소년안전망에서
			 * 관리하는 복지자원제공주체ID로 전송) */
			/* 10. 의뢰처리서비스기관명 RQST_PRCS_SRV_INST_NM */
			/* 11. 의뢰처리기관처리자성명 RQST_PRCS_INST_PRCR_FLNM */
			/* 12. 의뢰처리기관처리자전화번호 RQST_PRCS_INST_PRCR_TELNO */
			saveMap.put("RQST_PRCS_SRV_INST_ID"			  , mapIns.get("RQST_PRCS_SRV_INST_ID"));
			saveMap.put("RQST_PRCS_SRV_INST_NM"			  , mapIns.get("RQST_PRCS_SRV_INST_NM"));
			saveMap.put("RQST_PRCS_INST_PRCR_FLNM"		  , mapIns.get("RQST_PRCS_INST_PRCR_FLNM"));
			saveMap.put("RQST_PRCS_YMD"					  , mapIns.get("RQST_PRCS_YMD")); 					/* 13. 의뢰처리일자 */
			saveMap.put("RQST_PRSLT_DTL_CD"				  , mapIns.get("RQST_PRSLT_DTL_CD")); 				/* 14. 의뢰처리결과상세코드 */
			saveMap.put("RQST_PRSLT_DTL_NM"			      , mapIns.get("RQST_PRSLT_DTL_NM")); 				/* 15. 의뢰처리결과상세명 */
			saveMap.put("SRV_PVSN_CYC_NM"			      , mapIns.get("SRV_PVSN_CYC_NM")); 				/* 27. 서비스제공주기명 */
			saveMap.put("SRV_PVSN_TMCNT"			      , mapIns.get("SRV_PVSN_TMCNT")); 				    /* 28. 서비스제공횟수 */
			saveMap.put("PRCS_RSC_PRVSV_NM"				  , mapIns.get("PRCS_RSC_PRVSV_NM")); 				/* 36. 서비스제공자원명 */
			saveMap.put("PRCS_INST_SRV_BGNG_YMD"	      , mapIns.get("PRCS_INST_SRV_BGNG_YMD")); 			/* 39. 처리기관서비스시작일자 */
			saveMap.put("PRCS_INST_SRV_END_YMD"		      , mapIns.get("PRCS_INST_SRV_END_YMD")); 			/* 40. 처리기관서비스종료일자 */
			saveMap.put("PRCS_INST_SRV_PRSLT_CN"	      , mapIns.get("PRCS_INST_SRV_PRSLT_CN")); 			/* 41. 처리기관서비스처리결과내용 */
			saveMap.put("PRCS_INST_SRV_RQST_RJCT_RSN_CD"  , mapIns.get("PRCS_INST_SRV_RQST_RJCT_RSN_CD"));  /* 42. 처리기관서비스의뢰반려사유코드 */
			saveMap.put("PRCS_INST_SRV_RJCT_RSN_CN"	      , mapIns.get("PRCS_INST_SRV_RJCT_RSN_CN")); 		/* 43. 처리기관서비스반려사유내용 */
			
			/* (여가부 연계컬럼) */
			/* 50. 트랜잭션아이디 ESB_TX_ID */
			/* 51. 연계시퀸스 ESB_SEQ*/
			
//			saveMap.put("ESB_IF_ID"	      , "INFIF_IR_SSI_FS_02");	
//			saveMap.put("SND_CD"	      , "MOG"); 			  	
//			saveMap.put("RCV_CD"	      , "SSI"); 		      	
			
			mapIns.put("ESB_IF_ID", ESB_IF_ID);					/* 52. 연계인터페이스ID ESB_IF_ID */
			mapIns.put("SND_CD"   , SND_CD);					/* 53. 송신기관코드ID SND_CD */
			mapIns.put("RCV_CD"   , RCV_CD);					/* 54. 수신기관코드ID RCV_CD */
			
			/* 55. 연계파일이름ID ESB_FILE_NAME */
			/* 56. 데이터생성일시 CREATE_TIME */
			/* 57. 연계시작일시 ESB_INIT_TIME */
			/* 58. 연계시작일시 ESB_INIT_TIME */
			/* 58. 연계종료일시 ESB_COMPLITE_TIME */
			mapIns.put("ESB_STATUS", ESB_STATUS);				/* 59. 연계상태 ESB_STATUS*/
			

			
			/* 대상자정보 컬럼*/
			mapIns.put("TRPR_FLNM"       	, sTrprFlnm);
			mapIns.put("TRPR_RRNO_SEX_CD"	, sTrprRrnoSexCd);
			mapIns.put("TRPR_BRDT"       	, sTrprBrdt);
			mapIns.put("TRPR_RRNO"		 	, sTrprRrno);
			mapIns.put("TRPR_TELNO"		 	, sTrprTelno.replace("-", ""));
			mapIns.put("TRPR_MPNO"		 	, sTrprMpno.replace("-", ""));
			mapIns.put("TRPR_RDNM_ZIP"	 	, sTrprRdnmZip);
			mapIns.put("TRPR_RDNMADR"	 	, sTrprRdnmadr);
			mapIns.put("TRPR_RDNM_DTLADR"   , sTrprRdnmDtladr);
			mapIns.put("TRPR_PRVC_PVAGR_YN" , sTrprPrvcPvagrYn);
			
			/*
			 * 화면 Column : SRV_RQST_PRCS_DCD <저장> 1.의뢰처리구분 = “결정“ - 대상자정보 생성 - 사례기본 생성 -
			 * 의뢰접수 연계상태를 “Y’로 update - 의뢰접수결과 생성(연계상태=“N”)
			 * 2.의뢰처리구분 = “반려“ - 의뢰접수 연계상태를 “Y’로 update - 의뢰접수결과 생성(연계상태=“N”)
			 */
			LOGGER.debug(":::::::::: 서비스의뢰접수 결과 등록=[" + mapIns + "] ::::::::::");
			/* 서비스의뢰접수결과 등록 */
			intCnt = linkMohwSrvcRqstMapper.insertMohwSrvcRqstRcptResult(mapIns);
			if (intCnt > 0) {
				
				Map<String, String> trprMap = new HashMap<>();
				
				trprMap.put("SRV_RQST_PRCS_ID"		, mapIns.get("SRV_RQST_PRCS_ID"));				/* 4. 서비스의뢰처리ID SRV_RQST_PRCS_ID*/
				trprMap.put("ESB_SEQ"			    , mapIns.get("ESB_SEQ"));						/* 51. 연계시퀸스 ESB_SEQ*/
				trprMap.put("SRV_RQST_PRCS_DCD"		, mapIns.get("SRV_RQST_PRCS_DCD"));				/* 2. 서비스의뢰처리구분코드 SRV_RQST_PRCS_DCD*/
				trprMap.put("RQST_PRSLT_DTL_CD"		, mapIns.get("RQST_PRSLT_DTL_CD"));				/* 14. 의뢰처리결과상세코드 RQST_PRSLT_DTL_CD*/
				
				// 대상자정보 생성 : SEA200, 대상자정보이력 생성 : SEA201
				// 의뢰처리기관의 담당업무구분의 단위업무구분으로 단위업무구분코드를 셋팅 후 생성해야함
				trprMap.put("TRPR_FLNM"		   	   , sTrprFlnm);						
				trprMap.put("TRPR_BRDT"		       , sTrprBrdt);						
				trprMap.put("SXDC_SE_CD"		   , sTrprRrnoSexCd);				
				trprMap.put("TRPR_RRNO"			   , sTrprRrno);
				trprMap.put("TRPR_TELNO"		   , sTrprTelno.replace("-", ""));
				trprMap.put("TRPR_MPNO"		 	   , sTrprMpno.replace("-", ""));					
				trprMap.put("TRPR_RDNM_ZIP"		   , sTrprRdnmZip);
				trprMap.put("TRPR_RDNMADR"		   , sTrprRdnmadr);
				trprMap.put("TRPR_RDNM_DTLADR"	   , sTrprRdnmDtladr);
				trprMap.put("TRPR_PRVC_PVAGR_YN"   , sTrprPrvcPvagrYn);
				
				trprMap.put("UNT_TASKWK_SE_CD"     , mapIns.get("UNT_TASKWK_SE_CD"));				/* 단위업무구분코드 UNT_TASKWK_SE_CD*/						
				trprMap.put("RCPT_PIC_NO"		   , mapIns.get("PRCS_RQST_PIC_ENFSN_NO"));    		/* 접수담당자번호 RCPT_PIC_NO */
				trprMap.put("RCPT_INST_NO"		   , mapIns.get("RQST_PRCS_SRV_INST_ID"));    		/* 접수기관번호 RCPT_INST_NO */
				trprMap.put("DATAA_CHG_SE_CD"	   , sts); 											/* 데이터변경구분코드 DATAA_CHG_SE_CD */
				
				switch (sRqstPrsltDtlCd) {
				case "60081": /* 60081 : 서비스신청대기 */
				case "60082": /* 60082 : 서비스진행중 */
					
					LOGGER.debug(":::::::::: 서비스의뢰접수 결과 의뢰처리결과상세코드=[60081 : 서비스신청대기 60082 : 서비스진행중 60083 : 서비스신청반려 60084 : 서비스완료] ::::::::::");					
					LOGGER.debug(":::::::::: 서비스의뢰접수 결과 의뢰처리결과상세코드=[" + sRqstPrsltDtlCd + "] ::::::::::");					
					
					/* 서비스의뢰접수 수정(연계상태 수정 => 'Y') */
					linkMohwSrvcRqstMapper.updateMohwSrvcRqstRcpt(mapIns);						
					
					/* 문제상태및원인 : SEB130 */
					trprMap.put("PROBM_STTS_LCLAS_SE_CD"		  , mapIns.get("PROBM_STTS_LCLAS_SE_CD")); 			/* 문제상태 대분류 */
					trprMap.put("PROBM_STTS_MLSFC_SE_CD"		  , mapIns.get("PROBM_STTS_MLSFC_SE_CD")); 			/* 문제상태 중분류 */
					trprMap.put("PROBM_STTS_SCLAS_SE_CD"		  , mapIns.get("PROBM_STTS_SCLAS_SE_CD"));			/* 문제상태 소분류 */
					trprMap.put("PROBM_CAS_LCLAS_SE_CD" 		  , mapIns.get("PROBM_CAS_LCLAS_SE_CD")); 			/* 문제원인 대분류 */
					trprMap.put("PROBM_CAS_SCLAS_SE_CD" 		  , mapIns.get("PROBM_CAS_SCLAS_SE_CD")); 			/* 문제원인 소분류 */
					trprMap.put("PROBM_CAS_ETC_CN"      		  , mapIns.get("PROBM_CAS_ETC_CN")); 				/* 문제원인 기타내용 */
					/* 청소년상태구분 : SEB100 */ 
					trprMap.put("YNGBGS_STTS_LCLAS_SE_CD"		  , mapIns.get("YNGBGS_STTS_LCLAS_SE_CD"));			/* 청소년상태구분 */
					/* 서비스제공 SEB500*/
					trprMap.put("SRV_PVSN_CYC_NM"		 		  , mapIns.get("SRV_PVSN_CYC_NM"));				    /* 서비스제공주기명 */
					trprMap.put("SRV_PVSN_TMCNT"		 		  , mapIns.get("SRV_PVSN_TMCNT"));				    /* 서비스제공횟수 */						
					
					break;
				case "60083" : /* 60083 : 서비스신청반려*/
					LOGGER.debug(":::::::::: 서비스의뢰접수 결과 의뢰처리결과상세코드=[" + sRqstPrsltDtlCd + "] ::::::::::");	
					
					trprMap.put("PRCS_INST_SRV_RQST_RJCT_RSN_NM"  , mapIns.get("PRCS_INST_SRV_RQST_RJCT_RSN_NM"));	/* 결정취소(사례미신청사유구분)*/						
					
					break;
				default :	   /* 60084 : 서비스완료*/
					LOGGER.debug(":::::::::: 서비스의뢰접수 결과 의뢰처리결과상세코드=[" + sRqstPrsltDtlCd + "] ::::::::::");	
					break;
				}
				saveTrprInqDetail(trprMap,  sUserId, request);//////////////////
			}
		}
		while (updatedRows.hasNext()) {
			String sts = "U";
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			retMap.put("SRV_RQST_PRCS_ID", mapUpd.get("SRV_RQST_PRCS_ID"));
			
			udtCnt = linkMohwSrvcRqstMapper.updateMohwSrvcRqstRcptResult(mapUpd);
			if (udtCnt > 0) {
				
				mapUpd.put("TRPR_FLNM"		   	   , sTrprFlnm);						
				mapUpd.put("TRPR_BRDT"		       , sTrprBrdt);						
				mapUpd.put("SXDC_SE_CD"		       , sTrprRrnoSexCd);				
				mapUpd.put("TRPR_RRNO"			   , sTrprRrno);
				mapUpd.put("TRPR_TELNO"		       , sTrprTelno.replace("-", ""));
				mapUpd.put("TRPR_MPNO"		 	   , sTrprMpno.replace("-", ""));				
				mapUpd.put("TRPR_RDNM_ZIP"		   , sTrprRdnmZip);
				mapUpd.put("TRPR_RDNMADR"		   , sTrprRdnmadr);
				mapUpd.put("TRPR_RDNM_DTLADR"	   , sTrprRdnmDtladr);
				mapUpd.put("TRPR_PRVC_PVAGR_YN"    , sTrprPrvcPvagrYn);
				mapUpd.put("RCPT_PIC_NO"    	   , sEnfsnNo);
				mapUpd.put("RCPT_INST_NO"    	   , sInstNo);
				
				mapUpd.put("RCPT_PIC_NO"		   , mapUpd.get("PRCS_RQST_PIC_ENFSN_NO"));    		/* 접수담당자번호 RCPT_PIC_NO */
				mapUpd.put("RCPT_INST_NO"		   , mapUpd.get("RQST_PRCS_SRV_INST_ID"));    		/* 접수기관번호 RCPT_INST_NO */				
				
				Map<String, String> trprMap = new HashMap<>();				
				trprMap = saveTrprInqDetail(mapUpd, sUserId, request);
			}
		}
		while (deletedRows.hasNext()) {
			String sts = "D";
//			Map<String, String> mapDel = deletedRows.next().toMap();
		}		
		
		
		LOGGER.debug("========== 화면으로 전달한 retMap" + retMap + "==========");		
		
		return retMap;
	}
	
	private Map<String, String> saveTrprInqDetail(Map<String, String> paramMap, String sUserId, HttpServletRequest request) throws Exception {
		
		LOGGER.debug("========== 대상자 저장 Start ==========");		
		Map<String, String> saveMap = paramMap;
		Map<String, String> getMap  = new HashMap<>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		
		int iInstNo     = loginVO.getInstNo();
		String sEnfsnNo = loginVO.getEnfsnNo();
		
		LOGGER.debug("대상자 저장.saveTrprInqDetail1=[" + saveMap + "]");
		
		// 데이터변경구분코드
		 String sType = String.valueOf(saveMap.get("DATAA_CHG_SE_CD"));
		/* 의뢰처리결과상세코드*/
		String sRqstPrsltDtlCd = saveMap.get("RQST_PRSLT_DTL_CD"); 
//		LOGGER.debug(":::::::::: 서비스의뢰접수 결과 의뢰처리결과상세코드=[60081 : 서비스신청대기 60082 : 서비스진행중 60083 : 서비스신청반려 60084 : 서비스완료] ::::::::::");					
//		LOGGER.debug(":::::::::: 서비스의뢰접수 결과 의뢰처리결과상세코드=[" + sRqstPrsltDtlCd + "] ::::::::::");			 
		 
		 if (sType == "I" || sType == "i") {
			 // 대상자정보 생성 : SEA200, 대상자정보이력 생성 : SEA201
			 // 대상자번호 채번
			 String sTrprInfoNo = selectRenuNo(sUserId, "TR");
			 LOGGER.debug(":::::::::: 대상자번호1=[" + sTrprInfoNo + "] ::::::::::");
			 saveMap.put("TRPR_INFO_NO"		    , sTrprInfoNo); 										/* 대상자번호 TRPR_INFO_NO*/
			 saveMap.put("TRPR_NM"		        , saveMap.get("TRPR_FLNM")); 			/* 대상자명암호화 TRPR_NM_ENCPT*/
			 saveMap.put("MBL_TELNO"		    , saveMap.get("TRPR_MPNO")); 			/* 휴대전화번호 MBL_TELNO_ENCPT*/
			 saveMap.put("TRPR_BRTH_YMD"		, saveMap.get("TRPR_BRDT")); 							/* 대상자출생일자 TRPR_BRTH_YMD*/
			 saveMap.put("ZIP"				    , saveMap.get("TRPR_RDNM_ZIP")); 						/* 대상자우편번호 ZIP*/
			 saveMap.put("PST_ADDR"			    , saveMap.get("TRPR_RDNMADR")); 						/* 대상자도로명주소 PST_ADDR*/
			 saveMap.put("DADDR"				, saveMap.get("TRPR_RDNM_DTLADR")); 					/* 대상자도로명상세주소 DADDR*/
			 
			 // 전화번호
			 // 정보제공동의여부
			 
			 // 연계성공후 사례등록시 필수 *** 사례담당자
//			 saveMap.put("RCPT_PIC_NO", sEnfsnNo);				/* 담당지번호 RCPT_PIC_NO*/
//			 saveMap.put("RCPT_INST_NO", iInstNo);				/* 담당자기관번호 RCPT_INST_NO*/
			 
			 // 연락처 정보가 없는경우 연락처미입력 사유 입력 (대상자 등록시)
			 /* 개인식별정보 미입력사유내용 INDV_IDNTFC_INFO_UNIPT_CS_CN*/
			 String sTrprTelno = saveMap.get("TRPR_TELNO");												/* 대상자전화번호 TRPR_TELNO*/
			 String sMblTelno  = saveMap.get("TRPR_MPNO");												/* 대상자휴대전화번호 MBL_TELNO_ENCPT */
			 if (sTrprTelno == null || "".equals(sTrprTelno) || sMblTelno == null || "".equals(sMblTelno)) {
				 saveMap.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "복지부연계대상자로 연락처정보 미입력");
			 }else if(sTrprTelno != null || ! "".equals(sTrprTelno) && sMblTelno != null || ! "".equals(sMblTelno)) {
				 saveMap.put("TRPR_TELNO", sTrprTelno);
				 saveMap.put("MBL_TELNO"      , sMblTelno);
			 }
			 
			 /* 사례대상자유형구분코드=[발굴대상자 : 01, 기관의뢰대상자 : 02, 병무청연계대상자 : 03, 복지부연계대상자 : 04] */
			 saveMap.put("CASE_TRPR_TYPE_SE_CD"  , "04"); 												/* 사례대상자유형구분코드 CASE_TRPR_TYPE_SE_CD */
			 /* 사례관리구분코드=[사례대상자미신청 : 01, 사례대상자신청(대기상태) : 02, 사례대상자미선정 : 03, 사례대상자선정 : 04] */
			 // 사례관리구분코드 CASE_MNG_SE_CD (연계상태성공 후 배치돌고 성공하면 사례기본 업데이트 후 사례대상자선정 : 04로 업데이트)
			 saveMap.put("CASE_MNG_SE_CD"	     , "02"); 												/* 사례관리구분코드 CASE_MNG_SE_CD */
			 
			 // @TODO 대상자등록화면 필수
			 // 보건복지부 소관 기타 기관·시설
			 saveMap.put("RCPT_RQST_COURS_SE_CD" , "02030118"); 		/* 접수의뢰경로구분코드 RCPT_RQST_COURS_SE_CD */
			 saveMap.put("FAM_SHAPE_SE_CD" 		 , "99"); 				/* 가족구성형태 FAM_SHAPE_SE_CD*/
			 saveMap.put("FAM_SHAPE_ETC_CN"   	 , "복지부연계대상자"); /* 가족구성형태 기타내용 FAM_SHAPE_ETC_CN*/
			 saveMap.put("RESIDE_SHAPE_SE_CD"    , "99"); 				/* 주거형태 RESIDE_SHAPE_SE_CD*/
			 saveMap.put("RESIDE_SHAPE_ETC_CN"   , "복지부연계대상자"); /* 주거형태 기타내용 RESIDE_SHAPE_ETC_CN*/
			 saveMap.put("PBLAST_SE_CD" 		 , "05"); 				/* 사회보장구분 PBLAST_SE_CD*/
			 
			 saveMap.put("SESS_USER_ID"	         , sUserId); 			/* 최초등록자아이디 FRST_RGTR_ID, 최초수정자아이디 LAST_MDFR_ID */
			 
			 /* 대상자 연계추가데이터값1 ~ 10 등록*/
			 saveMap.put("LINK_ADDTNG_DATAA_VALUE1"  , saveMap.get("SRV_RQST_PRCS_ID"));	/* SRV_RQST_PRCS_ID*/			 
			 
			 // 60081 : 서비스신청대기 , 서비스신청대기 60082
			 if ("60081".equals(sRqstPrsltDtlCd) || "60082".equals(sRqstPrsltDtlCd)) {
				 
				LOGGER.debug("대상자 저장.saveTrprInqDetail2=[" + saveMap + "]");
				 
				 /* 문제상태및원인 : SEB130 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE2"  , saveMap.get("PROBM_STTS_LCLAS_SE_CD"));	/* 문제상태 대분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE3"  , saveMap.get("PROBM_STTS_MLSFC_SE_CD"));	/* 문제상태 중분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE4"  , saveMap.get("PROBM_STTS_SCLAS_SE_CD"));	/* 문제상태 소분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE5"  , saveMap.get("PROBM_CAS_LCLAS_SE_CD"));	/* 문제원인 대분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE6"  , saveMap.get("PROBM_CAS_SCLAS_SE_CD"));	/* 문제원인 소분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE7"  , saveMap.get("PROBM_CAS_ETC_CN"));		/* 문제원인 기타내용 */
				 /* 청소년상태구분 : SEB100 */ 
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE8"  , saveMap.get("YNGBGS_STTS_LCLAS_SE_CD"));	/* 청소년상태구분 */
				 /* 서비스제공 SEB500*/
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE9"  , saveMap.get("SRV_PVSN_CYC_NM"));		/* 서비스제공주기명 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE10" , saveMap.get("SRV_PVSN_TMCNT"));		/* 서비스제공횟수 */				
			
				// 60083 : 서비스신청반려 
			 }else if ("60083".equals(sRqstPrsltDtlCd)){
				 /* 사례관리구분코드=[사례대상자미신청 : 01, 사례대상자신청(대기상태) : 02, 사례대상자미선정 : 03, 사례대상자선정 : 04] */
				 // 사례관리구분코드 CASE_MNG_SE_CD (반려시 : 01로 업데이트)
				 saveMap.put("CASE_MNG_SE_CD"	     		 , "01"); 												/* 사례관리구분코드 CASE_MNG_SE_CD */
				 saveMap.put("CASE_TRPR_NOAP_CS_SE_CD"	     , "99"); 										/* 사례대상자미신청사유구분코드 CASE_TRPR_NOAP_CS_SE_CD */
				 saveMap.put("CASE_TRPR_UNSL_CS_CN"	     	 , saveMap.get("PRCS_INST_SRV_RQST_RJCT_RSN_NM")); 		/* 사례대상자미신청사유내용 CASE_TRPR_UNSL_CS_CN */				 
//				 saveMap.put("CASE_TRPR_UNSL_CS_CN"	     	 , "복지부연계 대기 취소건"); 					/* 사례대상자미신청사유내용 CASE_TRPR_UNSL_CS_CN */				 
			}
			 
			 /* 2023-01-17 주민번호가 있는경우 개인식별번호 채번*/
			 String sTrprRrno = String.valueOf(saveMap.get("TRPR_RRNO"));
			 LOGGER.debug("===== 복지부연계 주민 =====" + (sTrprRrno));
			 if( ! "".equals(sTrprRrno) || ! "null".equals(sTrprRrno) && sTrprRrno.length() == 13) {
//				 LOGGER.debug("===== 복지부연계 주민등록번호 =====");
				 
				 String sIndbIdntfcNo = "";
				 Map<String, Object> infoMap = new HashMap<>();
				 
				 infoMap.put("TRPR_NM"		  , saveMap.get("TRPR_FLNM"));						
				 infoMap.put("TRPR_BRTH_YMD"  , saveMap.get("TRPR_BRDT"));						
				 infoMap.put("SXDC_SE_CD"	  , saveMap.get("SXDC_SE_CD"));				
				 infoMap.put("RRNO"			  , saveMap.get("TRPR_RRNO"));		
				 infoMap.put("WRD_TELNO"	  , saveMap.get("TRPR_TELNO")); 
				 infoMap.put("MBL_TELNO"	  , saveMap.get("TRPR_MPNO")); 
				 infoMap.put("ZIP"			  , saveMap.get("TRPR_RDNM_ZIP")); 
				 infoMap.put("PST_ADDR"		  , saveMap.get("TRPR_RDNMADR")); 
				 infoMap.put("DADDR"		  , saveMap.get("TRPR_RDNM_DTLADR")); 
				 
				 sIndbIdntfcNo = trprInqService.setPersonal(request, infoMap);
				 
				 saveMap.put("INDV_IDNTFC_NO", sIndbIdntfcNo);
			 }
			 
			 // 대상자 등록
			 trprInqMapper.insertTrprInqDetail(saveMap);
			 
		 }else if(sType == "U" || sType == "u") {
			 
			/* 사례대상자유형구분코드=[발굴대상자 : 01, 기관의뢰대상자 : 02, 병무청연계대상자 : 03, 복지부연계대상자 : 04] */
			saveMap.put("CASE_TRPR_TYPE_SE_CD"  , "04"); 												/* 사례대상자유형구분코드 CASE_TRPR_TYPE_SE_CD */
			// @TODO 대상자등록화면 필수
			// 보건복지부 소관 기타 기관·시설
			saveMap.put("RCPT_RQST_COURS_SE_CD"	 , "02030118"); /* 접수의뢰경로구분코드 RCPT_RQST_COURS_SE_CD */
			saveMap.put("FAM_SHAPE_SE_CD"		 , "99"); /* 가족구성형태 FAM_SHAPE_SE_CD */
			saveMap.put("FAM_SHAPE_ETC_CN"		 , "복지부연계대상자"); /* 가족구성형태 기타내용 FAM_SHAPE_ETC_CN */
			saveMap.put("RESIDE_SHAPE_SE_CD"	 , "99"); /* 주거형태 RESIDE_SHAPE_SE_CD */
			saveMap.put("RESIDE_SHAPE_ETC_CN"	 , "복지부연계대상자"); /* 주거형태 기타내용 RESIDE_SHAPE_ETC_CN */
			saveMap.put("PBLAST_SE_CD"			 , "05"); /* 사회보장구분 PBLAST_SE_CD */				 
			 
			// 반려일경우 연계서비스를 의뢰하지 않기 때문에 사례관리구분코드 CASE_MNG_SE_CD ( 사례대상자미신청 : 01 ) 수정
			// 서비스의뢰처리구분코드(반려 : 002) 
			if ("60083".equals(sRqstPrsltDtlCd) || "60083" == sRqstPrsltDtlCd){
				
				LOGGER.debug("대상자 저장.saveTrprInqDetail60083=[" + saveMap + "]");				
				
//				Map<String, String> getMap = new HashMap<>();
				/* 대상자 연계추가데이터값1 ~ 10*/
				String sSrvRqstPrcsId = String.valueOf(saveMap.get("SRV_RQST_PRCS_ID"));							/* 서비스의뢰처리ID*/
				LOGGER.debug("서비스의뢰처리ID 대상자조회=[" + sSrvRqstPrcsId + "]");
				// 연계시퀀스로 대상자번호 조회 
				getMap = linkMohwSrvcRqstMapper.selectTrprInfoNo(sSrvRqstPrcsId);							/* 대상자번호*/				
				
				/*
				 * 1) 대상자 SEA200 수정 
				- 사례관리구분코드 ( CASE_MNG_SE_CD ) => 사례대상자미신청 : 01
				- 사례대상자미신청사유구분코드 ( CASE_TRPR_NOAP_CS_SE_CD ) => 기타 : 99
				- 사례대상자미선정사유내용 ( CASE_TRPR_UNSL_CS_CN ) => 
					처리기관서비스의뢰반려사유코드 ( PRCS_INST_SRV_RQST_RJCT_RSN_CD )의 label명으로 입력
				 */
				saveMap.put("CASE_MNG_SE_CD"	        , String.valueOf("01")); 						/* 사례관리구분코드 CASE_MNG_SE_CD */			 
				saveMap.put("CASE_TRPR_NOAP_CS_SE_CD"	, String.valueOf("99")); 					
				saveMap.put("CASE_TRPR_UNSL_CS_CN"		, saveMap.get("PRCS_INST_SRV_RQST_RJCT_RSN_NM")); 		/* 사례대상자미선정사유내용 CASE_TRPR_UNSL_CS_CN */
				
				saveMap.put("TRPR_INFO_NO"				, getMap.get("TRPR_INFO_NO")); 					/* 대상자번호 TRPR_INFO_NO*/
				saveMap.put("TRPR_NM"				    , getMap.get("TRPR_NM"));					/* 성명*/
				saveMap.put("MBL_TELNO"				    , getMap.get("MBL_TELNO"));					/* 휴대전화번호*/
				saveMap.put("TRPR_BRTH_YMD"				, getMap.get("TRPR_BRTH_YMD")); 				/* 생년월일*/
				saveMap.put("ZIP"				        , getMap.get("ZIP")); 						/* 대상자우편번호 ZIP*/
				saveMap.put("PST_ADDR"			        , getMap.get("PST_ADDR")); 						/* 대상자도로명주소 PST_ADDR*/
				saveMap.put("DADDR"				        , getMap.get("DADDR")); 					/* 대상자도로명상세주소 DADDR*/				
				saveMap.put("UNT_TASKWK_SE_CD"			, getMap.get("UNT_TASKWK_SE_CD")); 				/* 단위업무구분코드*/
				saveMap.put("SXDC_SE_CD"				, getMap.get("SXDC_SE_CD")); 					/* 성별*/
				saveMap.put("RCPT_RQST_COURS_SE_CD"		, getMap.get("RCPT_RQST_COURS_SE_CD")); 		/* 의뢰경로*/
				
				/* 대상자 연계추가데이터값1 ~ 10 등록*/
//				 saveMap.put("LINK_ADDTNG_DATAA_VALUE1"  , "");	/* 연계시퀀스*/
				 /* 문제상태및원인 : SEB130 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE2"  , "");	/* 문제상태 대분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE3"  , "");	/* 문제상태 중분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE4"  , "");	/* 문제상태 소분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE5"  , "");	/* 문제원인 대분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE6"  , "");	/* 문제원인 소분류 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE7"  , "");	/* 문제원인 기타내용 */
				 /* 청소년상태구분 : SEB100 */ 
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE8"  , "");	/* 청소년상태구분 */
				 /* 서비스제공 SEB500*/
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE9"  , "");	/* 제공시작시간 */
				 saveMap.put("LINK_ADDTNG_DATAA_VALUE10" , "");	/* 제공종료시간 */						
				
				 saveMap.put("SESS_USER_ID"	         , sUserId); 											/* 최초등록자아이디 FRST_RGTR_ID, 최초수정자아이디 LAST_MDFR_ID */				
				
				 
				 LOGGER.debug("updateTrprInqDetail.saveMap1=[" + saveMap + "]");
				 
				 trprInqMapper.updateTrprInqDetail(saveMap);
			
			// 서비스의뢰처리구분코드(결정 : 001) 	
			}else if("60081".equals(sRqstPrsltDtlCd) || "60081" == sRqstPrsltDtlCd || "60082".equals(sRqstPrsltDtlCd) || "60082" == sRqstPrsltDtlCd) {
				
				LOGGER.debug("대상자 저장.saveTrprInqDetail4=[" + saveMap + "]");	
				
				 /* 사례대상자유형구분코드=[발굴대상자 : 01, 기관의뢰대상자 : 02, 병무청연계대상자 : 03, 복지부연계대상자 : 04] */
				 saveMap.put("CASE_TRPR_TYPE_SE_CD"  , "04"); 												/* 사례대상자유형구분코드 CASE_TRPR_TYPE_SE_CD */
				 /* 사례관리구분코드=[사례대상자미신청 : 01, 사례대상자신청(대기상태) : 02, 사례대상자미선정 : 03, 사례대상자선정 : 04] */
				 // 사례관리구분코드 CASE_MNG_SE_CD (연계상태성공 후 배치돌고 성공하면 사례기본 업데이트 후 사례대상자선정 : 04로 업데이트)
				 saveMap.put("CASE_MNG_SE_CD"	     , "02"); 												/* 사례관리구분코드 CASE_MNG_SE_CD */				
				
				 //				Map<String, String> getMap = new HashMap<>();
				/* 대상자 연계추가데이터값1 ~ 10*/
				String sSrvRqstPrcsId = String.valueOf(saveMap.get("SRV_RQST_PRCS_ID"));							/* 서비스의뢰처리ID*/
				LOGGER.debug("서비스의뢰처리ID 대상자조회=[" + sSrvRqstPrcsId + "]");
				// 연계시퀀스로 대상자번호 조회 
				getMap = linkMohwSrvcRqstMapper.selectTrprInfoNo(sSrvRqstPrcsId);							/* 대상자번호*/
				
				// 서비스의뢰처리구분코드(반려 : 002) 최초선택 후 (결정 : 001)로 변경시 대상자 정보 생성
				if (getMap == null) {
					 // 대상자번호 채번
					 String sTrprInfoNo = selectRenuNo(sUserId, "TR");
					 saveMap.put("TRPR_INFO_NO"		    , sTrprInfoNo); 										/* 대상자번호 TRPR_INFO_NO*/
						/* 단위업무구분코드 UNT_TASKWK_SE_CD*/					
					saveMap.put("TRPR_NM"		    , saveMap.get("TRPR_FLNM")); 			/* 대상자명암호화 TRPR_NM_ENCPT*/
					/* 주민등록번호 TRPR_RRNO*/
					/* 성별구분코드 SXDC_SE_CD*/
					saveMap.put("TRPR_BRTH_YMD"		, saveMap.get("TRPR_BRDT")); 							/* 대상자출생일자 TRPR_BRTH_YMD*/
					saveMap.put("SXDC_SE_CD"		, saveMap.get("SXDC_SE_CD")); 							/* 대상자출생일자 TRPR_BRTH_YMD*/
					saveMap.put("ZIP"				, saveMap.get("TRPR_RDNM_ZIP ")); 						/* 대상자우편번호 ZIP*/
					saveMap.put("PST_ADDR"			, saveMap.get("TRPR_RDNMADR")); 						/* 대상자도로명주소 PST_ADDR*/
					saveMap.put("DADDR"				, saveMap.get("TRPR_RDNM_DTLADR")); 					/* 대상자도로명상세주소 DADDR*/
					saveMap.put("PRVC_PVSN_AGRE_YN"	, saveMap.get("TRPR_PRVC_PVAGR_YN")); 					/* 개인정보제공동의여부 TRPR_PRVC_PVAGR_YN*/
					
					// 연락처 정보가 없는경우 연락처미입력 사유 입력 (대상자 등록시)
					/* 개인식별정보 미입력사유내용 INDV_IDNTFC_INFO_UNIPT_CS_CN*/
					String sTrprTelno = saveMap.get("TRPR_TELNO");												/* 대상자전화번호 TRPR_TELNO*/
					String sMblTelno  = saveMap.get("TRPR_MPNO");												/* 대상자휴대전화번호 MBL_TELNO_ENCPT */
					if (sTrprTelno == null || "".equals(sTrprTelno) && sMblTelno == null || "".equals(sTrprTelno)) {
					saveMap.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "복지부연계대상자로 연락처정보 미입력");
					}else if(sTrprTelno != null || ! "".equals(sTrprTelno) && sMblTelno != null || ! "".equals(sMblTelno)) {
					saveMap.put("TRPR_TELNO", sTrprTelno);
					saveMap.put("MBL_TELNO"      , sMblTelno);
					}
					
//					/* 사례대상자유형구분코드=[발굴대상자 : 01, 기관의뢰대상자 : 02, 병무청연계대상자 : 03, 복지부연계대상자 : 04] */
//					saveMap.put("CASE_TRPR_TYPE_SE_CD"  , "04"); 												/* 사례대상자유형구분코드 CASE_TRPR_TYPE_SE_CD */
//					// @TODO 대상자등록화면 필수
//					// 보건복지부 소관 기타 기관·시설
//					saveMap.put("RCPT_RQST_COURS_SE_CD"	 , "02030118"); /* 접수의뢰경로구분코드 RCPT_RQST_COURS_SE_CD */
//					saveMap.put("FAM_SHAPE_SE_CD"		 , "99"); /* 가족구성형태 FAM_SHAPE_SE_CD */
//					saveMap.put("FAM_SHAPE_ETC_CN"		 , "복지부연계대상자"); /* 가족구성형태 기타내용 FAM_SHAPE_ETC_CN */
//					saveMap.put("RESIDE_SHAPE_SE_CD"	 , "99"); /* 주거형태 RESIDE_SHAPE_SE_CD */
//					saveMap.put("RESIDE_SHAPE_ETC_CN"	 , "복지부연계대상자"); /* 주거형태 기타내용 RESIDE_SHAPE_ETC_CN */
//					saveMap.put("PBLAST_SE_CD"			 , "05"); /* 사회보장구분 PBLAST_SE_CD */				
					
					/* 대상자 연계추가데이터값1 ~ 10 등록*/
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE1"  , String.valueOf(saveMap.get("SRV_RQST_PRCS_ID")));	/* SRV_RQST_PRCS_ID*/
					 /* 문제상태및원인 : SEB130 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE2"  , saveMap.get("PROBM_STTS_LCLAS_SE_CD"));	/* 문제상태 대분류 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE3"  , saveMap.get("PROBM_STTS_MLSFC_SE_CD"));	/* 문제상태 중분류 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE4"  , saveMap.get("PROBM_STTS_SCLAS_SE_CD"));	/* 문제상태 소분류 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE5"  , saveMap.get("PROBM_CAS_LCLAS_SE_CD"));	/* 문제원인 대분류 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE6"  , saveMap.get("PROBM_CAS_SCLAS_SE_CD"));	/* 문제원인 소분류 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE7"  , saveMap.get("PROBM_CAS_ETC_CN"));		/* 문제원인 기타내용 */
					 /* 청소년상태구분 : SEB100 */ 
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE8"  , saveMap.get("YNGBGS_STTS_LCLAS_SE_CD"));	/* 청소년상태구분 */
					 /* 서비스제공 SEB500*/
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE9"  , saveMap.get("SRV_PVSN_CYC_NM"));		/* 서비스제공주기명 */
					 saveMap.put("LINK_ADDTNG_DATAA_VALUE10" , saveMap.get("SRV_PVSN_TMCNT"));		/* 서비스제공횟수 */						
					
					 saveMap.put("SESS_USER_ID"	         , sUserId); 											/* 최초등록자아이디 FRST_RGTR_ID, 최초수정자아이디 LAST_MDFR_ID */
					 // 대상자 등록
					 trprInqMapper.insertTrprInqDetail(saveMap);
				
				// 서비스의뢰처리구분코드(결정 : 001) 최처선택 후 수정시 대상자정보수정	
				}else if (getMap != null) {
					saveMap.put("TRPR_INFO_NO"  		    , getMap.get("TRPR_INFO_NO"));						/* 대상자번호 */
					saveMap.put("TRPR_NM"  		            , getMap.get("TRPR_NM"));						/* 대상자명암호화 TRPR_NM_ENCPT */
					saveMap.put("MBL_TELNO"				    , getMap.get("MBL_TELNO"));					/* 휴대전화번호*/					
					saveMap.put("TRPR_BRTH_YMD"  		    , getMap.get("TRPR_BRTH_YMD"));						/* 대상자출생일자 TRPR_BRTH_YMD */
					saveMap.put("SXDC_SE_CD"  		    	, getMap.get("SXDC_SE_CD"));						/* 성별구분코드 SXDC_SE_CD*/
					saveMap.put("ZIP"				        , getMap.get("ZIP")); 						/* 대상자우편번호 ZIP*/
					saveMap.put("PST_ADDR"			        , getMap.get("PST_ADDR")); 						/* 대상자도로명주소 PST_ADDR*/
					saveMap.put("DADDR"				        , getMap.get("DADDR")); 					/* 대상자도로명상세주소 DADDR*/						
					saveMap.put("UNT_TASKWK_SE_CD"  		, getMap.get("UNT_TASKWK_SE_CD"));					/* 단위업무구분코드 UNT_TASKWK_SE_CD*/
					
					/* 문제상태및원인 : SEB130 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE2"  , saveMap.get("PROBM_STTS_LCLAS_SE_CD"));	/* 문제상태 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE3"  , saveMap.get("PROBM_STTS_MLSFC_SE_CD"));	/* 문제상태 중분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE4"  , saveMap.get("PROBM_STTS_SCLAS_SE_CD"));	/* 문제상태 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE5"  , saveMap.get("PROBM_CAS_LCLAS_SE_CD"));	/* 문제원인 대분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE6"  , saveMap.get("PROBM_CAS_SCLAS_SE_CD"));	/* 문제원인 소분류 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE7"  , saveMap.get("PROBM_CAS_ETC_CN"));			/* 문제원인 기타내용 */
					/* 청소년상태구분 : SEB100 */ 
					saveMap.put("LINK_ADDTNG_DATAA_VALUE8"  , saveMap.get("YNGBGS_STTS_LCLAS_SE_CD"));	/* 청소년상태구분 */
					/* 서비스제공 SEB500*/
					saveMap.put("LINK_ADDTNG_DATAA_VALUE9"  , saveMap.get("SRV_PVSN_CYC_NM"));		/* 서비스제공주기명 */
					saveMap.put("LINK_ADDTNG_DATAA_VALUE10" , saveMap.get("SRV_PVSN_TMCNT"));			/* 서비스제공횟수 */	
					
					saveMap.put("SESS_USER_ID"	         , sUserId); 										/* 최초수정자아이디 LAST_MDFR_ID*/
					
					 LOGGER.debug("서비스의뢰처리 대상자처리2=[" + saveMap + "]");					
					trprInqMapper.updateTrprInqDetail(saveMap);			 
				}
			}
		 }
		 
		// 연계추가값에서 연계시퀀스 값으로 대상자번호를 가져와서 수정해야함 (컬럼 추가되면 조회추가하여 대상자번호 가져와서 이력등록 주석제거
		// 대상자 이력등록
		trprInqMapper.insertTrprInqHistory(saveMap);		
		
		LOGGER.debug("========== 대상자 저장 End ==========");			
		return saveMap;
	}
	
		
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 목록
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstDmndList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
			ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
			Map<String, String> paramMap = paramGroup.getSingleValueMap();
	
			HttpSession session   = request.getSession();
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		

			/*20230126_강화영_권한 적용_시작*/
			paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
			
			Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap.forEach((StrKey, StrValue) ->{
				paramMap2.put(StrKey, StrValue);
			});	
			paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//			paramMap2.put("checkAll", comMap.get("checkAll"));
			paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
			paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
			
		return linkMohwSrvcRqstMapper.selectMohwSrvcRqstDmndList(paramMap2);
	}

	/**
	 * @Method명   : selectMohwSrvcRqstDmndInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청정보 조회
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstDmndInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 의뢰요청 정보가 없습니다.");
		}
		LOGGER.debug("========== 복지부 서비스의뢰 요청정보 조회 START ==========");
		LOGGER.debug("selectMohwSrvcRqstDmndInfo.paramGroup=[" + paramGroup + "]");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

//		paramMap.get("SRV_CNSRQ_ID"); 	  /* 서비스상담의뢰ID */
//		paramMap.get("ESB_SEQ"); 	  	  /* 연계시퀀스 */
//		paramMap.get("TRPR_INFO_NO"); 	  /* 대상자번호 */

		List<Map<String, Object>> retList = new ArrayList<>();
		retList = linkMohwSrvcRqstMapper.selectMohwSrvcRqstDmndInfo(paramMap);    /* 서비스의뢰요청 정보 조회 */
		
		// 전화번호 포멧팅
		for (int idx = 0; idx < retList.size(); idx++) {
			String sRqstInstPicTelNo = String.valueOf(retList.get(idx).get("RQST_INST_PIC_TELNO"));
			String sRqstInstPicMpno  = String.valueOf(retList.get(idx).get("RQST_INST_PIC_MPNO"));
			String sTrprTelno 		 = String.valueOf(retList.get(idx).get("TRPR_TELNO"));
			String sTrprMpno         = String.valueOf(retList.get(idx).get("TRPR_MPNO"));
			
			if (! "".equals(sRqstInstPicTelNo) || "null".equals(sRqstInstPicTelNo)) retList.get(idx).put("RQST_INST_PIC_TELNO", Formatter.phoneFormat(sRqstInstPicTelNo, 1));
			if (! "".equals(sRqstInstPicMpno)  || "null".equals(sRqstInstPicMpno))  retList.get(idx).put("RQST_INST_PIC_MPNO" , Formatter.phoneFormat(sRqstInstPicMpno, 1));
			if (! "".equals(sTrprTelno)  	   || "null".equals(sTrprTelno)) retList.get(idx).put("TRPR_TELNO"		  , Formatter.phoneFormat(sTrprTelno, 1));
			if (! "".equals(sTrprMpno)  	   || "null".equals(sTrprMpno)) retList.get(idx).put("TRPR_MPNO"		  , Formatter.phoneFormat(sTrprMpno, 1));
		}			

		/* 서비스의뢰요청 연계상태가 “Y”이면 수정이 불가능 함 */
		LOGGER.debug("========== 복지부 서비스의뢰요청정보 조회 retList" + retList + "==========");
		
		return retList;		
	}
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndInfoResultList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청결과목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectMohwSrvcRqstDmndInfoResultList(HttpServletRequest request,DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 서비스의뢰접수정보결과가 없습니다.");
		}
		LOGGER.debug("========== 복지부 서비스의뢰요청결과정보 목록 START ==========");
		LOGGER.debug("서비스의뢰요청결과정보.paramGroup=[" + paramGroup + "]");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		paramMap.get("ESB_SEQ");	   /* 연계시퀀스*/
		paramMap.get("SRV_CNSRQ_ID"); /* 서비스상담의뢰ID*/

		List<Map<String, Object>> retList = new ArrayList<>();
		retList = linkMohwSrvcRqstMapper.selectMohwSrvcRqstDmndInfoResultList(paramMap); /* 서비스의뢰요청결과정보 조회 Mapper */

		LOGGER.debug("========== 복지부 서비스의뢰요청결과목록 retList + " + retList + "==========");

		return retList;		
	}

	/**
	 * @Method명   : processMohwSrvcRqstDmnd
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 처리
	 */
	@Override
	public Map<String, Object> processMohwSrvcRqstDmnd(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		int udtCnt = 0; // 수정
		int intCnt = 0; // 저장

		ParameterGroup dsCAB120Save = dataRequest.getParameterGroup("dsCAB120Save");

		LOGGER.debug("========== 복지부 서비스의뢰요청 START ==========");
		LOGGER.debug("processMohwSrvcRqstDmnd.dsCAB120Save=[" + dsCAB120Save + "]");

		if (dsCAB120Save == null) {
			throw new AppWorksException("서비스의뢰요청을 처리할 정보가 없습니다.");
		}
		
		// 키값 저장
		Map<String, Object> retMap = new HashMap<>();			/* 화면 재조회용 returnMap*/
		retMap.put("SRV_CNSRQ_ID", dsCAB120Save.getValue("SRV_CNSRQ_ID"));
		retMap.put("ESB_SEQ", dsCAB120Save.getValue("ESB_SEQ"));
		
		// 세션정보
		String sUserId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}		
		
		// 저장, 수정, 삭제 시작
		Iterator<ParameterRow> insertedRows = dsCAB120Save.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = dsCAB120Save.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = dsCAB120Save.getDeletedRows();
		
		while (insertedRows.hasNext()) {
			String sts = "I";
			Map<String, String> mapIns 	      = insertedRows.next().toMap();
			
			// 서비스의뢰신청유형코드 ( 대상자의뢰 : 001, 서비스의뢰: 002 )
			String sSrvRqstAplyTcd = mapIns.get("SRV_RQST_APLY_TCD");
			if (sSrvRqstAplyTcd.isEmpty()) {
				throw new AppWorksException("의뢰신청유형 선택되지 않았습니다.\n확인 후 다시 저장 바랍니다.");
			}
			
			/* 화면에서 입력받은 서비스의뢰요청 정보 */
			/* 1. 서비스의뢰연계구분코드 SRV_RQST_LINK_DCD*/
			/* 2. 서비스의뢰처리구분코드 SRV_RQST_APLY_DCD*/
			/* 3. 서비스의뢰신청유형코드 SRV_RQST_APLY_TCD*/
			String sSrvCnsrqId = selectRenuNo(sUserId, "SV");
			mapIns.put("SRV_CNSRQ_ID", sSrvCnsrqId);			
			/* 5. 의뢰기관유형상세코드 RQST_INST_TYP_DTL_CD*/
			/* 6. 의뢰기관유형상세명  RQST_INST_TYP_DTL_NM*/
			/* 7. 의뢰서비스기관ID  RQST_SRV_INST_ID*/
			/* 8. 의뢰서비스기관명  RQST_SRV_INST_NM*/
			/* 9. 상담일자 DSCSN_YMD*/
			/* 10. 의뢰일자	RQST_YMD*/
			/* 11. 의뢰기관담당자성명	RQST_INST_PIC_FLNM*/
			/* 12. 의뢰기관담당자전화번호	RQST_INST_PIC_TELNO*/
			/* 13. 의뢰기관담당자휴대전화번호	RQST_INST_PIC_MPNO*/
			/* 14. 의뢰인상담소견내용	CLNT_DSCSN_OPIN_CN*/
			/* 15. 의뢰기관담당자특화내용	RQST_INST_PIC_SPCL_CN*/
			/* 16. 의뢰기관담당자알림사용여부	RQST_INST_PIC_NTCE_USE_YN*/
			/* 17. 대상자성명	TRPR_FLNM*/
			/* 18. 대상자주민등록번호	TRPR_RRNO*/
			/* 19. 대상자생년월일	TRPR_BRDT
			/* 20. 대상자주민등록번호성별코드	TRPR_RRNO_SEX_CD*/
			
			/* 2023-03-06 3. 대상자주민등록번호성별코드	F, M 으로 들어가있는데, 요청 코드가 따로있다. 성별코드 0 - 미기재 1 - 남성 2 - 여성 3 - 성별미상 9 - 기타*/
			;
			String sTrprSxdcSeCd = String.valueOf(mapIns.get("TRPR_RRNO_SEX_CD"));
			
			switch (sTrprSxdcSeCd) {
			case "M":
				mapIns.put("TRPR_RRNO_SEX_CD", "1");
				break;
			case "F":
				mapIns.put("TRPR_RRNO_SEX_CD", "2");
				break;
			case "X":
				mapIns.put("TRPR_RRNO_SEX_CD", "3");
				break;
			default:
				mapIns.put("TRPR_RRNO_SEX_CD", "0");
				break;
			}
			
			
			/* 21. 대상자관리읍면동부서코드	TRPR_MGEMD_DEPT_CD*/
			/* 22. 대상자전화번호	TRPR_TELNO*/
			/* 23. 대상자휴대전화번호	TRPR_MPNO*/
			/* 24. 대상자도로명우편번호	TRPR_RDNM_ZIP*/
			/* 25. 대상자도로명주소	TRPR_RDNMADR*/
			/* 26. 대상자도로명상세주소	TRPR_RDNM_DTLADR*/
			/* 27. 희망지원코드목록	HOPE_SPRT_CD_LST*/
			/* 28. 희망지원기타내용	HOPE_SPRT_ETC_CN*/
			/* 29. 대상자개인정보제공동의여부	TRPR_PRVC_PVAGR_YN*/
			/* 30. 대상자종합장애정도코드	TRPR_OVL_DSDGR_CD*/
			/* 31. 처리의뢰기관유형상세코드	PRCS_RQST_INST_TYP_DTL_CD*/
			/* 32. 의뢰처리서비스기관ID	RQST_PRCS_SRV_INST_ID*/
			/* 33  의뢰처리서비스기관명	RQST_PRCS_SRV_INST_NM*/
			/* 34. 의뢰원천기관자원제공서비스ID RQST_SRC_INST_RSC_PRVSV_ID <- 변경됨 = 의뢰자원제공서비스ID	RQST_RSC_PRVSV_ID*/
			/* 35. 의뢰자원제공서비스명	RQST_RSC_PRVSV_NM*/
			/* 36. 의뢰신청서비스시작일자	RQST_APLY_SRV_BGNG_YMD*/
			/* 37. 의뢰신청서비스종료일자	RQST_APLY_SRV_END_YMD*/
			/* 38. 의뢰첨부파일명	RQST_ATCFL_NM*/
			
			/* (여가부 연계컬럼)*/
			/* 39. 트랜잭션아이디 ESB_TX_ID*/
			/* 40. 연계시퀸스 ESB_SEQ */
			mapIns.put("ESB_IF_ID", "INFIF_IR_SSI_FS_01");/* 41. 연계인터페이스ID ESB_IF_ID*/
			mapIns.put("SND_CD"   , SND_CD); /* 42. 송신기관코드ID SND_CD*/
			mapIns.put("RCV_CD"   , RCV_CD); /* 43. 수신기관코드ID RCV_CD*/
			/* 44. 연계파일이름ID ESB_FILE_NAME*/
			/* 45. 데이터생성일시 CREATE_TIME*/
			/* 46. 연계시작일시 ESB_INIT_TIME*/
			/* 47. 연계종료일시 ESB_COMPLITE_TIME*/
			/*  저장 후에 “수정＂버튼으로 변경 연계상태가 “Y”이면 수정버튼을 클릭할 수 없음*/
			mapIns.put("ESB_STATUS", "N"); /* 48. 연계상태 ESB_STATUS ( DEFAUL  = N )*/

			/* 서비스의뢰접수결과 등록 */
			intCnt = linkMohwSrvcRqstMapper.insertMohwSrvcRqstDmnd(mapIns);
//			LOGGER.debug("===== mapIns =====");
//			LOGGER.debug("=====[" + mapIns);
			
			retMap.put("SRV_CNSRQ_ID", mapIns.get("SRV_CNSRQ_ID"));
			retMap.put("ESB_SEQ"     , mapIns.get("ESB_SEQ"));
			
			if(intCnt > 0) {
				
				Map<String, Object> getMap = null;
				/* 2023-05-18 일감/개발#463 연계의뢰 화면에서 대상자 정보를 조회하거나, 수정 할 수 있도록 하단에 "대상자정보"버튼 추가*/
				Map<String, String> chkMap = new HashMap<>();
				chkMap.put("TRPR_INFO_NO", mapIns.get("TRPR_INFO_NO"));
				
				getMap = trprInqMapper.selectTrprInqDetail(chkMap);
				
				/* 대상자확인*/
				if(getMap != null) {
					getMap.put("LINK_ADDTNG_DATAA_VALUE11", mapIns.get("SRV_CNSRQ_ID"));
					Map<String, String> saveMap = new HashMap<>();
					
					Iterator<String> it = getMap.keySet().iterator();	
					while(it.hasNext()) {
						String keys = it.next();
						
						saveMap.put(keys, String.valueOf( (getMap.get(keys) == null ? "" : getMap.get(keys)) ));	
					}
					saveMap.put("SESS_USER_ID", sUserId);
					
					LOGGER.debug("updateTrprInqDetail.saveMap2=[" + saveMap + "]");
					
					trprInqMapper.updateTrprInqDetail(saveMap);
					
					saveMap.put("DATAA_CHG_SE_CD", "U");
					trprInqMapper.insertTrprInqHistory(saveMap);
				}
			}
		}
		while (updatedRows.hasNext()) {
			String sts = "U";
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			udtCnt = linkMohwSrvcRqstMapper.updateMohwSrvcRqstDmnd(mapUpd);
			
			if(udtCnt > 0) {
				
//				Map<String, Object> getMap = null;
//				/* 2023-05-18 일감/개발#463 연계의뢰 화면에서 대상자 정보를 조회하거나, 수정 할 수 있도록 하단에 "대상자정보"버튼 추가*/
//				Map<String, String> chkMap = new HashMap<>();
//				chkMap.put("TRPR_INFO_NO", mapUpd.get("TRPR_INFO_NO"));
//				
//				getMap = trprInqMapper.selectTrprInqDetail(chkMap);
//				
//				/* 대상자확인*/
//				if(getMap != null) {
//					getMap.put("LINK_ADDTNG_DATAA_VALUE11", mapUpd.get("SRV_CNSRQ_ID"));
//					Map<String, String> saveMap = new HashMap<>();
//					
//					Iterator<String> it = getMap.keySet().iterator();	
//					while(it.hasNext()) {
//						String keys = it.next();
//						
//						saveMap.put(keys, String.valueOf( (getMap.get(keys) == null ? "" : getMap.get(keys)) ));	
//					}
//					saveMap.put("SESS_USER_ID", sUserId);
//					trprInqMapper.updateTrprInqDetail(saveMap);
//					
//					saveMap.put("DATAA_CHG_SE_CD", sts);
//					trprInqMapper.insertTrprInqHistory(saveMap);
//				}
			}			
		}
		while (deletedRows.hasNext()) {
			String sts = "D";
			Map<String, String> mapDel = deletedRows.next().toMap();
		}
		
		LOGGER.debug("========== 복지부 서비스의뢰요청 End ==========");		
		
		return retMap;
	}
	
	/**
	 * @Method명   : linkMohwCaseReg
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 복지부 서비스의뢰 접수 후 연계성공후 사례등록 배치
	 */
	@Override
	public void linkMohwCaseReg() throws Exception {
		
		LOGGER.debug("========== 복지부 연계 서비스의뢰접수 사례등록 Start ==========");
		String serverName = System.getProperty("SERVER"); // 서버명
		if (!"rybwas11".equals(serverName)) {
			return ;
		}
		
		Map<String, String> saveMap = new HashMap<>();
		
		List<Map<String, String>> trprList = new ArrayList<>();
		trprList = linkMohwSrvcRqstMapper.searchLinkTrprInfo();	/* 연계상태*/
		
		for (Map<String, String> map : trprList) {
			
			String sTrprInfoNo = String.valueOf(map.get("TRPR_INFO_NO"));

			Map<String, String> caseParamMap = new HashMap<>();
			Map<String, Object> caseMap      = new HashMap<>();
			caseParamMap.put("TRPR_INFO_NO", sTrprInfoNo);
			
			String sCaseMngNoChk    = "";	/* 사례관리번호*/
			String sCaseMngOdrnoChk = "";	/* 사례관리차수번호*/
			caseMap = linkMohwSrvcRqstMapper.searchLinkTrprCaseInfo(caseParamMap);	// 사례기본 확인 Mapper
			
			if (caseMap != null) {
				sCaseMngNoChk    = String.valueOf(caseMap.get("CASE_MNG_NO"));
				sCaseMngOdrnoChk = String.valueOf(caseMap.get("CASE_MNG_ODRNO"));
			}
			
			if (sCaseMngNoChk.isEmpty()) {
				
				// 사례기본 생성 : SEB100, 사례기본이력 : SEB101, 사례담당자 : SEB150 (사례화면 권한적용으로 인하여 insert)
				String sCaseMngNo = selectRenuNo("BATH", "CS"); /* 사례관리번호 채번 */
				
				/* 사례기본 SEB100*/
				saveMap.put("CASE_MNG_NO"			  , sCaseMngNo); 								/* 사례관리번호 CASE_MNG_NO*/
				saveMap.put("CASE_MNG_ODRNO"		  , "1"); 										/* 사례관리차수 CASE_MNG_ODRNO*/
				saveMap.put("TRPR_INFO_NO"	          , map.get("TRPR_INFO_NO"));					/* 대상자정보번호 TRPR_INFO_NO*/
				saveMap.put("CASE_BGNG_YMD"			  , DateUtil.getToday()); 						/* 사례시작일자 CASE_BGNG_YMD*/
				saveMap.put("UNT_TASKWK_SE_CD"	      , map.get("UNT_TASKWK_SE_CD"));				/* 단위업무구분코드 UNT_TASKWK_SE_CD*/
				saveMap.put("CASE_PIC_NO" 			  , map.get("RCPT_PIC_NO"));					/* 사례담당자번호 CASE_PIC_NO*/
				saveMap.put("NEW_CASE_PIC_NO" 		  , map.get("RCPT_PIC_NO"));					/* 사례담당자번호 CASE_PIC_NO*/
				saveMap.put("PIC_INST_NO" 			  , String.valueOf(map.get("RCPT_INST_NO")));	/* 담당자기관번호 PIC_INST_NO*/
				saveMap.put("YNGBGS_SE_NO" 			  , map.get("YNGBGS_STTS_LCLAS_SE_CD"));		/* 청소년구분번호 YNGBGS_SE_NO*/
				saveMap.put("CASE_PRGRS_STTS_SE_CD"   , "01"); 										/* 사례진행상태구분코드 CASE_PRGRS_STTS_SE_CD */
				saveMap.put("USER_ID"				  , "BATH"); 									/* 최초등록자아이디 FRST_RGTR_ID */
				
				// 이력테이블 데이터상태구분코드
				saveMap.put("DATAA_CHG_SE_CD", "I");		
				
				// 사례기본 등록
				caseRegMapper.insertSEB100Data(saveMap);
				// 사례기본 이력 등록
				caseRegMapper.insertSEB101Data(saveMap);
				
				// 사례관리 이력 
				/*
				2022-10-06 추가사항 
				사례기본T에서 사례진행상태구분코드(CASE_PRGRS_STTS_SE_CD) 변경시 
				사례관리이력T(SEB110)에 변경 이력이 insert 되어야 합니다.
				본인이 개발한 업무공통 화면이나 배치 중 혹시 사례진행상태구분코드 변경로직이 있다면 SEB110에 insert 되는 로직 추가를 요청드립니다.
				SEB110 테이블에서 사례관리이력번호(CASE_MNG_HSTR_NO)는 시퀀스로 입력하시면 됩니다.
				SEB110_SQ01 시퀀스 생성이 완료 되었으니 nextval로 추가 하시기 바랍니다.
				 */
				// 최초 사례관리 이력 등록시 사례등록 : 01 등록 이후 연계서비스 결정이되면서 제공자원서비스를 요청하기때문에 사례관리에서 서비스실행 등록으로 코드 추가 이력등록
				linkMohwSrvcRqstMapper.insertSEB110DataTEST(saveMap);
				
				// 문제상태및원인 SEB130, 문제상태및원인이력 SEB131
				String sProbmSttsCasNo = selectRenuNo("BATH", "PR"); 								/* 문제상태원인번호 채번 */
				saveMap.put("PROBM_STTS_CAS_NO"	      , sProbmSttsCasNo); 		/* 문제상태원인번호 PROBM_STTS_CAS_NO */
				
				saveMap.put("PROBM_STTS_LCLAS_SE_CD"  , map.get("PROBM_STTS_LCLAS_SE_CD"));		/* 문제상태대분류*/
				saveMap.put("PROBM_STTS_MLSFC_SE_CD"  , map.get("PROBM_STTS_MLSFC_SE_CD"));		/* 문제상태중분류*/
				saveMap.put("PROBM_STTS_SCLAS_SE_CD"  , map.get("PROBM_STTS_SCLAS_SE_CD"));		/* 문제상태소분류*/
				saveMap.put("PROBM_CAS_LCLAS_SE_CD"   , map.get("PROBM_CAS_LCLAS_SE_CD"));		/* 문제원인대분류*/
				saveMap.put("PROBM_CAS_SCLAS_SE_CD"   , map.get("PROBM_CAS_SCLAS_SE_CD"));		/* 문제원인소분류*/
				saveMap.put("PROBM_CAS_ETC_CN"        , map.get("PROBM_CAS_ETC_CN"));			/* 문제원인기타내용*/			
				
				// 문제상태및원인 등록
				caseRegMapper.insertCaseYngbgs(saveMap);
				// 문제상태및원인 이력 등록
				caseRegMapper.insertCaseYngbgsHstr(saveMap);					
				
				// 사례담당자 등록		(사례관리 권한적용으로 등록 필수)
				// 사례기본(SEB100) 생성시, 담당자를 사례담당자(SEB150,SEB151)테이블에 주 담당자로
				saveMap.put("PCHPRS_YN"			    , "Y");													/* 주담당자여부 PCHPRS_YN*/
				saveMap.put("PCHPRS_DSGN_YMD"		, DateUtil.getToday()); 								/* 주담당자지정일자 PCHPRS_DSGN_YMD*/		
				saveMap.put("PIC_DSGN_YMD"		    , DateUtil.getToday());									/* 담당자지정일자 PIC_DSGN_YMD*/
				
				saveMap.put("REG_AUTHRT_YN"   , "Y"); 		/* 등록권한여부*/
				saveMap.put("PLAN_AUTHRT_YN"  , "Y"); 		/* 계획권한여부*/
				saveMap.put("EXCN_AUTHRT_YN"  , "Y"); 		/* 실행권한여부*/
				saveMap.put("OUTC_AUTHRT_YN"  , "Y"); 		/* 성과권한여부*/
				saveMap.put("TRMN_AUTHRT_YN"  , "Y"); 		/* 종결권한여부*/
				saveMap.put("AFTFCT_AUTHRT_YN", "Y"); 		/* 사후권한여부*/
				
				// 사례담당자 등록
				caseRegMapper.insertSEB150Data(saveMap);
				// 사례담당자 이력 등록
				caseRegMapper.insertSEB151Data(saveMap);
				
				/* 서비스제공*/
				String sSrvcPvsnNo = selectRenuNo("BATH", "SR"); /* 서비스제공번호 채번 */
				saveMap.put("SRVC_PVSN_NO"			  , sSrvcPvsnNo);								/* 서비스제공번호 SRVC_PVSN_NO*/
				saveMap.put("RESRCE_NO"				  , map.get("PRCS_SRC_INST_RSC_PRVSV_ID"));		/* 자원번호 RESRCE_NO*/
				/* 서비스제공계획번호 SRVC_PVSN_PLAN_NO*/
				/* 서비스제공의뢰번호 SRVC_PVSN_RQST_NO*/
				
				// 서비스제공방법구분코드 ( 온라인 : 01, 오프라인 : 02, 전화 : 03, 온라인.오프라인.전화 병행 : 04, 화상상담 : 05 )
				saveMap.put("SRVC_PVSN_MTHD_SE_CD"    , "01");										/* 서비스제공방법구분코드 SRVC_PVSN_MTHD_SE_CD*/
				saveMap.put("SRVC_PVSN_TTL_NM"		  , map.get("PRCS_RSC_PRVSV_NM"));				/* 서비스제공제목명*/
				saveMap.put("SRVC_PVSN_CN"		      , map.get("PRCS_RSC_PRVSV_NM") + "/복지부연계");	/* 서비스제공내용 SRVC_PVSN_CN*/
				saveMap.put("SRVC_PVSN_BGNG_YMD"      , map.get("PRCS_INST_SRV_BGNG_YMD"));			/* 제공기간시작일자*/					 
				saveMap.put("SRVC_PVSN_END_YMD"       , map.get("PRCS_INST_SRV_END_YMD"));			/* 제공기간종료일자*/
				
				/* 2023-03-02 UI 제공시간 제공주기.횟수로 변경되어 주석처리*/
//				saveMap.put("SRVC_PVSN_BGNG_HR"       , map.get("LINK_ADDTNG_DATAA_VALUE9"));		/* 제공시작시간*/
//				saveMap.put("SRVC_PVSN_END_HR"        , map.get("LINK_ADDTNG_DATAA_VALUE10"));		/* 제공종료시간*/
				
				/* 서비스제공전일여부 SRVC_PVSN_WHDA_YN(제공시간여부에따라 'Y' OR 'N'*/
//			saveMap.put("SRVC_EXCN_BIZ_NO"		  , map.get("SRVC_EXCN_BIZ_NO"));				/* 서비스실행사업번호 SRVC_EXCN_BIZ_NO*/
				saveMap.put("PIC_NO"				  , map.get("RCPT_PIC_NO"));					/* 담당자번호 PIC_NO*/
				saveMap.put("TKCG_INST_NO"			  , String.valueOf(map.get("RCPT_INST_NO")));	/* 담당기관번호 TKCG_INST_NO(화면에서)*/
				saveMap.put("SRVC_GR_PVSN_YN", "N");												/* 서비스집단제공여부 SRVC_GR_PVSN_YN*/		
				
				// 서비스제공
				caseExcnMapper.insertSEB500Data(saveMap);
				// 서비스제공 이력
				caseExcnMapper.insertSEB501Data(saveMap);
				// 서비스제공 대상자 
				caseExcnMapper.saveSEB510Data(saveMap);
				// 서비스제공대상자 이력
				caseExcnMapper.insertSEB511Data(saveMap);
				
				// 최초 사례관리 이력 등록시 사례등록 : 01 등록 이후 연계서비스 결정이되면서 제공자원서비스를 요청하기때문에 사례관리에서 서비스실행 등록으로 코드 추가 이력등록			
				saveMap.put("CASE_PRGRS_STTS_SE_CD", "03");			/* 사례진행상태구분코드 CASE_PRGRS_STTS_SE_CD ( 서비스실행 : 03)*/
				linkMohwSrvcRqstMapper.insertSEB110DataTEST(saveMap);
				// 사례관리이력 등록 후 사례기본 사례진행상태구분코드
				caseRegMapper.updateSEB100Data(saveMap);
				// 사례기본이력 등록
				saveMap.put("DATAA_CHG_SE_CD", "U");
				
				/* 위 사례등록 후 이력등록시 수정일시 UNIQUE 발생으로 시간 조정*/
				Thread.sleep(1000);
				caseRegMapper.insertSEB101Data(saveMap);			
				
				// 배치후 대상자테이블 사례관리구분 수정
				/* 사례관리구분코드=[사례대상자미신청 : 01, 사례대상자신청(대기상태) : 02, 사례대상자미선정 : 03, 사례대상자선정 : 04] */
				// 사례관리구분코드 CASE_MNG_SE_CD (연계상태성공 후 배치돌고 성공하면 사례기본 업데이트 후 사례대상자선정 : 04로 업데이트)
				
				Map<String, String> trprMap = new HashMap<>();
				trprMap.put("TRPR_INFO_NO"			, map.get("TRPR_INFO_NO"));			/* 연계대상자 대상자번호 TRPR_INFO_NO*/
				trprMap.put("CASE_MNG_SE_CD"		, "04");							/* 사례관리구분코드 CASE_MNG_SE_CD*/
				trprMap.put("DATAA_CHG_SE_CD"		, "U");
				trprMap.put("USER_ID"		 		, "BATH");							/* 등록, 수정자아이디*/
				
				linkMohwSrvcRqstMapper.updateLinkTrprCaseMngSeCd(trprMap);
				// 대상자 이력등록
				caseRegMapper.insertSEA201Data(trprMap);
			}
			LOGGER.debug("========== 복지부 연계 서비스의뢰접수 사례등록 End ==========");			
		}
	}

	/**
	 * @Method명   : selectMohwWlfarResrce
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 복지부 실시간 연계_복지자원조회
	 */
	@Override
//	public List<Map<String, Object>> selectMohwWlfarResrce(HttpServletRequest request, DataRequest dataRequest) throws Exception {
	public Map<String, Object> selectMohwWlfarResrce(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup pageGroup  = dataRequest.getParameterGroup("dmPage");

		if (paramGroup == null) {
			throw new AppWorksException("조회할 복지자원 정보가 없습니다.");
		}
		LOGGER.debug("selectMohwWlfarResrce.paramGroup=[" + paramGroup + "]");
		LOGGER.debug("selectMohwWlfarResrce.pageGroup=[" + pageGroup + "]");

		LOGGER.debug("========== 복지부 실시간 연계_복지자원조회 START ==========");
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		// 세션정보
		int    instNo = 0;  //기관번호
//		String deptCd = ""; //부서코드
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			instNo = loginVO.getInstNo();
//			deptCd = loginVO.getDeptCd();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		Map<String, String> pageMap  = pageGroup .getSingleValueMap();

		//정보공유기관ID
		String sInfoSharInstId = "";
		sInfoSharInstId = paramMap.get("INFO_SHAR_INST_ID");
		if(sInfoSharInstId == null || "".equals(sInfoSharInstId)) {
			sInfoSharInstId = String.valueOf(instNo);
		}

		//기관시군구행정지역코드
		String sInstSggAdrgnCd = "";
		Map<String, Integer> cndMap = new HashMap<String, Integer>();
		cndMap.put("orgCode", Integer.parseInt(sInfoSharInstId));

		Map<String, Object> inqOrgMap = inqOrgListMapper.selectOrgDetail(cndMap);
		if(inqOrgMap != null) {
			sInstSggAdrgnCd = String.valueOf(inqOrgMap.get("SGG_LINK_PBADMS_RGN_CD"));
		}

		//욕구ID
		String sDsrId = "";
		if(paramMap.get("MOHW_DESIR_MLSFC_SE_CD") != null && !"".equals(paramMap.get("MOHW_DESIR_MLSFC_SE_CD"))) {
			sDsrId = paramMap.get("MOHW_DESIR_MLSFC_SE_CD");
		} else {
			sDsrId = paramMap.get("MOHW_DESIR_LCLAS_SE_CD");
		}

		//복지자원제공서비스분류ID
		String sWlrscPrvsvClsId = "";
		if(paramMap.get("MOHW_RESRCE_SCLAS_SE_CD") != null && !"".equals(paramMap.get("MOHW_RESRCE_SCLAS_SE_CD"))) {
			sWlrscPrvsvClsId = paramMap.get("MOHW_RESRCE_SCLAS_SE_CD");

		} else if(paramMap.get("MOHW_RESRCE_MLSFC_SE_CD") != null && !"".equals(paramMap.get("MOHW_RESRCE_MLSFC_SE_CD"))) {
			sWlrscPrvsvClsId = paramMap.get("MOHW_RESRCE_MLSFC_SE_CD");

		} else {
			sWlrscPrvsvClsId = paramMap.get("MOHW_RESRCE_LCLAS_SE_CD");
		}
		
		//20230318-시군구가 들어올 경우 행정동코드로 보내줌
		if(paramMap.get("PVSN_MBD_LOCTN_EMD_ADRGN_CD") != null && !"".equals(paramMap.get("PVSN_MBD_LOCTN_EMD_ADRGN_CD"))) {
			String[] a = paramMap.get("PVSN_MBD_LOCTN_EMD_ADRGN_CD").split("-");
			if(a.length > 1) {
				paramMap.put("PVSN_MBD_LOCTN_EMD_ADRGN_CD", a[1]);
			}
			
		}

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("jobPrcsDcd"            , "R");										   //작업처리구분코드
		reqMap.put("srvRqstInstTypDtlCd"   , "K001");	  			 					   //서비스의뢰기관유형상세코드(K001:청소년안전망)
		reqMap.put("infoSharInstId"        , sInfoSharInstId);			   				   //정보공유기관ID
		reqMap.put("instSggAdrgnCd"        , sInstSggAdrgnCd);							   //기관시군구행정지역코드
		reqMap.put("pvsnMbdLoctnSggAdrgnCd", paramMap.get("PVSN_MBD_LOCTN_SGG_ADRGN_CD")); //자원제공주체소재지시군구행정지역코드
		reqMap.put("pvsnMbdLoctnEmdAdrgnCd", paramMap.get("PVSN_MBD_LOCTN_EMD_ADRGN_CD")); //자원제공주체소재지읍면동행정지역코드
		reqMap.put("dsrId"				   , sDsrId);									   //욕구ID
		reqMap.put("wlrscPrvsvClsId"	   , sWlrscPrvsvClsId);							   //복지자원제공서비스분류ID
		reqMap.put("srchwdDiv"             , paramMap.get("SRCHWD_DIV"));				   //검색어구분(제공주체(001) / 제공서비스(002) / ALL(003))
		reqMap.put("srchwdCn"              , paramMap.get("SRCHWD_CN"));				   //검색어내용
		reqMap.put("currentPageIndex"      , pageMap.get("pageNo"));				   	   //현재페이지
		reqMap.put("pageRowCount"          , pageMap.get("pageRowCount"));				   //한 페이지에 보여질 행의 개수
		
		/* 2022-12-23 여가부요청 검색조건 추가*/
		reqMap.put("rscPvsnSysDcd"         , paramMap.get("MOHW_RSFR_SYS_SE_CD"));			//자원제공시스템구분코드
		reqMap.put("rscPvsnSrvTcd"         , paramMap.get("SRVC_TYPE_SE_CD"));				//자원제공서비스유형코드
		reqMap.put("lftmCycCd"             , paramMap.get("MOHW_LFE_CYCL_SE_CD"));			//생애주기(생애주기코드)
		reqMap.put("idchCd"          	   , paramMap.get("MOHW_INDV_CHAR_SE_CD"));			//개인특성(개인특성코드)
		reqMap.put("dsbClsCd"          	   , paramMap.get("MOHW_TROBL_CL_SE_CD"));			//장애분류(장애분류코드)
		reqMap.put("dsdgrCd"          	   , paramMap.get("MOHW_TROBL_DGREE_SE_CD"));		//장애정도(장애정도코드)
		reqMap.put("incClasCd"             , paramMap.get("MOHW_INCMEA_SCLSRT_SE_CD"));		//소득계층(소득계층코드)
		reqMap.put("fmlyTypCd"             , paramMap.get("MOHW_HOUSEH_TYPE_SE_CD"));		//가구유형(가구유형코드)

		List<Map<String, Object>> retList = new ArrayList<>();

		String intrfcID = "INFIF_IR_SSI_WS_08"; //복지자원조회 연계
		
		String[][] keyArray = {  {"rscPrvsvId"		   ,"RSC_PRVSV_ID"}				//자원제공서비스ID
								,{"rscPvsnMbdId"	   ,"RSC_PVSN_MBD_ID"}			//자원제공주체ID
								,{"rscPvsnMbdNm"	   ,"RSC_PVSN_MBD_NM"}			//자원제공주체명
								,{"rscPvsnMbdAddr"	   ,"RSC_PVSN_MBD_ADDR"}		//자원제공주체주소
								,{"srvRqstInstTypDtlCd","SRV_RQST_INST_TYP_DTL_CD"}	//서비스의뢰기관유형상세코드
								,{"srvRqstInstTypDtlNm","SRV_RQST_INST_TYP_DTL_NM"}	//서비스의뢰기관유형상세명
								,{"wlrscPrvsvClsId"	   ,"WLRSC_PRVSV_CLS_ID"}		//복지자원제공서비스분류ID(자원분류체계)
								,{"wlrscPrvsvCnm"	   ,"WLRSC_PRVSV_CNM"}			//복지자원제공서비스분류명(자원분류체계)
								,{"rscPrvsvNm"		   ,"RSC_PRVSV_NM"}				//자원제공서비스명
								,{"rscPrvsvPgmNm"	   ,"RSC_PRVSV_PGM_NM"}			//자원제공서비스프로그램명
								,{"rscPrvsvDc"		   ,"RSC_PRVSV_DC"}				//자원제공서비스설명
								,{"rscPrvsvBgngYmd"	   ,"RSC_PRVSV_BGNG_YMD"}		//자원제공서비스시작일자
								,{"rscPrvsvEndYmd"	   ,"RSC_PRVSV_END_YMD"}		//자원제공서비스종료일자
								,{"rscPvsnSrvTcd"	   ,"RSC_PVSN_SRV_TCD"}			//자원제공서비스유형코드
								,{"rscPvsnSrvTnm"	   ,"RSC_PVSN_SRV_TNM"}			//자원제공서비스유형코드명
								,{"rscPrvsvOtmYn"	   ,"RSC_PRVSV_OTM_YN"}			//자원제공서비스일회성여부
								,{"rscPrvsvScd"		   ,"RSC_PRVSV_SCD"}			//자원제공서비스상태코드
								,{"rscPrvsvSnm"		   ,"RSC_PRVSV_SNM"}			//자원제공서비스상태코드명
								,{"rscPvsnSysDcd"	   ,"RSC_PVSN_SYS_DCD"}			//자원제공시스템구분코드
								,{"rscPvsnSysDnm"	   ,"RSC_PVSN_SYS_DNM"}			//자원제공시스템구분코드명
								,{"picFlnm"			   ,"PIC_FLNM"}					//담당자성명
								,{"picTelno"		   ,"PIC_TELNO"}				//담당자전화번호
								,{"picMpno"			   ,"PIC_MPNO"}					//담당자휴대전화번호
								,{"picEmadr"		   ,"PIC_EMADR"}				//담당자이메일주소
								,{"hmpgUrl"			   ,"HMPG_URL"}					//홈페이지URL
								,{"pvsnQty"			   ,"PVSN_QTY"}					//제공수량
								,{"pvsnAmt"			   ,"PVSN_AMT"}					//제공금액
								,{"dpstnTam"		   ,"DPSTN_TAM"}				//기탁총액
								,{"dpstnTtlQty"		   ,"DPSTN_TTL_QTY"}			//기탁총수량
								,{"dpstnPvsnCycCd"	   ,"DPSTN_PVSN_CYC_CD"}		//기탁제공주기코드
								,{"dpstnPvsnCycNm"	   ,"DPSTN_PVSN_CYC_NM"}		//기탁제공주기코드명
								,{"rscPvsnSrspUnitCd"  ,"RSC_PVSN_SRSP_UNIT_CD"}	//자원제공서비스지원단위코드
								,{"rscPvsnSrspUnitNm"  ,"RSC_PVSN_SRSP_UNIT_NM"}	//자원제공서비스지원단위코드명
								,{"dppsFlnm"		   ,"DPPS_FLNM"}				//기탁자성명
								,{"dppsTelno"		   ,"DPPS_TELNO"}				//기탁자전화번호
								,{"dpstnUnitUprc"	   ,"DPSTN_UNIT_UPRC"}			//기탁단위단가
								,{"slbnamAmt"		   ,"SLBNAM_AMT"}				//본인부담금액
								,{"sprtTmcnt"		   ,"SPRT_TMCNT"}				//지원횟수
								,{"rscPvsnCycCd"	   ,"RSC_PVSN_CYC_CD"}			//자원제공주기코드
								,{"rscPvsnCycNm"	   ,"RSC_PVSN_CYC_NM"}			//자원제공주기코드명
								,{"utlFxcnt"		   ,"UTL_FXCNT"}				//이용정원수
								,{"utlNope"			   ,"UTL_NOPE"}					//이용인원수
								,{"utlPsbltNope"	   ,"UTL_PSBLT_NOPE"}			//이용가능인원수
								,{"utlWtpsNt"		   ,"UTL_WTPS_NT"}				//이용대기자수
								,{"srvcNope"		   ,"SRVC_NOPE"}				//봉사인원수
								,{"srvcPsbltRegnCn"	   ,"SRVC_PSBLT_REGN_CN"}		//봉사가능지역내용
								,{"regnCareRscYn"	   ,"REGN_CARE_RSC_YN"}			//지역돌봄자원여부
								,{"sinfoInclRscYn"	   ,"SINFO_INCL_RSC_YN"}		//민감정보포함자원여부
								,{"onapPsbltYn"		   ,"ONAP_PSBLT_YN"} };			//온라인신청가능여부

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		json = mapper.writeValueAsString(reqMap);
		LOGGER.debug("복지자원조회 연계 전달 json : " + json);

		String resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
		LOGGER.debug("복지자원조회 연계 연계 응답 결과 : " + resResult);

		JSONParser parser  = new JSONParser(); 				 	  	   //JSON Parser 객체 생성. parser를 통해 파싱

		String totalRowCount = "0";

		if(resResult != null && !"".equals(resResult)) {
			JSONObject jsonObj = (JSONObject)parser.parse(resResult); 	   //Parser로 문자열 데이터를 JSON 데이터로 변환

			if(jsonObj.get("totalRowCount") != null) {
				totalRowCount = String.valueOf(jsonObj.get("totalRowCount"));
			}			

			if(jsonObj.get("listWlrscPrvsv") != null) {
				JSONArray  arrList = (JSONArray)jsonObj.get("listWlrscPrvsv");

				for(int i=0; i<arrList.size(); i++) {
					Map<String, Object> map = new HashMap<>();

					JSONObject jsObj  = (JSONObject)arrList.get(i);
					Iterator iterator = jsObj.keySet().iterator();
					while(iterator.hasNext()) {
						String key   = (String)iterator.next();
						String value = "";
						if(jsObj.get(key) != null) {
							value = jsObj.get(key).toString();
						}

						for(int j=0; j<keyArray.length; j++) {
							if(key.equals(keyArray[j][0])) {
								map.put(keyArray[j][1], value);
							}
						}
					}

					retList.add(map);
				}
			}
		}

		LOGGER.debug("========== 복지부 실시간 연계_복지자원조회 END ==========");	

		pageMap.put("totalCount", totalRowCount);

		rtnMap.put("dsList", retList);
		rtnMap.put("dmPage", pageMap);

		return rtnMap;
	}
	
	/**
	 * @Method명   : linkMohwJobTest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 :
	 */
	@Override
	public void linkMohwJobTest() throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy MM-dd HH:mm:ss", Locale.KOREA);
		
		LOGGER.debug("JOB 실행=["  + dataFormat.format(calendar.getTime())+ "]");
	}

}
