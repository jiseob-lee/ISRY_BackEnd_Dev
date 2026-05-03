/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.address.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.address.service.AddressService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
//import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : AddressController.java
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
@Controller
@RequestMapping(value = "/isry/itgcms/sysmgmt/address")
public class AddressController {
	
	private final Logger log = LoggerFactory.getLogger(AddressController.class);

	//@Resource(name = "inqOrgListService")
	//private InqOrgListService inqOrgListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "addressService")
	private AddressService addressService;

	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/onloadAddress.do")
	public View onloadAddress(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));  // 기관 목록

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
		
		log.debug("test");
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectAddress.do")
	public View selectAddress(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

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
		
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = addressService.selectAddressCount(map);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		map.put("START_IDX", startIndex);
		map.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = addressService.selectAddress(map);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsAddress", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		//dataRequest.setResponse("dsAddress", addressService.selectAddress(request, dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/onloadWorker.do")
	public View onloadWorker(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));  // 기관 목록

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectWorker.do")
	public View selectWorker(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsAddress", addressService.selectWorker(request, dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/onloadClient.do")
	public View onloadClient(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
		
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectClient.do")
	public View selectClient(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		//Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		String flnm = "";
		String emlAddr = "";
		String mblTelno = "";
		String userId = "";
		String legalRepresentative = "";
		String targetPerson = "";
		String regionCode = "";
		String regionSido = "";
		String regionSgg = "";
		
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
			regionSido = parameterGroup.getValue("REGION_SIDO");
			regionSgg = parameterGroup.getValue("REGION_SGG");
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
		if (regionSido != null && !"".equals(regionSido)) {
			map.put("regionSido", regionSido);
		}
		if (regionSgg != null && !"".equals(regionSgg)) {
			map.put("regionSgg", regionSgg);
		}
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = addressService.selectClientCount(map);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		map.put("START_IDX", startIndex);
		map.put("OFFSET_IDX", startIndex - 1);
		map.put("LAST_IDX", lastIndex);
		map.put("ROW_COUNT", rowSize);
		
		List<Map<String, Object>> listBoard = addressService.selectClientPaging(map);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsAddress", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		//dataRequest.setResponse("dsAddress", addressService.selectClient(request, dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectGroupAddress.do")
	public View selectGroupAddress(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsGroup", addressService.selectGroup(request, dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveGroupAddress.do")
	public View saveGroupAddress(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		addressService.saveGroup(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/deleteGroupAddress.do")
	public View deleteGroupAddress(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		addressService.deleteGroup(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectGroupPerson.do")
	public View selectGroupPerson(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsPerson", addressService.selectGroupPerson(request, dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveGroupPerson.do")
	public View saveGroupPerson(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		addressService.saveGroupPerson(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/deleteGroupPerson.do")
	public View deleteGroupPerson(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		addressService.deleteGroupPerson(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectGroupsPersons.do")
	public View selectGroupsPersons(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsPersonSelected", addressService.selectGroupsPersons(request, dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSggList.do")
	public View selectSggList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsOrgRegionSgg", addressService.selectSggList());

		return new JSONDataView();
	}
}
