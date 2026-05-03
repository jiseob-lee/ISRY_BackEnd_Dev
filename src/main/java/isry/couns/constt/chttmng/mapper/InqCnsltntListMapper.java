/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InqCnsltntListMapper")
public interface InqCnsltntListMapper {
	/**
	 * @파일명        : SpclaMapper.java
	 * @프로그램 설명 :
	 * - 
	 * - 
	 * @작성자        : Song.Young.Il
	 * @작성일        : 2022. 5. 4. 
	 * @수정자        : Song.Young.Il
	 * @수정일        : 2022. 5. 4.
	 * @수정내용      : 
	 * -                
	 * -                
	 */
	List<Map<String , Object>> selectInqCnsltntList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectInqCnsltntDetail(Map<String, Object> mapParam);
	
	void insertCnsltnt(Map<String, String> mapIns);

	void updateCnsltnt(Map<String, String> mapUpd);
	
	void deleteCnsltnt(Map<String, String> mapUpd);
}
