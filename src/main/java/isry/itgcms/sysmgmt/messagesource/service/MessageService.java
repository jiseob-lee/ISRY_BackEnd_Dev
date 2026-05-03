package isry.itgcms.sysmgmt.messagesource.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcms.sysmgmt.messagesource.vo.MessageVO;

public interface MessageService {
	
	public MessageVO getMessageBundle(String messageCode, Locale locale) throws Exception;
	
	public List<MessageVO> selectMessages() throws Exception;
	
	public Map<String, String> insertMessage(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, String> updateMessage(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> deleteMessage(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
