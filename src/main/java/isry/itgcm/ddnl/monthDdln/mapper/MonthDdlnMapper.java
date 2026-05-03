/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.ddnl.monthDdln.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MonthDdlnMapper.java
 * @프로그램 설명 : 월마감 관리
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("monthDdlnMapper")
public interface MonthDdlnMapper {
	
	
	/**
	 * @Method명   : selectUntTaskwkInstList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 단위업무구분 시도수행기관, 시군구수행기관 조회
	 */
	public List<Map<String, Object>> selectUntTaskwkInstList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method명   : selectMonthDdlnList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 월마감관리 목록
	 */
	public List<Map<String, Object>> selectMonthDdlnList(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectBfeMonthDdlnList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 전월마감 조회
	 */
	public List<Map<String, Object>> selectBfeMonthDdlnList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMonthDdlnPrd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 : 기간 조회
	 */
	public Map<String, Object> selectMonthDdlnPrd() throws Exception;
	
	/**
	 * @Method명   : selectMonthDdlnCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 28. 
	 * @Method설명 : 월마감 등록 확인
	 */
	public Integer selectMonthDdlnCnt(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : getSAA000InstNm
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 기관명 확인
	 */
	public String getSAA000InstNm(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertMonthDdln
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 28. 
	 * @Method설명 : 월마감 처리
	 */
	public Integer insertMonthDdln(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateMonthDdln
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 28. 
	 * @Method설명 : 월마감 수정
	 */
	public Integer updateMonthDdln(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseMngDdlnCrtrInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 11. 01. 
	 * @Method설명 : 사례관리 마감기준정보 조회
	 */
	public List<Map<String, Object>> selectCaseMngDdlnCrtrInfo(Map<String, String> paramMap) throws Exception;
	
}
