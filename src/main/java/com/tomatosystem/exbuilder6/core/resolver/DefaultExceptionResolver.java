package com.tomatosystem.exbuilder6.core.resolver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import com.google.gson.Gson;
import com.tomatosystem.exbuilder6.core.exception.AbstractException;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.logging.mapper.ErrorLoggingMapper;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.IP;
import isry.itgcms.util.StringUtil;
import isry.itgcms.util.UserException;

/**
 * 
 * DefaultExceptionResolver.java
 * 
 * @Description Exception Resolver
 * @author Park. ju wan
 * @since 2020. 10. 8.
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일             수정자             수정내용
 *    -------        ---------------       --------------
 *   2020. 10. 8.        Park. ju wan       최초 생성
 *
 *      </pre>
 */
public class DefaultExceptionResolver implements HandlerExceptionResolver {

	
	private final Logger logger = LogManager.getLogger(DefaultExceptionResolver.class);

	private static String defaultErrorMessage = "요청 작업 처리중 오류가 발생하였습니다.\n시스템 관리자에게 문의하세요.";
	
	@Autowired
	private MessageSourceAccessor messageSource;

	@Resource(name = "errorLoggingMapper")
	private ErrorLoggingMapper errorLoggingMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		Integer errorKey = null;
		
		String errorMsg = "";
		
		if (ex instanceof java.lang.NullPointerException) {
			errorMsg = "java.lang.NullPointerException";
		} else {
			errorMsg = findCauseUsingPlainJava(ex).getMessage();
			if (errorMsg == null || "".equals(errorMsg)) {
				errorMsg = ex.getMessage();
			}
		}
		
		logger.error("\n\n" + "#### errorMsg : " + errorMsg + "\n");
		
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("CLASS_NM", handlerMethod.getBeanType().getCanonicalName());
		errorMap.put("METHOD_NM", handlerMethod.getMethod().getName());
		errorMap.put("ERROR_MSG", errorMsg);
		//StackTraceElement[] st = ex.getStackTrace();
		StackTraceElement[] st = findCauseUsingPlainJava(ex).getStackTrace();
		StringJoiner strJoiner = new StringJoiner("\n");
		for (StackTraceElement ste : st) {
			strJoiner.add(ste.toString());
		}
		errorMap.put("STACKTRACE", strJoiner.toString());

		Map<String,String[]> paramMap = request.getParameterMap();
		String formData = new Gson().toJson(paramMap); 
		errorMap.put("FORM_DATA", formData);
		
		//logger.info("#### FORM_DATA 1 : " + formData);
		
		//String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		//if ("local".equals(profile)) {
			logger.info("\n\n" + strJoiner.toString() + "\n");
		//}

		try {
			errorKey = errorLog(errorMap);
		} catch (Exception e1) {
			logger.info(e1.getMessage());
		}
		
		AbstractException appException = null;
		String oriErrMsg = "";
		SQLException sqlEx = null;
		String message = null;
		if (ex instanceof AbstractException) {
			appException = (AbstractException) ex;

			logger.error("\nError Type: AppWorksException: " + "\nError Message: " + ex.getMessage() + "\nposition: "
					+ ex.getStackTrace()[0]);

			message = ex.getMessage();
		} else if (ex instanceof DataAccessException) {

			if (((DataAccessException) ex).getRootCause() instanceof SQLException) {
				sqlEx = (SQLException) ((DataAccessException) ex).getRootCause();

				//appException = new AppWorksException("errors.sql-" + sqlEx.getErrorCode(), 500);
				appException = new AppWorksException("errors.sql", 500);

			}
//			else if(((DataAccessException)ex).getRootCause() instanceof OracleDatabaseException) {
//				
//				OracleDatabaseException oraEx = (OracleDatabaseException) ((DataAccessException) ex).getRootCause();
//				
//				appException = new AppWorksException("ORA-"+oraEx.getOracleErrorNumber(), 500);
//				
//			}
			else {
				appException = new AppWorksException("errors.db", 500);
			}

			// Error console Logging
			if (logger.isInfoEnabled()) {
				logger.info("\n\n" + ex.getMessage() + "\n");
			}

		} else if (findCauseUsingPlainJava(ex) instanceof UserException) {
			
			logger.debug("#### ex instanceof UserException");
			
			appException = new AppWorksException("errors.sessionExpired", 500);
			
			message = errorMap.get("ERROR_MSG").toString();

		} else if (errorMsg != null && errorMsg.startsWith("error")) {
			
			logger.debug("#### errorMsg.startsWith(\"error\")");
			
			appException = new AppWorksException("errors.sessionExpired", 500);
			
			if (errorMsg.startsWith("errors :")) {
				message = errorMsg.substring(8).trim();
			} else if (errorMsg.startsWith("error :")) {
				message = errorMsg.substring(7).trim();
			} else if (errorMsg.startsWith("errors")) {
				message = errorMsg.substring(6).trim();
			} else if (errorMsg.startsWith("error")) {
				message = errorMsg.substring(5).trim();
			} else {
				message = errorMsg;
			}
			
		} else {
			oriErrMsg = ex.getMessage();
			// 에러가 발생했습니다. 시스템 관리자에게 문의하여 주십시오.
			appException = new AppWorksException("errors.app", 500);

			// Error console Logging
			if (logger.isInfoEnabled()) {
				logger.info(ex.getMessage());
			}
		}

		int statusCode = appException.getStatusCode();

		//Map<String, Object> mapMsg = null;

		if (message == null) {
			message = appException
					.parseMessage(messageSource.getMessage(appException.getMessage(), defaultErrorMessage));

			if (errorMap.get("ERROR_MSG") != null) {
				if (errorMap.get("ERROR_MSG").toString().endsWith("권한이 없습니다.")) {
					message = errorMap.get("ERROR_MSG").toString();
				}
			}
		}
		
		// eXbuilder6 화면에서 호출한 경우인지 확인
		boolean isExbuilderRequest = false;
		MethodParameter[] params = handlerMethod.getMethodParameters();
		if (params != null && params.length > 0) {
			MethodParameter mparam = null;
			for (int i = 0, len = params.length; i < len; i++) {
				mparam = params[i];
				if (DataRequest.class.isAssignableFrom(mparam.getParameterType())) {
					isExbuilderRequest = true;
					break;
				}
			}
		}

		// eXbuilder6 요청에 의한 오류인 경우에는
		// JSON 형태로 에러 정보를 만들어서 reponse에 반환한다.
		if (isExbuilderRequest) {
			Map<String, Object> mapErrResult = new HashMap<String, Object>();
			Map<String, Object> errorState = new HashMap<String, Object>();
			
			errorState.put("STATUSCODE", statusCode);
			errorState.put("ERRCODE", appException.getMessage());
			errorState.put("ERRMSG", message);
			
			errorState.put("LOG_MNG_NO", errorKey);
			
			mapErrResult.put("ERRMSGINFO", errorState);

			ModelAndView mv = new ModelAndView();
			mv.addObject("ERRMSGINFO", errorState);
			mv.setView(new JSONDataView());
//		     return new ModelAndView(new JSONDataView(mapErrResult));     
			return mv;
		} else {
			// 그외 HTML, JSP에서 요청온 경우이면... HTTP 에러코드를 반환한다.
			try {
				response.sendError(statusCode, message);
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
			return null;
		}
	}

	/*
	 * private ModelAndView _resolveException(String viewName, Exception e) {
	 * ModelAndView modelAndView = new ModelAndView();
	 * 
	 * modelAndView.setViewName(viewName);
	 * 
	 * if (viewName.equals("jsonView")) { modelAndView.addObject("errorCode", 1);
	 * modelAndView.addObject("errorMessage", String.format("Exception : %s( %s )",
	 * e.getClass().getName(), e.getMessage())); return modelAndView; } else {
	 * modelAndView.addObject("error", e.getMessage()); return modelAndView; } }
	 */


	private Integer errorLog(Map<String, Object> map) throws Exception {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		if ("local".equals(profile)) {
			return null;
		}

		String ip = IP.getClientIP(request);
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		map.put("USER_ID", userId == null || "".equals(userId) ? "system" : userId); 
		map.put("REQUEST_URL", request.getRequestURI());
		map.put("CLASS_NM", map.get("CLASS_NM"));
		map.put("METHOD_NM", map.get("METHOD_NM"));
		map.put("ERROR_MSG", map.get("ERROR_MSG") == null ? "" : map.get("ERROR_MSG"));
		map.put("STACKTRACE", map.get("STACKTRACE"));
		map.put("_AUTH_APP_ID", request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID"));
		map.put("_AUTH_MENU_NO", request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO")) ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO")));
		
		map.put("TOP_MENU_NM", mgmtMenuMapper.selectTopMenuNm(loginVO.getUntTaskwk()));
		
		map.put("REQUEST_IP", ip);
		map.put("CREATOR", userId == null || "".equals(userId) ? "system" : userId);
		map.put("FORM_DATA", StringUtil.truncateWhenUTF8((String)map.get("FORM_DATA"), 65000));
		
		errorLoggingMapper.insertErrorLog(map);
		
		//logger.info("#### FORM_DATA 2 : " + cut((String)map.get("FORM_DATA"), 65000));
		
		//logger.debug("#### ERROR MAP : " + map.toString());
		
		//logger.debug("#### LOG_MNG_NO : " + map.get("LOG_MNG_NO"));
		
		return ((java.math.BigDecimal)map.get("LOG_MNG_NO")).intValue();
	}

	public Throwable findCauseUsingPlainJava(Throwable throwable) {
	    Objects.requireNonNull(throwable);
	    Throwable rootCause = throwable;
	    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
	        rootCause = rootCause.getCause();
	    }
	    return rootCause;
	}

	private String cut(String s, int n) {
		byte[] utf8 = s.getBytes();
		if (utf8.length < n) n = utf8.length;
		int n16 = 0;
		int advance = 1;
		int i = 0;
		while (i < n) {
			advance = 1;
			if ((utf8[i] & 0x80) == 0) i += 1;
			else if ((utf8[i] & 0xE0) == 0xC0) i += 2;
			else if ((utf8[i] & 0xF0) == 0xE0) i += 3;
			else { i += 4; advance = 2; }
			if (i <= n) n16 += advance;
		}
		return s.substring(0,n16);
	}

}
