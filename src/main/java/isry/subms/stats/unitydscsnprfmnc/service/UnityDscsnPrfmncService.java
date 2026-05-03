/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.unitydscsnprfmnc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : UnityDscsnPrfmncService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 7. 7. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 7. 7.
 * @수정내용      : 
 * -                
 * -                
 */
public interface UnityDscsnPrfmncService {

	/**
	 * @Method명   : selectTrprSxdcPrfmncList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSxdcDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	List<Map<String, String>> selectTrprTypeDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	List<Map<String, String>> selectPvsnMtdAndTrprTypeDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	List<Map<String, String>> selectAgeDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	List<Map<String, String>> selectYngbgsSttsDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	List<Map<String, String>> selectPvsnMtdDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectRegion() throws Exception;
	public List<Map<String, Object>> selectRegion2() throws Exception;
	
}
