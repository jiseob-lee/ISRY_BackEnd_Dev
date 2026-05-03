/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.stats.entrncpreconunityreprts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.csems.stats.entrncpreconunityreprts.mapper.EntrncPreconUnityReprtsMapper;
import isry.csems.stats.entrncpreconunityreprts.service.EntrncPreconUnityReprtsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : EntrncPreconUnityReprtsServiceImpl.java
 * @프로그램 설명 : 입소현황통합보고서 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 3. 2.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 3. 2.
 * @수정내용 : - -
 */
@Service("entrncPreconUnityReprtsService")
public class EntrncPreconUnityReprtsServiceImpl implements EntrncPreconUnityReprtsService {

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 입소현황통합보고서 관련 매퍼
	@Resource(name = "entrncPreconUnityReprtsMapper")
	private EntrncPreconUnityReprtsMapper entrncPreconUnityReprtsMapper;

	/**
	 * @Method명 : selectEntrncPreconUnityReprts
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 입소현황통합보고서 조회
	 */
	@Override
	public void selectEntrncPreconUnityReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> reqMap = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		reqMap.forEach((StrKey, StrValue) -> {
			paramMap.put(StrKey, StrValue);
		});

		// 서비스실행사업연도 배열
		String biz[] = reqMap.get("BIZ_YR").split(",");
		paramMap.put("BIZ_YR", biz);

		// 기관 배열
		String inst[] = reqMap.get("INST_NO").split(",");
		if (inst[0].equals("ALL")) {
			paramMap.put("INST_NO", "ALL");
		} else {
			paramMap.put("INST_NO", inst);
		}

		// 서비스실행사업 배열
		String srvc[] = reqMap.get("SRVC_EXCN_BIZ_NO").split(",");
		paramMap.put("SRVC_EXCN_BIZ_NO", srvc);

		// 단위업무구분코드
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		String uri = request.getRequestURI();
		String[] arr = uri.split("/");
		uri = arr[arr.length - 1];

		if (uri.equals("selectEntrncPreconUnityReprts.do")) {

			/* 입소생현황 통합집계 */
			dataRequest.setResponse("dsEtrstdPreconUnityTot",
					entrncPreconUnityReprtsMapper.selectEntrncPreconUnityReprts(paramMap));
			/* 신청기관별 집계 */
			dataRequest.setResponse("dsAplyInstTot", entrncPreconUnityReprtsMapper.selectAplyInstReprts(paramMap));

		} else if (uri.equals("selectTotTrprList.do")) {

			/* 집계대상자목록 */
			List<Map<String, String>> retMap = new ArrayList<Map<String, String>>();
			retMap = entrncPreconUnityReprtsMapper.selectTotTrprList(paramMap);
			
			dataRequest.setResponse("dsTotList", retMap);
		}
	}
}
