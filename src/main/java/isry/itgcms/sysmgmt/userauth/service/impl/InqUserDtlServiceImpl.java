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
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.userauth.mapper.InqUserDtlMapper;
import isry.itgcms.sysmgmt.userauth.service.InqUserDtlService;

/**
 * @파일명        : InqUserDtlServiceImpl.java
 * @프로그램 설명 : 사용자 상세 정보 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 10. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("inqUserDtlService")
public class InqUserDtlServiceImpl extends IsryBaseServiceImpl implements InqUserDtlService {

	@Resource(name="inqUserDtlMapper")
    private InqUserDtlMapper inqUserDtlMapper;

	@Resource(name="personalInfoMapper")
    private PersonalInfoMapper personalInfoMapper;

	public Map<String, String> selectUserDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmMemberId");
		
		if (param != null) {
			
			//Map<String, String> map2 = param.getSingleValueMap();
			
			String userId = param.getValue("USER_ID");
			
			if (userId != null && !"".equals(userId)) {
				
				Map<String, Object> map = new HashMap<>();
				map.put("USER_ID", userId);
				
				//Map<String, Object> map1 = inqUserDtlMapper.selectUserDetail(map);
				
				Map<String, String> userMap = personalInfoMapper.selectUserInfo(map);

				//ScpDb scpDb = new ScpDb();
				
				if (userMap != null && userMap.get("ENFSN_NO") != null && !"".equals(userMap.get("ENFSN_NO"))) {
					Map<String, String> workerMap = personalInfoMapper.selectWorkerInfo(userMap);
					if (workerMap != null) {
						//workerMap.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap.get("FLNM_ENCPT")));
						//workerMap.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap.get("MBL_TELNO_ENCPT")));
						//workerMap.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap.get("EML_ADDR_ENCPT")));
						//workerMap.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap.get("MSNGR_ID_ENCPT")));
						
						workerMap.forEach((key, value) -> {
							if (userMap.get(key) == null && value != null) {
								userMap.put(key, value);
							}
						});
					}
					
				} else if (userMap != null && userMap.get("INST_NO") != null && !"".equals(userMap.get("INST_NO"))) {
					Map<String, String> instituteMap = personalInfoMapper.selectInstituteInfo(userMap);
					if (instituteMap != null) {
						//instituteMap.put("RPRSV_NM_ENCPT", scpDb.scpDecB64(instituteMap.get("RPRSV_NM_ENCPT")));
						//instituteMap.put("PIC_NM_ENCPT", scpDb.scpDecB64(instituteMap.get("PIC_NM_ENCPT")));
						//instituteMap.put("PIC_MBL_TELNO_ENCPT", scpDb.scpDecB64(instituteMap.get("PIC_MBL_TELNO_ENCPT")));
						//instituteMap.put("PIC_EML_ADDR_ENCPT", scpDb.scpDecB64(instituteMap.get("PIC_EML_ADDR_ENCPT")));
						
						instituteMap.forEach((key, value) -> {
							if (userMap.get(key) == null && value != null) {
								userMap.put(key, value);
							}
						});
					}
				} else if (userMap != null && userMap.get("YNGBGS_PRTCR_NO") != null && !"".equals(userMap.get("YNGBGS_PRTCR_NO"))) {
					Map<String, String> youthGuardianMap = personalInfoMapper.selectYouthInfo(userMap);
					if (youthGuardianMap != null) {
						//youthGuardianMap.put("FLNM_ENCPT", scpDb.scpDecB64((String)youthGuardianMap.get("FLNM_ENCPT")));
						//youthGuardianMap.put("MBL_TELNO_ENCPT", scpDb.scpDecB64((String)youthGuardianMap.get("MBL_TELNO_ENCPT")));
						//youthGuardianMap.put("EML_ADDR_ENCPT", scpDb.scpDecB64((String)youthGuardianMap.get("EML_ADDR_ENCPT")));
						//youthGuardianMap.put("STTY_AGT_NM_ENCPT", scpDb.scpDecB64((String)youthGuardianMap.get("STTY_AGT_NM_ENCPT")));
						//youthGuardianMap.put("PSPT_ENG_FLNM_ENCPT", scpDb.scpDecB64((String)youthGuardianMap.get("PSPT_ENG_FLNM_ENCPT")));
						
						youthGuardianMap.forEach((key, value) -> {
							if (userMap.get(key) == null && value != null) {
								userMap.put(key, value);
							}
						});
					}
				}
				
				
				return userMap;
			}
		}
		
		return null;
	}
	
	// 개인의 기관 권한 목록을 구한다.
	@Override
	public List<Map<String, String>> selectUserInstituteAuthList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmMemberId");

		if (param == null) {
			return null;
		} else {
			String userId = param.getValue("USER_ID");
			return inqUserDtlMapper.selectUserInstituteAuthList(userId);
		}
	}
	
	@Override
	public List<Map<String, Object>> selectUserUnitSystem(String id) throws Exception {
		return inqUserDtlMapper.selectUserUnitSystem(id);
	}
}
