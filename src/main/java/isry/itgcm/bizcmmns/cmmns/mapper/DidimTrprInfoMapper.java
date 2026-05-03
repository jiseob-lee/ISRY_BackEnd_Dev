/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DidimTrprInfoMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 9. 15. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 9. 15.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("didimTrprInfoMapper")
public interface DidimTrprInfoMapper {
	
	/**
	* 대상자정보 목록조회(디딤,드림)
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectDidimTrprInfoList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectDidimTrprDetail
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 9. 15.
	 * @Method설명 : 대상자 상세조회(디딤,드림)
	 */
	public Map<String, Object> selectDidimTrprDetail(Map<String, String> paramMap) throws Exception;
	
	

}
