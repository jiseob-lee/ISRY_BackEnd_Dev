/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import isry.itgcms.sysmgmt.userauth.mapper.MgmtOrgDtlMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtOrgMapper;
import isry.itgcms.sysmgmt.userauth.mapper.OffcsSgnngMngMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : MgmtOrgServiceImpl.java
 * @프로그램 설명 : 기관 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 1. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 1.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mgmtOrgService")
public class MgmtOrgServiceImpl extends IsryBaseServiceImpl implements MgmtOrgService {

	@Resource(name="mgmtOrgMapper")
    private MgmtOrgMapper mgmtOrgMapper;

	@Resource(name="mgmtOrgDtlMapper")
    private MgmtOrgDtlMapper mgmtOrgDtlMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "offcsSgnngMngMapper")
	OffcsSgnngMngMapper offcsSgnngMngMapper;
	
	@Override
	public void saveOrg(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup param = dataRequest.getParameterGroup("dsOrganization");
		
		if (param != null) {
			//mgmtOrgMapper.deleteAllOrg();
			List<Map<String, String>> list = param.getAllRowList();
			if (list != null && list.size() > 0) {
				for (int i=0; i < list.size(); i++) {
					Map<String, String> map = list.get(i);
					map.put("USER_ID", userId);
					mgmtOrgMapper.saveOrg(map);
				}
			}
		}
	}
	
	@Override
	public void saveOrgUnitSystem(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsOrgUnitSystem");
		List<Map<String, String>> saveUnitSystem = new ArrayList<>();
		if (parameterGroup != null) {
			saveUnitSystem = parameterGroup.getAllRowList();
		}
		
		log.debug("#### saveUnitSystem.size : " + saveUnitSystem.size());
		
		//parameterGroup = dataRequest.getParameterGroup("dsUnitSystemDelete");
		//List<Map<String, String>> deleteUnitSystem = new ArrayList<>();
		//if (parameterGroup != null) {
			//deleteUnitSystem = parameterGroup.getAllRowList();
		//}
		
		parameterGroup = dataRequest.getParameterGroup("dmOrgInfo");
		Integer orgCode = null;
		if (parameterGroup != null) {
			if (parameterGroup.getValue("ORG_CODE") != null && !"".equals(parameterGroup.getValue("ORG_CODE"))) {
				orgCode = Integer.valueOf(parameterGroup.getValue("ORG_CODE"));
			}
		}
		
		if (orgCode != null) {
			Map<String, String> map1 = new HashMap<>();
			map1.put("INST_CD", String.valueOf(orgCode));
			mgmtOrgMapper.deleteOrgUnitSystem(map1);
		}
		
		HttpSession session = request.getSession();
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId())) {
			userId = userDetailsVO.getId();
		}
		
		for (int i=0; i < saveUnitSystem.size(); i++) {
			Map<String, String> map = saveUnitSystem.get(i);
			map.put("USER_ID", userId);
			if (map.get("INST_CD") != null && !"".equals(map.get("INST_CD"))
				&& map.get("UNT_SYS_SE_CD") != null && !"".equals(map.get("UNT_SYS_SE_CD"))) {
				mgmtOrgMapper.insertOrgUnitSystem(map);
			}
		}
	}
	
	@Override
	public Map<String, Object> selectMaxInstCd() throws Exception {
		return mgmtOrgMapper.selectMaxInstCd();
	}

	// 기관 승인
	@Override
	public void saveApproveInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> map = new HashMap<>();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		
		Integer orgCode = 0;
		if (parameterGroup != null) {
			if (parameterGroup.getValue("INST_NO") != null && !"".equals(parameterGroup.getValue("INST_NO"))) {
				orgCode = Integer.valueOf(parameterGroup.getValue("INST_NO"));
			}
		}

		HttpSession session = request.getSession();
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId())) {
			userId = userDetailsVO.getId();
		}
		
		map.put("INST_NO", String.valueOf(orgCode));
		map.put("USER_ID", userId);
		
		// mgmtOrgMapper.saveApproveInstitute(map);
		// 변경된 승인 요청 건에 대한 정보 가져오기.
		Map<String, Object> temp = mgmtOrgDtlMapper.selectAprvOrgData(map);
		
		if (temp != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			//INST_NM, UNT_TASKWK_SE_CD, UP_INST_NO, UP_APRV_INST_NO, CTPV_CD, SGG_CD , RPRSV_NM_ENCPT
			param.put("INST_NO", orgCode);
			param.put("INST_NM", temp.get("INST_NM"));
			param.put("UNT_TASKWK_SE_CD", temp.get("UNT_TASKWK_SE_CD"));
			param.put("UP_INST_NO", temp.get("UP_INST_NO"));
			param.put("UP_APRV_INST_NO", temp.get("UP_APRV_INST_NO"));
			param.put("CTPV_CD", temp.get("CTPV_CD"));
			param.put("SGG_CD", temp.get("SGG_CD"));
			param.put("RPRSV_NM_ENCPT", temp.get("RPRSV_NM_ENCPT"));
			param.put("APLY_DT", temp.get("APLY_DT"));
			param.put("APRV_STTS_SE_CD", "2"); // 승인상태 변경.
			param.put("USER_ID", userId);
			// OFFCS_SGNNG_NO, ATFINO
			if (temp.get("OFFCS_SGNNG_NO") != null) {
				param.put("OFFCS_SGNNG_NO", temp.get("OFFCS_SGNNG_NO"));
			}
			
			mgmtOrgMapper.saveApproveOrgChangeData(param);
			
			if (temp.get("ATFINO") != null) {
				param.put("ATFINO", temp.get("ATFINO"));
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("OFFCS_SGNNG_NO", String.valueOf(temp.get("OFFCS_SGNNG_NO")));
				paramMap.put("INST_NO", String.valueOf(temp.get("INST_NO")));
				// SAA230 테이블의 사용여부를 전부 N으로 변경 처리.
				offcsSgnngMngMapper.setOffcsSgnng(paramMap);
				// SAA230 의 ATFINO 의 정보의 사용여부를 Y로 변경 처리.
				mgmtOrgMapper.updateUsingSealInfo(param);
			}
			mgmtOrgMapper.updateApproveOrgStatus(param);
		}
	}
	
	// 기관 반려
	@Override
	public void saveRejectInstitute(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = new HashMap<>();

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmGiveBack");
		
		Integer orgCode = 0;
		String rejectReason = "";
		if (parameterGroup != null) {
			if (parameterGroup.getValue("INST_NO") != null && !"".equals(parameterGroup.getValue("INST_NO"))) {
				orgCode = Integer.valueOf(parameterGroup.getValue("INST_NO"));
			}
			rejectReason = parameterGroup.getValue("REJECT_REASON");
		}

		HttpSession session = request.getSession();
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId())) {
			userId = userDetailsVO.getId();
		}
		
		map.put("INST_NO", orgCode);
		map.put("REJECT_REASON", rejectReason);
		map.put("USER_ID", userId);
		// 프로세스 변경으로 인해 기존 승인 여부 안 씀. SAA010 으로 대체 
		// mgmtOrgMapper.saveRejectInstitute(map);
		mgmtOrgMapper.updateRejectInstituteOffer(map);
		
	}

}
