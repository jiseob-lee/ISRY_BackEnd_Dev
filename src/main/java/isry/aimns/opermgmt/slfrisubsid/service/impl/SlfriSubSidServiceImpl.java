/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.slfrisubsid.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.aimns.opermgmt.slfrisubsid.mapper.SlfriSubSidMapper;
import isry.aimns.opermgmt.slfrisubsid.service.SlfriSubSidService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

/**
 * @파일명 : SlfriSubSidServiceImpl.java
 * @프로그램 설명 : 자립장려금 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 24.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 24.
 * @수정내용 : - -
 */
@Service("slfriSubSidService")
public class SlfriSubSidServiceImpl implements SlfriSubSidService {

	// 자립장려금 매퍼
	@Resource(name = "slfriSubSidMapper")
	private SlfriSubSidMapper slfriSubSidMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectSlfrisubSidStatusList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 1.
	 * @Method설명 : 자립지원금현황 목록조회
	 */
	@Override
	public List<Map<String, String>> selectSlfrisubSidStatusList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		dmSearch.forEach((StrKey, StrValue) -> { paramMap.put(StrKey, StrValue); });
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		List<Map<String, String>> returnMap = slfriSubSidMapper.selectSlfrisubSidStatusList(paramMap);

		for (Map<String, String> map : returnMap) {
			map.put("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));

			String bgngYmd = (String) map.get("PVSN_BGNG_YMD");
			String endYmd = (String) map.get("PVSN_END_YMD");
			String traingPrd = bgngYmd.substring(0, 4) + "-" + bgngYmd.substring(4, 6) + "-" + bgngYmd.substring(6, 8)
					+ "~" + endYmd.substring(0, 4) + "-" + endYmd.substring(4, 6) + "-" + endYmd.substring(6, 8);
			map.put("TRAING_PRD", traingPrd);
		}

		return returnMap;
	}

	/**
	 * @Method명 : selectSlfriSubSidList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 27.
	 * @Method설명 : 자립장려금 목록조회
	 */
	@Override
	public List<Map<String, String>> selectSlfriSubSidList(DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> inMap = dmSearch.getAllRowList().get(0);

		List<Map<String, String>> returnMap = slfriSubSidMapper.selectSlfriSubSidList(inMap);

		return returnMap;
	}

	/**
	 * @Method명 : selectSlfriSubSidInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 28.
	 * @Method설명 : 자립장려금 상세조회
	 */
	@Override
	public List<Map<String, String>> selectSlfriSubSidInfo(DataRequest dataRequest) throws Exception {

		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> inMap = dmDtlParam.getAllRowList().get(0);

		List<Map<String, String>> returnMap = slfriSubSidMapper.selectSlfriSubSidInfo(inMap);

		return returnMap;
	}

	/**
	 * @Method명 : saveSlfriSubSid
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 29.
	 * @Method설명 : 자립장려금 데이터 수정/삭제
	 */
	@Override
	public Map<String, Object> saveSlfriSubSid(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		String today = dataRequest.getParameter("Today");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		for (Map<String, String> map : insertedRowList) {
			map.put("FRST_RGTR_ID", userId);
			map.put("LAST_MDFR_ID", userId);
			map.put("APLY_PIC_NO", loginVO.getEnfsnNo());
			map.put("APLY_DT", today);

			slfriSubSidMapper.insertSlfriSubSid(map);
			slfriSubSidMapper.insertSlfriSubSidHstr(map);

			returnMap.put("SLFRL_SUBSID_NO", map.get("SLFRL_SUBSID_NO"));
		}

		for (Map<String, String> map : updatedRowList) {
			map.put("LAST_MDFR_ID", userId);
			map.put("APRV_PIC_NO", loginVO.getEnfsnNo());

			slfriSubSidMapper.updateSlfriSubSid(map);
			slfriSubSidMapper.insertSlfriSubSidHstr(map);
		}

		for (Map<String, String> map : deletedRowList) {
			map.put("LAST_MDFR_ID", userId);

			slfriSubSidMapper.deleteSlfriSubSid(map);
			slfriSubSidMapper.insertSlfriSubSidHstr(map);
		}
		return returnMap;
	}

	/**
	 * @Method명 : selectSlfriSubSidCheck
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 : 자립장려금 신청/승인중인지 여부 확인
	 */
	@Override
	public Map<String, Object> selectSlfriSubSidCheck(DataRequest dataRequest) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, String> dmDtlParam = dataRequest.getParameterGroup("dmDtlParam").getSingleValueMap();

		String aprvSttsSeCd = slfriSubSidMapper.selectSlfriSubSidCheck(dmDtlParam);

		if (aprvSttsSeCd == null)
			returnMap.put("msg", "");
		else if (aprvSttsSeCd.equals("2"))
			returnMap.put("msg", "이미 승인된 자립장려금입니다.");
		else
			returnMap.put("msg", "신청중인 자립장려금이 있습니다.");

		return returnMap;
	}
}
