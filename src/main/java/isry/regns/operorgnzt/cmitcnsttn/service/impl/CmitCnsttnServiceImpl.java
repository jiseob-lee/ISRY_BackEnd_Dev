/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.operorgnzt.cmitcnsttn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.clipsoft.org.apache.commons.lang.StringUtils;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;
import isry.regns.operorgnzt.cmitcnsttn.mapper.CmitCnsttnMapper;
import isry.regns.operorgnzt.cmitcnsttn.service.CmitCnsttnService;
import lombok.extern.slf4j.Slf4j;

/**
 * @파일명        : LinkInstServiceImpl.java
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
@Service("cmitCnsttnService")
public class CmitCnsttnServiceImpl implements CmitCnsttnService{
	
	@Resource(name = "cmitCnsttnMapper")
	private CmitCnsttnMapper cmitCnsttnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명   : selectKeyValue
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 4. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectKeyValue(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> mngNoMap = new HashMap<>();
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		mngNoMap.put("CMIT_CNSTTN_MNG_NO", cmitCnsttnMapper.selectKeyValue(userId));

		return mngNoMap;

	}

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
		
		Map<String, String> paramMap = getParamMap(dataRequest);

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
		
		List<Map<String, String>> result = cmitCnsttnMapper.selectReqList(paramMap2);
		
		return result;
	}

	/**
	 * @Method명   : getParamMap
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 :
	 */
	private Map<String, String> getParamMap(DataRequest dataRequest) {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INST_SE", param.getValue("INST_SE"));
		paramMap.put("CMIT_SE_CD", param.getValue("CMIT_SE"));
		paramMap.put("ACTVT_YN", param.getValue("ACTVT_YN"));
		paramMap.put("ENTRST_PIC_NM", param.getValue("PIC_NM"));
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));
		
		return paramMap;
	}

	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("CMIT_CNSTTN_MNG_NO");

		List<Map<String, String>> result = cmitCnsttnMapper.selectReqById(mngNo);
		for (Map<String, String> map : result) {
			
			if (!StringUtils.isEmpty(map.get("PIC_MBL_TELNO_ENCPT"))) {
				map.put("PIC_MBL_TELNO", Formatter.phoneFormat(map.get("PIC_MBL_TELNO"), 1));
			}

			if (!StringUtils.isEmpty(map.get("OFFM_TELNO"))) {
				map.put("OFFM_TELNO", Formatter.phoneFormat(map.get("OFFM_TELNO"), 1));
			}
		}
		
		return result; 
	}

	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
				
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		
		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));      //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", userId);
			cmitCnsttnMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));      //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", userId);
			cmitCnsttnMapper.saveData(map);
		}
		

		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			String mngNo = map.get("CMIT_CNSTTN_MNG_NO");
			cmitCnsttnMapper.deleteData(mngNo);
		}
		
	}
	
	/**
	 * @Method명   : deleteData
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 10. 
	 * @Method설명 :
	 */
	@Override
	public void deleteData(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("CMIT_CNSTTN_MNG_NO");

		cmitCnsttnMapper.deleteData(mngNo);
	}

	/**
	 * @Method명   : selectSugrCnsttnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 2. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSugrCnsttnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = getParamMap(dataRequest);

		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		List<Map<String, String>> result = cmitCnsttnMapper.selectSugrCnsttnList(paramMap2);
		
		return result;
	}

}
