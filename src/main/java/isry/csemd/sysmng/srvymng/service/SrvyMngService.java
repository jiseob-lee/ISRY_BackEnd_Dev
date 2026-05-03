/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.sysmng.srvymng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : SrvyMngService.java
 * @프로그램 설명 :설문관리 서비스 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 25.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 25.
 * @수정내용 : - -
 */
public interface SrvyMngService {

	List<Map<String, Object>> selectTrprInfo(Map<String, String> requestMap);

	List<Map<String, Object>> selectSrvyRspnsInfo(Map<String, String> requestMap);

	/**
	 * @Method명 : selectSrvyChart
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 : 설문차트 조회
	 */
	void selectSrvyChart(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명 : selectSrvyAnlsCn
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 28.
	 * @Method설명 : 설문내용 분석내용 조회
	 */
	void selectSrvyAnlsCn(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명 : selectSrvyRecodeList
	 * @param request
	 * @param dataRequest
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 : 설문이력 조회
	 */
	List<Map<String, Object>> selectSrvyRecodeList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/**
	 * @Method명 : selectSrvySndngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 1.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSrvySndngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : updateQustnbCompno
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 1.
	 * @Method설명 :
	 */
	void updateQustnbCompno(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> resultMap) throws Exception;

	/**
	 * @Method명 : updateQustnbSingle
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 3.
	 * @Method설명 :
	 */
	void updateQustnbSingle(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> resultMap) throws Exception;

	/**
	 * @Method명 : selectQustnbList
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 24.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectQustnbList(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectQustnbQesitm
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 24.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectQustnbQesitm(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : chkQustnbTmptUseYn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 12. 13. 
	 * @Method설명 :
	 */
	public Map<String, Object> chkQustnbTmptUseYn(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectAddtng
	 * @param dmMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 :
	 */
	public Map<String, String> selectAddtng(Map<String, String> dmMap);

	
}
