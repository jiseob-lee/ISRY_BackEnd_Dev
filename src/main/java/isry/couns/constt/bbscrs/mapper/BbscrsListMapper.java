/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbscrs.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : BbsonmMapper.java
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

@Mapper("BbscrsListMapper")
public interface BbscrsListMapper {
	
	List<Map<String , Object>> selectBbscrsList1(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbscrsList2(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbscrsList3(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbscrsList4(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbscrsList5(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbscrsList6(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbscrsList7(Map<String, Object> mapParam);

	List<Map<String, Object>> selecBbscrsIndvList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selecBbscrsIndvDetail(Map<String, Object> mapParam);
	
	void updateBbscrsIndv(Map<String, String> mapUpd);
	
	void updateBbscrsIndv100(Map<String, String> mapUpd);
	
	void deleteBbscrsIndv(Map<String, String> mapDel);
	
	void deleteBbscrsIndv100(Map<String, String> mapDel);

	int getTotalCount(Map<String, Object> mapParam);
	
	void insertBbscrsIndv(Map<String, String> mapParam);
	
	void insertCrisisBoard(Map<String, String> mapParam);
	
	void updateCrisisBoard(Map<String, String> mapUpd);
	// 연계상담게시판  내담자 정보 조회
	List<Map<String, String>> selecClienaInfoList(Map<String, String> mapParam);
	// 연계상담게시판  내담자 정보 조회_채팅
	List<Map<String, String>> selecClienaInfoChttList(Map<String, String> mapParam);
}
