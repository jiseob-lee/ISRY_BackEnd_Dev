/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.extrnltaskwk.extrnlsprtactvt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : ExtrnlSprtActvtService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 6. 15. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 6. 15.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ExtrnlSprtActvtService {
	
	/**
	 * @Method명   : getTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : KIM.SEONG.OK
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	public int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	
	
	/**
	 * @Method명   : selectExtrnlSprtActvtList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : KIM.SEONG.OK
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectExtrnlSprtActvtList(Map<String, Object> mapParam) throws Exception;
	
	
	
	/**
	 * @Method명   : selectLinkVlntrInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : KIM.SEONG.OK
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	public Map<String, String> selectUserInfo(Map<String, Object> loginMap) throws Exception;



	/**
	 * @Method명   : selectEnfsnList
	 * @param userInfoMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	public List<Map<String, String>> selectEnfsnList(Map<String, String> userInfoMap);
	
	
	
	/**
	 * @Method명   : insertExtrnlSprtActvtDetail
	 * @param userInfoMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	public void insertExtrnlSprtActvtDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	
	/**
	 * @Method명   : updateExtrnlSprtActvtDetail
	 * @param userInfoMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	public void updateExtrnlSprtActvtDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	


	/**
	 * @Method명   : selectExtrnlSprtActvtDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : KIM.SEONG.OK
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectExtrnlSprtActvtDetail(DataRequest dataRequest) throws Exception;



	/**
	 * @Method명   : selectLinkResrceInstChcList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 24. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectLinkResrceInstChcList(Map<String, Object> mapParam);



	/**
	 * @Method명   : getLinkResrceInstChcTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 24. 
	 * @Method설명 :
	 */
	public int getLinkResrceInstChcTotalCount(Map<String, Object> mapParam);



	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 27. 
	 * @Method설명 :
	 */
	public String selectTaskwkSeCd(Map<String, Object> requestMap);



	/**
	 * @Method명   : deleteExtrnlSprtActvtDetail
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 29. 
	 * @Method설명 :
	 */
	public void deleteExtrnlSprtActvtDetail(DataRequest dataRequest);



	/**
	 * @Method명   : getSearchCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 30. 
	 * @Method설명 :
	 */
	public int getSearchCount(Map<String, Object> mapParam);



	/**
	 * @Method명   : selectSearchInstInfo
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 30. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectSearchInstInfo(Map<String, Object> mapParam);



}
