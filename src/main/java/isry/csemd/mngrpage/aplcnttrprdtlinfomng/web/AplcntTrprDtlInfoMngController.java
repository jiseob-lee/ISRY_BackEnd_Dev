/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprdtlinfomng.web;

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

import egovframework.com.cmm.service.EgovProperties;
import isry.csemd.cmmn.service.CsemdService;
import isry.csemd.mngrpage.aplcnttrprdtlinfomng.service.AplcntTrprDtlInfoMngService;
import isry.csems.srnggrdngmng.srnggrdng.service.SrngGrdngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : AplcntTrprDtlInfoMngController.java
 * @프로그램 설명 : 사례관리목록[관리자페이지]- -
 * @작성자 : Park.Seuong.Won
 * @작성일 : 2022. 9. 16.
 * @수정자 : Park.Seuong.Won
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */

@Controller("csemdMngrPageAplcntTrprDtlInfoMngController")
@RequestMapping(value = "/isry/csemd/mngrpage/aplcnttrprdtlinfomng")
public class AplcntTrprDtlInfoMngController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	// 입교자외 관리자 서비스
	@Resource(name = "csemdMngrPageAplcntTrprDtlInfoMngService")
	private AplcntTrprDtlInfoMngService aplcntTrprDtlInfoMngService;

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	// 드림&디딤 콤보데이터 조회 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;
	
	@Resource(name = "csemsSrngGrdngService")
	private SrngGrdngService srngGrdngService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 
	 * @Method명 : selectCompnoTypeCd
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 디딤 참가신청서 공통 목록 조회 (관리자)
	 */
	@RequestMapping(value = "/selectCompnoTypeCd.do")
	public View selectCompnoTypeCd(DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsViolnc", csemdService.selectViolnc());
		dataRequest.setResponse("dsDiss", csemdService.selectDiss());
		dataRequest.setResponse("dsLgsltn", csemdService.selectLgsltn());
		dataRequest.setResponse("dsDscsn", csemdService.selectDscsn());
		dataRequest.setResponse("dsMaap", csemdService.selectMaap());
		dataRequest.setResponse("dsEtc", csemdService.selectEtc());
		dataRequest.setResponse("dsGhvrLatent", csemdService.selectGhvrLatent());
		dataRequest.setResponse("dsGhvrOmen", csemdService.selectOmen());
		dataRequest.setResponse("dsProbmGhvr", csemdService.selectProbmGhvr());
		dataRequest.setResponse("dsViolncYn", csemdService.selectViolncYn());
		dataRequest.setResponse("dsSlfijr", csemdService.selectSlfijr());
		dataRequest.setResponse("dsSucde", csemdService.selectSucde());
		dataRequest.setResponse("dsBrhs", csemdService.selectBrhs());
		dataRequest.setResponse("dsNowTakng", csemdService.selectNowTakng());
		dataRequest.setResponse("dsMece", csemdService.selectMece());
		dataRequest.setResponse("dsRprsMaap", csemdService.selectRprsMaap());
		dataRequest.setResponse("dsPblast", csemdService.selectPblast());
		dataRequest.setResponse("dsReside", csemdService.selectReside());
		dataRequest.setResponse("dsFam", csemdService.selectFam());

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectPtcptReqstdAplcntPop
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 참가신청서_신청자용 조회(디딤)
	 */
	@RequestMapping(value = "/selectPtcptReqstdAplcntPop.do")
	public View selectPtcptReqstdAplcntPop(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> info = aplcntTrprDtlInfoMngService.selectPtcptReqstdAplcntPop(request, dataRequest);
		List<Map<String,String>> dsPtcpt = srngGrdngService.selectPtcptList(request, dataRequest);
		List<Map<String, String>> dsFamInfoImsy = aplcntTrprDtlInfoMngService.selectdsFamInfoImsy(request, dataRequest);
		Map<String, String> dmPicPhoto = aplcntTrprDtlInfoMngService.selectTrprAtfino(request, dataRequest);
		
		dataRequest.setResponse("dsList", info);
		dataRequest.setResponse("dsPtcpt", dsPtcpt);
		dataRequest.setResponse("dsFamInfoImsy", dsFamInfoImsy);
		dataRequest.setResponse("dmPicPhoto", dmPicPhoto);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectListCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 목록 콤보
	 */
	@RequestMapping(value = "/selectListCombo.do")
	public View selectListCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", dataRequest.getParameter("AUTHRT_SE_CD"));

		// 사업연도
		List<Map<String, Object>> bizYrList = csemdService.selectBizYrCmb(requestMap);

		// 서비스실행사업번호
		List<Map<String, Object>> srvcExcnBizList = csemdService.selectSrvcExcnBizCmb(requestMap);

		// 배정그룹소분류구분코드 [생활동콤보]
		List<Map<String, Object>> altmntGrpSclas = mgmtCmmnCodeService.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk());

		// 청소년상태대분류
		List<Map<String, Object>> yngbgsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_STTS_LCLAS_SE_CD", loginVO.getUntTaskwk());

		// 청소년상태소분류
		List<Map<String, Object>> yngbgsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_STTS_SCLAS_SE_CD", loginVO.getUntTaskwk());

		// 사례진행상태
		List<Map<String, Object>> casePrgrsStts = mgmtCmmnCodeService.selectCommonCodeUnit("CASE_PRGRS_STTS_SE_CD", loginVO.getUntTaskwk());

		// 사례대상자유형
		List<Map<String, Object>> caseTrprType = mgmtCmmnCodeService.selectCommonCodeUnit("CASE_TRPR_TYPE_SE_CD", loginVO.getUntTaskwk());

		// 성별
		List<Map<String, Object>> sxdc = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", loginVO.getUntTaskwk());

		// 종결구분
		List<Map<String, Object>> trmn = mgmtCmmnCodeService.selectCommonCodeUnit("TRMN_SE_CD", loginVO.getUntTaskwk());

		// 종결사유구분코드
		List<Map<String, Object>> trmnCsSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("TRMN_CS_SE_CD", loginVO.getUntTaskwk());

		// 영역구분
		List<Map<String, Object>> tkcgRelm = mgmtCmmnCodeService.selectCommonCodeUnit("TKCG_RELM_SE_CD", loginVO.getUntTaskwk());

		// 사건사고분류코드
		List<Map<String, Object>> incdntAcdntCl = mgmtCmmnCodeService.selectCommonCodeUnit("INCDNT_ACDNT_CL_SE_CD", loginVO.getUntTaskwk());

		// 관리자확인
		List<Map<String, Object>> MngrIdntyYn = mgmtCmmnCodeService.selectCommonCodeUnit("YES_OR_NO", loginVO.getUntTaskwk());
		
		dataRequest.setResponse("dsBizYr", bizYrList);
		dataRequest.setResponse("dsSrvcExcnBizCmb", srvcExcnBizList);
		dataRequest.setResponse("dsAltmntGroupSclasSeCd", altmntGrpSclas);
		dataRequest.setResponse("dsYngbgsSttsLclasSeCd", yngbgsSttsLclas);
		dataRequest.setResponse("dsYngbgsSttsSclasSeCd", yngbgsSttsSclas);
		dataRequest.setResponse("dsCasePrgrsSttsSeCd", casePrgrsStts);
		dataRequest.setResponse("dsCaseTrprTtpeSeCd", caseTrprType);
		dataRequest.setResponse("dsSxdcSeCd", sxdc);
		dataRequest.setResponse("dsTrmnSeCd", trmn);
		dataRequest.setResponse("dsTkcgRelmSeCd", tkcgRelm);
		dataRequest.setResponse("dsIncdntAcdntClSeCd", incdntAcdntCl);
		dataRequest.setResponse("dsTrmnCsSeCd", trmnCsSeCd);
		dataRequest.setResponse("dsMngrIdntyYn", MngrIdntyYn);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectEnstCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 팝업 콤보(안정도관찰지, 문제행동발생보고서)
	 */
	@RequestMapping(value = "/selectEnstCombo.do")
	public View selectEnstCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", dataRequest.getParameter("AUTHRT_SE_CD"));

		// 사업연도
		List<Map<String, Object>> bizYrList = csemdService.selectBizYrCmb(requestMap);

		// 서비스실행사업명
		List<Map<String, Object>> srvcExcnBizList = csemdService.selectSrvcExcnBizCmb(requestMap);

		// 성별구분
		List<Map<String, Object>> sxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", loginVO.getUntTaskwk());

		// 사건사고분류
		List<Map<String, Object>> incdntAcdntCl = mgmtCmmnCodeService.selectCommonCodeUnit("INCDNT_ACDNT_CL_SE_CD", loginVO.getUntTaskwk());

		// 담당영역
		List<Map<String, Object>> tkcgRelm = mgmtCmmnCodeService.selectCommonCodeUnit("TKCG_RELM_SE_CD", loginVO.getUntTaskwk());

		// 생활동콤보
		List<Map<String, Object>> altmntGrpSclas = mgmtCmmnCodeService.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk());

		// 생활동콤보
		List<Map<String, Object>> trprRel = mgmtCmmnCodeService.selectCommonCodeUnit("TRPR_REL_SE_CD",
				loginVO.getUntTaskwk());

		// 대상자명콤보
		aplcntTrprDtlInfoMngService.selectEnstTrprList(request, dataRequest);

		dataRequest.setResponse("dsBizYr", bizYrList);
		dataRequest.setResponse("dsSrvcExcnBizCmb", srvcExcnBizList);
		dataRequest.setResponse("dsSxdcSeCd", sxdcSeCd);
		dataRequest.setResponse("dsIncdntAcdntClSeCd", incdntAcdntCl);
		dataRequest.setResponse("dsTkcgRelmSeCd", tkcgRelm);
		dataRequest.setResponse("dsAltmntGroupSclasSeCd", altmntGrpSclas);
		dataRequest.setResponse("dsTrprRelSeCd", trprRel);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectRqcpInfoList
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 11. 05.
	 * @Method설명 : 의뢰정보 목록(사례관리 대상자 목록) 조회
	 */
	@RequestMapping(value = "/selectRqcpInfoList.do") 
	public View selectRqcpInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 사례목록 조회
		List<Map<String, Object>> list = aplcntTrprDtlInfoMngService.selectRqcpInfoList(request, dataRequest);
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
		
	}
	/**
	 * @Method명 : selectEnstcRcptInfoList
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 23.
	 * @Method설명 : 입교, 입소접수정보 목록(사례관리 대상자 목록) 조회
	 */
	
	@RequestMapping(value = "/selectEnstcRcptInfoList.do")
	public View selectEnstcRcptInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 사례목록 조회
		List<Map<String, Object>> list = aplcntTrprDtlInfoMngService.selectMainList(request, dataRequest);

		dataRequest.setResponse("dsList", list);
		LOGGER.debug("사례대상자목록22=[" + list + "]");

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDeofstObservList
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 안정도관찰지 목록조회
	 */
	@RequestMapping(value = "/selectDeofstObservList.do")
	public View selectDeofstObservList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사례목록 조회
		List<Map<String, Object>> dsList = aplcntTrprDtlInfoMngService.selectDeofstObservList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveDeofstObserv
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 안정도 관찰지 저장 및 수정
	 */
	@RequestMapping(value = "/saveDeofstObserv.do")
	public View saveDeofstObserv(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aplcntTrprDtlInfoMngService.saveDeofstObserv(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDtlEnstInfo
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 10. 7.
	 * @Method설명 : 안정도관찰지 상세조회
	 */
	@RequestMapping(value = "/selectDeofstObservDtl.do")
	public View selectDeofstObservDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, String> paraMap = dmDtlParam.getSingleValueMap();

		List<Map<String, Object>> list = aplcntTrprDtlInfoMngService.selectDeofstObservDtl(paraMap);

		dataRequest.setResponse("dsList", list);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectProbRepoList
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 문제행동발생 보고서(사례관리 대상자 목록) 조회
	 */
	@RequestMapping(value = "/selectProbRepoList.do")
	public View selectProbRepoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사례목록 조회(업무공통)
		List<Map<String, Object>> list = aplcntTrprDtlInfoMngService.selectProbmList(request, dataRequest);
		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveProbm
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 9. 30.
	 * @Method설명 : 문제행동발생보고서 저장 및 수정
	 */
	@RequestMapping(value = "/saveProbm.do")
	public View saveProbm(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 문제행동발생보고서 저장 및 수정
		aplcntTrprDtlInfoMngService.saveProbm(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDtlProbInfo
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 10. 7.
	 * @Method설명 : 문제행동발생보고서 상세조회
	 */
	@RequestMapping(value = "/selectDtlProbInfo.do")
	public View selectDtlProbInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 문제행동발생보고서 상세조회
		List<Map<String, Object>> list = aplcntTrprDtlInfoMngService.selectDtlProbInfo(request, dataRequest);

		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveDtlProbInfoConfirm
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 문제행동발생보고서 관리자확인
	 */
	@RequestMapping(value = "/saveDtlProbInfoConfirm.do")
	public View saveDtlProbInfoConfirm(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		// 문제행동발생보고서 관리자확인
		aplcntTrprDtlInfoMngService.saveDtlProbInfoConfirm(request, dataRequest);

		return new JSONDataView();

	}

	/**
	 * @Method명 : selectPsycholRepoList
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 심리평가보고서 (사례관리 대상자 목록) 조회
	 */
	@RequestMapping(value = "/selectPsycholRepoList.do")
	public View selectPsycholRepoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사례목록 조회(업무공통)
		List<Map<String, Object>> list = aplcntTrprDtlInfoMngService.selectPsycholRepoList(request, dataRequest);
		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDtlPsycholInfo
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seoung.Won
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 심리평가보고서 상세조회
	 */
	@RequestMapping(value = "/selectDtlPsycholInfo.do")
	public View selectDtlPsycholInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 대상자기본정보
		aplcntTrprDtlInfoMngService.selectDtlPsycholInfo(request, dataRequest);

		// 개별심리검사결과(추후 쿼리 수정 예정)
		aplcntTrprDtlInfoMngService.selectDtlPsycholList(request, dataRequest);

		return new JSONDataView();

	}
}
