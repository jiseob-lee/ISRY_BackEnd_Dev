package isry.itgcms.sysmgmt.util;

import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;

/**
*
* SessionTimeoutCookieFilter 
* @author 공통컴포넌트 팀 신용호
* @since 2020.06.17
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*  수정일               수정자           수정내용
*  ----------   --------   ---------------------------
*  2020.06.17   신용호            최초 생성
*
*/

@Component("sessionTimeoutCookieFilterBean")
public class SessionTimeoutCookieFilter implements Filter {

	@SuppressWarnings("unused")
	private FilterConfig config;

	@Autowired
    private PersonalInfoService personalInfoService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        //HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        int sessionTime = 30;
        
        try {
        	Map<String, String> map = personalInfoService.selectSystemEnv();
        	sessionTime = Integer.parseInt(map.get("SESIN_TMOUT_HR"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        //long serverTime = System.currentTimeMillis();
        //long sessionExpireTime = serverTime + httpRequest.getSession().getMaxInactiveInterval() * 1000;
        
        
        long sessionDurationTime = System.currentTimeMillis() + sessionTime * 60 * 1000; 
        
        //Cookie cookie = new Cookie("egovLatestServerTime", "" + serverTime);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        //httpResponse.addCookie(cookie);
        
        
        Cookie cookie = new Cookie("sessionDurationTime", "" + sessionDurationTime);
        //cookie.setSecure(true);
        cookie.setPath("/");
        //cookie.setHttpOnly(true);
        httpResponse.addCookie(cookie);
        
        //cookie = new Cookie("egovExpireSessionTime", "" + sessionExpireTime);
        //cookie.setPath("/");
        
        //Date dateServer = new Date(serverTime);
        //Date dateExpiry = new Date(sessionExpireTime);
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //String serverYMD = format.format(dateServer);
        //String expiryYMD = format.format(dateExpiry);
        //System.out.println("=====>>> serverYMD = "+serverYMD);
        //System.out.println("=====>>> expiryYMD = "+expiryYMD);
        //System.out.println("=====>>> server TimeStamp = "+serverTime);
        //System.out.println("=====>>> expire TimeStamp = "+sessionExpireTime);
        
        //httpResponse.addCookie(cookie);

        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	@Override
	public void destroy() {

	}
}
