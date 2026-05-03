/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.unitystats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : UnitystatsMapper.java
 * @프로그램 설명 : 공통통계 Mapper - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 1. 9.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 1. 9.
 * @수정내용 : - -
 */
@Mapper("UnitystatsMapper")
public interface UnitystatsMapper {

	/**
	 * @Method명 : selectUneartMngStatsList
	 * @param reqMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 9.
	 * @Method설명 : 1.발굴관리통계
	 */
	List<Map<String, Object>> selectUneartMngStatsList(Map<String, Object> reqMap) throws Exception;

	/**
	 * @Method명 : selectYngbgsCaseMngStatsList
	 * @param reqMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 9.
	 * @Method설명 : 3.청소년구분별사례관리통계
	 */
	List<Map<String, Object>> selectYngbgsCaseMngStatsList(Map<String, Object> reqMap) throws Exception;

	/**
	 * @Method명 : selectProbmSttsCaseMsgStatsList
	 * @param dmSearch
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 4.문제상태별사례관리통계
	 */
	List<Map<String, Object>> selectProbmSttsCaseMsgStatsList(Map<String, Object> dmSearch) throws Exception;
	
	/**
	 * @Method명   : selectSprtSrvcStatsList
	 * @param dmSearch
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 : 5.지원서비스통계
	 */
	List<Map<String, Object>> selectSprtSrvcStatsList(Map<String, Object> dmSearch) throws Exception;
	
	/**
	 * @Method명 : selectOutStatsPubmsList
	 * @param dmSearch
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2023. 1. 11.
	 * @Method설명 : 6-1.성과통계(학교밖) / 6-2.성과통계(쉼터)
	 */
	List<Map<String, Object>> selectOutStatsPubmsList(Map<String, Object> dmSearch) throws Exception;
	
	/**
	 * @Method명 : selectOutStatsPubmtList
	 * @param reqMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 6-3.성과통계(자립지원관)
	 */
	List<Map<String, Object>> selectOutStatsPubmtList(Map<String, Object> reqMap) throws Exception;

	/**
	 * @Method명 : selectDscsnOutrcMngStatsList
	 * @param reqMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 9.아웃리치통계(쉼터)
	 */
	List<Map<String, Object>> selectDscsnOutrcMngStatsList(Map<String, Object> reqMap) throws Exception;

	/**
	 * @Method명 : selectTlphonDscsnMngStatsList
	 * @param reqMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 11.
	 * @Method설명 : 10.1388전화상담통계(상담복지센터)
	 */
	List<Map<String, Object>> selectTlphonDscsnMngStatsList(Map<String, Object> reqMap) throws Exception;

	/**
	 * @Method명 : selectEmrgIntrvnMngStatsList
	 * @param reqMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 11.긴급구조통계(지자체안정망, 상담복지센터)
	 */
	List<Map<String, Object>> selectEmrgIntrvnMngStatsList(Map<String, Object> reqMap) throws Exception;

	/**
	 * @Method명   : selectCaseMngBassStatsList
	 * @param dmSearch
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCaseMngBassStatsList(Map<String, Object> dmSearch) throws Exception;

}
