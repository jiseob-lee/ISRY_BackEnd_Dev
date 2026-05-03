/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmsr.casemng.recvrytrmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.pubms.casemng.sheltrtrmn.mapper.SheltrTrmnMapper;
import isry.pubmsr.casemng.recvrytrmn.mapper.RecvryTrmnMapper;
import isry.pubmsr.casemng.recvrytrmn.service.RecvryTrmnService;

/**
 * @파일명        : RecvryTrmnServiceImpl.java
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
@Service("recvryTrmnService")
public class RecvryTrmnServiceImpl implements RecvryTrmnService{
	
	@Resource(name = "recvryTrmnMapper")
	private RecvryTrmnMapper recvryTrmnMapper;

	@Resource(name = "sheltrTrmnMapper")
	private SheltrTrmnMapper sheltrTrmnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

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
		Map<String, String> map = param.getSingleValueMap();

		List<Map<String, String>> result = recvryTrmnMapper.selectReqById(map);
		
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
		
		ParameterGroup param = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsList = param.getAllRowList();
		Map<String, String> paramMap = dsList.get(0);
		paramMap.put("USER_ID", userId);
		sheltrTrmnMapper.saveData(paramMap);
		
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
		Map<String, String> paramMap = new HashMap<>();
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		sheltrTrmnMapper.deleteData(paramMap);
	}

	/**
	 * @Method명   : saveEntrncXtndData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 25. 
	 * @Method설명 :
	 */
	@Override
	public void saveEntrncXtndData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsEntrncXtnd");

		List<Map<String, String>> updatedRowList = param.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("NEW_XTND_SE_CD", "2");
			map.put("USER_ID", userId);
			recvryTrmnMapper.saveEntrncXtndData(map);
		}

		List<Map<String, String>> deletedRowList = param.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			map.put("NEW_XTND_SE_CD", "1");
			map.put("PRTCTN_DSPS_XTND_BGNG_YMD", "");
			map.put("PRTCTN_DSPS_XTND_END_YMD", "");

			map.put("PRTCTN_DSPS_XTND_APLCNT_SE_CD", "");
			map.put("PRTCTN_DSPS_XTND_APLCNT_ETC_CN", "");
			map.put("XTND_APLY_COURT_SE_CD", "");

			map.put("PRTCTN_DSPS_XTND_CS_SE_CD", "");
			map.put("PRTCTN_DSPS_XTND_CS_ETC_CN", "");

			map.put("USER_ID", userId);
			recvryTrmnMapper.saveEntrncXtndData(map);
		}

	}

}
