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
import isry.couns.mngr.atrzmng.service.StmtOfRsMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "StmtOfRsMngController Controller")
@RequestMapping("/atrzmng")
public class StmtOfRsMngController extends IsryBaseController {

    @Autowired
    private StmtOfRsMngService stmtOfRsMngService;
    
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
    @RequestMapping("/sampleSearchOptionStmt.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
	   	
	   	///조회 조건 검색 (부서)
    	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	//승인상태구분코드
	   	List<Map<String, Object>> searchComboAprvList = stmtOfRsMngService.searchComboBoxAprv(null);
	   	
	   	dataRequest.setResponse("dsSearchComboAprv", searchComboAprvList);
	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	
	   	return new JSONDataView();
   }
    
    @RequestMapping("/selectStmtOfRsMngList.do")
    public View selectStmtOfRsMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
		
		List<Map<String, Object>> dsList = stmtOfRsMngService.selectStmtOfRsMngList(request, mapParam);
		
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
    
    @RequestMapping("/selectStmtOfRsMngDetail.do")
    public View selectStmtOfRsMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	//조회 조건 param    	
    	String stofreSn = searchParam.getValue("STOFRE_SN"); //사유서 일련번호
    	
    	mapParam.put("stofreSn",stofreSn);
    	List<Map<String, Object>> dsList = null;
    	if(stofreSn != null && !stofreSn.isEmpty()) {
    		dsList = stmtOfRsMngService.selectStmtOfRsMngDetail(mapParam);
    		//dsList.get(0).replace("WORK_YMD", dsList.get(0).get("WORK_YMD").toString().replace("@", "\n"));
    	}
    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/updateStmtOfRsMng.do")
    public View updateStmtOfRsMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	ParameterGroup searchParamList = dataRequest.getParameterGroup("dsList");
    	//System.out.println("DDD : "+searchParamList.toString());
    	//조회 조건 param    	
    	String stofreSn = searchParam.getValue("STOFRE_SN"); //사유서 일련번호
    	
    	//저장할 데이터
    	String wrtYmd = searchParamList.getValue("WRT_YMD");
    	String stofreTtlNm = searchParamList.getValue("STOFRE_TTL_NM");
    	String stofreCn = searchParamList.getValue("STOFRE_CN");
    	String aprvSttsSeCd = searchParamList.getValue("APRV_STTS_SE_CD");
    	String rjctCsCn = searchParamList.getValue("RJCT_CS_CN");
    	String atfino	= searchParamList.getValue("ATFINO");
    	mapParam.put("stofreSn",stofreSn);
    	mapParam.put("wrtYmd",wrtYmd);
    	mapParam.put("stofreTtlNm",stofreTtlNm);
    	mapParam.put("stofreCn",stofreCn);
    	mapParam.put("aprvSttsSeCd",aprvSttsSeCd);
    	mapParam.put("rjctCsCn",rjctCsCn);
    	mapParam.put("atfino",atfino);
    	
    	//System.out.println("atfino 132145h3ujlkthrewjltgkrhewjlktgrew"+atfino);
    	if(stofreSn != null && !stofreSn.isEmpty()) {
    		stmtOfRsMngService.updateStmtOfRsMng(mapParam);
    	}
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteStmtOfRsMng.do")
    public View deleteStmtOfRsMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	HttpSession session = request.getSession();
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	//System.out.println("DDD : "+searchParam.toString());
    	
    	String stofreSn = searchParam.getValue("STOFRE_SN");
    	mapParam.put("loginId", loginId);
    	mapParam.put("stofreSn", stofreSn);
    	if(!loginId.isEmpty() && !stofreSn.isEmpty())
    		stmtOfRsMngService.deleteStmtOfRsMng(mapParam);
    	
    	return new JSONDataView();
    }
}