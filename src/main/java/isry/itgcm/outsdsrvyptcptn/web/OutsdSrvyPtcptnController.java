/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.outsdsrvyptcptn.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.UIView;

import isry.itgcms.util.StringUtil;

/**
 * @파일명        : OutsdSrvyPtcptnController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 11. 21. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 11. 21.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/outsdsrvyptcptn")
public class OutsdSrvyPtcptnController {

	
	/**
	 * @Method명 : outsdSrvyPtcptnWrite
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 모바일버전 설문작성화면
	 */
	@RequestMapping(value = "/outsdSrvyPtcptnWrite.do")
	public View outsdSrvyPtcptnWrite(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String userId = StringUtil.nullConvert(request.getParameter("userId"));
		String qustnbMngNo = StringUtil.nullConvert(request.getParameter("qustnbMngNo"));
		String untTaskwkSeCd = StringUtil.nullConvert(request.getParameter("untTaskwkSeCd"));
		String sxdcSeCd = StringUtil.nullConvert(request.getParameter("sxdcSeCd"));
		String mngrYn = StringUtil.nullConvert(request.getParameter("mngrYn"));
		String cntnCours = StringUtil.nullConvert(request.getParameter("cntnCours"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("USER_ID", userId);
		paramMap.put("QUSTNB_MNG_NO", qustnbMngNo);
		paramMap.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
		paramMap.put("SXDC_SE_CD", sxdcSeCd);
		paramMap.put("MNGR_YN", mngrYn);
		paramMap.put("CNTN_COURS", cntnCours);
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		String deployPath = pathList.get(0);
		String pageUrl  = deployPath + "/";
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
		//pageUrl += "app/csemd/aplcntpage/aplcnttrprmng/SurvshtDtlPopup2.clx";
		pageUrl += "app/itgcm/outsdsrvyptcptn/CmmnsSurvshtDtlPopup.clx";
		
		return new UIView(pageUrl, paramMap);

	}
	
}
