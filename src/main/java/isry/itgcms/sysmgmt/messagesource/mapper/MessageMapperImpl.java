package isry.itgcms.sysmgmt.messagesource.mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import isry.itgcms.sysmgmt.messagesource.vo.MessageVO;

@Mapper("messageMapper")
public class MessageMapperImpl implements MessageMapper {

	@Autowired
	@Resource(name="sqlSessionTemplate")
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	private static final String namespace = "isry.itgcms.sysmgmt.messagesource.mapper.MessageMapper";
	
	@Override
	public MessageVO selectMessage(String messageCode) throws Exception {
		return sqlSession.selectOne(namespace + ".selectMessage", messageCode);
	}

	@Override
	public List<MessageVO> selectMessages() throws Exception {
		List<MessageVO> list = sqlSession.selectList(namespace + ".selectMessages", null);
		return list;
	}

	@Override
	public void insertMessage(Map<String, String> map) throws Exception {
		sqlSession.insert(namespace + ".deleteBoardArticle", map);
	}

	@Override
	public int checkExistsMessage(String messageId) throws Exception {
		int count = sqlSession.selectOne(namespace + ".checkExistsMessage", messageId);
		return count;
	}

	@Override
	public void updateMessage(Map<String, String> map) throws Exception {
		sqlSession.update(namespace + ".updateMessage", map);
	}

	@Override
	public void deleteMessage(Map<String, String> map) throws Exception {
		sqlSession.delete(namespace + ".deleteMessage", map);
	}

}
