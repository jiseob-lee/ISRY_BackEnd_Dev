package isry.base;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;


public class IsryBaseController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "msg")
	protected EgovMessageSource msg;

	@Resource(name = "prop")
	protected EgovProperties prop;

	
	public IsryBaseController() {
		//System.out.println(".IsryBaseController Called!!!");
	}	
	
	
}