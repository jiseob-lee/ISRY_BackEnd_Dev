/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.constants.PageConst;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.rte.fdl.string.EgovStringUtil;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.syscmmn.sms.service.SmsMessageVO;
import isry.itgcms.syscmmn.sms.service.SmsService;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtAuthGrpMapper;
import isry.itgcms.sysmgmt.userauth.mapper.UserAuthAplyMapper;
import isry.itgcms.sysmgmt.userauth.mapper.UserInstAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtAuthGrpService;
import isry.itgcms.sysmgmt.userauth.service.MgmtUserAuthAprvService;
import isry.itgcms.sysmgmt.userauth.service.MgmtUserAuthService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ConvertUtils;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : MgmtUserAuthAprvServiceImpl.java
 * @프로그램 설명 : 사용자 권한 승인 관리
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 21. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 21.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mgmtUserAuthAprvService")
public class MgmtUserAuthAprvServiceImpl extends IsryBaseServiceImpl implements MgmtUserAuthAprvService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MgmtUserAuthAprvServiceImpl.class);
	
	@Resource(name = "userAuthAplyMapper")
	private UserAuthAplyMapper mapper;
	
	@Resource(name = "userInstAuthMapper")
	private UserInstAuthMapper userInstAuthMapper;
	
	@Resource(name="mgmtAuthGrpMapper")
    private MgmtAuthGrpMapper mgmtAuthGrpMapper;
	
	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "mgmtAuthGrpService")
	private MgmtAuthGrpService mgmtAuthGrpService;
	
	@Resource(name = "mgmtUserAuthService")
	private MgmtUserAuthService mgmtUserAuthService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	@Resource(name = "smsService")
	private SmsService smsService;

	@Resource(name="mgmtMenuService")
	private MgmtMenuService mgmtMenuService;
	
	/** DB 암복호화 모듈 */
	//private final ScpDb scpDb = new ScpDb();
	
	/**
	 * @Method명   : selectAprvAdminInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 8. 
	 * @Method설명 : 승인관리자 기관 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectAprvAdminInstList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 승인관리자 기관 목록 조회 처리
		Map<String, Object> mapParam = new LinkedHashMap<>();
		mapParam.put("userId", userDetailsVO.getId());
		List<Map<String, Object>> results = mapper.selectAprvAdminInstList(mapParam);
		
		return results;
	}
	
	/**
	 * @Method명   : selectUserAuthAprvList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 사용자별 권한 승인 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectUserAuthAprvList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		// 검색조건 Parameter 설정
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> mapParam = new LinkedHashMap<>();
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		if (mapParam.containsKey("DS_NAME")) {
			mapParam.remove("DS_NAME");
		}
		
		// 요청 화면에 따른 분기처리
		String reqAppId = request.getParameter("_AUTH_APP_ID");
		String reqMenuNo = request.getParameter("_AUTH_MENU_NO");
		
		if (reqAppId.lastIndexOf("RightsApprovalManage.clx") > -1 || "5056".equals(reqMenuNo) ) {	// 신청승인관리
			// 2023-03-28 : 로그인한 승인관리자 계정 제외
			UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
			if (userDetailsVO == null) {
				throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
			}
			mapParam.put("APRV_ADMIN_ID", userDetailsVO.getId());
		}
		
		// 승인 기관번호 파라메터 설정
		Map<String, Object> aprvInstNoInfo = userInstAuthService.getAprvInstNoInfo(request, dataRequest, null);
		mapParam.putAll(aprvInstNoInfo);
		
		// 검색조건 설정
		String srchKey = searchParam.getValue("SRCH_KEY");
		String srchTxt = searchParam.getValue("SRCH_TXT");
		
		// 검색조건 성명일때, 암호화 처리
		//if ("NAME".equals(srchKey)) {
			//mapParam.put("SRCH_TXT", scpDb.scpEncB64(srchTxt));
		//}
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
        ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		 
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = NumberUtils.toInt(reqPage.getValue(PageConst.KEY_PAGE_NO));
		int rowSize = NumberUtils.toInt(reqPage.getValue(PageConst.KEY_RECORD_CNT_PER_PAGE));
		int startIndex = (pageIdx - 1) * rowSize;
		
		mapParam.put(PageConst.KEY_FIRST_RECODE_IDX, startIndex);
		mapParam.put(PageConst.KEY_PAGE_ROW_COUNT, rowSize);
		
		LOGGER.debug("selectUserAuthAprvList: mapParam={}", mapParam);
		
		// 총 건수 조회
		Long totalCount = mapper.selectUserAuthAplyCount(mapParam);
		LOGGER.debug("selectUserAuthAprvList: totalCount={}", totalCount);
		
		resPage.put(PageConst.KEY_RECORD_TOTAL_CNT, totalCount);
		resPage.put(PageConst.KEY_PAGE_NO, pageIdx);
		resPage.put(PageConst.KEY_RECORD_CNT_PER_PAGE, rowSize);
		
		// 페이징 결과 Response 저장
		dataRequest.setResponse("dmPageInfo", resPage);
		
		// 권한 승인 목록 조회
		List<Map<String, Object>> results = mapper.selectUserAuthAplyList(mapParam);
		
		// DB 칼럼 암호화값 복호화 처리
		//decodeDbColumns(results);
		
		return results;
	}
	
	/**
	 * @Method명   : selectUserAuthAprvDetails
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자별 권한 승인 상세 조회
	 */
	@Override
	public List<Map<String, Object>> selectUserAuthAprvDetails(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		// 검색조건 Parameter 설정
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> mapParam = new LinkedHashMap<>();
		searchParam.getSingleValueMap().forEach((key, value) -> {
			mapParam.put(EgovStringUtil.convertToCamelCase(key), value);
		});
		
		// 사용자 정보 조회
		List<Map<String, Object>> userInfo = mapper.selectUserInfoByAuthAplyDetails(mapParam);
		
		// DB 칼럼 암호화값 복호화 처리 - 사용자 권한 승인 요청 상세 정보
		//decodeDbColumns(userInfo);
		
		// 사용자 정보 DS 설정
		dataRequest.setResponse("dsUserInfo", userInfo);
		
		// 권한 승인 요청 상세 조회
		List<Map<String, Object>> results = mapper.selectUserAuthAplyDetails(mapParam);
		
		// DB 칼럼 암호화값 복호화 처리 - 권한 승인 요청 상세
		//decodeDbColumns(results);
		
		return results;
	}
	
	
	/**
	 * @Method명   : processUserAuthAplyByReject
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 사용자별 권한 승인 반려 처리
	 */
	@Override
	public Map<String, Object> processUserAuthAplyByReject(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<>();
		
		// 권한 반려 처리 데이터 DM
		ParameterGroup dmRejectAuthAply = dataRequest.getParameterGroup("dmRejectAuthAply");
		LOGGER.debug("processUserAuthAplyByReject :: {}", dmRejectAuthAply);
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 2023.04.25 (Myeong.Jae.Cheol) : 권한 신청 상태 체크
		Map<String, Object> checkRstMap = checkAprvStatus(dmRejectAuthAply.getValue("APLY_SN"));
		if (checkRstMap.containsKey("hasAprvProc")) {
			boolean hasAprvProc = (Boolean) checkRstMap.get("hasAprvProc");
			
			// 이미 다른 승인처리자 (시스템총괄관리자 및 총괄관리자) 가 승인 또는 반려 처리한 경우
			// 권한 신청건 반려 처리 못하게 처리
			if (hasAprvProc) {
				mapResult.put("RESULT_OK", "N");
				mapResult.put("RESULT_MSG", checkRstMap.get("RESULT_MSG"));
				
				return mapResult;
			}
		}
		
		// 반려처리 Parameter 설정
		Map<String, Object> mapParam = new LinkedHashMap<>();
		
		// 승인관리자 정보 입력
		mapParam.put("prcrId", userDetailsVO.getId());			// 처리자아이디
		
		// 수정 사용자아이디 설정
		mapParam.put("lastMdfrId", userDetailsVO.getId());		// 최종수정자아이디
		
		// 반려 관련 정보 설정 (신청일련번호, 반려사유 등)
		dmRejectAuthAply.getSingleValueMap().forEach((key, value) -> {
			mapParam.put(EgovStringUtil.convertToCamelCase(key), value);
		});
		
		// 권한 요청 반려 처리
		int resultCnt = mapper.updateUserAuthAplyByReject(mapParam);
		
		// 처리 결과 설정
		if (resultCnt > 0) {
			// SMS 메시지 전송
			processSendSMS(dmRejectAuthAply.getValue("APLY_SN"), userDetailsVO.getId());
			
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "권한 승인 요청 반려 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "권한 승인 요청 반려 처리 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : processUserAuthAplyByApproval
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 사용자별 권한 승인 처리
	 */
	@Override
	public Map<String, Object> processUserAuthAplyByApproval(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<>();
		
		// 승인 요청 Parameter DM
		ParameterGroup dmAprvParam = dataRequest.getParameterGroup("dmAprvParam");
		LOGGER.debug("processUserAuthAplyByApproval :: {}", dmAprvParam);
		
		// 권한 승인 처리 데이터 DM
		ParameterGroup dmInstAuthAprv = dataRequest.getParameterGroup("dmInstAuthAprv");
		LOGGER.debug("processUserAuthAplyByApproval :: {}", dmInstAuthAprv);
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 2023.04.25 (Myeong.Jae.Cheol) : 권한 신청 상태 체크
		Map<String, Object> checkRstMap = checkAprvStatus(dmAprvParam.getValue("APLY_SN"));
		if (checkRstMap.containsKey("hasAprvProc")) {
			boolean hasAprvProc = (Boolean) checkRstMap.get("hasAprvProc");
			
			// 이미 다른 승인처리자 (시스템총괄관리자 및 총괄관리자) 가 승인 또는 반려 처리한 경우
			// 권한 신청건 승인 처리 못하게 처리
			if (hasAprvProc) {
				mapResult.put("RESULT_OK", "N");
				mapResult.put("RESULT_MSG", checkRstMap.get("RESULT_MSG"));
				
				return mapResult;
			}
		}
		
		// 승인처리 Parameter 설정
		Map<String, Object> mapParam = new LinkedHashMap<>(dmInstAuthAprv.getSingleValueMap());
		
		String addtngChgInstNo = StringUtil.nullConvert(dmAprvParam.getValue("ADDTNG_CHG_INST_NO"));
		String addtngChgOgdpDeptCd = StringUtil.nullConvert(dmAprvParam.getValue("ADDTNG_CHG_OGDP_DEPT_CD"));
		String addtngChgOgdpDeptNm = StringUtil.nullConvert(dmAprvParam.getValue("ADDTNG_CHG_OGDP_DEPT_NM"));
		String addtngChgJbpsSeCd = StringUtil.nullConvert(dmAprvParam.getValue("ADDTNG_CHG_JBPS_SE_CD"));
		String addtngChgJbpsNm = StringUtil.nullConvert(dmAprvParam.getValue("ADDTNG_CHG_JBPS_NM"));
		
		// 권한 요청 승인 처리
		
		// 신청 유형에 따른 분기처리...
		// 기관삭제시 주기관은 삭제불가
		// 기관변경시 주기관은 SCA100 (종사자) 테이블 소속기관번호 및 소속부서코드, 직위구분코드 업데이트 처리
		// 기관변경시 로직: SAB230 (사용자별 기관 권한) DELETE 처리후 INSERT 함
		// 역할 (권한구분코드 - AUTHRT_SE_CD) 변경시 SAB230 (사용자별 기관 권한) UPDATE
		String aplyTypeSeCd = StringUtil.nullConvert(dmAprvParam.getValue("APLY_TYPE_SE_CD"));
		String untTaskwkMenuAuthrtCn = StringUtil.nullConvert(dmAprvParam.getValue("UNT_TASKWK_MENU_AUTHRT_CN"));
		
		// 변경신청 기관번호 값 조회
		String instNoVal = StringUtil.nullConvert(dmInstAuthAprv.getValue("INST_NO"));
		
		// 신청자아이디 조회
		String userId = StringUtil.nullConvert(mapParam.get("USER_ID"));
		
		long menuModifyCnt = 0;		// 메뉴 추가변경 건수
		long resultCnt = 0;
		
		LOGGER.info("### processUserAuthAplyByApproval: aplyTypeSeCd={}", aplyTypeSeCd);
		
		switch (aplyTypeSeCd) {
		case "01":	// 총괄관리자
		case "02":	// 기관관리자
		case "03":	// 사업담당자
		case "04":	// 시스템관리자
			boolean adminMenuNotRemoved = false;
			
			if ("01".equals(aplyTypeSeCd)) {
				// 총괄관리자인 경우 권한승인관리 상위 메뉴 번호 삭제 제외
				adminMenuNotRemoved = true;
			} else {
				// 2023.06.05 (Myeong.Jae.Cheol) : 주기관 권한 1개 밖에 없을때, 역할 변경시 기존 총괄관리자 관련 메뉴 삭제
//				adminMenuNotRemoved = checkUserAuthrtRole(userId, "ROLE_GENERAL_ADMIN");
				adminMenuNotRemoved = countUserAuthrtRole(userId, "ROLE_GENERAL_ADMIN") > 1 ? true : false;
			}
			 
			LOGGER.info("### processUserAuthAplyByApproval: 0) adminMenuNotRemoved={}", adminMenuNotRemoved);
			
			// 사용자별 기관 권한중 총괄관리자 존재 여부 체크하여 관련 메뉴가 삭제 안하게 처리
			if (adminMenuNotRemoved) {
				String authrtAprvMgmtMenuNo = "5056";	// 신청승인관리 메뉴번호
				List<Map<String, Object>> menuDetails = mgmtMenuMapper.selectMenuDetails(authrtAprvMgmtMenuNo);	
				if (menuDetails != null && menuDetails.size() > 0) {
					Map<String, Object> menuDtlMap = menuDetails.get(0);
					
					String hghrkMenuNo = StringUtil.nullConvert(menuDtlMap.get("HGHRK_MENU_NO"));	// 최상위메뉴번호
					String me2dNo = StringUtil.nullConvert(menuDtlMap.get("ME2D_NO"));		// 중메뉴번호
					
					List<String> notInMenuNos = Arrays.asList(hghrkMenuNo, me2dNo, authrtAprvMgmtMenuNo);
					mapParam.put("NOT_IN_MENU_NOS", notInMenuNos);
				}
			}
			
			// 총괄관리자 및 기관관리자 시 단위업무 메뉴신청 로직 적용
			if ("01".equals(aplyTypeSeCd) || "02".equals(aplyTypeSeCd)) {
				if (!StringUtil.isEmpty(untTaskwkMenuAuthrtCn)) {
					// 2023.03.29 (Myeong.Jae.Cheol) : 기존 단위업무별 메뉴권한 전체 삭제 무시처리
					menuModifyCnt += addUntTaskwkMenuAuthrt(request, mapParam, untTaskwkMenuAuthrtCn, false);
					
					LOGGER.info("### processUserAuthAplyByApproval: 1) menuModifyCnt={} / userId={}", menuModifyCnt, userId);
				}
			}
			
			// 역할별 사용자별 메뉴권한 (SAB250) 변경처리 (총괄관리자, 기관관리자, 사업담당자)
			menuModifyCnt += updateMenuAuthrt(request, mapParam, instNoVal);
			LOGGER.info("### processUserAuthAplyByApproval: 2) menuModifyCnt={} / userId={}", menuModifyCnt, userId);
			
			mapResult.put("RESULT_CNT", menuModifyCnt);		// 사용자별 메뉴권한 (SAB250) 변경시 변경 건수 취합
			
			// 시스템관리자 메뉴권한 업데이트
			updateSysMngrMenuAuthrt(request, mapParam, instNoVal);
			
			if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
				mapParam.remove("NOT_IN_MENU_NOS");
			}
			
			// 기관 권한 업데이트 처리
			mapParam.put("LAST_MDFR_ID", userDetailsVO.getId());	// 수정 사용자아이디 설정
			resultCnt = userInstAuthMapper.updateUserInstAuth(mapParam);
			break;
		case "05":	// 단위업무 메뉴신청
			if (!StringUtil.isEmpty(untTaskwkMenuAuthrtCn)) {
				menuModifyCnt = addUntTaskwkMenuAuthrt(request, mapParam, untTaskwkMenuAuthrtCn, false);
				LOGGER.info("### processUserAuthAplyByApproval: 3) menuModifyCnt={} / userId={}", menuModifyCnt, userId);
				mapResult.put("RESULT_CNT", menuModifyCnt);		// 사용자별 메뉴권한 (SAB250) 변경시 변경 건수 취합
				resultCnt += menuModifyCnt;
			}
			break;
		case "06":	// 기관변경신청
			// 주기관일때, 중복 소속기관번호 체크
			boolean isMaistChanged = isOgdpInstChanged(mapParam);
			
			// SCA100 (종사자) 테이블 업데이트 처리 (주기관)
			if (isMaistChanged) {
				Map<String, Object> updateParam = new LinkedHashMap<>();
				updateParam.put("USER_ID", mapParam.get("USER_ID"));	// 사용자아아디
				updateParam.put("OGDP_INST_NO", addtngChgInstNo);		// 추가변경기관번호
				updateParam.put("OGDP_DEPT_CD", addtngChgOgdpDeptCd);	// 추가변경소속부서코드
				updateParam.put("OGDP_DEPT_NM", addtngChgOgdpDeptNm);	// 추가변경소속부서명
				updateParam.put("JBPS_SE_CD", addtngChgJbpsSeCd);		// 추가변경직위구분코드
				updateParam.put("JBPS_NM", addtngChgJbpsNm);			// 추가변경직위명
				updateParam.put("MDRF_ID", userDetailsVO.getId());		// 최종수정자아이디
				
				// 전용 가입 구분 변경시 삭제 처리
				// (여성가족부, 중앙관리기관은 변경처리 할 수 없음 - 회원가입 당시에 한번만)
				boolean hasPrvUseJoin = true;		// 전용가입 사용여부 (기본값: true)
				String groupAuthrtSeCd = StringUtil.nullConvert(mapParam.get("GROUP_AUTHRT_SE_CD"));
				char instTypeDiv = groupAuthrtSeCd.charAt(0);
				if (instTypeDiv == '3') {	// 기관 (단위업무별)
					// 전용가입구분: 지자체안정망 (U01)
					String untTaskwkSeCd = StringUtil.nullConvert(dmAprvParam.getValue("UNT_TASKWK_SE_CD"));
					if ("U01".equals(untTaskwkSeCd)) {
						// 추가변경기관번호을 통해 기관 상세 정보 조회
						List<Map<String, Object>> instDtlInfo = mapper.selectInstDetails(Collections.singletonMap("INST_NO", addtngChgInstNo));
						if (instDtlInfo != null && instDtlInfo.size() > 0) {
							Map<String, Object> instDtlMap = instDtlInfo.get(0);
							
							// 기관유형 조회 (시도/시군구 수행기관)
							String instTypeSeCd = StringUtil.nullConvert(instDtlMap.get("INST_TYPE_SE_CD"));
							if ("5".equals(instTypeSeCd) || "8".equals(instTypeSeCd)) {
								updateParam.put("PRVUSE_JOIN_SE_CD", "03");		// 전용가입구분 (지자체)
							} else {
								// 전용가입 구분 초기화
								hasPrvUseJoin = false;
							}
						} else {
							throw new AppWorksException("기관 상세 정보를 찾을 수 없습니다!", Alert.WARN);
						}
					} else {
						// 전용가입 구분 초기화
						hasPrvUseJoin = false;
					}
				} else if (instTypeDiv == '4') {	// 경찰공무원
					updateParam.put("PRVUSE_JOIN_SE_CD", "04");		// 전용가입구분 (경찰청)
				} else if (instTypeDiv == '5') {	// 교육부
					updateParam.put("PRVUSE_JOIN_SE_CD", "05");		// 전용가입구분 (교육청)
				}
				
				if (hasPrvUseJoin) {
					updateParam.put("PRVUSE_JOIN_SCRIN_YN", "Y");	// 전용가입화면여부 : 활성화
				} else {
					updateParam.put("PRVUSE_JOIN_SCRIN_YN", "N");	// 전용가입화면여부 : 비활성화 
					updateParam.put("PRVUSE_JOIN_SE_CD", null);		// 전용가입구분: null 처리
				}
				
				mapper.updateWorkerInfoByUserAuthAply(updateParam);
			}
			
			// 사용자별 메뉴권한 (SAB250) 변경처리
			long updateCnt2 = updateMenuAuthrt(request, mapParam, instNoVal);
			LOGGER.info("### processUserAuthAplyByApproval: 4) updateCnt2={}", updateCnt2);
			mapResult.put("RESULT_CNT", updateCnt2);		// 사용자별 메뉴권한 (SAB250) 변경시 변경 건수 취합
			
			// 기존 기관 권한 삭제
			Map<String, Object> deleteParam = new LinkedHashMap<>();
			deleteParam.put("USER_ID", mapParam.get("USER_ID"));
			deleteParam.put("INST_NO", mapParam.get("INST_NO"));
			resultCnt = userInstAuthMapper.deleteUserInstAuth(deleteParam);
			
			// 기관 권한 추가
			mapParam.put("RGTR_ID", userDetailsVO.getId());			// 등록 사용자아이디 설정
			mapParam.replace("INST_NO", addtngChgInstNo);			// 변경된 기관번호로 설정
			mapParam.put("MAIST_YN", isMaistChanged ? "Y" : "N");	// 주기관여부 설정
			resultCnt = userInstAuthMapper.insertUserInstAuth(mapParam);
			break;
		case "07":	// 기관추가신청
			// 사용자별 메뉴권한 (SAB250) 추가
			insertMenuAuthrt(request, mapParam);
			
			// 기관 권한 등록 처리
			mapParam.put("RGTR_ID", userDetailsVO.getId());	// 등록 사용자아이디 설정
			mapParam.replace("INST_NO", addtngChgInstNo);	// 추가된 기관번호로 설정
			resultCnt = userInstAuthMapper.insertUserInstAuth(mapParam);
			break;
		case "08":	// 기관삭제신청
			// 주기관 삭제 요청인지 체크
			boolean isMaistDeleted = isOgdpInstChanged(mapParam);
			
			if (isMaistDeleted) {
				throw new AppWorksException("주기관은 삭제할 수 없습니다!", Alert.WARN);
			}
			
			// 사용자별 기관 권한중 총괄관리자 존재 여부 체크하여 관련 메뉴가 삭제 안하게 처리
			if (checkUserAuthrtRole(userId, "ROLE_GENERAL_ADMIN")) {
				String authrtAprvMgmtMenuNo = "5056";	// 신청승인관리 메뉴번호
				List<Map<String, Object>> menuDetails = mgmtMenuMapper.selectMenuDetails(authrtAprvMgmtMenuNo);	
				if (menuDetails != null && menuDetails.size() > 0) {
					Map<String, Object> menuDtlMap = menuDetails.get(0);
					
					String hghrkMenuNo = StringUtil.nullConvert(menuDtlMap.get("HGHRK_MENU_NO"));	// 최상위메뉴번호
					String me2dNo = StringUtil.nullConvert(menuDtlMap.get("ME2D_NO"));		// 중메뉴번호
					
					List<String> notInMenuNos = Arrays.asList(hghrkMenuNo, me2dNo, authrtAprvMgmtMenuNo);
					mapParam.put("NOT_IN_MENU_NOS", notInMenuNos);
				}
			}
			
			// 사용자별 메뉴권한 (SAB250) 삭제 처리
			deleteMenuAuthrt(request, mapParam);
			
			if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
				mapParam.remove("NOT_IN_MENU_NOS");
			}
			
			// 기관 권한 삭제 처리
			resultCnt = userInstAuthMapper.deleteUserInstAuth(mapParam);
			break;
		default:
			throw new AppWorksException(String.format("유효하지 않는 신청유형 입니다. (%s)", aplyTypeSeCd), Alert.WARN);
		}
		
		// SAB240 (사용자별 권한 신청) 승인 처리
		Map<String, Object> aprvParam = new LinkedHashMap<>();
		aprvParam.put("aplySn", dmAprvParam.getValue("APLY_SN"));		// 신청일련번호
		aprvParam.put("prcrId", userDetailsVO.getId());					// 처리자아이디
		aprvParam.put("lastMdfrId", userDetailsVO.getId());				// 최종수정자아이디
		resultCnt = mapper.updateUserAuthAplyByApproval(aprvParam);
		
		// 처리 결과 설정
		if (resultCnt > 0) {
			// SMS 메시지 전송
			processSendSMS(dmAprvParam.getValue("APLY_SN"), userDetailsVO.getId());
			
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "권한승인 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "권한승인 처리 실패!");
		}
		
		// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
		mgmtMenuService.increaseMenuUpdateCountByUserId(userId);
		
		return mapResult;
	}
	
	/**
	 * @Method명   : processUserAuthAplyByCancel
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 6. 
	 * @Method설명 : 사용자별 권한 신청 취소 처리
	 */
	@Override
	public Map<String, Object> processUserAuthAplyByCancel(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<>();
		
		// 권한 신청 취소 처리 데이터 DM
		ParameterGroup dmAplyCancel = dataRequest.getParameterGroup("dmAplyCancel");
		LOGGER.debug("processUserAuthAplyByCancel :: {}", dmAplyCancel);
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 2023.04.25 (Myeong.Jae.Cheol) : 권한 신청 상태 체크
		Map<String, Object> checkRstMap = checkAprvStatus(dmAplyCancel.getValue("APLY_SN"));
		if (checkRstMap.containsKey("hasAprvProc")) {
			boolean hasAprvProc = (Boolean) checkRstMap.get("hasAprvProc");
			
			// 이미 다른 승인처리자 (시스템총괄관리자 및 총괄관리자) 가 승인 또는 반려 처리한 경우
			// 권한 신청건 취소 처리 못하게 처리
			if (hasAprvProc) {
				mapResult.put("RESULT_OK", "N");
				mapResult.put("RESULT_MSG", checkRstMap.get("RESULT_MSG"));
				
				return mapResult;
			}
		}
		
		// SAB240 (사용자별 권한 신청) 신청 취소 처리
		Map<String, Object> cancelParam = new LinkedHashMap<>();
		cancelParam.put("aplySn", dmAplyCancel.getValue("APLY_SN"));		// 신청일련번호
		cancelParam.put("lastMdfrId", userDetailsVO.getId());				// 최종수정자아이디
		
		int resultCnt = mapper.updateUserAuthAplyByCancel(cancelParam);
		
		// 처리 결과 설정
		if (resultCnt > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "권한 신청 취소 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "권한 신청 취소 처리 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : checkAprvStatus
	 * @param aplySn		신청일련번호
	 * @return				신청상태 체크 결과값 map
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 25. 
	 * @Method설명 : 권한 신청상태 체크
	 */
	public Map<String, Object> checkAprvStatus(String aplySn) throws Exception {
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<>();
		
		// 해당 신청일련번호에 대한 승인상태 조회
		Map<String, Object> checkParam = new LinkedHashMap<>();
		checkParam.put("aplySn", aplySn);
		
		List<Map<String, Object>> aprvStatusInfo = mapper.selectUserAuthAplyAprvStatus(checkParam);
		if (aprvStatusInfo != null && aprvStatusInfo.size() > 0) {
			Map<String, Object> aprvStatusMap = aprvStatusInfo.get(0);
			
			// 승인상태 조회후 이미 다른 승인처리자 (시스템총괄관리자 및 총괄관리자) 가 승인 또는 반려 처리한 경우
			// 중복으로 승인/반려 처리 못하게 처리
			String aprvSttsSeCd = StringUtil.nullConvert(aprvStatusMap.get("APRV_STTS_SE_CD"));
			
			boolean hasAprvProc = false;
			switch (aprvSttsSeCd) {
			case "0":
			case "1":
				// 대기 또는 요청상태
				hasAprvProc = false;
				break;
			default:
				// 승인 또는 반려상태 등
				hasAprvProc = true;
				break;
			}
			
			// 승인/반려처리 여부
			mapResult.put("hasAprvProc", Boolean.valueOf(hasAprvProc));
			
			if (hasAprvProc) {
				// 승인상태구분명 조회
				String aprvSttsSeNm = StringUtil.nullConvert(aprvStatusMap.get("APRV_STTS_SE_NM"));
				String aprvPrcr = String.format("%s (%s)", aprvStatusMap.get("PRCR_ID"), aprvStatusMap.get("PRCR_FLNM"));
				String prcrYmd = StringUtil.nullConvert(aprvStatusMap.get("PRCS_YMD"));
				
				// 결과메시지 설정
				StringBuilder sb = new StringBuilder();
				sb.append("해당 권한 신청건은 ");
				
				if ("9".equals(aprvSttsSeCd)) {		// 신청자 취소
					// 신청자아이디 조회
					//String lastMdrfId = StringUtil.nullConvert(aprvStatusMap.get("LAST_MDFR_ID"));
					
					// 취소일자 설정
					Date lastMdfcnDt = (Timestamp) aprvStatusMap.get("LAST_MDFCN_DT");
					Instant instant = lastMdfcnDt.toInstant();
					LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault())
							.toLocalDateTime();
					String cancelYmd = dateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
					
					sb.append("사용자로 인하여 ")
						.append(aprvSttsSeNm).append(" 처리 되었습니다.\n")
						.append("\n").append("취소일자: ").append(cancelYmd);
				} else {
					sb.append(String.format("이미 %s 처리 되었습니다.\n", aprvSttsSeNm))
						.append("\n").append("승인처리자: ").append(aprvPrcr)
						.append("\n").append("처리일자: ").append(DateUtil.formatDate(prcrYmd, "-"));
				}
				
				String resultMsg = sb.toString();
				mapResult.put("RESULT_MSG", resultMsg);
			}
		} else {
			mapResult.put("hasAprvProc", Boolean.FALSE);
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : isOgdpInstChanged
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 소속기관 (주기관) 변경 여부 체크
	 */
	public boolean isOgdpInstChanged(Map<String, Object> mapParam) throws Exception {
		boolean isChanged = false;
		
		Map<String, Object> checkParam = new LinkedHashMap<>();
		checkParam.put("USER_ID", mapParam.get("USER_ID"));
		List<Map<String, Object>> workerInfo = mapper.selectWorkerInfoByChgOgdpInst(checkParam);
		if (workerInfo != null && workerInfo.size() > 0) {
			Map<String, Object> workInfoMap = workerInfo.get(0);
			String ogdpInstNo = StringUtil.nullConvert(workInfoMap.get("OGDP_INST_NO"));
			if (ogdpInstNo.equals((String) mapParam.get("INST_NO"))) {
				isChanged = true;
			}
		}
		return isChanged;
	}
	
	/**
	 * @Method명   : addUntTaskwkMenuAuthrt
	 * @param request
	 * @param mapParam
	 * @param untTaskwkMenuAuthrtCn
	 * @param isRemoveAllMenu
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 단위업무 메뉴권한 추가
	 */
	@SuppressWarnings("unchecked")
	public long addUntTaskwkMenuAuthrt(HttpServletRequest request, Map<String, Object> mapParam, 
			String untTaskwkMenuAuthrtCn, boolean isRemoveAllMenu) throws Exception {
		// 단위업무메뉴권한내용 파싱 처리 (JSON String -> Map)
		Map<String, Object> untTaskwkMenuAuthrtMap = ConvertUtils.convertJsonStringToMap(untTaskwkMenuAuthrtCn);
		
		// 권한아이디 목록 생성
		List<String> authrtIdList = new ArrayList<>();
		List<Map<String, Object>> items = (List<Map<String, Object>>) untTaskwkMenuAuthrtMap.get("items");
		if (items != null) {
			items.forEach(map -> {
				authrtIdList.add(StringUtil.nullConvert(map.get("AUTHRT_ID")));
			});
		}
		
		// 권한아이디 목록 Validation 체크
		if (ObjectUtils.isEmpty(authrtIdList)) {
			LOGGER.info("### addUntTaskwkMenuAuthrt: 단위 업무 메뉴 신청 - 권한아이디 목록 없음!");
			return 0;
		}
		
		// 사용자별 메뉴권한 전체삭제
//		if (isRemoveAllMenu) {
//			mgmtUserAuthService.deleteAllUserMenuAuth(request, mapParam);
//		}
		
		// 메뉴 권한 저장 처리
		Map<String, Object> saveParam = new LinkedHashMap<>();
		saveParam.put("USER_ID", mapParam.get("USER_ID"));
		saveParam.put("AUTHRT_SE_CDS", authrtIdList);
		
		Map<String, Object> resultMap = mgmtAuthGrpService.saveMenuAuthMapping(request, saveParam);
		if (resultMap != null) {
			String resultYn = (String) resultMap.get("RESULT_OK");
			if ("N".equals(resultYn)) {
				String resultMsg = "권한 요청 승인중 해당 권한 유형의 메뉴 권한 항목이 누락되었습니다.\n시스템 관리자에게 문의하세요.";
				throw new AppWorksException(resultMsg, Alert.INFO);
			} else {
				if (resultMap.containsKey("RESULT_CNT")) {
					return (Long) resultMap.get("RESULT_CNT");
				}
			}
		}
		return 0;
	}
	
	/**
	 * @Method명   : updateMenuAuthrt
	 * @param request
	 * @param mapParam
	 * @param instNoVal
	 * @return 처리 결과
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 업데이트 처리
	 */
	public long updateMenuAuthrt(HttpServletRequest request, Map<String, Object> mapParam, 
			String instNoVal) throws Exception {
		
		// 변경신청할 사용자아이디 조회
		String userId = StringUtil.nullConvert(mapParam.get("USER_ID"));
		
		// 변경신청할 권한유형구분코드 조회
		String authrtSeCd = StringUtil.nullConvert(mapParam.get("AUTHRT_SE_CD"));
		
		Map<String, Object> findMap = new HashMap<>();
		
		// 사용자별 기관 권한 (SAB230) 조회
		List<Map<String, Object>> instAuthList = userInstAuthMapper.selectUserInstAuthList(userId);
		if (instAuthList != null) {
			Optional<Map<String, Object>> findMapOpt = instAuthList.stream()
					.filter(map -> {
						String tmpInstNoVal = StringUtil.nullConvert(map.get("INST_NO"));
						return tmpInstNoVal.equals(instNoVal);
					})
					.findFirst();
			if (findMapOpt.isPresent()) {
				findMap = findMapOpt.get();
			}
		}
		
		if (!findMap.isEmpty()) {
			UserInstAuthVO userInstAuthVO = ConvertUtils.convertValueObjectAtDataMap(findMap, UserInstAuthVO.class);
			String userAuthrtSeCd = userInstAuthVO.getAuthrtSeCd();		// 변경신청할 사용자의 권한유형코드
			if (!authrtSeCd.equals(userAuthrtSeCd)) {
				
				// 2023.03.24 (Myeong.Jae.Cheol) : 특수한 그룹권한구분코드 체크하여 무시처리 (이인성 PL 요청)
				// 여성가족부-기관관리자(120), 여성가족부-담당자(140), 
				// 중앙관리기관-기관관리자(220), 중앙관리기관-사업담당자(230), 중앙관리기관-담당자(240)
				String groupAuthrtSeCd = StringUtil.nullConvert(mapParam.get("GROUP_AUTHRT_SE_CD"));
				char instTypeDiv = groupAuthrtSeCd.charAt(0);		// 기관유형구분
				char roleDiv = groupAuthrtSeCd.charAt(1);			// 역할구분
				
				boolean existMenuTemplate = true;					// 메뉴템플릿 존재여부
				if (instTypeDiv == '1' || instTypeDiv == '2') {		// 여성가족부, 중앙관리기관(개발원)
					if (roleDiv == '2' || roleDiv == '3' || roleDiv == '4') {	// 기관관리자, 사업담당자, 담당자
						List<Map<String, Object>> menuAuthrtList = mgmtAuthGrpService.selectMenuAuthTemplateList(request, mapParam);
						existMenuTemplate = !ObjectUtils.isEmpty(menuAuthrtList);
					}
				}
				
				// 기존 메뉴 권한 삭제
				Map<String, Object> deleteParam = new LinkedHashMap<>();
				deleteParam.put("USER_ID", userId);							// 삭제할 사용자아이디
				deleteParam.put("AUTHRT_SE_CD", userAuthrtSeCd);			// 삭제할 권한유형코드 (그룹 권한아이디)
				
				// 삭제 제외할 메뉴 번호 목록 설정
				if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
					deleteParam.put("NOT_IN_MENU_NOS", mapParam.get("NOT_IN_MENU_NOS"));
				}
				
				// 2023.06.05 (Myeong.Jae.Cheol) : 주기관 권한 1개 밖에 없을때, 역할 변경시 기존 시스템관리자 옵션 체크
				// 사용자별 기관 권한중 시스템관리자 옵션이 1개 이상인 경우 시스템관리자 옵션 체크하여 존재하면 관련 메뉴번호 제외
				String sysMngrYn = StringUtil.nullConvert(mapParam.get("SYS_MNGR_YN"));
				int sysMngrCnt = countUserAuthrtRole(userId, "IS_SYSTEM_MNGR");
				
				//if (checkUserAuthrtRole(userId, "IS_SYSTEM_MNGR") || "Y".equals(sysMngrYn)) {
				if (sysMngrCnt > 1 || "Y".equals(sysMngrYn)) {
					String mberJoinAprvMenuNo = "5031";	// 회원가입승인 메뉴번호
					List<Map<String, Object>> menuDetails = mgmtMenuMapper.selectMenuDetails(mberJoinAprvMenuNo);	
					if (menuDetails != null && menuDetails.size() > 0) {
						Map<String, Object> menuDtlMap = menuDetails.get(0);
						
						String hghrkMenuNo = StringUtil.nullConvert(menuDtlMap.get("HGHRK_MENU_NO"));	// 최상위메뉴번호
						String me2dNo = StringUtil.nullConvert(menuDtlMap.get("ME2D_NO"));		// 중메뉴번호
						
						List<String> notInMenuNos = Arrays.asList(hghrkMenuNo, me2dNo, mberJoinAprvMenuNo);
						
						if (deleteParam.containsKey("NOT_IN_MENU_NOS")) {
							@SuppressWarnings("unchecked")
							List<String> tmpNotInMenuNos = (List<String>) deleteParam.get("NOT_IN_MENU_NOS");
							
							// 새로운 ArrayList 로 복사
							List<String> copyNotInMenuNos = new ArrayList<>(tmpNotInMenuNos);
							
							// 기존 항목 제거
							deleteParam.remove("NOT_IN_MENU_NOS");
							
							// 중복된 메뉴번호 무시
							for (String menuNo : notInMenuNos) {
								if (!copyNotInMenuNos.contains(menuNo)) {
									copyNotInMenuNos.add(menuNo);
								}
							}
							deleteParam.put("NOT_IN_MENU_NOS", copyNotInMenuNos);
						} else {
							deleteParam.put("NOT_IN_MENU_NOS", notInMenuNos);
						}
					}
				}
				
				LOGGER.info("### updateMenuAuthrt: {}", deleteParam);
				Map<String, Object> resultMap = mgmtAuthGrpService.deleteMenuAuthMapping(request, deleteParam);
				if (resultMap != null) {
					if (resultMap.containsKey("RESULT_CNT")) {
						long resultCnt = (Long) resultMap.get("RESULT_CNT");
						LOGGER.info("### updateMenuAuthrt: Delete resultCnt={} / userId={}", resultCnt, userId);
					}
				}
				
				// 메뉴 권한 저장 처리
				LOGGER.info("### updateMenuAuthrt: existMenuTemplate={}", existMenuTemplate);
				if (existMenuTemplate) {
					Map<String, Object> saveParam = new LinkedHashMap<>();
					saveParam.put("USER_ID", mapParam.get("USER_ID"));
					saveParam.put("AUTHRT_SE_CD", authrtSeCd);
					
					resultMap = mgmtAuthGrpService.saveMenuAuthMapping(request, saveParam);
					if (resultMap != null) {
						if (resultMap.containsKey("RESULT_CNT")) {
							return (Long) resultMap.get("RESULT_CNT");
						}
					}
				}
			}
		}
		
		return 0L;
	}
	
	/**
	 * @Method명   : insertMenuAuthrt
	 * @param request
	 * @param mapParam
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 추가
	 */
	public void insertMenuAuthrt(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		Map<String, Object> saveParam = new LinkedHashMap<>();
		saveParam.put("USER_ID", mapParam.get("USER_ID"));				// 추가할 사용자아이디
		saveParam.put("AUTHRT_SE_CD", mapParam.get("AUTHRT_SE_CD"));	// 추가할 권한유형코드 (그룹 권한아이디)
		
		// 2023.04.13 (Myeong.Jae.Cheol) : 특수한 그룹권한구분코드 체크하여 무시처리
		// 여성가족부-기관관리자(120), 여성가족부-담당자(140), 
		// 중앙관리기관-기관관리자(220), 중앙관리기관-사업담당자(230), 중앙관리기관-담당자(240)
		String groupAuthrtSeCd = StringUtil.nullConvert(mapParam.get("GROUP_AUTHRT_SE_CD"));
		char instTypeDiv = groupAuthrtSeCd.charAt(0);		// 기관유형구분
		char roleDiv = groupAuthrtSeCd.charAt(1);			// 역할구분
		
		boolean existMenuTemplate = true;					// 메뉴템플릿 존재여부
		if (instTypeDiv == '1' || instTypeDiv == '2') {		// 여성가족부, 중앙관리기관(개발원)
			if (roleDiv == '2' || roleDiv == '3' || roleDiv == '4') {	// 기관관리자, 사업담당자, 담당자
				List<Map<String, Object>> menuAuthrtList = mgmtAuthGrpService.selectMenuAuthTemplateList(request, mapParam);
				existMenuTemplate = !ObjectUtils.isEmpty(menuAuthrtList);
			}
		}
		
		// 메뉴 권한 저장 처리
		if (existMenuTemplate) {
			Map<String, Object> resultMap = mgmtAuthGrpService.saveMenuAuthMapping(request, saveParam);
			if (resultMap != null) {
				if (resultMap.containsKey("RESULT_CNT")) {
					long resultCnt = (Long) resultMap.get("RESULT_CNT");
					LOGGER.info("### insertMenuAuthrt: resultCnt={} / userId={}", resultCnt, mapParam.get("USER_ID"));
				}
				
				String resultYn = (String) resultMap.get("RESULT_OK");
				if ("N".equals(resultYn)) {
					String resultMsg = "권한 요청 승인중 해당 권한 유형의 메뉴 권한 항목이 누락되었습니다.\n시스템 관리자에게 문의하세요.";
					throw new AppWorksException(resultMsg, Alert.INFO);
				}
			}
		}
	}
	
	/**
	 * @Method명   : deleteMenuAuthrt
	 * @param request
	 * @param mapParam
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 삭제
	 */
	public void deleteMenuAuthrt(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		Map<String, Object> deleteParam = new LinkedHashMap<>();
		deleteParam.put("USER_ID", mapParam.get("USER_ID"));			// 삭제할 사용자아이디
		deleteParam.put("AUTHRT_SE_CD", mapParam.get("AUTHRT_SE_CD"));	// 삭제할 권한유형코드 (그룹 권한아이디)
		
		// 삭제시 기관 권한 삭제 대상 기관권한 및 주기관일때, 시스템관리자여부가 N 인 경우 시스템관리자 메뉴 삭제
		String userId = StringUtil.nullConvert(mapParam.get("USER_ID"));
		
		// 사용자아이디를 통한 사용자별 기관 권한 (SAB230) 조회
		List<Map<String, Object>> mapList = userInstAuthMapper.selectUserInstAuthList(userId);
		
		// 사용자별 기관 권한 VO 목록으로 변환
		List<UserInstAuthVO> instAuthList = ConvertUtils.convertValueObjectsAtDataSet(mapList, UserInstAuthVO.class);
		if (instAuthList != null) {
			// 주기관 권한 조회
			Optional<UserInstAuthVO> mainInstAuthOpt = instAuthList.stream()
					.filter(m -> m.getMaistYn().equals("Y"))
					.findFirst();
			if (mainInstAuthOpt.isPresent()) {
				String sysMngrYn = mainInstAuthOpt.get().getSysMngrYn();
				if ("N".equals(sysMngrYn)) {	// 주기관이 시스템관리자가 아닌 경우
					Set<UserInstAuthVO> sysMngrInstAuthSet = instAuthList.stream()
							.filter(m -> m.getSysMngrYn().equals("Y"))
							.collect(Collectors.toSet());
					
					// 기관 권한이 주기관 및 서브 기관 1개밖에 존재할 때
					if (sysMngrInstAuthSet != null && sysMngrInstAuthSet.size() == 1) {
						String authrtSeCd = (String) mapParam.get("AUTHRT_SE_CD");
						String sysMngrAuthrtSeCd = "9999999";	/* 공통코드에는 존재하지 않는 그룹권한유형구분코드 */
						deleteParam.put("AUTHRT_SE_CDS", Arrays.asList(authrtSeCd, sysMngrAuthrtSeCd));
						mapParam.remove("AUTHRT_SE_CD");
					}
				}
			}
		}
		
		// 삭제 제외할 메뉴 번호 목록 설정
		if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
			deleteParam.put("NOT_IN_MENU_NOS", mapParam.get("NOT_IN_MENU_NOS"));
		}
		
		LOGGER.info("### deleteMenuAuthrt: {}", deleteParam);
		Map<String, Object> resultMap = mgmtAuthGrpService.deleteMenuAuthMapping(request, deleteParam);
		if (resultMap != null) {
			if (resultMap.containsKey("RESULT_CNT")) {
				long resultCnt = (Long) resultMap.get("RESULT_CNT");
				LOGGER.info("### deleteMenuAuthrt: resultCnt={} / userId={}", resultCnt, mapParam.get("USER_ID"));
			}
		}
	}
	
	/**
	 * @Method명   : updateSysMngrMenuAuthrt
	 * @param request
	 * @param mapParam
	 * @param instNoVal
	 * @return 처리 결과
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 10. 
	 * @Method설명 : 시스템관리자 메뉴 권한 업데이트 처리
	 */
	public boolean updateSysMngrMenuAuthrt(HttpServletRequest request, Map<String, Object> mapParam,
			String instNoVal) throws Exception {
		
		// 변경신청할 사용자아이디 조회
		String userId = StringUtil.nullConvert(mapParam.get("USER_ID"));
		
		// 2023.06.05 (Myeong.Jae.Cheol) : 주기관 권한 1개 밖에 없을때, 역할 변경시 기존 시스템관리자 옵션 체크 
		// 사용자별 기관 권한중 시스템관리자 옵션이 1개 이상인 경우 설정 여부 체크하여 존재하면 무시하도록 처리
		
		int sysMngrCnt = countUserAuthrtRole(userId, "IS_SYSTEM_MNGR");
		//if (checkUserAuthrtRole(userId, "IS_SYSTEM_MNGR")) {
		if (sysMngrCnt > 1) {
			LOGGER.info("### updateSysMngrMenuAuthrt: 이미 기관 권한에 시스템관리자여부 항목 존재.");
			return false;
		}
		
		Map<String, Object> findMap = new HashMap<>();
		
		// 사용자별 기관 권한 (SAB230) 조회
		List<Map<String, Object>> instAuthList = userInstAuthMapper.selectUserInstAuthList(userId);
		if (instAuthList != null) {
			Optional<Map<String, Object>> findMapOpt = instAuthList.stream()
					.filter(map -> {
						String tmpInstNoVal = StringUtil.nullConvert(map.get("INST_NO"));
						return tmpInstNoVal.equals(instNoVal);
					})
					.findFirst();
			if (findMapOpt.isPresent()) {
				findMap = findMapOpt.get();
			}
		}
		
		if (!findMap.isEmpty()) {
			// 시스템관리자여부 체크후 업데이트
			String currSysMngrYn = StringUtil.nullConvert(findMap.get("SYS_MNGR_YN"));
			String sysMngrYn = StringUtil.nullConvert(mapParam.get("SYS_MNGR_YN"));
			
			// 사용자별 기관 권한과 동일하면 무시
			// 만약, 시스템관리자의 권한별 메뉴 (SAB300) 가 변경되면 사용자별 메뉴권한 (SAB250) 의 배치 작업이 필요할 것 같음.
			if (currSysMngrYn.equals(sysMngrYn)) {
				LOGGER.info("### updateSysMngrMenuAuthrt: 이미 동일한 시스템관리자여부.");
				return false;
			}
			
			// 사용자별 메뉴권한 (SAB250) : 시스템관리자 추가/삭제 처리
			String sysMngrAuthrtSeCd = "9999999";	/* 공통코드에는 존재하지 않는 그룹권한유형구분코드 */
			if ("Y".equals(sysMngrYn)) {
				// 시스템관리자 메뉴권한 저장 처리
				Map<String, Object> saveParam = new LinkedHashMap<>();
				saveParam.put("USER_ID", userId);						// 추가할 사용자아이디
				saveParam.put("AUTHRT_SE_CD", sysMngrAuthrtSeCd);		// 추가할 권한유형코드
				
				String groupAuthrtSeCd = StringUtil.nullConvert(mapParam.get("GROUP_AUTHRT_SE_CD"));
				saveParam.put("GROUP_AUTHRT_SE_CD", groupAuthrtSeCd);			// 그룹권한유형코드
				
				LOGGER.info("### updateSysMngrMenuAuthrt: {}", saveParam);
				insertMenuAuthrt(request, saveParam);
			} else {
				// 시스템관리자 메뉴권한 삭제
				Map<String, Object> deleteParam = new LinkedHashMap<>();
				deleteParam.put("USER_ID", userId);						// 삭제할 사용자아이디
				deleteParam.put("AUTHRT_SE_CD", sysMngrAuthrtSeCd);		// 삭제할 권한유형코드
				
				// 삭제 제외할 메뉴 번호 목록 설정
				if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
					deleteParam.put("NOT_IN_MENU_NOS", mapParam.get("NOT_IN_MENU_NOS"));
				}
				
				LOGGER.info("### updateSysMngrMenuAuthrt: {}", deleteParam);
				Map<String, Object> resultMap = mgmtAuthGrpService.deleteMenuAuthMapping(request, deleteParam);
				if (resultMap != null) {
					if (resultMap.containsKey("RESULT_CNT")) {
						long resultCnt = (Long) resultMap.get("RESULT_CNT");
						LOGGER.info("### updateSysMngrMenuAuthrt: Delete ResultCnt={} / userId={}", resultCnt, userId);
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @Method명   : processSendSMS
	 * @param aplySn		신청일련번호
	 * @param loginId		로그인아이디
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 권한 신청 승인/반려시 SMS (문자메시지) 전송 처리
	 */
	public void processSendSMS(String aplySn, String loginId) throws Exception {
		
		// 알림 전송 수신자 정보 조회
		List<Map<String, Object>> receiverInfo = mapper.selectNtcnSendReceiverInfo(Collections.singletonMap("aplySn", aplySn));
		
		if (receiverInfo != null && receiverInfo.size() > 0) {
			// DB 칼럼 암호화값 복호화 처리
			//decodeDbColumns(receiverInfo);

			Map<String, Object> rcvrMap = receiverInfo.get(0);
			
			String aprvSttsSeCd = StringUtil.nullConvert(rcvrMap.get("APRV_STTS_SE_CD"));
			if ("2".equals(aprvSttsSeCd) || "3".equals(aprvSttsSeCd)) {				// 승인 또는 반려
				
				String mblTelno = StringUtil.nullConvert(rcvrMap.get("MBL_TELNO"));
				
				if (StringUtil.isEmpty(mblTelno)) {
					String resultMsg = "수신자의 휴대전화번호가 누락되었습니다.\n시스템 관리자에게 문의하세요.";
					throw new AppWorksException(resultMsg, Alert.INFO);
				}
				
				// 수신자 휴대전화번호 (복수)
				List<String> recvTelNo = new ArrayList<>();
				recvTelNo.add(mblTelno);
				
				// 승인상태구분명 조회
				String aprvSttsSeNm = StringUtil.nullConvert(rcvrMap.get("APRV_STTS_SE_NM"));
				
				// SMS 전송 메시지 설정
				StringBuilder sb = new StringBuilder();
				
				// 헤더 내용
				sb.append("[청소년 안전망 시스템]\n");
				
				// 콘텐츠 내용
				sb.append(rcvrMap.get("USER_FLNM")).append("님 ")			// 신청자명
					.append(rcvrMap.get("APLY_TYPE_SE_NM")).append(" ")		// 신청유형명
					.append("권한 신청이 ").append(aprvSttsSeNm).append(" 되었습니다.");
				
				String contents = sb.toString();
				
				SmsMessageVO smsMessage = new SmsMessageVO();
				
				String senderTelNo = "0516623229";	// 발신자번호
				
				smsMessage.setSenderTelNo(senderTelNo);
				smsMessage.setRecvTelNo(recvTelNo);
				smsMessage.setContents(contents);
				smsMessage.setUserId(loginId);
				
				LOGGER.info(">>> 전송할 휴대전화번호: " + mblTelno);
				LOGGER.info(">>> SMS 내용: " + contents);
				
				// SMS 발송
				try {
					smsService.sendSMS(smsMessage);
				} catch (Exception e) {
					LOGGER.info("#### processSendSMS exception : " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * @Method명   : checkUserAuthrtRole
	 * @param userId		조회할 사용자아이디
	 * @param roleName		조회할 역할명
	 * @return	권한 역할 존재 여부
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 14. 
	 * @Method설명 : 사용자의 권한 역할 체크
	 * <pre>
	 * 	- 사용자별 기관 권한 목록 (SAB230) 에서 해당 되는 역할이 존재하는 체크한다.
	 * 	- 예) ROLE_GENERAL_ADMIN, ROLE_INST_ADMIN, ROLE_BIZ_WORKER, ROLE_WORKER, ROLE_USER)
	 * </pre>
	 */
	public Boolean checkUserAuthrtRole(String userId, String roleName) throws Exception {
		
		// Parameter의 역할명을 대문자로 변환
		roleName = roleName.toUpperCase();
		
		// 역할 유형이 사용자 (청소년 및 보호자) 인 경우 안정망 시스템의 기관 권한을 사용할 수 없음!
		if (UserInstAuthService.TYPE_ROLE_USER.equals(roleName)) {
			throw new AppWorksException(String.format("유효하지 않은 역할명 입니다. (%s)", roleName), Alert.WARN);
		}
		
		// 사용자아이디를 통한 사용자별 기관 권한 (SAB230) 조회
		List<Map<String, Object>> mapList = userInstAuthMapper.selectUserInstAuthList(userId);
		
		// 사용자별 기관 권한 VO 목록으로 변환
		List<UserInstAuthVO> instAuthList = ConvertUtils.convertValueObjectsAtDataSet(mapList, UserInstAuthVO.class);
		if (!ObjectUtils.isEmpty(instAuthList)) {
			// 시스템관리자여부로 진입시 각 기관 권한별로 시스템관리자여부 값(Y) 로 검색한다.
			if (UserInstAuthService.TYPE_IS_SYSTEM_MNGR.equals(roleName)) {
				Set<UserInstAuthVO> findInstAuthSet = instAuthList.stream()
						.filter(m -> m.getSysMngrYn().equals("Y"))
						.collect(Collectors.toSet());
				return !ObjectUtils.isEmpty(findInstAuthSet);	
			} else {
				// 역할구분값 설정
				final char roleDiv;
				
				switch (roleName) {
				case UserInstAuthService.TYPE_ROLE_GENERAL_ADMIN:	// 총괄관리자
					roleDiv = '1';
					break;
				case UserInstAuthService.TYPE_ROLE_INST_ADMIN:		// 기관관리자
					roleDiv = '2';
					break;
				case UserInstAuthService.TYPE_ROLE_BIZ_WORKER:		// 사업담당자
					roleDiv = '3';
					break;
				case UserInstAuthService.TYPE_ROLE_WORKER:			// 담당자
					roleDiv = '4';
					break;
				default:
					throw new AppWorksException(String.format("유효하지 않은 역할명 입니다. (%s)", roleName), Alert.WARN);
				}
				
				// 역할검색 및 결과 설정
				if (roleDiv != 0) {
					Set<UserInstAuthVO> findInstAuthSet = instAuthList.stream()
							.filter(m -> m.getGroupAuthrtSeCd().charAt(1) == roleDiv)
							.collect(Collectors.toSet());
					return !ObjectUtils.isEmpty(findInstAuthSet);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * @Method명   : countUserAuthrtRole
	 * @param userId		조회할 사용자아이디
	 * @param roleName		조회할 역할명
	 * @return	권한 역할 갯수
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 6. 5. 
	 * @Method설명 : 사용자의 권한 역할 갯수 조회
	 * <pre>
	 * 	- 사용자별 기관 권한 목록 (SAB230) 에서 해당 되는 역할이 존재하는 체크한다.
	 * 	- 예) ROLE_GENERAL_ADMIN, ROLE_INST_ADMIN, ROLE_BIZ_WORKER, ROLE_WORKER, ROLE_USER)
	 * </pre>
	 */
	public Integer countUserAuthrtRole(String userId, String roleName) throws Exception {
		
		// Parameter의 역할명을 대문자로 변환
		roleName = roleName.toUpperCase();
		
		// 역할 유형이 사용자 (청소년 및 보호자) 인 경우 안정망 시스템의 기관 권한을 사용할 수 없음!
		if (UserInstAuthService.TYPE_ROLE_USER.equals(roleName)) {
			throw new AppWorksException(String.format("유효하지 않은 역할명 입니다. (%s)", roleName), Alert.WARN);
		}
		
		// 사용자아이디를 통한 사용자별 기관 권한 (SAB230) 조회
		List<Map<String, Object>> mapList = userInstAuthMapper.selectUserInstAuthList(userId);
		
		// 사용자별 기관 권한 VO 목록으로 변환
		List<UserInstAuthVO> instAuthList = ConvertUtils.convertValueObjectsAtDataSet(mapList, UserInstAuthVO.class);
		if (!ObjectUtils.isEmpty(instAuthList)) {
			// 시스템관리자여부로 진입시 각 기관 권한별로 시스템관리자여부 값(Y) 로 검색한다.
			if (UserInstAuthService.TYPE_IS_SYSTEM_MNGR.equals(roleName)) {
				Set<UserInstAuthVO> findInstAuthSet = instAuthList.stream()
						.filter(m -> m.getSysMngrYn().equals("Y"))
						.collect(Collectors.toSet());
				return ObjectUtils.isEmpty(findInstAuthSet) ? 0 : findInstAuthSet.size();	
			} else {
				// 역할구분값 설정
				final char roleDiv;
				
				switch (roleName) {
				case UserInstAuthService.TYPE_ROLE_GENERAL_ADMIN:	// 총괄관리자
					roleDiv = '1';
					break;
				case UserInstAuthService.TYPE_ROLE_INST_ADMIN:		// 기관관리자
					roleDiv = '2';
					break;
				case UserInstAuthService.TYPE_ROLE_BIZ_WORKER:		// 사업담당자
					roleDiv = '3';
					break;
				case UserInstAuthService.TYPE_ROLE_WORKER:			// 담당자
					roleDiv = '4';
					break;
				default:
					throw new AppWorksException(String.format("유효하지 않은 역할명 입니다. (%s)", roleName), Alert.WARN);
				}
				
				// 역할검색 및 결과 설정
				if (roleDiv != 0) {
					Set<UserInstAuthVO> findInstAuthSet = instAuthList.stream()
							.filter(m -> m.getGroupAuthrtSeCd().charAt(1) == roleDiv)
							.collect(Collectors.toSet());
					return ObjectUtils.isEmpty(findInstAuthSet) ? 0 : findInstAuthSet.size();
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * @Method명   : decodeDbColumns
	 * @param results
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 3. 
	 * @Method설명 : DB 칼럼 복호화 처리
	 */
	protected void decodeDbColumns(List<Map<String, Object>> results) {
		/*
		for (Map<String, Object> result : results) {
			result.forEach((key, value) -> {
				String plainText = new String();
				
				// 성명 및 휴대전화번호, 이메일주소 암호화값 복호화 처리
				if ("USER_FLNM".equals(key) || "PRCR_FLNM".equals(key)
						|| "MBL_TELNO".equals(key) || "EML_ADDR".equals(key)) {
					plainText = StringUtil.nullConvert(value);
					String decodeText = scpDb.scpDecB64(plainText);
					result.replace(key, decodeText);
				}
			});
		}
		*/
	}
}
