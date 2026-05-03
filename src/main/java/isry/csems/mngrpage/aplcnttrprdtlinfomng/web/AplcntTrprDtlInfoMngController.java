/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.mngrpage.aplcnttrprdtlinfomng.web;

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

import isry.csems.cmmn.service.CsemsService;
import isry.csems.mngrpage.aplcnttrprdtlinfomng.service.AplcntTrprDtlInfoMngService;
import isry.csems.srnggrdngmng.srnggrdng.service.SrngGrdngService;

/**
 * @파일명        : AplcntTrprDtlInfoMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 5. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller("csemsMngrPageAplcntTrprDtlInfoMngController")
@RequestMapping(value = "/isry/csems/mngrpage/aplcnttrprdtlinfomng")
public class AplcntTrprDtlInfoMngController {

	@Resource(name = "csemsMngrPageAplcntTrprDtlInfoMngService")
	private AplcntTrprDtlInfoMngService aplcntTrprDtlInfoMngService;
		
	@Resource(name = "csemsService")
	private CsemsService csemsService;
	
	@Resource(name = "csemsSrngGrdngService")
	private SrngGrdngService srngGrdngService;
	
	/**
	 * 
	 * @Method명   : selectCompnoTypeCd
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 참가신청서 공통 목록 조회(드림)
	 */
	@RequestMapping(value = "/selectCompnoTypeCd.do")
	public View selectCompnoTypeCd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		dataRequest.setResponse("dsLgsltn", csemsService.selectLgsltn());
		dataRequest.setResponse("dsDscsn", csemsService.selectDscsn());
		dataRequest.setResponse("dsDiss", csemsService.selectDiss());
		dataRequest.setResponse("dsPrtcr", csemsService.selectPrtcr());
		dataRequest.setResponse("dsProbmRelm", csemsService.selectProbmRelm());
		dataRequest.setResponse("dsSmkng", csemsService.selectSmkng());
		dataRequest.setResponse("dsDrnkg", csemsService.selectDrnkg());
		dataRequest.setResponse("dsTeachr", csemsService.selectTeachr());
		dataRequest.setResponse("dsFrid", csemsService.selectFrid());
		dataRequest.setResponse("dsSocty", csemsService.selectSocty());
		dataRequest.setResponse("dsFridCnt", csemsService.selectFridCnt());
		dataRequest.setResponse("dsDevlpa", csemsService.selectDevlpa());
		dataRequest.setResponse("dsViolnc", csemsService.selectViolnc());
		dataRequest.setResponse("dsSlfijr", csemsService.selectSlfijr());
		dataRequest.setResponse("dsSucde", csemsService.selectSucde());
		dataRequest.setResponse("dsNowTakng", csemsService.selectNowTakng());
		dataRequest.setResponse("dsTrl", csemsService.selectTrl());
		dataRequest.setResponse("dsRprsMaap", csemsService.selectRprsMaap());
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectPtcptReqstdAplcntPop
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 참가신청서_신청자용 조회(드림)
	 */
	@RequestMapping(value = "/selectPtcptReqstdAplcntPop.do")
	public View selectPtcptReqstdAplcntPop(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		List<Map<String, Object>> info = aplcntTrprDtlInfoMngService.selectPtcptReqstdAplcntPop(request, dataRequest);
		List<Map<String,String>> dsPtcpt = srngGrdngService.selectPtcptList(request, dataRequest);
		
		dataRequest.setResponse("dsList", info);
		dataRequest.setResponse("dsPtcpt", dsPtcpt);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : savePtcptReqstdAplcntPop
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 참가신청서_신청자용 저장(드림)
	 */
	@RequestMapping(value = "/savePtcptReqstdAplcntPop.do")
	public View savePtcptReqstdAplcntPop(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {

		aplcntTrprDtlInfoMngService.savePtcptReqstdAplcntPop(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명   : selectAdhrncWrtcns
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 드림 참가자동의서 조회
	 */
	@RequestMapping(value = "/selectAdhrncWrtcns.do")
	public View selectAdhrncWrtcns(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	throws Exception{
		
		List<Map<String, String>> info = aplcntTrprDtlInfoMngService.selectAdhrncWrtcns(request, dataRequest);
		
		dataRequest.setResponse("dsWrtcns", info);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : saveAdhrncWrtcns
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 드림 참가자동의서 저장 
	 */
	@RequestMapping(value = "/saveAdhrncWrtcns.do")
	public View saveAdhrncWrtcns(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		aplcntTrprDtlInfoMngService.saveAdhrncWrtcns(request, dataRequest);
		
		return new JSONDataView();
	}
}
