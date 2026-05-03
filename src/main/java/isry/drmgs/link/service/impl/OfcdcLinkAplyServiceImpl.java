/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.drmgs.link.mapper.OfcdcLinkAplyMapper;
import isry.drmgs.link.service.OfcdcLinkAplyService;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.redis.service.RedisService;

/**
 * @파일명        : OfcdcLinkAplyServiceImpl.java
 * @프로그램 설명 : 교육청 연계신청
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 09. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 09. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("ofcdcLinkAplyService")
public class OfcdcLinkAplyServiceImpl extends IsryBaseServiceImpl implements OfcdcLinkAplyService {
	
private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="ofcdcLinkAplyMapper")
	private OfcdcLinkAplyMapper ofcdcLinkAplyMapper;

	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper; 			// 대상자정보조회 팝업 Mapper

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : processLinkRqstdoExcelUpload
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 10. 
	 * @Method설명 : 연계의뢰서 업로드(집단) 엑셀업로드
	 */
	@Override	
	public Map<String, String> processLinkRqstdoExcelUpload(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		String sEnfsnNo      = "";	// 세션정보의 종사자번호
		String sEnfsnInstNo  = "";	// 세션정보의 종사자기관번호
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId  	 = loginVO.getId();
			sEnfsnNo 	 = loginVO.getEnfsnNo();
			sEnfsnInstNo = String.valueOf(loginVO.getInstNo());
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		params = dataRequest.getParameterGroup("dsExcelUpload");
		List<Map<String, String>> dsExcelList = params.getAllRowList();
		
		Map<String, String> srvcPvsnRcptMap = new HashMap<>(); // 서비스제공접수T insert map	
		Map<String, String> srvcPvsnMap = new HashMap<>(); // 서비스제공의뢰T insert map				
		Map<String, String> resuilMap = new HashMap<>();
		Map<String, Object> subMap01 = new HashMap<>();
		Map<String, Object> subMap02 = new HashMap<>();
		Map<String, Object> subMap03 = new HashMap<>();
		Map<String, String> subMap04 = new HashMap<>();
		Map<String, Object> subResultMap04 = new HashMap<>(); // sub결과 mqp
		Map<String, String> regCntSEA200Map = new HashMap<>(); // 대상자정보T insert map
		Map<String, String> regCntSEA240Map = new HashMap<>(); // 학업중단T insert map
		Map<String, String> regCntSEA230Map = new HashMap<>(); // 학력상태T insert MAP
		
		BigDecimal iMngSn 	= new BigDecimal("0"); 		// 관리일련번호(SEQUENCES)
		BigDecimal bMngSn200 	= new BigDecimal("0"); 	// 관리일련번호(AKA220)
		String sUldRqstdoSttsSeCd = "01"; 				// 업로드의뢰서상태구분코드(01:신규등록)
		String sMblTelnoErr 	= ""; 	// 학생핸드폰번호 갯수
		String sTelnoErr    	= ""; 	// 학생전화번호번호 갯수
		int iMblTelnoErr 		= 0; 	// 학생핸드폰번호 갯수
		int iTelnoErr    		= 0; 	// 학생전화번호번호 갯수
		
//		int iSttyTelnoErr   	= 0; 	// 법정대리인전화번호번호 갯수
		String sSchulwCn		= "";	// 학업중단사유 					
		int iExceptionErr   	= 0; 	// Exception 오류 갯수
		String sDupChk   		= ""; 	// 중복등록확인
		String sStdntNm			= "";   // 학생이름(암호화)
		String sStdntBrth		= "";   // 학생생년월일
		String sStdntMblTelno	= "";   // 학생핸드폰번호(암호화)
//		String sStdntTelno		= "";   // 학생전화번호
//		String sStdntEml		= "";   // 학생이메일(암호화)
		String sStdntAddr		= "";   // 학생주소
		String sStdntAge		= "";   // 학생나이
		int iStdntAge		    = 0;    // 학생나이
		
		int iTot 				= 0; // 집계건수
		int iNewReg 			= 0; // 신규등록건수
		int iDpcn 				= 0; // 중복건수
		int iErrInfo 			= 0; // 오류정보건수
		int iSpcla 				= 0; // 특별관리건수
		int iFail 				= 0; // 실패건수
		String sTot 			= ""; // 집계건수
		String sNewReg 			= ""; // 신규등록건수
		String sDpcn 			= ""; // 중복건수
		String sErrInfo 		= ""; // 오류정보건수
		String sSpcla 			= ""; // 특별관리건수
		String sFail 			= ""; // 실패건수
		
		String sErrInfoDtlCn	= ""; // 오류내용
		String sSrvcPvsnRqstNo	= ""; // 서비스제공의뢰번호
		String sRqstTrprInfoNo	= ""; // 의뢰대상자정보번호
		
		int regCntSEA420  		= 0; // 서비스제공접수T 등록건수
		int regCntSEA400  		= 0; // 서비스제공의뢰T 등록건수
		int regCntSEA200  		= 0; // 대상자정보T 등록건수
		int regCntSEA240  		= 0; // 학업중단T 등록건수
		int regCntSEA230  		= 0; // 학력상태T 등록건수
		
		String sGradeSeCd       = ""; // 학력구분코드
		int iGradeSeCd          = 0;  // 학력구분코드 
		String sGradeSeCd1      = ""; // 학년구분코드
		int iGradeSeCd1         = 0;  // 학년구분코드
		
		String sSggCd			= ""; // 시군구코드	
		
		// 관리일련번호(SEQUENCES) 구하기
		subMap01 = ofcdcLinkAplyMapper.selectMngSn();
		iMngSn = (BigDecimal) subMap01.get("MNG_SN");
		LOGGER.debug("관리일련번호(SEQUENCES) ::::::::::: " + iMngSn);
		
		dmOutcomeDetailMap.put("MNG_SN",  iMngSn.toString()); 	// 관리일련번호
		dmOutcomeDetailMap.put("ENFSN_NO",  sEnfsnNo); 			// 종사자번호
		LOGGER.debug("종사자번호 ::::::::::: " + sEnfsnNo);
		
		try {
			ofcdcLinkAplyMapper.insertOfcdcGrFileUld(dmOutcomeDetailMap); // AKA220 insert
		} catch (Exception e) {
			LOGGER.debug("AKA221 ::::::::::: " + e.getMessage());
		}
		
		LOGGER.debug("dsExcel ::::::::::: " + dsExcelList.size());
		List<String> moeSchulmCodeList = ofcdcLinkAplyMapper.moeSchulmCodeList();
		for(int i=0; i<dsExcelList.size(); i++) {
			
			try {		
				
				iMblTelnoErr = dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT").length(); // 학생핸드폰번호 갯수
				
				// 관리일련번호(AKA220) 구하기
				subMap02 = ofcdcLinkAplyMapper.selectMngSn220(sEnfsnNo);
				bMngSn200 = (BigDecimal) subMap02.get("MNG_SN");
				dsExcelList.get(i).put("MNG_SN", bMngSn200.toString()); 					// 관리일련번호				
				LOGGER.debug("관리일련번호(AKA220) ==>> " + bMngSn200);
				
				dsExcelList.get(i).put("ENFSN_NO",  sEnfsnNo); 								// 종사자번호
				
				if("동의".equals(dsExcelList.get(i).get("STDNT_WRTCNS_SBMSN_YN"))) {
					dsExcelList.get(i).put("STDNT_WRTCNS_SBMSN_YN",  "Y"); 					// 학생동의서제출여부	
				}else {
					dsExcelList.get(i).put("STDNT_WRTCNS_SBMSN_YN",  "N"); 					// 학생동의서제출여부
				}
				if("동의".equals(dsExcelList.get(i).get("STTY_AGT_WRTCNS_SBMSN_YN"))) {
					dsExcelList.get(i).put("STTY_AGT_WRTCNS_SBMSN_YN",  "Y"); 				// 법정대리인동의서제출여부	
				}else {
					dsExcelList.get(i).put("STTY_AGT_WRTCNS_SBMSN_YN",  "N"); 				// 법정대리인동의서제출여부	
				}	
				
				LOGGER.debug("학생 성명 ::::::::::: " + dsExcelList.get(i).get("STDNT_FLNM_ENCPT"));				
				if(dsExcelList.get(i).get("STDNT_FLNM_ENCPT") == null && "".equals(dsExcelList.get(i).get("STDNT_FLNM_ENCPT")) && "null".equals(dsExcelList.get(i).get("STDNT_FLNM_ENCPT"))) {
					sErrInfoDtlCn += " / 학생 성명이 누락되었습니다.";
				}else {
//					dsExcelList.get(i).put("STDNT_FLNM_ENCPT",  mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_FLNM_ENCPT")))); // 학생성명 암호화	
					//dsExcelList.get(i).put("STDNT_FLNM_ENCPT",  scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_FLNM_ENCPT"))); // 학생성명 암호화
				}
				
				if(dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", "") == null && "".equals(dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", "")) && "null".equals(dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", ""))) {
					sErrInfoDtlCn += " / 생년월일이 없습니다.";
				}else {
					dsExcelList.get(i).put("STDNT_BRTH_YMD",  dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", ""));	// 학생출생일자	
				}
					
				dsExcelList.get(i).put("STDNT_SXDC_NM",  dsExcelList.get(i).get("STDNT_SXDC_NM"));	// 학생성별명
				
				if(dsExcelList.get(i).get("STDNT_ADDR") == null && "".equals(dsExcelList.get(i).get("STDNT_ADDR")) && "null".equals(dsExcelList.get(i).get("STDNT_ADDR"))) {
					sErrInfoDtlCn += " / 주소가 누락되었습니다.";
				}else {
					dsExcelList.get(i).put("STDNT_ADDR",  dsExcelList.get(i).get("STDNT_ADDR"));	// 학생주소	
				}
				
				if(dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT") == null && "".equals(dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT")) && "null".equals(dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT"))) {
					sErrInfoDtlCn += " / 연락처가 필요합니다.";
				}else {
//					dsExcelList.get(i).put("STDNT_MBL_TELNO_ENCPT",  mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT"))));	// 학생휴대전화번호암호화	
					dsExcelList.get(i).put("STDNT_MBL_TELNO_ENCPT",  dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT"));	// 학생휴대전화번호암호화
				}
				
				dsExcelList.get(i).put("STDNT_TELNO",  dsExcelList.get(i).get("STDNT_TELNO"));	// 학생전화번호
//				dsExcelList.get(i).put("STDNT_EML_ADDR_ENCPT",  mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_EML_ADDR_ENCPT"))));	// 학생이메일주소암호화
//				dsExcelList.get(i).put("STDNT_EML_ADDR_ENCPT",  scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_EML_ADDR_ENCPT")));	// 학생이메일주소암호화
				dsExcelList.get(i).put("STTY_AGT_CTTPC_TELNO",  dsExcelList.get(i).get("STTY_AGT_CTTPC_TELNO"));	// 법정대리인연락처전화번호
//				dsExcelList.get(i).put("STTY_AGT_EML_ADDR_ENCPT",  mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STTY_AGT_EML_ADDR_ENCPT"))));	// 법정대리인이메일주소암호화
//				dsExcelList.get(i).put("STTY_AGT_EML_ADDR_ENCPT",  scpDb.scpEncB64( dsExcelList.get(i).get("STTY_AGT_EML_ADDR_ENCPT")));	// 법정대리인이메일주소암호화
				dsExcelList.get(i).put("LAST_SCHL_GRADE_NM",  dsExcelList.get(i).get("LAST_SCHL_GRADE_NM"));	// 최종학교학년명
				dsExcelList.get(i).put("SCHULW_DSCNTC_YMD",  dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", ""));	// 학업중단일자
				dsExcelList.get(i).put("SCHULW_DSCNTC_CS_CN",  dsExcelList.get(i).get("SCHULW_DSCNTC_CS_CN"));	// 학업중단사유내용
				dsExcelList.get(i).put("HNCFRT_HPE_COSE_NM",  dsExcelList.get(i).get("HNCFRT_HPE_COSE_NM"));	// 향후희망진로명
				dsExcelList.get(i).put("FRST_RGTR_ID",  sUserId);
				dsExcelList.get(i).put("LAST_MDFR_ID",  sUserId);
			} catch (Exception e) {
				iExceptionErr++;
				iFail++; // 실패건수
				LOGGER.debug("AKA221 ::::::::::: " + e.getMessage());
			}
			
			LOGGER.debug("성명 암호화 테스트 111::::::::::: " + dsExcelList.get(i).get("STDNT_FLNM_ENCPT"));			
//			sStdntNm		= mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_FLNM_ENCPT")));   // 학생이름(암호화)
			sStdntNm		=  dsExcelList.get(i).get("STDNT_FLNM_ENCPT");   // 학생이름(암호화)
			LOGGER.debug("성명 암호화 테스트 222::::::::::: " + sStdntNm);
			sStdntBrth		= dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", "");   // 학생생년월일
//			sStdntMblTelno	= mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT")));   // 학생핸드폰번호(암호화)
			sStdntMblTelno	= dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT");   // 학생핸드폰번호(암호화)
//			sStdntTelno		= "";   // 학생전화번호
//			sStdntEml		= "";   // 학생이메일(암호화)			
			// 우선순위3_중복등록
			subMap03.put("STDNT_FLNM_ENCPT", sStdntNm);
			subMap03.put("STDNT_BRTH_YMD", sStdntBrth);
			subMap03.put("STDNT_SXDC_NM", dsExcelList.get(i).get("STDNT_SXDC_NM"));
			subMap03.put("STDNT_TELNO", dsExcelList.get(i).get("STDNT_TELNO"));
			subMap03.put("STDNT_MBL_TELNO_ENCPT", sStdntMblTelno);
			sDupChk = ofcdcLinkAplyMapper.selectDupChk(subMap03);
			
			// 우선순위2_특별관리
			sSchulwCn = dsExcelList.get(i).get("SCHULW_DSCNTC_CS_CN");
			LOGGER.debug("학업중단사유 ::::::::::: " + sSchulwCn);
//			STDNT_MBL_TELNO_ENCPT 학생핸드폰번호
//			STDNT_TELNO			  학생전화번호
//			String sMblTelnoErr 	= ""; 	// 학생핸드폰번호 갯수
//			String sTelnoErr    	= ""; 	// 학생전화번호번호 갯수
//			int iMblTelnoErr 		= 0; 	// 학생핸드폰번호 갯수
//			int iTelnoErr    		= 0; 	// 학생전화번호번호 갯수
			
			
			// 우선순위1_오류정보
//			iMblTelnoErr = dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT").length(); // 학생핸드폰번호 갯수
			sMblTelnoErr = String.valueOf(iMblTelnoErr); // 학생전화번호번호 갯수
			LOGGER.debug("학생핸드폰번호111 ::::::::::: " + iMblTelnoErr);
			LOGGER.debug("학생핸드폰번호222 ::::::::::: " + sMblTelnoErr);
			
			iTelnoErr = dsExcelList.get(i).get("STDNT_TELNO").length(); // 학생전화번호번호 갯수
			sTelnoErr = String.valueOf(iTelnoErr); // 학생전화번호번호 갯수
			LOGGER.debug("학생전화번호번호111 ::::::::::: " + iTelnoErr);
			LOGGER.debug("학생전화번호번호222 ::::::::::: " + sTelnoErr);
			
			sStdntAddr = dsExcelList.get(i).get("STDNT_ADDR"); // 학생주소
			LOGGER.debug("학생주소 ::::::::::: " + sStdntAddr);
			
			// 학생나이 구하기
			sStdntAge = ofcdcLinkAplyMapper.selectStdntAge(dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", ""));
			LOGGER.debug("sStdntAge ::::::::::: " + sStdntAge);
			iStdntAge = Integer.valueOf(sStdntAge);
			LOGGER.debug("학생나이 ::::::::::: " + iStdntAge);
			
			LOGGER.debug("sMblTelnoErr ::::::::::: " + sMblTelnoErr);			
//			LOGGER.debug("iSttyTelnoErr ::::::::::: " + iSttyTelnoErr);
			LOGGER.debug("sStdntAddr ::::::::::: " + sStdntAddr);
			LOGGER.debug("iStdntAge ::::::::::: " + iStdntAge);
			
			/*
			 * 코드 체계 변경에 따라 수정
			if(!sSchulwCn.contains("건강 문제") && !sSchulwCn.contains("해외 출국") && !sSchulwCn.contains("건강문제") && !sSchulwCn.contains("해외출국")) {
				if("0".equals(sDupChk)) {
					if(iExceptionErr != 0) {
						sUldRqstdoSttsSeCd = "05"; // 05:실패	
					}
				}else {
					sUldRqstdoSttsSeCd = "02"; // 02:중복등록	
				}
			} else {
				sUldRqstdoSttsSeCd = "04"; // 04:특별관리	
			}
			*/
			
			boolean bSpclaMngYn = false;
			for(String codeNm : moeSchulmCodeList) {
				// 문자열 비교를 위해 공백 제거
				sSchulwCn = sSchulwCn.replace(" ", "");
				codeNm = codeNm.replace(" ", "");
				if(codeNm.equals(sSchulwCn)) {
					bSpclaMngYn = true;
				}
			}
			if(!bSpclaMngYn) {
				if("0".equals(sDupChk)) {
					if(iExceptionErr != 0) {
						sUldRqstdoSttsSeCd = "05"; // 05:실패	
					}
				}else {
					sUldRqstdoSttsSeCd = "02"; // 02:중복등록	
				}
			} else {
				sUldRqstdoSttsSeCd = "04"; // 04:특별관리	
			}
			
			LOGGER.debug("학생핸드폰번호갯수	 ::::::::"          + sMblTelnoErr);
			LOGGER.debug("학생전화번호갯수	 ::::::::"          + iTelnoErr);
			
			// 2023.04.17 학생 휴대전화번호, 학생 전화번호 둘 중 하나만 정상이면 정상으로 로직 변경
			/*
			if(!"11".equals(sMblTelnoErr)){ // 학생핸드폰번호갯수 11개 확인
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
				sErrInfoDtlCn += " / 연락처가 유효하지 않습니다.";
			}
			
			if(10 > iTelnoErr){ // 학생전화번호
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
				sErrInfoDtlCn += " / 연락처가 유효하지 않습니다.";
			}
			*/
			if(!"11".equals(sMblTelnoErr) && 10 > iTelnoErr){
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
				sErrInfoDtlCn += " / 연락처가 유효하지 않습니다.";
			}
			if("null".equals(sStdntAddr) && "".equals(sStdntAddr) && sStdntAddr == null) {
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
			}
			if(10 > iStdntAge || 25 < iStdntAge) {
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
				sErrInfoDtlCn += " / 9세부터 24세까지만 해당연령 입니다.";
			}
			
			String sLastSchlGradeNm = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM");
			if("".equals(sLastSchlGradeNm) || null == sLastSchlGradeNm) {
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
				sErrInfoDtlCn += " / 학교정보가 유효하지 않습니다.";
			}
			
			// 2023-06-05 주소 유효성 체크
			String sSdSggNm = "";
			String sSggNm1 = "";
			String sSggNm2 = "";
			String sCtpvNM = "";
			String sCtpvNM1 = "";
			String sCtpvNM2 = "";
			if(!"".equals(dsExcelList.get(i).get("STDNT_ADDR")) && null != dsExcelList.get(i).get("STDNT_ADDR")) {
				String oCtpvNM[] = dsExcelList.get(i).get("STDNT_ADDR").split("\n");
				if(oCtpvNM.length > 2) {
					String oSggNm[] = oCtpvNM[1].split(" ");
					String ctpvList[] = {"강원","경기","경남","경상남도","경북","경상북도","광주","대구","대전","부산","서울","세종","울산","인천","전남","전라남도","전북","전라북도","제주","충남","충청남도","충북","충청북도"};
					boolean bCtpv = false; // 유효한 주소 여부
					for(int a = 0; a < ctpvList.length; a++) {
						if(oSggNm[0].indexOf(ctpvList[a]) > -1) {
							sSdSggNm = oSggNm[0]; 
							sSggNm1  = oSggNm[1];
							sSggNm2  = oSggNm[2];
							bCtpv = true; // 유효한 주소
						}
					}
					sCtpvNM = oCtpvNM[0];
					sCtpvNM1 = oCtpvNM[1];
					sCtpvNM2 = oCtpvNM[2];
					if(!bCtpv) { // 유효한 주소가 아님
						sUldRqstdoSttsSeCd = "03"; // 03:오류정보
						sErrInfoDtlCn += " / 주소가 유효하지 않습니다.";
					}
				} else {
					sUldRqstdoSttsSeCd = "03"; // 03:오류정보
					sErrInfoDtlCn += " / 주소가 유효하지 않습니다.";
				}
			} else {
				sUldRqstdoSttsSeCd = "03"; // 03:오류정보
				sErrInfoDtlCn += " / 주소가 유효하지 않습니다.";
			}
				
			  
			
			
			// 주소 유효성 체크 끝
			
			dsExcelList.get(i).put("ULD_RQSTDO_STTS_SE_CD",  sUldRqstdoSttsSeCd); 		// 업로드의뢰서상태구분코드 
			
			// SEB400(서비스제공의뢰) insert
			if("01".equals(sUldRqstdoSttsSeCd)) { // 01:신규등록 02:중복등록
				
				// 서비스제공의뢰번호 채번
				Map<String, String> seqMap1 = new HashMap<>();
				Map<String, Object> valMap1 = new HashMap<>();
				seqMap1.put("USER_ID",       sUserId);
				seqMap1.put("RENU_NO_SE_CD", "SQ");					// 서비스제공의뢰번호 채번코드
				seqMap1.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
				// 채번서비스 호출
				valMap1 = renuNoMapper.selectCaseMngNoRenu(seqMap1);
				sSrvcPvsnRqstNo = String.valueOf(valMap1.get("RENU_NO"));	// 서비스제공의뢰번호(SQ) 발번
				LOGGER.debug("서비스제공의뢰번호(SQ) 발번	 ::::::::"          + sSrvcPvsnRqstNo);
				
				srvcPvsnMap.put("SRVC_PVSN_RQST_NO", sSrvcPvsnRqstNo);				// 서비스제공의뢰번호
				srvcPvsnMap.put("RESRCE_NO", "");									// 자원번호
				srvcPvsnMap.put("HPE_SRVC_YN", "N");								// 희망서비스여부
				srvcPvsnMap.put("RQST_UNT_TASKWK_SE_CD", "U03");					// 의뢰단위업무구분코드
				srvcPvsnMap.put("RCPT_RQST_COURS_SE_CD", "02040101");				// 접수의뢰경로구분코드(02040101:교육청 및 교육지원청)
				srvcPvsnMap.put("RQST_YMD", DateUtil.getToday());					// 의뢰일자
				srvcPvsnMap.put("CLINT_NO", sEnfsnNo);								// 의뢰자번호_(교육청담당자 종사자번호)				
				srvcPvsnMap.put("RQST_INST_NO", sEnfsnInstNo);						// 의뢰기관번호_(소속교육청기관번호 종사자 기관번호)
				srvcPvsnMap.put("RQST_CS_CN", "교육청 집단 연계");						// 의뢰사유내용
				srvcPvsnMap.put("PRNMNT_USE_BGNG_YMD", "");							// 예정사용시작일자
				srvcPvsnMap.put("PRNMNT_USE_BGNG_HR", "");							// 예정사용시작시간
				srvcPvsnMap.put("PRNMNT_USE_END_YMD", "");							// 예정사용종료일자
				srvcPvsnMap.put("PRNMNT_USE_END_HR", "");							// 예정사용종료시간
				srvcPvsnMap.put("RCPT_UNT_TASKWK_SE_CD", "U03");					// 접수단위업무구분코드
				
				//String oCtpvNM[] = dsExcelList.get(i).get("STDNT_ADDR").split("\n");	 		
				//String oSggNm[] = oCtpvNM[1].split(" ");                        
				String sCtpvSeCd = "";
				if(sSdSggNm.indexOf("강원") >= 0 || sSdSggNm.indexOf("강원도") >= 0) {
					sSdSggNm = "강원도";
					sCtpvSeCd = "42";
				}
				if(sSdSggNm.indexOf("경기") >= 0 || sSdSggNm.indexOf("경기도") >= 0) {
					sSdSggNm = "경기도";
					sCtpvSeCd = "41";
				}
				if(sSdSggNm.indexOf("경남") >= 0 || sSdSggNm.indexOf("경상남도") >= 0) {
					sSdSggNm = "경상남도";
					sCtpvSeCd = "48";
				}
				if(sSdSggNm.indexOf("경북") >= 0 || sSdSggNm.indexOf("경상북도") >= 0) {
					sSdSggNm = "경상북도";
					sCtpvSeCd = "47";
				}
				if(sSdSggNm.indexOf("광주") >= 0 || sSdSggNm.indexOf("광주광역시") >= 0) {
					sSdSggNm = "광주광역시";
					sCtpvSeCd = "29";
				}
				if(sSdSggNm.indexOf("대구") >= 0 || sSdSggNm.indexOf("대구광역시") >= 0) {
					sSdSggNm = "대구광역시";
					sCtpvSeCd = "27";
				}
				if(sSdSggNm.indexOf("대전") >= 0 || sSdSggNm.indexOf("대전광역시") >= 0) {
					sSdSggNm = "대전광역시";
					sCtpvSeCd = "30";
				}
				if(sSdSggNm.indexOf("부산") >= 0 || sSdSggNm.indexOf("부산광역시") >= 0) {
					sSdSggNm = "부산광역시";
					sCtpvSeCd = "26";
				}
				if(sSdSggNm.indexOf("서울") >= 0 || sSdSggNm.indexOf("서울특별시") >= 0) {
					sSdSggNm = "서울특별시";
					sCtpvSeCd = "11";
				}
				if(sSdSggNm.indexOf("세종") >= 0 || sSdSggNm.indexOf("세종특별자치시") >= 0) {
					sSdSggNm = "세종특별자치시";
					sCtpvSeCd = "36";
				}
				if(sSdSggNm.indexOf("울산") >= 0 || sSdSggNm.indexOf("울산광역시") >= 0) {
					sSdSggNm = "울산광역시";
					sCtpvSeCd = "31";
				}
				if(sSdSggNm.indexOf("인천") >= 0 || sSdSggNm.indexOf("인천광역시") >= 0) {
					sSdSggNm = "인천광역시";
					sCtpvSeCd = "28";
				}
				if(sSdSggNm.indexOf("전남") >= 0 || sSdSggNm.indexOf("전라남도") >= 0) {
					sSdSggNm = "전라남도";
					sCtpvSeCd = "46";
				}
				if(sSdSggNm.indexOf("전북") >= 0 || sSdSggNm.indexOf("전라북도") >= 0) {
					sSdSggNm = "전라북도";
					sCtpvSeCd = "45";
				}
				if(sSdSggNm.indexOf("제주") >= 0 || sSdSggNm.indexOf("제주특별자치도") >= 0) {
					sSdSggNm = "제주특별자치도";
					sCtpvSeCd = "50";
				}
				if(sSdSggNm.indexOf("충남") >= 0 || sSdSggNm.indexOf("충청남도") >= 0) {
					sSdSggNm = "충청남도";
					sCtpvSeCd = "44";
				}
				if(sSdSggNm.indexOf("충북") >= 0 || sSdSggNm.indexOf("충청북도") >= 0) {
					sSdSggNm = "충청북도";
					sCtpvSeCd = "43";
				}
				LOGGER.debug("oCtpvNM[]	 @@@@@@@@"          + sCtpvNM1);
				LOGGER.debug("sSdSggNm	 @@@@@@@@"          + sSdSggNm);
				LOGGER.debug("oSggNm[1]	 @@@@@@@@"          + sSggNm1);
				
				subMap04.put("CTPV_NM", sSdSggNm); 
				subMap04.put("SGG_NM", sSggNm1);
				subMap04.put("SGG_NM1", sSggNm1 + " " + sSggNm2);
				subMap04.put("CTPV_SE_CD", sCtpvSeCd);
				
				// 기관번호,담당자종사자번호 구하기
				List<Map<String, Object>> list = ofcdcLinkAplyMapper.selectInstEnfsnNo(subMap04);
				if(list.size() > 0) {
					subResultMap04 = list.get(0);
				} else {
					sErrInfoDtlCn += " / 주소로 지역을 찾을 수 없습니다.";
				}
				
				
				if("".equals(subResultMap04.get("INST_NO")) || null == subResultMap04.get("INST_NO")) {
					sErrInfoDtlCn += " / 주소로 지역을 찾을 수 없습니다.";
				}
				if(subResultMap04 != null) {
					sSggCd = subResultMap04.get("SGG_CD").toString(); // 시군구코드
					srvcPvsnMap.put("CLR_NO", subResultMap04.get("PIC_ENFSN_NO").toString());	// 접수자번호
					srvcPvsnMap.put("RCPT_INST_NO", subResultMap04.get("INST_NO").toString());	// 접수기관번호
				}
				// 의뢰대상자정보번호 채번
				Map<String, String> seqMap2 = new HashMap<>();
				Map<String, Object> valMap2 = new HashMap<>();
				seqMap2.put("USER_ID",       sUserId);
				seqMap2.put("RENU_NO_SE_CD", "TR");					// 의뢰대상자정보번호 채번코드
				seqMap2.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
				// 채번서비스 호출
				valMap2 = renuNoMapper.selectCaseMngNoRenu(seqMap2);
				sRqstTrprInfoNo = String.valueOf(valMap2.get("RENU_NO"));	// 의뢰대상자정보번호(TR) 발번
				LOGGER.debug("의뢰대상자정보번호(TR) 발번	 ::::::::"          + sRqstTrprInfoNo);
				
				// 엑셀업로드 추가정보 내용
				String lastSchlGradeNm  = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM");						// 최종학교학년명
				String schulwDscntcYmd  = dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "-");	// 학업중단일자
				String schulwDscntcCsCn = dsExcelList.get(i).get("SCHULW_DSCNTC_CS_CN");					// 학업중단사유내용
				String hncfrtHpeCoseNm  = dsExcelList.get(i).get("HNCFRT_HPE_COSE_NM");						// 향후희망진로명
				String rqstDtlCn = "";	// 의뢰 상세내용
				
				rqstDtlCn  = "엑셀업로드 추가정보";
				rqstDtlCn += "\n최종학교 및 학년  : "  + lastSchlGradeNm;
				rqstDtlCn += "\n학업 중단 일자     : " + schulwDscntcYmd;
				rqstDtlCn += "\n학업중단사유내용 : "   + schulwDscntcCsCn;
				rqstDtlCn += "\n향후 희망 진로 명  : " + hncfrtHpeCoseNm;
				// 엑셀업로드 추가정보 내용 끝
				
				srvcPvsnMap.put("RQST_TRPR_INFO_NO", sRqstTrprInfoNo);				// 의뢰대상자정보번호
//				srvcPvsnMap.put("FRST_RQST_NO", sSrvcPvsnRqstNo);					// 최초의뢰번호_컬럼삭제됨
				srvcPvsnMap.put("CASE_MNG_NO", "");									// 사례관리번호
				srvcPvsnMap.put("CASE_MNG_ODRNO", "");								// 사례관리차수
				srvcPvsnMap.put("RQST_APLY_YN", "Y");								// 의뢰신청여부
				srvcPvsnMap.put("RQST_DTL_CN", rqstDtlCn);							// 의뢰상세내용
				srvcPvsnMap.put("LINK_TYPE_SE_CD", "06");							// 연계유형구분코드(06:교육부연계)
				srvcPvsnMap.put("ATFINO", "");										// 첨부파일번호
				srvcPvsnMap.put("TRMN_PRCS_YN", "N");								// 종결처리여부
				srvcPvsnMap.put("DEL_YN", "N");										// 삭제여부
				srvcPvsnMap.put("FRST_RGTR_ID", sUserId);
				srvcPvsnMap.put("LAST_MDFR_ID", sUserId);
				
				regCntSEA400 =ofcdcLinkAplyMapper.insertSrvcPvsnRqst(srvcPvsnMap); // SEB400(서비스제공의뢰) insert
				
				if (regCntSEA400 > 0) {
					// 데이터변경구분코드
					srvcPvsnMap.put("DATAA_CHG_SE_CD", "I");					
					ofcdcLinkAplyMapper.insertSrvcPvsnRqstHstr(srvcPvsnMap); // SEB401(서비스제공의뢰이력) insert			
				}
				
				srvcPvsnRcptMap.put("SRVC_PVSN_RQST_NO", sSrvcPvsnRqstNo);						// 서비스제공의뢰번호				
				srvcPvsnRcptMap.put("RESRCE_NO", "");											// 자원번호
				srvcPvsnRcptMap.put("LINK_TYPE_SE_CD", "06");									// 연계유형구분코드
				srvcPvsnRcptMap.put("RCPT_UNT_TASKWK_SE_CD", "U03");							// 접수단위업무구분코드
				srvcPvsnRcptMap.put("CLR_NO", subResultMap04.get("PIC_ENFSN_NO").toString());	// 접수자번호
				srvcPvsnRcptMap.put("RCPT_TRPR_INFO_NO", sRqstTrprInfoNo);						// 접수대상자정보번호
				srvcPvsnRcptMap.put("FRST_RQST_NO", sSrvcPvsnRqstNo);							// 최초의뢰번호
				srvcPvsnRcptMap.put("RCPT_INST_NO", subResultMap04.get("INST_NO").toString());	// 접수기관번호
				srvcPvsnRcptMap.put("RCPT_YMD", DateUtil.getToday());							// 접수일자
				srvcPvsnRcptMap.put("RCPT_SE_CD", "11");										// 접수구분코드
				srvcPvsnRcptMap.put("RCPT_DTL_CN", "");											// 접수상세내용
				srvcPvsnRcptMap.put("RJCT_CS_SE_CD", "");										// 반려사유구분코드
				srvcPvsnRcptMap.put("RJCT_CS_ETC_CN", "");										// 반려사유기타내용
				srvcPvsnRcptMap.put("RRQST_YN", "N");											// 재의뢰여부
				srvcPvsnRcptMap.put("RRQST_RCPT_UNT_TASKWK_SE_CD", "");							// 재의뢰접수단위업무구분코드
				srvcPvsnRcptMap.put("RRQST_CLR_NO", "");										// 재의뢰접수자번호
				srvcPvsnRcptMap.put("RRQST_RCPT_INST_NO", "");									// 재의뢰접수기관번호
				srvcPvsnRcptMap.put("RRQST_YMD", "");											// 재의뢰일자
				srvcPvsnRcptMap.put("APRV_USE_BGNG_YMD", "");									// 승인사용시작일자
				srvcPvsnRcptMap.put("APRV_USE_BGNG_HR", "");									// 승인사용시작시간
				srvcPvsnRcptMap.put("APRV_USE_END_YMD", "");									// 승인사용종료일자
				srvcPvsnRcptMap.put("APRV_USE_END_HR", "");										// 승인사용종료시간
				srvcPvsnRcptMap.put("HPE_SRVC_YN", "N");										// 희망서비스여부
				srvcPvsnRcptMap.put("ATFINO", "");												// 첨부파일번호
				srvcPvsnRcptMap.put("FRST_RGTR_ID", sUserId);
				srvcPvsnRcptMap.put("LAST_MDFR_ID", sUserId);
				
				regCntSEA420 =ofcdcLinkAplyMapper.insertSrvcPvsnRcpt(srvcPvsnRcptMap); // SEB420(서비스제공접수) insert
				
				if (regCntSEA420 > 0) {
					// 데이터변경구분코드
					srvcPvsnRcptMap.put("DATAA_CHG_SE_CD", "I");					
					ofcdcLinkAplyMapper.insertSrvcPvsnRcptHstr(srvcPvsnRcptMap); // SEB421(서비스제공접수이력) insert			
				}
				
				regCntSEA200Map.put("TRPR_INFO_NO", sRqstTrprInfoNo);				// 대상자정보번호
				regCntSEA200Map.put("INDV_IDNTFC_NO", "");							// 개인식별번호
				regCntSEA200Map.put("UNT_TASKWK_SE_CD", "U03");						// 단위업무구분코드
				
				LOGGER.debug("대상자명암호화	 ::::::::" + dsExcelList.get(i).get("STDNT_FLNM_ENCPT"));
//				regCntSEA200Map.put("TRPR_NM_ENCPT", mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_FLNM_ENCPT")))); // 대상자명암호화
				regCntSEA200Map.put("TRPR_NM_ENCPT", dsExcelList.get(i).get("STDNT_FLNM_ENCPT")); // 대상자명암호화
				regCntSEA200Map.put("TRPR_BRTH_YMD", dsExcelList.get(i).get("STDNT_BRTH_YMD").replace(".", "")); // 대상자출생일자
				
				if("남성".equals(dsExcelList.get(i).get("STDNT_SXDC_NM"))) {
					regCntSEA200Map.put("SXDC_SE_CD", "M");							// 성별구분코드	
				}else if("여성".equals(dsExcelList.get(i).get("STDNT_SXDC_NM"))) {
					regCntSEA200Map.put("SXDC_SE_CD", "F");							// 성별구분코드
				}else {
					regCntSEA200Map.put("SXDC_SE_CD", "X");							// 성별구분코드
				}
				
				regCntSEA200Map.put("MRG_YN", "N");									// 결혼여부
				regCntSEA200Map.put("TRPR_USER_ID", "");							// 대상자사용자아이디
				regCntSEA200Map.put("TRPR_TELNO", dsExcelList.get(i).get("STDNT_TELNO")); // 대상자전화번호
				
//				regCntSEA200Map.put("MBL_TELNO_ENCPT", mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT")))); // 휴대전화번호암호화
//				regCntSEA200Map.put("EML_ADDR_ENCPT", mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STDNT_EML_ADDR_ENCPT")))); // 이메일주소암호화
				regCntSEA200Map.put("MBL_TELNO_ENCPT", dsExcelList.get(i).get("STDNT_MBL_TELNO_ENCPT")); // 휴대전화번호암호화
				regCntSEA200Map.put("EML_ADDR_ENCPT", dsExcelList.get(i).get("STDNT_EML_ADDR_ENCPT")); // 이메일주소암호화
				
				regCntSEA200Map.put("SNS_SE_CD", "");								// SNS구분코드
				regCntSEA200Map.put("MSNGR_ID_ENCPT", "");							// 메신저아이디암호화
				regCntSEA200Map.put("ZIP", sCtpvNM.trim());						// 우편번호
				regCntSEA200Map.put("PST_ADDR", sCtpvNM1);						// 우편주소
				regCntSEA200Map.put("DADDR", sCtpvNM2);							// 상세주소
				
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("미취학");
				LOGGER.debug("iGradeSeCd111	 ::::::::"          + iGradeSeCd);				
				if(iGradeSeCd > -1) {
					sGradeSeCd = "01";
				}
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("초등학교");
				LOGGER.debug("iGradeSeCd222	 ::::::::"          + iGradeSeCd);
				if(iGradeSeCd > -1) {
					sGradeSeCd = "02";
				}
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("중학교");
				LOGGER.debug("iGradeSeCd333	 ::::::::"          + iGradeSeCd);
				if(iGradeSeCd > -1) {
					sGradeSeCd = "03";
				}
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("고등학교");
				LOGGER.debug("iGradeSeCd444	 ::::::::"          + iGradeSeCd);
				if(iGradeSeCd > -1) {
					sGradeSeCd = "04";
				}	
				
				LOGGER.debug("학력구분코드111	 ::::::::"          + sGradeSeCd);
				LOGGER.debug("학력구분코드222	 ::::::::"          + dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").indexOf("고등학교"));
				
				//20221009 : 강화영 : 교육청 엑셀 업로드 관련하여 사례대상자유형구분코드, 사례관리구분코드 수정
				
				regCntSEA200Map.put("ACBG_SE_CD", sGradeSeCd);						// 학력구분코드
				
				regCntSEA200Map.put("PSPT_ENG_FLNM_ENCPT", "");						// 여권영문성명암호화
				regCntSEA200Map.put("TRPR_BCRN_TYPE_SE_CD", "");					// 대상자배경유형구분코드
				regCntSEA200Map.put("TRPR_BCRN_TYPE_RM_NM", "");					// 대상자배경유형비고명
				regCntSEA200Map.put("NLTY_NTN_SE_CD", "");							// 국적국가구분코드
				regCntSEA200Map.put("ENTCNY_YMD", "");								// 입국일자
				regCntSEA200Map.put("BRTH_NTN_SE_CD", "");							// 출생국가구분코드
				regCntSEA200Map.put("GROWTH_NTN_SE_CD", "");						// 성장국가구분코드
				regCntSEA200Map.put("VISA_TYPE_SE_CD", "");							// 비자유형구분코드
				regCntSEA200Map.put("HANAWO_PENU_NO", "");							// 하나원기수번호
				regCntSEA200Map.put("RELGN_SE_CD", "");								// 종교구분코드
				regCntSEA200Map.put("QLFC_INFO_MNG_NO", "");						// 자격정보관리번호
				regCntSEA200Map.put("RCPT_PIC_NO", subResultMap04.get("PIC_ENFSN_NO").toString());						// 접수담당자번호
				regCntSEA200Map.put("RCPT_INST_NO", subResultMap04.get("INST_NO").toString());					// 접수기관번호
				regCntSEA200Map.put("CASE_TRPR_TYPE_SE_CD", "06");					// 사례대상자유형구분코드(발굴대상자)
				regCntSEA200Map.put("CASE_MNG_SE_CD", "01");						// 사례관리구분코드(사례대상자미선정)
				regCntSEA200Map.put("CASE_TRPR_SLCTN_YMD", "");						// 사례대상자선정일자
				regCntSEA200Map.put("CASE_TRPR_UNSL_CS_CN", "");					// 사례대상자미선정사유내용
				regCntSEA200Map.put("PRTCR_NM_ENCPT", "");							// 보호자명암호화
				regCntSEA200Map.put("TRPR_REL_SE_CD", "");							// 대상자관계구분코드
				regCntSEA200Map.put("LIVTGT_YN", "N");								// 동거여부 
				if("동의".equals(dsExcelList.get(i).get("STTY_AGT_WRTCNS_SBMSN_YN"))) {
					regCntSEA200Map.put("PRTCR_AGRE_YN", "Y");						// 보호자동의여부	
				}else {
					regCntSEA200Map.put("PRTCR_AGRE_YN", "N");						// 보호자동의여부
				}
				if("동의".equals(dsExcelList.get(i).get("STDNT_WRTCNS_SBMSN_YN"))) {
					regCntSEA200Map.put("PRVC_PVSN_AGRE_YN", "Y");					// 개인정보제공동의여부
				}else {
					regCntSEA200Map.put("PRVC_PVSN_AGRE_YN", "N");					// 개인정보제공동의여부
				}
				regCntSEA200Map.put("SRVC_CTRT_AGRE_YN", "N");						// 서비스계약동의여부
				regCntSEA200Map.put("TAG_NM", "");									// 태그명
				regCntSEA200Map.put("TAG_NO", "");									// 태그번호
				regCntSEA200Map.put("CASE_TRPR_NOAP_CS_SE_CD", "");					// 사례대상자미신청사유구분코드
				regCntSEA200Map.put("PRTCR_TELNO", dsExcelList.get(i).get("STTY_AGT_CTTPC_TELNO")); // 보호자전화번호
				regCntSEA200Map.put("PRTCR_MBL_TELNO_ENCPT", "");					// 보호자휴대전화번호암호화
				
//				regCntSEA200Map.put("PRTCR_EML_ADDR_ENCPT", mask.addressMasking( scpDb.scpEncB64( dsExcelList.get(i).get("STTY_AGT_EML_ADDR_ENCPT")))); // 보호자이메일주소암호화
				regCntSEA200Map.put("PRTCR_EML_ADDR_ENCPT", dsExcelList.get(i).get("STTY_AGT_EML_ADDR_ENCPT")); // 보호자이메일주소암호화
				
				regCntSEA200Map.put("PRTCR_ZIP", "");								// 보호자우편번호
				regCntSEA200Map.put("PRTCR_PST_ADDR", "");							// 보호자우편주소
				regCntSEA200Map.put("PRTCR_DADDR", "");								// 보호자상세주소
				regCntSEA200Map.put("RESDN_SHAPE_SE_CD", "");						// 거주형태구분코드
				regCntSEA200Map.put("RESIDE_SHAPE_SE_CD", "");						// 주거형태구분코드
				regCntSEA200Map.put("RESIDE_SHAPE_ETC_CN", "");						// 주거형태기타내용
				regCntSEA200Map.put("FAM_CHAR_SE_CD", "");							// 가족특성구분코드
				regCntSEA200Map.put("INDV_IDNTFC_INFO_UNIPT_CS_CN", "");			// 개인식별정보미입력사유내용
				regCntSEA200Map.put("INDV_IDNTFC_CRTR_YMD", "");					// 개인식별기준일자
				regCntSEA200Map.put("ORGNL_USER_ID", "");							// 원본사용자아이디
				regCntSEA200Map.put("FRST_RGTR_ID", sUserId);
				regCntSEA200Map.put("LAST_MDFR_ID", sUserId);
				
				// 2022.08.17 대상자정보,학업중단,학력상태 insert 추가
				regCntSEA200 = ofcdcLinkAplyMapper.insertTrprInfo(regCntSEA200Map); // SEA200(대상자정보) insert				
				if (regCntSEA200 > 0) {
					// 데이터변경구분코드
					regCntSEA200Map.put("DATAA_CHG_SE_CD", "I");					
					ofcdcLinkAplyMapper.insertTrprInfoHstr(regCntSEA200Map); // SEA201(대상자정보이력) insert					
				}
				
				if(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "") != null && !"".equals(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "")) && !"null".equals(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", ""))) {
					
					regCntSEA240Map.put("TRPR_INFO_NO", sRqstTrprInfoNo);				// 대상자정보번호
					regCntSEA240Map.put("INDV_IDNTFC_NO", "");							// 개인식별번호
					regCntSEA240Map.put("MNG_SN", "1");									// 관리일련번호 TODO 확인
					regCntSEA240Map.put("SCHULW_DSCNTC_YMD", dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "")); // 학업중단일자
					
					iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("미취학");
					if(iGradeSeCd > -1) {
						sGradeSeCd = "01";
					}
					iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("초등학교");
					if(iGradeSeCd > -1) {
						sGradeSeCd = "02";
					}
					iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("중학교");
					if(iGradeSeCd > -1) {
						sGradeSeCd = "03";
					}
					iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("고등학교");
					if(iGradeSeCd > -1) {
						sGradeSeCd = "04";
					}				
					
					regCntSEA240Map.put("ACBG_SE_CD", sGradeSeCd);						// 학력구분코드
					
					iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("1");
					if(iGradeSeCd1 > -1) {
						sGradeSeCd1 = "01";
					}
					iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("2");
					if(iGradeSeCd1 > -1) {
						sGradeSeCd1 = "02";
					}
					iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("3");
					if(iGradeSeCd1 > -1) {
						sGradeSeCd1 = "03";
					}
					iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("4");
					if(iGradeSeCd1 > -1) {
						sGradeSeCd1 = "04";
					}	
					iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("5");
					if(iGradeSeCd1 > -1) {
						sGradeSeCd1 = "05";
					}	
					iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("6");
					if(iGradeSeCd1 > -1) {
						sGradeSeCd1 = "06";
					}	
					
					LOGGER.debug("학년구분코드111	 ::::::::"          + sGradeSeCd1);
					LOGGER.debug("학년구분코드222	 ::::::::"          + dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").indexOf("3"));
					
					regCntSEA240Map.put("GRADE_SE_CD", sGradeSeCd1);					// 학년구분코드
					
					regCntSEA240Map.put("DSCNTC_CS_LCLAS_SE_CD", "99");					// 중단사유대분류구분코드 
					regCntSEA240Map.put("DSCNTC_CS_SCLAS_SE_CD", "");					// 중단사유소분류구분코드
					regCntSEA240Map.put("RM_CN", dsExcelList.get(i).get("SCHULW_DSCNTC_CS_CN") + " / " + dsExcelList.get(i).get("HNCFRT_HPE_COSE_NM")); // 비고내용
					regCntSEA240Map.put("DEL_YN", "N");									// 삭제여부
					regCntSEA240Map.put("FRST_RGTR_ID", sUserId);
					regCntSEA240Map.put("LAST_MDFR_ID", sUserId);
					
					regCntSEA240 = ofcdcLinkAplyMapper.insertSchulwDscntc(regCntSEA240Map); // SEA240(학업중단) insert				
					if (regCntSEA240 > 0) {
						// 데이터변경구분코드
						regCntSEA240Map.put("DATAA_CHG_SE_CD", "I");					
						ofcdcLinkAplyMapper.insertSchulwDscntcHstr(regCntSEA240Map); // SEA241(학업중단이력) insert					
					}	
				} // end if(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "") != null && !"".equals(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "")) && !"null".equals(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", ""))) {
				
				regCntSEA230Map.put("TRPR_INFO_NO", sRqstTrprInfoNo);				// 대상자정보번호
				regCntSEA230Map.put("INDV_IDNTFC_NO", "");							// 개인식별번호
				regCntSEA230Map.put("MNG_SN", "1");									// 관리일련번호
				regCntSEA230Map.put("DGRI_ACQS_ERA_YM", "");						// 학위취득시기연월
				regCntSEA230Map.put("MTCLTN_YMD", "");								// 입학일자
				regCntSEA230Map.put("GRDTN_YMD", "");								// 졸업일자
				
				String sSchlNm[] = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").split(",");
				
	 			regCntSEA230Map.put("SCHL_NM", sSchlNm[0]);							// 학교명
				
	 			regCntSEA230Map.put("DGRI_ACQS_MTHD_SE_CD", "");					// 학위취득방법구분코드
	 			
	 			iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("미취학");
				if(iGradeSeCd > -1) {
					sGradeSeCd = "01";
				}
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("초등학교");
				if(iGradeSeCd > -1) {
					sGradeSeCd = "02";
				}
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("중학교");
				if(iGradeSeCd > -1) {
					sGradeSeCd = "03";
				}
				iGradeSeCd = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("고등학교");
				if(iGradeSeCd > -1) {
					sGradeSeCd = "04";
				}				
				
	 			regCntSEA230Map.put("ACBG_SE_CD", sGradeSeCd);						// 학력구분코드
	 			
	 			regCntSEA230Map.put("SGG_CD", sSggCd);								// 시군구코드
	 			regCntSEA230Map.put("MAJOR_NM", "");								// 전공명
	 			regCntSEA230Map.put("GRDTN_STTS_SE_CD", "01");						// 졸업상태구분코드 01:재학
	 			
	 			iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("1");
				if(iGradeSeCd1 > -1) {
					sGradeSeCd1 = "01";
				}
				iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("2");
				if(iGradeSeCd1 > -1) {
					sGradeSeCd1 = "02";
				}
				iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("3");
				if(iGradeSeCd1 > -1) {
					sGradeSeCd1 = "03";
				}
				iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("4");
				if(iGradeSeCd1 > -1) {
					sGradeSeCd1 = "04";
				}	
				iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("5");
				if(iGradeSeCd1 > -1) {
					sGradeSeCd1 = "05";
				}	
				iGradeSeCd1 = dsExcelList.get(i).get("LAST_SCHL_GRADE_NM").trim().indexOf("6");
				if(iGradeSeCd1 > -1) {
					sGradeSeCd1 = "06";
				}	
				
	 			regCntSEA230Map.put("GRADE_SE_CD", sGradeSeCd1);					// 학년구분코드
	 			
	 			regCntSEA230Map.put("STCLS_NO", "");								// 학급번호
	 			
	 			if(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "") == null && "".equals(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", "")) && "null".equals(dsExcelList.get(i).get("SCHULW_DSCNTC_YMD").replace(".", ""))) {
	 				regCntSEA230Map.put("SCHULW_DSCNTC_YN", "N");					// 학업중단여부
	 			}else {
	 				regCntSEA230Map.put("SCHULW_DSCNTC_YN", "Y");					// 학업중단여부
	 			}
	 			
	 			regCntSEA230Map.put("RM_CN", "");									// 비고내용
	 			regCntSEA230Map.put("DEL_YN", "N");									// 삭제여부
				regCntSEA230Map.put("FRST_RGTR_ID", sUserId);
				regCntSEA230Map.put("LAST_MDFR_ID", sUserId);
				
				regCntSEA230 = ofcdcLinkAplyMapper.insertAcbgStts(regCntSEA230Map); // SEA230(학력상태) insert
				if (regCntSEA230 > 0) {
					// 데이터변경구분코드
					regCntSEA230Map.put("DATAA_CHG_SE_CD", "I");					
					ofcdcLinkAplyMapper.insertAcbgSttsHstr(regCntSEA230Map); // SEA231(학력상태이력) insert					
				}
				
			} // end if("01".equals(sUldRqstdoSttsSeCd) || "02".equals(sUldRqstdoSttsSeCd)) { // 01:신규등록 02:중복등록
			
			if("02".equals(sUldRqstdoSttsSeCd)) {
				sErrInfoDtlCn += " / 중복된 데이터 입니다.";
			}
			LOGGER.debug("오류정보상세내용	 ::::::::"          + sErrInfoDtlCn);
			dsExcelList.get(i).put("ERR_INFO_DTL_CN",  sErrInfoDtlCn); 		// 오류정보상세내용 
			
			switch (sUldRqstdoSttsSeCd) {
			case "01":	//신규등록
				iNewReg++;
				break;
			case "02":	//중복등록
				iDpcn++;
				break;
			case "03":	//오류정보
				iErrInfo++;
				break;
			case "04":	//특별관리
				iSpcla++;
				break;
			default:
				iFail++; //실패
				break;
			}
			
			ofcdcLinkAplyMapper.insertOfcdcGrFileUldDtl(dsExcelList.get(i)); // AKA221 insert
			
			// 초기화
			sUldRqstdoSttsSeCd = "01"; 
			sErrInfoDtlCn      = "";
			
			iTot++; // 집계건수
			
		} // end for(int i=0; i<dsExcelList.size(); i++) {	
		
		sTot = String.valueOf(iTot);
		sNewReg = String.valueOf(iNewReg);
		sDpcn = String.valueOf(iDpcn);
		sErrInfo = String.valueOf(iErrInfo);
		sSpcla = String.valueOf(iSpcla);
		sFail = String.valueOf(iFail);
			
		resuilMap.put("sEnfsnNo", sEnfsnNo);			// 관리일련번호
		resuilMap.put("MNG_SN"  , iMngSn.toString());	// 종사자번호
		resuilMap.put("sTot"	, sTot);
		resuilMap.put("sNewReg"	, sNewReg);
		resuilMap.put("sDpcn"	, sDpcn);
		resuilMap.put("sErrInfo", sErrInfo);
		resuilMap.put("sSpcla"	, sSpcla);
		resuilMap.put("sFail"	, sFail);
		resuilMap.put("LAST_MDFR_ID", sUserId);
		
		// 교육청집단연계(AKA220) update
		ofcdcLinkAplyMapper.updateLinkRqst(resuilMap);
		
		return null;	
	}
	
	/**
	 * @Method명   : selectLinkRqstList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 10. 
	 * @Method설명 : 연계의뢰서 업로드(집단) 조회
	 */
	public List<Map<String, String>> selectLinkRqstList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sEnfsnNo      = "";	// 세션정보의 종사자번호
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sEnfsnNo = loginVO.getEnfsnNo();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		String trprNm = null;
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		} else {
			trprNm = searchParam.getValue("PIC_FLNM");	//내담자성명
		}
		
		Map<String, String> paramMap = searchParam.getSingleValueMap();
		
		paramMap.put("ENFSN_NO", sEnfsnNo);
		
		return ofcdcLinkAplyMapper.selectLinkRqstList(paramMap);
		
	}
	
	
	/**
	 * @Method명   : selectOfcdcSpclaMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 : 특별관리리스트 조회
	 */
	public List<Map<String, String>> selectOfcdcSpclaMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sEnfsnNo      = "";	// 세션정보의 종사자번호
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sEnfsnNo = loginVO.getEnfsnNo();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = searchParam.getSingleValueMap();
		int instNo = loginVO.getInstNo();
		String grpAuth = loginVO.getGroupAuthrtSeCd();
		String allAuth = "N"; // 중앙관리기관 여부
		if("1".equals(grpAuth.substring(0, 1)) || "2".equals(grpAuth.substring(0, 1))) {
			allAuth = "Y";
		}
		paramMap.put("AUTH", allAuth);
		String moeCd = ofcdcLinkAplyMapper.selectMoeCode(instNo);
		
		paramMap.put("ENFSN_NO", moeCd);
		
		List<Map<String, String>> resultMap = ofcdcLinkAplyMapper.selectOfcdcSpclaMngList(paramMap);
		
		return resultMap;
	}
	
	/**
	 * @Method명   : selectOfcdcErrorInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 : 오류정보리스트 조회
	 */
	public List<Map<String, String>> selectOfcdcErrorInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = searchParam.getSingleValueMap();
		int instNo = loginVO.getInstNo();
		String grpAuth = loginVO.getGroupAuthrtSeCd();
		String allAuth = "N"; // 중앙관리기관 여부
		if("1".equals(grpAuth.substring(0, 1)) || "2".equals(grpAuth.substring(0, 1))) {
			allAuth = "Y";
		}
		paramMap.put("AUTH", allAuth);
		String moeCd = ofcdcLinkAplyMapper.selectMoeCode(instNo);
		
		paramMap.put("ENFSN_NO", moeCd);
		
		List<Map<String, String>> resultMap = ofcdcLinkAplyMapper.selectOfcdcErrorInfoList(paramMap);
		
		return resultMap;
	}
	
	/**
	 * @Method명   : selectLinkRqstDetList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 04. 18.
	 * @Method설명 : 연계의뢰서 업로드(집단) 상세 조회
	 */
	public List<Map<String, String>> selectLinkRqstDetList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = searchParam.getSingleValueMap();
		
		return ofcdcLinkAplyMapper.selectLinkRqstDetList(paramMap);
		
	}
}

