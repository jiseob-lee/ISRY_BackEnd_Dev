/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.taskwkaltmntmng.web;

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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.google.common.collect.Iterators;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.mngr.taskwkaltmntmng.service.CnnctChatReqstdService;
import isry.couns.taskwksprt.taskwkandatdmng.web.TaskwkReprtsController;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService;

@Controller
@Api(value = "CnnctChatReqstdController Controller")
@RequestMapping("/taskwkaltmntmng")
public class CnnctChatReqstdController extends IsryBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskwkReprtsController.class);
	
    @Autowired
    private CnnctChatReqstdService cnnctChatReqstdService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    static private String sTimeOfInterviewTable[] = {"17-18","16-17","15-16","14-15","13-14","11-12","10-11"};
    static private String sTimeOfMeetingTableBefore[] = {"19-20","18-19","17-18","16-17","15-16","14-15","13-14","12-13","11-12","09-10"};
    static private String sTimeOfMeetingTableAfter[] = {"20:30 ~ 00:30","17:30 ~ 21:30","14:00 ~ 18:00","10:30 ~ 14:30", "07:00 ~ 11:00","03:30 ~ 07:30","00:00 ~ 04:00"};
    static private String sDayIntColNm[]  = {
    	"ITNEUN_CHTT_RCPT_INTRVW_MON_HR_CN"
    	,"ITNEUN_CHTT_RCPT_INTRVW_TUES_HR_CN"
    	,"ITNEUN_CHTT_RCPT_INTRVW_WED_HR_CN"
    	,"ITNEUN_CHTT_RCPT_INTRVW_THUR_HR_CN"
    	,"ITNEUN_CHTT_RCPT_INTRVW_FRI_HR_CN"
    	,"ITNEUN_CHTT_RCPT_INTRVW_SAT_HR_CN"
    	,"ITNEUN_CHTT_RCPT_INTRVW_SUN_HR_CN"
    };
    static private String DayMeetColNm[] = {
		"ITNEUN_CHTT_CHTT_DSCSN_MON_HR_CN"
		,"ITNEUN_CHTT_CHTT_DSCSN_TUES_HR_CN"
		,"ITNEUN_CHTT_CHTT_DSCSN_WED_HR_CN"
		,"ITNEUN_CHTT_CHTT_DSCSN_THUR_HR_CN"
		,"ITNEUN_CHTT_CHTT_DSCSN_FRI_HR_CN"
		,"ITNEUN_CHTT_CHTT_DSCSN_SAT_HR_CN"
		,"ITNEUN_CHTT_CHTT_DSCSN_SUN_HR_CN"
    };
    
    /**
	 * @Method명   : selectCnnctChatReqstdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 17. 
	 * @Method설명 : 잇는채팅 상담신청서 목록 조회 
	 */
    @RequestMapping("/selectCnnctChatReqstdList.do")
    public View selectCnnctChatReqstdList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	log.info("CnnctChatReqstdController selectCnnctChatReqstdList");
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup pageParam = dataRequest.getParameterGroup("dmPage");
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	String startDate = searchParam.getValue("BGNG_YMD");
    	String endDate = searchParam.getValue("END_YMD");
    	String indexSn = searchParam.getValue("INDEX_SN");
    	String trprNm = searchParam.getValue("TRPR_NM");
    	
    	mapParam.put("startDate", startDate);
    	mapParam.put("endDate", endDate);
    	mapParam.put("indexSn", indexSn);
    	mapParam.put("trprNm", trprNm);
    	
    	// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) pageParam.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) pageParam.getValue("pageRowCount")); // 15
		int startIndex = (pageIdx - 1) * rowSize; 
		int totalCount = 0;
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
    	
    	List<Map<String, Object>> list = cnnctChatReqstdService.selectCnnctChatReqstdList(mapParam);
    	try {
			totalCount = Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString());
		} catch (Exception e) {
			// TODO: handle exception
			
		}
    	for (Map<String, Object> map : list) {
    		if(map.get("TRPR_NM_ENCPT") == null ) {
    			map.replace("TRPR_NM_ENCPT", "-");
    			
    		} 
    		
    		if(map.get("CONSTT_NM_ENCPT") == null ) {
    			map.replace("CONSTT_NM_ENCPT", "-");
    		} 

		}
    	// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
    	Map<String, Object> dmPage = new HashMap<String, Object>();

		dmPage.put("totalCount", totalCount);
		dmPage.put("pageNo", pageIdx);
		dmPage.put("pageRowCount", rowSize);
    	
    	dataRequest.setResponse("dsList", list);
    	dataRequest.setResponse("dmPage", dmPage);
    	return new JSONDataView();
    }
    /**
	 * @Method명   : selectCnnctChatReqstdDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 18. 
	 * @Method설명 : 잇는채팅 상담신청서 상세 조회
	 */
    @RequestMapping("/selectCnnctChatReqstdDetail.do")
    public View selectCnnctChatReqstdDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	log.info("CnnctChatReqstdController selectCnnctChatReqstdDetail");
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	String indexSn = searchParam.getValue("INDEX_SN");
    	
    	mapParam.put("indexSn", indexSn);
    	
    	List<Map<String, Object>> list = cnnctChatReqstdService.selectCnnctChatReqstdDetail(mapParam);
    	//System.out.println("DDDD : "+list.toString());
    	List<Map<String, Object>> listInfo1 = cnnctChatReqstdService.selectCnnctChatReqstdDetailInfo(mapParam);
    	List<Map<String, Object>> listInfo2 = cnnctChatReqstdService.selectCnnctChatReqstdExpInfo(mapParam);
    	List<Map<String, Object>> listInfo3 = null;
    	
    	List<Map<String, Object>> timeTableMeetingBefore = new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> timeTableMeetingAfter = new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> timeTableInterview = new ArrayList<Map<String,Object>>();
    	
    	//list.get(0).get("ITNEUN_CHTT_APLY_CS_DTL_CN");
    	String detailInfo1 = "상담신청 이유 : " + "\n"; 
    	String detailInfo2 = "";
    	
    	// 상담신청사유 (ITNEUN_CHTT_APLY_CS_SE_CD)
    	if (listInfo1.size() > 0) {
    		log.debug("listInfo1.size() ::: " + listInfo1.size());
    		for (Map<String, Object> map : listInfo1) {
        		if(map.get("CHAT_DETAIL_INFO") !=null && !"".equals(map.get("CHAT_DETAIL_INFO"))) {
        			detailInfo1 += map.get("CHAT_DETAIL_INFO");
        			detailInfo1 += "\n";
        		}
    		}
    	}
    	
    	// 상담신청 상세내용
    	if (list.get(0).get("ITNEUN_CHTT_APLY_CS_DTL_CN") != null && list.get(0).get("ITNEUN_CHTT_APLY_CS_DTL_CN") != "") {
    		detailInfo1 += "○ 상세 내용 : ";
    		detailInfo1 += list.get(0).get("ITNEUN_CHTT_APLY_CS_DTL_CN");
    		detailInfo1 += "\n";
    	}
    	
    	// 상담경험 (ITNEUN_CHTT_DSCSN_COURS_SE_CD)
    	if (listInfo2.size() > 0) {
    		log.debug("listInfo2.size() ::: " + listInfo2.size());
    		for (Map<String, Object> map : listInfo2) {
        		if(map.get("CHAT_EXP_INFO") !=null && !"".equals(map.get("CHAT_EXP_INFO"))) {
        			detailInfo1 += map.get("CHAT_EXP_INFO");
        			detailInfo1 += "\n";
        		}
        	}
    	}
    	
    	list.get(0).replace("ITNEUN_CHTT_APLY_CS_DTL_CN", detailInfo1);
    	
    	for (int i = 1; i < 13; i++) {
    		String key = "EXP" + i;
    		String key_1 = "EXP" + i + "_1";
    		String key_2 = "EXP" + i + "_2";
//    		LOGGER.debug("이게 뭐야 ::: " + ObjectUtils.isEmpty(list.get(0).get(key_2)));
    		if ("Y".equals(list.get(0).get(key))) {
    			detailInfo2 += list.get(0).get(key_1);
    			if (!ObjectUtils.isEmpty(list.get(0).get(key_2))) {
    				detailInfo2 += convertDscsnExpCnt(list.get(0).get(key_2).toString());
    			}
				detailInfo2 += "\n";
    		}
		}
    	list.get(0).replace("DSCSN_EXPRNC_YN", detailInfo2);
    	
    	for (Map<String, Object> map : list) {
    		
    		// TRPR_NM : 대상자 성명 복호화
    		if(map.get("TRPR_NM") == null) {
    			map.put("TRPR_NM", "-");
    		} 
    		
    		// TRPR_MBL_TELNO : 대상자 전화번호 복호화
    		if(map.get("TRPR_MBL_TELNO") == null) {
    			map.replace("TRPR_MBL_TELNO", "-");
    		} else {
    			map.replace("TRPR_MBL_TELNO", map.get("TRPR_MBL_TELNO").toString().replace("-", ""));
    		}
    		
    		// TRPR_EML_ADDR : 대상자 이메일 복호화
			if(map.get("TRPR_EML_ADDR") == null) {
    			map.replace("TRPR_EML_ADDR", "-");
    		} 

			// APLCNT_CTTPC_TELNO : 신청자 이메일 복호화
			if(map.get("APLCNT_CTTPC_TELNO") == null) {
				map.replace("APLCNT_CTTPC_TELNO", "-");
			} else {
				map.replace("APLCNT_CTTPC_TELNO", map.get("APLCNT_CTTPC_TELNO").toString().replace("-", ""));
			}
			
			// AGT_NM : 대리인 성명 복호화
			if(map.get("AGT_NM") == null) {
				map.replace("AGT_NM", "-");
			} 
			
			// AGT_MBL_TELNO : 대리인 전화번호 복호화
			if(map.get("AGT_MBL_TELNO") == null) {
				map.replace("AGT_MBL_TELNO", "-");
			} else {
				map.replace("AGT_MBL_TELNO", map.get("AGT_MBL_TELNO").toString().replace("-", ""));
			}
		}
    	
    	getTimetable(0,list,timeTableInterview);
    	getTimetable(1,list,timeTableMeetingBefore);
    	getTimetable(2,list,timeTableMeetingAfter);
    	
    	LOGGER.debug("timeTableMeetingAfter ::: " + timeTableMeetingAfter);
    	
    	String constId = (String)list.get(0).get("CONSTT_ID");
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	retMap.put("CONSTT_ID",constId);
    	
    	dataRequest.setResponse("dsDetail", list);
    	dataRequest.setResponse("dmComboCouns", retMap);
    	dataRequest.setResponse("dsTableInterview", timeTableInterview);
    	dataRequest.setResponse("dsTableCharBefore", timeTableMeetingBefore);
    	dataRequest.setResponse("dsTableChatAfter", timeTableMeetingAfter);
    	//System.out.println("DDDD   timeTableInterview : "+timeTableInterview.toString());
    	//System.out.println("timeTableMeetingBefore : "+timeTableMeetingBefore.toString());
    	//System.out.println("timeTableMeetingAfter : "+timeTableMeetingAfter.toString());
    	return new JSONDataView();
    }
    
    /**
	 * @Method명   : processCnnctChatReqstd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 20. 
	 * @Method설명 : 잇는채팅 상담신청서 상담자 할당
	 */
    @RequestMapping("/processCnnctChatReqstd.do")
    public View processCnnctChatReqstd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}
		
//		try {
	    	Map<String, Object> mapParam = new HashMap<String, Object>();
	    	ParameterGroup searchParam1 = dataRequest.getParameterGroup("dmComboCouns");
	    	ParameterGroup searchParam2 = dataRequest.getParameterGroup("dmSearch");
	    	ParameterGroup searchParam3 = dataRequest.getParameterGroup("dsDetail");
	    	//System.out.println("searchParam1    ::"+searchParam1.toString());
	    	//System.out.println("searchParam2    ::"+searchParam2.toString());
	    	//System.out.println("searchParam3    ::"+searchParam3.toString());
	    	String consttId = searchParam1.getValue("CONSTT_ID");
	    	//String consttNm = searchParam1.getValue("CONSTT_ID").split(",")[1];
	    	String indexSn = searchParam2.getValue("INDEX_SN"); //잇는채팅 인덱스 (=AYA150 BBSCTT_ESNTAL_NO)
	    	String atfino = searchParam3.getValue("ATFINO");
	    	//System.out.println("zzzzzzzz::"+atfino.length());
	    	String indexSn2 = searchParam2.getValue("INDEX_SN_150"); //상담사 배정 인덱스
	    	int ret = -1;
	    	
	    	mapParam.put("SESS_USER_ID", sUserId);
	    	mapParam.put("consttId",consttId.trim());
	    	//mapParam.put("consttNm",consttNm);
	    	mapParam.put("indexSn",indexSn);
	    	//mapParam.put("indexSn2",indexSn2);
	    	mapParam.put("atfino", atfino);
	    	//System.out.println("aaaaaaaaaaaaaaa::"+mapParam.toString());
	    	Map<String, Object> mapParam1 = mapParam;
	    	
	    	if(atfino.length() > 0 ) {
	    		//System.out.println("nunununununu::"+atfino);	    			
	    		cnnctChatReqstdService.updateFileAffi(mapParam1);
	    	}
	    	cnnctChatReqstdService.processCnnctChatReqstd(mapParam); //등록
	    	
//		} catch (Exception e) {
//			// TODO: handle exception
//			//System.out.println("MSG FROM processCnnctChatReqstd" + e.getMessage());
//		}
    	
    	return new JSONDataView();
    }
    
//    @RequestMapping("/processCnnctChatReqstdDeleteFile.do")
//    public View processCnnctChatReqstdDeleteFile(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//    	
//    	ParameterGroup searchParam = dataRequest.getParameterGroup("dsDetail");
//    	System.out.println("DDD ::"+searchParam.toString());
//    	Map<String, Object> mapParam = new HashMap<String, Object>();
//    	
//    	
//		cnnctChatReqstdService.updateFileAffi(mapParam);
//		
//		return new JSONDataView();
//    }
    
    /**
	 * @Method명   : updateCnnctChatReqstd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 :	잇는채팅 상담신청서 상담자 할당 삭제
	 */
    @RequestMapping("/updateCnnctChatReqstd.do")
    public View updateCnnctChatReqstd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	log.info("CnnctChatReqstdController updateCnnctChatReqstd");
    	HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}
    	
    	try {
    		Map<String, Object> mapParam = new HashMap<String, Object>();
    		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    		//String indexSn = searchParam.getValue("INDEX_SN");
    		String indexSn = searchParam.getValue("INDEX_SN");
    		int ret = -1;
    		mapParam.put("indexSn",indexSn);
    		mapParam.put("loginId", sUserId);
    		ret = cnnctChatReqstdService.updateCnnctChatReqstd(mapParam); //삭제
			
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("MSG FROM updateCnnctChatReqstd" + e.getMessage());
		}
    	
    	return new JSONDataView();
    }
    	
    	
    
    /**
	 * @Method명   : searchComboOption
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 18. 
	 * @Method설명 : 잇는채팅 상담신청서 상세 화면에서 상담자 리스트 (콤보박스)
	 */
    @RequestMapping("/searchComboOption.do")
    public View searchComboOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
	   	log.info("CnnctChatReqstdController searchComboOption");
	   	
	   	///할당 가능 한 상당자 리스트 AYC470
	   	List<Map<String, Object>> searchComboList = cnnctChatReqstdService.searchComboOption(); /* *할당 할 수 있는 상담자 콤보박스 */
	   	
	   	dataRequest.setResponse("dsCounsAgnCombo", searchComboList);
	   	
	   	return new JSONDataView();
   }
   
	private String convertDscsnExpCnt(String dscsnExpCnt) throws Exception {
			
		switch (dscsnExpCnt) {
		case "1-5_week" : dscsnExpCnt = "일주일에 1회 이상 ~ 5회 미만";
			break;
		case "over5_week" : dscsnExpCnt = "일주일에 5회 이상";
			break;
		case "1-5_month" : dscsnExpCnt = "한달에 1회 이상 ~ 5회 미만";
			break;
		case "over5_month" : dscsnExpCnt = "한달에 5회 이상";
			break;
		case "1-5_year" : dscsnExpCnt = "1년에 1회 이상 ~ 5회 미만";
			break;
		case "over5_year" : dscsnExpCnt = "1년에 5회 이상";
			break;
		case "0m-1m" : dscsnExpCnt = "1개월 미만";
			break;
		case "1m-3m" : dscsnExpCnt = "1개월 이상 ~ 3개월 미만";
			break;
		case "3m-6m" : dscsnExpCnt = "3개월 이상 ~ 6개월 미만";
			break;
		case "6m-1y" : dscsnExpCnt = "6개월 이상 ~ 1년 미만";
			break;
		case "1y-3y" : dscsnExpCnt = "1년 이상 ~ 3년 미만";
			break;
		case "3y-5y" : dscsnExpCnt = "3년 이상 ~ 5년 미만";
			break;
		case "5y-over" : dscsnExpCnt = "5년 이상";
			break;
			
		default : dscsnExpCnt = "-";
			break;
		}
		
		return dscsnExpCnt;
	}
   
    
   private boolean getTimetable(int nType, List<Map<String, Object>> src, List<Map<String, Object>> retVal){
   	try {
    	String arrDayColNm[] = null;
    	String arrTimeTable[] = null;
    	if(nType == 0) {
    		arrDayColNm = sDayIntColNm;
    		arrTimeTable = sTimeOfInterviewTable;
    	}else if(nType == 1) {
    		arrDayColNm = sDayIntColNm;
    		arrTimeTable = sTimeOfMeetingTableBefore;
    	}else if(nType == 2) {
    		arrDayColNm = DayMeetColNm;
    		arrTimeTable = sTimeOfMeetingTableAfter;
    	}
    	if(arrDayColNm == null || arrTimeTable == null)	return false;
    	
    	Map<String, ArrayList> mapDayWithTimes = new HashMap<String, ArrayList>();
    	
    	for (String tmpColDay : arrDayColNm) { //각 요일마다 배열로 시간대를 담아 놓는다.
   			String tempChkDayTime = null;
   			String tmpArr[] = null;
   			ArrayList<String> tempChkDayTimeList = new ArrayList<String>();
   			
   			tempChkDayTime = (String)(src.get(0).get(tmpColDay));
   			if(tempChkDayTime != null) {
   				tmpArr = tempChkDayTime.split(",");
   				
   				for (String tempTime : tmpArr) {
   					tempChkDayTimeList.add(tempTime);//각 요일의 시간대를 arraylist에 담는다.
   				}
   			}
   			mapDayWithTimes.put(tmpColDay, tempChkDayTimeList); // 각 요일의 컬럼 이름과 그 요일에 해당하는 시간대들을 담는다.
		}
    	
    	for (String element : arrTimeTable) {
    		int index = 0;
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("TIMES", element);
    		
    		for (String temp : arrDayColNm) {
    			LOGGER.debug("element :: " + element);
    			LOGGER.debug("temp :: " + temp);
    			LOGGER.debug("mapDayWithTimes.get(temp).contains(element) :: " + mapDayWithTimes.get(temp).contains(element));
    			if(mapDayWithTimes.get(temp).contains(element)) {
    				map.put(temp, "가능");
    			}else {
    				map.put(temp, "");
    			}
			}    		
    		retVal.add(index, map);
    		index++;
		}
    	return true;
   	}catch(Exception e) {
   		return false;
   	}
   }
}