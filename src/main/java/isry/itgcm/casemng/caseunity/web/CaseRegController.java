/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.web;

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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : CaseRegController.java
 * @프로그램 설명	: 사례관리 대상자에 대한 내역을 관리한다
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 4. 29.
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 4. 29.
 * @수정내용      :
 * -
 * -
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/case")
public class CaseRegController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaseRegController.class);

	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@RequestMapping("/onLoadCaseReq.do")
	public View onLoadCaseReq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectCaseTrmnOnLoad.paramGroup=[" + paramGroup + "]");
		HttpSession session = request.getSession();
        UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		List<Map<String, String>> paramList = paramGroup.getAllRowList();

		for (Map<String, String> rowMap : paramList) {

			sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
			rowMap.put("unitCode", userVo.getUntTaskwk());

			List<Map<String, Object>> list = comCodeService.selectCommonCodeUnit(rowMap);
			dataRequest.setResponse(sRetDsSet, list);

		}

		return new JSONDataView();

	}

	@RequestMapping(value = "/selectCaseMngNocs.do")
	public View selectCaseMngNocs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//단위업무상세페이지경로 조회
		List<Map<String, Object>> map = caseRegService.selectCaseMngNocs(dataRequest);
		dataRequest.setResponse("dsCaseMngNocs", map);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectUrlValue.do")
	public View selectUrlValue(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//단위업무상세페이지경로 조회
		List<Map<String, Object>> list = caseRegService.selectUrlValue(dataRequest);
		dataRequest.setResponse("dsUrlList", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectMainList.do")
	public View selectMainList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례 목록 조회
//		List<Map<String, Object>> list = caseRegService.selectMainList(request, dataRequest);
//		dataRequest.setResponse("dsCaseInqList", list);
		Map<String, Object> result =  caseRegService.selectCaseinqPagingList(request, dataRequest);

		dataRequest.setResponse("dsCaseInqList", result.get("dsCaseInqList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectDetailInfo.do")
	public View selectDetailInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 기본정보 조회
		List<Map<String, Object>> list = caseRegService.selectCaseBassDetail(dataRequest);
		dataRequest.setResponse("dsDetailInfo", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCaseYngbgsList.do")
	public View selectCaseYngbgsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 문제상태및원인 조회
		List<Map<String, Object>> list = caseRegService.selectCaseYngbgsList(dataRequest);
		dataRequest.setResponse("dsCaseYngbgs", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectFamInfoList.do")
	public View selectFamInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 가족정보 조회
		List<Map<String, Object>> list = caseRegService.selectFamInfoList(dataRequest);
		dataRequest.setResponse("dsFamInfo", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectAcbgSttsList.do")
	public View selectAcbgSttsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 학력상태 조회
		List<Map<String, Object>> list = caseRegService.selectAcbgSttsList(dataRequest);
		dataRequest.setResponse("dsAcbgStts", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSchulwDscntcList.do")
	public View selectSchulwDscntcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 학업중단 조회
		List<Map<String, Object>> list = caseRegService.selectSchulwDscntcList(dataRequest);
		dataRequest.setResponse("dsSchulwDscntc", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectEmpymnInfoList.do")
	public View selectEmpymnInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 취업정보 조회
		List<Map<String, Object>> list = caseRegService.selectEmpymnInfoList(dataRequest);
		dataRequest.setResponse("dsEmpymnInfo", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCasePicList.do")
	public View selectCasePicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 담당자 조회
		List<Map<String, Object>> list = caseRegService.selectCasePicList(dataRequest);
		dataRequest.setResponse("dsPic", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectBizRegList.do")
	public View selectBizRegList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 서비스실행사업 조회
		List<Map<String, Object>> list = caseRegService.selectBizRegList(dataRequest);
		dataRequest.setResponse("dsBizReg", list);

		return new JSONDataView();
	}

	@RequestMapping(value = "/processData.do")
	public View processData(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례등록 저장
		Map<String, Object> info = caseRegService.processData(request, dataRequest);
		dataRequest.setResponse("dmParam", info);

		return new JSONDataView();
	}

	/**
	 * @Method명   : selectCaseRegOnLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 20.
	 * @Method설명 : 사례관리목록 검색 공통코드 조회
	 */
	@RequestMapping(value = "/selectCaseRegOnLoad.do")
	public View selectCaseRegOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		LOGGER.debug("selectCaseRegOnLoad.paramGroup = [" + paramGroup + "]");

		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			LOGGER.debug("paramList = [" + paramList + "]");

			for (Map<String, String> rowMap : paramList) {

				sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
				rowMap.put("unitCode", userVo.getUntTaskwk());

				// 사례관리목록 검색 공통코드 조회
				List<Map<String, Object>> list = comCodeService.selectCommonCodeUnit(rowMap);
				dataRequest.setResponse(sRetDsSet, list);

			}
		}

		return new JSONDataView();
	}

	/**
	 * @Method명		: selectCaseRegOnLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자    	: Lee.Seung.Yeon
	 * @작성일		: 2022. 8. 05.
	 * @Method설명	: 원스크린 정보 조회
	 */
	@RequestMapping(value = "/selectOneScreenInfo.do")
	public View selectOneScreenInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> resultMap = caseRegService.selectOneScreenInfo(request, dataRequest);

		dataRequest.setResponse("dsTrprInfoList"	   , resultMap.get("trprInfoList"));		//대상자정보
		dataRequest.setResponse("dsEtcInfoList"		   , resultMap.get("etcInfoList"));			//기초생활수급/차상위계증/저소득대상지원/복지부 연계 정보
		dataRequest.setResponse("dsYngbgsSpclaSprtList", resultMap.get("yngbgsSpclaSprtList"));	//청소년특별지원 복지부 연계 정보
		dataRequest.setResponse("dsDscsnUneartHstrList", resultMap.get("dscsnUneartHstrList")); //상담(발굴)이력
		dataRequest.setResponse("dsCaseMngHstrList"	   , resultMap.get("caseMngHstrList"));		//사례관리이력
		dataRequest.setResponse("dsSrvcPvsnHstrList"   , resultMap.get("srvcPvsnHstrList"));	//서비스제공이력
		dataRequest.setResponse("dsAsisSysHstrList"    , resultMap.get("asisSysHstrList"));		//(구)서비스이력
		dataRequest.setResponse("dsBizHstrList"    	   , resultMap.get("bizHstrList"));			//사업이력

		return new JSONDataView();
	}

	/**
	 * @Method명		: selectTrprFamBeischList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자    	: Lee.Seung.Yeon
	 * @작성일		: 2022. 8. 24.
	 * @Method설명	: 청소년상태대분류 정합성 체크를 위한 대상자 가족특성/졸업상태 조회
	 */
	@RequestMapping(value = "/selectTrprFamBeischList.do")
	public View selectTrprFamBeischList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//대상자 가족특성/졸업상태 조회
		List<Map<String, Object>> list = caseRegService.selectTrprFamBeischList(dataRequest);
		dataRequest.setResponse("dsTrprFamBeisch", list);

		return new JSONDataView();
	}

	/**
	 * @Method명		: selectFamCnsttnInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자    	: Lee.Seung.Yeon
	 * @작성일		: 2022. 10. 25.
	 * @Method설명	: 가족구성정보 조회(행정안전부 실시간 연계)
	 */
	@RequestMapping(value = "/selectFamCnsttnInfo.do")
	public View selectFamCnsttnInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> resultMap = caseRegService.selectFamCnsttnInfo(request, dataRequest);

		dataRequest.setResponse("dsFamCnsttList", resultMap.get("famCnsttnList")); //가족구성정보

		return new JSONDataView();
	}

}
