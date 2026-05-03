/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.url.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import isry.itgcms.syscmmn.url.service.vo.ShortUrlVO;

/**
 * @파일명        : ShortURLMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.In.Sung
 * @작성일        : 2022. 11. 18. 
 * @수정자        : Lee.In.Sung
 * @수정일        : 2022. 11. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("shortUrlMapper")
public interface ShortUrlMapper {

	/**
	 * @Method명   : selectShortUrl
	 * @param sKey
	 * @return
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 :
	 */
	ShortUrlVO selectShortUrl(String sKey);

	/**
	 * @Method명   : createShortUrl
	 * @param shortUrl
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 :
	 */
	void createShortUrl(ShortUrlVO shortUrl);

}
