/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MohwResrceClService.java
 * @프로그램 설명 : 복지부자원분류(팝업) Service Class
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
public interface MohwResrceClService {
	
	/**
	 * @Method     : selectMohwResrceClList
	 * @Method설명 : 보건복지부자원분류 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectMohwResrceClList(DataRequest dataRequest) throws Exception;

}
