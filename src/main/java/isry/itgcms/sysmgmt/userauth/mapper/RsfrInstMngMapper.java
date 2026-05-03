/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : RsfrInstMngMapper.java
 * @프로그램 설명 : 순수자원제공주체기관 관리
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("rsfrInstMngMapper")
public interface RsfrInstMngMapper {
	
	/**
	 * @Method명   : selectRsfrInstMngList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 목록
	 */
	public List<Map<String, Object>> selectRsfrInstMngList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectRsfrInstDetail
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 자원제공주체기관 상세정보
	 */
	public List<Map<String, Object>> selectRsfrInstDetail(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectRsfrInstHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 6. 
	 * @Method설명 : 순수자원제공주체기관 이력조회
	 */
	public List<Map<String, Object>> selectRsfrInstHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectInstDuplCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 10. 
	 * @Method설명 : 순수자원제공주체기관 기관번호 확인
	 */
	public Integer selectInstNoExists(Integer iInstNo) throws Exception;
	
	/**
	 * @Method명   : insertRsfrInst
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공주체기관 등록
	 */
	public Integer insertRsfrInst(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertRsfrInstHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공주체기관 이력등록
	 */
	public Integer insertRsfrInstHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateRsfrInst
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공주체기관 수정
	 */
	public Integer updateRsfrInst(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteRsfrInst
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공주체기관 삭제
	 */
	public Integer deleteRsfrInst(Map<String, String> paramMap) throws Exception;
	
	
	

}
