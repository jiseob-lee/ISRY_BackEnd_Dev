/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.web;

import java.util.ArrayList;
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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
//import isry.couns.taskwkschmng.schprecon.service.DailyschService;
import isry.couns.taskwkschmng.schprecon.service.dailyschAllService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService;



@Controller
@Api(value = "workChgAplyController Controller")
                  
@RequestMapping("/workmng") 
public class workChgAplyController  extends IsryBaseController {

	@Autowired
	private isry.couns.taskwkschmng.schprecon.service.workChgAplyService workChgAplyService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectWorkSearchListFrom.do")
	public View selectWorkSearchListFrom(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 2023.01.04 부서 코드 추가 _ Jeong.Won.Je
		String varId = "";				// 접속 계정의 ID
		String userDept = "";			// 접속 계정의 부서 코드 

		//사용자정보	
		
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

 		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
 			
 			varId = loginVO.getId();
 			
 		} else {
 			
 			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
 			
 		}
 		
 		if (loginVO != null && loginVO.getDeptCd() != null && !"".equals(loginVO.getDeptCd())) {
 			
 			userDept = loginVO.getDeptCd();
 			
 		} else {
 			
 			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
 			
 		}

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmWorkSearchFrom");
		
		String workYmd = searchParam.getValue("SEARCH_YMD_FROM");
		mapParam.put("WORKYMD", workYmd);
		mapParam.put("USERID", varId);
		mapParam.put("deptCd", userDept);
		
		
		List<Map<String, Object>> dsCyberList1 = workChgAplyService.selectWorkListFrom(mapParam);
		
		for (Map<String, Object> map : dsCyberList1) {
			if ( map.get("FLNM") != null && !map.get("FLNM").toString().isEmpty() )
				map.replace("WORKER_FROM", map.get("FLNM").toString()+", "+map.get("WORKER_FROM").toString());
		}
		
		dataRequest.setResponse("dsWorkListFrom", dsCyberList1);
		
		return new JSONDataView();

	}

	@RequestMapping("/selectWorkSearchListTo.do")
	public View selectWorkSearchListTo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 2023.01.04 부서 코드 추가 _ Jeong.Won.Je
		String varId = "";				// 접속 계정의 ID
		String userDept = "";			// 접속 계정의 부서 코드 
		
		//사용자정보	
		
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

 		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
 			
 			varId = loginVO.getId();
 			
 		} else {
 			
 			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
 			
 		}
 		
 		if (loginVO != null && loginVO.getDeptCd() != null && !"".equals(loginVO.getDeptCd())) {
 			
 			userDept = loginVO.getDeptCd();
 			
 		} else {
 			
 			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
 			
 		}
 		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmWorkSearchTo");
		
		String workYmd = searchParam.getValue("SEARCH_YMD_TO");
		mapParam.put("WORKYMD", workYmd);
		mapParam.put("USERID", varId);
		mapParam.put("deptCd", userDept);
		
		List<Map<String, Object>> dsCyberList1 = workChgAplyService.selectWorkListTo(mapParam);
		
		for (Map<String, Object> map : dsCyberList1) {
			if ( map.get("FLNM") != null && !map.get("FLNM").toString().isEmpty() )
				map.replace("WORKER_TO", map.get("FLNM").toString()+", "+map.get("WORKER_TO").toString());
		}
		
		dataRequest.setResponse("dsWorkListTo", dsCyberList1);
		
		return new JSONDataView();

	}

	@RequestMapping("/insertWorkChgAply.do")
	public View insertWorkChgAply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		
	//사용자정보	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSave");
//System.out.println("DDD : "+searchParam.toString());		

	    String chgDmndId = searchParam.getValue("CHG_DMND_ID");
		mapParam.put("CHG_DMND_ID",   chgDmndId);

		mapParam.put("CHG_DMND_WORK_YMD",   searchParam.getValue("CHG_DMND_YMD"));

	    String chgDmndTime = searchParam.getValue("CHG_DMND_TIME");
		mapParam.put("CHG_DMND_WORK_HR_CN",   chgDmndTime);

	    String chgDmndSn = searchParam.getValue("CHG_DMND_SN");
		mapParam.put("CHG_DMND_SN",   chgDmndSn);

		mapParam.put("CHG_TRGT_ID",   searchParam.getValue("CHG_TRGT_ID"));

		mapParam.put("CHG_TRGT_WORK_YMD",   searchParam.getValue("CHG_TRGT_YMD"));

	    String chgTrgtTime = searchParam.getValue("CHG_TRGT_TIME");
		mapParam.put("CHG_TRGT_WORK_HR_CN",   chgTrgtTime);

		String chgTrgtSn = searchParam.getValue("CHG_TRGT_SN");
		mapParam.put("CHG_TRGT_SN",   chgTrgtSn);
		
	    String chgCsCn = searchParam.getValue("CHG_CS_CN");
		mapParam.put("CHG_CS_CN",   chgCsCn);

		workChgAplyService.insertWorkChgAply(request, dataRequest);
		
		return new JSONDataView();

	}
	
	@RequestMapping("/selectWorkChgAplyList.do")
	public View selectWorkChgAplyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		
	//사용자정보	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchList");
		//System.out.println("DDD : "+searchParam.toString());		

	    String chgDmndId = searchParam.getValue("CHG_DMND_ID");
		mapParam.put("CHGDMNDID",   chgDmndId);
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
        
		List<Map<String, Object>> dsSearchList = workChgAplyService.selectWorkChgAplyList(mapParam);
		
    	for (Map<String, Object> map : dsSearchList) {
    		
    		String tmpChgDmndNm = StringUtil.nullConvert(map.get("CHG_DMND_NM"));
    		if (StringUtil.isEmpty(tmpChgDmndNm)) {
    			map.put("CHG_DMND_ID", "");
    		} else {
    			String tmpChgDmndId = StringUtil.nullConvert(map.get("CHG_DMND_ID"));
    			map.put("CHG_DMND_ID", String.format("%s (%s)", tmpChgDmndId, tmpChgDmndNm));
    		}
    		
    		String tmpChgTrgtNm = StringUtil.nullConvert(map.get("CHG_TRGT_NM"));
    		if (StringUtil.isEmpty(tmpChgTrgtNm)) {
    			map.put("CHG_TRGT_ID", "");
    		} else {
    			String tmpChgTrgtId = StringUtil.nullConvert(map.get("CHG_TRGT_ID"));
    			map.put("CHG_TRGT_ID", String.format("%s (%s)", tmpChgTrgtId, tmpChgTrgtNm));
    		}
		}
    	if (dsSearchList.size() > 0 )
    		totalCount = Integer.parseInt(dsSearchList.get(0).get("TOTAL_COUNT").toString());
    	else 
    		totalCount = 0;
    	resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsSearchList", dsSearchList);
		dataRequest.setResponse("dmPage", resPage);
		
		return new JSONDataView();

	}
	
	@RequestMapping("/selectWorkChgAplyDetail.do")
	public View selectWorkChgAplyDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		
	//사용자정보	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+searchParam.toString());		

		mapParam.put("INDEX_SN",   searchParam.getValue("INDEX_SN"));
		
		List<Map<String, Object>> dsList = workChgAplyService.selectWorkChgAplyDetail(mapParam);
		
    	for (Map<String, Object> map : dsList) {
    		if (map.get("CHG_DMND_NM") == null || map.get("CHG_DMND_NM").toString().isEmpty() )
    			map.replace("CHG_DMND_NM", "");

    		map.put("PRE_CHG_DMND", map.get("CHG_DMND_WORK_YMD").toString()+" "+map.get("CHG_DMND_NM"));
//    		map.replace("CHG_DMND_WORK_YMD", map.get("CHG_DMND_WORK_YMD").toString()+" "+map.get("CHG_DMND_NM"));
    		
    		if (map.get("CHG_TRGT_NM") == null || map.get("CHG_TRGT_NM").toString().isEmpty() )
    			map.replace("CHG_TRGT_NM", "");

    		map.put("PRE_CHG_TRGT", map.get("CHG_TRGT_WORK_YMD").toString()+" "+map.get("CHG_TRGT_NM"));
//    		map.replace("CHG_TRGT_WORK_YMD", map.get("CHG_TRGT_WORK_YMD").toString()+" "+map.get("CHG_TRGT_NM"));
    		
			map.put("CHG_DMND_RESULT", map.get("CHG_DMND_WORK_YMD").toString()+" "+map.get("CHG_TRGT_NM"));
    		map.put("CHG_TRGT_RESULT", map.get("CHG_TRGT_WORK_YMD").toString()+" "+map.get("CHG_DMND_NM"));
    		

    	}
    	Map<String, Object> dmList = dsList.get(0);
//System.out.println("dmList : "+dmList.toString());		
		dataRequest.setResponse("dmList", dmList);
		
		return new JSONDataView();

	}
		
	@RequestMapping("/deleteWorkChgAplyDetail.do")
	public View deleteWorkChgAplyDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+searchParam.toString());		

		mapParam.put("INDEX_SN",   searchParam.getValue("INDEX_SN"));

		workChgAplyService.deleteWorkChgAplyDetail(mapParam);
		
		return new JSONDataView();

	}
	
		
}