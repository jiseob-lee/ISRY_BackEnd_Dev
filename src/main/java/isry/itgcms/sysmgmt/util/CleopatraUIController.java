package isry.itgcms.sysmgmt.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.StringJoiner;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.cleopatra.spring.UIView;
import com.cleopatra.ui.PageGenerator;

@Controller
public class CleopatraUIController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CleopatraUIController.class);
	
	@Autowired
	ServletContext context;
	
	public CleopatraUIController() {
	}

	@PostConstruct
	private void initPageGenerator() {
		PageGenerator instance = PageGenerator.getInstance();
		instance.setURLSuffix(".clx");
	}

	@RequestMapping("/**/*.clx")
	public View index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.debug("########################");
		//return new InternalResourceView("/WEB-INF/jsp/exception/exception.jsp");
		// return new JstlView("");
		//return new UIView("app/exam/controls/Button.clx");
		return new UIView();
	}

	@RequestMapping("/**/*333.js")
	public View clxjs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.debug("1 # 1 #######################");
		return new InternalResourceView("/WEB-INF/jsp/exception/exception.jsp");
		// /ui/app/exam/controls/Button.clx.js
		//return new JstlView("");
		//return new UIView("app/exam/controls/Button.clx");
	}

	//@GetMapping(value = "/**/*222.js", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	//@RequestMapping(value = "/**/*.js")
	@ResponseBody
	//public @ResponseBody byte[] getFile() throws IOException {
	//public FileSystemResource getFile() throws IOException {
	public void getFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Instant start = Instant.now();
		
	    String uri = request.getRequestURI();
	    String url = request.getRequestURL().toString();
	    
	    LOGGER.debug("#### uri : " + uri);
	    LOGGER.debug("#### url : " + url);
	    
		LOGGER.debug("2 # 2 #######################");
		//InputStream in = getClass().getResourceAsStream("/ui/app/exam/controls/Button.clx.js");
		//InputStream in = getClass().getResourceAsStream("/js/EgovBBSMng.js");
		//InputStream in = CleopatraUIController.class.getResourceAsStream("/js/EgovBBSMng.js");
		//if (in == null) {
			//LOGGER.debug("#### in is null.");
		//}
		//String jsFile = "ui/app/exam/controls/Button.clx.js";
		String jsFile = uri.substring(1);
		
		if (uri.equals("/ui/app/exam/controls/Button.clx.js")) {
			jsFile = "ui/app/test/edu01.clx.js";
		}
		
		String absolutePath = context.getRealPath("/");
		LOGGER.debug("#### absolutePath : " + absolutePath);
		LOGGER.debug("#### " + request.getContextPath() + File.separator);
		if (!"".equals(request.getContextPath()) && absolutePath.endsWith(request.getContextPath().substring(1) + File.separator)) {
			absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf(request.getContextPath().substring(1) + File.separator));
		}
		
		File initialFile = new File(absolutePath + jsFile);
		if (!initialFile.exists()) {
			return;
		}
		
		Path path = Paths.get(absolutePath + jsFile);
        List<String> contentList = Files.readAllLines(path, StandardCharsets.UTF_8);
        StringJoiner sj = new StringJoiner("\r\n");
        int i = 0;
        for (String str : contentList) {
        	if (i < 15) {
        		if (uri.indexOf(".clx") > -1) {
        			sj.add(str.replace("app/test/edu01", uri.substring(4, uri.lastIndexOf(".clx"))));
        		} else {
        			sj.add(str);
        		}
        	} else {
        		sj.add(str);
        	}
        	i++;
        }
        
        InputStream is = new ByteArrayInputStream(sj.toString().getBytes(StandardCharsets.UTF_8));
        
	    //InputStream targetStream = new FileInputStream(initialFile);
	    //InputStream is = new FileInputStream(initialFile);
	    
	    byte[] file = IOUtils.toByteArray(is);
	    int DEFAULT_BUFFER_SIZE = 1024;
	    response.reset();
	    response.setBufferSize(DEFAULT_BUFFER_SIZE);
	    //response.setContentType("application/octet-stream"); //or whatever file type you want to send.
	    response.setContentType("application/javascript");
	    OutputStream os = response.getOutputStream();
	    try {
	    	os.write(file);
	    } catch (IOException e) {
	    	LOGGER.debug(e.getMessage());
		}
	    os.flush();
	    is.close();
	    os.close();

	    Instant finish = Instant.now();
	    long timeElapsed = Duration.between(start, finish).toMillis();
	    
	    LOGGER.debug("#### timeElapsed : " + timeElapsed);
	    
		//return IOUtils.toByteArray(targetStream);
		//return new FileSystemResource(initialFile);

//	    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(initialFile), "UTF-8"));
//	    response.reset();
//	    response.setContentType("application/javascript");
//	    OutputStreamWriter os = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
//	    char[] buff = new char[1024];
//	    //int readCount = 0;
//	    //String str = "";
//	    while (reader.read(buff, 0, 1024) != -1) {
//	    	os.write(buff);
//	    }
//	    os.flush();
//	    reader.close();
//	    os.close();
	}

	@RequestMapping(value = "/sessionC.do")
	@ResponseBody
	public void sessionC(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("str", "str");
	}
	
	@RequestMapping(value = "/sessionV.do")
	@ResponseBody
	public String sessionV(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String str = (String)session.getAttribute("str");
		return str;
	}
}
