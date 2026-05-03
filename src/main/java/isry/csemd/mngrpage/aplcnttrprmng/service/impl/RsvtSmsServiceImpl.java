/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csemd.mngrpage.aplcnttrprmng.mapper.RsvtSmsMapper;
import isry.csemd.mngrpage.aplcnttrprmng.service.RsvtSmsService;
import isry.itgcms.syscmmn.rsvtmng.mapper.RsvtMngMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry2.csemd.mngrpage.aplcnttrprmng.mapper.RsvtSms2Mapper;

/**
 * @파일명 : RsvtSmsServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 10. 14.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 10. 14.
 * @수정내용 : - -
 */
@Service("csemdRsvtSmsService")
public class RsvtSmsServiceImpl implements RsvtSmsService {

	@Resource(name = "csemdRsvtSmsMapper")
	private RsvtSmsMapper rsvtSmsMapper;

	@Resource(name = "csemdRsvtSms2Mapper")
	private RsvtSms2Mapper rsvtSms2Mapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "rsvtMngMapper")
	private RsvtMngMapper rsvtMngMapper;

	/**
	 * @Method명 : selectRcptnTrprList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectRcptnTrprList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dsParam = dataRequest.getParameterGroup("dsParam");

		List<Map<String, String>> getAllList = dsParam.getAllRowList();
		List<Map<String, String>> retMap = new ArrayList<Map<String, String>>();

		// 로그인한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sWrdTelno = loginVO.getWrdTelno(); // 기관전화번호
				
		for (Map<String, String> map : getAllList) {
			retMap.addAll(rsvtSmsMapper.selectRcptnTrprList(map));
		}

		for (Map<String, String> map : retMap) {
			map.put("REAL_RCPTN_TRPR_NM", map.get("RCPTN_TRPR_NM"));
			map.replace("RCPTN_TRPR_NM", Masking.nameMasking(map.get("RCPTN_TRPR_NM")));

			map.put("REAL_RCPTN_MBL_TELNO", map.get("RCPTN_MBL_TELNO"));
			map.replace("RCPTN_MBL_TELNO", Masking.phoneMasking(map.get("RCPTN_MBL_TELNO")));
			
			map.put("REAL_TRPR_NM", map.get("TRPR_NM"));
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
			
			map.put("CALL_TO", map.get("CALL_TO"));
			map.put("CALL_FROM", sWrdTelno);
		}
		return retMap;
	}

	/**
	 * @Method명 : insertRcptnTrpr
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :
	 */
	@Override
	public void insertRcptnTrpr(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> getAllRowList = dsList.getAllRowList();
		List<Map<String, String>> insertRow = new ArrayList<Map<String, String>>();

		// insert할 대상자
		for (int i = 0; i < getAllRowList.size(); i++) {
			if (!getAllRowList.get(i).get("DELETE").equals("delete")) {
				insertRow.add(getAllRowList.get(i));
				
			}else if (getAllRowList.get(i).get("DELETE").equals("insert")) {
				insertRow.add(getAllRowList.get(i));
			}
		}

		// 로그인한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId(); // 아이디
		String sUserNm = loginVO.getUserName(); // 이름
		String sEnfsnNo = loginVO.getEnfsnNo(); // 종사자번호
		Integer sInstNo = loginVO.getInstNo(); // 기관번호
		
		Map<String, String> instMap = new HashMap<String, String>();
		instMap.put("INST_NO", Integer.toString(sInstNo));
		String sWrdTelno = rsvtMngMapper.selectRprsTelno(instMap);
		
		for (Map<String, String> map : insertRow) {

			rsvtSms2Mapper.insertMmsContentsInfo(map);

			map.put("CONT_SEQ", map.get("CONT_SEQ")); // MMS 컨텐츠 키
			map.put("FRST_RGTR_ID", sUserId);
			map.put("LAST_MDFR_ID", sUserId);
			map.put("DSPTCH_TRPR_NM_ENCPT", sUserNm); // 발신대상자명암호화
			map.put("CALL_FROM", sWrdTelno.replace("-", "")); // 발신휴대전화번호
			map.put("PIC_NO", sEnfsnNo); // 담당자번호
			map.put("TRNSMI_INST_NO", String.valueOf(sInstNo)); // 송신기관번호
			map.put("CALL_TO", map.get("CALL_TO").replace("-", ""));
			
			rsvtSms2Mapper.insertMsgData(map);

			map.put("MSG_SEQ", map.get("MSG_SEQ")); // 메세지 고유번호
			map.put("RCPTN_TRPR_NM_ENCPT", map.get("REAL_RCPTN_TRPR_NM"));
			rsvtSmsMapper.insertNtcnSnsSndng(map);
			
		}

	}

}
