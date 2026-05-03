package isry.itgcms.sysmgmt.userlogin.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//import com.shilladfs.adcommon.login.svc.CommonSVC;
//import com.shilladfs.adcommon.login.svc.LoginSVC;

import isry.itgcms.sysmgmt.userlogin.vo.CustomUserDetails;
import isry.itgcms.sysmgmt.userlogin.vo.JSONResult;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    
	private final Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);

    String returnUrl = "/index.do";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    		throws IOException, ServletException {
        
    	LOG.info("# LoginSuccessHandler.onAuthenticationSuccess");

		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		// 5분 더하기
		cal.add(Calendar.MINUTE, 10);
		String dateTime = simpleDateFormat.format(cal.getTime());
		
		Cookie cookie = new Cookie("currentTime", dateTime);
		//cookie.setDomain("localhost");
		cookie.setPath("/");
		// 5분간 저장
		cookie.setMaxAge(10 * 60 * 60);
		// 30초간 저장
		//cookie.setMaxAge(30 * 60);
		//cookie.setSecure(true);
		response.addCookie(cookie);
		
        CustomUserDetails detailVO = (CustomUserDetails) authentication.getDetails();

        // application/json(ajax) 요청일 경우 아래의 처리!
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        JSONResult jsonResult = JSONResult.success(detailVO);
        
        jsonResult.setMessage("2");
        
        if (jsonConverter.canWrite(jsonResult.getClass(), jsonMimeType)) {
            jsonConverter.write(jsonResult, jsonMimeType, new ServletServerHttpResponse(response));
        }
    }

}
