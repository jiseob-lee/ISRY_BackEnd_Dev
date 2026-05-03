/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cyberdscsnmnla.web;

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
import isry.couns.cyberdscsnmnla.service.CyberMnlaListservice;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

@Controller
@Api(value = "cyberMnlaListController Controller")
@RequestMapping("/cyberdscsnmnla")
public class CyberMnlaListController extends IsryBaseController {

	@Autowired
    private CyberMnlaListservice cyberMnlaListservice;
	
	///cyberdscsnmnla/cyberMnlaOnload.do
	@RequestMapping("/cyberMnlaOnload.do")
    public View subOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                   
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

        dataRequest.setResponse("dsMenuCmb", cyberMnlaListservice.clienaMnlaCode("CYBER_DSCSN_MNLA_MLSFC_SE_CD"));
        dataRequest.setResponse("dsSMenuCmb", cyberMnlaListservice.clienaMnlaCodeS("CYBER_DSCSN_MNLA_SCLAS_SE_CD"));     

		return new JSONDataView();
    }

	@RequestMapping("/selectInqCyberMnlaList.do")
    public View selectInqCyberMnlaList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup cmmnsParam = dataRequest.getParameterGroup("dmCmbChange");        
        String cmmnsVal = cmmnsParam.getValue("CMMNS_CD_VALUE_M"); //중분류공통코드값
        String sCmmnsVal = cmmnsParam.getValue("CMMNS_CD_VALUE_S");//소분류공통코드값     
        
        // 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
  		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
         
  		// 페이지 인덱싱에 필요한 정보를 정제합니다.
  		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
  		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
  		int startIndex = (pageIdx - 1) * rowSize;

  		mapParam.put("START_IDX", startIndex);
  		mapParam.put("ROW_COUNT", rowSize);
  		
  		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
 		Map<String, Object> resPage = new HashMap<String, Object>();
        
        if(cmmnsParam.getValue("CMMNS_CD_VALUE_M").equals("02")) {
        	
        	mapParam.put("CYBER_DSCSN_MNLA_LCLAS_SE_CD", cmmnsVal);
        	List<Map<String, Object>> dsBoardList = cyberMnlaListservice.selectCyberMnlaList(mapParam);
        	
        	if(dsBoardList.size() == 0) {
				resPage.put("totalCount", 0);
			} else {
				resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
			}
			
			resPage.put("pageNo", pageIdx);
			resPage.put("pageRowCount", rowSize);
			
			dataRequest.setResponse("dmPage", resPage);
        	
            dataRequest.setResponse("dsBoardList", dsBoardList);
        } else {
        	mapParam.put("CYBER_DSCSN_MNLA_MLSFC_SE_CD", cmmnsVal);
            mapParam.put("CYBER_DSCSN_MNLA_SCLAS_SE_CD", sCmmnsVal);  
            List<Map<String, Object>> dsBoardList = cyberMnlaListservice.selectCyberMnlaList(mapParam);
            
        	if(dsBoardList.size() == 0) {
				resPage.put("totalCount", 0);
			} else {
				resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
			}
			
			resPage.put("pageNo", pageIdx);
			resPage.put("pageRowCount", rowSize);
			
			dataRequest.setResponse("dmPage", resPage);
            
            dataRequest.setResponse("dsBoardList", dsBoardList); 
        }
        
        return new JSONDataView();
    }

	@RequestMapping("/selectInqCyberMnlaDetail.do")
    public View selectInqCyberMnlaDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup cmmnsParam = dataRequest.getParameterGroup("dmDetail");        
        String param = cmmnsParam.getValue("INDEX");
       
        mapParam.put("INDEX", param);  
        List<Map<String, Object>> dsBoardList = cyberMnlaListservice.selectInqCyberMnlaDetail(mapParam);
        
        dataRequest.setResponse("dsBoardList", dsBoardList);                
		return new JSONDataView();
    }
	
	@RequestMapping("/saveCyberMnlaProc.do")
    public View saveCyberMnlaProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = cyberMnlaListservice.saveCyberMnlaProc(request, dataRequest);     
		
		Map<String, Object> message = new HashMap<String, Object>();

		message.put("MNLA_NO", returnParam.get("MNLA_NO"));
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	
}








