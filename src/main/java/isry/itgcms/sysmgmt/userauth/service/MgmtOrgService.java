/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MgmtOrgService.java
 * @프로그램 설명 : 기관 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 1. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 1.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MgmtOrgService {
	
	public void saveOrg(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void saveOrgUnitSystem(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectMaxInstCd() throws Exception;

	// 기관 승인
	public void saveApproveInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 기관 반려
	public void saveRejectInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
}
