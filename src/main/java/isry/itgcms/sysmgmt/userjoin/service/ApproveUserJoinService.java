package isry.itgcms.sysmgmt.userjoin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface ApproveUserJoinService {
	
	public List<Map<String, Object>> selectUserJoin(Map<String, Object> dmSearchMap) throws Exception;
	
	public Integer selectUserJoinCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public void saveGiveBackUserJoin(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void saveUserJoin(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthInfo
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 25. 
	 * @Method설명 : 사용자 권한 정보 조회 (회원가입승인 팝업)
	 */
	public List<Map<String, Object>> selectUserAuthInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
