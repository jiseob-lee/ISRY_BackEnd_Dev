/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.bgtprfmnc.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : BgtPrfmncMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 6. 27. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 6. 27.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("bgtPrfmncMapper")
public interface BgtPrfmncMapper {

	/**
	 * @Method명   : selectBgtPrfmncList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 6. 
	 * @Method설명 : 리스트 조회
	 */
	List<Map<String, String>> selectBgtPrfmncList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectBgtPrfmnc
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectBgtPrfmnc(Map<String, Object> mapParam);
	
	/**
	 * 
	 * @Method명   : deleteBgtPrfmnc
	 * @param mapParam
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 예산실적 상세 삭제
	 */
	void deleteBgtPrfmnc(Map<String, String> mapParam);
	
	/**
	 * 
	 * @Method명   : updateBgtPrfmncInfo
	 * @param mapParam
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 예산실적 수정
	 */
	void updateBgtPrfmncInfo(Map<String, String> mapParam);
	
	/**
	 * 
	 * @Method명   : updateBgtPrfmncAmount
	 * @param updatedDsBgtImplListRowList
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 예산실적 상세 수정
	 */
	void updateBgtPrfmncAmount(List<Map<String, String>> updatedDsBgtImplListRowList);
	
	/**
	 * 
	 * @Method명   : insertBgtPrfmnc
	 * @param mapParam
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 예산실적 입력
	 */
	void insertBgtPrfmnc(Map<String, String> mapParam);
	
	/**
	 * 
	 * @Method명   : insertBgtPrfmncAmount
	 * @param mapParam
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 예산실적 상세 입력
	 */
	void insertBgtPrfmncAmount(List<Map<String, String>> mapParam);
	
	/**
	 * 
	 * @Method명   : selectBgtImplCmmnCode
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 예산실적 등록 시 화면에 나타낼 공통코드를 호출
	 */
	List<Map<String, Object>> selectBgtImplCmmnCode();
	
	/**
	 * 
	 * @Method명   : selectBgtPrfmncStatusList
	 * @param mapParam
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 예산실적 일괄조회
	 */
	List<Map<String, Object>> selectBgtPrfmncStatusList(Map<String, Object> mapParam);

	/**
	 * @Method명   : updateBgtPrfmncAmoutInfo
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 :
	 */
	void updateBgtPrfmncAmoutInfo(Map<String, String> map);

	/**
	 * @Method명   : selectBgtPrfmncExist
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 24. 
	 * @Method설명 : 예산실적 중복 체크
	 */
	Map<String, Object> selectBgtPrfmncExist(Map<String, String> mapParam);

	/**
	 * @Method명   : insertBgtPrfmncHstr
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 28. 
	 * @Method설명 : 예산실적 이력 등록
	 */
	void insertBgtPrfmncHstr(Map<String, String> map);

	/**
	 * @Method명   : insertBgtPrfmncAmountHstr
	 * @param updatedDsBgtImplList
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 28. 
	 * @Method설명 : 예산실적상세 이력 등록
	 */
	void insertBgtPrfmncAmountHstr(List<Map<String, String>> updatedDsBgtImplList);

	/**
	 * @Method명   : deleteBgtPrfmncAmount
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 예산실적상세 삭제
	 */
	void deleteBgtPrfmncAmount(Map<String, String> map);

	/**
	 * @Method명   : insertBgtPrfmncAmountHstrDel
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 예산실적 삭제 시 상세이력 입력
	 */
	void insertBgtPrfmncAmountHstrDel(Map<String, String> map);

	/**
	 * @Method명   : insertBgtPrfmncAmountHstrUpdate
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 예산실적 수정 테이블의 기본키가 바뀔 때 예산실적 상세이력 입력
	 */
	void insertBgtPrfmncAmountHstrUpdate(Map<String, String> map);
}






