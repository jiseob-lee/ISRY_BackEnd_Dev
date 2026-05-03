/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsmcr.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.couns.constt.bbsmcr.mapper.BbsmcrListMapper;
import isry.couns.constt.bbsmcr.service.BbsmcrListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : BbsmcrListServiceimpl.java
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

@Service("BbsmcrListService")
public class BbsmcrListServiceimpl implements BbsmcrListService {
	
	@Resource(name = "BbsmcrListMapper")
	private BbsmcrListMapper bbsmcrListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbsmcrList1(Map<String, Object> mapParam) {
		
		return bbsmcrListMapper.selectBbsmcrList1(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsmcrList2(Map<String, Object> mapParam) {
		
		return bbsmcrListMapper.selectBbsmcrList2(mapParam);
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		return bbsmcrListMapper.getTotalCount(mapParam);
	}

	@Override
	public List<Map<String, Object>> selecBbscrsIndvList(Map<String, Object> mapParam) {
		return bbsmcrListMapper.selecBbscrsIndvList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsmcrDetail(Map<String, Object> mapParam) {
		return bbsmcrListMapper.selectBbsmcrDetail(mapParam);
	}

	@Override
	public Map<String, Object> saveBbsmcrList(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		
//		System.out.println("DDDDDD n01 : "+dsList.rowSize() );

//		System.out.println("dsList = "+dsList);
		
		Iterator<ParameterRow> insertedRows = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();
				
		if (dsList != null && dsList.rowSize() > 0) {
		
			if(dsList.getValue("PVSN_SHAPE_EML_YN") == null || dsList.getValue("PVSN_SHAPE_EML_YN").equals("")) {
//	System.out.println("DDDDDD : "+dsList.getValue("PVSN_SHAPE_EML_YN"));
				dsList.setValue(0, "PVSN_SHAPE_EML_YN", "N");
			}
			if(dsList.getValue("PVSN_SHAPE_TLPHON_YN") == null || dsList.getValue("PVSN_SHAPE_TLPHON_YN").equals("")) {
				dsList.setValue(0, "PVSN_SHAPE_TLPHON_YN", "N");
			}
			if(dsList.getValue("PVSN_SHAPE_SYS_YN") == null || dsList.getValue("PVSN_SHAPE_SYS_YN").equals("")) {
				dsList.setValue(0, "PVSN_SHAPE_SYS_YN", "N");
			}
			if(dsList.getValue("UTZTN_PURPS_RSTRCT_MATTER_OBSRY_YN") == null || dsList.getValue("UTZTN_PURPS_RSTRCT_MATTER_OBSRY_YN").equals("")) {
				dsList.setValue(0, "UTZTN_PURPS_RSTRCT_MATTER_OBSRY_YN", "N");
			}
			if(dsList.getValue("UTZTN_PRD_RSTRCT_MATTER_OBSRY_YN") == null || dsList.getValue("UTZTN_PRD_RSTRCT_MATTER_OBSRY_YN").equals("")) {
				dsList.setValue(0, "UTZTN_PRD_RSTRCT_MATTER_OBSRY_YN", "N");
			}
			if(dsList.getValue("SAFETY_ACTN_RSTRCT_MATTER_OBSRY_YN") == null || dsList.getValue("SAFETY_ACTN_RSTRCT_MATTER_OBSRY_YN").equals("")) {
				dsList.setValue(0, "SAFETY_ACTN_RSTRCT_MATTER_OBSRY_YN", "N");
			}
			if(dsList.getValue("EMRG_RESC_YN") == null || dsList.getValue("EMRG_RESC_YN").equals("")) {
				dsList.setValue(0, "EMRG_RESC_YN", "N");
			}
			if(dsList.getValue("SPCLTY_INST_DRCT_LINK_YN") == null || dsList.getValue("SPCLTY_INST_DRCT_LINK_YN").equals("")) {
				dsList.setValue(0, "SPCLTY_INST_DRCT_LINK_YN", "N");
			}
			if(dsList.getValue("UTZTN_PSBLTY_INST_INFO_PVSN_YN") == null || dsList.getValue("UTZTN_PSBLTY_INST_INFO_PVSN_YN").equals("")) {
				dsList.setValue(0, "UTZTN_PSBLTY_INST_INFO_PVSN_YN", "N");
			}
			if(dsList.getValue("DCLR_MTHD_GUIDAN_YN") == null || dsList.getValue("DCLR_MTHD_GUIDAN_YN").equals("")) {
				dsList.setValue(0, "DCLR_MTHD_GUIDAN_YN", "N");
			}
			if(dsList.getValue("ETC_RELATA_INFO_PVSN_YN") == null || dsList.getValue("ETC_RELATA_INFO_PVSN_YN").equals("")) {
				dsList.setValue(0, "ETC_RELATA_INFO_PVSN_YN", "N");
			}
			if(dsList.getValue("EMTNL_BACKIN_YN") == null || dsList.getValue("EMTNL_BACKIN_YN").equals("")) {
				dsList.setValue(0, "EMTNL_BACKIN_YN", "N");
			}
			if(dsList.getValue("CHRCTR_YN") == null || dsList.getValue("CHRCTR_YN").equals("")) {
				dsList.setValue(0, "CHRCTR_YN", "N");
			}
			if(dsList.getValue("TLPHON_YN") == null || dsList.getValue("TLPHON_YN").equals("")) {
				dsList.setValue(0, "TLPHON_YN", "N");
			}
			if(dsList.getValue("NOTE_YN") == null || dsList.getValue("NOTE_YN").equals("")) {
				dsList.setValue(0, "NOTE_YN", "N");
			}
			if(dsList.getValue("EML_GUIDAN_YN") == null || dsList.getValue("EML_GUIDAN_YN").equals("")) {
				dsList.setValue(0, "EML_GUIDAN_YN", "N");
			}
			if(dsList.getValue("YNGBGS_DSCSN_WLFAR_CNTER_RQST_YN") == null || dsList.getValue("YNGBGS_DSCSN_WLFAR_CNTER_RQST_YN").equals("")) {
				dsList.setValue(0, "YNGBGS_DSCSN_WLFAR_CNTER_RQST_YN", "N");
			}
			if(dsList.getValue("FAM_RQST_YN") == null || dsList.getValue("FAM_RQST_YN").equals("")) {
				dsList.setValue(0, "FAM_RQST_YN", "N");
			}
			if(dsList.getValue("DT_CACE_SHELTR_RQST_YN") == null || dsList.getValue("DT_CACE_SHELTR_RQST_YN").equals("")) {
				dsList.setValue(0, "DT_CACE_SHELTR_RQST_YN", "N");
			}
			if(dsList.getValue("EMRG_RESC_RQST_YN") == null || dsList.getValue("EMRG_RESC_RQST_YN").equals("")) {
				dsList.setValue(0, "EMRG_RESC_RQST_YN", "N");
			}
			if(dsList.getValue("LEADER_RQST_YN") == null || dsList.getValue("LEADER_RQST_YN").equals("")) {
				dsList.setValue(0, "LEADER_RQST_YN", "N");
			}
			if(dsList.getValue("RQST_INST_INFO_PVSN_NDLS_YN") == null || dsList.getValue("RQST_INST_INFO_PVSN_NDLS_YN").equals("")) {
				dsList.setValue(0, "RQST_INST_INFO_PVSN_NDLS_YN", "N");
			}
			if(dsList.getValue("FLNM_PVSN_YN") == null || dsList.getValue("FLNM_PVSN_YN").equals("")) {
				dsList.setValue(0, "FLNM_PVSN_YN", "N");
			}
			if(dsList.getValue("AGEA_PVSN_YN") == null || dsList.getValue("AGEA_PVSN_YN").equals("")) {
				dsList.setValue(0, "AGEA_PVSN_YN", "N");
			}
			if(dsList.getValue("SXDC_PVSN_YN") == null || dsList.getValue("SXDC_PVSN_YN").equals("")) {
				dsList.setValue(0, "SXDC_PVSN_YN", "N");
			}
			if(dsList.getValue("TELNO_PVSN_YN") == null || dsList.getValue("TELNO_PVSN_YN").equals("")) {
				dsList.setValue(0, "TELNO_PVSN_YN", "N");
			}
			if(dsList.getValue("ADDR_PVSN_YN") == null || dsList.getValue("ADDR_PVSN_YN").equals("")) {
				dsList.setValue(0, "ADDR_PVSN_YN", "N");
			}
			if(dsList.getValue("SCHL_INFO_PVSN_YN") == null || dsList.getValue("SCHL_INFO_PVSN_YN").equals("")) {
				dsList.setValue(0, "SCHL_INFO_PVSN_YN", "N");
			}
			if(dsList.getValue("APEAL_PROBM_PVSN_YN") == null || dsList.getValue("APEAL_PROBM_PVSN_YN").equals("")) {
				dsList.setValue(0, "APEAL_PROBM_PVSN_YN", "N");
			}
			if(dsList.getValue("MBLA_RQST_PVSN_ETC_YN") == null || dsList.getValue("MBLA_RQST_PVSN_ETC_YN").equals("")) {
				dsList.setValue(0, "MBLA_RQST_PVSN_ETC_YN", "N");
			}
			if(dsList.getValue("ETC_SPCLTY_INST_RQST_YN") == null || dsList.getValue("ETC_SPCLTY_INST_RQST_YN").equals("")) {
				dsList.setValue(0, "ETC_SPCLTY_INST_RQST_YN", "N");
			}
			if(dsList.getValue("RLVT_NAPC_YN") == null || dsList.getValue("RLVT_NAPC_YN").equals("")) {
				dsList.setValue(0, "RLVT_NAPC_YN", "N");
			}
	//////////////////////
			if(dsList.getValue("YC1388_YN") == null || dsList.getValue("YC1388_YN").equals("")) {
				dsList.setValue(0, "YC1388_YN", "N");
			}
			if(dsList.getValue("MHW129_YN") == null || dsList.getValue("MHW129_YN").equals("")) {
				dsList.setValue(0, "MHW129_YN", "N");
			}
			if(dsList.getValue("SUNFLO_CNTER_YN") == null || dsList.getValue("SUNFLO_CNTER_YN").equals("")) {
				dsList.setValue(0, "SUNFLO_CNTER_YN", "N");
			}
			if(dsList.getValue("PRSTT_DMGE_CSLC_YN") == null || dsList.getValue("PRSTT_DMGE_CSLC_YN").equals("")) {
				dsList.setValue(0, "PRSTT_DMGE_CSLC_YN", "N");
			}
			if(dsList.getValue("SUCDE_PREVNT_CNTER_YN") == null || dsList.getValue("SUCDE_PREVNT_CNTER_YN").equals("")) {
				dsList.setValue(0, "SUCDE_PREVNT_CNTER_YN", "N");
			}
			if(dsList.getValue("SCHL_VIOLNC_SOS_SUGR_YN") == null || dsList.getValue("SCHL_VIOLNC_SOS_SUGR_YN").equals("")) {
				dsList.setValue(0, "SCHL_VIOLNC_SOS_SUGR_YN", "N");
			}
			if(dsList.getValue("CHIL_PRTCTN_SPCLTY_INST_YN") == null || dsList.getValue("CHIL_PRTCTN_SPCLTY_INST_YN").equals("")) {
				dsList.setValue(0, "CHIL_PRTCTN_SPCLTY_INST_YN", "N");
			}
			if(dsList.getValue("HEALTH_HOUSEK_SPRT_CNTER_YN") == null || dsList.getValue("HEALTH_HOUSEK_SPRT_CNTER_YN").equals("")) {
				dsList.setValue(0, "HEALTH_HOUSEK_SPRT_CNTER_YN", "N");
			}
			if(dsList.getValue("OFCDC_YN") == null || dsList.getValue("OFCDC_YN").equals("")) {
				dsList.setValue(0, "OFCDC_YN", "N");
			}
			if(dsList.getValue("ALTRV_SCHL_YN") == null || dsList.getValue("ALTRV_SCHL_YN").equals("")) {
				dsList.setValue(0, "ALTRV_SCHL_YN", "N");
			}
			if(dsList.getValue("YNGBGS_LABOR_PRTCTN_CNTER_YN") == null || dsList.getValue("YNGBGS_LABOR_PRTCTN_CNTER_YN").equals("")) {
				dsList.setValue(0, "YNGBGS_LABOR_PRTCTN_CNTER_YN", "N");
			}
			if(dsList.getValue("ADDC_MNG_UNITY_SPRT_CNTER_YN") == null || dsList.getValue("ADDC_MNG_UNITY_SPRT_CNTER_YN").equals("")) {
				dsList.setValue(0, "ADDC_MNG_UNITY_SPRT_CNTER_YN", "N");
			}
			if(dsList.getValue("YNGBGS_SECU_CNTER_YN") == null || dsList.getValue("YNGBGS_SECU_CNTER_YN").equals("")) {
				dsList.setValue(0, "YNGBGS_SECU_CNTER_YN", "N");
			}
			if(dsList.getValue("SAFETY_DREAMA_CHIL_FEMALE_DSPSN_POLC_SPRT_CNTER_YN") == null || dsList.getValue("SAFETY_DREAMA_CHIL_FEMALE_DSPSN_POLC_SPRT_CNTER_YN").equals("")) {
				dsList.setValue(0, "SAFETY_DREAMA_CHIL_FEMALE_DSPSN_POLC_SPRT_CNTER_YN", "N");
			}
			if(dsList.getValue("SXVLC_CSLC_YN") == null || dsList.getValue("SXVLC_CSLC_YN").equals("")) {
				dsList.setValue(0, "SXVLC_CSLC_YN", "N");
			}
			if(dsList.getValue("UNMRMT_FANSO_FTHOLD_INST_YN") == null || dsList.getValue("UNMRMT_FANSO_FTHOLD_INST_YN").equals("")) {
				dsList.setValue(0, "UNMRMT_FANSO_FTHOLD_INST_YN", "N");
			}
			if(dsList.getValue("MIND_HEALTH_WLFAR_CNTER_YN") == null || dsList.getValue("MIND_HEALTH_WLFAR_CNTER_YN").equals("")) {
				dsList.setValue(0, "MIND_HEALTH_WLFAR_CNTER_YN", "N");
			}
			if(dsList.getValue("POLICE_CYBER_SEAD_YN") == null || dsList.getValue("POLICE_CYBER_SEAD_YN").equals("")) {
				dsList.setValue(0, "POLICE_CYBER_SEAD_YN", "N");
			}
			if(dsList.getValue("HMVLN_CSLC_YN") == null || dsList.getValue("HMVLN_CSLC_YN").equals("")) {
				dsList.setValue(0, "HMVLN_CSLC_YN", "N");
			}
			if(dsList.getValue("KOLEAI_YN") == null || dsList.getValue("KOLEAI_YN").equals("")) {
				dsList.setValue(0, "KOLEAI_YN", "N");
			}
			if(dsList.getValue("YNGBGS_SPRT_CNTER_DREAM_YN") == null || dsList.getValue("YNGBGS_SPRT_CNTER_DREAM_YN").equals("")) {
				dsList.setValue(0, "YNGBGS_SPRT_CNTER_DREAM_YN", "N");
			}
			if(dsList.getValue("EMPLYA_CNTER_YN") == null || dsList.getValue("EMPLYA_CNTER_YN").equals("")) {
				dsList.setValue(0, "EMPLYA_CNTER_YN", "N");
			}
			if(dsList.getValue("YNGBGS_LABOR_INTRRT_CNTER_YN") == null || dsList.getValue("YNGBGS_LABOR_INTRRT_CNTER_YN").equals("")) {
				dsList.setValue(0, "YNGBGS_LABOR_INTRRT_CNTER_YN", "N");
			}
			if(dsList.getValue("SMRE_CNTER_YN") == null || dsList.getValue("SMRE_CNTER_YN").equals("")) {
				dsList.setValue(0, "SMRE_CNTER_YN", "N");
			}
			if(dsList.getValue("ONST_SPRT_CNTER_YN") == null || dsList.getValue("ONST_SPRT_CNTER_YN").equals("")) {
				dsList.setValue(0, "ONST_SPRT_CNTER_YN", "N");
			}
			if(dsList.getValue("LILI_YN") == null || dsList.getValue("LILI_YN").equals("")) {
				dsList.setValue(0, "LILI_YN", "N");
			}
			if(dsList.getValue("ETC_SPCLTY_INST_ETC_YN") == null || dsList.getValue("ETC_SPCLTY_INST_ETC_YN").equals("")) {
				dsList.setValue(0, "ETC_SPCLTY_INST_ETC_YN", "N");
			}
			
			UserDetailsVO loginVO = null;
			try {
				loginVO = userLoginService.getLoginSessionVO(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = "";
			
			String userIp = request.getRemoteAddr();
			
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId = loginVO.getId();
			}
			
			while (insertedRows.hasNext()) {
	
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("FRST_RGTR_ID", userId);
				mapIns.put("LAST_MDFR_ID", userId);
				mapIns.put("CNTN_IP_ADDR", userIp);
				bbsmcrListMapper.insertBbsmcr(mapIns);
	
				// 게시글 번호 키값 셋팅
				mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
	
			}
	
			while (updatedRows.hasNext()) {
	
				Map<String, String> mapUpd = updatedRows.next().toMap();
//				System.out.println("mapUpd = "+mapUpd);
				mapUpd.put("LAST_MDFR_ID", userId);
				bbsmcrListMapper.updateBbsmcr(mapUpd);
				
				mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
	
			}
	
			while (deletedRows.hasNext()) {
				
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("LAST_MDFR_ID", userId);
				bbsmcrListMapper.deleteBbsmcr(mapDel);			
				
			}
		}
			
		return mapReturn;
	}
	
	@Override
	public Map<String, Object> saveBbsmcrAftList(HttpServletRequest request, DataRequest dataRequest) {
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		
		if (dsList == null || dsList.rowSize() == 0) {
			return mapReturn;
		}
		
		if(dsList.getValue("RSPNS_YN") == null || dsList.getValue("RSPNS_YN").equals("")) {
			dsList.setValue(0, "RSPNS_YN", "N");
		}
		if(dsList.getValue("TLPHON_SAFETY_IDNTY_YN") == null || dsList.getValue("TLPHON_SAFETY_IDNTY_YN").equals("")) {
			dsList.setValue(0, "TLPHON_SAFETY_IDNTY_YN", "N");
		}
		if(dsList.getValue("PRTCR_PRTCTN_DMND_YN") == null || dsList.getValue("PRTCR_PRTCTN_DMND_YN").equals("")) {
			dsList.setValue(0, "PRTCR_PRTCTN_DMND_YN", "N");
		}
		if(dsList.getValue("SPCLTY_INST_SRVC_UTZTN_IDNTY_YN") == null || dsList.getValue("SPCLTY_INST_SRVC_UTZTN_IDNTY_YN").equals("")) {
			dsList.setValue(0, "SPCLTY_INST_SRVC_UTZTN_IDNTY_YN", "N");
		}
		if(dsList.getValue("AFTFCT_MNG_ETC_YN") == null || dsList.getValue("AFTFCT_MNG_ETC_YN").equals("")) {
			dsList.setValue(0, "AFTFCT_MNG_ETC_YN", "N");
		}
		if(dsList.getValue("PVSN_SHAPE_EML_YN") == null || dsList.getValue("PVSN_SHAPE_EML_YN").equals("")) {
			dsList.setValue(0, "PVSN_SHAPE_EML_YN", "N");
		}
		if(dsList.getValue("PVSN_SHAPE_TLPHON_YN") == null || dsList.getValue("PVSN_SHAPE_TLPHON_YN").equals("")) {
			dsList.setValue(0, "PVSN_SHAPE_TLPHON_YN", "N");
		}
		if(dsList.getValue("PVSN_SHAPE_SYS_YN") == null || dsList.getValue("PVSN_SHAPE_SYS_YN").equals("")) {
			dsList.setValue(0, "PVSN_SHAPE_SYS_YN", "N");
		}
		if(dsList.getValue("UTZTN_PURPS_RSTRCT_MATTER_OBSRY_YN") == null || dsList.getValue("UTZTN_PURPS_RSTRCT_MATTER_OBSRY_YN").equals("")) {
			dsList.setValue(0, "UTZTN_PURPS_RSTRCT_MATTER_OBSRY_YN", "N");
		}
		if(dsList.getValue("UTZTN_PRD_RSTRCT_MATTER_OBSRY_YN") == null || dsList.getValue("UTZTN_PRD_RSTRCT_MATTER_OBSRY_YN").equals("")) {
			dsList.setValue(0, "UTZTN_PRD_RSTRCT_MATTER_OBSRY_YN", "N");
		}
		if(dsList.getValue("SAFETY_ACTN_RSTRCT_MATTER_OBSRY_YN") == null || dsList.getValue("SAFETY_ACTN_RSTRCT_MATTER_OBSRY_YN").equals("")) {
			dsList.setValue(0, "SAFETY_ACTN_RSTRCT_MATTER_OBSRY_YN", "N");
		}
		if(dsList.getValue("CHRCTR_YN") == null || dsList.getValue("CHRCTR_YN").equals("")) {
			dsList.setValue(0, "CHRCTR_YN", "N");
		}
		if(dsList.getValue("TLPHON_YN") == null || dsList.getValue("TLPHON_YN").equals("")) {
			dsList.setValue(0, "TLPHON_YN", "N");
		}
		if(dsList.getValue("NOTE_YN") == null || dsList.getValue("NOTE_YN").equals("")) {
			dsList.setValue(0, "NOTE_YN", "N");
		}
		if(dsList.getValue("EML_GUIDAN_YN") == null || dsList.getValue("EML_GUIDAN_YN").equals("")) {
			dsList.setValue(0, "EML_GUIDAN_YN", "N");
		}
		if(dsList.getValue("YNGBGS_DSCSN_WLFAR_CNTER_RQST_YN") == null || dsList.getValue("YNGBGS_DSCSN_WLFAR_CNTER_RQST_YN").equals("")) {
			dsList.setValue(0, "YNGBGS_DSCSN_WLFAR_CNTER_RQST_YN", "N");
		}
		if(dsList.getValue("FAM_RQST_YN") == null || dsList.getValue("FAM_RQST_YN").equals("")) {
			dsList.setValue(0, "FAM_RQST_YN", "N");
		}
		if(dsList.getValue("DT_CACE_SHELTR_RQST_YN") == null || dsList.getValue("DT_CACE_SHELTR_RQST_YN").equals("")) {
			dsList.setValue(0, "DT_CACE_SHELTR_RQST_YN", "N");
		}
		if(dsList.getValue("EMRG_RESC_RQST_YN") == null || dsList.getValue("EMRG_RESC_RQST_YN").equals("")) {
			dsList.setValue(0, "EMRG_RESC_RQST_YN", "N");
		}
		if(dsList.getValue("LEADER_RQST_YN") == null || dsList.getValue("LEADER_RQST_YN").equals("")) {
			dsList.setValue(0, "LEADER_RQST_YN", "N");
		}
		if(dsList.getValue("RQST_INST_INFO_PVSN_NDLS_YN") == null || dsList.getValue("RQST_INST_INFO_PVSN_NDLS_YN").equals("")) {
			dsList.setValue(0, "RQST_INST_INFO_PVSN_NDLS_YN", "N");
		}
		if(dsList.getValue("FLNM_PVSN_YN") == null || dsList.getValue("FLNM_PVSN_YN").equals("")) {
			dsList.setValue(0, "FLNM_PVSN_YN", "N");
		}
		if(dsList.getValue("AGEA_PVSN_YN") == null || dsList.getValue("AGEA_PVSN_YN").equals("")) {
			dsList.setValue(0, "AGEA_PVSN_YN", "N");
		}
		if(dsList.getValue("SXDC_PVSN_YN") == null || dsList.getValue("SXDC_PVSN_YN").equals("")) {
			dsList.setValue(0, "SXDC_PVSN_YN", "N");
		}
		if(dsList.getValue("TELNO_PVSN_YN") == null || dsList.getValue("TELNO_PVSN_YN").equals("")) {
			dsList.setValue(0, "TELNO_PVSN_YN", "N");
		}
		if(dsList.getValue("ADDR_PVSN_YN") == null || dsList.getValue("ADDR_PVSN_YN").equals("")) {
			dsList.setValue(0, "ADDR_PVSN_YN", "N");
		}
		if(dsList.getValue("SCHL_INFO_PVSN_YN") == null || dsList.getValue("SCHL_INFO_PVSN_YN").equals("")) {
			dsList.setValue(0, "SCHL_INFO_PVSN_YN", "N");
		}
		if(dsList.getValue("APEAL_PROBM_PVSN_YN") == null || dsList.getValue("APEAL_PROBM_PVSN_YN").equals("")) {
			dsList.setValue(0, "APEAL_PROBM_PVSN_YN", "N");
		}
		if(dsList.getValue("MBLA_RQST_PVSN_ETC_YN") == null || dsList.getValue("MBLA_RQST_PVSN_ETC_YN").equals("")) {
			dsList.setValue(0, "MBLA_RQST_PVSN_ETC_YN", "N");
		}
		if(dsList.getValue("ETC_SPCLTY_INST_RQST_YN") == null || dsList.getValue("ETC_SPCLTY_INST_RQST_YN").equals("")) {
			dsList.setValue(0, "ETC_SPCLTY_INST_RQST_YN", "N");
		}
		if(dsList.getValue("RLVT_NAPC_YN") == null || dsList.getValue("RLVT_NAPC_YN").equals("")) {
			dsList.setValue(0, "RLVT_NAPC_YN", "N");
		}
		
//////////////////////

		if(dsList.getValue("YC1388_YN") == null || dsList.getValue("YC1388_YN").equals("")) { 
		    dsList.setValue(0, "YC1388_YN", "N");
		} 
		if(dsList.getValue("MHW129_YN") == null || dsList.getValue("MHW129_YN").equals("")) { 
		    dsList.setValue(0, "MHW129_YN", "N");
		} 
		if(dsList.getValue("SUNFLO_CNTER_YN") == null || dsList.getValue("SUNFLO_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "SUNFLO_CNTER_YN", "N");
		} 
		if(dsList.getValue("PRSTT_DMGE_CSLC_YN") == null || dsList.getValue("PRSTT_DMGE_CSLC_YN").equals("")) { 
		    dsList.setValue(0, "PRSTT_DMGE_CSLC_YN", "N");
		} 
		if(dsList.getValue("SUCDE_PREVNT_CNTER_YN") == null || dsList.getValue("SUCDE_PREVNT_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "SUCDE_PREVNT_CNTER_YN", "N");
		} 
		if(dsList.getValue("SCHL_VIOLNC_SOS_SUGR_YN") == null || dsList.getValue("SCHL_VIOLNC_SOS_SUGR_YN").equals("")) { 
		    dsList.setValue(0, "SCHL_VIOLNC_SOS_SUGR_YN", "N");
		} 
		if(dsList.getValue("CHIL_PRTCTN_SPCLTY_INST_YN") == null || dsList.getValue("CHIL_PRTCTN_SPCLTY_INST_YN").equals("")) { 
		    dsList.setValue(0, "CHIL_PRTCTN_SPCLTY_INST_YN", "N");
		} 
		if(dsList.getValue("HEALTH_HOUSEK_SPRT_CNTER_YN") == null || dsList.getValue("HEALTH_HOUSEK_SPRT_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "HEALTH_HOUSEK_SPRT_CNTER_YN", "N");
		} 
		if(dsList.getValue("OFCDC_YN") == null || dsList.getValue("OFCDC_YN").equals("")) { 
		    dsList.setValue(0, "OFCDC_YN", "N");
		} 
		if(dsList.getValue("ALTRV_SCHL_YN") == null || dsList.getValue("ALTRV_SCHL_YN").equals("")) { 
		    dsList.setValue(0, "ALTRV_SCHL_YN", "N");
		} 
		if(dsList.getValue("YNGBGS_LABOR_PRTCTN_CNTER_YN") == null || dsList.getValue("YNGBGS_LABOR_PRTCTN_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "YNGBGS_LABOR_PRTCTN_CNTER_YN", "N");
		} 
		if(dsList.getValue("ADDC_MNG_UNITY_SPRT_CNTER_YN") == null || dsList.getValue("ADDC_MNG_UNITY_SPRT_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "ADDC_MNG_UNITY_SPRT_CNTER_YN", "N");
		} 
		if(dsList.getValue("YNGBGS_SECU_CNTER_YN") == null || dsList.getValue("YNGBGS_SECU_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "YNGBGS_SECU_CNTER_YN", "N");
		} 
		if(dsList.getValue("SAFETY_DREAMA_CHIL_FEMALE_DSPSN_POLC_SPRT_CNTER_YN") == null || dsList.getValue("SAFETY_DREAMA_CHIL_FEMALE_DSPSN_POLC_SPRT_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "SAFETY_DREAMA_CHIL_FEMALE_DSPSN_POLC_SPRT_CNTER_YN", "N");
		} 		
		if(dsList.getValue("WH1366_YN") == null || dsList.getValue("WH1366_YN").equals("")) { 
		    dsList.setValue(0, "WH1366_YN", "N");
		}
				if(dsList.getValue("SXVLC_CSLC_YN") == null || dsList.getValue("SXVLC_CSLC_YN").equals("")) { 
		    dsList.setValue(0, "SXVLC_CSLC_YN", "N");
		} 
		if(dsList.getValue("UNMRMT_FANSO_FTHOLD_INST_YN") == null || dsList.getValue("UNMRMT_FANSO_FTHOLD_INST_YN").equals("")) { 
		    dsList.setValue(0, "UNMRMT_FANSO_FTHOLD_INST_YN", "N");
		} 
		if(dsList.getValue("MIND_HEALTH_WLFAR_CNTER_YN") == null || dsList.getValue("MIND_HEALTH_WLFAR_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "MIND_HEALTH_WLFAR_CNTER_YN", "N");
		} 
		if(dsList.getValue("POLICE_CYBER_SEAD_YN") == null || dsList.getValue("POLICE_CYBER_SEAD_YN").equals("")) { 
		    dsList.setValue(0, "POLICE_CYBER_SEAD_YN", "N");
		} 
		if(dsList.getValue("HMVLN_CSLC_YN") == null || dsList.getValue("HMVLN_CSLC_YN").equals("")) { 
		    dsList.setValue(0, "HMVLN_CSLC_YN", "N");
		} 
		if(dsList.getValue("KOLEAI_YN") == null || dsList.getValue("KOLEAI_YN").equals("")) { 
		    dsList.setValue(0, "KOLEAI_YN", "N");
		} 
		if(dsList.getValue("YNGBGS_SPRT_CNTER_DREAM_YN") == null || dsList.getValue("YNGBGS_SPRT_CNTER_DREAM_YN").equals("")) { 
		    dsList.setValue(0, "YNGBGS_SPRT_CNTER_DREAM_YN", "N");
		} 
		if(dsList.getValue("EMPLYA_CNTER_YN") == null || dsList.getValue("EMPLYA_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "EMPLYA_CNTER_YN", "N");
		} 
		if(dsList.getValue("YNGBGS_LABOR_INTRRT_CNTER_YN") == null || dsList.getValue("YNGBGS_LABOR_INTRRT_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "YNGBGS_LABOR_INTRRT_CNTER_YN", "N");
		} 
		if(dsList.getValue("SMRE_CNTER_YN") == null || dsList.getValue("SMRE_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "SMRE_CNTER_YN", "N");
		} 
		if(dsList.getValue("ONST_SPRT_CNTER_YN") == null || dsList.getValue("ONST_SPRT_CNTER_YN").equals("")) { 
		    dsList.setValue(0, "ONST_SPRT_CNTER_YN", "N");
		} 
		if(dsList.getValue("LILI_YN") == null || dsList.getValue("LILI_YN").equals("")) { 
		    dsList.setValue(0, "LILI_YN", "N");
		} 
		if(dsList.getValue("ETC_SPCLTY_INST_ETC_YN") == null || dsList.getValue("ETC_SPCLTY_INST_ETC_YN").equals("")) { 
		    dsList.setValue(0, "ETC_SPCLTY_INST_ETC_YN", "N");
		} 
		
//		System.out.println("dsList = "+dsList);
		Iterator<ParameterRow> insertedRows = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();
				
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId = "";
		
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			bbsmcrListMapper.insertBbsmcrAft(mapIns);
			
			// 게시글 번호 키값 셋팅
			mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));
			mapReturn.put("AFTFCT_MNG_ESNTAL_NO", mapIns.get("AFTFCT_MNG_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
//			System.out.println("mapUpd = "+mapUpd);
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsmcrListMapper.updateBbsmcrAft(mapUpd);

			mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));
			mapReturn.put("AFTFCT_MNG_ESNTAL_NO", mapUpd.get("AFTFCT_MNG_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsmcrListMapper.deleteBbsmcrAft(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public List<Map<String, Object>> selectBbsmcrAftDetail(Map<String, Object> mapParam) {
		return bbsmcrListMapper.selectBbsmcrAftDetail(mapParam);
	}

}
