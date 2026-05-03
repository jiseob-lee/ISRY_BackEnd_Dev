/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.etxpyaplyandinq.web;

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

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.couns.taskwksprt.etxpyaplyandinq.service.OvtiWorkEtxpyAplyService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "OvtiWorkEtxpyAplyController Controller")
@RequestMapping("/ovtiWorkEtxpyAply")
public class OvtiWorkEtxpyAplyController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
    @Resource(name = "ovtiWorkEtxpyAplyService")
    private OvtiWorkEtxpyAplyService ovtiWorkEtxpyAplyService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    /**
	 * @Method     : selectOvtiWorkEtxpyAplyInit
	 * @Method설명 : 시간외근무수당(주간/야간) 부서 존재 여부
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kim.Seong.Geun
	 * @작성일     : 2022. 08. 18. 
 	 */	
    @RequestMapping("/selectOvtiWorkEtxpyAplyInit.do")
    public View selectOvtiWorkEtxpyAplyInit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapReturn= new HashMap<String, Object>();
		HttpSession session = request.getSession();
		UserDetailsVO vo = userLoginService.getLoginSessionVO(request);
		String deptCd = vo.getDeptCd();
//System.out.println("deptCd : "+ deptCd);        
		if ( "324".equals(deptCd) || "325".equals(deptCd) || "326".equals(deptCd)) {
			mapReturn.put("RETN_VALU", "00");
		} else {
			mapReturn.put("RETN_VALU", "10");
		}
        
		dataRequest.setResponse("dmReturn" , mapReturn);
        
        return new JSONDataView();
    }
    
    /**
	 * @Method     : selectOvtiWorkEtxpyAplyList
	 * @Method설명 : 시간외근무수당(주간/야간) 목록 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kim.Seong.Geun
	 * @작성일     : 2022. 08. 18. 
 	 */	
    @RequestMapping("/selectOvtiWorkEtxpyAplyList.do")
    public View selectOvtiWorkEtxpyAplyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
        Map<String, Object> mapReturn= new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString());        
		HttpSession session = request.getSession();
		UserDetailsVO vo = userLoginService.getLoginSessionVO(request);
		mapParam.put("YYYYMM", searchParam.getValue("YYYYMM"));
		String deptCd = vo.getDeptCd();
		String userId = vo.getId();
//System.out.println("deptCd : "+ deptCd);        
		mapParam.put("DEPT_CD", deptCd );
//System.out.println("userId : "+ userId); 		
		mapParam.put("USER_ID", userId);
//System.out.println("mapParam : "+ mapParam.toString());        
	
        List<Map<String, Object>> list = ovtiWorkEtxpyAplyService.selectOvtiWorkEtxpyAplyList1(mapParam);
        List<Map<String, Object>> list2= ovtiWorkEtxpyAplyService.selectOvtiWorkEtxpyAplyList2(mapParam);
        List<Map<String, Object>> list3= ovtiWorkEtxpyAplyService.selectOvtiWorkEtxpyAplyList3(mapParam);
        List<Map<String, Object>> list4= ovtiWorkEtxpyAplyService.selectOvtiWorkEtxpyAplyList4(mapParam);

        dataRequest.setResponse("dsList" , list);
        dataRequest.setResponse("dsList2", list2);
        dataRequest.setResponse("dsList3", list3);
        dataRequest.setResponse("dsList4", list4);

        return new JSONDataView();

    }
    
    /**
	 * @Method     : insertOvtiWorkEtxpyAply
	 * @Method설명 : 시간외근무수당(주간/야간) 등록
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 14. 
 	 */	
    @RequestMapping("/insertOvtiWorkEtxpyAply.do")
    public View insertNgtmWorkEtxpyAply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
    	//  공통코드(시간외근무형태구분코드) 조회
    	List<Map<String, Object>> commonCodeList = mgmtCmmnCodeService.selectCommonCodeUnit("OVTIME_WORK_SHAPE_SE_CD", userVo.getUntTaskwk());
    	LOGGER.debug("commonCodeList === [" + commonCodeList + "]");
    	
    	// 화면에서 넘어온 데이터맵
    	ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmWeekSave");
    	ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dmNightSave");
        
    	Map<String, String> weekSaveMap = paramGroup1.getSingleValueMap();
    	Map<String, String> nightSaveMap = paramGroup2.getSingleValueMap();
    	
    	// 저장할 목록 List
    	List<Map<String, String>> saveList = new ArrayList<Map<String,String>>();
    	
    	// 주간시간외근무신청 데이터 존재 조건
    	if(!("").equals(weekSaveMap.get("USER_ID")) && weekSaveMap.get("USER_ID") != null) {
    		LOGGER.debug("weekSaveMap ==== [" + weekSaveMap + "]");
    		
    		// weekSaveMap의 OVTIME_WORK_SHAPE_SE_CD값과 일치하는 공통코드의 CMMNS_CD_VALUE_NM 찾기
			for(Map<String, Object> map : commonCodeList) {
				if(map.get("CMMNS_CD_VALUE").equals(weekSaveMap.get("OVTIME_WORK_SHAPE_SE_CD"))) {
					weekSaveMap.put("OVTIME_WORK_SHAPE_VALUE", map.get("CMMNS_CD_VALUE_NM").toString());
				}
			}
			
			weekSaveMap.put("SESSION_ID", userVo.getId());
    		
    		saveList.add(weekSaveMap);
    	}
    	
    	// 야간시간외근무신청 데이터 존재 조건
    	if(!("").equals(nightSaveMap.get("USER_ID")) && nightSaveMap.get("USER_ID") != null) {
    		LOGGER.debug("nightSaveMap ==== [" + nightSaveMap + "]");
    		
    		// nightSaveMap의 OVTIME_WORK_SHAPE_SE_CD값과 일치하는 공통코드의 CMMNS_CD_VALUE_NM 찾기
    		for(Map<String, Object> map : commonCodeList) {
				if(map.get("CMMNS_CD_VALUE").equals(nightSaveMap.get("OVTIME_WORK_SHAPE_SE_CD"))) {
					nightSaveMap.put("OVTIME_WORK_SHAPE_VALUE", map.get("CMMNS_CD_VALUE_NM").toString());
				}
			}
    		
    		nightSaveMap.put("SESSION_ID", userVo.getId());
    		
    		saveList.add(nightSaveMap);
    	}
    	
    	LOGGER.debug("saveList ==== [" + saveList + "]");
        
    	for(Map<String, String> map : saveList) {
    		ovtiWorkEtxpyAplyService.insertOvtiWorkEtxpyAply(map);
    	}

        return new JSONDataView();

    }
    
}