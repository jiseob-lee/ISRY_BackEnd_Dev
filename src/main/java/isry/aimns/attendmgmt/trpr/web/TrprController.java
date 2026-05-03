/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.trpr.web;

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

import isry.aimns.attendmgmt.trpr.service.TrprService;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : TrprController.java
 * @프로그램 설명 :
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 6. 7.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 6. 7.
 * @수정내용 : - -
 */

@Controller
@RequestMapping(value = "/isry/aimns/attendmgmt/trpr")
public class TrprController {

//	Logger logger = LoggerFactory.getLogger(this.getClass());

	// 내일이룸 관련 서비스
	@Resource(name = "submsService")
	private SubmsService submsService;

	@Resource(name = "trprInqService")
	private TrprInqService trprInqService;

	// 공통코드 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	// 프로그램실시관리 관련 서비스
	@Resource(name = "trprService")
	private TrprService trprService;

	/**
	 * @Method명 : selectTrprCombo
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 7.
	 * @Method설명 : 검색콤보
	 */
	@RequestMapping(value = "/selectTrprCombo.do")
	public View selectTrprCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> listBizYrCombo = submsService.selectBizYrCombo(request);
		List<Map<String, Object>> listInstCombo = submsService.selectInstNmCombo(request);
		List<Map<String, Object>> listResrceCombo = submsService.selectResrceNmCombo(request);

		dataRequest.setResponse("dsBizYr", listBizYrCombo);
		dataRequest.setResponse("dsInst", listInstCombo);
		dataRequest.setResponse("dsResrce", listResrceCombo);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprFnsh
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 8.
	 * @Method설명 : 수료자 명단
	 */

	@RequestMapping(value = "/selectTrprFnsh.do")
	public View selectTrprFnsh(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", trprService.selectTrprFnsh(request, dataRequest));

		return new JSONDataView();

	}

	/**
	 * @Method명 : selectTrprEmpymn
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :(조기)취업자명단(파일다운로드)
	 */
	@RequestMapping(value = "/selectTrprEmpymn.do")
	public View selectTrprEmpymn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", trprService.selectTrprEmpymn(request, dataRequest));

		return new JSONDataView();

	}

	/**
	 * @Method명 : selectTrprMdstrmFailr
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 중도탈락자 명단
	 */
	@RequestMapping(value = "/selectTrprMdstrmFailr.do")
	public View selectTrprMdstrmFailr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", trprService.selectTrprMdstrmFailr(request, dataRequest));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprCertiAcqs
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :자격증취득자 명단
	 */
	@RequestMapping(value = "/selectTrprCertiAcqs.do")
	public View selectTrprCertiAcqs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", trprService.selectTrprCertiAcqs(request, dataRequest));

		return new JSONDataView();

	}

	/** 기타자 명단 */
	@RequestMapping(value = "/selectTrprEtc.do")
	public View selectTrprEtc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", trprService.selectTrprEtc(request, dataRequest));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprAcbgAcqs
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 8.
	 * @Method설명 : 학력취득자 명단
	 */
	@RequestMapping(value = "/selectTrprAcbgAcqs.do")
	public View selectTrprAcbgAcqs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", trprService.selectTrprAcbgAcqs(request, dataRequest));

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectTrprDetailCombo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 1.
	 * @Method설명 : 상세조회 콤보
	 */
	@RequestMapping(value = "/selectTrprDetailCombo.do")
	public View selectTrprDetailCombo(DataRequest dataRequest, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsCerti",
				mgmtCmmnCodeService.selectCommonCodeUnit("CERTI_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsQlfcGrad",
				mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_GRAD_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsEmpymnEra",
				mgmtCmmnCodeService.selectCommonCodeUnit("EMPYMN_ERA_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsEmpymnType",
				mgmtCmmnCodeService.selectCommonCodeUnit("EMPYMN_TYPE_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsDgriAcqsMthd",
				mgmtCmmnCodeService.selectCommonCodeUnit("DGRI_ACQS_MTHD_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsAcbg",
				mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsGrade",
				mgmtCmmnCodeService.selectCommonCodeUnit("GRADE_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsGrdtnStts",
				mgmtCmmnCodeService.selectCommonCodeUnit("GRDTN_STTS_SE_CD", userVo.getUntTaskwk()));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprDetailInfo
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 상세조회
	 */
	@RequestMapping(value = "/selectTrprDetailInfo.do")
	public View selectTrprDetailInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 사례등록 기본정보 조회
		Map<String, List<Map<String, String>>> dsMap = trprService.selectTrprAcbgAcqsDtl(dataRequest);
		Map<String, Object> retMap = trprInqService.selectTrprInqDetail(dataRequest);

		dataRequest.setResponse("dmDetail", retMap.get("dmDetail"));
		dataRequest.setResponse("dsEdu", dsMap.get("dsEdu"));
		dataRequest.setResponse("dsLast", dsMap.get("dsLast"));

		return new JSONDataView();
	}

	/**
	 * @Method명 : trprProcessData
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 데이터 저장
	 */
	@RequestMapping(value = "/trprProcessData.do")
	public View trprProcessData(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

//		Map<String, Object> info = caseRegService.processData(request, dataRequest);
		Map<String, Object> retMap = trprInqService.processTrprInqDetail(request, dataRequest);

		// 재조회시 대상자정보번호(TR) 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("TRPR_INFO_NO", retMap.get("TRPR_INFO_NO"));
		dataRequest.setMetadata(true, message);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprHstrList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 12. 16.
	 * @Method설명 : 대상자이력목록
	 */
	@RequestMapping(value = "/selectTrprHstrList.do")
	public View selectTrprHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> dsList = trprService.selectTrprHstrList(request, paramMap);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

}
