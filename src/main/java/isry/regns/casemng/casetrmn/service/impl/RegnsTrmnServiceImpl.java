/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.casemng.casetrmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.casemng.casereg.mapper.CysnsRegMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.syscmmn.survsht.service.SurvshtSaveService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.redis.service.RedisService;
import isry.regns.casemng.casereg.mapper.RegnsRegMapper;
import isry.regns.casemng.casereg.service.RegnsRegService;
import isry.regns.casemng.casetrmn.service.RegnsTrmnService;

/**
 * @파일명        : RegnsRegServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("regnsTrmnService")
public class RegnsTrmnServiceImpl implements RegnsTrmnService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegnsTrmnServiceImpl.class);
	
	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

	@Resource(name = "survshtSaveService")
	private SurvshtSaveService survshtSaveService;
	
	@Resource(name = "regnsRegMapper")
	RegnsRegMapper regnsRegMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;


	/**
	 * @Method명   : selectEfectnById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEfectnById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		paramMap.put("CASE_PRGRS_STTS_SE_CD", "05");
		
		List<Map<String, String>> result = regnsRegMapper.selectEfectnById(paramMap);
		
		return result; 
	}

	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//청소년안전망효과성평가 결과 & 설문지 등록
		saveEfectnEvl(request, dataRequest);

	}

	/**
	 * @Method명   : saveEfectnEvl
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 20. 
	 * @Method설명 :
	 */
	private void saveEfectnEvl(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsEfectnList");
		List<Map<String, String>> allRowList = param.getAllRowList();
		
		for (Map<String, String> map : allRowList) {
			map.put("CASE_PRGRS_STTS_SE_CD", "05");
			map.put("USER_ID", userId);
			
			regnsRegMapper.saveEfectnData(map);
		}
		
		//설문지등록
		if (allRowList.size() > 0) {
			String caseMngNo = allRowList.get(0).get("CASE_MNG_NO");
			String caseMngOdrno = allRowList.get(0).get("CASE_MNG_ODRNO");
			saveSrvyData(request, dataRequest, caseMngNo, caseMngOdrno);
		}
		
	}

	/**
	 * @Method명   : saveSrvyData
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 :
	 */
	private void saveSrvyData(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request));
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("CASE_MNG_NO", sCaseMngNo);
		paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
		paramMap.put("ENFSN_NO", enfsnNo);
		paramMap.put("USER_ID", userId);

		//설문지 저장
		//청소년안전망효과성평가
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyEfectn"), dataRequest.getParameterGroup("dsSrvyRelmEfectn"), paramMap, "22");
	
	}

}
