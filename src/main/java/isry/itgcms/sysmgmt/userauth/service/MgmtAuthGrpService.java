/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MgmtAuthGrpService.java
 * @프로그램 설명 : 권한 그룹 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MgmtAuthGrpService {
	
	public void saveAuthGrp(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void savePersonalAuthGrpMapping(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : saveMenuAuthMapping
	 * @param request		
	 * @param dataRequest
	 * @param dataMapId		Parameter 데이터맵 ID (기본값: dmParam)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 메뉴 권한 저장 처리
	 * <pre>
	 * 	- 회원 가입 승인시 사용<br>
	 *  - 권한 승인시 사용
	 * </pre>
	 */
	Map<String, Object> saveMenuAuthMapping(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : saveMenuAuthMapping
	 * @param request
	 * @param mapParam	Map 형식의 Parameter
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 24. 
	 * @Method설명 : 메뉴 권한 저장 처리
	 * <pre>
	 * 	- 회원 가입 승인시 사용<br>
	 *  - 권한 승인시 사용
	 * </pre>
	 */
	<K, V> Map<String, Object> saveMenuAuthMapping(HttpServletRequest request, Map<? super K, ? super V> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteMenuAuthMapping
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		Parameter 데이터맵 ID (기본값: dmParam)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 삭제 처리
	 * <pre>
	 * 	- 내 정보 수정시 사용<br>
	 *  - 기관 권한 삭제시 사용
	 * </pre>
	 */
	Map<String, Object> deleteMenuAuthMapping(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : deleteMenuAuthMapping
	 * @param request
	 * @param mapParam	Map 형식의 Parameter
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 삭제 처리
	 * <pre>
	 * 	- 내 정보 수정시 사용<br>
	 *  - 기관 권한 삭제시 사용
	 * </pre>
	 */
	<K, V> Map<String, Object> deleteMenuAuthMapping(HttpServletRequest request, Map<? super K, ? super V> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectMenuAuthTemplateList
	 * @param request
	 * @param mapParam	Map 형식의 Parameter
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 13. 
	 * @Method설명 : 메뉴 권한 템플릿 목록 조회
	 */
	<K, V> List<Map<String, Object>> selectMenuAuthTemplateList(HttpServletRequest request, Map<? super K, ? super V> mapParam) throws Exception;
}
