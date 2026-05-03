/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consttsincry.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.consttsincry.service.ConsttSincryService;

@Controller
@Api(value = "ConsttSincryController Controller")
@RequestMapping("/consttSincry")
public class ConsttSincryController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "consttSincryService")
    private ConsttSincryService svc;
    
    @Resource(name = "counsService")
    private CounsService counsService;

    @RequestMapping("/selectCombo1List.do")
    public View selectCombo1List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	// 조회 조건 검색 (소속기관)
//	   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	
	   	dataRequest.setResponse("dsSearchCombo", counsService.selectOrgDeptCombo(request));
//        dataRequest.setResponse("dsSearchCombo1", searchComboList);
//        dataRequest.setResponse("dsSearchCombo2", searchComboList);

        return new JSONDataView();

    }

    /**
     * @Method명   : selectCombo3List
     * @param 	   : request
     * @param 	   : response
     * @param 	   : dataRequest
     * @return	   : List
     * @throws 	   : Exception
     * @수성자     : Jeong.Won.Je
     * @수정일     : 2023. 7. 18. 
     * @Method설명 : 상담원 목록 조회
     * 수정내용	   : 주석 추가 및 암복호화 JAVA → DB 방식으로 변경
     */
    @RequestMapping("/selectCombo3List.do")
    public View selectCombo3List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch1");
//System.out.println("DDD : "+ searchParam.toString());        
		mapParam.put("DEPT_CD", searchParam.getValue("DEPT_CD"));

        List<Map<String, Object>> list3 = svc.selectCombo3List(mapParam);
       
        dataRequest.setResponse("dsComb3", list3);
        //System.out.println("dsComb3 : "+ list3.toString());        

        return new JSONDataView();

    }

    @RequestMapping("/selectConsttSincryCnsltntList.do")
    public View selectConsttSincryCnsltntList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch1");
//System.out.println("DDD : "+ searchParam.toString());        
//        mapParam.put("instNo", searchParam.getValue("instNo"));
        mapParam.put("DEPT_CD", searchParam.getValue("DEPT_CD"));
        mapParam.put("yyyyMm", searchParam.getValue("yyyyMm"));
        mapParam.put("userId", searchParam.getValue("userId"));

        List<Map<String, Object>> list = svc.selectConsttSincryCnsltntList(request, mapParam);
		for (Map<String, Object> map : list) {
			try {
				String cnsltntFlnm = map.get("FLNM").toString();
				map.replace("FLNM", cnsltntFlnm + "(" + map.get("USER_ID").toString() + ")");
				map.replace("MSG", "(" + cnsltntFlnm + map.get("MSG2").toString());
//				
//		          ,   LL.FLNM || '님의 근무일수: '|| (LL.A100-LL.A66-LL.A77-LL.A99) ||' 일, 총 지각: '|| LL.A11 ||'회, 총 지각 시간: '
//	              || LL.A200 ||' 분, 총 휴가: '|| LL.A77 || ' 건, 총 결근: '|| LL.A99 || ' 건'    AS  MSG

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(list.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", list.get(0).get("TOTAL_COUNT"));
		}
		
		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsList1", list);

        return new JSONDataView();

    }

    @RequestMapping("/selectConsttSincryDalyList.do")
    public View selectConsttSincryDalyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch2");
//System.out.println("DDD : "+ searchParam.toString());    
//		mapParam.put("instNo", searchParam.getValue("instNo"));
        mapParam.put("DEPT_CD", searchParam.getValue("DEPT_CD"));
		mapParam.put("yyyyMm", searchParam.getValue("yyyyMm"));

        List<Map<String, Object>> list = svc.selectConsttSincryDalyList(request, mapParam);
        
        // 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(list.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", list.get(0).get("TOTAL_COUNT"));
		}
		
		dataRequest.setResponse("dmPage2", resPage);
        dataRequest.setResponse("dsList2", list);

        return new JSONDataView();

    }

}