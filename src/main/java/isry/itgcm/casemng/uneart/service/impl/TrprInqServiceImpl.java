/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.aimns.cmmn.mapper.AimnsMapper;
import isry.itgcm.bizcmmns.cmmns.mapper.IdntfcTrprMapper;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.uneart.mapper.TrprInqMapper;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.NumberCheckUtil;
import isry.itgcms.util.UserException;

/**
 * @Class Name : TrprInqServicImpl.java
 * @Description : 대상자정보 ServiceImpl Class
 *
 * @author : Seo.Hae.Seok
 * @since : 2022. 05. 18.
 * @version : 1.0
 * @see
 * 
 *      <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 18.  Seo.Hae.Seok    최초작성
 *      </pre>
 */
@Service("trprInqService")
public class TrprInqServiceImpl extends EgovAbstractServiceImpl implements TrprInqService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "trprInqMapper")
	private TrprInqMapper trprInqMapper;

	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name = "idntfcTrprMapper")
	private IdntfcTrprMapper idntfcTrprMapper;
	
	@Resource(name = "aimnsMapper")
	private AimnsMapper aimnsMapper;

	@Resource(name = "personalInfoService")
	private PersonalInfoService personalInfoService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명 : selectRenuNo
	 * @param sessionUserId(세션정보), RenuNoSeCd(채번코드)
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 1.
	 * @Method설명 : 식별번호 채번
	 */
	private String selectRenuNo(String sessionUserId, String RenuNoSeCd) throws Exception {

		String sIdntfcNo = "";
		// 식별번호 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();

		seqMap.put("USER_ID", sessionUserId);
		seqMap.put("RENU_NO_SE_CD", RenuNoSeCd); // 채번코드
		seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자

		// 채번서비스 호출
		valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);

		sIdntfcNo = String.valueOf(valMap.get("RENU_NO")); // 식별번호 채번

		return sIdntfcNo;
	}
	
	/**
	 * @Method명   : setCaseTrprType
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 11. 
	 * @Method설명 : 사례대상자유형구분 코드 셋팅
	 */
	private String setCaseTrprType(HttpServletRequest request) throws Exception {
		
		/* 화면에서 사례대상자유형구분코드가 null로 넘어올수 있는경우 화면아이디로 해당 사례대상자유형구분 셋팅*/
		String retCode  = "";
		
//		Integer menuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO")) 
//				? 0 : Integer.valueOf(request.getParameter("_AUTH_MENU_NO"));
		String menuUrl = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		
		if(! "".equals(menuUrl)) {
			menuUrl = menuUrl.replace(".clx", "");
			menuUrl = menuUrl.substring(menuUrl.lastIndexOf("/"), menuUrl.length());
		}
		
		/* 직접발굴대상자*/
		if(menuUrl.endsWith("trprInqInsert") || menuUrl.endsWith("linkTrprRqst")) {
			/* 발굴대상자등록, 연계의뢰(내부)*/
			retCode = "01";
		}else if(menuUrl.endsWith("학교진단대상자")) {
			retCode = "02";
		}else if(menuUrl.endsWith("병무청연계대상자")) {		
			retCode = "03";
		}else if(menuUrl.endsWith("보건복지부연계대상자")) {
			retCode = "04";
		}else if(menuUrl.endsWith("경찰청연계대상자")) {
			retCode = "05";
		}else if(menuUrl.endsWith("교육부연계대상자")) {
			retCode = "06";
		}else if(menuUrl.endsWith("학교진단대상자")) {
			retCode = "07";
		}else if(menuUrl.endsWith("유관기관대상자")) {
			retCode = "08";
		}else if(menuUrl.endsWith("센터진단대상자")) {			
			retCode = "09";
		/* 발굴상담대상자*/	
		}else if(menuUrl.endsWith("dscsnUneartInsert")) {
			retCode = "10";
		/* 아웃리치대상자*/
		}else if(menuUrl.endsWith("dscsnOutrcInsert")) {
			retCode = "11";
		/* 긴급구조대상자*/
		}else if(menuUrl.endsWith("emrgIntrvnInsert")) {
			retCode = "12";
		/* 사례등록대상자*/
		}else if(menuUrl.endsWith("caseRegInsert")) {			
			retCode = "13";
		}else if(menuUrl.endsWith("디딤센터신청대상자")) {	/* 신청자페이지*/
			retCode = "14";
		}else if(menuUrl.endsWith("전화상담대상자")) {			
			retCode = "15";
		}else if(menuUrl.endsWith("드림마을신청대상자")) {	/* 신청자페이지*/	
			retCode = "16";
		}else {
			retCode = "01";			
		}
		
		return retCode;
	}
	
	/**
	 * @Method명   : getTrprCaseDpcnInq
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 : 대상자 사례진행여부 확인( 사례관리구분코드가 미선정이나 사례대상자신청(대기상태)로 들어오면 저장전 확인)
	 */
	@Override
	public Map<String, Object> selectTrprCaseDpcnInq(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDetail");

		if ("".equals(paramGroup.getValue("TRPR_INFO_NO")) || "null".equals(paramGroup.getValue("TRPR_INFO_NO")) || paramGroup.getValue("TRPR_INFO_NO") == null) {
			throw new AppWorksException("조회할 개인정보가 없습니다.");
		}
		if ("".equals(paramGroup.getValue("UNT_TASKWK_SE_CD")) || "null".equals(paramGroup.getValue("UNT_TASKWK_SE_CD")) || paramGroup.getValue("UNT_TASKWK_SE_CD") == null) {
			throw new AppWorksException("조회할 개인정보가 없습니다.");
		}

		Map<String, String> paramMap = new HashMap<>();
		paramMap = paramGroup.getSingleValueMap();		
		
		return trprInqMapper.selectTrprCaseDpcnInq(paramMap);
	}

	/**
	 * @Method명   : selectTrprRegCnt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 대상자등록 확인(발굴상담, 아웃리치, 긴급구조, 복지부연계, 1338상담)
	 */
	@Override
	public Map<String, Object> selectTrprRegCnt(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");		
		
		Map<String, String> paramMap      = paramGroup.getSingleValueMap();
		
		Map<String, Object> retMap = new HashMap<>();		
		
		int iDscsnCnt      = trprInqMapper.selectUneartDscsnTrpr(paramMap);
		if(iDscsnCnt > 0 ) {
			retMap.put("DSCSN_CNT", iDscsnCnt);
			retMap.put("DSCSN_NM" , "발굴관리-발굴상담");
		}
		
		int iOUTRC_Cnt     = trprInqMapper.selectOutrcTrpr(paramMap);
		if(iOUTRC_Cnt > 0 ) {
			retMap.put("OUTRC_CNT", iOUTRC_Cnt);
			retMap.put("OUTRC_NM" , "발굴관리-아웃리치");
		}
		
		int iEmrgIntrvnCnt = trprInqMapper.selectEmrgIntrvnTrpr(paramMap);
		if(iEmrgIntrvnCnt > 0 ) {
			retMap.put("EMRG_INTRVN_CNT", iEmrgIntrvnCnt);
			retMap.put("EMRG_INTRVN_NM", "발굴관리-긴급구조");
		}
		
		int iLinkMohwCnt   = trprInqMapper.selectLinkMohwSrvcRqstTrpr(paramMap);
		if(iLinkMohwCnt > 0 ) {
			retMap.put("LINK_MOHW_CNT", iLinkMohwCnt);
			retMap.put("LINK_MOHW_NM", "연계접수(복지부연계)");
		}
		
		int iLinkTrprCnt   = trprInqMapper.selectLinkTrprRqst(paramMap);
		if(iLinkTrprCnt > 0 ) {
			retMap.put("LINK_TRPR_CNT", iLinkTrprCnt);
			retMap.put("LINK_TRPR_NM", "내부연계접수");
		}
		
		int i1388TlphonCnt   = trprInqMapper.select1388TlphonDscsn(paramMap);
		if(i1388TlphonCnt > 0 ) {
			retMap.put("TL_PHON_CNT", i1388TlphonCnt);
			retMap.put("TL_PHON_NM", "1388전화상담");
		}
		int iCaseRegCnt   = trprInqMapper.selectCaseRegTrpr(paramMap);
		if(iCaseRegCnt > 0 ) {
			retMap.put("CASE_REG_CNT", iCaseRegCnt);
			retMap.put("CASE_REG_NM", "사례등록");
			
			if (iCaseRegCnt > 0) {
				throw new AppWorksException("삭제요청한 대상자는 사례등록 대상자로 삭제가 불가합니다.");
			}			
		}
		
		return retMap;
	}	

	/**
	 * @Method : selectTrprInqList
	 * @Method설명 : 대상자 목록조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Seo.Hae.Seok
	 * @작성일 : 2022. 05. 18.
	 */
	@Override
	public Map<String, Object> selectTrprInqList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 대상자 목록자료가 없습니다.", Alert.ERROR);
		}
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");		
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String,Object> retMap = new HashMap<>();
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		
		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/

		/* 페이징 건수 조회*/
		int trprCnt = trprInqMapper.selectTrprInqListCount(paramMap2);
		paramMap2.put("TOT_CNT", trprCnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = trprCnt;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(trprCnt > 0) {
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			// 대상자목록 returnList
			retList = trprInqMapper.selectTrprInqList(paramMap2);
		}
		
		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);
		
		/* 리턴 map 정보*/
		retMap.put("dsList", retList);
		retMap.put("dmPage", pageMap);

		return retMap;
	}

	/**
	 * @Method : selectTrprInqDetail
	 * @Method설명 : 대상자 상세조회
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Seo.Hae.Seok
	 * @작성일 : 2022. 05. 18.
	 */
	@Override
	public Map<String, Object> selectTrprInqDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 대상자 상세자료가 없습니다.", Alert.ERROR);
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		String sTrprInfoNo = "";
		sTrprInfoNo = paramGroup.getValue("TRPR_INFO_NO"); // 대상자정보번호
		if (sTrprInfoNo == null || sTrprInfoNo.equals("null") || sTrprInfoNo.equals("")) {
			throw new AppWorksException("대상자정보번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		// 대상자 상세조회
		Map<String, Object> dmDetail = new HashMap<String, Object>();
		dmDetail = trprInqMapper.selectTrprInqDetail(paramMap);

		// 전화번호 null 처리 ( 화면에서 format 웹 console 오류발생 처리 )
		String sTelNo = (String) dmDetail.get("TRPR_TELNO");
		Optional<String> optTelNo = Optional.ofNullable(sTelNo);
		dmDetail.put("TRPR_TELNO", optTelNo.orElse(""));

		/* 개인식별 확인조회*/
		String chkRrno = String.valueOf(dmDetail.get("RRNO"));
		String chkIndx = String.valueOf(dmDetail.get("INDV_IDNTFC_NO"));
		if("".equals(chkIndx) || "null".equals(chkIndx)) {
			
			LOGGER.debug("===========.chkIndx[" + chkIndx + "]===========");
			Map<String,String> map = new HashMap<>();
			
			map.put("TRPR_INFO_NO" , String.valueOf(dmDetail.get("TRPR_INFO_NO")));
			map.put("TRPR_NM_ENCPT", String.valueOf(dmDetail.get("TRPR_NM_ENCPT")));
			map.put("SXDC_SE_CD"   , String.valueOf(dmDetail.get("SXDC_SE_CD")));
			map.put("TRPR_BRTH_YMD", String.valueOf(dmDetail.get("TRPR_BRTH_YMD")));
			
			int iChkCnt = idntfcTrprMapper.getIdntfcTrprList(map);
			
			if(iChkCnt > 0) {
				/* 동일한 개인정보 */
				dmDetail.put("CHK_CNT", iChkCnt);				
			}
		}
		
		// 2022-10-12 이충수매니저 추가 요청, 이주배경 고유컬럼 추가( 심사대상자, AFA400 )
		String sUntTaskwkSeCd = String.valueOf(dmDetail.get("UNT_TASKWK_SE_CD"));
		if (sUntTaskwkSeCd.equals("U10")) {
			Map<String, Object> getMap = new HashMap<>();
			getMap = aimnsMapper.SelectSrngTrprDetail(paramMap);

			if (getMap != null) {
				dmDetail.put("PAPERS_SRNG_PASS_SE_CD", (getMap.get("PAPERS_SRNG_PASS_SE_CD")) == null ? ""
						: getMap.get("PAPERS_SRNG_PASS_SE_CD")); /* 서류심사합격구분코드 */
				dmDetail.put("PREPAR_SCHL_PTCPTN_SE_CD", (getMap.get("PREPAR_SCHL_PTCPTN_SE_CD")) == null ? ""
						: getMap.get("PREPAR_SCHL_PTCPTN_SE_CD")); /* 예비학교참여구분코드 */
				dmDetail.put("PSNCPA_ELSE_REG_SE_CD", (getMap.get("PSNCPA_ELSE_REG_SE_CD")) == null ? ""
						: getMap.get("PSNCPA_ELSE_REG_SE_CD")); /* 정원이외등록구분코드 */
			}
		}
		retMap.put("dmDetail", dmDetail);

		return retMap;
	}
	
	/**
	 * @Method명   : selectPersonalInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 1. 
	 * @Method설명 : 발굴대상자 개인정보조회
	 */
	@Override
	public List<Map<String, Object>> selectPersonalInfo(HttpServletRequest request, DataRequest dataRequest)throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		if (paramMap == null || paramMap.equals("null") || paramMap.equals("")) {
			throw new AppWorksException("조회할 발굴대상자의 개인식별번호가 없습니다.", Alert.ERROR);
		}

		return trprInqMapper.selectPersonalInfo(paramMap);
	}
	
	/**
	 * @Method명   : selectPersonalInfoHistory
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 17. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectPersonalInfoHistory(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		if (paramMap == null || paramMap.equals("null") || paramMap.equals("")) {
			throw new AppWorksException("조회할 발굴대상자의 개인식별번호가 없습니다.", Alert.ERROR);
		}

		return trprInqMapper.selectPersonalInfoHistory(paramMap);
	}	
	
	/**
	 * @Method : processTrprInqDetail
	 * @Method설명 : 대상자 상세저장(등록,수정,삭제,이력)
	 * @param : request
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Seo.Hae.Seok
	 * @작성일 : 2022. 05. 18.
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> processTrprInqDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();

		int itotCnt = 0; // 전체건수
		int iIntCnt = 0; // 등록건수
		int iUpdCnt = 0; // 수정건수
		int iDelCnt = 0; // 삭제건수
		int iHisCnt = 0; // 이력등록건수
		
		String sUserId = ""; // 세션정보의 유저ID
		String sWprkSqn = ""; // 채번번호
		String sIndvIdntfcNo = ""; // 개인식별번호 채번번호
		String sStatus = "s"; // 상태코드(s:조회, i:신규, u:변경, d:삭제)
		String sCaseTrprType = "";

		// 세션정보 가져오기
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		sUserId = loginVO.getId();

		/* ---------------- */
		/* 대상자 상세 처리 */
		/* ---------------- */
		// 대상자상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDetail");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 대상자 상세자료가 없습니다.", Alert.ERROR);
		}

		// 상태코드(s:조회, i:신규, u:변경, d:삭제) 설정
		sStatus = paramGroup.getValue("TYPE");
		if (sStatus == null || sStatus.equals("null") || sStatus.equals("")) {
			sStatus = "s";
		}
		
		sCaseTrprType = paramGroup.getValue("CASE_TRPR_TYPE_SE_CD");
		if (sCaseTrprType == null || sCaseTrprType.equals("null") || sCaseTrprType.equals("")) {
			sCaseTrprType = setCaseTrprType(request);
			
			paramGroup.setValue(0, "CASE_TRPR_TYPE_SE_CD", sCaseTrprType);
		}
		
		Map<String, String> saveMap = paramGroup.getSingleValueMap();
		
		/* 단위업무구분코드 */
		String sUntTaskwkSeCd = String.valueOf(saveMap.get("UNT_TASKWK_SE_CD"));
		String sUntTask       = String.valueOf(loginVO.getUntTaskwk());
		/* 2023-06-02 단위업무구분 U10 내일이룸학교에서만 이력/성과 선택을 하여 단위업무조건 추가*/
		if("U10".equals(sUntTaskwkSeCd) || "U10".equals(sUntTask)) {
			LOGGER.debug("내일이룸체크=[");
			/*
			 * CASE_MNG_SE_CD 사례관리구분코드 2022-09-05 대상자정보 등록화면 변경사항
			 * 
			 * 성과 또는 이력 선택 (성과구분코드) - 디폴트 ( 1 : 이력 ) - 성과 선택시에는 취득일자가 사례시작일자 이후로 ( 학력상태,
			 * 학업중단, 취업정보, 자격정보 ) 성과로 등록
			 * 
			 * 취업정보 (입사일자), 자격정보(취득일자)만 체크
			 * 
			 */
			// (사례관리구분코드 || 01 : 사례대상자미신청 , 02 : 사례대상자신청(대기상태), 03 : 사례대상자미선정, 04 : 사례대상자선정
			// )
			
			String sCaseMngSeCd = saveMap.get("CASE_MNG_SE_CD"); // 사례관리구분코드
			if ("04".equals(sCaseMngSeCd) || sCaseMngSeCd == "04") {
				LOGGER.debug("사례관리구분코드=[" + sCaseMngSeCd + "]");
				
				// dsAcbgStts : 학력상태, dsEmpymnInfo : 취업정보, dsQlfcInfo :자격정보
				
				ParameterGroup saveAcbgSttsList = dataRequest.getParameterGroup("dsAcbgStts");
				ParameterGroup saveEmpymnInfoList = dataRequest.getParameterGroup("dsEmpymnInfo");
				ParameterGroup saveInsertQlfcInfoList = dataRequest.getParameterGroup("dsQlfcInfo");
				
				List<Map<String, String>> acbgSttsList = saveAcbgSttsList.getAllRowList();
				List<Map<String, String>> empymnInfoList = saveEmpymnInfoList.getAllRowList();
				List<Map<String, String>> qlfcInfoList = saveInsertQlfcInfoList.getAllRowList();
				
				Map<String, String> getMap = new HashMap<>();
				
				getMap = trprInqMapper.selectTrprCaseBgngYmd(saveMap);
				// 학력상태
				int iCaseBgngYmd = 0;
				if (getMap != null) {
					iCaseBgngYmd = Integer.parseInt(getMap.get("CASE_BGNG_YMD")); /* 사례시작일자 */
				}
				
				if (acbgSttsList.size() > 0) {
					
					for (int idx = 0; idx < acbgSttsList.size(); idx++) {
						int iOutcSeCd = Integer.parseInt(acbgSttsList.get(idx).get("OUTC_SE_CD")); /* 성과구분코드 */
						
						if (iOutcSeCd == 2) { // 성과구분코드 OUTC_SE_CD :: 이력 1, 성과 2
							
							String sDgriAcqsMthdSeCd = String.valueOf(acbgSttsList.get(idx).get("DGRI_ACQS_MTHD_SE_CD"));							
							
							LOGGER.debug("==========학위취득방법구분코드[" + sDgriAcqsMthdSeCd + "]==========");
							if(! "2".equals(sDgriAcqsMthdSeCd)) {	/* 2 : 검정고시*/
								if(acbgSttsList.get(idx).get("MTCLTN_YMD").isEmpty()) {
									throw new AppWorksException("성과등록시 입학일자를 입력바랍니다.");								
								}
								int iMtcltnYmd = Integer.parseInt(acbgSttsList.get(idx).get("MTCLTN_YMD")); /* 입학일자 */
								if (iMtcltnYmd < iCaseBgngYmd) {
									throw new AppWorksException("성과등록한 학력상태의 입학일자가\n사례시작 이전으로 성과등록을 할 수 없습니다.");
								}
							}
						}
					}
				}
				// 취업정보
				if (empymnInfoList.size() > 0) {
					for (int idx = 0; idx < empymnInfoList.size(); idx++) {
						int iOutcSeCd = Integer.parseInt(empymnInfoList.get(idx).get("OUTC_SE_CD")); /* 성과구분코드 */
						
						if (iOutcSeCd == 2) { // 성과구분코드 OUTC_SE_CD :: 이력 1, 성과 2
							
							String sLaborSttsSeCd = String.valueOf(empymnInfoList.get(idx).get("LABOR_STTS_SE_CD"));
							
							LOGGER.debug("==========근로상태구분코드[" + sLaborSttsSeCd + "]==========");
							if("01".equals(sLaborSttsSeCd)) {	/* 01 :취업, 02 : 취업준비, 03 : 미취업*/
								
								if(empymnInfoList.get(idx).get("JNCMP_YMD").isEmpty()) {
									throw new AppWorksException("성과등록시 취업일자를 입력바랍니다.");
								}
								int iJncmpYmd = Integer.parseInt(empymnInfoList.get(idx).get("JNCMP_YMD")); /* 입사일자 */
								if (iJncmpYmd < iCaseBgngYmd) {
									throw new AppWorksException("성과등록한 취업정보의 취업일자\n사례시작 이전으로 성과등록을 할 수 없습니다.");
								}
							}
						}
					}
				}
				// 자격정보
				if (qlfcInfoList.size() > 0) {
					for (int idx = 0; idx < qlfcInfoList.size(); idx++) {
						int iOutcSeCd = Integer.parseInt(qlfcInfoList.get(idx).get("OUTC_SE_CD")); /* 성과구분코드 */
						
						if (iOutcSeCd == 2) { // 성과구분코드 OUTC_SE_CD :: 이력 1, 성과 2
							if(qlfcInfoList.get(idx).get("ACQS_YMD").isEmpty()) {
								throw new AppWorksException("성과등록시 취득일자를 입력바랍니다.");
							}
							int iAcqsYmd = Integer.parseInt(qlfcInfoList.get(idx).get("ACQS_YMD")); /* 취득일자 */
							if (iAcqsYmd < iCaseBgngYmd) {
								throw new AppWorksException("성과등록한 자격정보의 취득일자가\n사례시작 이전으로 성과등록을 할 수 없습니다.");
							}
						}
					}
				}
			}
		}

		// 필수항목 및 처리항목 체크
		saveMap.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
		saveMap.put("DATAA_CHG_SE_CD", sStatus); // 데이터변경구분코드 셋팅

		// 개인정보 테이블 insert, update, delete Map<String, Object>
		Map<String, Object> infoMap = new HashMap<>();
		
		// 등록처리
		if (sStatus.equals("i") || sStatus.equals("I")) {
			
			/* 일감 #개발505 개인식별로직*/
//			int iChkCnt = idntfcTrprMapper.getIdntfcTrprList(saveMap);
//			retMap.put("CHK_CNT", iChkCnt);			
			
			/* 2023-05-25 주민등록번호 있는경우만 개인식별번호를 채번
			 * 1. 대상자정보 저장
			 * 2. 저장이후 대상자명 + 생년월일 + 성별로 같은 대상자가 있는지 확인
			 * 3. 2건 이상 있을 경우 저장 이후 "대상자명, 생년월일, 성별이 같은 대상자가 존재합니다." 메세지출력"
			 * 4. 출력 이후 개인식별 팝업을 띄우고 해당 데이터를 검색 파라미터에 넣은 후 같은 건을 목록에 표시
			 * 주민번호는 수정불가(주민번호 입력시 저장전에 주민번호는 이후 수정이 불가능 합니다. 계속 진행하시겠습니까? confirm창 열고 저장전에 확인 하도록함
			 */
			
			// 주민등록번호
			String sRrno = String.valueOf(saveMap.get("RRNO"));
			if (! "".equals(sRrno) && ! "null".equals(sRrno) && sRrno.length() == 13) {
				
				infoMap.put("TRPR_NM"         , saveMap.get("TRPR_NM"));          // 성명
				infoMap.put("RRNO"            , saveMap.get("RRNO"));             // 주민등록번호
				infoMap.put("TRPR_BRTH_YMD"   , saveMap.get("TRPR_BRTH_YMD"));    // 출생일자
				infoMap.put("SXDC_SE_CD"      , saveMap.get("SXDC_SE_CD"));       // 성별구분코드
				infoMap.put("MRG_YN"          , saveMap.get("MRG_YN"));           // 결혼여부
				infoMap.put("LAST_ACBG_SE_CD" , saveMap.get("ACBG_SE_CD"));       // 최종학력구분코드
				infoMap.put("WRD_TELNO"		  , saveMap.get("TRPR_TELNO"));       // 유선전화번호
				infoMap.put("MBL_TELNO"       , saveMap.get("MBL_TELNO"));        // 휴대전화번호
				infoMap.put("EML_ADDR"        , saveMap.get("EML_ADDR"));         // 이메일주소
				infoMap.put("SNS_SE_CD"       , saveMap.get("SNS_SE_CD"));        // SNS구분코드
				infoMap.put("MSNGR_ID"        , saveMap.get("MSNGR_ID"));         // 메신저아이디 
				infoMap.put("ZIP"             , saveMap.get("ZIP"));              // 우편번호
				infoMap.put("PST_ADDR"        , saveMap.get("PST_ADDR"));         // 우편주소
				infoMap.put("DADDR"           , saveMap.get("DADDR"));            // 상세주소
				infoMap.put("RGN_CD"          , saveMap.get("RGN_CD"));           // 지역코드
				infoMap.put("ENFSN_NO"        , saveMap.get("RCPT_PIC_NO"));      // 접수담당자번호
				infoMap.put("INST_NO"         , saveMap.get("RCPT_INST_NO"));     // 접수기관번호
				
				/* 개인식별번호 get or 채번*/
				sIndvIdntfcNo = setPersonal(request, infoMap);
			} 			

			// 개인식별번호
			if (!sIndvIdntfcNo.isEmpty()) {
				saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
				request.setAttribute("INDV_IDNTFC_NO", sIndvIdntfcNo);
			} else if (sIndvIdntfcNo.isEmpty()) {
				request.setAttribute("INDV_IDNTFC_NO", "");
			}
			sWprkSqn = selectRenuNo(sUserId, "TR"); // 대상자번호(TR) 발번
			// 대상자번호 채번
			saveMap.put("TRPR_INFO_NO", sWprkSqn);

			// 대상자상세 등록 호출
			iIntCnt = trprInqMapper.insertTrprInqDetail(saveMap);
			if (iIntCnt > 0) {
				// 대상자이력 등록 호출
				iHisCnt = trprInqMapper.insertTrprInqHistory(saveMap);
			}

			// 2022-10-12 이충수매니저 요청사항 내일이룸(U10) 고유컬럼 저장
			if (sUntTaskwkSeCd.equals("U10")) {
				saveMap.put("FRST_RGTR_ID", sUserId);
				saveMap.put("LAST_MDFR_ID", sUserId);
				aimnsMapper.insertSrngTrpr(saveMap);
			}

		// 수정처리
		} else if (sStatus.equals("u") || sStatus.equals("U")) {

			// 개인식별번호
			sIndvIdntfcNo = saveMap.get("INDV_IDNTFC_NO");
//			if (!sIndvIdntfcNo.isEmpty()) {
//				request.setAttribute("INDV_IDNTFC_NO", sIndvIdntfcNo);
//			} else if (sIndvIdntfcNo.isEmpty()) {
//				request.setAttribute("INDV_IDNTFC_NO", "");
//			}
			
			String sModChk = "N"; // 항목변경여부
			sModChk = trprInqMapper.selectTrprInqDetailModChk(saveMap);
			
			// 항목이 변경된건이 있을경우
			if (sModChk.equals("Y") || sModChk.equals("y")) {
				// 대상자상세 수정 호출
				iUpdCnt = trprInqMapper.updateTrprInqDetail(saveMap);
				if (iUpdCnt > 0) {
					// 대상자이력 등록 호출
//					iHisCnt = trprInqMapper.insertTrprInqHistory(saveMap);
				}
			}
			
			infoMap.put("TRPR_NM"         , saveMap.get("TRPR_NM"));          // 성명					
			infoMap.put("TRPR_BRTH_YMD"   , saveMap.get("TRPR_BRTH_YMD"));    // 출생일자					
			infoMap.put("SXDC_SE_CD"      , saveMap.get("SXDC_SE_CD"));       // 성별구분코드				
			infoMap.put("INDV_IDNTFC_NO"  , saveMap.get("INDV_IDNTFC_NO"));  // 개인식별번호
			infoMap.put("RRNO"            , saveMap.get("RRNO"));            // 주민등록번호
			infoMap.put("BRTH_YMD"        , saveMap.get("TRPR_BRTH_YMD"));    // 출생일자
			infoMap.put("SXDC_SE_CD"      , saveMap.get("SXDC_SE_CD"));       // 성별구분코드
			infoMap.put("MRG_YN"          , saveMap.get("MRG_YN"));           // 결혼여부
			infoMap.put("LAST_ACBG_SE_CD" , saveMap.get("ACBG_SE_CD"));       // 최종학력구분코드
			infoMap.put("WRD_TELNO"		  , saveMap.get("TRPR_TELNO"));       // 유선전화번호
			infoMap.put("MBL_TELNO"       , saveMap.get("MBL_TELNO"));        // 휴대전화번호
			infoMap.put("EML_ADDR"        , saveMap.get("EML_ADDR"));         // 이메일주소
			infoMap.put("SNS_SE_CD"       , saveMap.get("SNS_SE_CD"));        // SNS구분코드
			infoMap.put("MSNGR_ID"        , saveMap.get("MSNGR_ID"));         // 메신저아이디 
			infoMap.put("ZIP"             , saveMap.get("ZIP"));              // 우편번호
			infoMap.put("PST_ADDR"        , saveMap.get("PST_ADDR"));         // 우편주소
			infoMap.put("DADDR"           , saveMap.get("DADDR"));            // 상세주소
			infoMap.put("RGN_CD"          , saveMap.get("RGN_CD"));           // 지역코드			
			
			// 주민등록번호
			String sRrno = String.valueOf(saveMap.get("RRNO"));
			/* 개인식별번호*/
			if (!sIndvIdntfcNo.isEmpty()) {
				
				infoMap.put("USER_ID"          , sUserId);     // 등록자
				
				String sPesnalModChk = "N"; // 항목변경여부
				sPesnalModChk = trprInqMapper.selectPesnalInfolModChk(infoMap);
				
				// 항목이 변경된건이 있을경우
				if ("Y".equals(sPesnalModChk) || "y".equals(sPesnalModChk)) {
					
					trprInqMapper.updateSCA300(infoMap);
					
					infoMap.put("DATAA_CHG_SE_CD", sStatus);
					trprInqMapper.insertSCA301(infoMap);
				}
				
				request.setAttribute("INDV_IDNTFC_NO", sIndvIdntfcNo);					
				
			} else{
				
//				LOGGER.debug("===== 개인식별번호 없음 =====");
				/* 주민번호*/
				if (! "".equals(sRrno) && ! "null".equals(sRrno)) {
					
					/* 개인식별번호 get or 채번*/
					sIndvIdntfcNo = setPersonal(request, infoMap);
					
					saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
					request.setAttribute("INDV_IDNTFC_NO", sIndvIdntfcNo);
					
					/* 개인식별번호만 update*/
					trprInqMapper.updateIndvIdntfcNo(saveMap);
					
					request.setAttribute("INDV_IDNTFC_NO", sIndvIdntfcNo);						
				}else {
					
//					LOGGER.debug("===== 개인식별번호 없음, 주민번호 없음 =====");
					/* 일감 #개발505 개인식별로직*/
//					int iChkCnt = idntfcTrprMapper.getIdntfcTrprList(saveMap);
//					retMap.put("CHK_CNT", iChkCnt);						
				}
			}
			
			// 대상자이력 등록 호출
			iHisCnt = trprInqMapper.insertTrprInqHistory(saveMap);
			
			// 2022-10-12 이충수매니저 요청사항 내일이룸(U10) 고유컬럼 저장
			if (sUntTaskwkSeCd.equals("U10")) {
				saveMap.put("LAST_MDFR_ID", sUserId);
				aimnsMapper.updateSrngTrpr(saveMap);
			}
			
		// 삭제처리
		} else if (sStatus.equals("d") || sStatus.equals("D")) {

			// 개인식별번호
			sIndvIdntfcNo = saveMap.get("INDV_IDNTFC_NO");
			if (!sIndvIdntfcNo.isEmpty()) {
				request.setAttribute("INDV_IDNTFC_NO", sIndvIdntfcNo);
			} else if (sIndvIdntfcNo.isEmpty()) {
				request.setAttribute("INDV_IDNTFC_NO", "");
			}

			// 대상자상세 삭제 호출
			iDelCnt = trprInqMapper.deleteTrprInqDetail(saveMap);
			if (iDelCnt > 0) {
				saveMap.put("DEL_YN", "Y");
				// 대상자이력 등록 호출
				iHisCnt = trprInqMapper.insertTrprInqHistory(saveMap);
			}
		}

		// 대상자번호 각 사례공통정보 테이블에서 사용위해 setAttribute
		String sTrprInfoNo = saveMap.get("TRPR_INFO_NO");
		if (sTrprInfoNo != null && !sTrprInfoNo.isEmpty()) {
			request.setAttribute("TRPR_INFO_NO", String.valueOf(sTrprInfoNo));
		} else if (sTrprInfoNo == null || sTrprInfoNo.equals("") || sTrprInfoNo.length() <= 0) {
			throw new UserException("errors.trprInfoNoRequired"); // 대상자 번호가 입력되지 않았습니다.
		}

		// 사례대상자가족 등록
		saveCaseTrprFamList(request, dataRequest);
		// 학력상태 등록
		saveAcbgSttsList(request, dataRequest);
		// 학업중단 등록
		saveSchulwDscntcList(request, dataRequest);
		// 취업정보 등록
		saveEmpymnInfoList(request, dataRequest);
		// 자격정보 등록
		saveInsertTrprQlfcInfoList(request, dataRequest);

		/* 2022-11-15 청소년자립지원관 면접심사 고유 추가요청(이명상이사님) */
		if (sUntTaskwkSeCd.equals("U05")) {
			saveAEB100List(request, dataRequest);
		}

		// 대상자정보번호(TR) key값 셋팅 (재조회용)
		retMap.put("TRPR_INFO_NO", saveMap.get("TRPR_INFO_NO"));
		
		return retMap;
	}
	
	/**
	 * @Method명   : deleteTrprInqDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 대상자 삭제
	 */
	@Override
	public Map<String, Object> deleteTrprInqDetail(HttpServletRequest request, DataRequest dataRequest)throws Exception {
			
		LOGGER.debug("=========== 대상자 삭제 START : deleteTrprInqDetail ===========");		
		
		/* 대상자 삭제*/
		int iIntCnt = 0; // 등록건수
		int iDelCnt = 0; // 삭제건수
		String sUserId = ""; // 세션정보의 유저ID
		String sStatus = "d"; // 상태코드(s:조회, i:신규, u:변경, d:삭제)

		// 세션정보 가져오기
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		sUserId = loginVO.getId();

		/* ---------------- */
		/* 대상자 상세 처리 */
		/* ---------------- */
		// 대상자상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDetail");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 대상자 상세자료가 없습니다.", Alert.ERROR);
		}
		Map<String, String> saveMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("======대상자삭제정보===[" + saveMap + "]");
		
		/* parameter로 넘어온 값으로 체크*/
		caseMngAndTrprTypeSeCdCheck(saveMap);
		
		/* 화면에서 tap을 눌러 사례를 등록하고 삭제하는경우 방지*/
		Map<String, Object> chkMap = new HashMap<>();
		chkMap = trprInqMapper.selectTrprInqDetail(saveMap);
		
		if(chkMap != null) {
			caseMngAndTrprTypeSeCdCheck(saveMap);
		}
		
		/* 대상자 삭제*/
		saveMap.put("SESS_USER_ID"   , sUserId); 					 /* 최종수정자아이디 */
		iDelCnt = trprInqMapper.deleteTrprInqDetail(saveMap);
		if(iDelCnt > 0) {
			
			saveMap.put("DATAA_CHG_SE_CD", sStatus);					 /* 데이터변경구분코드 */
			saveMap.put("DEL_YN", "Y");
			/* 대상자 이력등록*/
			 iIntCnt = trprInqMapper.insertTrprInqHistory(saveMap);		
		}
		
		LOGGER.debug("=========== 대상자 삭제 END : deleteTrprInqDetail ===========");

		return null;
	}
	
	private void caseMngAndTrprTypeSeCdCheck(Map<String, String> map) {
		
		LOGGER.debug("=========== 대상자 삭제 check start : caseMngAndTrprTypeSeCdCheck ===========");
		
		/* 사례가진행되고있는경우 삭제불가*/
		if("04".equals(map.get("CASE_MNG_SE_CD"))) {
			throw new AppWorksException("발굴대상자는 사례진행중으로 삭제가 불가능합니다.", Alert.ERROR);
		
		/* 사례대상자유형(직접발굴대상자만 삭제가능)*/
		}else if(! "01".equals(map.get("CASE_TRPR_TYPE_SE_CD"))) {
			throw new AppWorksException("대상자유형[직접발굴대상자]외 대상자는 삭제가 불가능합니다.", Alert.ERROR);
		
		/* 사례대상자유형구분 빈값*/
		}else if(map.get("CASE_MNG_SE_CD").isEmpty()) {
			throw new AppWorksException("발굴대상자의 사례관리구분 상태를 확인이 되지 않습니다.", Alert.ERROR);
		
		/* 사례진행상태구분코드 빈값*/
		} else if(map.get("CASE_TRPR_TYPE_SE_CD").isEmpty()) {
			throw new AppWorksException("발굴대상자의 사례관리구분 상태를 확인이 되지 않습니다.", Alert.ERROR);
		}
		
		LOGGER.debug("=========== 대상자 삭제 check end: caseMngAndTrprTypeSeCdCheck ===========");
	}

	/**
	 * @Method명 : setPersonal
	 * @param request
	 * @param infoMap
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 12. 7.
	 * @Method설명 : 개인식별번호 채번
	 */
	@Override
	public String setPersonal(HttpServletRequest request, Map<String, Object> infoMap) throws Exception {
		
		/* 개인식별번호 return 후 개인정보 테이블의 정보를 입력한 대상자정보로 update하는 로직은 포함되어있지 않음
		 * 개인식별번호만 retrun
		 * */
		
		/* infoMap 
		 * 대상자명   : TRPR_NM
		 * 생년월일   : TRPR_BRTH_YMD
		 * 성별       : SXDC_SE_CD
		 * 주민번호   : RRNO
		 * 휴대전화   : MBL_TELNO
		 * E-MAIL주소 : EML_ADDR
		 * 메신저 ID  : MSNGR_ID
		 * */	
		
		Map<String, String> chkMap = new HashMap<>();

		int iCnt = 0;
		boolean boRrno = false;
		String personalInfoId = "";

		String sRrno        = (String) infoMap.get("RRNO");
		String sTrprNm      = (String) infoMap.get("TRPR_NM");
		String sTrprBrthYmd = (String) infoMap.get("TRPR_BRTH_YMD");
		String sSxdcSeCd    = (String) infoMap.get("SXDC_SE_CD");
		
		
		// 1. if(생년월일, 성명, 성별이 없으면){ return personalInfoId; }
		if ( sTrprNm == null || sTrprBrthYmd == null || sSxdcSeCd == null) {
			return personalInfoId;
		}
		if ("".equals(sTrprNm) || "".equals(sTrprBrthYmd) || "".equals(sSxdcSeCd)) {
			return personalInfoId;
		}
		if ("null".equals(sTrprNm) || "null".equals(sTrprBrthYmd) || "null".equals(sSxdcSeCd)) {
			return personalInfoId;
		}	
		
		// 2. if(성별.equal("미확인") || 이름.equal("미확인") || 생년월일.equal("19000101")){ return personalInfoId; }
		if ("미확인".equals(sTrprNm) || "19000101".equals(sTrprBrthYmd) || "X".equals(sSxdcSeCd)) {
			return personalInfoId;
		}
		
		/* 0. 주민번호확인 ( 주민번호가 있는경우 개인정보테이블 주민번호 확인 후 있으면 
		 * 해당 개인식별번호 return, 없는경우 신규생성하여 해당 주민번호 return)
		 */
		if(sRrno != null && ! "null".equals(sRrno) && ! "".equals(sRrno)) {
			
			// 주민번호 유효성 체크
			boRrno = NumberCheckUtil.checkJuminNumber(sRrno);			
//			if(false == boRrno) throw new AppWorksException("주민번호 형식을 다시 확인 바랍니다.");			
			if(boRrno){
				
				Map<String, String> rrnoMap = new HashMap<>();
				rrnoMap.put("RRNO", sRrno);
				
				int iRrnoCnt = trprInqMapper.selectRRnoDpcnInq(rrnoMap);
				
				// 주민번호가 있으면 해당 개인식별번호 return
				if(iRrnoCnt >= 1) {
					
					rrnoMap = trprInqMapper.getSCA300IndvIdntfcNo(rrnoMap);
					personalInfoId = rrnoMap.get("INDV_IDNTFC_NO");
					
					return personalInfoId;
					
				// 주민번호가 없는경우 개인정보 신규생성하여 해당 개인식별번호 return
				//20230324 : 같은 주민번호가 없는 경우 신규로 개인식별번호를 채번하고 개인정보T에 insert
				}else {
					// 개인식별번호부여 함수 호출
					// 개인정보 SCA300 컬럼으로 변경
					infoMap.put("FLNM"     , sTrprNm);
					infoMap.put("BRTH_YMD" , sTrprBrthYmd);		
					personalInfoId = personalInfoService.savePersonalInfo(request, infoMap);				
					
					return personalInfoId;
				}
			}
		}	
		
		// 3. if(동일한 개인정보가 있는경우){ return personalInfoId; }
		chkMap.put("TRPR_NM", sTrprNm);
		chkMap.put("TRPR_BRTH_YMD", sTrprBrthYmd);
		chkMap.put("SXDC_SE_CD"   , sSxdcSeCd);
		// 개인정보 SCA300 대상자명 ,생년월일, 성별 확인
		iCnt = trprInqMapper.selectTrprNmBrdtDpcnInq(chkMap);
		
		if (iCnt > 0) return personalInfoId;
			
		// 4. 생년월일, 성명, 성별이 동일한 개인정보가 없는 경우 (대상자정보를 생성, 개인정보 생성)
		// 개인식별번호부여 함수 호출
		// 개인정보 SCA300 컬럼으로 변경
		infoMap.put("FLNM"     , sTrprNm);
		infoMap.put("BRTH_YMD" , sTrprBrthYmd);		
		personalInfoId = personalInfoService.savePersonalInfo(request, infoMap);

//		LOGGER.debug("개인식별번호 채번.setPersonal=[" + personalInfoId + "]");

		return personalInfoId;
	}

	/**
	 * @Method명 : selectCaseFamInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 7. 22.
	 * @Method설명 : 사례가족대상자 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCaseTrprFamList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 가족대상자가 없습니다..", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();


		return trprInqMapper.selectCaseTrprFamList(paramMap);
	}

	// 사례대상자가족 저장
	private void saveCaseTrprFamList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveCaseTrprFamList = dataRequest.getParameterGroup("dsFamInfo");

		Iterator<ParameterRow> insertedRows = saveCaseTrprFamList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = saveCaseTrprFamList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = saveCaseTrprFamList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		String sTrprInfoNo   =  (request.getAttribute("TRPR_INFO_NO") == null ? "" : request.getAttribute("TRPR_INFO_NO").toString());
		String sIndvIdntfcNo =  (request.getAttribute("INDV_IDNTFC_NO") == null ? "" : request.getAttribute("INDV_IDNTFC_NO").toString());

		// 2022-10-11 강화영 매니저님 요청
		// 개인식별번호가 부여됬을때 가족정보, 학력상태, 학업중단, 취업정보, 자격정보 에 있는 테이블의 개인식별번호를 업데이트
		// 개인식별전 등록된 정보 개인식별번호 수정
		Map<String, String> saveMap = new HashMap<>();
		if (! sTrprInfoNo.isEmpty()) {
			saveMap.put("TRPR_INFO_NO", sTrprInfoNo);
			saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
			saveMap.put("LAST_MDFR_ID", userId);
			trprInqMapper.updateCaseTrprFamIndvIdntfcNo(saveMap);
		}

		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();

			String sMngSn = mapIns.get("MNG_SN"); // 관리일련번호
			if (sMngSn.isEmpty()) {
				sMngSn = trprInqMapper.selectCaseTrprFamMngSn(sTrprInfoNo);
				mapIns.put("MNG_SN", sMngSn);
			}
			
			mapIns.put("TRPR_INFO_NO", sTrprInfoNo); /* 대상자번호(TR) */
			mapIns.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapIns.put("FRST_RGTR_ID", userId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID", userId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			trprInqMapper.insertCaseTrprFam(mapIns);
			trprInqMapper.insertCaseFamHistory(mapIns);
		}
		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();

			mapUpd.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.updateCaseTrprFam(mapUpd);
			trprInqMapper.insertCaseFamHistory(mapUpd);
		}
		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();

			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.deleteCaseTrprFam(mapDel);
			trprInqMapper.insertCaseFamHistory(mapDel);
		}
	}

	/**
	 * @Method명 : selectAcbgSttsList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 학력상태 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectAcbgSttsList(DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 학력상태가 없습니다..", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return trprInqMapper.selectAcbgSttsList(paramMap);
	}

	// 학력상태 저장
	private void saveAcbgSttsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveAcbgSttsList = dataRequest.getParameterGroup("dsAcbgStts");

		Iterator<ParameterRow> insertedRows = saveAcbgSttsList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = saveAcbgSttsList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = saveAcbgSttsList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		String sTrprInfoNo   =  (request.getAttribute("TRPR_INFO_NO") == null ? "" : request.getAttribute("TRPR_INFO_NO").toString());
		String sIndvIdntfcNo =  (request.getAttribute("INDV_IDNTFC_NO") == null ? "" : request.getAttribute("INDV_IDNTFC_NO").toString());

		// 2022-10-11 강화영 매니저님 요청
		// 개인식별번호가 부여됬을때 가족정보, 학력상태, 학업중단, 취업정보, 자격정보 에 있는 테이블의 개인식별번호를 업데이트
		Map<String, String> saveMap = new HashMap<>();
		if (!sIndvIdntfcNo.isEmpty()) {
			saveMap.put("TRPR_INFO_NO", sTrprInfoNo);
			saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
			saveMap.put("LAST_MDFR_ID", userId);
			trprInqMapper.updateAcbgSttsIndvIdntfcNo(saveMap);
		}

		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();

			String sMngSn = mapIns.get("MNG_SN"); // 관리일련번호
			if (sMngSn.isEmpty()) {
				sMngSn = trprInqMapper.selectAcbgSttsMngSn(sTrprInfoNo);
				mapIns.put("MNG_SN", sMngSn);
			}
			mapIns.put("TRPR_INFO_NO", sTrprInfoNo); /* 대상자번호(TR) */
			mapIns.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapIns.put("FRST_RGTR_ID", userId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID", userId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			trprInqMapper.insertAcbgStts(mapIns);
			trprInqMapper.insertAcbgSttsHistory(mapIns);
		}
		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.updateAcbgStts(mapUpd);
			trprInqMapper.insertAcbgSttsHistory(mapUpd);
		}
		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.deleteAcbgStts(mapDel);
			trprInqMapper.insertAcbgSttsHistory(mapDel);
		}
	}

	/**
	 * @Method명 : selectSchulwDscntcList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 학업중단 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectSchulwDscntcList(DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 학업중단상태가 없습니다..", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return trprInqMapper.selectSchulwDscntcList(paramMap);
	}

	// 학업중단 저장
	private void saveSchulwDscntcList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveSchulwDscntcList = dataRequest.getParameterGroup("dsSchulwDscntc");

		Iterator<ParameterRow> insertedRows = saveSchulwDscntcList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = saveSchulwDscntcList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = saveSchulwDscntcList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		String sTrprInfoNo   =  (request.getAttribute("TRPR_INFO_NO") == null ? "" : request.getAttribute("TRPR_INFO_NO").toString());
		String sIndvIdntfcNo =  (request.getAttribute("INDV_IDNTFC_NO") == null ? "" : request.getAttribute("INDV_IDNTFC_NO").toString());

		// 2022-10-11 강화영 매니저님 요청
		// 개인식별번호가 부여됬을때 가족정보, 학력상태, 학업중단, 취업정보, 자격정보 에 있는 테이블의 개인식별번호를 업데이트
		Map<String, String> saveMap = new HashMap<>();
		if (!sIndvIdntfcNo.isEmpty()) {
			saveMap.put("TRPR_INFO_NO", sTrprInfoNo);
			saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
			saveMap.put("LAST_MDFR_ID", userId);
			trprInqMapper.updateSchulwDscntcIndvIdntfcNo(saveMap);
		}

		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();

			String sMngSn = mapIns.get("MNG_SN"); // 관리일련번호
			if (sMngSn.isEmpty() || sMngSn == null || sMngSn.equals("")) {
				sMngSn = trprInqMapper.selectSchulwDscntcMngSn(sTrprInfoNo);
				mapIns.put("MNG_SN", sMngSn);
			}

			mapIns.put("TRPR_INFO_NO", sTrprInfoNo); /* 대상자번호(TR) */
			mapIns.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapIns.put("FRST_RGTR_ID", userId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID", userId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			trprInqMapper.insertSchulwDscnt(mapIns);
			trprInqMapper.insertSchulwDscntHistory(mapIns);
		}
		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.updateSchulwDscnt(mapUpd);
			trprInqMapper.insertSchulwDscntHistory(mapUpd);
		}
		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.deleteSchulwDscnt(mapDel);
			trprInqMapper.insertSchulwDscntHistory(mapDel);
		}
	}

	/**
	 * @Method명 : selectEmpymnInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 취업정보 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectEmpymnInfoList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 취업정보가 없습니다..", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return trprInqMapper.selectEmpymnInfoList(paramMap);
	}

	// 취업정보 저장
	private void saveEmpymnInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveEmpymnInfoList = dataRequest.getParameterGroup("dsEmpymnInfo");

		Iterator<ParameterRow> insertedRows = saveEmpymnInfoList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = saveEmpymnInfoList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = saveEmpymnInfoList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		String sTrprInfoNo   =  (request.getAttribute("TRPR_INFO_NO") == null ? "" : request.getAttribute("TRPR_INFO_NO").toString());
		String sIndvIdntfcNo =  (request.getAttribute("INDV_IDNTFC_NO") == null ? "" : request.getAttribute("INDV_IDNTFC_NO").toString());

		// 2022-10-11 강화영 매니저님 요청
		// 개인식별번호가 부여됬을때 가족정보, 학력상태, 학업중단, 취업정보, 자격정보 에 있는 테이블의 개인식별번호를 업데이트
		Map<String, String> saveMap = new HashMap<>();
		if (!sIndvIdntfcNo.isEmpty()) {
			saveMap.put("TRPR_INFO_NO", sTrprInfoNo);
			saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
			saveMap.put("LAST_MDFR_ID", userId);
			trprInqMapper.updateEmpymnInfoIndvIdntfcNo(saveMap);
		}

		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();

			String sMngSn = mapIns.get("MNG_SN"); // 관리일련번호
			if (sMngSn.isEmpty() || sMngSn == null || sMngSn.equals("")) {
				sMngSn = trprInqMapper.selectEmpymnInfoMngSn(sTrprInfoNo);
				mapIns.put("MNG_SN", sMngSn);
			}

			mapIns.put("TRPR_INFO_NO", sTrprInfoNo); /* 대상자번호(TR) */
			mapIns.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapIns.put("FRST_RGTR_ID", userId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID", userId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			trprInqMapper.insertEmpymnInfo(mapIns);
			trprInqMapper.insertEmpymnInfoHistory(mapIns);
		}
		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.updateEmpymnInfo(mapUpd);
			trprInqMapper.insertEmpymnInfoHistory(mapUpd);
		}
		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.deleteEmpymnInfo(mapDel);
			trprInqMapper.insertEmpymnInfoHistory(mapDel);
		}
	}

	/**
	 * @Method명 : selectTrprQlfcInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 7. 26.
	 * @Method설명 : 대상자자격정보 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectTrprQlfcInfoList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자격정보가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return trprInqMapper.selectTrprQlfcInfoList(paramMap);
	}

	// 자격정보 저장
	private void saveInsertTrprQlfcInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveInsertQlfcInfoList = dataRequest.getParameterGroup("dsQlfcInfo");

		Iterator<ParameterRow> insertedRows = saveInsertQlfcInfoList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = saveInsertQlfcInfoList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = saveInsertQlfcInfoList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		String sTrprInfoNo   =  (request.getAttribute("TRPR_INFO_NO") == null ? "" : request.getAttribute("TRPR_INFO_NO").toString());
		String sIndvIdntfcNo =  (request.getAttribute("INDV_IDNTFC_NO") == null ? "" : request.getAttribute("INDV_IDNTFC_NO").toString());

		// 2022-10-11 강화영 매니저님 요청
		// 개인식별번호가 부여됬을때 가족정보, 학력상태, 학업중단, 취업정보, 자격정보 에 있는 테이블의 개인식별번호를 업데이트
		Map<String, String> saveMap = new HashMap<>();
		if (!sIndvIdntfcNo.isEmpty()) {
			saveMap.put("TRPR_INFO_NO", sTrprInfoNo);
			saveMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
			saveMap.put("LAST_MDFR_ID", userId);
			trprInqMapper.updateQlfcInfoDtlIndvIdntfcNo(saveMap);
		}

		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();

			String sMngSn = mapIns.get("MNG_SN"); // 관리일련번호
			if (sMngSn.isEmpty() || sMngSn == null || sMngSn.equals("")) {

				sMngSn = trprInqMapper.selectTrprQlfcInfoMngSn(sTrprInfoNo);
				mapIns.put("MNG_SN", sMngSn);
			}

			mapIns.put("TRPR_INFO_NO", sTrprInfoNo); /* 대상자번호(TR) */
			mapIns.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapIns.put("FRST_RGTR_ID", userId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID", userId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			trprInqMapper.insertTrprQlfcInfo(mapIns);
			trprInqMapper.insertTrprQlfcInfoHistory(mapIns);
		}

		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("INDV_IDNTFC_NO", sIndvIdntfcNo); /* 개인식별번호(PN) */
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.updateTrprQlfcInfo(mapUpd);
			trprInqMapper.insertTrprQlfcInfoHistory(mapUpd);
		}

		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.deleteTrprQlfcInfo(mapDel);
			trprInqMapper.insertTrprQlfcInfoHistory(mapDel);
		}
	}

	/**
	 * @Method명 : selectPrvcHistoryList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 7. 26.
	 * @Method설명 : 개인정보이력 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectPrvcHistoryList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자격정보가 없습니다..", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList = trprInqMapper.selectPrvcHistoryList(paramMap);

		for (int i = 0; i < retList.size(); i++) {
			String sChgCsCn = ""; // 변경사유
			String sDataaChgSeCd = String.valueOf(retList.get(i).get("DATAA_CHG_SE_CD")); // 데이터변경구분코드
			if (sDataaChgSeCd.equals("I") || sDataaChgSeCd.equals("i")) {
				sChgCsCn = "정보등록";
				retList.get(i).put("CHG_CS_CN", sChgCsCn);
			} else if (sDataaChgSeCd.equals("U") || sDataaChgSeCd.equals("u")) {
				sChgCsCn = "정보수정";
				retList.get(i).put("CHG_CS_CN", sChgCsCn);
			} else if (sDataaChgSeCd.equals("D") || sDataaChgSeCd.equals("d")) {
				sChgCsCn = "정보삭제";
				retList.get(i).put("CHG_CS_CN", sChgCsCn);
			}
		}
		return retList;
	}

	/**
	 * @Method명 : selectAEB100List
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 11. 16.
	 * @Method설명 : 발굴대상자 등록(청소년자립지원관 면접심사 등록)
	 */
	@Override
	public List<Map<String, Object>> selectAEB100List(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 면접심사정보가 없습니다.", Alert.ERROR);
		}
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return trprInqMapper.selectAEB100List(paramMap);
	}

	/* 발굴대상자 등록(청소년자립지원관 면접심사 정보 등록) */
	private void saveAEB100List(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveAEB100List = dataRequest.getParameterGroup("dsIntrvwSrng");

		Iterator<ParameterRow> insertedRows = saveAEB100List.getInsertedRows();
		Iterator<ParameterRow> updatedRows = saveAEB100List.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = saveAEB100List.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		String sTrprInfoNo = String.valueOf(request.getAttribute("TRPR_INFO_NO"));
		while (insertedRows.hasNext()) {
			String sts = "I";

			String sAplyIntrvwMngNo = selectRenuNo(userId, "AI"); /* 신청면접관리번호 채번 */

			Map<String, String> mapIns = insertedRows.next().toMap();

			mapIns.put("TRPR_INFO_NO", sTrprInfoNo); /* 대상자번호(TR) */
			mapIns.put("APLY_INTRVW_MNG_NO", sAplyIntrvwMngNo); /* 신청면접관리번호 */
			mapIns.put("FRST_RGTR_ID", userId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID", userId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			trprInqMapper.insertAEB100(mapIns);
			// 이력
		}

		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.updateAEB100(mapUpd);
			// 이력
		}

		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			trprInqMapper.deleteAEB100(mapDel);
			// 이력
		}
	}

	/**
	 * @Method : selectPicList
	 * @Method설명 : 총괄담당자, 기관담당자 목록조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Yoon.Hee.Sung
	 * @작성일 : 2023. 04. 12.
	 */
	@Override
	public List<Map<String, Object>> selectPicList(DataRequest dataRequest)	throws Exception {
		List<Map<String, Object>> picList = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmPicSearch");

		if (paramGroup == null) {
			throw new AppWorksException("조회할 담당자 정보가 없습니다.");
		}

		Map<String, String> paramMap = new HashMap<>();
		paramMap = paramGroup.getSingleValueMap();
		
		return trprInqMapper.selectPicList(paramMap);
	}
	
	/**
	 * @Method : selectCaseCnt
	 * @Method설명 : 대상자 진행중인 사례건수 조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Yoon.Hee.Sung
	 * @작성일 : 2023. 05. 17.
	 */
	@Override
	public Map<String, Object> selectCaseCnt(DataRequest dataRequest)	throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");		
		
		Map<String, String> paramMap      = paramGroup.getSingleValueMap();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		rtnMap = trprInqMapper.selectCaseCnt(paramMap);
		
		return rtnMap; 
	}
}
