/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.emrgcntrms.emrgactn.service.impl;

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
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.ScpDb;
import isry.regns.emrgcntrms.emrgactn.mapper.EmrgActnMapper;
import isry.regns.emrgcntrms.emrgactn.service.EmrgActnService;

/**
 * @파일명        : EmrgActnServiceImpl.java
 * @프로그램 설명        : 긴급대응사건처리에 CRUD 처리
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 3.
 * @수정내용       : 삭제기능 추가
 * -                
 * -                
 */
//@Slf4j
@Service("emrgActnService")
public class EmrgActnServiceImpl implements EmrgActnService{
	
	@Resource(name = "emrgActnMapper")
	private EmrgActnMapper emrgActnMapper;

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

		mngNoMap.put("EMRG_CNTRMS_ACTN_NO", emrgActnMapper.selectKeyValue(userId));

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
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("INCDNT_TTL_NM", "");
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
		
		List<Map<String, String>> resultList = emrgActnMapper.selectReqList(paramMap2);
		
		return resultList;
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

		return emrgActnMapper.selectReqById(mngNo); 
	}

	/**
	 * @Method명   : selectEmrgRptById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEmrgRptById(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("EMRG_CNTRMS_INCDNT_NO");

		List<Map<String, String>> result = emrgActnMapper.selectEmrgRptById(mngNo);
		
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
			emrgActnMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));      //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", userId);
			emrgActnMapper.saveData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			emrgActnMapper.deleteDtlData(map);
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
		String mngNo = param.getValue("EMRG_CNTRMS_INCDNT_NO");

		emrgActnMapper.deleteData(mngNo);
	}

}
