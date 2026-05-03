package egovframework.com.sym.log.lgm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.EgovMessageSource;
//import egovframework.com.cmm.privacy.UploadAPICaller;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.ptl.mvc.filter.HTMLTagFilterRequestWrapper;
import isry.itgcms.sysmgmt.logging.mapper.SystemLoggingMapper;
import isry.itgcms.util.IP;
import isry.itgcms.util.StringUtil;
import isry.itgcms.util.UserException;

 /**
 * @파일명        : EgovSysLogAspect.java
 * @프로그램 설명      : 시스템 로그 생성을 위한 ASPECT 클래스
 * @작성자        : 송영일
 * @작성일        : 2021.11.18 
 * @수정자        : HAN CHANH HUN
 * @수정일        : 2022. 12. 23.
 * @수정내용      : 개인정보 API 연동             
 */

public class EgovSysLogAspect {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "msg")
	protected EgovMessageSource msg;

	@Resource(name = "prop")
	protected EgovProperties prop;
	
	@Resource(name = "systemLoggingMapper")
	private SystemLoggingMapper systemLoggingMapper;

	//@Autowired
	//private RedisService3 redisService;
	
	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 init으로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logInit(ProceedingJoinPoint joinPoint) throws Throwable {

		// #######################################################################
		// [Insert] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################
		
		log.debug(".EgovSysLogAspect.logInit() Start!");
		
		StopWatch stopWatch = new StopWatch();

		checkRights("INIT", joinPoint.getSignature().getName());
				
		try {

			stopWatch.start();

			Object retValue = joinPoint.proceed();

			return retValue;

		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "INIT";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);

			log.debug(".EgovSysLogAspect.logInit() End!"+sysLog.toString());
			
		}

	}

	
	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 insert로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logInsert(ProceedingJoinPoint joinPoint) throws Throwable {

		// #######################################################################
		// [Insert] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################
		
		log.debug(".EgovSysLogAspect.logInsert() Start!");
		
		StopWatch stopWatch = new StopWatch();

		checkRights("CREATE", joinPoint.getSignature().getName());
		
		try {

			stopWatch.start();

			Object retValue = joinPoint.proceed();

			return retValue;

		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "CREATE";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);
			
			// 개인정보 API 호출(2022-06-27)
			/*
			List<String> paramList = getParam(joinPoint);
			UploadAPICaller caller = new UploadAPICaller();
			if(paramList.size() > 0) {
				   caller.prevacyContent(paramList.get(0));
				}
			*/
			log.debug(".EgovSysLogAspect.logInsert() End!"+sysLog.toString());
			
		}

	}

	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 update로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logUpdate(ProceedingJoinPoint joinPoint) throws Throwable {


		// #######################################################################
		// [Update] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################

		log.debug(".EgovSysLogAspect.logUpdate() Start!");
		
		StopWatch stopWatch = new StopWatch();

		checkRights("UPDATE", joinPoint.getSignature().getName());
		
		try {
			stopWatch.start();

			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "UPDATE";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			/* Authenticated */

			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);
			/*
			// 개인정보 API 호출(2022-06-27일)
			List<String> paramList = getParam(joinPoint);
			UploadAPICaller caller = new UploadAPICaller();
			if(paramList.size() > 0) {
				   caller.prevacyContent(paramList.get(0));
				}
			*/
			log.debug(".EgovSysLogAspect.logUpdate() End!"+sysLog.toString());
			
		}

	}

	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 update로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {

		// #######################################################################
		// [Save] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################

		log.debug(".EgovSysLogAspect.logSave() Start!");
		
		StopWatch stopWatch = new StopWatch();

		if (!"processLogoutLog".equals(joinPoint.getSignature().getName()) &&
				!"saveFileDownloadAllHistory".equals(joinPoint.getSignature().getName()) &&
				!"saveFileDownloadHistory".equals(joinPoint.getSignature().getName())) {
			checkRights("MANAGE", joinPoint.getSignature().getName());
		}
		
		try {
			
			stopWatch.start();

			Object retValue = joinPoint.proceed();
			
			return retValue;
			
		} catch (Throwable e) {
			
			throw e;
			
		} finally {
			
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "SAVE";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);
			
			// 개인정보 API 호출(2022-06-27일)
			/*
			List<String> paramList = getParam(joinPoint);
			UploadAPICaller caller = new UploadAPICaller();
			if(paramList.size() > 0) {
				   caller.prevacyContent(paramList.get(0));
				}
			 */
			log.debug(".EgovSysLogAspect.logSave() End!"+sysLog.toString());
		}
	}
	
	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 delete로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logDelete(ProceedingJoinPoint joinPoint) throws Throwable {

		// #######################################################################
		// [Delete] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################

		log.debug(".EgovSysLogAspect.logDelete() Start!");
		
		StopWatch stopWatch = new StopWatch();

		checkRights("DELETE", joinPoint.getSignature().getName());
		
		try {
			stopWatch.start();

			Object retValue = joinPoint.proceed();
			
			return retValue;
			
		} catch (Throwable e) {
			
			throw e;
			
		} finally {
			
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "DELETE";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);
			
			// 개인정보 API 호출(2022-06-27)
			//List<String> paramList = getParam(joinPoint);
			//UploadAPICaller caller = new UploadAPICaller();
			//if(paramList.size() > 0) {
			   //caller.prevacyContent(paramList.get(0));
			//}
			
			log.debug(".EgovSysLogAspect.logDelete() End!"+sysLog.toString());
		}
	}

	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 select로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object logSelect(ProceedingJoinPoint joinPoint) throws Throwable {

		// #######################################################################
		// [Select] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################
		
		log.debug(".EgovSysLogAspect.logSelect() Start!");
		
		StopWatch stopWatch = new StopWatch();

		//log.info("#### Name : " + joinPoint.getSignature().getName());
		
		if (!"selectErrorLogDetail".equals(joinPoint.getSignature().getName())
			&& !"selectErrorDetail".equals(joinPoint.getSignature().getName())) {
			checkRights("VIEW", joinPoint.getSignature().getName());
		}
		
		try {
			stopWatch.start();

			Object retValue = joinPoint.proceed();
			/*
			if(retValue instanceof List) { 
				List<String> paramList  = (List<String>) retValue;
				// 개인정보 API 호출(2022-12-22일)
				UploadAPICaller caller = new UploadAPICaller();
				if(paramList.size() > 0) {
//				   System.out.println("########################");	
//				   System.out.println("전체정보 =>"+paramList.toString());
//				   System.out.println("########################");	
				   caller.prevacyContent(paramList.toString());
				}
			}
			*/
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "READ";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			// 파라미터 정보 받아오기
			/*
			Object[] args = joinPoint.getArgs();
			if (args.length <= 0 ) log.info("파라미터 없음!!!!");
			for (Object arg : args) {
				log.info("파라미터  타입 = {}", arg.getClass().getSimpleName());
				log.info("파라미터 value = {}", arg);
			}
			*/
	
			
			log.debug(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);
			
			log.debug(".EgovSysLogAspect.logSelect() End!"+sysLog.toString());

		}

	}

	/**
	 * 시스템 로그정보를 생성한다. sevice Class의 select로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logSelectDetail(ProceedingJoinPoint joinPoint) throws Throwable {

		// #######################################################################
		// [Select] 로그에 대한 AOP 기능에 따라 로그 처리를 한다 
		// 송영일 2021.11.18
		// #######################################################################
		
		log.debug(".EgovSysLogAspect.logSelectDetail() Start!");
		
		StopWatch stopWatch = new StopWatch();

		checkRights("DETAIL", joinPoint.getSignature().getName());
		
		try {
			stopWatch.start();

			Object retValue = joinPoint.proceed();
			/*
			if(retValue instanceof List) { 
				List<String> paramList  = (List<String>) retValue;
				// 개인정보 API 호출(2022-12-22일)
				UploadAPICaller caller = new UploadAPICaller();
				if(paramList.size() > 0) {
//				   System.out.println("########################");	
//				   System.out.println("전체정보 =>"+paramList.toString());
//				   System.out.println("########################");	
				   caller.prevacyContent(paramList.toString());
				}
			}
			*/
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			
			stopWatch.stop();

			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "DETAIL";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";

			// 파라미터 정보 받아오기
			/*
			Object[] args = joinPoint.getArgs();
			if (args.length <= 0 ) log.info("파라미터 없음!!!!");
			for (Object arg : args) {
				log.info("파라미터  타입 = {}", arg.getClass().getSimpleName());
				log.info("파라미터 value = {}", arg);
			}
			*/
	
			log.debug(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrchFactrCn(Arrays.toString(getParam(joinPoint).toArray()));
			
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			systemLog(sysLog);
			
			
			log.debug(".EgovSysLogAspect.logSelectDetail() End!"+sysLog.toString());

		}

	}

	
	@SuppressWarnings("unchecked")
	private List<String> getParam(ProceedingJoinPoint joinPoint) {
		
		List<String> paramList = new ArrayList<>();
		
		String methodName = joinPoint.getSignature().getName();
		if ("processUserLogin2".equals(methodName)) {
			log.info("\"processUserLogin2\".equals(methodName)");
			return paramList;
		}
		
		Object[] paramValues = joinPoint.getArgs();
		
		for (int i=0; i < paramValues.length; i++) {
			if (paramValues[i] instanceof DataRequest) {
				DataRequest dataRequest = (DataRequest)paramValues[i];
				List<String> dataNames = dataRequest.getParameterGroupNames();
				if (dataNames != null) {
					for (int j=0; j < dataNames.size(); j++) {
						String dataName = dataNames.get(j);
						ParameterGroup paramGroup = dataRequest.getParameterGroup(dataName);
						
						if (paramGroup.isDataSet()) {
							List<Map<String, String>> dataList = paramGroup.getAllRowList();
							if (dataList != null) {
								for (int k=0; k < dataList.size(); k++) {
									Map<String, String> dataMap = dataList.get(k);
									if (dataMap != null) {
										log.debug("(DataSet) " + mapString(new HashMap<String, Object>(dataMap)));
										paramList.add("(DataSet) " + mapString(new HashMap<String, Object>(dataMap)));
									}
								}
							}
						} else {
							Map<String, Object> dataMap = new HashMap<String, Object>(paramGroup.getSingleValueMap());
							if (dataMap != null) {
								log.debug("(DataMap) " + mapString(dataMap));
								paramList.add("(DataMap) " + mapString(dataMap));
							}
						}
					}
				}
			} else if (paramValues[i] instanceof HashMap) {
				log.debug("(HashMap) : " + mapString(new HashMap<String, Object>((HashMap)paramValues[i])));
				paramList.add("(HashMap) : " + mapString(new HashMap<String, Object>((HashMap)paramValues[i])));
			} else if (paramValues[i] instanceof String) {
				log.debug("(String) : " + paramValues[i].toString());
				paramList.add("(String) : " + paramValues[i].toString());
			} else if (paramValues[i] instanceof Integer) {
				log.debug("(Integer) : " + paramValues[i].toString());
				paramList.add("(Integer) : " + paramValues[i].toString());
				
			} else if (paramValues[i] instanceof HTMLTagFilterRequestWrapper) {
				HttpServletRequest dataRequest = (HttpServletRequest)paramValues[i];
				Map<String, String[]> paramMap = dataRequest.getParameterMap();
				for (String key : paramMap.keySet()) {
					String[] values = paramMap.get(key);
					log.debug("(HTMLTagFilterRequestWrapper) : " + key + " : " + (values.length <= 1 ? values[0] : Arrays.toString(values)));
					
					if ("saveCode".equals(methodName)) {
						log.debug("methodName : " + methodName + ", key : " + key + ", values.length : " + values.length);
					}
					
					if ("saveCode".equals(methodName) && "@d1#str".equals(key)) {
						
						log.debug("methodName : " + methodName + ", key : " + key + ", values.length : " + values.length);
						
						String str = "";
						
						if (values.length > 1) {
							for (int j=0; j < values.length; j++) {
								if (j > 0) {
									str += ", " + new String(Base64.getDecoder().decode(values[j]));
								} else {
									str += new String(Base64.getDecoder().decode(values[j]));
								}
							}
						}
						
						paramList.add("(HTMLTagFilterRequestWrapper) : " + key + " : " + (values.length <= 1 ? new String(Base64.getDecoder().decode(values[0])) : str));
						//paramList.add("(HTMLTagFilterRequestWrapper) : " + key + " : " + (values.length <= 1 ? new String(Base64.getDecoder().decode(values[0])) : Arrays.toString(values)));
					} else {
						paramList.add("(HTMLTagFilterRequestWrapper) : " + key + " : " + (values.length <= 1 ? values[0] : Arrays.toString(values)));
					}
				}
				
			} //else {
				//log.debug("(else) : " + (paramValues[i] == null ? "" : paramValues[i].toString()));
				//paramList.add("(else) : " + (paramValues[i] == null ? "" : paramValues[i].toString()));
			//}
		}
		
		return paramList;
	}
	
	private String mapString(Map<String, Object> map) {
		String mapStr = "";
		log.debug("#### map : " + map.toString());
		Iterator<String> keys = map.keySet().iterator();
		//ScpDb scpDb = new ScpDb();
		int i = 0;
		while (keys.hasNext()) {
			String key = keys.next();
			if (i > 0) {
				mapStr += ", ";
			}
			//if (key.endsWith("_ENCPT") && String.valueOf(map.get(key)).endsWith("=")) {
				//mapStr += key + " : " + scpDb.scpDecB64(String.valueOf(map.get(key)));
			//} else {
				mapStr += key + " : " + String.valueOf(map.get(key));
			//}
			i++;
		}
		return mapStr;
	}
	
	private void systemLog(SysLog sysLog) throws Exception {
		
		if ("selectCommonCode".equals(sysLog.getMethodNm()) ||
				"selectCommonCodeUnit".equals(sysLog.getMethodNm())) {
			return;
		}

		if (RequestContextHolder.getRequestAttributes() == null ||
				!(RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes)) {
			return;
		}
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		Map<String, Object> map = new HashMap<>();
		
		HttpSession session = request.getSession();
		//UserDetailsVO loginVO = redisService.selectRedisSession("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		String loginId = (String)session.getAttribute("userId");
		String userId = "";
		if (loginId != null && !"".equals(loginId)) {
			userId = loginId;
		}
		
		map.put("USER_ID", userId == null || "".equals(userId) ? "system" : userId);
		map.put("REQUEST_URL", request.getRequestURI());
		map.put("CLASS_NM", sysLog.getSrvcNm());
		map.put("METHOD_NM", sysLog.getMethodNm());
		map.put("PROCESS_SE_CODE", sysLog.getProcessSeCode());
		map.put("PROCESS_TIME", sysLog.getProcessTime());
		map.put("_AUTH_APP_ID", request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID"));
		map.put("_AUTH_MENU_NO", request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO")) ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO")));
		map.put("_AUTH_UP_MENU_ID", request.getParameter("_AUTH_UP_MENU_ID") == null || "".equals(request.getParameter("_AUTH_UP_MENU_ID")) ? 0 : Integer.parseInt(request.getParameter("_AUTH_UP_MENU_ID")));
		map.put("REQUEST_IP", IP.getClientIP(request));
		map.put("CREATOR", userId == null || "".equals(userId) ? "system" : userId);
		map.put("SRCH_FACTR_CN", StringUtil.truncateWhenUTF8(sysLog.getSrchFactrCn(), 65000));
		
		systemLoggingMapper.insertSystemLog(map);
				
	}

	private void checkRights(String act, String methodName) throws Exception {

		if (RequestContextHolder.getRequestAttributes() == null ||
				!(RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes)) {
			return;
		}
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession session = request.getSession();
		//UserDetailsVO loginVO = redisService.selectRedisSession("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		//UserDetailsVO loginVO = redisService.selectRedisSession("LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId());
		String loginId = (String)session.getAttribute("userId");
		String userId = "";
		if (loginId != null && !"".equals(loginId)) {
			userId = loginId;
		}

		log.debug("#### methodName : " + methodName);
		
		/*
		String[] permitMethods = {"selectButtonUseYn", "selectCheckedMyPage", "userLogin", "userLogin2", "logoutLog",
				"registCertificate", "deleteCertificate", "loginCertificate", "selectMenuAuth", "selectComCodeList",
				"selectCommonCode", "selectCommonCodeUnit", "registFinanceCertificate", "deleteFinanceCertificate", 
				"loginFinanceCertificate"};
		
		String[] requestURIs = {"onLoadUserJoin.do", "saveWorker.do", "selectMaxInstCd.do", "checkIdDuplicate.do", 
				"upload.do", "imageUpload.do", "list.do", "delete.do", "selectUnitSystemOrganization.do", "selectOrgDept.do",
				"gitpleSave.do", "selectOrg.do", "saveInstitute.do", "selectNoticePopupList.do", 
				"selectWorker.do", "userLogin.do", "selectErrorLogDetail.do", "selectQualificationNo.do",
				"jusoUpdate.do", "findId.do", "findPw.do", "findId2.do", "findPw2.do", "userLogin2.do",
				"selectNoticePopupListOuter.do", "loginNotice.do", "initDyncNtcBrd.do", "onLoadDyncNtcBrd.do", "listDyncNtcBrd.do",
				"dtlListDyncNtcBrd.do", "checkBookmark.do", "manageBookmark.do", "loginNoticeList.do", "loginNoticeDetail.do",
				"phoneRegist.do", "phoneDelete.do", "phoneLogin.do", "simpleRegist.do", "simpleDelete.do", "simpleLogin.do",
				"checkCertificateDuplicate.do", "loginProcess.do", "checkSimpleDuplicate.do", "selectSessionExpireMessage.do",
				"userLogin4.do", "login2.do", "loginFinanceCertificate.do", "loginCertificate.do", "download.do",
				"selectCommonCodeUnit.do", "selectCommonCodeJoinRights.do", "fileDown.do", "selectErrorLogDetail.do",
				"getDangerLastArticleNo.do"};
		
		if (userId == null || "".equals(userId)) {
			
			boolean permitUrl = false;
			for (int i=0; i < requestURIs.length; i++) {
				if (request.getRequestURI().endsWith(requestURIs[i])) {
					permitUrl = true;
					break;
				}
			}
			
			if (permitUrl || Arrays.stream(permitMethods).anyMatch(s -> s.equals(methodName))) {
				return;
			} else {
				log.debug("#### methodName 1 : " + methodName);
				log.debug("#### RequestURI : " + request.getRequestURI());
				throw new UserException("errors.sessionExpired");
			}
		}
		*/
		
		//Integer menuId = request.getHeader("_AUTH_APP_ID") == null || "".equals(request.getHeader("_AUTH_APP_ID"))
				//? 0 : systemLoggingMapper.getMenuId(request.getHeader("_AUTH_APP_ID"));
		
		Integer menuId = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.valueOf(request.getParameter("_AUTH_MENU_NO"));
		
		log.debug("#### _AUTH_APP_ID : " + request.getParameter("_AUTH_APP_ID"));
		log.debug("#### menuNo : " + menuId);
		
		if (menuId == null || menuId == 0) {
			return;
		}
		
		if (menuId != 1150  // 내 정보 수정
				&& menuId != 4803  // 업무및메뉴권한신청
				&& menuId != 5199  // 기관 관리
				&& !checkHasRights(userId, menuId, act)) {
			
			if (loginId == null || "".equals(loginId)) {
				throw new UserException("errors.sessionExpired");
			} else {
				throw new UserException(act + " 권한이 없습니다.");
			}
		}
	}
	
	private boolean checkHasRights(String userId, Integer menuId, String act) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("USER_ID", userId);
		map.put("MENU_NO", menuId);
		map.put("ACT", act);
		
		// 메뉴 그룹별 권한 외에 사용자별 권한 설정이 있는지 체크한다.
		Integer checkCount = systemLoggingMapper.checkUserRightsExists(map);

		if (checkCount > 0) {

			// 사용자별 권한 설정 내용을 구한다.
			String right1 = systemLoggingMapper.checkUserRights(map);

			if ("Y".equals(right1)) {
				return true;
			} else {
				return false;
			}
		
		} else {
			
			// 메뉴 그룹별 권한 설정 내용을 구한다.
			String right2 = systemLoggingMapper.checkGroupRights(map);

			if ("Y".equals(right2)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
