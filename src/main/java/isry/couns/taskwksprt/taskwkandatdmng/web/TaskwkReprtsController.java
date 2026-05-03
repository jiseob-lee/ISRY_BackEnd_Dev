/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.web;

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
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.cmmn.service.CounsService;
import isry.couns.taskwksprt.taskwkandatdmng.service.TaskwkReprtsService;
import isry.itgcms.util.StringUtil;

@Controller
@Api(value = "TaskwkReprtsController Controller")
@RequestMapping(value = "/isry/couns/taskwksprt/taskwkandatdmng")
public class TaskwkReprtsController {

	// Logger 추가 - 2022/08/30 JUNG WON JE
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskwkReprtsController.class);

    @Resource(name = "taskwkReprtsService")
    private TaskwkReprtsService svc;
    
    @Resource(name = "counsService")
    private CounsService counsService;
	
	/**
	 * 업무보고서 목록 화면 OnLoad
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return	부서정보 콤보박스 공통코드 목록 (dsCombDeptCd) 
	 * @throws Exception
	 */
	@RequestMapping(value = "/onLoadTaskwkReprtsList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View onLoadTaskwkReprtsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
        // 화면에서 넘어온 파라미터
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        LOGGER.debug("onLoadTaskwkReprtsList.do :: {}", searchParam.toString());        

		// 기관별 부서 목록 조회 (콤보박스)
        List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);

        // 리턴 데이터 설정
        dataRequest.setResponse("dsCombDeptCd", searchComboList); 
        
        return new JSONDataView();

    }

	/**
	 * 업무보고서 목록 조회
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return	업무보고서 목록 (dsList) 및 페이징 정보 (dmPage)
	 * @throws Exception
	 */
    @RequestMapping(value = "/selectTaskwkReprtsList.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectTaskwkReprtsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
    	// 업무보고서 목록 조회
        List<Map<String, Object>> list = svc.selectTaskwkReprtsList(request, dataRequest);
        
		// 조회결과 Response 저장
        dataRequest.setResponse("dsList", list);
        
        return new JSONDataView();
    }
    
    /**
     * 업무보고서 상세정보 조회
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return 업무보고서 상세정보 (dsReportDtl)
     * @throws Exception
     */
    @RequestMapping(value = "/selectTaskwkReprtsDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectTaskwkReprtsDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	// 업무보고서 상세정보 조회
        List<Map<String, Object>> reportDtl = svc.selectTaskwkReprtsDetail(dataRequest);
        dataRequest.setResponse("dsReportDtl", reportDtl);
        
        // 업무보고서 (모바일상담) 수정용 데이터 조회
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        String deptCd = searchParam.getValue("DEPT_CD");
        if (!StringUtil.isEmpty(deptCd) && "326".equals(deptCd)) {
        	List<Map<String, Object>> mobileEditDtl = svc.selectTaskwkReprtsDetailByMblaDscsn(dataRequest);
            dataRequest.setResponse("dsMobileEditDtl", mobileEditDtl);
        }
        
        return new JSONDataView();

    }
    
    /**
     * 업무보고서 등록 초기데이터 load
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return 업무보고서 등록 초기데이터 (dmReport)
     * @throws Exception
     */
    @RequestMapping(value = "/onLoadTaskwkReprtsInsert.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View onLoadTaskwkReprtsInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String resultYn = "Y";
    	
    	Map<String, Object> result = svc.selectTaskwkReprtsRegData(request, dataRequest);
    	if (result.containsKey("RESULT_OK")) {
    		resultYn = result.get("RESULT_OK").toString();
    	}
    	
    	if ("N".equals(resultYn)) {
    		Map<String, Object> message = new LinkedHashMap<String, Object>();
    		result.forEach(message::put);
    		
    		dataRequest.setMetadata(true, message);
    	} else {
    		dataRequest.setResponse("dmReport", result);
    	}
    	
    	return new JSONDataView();
    }
    
    /**
     * 시간외 근무 신청 목록
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	시간외 근무 신청 목록 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectOvtimeAplyHistbDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectOvtimeAplyHistbDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        List<Map<String, Object>> list = svc.selectOvtimeAplyHistbDetail(dataRequest);
        
        dataRequest.setResponse("dsList", list);
        
        return new JSONDataView();
    }
    
    /**
     * 업무보고서 상세 > 채팅상담목록 조회
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	채팅상담목록 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectChttDscsnListByDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectChttDscsnListByDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        List<Map<String, Object>> list = svc.selectChttDscsnListByDetail(dataRequest);
        
        dataRequest.setResponse("dsList", list);
        
        return new JSONDataView();
    }
    
    /**
     * 업무보고서 상세 > 게시판상담목록 조회
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	게시판상담목록 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectNtabrdDscsnListByDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectNtabrdDscsnListByDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        List<Map<String, Object>> list = svc.selectNtabrdDscsnListByDetail(dataRequest);
        
        dataRequest.setResponse("dsList", list);
        
        return new JSONDataView();
    }
    
    /**
     * 업무보고서 상세 > 아웃리치상담목록 조회
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectOutrcDscsnListByDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectOutrcDscsnListByDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        List<Map<String, Object>> list = svc.selectOutrcDscsnListByDetail(dataRequest);
        
        dataRequest.setResponse("dsList", list);
        
        return new JSONDataView();
    }
    
    /**
     * 채팅상담내역 조회
     * (업무보고서 상세 > 채팅상담 평가내역 > 채팅상담내역 > 채팅내역보기)
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	채팅상담내역 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectChttDscsnHistbInqDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectChttDscsnHistbInqDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        List<Map<String, Object>> results = svc.selectChttDscsnHistbInqDetail(dataRequest);
        
        dataRequest.setResponse("dsChttDscsnHistb", results);
        
        return new JSONDataView();
    }
    
    /**
     * 업무보고서 등록 > 채팅상담내역 목록 조회
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	채팅상담내역 목록 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectChttDscsnHistbList.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectChttDscsnHistbList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        List<Map<String, Object>> results = svc.selectChttDscsnHistbList(request, dataRequest);

        dataRequest.setResponse("dsList", results);
        
        return new JSONDataView();

    }
    
    /**
     * 게시판상담내역 조회
     * (업무보고서 상세 > 게시판상담 평가내역 > 게시판상담내역 > 게시글보기)
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	게시판상담내역 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectNtabrdDscsnHistbInqDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectNtabrdDscsnHistbInqDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        List<Map<String, Object>> results = svc.selectNtabrdDscsnHistbInqDetail(dataRequest);

        dataRequest.setResponse("dsList", results);
        
        return new JSONDataView();

    }
    
    /**
     * 업무보고서 등록 > 게시판상담내역 목록 조회
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return	게시판상담내역 목록 (dsList)
     * @throws Exception
     */
    @RequestMapping(value = "/selectNtabrdDscsnHistList.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectNtabrdDscsnHistList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("selectNtabrdDscsnHistList.do :: {}", searchParam.toString());
        
		List<Map<String, Object>> results = svc.selectNtabrdDscsnHistList(dataRequest);

        dataRequest.setResponse("dsList", results);
        
        return new JSONDataView();
    }
    
    /**
     * 업무보고서 등록 > 체크리스트 목록 조회 (채팅상담, 게시판 상담) 
     * 
     * @Method명   : selectAYC260List
     * @param request
     * @param response
     * @param dataRequest
     * @return	체크리스트 목록 (ds260)
     * @throws Exception
     */
    @RequestMapping(value = "/selectAYC260List.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View selectAYC260List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("selectAYC260List.do :: ", searchParam.toString());        
		mapParam.put("GB"	, searchParam.getValue("GB"));
        
        List<Map<String, Object>> list = svc.selectAYC260List(mapParam);

        dataRequest.setResponse("ds260", list);
        
        return new JSONDataView();

    }
    
    /**
     * 업무보고서 등록 처리
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertTaskwkReprts.do", method = RequestMethod.POST)
    public View insertTaskwkReprts(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
    	// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 업무보고서 등록 처리 및 결과 모델 설정 
    	Map<String, Object> result = svc.insertTaskwkReprts(request, dataRequest);
    	result.forEach(message::put);
    	
		dataRequest.setMetadata(true, message);
    	
    	return new JSONDataView();
    }
    
    /**
     * 업무보고서 수정 처리
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTaskwkReprts.do")
    public View updateTaskwkReprts(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
    	
    	// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 업무보고서 수정 처리 및 결과 모델 설정 
    	Map<String, Object> result = svc.updateTaskwkReprts(request, dataRequest);
    	result.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
    	return new JSONDataView();
    }
    
    /**
     * 업무보고서 삭제 처리
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteTaskwkReprts.do", method = RequestMethod.POST)
    public View deleteTaskwkReprts(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
    	
    	// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 업무보고서 삭제 처리 및 결과 모델 설정
    	Map<String, Object> result = svc.deleteTaskwkReprts(request, dataRequest);
    	result.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
    	return new JSONDataView();
    }
    
    /**
     * 업무보고서 상세(팝업) 수정 처리
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTaskwkReprtsByDetail.do", method = RequestMethod.POST)
    public View updateTaskwkReprtsByDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
    	
    	// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 업무보고서 상세(팝업) 수정 처리 및 결과 모델 설정 
    	Map<String, Object> result = svc.updateTaskwkReprtsByDetail(dataRequest);
    	result.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
    	return new JSONDataView();
    }
}