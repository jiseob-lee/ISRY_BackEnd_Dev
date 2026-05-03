/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.unitystats.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.unitystats.service.UnitystatsService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : UnitystatsController.java
 * @프로그램 설명 : 공통통계 Controller - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 1. 9.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 1. 9.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/itgcm/unitystats")
public class UnitystatsController {

	@Resource(name = "unitystatsService")
	UnitystatsService unitystatsService;
	
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	/**
	 * @Method명 : uneartMngStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 9.
	 * @Method설명 : 1.발굴관리통계
	 */
	@RequestMapping(value = "/uneartMngStatsList.do")
	public View uneartMngStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectUneartMngStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : yngbgsCaseMngStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 9.
	 * @Method설명 : 3.청소년구분별사례관리통계
	 */
	@RequestMapping(value = "/yngbgsCaseMngStatsList.do")
	public View yngbgsCaseMngStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectYngbgsCaseMngStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectProbmSttsCaseMsgStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 4.문제상태별사례관리통계
	 */
	@RequestMapping(value = "/selectProbmSttsCaseMsgStatsList.do")
	public View selectProbmSttsCaseMsgStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectProbmSttsCaseMsgStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명 : selectProbmSttsCaseMsgStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 5.지원서비스통계
	 */
	@RequestMapping(value = "/selectSprtSrvcStatsList.do")
	public View selectSprtSrvcStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectSprtSrvcStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectOutStatsPubmsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2023. 1. 11.
	 * @Method설명 : 6-1.성과통계(학교밖) / 6-2.성과통계(쉼터)
	 */
	@RequestMapping(value = "/selectOutStatsPubmsList.do")
	public View selectOutStatsPubmsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> dsList = unitystatsService.selectOutStatsPubmsList(request, dataRequest);
		
		dataRequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : outStatsPubmtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 6-3.성과통계(자립지원관)
	 */
	@RequestMapping(value = "/outStatsPubmtList.do")
	public View outStatsPubmtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectOutStatsPubmtList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : dscsnOutrcMngStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 9.아웃리치통계(쉼터)
	 */
	@RequestMapping(value = "/dscsnOutrcMngStatsList.do")
	public View dscsnOutrcMngStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectDscsnOutrcMngStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : tlphonDscsnMngStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 11.
	 * @Method설명 : 10.1388전화상담통계(상담복지센터)
	 */
	@RequestMapping(value = "/tlphonDscsnMngStatsList.do")
	public View tlphonDscsnMngStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectTlphonDscsnMngStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : emrgIntrvnMngStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 11.긴급구조통계(지자체안정망,상담복지센터)
	 */
	@RequestMapping(value = "/emrgIntrvnMngStatsList.do")
	public View emrgIntrvnMngStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsList = unitystatsService.selectEmrgIntrvnMngStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectCaseMngBassStatsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectCaseMngBassStatsList.do")
	public View selectCaseMngBassStatsList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> dsList = unitystatsService.selectCaseMngBassStatsList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);
		
		
		return new JSONDataView();
	}
}
