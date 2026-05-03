/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.aimns.casemng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.aimns.casemng.mapper.AimnsCaseMngMapper;
import isry.aimns.casemng.service.AimnsCaseMngService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

/**
 * @파일명 : AimnsCaseMngServiceImpl.java
 * @프로그램 설명 : 사례관리>실행&종결 화면의 고유항목 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 12.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 12.
 * @수정내용 : - -
 */
@Service("aimnsCaseMngService")
public class AimnsCaseMngServiceImpl implements AimnsCaseMngService {

	@Resource(name = "aimnsCaseMngMapper")
	private AimnsCaseMngMapper aimnsCaseMngMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @Method명 : selectEduComplSchdl
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 교육이수일정 조회
	 */
	@Override
	public void selectEduComplSchdl(DataRequest dataRequest) throws Exception {
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		List<Map<String, Object>> dsEduComplSchdl = aimnsCaseMngMapper.selectEduComplSchdl(dmSearch);

		dataRequest.setResponse("dsEduComplSchdl", dsEduComplSchdl);
	}

	/**
	 * @Method명 : saveEduComplSchdl
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 교육이수일정 등록/수정
	 */
	@Override
	public void saveEduComplSchdl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup dsEduComplSchdl = dataRequest.getParameterGroup("dsEduComplSchdl");

		List<Map<String, String>> insertedRowList = dsEduComplSchdl.getInsertedRowList();
		List<Map<String, String>> updatedRowList = dsEduComplSchdl.getUpdatedRowList();

		for (Map<String, String> map : insertedRowList) {
			if (map.get("BGNG_YMD") != null) {

				map.put("FRST_RGTR_ID", userVo.getId());
				map.put("LAST_MDFR_ID", userVo.getId());

				aimnsCaseMngMapper.insertEduComplSchdl(map);
			}
		}
		for (Map<String, String> map : updatedRowList) {
			map.put("LAST_MDFR_ID", userVo.getId());

			aimnsCaseMngMapper.updateEduComplSchdl(map);
		}
	}

	/**
	 * @Method명 : selectPvsnResrceNm
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 : 교육이수일정관리 과정명 조회
	 */
	@Override
	public Map<String, Object> selectPvsnResrceNm(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		String trprNm = null;
		String picNm = null;
		String taskwkSeCd = null;
		String usrId = null;

		if (parameterGroup != null) {
			trprNm = parameterGroup.getValue("TRPR_NM_ENCPT"); // 대상자성명
			picNm = parameterGroup.getValue("PIC_NM_ENCPT"); // 담당자성명
			taskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD").replaceAll(",", ""); // 단위업무구분코드
			usrId = parameterGroup.getValue("USER_ID"); // 접수담당자

		}

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		if (trprNm != null)
			paramMap.put("TRPR_NM_ENCPT", trprNm);
		if (picNm != null)
			paramMap.put("PIC_NM_ENCPT", picNm);
		if (taskwkSeCd != null)
			paramMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		if (usrId != null)
			paramMap.put("USER_ID", usrId);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */

		String cnt = aimnsCaseMngMapper.selectInqCntList(paramMap2);
		paramMap2.put("TOT_CNT", cnt);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt = (cnt == null || cnt.trim().isEmpty()) ? 0 : Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		// 쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;

		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);

		rtnMap = aimnsCaseMngMapper.selectPvsnResrceNm(paramMap2);

		// 심사담당자명 복호화(2022.06.21 적용)처리
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < rtnMap.toArray().length; i++) {
			map = rtnMap.get(i);

			if (map.get("TRPR_NM_ENCPT") != null)
				map.put("TRPR_NM_ENCPT", Masking.nameMasking(map.get("TRPR_NM_ENCPT").toString()));
//					if (map.get("TRPR_BRTH_YMD") != null) map.put("TRPR_BRTH_YMD", mask.birthMasking( map.get("TRPR_BRTH_YMD").toString() ) );
			if (map.get("TRPR_BRTH_YMD") != null)
				map.put("TRPR_BRTH_YMD", Masking.birthMaskingDay(map.get("TRPR_BRTH_YMD").toString()));
			if (map.get("PIC_NM_ENCPT") != null)
				map.put("PIC_NM_ENCPT", Masking.nameMasking(map.get("PIC_NM_ENCPT").toString()));
			rtnMap.set(i, map);
		}

		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsCaseInqList", rtnMap);
		result.put("dmPage", resPage);

		return result;
	}

	/**
	 * @Method명 : saveEduCmplSchdlMng
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 : 교육이수일정관리 save
	 */
	@Override
	public void saveEduCmplSchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dmGroup = dataRequest.getParameterGroup("dmSearch");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> map = new HashMap<String, Object>();

		map.putAll(dmGroup.getSingleValueMap());
		map.put("USER_ID", loginVO.getId());

		aimnsCaseMngMapper.saveEduCmplSchdlMng(map);

	}

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
		String cnt = aimnsCaseMngMapper.caseinqListCount(paramMap2);
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
			rtnMap = aimnsCaseMngMapper.selectCaseinqList(paramMap2);
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
