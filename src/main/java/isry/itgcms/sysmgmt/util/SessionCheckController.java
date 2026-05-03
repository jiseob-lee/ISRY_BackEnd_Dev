package isry.itgcms.sysmgmt.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
public class SessionCheckController {

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@RequestMapping(value = "/sessionCheck.do")
	public View selectWorkUnit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		int sessionExists = 1;
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
		if (loginVO == null || loginVO.getId().equals("")) {
        	sessionExists = 0;
        }
        
        Map<String, Object> map = new HashMap<>();
		
        map.put("second", sessionExists);
        
        dataRequest.setResponse("dmTimer", map);
		
        return new JSONDataView();
	}

}
