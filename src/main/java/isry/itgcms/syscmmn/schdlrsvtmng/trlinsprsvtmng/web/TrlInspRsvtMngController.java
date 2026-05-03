/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.service.TrlInspRsvtMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : TrlInspRsvtMngController.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 7. 6.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 7. 6.
 * @수정내용      :
 * -
 * -
 */
@Controller
@RequestMapping(value = "/isry/itgcms/syscmmn/schdlrsvtmng/trlinsprsvtmng")
public class TrlInspRsvtMngController extends IsryBaseController {


	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;


	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;


	@Resource(name = "trlInspRsvtMngService")
	private TrlInspRsvtMngService trlInspRsvtMngService;


	@Resource(name="userLoginService")
	private UserLoginService userLoginService;


	// 테스트용 로그인 사용자 아이디
	//private String testUserId = "SUBMS01";



	/**
	 * @Method명 : onLoadTrlInspRsvtMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadTrlInspRsvtMng.do")
	public View onLoadTrlInspRsvtMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다.
		 */


		Integer authMenuNo = 0;
		authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.


		Map<String, Object> mapDate = new HashMap<String, Object>();

		// 업무구분(해당업무 진입 메뉴 구분) > 변경될 수 있음. 정책 최종 확인 필요.
        // 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04)
		String taskwkSeCd = trlInspRsvtMngService.selectTaskwkSeCd(requestMap);

		mapDate.put("taskwkSeCd", taskwkSeCd);

		// 현재 일자 조회
		mapDate.put("strSysDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));


		log.info("#### strSysDate : " + mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		log.info("#### taskwkSeCd : " + taskwkSeCd);

		dataRequest.setResponse("dmInitInfo", mapDate);


		return new JSONDataView();
	}



	/**
	 * @Method명 : trlInspRsvtMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 13.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/trlInspRsvtMngList.do")
	public View trlInspRsvtMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);

			loginMap.put("USER_ID", loginVO.getId());

		}


		Map<String, Object> mapParam = new HashMap<String, Object>();

		mapParam.put("USER_ID", loginMap.get("USER_ID"));

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
//		log.info("#### strSearchKey              :" + searchParam.getValue("strSearchKey"));

		String searchKey = null;
		searchKey = searchParam.getValue("strSearchKey");
		log.info("#### searchKey              : " + searchKey);
		log.info("#### strSearchData              : " + searchParam.getValue("strSearchData"));
		// 검색조건 입력한 이름 암호화

		if(searchKey.equals("01") || searchKey.equals("02")) {

			log.info("#### searchKey 01:예약자명 / 02:상담자명");

			String encStr = null;
			encStr = searchParam.getValue("strSearchData").toString();

			if (encStr != null || "".equals(encStr)) {


				log.info("#### encStr != null             : " + encStr);
				mapParam.put("SEARCH_DATA", encStr);

			}

		}
		else {
			mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
		}

		mapParam.put("SEARCH_KEY", searchKey);
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
		totalCount = trlInspRsvtMngService.getTrlInspRsvtMngListTotalCount(mapParam);

		// 기본 데이터 호출
		List<Map<String, Object>> trlInspRsvtMngList = trlInspRsvtMngService.selectTrlInspRsvtMngList(mapParam);



		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", trlInspRsvtMngList);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();

	}



	/**
	 * @Method명 : onLoadTrlInspRsvtMngDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadTrlInspRsvtMngDetail.do")
	public View onLoadTrlInspRsvtMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다.
		 */


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);

			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());


		}


		Map<String, Object> mapDate = new HashMap<String, Object>();


		// 사용자 아이디
		mapDate.put("USER_ID", loginMap.get("USER_ID"));

		// 사용자 이름
		mapDate.put("USER_NM", loginMap.get("USER_NM"));

		// 현재 일자 조회
		mapDate.put("strSysDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));


		log.info("#### strSysDate : " + mgmtCmmnCodeService.getSysDate("YYYYMMDD"));

		dataRequest.setResponse("dmInitInfo", mapDate);



		// 심리검사 5개 그룹 대분류, 소분류 공통 코드

		Map<String, String> comCodeMap1 = new HashMap<String, String>();
		comCodeMap1.put("CMMNS_CD_ID", "CMMNS_TRL_INSP_LCLAS_SE_CD");


		// 대분류(동일)
		comCodeMap1.put("unitCode", loginVO.getUntTaskwk());
		dataRequest.setResponse("dsTrlInspLclasSeCDdGroup", comCodeService.selectCommonCodeUnit(comCodeMap1));


		Map<String, String> comCodeMap2 = new HashMap<String, String>();
		comCodeMap2.put("CMMNS_CD_ID", "CMMNS_TRL_INSP_SCLAS_SE_CD");

		// 소분류(동일)
		comCodeMap1.put("comCodeMap2", loginVO.getUntTaskwk());
		dataRequest.setResponse("dsTrlInspSclasSeCDdGroup", comCodeService.selectCommonCodeUnit(comCodeMap2));


		return new JSONDataView();
	}



	/**
	 * @Method명 : trlInspRsvtMngDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/trlInspRsvtMngDetail.do")
	public View trlInspRsvtMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		trlInspRsvtMngService.trlInspRsvtMngDetail(request, dataRequest);

		return new JSONDataView();
	}



	/**
	 * @Method명 : selectTrlInspRsvtMngDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectTrlInspRsvtMngDetail.do")
	public View selectTrlInspRsvtMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {


		// 상세처리
		List<Map<String, Object>> selectTrlInspRsvtMngDetail = trlInspRsvtMngService.selectTrlInspRsvtMngDetail(request, dataRequest);
		dataRequest.setResponse("dsList", selectTrlInspRsvtMngDetail);


		// 심리검사 선택 그룹 항목 리스트 가져오기
		List<Map<String, Object>> selectChcTrlInsp = trlInspRsvtMngService.selectChcTrlInsp(dataRequest);
		dataRequest.setResponse("dsTrlInspList", selectChcTrlInsp);

		return new JSONDataView();
	}








	/**
	 * @Method명 : onLoadTrlInspRsvtMngDaily
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadTrlInspRsvtMngDaily.do")
	public View onLoadTrlInspRsvtMngDaily(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다.
		 */
        UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, Object> mapDate = new HashMap<String, Object>();



		// 현재 일자 조회
		mapDate.put("searchDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));


		log.info("#### searchDate : " + mgmtCmmnCodeService.getSysDate("YYYYMMDD"));

		dataRequest.setResponse("dmSearchParam", mapDate);


		// 심리검사 5개 그룹 대분류, 소분류 공통 코드

		Map<String, String> comCodeMap1 = new HashMap<String, String>();
		comCodeMap1.put("CMMNS_CD_ID", "CMMNS_TRL_INSP_LCLAS_SE_CD");

		// 대분류(동일)
		comCodeMap1.put("unitCode", userVo.getUntTaskwk());
		dataRequest.setResponse("dsTrlInspLclasSeCDdGroup", comCodeService.selectCommonCodeUnit(comCodeMap1));


		Map<String, String> comCodeMap2 = new HashMap<String, String>();
		comCodeMap2.put("CMMNS_CD_ID", "CMMNS_TRL_INSP_SCLAS_SE_CD");

		// 소분류(동일)
		comCodeMap2.put("unitCode", userVo.getUntTaskwk());
		dataRequest.setResponse("dsTrlInspSclasSeCDdGroup", comCodeService.selectCommonCodeUnit(comCodeMap2));


		return new JSONDataView();
	}




	/**
	 * @Method명 : trlInspRsvtMngDailyList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 13.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/trlInspRsvtMngDailyList.do")
	public View trlInspRsvtMngDailyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);

			loginMap.put("USER_ID", loginVO.getId());

		}


		Map<String, Object> mapParam = new HashMap<String, Object>();

		mapParam.put("USER_ID", loginMap.get("USER_ID"));

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
//		log.info("#### strSearchKey              :" + searchParam.getValue("strSearchKey"));

		String searchDate = null;
		searchDate = searchParam.getValue("searchDate");
		log.info("#### searchDate              : " + searchDate);

		mapParam.put("SEARCH_DATE", searchDate);

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
		totalCount = trlInspRsvtMngService.getTrlInspRsvtMngDailyListTotalCount(mapParam);

		// 기본 데이터 호출
		List<Map<String, Object>> dailyList = trlInspRsvtMngService.selectTrlInspRsvtMngDailyList(mapParam);



		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", dailyList);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();

	}



	/**
	 * @Method명 : selectChcTrlInsp
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectChcTrlInsp.do")
	public View selectChcTrlInsp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 심리검사 선택 그룹 항목 리스트 가져오기
		List<Map<String, Object>> selectChcTrlInsp = trlInspRsvtMngService.selectChcTrlInsp(dataRequest);
		dataRequest.setResponse("dsTrlInspList", selectChcTrlInsp);

		return new JSONDataView();
	}



}
