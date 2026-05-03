/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : UserLoginService.java
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
public interface UserLoginService {
	
	public Map<String, Object> processUserLogin(DataRequest dataRequest, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public Map<String, Object> processUserLogin3(String userId, String userPw, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public Map<String, Object> processUserLogin2(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public Map<String, Object> processUserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public void processLogoutLog(HttpServletRequest request) throws Exception;

	public Map<String, Object> processRegistCertificate(HttpServletRequest request) throws Exception;

	public Map<String, Object> processRegistFinanceCertificate(HttpServletRequest request) throws Exception;
	
	public Map<String, Object> processLoginCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public Map<String, Object> processLoginFinanceCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public Map<String, Object> deleteCertificate(HttpServletRequest request) throws Exception;

	public Map<String, Object> deleteFinanceCertificate(HttpServletRequest request) throws Exception;
	
	public String savePassword(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public String savePasswordNew(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception;

	public void updatePasswordChangeDate(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 아이디 찾기
	public Map<String, String> processFindId(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 아이디 찾기 2
	public Map<String, String> processFindId2(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 비밀번호 찾기
	public Map<String, String> processFindPw(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 비밀번호 찾기 2
	public Map<String, String> processFindPw2(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 휴대폰 인증 등록
	public Map<String, Object> processRegistPhone(HttpServletRequest request) throws Exception;
	
	// 휴대폰 인증 삭제
	public Map<String, Object> deletePhone(HttpServletRequest request) throws Exception;
	
	// 휴대폰 인증 로그인
	public Map<String, Object> processLoginPhone(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 간편 인증 등록
	public Map<String, Object> processRegistSimple(HttpServletRequest request) throws Exception;
	
	// 간편 인증 삭제
	public Map<String, Object> deleteSimple(HttpServletRequest request) throws Exception;
	
	// 간편 인증 로그인
	public Map<String, Object> processLoginSimple(HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 세션 종료 메시지 가져오기
	public String selectSessionExpireMessage(String ip) throws Exception;

	// 로그인 세션 VO 가져오기
	public UserDetailsVO getLoginSessionVO(HttpServletRequest request) throws Exception;

	// 개인정보 처리방침 재동의 구하기
	public String selectPrivacySchedule(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 비밀번호 변경 주기에 비밀번호 변경하기
	public String selectChangePassword(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 본인의 등록된 2차 인증 목록(수단) 구하기
	public Map<String, String> selectSecondAuthList(HttpServletRequest request) throws Exception;
	
	// 아이디, 비밀번호 확인
	public Map<String, Object> selectIdPwCheck(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 사용자 회원 상태 구하기
	public Map<String, Object> selectUserStatus(String loginId) throws Exception;
	
	// 패스워드 일치여부 확인
	public boolean selectPasswordEquals(String pass1, String pass2) throws Exception;
	
}
