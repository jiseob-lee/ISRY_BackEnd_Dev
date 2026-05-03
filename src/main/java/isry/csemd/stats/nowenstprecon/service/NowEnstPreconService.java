/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.nowenstprecon.service;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : NowEnstPreconService.java
 * @프로그램 설명 : 현재 입교생 현황 서비스 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 6.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 6.
 * @수정내용 : - -
 */
public interface NowEnstPreconService {
	/**
	 * @Method명 : selectNowEnstPrecon
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 6.
	 * @Method설명 : 현재입교생현황 조회
	 */
	public void selectNowEnstPrecon(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
