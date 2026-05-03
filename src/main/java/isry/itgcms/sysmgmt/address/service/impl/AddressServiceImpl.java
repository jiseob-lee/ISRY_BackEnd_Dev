/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.address.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.address.mapper.AddressMapper;
import isry.itgcms.sysmgmt.address.service.AddressService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.Masking;

/**
 * @파일명        : AddressServiceImpl.java
 * @프로그램 설명 : 주소록 프로그램
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 5. 18. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 5. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {
	
	private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	@Resource(name="addressMapper")
    private AddressMapper addressMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectAddress(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		String flnm = "";
		String flnmNormal = "";
		String emlAddr = "";
		String mblTelno = "";
		String userId = "";
		String untTaskwkSeCd = "";
		String instNm = "";
		
		//ScpDb scpDb = new ScpDb();
		
		if (parameterGroup != null) {
			flnmNormal = parameterGroup.getValue("FLNM_ENCPT");
			//flnm = scpDb.scpEncB64(parameterGroup.getValue("FLNM_ENCPT"));
			//emlAddr = scpDb.scpEncB64(parameterGroup.getValue("EML_ADDR_ENCPT"));
			//mblTelno = scpDb.scpEncB64(parameterGroup.getValue("MBL_TELNO_ENCPT"));
			flnm = parameterGroup.getValue("FLNM_ENCPT");
			emlAddr = parameterGroup.getValue("EML_ADDR_ENCPT");
			mblTelno = parameterGroup.getValue("MBL_TELNO_ENCPT");
			userId = parameterGroup.getValue("USER_ID");
			untTaskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD");
			instNm = parameterGroup.getValue("INST_NM");
		}
		
		Map<String, Object> map = new HashMap<>();

		if (flnm != null && !"".equals(flnm)) {
			map.put("flnm", flnm);
			map.put("flnmNormal", flnmNormal);
		}
		if (emlAddr != null && !"".equals(emlAddr)) {
			map.put("emlAddr", emlAddr);
		}
		if (mblTelno != null && !"".equals(mblTelno)) {
			map.put("mblTelno", mblTelno);
		}
		if (userId != null && !"".equals(userId)) {
			map.put("userId", userId);
		}
		if (untTaskwkSeCd != null && !"".equals(untTaskwkSeCd)) {
			map.put("untTaskwkSeCd", untTaskwkSeCd);
		}
		if (instNm != null && !"".equals(instNm)) {
			map.put("instNm", instNm);
		}
		
		//log.debug("#### untTaskwkSeCd : " + map.get("untTaskwkSeCd"));

		List<Map<String, Object>> list = addressMapper.selectAddress(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//for (int i=0; i < list.size(); i++) {
			//Map<String, Object> map1 = list.get(i);
			//map1.put("FLNM_ENCPT", scpDb.scpDecB64((String)map1.get("FLNM_ENCPT")));
			//map1.put("MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT")));
			//map1.put("EML_ADDR_ENCPT", scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT")));
			//map1.put("MSNGR_ID_ENCPT", scpDb.scpDecB64((String)map1.get("MSNGR_ID_ENCPT")));
			//list2.add(map1);
		//}
		return list;
	}

	@Override
	public Integer selectAddressCount(Map<String, Object> map) throws Exception {
		return addressMapper.selectAddressCount(map);
	}

	@Override
	public List<Map<String, Object>> selectAddress(Map<String, Object> map) throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		List<Map<String, Object>> list = addressMapper.selectAddressPaging(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//for (int i=0; i < list.size(); i++) {
			//Map<String, Object> map1 = list.get(i);
			//map1.put("FLNM_ENCPT", scpDb.scpDecB64((String)map1.get("FLNM_ENCPT")));
			//map1.put("MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT")));
			//map1.put("EML_ADDR_ENCPT", scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT")));
			//map1.put("MSNGR_ID_ENCPT", scpDb.scpDecB64((String)map1.get("MSNGR_ID_ENCPT")));
			//list2.add(map1);
		//}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> selectWorker(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		String flnm = "";
		String emlAddr = "";
		String mblTelno = "";
		String userId = "";
		String untTaskwkSeCd = "";
		String instNm = "";
		String wrdTelno = "";
		
		//ScpDb scpDb = new ScpDb();
		
		if (parameterGroup != null) {
			//flnm = scpDb.scpEncB64(parameterGroup.getValue("FLNM_ENCPT"));
			//emlAddr = scpDb.scpEncB64(parameterGroup.getValue("EML_ADDR_ENCPT"));
			//mblTelno = scpDb.scpEncB64(parameterGroup.getValue("MBL_TELNO_ENCPT"));
			flnm = parameterGroup.getValue("FLNM_ENCPT");
			emlAddr = parameterGroup.getValue("EML_ADDR_ENCPT");
			mblTelno = parameterGroup.getValue("MBL_TELNO_ENCPT");
			userId = parameterGroup.getValue("USER_ID");
			untTaskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD");
			instNm = parameterGroup.getValue("INST_NM");
			wrdTelno = parameterGroup.getValue("WRD_TELNO");
		}
		
		Map<String, Object> map = new HashMap<>();

		if (flnm != null && !"".equals(flnm)) {
			map.put("flnm", flnm);
		}
		if (emlAddr != null && !"".equals(emlAddr)) {
			map.put("emlAddr", emlAddr);
		}
		if (mblTelno != null && !"".equals(mblTelno)) {
			map.put("mblTelno", mblTelno);
		}
		if (userId != null && !"".equals(userId)) {
			map.put("userId", userId);
		}
		if (untTaskwkSeCd != null && !"".equals(untTaskwkSeCd)) {
			map.put("untTaskwkSeCd", untTaskwkSeCd);
		}
		if (instNm != null && !"".equals(instNm)) {
			map.put("instNm", instNm);
		}
		if (wrdTelno != null && !"".equals(wrdTelno)) {
			map.put("wrdTelno", wrdTelno);
		}
		
		/* 2023-04-26 pre시스템문의사항 184번 *
		 * 담당자 검색에 전국 쉼터 종사자가 나타남
		 * 접속한 해당 종사자의 기관의 담당자만 나오도록 추가
		 */
		Integer menuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO")) 
				? 0 : Integer.valueOf(request.getParameter("_AUTH_MENU_NO"));
		String menuUrl = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		if(! "".equals(menuUrl)) {
			menuUrl = menuUrl.replace(".clx", "");
			menuUrl = menuUrl.substring(menuUrl.lastIndexOf("/"), menuUrl.length());
		}
		
		//log.debug("#### untTaskwkSeCd : " + map.get("untTaskwkSeCd"));

		List<Map<String, Object>> list = addressMapper.selectWorker(map);
		List<Map<String, Object>> list2 = new ArrayList<>();
		
		for (int i=0; i < list.size(); i++) {
			
			Map<String, Object> map1 = list.get(i);
			
			//map1.put("FLNM", scpDb.scpDecB64((String)map1.get("FLNM_ENCPT")));
			//map1.put("MBL_TELNO", scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT")));
			//map1.put("EML_ADDR", scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT")));
			//map1.put("MSNGR_ID", scpDb.scpDecB64((String)map1.get("MSNGR_ID_ENCPT")));

			map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
			
			
				
			String mblTN=   Masking.phoneMasking((String) map1.get("MBL_TELNO"));
			if (mblTN.equals("") || mblTN.equals("null") || mblTN == null) {
				mblTN = "";
			} else {
				if(mblTN.length() >= 2 ) {
					if (mblTN.substring(0, 2).contains("02") && mblTN.length() == 9) {
						
						mblTN = mblTN.substring(0, 2) + "-" + mblTN.substring(2, 5) + "-" + mblTN.substring(5,9);
					}else if(mblTN.substring(0, 2).contains("02") && mblTN.length() == 10) {
						mblTN = mblTN.substring(0, 2) + "-" + mblTN.substring(2, 6) + "-" + mblTN.substring(6,10);
					}else if(mblTN.length() == 10) {
						mblTN = mblTN.substring(0, 3) + "-" + mblTN.substring(3, 6) + "-" + mblTN.substring(6,10);
					}else if(mblTN.length() == 11) {
						mblTN = mblTN.substring(0, 3) + "-" + mblTN.substring(3, 7) + "-" + mblTN.substring(7,11);
					}
				}
			}
			

			map1.put("MBL_TELNO_MASKING", mblTN);
		
			
			map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
			map1.put("BRTH_YMD_MASKING", Masking.birthMasking((String)map1.get("BRTH_YMD")));
			
			//map1.put("MSNGR_ID_MASKING", map1.get("MSNGR_ID_ENCPT"));
			
			list2.add(map1);
		}
		
		return list2;
	}


	@Override
	public List<Map<String, Object>> selectClient(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		String flnm = "";
		String emlAddr = "";
		String mblTelno = "";
		String userId = "";
		String legalRepresentative = "";
		String targetPerson = "";
		String regionCode = "";
		
		//ScpDb scpDb = new ScpDb();
		
		if (parameterGroup != null) {
			//flnm = scpDb.scpEncB64(parameterGroup.getValue("FLNM_ENCPT"));
			//emlAddr = scpDb.scpEncB64(parameterGroup.getValue("EML_ADDR_ENCPT"));
			//mblTelno = scpDb.scpEncB64(parameterGroup.getValue("MBL_TELNO_ENCPT"));
			flnm = parameterGroup.getValue("FLNM_ENCPT");
			emlAddr = parameterGroup.getValue("EML_ADDR_ENCPT");
			mblTelno = parameterGroup.getValue("MBL_TELNO_ENCPT");
			userId = parameterGroup.getValue("USER_ID");
			//legalRepresentative = scpDb.scpEncB64(parameterGroup.getValue("LEGAL_REPRESENTATIVE"));
			legalRepresentative = parameterGroup.getValue("LEGAL_REPRESENTATIVE");
			targetPerson = parameterGroup.getValue("TARGET_PERSON");
			regionCode = parameterGroup.getValue("REGION_CODE");
		}
		
		Map<String, Object> map = new HashMap<>();

		if (flnm != null && !"".equals(flnm)) {
			map.put("flnm", flnm);
		}
		if (emlAddr != null && !"".equals(emlAddr)) {
			map.put("emlAddr", emlAddr);
		}
		if (mblTelno != null && !"".equals(mblTelno)) {
			map.put("mblTelno", mblTelno);
		}
		if (userId != null && !"".equals(userId)) {
			map.put("userId", userId);
		}
		if (legalRepresentative != null && !"".equals(legalRepresentative)) {
			map.put("legalRepresentative", legalRepresentative);
		}
		if (targetPerson != null && !"".equals(targetPerson)) {
			map.put("targetPerson", targetPerson);
		}
		if (regionCode != null && !"".equals(regionCode)) {
			map.put("regionCode", regionCode);
		}
		
		//log.debug("#### untTaskwkSeCd : " + map.get("untTaskwkSeCd"));

		List<Map<String, Object>> list = addressMapper.selectClient(map);
		List<Map<String, Object>> list2 = new ArrayList<>();
		
		for (int i=0; i < list.size(); i++) {
			
			Map<String, Object> map1 = list.get(i);
			
			//map1.put("FLNM", scpDb.scpDecB64((String)map1.get("FLNM_ENCPT")));
			//map1.put("MBL_TELNO", scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT")));
			//map1.put("EML_ADDR", scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT")));
			//map1.put("STTY_AGT_NM", scpDb.scpDecB64((String)map1.get("STTY_AGT_NM_ENCPT")));
			
			map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
			map1.put("MBL_TELNO_MASKING", Masking.phoneMasking((String)map1.get("MBL_TELNO")));
			map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
			map1.put("STTY_AGT_NM_MASKING", Masking.nameMasking((String)map1.get("STTY_AGT_NM")));
			
			//map1.put("MSNGR_ID_MASKING", map1.get("MSNGR_ID_ENCPT"));
			
			list2.add(map1);
		}
		
		return list2;
	}

	@Override
	public Integer selectClientCount(Map<String, Object> map) throws Exception {
		Integer count = addressMapper.selectClientCount(map);
		return count;
	}
	
	@Override
	public List<Map<String, Object>> selectClientPaging(Map<String, Object> map) throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		List<Map<String, Object>> list = addressMapper.selectClientPaging(map);
		List<Map<String, Object>> list2 = new ArrayList<>();
		
		for (int i=0; i < list.size(); i++) {
			
			Map<String, Object> map1 = list.get(i);
			
			//map1.put("FLNM", scpDb.scpDecB64((String)map1.get("FLNM_ENCPT")));
			//map1.put("MBL_TELNO", scpDb.scpDecB64((String)map1.get("MBL_TELNO_ENCPT")));
			//map1.put("EML_ADDR", scpDb.scpDecB64((String)map1.get("EML_ADDR_ENCPT")));
			//map1.put("STTY_AGT_NM", scpDb.scpDecB64((String)map1.get("STTY_AGT_NM_ENCPT")));
			
			map1.put("FLNM_MASKING", Masking.nameMasking((String)map1.get("FLNM")));
			//map1.put("MBL_TELNO_MASKING", Masking.phoneMasking((String)map1.get("MBL_TELNO")));
			map1.put("MBL_TELNO_MASKING", Formatter.phoneFormat((String)map1.get("MBL_TELNO"), 0));
			map1.put("EML_ADDR_MASKING", Masking.emailMasking((String)map1.get("EML_ADDR")));
			map1.put("STTY_AGT_NM_MASKING", Masking.nameMasking((String)map1.get("STTY_AGT_NM")));
			
			//map1.put("MSNGR_ID_MASKING", map1.get("MSNGR_ID_ENCPT"));
			
			list2.add(map1);
		}
		
		return list2;
	}

	@Override
	public List<Map<String, Object>> selectGroup(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("POSESN_USER_ID", userId);
		
		return addressMapper.selectGroup(map);
	}

	
	@Override
	public void saveGroup(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("POSESN_USER_ID", userId);
		map.put("USER_ID", userId);
		
		//addressMapper.deleteGroup(map);
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsGroup");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null) {
			for (int i=0; i < list.size(); i++) {
				
				log.debug(i + " : " + list.get(i).get("ADBK_GROUP_ESNTAL_NO"));
				log.debug(list.get(i).toString());
				
				map.putAll(list.get(i));
				map.put("POSESN_USER_ID", userId);
				addressMapper.insertGroup(map);
			}
		}
	}

	
	@Override
	public void deleteGroup(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("POSESN_USER_ID", userId);
		map.put("USER_ID", userId);
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsGroupDeleted");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null) {
			for (int i=0; i < list.size(); i++) {
				Map<String, String> map1 = list.get(i);
				addressMapper.deleteGroupName(map1);
				addressMapper.deleteGroupPersons(map1);
			}
		}
	}

	@Override
	public List<Map<String, Object>> selectGroupPerson(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String groupEsntalNo = parameterGroup.getValue("ADBK_GROUP_ESNTAL_NO");
		
		if (groupEsntalNo == null || "".equalsIgnoreCase(groupEsntalNo)) {
			return null;
		}
		
		Map<String, Object> map1 = new HashMap<>();
		
		map1.put("ADBK_GROUP_ESNTAL_NO", Integer.parseInt(groupEsntalNo));
		//map.put("ADBK_GROUP_ESNTAL_NO", userId);
		
		List<Map<String, Object>> list1 = addressMapper.selectGroupPerson(map1);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		if (list1 == null || list1.size() == 0) {
			return null;
		}
		
		//ScpDb scpDb = new ScpDb();
		
		//for (int i=0; i < list1.size(); i++) {
			//Map<String, Object> map = list1.get(i);
			//map.put("USER_NM_ENCPT", scpDb.scpDecB64((String)map.get("USER_NM_ENCPT")));
			//map.put("USER_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map.get("USER_MBL_TELNO_ENCPT")));
			//map.put("USER_EML_ADDR_ENCPT", scpDb.scpDecB64((String)map.get("USER_EML_ADDR_ENCPT")));
			//list2.add(map);
		//}
		
		return list1;
	}

	@Override
	public void saveGroupPerson(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("USER_ID2", userId);
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsPerson");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null) {
			
			//ScpDb scpDb = new ScpDb();
			
			for (int i=0; i < list.size(); i++) {
				
				//map.putAll(list.get(i));
				
				Map<String, String> map1 = list.get(i);
				
				map.put("ADBK_GROUP_ESNTAL_NO", map1.get("ADBK_GROUP_ESNTAL_NO"));
				map.put("USER_NO", map1.get("USER_NO"));
				map.put("USER_SE_CD_NM", map1.get("USER_SE_CD_NM"));
				//map.put("USER_NM_ENCPT", scpDb.scpEncB64(map1.get("USER_NM_ENCPT")));
				map.put("USER_NM_ENCPT", map1.get("USER_NM_ENCPT"));
				map.put("USER_ID", map1.get("USER_ID"));
				//map.put("USER_MBL_TELNO_ENCPT", scpDb.scpEncB64(map1.get("USER_MBL_TELNO_ENCPT") == null ? "" : map1.get("USER_MBL_TELNO_ENCPT").replaceAll("[^\\d]", "")));
				map.put("USER_MBL_TELNO_ENCPT", map1.get("USER_MBL_TELNO_ENCPT") == null ? "" : map1.get("USER_MBL_TELNO_ENCPT").replaceAll("[^\\d]", ""));
				//map.put("USER_EML_ADDR_ENCPT", scpDb.scpEncB64(map1.get("USER_EML_ADDR_ENCPT")));
				map.put("USER_EML_ADDR_ENCPT", map1.get("USER_EML_ADDR_ENCPT"));
				
				addressMapper.insertGroupPerson(map);
			}
		}
	}
	
	@Override
	public void deleteGroupPerson(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsPersonDeleted");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null) {
			for (int i=0; i < list.size(); i++) {
				Map<String, String> map1 = list.get(i);
				addressMapper.deleteGroupPerson(map1);
			}
		}
	}

	@Override
	public List<Map<String, Object>> selectGroupsPersons(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsGroupSelected");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list == null || list.size() == 0) {
			return null;
		}
		
		List<Integer> listNo = new ArrayList<>();
		
		for (int i=0; i < list.size(); i++) {
			listNo.add(Integer.parseInt(list.get(i).get("ADBK_GROUP_ESNTAL_NO")));
		}
		
		List<Map<String, Object>> list1 = addressMapper.selectGroupsPersons(listNo);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		if (list1 == null || list1.size() == 0) {
			return null;
		}
		
		//ScpDb scpDb = new ScpDb();
		
		//for (int i=0; i < list1.size(); i++) {
			//Map<String, Object> map = list1.get(i);
			//map.put("USER_NM_ENCPT", scpDb.scpDecB64((String)map.get("USER_NM_ENCPT")));
			//map.put("USER_MBL_TELNO_ENCPT", scpDb.scpDecB64((String)map.get("USER_MBL_TELNO_ENCPT")));
			//map.put("USER_EML_ADDR_ENCPT", scpDb.scpDecB64((String)map.get("USER_EML_ADDR_ENCPT")));
			//list2.add(map);
		//}
		
		return list1;
	}

	@Override
	public List<Map<String, Object>> selectSggList() throws Exception {
		return addressMapper.selectSggList();
	}
}
