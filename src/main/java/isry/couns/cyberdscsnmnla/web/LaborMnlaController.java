/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cyberdscsnmnla.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import isry.couns.cyberdscsnmnla.service.LaborMnlaListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


@Controller
@Api(value = "laborMnlaController Controller")
@RequestMapping("/cyberdscsnmnla")
public class LaborMnlaController extends IsryBaseController {

	@Autowired
    private LaborMnlaListService laborMnlaListService;

	@RequestMapping("/laborMnlaSubOnLoad.do")
    public View subOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
                   
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
//		System.out.println("loginVO : "+loginVO.getUserName());

		
//        List<Map<String, Object>> dsOnLoadList = laborMnlaListService.subOnLoad(mapParam);
//        dataRequest.setResponse("dsBoardList", dsOnLoadList);      

		return new JSONDataView();
    }
	
	
//	@RequestMapping("/selectClienaMnlaList.do")
//    public View subSeachList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//     
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//        
//        ParameterGroup cmmnsParam = dataRequest.getParameterGroup("dmCmbChange");        
//        String cmmnsVal = cmmnsParam.getValue("CMMNS_CD_VALUE"); //중분류공통코드값
//        ParameterGroup sCmmnsParam = dataRequest.getParameterGroup("dmSMenuCmb");
//        String sCmmnsVal = sCmmnsParam.getValue("CMMNS_CD_VALUE");//소분류공통코드값
//        mapParam.put("CYBER_DSCSN_MNLA_MLSFC_SE_CD", cmmnsVal);
//        mapParam.put("CYBER_DSCSN_MNLA_SCLAS_SE_CD", sCmmnsVal);        
//        List<Map<String, Object>> dsBoardList = laborMnlaListService.selectClienaMnlaList(mapParam);        
//        dataRequest.setResponse("dsBoardList", dsBoardList);                
//		return new JSONDataView();
//    }
//	
	@RequestMapping("/saveLaborMnlaProc.do")
    public View saveLaborMnlaProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		laborMnlaListService.saveLaborMnlaProc(request, dataRequest);     
        
		return new JSONDataView();
    }
	
	
}








