/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.atrzmng.service.VacAprvMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "VacAprvMngController Controller")
@RequestMapping("/atrzmng")
public class VacAprvMngController extends IsryBaseController {

    @Autowired
    private VacAprvMngService vacAprvMngService;
    
    @Resource(name = "counsService")
    private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;    
    
    /**
	 * @Method명   : sampleSearchOption
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 :	소속기관 콤보박스 조회(나중에 부서명으로 변경 될 예정)
	 */
    @RequestMapping("/sampleSearchOptionVacAprv.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
	   	
    	// 조회 조건 검색
	   	// 1. 소속부서
	   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	// 2. 신청구분
	   	List<Map<String, Object>> searchComboAplyList = vacAprvMngService.searchComboBoxAply();
	   	
	   	// 3. 승인상태구분코드
	   	List<Map<String, Object>> searchComboAprvList = vacAprvMngService.searchComboBoxAprv(null); 
	   	
	   	// 4. 휴가구분코드
	   	List<Map<String, Object>> searchComboVacList = vacAprvMngService.searchComboBoxVac(null); 
	   	
	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	dataRequest.setResponse("dsSearchComboAply", searchComboAplyList);
	   	dataRequest.setResponse("dsSearchComboAprv", searchComboAprvList);
	   	dataRequest.setResponse("dsSearchComboVac", searchComboVacList);
	   	
	   	return new JSONDataView();
   }
    
    @RequestMapping("/selectVacAprvMngList.do")
    public View selectVacAprvMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	ParameterGroup pageParam = dataRequest.getParameterGroup("dmPage");
    	
    	//조회 조건 param
    	String deptCd = searchParam.getValue("DEPT_CD");
    	String aplySeCd = searchParam.getValue("APLY_SE_CD_SEARCH");
    	String aprvSttsSeCd = searchParam.getValue("APRV_STTS_SE_CD_SEARCH");
    	String vcatnSeCd = searchParam.getValue("VCATN_SE_CD_SEARCH");
    	String bgngYmd = searchParam.getValue("BGNG_YMD");
    	String endYmd = searchParam.getValue("END_YMD");
    	
    	String userInfo = searchParam.getValue("CONSTT_ID_NM");
    	String consttCkb = searchParam.getValue("CONSTT_CKB");
    	if(consttCkb.equalsIgnoreCase("ID")) {
    		mapParam.put("userId", userInfo);
    	}else if(consttCkb.equalsIgnoreCase("NAME")) {
    		mapParam.put("userNm", userInfo);
    	}
    	
    	mapParam.put("deptCd",deptCd);
    	mapParam.put("aplySeCd",aplySeCd);
    	mapParam.put("aprvSttsSeCd",aprvSttsSeCd);
    	mapParam.put("vcatnSeCd",vcatnSeCd);
    	mapParam.put("bgngYmd", bgngYmd);
    	mapParam.put("endYmd", endYmd);
    	
    	// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) pageParam.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) pageParam.getValue("pageRowCount")); // 15
		int startIndex = (pageIdx - 1) * rowSize; 
		int totalCount = 0;
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		
		List<Map<String, Object>> dsList = vacAprvMngService.selectVacAprvMngList(request, mapParam);
		try {
			totalCount = Integer.parseInt(dsList.get(0).get("TOTAL_COUNT").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> dmPage = new HashMap<String, Object>();
		//System.out.println("totalCount: "+totalCount);
		dmPage.put("totalCount", totalCount);
		dmPage.put("pageNo", pageIdx);
		dmPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsList", dsList);
		dataRequest.setResponse("dmPage", dmPage);
		
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectVacAprvMngDetail.do")
    public View selectVacAprvMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param
    	String userId = searchParam.getValue("CNSLTNT_ID");
    	String copaIndexSn = searchParam.getValue("COPA_INDEX_SN");
    	
    	mapParam.put("userId",userId);
    	mapParam.put("copaIndexSn",copaIndexSn);
    	List<Map<String, Object>> dsList = null;
    	if(copaIndexSn != null && !copaIndexSn.isEmpty()) {
    		dsList = vacAprvMngService.selectVacAprvMngDetail(mapParam);    		
    	}
    	//System.out.println("dsList To STRING : " +dsList.get(0).toString());
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/updateVacAprvMngBatch.do")
    public View updateVacAprvMngBatch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request); 
		String loginId = loginVO.getId();
		mapParam.put("loginId", loginId);
		//확인 후 최종적으로 실행하겠음.
    	vacAprvMngService.updateVacAprvMngBatch(mapParam);
    	
    	return new JSONDataView();
    }
    
    /**
	 * @Method명   : processVacAprvBatch
	 * @Method설명 : 휴가승인관리 - 일괄 승인 process
	 * @param 	   : request
	 * @param	   : response
	 * @param	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 10. 25. 
	 */
    @RequestMapping("/processVacAprvBatch.do")
    public View processVacAprvBatch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	int returnVal = vacAprvMngService.processVacAprvBatch(request, dataRequest);
    	
    	resultMap.put("returnVal", returnVal);
    	dataRequest.setMetadata(true, resultMap);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/updateVacAprvMng.do")
    public View updateVacAprvMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
		vacAprvMngService.updateVacAprvMng(request, dataRequest);			
		
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteVacAprvMng.do")
    public View deleteVacAprvMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request); 
		String loginId = loginVO.getId();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String indexSn = searchParam.getValue("COPA_INDEX_SN");
		
		mapParam.put("loginId", loginId);
		mapParam.put("indexSn",indexSn);
		if(indexSn != null && !indexSn.isEmpty()) {			
			vacAprvMngService.deleteVacAprvMng(mapParam);
		}
		return new JSONDataView();
    }
    
    /**
     * 
     * @Method명   : processVacAprvMngSms
     * @Method설명 : 휴가승인관리 - 문자 일괄 전송 process
     * @param 	   : request
     * @param 	   : response
     * @param 	   : dataRequest
     * @throws 	   : Exception
     * @작성자     : Jeong.Won.Je
     * @작성일     : 2022. 11. 2. 
     */
    @RequestMapping("/processVacAprvMngSms.do")
    public View processVacAprvMngSms(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
    	
    	Map<String, Object> returnMap = vacAprvMngService.processVacAprvMngSms(request, dataRequest);
    	
    	dataRequest.setMetadata(true, returnMap);
    	return new JSONDataView();
    }
    
    /**
     * 휴가일정 가져오기 (일괄등록)
     * 
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertVacAprvMngGw.do")
    public View insertVacAprvMngGw(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 휴가일정 일괄 등록 처리 및 결과 모델 설정
    	Map<String, Object> result = vacAprvMngService.insertVacAprvMngGw(request, dataRequest);
    	result.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
    	return new JSONDataView();
    }
    
}