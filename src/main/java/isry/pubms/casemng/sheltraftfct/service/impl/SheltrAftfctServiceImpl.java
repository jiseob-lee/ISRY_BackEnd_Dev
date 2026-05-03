/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltraftfct.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.pubms.casemng.sheltraftfct.mapper.SheltrAftfctMapper;
import isry.pubms.casemng.sheltraftfct.service.SheltrAftfctService;
import isry.redis.service.RedisService;

/**
 * @파일명        : SheltrRegServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("sheltrAftfctService")
public class SheltrAftfctServiceImpl implements SheltrAftfctService{
	
	@Resource(name = "sheltrAftfctMapper")
	private SheltrAftfctMapper sheltrAftfctMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : processCaseEsntalRegString
	 * @param request
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @param sNewCaseMngOdrno
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 20. 
	 * @Method설명 :
	 */
	@Override
	public void processCaseEsntalRegString(HttpServletRequest request, String sCaseMngNo, String sCaseMngOdrno, String sNewCaseMngOdrno) throws Exception {
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("CASE_MNG_NO", sCaseMngNo);
		paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
		paramMap.put("NEW_CASE_MNG_ODRNO", sNewCaseMngOdrno);
		paramMap.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));
		
		sheltrAftfctMapper.saveCaseInfoData(paramMap);
		sheltrAftfctMapper.saveAsessRcordData(paramMap);
		
	}

}
