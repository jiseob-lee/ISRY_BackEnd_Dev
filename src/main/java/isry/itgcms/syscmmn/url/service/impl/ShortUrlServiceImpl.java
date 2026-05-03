/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.url.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.syscmmn.url.mapper.ShortUrlMapper;
import isry.itgcms.syscmmn.url.service.ShortUrlService;
import isry.itgcms.syscmmn.url.service.vo.ShortUrlVO;
import isry.itgcms.util.URLShortener;

/**
 * @파일명        : ShortURLServiceImpl.java
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
@Service("shortUrlService")
public class ShortUrlServiceImpl extends IsryBaseServiceImpl 
	implements ShortUrlService {

	@Resource(name = "shortUrlMapper")
	private ShortUrlMapper shortUrlMapper;
	
	/**
	 * @Method명   : selectShortUrl
	 * @param url
	 * @return
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 :
	 */
	@Override
	public ShortUrlVO selectShortUrl(String sKey) {
		// TODO Auto-generated method stub
		return shortUrlMapper.selectShortUrl(sKey);
	}

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
	@Override
	public String createShortUrl(String domain, String urlPath, String method, String param) {
		ShortUrlVO shortUrl = new ShortUrlVO();
		String shortKey = getShortUrl(urlPath);
		shortUrl.setUrlMngNo(shortKey);
		shortUrl.setDomnNm(domain);
		shortUrl.setOrgnlUrlAddr(urlPath);
		shortUrl.setMethdaTypeNm(method);
		shortUrl.setParaDtlCn(param);
		shortUrl.setFrstRgtrId("SYSTEM");
		shortUrl.setLastMdfrId("SYSTEM");
		
		shortUrlMapper.createShortUrl(shortUrl);
		
		return domain+"/in/"+shortKey + "/s.do";
	}
	
	private String getShortUrl(String urlPath) {
		URLShortener shortener = new URLShortener();
		String shortKey = shortener.shortenURL(urlPath);
		
		ShortUrlVO hasUrl = shortUrlMapper.selectShortUrl(shortKey);
		if(hasUrl != null) {
			return getShortUrl(urlPath);
		}
		return shortKey;
	}

}
