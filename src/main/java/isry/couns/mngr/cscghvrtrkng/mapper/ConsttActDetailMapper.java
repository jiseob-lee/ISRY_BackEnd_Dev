/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.cscghvrtrkng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : ConsttActDetailMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 5. 10. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 5. 10.
 * @수정내용      : 
 * -                
 * -                
 */

@Mapper("ConsttActDetailMapper")
public interface ConsttActDetailMapper {
	public List<Map<String, Object>> selectConsttActDetailList (Map<String, Object> mapParam) throws Exception;
	
	public List<Map<String, Object>> selectConsttActDetailInfo (Map<String, Object> mapParam) throws Exception;
}
