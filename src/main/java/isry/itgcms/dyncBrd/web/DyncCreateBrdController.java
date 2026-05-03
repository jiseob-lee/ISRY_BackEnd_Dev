/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.dyncBrd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.itgcms.dyncBrd.service.DyncCreateBrdService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.pgmemu.service.InqProgListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


/**
 * 
 * @파일명 : DyncCreateBrdController.java
 * @프로그램 설명 : 게시판 생성 컨트롤러
 * @작성자 : You MinSang
 * @작성일 : 2022. 02. 03.
 * @수정자 : You MinSang
 * @수정일 : 2022. 02. 03.
 * @수정내용 :
 */
@Controller
@Api(value = "Dynamic Create Board Controller")
@RequestMapping("/dyncCreateBrd")
public class DyncCreateBrdController extends IsryBaseController {

	@Autowired
	private DyncCreateBrdService dyncCreateBrdService;

	@Autowired
	private InqProgListService inqProgListService;
	
	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/onLoadDyncCreateBrd.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 업무시스템 구분 코드 + 단위 시스템 메뉴 코드 조회
		dataRequest.setResponse("dsCmbUntTaskwkSeCd", dyncCreateBrdService.selectRootMenuList());
		
		return new JSONDataView();

	}

	@RequestMapping("/listDyncCreateBrd.do")
	public View listCreateBoard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 검색 조건
		Map<String, String> mapParam = new HashMap<String, String>();

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		mapParam.put("NTABRD_NM", param.getValue("NTABRD_NM"));
		mapParam.put("NTABRD_TYPE_SE_CD", param.getValue("NTABRD_TYPE_SE_CD"));
		mapParam.put("UNT_TASKWK_SE_CD", param.getValue("UNT_TASKWK_SE_CD"));

		List<Map<String, Object>> listCreateBoard = dyncCreateBrdService.selectCreateBoardList(mapParam);

		dataRequest.setResponse("dsBoardList", listCreateBoard);

		return new JSONDataView();

	}

	@RequestMapping("/colListDyncCreateBrd.do")
	public View colListCreateBoard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup mapColParam = dataRequest.getParameterGroup("dmColParam");

		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("NTABRD_ESNTAL_NO", mapColParam.getValue("NTABRD_ESNTAL_NO"));

		List<Map<String, Object>> colListCreateBoard = dyncCreateBrdService.selectCreateBoardColList(mapParam);

		List<Map<String, Object>> boardProgramInfo = dyncCreateBrdService.selectBoardProgramInfo(mapParam);

		dataRequest.setResponse("dsEndPoints", boardProgramInfo);

		dataRequest.setResponse("dsBoardColList", colListCreateBoard);

		return new JSONDataView();

	}

	@RequestMapping("/saveDyncCreateBrd.do")
	public View saveColInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = dyncCreateBrdService.saveCreateBoardList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("numNtabrdEsntalNo", returnParam.get("NTABRD_ESNTAL_NO"));
		message.put("strFindRowKey", "NTABRD_ESNTAL_NO == '" + returnParam.get("NTABRD_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);

		return new JSONDataView();
	}

	@RequestMapping("/saveProgram.do")
	public View saveProgram(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		inqProgListService.saveProgram(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : listCmmnsCd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 6. 17.
	 * @Method설명 : 게시판 상세설정시 공토코드 팝업 조회
	 */
	@RequestMapping("/listCmmnsCdPop.do")
	public View listCmmnsCdPop(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup mapSearchParam = dataRequest.getParameterGroup("dmSearchParam");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("CMMNS_CD_ID", mapSearchParam.getValue("strCmmsCdId"));
		mapParam.put("CMMNS_CD_NM", mapSearchParam.getValue("strCmmsCdNm"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		totalCount = dyncCreateBrdService.getCmmnsCdTotalCount(mapParam);

		List<Map<String, Object>> cmmnsCdListCreateBoard = dyncCreateBrdService.selectCreateBoardcmmnsCdList(mapParam);

		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", resPage);	
		dataRequest.setResponse("dsCmmnsCd", cmmnsCdListCreateBoard);	

		return new JSONDataView();
	}
	
	/**
	 * @Method명 : listCmmnsCd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 6. 17.
	 * @Method설명 : 게시판 상세설정시 공토코드 팝업 조회
	 */
	@RequestMapping("/listCmmnsItemCdPop.do")
	public View listCmmnsItemCdPop(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup mapSearchParam = dataRequest.getParameterGroup("dmParam");

		String strCmmnsCdId = mapSearchParam.getValue("CMMNS_CD_ID");
		List<Map<String, Object>> cmmnsItemCdListCreateBoard = mgmtCmmnCodeService.selectCommonCodeUnit(strCmmnsCdId, userVo.getUntTaskwk());
		
		dataRequest.setResponse("dsCmmnsCd", cmmnsItemCdListCreateBoard);	

		return new JSONDataView();
	}
}
