package isry.sample.web;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.UIView;

/**
 * <pre>
 * 시  스  템  : 공통
 * 단위시스템  : 공통시스템
 * 프로그램명  : 
 * 설      명    : eXBuilder6 화면(*.clx)을 window 팝업으로 Open 한다. 
 * </pre>
 * 2021.12.01 YouMinsang 최초작성
 * 이력사항
 * 
 */

@Controller
public class WindowOpenController {
	
	@RequestMapping("/windowOpen.do")
	public View index(HttpServletRequest request, HttpServletResponse response, 
			DataRequest reqData) throws Exception {
					
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String popUpPageUrl = deployPath+"/";   // 페이지 URL
		String requestUrl = request.getParameter("CLX_PATH");
	 	
		requestUrl = requestUrl == null ? "app/itgcms/syscmmn/error/error" : requestUrl.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","").replaceAll("\r|\n|&nbsp;","");
	 	
		requestUrl += ".clx";
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
		//requestUrl += refreshParam;
		
		Enumeration<String> paramName = request.getParameterNames();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		for(String name : Collections.<String>list(paramName)) {
			
			//paramMap.put(name, request.getParameter(name));
			
			paramMap.put(name, request.getParameter(name).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","").replaceAll("\r|\n|&nbsp;","") );

		}	
				
		popUpPageUrl += requestUrl;
		
		return new UIView(popUpPageUrl, paramMap); 
	}

}
