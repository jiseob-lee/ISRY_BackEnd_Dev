/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.atend.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : AtboYrPreconMapper.java
 * @프로그램 설명 : 출석부현황 매퍼 - -
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 7. 28.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 7. 28.
 * @수정내용 : - -
 */

@Mapper("atboYrPreconMapper")
public interface AtboYrPreconMapper {

	public List<Map<String, Object>> selectAtboPcList(Map<String, String> paramMap) throws Exception;
	
}
