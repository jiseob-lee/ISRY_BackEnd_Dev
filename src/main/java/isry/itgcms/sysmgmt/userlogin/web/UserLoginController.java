/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.web;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.dyncBrd.service.DyncNtcBrdService;
import isry.itgcms.sysmgmt.systemenv.service.SystemEnvService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.IP;

/**
 * @파일명        : UserLoginController.java
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
@Controller
//@Api(value = "UserLogin web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userlogin")
public class UserLoginController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	@Autowired
	private DyncNtcBrdService dyncNtcBrdService;

	//@Autowired
	//private RedisService3 redisService;

	@Resource(name = "systemEnvService")
	private SystemEnvService systemEnvService;
	
	// 로그인 공지사항 게시판 번호
	private final String loginNoticeBoardNo = EgovProperties.getProperty("globals", "isry.loginNoticeBoardNo");
	
	
	//@ApiOperation(value = "/userLogin.do", notes = "주소검색 [공통] 이지섭")
	@RequestMapping(value="/userLogin_backup.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userLogin_backup(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest, HttpSession session) throws Exception {
		
		//dataRequest.setResponse("dmLoginResult", userLoginService.userLogin(dataRequest, request));
		Map<String, Object> result = userLoginService.processUserLogin(request, response);
		
		//return new JSONDataView();
		return result;
	}
	
	@RequestMapping(value="/userLogin.do", method=RequestMethod.POST)
	public View userLogin(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		Map<String, Object> message = new HashMap<String, Object>();
		Map<String, Object> result = userLoginService.processUserLogin(dataRequest, request, response);
		
		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {  // 로그인 실패
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명   : userLogin2
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 : eXBuilder 6 를 사용하지 않는 로그인
	 */
	@RequestMapping(value="/userLogin2.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userLogin2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> message = new HashMap<String, Object>();
		
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

		

		Map<String, Object> result = userLoginService.processUserLogin2(request, response);
		
		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		Integer sessionCount = result == null ? null : (Integer)result.get("sessionCount");
		
		log.debug("#### loginResult : " + loginResult);
		
		
		
		boolean checkDeveloper = systemEnvService.checkDeveloperIp(request);

		if (!("local".equals(profile) || "dev2".equals(profile) || checkDeveloper)) {  // 로컬 및 개발서버
		//if (!checkDeveloper) {  // 로컬 및 개발서버
		//if (1 == 1 + 0) {
			
			Map<String, String> secondAuthMap = userLoginService.selectSecondAuthList(request);
			
			String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
			
			Map<String, Object> userStatusMap = null;
			
			if (!"".equals(loginId)) {
				userStatusMap = userLoginService.selectUserStatus(loginId);
			}
			
			if (userStatusMap != null) {
				msg = String.valueOf(userStatusMap.get("msg"));
				
			} else if (loginResult != 1 && loginResult != 2) {
				msg = "아이디와 패스워드를 확인해주시기 바랍니다.";
				
			} else {
				msg = "2차 인증을 진행해주시기 바랍니다.,";
			
				if (secondAuthMap == null) {
					msg += "NNNN";
					
				} else {
					msg += secondAuthMap.get("OFCERT_ME_CERT_DN_YN") 
						+ secondAuthMap.get("FICE_ME_CERT_DN_YN") 
						+ secondAuthMap.get("MOPH_ME_CERT_DN_YN") 
						+ secondAuthMap.get("SNS_SIMPC_CERT_DN_YN");
				}
			}
		
			message.put("msg", msg);
			
			return message;
		}
		
		
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			
			if (sessionCount != null && sessionCount > 0) {
				message.put("sessionCount", sessionCount);
			}
			
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		//dataRequest.setMetadata(true, message);
		
		//return new JSONDataView();
		return message;
	}

	// 로그인 아이디 중복 될 경우 기존의 아이디 세션을 종료시키고 로그인 하기
	@RequestMapping(value="/userLogin4.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userLogin4(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		Integer previousSessionExists = (Integer)session.getAttribute("previousSessionExists");
		
		if (previousSessionExists == null || previousSessionExists == 0) {
			// 앞단에서 메시지 확인 처리가 안된 경우에는 널 리턴
			return null;
		} else {
			session.setAttribute("forceLogin", 1);
		}
		
		Map<String, Object> message = new HashMap<String, Object>();
		Map<String, Object> result = userLoginService.processUserLogin2(request, response);
		
		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		//dataRequest.setMetadata(true, message);
		
		//return new JSONDataView();
		return message;
	}

	
	@RequestMapping(value="/userLogout.do", method=RequestMethod.POST)
	public View userLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		userLoginService.processLogoutLog(request);
		
		HttpSession session = request.getSession();
		
		//redisService.deleteRedisLikeSession("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		
		try {
			session.invalidate();
		} catch (IllegalStateException e) {
			log.debug(e.getMessage());
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value="/changePassword.do", method=RequestMethod.POST)
	public View changePassword(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String msg = userLoginService.savePassword(request, dataRequest);
		
		if (msg != null || !"".equals(msg)) {
			Map<String, String> map = new HashMap<>();
			map.put("msg", msg);
			dataRequest.setResponse("dmResult", map);
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value="/settingPassword.do", method=RequestMethod.POST)
	public View settingPassword(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String msg = userLoginService.savePasswordNew(request, response, dataRequest);
		
		if (msg != null || !"".equals(msg)) {
			Map<String, String> map = new HashMap<>();
			map.put("msg", msg);
			dataRequest.setResponse("dmResult", map);
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value="/registCertificate.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actCertificate"))) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		session.setAttribute("actCertificate", "processed");
		
		Map<String, Object> result = userLoginService.processRegistCertificate(request);
		
		return result;
	}


	@RequestMapping(value="/registFinanceCertificate.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registFinanceCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actCertificate"))) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		session.setAttribute("actCertificate", "processed");
		
		Map<String, Object> result = userLoginService.processRegistFinanceCertificate(request);
		
		return result;
	}


	@RequestMapping(value="/deleteCertificate.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actCertificate"))) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		session.setAttribute("actCertificate", "processed");
		
		Map<String, Object> result = userLoginService.deleteCertificate(request);
		
		return result;
	}


	@RequestMapping(value="/deleteFinanceCertificate.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFinanceCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actCertificate"))) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		session.setAttribute("actCertificate", "processed");
		
		Map<String, Object> result = userLoginService.deleteFinanceCertificate(request);
		
		return result;
	}
	
	

	@RequestMapping(value="/loginCertificate.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		String dateTime = simpleDateFormat.format(nowDate);
		
		String idPwLogin = (String)session.getAttribute("IdPwLogin");
		
		if (idPwLogin == null || "".equals(idPwLogin) || Long.parseLong(idPwLogin) < Long.parseLong(dateTime)) {
			//message.put("msg", "아이디/패스워드 로그인을 먼저 실행해주시기 바랍니다.");
			//return message;
		}
		
		if (!"processing".equals((String)session.getAttribute("actCertificate"))) {
			message.put("msg", "로그인에 실패하였습니다.");
			return message;
		}
		
		session.setAttribute("actCertificate", "processed");
		
		Map<String, Object> result = userLoginService.processLoginCertificate(request, response);
		
		//Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = result == null ? "" : (String)result.get("lastLoginTime");
		//String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		//log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		//if (loginResult == 1) {
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}
		//} else {
			//message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		//}
		
		
		

		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		Integer sessionCount = result == null ? null : (Integer)result.get("sessionCount");
		
		log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			
			if (sessionCount != null && sessionCount > 0) {
				message.put("sessionCount", sessionCount);
			}
			
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		//dataRequest.setMetadata(true, message);
		
		//return new JSONDataView();
		
		return message;
	}


	@RequestMapping(value="/loginFinanceCertificate.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginFinanceCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		String dateTime = simpleDateFormat.format(nowDate);
		
		String idPwLogin = (String)session.getAttribute("IdPwLogin");
		
		if (idPwLogin == null || "".equals(idPwLogin) || Long.parseLong(idPwLogin) < Long.parseLong(dateTime)) {
			//message.put("msg", "아이디/패스워드 로그인을 먼저 실행해주시기 바랍니다.");
			//return message;
		}
		
		if (!"processing".equals((String)session.getAttribute("actCertificate"))) {
			message.put("msg", "로그인에 실패하였습니다.");
			return message;
		}
		
		session.setAttribute("actCertificate", "processed");
		
		Map<String, Object> result = userLoginService.processLoginFinanceCertificate(request, response);
		
		//Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = result == null ? "" : (String)result.get("lastLoginTime");
		//String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		//log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		//if (loginResult == 1) {
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}
		//} else {
			//message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		//}
		
		//dataRequest.setMetadata(true, message);
		
		//return new JSONDataView();
		
		

		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		Integer sessionCount = result == null ? null : (Integer)result.get("sessionCount");
		
		log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			
			if (sessionCount != null && sessionCount > 0) {
				message.put("sessionCount", sessionCount);
			}
			
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		return message;
	}

	
	@RequestMapping("/findId.do")
	public View findId(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		/*
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		String deployPath = pathList.get(0);
		String pageUrl  = deployPath + "/";
		pageUrl += "app/itgcms/sysmgmt/05_user/FindID";
		return new UIView(pageUrl);
		*/
		
		Map<String, String> msgMap = userLoginService.processFindId(request, dataRequest);
		
		dataRequest.setResponse("dmMsg", msgMap);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/findId2.do")
	@ResponseBody
	public Map<String, String> findId2(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		
		Map<String, String> msgMap = userLoginService.processFindId2(request, dataRequest);
		
		return msgMap;
	}


	@RequestMapping("/findPw.do")
	public View findPw(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		/*
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		String deployPath = pathList.get(0);
		String pageUrl  = deployPath + "/";
		pageUrl += "app/itgcms/sysmgmt/05_user/FindPassword";
		return new UIView(pageUrl);
		*/
		
		Map<String, String> msgMap = userLoginService.processFindPw(request, dataRequest);
		
		dataRequest.setResponse("dmMsg", msgMap);
		
		return new JSONDataView();
	}

	@RequestMapping("/findPw2.do")
	@ResponseBody
	public Map<String, String> findPw2(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		Map<String, String> msgMap = userLoginService.processFindPw2(request, dataRequest);
		
		return msgMap;
	}


	@RequestMapping("/joining.do")
	public View joining(HttpServletRequest request, HttpServletResponse response, 
			DataRequest reqData) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		String deployPath = pathList.get(0);
		String pageUrl  = deployPath + "/";
		pageUrl += "app/itgcms/sysmgmt/05_user/MemberJoin5";
		return new UIView(pageUrl);
	}

	@RequestMapping("/updatePasswordChangeDate.do")
	public View updatePasswordChangeDate(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {

		userLoginService.updatePasswordChangeDate(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping("/loginNotice.do")
	public View loginNotice(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String mainPageUrl = deployPath+"/";   //메인 페이지 URL
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
		mainPageUrl += "app/exam/demo/dyncBrd/dyncNtcBrd/dyncNtcBrdList.clx";

		Map<String, String> initParam = new HashMap<String, String>();

		initParam.put("strBoardId", loginNoticeBoardNo);  // 로그인 공지사항

		return new UIView(mainPageUrl, initParam); // UIView() 인자로 파라미터 전달
	}


	@RequestMapping("/loginNoticeList.do")
	@ResponseBody
	public List<Map<String, Object>> loginNoticeList(HttpServletRequest request, HttpServletResponse resp) throws Exception {
	
		//selectSampleBoardList

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("NTABRD_ESNTAL_NO", loginNoticeBoardNo);
		mapParam.put("FIRST_RECORD_INDEX", "0");
		mapParam.put("PAGE_ROW_COUNT", "5");
		
		// 게시판 목록 데이터 호출
		List<Map<String, Object>> listSampleBoard = dyncNtcBrdService.selectSampleBoardList(mapParam);
		
		return listSampleBoard;
	}

	@RequestMapping("/loginNoticeDetail.do")
	@ResponseBody
	public View loginNoticeDetail(HttpServletRequest request, HttpServletResponse resp) throws Exception {
	
		//selectDyncBrdCmnDtlList
		Map<String, String> mapParam = new HashMap<>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		mapParam.put("NTABRD_ESNTAL_NO", loginNoticeBoardNo);
		mapParam.put("BBSCTT_ESNTAL_NO", request.getParameter("BBSCTT_ESNTAL_NO"));
		//mapParam.put("CREATE_YN", param.getValue("strCreateYn"));
		
		// 게시판 기본 데이터 호출
		/*
		List<Map<String, Object>> dtlListDynamicCmntBoard = dyncBrdCmnService.selectDyncBrdCmnDtlList(mapParam);
		
		if (dtlListDynamicCmntBoard != null && dtlListDynamicCmntBoard.size() > 0) {
			return dtlListDynamicCmntBoard.get(0);
		} else {
			return new HashMap<>();
		}
		*/
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		String deployPath = pathList.get(0);
		String pageUrl  = deployPath + "/";
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
		pageUrl += "app/exam/demo/dyncBrd/dyncNtcBrd/dyncNtcBrdDtl.clx";

		return new UIView(pageUrl, mapParam);
	}
		

	
	
	@RequestMapping(value="/phoneRegist.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> phoneRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actPhone"))) {
			log.info("#### actPhone is not processing.");
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		log.info("#### actPhone is processing.");
		
		session.setAttribute("actPhone", "processed");
		
		Map<String, Object> result = userLoginService.processRegistPhone(request);
		
		return result;
	}


	@RequestMapping(value="/phoneDelete.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> phoneDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actPhone"))) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		session.setAttribute("actPhone", "processed");
		
		Map<String, Object> result = userLoginService.deletePhone(request);
		
		return result;
	}


	@RequestMapping(value="/phoneLogin.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> phoneLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		String dateTime = simpleDateFormat.format(nowDate);
		
		String idPwLogin = (String)session.getAttribute("IdPwLogin");
		
		if (idPwLogin == null || "".equals(idPwLogin) || Long.parseLong(idPwLogin) < Long.parseLong(dateTime)) {
			//message.put("msg", "아이디/패스워드 로그인을 먼저 실행해주시기 바랍니다.");
			//return message;
		}
		
		if (!"processing".equals((String)session.getAttribute("actPhone"))) {
			message.put("msg", "로그인에 실패하였습니다.");
			return message;
		}
		
		session.setAttribute("actPhone", "processed");
		
		Map<String, Object> result = userLoginService.processLoginPhone(request, response);
		
		//Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = result == null ? "" : (String)result.get("lastLoginTime");
		//String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		//log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		//if (loginResult == 1) {
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}
		//} else {
			//message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		//}
		
		//dataRequest.setMetadata(true, message);
		
		//return new JSONDataView();

		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		Integer sessionCount = result == null ? null : (Integer)result.get("sessionCount");
		
		log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			
			if (sessionCount != null && sessionCount > 0) {
				message.put("sessionCount", sessionCount);
			}
			
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		return message;
	}

	

	
	@RequestMapping(value="/simpleRegist.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> simpleRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actSimple"))) {
			log.info("#### actSimple is not processing.");
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		log.info("#### actSimple is processing.");
		
		session.setAttribute("actSimple", "processed");
		
		Map<String, Object> result = userLoginService.processRegistSimple(request);
		
		return result;
	}


	@RequestMapping(value="/simpleDelete.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> simpleDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (!"processing".equals((String)session.getAttribute("actSimple"))) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("result", 0);
			return resultMap;
		}
		
		session.setAttribute("actSimple", "processed");
		
		Map<String, Object> result = userLoginService.deleteSimple(request);
		
		return result;
	}


	@RequestMapping(value="/simpleLogin.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> simpleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		String dateTime = simpleDateFormat.format(nowDate);
		
		String idPwLogin = (String)session.getAttribute("IdPwLogin");
		
		if (idPwLogin == null || "".equals(idPwLogin) || Long.parseLong(idPwLogin) < Long.parseLong(dateTime)) {
			//message.put("msg", "아이디/패스워드 로그인을 먼저 실행해주시기 바랍니다.");
			//return message;
		}
		
		log.info("#### actSimple : " + (String)session.getAttribute("actSimple"));
		
		if (!"processing".equals((String)session.getAttribute("actSimple"))) {
			message.put("msg", "로그인에 실패하였습니다.");
			return message;
		}
		
		session.setAttribute("actSimple", "processed");
		
		Map<String, Object> result = userLoginService.processLoginSimple(request, response);
		
		//Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = result == null ? "" : (String)result.get("lastLoginTime");
		//String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		//log.debug("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		//if (loginResult == 1) {
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}
		//} else {
			//message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		//}
		
		//dataRequest.setMetadata(true, message);
		
		//return new JSONDataView();

		Integer loginResult = (result == null || result.get("loginResult") == null) ? 0 : (Integer)result.get("loginResult");
		//String lastLoginTime = (String)result.get("lastLoginTime");
		String msg = result == null || result.get("msg") == null ? null : (String)result.get("msg");
		
		Integer sessionCount = result == null ? null : (Integer)result.get("sessionCount");
		
		log.info("#### loginResult : " + loginResult);
		
		/* 로그인 성공시 */
		if (loginResult == 1) {  // 운영 서버
			
			if (sessionCount != null && sessionCount > 0) {
				message.put("sessionCount", sessionCount);
			}
			
			//message.put("uri", "app/com/main/main"); //로그인 AppId
			//if (lastLoginTime != null && !"".equals(lastLoginTime)) {
				//message.put("lastLoginTime", lastLoginTime);
			//}

		} else if (loginResult == 2) {  // 로컬 및 개발서버
			message.put("msg", "2");
			message.put("uri", "app/com/main/main"); //로그인 AppId
			
		} else {
			message.put("msg", msg == null ? "로그인에 실패하였습니다." : msg);
		}
		
		return message;
	}

	
	@RequestMapping(value="/selectSessionExpireMessage.do")
	@ResponseBody
	public Map<String, String> selectSessionExpireMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String message = userLoginService.selectSessionExpireMessage(IP.getClientIP(request));

		Map<String, String> map = new HashMap<>();
		map.put("message", message);
		
		return map;
	}

	@RequestMapping("/selectSchedule.do")
	public View selectSchedule(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {

		// 개인정보 처리방침 재동의 구하기
		String privacyReConsent = userLoginService.selectPrivacySchedule(request, dataRequest);
		
		// 비밀번호 변경 주기에 비밀번호 변경하기
		String changePassword = userLoginService.selectChangePassword(request, dataRequest);
		
		Map<String, String> map = new HashMap<>();
		
		map.put("privacyReConsent", privacyReConsent);
		map.put("changePassword", changePassword);
		
		dataRequest.setResponse("dmSchedule", map);
		
		return new JSONDataView();
	}

	@RequestMapping("/idpwCheck.do")
	@ResponseBody
	public Map<String, Object> idpwCheck(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = userLoginService.selectIdPwCheck(request, response);
		return map;
	}
	
}
