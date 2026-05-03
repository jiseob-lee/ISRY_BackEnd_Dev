/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * @파일명        : CaseDdlnMapper.java
 * @프로그램 설명 	: 마감여부
 *
 * @작성자        : Choi.Doo.Il
 * @작성일        : 2022. 9. 05. 
 * @수정자        : Choi.Doo.Il
 * @수정일        : 
 * @수정내용      : 
 *
 */
@Mapper("caseDdlnYnMapper")
public interface CaseDdlnYnMapper {

	/**
	* @Method    : 마감여부
	* @param     : Map  : 
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public Map<String, Object> selectDdlnYn(Map<String, String> paramMap) throws Exception;
}
