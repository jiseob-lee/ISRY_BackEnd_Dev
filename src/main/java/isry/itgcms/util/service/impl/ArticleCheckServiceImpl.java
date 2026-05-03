/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import isry.itgcms.sysmgmt.cmmncode.mapper.MgmtCmmnCodeMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.IP;
import isry.itgcms.util.mapper.ArticleCheckMapper;
import isry.itgcms.util.service.ArticleCheckService;

/**
 * @파일명        : ArticleCheckServiceImpl.java
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
@Service("articleCheckService")
public class ArticleCheckServiceImpl implements ArticleCheckService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="articleCheckMapper")
    private ArticleCheckMapper articleCheckMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name="mgmtCmmnCodeMapper")
    private MgmtCmmnCodeMapper mgmtCmmnCodeMapper;
	
	public int checkDuplicateArticleRegist(String methodName, HttpServletRequest request) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		//LocalDateTime checkTime = LocalDateTime.now();
		//checkTime = checkTime.minusMinutes(1);  // 1분 이내에 다시 등록 안됨.
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//String limitTime = dtf.format(checkTime);
		
		String limitTime = mgmtCmmnCodeMapper.getSysDateMinus1Minute(null);
		
		String ip = IP.getClientIP(request);
		
		Map<String, String> map = new HashMap<>();
		map.put("IP", ip);
		map.put("METHDA_NM", methodName);
		map.put("LIMIT_TIME", limitTime);
		map.put("FRST_RGTR_ID", userId);
		
		log.info("#### IP : " + ip);
		log.info("#### METHDA_NM : " + methodName);
		log.info("#### LIMIT_TIME : " + limitTime);
		log.info("#### FRST_RGTR_ID : " + userId);
		
		return articleCheckMapper.checkDuplicateArticleRegist(map);
	}
	
}
