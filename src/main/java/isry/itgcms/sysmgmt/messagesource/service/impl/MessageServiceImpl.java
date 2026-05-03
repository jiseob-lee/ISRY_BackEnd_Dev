package isry.itgcms.sysmgmt.messagesource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.messagesource.mapper.MessageMapper;
import isry.itgcms.sysmgmt.messagesource.service.MessageService;
import isry.itgcms.sysmgmt.messagesource.vo.MessageVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("messageService")
public class MessageServiceImpl extends IsryBaseServiceImpl implements MessageService {

	@Resource(name = "messageMapper")
	private MessageMapper messageMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public MessageVO getMessageBundle(String messageCode, Locale locale) throws Exception {
		
		MessageVO resultVO = messageMapper.selectMessage(messageCode);

		log.info("#### messageCode : " + messageCode);
		
		if ("ko".equals(locale.getLanguage())) {
			resultVO.setValue((messageCode.startsWith("e1rror") ? messageCode + " : " : "") + resultVO.getMssageKlangCn());
		} else if ("en".equals(locale.getLanguage())) {
			resultVO.setValue((messageCode.startsWith("e1rror") ? messageCode + " : " : "") + resultVO.getMssageEnlCn());
		} else if ("zh".equals(locale.getLanguage())) {
			resultVO.setValue((messageCode.startsWith("e1rror") ? messageCode + " : " : "") + resultVO.getMssageChnlngCn());
		} else if ("ja".equals(locale.getLanguage())) {
			resultVO.setValue((messageCode.startsWith("e1rror") ? messageCode + " : " : "") + resultVO.getMssageJplngCn());
		} else {
			resultVO.setValue((messageCode.startsWith("e1rror") ? messageCode + " : " : "") + resultVO.getMssageKlangCn());
		}
		
		resultVO.setValue(StringEscapeUtils.unescapeJava(resultVO.getValue()));
		
		return resultVO;
	}
	
	@Override
	public List<MessageVO> selectMessages() throws Exception {
		List<MessageVO> resultList = messageMapper.selectMessages();
		return resultList;
	}
	
	@Override
	public Map<String, String> insertMessage(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmMessage");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		if (map.get("mssageId") == null || "".equals(map.get("mssageId"))) {
			resultMap.put("message", "메시지 ID 가 없습니다.");
			return resultMap;
		}
		
		int existsCount = messageMapper.checkExistsMessage(map.get("mssageId"));
		if (existsCount > 0) {
			//throw new Exception("메시지 ID 가 중복됩니다.");
			resultMap.put("message", "메시지 ID 가 중복됩니다.");
			return resultMap;
		}
		
		map.put("USER_ID", userId2);
		
		messageMapper.insertMessage(map);
		
		return resultMap;
	}
	
	@Override
	public Map<String, String> updateMessage(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmMessage");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		if (map.get("mssageId") == null || "".equals(map.get("mssageId"))) {
			resultMap.put("message", "메시지 ID 가 없습니다.");
			return resultMap;
		}

		int existsCount = messageMapper.checkExistsMessage(map.get("mssageId"));
		if (existsCount == 0) {
			//throw new Exception("메시지 ID 가 중복됩니다.");
			resultMap.put("message", "해당 메시지 ID 가 없습니다.");
			return resultMap;
		}
		
		map.put("USER_ID", userId2);
		
		messageMapper.updateMessage(map);
		
		return resultMap;
	}
	
	@Override
	public Map<String, String> deleteMessage(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmMessage");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		if (map.get("mssageId") == null || "".equals(map.get("mssageId"))) {
			resultMap.put("message", "메시지 ID 가 없습니다.");
			return resultMap;
		}

		int existsCount = messageMapper.checkExistsMessage(map.get("mssageId"));
		if (existsCount == 0) {
			//throw new Exception("메시지 ID 가 중복됩니다.");
			resultMap.put("message", "해당 메시지 ID 가 없습니다.");
			return resultMap;
		}
		
		map.put("USER_ID", userId2);
		
		messageMapper.deleteMessage(map);
		
		return resultMap;
	}
}
