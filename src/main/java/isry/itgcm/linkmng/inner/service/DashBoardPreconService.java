/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.inner.service;

/**
 * @파일명     	: DashBoardPreconService.java
 * @프로그램 설명	: 메인 대시보드에 표현되는 현황별 집계 데이터 생성을 위한 배치
 * - 
 * - 
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 11. 3.
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 11. 3.
 * @수정내용    	: 
 * -                
 * -                
 */
public interface DashBoardPreconService {
	
	/**
	 * @Method		: createPreconTotData
	 * @Method설명 	: 
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 
 	 */	
	public void createPreconTotData()  throws Exception;
}
