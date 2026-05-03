/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.extrnltaskwk.extrnlsprtactvt.web;

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
import isry.itgcms.extrnltaskwk.extrnlsprtactvt.service.ExtrnlSprtActvtService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : ExtrnlSprtActvtController.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 6. 15.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 6. 15.
 * @수정내용      :
 * -
 * -
 */
@Controller
@RequestMapping(value = "/isry/itgcms/extrnltaskwk/extrnlsprtactvt")
public class ExtrnlSprtActvtController extends IsryBaseController {


	protected Logger log = LoggerFactory.getLogger(this.getClass());


	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "extrnlSprtActvtService")
	private ExtrnlSprtActvtService extrnlSprtActvtService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	// 테스트용 로그인 사용자 아이디
	//private String testUserId = "SUBMS01";


	/**
	 * @Method명 : onLoadExtrnlSprtActvt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 6. 15.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadExtrnlSprtActvt.do")
	public View onLoadExtrnlSprtActvt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//		Map<String, Object> loginMap = new HashMap<>();
//		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
////			loginMap.put("USER_ID", loginVO.getId());
////			loginMap.put("USER_NM", loginVO.getUserName());
//
//			loginMap.put("UntTaskwk", loginVO.getUntTaskwk());
//			loginMap.put("UntSystemSeCd", loginVO.getUntSystemSeCd());
//			loginMap.put("UntTaskwkSeCd", loginVO.getUntTaskwkSeCd());
//		}

		Integer authMenuNo = 0;
		authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.



		// 업무구분(해당업무 진입 메뉴 구분) > 변경될 수 있음. 정책 최종 확인 필요.
        // 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04)
		String taskwkSeCd = extrnlSprtActvtService.selectTaskwkSeCd(requestMap);


		Map<String, Object> mapDate = new HashMap<String, Object>();

		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다.
		 */


		mapDate.put("taskwkSeCd", taskwkSeCd);


		// 현재 일자 조회
		mapDate.put("strSysDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));




//		// 현재 선택된 단위 시스템 코드
//		mapDate.put("untTaskwk", loginMap.get("UntTaskwk"));
//
//		// 단위 시스템 코드
//		mapDate.put("untSystemSeCd", loginMap.get("UntSystemSeCd"));
//
//		// 단위 업무 코드
//		mapDate.put("untTaskwkSeCd", loginMap.get("UntTaskwkSeCd"));

		dataRequest.setResponse("dmInitInfo", mapDate);


//		dataRequest.setResponse("dmUserInfo", loginMap);
		log.info("#### strSysDate : " + mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
//		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
//		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);

//		dataRequest.setResponse("dmUserInfo", loginMap);
//
//		dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS"));
//
//		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD"));
//
//		dataRequest.setResponse("dsQualifyClass", mgmtCmmnCodeService.selectCommonCodeUnit("81"));
//
//		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD"));  // 종사자 SNS 구분
//
//		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCodeUnit("ISTDR_YN"));  // 기관장 여부
//
//		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());

		return new JSONDataView();
	}



	/**
	 * @Method명 : extrnlSprtActvtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 6. 15.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/extrnlSprtActvtList.do")
	public View extrnlSprtActvtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
//		log.info("#### strSearchKey              :" + searchParam.getValue("strSearchKey"));

		String searchKey = null;
		searchKey = searchParam.getValue("strSearchKey");
		log.info("#### searchKey              : " + searchKey);
		log.info("#### strSearchData              : " + searchParam.getValue("strSearchData"));
		// 검색조건 입력한 이름 암호화

		if(searchKey.equals("03")) {

			log.info("#### searchKey 03");

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
		totalCount = extrnlSprtActvtService.getTotalCount(mapParam);

		// 게시판 기본 데이터 호출
		List<Map<String, Object>> extrnlSprtActvtList = extrnlSprtActvtService.selectExtrnlSprtActvtList(mapParam);

		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", extrnlSprtActvtList);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();

	}



	/**
	 * @Method명 : searchInstSearch
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 6. 15.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/instSearch.do")
	public View instSearch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchInstInfo");

		// 입력한 기관 이름
		mapParam.put("INPUT_INST_NM", searchParam.getValue("INPUT_INST_NM"));

		int searchCount = 0;

		// 조회된 전체 데이터 갯수를 가져옵니다.
		searchCount = extrnlSprtActvtService.getSearchCount(mapParam);


		Map<String, Object> responseMap = new HashMap<String, Object>();

		if(searchCount == 0) {
			// 입력한 기관정보가 없습니다.
			responseMap.put("RESULT_YN", "N");
			responseMap.put("INPUT_INST_NM", searchParam.getValue("INPUT_INST_NM"));

			dataRequest.setResponse("dmSearchInstInfo", responseMap);

		}else if(searchCount > 0) {
			// 조회된 기관 정보 넘김
			// 입력한 기관 정보 조회 데이터
			List<Map<String, Object>> searchInstInfoList = extrnlSprtActvtService.selectSearchInstInfo(mapParam);

//			responseMap.put("RESULT_YN", "Y");
//			responseMap.put("INPUT_INST_NM", searchParam.getValue("INPUT_INST_NM"));
//			responseMap.put("SEARCH_INST_NO", searchInstInfo.get("INST_NO"));
//			responseMap.put("SEARCH_INST_NM", searchInstInfo.get("INST_NM"));

			responseMap.put("RESULT_YN", "Y");
			responseMap.put("INPUT_INST_NM", searchParam.getValue("INPUT_INST_NM"));

			dataRequest.setResponse("dmSearchInstInfo", responseMap);
			dataRequest.setResponse("dsSearchInstInfoList", searchInstInfoList);

		}



		return new JSONDataView();

	}



	/**
	 * @Method명 : onLoadExtrnlSprtActvtDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 6. 15.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadExtrnlSprtActvtDetail.do")
	public View onLoadExtrnlSprtActvtDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);

			loginMap.put("USER_ID", loginVO.getId());

		}



		// 용도 확인 필요 > 비빌번호 재확인용인듯함.
//		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
//		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);

//		dataRequest.setResponse("dmUserInfo", loginMap);


		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다.
		 */

		Map<String, String> mapDate = new HashMap<String, String>();

		// 현재 일자 조회
//		log.info("#### strSysDate : " + mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		mapDate.put("strSysDate", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		dataRequest.setResponse("dmTime", mapDate);


		// 집단연계지원관리번호 채번


		// 연계지원자 select box 목록
		Map<String, String> userInfoMap = extrnlSprtActvtService.selectUserInfo(loginMap);
		List<Map<String, String>> enfsnListMap = extrnlSprtActvtService.selectEnfsnList(userInfoMap);
		dataRequest.setResponse("dsEnfsnList", enfsnListMap);

		// 활동구분(연계지원활동구분코드) select box
		dataRequest.setResponse("dsLinkSprtActvtSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_SPRT_ACTVT_SE_CD", loginVO.getUntTaskwk()));


		// 기관구분(연계지원기관구분코드) select box
		dataRequest.setResponse("dsLinkSprtInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_SPRT_INST_SE_CD", loginVO.getUntTaskwk()));


		return new JSONDataView();
	}



	/**
	 * @Method명 : extrnlSprtActvtDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 6. 15.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/extrnlSprtActvtDetail.do")
	public View extrnlSprtActvtDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {


		String sViewType       = "";	// 화면 상태코드(s:상세, i:등록, u:수정, d:삭제)

		ParameterGroup paramDsForm = dataRequest.getParameterGroup("dsForm");

		// 화면 상태코드(s:상세, i:등록, u:수정, d:삭제)
		ParameterGroup viewTypeParam = dataRequest.getParameterGroup("dmDetail");

		sViewType = viewTypeParam.getValue("VIEW_TYPE");


		if (sViewType.equals("i") || sViewType.equals("I")) {

			// 등록처리
			extrnlSprtActvtService.insertExtrnlSprtActvtDetail(request, dataRequest);

		}else if (sViewType.equals("r") || sViewType.equals("R")) {

			// 상세처리
			List<Map<String, Object>> extrnlSprtActvtDetail = extrnlSprtActvtService.selectExtrnlSprtActvtDetail(dataRequest);

			dataRequest.setResponse("dsForm", extrnlSprtActvtDetail);


		}else if (sViewType.equals("u") || sViewType.equals("U")) {

			// 수정처리
			extrnlSprtActvtService.updateExtrnlSprtActvtDetail(request, dataRequest);

		}else if (sViewType.equals("d") || sViewType.equals("D")) {

			// 삭제처리
			extrnlSprtActvtService.deleteExtrnlSprtActvtDetail(dataRequest);

		}


		return new JSONDataView();

	}




		/**
		 * @Method명 : onLoadLinkResrceInstChc
		 * @param request
		 * @param response
		 * @param dataRequest
		 * @return
		 * @throws Exception
		 * @작성자 : KIM.SEONG.OK
		 * @작성일 : 2022. 6. 15.
		 * @Method설명 :
		 */
		@RequestMapping(value = "/onLoadLinkResrceInstChc.do")
		public View onLoadLinkResrceInstChc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			// 서비스내용(기관 서비스내용구분코드) select box
			dataRequest.setResponse("dsSrvcCnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_CN_SE_CD", userVo.getUntTaskwk()));


			return new JSONDataView();
		}


		/**
		 * @Method명 : linkResrceInstChcList
		 * @param request
		 * @param response
		 * @param dataRequest
		 * @return
		 * @throws Exception
		 * @작성자 : KIM.SEONG.OK
		 * @작성일 : 2022. 6. 15.
		 * @Method설명 :
		 */
		@RequestMapping(value = "/linkResrceInstChcList.do")
		public View linkResrceInstChcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			Map<String, Object> loginMap = new HashMap<>();
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

				// 임시 테스트용 하드코딩
//				loginMap.put("USER_ID", testUserId);


				loginMap.put("USER_ID", loginVO.getId());

			}


			// 로그인 사용자 기관번호와 자원제공주체번호 가 같은 데이터만 임시로 가져옴
			// 연계자원기관 목록
			Map<String, String> userInfoMap = extrnlSprtActvtService.selectUserInfo(loginMap);


			Map<String, Object> mapParam = new HashMap<String, Object>();

			// 검색 조회조건
			ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");


			mapParam.put("INST_NO", userInfoMap.get("INST_NO"));

			mapParam.put("SEARCH_KEY", searchParam.getValue("strSearchKey"));
			mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
			mapParam.put("SRVC_RESRCE_LCLAS_SE_CD", searchParam.getValue("srvcResrceLclasSeCd"));

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
			totalCount = extrnlSprtActvtService.getLinkResrceInstChcTotalCount(mapParam);

			// 연계자원 기관 조회
			List<Map<String, Object>> linkResrceInstChcList = extrnlSprtActvtService.selectLinkResrceInstChcList(mapParam);

			// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
			Map<String, Object> resPage = new HashMap<String, Object>();

			resPage.put("totalCount", totalCount);
			resPage.put("pageNo", pageIdx);
			resPage.put("pageRowCount", rowSize);

			dataRequest.setResponse("dsList", linkResrceInstChcList);
			dataRequest.setResponse("dmPage", resPage);

			return new JSONDataView();

		}


}
