/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.linkmng.linkmedia.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.linkmng.linkmedia.mapper.LinkMediaMapper;
import isry.cysns.linkmng.linkmedia.service.LinkMediaService;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : LinkMediaServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("linkMediaService")
public class LinkMediaServiceImpl implements LinkMediaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	
	
	@Resource(name = "linkMediaMapper")
	private LinkMediaMapper linkMediaMapper;

	/**
	 * @Method명   : saveWlfarCnterData
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 : 복지센터정보
	 */
	@Override
	public void saveWlfarCnterData() throws Exception {
		
		LOGGER.debug("=========== 복지센터코드 연계 START : saveWlfarCnterData ===========");
		int inqCnt = 0; // 조회건수
		int regCnt = 0; // 등록건수
		int exclCnt = 0; // 제외건수
	
		List<Map<String, String>> selectWlfarCnterList = linkMediaMapper.selectWlfarCnterList();
		
		for (Map<String, String> map : selectWlfarCnterList) {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("WLFAR_CNTER_NO", map.get("WLFAR_CNTER_NO"));
			paramMap.put("INST_NM", map.get("INST_NM"));
			paramMap.put("CTPV_NM", map.get("CTPV_NM"));
			paramMap.put("WLFAR_CNTER_TELNO", map.get("WLFAR_CNTER_TELNO"));
			paramMap.put("WLFAR_CNTER_FXNO", map.get("WLFAR_CNTER_FXNO"));
			paramMap.put("INST_ADDR", map.get("INST_ADDR"));
			paramMap.put("HPGE_ADDR", map.get("HPGE_ADDR"));
			paramMap.put("ESB_SEQ", String.valueOf(map.get("ESB_SEQ")));
			paramMap.put("USER_ID", "BATCH");

			inqCnt++; //조회건수
			
			linkMediaMapper.saveWlfarCnterData(paramMap);
			linkMediaMapper.updateWlfarCnterData(paramMap);
			
			regCnt++; //등록건수
		}

		LOGGER.debug("=========== 복지센터코드 연계 END : saveWlfarCnterData ===========");
		
		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("*** 제외건수 : " + exclCnt);
		LOGGER.debug("******************************************");
	}

	/**
	 * @Method명   : saveSchlScoreData
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 : 설문답변
	 */
	@Override
	public void saveSchlScoreData() throws Exception {

		LOGGER.debug("=========== 설문답변관리 연계 START : saveSchlScoreData ===========");

		int inqCnt = 0; // 조회건수
		int regCnt = 0; // 등록건수
		int exclCnt = 0; // 제외건수
		
		List<Map<String, String>> selectSchlScoreList = linkMediaMapper.selectSchlScoreList();
		
		for (Map<String, String> map : selectSchlScoreList) {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("DGNSS_EXMN_MNG_NO", map.get("DGNSS_EXMN_MNG_NO"));
			paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");
			paramMap.put("DGNSS_EXMN_SE_CD", getExmnSe(map.get("RSCH_DIV_CD")));
			paramMap.put("SCORE_SE_SE_CD", map.get("ORD"));
			paramMap.put("DGNSS_SCORE", map.get("ANSWER"));
			paramMap.put("TRPR_INFO_NO", map.get("TRPR_INFO_NO"));
			paramMap.put("ESB_SEQ", map.get("ESB_SEQ"));
			paramMap.put("USER_ID", "BATCH");

			inqCnt++; // 조회건수
			
			linkMediaMapper.saveDgnssScoreData(paramMap);  //진단조사점수
			linkMediaMapper.updateSchlScoreData(paramMap); //설문답변

			regCnt++; // 등록건수
		}

		LOGGER.debug("=========== 설문답변관리 연계  : saveSchlScoreData ===========");
		
		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("*** 제외건수 : " + exclCnt);
		LOGGER.debug("******************************************");
	}

	/**
	 * @Method명   : saveInstScoreData
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 31. 
	 * @Method설명 : 
	 */
	@Override
	public void saveInstScoreData() throws Exception {
		LOGGER.debug("=========== 기관설문답변관리 연계 START : saveInstScoreData ===========");

		int inqCnt = 0; // 조회건수
		int regCnt = 0; // 등록건수
		int exclCnt = 0; // 제외건수
		
		List<Map<String, String>> selectInstScoreList = linkMediaMapper.selectInstScoreList();
		
		for (Map<String, String> map : selectInstScoreList) {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("DGNSS_EXMN_MNG_NO", map.get("DGNSS_EXMN_MNG_NO"));
			paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");
			paramMap.put("DGNSS_EXMN_SE_CD", getExmnSe(map.get("RSCH_DIV_CD")));
			paramMap.put("SCORE_SE_SE_CD", map.get("ORD"));
			paramMap.put("DGNSS_SCORE", map.get("ANSWER"));
			paramMap.put("TRPR_INFO_NO", map.get("TRPR_INFO_NO"));
			paramMap.put("ESB_SEQ", map.get("ESB_SEQ"));
			paramMap.put("USER_ID", "BATCH");

			inqCnt++; // 조회건수
			
			linkMediaMapper.saveDgnssScoreData(paramMap); //진단조사점수
			linkMediaMapper.updateInstScoreData(paramMap); //기간설문답변

			regCnt++; // 등록건수
		}

		LOGGER.debug("=========== 기관설문답변관리 연계  : saveInstScoreData ===========");
		
		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("*** 제외건수 : " + exclCnt);
		LOGGER.debug("******************************************");
	}

	/**
	 * @Method명   : saveSchlDgnssData (학교진단)
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	@Override
	public void saveSchlDgnssData() throws Exception {
		ScpDb scpDb = new ScpDb();
		
		LOGGER.debug("=========== 학교진단 연계 START : saveSchlDgnssData ===========");
		int inqCnt = 0; // 조회건수
		int regCnt = 0; // 등록건수
		int exclCnt = 0; // 제외건수

		
		List<Map<String, String>> selectSchlDgnssList = linkMediaMapper.selectSchlDgnssList();
		
		for (Map<String, String> map : selectSchlDgnssList) {
			map.put("TRPR_INFO_NO", linkMediaMapper.selectKeyValue(getKeyValue("TR")));  //대상자번호
			map.put("TRPR_BRTH_YMD", "19000101"); 				                         //대상자출생일자 //
			map.put("CASE_TRPR_TYPE_SE_CD", "07");                                       //사례대상자유형구분코드 - 인터넷과의존학교진단대상자
			map.put("SRVC_PVSN_RQST_NO", linkMediaMapper.selectKeyValue(getKeyValue("SQ"))); //서비스제공의뢰번호
			map.put("EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("EML_ADDR")));	          //이메일주소암호화 
			map.put("RCPT_RQST_COURS_SE_CD", getRqstCours(map.get("MEDIAA_SCHL_SE_CD"))); //접수의뢰경로구분코드 - //접수의뢰경로구분코드 - 초등학교/중학교/고등학교
			map.put("LINK_TYPE_SE_CD", "07");                                            //연계유형구분코드
			map.put("USER_ID", "BATCH");                                                 //사용자id

			inqCnt++; // 조회건수
			
			insertTrprInfoData(map); //대상자정보
			insertTrprInfoHistory(map); //대상자정보 이력
			int mngSn = insertAcbgSttsForSchl(map); //학력상태(학교진단)
			map.put("MNG_SN", String.valueOf(mngSn));                                   //접수일련번호
			insertAcbgSttsForSchlHistory(map); //학력상태-이력(학교진단)
			insertSrvcPvsnRqst(map); //서비스의뢰접수
			insertSrvcPvsnRqstHistory(map); //서비스의뢰접수이력
			int rcptSn = insertSrvcPvsnRqstRcpt(map); //서비스의뢰접수
			map.put("RCPT_SN", String.valueOf(rcptSn));                                   //접수일련번호
			insertSrvcPvsnRqstRcptHistory(map); //서비스의뢰접수이력

			insertSchlDgnssData(map); //학교진단
			linkMediaMapper.updateSchlDgnssData(map); //학교진단 연계

			regCnt++; // 등록건수
		}

		LOGGER.debug("=========== 학교진단 연계 END : saveSchlDgnssData ===========");

		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("*** 제외건수 : " + exclCnt);
		LOGGER.debug("******************************************");

	}


	/**
	 * @Method명   : saveInstDgnssData(기관진단)
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	@Override
	public void saveInstDgnssData() throws Exception {
		
		LOGGER.debug("=========== 기관진단 연계 START : saveInstDgnssData ===========");
		int inqCnt = 0; // 조회건수
		int regCnt = 0; // 등록건수
		int exclCnt = 0; // 제외건수

		List<Map<String, String>> selectInstDgnssList = linkMediaMapper.selectInstDgnssList();
		
		for (Map<String, String> map : selectInstDgnssList) {
			map.put("TRPR_INFO_NO", linkMediaMapper.selectKeyValue(getKeyValue("TR")));      //대상자번호
			map.put("TRPR_BRTH_YMD", map.get("BRTH_YMD_NM").replace("-", ""));               //대상자출생일자 //
			map.put("CASE_TRPR_TYPE_SE_CD", getLinkType(map.get("SRVY_INST_NM")));           //사례대상자유형구분코드 - 설문기관코드
			map.put("SRVC_PVSN_RQST_NO", linkMediaMapper.selectKeyValue(getKeyValue("SQ"))); //서비스제공의뢰번호
			map.put("RCPT_RQST_COURS_SE_CD", getRqstCours(map.get("STDNT_AGE_NM")));         //접수의뢰경로구분코드 - 초등학교/중학교/고등학교
			map.put("LINK_TYPE_SE_CD", getLinkType(map.get("SRVY_INST_NM")));                //연계유형구분코드 - 설문기관코드
			map.put("USER_ID", "BATCH");                                                          //사용자id

			inqCnt++; // 조회건수

			insertTrprInfoData(map); //대상자정보
			insertTrprInfoHistory(map); //대상자정보 이력
			int mngSn = insertAcbgSttsForInst(map); //학력상태(기관진단)
			map.put("MNG_SN", String.valueOf(mngSn)); //학력상태관리번버
			insertAcbgSttsForInstHistory(map); //학력상태-이력(기관진단)
			insertSrvcPvsnRqst(map); //서비스의뢰접수
			insertSrvcPvsnRqstHistory(map); //서비스의뢰접수이력
			int rcptSn = insertSrvcPvsnRqstRcpt(map); //서비스의뢰접수
			map.put("RCPT_SN", String.valueOf(rcptSn)); //접수일련번호
			insertSrvcPvsnRqstRcptHistory(map); //서비스의뢰접수이력

			insertInstDgnssData(map); //기관진단
			linkMediaMapper.updateInstDgnssData(map); //기관진단 연계

			regCnt++; // 등록건수
		}
		
		LOGGER.debug("=========== 기관진단 연계 END : saveInstDgnssData ===========");

		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("*** 제외건수 : " + exclCnt);
		LOGGER.debug("******************************************");
	}

	/**
	 * @Method명   : insertTrprInfoData
	 * @param map
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	private void insertTrprInfoData(Map<String, String> map) throws Exception {
		ScpDb scpDb = new ScpDb();

		Map<String, String> paramMap = new HashMap<>(map);

		paramMap.put("UNT_TASKWK_SE_CD", "U02"); 			                    //단위업무구분코드
		paramMap.put("TRPR_NM_ENCPT", scpDb.scpEncB64(map.get("STDNT_NM")));    //대상자명암호화
		paramMap.put("MRG_YN", "N");                       						//결혼여부

		paramMap.put("GRDTN_STTS_SE_CD", "01");                                 //졸업상태구분코드 - 재학
		paramMap.put("RCPT_INST_NO", map.get("CNTER_ALTMNT_INST_NO"));   		//접수기관번호

		paramMap.put("CASE_MNG_SE_CD", "01");                                    //사례관리구분코드 - 사례대상자미선정
		paramMap.put("CASE_TRPR_UNSL_CS_CN", "미디어과의존");                        //사례대상자미선정사유내용
		paramMap.put("PRTCR_NM_ENCPT", scpDb.scpEncB64(map.get("PRTCR_FLNM")));  //보호자명암호화
		paramMap.put("LIVTGT_YN", "N");                                          //동거여부

		paramMap.put("PRTCR_AGRE_YN", "N");                                       //보호자동의여부
		paramMap.put("PRVC_PVSN_AGRE_YN", "N");                                   //개인정보제공동의여부
		paramMap.put("SRVC_CTRT_AGRE_YN", "N");                                   //서비스계약동의여부
		paramMap.put("CASE_TRPR_NOAP_CS_SE_CD", "99");                            //사례대상자미신청사유구분코드 -기타

		paramMap.put("PRTCR_MBL_TELNO_ENCPT", scpDb.scpEncB64(map.get("PRTCR_TELNO").replace("-", "")));  //보호자휴대전화번호암호화
		paramMap.put("PRTCR_EML_ADDR_ENCPT", "");                                 //보호자이메일주소암호화
		paramMap.put("RESIDE_SHAPE_SE_CD", "07");                                 //주거형태구분코드 - 미확인
		paramMap.put("PBLAST_SE_CD", "05");                                       //사회보장구분코드 - 미확인
		paramMap.put("FAM_SHAPE_SE_CD", "09");                                    //가족형태구분코드 - 미확인
		paramMap.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "미디어과의존");                 //개인식별정보미입력사유내용 
//		paramMap.put("INDV_IDNTFC_CRTR_YMD", map.get("USER_TEST_KND_NM"));        //개인식별기준일자
//		paramMap.put("RCPT_RQST_COURS_SE_CD", "02020120");                        //접수의뢰경로구분코드 - 기타청소년기관시설

		paramMap.put("DEL_YN", "N");                                              //삭제여부
//		paramMap.put("USER_ID", map.get("USER_ID"));                              //사용자id

		linkMediaMapper.insertTrprInfoData(paramMap);
		
	}

	/**
	 * @Method명   : insertTrprInfoHistory
	 * @param map
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	private void insertTrprInfoHistory(Map<String, String> map) throws Exception {
		ScpDb scpDb = new ScpDb();
		
		Map<String, String> paramMap = new HashMap<>(map);
		
		paramMap.put("DATAA_CHG_SE_CD", "I");    							//데이터변경구분코드

		paramMap.put("UNT_TASKWK_SE_CD", "U02"); 			                    //단위업무구분코드
		paramMap.put("TRPR_NM_ENCPT", scpDb.scpEncB64(map.get("STDNT_NM")));    //대상자명암호화
		paramMap.put("MRG_YN", "N");                       						//결혼여부
		
		paramMap.put("GRDTN_STTS_SE_CD", "01");                                 //졸업상태구분코드 - 재학
		paramMap.put("RCPT_INST_NO", map.get("CNTER_ALTMNT_INST_NO"));   		//접수기관번호
		
		paramMap.put("CASE_MNG_SE_CD", "01");                                    //사례관리구분코드 - 사례대상자미선정
		paramMap.put("CASE_TRPR_UNSL_CS_CN", "미디어과의존");                        //사례대상자미선정사유내용
		paramMap.put("PRTCR_NM_ENCPT", scpDb.scpEncB64(map.get("PRTCR_FLNM")));  //보호자명암호화
		paramMap.put("LIVTGT_YN", "N");                                          //동거여부
		
		paramMap.put("PRTCR_AGRE_YN", "N");                                       //보호자동의여부
		paramMap.put("PRVC_PVSN_AGRE_YN", "N");                                   //개인정보제공동의여부
		paramMap.put("SRVC_CTRT_AGRE_YN", "N");                                   //서비스계약동의여부
		paramMap.put("CASE_TRPR_NOAP_CS_SE_CD", "99");                            //사례대상자미신청사유구분코드 -기타
		
		paramMap.put("PRTCR_MBL_TELNO_ENCPT", scpDb.scpEncB64(map.get("PRTCR_TELNO").replace("-", "")));  //보호자휴대전화번호암호화
		paramMap.put("PRTCR_EML_ADDR_ENCPT", "");                                 //보호자이메일주소암호화
		paramMap.put("RESIDE_SHAPE_SE_CD", "07");                                 //주거형태구분코드 - 미확인
		paramMap.put("PBLAST_SE_CD", "05");                                       //사회보장구분코드 - 미확인
		paramMap.put("FAM_SHAPE_SE_CD", "09");                                    //가족형태구분코드 - 미확인
		paramMap.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "미디어과의존");                 //개인식별정보미입력사유내용 
//		paramMap.put("INDV_IDNTFC_CRTR_YMD", map.get("USER_TEST_KND_NM"));        //개인식별기준일자
//		paramMap.put("RCPT_RQST_COURS_SE_CD", "02020120");                        //접수의뢰경로구분코드 - 기타청소년기관시설
		
		paramMap.put("DEL_YN", "N");                                              //삭제여부
//		paramMap.put("USER_ID", map.get("USER_ID"));                              //사용자id
		
		linkMediaMapper.insertTrprInfoHistory(paramMap);
		
	}

	/**
	 * @Method명   : insertAcbgSttsForSchl
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 4. 17. 
	 * @Method설명 :
	 */
	private int insertAcbgSttsForSchl(Map<String, String> map) throws Exception {

		Map<String, String> paramMap = new HashMap<>(map);

		paramMap.put("ACBG_SE_CD", getAcbgSe(map.get("MEDIAA_SCHL_SE_CD")));    //학력구분코드
		paramMap.put("GRDTN_STTS_SE_CD", "01");                                 //졸업상태구분코드 - 재학
		paramMap.put("SCHULW_DSCNTC_YN", "N");                                  //학업중단여부 
		paramMap.put("DEL_YN", "N");                                            //삭제여부
		
		return linkMediaMapper.insertAcbgSttsData(paramMap);

	}

	/**
	 * @Method명   : insertAcbgSttsForSchlHistory
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 4. 17. 
	 * @Method설명 :
	 */
	private void insertAcbgSttsForSchlHistory(Map<String, String> map) throws Exception {
		
		Map<String, String> paramMap = new HashMap<>(map);

		paramMap.put("DATAA_CHG_SE_CD", "I");    							    //데이터변경구분코드
		paramMap.put("ACBG_SE_CD", getAcbgSe(map.get("MEDIAA_SCHL_SE_CD")));       //학력구분코드 
		paramMap.put("GRDTN_STTS_SE_CD", "01");                                 //졸업상태구분코드 - 재학
		paramMap.put("SCHULW_DSCNTC_YN", "N");                                  //학업중단여부 
		paramMap.put("DEL_YN", "N");                                            //삭제여부
		
		linkMediaMapper.insertAcbgSttsHistory(paramMap);
		
	}
	
	/**
	 * @Method명   : insertAcbgSttsForInst
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 2. 
	 * @Method설명 :
	 */
	private int insertAcbgSttsForInst(Map<String, String> map) throws Exception {
		Map<String, String> paramMap = new HashMap<>(map);

		paramMap.put("ACBG_SE_CD", getAcbgSe(map.get("STDNT_AGE_NM")));  //학력구분코드
		paramMap.put("GRDTN_STTS_SE_CD", "01");                                 //졸업상태구분코드 - 재학
		paramMap.put("SCHULW_DSCNTC_YN", "N");                                  //학업중단여부 
		paramMap.put("DEL_YN", "N");                                            //삭제여부
		
		if (StringUtil.isEmpty(map.get("SCHL_NM"))) {
			paramMap.put("SCHL_NM", "미확인");                                    //학교명
		}
		
		return linkMediaMapper.insertAcbgSttsData(paramMap);
		
	}

	/**
	 * @Method명   : insertAcbgSttsForInstHistroy
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 2. 
	 * @Method설명 :
	 */
	private void insertAcbgSttsForInstHistory(Map<String, String> map) throws Exception {
		Map<String, String> paramMap = new HashMap<>(map);
		
		paramMap.put("DATAA_CHG_SE_CD", "I");    							//데이터변경구분코드
		paramMap.put("ACBG_SE_CD", getAcbgSe(map.get("STDNT_AGE_NM")));  //학력구분코드
		paramMap.put("GRDTN_STTS_SE_CD", "01");                                 //졸업상태구분코드 - 재학
		paramMap.put("SCHULW_DSCNTC_YN", "N");                                  //학업중단여부 
		paramMap.put("DEL_YN", "N");                                            //삭제여부
		
		if (StringUtil.isEmpty(map.get("SCHL_NM"))) {
			paramMap.put("SCHL_NM", "미확인");                                    //학교명
		}

		
		linkMediaMapper.insertAcbgSttsHistory(paramMap);
		
	}
	
	
	/**
	 * @Method명   : insertSrvcPvsnRqst
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 :
	 */
	private void insertSrvcPvsnRqst(Map<String, String> map) throws Exception {

		Map<String, String> paramMap = new HashMap<>();
		
		paramMap.put("SRVC_PVSN_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));    //서비스제공의뢰번호
		paramMap.put("HPE_SRVC_YN", "N"); 							        //희망서비스여부
		paramMap.put("RQST_UNT_TASKWK_SE_CD", "U02"); 	                    //의뢰단위업무구분코드
		paramMap.put("RCPT_RQST_COURS_SE_CD", "02020120"); 					//접수의뢰경로구분코드
		
		paramMap.put("CLINT_NO", "BATCH"); 			                        //의뢰자번호
		paramMap.put("RQST_INST_NO", map.get("CNTER_ALTMNT_INST_NO"));      //의뢰기관번호
		paramMap.put("RQST_CS_CN", "미디어과의존");   				            //의뢰사유내용
		paramMap.put("RCPT_UNT_TASKWK_SE_CD", "U02");               		//접수단위업무구분코드
		paramMap.put("RCPT_INST_NO", map.get("CNTER_ALTMNT_INST_NO")); 		//접수기관번호

		paramMap.put("RQST_TRPR_INFO_NO", map.get("TRPR_INFO_NO"));         //의뢰대상자정보번호
		paramMap.put("RQST_APLY_YN", "Y"); 				                    //의뢰신청여부
		paramMap.put("RQST_DTL_CN", "미디어과의존");   				            //의뢰상세내용
		paramMap.put("LINK_TYPE_SE_CD", map.get("LINK_TYPE_SE_CD"));        //연계유형구분코드
		paramMap.put("TRMN_PRCS_YN", "N");   		                        //종결처리여부

		paramMap.put("DEL_YN", "N"); 		                                //삭제여부
		paramMap.put("USER_ID", map.get("USER_ID"));                        //사용자id

		linkMediaMapper.insertSrvcPvsnRqst(paramMap);

	}

	/**
	 * @Method명   : insertSrvcPvsnRqstHistory
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 :
	 */
	private void insertSrvcPvsnRqstHistory(Map<String, String> map) throws Exception {

		Map<String, String> paramMap = new HashMap<>();
		
		paramMap.put("SRVC_PVSN_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));    //서비스제공의뢰번호
		paramMap.put("DATAA_CHG_SE_CD", "I");    							//데이터변경구분코드
		paramMap.put("HPE_SRVC_YN", "N"); 							        //희망서비스여부
		paramMap.put("RQST_UNT_TASKWK_SE_CD", "U02"); 	                    //의뢰단위업무구분코드

		paramMap.put("RCPT_RQST_COURS_SE_CD", "02020120"); 					//접수의뢰경로구분코드
		paramMap.put("CLINT_NO", "BATCH"); 			                        //의뢰자번호
		paramMap.put("RQST_INST_NO", map.get("CNTER_ALTMNT_INST_NO"));      //의뢰기관번호
		paramMap.put("RQST_CS_CN", "미디어과의존");   				            //의뢰사유내용
		paramMap.put("RCPT_UNT_TASKWK_SE_CD", "U02");               		//접수단위업무구분코드

		paramMap.put("RCPT_INST_NO", map.get("CNTER_ALTMNT_INST_NO")); 		//접수기관번호
		paramMap.put("RQST_TRPR_INFO_NO", map.get("TRPR_INFO_NO"));         //의뢰대상자정보번호
		paramMap.put("RQST_APLY_YN", "Y"); 				                    //의뢰신청여부
		paramMap.put("RQST_DTL_CN", "미디어과의존");   				            //의뢰상세내용

		paramMap.put("LINK_TYPE_SE_CD", map.get("LINK_TYPE_SE_CD"));        //연계유형구분코드
		paramMap.put("TRMN_PRCS_YN", "N");   		                        //종결처리여부
		paramMap.put("DEL_YN", "N"); 		                                //삭제여부
		paramMap.put("USER_ID", map.get("USER_ID"));                        //사용자id

		linkMediaMapper.insertSrvcPvsnRqstHistory(paramMap);
		
	}

	/**
	 * @Method명   : insertSrvcPvsnRqstRcpt
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 :
	 */
	private int insertSrvcPvsnRqstRcpt(Map<String, String> map) throws Exception {

		Map<String, String> paramMap = new HashMap<>();
		
		paramMap.put("SRVC_PVSN_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));    //서비스제공의뢰번호
		paramMap.put("LINK_TYPE_SE_CD", map.get("LINK_TYPE_SE_CD"));        //연계유형구분코드
		paramMap.put("RCPT_UNT_TASKWK_SE_CD", "U02");               		//접수단위업무구분코드
		paramMap.put("RCPT_TRPR_INFO_NO", map.get("TRPR_INFO_NO"));         //접수대상자정보번호

		paramMap.put("FRST_RQST_NO", map.get("SRVC_PVSN_RQST_NO")); 		//최초의뢰번호
		paramMap.put("RCPT_INST_NO", map.get("CNTER_ALTMNT_INST_NO")); 		//접수기관번호
		paramMap.put("RCPT_SE_CD", "11");                            		//접수구분코드 - 의뢰신청
		paramMap.put("RRQST_YN", "N"); 					                    //재의뢰여부

		paramMap.put("HPE_SRVC_YN", "N");   		                        //희망서비스여부
		paramMap.put("DEL_YN", "N"); 		                                //삭제여부
		paramMap.put("USER_ID", map.get("USER_ID"));                        //사용자id

		return linkMediaMapper.insertSrvcPvsnRqstRcpt(paramMap);
		
	}

	/**
	 * @Method명   : insertSrvcPvsnRqstRcptHistory
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 :
	 */
	private void insertSrvcPvsnRqstRcptHistory(Map<String, String> map) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		paramMap.put("SRVC_PVSN_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));    //서비스제공의뢰번호
		paramMap.put("RCPT_SN", map.get("RCPT_SN"));                        //접수일련번호
		paramMap.put("DATAA_CHG_SE_CD", "I");    							//데이터변경구분코드
		paramMap.put("LINK_TYPE_SE_CD", map.get("LINK_TYPE_SE_CD"));        //연계유형구분코드
		paramMap.put("RCPT_UNT_TASKWK_SE_CD", "U02");               		//접수단위업무구분코드

		paramMap.put("RCPT_TRPR_INFO_NO", map.get("TRPR_INFO_NO"));         //접수대상자정보번호
		paramMap.put("FRST_RQST_NO", map.get("SRVC_PVSN_RQST_NO")); 		//최초의뢰번호
		paramMap.put("RCPT_INST_NO", map.get("CNTER_ALTMNT_INST_NO")); 		//접수기관번호
		paramMap.put("RCPT_SE_CD", "11");                            		//접수구분코드 - 의뢰신청

		paramMap.put("RRQST_YN", "N"); 					                    //재의뢰여부
		paramMap.put("HPE_SRVC_YN", "N");   		                        //희망서비스여부
		paramMap.put("DEL_YN", "N"); 		                                //삭제여부
		paramMap.put("USER_ID", map.get("USER_ID"));                        //사용자id

		linkMediaMapper.insertSrvcPvsnRqstRcptHistory(paramMap);
		
	}

	/**
	 * @Method명   : insertSchlDgnssData
	 * @param map
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	private void insertSchlDgnssData(Map<String, String> map) throws Exception {
		ScpDb scpDb = new ScpDb();

		Map<String, String> paramMap = new HashMap<>(map);

		paramMap.put("DGNSS_EXMN_MNG_NO", linkMediaMapper.selectKeyValue(getKeyValue("DE"))); //진단조사관리번호
		paramMap.put("STDNT_NM_ENCPT", scpDb.scpEncB64(map.get("STDNT_NM")));                 //학생명암호화
		paramMap.put("PRTCR_FLNM_ENCPT", scpDb.scpEncB64(map.get("PRTCR_FLNM")));             //보호자성명암호화
		paramMap.put("PRTCR_TELNO_ENCPT", scpDb.scpEncB64(map.get("PRTCR_TELNO")));           //보호자전화번호암호화
		paramMap.put("EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("EML_ADDR")));                 //이메일주소암호화

		paramMap.put("HL_PIC_NM_ENCPT", scpDb.scpEncB64(map.get("HL_PIC_NM")));               //치유담당자명암호화
		paramMap.put("HL_PIC_EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("HL_PIC_EML_ADDR")));   //치유담당자이메일주소암호화
		paramMap.put("HL_PIC_TELNO_ENCPT", scpDb.scpEncB64(map.get("HL_PIC_TELNO")));         //치유담당자전화번호암호화

		linkMediaMapper.saveSchlDgnssData(paramMap);
	}

	/**
	 * @Method명   : insertInstDgnssData
	 * @param map
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 :
	 */
	private void insertInstDgnssData(Map<String, String> map) throws Exception {
		ScpDb scpDb = new ScpDb();

		Map<String, String> paramMap = new HashMap<>(map);

		paramMap.put("DGNSS_EXMN_MNG_NO", linkMediaMapper.selectKeyValue(getKeyValue("DE"))); //진단조사관리번호
		paramMap.put("STDNT_NM_ENCPT", scpDb.scpEncB64(map.get("STDNT_NM"))); 		//학생명암호화
		paramMap.put("STDNT_TELNO_ENCPT", scpDb.scpEncB64(map.get("STDNT_TELNO"))); //학생전화번호암호화
		paramMap.put("PRTCR_FLNM_ENCPT", scpDb.scpEncB64(map.get("PRTCR_FLNM")));   //보호자성명암호화
		paramMap.put("PRTCR_TELNO_ENCPT", scpDb.scpEncB64(map.get("PRTCR_TELNO"))); //보호자전화번호암호화

		paramMap.put("CNSLTNT_NM_ENCPT", scpDb.scpEncB64(map.get("CNSLTNT_NM")));	//상담원이름 
		paramMap.put("CNSLTNT_TELNO_ENCPT", scpDb.scpEncB64(map.get("CNSLTNT_TELNO").replace("-", ""))); //상담원연락처 

		linkMediaMapper.saveInstDgnssData(paramMap);
		
	}

	/**
	 * @Method명   : getKeyValue
	 * @param string
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	private Map<String, String> getKeyValue(String keyValue) {
		
		Map<String, String> keyMap = new HashMap<>();
		keyMap.put("SYS_CD", keyValue);
		keyMap.put("USER_ID", "BATCH");

		return keyMap;
	}

	
	/**
	 * @Method명   : getExmnSe
	 * @param string
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29. 
	 * @Method설명 :
	 */
	private String getExmnSe(String rschDivCd) {
		
		switch(rschDivCd) {
			case "ET" :
				return "1";
			case "SM" :
				return "2";
			case "CP" :
				return "3";
			case "CS" :
				return "4";
			case "PR" :
				return "5";
			case "GB" :
				return "6";
			case "AD" :
				return "7";
			default:
				return "1";	
		}
	} 
	
	/**
	 * @Method명   : getAcbgSe
	 * @param string
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 4. 17. 
	 * @Method설명 :
	 */
	private String getAcbgSe(String str) {
		
		if (str.contains("1")) {
			return "02";
		} else if (str.contains("2")) {
			return "03";
		} else {
			return "04";
		}
		
	}

	/**
	 * @Method명   : getLinkType
	 * @param string
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 4. 17. 
	 * @Method설명 :
	 */
	private String getLinkType(String param) {
		
		if (param.equals("ro") ) {
			return "08";
		}

		return "09";
	}

	/**
	 * @Method명   : getRqstCours
	 * @param string
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 8. 
	 * @Method설명 :
	 */
	private String getRqstCours(String str) {
		
		if (str.contains("1")) {
			return "02040102";
		} else if (str.contains("2")) {
			return "02040103";
		} else {
			return "02040104";
		}
	}
	
}
