/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

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

import io.swagger.annotations.Api;
import isry.couns.stats.dscsnstats.service.ConQuestionNewStatsService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


@Controller
@Api(value = "conQuestionNewStatsController Controller")
@RequestMapping("/conQuestionNewStats")
public class ConQuestionNewStatsController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());	

    @Resource(name = "conQuestionNewStatsService")
    private ConQuestionNewStatsService  conQuestionNewStatsService;
    
    @Resource(name="userLoginService")
	private UserLoginService userLoginService;
    
    @Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
    
    @RequestMapping("/selectconQuestionNewStats.do")
    public View selectconQuestionNewStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("startDate");
		String endDate   = searchParam.getValue("endDate");
		String member    = searchParam.getValue("member");
		String gender    = searchParam.getValue("gender");
		String type1     = searchParam.getValue("type1");
		String type2     = searchParam.getValue("type2");
		String lowEvalu  = searchParam.getValue("lowEvalu");

		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		mapParam.put("member", member);	
		mapParam.put("gender", gender);	
		mapParam.put("type1", type1);	
		mapParam.put("type2", type2);	
		mapParam.put("lowEvalu", lowEvalu);	

		String tabIndex	= searchParam.getValue("TABINDEX");
		int tabIndexInt	= 0;
		if (tabIndex != null && !"".equals(tabIndex) ) {
			tabIndexInt	=  Integer.parseInt(tabIndex) - 1;
		}
				
		mapParam.put("tabIndex", String.valueOf(tabIndexInt));	
		
		// 12 : 채팅
		if("12".equals(tabIndex)) {
			// 상담설문 통계 채팅 평균 만족도, 평균 채팅 대기 시간
	        List<Map<String, Object>> dsListChttWaitAvrg = conQuestionNewStatsService.selectSuryChttWaitAvrg(mapParam);
	        dataRequest.setResponse("dsListChttWaitAvrg", dsListChttWaitAvrg);	        
	        // 상담설문 통계 채팅대기 시간별 건수
	        List<Map<String, Object>> dsListChttWaitNocs = conQuestionNewStatsService.selectSuryChttWait(mapParam);
	        dataRequest.setResponse("dsListChttWaitNocs", dsListChttWaitNocs);
		}else {
			List<Map<String, Object>> dsSuryScoreStatsList = conQuestionNewStatsService.selectSuryScoreStats(mapParam);
			dataRequest.setResponse("dsSuryScoreStatsList", dsSuryScoreStatsList);
			
			List<Map<String, Object>> dsSuryScoreStatsAvgrList = conQuestionNewStatsService.selectSuryScoreStatsAvgr(mapParam);
			dataRequest.setResponse("dsSuryScoreStatsAvgrList", dsSuryScoreStatsAvgrList);
		}
				
		/*
		if("1".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 전체
	        List<Map<String, Object>> dsListAll = conQuestionNewStatsService.selectconQuestionNewStats(mapParam);
	        dataRequest.setResponse("dsListAll", dsListAll);	
		}else if("2".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 1번
	        List<Map<String, Object>> dsList1   = conQuestionNewStatsService.selectconQuestionNewStats1(mapParam);
	        dataRequest.setResponse("dsList1", dsList1);
		}else if("3".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 2번
	        List<Map<String, Object>> dsList2 = conQuestionNewStatsService.selectconQuestionNewStats2(mapParam);
	        dataRequest.setResponse("dsList2", dsList2);
		}else if("4".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 3번
	        List<Map<String, Object>> dsList3 = conQuestionNewStatsService.selectconQuestionNewStats3(mapParam);
	        dataRequest.setResponse("dsList3", dsList3);
		}else if("5".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 4번
	        List<Map<String, Object>> dsList4 = conQuestionNewStatsService.selectconQuestionNewStats4(mapParam);
	        dataRequest.setResponse("dsList4", dsList4);
		}else if("6".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 5번
	        List<Map<String, Object>> dsList5 = conQuestionNewStatsService.selectconQuestionNewStats5(mapParam);
	        dataRequest.setResponse("dsList5", dsList5);
		}else if("7".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 6번
	        List<Map<String, Object>> dsList6 = conQuestionNewStatsService.selectconQuestionNewStats6(mapParam);
	        dataRequest.setResponse("dsList6", dsList6);
		}else if("8".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 7번
	        List<Map<String, Object>> dsList7 = conQuestionNewStatsService.selectconQuestionNewStats7(mapParam);
	        dataRequest.setResponse("dsList7", dsList7);
		}else if("9".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 8번
	        List<Map<String, Object>> dsList8 = conQuestionNewStatsService.selectconQuestionNewStats8(mapParam);
	        dataRequest.setResponse("dsList8", dsList8);
		}else if("10".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionNewStatsService.selectType1NewComb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionNewStatsService.selectType2NewComb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2); 
	        // 채팅평균대기시간
	        List<Map<String, Object>> dsListChatWaitAvg = conQuestionNewStatsService.selectconQuestionNewStatsChatWaitAvg(mapParam);
	        dataRequest.setResponse("dsListChatWaitAvg", dsListChatWaitAvg);	        
	        // 채팅
	        List<Map<String, Object>> dsListChatWait = conQuestionNewStatsService.selectconQuestionNewStatsChatWait(mapParam);
	        dataRequest.setResponse("dsListChatWait", dsListChatWait);			
		}
		
		*/
		
        return new JSONDataView();

    }
    
    @RequestMapping("/onLoadConQuestion.do")
	public View onLoadConQuestion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		UserDetailsVO userVo	= userLoginService.getLoginSessionVO(request);		
		
		List<Map<String, Object>> dsSrvyStatsSeCd		= mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_STATS_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsSxdcSeCd			= mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsProbmSttsLclasSeCd	= mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		
		dataRequest.setResponse("dsSrvyStatsSeCd", 		dsSrvyStatsSeCd);						
		dataRequest.setResponse("dsSxdcSeCd", 			dsSxdcSeCd);				
		dataRequest.setResponse("dsProbmSttsLclasSeCd",	dsProbmSttsLclasSeCd);

		return new JSONDataView();
	}
}