package isry.itgcms.sysmgmt.messagesource.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import isry.itgcms.sysmgmt.messagesource.vo.MessageVO;

//@Mapper("messageMapper")
public interface MessageMapper {
	
	public MessageVO selectMessage(String messageCode) throws Exception;
	
	public List<MessageVO> selectMessages() throws Exception;
	
	public void insertMessage(Map<String, String> map) throws Exception;
	
	int checkExistsMessage(String messageId) throws Exception;
	
	public void updateMessage(Map<String, String> map) throws Exception;
	
	public void deleteMessage(Map<String, String> map) throws Exception;
	
}
