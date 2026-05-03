package isry.itgcms.sysmgmt.userjoin.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("approveUserJoinMapper")
public interface ApproveUserJoinMapper {
	
	public List<Map<String, Object>> selectUserJoin(Map<String, Object> dmSearchMap) throws Exception;
	
	public Integer selectUserJoinCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public void saveGiveBackUserJoin(Map<String, String> map) throws Exception;
	
	public void saveUserJoin(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : updateWorkerInfoByUserJoin
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 30. 
	 * @Method설명 : 종사자 정보 수정 (회원가입승인)
	 */
	public int updateWorkerInfoByUserJoin(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 25. 
	 * @Method설명 : 사용자 권한 정보 조회 (회원가입승인 팝업)
	 */
	public List<Map<String, Object>> selectUserAuthInfo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateUserInstAuthrtByUserJoin
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 17. 
	 * @Method설명 : 기관 권한 업데이트 (회원가입승인)
	 */
	public int updateUserInstAuthrtByUserJoin(Map<String, Object> mapParam) throws Exception;

	// 사용자별 기관 권한 (SAB230) 사용자 삭제시 함께 삭제
	public void deleteSAB230(String userId) throws Exception;

	// 사용자별 메뉴 권한 (SAB250) 사용자 삭제시 함께 삭제
	public void deleteSAB250(String userId) throws Exception;
	
	// 회원 가입 신청 정보 조회
	public Map<String, String> selectUserJoinInfo(String userId) throws Exception;
	
}
