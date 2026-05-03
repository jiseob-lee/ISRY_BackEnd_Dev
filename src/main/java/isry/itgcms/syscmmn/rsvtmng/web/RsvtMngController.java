/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.rsvtmng.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.syscmmn.rsvtmng.service.RsvtMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : RsvtMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 8. 29. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 8. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcms/syscmmn/rsvtmng")
public class RsvtMngController extends IsryBaseController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	// 단일 공통 코드 조회용
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	// 상하위분류가 있는 경우 공통 코드 조회용
//	@Resource(name = "comCodeService")
//	private ComCodeService comCodeService;
	
	
	// 암복호화
	//private ScpDb scpDb = new ScpDb();
	
	
	@Resource(name = "rsvtMngService")
	private RsvtMngService rsvtMngService;
	
	
	/**
	 * @Method명 : onLoadRsvtmng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29. 
	 * @Method설명 : 자원예약 초기값 로드
	 */
	@RequestMapping(value = "/onLoadRsvtmng.do")
	public View onLoadRsvtmng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String sUserId = "";
		String sInstNo = "";
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (userVo != null && userVo.getId() != null && !"".equals(userVo.getId())) {
			
			loginMap.put("USER_ID", userVo.getId());		// 사용자 아이디
			loginMap.put("INST_NO", userVo.getInstNo());	// 기관번호
			loginMap.put("ENFSN_NO", userVo.getEnfsnNo());	// 사용자 종사자번호
			
			String untTaskwkSeCd = userVo.getUntTaskwk().toString();
			if(!untTaskwkSeCd.equals("U15")) {
				loginMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
			}else {
				loginMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwkSeCd());
			}
			
			
		}
		
		
		// 메뉴 번호
		Integer authMenuNo = 0;
		authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("MENU_NO", authMenuNo); 
		
		
		// 단위업무구분 코드 UNT_TASKWK_SE_CD
        // EX) 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04) 외
		String taskwkSeCd = rsvtMngService.selectTaskwkSeCd(requestMap);


		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("USER_ID", loginMap.get("USER_ID"));
		responseMap.put("INST_NO", loginMap.get("INST_NO"));
		responseMap.put("ENFSN_NO", loginMap.get("ENFSN_NO"));
		//responseMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		responseMap.put("UNT_TASKWK_SE_CD", loginMap.get("UNT_TASKWK_SE_CD"));
		responseMap.put("SYS_DATE", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));	
		
		
		dataRequest.setResponse("dmInitInfo", responseMap);
		
		sUserId = userVo.getId().toString();
		sInstNo = userVo.getInstNo().toString();
		
		log.debug("sUserId :::::::::::" + sUserId);
		log.debug("sInstNo :::::::::::" + sInstNo);
		
//		// 자원분류관리대분류구분코드
		dataRequest.setResponse("dsResrceClMngLclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RESRCE_CL_MNG_LCLAS_SE_CD", userVo.getUntTaskwk()));
		
		// 시설 및 물품목록조회
//		List<Map<String, Object>> rsvtPreconList = rsvtMngService.selectFcltyThngList(responseMap);
//		dataRequest.setResponse("dsResrceClMngLclasSeCd", rsvtPreconList);
		// 이태호 매니저님 적용 사항 > dataRequest.setResponse("dsResrceClMngLclasSeCd", rsvtMngService.selectFcltyThngList("RESRCE_CL_MNG_LCLAS_SE_CD",sUserId,sInstNo));	
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectDailyRsvtPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29. 
	 * @Method설명 : 일별예약현황
	 */
	@RequestMapping(value = "/selectDailyRsvtPreconList.do")
	public View selectDailyRsvtPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("SEARCH_DATE", dmDtlParam.getValue("SEARCH_DATE"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dmDtlParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		
		log.debug("selectDailyRsvtPreconList INST_NO=[" + dmDtlParam.getValue("INST_NO") + "]");
		log.debug("selectDailyRsvtPreconList USER_ID=[" + dmDtlParam.getValue("USER_ID") + "]");
		log.debug("selectDailyRsvtPreconList UNT_TASKWK_SE_CD=[" + dmDtlParam.getValue("UNT_TASKWK_SE_CD") + "]");
		log.debug("selectDailyRsvtPreconList SEARCH_DATE=[" + dmDtlParam.getValue("SEARCH_DATE") + "]");
		log.debug("selectDailyRsvtPreconList RESRCE_CL_MNG_LCLAS_SE_CD=[" + dmDtlParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
		
		
		
		// 일별예약 현황 조회
		List<Map<String, Object>> rsvtPreconList = rsvtMngService.selectDailyRsvtPreconList(mapParam);
		
		
		dataRequest.setResponse("dsRsvtPreconList", rsvtPreconList);
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method명 : selectWeeklyRsvtPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29. 
	 * @Method설명 : 주별예약현황
	 */
	@RequestMapping(value = "/selectWeeklyRsvtPreconList.do")
	public View selectWeeklyRsvtPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("SEARCH_DATE", dmDtlParam.getValue("SEARCH_DATE"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dmDtlParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		
		log.debug("selectDailyRsvtPreconList INST_NO=[" + dmDtlParam.getValue("INST_NO") + "]");
		log.debug("selectDailyRsvtPreconList USER_ID=[" + dmDtlParam.getValue("USER_ID") + "]");
		log.debug("selectDailyRsvtPreconList UNT_TASKWK_SE_CD=[" + dmDtlParam.getValue("UNT_TASKWK_SE_CD") + "]");
		log.debug("selectDailyRsvtPreconList SEARCH_DATE=[" + dmDtlParam.getValue("SEARCH_DATE") + "]");
		log.debug("selectDailyRsvtPreconList RESRCE_CL_MNG_LCLAS_SE_CD=[" + dmDtlParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
		
		
		
		// 주별예약 현황 조회
		List<Map<String, Object>> rsvtPreconList = rsvtMngService.selectWeeklyRsvtPreconList(mapParam);
		
		
		dataRequest.setResponse("dsRsvtPreconList", rsvtPreconList);
		
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : selectedResrceRsvtPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectedResrceRsvtPreconList.do")
	public View selectedResrceRsvtPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dmDtlParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("RESRCE_NM", dmDtlParam.getValue("RESRCE_NM"));
		mapParam.put("SEARCH_DATE", dmDtlParam.getValue("SEARCH_DATE"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dmDtlParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		
		
		log.debug("selectedResrceRsvtPreconList INST_NO=[" + dmDtlParam.getValue("INST_NO") + "]");
		log.debug("selectedResrceRsvtPreconList USER_ID=[" + dmDtlParam.getValue("USER_ID") + "]");
		log.debug("selectedResrceRsvtPreconList SEARCH_DATE=[" + dmDtlParam.getValue("SEARCH_DATE") + "]");
		log.debug("selectedResrceRsvtPreconList UNT_TASKWK_SE_CD=[" + dmDtlParam.getValue("UNT_TASKWK_SE_CD") + "]");
		log.debug("selectedResrceRsvtPreconList RESRCE_CL_MNG_ESNTAL_NO=[" + dmDtlParam.getValue("RESRCE_CL_MNG_ESNTAL_NO") + "]");
		log.debug("selectedResrceRsvtPreconList RESRCE_NM=[" + dmDtlParam.getValue("RESRCE_NM") + "]");
		
		// 자원별 예약 현황 조회
		List<Map<String, Object>> selectRsvtPreconList = rsvtMngService.selectRsvtPreconList(mapParam);
		
		log.debug("selectRsvtPreconList :::::::::::: " + selectRsvtPreconList.toString());
		
		// 자원별 예약현황 배정 그리드 구성용
		List<Map<String, Object>> selectRsvtAltmntPreconList = rsvtMngService.selectRsvtAltmntPreconList(mapParam);
		
		log.debug("selectRsvtAltmntPreconList :::::::::::: " + selectRsvtAltmntPreconList.toString());
		
		dataRequest.setResponse("dsRsvtPreconList", selectRsvtPreconList);
		dataRequest.setResponse("dsRsvtAltmntPreconList", selectRsvtAltmntPreconList);
		
		
		return new JSONDataView();
	}


	
	/**
	 * @Method명 : onLoadResrceClMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29.
	 * @Method설명 : 자원분류관리 초기값 로드
	 */
	@RequestMapping(value = "/onLoadResrceClMng.do")
	public View onLoadResrceClMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		// 자원분류관리대분류구분코드
		dataRequest.setResponse("dsResrceClMngLclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RESRCE_CL_MNG_LCLAS_SE_CD", userVo.getUntTaskwk()));
			
		
		return new JSONDataView();
	}


	
	/**
	 * @Method명 : selectResrceClMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29.
	 * @Method설명 : 자원분류관리 목록
	 */
	@RequestMapping(value = "/selectResrceClMngList.do")
	public View resrceClMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmUserInfo");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("ENFSN_NO", userInfoParam.getValue("ENFSN_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", userInfoParam.getValue("UNT_TASKWK_SE_CD"));
				
				
		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", searchParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		mapParam.put("SEARCH_KEY", searchParam.getValue("SEARCH_KEY"));
		mapParam.put("SEARCH_DATA", searchParam.getValue("SEARCH_DATA"));
		

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
		totalCount = rsvtMngService.getResrceClMngListTotalCount(mapParam);

		// 기본 데이터 호출
		List<Map<String, Object>> resrceClMngList = rsvtMngService.selectResrceClMngList(mapParam);
		
		

		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", resrceClMngList);
		dataRequest.setResponse("dmPage", resPage);
		
		
		
		return new JSONDataView();

	}


	
	/**
	 * @Method명 : onLoadResrceClMngDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29.
	 * @Method설명 : 자원분류관리상세
	 */
	@RequestMapping(value = "/onLoadResrceClMngDtl.do")
	public View onLoadResrceClMngDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		// 자원분류관리대분류구분코드
		dataRequest.setResponse("dsResrceClMngLclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RESRCE_CL_MNG_LCLAS_SE_CD", userVo.getUntTaskwk()));
			
		
		// 사용여부 사용 select box
		dataRequest.setResponse("dsUseYnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USE_YN", userVo.getUntTaskwk()));
		
		
		// 참여가능여부 사용 select box
		dataRequest.setResponse("dsPtcptnPsbltySeCd", mgmtCmmnCodeService.selectCommonCodeUnit("PTCPTN_PSBLTY_YN", userVo.getUntTaskwk()));
		
		
		return new JSONDataView();
	}

	
	
	/**
	 * @Method명 : resrceNmDpcnChk
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 자원명 중복 체크(등록 or 수정)
	 */
	@RequestMapping(value = "/resrceNmDpcnChk.do")
	public View resrceNmDpcnChk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> resrceNmDpcnChkMap = new HashMap<String, Object>();
		//String resultDpcnYn = "";
		
		// 중복 체크용 자원분류관리 자원 리스트
//		List<Map<String, Object>> resrceNmDpcnChkList =  rsvtMngService.selectResrceNmDpcnChkList(request, dataRequest);
//				
//		if(resrceNmDpcnChkList.size() == 0) {
//			log.debug("등록 리스트 == 0");
//			// 01. 예약리스트 조회 / 예약리스트 count 가 0 이면 저장
//			responseMap.put("DPCN_YN", "N");
//			dataRequest.setResponse("dmDpcnYn", responseMap);
//			
//		}else if(resrceNmDpcnChkList.size() > 0){
//			
//			// 02. 0 이상이면 등록 요청된 예약시간과 조회된 리스트에 예약시간 중복 체크
//			log.debug("등록 리스트 > 0");
////			resultDpcnYn = "N";
//			resultDpcnYn = rsvtMngService.resrceNmDpcnChk(request, dataRequest);
//			
//			responseMap.put("DPCN_YN", resultDpcnYn);
//			dataRequest.setResponse("dmDpcnYn", responseMap);
//		}
		
		
		responseMap = rsvtMngService.resrceNmDpcnChk(request, dataRequest);
		
//		responseMap.put("DPCN_YN", resrceNmDpcnChkMap.get("DPCN_YN").toString());
//		responseMap.put("USE_YN", resrceNmDpcnChkMap.get("USE_YN").toString());
		
		//responseMap.put("DPCN_YN", resultDpcnYn);
		dataRequest.setResponse("dmDpcnYn", responseMap);
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : saveResrceClMngDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 자원분류관리(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/saveResrceClMngDtl.do")
	public View saveResrceClMngDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		rsvtMngService.saveResrceClMngDtl(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : selectResrceClMngDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectResrceClMngDtl.do")
	public View selectResrceClMngDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 상세처리
		List<Map<String, Object>> selectResrceClMngDtl =  rsvtMngService.selectResrceClMngDtl(request, dataRequest);
		dataRequest.setResponse("dsList", selectResrceClMngDtl);
				
		
		return new JSONDataView();
	}


	
	/**
	 * @Method명 : onLoadResrceRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29.
	 * @Method설명 : 자원예약상세
	 */
	@RequestMapping(value = "/onLoadResrceRsvtDtl.do")
	public View onLoadResrceRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginMap.put("USER_ID", loginVO.getId());		// 사용자 아이디
			loginMap.put("INST_NO", loginVO.getInstNo());	// 기관번호
			loginMap.put("ENFSN_NO", loginVO.getEnfsnNo());	// 사용자 종사자번호
			
			String untTaskwkSeCd = loginVO.getUntTaskwk().toString();
			if(!untTaskwkSeCd.equals("U15")) {
				loginMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
			}else {
				loginMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwkSeCd());
			}
			
			
		}
		
		
		// 메뉴 번호
		Integer authMenuNo = 0;
		authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("MENU_NO", authMenuNo); 
		
		
		// 단위업무구분 코드 UNT_TASKWK_SE_CD
        // EX) 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04) 외
		String taskwkSeCd = rsvtMngService.selectTaskwkSeCd(requestMap);


		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("USER_ID", loginMap.get("USER_ID"));
		responseMap.put("INST_NO", loginMap.get("INST_NO"));
		responseMap.put("ENFSN_NO", loginMap.get("ENFSN_NO"));
		responseMap.put("UNT_TASKWK_SE_CD", loginMap.get("UNT_TASKWK_SE_CD"));
		//responseMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		responseMap.put("SYS_DATE", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));	
		
		
		dataRequest.setResponse("dmInitInfo", responseMap);
		
		
		// 자원분류관리대분류구분코드
		dataRequest.setResponse("dsResrceClMngLclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RESRCE_CL_MNG_LCLAS_SE_CD", loginVO.getUntTaskwk()));
		
		
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectResrceClMngUseYList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 8. 29.
	 * @Method설명 : 자원예약상세 > 자원분류관리 자원
	 */
	@RequestMapping(value = "/selectResrceClMngUseYList.do")
	public View selectResrceClMngUseYList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

				
		// 자원분류관리 자원 리스트 -> 사용여부 Y
		List<Map<String, Object>> selectResrceClMngUseYList =  rsvtMngService.selectResrceClMngUseYlist(request, dataRequest);
		dataRequest.setResponse("dsResrceClMngList", selectResrceClMngUseYList);
		
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : resrceRsvtDpcnChk
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 자원예약 중복 체크(등록 or 수정)
	 */
	@RequestMapping(value = "/resrceRsvtDpcnChk.do")
	public View resrceRsvtDpcnChk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		String resultDpcnYn = "";
		
		
		resultDpcnYn = rsvtMngService.resrceRsvtDpcnChk(request, dataRequest);
		responseMap.put("DPCN_YN", resultDpcnYn);
		dataRequest.setResponse("dmDpcnYn", responseMap);
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : saveResrceRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 자원예약(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/saveResrceRsvtDtl.do")
	public View saveResrceRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		rsvtMngService.saveResrceRsvtDtl(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectResrceRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectResrceRsvtDtl.do")
	public View selectResrceRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 자원예약 상세처리
		List<Map<String, Object>> selectResrceRsvtDtl =  rsvtMngService.selectResrceRsvtDtl(request, dataRequest);
		dataRequest.setResponse("dsList", selectResrceRsvtDtl);
				
		
		return new JSONDataView();
	}
	
	
	
	
	
	
	/**
	 * @Method : onLoadSchdlRsvt
	 * @Method설명 : 일정예약 목록 OnLoad
	 * @param : request
	 * @param : response
	 * @return : dataRequest
	 * @exception : Exception
	 * @작성자 :
	 * @작성일 : ****************************** 공통코드 조회 조건 (dsCodeParam) 1.CMMNS_CD_ID
	 *      : 공통코드아이디 (필수) - ex) SRVC_RESRCE_LCLAS_SE_CD 2.DS_SET_NM : RETURN 데이터셋
	 *      (필수) - ex) dsSrvcResrceLclasSeCd 3.CMMNS_CD_VALUE : 공통코드값
	 *      4.CMMNS_CD_VALUE_NM : 공통코드값명 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 *      6.ADDTNG_MNG_VALUE2 : 추가관리값2 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 *      8.ADDTNG_MNG_VALUE4 : 추가관리값4 9.ADDTNG_MNG_VALUE5 : 추가관리값5 10.USE_YN :
	 *      사용여부
	 */
	@RequestMapping(value = "/onLoadSchdlRsvt.do")
	public View onLoadSchdlRsvt(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		String codeId = ""; // 공통코드 아이디
		String dataSetNm = ""; // 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		log.debug("onLoadSchdlRsvt.paraGroup=[" + paramGroup + "]");

		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			for (Map<String, String> rowMap : paramList) {

				dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); // 응답 데이터셋
				codeId = String.valueOf(rowMap.get("CMMNS_CD_ID")); // 요청 공통코드 아이디
				log.debug("onLoadSchdlRsvt.dataSetNm=[" + dataSetNm + "]");
				log.debug("onLoadSchdlRsvt.codeId=[" + codeId + "]");
				// 시스템 공통코드 조회 서비스 요청
				List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, userVo.getUntTaskwk());
				// 응답객체 셋팅
				dataRequest.setResponse(dataSetNm, list);
			}
		}

		

		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectSchdlRsvtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectSchdlRsvtList.do")
	public View selectSchdlRsvtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearchParam");
		
		List<Map<String, Object>> selectSchdlRsvtList = new ArrayList<Map<String,Object>>();
		
		if (paramGroup != null) {
			// 일정예약 목록
			selectSchdlRsvtList =  rsvtMngService.selectSchdlRsvtList(request, dataRequest);
			dataRequest.setResponse("dsList", selectSchdlRsvtList);
		} else {
			//일정예약 현황목록
			selectSchdlRsvtList =  rsvtMngService.selectDailyPopUpList(request, dataRequest);
			dataRequest.setResponse("dsNowList", selectSchdlRsvtList);
		}
		
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method : onLoadSchdlRsvtDtl
	 * @Method설명 : 일정예약 등록/상세 OnLoad
	 * @param : request
	 * @param : response
	 * @return : dataRequest
	 * @exception : Exception
	 * @작성자 :
	 * @작성일 : ****************************** 공통코드 조회 조건 (dsCodeParam) 1.CMMNS_CD_ID
	 *      : 공통코드아이디 (필수) - ex) SRVC_RESRCE_LCLAS_SE_CD 2.DS_SET_NM : RETURN 데이터셋
	 *      (필수) - ex) dsSrvcResrceLclasSeCd 3.CMMNS_CD_VALUE : 공통코드값
	 *      4.CMMNS_CD_VALUE_NM : 공통코드값명 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 *      6.ADDTNG_MNG_VALUE2 : 추가관리값2 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 *      8.ADDTNG_MNG_VALUE4 : 추가관리값4 9.ADDTNG_MNG_VALUE5 : 추가관리값5 10.USE_YN :
	 *      사용여부
	 */
	@RequestMapping(value = "/onLoadSchdlRsvtDtl.do")
	public View onLoadSchdlRsvtDtl(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String codeId = ""; // 공통코드 아이디
		String dataSetNm = ""; // 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");

		log.debug("onLoadSchdlRsvtDtl.paraGroup=[" + paramGroup + "]");

		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			for (Map<String, String> rowMap : paramList) {

				dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); // 응답 데이터셋
				codeId = String.valueOf(rowMap.get("CMMNS_CD_ID")); // 요청 공통코드 아이디
				log.debug("onLoadSchdlRsvtDtl.dataSetNm=[" + dataSetNm + "]");
				log.debug("onLoadSchdlRsvtDtl.codeId=[" + codeId + "]");
				// 시스템 공통코드 조회 서비스 요청
				List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, userVo.getUntTaskwk());
				// 응답객체 셋팅
				dataRequest.setResponse(dataSetNm, list);
			}
		}
		
		
		
		// 상담실(면접)실 = 자원분류관리 자원 리스트 -> 사용여부 Y, 일정예약연결사용여부 Y
		List<Map<String, Object>> selectTrprPtcptnPsbltyYlist =  rsvtMngService.selectTrprPtcptnPsbltyYlist(request, dataRequest);
		dataRequest.setResponse("dsTrprPtcptnPsbltyYlist", selectTrprPtcptnPsbltyYlist);

		

		return new JSONDataView();
	}
	
	
	
	
	
	/**
	 * @Method명 : saveSchdlRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 일정예약(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/saveSchdlRsvtDtl.do")
	public View saveSchdlRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		rsvtMngService.saveSchdlRsvtDtl(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : selectSchdlRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 일정예약(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/selectSchdlRsvtDtl.do")
	public View selectSchdlRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//rsvtMngService.selectSchdlRsvtDtl(request, dataRequest);
		// 01. 대상자 정보
		List<Map<String, Object>> selectSchdlRsvtTrprDtl =  rsvtMngService.selectSchdlRsvtTrprDtl(request, dataRequest);
		dataRequest.setResponse("dsTrprInfo", selectSchdlRsvtTrprDtl);
		
		// 02. 예약정보
		List<Map<String, Object>> selectSchdlRsvtDtl =  rsvtMngService.selectSchdlRsvtDtl(request, dataRequest);
		dataRequest.setResponse("dsList", selectSchdlRsvtDtl);
		
		
		// 03. 담당자정보
		List<Map<String, Object>> selectSchdlRsvtPicDtl =  rsvtMngService.selectSchdlRsvtPicDtl(request, dataRequest);
		dataRequest.setResponse("dsPicList", selectSchdlRsvtPicDtl);
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : cancleSchdlRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 일정예약(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/cancleSchdlRsvtDtl.do")
	public View cancleSchdlRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		rsvtMngService.cancleSchdlRsvtDtl(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
		
	
	/**
	 * @Method명 : getSysDt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 일정예약(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/getSysDt.do")
	public View getSysDt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapDate = new HashMap<String, Object>();
		mapDate.put("SYS_DT", mgmtCmmnCodeService.getSysDate("YYYYMMDD HH24MI"));
		dataRequest.setResponse("dmSysDt", mapDate);
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : selectDailyList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/selectDailyList.do")
	public View selectDailyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		// 일정예약 목록
		List<Map<String, Object>> selectDailyList =  rsvtMngService.selectDailyList(request, dataRequest);
		dataRequest.setResponse("dsList", selectDailyList);
				
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : selectDailySchdlRsvtDtl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 일정예약(등록, 수정, 삭제)
	 */
	@RequestMapping(value = "/selectDailySchdlRsvtDtl.do")
	public View selectDailySchdlRsvtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 02. 예약정보
		List<Map<String, Object>> selectSchdlRsvtDtl =  rsvtMngService.selectSchdlRsvtDtl(request, dataRequest);
		dataRequest.setResponse("dsForm", selectSchdlRsvtDtl);
		
		
		// 03. 담당자정보
		List<Map<String, Object>> selectSchdlRsvtPicDtl =  rsvtMngService.selectSchdlRsvtPicDtl(request, dataRequest);
		dataRequest.setResponse("dsPicList", selectSchdlRsvtPicDtl);
		
		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : selectedMonthsRsvtCnt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 12. 15. 
	 * @Method설명 :현재월에 예약건수 조회
	 */
	@RequestMapping(value = "/selectedMonthsRsvtCnt.do")
	public View selectedMonthsRsvtCnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 사용자 정보
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("SEARCH_MONTHS", dmDtlParam.getValue("SEARCH_MONTHS"));
		
		log.debug("selectedMonthsRsvtCnt INST_NO=[" + dmDtlParam.getValue("INST_NO") + "]");
		log.debug("selectedMonthsRsvtCnt USER_ID=[" + dmDtlParam.getValue("USER_ID") + "]");
		log.debug("selectedMonthsRsvtCnt SEARCH_DATE=[" + dmDtlParam.getValue("SEARCH_MONTHS") + "]");
		
		// 현재월 예약건수 조회
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> selectedMonthsRsvtCnt = rsvtMngService.selectedMonthsRsvtCnt(mapParam);
		
		
		for (Map<String, Object> rowMap : selectedMonthsRsvtCnt) {
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("data", rowMap.get("RSVT_DATE"));
			resultMap.put("label", rowMap.get("CNT").toString() + "건");
			resultMap.put("start", rowMap.get("RSVT_DATE").toString());
			resultMap.put("end", rowMap.get("RSVT_DATE").toString());
			
			resultList.add(resultMap);
		}
		
		
		dataRequest.setResponse("dsMonthsRsvtCnt", resultList);
		
		return new JSONDataView();
	}
	
	
}
