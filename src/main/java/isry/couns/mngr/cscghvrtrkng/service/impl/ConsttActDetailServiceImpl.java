/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.cscghvrtrkng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.cscghvrtrkng.mapper.ConsttActDetailMapper;
import isry.couns.mngr.cscghvrtrkng.service.ConsttActDetailService;

/**
 * @파일명        : ConsttActDetailServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 5. 11. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 5. 11.
 * @수정내용      : 
 * -                
 * -                
 */

@Service("ConsttActDetailServiceImpl")
public class ConsttActDetailServiceImpl extends IsryBaseServiceImpl implements ConsttActDetailService {
	
	@Resource (name = "ConsttActDetailMapper")
	private ConsttActDetailMapper ConsttActDetailMapper;

	/**
	 * @Method명   : consttActDetailList
	 * @param request
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 5. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> consttActDetailList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ConsttActDetailMapper.selectConsttActDetailList(mapParam);
	}
	
	public List<Map<String, Object>> consttActDetailInfo(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		return ConsttActDetailMapper.selectConsttActDetailInfo(mapParam);
	}

}
