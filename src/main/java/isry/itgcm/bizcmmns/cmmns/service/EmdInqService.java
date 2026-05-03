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
 * @파일명        : EmdInqService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 14. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 14.
 * @수정내용      : 
 * -                
 * -                
 */
public interface EmdInqService {
	
	/**
	 * @Method명   : selectEmdInqList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 시군구 읍면동 목록 조회
	 */
	public List<Map<String, Object>> selectEmdInqList (DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectEmdCodeList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 읍면동, 시군구코드 조회
	 */
	public List<Map<String, Object>> selectEmdCodeList (Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectStdgCodeList
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 27. 
	 * @Method설명 : 시도코드 조회
	 */
	public List<Map<String, Object>> selectSggCtpvCodeList () throws Exception;
	
	/**
	 * @Method명   : selectSsgCodeList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 시군구코드 조회
	 */
	public List<Map<String, Object>> selectSsgCodeList (DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectDongInqList
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 법정읍면동코드 조회(시도명, 시군구명)
	 */
	public List<Map<String, Object>> selectDongInqList (DataRequest dataRequest) throws Exception;

}
