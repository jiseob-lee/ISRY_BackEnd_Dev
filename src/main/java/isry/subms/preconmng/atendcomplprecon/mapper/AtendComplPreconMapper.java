/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendcomplprecon.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : AtendComplPreconMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 6. 26. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 6. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("atendComplPreconMapper")
public interface AtendComplPreconMapper {
	
	/**
	 * @Method명   : selectAtendComplPreconList
	 * @param map
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 26. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAtendComplPreconList(Map<String, Object> map);

	/**
	 * @Method명   : selectAtendComplPreconMngList
	 * @param map
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAtendComplPreconMngList(Map<String, Object> map);

	/**
	 * @Method명   : saveAtendComplPreconMng
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 30. 
	 * @Method설명 :
	 */
	void saveAtendComplPreconMng(Map<String, String> map);

	/**
	 * @Method명   : deleteComplPreconMng
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 30. 
	 * @Method설명 :
	 */
	void deleteComplPreconMng(Map<String, String> map);

	/**
	 * @Method명   : saveAtendComplPreconList
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 7. 3. 
	 * @Method설명 :
	 */
	void saveAtendComplPreconList(Map<String, String> map);

	/**
	 * @Method명   : saveAtendComplPreconAFA330
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 :
	 */
	void saveAtendComplPreconAFA330(Map<String, String> map);

}
