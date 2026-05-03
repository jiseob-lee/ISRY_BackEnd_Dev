/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.eryycose.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : EryyCoseService.java
 * @프로그램 설명 : 초기진로 서비스 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 30.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 30.
 * @수정내용 : - -
 */
public interface EryyCoseService {

	public Map<String, Object> selectSemstrNm(List<Map<String, Object>> map) throws Exception;

	public List<Map<String, Object>> selectExcnBizSemstr() throws Exception;

	public List<Map<String, Object>> selectInstPrgrsPrfmncList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectAgePreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectLinkPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectBrthNtnPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectVisaTypePreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectTrprTypePreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectBrthNtnPreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectGrowthNtnPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectNowNltyPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectAcbgPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;
	
	public List<Map<String, Object>> selectAgePreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectSxdcPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectLinkPreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectVisaTypePreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectTrprTypePreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectTrlSoctyAdaptInspYnList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;
	
	public List<Map<String, Object>> selectKlangLevelEvl(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public List<Map<String, Object>> selectKlangMiddleEvl(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public List<Map<String, Object>> selectKlangSccesdEvl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
