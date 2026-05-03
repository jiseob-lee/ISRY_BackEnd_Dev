/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.taskwkaltmntmng.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

public interface CnnctChatReqstdService {
	
	List<Map<String, Object>> selectCnnctChatReqstdList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnnctChatReqstdDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnnctChatReqstdDetailInfo(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnnctChatReqstdExpInfo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCnnctChatRcritTrgtInfo
	 * @param 	   : mapParam
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 30. 
	 * @Method설명 : 잇는채팅모집대상구분(AYB143) 목록 조회
	 */
	List<Map<String, Object>> selectCnnctChatRcritTrgtInfo(Map<String, Object> mapParam) throws Exception;
	
	int processCnnctChatReqstd(Map<String, Object> mapParam) throws Exception;
	
	int updateCnnctChatReqstd(Map<String, Object> mapParam) throws Exception;
	
	int updateFileAffi(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboOption() throws Exception;
	
}
