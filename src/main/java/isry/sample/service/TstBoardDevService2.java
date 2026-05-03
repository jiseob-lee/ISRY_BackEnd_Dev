/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.service;

import java.util.List;
import java.util.Map;

/**
 * @파일명 : TstBoardDevService2.java
 * @프로그램 설명 : - -
 * @작성자 : Jeong.Tae.Young
 * @작성일 : 2022. 3. 23.
 * @수정자 : Jeong.Tae.Young
 * @수정일 : 2022. 3. 23.
 * @수정내용 : - -
 */
public interface TstBoardDevService2 {

	/**
	 * @Method명 : selectSysDate
	 * @return
	 * @throws Exception
	 * @작성자 : Jeong.Tae.Young
	 * @작성일 : 2022. 3. 23.
	 * @Method설명 :
	 */
	String selectSysDate() throws Exception;

	/**
	 * @Method명 : getTotalCount
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2021. 12. 30.
	 * @Method설명 :
	 */
	String getTotalCount() throws Exception;

	/**
	 * @param mapParam
	 * @Method명 : selectBoardList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2021. 12. 20.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectBoardList(Map<String, Object> mapParam) throws Exception;
}
