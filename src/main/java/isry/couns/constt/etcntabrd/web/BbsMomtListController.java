/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.constt.etcntabrd.service.BbsMomtListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "bbsMomtListController Controller")
@RequestMapping("/constt/etcntabrd")
public class BbsMomtListController extends IsryBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Autowired
    private BbsMomtListService bbsMomtListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/selectBbsMomtList.do")
    public View selectBbsMomtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
                   
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
//		String loginNm = loginVO.getUserName();

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");//조회			
		
		mapParam.put("NAME", dtlParam.getValue("NAME"));
		mapParam.put("BBSCTT_TTL_NM", dtlParam.getValue("BBSCTT_TTL_NM"));
		mapParam.put("INDEX_SN", dtlParam.getValue("INDEX_SN"));
		
        List<Map<String, Object>> dsBoardList = bbsMomtListService.selectBbsMomtList(mapParam);
        
        if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}
        
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsBoardList", dsBoardList);
        
        Map<String, String> mapDate = new HashMap<String, String>();
        
        HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		mapDate.put("loginNm", loginVO.getUserName());
		mapDate.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		
		dataRequest.setResponse("dmTime", mapDate);
        
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbsMomtDetail.do")
    public View selectBbsMomtDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		dataRequest.setResponse("dmChmtDtl", bbsMomtListService.selectBbsMomtDetail(dataRequest));
		
		return new JSONDataView();
    }

	@RequestMapping("/saveBbsMomtProc.do")
    public View saveBbsMomtProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsMomtListService.saveBbsMomtProc(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		
		message.put("INDEX_SN", returnParam.get("INDEX_SN"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	@RequestMapping(value = "/insertChmtDtl.do")
	public View insertChmtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> message = new HashMap<String, Object>();
		
		int resultVal = bbsMomtListService.insertChmtDtl(request, dataRequest);
		
		message.put("resultVal", resultVal);
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
		
	}
	
	@RequestMapping(value = "/updateChmtDtl.do")
	public View updateChmtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> message = new HashMap<String, Object>();
		
		int resultVal = bbsMomtListService.updateChmtDtl(request, dataRequest);
		
		message.put("resultVal", resultVal);
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
		
	}
	
	@RequestMapping(value = "/deleteChmtDtl.do")
	public View deleteChmtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> message = new HashMap<String, Object>();
		
		int resultVal = bbsMomtListService.deleteChmtDtl(request, dataRequest);
		
		message.put("resultVal", resultVal);
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
		
	}
	
//	@RequestMapping("/saveBbsMomtFileProc.do")
//    public View saveBbsMomtFileProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//        
//		ParameterGroup list = dataRequest.getParameterGroup("dsBoardList");
//		
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//		String userId = "";
//		
//		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
//			userId = loginVO.getId();
//		}
//		
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//		mapParam.put("BBSCTT_TTL_NM", list.getValue("BBSCTT_TTL_NM"));
//		mapParam.put("FRST_REG_DT", list.getValue("FRST_REG_DT"));
//		mapParam.put("LAST_MDFCN_DT", list.getValue("LAST_MDFCN_DT"));
//		
//		mapParam.put("FRST_RGTR_ID", userId);
//		mapParam.put("LAST_MDFR_ID", userId);
//		
//		System.out.println("고우:"+list.getValue("BBSCTT_TTL_NM"));
//		System.out.println("고우1:"+list.getValue("FRST_REG_DT"));
//		System.out.println("고우2:"+list.getValue("LAST_MDFCN_DT"));
//		System.out.println("고우3:"+mapParam.get("FRST_RGTR_ID"));
//		System.out.println("고우4:"+mapParam.get("LAST_MDFR_ID"));
//		
//		bbsMomtListService.saveBbsMomtProc(request, dataRequest);
//		
//		return new JSONDataView();
//    }
	
	
}








