/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명     	: LinkMohwJobService.java
 * @프로그램 설명	: 복지부 관련 연계
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 08. 01. 
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 08. 01.
 * @수정내용    	: 복지부 관련 연계
 * -                
 * -                
 */
public interface LinkMohwJobService {

	/**
	 * @Method		: linkMohwPvsnMby
	 * @Method설명 	: 
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 
 	 */	
	public void linkMohwPvsnMby()  throws Exception;
	
	/**
	 * @Method		: linkMohwPvsnSrvc
	 * @Method설명 	: 공유자원 제공서비스 사보정 송신 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 01. 
 	 */
	public void linkMohwPvsnSrvc() throws Exception;
	
	/**
	 * @Method		: linkMohwDscsnHstr
	 * @Method설명 	: 상담이력 사보정 송신 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 03. 
 	 */
	public void linkMohwDscsnHstr() throws Exception;
	
	/**
	 * @Method		: linkMohwPvsnSrvc
	 * @Method설명 	: 서비스이력 사보정 송신 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 03. 
 	 */
	public void linkMohwSrvcHstr() throws Exception;
	
	/**
	 * @Method		: linkMohwCaseMng
	 * @Method설명 	: 사례관리 사보정 송신 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 04. 
 	 */
	public void linkMohwCaseMng() throws Exception;

	/**
	 * @Method		: linkMohwSrvcRqstRcpt
	 * @Method설명 	: 서비스의뢰 접수 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */
//	public Map<String, Object> linkMohwSrvcRqstRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public void linkMohwSrvcRqstRcpt() throws Exception;
}
