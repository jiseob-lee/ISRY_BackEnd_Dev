/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.ensttrlinsp.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : EnstTrlInspMapper.java
 * @프로그램 설명 : 입교생심리검사 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 7.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 7.
 * @수정내용 : - -
 */
@Mapper("enstTrlInspMapper")
public interface EnstTrlInspMapper {

	/**
	 * @Method명 : selectQesitm
	 * @param str
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 문항정보 조회
	 */
	public List<Map<String, Object>> selectQesitm(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : selectAwarExmn
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 인지도조사 조회
	 */
	public List<Map<String, Object>> selectAwarExmn(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : selectEmtGhvr
	 * @param dmSearch
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 8. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectEmtGhvr(Map<String, String> dmSearch) throws Exception;
	
	/**
	 * @Method명   : selectPopulStatsInfoPrtcrSeEtc
	 * @param dmSearch
	 * @return
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보 - 보호자구분 - 기타
	 */
	public List<Map<String, Object>> selectPopulStatsInfoPrtcrSeEtc(Map<String, String> dmSearch) throws Exception;
	
	/**
	 * @Method명   : selecPopulStatsInfoSxdc
	 * @param dmSearch
	 * @return
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보 - 성별구분
	 */
	public List<Map<String, Object>> selectPopulStatsInfoSxdc(Map<String, String> dmSearch) throws Exception;
	
	/**
	 * @Method명   : selectPopulStatsInfoAcbg
	 * @param dmSearch
	 * @return
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보 - 학력구분
	 */
	public List<Map<String, Object>> selectPopulStatsInfoAcbg(Map<String, String> dmSearch) throws Exception;
	
	/**
	 * @Method명   : selectPopulStatsInfoResdnRgn
	 * @param dmSearch
	 * @return
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보 - 거주지역구분
	 */
	public List<Map<String, Object>> selectPopulStatsInfoResdnRgn(Map<String, String> dmSearch) throws Exception;
	
	/**
	 * @Method명   : selectPopulStatsInfoReside
	 * @param dmSearch
	 * @return
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보 - 주거지구분
	 */
	public List<Map<String, Object>> selectPopulStatsInfoReside(Map<String, String> dmSearch) throws Exception;

	/**
	 * @Method명   : selectTrlEmtInsp
	 * @param dmSearch
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectTrlEmtInsp(Map<String, String> dmSearch) throws Exception;
	
}
