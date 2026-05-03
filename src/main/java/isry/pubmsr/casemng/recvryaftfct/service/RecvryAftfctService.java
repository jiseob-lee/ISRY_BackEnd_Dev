/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmsr.casemng.recvryaftfct.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @파일명        : RecvryAftfctService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 12. 20. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 12. 20.
 * @수정내용      : 
 * -                
 * -                
 */
public interface RecvryAftfctService {

	public void processCaseEsntalRegString(HttpServletRequest request, String sCaseMngNo, String sCaseMngOdrno, String sNewCaseMngOdrno) throws Exception;
	
}
