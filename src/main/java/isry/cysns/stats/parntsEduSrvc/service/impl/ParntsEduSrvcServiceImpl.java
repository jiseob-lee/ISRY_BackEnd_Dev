/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.parntsEduSrvc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.cysns.stats.parntsEduSrvc.mapper.ParntsEduSrvcMapper;
import isry.cysns.stats.parntsEduSrvc.service.ParntsEduSrvcService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : DscsnMdlrtSrvcServiceImpl.java
 * @프로그램 설명 : 상담치료서비스통계 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 5. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 5. 11.
 * @수정내용 : - -
 */
@Service("parntsEduSrvcService")
public class ParntsEduSrvcServiceImpl implements ParntsEduSrvcService {
	@Resource(name = "parntsEduSrvcMapper")
	private ParntsEduSrvcMapper parntsEduSrvcMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명   : selectParntsEduSrvcList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 5. 15. 
	 * @Method설명 : 부모교육 서비스 통계
	 */
	@Override
	public List<Map<String, Object>> selectParntsEduSrvcList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		Map<String, Object> dmParam = new HashMap<String, Object>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		paramMap2.put("ARR_RELM_SE", dmSearch.get("RELM_SE").split(","));
		paramMap2.put("ARR_LEVEL_SE", dmSearch.get("LEVEL_SE").split(","));
		
		return parntsEduSrvcMapper.selectParntsEduSrvcList(paramMap2);
	}
}
