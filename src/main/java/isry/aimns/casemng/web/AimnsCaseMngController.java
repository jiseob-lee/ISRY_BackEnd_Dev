/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.aimns.casemng.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.aimns.casemng.service.AimnsCaseMngService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : AimnsCaseMngController.java
 * @프로그램 설명 : 사례관리>실행&종결 화면의 고유항목 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 12.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 12.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/aimns/casemng")
public class AimnsCaseMngController {

	@Resource(name = "aimnsCaseMngService")
	private AimnsCaseMngService aimnsCaseMngService;

	@Resource(name = "submsService")
	private SubmsService aimnsService;

	/**
	 * @Method명 : selectEduComplSchdl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 교육이수일정 조회
	 */
	@RequestMapping(value = "/selectEduComplSchdl.do")
	public View selectEduComplSchdl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aimnsCaseMngService.selectEduComplSchdl(dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveEduComplSchdl.do")
	public View saveEduComplSchdl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aimnsCaseMngService.saveEduComplSchdl(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 *
	 * @Method명 : selectPvsnResrceNm
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 : 교육이수일정관리 과정명 조회
	 */
	@RequestMapping(value = "/selectPvsnResrceNm.do")
	public View selectPvsnResrceNm(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> result = aimnsCaseMngService.selectPvsnResrceNm(request, dataRequest);

		dataRequest.setResponse("dsCaseInqList", result.get("dsCaseInqList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}

	/**
	 *
	 * @Method명 : saveEduCmplSchdlMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 : 교육이수일정관리 save
	 */
	@RequestMapping(value = "/saveEduCmplSchdlMng.do")
	public View saveEduCmplSchdlMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aimnsCaseMngService.saveEduCmplSchdlMng(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectMainList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록조회
	 */
	@RequestMapping(value = "/selectMainList.do")
	public View selectMainList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		// 사례 목록 조회
		Map<String, Object> result = aimnsCaseMngService.selectCaseinqPagingList(request, dataRequest);

		dataRequest.setResponse("dsCaseInqList", result.get("dsCaseInqList"));
		dataRequest.setResponse("dmPageInfo", result.get("dmPage"));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectCaseMngOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록 콤보데이터조회
	 */
	@RequestMapping(value = "/selectCaseMngOnload.do")
	public View selectCaseMngOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		dataRequest.setResponse("dsResrceCmb", aimnsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsBizYr", aimnsService.selectBizYrCombo(request));
		return new JSONDataView();
	}
}
