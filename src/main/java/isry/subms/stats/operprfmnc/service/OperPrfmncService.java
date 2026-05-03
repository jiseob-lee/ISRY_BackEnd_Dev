/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.operprfmnc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @파일명 : OperPrfmncService.java
 * @프로그램 설명 : 운영실적 서비스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
public interface OperPrfmncService {

	/**
	 * @Method명 : selectNmprPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 인원현황 목록 조회
	 */
	public List<Map<String, Object>> selectNmprPreconList(HttpServletRequest request,Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectNmprAchivRateList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 17.
	 * @Method설명 : 누적인원 및 달성률 목록 조회
	 */
	public List<Map<String, Object>> selectNmprAchivRateList(HttpServletRequest request, Map<String, Object> map) throws Exception;

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
	 * @Method설명 : 참여자대상자 연계현황 목록 조회
	 */
	public List<Map<String, Object>> selectPrtpntTrprLinkPreconList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectEareEduPrgrsPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 영역별 교육진행현황 목록 조회
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
	 * @param request
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 : 과정명에 맞는 학기 출력을 위한 학기조회
	 */
	public List<Map<String, Object>> selectSemstrCombo(HttpServletRequest request) throws Exception;

}
