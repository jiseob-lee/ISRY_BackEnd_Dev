package isry.itgcms.sysmgmt.userauth.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userauth.service.MgmtAuthInsertService;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class MgmtAuthInsertController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "mgmtAuthInsertService")
	private MgmtAuthInsertService mgmtAuthInsertService;

	@RequestMapping(value = "/insertGrpAuth.do")
	public View insertGrpAuth(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		log.debug("test");
		
		mgmtAuthInsertService.insertGrpAuth(request, dataRequest);

		return new JSONDataView();

	}

}
