/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.url.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import isry.base.IsryBaseController;
import isry.itgcms.syscmmn.url.service.ShortUrlService;
import isry.itgcms.syscmmn.url.service.vo.ShortUrlVO;

/**
 * @파일명        : ShortURLController.java
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
@Controller
public class ShortUrlController extends IsryBaseController {
	@Resource(name = "shortUrlService")
	private ShortUrlService shortUrlService;
	
	/**
	 * @Method명   : ShortUrlView
	 * @param request
	 * @param response
	 * @param url
	 * @param redirect
	 * @return
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 단축 URL 
	 */
	@RequestMapping("/in/{url}/s.do")
	public String ShortUrlView(HttpServletRequest request, HttpServletResponse response, @PathVariable("url") String url) {
		// URL 조회
		ShortUrlVO shortUrl = shortUrlService.selectShortUrl(url);
		
		if(shortUrl != null) {
				String param = shortUrl.getParaDtlCn();
				String path = shortUrl.getOrgnlUrlAddr();
				String newPath = "";
				if(path.indexOf("?") > -1) {
					newPath = shortUrl.getOrgnlUrlAddr() + "&" + shortUrl.getParaDtlCn();
				} else {
					newPath = shortUrl.getOrgnlUrlAddr() + "?" + shortUrl.getParaDtlCn();
				}
				shortUrl.setOrgnlUrlAddr(newPath);
			return "forward:"+shortUrl.getOrgnlUrlAddr();
		} else {
			return "forward:/";
		}
	}

	
	/**
	 * @Method명   : getQueryMap
	 * @param query
	 * @return
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 :URL 파라미터를 파싱한다
	 */
    public static Map<String, String> getQueryMap(String query)
    {    	
    	if (query==null) return null;
    	
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
	}

    /**
     * @Method명   : convertToHashMaptoQueryString
     * @param params
     * @return
     * @작성자     : Lee.In.Sung
     * @작성일     : 2022. 11. 18. 
     * @Method설명 : 맵 데이터를 쿼리 스트링으로 반환 한다.
     */
    public static String convertToHashMaptoQueryString(
            Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        Iterator<?> iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            if (sb.length() > 0) {
                sb.append('&');
            }//w  w  w. j ava2s .  c o m
            Entry<?, ?> entry = (Entry<?, ?>) iter.next();
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

}
