/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.DisconnectUserService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : DisconnectUserController.java
 * @프로그램 설명 : 사용자 접속 차단
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 3. 31. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 3. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userlogin")
public class DisconnectUserController extends IsryBaseController {

	@Resource(name = "disconnectUserService")
	private DisconnectUserService disconnectUserService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value="/onLoadDisconnectUser.do")
	@ResponseBody
	public View onLoadDisconnectUser(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		//ScpDb scpDb = new ScpDb();
		//dmSearchMap.put("USER_NM", scpDb.scpEncB64((String)dmSearchMap.get("USER_NM")));
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = disconnectUserService.selectDisconnectUserCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex - 1);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("ROW_SIZE", rowSize);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		dmSearchMap.put("USER_ID2", userId);
		
		dataRequest.setResponse("dsList", disconnectUserService.selectDisconnectUser(dmSearchMap));

		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dmPage", resPage);

		dataRequest.setResponse("dsUserType", mgmtCmmnCodeService.selectCommonCodeUnit("USER_TYPE", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));
		// 기관 유형 추가
		dataRequest.setResponse("dsInstTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getInstTypeSeCd()));
		dataRequest.setResponse("dsUserCntnIntrcpSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USER_CNTN_INTRCP_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}

	@RequestMapping(value="/selectDisconnectUser.do")
	@ResponseBody
	public View selectDisconnectUser(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		//ScpDb scpDb = new ScpDb();
		//dmSearchMap.put("USER_NM", scpDb.scpEncB64((String)dmSearchMap.get("USER_NM")));
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = disconnectUserService.selectDisconnectUserCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex - 1);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("ROW_SIZE", rowSize);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		dmSearchMap.put("USER_ID2", userId);
				
		dataRequest.setResponse("dsList", disconnectUserService.selectDisconnectUser(dmSearchMap));

		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dmPage", resPage);
		
		return new JSONDataView();
	}

	@RequestMapping(value="/saveDisconnectUser.do")
	@ResponseBody
	public View saveDisconnectUser(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		disconnectUserService.saveDisconnectUser(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value="/onloadDisconnectUserReason.do")
	@ResponseBody
	public View onloadDisconnectUserReason(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsUserCntnIntrcpSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USER_CNTN_INTRCP_SE_CD", null));
		
		return new JSONDataView();
	}
	
}
