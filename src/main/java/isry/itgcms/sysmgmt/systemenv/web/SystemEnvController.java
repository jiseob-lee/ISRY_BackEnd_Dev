package isry.itgcms.sysmgmt.systemenv.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.systemenv.service.SystemEnvService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.IP;
import isry.redis.service.RedisService;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/systemenv")
public class SystemEnvController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "systemEnvService")
	private SystemEnvService systemEnvService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = {"/onloadAdminIp.do", "/selectAdminIp.do"})
	public View selectAdminIp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		log.debug("#### ip : " + IP.getClientIP(request));
		
		dataRequest.setResponse("dsList", systemEnvService.selectAdminIp());
		
		if (requestUrl.endsWith("/onloadAdminIp.do")) {
			dataRequest.setResponse("dsUseYN", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveAdminIp.do")
	public View saveAdminIp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		systemEnvService.saveAdminIp(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value = {"/onloadSecondSkipIp.do", "/selectSecondSkipIp.do"})
	public View selectSecondSkipIp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		log.debug("#### ip : " + IP.getClientIP(request));
		
		dataRequest.setResponse("dsList", systemEnvService.selectSecondSkipIp());
		
		if (requestUrl.endsWith("/onloadSecondSkipIp.do")) {
			dataRequest.setResponse("dsUseYN", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveSecondSkipIp.do")
	public View saveSecondSkipIp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		systemEnvService.saveSecondSkipIp(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = {"/onloadSecondSkipId.do", "/selectSecondSkipId.do"})
	public View selectSecondSkipId(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		log.debug("#### ip : " + IP.getClientIP(request));
		
		dataRequest.setResponse("dsList", systemEnvService.selectSecondSkipId());
		
		if (requestUrl.endsWith("/onloadSecondSkipId.do")) {
			dataRequest.setResponse("dsUseYN", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveSecondSkipId.do")
	public View saveSecondSkipId(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		systemEnvService.saveSecondSkipId(request, dataRequest);
		
		return new JSONDataView();
	}

}
