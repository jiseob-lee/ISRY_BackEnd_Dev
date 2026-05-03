package egovframework.com.cmm.interceptor;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cleopatra.protocol.data.DataRequest;
import com.tomatosystem.exbuilder6.core.util.FileUtil;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.ModifiableHttpServletRequest ; 

/**
 * 
 * @파일명        : MeasuringInterceptor.java
 * @프로그램 설명      : 인터셉터 클래스이며  웹 반응시간 측정을 한다.
 * @작성자        : 한창헌
 * @작성일        : 2022. 03. 14. 
 * @수정자        :
 * @수정일        : 
 * @수정내용       : 
 */
public class MeasuringInterceptor extends HandlerInterceptorAdapter{
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
 
        //시간을 가져온다
        long currentTime = System.currentTimeMillis(); 

        //현재시간을 모델에 넣는다.
        request.setAttribute("bTime", currentTime);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
 
        // 현재 시간을 구한다
        long currentTime = System.currentTimeMillis();
        
        // 요청이 시작된 시간을 가져온다
        long beginTime = (long)request.getAttribute("bTime");
        
        // 현재 시간 - 요청이 시작된 시간 = 총 처리시간을 구한다
        //long processedTime = currentTime - beginTime;
        
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss SSS", Locale.KOREAN);
    	// 웹 호출시간
    	Date startDate = new Date();
    	startDate.setTime(beginTime);
    	// 웹 결과시간
    	Date currentDate = new Date();
    	currentDate.setTime(currentTime);
    	
    	String startString = simpleDateFormat.format(startDate);
    	String dateString = simpleDateFormat.format(currentDate);
    	log.info("1.요청 URL : " + request.getRequestURI());
    	log.info("2.요청 시간 : " +startString);
    	log.info("3.요청결과시간 : " +dateString);
        
        //log.info("요청된 URL : " + request.getRequestURI());
        //log.info("총 요청시간은 " + processedTime);

        super.afterCompletion(request, response, handler, ex);
        
    }
}
