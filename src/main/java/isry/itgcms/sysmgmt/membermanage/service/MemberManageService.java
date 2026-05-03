/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.membermanage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MemberManageService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 4. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 4. 20.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MemberManageService {

	public List<Map<String, Object>> selectWorker(Map<String, Object> map) throws Exception;
	
	public Integer selectWorkerCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectYouthGuardian(Map<String, Object> map) throws Exception;
	
	public Integer selectYouthGuardianCount(Map<String, Object> map) throws Exception;

	
	// 청소년 보호자 등록
	public void saveYouthGuardian(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 보호자 등록
	public void saveInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 14세 미만 청소년 보호자 휴대폰 인증 SMS 인증 토큰 보내기
	public Map<String, String> saveYouthAuthSms(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 14세 미만 청소년 보호자 휴대폰 인증 토큰 일치여부 확인
	public Map<String, String> selectYouthAuthSmsToken(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 청소년, 보호자 삭제
	public Map<String, String> deleteYouthGuardian(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 담당자 정보 가져오기.
	public List<Map<String,Object>> getTargetOgdpWorkerList(String INST_NO, String TYPE) throws Exception;
}
