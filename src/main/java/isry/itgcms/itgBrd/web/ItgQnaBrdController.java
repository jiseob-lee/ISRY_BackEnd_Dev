/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.itgBrd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.itgcms.itgBrd.service.ItgBrdCmnCmntService;
import isry.itgcms.itgBrd.service.ItgBrdCmnService;
import isry.itgcms.itgBrd.service.ItgQnaBrdService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : ItgQnaBrdController.java
 * @프로그램 설명 : 통합 질의응답 게시판
 * @작성자 : You Minsang
 * @작성일 : 2022. 7. 15.
 * @수정자 : You Minsang
 * @수정일 : 2022. 7. 15.
 * @수정내용 : - -
 */
@Controller
@Api(value = "integrate QnA Board Controller")
@RequestMapping("/itgQnaBrd")
public class ItgQnaBrdController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "msg")
	protected EgovMessageSource msg;

	@Resource(name = "prop")
	protected EgovProperties prop;

	@Autowired
	private ItgBrdCmnService itgBrdCmnService;

	@Autowired
	private ItgBrdCmnCmntService itgBrdCmnCmntService;

	@Autowired
	private ItgQnaBrdService itgQnaBrdService;

	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@RequestMapping("/initItgQnaBrd.do")
	public View init(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 정보 조회
		ParameterGroup mapInit = dataRequest.getParameterGroup("dmInit");

		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", mapInit.getValue("strBbscttTypeSeCd"));
		mapParam.put("UNT_TASKWK_SE_CD", mapInit.getValue("strTaskwkSeCd"));

		// 업무단위 카테고리 반환
		List<Map<String, Object>> listItgNtcCtgrybSeCd = itgBrdCmnService.selectCtgrySeCdList(mapParam);

		dataRequest.setResponse("dsCmbCtgrybSeCd", listItgNtcCtgrybSeCd);

		return new JSONDataView();

	}

	@RequestMapping("/onLoadItgQnaBrd.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, String> mapDate = new HashMap<String, String>();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		// 현재 일자 조회
		mapDate.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		dataRequest.setResponse("dmTime", mapDate);

		// 업무처리구분코드 콤보 조회
		dataRequest.setResponse("dsCmbTaskwkPrcsSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("TASKWK_PRCS_SE_CD", userVo.getUntTaskwk()));

		// 업무처리팀구분코드 콤보 조회
		dataRequest.setResponse("dsCmbTaskwkTaskfoSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("TASKWK_TASKFO_SE_CD", userVo.getUntTaskwk()));

		// 게시판 검색 콤보 조회
		dataRequest.setResponse("dsCmbSearchSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("BULLETIN_SEARCH_BREAKDOWN", userVo.getUntTaskwk()));

		// 업무시스템 구분 코드 조회
		dataRequest.setResponse("dsCmbTaskSysSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("TASKWK_SYS_SE_CD", userVo.getUntTaskwk()));

		// 기관 구분 코드 조회
		//20230713_강화영_최적화 작업
//		List<Map<String, Object>> listInstCode = itgBrdCmnService.selectInstCodeList();

//		dataRequest.setResponse("dsCmbInstCd", listInstCode);

		return new JSONDataView();

	}

	@RequestMapping("/listItgQnaBrd.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시글 조회 정보
		ParameterGroup mapInit = dataRequest.getParameterGroup("dmInit");

		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", mapInit.getValue("strBbscttTypeSeCd"));
		mapParam.put("UNT_TASKWK_SE_CD", mapInit.getValue("strTaskwkSeCd"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);

		// 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");

		String strCtgrybSeCd = searchParam.getValue("strCtgrybSeCd");
		String strSearchKey = searchParam.getValue("strSearchKey");
		String strSearchData = searchParam.getValue("strSearchData");
		String strCllrRegYN = searchParam.getValue("strCllrRegYN");
		String[] strTaskwkPrcsSeCd = null;
		if (searchParam.getValue("strTaskwkPrcsSeCd") != null) {
			strTaskwkPrcsSeCd = searchParam.getValue("strTaskwkPrcsSeCd").split(",");
		}
		String strTaskwkTaskfoSeCd = searchParam.getValue("strTaskwkTaskfoSeCd");

		// 전체, 공통 조회시 카테고리는 빈값으로 검색
		if ("total".equals(strCtgrybSeCd)) {
			strCtgrybSeCd = "";
		}

		mapParam.put("SEARCH_KEY", strSearchKey);
		mapParam.put("SEARCH_DATA", strSearchData);
		mapParam.put("UNITY_BBSCTT_CTGRYB_SE_CD", strCtgrybSeCd);
		mapParam.put("CLLR_REG_YN", strCllrRegYN);
		if (strTaskwkPrcsSeCd != null && strTaskwkPrcsSeCd[0] != null && !"".equals(strTaskwkPrcsSeCd[0])) {
			mapParam.put("TASKWK_PRCS_SE_CD", strTaskwkPrcsSeCd);
		}
		mapParam.put("TASKWK_TASKFO_SE_CD", strTaskwkTaskfoSeCd);

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		totalCount = itgBrdCmnService.getTotalCount(mapParam);

		// 게시판 목록 데이터 조회
		List<Map<String, Object>> listItgNtcBoard = itgQnaBrdService.selectItgQnaBrdList(mapParam);

		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsBoardList", listItgNtcBoard);

		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();

	}

	@RequestMapping("/deleteItgQnaBrd.do")
	public View delete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		itgBrdCmnService.deleteItgNtcBrd(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping("/dtlListItgQnaBrd.do")
	public View dtlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 ID(NTABRD_ESNTAL_NO)
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", param.getValue("strBbscttTypeSeCd"));
		mapParam.put("UNT_TASKWK_SE_CD", param.getValue("strTaskwkSeCd"));
		mapParam.put("UNITY_BBSCTT_ESNTAL_NO", param.getValue("strBbscttEsntalNo"));
		mapParam.put("CREATE_YN", param.getValue("strCreateYn"));

		// 게시글 상세 데이터 조회
		List<Map<String, Object>> dtlListItgBrd = itgBrdCmnService.selectItgBrdDtlList(mapParam);

		// 게시글 출력 시스템 조회
		List<Map<String, Object>> dtlTaskSysCdListItgBrd = itgBrdCmnService.selectItgBrdDtlTaskSysCdList(mapParam);

		// 댓글 데이터 조회
		List<Map<String, Object>> dtlCmntListItgBrd = itgBrdCmnCmntService.selectItgBrdCmntList(mapParam);

		dataRequest.setResponse("dsBoardDtlList", dtlListItgBrd);
		dataRequest.setResponse("dsUseTaskSysSeCd", dtlTaskSysCdListItgBrd);
		dataRequest.setResponse("dsBoardDtlCmntList", dtlCmntListItgBrd);

		return new JSONDataView();
	}

	@RequestMapping("/dtlSaveItgQnaBrd.do")
	public View save(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = itgBrdCmnService.saveItgBrdCmnList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("UNITY_BBSCTT_ESNTAL_NO", returnParam.get("UNITY_BBSCTT_ESNTAL_NO"));

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}

	@RequestMapping("/dtlCmntSaveItgQnaBrd.do")
	public View cmntSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		itgBrdCmnCmntService.saveItgBrdCmntList(request, dataRequest);

		return new JSONDataView();
	}
}
