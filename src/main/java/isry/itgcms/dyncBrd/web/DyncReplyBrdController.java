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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.itgcms.dyncBrd.service.DyncBrdCmnService;
import isry.itgcms.dyncBrd.service.DyncReplyBrdService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * 
 * @파일명 : DyncReplyBrdController.java
 * @프로그램 설명 : - -
 * @작성자 : You MinSang
 * @작성일 : 2022. 06. 15.
 * @수정자 : You MinSang
 * @수정일 : 2022. 06. 15.
 * @수정내용 : - 유연한 구조의 게시판을 개발 하기 위한 프로토 타입의 게시판입니다. -
 */
@Controller
@Api(value = "Dynamic Reply Board Controller")
@RequestMapping("/dyncReplyBrd")
public class DyncReplyBrdController {
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DyncReplyBrdService dyncReplyBrdService;
	
	@Autowired
	private DyncBrdCmnService dyncBrdCmnService;
	
	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/initDyncReplyBrd.do")
	public View init(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup init = dataRequest.getParameterGroup("dmInit");

		Map<String, String> mapParam = new HashMap<String, String>();

		mapParam.put("NTABRD_ESNTAL_NO", init.getValue("strBoardId"));
		
		Map<String, Object> mapDynamicReplyBoardinfo = dyncBrdCmnService.selectDyncBrdCmnInfoList(mapParam);
		List<Map<String, Object>> listDynamicReplyBoardCol  = dyncBrdCmnService.selectDyncBrdCmnColList(mapParam);
		
		// 공통 코드 조회
		listDynamicReplyBoardCol.forEach(x -> {
			String cmmCd = (String) x.get("CMMNS_CD_ID");
			
			if(!"".equals(cmmCd) && cmmCd != null) {				
				String dsNm = "ds" + (String) x.get("NTABRD_COL_ESNTAL_ID") + cmmCd;
				try {
					dataRequest.setResponse(dsNm, mgmtCmmnCodeService.selectCommonCodeUnit(cmmCd, userVo.getUntTaskwk()));
				} catch (Exception e) {					
					e.printStackTrace();
				}
			}
		});
		dataRequest.setResponse("dmBoardInfo", mapDynamicReplyBoardinfo);
		dataRequest.setResponse("dsBoardColList", listDynamicReplyBoardCol);
		dataRequest.setResponse("dsBoardColList2", listDynamicReplyBoardCol);

		return new JSONDataView();

	}

	@RequestMapping("/onLoadDyncReplyBrd.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, String> mapDate = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		// 현재 일자 조회
		mapDate.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		
		// 게시판 검색조건 코드 조회
		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("BULLETIN_SEARCH_BREAKDOWN", userVo.getUntTaskwk()));
		dataRequest.setResponse("dmTime", mapDate);

		return new JSONDataView();

	}

	@RequestMapping("/listDyncReplyBrd.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup init = dataRequest.getParameterGroup("dmInit");
		mapParam.put("NTABRD_ESNTAL_NO", init.getValue("strBoardId"));

		// 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");

		mapParam.put("SEARCH_KEY", searchParam.getValue("strSearchKey"));
		mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
		mapParam.put("START_DATE", searchParam.getValue("strStartDate"));
		mapParam.put("END_DATE", searchParam.getValue("strEndDate"));
		mapParam.put("REPLY_YN", searchParam.getValue("replyYn"));
		
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
		List<Map<String, Object>> listDynamicReplyBoard = dyncReplyBrdService.selectDynamicReplyBoardList(mapParam);

		dataRequest.setResponse("dsBoardList", listDynamicReplyBoard);

		return new JSONDataView();

	}

	@RequestMapping("/listDyncReplyBrdExcel.do")
	public View listExcel(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup init = dataRequest.getParameterGroup("dmInit");
		mapParam.put("NTABRD_ESNTAL_NO", init.getValue("strBoardId"));

		// 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");

		mapParam.put("SEARCH_KEY", searchParam.getValue("strSearchKey"));
		mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
		mapParam.put("START_DATE", searchParam.getValue("strStartDate"));
		mapParam.put("END_DATE", searchParam.getValue("strEndDate"));
		mapParam.put("REPLY_YN", searchParam.getValue("replyYn"));
		
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
	
		mapParam.put("ALL_DATA_EXCEL_DOWN", "Y");		

		// 게시판 목록 데이터 호출
		List<Map<String, Object>> listDynamicReplyBoard = dyncReplyBrdService.selectDynamicReplyBoardList(mapParam);

		dataRequest.setResponse("dsBoardList2", listDynamicReplyBoard);

		return new JSONDataView();

	}

	@RequestMapping("/deleteDyncReplyBrd.do")
	public View delete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dyncReplyBrdService.deleteMstDynamicReplyBoardList(request, dataRequest);

		return new JSONDataView();
	}
	
	@RequestMapping("/dtlListDyncReplyBrd.do")
	public View dtlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		mapParam.put("NTABRD_ESNTAL_NO", param.getValue("NTABRD_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("CREATE_YN", param.getValue("strCreateYn"));
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dtlListDynamicReplyBoard = dyncReplyBrdService.selectDynamicReplyBoardDtlList(mapParam);
		
		// 게시판 추가 컬럼 데이터 호출
		List<Map<String, Object>> dtlColDataListDynamicReplyBoard = dyncBrdCmnService.selectDyncBrdCmnColDataList(mapParam);
		
		// 댓글 데이터 호출
		List<Map<String, Object>> dtlReplyListDynamicReplyBoard = dyncReplyBrdService.selectDynamicReplyBoardReplyList(mapParam);
		
		dataRequest.setResponse("dsBoardDtlList", dtlListDynamicReplyBoard);
		dataRequest.setResponse("dsBoardDtlColDataList", dtlColDataListDynamicReplyBoard);
		dataRequest.setResponse("dsBoardDtlReplyList", dtlReplyListDynamicReplyBoard);
		
		return new JSONDataView();
	}
	
	
	@RequestMapping("/dtlSaveDyncReplyBrd.do")
	public View dtlSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = dyncReplyBrdService.saveDynamicReplyBoardList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		
		if(!"".equals(returnParam.get("RETE_ESNTAL_NO"))) {
			message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
		}		
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
}
