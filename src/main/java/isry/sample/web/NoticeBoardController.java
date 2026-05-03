/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.web;

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

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.sample.service.NoticeBoardService;

/**
 * 
 * @파일명 : NoticeBoardController.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2021. 12. 29.
 * @수정자 : Song.Young.Il
 * @수정일 : 2021. 12. 29.
 * @수정내용 : - 유연한 구조의 게시판을 개발 하기 위한 프로토 타입의 게시판입니다. -
 */
@Controller
@Api(value = "Notice Board Controller")
@RequestMapping("/noticeboard")
public class NoticeBoardController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "msg")
	protected EgovMessageSource msg;

	@Resource(name = "prop")
	protected EgovProperties prop;

	@Autowired
	private NoticeBoardService noticeBoardService;

	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : onLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 :
	 */
	@RequestMapping("/onLoadNoticeBoard.do")
	public View onLoadNoticeBoard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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

	/**
	 * @Method명 : list
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 :
	 */
	@RequestMapping("/listNoticeBoard.do")
	public View listNoticeBoard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");

		mapParam.put("NTABRD_ESNTAL_NO", searchParam.getValue("strBoardId"));
		mapParam.put("SEARCH_KEY", searchParam.getValue("strSearchKey"));
		mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
		mapParam.put("START_DATE", searchParam.getValue("strStartDate"));
		mapParam.put("END_DATE", searchParam.getValue("strEndDate"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);

		// 조회된 전체 데이터 갯수를 가져옵니다.
		totalCount = noticeBoardService.getTotalCount(mapParam);

		// 게시판 기본 데이터 호출
		List<Map<String, Object>> listNoticeBoard = noticeBoardService.selectNoticeBoardList(mapParam);

		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsBoardList", listNoticeBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();

	}

	/**
	 * @Method명 : list
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 :
	 */
	@RequestMapping("/listNoticeBoardDtl.do")
	public View listNoticeBoardDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmDtlParam");

		mapParam.put("NTABRD_ESNTAL_NO", dtlParam.getValue("NTABRD_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", dtlParam.getValue("BBSCTT_ESNTAL_NO"));
		
		// 조회수 추가
		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> listNoticeBoardDtl = noticeBoardService.selectNoticeBoardDtlList(mapParam);

		dataRequest.setResponse("dsBoardList", listNoticeBoardDtl);

		return new JSONDataView();

	}

	@RequestMapping("/saveNoticeBoard.do")
	public View saveNoticeBoard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = noticeBoardService.saveNoticeBoardList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
}
