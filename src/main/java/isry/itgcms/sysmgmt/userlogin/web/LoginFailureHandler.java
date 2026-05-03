package isry.itgcms.sysmgmt.userlogin.web;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.session.SessionAuthenticationException;
//import org.springframework.util.StringUtils;

//import isry.itgcms.sysmgmt.userlogin.vo.CustomUserDetails;
import isry.itgcms.sysmgmt.userlogin.vo.JSONResult;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFailureHandler.class);

    String returnUrl = "/login.do";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {
    	
    	LOG.info("#### authenticationException : " + authenticationException.getMessage());
    	
    	String message = authenticationException.getMessage();
    	
        //if (authenticationException instanceof SessionAuthenticationException) {
        	//LOG.debug("# onAuthenticationFailure.cookies.delete  2 =   " + request.getContextPath() + returnUrl + "?error="+authenticationException.getMessage());
        	//LOG.info("#### authenticationException : SessionAuthenticationException occurred.");
        	//response.sendRedirect(request.getContextPath() + returnUrl + "?error=1");
        	//return;
        //}
        //LOG.debug("# onAuthenticationFailure.cookies.delete 3  =   " + request.getContextPath() + returnUrl + "?error="+authenticationException.getMessage());
        //if (authenticationException instanceof BadCredentialsException) {}
        //if (authenticationException instanceof LockedException) {}
        //if (authenticationException instanceof UsernameNotFoundException) {}
		//response.sendRedirect(request.getContextPath() + returnUrl + "?error=2");

		
        // application/json(ajax) 요청일 경우 아래의 처리!
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        JSONResult jsonResult = JSONResult.fail(message); 
        if (jsonConverter.canWrite(jsonResult.getClass(), jsonMimeType)) {
            jsonConverter.write(jsonResult, jsonMimeType, new ServletServerHttpResponse(response));
        }
        
    }
}
