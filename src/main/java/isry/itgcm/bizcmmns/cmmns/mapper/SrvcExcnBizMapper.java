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
 * @파일명        : SrvcExcnBizMapper.java
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
@Mapper("srvcExcnBizMapper")
public interface SrvcExcnBizMapper {
	
	/**
	* @Method    : 서비스실행사업 목록조회
	* @param     : Map
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcExcnBizList(Map<String, String> paramMap) throws Exception;

}
