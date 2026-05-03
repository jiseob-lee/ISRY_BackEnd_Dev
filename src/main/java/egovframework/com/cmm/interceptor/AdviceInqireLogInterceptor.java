package egovframework.com.cmm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <pre>
 * 기능 : Access Log 를 처리하기 위한 인터셉터 
 * </pre>
 */
	public class AdviceInqireLogInterceptor extends HandlerInterceptorAdapter {

		/**
		 * 로그기본 정보를 생성한다.
		 * 
		 * @param HttpServletRequest request, HttpServletResponse response, Object handler 
		 * @return 
		 * @throws Exception 
		 */
		@Override
		public boolean preHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler) throws Exception {
				request.setAttribute("ip.server", request.getLocalAddr());
				request.setAttribute("ip.client", request.getRemoteAddr());
				request.setAttribute("uri.request", request.getRequestURI());
				request.setAttribute("url.request", request.getRequestURL().toString());
				request.setAttribute("header.userAgent", request.getHeader("User-Agent"));
			    request.setAttribute("url.reqQuery", request.getParameter("reqQuery"));
			    
			return super.preHandle(request, response, handler);
		}
			
	}

	