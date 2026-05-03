/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : BbsmomListService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */
public interface BbsgomListService {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	//고민해결백과(댓글)본인상담
	List<Map<String, Object>> selectBbsgomList(Map<String, Object> mapParam) throws Exception;
	
	//고민해결백과(댓글)답변완료
	List<Map<String, Object>> selectBbsgomListY(Map<String, Object> mapParam) throws Exception;
	
	//고민해결백과(댓글)미답변
	List<Map<String, Object>> selectBbsgomListN(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsgomDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveCounselor(HttpServletRequest request, DataRequest dataRequest);
	
	List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveBbsgom(HttpServletRequest request, DataRequest dataRequest);
	
////	-----------------------------------------------------------댓글
	List<Map<String, Object>> selectRplyDetail(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveRply(HttpServletRequest request, DataRequest dataRequest);
	
//	-----------------------------------------상담사메모
	void insertMemo(Map<String, Object> mapParam);
}









