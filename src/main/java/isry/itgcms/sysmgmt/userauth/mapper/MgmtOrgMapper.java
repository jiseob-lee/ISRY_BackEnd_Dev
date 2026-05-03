/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtOrgMapper.java
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
@Mapper("mgmtOrgMapper")
public interface MgmtOrgMapper {
	
	public void saveOrg(Map<String, String> map) throws Exception;

	public void deleteOrgUnitSystem(Map<String, String> map) throws Exception;
	
	public void insertOrgUnitSystem(Map<String, String> map) throws Exception;
	
	public Map<String, Object> selectMaxInstCd() throws Exception;

	// 기관 승인
	public void saveApproveInstitute(Map<String, Object> map) throws Exception;
	
	// 기관 반려
	public void saveRejectInstitute(Map<String, Object> map) throws Exception;
	
	// 기관 승인 처리. 20230426 Taesoo Song.
	public void saveApproveOrgChangeData(Map<String, Object> map) throws Exception;

	// 기관신청 상태값 변경 20230426 Taesoo Song.
	public void updateApproveOrgStatus(Map<String, Object> map) throws Exception;
	
	public void updateUsingSealInfo(Map<String, Object> map) throws Exception;

	public void updateRejectInstituteOffer(Map<String, Object> map) throws Exception;
}
