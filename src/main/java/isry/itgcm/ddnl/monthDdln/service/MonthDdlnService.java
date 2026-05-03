/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.ddnl.monthDdln.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MonthDdlnService.java
 * @프로그램 설명 : 월마감 관리
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MonthDdlnService {
	
	
	/**
	 * @Method명   : selectUntTaskwkInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 단위업무구분 시도수행기관, 시군구수행기관 조회
	 */
	public List<Map<String, Object>> selectUntTaskwkInstList (HttpServletRequest request, DataRequest dataRequest) throws Exception;	

	/**
	 * @Method명   : selectMonthDdlnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 월관리마감 목록
	 */
	public List<Map<String, Object>> selectMonthDdlnList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectBfeMonthDdlnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 전월마감 조회
	 */
	public List<Map<String, Object>> selectBfeMonthDdlnList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectMonthDdlnPrd
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 : 기간 조회
	 */
	public Map<String, Object> selectMonthDdlnPrd (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processMonthDddln
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 27. 
	 * @Method설명 : 월마감 처리
	 */
	public Map<String, Object> processMonthDddln (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectCaseMngDdlnCrtrInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 11. 01.
	 * @Method설명 : 사례관리 마감기준정보 조회
	 */
	public List<Map<String, Object>> selectCaseMngDdlnCrtrInfo (DataRequest dataRequest) throws Exception;
}
