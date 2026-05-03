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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import io.swagger.annotations.Api;
import isry.couns.taskwksprt.taskwkandatdmng.service.OfwkUpcsdClngSprtService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "OfwkUpcsdClngSprtController Controller")
@RequestMapping("/taskwkandatdmng")
public class OfwkUpcsdClngSprtController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "ofwkUpcsdClngSprtService")
    private OfwkUpcsdClngSprtService ofwkUpcsdClngSprtService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping("/selectOfwkUpcsdClngList.do")
    public View selectOfwkUpcsdClngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	HttpSession session = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String loginId = "";
        if(!loginVO.getId().isEmpty())
        	loginId = loginVO.getId();
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        ParameterGroup pageParam = dataRequest.getParameterGroup("dmPage");
        
        String bgngYmd = searchParam.getValue("BGNG_YMD");
        String endYmd = searchParam.getValue("END_YMD");
//        String userInfo = searchParam.getValue("CONSTT_ID_NM");
//    	String consttCkb = searchParam.getValue("CONSTT_CKB");
    	
    	// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) pageParam.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) pageParam.getValue("pageRowCount")); // 20
		int startIndex = (pageIdx - 1) * rowSize; 
		int totalCount = 0;
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
//    	if(consttCkb.equalsIgnoreCase("ID")) {
//    		mapParam.put("userId", userInfo);
//    	}else if(consttCkb.equalsIgnoreCase("NAME")) {
//    		mapParam.put("userNm", scpDb.scpEncB64(userInfo));
//    	}
		mapParam.put("userId", loginId);
        mapParam.put("loginId", loginId);
        mapParam.put("bgngYmd", bgngYmd);
        mapParam.put("endYmd", endYmd);
        
        List<Map<String, Object>> dsList = ofwkUpcsdClngSprtService.selectOfwkUpcsdClngList(mapParam);
        
        try {
    		totalCount = Integer.parseInt(dsList.get(0).get("TOTAL_COUNT")+"");			
		} catch (Exception e) {
			// TODO: handle exception
			totalCount = 0;
		}
        
       	Map<String, Object> dmPage = new HashMap<String, Object>();
		dmPage.put("totalCount", totalCount);
		dmPage.put("pageNo", pageIdx);
		dmPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", dmPage);
       	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectOfwkUpcsdClngRegDtl.do")
    public View selectOfwkUpcsdClngRegDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String userId = searchParam.getValue("USER_ID");
        String unofYmd = searchParam.getValue("UNOF_YMD");
        
        mapParam.put("userId", userId);
        mapParam.put("unofYmd", unofYmd);
        
        List<Map<String, Object>> dsList = ofwkUpcsdClngSprtService.selectOfwkUpcsdClngRegDtl(mapParam);
        
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/insertOfwkUpcsdClng.do")
    public View insertOfwkUpcsdClng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	HttpSession session = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String loginId = "";
        if(!loginVO.getId().isEmpty())
        	loginId = loginVO.getId();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dsList");
    	
        String userId = searchParam.getValue("CNSLTNT_ID");
        String unofYmd = searchParam.getValue("UNOF_YMD");
        String lvffcCsCn = searchParam.getValue("LVFFC_CS_CN");
        String atfino= searchParam.getValue("ATFINO");

        // 파일삭제여부
        String fileDelYn = searchParam.getValue("fileDelYn");
        if (StringUtil.isBlank(fileDelYn)) {
        	fileDelYn = "N";
        }
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("userId", userId);
        mapParam.put("unofYmd", unofYmd);
        mapParam.put("lvffcCsCn", lvffcCsCn);
        mapParam.put("atfino", atfino);
        mapParam.put("fileDelYn", fileDelYn);
        mapParam.put("loginId", loginId);
        
        ofwkUpcsdClngSprtService.insertOfwkUpcsdClng(mapParam);
        
    	return new JSONDataView();
    }
    
    
}