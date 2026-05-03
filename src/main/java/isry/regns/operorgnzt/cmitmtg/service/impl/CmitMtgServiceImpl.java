/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.operorgnzt.cmitmtg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.clipsoft.org.apache.commons.lang.StringUtils;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.ScpDb;
import isry.regns.operorgnzt.cmitmtg.mapper.CmitMtgMapper;
import isry.regns.operorgnzt.cmitmtg.service.CmitMtgService;

/**
 * @파일명        : CmitMtgServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 22. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 22.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("cmitMtgService")
public class CmitMtgServiceImpl implements CmitMtgService{
	
	@Resource(name = "cmitMtgMapper")
	private CmitMtgMapper cmitMtgMapper;

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

		mngNoMap.put("MTG_NO", cmitMtgMapper.selectKeyValue(userId));

		return mngNoMap;
	}

	/**
	 * @Method명   : selectReqList
	 * @param request, dataRequest
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

		paramMap.put("CMIT_SE_CD", param.getValue("CMIT_SE_CD"));
		paramMap.put("ESNTL_LINK_INST_SE_CD", param.getValue("ESNTL_LINK_INST_SE_CD"));
		paramMap.put("MTG_ITOAGD_CN", param.getValue("MTG_ITOAGD_CN"));
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO.getUntTaskwk().equals("U02") && !StringUtils.isEmpty(param.getValue("CMIT_SE_CD"))) {
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

		return cmitMtgMapper.selectReqList(paramMap2);
		
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
		String mngNo = param.getValue("MTG_NO");

		return cmitMtgMapper.selectReqById(mngNo);
		
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
	public List<Map<String, String>> selectAtndById(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("MTG_NO");

		List<Map<String, String>> resultList = cmitMtgMapper.selectAtndById(mngNo);
		
		return resultList; 
	}

	/**
	 * @Method명   : selectItoagdById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectItoagdById(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("MTG_NO");

		List<Map<String, String>> resultList = cmitMtgMapper.selectItoagdById(mngNo);
		
		return resultList; 
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
		
		saveHdrData(request, dataRequest); //위원회회의
		saveAtndData(request, dataRequest); //회의참석자
		saveItoagdData(request, dataRequest); //회의안건
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
	private void saveHdrData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));     //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));     //사용자Id
			cmitMtgMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));     //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));     //사용자Id
			cmitMtgMapper.saveData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			String mngNo = map.get("MTG_NO");
			cmitMtgMapper.deleteData(mngNo);
			cmitMtgMapper.deleteAllAtndData(mngNo);
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
	private void saveAtndData(HttpServletRequest request, DataRequest dataRequest) throws Exception  {
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsAtndList");
		
		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));     //사용자Id
			cmitMtgMapper.insertAtndData(map);
		}

		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));     //사용자Id
			cmitMtgMapper.updateAtndData(map);
		}

		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			cmitMtgMapper.deleteAtndData(map);
		}

	}

	/**
	 * @Method명   : saveItoagdData
	 * @param request
	 * @param dataRequest
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 :
	 */
	private void saveItoagdData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsItoagdList");
		
		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));     //사용자Id
			cmitMtgMapper.insertItoagdData(map);
		}


		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));     //사용자Id
			cmitMtgMapper.updateItoagdData(map);
		}

		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			cmitMtgMapper.deleteItoagdData(map);
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
		String mngNo = param.getValue("MTG_NO");

		cmitMtgMapper.deleteData(mngNo);
		cmitMtgMapper.deleteAllAtndData(mngNo);
		cmitMtgMapper.deleteAllItoagdData(mngNo);
	}

}
