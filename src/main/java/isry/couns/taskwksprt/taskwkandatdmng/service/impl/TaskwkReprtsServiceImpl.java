/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.TaskwkReprtsMapper;
import isry.couns.taskwksprt.taskwkandatdmng.service.TaskwkReprtsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;
import isry.itgcms.util.StringUtil;

@Service("taskwkReprtsService")
public class TaskwkReprtsServiceImpl extends IsryBaseServiceImpl implements TaskwkReprtsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskwkReprtsServiceImpl.class);
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

	@Resource(name = "taskwkReprtsMapper")
	private TaskwkReprtsMapper mapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 
	 * @Method명   : selectTaskwkReprtsList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 업무보고서 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectTaskwkReprtsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 세션 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 세션정보의 유저ID
    	String userId = loginVO.getId();								// 로그인아이디
    	String userGroupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();		// 그룹권한구분코드
    	String untTaskwkSeCd = loginVO.getUntTaskwkSeCd();				// 단위업무코드
		
		LOGGER.debug("userId = [ " + userId + " ]");
		LOGGER.debug("userGroupAuthrtSeCd = [ " + userGroupAuthrtSeCd + " ]");
		LOGGER.debug("untTaskwkSeCd = [ " + untTaskwkSeCd + " ]");
		
		mapParam.put("USER_ID", userId);
		mapParam.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
		
		if ("340".equals(userGroupAuthrtSeCd)) {
			mapParam.put("IS_ADMIN", "N");
		} else {
			mapParam.put("IS_ADMIN", "Y");
		}
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        searchParam.getSingleValueMap().forEach((key, value) -> {
        	if (key.equals("DEPT_CD") 
					|| key.equals("START_DATE")
					|| key.equals("END_DATE")
			) {
        		if (!StringUtil.isEmpty(value)) {
        			mapParam.put(key, value);
        		}
			}
        });
        
        // 검색 조건 [이름 or 아이디]
        String gubun = searchParam.getValue("GB"); //
        String[] arrGuid = gubun.split(","); //
        String gbIdnt = ""; //
        String gbName = ""; //
        
        if (Arrays.asList(arrGuid).contains("GB_ID"))	gbIdnt = "T"; //
        if (Arrays.asList(arrGuid).contains("GB_NM"))	gbName = "T"; //
        mapParam.put("GB_ID"		, gbIdnt);
        mapParam.put("GB_NM"		, gbName);
        
        mapParam.put("SEARCH_TXT"	, searchParam.getValue("SEARCH_TXT"));
        //mapParam.put("SEARCH_TXT_NM", scpDb.scpEncB64(searchParam.getValue("SEARCH_TXT")));
        mapParam.put("SEARCH_TXT_NM", searchParam.getValue("SEARCH_TXT"));
        
        // 검색할 이름 암호화 처리
        CounsUtils.encodeColumns(mapParam, "SEARCH_TXT_NM");
        
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
 		
 		LOGGER.debug("mapParam : {}", mapParam); 
		
		List<Map<String, Object>> results = mapper.selectTaskwkReprtsList(mapParam);
		
		if (ObjectUtils.isEmpty(results))
    		totalCount = 0;
    	else {
    		if (results.get(0).containsKey("TOTAL_CNT")) {
    			totalCount = NumberUtils.toInt(results.get(0).get("TOTAL_CNT").toString());	
    		}
    	}
		
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		// 페이징 결과 Response 저장
		dataRequest.setResponse("dmPage", resPage);
		
		// 암호화된 칼럼 복호화 처리
		CounsUtils.decodeColumns(results, "FLNM");
		
		// 상담원명 마스킹 처리
		for (Map<String, Object> map : results) {
			String flnm = StringUtil.nullConvert(map.get("FLNM"));
			map.replace("FLNM", Masking.nameMasking(flnm));
		}
		
		return results;
	}

	/**
	 * 
	 * @Method명   : selectTaskwkReprtsDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 4.
	 * @Method설명 : 업무보고서 상세 조회
	 */
	@Override
	public List<Map<String, Object>> selectTaskwkReprtsDetail(DataRequest dataRequest) throws Exception {
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
	    
		// 검색 조건 Parameter
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("selectTaskwkReprtsDetail :: {}", searchParam);
        
        searchParam.getSingleValueMap().forEach(mapParam::put);
		
		List<Map<String, Object>> results = mapper.selectTaskwkReprtsDetail(mapParam);
		
		// 암호화된 칼럼 복호화 처리
		CounsUtils.decodeColumns(results, "CNSLTNT_FLNM");
		
		for (Map<String, Object> map : results) {
			mapParam.put("CNSLTNT_ID", map.get("CNSLTNT_ID"));
			
			// 근무일자
			mapParam.put("WORK_YMD", map.get("WORK_YMD"));
			
			// 근무시작일시 변환 및 설정	
			Date workBgngDt = (Timestamp) map.get("WORK_BGNG_DT");
			
			Instant instant = workBgngDt.toInstant();
			LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			mapParam.put("WORK_BGNG_DT", dateTime.format(formatter));
			
			// 근무종료일시 변환 및 설정
			Date workEndDt = (Timestamp) map.get("WORK_END_DT");
			instant = workEndDt.toInstant();
			dateTime = instant.atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			mapParam.put("WORK_END_DT", dateTime.format(formatter));
			
			// 상담사지각시간 조회
			Integer latenTime = mapper.selectLatenTimeByReprtsDetail(mapParam);
			map.put("LATEN_TIME", latenTime);
			
			// 상담사시간외근무시간 조회
			Integer ovrTimeWorkTime = mapper.selectOvrTimeByReprtsDetail(mapParam);
			map.put("OVTIME_WORK_TIME", ovrTimeWorkTime);
			
			// 상담사휴게시간 조회
			List<Map<String, Object>> breakTimeList = mapper.selectBreakTimeByReprtsDetail(mapParam);
			dataRequest.setResponse("dsBreakTime", breakTimeList);
			
			// 채팅상담 평가내역 내용 조회
			String chroNo = StringUtil.nullConvert(map.get("CHRO_NO"));
			String chttEvlYn = StringUtil.nullConvert(map.get("CHTT_EVL_YN"));
			if ("Y".equals(chttEvlYn) && StringUtils.hasText(chroNo)) {
				List<String> seqList = map.keySet().stream()
					.filter(e -> e.startsWith("RVSN_CHTT_DSCSN_EVL_SCORE"))
					.map(key -> StringUtil.nullConvert(map.get(key)))
					.collect(Collectors.toList());
				
				if (!ObjectUtils.isEmpty(seqList)) {
					mapParam.clear();
					mapParam.put("seqList", seqList);
					
					List<Map<String, Object>> chkListQuestList = mapper.selectChkListQuestList(mapParam);
					dataRequest.setResponse("dsChatEvlChkListQuest", chkListQuestList);
				}
			}
			
			// 게시판상담 평가내역 내용 조회
			String bbsCttEsntalNo = StringUtil.nullConvert(map.get("BBSCTT_ESNTAL_NO"));
			String ntabrdEvlYn = StringUtil.nullConvert(map.get("NTABRD_EVL_YN"));
			if ("Y".equals(ntabrdEvlYn) && StringUtils.hasText(bbsCttEsntalNo)) {
				List<String> seqList = map.keySet().stream()
					.filter(e -> e.startsWith("RVSN_NTABRD_DSCSN_EVL_SCORE"))
					.map(key -> StringUtil.nullConvert(map.get(key)))
					.collect(Collectors.toList());
				
				if (!ObjectUtils.isEmpty(seqList)) {
					mapParam.clear();
					mapParam.put("seqList", seqList);
					
					List<Map<String, Object>> chkListQuestList = mapper.selectChkListQuestList(mapParam);
					dataRequest.setResponse("dsNtabrdEvlChkListQuest", chkListQuestList);
				}
			}
		}
		
		return results;
	}
	
	/**
	 * 
	 * @Method명   : selectTaskwkReprtsDetailByMblaDscsn
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 : 업무보고서 상세 조회 (모바일상담 - 수정시 조회)
	 */
	@Override
	public List<Map<String, Object>> selectTaskwkReprtsDetailByMblaDscsn(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
	    
		// 검색 조건 Parameter
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("selectTaskwkReprtsDetailByMblaDscsn :: {}", searchParam);
        searchParam.getSingleValueMap().forEach(mapParam::put);
        
        List<Map<String, Object>> results = mapper.selectTaskwkReprtsDetailByMblaDscsn(mapParam);
        
        // 암호화된 칼럼 복호화 처리
        CounsUtils.decodeColumns(results, "CNSLTNT_FLNM");
     	
		return results;
	}
	
	/**
	 * 
	 * @Method명   : selectOvtimeAplyHistbDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 시간외 근무 신청 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectOvtimeAplyHistbDetail(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		LOGGER.debug("selectOvtimeAplyHistbDetail :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		// 시간범위 기본 설정
		String workYmd = StringUtil.nullConvert(mapParam.get("WORK_YMD"));
		Map<String, String> defaultMap = this.setDefaultDateBetweenByDetailPop(workYmd);
		defaultMap.forEach(mapParam::put);
		
		// 상세팝업 기본정보 조회
		Map<String, Object> popupBaseInfo = mapper.selectReprtsDetailPopupBaseInfo(mapParam);
		dataRequest.setResponse("dmBaseInfo", popupBaseInfo);
		
		List<Map<String, Object>> results = null;
		if (popupBaseInfo != null) {
			// 상담사성명 복호화 처리
			CounsUtils.decodeColumns(popupBaseInfo, "CNSLTNT_FLNM");
			
			mapParam.put("ORG_WORK_END_DT", popupBaseInfo.get("ORG_WORK_END_DT"));
			if (popupBaseInfo.containsKey("WORK_END_DT")) {
				mapParam.put("WORK_END_DT", popupBaseInfo.get("WORK_END_DT"));
			}
			results = mapper.selectOvtimeAplyHistbDetail(mapParam);
		}
		
		return results;
	}
	
	/**
	 * 
	 * @Method명   : selectChttDscsnListByDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 채팅상담목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectChttDscsnListByDetail(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		LOGGER.debug("selectChttDscsnListByDetail :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		// 시간범위 기본 설정
		String workYmd = StringUtil.nullConvert(mapParam.get("WORK_YMD"));
		Map<String, String> defaultMap = this.setDefaultDateBetweenByDetailPop(workYmd);
		defaultMap.forEach(mapParam::put);
		
		// 상세팝업 기본정보 조회
		Map<String, Object> popupBaseInfo = mapper.selectReprtsDetailPopupBaseInfo(mapParam);
		if (popupBaseInfo != null) {
			// 상담사성명 복호화 처리
			CounsUtils.decodeColumns(popupBaseInfo, "CNSLTNT_FLNM");
						
			mapParam.put("WORK_BGNG_DT", popupBaseInfo.get("WORK_BGNG_DT"));
			mapParam.put("WORK_END_DT", popupBaseInfo.get("WORK_END_DT"));	
		}
		
		List<Map<String, Object>> results = mapper.selectChttDscsnListByDetail(mapParam);
		
		return results;
	}
	
	/**
	 * 
	 * @Method명   : selectNtabrdDscsnListByDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 게시판상담목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectNtabrdDscsnListByDetail(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		LOGGER.debug("selectNtabrdDscsnListByDetail :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		// 시간범위 기본 설정
		String workYmd = StringUtil.nullConvert(mapParam.get("WORK_YMD"));
		Map<String, String> defaultMap = this.setDefaultDateBetweenByDetailPop(workYmd);
		defaultMap.forEach(mapParam::put);
		
		// 상세팝업 기본정보 조회
		Map<String, Object> popupBaseInfo = mapper.selectReprtsDetailPopupBaseInfo(mapParam);
		if (popupBaseInfo != null) {
			// 상담사성명 복호화 처리
			CounsUtils.decodeColumns(popupBaseInfo, "CNSLTNT_FLNM");
			
			mapParam.put("WORK_BGNG_DT", popupBaseInfo.get("WORK_BGNG_DT"));
			mapParam.put("WORK_END_DT", popupBaseInfo.get("WORK_END_DT"));	
		}
		
		List<Map<String, Object>> results = null;
		
		// 게시판유형에 따른 분기처리
		String ntabrdTypeVal = StringUtil.nullConvert(mapParam.get("NTABRD_TYPE_VALUE"));
		if ("board_coun".equals(ntabrdTypeVal) || "board_solrobot_cmnt".equals(ntabrdTypeVal)) {
			// 댓글게시판 또는 솔로봇답변
			results = mapper.selectCmntNtabrdDscsnListByDetail(mapParam);
		} else {
			// 그외 답글게시판
			results = mapper.selectNtabrdDscsnListByDetail(mapParam);
		}
		
		return results;
	}
	
	/**
	 * 
	 * @Method명   : selectOutrcDscsnListByDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 30. 
	 * @Method설명 : 업무보고서 상세 > 아웃리치상담목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectOutrcDscsnListByDetail(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		LOGGER.debug("selectOutrcDscsnListByDetail :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		// 시간범위 기본 설정
		String workYmd = StringUtil.nullConvert(mapParam.get("WORK_YMD"));
		Map<String, String> defaultMap = this.setDefaultDateBetweenByDetailPop(workYmd);
		defaultMap.forEach(mapParam::put);
		
		// 상세팝업 기본정보 조회
		Map<String, Object> popupBaseInfo = mapper.selectReprtsDetailPopupBaseInfo(mapParam);
		if (popupBaseInfo != null) {
			// 상담사성명 복호화 처리
			CounsUtils.decodeColumns(popupBaseInfo, "CNSLTNT_FLNM");
			
			mapParam.put("WORK_BGNG_DT", popupBaseInfo.get("WORK_BGNG_DT"));
			mapParam.put("WORK_END_DT", popupBaseInfo.get("WORK_END_DT"));	
		}
		
		List<Map<String, Object>> results = mapper.selectOutrcDscsnListByDetail(mapParam);
		
		return results;
	}
	
	/**
	 * @Method명   : selectChttDscsnHistbInqDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 : 업무보고서 상세 > 채팅상담내역 조회
	 */
	@Override
	public List<Map<String, Object>> selectChttDscsnHistbInqDetail(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		LOGGER.debug("selectChttDscsnHistbInqDetail :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		List<Map<String, Object>> results = null;
		
		String workYmd = searchParam.getValue("WORK_YMD");
		int workYmdVal = NumberUtils.toInt(workYmd);
		LOGGER.debug("### workYmdVal={}", workYmdVal);
		
		// AS-IS (report_view.asp) 소스에는 특정날짜 (2016-06-29) 범위에 대해 분기처리하게 되어있음!
		//<% if articleCreateDateFormat>"2016-06-29" then %>
		//var win = window.open("/admin/chat2016/chat_view2.asp?roomid="+roomid, "chat", "menubar=no,toolbar=no,directories=no,location=no,scrollbars=yes,status=no,resizable=yes,width=650,height=600,left=180,top=50");
		//<% else %>
		//var win = window.open("/admin/chat1/chat_view2.asp?roomid="+roomid, "chat", "menubar=no,toolbar=no,directories=no,location=no,scrollbars=yes,status=no,resizable=yes,width=650,height=600,left=180,top=50");
		//<% end if %>
		if (workYmdVal > 20160629) {
			results = mapper.selectChttDscsnHistbInqDetail(mapParam);
		} else {
			results = mapper.selectBfeChttDscsnHistbInqDetail(mapParam);
		}
		
		// 암호화된 칼럼 복호화 처리
		String[] keys = new String[] { 
				"CNSLTNT_NM"	// 상담사명
				, "CLIENA_NM"	// 내담자명
		};
		CounsUtils.decodeColumns(results, keys);
		
		for (Map<String, Object> map : results) {
			mapParam.put("USER_ID", map.get("CLIENA_ID"));	// 내담자아이디
			List<Map<String, Object>> chttLogUserInfos = mapper.selectChttLogUserInfo(mapParam);
			dataRequest.setResponse("dsChttLogUser", chttLogUserInfos);
		}
		
		return results;
	}
	
	/**
	 * @Method명   : selectChttDscsnHistbList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 : 업무보고서 등록 > 채팅상담목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectChttDscsnHistbList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
	       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("selectChttDscsnHistbList :: {}", searchParam.toString());
        searchParam.getSingleValueMap().forEach(mapParam::put);
        
        // 단위업무구분코드 설정
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String untTaskwkSeCd  = "";
        if (StringUtil.isEmpty(loginVO.getUntTaskwkSeCd())) {
        	untTaskwkSeCd = "U11";
        } else {
        	untTaskwkSeCd = loginVO.getUntTaskwkSeCd();	
        }
        mapParam.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
        
        List<Map<String, Object>> results = mapper.selectChttDscsnHistbList(mapParam);
 		
        // 암호화된 칼럼 복호화 처리
 		CounsUtils.decodeColumns(results, "FLNM");
        
		return results;
	}
	
	/**
	 * @Method명   : selectNtabrdDscsnHistbInqDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 28. 
	 * @Method설명 : 업무보고서 상세 > 게시판상담내역 조회
	 */
	@Override
	public List<Map<String, Object>> selectNtabrdDscsnHistbInqDetail(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		LOGGER.debug("selectChttDscsnHistbInqDetail :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		List<Map<String, Object>> results = mapper.selectNtabrdDscsnHistbInqDetail(mapParam);
		
		return results;
	}
	
	/**
	 * @Method명   : selectNtabrdDscsnHistList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 : 업무보고서 등록 > 게시판상담목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectNtabrdDscsnHistList(DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        searchParam.getSingleValueMap().forEach(mapParam::put);
		
		return mapper.selectNtabrdDscsnHistList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectAYC260List(Map<String, Object> mapParam) throws Exception {
		return mapper.selectAYC260List(mapParam);
	}
	
	/**
	 * @Method명   : selectTaskwkReprtsRegData
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 : 업무보고서 등록 관련 초기 데이터 조회
	 */
	@Override
	public Map<String, Object> selectTaskwkReprtsRegData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		
		ParameterGroup reqParam = dataRequest.getParameterGroup("dmParam");
		reqParam.getSingleValueMap().forEach(mapParam::put);
		
		// 근무일자 구하기
		Map<String, Object> workDateTime = mapper.selectWorkDateTime(mapParam);
		if (workDateTime == null) {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "근무일이 없습니다.");
			return mapResult;
		}
		
		mapResult.put("WORK_YMD", workDateTime.get("WORK_YMD").toString());
		mapResult.put("BGNG_HR", workDateTime.get("BGNG_HR").toString());
		mapResult.put("END_HR", workDateTime.get("END_HR").toString());
		mapResult.put("TASKWK_SCHDL_SN", workDateTime.get("TASKWK_SCHDL_SN").toString());
		mapResult.put("CNSLTNT_ID", workDateTime.get("CNSLTNT_ID").toString());
		
		// 자기평가표작성 (설정기간내에만 평가표작성하기)
		Map<String, Object> evelOption = mapper.selectEvalutaionOpt(mapParam);
		if (evelOption != null) {
			// 상담사성명 복호화 처리
			CounsUtils.decodeColumns(evelOption, "CNSLTNT_FLNM");
			
			// 결과 설정
			mapResult.putAll(evelOption);
		}
		
		// 근무시작일시 변환 및 설정		
		Date workBgngDt = (Timestamp) workDateTime.get("WORK_BGNG_DT");
		
		Instant instant = workBgngDt.toInstant();
		LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		
		mapParam.put("WORK_BGNG_DT", dateTime.format(formatter));
		
		// 근무종료일시 변환 및 설정
		Date workEndDt = (Timestamp) workDateTime.get("WORK_END_DT");
		instant = workEndDt.toInstant();
		dateTime = instant.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		
		mapParam.put("WORK_END_DT", dateTime.format(formatter));
		
		// 근무시작일시 및 근무종료일시 설정
		mapResult.put("WORK_BGNG_DT", mapParam.get("WORK_BGNG_DT").toString());
		mapResult.put("WORK_END_DT", mapParam.get("WORK_END_DT").toString());
		
		// 시작시간, 종료시간 설정
		mapParam.put("BGNG_HR", workDateTime.get("BGNG_HR").toString());
		mapParam.put("END_HR", workDateTime.get("END_HR").toString());
		
		// 채팅내역의 메모 가져오기
		List<Map<String, Object>> memoList = mapper.selectChatMemo(mapParam);
		
		// 기타보고사항 내용 설정
		if (memoList != null) {
			String etcDtlCn = "";
			
			// 내담자명 복호화 처리
			CounsUtils.decodeColumns(memoList, "CLIENA_NM");
			
			StringBuilder sb = new StringBuilder();
			memoList.forEach(map -> {
				String chroCm = StringUtil.nullConvert(map.get("CHRO_NM"));
				String clienaNm = StringUtil.nullConvert(map.get("CLIENA_NM"));
				String chttMemoCn = StringUtil.nullConvert(map.get("CHTT_MEMO_CN"));
				
				sb.append(String.format("[%s]-[%s] : %s // ", chroCm, clienaNm, chttMemoCn));
			});
			
			etcDtlCn = sb.toString();
			mapResult.put("ETC_DTL_CN", "");
		}
		
		// 모바일 위기 및 연계 건수, 사후관리 카운팅
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String deptCd = StringUtil.nullConvert(loginVO.getDeptCd());
		if ("326".equals(deptCd)) {		// 소속기관이 모바일상담인 경우
			Map<String, Object> mobileCrisisNocs = mapper.selectMobileCrisisNocs(mapParam);
			if (mobileCrisisNocs != null) {
				mobileCrisisNocs.forEach(mapResult::put);	
			}
		}
		
		LOGGER.debug("mapResult ::: " + mapResult);
		
		return mapResult;
	}
	
	/**
	 * @Method명   : insertTaskwkReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 : 업무보고서 등록 처리
	 */
	@Override
	public Map<String, Object> insertTaskwkReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserIp	=	"";		// 세션정보의 유저IP
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getIp() != null && !"".equals(loginVO.getIp())) {
			sUserIp = loginVO.getIp();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 등록 Parameter map
		Map<String, Object> insertParam = new HashMap<String, Object>();
		
		// 업무보고서 등록데이터 (공통)
		ParameterGroup dsCmmnReport = dataRequest.getParameterGroup("dsCmmnReport");
		LOGGER.debug("insertTaskwkReprts :: {}", dsCmmnReport);
		dsCmmnReport.getSingleValueMap().forEach(insertParam::put);
		
		// 요청 Parameter map
		Map<String, Object> reqParam = new LinkedHashMap<String, Object>();
		dsCmmnReport.getSingleValueMap().forEach((key, value) -> {
			if (key.equals("CNSLTNT_ID") 
					|| key.equals("TASKWK_SCHDL_SN")
					|| key.equals("OGDP_DEPT_CD")
					|| key.equals("WORK_YMD")
			) {
				reqParam.put(key, value);
			}
		});
		
		LOGGER.debug("궁금하다 ::: " + reqParam);
		
		// 업무보고서 중복체크
		String taskSchdlSn = StringUtil.nullConvert(reqParam.get("TASKWK_SCHDL_SN"));
		Integer existCnt = mapper.selectExistTaskwkReprts(taskSchdlSn);
		if (existCnt > 0) {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "이미 등록되었습니다.");
			return mapResult;
		}
		
		// 근무일자 구하기
		Map<String, Object> workDateTime = mapper.selectWorkDateTimeByReg(reqParam);
		if (workDateTime != null) {
			workDateTime.forEach(reqParam::put);
		}
		
		// [BEGIN] 사이버 아웃리치인 경우 실적건수 구하기
		String deptCd = StringUtil.nullConvert(reqParam.get("OGDP_DEPT_CD"));
		if (StringUtil.isEmpty(deptCd)) {
//			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			deptCd = StringUtil.nullConvert(loginVO.getDeptCd());
		}
		
		if ("325".equals(deptCd)) {
			Map<String, Object> outrcInfo = mapper.selectOutrcInfoByReg(reqParam);
			if (outrcInfo != null) {
				LOGGER.debug("insertTaskwkReprts :: outrcInfo >>> {}", outrcInfo);
				outrcInfo.forEach(insertParam::put);
			}
		}
		// [END] 사이버 아웃리치인 경우 실적건수 구하기
		
		// 채팅방 개설시간 조회 (채팅출근일시 구하기)
		// 2022-12-02 : 모바일상담(326) 을 제외한 사이버상담(324) 및 사이버아웃리치(325) 만 적용
		if ("324".equals(deptCd) || "325".equals(deptCd)) {
			String chatAtendbDt = mapper.selectChatOpenTimeByReg(reqParam);
			if (StringUtils.hasText(chatAtendbDt)) {				
				insertParam.put("CHTT_ATENDB_DT", chatAtendbDt);
			}
		}
		
		// 채팅 상담 건수 조회
		Integer chatNocs = mapper.selectChatDscsnCntByReg(reqParam);
		if (chatNocs != null) {
			LOGGER.debug("insertTaskwkReprts :: chatNocs >>> {}", chatNocs);
			insertParam.put("CHTT_NOCS", chatNocs);
		}
		
		// 채팅 위기내역 조회
		Map<String, Object> chatDscsnCrisis = mapper.selectChatDscsnCrisisByReg(reqParam);
		if (chatDscsnCrisis != null) {
			LOGGER.debug("insertTaskwkReprts :: chatDscsnCrisis >>> {}", chatDscsnCrisis);	
			chatDscsnCrisis.forEach(insertParam::put);
		}
		
		// 상담게시판 건수 조회
		Map<String, Object> ntbrdDscsnCnts = mapper.selectNtbrdDscsnCntsByReg(reqParam);
		if (ntbrdDscsnCnts != null) {
			LOGGER.debug("insertTaskwkReprts :: ntbrdDscsnCnts >>> {}", ntbrdDscsnCnts);	
			ntbrdDscsnCnts.forEach(insertParam::put);
		}
		
		// 상담게시판 유형별 등록된 위기 내역 조회
		Map<String, Object> ntbrdDscsnCrisis = mapper.selectNtbrdDscsnCrisisByReg(reqParam);
		if (ntbrdDscsnCrisis != null) {
			LOGGER.debug("insertTaskwkReprts :: ntbrdDscsnCrisis >>> {}", ntbrdDscsnCrisis);	
			ntbrdDscsnCrisis.forEach(insertParam::put);
		}
		
		// 채팅상담 평가정보
		ParameterGroup dsChatEvlInfo = dataRequest.getParameterGroup("dsChatEvlInfo");
		LOGGER.debug("insertTaskwkReprts :: {}", dsChatEvlInfo);
		dsChatEvlInfo.getSingleValueMap().forEach(insertParam::put);
		
		// 게시판상담 평가정보
		ParameterGroup dsNtabrdEvlInfo = dataRequest.getParameterGroup("dsNtabrdEvlInfo");
		LOGGER.debug("insertTaskwkReprts :: {}", dsNtabrdEvlInfo);
		dsNtabrdEvlInfo.getSingleValueMap().forEach(insertParam::put);
		
		// 등록자 정보 설정
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		insertParam.put("RGTR_ID", loginVO.getId());
		
		// 상담원출퇴근관리(AYC495) 업무보고서 등록 근무일자에 대한 데이터 확인
		Integer commuteCnt = mapper.selectCnsltntCommuteInfoCnt(reqParam);
		
		// 업무보고서 등록 처리
		LOGGER.debug("insertTaskwkReprts :: insertParam >>> {}", insertParam);
		int result = mapper.insertTaskwkReprts(insertParam);
		int commuteResult = 0;
		if (result > 0) {
			/*
			 * 상담원출퇴근관리(AYC495) 출근시간 등록 처리
			 * AYB202(깃플챗상담원상태관리)에서 상담상태구분코드 = '10'인 시간 순으로 첫번째 데이터 조회
			 */
			String AtendbDate = mapper.selectFrstAtenDbInfo(reqParam);

			// AYC495(상담원출퇴근관리) 데이터 INSERT
			if (AtendbDate != null && !"".equals(AtendbDate)) {
				reqParam.put("ATENDB_DT", AtendbDate);
				reqParam.put("loginId", loginVO.getId());
				reqParam.put("ATENDB_IP_ADDR", sUserIp);

				commuteResult = mapper.updateCnsltntCommute(reqParam);
			} else {
				commuteResult = -1;
			}
			
			// 모바일상담 관련 추가 업무보고서 등록처리
			if ("326".equals(deptCd)) {
				String indexSn = new String(String.valueOf(insertParam.get("TASKWK_REPRTS_INDEX_SN")));
				indexSn = StringUtil.nullConvert(indexSn);
				LOGGER.debug("insertTaskwkReprts :: ### Create WorkReport IndexNo => {}", indexSn);
				
				// 기존 등록 Parameter map 초기화
				insertParam.clear();
				
				// 업무일정일련번호 Parameter 조회
				String schdlSn = StringUtil.nullConvert(dsCmmnReport.getSingleValueMap().get("TASKWK_SCHDL_SN"));
				
				// 수정 조건 설정 (업무보고서색인일련번호 및 업무일정일련번호)
				insertParam.put("TASKWK_REPRTS_INDEX_SN", indexSn);
				insertParam.put("TASKWK_SCHDL_SN", schdlSn);
				
				// 수정자 정보 설정
				insertParam.put("LAST_MDFR_ID", loginVO.getId());
				
				// 업무보고서 등록데이터 (모바일상담)
				ParameterGroup dsMobileReport = dataRequest.getParameterGroup("dsMobileReport");
				LOGGER.debug("insertTaskwkReprts :: {}", dsMobileReport);
				dsMobileReport.getSingleValueMap().forEach(insertParam::put);
				
				// 시간외근무신청내역 (모바일)
				ParameterGroup dsOvtimeMobile = dataRequest.getParameterGroup("dsOvtimeMobile");
				LOGGER.debug("insertTaskwkReprts :: {}", dsOvtimeMobile);
				dsOvtimeMobile.getSingleValueMap().forEach(insertParam::put);
				
				result = mapper.updateTaskwkReprtsByMobile(insertParam);
			}
		}
		
		// 등록 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "업무보고서 등록에 성공하였습니다.");	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "업무보고서 등록에 실패하였습니다.");
		}
		
		// 출근 처리 결과 설정
		if (commuteCnt == 0 && commuteResult == 1) {
			mapResult.put("COMMUTE_OK", "Y");
			mapResult.put("COMMUTE_MSG", "출근 처리 성공!");
		} else if (commuteCnt != 0) {
			mapResult.put("COMMUTE_OK", "M");
			mapResult.put("COMMUTE_MSG", "출근 처리 되어있다.!");
		} else {
			mapResult.put("COMMUTE_OK", "N");
			mapResult.put("COMMUTE_MSG", "데이터를 찾을수가 없어 출근 처리를 실패하였습니다.\n관리자에게 문의하세요.");
		}
		
		return mapResult;
	}
	
	/**
	 * 
	 * @Method명   : updateTaskwkReprts
	 * @param dataRequest
	 * @return	수정 처리 결과
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 1. 
	 * @Method설명 : 업무보고서 수정 처리
	 */
	@Override
	public Map<String, Object> updateTaskwkReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 결과 변수 초기화
		int result = 0;
		
		// 수정 Parameter map
		Map<String, Object> updateParam = new HashMap<String, Object>();
		
		// 수정자 정보 설정
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		updateParam.put("LAST_MDFR_ID", loginVO.getId());
		
		// 부서코드 조회
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		String deptCd = searchParam.getValue("DEPT_CD");
		
		// 모바일상담 업무보고서 수정처리
        if (!StringUtil.isEmpty(deptCd) && "326".equals(deptCd)) {
        	ParameterGroup dsMobileEditDtl = dataRequest.getParameterGroup("dsMobileEditDtl");
        	LOGGER.debug("updateTaskwkReprts :: {}", dsMobileEditDtl);
        	
        	Iterator<ParameterRow> updatedRows = dsMobileEditDtl.getUpdatedRows();
        	
        	while (updatedRows.hasNext()) {
    			// 수정 데이터 Mapping
    			updatedRows.next().toMap().forEach(updateParam::put);
    			LOGGER.debug("updateTaskwkReprts :: {}", updateParam);
    			
    			// 채팅출근일시 설정
    			String atendbYmd = StringUtil.nullConvert(updateParam.get("ATENDB_YMD"));
    			String atendbHour = StringUtil.nullConvert(updateParam.get("ATENDB_HOUR"));
    			String atendMin = StringUtil.nullConvert(updateParam.get("ATENDB_MIN"));
    			
    			if (StringUtils.hasText(atendbYmd) && StringUtils.hasText(atendbHour) && StringUtils.hasText(atendMin)) {
    				updateParam.put("CHTT_ATENDB_DT", String.format("%s %s:%s:00", atendbYmd, atendbHour, atendMin));
    			}
    			
    			// 시간이외근무일시 설정
    			String ovtimeWorkYmd = StringUtil.nullConvert(updateParam.get("OVTIME_WORK_YMD"));
    			String ovtimeWorkHour = StringUtil.nullConvert(updateParam.get("OVTIME_WORK_HOUR"));
    			String ovtimeWorkMin = StringUtil.nullConvert(updateParam.get("OVTIME_WORK_MIN"));
    			
    			String ovtimeAplyYn = StringUtil.nullConvert(updateParam.get("OVTIME_APLY_YN"));
    			if ("Y".equals(ovtimeAplyYn)) {
    				if (StringUtils.hasText(ovtimeWorkYmd) && StringUtils.hasText(ovtimeWorkHour) && StringUtils.hasText(ovtimeWorkMin)) {
        				updateParam.put("OVTIME_WORK_DT", String.format("%s %s:%s:00", ovtimeWorkYmd, ovtimeWorkHour, ovtimeWorkMin));
        			}
    			}
    			result = mapper.updateTaskwkReprtsByMobile(updateParam);
    		}
        } else {
        	// 사이버상담, 사이버아웃리치 수정처리
        	ParameterGroup dsReportDtl = dataRequest.getParameterGroup("dsReportDtl");
    		LOGGER.debug("updateTaskwkReprts :: {}", dsReportDtl);
        	
        	Iterator<ParameterRow> updatedRows = dsReportDtl.getUpdatedRows();
    		
    		while (updatedRows.hasNext()) {
    			// 수정 데이터 Mapping
    			updatedRows.next().toMap().forEach(updateParam::put);
    			
    			LOGGER.debug("updateTaskwkReprts :: {}", updateParam);
    			result = mapper.updateTaskwkReprts(updateParam);
    		}
        }
		
		// 수정 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "업무보고서 수정 성공!");	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "업무보고서 수정 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : deleteTaskwkReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 업무보고서 삭제
	 */
	@Override
	public Map<String, Object> deleteTaskwkReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 결과 변수 초기화
		int result = 0;
		
		// 사용자 세션 정보 조회
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	mapParam.put("loginId", loginVO.getId());	// 로그인아이디	
    	mapParam.put("indexSn", searchParam.getValue("TASKWK_REPRTS_INDEX_SN"));
    	
    	result = mapper.deleteTaskwkReprts(mapParam);
    	
    	// 삭제 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "업무보고서 삭제 성공!");	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "업무보고서 삭제 실패!");
		}
    	
		return mapResult;
	}
	
	/**
	 * @Method명   : updateTaskwkReprtsByDetail
	 * @param dataRequest
	 * @return	수정 처리 결과
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 23.
	 * @수정자	   : Jeong.Won.Je
	 * @수정일	   : 2023.06.27
	 * @Method설명 : 업무보고서 상세(팝업) 수정 처리
	 */
	@Override
	public Map<String, Object> updateTaskwkReprtsByDetail(DataRequest dataRequest) throws Exception {
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 결과 변수 초기화
		int result = 0;
		String reqType = "";
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		
		// 업무보고서 상세(팝업) 수정 - 기본 파라메터 조회
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> reqParam = dmParam.getSingleValueMap();
		
		// 수정유형 조회
		String updType = reqParam.get("UPD_TYPE");
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> ovTimeList = paramGroup.getAllRowList();
		
		ParameterGroup searchGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = searchGroup.getSingleValueMap();
		
		if ("OVRTIME_APLY".equalsIgnoreCase(updType.toUpperCase())) {
			// 2023.06.27 추가 Start
			for (Map<String, String> map : ovTimeList) {
				LOGGER.debug("ovTimeList ::: " + map);

				String aplyYn = map.get("APLY_YN");
				if ("Y".equals(aplyYn)) {
					map.put("OVTIME_WORK_YMD", dmSearch.get("WORK_YMD"));
				} else {
					map.put("OVTIME_WORK_YMD", null);
				}
				result = mapper.updateOvtimeAplyHistbDetail(map);
				reqType = "시간외근무신청";
			}
			// End

		} else {

		}
		
		/*
		 * // 시간외근무신청 ParameterGroup dmOvtimeAplyHistb =
		 * dataRequest.getParameterGroup("dmOvtimeAplyHistb");
		 * dmOvtimeAplyHistb.getSingleValueMap().forEach(mapParam::put);
		 * 
		 * result = mapper.updateOvtimeAplyHistbDetail(mapParam); reqType = "시간외근무신청";
		 */
		
		// 수정 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", String.format("%s 수정 성공!", reqType));	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", String.format("%s 수정 실패!", reqType));
		}
		
		return mapResult;
	}
	
	/**
	 * 
	 * @Method명   : setDefaultDateBetweenByDetailPop
	 * @param workYmd	근무일자
	 * @return
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 날짜 검색 범위 기본값 설정
	 */
	private Map<String, String> setDefaultDateBetweenByDetailPop(String workYmd) {
		LOGGER.info("setDefaultDateBetweenByDetailPop :: init !!!");
		Map<String, String> resultMap = new LinkedHashMap<>();
		
		if (!StringUtils.hasText(workYmd)) {
			LocalDate todayDate = LocalDate.now();
			workYmd = todayDate.format(DateTimeFormatter.BASIC_ISO_DATE);
		}
		LOGGER.debug("### workYmd={}", workYmd);
		
		String startDateStr = DateUtil.addYearMonthDay(workYmd, 0, 0, -120);
		LOGGER.debug("### startDateStr={}", startDateStr);
		String endDateStr = DateUtil.addYearMonthDay(workYmd, 0, 0, 10);
		LOGGER.debug("### endDateStr={}", endDateStr);
		
		LocalDate beginDate = LocalDate.parse(startDateStr, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.BASIC_ISO_DATE);
		
		LocalDateTime beginDateTime = beginDate.atStartOfDay();		// YYYY-MM-dd 00:00:00.00000
		LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);	// YYYY-MM-dd 23:59:59.99999
		
		String beginDtStr = beginDateTime.format(formatter);
		String endDtStr = endDateTime.format(formatter);
		
		LOGGER.debug("### beginDateTime: {}", beginDtStr);
		LOGGER.debug("### endDateTime: {}", endDtStr);
		
		resultMap.put("WORK_BGNG_DT", beginDtStr);
		resultMap.put("WORK_END_DT", endDtStr);
		
		return resultMap;
	}
}
