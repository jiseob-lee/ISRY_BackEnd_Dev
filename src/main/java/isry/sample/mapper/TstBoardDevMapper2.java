/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : TstBoardDevMapper2.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Jeong.Tae.Young
 * @작성일        : 2022. 3. 23. 
 * @수정자        : Jeong.Tae.Young
 * @수정일        : 2022. 3. 23.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("tstBoardDevMapper2")
public interface TstBoardDevMapper2 {

	/**
	 * @Method명   : selectSysDate
	 * @param mapParam
	 * @return
	 * @작성자     : Jeong.Tae.Young
	 * @작성일     : 2022. 3. 23. 
	 * @Method설명 :
	 */
	String selectSysDate(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method명   : getTotalCount
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 30. 
	 * @Method설명 :
	 */
	String getTotalCount();
	
	/**
	 * @Method명   : selectBoardList
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 20. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectBoardList(Map<String, Object> mapParam) throws Exception;
}
