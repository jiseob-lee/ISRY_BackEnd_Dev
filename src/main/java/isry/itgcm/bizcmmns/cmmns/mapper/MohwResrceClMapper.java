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
 * @파일명        : MohwResrceClMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 7. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mohwResrceClMapper")
public interface MohwResrceClMapper {
	/**
	 * @Method     : selectMohwResrceClList
	 * @Method설명 : (보건)복지부자원분류 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 7. 
 	 */	
	public List<Map<String, Object>> selectMohwResrceClList(Map<String, String> paramMap) throws Exception;

}
