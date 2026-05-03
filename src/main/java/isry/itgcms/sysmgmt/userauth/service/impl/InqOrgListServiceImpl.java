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

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.InqOrgListMapper;
import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : InqOrgListServiceImpl.java
 * @프로그램 설명 : 기관 조회
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
@Service("inqOrgListService")
public class InqOrgListServiceImpl extends IsryBaseServiceImpl implements InqOrgListService {

	@Resource(name="inqOrgListMapper")
    private InqOrgListMapper inqOrgListMapper;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	public List<Map<String, String>> selectOrg(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Integer instNo = null;
		Integer orgType = null;
		String unitSystem = null;
		String unitTaskWork = null;
		String orgName = null;
		String engCtpvNm = null;
		String rgnCd = null;
		String part = null;
		int upInstNo = 0;
		String six = null;
		boolean bPicEdu = false;	// 경찰청 교육청 여부
		
		if (param != null) {
			if (param.getValue("instNo") != null && !"".equals(param.getValue("instNo"))
					&& !"NULL".equals(param.getValue("instNo")) && !"null".equals(param.getValue("instNo"))) {
				instNo = Integer.parseInt(param.getValue("instNo"));
			}
			if (param.getValue("orgType") != null && !"".equals(param.getValue("orgType"))) {
				orgType = Integer.parseInt(param.getValue("orgType"));
			}
			if (param.getValue("unitSystem") != null && !"".equals(param.getValue("unitSystem"))) {
				unitSystem = param.getValue("unitSystem");
			}
			if (param.getValue("unitTaskWork") != null && !"".equals(param.getValue("unitTaskWork"))) {
				unitTaskWork = param.getValue("unitTaskWork");
			}
			if (param.getValue("orgName") != null && !"".equals(param.getValue("orgName"))) {
				orgName = param.getValue("orgName");
			}
			if (param.getValue("engCtpvNm") != null && !"".equals(param.getValue("engCtpvNm"))) {
				engCtpvNm = param.getValue("engCtpvNm");
			}
			if (param.getValue("rgnCd") != null && !"".equals(param.getValue("rgnCd"))) {
				rgnCd = param.getValue("rgnCd");
			}
			if (param.getValue("part") != null && !"".equals(param.getValue("part"))) {
				part = param.getValue("part");
				switch (part) {
				case "03" :
					upInstNo = 1000000949;
					break;
				case "04" :
					upInstNo = 1000000461;
					bPicEdu = true;
					break;
				case "05" :
					upInstNo = 1000001215;
					bPicEdu = true;
					break;
				default :
					break;
				}
			}
			if (param.getValue("six") != null && !"".equals(param.getValue("six"))) {
				six = param.getValue("six");
			}
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("INST_NO", instNo);
		paramMap.put("ORG_TYPE", orgType);
		paramMap.put("UNIT_SYSTEM", unitSystem);
		paramMap.put("UNIT_TASKWORK", unitTaskWork);
		paramMap.put("ORG_NAME", orgName);
		paramMap.put("ENG_CTPV_NM", engCtpvNm);
		paramMap.put("RGN_CD", rgnCd);
		paramMap.put("UP_INST_NO", upInstNo);
		paramMap.put("SIX", six);
		if(bPicEdu) { // 경찰청 또는 교육청인경우
			paramMap.put("PICEDU", "Y");
		}
		
		String authAppId = dataRequest.getParameter("_AUTH_APP_ID");
		
		if ("app/itgcms/sysmgmt/02_institute/OrganizationManage.clx".equals(authAppId)) {
			paramMap.put("DEL_YN", "Y");
		} else {
			paramMap.put("DEL_YN", "N");
		}
		
		/* 시군구센터지원등록 시도,시군구 센터기관조회*/
		if ("app/itgcm/casemng/uneart/cnterSprtInsert.clx".equals(authAppId)) {
			paramMap.put("SIDO_SGG_TYPE", "1");
		} 
		
		List<Map<String, String>> list1 = inqOrgListMapper.selectOrg(paramMap);
		//List<Map<String, String>> list2 = new ArrayList<>();
		
		//ScpDb scpDb = new ScpDb();
		
		//if (list1 != null) {
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, String> map = list1.get(i);
				//map.put("RPRSV_NM", scpDb.scpDecB64(map.get("RPRSV_NM_ENCPT")));
				//map.put("PIC_MBL_TELNO", scpDb.scpDecB64(map.get("PIC_MBL_TELNO_ENCPT")));
				//map.put("PIC_EML_ADDR", scpDb.scpDecB64(map.get("PIC_EML_ADDR_ENCPT")));
				
				//list2.add(map);
			//}
		//}
		
		return list1;
	}

	public Integer selectOrgCount(Map<String, Object> paramMap) throws Exception {
		Integer count = inqOrgListMapper.selectOrgCount(paramMap);
		return count;
	}
	
	public List<Map<String, Object>> selectOrgPaging(Map<String, Object> paramMap) throws Exception {

		
		log.info("selectOrgPaging.paramMap=[" + paramMap + "]");
		
		List<Map<String, Object>> list1 = inqOrgListMapper.selectOrgPaging(paramMap);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//ScpDb scpDb = new ScpDb();
		
		//if (list1 != null) {
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, Object> map = list1.get(i);
				//map.put("RPRSV_NM", scpDb.scpDecB64((String)map.get("RPRSV_NM_ENCPT")));
				//map.put("PIC_MBL_TELNO", scpDb.scpDecB64((String)map.get("PIC_MBL_TELNO_ENCPT")));
				//map.put("PIC_EML_ADDR", scpDb.scpDecB64((String)map.get("PIC_EML_ADDR_ENCPT")));
				
				//list2.add(map);
			//}
		//}
		
		return list1;
	}
		
	public Map<String, Object> selectOrgDetail(DataRequest dataRequest) throws Exception {

		log.debug("#### selectOrgDetail");
		
		ParameterGroup param = dataRequest.getParameterGroup("dmOrgCode");
		
		if (param != null) {
			String orgCode = param.getValue("orgCode");
			log.debug("#### orgCode : " + orgCode);
			if (orgCode != null && !"".equals(orgCode)) {
				Map<String, Integer> map = new HashMap<>();
				map.put("orgCode", Integer.parseInt(orgCode));
				Map<String, Object> map1 = inqOrgListMapper.selectOrgDetail(map);
				//if (map1 != null) {
					//ScpDb scpDb = new ScpDb();
					//map1.put("RPRSV_NM_ENCPT", scpDb.scpDecB64((String)map1.get("RPRSV_NM_ENCPT")));
					//map1.put("RPRS_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("RPRS_MBL_TELNO_ENCPT")));
					//map1.put("RPRS_EML_ADDR_ENCPT", scpDb.scpDecB64((String)map1.get("RPRS_EML_ADDR_ENCPT")));
					//map1.put("PIC_NM_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_NM_ENCPT")));
					//map1.put("PIC_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_TELNO_ENCPT")));
					//map1.put("PIC_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_MBL_TELNO_ENCPT")));
					//map1.put("PIC_EML_ADDR_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_EML_ADDR_ENCPT")));
					//map1.put("AUTZR_NM", scpDb.scpDecB64((String)map1.get("AUTZR_NM")));
					//map1.put("AUTZR_ENCPT", scpDb.scpDecB64((String)map1.get("AUTZR_ENCPT")));
				//}
				return map1;
			}
		}
		
		return null;
	}

	public Map<String, Object> selectOrgDetailHistoryData(DataRequest dataRequest) throws Exception {
		
		log.debug("#### selectOrgDetailHistory");
		
		ParameterGroup param = dataRequest.getParameterGroup("dmOrgCode");
		
		if (param != null) {
			String orgCode = param.getValue("orgCode");
			String mdfcnDt = param.getValue("MDFCN_DT");
			log.debug("#### orgCode : " + orgCode);
			if (orgCode != null && !"".equals(orgCode)) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgCode", Integer.parseInt(orgCode));
				map.put("MDFCN_DT", mdfcnDt);
				Map<String, Object> map1 = inqOrgListMapper.selectOrgDetailHistoryData(map);
				//if (map1 != null) {
					//ScpDb scpDb = new ScpDb();
					//map1.put("RPRSV_NM_ENCPT", scpDb.scpDecB64((String)map1.get("RPRSV_NM_ENCPT")));
					//map1.put("RPRS_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("RPRS_MBL_TELNO_ENCPT")));
					//map1.put("RPRS_EML_ADDR_ENCPT", scpDb.scpDecB64((String)map1.get("RPRS_EML_ADDR_ENCPT")));
					//map1.put("PIC_NM_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_NM_ENCPT")));
					//map1.put("PIC_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_TELNO_ENCPT")));
					//map1.put("PIC_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_MBL_TELNO_ENCPT")));
					//map1.put("PIC_EML_ADDR_ENCPT", scpDb.scpDecB64((String)map1.get("PIC_EML_ADDR_ENCPT")));
					//map1.put("AUTZR_NM", scpDb.scpDecB64((String)map1.get("AUTZR_NM")));
				//}
				return map1;
			}
		}
		
		return null;
	}
	
	@Override
	public List<Map<String, String>> selectInstituteType() throws Exception {
		return inqOrgListMapper.selectInstituteType();
	}
	
	@Override
	public List<Map<String, String>> selectOrgName(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmOrgInfo");
		
		if (param != null) {
			String instNm = param.getValue("INST_NM");
			if (instNm != null && !"".equals((instNm.trim()))) {
				return inqOrgListMapper.selectOrgName(instNm);
			}
		}
		
		return null;
	}
	
	@Override
	public Map<String, Object> selectOrgRestArea(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmOrgCode");
		
		if (param != null) {
			String orgCode = param.getValue("orgCode");
			
			if (orgCode != null && !"".equals(orgCode)) {
				return inqOrgListMapper.selectOrgRestArea(Integer.parseInt(orgCode));
			}
		}
		
		return null;
	}
	
	/**
	 * @Method명   : selectNewInstituteList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 13. 
	 * @Method설명 : 기관 추가 신청 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectNewInstituteList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		
		//ScpDb scpDb = new ScpDb();
		
		Map<String, String> paramMap = param.getSingleValueMap();
		
		// 승인 기관번호 파라메터 설정
		Map<String, Object> aprvInstNoInfo = userInstAuthService.getAprvInstNoInfo(request, paramMap);
		aprvInstNoInfo.forEach((key, value) -> {
			paramMap.put(key, StringUtil.nullConvert(value));	// null 문자열 방지
		});
		
		//paramMap.put("RPRSV_NM_ENCPT", scpDb.scpEncB64(paramMap.get("RPRSV_NM")));
		//paramMap.put("PIC_NM_ENCPT", scpDb.scpEncB64(paramMap.get("PIC_NM")));
		
		if ("Y".equals(paramMap.get("REJECT_YN"))) {
			paramMap.put("APRV_STTS_SE_CD", "3");
		} else {
			paramMap.put("APRV_STTS_SE_CD", "1");
		}
		
		List<Map<String, Object>> list1 = inqOrgListMapper.selectNewInstituteList(paramMap);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//if (list1 != null && list1.size() > 0) {
			
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, Object> map1 = list1.get(i);
				//map1.put("RPRSV_NM", scpDb.scpDecB64((String)map1.get("RPRSV_NM_ENCPT")));
				//map1.put("RPRSV_NM_MASKING", Masking.nameMasking((String)map1.get("RPRSV_NM")));
				//map1.put("PIC_NM", scpDb.scpDecB64((String)map1.get("PIC_NM_ENCPT")));
				//map1.put("PIC_NM_MASKING", Masking.nameMasking((String)map1.get("PIC_NM")));
				//map1.put("FRST_RGTR_NM", scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")));
				//list2.add(map1);
			//}
		//}
		
		return list1;
	}

	public List<Map<String, String>> resrceSelectOrg(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Integer orgType = null;
		String unitSystem = null;
		String unitTaskWork = null;
		String orgName = null;
		String engCtpvNm = null;
		String rgnCd = null;
		String ctpvCd = null;
		String sggCd = null;
		if (param != null) {
			if (param.getValue("orgType") != null && !"".equals(param.getValue("orgType"))) {
				orgType = Integer.valueOf(param.getValue("orgType"));
			}
			if (param.getValue("unitSystem") != null && !"".equals(param.getValue("unitSystem"))) {
				unitSystem = param.getValue("unitSystem");
			}
			if (param.getValue("unitTaskWork") != null && !"".equals(param.getValue("unitTaskWork"))) {
				unitTaskWork = param.getValue("unitTaskWork");
			}
			if (param.getValue("orgName") != null && !"".equals(param.getValue("orgName"))) {
				orgName = param.getValue("orgName");
			}
			if (param.getValue("engCtpvNm") != null && !"".equals(param.getValue("engCtpvNm"))) {
				engCtpvNm = param.getValue("engCtpvNm");
			}
			if (param.getValue("rgnCd") != null && !"".equals(param.getValue("rgnCd"))) {
				rgnCd = param.getValue("rgnCd");
			}
			if (param.getValue("CTPV_CD") != null && !"".equals(param.getValue("CTPV_CD"))) {
				ctpvCd = param.getValue("CTPV_CD");
			}
			if (param.getValue("SGG_CD") != null && !"".equals(param.getValue("SGG_CD"))) {
				sggCd = param.getValue("SGG_CD");
			}
			
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORG_TYPE", orgType);
		paramMap.put("UNIT_SYSTEM", unitSystem);
		paramMap.put("UNIT_TASKWORK", unitTaskWork);
		paramMap.put("ORG_NAME", orgName);
		paramMap.put("ENG_CTPV_NM", engCtpvNm);
		paramMap.put("RGN_CD", rgnCd);
		paramMap.put("CTPV_CD", ctpvCd);
		paramMap.put("SGG_CD", sggCd);
		
		String authAppId = dataRequest.getParameter("_AUTH_APP_ID");
		
		if ("app/itgcms/sysmgmt/02_institute/OrganizationManage.clx".equals(authAppId)) {
			paramMap.put("DEL_YN", "Y");
		} else {
			paramMap.put("DEL_YN", "N");
		}
		
		List<Map<String, String>> list1 = inqOrgListMapper.resrceSelectOrg(paramMap);
		//List<Map<String, String>> list2 = new ArrayList<>();
		
		//ScpDb scpDb = new ScpDb();
		
		//if (list1 != null) {
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, String> map = list1.get(i);
				//map.put("RPRSV_NM", scpDb.scpDecB64(map.get("RPRSV_NM_ENCPT")));
				//map.put("PIC_MBL_TELNO", scpDb.scpDecB64(map.get("PIC_MBL_TELNO_ENCPT")));
				//map.put("PIC_EML_ADDR", scpDb.scpDecB64(map.get("PIC_EML_ADDR_ENCPT")));
				
				//list2.add(map);
			//}
		//}
		
		return list1; 
	}	
	
	public Integer selectOrgAuthryCount(Map<String, Object> paramMap) throws Exception {
		Integer count = inqOrgListMapper.selectOrgAuthryCount(paramMap);
		return count;
	}
	
	public List<Map<String, Object>> selectOrgAuthryPaging(Map<String, Object> paramMap) throws Exception {

		List<Map<String, Object>> list1 = inqOrgListMapper.selectOrgAuthryPaging(paramMap);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//ScpDb scpDb = new ScpDb();
		
		//if (list1 != null) {
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, Object> map = list1.get(i);
				//map.put("RPRSV_NM", scpDb.scpDecB64((String)map.get("RPRSV_NM_ENCPT")));
				//map.put("PIC_MBL_TELNO", scpDb.scpDecB64((String)map.get("PIC_MBL_TELNO_ENCPT")));
				//map.put("PIC_EML_ADDR", scpDb.scpDecB64((String)map.get("PIC_EML_ADDR_ENCPT")));
				
				//list2.add(map);
			//}
		//}
		
		return list1;
	}
	
	public List<Map<String, Object>> selectOrgDetailHistory(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgCode", param.getValue("orgCode"));
		List<Map<String, Object>> list = inqOrgListMapper.selectOrgDetailHistory(map);
		//List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		//if(list.size() > 0) {
			//for (int i=0; i<list.size(); i++) {
				//Map<String, Object> temp = list.get(i);
				
				//ScpDb scpDb = new ScpDb();
				//temp.put("RPRSV_NM_ENCPT", scpDb.scpDecB64((String)temp.get("RPRSV_NM_ENCPT")));
				//temp.put("RPRS_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)temp.get("RPRS_MBL_TELNO_ENCPT")));
				//temp.put("RPRS_EML_ADDR_ENCPT", scpDb.scpDecB64((String)temp.get("RPRS_EML_ADDR_ENCPT")));
				//temp.put("PIC_NM_ENCPT", scpDb.scpDecB64((String)temp.get("PIC_NM_ENCPT")));
				//temp.put("PIC_TELNO_ENCPT", scpDb.scpDecB64((String)temp.get("PIC_TELNO_ENCPT")));
				//temp.put("PIC_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)temp.get("PIC_MBL_TELNO_ENCPT")));
				//temp.put("PIC_EML_ADDR_ENCPT", scpDb.scpDecB64((String)temp.get("PIC_EML_ADDR_ENCPT")));
				//temp.put("AUTZR_NM", scpDb.scpDecB64((String)temp.get("AUTZR_NM")));
				//temp.put("MDFCN_NM", scpDb.scpDecB64((String)temp.get("MDFCN_NM")));
				
				//list2.add(temp);
			//}
		//}
		
		return list;
	}
	
	public Map<String, Object> selectOrgRenameDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> dmDetail = new HashMap<String, Object>();
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		String UntTaskwk = "";
		
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		
		map.put("INST_NO", param.getValue("INST_NO"));
		System.out.println(param.getValue("APLY_DT"));
		if (null != param.getValue("APLY_DT") && !"".equals(param.getValue("APLY_DT"))) {
			map.put("APRV_STTS_SE_CD", "3");
		}else {
			map.put("APRV_STTS_SE_CD", "1");
		}
		map.put("APLY_DT", param.getValue("APLY_DT"));
		
		dmDetail = inqOrgListMapper.selectOrgRenameDetail(map);
		
		//ScpDb scpDb = new ScpDb();
		
		//if (dmDetail != null) {
			//dmDetail.put("RPRSV_NM_ENCPT", scpDb.scpDecB64(String.valueOf(dmDetail.get("RPRSV_NM_ENCPT"))));
			//dmDetail.put("RPRSV_NM_ENCPT2", scpDb.scpDecB64(String.valueOf(dmDetail.get("RPRSV_NM_ENCPT2"))));
		//}
		
		List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> temp = new HashMap<String, Object>();
		
		temp.put("OFFCS_SGNNG_NO", dmDetail.get("OFFCS_SGNNG_NO"));
		temp.put("MNG_SN", dmDetail.get("MNG_SN"));
		temp.put("ATCMFL_MNG_SN", dmDetail.get("MNG_SN"));
		temp.put("OFFCS_SGNNG_SE_CD", "01");
		temp.put("ATFINO", dmDetail.get("ATFINO"));
		
		dsList.add(temp);
		
		temp = new HashMap<String, Object>();
		temp.put("OFFCS_SGNNG_NO", dmDetail.get("OFFCS_SGNNG_NO"));
		temp.put("MNG_SN", dmDetail.get("MNG_SN2"));
		temp.put("ATCMFL_MNG_SN", dmDetail.get("MNG_SN2"));
		temp.put("OFFCS_SGNNG_SE_CD", "01");
		temp.put("ATFINO", dmDetail.get("ATFINO2"));
		
		dsList.add(temp);
		
		returnMap.put("dmDetail", dmDetail);
		returnMap.put("dsList", dsList);
		
		
		return returnMap;
	}
	
	public List<Map<String, Object>> selectOrgList(DataRequest dataRequest) throws Exception {
			
			ParameterGroup param = dataRequest.getParameterGroup("dmInstNoCn");
			Map<String, Object> map = new HashMap<String, Object>();
			List<Integer> paramInstNoList = new ArrayList<>();
			String instNos = param.getValue("INST_NO_CN");
			String instNoList[] = instNos.split(",");
			for(int i = 0; i < instNoList.length; i++) {
				paramInstNoList.add(Integer.parseInt(instNoList[i]));
			}
			map.put("INST_NO_CN", paramInstNoList);
			List<Map<String, Object>> list = inqOrgListMapper.selectOrgList(map);
			
			return list;
	}
}
