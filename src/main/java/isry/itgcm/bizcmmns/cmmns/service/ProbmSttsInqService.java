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
 * @파일명        : ProbmSttsInqService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자     : Kwon.Min.Seo
 * @작성일     : 2022. 07. 29. 
 * @작성자     : Kwon.Min.Seo
 * @작성일     : 2022. 07. 29. 
 * @수정내용      : 
 * -                
 * -                
 */
public interface ProbmSttsInqService {
	/**
	 * @Method     : selectProbmSttsInqList
	 * @Method설명 : 문제상태 및 원인 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 29.  
 	 */	
	public List<Map<String, Object>> selectProbmSttsInqList(DataRequest dataRequest) throws Exception;
	

}
