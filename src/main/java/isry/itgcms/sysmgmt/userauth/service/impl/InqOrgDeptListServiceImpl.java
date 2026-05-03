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

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.InqOrgDeptListMapper;
import isry.itgcms.sysmgmt.userauth.service.InqOrgDeptListService;

/**
 * @파일명        : InqOrgDeptListServiceImpl.java
 * @프로그램 설명 : 기관 부서 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("inqOrgDeptListService")
public class InqOrgDeptListServiceImpl extends IsryBaseServiceImpl implements InqOrgDeptListService {

	@Resource(name="inqOrgDeptListMapper")
    private InqOrgDeptListMapper inqOrgDeptListMapper;
	
	@Override
	public List<Map<String, Object>> selectOrgDept(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmOrg");
		
		if (param != null) {
			String orgCode = param.getValue("orgCode");
			if (orgCode != null && !"".equals(orgCode)) {
				Map<String, String> map = new HashMap<>();
				map.put("INST_NO", orgCode);
				return inqOrgDeptListMapper.selectOrgDept(map);
			}
		}
		
		return null;
	}

	@Override
	public List<Map<String, Object>> selectSubOrgInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmSubOrgParam");
		
		if (param != null) {
			String orgCode = param.getValue("orgCode");
			if (orgCode == null || "".equals(orgCode)) {
				return null;
			}
			String engCtpvNm = param.getValue("engCtpvNm");
			String ctpvSggCd = param.getValue("ctpvSggCd");
			
			Map<String, String> map = new HashMap<>();
			map.put("orgCode", orgCode);
			map.put("engCtpvNm", engCtpvNm.indexOf("|") > -1 
					? engCtpvNm.substring(engCtpvNm.indexOf("|") + 1) : engCtpvNm);
			map.put("ctpvSggCd", ctpvSggCd);
			
			return inqOrgDeptListMapper.selectSubOrgInfo(map);
		}
		
		return null;
	}

	@Override
	public Map<String, Object> selectMaxDeptCd() throws Exception {
		return inqOrgDeptListMapper.selectMaxDeptCd();
	}

}
