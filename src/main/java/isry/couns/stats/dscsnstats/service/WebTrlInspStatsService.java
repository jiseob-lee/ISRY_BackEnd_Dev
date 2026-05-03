/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : WebTrlInspStatsService.java
 * @프로그램 설명 : 웹심리검사 통계 Service Class
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2023. 2. 10. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2023. 2. 10.
 * @수정내용      : 
 * -                
 * -                
 */
public interface WebTrlInspStatsService {
	
	/**
	 * @Method명   : selectWebTrlInspKndList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 웹심리검사 통계_검사 종류 List 조회(검사종류/검사구분/실시건수/댓글건수)
	 */
	public List<Map<String, Object>> selectWebTrlInspKndList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectWebTrlInspKndDetail
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 선택한 웹심리검사에 대한 검사결과현황과 검사결과에 대한 내역 조회
	 */
	public void selectWebTrlInspKndDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectWebTrlInspProbmSttsList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 10. 
	 * @Method설명 : 웹심리검사 문제상태 통계
	 */
	public List<Map<String, Object>> selectWebTrlInspProbmSttsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectWebTrlInspDgstfnKndList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_검사 종류 List 조회(검사종류/응답건수)
	 */
	public List<Map<String, Object>> selectWebTrlInspDgstfnKndList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectWebTrlInspDgstfnKndDetail
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_선택한 검사에 대한 만족도 조사 결과 조회
	 */
	public List<Map<String, Object>> selectWebTrlInspDgstfnKndDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
