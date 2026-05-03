/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operwoho.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : OperWohoMapper.java
 * @프로그램 설명 : 운영시수 mapper interface - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 6. 29.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 6. 29.
 * @수정내용 : - -
 */
@Mapper("operWohoMapper")
public interface OperWohoMapper {

	/**
	 * @Method명 : selectOperWohoList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영시수 목록 조회
	 */
	List<Map<String, Object>> selectOperWohoList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectOperWohoMng
	 * @param paraMap
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 7. 6. 
	 * @Method설명 : 운영시수관리 조회
	 */
	List<Map<String, Object>> selectOperWohoMng(Map<String, Object> paraMap) throws Exception;

	/**
	 * @Method명 : chkOperWohoMng
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 5.
	 * @Method설명 : 운영시수 존재여부 확인
	 */
	Map<String, String> chkOperWohoMng(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : insertOperWohoMng
	 * @param map
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 4.
	 * @Method설명 : 운영시수 삽입
	 */
	void insertOperWohoMng(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : updateOperWohoMng
	 * @param map
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 4.
	 * @Method설명 : 운영시수 수정
	 */
	void updateOperWohoMng(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : deleteOperWohoMng
	 * @param map
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 4.
	 * @Method설명 : 운영시수 삭제
	 */
	void deleteOperWohoMng(Map<String, String> map) throws Exception;

}
