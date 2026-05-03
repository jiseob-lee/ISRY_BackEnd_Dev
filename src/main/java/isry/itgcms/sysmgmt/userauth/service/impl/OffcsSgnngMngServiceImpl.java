/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.mapper.OffcsSgnngMngMapper;
import isry.itgcms.sysmgmt.userauth.service.OffcsSgnngMngService;

/**
 * @파일명 : OffcsSgnngMngServiceImpl.java
 * @프로그램 설명 : 직인서명관리 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 8. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 8. 11.
 * @수정내용 : - -
 */
@Service("offcsSgnngMngService")
public class OffcsSgnngMngServiceImpl implements OffcsSgnngMngService {

	@Resource(name = "offcsSgnngMngMapper")
	OffcsSgnngMngMapper offcsSgnngMngMapper;

	/**
	 * @Method명 : selectOffcsSgnngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 11.
	 * @Method설명 : 직인서명 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectOffcsSgnngList(DataRequest dataRequest) throws Exception {
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		Map<String, String> paramMap = dmParam.getAllRowList().get(0);

		List<Map<String, Object>> result = offcsSgnngMngMapper.selectOffcsSgnngList(paramMap);
		Map<String, Object> addMap = new HashMap<String, Object>();
		addMap.put("OFFCS_SGNNG_NO", paramMap.get("OFFCS_SGNNG_NO"));
		addMap.put("OFFCS_SGNNG_SE_CD", paramMap.get("OFFCS_SGNNG_SE_CD"));
		if (!result.isEmpty()) {
			addMap.put("ATFINO", result.get(0).get("ATFINO"));
			addMap.put("ATCMFL_MNG_SN", ((BigDecimal) result.get(0).get("ATCMFL_MNG_SN")).intValue() + 1);
			addMap.put("MNG_SN", ((BigDecimal) result.get(0).get("MNG_SN")).intValue() + 1);
			// addMap.put("ATCMFL_CL_NM", Integer.valueOf((String)
			// result.get(0).get("ATCMFL_CL_NM")) + 1);
		} else {
			addMap.put("MNG_SN", 1);
			addMap.put("ATCMFL_MNG_SN", 1);
			// addMap.put("ATCMFL_CL_NM", 1);
		}
		result.add(0, addMap);

		return result;
	}

	/**
	 * @Method명 : saveOffcsSgnng
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 11.
	 * @Method설명 : 직인서명 저장/수정/삭제
	 */
	@Override
	public Map<String, Object> saveOffcsSgnng(DataRequest dataRequest) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		List<Map<String, String>> insertMap = dsList.getInsertedRowList();
		List<Map<String, String>> deleteMap = dsList.getDeletedRowList();

		Map<String, String> paramMap = dmParam.getAllRowList().get(0);

		for (Map<String, String> map : insertMap) {
			// 승인 처리를 위해 기존 정보 사용여부 변경 안하는 식으로 처리. 
			// 승인에서 교체 되도록 함. 하단 주석.
			// offcsSgnngMngMapper.setOffcsSgnng(map);
			map.put("USE_YN", "N");
			offcsSgnngMngMapper.insertOffcsSgnng(map);

			
			// 요청 여부 체크 해서 없으면 insert 요청건이 있으면 업데이트 처리. 
			if (paramMap.get("OFFCS_SGNNG_NO") == null || "".equals(paramMap.get("OFFCS_SGNNG_NO"))) {
				paramMap.replace("OFFCS_SGNNG_NO", String.valueOf(map.get("OFFCS_SGNNG_NO")));
				offcsSgnngMngMapper.updateOffcsSgnggNoToInst(paramMap);
			}
			map.put("INST_NO", paramMap.get("INST_NO"));
			// 통합기관신청 테이블 INSERT 20230427 Taesoo Song
			offcsSgnngMngMapper.insertAprvData(map);
			returnMap.put("OFFCS_SGNNG_NO", map.get("OFFCS_SGNNG_NO"));
			returnMap.put("MNG_SN", map.get("MNG_SN"));
		}
		for (Map<String, String> map : deleteMap) {
			offcsSgnngMngMapper.deleteOffcsSgnng(map);
			returnMap.put("OFFCS_SGNNG_NO", map.get("OFFCS_SGNNG_NO"));
		}

		return returnMap;
	}

}
