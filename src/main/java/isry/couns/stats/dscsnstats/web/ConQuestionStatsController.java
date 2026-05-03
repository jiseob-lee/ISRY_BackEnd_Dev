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

import io.swagger.annotations.Api;
import isry.couns.stats.dscsnstats.service.ConQuestionStatsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Controller
@Api(value = "conQuestionStatsController Controller")
@RequestMapping("/conQuestionStats")
public class ConQuestionStatsController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Resource(name = "conQuestionStatsService")
    private ConQuestionStatsService  conQuestionStatsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    /**
	 * 상담설문통계(~180228) 목록 조회
	 * @Method명   : selectconQuestionStats
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 11. 
	 * @Method설명 :
	 */
    @RequestMapping("/selectconQuestionStats.do")
    public View selectconQuestionStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("startDate");
		String endDate   = searchParam.getValue("endDate");
		String member    = searchParam.getValue("member");
		String gender    = searchParam.getValue("gender");
		String type1     = searchParam.getValue("type1");
		String type2     = searchParam.getValue("type2");

		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		mapParam.put("member", member);	
		mapParam.put("gender", gender);	
		mapParam.put("type1", type1);	
		mapParam.put("type2", type2);
		
		String tabIndex  = searchParam.getValue("TABINDEX");
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 전체
	        List<Map<String, Object>> dsListAll = conQuestionStatsService.selectconQuestionStats(mapParam);
	        dataRequest.setResponse("dsListAll", dsListAll);
		}else if("2".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);	        
	        // 1번
	        List<Map<String, Object>> dsList1   = conQuestionStatsService.selectconQuestionStats1(mapParam);
	        dataRequest.setResponse("dsList1", dsList1);
		}else if("3".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 2번
	        List<Map<String, Object>> dsList2 = conQuestionStatsService.selectconQuestionStats2(mapParam);
	        dataRequest.setResponse("dsList2", dsList2);
		}else if("4".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 3번
	        List<Map<String, Object>> dsList3 = conQuestionStatsService.selectconQuestionStats3(mapParam);
	        dataRequest.setResponse("dsList3", dsList3);	        
		}else if("5".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 4번
	        List<Map<String, Object>> dsList4 = conQuestionStatsService.selectconQuestionStats4(mapParam);
	        dataRequest.setResponse("dsList4", dsList4);
		}else if("6".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);	        
	        // 5번
	        List<Map<String, Object>> dsList5 = conQuestionStatsService.selectconQuestionStats5(mapParam);
	        dataRequest.setResponse("dsList5", dsList5);
		}else if("7".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 6번
	        List<Map<String, Object>> dsList6 = conQuestionStatsService.selectconQuestionStats6(mapParam);
	        dataRequest.setResponse("dsList6", dsList6);
		}else if("8".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 7번
	        List<Map<String, Object>> dsList7 = conQuestionStatsService.selectconQuestionStats7(mapParam);
	        dataRequest.setResponse("dsList7", dsList7);
		}else if("9".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 8번
	        List<Map<String, Object>> dsList8 = conQuestionStatsService.selectconQuestionStats8(mapParam);
	        dataRequest.setResponse("dsList8", dsList8);
		}else if("10".equals(tabIndex)) {
			List<Map<String, Object>> dsComb1 = conQuestionStatsService.selectType1Comb(mapParam);
	        dataRequest.setResponse("dsComb1", dsComb1);
	        List<Map<String, Object>> dsComb2 = conQuestionStatsService.selectType2Comb(mapParam);
	        dataRequest.setResponse("dsComb2", dsComb2);
	        // 채팅평균대기시간
	        List<Map<String, Object>> dsListChatWaitAvg = conQuestionStatsService.selectconQuestionStatsChatWaitAvg(mapParam);
	        dataRequest.setResponse("dsListChatWaitAvg", dsListChatWaitAvg);
	        
	        // 채팅
	        List<Map<String, Object>> dsListChatWait = conQuestionStatsService.selectconQuestionStatsChatWait(mapParam);
	        dataRequest.setResponse("dsListChatWait", dsListChatWait);
		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 설문조사 통계 목록 조회
   	 * @Method명   : selectSrvyExmnStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subSrvyExmnStatsList.do")
    public View selectSrvyExmnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String tabIndex  = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex)) { // 접근경로			
	        List<Map<String, Object>> dsList1 = conQuestionStatsService.selectSrvyExmnStatsList1(mapParam);
	        dataRequest.setResponse("dsSrvyExmnStatsList1", dsList1);
		}else if("2".equals(tabIndex)) { // 지역
			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectSrvyExmnStatsList2(mapParam);
	        dataRequest.setResponse("dsSrvyExmnStatsList2", dsList2);
		}else if("3".equals(tabIndex)) { // 서비스개선
			List<Map<String, Object>> dsList3 = conQuestionStatsService.selectSrvyExmnStatsList3(mapParam);
	        dataRequest.setResponse("dsSrvyExmnStatsList3", dsList3);
		}else if("4".equals(tabIndex)) { // 성별
			List<Map<String, Object>> dsList4 = conQuestionStatsService.selectSrvyExmnStatsList4(mapParam);
	        dataRequest.setResponse("dsSrvyExmnStatsList4", dsList4);
		}else if("5".equals(tabIndex)) { // 대상
			List<Map<String, Object>> dsList5 = conQuestionStatsService.selectSrvyExmnStatsList5(mapParam);
	        dataRequest.setResponse("dsSrvyExmnStatsList5", dsList5);
		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 이음-e 상담통계 목록 조회
   	 * @Method명   : selectCnctDscsnStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subCnctDscsnStatsList.do")
    public View selectCnctDscsnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String bbscttTypeSeCd = searchParam.getValue("BBSCTT_TYPE_SE_CD");
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		
		mapParam.put("bbscttTypeSeCd", bbscttTypeSeCd);
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
				
        List<Map<String, Object>> dsList = conQuestionStatsService.selectCnctDscsnStatsList(mapParam);
        dataRequest.setResponse("dsCnctDscsnStatsList", dsList);
		
        return new JSONDataView();
    }
    
    /**
   	 * 상담자별 설문통계 조회
   	 * @Method명   : selectConsttSrvyStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 11. 03. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subConsttSrvyStatsList.do")
    public View selectConsttSrvyStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String deptCd   = searchParam.getValue("deptCd");
		String userGroupAuthrt = searchParam.getValue("userGroupAuthrt");
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String sUserId = loginVO.getId();
        
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		mapParam.put("deptCd", deptCd);
		mapParam.put("sUserId", sUserId);
		mapParam.put("userGroupAuthrt", userGroupAuthrt);
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
				
        List<Map<String, Object>> dsList = conQuestionStatsService.selectConsttSrvyStatsList(mapParam, request);
        
        Map<String, Object> map = new HashMap<>();
		for(int i=0; i<dsList.toArray().length; i++) {
			map = dsList.get(i);
			if(map.get("NM") != null) map.put("NM", map.get("NM").toString() );
			dsList.set(i, map);
		}
        
        dataRequest.setResponse("dsConsttSrvyStatsList", dsList);
		
        return new JSONDataView();
    }
    
    /**
   	 * 이음-e 만족도통계 조회
   	 * @Method명   : selectCnctDgstfnStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subCnctDgstfnStatsList.do")
    public View selectCnctDgstfnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        Map<String, String> paramMap = searchParam.getSingleValueMap();
        
        dataRequest.setResponse("dsCnctDgstfnStatsList", conQuestionStatsService.selectCnctDgstfnStatsList(paramMap));
        
//		String startDate = searchParam.getValue("STDT");
//		String endDate   = searchParam.getValue("EDDT");
//		String tabIndex  = searchParam.getValue("TABINDEX");
//		
//		mapParam.put("startDate", startDate);
//		mapParam.put("endDate", endDate);	
//		
//		log.debug("mapParam 1111 ==>> " + mapParam.toString());
//		
//		if("1".equals(tabIndex)) { // 자녀와 함께 성장하는 부모			
//	        List<Map<String, Object>> dsList1 = conQuestionStatsService.selectCnctDgstfnStatsList1(mapParam);
//	        dataRequest.setResponse("dsCnctDgstfnStatsList1", dsList1);
//		}else if("2".equals(tabIndex)) { // 학교폭력예방
//			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectCnctDgstfnStatsList2(mapParam);
//	        dataRequest.setResponse("dsCnctDgstfnStatsList2", dsList2);
//		}else if("3".equals(tabIndex)) { // 다문화가족
//			List<Map<String, Object>> dsList3 = conQuestionStatsService.selectCnctDgstfnStatsList3(mapParam);
//	        dataRequest.setResponse("dsCnctDgstfnStatsList3", dsList3);
//		}else if("4".equals(tabIndex)) { // 이혼가정 부모교육
//			List<Map<String, Object>> dsList4 = conQuestionStatsService.selectCnctDgstfnStatsList4(mapParam);
//	        dataRequest.setResponse("dsCnctDgstfnStatsList4", dsList4);
//		}
        return new JSONDataView();

    }
    
    /**
   	 * 이음-e 변화도 조회
   	 * @Method명   : selectCnctChngList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subCnctChngList.do")
    public View selectCnctChngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");

        String bbscttTypeSeCd = searchParam.getValue("BBSCTT_TYPE_SE_CD");
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");		
		String year   	 = searchParam.getValue("YEAR");
		String month     = searchParam.getValue("MONTH");
		
		mapParam.put("bbscttTypeSeCd", bbscttTypeSeCd);
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		mapParam.put("year", year);
		mapParam.put("month", month);
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
				
        List<Map<String, Object>> dsList = conQuestionStatsService.selectCnctChngList(mapParam);
        dataRequest.setResponse("dsCnctChngList", dsList);
		
        return new JSONDataView();
    }
    
    /**
   	 * 게시판상담통계 조회
   	 * @Method명   : selectNtabrdDscsnStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 11. 01. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subNtabrdDscsnStatsList.do")
    public View selectNtabrdDscsnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
//		String startDate = searchParam.getValue("STDT");
//		String endDate   = searchParam.getValue("EDDT");		
		String type   	 = searchParam.getValue("TYPE");
		String statsYr   = searchParam.getValue("STATS_YR");
		
//		mapParam.put("startDate", startDate);
//		mapParam.put("endDate", endDate);
		mapParam.put("type", type);
		mapParam.put("statsYr", statsYr);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(type)) { // 1:내담자글
			List<Map<String, Object>> dsList = conQuestionStatsService.selectNtabrdDscsnStatsList2(mapParam);
	        dataRequest.setResponse("dsNtabrdDscsnStatsList", dsList);	
		}else if("2".equals(type)) { // 2:상담자글
			List<Map<String, Object>> dsList = conQuestionStatsService.selectNtabrdDscsnStatsList(mapParam);
	        dataRequest.setResponse("dsNtabrdDscsnStatsList", dsList);
		}else {
			List<Map<String, Object>> dsList = conQuestionStatsService.selectNtabrdDscsnStatsList(mapParam);
	        dataRequest.setResponse("dsNtabrdDscsnStatsList", dsList);
		}
		
        return new JSONDataView();
    }
    
    /**
   	 * 솔로봇 상담 통계 조회
   	 * @Method명   : selectSlrbyDscsnStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subSlrbyDscsnStatsList.do")
    public View selectSlrbyDscsnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String tabIndex  = searchParam.getValue("TABINDEX");
		String startDate = searchParam.getValue("START_DT");
		String endDate   = searchParam.getValue("END_DT");
		
		mapParam.put("START_DT", startDate);
		mapParam.put("END_DT", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex)) { // 채팅상담(성별)	
	        List<Map<String, Object>> dsList1 = conQuestionStatsService.selectSlrbyDscsnStatsList1(mapParam);
	        dataRequest.setResponse("dsSlrbyDscsnStatsList1", dsList1);
		}else if("2".equals(tabIndex)) { // 채팅상담(청소년상태)
			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectSlrbyDscsnStatsList2(mapParam);
	        dataRequest.setResponse("dsSlrbyDscsnStatsList2", dsList2);
		}else if("3".equals(tabIndex)) { // 채팅상담(나이:실시건수)
			List<Map<String, Object>> dsList3 = conQuestionStatsService.selectSlrbyDscsnStatsList3(mapParam);
	        dataRequest.setResponse("dsSlrbyDscsnStatsList3", dsList3);
		}else if("4".equals(tabIndex)) { // 채팅상담(나이:게시판건수)
			List<Map<String, Object>> dsList4 = conQuestionStatsService.selectSlrbyDscsnStatsList4(mapParam);
	        dataRequest.setResponse("dsSlrbyDscsnStatsList4", dsList4);
		}else if("5".equals(tabIndex)) { // 미디어상담
			List<Map<String, Object>> dsList5 = conQuestionStatsService.selectSlrbyDscsnStatsList5(mapParam);
	        dataRequest.setResponse("dsSlrbyDscsnStatsList5", dsList5);
		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 솔로봇 만족도 통계(신 2109~) 조회
   	 * @Method명   : selectSlrbyDgstfnStatsNewList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subSlrbyDgstfnStatsNewList.do")
    public View selectSlrbyDgstfnStatsNewList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String tabIndex  = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 2222 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex)) { // 게임상담			
	        List<Map<String, Object>> dsList1 = conQuestionStatsService.selectSlrbyDgstfnStatsNewList1(mapParam);
	        dataRequest.setResponse("dsSlrbyDgstfnStatsNewList1", dsList1);
		}else if("2".equals(tabIndex)) { // 영상상담
			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectSlrbyDgstfnStatsNewList2(mapParam);
	        dataRequest.setResponse("dsSlrbyDgstfnStatsNewList2", dsList2);
		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 고민해결백과 만족도 통계 조회
   	 * @Method명   : selectGriefSltnAlkiolDgstfnStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 12. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subGriefSltnAlkiolDgstfnStatsList.do")
    public View selectGriefSltnAlkiolDgstfnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String bbscttTypeSeCd = searchParam.getValue("BBSCTT_TYPE_SE_CD");
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		
		mapParam.put("bbscttTypeSeCd", bbscttTypeSeCd);
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		List<Map<String, Object>> dsList = conQuestionStatsService.selectGriefSltnAlkiolDgstfnStatsList(mapParam);
        dataRequest.setResponse("dsGriefSltnAlkiolDgstfnStatsList", dsList);
        
        return new JSONDataView();

    }
    
    /**
   	 * 웹심리검사 통계 조회
   	 * @Method명   : selectWeposInspStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 13. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subWeposInspStatsList.do")
    public View selectWeposInspStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
//		String tabIndex  = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		List<Map<String, Object>> dsList = conQuestionStatsService.selectWeposInspStatsList(mapParam);
//        dataRequest.setResponse("dsWeposInspStatsList", dsList);
        List<Map<String, Object>> dsListSe2 = conQuestionStatsService.selectWeposInspStatsListSe2(mapParam);
        dsListSe2.addAll(dsList);
        dataRequest.setResponse("dsWeposInspStatsList", dsListSe2);
        
//		if("1".equals(tabIndex)) { // 검사결과현황			
//	        List<Map<String, Object>> dsList1 = conQuestionStatsService.selectWeposInspStatsList1(mapParam);
//	        dataRequest.setResponse("dsWeposInspStatsList1", dsList1);
//		}else if("2".equals(tabIndex)) { // 검사결과
//			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectWeposInspStatsList2(mapParam);
//	        dataRequest.setResponse("dsWeposInspStatsList2", dsList2);
//		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 웹심리검사 통계 조회_결과
   	 * @Method명   : selectWeposInspStatsDetailResultList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 19. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subWeposInspStatsDetailResultList.do")
    public View selectWeposInspStatsDetailResultList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

    	dataRequest.setResponse("dsWeposInspStatsList2", conQuestionStatsService.selectWeposInspStatsDetailResultList(dataRequest));
		
        return new JSONDataView();

    }
    
    /**
   	 * 웹심리검사 통계 조회_디테일
   	 * @Method명   : selectWeposInspStatsDetailList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 18. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subWeposInspStatsDetailList.do")
    public View selectWeposInspStatsDetailList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
//		String tabIndex  = searchParam.getValue("TABINDEX");
		String oSn       = searchParam.getValue("WEPOS_MLSFC_SN");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("05".equals(oSn)) {
			List<Map<String, Object>> dsList05 = conQuestionStatsService.selectWeposInspStatsList05(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList05);
		}else if("09".equals(oSn)){
			List<Map<String, Object>> dsList09 = conQuestionStatsService.selectWeposInspStatsList09(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList09);
		}else if("11".equals(oSn)){
			List<Map<String, Object>> dsList11 = conQuestionStatsService.selectWeposInspStatsList11(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList11);
		}else if("17".equals(oSn)){
			List<Map<String, Object>> dsList17 = conQuestionStatsService.selectWeposInspStatsList17(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList17);
		}else if("07".equals(oSn)){
			List<Map<String, Object>> dsList07 = conQuestionStatsService.selectWeposInspStatsList07(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList07);
		}else if("KK".equals(oSn)){
			List<Map<String, Object>> dsListKK = conQuestionStatsService.selectWeposInspStatsListKK(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListKK);
		}else if("SS".equals(oSn)){
			List<Map<String, Object>> dsListSS = conQuestionStatsService.selectWeposInspStatsListSS(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListSS);
		}else if("KP".equals(oSn)){
			List<Map<String, Object>> dsListKP = conQuestionStatsService.selectWeposInspStatsListKP(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListKP);
		}else if("CAGI".equals(oSn)){
			List<Map<String, Object>> dsListCAGI = conQuestionStatsService.selectWeposInspStatsListCAGI(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListCAGI);
		}else if("01".equals(oSn)){
			List<Map<String, Object>> dsList01 = conQuestionStatsService.selectWeposInspStatsList01(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList01);
		}else if("02".equals(oSn)){
			List<Map<String, Object>> dsList02 = conQuestionStatsService.selectWeposInspStatsList02(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList02);
		}else if("03".equals(oSn)){
			List<Map<String, Object>> dsList03 = conQuestionStatsService.selectWeposInspStatsList03(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList03);
		}else if("04".equals(oSn)){
			List<Map<String, Object>> dsList04 = conQuestionStatsService.selectWeposInspStatsList04(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList04);
		}else if("KC".equals(oSn)){
			List<Map<String, Object>> dsListKC = conQuestionStatsService.selectWeposInspStatsListKC(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListKC);
		}else if("SC".equals(oSn)){
			List<Map<String, Object>> dsListSC = conQuestionStatsService.selectWeposInspStatsListSC(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListSC);
		}else if("ST".equals(oSn)){
			List<Map<String, Object>> dsListST = conQuestionStatsService.selectWeposInspStatsListST(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListST);
		}else if("ANGR".equals(oSn)){
			List<Map<String, Object>> dsListANGR = conQuestionStatsService.selectWeposInspStatsListANGR(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListANGR);
		}else if("LIFE".equals(oSn)){
			List<Map<String, Object>> dsListLIFE = conQuestionStatsService.selectWeposInspStatsListLIFE(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListLIFE);
		}else if("UR".equals(oSn)){
			List<Map<String, Object>> dsListUR = conQuestionStatsService.selectWeposInspStatsListUR(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListUR);
		}else if("MOM".equals(oSn)){
			List<Map<String, Object>> dsListMOM = conQuestionStatsService.selectWeposInspStatsListMOM(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsListMOM);
		}else if("1".equals(oSn)){
			List<Map<String, Object>> dsList1 = conQuestionStatsService.selectWeposInspStatsDetailList1(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList1);
		}else if("2".equals(oSn)){
			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectWeposInspStatsDetailList2(mapParam);
	        dataRequest.setResponse("dsWeposInspStatsList1", dsList2);
		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 솔로복만족도 통계 신_디테일
   	 * @Method명   : selectSlrbyDgstfnStatsNewDetailList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 25.
   	 * @수정자     : Kim.Hai.Ryong
   	 * @수정일		 : 2023. 02. 17
   	 * @Method설명 :
   	 */
    @RequestMapping("/subSlrbyDgstfnStatsNewDetailList.do")
    public View selectSlrbyDgstfnStatsNewDetailList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String slrbtCD   = searchParam.getValue("SLRBTCD"); // 솔로봇상담소분류구분코드
		String oTap      = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		mapParam.put("slrbtCD", slrbtCD);
		
		log.debug("mapParam 1122## ==>> " + mapParam.toString());
		
		if("1".equals(oTap)) {
			List<Map<String, Object>> dsList1 = conQuestionStatsService.selectSlrbyDgstfnStatsNewDetailList1(mapParam);
	        dataRequest.setResponse("dsSlrbyDgstfnStatsNewDetailList", dsList1);	
		}else if("2".equals(oTap)) {
			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectSlrbyDgstfnStatsNewDetailList2(mapParam);
	        dataRequest.setResponse("dsSlrbyDgstfnStatsNewDetailList", dsList2);
		}
		
        return new JSONDataView();

    }
    
    /**
   	 * 연계실적 통계 조회
   	 * @Method명   : selectLinkPrfmncStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 13. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subLinkPrfmncStatsList.do")
    public View selectLinkPrfmncStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String tabIndex  = searchParam.getValue("TABINDEX");
		String tabIndex2  = searchParam.getValue("TABINDEX2");
		String gubun  = searchParam.getValue("GUBUN");
		String bbscttTypeSeCd  = searchParam.getValue("CD");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);
		mapParam.put("gubun", gubun);	
		mapParam.put("bbscttTypeSeCd", bbscttTypeSeCd);
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex2)) { // 연계실적
			if("1".equals(tabIndex)) { // 연계기관별실적		
				List<Map<String, Object>> dsList1 = conQuestionStatsService.selectLinkPrfmncStatsList1(mapParam);
				dataRequest.setResponse("dsLinkPrfmncStatsList1", dsList1);
			}else if("2".equals(tabIndex)) { // 연계방법별실적
		        if("전체".equals(gubun)) {
		        	List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsList2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}else if("채팅".equals(gubun)) {
		       	List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsListCHTT2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}else if("비밀".equals(gubun)) {
					List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsListSECRE2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}else if("네이버".equals(gubun)) {
					List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsListLINK2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}else if("솔로봇".equals(gubun)) {
					List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsListSLRBT2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}else if("아웃리치".equals(gubun)) {
					List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsListOUTRC2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}else if("오픈채팅".equals(gubun)) {
					List<Map<String, Object>> dsList2 = conQuestionStatsService.selectLinkPrfmncStatsListOpenChtt2(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList2", dsList2);
				}	     
			}else if("3".equals(tabIndex)) { // 기타전문기관보기			
		        if("전체".equals(gubun)) {
		        	List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsList3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}else if("채팅".equals(gubun)) {
					List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsListCHTT3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}else if("비밀".equals(gubun)) {
					List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsListSECRE3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}else if("네이버".equals(gubun)) {
					List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsListLINK3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}else if("솔로봇".equals(gubun)) {
					List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsListSLRBT3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}else if("아웃리치".equals(gubun)) {
					List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsListOUTRC3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}else if("오픈채팅".equals(gubun)) {
					List<Map<String, Object>> dsList3 = conQuestionStatsService.selectLinkPrfmncStatsListOpenChtt3(mapParam);
			        dataRequest.setResponse("dsLinkPrfmncStatsList3", dsList3);
				}	 
			}
		}else if("2".equals(tabIndex2)) { // 월별*기관별
			
			log.debug("월별*기관별 ==>> " + mapParam.toString());
			Map<String, Object> resultInst = conQuestionStatsService.selectInstList(mapParam);
            dataRequest.setResponse("dsInstList", resultInst.get("dsInstList"));    
			
		}else if("3".equals(tabIndex2)) { // 매체별*기관별
			
			log.debug("매체별*기관별 ==>> " + mapParam.toString());
			List<Map<String, Object>> dsMediaList = conQuestionStatsService.selectMediaList(mapParam);
	        dataRequest.setResponse("dsMediaList", dsMediaList);
		}
        
        return new JSONDataView();

    }
    
    /** 삭제
   	 * 아웃리치 통계 조회
   	 * @Method명   : selectOutrcStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 13. 
   	 * @Method설명 :
   	 * @사용하지않음 : 2023.07.27
   	 */
    @RequestMapping("/subOutrcStatsList.do")
    public View selectOutrcStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String tabIndex  = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex)) { // 방법별 실적 			
	        List<Map<String, Object>> dsList1 = conQuestionStatsService.selectOutrcStatsList1(mapParam);
	        dataRequest.setResponse("dsOutrcStatsList1", dsList1);
		}else if("2".equals(tabIndex)) { // 메신저상담 영역별 실적
			List<Map<String, Object>> dsList2 = conQuestionStatsService.selectOutrcStatsList2(mapParam);
	        dataRequest.setResponse("dsOutrcStatsList2", dsList2);
		}else if("3".equals(tabIndex)) { // 댓글상담 실적
			List<Map<String, Object>> dsList3 = conQuestionStatsService.selectOutrcStatsList3(mapParam);
	        dataRequest.setResponse("dsOutrcStatsList3", dsList3);
		}else if("4".equals(tabIndex)) { // 홍보 영역별 실적
			List<Map<String, Object>> dsList4 = conQuestionStatsService.selectOutrcStatsList4(mapParam);
	        dataRequest.setResponse("dsOutrcStatsList4", dsList4);
		}else if("5".equals(tabIndex)) { // 상담사별 실적
			List<Map<String, Object>> dsList5 = conQuestionStatsService.selectOutrcStatsList5(mapParam);
			
			Map<String, Object> map = new HashMap<>();
			
			for(int i=0; i<dsList5.toArray().length; i++) {
				map = dsList5.get(i);
				
				dsList5.set(i, map);
			}
			
			log.debug("dsList5 1111 ==>> " + dsList5.toString());
			
	        dataRequest.setResponse("dsOutrcStatsList5", dsList5);
		}
        
        return new JSONDataView();

    }
    
    /**
   	 * 모바일 연계실적 통계 조회
   	 * @Method명   : selectMblaLinkPrfmncStatsList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 13. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subMblaLinkPrfmncStatsList.do")
    public View selectMblaLinkPrfmncStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		String tabIndex  = searchParam.getValue("TABINDEX");
		String gubun  = searchParam.getValue("GUBUN");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);
		mapParam.put("gubun", gubun);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		if("1".equals(tabIndex)) { // 연계기관별실적		
			if("전체".equals(gubun)) {
				List<Map<String, Object>> dsList1 = conQuestionStatsService.selectMblaLinkPrfmncStatsList1(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList1", dsList1);	
			}else if("채팅".equals(gubun)) {
				List<Map<String, Object>> dsList1 = conQuestionStatsService.selectMblaLinkPrfmncStatsListCHTT1(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList1", dsList1);
			}else if("모바일".equals(gubun)) {
				List<Map<String, Object>> dsList1 = conQuestionStatsService.selectMblaLinkPrfmncStatsListMBLA1(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList1", dsList1);
			}        
		}else if("2".equals(tabIndex)) { // 연계방법별실적
			if("전체".equals(gubun)) {
				List<Map<String, Object>> dsList2 = conQuestionStatsService.selectMblaLinkPrfmncStatsList2(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList2", dsList2);
			}else if("채팅".equals(gubun)) {
				List<Map<String, Object>> dsList2 = conQuestionStatsService.selectMblaLinkPrfmncStatsListCHTT2(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList2", dsList2);
			}else if("모바일".equals(gubun)) {
				List<Map<String, Object>> dsList2 = conQuestionStatsService.selectMblaLinkPrfmncStatsListMBLA2(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList2", dsList2);
			}   
		}else if("3".equals(tabIndex)) { // 기타전문기관보기		
			if("전체".equals(gubun)) {
				List<Map<String, Object>> dsList3 = conQuestionStatsService.selectMblaLinkPrfmncStatsList3(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList3", dsList3);
			}else if("채팅".equals(gubun)) {
				List<Map<String, Object>> dsList3 = conQuestionStatsService.selectMblaLinkPrfmncStatsListCHTT3(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList3", dsList3);
			}else if("모바일".equals(gubun)) {
				List<Map<String, Object>> dsList3 = conQuestionStatsService.selectMblaLinkPrfmncStatsListMBLA3(mapParam);
		        dataRequest.setResponse("dsMblaLinkPrfmncStatsList3", dsList3);
			}   
		}
        
        return new JSONDataView();
    }
    
    /**
   	 * 고민해결백과 만족도 통계  조회_Detail
   	 * @Method명   : selectGriefSltnAlkiolDgstfnStatsDetailList
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 11. 02. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subGriefSltnAlkiolDgstfnStatsDetailList.do")
    public View selectGriefSltnAlkiolDgstfnStatsDetailList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String bbscttTypeSeCd       = searchParam.getValue("BBSCTT_TYPE_SE_CD");
        String griefSltnAlkiolSn = searchParam.getValue("GRIEF_SLTN_ALKIOL_SN");
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
		
		mapParam.put("bbscttTypeSeCd", bbscttTypeSeCd);	
		mapParam.put("griefSltnAlkiolSn", griefSltnAlkiolSn);	
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		List<Map<String, Object>> dsList = conQuestionStatsService.selectGriefSltnAlkiolDgstfnStatsDetailList(mapParam);
        dataRequest.setResponse("dsGriefSltnAlkiolDgstfnStatsDetailList", dsList);
        
        return new JSONDataView();

    }
    
    /**
   	 * 연계실적 통계 Onload 조회
   	 * @Method명   : selectLinkPrfmncStatsOnload
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 26. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subLinkPrfmncStatsOnload.do")
    public View selectLinkPrfmncStatsOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
//		String tabIndex  = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		List<Map<String, Object>> dsList = conQuestionStatsService.selectLinkPrfmncStatsOnload(mapParam);
        dataRequest.setResponse("dsLinkPrfmncStatsList", dsList);
        
        return new JSONDataView();
    }
    
    /**
   	 * (모바일)연계실적 통계 Onload 조회
   	 * @Method명   : selectMobileLinkPrfmncStatsOnload
   	 * @param request
   	 * @param response
   	 * @param dataRequest
   	 * @return
   	 * @throws Exception
   	 * @작성자     : Lee.Tae.Ho
   	 * @작성일     : 2022. 10. 26. 
   	 * @Method설명 :
   	 */
    @RequestMapping("/subMobileLinkPrfmncStatsOnload.do")
    public View selectMobileLinkPrfmncStatsOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
		String startDate = searchParam.getValue("STDT");
		String endDate   = searchParam.getValue("EDDT");
//		String tabIndex  = searchParam.getValue("TABINDEX");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
		
		log.debug("mapParam 1111 ==>> " + mapParam.toString());
		
		List<Map<String, Object>> dsList = conQuestionStatsService.selectMobileLinkPrfmncStatsOnload(mapParam);
        dataRequest.setResponse("dsMblaLinkPrfmncStatsList", dsList);
        
        return new JSONDataView();
    }
   
}