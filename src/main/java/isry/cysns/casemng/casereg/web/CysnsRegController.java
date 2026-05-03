/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casereg.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.casemng.casereg.service.CysnsRegService;
import isry.cysns.uneartmng.dgnssrcpt.service.DgnssRcptService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import lombok.extern.slf4j.Slf4j;

/**
 * @파일명        : CysnsRegController.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 7.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 7.
 * @수정내용      :
 * -
 * -
 */
@Controller
@RequestMapping(value = "/isry/cysns/casemng/casereg")
public class CysnsRegController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "cysnsRegService")
	private CysnsRegService cysnsRegService;

	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Resource(name = "dgnssRcptService")
	private DgnssRcptService dgnssRcptService;

	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsList", cysnsRegService.selectReqById(dataRequest));

		dataRequest.setResponse("dsList2", cysnsRegService.selectReqById2(dataRequest));

		List<Map<String, String>> result = cysnsRegService.selectReqById3(dataRequest);

		dataRequest.setResponse("dsList3", result);
		dataRequest.setResponse("dsSrvyRspnsInfo", cysnsRegService.selectReqBySrvyInfo(dataRequest));

		if (result.size() > 0) { //미디어 데이타가 있으면 실행
			String trprInfoNo = result.get(0).get("TRPR_INFO_NO");
			String dgnssExmnMngNo = result.get(0).get("DGNSS_EXMN_MNG_NO");
			dataRequest.getParameterGroup("dmListParam").setValue(0, "TRPR_INFO_NO", trprInfoNo);  //대상자번호
			dataRequest.getParameterGroup("dmListParam").setValue(0, "DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);  //진단조사관리번호

			//미디어이용습관결과
			dataRequest.setResponse("dsDgnssScoreList", dgnssRcptService.selectDgnssScoreList(dataRequest));
			dataRequest.setResponse("dsInfantChilList", dgnssRcptService.selectInfantChilList(dataRequest));
			dataRequest.setResponse("dsCyberGambleList", dgnssRcptService.selectCyberGambleList(dataRequest));
			dataRequest.setResponse("dsAddInspList", dgnssRcptService.selectAddInspList(dataRequest));
		}

		return new JSONDataView();
	}

	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> dmParam = cysnsRegService.saveData(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}

	@RequestMapping("/selectDgnssByTrprInfoNo.do") //진단조사 & 학교학년
	public View selectDgnssByTrprInfoNo(DataRequest dataRequest) throws Exception {

		List<Map<String, String>> result = cysnsRegService.selectDgnssByTrprInfoNo(dataRequest);
		dataRequest.setResponse("dsList3", result);

		if (result.size() > 0) {
			//미디어이용습관결과
			String dgnssExmnMngNo = result.get(0).get("DGNSS_EXMN_MNG_NO");
			dataRequest.getParameterGroup("dmListParam").setValue(0, "DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);  //진단조사관리번호

			dataRequest.setResponse("dsDgnssScoreList", dgnssRcptService.selectDgnssScoreList(dataRequest));
			dataRequest.setResponse("dsInfantChilList", dgnssRcptService.selectInfantChilList(dataRequest));
			dataRequest.setResponse("dsCyberGambleList", dgnssRcptService.selectCyberGambleList(dataRequest));
			dataRequest.setResponse("dsAddInspList", dgnssRcptService.selectAddInspList(dataRequest));
		}

		return new JSONDataView();
	}

	//설문지조회
	@RequestMapping("/selectSrvyResultList.do")  //설문결과
	public View selectSrvyResultList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, String>> allRowList = dataRequest.getParameterGroup("dsSrvyParam").getAllRowList();

		for (Map<String, String> map : allRowList) {
			Map<String, Object> result1 = getSrvyResultById(request, dataRequest, map.get("QUSTNB_SHAPE_SE_CD"));
			dataRequest.setResponse(map.get("SRVY_DSNAME"), result1.get("dsList")); //설문지문항정보
			dataRequest.setResponse(map.get("RELM_DSNAME"), result1.get("ds2"));
		}

		return new JSONDataView();
	}


	private Map<String, Object> getSrvyResultById(HttpServletRequest request, DataRequest dataRequest, String param) throws Exception {

		dataRequest.getParameterGroup("dmSearch").setValue(0, "QUSTNB_SHAPE_SE_CD", param);  //설문지템프번호

		String qustnbMngNo = cysnsRegService.selectSrvyTrprById(dataRequest);  //설문지관리번호
		dataRequest.getParameterGroup("dmQustnbMngInfo").setValue(0, "QUSTNB_MNG_NO", qustnbMngNo);
		Map<String, Object> result = survshtMmnService.selectPreSurvshtList(request, dataRequest);
		return result;
	}

	//설문지생성
	@RequestMapping("/onLoadPreSurvshtList.do")
	public View onLoadPreSurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//설문지 생성 by 설문지 템프번호
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");

		if (StringUtils.isEmpty(param.getValue(0, "QUSTNB_MNG_NO"))) {  //설문지관리번호가 없는 경우

			Map<String, Object> map = new HashMap<>();
			String qustnbTmptMngNo = param.getValue("QUSTNB_TMPT_MNG_NO");  //설문템프번호
			map.put("QUSTNB_TMPT_MNG_NO", qustnbTmptMngNo);
			Map<String, Object> result = survshtMmnService.processSurvshtTmptData(request, dataRequest, map);

			LOGGER.debug("copySurvshtTmptData={}", (String) result.get("QUSTNB_MNG_NO"));

			//설문지
			param.setValue(0, "QUSTNB_MNG_NO", (String) result.get("QUSTNB_MNG_NO"));  //구한 설문지번호

		} else {
			param.setValue(0, "QUSTNB_MNG_NO", param.getValue(0, "QUSTNB_MNG_NO"));  //기존 설문지번호
		}

		Map<String, Object> result1 = survshtMmnService.selectPreSurvshtInfo(request, dataRequest);
		dataRequest.setResponse("dmQustnbMngInfo", result1.get("dmQustnbMngInfo"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPreSurvshtList.do")  //첫번째 설문 - 동일 화면
	public View selectPreSurvshtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectPreSurvshtList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList")); //설문지문항정보
		dataRequest.setResponse("dsSrvyQesitm", result.get("ds1"));
		dataRequest.setResponse("dsSrvyRelm", result.get("ds2"));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPreSurvshtList2.do") //두번째 설문 - 동일화면
	public View selectPreSurvshtList2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = survshtMmnService.selectPreSurvshtList(request, dataRequest);

		dataRequest.setResponse("dsList2", result.get("dsList")); //설문지문항정보
		dataRequest.setResponse("dsSrvyQesitm2", result.get("ds1"));
		dataRequest.setResponse("dsSrvyRelm2", result.get("ds2"));
		return new JSONDataView();
	}


	@RequestMapping("/selectExcnReqById.do")
	public View selectExcnReqById(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsList", cysnsRegService.selectExcnReqById(dataRequest));

		dataRequest.setResponse("dsList2", cysnsRegService.selectExcnReqById2(dataRequest));
		dataRequest.setResponse("dsList3", cysnsRegService.selectTrlInspByList(dataRequest));

		return new JSONDataView();
	}

	@RequestMapping("/saveExcnData.do")
	public View saveExcnData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, String>> params = new ArrayList<>();

		cysnsRegService.saveExcnData(request, dataRequest, params);

		return new JSONDataView();
	}

	@RequestMapping("/selectTrlInspByResrceNo.do")
	public View selectTrlInspByResrceNo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsList4", cysnsRegService.selectTrlInspByResrceNo(dataRequest));

		return new JSONDataView();
	}

	@RequestMapping("/saveMdlrtSprtData.do")
	public View saveMdlrtSprtData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		cysnsRegService.saveMdlrtSprtData(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping("/selectMdlrtReqById.do")
	public View selectMdlrtReqById(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsList", cysnsRegService.selectExcnReqById(dataRequest));
		dataRequest.setResponse("dsList2", cysnsRegService.selectExcnReqById2(dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCnctrClinicInfo.do")
	public View selectCnctrClinicInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = cysnsRegService.selectCnctrClinicInfo(request, dataRequest);

		dataRequest.setResponse("dsCnctrClinic", result.get("dsCnctrClinic"));
		dataRequest.setResponse("dsCnctrClinicSrvy", result.get("dsCnctrClinicSrvy"));

		List<Map<String, String>> dsSrvy = (List<Map<String, String>>) result.get("dsCnctrClinicSrvy");

		String qustnbMngNo = "";
		for (int i=0;i<dsSrvy.size();i++) {
			qustnbMngNo = dsSrvy.get(i).get("QUSTNB_MNG_NO");
			if (i == 0) {
				if (!"".equals(qustnbMngNo) && null != qustnbMngNo) {
					Map<String, Object> map = survshtMmnService.selectPreSurvshtResponseList(qustnbMngNo);

					dataRequest.setResponse("dsCnctrSucClinic", map.get("dsList")); //설문지문항정보
					dataRequest.setResponse("dsCnctrSucRelmClinic", map.get("ds2"));
				}
			} else if (i == 1) {
				if (!"".equals(qustnbMngNo) && null != qustnbMngNo) {
					Map<String, Object> map = survshtMmnService.selectPreSurvshtResponseList(qustnbMngNo);

					dataRequest.setResponse("dsCnctrInjClinic", map.get("dsList")); //설문지문항정보
					dataRequest.setResponse("dsCnctrInjRelmClinic", map.get("ds2"));
				}

			}
		}

		return new JSONDataView();
	}
}
