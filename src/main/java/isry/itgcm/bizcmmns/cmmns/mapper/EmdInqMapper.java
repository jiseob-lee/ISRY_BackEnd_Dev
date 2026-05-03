/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : EmdInqMapper.java
 * @프로그램 설명 : 읍면동 조회팝업
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 14. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("emdInqMapper")
public interface EmdInqMapper {
	/**
	 * @Method명   : selectEmdInqList
	 * @param mapParam
	 * @return
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 시도, 시군구 읍면동 목록조회
	 */
	public List<Map<String, Object>> selectEmdInqList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectEmdCodeList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 읍면동, 시군구 코드 조회
	 */
	public List<Map<String, Object>> selectEmdCodeList (Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectSggCtpvCodeList (Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectSggCodeList (Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectStdgCodeList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectDongInqList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 법정동코드, 법정읍면동 조회
	 */
	public List<Map<String, Object>> selectDongInqList (Map<String, String> paramMap) throws Exception;


}
