/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.web;

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

import isry.base.IsryBaseController;
import isry.itgcm.linkmng.outsd.service.LinkMohwSrvcRqstService;

/**
 * @파일명        : LinkMohwSrvcRqstController.java
 * @프로그램 설명 : 복지부 연계서비스 의뢰
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 29. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcm/linkmng/outsd")
public class LinkMohwSrvcRqstController extends IsryBaseController {
	
	@Resource(name = "linkMohwSrvcRqstService")
	private LinkMohwSrvcRqstService linkMohwSrvcRqstService;			/* 복지부 서비스의뢰 Service*/
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 : 복지부 서비스의뢰접수 목록
	 */
	@RequestMapping(value = "/selectMohwSrvcRqstRcptList.do")
	public View selectMohwSrvcRqstRcptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> list = linkMohwSrvcRqstService.selectMohwSrvcRqstRcptList(request, dataRequest);
		dataRequest.setResponse("dsCAB100", list);
	
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰 접수정보 조회
	 */
	@RequestMapping(value = "/selectMohwSrvcRqstRcptInfo.do")
	public View selectMohwSrvcRqstRcptInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> retList1  = linkMohwSrvcRqstService.selectMohwSrvcRqstRcptInfo(request, dataRequest);
		List<Map<String, Object>> retList2  = linkMohwSrvcRqstService.selectMohwSrvcRqstRcptInfoResultList(request, dataRequest);
		dataRequest.setResponse("dsCAB100"     , retList1);
		dataRequest.setResponse("dsCAB110"     , retList2);
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 : 복지부 서비스의뢰접수결과정보 조회
	 */
	@RequestMapping(value = "/selectMohwSrvcRqstRcptInfoResultInfo.do")
	public View selectMohwSrvcRqstRcptInfoResultInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> retList  = linkMohwSrvcRqstService.selectMohwSrvcRqstRcptInfoResultInfo(request, dataRequest);
		dataRequest.setResponse("dsCAB110Save", retList);
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : processMohwSrvcRqstRcpt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수처리
	 */
	@RequestMapping(value = "/processMohwSrvcRqstRcpt.do")
	public View processMohwSrvcRqstRcpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		 Map<String, Object> retMap = linkMohwSrvcRqstService.processMohwSrvcRqstRcpt(request, dataRequest);
		 dataRequest.setResponse("dmSearch", retMap);
		 
		 dataRequest.setMetadata(true, retMap);
		 
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndResultList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부서비스의뢰요청 목록
	 */
	@RequestMapping(value = "/selectMohwSrvcRqstDmndList.do")
	public View selectMohwSrvcRqstDmndResultList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> list = linkMohwSrvcRqstService.selectMohwSrvcRqstDmndList(request, dataRequest);
		dataRequest.setResponse("dsCAB120", list);
	
		return new JSONDataView();
	}
	/**
	 * 
	 * @Method명   : selectMohwSrvcRqstDmndInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 서비스의뢰요청정보 조회
	 */
	@RequestMapping(value = "/selectMohwSrvcRqstDmndInfo.do")
	public View selectMohwSrvcRqstDmndInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> retList1 = linkMohwSrvcRqstService.selectMohwSrvcRqstDmndInfo(request, dataRequest);
		List<Map<String, Object>> retList2 = linkMohwSrvcRqstService.selectMohwSrvcRqstDmndInfoResultList(request, dataRequest);
		dataRequest.setResponse("dsCAB120Save", retList1);
		dataRequest.setResponse("dsCAB130"    , retList2);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : processMohwSrvcRqstDmnd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청처리
	 */
	@RequestMapping(value = "/processMohwSrvcRqstDmnd.do")
	public View processMohwSrvcRqstDmnd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{

		Map<String, Object> retMap = linkMohwSrvcRqstService.processMohwSrvcRqstDmnd(request, dataRequest);
		dataRequest.setResponse("dmSearch", retMap);

		return new JSONDataView();
	}

	/**
	 * @Method명   : selectMohwWlfarResrce
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 복지부 실시간 연계_복지자원조회
	 */
	@RequestMapping(value = "/selectMohwWlfarResrce.do")
	public View selectMohwWlfarResrce(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{

		Map<String, Object> rtnMap = linkMohwSrvcRqstService.selectMohwWlfarResrce(request, dataRequest);

		dataRequest.setResponse("dsList", rtnMap.get("dsList"));
		dataRequest.setResponse("dmPage", rtnMap.get("dmPage"));
		
		return new JSONDataView();
	}
	
}
