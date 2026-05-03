/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.membermanage.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
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

import isry.itgcms.sysmgmt.history.mapper.PersonalHistoryMapper;
import isry.itgcms.sysmgmt.membermanage.mapper.MemberManageMapper;
import isry.itgcms.sysmgmt.membermanage.service.MemberManageService;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtOrgDtlMapper;
import isry.itgcms.sysmgmt.userjoin.mapper.ReqUserJoinMapper;
import isry.itgcms.sysmgmt.userlogin.mapper.UserLoginMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.PasswordHelper;
import isry.itgcms.util.RandomString;
import isry.itgcms.util.UserException;
import isry2.itgcms.syscmmn.sms.mapper.SmsMapper;

/**
 * @파일명        : MemberManageServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 4. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 4. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("memberManageService")
public class MemberManageServiceImpl implements MemberManageService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "memberManageMapper")
	private MemberManageMapper memberManageMapper;

	@Resource(name = "reqUserJoinMapper")
	private ReqUserJoinMapper reqUserJoinMapper;
	
	@Resource(name = "personalInfoMapper")
	private PersonalInfoMapper personalInfoMapper;

	@Resource(name="mgmtOrgDtlMapper")
    private MgmtOrgDtlMapper mgmtOrgDtlMapper;

	@Resource(name = "smsMapper")
	private SmsMapper smsMapper;

	@Resource(name="userLoginMapper")
    private UserLoginMapper userLoginMapper;

	@Resource(name = "personalHistoryMapper")
	private PersonalHistoryMapper personalHistoryMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Override
	public List<Map<String, Object>> selectWorker(Map<String, Object> map) throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM_ENCPT")));
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("MBL_TELNO_ENCPT")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("EML_ADDR_ENCPT")));
		
		List<Map<String, Object>> list = memberManageMapper.selectWorker(map);
		
		List<Map<String, Object>> list2 = new ArrayList<>();
		
		for (int i=0; i < list.size(); i++) {
			Map<String, Object> map1 = list.get(i);
			//map1.put("FLNM_ENCPT", Masking.nameMasking(scpDb.scpDecB64((String)map1.get("FLNM_ENCPT"))));
			//map1.put("MBL_TELNO_ENCPT", map1.get("MBL_TELNO_ENCPT") == null ? "" : Masking.phoneMasking(scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT"))));
			//map1.put("EML_ADDR_ENCPT", map1.get("EML_ADDR_ENCPT") == null ? "" : Masking.emailMasking(scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT"))));
			map1.put("BRTH_YMD", map1.get("BRTH_YMD") == null ? "" : Masking.birthMasking((String)map1.get("BRTH_YMD")));
			map1.put("NCKN_NM", map1.get("NCKN_NM") == null ? "" : Masking.nameMasking((String)map1.get("NCKN_NM")));
			
			list2.add(map1);
		}
		return list2;
	}
	
	@Override
	public Integer selectWorkerCount(Map<String, Object> map) throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM_ENCPT")));
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("MBL_TELNO_ENCPT")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("EML_ADDR_ENCPT")));

		return memberManageMapper.selectWorkerCount(map);
	}

	@Override
	public List<Map<String, Object>> selectYouthGuardian(Map<String, Object> map) throws Exception {

		//ScpDb scpDb = new ScpDb();
		
		//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM")));
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("MBL_TELNO")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("EML_ADDR")));
		
		List<Map<String, Object>> list = memberManageMapper.selectYouthGuardian(map);
		
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//for (int i=0; i < list.size(); i++) {
			//Map<String, Object> map1 = list.get(i);
			//map1.put("FLNM", scpDb.scpDecB64((String)map1.get("FLNM_ENCPT")));
			//map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
			
			//map1.put("MBL_TELNO", scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT")));
			//map1.put("MBL_TELNO_MASKING", Masking.phoneMasking((String)map1.get("MBL_TELNO")));
			
			//map1.put("EML_ADDR", scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT")));
			//map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
			
			//map1.put("BRTH_YMD_MASKING", Masking.birthMasking((String)map1.get("BRTH_YMD")));
			
			//map1.put("STTY_AGT_NM", scpDb.scpDecB64((String)map1.get("STTY_AGT_NM_ENCPT")));
			//map1.put("PSPT_ENG_FLNM", scpDb.scpDecB64((String)map1.get("PSPT_ENG_FLNM_ENCPT")));
			
			//list2.add(map1);
		//}
		return list;
	}
	
	@Override
	public Integer selectYouthGuardianCount(Map<String, Object> map) throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM")));
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("MBL_TELNO")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("EML_ADDR")));

		return memberManageMapper.selectYouthGuardianCount(map);
	}

	// 청소년 보호자 등록
	@Override
	public void saveYouthGuardian(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmMemberInfo");
		Map<String, Object> map = new HashMap<>(parameterGroup.getSingleValueMap());
		
		if (map.get("USER_PSWD_ENCPT") != null && !"".equals(map.get("USER_PSWD_ENCPT"))) {
			map.put("USER_PSWD_ENCPT", new String(Base64.getDecoder().decode((String)map.get("USER_PSWD_ENCPT"))));
		}
		
		// 청소년 보호자 휴대폰 토큰 인증 확인
		String guardianPhone = (String)map.get("STTY_AGT_CTTPC_TELNO");
		guardianPhone = guardianPhone == null ? "" : guardianPhone.replaceAll("[^\\d]", "");
		
		String sessionGuardianPhone = (String)session.getAttribute("YOUTH_GUARDIAN_PHONE");
		sessionGuardianPhone = sessionGuardianPhone == null ? "" : sessionGuardianPhone.replaceAll("[^\\d]", "");
		
		String youthGuardianAuth = (String)session.getAttribute("YOUTH_GUARDIAN_AUTH");
		
		if (guardianPhone != null && !"".equals(guardianPhone)) {
			if (!guardianPhone.equals(sessionGuardianPhone) || !"1".equals(youthGuardianAuth)) {
				throw new UserException("errors.youthGuardianPhoneFail");
			}
		}
		
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM")));
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("MBL_TELNO")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("EML_ADDR")));
		//map.put("STTY_AGT_NM_ENCPT", scpDb.scpEncB64((String)map.get("STTY_AGT_NM")));
		
		map.put("FLNM_ENCPT", map.get("FLNM"));
		map.put("MBL_TELNO_ENCPT", map.get("MBL_TELNO"));
		map.put("EML_ADDR_ENCPT", map.get("EML_ADDR"));
		map.put("STTY_AGT_NM_ENCPT", map.get("STTY_AGT_NM"));
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		map.put("USER_ID2", userId2);
		//if (map.get("USER_ID") == null || "".equals((String)map.get("USER_ID"))) {
			//map.put("USER_ID", userId2);
		//}
		
		
		// 청소년 정보 입력
		String yngbgsPrtcrNo = (String)map.get("YNGBGS_PRTCR_NO");
		if (yngbgsPrtcrNo == null || "".equals(yngbgsPrtcrNo)) {
			yngbgsPrtcrNo = memberManageMapper.selectYngbgsPrtcrNo(userId2);
			map.put("YNGBGS_PRTCR_NO", yngbgsPrtcrNo);
		}
		
		String personalIdNum = (String)map.get("INDV_IDNTFC_NO");
		if (personalIdNum == null || "".equals(personalIdNum)) {
			personalIdNum = reqUserJoinMapper.selectPersonalIdNum(userId2);
			map.put("INDV_IDNTFC_NO", personalIdNum);
		}
		
		memberManageMapper.saveYouthGuardianHistory(map);
		memberManageMapper.saveYouthGuardian(map);
		
		
		
		// 개인 정보 입력
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		if (map.get("LSFT_IDNTY_YMD") == null || "".equals((String)map.get("LSFT_IDNTY_YMD"))) {
			map.put("LSFT_IDNTY_YMD", formatedNow);  // 가입 신청 일자
		}
		if (map.get("MRG_YN") == null || "".equals((String)map.get("MRG_YN"))) {
			map.put("MRG_YN", "N");
		}
		
		//reqUserJoinMapper.insertPersonalBasicInfo(map);
		Map<String, String> map1 = new HashMap<>();
		map1.put("INDV_IDNTFC_NO", personalIdNum);
		int count = personalInfoMapper.selectPersonalInfoIsExists(map1);
		if (count > 0) {
			personalInfoMapper.updatePersonalInfo(map);
			map.put("DATAA_CHG_SE_CD", "U");
		} else {
			personalInfoMapper.insertPersonalInfo(map);
			map.put("DATAA_CHG_SE_CD", "I");
		}

		personalInfoMapper.insertPersonalInfoHistory(map);
		
		
		// 사용자 로그인 정보 입력
		if (map.get("USER_ID") != null && !"".equals((String)map.get("USER_ID"))) {
			
			Map<String, String> passwordMap = userLoginMapper.selectPasswordMap((String)map.get("USER_ID"));
			
			if (map.get("USER_PSWD_ENCPT") != null && !"".equals((String)map.get("USER_PSWD_ENCPT"))) {
				
				String passwordCheckResult = passwordHelper.passwordCheck(null, (String)map.get("USER_PSWD_ENCPT"), 
						(String)map.get("USER_ID"), (String)map.get("MBL_TELNO"), 
						passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"), 
						passwordMap == null ? null : passwordMap.get("PREV_PASSWORD"), false);
	
		        if (passwordCheckResult != null && !"".equals(passwordCheckResult)) {
		        	throw new Exception("errors : " + passwordCheckResult);
		        }
			}
			
			if (map.get("JOIN_APLY_DT") == null || "".equals((String)map.get("JOIN_APLY_DT"))) {
				map.put("JOIN_APLY_DT", formatedNow);
			}
			if (map.get("USER_ID_USE_SE_CD") == null || "".equals((String)map.get("USER_ID_USE_SE_CD"))) {
				map.put("USER_ID_USE_SE_CD", 1);  // 사용자 ID 사용구분코드 : 신청(1), 승인, 반려, 사용중지, 삭제
			}
			
			if (map.get("USER_PSWD_ENCPT") != null && !"".equals((String)map.get("USER_PSWD_ENCPT"))) {
				//map.put("USER_PSWD_ENCPT", scpDb.scpHashB64((String)map.get("USER_PSWD_ENCPT")));
				map.put("USER_PSWD_ENCPT", (String)map.get("USER_PSWD_ENCPT"));
				map.put("BFE_PSWD_ENCPT1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
				map.put("LGN_ERR_CONT", 0);
			}
			
			int count1 = personalInfoMapper.selectUserInfoExists((String)map.get("USER_ID"));
			if (count1 > 0) {
				map.put("DATAA_CHG_SE_CD", "U");
			} else {
				map.put("DATAA_CHG_SE_CD", "I");
			}
			reqUserJoinMapper.insertUserInfo(map);
			personalInfoMapper.insertUserInfoHistory(map);
		}
		
		session.removeAttribute("YOUTH_GUARDIAN_PHONE");
		session.removeAttribute("YOUTH_GUARDIAN_AUTH");
	}

	// 청소년 보호자 등록
	@Override
	public Map<String, String> deleteYouthGuardian(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> returnMap = new HashMap<>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmMemberInfo = dataRequest.getParameterGroup("dmMemberInfo");
		
		Map<String, String> map = dmMemberInfo.getSingleValueMap();
		
		map.put("USER_ID2", userId2);

		map.put("USER_ID", dmMemberInfo == null ? userId2 : map.get("USER_ID"));
		
		int count = personalInfoMapper.selectYouthGuardianInfoExists(map);
		if (count == 0) {
			returnMap.put("RESULT", "종사자 정보가 존재하지 않습니다.");
			return returnMap;
		}
		
		map.put("DATAA_CHG_SE_CD", "D");
		
		map.put("DEL_YN", "Y");
		
		int cnt = personalInfoMapper.deleteYouthGuardianInfo(map);
		
		if (cnt == 0) {
			returnMap.put("RESULT", "삭제된 내역이 없습니다.");
		} else {
			returnMap.put("RESULT", "삭제되었습니다.");
			
			if (map.get("INDV_IDNTFC_NO") != null && !"".equals(map.get("INDV_IDNTFC_NO")) && !"null".equals(map.get("INDV_IDNTFC_NO")) ) {
				personalInfoMapper.deletePersonalInfo(map);
				personalHistoryMapper.insertPersonalInfoHistory(map);
			}
		}
		
		personalHistoryMapper.insertYouthGuardianInfoHistory(map);
		
		return returnMap;
	}
	
	// 사용자 기관 정보 저장
	@Override
	public void saveInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmOrgDetail");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		//ScpDb scpDb = new ScpDb();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		map.put("USER_ID", userId2);
		map.put("USER_ID2", userId2);
		
		
		
		//map.put("RPRSV_NM_ENCPT", scpDb.scpEncB64(map.get("RPRSV_NM_ENCPT")));
		//map.put("PIC_NM_ENCPT", scpDb.scpEncB64(map.get("PIC_NM_ENCPT")));
		//map.put("PIC_MBL_TELNO_ENCPT", scpDb.scpEncB64(map.get("PIC_MBL_TELNO_ENCPT")));
		//map.put("PIC_EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("PIC_EML_ADDR_ENCPT")));
		
		map.put("RPRSV_NM_ENCPT", map.get("RPRSV_NM_ENCPT"));
		map.put("PIC_NM_ENCPT", map.get("PIC_NM_ENCPT"));
		map.put("PIC_MBL_TELNO_ENCPT", map.get("PIC_MBL_TELNO_ENCPT"));
		map.put("PIC_EML_ADDR_ENCPT", map.get("PIC_EML_ADDR_ENCPT"));

		if (map.get("APRV_YN") == null || "".equals(map.get("APRV_YN"))) { 
			map.put("APRV_YN", "N");
		}
		if (map.get("DEL_YN") == null || "".equals(map.get("DEL_YN"))) {
			map.put("DEL_YN", "N");
		}
		
		int INST_NO_CNT = mgmtOrgDtlMapper.selectInstNoCnt(map);	//통합기관이력 INST_NO 중복값 확인
		if (INST_NO_CNT > 0) {
			map.put("DATAA_CHG_SE_CD", "U");	//데이터변경 구분코드 "변경"
		} else {
			map.put("DATAA_CHG_SE_CD", "I");	//데이터변경 구분코드 "신규"
		}

		
		
		
		
		//memberManageMapper.saveInstitute(map);
		mgmtOrgDtlMapper.saveOrgDtl(map);
		mgmtOrgDtlMapper.saveOrgDtlHistory(map);
		
		
		
		// 개인 정보 입력
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		
		// 사용자 로그인 정보 입력
		if (map.get("USER_ID") != null && !"".equals((String)map.get("USER_ID"))) {
			
			Map<String, String> passwordMap = userLoginMapper.selectPasswordMap((String)map.get("USER_ID"));
			
			if (map.get("USER_PSWD_ENCPT") != null && !"".equals((String)map.get("USER_PSWD_ENCPT"))) {
				
				String passwordCheckResult = passwordHelper.passwordCheck(null, map.get("USER_PSWD_ENCPT"), 
						map.get("USER_ID"), map.get("RPRS_TELNO"), 
						passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"), 
						passwordMap == null ? null : passwordMap.get("PREV_PASSWORD"), false);
	
		        if (passwordCheckResult != null && !"".equals(passwordCheckResult)) {
		        	throw new Exception("errors : " + passwordCheckResult);
		        }
			}

			if (map.get("JOIN_APLY_DT") == null || "".equals((String)map.get("JOIN_APLY_DT"))) {
				map.put("JOIN_APLY_DT", formatedNow);
			}
			if (map.get("USER_ID_USE_SE_CD") == null || "".equals((String)map.get("USER_ID_USE_SE_CD"))) {
				map.put("USER_ID_USE_SE_CD", "1");  // 사용자 ID 사용구분코드 : 신청(1), 승인, 반려, 사용중지, 삭제
			}
			
			if (map.get("USER_PSWD_ENCPT") != null && !"".equals((String)map.get("USER_PSWD_ENCPT"))) {
				//map.put("USER_PSWD_ENCPT", scpDb.scpHashB64((String)map.get("USER_PSWD_ENCPT")));
				map.put("USER_PSWD_ENCPT", (String)map.get("USER_PSWD_ENCPT"));
				map.put("BFE_PSWD_ENCPT1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
				map.put("LGN_ERR_CONT", "0");
			}
			
			int count1 = personalInfoMapper.selectUserInfoExists((String)map.get("USER_ID"));
			if (count1 > 0) {
				map.put("DATAA_CHG_SE_CD", "U");
			} else {
				map.put("DATAA_CHG_SE_CD", "I");
			}
			reqUserJoinMapper.insertUserInfo(new HashMap<String, Object>(map));
			personalInfoMapper.insertUserInfoHistory(new HashMap<String, Object>(map));
		}
	}

	// 14세 미만 청소년 보호자 휴대폰 인증 SMS 인증 토큰 보내기
	@Override
	public Map<String, String> saveYouthAuthSms(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmAuthSms");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		String mblTelno = map.get("STTY_AGT_CTTPC_TELNO");
		mblTelno = mblTelno.replaceAll("[^\\d]", "");
		
		session.setAttribute("YOUTH_GUARDIAN_PHONE", mblTelno);
		
		String authToken = RandomString.generate(6);
		
		log.debug("#### authToken : " + authToken);
		
		session.setAttribute("YOUTH_AUTH_TOKEN", authToken);
		
		String contents = "[청소년 안전망 시스템] 인증 토큰은 " + authToken + " 입니다.";
		
		String sender = "0516623229";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("receiver", mblTelno);
		paramMap.put("sender", sender);
		paramMap.put("contents", contents);
		paramMap.put("userId", userId2);

		smsMapper.insertSMS(paramMap);
		
		map.put("AUTH_TOKEN", "");
		
		return map;
	}

	// 14세 미만 청소년 보호자 휴대폰 인증 토큰 일치여부 확인
	@Override
	public Map<String, String> selectYouthAuthSmsToken(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		//UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//String userId2 = "";
		//if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			//userId2 = loginVO.getId();
		//}

		String youthAuthToken = (String)session.getAttribute("YOUTH_AUTH_TOKEN");
		//session.removeAttribute("YOUTH_AUTH_TOKEN");
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmAuthSms");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		String authToken = map.get("AUTH_TOKEN");
		
		String result = "";
		
		if (authToken != null && authToken.equals(youthAuthToken)) {
			result = "1";
			session.setAttribute("YOUTH_GUARDIAN_AUTH", "1");
		} else {
			result = "0";
		}
		
		Map<String, String> map1 = new HashMap<>();
		map1.put("RESULT", result);
		
		return map1;
	}
	
	/**
	 * 관리자 여부는 (그룹 권한 코드)로 구분 하도록 함.
	 * TYPE 1 기관정보에 등록된 담당자, 총괄, 기관, 사업관리자로 등록된 종사자 전체와 종사자 전체 목록 리턴
	 * TYPE 2 기관정보에 등록된 담당자, 총괄, 기관관리자로 등록된 종사자 전체와 종사자 한명 리턴.
	 * TYPE 3 기관정보에 등록된 담당자, 총괄 1명, 기관 1명 등록된 종사자 1명 리턴.
	 * 데이터가 없으면 리스트 size는 0으로 리턴 됩니다. 
	 * @Method명   : getTargetOgdpWorkerList
	 * @param INST_NO
	 * @param TYPE : 1,2,3 으로 전달.
	 * @return
	 * @throws Exception
	 * @작성자     : Tae soo Song
	 * @작성일     : 2023. 4. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> getTargetOgdpWorkerList(String INST_NO, String TYPE) throws Exception {
		// 리턴할 LIST .
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		// 파라미터용.
		Map<String, Object> pMap = new HashMap<String, Object>();
		
		pMap.put("INST_NO", INST_NO);
		
		// 기관에 속해있는 대상자 정보 가져오기.
		List<Map<String, Object>> workerList = memberManageMapper.getTargetOgdpWorkerList(pMap);
		
		// 관리자 추가 여부 체크
		boolean adminYn = false; //총괄관리자
		boolean masterYn = false; // 기관관리자
		boolean businessYn = false; 	  // 사업 관리자.
		boolean memberYn = false; // 종사자
		
		if(workerList != null) {
			for (int i=0; i<workerList.size(); i++) {
				Map<String, Object> defMap = workerList.get(i);
				Map<String, Object> dMap = new HashMap<String, Object>();
				if(i == 0) {
					// 담당자로 지정 되었으나 종사여부가 불 명확함. 필요에 의해 추가 해둠.
					// 기관 담당자가 이관,퇴사하여도 기관 정보를 변경하지 않으면 바뀌지 않을 정보이기 때문에..
					if (defMap.get("OGDP_PIC_NO") != null) {
						Map<String, Object> dMap2 = new HashMap<String, Object>();
						dMap2.put("OGDP_ENFSN_NO", defMap.get("OGDP_PIC_NO"));
						dMap2.put("INST_NO", INST_NO);
						
						// 기관 담당자 정보 조회.
						Map<String,Object> ogdpWorker = memberManageMapper.getInstOgdpWorkerInfo(dMap2);
						
						if( !ogdpWorker.isEmpty() ) {
							dMap2.put("ENFSN_NO", ogdpWorker.get("ENFSN_NO")); // 통합기관 담당자 종사자 번호.
							dMap2.put("FLNM_ENCPT", ogdpWorker.get("FLNM_ENCPT")); // 통합기관 담당자명
							dMap2.put("GROUP_AUTHRT_SE_CD", ogdpWorker.get("GROUP_AUTHRT_SE_CD")); // 그룹 권한 코드값. 
							dMap2.put("USER_ID", ogdpWorker.get("USER_ID")); // 유저 아이디.
							dMap2.put("PIC_ENFSN_YN", "Y"); // 통합기관내 담당자 여부. 종사여부 불확실 함.
							
							returnList.add(dMap2);
						}
					}
				}
				
				/* 
				 * GROUP_AUTHRT_SE_CD (그룹 권한 코드)
				 * 310 : 총괄관리자.
				 * 320 : 기관관리자.
				 * 330 : 시설관리자.
				 * 340 : 종사자.
				 * */
				String groupAuth = String.valueOf(defMap.get("GROUP_AUTHRT_SE_CD"));
				if ("310".equals(groupAuth) || "320".equals(groupAuth) || "330".equals(groupAuth) || "340".equals(groupAuth) ) {
					if (!adminYn) {
						if ("310".equals(groupAuth)) { // 총괄관리자.
							dMap.put("ENFSN_NO", defMap.get("PIC_NO")); // 통합기관 담당자 종사자 번호.
							dMap.put("FLNM_ENCPT", defMap.get("FLNM_ENCPT")); // 통합기관 담당자명
							dMap.put("GROUP_AUTHRT_SE_CD", defMap.get("GROUP_AUTHRT_SE_CD")); // 그룹 권한 코드값. 
							dMap.put("USER_ID", defMap.get("USER_ID")); // 유저 아이디. 
							dMap.put("PIC_ENFSN_YN", "N"); // 통합기관내 담당자 여부. 
							returnList.add(dMap);
							if ("3".equals(TYPE)) {
								adminYn = true;
							}
						}
					}
					if (!masterYn) {
						if ("320".equals(groupAuth)) {
							dMap.put("ENFSN_NO", defMap.get("PIC_NO")); // 통합기관 담당자 종사자 번호.
							dMap.put("FLNM_ENCPT", defMap.get("FLNM_ENCPT")); // 통합기관 담당자명
							dMap.put("GROUP_AUTHRT_SE_CD", defMap.get("GROUP_AUTHRT_SE_CD")); // 그룹 권한 코드값.
							dMap.put("USER_ID", defMap.get("USER_ID")); // 유저 아이디.
							dMap.put("PIC_ENFSN_YN", "N"); // 통합기관내 담당자 여부.
							returnList.add(dMap);
							if ("3".equals(TYPE)) {
								masterYn = true;
							}
						}
					}

					/* 사업담당자도 담당자로 지정 될 여지가 있다 하여 추가함. Taesoo Song. 20230425.*/
					if (!businessYn) {
						if ("330".equals(groupAuth)) {
							dMap.put("ENFSN_NO", defMap.get("PIC_NO")); // 통합기관 담당자 종사자 번호.
							dMap.put("FLNM_ENCPT", defMap.get("FLNM_ENCPT")); // 통합기관 담당자명
							dMap.put("GROUP_AUTHRT_SE_CD", defMap.get("GROUP_AUTHRT_SE_CD")); // 그룹 권한 코드값.
							dMap.put("USER_ID", defMap.get("USER_ID")); // 유저 아이디.
							dMap.put("PIC_ENFSN_YN", "N"); // 통합기관내 담당자 여부.
							returnList.add(dMap);
							if ("3".equals(TYPE)) {
								businessYn = true;
							}
						}
					}
					
					if (!memberYn) {
						if ("340".equals(groupAuth)) {
							dMap.put("ENFSN_NO", defMap.get("PIC_NO")); // 통합기관 담당자 종사자 번호.
							dMap.put("FLNM_ENCPT", defMap.get("FLNM_ENCPT")); // 통합기관 담당자명
							dMap.put("GROUP_AUTHRT_SE_CD", defMap.get("GROUP_AUTHRT_SE_CD")); // 그룹 권한 코드값.
							dMap.put("USER_ID", defMap.get("USER_ID")); // 유저 아이디.
							dMap.put("PIC_ENFSN_YN", "N"); // 통합기관내 담당자 여부.
							returnList.add(dMap);
							if (!"1".equals(TYPE)) {
								memberYn = true;
							}
						}
					}
				}
			}
		}
		
		return returnList;
	}
}
