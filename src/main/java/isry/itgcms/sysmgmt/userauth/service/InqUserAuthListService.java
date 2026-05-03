/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * 
 * @파일명        : InqUserAuthListService.java
 * @프로그램 설명 : 사용자별 권한 조회
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
public interface InqUserAuthListService {

	public Map<String, Object> selectUserAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectUserAuth2(String userId) throws Exception;
	
	public List<Map<String, Object>> selectUserAuthList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectAuthrtList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
