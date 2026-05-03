/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.linkmng.outsd.mapper.LinkMmaRcptJobMapper;
import isry.itgcm.linkmng.outsd.mapper.LinkMohwSrvcRqstMapper;
import isry.itgcm.linkmng.outsd.service.LinkMmaRcptJobService;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.ScpDb;

/**
 * @파일명     	: LinkMmaRcptJobServiceImpl.java
 * @프로그램 설명	: 병무청 관련 연계
 * @작성자      	: Lee.Tae.Ho
 * @작성일      	: 2022. 08. 04. 
 * @수정자      	: Lee.Tae.Ho
 * @수정일      	: 2022. 08. 04.
 * @수정내용    	: 병무청 관련 연계
 * -                
 * -                
 */
@Service("linkMmaRcptJobService")
public class LinkMmaRcptJobServiceImpl implements LinkMmaRcptJobService {

	@Resource(name="linkMmaRcptJobMapper")
    public LinkMmaRcptJobMapper linkMmaRcptJobMapper;
	
	@Resource(name = "linkMohwSrvcRqstMapper")
	public LinkMohwSrvcRqstMapper linkMohwSrvcRqstMapper; /* 복지부 서비스의뢰 Mapper */
	
	@Resource(name = "caseRegMapper")
	private CaseRegMapper caseRegMapper; /* 사례기본등록 Mapper */
	
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper; /* 채번 Mapper */

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	ScpDb scpDb = new ScpDb();
	
	/**
	 * @Method		: linkMmaSprt
	 * @Method설명 	: 심리취약 병역의무자지원 구분 및 자원기관정보
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 04.
 	 */
	@Override
	public void linkMmaSprt() throws Exception {
		LOGGER.debug("=========== 심리취약 병역의무자지원 구분 및 자원기관정보 연계 START : linkMmaSprt ===========");

		String serverName = System.getProperty("SERVER"); // 서버명
		if(!"rybwas11".equals(serverName)) {
			return;
		}

		int inqCnt  = 0; //조회건수
		int regCnt  = 0; //등록건수
		int exclCnt = 0; //제외건수

		String exclCn = ""; //제외내용

		Map<String, String> paramMap = new HashMap<>();		

		// CAA140 연계상태(ESB_STATUS) != 'Y' 데이터 삭제
		linkMmaRcptJobMapper.deleteMmaSprt(paramMap);
		
		// 송신 대상 목록 조회
		List<Map<String, Object>> trgtList = new ArrayList<Map<String, Object>>();
		trgtList = linkMmaRcptJobMapper.selectMmaSprtList(paramMap);
				
		for(Map<String, Object> trgtMap : trgtList) {

//			Map<String, Object> map = new HashMap<>();

			exclCn = "";

			if(!"".equals(exclCn)) {
				LOGGER.debug("=========== " + exclCn);

				exclCnt++;

			} else {
				//CAA140 등록
				linkMmaRcptJobMapper.insertMmaSprt(trgtMap);

				regCnt++;				
			}

			inqCnt++;
		}

		LOGGER.debug("=========== 심리취약 병역의무자지원 구분 및 자원기관정보 연계 END : linkMmaSprt ===========");

		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("*** 제외건수 : " + exclCnt);
		LOGGER.debug("******************************************");
	}
	
	/**
	 * @Method		: linkMmaTrmnPrcs
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 04. 
	 * 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 + 심리취약한 병역의무자 상담지원 의뢰 사례관리 대상자선정결과 연계 송신
	 * => 조회 조건이 같으니 두가지 IF 동시에 연계(CAA120,CAA110) 
 	 */	
	@Override
	public void linkMmaTrmnPrcs() throws Exception {

		LOGGER.debug("=========== 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 START : linkMmaTrmnPrcs ===========");

		String serverName = System.getProperty("SERVER"); // 서버명
		if (!"rybwas11".equals(serverName)) {
			return;
		}
		
		int inqCntCaa120  = 0; //CAA120 조회건수
		int regCntCaa120  = 0; //CAA120 등록건수

		int inqCntCaa110  = 0; //CAA110 조회건수
		int regCntCaa110  = 0; //CAA110 등록건수

		Map<String, String> paramMap = new HashMap<>();		

		// CAA120 연계상태(ESB_STATUS) != 'S' 데이터 삭제
		linkMmaRcptJobMapper.deleteMmaTrmn(paramMap);

		// CAA110 연계상태(ESB_STATUS) != 'S' 데이터 삭제
		linkMmaRcptJobMapper.deleteMmaTrpr(paramMap);

		List<Map<String, Object>> trgtList = new ArrayList<Map<String, Object>>();

/*		
		// 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 목록조회		
		List<Map<String, Object>> trgtList = new ArrayList<Map<String, Object>>();
		trgtList = linkMmaRcptJobMapper.selectMmaTrmnList(paramMap);
		
		String sResultSecd		= "";   // 종결심사결과
		String sCaseSttsCd		= "";   // 사례진행상태구분코드

		for(Map<String, Object> trgtMap : trgtList) {

			Map<String, Object> map = new HashMap<>();
			
			sCaseSttsCd = trgtMap.get("CASE_PRGRS_STTS_SE_CD").toString(); // 사례진행상태구분코드
			LOGGER.debug("사례진행상태구분코드 ===>>> " + sCaseSttsCd);
			
//			if("04".equals(sCaseSttsCd) || "05".equals(sCaseSttsCd)) { // 04:종결 05:재개입
			if("04".equals(sCaseSttsCd)) { // 04:종결 05:재개입
				map.put("TRPR_RRNO", scpDb.scpDecB64(String.valueOf(trgtMap.get("TRPR_RRNO"))));	// 대상자주민등록번호
				map.put("TRPR_NM", scpDb.scpDecB64(String.valueOf(trgtMap.get("TRPR_NM"))));		// 대상자명
				map.put("TRMN_CS_SE_CD", trgtMap.get("TRMN_CS_SE_CD").toString()); 					// 종결사유구분코드
				map.put("GOAL_PLAN_CN", trgtMap.get("GOAL_PLAN_CN").toString()); 					// 목표계획내용
				LOGGER.debug(" 목표달성성과내용 ==>>> "  + trgtMap.get("GOAL_ACHIV_OUTC_CN"));			
				if(trgtMap.get("GOAL_ACHIV_OUTC_CN") != null && !"".equals(trgtMap.get("GOAL_ACHIV_OUTC_CN")) && !"null".equals(trgtMap.get("GOAL_ACHIV_OUTC_CN"))) {
					map.put("GOAL_ACHIV_OUTC_CN", trgtMap.get("GOAL_ACHIV_OUTC_CN").toString()); 	// 목표달성성과내용
				} else {
					map.put("GOAL_ACHIV_OUTC_CN", ""); 												// 목표달성성과내용
				}
				
				if(trgtMap.get("TRMN_SRNG_YMD") != null && !"".equals(trgtMap.get("TRMN_SRNG_YMD")) && !"null".equals(trgtMap.get("TRMN_SRNG_YMD"))) {
					map.put("TRMN_SRNG_YMD", trgtMap.get("TRMN_SRNG_YMD").toString()); 				// 종결심사일자
				} else {
					map.put("TRMN_SRNG_YMD", ""); 													// 종결심사일자
				}			
				
				sResultSecd = trgtMap.get("TRMN_SRNG_RESULT_SE_CD").toString();
				if("01".equals(sResultSecd)) {
					sResultSecd = "01"; // 01:신청
				} else if("02".equals(sResultSecd)) {
					sResultSecd = "02"; // 02:종결
				} else if("03".equals(sResultSecd)) {
					sResultSecd = "04"; // 04:재개입
				} else {
					sResultSecd = "03"; // 03:보류는 현재 코드없음
				}
				map.put("TRMN_SRNG_RESULT_SE_CD", sResultSecd);// 종결심사결과

				map.put("ESB_COMPLITE_TIME", trgtMap.get("CASE_TRMN_YMD").toString());				// 연계종료일시
				map.put("TRPR_RRNO_ENCPT", trgtMap.get("TRPR_RRNO").toString());					// 대상자주민등록번호암호화

				inqCntCaa120++;

				//CAB120(병역의무자 상담지원의뢰 종결처리 현황) 등록
				linkMmaRcptJobMapper.insertMmaTrmn(map);

				regCntCaa120++;	

			} else if("01".equals(sCaseSttsCd)) { // 01:사례등록
				map.put("TRPR_RRNO", scpDb.scpDecB64(String.valueOf(trgtMap.get("TRPR_RRNO"))));	// 대상자주민등록번호
				map.put("TRPR_RRNO_ENCPT", trgtMap.get("TRPR_RRNO").toString());					// 대상자주민등록번호_암호화
				map.put("TRPR_NM", scpDb.scpDecB64(String.valueOf(trgtMap.get("TRPR_NM"))));		// 대상자명
				map.put("CASE_MNG_SE_CD", "04");													// 사례관리구분코드 04:사례대상자선정
				map.put("CASE_TRPR_SLCTN_YMD", trgtMap.get("CASE_BGNG_YMD").toString());			// 사례대상자선정일자_사례시작일자

				inqCntCaa110++;

				//CAA110(병역의무자 상담지원의뢰 대상자선정 결과) 등록
				linkMmaRcptJobMapper.insertMmaTrpr(map);

				regCntCaa110++;	

			} else {				
				LOGGER.debug("continue 사례진행상태구분코드 ===>>> " + sCaseSttsCd);
				continue;
			}

		} // end for(Map<String, Object> trgtMap : trgtList) {
*/		

		// 심리취약한 병역의무자 상담지원 의뢰 사례관리 대상자선정 연계 목록조회
		trgtList.clear();
		trgtList = linkMmaRcptJobMapper.selectMmaCaseTrprSlctnList(paramMap);

		for(Map<String, Object> trgtMap : trgtList) {

			inqCntCaa110++;

			//CAA110(병역의무자 상담지원의뢰 대상자선정 결과) 등록
			linkMmaRcptJobMapper.insertMmaTrpr(trgtMap);

			regCntCaa110++;
		}

		// 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 목록조회
		trgtList.clear();
		trgtList = linkMmaRcptJobMapper.selectMmaCaseTrmnPrcsList(paramMap);

		for(Map<String, Object> trgtMap : trgtList) {

			inqCntCaa120++;

			//CAB120(병역의무자 상담지원의뢰 종결처리 현황) 등록
			linkMmaRcptJobMapper.insertMmaTrmn(trgtMap);

			regCntCaa120++;
		}

		LOGGER.debug("=========== 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 END : linkMmaTrmnPrcs ===========");

		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 종결처리 조회건수 : " + inqCntCaa120);
		LOGGER.debug("*** 종결처리 등록건수 : " + regCntCaa120);
		LOGGER.debug("******************************************");

		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 대상자처리 조회건수 : " + inqCntCaa110);
		LOGGER.debug("*** 대상자처리 등록건수 : " + regCntCaa110);
		LOGGER.debug("******************************************");
	}

	@Override
	public void linkMmaCaseReg() throws Exception {

		LOGGER.debug("========== 사례기본, 사례관리이력, 사례담당자 저장,수정 Start ==========");

		String serverName = System.getProperty("SERVER"); // 서버명
		if (!"rybwas11".equals(serverName)) {
			return;
		}

		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = linkMmaRcptJobMapper.seachTrpr();

		for(Map<String, String> map : list) {
			Map<String, String> saveMap = new HashMap<String, String>();

			// 사례기본 생성 : SEB100, 사례기본이력 : SEB101, 사례담당자 : SEB150 (사례화면 권한적용으로 인하여 insert)
			String sCaseMngNo = selectRenuNo("BATCH", "CS"); /* 사례관리번호 채번 */
			LOGGER.debug(":::::::::: 사례관리번호=[" + sCaseMngNo + "] ::::::::::");

			saveMap.put("CASE_MNG_NO"		   , sCaseMngNo); 				   /* 사례관리번호 CASE_MNG_NO */
			saveMap.put("CASE_MNG_ODRNO"	   , "1"); 						   /* 사례관리차수 CASE_MNG_ODRNO */
			saveMap.put("CASE_BGNG_YMD"		   , DateUtil.getToday()); 		   /* 사례시작일자 CASE_BGNG_YMD */			
			saveMap.put("CASE_PRGRS_STTS_SE_CD", "01"); 					   /* 사례진행상태구분코드 CASE_PRGRS_STTS_SE_CD */
			saveMap.put("USER_ID"			   , "BATCH"); 					   /* 최초등록자아이디 FRST_RGTR_ID */
			saveMap.put("UNT_TASKWK_SE_CD"	   , map.get("UNT_TASKWK_SE_CD"));
			saveMap.put("YNGBGS_SE_NO" 		   , map.get("YNGBGS_STTS_LCLAS_SE_CD"));		/* 청소년구분번호 YNGBGS_SE_NO*/			
			saveMap.put("TRPR_INFO_NO"    	   , map.get("TRPR_INFO_NO"));

			// 사례기본 등록
			caseRegMapper.insertSEB100Data(saveMap);

			// 사례기본 이력 등록
			saveMap.put("DATAA_CHG_SE_CD", "I");		
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
			
			// 화면에서 자원입력으로 인하여 서비스제공 등록 SEB500, 서비스제공이력 SEB501

			// 사례담당자 등록		(사례관리 권한적용으로 등록 필수)

			saveMap.put("NEW_CASE_PIC_NO" , (map.get("CASE_PIC_NO") == null ? String.valueOf(map.get("RCPT_INST_NO")) : map.get("CASE_PIC_NO")));	
			saveMap.put("CASE_MNG_NO"	  , sCaseMngNo);	
			saveMap.put("CASE_MNG_ODRNO"  , "1");
			saveMap.put("PCHPRS_YN"		  , "Y");	
			saveMap.put("PIC_DSGN_YMD"	  , DateUtil.getToday());
			saveMap.put("PIC_INST_NO"	  , (map.get("PIC_INST_NO") == null ? String.valueOf(map.get("RCPT_INST_NO")) : map.get("PIC_INST_NO")) );
			saveMap.put("REG_AUTHRT_YN"	  , "Y");	
			saveMap.put("PLAN_AUTHRT_YN"  , "Y");	
			saveMap.put("EXCN_AUTHRT_YN"  , "Y");	
			saveMap.put("OUTC_AUTHRT_YN"  , "Y");	
			saveMap.put("TRMN_AUTHRT_YN"  , "Y");	
			saveMap.put("AFTFCT_AUTHRT_YN", "Y");			
			saveMap.put("USER_ID"		  , "BATCH");			

			caseRegMapper.insertSEB150Data(saveMap);

			saveMap.put("DATAA_CHG_SE_CD", "I");		
			caseRegMapper.insertSEB151Data(saveMap);

			//대상자정보 수정
			saveMap.put("CASE_MNG_SE_CD"  , "04");   /* 사례관리구분코드 */
			caseRegMapper.updateSEA200Data(saveMap);

			//대상자정보 이력 등록
			caseRegMapper.insertSEA201Data(saveMap);

			//사례등록 후 연계상태 Y
			linkMmaRcptJobMapper.esbStatus(map);

			// 배치후 대상자테이블 사례관리구분 수정
			// 사례관리구분코드 CASE_MNG_SE_CD (연계상태성공 후 배치돌고 성공하면 사례기본 업데이트 후 사례대상자선정 : 04로 업데이트)
		}

		LOGGER.debug("========== 사례기본, 사례관리이력, 사례담당자 저장,수정 End ==========");		
	}

	/**
	 * @Method명 : selectRenuNo
	 * @param sessionUserId(세션정보), RenuNoSeCd(채번코드)
	 * @return
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 1.
	 * @Method설명 : 식별번호 채번
	 */
	public String selectRenuNo(String sessionUserId, String RenuNoSeCd) {

		String sIdntfcNo = "";

		// 식별번호 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();

		seqMap.put("USER_ID"	  , sessionUserId);
		seqMap.put("RENU_NO_SE_CD", RenuNoSeCd); 		  // 채번코드
		seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

		// 채번서비스 호출
		try {
			valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		sIdntfcNo = String.valueOf(valMap.get("RENU_NO")); // 식별번호 채번
		return sIdntfcNo;
	}
}

