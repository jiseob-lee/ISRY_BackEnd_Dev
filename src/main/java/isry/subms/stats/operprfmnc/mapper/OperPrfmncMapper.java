/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.operprfmnc.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : OperPrfmncMapper.java
 * @프로그램 설명 : 운영실적 매퍼 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
@Mapper("operPrfmncMapper")
public interface OperPrfmncMapper {

	/**
	 * @Method명 : selectNmprPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 인원현황 목록 조회
	 */
	public List<Map<String, Object>> selectNmprPreconList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectNmprAchivRateList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 17.
	 * @Method설명 : 누적인원 및 달성률 목록 조회
	 */
	public List<Map<String, Object>> selectNmprAchivRateList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectPrtpntTrgtPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참여자대상별현황 목록 조회
	 */
	public List<Map<String, Object>> selectPrtpntTrgtPreconList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectPrtpntTrprLinkPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참여자대상자 연계 현황 목록 조회
	 */
	public List<Map<String, Object>> selectPrtpntTrprLinkPreconList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectEareEduPrgrsPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 영역벽 교육진행현황 목록 조회
	 */
	public List<Map<String, Object>> selectEareEduPrgrsPreconList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectProgrmEduPrgrsPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 프로그램별 교육진행현황 목록 조회
	 */
	public List<Map<String, Object>> selectProgrmEduPrgrsPreconList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectSemstrCombo
	 * @param userInfoMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectSemstrCombo(Map<String, String> userInfoMap) throws Exception;

}
