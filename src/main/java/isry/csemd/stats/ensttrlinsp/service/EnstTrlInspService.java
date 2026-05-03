/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.ensttrlinsp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : EnstTrlInspService.java
 * @프로그램 설명 : 입교생심리검사 서비스 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 7.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 7.
 * @수정내용 : - -
 */
public interface EnstTrlInspService {

	/**
	 * @Method명   : selectQesitm
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 문항정보 조회
	 */
	public List<Map<String, Object>> selectQesitm(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectAwarExmn
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 인지도조사 조회
	 */
	public void selectAwarExmn(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectEmtGhvr
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 8. 
	 * @Method설명 : 정서행동검사통계
	 */
	public void selectEmtGhvr(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectPopulStatsInfo
	 * @param dataRequest
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보
	 */
	public void selectPopulStatsInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectTrlEmtInsp
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 :
	 */
	public void selectTrlEmtInsp(DataRequest dataRequest) throws Exception;
}
