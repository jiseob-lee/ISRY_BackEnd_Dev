/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtOrgDeptMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgDeptService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : MgmtOrgDeptServiceImpl.java
 * @프로그램 설명 : 기관의 부서 관리
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
@Service("mgmtOrgDeptService")
public class MgmtOrgDeptServiceImpl extends IsryBaseServiceImpl implements MgmtOrgDeptService {

	@Resource(name="mgmtOrgDeptMapper")
    private MgmtOrgDeptMapper mgmtOrgDeptMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public void saveOrgDept(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dsOrgDept");
		
		if (param != null) {

			HttpSession session = request.getSession();
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			String userId = "";
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId = loginVO.getId();
			}
			
			List<Map<String, String>> list = param.getAllRowList();
			
			List<Map<String, String>> list2 = param.getUpdatedRowList();
			List<String> updateList = new ArrayList<>();
			if (list2 != null && list2.size() > 0) {
				for (int i=0; i < list2.size(); i++) {
					Map<String, String> map = list2.get(i);
					String str = map.get("DEPT_CD") + "_" + map.get("INST_NO");
					//log.debug(i + " : str : " + str);
					updateList.add(str);
				}
			}
			
			if (list != null && list.size() > 0) {
				mgmtOrgDeptMapper.deleteAllOrgDept(list.get(0));
				for (int i=0; i < list.size(); i++) {
					Map<String, String> map = list.get(i);
					map.put("USER_ID", userId);
					map.put("SRTNG_SQNCE", String.valueOf(i + 1));
					
					String str = map.get("DEPT_CD") + "_" + map.get("INST_NO");
					
					map.put("DATAA_CHG_SE_CD", updateList.contains(str) ? "U" : "I");	//데이터변경 구분코드 "신규"
					
					mgmtOrgDeptMapper.saveOrgDept(map);
					mgmtOrgDeptMapper.insertOrgDeptHistory(map);
				}
			}
		}
	}
}
