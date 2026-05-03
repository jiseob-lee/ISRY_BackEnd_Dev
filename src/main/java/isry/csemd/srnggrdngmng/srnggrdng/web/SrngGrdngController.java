/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.srnggrdngmng.srnggrdng.web;

import java.util.HashMap;
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

import isry.csemd.cmmn.service.CsemdService;
import isry.csemd.srnggrdngmng.srnggrdng.service.SrngGrdngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : SrngGrdngController.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 10. 4.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 10. 4.
 * @수정내용 : - -
 */
@Controller("csemdSrngGrdngController")
@RequestMapping(value = "/isry/csemd/srnggrdngmng/srnggrdng")
public class SrngGrdngController {

	@Resource(name = "csemdSrngGrdngService")
	private SrngGrdngService srngGrdngService;

	@Resource(name = "csemsSrngGrdngService")
	private isry.csems.srnggrdngmng.srnggrdng.service.SrngGrdngService csemsSrngGrdngService;

	// 드림&디딤 콤보데이터 조회 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 
	 * @Method명 : selectAplyRcptTypeCd
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 : 코드조회
	 */
	@RequestMapping(value = "/selectAplyRcptTypeCd.do")
	public View selectAplyRcptTypeCd(DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsAplyRcptCd", srngGrdngService.selectAplyRcptCd());

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectSrngGrdngList
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 : 면접심사채점표 (디딤) 조회
	 */
	@RequestMapping(value = "/selectSrngGrdngList.do")
	public View selectSrngGrdngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = srngGrdngService.selectScrennList(request, dataRequest);
		List<Map<String, String>> dsGrdng = srngGrdngService.selectGrdngList(request, dataRequest);
		List<Map<String, Object>> dsScrenn = srngGrdngService.selectScrenn(request, dataRequest);
		List<Map<String, String>> dsPtcpt = csemsSrngGrdngService.selectPtcptList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);
		dataRequest.setResponse("dsGrdng", dsGrdng);
		dataRequest.setResponse("dsScrenn", dsScrenn);
		dataRequest.setResponse("dsPtcpt", dsPtcpt);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : saveSrngGrdngPop
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 면접심사채점표 (디딤) 저장
	 */
	@RequestMapping(value = "/saveSrngGrdngPop.do")
	public View saveSrngGrdngPop(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srngGrdngService.saveSrngGrdngPop(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : intrvwSchdlOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 Onload
	 */
	@RequestMapping(value = "/intrvwSchdlOnload.do")
	public View intrvwSchdlOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		// 신청접수심사구분코드
		List<Map<String, Object>> intrvwHrSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("INTRVW_HR_SE_CD",
				loginVO.getUntTaskwk());

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		// 서비스실행사업정보(과정)
		List<Map<String, Object>> srvcExcnBizList = csemdService.selectSrvcExcnBizCmb(requestMap);

		dataRequest.setResponse("dsIntrvwHrSeCd", intrvwHrSeCd);
		dataRequest.setResponse("dsSrvcExcnBizCmb", srvcExcnBizList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectIntrvwSchdlList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 목록 조회
	 */
	@RequestMapping(value = "/selectIntrvwSchdlList.do")
	public View selectIntrvwSchdlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		List<Map<String, Object>> dsList = srngGrdngService.selectIntrvwSchdlList(dmSearch);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : insertIntrvwSchdlMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 등록
	 */
	@RequestMapping(value = "/insertIntrvwSchdlMng.do")
	public View insertIntrvwSchdlMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srngGrdngService.insertIntrvwSchdlMng(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updateIntrvwSchdlMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 5.
	 * @Method설명 : 면접일정관리 수정
	 */
	@RequestMapping(value = "/updateIntrvwSchdlMng.do")
	public View updateIntrvwSchdlMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srngGrdngService.updateIntrvwSchdlMng(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : deleteIntrvwSchdlMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 5.
	 * @Method설명 : 면접일정관리 삭제
	 */
	@RequestMapping(value = "/deleteIntrvwSchdlMng.do")
	public View deleteIntrvwSchdlMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srngGrdngService.deleteIntrvwSchdlMng(dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명   : selectIntrvwAplcntList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접참여자 조회
	 */
	@RequestMapping(value = "/selectIntrvwAplcntList.do")
	public View selectIntrvwAplcntList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> dsIntrvwAplcnt = srngGrdngService.selectIntrvwAplcntList(dataRequest);
		
		dataRequest.setResponse("dsIntrvwAplcnt", dsIntrvwAplcnt);

		return new JSONDataView();
	}

}
