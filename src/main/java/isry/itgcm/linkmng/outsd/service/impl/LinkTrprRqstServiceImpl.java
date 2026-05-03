/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.linkmng.outsd.mapper.LinkTrprRqstMapper;
import isry.itgcm.linkmng.outsd.service.LinkTrprRqstService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.Formatter;

/**
 * @파일명        : LinkTrprRqstServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : TaesooSong
 * @작성일        : 2022. 8. 2. 
 * @수정자        : TaesooSong
 * @수정일        : 2022. 8. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("linkTrprRqstService")
public class LinkTrprRqstServiceImpl extends EgovAbstractServiceImpl implements LinkTrprRqstService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	
	
	@Resource(name = "linkTrprRqstMapper")
	private LinkTrprRqstMapper linkTrprRqstMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	public Map<String, Object> onLoadLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmBaseParam");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> dmParam = new HashMap<String, Object>();
		dmParam.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		dmParam.put("USER_ID", dmBase.getValue("USER_ID"));
		dmParam.put("LINK_TYPE_SE_CD", dmBase.getValue("LINK_TYPE_SE_CD"));
		dmParam.put("RQST_TRPR_INFO_NO", dmBase.getValue("RQST_TRPR_INFO_NO"));
		Map<String, Object> param1 = new HashMap<String, Object>();
		String enfsnNo = "";
		String untTaskwkSeCd = "";
		String userId = "";
		String instNo = "";
		String instNm = "";
		String clintNm = "";
		String trprInfoNo = "";
		// RQST_INST_NM
		// CLINT_NM
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			instNo = String.valueOf(loginVO.getInstNo());
			instNm = loginVO.getInstNm();
			untTaskwkSeCd = loginVO.getUntTaskwk();
			
			if ("".equals(untTaskwkSeCd) || null == untTaskwkSeCd) {
				untTaskwkSeCd = loginVO.getUntTaskwkSeCd();
			}
			enfsnNo = loginVO.getEnfsnNo();
			clintNm = loginVO.getUserName();
		}
		
		param1.put("USER_ID", userId);
		param1.put("CLINT_NO", enfsnNo);
		param1.put("CLINT_NM", clintNm);
		param1.put("RQST_INST_NM", instNm);
		param1.put("RQST_INST_NO", instNo);
		
		param.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		param.put("LINK_TYPE_SE_CD", dmBase.getValue("LINK_TYPE_SE_CD"));
		param.put("TRPR_INFO_NO", dmBase.getValue("RQST_TRPR_INFO_NO"));
		param.put("ENFSN_NO", dmBase.getValue("ENFSN_NO"));
		param.put("USER_ID", dmBase.getValue("USER_ID"));
		
		//의뢰검사 목록 가져오기
		List<Map<String, Object>> dsRcptList = linkTrprRqstMapper.getRcptHisList(param);
		
		//대상자 정보.
		Map<String, Object> existingTrprInfo = linkTrprRqstMapper.selectTrprInfoData(param);

		Map<String, Object> listParam = new HashMap<String, Object>();
		listParam.put("TRPR_INFO_NO", param.get("TRPR_INFO_NO"));
		listParam.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		if(null != existingTrprInfo) {
			if(!"".equals(existingTrprInfo.get("INDV_IDNTFC_NO")) && null != existingTrprInfo.get("INDV_IDNTFC_NO")) {
				listParam.put("INDV_IDNTFC_NO", existingTrprInfo.get("INDV_IDNTFC_NO"));
			}
		}
		
		// 사례관리이력 목록 가져오기
		List<Map<String, Object>> dsCaseMngHisList = linkTrprRqstMapper.selectCaseMngHisList(listParam); 
		
		// 서비스제공이력 목록 가져오기
		List<Map<String, Object>> dsSrvcPvsnHisList = linkTrprRqstMapper.selectSrvcPvsnHisList(param);
		
		List<Map<String, Object>> dsSrvcPvsnRqstList = linkTrprRqstMapper.selectSrvcPvsnRqstList(listParam);
		
		if(!"".equals(trprInfoNo)) {
			dmParam.put("RQST_TRPR_INFO_NO", trprInfoNo);
		}
		// param1.put("RQST_TRPR_INFO_NO", trprInfoNo);
		result.put("dmBaseParam", dmParam);
		result.put("dsRcptList", dsRcptList);
		result.put("dsCaseMngHisList", dsCaseMngHisList);
		result.put("dsSrvcPvsnHisList", dsSrvcPvsnHisList);
		result.put("dsSrvcPvsnRqstList", dsSrvcPvsnRqstList);
		result.put("param1", param1); // 세션정보 셋팅.
		return result;
	}
	
	public Map<String, Object> saveLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		
		Map<String, Object> param = new HashMap<String, Object>();
		ParameterGroup dmSrvcPvsnRqst = dataRequest.getParameterGroup("dmSrvcPvsnRqst");
		ParameterGroup dmTrprInq = dataRequest.getParameterGroup("dmTrprInq");
		ParameterGroup dmBaseParam = dataRequest.getParameterGroup("dmBaseParam");
		
		LOGGER.debug("saveLinkTrprRqst.dmSrvcPvsnRqst=[" + dmSrvcPvsnRqst + "]");
		LOGGER.debug("saveLinkTrprRqst.dmTrprInq=[" + dmTrprInq + "]");
		LOGGER.debug("saveLinkTrprRqst.dmBaseParam=[" + dmBaseParam + "]");
		
		Map<String, String> bbscttParam = dmBaseParam.getSingleValueMap();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		Integer authMenuNo = 128;
		String untTaskwkSeCd = "";
		String enfsnNo = "";
		String instNo = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			
			untTaskwkSeCd = loginVO.getUntTaskwkSeCd();
			if ("".equals(untTaskwkSeCd) || null == untTaskwkSeCd) {
				untTaskwkSeCd = loginVO.getUntTaskwk();
			}
			enfsnNo = loginVO.getEnfsnNo();
			instNo = String.valueOf(loginVO.getInstNo());
			
		}
		
		param.put("INDV_IDNTFC_NO", dmSrvcPvsnRqst.getValue("INDV_IDNTFC_NO"));
		param.put("TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		param.put("RQST_TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		param.put("RQST_TRPR_INFO_NM", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NM"));
		param.put("RCPT_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD")); // 접수단위업무구분코드
		param.put("RQST_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RQST_UNT_TASKWK_SE_CD")); // 의뢰단위업무구분코드
		
		// 2022-10-13 기준 로직 변경.
		// 선택된 대상자 정보 가져오기.
		Map<String, Object> existingTrprInfo = linkTrprRqstMapper.selectTrprInfoData(param);
		
		if ("".equals(dmSrvcPvsnRqst.getValue("SRVC_PVSN_RQST_NO"))) { // 등록일 경우.
			// SRVC_PVSN_RQST_NO 채번 
			Map<String, String> mngNoMap = new HashMap<String, String>();
			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "SQ");
			String srvcPvsnRqstNo = linkTrprRqstMapper.selectSysSeCd(mngNoMap);
			
			param.put("MENU_NO", authMenuNo);
			param.put("SRVC_PVSN_RQST_NO", srvcPvsnRqstNo); // 서비스제공의뢰번호
			param.put("RESRCE_NO", dmSrvcPvsnRqst.getValue("RESRCE_NO")); // 자원번호
			param.put("HPE_SRVC_YN", dmSrvcPvsnRqst.getValue("HPE_SRVC_YN")); // 희망서비스여부
			// 의뢰정보
			param.put("RCPT_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD"));
			param.put("RCPT_RQST_COURS_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_RQST_COURS_SE_CD")); // 접수의뢰경로구분코드
			param.put("RQST_YMD", dmSrvcPvsnRqst.getValue("RQST_YMD")); // 의뢰일자
			param.put("CLINT_NO", dmSrvcPvsnRqst.getValue("CLINT_NO")); // 의뢰자번호
			param.put("CLINT_NM", dmSrvcPvsnRqst.getValue("CLINT_NM")); // 의뢰자명
			param.put("RQST_INST_NO", dmSrvcPvsnRqst.getValue("RQST_INST_NO")); // 의뢰자기관번호
			param.put("RQST_CS_CN", dmSrvcPvsnRqst.getValue("RQST_CS_CN")); // 의뢰사유내용
			// 사용기간정보
			param.put("PRNMNT_USE_BGNG_YMD", dmSrvcPvsnRqst.getValue("PRNMNT_USE_BGNG_YMD")); // 예정사용시작일자
			param.put("PRNMNT_USE_BGNG_HR", dmSrvcPvsnRqst.getValue("PRNMNT_USE_BGNG_HR")); // 예정사용시간
			param.put("PRNMNT_USE_END_YMD", dmSrvcPvsnRqst.getValue("PRNMNT_USE_END_YMD")); // 예정사용종료일자
			param.put("PRNMNT_USE_END_HR", dmSrvcPvsnRqst.getValue("PRNMNT_USE_END_HR")); // 예정사용종료시간
			// 접수/승인정보
			param.put("UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD")); // 접수단위업무구분코드
			param.put("CLR_NO", dmSrvcPvsnRqst.getValue("CLR_NO")); // 접수자번호
			param.put("RCPT_INST_NO", dmSrvcPvsnRqst.getValue("RCPT_INST_NO")); // 접수기관번호
			param.put("RCPT_INST_NM", dmSrvcPvsnRqst.getValue("RCPT_INST_NM")); // 접수기관명
			param.put("RQST_INST_NM", dmSrvcPvsnRqst.getValue("RQST_INST_NM")); // 의뢰자기관명
			param.put("CASE_MNG_NO", dmSrvcPvsnRqst.getValue("CASE_MNG_NO")); // 사례관리번호
			param.put("CASE_MNG_ORDNO", dmSrvcPvsnRqst.getValue("CASE_MNG_ORDNO")); // 사례관리차수번호
			param.put("RQST_APLY_YN", dmSrvcPvsnRqst.getValue("RQST_APLY_YN")); // 의뢰신청여부
			param.put("RQST_DTL_CN", dmSrvcPvsnRqst.getValue("RQST_DTL_CN")); // 의뢰상세내용
			param.put("LINK_TYPE_SE_CD", dmSrvcPvsnRqst.getValue("LINK_TYPE_SE_CD")); // 연계유형구분코드
			param.put("ATFINO", dmSrvcPvsnRqst.getValue("ATFINO")); // 첨부파일번호
			param.put("TRMN_PRCS_YN", dmSrvcPvsnRqst.getValue("TRMN_PRCS_YN")); // 종결처리여부
			param.put("USER_ID", userId); // 등록자Id
			
			linkTrprRqstMapper.insertSrvcPvsnRqst(param);
			
			param.put("DATAA_CHG_SE_CD", "I"); // 
			// 이력 INSERT SEB401
			linkTrprRqstMapper.insertSrvcPvsnRqstHistory(param);
			
		} else { // 수정일 경우.
			//System.out.println(dmSrvcPvsnRqst.getValue("SRVC_PVSN_RQST_NO"));
			
			param.put("MENU_NO", authMenuNo);
			param.put("SRVC_PVSN_RQST_NO", dmSrvcPvsnRqst.getValue("SRVC_PVSN_RQST_NO")); // 서비스제공의뢰번호
			param.put("RESRCE_NO", dmSrvcPvsnRqst.getValue("RESRCE_NO")); // 자원번호
			param.put("HPE_SRVC_YN", dmSrvcPvsnRqst.getValue("HPE_SRVC_YN")); // 희망서비스여부
			// 의뢰정보
			param.put("RCPT_RQST_COURS_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_RQST_COURS_SE_CD")); // 접수의뢰경로구분코드
			param.put("RQST_YMD", dmSrvcPvsnRqst.getValue("RQST_YMD")); // 의뢰일자
			param.put("CLINT_NO", dmSrvcPvsnRqst.getValue("CLINT_NO")); // 의뢰자번호
			param.put("CLINT_NM", dmSrvcPvsnRqst.getValue("CLINT_NM")); // 의뢰자명
			param.put("RQST_INST_NO", dmSrvcPvsnRqst.getValue("RQST_INST_NO")); // 의뢰자기관번호
			param.put("RQST_INST_NM", dmSrvcPvsnRqst.getValue("RQST_INST_NM")); // 의뢰자기관명
			param.put("RQST_CS_CN", dmSrvcPvsnRqst.getValue("RQST_CS_CN")); // 의뢰사유내용
			// 사용기간정보
			param.put("PRNMNT_USE_BGNG_YMD", dmSrvcPvsnRqst.getValue("PRNMNT_USE_BGNG_YMD")); // 예정사용시작일자
			param.put("PRNMNT_USE_BGNG_HR", dmSrvcPvsnRqst.getValue("PRNMNT_USE_BGNG_HR")); // 예정사용시간
			param.put("PRNMNT_USE_END_YMD", dmSrvcPvsnRqst.getValue("PRNMNT_USE_END_YMD")); // 예정사용종료일자
			param.put("PRNMNT_USE_END_HR", dmSrvcPvsnRqst.getValue("PRNMNT_USE_END_HR")); // 예정사용종료시간
			// 접수/승인정보
			param.put("RCPT_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD")); // 접수단위업무구분코드
			param.put("CLR_NO", dmSrvcPvsnRqst.getValue("CLR_NO")); // 접수자번호
			param.put("RCPT_INST_NO", dmSrvcPvsnRqst.getValue("RCPT_INST_NO")); // 접수기관번호
			param.put("RCPT_INST_NM", dmSrvcPvsnRqst.getValue("RCPT_INST_NM")); // 접수기관명

//			param.put("RQST_TRPR_INFO_NO", trprInfoNo); // 의뢰대상자정보번호
			param.put("DATAA_CHG_SE_CD", "U"); 
			param.put("CASE_MNG_NO", dmSrvcPvsnRqst.getValue("CASE_MNG_NO")); // 사례관리번호
			param.put("CASE_MNG_ORDNO", dmSrvcPvsnRqst.getValue("CASE_MNG_ORDNO")); // 사례관리차수번호
			param.put("RQST_APLY_YN", dmSrvcPvsnRqst.getValue("RQST_APLY_YN")); // 의뢰신청여부
			param.put("RQST_DTL_CN", dmSrvcPvsnRqst.getValue("RQST_DTL_CN")); // 의뢰상세내용
			param.put("LINK_TYPE_SE_CD", dmSrvcPvsnRqst.getValue("LINK_TYPE_SE_CD")); // 연계유형구분코드
			param.put("ATFINO", dmSrvcPvsnRqst.getValue("ATFINO")); // 첨부파일번호
			param.put("TRMN_PRCS_YN", dmSrvcPvsnRqst.getValue("TRMN_PRCS_YN")); // 종결처리여부
			param.put("USER_ID", userId); // 등록자Id
			
			LOGGER.debug("업데이트=[" + param + "]");
			
			// 업데이트 SEB400
			linkTrprRqstMapper.updateSrvcPvsnRqst(param);
			// 이력 INSERT SEB401
			linkTrprRqstMapper.insertSrvcPvsnRqstHistory(param);
			
			// SEB420테이블 데이터가 있는지 확인
			Map<String, String> SEB420Map = linkTrprRqstMapper.selectSEB420(param);
			if(SEB420Map != null) {
				SEB420Map.put("USER_ID", userId);
				SEB420Map.put("RCPT_INST_NO", dmSrvcPvsnRqst.getValue("RCPT_INST_NO"));
				linkTrprRqstMapper.updateSEB420(SEB420Map);
			}
			
		}
		
		// 정태영이사님 요청 사항.
		if (!"".equals(dmBaseParam.getValue("BBSCTT_ESNTAL_NO"))) {
			param.put("BBSCTT_ESNTAL_NO", dmBaseParam.getValue("BBSCTT_ESNTAL_NO"));
			
			linkTrprRqstMapper.updateBbscttEsntalNo(param);
		}

		// 반재정 부장님 요청
		if(!"".equals(bbscttParam.get("BBSCTT_ESNTAL_NO")) && bbscttParam.get("BBSCTT_ESNTAL_NO") != null) {
			if(!"".equals(bbscttParam.get("BBSCTT_TYPE_SE_CD")) && bbscttParam.get("BBSCTT_TYPE_SE_CD") != null) {
				// 게시글 번호, 게시글유형구분코드가 있다
				param.put("BBSCTT_ESNTAL_NO", dmBaseParam.getValue("BBSCTT_ESNTAL_NO"));
				param.put("BBSCTT_TYPE_SE_CD", dmBaseParam.getValue("BBSCTT_TYPE_SE_CD"));
				linkTrprRqstMapper.updateBbscttEsntalNo2(param);
			} else {
				// 게시글 번호는 있지만 게시글유형구분코드가 없다
				param.put("BBSCTT_ESNTAL_NO", dmBaseParam.getValue("BBSCTT_ESNTAL_NO"));
				linkTrprRqstMapper.updateBbscttEsntalNo3(param);
			}
		}
		
		// ##############################################################
		result.put("dmSrvcPvsnRqst", param);
		return result;
	}
	
	public Map<String, Object> selectLinkTrprRqstList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup      = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 대상자 상세자료가 없습니다.", Alert.ERROR);
		}		
		LOGGER.debug("selectLinkTrprRqstList.paramMap=[" + paramGroup + "]");
		
		Map<String,Object> result      = new HashMap<String, Object>();
		Map<String, String> paramMap   = paramGroup.getSingleValueMap();
		
		/* 세션정보*/
		String userId = "";
		Integer authMenuNo = 128;

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		List<Map<String, Object>> list = linkTrprRqstMapper.selectLinkTrprRqstList(paramMap2);

		Map<String,Object> param = new HashMap<String, Object>();
		param.put("SRVC_PVSN_RQST_NO", paramMap.get("SRVC_PVSN_RQST_NO"));
		
		//의뢰검사 목록 가져오기
		List<Map<String, Object>> dsRcptList = linkTrprRqstMapper.getRcptHisList(param);
		
		result.put("dsList", list);
		result.put("dsRcptList", dsRcptList);
		
		return result;
	}
		
	
	public Map<String, Object> selectLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmBaseParam");
		ParameterGroup dmSrvcPvsnRqst = dataRequest.getParameterGroup("dmSrvcPvsnRqst");
		String userId = "";
		Integer authMenuNo = 128;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		Map<String, Object> srvcData = new HashMap<String, Object>();
		Map<String, Object> trprData = new HashMap<String, Object>();
		param.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		if (!"".equals(dmBase.getValue("SRVC_PVSN_RQST_NO"))) {
			srvcData = linkTrprRqstMapper.getSrvcPvsnRqstData(param);
			
			param.put("RQST_TRPR_INFO_NO", srvcData.get("RQST_TRPR_INFO_NO"));
			trprData = linkTrprRqstMapper.getRqstTrprData(param);
			
			srvcData.put("INDV_IDNTFC_NO", srvcData.get("INDV_IDNTFC_NO")); //개인식별번호
			srvcData.put("RQST_TRPR_INFO_NO", trprData.get("TRPR_INFO_NO")); //대상자번호
			
			// 휴대전화번호
			String sMblTelNoEncpt = String.valueOf(trprData.get("MBL_TELNO"));
			if (sMblTelNoEncpt == null || sMblTelNoEncpt.equals("null") || sMblTelNoEncpt.equals("")) {
				trprData.put("MBL_TELNO", "");
			} else {
				trprData.put("MBL_TELNO", Formatter.phoneFormat(sMblTelNoEncpt, 1));
			}
			
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(trprData.get("TRPR_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				trprData.put("TRPR_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
		} else if ("".equals(dmBase.getValue("SRVC_PVSN_RQST_NO")) && !"".equals(dmBase.getValue("RQST_TRPR_INFO_NO"))){
			param.put("RQST_TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
			
			trprData = linkTrprRqstMapper.getRqstTrprData(param);
			srvcData.put("RQST_TRPR_INFO_NO", trprData.get("TRPR_INFO_NO")); //대상자번호
		}
		
		result.put("dmSrvcPvsnRqst", srvcData);
		result.put("dmTrprInq", trprData);
		
		return result;
	}
	
	public Map<String, Object> executeLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 서비스 제공 접수 처리 .
		Map<String,Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSrvcPvsnRqst = dataRequest.getParameterGroup("dmSrvcPvsnRqst");
		ParameterGroup dmTrprInq = dataRequest.getParameterGroup("dmTrprInq");
		String userId = "";
		Integer authMenuNo = 128;
		String trprInfoNo = "";
		String untTaskwkSeCd = "";
		String enfsnNo = "";
		String instNo = "";
		String caseTrprTypeSeCd = ""; //사례대상자유형 구분코드
		String linkTypeSeCd = dmSrvcPvsnRqst.getValue("LINK_TYPE_SE_CD"); //연계유형구분코드
		
		//요청의뢰경로 구분을 확인(접수의뢰경로구분코드)
		String rqstCoursSeCd = dmSrvcPvsnRqst.getValue("RQST_COURS_SE_CD");
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			enfsnNo = loginVO.getEnfsnNo();
			//instNo = String.valueOf(loginVO.getInstNo());
		}
		instNo = dmSrvcPvsnRqst.getValue("RCPT_INST_NO");
		
		// 복지부, 병무청, 경찰청, 교육부 연계가 아님
		if(!"03".equals(linkTypeSeCd) && !"04".equals(linkTypeSeCd) && !"05".equals(linkTypeSeCd) && !"06".equals(linkTypeSeCd)) {
			caseTrprTypeSeCd = "02"; // 여성가족부기관연계대상자
		} else {
			if("03".equals(linkTypeSeCd)) {
				caseTrprTypeSeCd = "04"; // 보건복지부연계대상자
			} else if("04".equals(linkTypeSeCd)) {
				caseTrprTypeSeCd = "03"; // 병무청연계대상자
			} else if("05".equals(linkTypeSeCd)) {
				caseTrprTypeSeCd = "05"; // 경찰청연계대상자
			} else if("06".equals(linkTypeSeCd)) {
				caseTrprTypeSeCd = "06"; // 교육부연계대상자
			}
		}
		
		param.put("TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		param.put("RQST_TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		param.put("RCPT_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD")); // 접수단위업무구분코드
		param.put("RQST_UNT_TASKWK_SE_CD", untTaskwkSeCd); // 의뢰단위업무구분코드
		
		// 로직 전체 변경 2022-10-13 강매니저님과 협의 된 내용 기준 수정.
		
		// 선택되어 들어온 대상자 정보.
		Map<String, Object> existingTrprInfo = linkTrprRqstMapper.selectTrprInfoData(param);
		
		int typeNum = 0;
		// 개인식별번호 여부 체크.
		if (!"".equals(existingTrprInfo.get("INDV_IDNTFC_NO")) && null != existingTrprInfo.get("INDV_IDNTFC_NO")) {
			param.put("INDV_IDNTFC_NO", existingTrprInfo.get("INDV_IDNTFC_NO")); // 대상자 개인식별번호
			
			// 개인식별번호 기준으로 등록된 대상자 정보를 다 가져온다. 
			List<Map<String,Object>> trprInfoList = linkTrprRqstMapper.selectTrprInfoChk(param);
			
			// 의뢰신청 할 업무구분코드
			untTaskwkSeCd = dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD");
			if (null != trprInfoList && trprInfoList.size() > 0) {
				typeNum = trprInfoList.size() -1;
				Map<String, String> mngNoMap = new HashMap<String, String>();
				mngNoMap.put("USER_ID", userId);
				mngNoMap.put("SYS_CD", "TR");
				if("".equals(trprInfoNo)) {
					trprInfoNo = linkTrprRqstMapper.selectSysSeCd(mngNoMap);
				}
				
				Map<String, Object> temp = trprInfoList.get(typeNum); // 기 생성되어있는 대상자 정보중 마지막등록 된 내용을 가져온다.
				
				temp.put("TRPR_INFO_NO", trprInfoNo);
				temp.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
				temp.put("CASE_MNG_SE_CD", "01");
				temp.put("CASE_TRPR_UNSL_CS_CN", "연계의뢰");
				temp.put("RCPT_INST_NO", instNo); // 접수기관번호
				temp.put("CLR_NO", "0"); // 접수자번호
				temp.put("USER_ID", userId); // 접수자아이디
				temp.put("CASE_TRPR_TYPE_SE_CD", caseTrprTypeSeCd); // 사례대상자유형구분코드
				temp.put("RCPT_RQST_COURS_SE_CD", rqstCoursSeCd); // 접수의뢰경로구분코드
				linkTrprRqstMapper.insertLinkTrprInfo(temp);
			}
		} else { // 개인식별번호가 없을 경우.
			Map<String, String> mngNoMap = new HashMap<String, String>();
			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "TR");
			if("".equals(trprInfoNo)) {
				trprInfoNo = linkTrprRqstMapper.selectSysSeCd(mngNoMap);
			}
			// existingTrprInfo : 기존 대상자 정보 기준으로 복사하여 등록한다.
			Map<String, Object> temp = existingTrprInfo;
			
			// 의뢰신청 할 업무구분코드
			untTaskwkSeCd = dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD");
			temp.put("TRPR_INFO_NO", trprInfoNo);
			temp.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
			temp.put("CASE_MNG_SE_CD", "01");
			temp.put("CASE_TRPR_NOAP_CS_SE_CD", "99");
			temp.put("CASE_TRPR_UNSL_CS_CN", "연계의뢰");
			temp.put("RCPT_INST_NO", instNo); // 접수기관번호
			temp.put("CLR_NO", enfsnNo); // 접수자번호
			temp.put("USER_ID", userId); // 접수자아이디
			temp.put("CASE_TRPR_TYPE_SE_CD", caseTrprTypeSeCd); // 사례대상자유형구분코드
			temp.put("RCPT_RQST_COURS_SE_CD", rqstCoursSeCd); // 접수의뢰경로구분코드
			linkTrprRqstMapper.insertLinkTrprInfo(temp);
		}
		// 대상자 정보 삽입 완료.
		
		param.put("SRVC_PVSN_RQST_NO", dmSrvcPvsnRqst.getValue("SRVC_PVSN_RQST_NO"));
		int rcptSn = 0;
		String rqstAplyYn = "N"; //RQST_APLY_YN
		Map<String, Object> trprParam = new HashMap<String, Object>();
		
		// 신청, 신청취소, 반려 처리.
		if ("".equals(dmSrvcPvsnRqst.getValue("RCPT_SE_CD")) || "12".equals(dmSrvcPvsnRqst.getValue("RCPT_SE_CD")) || "30".equals(dmSrvcPvsnRqst.getValue("RCPT_SE_CD"))) {
			// 최초신청 or 신청취소 상태일 경우.
			param.put("RCPT_SE_CD", "11"); // 의뢰신청
			rqstAplyYn = "Y";
			
			trprParam = new HashMap<String, Object>();
			trprParam.put("CASE_MNG_SE_CD", "01"); // 사례대상자미신청
			trprParam.put("CASE_TRPR_NOAP_CS_SE_CD", "99"); // 기타
			trprParam.put("CASE_TRPR_UNSL_CS_CN", "연계의뢰");
			trprParam.put("TRPR_INFO_NO", trprInfoNo);
			linkTrprRqstMapper.updateTrprCaseMngInfo(trprParam);
			
		} else {
			param.put("RCPT_SE_CD", "12"); // 의뢰신청 취소
			
			// 기존 대상자
			trprParam = new HashMap<String, Object>();
			trprParam.put("CASE_MNG_SE_CD", "01"); // 사례대상자미신청
			trprParam.put("CASE_TRPR_NOAP_CS_SE_CD", "99"); // 기타
			trprParam.put("CASE_TRPR_UNSL_CS_CN", "연계의뢰취소");
			trprParam.put("TRPR_INFO_NO", trprInfoNo);
			linkTrprRqstMapper.updateTrprCaseMngInfo(trprParam);
		}
		
		param.put("RESRCE_NO", dmSrvcPvsnRqst.getValue("RESRCE_NO"));
		param.put("LINK_TYPE_SE_CD", dmSrvcPvsnRqst.getValue("LINK_TYPE_SE_CD"));
		param.put("RCPT_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD")); //접수단위업무구분코드
		param.put("RQST_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RQST_UNT_TASKWK_SE_CD")); // 복사될 업무구분.
		param.put("CLR_NO", dmSrvcPvsnRqst.getValue("CLR_NO"));
		param.put("RCPT_TRPR_INFO_NO", trprInfoNo);
		param.put("FRST_RQST_NO", dmSrvcPvsnRqst.getValue("SRVC_PVSN_RQST_NO"));
		
		param.put("RQST_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		//param.put("RCPT_TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		param.put("TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO"));
		param.put("RCPT_INST_NO", dmSrvcPvsnRqst.getValue("RCPT_INST_NO"));
		param.put("RCPT_YMD", dmSrvcPvsnRqst.getValue("RQST_YMD"));
		param.put("HPE_SRVC_YN", dmSrvcPvsnRqst.getValue("HPE_SRVC_YN"));
		param.put("RRQST_YN", "N");
		param.put("USER_ID", userId);
		param.put("ATFINO", dmSrvcPvsnRqst.getValue("ATFINO")); // 첨부파일번호
		rcptSn = linkTrprRqstMapper.insertSrvcPvsnRqstRcpt(param);
		
		param.put("RQST_APLY_YN", rqstAplyYn);
		// SEB400 RQST_APLY_YN 의뢰신청여부 UPDATE 처리
		linkTrprRqstMapper.updateSrvcPvsnRqstAplyYn(param);
		
		// SEB421 테이블 이력 등록
		param.put("RCPT_SN", rcptSn);
		param.put("DATAA_CHG_SE_CD", "U"); 
		linkTrprRqstMapper.insertSrvcPvsnRqstRcptHistory(param);
		
		// 이력 등록 
		param.put("MENU_NO", authMenuNo);
		param.put("SRVC_PVSN_RQST_NO", dmSrvcPvsnRqst.getValue("SRVC_PVSN_RQST_NO")); // 서비스제공의뢰번호
		param.put("RESRCE_NO", dmSrvcPvsnRqst.getValue("RESRCE_NO")); // 자원번호
		param.put("HPE_SRVC_YN", dmSrvcPvsnRqst.getValue("HPE_SRVC_YN")); // 희망서비스여부
		// 의뢰정보
		param.put("RCPT_RQST_COURS_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_RQST_COURS_SE_CD")); // 접수의뢰경로구분코드
		param.put("RQST_YMD", dmSrvcPvsnRqst.getValue("RQST_YMD")); // 의뢰일자
		param.put("CLINT_NO", dmSrvcPvsnRqst.getValue("CLINT_NO")); // 의뢰자번호
		param.put("RQST_INST_NO", dmSrvcPvsnRqst.getValue("RQST_INST_NO")); // 의뢰자기관번호
		param.put("RQST_CS_CN", dmSrvcPvsnRqst.getValue("RQST_CS_CN")); // 의뢰사유내용
		// 사용기간정보
		param.put("PRNMNT_USE_BGNG_YMD", dmSrvcPvsnRqst.getValue("PRNMNT_USE_BGNG_YMD")); // 예정사용시작일자
		param.put("PRNMNT_USE_BGNG_HR", dmSrvcPvsnRqst.getValue("PRNMNT_USE_BGNG_HR")); // 예정사용시간
		param.put("PRNMNT_USE_END_YMD", dmSrvcPvsnRqst.getValue("PRNMNT_USE_END_YMD")); // 예정사용종료일자
		param.put("PRNMNT_USE_END_HR", dmSrvcPvsnRqst.getValue("PRNMNT_USE_END_HR")); // 예정사용종료시간
		// 접수/승인정보
		param.put("RCPT_UNT_TASKWK_SE_CD", dmSrvcPvsnRqst.getValue("RCPT_UNT_TASKWK_SE_CD")); // 접수단위업무구분코드
		param.put("CLR_NO", dmSrvcPvsnRqst.getValue("CLR_NO")); // 접수자번호
		param.put("RCPT_INST_NO", dmSrvcPvsnRqst.getValue("RCPT_INST_NO")); // 접수기관번호

		param.put("RQST_TRPR_INFO_NO", dmSrvcPvsnRqst.getValue("RQST_TRPR_INFO_NO")); // 의뢰대상자정보번호
		
		param.put("CASE_MNG_NO", dmSrvcPvsnRqst.getValue("CASE_MNG_NO")); // 사례관리번호
		param.put("CASE_MNG_ORDNO", dmSrvcPvsnRqst.getValue("CASE_MNG_ORDNO")); // 사례관리차수번호
		param.put("RQST_DTL_CN", dmSrvcPvsnRqst.getValue("RQST_DTL_CN")); // 의뢰상세내용
		param.put("LINK_TYPE_SE_CD", dmSrvcPvsnRqst.getValue("LINK_TYPE_SE_CD")); // 연계유형구분코드
		param.put("TRMN_PRCS_YN", dmSrvcPvsnRqst.getValue("TRMN_PRCS_YN")); // 종결처리여부
		param.put("USER_ID", userId); // 등록자Id
		
		// SEB401 이력 추가
		linkTrprRqstMapper.insertSrvcPvsnRqstHistory(param);
		
		return result;
	}
	
	public Map<String, Object> selectLinkTrprRcptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String,Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		Map<String,String> param = dmBase.getSingleValueMap();
		String userId = "";
		Integer authMenuNo = 128;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		//의뢰대상기관 - 로그인한 사용자 기관
		String INST_TYPE_SE_CD = loginVO.getInstTypeSeCd(); //기관유형
		String UNT_TASKWK_SE_CD = loginVO.getUntTaskwk(); //단위업무구분코드
		Integer USER_INST_NO = loginVO.getUserInstNo(); //사용자기관번호
		

		
		
		param.put("INST_TYPE_SE_CD", INST_TYPE_SE_CD);
		if(param.get("UNT_TASKWK_SE_CD") == null || "".equals(param.get("UNT_TASKWK_SE_CD"))) {
			param.put("UNT_TASKWK_SE_CD", UNT_TASKWK_SE_CD);
		}
		//param.put("UNT_TASKWK_SE_CD", "U02");
		param.put("USER_INST_NO", String.valueOf(USER_INST_NO));
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		param.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		LOGGER.debug("연계접수목록검색=[" + param + "]");
		List<Map<String, Object>> list  = linkTrprRqstMapper.selectLinkTrprRcptList(paramMap2);
		
		result.put("dsList", list);
		return result;
	}
	
	public Map<String, Object> selectLinkTrprRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSrvcPvsnRqst");
		String userId = "";
		Integer authMenuNo = 128;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		
		param.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		Map<String, Object> srvcData = linkTrprRqstMapper.getSrvcPvsnRqstData(param);
		
		param.put("RQST_TRPR_INFO_NO", srvcData.get("RQST_TRPR_INFO_NO"));
		Map<String, Object> trprData = linkTrprRqstMapper.getRqstTrprData(param);
		
		srvcData.put("INDV_IDNTFC_NO", srvcData.get("INDV_IDNTFC_NO")); //개인식별번호.
		// 휴대전화번호
		String sMblTelNoEncpt = String.valueOf(trprData.get("MBL_TELNO"));
		if (sMblTelNoEncpt == null || sMblTelNoEncpt.equals("null") || sMblTelNoEncpt.equals("")) {
			trprData.put("MBL_TELNO", "");
		} else {
			trprData.put("MBL_TELNO", Formatter.phoneFormat(sMblTelNoEncpt, 1));
		}
		
		// 전화번호 포멧팅
		String sTrprTelno = String.valueOf(trprData.get("TRPR_TELNO"));
		if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
			trprData.put("TRPR_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
		}
		
		List<Map<String, Object>> rcptList = linkTrprRqstMapper.selectLinkTrprRcptHistory(param);
		for (int i=0;i<rcptList.size();i++) {
			Map<String, Object> temp = rcptList.get(i);
			if (i == 0) {
				temp.put("FRST_YN", "Y");
			} else {
				temp.put("FRST_YN", "N");
			}
			
			rcptList.set(i, temp);
		}
		
		result.put("dmSrvcPvsnRqst", srvcData);
		result.put("dmTrprInq", trprData);
		result.put("rcptList", rcptList);
		
		return result;
		
	}
	
	public Map<String, Object> saveLinkTrprRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSrvcPvsnRcpt");
		
		param.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		param.put("RCPT_SN", dmBase.getValue("RCPT_SN"));
		param.put("RCPT_DTL_CN", dmBase.getValue("RCPT_DTL_CN")); // 접수상세내용
		param.put("USER_ID", userId);
		
		param.put("RRQST_YN", dmBase.getValue("RRQST_YN"));

		// 수정할 내용 정리. 
		param.put("RJCT_CS_SE_CD", dmBase.getValue("RJCT_CS_SE_CD")); // 반려사유구분코드
		param.put("RJCT_CS_ETC_CN", dmBase.getValue("RJCT_CS_ETC_CN")); // 반려사유기타내용
		param.put("ATFINO", dmBase.getValue("ATFINO")); // 첨부파일
		
		param.put("RRQST_YN", dmBase.getValue("RRQST_YN")); // 첨부파일
		
		if("Y".equals(dmBase.getValue("RRQST_YN"))) { // 재의뢰여부 예 선택시. 
			// 로직 Description
			// 기존 정보에 받아온 정보를 업데이트 한다. 
			param.put("RRQST_YMD", dmBase.getValue("RRQST_YMD")); // 첨부파일
			param.put("RRQST_RCPT_INST_NO", dmBase.getValue("RRQST_RCPT_INST_NO")); // 첨부파일
			param.put("RRQST_RCPT_UNT_TASKWK_SE_CD", dmBase.getValue("RRQST_RCPT_UNT_TASKWK_SE_CD")); // 첨부파일
			param.put("RRQST_CLR_NO", dmBase.getValue("RRQST_CLR_NO")); // 첨부파일
			param.put("RCPT_SE_CD", "40"); // 접수 구분을 타기관이송 상태로 변경한다.
			// 기존 정보 업데이트
			linkTrprRqstMapper.updateSrvcPvsnRcpt(param);
			
			Map<String, Object> rcptHist = linkTrprRqstMapper.selectLinkTrprRcptHist(param);
			rcptHist.put("RRQST_YMD", ""); // 
			rcptHist.put("RRQST_RCPT_INST_NO", ""); // 
			rcptHist.put("RRQST_RCPT_UNT_TASKWK_SE_CD", ""); // 
			rcptHist.put("RRQST_CLR_NO", ""); // 
			rcptHist.put("RCPT_SE_CD", "11"); // 
			rcptHist.put("FRST_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO")); // 임시로 넣음.

			rcptHist.put("ATFINO", dmBase.getValue("ATFINO")); // 
			rcptHist.put("RCPT_YMD", dmBase.getValue("RRQST_YMD")); // 
			rcptHist.put("RCPT_INST_NO", dmBase.getValue("RRQST_RCPT_INST_NO")); // 
			rcptHist.put("RCPT_UNT_TASKWK_SE_CD", dmBase.getValue("RRQST_RCPT_UNT_TASKWK_SE_CD")); // 
			rcptHist.put("CLR_NO", dmBase.getValue("RRQST_CLR_NO")); // 
			rcptHist.put("RRQST_YN", "N"); // 재의뢰여부 
			rcptHist.put("USER_ID", userId); // 재의뢰여부 
			
			int rcptSn = linkTrprRqstMapper.insertSrvcPvsnRqstRcpt(rcptHist);
			
			// SEB421 테이블 이력 등록
			rcptHist.put("RCPT_SN", rcptSn);
			rcptHist.put("DATAA_CHG_SE_CD", "U");
			rcptHist.replace("RRQST_YN", "Y"); // 첨부파일
			rcptHist.replace("RRQST_YMD", dmBase.getValue("RRQST_YMD")); // 첨부파일
			rcptHist.replace("RRQST_RCPT_INST_NO", dmBase.getValue("RRQST_RCPT_INST_NO")); // 첨부파일
			rcptHist.replace("RRQST_RCPT_UNT_TASKWK_SE_CD", dmBase.getValue("RRQST_RCPT_UNT_TASKWK_SE_CD")); // 첨부파일
			rcptHist.replace("RRQST_CLR_NO", dmBase.getValue("RRQST_CLR_NO")); // 첨부파일
			rcptHist.replace("RCPT_SE_CD", "40"); // 접수 구분을 타기관이송 상태로 변경한다.
			
			linkTrprRqstMapper.insertSrvcPvsnRqstRcptHistory(rcptHist);
			
		}
		if("30".equals(dmBase.getValue("RCPT_TYPE_CD"))) { // 반려
			//반려사유구분, 반려사유기타, 첨부파일, 접수상세내용 업데이트
			param.put("RRQST_YMD", ""); // 
			param.put("RRQST_RCPT_INST_NO", ""); // 
			param.put("RRQST_RCPT_UNT_TASKWK_SE_CD", ""); // 
			param.put("RRQST_CLR_NO", ""); // 
			param.put("ATFINO", dmBase.getValue("ATFINO")); //
			
			linkTrprRqstMapper.updateSrvcPvsnRcpt(param);
			
			Map<String, Object> rcptHist = linkTrprRqstMapper.selectLinkTrprRcptHist(param);
			// SEB421 테이블 이력 등록
			rcptHist.put("DATAA_CHG_SE_CD", "U"); 
			rcptHist.put("FRST_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO")); 
			rcptHist.put("USER_ID", userId); 
			linkTrprRqstMapper.insertSrvcPvsnRqstRcptHistory(rcptHist);
			
		}
		
		linkTrprRqstMapper.updateRcptDtlCn(param);
		// 갱신 용.
		//List<Map<String, Object>> rcptList = linkTrprRqstMapper.selectLinkTrprRcpt(param);
		List<Map<String, Object>> rcptList = linkTrprRqstMapper.selectLinkTrprRcptHistory(param);

		result.put("rcptList", rcptList);
		
		return result;
	}
	
	public Map<String, Object> executeLinkTrprRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSrvcPvsnRcpt");
		ParameterGroup dmSrvcPvsnRqst = dataRequest.getParameterGroup("dmSrvcPvsnRqst");
		String userId = "";
		Integer authMenuNo = 128;
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String today = simpleDateFormat.format(new Date()); // 오늘날짜(YYYYMMDD)
		String untTaskwk = "";
		String enfsnNo = "";
		int instNo = 0;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		
		param.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		param.put("RCPT_SN", dmBase.getValue("RCPT_SN"));
		param.put("RRQST_YN", dmBase.getValue("RRQST_YN"));
		param.put("FRST_YN", dmBase.getValue("FRST_YN"));
		param.put("RCPT_SE_CD", dmBase.getValue("RCPT_SE_CD"));
		param.put("USER_ID", userId); 
		
		Map<String, Object> trprParam = new HashMap<String, Object>();
		Map<String, Object> trprSrvcInfo = linkTrprRqstMapper.selectTrprSrvcInfo(param);
		// RCPT_TYPE_CD
		if ("21".equals(dmBase.getValue("RCPT_TYPE_CD"))) { // 접수승인
			param.put("RCPT_SE_CD", "21");
			// 접수등록 대상자
			trprParam = new HashMap<String, Object>();
			trprParam.put("CASE_MNG_SE_CD", "02"); // 사례대상자신청(대기상태)
			trprParam.put("CASE_TRPR_NOAP_CS_SE_CD", ""); // 삭제
			trprParam.put("CASE_TRPR_UNSL_CS_CN", " "); //삭제
			trprParam.put("TRPR_INFO_NO", trprSrvcInfo.get("RCPT_TRPR_INFO_NO"));
			
			if (loginVO != null && loginVO.getEnfsnNo() != null && !"".equals(loginVO.getEnfsnNo())) {
				enfsnNo = loginVO.getEnfsnNo();
				trprParam.put("RCPT_PIC_NO", enfsnNo);	// 접수 담당자
			}
			if (loginVO != null && loginVO.getInstNo() != null && loginVO.getInstNo() != 0) {
				instNo = loginVO.getInstNo();
				trprParam.put("RCPT_INST_NO", instNo);	// 접수 기관
			}
			
			if (loginVO != null && loginVO.getUntTaskwk() != null && !"".equals(loginVO.getUntTaskwk())) {
				untTaskwk = loginVO.getUntTaskwk();
				trprParam.put("UNT_TASKWK_SE_CD", untTaskwk);	// 접수 단위업무
			}
			linkTrprRqstMapper.updateTrprCaseMngInfo(trprParam);
		} else if ("22".equals(dmBase.getValue("RCPT_TYPE_CD"))) { // 접수승인취소
			param.put("RCPT_SE_CD", "22");
			
			// 사례가 진행중인지 확인
			Map<String, Object> caseParam = new HashMap<String, Object>();
			caseParam.put("RCPT_TRPR_INFO_NO", dmBase.getValue("RCPT_TRPR_INFO_NO"));
			caseParam.put("RCPT_UNT_TASKWK_SE_CD", dmBase.getValue("RCPT_UNT_TASKWK_SE_CD"));
			String caseYn = linkTrprRqstMapper.getCaseYn(caseParam);
			if("Y".equals(caseYn)) {
				throw new AppWorksException("사례가 진행중인 건은\n접수취소 하실 수 없습니다.", Alert.ERROR);
			}
			
			// 접수등록 대상자
			trprParam = new HashMap<String, Object>();
			trprParam.put("CASE_MNG_SE_CD", "01"); // 사례대상자미신청
			trprParam.put("CASE_TRPR_NOAP_CS_SE_CD", "99"); // 기타
			trprParam.put("CASE_TRPR_UNSL_CS_CN", "연계접수승인취소"); //연계접수승인취소
			trprParam.put("TRPR_INFO_NO", trprSrvcInfo.get("RCPT_TRPR_INFO_NO"));
			linkTrprRqstMapper.updateTrprCaseMngInfo(trprParam);
		} else if ("30".equals(dmBase.getValue("RCPT_TYPE_CD"))) { // 반려
			param.put("RCPT_SE_CD", "30");
			param.put("RJCT_CS_SE_CD", dmBase.getValue("RJCT_CS_SE_CD"));
			param.put("RJCT_CS_ETC_CN", dmBase.getValue("RJCT_CS_ETC_CN"));
			param.put("RQST_APLY_YN", "N");
			//	접수등록 대상자
			trprParam = new HashMap<String, Object>();
			trprParam.put("CASE_MNG_SE_CD", "01"); // 사례대상자미신청
			trprParam.put("CASE_TRPR_NOAP_CS_SE_CD", "99"); // 삭제
			trprParam.put("CASE_TRPR_UNSL_CS_CN", "연계접수반려"); //연계접수반려
			trprParam.put("TRPR_INFO_NO", trprSrvcInfo.get("RCPT_TRPR_INFO_NO"));
			linkTrprRqstMapper.updateTrprCaseMngInfo(trprParam);
			// 의뢰 신청여부 update. 반려인 경우 N으로 update해줘야 재신청이 가능하다.
			trprParam.put("RQST_APLY_YN", "N");
			trprParam.put("SRVC_PVSN_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
			trprParam.put("USER_ID", userId);
			linkTrprRqstMapper.updateAplyYn(trprParam);
			
		}
		param.put("RCPT_DTL_CN", dmBase.getValue("RCPT_DTL_CN"));
		param.put("RCPT_YMD", today);
		
		linkTrprRqstMapper.updateLinkTrprRcptSeCd(param);
		
		Map<String, Object> rcptHist = linkTrprRqstMapper.selectLinkTrprRcptHist(param);
		// SEB421 테이블 이력 등록
		rcptHist.put("DATAA_CHG_SE_CD", "U"); 
		rcptHist.put("FRST_RQST_NO", dmBase.getValue("SRVC_PVSN_RQST_NO"));
		rcptHist.put("RJCT_CS_SE_CD", dmBase.getValue("RJCT_CS_SE_CD")); 
		rcptHist.put("RJCT_CS_ETC_CN", dmBase.getValue("RJCT_CS_ETC_CN")); 
		rcptHist.put("RCPT_SE_CD", param.get("RCPT_SE_CD"));
		rcptHist.put("RCPT_DTL_CN", param.get("RCPT_DTL_CN"));
		rcptHist.put("RCPT_YMD", today);
		if(!"".equals(untTaskwk)) {
			rcptHist.put("RCPT_UNT_TASKWK_SE_CD", untTaskwk);
		}
		if(instNo != 0) {
			rcptHist.put("RCPT_INST_NO", instNo);
		}
		rcptHist.put("USER_ID", userId); 
		linkTrprRqstMapper.insertSrvcPvsnRqstRcptHistory(rcptHist);
		
		// 갱신 용.
		List<Map<String, Object>> rcptList = linkTrprRqstMapper.selectLinkTrprRcptHistory(param);
		for (int i=0;i<rcptList.size();i++) {
			Map<String, Object> temp = rcptList.get(i);
			if (i == 0) {
				temp.put("FRST_YN", "Y");
			} else {
				temp.put("FRST_YN", "N");
			}
			
			rcptList.set(i, temp);
		}
		
		result.put("rcptList", rcptList);
		
		return result;
	}

	/**
	 * @Method명   : deleteLinkTrprRqst
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 :
	 */
	public void deleteLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dmBaseParam = dataRequest.getParameterGroup("dmBaseParam");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("SRVC_PVSN_RQST_NO", dmBaseParam.getValue("SRVC_PVSN_RQST_NO"));
		linkTrprRqstMapper.deleteLinkTrprRqst(param);
		linkTrprRqstMapper.deleteLinkTrprRqstHis(param);
		
		// 비밀게시글에 연계번호가 있으면 연계번호를 null로 update한다
		// 2022.12.14 반재정부장님 요청
		Map<String, Object> bbscttParam = linkTrprRqstMapper.selectLinkBbsctt(param);
		if(bbscttParam != null) {
			linkTrprRqstMapper.updateBbscttEsntalNo2(bbscttParam);
		}
	}
	
	/**
	 * @Method명   : selectLinkTrprRcptPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 07. 05. 
	 * @Method설명 : 페이징 처리를 위해 추가
	 */
	public Map<String, Object> selectLinkTrprRcptPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		Map<String,String> param = dmBase.getSingleValueMap();
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
				ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		String userId = "";
		Integer authMenuNo = 128;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		//의뢰대상기관 - 로그인한 사용자 기관
		String INST_TYPE_SE_CD = loginVO.getInstTypeSeCd(); //기관유형
		String UNT_TASKWK_SE_CD = loginVO.getUntTaskwk(); //단위업무구분코드
		Integer USER_INST_NO = loginVO.getUserInstNo(); //사용자기관번호
		
		param.put("INST_TYPE_SE_CD", INST_TYPE_SE_CD);
		if(param.get("UNT_TASKWK_SE_CD") == null || "".equals(param.get("UNT_TASKWK_SE_CD"))) {
			param.put("UNT_TASKWK_SE_CD", UNT_TASKWK_SE_CD);
		}
		//param.put("UNT_TASKWK_SE_CD", "U02");
		param.put("USER_INST_NO", String.valueOf(USER_INST_NO));
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		param.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		String cnt = linkTrprRqstMapper.selectLinkTrprRcptCnt(paramMap2);
		paramMap2.put("TOT_CNT", cnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(totCnt > 0) {
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
	        rtnMap = linkTrprRqstMapper.selectLinkTrprRcptList(paramMap2);
		}
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		result.put("dsList", rtnMap);
		result.put("dmPage", resPage);
		
		return result;
	}
}
