/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.wiktaskwkrpt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.preconmng.wiktaskwkrpt.mapper.WikTaskwkRptMapper;
import isry.subms.preconmng.wiktaskwkrpt.service.WikTaskwkRptService;

/**
 * @파일명 : WikTaskWorkRptServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 6. 10.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 6. 10.
 * @수정내용 : - -
 */
@Service("wikTaskwkRptService")
public class WikTaskwkRptServiceImpl implements WikTaskwkRptService {

	@Resource(name = "wikTaskwkRptMapper")
	private WikTaskwkRptMapper wikTaskwkRptMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectWikTaskwkList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 10.
	 * @Method설명 : 주간업무보고 리스트 조회
	 */
	@Override
	public List<Map<String, String>> selectWikTaskwkList(DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> mapParam = parameterGroup.getSingleValueMap();

		return wikTaskwkRptMapper.selectWikTaskwkList(mapParam);
	}

	/**
	 * @Method명 : saveWikTaskwkRpt
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 주간업무보고 등록/수정/삭제
	 */
	@Override
	public Map<String, Object> saveWikTaskwkRpt(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsWikOperRpt");

		List<Map<String, String>> insertedRowList = parameterGroup.getInsertedRowList();
		List<Map<String, String>> updatedRowList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = parameterGroup.getDeletedRowList();

		Map<String, Object> returnMap = new HashMap<String, Object>();

		UserDetailsVO loginUser = null;
		try {
			loginUser = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (loginUser != null && loginUser.getId() != null && !loginUser.getId().equals("")) {
			if (!deletedRowList.isEmpty()) {
				for (Map<String, String> map : deletedRowList) {
					map.put("LAST_MDFR_ID", loginUser.getId());
					wikTaskwkRptMapper.deleteWikTaskwkRpt(map);
				}
			}

			if (!insertedRowList.isEmpty()) {
				for (Map<String, String> map : insertedRowList) {
					map.put("RPT_SE_CD", "01");
					map.put("INST_NO", Integer.toString(loginUser.getInstNo()));
					map.put("FRST_RGTR_ID", loginUser.getId());
					map.put("LAST_MDFR_ID", loginUser.getId());
					map.put("APLY_PIC_NO", loginUser.getEnfsnNo());

					wikTaskwkRptMapper.insertWikTaskwkRpt(map);

					returnMap.put("SRVC_EXCN_BIZ_NO", map.get("SRVC_EXCN_BIZ_NO"));
					// 자원번호 조회하는 매퍼한번 태워서 조회하여 입력되었었는데 이제 그냥 파라미터로 넘어오니 바로 파라미터 넣어줬습니다.
					returnMap.put("RESRCE_NO", map.get("RESRCE_NO"));
					returnMap.put("INST_NO", map.get("INST_NO"));
					returnMap.put("RPT_SE_CD", map.get("RPT_SE_CD"));
					returnMap.put("TASKWK_RPT_SN", map.get("TASKWK_RPT_SN"));
					returnMap.put("PRD_BGNG_YMD", map.get("PRD_BGNG_YMD"));
					returnMap.put("PRD_END_YMD", map.get("PRD_END_YMD"));
				}
			}

			if (!updatedRowList.isEmpty()) {
				for (Map<String, String> map : updatedRowList) {
					map.put("RPT_SE_CD", "01");
					map.put("INST_NO", Integer.toString(loginUser.getInstNo()));
					map.put("LAST_MDFR_ID", loginUser.getId());

					wikTaskwkRptMapper.updateWikTaskwkRpt(map);

					returnMap.put("SRVC_EXCN_BIZ_NO", map.get("SRVC_EXCN_BIZ_NO"));
					returnMap.put("RESRCE_NO", map.get("RESRCE_NO"));
					returnMap.put("INST_NO", map.get("INST_NO"));
					returnMap.put("RPT_SE_CD", map.get("RPT_SE_CD"));
					returnMap.put("TASKWK_RPT_SN", map.get("TASKWK_RPT_SN"));
					returnMap.put("PRD_BGNG_YMD", map.get("PRD_BGNG_YMD"));
					returnMap.put("PRD_END_YMD", map.get("PRD_END_YMD"));
				}
			}
		}
		return returnMap;
	}

	/**
	 * @Method명 : selectWikTaskwk
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 주간업무보고 상세조회
	 */
	@Override
	public Map<String, List<Map<String, Object>>> selectWikTaskwk(HttpServletRequest request, DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();

		UserDetailsVO userVo = null;
		try {
			userVo = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		mapParam.put("SRVC_EXCN_BIZ_NO", parameterGroup.getValue("SRVC_EXCN_BIZ_NO"));
		mapParam.put("RESRCE_NO", parameterGroup.getValue("RESRCE_NO"));
		mapParam.put("INST_NO", parameterGroup.getValue("INST_NO"));
		mapParam.put("RPT_SE_CD", parameterGroup.getValue("RPT_SE_CD"));
		mapParam.put("TASKWK_RPT_SN", parameterGroup.getValue("TASKWK_RPT_SN"));
		mapParam.put("PRD_BGNG_YMD", parameterGroup.getValue("PRD_BGNG_YMD"));
		mapParam.put("PRD_END_YMD", parameterGroup.getValue("PRD_END_YMD"));

		mapList.put("dsWikOperRpt", wikTaskwkRptMapper.selectWikTaskwkInqCnd(mapParam));
		mapList.put("dsWeekMng", wikTaskwkRptMapper.selectWikTaskwkWikOper(mapParam));
		mapList.put("dsPgmInfo", wikTaskwkRptMapper.selectWikTaskwkProgrmPtcptn(mapParam));
		mapList.put("dsLinkData", wikTaskwkRptMapper.selectWikTaskwkLinkPrecon(mapParam));
		return mapList;
	}

	/**
	 * @Method명 : selectWikTaskwkSearch
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 주간업무보고 상세에서 조건으로 검색
	 */
	@Override
	public Map<String, List<Map<String, Object>>> selectWikTaskwkSearch(HttpServletRequest request,
			DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();

		UserDetailsVO userVo = null;
		try {
			userVo = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		mapParam.put("SRVC_EXCN_BIZ_NO", parameterGroup.getValue("SRVC_EXCN_BIZ_NO"));
		mapParam.put("RESRCE_NO", parameterGroup.getValue("RESRCE_NO"));
		mapParam.put("INST_NO", parameterGroup.getValue("INST_NO"));
		mapParam.put("PRD_BGNG_YMD", parameterGroup.getValue("PRD_BGNG_YMD"));
		mapParam.put("PRD_END_YMD", parameterGroup.getValue("PRD_END_YMD"));

		mapList.put("dsWeekMng", wikTaskwkRptMapper.selectWikTaskwkWikOper(mapParam));
		mapList.put("dsPgmInfo", wikTaskwkRptMapper.selectWikTaskwkProgrmPtcptn(mapParam));
		mapList.put("dsLinkData", wikTaskwkRptMapper.selectWikTaskwkLinkPrecon(mapParam));
		return mapList;
	}

}
