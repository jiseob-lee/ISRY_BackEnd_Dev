/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.web;

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
import isry.couns.mngr.workaltmntmng.service.OfwkUpcsdClngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "OfwkUpcsdClngController Controller")
@RequestMapping("/workaltmntmng")
public class OfwkUpcsdClngController extends IsryBaseController {

    @Autowired
    private OfwkUpcsdClngService ofwkUpcsdClngService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;    
    
    /**
	 * @Method명   : selectOfwkUpcsdClngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 :	
	 */
    @RequestMapping("/selectOfwkUpcsdClngList.do")
    public View selectOfwkUpcsdClngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	ParameterGroup pageParam = dataRequest.getParameterGroup("dmPage");
    	
    	String flagYn = searchParam.getValue("FLAG_YN");
    	String bgngYmd = searchParam.getValue("BGNG_YMD");
    	String endYmd = searchParam.getValue("END_YMD");
    	String userInfo = searchParam.getValue("CONSTT_ID_NM");
    	String consttCkb = searchParam.getValue("CONSTT_CKB");
    	
    	// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) pageParam.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) pageParam.getValue("pageRowCount")); // 15
		int startIndex = (pageIdx - 1) * rowSize; 
		int totalCount = 0;
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
    	
    	if(consttCkb.equalsIgnoreCase("ID")) {
    		mapParam.put("userId", userInfo);
    	}else if(consttCkb.equalsIgnoreCase("NAME")) {
    		mapParam.put("userNm", userInfo);
    	}
    	mapParam.put("flagYn", flagYn);
    	mapParam.put("bgngYmd", bgngYmd);
    	mapParam.put("endYmd", endYmd);
    	
    	//totalCount = ofwkUpcsdClngService.getTotalCount(mapParam);
    	List<Map<String, Object>> dsList = ofwkUpcsdClngService.selectOfwkUpcsdClngList(mapParam);
    	
    	try {
    		totalCount = Integer.parseInt(dsList.get(0).get("TOTAL_COUNT")+"");			
		} catch (Exception e) {
			// TODO: handle exception
			totalCount = 0;
		}
    	
    	// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> dmPage = new HashMap<String, Object>();
		dmPage.put("totalCount", totalCount);
		dmPage.put("pageNo", pageIdx);
		dmPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", dmPage);
    	dataRequest.setResponse("dsList", dsList);
   	
    	return new JSONDataView();
   }
    
    @RequestMapping("/searchComboAprvDetail.do")
    public View searchComboAprvDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	List<Map<String, Object>> searchComboAprvList = ofwkUpcsdClngService.searchComboBoxAprv(null);
    	
    	dataRequest.setResponse("dsSearchComboAprv", searchComboAprvList);
    	
    	return new JSONDataView();
    }
    
    /**
	 * @Method명   : selectOfwkUpcsdClngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
    @RequestMapping("/selectOfwkUpcsdClngDetail.do")
    public View selectOfwkUpcsdClngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
       	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	String userId = searchParam.getValue("CNSLTNT_ID");
    	String unofYmd = searchParam.getValue("UNOF_YMD");
    	
    	mapParam.put("userId", userId);
    	mapParam.put("unofYmd", unofYmd);
    	
    	List<Map<String, Object>> dsList = ofwkUpcsdClngService.selectOfwkUpcsdClngDetail(mapParam);
    	
   		dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
    	
    }
    @RequestMapping("/insertOfwkUpcsdClngBatch.do")
    public View insertOfwkUpcsdClngBatch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request); 
		String loginId = "";
		loginId = loginVO.getId();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	String fromWorkYmd = searchParam.getValue("BGNG_YMD");
    	String toWorkYmd = searchParam.getValue("END_YMD");
		String retVal = "";
    	mapParam.put("loginId",loginId);
    	mapParam.put("fromWorkYmd", fromWorkYmd);
    	mapParam.put("toWorkYmd", toWorkYmd);
    	mapParam.put("retVal", retVal);
    	
    	Map<String, Object> resultMap = ofwkUpcsdClngService.insertOfwkUpcsdClngBatch(mapParam);
    	
    	retVal = resultMap.get("retVal").toString();
    	//System.out.println("retVal"+retVal);
    	dataRequest.setMetadata(true, resultMap);
    	
    	return new JSONDataView();
    }
    
    /**
	 * @Method명   : updateOfwkUpcsdClng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
    @RequestMapping("/updateOfwkUpcsdClng.do")
    public View updateOfwkUpcsdClng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			mapParam.put("LOGIN_ID", loginVO.getId());
		}
		
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dsList");
    	
    	String userId = searchParam.getValue("USER_ID");
    	String unofYmd = searchParam.getValue("UNOF_YMD");
    	String aprvSttsSeCd = searchParam.getValue("APRV_STTS_SE_CD");
    	String lvffcCsCn = searchParam.getValue("LVFFC_CS_CN");
    	String mdfcnCsCn = searchParam.getValue("MDFCN_CS_CN");
    	String atfino = searchParam.getValue("ATFINO");
    	
    	mapParam.put("userId", userId);
    	mapParam.put("unofYmd", unofYmd);
    	mapParam.put("aprvSttsSeCd", aprvSttsSeCd);
    	mapParam.put("lvffcCsCn", lvffcCsCn);
    	mapParam.put("mdfcnCsCn", mdfcnCsCn);
    	mapParam.put("atfino", atfino);
    	int ret = -1;
    	try {
    		ret = ofwkUpcsdClngService.updateOfwkUpcsdClng(mapParam);
    		//dataRequest.setResponse("dsList", dsList);
			
		} catch (Exception e) {
			// TODO: handle exception
			//log.info(e.getMessage());
		}
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/downloadOfwkUpcsdClng.do")
    public View downloadOfwkUpcsdClng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	return new JSONDataView();
    	
    }
    
}