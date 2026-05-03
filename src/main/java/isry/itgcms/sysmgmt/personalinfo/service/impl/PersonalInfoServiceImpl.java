package isry.itgcms.sysmgmt.personalinfo.service.impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.history.mapper.PersonalHistoryMapper;
import isry.itgcms.sysmgmt.logging.mapper.SystemLoggingMapper;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtAuthGrpMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtUserAuthMapper;
import isry.itgcms.sysmgmt.userauth.mapper.UserAuthAplyMapper;
import isry.itgcms.sysmgmt.userauth.mapper.UserInstAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtAuthGrpService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.IP;
import isry.itgcms.util.UserException;

@Service("personalInfoService")
public class PersonalInfoServiceImpl extends IsryBaseServiceImpl implements PersonalInfoService {

	@Resource(name="personalInfoMapper")
    private PersonalInfoMapper personalInfoMapper;

	@Resource(name = "systemLoggingMapper")
	private SystemLoggingMapper systemLoggingMapper;

	@Resource(name = "personalHistoryMapper")
	private PersonalHistoryMapper personalHistoryMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name="mgmtAuthGrpService")
	private MgmtAuthGrpService mgmtAuthGrpService;
	
	//@Resource(name="reqUserJoinMapper")
    //private ReqUserJoinMapper reqUserJoinMapper;
	
	@Resource(name="mgmtAuthGrpMapper")
    private MgmtAuthGrpMapper mgmtAuthGrpMapper;

	@Resource(name = "mgmtUserAuthMapper")
	private MgmtUserAuthMapper mgmtUserAuthMapper;
	
	@Resource(name = "userAuthAplyMapper")
	private UserAuthAplyMapper userAuthAplyMapper;
	
	@Resource(name = "userInstAuthMapper")
	private UserInstAuthMapper userInstAuthMapper;

	@Resource(name="mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;
	
	@Override
	public boolean isPersonalInfo(String exportTitle, Integer menuNo, String menuUrl) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("EXPORT_TITLE", exportTitle);
		map.put("MENU_NO", menuNo);
		map.put("MENU_URL", menuUrl);
		
		String isPersonalInfo = "N";
		
		if (menuNo == null || menuNo == 0) {
			isPersonalInfo = personalInfoMapper.isPersonalInfoUrl(map);
		} else {
			isPersonalInfo = personalInfoMapper.isPersonalInfo(map);
		}
		
		return "Y".equals(isPersonalInfo) ? true : false;
	}

	@Override
	public Integer isExcelDownloadRegistered(Integer menuNo, String fileName, String menuUrl) throws Exception {
		
		Map<String, Object> map = new HashMap<>();

		map.put("MENU_NO", menuNo);
		map.put("MENU_URL", menuUrl);
		map.put("EXPORT_TITLE", fileName);

		Integer cnt = 0;
		
		if (menuNo == null || menuNo == 0) {
			cnt = personalInfoMapper.isExcelDownloadRegisteredUrl(map);
		} else {
			cnt = personalInfoMapper.isExcelDownloadRegistered(map);
		}
		
		return cnt;
	}
	
	@Override
	public String getMenuNm(String menuUrl) throws Exception {
		return systemLoggingMapper.getMenuNm(menuUrl);
	}
	
	@Override
	public void recordPersonalInfoDownloadReason(Map<String, String> map) throws Exception {
		// test
	}
	
	@Override
	public List<Map<String, Object>> selectExcelDownload(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = personalInfoMapper.selectExcelDownload();
		
		return list;
	}

	@Override
	public List<Map<String, Object>> selectExcelDownloadList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = personalInfoMapper.selectExcelDownloadList();
		
		return list;
	}
	
	@Override
	public List<Map<String, String>> selectMenuNm(DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmMenuUrl");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		String menuUrl = paramMap.get("menuUrl");
		
		Map<String, String> map = new HashMap<>();
		map.put("menuUrl", menuUrl);
		
		List<Map<String, String>> manuMap = personalInfoMapper.selectMenuNm(map);
		
		//map.put("menuNm", manuMap.get("MENU_NM"));
		//map.put("rootMenuNm", manuMap.get("ROOT_MENU_NM"));
		
		return manuMap;
	}
	
	@Override
	public void saveExcelDownload(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsList");
		
		List<Map<String, String>> list = dsMessage.getAllRowList();
		
		log.info("#### size : " + list.size());
		
		//화면에서 입력 처리된 레코드의 데이터 값을 일괄 전달 받는다.
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();

		//화면에서 수정 처리된 레코드의 데이터 값을 일괄 전달 받는다.
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();

		//화면에서 삭제 처리된 레코드의 데이터 값을 일괄 전달 받는다.
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		Map<String, String> map = new HashMap<>();
		
		//삭제 처리 
		while (deletedRows.hasNext()) {

			map = deletedRows.next().toMap();
			map.put("USER_ID", userId2);
			
			personalInfoMapper.deleteExcelDownload(map);
		}
		
		// 입력 처리 
		while (insertedRows.hasNext()) {
			
			map = insertedRows.next().toMap();
			map.put("USER_ID", userId2);
			
			personalInfoMapper.insertExcelDownload(map);
		}

		// 수정 처리 
		while (updatedRows.hasNext()) {
			
			map = updatedRows.next().toMap();
			map.put("USER_ID", userId2);
			
			personalInfoMapper.updateExcelDownload(map);
		}
	}

	@Override
	public List<Map<String, Object>> selectExcelDownloadList(Map<String, Object> dmSearchMap) throws Exception {
		//ScpDb scpDb = new ScpDb();
		//dmSearchMap.put("FRST_RGTR_NM_DEC", dmSearchMap.get("FRST_RGTR_NM"));
		//dmSearchMap.put("FRST_RGTR_NM_ENC", scpDb.scpEncB64((String)dmSearchMap.get("FRST_RGTR_NM")));
		List<Map<String, Object>> list1 = personalInfoMapper.selectExcelDownloadList(dmSearchMap);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//if (list1 != null) {
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, Object> map1 = list1.get(i);
				//map1.put("FRST_RGTR_NM", scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")));
				//list2.add(map1);
			//}
		//}
		return list1;
	}
	
	@Override
	public Integer selectExcelDownloadListCount(Map<String, Object> dmSearchMap) throws Exception {
		//ScpDb scpDb = new ScpDb();
		//dmSearchMap.put("FRST_RGTR_NM_DEC", dmSearchMap.get("FRST_RGTR_NM"));
		//dmSearchMap.put("FRST_RGTR_NM_ENC", scpDb.scpEncB64((String)dmSearchMap.get("FRST_RGTR_NM")));
		return personalInfoMapper.selectExcelDownloadListCount(dmSearchMap);
	}
	
	@Override
	public boolean saveExcelDownloadReason(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		Map<String, Object> map = new HashMap<>(param.getSingleValueMap());
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		map.put("USER_ID", userId2);
		
		Integer menuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO")) 
				? 0 : Integer.valueOf(request.getParameter("_AUTH_MENU_NO"));
		String menuUrl = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		
		map.put("MENU_NO", menuNo);
		map.put("MENU_URL_ADDR", menuUrl);
		
		map.put("TOP_MENU_NM", mgmtMenuMapper.selectTopMenuNm(loginVO.getUntTaskwk()));
		
		String smsToken1 = (String)session.getAttribute("smsToken1");
		String smsToken2 = (String)map.get("smsToken2");
		
		//log.debug("#### smsToken1 : " + smsToken1);
		//log.debug("#### smsToken2 : " + smsToken2);
		//log.debug("#### smsToken12 : " + smsToken1.equals(smsToken2));
		
		if (smsToken1 == null || smsToken2 == null || "".equals(smsToken1) || !smsToken1.equals(smsToken2)) {
			return false;
		}
		
		session.setAttribute("smsToken2", map.get("smsToken2"));
		session.setAttribute("downloadReason", map.get("DWNLD_CS_CN"));
		
		map.put("MOBILE_COUNT", map.get("MOBILE_COUNT"));
		map.put("WIRED_COUNT", map.get("WIRED_COUNT"));
		map.put("EMAIL_COUNT", map.get("EMAIL_COUNT"));
		map.put("RRNO_COUNT", map.get("RRNO_COUNT"));
		map.put("TOTAL_ROW_COUNT", map.get("TOTAL_ROW_COUNT"));
		
		map.put("CNTN_IP_ADDR", IP.getClientIP(request));
		
		personalInfoMapper.saveExcelDownloadReason(map);
		
		return true;
	}
	
	@Override
	public List<Map<String, Object>> selectLongTermNotConnect(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		return null;
	}

	@Override
	public boolean checkReconfirmPassword(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmUserInfo");
		
		Map<String, String> map = new HashMap<>();
		map.put("USER_ID", dmParam.getValue("USER_ID"));

		String passwd = new String(Base64.getDecoder().decode(dmParam.getValue("USER_PW")));
		
		//ScpDb scpDb = new ScpDb();
		//String userPw = scpDb.scpHashB64(passwd);
		//map.put("USER_PW", userPw);
		map.put("USER_PW", passwd);
		
		Integer count = personalInfoMapper.checkReconfirmPassword(map);
		
		HttpSession session = request.getSession();
		
		if (count != null && count > 0) {
			session.setAttribute("personalInfoRights", "1");
			return true;
		}
		
		return false;
	}
	
	@Override
	public void saveSystemEnv(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmValue");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("USER_ID", userId2);
		
		//map.put("USER_TYPE_SE_CD", "PART1");
		map.put("LOTE_NOCO_PRD_VALUE", Integer.parseInt(dmParam.getValue("part1Value")));
		map.put("PRD_KND_SE_CD", dmParam.getValue("part1Unit"));
		
		//personalInfoMapper.saveLongTermNotConnect(map);
		
		//map.put("USER_TYPE_SE_CD", "PART2");
		//map.put("LOTE_NOCO_PRD_VALUE", Integer.parseInt(dmParam.getValue("part2Value")));
		//map.put("PRD_KND_SE_CD", dmParam.getValue("part2Unit"));
		
		//personalInfoMapper.saveLongTermNotConnect(map);
		
		String value1 = dmParam.getValue("SESIN_TMOUT_HR");  // 세션 시간
		String value2 = dmParam.getValue("PSWD_FAIL_PRMT_CONT");  // 비밀번호 실패 허용 회수
		String value3 = dmParam.getValue("PSWD_CHG_CYCL_DAYCNT");  // 비밀번호 변경 알림 주기
		String value4 = dmParam.getValue("PRVC_CLCT_REAGRE_CYCL_DAYCNT");  // 개인정보수집재동의주기일수
		
		if (value1 == null || "".equals(value1)) {
			value1 = "30";
		}
		if (value2 == null || "".equals(value2)) {
			value2 = "5";
		}
		if (value3 == null || "".equals(value3)) {
			value3 = "365";
		}
		if (value4 == null || "".equals(value4)) {
			value4 = "365";
		}
		
		map.put("SESIN_TMOUT_HR", Integer.parseInt(value1));
		map.put("PSWD_FAIL_PRMT_CONT", Integer.parseInt(value2));
		map.put("PSWD_CHG_CYCL_DAYCNT", Integer.parseInt(value3));
		map.put("PRVC_CLCT_REAGRE_CYCL_DAYCNT", Integer.parseInt(value4));
		
		personalInfoMapper.deleteSystemEnv();
		personalInfoMapper.saveSystemEnv(map);
		personalInfoMapper.saveSystemEnvLog(map);
	}
	
	
	@Override
	public Map<String, String> selectWorkerInfo(Map<String, Object> loginMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfo(loginMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectWorkerInfo(workerMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);
		
		//ScpDb scpDb = new ScpDb();

		//if (workerMap != null) {
			//if (workerMap.get("JOIN_AUTZR_NM") != null && !"".equals(workerMap.get("JOIN_AUTZR_NM"))) {
				//workerMap.put("JOIN_AUTZR_NM", scpDb.scpDecB64(workerMap.get("JOIN_AUTZR_NM")));
			//}
		//}

		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap2.get("MSNGR_ID_ENCPT"))) {
				//workerMap2.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap2.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		/*
		String RRNO = workerMap1.get("RRNO_ENCPT");
		String strDec = "";
		
		try {
			ScpDb scpDb = new ScpDb();
			strDec = scpDb.scpDecB64(RRNO);
		} catch (Exception e) {
			e.printStackTrace();
			strDec = "";
		}
		workerMap1.put("RRNO", strDec);
		*/
		
		//workerMap1.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));
		//workerMap2.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));

		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, String.valueOf(value));
					}
				});
		}
		
		if (workerMap2 != null) {
			workerMap2.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, value);
					}
				});
		}
		
		return workerMap;
	}
	
	public List<Map<String, String>> selectUnitSystemList(Map<String, String> workerMap) throws Exception {
		List<Map<String, String>> unitSystemList = personalInfoMapper.selectUnitSystemList(workerMap);
		return unitSystemList;
	}
	

	private boolean emailCheck(String email) {
		
		return EmailValidator.getInstance().isValid(email);
		
		//boolean err = false;
		//String regex = "([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		//Pattern p = Pattern.compile(regex);
		//Matcher m = p.matcher(email);
		//if (m.matches()) {
			//err = true;
		//}
		//return err;
		//return (email != null && email != '' && email != 'undefined' && regex.test(email)); 
	}
	
	private String checkValidity(Map<String, String> map) throws Exception {

        //if (map.get("USER_ID") == null || "".equals(map.get("USER_ID"))) {
        	//return "사용자 아이디를 입력해주시기 바랍니다.";
        //}
        //if (map.get("ID_DUPLICATE_CHECK") == null || !"사용 가능".equals(map.get("ID_DUPLICATE_CHECK"))) {
        	//return "사용자 아이디 중복을 체크해주시기 바랍니다.";
        //}
        //Integer existCount = reqUserJoinMapper.selectIdExistsCount(map.get("USER_ID"));
		//if (existCount != null && existCount > 0) {
			//return "사용자 아이디가 중복됩니다.";
		//}
        if (map.get("FLNM_ENCPT") == null || "".equals(map.get("FLNM_ENCPT"))) {
        	return "이름을 입력해주시기 바랍니다.";
        }
		//if (map.get("USER_PSWD") == null || "".equals(map.get("USER_PSWD"))) {
        	//return "비밀번호를 입력해주시기 바랍니다.";
        //}
        //if (map.get("USER_PSWD2") == null || "".equals(map.get("USER_PSWD2"))) {
        	//return "확인 비밀번호를 입력해주시기 바랍니다.";
        //}
        //if (!map.get("USER_PSWD").equals(map.get("USER_PSWD2"))) {
        	//return "비밀번호가 일치하지 않습니다.";
        //}
        //if (map.get("PERSONAL_ID_NUM") == null || "".equals(map.get("PERSONAL_ID_NUM"))) {}
        //if (map.get("USE_CLASS_CODE") == null || "".equals(map.get("USE_CLASS_CODE"))) {
        	//return "";
        //}
        //if (map.get("DATE_APPLICATION") == null || "".equals(map.get("DATE_APPLICATION"))) {
        	//return "";
        //}
        
        //if (map.get("PERSONAL_ID_NUM") == null || "".equals(map.get("PERSONAL_ID_NUM"))) {}
        //if (map.get("RRNO") == null || "".equals(map.get("RRNO"))) {
        	//return "주민등록번호를 입력해주시기 바랍니다.";
        //}
        //if (!juminCheck(map.get("RRNO"))) {
        	//return "주민등록번호가 유효하지 않습니다.";
        //}
        if (map.get("SXDC_SE_CD") == null || "".equals(map.get("SXDC_SE_CD"))) {
        	return "성별을 선택해주시기 바랍니다.";
        }
        if (map.get("BRTH_YMD") == null || "".equals(map.get("BRTH_YMD"))) {
        	return "생년월일을 입력해주시기 바랍니다.";
        }
        //if (map.get("AGE") == null || "".equals(map.get("AGE"))) {
        	//return " 입력해주시기 바랍니다.";
        //}
        //if (map.get("WRD_TELNO") == null || "".equals(map.get("WRD_TELNO"))) {
        	//return "전화번호를 입력해주시기 바랍니다.";
        //}
        
//        if (!(map.get("WRD_TELNO") == null || "".equals(map.get("WRD_TELNO")))) {
//	        String phoneNum = map.get("WRD_TELNO").replaceAll("[^0-9]", "");
//	        if (phoneNum.length() < 8 || phoneNum.length() > 11) {
//	        	return "직장전화번호를 확인해주시기 바랍니다.";
//	        }
//        }
	        //if (map.get("MBL_TELNO") == null || "".equals(map.get("MBL_TELNO"))) {
	        	//return "휴대전화번호를 입력해주시기 바랍니다.";
	        //}
	    if (!(map.get("MBL_TELNO_ENCPT") == null || "".equals(map.get("MBL_TELNO_ENCPT")))) {
	        String mobileNum = map.get("MBL_TELNO_ENCPT").replaceAll("[^0-9]", "");
	        if (mobileNum.length() < 10 || mobileNum.length() > 11) {
	        	return "휴대전화번호를 확인해주시기 바랍니다.";
	        }
        }
        
        //if (!(map.get("EML_ADDR_ENCPT") == null || "".equals(map.get("EML_ADDR_ENCPT")) || "@".equals(map.get("EML_ADDR_ENCPT")))) {
//	        if (map.get("EML_ADDR_ENCPT") == null || "".equals(map.get("EML_ADDR_ENCPT")) || "@".equals(map.get("EML_ADDR_ENCPT"))) {
//	        	return "이메일 주소를 입력해주시기 바랍니다.";
//	        }
//	        if (!emailCheck(map.get("EML_ADDR_ENCPT"))) {
//	        	return "이메일 주소 형식을 확인해주시기 바랍니다.";
//	        }
        //}
        
        //if (map.get("MSNGR_ID") == null || "".equals(map.get("MSNGR_ID"))) {}
        //if (map.get("ZIP") == null || "".equals(map.get("ZIP"))) {
        	//return "우편번호를 입력해주시기 바랍니다.";
        //}
        //if (map.get("PST_ADDR") == null || "".equals(map.get("PST_ADDR"))) {
        	//return "우편주소를 입력해주시기 바랍니다.";
        //}
        //if (map.get("DADDR") == null || "".equals(map.get("DADDR"))) {
        	//return "상세주소를 입력해주시기 바랍니다.";
        //}
        //if (map.get("COUNSELEE_YN") == null || "".equals(map.get("COUNSELEE_YN"))) {
        	//return "내담자 여부를 선택해주시기 바랍니다.";
        //}

        //if (map.get("UNT_SYS_SE_CD") == null || "".equals(map.get("UNT_SYS_SE_CD"))) {
        	//return "단위 시스템을 선택해주시기 바랍니다.";
        //}
        //if (map.get("OGDP_INST_CD") == null || "".equals(map.get("OGDP_INST_CD"))) {
        	//return "소속 기관을 선택해주시기 바랍니다.";
        //} //else if ("0".equals(map.get("ORG_CODE")) && (map.get("ORG_NAME") == null || "".equals(map.get("ORG_NAME")))) {
        	//return "소속 기관을 입력해주시기 바랍니다.";
        //}
        //if (map.get("OGDP_DEPT_CD") == null || "".equals(map.get("OGDP_DEPT_CD"))) {
        	//return "소속 부서를 선택해주시기 바랍니다.";
        //} //else if ("0".equals(map.get("DEPT_CODE")) && (map.get("DEPT_NAME") == null || "".equals(map.get("DEPT_NAME")))) {
        	//return "소속 부서를 입력해주시기 바랍니다.";
        //}
        //if (map.get("SUB_ORG_ID") == null || "".equals(map.get("SUB_ORG_ID"))) {
        	//return "하부 조직을 선택해주시기 바랍니다.";
        //}
        //if (map.get("ORG_NAME") == null || "".equals(map.get("ORG_NAME"))) {}
        //if (map.get("DEPT_NAME") == null || "".equals(map.get("DEPT_NAME"))) {}
        //log.debug("#### UNT_TASKWK_SE_CD : " + map.get("UNT_TASKWK_SE_CD"));
        if (map.get("UNT_TASKWK_SE_CD") == null || "".equals(map.get("UNT_TASKWK_SE_CD")) || "0".equals(map.get("UNT_TASKWK_SE_CD"))) {
        	return "단위 시스템을 선택해주시기 바랍니다.";
        }
        if (map.get("OGDP_INST_NO") == null || "".equals(map.get("OGDP_INST_NO"))) {
        	return "소속 기관을 선택해주시기 바랍니다.";
        }
        if (map.get("OGDP_DEPT_CD") == null || "".equals(map.get("OGDP_DEPT_CD"))) {
        	return "소속 부서를 선택해주시기 바랍니다.";
        }

        //if (map.get("ISTDR_YN") == null || "".equals(map.get("ISTDR_YN"))) {
        	//return "기관장 여부를 선택해주시기 바랍니다.";
        //}
        
        //if (map.get("QLFC_SE_CD") == null || "".equals(map.get("QLFC_SE_CD"))) {
        	//return "자격구분코드를 선택해주시기 바랍니다.";
        //}
        
        //if (map.get("LAST_ACBG_SE_CD") == null || "".equals(map.get("LAST_ACBG_SE_CD"))) {
        	//return "최종 학력을 선택해주시기 바랍니다.";
        //}
        //if (map.get("FINAL_EDUCATION") == null || "".equals(map.get("FINAL_EDUCATION"))) {
        	//return "최종 학력 기관명을 입력해주시기 바랍니다.";
        //}

        //if (map.get("MRG_YN") == null || "".equals(map.get("MRG_YN"))) {
        	//return "결혼 여부를 선택해주시기 바랍니다.";
        //}
        
		return "";
	}
	
	@Override
	public Map<String, String> saveWorkerInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> returnMap = new HashMap<>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmParam1 = dataRequest.getParameterGroup("dmWorker");
		ParameterGroup dmEnfsnNo = dataRequest.getParameterGroup("dmEnfsnNo");
		
		//ParameterGroup dmParam2 = dataRequest.getParameterGroup("dsWorkerUnitSystem");

		String resultStr = checkValidity(dmParam1.getSingleValueMap());
		
		if (resultStr != null && !"".equals(resultStr)) {
			returnMap.put("resultStr", resultStr);
			return returnMap;
		}
		
		Map<String, Object> map1 = new HashMap<>(dmParam1.getSingleValueMap());
				
		//ScpDb scpDb = new ScpDb();
		
		//map1.put("FLNM_ENCPT", scpDb.scpEncB64((String)map1.get("FLNM_ENCPT")));
		//String sMblTelNo = map1.get("MBL_TELNO_ENCPT") == null ? "" : ((String)map1.get("MBL_TELNO_ENCPT")).replace("-", "");
		//if(! "".equals(sMblTelNo) && ! "null".equals(sMblTelNo)) {
			//map1.put("MBL_TELNO_ENCPT", scpDb.scpEncB64(sMblTelNo));
		//}		
		String sWreTelNo = map1.get("WRD_TELNO") == null ? "" : ((String)map1.get("WRD_TELNO")).replace("-", "");
		if(! "".equals(sWreTelNo) && ! "null".equals(sWreTelNo)) {
			map1.put("WRD_TELNO", sWreTelNo);
		}		
		//String sEmlAddr  = map1.get("EML_ADDR_ENCPT") == null ? "" : ((String)map1.get("EML_ADDR_ENCPT"));
		//if(! "".equals(sEmlAddr) && ! "null".equals(sEmlAddr)) {
			//map1.put("EML_ADDR_ENCPT", scpDb.scpEncB64(sEmlAddr));
		//}
		//String sMsNgID   = map1.get("MSNGR_ID_ENCPT") == null ? "" : ((String)map1.get("MSNGR_ID_ENCPT"));
		//if(! "".equals(sMsNgID) && ! "null".equals(sMsNgID)) {
			//map1.put("MSNGR_ID_ENCPT", scpDb.scpEncB64(sMsNgID));
		//}
		
		map1.put("USER_ID2", userId2);
		
		//List<Map<String, String>> list2 = dmParam2.getAllRowList();

		map1.put("USER_ID", dmEnfsnNo == null ? userId2 : map1.get("USER_ID"));
		
		personalInfoMapper.updateWorkerInfo(map1);
		personalInfoMapper.updateWorkerInfoHistory(map1);
		
		/* 2023-02-10 YOO.CHI.HOON 화면조회를 위해 userId, enfsnNo 전달위해 추가*/
		log.info("========== saveWorkerInfo.화면조회용 아이디, 종사자번호 담기 시작 ==========");
		returnMap.put("USER_ID", (map1.get("USER_ID") == null ? "" : String.valueOf(map1.get("USER_ID"))));
		returnMap.put("ENFSN_NO", (map1.get("ENFSN_NO") == null ? "" : String.valueOf(map1.get("ENFSN_NO"))));
		returnMap.put("INDV_IDNTFC_NO", (map1.get("ENFSN_NO") == null ? "" : String.valueOf(map1.get("INDV_IDNTFC_NO"))));
		log.info(returnMap.get("USER_ID"));
		log.info(returnMap.get("ENFSN_NO"));
		log.info(returnMap.get("INDV_IDNTFC_NO"));
		log.info("========== saveWorkerInfo.화면조회용 아이디, 종사자번호 담기 종료 ==========");		
		
		int count = personalInfoMapper.selectUserInfoExists((String)map1.get("USER_ID"));
		if (count > 0) {
			map1.put("DATAA_CHG_SE_CD", "U");
		} else {
			map1.put("DATAA_CHG_SE_CD", "I");
		}		
		personalInfoMapper.updateUserInfo(map1);
		personalInfoMapper.insertUserInfoHistory(map1);
		
		personalInfoMapper.updatePersonalInfo(map1);
		map1.put("DATAA_CHG_SE_CD", "U");
		personalInfoMapper.insertPersonalInfoHistory(map1);
		
		/* 2023-03-03 YOO.CHI.HOON
		    내정보수정 내 담당업무 수정
			담당업무가 기관-담당자 일경우
			AUTHRT_SE_CD 변경이 가능하도록 하며
			변경후 저장시 AUTHRT_SE_CD 코드값을 기준으로
			메뉴(SAB300)를 해당 종사자 메뉴T(SAB250)에 delete-->insert 해야 합니다.(SAB300 --> SAB250)
			저장전에 "메뉴가변경됩니다. 수정하시겠습니까" 하고 메세지를 띄워야 합니다.*/
		String sAfterAuthSeCd  = String.valueOf(map1.get("AUTHRT_SE_CD"));
		String sOgdpInstNo     = String.valueOf(map1.get("OGDP_INST_NO"));	/* 종사자테이블 기관번호*/
		map1.put("INST_NO", sOgdpInstNo);	/* 담당업무수정시 종사자테이블에서 OGDP_INST_NO 컬럼으로 조회 UPDATE 매핑컬럼으로 추가*/
		
		int iChk = 0;
		String sBeForeAuthSeCd = ""; 
		sBeForeAuthSeCd = personalInfoMapper.selectUserAuthSeCd(map1);
		

		if (sAfterAuthSeCd == null || "null".equals(sAfterAuthSeCd)) {
			sAfterAuthSeCd = sBeForeAuthSeCd;
		}
		
		log.info("#### sAfterAuthSeCd : " + sAfterAuthSeCd);
		
		
		if(! sAfterAuthSeCd.equals(sBeForeAuthSeCd)) {
//			log.debug("========== sAfterAuthSeCd  =========[" + sAfterAuthSeCd + "]");
//			log.debug("========== sBeForeAuthSeCd =========[" + sBeForeAuthSeCd + "]");
			if(! "".equals(sBeForeAuthSeCd) && ! "null".equals(sBeForeAuthSeCd) || ! "".equals(sAfterAuthSeCd) && ! "null".equals(sAfterAuthSeCd)) {
				log.info("========= 권한구분코드 update Start =========");
				/* SEB230 Update*/
				int uptCnt = personalInfoMapper.updateUserInstAuth(map1);
				
				if(uptCnt > 0) {
					
					map1.put("AUTHRT_SE_CD", sBeForeAuthSeCd);
					map1.put("authrtId", sBeForeAuthSeCd);
					
					/* SEB250 Delete*/
					mgmtAuthGrpService.deleteMenuAuthMapping(request, map1);
					/* SEB300 Select*/
					map1.put("AUTHRT_SE_CD ", sAfterAuthSeCd);	/* 변경후 권한구분코드*/
					
					/* 사용자기관권한조회*/
					iChk = personalInfoMapper.getUserAuthCnt(map1);
					
					log.info("#### sBeForeAuthSeCd : " + sBeForeAuthSeCd + ", sAfterAuthSeCd : " + sAfterAuthSeCd);
					
					if(iChk == 1) {
						/* SEB250 Insert 사용자별 메뉴권한*/
						map1.put("AUTHRT_SE_CD", sAfterAuthSeCd);
					}else if(iChk > 1) {
						
//						dmParam1.get(0).setValue("AUTHRT_SE_CDS", sAfterAuthSeCd);	/* 신규등록 권한*/
					}
					mgmtAuthGrpService.saveMenuAuthMapping(request, map1);
				}
				log.info("========= 권한구분코드 update End =========");
			}
		}
		
		/*
		personalInfoMapper.deleteWorkerUnitSystem(map1);
		
		if (list2 != null) {
			for (int i=0; i < list2.size(); i++) {
				Map<String, String> map2 = list2.get(i);
				map2.put("ENFSN_NO", (String)map1.get("ENFSN_NO"));
				map2.put("USER_ID", userId2);
				personalInfoMapper.insertWorkerUnitSystem(map2);
			}
		}
		*/
		
		ParameterGroup dmParam2 = dataRequest.getParameterGroup("dsEducation");
		List<Map<String, String>> eduList = dmParam2.getAllRowList();
		//log.debug("#### eduList size : " + eduList.size());
		personalInfoMapper.deleteEducation((String)map1.get("ENFSN_NO"));
		if (eduList != null && eduList.size() > 0) {
			for (int i=0; i < eduList.size(); i++) {
				Map<String, String> map = eduList.get(i);
				map.put("USER_ID", userId2);
				personalInfoMapper.insertEducation(map);
			}
		}

		ParameterGroup dmParam3 = dataRequest.getParameterGroup("dsWork");
		List<Map<String, String>> workList = dmParam3.getAllRowList();
		//log.debug("#### workList size : " + workList.size());
		personalInfoMapper.deleteWork((String)map1.get("ENFSN_NO"));
		if (workList != null && workList.size() > 0) {
			for (int i=0; i < workList.size(); i++) {
				Map<String, String> map = workList.get(i);
				map.put("USER_ID", userId2);
				personalInfoMapper.insertWork(map);
			}
		}

		ParameterGroup dmParam4 = dataRequest.getParameterGroup("dsQualification");
		List<Map<String, String>> qualificationList = dmParam4.getAllRowList();
		//log.debug("#### qualificationList size : " + qualificationList.size());
		if (map1.get("QLFC_INFO_MNG_NO") != null && !"".equals((String)map1.get("QLFC_INFO_MNG_NO"))) {
			Map<String, String> map = new HashMap<>();
			map.put("QLFC_INFO_MNG_NO", (String)map1.get("QLFC_INFO_MNG_NO"));
			map.put("USER_ID", userId2);
			personalInfoMapper.insertQualificationNo(map);
			personalInfoMapper.deleteQualification((String)map1.get("QLFC_INFO_MNG_NO"));
			if (qualificationList != null && qualificationList.size() > 0) {
				for (int i=0; i < qualificationList.size(); i++) {
					map = qualificationList.get(i);
					map.put("USER_ID", userId2);
					personalInfoMapper.insertQualification(map);
				}
			}
		}
		
		/*
		 * 마이페이지 > 내 정보 수정 > 추가정보 저장
		 * 2022.08.22 Hee Sung Yoon
		 * 
		 * 2023.06.15 Hee Sung Yoon
		 * AKA600테이블 delete insert에서 CUD로 수정
		 */
		String enfsnNo = map1.get("ENFSN_NO").toString();
		ParameterGroup dmParam5 = dataRequest.getParameterGroup("dsCnterEnfsnInfo");
		Iterator<ParameterRow> insRows = dmParam5.getInsertedRows();
		Iterator<ParameterRow> updRows = dmParam5.getUpdatedRows();
		Iterator<ParameterRow> delRows = dmParam5.getDeletedRows();		
		
		while (insRows.hasNext()) {
			Map<String, String> mapIns = insRows.next().toMap();
			if("".equals(mapIns.get("ENFSN_NO")) || mapIns.get("ENFSN_NO") == null) {
				mapIns.put("ENFSN_NO", enfsnNo);
			}
			mapIns.put("USER_ID", userId2);
			personalInfoMapper.insEnfsnInfo(mapIns);
		}
		while (updRows.hasNext()) {
			Map<String, String> mapUpd = updRows.next().toMap();
			if("".equals(mapUpd.get("ENFSN_NO")) || mapUpd.get("ENFSN_NO") == null) {
				mapUpd.put("ENFSN_NO", enfsnNo);
			}
			mapUpd.put("USER_ID", userId2);
			personalInfoMapper.insEnfsnInfo(mapUpd);
		}
		while (delRows.hasNext()) {
			Map<String, String> mapDel = delRows.next().toMap();
			if("".equals(mapDel.get("ENFSN_NO")) || mapDel.get("ENFSN_NO") == null) {
				mapDel.put("ENFSN_NO", enfsnNo);
			}
			personalInfoMapper.delEnfsnInfo(enfsnNo);
		}
		/*
		List<Map<String, String>> enfsnInfoList = dmParam5.getAllRowList();
		if(enfsnInfoList.size() > 0) {
			personalInfoMapper.delEnfsnInfo(enfsnNo);
			for(Map<String, String> map : enfsnInfoList) {
				if("".equals(map.get("ENFSN_NO")) || map.get("ENFSN_NO") == null) {
					map.put("ENFSN_NO", enfsnNo);
				}
				map.put("USER_ID", userId2);
				personalInfoMapper.insEnfsnInfo(map);
			}
		}
		*/
		ParameterGroup dmParam7 = dataRequest.getParameterGroup("dsCnterEnfsnCerti");
		Iterator<ParameterRow> insertedRows = dmParam7.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dmParam7.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dmParam7.getDeletedRows();
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			if("".equals(mapIns.get("ENFSN_NO")) || mapIns.get("ENFSN_NO") == null) {
				mapIns.put("ENFSN_NO", enfsnNo);
			}
			mapIns.put("FRST_RGTR_ID", userId2);
			mapIns.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.insEnfsnCerti(mapIns);
		}
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			if("".equals(mapUpd.get("ENFSN_NO")) || mapUpd.get("ENFSN_NO") == null) {
				mapUpd.put("ENFSN_NO", enfsnNo);
			}
			mapUpd.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.updEnfsnCerti(mapUpd);
		}
		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			if("".equals(mapDel.get("ENFSN_NO")) || mapDel.get("ENFSN_NO") == null) {
				mapDel.put("ENFSN_NO", enfsnNo);
			}
			mapDel.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.delEnfsnCerti(mapDel);
		}
		
		ParameterGroup dmParam8 = dataRequest.getParameterGroup("dsCnterEnfsnTrnngEdu");
		insertedRows = dmParam8.getInsertedRows();
		updatedRows = dmParam8.getUpdatedRows();
		deletedRows = dmParam8.getDeletedRows();
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId2);
			mapIns.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.insEnfsnTrnngEdu(mapIns);
		}
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.updEnfsnTrnngEdu(mapUpd);
		}
		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.delEnfsnTrnngEdu(mapDel);
		}
		
		ParameterGroup dmParam9 = dataRequest.getParameterGroup("dsCnterEnfsnYngbgsPrvateCerti");
		insertedRows = dmParam9.getInsertedRows();
		updatedRows = dmParam9.getUpdatedRows();
		deletedRows = dmParam9.getDeletedRows();
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId2);
			mapIns.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.insEnfsnPrvateCerti(mapIns);
		}
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.updEnfsnPrvateCerti(mapUpd);
		}
		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId2);
			personalInfoMapper.delEnfsnPrvateCerti(mapDel);
		}

		// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
		mgmtMenuMapper.updateUserMenuUpdateCountIncrease(String.valueOf(map1.get("USER_ID")));
		
		return returnMap;
	}
	
	@Override
	public Map<String, String> deleteWorkerInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> returnMap = new HashMap<>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmEnfsnNo = dataRequest.getParameterGroup("dmEnfsnNo");
		
		Map<String, String> map = dmEnfsnNo.getSingleValueMap();
		
		map.put("USER_ID2", userId2);

		map.put("USER_ID", dmEnfsnNo == null ? userId2 : map.get("USER_ID"));
		
		int count = personalInfoMapper.selectWorkerInfoExists(map);
		if (count == 0) {
			returnMap.put("resultStr", "종사자 정보가 존재하지 않습니다.");
			return returnMap;
		}
		
		map.put("DATAA_CHG_SE_CD", "D");
		
		map.put("DEL_YN", "Y");
		
		map.put("ENFSN_NO", map.get("enfsnNo"));
		
		if (map.get("enfsnNo") != null && !"".equals(map.get("enfsnNo")) && !"null".equals(map.get("enfsnNo")) ) {
			personalInfoMapper.deleteWorkerInfo(map);
			personalHistoryMapper.insertWorkerInfoHistory(map);
		}
		
		if (map.get("INDV_IDNTFC_NO") != null && !"".equals(map.get("INDV_IDNTFC_NO")) && !"null".equals(map.get("INDV_IDNTFC_NO")) ) {
			personalInfoMapper.deletePersonalInfo(map);
			personalHistoryMapper.insertPersonalInfoHistory(map);
		}
		
		return returnMap;
	}
	
	@Override
	public Map<String, String> selectSystemEnv() throws Exception {
		
		Map<String, String> map = personalInfoMapper.selectSystemEnv();
		
		if (map == null) {
			map = new HashMap<>();
		}
		
		//List<Map<String, String>> list = personalInfoMapper.selectLongTermNotConnect();
		
		//for (int i=0; i < list.size(); i++) {
			//Map<String, String> map1 = list.get(i);
			
			//if ("PART1".equals(map1.get("USER_TYPE_SE_CD"))) {
				map.put("part1Value", map == null || map.get("LOTE_NOCO_PRD_VALUE") == null ? "365" : String.valueOf(map.get("LOTE_NOCO_PRD_VALUE")));
				map.put("part1Unit", map == null || map.get("LOTE_NOCO_PRD_VALUE") == null ? "D" : String.valueOf(map.get("PRD_KND_SE_CD")));
				
			//} else if ("PART2".equals(map1.get("USER_TYPE_SE_CD"))) {
				//map.put("part2Value", String.valueOf(map1.get("LOTE_NOCO_PRD_VALUE")));
				//map.put("part2Unit", String.valueOf(map1.get("PRD_KND_SE_CD")));
			//}
		//}
		
		return map;
	}


	@Override
	public Map<String, String> selectInstituteInfo(Map<String, Object> loginMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfo(loginMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectInstituteInfo(workerMap);
		//Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);

		//ScpDb scpDb = new ScpDb();

		//if (workerMap2 != null) {
			//if (workerMap2.get("PIC_MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("PIC_MBL_TELNO_ENCPT"))) {
				//workerMap2.put("PIC_MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap2.get("PIC_MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("PIC_EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("PIC_EML_ADDR_ENCPT"))) {
				//workerMap2.put("PIC_EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap2.get("PIC_EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("PIC_NM_ENCPT") != null && !"".equals(workerMap2.get("PIC_NM_ENCPT"))) {
				//workerMap2.put("PIC_NM_ENCPT", scpDb.scpDecB64(workerMap2.get("PIC_NM_ENCPT")));
			//}
			//if (workerMap2.get("RPRSV_NM_ENCPT") != null && !"".equals(workerMap2.get("RPRSV_NM_ENCPT"))) {
				//workerMap2.put("RPRSV_NM_ENCPT", scpDb.scpDecB64(workerMap2.get("RPRSV_NM_ENCPT")));
				//log.debug("#### RPRSV_NM_ENCPT : " + workerMap2.get("RPRSV_NM_ENCPT"));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		/*
		String RRNO = workerMap1.get("RRNO_ENCPT");
		String strDec = "";
		
		try {
			ScpDb scpDb = new ScpDb();
			strDec = scpDb.scpDecB64(RRNO);
		} catch (Exception e) {
			e.printStackTrace();
			strDec = "";
		}
		workerMap1.put("RRNO", strDec);
		*/
		
		//workerMap1.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));
		//workerMap2.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));

		//if (workerMap1 != null) {
			//workerMap1.forEach((key, value) -> {
					//if (workerMap.get(key) == null && value != null) {
						//workerMap.put(key, value);
					//}
				//});
		//}
		
		if (workerMap2 != null) {
			workerMap2.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, value);
					}
				});
		}
		
		return workerMap;
	}


	@Override
	public Map<String, String> selectYouthInfo(Map<String, Object> loginMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfo(loginMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectYouthInfo(workerMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);

		//ScpDb scpDb = new ScpDb();

		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("STTY_AGT_NM_ENCPT") != null && !"".equals(workerMap2.get("STTY_AGT_NM_ENCPT"))) {
				//workerMap2.put("STTY_AGT_NM", scpDb.scpDecB64(workerMap2.get("STTY_AGT_NM_ENCPT")));
				//workerMap2.put("STTY_AGT_NM_ENCPT", scpDb.scpDecB64(workerMap2.get("STTY_AGT_NM_ENCPT")));
			//}
			//if (workerMap2.get("PSPT_ENG_FLNM_ENCPT") != null && !"".equals(workerMap2.get("PSPT_ENG_FLNM_ENCPT"))) {
				//workerMap2.put("PSPT_ENG_FLNM", scpDb.scpDecB64(workerMap2.get("PSPT_ENG_FLNM_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		/*
		String RRNO = workerMap1.get("RRNO_ENCPT");
		String strDec = "";
		
		try {
			ScpDb scpDb = new ScpDb();
			strDec = scpDb.scpDecB64(RRNO);
		} catch (Exception e) {
			e.printStackTrace();
			strDec = "";
		}
		workerMap1.put("RRNO", strDec);
		*/
		
		//workerMap1.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));
		//workerMap2.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));

		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, value);
					}
				});
		}
		
		if (workerMap2 != null) {
			workerMap2.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, value);
					}
				});
		}
		
		return workerMap;
	}


	@Override
	public Map<String, String> selectGuardianInfo(Map<String, Object> loginMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfo(loginMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectGuardianInfo(workerMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);

		//ScpDb scpDb = new ScpDb();

		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("STTY_AGT_NM_ENCPT") != null && !"".equals(workerMap2.get("STTY_AGT_NM_ENCPT"))) {
				//workerMap2.put("STTY_AGT_NM", scpDb.scpDecB64(workerMap2.get("STTY_AGT_NM_ENCPT")));
				//workerMap2.put("STTY_AGT_NM_ENCPT", scpDb.scpDecB64(workerMap2.get("STTY_AGT_NM_ENCPT")));
			//}
			//if (workerMap2.get("PSPT_ENG_FLNM_ENCPT") != null && !"".equals(workerMap2.get("PSPT_ENG_FLNM_ENCPT"))) {
				//workerMap2.put("PSPT_ENG_FLNM", scpDb.scpDecB64(workerMap2.get("PSPT_ENG_FLNM_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		/*
		String RRNO = workerMap1.get("RRNO_ENCPT");
		String strDec = "";
		
		try {
			ScpDb scpDb = new ScpDb();
			strDec = scpDb.scpDecB64(RRNO);
		} catch (Exception e) {
			e.printStackTrace();
			strDec = "";
		}
		workerMap1.put("RRNO", strDec);
		*/
		
		//workerMap1.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));
		//workerMap2.forEach((key, value) -> workerMap.merge(key, value, (v1, v2) -> v2));

		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, value);
					}
				});
		}
		
		if (workerMap2 != null) {
			workerMap2.forEach((key, value) -> {
					if (workerMap.get(key) == null && value != null) {
						workerMap.put(key, value);
					}
				});
		}
		
		return workerMap;
	}


	@Override
	public Map<String, String> selectWorkerInfoNo(Map<String, String> noMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfoWorker(noMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectWorkerInfo(noMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);
		
		//ScpDb scpDb = new ScpDb();

		//if (workerMap != null) {
			//if (workerMap.get("JOIN_AUTZR_NM") != null && !"".equals(workerMap.get("JOIN_AUTZR_NM"))) {
				//workerMap.put("JOIN_AUTZR_NM", scpDb.scpDecB64(workerMap.get("JOIN_AUTZR_NM")));
			//}
		//}
		
		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap2.get("MSNGR_ID_ENCPT"))) {
				//workerMap2.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap2.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}

		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
				if (workerMap2 != null && workerMap2.get(key) == null && value != null) {
					workerMap2.put(key, String.valueOf(value));
				}
			});
		}

		if (workerMap != null) {
			workerMap.forEach((key, value) -> {
				if (workerMap2 != null && workerMap2.get(key) == null && value != null) {
					workerMap2.put(key, String.valueOf(value));
				}
			});
		}
		
		log.info( "==== workerMap ====22" + "[" + workerMap2);		
		
		return workerMap2;
	}

	@Override
	public Map<String, String> selectInstituteInfoNo(Map<String, String> noMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfoInstitute(noMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectInstituteInfo(noMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);

		//ScpDb scpDb = new ScpDb();

		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap2.get("MSNGR_ID_ENCPT"))) {
				//workerMap2.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap2.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM_ENCPT", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO_ENCPT", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR_ENCPT", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID_ENCPT", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}

		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
					if (workerMap2.get(key) == null && value != null) {
						workerMap2.put(key, value);
					}
				});
		}

		if (workerMap != null) {
			workerMap.forEach((key, value) -> {
					if (workerMap2.get(key) == null && value != null) {
						workerMap2.put(key, String.valueOf(value));
					}
				});
		}
		
		return workerMap2;
	}


	@Override
	public Map<String, String> selectYouthInfoNo(Map<String, String> noMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfoYouthGuardian(noMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectYouthInfo(noMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);

		//ScpDb scpDb = new ScpDb();

		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("STTY_AGT_NM_ENCPT") != null && !"".equals(workerMap2.get("STTY_AGT_NM_ENCPT"))) {
				//workerMap2.put("STTY_AGT_NM", scpDb.scpDecB64(workerMap2.get("STTY_AGT_NM_ENCPT")));
			//}
			//if (workerMap2.get("PSPT_ENG_FLNM_ENCPT") != null && !"".equals(workerMap2.get("PSPT_ENG_FLNM_ENCPT"))) {
				//workerMap2.put("PSPT_ENG_FLNM", scpDb.scpDecB64(workerMap2.get("PSPT_ENG_FLNM_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}
		
		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
				if (workerMap2.get(key) == null && value != null) {
					workerMap2.put(key, value);
				}
			});
		}

		if (workerMap != null) {
			workerMap.forEach((key, value) -> {
				if (workerMap2.get(key) == null && value != null) {
					workerMap2.put(key, String.valueOf(value));
				}
			});
		}
		
		return workerMap2;
	}


	@Override
	public Map<String, String> selectGuardianInfoNo(Map<String, String> noMap) throws Exception {
		
		Map<String, String> workerMap = personalInfoMapper.selectUserInfoYouthGuardian(noMap);
		Map<String, String> workerMap2 = personalInfoMapper.selectGuardianInfo(noMap);
		Map<String, String> workerMap1 = personalInfoMapper.selectPersonalInfo(workerMap2);

		//ScpDb scpDb = new ScpDb();

		//if (workerMap2 != null) {
			//if (workerMap2.get("FLNM_ENCPT") != null && !"".equals(workerMap2.get("FLNM_ENCPT"))) {
				//workerMap2.put("FLNM", scpDb.scpDecB64(workerMap2.get("FLNM_ENCPT")));
			//}
			//if (workerMap2.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap2.get("MBL_TELNO_ENCPT"))) {
				//workerMap2.put("MBL_TELNO", scpDb.scpDecB64(workerMap2.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap2.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap2.get("EML_ADDR_ENCPT"))) {
				//workerMap2.put("EML_ADDR", scpDb.scpDecB64(workerMap2.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap2.get("STTY_AGT_NM_ENCPT") != null && !"".equals(workerMap2.get("STTY_AGT_NM_ENCPT"))) {
				//workerMap2.put("STTY_AGT_NM", scpDb.scpDecB64(workerMap2.get("STTY_AGT_NM_ENCPT")));
			//}
			//if (workerMap2.get("PSPT_ENG_FLNM_ENCPT") != null && !"".equals(workerMap2.get("PSPT_ENG_FLNM_ENCPT"))) {
				//workerMap2.put("PSPT_ENG_FLNM", scpDb.scpDecB64(workerMap2.get("PSPT_ENG_FLNM_ENCPT")));
			//}
		//}
		
		//if (workerMap1 != null) {
			//if (workerMap1.get("FLNM_ENCPT") != null && !"".equals(workerMap1.get("FLNM_ENCPT"))) {
				//workerMap1.put("FLNM", scpDb.scpDecB64(workerMap1.get("FLNM_ENCPT")));
			//}
			//if (workerMap1.get("MBL_TELNO_ENCPT") != null && !"".equals(workerMap1.get("MBL_TELNO_ENCPT"))) {
				//workerMap1.put("MBL_TELNO", scpDb.scpDecB64(workerMap1.get("MBL_TELNO_ENCPT")));
			//}
			//if (workerMap1.get("EML_ADDR_ENCPT") != null && !"".equals(workerMap1.get("EML_ADDR_ENCPT"))) {
				//workerMap1.put("EML_ADDR", scpDb.scpDecB64(workerMap1.get("EML_ADDR_ENCPT")));
			//}
			//if (workerMap1.get("MSNGR_ID_ENCPT") != null && !"".equals(workerMap1.get("MSNGR_ID_ENCPT"))) {
				//workerMap1.put("MSNGR_ID", scpDb.scpDecB64(workerMap1.get("MSNGR_ID_ENCPT")));
			//}
		//}

		if (workerMap1 != null) {
			workerMap1.forEach((key, value) -> {
					if (workerMap2.get(key) == null && value != null) {
						workerMap2.put(key, value);
					}
				});
		}
		
		if (workerMap != null) {
			workerMap.forEach((key, value) -> {
					if (workerMap2.get(key) == null && value != null) {
						workerMap2.put(key, String.valueOf(value));
					}
				});
		}
		
		return workerMap2;
	}

	@Override
	public String selectPersonalInfoId(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		Map<String, String> map = new HashMap<>();
		map.put("USER_ID", userId2);
		
		return personalInfoMapper.selectPersonalInfoId(map);
	}
	
	@Override
	public String savePersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmPersonalInfo");

		Map<String, Object> map = new HashMap<>(dmParam.getSingleValueMap());
		
		return subSavePersonalInfo(map, userId2);
	}

	
	@Override
	public String savePersonalInfo(HttpServletRequest request, Map<String, Object> infoMap) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		return subSavePersonalInfo(infoMap, userId2);
	}

	
	@Override
	public Map<String, String> deletePersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> returnMap = new HashMap<>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmPersonalInfo = dataRequest.getParameterGroup("dmPersonalInfo");
		
		Map<String, String> map = dmPersonalInfo.getSingleValueMap();
		
		map.put("USER_ID2", userId2);

		map.put("USER_ID", dmPersonalInfo == null ? userId2 : map.get("USER_ID"));
		
		int count = personalInfoMapper.selectPersonalInfoExists(map);
		if (count == 0) {
			returnMap.put("RESULT", "개인 정보가 존재하지 않습니다.");
			return returnMap;
		}
		
		map.put("DATAA_CHG_SE_CD", "D");
		
		map.put("DEL_YN", "Y");
		
		int cnt = personalInfoMapper.deletePersonalInfo(map);
		if (cnt == 0) {
			returnMap.put("RESULT", "삭제된 내역이 없습니다.");
		} else {
			returnMap.put("RESULT", "삭제되었습니다.");
		}
		personalHistoryMapper.insertPersonalInfoHistory(map);
		
		return returnMap;
	}
	
	
	private String subSavePersonalInfo(Map<String, Object> map, String userId2) throws Exception {
		
		Map<String, String> map1 = new HashMap<>();
		map1.put("USER_ID", userId2);

		String indvIdntfcNo = (String)map.get("INDV_IDNTFC_NO");
		String personalInfoId = indvIdntfcNo;
		
		if (indvIdntfcNo == null || "".equals(indvIdntfcNo)) {
			personalInfoId = personalInfoMapper.selectPersonalInfoId(map1);
		}
		
		map1.put("INDV_IDNTFC_NO", personalInfoId);
		
		int cnt = personalInfoMapper.selectPersonalInfoIsExists(map1);

		map.put("INDV_IDNTFC_NO", personalInfoId);
		map.put("USER_ID", userId2);
		
		if (map.get("MRG_YN") == null || "".equals((String)map.get("MRG_YN"))) {
			map.put("MRG_YN", "N");
		}
		
		if (map.get("FLNM") == null || "".equals((String)map.get("FLNM"))) {
			//throw new Exception("성명을 입력해주시기 바랍니다.");
			throw new UserException("errors.nameRequired");
		}
		
		//ScpDb scpDb = new ScpDb();
		
		//if (map.get("FLNM") != null && !"".equals((String)map.get("FLNM"))) {
			//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM")));
		//} else if (map.get("FLNM_ENCPT") != null && !"".equals((String)map.get("FLNM_ENCPT"))) {
			//map.put("FLNM_ENCPT", scpDb.scpEncB64((String)map.get("FLNM_ENCPT")));
		//}

		if (map.get("FLNM") != null && !"".equals((String)map.get("FLNM"))) {
			map.put("FLNM_ENCPT", (String)map.get("FLNM"));
		} else if (map.get("FLNM_ENCPT") != null && !"".equals((String)map.get("FLNM_ENCPT"))) {
			map.put("FLNM_ENCPT", (String)map.get("FLNM_ENCPT"));
		}
		
		//map.put("RRNO_ENCPT", scpDb.scpEncB64((String)map.get("RRNO")));
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64((String)map.get("MBL_TELNO")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64((String)map.get("EML_ADDR")));
		//map.put("MSNGR_ID_ENCPT", scpDb.scpEncB64((String)map.get("MSNGR_ID")));

		map.put("RRNO_ENCPT", (String)map.get("RRNO"));
		map.put("MBL_TELNO_ENCPT", (String)map.get("MBL_TELNO"));
		map.put("EML_ADDR_ENCPT", (String)map.get("EML_ADDR"));
		map.put("MSNGR_ID_ENCPT", (String)map.get("MSNGR_ID"));
		
		int resultCnt = 0;
		
		if (cnt > 0) {
			// update
			resultCnt = personalInfoMapper.updatePersonalInfo(map);
			
			map.put("DATAA_CHG_SE_CD", "U");
			
			personalInfoMapper.insertPersonalInfoHistory(map);
			
			if ("WORKER".equals(map.get("MEMBER_TYPE"))) {
				
				List<String> enfsnNoList = personalInfoMapper.selectWorkerPersonal((String)map.get("INDV_IDNTFC_NO"));
				
				if (enfsnNoList != null && enfsnNoList.size() > 0) {
					for (int i=0; i < enfsnNoList.size(); i++) {
						map.put("ENFSN_NO", enfsnNoList.get(i));
						int cnt1 = personalInfoMapper.updateWorkerInfoPersonal(map);
						if (cnt1 > 0) {
							map.put("DATAA_CHG_SE_CD", "U");
							personalInfoMapper.updateWorkerInfoPersonalHistory(map);
						}
					}
				}
				
			} else if ("YOUTH".equals(map.get("MEMBER_TYPE")) || "GUARDIAN".equals(map.get("MEMBER_TYPE"))) {
				List<String> yngbgsPrtcrNoList = personalInfoMapper.selectClientPersonal((String)map.get("INDV_IDNTFC_NO"));
				if (yngbgsPrtcrNoList != null && yngbgsPrtcrNoList.size() > 0) {
					for (int i=0; i < yngbgsPrtcrNoList.size(); i++) {
						map.put("YNGBGS_PRTCR_NO", yngbgsPrtcrNoList.get(i));
						int cnt1 = personalInfoMapper.updateClientInfoPersonal(map);
						if (cnt1 > 0) {
							map.put("DATAA_CHG_SE_CD", "U");
							personalInfoMapper.updateClientInfoPersonalHistory(map);
						}
					}
				}
			}
			
		} else {
			// insert
			resultCnt = personalInfoMapper.insertPersonalInfo(map);
			
			map.put("DATAA_CHG_SE_CD", "I");
			
			personalInfoMapper.insertPersonalInfoHistory(map);
		}
		
		return personalInfoId;
	}
	
	
	// 학력 정보 조회
	@Override
	public List<Map<String, Object>> selectEducation(String enfsnNo) throws Exception {
		return personalInfoMapper.selectEducation(enfsnNo);
	}
	
	// 자격 정보 조회
	@Override
	public List<Map<String, Object>> selectQualification(String qlfcInfoMngNo) throws Exception {
		return personalInfoMapper.selectQualification(qlfcInfoMngNo);
	}
	
	// 근무 이력 조회
	@Override
	public List<Map<String, Object>> selectWork(String enfsnNo) throws Exception {
		return personalInfoMapper.selectWork(enfsnNo);
	}

	
	// 자격정보관리번호 채번
	@Override
	public String selectQualificationNo(Map<String, String> map) throws Exception {
		return personalInfoMapper.selectQualificationNo(map);
	}
	
	// 종사자 정보
	@Override
	public List<Map<String, Object>> selectEnfsnInfo(String enfsnNo) throws Exception {
		return null;  // personalInfoMapper.selectEnfsnInfo(enfsnNo);
	}
	
	// 종사자자격증
	@Override
	public List<Map<String, Object>> selectEnfsnCerti(String enfsnNo) throws Exception {
		return null;  // personalInfoMapper.selectEnfsnCerti(enfsnNo);
	}
	
	// 종사자전문인력양성교육
	@Override
	public List<Map<String, Object>> selectEnfsnTrnngEdu(String enfsnNo) throws Exception {
		return null;  // personalInfoMapper.selectEnfsnTrnngEdu(enfsnNo);
	}	
	// 종사자청소년관련민간자격증
	@Override
	public List<Map<String, Object>> selectEnfsnYngbgsPrvateCerti(String enfsnNo) throws Exception {
		return null;  // personalInfoMapper.selectEnfsnYngbgsPrvateCerti(enfsnNo);
	}
	
	// 기관 타입
	@Override
	public Map<String, String> selectInstType(int instNo) throws Exception {
		return personalInfoMapper.selectInstType(instNo);
	}
	

	// 개인 정보 조회
	@Override
	public List<Map<String, Object>> selectPersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> map = dmParam.getSingleValueMap();

		//ScpDb scpDb = new ScpDb();
		
		//map.put("MBL_TELNO_ENCPT", scpDb.scpEncB64(map.get("MBL_TELNO_ENCPT")));
		//map.put("FLNM_ENCPT", scpDb.scpEncB64(map.get("FLNM_ENCPT")));
		//map.put("RRNO_ENCPT", scpDb.scpEncB64(map.get("RRNO_ENCPT")));
		//map.put("EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("EML_ADDR_ENCPT")));
		//map.put("MSNGR_ID_ENCPT", scpDb.scpEncB64(map.get("MSNGR_ID_ENCPT")));
		
		List<Map<String, Object>> list1 = personalInfoMapper.searchPersonalInfo(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//if (list1 != null && list1.size() > 0) {
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, Object> map2 = list1.get(i);
				//map2.put("FLNM", scpDb.scpDecB64((String)map2.get("FLNM_ENCPT")));
				//map2.put("MBL_TELNO", scpDb.scpDecB64((String)map2.get("MBL_TELNO_ENCPT")));
				//map2.put("RRNO", scpDb.scpDecB64((String)map2.get("RRNO_ENCPT")));
				//map2.put("EML_ADDR", scpDb.scpDecB64((String)map2.get("EML_ADDR_ENCPT")));
				//map2.put("MSNGR_ID", scpDb.scpDecB64((String)map2.get("MSNGR_ID_ENCPT")));
				//list2.add(map2);
			//}
		//}
		
		return list1;
	}

	// 회원 탈퇴 처리
	@Override
	public void saveWithdrawal(HttpServletRequest request) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		Map<String, String> map = new HashMap<>();
		map.put("USER_ID", userId2);
		
		personalInfoMapper.saveWithdrawal(map);
		personalInfoMapper.saveWithdrawalHistory(map);

		
		// 회원 탈퇴시 종사자 정보, 개인정보 삭제 처리
		Map<String, Object> map1 = new HashMap<>();
		
		map1.put("USER_ID", map.get("USER_ID"));
		
		Map<String, String> userInfoMap = personalInfoMapper.selectUserInfo(map1);
		
		userInfoMap.put("enfsnNo", userInfoMap.get("ENFSN_NO"));
		
		userInfoMap.put("USER_ID2", userId2);

		userInfoMap.put("DATAA_CHG_SE_CD", "D");
		userInfoMap.put("DEL_YN", "Y");
		
		if (userInfoMap.get("enfsnNo") != null && !"".equals(userInfoMap.get("enfsnNo")) && !"null".equals(userInfoMap.get("enfsnNo")) ) {
			personalInfoMapper.deleteWorkerInfo(userInfoMap);
			personalHistoryMapper.insertWorkerInfoHistory(userInfoMap);
		}
		
		if (userInfoMap.get("INDV_IDNTFC_NO") != null && !"".equals(userInfoMap.get("INDV_IDNTFC_NO")) && !"null".equals(userInfoMap.get("INDV_IDNTFC_NO")) ) {
			personalInfoMapper.deletePersonalInfo(userInfoMap);
			personalHistoryMapper.insertPersonalInfoHistory(userInfoMap);
		}
	}
	
	
	// 코드 사용 단위업무 조회
	@Override
	public List<Map<String, Object>> selectCommonuUseUnit(String codeId) throws Exception {

		List<Map<String, Object>> list = personalInfoMapper.selectCommonuUseUnit(codeId);
		return list;
	}
	
	// 사용자 기관 정보
	@Override
	public List<Map<String, Object>> selectAplyInstList(String userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<Map<String, Object>> list = userAuthAplyMapper.selectAplyInstList(map);
		return list;
	}
	
	/**
	 * @Method명   : processAuthrtReset
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 권한 초기화 처리
	 */
	@Override
	public void processAuthrtReset(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 권한 초기화 처리 데이터 DM
		ParameterGroup dmAuthrtReset = dataRequest.getParameterGroup("dmAuthrtReset");
		log.debug("### processAuthrtReset :: {}", dmAuthrtReset);
		
		// 권한 초기화할 사용자아이디 조회
		String userId = dmAuthrtReset.getValue("USER_ID");
		
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("userId", userId);
		
		// 1) 주기관 이외의 추가 기관 신청한내역 삭제 (SAB230)
		userInstAuthMapper.deleteUserInstAuthByReset(mapParam);
		 
		// 2) 사용자 메뉴 전체 삭제 (SAB250)
		mgmtUserAuthMapper.deleteUserAuthWithId(mapParam);
		
		// 사용자별 메뉴권한 삭제 이력
		Map<String, String> histortParam = new LinkedHashMap<>();
		histortParam.put("userId", userId);
		histortParam.put("USER_ID2", userDetailsVO.getId());
		mgmtUserAuthMapper.deleteUserAuthWithIdHistory(histortParam);
		
		// 3) 사용자 권한 최소권한 종사자로 세팅 (SAB230)
		mapParam.put("mdfrId", userDetailsVO.getId());
		userInstAuthMapper.updateUserInstAuthByReset(mapParam);
		
		// 4) 사용자 메뉴 초기세팅 (SAB250)
		mapParam.put("rgtrId", userDetailsVO.getId());
		mgmtUserAuthMapper.insertUserDetailAuthsByInstAuth(mapParam);
		
		List<Map<String, Object>> instAuthList = userInstAuthMapper.selectUserInstAuthList(userId);
		
		if (instAuthList != null && instAuthList.size() > 0) {
			Map<String, Object> instAuthMap = instAuthList.get(0);
			
			String aurhrtSeCd = (String) instAuthMap.get("AUTHRT_SE_CD");
			mapParam.put("USER_ID", userId);
			mapParam.put("AUTHRT_IDS", Arrays.asList(aurhrtSeCd));
			 
			// SAB250 (사용자별 메뉴권한) 목록 조회
			List<Map<String, Object>> userMenuAuthrtList = mgmtUserAuthMapper.selectUserAuthList(mapParam);
			
			for (Map<String, Object> menuAuthrtMap : userMenuAuthrtList) {
				// 사용자별 메뉴권한 이력 저장
				menuAuthrtMap.put("RGTR_ID", userDetailsVO.getId());	// 등록 (수정)자 아이디	
				menuAuthrtMap.put("DATAA_CHG_SE_CD", "I");				// 이력 추가로 지정
				mgmtAuthGrpMapper.insertUserMenuAuthHistory(menuAuthrtMap);
			}	
		}
		
		// 5) 권한 신청 내역 전체 삭제 (SAB240)
		userAuthAplyMapper.deleteUserAuthAplyByUserId(userId);
		
		// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
		mgmtMenuService.increaseMenuUpdateCountByUserId(userId);
	}
}
