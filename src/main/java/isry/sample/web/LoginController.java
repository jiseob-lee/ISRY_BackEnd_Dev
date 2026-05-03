package isry.sample.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.redis.service.RedisService3;

/**
 * <pre>
 * 시  스  템  : 공통
 * 단위시스템  : 공통시스템
 * 프로그램명  : 
 * 설      명    : login controller
 * </pre>
 * 
 * 이력사항
 * 
 */

@Controller
//@RequestMapping("/Login")
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Autowired
	private RedisService3 redisService;
	
	@RequestMapping("/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		mav.addObject("profile", profile);
		
		log.debug("#### SERVER : " + System.getProperty("SERVER"));
		
		mav.addObject("SERVER", System.getProperty("SERVER"));
		
		mav.setViewName("isry/itgcms/login/login2");
		
		return mav;
	}
	
	@RequestMapping("/login2.do")
	public ModelAndView login2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		log.debug("#### SERVER : " + System.getProperty("SERVER"));
		
		mav.addObject("SERVER", System.getProperty("SERVER"));
		
		mav.setViewName("isry/itgcms/login/login");
		
		return mav;
	}
	

	@RequestMapping("/login_backup.do")
	public View login_backup(HttpServletRequest request, HttpServletResponse resp, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> message = new HashMap<String, Object>();
	
		ParameterGroup pgParam = dataRequest.getParameterGroup("dsParam");
		 
//		String pwd = AESCryptUtil.decrypt(AESCryptUtil.SALT,
//				AESCryptUtil.IV, AESCryptUtil.PASSPHRASE, pgParam.getValue("PWD")	, 
//				AESCryptUtil.ITERATION_COUNT, AESCryptUtil.KEYSIZE);
			
		message.put("uri", "app/com/main/main"); //로그인 AppId
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
		
	}
	
	/**
	 * 
	 * <pre>
	 * 메소드명	: logout
	 * 설	 명	: 로그아웃을 한다.
	 * </pre>
	 *
	 * @param req
	 * @param resp
	 * @param dataView
	 * @param sqlClientAssists
	 * @param reqData
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws StdServiceException
	 * @throws AppWorksException
	 */
	@RequestMapping("/logout.do")
	public View logout(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();
		
		HttpSession session = request.getSession(false);
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

		//if (!"local".equals(profile) && !"pre".equals(profile)) {
		if (!"local".equals(profile)) {
			redisService.processRedisLogout("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		}
		
		if (session != null) {
			session.invalidate();
		}
		
		//message.put("uri", AppProperties.getProperty("login.page.appid")); //로그인 AppId
		message.put("uri", "app/com/inc/login"); //로그인 AppId
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
	
}
