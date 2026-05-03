/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.etxpyreqstdmng.web;

import java.util.ArrayList;
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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import io.swagger.annotations.Api;
import isry.couns.mngr.etxpyreqstdmng.service.NgtmWorkEtxpyReqstdMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "NgtmWorkEtxpyReqstdMngController Controller")
@RequestMapping("/ngtmWorkEtxpyReqstdMng")
public class NgtmWorkEtxpyReqstdMngController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "ngtmWorkEtxpyReqstdMngService")
    private NgtmWorkEtxpyReqstdMngService svc;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping("/selectNgtmWorkEtxpyReqstdMngList.do")
    public View selectNgtmWorkEtxpyReqstdMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString());        
       
        mapParam.put("yyyyMm", searchParam.getValue("yyyyMm"));
        mapParam.put("YYYYMMDD", searchParam.getValue("yyyyMm") + "01");

        List<Map<String, Object>> list = svc.selectNgtmWorkEtxpyReqstdMngList(mapParam);
        
        // 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(list.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", list.get(0).get("TOTAL_COUNT"));
		}

		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }
    
    @RequestMapping("/selectNgtmWorkEtxpyReqstdMngDetail.do")
    public View selectNgtmWorkEtxpyReqstdMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString());        
       
		mapParam.put("YYYYMM" , searchParam.getValue("yyyyMm"));
		mapParam.put("USER_ID", searchParam.getValue("userId"));
		

        List<Map<String, Object>> list1= svc.selectNgtmWorkEtxpyReqstdMngDetail1(mapParam);
        
        List<Map<String, Object>> list2= svc.selectNgtmWorkEtxpyReqstdMngDetail2(mapParam);
        List<Map<String, Object>> list3= svc.selectNgtmWorkEtxpyReqstdMngDetail3(mapParam);

        dataRequest.setResponse("dsList1", list1);
        dataRequest.setResponse("dsList2", list2);
        dataRequest.setResponse("dsList3", list3);

        return new JSONDataView();

    }
    
    @RequestMapping("/saveNgtmWorkEtxpyReqstdMngDetail.do")
    public View saveNgtmWorkEtxpyReqstdMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmMemo");
//System.out.println("DDD : "+ searchParam.toString());        
       
        mapParam.put("GIVE_YM"					, searchParam.getValue("GIVE_YM"));
        mapParam.put("CONSTT_ID"				, searchParam.getValue("USER_ID"));
        mapParam.put("BIGO"             		, searchParam.getValue("BIGO"));
        mapParam.put("USER_ID"					, userId);

        int ccnt = svc.saveNgtmWorkEtxpyReqstdMngDetail(mapParam);	// 저장
        
        return new JSONDataView();

    }
     
//    @RequestMapping("/deleteWorkEtxpyUntpcMng.do")
//    public View deleteeWorkEtxpyUntpcMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//       
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//       
//        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//log.debug("DDD : "+ searchParam.toString());        
//       
//        mapParam.put("CRTR_YR"					, searchParam.getValue("CRTR_YR"));
//        
//        int ccnt = svc.deleteWorkEtxpyUntpcMng(mapParam);	// 저장
//        
//        List<Map<String, Object>> list = svc.selectWorkEtxpyUntpcMngList(mapParam);
//
//        dataRequest.setResponse("dsList", list);
//
//        return new JSONDataView();
//
//    }
     

}