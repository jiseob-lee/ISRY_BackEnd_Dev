/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.common.popup.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.cysns.common.popup.mapper.CysnsPopupMapper;
import isry.cysns.common.popup.service.CysnsPopupService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : PopupServiceImpl.java
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
@Service("cysnsPopupService")
public class CysnsPopupServiceImpl implements CysnsPopupService{
	
	@Resource(name = "cysnsPopupMapper")
	private CysnsPopupMapper cysnsPopupMapper;
	
	//20230629 이승재 - 권한 추가 위한 수정 시작
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	//20230629 이승재 - 권한 추가 위한 수정 끝

	/**
	 * @Method명   : selectTlphonDscsnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 22. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectTlphonDscsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		//20230629 이승재 - 권한 추가 위한 수정 시작(서비스 매개변수 request도 추가)
		// 세션정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));
		paramMap.put("TRPR_NM", param.getValue("TRPR_NM"));
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		//20230629 이승재 - 권한 추가 위한 수정 끝
		
		List<Map<String, String>> selectList = cysnsPopupMapper.selectTlphonDscsnList(paramMap2);
		
		return selectList;
		
	}
}
