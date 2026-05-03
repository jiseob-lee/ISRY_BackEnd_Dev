/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : MgmtGrpAuthMapper.java
 * @프로그램 설명 : 그룹별 권한 저장 매퍼
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 29.
 * @수정내용      : 
 * -                
 * -
 */

@Mapper("mgmtGrpAuthMapper")
public interface MgmtGrpAuthMapper {
	
	public List<Integer> getGrpAuthExists(Map<String, Object> paramMap) throws Exception;
	
	public void saveGrpAuth(Map<String, Object> map) throws Exception;
	
	public void saveGrpAuthHistory(Map<String, Object> map) throws Exception;
	
	public void deleteGrpAuth(Map<String, Object> paramMap) throws Exception;
	
	public void deleteGrpAuthAll(Map<String, String> paramMap) throws Exception;
	
	public void saveGroupDetailAuths(Map<String, Object> paramMap) throws Exception;
	
	public void saveGroupDetailAuthsHistory(Map<String, Object> paramMap) throws Exception;
	
	public void deleteUnavailGrpAuth() throws Exception;
	
	public void saveGrpAuthAll(Map<String, Object> map) throws Exception;

	// 그룹 권한을 갖고 있는 사람 목록을 구한다.
	public List<String> selectGrpAuthPersons(String authrtSeCd) throws Exception;
	
	// 시스템 관리 권한을 갖고 있는 사람 목록을 구한다.
	public List<String> selectGrpAuthPersonsSysMgr() throws Exception;
		
	// 수정된 그룹 권한을 개인들에게 적용한다.
	public void savePersonalGrpAuth(Map<String, String> paramMap) throws Exception;

	// 수정된 그룹 권한을 개인들에게 적용한 이력을 기록한다.
	public void savePersonalGrpAuthHistory(Map<String, String> paramMap) throws Exception;
	
	// 메뉴 권한 목록을 구한다.
	public List<Map<String, String>> selectMenuAuths(Map<String, String> paramMap) throws Exception;
	
	// 메뉴 권한 존재 여부를 구한다.
	public Integer selectMenuAuthCount(Map<String, String> paramMap) throws Exception;
	
	// 개인 메뉴 권한을 추가 입력한다.
	public void insertPersonalGrpAuth(Map<String, Object> paramMap) throws Exception;
	
	// 개인 메뉴 권한 변경 이력을 기록한다.
	public void insertPersonalGrpAuthHistory(Map<String, Object> paramMap) throws Exception;

	// 개인 메뉴 이력을 수정한다.
	public void updatePersonalGrpAuth(Map<String, Object> paramMap) throws Exception;

	// 개인 메뉴 권한을 삭제한다.
	public void deleteMenuAuths(Map<String, Object> paramMap) throws Exception;

	// 다른 권한에 메뉴가 존재하는 것 카운트
	public Integer selectExistsMenuAuth(Map<String, Object> paramMap) throws Exception; 
}
