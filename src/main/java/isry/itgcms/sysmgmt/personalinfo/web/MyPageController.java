package isry.itgcms.sysmgmt.personalinfo.web;

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

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.personalinfo.service.MyPageService;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/personalinfo")
public class MyPageController extends IsryBaseController {
	
	@Resource(name = "myPageService")
	private MyPageService myPageService;

	@RequestMapping(value = "/manageBookmark.do")
	public View manageBookmark(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		myPageService.saveMyPage(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/checkBookmark.do")
	public View checkBookmark(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		boolean checkResult = myPageService.selectCheckedMyPage(request, dataRequest);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("checkResult", checkResult ? 1 : 0);
		
		dataRequest.setResponse("dmCheckBookmark", map);		
		
		return new JSONDataView();
	}

}
