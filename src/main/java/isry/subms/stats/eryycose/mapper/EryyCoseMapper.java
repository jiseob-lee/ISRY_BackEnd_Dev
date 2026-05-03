/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.eryycose.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : EryyCoseMapper.java
 * @프로그램 설명 : 초기진로 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 30.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 30.
 * @수정내용 : - -
 */
@Mapper("eryyCoseMapper")
public interface EryyCoseMapper {

	public Map<String, Object> selectSemstrNm() throws Exception;

	public List<Map<String, Object>> selectExcnBizSemstr() throws Exception;

	public List<Map<String, Object>> selectInstPrgrsPrfmncList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectAgePreconList(Map<String, Object> inMap) throws Exception;
	
	public List<Map<String, Object>> selectLinkPreconList(Map<String, Object> inMap) throws Exception;
	
	public List<Map<String, Object>> selectBrthNtnPreconList(Map<String, Object> inMap) throws Exception;
	
	public List<Map<String, Object>> selectVisaTypePreconList(Map<String, Object> inMap) throws Exception;
	
	public List<Map<String, Object>> selectTrprTypePreconList(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectBrthNtnPreconList2(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectGrowthNtnPreconList(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectNowNltyPreconList(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectAcbgPreconList(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectAcbgPreconList2(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectSxdcPreconList(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectLinkPreconList2(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectVisaTypePreconList2(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectTrprTypePreconList2(Map<String, Object> inMap) throws Exception;

	public List<Map<String, Object>> selectTrlSoctyAdaptInspYnList(Map<String, Object> inMap) throws Exception;

	/**
	 * @Method명   : selectKlangLevelEvl
	 * @param paramMap2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 한국어평가(레벨테스트)
	 */
	public List<Map<String, Object>> selectKlangLevelEvl(Map<String, Object> paramMap2) throws Exception;

	/**
	 * @Method명   : selectKlangMiddleEvl
	 * @param paramMap2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 한국어평가(중간테스트)
	 */
	public List<Map<String, Object>> selectKlangMiddleEvl(Map<String, Object> paramMap2) throws Exception;

	/**
	 * @Method명   : selectKlangSccesdEvl
	 * @param paramMap2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 한국어평가(성취도평가)
	 */
	public List<Map<String, Object>> selectKlangSccesdEvl(Map<String, Object> paramMap2) throws Exception;

}
