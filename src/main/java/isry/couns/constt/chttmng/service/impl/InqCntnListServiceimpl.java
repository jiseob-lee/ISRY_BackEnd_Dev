/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.couns.constt.chttmng.mapper.InqCntnListMapper;
import isry.couns.constt.chttmng.service.InqCntnListService;

/**
 * @파일명        : BbsonmServicelmpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Service("InqCntnListService")
public class InqCntnListServiceimpl implements InqCntnListService{
	
	@Resource(name = "InqCntnListMapper")
	private InqCntnListMapper inqCntnListMapper;

	@Override
	public List<Map<String, Object>> selectInqCntnList(Map<String, Object> mapParam) {
		
		return inqCntnListMapper.selectInqCntnList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectInqCntnDetailPopup(Map<String, Object> mapParam) {
		return inqCntnListMapper.selectInqCntnDetailPopup(mapParam);
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return inqCntnListMapper.getTotalCount(mapParam);
	}

}
