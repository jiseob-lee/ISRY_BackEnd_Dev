/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CaseMtgService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 13. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 13.
 * @수정내용      : 
 * -                
 * -                
 */
public interface CaseMtgService {
	
	
	/**
	 * @Method명   : selectCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 목록 조회
	 */
	public List<Map<String, Object>> selectCaseMtgList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectCaseMtgAtdrnlList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 참석자 조회
	 */
	public List<Map<String, Object>> selectCaseMtgAtdrnlList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectCaseMtgPiclList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 담당자 조회
	 */
	public List<Map<String, Object>> selectCaseMtgPiclList (HttpServletRequest request, DataRequest dataRequest) throws Exception;	

	/**
	 * @Method명   : processCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 저장
	 */
	public void processCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectGrCaseMtgList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 4. 
	 * @Method설명 : 집단사례회의 목록
	 */
	List<Map<String,Object>> selectGrCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectGrCaseMtgDetail
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 4. 
	 * @Method설명 : 집단사례회의 상세조회
	 */
	Map<String,Object> selectGrCaseMtgDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processGrCaseMtgList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 4. 
	 * @Method설명 : 집단사례회의 등록, 수정, 삭제
	 */
	Map<String,Object> processGrCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception;	
	
	
}
