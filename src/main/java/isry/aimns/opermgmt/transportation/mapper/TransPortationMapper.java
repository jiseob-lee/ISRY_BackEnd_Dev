/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.transportation.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : TransPortationMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 6. 9. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 6. 9.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("transPortationMapper")
public interface TransPortationMapper {

	/**
	 * @param map 
	 * @Method명   : selectTransFoodList
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 10. 
	 * @Method설명 :
	 */
	public List<Map<String, String>> selectTransFoodList(Map<String, String> map) throws Exception;

}
