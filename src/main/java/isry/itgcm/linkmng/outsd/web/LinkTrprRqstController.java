/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.web;

import java.util.HashMap;
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

import isry.base.IsryBaseController;
import isry.itgcm.linkmng.outsd.service.LinkTrprRqstService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : LinkTrprRqstController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : TaesooSong
 * @작성일        : 2022. 8. 2. 
 * @수정자        : TaesooSong
 * @수정일        : 2022. 8. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcm/linkmng/outsd")
public class LinkTrprRqstController  extends IsryBaseController {
	
	@Resource(name = "linkTrprRqstService")
	private LinkTrprRqstService linkTrprRqstService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/onLoadLinkTrprRqst.do")
	public View onLoadLinkTrprRqst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		Map<String, Object> result = linkTrprRqstService.onLoadLinkTrprRqst(request, dataRequest);
		
		dataRequest.setResponse("dmSrvcPvsnRqst", result.get("param1"));
		dataRequest.setResponse("dmBaseParam", result.get("dmBaseParam"));
		dataRequest.setResponse("dsRcptList", result.get("dsRcptList"));
		dataRequest.setResponse("dsCaseMngHisList", result.get("dsCaseMngHisList"));
		dataRequest.setResponse("dsSrvcPvsnHisList", result.get("dsSrvcPvsnHisList"));
		//dataRequest.setResponse("dsLinkType", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD")); //공통코드 상태값 목록
		//dataRequest.setResponse("dsLinkType", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		//dataRequest.setResponse("dsLinkUntTaskwk", mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_UNT_TASKWK_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		//dataRequest.setResponse("dsRcpt", mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsSrvcPvsnRqstList", result.get("dsSrvcPvsnRqstList"));
		// 2022-12-22 공통코드 가져오는 로직 수정
		List<Map<String, String>> allRowList = dataRequest.getParameterGroup("dsCommParam").getAllRowList();
		log.info("allRowList size={}", allRowList.size() );
		
		for (Map<String, String> map : allRowList) {
			log.info("[for]  CMMNS_ID={}, DS_NAME={}", map.get("CMMNS_ID"), map.get("DS_NAME") );
			dataRequest.setResponse(map.get("DS_NAME"), mgmtCmmnCodeService.selectCommonCodeUnit(map.get("CMMNS_ID"),userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectLinkTrprRqst.do")
	public View selectLinkTrprRqst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		Map<String, Object> result = linkTrprRqstService.selectLinkTrprRqst(request, dataRequest); 
		
		dataRequest.setResponse("dmSrvcPvsnRqst", result.get("dmSrvcPvsnRqst"));
		dataRequest.setResponse("dmTrprInq", result.get("dmTrprInq"));
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/saveLinkTrprRqst.do")
	public View saveLinkTrprRqst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		Map<String, Object> result = linkTrprRqstService.saveLinkTrprRqst(request, dataRequest);
		
		dataRequest.setResponse("dmSrvcPvsnRqst", result.get("dmSrvcPvsnRqst"));
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/onLoadLinkTrprRqstList.do")
	public View onLoadLinkTrprRqstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//dataRequest.setResponse("dsRcpt", mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		//dataRequest.setResponse("dsLinkType", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		//dataRequest.setResponse("dsLinkTypeList", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		// 2022-12-22 공통코드 가져오는 로직 수정
		List<Map<String, String>> allRowList = dataRequest.getParameterGroup("dsCommParam").getAllRowList();
		log.info("allRowList size={}", allRowList.size() );
		
		for (Map<String, String> map : allRowList) {
			log.info("[for]  CMMNS_ID={}, DS_NAME={}", map.get("CMMNS_ID"), map.get("DS_NAME") );
			dataRequest.setResponse(map.get("DS_NAME"), mgmtCmmnCodeService.selectCommonCodeUnit(map.get("CMMNS_ID"),userVo.getUntTaskwk()));
		}
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectLinkTrprRqstList.do")
	public View selectLinkTrprRqstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		Map<String, Object> result = linkTrprRqstService.selectLinkTrprRqstList(request, dataRequest);
		
		dataRequest.setResponse("dsList", result.get("dsList"));
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/executeLinkTrprRqst.do")
	public View executeLinkTrprRqst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		Map<String, Object> result = linkTrprRqstService.executeLinkTrprRqst(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/onLoadLinkTrprRcpt.do")
	public View onLoadLinkTrprRcpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwkSeCd = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			untTaskwkSeCd = loginVO.getUntTaskwk();
		}
		dataRequest.setResponse("dsLinkType", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsRcpt", mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsRjctCsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RJCT_CS_SE_CD", untTaskwkSeCd)); //공통코드 상태값 목록
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/onLoadLinkTrprRcptList.do")
	public View onLoadLinkTrprRcptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsRcpt", mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsLinkTypeList", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectLinkTrprRcptList.do")
	public View selectLinkTrprRcptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		//Map<String, Object> result = linkTrprRqstService.selectLinkTrprRcptList(request, dataRequest);
		//dataRequest.setResponse("dsList", result.get("dsList"));
		
		Map<String, Object> result =  linkTrprRqstService.selectLinkTrprRcptPagingList(request, dataRequest);
		
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectLinkTrprRcpt.do")
	public View selectLinkTrprRcpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		Map<String, Object> result = linkTrprRqstService.selectLinkTrprRcpt(request, dataRequest);
		
		dataRequest.setResponse("dmSrvcPvsnRqst", result.get("dmSrvcPvsnRqst"));
		dataRequest.setResponse("dmTrprInq", result.get("dmTrprInq"));
		dataRequest.setResponse("dsList", result.get("rcptList"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/saveLinkTrprRcpt.do")
	public View saveLinkTrprRcpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		Map<String, Object> result = linkTrprRqstService.saveLinkTrprRcpt(request, dataRequest);
		
		dataRequest.setResponse("dsList", result.get("rcptList"));
		dataRequest.setResponse("dmSrvcPvsnRcpt", result.get("dmSrvcPvsnRcpt"));
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/executeLinkTrprRcpt.do")
	public View executeLinkTrprRcpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		Map<String, Object> result = linkTrprRqstService.executeLinkTrprRcpt(request, dataRequest);
		if(result.get("rcptList") != null) {
			dataRequest.setResponse("dsList", result.get("rcptList"));
			//dataRequest.setResponse("dmSrvcPvsnRcpt", result.get("dmSrvcPvsnRcpt"));
		}
		
		return new JSONDataView();
	}
//	//병무청 연계접수
//	@RequestMapping(value = "/selectLinkMmatList.do")
//	public View selectLinkMmatList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
//		List<Map<String, Object>> result = linkTrprRqstService.selectLinkMmatList(request, dataRequest);
//		dataRequest.setResponse("dsList", result);
//		return new JSONDataView();
//	}
	
	@RequestMapping(value = "/deleteLinkTrprRqst.do")
	public View deleteLinkTrprRqst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		linkTrprRqstService.deleteLinkTrprRqst(request, dataRequest);
		
		return new JSONDataView();
	}
}