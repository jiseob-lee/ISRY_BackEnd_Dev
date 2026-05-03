/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.sms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.syscmmn.sms.service.RsvtSmsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;
import isry2.itgcms.syscmmn.sms.mapper.RsvtSmsMapper;

/**
 * @파일명        : RsvtSmsServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 13. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("rsvtSmsService")
public class RsvtSmsServiceImpl implements RsvtSmsService {

	@Resource(name = "rsvtSmsMapper")
	private RsvtSmsMapper rsvtSmsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Method명   : selectSmsRcptnTrprList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSmsRcptnTrprList(HttpServletRequest request, DataRequest dataRequest)
		throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> searchMap = dmSearch.getAllRowList().get(0);
		
		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);
		
		searchMap.put("UNT_TASKWK_SE_CD", loginVo.getUntTaskwk());
		
		List<Map<String, String>> result = rsvtSmsMapper.selectSmsRcptnTrprList(searchMap);
		
		log.error("#### resultresultresultresultresult : " + result);
		
		for(Map<String, String> map : result) {
			map.put("MASKING_RCPTN_TRPR_NM_ENCPT", Masking.nameMasking(map.get("RCPTN_TRPR_NM_ENCPT")));
			map.put("MASKING_RCPTN_MBL_TELNO_ENCPT", Masking.phoneMasking(map.get("RCPTN_MBL_TELNO_ENCPT")));
			map.put("MASKING_TRPR_NM_ENCPT", Masking.nameMasking(map.get("TRPR_NM_ENCPT")));
			map.put("MASKING_USER_NAME", Masking.nameMasking(map.get("USER_NAME")));
			map.put("MASKING_CALL_FROM", Masking.phoneMasking(map.get("CALL_FROM")));
		}
		
		log.error("#### resultresultresultresultresult : " + result);
		
		return result;
	}
	
	
	
	
}
