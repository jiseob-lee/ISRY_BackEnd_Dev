/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.UserInstAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ConvertUtils;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService3;

/**
 * @파일명        : UserInstAuthServiceImpl.java
 * @프로그램 설명 : 사용자별 기관 권한
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 19. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("userInstAuthService")
public class UserInstAuthServiceImpl extends IsryBaseServiceImpl implements UserInstAuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInstAuthServiceImpl.class);
	
	@Resource(name = "userInstAuthMapper")
	private UserInstAuthMapper mapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Autowired
	private RedisService3 redisService;

	/**
	 * @Method명   : storeSession
	 * @param loginVO
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 사용자별 기관 권한 세션 적재
	 */
	@Override
	public void storeSession(UserDetailsVO loginVO) throws Exception {
		// 로그인 VO 체크
		if (loginVO == null) {
			LOGGER.info("loginVO is null.");
			return;
		}
		
		LOGGER.info("##### storeSession: userId={}", loginVO.getId());
		LOGGER.info("##### storeSession: groupAuthrtSeCd={}", loginVO.getGroupAuthrtSeCd());
		
		List<UserInstAuthVO> instAuthList = null;
		List<Map<String, Object>> mapList = mapper.selectUserInstAuthList(loginVO.getId());
		if (mapList != null) {
			// 사용자별 기관 권한이 존재하는 경우
			instAuthList = ConvertUtils.convertValueObjectsAtDataSet(mapList, UserInstAuthVO.class);
		} else {
			// 사용자별 기관 권한이 존재하지 않은 경우
			instAuthList = new ArrayList<>();
		}
		LOGGER.info("##### storeSession: {}", instAuthList);
		
		// 로그인 VO 에 적재
		loginVO.setInstAuthList(instAuthList);
	}

	/**
	 * @Method명   : destorySession
	 * @param request
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 사용자별 기관 권한 세션 삭제
	 */
	@Override
	public void destorySession(HttpServletRequest request) throws Exception {
		LOGGER.info("##### destorySession");
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			// 로그인 VO 에서 사용자별 기관 권한 초기화
			List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
			if (instAuthList != null) {
				instAuthList.clear();
			}
		}
	}

	/**
	 * @Method명   : getOgdpInstNo
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 소속(주)기관번호 조회
	 */
	@Override
	public Integer getOgdpInstNo(HttpServletRequest request) throws Exception {
		Integer instNo = null;
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			// 로그인 VO 에서 사용자별 기관 권한 조회
			List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
			if (instAuthList != null) {
				Optional<UserInstAuthVO> findInstAuthOpt = instAuthList.stream()
						.filter(m -> m.getMaistYn().equals("Y"))
						.findFirst();
				if (findInstAuthOpt.isPresent()) {
					instNo = findInstAuthOpt.get().getInstNo();
				}
			}
		}
		return instNo;
	}
	
	/**
	 * @Method명   : checkAuthrtRole
	 * @param request
	 * @param roleName		역할명
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 14. 
	 * @Method설명 : 권한에 대한 역할 체크
	 * <pre>
	 * 	- 사용자 세션에 저장된 기관 권한 목록 (SAB230) 에서 해당 되는 역할이 존재하는 체크한다.
	 * 	- 예) ROLE_GENERAL_ADMIN, ROLE_INST_ADMIN, ROLE_BIZ_WORKER, ROLE_WORKER, ROLE_USER)
	 * </pre>
	 */
	@Override
	public Boolean checkAuthrtRole(HttpServletRequest request, String roleName) throws Exception {
		
		// Parameter의 역할명을 대문자로 변환
		roleName = roleName.toUpperCase();
		
		// 역할 유형이 사용자 (청소년 및 보호자) 인 경우 안정망 시스템의 기관 권한을 사용할 수 없음!
		if (TYPE_ROLE_USER.equals(roleName)) {
			throw new AppWorksException(String.format("유효하지 않은 역할명 입니다. (%s)", roleName), Alert.WARN);
		}
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 로그인 VO 에서 사용자별 기관 권한 조회
		List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
		if (instAuthList != null) {
			// 시스템관리자여부로 진입시 각 기관 권한별로 시스템관리자여부 값(Y) 로 검색한다.
			if (TYPE_IS_SYSTEM_MNGR.equals(roleName)) {
				Set<UserInstAuthVO> findInstAuthSet = instAuthList.stream()
						.filter(m -> m.getSysMngrYn().equals("Y"))
						.collect(Collectors.toSet());
				return !ObjectUtils.isEmpty(findInstAuthSet);	
			} else {
				// 역할구분값 설정
				final char roleDiv;
				
				switch (roleName) {
				case TYPE_ROLE_GENERAL_ADMIN:	// 총괄관리자
					roleDiv = '1';
					break;
				case TYPE_ROLE_INST_ADMIN:		// 기관관리자
					roleDiv = '2';
					break;
				case TYPE_ROLE_BIZ_WORKER:		// 사업담당자
					roleDiv = '3';
					break;
				case TYPE_ROLE_WORKER:			// 담당자
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
	 * @Method명   : getUserInstAuthVO
	 * @param request
	 * @param instNo	검색할 기관번호
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 사용자별 기관 권한 조회
	 */
	@Override
	public UserInstAuthVO getUserInstAuthVO(HttpServletRequest request, Integer instNo) throws Exception {
		LOGGER.info("##### getUserInstAuthVO");
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			// 로그인 VO 에서 사용자별 기관 권한 조회
			List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
			//refreshInstAuthSession(userDetailsVO, instAuthList);
			
			if (instAuthList != null) {
				Optional<UserInstAuthVO> findInstAuthOpt = instAuthList.stream()
						.filter(m -> m.getInstNo().equals(instNo))
						.findFirst();
				if (findInstAuthOpt.isPresent()) {
					return findInstAuthOpt.get();
				}
			}
		}
		return null;
	}
	
	/**
	 * @Method명   : getAprvInstNoList
	 * @param request
	 * @param mapParam	Map 형식의 검색 데이터
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 7. 
	 * @Method설명 : 승인 기관번호 정보 조회
	 */
	@Override
	public <K, V> Map<String, Object> getAprvInstNoInfo(HttpServletRequest request, Map<? super K, ? super V> mapParam)
			throws Exception {
		LOGGER.info("##### getAprvInstNoInfo(request, mapParam)");
		
		// 요청 화면에 따른 분기처리
		String reqAppId = request.getParameter("_AUTH_APP_ID");
		String reqMenuNo = request.getParameter("_AUTH_MENU_NO");
		
		log.info("##2## reqAppId : " + reqAppId);
		log.info("##2## reqMenuNo : " + reqMenuNo);
		
		if (reqAppId.lastIndexOf("MyAuthrtAplyList.clx") > -1 || "4803".equals(reqMenuNo) ) {	// 마이페이지 > 업무메뉴및권한신청
			// 자신이 권한 신청 내역은 승인 기관번호 정보 조회가 필요없음. 빈 map 으로 전달
			return Collections.emptyMap();
		}
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
				
		// 관리자 일때, 승인기관번호 추가 (시스템총괄관리자, 총괄관리자, 기관관리자 등)
		// 시스템총괄관리자는 전부조회
		String groupAuthrtSeCd = userDetailsVO.getGroupAuthrtSeCd();
		char instTypeDiv = groupAuthrtSeCd.charAt(0);
		char roleDiv = groupAuthrtSeCd.charAt(1);
		
		String aprvInstNo = null;		// 승인기관번호: 총괄관리자에서 사용
		String ogdpInstNo = null;		// 소속기관번호: 기관관리자, 사업담당자, 담당자에서 사용
		
		if (reqAppId.lastIndexOf("MemberJoinApprove1.clx") > -1 || "5031".equals(reqMenuNo) ) {		// 회원 가입 승인
			groupAuthrtSeCd = userDetailsVO.getGroupAuthrtSeCd();
			instTypeDiv = groupAuthrtSeCd.charAt(0);
			roleDiv = groupAuthrtSeCd.charAt(1);
			
			// 시스템관리자 옵션 체크 (일단 주기관으로 필터링 하여 조회)
			// 기관 권한 목록 조회
			List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
			
			String sysMngrYn = "N";
			if (instAuthList != null && instAuthList.size() > 0) {
				// 기관권한 필터링
				Optional<UserInstAuthVO> findInstAuthOpt = instAuthList.stream()
						.filter(m -> "Y".equals(m.getMaistYn()))
						.findFirst();
				
				if (findInstAuthOpt.isPresent()) {
					UserInstAuthVO instAuthVO = findInstAuthOpt.get();
					sysMngrYn = instAuthVO.getSysMngrYn();
				}
			}
			
			// 시스템총괄관리자는 기본적으로 시스템관리자 옵션 체크 필요 없음!
			if (roleDiv == '0') {
				mapResult.put("SYS_MNGR_YN", "Y");
			} else {
				// 시스템관리자 여부 파라메터 설정
				mapResult.put("SYS_MNGR_YN", sysMngrYn);
			}
			
			// 사용자 세션의 기관 번호 조회
			Integer instNo = userDetailsVO.getInstNo();
					
			// 시스템총괄관리자 제외
			if (roleDiv == '1') {
				// 2023.04.10 - 여성가족부/중앙관리기관 (개발원) 및 총괄관리자 및 시스템관리자 여부 (Y) 인 경우 하위기관 전체
				if (instTypeDiv == '1' || instTypeDiv == '2') {
					mapResult.put("ALL_LWPRT_INST_YN", "Y");
					
					// 상위기관번호 파라메터 설정
					mapResult.put("SRCH_UP_INST_NO", String.valueOf(instNo));
				} else {
					// 총괄관리자 (본인기관 + 하위 1단계 아래 기관)
					aprvInstNo = String.valueOf(instNo);
				}
			} else if (roleDiv == '2' || roleDiv == '3' || roleDiv == '4') {
				// 기관관리자 및 사업담당자, 담당자
				ogdpInstNo = String.valueOf(instNo);
			}
		
		} else if (reqAppId.lastIndexOf("RightsApprovalManage.clx") > -1 || "5056".equals(reqMenuNo) ) {	// 신청승인관리 (권한신청)
			
			groupAuthrtSeCd = StringUtil.nullConvert(mapParam.get("GROUP_AUTHRT_SE_CD"));
			
			if (groupAuthrtSeCd == null || "".equals(groupAuthrtSeCd)) {
				groupAuthrtSeCd = userDetailsVO.getGroupAuthrtSeCd();
			}
			
			instTypeDiv = groupAuthrtSeCd.charAt(0);	// 기관유형구분
			roleDiv = groupAuthrtSeCd.charAt(1);		// 역할구분
			
			String aprvInstNoVal = StringUtil.nullConvert(mapParam.get("APRV_INST_NO"));
			
			// 시스템총괄관리자 제외
			// 여성가족부는 상위기관이 없기 때문에 여성가족부 기관으로 자체 승인
			if (instTypeDiv == '1') {
				if (roleDiv == '1') {
					// 총괄관리자
					aprvInstNo = aprvInstNoVal;
					
					// 자신이 속한 기관번호 + 하위기관번호 검색
					ogdpInstNo = String.valueOf(userDetailsVO.getInstNo());
				}
			} else {
				if (roleDiv == '1') {
					// 총괄관리자
					aprvInstNo = aprvInstNoVal;
				} else {
					// 기타권한은 승인권한 없음!
				}
			}
		} else if (reqAppId.lastIndexOf("InstituteApprove.clx") > -1 || "3582".equals(reqMenuNo) ) {	// 기관 승인
			// 사용자 세션의 그룹권한구분코드 조회
			groupAuthrtSeCd = userDetailsVO.getGroupAuthrtSeCd();
			instTypeDiv = groupAuthrtSeCd.charAt(0);
			roleDiv = groupAuthrtSeCd.charAt(1);
			
			// 시스템총괄관리자 제외
			// 여성가족부는 상위기관이 없기 때문에 여성가족부 기관으로 자체 승인
			if (instTypeDiv == '1') {
				if (roleDiv == '1') {	// 총괄관리자
					aprvInstNo = String.valueOf(userDetailsVO.getInstNo());
					
					// 자신이 속한 기관번호 + 하위기관번호 검색 (소속기관번호 설정)
					ogdpInstNo = String.valueOf(userDetailsVO.getInstNo());
				}
			} else {
				if (roleDiv == '1') {	// 총괄관리자
					aprvInstNo = String.valueOf(userDetailsVO.getInstNo());
				} else {
					// 기타권한은 승인권한 없음!
				}
			}
		} else {
			// 추가 화면별로 구현 필요하면 위에 else if 조건문으로 reqAppId 또는 reqMenuNo 비교 처리
			// 기타 화면 접근시 빈 map 으로 전달
			return Collections.emptyMap();
		}
		
		// 소속기관번호 파라메터 설정
		mapResult.put("OGDP_INST_NO", ogdpInstNo);
		
		// 승인기관번호 파라메터 설정
		mapResult.put("APRV_INST_NO", aprvInstNo);
		
		return mapResult;
	}
	
	/**
	 * @Method명   : getAprvInstNoList
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 9. 
	 * @Method설명 : 승인 기관번호 정보 조회
	 */
	@Override
	public Map<String, Object> getAprvInstNoInfo(HttpServletRequest request, DataRequest dataRequest, String dataMapId)
			throws Exception {
		LOGGER.info("##### getAprvInstNoInfo(request, dataRequest, dataMapId)");
		
		// 검색조건 Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmSearch";
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup(dataMapId);
		if (searchParam == null) {
			throw new AppWorksException("검색조건 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### getAprvInstNoInfo: {}", searchParam);
		
		return getAprvInstNoInfo(request, searchParam.getSingleValueMap());
	}
	
	/**
	 * @Method명   : checkInstAuth
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자별 기관 권한 체크
	 */
	@Override
	public Map<String, Object> checkInstAuth(HttpServletRequest request, DataRequest dataRequest, String dataMapId)
			throws Exception {
		LOGGER.info("##### checkInstAuth");
		
		// 검색조건 Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmSearch";
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup(dataMapId);
		if (searchParam == null) {
			throw new AppWorksException("검색조건 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### checkInstAuth: {}", searchParam);
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		LOGGER.info("##### checkInstAuth: userId={}", userDetailsVO.getId());
		LOGGER.info("##### checkInstAuth: groupAuthrtSeCd={}", userDetailsVO.getGroupAuthrtSeCd());
		LOGGER.info("##### checkInstAuth: authrtSeCd={}", userDetailsVO.getAuthrtSeCd());
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 단위업무구분코드 (필수)
		String untTaskwkSeCd = searchParam.getValue("UNT_TASKWK_SE_CD");
		
		LOGGER.info(">>> untTaskwkSeCd: {}", untTaskwkSeCd);
		
		// 사용자별 기관 권한 존재 유무
		boolean hasInstAuth = false;
		
		// 기관 권한 체크 가능여부
		String _instNm = "";
		String _untTaskwkSeCd = "";
		String _instTypeSeCd = "";
		
		// 로그인 VO 에서 사용자별 기관 권한 조회
		List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
		Optional<UserInstAuthVO> findInstAuthOpt = instAuthList.stream()
				.filter(m -> m.getMaistYn().equals("Y"))	// 주기관의 기관 권한만
				.findFirst();
		if (findInstAuthOpt.isPresent()) {
			UserInstAuthVO findInstAuth = findInstAuthOpt.get();
			
			_instNm = findInstAuth.getInstNm();
			_untTaskwkSeCd = findInstAuth.getUntTaskwkSeCd();
			_instTypeSeCd = findInstAuth.getInstTypeSeCd();
		} else {
			// 없으면 로그인 VO 의 값 조회
			_instNm = userDetailsVO.getInstNm();
			_untTaskwkSeCd = userDetailsVO.getUntTaskwkSeCd();
			_instTypeSeCd = userDetailsVO.getInstTypeSeCd();
		}
		
		// 아래의 조건과 일치하면 기관 권한 체크 무시한다. 
		if ("1".equals(_instTypeSeCd) && "여성가족부".equals(_instNm)	/* 여성가족부 */
				|| "4".equals(_instTypeSeCd)	/* 중앙관리기관 */ && "한국청소년상담복지개발원".equals(_instNm)
				|| ("U01".equals(_untTaskwkSeCd)	/* 지자체청소년안전망 */
						&& ("5".equals(_instTypeSeCd) || "8".equals(_instTypeSeCd)))	/* 시도 및 시군구 수행기관 */
		) {
			LOGGER.info("### 사용자별 기관 권한 체크 무시 !!!");
			mapResult.put("IGNORE_CHECK", "1");
			hasInstAuth = true;
		} else {
			if (instAuthList != null && instAuthList.size() > 0) {
				// 기관권한 필터링 (단위업무구분코드)
				Optional<UserInstAuthVO> instAuthOpt = instAuthList.stream()
						.filter(m -> m.getUntTaskwkSeCd().equals(untTaskwkSeCd))
						.findFirst();
				
				hasInstAuth = instAuthOpt.isPresent();
			}
		}
		
		// 응답 결과 설정
		if (hasInstAuth) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "기관 권한 체크 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "해당 단위업무의 기관 권한이 존재하지 않습니다!\n마이페이지 > 권한 승인 요청이 필요합니다.");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : getUnitSysAuthItems
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 사용자별 기관 권한 목록 조회
	 */
	@Override
	public List<Map<String, Object>> getUserInstAuthItems(HttpServletRequest request, DataRequest dataRequest,
			String dataMapId) throws Exception {
		LOGGER.info("##### getUserInstAuthItems");
		
		// 검색조건 Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmSearch";
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup(dataMapId);
		if (searchParam == null) {
			throw new AppWorksException("검색조건 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### getUserInstAuthItems: {}", searchParam);
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		LOGGER.info("##### getUserInstAuthItems: userId={}", userDetailsVO.getId());
		LOGGER.info("##### getUserInstAuthItems: groupAuthrtSeCd={}", userDetailsVO.getGroupAuthrtSeCd());
		LOGGER.info("##### getUserInstAuthItems: authrtSeCd={}", userDetailsVO.getAuthrtSeCd());
		
		List<Map<String, Object>> results = new ArrayList<>();
		
		// 단위업무구분코드 (필수)
		String untTaskwkSeCd = searchParam.getValue("UNT_TASKWK_SE_CD");
		
		// 기관 권한 목록 조회
		List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
		refreshInstAuthSession(userDetailsVO, instAuthList);
		
		// 선택된 단위업무에 따른 기관권한 목록 생성
		if (instAuthList != null && instAuthList.size() > 0) {
			// 단위업무별 기관권한 임시 목록 
			List<UserInstAuthVO> tmpInstAuthList = new ArrayList<>();
			instAuthList.forEach(m -> {
				if (m.getUntTaskwkSeCd().equals(untTaskwkSeCd)) {
					tmpInstAuthList.add(m);
				}
			});
			
			// 사용자별 기관권한 VO => MAP
			results.addAll(ConvertUtils.convertToDataSet(tmpInstAuthList));
		}
		
		LOGGER.info("results={}", results);
		
		return results;
	}
	
	/**
	 * @Method명   : updateInstAuthSession
	 * @param request
	 * @param dataRequest
	 * @param dataMapId
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 권한 세션 업데이트
	 */
	@Override
	public Map<String, Object> updateInstAuthSession(HttpServletRequest request, DataRequest dataRequest,
			String dataMapId) throws Exception {
		LOGGER.info("##### updateInstAuthSession");
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 검색조건 Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmSearch";
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup(dataMapId);
		if (searchParam == null) {
			throw new AppWorksException("검색조건 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### updateInstAuthSession: {}", searchParam);
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 기관 권한 목록 조회
		List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
		refreshInstAuthSession(userDetailsVO, instAuthList);
		
		boolean success = false;
		
		if (instAuthList != null && instAuthList.size() > 0) {
			// 단위업무구분코드 (필수)
			String untTaskwkSeCd = searchParam.getValue("UNT_TASKWK_SE_CD");
			
			// 기관번호 (필수)
			String instNoVal = searchParam.getValue("INST_NO");
			
			// 기관권한 검색
			Optional<UserInstAuthVO> instAuthOpt = instAuthList.stream()
					.filter(m -> m.getUntTaskwkSeCd().equals(untTaskwkSeCd) 
							&& m.getInstNo().equals(Integer.valueOf(instNoVal)))
					.findFirst();
			
			if (instAuthOpt.isPresent()) {
				// 검색한 기관 권한 조회 및 사용자 세션 VO 수정
				UserInstAuthVO tmpInstAuthVO = instAuthOpt.get();
				
				if (tmpInstAuthVO.getInstNo() != userDetailsVO.getInstNo()) {
					LOGGER.info("##### updateInstAuthSession: 기관 권한 세션 업데이트!");
					userDetailsVO.setInstNo(tmpInstAuthVO.getInstNo());	
					userDetailsVO.setInstNm(tmpInstAuthVO.getInstNm());
					userDetailsVO.setInstTypeSeCd(tmpInstAuthVO.getInstTypeSeCd());
					userDetailsVO.setUntTaskwkSeCd(tmpInstAuthVO.getUntTaskwkSeCd());
					userDetailsVO.setGroupAuthrtSeCd(tmpInstAuthVO.getGroupAuthrtSeCd());
					userDetailsVO.setAuthrtSeCd(tmpInstAuthVO.getAuthrtSeCd());
					
					// 사용자 세션 업데이트
					updateUserSession(request, userDetailsVO);
				}
				
				success = true;
			}
		}
		
		// 응답 결과 설정
		if (success) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "기관 권한 세션 업데이트 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "기관 권한 세션 업데이트 처리 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : createInstSrchParams
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 검색 Parameter 생성 (사용자별 기관 권한)
	 */
	@Override
	public Map<String, Object> createInstSrchParams(HttpServletRequest request, DataRequest dataRequest,
			String dataMapId) throws Exception {
		LOGGER.info("##### createInstSrchParams");
		
		// 검색조건 Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmSearch";
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup(dataMapId);
		if (searchParam == null) {
			throw new AppWorksException("검색조건 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### createInstSrchParams: {}", searchParam);
		
		return createInstSrchParams(request, searchParam.getSingleValueMap());
	}
	
	/**
	 * @Method명   : createInstSrchParams
	 * @param request
	 * @param mapParam		Map 형식의 검색 데이터
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 검색 Parameter 생성 (사용자별 기관 권한)
	 */
	@Override
	public <K, V> Map<String, Object> createInstSrchParams(HttpServletRequest request,
			Map<? super K, ? super V> mapParam) throws Exception {
		
		if (Objects.isNull(mapParam)) {
			throw new AppWorksException("검색조건 객체가 없습니다.", Alert.ERROR);
		}
		
		// 단위업무구분코드 (필수)
		String untTaskwkSeCd = (String) mapParam.get("UNT_TASKWK_SE_CD");
		
		return createInstSrchParams(request, untTaskwkSeCd);
	}
	
	/**
	 * @Method명   : createInstSrchParams
	 * @param request
	 * @param untTaskwkSeCd		단위업무구분코드 (필수)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 검색 Parameter 생성 (사용자별 기관 권한)
	 */
	@Override
	public Map<String, Object> createInstSrchParams(HttpServletRequest request, String untTaskwkSeCd) throws Exception {
		if (StringUtil.isEmpty(untTaskwkSeCd)) {
			throw new AppWorksException("필수값인 단위업무구분코드가 없습니다.", Alert.ERROR);
		}
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		LOGGER.debug("##### createInstSrchParams: userId={}", userDetailsVO.getId());
		LOGGER.debug("##### createInstSrchParams: groupAuthrtSeCd={}", userDetailsVO.getGroupAuthrtSeCd());
		LOGGER.debug("##### createInstSrchParams: authrtSeCd={}", userDetailsVO.getAuthrtSeCd());
		
		// 결과를 담을 리스트 객체 생성
		Map<String, Object> resultMap = new HashMap<>();
		
		// 기관번호 리스트 객체 생성
		List<Integer> instNoList = new ArrayList<>();
		
		// 기관 권한 목록 조회
		List<UserInstAuthVO> instAuthList = userDetailsVO.getInstAuthList();
		refreshInstAuthSession(userDetailsVO, instAuthList);
		
		if (ObjectUtils.isEmpty(instAuthList)) {
			LOGGER.info("##### createInstSrchParams: 로그인한 사용자의 기관 권한이 없음!");
			
			// 단위업무별 권한에서 기관번호가 조회되지 않으면 사용자 세션 VO 의 기관번호를 설정
			Object defInstNoList = Collections.singletonList(userDetailsVO.getInstNo());
			Map<String, Object> defaultMap = Collections.singletonMap(KEY_INST_NOS, defInstNoList);
			return defaultMap;
		}
		
		// 선택된 기관번호 추가
		Integer selectInstNo = userDetailsVO.getInstNo();
//		LOGGER.debug("##### createInstSrchParams: 사용자 세션 - 기관번호 추가 !!! [{}]", userDetailsVO.getInstNo());
//		instNoList.add(selectInstNo);
		
		// 총괄 권한이면 하위기관번호 셋팅
		String groupAuthrtSeCd = "";
		
		// 선택된 기관 권한의 그룹권한구분코드 조회
		Optional<UserInstAuthVO> findInstAuthOpt = instAuthList.stream()
				.filter(m -> m.getInstNo().equals(selectInstNo))
				.findFirst();
		if (findInstAuthOpt.isPresent()) {
			UserInstAuthVO findInstAuth = findInstAuthOpt.get();
			groupAuthrtSeCd = findInstAuth.getGroupAuthrtSeCd();
		} else {
			// 없으면 로그인 VO 에 있는 주기관의 그룹권한구분코드 조회
			groupAuthrtSeCd = userDetailsVO.getGroupAuthrtSeCd();
		}
		
		instNoList = getLwprtInstList(selectInstNo, groupAuthrtSeCd, untTaskwkSeCd);
//		if (!ObjectUtils.isEmpty(lwprtInstList)) {
//			List<Integer> lwprtInstNoList = lwprtInstList.stream()
//					.filter(map -> untTaskwkSeCd.equals(map.get("UNT_TASKWK_SE_CD")))
//					.map(map -> Integer.valueOf(map.get("INST_NO").toString()))
//					.collect(Collectors.toList());
			
//			instNoList.addAll(lwprtInstList);
//		}
		
//		if (instNoList.size() > 0) {
//			// 기관번호 중복제거
//			instNoList = instNoList.stream()
//					.distinct()
//					.collect(Collectors.toList());
//		}
		if(instNoList == null || instNoList.size() == 0) {
			instNoList.add(selectInstNo);
		}
		LOGGER.debug("##### createInstSrchParams: instNoList.size()={}", instNoList.size());
		
		// 검색된 기관번호 목록 설정
		resultMap.put(KEY_INST_NOS, instNoList);
		
		LOGGER.debug("##### createInstSrchParams: resultMap={}", resultMap);
		
		return resultMap;
	}
	
	/**
	 * @Method명   : getLwprtInstList
	 * @param selectInstNo			선택된 기관번호
	 * @param groupAuthrtSeCd		그룹권한구분코드
	 * @param untTaskwkSeCd			단위업무구분코드
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 하위기관번호 조회
	 */
	protected List<Integer> getLwprtInstList(Integer selectInstNo, String groupAuthrtSeCd, String untTaskwkSeCd) throws Exception {
		Objects.requireNonNull(selectInstNo, "selectInstNo must not be null");
		Objects.requireNonNull(groupAuthrtSeCd, "groupAuthrtSeCd must not be null");
		Objects.requireNonNull(untTaskwkSeCd, "untTaskwkSeCd must not be null");
		
		List<Integer> results = new ArrayList<>();
		
		// 2023.04.28 (Myeong.Jae.Cheol) : 일반 사용자 진입 관련 방어코드
		char instTypeDiv = groupAuthrtSeCd.charAt(0);
		if (instTypeDiv == '9') {
			throw new AppWorksException(String.format("유효하지 않은 그룹권한 입니다. (codeVal=%s)", groupAuthrtSeCd), Alert.INFO);
		}
		
		LOGGER.info("##### getLwprtInstList: instTypeDiv={} / groupAuthrtSeCd={} / untTaskwkSeCd={}", instTypeDiv, groupAuthrtSeCd, untTaskwkSeCd);
		
		// 그룹권한구분코드가 총괄인 경우 하위기관 목록 조회
		char roleDiv = groupAuthrtSeCd.charAt(1);
		
		// 시스템총괄관리자 또는 총괄관리자
		if (roleDiv == '0' || roleDiv == '1') {
			Map<String, Object> paramMap = new HashMap<>();
			
			if (instTypeDiv == '3') {	// 단위업무기관별 권한
				// 2023.05.04 (Myeong.Jae.Cheol) : 단위업무구분코드 파라메터 값이 U01 (지자체청소년안정망), 
				// 청소년쉼터(U04), 청소년자립지원단(U05), 청소년회복지원시설(U06) 이 아닌경우만 처리
				boolean enableFindUpInstNo;
				
				switch (untTaskwkSeCd) {
				case "U01":		// 지자체청소년안정망
				case "U04":		// 청소년쉼터
				case "U05":		// 청소년자립지원단
				case "U06":		// 청소년회복지원시설
					// 청소년쉼터, 청소년자립지원단, 청소년회복지원시설 해당기관의 상위기관은 시/도 지자체 행정기관임.
					enableFindUpInstNo = false;
					break;
				default:
					enableFindUpInstNo = true;
					break;
				}
				
				if (enableFindUpInstNo) {
					// 상위기관 상세 조회 후 해당 데이터의 기관유형구분코드가 5 (시도 및 시도수행기관) 이면서 
					// U01 (지자체청소년안정망) 인 경우 하위기관번호 목록 조회 다르게 처리
					List<Map<String, Object>> upInstDetails = mapper.selectUpInstDetails(String.valueOf(selectInstNo));
					if (upInstDetails != null && upInstDetails.size() > 0) {
						Map<String, Object> upInstDtlMap = upInstDetails.get(0);
						
						String _untTaskwkSeCd = StringUtil.nullConvert(upInstDtlMap.get("UNT_TASKWK_SE_CD"));
						String _instTypeSeCd = StringUtil.nullConvert(upInstDtlMap.get("INST_TYPE_SE_CD"));
						
						if ("U01".equals(_untTaskwkSeCd) && "5".equals(_instTypeSeCd)) {
							paramMap.put("srchUntTaskwkSeCd", untTaskwkSeCd);
							paramMap.put("srchUpInstNo", upInstDtlMap.get("INST_NO"));	// 상위기관번호
						}
					}
				}
			}
			
			if (!paramMap.containsKey("srchUpInstNo")) {
				paramMap.put("srchUpInstNo", String.valueOf(selectInstNo));	// 선택된 기관번호
				paramMap.put("srchUntTaskwkSeCd", untTaskwkSeCd);
			}
			
			results = mapper.searchLwprtInstList(paramMap);
		}
		
		return results;
	}
	
	/**
	 * @Method명   : refreshInstAuthSession
	 * @param loginVO
	 * @param instAuthList
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자별 기관 권한 갱신처리 (임시)
	 */
	protected void refreshInstAuthSession(UserDetailsVO loginVO, List<UserInstAuthVO> instAuthList) throws Exception {
		// 2023-02-22 (Myeong.Jae.Cheol) : 세션 관련 방어코드 적용
		if (instAuthList == null) {
			LOGGER.info("refreshInstAuthSession: >>> Session IS NULL !!! Reload UserInstAuthList...");
			List<Map<String, Object>> mapList = mapper.selectUserInstAuthList(loginVO.getId());
			
			if (mapList != null) {
				// 사용자별 기관 권한이 존재하는 경우
				instAuthList = ConvertUtils.convertValueObjectsAtDataSet(mapList, UserInstAuthVO.class);
			} else {
				// 사용자별 기관 권한이 존재하지 않은 경우
				instAuthList = new ArrayList<>();
			}
			
			// 로그인 VO 에 적재
			loginVO.setInstAuthList(instAuthList);
		}
		
		LOGGER.info(">>> instAuthList: {}", instAuthList);
	}
	
	/**
	 * @Method명   : updateUserSession
	 * @param request
	 * @param userDetailsVO
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자 세션 업데이트
	 */
	protected void updateUserSession(HttpServletRequest request, UserDetailsVO userDetailsVO) {
		HttpSession session = request.getSession();
		String redisKey = "LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId();
		
		// 프로파일 조회하여 세션 업데이트 처리
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

		//if ("local".equals(profile) || "pre".equals(profile)) {
		if ("local".equals(profile)) {
			session.setAttribute("loginVO", userDetailsVO);
		} else {
			redisService.insertRedisMap(redisKey, userDetailsVO.getMap());
		}
	}
}
