/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.eryycose.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.stats.eryycose.service.EryyCoseService;

/**
 * @파일명 : EryyCoseController.java
 * @프로그램 설명 : 초기진로 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/subms/stats/eryycose")
public class EryyCoseController {

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	MgmtCmmnCodeService mgmtCmmnCodeService;
	// 이주배경 관련 서비스
	@Resource(name = "submsService")
	SubmsService submsService;
	// 초기진로 관련 서비스
	@Resource(name = "eryyCoseService")
	EryyCoseService eryyCoseService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectEryyCoseCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 초기진로 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectEryyCoseCombo.do")
	public View selectEryyCoseCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String,Object>> semstrlistMap = mgmtCmmnCodeService.selectCommonCodeUnit("SEMSTR_SE_CD", userVo.getUntTaskwk());

		Map<String, Object> semstrMap = eryyCoseService.selectSemstrNm(semstrlistMap);
		List<Map<String, Object>> listBizYrCombo = submsService.selectBizYrCombo(request);
		List<Map<String, Object>> listSrvcExcnBizCombo = submsService.selectSrvcExcnBizCombo(request);
		List<Map<String, Object>> listInstNmCombo = submsService.selectInstNmCombo(request);
		List<Map<String, Object>> listOperShape = mgmtCmmnCodeService.selectCommonCodeUnit("OPER_SHAPE_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsSemstr = eryyCoseService.selectExcnBizSemstr();
		List<Map<String, Object>> listResrceNmCombo = submsService.selectResrceNmCombo(request);

		dataRequest.setResponse("dmColNm", semstrMap);
		dataRequest.setResponse("dsBizYr", listBizYrCombo);
		dataRequest.setResponse("dsSrvcExcnBiz", listSrvcExcnBizCombo);
		dataRequest.setResponse("dsOperInst", listInstNmCombo);
		dataRequest.setResponse("dsOperShape", listOperShape);
		dataRequest.setResponse("dsSemstr", dsSemstr);
		dataRequest.setResponse("dsResrce", listResrceNmCombo);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectInstPrgrsPrfmncList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 30.
	 * @Method설명 : 기관별 추진실적 통계목록 조회
	 */
	@RequestMapping(value = "/selectInstPrgrsPrfmncList.do")
	public View selectInstPrgrsPrfmncList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> listBoard = eryyCoseService.selectInstPrgrsPrfmncList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectCharPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 30.
	 * @Method설명 : 연령별 현황 통계목록 조회
	 */
	@RequestMapping(value = "/selectAgePreconList.do")
	public View selectAgePreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

//		List<Map<String, Object>> listBoard = eryyCoseService.selectAgePreconList(request, dataRequest);
		
		//2차통계 수정본 (연령별현황2)
		List<Map<String, Object>> listBoard = eryyCoseService.selectAgePreconList2(request, dataRequest);
		

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectLinkPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 연계현황 통계목록 조회
	 */
	@RequestMapping(value = "/selectLinkPreconList.do")
	public View selectLinkPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

//		List<Map<String, Object>> listBoard = eryyCoseService.selectLinkPreconList(request, dataRequest);
		
		//2차통계 수정본 (연계현황2)
		List<Map<String, Object>> listBoard = eryyCoseService.selectLinkPreconList2(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectBrthNtnPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 : 출생국가별 현황 통계목록 조회
	 */
	@RequestMapping(value = "/selectBrthNtnPreconList.do")
	public View selectBrthNtnPreconList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

//		List<Map<String, Object>> listBoard = eryyCoseService.selectBrthNtnPreconList(request, dataRequest);
		
		//2차통계 수정본 (출생국가별현황2)
		List<Map<String, Object>> listBoard = eryyCoseService.selectBrthNtnPreconList2(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectVisaTypePreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 비자유형별 현황 통계목록 조회
	 */
	@RequestMapping(value = "/selectVisaTypePreconList.do")
	public View selectVisaTypePreconList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

//		List<Map<String, Object>> listBoard = eryyCoseService.selectVisaTypePreconList(request, dataRequest);
		
		//2차통계 수정본 (비자유형현황2)
		List<Map<String, Object>> listBoard = eryyCoseService.selectVisaTypePreconList2(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprTypePreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 대상자유형별현황 통계목록 조회
	 */
	@RequestMapping(value = "/selectTrprTypePreconList.do")
	public View selectTrprTypePreconList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

//		List<Map<String, Object>> listBoard = eryyCoseService.selectTrprTypePreconList(request, dataRequest);
		
		//2차통계 수정본 (대상자유형별현황2)
		List<Map<String, Object>> listBoard = eryyCoseService.selectTrprTypePreconList2(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectGrowthNtnPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 성장국가별현황
	 */
	@RequestMapping(value = "/selectGrowthNtnPreconList.do")
	public View selectGrowthNtnPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		List<Map<String, Object>> listBoard = eryyCoseService.selectGrowthNtnPreconList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
		
	}
	
	/**
	 * 
	 * @Method명   : selectNowNltyPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 현재국적별현황
	 */
	@RequestMapping(value = "/selectNowNltyPreconList.do")
	public View selectNowNltyPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		List<Map<String, Object>> listBoard = eryyCoseService.selectNowNltyPreconList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectAcbgPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 학력별현황
	 */
	@RequestMapping(value = "/selectAcbgPreconList.do")
	public View selectAcbgPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		List<Map<String, Object>> listBoard = eryyCoseService.selectAcbgPreconList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectSxdcPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 성별현황
	 */
	@RequestMapping(value = "/selectSxdcPreconList.do")
	public View selectSxdcPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		List<Map<String, Object>> listBoard = eryyCoseService.selectSxdcPreconList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectTrlSoctyAdaptInspYnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 심리사회적응검사여부
	 */
	@RequestMapping(value = "/selectTrlSoctyAdaptInspYnList.do")
	public View selectTrlSoctyAdaptInspYnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		List<Map<String, Object>> listBoard = eryyCoseService.selectTrlSoctyAdaptInspYnList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
		
	}
	
	/**
	 * @Method명 : selectKlangLevelEvl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 9. 4.
	 * @Method설명 : 한국어평가(레벨테스트)
	 */
	@RequestMapping(value = "/selectKlangLevelEvl.do")
	public View selectKlangLevelEvl(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = eryyCoseService.selectKlangLevelEvl(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectKlangMiddleEvl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 9. 4.
	 * @Method설명 : 한국어평가(중간테스트)
	 */
	@RequestMapping(value = "/selectKlangMiddleEvl.do")
	public View selectKlangMiddleEvl(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = eryyCoseService.selectKlangMiddleEvl(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectKlangSccesdEvl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 9. 4.
	 * @Method설명 : 한국어평가(성취도평가)
	 */
	@RequestMapping(value = "/selectKlangSccesdEvl.do")
	public View selectKlangSccesdEvl(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = eryyCoseService.selectKlangSccesdEvl(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}
}
