/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.service;

import java.util.List;
import java.util.Map;

public interface MnthngSchdlService {
	
	
	List<Map<String, Object>> consultantSrch(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboDept(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboHrWork(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectCombo1ListMnthng(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthngSchdlList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthngSchdlList01(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthngSchdlList02(Map<String, Object> mapParam) throws Exception;
	
	
	List<Map<String, Object>> searchComboOptionTimes(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> getRowCnt(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectYmdSchdlExmpl(Map<String, Object> mapParam) throws Exception;
	
	int insertMnthngSchdl(Map<String, String> mapParam) throws Exception;
	
	int insertMnthngSchdlOut(Map<String, String> mapParam) throws Exception;
	
	int updateMnthngSchdlDetail(Map<String, Object> mapParam) throws Exception;
	
	int deleteMnthngSchdlDetail(Map<String, Object> mapParam) throws Exception;
	
	int deleteDaySchdl(Map<String, Object> mapParam) throws Exception;
	
	int insertDaySchdl(Map<String, String> mapParam) throws Exception;
	
	int insertDaySchdlOut(Map<String, String> mapParam) throws Exception;	
	
	List<Map<String, Object>> getTimesForExclDown(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthForExclDown(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectAllMemberDeptcd(Map<String, Object> mapParam) throws Exception;
	
}
