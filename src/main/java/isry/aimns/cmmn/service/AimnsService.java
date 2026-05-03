/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.cmmn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @파일명        : AimnsService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 6. 7. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 6. 7.
 * @수정내용      : 
 * -                
 * -                
 */
public interface AimnsService {

	/**
	 * @Method명   : selectBizYrCombo
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectBizYrCombo() throws Exception;

	/**
	 * @Method명   : selectInstCombo
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectInstCombo() throws Exception;

	/**
	 * @Method명   : selectProgrmCombo
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectProgrmCombo() throws Exception;

	/**
	 * @Method명   : selectResrceCombo
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 30. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectResrceCombo() throws Exception;

	
}
