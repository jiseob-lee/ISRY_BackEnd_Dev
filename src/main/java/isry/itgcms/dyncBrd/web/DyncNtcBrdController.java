/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.dyncBrd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.itgcms.dyncBrd.service.DyncBrdCmnService;
import isry.itgcms.dyncBrd.service.DyncNtcBrdService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 *
 * @파일명 : DyncNtcBrdController.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2021. 12. 29.
 * @수정자 : You Minsang
 * @수정일 : 2021. 12. 29.
 * @수정내용 : - 유연한 구조의 게시판을 개발 하기 위한 프로토 타입의 게시판입니다. -
 */
@Controller
@Api(value = "Dynamic Notice Board Controller")
@RequestMapping("/dyncNtcBrd")
public class DyncNtcBrdController extends IsryBaseController {

	@Autowired
	private DyncNtcBrdService dyncNtcBrdService;

	@Autowired
	private DyncBrdCmnService dyncBrdCmnService;

	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@RequestMapping("/initDyncNtcBrd.do")
	public View init(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup init = dataRequest.getParameterGroup("dmInit");

		Map<String, String> mapParam = new HashMap<String, String>();

		mapParam.put("NTABRD_ESNTAL_NO", init.getValue("strBoardId"));

		Map<String, Object> mapBoardinfo = dyncBrdCmnService.selectDyncBrdCmnInfoList(mapParam);
		List<Map<String, Object>> listBoardCol  = dyncBrdCmnService.selectDyncBrdCmnColList(mapParam);

		// 공통 코드 조회
		listBoardCol.forEach(x -> {
			String cmmCd = (String) x.get("CMMNS_CD_ID");

			if(!"".equals(cmmCd) && cmmCd != null) {
				String dsNm = "ds" + (String) x.get("NTABRD_COL_ESNTAL_ID") + cmmCd;
				try {
					dataRequest.setResponse(dsNm, mgmtCmmnCodeService.selectCommonCodeUnit(cmmCd, userVo == null ? "" : userVo.getUntTaskwk()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		dataRequest.setResponse("dmBoardInfo", mapBoardinfo);
		dataRequest.setResponse("dsBoardColList", listBoardCol);

		return new JSONDataView();

	}

	@RequestMapping("/onLoadDyncNtcBrd.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> mapDate = new HashMap<String, String>();

		// 현재 일자 조회
		mapDate.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));

		// 게시판 검색조건 코드 조회
		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("BULLETIN_SEARCH_BREAKDOWN", userVo == null ? "" : userVo.getUntTaskwk()));
		dataRequest.setResponse("dmTime", mapDate);

		return new JSONDataView();

	}

	@RequestMapping("/listDyncNtcBrd.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup init = dataRequest.getParameterGroup("dmInit");
		mapParam.put("NTABRD_ESNTAL_NO", init.getValue("strBoardId"));

		// 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");

		String strSearchKey = searchParam.getValue("strSearchKey");
		String strSearchData = searchParam.getValue("strSearchData");

		mapParam.put("SEARCH_KEY", strSearchKey);
		mapParam.put("SEARCH_DATA", strSearchData);
		mapParam.put("START_DATE", searchParam.getValue("strStartDate"));
		mapParam.put("END_DATE", searchParam.getValue("strEndDate"));

		// 추가 컬럼 조회조건
		ParameterGroup addSearchParam = dataRequest.getParameterGroup("dmAddSearchParam");

		List<Map<String, String>> addSearchList = new ArrayList<Map<String, String>>();

		String[] colNames = addSearchParam.getColumnNames();

		for (String name : colNames) {
			Map<String, String> searchMap = new HashMap<String, String>();
			String value = addSearchParam.getValue(name);

			if (value != null && !value.isEmpty()) {
				String[] arrKeyType = name.split("_");
				searchMap.put("KEY", arrKeyType[0]);
				searchMap.put("VALUE", addSearchParam.getValue(name));
				searchMap.put("TYPE", arrKeyType[1]);

				addSearchList.add(searchMap);
			}
		}

		mapParam.put("ADD_ARRAY", addSearchList);

		// 게시판 목록 데이터 호출
		List<Map<String, Object>> listSampleBoard = dyncNtcBrdService.selectSampleBoardList(mapParam);

		dataRequest.setResponse("dsBoardList", listSampleBoard);

		return new JSONDataView();

	}

	@RequestMapping("/deleteDyncNtcBrd.do")
	public View delete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dyncNtcBrdService.deleteMstSampleBoardList(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping("/dtlListDyncNtcBrd.do")
	public View dtlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		mapParam.put("NTABRD_ESNTAL_NO", param.getValue("NTABRD_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CREATE_YN", param.getValue("strCreateYn"));

		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dtlListSampleBoard = dyncBrdCmnService.selectDyncBrdCmnDtlList(mapParam);

		// 게시판 추가 컬럼 데이터 호출
		List<Map<String, Object>> dtlColDataListSampleBoard = dyncBrdCmnService.selectDyncBrdCmnColDataList(mapParam);

		dataRequest.setResponse("dsBoardDtlList", dtlListSampleBoard);
		dataRequest.setResponse("dsBoardDtlColDataList", dtlColDataListSampleBoard);

		return new JSONDataView();
	}


	@RequestMapping("/dtlSaveDyncNtcBrd.do")
	public View save(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = dyncNtcBrdService.saveDyncNtcBrdList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
}
