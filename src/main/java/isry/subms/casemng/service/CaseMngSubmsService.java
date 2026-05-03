/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.subms.casemng.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : CaseMngSubmsService.java
 * @프로그램 설명 : 이주배경 사례관리 관련 Service Interface- -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 8. 7.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 8. 7.
 * @수정내용 : - -
 */
public interface CaseMngSubmsService {

	/**
	 * @Method명 : selectCaseinqPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록
	 */
	public Map<String, Object> selectCaseinqPagingList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;
}
