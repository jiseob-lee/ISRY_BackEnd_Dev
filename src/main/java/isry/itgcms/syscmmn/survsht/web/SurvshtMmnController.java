/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.web;

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
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;

/**
 * @파일명 : SurvshtController.java
 * @프로그램 설명 : 설문지 작성을 관리하는 Controller
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 :
 * @수정일 :
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/itgcms/syscmmn/survsht")
public class SurvshtMmnController extends IsryBaseController {

	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	ScpDb  scpDb   = new ScpDb();
	Masking mask   = new Masking();

	/**
	 * @Method명 : onLoadSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 5. 10.
	 * @Method설명 : 설문지목록화면 공통코드 조회
	 */
	@RequestMapping(value = "/onLoadSurvsht.do")
	public View onLoadSurvsht(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("BULLETIN_SEARCH_BREAKDOWN", userVo.getUntTaskwk())); //공통코드 검색조건
		dataRequest.setResponse("dsSrvyPrgrsSttsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_PRGRS_STTS_SE_CD", userVo.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsUseYn", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk())); //사용여부

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSurvshtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 5. 04
	 * @Method설명 : 설문지 목록 조회
	 */
	@RequestMapping(value = "/selectSurvshtList.do")
	public View selectSurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");

		Map<String, Object> dmSearchMap = new HashMap<>();
		if (dmSearchParam.getValue("strSearchKey").equals("tit")) {
			dmSearchMap.put("QUSTNB_NM", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("cont")) {
			dmSearchMap.put("DOC_INO_CN", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("rcvrNm")) {
			dmSearchMap.put("RCVR_ID", dmSearchParam.getValue("strSearchData"));
		}
		dmSearchMap.put("START_DATE", dmSearchParam.getValue("startDate"));					//조회시작날짜
		dmSearchMap.put("END_DATE", dmSearchParam.getValue("endDate"));

		dmSearchMap.put("UNT_TASKWK_SE_CD", dmSearchParam.getValue("UNT_TASKWK_SE_CD"));
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = survshtMmnService.selectSurvshtListTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = survshtMmnService.selectSurvshtList(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(CUD)
	 */
	@RequestMapping(value = "/saveSurvsht.do")
	public View saveSurvsht(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		survshtMmnService.saveSurvsht(dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : insertSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	@RequestMapping(value = "/insertSurvsht.do")
	public View insertSurvsht(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSaveMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			dmSaveMap.put("SNDPTY_ID", loginVO.getUserName());
		}
		dmSaveMap.put("DOC_INO_CN", dmSave.getValue("DOC_INO_CN"));
		dmSaveMap.put("RCVR_ID", dmSave.getValue("RCVR_ID"));
		dmSaveMap.put("INO_DOC_TTL_NM", dmSave.getValue("INO_DOC_TTL_NM"));
		dmSaveMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));

		survshtMmnService.insertSurvsht(dmSaveMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updateSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/updateSurvsht.do")
	public View updateSurvsht(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmUpdateMap = new HashMap<>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			dmUpdateMap.put("LAST_MDFR_ID", loginVO.getUserName());
		}

		dmUpdateMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));

		survshtMmnService.updateSurvsht(dmUpdateMap);

		return new JSONDataView();
	}


	/**
	 * @Method명 : onLoadSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 5. 10.
	 * @Method설명 : 문항목록화면 공통코드 조회
	 */
	@RequestMapping(value = "/onLoadQesitmList.do")
	public View onLoadQesitmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); //공통코드 검색조건
		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("QESITM_TYPE_SE_CD", userVo.getUntTaskwk())); //공통코드 검색조건
		//dataRequest.setResponse("dsSrvyTrpr", survshtMmnService.selectSrvyTrprList(request, dataRequest));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSurvshtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 5. 04
	 * @Method설명 : 문항 목록 조회
	 */
	@RequestMapping(value = "/selectQesitmList.do")
	public View selectQesitmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");
		int pageIdx = 1;
		int rowSize = 9999;
		int startIndex = 0;
		Integer totalCount = 0;

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("QESITM_TYPE_SE_CD", dmSearchParam.getValue("strSearchKey"));
		dmSearchMap.put("QESITM_CN", dmSearchParam.getValue("strSearchData"));

		if(dataRequest.getParameterGroup("dmPage") != null) {
			// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
			ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

			// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
			totalCount = survshtMmnService.selectQesitmListTotalCount(dmSearchMap);

			// 페이지 인덱싱에 필요한 정보를 정제합니다.
			pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
			rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
			startIndex = (pageIdx - 1) * rowSize;
		}

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = survshtMmnService.selectQesitmList(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", listBoard.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSurvshtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 5. 04
	 * @Method설명 : 문항내용,문항보기목록 조회
	 */
	@RequestMapping(value = "/selectQesitmExmplList.do")
	public View selectQesitmExmplList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup ds1 = dataRequest.getParameterGroup("dmSearch");

		ParameterRow pRow = ds1.get(0);
		Map<String, String> dmMap = pRow.toMap();

		List<Map<String, Object>> rMap = survshtMmnService.selectQesitm(dmMap);
		List<Map<String, Object>> dsMap = survshtMmnService.selectQesitmExmplList(dmMap);

		dataRequest.setResponse("ds1", rMap);
		dataRequest.setResponse("dsList", dsMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : insertSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	@RequestMapping(value = "/saveQesitmMng.do")
	public View saveQesitmMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		survshtMmnService.saveQesitmMng(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : insertSurvsht
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	@RequestMapping(value = "/deleteQesitmMng.do")
	public View deleteQesitmMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> result = survshtMmnService.deleteQesitmMng(request, dataRequest);
		Map<String, Object> dmReturn = new HashMap<String, Object>();

		dmReturn.put("QESITM_MNG_NO", result.get("QESITM_MNG_NO"));
		dmReturn.put("RETURN_VALUE", result.get("RETURN_VALUE"));

		dataRequest.setResponse("dmReturn", dmReturn);
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveQustnbMng.do")
	public View saveQustnbMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.saveQustnbMng(request, dataRequest);
		dataRequest.setResponse("dmSrvyDtl", result.get("dmSrvyDtl"));
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectQesitmQustnbMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 5. 04
	 * @Method설명 : 설문지 문항 목록
	 */
	@RequestMapping(value = "/selectQesitmQustnbMngList.do")
	public View selectQesitmQustnbMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

//		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");
//		int pageIdx = 1;
//		int rowSize = 9999;
//		int startIndex = 0;
//		Integer totalCount = 0;
		Map<String, Object> result = survshtMmnService.selectQesitmQustnbMngList(request, dataRequest);

		log.debug(result.toString());
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dsList2", result.get("dsList2"));
		dataRequest.setResponse("dsResultCrtrList", result.get("dsResultCrtrList"));
		dataRequest.setResponse("dsGrdngRelmList", result.get("dsGrdngRelmList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadGrdngRelmList.do")
	public View onLoadGrdngRelmList (HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsGrdngCrtrList", mgmtCmmnCodeService.selectCommonCodeUnit("GRDNG_CRTR_SE_CD", userVo.getUntTaskwk())); //공통코드 채점기준 구분코드
		dataRequest.setResponse("dsUseYn", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk())); //공통코드 사용자설문영역표시여부, 관리자설문영역표시여부

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectQustnbRelmList.do")
	public View selectQustnbRelmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectQuestnbRelmList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveQustnbRelmList.do")
	public View saveQustnbRelmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.saveQuestnbRelmList(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadResultCrtrList.do")
	public View onLoadResultCrtrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsGrdngCrtrList", mgmtCmmnCodeService.selectCommonCodeUnit("GRDNG_CRTR_SE_CD", userVo.getUntTaskwk())); //공통코드 채점기준 구분코드
		dataRequest.setResponse("dsGrdngRelmList", mgmtCmmnCodeService.selectCommonCodeUnit("GRDNG_RELM_SE_CD", userVo.getUntTaskwk())); //공통코드 채점영역 구분코드
		dataRequest.setResponse("dsResultCrtrList", mgmtCmmnCodeService.selectCommonCodeUnit("RESULT_CRTR_SE_CD", userVo.getUntTaskwk())); //공통코드 결과기준구분코드

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectResultCrtrList.do")
	public View selectResultCrtrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectResultCrtrList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadResultCrtr.do")
	public View onLoadResultCrtr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsResultCrtrList", mgmtCmmnCodeService.selectCommonCodeUnit("RESULT_CRTR_SE_CD", userVo.getUntTaskwk())); //공통코드 결과기준구분코드

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectResultQustnbRelm.do")
	public View selectResultQustnbRelm(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectResultQustnbRelm(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveResultCrtrList.do")
	public View saveResultCrtrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.saveResultCrtrList(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadPreSurvshtList.do")
	public View onLoadPreSurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {


		Map<String, Object> result = survshtMmnService.selectPreSurvshtInfo(request, dataRequest);

		dataRequest.setResponse("dmQustnbMngInfo", result.get("dmQustnbMngInfo"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPreSurvshtList.do")
	public View selectPreSurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectPreSurvshtList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList")); //설문지문항정보
		dataRequest.setResponse("ds1", result.get("ds1"));
		dataRequest.setResponse("ds2", result.get("ds2"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/excuteStatusQustnbMng.do")
	public View excuteStatusQustnbMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.processStatusQustnbMng(request, dataRequest);

		dataRequest.setResponse("dmReturn", result.get("dmReturn"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSurvshtTmptList.do")
	public View selectSurvshtTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");

		Map<String, Object> dmSearchMap = new HashMap<>();
//		if (dmSearchParam.getValue("strSearchKey").equals("tit")) {
//			dmSearchMap.put("QUSTNB_NM", dmSearchParam.getValue("strSearchData"));
//		} else if (dmSearchParam.getValue("strSearchKey").equals("sndptyNm")) {
//			dmSearchMap.put("SNDPTY_ID", dmSearchParam.getValue("strSearchData"));
//		} else if (dmSearchParam.getValue("strSearchKey").equals("cont")) {
//			dmSearchMap.put("DOC_INO_CN", dmSearchParam.getValue("strSearchData"));
//		} else if (dmSearchParam.getValue("strSearchKey").equals("rcvrNm")) {
//			dmSearchMap.put("RCVR_ID", dmSearchParam.getValue("strSearchData"));
//		}

		dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
		dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		dmSearchMap.put("START_DATE", dmSearchParam.getValue("startDate"));					//조회시작날짜
		dmSearchMap.put("END_DATE", dmSearchParam.getValue("endDate"));

		dmSearchMap.put("UNT_TASKWK_SE_CD", dmSearchParam.getValue("UNT_TASKWK_SE_CD"));
		dmSearchMap.put("USE_YN", dmSearchParam.getValue("USE_YN"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = survshtMmnService.selectSurvshtTmptListTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = survshtMmnService.selectSurvshtTmptList(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectQesitmQustnbTmptMngList.do")
	public View selectQesitmQustnbTmptMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.selectQesitmQustnbTmptMngList(request, dataRequest);

		log.debug(result.toString());
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dsList2", result.get("dsList2"));
		dataRequest.setResponse("dmSrvyDtl", result.get("dmSrvyDtl"));
		dataRequest.setResponse("dsResultCrtrList", result.get("dsResultCrtrList"));
		dataRequest.setResponse("dsGrdngRelmList", result.get("dsGrdngRelmList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveQustnbTmptMng.do")
	public View saveQustnbTmptMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.saveQustnbTmptMng(request, dataRequest);
		dataRequest.setResponse("dmSrvyDtl", result.get("dmSrvyDtl"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectQustnbRelmTmptList.do")
	public View selectQustnbRelmTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectQuestnbRelmTmptList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveQustnbRelmTmptList.do")
	public View saveQustnbRelmTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.saveQuestnbRelmTmptList(request, dataRequest);
		dataRequest.setResponse("dmSrvyDtl", result.get("dmSrvyDtl"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectResultCrtrTmptList.do")
	public View selectResultCrtrTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectResultCrtrTmptList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveResultCrtrTmptList.do")
	public View saveResultCrtrTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.saveResultCrtrTmptList(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectResultQustnbRelmTmpt.do")
	public View selectResultQustnbRelmTmpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectResultQustnbRelmTmpt(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("resPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadPreSurvshtTmptList.do")
	public View onLoadPreSurvshtTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectPreSurvshtTmptInfo(request, dataRequest);

		dataRequest.setResponse("dmQustnbMngInfo", result.get("dmQustnbMngInfo"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPreSurvshtTmptList.do")
	public View selectPreSurvshtTmptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectPreSurvshtTmptList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList")); //설문지문항정보
		dataRequest.setResponse("ds1", result.get("ds1"));
		dataRequest.setResponse("ds2", result.get("ds2"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadSurvshtCpTmpt.do")
	public View onLoadSurvshtCpTmpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); //공통코드 검색조건
		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("BULLETIN_SEARCH_BREAKDOWN", userVo.getUntTaskwk())); //공통코드 검색조건

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSurvshtCpTmpt.do")
	public View selectSurvshtCpTmpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectSurvshtCpTmptList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList")); //설문지문항정보
		dataRequest.setResponse("dmPage", result.get("dmPage")); //설문지문항정보

		return new JSONDataView();
	}

	@RequestMapping(value = "/excuteSurvshtCpTmpt.do")
	public View processSurvshtCpTmpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.processSurvshtCpTmpt(request, dataRequest);

		dataRequest.setResponse("ds2", result.get("ds1"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/excuteQustnbTmptMng.do")
	public View processQustnbTmptMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.processQustnbTmptMng(request, dataRequest);

		dataRequest.setResponse("dmSrvyDtl", result.get("dmSrvyDtl")); //설문지문항정보
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadMySurvshtList.do")
	public View onLoadMySurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//Map<String, Object> result = survshtMmnService.excuteQustnbTmptMng(request, dataRequest);
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsTrgtSeList", mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_TRGT_SE_CD", userVo.getUntTaskwk())); //공통코드 검색조건
		dataRequest.setResponse("dsPrgrsSttsList", mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_PRGRS_STTS_SE_CD", userVo.getUntTaskwk())); //공통코드 검색조건
		//dataRequest.setResponse("dmSrvyDtl", result.get("dmSrvyDtl")); //설문지문항정보
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectMySurvshtList.do")
	public View selectMySurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.selectMySurvshtList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/savePreSurvshtList.do")
	public View savePreSurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.savePreSurvshtList(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSurvshtTmptRelmMarkList.do")
	public View selectSurvshtTmptRelmMarkList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.selectSurvshtTmptRelmMarkList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadDidimGrdngRelmList.do")
	public View onLoadDidimGrdngRelmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsSxdcSeList", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk())); // 성별 기준코드
		dataRequest.setResponse("dsGrdngCrtrList", mgmtCmmnCodeService.selectCommonCodeUnit("GRDNG_CRTR_SE_CD", userVo.getUntTaskwk())); //공통코드 채점기준 구분코드
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectDidimGrdngRelmList.do")
	public View selectDidimGrdngRelmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.selectDidimGrdngRelmList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveDidimGrdngRelmList.do")
	public View saveDidimGrdngRelmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = survshtMmnService.saveDidimGrdngRelmList(request, dataRequest);

//		dataRequest.setResponse("dsList", result.get("dsList"));

		return new JSONDataView();
	}

	// 추가 된 내용 2023-08-17
	@RequestMapping(value = "/sendSrvyMsg.do")
	public View sendSrvyMsg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result =  survshtMmnService.sendSrvyMsg(request, dataRequest);

		dataRequest.setResponse("dmSendSrvyMsgInfo", result);
		return new JSONDataView();
	}

}
