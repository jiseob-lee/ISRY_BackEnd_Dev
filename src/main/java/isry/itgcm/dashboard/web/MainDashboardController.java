/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.dashboard.web;

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

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.dashboard.service.MainDashboardService;
import isry.itgcms.itgBrd.service.ItgBrdCmnService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.wrksupt.docsr.service.DocsrService;
import isry.redis.service.RedisService;

/**
 * @파일명        : MainDashboardController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 11. 08. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 11. 08.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcm/dashboard")
public class MainDashboardController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Autowired
	private ItgBrdCmnService itgBrdCmnService;
	
	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Autowired
	private MainDashboardService mainDashboardService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method     : selectMainDashboard
	 * @Method설명 : dashboar 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 11. 08.
 	 */	
	@RequestMapping(value = "/selectMainDashboard.do")
	public View selectMainDashboard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> list = mainDashboardService.selectMainDashboard(request, dataRequest);
		dataRequest.setResponse("dsList", list);
		
		List<Map<String, Object>> CodeList = mgmtCmmnCodeService.selectCommonCodeUnit("CTPV_SE_CD", loginVO == null ? "" : loginVO.getUntTaskwk());
		dataRequest.setResponse("dsRgnList", CodeList);
		
		List<Map<String, Object>> rcptCodeList = mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_UNT_TASKWK_SE_CD", loginVO == null ? "" : loginVO.getUntTaskwk());
		dataRequest.setResponse("dsRcptUntTaskwkSeCd", rcptCodeList);
		
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		Map<String,String> param = dmBase.getSingleValueMap();
		String untTaskwkSeCd = param.get("UNT_TASKWK_SE_CD");
		
		LOGGER.debug("parma.get = [" + untTaskwkSeCd + "]" );
		LOGGER.debug("getUntTaskwk = [" + (loginVO == null ? "" : loginVO.getUntTaskwk()) + "]" );
		LOGGER.debug("getUntTaskwkSeCd = [" + (loginVO == null ? "" : loginVO.getUntTaskwkSeCd()) + "]" );
		
		if("".equals(untTaskwkSeCd) || untTaskwkSeCd == null) {
			untTaskwkSeCd = loginVO.getUntTaskwk();
		}
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
		mapParam.put("START_IDX", 0);
		mapParam.put("ROW_COUNT", 5);
		mapParam.put("UNITY_BBSCTT_CTGRYB_SE_CD", "");
		mapParam.put("PSTG_OPEN_YN", "Y");
		mapParam.put("START_DATE", param.get("START_DATE"));
		mapParam.put("END_DATE", param.get("END_DATE"));
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "001"); // 공지사항
		List<Map<String, Object>> noticeList = itgBrdCmnService.selectItgCmnBrdImprtnList(mapParam);
		dataRequest.setResponse("dsNotice", noticeList);

		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "002"); // 자료실
		List<Map<String, Object>> referenceList = itgBrdCmnService.selectItgCmnBrdList(mapParam);
		dataRequest.setResponse("dsReference", referenceList);
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "003"); // 지역별정보공유
		mapParam.put("ITG_SEARCH_YN", "Y");
		List<Map<String, Object>> rgnNtcList = itgBrdCmnService.selectItgCmnBrdList(mapParam);
		dataRequest.setResponse("dsRgnNtc", rgnNtcList);
		
		List<Map<String, Object>> result = mainDashboardService.selectDocsCommonList(request, dataRequest);
		dataRequest.setResponse("dsDocRcptn", result);
		
		List<Map<String, Object>> emlResult = mainDashboardService.selectInnerEmlList(request, dataRequest);
		dataRequest.setResponse("dsInnerEmlRcptn", emlResult);
		
		int mogefCnt = 0;		// 여가부
		int wlfarCnt = 0;	// 복지부
		int moeCnt = 0;	// 교육부
		int policeCnt = 0; // 경찰청
		int mmaCnt = 0; 	// 병무청
		List<Map<String, Object>> linkList = mainDashboardService.selectLinkList(request, dataRequest);
		for(Map<String, Object> map : linkList) {
			if("06".equals(map.get("LINK_TYPE_SE_CD"))) { // 교육부
				moeCnt = Integer.parseInt(map.get("LINK_CNT").toString());
			} else if("05".equals(map.get("LINK_TYPE_SE_CD"))) { // 경찰청
				policeCnt = Integer.parseInt(map.get("LINK_CNT").toString());
			} else if("04".equals(map.get("LINK_TYPE_SE_CD"))) { // 병무청
				mmaCnt = Integer.parseInt(map.get("LINK_CNT").toString());
			} else if("01".equals(map.get("LINK_TYPE_SE_CD"))) { // 여가부
				mogefCnt = Integer.parseInt(map.get("LINK_CNT").toString());
			}
		}
		
		List<Map<String, Object>> wlfarLinkList = mainDashboardService.selectWlfarLinkList(request, dataRequest);
		wlfarCnt = wlfarLinkList.size();
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("MOGEF", Integer.toString(mogefCnt));
		resultMap.put("WLFAR", Integer.toString(wlfarCnt));
		resultMap.put("MOE", Integer.toString(moeCnt));
		resultMap.put("POLICE", Integer.toString(policeCnt));
		resultMap.put("MMA", Integer.toString(mmaCnt));
		dataRequest.setResponse("dmLink", resultMap);
		
		// 권한 적용으로 메인화면과 사례화면 카운트 차이 발생. 2023.03.23 윤희성 추가
		//Map<String, Object> caseMap = mainDashboardService.selectCaseList(request, dataRequest);
		//dataRequest.setResponse("dmCase", caseMap);
		Map<String, Object> caseMap = mainDashboardService.selectDca010(request);
		dataRequest.setResponse("dmCase", caseMap);
		
		// 사례관리 메뉴 ID조회
		Map<String, Object> menuMap = mainDashboardService.selectMenuId(untTaskwkSeCd);
		dataRequest.setResponse("dmMenuNo", menuMap);
		
		// 사용자 직위구분코드 조회
		Map<String, Object> jbpsMap = mainDashboardService.selectJbps(request, dataRequest);
		dataRequest.setResponse("dmJbpsSeCd", jbpsMap);
				
		return new JSONDataView();
	}

	/**
	 * @Method     : selectCaseCnt
	 * @Method설명 : dashboar 사례 건수 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 11. 08.
 	 */	
	@RequestMapping(value = "/selectCaseCnt.do")
	public View selectCaseCnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> caseMap = mainDashboardService.selectCaseList(request, dataRequest);
		mainDashboardService.saveDca010(request, caseMap);
		
		dataRequest.setResponse("dmCase", caseMap);
		
		return new JSONDataView();
	}
}
