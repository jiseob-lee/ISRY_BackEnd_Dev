/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.uneartmng.policelinkaply.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.uneartmng.policelinkaply.mapper.PicMngMapper;
import isry.uneartmng.policelinkaply.service.PicMngService;


/**
 * @파일명        : GitpleEventMapper.java
 * @프로그램 설명 	: 깃플챗 이벤트를 저장한다.
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("picMngService")
public class PicMngServiceImpl implements PicMngService {

	@Resource(name="picMngMapper")
    public PicMngMapper picMngMapper;
	
	@Override
	public List<Map<String, String>> selectRegion() throws Exception {
		return picMngMapper.selectRegion();
	}
	
	@Override
	public List<Map<String, String>> selectRegion2() throws Exception {
		return picMngMapper.selectRegion2();
	}
	
	@Override
	public List<Map<String, String>> selectPicAgency() throws Exception {
		return picMngMapper.selectPicAgency();
	}
	
	@Override
	public List<Map<String, String>> selectPicStation() throws Exception {
		return picMngMapper.selectPicStation();
	}
	
	@Override
	public List<Map<String, String>> selectPicList(Map<String, String> map) throws Exception {
		return picMngMapper.selectPicList(map);
	}
	
	@Override
	public List<Map<String, String>> selectUserHisList(String str) throws Exception {
		return picMngMapper.selectUserHisList(str);
	}

	/**
	 * @Method명   : selectOfcdcPicList
	 * @param str
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOfcdcPicList(DataRequest dataRequest) throws Exception {
		
       	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");		
       	
		return picMngMapper.selectOfcdcPicList(searchParam.getSingleValueMap());
	}

	/**
	 * @Method명   : selectPolicePicList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 21. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectPolicePicList(DataRequest dataRequest) throws Exception {
		
       	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");		
       	
		return picMngMapper.selectPolicePicList(searchParam.getSingleValueMap());
	}
}