/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.statsmgmt.stats.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.aimns.statsmgmt.stats.mapper.StatsMapper;
import isry.aimns.statsmgmt.stats.service.StatsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : StatsServiceImpl.java
 * @프로그램 설명 : 통계관리 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 7. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 7. 11.
 * @수정내용 : - -
 */
@Service("statsService")
public class StatsServiceImpl implements StatsService {

	// 통계관리 매퍼
	@Resource(name = "statsMapper")
	private StatsMapper statsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectTrprDetailStatusStatsList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 12.
	 * @Method설명 : 교육생세부현황 목록조회
	 */
	@Override
	public List<Map<String, String>> selectTrprDetailStatusStatsList(HttpServletRequest request,
			DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> inMap = dmSearch.getAllRowList().get(0);
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		List<Map<String, String>> returnMap = statsMapper.selectTrprDetailStatusStatsList(inMap);
		for (Map<String, String> map : returnMap) {
			map.replace("TRPR_NM", map.get("TRPR_NM"));
			map.replace("RRNO", map.get("RRNO"));
			map.replace("TELNO", map.get("TELNO"));
			map.replace("EML", map.get("EML"));
			String telNo = String.valueOf(map.get("TELNO"));
			if (telNo.length() == 11)
				map.put("TELNO", telNo.substring(0, 3) + "-" + telNo.substring(3, 7) + "-" + telNo.substring(7, 11));
			String rrno = String.valueOf(map.get("RRNO"));
			if (rrno.length() == 13)
				map.put("RRNO", rrno.substring(0, 6) + "-" + rrno.substring(6, 13));
		}

		return returnMap;
	}

	/**
	 * @Method명 : selectMonthExecStatusStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 22.
	 * @Method설명 : 월별실시현황 목록조회
	 */
	@Override
	public List<Map<String, String>> selectMonthExecStatusStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> inMap = dmSearch.getAllRowList().get(0);
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		if (loginVO.getInstTypeSeCd() != null) {
			inMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		}
		if(loginVO.getInstNo() != null) {
			inMap.put("INST_NO", Integer.toString(loginVO.getInstNo()));
		}
		List<Map<String, String>> returnMap = statsMapper.selectMonthExecStatusStatsList(inMap);

		return returnMap;
	}

	/**
	 * @Method명 : selectRecruitStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 모집통계 목록조회
	 */
	@Override
	public void selectRecruitStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getAllRowList().get(0);

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		if (loginVO.getInstTypeSeCd() != null) {
			paramMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		}
		if(loginVO.getInstNo() != null) {
			paramMap.put("INST_NO", Integer.toString(loginVO.getInstNo()));
		}

		List<Map<String, Object>> returnList = statsMapper.selectRecruitStatsList(paramMap);
		dataRequest.setResponse("dsList", returnList);
	}

	/**
	 * @Method명 : selectProgressStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 진행통계 목록조회
	 */
	@Override
	public void selectProgressStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getAllRowList().get(0);

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		if (loginVO.getInstTypeSeCd() != null) {
			paramMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		}
		if(loginVO.getInstNo() != null) {
			paramMap.put("INST_NO", Integer.toString(loginVO.getInstNo()));
		}

		List<Map<String, Object>> returnList = statsMapper.selectProgressStatsList(paramMap);
		dataRequest.setResponse("dsList", returnList);
	}

	/**
	 * @Method명 : selectTrprNumberOfAgeList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 연령별 인원 현황 목록조회
	 */
	@Override
	public void selectTrprNumberOfAgeList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getAllRowList().get(0);

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		List<Map<String, Object>> returnList = statsMapper.selectTrprNumberOfAgeList(paramMap);
		dataRequest.setResponse("dsList", returnList);
	}

	/**
	 * @Method명 : selectReportStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 통계보고서
	 */
	@Override
	public void selectReportStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getAllRowList().get(0);

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		List<Map<String, Object>> returnList1 = statsMapper.selectReportStatsStudntSlctList(paramMap);
		List<Map<String, Object>> returnList2 = statsMapper.selectReportStatsStudntEduList(paramMap);
		List<Map<String, Object>> returnList3 = statsMapper.selectReportStatsStudntResultList(paramMap);
		dataRequest.setResponse("dsReportStatsStudntSlctList", returnList1);
		dataRequest.setResponse("dsReportStatsStudntEduList", returnList2);
		dataRequest.setResponse("dsReportStatsStudntResultList", returnList3);
	}
}
