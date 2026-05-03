/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.dashboard.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MainDashboardService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 11. 08. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 11. 08.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MainDashboardService {
	
	public List<Map<String, Object>> selectMainDashboard(HttpServletRequest request ,DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectDocsCommonList(HttpServletRequest request ,DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectLinkList(HttpServletRequest request ,DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectWlfarLinkList(HttpServletRequest request ,DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectInnerEmlList(HttpServletRequest request ,DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectCaseList(HttpServletRequest request ,DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectMenuId(String untTaskwkSeCd) throws Exception;
	
	public Map<String, Object> selectJbps(HttpServletRequest request ,DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectDca010(HttpServletRequest request) throws Exception;
	
	public void saveDca010(HttpServletRequest request, Map<String, Object> map) throws Exception;
}
