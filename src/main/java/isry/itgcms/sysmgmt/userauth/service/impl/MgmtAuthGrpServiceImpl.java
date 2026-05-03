/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtAuthGrpMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtUserAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtAuthGrpService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : MgmtAuthGrpServiceImpl.java
 * @프로그램 설명 : 권한 그룹 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mgmtAuthGrpService")
public class MgmtAuthGrpServiceImpl extends IsryBaseServiceImpl implements MgmtAuthGrpService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MgmtAuthGrpServiceImpl.class);

	@Resource(name="mgmtAuthGrpMapper")
    private MgmtAuthGrpMapper mgmtAuthGrpMapper;
	
	@Resource(name="mgmtUserAuthMapper")
    private MgmtUserAuthMapper mgmtUserAuthMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;
	
	/**
	 * @Method명   : saveAuthGrp
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2021. 12. 6. 
	 * @Method설명 :
	 */
	@Override
	public void saveAuthGrp(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup param = dataRequest.getParameterGroup("dsAuthGrp");
		
		if (param != null) {
			List<Map<String, String>> list = param.getAllRowList();
			
			//List<Map<String, String>> list1 = param.getInsertedRowList();
			//List<Map<String, String>> list2 = param.getUpdatedRowList();

			if (list != null && list.size() > 0) {
				mgmtAuthGrpMapper.deleteAllAuthGrp();
				
				if (list != null) {
					for (int i=0; i < list.size(); i++) {
						Map<String, String> map = list.get(i);
						map.put("USER_ID", userId);
						mgmtAuthGrpMapper.saveAuthGrp(map);
						
						// 이력 관리
						if ("U".equals(map.get("ROW_STATE"))) {
							map.put("DATAA_CHG_SE_CD", "U");
						} else {
							map.put("DATAA_CHG_SE_CD", "I");
						}
						mgmtAuthGrpMapper.saveAuthGrpHistory(map);
					}
				}
			}
		}
		
	}

	@Override
	public void savePersonalAuthGrpMapping(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup param1 = dataRequest.getParameterGroup("dsSelectedAuthGrp");
		
		ParameterGroup param2 = dataRequest.getParameterGroup("dmMemberId");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		LOGGER.debug("savePersonalAuthGrpMapping :: {}", param1);
		
		if (param1 != null && param2 != null) {
			
			// 추가된 로우 정보
			Iterator<ParameterRow> insertedRows = param1.getInsertedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> row = insertedRows.next().toMap();
				LOGGER.debug("savePersonalAuthGrpMapping :: insertedRow => {}", row);
			}
			
			// 삭제된 로우 정보
			Iterator<ParameterRow> deletedRows = param1.getDeletedRows();
			
			while (deletedRows.hasNext()) {
				Map<String, String> row = deletedRows.next().toMap();
				LOGGER.debug("savePersonalAuthGrpMapping :: deletedRow => {}", row);
			}
			
			String userId = param2.getValue("USER_ID");
			
			List<Map<String, String>> list = param1.getAllRowList();
			
			if (list != null && list.size() > 0) {				
				// SAB250 (사용자별 메뉴권한) 일괄 Insert
				Map<String, Object> mapParam = new LinkedHashMap<>();
				mapParam.put("USER_ID", userId);	// merge할 사용자아이디
				mapParam.put("RGTR_ID", userId2);	// 등록 (수정)자 아이디
				
				// 권한아이디 목록 취합
				Set<String> authrtIdList = list.stream()
					.map(m -> m.get("AUTHRT_ID"))
					.collect(Collectors.toSet());
				mapParam.put("AUTHRT_IDS", authrtIdList);	// 권한아이디 (복수)
				
				// 사용자별 메뉴권한 저장 처리
				mgmtAuthGrpMapper.saveUserMenuAuth(mapParam);
				
				// 사용자별 메뉴권한 이력 변경데이터 조회
				List<Map<String, Object>> chgHistoryInfos = mgmtAuthGrpMapper.selectUserMenuAuthChgHistoryInfos(mapParam);
				for (Map<String, Object> chgHistoryInfo : chgHistoryInfos) {
					// 사용자별 메뉴권한 이력 저장
					chgHistoryInfo.put("RGTR_ID", userId2);			// 등록 (수정)자 아이디
					
					// 데이터 변경여부 체크하여 데이터변경구분코드 변경
					String dataaChgYn = StringUtil.nullConvert(chgHistoryInfo.get("DATAA_CHG_YN"));
					if ("Y".equals(dataaChgYn)) {
						chgHistoryInfo.put("DATAA_CHG_SE_CD", "U");				// 이력 변경으로 지정
					} else {
						chgHistoryInfo.put("DATAA_CHG_SE_CD", "I");				// 이력 추가로 지정
					}
					mgmtAuthGrpMapper.insertUserMenuAuthHistory(chgHistoryInfo);
					
					String chgSeCd = StringUtil.nullConvert(chgHistoryInfo.get("DATAA_CHG_SE_CD"));
					if ("D".equals(chgSeCd)) {	// 메뉴권한 삭제처리 (삭제된 이력)
						Map<String, Object> deleteParam = new HashMap<>();
						deleteParam.put("userId", chgHistoryInfo.get("USER_ID"));
						deleteParam.put("menuId", chgHistoryInfo.get("MENU_NO"));
						mgmtUserAuthMapper.deleteUserAuth(deleteParam);
					}
				}
			}

			// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
			mgmtMenuMapper.updateUserMenuUpdateCountIncrease(userId);
			
		}
	}
	
	/**
	 * @Method명   : saveMenuAuthMapping
	 * @param request		
	 * @param dataRequest
	 * @param dataMapId		Parameter 데이터맵 ID (기본값: dmParam)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 메뉴 권한 저장 처리
	 * <pre>
	 * 	- 회원 가입 승인시 사용<br>
	 *  - 권한 승인시 사용
	 * </pre>
	 */
	@Override
	public Map<String, Object> saveMenuAuthMapping(HttpServletRequest request, DataRequest dataRequest,
			String dataMapId) throws Exception {
		LOGGER.info("##### saveMenuAuthMapping(request, dataRequest, dataMapId) init...");
		
		// Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmParam";
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup(dataMapId);
		if (paramGroup == null) {
			throw new AppWorksException("Parameter 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### saveMenuAuthMapping: {}", paramGroup);
		
		return saveMenuAuthMapping(request, paramGroup.getSingleValueMap());
	}
	
	/**
	 * @Method명   : saveMenuAuthMapping
	 * @param request
	 * @param mapParam	Map 형식의 검색 데이터
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 24. 
	 * @Method설명 : 메뉴 권한 저장 처리
	 * <pre>
	 * 	- 회원 가입 승인시 사용<br>
	 *  - 권한 승인시 사용
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Map<String, Object> saveMenuAuthMapping(HttpServletRequest request,
			Map<? super K, ? super V> mapParam) throws Exception {
		LOGGER.info("##### saveMenuAuthMapping(request, mapParam) init...");
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 사용자아이디 (필수)
		String userId = (String) mapParam.get("USER_ID");
		
		// SAB250 (사용자별 메뉴권한) 일괄 Insert
		Map<String, Object> insertParam = new LinkedHashMap<>();
		insertParam.put("USER_ID", userId);					// 메뉴 권한 추가할 사용자아이디
		insertParam.put("RGTR_ID", userDetailsVO.getId());	// 등록 (수정)자 아이디
		
		// 권한아이디 목록 취합 - 단위업무별 메뉴 권한 복수 등록 대응
		List<String> authrtIdList = new ArrayList<>();
		if (mapParam.containsKey("AUTHRT_SE_CDS")) {
			List<String> authrtSeCds = (List<String>) mapParam.get("AUTHRT_SE_CDS");
			authrtIdList.addAll(authrtSeCds);
		} else {
			/* 권한구분코드 (AUTHRT_SE_CD) => 권한아이디로 맵핑 (AUTHRT_ID) */
			String authrtId = (String) mapParam.get("AUTHRT_SE_CD");
			authrtIdList = Arrays.asList(authrtId);
		}
		insertParam.put("AUTHRT_IDS", authrtIdList);	// 권한아이디 (복수)
		
		// 사용자별 메뉴권한 저장 처리
		int resultCnt = mgmtAuthGrpMapper.saveUserMenuAuth(insertParam);
		
		// 추가후 SAB250 (사용자별 메뉴권한) 목록 조회 
		List<Map<String, Object>> userMenuAuthrtList = mgmtUserAuthMapper.selectUserAuthList(insertParam);
		
		// 사용자별 메뉴권한 이력 변경데이터 조회
		List<Map<String, Object>> chgHistoryInfos = mgmtAuthGrpMapper.selectUserMenuAuthChgHistoryInfos(insertParam);
		
		if (chgHistoryInfos != null && chgHistoryInfos.size() > 0) {
			Iterator<Map<String, Object>> iter = chgHistoryInfos.iterator();
			while (iter.hasNext()) {
				Map<String, Object> map = (Map<String, Object>) iter.next();
				
				Long recDiffTm = (Long) map.get("REC_DIFF_TM");
				String dataaChgYn = StringUtil.nullConvert(map.get("DATAA_CHG_YN"));
				//String dattaChgSeCd = StringUtil.nullConvert(map.get("DATAA_CHG_SE_CD"));
				
				// 변경된지 얼마 안된 데이터는 Insert 시 UNIQUE 에러 발생함으로 1초 정도의 지연시간을 줌.
				if (dataaChgYn == "N" || (recDiffTm == null || recDiffTm == 0)) {
					LOGGER.info("### saveMenuAuthMapping: MENU_NO={}", map.get("MENU_NO"));
					//iter.remove();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					break;
				}
			}
			
			for (Map<String, Object> chgHistoryInfo : chgHistoryInfos) {
				// 사용자별 메뉴권한 이력 저장
				chgHistoryInfo.put("RGTR_ID", userDetailsVO.getId());	// 등록 (수정)자 아이디	
				chgHistoryInfo.put("DATAA_CHG_SE_CD", "I");				// 이력 추가로 지정
				mgmtAuthGrpMapper.insertUserMenuAuthHistory(chgHistoryInfo);
			}
		} else {
			for (Map<String, Object> menuAuthrtMap : userMenuAuthrtList) {
				// 사용자별 메뉴권한 이력 저장
				menuAuthrtMap.put("RGTR_ID", userDetailsVO.getId());	// 등록 (수정)자 아이디	
				menuAuthrtMap.put("DATAA_CHG_SE_CD", "I");				// 이력 추가로 지정
				mgmtAuthGrpMapper.insertUserMenuAuthHistory(menuAuthrtMap);
			}
		}
		
		// 등록 결과 설정
		// 2023.03.24 (Myeong.Jae.Cheol) : 저장갯수 전달
		mapResult.put("RESULT_CNT", Long.valueOf(resultCnt));
		if (resultCnt > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "메뉴 권한 저장 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "메뉴 권한 저장 처리 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : deleteMenuAuthMapping
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		Parameter 데이터맵 ID (기본값: dmParam)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 삭제 처리
	 * <pre>
	 * 	- 내 정보 수정시 사용<br>
	 *  - 기관 권한 삭제시 사용
	 * </pre>
	 */
	@Override
	public Map<String, Object> deleteMenuAuthMapping(HttpServletRequest request, DataRequest dataRequest,
			String dataMapId) throws Exception {
		LOGGER.info("##### deleteMenuAuthMapping(request, dataRequest, dataMapId) init...");
		
		// Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmParam";
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup(dataMapId);
		if (paramGroup == null) {
			throw new AppWorksException("Parameter 데이터맵이 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("##### deleteMenuAuthMapping: {}", paramGroup);
		
		return deleteMenuAuthMapping(request, paramGroup.getSingleValueMap());
	}
	
	/**
	 * @Method명   : deleteMenuAuthMapping
	 * @param request
	 * @param mapParam	Map 형식의 Parameter
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 메뉴 권한 삭제 처리
	 * <pre>
	 * 	- 내 정보 수정시 사용<br>
	 *  - 기관 권한 삭제시 사용
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Map<String, Object> deleteMenuAuthMapping(HttpServletRequest request,
			Map<? super K, ? super V> mapParam) throws Exception {
		LOGGER.info("##### deleteMenuAuthMapping(request, mapParam) init...");
		
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 사용자아이디 (필수)
		String userId = (String) mapParam.get("USER_ID");
		
		// SAB250 (사용자별 메뉴권한) 일괄 Delete
		Map<String, Object> deleteParam = new LinkedHashMap<>();
		deleteParam.put("USER_ID", userId);					// 메뉴 권한 삭제할 사용자아이디
		
		// 권한아이디 목록 취합 - 단위업무별 메뉴 권한 복수 삭제 대응
		List<String> authrtIdList = new ArrayList<>();
		if (mapParam.containsKey("AUTHRT_SE_CDS")) {
			List<String> authrtSeCds = (List<String>) mapParam.get("AUTHRT_SE_CDS");
			authrtIdList.addAll(authrtSeCds);
		} else {
			/* 권한구분코드 (AUTHRT_SE_CD) => 권한아이디로 맵핑 (AUTHRT_ID) */
			String authrtId = (String) mapParam.get("AUTHRT_SE_CD");
			authrtIdList = Arrays.asList(authrtId);
		}
		deleteParam.put("AUTHRT_IDS", authrtIdList);	// 권한아이디 (복수)
		
		// 삭제 제외할 메뉴 번호 목록 설정
		if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
			deleteParam.put("NOT_IN_MENU_NOS", mapParam.get("NOT_IN_MENU_NOS"));
		}
		
		// 삭제전 SAB250 (사용자별 메뉴권한) 목록 조회 
		List<Map<String, Object>> userMenuAuthrtList = mgmtUserAuthMapper.selectUserAuthList(deleteParam);
		
		// 사용자별 메뉴권한 삭제 처리
		int resultCnt = mgmtUserAuthMapper.deleteUserAuthByAuthrtIds(deleteParam);
		
		// 사용자별 메뉴권한 이력 삭제데이터 조회
		List<Map<String, Object>> chgHistoryInfos = mgmtAuthGrpMapper.selectUserMenuAuthHistoryInfos(deleteParam);
		
		if (chgHistoryInfos != null && chgHistoryInfos.size() > 0) {
			// 권한아이디에 따른 삭제 메뉴번호 목록 조회
			Map<String, Object> menuNoParam = new LinkedHashMap<>();
			menuNoParam.put("AUTHRT_IDS", authrtIdList);	// 권한아이디 (복수)
			List<Map<String, Object>> menuNoList = mgmtAuthGrpMapper.selectMenuNoListByAuthrtIds(menuNoParam);
			
			Iterator<Map<String, Object>> iter = chgHistoryInfos.iterator();
			while (iter.hasNext()) {
				Map<String, Object> map = (Map<String, Object>) iter.next();
				
				String menuNo = StringUtil.nullConvert(map.get("MENU_NO"));
				Long recDiffTm = (Long) map.get("REC_DIFF_TM");
				//String dattaChgSeCd = StringUtil.nullConvert(map.get("DATAA_CHG_SE_CD"));
				
				boolean isBreak = false;
				for (Map<String, Object> menuNoMap : menuNoList) {
					String tmpMenuNo = StringUtil.nullConvert(menuNoMap.get("MENU_NO"));
					// 변경된지 얼마 안된 데이터는 Insert 시 UNIQUE 에러 발생함으로 1초 정도의 지연시간을 줌.
					if (tmpMenuNo.equals(menuNo) && recDiffTm == 0) {
						LOGGER.info("### deleteMenuAuthMapping: MENU_NO={}", menuNo);
						//iter.remove();
						isBreak = true;
						break;
					}
				}
				
				if (isBreak) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					break;
				}
			}
			
			// 사용자별 메뉴권한 삭제 이력
			for (Map<String, Object> chgHistoryInfo : chgHistoryInfos) {
				// 사용자별 메뉴권한 이력 저장
				chgHistoryInfo.put("RGTR_ID", userDetailsVO.getId());	// 등록 (수정)자 아이디	
				chgHistoryInfo.put("DATAA_CHG_SE_CD", "D");				// 이력 삭제로 지정
				mgmtAuthGrpMapper.insertUserMenuAuthHistory(chgHistoryInfo);
			}
		} else {
			for (Map<String, Object> menuAuthrtMap : userMenuAuthrtList) {
				// 사용자별 메뉴권한 이력 저장
				menuAuthrtMap.put("RGTR_ID", userDetailsVO.getId());	// 등록 (수정)자 아이디	
				menuAuthrtMap.put("DATAA_CHG_SE_CD", "D");				// 이력 삭제로 지정
				mgmtAuthGrpMapper.insertUserMenuAuthHistory(menuAuthrtMap);
			}
		}
		
		// 삭제 결과 설정
		// 2023.03.24 (Myeong.Jae.Cheol) : 삭제갯수 전달
		mapResult.put("RESULT_CNT", Long.valueOf(resultCnt));
		if (resultCnt > 0) {
			mapResult.put("RESULT_OK", "Y");
			mapResult.put("RESULT_MSG", "메뉴 권한 삭제 처리 성공!");
		} else {
			mapResult.put("RESULT_OK", "N");
			mapResult.put("RESULT_MSG", "메뉴 권한 삭제 처리 실패!");
		}
		
		return mapResult;
	}
	
	/**
	 * @Method명   : selectMenuAuthTemplateList
	 * @param request
	 * @param mapParam	Map 형식의 Parameter
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 13. 
	 * @Method설명 : 메뉴 권한 템플릿 목록 조회
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<Map<String, Object>> selectMenuAuthTemplateList(HttpServletRequest request,
			Map<? super K, ? super V> mapParam) throws Exception {
		
		// 검색 Parameter Map 생성
		Map<String, Object> srchParamMap = new LinkedHashMap<>();
		
		// 권한아이디 목록 취합 - 단위업무별 메뉴별 권한 목록
		List<String> authrtIdList = new ArrayList<>();
		if (mapParam.containsKey("AUTHRT_SE_CDS")) {
			List<String> authrtSeCds = (List<String>) mapParam.get("AUTHRT_SE_CDS");
			authrtIdList.addAll(authrtSeCds);
		} else {
			/* 권한구분코드 (AUTHRT_SE_CD) => 권한아이디로 맵핑 (AUTHRT_ID) */
			String authrtId = (String) mapParam.get("AUTHRT_SE_CD");
			authrtIdList = Arrays.asList(authrtId);
		}
		srchParamMap.put("AUTHRT_IDS", authrtIdList);	// 권한아이디 (복수)
		
		List<Map<String, Object>> results = mgmtAuthGrpMapper.selectMenuNoListByAuthrtIds(srchParamMap);
		
		return results;
	}
}
