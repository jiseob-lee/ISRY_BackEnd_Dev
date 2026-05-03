/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.emrgcntrms.emrgrpt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;
import isry.regns.emrgcntrms.emrgrpt.mapper.EmrgRptMapper;
import isry.regns.emrgcntrms.emrgrpt.service.EmrgRptService;
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
@Service("emrgRptService")
public class EmrgRptServiceImpl implements EmrgRptService{
	
	@Resource(name = "emrgRptMapper")
	private EmrgRptMapper emrgRptMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명   : selectKeyValue
	 * @param userId
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

		mngNoMap.put("EMRG_CNTRMS_INCDNT_NO", emrgRptMapper.selectKeyValue(userId));

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
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INCDNT_TTL_NM", "");
		paramMap.put("PIC_NM", "");
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));

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

		List<Map<String, String>> selectList = emrgRptMapper.selectReqList(paramMap2);
		
		return selectList;
		
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
		String mngNo = param.getValue("EMRG_CNTRMS_INCDNT_NO");

		List<Map<String, String>> result = emrgRptMapper.selectReqById(mngNo);
		
		return result; 
	}

	/**
	 * @Method명   : selectDtlById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 1. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDtlById(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("EMRG_CNTRMS_INCDNT_NO");

		List<Map<String, String>> result = emrgRptMapper.selectDtlById(mngNo);
		
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
		
		saveHdrData(userId, dataRequest); //긴급대응보고
		saveDtlData(userId, dataRequest); //간급대응보고 대상자

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
		String mngNo = param.getValue("EMRG_CNTRMS_INCDNT_NO");

		emrgRptMapper.deleteData(mngNo);
		emrgRptMapper.deleteAllDltData(mngNo);
	}

	/**
	 * @Method명   : saveHdrData
	 * @param userId
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 4. 
	 * @Method설명 :
	 */
	private void saveHdrData(String userId, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("USER_ID", userId);
			emrgRptMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("USER_ID", userId);
			emrgRptMapper.saveData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			String mngNo = map.get("EMRG_CNTRMS_INCDNT_NO");
			emrgRptMapper.deleteData(mngNo);
			emrgRptMapper.deleteAllDltData(mngNo);
		}
		
	}

	/**
	 * @Method명   : saveDtlData
	 * @param  userId, emrgCntrmsIncdntNo, dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 1. 
	 * @Method설명 :
	 */
	private void saveDtlData(String userId, DataRequest dataRequest) throws Exception  {
		
		ParameterGroup dsDtlList = dataRequest.getParameterGroup("dsDtlList");
		
		List<Map<String, String>> insertedRowList = dsDtlList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("USER_ID", userId);
			emrgRptMapper.insertDtlData(map);
		}

		List<Map<String, String>> updatedRowList = dsDtlList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("USER_ID", userId);
			emrgRptMapper.updateDtlData(map);
		}

		List<Map<String, String>> deletedRowList = dsDtlList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			emrgRptMapper.deleteDtlData(map);
		}

	}
	
}
