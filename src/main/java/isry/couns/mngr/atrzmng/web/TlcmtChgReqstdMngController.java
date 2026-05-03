/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.web;

import java.util.HashMap;
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
import isry.couns.mngr.atrzmng.service.TlcmtChgReqstdMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "TlcmtChgReqstdMngController Controller")
@RequestMapping("/atrzmng")
public class TlcmtChgReqstdMngController extends IsryBaseController {

    @Autowired
    private TlcmtChgReqstdMngService tlcmtChgReqstdMngService;
    
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
    @RequestMapping("/sampleSearchOptionTlcmt.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
	   	Map<String, Object> mapParam = new HashMap<String, Object>();
	   	
	   	//조회 조건 검색 (부서)
	   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	//조회 조건 검색 (승인상태)
	   	List<Map<String, Object>> searchComboAprvList = tlcmtChgReqstdMngService.searchComboBoxAprv(mapParam);
	   	
	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	dataRequest.setResponse("dsSearchComboAprv", searchComboAprvList);
	   	
	   	return new JSONDataView();
   }
    
    @RequestMapping("/selectTlcmtChgReqstdMngList.do")
    public View selectTlcmtChgReqstdMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param
    	String deptCd = searchParam.getValue("DEPT_CD");
    	String aprvSttsSeCd = searchParam.getValue("APRV_STTS_SE_CD");
    	String userInfo = searchParam.getValue("CONSTT_ID_NM");
    	String consttCkb = searchParam.getValue("CONSTT_CKB");
    	if(consttCkb.equalsIgnoreCase("ID")) {
    		mapParam.put("userId", userInfo);
    	}else if(consttCkb.equalsIgnoreCase("NAME")) {
    		mapParam.put("userNm", userInfo);
    	}
    	
    	mapParam.put("deptCd",deptCd);
    	mapParam.put("aprvSttsSeCd",aprvSttsSeCd);
		
		List<Map<String, Object>> dsList = tlcmtChgReqstdMngService.selectTlcmtChgReqstdMngList(request, mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(dsList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsList.get(0).get("TOTAL_COUNT"));
		}
			
		dataRequest.setResponse("dmPage", resPage);
		dataRequest.setResponse("dsList", dsList);
		
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectTlcmtChgReqstdMngDetail.do")
    public View selectTlcmtChgReqstdMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param
    	//String userId = searchParam.getValue("CNSLTNT_ID");
    	String indexSn	= searchParam.getValue("INDEX_SN");
    	
    	mapParam.put("indexSn",indexSn);
    	List<Map<String, Object>> dsList = null;
    	
    	if(indexSn != null && !indexSn.isEmpty()) {
    		dsList = tlcmtChgReqstdMngService.selectTlcmtChgReqstdMngDetail(mapParam);
    	}
    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    @RequestMapping("/updateTlcmtChgReqstdMng.do")
    public View updateTlcmtChgReqstdMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	HttpSession session = request.getSession();
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	ParameterGroup searchParamList = dataRequest.getParameterGroup("dsList");
    	
    	String indexSn = searchParam.getValue("INDEX_SN");
    	String hmtrcWorkChgYmd = searchParamList.getValue("HMTRC_WORK_CHG_YMD");
    	String hmtrcWorkCsCn = searchParamList.getValue("HMTRC_WORK_CS_CN");
    	String zip = searchParamList.getValue("ZIP");
    	String trprAddr = searchParamList.getValue("TRPR_ADDR");
    	String trprLctnDaddr = searchParamList.getValue("TRPR_LCTN_DADDR");
    	String aprvSttsSeCd = searchParamList.getValue("APRV_STTS_SE_CD");
    	String rjctCsCn = searchParamList.getValue("RJCT_CS_CN");
    	
    	mapParam.put("loginId", loginId);
    	mapParam.put("indexSn", indexSn);
    	mapParam.put("hmtrcWorkChgYmd", hmtrcWorkChgYmd);
    	mapParam.put("hmtrcWorkCsCn", hmtrcWorkCsCn);
    	mapParam.put("zip", zip);
    	mapParam.put("trprAddr", trprAddr);
    	mapParam.put("trprLctnDaddr", trprLctnDaddr);
    	mapParam.put("aprvSttsSeCd", aprvSttsSeCd);
    	mapParam.put("rjctCsCn", rjctCsCn);
    	
    	if(indexSn != null && !indexSn.isEmpty()) {
    		tlcmtChgReqstdMngService.updateTlcmtChgReqstdMng(mapParam);
    	}
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteTlcmtChgReqstdMng.do")
    public View deleteTlcmtChgReqstdMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	HttpSession session = request.getSession();
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	String indexSn = searchParam.getValue("INDEX_SN");
    	
    	mapParam.put("loginId", loginId);
    	mapParam.put("indexSn", indexSn);
    	
    	if(!loginId.isEmpty() && !indexSn.isEmpty())
    		tlcmtChgReqstdMngService.deleteTlcmtChgReqstdMng(mapParam);
    	
    	return new JSONDataView();
    }
    
}