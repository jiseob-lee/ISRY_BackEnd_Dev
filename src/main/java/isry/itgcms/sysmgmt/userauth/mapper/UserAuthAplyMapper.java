/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : UserAuthAplyMapper.java
 * @프로그램 설명 : 사용자 권한 신청
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 20. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("userAuthAplyMapper")
public interface UserAuthAplyMapper {

	/**
	 * @Method명   : selectAplyInstList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 신청 기관 목록 조회
	 */
	List<Map<String, Object>> selectAplyInstList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectRgnSidoCombo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 지역 (시/도) 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectRgnSidoCombo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectRgnSggCombo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 지역 (시/군/구) 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectRgnSggCombo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectRgnInstCombo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 지역 (기관) 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectRgnInstCombo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectInstDeptCombo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 기관 부서 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectInstDeptCombo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectUserAuthAplyDuplicateCheck
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 25. 
	 * @Method설명 : 사용자별 권한 신청 중복 체크
	 */
	Long selectUserAuthAplyDuplicateCheck(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertUserAuthAply
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 사용자별 권한 신청 등록
	 */
	int insertUserAuthAply(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectAprvAdminInstList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 9. 
	 * @Method설명 : 승인관리자 기관 목록 조회
	 */
	List<Map<String, Object>> selectAprvAdminInstList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthAplyCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 사용자별 권한 신청 총 건수 조회
	 */
	Long selectUserAuthAplyCount(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthAplyList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 사용자별 권한 신청 목록 조회
	 */
	List<Map<String, Object>> selectUserAuthAplyList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserInfoByAuthAplyDetails
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 8. 
	 * @Method설명 : 사용자정보 조회 (권한 승인 요청 상세)
	 */
	List<Map<String, Object>> selectUserInfoByAuthAplyDetails(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthAplyDetails
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자별 권한 신청 상세 조회
	 */
	List<Map<String, Object>> selectUserAuthAplyDetails(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNtcnSendReceiverInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 알림 전송 수신자 정보 조회
	 */
	List<Map<String, Object>> selectNtcnSendReceiverInfo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthAplyAprvStatus
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 25. 
	 * @Method설명 : 사용자별 권한 신청 승인상태 조회
	 */
	List<Map<String, Object>> selectUserAuthAplyAprvStatus(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateUserAuthAplyByApproval
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사용자별 권한 신청 승인 처리
	 */
	int updateUserAuthAplyByApproval(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateUserAuthAplyByReject
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 사용자별 권한 신청 반려 처리
	 */
	int updateUserAuthAplyByReject(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateUserAuthAplyByCancel
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 6. 
	 * @Method설명 : 사용자별 권한 신청 취소 처리
	 */
	int updateUserAuthAplyByCancel(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteUserAuthAplyByUserId
	 * @param userId 사용자아이디
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 사용자별 권한 신청 삭제
	 */
	int deleteUserAuthAplyByUserId(String userId) throws Exception;
	
	/**
	 * @Method명   : selectWorkerInfoByChgOgdpInst
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 종사자 정보 조회 (소속기관 변경 유무 체크)
	 */
	List<Map<String, Object>> selectWorkerInfoByChgOgdpInst(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectInstDetails
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 16. 
	 * @Method설명 : 기관 상세 조회 (전용가입 구분 체크)
	 */
	List<Map<String, Object>> selectInstDetails(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateWorkerInfoByUserAuthAply
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 종사자 정보 수정 (권한 신청 승인)
	 */
	int updateWorkerInfoByUserAuthAply(Map<String, Object> mapParam) throws Exception;
	
}
