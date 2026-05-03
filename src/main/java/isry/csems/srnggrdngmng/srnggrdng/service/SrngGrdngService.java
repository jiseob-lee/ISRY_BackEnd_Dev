/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.srnggrdngmng.srnggrdng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : SrngGrdngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 4. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 4.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SrngGrdngService {

	/**
	 * @Method명   : selectAplyRcpt
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String,String>> selectAplyRcptCd();

	/**
	 * @Method명   : selectMaapCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String,String>> selectMaapCd();
	
	/**
	 * @Method명   : selectCampPrtcrCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectCampPrtcrCd();
	
	/**
	 * @Method명   : selectCampYngbgsCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectCampYngbgsCd();

	/**
	 * @Method명   : selectQustnbList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectQustnbList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : selectdsQustnb2List
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectdsQustnb2List(HttpServletRequest request, DataRequest dataRequest);
	
	/**
	 * @Method명   : selectdsQustnb3List
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectdsQustnb3List(HttpServletRequest request, DataRequest dataRequest);
	
	/**
	 * @Method명   : selectSrngCnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSrngCnList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : selectGrdngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectGrdngList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : saveSrngGrdngPop
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	void saveSrngGrdngPop(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectPtcptList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectPtcptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	

	



	


}
