/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DisconnectUserMapper.java
 * @프로그램 설명 : 사용자 접속 차단
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 3. 31. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 3. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("disconnectUserMapper")
public interface DisconnectUserMapper {
	
	public List<Map<String, Object>> selectDisconnectUser(Map<String, Object> dmSearchMap) throws Exception;
	
	public void saveDisconnectUser(Map<String, String> map) throws Exception;
	
	public Integer selectDisconnectUserCount(Map<String, Object> map) throws Exception;
	
	public Map<String, String> selectDisconnectUserInfo(String userId) throws Exception;
	
	// 차단 해제할 때 다시 새벽 스케줄러에서 차단되는 것을 방지하기 위하여 로그인 기록을 넣음.
	public void insetLoginHistory(String userId) throws Exception;
}
