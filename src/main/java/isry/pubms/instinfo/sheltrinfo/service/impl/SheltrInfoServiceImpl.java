/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.instinfo.sheltrinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.util.Formatter;
import isry.pubms.instinfo.sheltrinfo.mapper.SheltrInfoMapper;
import isry.pubms.instinfo.sheltrinfo.service.SheltrInfoService;

/**
 * @파일명        : EmrgActnServiceImpl.java
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
@Service("sheltrInfoService")
public class SheltrInfoServiceImpl implements SheltrInfoService{
	
	@Resource(name = "sheltrInfoMapper")
	private SheltrInfoMapper sheltrInfoMapper;

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
	public List<Map<String, String>> selectReqList(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CTPV_CD", param.getValue("CTPV_CD"));
		
		List<Map<String, String>> result = sheltrInfoMapper.selectReqList(paramMap);
		for (Map<String, String> map : result) {
			map.put("RPRS_TELNO", Formatter.phoneFormat(map.get("RPRS_TELNO"), 1));
		}

		return result;
	}

	/**
	 * @Method명   : selectSheltrCntList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSheltrCntList(DataRequest dataRequest) throws Exception {

		return sheltrInfoMapper.selectSheltrCntList();
		
	}

}
