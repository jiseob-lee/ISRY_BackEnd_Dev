package egovframework.com.cmm.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.UserException;

/**
 * <pre>
 * 기능 : 접근할 수 있는 자원에 대한 메뉴접근 권한 여부 체크 Intercepter
 * </pre>
 */
public class AuthenticInterceptor implements HandlerInterceptor {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	// 컨텍스트 정보  + 나머지 전체 url 정보
    	String url = request.getRequestURI();
    	log.info("#### url 주소 : " + url);
    	
    	// 컨텍스트 정보
    	String contextPath = request.getContextPath();
    	contextPath = contextPath.indexOf("ISRY_BackEnd") > -1 ? "/ISRY_BackEnd" : "";
    	
    	// 도메인 정보
    	String urlInfo = request.getRequestURL().toString().replace(request.getRequestURI(), "");   	
    	log.info("#### urlInfo 정보 : " + urlInfo);
    	
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

        /* 예외처리 URL: 세션이 필요없는 부분. 단순호출 화면. 로그인 화면단. 아래에 등록바랍니다. */
        if (loginVO == null || loginVO.getId().equals("")) {
        	if(url.contains("sheltrListApi.do")) {
        		return true;
        	}

        	if (isAjax(request)) {
        		throw new UserException("errors.sessionExpired");
        	} else {
        		response.sendRedirect(urlInfo+contextPath);
        	}
            
            return false;
        }
    	
		return true;
	}  

    private boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }
}
