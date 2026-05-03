/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.email.web;

import java.util.HashMap;
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
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.syscmmn.email.service.EmailService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;

/**
 * @파일명        : EmailController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 7. 18. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 7. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcms/syscmmn/email")
public class EmailController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "emailService")
	private EmailService emailService;

	// 공통코드 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@RequestMapping(value = "/send.do")
	public View send(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		emailService.insertEmail(request, dataRequest);
		return new JSONDataView();
	}

	@RequestMapping(value = "/emailHistory.do")
	public View emailHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = emailService.selectEmailHistoryCount(request, dmSearchMap);
		log.debug("totalCount : " + totalCount);
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("OFFSET_IDX", startIndex - 1);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);
		
		List<Map<String, Object>> listBoard = emailService.selectEmailHistory(request, dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		//dataRequest.setResponse("dsProcess", mgmtCmmnCodeService.selectCommonCode("SYS_PRCS_SE_CD"));  // 시스템 로그 프로세스 구분	
		
		
		
		
		//ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		//Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		//dataRequest.setResponse("dsList", smsService.selectSmsHistory(request, dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/emailDetailAttachList.do")
	public View emailDetailAttachList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> attachList = emailService.selectEmailDetailAttachList(request, dataRequest);
		
		dataRequest.setResponse("dsAttach", attachList);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/processEmailCancel.do")
	public View processEmailCancel(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		emailService.processEmailCancel(request, dataRequest);
		
		return new JSONDataView();
	}


	@RequestMapping(value = "/onloadEmailHistory.do")
	public View onloadEmailHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsEmailSendStatus", mgmtCmmnCodeService.selectCommonCode("EMAIL_SEND_STATUS_SE_CD"));
		
		return new JSONDataView();
	}

}
