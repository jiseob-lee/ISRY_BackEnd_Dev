/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.sysmgmt.history.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import isry.itgcms.sysmgmt.history.mapper.PersonalHistoryMapper;
import isry.itgcms.sysmgmt.history.service.PersonalHistoryService;
import isry.itgcms.util.Masking;

/**
 * @파일명 : PersonalHistoryServiceImpl.java
 * @프로그램 설명 : 사용자의 이력 조회 및 상세조회 ServiceImpl
 * @작성자 : Ji-Seob.Lee
 * @작성일 : 2022. 10. 8.
 * @수정자 : Ji-Seob.Lee
 * @수정일 : 2022. 10. 8.
 * @수정내용 : - -
 */
@Service("personalHistoryService")
public class PersonalHistoryServiceImpl implements PersonalHistoryService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "personalHistoryMapper")
	private PersonalHistoryMapper personalHistoryMapper;

	@Override
	public Integer selectWorkerHistoryCount(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("FLNM_ENCPT", (String)dmSearchMap.get("FLNM"));
		dmSearchMap.put("EML_ADDR_ENCPT", (String)dmSearchMap.get("EML_ADDR"));

		log.debug("test");

		return personalHistoryMapper.selectWorkerHistoryCount(dmSearchMap);
	}

	@Override
	public List<Map<String, Object>> selectWorkerHistory(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("FLNM_ENCPT", (String)dmSearchMap.get("FLNM"));
		dmSearchMap.put("EML_ADDR_ENCPT", (String)dmSearchMap.get("EML_ADDR"));

		List<Map<String, Object>> list1 = personalHistoryMapper.selectWorkerHistory(dmSearchMap);
		List<Map<String, Object>> list2 = new ArrayList<>();

		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				map1.put("FLNM", (String)map1.get("FLNM_ENCPT"));
				map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
				map1.put("MBL_TELNO", (String)map1.get("MBL_TELNO_ENCPT"));
				map1.put("MBL_TELNO_MASKING", Masking.phoneMasking((String)map1.get("MBL_TELNO")));
				map1.put("EML_ADDR", (String)map1.get("EML_ADDR_ENCPT"));
				map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
				map1.put("MSNGR_ID", (String)map1.get("MSNGR_ID_ENCPT"));
				map1.put("MSNGR_ID_MASKING", Masking.msngrIdMasking((String)map1.get("MSNGR_ID")));
				map1.put("BRTH_YMD_MASKING", Masking.birthMaskingDay((String)map1.get("BRTH_YMD")));
				list2.add(map1);
			}
		}
		return list2;
	}

	@Override
	public Integer selectYouthGuardianHistoryCount(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("FLNM_ENCPT", (String)dmSearchMap.get("FLNM"));

		return personalHistoryMapper.selectYouthGuardianHistoryCount(dmSearchMap);
	}

	@Override
	public List<Map<String, Object>> selectYouthGuardianHistory(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("FLNM_ENCPT", (String)dmSearchMap.get("FLNM"));

		List<Map<String, Object>> list1 = personalHistoryMapper.selectYouthGuardianHistory(dmSearchMap);
		List<Map<String, Object>> list2 = new ArrayList<>();

		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				map1.put("FLNM", (String)map1.get("FLNM_ENCPT"));
				map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
				map1.put("MBL_TELNO", (String)map1.get("MBL_TELNO_ENCPT"));
				map1.put("MBL_TELNO_MASKING", Masking.phoneMasking((String)map1.get("MBL_TELNO")));
				map1.put("EML_ADDR", (String)map1.get("EML_ADDR_ENCPT"));
				map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
				map1.put("STTY_AGT_NM", (String)map1.get("STTY_AGT_NM_ENCPT"));
				map1.put("STTY_AGT_NM_MASKING", Masking.nameMasking((String)map1.get("STTY_AGT_NM")));
				map1.put("PSPT_ENG_FLNM", (String)map1.get("PSPT_ENG_FLNM_ENCPT"));
				map1.put("PSPT_ENG_FLNM_MASKING", Masking.nameMasking((String)map1.get("PSPT_ENG_FLNM")));
				map1.put("BRTH_YMD_MASKING", Masking.birthMaskingDay((String)map1.get("BRTH_YMD")));
				list2.add(map1);
			}
		}
		return list2;
	}

	@Override
	public Integer selectPersonalInfoHistoryCount(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("FLNM_ENCPT", (String)dmSearchMap.get("FLNM"));

		return personalHistoryMapper.selectPersonalInfoHistoryCount(dmSearchMap);
	}

	@Override
	public List<Map<String, Object>> selectPersonalInfoHistory(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("FLNM_ENCPT", (String)dmSearchMap.get("FLNM"));

		List<Map<String, Object>> list1 = personalHistoryMapper.selectPersonalInfoHistory(dmSearchMap);
		List<Map<String, Object>> list2 = new ArrayList<>();

		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				map1.put("FLNM", (String)map1.get("FLNM_ENCPT"));
				map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
				map1.put("RRNO", (String)map1.get("RRNO_ENCPT"));
				map1.put("RRNO_MASKING", Masking.rrnoMasking((String)map1.get("RRNO")));
				map1.put("MBL_TELNO", (String)map1.get("MBL_TELNO_ENCPT"));
				map1.put("MBL_TELNO_MASKING", Masking.phoneMasking((String)map1.get("MBL_TELNO")));
				map1.put("EML_ADDR", (String)map1.get("EML_ADDR_ENCPT"));
				map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
				map1.put("MSNGR_ID", (String)map1.get("MSNGR_ID_ENCPT"));
				map1.put("MSNGR_ID_MASKING", Masking.msngrIdMasking((String)map1.get("MSNGR_ID")));
				list2.add(map1);
			}
		}
		return list2;
	}

	@Override
	public Integer selectLoginUserHistoryCount(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("USER_NM_ENCPT", (String)dmSearchMap.get("USER_NM"));

		return personalHistoryMapper.selectLoginUserHistoryCount(dmSearchMap);
	}

	@Override
	public List<Map<String, Object>> selectLoginUserHistory(Map<String, Object> dmSearchMap) throws Exception {

		dmSearchMap.put("USER_NM_ENCPT", (String)dmSearchMap.get("USER_NM"));

		List<Map<String, Object>> list1 = personalHistoryMapper.selectLoginUserHistory(dmSearchMap);
		List<Map<String, Object>> list2 = new ArrayList<>();

		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				map1.put("USER_NM", (String)map1.get("USER_NM_ENCPT"));
				map1.put("USER_NM_MASKING", Masking.nameMasking((String)map1.get("USER_NM")));
				list2.add(map1);
			}
		}
		return list2;
	}

}
