/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.uneartmng.dgnssrcpt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.uneartmng.dgnssrcpt.service.DgnssRcptService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : DgnssRcptController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/cysns/uneartmng/dgnssrcpt")
public class DgnssRcptController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dgnssRcptService")
	private DgnssRcptService dgnssRcptService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/onLoadLinkTrprRcptList.do")
	public View onLoadLinkTrprRcptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsRcpt", mgmtCmmnCodeService.selectCommonCodeUnit("RCPT_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsLinkTypeList", mgmtCmmnCodeService.selectCommonCodeUnit("LINK_TYPE_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsSxDcSe", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		dataRequest.setResponse("dsIntrvnStts", mgmtCmmnCodeService.selectCommonCodeUnit("INTRVN_STTS_SE_CD", loginVO.getUntTaskwk())); //공통코드 상태값 목록
		
		return new JSONDataView();
	}
	

	@RequestMapping(value = "/selectLinkTrprRcptList.do")
	public View selectLinkTrprRcptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
//		Map<String, Object> result = dgnssRcptService.selectLinkTrprRcptList(request, dataRequest);
		Map<String, Object> result = dgnssRcptService.selectLinkRcptPagingList(request, dataRequest);
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPageInfo", result.get("dmPageInfo"));
		
		return new JSONDataView();
	}
	
	
	@RequestMapping("/selectRelaInstById.do")
	public View selectRelaInstById(DataRequest dataRequest) throws Exception {
		
		//유관기관진단조사
		List<Map<String, String>> result = dgnssRcptService.selectRelaInstById(dataRequest);
		dataRequest.setResponse("dsList", result);

		if (result.size() > 0) {
			String dgnssExmnMngNo = result.get(0).get("DGNSS_EXMN_MNG_NO");
			dataRequest.getParameterGroup("dmListParam").setValue(0, "DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);  //진단조사관리번호

			dataRequest.setResponse("dsDgnssScoreList", dgnssRcptService.selectDgnssScoreList(dataRequest));
			dataRequest.setResponse("dsInfantChilList", dgnssRcptService.selectInfantChilList(dataRequest));
			dataRequest.setResponse("dsCyberGambleList", dgnssRcptService.selectCyberGambleList(dataRequest));
			dataRequest.setResponse("dsRcptHstrList", dgnssRcptService.selectLinkTrprRcptHistory(dataRequest));

		}	
		
		//selectLinkTrprRcptHistory
		return new JSONDataView();
	}

	@RequestMapping("/updateRelaInstData.do")
	public View updateRelaInstData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		dgnssRcptService.updateRelaInstData(request, dataRequest);

	    return new JSONDataView();
	}

	@RequestMapping("/selectSchlDgnssById.do")
	public View selectSchlDgnssById(DataRequest dataRequest) throws Exception {
		//학교진단
		List<Map<String, String>> result = dgnssRcptService.selectSchlDgnssById(dataRequest);
		dataRequest.setResponse("dsList", result);

		if (result.size() > 0) {
			String dgnssExmnMngNo = result.get(0).get("DGNSS_EXMN_MNG_NO");
			dataRequest.getParameterGroup("dmListParam").setValue(0, "DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);  //진단조사관리번호

			dataRequest.setResponse("dsDgnssScoreList", dgnssRcptService.selectDgnssScoreList(dataRequest));
			dataRequest.setResponse("dsInfantChilList", dgnssRcptService.selectInfantChilList(dataRequest));
			dataRequest.setResponse("dsCyberGambleList", dgnssRcptService.selectCyberGambleList(dataRequest));
			dataRequest.setResponse("dsRcptHstrList", dgnssRcptService.selectLinkTrprRcptHistory(dataRequest));

		}	

		return new JSONDataView();
	}
	
	@RequestMapping("/updateSchlDgnssData.do")
	public View updateSchlDgnssData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		dgnssRcptService.updateSchlDgnssData(request, dataRequest);
		
		return new JSONDataView();
	}
	

}
