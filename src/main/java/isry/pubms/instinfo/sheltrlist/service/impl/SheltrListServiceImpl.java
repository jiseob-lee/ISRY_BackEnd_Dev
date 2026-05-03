/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.instinfo.sheltrlist.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.util.Formatter;
import isry.pubms.instinfo.sheltrlist.mapper.SheltrListMapper;
import isry.pubms.instinfo.sheltrlist.service.SheltrListService;

/**
 * @파일명        : SheltrListServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Jeong.Hyun.Jin
 * @작성일        : 2023. 8. 22. 
 * @수정자        : Jeong.Hyun.Jin
 * @수정일        : 2023. 8. 22.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("sheltrListService")
public class SheltrListServiceImpl implements SheltrListService{
	
	@Resource(name = "sheltrListMapper")
	private SheltrListMapper sheltrListMapper;

	/**
	 * @Method명   : selectSheltrList
	 * @param local
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Hyun.Jin
	 * @작성일     : 2023. 8. 22.
	 * @Method설명 :
	 */
	
	@Override
	public List<Map<String, String>> selectSheltrList(Integer local) throws Exception {

		return sheltrListMapper.selectSheltrList(local);
		
	}

}
