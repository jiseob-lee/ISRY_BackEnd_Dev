/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userjoin.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.cmmncode.mapper.MgmtCmmnCodeMapper;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtOrgDtlMapper;
import isry.itgcms.sysmgmt.userjoin.mapper.ReqUserJoinMapper;
import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.itgcms.sysmgmt.userlogin.mapper.UserLoginMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.PasswordHelper;
import isry.itgcms.util.UserException;

/**
 * @파일명        : ReqUserJoinServiceImpl.java
 * @프로그램 설명 : 회원 가입
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("reqUserJoinService")
public class ReqUserJoinServiceImpl extends IsryBaseServiceImpl implements ReqUserJoinService {

	@Resource(name="reqUserJoinMapper")
    private ReqUserJoinMapper reqUserJoinMapper;

	@Resource(name="mgmtCmmnCodeMapper")
    private MgmtCmmnCodeMapper mgmtCmmnCodeMapper;
	
	@Resource(name="personalInfoMapper")
    private PersonalInfoMapper personalInfoMapper;

	@Resource(name="mgmtOrgDtlMapper")
	private MgmtOrgDtlMapper mgmtOrgDtlMapper;

	@Resource(name="userLoginMapper")
    private UserLoginMapper userLoginMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Override
	public List<Map<String, Object>> selectQualifyClass() throws Exception {
		//return reqUserJoinMapper.selectQualifyClass();
		return mgmtCmmnCodeMapper.selectCodeValue("QLFC_SE_CD");
	}
	
	@Override
	public Map<String, Integer> checkIdDuplicate(DataRequest dataRequest) throws Exception {

		Map<String, Integer> map = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmCheckId");
		
		if (param != null) {
			
			String id = param.getValue("id");
			
			if (id != null && !"".equals(id)) {
				Integer existCount = reqUserJoinMapper.selectIdExistsCount(id);
				
				if (existCount != null && existCount > 0) {
					map.put("idExists", 1);
				} else {
					map.put("idExists", 0);
				}
			}
		}
		
		return map;
	}

	private boolean emailCheck(String email) {

		return EmailValidator.getInstance().isValid(email);
		
		//boolean err = false;
		//String regex = "([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		//Pattern p = Pattern.compile(regex);
		//Matcher m = p.matcher(email);
		//if (m.matches()) {
			//err = true;
		//}
		//return err;
		//return (email != null && email != '' && email != 'undefined' && regex.test(email)); 
	}
	
	private String checkValidity(Map<String, String> map, String type) throws Exception {

		// type 1 : 내 정보 수정에서 넘어온 경우
		// type 2 : 종사자 목록에서 넘어온 경우
		
        if ("1".equals(type) && (map.get("USER_ID") == null || "".equals(map.get("USER_ID")))) {
        	return "사용자 아이디를 입력해주시기 바랍니다.";
        }
        if (map.get("USER_ID") != null && !"".equals(map.get("USER_ID"))) {
	        if (map.get("ID_DUPLICATE_CHECK") == null || !"사용 가능".equals(map.get("ID_DUPLICATE_CHECK"))) {
	        	return "사용자 아이디 중복을 체크해주시기 바랍니다.";
	        }
	        Integer existCount = reqUserJoinMapper.selectIdExistsCount(map.get("USER_ID"));
			if ("1".equals(type) && existCount != null && existCount > 0) {
				return "사용자 아이디가 중복됩니다.";
			}
        }
        if ("1".equals(type) || (map.get("USER_ID") != null && !"".equals(map.get("USER_ID")))) {
			if ((map.get("USER_PSWD") == null || "".equals(map.get("USER_PSWD")))) {
				if ("1".equals(type)) {
					return "비밀번호를 입력해주시기 바랍니다.";
				} else {
					return "비밀번호를 설정해주시기 바랍니다.";
				}
	        }
        }
        if ("1".equals(type) && (map.get("USER_PSWD2") == null || "".equals(map.get("USER_PSWD2")))) {
        	return "확인 비밀번호를 입력해주시기 바랍니다.";
        }
        if (map.get("USER_PSWD") != null && !"".equals(map.get("USER_PSWD")) && map.get("USER_PSWD2") != null && !"".equals(map.get("USER_PSWD2"))) {
	        if (!map.get("USER_PSWD").equals(map.get("USER_PSWD2"))) {
	        	return "비밀번호가 일치하지 않습니다.";
	        }
	        
	        Map<String, String> passwordMap = userLoginMapper.selectPasswordMap(map.get("USER_ID"));
	        
	        String passwordCheckResult = passwordHelper.passwordCheck(null, map.get("USER_PSWD"), map.get("USER_ID"), null, 
	        		passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"), passwordMap == null ? null : passwordMap.get("PREV_PASSWORD"), false);

	        if (passwordCheckResult != null && !"".equals(passwordCheckResult)) {
	        	return passwordCheckResult;
	        }
	        
        }

        if (map.get("FLNM_ENCPT") == null || "".equals(map.get("FLNM_ENCPT"))) {
        	return "이름을 입력해주시기 바랍니다.";
        }
        
        String flnm = map.get("FLNM_ENCPT");
        flnm = flnm.replace("-", "");
        flnm = flnm.replaceAll("[\\d]", "");
        if (flnm == null || "".equals(flnm)) {
        	return "이름이 숫자로만 되어 있습니다.";
        }
        
        if (map.get("SXDC_SE_CD") == null || "".equals(map.get("SXDC_SE_CD"))) {
        	return "성별을 입력해주시기 바랍니다.";
        }
        
        if (map.get("BRTH_YMD") == null || "".equals(map.get("BRTH_YMD"))) {
        	return "생년월일을 입력해주시기 바랍니다.";
        }
        
        if (!(map.get("WRD_TELNO") == null || "".equals(map.get("WRD_TELNO")))) {
	        String phoneNum = map.get("WRD_TELNO").replaceAll("[^0-9]", "");
	        if (phoneNum.length() < 8 || phoneNum.length() > 11) {
	        	return "직장전화번호를 확인해주시기 바랍니다.";
	        }
        }
	        String mobileNum = map.get("MBL_TELNO_ENCPT").replaceAll("[^0-9]", "");
	        if (mobileNum.length() < 10 || mobileNum.length() > 11) {
	        	return "휴대전화번호를 확인해주시기 바랍니다.";
	        }
        //}
        
	    if (map.get("USER_PSWD") != null && !"".equals(map.get("USER_PSWD")) && map.get("USER_PSWD2") != null && !"".equals(map.get("USER_PSWD2"))) {
		    String passwordCheckResult2 = passwordHelper.passwordCheck(null, map.get("USER_PSWD"), null, map.get("MBL_TELNO_ENCPT"), null, null, true);
	
	        if (passwordCheckResult2 != null && !"".equals(passwordCheckResult2)) {
	        	return passwordCheckResult2;
	        }
	    }
                
        //if (!(map.get("EML_ADDR_ENCPT") == null || "".equals(map.get("EML_ADDR_ENCPT")) || "@".equals(map.get("EML_ADDR_ENCPT")))) {
	        if (map.get("EML_ADDR_ENCPT") == null || "".equals(map.get("EML_ADDR_ENCPT"))) {
	        	return "이메일 주소를 입력해주시기 바랍니다.";
	        }
	        if (!emailCheck(map.get("EML_ADDR_ENCPT"))) {
	        	return "이메일 주소 형식을 확인해주시기 바랍니다.";
	        }
        //}
        
        if (map.get("UNT_TASKWK_SE_CD") == null || "".equals(map.get("UNT_TASKWK_SE_CD")) || "0".equals(map.get("UNT_TASKWK_SE_CD"))) {
        	return "단위 시스템을 선택해주시기 바랍니다.";
        }
        if (map.get("OGDP_INST_NO") == null || "".equals(map.get("OGDP_INST_NO"))) {
        	return "소속 기관을 선택해주시기 바랍니다.";
        }
        if (map.get("OGDP_DEPT_CD") == null || "".equals(map.get("OGDP_DEPT_CD"))) {
        	return "소속 부서를 선택해주시기 바랍니다.";
        }

		return "";
	}
	
	/*
	@Override
	public Map<String, String> saveMember(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> returnMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmMemberInfo");

		if (param == null) {
			param = dataRequest.getParameterGroup("dmWorker");
		}
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
				
		if (param != null) {
			
			Map<String, String> paramMap = param.getSingleValueMap();

			if (userId == null || "".equals(userId)) {
				userId = paramMap.get("USER_ID");
			}
			
			String resultStr = checkValidity(paramMap, "1");
			
			if (resultStr != null && !"".equals(resultStr)) {
				returnMap.put("resultStr", resultStr);
				return returnMap;
			}
			//for (String key : paramMap.keySet()) {
				//log.debug(key + " : " + paramMap.get(key));
			//}
			
			//System.out.println("#################");
			
			Map<String, Object> map = new HashMap<String, Object>(paramMap);
			
			map.put("USER_ID2", userId);
			
			// 이메일
			if ("@".equals(map.get("EML_ADDR_ENCPT"))) {
				map.put("EML_ADDR_ENCPT", "");
			}
			//for (String key : map.keySet()) {
				//log.debug(key + " : " + map.get(key));
			//}
			
			// 개인 식별 번호
			String personalIdNum = reqUserJoinMapper.selectPersonalIdNum(userId);
			map.put("INDV_IDNTFC_NO", personalIdNum);
			map.put("AGE", 1);
			
			// 주민등록번호
			String residentRegistNum = paramMap.get("RRNO");
			
			if (residentRegistNum != null) {
				
				residentRegistNum = residentRegistNum.replaceAll("[^0-9]", "");
				map.put("RRNO", residentRegistNum);
				
				//map.put("GENDER", "Male");
				//map.put("BIRTHDATE", "19000101");
				
				
				//char ch = residentRegistNum.length() >= 7 ? residentRegistNum.charAt(7) : 0;
				
				// 성별
				//if (ch == '1' || ch == '3') {
					//map.put("GENDER", "Male");
				//} else if (ch == '2' || ch == '4') {
					//map.put("GENDER", "Female");
				//}
				
			}

			
			//if (paramMap.get("BIRTHDATE") != null) {
				
				//int birthYear = Integer.parseInt(paramMap.get("BIRTHDATE").substring(0, 4));
				
				// 생일
				//if (ch == '1' || ch == '3') {
					//map.put("BIRTHDATE", "19" + residentRegistNum.substring(0, 6));
					//birthYear = Integer.parseInt("19" + residentRegistNum.substring(0, 2));
				//} else if (ch == '2' || ch == '4') {
					//map.put("BIRTHDATE", "20" + residentRegistNum.substring(0, 6));
					//birthYear = Integer.parseInt("20" + residentRegistNum.substring(0, 2));
				//}
				
				// 나이
				//if (residentRegistNum.length() >= 4 && birthYear > 0) {
					//int currentYear = Calendar.getInstance().get(Calendar.YEAR);
					//map.put("AGE", currentYear - birthYear + 1);
				//}
			//}
			
			String workerId = reqUserJoinMapper.getWorkerId(userId);
			map.put("ENFSN_NO", workerId);
			
			reqUserJoinMapper.insertWorker(map);
			reqUserJoinMapper.insertWorkerHistory(map);
			
			ScpDb scpDb = new ScpDb();
			
			// 가입 신청 일자
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			map.put("LSFT_IDNTY_YMD", formatedNow);
			if (map.get("RRNO") != null && !"".equals((String)map.get("RRNO"))) {
				map.put("RRNO", scpDb.scpEncB64((String)map.get("RRNO")));  // 주민등록번호 암호화
			}
			
			reqUserJoinMapper.insertPersonalBasicInfo(map);
			
			map.put("DATAA_CHG_SE_CD", "I");
			personalInfoMapper.insertPersonalInfoHistory(map);
			
			map.put("JOIN_APLY_DT", formatedNow);
			
			map.put("USER_ID_USE_SE_CD", 1);  // 사용자 ID 사용구분코드 : 신청(1), 승인, 반려, 사용중지, 삭제
			

			String strEnc = scpDb.scpHashB64((String)map.get("USER_PSWD"));
			map.put("USER_PSWD", strEnc);  // 비밀번호 암호화
			
			int count1 = personalInfoMapper.selectUserInfoExists((String)map.get("USER_ID"));
			if (count1 > 0) {
				map.put("DATAA_CHG_SE_CD", "U");
			} else {
				map.put("DATAA_CHG_SE_CD", "I");
			}
			reqUserJoinMapper.insertUserInfo(map);
			personalInfoMapper.insertUserInfoHistory(map);
		}
		
		return returnMap;
	}
	*/
	
	
	@Override
	public Map<String, String> saveWorker(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> returnMap = new HashMap<>();
		
		//returnMap.put("resultStr", "");
		
		HttpSession session = request.getSession();

		//if (session.getAttribute("ci") == null || "".equals(String.valueOf(session.getAttribute("ci")))) {
			//returnMap.put("resultStr", "휴대폰 본인 인증을 진행해주시기 바랍니다.");
			//return returnMap;
		//}
		
		//int cntCi = reqUserJoinMapper.selectCiCount(String.valueOf(session.getAttribute("ci")));
		//if (cntCi > 0) {
			//returnMap.put("resultStr", "이미 등록되어 있는 회원 개인정보가 있습니다.");
			//return returnMap;
		//}
		
		ParameterGroup param1 = dataRequest.getParameterGroup("dmWorkerDetail");
		ParameterGroup param2 = dataRequest.getParameterGroup("dsWorkerUnitSystem");
				
		String type =  "1";  // 내 정보 수정에서 넘어온 경우
		if (param1 == null) {
			param1 = dataRequest.getParameterGroup("dmWorker");
			type =  "2";  // 종사자 관리 목록에서 넘어온 경우
		}
		
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		if (param1 != null) {
			
			Map<String, String> workerMap = param1.getSingleValueMap();
			
			if (userId == null || "".equals(userId)) {
				userId = workerMap.get("USER_ID");
			}
			
			workerMap.put("USER_PSWD", new String(Base64.getDecoder().decode(workerMap.get("USER_PSWD"))));
			
			if (workerMap.get("USER_PSWD2") == null) {
				workerMap.put("USER_PSWD2", new String(workerMap.get("USER_PSWD")));
			} else {
				workerMap.put("USER_PSWD2", new String(Base64.getDecoder().decode(workerMap.get("USER_PSWD2"))));
			}
			
			log.info(":::::::::: " + " :::: " + param1.getValue("OGDP_INST_NO"));
			
			String resultStr = checkValidity(workerMap, type);
			
			if (resultStr != null && !"".equals(resultStr)) {
				returnMap.put("resultStr", resultStr);
				return returnMap;
			}
			//for (String key : paramMap.keySet()) {
				//log.debug(key + " : " + paramMap.get(key));
			//}
			
			//System.out.println("#################");
			
			Map<String, Object> map = new HashMap<String, Object>(workerMap);
			
			//ScpDb scpDb = new ScpDb();
			
			//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM_ENCPT")));
			String sMblTelNo = map.get("MBL_TELNO_ENCPT") == null ? "" : ((String)map.get("MBL_TELNO_ENCPT")).replace("-", "");
			if(!"".equals(sMblTelNo) && !"null".equals(sMblTelNo)) {
				//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64(sMblTelNo));
				map.put("MBL_TELNO_ENCPT", sMblTelNo);
			}
			//String sEmlAddr  = map.get("EML_ADDR_ENCPT") == null ? "" : ((String)map.get("EML_ADDR_ENCPT"));
			//if(! "".equals(sEmlAddr) && ! "null".equals(sEmlAddr)) {
				//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64(sEmlAddr));
			//}
			//String sMsNgID   = map.get("MSNGR_ID_ENCPT") == null ? "" : ((String)map.get("MSNGR_ID_ENCPT"));
			//if(! "".equals(sMsNgID) && ! "null".equals(sMsNgID)) {
				//map.put("MSNGR_ID_ENCPT", scpDb.scpEncB64(sMsNgID));
			//}
			String sWreTelNo = map.get("WRD_TELNO") == null ? "" : ((String)map.get("WRD_TELNO")).replace("-", "");
			if(!"".equals(sWreTelNo) && !"null".equals(sWreTelNo)) {
				//map.put("WRD_TELNO", scpDb.scpEncB64(sWreTelNo));
				map.put("WRD_TELNO", sWreTelNo);
			}
			String sRrno = map.get("RRNO_ENCPT") == null ? "" : ((String)map.get("RRNO_ENCPT")).replace("-", "");
			if(!"".equals(sRrno) && !"null".equals(sRrno)) {
				//map.put("RRNO_ENCPT", scpDb.scpEncB64(sRrno));
				map.put("RRNO_ENCPT", sRrno);
			}

			if (reqUserJoinMapper.selectSamePhoneCount((String)map.get("MBL_TELNO_ENCPT")) > 0) {
				throw new UserException("errors.samePhone");
			}

			if (reqUserJoinMapper.selectSameEmailCount((String)map.get("EML_ADDR_ENCPT")) > 0) {
				throw new UserException("errors.sameEmail");
			}
			
			map.put("USER_ID2", userId);
			
			//for (String key : map.keySet()) {
				//log.debug(key + " : " + map.get(key));
			//}
			
			// 개인 식별 번호
			String personalIdNum = reqUserJoinMapper.selectPersonalIdNum(userId);
			map.put("INDV_IDNTFC_NO", personalIdNum);
			
			//int year = Calendar.getInstance().get(Calendar.YEAR);
			//map.put("AGE", year - Integer.parseInt(((String)map.get("BRTH_YMD")).substring(0, 4)) + 1);
			map.put("MRG_YN", "N");
			

			String workerId = reqUserJoinMapper.getWorkerId(userId);
			map.put("ENFSN_NO", workerId);
			
			reqUserJoinMapper.insertWorker(map);
			reqUserJoinMapper.insertWorkerHistory(map);
			
			/* 2023-02-09 YOO.CHI.HOON 화면조회를 위해 userId, enfsnNo 전달위해 추가*/
//			log.info("========== 화면조회용 아이디, 종사자번호 담기 시작 ==========");
			returnMap.put("USER_ID", (map.get("USER_ID2") == null ? "" : String.valueOf(map.get("USER_ID"))));
			returnMap.put("ENFSN_NO", (map.get("ENFSN_NO") == null ? "" : String.valueOf(map.get("ENFSN_NO"))));
			returnMap.put("INDV_IDNTFC_NO", (map.get("ENFSN_NO") == null ? "" : String.valueOf(map.get("INDV_IDNTFC_NO"))));
			log.info(returnMap.get("USER_ID"));
			log.info(returnMap.get("ENFSN_NO"));
			log.info(returnMap.get("INDV_IDNTFC_NO"));
//			log.info("========== 화면조회용 아이디, 종사자번호 담기 종료 ==========");
			
			// 가입 신청 일자
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			map.put("LSFT_IDNTY_YMD", formatedNow);
			map.put("MOPH_ME_CERT_DN_VALUE_CN", String.valueOf(session.getAttribute("ci")));
			
			session.setAttribute("ci", "");
			
			String server = System.getProperty("SERVER");
			String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
			
			log.info("#### type : " + type);
			log.info("#### profile : " + profile);
			
			// pre 서버는 본인 인증 건너뜀. 취소
			// dev 서버는 본인 인증 건너뜀.
			//if (!"ryewas11".equals(server) && !"ryewas21".equals(server)
			if (!"dev2".equals(profile) && !"local".equals(profile) && !"2".equals(type)) {
				
				log.info("#### userId : " + userId);
				log.info("#### OFCERT_ME : " + map.get("OFCERT_ME_CERT_DN_VALUE_CN"));
				log.info("#### FICE_ME : " + map.get("FICE_ME_CERT_DN_VALUE_CN"));
				log.info("#### MOPH_ME : " + map.get("MOPH_ME_CERT_DN_VALUE_CN"));
				log.info("#### SNS_SIMPC : " + map.get("SNS_SIMPC_CERT_DN_VALUE_CN"));
				
				if (
					(map.get("OFCERT_ME_CERT_DN_VALUE_CN") == null || "".equals((String)map.get("OFCERT_ME_CERT_DN_VALUE_CN")))
					&&
					(map.get("FICE_ME_CERT_DN_VALUE_CN") == null || "".equals((String)map.get("FICE_ME_CERT_DN_VALUE_CN")))
					&&
					(map.get("MOPH_ME_CERT_DN_VALUE_CN") == null || "".equals((String)map.get("MOPH_ME_CERT_DN_VALUE_CN")))
					&&
					(map.get("SNS_SIMPC_CERT_DN_VALUE_CN") == null || "".equals((String)map.get("SNS_SIMPC_CERT_DN_VALUE_CN")))
	            ) {
					// 본인 인증 데이터가 없습니다.
					throw new UserException("errors.personalValidationFail");
				}
			}
			
			reqUserJoinMapper.insertPersonalBasicInfo(map);
			
			map.put("DATAA_CHG_SE_CD", "I");
			personalInfoMapper.insertPersonalInfoHistory(map);
			
			map.put("JOIN_APLY_DT", formatedNow);
			
			map.put("USER_ID_USE_SE_CD", 1);  // 사용자 ID 사용구분코드 : 신청(1), 승인, 반려, 사용중지, 삭제
			

			//String strEnc = scpDb.scpHashB64((String)map.get("USER_PSWD"));
			map.put("USER_PSWD_ENCPT", (String)map.get("USER_PSWD"));  // 비밀번호 암호화
			
			if (workerMap.get("USER_ID") != null && !"".equals(workerMap.get("USER_ID"))) {

				Map<String, String> passwordMap = userLoginMapper.selectPasswordMap(workerMap.get("USER_ID"));
				
				map.put("BFE_PSWD_ENCPT1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
				map.put("LGN_ERR_CONT", 0);
				
				int count1 = personalInfoMapper.selectUserInfoExists(workerMap.get("USER_ID"));
				if (count1 > 0) {
					map.put("DATAA_CHG_SE_CD", "U");
				} else {
					map.put("DATAA_CHG_SE_CD", "I");
				}
				reqUserJoinMapper.insertUserInfo(map);
				
				personalInfoMapper.insertUserInfoHistory(map);
			}
			
			/* 2023-02-14 YOO.CHI.HOON SAB230 사용자별 기관 권한 추가*/
			if(workerMap.get("UNT_TASKWK_SE_CD") != null && ! "".equals(workerMap.get("UNT_TASKWK_SE_CD"))) {
				
//				log.info(" ========== 사용자별 기관 권한 start ========== ");
				
//				String sGrpAuthSeCd = String.valueOf(workerMap.get("GROUP_AUTHRT_SE_CD"));
//				String sUnitCode = String.valueOf(workerMap.get("UNT_TASKWK_SE_CD")).replace("U", "");	/* 단위업무구분코드*/
				
				Map<String, Object> userAuth = new HashMap<>();
				
//				String sAuthrtSeCd = String.valueOf(reqUserJoinMapper.getAuthrtSeCd(map));
				
				userAuth.put("USER_ID", String.valueOf(map.get("USER_ID")));		    /* USER_ID			사용자아이디*/
				userAuth.put("INST_NO", String.valueOf(map.get("OGDP_INST_NO")));       /* INST_NO			기관번호*/
				userAuth.put("GROUP_AUTHRT_SE_CD", String.valueOf(map.get("GROUP_AUTHRT_SE_CD")));       /*  GROUP_AUTHRT_SE_CD	그룹권한구분코드*/
				userAuth.put("AUTHRT_SE_CD", String.valueOf(map.get("AUTHRT_SE_CD")));  /*	AUTHRT_SE_CD        권한구분코드*/
				userAuth.put("SYS_MNGR_YN" , "N");									    /*	SYS_MNGR_YN	        시스템관리자여부*/
				userAuth.put("MAIST_YN"    , "Y");//									/*	MAIST_YN			주기관여부*/	
				userAuth.put("USER_ID2"    , String.valueOf(map.get("USER_ID2")));//	/*	등록자*/	
				
				reqUserJoinMapper.insertUserInstAuth(userAuth);
				
//				log.info(" ========== 사용자별 기관 권한 end ========== ");				
			}
			
			if (param2 != null) {
				List<Map<String, String>> unitList = param2.getAllRowList();
				for (int i=0; i < unitList.size(); i++) {
					Map<String, Object> unit = new HashMap<>(unitList.get(i));
					unit.put("ENFSN_NO", workerId);
					unit.put("USER_ID", map.get("USER_ID"));
					unit.put("UNT_TASKWK_SE_CD", unit.get("UNT_TASKWK_SE_CD") == null ? "" : unit.get("UNT_TASKWK_SE_CD"));
					unit.put("OGDP_INST_NO", unit.get("OGDP_INST_NO") == null ? "" : unit.get("OGDP_INST_NO"));
					unit.put("OGDP_DEPT_CD", unit.get("OGDP_DEPT_CD") == null ? "" : unit.get("OGDP_DEPT_CD"));
					unit.put("JBPS_NM", unit.get("JBPS_NM") == null ? "" : unit.get("JBPS_NM"));
					unit.put("ISTDR_YN", unit.get("ISTDR_YN") == null ? "" : unit.get("ISTDR_YN"));
					unit.put("JNCMP_YMD", unit.get("JNCMP_YMD") == null ? "" : unit.get("JNCMP_YMD"));
					unit.put("RETIRE_YMD", unit.get("RETIRE_YMD") == null ? "" : unit.get("RETIRE_YMD"));
					reqUserJoinMapper.insertUnitSystem(unit);
				}
			}
			
			// 학력 정보
			ParameterGroup dmParam7 = dataRequest.getParameterGroup("dsEducation");
			List<Map<String, String>> eduList = dmParam7.getAllRowList();
			log.debug("#### eduList size : " + eduList.size());
			if (eduList != null && eduList.size() > 0) {
				//personalInfoMapper.deleteEducation((String)map1.get("ENFSN_NO"));
				for (int i=0; i < eduList.size(); i++) {
					Map<String, String> map2 = eduList.get(i);
					map2.put("ENFSN_NO", workerId);
					map2.put("USER_ID", userId);
					personalInfoMapper.insertEducation(map2);
				}
			}

			// 근무 이력
			ParameterGroup dmParam8 = dataRequest.getParameterGroup("dsWork");
			List<Map<String, String>> workList = dmParam8.getAllRowList();
			log.debug("#### workList size : " + workList.size());
			if (workList != null && workList.size() > 0) {
				//personalInfoMapper.deleteWork((String)map1.get("ENFSN_NO"));
				for (int i=0; i < workList.size(); i++) {
					Map<String, String> map2 = workList.get(i);
					map2.put("ENFSN_NO", workerId);
					map2.put("USER_ID", userId);
					personalInfoMapper.insertWork(map2);
				}
			}

			// 자격 정보
			ParameterGroup dmParam9 = dataRequest.getParameterGroup("dsQualification");
			List<Map<String, String>> qualificationList = dmParam9.getAllRowList();
			log.debug("#### qualificationList size : " + qualificationList.size());
			if (qualificationList != null && qualificationList.size() > 0 &&
					workerMap.get("QLFC_INFO_MNG_NO") != null && !"".equals(workerMap.get("QLFC_INFO_MNG_NO"))) {
				Map<String, String> map2 = new HashMap<>();
				map2.put("QLFC_INFO_MNG_NO", workerMap.get("QLFC_INFO_MNG_NO"));
				map2.put("USER_ID", userId);
				personalInfoMapper.insertQualificationNo(map2);
				//personalInfoMapper.deleteQualification((String)map1.get("QLFC_INFO_MNG_NO"));
				for (int i=0; i < qualificationList.size(); i++) {
					map2 = qualificationList.get(i);
					map2.put("USER_ID", userId);
					personalInfoMapper.insertQualification(map2);
				}
			}
			
			
			/*
			 * 회원가입 > 추가정보 저장
			 * 2022.08.25 Hee Sung Yoon
			 * 20220916 : null 에러 관련 수정(강화영)
			 */
			ParameterGroup dmParam3 = dataRequest.getParameterGroup("dmCnterEnfsnInfo");
			Map<String, String> enfsnMap = null;
			
			if (dmParam3 != null) {
				enfsnMap = dmParam3.getSingleValueMap();
			}
			
//			Map<String, String> enfsnMap = dmParam3.getSingleValueMap();
			if(enfsnMap != null && (enfsnMap.get("FTE_YN") != null && !"".equals(enfsnMap.get("FTE_YN")))) {
				
				String userId2 = map.get("USER_ID").toString();
				enfsnMap.put("ENFSN_NO", workerId);
				enfsnMap.put("USER_ID", workerId);
				personalInfoMapper.insEnfsnInfo(enfsnMap);
				
				ParameterGroup dmParam4 = dataRequest.getParameterGroup("dsCnterEnfsnCerti");
				if(dmParam4 != null) {
					List<Map<String, String>> certiList = dmParam4.getAllRowList();
					for (int i=0; i < certiList.size(); i++) {
						Map<String, String> certi = new HashMap<>(certiList.get(i));
						certi.put("ENFSN_NO", workerId);
						certi.put("FRST_RGTR_ID", userId2);
						certi.put("LAST_MDFR_ID", userId2);
						personalInfoMapper.insEnfsnCerti(certi);
					}
				}
				
				ParameterGroup dmParam5 = dataRequest.getParameterGroup("dsCnterEnfsnTrnngEdu");
				if(dmParam5 != null) {
					List<Map<String, String>> trnngEduList = dmParam5.getAllRowList();
					for (int i=0; i < trnngEduList.size(); i++) {
						Map<String, String> trnngEdu = new HashMap<>(trnngEduList.get(i));
						trnngEdu.put("ENFSN_NO", workerId);
						trnngEdu.put("FRST_RGTR_ID", userId2);
						trnngEdu.put("LAST_MDFR_ID", userId2);
						personalInfoMapper.insEnfsnTrnngEdu(trnngEdu);
					}
				}
				
				ParameterGroup dmParam6 = dataRequest.getParameterGroup("dsCnterEnfsnYngbgsPrvateCerti");
				if(dmParam6 != null) {
					List<Map<String, String>> prvateCertiList = dmParam6.getAllRowList();
					for (int i=0; i < prvateCertiList.size(); i++) {
						Map<String, String> prvateCerti = new HashMap<>(prvateCertiList.get(i));
						prvateCerti.put("ENFSN_NO", workerId);
						prvateCerti.put("FRST_RGTR_ID", userId2);
						prvateCerti.put("LAST_MDFR_ID", userId2);
						personalInfoMapper.insEnfsnPrvateCerti(prvateCerti);
					}
				}
			}
		}
		
		return returnMap;
	}
	
	
	@SuppressWarnings("unused")
	private boolean juminCheck(String juminStr) {

		//char[] juminChar = juminStr.replaceAll("[^\\d.]", "").toCharArray();  // 주민등록번호를 한자리씩 쪼개서 배열에 담기
		String[] juminChar = juminStr.replaceAll("[^\\d.]", "").split("(?<!^)");  // 주민등록번호를 한자리씩 쪼개서 배열에 담기
	    int[] charr = {2,3,4,5,6,7,8,9,2,3,4,5};  // 곱해줄 숫자 배열

	    int[] jumin = new int[juminChar.length - 1];
	    // 1. 각 자리에 2,3,4,5,6,7,8,9,2,3,4,5 를 곱해줌. 단, 마지막 자리는 빼놓음.
	    for (int i=0; i < juminChar.length - 1; i++) {
	        jumin[i] = Integer.parseInt(juminChar[i]) * charr[i];
	    }
	    int juminlast = Integer.parseInt(juminChar[juminChar.length - 1]);  // 주민등록번호 마지막자리 따로 빼두기

	    // 2. 각 자리의 숫자를 모두 더함
	    int sum = 0;
	    for (int i=0; i < juminChar.length - 1; i++) {
	        sum += jumin[i];
	    }

	    // 3. 11로 나눈 나머지 값을 구함
	    sum = sum % 11;

	    // 4. 11에서 결과값을 뺌 (단, 마지막 결과가 두자리인 경우 다시 10으로 나눈 나머지 값을 구함)
	    sum = 11 - sum;

	    if (sum > 9) {
	        sum = sum % 10;
	    }
	    
	    //System.out.println("sum : " + sum);
	    //System.out.println("juminlast : " + juminlast);

	    // 5. 결과가 주민등록번호 마지막 자리와 일치하면 유효한 주민등록번호임.
	    if (sum == juminlast) {  
	        // 결과값과 주민등록번호 마지막 번호가 일치한다면
	        // id 가 result 인 Element 에 해당 값 삽입
	        //document.getElementById('result').innerHTML = '유효한 주민등록번호 입니다.';
	        return true;
	    } else {
	        // 결과값과 주민등록번호 마지막 번호가 일치하지 않는다면
	        // id 가 result 인 Element 에 해당 값 삽입
	        //document.getElementById('result').innerHTML = '유효하지 않은 주민등록번호 입니다.';
	        return false;
	    }
	}

	@Override
	public List<Map<String, Object>> selectOrgRegion(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Integer orgType = null;
		String unitSystem = null;
		String unitTaskWork = null;
		String part = null;
		int upInstNo = 0;
		
		if (param != null) {
			if (param.getValue("orgType") != null && !"".equals(param.getValue("orgType"))) {
				orgType = Integer.valueOf(param.getValue("orgType"));
			}
			if (param.getValue("unitSystem") != null && !"".equals(param.getValue("unitSystem"))) {
				unitSystem = param.getValue("unitSystem");
			}
			if (param.getValue("unitTaskWork") != null && !"".equals(param.getValue("unitTaskWork"))) {
				unitTaskWork = param.getValue("unitTaskWork");
			}
			if (param.getValue("part") != null && !"".equals(param.getValue("part"))) {
				part = param.getValue("part");
				switch (part) {
				case "03" :
					upInstNo = 1000000948;
					break;
				case "04" :
					upInstNo = 1000000461;
					break;
				case "05" :
					upInstNo = 1000001215;
					break;
				default :
					break;
				}
			}
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORG_TYPE", orgType);
		paramMap.put("UNIT_SYSTEM", unitSystem);
		paramMap.put("UNIT_TASKWORK", unitTaskWork);
		paramMap.put("UP_INST_NO", upInstNo);
		
		return reqUserJoinMapper.selectOrgRegion(paramMap);
	}
	
	@Override
	public List<Map<String, String>> selectSiGunGu(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = param.getSingleValueMap();
		
		return reqUserJoinMapper.selectSiGunGu(paramMap);
		
	}
	
	/**
	 * @Method명   : selectAuthrtSeCd
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectAuthSeCd(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		log.info(" :::::::::: param :::::::::: " + param);
		
		String groupAuthrtSeCd = null;
		String unitSystem      = null;
		String unitTaskWork    = null;
		
		if (param != null) {
			if (param.getValue("groupAuthrtSeCd") != null && !"".equals(param.getValue("groupAuthrtSeCd"))) {
				groupAuthrtSeCd = String.valueOf(param.getValue("groupAuthrtSeCd"));
			}
			if (param.getValue("unitSystem") != null && !"".equals(param.getValue("unitSystem"))) {
				unitSystem = param.getValue("unitSystem");
			}
			if (param.getValue("unitTaskWork") != null && !"".equals(param.getValue("unitTaskWork"))) {
				unitTaskWork = param.getValue("unitTaskWork");
			}
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("GROUP_AUTHRT_SE_CD", groupAuthrtSeCd);
		paramMap.put("UNT_TASKWK_SE_CD", unitTaskWork);
		
		return reqUserJoinMapper.selectAuthSeCd(paramMap);
	}	

	@Override
	public Map<String, String> saveInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> returnMap = new HashMap<>();
		
		ParameterGroup param1 = dataRequest.getParameterGroup("dmOrgDetail");
		//ParameterGroup param2 = dataRequest.getParameterGroup("dsWorkerUnitSystem");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		if (param1 != null) {
			
			Map<String, String> instituteMap = param1.getSingleValueMap();
			
			log.info("#### INST_NO : " + instituteMap.get("INST_NO"));
			log.info("#### INST_NM : " + instituteMap.get("INST_NM"));
			
			if (userId == null || "".equals(userId)) {
				userId = instituteMap.get("USER_ID");
			}
			
			String resultStr = checkValidity2(instituteMap);
			
			if (resultStr != null && !"".equals(resultStr)) {
				log.debug("#### resultStr : " + resultStr);
				returnMap.put("resultStr", resultStr);
				return returnMap;
			}
			//for (String key : paramMap.keySet()) {
				//log.debug(key + " : " + paramMap.get(key));
			//}
			
			//System.out.println("#################");
			
			Map<String, Object> map = new HashMap<String, Object>(instituteMap);
			
			//ScpDb scpDb = new ScpDb();
			
			//map.put("RPRSV_NM_ENCPT", scpDb.scpEncB64((String)map.get("RPRSV_NM_ENCPT")));
			//map.put("PIC_NM_ENCPT", scpDb.scpEncB64((String)map.get("PIC_NM_ENCPT")));
			//map.put("PIC_MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("PIC_MBL_TELNO_ENCPT")));
			//map.put("PIC_EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("PIC_EML_ADDR_ENCPT")));
			//map.put("RRNO_ENCPT", scpDb.scpEncB64((String)map.get("RRNO_ENCPT")));
			map.put("USER_ID2", userId);
			map.put("USER_ID", userId);
			
			instituteMap.put("RPRSV_NM_ENCPT", (String)map.get("RPRSV_NM_ENCPT"));
			instituteMap.put("PIC_NM_ENCPT", (String)map.get("PIC_NM_ENCPT"));
			instituteMap.put("PIC_MBL_TELNO_ENCPT", (String)map.get("PIC_MBL_TELNO_ENCPT"));
			instituteMap.put("PIC_EML_ADDR_ENCPT", (String)map.get("PIC_EML_ADDR_ENCPT"));
			instituteMap.put("RRNO_ENCPT", (String)map.get("RRNO_ENCPT"));
			instituteMap.put("USER_ID2", userId);
			
			//for (String key : map.keySet()) {
				//log.debug(key + " : " + map.get(key));
			//}
			
			// 개인 식별 번호
			//String personalIdNum = reqUserJoinMapper.selectPersonalIdNum(userId);
			//map.put("INDV_IDNTFC_NO", personalIdNum);
			map.put("AGE", 1);
			

			//String workerId = reqUserJoinMapper.getWorkerId(userId);
			//map.put("ENFSN_NO", workerId);
			
			
			mgmtOrgDtlMapper.saveOrgDtl(instituteMap);
			
			
			// 가입 신청 일자
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			map.put("LSFT_IDNTY_YMD", formatedNow);
			
			
			//reqUserJoinMapper.insertPersonalBasicInfo(map);
			
			map.put("JOIN_APLY_DT", formatedNow);
			
			map.put("USER_ID_USE_SE_CD", 1);  // 사용자 ID 사용구분코드 : 신청(1), 승인, 반려, 사용중지, 삭제
			

			//String strEnc = scpDb.scpHashB64((String)map.get("USER_PSWD"));
			map.put("USER_PSWD_ENCPT", (String)map.get("USER_PSWD"));  // 비밀번호 암호화

			
			if (!(map.get("USER_ID") == null || "".equals(map.get("USER_ID")))) {

				Map<String, String> passwordMap = userLoginMapper.selectPasswordMap((String)map.get("USER_ID"));
				
				map.put("BFE_PSWD_ENCPT1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
				map.put("LGN_ERR_CONT", 0);

				int count1 = personalInfoMapper.selectUserInfoExists((String)map.get("USER_ID"));
				if (count1 > 0) {
					map.put("DATAA_CHG_SE_CD", "U");
				} else {
					map.put("DATAA_CHG_SE_CD", "I");
				}
				reqUserJoinMapper.insertUserInfo(map);
				
				personalInfoMapper.insertUserInfoHistory(map);
			}
			
			//추가 기관 데이터 입력 - 센터현황 입력 - 20220911
			ParameterGroup param2 = dataRequest.getParameterGroup("dmAddtngBassInfo");
			if (param2 != null) {
				Map<String, String> dmAddtngBassInfoMap = param2.getSingleValueMap();
				
				dmAddtngBassInfoMap.put("INST_NO", instituteMap.get("INST_NO")); 				// 기관번호
				dmAddtngBassInfoMap.put("USER_ID", userId);		
				dmAddtngBassInfoMap.put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드
				
				mgmtOrgDtlMapper.saveCnterPreconAddingBassInfo(dmAddtngBassInfoMap); // 센터현황T(AKA500)_추가기본정보
			}

			ParameterGroup param3 = dataRequest.getParameterGroup("dmInstlCnsgnInfo");
			if (param3 != null) {
				Map<String, String> dmInstlCnsgnInfoMap = param3.getSingleValueMap();
				
				dmInstlCnsgnInfoMap.put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
				dmInstlCnsgnInfoMap.put("USER_ID", userId);	
				dmInstlCnsgnInfoMap.put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드
					
				mgmtOrgDtlMapper.saveCnterPreconInstlCnsgnInfo(dmInstlCnsgnInfoMap); // 센터현황T(AKA500)_설치및위탁정보
			}
			ParameterGroup param4 = dataRequest.getParameterGroup("dmFcltyInfo"); // 시설정보 DM
			if (param4 != null) {
				Map<String, String> dmFcltyInfo = param4.getSingleValueMap();
				dmFcltyInfo.put("INST_NO", instituteMap.get("INST_NO")); 				 								// 기관번호
				dmFcltyInfo.put("USER_ID", userId);	
				dmFcltyInfo.put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
								
				mgmtOrgDtlMapper.saveCnterPreconFcltyInfo(dmFcltyInfo); // 센터현황T(AKA500)_시설정보
			}
			ParameterGroup param5 = dataRequest.getParameterGroup("dsUseSpce"); // 사용공간세부 DS
			if (param5 != null) {
				List<Map<String, String>> dsUseSpceInsert = param5.getInsertedRowList();
					for(int i=0; i<dsUseSpceInsert.size(); i++) {	
					dsUseSpceInsert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 												// 기관번호
					dsUseSpceInsert.get(i).put("USER_ID", userId);	
					dsUseSpceInsert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		

					mgmtOrgDtlMapper.insertCnterPreconUseSpce(dsUseSpceInsert.get(i)); // 센터현황-사용공간세부T(AKA570)
				}
			}
			ParameterGroup param6 = dataRequest.getParameterGroup("dsUseSpceInfo"); // 사용공간정보세부 DS
			if (param6 != null) {
				List<Map<String, String>> dsUseSpceInfoInsert = param6.getInsertedRowList();
				for(int i=0; i<dsUseSpceInfoInsert.size(); i++) {	
					dsUseSpceInfoInsert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 											      			// 기관번호
					dsUseSpceInfoInsert.get(i).put("USER_ID", userId);	
					dsUseSpceInfoInsert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
	
					mgmtOrgDtlMapper.insertCnterPreconMvmnSheltrCar(dsUseSpceInfoInsert.get(i)); // 센터현황-이동형일시쉼터용차량T(AKA580)
				}
			}
			ParameterGroup param7 = dataRequest.getParameterGroup("dsOschlYngbgsPrvuseSpace"); // 학교밖청소년전용공간 DS
			if (param7 != null) {
				List<Map<String, String>> dsOschlYngbgsPrvuseSpaceInsert = param7.getInsertedRowList();
				for(int i=0; i<dsOschlYngbgsPrvuseSpaceInsert.size(); i++) {
					dsOschlYngbgsPrvuseSpaceInsert.get(i).put("INST_NO", instituteMap.get("INST_NO"));			      								// 기관번호
					dsOschlYngbgsPrvuseSpaceInsert.get(i).put("USER_ID", userId);	
					dsOschlYngbgsPrvuseSpaceInsert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
		
					mgmtOrgDtlMapper.insertCnterPreconOschlYngbgsPrvuseSpace(dsOschlYngbgsPrvuseSpaceInsert.get(i)); // 센터현황-학교밖청소년전용공간T(AKA590)
				}
			}
			ParameterGroup param8 = dataRequest.getParameterGroup("dsYngbgsFclty"); // 청소년시설 DM
			if (param8 != null) {
				List<Map<String, String>>  dsYngbgsFcltyInsert = param8.getInsertedRowList();
				for(int i=0; i<dsYngbgsFcltyInsert.size(); i++) {
					dsYngbgsFcltyInsert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsYngbgsFcltyInsert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsYngbgsFcltyInsert.get(i).put("USER_ID", userId);	
					
					mgmtOrgDtlMapper.saveCnterPreconYngbgsFclty(dsYngbgsFcltyInsert.get(i)); // 센터현황t(AKA560)-청소년시설
				}		
			}
			ParameterGroup param9 = dataRequest.getParameterGroup("dsOperHour"); // 운영시간DS
			if (param9 != null) {
				List<Map<String, String>> dsOperHourInsert  = param9.getInsertedRowList();
				for(int i=0; i<dsOperHourInsert.size(); i++) {
					dsOperHourInsert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsOperHourInsert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsOperHourInsert.get(i).put("USER_ID", userId);		
					
					mgmtOrgDtlMapper.insertCnterPreconOperHour(dsOperHourInsert.get(i)); // 센터현황-운영시간T(AKA510)
				}
			}
			ParameterGroup param10 = dataRequest.getParameterGroup("dsBrofaOper"); // 분소운영DS
			if (param10 != null) {
				List<Map<String, String>> dsBrofaOperInsert  = param10.getInsertedRowList();
				for(int i=0; i<dsBrofaOperInsert.size(); i++) {
					dsBrofaOperInsert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsBrofaOperInsert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsBrofaOperInsert.get(i).put("USER_ID", userId);		
					
					mgmtOrgDtlMapper.insertBrofaOper(dsBrofaOperInsert.get(i)); // 센터현황-분소운영T(AKA520)
				}
			}
			ParameterGroup param11 = dataRequest.getParameterGroup("dmYngbgs1388");
			if (param11 != null) {
				Map<String, String> dmYngbgs1388Map = param11.getSingleValueMap();
				dmYngbgs1388Map.put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
				dmYngbgs1388Map.put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
				dmYngbgs1388Map.put("USER_ID", userId);	

				mgmtOrgDtlMapper.saveCnterPreconYngbgsDscsnTlphon1388(dmYngbgs1388Map); // 센터현황T(AKA500)_청소년상담전화1388	
			}
			ParameterGroup param12 = dataRequest.getParameterGroup("dsOperHour1388"); // 운영시간1388 DS
			if (param12 != null) {
				List<Map<String, String>> dsOperHour1388Insert = param12.getInsertedRowList();
				for(int i=0; i<dsOperHour1388Insert.size(); i++) {	
					dsOperHour1388Insert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsOperHour1388Insert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsOperHour1388Insert.get(i).put("USER_ID", userId);	

					mgmtOrgDtlMapper.insertCnterPrecon1388(dsOperHour1388Insert.get(i)); // 센터현황-1388전화운영시간T(AKA530)
				}
			}
			ParameterGroup param13 = dataRequest.getParameterGroup("dsTpriRcvr1388"); // 청소년전화13881차수신자1388 DS
			if (param13 != null) {
				List<Map<String, String>> dsTpriRcvr1388Insert = param13.getInsertedRowList();
				for(int i=0; i<dsTpriRcvr1388Insert.size(); i++) {	
					dsTpriRcvr1388Insert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsTpriRcvr1388Insert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsTpriRcvr1388Insert.get(i).put("USER_ID", userId);	

					mgmtOrgDtlMapper.insertCnterPreconTelephone1388(dsTpriRcvr1388Insert.get(i)); // 센터현황-1388전화근무현황T(AKA540)
				}
			}
			ParameterGroup param14 = dataRequest.getParameterGroup("dsEcshgStaff1388"); // 1388전담요원근무인원수1388 DS
			if (param14 != null) {
				List<Map<String, String>> dsEcshgStaff1388Insert = param14.getInsertedRowList();
				for(int i=0; i<dsEcshgStaff1388Insert.size(); i++) {
					dsEcshgStaff1388Insert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsEcshgStaff1388Insert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsEcshgStaff1388Insert.get(i).put("USER_ID", userId);	

					mgmtOrgDtlMapper.insertCnterPreconTelephoneStaff1388(dsEcshgStaff1388Insert.get(i)); // 센터현황-1388전담요원현황T(AKA630)
				}
			}
			ParameterGroup param15 = dataRequest.getParameterGroup("dsOperHnf1388"); // 운영인력1388 DS
			if (param15 != null) {
				List<Map<String, String>> dsOperHnf1388Insert = param15.getInsertedRowList();
				for(int i=0; i<dsOperHnf1388Insert.size(); i++) {
					dsOperHnf1388Insert.get(i).put("INST_NO", instituteMap.get("INST_NO")); 										// 기관번호
					dsOperHnf1388Insert.get(i).put("CNTER_PRECON_UNT_TASKWK_SE_CD", instituteMap.get("UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드		
					dsOperHnf1388Insert.get(i).put("USER_ID", userId);	

					mgmtOrgDtlMapper.insertCnterPreconOperHnf1388(dsOperHnf1388Insert.get(i)); // 센터현황-1388운영인력T(AKA550)
				}
			}
		}
		
		return returnMap;
	}

	private String checkValidity2(Map<String, String> map) throws Exception {

        if (map.get("USER_ID") == null || "".equals(map.get("USER_ID"))) {
        	return "사용자 아이디를 입력해주시기 바랍니다.";
        }
        if (map.get("ID_DUPLICATE_CHECK") == null || !"사용 가능".equals(map.get("ID_DUPLICATE_CHECK"))) {
        	return "사용자 아이디 중복을 체크해주시기 바랍니다.";
        }
        Integer existCount = reqUserJoinMapper.selectIdExistsCount(map.get("USER_ID"));
		if (existCount != null && existCount > 0) {
			return "사용자 아이디가 중복됩니다.";
		}
		if (map.get("USER_PSWD") == null || "".equals(map.get("USER_PSWD"))) {
        	return "비밀번호를 입력해주시기 바랍니다.";
        }
        if (map.get("USER_PSWD2") == null || "".equals(map.get("USER_PSWD2"))) {
        	return "확인 비밀번호를 입력해주시기 바랍니다.";
        }
        if (!map.get("USER_PSWD").equals(map.get("USER_PSWD2"))) {
        	return "비밀번호가 일치하지 않습니다.";
        }

        if (map.get("USER_PSWD") != null && !"".equals(map.get("USER_PSWD")) && map.get("USER_PSWD2") != null && !"".equals(map.get("USER_PSWD2"))) {

        	Map<String, String> passwordMap = userLoginMapper.selectPasswordMap(map.get("USER_ID"));
	        
        	String passwordCheckResult = passwordHelper.passwordCheck(null, map.get("USER_PSWD"), map.get("USER_ID"), 
        			null, passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"), passwordMap == null ? null : passwordMap.get("PREV_PASSWORD"), false);
	
	        if (passwordCheckResult != null && !"".equals(passwordCheckResult)) {
	        	return passwordCheckResult;
	        }
        }
        
        if (map.get("INST_NM") == null || "".equals(map.get("INST_NM"))) {
        	return "기관명을 입력해주시기 바랍니다.";
        }
        
        if (map.get("INST_TYPE_SE_CD") == null || "".equals(map.get("INST_TYPE_SE_CD")) || "0".equals(map.get("INST_TYPE_SE_CD"))) {
        	return "기관 유형을 선택해주시기 바랍니다.";
        }
        
        if (map.get("RGN_CD") == null || "".equals(map.get("RGN_CD"))) {
        	return "지역 구분 코드를 입력해주시기 바랍니다.";
        }
        
        if (map.get("BPLC_ZIP") == null || "".equals(map.get("BPLC_ZIP"))) {
        	return "사업장 주소를 입력해주시기 바랍니다.";
        }

        if (map.get("RPRSV_NM_ENCPT") == null || "".equals(map.get("RPRSV_NM_ENCPT"))) {
        	return "대표자명을 입력해주시기 바랍니다.";
        }
        
        if (map.get("RPRS_TELNO") == null || "".equals(map.get("RPRS_TELNO"))) {
        	return "대표전화번호를 입력해주시기 바랍니다.";
        }
        
        if (map.get("USER_PSWD") != null && !"".equals(map.get("USER_PSWD")) && map.get("USER_PSWD2") != null && !"".equals(map.get("USER_PSWD2"))) {

        	String passwordCheckResult2 = passwordHelper.passwordCheck(null, map.get("USER_PSWD"), null, map.get("RPRS_TELNO"), null, null, true);
	
	        if (passwordCheckResult2 != null && !"".equals(passwordCheckResult2)) {
	        	return passwordCheckResult2;
	        }
        }
                
        //if (map.get("PERSONAL_ID_NUM") == null || "".equals(map.get("PERSONAL_ID_NUM"))) {}
        //if (map.get("USE_CLASS_CODE") == null || "".equals(map.get("USE_CLASS_CODE"))) {
        	//return "";
        //}
        //if (map.get("DATE_APPLICATION") == null || "".equals(map.get("DATE_APPLICATION"))) {
        	//return "";
        //}
        
        //if (map.get("PERSONAL_ID_NUM") == null || "".equals(map.get("PERSONAL_ID_NUM"))) {}
        //if (map.get("RRNO") == null || "".equals(map.get("RRNO"))) {
        	//return "주민등록번호를 입력해주시기 바랍니다.";
        //}
        //if (!juminCheck(map.get("RRNO"))) {
        	//return "주민등록번호가 유효하지 않습니다.";
        //}
        //if (map.get("SXDC_SE_CD") == null || "".equals(map.get("SXDC_SE_CD"))) {
        	//return "성별을 선택해주시기 바랍니다.";
        //}
        //if (map.get("BRTH_YMD") == null || "".equals(map.get("BRTH_YMD"))) {
        	//return "생년월일을 입력해주시기 바랍니다.";
        //}
        //if (map.get("AGE") == null || "".equals(map.get("AGE"))) {
        	//return " 입력해주시기 바랍니다.";
        //}
        //if (map.get("WRD_TELNO") == null || "".equals(map.get("WRD_TELNO"))) {
        	//return "전화번호를 입력해주시기 바랍니다.";
        //}
        
        if (!(map.get("RPRS_TELNO") == null || "".equals(map.get("RPRS_TELNO")))) {
	        String phoneNum = map.get("RPRS_TELNO").replaceAll("[^0-9]", "");
	        if (phoneNum.length() < 8 || phoneNum.length() > 11) {
	        	return "대표전화번호를 확인해주시기 바랍니다.";
	        }
        }

        if (!(map.get("RPRS_FXNO") == null || "".equals(map.get("RPRS_FXNO")))) {
	        String phoneNum = map.get("RPRS_FXNO").replaceAll("[^0-9]", "");
	        if (phoneNum.length() < 8 || phoneNum.length() > 11) {
	        	return "대표팩스번호를 확인해주시기 바랍니다.";
	        }
        }
        
        if (!(map.get("PIC_TELNO") == null || "".equals(map.get("PIC_TELNO")))) {
	        String phoneNum = map.get("PIC_TELNO").replaceAll("[^0-9]", "");
	        if (phoneNum.length() < 8 || phoneNum.length() > 11) {
	        	return "담당자 전화번호를 확인해주시기 바랍니다.";
	        }
        }
	    
	    if (!(map.get("PIC_MBL_TELNO_ENCPT") == null || "".equals(map.get("PIC_MBL_TELNO_ENCPT")))) {
	        String mobileNum = map.get("PIC_MBL_TELNO_ENCPT").replaceAll("[^0-9]", "");
	        if (mobileNum.length() < 10 || mobileNum.length() > 11) {
	        	return "담당자 휴대전화번호를 확인해주시기 바랍니다.";
	        }
        }
	    
        if (!(map.get("PIC_EML_ADDR_ENCPT") == null || "".equals(map.get("PIC_EML_ADDR_ENCPT")) || "@".equals(map.get("PIC_EML_ADDR_ENCPT")))) {
	        //if (map.get("PIC_EML_ADDR_ENCPT") == null || "".equals(map.get("PIC_EML_ADDR_ENCPT"))) {
	        	//return "담당자 이메일 주소를 입력해주시기 바랍니다.";
	        //}
	        if (!emailCheck(map.get("PIC_EML_ADDR_ENCPT"))) {
	        	return "담당자 이메일 주소 형식을 확인해주시기 바랍니다.";
	        }
        }
        
        //if (map.get("MSNGR_ID") == null || "".equals(map.get("MSNGR_ID"))) {}
        //if (map.get("ZIP") == null || "".equals(map.get("ZIP"))) {
        	//return "우편번호를 입력해주시기 바랍니다.";
        //}
        //if (map.get("PST_ADDR") == null || "".equals(map.get("PST_ADDR"))) {
        	//return "우편주소를 입력해주시기 바랍니다.";
        //}
        //if (map.get("DADDR") == null || "".equals(map.get("DADDR"))) {
        	//return "상세주소를 입력해주시기 바랍니다.";
        //}
        //if (map.get("COUNSELEE_YN") == null || "".equals(map.get("COUNSELEE_YN"))) {
        	//return "내담자 여부를 선택해주시기 바랍니다.";
        //}

        //if (map.get("UNT_SYS_SE_CD") == null || "".equals(map.get("UNT_SYS_SE_CD"))) {
        	//return "단위 시스템을 선택해주시기 바랍니다.";
        //}
        //if (map.get("OGDP_INST_CD") == null || "".equals(map.get("OGDP_INST_CD"))) {
        	//return "소속 기관을 선택해주시기 바랍니다.";
        //} //else if ("0".equals(map.get("ORG_CODE")) && (map.get("ORG_NAME") == null || "".equals(map.get("ORG_NAME")))) {
        	//return "소속 기관을 입력해주시기 바랍니다.";
        //}
        //if (map.get("OGDP_DEPT_CD") == null || "".equals(map.get("OGDP_DEPT_CD"))) {
        	//return "소속 부서를 선택해주시기 바랍니다.";
        //} //else if ("0".equals(map.get("DEPT_CODE")) && (map.get("DEPT_NAME") == null || "".equals(map.get("DEPT_NAME")))) {
        	//return "소속 부서를 입력해주시기 바랍니다.";
        //}
        //if (map.get("SUB_ORG_ID") == null || "".equals(map.get("SUB_ORG_ID"))) {
        	//return "하부 조직을 선택해주시기 바랍니다.";
        //}
        //if (map.get("ORG_NAME") == null || "".equals(map.get("ORG_NAME"))) {}
        //if (map.get("DEPT_NAME") == null || "".equals(map.get("DEPT_NAME"))) {}
        //if (map.get("UNT_TASKWK_SE_CD") == null || "".equals(map.get("UNT_TASKWK_SE_CD")) || "0".equals(map.get("UNT_TASKWK_SE_CD"))) {
        	//return "단위 시스템을 선택해주시기 바랍니다.";
        //}
        //if (map.get("OGDP_INST_NO") == null || "".equals(map.get("OGDP_INST_NO")) || "0".equals(map.get("OGDP_INST_NO"))) {
        	//return "소속 기관을 선택해주시기 바랍니다.";
        //}
        //if (map.get("OGDP_DEPT_CD") == null || "".equals(map.get("OGDP_DEPT_CD")) || "0".equals(map.get("OGDP_DEPT_CD"))) {
        	//return "소속 부서를 선택해주시기 바랍니다.";
        //}
        //if (map.get("QLFC_SE_CD") == null || "".equals(map.get("QLFC_SE_CD"))) {
        	//return "자격구분코드를 선택해주시기 바랍니다.";
        //}
        
        //if (map.get("LAST_ACBG_SE_CD") == null || "".equals(map.get("LAST_ACBG_SE_CD"))) {
        	//return "최종 학력을 선택해주시기 바랍니다.";
        //}
        //if (map.get("FINAL_EDUCATION") == null || "".equals(map.get("FINAL_EDUCATION"))) {
        	//return "최종 학력 기관명을 입력해주시기 바랍니다.";
        //}

        if (map.get("RSFR_INST_YN") == null || "".equals(map.get("RSFR_INST_YN"))) {
        	return "자원제공주체 여부를 선택해주시기 바랍니다.";
        }
        
		return "";
	}
	
	@Override
	public void saveReconsent(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmReconsent");
		// 시스템 관리에서 종사자 등록시 회원 동의 내역 저장처리 무시
		if (param == null) return;
		
		Map<String, String> map = param.getSingleValueMap();
		
		String consent1 = map.get("PRVC_CLCT_UTZTN_WRTCNS_ESNTL_INFO_CLCT_UTZTN_AGRE_YN");
		String consent2 = map.get("PRVC_CLCT_UTZTN_WRTCNS_CHC_INFO_CLCT_UTZTN_AGRE_YN");
		String consent3 = map.get("SCURTY_WROA_AGRE_YN");
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String userId = "";
		String userNm = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			userNm = loginVO.getUserName();
		} else {
			ParameterGroup param1 = dataRequest.getParameterGroup("dmWorkerDetail");
			Map<String, String> workerMap = param1.getSingleValueMap();
			userId = workerMap.get("USER_ID");
			userNm = workerMap.get("FLNM_ENCPT");
		}

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("consent1", consent1);
		paramMap.put("consent2", consent2);
		paramMap.put("consent3", consent3);
		paramMap.put("userId", userId);
		
		//ScpDb scpDb = new ScpDb();
		//String userNmEncpt = scpDb.scpHashB64(userNm);
		paramMap.put("userNmEncpt", userNm);
		
		
		reqUserJoinMapper.saveReconsent(paramMap);
	}
	
	@Override
	public Map<String, Integer> checkPhoneDuplicate(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String ci = (String)session.getAttribute("ci");
		int count = reqUserJoinMapper.selectCiCount(ci);
		Map<String, Integer> map = new HashMap<>();
		map.put("existCount", count);
		return map;
	}
	
	@Override
	public Map<String, Integer> checkCertificateDuplicate(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String signerDN = (String)session.getAttribute("signerDN");
		int count = reqUserJoinMapper.selectCertificateCount(signerDN);
		Map<String, Integer> map = new HashMap<>();
		map.put("existCount", count);
		return map;
	}
	
	@Override
	public Map<String, Integer> checkSimpleDuplicate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmSimpleDuplicate");
		Map<String, String> mapParam = param.getSingleValueMap();

		String ci = mapParam.get("ci");
		
		log.info("#### checkSimpleDuplicate ci : " + ci);
		
		int count = reqUserJoinMapper.selectCiSimpleCount(ci);
		Map<String, Integer> map = new HashMap<>();
		map.put("existCount", count);
		return map;
	}





}
