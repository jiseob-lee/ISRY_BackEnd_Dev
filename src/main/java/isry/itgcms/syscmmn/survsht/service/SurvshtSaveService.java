/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.ParameterGroup;

/**
 * @파일명        : SurvshtSaveService.java
 * @프로그램 설명 :    응용1팀 설문지 저장 관리 서비스
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2023. 3. 20. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2023. 3. 20.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SurvshtSaveService {
	
	/**
	 * @Method명   : saveSrvyResult
	 * @param request
	 * @param paramSrvy
	 * @param paramRelm
	 * @param map
	 * @param qustnbShape
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 20. 
	 * @Method설명 :
	 */
	public void saveSrvy(HttpServletRequest request, ParameterGroup paramSrvy, ParameterGroup paramRelm, Map<String, String> map, String qustnbShape) throws Exception;

}
