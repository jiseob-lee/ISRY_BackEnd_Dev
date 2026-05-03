/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.test.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import isry.itgcms.sysmgmt.test.mapper.TestMapper;
import isry.itgcms.sysmgmt.test.service.TestService;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : TestServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 6. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 6. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("testService")
public class TestServiceImpl implements TestService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "testMapper")
	private TestMapper testMapper;

	@Override
	public void encSAA000() throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		List<Map<String, String>> list = testMapper.selectSAA000();
		
		log.debug("test");
		
		/*
		for (int i=0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			String RPRSV_NM_ENCPT = map.get("RPRSV_NM_ENCPT");
			String RPRS_MBL_TELNO_ENCPT = map.get("RPRS_MBL_TELNO_ENCPT");
			String RPRS_EML_ADDR_ENCPT = map.get("RPRS_EML_ADDR_ENCPT");
			String PIC_NM_ENCPT = map.get("PIC_NM_ENCPT");
			//String PIC_TELNO_ENCPT = map.get("PIC_TELNO_ENCPT");
			String PIC_MBL_TELNO_ENCPT = map.get("PIC_MBL_TELNO_ENCPT");

			if (RPRSV_NM_ENCPT != null && !"".equals(RPRSV_NM_ENCPT)) {
				RPRSV_NM_ENCPT = scpDb.scpEncB64(RPRSV_NM_ENCPT);
			} else {
				RPRSV_NM_ENCPT = "";
			}
			if (RPRS_MBL_TELNO_ENCPT != null && !"".equals(RPRS_MBL_TELNO_ENCPT)) {
				RPRS_MBL_TELNO_ENCPT = scpDb.scpEncB64(RPRS_MBL_TELNO_ENCPT);
			} else {
				RPRS_MBL_TELNO_ENCPT = "";
			}
			if (RPRS_EML_ADDR_ENCPT != null && !"".equals(RPRS_EML_ADDR_ENCPT)) {
				RPRS_EML_ADDR_ENCPT = scpDb.scpEncB64(RPRS_EML_ADDR_ENCPT);
			} else {
				RPRS_EML_ADDR_ENCPT = "";
			}
			if (PIC_NM_ENCPT != null && !"".equals(PIC_NM_ENCPT)) {
				PIC_NM_ENCPT = scpDb.scpEncB64(PIC_NM_ENCPT);
			} else {
				PIC_NM_ENCPT = "";
			}
			//if (PIC_TELNO_ENCPT != null && !"".equals(PIC_TELNO_ENCPT)) {
				//PIC_TELNO_ENCPT = scpDb.scpEncB64(PIC_TELNO_ENCPT);
			//} else {
				//PIC_TELNO_ENCPT = "";
			//}
			if (PIC_MBL_TELNO_ENCPT != null && !"".equals(PIC_MBL_TELNO_ENCPT)) {
				PIC_MBL_TELNO_ENCPT = scpDb.scpEncB64(PIC_MBL_TELNO_ENCPT);
			} else {
				PIC_MBL_TELNO_ENCPT = "";
			}
			
			map.put("RPRSV_NM_ENCPT", RPRSV_NM_ENCPT);
			map.put("RPRS_MBL_TELNO_ENCPT", RPRS_MBL_TELNO_ENCPT);
			map.put("RPRS_EML_ADDR_ENCPT", RPRS_EML_ADDR_ENCPT);
			map.put("PIC_NM_ENCPT", PIC_NM_ENCPT);
			//map.put("PIC_TELNO_ENCPT", PIC_TELNO_ENCPT);
			map.put("PIC_MBL_TELNO_ENCPT", PIC_MBL_TELNO_ENCPT);
			
			testMapper.updateSAA000(map);
		}
		*/
	}

	@Override
	public void encSCA100() throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		List<Map<String, String>> list = testMapper.selectSCA100();
		
		/*
		for (int i=0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			
			String FLNM_ENCPT = map.get("FLNM_ENCPT");
			String MBL_TELNO_ENCPT = map.get("MBL_TELNO_ENCPT");
			String EML_ADDR_ENCPT = map.get("EML_ADDR_ENCPT");
			String MSNGR_ID_ENCPT = map.get("MSNGR_ID_ENCPT");

			if (FLNM_ENCPT != null && !"".equals(FLNM_ENCPT)) {
				FLNM_ENCPT = scpDb.scpEncB64(FLNM_ENCPT);
			} else {
				FLNM_ENCPT = "";
			}
			if (MBL_TELNO_ENCPT != null && !"".equals(MBL_TELNO_ENCPT)) {
				MBL_TELNO_ENCPT = scpDb.scpEncB64(MBL_TELNO_ENCPT);
			} else {
				MBL_TELNO_ENCPT = "";
			}
			if (EML_ADDR_ENCPT != null && !"".equals(EML_ADDR_ENCPT)) {
				EML_ADDR_ENCPT = scpDb.scpEncB64(EML_ADDR_ENCPT);
			} else {
				EML_ADDR_ENCPT = "";
			}
			if (MSNGR_ID_ENCPT != null && !"".equals(MSNGR_ID_ENCPT)) {
				MSNGR_ID_ENCPT = scpDb.scpEncB64(MSNGR_ID_ENCPT);
			} else {
				MSNGR_ID_ENCPT = "";
			}
			
			map.put("FLNM_ENCPT", FLNM_ENCPT);
			map.put("MBL_TELNO_ENCPT", MBL_TELNO_ENCPT);
			map.put("EML_ADDR_ENCPT", EML_ADDR_ENCPT);
			map.put("MSNGR_ID_ENCPT", MSNGR_ID_ENCPT);
			
			testMapper.updateSCA100(map);
		}
		*/
	}

	@Override
	public void encSCA300() throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		List<Map<String, String>> list = testMapper.selectSCA300();
		
		/*
		for (int i=0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			
			String FLNM_ENCPT = map.get("FLNM_ENCPT");
			String RRNO_ENCPT = map.get("RRNO_ENCPT");
            String MBL_TELNO_ENCPT = map.get("MBL_TELNO_ENCPT");
            String EML_ADDR_ENCPT = map.get("EML_ADDR_ENCPT");
            String MSNGR_ID_ENCPT = map.get("MSNGR_ID_ENCPT");

			if (FLNM_ENCPT != null && !"".equals(FLNM_ENCPT)) {
				FLNM_ENCPT = scpDb.scpEncB64(FLNM_ENCPT);
			} else {
				FLNM_ENCPT = "";
			}
			if (RRNO_ENCPT != null && !"".equals(RRNO_ENCPT)) {
				RRNO_ENCPT = scpDb.scpEncB64(RRNO_ENCPT);
			} else {
				RRNO_ENCPT = "";
			}
			if (MBL_TELNO_ENCPT != null && !"".equals(MBL_TELNO_ENCPT)) {
				MBL_TELNO_ENCPT = scpDb.scpEncB64(MBL_TELNO_ENCPT);
			} else {
				MBL_TELNO_ENCPT = "";
			}
			if (EML_ADDR_ENCPT != null && !"".equals(EML_ADDR_ENCPT)) {
				EML_ADDR_ENCPT = scpDb.scpEncB64(EML_ADDR_ENCPT);
			} else {
				EML_ADDR_ENCPT = "";
			}
			if (MSNGR_ID_ENCPT != null && !"".equals(MSNGR_ID_ENCPT)) {
				MSNGR_ID_ENCPT = scpDb.scpEncB64(MSNGR_ID_ENCPT);
			} else {
				MSNGR_ID_ENCPT = "";
			}
			
			map.put("FLNM_ENCPT", FLNM_ENCPT);
			map.put("RRNO_ENCPT", RRNO_ENCPT);
			map.put("MBL_TELNO_ENCPT", MBL_TELNO_ENCPT);
			map.put("EML_ADDR_ENCPT", EML_ADDR_ENCPT);
			map.put("MSNGR_ID_ENCPT", MSNGR_ID_ENCPT);
			
			testMapper.updateSCA300(map);
		}
		*/
	}
	
}
