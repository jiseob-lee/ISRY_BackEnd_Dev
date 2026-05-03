package isry.itgcms.sysmgmt.messagesource.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.messagesource.service.MessageService;
import isry.itgcms.sysmgmt.messagesource.vo.MessageVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/message")
public class DatabaseMessageController extends IsryBaseController {
	
	@Resource(name = "messageService")
	private MessageService messageService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/selectMessages.do")
	public View selectMessages(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<MessageVO> list = messageService.selectMessages();
		
		dataRequest.setResponse("dsList", list);
		
		dataRequest.setResponse("dsUseYn", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/insertMessage.do")
	public View insertMessage(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmResult", messageService.insertMessage(request, dataRequest));
		
		List<MessageVO> list = messageService.selectMessages();
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/updateMessage.do")
	public View updateMessage(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmResult", messageService.updateMessage(request, dataRequest));
		
		List<MessageVO> list = messageService.selectMessages();
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/deleteMessage.do")
	public View deleteMessage(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmResult", messageService.deleteMessage(request, dataRequest));
		
		List<MessageVO> list = messageService.selectMessages();
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}
		
}
