/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.mediaaImprnmMng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.cysns.stats.mediaaImprnmMng.mapper.MediaaImprnmMngMapper;
import isry.cysns.stats.mediaaImprnmMng.service.MediaaImprnmMngService;

/**
 * @파일명        : MediaaImprnmMngServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 5. 17. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 5. 17.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mediaaImprnmMngService")
public class MediaaImprnmMngServiceImpl implements MediaaImprnmMngService {
	
	@Resource(name = "mediaaImprnmMngMapper")
	private MediaaImprnmMngMapper mediaaImprnmMngMapper;

	/**
	 * @Method명   : selectMediaaImprnmMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 5. 17. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectMediaaImprnmMngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		
		
		return mediaaImprnmMngMapper.selectMediaaImprnmMngList(dmSearch);
	}
}
