/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.sms.web;

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

import isry.itgcms.syscmmn.sms.service.SmsService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;

/**
 * @파일명        : SmsController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 6. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 6. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcms/syscmmn/sms")
public class SmsController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "smsService")
	private SmsService smsService;

	// 공통코드 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@RequestMapping(value = "/smsOnload.do")
	public View smsOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사용자 소속 기관의 대표 전화번호 구하기
		Map<String, String> paramMap = smsService.selectRepresentativePhone(request);
		
		dataRequest.setResponse("dmParam", paramMap);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/onloadSmsDetail.do")
	public View onloadSmsDetail(DataRequest dataRequest, HttpServletRequest request) throws Exception{
		
		dataRequest.setResponse("dsSmsResultCode", mgmtCmmnCodeService.selectCommonCode("SMS_RESULT_CODE"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/send.do")
	public View send(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String contents = paramMap.get("contents");
		if (contents.length() <= 45) {
			smsService.insertSMS(request, dataRequest);
		} else if (contents.length() <= 1000) {
			smsService.insertLMS(request, dataRequest);
		} else {
			Map<String, String> msgMap = new HashMap<>();
			msgMap.put("msg", "글자수가 1000 자를 초과하였습니다.");
			dataRequest.setResponse("dsMsg", msgMap);
		}
		log.debug("test");
		return new JSONDataView();
	}

	@RequestMapping(value = "/smsHistory.do")
	public View smsHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = smsService.selectSmsHistoryCount(request, dmSearchMap);
		
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
		
		List<Map<String, Object>> listBoard = smsService.selectSmsHistory(request, dmSearchMap);
		
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

	@RequestMapping(value = "/processSmsCancel.do")
	public View processSmsCancel(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		smsService.processSmsCancel(request, dataRequest);
		
		return new JSONDataView();
	}

}
