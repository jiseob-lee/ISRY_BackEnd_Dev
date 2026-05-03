/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.web;

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

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.ytosp.portalmng.menuandprogrm.service.YtospInqProgListService;

/**
 * @파일명        : YtospInqProgListController.java
 * @프로그램 설명 : 포털 프로그램 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2023. 9. 6. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2023. 9. 6.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/ytosp/portalmng/memuandprogrm")
public class YtospInqProgListController {
	
	@Resource(name = "ytospInqProgListService")
	private YtospInqProgListService ytospInqProgListService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@RequestMapping(value = "/selectProgram.do")
	public View selectProgram(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> list = ytospInqProgListService.selectProgram(dataRequest);

		dataRequest.setResponse("dsEndPoints", list);
		
		// 고민 주제
		dataRequest.setResponse("dsGriefThmaSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GRIEF_THMA_SE_CD", userVo.getUntTaskwk()));
		
		// 프로그램 사용 구분 코드
		dataRequest.setResponse("dsProgrmUseSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("PROGRM_USE_SE_CD", userVo.getUntTaskwk()));
		
		// 레이아웃 구분 코드
		dataRequest.setResponse("dsLayoutSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LAYOUT_SE_CD", userVo.getUntTaskwk()));
		
		
		//dataRequest.setResponse("dsProgramStatus", mgmtCmmnCodeService.selectCommonCodeUnit("PROGRM_USE_SE_CD", userVo.getUntTaskwk()));
		//dataRequest.setResponse("dsExternal", mgmtCmmnCodeService.selectCommonCodeUnit("IS_EXTERNAL_PROGRAM", userVo.getUntTaskwk()));
		//dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());
		//dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}

}
