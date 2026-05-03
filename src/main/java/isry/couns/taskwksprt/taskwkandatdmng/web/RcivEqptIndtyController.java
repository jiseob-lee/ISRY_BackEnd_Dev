/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.web;

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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.couns.taskwksprt.taskwkandatdmng.service.RcivEqptIndtyService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
@Api(value = "RcivEqptIndtyController Controller")
@RequestMapping("/taskwkandatdmng")
public class RcivEqptIndtyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
    @Resource(name = "rcivEqptIndtyService")
    private RcivEqptIndtyService rcivEqptIndtyService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping("/searchComboOptionRcivEq.do")
    public View searchComboOptionRcivEq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	List<Map<String, Object>> dsSearchComboItem = rcivEqptIndtyService.searchComboOptionRcivEq(null);
    	dataRequest.setResponse("dsSearchComboItem", dsSearchComboItem);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectRcivEqptIndtyList.do")
    public View selectRcivEqptIndtyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String loginId = "";		// session 정보의 ID
    	
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        if(loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
        	loginId = loginVO.getId();
        }
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("loginId", loginId);

        Map<String, Object> dmSearch = rcivEqptIndtyService.selectUserInfo(mapParam);
        List<Map<String, Object>> dsList = rcivEqptIndtyService.selectRcivEqptIndtyList(mapParam);
        
       	dataRequest.setResponse("dmSearch", dmSearch);
        dataRequest.setResponse("dsList", dsList);
        
    	return new JSONDataView();
    }
    
    @RequestMapping("/insertRcivEqptIndty.do")
    public View insertRcivEqptIndty(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String loginId = "";			// session 정보의 ID
    	int AYC330InsertSuccessYn = 0;	// insertRcivEqptIndty함수의 성공 여부
    	
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        if(loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
        	loginId = loginVO.getId();
        }
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String cnsltntNm = searchParam.getValue("FLNM");
        String cnsltntId = searchParam.getValue("USER_ID");
        String eqpmntSn = searchParam.getValue("EQPMNT_SN");
        String eqpmntItemCd = searchParam.getValue("EQPMNT_ITEM_CD");
        String chgYmd = searchParam.getValue("CHG_YMD");
        String rcptYmd = searchParam.getValue("RCPT_YMD");
        String zip = searchParam.getValue("ZIP");
        String eqpmntAddr = searchParam.getValue("EQPMNT_ADDR");
        String eqpmntAddr1 = searchParam.getValue("EQPMNT_ADDR1");
        String bbscttAtfino = searchParam.getValue("BBSCTT_ATFINO");
        String atfino = searchParam.getValue("ATFINO");
        
        String[] arrEqpmntItemCd = eqpmntItemCd.split(",");
        
        LOGGER.debug("arrEqpmntItemCd.length ::: " + arrEqpmntItemCd.length);
        LOGGER.debug("arrEqpmntItemCd[0] ::: " + arrEqpmntItemCd[0]);
        
        mapParam.put("loginId", loginId);
        mapParam.put("cnsltntNm", cnsltntNm);
        mapParam.put("cnsltntId", cnsltntId);
        mapParam.put("eqpmntSn", eqpmntSn);
        //mapParam.put("eqpmntItemCd", eqpmntItemCd);
        mapParam.put("chgYmd", chgYmd);
        mapParam.put("rcptYmd", rcptYmd);
        mapParam.put("zip", zip);
        mapParam.put("eqpmntAddr", eqpmntAddr);
        mapParam.put("eqpmntAddr1", eqpmntAddr1);
        mapParam.put("bbscttAtfino", bbscttAtfino);
        mapParam.put("atfino", atfino);
        
		if (arrEqpmntItemCd.length > 0) {
			AYC330InsertSuccessYn = rcivEqptIndtyService.insertRcivEqptIndty(mapParam);
			LOGGER.debug("결과값 :::: " + mapParam.get("RECPT_EQPMNT_CNFRMN_SN"));
			if (AYC330InsertSuccessYn == 1) {
				for (String insEqpSeCd : arrEqpmntItemCd) {
					mapParam.put("eqpSeCd", insEqpSeCd);
					rcivEqptIndtyService.insertRcivEqptIndty1(mapParam);
				}
			} else {
				throw new AppWorksException("기자재 수령 확인서 등록에 실패하였습니다.\n관리자에게 문의하세요.", Alert.ERROR);
			}
		} else {
			throw new AppWorksException("수령항목이 미입력되었습니다. 다시 시도해주세요.", Alert.ERROR);
		}
        
    	return new JSONDataView();
    }
    
}