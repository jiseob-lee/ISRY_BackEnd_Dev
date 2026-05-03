/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.popup.serivce;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : RecipiPopupService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.08.31
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.08.31
 * @수정내용      : 
 * - [2023-08-31, Gyu.Ha.Baek] PRE 반영               
 * -                
 */
public interface RecipiPopupService {

	public Map<String, Object> selectOrganizationViewList(HttpServletRequest request, DataRequest dataRequest) throws Exception;	
	
}
