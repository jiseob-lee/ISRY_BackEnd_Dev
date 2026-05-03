/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operrpt.service.impl;

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
import isry.subms.preconmng.operrpt.mapper.OperRptMapper;
import isry.subms.preconmng.operrpt.service.OperRptService;

/**
 * @파일명 : OperRptServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
@Service("operRptService")
public class OperRptServiceImpl implements OperRptService {

	@Resource(name = "operRptMapper")
	private OperRptMapper operRptMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectOperRptList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 운영보고 리스트를 불러옴
	 */
	@Override
	public List<Map<String, String>> selectOperRptList(DataRequest dataRequest) throws Exception {

		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearchParam");

		Map<String, String> mapParam = paraGroup.getSingleValueMap();

		return operRptMapper.selectOperRptList(mapParam);
	}

	/**
	 * @Method명 : selectOperRptDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 8.
	 * @Method설명 : 운영보고 상세페이지 조회
	 */
	@Override
	public List<Map<String, String>> selectOperRpt(DataRequest dataRequest) throws Exception {

		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, String> mapParam = paraGroup.getSingleValueMap();

		return operRptMapper.selectOperRpt(mapParam);
	}

	/**
	 * @Method명 : saveOperRpt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 9.
	 * @Method설명 : 업무보고테이블 삽입/수정/삭제
	 */
	@Override
	public Map<String, Object> saveOperRpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String userInst = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			userInst = loginVO.getInstNo().toString();
		}

		for (Map<String, String> map : insertedRowList) {
			map.put("FRST_RGTR_ID", userId);
			map.put("LAST_MDFR_ID", userId);
			map.put("APLY_PIC_NO", loginVO.getEnfsnNo());
			map.put("INST_NO", userInst);
			operRptMapper.insertOperRpt(map);

			returnMap.put("RESRCE_NO", map.get("RESRCE_NO"));
			returnMap.put("RPT_SE_CD", map.get("RPT_SE_CD"));
			returnMap.put("TASKWK_RPT_SN", map.get("TASKWK_RPT_SN"));
		}

		for (Map<String, String> map : updatedRowList) {
			map.put("LAST_MDFR_ID", userId);
			map.put("APRV_PIC_NO", loginVO.getEnfsnNo());
			operRptMapper.updateOperRpt(map);
		}

		for (Map<String, String> map : deletedRowList) {
			map.put("LAST_MDFR_ID", userId);
			operRptMapper.deleteOperRpt(map);
		}

		return returnMap;
	}
}
