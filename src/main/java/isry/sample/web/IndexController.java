package isry.sample.web;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * <pre>
 * 시  스  템  : 공통
 * 단위시스템  : 공통시스템
 * 프로그램명  : 
 * 설      명    : index view 
 * </pre>
 * 
 * 이력사항
 * 
 */

@Controller
public class IndexController {
	
	private Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	
	/**
	 * CSRF 방어용 :  Token 생성. 어플리케이션 시작시 최초생성
	 * @param HttpServletRequest, HttpServletResponse, DataRequest
	 * @return new JSONDataView()
	 * @exception Exception
	 */
	@RequestMapping("/init.do")
	public View init(HttpServletRequest request, HttpServletResponse response, 
			    DataRequest reqData) throws Exception {

		String csrfToken = UUID.randomUUID().toString();
		
	    Map<String, Object> message = new HashMap<String, Object>();
		message.put("x-csrf-token-header", "x-csrf-token-header");
		message.put("x-csrf-token", csrfToken);
		
		// session Token 생성
		request.getSession().setAttribute("csrfToken", csrfToken);
		
		reqData.setMetadata(true, message);
		
		Map<String, String> mapContext = new HashMap<>();
		mapContext.put("contextPath", request.getContextPath());
		reqData.setResponse("dmContextPath", mapContext);
		
	    return new JSONDataView();

	}
	
	
	@RequestMapping("/index.do")
	public View index(HttpServletRequest request, HttpServletResponse response, 
			DataRequest reqData) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String mainPageUrl  = deployPath+"/";   //메인 페이지 URL
		String loginPageUrl = deployPath+"/";  //로그인 페이지 URL

		loginPageUrl += "app/com/inc/login5.clx";
		mainPageUrl += "app/com/main/main.clx";
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
		//HttpSession session = request.getSession(false);
		HttpSession session = request.getSession();
		//UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		log.info("#### session 3 : " + session.getId());
		
		//ModelAndView mav = new ModelAndView("");
		//RedirectView redirectView = new RedirectView("/MagicLine4Web/ML4WebProcess/certificateLogin.jsp", true);
		RedirectView redirectView = new RedirectView("/login.do", true);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			return new UIView(mainPageUrl);
		} else {
			//return new UIView(loginPageUrl);
			return redirectView;
		}
	}

	@RequestMapping("/memberJoin.do")
	public View memberJoin(HttpServletRequest request, HttpServletResponse resp, DataRequest dataRequest) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String mainPageUrl = deployPath+"/";   //메인 페이지 URL
		
		mainPageUrl += "app/itgcms/sysmgmt/05_user/MemberJoinRequest";
		
		return new UIView(mainPageUrl); 
	}

	@RequestMapping("/findId.do")
	public View findId(HttpServletRequest request, HttpServletResponse resp, DataRequest dataRequest) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String mainPageUrl = deployPath+"/";   //메인 페이지 URL
		
		mainPageUrl += "app/itgcms/sysmgmt/05_user/FindID";
		
		return new UIView(mainPageUrl); 
	}

	@RequestMapping("/findPassword.do")
	public View findPassword(HttpServletRequest request, HttpServletResponse resp, DataRequest dataRequest) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String mainPageUrl = deployPath+"/";   //메인 페이지 URL
		
		mainPageUrl += "app/itgcms/sysmgmt/05_user/FindPassword";
		
		return new UIView(mainPageUrl); 
	}
	
}
