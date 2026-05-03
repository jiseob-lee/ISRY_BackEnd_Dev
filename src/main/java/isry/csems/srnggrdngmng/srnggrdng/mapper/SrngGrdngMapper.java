/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.srnggrdngmng.srnggrdng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SrngGrdngMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 4. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 4.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("csemsSrngGrdngMapper")
public interface SrngGrdngMapper {

	/**
	 * @Method명   : selectAplyRcpt
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAplyRcptCd();

	/**
	 * @Method명   : selectMaapCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectMaapCd();
	
	/**
	 * @Method명   : selectCampPrtcrCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectCampPrtcrCd();
	
	/**
	 * @Method명   : selectCampYngbgsCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectCampYngbgsCd();

	/**
	 * @Method명   : selectQustnbList
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectQustnbList(Map<String, String> dtlMap);
	
	/**
	 * @Method명   : selectdsQustnb2List
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectdsQustnb2List(Map<String, String> dtlMap);

	/**
	 * @Method명   : selectdsQustnb3List
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectdsQustnb3List(Map<String, String> dtlMap);
	
	/**
	 * @Method명   : selectSrngCnList
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSrngCnList(Map<String, String> dtlMap);

	/**
	 * @Method명   : selectGrdngList
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectGrdngList(Map<String, String> dtlMap);

	/**
	 * @Method명   : updateSrngGrdngGrdng
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	void updateSrngGrdngGrdng(Map<String, String> map);

	/**
	 * @Method명   : insertSrngGrdngPopGrdng
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	void insertSrngGrdngPopGrdng(Map<String, String> map);

	/**
	 * @Method명   : updateSrngGrdngPopGrdng
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	void updateSrngGrdngPopGrdng(Map<String, String> map);

	/**
	 * @Method명   : selectPtcptList
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectPtcptList(Map<String, String> dtlMap);

	

	



	

	



}
