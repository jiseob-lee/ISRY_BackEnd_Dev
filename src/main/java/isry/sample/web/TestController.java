package isry.sample.web;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import isry.sample.service.MemberTestVO;

@Controller
@RequestMapping(value = "/coung")
public class TestController {
	
	@Autowired
	private MessageSourceAccessor messageSource;
    
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private static String message = "";
    
	
    @RequestMapping("/loginTest.do")
    public String moveTestJsp(Model model) {
    	//빈 객체 넘겨줘야함.
    	model.addAttribute("memberTestVO", new MemberTestVO());
    	return "/egovframework/example/sample/loginTest";
    }
	
    /* 처리 process
     * 1. return : @ResponseBody Map<String, Object>
     * 2. request / @RequestParam Map<?, ?>
     * 3. Map<String, Object> resultMap
     * 4. 메세지 : messageSource.getMessage ==> DB 호출
    */
    @RequestMapping(value = "/loginProcessTest.do")
    public @ResponseBody Map<String, Object> boardInsert(@Valid MemberTestVO memberTestVO, HttpServletRequest request,@RequestParam Map<?, ?> commandMap) throws Exception {
    	
    	String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		log.info("id="+id);
		log.info("pw="+pw);
		
		log.info("id="+commandMap.get("id"));
		log.info("pw="+commandMap.get("pw"));

		Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("result", messageSource.getMessage("ISRY-CRM-M001", message));
    	resultMap.put("message",messageSource.getMessage("ISRY-CRM-M002", message));
    	resultMap.put("flag", messageSource.getMessage("ISRY-CRM-M003", message));

    	return resultMap;
    }
    
    /* 처리 process
     * 1. return : ModelAndView
     * 2. request / @RequestParam Map<?, ?>
     * 3. HashMap paramMap  / List<EgovMap>
     * 4. 메세지 : messageSource.getMessage ==> DB 호출
     * 5. View Resolve : jsonView
    */
    @RequestMapping(value = "/jsonTest.do")
    public ModelAndView JsonTest(@Valid MemberTestVO memberTestVO,BindingResult br, @RequestParam Map<?, ?> commandMap) throws Exception {
    	
		log.info("id="+commandMap.get("id"));
		log.info("pw="+commandMap.get("pw"));
    	
		ModelAndView modelAndView = new ModelAndView();
		
		HashMap paramMap = new HashMap();
		paramMap.put("id", commandMap.get("id"));
		paramMap.put("pw", commandMap.get("pw"));
		
		//List<EgovMap> resultList = loginService.selectList(paramMap);
		
		String errMsg="";
		if(br.hasErrors()) {
		      List<FieldError> errors = br.getFieldErrors();
		      for(FieldError err : errors) {
		    	  log.info("ObjectName:" + err.getObjectName() + "\tFieldName:" + err.getField()
		            + "\tFieldValue:" + err.getRejectedValue() + "\tMessage:" + err.getDefaultMessage());
		        
		        String errMsg1 = err.getDefaultMessage();
		        errMsg = errMsg + errMsg1+"\n";
		      }
		      
		      modelAndView.addObject("result", "로그인 규칙 실패");
		      modelAndView.addObject("message", errMsg);
		      modelAndView.addObject("flag", "0");
		      
		} else {
		modelAndView.addObject("result", "로그인 규칙 성공");
		modelAndView.addObject("message", "로그인 test");
		modelAndView.addObject("flag",    "1");
		}
		
		modelAndView.setViewName("jsonView");

		return modelAndView;
    }
    
}
