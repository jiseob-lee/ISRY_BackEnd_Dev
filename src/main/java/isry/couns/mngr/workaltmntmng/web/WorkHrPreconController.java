/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import isry.couns.mngr.workaltmntmng.service.WorkHrPreconService;

@Controller
@Api(value = "WorkHrPreconController Controller")
@RequestMapping("/workaltmntmng")
public class WorkHrPreconController extends IsryBaseController {

    @Autowired
    private WorkHrPreconService workHrPreconService;
    
    @Resource(name = "counsService")
    private CounsService counsService;
    
    /**
	 * @Method명   : sampleSearchOptionWork
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 : 부서 콤보박스 조회
	 */
    @RequestMapping("/sampleSearchOptionWork.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
	   	
	   	///조회 조건 검색 (부서)
	   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
	   	
	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	
	   	return new JSONDataView();
   }
    
    /**
	 * @Method명   : selectWorkHrPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
    @RequestMapping("/selectWorkHrPreconList.do")
    public View selectWorkHrPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
	   	
	   	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
	   	
	   	//String instNo = searchParam.getValue("INST_NO").split("-")[1];
	   	String instNo	= searchParam.getValue("DEPT_CD");
	   	String crtrYm = searchParam.getValue("CRTR_YM");
	   	mapParam.put("instNo", instNo);
	   	mapParam.put("crtrYm", crtrYm);
	   	//log.info("selectWorkHrPreconList param instNo:" + instNo+" crtrYm: " + crtrYm);
	   	List<Map<String, Object>> dsList = workHrPreconService.selectWorkHrPreconList(request, mapParam); 
	   	
       	for (Map<String, Object> map : dsList) {
       		map.put("FLNM", map.get("CNSLTNT_NM"));
		}
       	
       	// 일괄 엑셀 다운로드  
       	/* 작성자 : Jeong.Won.Je */
       	List<Map<String, Object>> dsAllWorkPreTest = null;
       	List<Map<String, Object>> dsAllWorkNextTest = null;
       	List<Map<String, Object>> dsAllWorkHrPrecon = new ArrayList<Map<String,Object>>();
       	
       	// 위에서 조회한 상담원 목록으로 FOR문
       	for (Map<String,Object> dsMap : dsList) {
       		dsAllWorkPreTest = new ArrayList<Map<String,Object>>();
       		dsAllWorkNextTest = new ArrayList<Map<String,Object>>();
       		
       		// 상담원의 ID와 성명 HashMap에 추가
       		mapParam.put("userId", dsMap.get("CNSLTNT_ID"));
       		mapParam.put("FLNM", dsMap.get("FLNM"));
       		
       		//System.out.println("userId === [" + mapParam.get("userId") + "]");
       		//System.out.println("FLNM === [" + mapParam.get("FLNM") + "]");
       		
       		// 해당 HashMap을 파라미터로 넘겨 상담원의 근무시간대별 상세 내용 조회
       		List<Map<String, Object>> List = workHrPreconService.selectAllWorkHrPrecon(mapParam);
       		
       		// 조회된 상담원의 근무시간대별 상세 내용으로 FOR문
       		for(Map<String, Object> map : List) {
       			dsAllWorkPreTest.add(map);
       		}
       		
       		// 작성한 function 호출
       		makeMonthTimeTable(dsAllWorkPreTest, dsAllWorkNextTest);
       		
       		// function의 결과물로 FOR문
       		for(Map<String, Object> map : dsAllWorkNextTest) {
       			// USER_ID와 FLNM 값 추가
       			map.put("USER_ID", mapParam.get("userId"));
       			map.put("FLNM", mapParam.get("FLNM"));
       			
       			dsAllWorkHrPrecon.add(map);
       		}
       	}
       	
       	//System.out.println("dsAllWorkHrPrecon ==== [" + dsAllWorkHrPrecon + "]");
       	
	   	dataRequest.setResponse("dsList", dsList);
	   	dataRequest.setResponse("dsAllWorkPrecon", dsAllWorkHrPrecon);
    	
    	return new JSONDataView();
    }
    
    /**
	 * @Method명   : selectWorkHrPreconDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
    @RequestMapping("/selectWorkHrPreconDetail.do")
    public View selectWorkHrPreconDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
	   	
	   	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
	   	
	   	//String instNo = searchParam.getValue("INST_NO").split("-")[1];
	   	String instNo	= searchParam.getValue("DEPT_CD");
	   	String userId = searchParam.getValue("USER_ID");
	   	String crtrYm = searchParam.getValue("CRTR_YM");
	   	
	   	mapParam.put("instNo", instNo);
	   	//아래 userID에 해당하는 데이터가 없어 임시로 전체 데이터를 불러오기 위해 주석 처리함.
	   	mapParam.put("userId", userId);
	   	mapParam.put("crtrYm", crtrYm);
	   	//log.info("selectWorkHrPreconDetail param userId:" + userId+" crtrYm: " + crtrYm);
	   	List<Map<String, Object>> desList2 = new ArrayList<Map<String,Object>>();
	   	if(userId == null || userId.isEmpty()) {
	   		dataRequest.setResponse("dsList2", desList2);
	    	return new JSONDataView();
	   	}else {
	   		try {
	   			List<Map<String, Object>> srcList2 = workHrPreconService.selectWorkHrPreconDetail(mapParam); 
	   			//ROW : TIMES , COL : MONTH 변환 테이블
	   			makeMonthTimeTable(srcList2,desList2);
	   		} catch (Exception e) {
	   			// TODO: handle exception
	   		}
	   	}
	   	
	   	dataRequest.setResponse("dsList2", desList2);
	   	
    	return new JSONDataView();
    }
    
    private void makeMonthTimeTable(List<Map<String, Object>> src,List<Map<String, Object>> des) {
    	List<String> timeArray = new ArrayList<String>();
    	Map<String, Object> tempMap = null;
    	int index = 0;
    	// ROW : TIMES  , COL : MONTHS
    	for (Map<String, Object> map : src) {
    		if(map.get("CRTR_YM") != null) {
    			String month = map.get("CRTR_YM").toString().substring(4);
    			String times = map.get("TIMES").toString();
    			
    			if(!timeArray.contains(times) && times != null) {
    				timeArray.add(times);
    				tempMap = new HashMap<String, Object>();
    				tempMap.put("TIMES", times);
    				tempMap.put("MONTH".concat(month), map.get("USER_ID_CNT"));
    				des.add(index, tempMap);
    				index++;
    			}else {
    				int findIdx = 0;
    				for(int i = 0 ; i<des.size() ; i++) {
    					if(times.equals(des.get(i).get("TIMES"))){
    						findIdx = i;
    						break;
    					}
    				}
    				
    				if(des.get(findIdx).get("MONTH".concat(month))!=null) {
    					int addCntVal = Integer.parseInt(map.get("USER_ID_CNT").toString()) + Integer.parseInt(des.get(findIdx).get("MONTH".concat(month)).toString());
    					des.get(findIdx).replace("MONTH".concat(month), addCntVal);
    				}else {
    					des.get(findIdx).put("MONTH".concat(month), map.get("USER_ID_CNT"));
    				}
    			}
    		}
		}
    	//log.info("timeArray Size : " + timeArray);
    }
    
   
    
    
    
    
}