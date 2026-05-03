package isry.itgcms.sysmgmt.image.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.util.StringUtil;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/image")
public class ImageViewController {
	
	@Resource(name = "mgmtFileService")
	private MgmtFileService mgmtFileService;
	
	@RequestMapping(value="/imageView.do")
	public @ResponseBody byte[] imageView(HttpServletRequest request) throws Exception {

		Map<String, String> mapParam = new HashMap<String,String>();
		
		mapParam.put("ATFINO", StringUtil.isNullToString(request.getParameter("ATFINO")));
		mapParam.put("ATCMFL_CL_NM", StringUtil.isNullToString(request.getParameter("ATCMFL_CL_NM")));
		mapParam.put("MNG_SN", StringUtil.isNullToString(request.getParameter("MNG_SN")));
		
		Map<String, Object> fileInfo = mgmtFileService.selectCmnFile(mapParam);
		
		
		InputStream in = new FileInputStream("/GCLOUD/WebApp/www/images/isry/itgcms/login2/ico_plus.png");
		return IOUtils.toByteArray(in);
	}
}