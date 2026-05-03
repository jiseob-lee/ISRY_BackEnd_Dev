/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casetrmn.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.casemng.casereg.service.CysnsRegService;
import isry.cysns.casemng.casetrmn.service.CysnsTrmnService;
import isry.cysns.uneartmng.dgnssrcpt.service.DgnssRcptService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;

/**
 * @파일명        : CysnsTrmnController.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 25.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      :
 * -
 * -
 */
@Controller
@RequestMapping(value = "/isry/cysns/casemng/casetrmn")
public class CysnsTrmnController {

	@Resource(name = "cysnsRegService")
	private CysnsRegService cysnsRegService;

	@Resource(name = "cysnsTrmnService")
	private CysnsTrmnService cysnsTrmnService;

	//추가
	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Resource(name = "dgnssRcptService")
	private DgnssRcptService dgnssRcptService;

	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsProbmStts", cysnsTrmnService.selectProbmSttsById(dataRequest));

		List<Map<String, String>> result = cysnsRegService.selectReqById3(dataRequest);
		dataRequest.setResponse("dsList", result);
		dataRequest.setResponse("dsSrvyAddtngInfo", cysnsRegService.selectSrvyAddtngInfo(dataRequest));

		if (result.size() > 0) {
			String trprInfoNo = result.get(0).get("TRPR_INFO_NO");
			String dgnssExmnMngNo = result.get(0).get("DGNSS_EXMN_MNG_NO");
			dataRequest.getParameterGroup("dmListParam").setValue(0, "TRPR_INFO_NO", trprInfoNo);  //대상자번호
			dataRequest.getParameterGroup("dmListParam").setValue(0, "DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);  //진단조사관리번호

			//미디어이용습관결과
			dataRequest.setResponse("dsDgnssScoreList", dgnssRcptService.selectDgnssScoreTrmnList(dataRequest));
			dataRequest.setResponse("dsInfantChilList", dgnssRcptService.selectInfantChilTrmnList(dataRequest));
			dataRequest.setResponse("dsCyberGambleList", dgnssRcptService.selectCyberGambleTrmnList(dataRequest));
		}

		// 추가 2023-09-05
		Map<String, Object> resultMap = cysnsTrmnService.selectCnctrClinicInfo(dataRequest);

		dataRequest.setResponse("dsCnctrClinic", resultMap.get("dsCnctrClinic"));
		dataRequest.setResponse("dsCnctrClinicSrvy", resultMap.get("dsCnctrClinicSrvy"));
		dataRequest.setResponse("dmCnctrClinicInfo", resultMap.get("dmCnctrClinicInfo"));


		return new JSONDataView();
	}

	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		cysnsTrmnService.saveData(request, dataRequest);

		return new JSONDataView();
	}

	//추가분
	@RequestMapping("/selectSrvyResultList.do")  //설문결과
	public View selectSrvyResultList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, String>> allRowList = dataRequest.getParameterGroup("dsSrvyParam").getAllRowList();

		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");

		for (Map<String, String> map : allRowList) {
			Map<String, Object> result1 = getSrvyResultById(request, dataRequest, map.get("QUSTNB_SHAPE_SE_CD"));
			dataRequest.setResponse(map.get("SRVY_DSNAME"), result1.get("dsList")); //설문지문항정보
			dataRequest.setResponse(map.get("RELM_DSNAME"), result1.get("ds2"));
		}

//		//서비스평가
//		Map<String, Object> result1 = getSrvyResultById(request, dataRequest, param.getValue("QUSTNB_TMPT_MNG_NO1"));
//		dataRequest.setResponse("dsSrvySrvc", result1.get("dsList")); //설문지문항정보
//		dataRequest.setResponse("dsSrvyRelmSrvc", result1.get("ds2"));
//
//		//서비스만족도
//		Map<String, Object> result2 = getSrvyResultById(request, dataRequest, param.getValue("QUSTNB_TMPT_MNG_NO2"));
//		dataRequest.setResponse("dsSrvyDgstfn", result2.get("dsList")); //설문지문항정보
//		dataRequest.setResponse("dsSrvyRelmDgstfn", result2.get("ds2"));
//
//		//고위기
//		Map<String, Object> result3 = getSrvyResultById(request, dataRequest, param.getValue("QUSTNB_TMPT_MNG_NO3"));
//		dataRequest.setResponse("dsSrvyHlisk", result3.get("dsList")); //설문지문항정보
//		dataRequest.setResponse("dsSrvyRelmHlisk", result3.get("ds2"));

		return new JSONDataView();
	}


	private Map<String, Object> getSrvyResultById(HttpServletRequest request, DataRequest dataRequest, String param) throws Exception {

		dataRequest.getParameterGroup("dmSearch").setValue(0, "QUSTNB_SHAPE_SE_CD", param);  //설문지형태구분코드

		String qustnbMngNo = cysnsRegService.selectSrvyTrprById(dataRequest);  //설문지관리번호
		dataRequest.getParameterGroup("dmQustnbMngInfo").setValue(0, "QUSTNB_MNG_NO", qustnbMngNo);
		Map<String, Object> result = survshtMmnService.selectPreSurvshtList(request, dataRequest);
		return result;
	}

}
