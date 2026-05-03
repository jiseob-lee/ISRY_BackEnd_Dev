/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.crtfmng.crtfissu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : CrtfiSsuService.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2022. 8. 12.
 * @수정자 : Song.Young.Il
 * @수정일 : 2022. 8. 12.
 * @수정내용 : - -
 */
public interface CrtfiSsuService {

	/**
	 * @Method명 : selectCrtfiNo
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 16.
	 * @Method설명 :
	 */
	public Map<String, Object> selectCrtfiNo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectCrtfiToReg
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 17.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectCrtfiToReg(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/**
	 * @Method명 : insertCrtfi
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 18.
	 * @Method설명 : 증명서저장
	 */
	public void insertCrtfi(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectCrtfssuList
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 16.
	 * @Method설명 : 증명서발급목록
	 */
	public List<Map<String, Object>> selectCrtfssuList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명 : selectListDtlSelected
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 증명서_리스트(상세조회)
	 */
	public List<Map<String, Object>> selectListDtlSelected(HttpServletRequest request, DataRequest dataRequest) throws Exception;	

	/**
	 * @Method명 : selectListDtl2Selected
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 증명서_리스트(상세조회)
	 */
	public List<Map<String, Object>> selectListDtl2Selected(DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectatendCrft2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public List<Map<String, Object>> selectListAtendCrft2(DataRequest dataRequest) throws Exception;

	/**
	 * @param request 
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 : 증명서출력이력저장
	 */
	public void insertCrtfOtpt(HttpServletRequest request, Map<String, Object> param) throws Exception;

	/**
	 * @Method명   : selectCsemdPicList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 23. 
	 * @Method설명 :
	 */
	public Map<String, String> selectCsemdPicList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : updateCrtfi
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 2. 
	 * @Method설명 :
	 */
	public void updateCrtfi(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectOffcs
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 직인 관련 내용 조회
	 */
	public void selectOffcs(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectWorker
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 6. 14. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectWorker(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectSEB900List
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : (구)시스템 이력
	 */
	//private void selectSEB900List(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
