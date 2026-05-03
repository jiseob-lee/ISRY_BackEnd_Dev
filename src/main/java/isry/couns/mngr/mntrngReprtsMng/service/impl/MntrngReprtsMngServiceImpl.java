/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.mntrngReprtsMng.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
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
import isry.couns.mngr.mntrngReprtsMng.mapper.MntrngReprtsMngMapper;
import isry.couns.mngr.mntrngReprtsMng.service.MntrngReprtsMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : MntrngReprtsMngServiceImpl.java
 * @프로그램 설명 : 모니터링 보고서
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 28. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 28. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mntrngReprtsMngService")
public class MntrngReprtsMngServiceImpl extends IsryBaseServiceImpl implements MntrngReprtsMngService {	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MntrngReprtsMngServiceImpl.class);
	
	private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
	private final DateTimeFormatter tmFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	@Resource(name="mntrngReprtsMngMapper")
	private MntrngReprtsMngMapper mntrngReprtsMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	ScpDb  scpDb  = new ScpDb();
	
	/**
	 * @Method명   : selectMntrngReprtsList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 28. 
	 * @Method설명 : 모니터링 보고서 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectMntrngReprtsList(Map<String, Object> mapParam) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		
		rtn = mntrngReprtsMngMapper.selectMntrngReprtsList(mapParam);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCyberDscsnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 : 사이버상담 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectCyberDscsnList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = mntrngReprtsMngMapper.selectCyberDscsnList(paramMap);
		
		// 2022-12-08 (명재철) : 암호화된 칼럼 복호화 처리
//		CounsUtils.decodeColumns(rtn, "TASKWK_RPT_CNSLTNT_NM");	// 업무보고상담사명 Key
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectOutreachList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 : 사이버아웃리치 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectOutreachList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = mntrngReprtsMngMapper.selectOutreachList(paramMap);
		
		// 2022-12-08 (명재철) : 암호화된 칼럼 복호화 처리
//		CounsUtils.decodeColumns(rtn, "TASKWK_RPT_CNSLTNT_NM");	// 업무보고상담사명 Key
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectMobileList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 : 모바일상담 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectMobileList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = mntrngReprtsMngMapper.selectMobileList(paramMap);
		
		// 2022-12-08 (명재철) : 암호화된 칼럼 복호화 처리
//		CounsUtils.decodeColumns(rtn, "TASKWK_RPT_CNSLTNT_NM");	// 업무보고상담사명 Key
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCrisisLinkBbsctt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 04. 
	 * @Method설명 : 위기및연계 게시글
	 */	
	@Override
	public List<Map<String, Object>> selectCrisisLinkBbsctt(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = mntrngReprtsMngMapper.selectCrisisLinkBbsctt(paramMap);
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCrisisLinkTypeNocs
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 04. 
	 * @Method설명 : 위기및연계 유형별건수
	 */	
	@Override
	public List<Map<String, Object>> selectCrisisLinkTypeNocs(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = mntrngReprtsMngMapper.selectCrisisLinkTypeNocs(paramMap);
		
		return rtn;
	}

	/**
	 * @Method명   : selectWorkAltMntCrtYmdCheckList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Min.Seong
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkAltMntCrtYmdCheckList(HttpServletRequest request,
			DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmReprtsReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		//중복일자 check목록  
		List<Map<String, Object>> selectWorkAltMntCrtYmdCheckList = mntrngReprtsMngMapper.selectWorkAltMntCrtYmdCheckList(dmOutcomeDetailMap);
		return selectWorkAltMntCrtYmdCheckList;
	}
	
	
	/**
	 * @Method명   : insertMntrngReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 모니터링 보고서 등록처리
	 */
	@Override
	public Map<String, Object> insertMntrngReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 요청 Parameter map
		Map<String, Object> reqParam = new LinkedHashMap<String, Object>();
				
		// 등록 Parameter map
		Map<String, Object> insertParam = new HashMap<String, Object>();
		
		// 모니터링 보고서 등록데이터
		ParameterGroup dmReprtsReg = dataRequest.getParameterGroup("dmReprtsReg");
		LOGGER.debug("insertMntrngReprts :: {}", dmReprtsReg);
		dmReprtsReg.getSingleValueMap().forEach(insertParam::put);
		
		// 등록자 정보 설정
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		insertParam.put("RGTR_ID", loginVO.getId());
		insertParam.put("OGDP_DEPT_CD", loginVO.getDeptCd());	// 소속부서코드
		
		// 업무보고서 등록 처리
		LOGGER.debug("insertMntrngReprts :: insertParam >>> {}", insertParam);
		int result = mntrngReprtsMngMapper.insertMntrngReprts(insertParam);
		
		if (result > 0) {
			result = 0;
			
			// 생성된 모니터링번호 조회
			String mntRngNo = new String(String.valueOf(insertParam.get("MNTRNG_NO")));
			mntRngNo = StringUtil.nullConvert(mntRngNo);
			LOGGER.debug("insertMntrngReprts :: ### Create MtnrngReprts MNTRNG_NO => {}", mntRngNo);
			
			// 상담사 근무일자 및 휴가 조회
			reqParam.put("WORK_YMD", dmReprtsReg.getValue("CRT_YMD"));
			List<Map<String, Object>> insertParams = mntrngReprtsMngMapper.selectCnsltntWorkSchdlList(reqParam);
			for (Map<String, Object> map : insertParams) {
				// 모니터링번호 parameter 설정
				map.put("MNTRNG_NO", mntRngNo);
				
				// 등록자 정보 설정
				map.put("RGTR_ID", insertParam.get("RGTR_ID"));
				
				// 채팅오픈시간내용 변수 선언
				String chttOpenHrCn = StringUtil.nullConvert(map.get("CHTT_OPEN_HR_CN"));
				
				// 채팅종료시각 변수 선언
				String chttEndTm = "";
				
				// 부서코드 조회
				String deptCd = StringUtil.nullConvert(map.get("OGDP_DEPT_CD"));
				
				// 사이버아웃리치 : 시간근무유형구분코드1
				String workTypeSeCd = StringUtil.nullConvert(map.get("HR_WORK_TYPE_SE_CD1"));
				LOGGER.debug("workTypeSeCd ::: " + workTypeSeCd);
				
				// 채팅오픈시간내용이 휴가가 아닌경우만
				if (!StringUtils.hasText(chttOpenHrCn) || !"휴가".equals(chttOpenHrCn.trim())) {
					
					// (사이버아웃리치) 접속이력 존재여부 확인
//					if ("325".equals(deptCd) && !"C".equals(workTypeSeCd)) {
//						String notExists = mntrngReprtsMngMapper.selectNotExistsByOutrcCnTnLog(map);
//						map.put("CNTN_LOG_NOT_EXIST", notExists);
//					}
					
					// 채팅 오픈 시간 조회
					Map<String, Object> chatOpenTime = mntrngReprtsMngMapper.selectChatOpenTime(map);
					LOGGER.debug("insertMntrngReprts :: {}", chatOpenTime);
					
					int latenTm = 0;	// 지각시간 (분)
					if (chatOpenTime != null && chatOpenTime.containsKey("CHTT_OPEN_DT")) {
						// 출근시간 데이터 설정
						Date chttOpenDt = (Timestamp) chatOpenTime.get("CHTT_OPEN_DT");
						Instant instant = chttOpenDt.toInstant();
						LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault())
								.toLocalDateTime();
						
						if (chatOpenTime.get("LATEN_TM") instanceof Integer) {
							latenTm = (Integer) chatOpenTime.get("LATEN_TM");
						} else {
							latenTm = NumberUtils.toInt(chatOpenTime.get("LATEN_TM").toString());
						}
						
						// 접속시간이 출근시간 -120분 에서 +5분까지는 정상출근
						// +5분 → 0분으로 변경 - 2023.06.29 수정 Jeong.Won.Je
						if (latenTm >= -120 && latenTm <= 0) {
							chttOpenHrCn = dateTime.format(tmFormatter);
						} else {
							// 지각처리
							chttOpenHrCn = dateTime.format(tmFormatter) + "(지각)";
						}
						
						// 채팅 오픈시간 parameter 설정
						map.put("CHTT_OPEN_DT", dateTime.format(dtFormatter));
					} else {
						// 결근처리
						chttOpenHrCn = "결근";
					}
					
					// 채팅오픈시간내용 parameter 설정
					map.put("CHTT_OPEN_HR_CN", chttOpenHrCn);
					
					// 채팅 종료시간 조회
					Map<String, Object> chatCloseTime = mntrngReprtsMngMapper.selectChatCloseTime(map);
					LOGGER.debug("insertMntrngReprts :: {}", chatCloseTime);
					
					if (chatCloseTime != null && chatCloseTime.containsKey("CHTT_END_DT")) {
						// 종료시간 데이터 설정
						Date chttCloseDt = (Timestamp) chatCloseTime.get("CHTT_END_DT");
						Instant instant = chttCloseDt.toInstant();
						LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault())
								.toLocalDateTime();
						
						// 채팅종료시각 설정
						chttEndTm = dateTime.format(tmFormatter);
						
						// 채팅 종료시간 parameter 설정
						map.put("CHTT_END_DT", dateTime.format(dtFormatter));
					}
					
					// 채팅종료시각 parameter 설정
					map.put("CHTT_END_TM", chttEndTm);
					
					LOGGER.debug("insertMntrngReprts :: CHTT_OPEN_DT={}", map.get("CHTT_OPEN_DT"));
					LOGGER.debug("insertMntrngReprts :: CHTT_END_DT={}", map.get("CHTT_END_DT"));
					
					String chttOpenDt = StringUtil.nullConvert(map.get("CHTT_OPEN_DT"));
					String chttEndDt = StringUtil.nullConvert(map.get("CHTT_END_DT"));
					
					// 휴게시간(분) 조회 및 parameter 설정
					if (StringUtils.hasText(chttOpenDt)) {
						Map<String, Object> restTime = mntrngReprtsMngMapper.selectRestTime(map);
						if (restTime != null && restTime.containsKey("TOTAL_BREAK_TIME")) {
							map.put("CONSTT_BREAK_HR", restTime.get("TOTAL_BREAK_TIME"));
						}
					}
					
					// 채팅미개설시각 조회 및 parameter 설정
					if (StringUtils.hasText(chttOpenDt) && StringUtils.hasText(chttEndDt)) {
						Map<String, Object> chatUnestaTime = mntrngReprtsMngMapper.selectChatUnestaTime(map);
						if (chatUnestaTime != null && chatUnestaTime.containsKey("CHTT_UNESTA_TM")) {
							map.put("CHTT_UNESTA_TM", chatUnestaTime.get("CHTT_UNESTA_TM"));
						}
					}
					
					// 상담사 퇴근일자 조회 및 parameter 설정
					Map<String, Object> cnsltntLeaveTime = mntrngReprtsMngMapper.selectCnsltntLeaveTime(map);
					if (cnsltntLeaveTime != null && !ObjectUtils.isEmpty(cnsltntLeaveTime.get("LVFFC_PRCS_DT"))) {
						map.put("LVFFC_PRCS_DT", cnsltntLeaveTime.get("LVFFC_PRCS_DT"));
					}
					
					// 임시변수 삭제 (사이버아웃리치-접속이력 존재여부 확인)
					if (map.containsKey("CNTN_LOG_NOT_EXIST")) {
						map.remove("CNTN_LOG_NOT_EXIST");
					}
				}
				
				LOGGER.debug("insertMntrngReprts :: input data={}", map);
			}
			
			if (!ObjectUtils.isEmpty(insertParams)) {
				// 등록 Parameter map 초기화 및 일괄등록 목록 설정 
				insertParam.clear();
				insertParam.put("params", insertParams);
				
				// 모니터링 보고서 데이터 등록처리
				result = mntrngReprtsMngMapper.insertMntrngReprtsData(insertParam);
				
				LOGGER.debug("insertMntrngReprts :: insert data cnt={}", result);
			}
			
		}
		
		// 등록 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "모니터링 보고서 등록 성공!");	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "모니터링 보고서 등록 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : updateMntrngReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 26. 
	 * @Method설명 : 모니터링 보고서 수정
	 */
	@Override
	public Map<String, Object> updateMntrngReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";		// 세션 정보 ID
		
		// 세션 정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);			
		}
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 수정 Parameter Map
		Map<String, Object> updateParam = new HashMap<String, Object>();
				
		// 결과 변수 초기화
		int result = 0;
		
		// 검색 조건 Parameter
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("updateMntrngReprts :: {}", searchParam);
        
        Map<String, String> paramMap = new HashMap<>();
        String forceUpdYn = "N";
        if (searchParam != null) {
        	paramMap = searchParam.getSingleValueMap();
        	forceUpdYn = StringUtil.nullConvert(searchParam.getValue("FORCE_UPD_YN"));
        }
		
        // forceUpdYn 가 "Y" 인 경우, 모니터링 보고서 관리 > 업데이트 버튼 눌렀을 때 처리
		if ("Y".equals(forceUpdYn)) {
			
			// [BEGIN] 2023-04-17 (Myeong.Jae.Cheol) : 일간 모니터링 보고서 데이터 (AYC410) 업데이트 로직이 필요한지 확인필요!
			
			/*
			 * // 업무보고서색인일련번호 목록조회 List<Map<String, String>> resultMap =
			 * mntrngReprtsMngMapper.selectTASKWKREPRTSINDEXSNList(paramMap);
			 * for(Map<String, String> map : resultMap) {
			 * 
			 * // map.put("TASKWK_REPRTS_INDEX_SN",
			 * map.get("TASKWK_REPRTS_INDEX_SN").toString()); // map.put("MNTRNG_NO",
			 * map.get("MNTRNG_NO")); // map.put("TASKWK_RPT_CNSLTNT_ID",
			 * map.get("TASKWK_RPT_CNSLTNT_ID").toString()); map.put("LAST_MDFR_ID",
			 * loginId);
			 * 
			 * mntrngReprtsMngMapper.UpdateTASKWKREPRTSINDEXSN(map); // 업무보고서색인일련번호(AYC410)
			 * }
			 */
			
			// [BEGIN] 2023-05-25 (Jeong.Won.Je) : 일간 모니터링 보고서 데이터 (AYC410) 업데이트 로직 추가
			List<Map<String, Object>> updateRowList = mntrngReprtsMngMapper.selectMntrngReprtsDataList(paramMap);
			for(Map<String, Object> map : updateRowList) {
				LOGGER.debug("updateRow :: {}", map);
				
				// 수정자 정보 설정
				map.put("RGTR_ID", loginId);
				
				// 채팅오픈시간내용 변수 선언
				String chttOpenHrCn = StringUtil.nullConvert(map.get("CHTT_OPEN_HR_CN"));
				
				// 채팅종료시각 변수 선언
				String chttEndTm = "";
				
				// 채팅오픈시간내용이 휴가가 아닌경우만
				if (!StringUtils.hasText(chttOpenHrCn) || !"휴가".equals(chttOpenHrCn.trim())) {
					
					// 채팅 오픈 시간 조회
					Map<String, Object> chatOpenTime = mntrngReprtsMngMapper.selectChatOpenTime(map);
					LOGGER.debug("updateMntrngReprts :: {}", chatOpenTime);
					
					int latenTm = 0;		// 지각시간(분)
					if (chatOpenTime != null && chatOpenTime.containsKey("CHTT_OPEN_DT")) {
						// 출근시간 데이터 설정
						Date chttOpenDt = (Timestamp)chatOpenTime.get("CHTT_OPEN_DT");
						Instant instant = chttOpenDt.toInstant();
						LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
						
						if (chatOpenTime.get("LATEN_TM") instanceof Integer) {
							latenTm = (Integer) chatOpenTime.get("LATEN_TM");
						} else {
							latenTm = NumberUtils.toInt(chatOpenTime.get("LATEN_TM").toString());
						}
						
						// 접속시간이 출근시간 -120분에서 +5분까지는 정상출근
						if (latenTm >= -120 && latenTm <= 5) {
							chttOpenHrCn = dateTime.format(tmFormatter);
						} else {
							// 지각처리
							chttOpenHrCn = dateTime.format(tmFormatter) + "(지각)";
						}
						
						// 채팅 오픈 시간 parameter 설정
						map.put("CHTT_OPEN_DT", dateTime.format(dtFormatter));
					} else {
						// 결근처리
						chttOpenHrCn = "결근";
					}
					
					// 채팅오픈시간내용 parameter 설정
					map.put("CHTT_OPEN_HR_CN", chttOpenHrCn);
					
					// 채팅 종료시간 조회
					Map<String, Object> chatCloseTime = mntrngReprtsMngMapper.selectChatCloseTime(map);
					LOGGER.debug("updateMntrngReprts :: {}", chatCloseTime);
					
					if (chatCloseTime != null && chatCloseTime.containsKey("CHTT_END_DT")) {
						// 종료시간 데이터 설정
						Date chttCloseDt = (Timestamp) chatCloseTime.get("CHTT_END_DT");
						Instant instant = chttCloseDt.toInstant();
						LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
						
						// 채팅종료시각 설정
						chttEndTm = dateTime.format(tmFormatter);
						
						// 채팅 종료시간 parameter 설정
						map.put("CHTT_END_DT", dateTime.format(dtFormatter));
					}
					
					// 채팅 종료시각 parameter 설정
					map.put("CHTT_END_TM", chttEndTm);
					
					LOGGER.debug("updateMntrngReprts :: CHTT_OPEN_DT={}", map.get("CHTT_OPEN_DT"));
					LOGGER.debug("updateMntrngReprts :: CHTT_END_DT={}", map.get("CHTT_END_DT"));
					
					String chttOpenDt = StringUtil.nullConvert(map.get("CHTT_OPEN_DT"));
					String chttEndDt = StringUtil.nullConvert(map.get("CHTT_END_DT"));
					
					// 휴게시간(분) 조회 및 parameter 설정
					if (StringUtils.hasText(chttOpenDt)) {
						Map<String, Object> restTime = mntrngReprtsMngMapper.selectRestTime(map);
						if (restTime != null && restTime.containsKey("TOTAL_BREAK_TIME")) {
							map.put("CONSTT_BREAK_HR", restTime.get("TOTAL_BREAK_TIME"));
						}
					}
					
					// 채팅 미개설시각 조회 및 parameter 설정
					if (StringUtils.hasText(chttOpenDt) && StringUtils.hasText(chttEndDt)) {
						Map<String, Object> chatUnestaTime = mntrngReprtsMngMapper.selectChatUnestaTime(map);
						if (chatUnestaTime != null && chatUnestaTime.containsKey("CHTT_UNESTA_TM")) {
							map.put("CHTT_UNESTA_TM", chatUnestaTime.get("CHTT_UNESTA_TM"));
						}
					}
					
					// 상담사 퇴근일자 조회 및 parameter 설정
					Map<String, Object> cnsltntLeaveTime = mntrngReprtsMngMapper.selectCnsltntLeaveTime(map);
					if (cnsltntLeaveTime != null && !ObjectUtils.isEmpty(cnsltntLeaveTime.get("LVFFC_PRCS_DT"))) {
						map.put("LVFFC_PRCS_DT", cnsltntLeaveTime.get("LVFFC_PRCS_DT"));
					}
				}
				
				LOGGER.debug("updateMntrngReprts :: input data={}", map);
				
				result = mntrngReprtsMngMapper.updateMntrngReprtsData(map);
			}
			
			// [END] 2023-05-25 (Jeong.Won.Je) : 일간 모니터링 보고서 데이터 (AYC410) 업데이트 로직 추가
			
			// [END] 2023-04-17 (Myeong.Jae.Cheol) : 일간 모니터링 보고서 데이터 (AYC410) 업데이트 로직이 필요한지 확인필요!
			
			// 일간 모니터링 보고서 (AYC400) 업데이트 로직
			Map<String, Object> updateRet = updateMntrngReprtsByReload(request, dataRequest);
			if (updateRet != null && !updateRet.isEmpty()) {
				result = 1;
			}
			
		} else {
			// 수정 Parameter map
//			Map<String, Object> updateParam = new HashMap<String, Object>();
			
			// FORCE_UPD_YN 설정
			updateParam.put("FORCE_UPD_YN", forceUpdYn);
			
			// 수정자 정보 설정
			updateParam.put("LAST_MDFR_ID", loginId);
			
			ParameterGroup dsMntrgReprtsDtl = dataRequest.getParameterGroup("dsMntrgReprtsDtl");
			LOGGER.debug("updateMntrngReprts :: {}", dsMntrgReprtsDtl);
        	
        	Iterator<ParameterRow> updatedRows = dsMntrgReprtsDtl.getUpdatedRows();
        	
        	while (updatedRows.hasNext()) {
    			// 수정 데이터 Mapping
    			updatedRows.next().toMap().forEach(updateParam::put);
    			LOGGER.debug("updateMntrngReprts :: {}", updateParam);
    			
    			result = mntrngReprtsMngMapper.updateMntrngReprts(updateParam);
        	}
		}
		
		// 수정 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "모니터링 보고서 수정 성공!");	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "모니터링 보고서 수정 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : deleteMntrngReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 : 모니터링 보고서 삭제처리
	 */
	@Override
	public Map<String, Object> deleteMntrngReprts(DataRequest dataRequest) throws Exception {
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 결과 변수 초기화
		int result = 0;
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 삭제 조건 Parameter
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("삭제할 Parameter 가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("deleteMntrngReprts :: {}", searchParam);
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		// 모니터링 보고서 데이터 (Detail Table) 삭제
		result = mntrngReprtsMngMapper.deleteMntrngReprtsData(mapParam);
		LOGGER.debug("deleteMntrngReprts :: 모니터링 보고서 데이터 삭제 : [cnt={}]", result);
		
		// 모니터링 보고서 (Main Table) 삭제
		result = mntrngReprtsMngMapper.deleteMntrngReprts(mapParam);
		LOGGER.debug("deleteMntrngReprts :: 모니터링 보고서 삭제? {}", result > 0 ? "SUCCESS" : "FAILURE");
		
		// 삭제 결과 설정
		if (result > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "모니터링 보고서 삭제 성공!");	
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "모니터링 보고서 삭제 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : selectMntrngReprtsDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 : 모니터링 보고서 상세 조회
	 */
	@Override
	public List<Map<String, Object>> selectMntrngReprtsDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
	    
		// 검색 조건 Parameter
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("selectMntrngReprtsDetail :: {}", searchParam);
        searchParam.getSingleValueMap().forEach(mapParam::put);
        
        List<Map<String, Object>> results = mntrngReprtsMngMapper.selectMntrngReprtsDetail(mapParam);
        
        // 암호화된 칼럼 복호화 처리
//        CounsUtils.decodeColumns(results, "WRTR_FLNM");	// 작성자성명 Key
     	
     	// 수정일자 (MDFCN_YMD) 조회후 데이터가 없으면 모니터링 보고서 정보 갱신
     	for (Map<String, Object> map : results) {
     		String mdfcnYmd = StringUtil.nullConvert(map.get("MDFCN_YMD"));
     		if (StringUtil.isEmpty(mdfcnYmd)) {
     			Map<String, Object> updateRet = updateMntrngReprtsByReload(request, dataRequest);
     			map.putAll(updateRet);
     		}
     	}
        
		return results;
	}
	
	/**
	 * @Method명   : updateMntrngReprtsByReload
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 26. 
	 * @Method설명 : 모니터링 보고서 갱신 처리
	 */
	public Map<String, Object> updateMntrngReprtsByReload(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 수정 Parameter map
		Map<String, Object> updateParam = new HashMap<String, Object>();
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 검색 조건 Parameter
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        LOGGER.debug("updateMntrngReprtsByReload :: {}", searchParam);
        searchParam.getSingleValueMap().forEach(mapParam::put);
        
        // 수정 Parameter 기본 정보 설정
        updateParam.put("MNTRNG_NO", searchParam.getValue("MNTRNG_NO"));
        
        // FORCE_UPD_YN 설정
        updateParam.put("FORCE_UPD_YN", searchParam.getValue("FORCE_UPD_YN"));
        
        // 수정자 정보 설정
     	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
     	updateParam.put("LAST_MDFR_ID", loginVO.getId());
		
		// 1. 근태관리 상태 목록 조회
		List<Map<String, Object>> dclzMngSttsList = mntrngReprtsMngMapper.selectDclzMngSttsList(mapParam);
		if (dclzMngSttsList == null) dclzMngSttsList = new ArrayList<>();
		
		// 암호화된 칼럼 복호화 처리
//     	CounsUtils.decodeColumns(dclzMngSttsList, "CNSLTNT_FLNM");	// 상담사성명 Key
     	
     	// 지각관련 변수
     	int latenNocs = 0;
		StringBuilder sbLatenCn = new StringBuilder();
		
		// 결근관련 변수
		int absencNocs = 0;
		StringBuilder sbAbsencCn = new StringBuilder();
		
		// 미로그아웃 관련 변수
		int unlgtNocs = 0;
		StringBuilder sbUnlgtCn = new StringBuilder();
     	
     	ListIterator<Map<String, Object>> itDclzMngStts = dclzMngSttsList.listIterator();
     	while (itDclzMngStts.hasNext()) {
     		//int idx = itDclzMngStts.nextIndex();
     		Map<String, Object> element = itDclzMngStts.next();
     		
     		String cnsltntFlnm = StringUtil.nullConvert(element.get("CNSLTNT_FLNM"));
     		
     		// 지각건수 조회 및 내용 설정
			String latenYn = StringUtil.nullConvert(element.get("LATEN_YN"));
			if ("Y".equals(latenYn)) {
				sbLatenCn.append(cnsltntFlnm);
				sbLatenCn.append(",");
				
				latenNocs++;
			}
			
			// 결근건수 조회 및 내용 설정
			String absencYn = StringUtil.nullConvert(element.get("ABSENC_YN"));
			if ("Y".equals(absencYn)) {
				sbAbsencCn.append(cnsltntFlnm);
				sbAbsencCn.append(",");
				
				absencNocs++;
			}
			
			// 미로그아웃건수 조회 및 내용 설정
			String unlgtYn = StringUtil.nullConvert(element.get("UNLGT_YN"));
			if ("Y".equals(unlgtYn)) {
				sbUnlgtCn.append(cnsltntFlnm);
				sbUnlgtCn.append(",");
				
				unlgtNocs++;
			}
     	}
     	
     	String latenCn = sbLatenCn.toString();
     	if (latenCn.lastIndexOf(",") != -1) {
     		latenCn = latenCn.substring(0, latenCn.length() - 1);
     	}
     	
     	String absencCn = sbAbsencCn.toString();
     	if (absencCn.lastIndexOf(",") != -1) {
     		absencCn = absencCn.substring(0, absencCn.length() - 1);
     	}
     	
     	String unlgtCn = sbUnlgtCn.toString();
     	if (unlgtCn.lastIndexOf(",") != -1) {
     		unlgtCn = unlgtCn.substring(0, unlgtCn.length() - 1);
     	}
     	
     	LOGGER.debug("updateMntrngReprtsByReload :: 지각건수 => {}", latenNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 지각내용 => {}", latenCn);
     	
     	LOGGER.debug("updateMntrngReprtsByReload :: 결근건수 => {}", absencNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 결근내용 => {}", absencCn);
     	
     	LOGGER.debug("updateMntrngReprtsByReload :: 미로그아웃건수 => {}", unlgtNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 미로그아웃내용 => {}", unlgtCn);
     	
     	updateParam.put("LATEN_NOCS", latenNocs);
     	updateParam.put("LATEN_CN", latenCn);
     	
     	updateParam.put("ABSENC_NOCS", absencNocs);
     	updateParam.put("ABSENC_CN", absencCn);
     	
     	updateParam.put("UNLGT_NOCS", unlgtNocs);
     	updateParam.put("UNLGT_CN", unlgtCn);
     	
		// 2. 위기 및 연계 건수 조회
     	Integer crisisLinkNocs = mntrngReprtsMngMapper.selectCrisisLinkNocs(mapParam);
     	updateParam.put("CRISIS_LINK_NOCS", crisisLinkNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 위기 및 연계 건수 => {}", crisisLinkNocs);
		
		// 3. 제 3자 제공내역 조회
     	Integer thptyPvsnHistbNocs = 0;
     	Map<String, BigDecimal> tmpThptyPvsnHistbNocs = mntrngReprtsMngMapper.selectThptyPvsnHistbNocs(mapParam);
     	if (tmpThptyPvsnHistbNocs != null) {
     		thptyPvsnHistbNocs = tmpThptyPvsnHistbNocs.values().stream()
     					.mapToInt(BigDecimal::intValue)
     					.sum();
     	}
     	
     	updateParam.put("THPTY_PVSN_HISTB_NOCS", thptyPvsnHistbNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 제 3자 제공내역 건수 => {}", thptyPvsnHistbNocs);
     	
     	// 4. 업무보고서 모니터링 조회
     	List<Map<String, Object>> taskwkReprtsWrtrList = mntrngReprtsMngMapper.selectTaskwkReprtsWrtrList(mapParam);
     	if (taskwkReprtsWrtrList == null) taskwkReprtsWrtrList = new ArrayList<>();
     	
     	// 암호화된 칼럼 복호화 처리
//     	CounsUtils.decodeColumns(taskwkReprtsWrtrList, "CNSLTNT_FLNM");	// 상담사성명 Key
     	
     	// 작성자 목록
     	List<Map<String, Object>> writList = taskwkReprtsWrtrList.stream().filter(m -> {
     		String wrtYn = StringUtil.nullConvert(m.get("TASKWK_REPRTS_WRT_YN"));	// 업무보고서 작성여부
     		return "Y".equals(wrtYn);
     	}).collect(Collectors.toList());
     	
     	// 업무보고서작성건수 설정
     	Integer taskwkReprtsWrtNocs = writList.size();
     	updateParam.put("TASKWK_REPRTS_WRT_NOCS", taskwkReprtsWrtNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 업무보고서작성건수 => {}", taskwkReprtsWrtNocs);
     	
     	// 미작성자 목록
     	List<Map<String, Object>> unwritList = taskwkReprtsWrtrList.stream().filter(m -> {
     		String wrtYn = StringUtil.nullConvert(m.get("TASKWK_REPRTS_WRT_YN"));	// 업무보고서 작성여부
     		return "N".equals(wrtYn);
     	}).collect(Collectors.toList());
     	
     	// 업무보고서미작성건수 설정
     	Integer taskwkReprtsUnWrtNocs = unwritList.size();
     	updateParam.put("TASKWK_REPRTS_UNWRIT_NOCS", taskwkReprtsUnWrtNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 업무보고서미작성건수 => {}", taskwkReprtsUnWrtNocs);
     	
     	if (taskwkReprtsUnWrtNocs > 0) {
     		// 업무보고서내용 설정
	     	StringBuilder sbTaskwkReprtsCn = new StringBuilder();
	     	ListIterator<Map<String, Object>> itUnwritList = unwritList.listIterator();
	     	int unwritLastIdx = taskwkReprtsUnWrtNocs - 1;  	     	
	     	
	     	while (itUnwritList.hasNext()) {
	     		int idx = itUnwritList.nextIndex();
	     		Map<String, Object> element = itUnwritList.next();
	     		
	     		String cnsltntFlnm = StringUtil.nullConvert(element.get("CNSLTNT_FLNM"));
	     		sbTaskwkReprtsCn.append(cnsltntFlnm);
	     		
	     		if (idx < unwritLastIdx) {
	     			sbTaskwkReprtsCn.append(",");
	     		}
	     	}
	     	
	     	String taskwkReprtsCn = sbTaskwkReprtsCn.toString();
	     	LOGGER.debug("updateMntrngReprtsByReload :: 업무보고서내용 => {}", taskwkReprtsCn);
	     	updateParam.put("TASKWK_REPRTS_CN", taskwkReprtsCn);	
     	}
     	
     	// 5. 글배정 조회
     	
     	// 오픈채팅방 답글 및 고민글 등록 건수 설정
     	Map<String, Object> openChroNocs = mntrngReprtsMngMapper.selectOpenChroNocs(mapParam);
     	if (openChroNocs != null) {
     		updateParam.put("OPEN_CHRO_RETE_NOCS", openChroNocs.get("OPEN_CHRO_RETE_NOCS"));
     		updateParam.put("OPEN_CHRO_STOFPR_NOCS", openChroNocs.get("OPEN_CHRO_STOFPR_NOCS"));
     	}
     	
     	// 6. 사이버상담후기 건수 조회
     	Integer cyberDscsnEpilgNocs = mntrngReprtsMngMapper.selectCyberDscsnEpilgNocs(mapParam);
     	updateParam.put("CYBER_DSCSN_EPILG_NOCS", cyberDscsnEpilgNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 사이버상담후기건수 => {}", cyberDscsnEpilgNocs);
     	
     	// 7. 고객의 소리 건수 조회
     	Integer vocNocs = mntrngReprtsMngMapper.selectVocNocs(mapParam);
     	updateParam.put("VOC_NOCS", vocNocs);
     	LOGGER.debug("updateMntrngReprtsByReload :: 고객의 소리 건수 => {}", vocNocs);
     	
     	// 업데이트 처리
     	mntrngReprtsMngMapper.updateMntrngReprts(updateParam);
     	
     	return updateParam;
	}	
}
