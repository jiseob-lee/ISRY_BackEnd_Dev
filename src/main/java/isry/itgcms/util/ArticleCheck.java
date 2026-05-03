/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import isry.itgcms.util.mapper.ArticleCheckMapper;
import isry.itgcms.util.service.ArticleCheckService;

/**
 * @파일명        : ArticleCheck.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2022. 12. 26. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2022. 12. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Component
public class ArticleCheck {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="articleCheckService")
    private ArticleCheckService articleCheckService;

	public boolean checkDuplicateArticleRegist(String methodName, HttpServletRequest request) throws Exception {
		
		int result = articleCheckService.checkDuplicateArticleRegist(methodName, request);
		
		return result > 0;
	}
}
