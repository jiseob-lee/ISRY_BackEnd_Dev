/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.common.popup.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.util.ScpDb;
import isry.regns.common.popup.mapper.PopupMapper;
import isry.regns.common.popup.service.PopupService;

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
@Service("popupService")
public class PopupServiceImpl implements PopupService{
	
	@Resource(name = "popupMapper")
	private PopupMapper popupMapper;

	/**
	 * @Method명   : selectCmitMtgList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 22. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCmitMtgList(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		String filterName = "";
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		String[] srchSes = param.getValue("SRCH_SE").split(",");
		for (String srchSe : srchSes) {
			if ("1".equals(srchSe)) filterName = param.getValue("SRCH_NM");  //위원성명
//			if ("1".equals(srchSe))	paramMap.put("FILTER_NM", param.getValue("SRCH_NM"));  //위원성명
			if ("2".equals(srchSe)) paramMap.put("INST_NM", param.getValue("SRCH_NM")); //기관명
		}

		paramMap.put("CMIT_SE_CD", param.getValue("CMIT_SE_CD"));
		paramMap.put("ESNTL_LINK_INST_SE_CD", param.getValue("ESNTL_LINK_INST_SE_CD"));
		
		List<Map<String, String>> selectList = popupMapper.selectCmitMtgList(paramMap);

		if (StringUtils.isEmpty(filterName)) {
			return selectList;
		}
		
		List<Map<String, String>> resultList = new ArrayList<>();
		for (Map<String, String> map : selectList) {
			if (map.get("ENTRST_PIC_NM").startsWith(filterName)) {
				resultList.add(map);
			}
		}
		
		return resultList;

		
//		return selectList.stream()
//		//.filter(map -> map.get("ENTRST_PIC_NM").startsWith(filterName))
//		.filter(map -> map.get("ENTRST_PIC_NM").startsWith(paramMap.get("FILTER_NM")))
//		.collect(Collectors.toList());
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
	public List<Map<String, String>> selectLinkInstList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("UNT_TASKWK_SE_CD", param.getValue("UNT_TASKWK_SE_CD"));
		paramMap.put("INST_SE", param.getValue("INST_SE"));
		paramMap.put("ACTVT_YN", param.getValue("ACTVT_YN"));
		paramMap.put("INST_NM", param.getValue("INST_NM"));
		
		return popupMapper.selectLinkInstList(paramMap);
	}

	/**
	 * @Method명   : selectEmrgRptList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEmrgRptList(DataRequest dataRequest) throws Exception {
		ScpDb scpDb = new ScpDb();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("SRCH_SE", param.getValue("SRCH_SE"));
		paramMap.put("INCDNT_TTL_NM", param.getValue("INCDNT_TTL_NM"));
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));

		List<Map<String, String>> resultList = popupMapper.selectEmrgRptList(paramMap);
		
		return resultList;
		
	}

	/**
	 * @Method명   : selectEmrgRptList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEmrgActnList(DataRequest dataRequest) throws Exception {
		ScpDb scpDb = new ScpDb();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("SRCH_SE", param.getValue("SRCH_SE"));
		paramMap.put("INCDNT_TTL_NM", param.getValue("INCDNT_TTL_NM"));
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));
		
		List<Map<String, String>> resultList = popupMapper.selectEmrgActnList(paramMap);
		for (Map<String, String> map : resultList) {
			map.put("PIC_NM", scpDb.scpDecB64(map.get("FLNM_ENCPT")));
			map.put("MBL_TELNO", scpDb.scpDecB64(map.get("MBL_TELNO_ENCPT")));
			map.put("EML_ADDR", scpDb.scpDecB64(map.get("EML_ADDR_ENCPT")));
		}
		
		return resultList;
		
	}

}
