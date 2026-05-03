/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.web;

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

import isry.base.IsryBaseController;
import isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.service.CscAltmntService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
//import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명        : CscAltmntController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 7. 21. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 7. 21.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcms/syscmmn/schdlrsvtmng/cscaltmnt")
public class CscAltmntController extends IsryBaseController {
	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
//	@Resource(name = "comCodeService")
//	private ComCodeService comCodeService;
	
	
	@Resource(name = "cscAltmntService")
	private CscAltmntService cscAltmntService;
	
	
	// 암복호화 관련 모듈
	//private final ScpDb scpDb = new ScpDb();
		
	
	// 테스트용 로그인 사용자 아이디
	//private String testUserId = "SUBMS01";
	
	
	
	/**
	 * @Method명 : onLoadCscAltmnt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadCscAltmnt.do")
	public View onLoadCscAltmnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다. 
		 */
		
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);
			
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("INST_NO", loginVO.getInstNo());
			
		}
		
		
		
		Integer authMenuNo = 0;
		authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.
		
		
		Map<String, Object> mapDate = new HashMap<String, Object>();
		
		// 업무구분(해당업무 진입 메뉴 구분) > 변경될 수 있음. 정책 최종 확인 필요.
        // 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04)
		String taskwkSeCd = cscAltmntService.selectTaskwkSeCd(requestMap);
		
		// 현재 일자 조회
		mapDate.put("SYS_DATE", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));	
		mapDate.put("TASKWK_SYS_SE_CD", taskwkSeCd);
		mapDate.put("USER_ID", loginMap.get("USER_ID"));
		mapDate.put("INST_NO", loginMap.get("INST_NO"));
		
//		mapDate.put("USER_ID", "TESTE1");
//		mapDate.put("INST_NO", "371");
		
	
//		log.info("#### strSysDate : " + mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
//		log.info("#### taskwkSeCd : " + taskwkSeCd);
		
		
		// 상담실 데이터 호출
//		List<Map<String, Object>> cscSelectBoxList = cscAltmntService.selectCscList(mapDate);
		
		
		// 예약자 데이터 호출(사용자가 속한 기관에 모든 종사자 목록)
		List<Map<String, String>> rsvctmList = cscAltmntService.selectRsvctmList(mapDate);
		
		
		
		dataRequest.setResponse("dmInitInfo", mapDate);
//		dataRequest.setResponse("dsCscSelectBoxList", cscSelectBoxList);
		dataRequest.setResponse("dsRsvctmList", rsvctmList);
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : searchAltmntPrecon
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/searchAltmntPrecon.do")
	public View searchCscAltmntPrecon(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmInitInfo");
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", userInfoParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("SEARCH_DATE", searchParam.getValue("SEARCH_DATE"));
//		mapParam.put("SEARCH_DATE", "20220801");
		
		log.debug("searchCscAltmntPrecon INST_NO=[" + userInfoParam.getValue("INST_NO") + "]");
		log.debug("searchCscAltmntPrecon USER_ID=[" + userInfoParam.getValue("USER_ID") + "]");
		log.debug("searchCscAltmntPrecon SEARCH_DATE=[" + searchParam.getValue("SEARCH_DATE") + "]");
		log.debug("searchCscAltmntPrecon TASKWK_SYS_SE_CD=[" + userInfoParam.getValue("TASKWK_SYS_SE_CD") + "]");
		
		
		// 상담실 데이터 호출(select box 용)
		List<Map<String, Object>> cscSelectBoxList = cscAltmntService.selectCscListUseY(mapParam);
		
		// 상담실별 예약 현황 조회
		List<Map<String, Object>> cscAltmntRsvtList = cscAltmntService.selectCscAltmntRsvtList(mapParam);
		
		
		// 상담실배정현황
		List<Map<String, Object>> cscAltmntPreconList = cscAltmntService.selectCscAltmntPreconList(mapParam);
		
		
		
		dataRequest.setResponse("dsCscSelectBoxList", cscSelectBoxList);
		dataRequest.setResponse("dsCscAltmntRsvtList", cscAltmntRsvtList);
		dataRequest.setResponse("dsCscAltmntPreconList", cscAltmntPreconList);
		
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method명 : searchRsvtPrecon
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/searchRsvtPrecon.do")
	public View searchRsvtPrecon(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmInitInfo");
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", userInfoParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("SEARCH_DATE", searchParam.getValue("SEARCH_DATE"));
//		mapParam.put("SEARCH_DATE", "20220801");
		
		log.debug("searchRsvtPrecon INST_NO=[" + userInfoParam.getValue("INST_NO") + "]");
		log.debug("searchRsvtPrecon USER_ID=[" + userInfoParam.getValue("USER_ID") + "]");
		log.debug("searchRsvtPrecon SEARCH_DATE=[" + searchParam.getValue("SEARCH_DATE") + "]");
		log.debug("searchRsvtPrecon TASKWK_SYS_SE_CD=[" + userInfoParam.getValue("TASKWK_SYS_SE_CD") + "]");
		
		
		// 상담실 데이터 호출(select box 용)
		List<Map<String, Object>> cscSelectBoxList = cscAltmntService.selectCscListUseY(mapParam);
		
//		mapParam.put("CSC_ESNTAL_NO", cscSelectBoxList.get(0).get("CSC_ESNTAL_NO"));
//		
//		// 상담실별 예약 현황 조회
//		List<Map<String, Object>> cscAltmntRsvtList = cscAltmntService.selectCscAltmntRsvtList(mapParam);
//		
//		
//		// 상담실배정현황
//		List<Map<String, Object>> cscAltmntPreconList = cscAltmntService.selectCscAltmntPreconList(mapParam);
		
		
		
		dataRequest.setResponse("dsCscSelectBoxList", cscSelectBoxList);
//		dataRequest.setResponse("dsCscAltmntRsvtList", cscAltmntRsvtList);
//		dataRequest.setResponse("dsCscAltmntPreconList", cscAltmntPreconList);
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectedRsvtPreconSearch
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectedRsvtPreconSearch.do")
	public View selectedRsvtPreconSearch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup detailParam = dataRequest.getParameterGroup("dmDetail");
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("INST_NO", detailParam.getValue("INST_NO"));
		mapParam.put("USER_ID", detailParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", detailParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("CSC_ESNTAL_NO", detailParam.getValue("CSC_ESNTAL_NO"));
		mapParam.put("CSC_NM", detailParam.getValue("CSC_NM"));
		mapParam.put("SEARCH_DATE", searchParam.getValue("SEARCH_DATE"));
//		mapParam.put("SEARCH_DATE", "20220801");
		
		log.debug("selectedRsvtPrecon INST_NO=[" + detailParam.getValue("INST_NO") + "]");
		log.debug("selectedRsvtPrecon USER_ID=[" + detailParam.getValue("USER_ID") + "]");
		log.debug("selectedRsvtPrecon SEARCH_DATE=[" + searchParam.getValue("SEARCH_DATE") + "]");
		log.debug("selectedRsvtPrecon TASKWK_SYS_SE_CD=[" + detailParam.getValue("TASKWK_SYS_SE_CD") + "]");
		log.debug("selectedRsvtPrecon CSC_ESNTAL_NO=[" + detailParam.getValue("CSC_ESNTAL_NO") + "]");
		log.debug("selectedRsvtPrecon CSC_NM=[" + detailParam.getValue("CSC_NM") + "]");
		
		// 상담실 데이터 호출(select box 용)
//		List<Map<String, Object>> cscSelectBoxList = cscAltmntService.selectCscListUseY(mapParam);
		
//		mapParam.put("CSC_ESNTAL_NO", cscSelectBoxList.get(0).get("CSC_ESNTAL_NO"));
//		
		// 상담실별 예약 현황 조회
		List<Map<String, Object>> selectedCscAltmntRsvtList = cscAltmntService.selectedCscAltmntRsvtSearchList(mapParam);
																	   
		
		// 상담실배정현황
		List<Map<String, Object>> selectedCscAltmntPreconList = cscAltmntService.selectedCscAltmntPreconSearchList(mapParam);
		
		
		
//		dataRequest.setResponse("dsCscSelectBoxList", cscSelectBoxList);
		dataRequest.setResponse("dsCscAltmntRsvtList", selectedCscAltmntRsvtList);
		dataRequest.setResponse("dsCscAltmntPreconList", selectedCscAltmntPreconList);
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectDayRsvtPrecon
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectDayRsvtPrecon.do")
	public View selectDayRsvtPrecon(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmInitInfo");
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", userInfoParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("SEARCH_DATE", searchParam.getValue("SEARCH_DATE"));
//		mapParam.put("SEARCH_DATE", "20220801");
		
		log.debug("searchCscAltmntPrecon INST_NO=[" + userInfoParam.getValue("INST_NO") + "]");
		log.debug("searchCscAltmntPrecon USER_ID=[" + userInfoParam.getValue("USER_ID") + "]");
		log.debug("searchCscAltmntPrecon SEARCH_DATE=[" + searchParam.getValue("SEARCH_DATE") + "]");
		log.debug("searchCscAltmntPrecon TASKWK_SYS_SE_CD=[" + userInfoParam.getValue("TASKWK_SYS_SE_CD") + "]");
		
		
		
		// 상담실별 예약 현황 조회
		List<Map<String, Object>> cscAltmntRsvtList = cscAltmntService.selectCscAltmntRsvtList(mapParam);
		
		
		dataRequest.setResponse("dsCscAltmntRsvtList", cscAltmntRsvtList);
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method명 : cscAltmntTab4
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/cscAltmntTab4.do")
	public View cscAltmntTab4(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmInitInfo");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", userInfoParam.getValue("TASKWK_SYS_SE_CD"));
		
		log.debug("cscAltmntTab4 INST_NO=[" + userInfoParam.getValue("INST_NO") + "]");
		log.debug("cscAltmntTab4 USER_ID=[" + userInfoParam.getValue("USER_ID") + "]");
		log.debug("cscAltmntTab4 TASKWK_SYS_SE_CD=[" + userInfoParam.getValue("TASKWK_SYS_SE_CD") + "]");
		
		// 상담실 데이터 호출
		List<Map<String, Object>> cscSelectBoxList = cscAltmntService.selectCscListUseY(mapParam);
		
		dataRequest.setResponse("dsCscSelectBoxList", cscSelectBoxList);
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : cscList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 13.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/cscList.do")
	public View cscList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmUserInfo");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", userInfoParam.getValue("TASKWK_SYS_SE_CD"));
				
				
		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
//		log.info("#### strSearchKey              :" + searchParam.getValue("strSearchKey"));  
		
		String searchKey = null;
		searchKey = searchParam.getValue("strSearchKey");
		log.info("#### searchKey              : " + searchKey); 
		log.info("#### strSearchData              : " + searchParam.getValue("strSearchData")); 
		
		
		mapParam.put("SEARCH_KEY", searchKey);
		mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
		

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		// 조회된 전체 데이터 갯수를 가져옵니다.
		totalCount = cscAltmntService.getCscListTotalCount(mapParam);

		// 기본 데이터 호출
		List<Map<String, Object>> cscList = cscAltmntService.selectCscList(mapParam);
		
		

		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", cscList);
		dataRequest.setResponse("dmPage", resPage);
		
		return new JSONDataView();

	}
	
	
	
	
	
	/**
	 * @Method명 : onLoadCscReg
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/onLoadCscReg.do")
	public View onLoadCscReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		/*
		 * 최초 화면 구성시 필요한 정보를 조회합니다. 
		 */
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		// 사용여부 select box
		dataRequest.setResponse("dsUseYnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : saveCscDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/saveCscDetail.do")
	public View saveCscDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		cscAltmntService.saveCscDetail(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectCscDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectCscDetail.do")
	public View selectCscDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		// 사용여부 select box
		dataRequest.setResponse("dsUseYnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
				
		// 상세처리
		List<Map<String, Object>> selectCscDetail =  cscAltmntService.selectCscDetail(request, dataRequest);
		dataRequest.setResponse("dsList", selectCscDetail);
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method명 : checkRsvtHrDpcn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/checkRsvtHrDpcn.do")
	public View checkRsvtHrDpcn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		int totalCount = cscAltmntService.getCscAltmntRsvtListTotalCount(request, dataRequest);
		String resultDpcnYn = "";
		
//		List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>(); 
		
		if(totalCount == 0) {
			log.debug("예약리스트 == 0");
			// 01. 예약리스트 조회 / 예약리스트 count 가 0 이면 저장
			responseMap.put("DPCN_YN", "N");
			dataRequest.setResponse("dmDpcnYn", responseMap);
			
		}else if(totalCount > 0){
			
			// 02. 0 이상이면 등록 요청된 예약시간과 조회된 리스트에 예약시간 중복 체크
			log.debug("예약리스트 > 0");
//			resultDpcnYn = "N";
			resultDpcnYn = cscAltmntService.checkRsvtHrDpcn(request, dataRequest);
			responseMap.put("DPCN_YN", resultDpcnYn);
			dataRequest.setResponse("dmDpcnYn", responseMap);
		}
		
		return new JSONDataView();
	}
		
	
	
	/**
	 * @Method명 : saveCscAltmntDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/saveCscAltmntDetail.do")
	public View saveCscAltmntDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		cscAltmntService.saveCscAltmntDetail(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectCscAltmntDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectCscAltmntDetail.do")
	public View selectCscAltmntDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> selectedDetail = cscAltmntService.selectCscAltmntDetail(request, dataRequest);
		dataRequest.setResponse("dsFrom", selectedDetail);
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectDayOfWeek
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectWeekly.do")
	public View selectWeekly(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		
		// 선택 날짜 속한 주간 데이터 조회
		List<Map<String, Object>> weeklyList = cscAltmntService.selectDateWeeklyList(dataRequest);
		
		
		dataRequest.setResponse("dsCscAltmntRsvtList", weeklyList);
				
		
		return new JSONDataView();
	}
	
	
	
	
	

}
