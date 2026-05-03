/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.statsmgmt.stats.web;

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
import com.cleopatra.spring.JSONDataView;

import isry.aimns.statsmgmt.stats.service.StatsService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : StatsController.java
 * @프로그램 설명 : 통계관리 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 7. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 7. 11.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/aimns/statsmgmt/stats")
public class StatsController {

	// 통계관리 서비스
	@Resource(name = "statsService")
	StatsService statsService;
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	MgmtCmmnCodeService mgmtCmmnCodeService;
	// 콤보데이터 관련 서비스
	@Resource(name = "submsService")
	SubmsService aimnsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectStatusCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 12.
	 * @Method설명 : 통계관리 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectStatusCombo.do")
	public View selectStatusCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		List<Map<String, Object>> dsBizYr = aimnsService.selectBizYrCombo(request);
		List<Map<String, Object>> dsInst = aimnsService.selectInstNmCombo(request);
		List<Map<String, Object>> dsResrce = aimnsService.selectResrceNmCombo(request);
		List<Map<String, Object>> dsYngbgsSttsLclas = mgmtCmmnCodeService
				.selectCommonCodeUnit("YNGBGS_STTS_LCLAS_SE_CD", loginVO.getUntTaskwk());

		dataRequest.setResponse("dsBizYr", dsBizYr);
		dataRequest.setResponse("dsInst", dsInst);
		dataRequest.setResponse("dsResrce", dsResrce);
		dataRequest.setResponse("dsYngbgsSttsLclas", dsYngbgsSttsLclas);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrprDetailStatusStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 12.
	 * @Method설명 : 교육생 세부현황 목록조회
	 */
	@RequestMapping(value = "/selectTrprDetailStatusStatsList.do")
	public View selectTrprDetailStatusStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, String>> dsList = statsService.selectTrprDetailStatusStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selecMonthExecStatusStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 22.
	 * @Method설명 : 월별실시현황 목록조회
	 */
	@RequestMapping(value = "/selectMonthExecStatusStatsList.do")
	public View selectMonthExecStatusStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, String>> dsList = statsService.selectMonthExecStatusStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectRecruitStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 모집통계 목록조회
	 */
	@RequestMapping(value = "/selectRecruitStatsList.do")
	public View selectRecruitStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		statsService.selectRecruitStatsList(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectRecruitStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 진행통계 목록조회
	 */
	@RequestMapping(value = "/selectProgressStatsList.do")
	public View selectProgressStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		statsService.selectProgressStatsList(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectRecruitStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 연령별 인원 현황
	 */
	@RequestMapping(value = "/selectTrprNumberOfAgeList.do")
	public View selectTrprNumberOfAgeList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		statsService.selectTrprNumberOfAgeList(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectReportStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 통계보고서
	 */
	@RequestMapping(value = "/selectReportStatsList.do")
	public View selectReportStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		statsService.selectReportStatsList(request, dataRequest);
		return new JSONDataView();
	}
}
