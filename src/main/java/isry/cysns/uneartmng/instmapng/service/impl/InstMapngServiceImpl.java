/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.uneartmng.instmapng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.cysns.uneartmng.instmapng.mapper.InstMapngMapper;
import isry.cysns.uneartmng.instmapng.service.InstMapngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;

/**
 * @파일명        : InstMapngServiceImpl.java
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
@Service("instMapngService")
public class InstMapngServiceImpl implements InstMapngService{
	
	@Resource(name = "instMapngMapper")
	private InstMapngMapper instMapngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : selectReqList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = getParamMap(request, dataRequest);

		List<Map<String, String>> selectList = instMapngMapper.selectReqList(paramMap);
		
		return selectList;
	}

	/**
	 * @Method명   : getParamMap
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 24. 
	 * @Method설명 :
	 */
	private Map<String, String> getParamMap(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INST_TYPE_SE_CD",param.getValue("INST_TYPE_SE_CD"));
		paramMap.put("CTPV_CD", param.getValue("CTPV_CD"));
		paramMap.put("INST_NM", param.getValue("INST_NM"));
		return paramMap;
	}

	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("WLFAR_CNTER_NO", param.getValue("WLFAR_CNTER_NO"));

		List<Map<String, String>> result = instMapngMapper.selectReqById(paramMap);
		
		return result; 
	}

	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> allRowList = dsList.getAllRowList();
		for (Map<String, String> map : allRowList) {
			map.put("USER_ID", userId);
			instMapngMapper.saveData(map);
		}
		
	}

	/**
	 * @Method명   : deleteData
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 24. 
	 * @Method설명 :
	 */
	@Override
	public void deleteData(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("WLFAR_CNTER_NO", param.getValue("WLFAR_CNTER_NO"));

		instMapngMapper.deleteData(paramMap);

	}

}
