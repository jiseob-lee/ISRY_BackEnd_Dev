/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.linkCaseMng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LinkCaseMngMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 5. 12. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 5. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("linkCaseMngMapper")
public interface LinkCaseMngMapper {

	/**
	 * @Method명   : selectLinkCaseMngList
	 * @param dmMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 5. 12. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectLinkCaseMngList(Map<String, Object> dmMap) throws Exception;

}
