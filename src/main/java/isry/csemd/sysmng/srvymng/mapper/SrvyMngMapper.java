/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.sysmng.srvymng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SrvyMngMapper.java
 * @프로그램 설명 : 설문관리 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 25.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 25.
 * @수정내용 : - -
 */
@Mapper(value = "srvyMngMapper")
public interface SrvyMngMapper {

	List<Map<String, Object>> selectTrprInfo(Map<String, String> requestMap);

	List<Map<String, Object>> selectSrvyRspnsInfo(Map<String, String> requestMap);

	/**
	 * @Method명 : selectSrvyChart
	 * @param requestMap
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 : 차트 조회
	 */
	List<Map<String, Object>> selectSrvyChart(Map<String, String> requestMap);

	/**
	 * @Method명 : selectSrvyTrprCount
	 * @param requestMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 27.
	 * @Method설명 : 사전/사후 설문 작성자 수 조회
	 */
	Map<String, Object> selectSrvyTrprCount(Map<String, String> requestMap);

	/**
	 * @Method명 : selectSrvyRecodeList
	 * @param requestMap
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSrvyRecodeList(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectSrvySndngList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 1.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSrvySndngList(Map<String, String> mapParam);

	/**
	 * @Method명 : insertQustnbTrprInfo
	 * @param map
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 2.
	 * @Method설명 :
	 */
	void insertQustnbTrprInfo(Map<String, String> map);

	/**
	 * @Method명 : insertQustnbSndngHstr
	 * @param map
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 2.
	 * @Method설명 :
	 */
	void insertQustnbSndngHstr(Map<String, String> map);

	/**
	 * @Method명 : selectSrvyAnlsCn
	 * @param requestMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 11. 2.
	 * @Method설명 : 설문내용 분석내용탭
	 */
	List<Map<String, Object>> selectSrvyAnlsCn(Map<String, String> requestMap);

	/**
	 * @Method명 : selectQustnbList
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 24.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectQustnbList(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명 : selectQustnbQesitm
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 24.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectQustnbQesitm(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : selectAddtng
	 * @param dmMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 :
	 */
	Map<String, String> selectAddtng(Map<String, String> dmMap);

	/**
	 * @Method명   : selectQustnbKndSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 1. 2. 
	 * @Method설명 :
	 */
	Map<String, Object> selectQustnbKndSeCd(Map<String, String> requestMap);

	/**
	 * @Method명   : selectUntTaskwkSeCd
	 * @param map
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 :
	 */
	String selectUntTaskwkSeCd(Map<String, String> map);




}
