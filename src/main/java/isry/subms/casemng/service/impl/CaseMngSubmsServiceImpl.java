/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.subms.casemng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.casemng.mapper.CaseMngSubmsMapper;
import isry.subms.casemng.service.CaseMngSubmsService;

/**
 * @파일명 : CaseMngSubmsServiceImpl.java
 * @프로그램 설명 : 이주배경 사례관리 관련 Service Implement - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 8. 7.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 8. 7.
 * @수정내용 : - -
 */
@Service("caseMngSubmsService")
public class CaseMngSubmsServiceImpl implements CaseMngSubmsService {

	@Resource(name = "caseMngSubmsMapper")
	private CaseMngSubmsMapper caseMngSubmsMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @Method명 : selectCaseinqPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록 조회
	 */
	@Override
	public Map<String, Object> selectCaseinqPagingList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = new HashMap<>();

		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		paramMap.put("USER_ID", loginVO.getId());

		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>(paramMap);

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		String cnt = caseMngSubmsMapper.caseinqListCount(paramMap2);
		paramMap2.put("TOT_CNT", cnt);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt = (cnt == null || cnt.trim().isEmpty()) ? 0 : Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		// 쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if (totCnt > 0) {
			// Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			rtnMap = caseMngSubmsMapper.selectCaseinqList(paramMap2);
		}

		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsCaseInqList", rtnMap);
		result.put("dmPage", resPage);
		return result;
	}
}
