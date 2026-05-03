/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.transportation.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import isry.aimns.opermgmt.transportation.mapper.TransPortationMapper;
import isry.aimns.opermgmt.transportation.service.TransPortationService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;



/**
 * @파일명        : TransPortationImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 6. 9. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 6. 9.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("transPortationService")
public class TransPortationServiceImpl implements TransPortationService{

	@Resource(name ="transPortationMapper")
	private TransPortationMapper transPortationMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	private final Masking mask = new Masking();

	/**
	 * @Method명   : selectTransFoodList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 10. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectTransFoodList(HttpServletRequest request, Map<String, String> map) throws Exception {


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String unt_taskwk = loginVO.getUntTaskwk();
		map.put("UNT_TASKWK_SE_CD", unt_taskwk);
		
		List<Map<String, String>> result = transPortationMapper.selectTransFoodList(map);
		
		for(Map<String, String> mmap : result) {
			mmap.replace("TRPR_NM_ENCPT", Masking.nameMasking(mmap.get("TRPR_NM_ENCPT")));
		}
		
		return result;
	}

}
