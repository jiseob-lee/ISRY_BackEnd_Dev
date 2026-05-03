/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsmcr.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

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

@Mapper("BbsmcrListMapper")
public interface BbsmcrListMapper {
	
	List<Map<String , Object>> selectBbsmcrList1(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbsmcrList2(Map<String, Object> mapParam);

	List<Map<String, Object>> selecBbscrsIndvList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbsmcrDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbsmcrAftDetail(Map<String, Object> mapParam);
	
	void insertBbsmcr(Map<String, String> mapUpd);
	
	void updateBbsmcr(Map<String, String> mapUpd);
	
	void deleteBbsmcr(Map<String, String> mapDel);
	
	void insertBbsmcrAft(Map<String, String> mapUpd);
	
	void updateBbsmcrAft(Map<String, String> mapUpd);
	
	void deleteBbsmcrAft(Map<String, String> mapDel);

	int getTotalCount(Map<String, Object> mapParam);

}
