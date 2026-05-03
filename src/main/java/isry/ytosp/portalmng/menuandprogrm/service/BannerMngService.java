/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : BannerMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 8. 25. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 8. 25.
 * @수정내용      : 
 * -                
 * -                
 */
public interface BannerMngService {
	
	/**
	 * @Method명   : selectBannerMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 8. 30. 
	 * @Method설명 : 배너 목록조회
	 */
	List<Map<String, Object>> selectBannerMngList(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : selectBannerMngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 9. 1. 
	 * @Method설명 : 배너 상세조회
	 */
	List<Map<String, Object>> selectBannerMngDetail(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method     	: insertBannerMng
	 * @Method설명 	: 배너 등록
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 8. 24.
	 * @상세       	: 
 	 */
	public int insertBannerMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     	: deleteBannerMng
	 * @Method설명 	: 배너 상태(삭제) 업데이트
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 04.
	 * @상세       	: 
 	 */
	public int deleteBannerMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : updateBannerMng
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 :
	 */
	public int updateBannerMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
