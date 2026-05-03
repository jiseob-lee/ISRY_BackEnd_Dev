/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.prtpntexmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : PrtpntExmnMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 5. 18. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 5. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("prtpntExmnMapper")
public interface PrtpntExmnMapper {
	/**
	 * @Method명   : selectPrtpntExmnList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 : 리스트 조회
	 */
	public List<Map<String, String>> selectPrtpntExmnList(Map<String, String> mapParam);

	/**
	 * @Method명   : updatePrtpntExmn
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 : 수정 완료 후 저장
	 */
	public int updatePrtpntExmn(Map<String, String> mapParam);

	/**
	 * @Method명   : selectPrtpntExmnDetail
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectPrtpntExmnDetail(Map<String, String> mapParam);

	/**
	 * @Method명   : insertPrtpntExmn
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	public int insertPrtpntExmn(Map<String, String> mapParam);

	/**
	 * @Method명   : updateName
	 * @param scpEncB64
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 29. 
	 * @Method설명 :
	 */
	public void updateName(String name);

	/**
	 * @Method명   : selectExcnBizSemstr
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 8. 
	 * @Method설명 :
	 */
	public List<Map<String, String>> selectExcnBizSemstr();
	
	/**
	 * @Method명   : savePrtpntExmn
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 8. 7. 
	 * @Method설명 : 참여자조사표 등록/수정
	 */
	public int savePrtpntExmn(Map<String, String> map);

	/**
	 * @Method명   : deleteBfeFnshYr
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 8. 8. 
	 * @Method설명 :
	 */
	public void deleteBfeFnshYr(Map<String, String> map);

	/**
	 * @Method명   : insertBfeFnshYr
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 8. 8. 
	 * @Method설명 :
	 */
	public void insertBfeFnshYr(Map<String, String> map);
}
