/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.cmmn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @파일명 : SubmsService.java
 * @프로그램 설명 : 이주배경 공통 서비스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 13.
 * @수정내용 : - -
 */
public interface SubmsService {

	public List<Map<String, Object>> selectBizYrCombo(HttpServletRequest request) throws Exception;

	public List<Map<String, Object>> selectSrvcExcnBizCombo(HttpServletRequest request) throws Exception;

	public List<Map<String, Object>> selectInstNmCombo(HttpServletRequest request) throws Exception;

	public List<Map<String, Object>> selectResrceNmCombo(HttpServletRequest request) throws Exception;

}
