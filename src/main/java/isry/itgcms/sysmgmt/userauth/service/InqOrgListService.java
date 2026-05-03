/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : InqOrgListService.java
 * @프로그램 설명 : 기관 조회
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
public interface InqOrgListService {
	
	public List<Map<String, String>> selectOrg(DataRequest dataRequest) throws Exception;
	
	public Integer selectOrgCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectOrgPaging(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectOrgDetail(DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectOrgDetailHistoryData(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> selectInstituteType() throws Exception;
	
	public List<Map<String, String>> selectOrgName(DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectOrgRestArea(DataRequest dataRequest) throws Exception;

	//public List<Map<String, Object>> selectNewInstituteList(DataRequest dataRequest) throws Exception;
	/**
	 * @Method명   : selectNewInstituteList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 13. 
	 * @Method설명 : 기관 추가 신청 목록 조회 
	 */
	public List<Map<String, Object>> selectNewInstituteList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> resrceSelectOrg(DataRequest dataRequest) throws Exception;
	
	public Integer selectOrgAuthryCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectOrgAuthryPaging(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectOrgDetailHistory(DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectOrgRenameDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectOrgList(DataRequest dataRequest) throws Exception;
}
