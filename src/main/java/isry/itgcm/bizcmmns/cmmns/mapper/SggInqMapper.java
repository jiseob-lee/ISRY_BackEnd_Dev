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
 * @파일명        : SggInqMapper.java
 * @프로그램 설명 : 시군구를 조회하는 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 5. 25. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 5. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("sggInqMapper")
public interface SggInqMapper {
	
	/**
	 * @Method명   : selectSggInqList
	 * @param mapParam
	 * @return
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 : 시도, 시군구 목록조회
	 */
	public List<Map<String, Object>> selectSggInqList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectSggCodeList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 시도, 시군구 코드 조회
	 */
	public List<Map<String, Object>> selectSggCodeList (Map<String, String> paramMap) throws Exception;

}
