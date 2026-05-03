/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.url.service;

import isry.itgcms.syscmmn.url.service.vo.ShortUrlVO;

/**
 * @파일명        : ShortURLService.java
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
public interface ShortUrlService {

	/**
	 * @Method명   : selectShortUrl
	 * @param url
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 단축 URL 정보 가저오기
	 */
	public ShortUrlVO selectShortUrl(String sKey);

	/**
	 * @Method명   : createShortUrl
	 * @param domain
	 * @param urlPath
	 * @param method
	 * @param param
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 :
	 */
	public String createShortUrl(String domain, String urlPath, String method, String param);

}
