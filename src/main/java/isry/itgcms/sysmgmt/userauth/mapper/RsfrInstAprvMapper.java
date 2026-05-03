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
 * @파일명        : RsfrInstAprvMapper.java
 * @프로그램 설명 : 순수자원제공주체 승인
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 6. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 6.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("rsfrInstAprvMapper")
public interface RsfrInstAprvMapper {
	
	/**
	 * @Method명   : selectRsfrInstAprvList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관승인 목록
	 */
	public List<Map<String, Object>> selectRsfrInstAprvList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateRsfrInstAprv
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 승인처리
	 */
	public Integer updateRsfrInstAprv(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateRsfrInstRjct
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 반려처리
	 */
	public Integer updateRsfrInstRjct(Map<String, Object> paramMap) throws Exception;
	

}
