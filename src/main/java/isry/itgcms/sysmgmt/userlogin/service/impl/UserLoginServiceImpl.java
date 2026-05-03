/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.syscmmn.email.service.EmailService;
import isry.itgcms.syscmmn.sms.service.SmsService;
import isry.itgcms.sysmgmt.config.service.MgmtCmmnConfigService;
import isry.itgcms.sysmgmt.messagesource.service.MessageService;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.systemenv.service.SystemEnvService;
import isry.itgcms.sysmgmt.userauth.service.InqAuthGrpListService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userjoin.mapper.ReqUserJoinMapper;
import isry.itgcms.sysmgmt.userlogin.mapper.UserLoginMapper;
import isry.itgcms.sysmgmt.userlogin.service.DisconnectUserService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.IP;
import isry.itgcms.util.PasswordHelper;
import isry.redis.service.RedisService3;

/**
 * @파일명        : UserLoginServiceImpl.java
 * @프로그램 설명 : 통합 로그인
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 6. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 6.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="userLoginMapper")
    private UserLoginMapper userLoginMapper;

	@Resource(name="personalInfoMapper")
    private PersonalInfoMapper personalInfoMapper;
	
	@Resource(name = "inqAuthGrpListService")
	private InqAuthGrpListService inqAuthGrpListService;

	@Resource(name = "emailService")
	private EmailService emailService;

	@Resource(name = "smsService")
	private SmsService smsService;

	@Resource(name = "reqUserJoinMapper")
	private ReqUserJoinMapper reqUserJoinMapper;

	@Resource(name = "messageService")
	private MessageService messageService;

	@Autowired
	private RedisService3 redisService;

	@Resource(name = "systemEnvService")
	private SystemEnvService systemEnvService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	@Resource(name = "mgmtCmmnConfigService")
	private MgmtCmmnConfigService mgmtCmmnConfigService;
	
	@Autowired
	private MessageSourceAccessor messageSource;
	
	@Resource(name = "disconnectUserService")
	private DisconnectUserService disconnectUserService;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	/**
	 * @Method명   : userLogin
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2021. 12. 6. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> processUserLogin(DataRequest dataRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<>();

		String loginId = "";
		String loginPass = "";
		
		ParameterGroup dsParam = dataRequest.getParameterGroup("dsParam");

		if (dsParam != null) {
			loginId = dsParam.getValue("USER_ID");
			loginPass = dsParam.getValue("PWD");
		}
		
		ParameterGroup param = dataRequest.getParameterGroup("dmLoginInfo");
		
		if (loginId == null || "".equals(loginId) || loginPass == null || "".equals(loginPass)) {
			if (param != null) {
				loginId = param.getValue("loginId");
				loginPass = param.getValue("loginPass");
			}
		}
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		if (dsParam != null || param != null) {
			
			//String loginId = param.getValue("loginId");
			//String loginPass = param.getValue("loginPass");
			
			if (loginId == null || "".equals(loginId) || loginPass == null || "".equals(loginPass)) {
				return null;
			}

			
			Map<String, Object> userStatusMap = selectUserStatus(loginId);
			if (userStatusMap != null) {
				return userStatusMap;
			}
			
			
			//ScpDb scpDb = new ScpDb();
			//String strEnc = scpDb.scpHashB64(loginPass);
			String strEnc = loginPass;

			UserDetailsVO userDetailsVO = new UserDetailsVO();
			
			userDetailsVO.setId(loginId);
			userDetailsVO.setPass(strEnc);
			
			userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
			
			String userId = "";
			//String server = System.getProperty("SERVER");
			
			//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
				//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
			//}
			
			
			if (userDetailsVO != null && userDetailsVO.getId() != null 
					&& !"".equals(userDetailsVO.getId())) {

				map = processLogin(loginId, map, request, response, userDetailsVO, "userLogin");
				
			} else {
				
				// 로그인 실패
				map.put("loginResult", 0);
				
				log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");
				
				loginFailLog(userDetailsVO, request, loginId);
				
			}
			
			return map;
		}
		
		return null;
	}

	@Override
	public Map<String, Object> processUserLogin3(String userId1, String userPw, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<>();
		
		map.put("loginResult", 0);
		
		String loginId = userId1 == null ? "" : userId1;
		String loginPass = userPw == null ? "" : userPw;
		

		if (RequestContextHolder.getRequestAttributes() == null ||
				!(RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes)) {
			return null;
		}
		
		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		//String loginId = param.getValue("loginId");
		//String loginPass = param.getValue("loginPass");
		
		if (loginId == null || "".equals(loginId) || loginPass == null || "".equals(loginPass)) {
			return null;
		}

		Map<String, Object> userStatusMap = selectUserStatus(loginId);
		if (userStatusMap != null) {
			return userStatusMap;
		}
		
		//ScpDb scpDb = new ScpDb();
		//String strEnc = scpDb.scpHashB64(loginPass);
		String strEnc = loginPass;

		UserDetailsVO userDetailsVO = new UserDetailsVO();
		
		userDetailsVO.setId(loginId);
		userDetailsVO.setPass(strEnc);
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		String userId = "";
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {
			
			map = processLogin(loginId, map, request, response, userDetailsVO, "userLogin3");
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");
			
			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> processUserLogin2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<>();
		
		map.put("loginResult", 0);
		
		String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String loginPass = request.getParameter("userPw") == null ? "" : new String(Base64.getDecoder().decode(request.getParameter("userPw")));
		
		HttpSession session = request.getSession();
		
		session.setAttribute("previousSessionExists", 0);
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		//String loginId = param.getValue("loginId");
		//String loginPass = param.getValue("loginPass");
		
		if (loginId == null || "".equals(loginId) || loginPass == null || "".equals(loginPass)) {
			return null;
		}

		Map<String, Object> userStatusMap = selectUserStatus(loginId);
		if (userStatusMap != null) {
			return userStatusMap;
		}
		
		//ScpDb scpDb = new ScpDb();
		//String strEnc = scpDb.scpHashB64(loginPass);
		String strEnc = loginPass;

		UserDetailsVO userDetailsVO = new UserDetailsVO();

		
		//Integer forceLogin = (Integer)session.getAttribute("forceLogin");
		//log.debug("#### forceLogin : " + forceLogin);
		
		//if (forceLogin != null && forceLogin == 1) {
			//userDetailsVO.setId(loginId);
			//userDetailsVO.setPass(strEnc);
		//} else {
			userDetailsVO.setId(loginId);
			userDetailsVO.setPass(strEnc);
		//}
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		//String userId = "";
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {
			
			map = processLogin(loginId, map, request, response, userDetailsVO, "userLogin2");
			
			session.setAttribute("processedUserLogin2", 1);
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + "fail");
			
			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
		
	}
	
	@Override
	public Map<String, Object> processUserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
	
		String loginId = request.getParameter("userId");
		String loginPass = request.getParameter("userPass");
		
		if (loginId == null || "".equals(loginId) || loginPass == null || "".equals(loginPass)) {
			return null;
		}

		UserDetailsVO userDetailsVO = new UserDetailsVO();
		
		userDetailsVO.setId(loginId);
		userDetailsVO.setPass(loginPass);
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		String userId = ""; //SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {
			
			map = processLogin(loginId, map, request, response, userDetailsVO, "userLogin_");
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");

			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
	}

	@Override
	public void processLogoutLog(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		UserDetailsVO userDetailsVO = getLoginSessionVO(request);
		
		// 사용자별 기관 권한 세션 삭제
		userInstAuthService.destorySession(request);
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

		//if (!"local".equals(profile) && !"pre".equals(profile)) {
		if (!"local".equals(profile)) {
			redisService.processRedisLogout("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		}
		
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId())) {
			userLoginMapper.logoutLog(userDetailsVO);
			userLoginMapper.deleteSessionExpireMessage(IP.getClientIP(request));
		}
		
		//List<String> keyList = redisService.selectKeys("rg");
		
		//if (keyList != null) {
			//for (int i=0; i < keyList.size(); i++) {
				//log.debug("keys " + i + " : " + keyList.get(i));
			//}
		//}
	}
	
	@Override
	public Map<String, Object> processRegistCertificate(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String signType = (String)session.getAttribute("signType");
		String loginId = (String)session.getAttribute("loginId");
		String birthday = (String)session.getAttribute("birthday");
		String signerDN = (String)session.getAttribute("signerDN");
        
		if ("1".equals(signType)) {
			
			Map<String, String> map = new HashMap<>();
			map.put("LOGIN_ID", loginId);
			map.put("BIRTHDAY", birthday);
			map.put("SIGNER_DN", signerDN);
			
			int count = userLoginMapper.selectExistsCertificateCount1(map);
			
			log.debug("#### existing certificate count : " + count);
			
			if (count > 0) {
				resultMap.put("msg", "이미 등록된 인증서입니다.");
				resultMap.put("result", 0);
				return resultMap;
			}
			
			int result = userLoginMapper.registCertificate(map);
			
			if (result == 1) {
				
				userLoginMapper.updateCertificateHistory(map);
				
				resultMap.put("result", 1);
				return resultMap;
			} else {
				resultMap.put("msg", "일치하는 회원정보가 없습니다.");
				//throw new UserException("errors.inaccurateMemberInfo");
			}
		}
		
		resultMap.put("result", 0);
		return resultMap;
	}

	@Override
	public Map<String, Object> processRegistFinanceCertificate(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String signType = (String)session.getAttribute("signType");
		String loginId = (String)session.getAttribute("loginId");
		String birthday = (String)session.getAttribute("birthday");
		String signerDN = (String)session.getAttribute("signerDN");
        
		if ("4".equals(signType)) {
			
			Map<String, String> map = new HashMap<>();
			map.put("LOGIN_ID", loginId);
			map.put("BIRTHDAY", birthday);
			map.put("SIGNER_DN", signerDN);
			
			int count = userLoginMapper.selectExistsFinanceCertificateCount1(map);
			
			log.debug("#### existing certificate count : " + count);
			
			if (count > 0) {
				resultMap.put("msg", "이미 등록된 인증서입니다.");
				resultMap.put("result", 0);
				return resultMap;
			}
			
			int result = userLoginMapper.registFinanceCertificate(map);
			
			if (result == 1) {
				
				userLoginMapper.updateFinanceCertificateHistory(map);
				
				resultMap.put("result", 1);
				return resultMap;
			} else {
				resultMap.put("msg", "일치하는 회원정보가 없습니다.");
			}
		}
		
		resultMap.put("result", 0);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteCertificate(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String signType = (String)session.getAttribute("signType");
		String loginId = (String)session.getAttribute("loginId");
		String birthday = (String)session.getAttribute("birthday");
		String signerDN = (String)session.getAttribute("signerDN");
        
		if ("3".equals(signType)) {
			
			Map<String, String> map = new HashMap<>();
			map.put("LOGIN_ID", loginId);
			map.put("BIRTHDAY", birthday);
			map.put("SIGNER_DN", signerDN);
			
			int count = userLoginMapper.selectExistsCertificateCount2(map);
			
			log.debug("#### existing certificate count : " + count);
			
			if (count == 0) {
				resultMap.put("msg", "등록된 인증서가 없습니다.");
				resultMap.put("result", 0);
				return resultMap;
			}
			
			int result = userLoginMapper.deleteCertificate(map);
			
			if (result == 1) {
				
				map.put("SIGNER_DN", "");
				userLoginMapper.updateCertificateHistory(map);
				
				resultMap.put("result", 1);
				return resultMap;
			} else {
				resultMap.put("msg", "일치하는 회원정보가 없습니다.");
			}
		}
		
		resultMap.put("result", 0);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteFinanceCertificate(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String signType = (String)session.getAttribute("signType");
		String loginId = (String)session.getAttribute("loginId");
		String birthday = (String)session.getAttribute("birthday");
		String signerDN = (String)session.getAttribute("signerDN");
        
		if ("6".equals(signType)) {
			
			Map<String, String> map = new HashMap<>();
			map.put("LOGIN_ID", loginId);
			map.put("BIRTHDAY", birthday);
			map.put("SIGNER_DN", signerDN);
			
			int count = userLoginMapper.selectExistsFinanceCertificateCount2(map);
			
			log.debug("#### existing certificate count : " + count);
			
			if (count == 0) {
				resultMap.put("msg", "등록된 인증서가 없습니다.");
				resultMap.put("result", 0);
				return resultMap;
			}
			
			int result = userLoginMapper.deleteFinanceCertificate(map);
			
			if (result == 1) {
				
				map.put("SIGNER_DN", "");
				userLoginMapper.updateFinanceCertificateHistory(map);
				
				resultMap.put("result", 1);
				return resultMap;
			} else {
				resultMap.put("msg", "일치하는 회원정보가 없습니다.");
			}
		}
		
		resultMap.put("result", 0);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> processLoginCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String loginId1 = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String loginPass1 = request.getParameter("userPw") == null ? "" : new String(Base64.getDecoder().decode(request.getParameter("userPw")));
		
		String signType = (String)session.getAttribute("signType");
		String signerDN = (String)session.getAttribute("signerDN");
        
		if (!"2".equals(signType)) {
			return null;
		}
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("loginResult", 0);
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		//String loginId = param.getValue("loginId");
		//String loginPass = param.getValue("loginPass");
		
		String loginId = userLoginMapper.selectLoginIdFromCertificate(signerDN);
		
		if (loginId == null || "".equals(loginId)) {
			//return null;
			map.put("msg", "로그인 하단의 인증서관리 메뉴에서 공동인증서를 등록하여 주시기 바랍니다.");
			return map;
		}

		Map<String, Object> userStatusMap = selectUserStatus(loginId);
		if (userStatusMap != null) {
			return userStatusMap;
		}
		
		//ScpDb scpDb = new ScpDb();
		
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		
		userDetailsVO.setId(loginId);
		userDetailsVO.setCertificate("Y");
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		String userId = "";
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		
		UserDetailsVO userDetailsVO1 = new UserDetailsVO();
		userDetailsVO1.setId(loginId1);
		//String strEnc = scpDb.scpHashB64(loginPass1);
		String strEnc = loginPass1;
		userDetailsVO1.setPass(strEnc);
	
		userDetailsVO1 = userLoginMapper.userLogin(userDetailsVO1);
	
		if (!(userDetailsVO1 != null && userDetailsVO1.getId() != null &&
			userDetailsVO != null && userDetailsVO.getId() != null &&
			userDetailsVO1.getId().equals(userDetailsVO.getId()))) {
			
			log.debug("userDetailsVO1.getId() : " + (userDetailsVO1 == null ? "" : userDetailsVO1.getId()));
			log.debug("userDetailsVO.getId() : " + (userDetailsVO == null ? "" : userDetailsVO.getId()));
			log.debug("userDetailsVO1.getId() != userDetailsVO.getId()");
			
			loginFailLog(userDetailsVO, request, loginId);
			
			map.put("msg", "로그인에 실패하였습니다.");
			return map;
		}
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {

			map = processLogin(loginId, map, request, response, userDetailsVO, "loginCertificate");
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");

			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
	}

	@Override
	public Map<String, Object> processLoginFinanceCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String loginId1 = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String loginPass1 = request.getParameter("userPw") == null ? "" : new String(Base64.getDecoder().decode(request.getParameter("userPw")));
		
		String signType = (String)session.getAttribute("signType");
		String signerDN = (String)session.getAttribute("signerDN");
        
		if (!"5".equals(signType)) {
			return null;
		}
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("loginResult", 0);
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		//String loginId = param.getValue("loginId");
		//String loginPass = param.getValue("loginPass");
		
		String loginId = userLoginMapper.selectLoginIdFromFinanceCertificate(signerDN);
		
		if (loginId == null || "".equals(loginId)) {
			//return null;
			map.put("msg", "로그인 하단의 인증서관리 메뉴에서 금융인증서를 등록하여 주시기 바랍니다.");
			return map;
		}

		Map<String, Object> userStatusMap = selectUserStatus(loginId);
		if (userStatusMap != null) {
			return userStatusMap;
		}
		
		//ScpDb scpDb = new ScpDb();
		
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		
		userDetailsVO.setId(loginId);
		userDetailsVO.setCertificate("Y");
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		String userId = "";
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		
		UserDetailsVO userDetailsVO1 = new UserDetailsVO();
		userDetailsVO1.setId(loginId1);
		//String strEnc = scpDb.scpHashB64(loginPass1);
		String strEnc = loginPass1;
		userDetailsVO1.setPass(strEnc);
	
		userDetailsVO1 = userLoginMapper.userLogin(userDetailsVO1);
	
		if (!(userDetailsVO1 != null && userDetailsVO1.getId() != null &&
			userDetailsVO != null && userDetailsVO.getId() != null &&
			userDetailsVO1.getId().equals(userDetailsVO.getId()))) {
			
			loginFailLog(userDetailsVO, request, loginId);
			
			map.put("msg", "로그인에 실패하였습니다.");
			return map;
		}
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {

			map = processLogin(loginId, map, request, response, userDetailsVO, "loginFinanceCertificate");
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");

			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
	}
	
	@Override
	public String savePassword(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		if (dmParam != null) {
			
			log.info("#### Yes Password. : " + request.getSession().getAttribute("userId"));
			
			if (dmParam.getValue("userId") == null || dmParam.getValue("userId").trim().equals("")) {
				return "아이디 정보가 없습니다.";
			}
			
			String currentPassword = new String(Base64.getDecoder().decode(dmParam.getValue("currentPassword")));
			String newPassword1 = new String(Base64.getDecoder().decode(dmParam.getValue("newPassword1")));
			String newPassword2 = new String(Base64.getDecoder().decode(dmParam.getValue("newPassword2")));

			
			if (currentPassword == null || "".equals(currentPassword)) {
				return "기존 비밀번호를 입력해주시기 바랍니다.";
			}
			if (newPassword1 == null || "".equals(newPassword1)) {
				return "신규 비밀번호를 입력해주시기 바랍니다.";
			}
			if (newPassword2 == null || "".equals(newPassword2)) {
				return "신규 확인 비밀번호를 입력해주시기 바랍니다.";
			}
			if (!newPassword2.equals(newPassword1)) {
				return "신규 비밀번호가 일치하지 않습니다.";
			}
			

			//ScpDb scpDb = new ScpDb();
			
			Map<String, String> map = new HashMap<>();
			map.put("currentPassword", currentPassword);
			map.put("newPassword", newPassword1);

			UserDetailsVO loginVO = getLoginSessionVO(request);
			String userId = "";
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId = loginVO.getId();
			}
			
			map.put("userId", dmParam.getValue("userId"));
			map.put("userId2", userId);
			map.put("USER_ID2", userId);

			//String currentPasswordEncpt = scpDb.scpHashB64(currentPassword);
			//map.put("currentPasswordEncpt", currentPasswordEncpt);
			
			//Integer count = userLoginMapper.selectCurrentPassword(map);
			//if (count < 1) {
				//return "기존 비밀번호가 일치하지 않습니다.";
			//}
			
        	Map<String, String> passwordMap = userLoginMapper.selectPasswordMap(map.get("userId"));

			String passwordCheckResult = passwordHelper.passwordCheck(currentPassword, newPassword1, 
					dmParam.getValue("userId"), dmParam.getValue("mobileNum"), 
					passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"), passwordMap == null ? null : passwordMap.get("PREV_PASSWORD"), false);

	        if (passwordCheckResult != null && !"".equals(passwordCheckResult)) {
	        	return passwordCheckResult;
	        }

			//String userPswdEncpt = scpDb.scpHashB64(newPassword1);
			String userPswdEncpt = newPassword1;
			map.put("userPswdEncpt", userPswdEncpt);
			map.put("USER_PSWD_ENCPT", userPswdEncpt);
			map.put("bfePswdEncpt1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
			map.put("BFE_PSWD_ENCPT1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
			map.put("LGN_ERR_CONT", "0");
			
			userLoginMapper.savePassword(map);
			
			map.put("USER_ID", dmParam.getValue("userId"));
			map.put("DATAA_CHG_SE_CD", "U");
			personalInfoMapper.insertUserInfoHistory(new HashMap<String, Object>(map));
		
		} else {
			log.info("#### No Password. : " + request.getSession().getAttribute("userId"));
			return "입력 데이터가 없습니다.";
		}
		
		return "";
	}
	

	@Override
	public String savePasswordNew(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		if (dmParam != null) {

			String newPassword1 = new String(Base64.getDecoder().decode(dmParam.getValue("newPassword1")));
			String newPassword2 = new String(Base64.getDecoder().decode(dmParam.getValue("newPassword2")));

			
			if (newPassword1 == null || "".equals(newPassword1)) {
				return "비밀번호를 입력해주시기 바랍니다.";
			}
			if (newPassword2 == null || "".equals(newPassword2)) {
				return "확인 비밀번호를 입력해주시기 바랍니다.";
			}
			if (!newPassword2.equals(newPassword1)) {
				return "비밀번호가 일치하지 않습니다.";
			}

			//ScpDb scpDb = new ScpDb();
			
			Map<String, String> map = new HashMap<>();
			map.put("newPassword", newPassword1);

			UserDetailsVO loginVO = getLoginSessionVO(request);
			String userId = "";
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId = loginVO.getId();
			}
			
			map.put("userId", dmParam.getValue("userId"));
			map.put("userId2", userId);
			map.put("USER_ID2", userId);

			Map<String, String> passwordMap = userLoginMapper.selectPasswordMap(map.get("userId"));
			
			String passwordCheckResult = passwordHelper.passwordCheck(null, newPassword1, 
					dmParam.getValue("userId"), dmParam.getValue("mobileNum"), 
					passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"), passwordMap == null ? null : passwordMap.get("PREV_PASSWORD"), false);

	        if (passwordCheckResult != null && !"".equals(passwordCheckResult)) {
	        	return passwordCheckResult;
	        }
			
			//String userPswdEncpt = scpDb.scpHashB64(newPassword1);
			String userPswdEncpt = newPassword1;
			map.put("userPswdEncpt", userPswdEncpt);
			map.put("USER_PSWD_ENCPT", userPswdEncpt);
			map.put("bfePswdEncpt1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
			map.put("BFE_PSWD_ENCPT1", passwordMap == null ? null : passwordMap.get("CURR_PASSWORD"));
			map.put("LGN_ERR_CONT", "0");
			
			userLoginMapper.savePasswordNew(map);
			
			map.put("USER_ID", dmParam.getValue("userId"));
			map.put("DATAA_CHG_SE_CD", "U");
			personalInfoMapper.insertUserInfoHistory(new HashMap<String, Object>(map));
			
			// 쿠키 및 세션 세팅
			Cookie cookie = new Cookie("userCreated", "1");
			//cookie.setDomain("localhost");
			cookie.setPath("/");
			// 10분간 저장
			cookie.setMaxAge(10 * 60 * 60);
			// 30초간 저장
			//cookie.setMaxAge(30 * 60);
			//cookie.setSecure(true);
			response.addCookie(cookie);
		}
		
		return "";
	}
	
	@Override
	public void updatePasswordChangeDate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
				
		if (userId != null && !"".equals(userId)) { 
			userLoginMapper.updatePasswordChangeDate(userId);
		}
	}

	// 아이디 찾기
	@Override
	public Map<String, String> processFindId(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> msgMap = new HashMap<>();
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		
		if ("email".equals(dmParam.getValue("method"))) {

			String email = dmParam.getValue("email");
			String name = dmParam.getValue("name");
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			//map.put("email", scpDb.scpEncB64(email));
			//map.put("name", scpDb.scpEncB64(name));
			map.put("email", email);
			map.put("name", name);
			List<String> ids = userLoginMapper.findIdEmail(map);
			if (ids == null || ids.size() == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			String idStr = "";
			for (int i=0; i < ids.size(); i++) {
				if (i > 0) {
					idStr += ", ";
				}
				idStr += ids.get(i);
			}
			
			String content = "[청소년 안전망 시스템]<br/><br/>아이디 찾기 안내입니다.<br/><br/>" + name + " 님의 아이디는 "
					+ idStr + " 입니다.";
			
			Map<String, String> emailMap = new HashMap<>();
			emailMap.put("contents", content);
			emailMap.put("sender", "no-reply@1388.kr");
			emailMap.put("title", "[청소년 안전망 시스템] 아이디 찾기");
			emailMap.put("userId", "system");
			ParameterGroup emailParam = new ParameterGroup("dmParam", emailMap);
			dataRequest.putParameterGroup(emailParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("EML_ADDR", email);
			receiverMap.put("FLNM", name);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			emailService.insertEmail(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "이메일이 발송되었습니다.");
			
			return msgMap;
			
		} else if ("phone".equals(dmParam.getValue("method"))) {

			String phone = dmParam.getValue("phone");
			String name = dmParam.getValue("name");
			
			if (phone == null) {
				phone = "";
			}
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			//map.put("phone", scpDb.scpEncB64(phone));
			//map.put("name", scpDb.scpEncB64(name));
			
			phone = phone.replaceAll("[^\\d]", "");
			map.put("phone1", phone);
			phone = Formatter.phoneFormat(phone, 1);
			map.put("phone2", phone);
			map.put("name", name);
			
			List<String> ids = userLoginMapper.findIdPhone(map);
			if (ids == null || ids.size() == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			String idStr = "";
			for (int i=0; i < ids.size(); i++) {
				if (i > 0) {
					idStr += ", ";
				}
				idStr += ids.get(i);
			}
			//String pass = PasswordHelper.generatePassword(8);
			String content = "[청소년 안전망 시스템]\n아이디 찾기 안내입니다.\n\n" + name + " 님의 아이디는 "
					+ idStr + " 입니다.";
			
			Map<String, String> phoneMap = new HashMap<>();
			phoneMap.put("contents", content);
			phoneMap.put("sender", "0516623229");
			ParameterGroup phoneParam = new ParameterGroup("dmParam", phoneMap);
			dataRequest.putParameterGroup(phoneParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("MBL_TELNO", phone);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver2", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			//emailService.insertEmail(request, dataRequest);
			
			smsService.insertSMS(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "휴대폰으로 문자가 발송되었습니다.");
			return msgMap;
		}
		
		return msgMap;
	}

	// 아이디 찾기 2
	@Override
	public Map<String, String> processFindId2(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> msgMap = new HashMap<>();
		
		if ("email".equals(request.getParameter("method"))) {

			String email = request.getParameter("email");
			String name = request.getParameter("name");
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			//map.put("email", scpDb.scpEncB64(email));
			//map.put("name", scpDb.scpEncB64(name));
			map.put("email", email);
			map.put("name", name);
			List<String> ids = userLoginMapper.findIdEmail(map);
			if (ids == null || ids.size() == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			String idStr = "";
			for (int i=0; i < ids.size(); i++) {
				if (i > 0) {
					idStr += ", ";
				}
				idStr += ids.get(i);
			}
			
			String content = "[청소년 안전망 시스템]<br/><br/>아이디 찾기 안내입니다.<br/><br/>" + name + " 님의 아이디는 "
					+ idStr + " 입니다.";
			
			Map<String, String> emailMap = new HashMap<>();
			emailMap.put("contents", content);
			emailMap.put("sender", "no-reply@1388.kr");
			emailMap.put("title", "[청소년 안전망 시스템] 아이디 찾기");
			emailMap.put("userId", "system");
			ParameterGroup emailParam = new ParameterGroup("dmParam", emailMap);
			dataRequest.putParameterGroup(emailParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("EML_ADDR", email);
			receiverMap.put("FLNM", name);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			emailService.insertEmail(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "이메일이 발송되었습니다.");
			
			return msgMap;
			
		} else if ("phone".equals(request.getParameter("method"))) {

			String phone = request.getParameter("phone");
			String name = request.getParameter("name");
			
			if (phone == null) {
				phone = "";
			}
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			//map.put("phone", scpDb.scpEncB64(phone));
			//map.put("name", scpDb.scpEncB64(name));
			
			phone = phone.replaceAll("[^\\d]", "");
			map.put("phone1", phone);
			phone = Formatter.phoneFormat(phone, 1);
			map.put("phone2", phone);
			map.put("name", name);
			
			List<String> ids = userLoginMapper.findIdPhone(map);
			if (ids == null || ids.size() == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			String idStr = "";
			for (int i=0; i < ids.size(); i++) {
				if (i > 0) {
					idStr += ", ";
				}
				idStr += ids.get(i);
			}
			//String pass = PasswordHelper.generatePassword(8);
			String content = "[청소년 안전망 시스템]\n아이디 찾기 안내입니다.\n\n" + name + " 님의 아이디는 "
					+ idStr + " 입니다.";
			
			Map<String, String> phoneMap = new HashMap<>();
			phoneMap.put("contents", content);
			phoneMap.put("sender", "0516623229");
			ParameterGroup phoneParam = new ParameterGroup("dmParam", phoneMap);
			dataRequest.putParameterGroup(phoneParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("MBL_TELNO", phone);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver2", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			//emailService.insertEmail(request, dataRequest);
			
			smsService.insertLMS(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "휴대폰으로 문자가 발송되었습니다.");
			return msgMap;
		}
		
		return msgMap;
	}

	// 비밀번호 찾기
	@Override
	public Map<String, String> processFindPw(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> msgMap = new HashMap<>();
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		
		if ("email".equals(dmParam.getValue("method"))) {

			String userId = dmParam.getValue("userId");
			String email = dmParam.getValue("email");
			String name = dmParam.getValue("name");
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			String pass = passwordHelper.generatePassword(8);
			map.put("userId", userId);
			map.put("name", name);
			//map.put("nameEncpt", scpDb.scpEncB64(name));
			map.put("nameEncpt", name);
			map.put("email", email);
			//map.put("emailEncpt", scpDb.scpEncB64(email));
			map.put("emailEncpt", email);
			
			Integer count = userLoginMapper.selectUserIdCount(map);
			if (count == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			
			//map.put("pass", scpDb.scpHashB64(pass));
			map.put("pass", pass);
			
			Integer cnt = userLoginMapper.setPasswordTemporary(map);

			if (cnt == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "임시 비밀번호가 세팅되지 않았습니다.");
				return msgMap;
			}
			
			String content = "[청소년 안전망 시스템]<br/><br/>임시 비밀번호 안내입니다.<br/><br/>" + name + " 님 (아이디 " + userId + ")의 임시 비밀번호는 "
					+ pass + " 입니다.";
			
			Map<String, String> emailMap = new HashMap<>();
			emailMap.put("contents", content);
			emailMap.put("sender", "no-reply@1388.kr");
			emailMap.put("title", "[청소년 안전망 시스템] 비밀번호 찾기");
			emailMap.put("userId", "system");
			ParameterGroup emailParam = new ParameterGroup("dmParam", emailMap);
			dataRequest.putParameterGroup(emailParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("EML_ADDR", email);
			receiverMap.put("FLNM", name);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			emailService.insertEmail(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "이메일이 발송되었습니다.");
			
			if (userId != null && !"".equals(userId)) { 
				userLoginMapper.updatePasswordChangeDate(userId);
			}
			
			return msgMap;
			
		} else if ("phone".equals(dmParam.getValue("method"))) {

			String userId = dmParam.getValue("userId");
			String phone = dmParam.getValue("phone");
			String name = dmParam.getValue("name");
			
			if (phone == null) {
				phone = "";
			}
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			String pass = passwordHelper.generatePassword(8);
			map.put("userId", userId);
			map.put("name", name);
			//map.put("nameEncpt", scpDb.scpEncB64(name));
			map.put("nameEncpt", name);
			map.put("phone", phone);
			//map.put("phoneEncpt", scpDb.scpEncB64(phone));
			map.put("phoneEncpt", phone);
			
			phone = phone.replaceAll("[^\\d]", "");
			map.put("phone1", phone);
			phone = Formatter.phoneFormat(phone, 1);
			map.put("phone2", phone);
			
			
			Integer count = userLoginMapper.selectUserIdCount(map);
			if (count == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			
			//map.put("pass", scpDb.scpHashB64(pass));
			map.put("pass", pass);
			
			Integer cnt = userLoginMapper.setPasswordTemporary(map);
			
			if (cnt == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "임시 비밀번호가 세팅되지 않았습니다.");
				return msgMap;
			}
			
			String content = "[청소년 안전망 시스템]\n임시 비밀번호 안내입니다.\n\n" + name + " 님 (아이디 " + userId + ")의 임시 비밀번호는 "
					+ pass + " 입니다.";
			
			Map<String, String> phoneMap = new HashMap<>();
			phoneMap.put("contents", content);
			phoneMap.put("sender", "0516623229");
			ParameterGroup phoneParam = new ParameterGroup("dmParam", phoneMap);
			dataRequest.putParameterGroup(phoneParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("MBL_TELNO", phone);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver2", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			//emailService.insertEmail(request, dataRequest);
			
			smsService.insertSMS(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "휴대폰으로 문자가 발송되었습니다.");
			
			if (userId != null && !"".equals(userId)) { 
				userLoginMapper.updatePasswordChangeDate(userId);
			}
			
			return msgMap;
		}
		
		return msgMap;
	}

	// 비밀번호 찾기 2
	@Override
	public Map<String, String> processFindPw2(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> msgMap = new HashMap<>();
		
		if ("email".equals(request.getParameter("method"))) {

			String userId = request.getParameter("userId");
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			String pass = passwordHelper.generatePassword(8);
			map.put("userId", userId);
			map.put("name", name);
			//map.put("nameEncpt", scpDb.scpEncB64(name));
			map.put("nameEncpt", name);
			map.put("email", email);
			//map.put("emailEncpt", scpDb.scpEncB64(email));
			map.put("emailEncpt", email);
			
			log.info("#### userId : " + userId);
			log.info("#### name : " + name);
			log.info("#### email : " + email);
			
			Integer count = userLoginMapper.selectUserIdCount(map);
			if (count == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			
			//map.put("pass", scpDb.scpHashB64(pass));
			map.put("pass", pass);
			
			Integer cnt = userLoginMapper.setPasswordTemporary(map);

			if (cnt == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "임시 비밀번호가 세팅되지 않았습니다.");
				return msgMap;
			}
			
			String content = "[청소년 안전망 시스템]<br/><br/>임시 비밀번호 안내입니다.<br/><br/>" + name + " 님 (아이디 " + userId + ")의 임시 비밀번호는 "
					+ pass + " 입니다.";
			
			Map<String, String> emailMap = new HashMap<>();
			emailMap.put("contents", content);
			emailMap.put("sender", "no-reply@1388.kr");
			emailMap.put("title", "[청소년 안전망 시스템] 비밀번호 찾기");
			emailMap.put("userId", "system");
			ParameterGroup emailParam = new ParameterGroup("dmParam", emailMap);
			dataRequest.putParameterGroup(emailParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("EML_ADDR", email);
			receiverMap.put("FLNM", name);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			emailService.insertEmail(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "이메일이 발송되었습니다.");
			
			if (userId != null && !"".equals(userId)) { 
				userLoginMapper.updatePasswordChangeDate(userId);
			}
			
			return msgMap;
			
		} else if ("phone".equals(request.getParameter("method"))) {

			String userId = request.getParameter("userId");
			String phone = request.getParameter("phone");
			String name = request.getParameter("name");
			
			if (phone == null) {
				phone = "";
			}
			Map<String, String> map = new HashMap<>();
			//ScpDb scpDb = new ScpDb();
			String pass = passwordHelper.generatePassword(8);
			map.put("userId", userId);
			map.put("name", name);
			//map.put("nameEncpt", scpDb.scpEncB64(name));
			map.put("nameEncpt", name);
			map.put("phone", phone);
			//map.put("phoneEncpt", scpDb.scpEncB64(phone));
			map.put("phoneEncpt", phone);

			phone = phone.replaceAll("[^\\d]", "");
			map.put("phone1", phone);
			phone = Formatter.phoneFormat(phone, 1);
			map.put("phone2", phone);
			
			Integer count = userLoginMapper.selectUserIdCount(map);
			if (count == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "해당 아이디가 없습니다.");
				return msgMap;
			}
			
			//map.put("pass", scpDb.scpHashB64(pass));
			map.put("pass", pass);
			
			Integer cnt = userLoginMapper.setPasswordTemporary(map);
			
			if (cnt == 0) {
				msgMap.put("resultCode", "0");
				msgMap.put("msg", "임시 비밀번호가 세팅되지 않았습니다.");
				return msgMap;
			}
			
			String content = "[청소년 안전망 시스템]\n임시 비밀번호 안내입니다.\n\n" + name + " 님 (아이디 " + userId + ")의 임시 비밀번호는 "
					+ pass + " 입니다.";
			
			Map<String, String> phoneMap = new HashMap<>();
			phoneMap.put("contents", content);
			phoneMap.put("sender", "0516623229");
			ParameterGroup phoneParam = new ParameterGroup("dmParam", phoneMap);
			dataRequest.putParameterGroup(phoneParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("MBL_TELNO", phone);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver2", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			//emailService.insertEmail(request, dataRequest);
			
			smsService.insertLMS(request, dataRequest);
			
			msgMap.put("resultCode", "1");
			msgMap.put("msg", "휴대폰으로 문자가 발송되었습니다.");
			
			if (userId != null && !"".equals(userId)) { 
				userLoginMapper.updatePasswordChangeDate(userId);
			}
			
			return msgMap;
		}
				
		return msgMap;
	}


	@Override
	public Map<String, Object> processRegistPhone(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String loginId = request.getParameter("id");
		String birthday = request.getParameter("birthday");
		String ci = (String)session.getAttribute("ci");
		session.setAttribute("ci", "");
		
		Map<String, String> map = new HashMap<>();
		map.put("LOGIN_ID", loginId);
		map.put("BIRTHDAY", birthday);
		map.put("CI", ci);
		
		int count = reqUserJoinMapper.selectCiCount2(map);
		
		log.info("#### existing phone count : " + count);
		
		if (count > 0) {
			resultMap.put("msg", "이미 등록된 휴대폰 인증입니다.");
			resultMap.put("result", 0);
			return resultMap;
		}
		
		int result = userLoginMapper.registPhone(map);
		
		if (result == 1) {
			
			userLoginMapper.updatePhoneHistory(map);
			
			resultMap.put("result", 1);
			return resultMap;
		} else {
			resultMap.put("msg", "일치하는 회원 정보가 없습니다.");
		}
		
		resultMap.put("result", 0);
		
		return resultMap;
	}


	@Override
	public Map<String, Object> deletePhone(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String loginId = request.getParameter("id");
		String birthday = request.getParameter("birthday");
		String ci = (String)session.getAttribute("ci");
		session.setAttribute("ci", "");
			
		Map<String, String> map = new HashMap<>();
		map.put("LOGIN_ID", loginId);
		map.put("BIRTHDAY", birthday);
		map.put("CI", ci);
		
		int count = reqUserJoinMapper.selectCiCount(ci);
		
		log.debug("#### existing phone count : " + count);
		
		if (count == 0) {
			resultMap.put("msg", "등록된 휴대폰 인증이 없습니다.");
			resultMap.put("result", 0);
			return resultMap;
		}
		
		int result = userLoginMapper.deletePhone(map);
		
		if (result == 1) {
			
			map.put("CI", "");
			userLoginMapper.updatePhoneHistory(map);
			
			resultMap.put("result", 1);
			return resultMap;
		} else {
			resultMap.put("msg", "일치하는 회원 정보가 없습니다.");
		}
		
		resultMap.put("result", 0);
		return resultMap;
	}


	@Override
	public Map<String, Object> processLoginPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String loginId1 = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String loginPass1 = request.getParameter("userPw") == null ? "" : new String(Base64.getDecoder().decode(request.getParameter("userPw")));
		
		//String birthday = request.getParameter("birthday");
		String ci = (String)session.getAttribute("ci");
		session.setAttribute("ci", "");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("loginResult", 0);
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		//String loginId = param.getValue("loginId");
		//String loginPass = param.getValue("loginPass");
		
		String loginId = userLoginMapper.selectLoginIdFromCI(ci);
		
		if (loginId == null || "".equals(loginId)) {
			//return null;
			map.put("msg", "로그인 하단의 인증서관리 메뉴에서 휴대폰 인증을 등록하여 주시기 바랍니다.");
			return map;
		}

		Map<String, Object> userStatusMap = selectUserStatus(loginId);
		if (userStatusMap != null) {
			return userStatusMap;
		}
		
		//ScpDb scpDb = new ScpDb();
		
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		
		userDetailsVO.setId(loginId);
		userDetailsVO.setCertificate("Y");
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		String userId = "";
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		
		UserDetailsVO userDetailsVO1 = new UserDetailsVO();
		userDetailsVO1.setId(loginId1);
		//String strEnc = scpDb.scpHashB64(loginPass1);
		String strEnc = loginPass1;
		userDetailsVO1.setPass(strEnc);
	
		userDetailsVO1 = userLoginMapper.userLogin(userDetailsVO1);
	
		if (!(userDetailsVO1 != null && userDetailsVO1.getId() != null &&
			userDetailsVO != null && userDetailsVO.getId() != null &&
			userDetailsVO1.getId().equals(userDetailsVO.getId()))) {
			
			loginFailLog(userDetailsVO, request, loginId);
			
			map.put("msg", "로그인에 실패하였습니다.");
			return map;
		}
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {
			
			map = processLogin(loginId, map, request, response, userDetailsVO, "loginPhone");
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");

			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
	}

	
	


	@Override
	public Map<String, Object> processRegistSimple(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String loginId = request.getParameter("id");
		String birthday = request.getParameter("birthday");
		String ci = request.getParameter("ci");
		session.setAttribute("ci", "");
		
		Map<String, String> map = new HashMap<>();
		map.put("LOGIN_ID", loginId);
		map.put("BIRTHDAY", birthday);
		map.put("CI", ci);
		
		int count = reqUserJoinMapper.selectCiSimpleCount2(map);
		
		log.info("#### existing phone count : " + count);
		
		if (count > 0) {
			resultMap.put("msg", "이미 등록된 간편 인증입니다.");
			resultMap.put("result", 0);
			return resultMap;
		}
		
		int result = userLoginMapper.registSimple(map);
		
		if (result == 1) {
			
			userLoginMapper.updateSimpleHistory(map);
			
			resultMap.put("result", 1);
			return resultMap;
		} else {
			resultMap.put("msg", "일치하는 회원 정보가 없습니다.");
		}
		
		resultMap.put("result", 0);
		
		return resultMap;
	}


	@Override
	public Map<String, Object> deleteSimple(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String loginId = request.getParameter("id");
		String birthday = request.getParameter("birthday");
		String ci = request.getParameter("ci");
		session.setAttribute("ci", "");
			
		Map<String, String> map = new HashMap<>();
		map.put("LOGIN_ID", loginId);
		map.put("BIRTHDAY", birthday);
		map.put("CI", ci);
		
		int count = reqUserJoinMapper.selectCiSimpleCount(ci);
		
		log.debug("#### existing phone count : " + count);
		
		if (count == 0) {
			resultMap.put("msg", "등록된 휴대폰 인증이 없습니다.");
			resultMap.put("result", 0);
			return resultMap;
		}
		
		int result = userLoginMapper.deleteSimple(map);
		
		if (result == 1) {
			
			map.put("CI", "");
			userLoginMapper.updateSimpleHistory(map);
			
			resultMap.put("result", 1);
			return resultMap;
		} else {
			resultMap.put("msg", "일치하는 회원 정보가 없습니다.");
		}
		
		resultMap.put("result", 0);
		return resultMap;
	}


	@Override
	public Map<String, Object> processLoginSimple(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String loginId1 = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String loginPass1 = request.getParameter("userPw") == null ? "" : new String(Base64.getDecoder().decode(request.getParameter("userPw")));
		
		//String birthday = request.getParameter("birthday");
		String ci = request.getParameter("ci");
		//session.setAttribute("ci", "");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("loginResult", 0);
		
		//SessionCookieConfig scc = request.getServletContext().getSessionCookieConfig();
		//scc.setPath("/");
		
		//String loginId = param.getValue("loginId");
		//String loginPass = param.getValue("loginPass");
		
		String loginId = userLoginMapper.selectLoginIdFromCISimple(ci);
		
		log.info("#### loginId : " + loginId);
		
		if (loginId == null || "".equals(loginId)) {
			//return null;
			map.put("msg", "로그인 하단의 인증서관리 메뉴에서 간편 인증을 등록하여 주시기 바랍니다.");
			return map;
		}

		Map<String, Object> userStatusMap = selectUserStatus(loginId);
		if (userStatusMap != null) {
			return userStatusMap;
		}
		
		//ScpDb scpDb = new ScpDb();
		
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		
		userDetailsVO.setId(loginId);
		userDetailsVO.setCertificate("Y");
		
		userDetailsVO = userLoginMapper.userLogin(userDetailsVO);
		
		String userId = "";
		//String server = System.getProperty("SERVER");
		
		//if ("grybwas11".equals(server) || "grybwas21".equals(server) || "rybwas11".equals(server) || "rybwas21".equals(server)) {
			//userId = SessionConfig.getSessionIdCheck("loginVO", userDetailsVO == null ? "" : userDetailsVO.getId());
		//}
		
		UserDetailsVO userDetailsVO1 = new UserDetailsVO();
		userDetailsVO1.setId(loginId1);
		//String strEnc = scpDb.scpHashB64(loginPass1);
		String strEnc = loginPass1;
		userDetailsVO1.setPass(strEnc);
	
		//log.info("#### loginId1 : " + loginId1 + ", loginPass1 : " + loginPass1);
		
		userDetailsVO1 = userLoginMapper.userLogin(userDetailsVO1);
	
		if (!(userDetailsVO1 != null && userDetailsVO1.getId() != null &&
			userDetailsVO != null && userDetailsVO.getId() != null &&
			userDetailsVO1.getId().equals(userDetailsVO.getId()))) {
			
			log.info("#### userLoginMapper.userLogin(userDetailsVO1) fail.");
			
			loginFailLog(userDetailsVO, request, loginId);
			
			map.put("msg", "로그인에 실패하였습니다.");
			return map;
		}
		
		if (userDetailsVO != null && userDetailsVO.getId() != null 
				&& !"".equals(userDetailsVO.getId())) {
			
			map = processLogin(loginId, map, request, response, userDetailsVO, "loginSimple");
			
		} else {
			
			// 로그인 실패
			map.put("loginResult", 0);
			
			log.info("#### loginId : " + loginId + " : " + userId + " : " + "fail");

			loginFailLog(userDetailsVO, request, loginId);
		}
		
		return map;
	}

	// 세션 종료 메시지 가져오기
	@Override
	public String selectSessionExpireMessage(String ip) throws Exception {
		
		log.info("#### insertSession Select ExpireMessage : " + ip);
		
		String message = userLoginMapper.selectSessionExpireMessage(ip);
		
		log.info("#### insertSession message : " + message);
		
		// 메시지 표시는 일회성이기 때문에 가져오고 나서 바로 삭제함.
		if (message != null && !"".equals(message)) {
			
			userLoginMapper.deleteSessionExpireMessage(ip);
			
			log.info("#### insertSession delete 2 : " + ip);
		}
		
		return message;
	}

	
	private Map<String, Object> processLogin(String loginId, Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, UserDetailsVO userDetailsVO, String action) throws Exception {
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		// 사용자별 기관 권한 세션 적재
		userInstAuthService.storeSession(userDetailsVO);
	
		//if ("local".equals(profile) || "pre".equals(profile)) {
		if ("local".equals(profile)) {
			return processLoginLocal(loginId, map, request, response, userDetailsVO, action);
		} else {
			return processLoginServer(loginId, map, request, response, userDetailsVO, action);
		}
	}

	private Map<String, Object> processLoginLocal(String loginId, Map<String, Object> map, HttpServletRequest request, 
			HttpServletResponse response, UserDetailsVO userDetailsVO, String action) throws Exception {

		HttpSession session = request.getSession();
		
		//ScpDb scpDb = new ScpDb();
		
		if (systemEnvService.checkAdminIp(request)) {
			userDetailsVO.setManagerYn("Y");
		} else {
			userDetailsVO.setManagerYn("N");
		}
		
		//userDetailsVO.setUserName(scpDb.scpDecB64(userDetailsVO.getUserName()));
		//userDetailsVO.setEmail(scpDb.scpDecB64(userDetailsVO.getEmail()));
		//userDetailsVO.setMobile(scpDb.scpDecB64(userDetailsVO.getMobile()));
		
		userDetailsVO.setIp(IP.getClientIP(request));
		
		Map<String, String> sysEnvMap = personalInfoMapper.selectSystemEnv();
		
		String sesinTmoutStr = sysEnvMap == null ? null : String.valueOf(sysEnvMap.get("SESIN_TMOUT_HR") == null ? "30" : sysEnvMap.get("SESIN_TMOUT_HR"));
		
		//log.debug("#### SESIN_TMOUT_HR : " + sesinTmoutStr);
		
		Integer sesinTmout = sesinTmoutStr == null || "".equals(sesinTmoutStr) ? 30 : Integer.valueOf(sesinTmoutStr);
		
		Integer sessionTime = sesinTmout * 60; // sesinTmout 분 * 60 초
		
		// 로그인 성공
		//session.setMaxInactiveInterval(2 * 60 * 60);  // 2시간 * 60분 * 60초
		session.setMaxInactiveInterval(sessionTime);  // 2시간 * 60분 * 60초
		//session.setMaxInactiveInterval(2 * 60);
		//session.setAttribute("loginVO", userDetailsVO);
		//if (userDetailsVO.getLastLoginTime() != null && !"".equals(userDetailsVO.getLastLoginTime())) {
			//map.put("lastLoginTime", userDetailsVO.getLastLoginTime());
		//}
		//map.put("loginResult", 1);

		//String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		//if ("local".equals(profile) || "dev2".equals(profile) || "real1".equals(profile) || "real2".equals(profile)) {  // 로컬 및 개발서버
			
			userDetailsVO.setSessionId(session.getId());
		
			session.setAttribute("userId", userDetailsVO.getId());
			session.setAttribute("userName", userDetailsVO.getUserName());
		
			session.setAttribute("loginVO", userDetailsVO);
			
			map.put("loginResult", 2);
			
			
		/*
		} else { // 운영 서버
			
			Date nowDate = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(nowDate);
			// 5분 더하기
			cal.add(Calendar.MINUTE, 10);
			String dateTime = simpleDateFormat.format(cal.getTime());  
			
			log.debug("#### dateTime : " + dateTime);
			
			// 쿠키 및 세션 세팅
			Cookie cookie = new Cookie("currentTime", dateTime);
			//cookie.setDomain("localhost");
			cookie.setPath("/");
			// 5분간 저장
			cookie.setMaxAge(10 * 60 * 60);
			// 30초간 저장
			//cookie.setMaxAge(30 * 60);
			//cookie.setSecure(true);
			response.addCookie(cookie);
			
			session.setAttribute("IdPwLogin", dateTime);
			
			map.put("loginResult", 1);
		}
		*/
		
		userDetailsVO.setLgnScsYn("S");
		
		log.info("#### loginId : " + loginId + " : " + "success");
		
		userLoginMapper.loginLog(userDetailsVO);

		userLoginMapper.resetLoginErrorCount(loginId);
		
		return map;
	}

	private Map<String, Object> processLoginServer(String loginId, Map<String, Object> map, 
			HttpServletRequest request, HttpServletResponse response, UserDetailsVO userDetailsVO, String action) throws Exception {
		
		String ip = IP.getClientIP(request);
		
		// 118.38.129.115 : 부산
		// 211.217.107.152 : 변호사 회관 12층 사무실
		// 10.33.2.171 : 반재정 매니저 업무 PC
		
		// 상담사 (U11) VPN 으로만 접근토록 함. 3 : 종사자
		if ("U11".equals(userDetailsVO.getUntTaskwkSeCd()) && "3".equals(userDetailsVO.getEnfsnRoleSeCd())) {
			if (!"118.38.129.115".equals(ip) && !"211.104.243.46".equals(ip) && !"211.217.107.152".equals(ip) && !"10.33.2.171".equals(ip)) {
				map.put("msg", "상담사는 VPN 으로만 접속이 가능합니다.");
				return map;
			}
		}
		
		
		HttpSession session = request.getSession();
		
		//ScpDb scpDb = new ScpDb();
		
		if (systemEnvService.checkAdminIp(request)) {
			userDetailsVO.setManagerYn("Y");
		} else {
			userDetailsVO.setManagerYn("N");
		}
		
		//userDetailsVO.setUserName(scpDb.scpDecB64(userDetailsVO.getUserName()));
		//userDetailsVO.setEmail(scpDb.scpDecB64(userDetailsVO.getEmail()));
		//userDetailsVO.setMobile(scpDb.scpDecB64(userDetailsVO.getMobile()));
		
		
		userDetailsVO.setIp(ip);

		Map<String, String> sysEnvMap = personalInfoMapper.selectSystemEnv();
		
		String sesinTmoutStr = sysEnvMap == null ? null : String.valueOf(sysEnvMap.get("SESIN_TMOUT_HR") == null ? "30" : sysEnvMap.get("SESIN_TMOUT_HR"));
		
		//log.debug("#### SESIN_TMOUT_HR : " + sesinTmoutStr);
		
		Integer sesinTmout = sesinTmoutStr == null || "".equals(sesinTmoutStr) ? 30 : Integer.valueOf(sesinTmoutStr);
		
		Integer sessionTime = sesinTmout * 60; // sesinTmout 분 * 60 초
		
		// 로그인 성공
		//session.setMaxInactiveInterval(2 * 60 * 60);  // 2시간 * 60분 * 60초
		session.setMaxInactiveInterval(sessionTime);  // 2시간 * 60분 * 60초
		//session.setMaxInactiveInterval(2 * 60);
		//session.setAttribute("loginVO", userDetailsVO);
		//if (userDetailsVO.getLastLoginTime() != null && !"".equals(userDetailsVO.getLastLoginTime())) {
			//map.put("lastLoginTime", userDetailsVO.getLastLoginTime());
		//}
		//map.put("loginResult", 1);
		
		session.setAttribute("sessionTime", sessionTime);
		
		//String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		//boolean checkDeveloper = systemEnvService.checkDeveloperIp(request);

		//if ("local".equals(profile) || "dev2".equals(profile) || checkDeveloper) {  // 로컬 및 개발서버
			
			log.info("#### session 1 : " + session.getId());
			
			
			userDetailsVO.setSessionId(session.getId());
			
			session.setAttribute("userId", userDetailsVO.getId());
			session.setAttribute("userName", userDetailsVO.getUserName());
			//session.setAttribute("loginVO", userDetailsVO);
			
			map.put("loginResult", 2);
			
			// 로그인 아이디 중복 될 경우 기존의 아이디 세션을 종료시키고 로그인 하기
			Integer forceLogin = (Integer)session.getAttribute("forceLogin");
			
			log.debug("#### forceLogin : " + forceLogin);
			
			int sessionCount = redisService.selectRedisLikeSessionCount("LOGIN||SESSION||" + userDetailsVO.getId() + "||*");
			
			log.debug("#### sessionCount : " + sessionCount);
			
			if (forceLogin == null || forceLogin != 1) {
				
				if (sessionCount > 0) {
					map.put("loginResult", 1);
					map.put("sessionCount", sessionCount);
					session.setAttribute("previousSessionExists", 1);
					return map;
				}
				
			} else {
				// 로그인 아이디 중복 될 경우 기존의 아이디 세션을 종료시키고 로그인 하기
				session.setAttribute("forceLogin", 0);
			}
			
			if (sessionCount > 0) {
				
				List<UserDetailsVO> prevLoginVOList = redisService.selectPrevLoginVOList("LOGIN||SESSION||" + userDetailsVO.getId() + "||*");
				
				if (prevLoginVOList != null && prevLoginVOList.size() > 0) {
					
					for (int n=0; n < prevLoginVOList.size(); n++) {
						
						UserDetailsVO prevLoginVO = prevLoginVOList.get(n);
						
						if (prevLoginVO != null) {
							
							Map<String, String> messageMap = new HashMap<>();
							
							messageMap.put("CNTN_IP_ADDR", prevLoginVO.getIp());
							messageMap.put("LGN_ID", userDetailsVO.getId());
							messageMap.put("SESIN_END_MSSAGE_CN", ip + " 아이피에서 같은 아이디로 로그인하여 세션이 종료되었습니다.");
							messageMap.put("FRST_RGTR_ID", userDetailsVO.getId());
							
							log.info("#### insertSessionExpireMessage : " + "CNTN_IP_ADDR : " + prevLoginVO.getIp() + ", SESIN_END_MSSAGE_CN : " + ip);

							int cnt1 = userLoginMapper.insertSessionExpireMessage(messageMap);
							
							log.info("#### insertSession cnt1 : " + cnt1);
							
						}
					}
				}
			}
			
			redisService.deleteRedisLikeSession("LOGIN||SESSION||" + userDetailsVO.getId() + "||*");
			
			// redis 세션 저장
			String redisKey = "LOGIN||SESSION||" + userDetailsVO.getId() + "||" + session.getId();
			redisService.insertRedisMap(redisKey, userDetailsVO.getMap());
			redisService.setTimeOutSecond(redisKey, sessionTime);
			
			//List<Map<String, Object>> redisList = redisService.selectRedisListLike("LOGIN||SESSION||" + userDetailsVO.getId() + "||*");
			
			//if (redisList != null && redisList.size() > 0) {
				//for (int ri = 0; ri < redisList.size(); ri++) {
					//Map<String, Object> redisMap = redisList.get(ri);
					//log.debug("#### redis id : " + redisMap.get("id"));
				//}
			//}
			
			// 로그인 성공일 때 세션 종료 메시지 삭제
			//userLoginMapper.deleteSessionExpireMessage(ip);
			
			log.info("#### insertSession delete 1 : " + ip);
			
			log.info("#### session 2 : " + session.getId());

		/*
		} else { // 운영 서버
			
			Date nowDate = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(nowDate);
			// 5분 더하기
			cal.add(Calendar.MINUTE, 10);
			String dateTime = simpleDateFormat.format(cal.getTime());  
			
			log.debug("#### dateTime : " + dateTime);
			
			// 쿠키 및 세션 세팅
			Cookie cookie = new Cookie("currentTime", dateTime);
			//cookie.setDomain("localhost");
			cookie.setPath("/");
			// 5분간 저장
			cookie.setMaxAge(10 * 60 * 60);
			// 30초간 저장
			//cookie.setMaxAge(30 * 60);
			//cookie.setSecure(true);
			response.addCookie(cookie);
			
			session.setAttribute("IdPwLogin", dateTime);
			
			map.put("loginResult", 1);
		}
		*/
		
		userDetailsVO.setLgnScsYn("S");
		
		log.info("#### loginId : " + loginId + " : " + "success");

		Integer processedUserLogin2 = (Integer)session.getAttribute("processedUserLogin2");
		
		if (processedUserLogin2 == null || processedUserLogin2 != 1) {
			userLoginMapper.loginLog(userDetailsVO);
		}
		
		userLoginMapper.resetLoginErrorCount(loginId);
		
		return map;
	}
	
	
	@Override
	public UserDetailsVO getLoginSessionVO(HttpServletRequest request) throws Exception {

		UserDetailsVO vo = null;

		HttpSession session = request.getSession();
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

		//if ("local".equals(profile) || "pre".equals(profile)) {
		if ("local".equals(profile)) {
			vo = (UserDetailsVO)session.getAttribute("loginVO");
			
		} else {
			vo = redisService.selectRedisSession("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		}
		
		return vo;
	}


	// 개인정보 처리방침 재동의 구하기
	public String selectPrivacySchedule(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		// 개인정보 처리방침 재동의 주기 (일 수)
		int privacyReConsentCycle = 90;
		List<Map<String, Object>> configList = mgmtCmmnConfigService.selectConfigList("SYSTEM_ENV");
		if (configList != null && configList.size() > 0) {
			Map<String, Object> configMap = configList.get(0);
			if (configMap != null && configMap.get("STNG_PARA_VALUE6") != null && !"".equals(configMap.get("STNG_PARA_VALUE6"))) {
				privacyReConsentCycle = Integer.parseInt((String)configMap.get("STNG_PARA_VALUE6"));
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("USER_ID", userId);
		map.put("PRIVACY_RE_CONSENT_CYCLE", privacyReConsentCycle);
		
		String result = userLoginMapper.selectPrivacySchedule(map);
		
		return result;
	}
	
	// 비밀번호 변경 주기에 비밀번호 변경하기
	public String selectChangePassword(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		// 비밀번호 변경 주기 (일 수)
		int passwordChangeCycle = 90;
		List<Map<String, Object>> configList = mgmtCmmnConfigService.selectConfigList("SYSTEM_ENV");
		if (configList != null && configList.size() > 0) {
			Map<String, Object> configMap = configList.get(0);
			if (configMap != null && configMap.get("STNG_PARA_VALUE5") != null && !"".equals(configMap.get("STNG_PARA_VALUE5"))) {
				passwordChangeCycle = Integer.parseInt((String)configMap.get("STNG_PARA_VALUE5"));
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("USER_ID", userId);
		map.put("PASSWORD_CHANGE_CYCLE", passwordChangeCycle);
		
		int count = userLoginMapper.selectChangePassword(map);
		
		return count > 0 ? "Y" : "N";
	}

	private void loginFailLog(UserDetailsVO userDetailsVO, HttpServletRequest request, String loginId) throws Exception {
		
		if (userDetailsVO == null) {
			userDetailsVO = new UserDetailsVO();
		}
		
		userDetailsVO.setIp(IP.getClientIP(request));
		
		userDetailsVO.setId(loginId);
		
		userDetailsVO.setLgnScsYn("F");

		if (systemEnvService.checkAdminIp(request)) {
			userDetailsVO.setManagerYn("Y");
		} else {
			userDetailsVO.setManagerYn("N");
		}
		
		userLoginMapper.loginLog(userDetailsVO);
		
		userLoginMapper.increaseLoginErrorCount(loginId);

	}


	// 본인의 등록된 2차 인증 목록(수단) 구하기
	public Map<String, String> selectSecondAuthList(HttpServletRequest request) throws Exception {
		
		String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		
		Map<String, String> secondAuthMap = userLoginMapper.selectSecondAuthList(loginId);
		
		return secondAuthMap;
	}

	// 아이디, 비밀번호 확인
	public Map<String, Object> selectIdPwCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String loginPass = request.getParameter("userPw") == null ? "" : new String(Base64.getDecoder().decode(request.getParameter("userPw")));
		
		int flag = 0;
		String message = "";
		
		Map<String, Object> map = new HashMap<>();

		Map<String, String> paramMap = new HashMap<>();

		//ScpDb scpDb = new ScpDb();
		//String strEnc = scpDb.scpHashB64(loginPass);
		String strEnc = loginPass;
			
		paramMap.put("loginId", loginId);
		paramMap.put("loginPass", strEnc);
		
		boolean govFlag = true;
		
		if (!"".equals(loginId) && !"".equals(loginPass)) {
	
			Map<String, Object> resultMap = userLoginMapper.selectIdPwCheck(paramMap);
			
			Map<String, Object> resultMap2 = userLoginMapper.selectIdPwCheck2(paramMap);

			Integer errorPermitCount = userLoginMapper.selectLoginErrorPermitCount();
			if (errorPermitCount == null) {
				errorPermitCount = 5;
			}

			Integer errorCount = userLoginMapper.selectLoginErrorCount(loginId);
			if (errorCount == null) {
				errorCount = 0;
			}
			
			if (resultMap2 != null && "Y".equals(resultMap2.get("USER_CNTN_INTRCP_YN"))) {
			
				flag = 7;
			
			} else {
				
				if (errorCount > errorPermitCount) {
					
					flag = 0;  // 일치하는 회원 정보가 없음.
					
					message = messageSource.getMessage("errors.loginErrorExceed", 
							new String[]{}, 
							Locale.KOREAN);
					
				} else if (resultMap == null) {
					
					flag = 0;  // 일치하는 회원 정보가 없음.
					
					userLoginMapper.increaseLoginErrorCount(loginId);
					
					errorCount++;
					
					if (errorCount != null && errorCount > errorPermitCount) {
						message = messageSource.getMessage("errors.loginErrorExceed", 
								new String[]{}, 
								Locale.KOREAN);
						
					} else if (errorCount != null && errorCount > 0) {
						message = messageSource.getMessage("errors.loginError2", 
							new String[]{String.valueOf(errorCount), String.valueOf(errorPermitCount)}, 
							Locale.KOREAN);
	
					} else {
						message = messageSource.getMessage("errors.loginError1", 
							new String[]{}, 
							Locale.KOREAN);
					}
				
	
				} else if (!"2".equals(resultMap.get("USER_ID_USE_SE_CD"))) {
	
					// AND C.USER_ID_USE_SE_CD = '2' /* 승인 상태만 로그인 허용 */
					
					flag = Integer.parseInt((String)resultMap.get("USER_ID_USE_SE_CD"));
					
					//신청 : 1
					//승인 : 2
					//반려 : 3
					//사용중지 : 4
					//삭제 : 5
					//회원탈퇴 : 6
					
				} else if (resultMap != null && "Y".equals(resultMap.get("DEL_YN"))) {
					flag = 0;  // 일치하는 회원 정보가 없음.
					
				}else if ("2".equals(resultMap.get("USER_ID_USE_SE_CD"))) {
					flag = 2;  // 정상인 회원 가입 승인 상태로 간주함.
				}
			}
		}
		
		if (flag == 2) {
			
			List<Map<String, Object>> resultList = userLoginMapper.selectGovLoginCheck(paramMap);
			
			/*
			gov 로그인 승인 조건
			1. 종사자의 주기관 : 1000000948 (여성가족부)
			2. 종사자의 주기관 : 1000000949 (한국청소년상담복지개발원)
			3. 종사자의 주기관 : 단위업무구분코드가 U07, U08, U09, U10, U11, 추가 U02
			*/

			String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
			
			if ("real1".equals(profile) || "real2".equals(profile)) {
				
				if ("211.217.107.152".equals(IP.getClientIP(request))) {  // 사무실 아이피는 제한 없음.
					
					govFlag = true;
					
				} else {
					
					govFlag = false;
					
					if (resultList == null || resultList.size() == 0) {
						
						//flag = 8;  // gov 로그인 승인 조건에 맞지 않음
					
					} else {
						
						LocalDate now = LocalDate.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
						Integer formatedNow = Integer.parseInt(now.format(formatter));
						
						log.info("#### formatedNow : " + formatedNow);
						
						Integer date1 = 20230601;
						Integer date2 = 20230605;
						
						for (int i=0; i < resultList.size(); i++) {
							
							Integer instNo = Integer.parseInt(String.valueOf(resultList.get(i).get("INST_NO")));
							String untTaskwkSeCd = (String)resultList.get(i).get("UNT_TASKWK_SE_CD");
						
							if (formatedNow < date1) {
								
								log.info("#### formatedNow 1.");
								
								if (1000000948 == instNo || 1000000949 == instNo
									|| "U02".equals(untTaskwkSeCd) || "U07".equals(untTaskwkSeCd) || "U08".equals(untTaskwkSeCd) 
									|| "U09".equals(untTaskwkSeCd) || "U10".equals(untTaskwkSeCd) || "U11".equals(untTaskwkSeCd)
								) {
									govFlag = true;
									break;
								}
								
							} else if (formatedNow >= date1 && formatedNow < date2) {
								
								log.info("#### formatedNow 2.");
								
								if (1000000948 == instNo || 1000000949 == instNo
									|| "U02".equals(untTaskwkSeCd) || "U07".equals(untTaskwkSeCd) || "U08".equals(untTaskwkSeCd) 
									|| "U09".equals(untTaskwkSeCd) || "U10".equals(untTaskwkSeCd) || "U11".equals(untTaskwkSeCd)
									
									|| "U01".equals(untTaskwkSeCd) || "U04".equals(untTaskwkSeCd) || "U05".equals(untTaskwkSeCd)
									|| "U06".equals(untTaskwkSeCd) 
								) {
									govFlag = true;
									break;
								}
									
							} else {
								
								log.info("#### formatedNow 3.");
								
								govFlag = true;
								break;
							}
						}
						
						//if (!govFlag) {
							//flag = 8;  // gov 로그인 승인 조건에 맞지 않음
						//}
					}
				}
			}
		}

		// 쿠키 및 세션 세팅
		Cookie cookie = new Cookie("govFlag", govFlag ? "1" : "0");
		//cookie.setDomain("localhost");
		cookie.setPath("/");
		// 48 시간 동안 저장
		cookie.setMaxAge(48 * 60 * 60 * 60);
		// 30초간 저장
		//cookie.setMaxAge(30 * 60);
		//cookie.setSecure(true);
		response.addCookie(cookie);
				
		map.put("result", flag);
		map.put("message", message);
		return map;
	}

	@Override
	public Map<String, Object> selectUserStatus(String loginId) throws Exception {

		Map<String, Object> map = new HashMap<>();
		
		Map<String, String> userMap = userLoginMapper.selectUserLoginStatus(loginId);
		String userIdUseSeCd = userMap == null ? "" : userMap.get("USER_ID_USE_SE_CD") == null ? "" : userMap.get("USER_ID_USE_SE_CD");
		String userCntnIntrcpYn = userMap == null ? "" : userMap.get("USER_CNTN_INTRCP_YN") == null ? "" : userMap.get("USER_CNTN_INTRCP_YN");

		if ("Y".equals(userCntnIntrcpYn)) {  // 접속 차단
			//map.put("msg", messageService.getMessageBundle("errors.loginInterrupted", Locale.KOREAN).getValue());
			
			Map<String, String> disconnectMap = disconnectUserService.selectDisconnectUserInfo(loginId);
			
			String message = messageSource.getMessage("errors.loginInterrupted", 
					new String[]{disconnectMap.get("ADDTNG_MNG_VALUE1")}, 
					Locale.KOREAN);
			
			map.put("msg", StringEscapeUtils.unescapeJava(message));
			
			return map;
			//throw new UserException("errors.loginInterrupted");
		}

		Integer errorCount = userLoginMapper.selectLoginErrorCount(loginId);
		if (errorCount == null) {
			errorCount = 0;
		}
		Integer errorPermitCount = userLoginMapper.selectLoginErrorPermitCount();
		if (errorPermitCount == null) {
			errorPermitCount = 5;
		}
		if (errorCount > errorPermitCount) {
			map.put("msg", messageService.getMessageBundle("errors.loginErrorExceed", Locale.KOREAN).getValue());
			
			//map.put("msg", messageSource.getMessage("errors.loginErrorExceed", new String[]{"111"}, Locale.KOREAN));
			
			return map;
			//throw new UserException("errors.loginErrorExceed");
		}
		
		switch (userIdUseSeCd) {
		case "1" :  // 신청
			map.put("msg", messageService.getMessageBundle("errors.loginApply", Locale.KOREAN).getValue());
			return map;
			//throw new UserException("errors.loginApply");
		case "3" :  // 반려
			map.put("msg", messageService.getMessageBundle("errors.loginGiveBack", Locale.KOREAN).getValue());
			return map;
			//throw new UserException("errors.loginGiveBack");
		case "4" :  // 사용중지
			map.put("msg", messageService.getMessageBundle("errors.loginStop", Locale.KOREAN).getValue());
			return map;
			//throw new UserException("errors.loginStop");
		case "5" :  // 삭제
			map.put("msg", messageService.getMessageBundle("errors.loginDelete", Locale.KOREAN).getValue());
			return map;
			//throw new UserException("errors.loginDelete");
		case "6" :  // 회원탈퇴
			map.put("msg", messageService.getMessageBundle("errors.loginWithdraw", Locale.KOREAN).getValue());
			return map;
			//throw new UserException("errors.loginWithdraw");
		case "2" :  // 승인
		default :
			break;
		}
		
		return null;
	}
	
	// 패스워드 일치여부 확인
	public boolean selectPasswordEquals(String pass1, String pass2) throws Exception {
		
		Map<String, String> map = new HashMap<>();
		map.put("pass1", pass1);
		map.put("pass2", pass2);
		
		int count = userLoginMapper.selectPasswordEquals(map);
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
		
}
