/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userjoin.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : ReqUserJoinMapper.java
 * @프로그램 설명 : 회원 가입
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("reqUserJoinMapper")
public interface ReqUserJoinMapper {
	
	public List<Map<String, Object>> selectQualifyClass() throws Exception;
	
	public Map<String, Integer> checkIdDuplicate(DataRequest dataRequest) throws Exception;
	
	public Integer selectIdExistsCount(String id) throws Exception;
	
	public String selectPersonalIdNum(String userId) throws Exception;
	
	public String getWorkerId(String userId) throws Exception;
	
	public void insertWorker(Map<String, Object> map) throws Exception;
	
	public void insertWorkerHistory(Map<String, Object> map) throws Exception;
	
	public void insertPersonalBasicInfo(Map<String, Object> map) throws Exception;
	
	public void insertUserInfo(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectOrgRegion(Map<String, Object> paramMap) throws Exception;

	public void insertUnitSystem(Map<String, Object> map) throws Exception;

	public List<Map<String, String>> selectSiGunGu(Map<String, String> map) throws Exception;

	// 개인정보 처리방침 재동의 처리
	public void saveReconsent(Map<String, String> paramMap) throws Exception;

	// CI 등록개수 조회
	public int selectCiCount(String CI) throws Exception;
	
	// 본인을 제외한 CI 등록 건수 조회
	public int selectCiCount2(Map<String, String> map) throws Exception;

	// 간편인증 CI 등록개수 조회
	public int selectCiSimpleCount(String CI) throws Exception;
	
	// 간편인증 본인을 제외한 CI 등록 건수 조회
	public int selectCiSimpleCount2(Map<String, String> map) throws Exception;

	// 공동인증서 등록개수 조회
	public int selectCertificateCount(String signerDN) throws Exception;
	
	/**
	 * @Method명   : getGrpAuthrtSeCd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 그룹권한코드
	 */
	public Map<String, Object> getGrpAuthrtSeCd (int isntNo) throws Exception;
	
	/**
	 * @Method명   : getAuthrtSeCd
	 * @param isntNo
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 권한구분코드
	 */
	public String getAuthrtSeCd (Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectAuthSeCd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 권한구분코드
	 */
	public List<Map<String, Object>> selectAuthSeCd (Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertUserInstAuth
	 * @param map
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 사용자별 기관 권한
	 */
	public void insertUserInstAuth(Map<String, Object> paramMap) throws Exception;

	// 종사자 테이블에서 전화번호 같은 것 개수 구함
	public Integer selectSamePhoneCount(String phone) throws Exception;
	
	// 종사자 테이블에서 이메일 같은 것 개수 구함
	public Integer selectSameEmailCount(String email) throws Exception;
	
}
