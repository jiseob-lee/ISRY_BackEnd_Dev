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
 * @파일명        : ProbmSttsInqMapper.java
 * @프로그램 설명 : 문제상태 및 원인분류(팝업) Mapper Class
 * - 
 * - 
 * @작성자     	: Kwon.Min.Seo
 * @작성일      : 2022. 07. 29. 
 * @수정자      : Kwon.Min.Seo
 * @수정일      : 2022. 07. 29.
 * @수정내용    : 
 * -                
 * -                
 */
@Mapper("probmSttsInqMapper")
public interface ProbmSttsInqMapper {
	
	/**
	 * @Method     : selectProbmSttsInqList
	 * @Method설명 : 문제상태및원인 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectProbmSttsInqList(Map<String, String> paramMap) throws Exception;

}
