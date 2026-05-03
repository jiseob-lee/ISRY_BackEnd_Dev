/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.unitydscsnprfmnc.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : UnityDscsnPrfmncMapper.java
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
@Mapper("unityDscsnPrfmncMapper")
public interface UnityDscsnPrfmncMapper {
	
	/**
	 * 
	 * @Method명   : selectSxdcDscsnStatsList
	 * @param paramMap2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 상담통계 - 성별상담통계
	 */
	List<Map<String, String>> selectSxdcDscsnStatsList(Map<String, Object> paramMap2);
	
	/**
	 * @Method명   : selectYngbgsSttsDscsnStatsList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectYngbgsSttsDscsnStatsList(Map<String, Object> mapParam);
	
	/**
	 * 
	 * @Method명   : selectPvsnMtdDscsnList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectPvsnMtdDscsnStatsList(Map<String, Object> mapParam);
	
	/**
	 * 
	 * @Method명   : selectAgeDscsnStatsList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAgeDscsnStatsList(Map<String, Object> mapParam);
	/**
	 * 
	 * @Method명   : selectTrprTypeDscsnStatsList
	 * @param paramMap2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectTrprTypeDscsnStatsList(Map<String, Object> paramMap2);
	/**
	 * 
	 * @Method명   : selectsrvcMthdPrfmncList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectPvsnMtdAndTrprTypeDscsnStatsList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectRegion
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRegion();

	/**
	 * @Method명   : selectRegion2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRegion2();

}
