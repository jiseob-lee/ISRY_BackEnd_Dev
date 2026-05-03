/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.onlineCnttMng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface GriefSltnAlkoolService {
	
	public List<Map<String, Object>> selectGriefSltnAlkoolThemaList(DataRequest dataRequest) throws Exception;
	
	public Map<String, String> griefSltnAlkoolThemaInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> griefSltnAlkoolInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectGriefSltnAlkoolThemaUpdate(DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectGriefSltnAlkoolUpdate(DataRequest dataRequest) throws Exception;
	
	public Map<String, String> griefSltnAlkoolThemaDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> griefSltnAlkoolDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> griefSltnAlkoolThemaUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> griefSltnAlkoolUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectGriefSltnAlkool(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectGriefSltnAlkoolList(DataRequest dataRequest) throws Exception;
}
