/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.ParameterGroup;

import isry.cysns.casemng.casereg.mapper.CysnsRegMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.syscmmn.survsht.service.SurvshtSaveService;

/**
 * @파일명        : SurvshtSaveServiceImpl.java
 * @프로그램 설명 :    설문 결과 저장
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2023. 3. 20. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2023. 3. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("survshtSaveService")
public class SurvshtSaveServiceImpl implements SurvshtSaveService{
	
	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Resource(name = "cysnsRegMapper")
	private CysnsRegMapper cysnsRegMapper;

	/**
	 * @Method명   : saveSrvyResult
	 * @param request
	 * @param paramSrvy
	 * @param paramRelm
	 * @param map
	 * @param qustnbShape
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 20. 
	 * @Method설명 :
	 */
	@Override
	public void saveSrvy(HttpServletRequest request, ParameterGroup paramSrvy, ParameterGroup paramRelm,
			Map<String, String> map, String qustnbShape) throws Exception {

		Map<String, String> paramMap = new HashMap<>();
		List<Map<String, String>> dsListSrvy = paramSrvy.getAllRowList();
		List<Map<String, String>> dsListRelm = paramRelm.getAllRowList();
		
		if (dsListSrvy.size() > 0 ) {
			paramMap.put("QUSTNB_MNG_NO", dsListSrvy.get(0).get("QUSTNB_MNG_NO"));
			paramMap.put("QUSTNB_TMPT_MNG_NO", dsListRelm.get(0).get("QUSTNB_TMPT_MNG_NO"));
	
			paramMap.put("CASE_MNG_NO", map.get("CASE_MNG_NO"));
			paramMap.put("CASE_MNG_ODRNO", map.get("CASE_MNG_ODRNO"));
			paramMap.put("ENFSN_NO", map.get("ENFSN_NO"));
			paramMap.put("QUSTNB_SHAPE_SE_CD", qustnbShape);
			paramMap.put("USER_ID", map.get("USER_ID"));
			
			cysnsRegMapper.saveSrvyTrprData(paramMap);  //SBB110 설문대상자 
			survshtMmnService.savePreSurvshtBySurvsht(request, paramSrvy, paramRelm); //SBB500, SBB220, SBB510
		}	
	}

}
