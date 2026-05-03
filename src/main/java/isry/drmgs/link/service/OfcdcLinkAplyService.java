/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : OfcdcLinkAplyService.java
 * @프로그램 설명 : 교육청 연계신청
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 09. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 09. 
 * @수정내용      : 
 * -                
 * -                
 */
public interface OfcdcLinkAplyService {

	/**
	 * @Method     : processLinkRqstdoExcelUpload
	 * @Method설명 : 연계의뢰서 업로드(집단) 엑셀업로드
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 09. 
 	 */	
	public Map<String, String> processLinkRqstdoExcelUpload(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectLinkRqstList
	 * @Method설명 : 연계의뢰서 업로드(집단) 조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 10. 
 	 */	
	public List<Map<String, String>> selectLinkRqstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectOfcdcSpclaMngList
	 * @Method설명 : 특별관리리스트 조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 16. 
 	 */	
	public List<Map<String, String>> selectOfcdcSpclaMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectOfcdcErrorInfoList
	 * @Method설명 : 오류정보리스트 조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 16. 
 	 */	
	public List<Map<String, String>> selectOfcdcErrorInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectLinkRqstDetList
	 * @Method설명 : 연계의뢰서 업로드(집단) 상세 조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 04. 18. 
 	 */	
	public List<Map<String, String>> selectLinkRqstDetList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
