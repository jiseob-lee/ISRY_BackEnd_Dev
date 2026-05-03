/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.InqAuthGrpListMapper;
import isry.itgcms.sysmgmt.userauth.service.InqAuthGrpListService;

/**
 * @파일명        : InqAuthGrpListServiceImpl.java
 * @프로그램 설명 : 권한 그룹 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("inqAuthGrpListService")
public class InqAuthGrpListServiceImpl extends IsryBaseServiceImpl implements InqAuthGrpListService {

	@Resource(name="inqAuthGrpListMapper")
    private InqAuthGrpListMapper inqAuthGrpListMapper;

	@Override
	public List<Map<String, Object>> selectAuthGrp(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = new HashMap<String, String>();
		if(searchParam != null) {
			paramMap = searchParam.getSingleValueMap();
			
		}
		return inqAuthGrpListMapper.selectAuthGrp(paramMap);
	}
	
	@Override
	public Map<String, Object> selectMaxAuthrtId() throws Exception {
		return inqAuthGrpListMapper.selectMaxAuthrtId();
	}
}
