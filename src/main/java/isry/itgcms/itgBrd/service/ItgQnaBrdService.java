/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.itgBrd.service;

import java.util.List;
import java.util.Map;


/**
 * @파일명        : ItgQnaBrdService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2022. 6. 30. 
 * @수정자        : You Minsang
 * @수정일        : 2022. 6. 30.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ItgQnaBrdService {

	/**
	 * @Method명   : selectItgQnaBrdList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgQnaBrdList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectSysItgQnaBrdList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee SeoungJae
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 시스템문의사항 리스트조회 - 원본 selectItgQnaBrdList
	 */
	List<Map<String, Object>> selectSysItgQnaBrdList(Map<String, Object> mapParam) throws Exception;

}
