/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.ctfctissumng.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : ConsttSrchService.java
 * @프로그램 설명 : 상담사 검색 Service 인터페이스
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 10. 31. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 10. 31.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ConsttSrchService {
	
	/**
	 * @Method명   : selectConsttList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 31. 
	 * @Method설명 : 상담사 목록 조회
	 */
	List<Map<String, Object>> selectConsttList(DataRequest dataRequest, Map<String, Object> resPage) throws Exception;
}
