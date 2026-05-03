package egovframework.com.cmm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @파일명        : CSRFInterceptor.java
 * @프로그램 설명      : 인터셉터 클래스이며  화면에서 요청한 CSRF 토큰을 서버 토큰과 비교 검증한다.
 * @작성자        : 한창헌
 * @작성일        : 2022. 04. 04. 
 * @수정자        :
 * @수정일        : 
 * @수정내용       : 
 */
public class CSRFInterceptor extends HandlerInterceptorAdapter {
	
	 protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	 @Override
	 public boolean preHandle(HttpServletRequest request, 
			                  HttpServletResponse response, 
			                  Object handler) throws Exception {

		 
//		 Enumeration<?> eh = request.getHeaderNames();
//		 while(eh.hasMoreElements()) {
//			 String name= (String)eh.nextElement();
//			 String value = request.getHeader(name);
//			 log.info("name="+name +"|" + "value="+value);
//		 }

		 // CSRF 토큰 체크
		 // 확인 사항 ==> 세션시간 만료시 토크 처리 확인. 분기처리 확인. 
		 String token = request.getHeader("x-csrf-token-header");
		 
		 log.info("token="+token);
		 log.info((String)request.getSession().getAttribute("csrfToken"));
		 
		 if(token != null && token.equals((String)request.getSession().getAttribute("csrfToken"))) {
			 log.info("CSRF 통과");
			 return true; 
		 } else {
			 log.info("CSRF 실패");
			 ModelAndView modelAndView = new ModelAndView("redirect:/index.do");
			 throw new ModelAndViewDefiningException(modelAndView);
		 }
		 
	 } 
	 
}