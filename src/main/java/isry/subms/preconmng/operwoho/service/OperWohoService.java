/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operwoho.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : OperWohoService.java
 * @프로그램 설명 : 운영시수 service interface - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 6. 29.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 6. 29.
 * @수정내용 : - -
 */
public interface OperWohoService {
	/**
	 * @Method명 : selectOperWohoList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영시수 목록 조회
	 */
	List<Map<String, Object>> selectOperWohoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectOperWohoMng
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 6.
	 * @Method설명 : 운영시수관리 조회
	 */
	List<Map<String, Object>> selectOperWohoMng(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : saveOperWohoMng
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 4.
	 * @Method설명 : 운영시수 삽입/수정/삭제
	 */
	void saveOperWohoMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
