/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : SrvcExcnBizService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 24. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 24.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SrvcExcnBizService {

	/**
	* @Method    : 서비스실행사업 목록조회
	* @param     : Map
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcExcnBizList(DataRequest dataRequest) throws Exception;
	
}
