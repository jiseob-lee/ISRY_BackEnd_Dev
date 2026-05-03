/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service;


/**
 * @파일명     	: LinkMmaRcptJobService.java
 * @프로그램 설명	: 병무청 관련 연계
 * @작성자      	: Lee.Tae.Ho
 * @작성일      	: 2022. 08. 04. 
 * @수정자      	: Lee.Tae.Ho
 * @수정일      	: 2022. 08. 04.
 * @수정내용    	: 병무청 관련 연계
 * -                
 * -                
 */
public interface LinkMmaRcptJobService {
	
	/**
	 * @Method		: linkMohwPvsnSrvc
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 04. 
 	 */
	public void linkMmaTrmnPrcs() throws Exception;

	/**
	 * @Method		: linkMmaSprt
	 * @Method설명 	: 심리취약 병역의무자지원 구분 및 자원기관정보
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 
 	 */	
	public void linkMmaSprt()  throws Exception;
	
	/**
	 * @Method		: saveCaseReg
	 * @Method설명 	: 병무청 연계 사례등록
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 
 	 */	
	public void linkMmaCaseReg()  throws Exception;
}
