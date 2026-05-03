/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userjoin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : ReqUserJoinService.java
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
public interface ReqUserJoinService {
	
	public List<Map<String, Object>> selectQualifyClass() throws Exception;
	
	public Map<String, Integer> checkIdDuplicate(DataRequest dataRequest) throws Exception;
	
	//public Map<String, String> saveMember(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> saveWorker(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> saveInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectOrgRegion(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> selectSiGunGu(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectAuthSeCd(DataRequest dataRequest) throws Exception;

	public void saveReconsent(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Integer> checkPhoneDuplicate(HttpServletRequest request) throws Exception;
	
	public Map<String, Integer> checkCertificateDuplicate(HttpServletRequest request) throws Exception;
	
	public Map<String, Integer> checkSimpleDuplicate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
