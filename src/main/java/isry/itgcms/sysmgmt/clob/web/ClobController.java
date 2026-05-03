package isry.itgcms.sysmgmt.clob.web;

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

import isry.itgcms.sysmgmt.clob.service.ClobService;

@Controller
@RequestMapping(value = "/isry/itgcms/sysmgmt/clob")
public class ClobController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "clobService")
	private ClobService clobService;

	@RequestMapping(value = "/selectClob.do")
	public View selectClob(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsClob", clobService.selectClob(request, dataRequest));
		
		log.debug("test");
		
		return new JSONDataView();
	}
}
