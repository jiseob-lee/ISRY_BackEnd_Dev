/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.recipireg.web;

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
import isry.pubms.slfrlsprtpensn.recipireg.service.RecipiRegService;

/**
 * @파일명        : RecipiRegController.java
 * @프로그램 설명 : 자립지원수당 - 1. 수급자등록 및 수당지급결정
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.07.10
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.07.27
 * @수정내용      : 
 * - Paging 처리 방식 변경 (강화영 수석 :기존 페이징 방식에 문제 있어서 사용 권유하지않는다고 함, 후속 작업자 참고 바람)
 * - [2023-08-30, Gyu.Ha.Baek] PRE 반영
 * -                
 */

@Controller
@RequestMapping("/isry/pubms/slfrlsprtpensn/recipireg")
public class RecipiRegController {
	
//	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "recipiRegService")
	private RecipiRegService recipiRegService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/selectRecipiList.do")
	public View selectRecipiList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//목록 조회
		Map<String, Object> result = recipiRegService.selectRecipiList(request, dataRequest);
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectTrprList.do")
	public View selectTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
				
		//목록 조회
		Map<String, Object> result = recipiRegService.selectTrprList(request, dataRequest);
		dataRequest.setResponse("dsTrprList", result.get("dsTrprList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/saveTrprReg.do")
	public View saveTrprReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		recipiRegService.saveTrprReg(request, dataRequest);
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/deleteTrprReg.do")
	public View deleteTrprReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {		
		recipiRegService.deleteTrprReg(request, dataRequest);		
		return new JSONDataView();		
	}
	
	@RequestMapping(value = "/selectPensnGiveDcsnList.do")
	public View selectPensnGiveDcsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//목록 조회
		Map<String, Object> result = recipiRegService.selectPensnGiveDcsnList(request, dataRequest);
		dataRequest.setResponse("dsPensnGiveDcsnList", result.get("dsPensnGiveDcsnList"));
		dataRequest.setResponse("dmPage2", result.get("dmPage2"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/savePensnGiveDcsnReg.do")
	public View savePensnGiveDcsnReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		recipiRegService.savePensnGiveDcsnReg(request, dataRequest);
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/deletePensnGiveDcsnReg.do")
	public View deletePensnGiveDcsnReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {		
		recipiRegService.deletePensnGiveDcsnReg(request, dataRequest);		
		return new JSONDataView();		
	}
	
	@RequestMapping(value = "/savePensnGiveDcsnSeq.do")
	public View subSavePensnGiveDcsnSeq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		recipiRegService.savePensnGiveDcsnSeq(request, dataRequest);
		return new JSONDataView();
	}

}
