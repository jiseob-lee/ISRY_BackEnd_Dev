/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.mnthngtaskwkrpt.web;


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
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.preconmng.mnthngtaskwkrpt.service.MnthngTaskwkRptService;

/**
 * @파일명        : MnthngTaskWorkRptController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 6. 10. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 6. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/subms/preconmng/mnthngtaskwkrpt")
public class MnthngTaskwkRptController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//월간업무보고 서비스
	@Resource(name="mnthngTaskwkRptService")
	private MnthngTaskwkRptService mnthngTaskwkRptService;
	
	//이주배경 공통 서비스
	@Resource(name="submsService")
	private SubmsService submsService;
	
	// 공통코드 사용하는 데이터 조회하기 위한 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectMnthngTaskwkCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 콤보데이터 조회
	 */
	@RequestMapping("/selectMnthngTaskwkCombo.do")
	public View selectMnthngTaskwkCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsBizYr", submsService.selectBizYrCombo(request));
		dataRequest.setResponse("dsSrvcExcnBiz", submsService.selectSrvcExcnBizCombo(request));
		dataRequest.setResponse("dsResrce", submsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsOperInst", submsService.selectInstNmCombo(request));
		dataRequest.setResponse("dsSemstr", mgmtCmmnCodeService.selectCommonCodeUnit("SEMSTR_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsMm", mgmtCmmnCodeService.selectCommonCodeUnit("MM_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
		
	}
	
	/**
	 * @Method명   : selectMnthngTaskwkList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 월간업무보고 목록조회
	 */
	@RequestMapping("/selectMnthngTaskwkList.do")
	public View selectMnthngTaskwkList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		
		List<Map<String, String>> resultList = mnthngTaskwkRptService.selectMnthngTaskwkList(dataRequest);
		dataRequest.setResponse("dsList", resultList);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectMnthngTaskwk
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 월간업무보고 상세조회
	 */
	@RequestMapping("/selectMnthngTaskwk.do")
	public View selectMnthngTaskwk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		Map<String,List<Map<String, Object>>> responseMap = mnthngTaskwkRptService.selectMnthngTaskwk(request, dataRequest);
		dataRequest.setResponse("dsMnthngOperRpt", responseMap.get("dsMnthngOperRpt"));
		dataRequest.setResponse("dsMnthngOperHrPrecon", responseMap.get("dsMnthngOperHrPrecon"));
		dataRequest.setResponse("dsMnthngAtendLinkPrecon", responseMap.get("dsMnthngAtendLinkPrecon"));
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : selectMnthngTaskwkSearch
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 월간업무보고 상세에서 조건으로 검색
	 */
	@RequestMapping("/selectMnthngTaskwkSearch.do")
	public View selectMnthngTaskwkSearch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		Map<String,List<Map<String, Object>>> responseMap = mnthngTaskwkRptService.selectMnthngTaskwkSearch(request, dataRequest);
		dataRequest.setResponse("dsMnthngOperHrPrecon", responseMap.get("dsMnthngOperHrPrecon"));
		dataRequest.setResponse("dsMnthngAtendLinkPrecon", responseMap.get("dsMnthngAtendLinkPrecon"));
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : saveMnthngTaskwkRpt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 월간업무보고 등록/수정/삭제
	 */
	@RequestMapping("/saveMnthngTaskwkRpt.do")
	public View saveMnthngTaskwkRpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		
		Map<String, Object> returnMap = mnthngTaskwkRptService.saveMnthngTaskwkRpt(request, dataRequest);
		
		dataRequest.setResponse("dmDtlParam", returnMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectCheckResrce
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 : 등록/수정 전 선택한 자원, 서비스 실행사업 등이 각각 맞물려 있는지 체크
	 */
	@RequestMapping("/selectCheckResrce.do")
	public View selectCheckResrce(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		mnthngTaskwkRptService.selectCheckResrce(request, dataRequest);
		
		return new JSONDataView();
	}
}








