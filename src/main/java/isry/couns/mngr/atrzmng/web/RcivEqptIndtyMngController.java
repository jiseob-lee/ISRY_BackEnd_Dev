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
import isry.couns.mngr.atrzmng.service.RcivEqptIndtyMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "RcivEqptIndtyMngController Controller")
@RequestMapping("/atrzmng")
public class RcivEqptIndtyMngController extends IsryBaseController {

    @Autowired
    private RcivEqptIndtyMngService rcivEqptIndtyMngService;
    
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
	 * @Method설명 :	소속기관 콤보박스 조회
	 */
    @RequestMapping("/sampleSearchOptionRciv.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
	   	
	   	/// 조회 조건 검색 (부서)
    	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	
	   	return new JSONDataView();
   }
    
    @RequestMapping("/selectRcivEqptIndtyMngList.do")
    public View selectRcivEqptIndtyMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param
    	String deptCd = searchParam.getValue("DEPT_CD");
    	String userInfo = searchParam.getValue("CONSTT_ID_NM");
    	String consttCkb = searchParam.getValue("CONSTT_CKB");
    	//System.out.println("검색 조건 구분 코드 === " + consttCkb);
    	if(("ID").equals(consttCkb)) {
    		mapParam.put("userId", userInfo);
    	}else if(("NAME").equals(consttCkb)) {
    		mapParam.put("userNm", userInfo);
    	}
    	
    	mapParam.put("deptCd",deptCd);
		
		List<Map<String, Object>> dsList = rcivEqptIndtyMngService.selectRcivEqptIndtyMngList(request, mapParam);
		
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
    
    @RequestMapping("/selectRcivEqptindtyMngDetail.do")
    public View selectRcivEqptindtyMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param    	
    	String indexSn = searchParam.getValue("INDEX_SN");
    	
    	mapParam.put("indexSn",indexSn);
    	
    	List<Map<String, Object>> dsList = null;
    	if(indexSn != null && !indexSn.isEmpty()) {
    		dsList = rcivEqptIndtyMngService.selectRcivEqptindtyMngDetail(mapParam);
    		dsList.get(0).replace("RECEIV_NM", dsList.get(0).get("RECEIV_NM"));
    		
    		for (Map<String, Object> map : dsList) {
    			map.replace("FLNM_ENCPT", map.get("FLNM_ENCPT"));
    			map.replace("MBL_TELNO_ENCPT", map.get("MBL_TELNO_ENCPT"));
    		}
    	}
    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/searchComboItem.do")
    public View searchComboItem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
	   	
	   	///장비아이템
	   	List<Map<String, Object>> searchComboList = rcivEqptIndtyMngService.searchComboItem(null); /* *소속기관 콤보박스 */
	   	
	   	dataRequest.setResponse("dsSearchComboItem", searchComboList);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/updateRcivEqptIndtyMng.do")
    public View updateRcivEqptIndtyMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
    	
    	//System.out.println("updateRcivEqptIndtyMng searchParam"+searchParam.toString());
    	//System.out.println("updateRcivEqptIndtyMng searchParamList"+searchParamList.toString());
    	
    	String indexSn = searchParam.getValue("INDEX_SN");
    	String eqpmntSn = searchParamList.getValue("EQPMNT_SN");
    	String tempEqpSeCd =  searchParamList.getValue("EQP_SE_CD");
    	String chgYmd = searchParamList.getValue("CHG_YMD");
    	String recptYmd = searchParamList.getValue("RECPT_YMD");
    	String eqpmntAddr = searchParamList.getValue("EQPMNT_ADDR");
    	String eqpmntDaddr = searchParamList.getValue("EQPMNT_DADDR");
    	//String eqpmntFileNm = searchParamList.getValue("EQPMNT_FILE_NM");
    	String zip = searchParamList.getValue("ZIP");
    	String atfino = searchParamList.getValue("ATFINO");
    	
    	String[] eqpSeCd = tempEqpSeCd.split(",");
    	
    	mapParam.put("loginId", loginId);
    	mapParam.put("indexSn", indexSn);
    	mapParam.put("eqpmntSn", eqpmntSn);
    	mapParam.put("chgYmd", chgYmd);
    	mapParam.put("recptYmd", recptYmd);
    	mapParam.put("eqpmntAddr", eqpmntAddr);
    	mapParam.put("eqpmntDaddr", eqpmntDaddr);
    	mapParam.put("zip", zip);
    	mapParam.put("atfino", atfino);
    	
    	rcivEqptIndtyMngService.updateRcivEqptIndtyMng(mapParam);
    	
    	if(eqpSeCd[0] != null && !eqpSeCd[0].isEmpty()) {
    		try {
    			rcivEqptIndtyMngService.deleteRciveEqptIndtyMng1(mapParam);
			} catch (Exception e) {				
				// TODO: handle exception
				//System.out.println("e MESSAGE : " + e.getMessage());
	    		for (String updEqpSeCd : eqpSeCd) {
	    			mapParam.put("eqpSeCd", updEqpSeCd);
	    			rcivEqptIndtyMngService.updateRcivEqptIndtyMng1(mapParam);
	    		}
	    		return new JSONDataView();
			}
    		
    		for (String updEqpSeCd : eqpSeCd) {
    			mapParam.put("eqpSeCd", updEqpSeCd);
    			rcivEqptIndtyMngService.updateRcivEqptIndtyMng1(mapParam);
    		}
    	}
    	//System.out.println("eqpSeCd" + eqpSeCd+" size:"+eqpSeCd.length);
    	
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteRciveEqptIndtyMng.do")
    public View deleteRciveEqptIndtyMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
    		rcivEqptIndtyMngService.deleteRciveEqptIndtyMng(mapParam);
    	
    	return new JSONDataView();
    }
    
   
    
}