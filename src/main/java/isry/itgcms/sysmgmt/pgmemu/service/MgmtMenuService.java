/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.pgmemu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface MgmtMenuService {
	
	public Map<String, Object> selectMenu(HttpServletRequest request) throws Exception;
	
	public void saveMenu(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Integer> selectMaxMenuId() throws Exception;
	
	public List<Map<String, Object>> selectRootMenu() throws Exception;

	public String selectTopMenuCd(String topMenuId) throws Exception;

	
	// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가 : 메뉴 템플릿 수정, 개별 메뉴 수정, 권한 수정 
	public void increaseMenuUpdateCountByRightId(String rightId) throws Exception;
	public void increaseMenuUpdateCountByUserId(String userId) throws Exception;
	
}
