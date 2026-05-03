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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.sysmgmt.userauth.mapper.UserAuthAplyMapper;
import isry.itgcms.sysmgmt.userauth.service.UserAuthAplyService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : UserAuthAplyServiceImpl.java
 * @프로그램 설명 : 사용자 권한 신청
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 20. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("userAuthAplyService")
public class UserAuthAplyServiceImpl extends IsryBaseServiceImpl implements UserAuthAplyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthAplyServiceImpl.class);
	
	@Resource(name = "userAuthAplyMapper")
	private UserAuthAplyMapper mapper;
	
	@Resource(name="renuNoMapper")
	private RenuNoMapper renuNoMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : selectAplyInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 신청 기관 목록 조회 (콤보박스)
	 */
	@Override
	public List<Map<String, Object>> selectAplyInstList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		LOGGER.info("##### selectAplyInstList");
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		List<Map<String, Object>> results = mapper.selectAplyInstList(Collections.singletonMap("userId", userDetailsVO.getId()));
		
		return results;
	}
	
	/**
	 * @Method명   : selectComboDataList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 콤보박스 데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectComboDataList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		// 검색조건 Parameter 설정
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmCmbSrch");
		Map<String, Object> mapParam = new LinkedHashMap<>();
		searchParam.getSingleValueMap().forEach(mapParam::put);
		mapParam.remove("CMB_TYPE_VAL");
		
		// 결과 목록 변수 초기화
		List<Map<String, Object>> results = new ArrayList<>();
		
		// 콤보박스 유형 조회
		String cmbType = searchParam.getValue("CMB_TYPE_VAL");
		
		switch (cmbType) {
			case "RGN_SIDO":
				// 지역 (시/도) 목록 조회
				results = mapper.selectRgnSidoCombo(mapParam);
				break;
			case "RGN_SGG":
				// 지역 (시/군/구) 목록 조회
				results = mapper.selectRgnSggCombo(mapParam);
				break;
			case "INST":
				// 기관 목록 조회
				results = mapper.selectRgnInstCombo(mapParam);
				break;
			case "INST_DEPT":
				// 기관 부서 목록 조회
				results = mapper.selectInstDeptCombo(mapParam);
				break;
			default:
				throw new AppWorksException("지원되지 않는 콤보박스 유형입니다.", Alert.ERROR);
		}
		
		return results;
	}
	
	/**
	 * @Method명   : saveUserAuthAply
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 사용자별 권한 신청 저장
	 */
	@Override
	public Map<String, Object> saveUserAuthAply(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<>();
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 권한 신청 DS
		ParameterGroup dsUserAuthrtAply = dataRequest.getParameterGroup("dsUserAuthrtAply");
		LOGGER.debug("saveUserAuthAply :: {}", dsUserAuthrtAply);
		
		// 2023.04.25 (Myeong.Jae.Cheol) : 권한 신청시 같은 유형건 중복 체크
		Map<String, Object> checkParam = new LinkedHashMap<>();
		
		// checkParam 에 검색할 Parameter 설정
		dsUserAuthrtAply.getSingleValueMap().forEach((key, value) -> {
			if ("APLY_TYPE_SE_CD".equals(key) // 신청유형구분코드
					|| "USER_ID".equals(key)) {
				checkParam.put(key, value);
			}
		});
		
		// 권한 신청 중복 체크
		Long duplicateCnt = mapper.selectUserAuthAplyDuplicateCheck(checkParam);
		if (duplicateCnt != null && duplicateCnt > 0) {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "해당 신청 유형의 권한 신청건이 존재합니다.");
			
			return mapResult;
		}
		
		// 사용자권한신청일련번호 채번 생성
		Map<String, String> reqRenuMoMap = new HashMap<>();
		reqRenuMoMap.put("USER_ID", userDetailsVO.getId());
		reqRenuMoMap.put("RENU_NO_SE_CD", "UA");	// 사용자권한신청일련번호 채번코드
		reqRenuMoMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
		
		// 채번서비스 호출
		Map<String, Object> renuNoMap = renuNoMapper.selectCaseMngNoRenu(reqRenuMoMap);
		String lastRenuNo = StringUtil.nullConvert(renuNoMap.get("RENU_NO"));
		LOGGER.debug("### lastRenuNo: {}", lastRenuNo);
		
		// 등록 Parameter 생성 및 등록 처리
		Map<String, Object> insertParam = new LinkedHashMap<>(dsUserAuthrtAply.getSingleValueMap());
		insertParam.put("APLY_SN", lastRenuNo);
		insertParam.put("RGTR_ID", userDetailsVO.getId());
		
		int resultCnt = mapper.insertUserAuthAply(insertParam);
		
		LOGGER.debug("saveUserAuthAply :: resultCnt={}", resultCnt);
		
		// 등록 결과 설정
		if (resultCnt > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "권한신청 저장 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "권한신청 저장 처리 실패!");
		}
		
		return mapResult;
	}
}
