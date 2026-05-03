/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.prtpntexmn.web;

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
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.preconmng.prtpntexmn.service.PrtpntExmnService;

/**
 * @파일명        : PrtpntExmnController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 5. 18. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 5. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("isry/subms/preconmng/prtpntexmn")
public class PrtpntExmnController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//참여자조사표 서비스
	@Resource(name = "prtpntExmnService")
	private PrtpntExmnService prtpntExmnService;
	
	//공통코드 사용하는 데이터 조회하기 위한 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	//콤보데이터 조회하는 데이터를 위한 서비스
	@Resource(name = "submsService")
	private SubmsService submsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 
	 * @Method명   : selectPrtpntExmnCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 2. 
	 * @Method설명 : 로딩시 콤보데이터 조회
	 */
	@RequestMapping("/selectPrtpntExmnCombo.do")
	public View selectPrtpntExmnCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception{
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//코드를 이름으로 나타내기 위한 모든 코드들 조회
		dataRequest.setResponse("dsOperInst", submsService.selectInstNmCombo(request));
		dataRequest.setResponse("dsSrvcExcnBiz", submsService.selectSrvcExcnBizCombo(request));
		dataRequest.setResponse("dsResrce", submsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsBizYr", submsService.selectBizYrCombo(request));
		dataRequest.setResponse("middleEvfoKnd", mgmtCmmnCodeService.selectCommonCodeUnit("MIDDLE_EVFO_KND_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("sccesdEvfoKnd", mgmtCmmnCodeService.selectCommonCodeUnit("SCCESD_EVFO_KND_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsOperShapeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OPER_SHAPE_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectPrtpntExmnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 참여자조사표 목록조회
	 */
	@RequestMapping("/selectPrtpntExmnList.do")
	public View selectPrtpntExmnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception{
		List<Map<String, String>> result = prtpntExmnService.selectPrtpntExmnList(request, dataRequest);
		
		dataRequest.setResponse("dsList", result);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : savePrtpntExmn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 참여자조사표 등록/수정
	 */
	@RequestMapping("/savePrtpntExmn.do")
	public View savePrtpntExmn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
		throws Exception{
		
		prtpntExmnService.savePrtpntExmn(request, dataRequest);
		
		return new JSONDataView();
	}
}



