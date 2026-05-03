/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.mntrngReprtsMng.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.couns.mngr.mntrngReprtsMng.service.MntrngReprtsMngService;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : MntrngReprtsMngController.java
 * @프로그램 설명 : 모니터링 보고서
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 28. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 28. 
 * @수정내용      : 모니터링 보고서
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/couns/mngr/mntrngReprtsMng")
public class MntrngReprtsMngController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Resource(name = "mntrngReprtsMngService")
	private MntrngReprtsMngService mntrngReprtsMngService;

	/**
	 * 모니터링 보고서 목록 조회
	 * @Method명   : selectMntrngReprtsList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 28. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMntrngReprtsList.do")
	public View selectMntrngReprtsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup pageParam = dataRequest.getParameterGroup("dmPage");
		ScpDb scpDb = new ScpDb();
		
		//조회 조건 param
    	String startDt = searchParam.getValue("START_DT");
    	String wrtrNm = searchParam.getValue("WRTR_NM");
    	String endDt = searchParam.getValue("END_DT");
    	String mntrngNo = searchParam.getValue("MNTRNG_NO");
    	String crtYmd = searchParam.getValue("CRT_YMD");
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) pageParam.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) pageParam.getValue("pageRowCount")); // 15
		int startIndex = (pageIdx - 1) * rowSize; 
		int totalCount = 0;
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("startDt",startDt);
    	mapParam.put("wrtrNmEncpt", wrtrNm);
    	mapParam.put("endDt",endDt);
    	mapParam.put("mntrngNo", mntrngNo);
    	mapParam.put("crtYmd", crtYmd);
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		//모니터링 보고서 목록 조회
		List<Map<String, Object>> dsMntrngReprtsList = mntrngReprtsMngService.selectMntrngReprtsList(mapParam);
		
		try {
			totalCount = Integer.parseInt(dsMntrngReprtsList.get(0).get("TOTAL_COUNT").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> dmPage = new HashMap<String, Object>();
		dmPage.put("totalCount", totalCount);
		dmPage.put("pageNo", pageIdx); 
		dmPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsMntrngReprtsList", dsMntrngReprtsList);
		dataRequest.setResponse("dmPage", dmPage);
		
		return new JSONDataView();
	}
	
	/**
	 * 보고서등록
	 * @Method명   : reprtsInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 28. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subReprtsInsert.do")
	@ResponseBody
	public View reprtsInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		int retVal = 0;
		List<Map<String, Object>> retMapList = mntrngReprtsMngService.selectWorkAltMntCrtYmdCheckList(request, dataRequest);
		
		if (retMapList.size() == 0) {
			//Map<String, Object> result = mntrngReprtsMngService.insertMntrngReprts(request, dataRequest);
			mntrngReprtsMngService.insertMntrngReprts(request, dataRequest);
			retVal = 1;//성공
		} 
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("retVal", retVal);
    	dataRequest.setMetadata(true, mapParam); 
		return new JSONDataView();
		
	}
	
	/**
	 * 모니터링 보고서 삭제
	 * @Method명   : mntrgReprtsDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 04. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subMntrgReprtsDelete.do")
	@ResponseBody
	public View mntrgReprtsDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
		
    	// 모니터링 보고서 삭제 처리 및 결과 모델 설정
    	Map<String, Object> result = mntrngReprtsMngService.deleteMntrngReprts(dataRequest);
    	result.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
		return new JSONDataView();
	}
	
	/**
	 * 사이버상담 목록조회
	 * @Method명   : selectCyberDscsnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subCyberDscsnList.do")
	public View selectCyberDscsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사이버상담 목록조회
		dataRequest.setResponse("dsCyberDscsnList", mntrngReprtsMngService.selectCyberDscsnList(dataRequest));
	
		return new JSONDataView();
	}
	
	/**
	 * 사이버아웃리치 목록조회
	 * @Method명   : selectOutreachList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subOutreachList.do")
	public View selectOutreachList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사이버아웃리치 목록조회
		dataRequest.setResponse("dsCyberOutrcList", mntrngReprtsMngService.selectOutreachList(dataRequest));
	
		return new JSONDataView();
	}
	
	/**
	 * 모바일상담 목록조회
	 * @Method명   : selectMobileList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMobileList.do")
	public View selectMobileList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 모바일상담 목록조회
		dataRequest.setResponse("dsMblaDscsnList", mntrngReprtsMngService.selectMobileList(dataRequest));
	
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : onLoadMntrgReprtsDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return	모니터링 보고서 상세 정보 (dsMntrgReprtsDtl)
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 : 모니터링 보고서 상세화면 OnLoad
	 */
	@RequestMapping(value = "/onLoadMntrgReprtsDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View onLoadMntrgReprtsDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사이버상담 목록조회
		dataRequest.setResponse("dsCyberDscsnList", mntrngReprtsMngService.selectCyberDscsnList(dataRequest));
		
		// 사이버아웃리치 목록조회
		dataRequest.setResponse("dsCyberOutrcList", mntrngReprtsMngService.selectOutreachList(dataRequest));
		
		// 모바일상담 목록조회
		dataRequest.setResponse("dsMblaDscsnList", mntrngReprtsMngService.selectMobileList(dataRequest));
		
		// 모니터링 보고서 상세 정보 조회
		List<Map<String, Object>> mntrgReprtsDtl = mntrngReprtsMngService.selectMntrngReprtsDetail(request, dataRequest);
		dataRequest.setResponse("dsMntrgReprtsDtl", mntrgReprtsDtl);
		
		return new JSONDataView();
	}
	
	/**
	 * 모니터링보고서 조회
	 * @Method명   : selectMntrngReprtsOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subMntrngReprtsOnload.do")
	public View selectMntrngReprtsOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 모니터링 보고서 상세 정보 조회
		List<Map<String, Object>> mntrgReprtsDtl = mntrngReprtsMngService.selectMntrngReprtsDetail(request, dataRequest);
		dataRequest.setResponse("dsMntrgReprtsDtl", mntrgReprtsDtl);
		
		// 위기및연계 게시글
		dataRequest.setResponse("dsCrisisLinkBbsctt" , mntrngReprtsMngService.selectCrisisLinkBbsctt(dataRequest));
		
		// 위기및연계 유형별건수
		dataRequest.setResponse("dsCrisisLinkTypeNocs" , mntrngReprtsMngService.selectCrisisLinkTypeNocs(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 모니터링 보고서 수정 처리
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMntrngReprts.do", method = RequestMethod.POST)
	public View updateMntrngReprts(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
		
    	// 모니터링 보고서 수정 처리 및 결과 모델 설정
    	Map<String, Object> result = mntrngReprtsMngService.updateMntrngReprts(request, dataRequest);
    	result.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
		return new JSONDataView();
	}
	
}
