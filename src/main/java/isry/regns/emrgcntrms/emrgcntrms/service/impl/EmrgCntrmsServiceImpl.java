/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.emrgcntrms.emrgcntrms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.regns.emrgcntrms.emrgcntrms.mapper.EmrgCntrmsMapper;
import isry.regns.emrgcntrms.emrgcntrms.service.EmrgCntrmsService;

/**
 * @파일명        : EmrgCntrmsServiceImpl.java
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
@Service("emrgCntrmsService")
public class EmrgCntrmsServiceImpl implements EmrgCntrmsService{
	
	@Resource(name = "emrgCntrmsMapper")
	private EmrgCntrmsMapper emrgCntrmsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명   : selectReqList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INCDNT_TTL_NM", "");
		paramMap.put("PIC_NM", "");
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));
		paramMap.put("EMRG_CNTRMS_INCDNT_LCLAS_SE_CD", param.getValue("EMRG_CNTRMS_INCDNT_LCLAS_SE_CD"));
		paramMap.put("EMRG_CNTRMS_INCDNT_MLSFC_SE_CD", param.getValue("EMRG_CNTRMS_INCDNT_MLSFC_SE_CD"));
		paramMap.put("EMRG_CNTRMS_INCDNT_SCLAS_SE_CD", param.getValue("EMRG_CNTRMS_INCDNT_SCLAS_SE_CD"));

		String[] srchSes = param.getValue("SRCH_SE").split(",");

		for (String srchSe : srchSes) {
			if ("1".equals(srchSe)) paramMap.put("PIC_NM", param.getValue("SRCH_NM"));
			if ("2".equals(srchSe)) paramMap.put("INCDNT_TTL_NM", param.getValue("SRCH_NM"));
		}

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO.getUntTaskwk().equals("U02")) {
			paramMap.put("AUTHRT", "1");
//		} else {
//			paramMap.put("AUTHRT", CommUtils.getAuthrt(loginVO.getGroupAuthrtSeCd()));
		}
			
		/*20230126_강화영_권한 적용_시작*/
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	

		List<Map<String, String>> selectList = emrgCntrmsMapper.selectReqList(paramMap2);
		
		return selectList;
		
	}

}
