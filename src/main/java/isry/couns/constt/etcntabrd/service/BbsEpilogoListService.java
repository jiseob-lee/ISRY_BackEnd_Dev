/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface BbsEpilogoListService {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoDetail(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveBbsEpilogoProc(HttpServletRequest request, DataRequest dataRequest);
	
	//답글
	List<Map<String, Object>> selectBbsEpilogoRplyDetail(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectBbsEpilogoRplyDetail2(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveBbsEpilogoReProc(HttpServletRequest request, DataRequest dataRequest);
	
	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	void saveCounselor(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectMemo(Map<String, Object> mapParam);
	
	Map<String, Object> saveBbsEpilogoAll(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : insertEpilgMemo
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 26. 
	 * @Method설명 : 사이버상담후기 메모 저장/수정
	 */
	public void insertEpilgMemo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
