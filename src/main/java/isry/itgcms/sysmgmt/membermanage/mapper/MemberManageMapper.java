/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.membermanage.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MemberManageMapper.java
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
@Mapper("memberManageMapper")
public interface MemberManageMapper {
	
	public List<Map<String, Object>> selectWorker(Map<String, Object> map) throws Exception;
	
	public Integer selectWorkerCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectYouthGuardian(Map<String, Object> map) throws Exception;
	
	public Integer selectYouthGuardianCount(Map<String, Object> map) throws Exception;
	
	// 청소년 보호자 등록
	public void saveYouthGuardian(Map<String, Object> map) throws Exception;
	public void saveYouthGuardianHistory(Map<String, Object> map) throws Exception;
	
	// 청소년 보호자 번호 채번
	public String selectYngbgsPrtcrNo(String userId) throws Exception;

	// 기관 정보 저장
	public void saveInstitute(Map<String, Object> map) throws Exception;
	
	// 기관에 속한 종사자 정보 가져오기.
	public List<Map<String, Object>> getTargetOgdpWorkerList(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getInstOgdpWorkerInfo(Map<String, Object> map) throws Exception;
}
