/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : UserLoginMapper.java
 * @프로그램 설명 : 통합 로그인
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 6. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 6.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("userLoginMapper")
public interface UserLoginMapper {
	
	public UserDetailsVO userLogin(UserDetailsVO userDetailsVO) throws Exception;
	
	public void loginLog(UserDetailsVO userDetailsVO) throws Exception;
	
	public void logoutLog(UserDetailsVO userDetailsVO) throws Exception;

	public int registCertificate(Map<String, String> map) throws Exception;

	public int registFinanceCertificate(Map<String, String> map) throws Exception;

	public int deleteCertificate(Map<String, String> map) throws Exception;

	public int deleteFinanceCertificate(Map<String, String> map) throws Exception;
	
	public int updateCertificateHistory(Map<String, String> map) throws Exception;
	
	public int updateFinanceCertificateHistory(Map<String, String> map) throws Exception;

	public String selectLoginIdFromCertificate(String signerDN) throws Exception;

	public String selectLoginIdFromFinanceCertificate(String signerDN) throws Exception;
	
	// 기존 비밀번호 일치 여부 확인
	public Integer selectCurrentPassword(Map<String, String> map) throws Exception;

	public int selectExistsCertificateCount1(Map<String, String> map) throws Exception;

	public int selectExistsFinanceCertificateCount1(Map<String, String> map) throws Exception;
	
	public int selectExistsCertificateCount2(Map<String, String> map) throws Exception;
	
	public int selectExistsFinanceCertificateCount2(Map<String, String> map) throws Exception;

	// 비밀번호 변경
	public void savePassword(Map<String, String> map) throws Exception;
	
	// 비밀번호 신규 설정
	public void savePasswordNew(Map<String, String> map) throws Exception;

	// 로그인 오류 회수 초기화
	public void resetLoginErrorCount(String loginId) throws Exception;
	
	// 로그인 오류 회수 증가
	public void increaseLoginErrorCount(String loginId) throws Exception;

	// 로그인 오류 회수 조회
	public Integer selectLoginErrorCount(String loginId) throws Exception;

	// 로그인 오류 허용 회수 조회
	public Integer selectLoginErrorPermitCount() throws Exception;

	// 현재와 이전 패스워드를 가져온다.
	public Map<String, String> selectPasswordMap(String userId) throws Exception;
	
	// 패스워드 변경일시를 최신으로 설정한다.
	public void updatePasswordChangeDate(String userId) throws Exception;

	// 이메일로 아이디 찾기
	public List<String> findIdEmail(Map<String, String> map) throws Exception;
	
	// 휴대폰으로 아이디 찾기
	public List<String> findIdPhone(Map<String, String> map) throws Exception;
	
	// 임시 비밀번호 세팅
	public Integer setPasswordTemporary(Map<String, String> map) throws Exception;

	// 아이디 존재 여부 체크
	public Integer selectUserIdCount(Map<String, String> map) throws Exception;

	// 회원 로그인 상태 조회
	public Map<String, String> selectUserLoginStatus(String loginId) throws Exception;

	// 휴대폰 인증 등록
	public int registPhone(Map<String, String> map) throws Exception;
	
	// 휴대폰 인증 삭제
	public int deletePhone(Map<String, String> map) throws Exception;
	
	// 휴대폰 인증 관리 내역 저장
	public int updatePhoneHistory(Map<String, String> map) throws Exception;
	
	// CI 로 부터 아이디 구하기
	public String selectLoginIdFromCI(String signerDN) throws Exception;
	

	// 간편 인증 등록
	public int registSimple(Map<String, String> map) throws Exception;
	
	// 간편 인증 삭제
	public int deleteSimple(Map<String, String> map) throws Exception;
	
	// 간편 인증 관리 내역 저장
	public int updateSimpleHistory(Map<String, String> map) throws Exception;

	// 간편인증 CI 로 부터 아이디 구하기
	public String selectLoginIdFromCISimple(String signerDN) throws Exception;
	
	// 세션 종료 메시지 가져오기
	public String selectSessionExpireMessage(String ip) throws Exception;
	
	// 세션 종료 메시지 입력하기
	public int insertSessionExpireMessage(Map<String, String> map) throws Exception;
	
	// 세션 종료 메시지 삭제하기
	public void deleteSessionExpireMessage(String ip) throws Exception;


	// 개인정보 처리방침 재동의 구하기
	public String selectPrivacySchedule(Map<String, Object> map) throws Exception;

	// 비밀번호 변경 주기에 비밀번호 변경하기
	public Integer selectChangePassword(Map<String, Object> map) throws Exception;
	
	// 본인의 등록된 2차 인증 목록(수단) 구하기
	public Map<String, String> selectSecondAuthList(String loginId) throws Exception;

	// 아이디, 비밀번호 확인
	public Map<String, Object> selectIdPwCheck(Map<String, String> paramMap) throws Exception;

	// 차단 여부 확인
	public Map<String, Object> selectIdPwCheck2(Map<String, String> paramMap) throws Exception;

	// 운영 로그인 권한 체크
	public List<Map<String, Object>> selectGovLoginCheck(Map<String, String> paramMap) throws Exception;

	// 비밀번호 일치여부 확인
	public int selectPasswordEquals(Map<String, String> map) throws Exception;
	
}
