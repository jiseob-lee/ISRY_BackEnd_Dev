/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consultantabltymng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.consultantabltymng.mapper.DscsnAbilitMngMapper;
import isry.couns.mngr.consultantabltymng.service.DscsnAbilitMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : DscsnAbilitMngServiceImpl.java
 * @프로그램 설명 : 상담원 역량관리
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 01. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 01. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("dscsnAbilitMngService")
public class DscsnAbilitMngServiceImpl extends IsryBaseServiceImpl implements DscsnAbilitMngService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="dscsnAbilitMngMapper")
	private DscsnAbilitMngMapper dscsnAbilitMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	ScpDb  scpDb  = new ScpDb();
	Masking mask  = new Masking();
	
	/**
	 * @Method명   : selectEvlCnsttnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 : 평가구성 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlCnsttnList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlCnsttnList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			String res = ""; // 평가항목
			if(map.get("PRVSON_NM").toString().indexOf("01")>=0) {
				res += "채팅상담";
			}	
			if(map.get("PRVSON_NM").toString().indexOf("02")>=0) {
				res += ("".equals(res)? "" : ",");
				res += "게시판상담";
			}
			if(map.get("PRVSON_NM").toString().indexOf("03")>=0) {
				res += ("".equals(res)? "" : ",");
				res += "메신저상담";
			}
			if(map.get("PRVSON_NM").toString().indexOf("04")>=0) {
				res += ("".equals(res)? "" : ",");
				res += "실적상담";
			}
			LOGGER.debug("res ::::::::::::" + res);
			map.put("EVL_PRVSON", res);
			
			String resValue = ""; // 반영비율
			if(map.get("PRVSON_NM").toString().indexOf("01")>=0) {
				resValue += "채팅반영비율:" + map.get("CHTT_WGHTVA_VALUE").toString() + "%";
			}	
			if(map.get("PRVSON_NM").toString().indexOf("02")>=0) {
				resValue += ("".equals(resValue)? "" : ",");
				resValue += "게시판상담반영비율:" + map.get("NTABRD_WGHTVA_VALUE").toString() + "%";
			}
			if(map.get("PRVSON_NM").toString().indexOf("03")>=0) {
				resValue += ("".equals(resValue)? "" : ",");
				resValue += "메신저상담반영비율:" + map.get("MSNGR_WGHTVA_VALUE").toString() + "%";
			}
			if(map.get("PRVSON_NM").toString().indexOf("04")>=0) {
				resValue += ("".equals(resValue)? "" : ",");
				resValue += "실적상담반영비율:" + map.get("PRFMNC_WGHTVA_VALUE").toString() + "%";
			}
			LOGGER.debug("resValue ::::::::::::" + resValue);
			map.put("RFLT_RATE", resValue);
			
//			if(map.get("PIC_EML_ADDR_ENCPT") != null) map.put("PIC_EML_ADDR_ENCPT", mask.nameMasking( scpDb.scpDecB64(map.get("PIC_EML_ADDR_ENCPT").toString() ) ) ); // 이메일
//			if(map.get("PIC_NM_ENCPT") != null) map.put("PIC_NM_ENCPT", mask.nameMasking( scpDb.scpDecB64(map.get("PIC_NM_ENCPT").toString() ) ) ); // 담당자명
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : evlCnsttnSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 01. 
	 * @Method설명 : 평가구성 등록
	 */
	@Override
	public Map<String, String> evlCnsttnInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		String sEnfsnNm     = ""; // 세션정보의 종사자명
//		String sPrvsonNm    = ""; // 평가항목
//		String sNm   		= ""; // 항목명
//		int regCntAYC200 	= 0; // 평가지관리T 
		
		Map<String, Object> subMap01 = new HashMap<>();
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvlCnsttnReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("RGTR_ID", sUserId); // 등록자아이디		
		dmOutcomeDetailMap.put("PRVSON_NM", dmOutcomeDetailMap.get("PRVSON_NM").toString()); // 항목명
		
		
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("01")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD1", "01");
		}	
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("02")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD2", "02");
		}
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("03")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD3", "03");
		}
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("04")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD4", "04");
		}
		
//		// enfsnNo 종사자번호로 종사자명 구하기
//		subMap01 = dscsnAbilitMngMapper.selectEnfsnNm(loginVO.getEnfsnNo());
//		sEnfsnNm = subMap01.get("FLNM_ENCPT").toString();
//		LOGGER.debug("종사자명 ::::::::::::" + scpDb.scpDecB64(sEnfsnNm)); // 종사자명 복호화
		
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.InsertEvlCnsttn(dmOutcomeDetailMap); // 평가지관리 저장(AYC200)
		
//		LOGGER.debug("dmOutcomeDetailMap 222::::::::::::" + dmOutcomeDetailMap.toString());
//		
//		if(regCntAYC200 > 0) {
//			LOGGER.debug("regCntAYC200 ::::::::::::" + regCntAYC200);
//
//			dmOutcomeDetailMap.put("CRT_YMD", DateUtil.getToday()); 	// 생성일자
//			dmOutcomeDetailMap.put("APRAI_ID", sUserId); 				// 평가자아이디
//			dmOutcomeDetailMap.put("APRAI_NM_ENCPT", sEnfsnNm); 		// 평가자명암호화
//			dscsnAbilitMngMapper.InsertEvlCnsttnDtl(dmOutcomeDetailMap); // 평가지관리상세 저장(AYC210)	
//		}
				
		return null;
	}
	
	/**
	 * @Method명	 : selectEvlCnsttnUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 03. 
	 * @Method설명 : 평가구성수정 조회
	 */
	@Override
	public Map<String,Object> selectEvlCnsttnUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvlCnsttnUpdate(paramMap);
	}
	
	/**
	 * @Method명   : evlCnsttnUpdate
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 02. 
	 * @Method설명 : 평가구성 수정
	 */
	@Override
	public Map<String, String> evlCnsttnUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvlCnsttnReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("01")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_1", "01");
		}else {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_1", "");
			dmOutcomeDetailMap.put("CHTT_WGHTVA_VALUE", "");
		}
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("02")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_2", "02");
		}else {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_2", "");
			dmOutcomeDetailMap.put("NTABRD_WGHTVA_VALUE", "");
		}
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("03")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_3", "03");
		}else {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_3", "");
			dmOutcomeDetailMap.put("MSNGR_WGHTVA_VALUE", "");
		}
		if(dmOutcomeDetailMap.get("PRVSON_NM").toString().indexOf("04")>=0) {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_4", "04");
		}else {
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_4", "");
			dmOutcomeDetailMap.put("PRFMNC_WGHTVA_VALUE", "");
		}
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateEvlCnsttn(dmOutcomeDetailMap); // 평가지관리 수정(AYC200)
				
		return null;
	}
	
	/**
	 * @Method명   : cfmtnSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 02. 
	 * @Method설명 : 확정 수정
	 */
	@Override
	public Map<String, String> cfmtnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		String sEvfoMngSn   = ""; // 평가지관리일련번호
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmCfmtnSave");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		LOGGER.debug("dmOutcomeDetailMap 222::::::::::::" + dmOutcomeDetailMap.get("EVFO_MNG_SN").toString());
		
		dscsnAbilitMngMapper.UpdateCfmtnYn(dmOutcomeDetailMap); // 평가지관리 수정(AYC200)_확정여부

		// AYC280(평가관리)테이블 조회
		sEvfoMngSn = dmOutcomeDetailMap.get("EVFO_MNG_SN").toString(); // 평가지관리일련번호  

		List<Map<String, Object>> resultMap = dscsnAbilitMngMapper.selectCfmtnInfo(sEvfoMngSn); // 확정정보 조회		
//		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		
		for(Map<String, Object> map : resultMap) {
			
			map.put("RLVT_YR", map.get("RLVT_YR").toString());
			map.put("EVL_TME_SE_CD", map.get("EVL_TME_SE_CD").toString());
			map.put("EVL_GROUP_SE_CD", map.get("EVL_GROUP_SE_CD").toString());
			map.put("SRVY_KND_SE_CD", map.get("SRVY_KND_SE_CD").toString());						
			map.put("CONSTT_ID", map.get("CONSTT_ID").toString());		
			
			if("01".equals(map.get("SRVY_KND_SE_CD").toString())) { // 채팅
				map.put("CHTT_DSCSN_EVL_SCORE",  map.get("EVL_SUM_SCORE").toString() );
			}else if("02".equals(map.get("SRVY_KND_SE_CD").toString())) { // 게시판
				map.put("NTABRD_DSCSN_EVL_SCORE",  map.get("EVL_SUM_SCORE").toString() );
			}else if("03".equals(map.get("SRVY_KND_SE_CD").toString())) { // 메신저
				map.put("MSNGR_DSCSN_EVL_SCORE",  map.get("EVL_SUM_SCORE").toString() );
			}
			
			map.put("FRST_RGTR_ID",  sUserId);
			map.put("LAST_MDFR_ID",  sUserId);
			
//			rtnMap.add(map);
			
			LOGGER.debug("RLVT_YR 000 ::::::::::::" + map.get("RLVT_YR").toString());
//			LOGGER.debug("rtnMap 000 ::::::::::::" + rtnMap.toString());
			
			// AYC270(평가점수관리)테이블 저장
			dscsnAbilitMngMapper.insertEvlScoreMng(map); // AYC270
		}
		
		return null;
	}
	
	/**
	 * @Method명   : exclncCaseRowDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 우수사례로우삭제
	 */
	@Override
	public Map<String, String> exclncCaseRowDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmRowDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.UpdateExclncCaseRow(dmOutcomeDetailMap); // AYC290
		
		return null;
	}
	
	/**
	 * @Method명   : exclncCaseAllDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 우수사례전체삭제
	 */
	@Override
	public Map<String, String> exclncCaseAllDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmRowDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.UpdateExclncCaseAll(dmOutcomeDetailMap); // AYC290
		
		return null;
	}
	
	/**
	 * @Method명   : superVisionAllDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 수퍼비전전체삭제
	 */
	@Override
	public Map<String, String> superVisionAllDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmRowDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.UpdateSuperVisionAll(dmOutcomeDetailMap); // AYC295
		
		return null;
	}
	
	/**
	 * @Method명   : superVisionRowDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 수퍼비전로우삭제
	 */
	@Override
	public Map<String, String> superVisionRowDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmRowDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.UpdateSuperVisionRow(dmOutcomeDetailMap); // AYC295
		
		return null;
	}
	
	/**
	 * @Method명   : cfmtnRtrcnSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 확정취소 수정
	 */
	@Override
	public Map<String, String> cfmtnRtrcnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmCfmtnSave");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateCfmtnRtrcnYn(dmOutcomeDetailMap); // 평가지관리 수정(AYC200)_확정여부
		
		dscsnAbilitMngMapper.UpdateEvlScoreMng(dmOutcomeDetailMap); // AYC270(평가점수관리)테이블 삭제여부	
		
		return null;
	}
	
	/**
	 * @Method명   : selectEvfoMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 : 평가지관리 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvfoMngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvfoMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명	 : selectEvfoMngBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 03. 
	 * @Method설명 : 평가지관리 기본정보 조회
	 */
	@Override
	public Map<String,Object> selectEvfoMngBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvfoMngBassInfo(paramMap);
	}
	
	/**
	 * @Method명   : selectEvfoMngInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 03. 
	 * @Method설명 : 평가지관리 목록 조회
	 */
	public List<Map<String, Object>> selectEvfoMngInfoList(DataRequest dataRequest) throws Exception {

//		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
//		
//		return dscsnAbilitMngMapper.selectEvfoMngInfoList(searchParamMap);
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvfoMngInfoList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
		
	}
	
	/**
	 * @Method명   : evfoAddingInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 : 평가지 추가 등록
	 */
	@Override
	public Map<String, String> evfoAddingInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvfoAdding");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
			
		
		String res = ""; // 항목명
		
		if("Y".equals(dmOutcomeDetailMap.get("chk1").toString())) {
			res += "01";
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_1", "01");
		}else {
			dmOutcomeDetailMap.put("CHTT_BGNG_YMD", "");
			dmOutcomeDetailMap.put("CHTT_END_YMD", "");
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_1", "");
//			dmOutcomeDetailMap.put("CHTT_WGHTVA_VALUE", "");
		}
		
		if("Y".equals(dmOutcomeDetailMap.get("chk2").toString())) {
			res += ("".equals(res)? "" : ",");
			res += "02";
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_2", "02");
		}else {
			dmOutcomeDetailMap.put("NTABRD_BGNG_YMD", "");
			dmOutcomeDetailMap.put("NTABRD_END_YMD", "");
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_2", "");
//			dmOutcomeDetailMap.put("NTABRD_WGHTVA_VALUE", "");
		}
		
		if("Y".equals(dmOutcomeDetailMap.get("chk3").toString())) {
			res += ("".equals(res)? "" : ",");
			res += "03";
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_3", "03");
		}else {
			dmOutcomeDetailMap.put("MSNGR_BGNG_YMD", "");
			dmOutcomeDetailMap.put("MSNGR_END_YMD", "");
			dmOutcomeDetailMap.put("SRVY_KND_SE_CD_3", "");
//			dmOutcomeDetailMap.put("MSNGR_WGHTVA_VALUE", "");
		}
		
		dmOutcomeDetailMap.put("PRVSON_NM", res); // 항목명
		
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.UpdateEvfoAdding(dmOutcomeDetailMap); // 평가지관리 수정(AYC200)_평가지 추가
				
		return null;
	}
	
	/**
	 * @Method명	 : selectEvfoAdding
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 03. 
	 * @Method설명 : 평가지 추가 조회
	 */
	@Override
	public Map<String,Object> selectEvfoAdding(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEvfoAdding");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvfoAdding(paramMap);
	}
	
	/**
	 * @Method명	 : selectEvfoMngBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 05. 
	 * @Method설명 : 평가지관리 역량관리 기본정보 조회
	 */
	@Override
	public Map<String,Object> selectEvfoAbilitMngBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvfoAbilitMngBassInfo(paramMap);
	}
	
	/**
	 * @Method명	 : selectEvfoCrtrMngBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 07. 
	 * @Method설명 : 평가지관리 기준관리 기본정보 조회
	 */
	@Override
	public Map<String,Object> selectEvfoCrtrMngBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvfoCrtrMngBassInfo(paramMap);		
	}
	
	/**
	 * @Method명   : selectEvfoAbilitMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 : 평가지관리 역량관리 목록 조회
	 */
	public List<Map<String, Object>> selectEvfoAbilitMngList(DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvfoAbilitMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
		
	}
	
	/**
	 * @Method명   : selectEvfoCrtrMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 : 평가지관리 기준관리 목록 조회
	 */
	public List<Map<String, Object>> selectEvfoCrtrMngList(DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvfoCrtrMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
		
	}
	
	/**
	 * @Method명   : evfoAbilitMngInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 05. 
	 * @Method설명 : 평가지 역량등록
	 */
	@Override
	public Map<String, String> evfoAbilitMngInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvfoAbilitMng");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
			
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		dmOutcomeDetailMap.put("CRT_ID", sUserId);
		dmOutcomeDetailMap.put("MDFR_ID", sUserId);
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.InsertEvfoAbilitMng(dmOutcomeDetailMap); // 체크리스트관리(AYC250)
		dscsnAbilitMngMapper.InsertEvfoAbilitMngGroup(dmOutcomeDetailMap); // 체크리스트그룹(AYC255)
		
				
		return null;
	}
	
	/**
	 * @Method명   : selectApraiMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 : 평가자 관리 조회
	 */	
	@Override
	public List<Map<String, Object>> selectApraiMngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectApraiMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlTrprMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 : 평가 대상자 관리 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlTrprMngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlTrprMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlTrprSlctnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 : 평가대상자 선정 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlTrprSlctnList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		LOGGER.debug("paramMap 111::::::::::::" + paramMap.toString());
		rtn = dscsnAbilitMngMapper.selectEvlTrprSlctnList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlTrprMatchingList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 평가대상가 매칭 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlTrprMatchingList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		LOGGER.debug("paramMap 111::::::::::::" + paramMap.toString());
		rtn = dscsnAbilitMngMapper.selectEvlTrprMatchingList(paramMap);
		
		LOGGER.debug("rtnsizesizesizesizesize ::::::::::::" + rtn.size());
		
		if(rtn.size() == 0) {
			throw new AppWorksException("평가자관리 화면에서 평가자를 우선 선정해주세요~!", Alert.ERROR);
		}
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlTrprChcList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 평가대상자선택 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlTrprChcList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		LOGGER.debug("paramMap 111::::::::::::" + paramMap.toString());
		rtn = dscsnAbilitMngMapper.selectEvlTrprChcList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : evlTrprSlctnInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 : 평가대상자선정 등록	
	 */	
	@Override
	public List<Map<String, String>> evlTrprSlctnInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 ::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsEvlTrprSlctnList");
		List<Map<String, String>> dsEvlTrprSlctnList = params.getAllRowList();
		
		LOGGER.debug("dsEvlTrprSlctnList 111 ::::::::::: " + dsEvlTrprSlctnList.toString());
		
		for(int i=0; i<dsEvlTrprSlctnList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsEvlTrprSlctnList.get(i).get("CHK").toString() );
			
			if("false".equals(dsEvlTrprSlctnList.get(i).get("CHK").toString()) ) {
				
				dmOutcomeDetailMap.put("EVFO_MNG_SN",  dsEvlTrprSlctnList.get(i).get("EVFO_MNG_SN").toString()); 
				dmOutcomeDetailMap.put("CONSTT_ID",  dsEvlTrprSlctnList.get(i).get("USER_ID").toString());
				
				dscsnAbilitMngMapper.updateTrprSlctnFalse(dmOutcomeDetailMap);    // AYC240
			}
			
			if("true".equals(dsEvlTrprSlctnList.get(i).get("CHK").toString()) ) {
				
				LOGGER.debug("dsEvlTrprSlctnList 222 ::::::::::: " + dsEvlTrprSlctnList.toString());
				
				dmOutcomeDetailMap.put("EVFO_MNG_SN",  dsEvlTrprSlctnList.get(i).get("EVFO_MNG_SN").toString()); 
				dmOutcomeDetailMap.put("CONSTT_ID",  dsEvlTrprSlctnList.get(i).get("USER_ID").toString());
							
				try {
					
					Integer evlSeq = dscsnAbilitMngMapper.selectEvlSeq(); // 평가대상자선정 일련번호
					dmOutcomeDetailMap.put("EVL_TRPR_SLCTN_SN",  evlSeq.toString()); 
					
					LOGGER.debug("evlSeq ::::::::::: " + evlSeq);
					
					dscsnAbilitMngMapper.insertEvlTrprSlctn(dmOutcomeDetailMap); // AYC240
					dscsnAbilitMngMapper.updateTrprSlctn(dmOutcomeDetailMap);    // AYC240
					
				} catch (Exception e) {
					LOGGER.debug("AYC240 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsEvlTrprSlctnList.size(); i++) {	
		
		return null;	
	}
	
	/**
	 * @Method명   : evlTrprChcInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 평가대상자선택 등록
	 */	
	@Override
	public List<Map<String, String>> evlTrprChcInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		String sMinDelYn     = "";
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 ::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsEvlTrprChcList");
		List<Map<String, String>> dsEvlTrprChcList = params.getAllRowList();
		
		LOGGER.debug("dsEvlTrprSlctnList 111 ::::::::::: " + dsEvlTrprChcList.toString());
		LOGGER.debug("size 111 ::::::::::: " + dsEvlTrprChcList.size());
		
		for(int i=0; i<dsEvlTrprChcList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsEvlTrprChcList.get(i).get("CHK").toString() );
			
			if("false".equals(dsEvlTrprChcList.get(i).get("CHK").toString()) || dsEvlTrprChcList.get(i).get("CHK").toString() == null ) {
				
				dmOutcomeDetailMap.put("EVL_GROUP_TYPE_SE_CD",  dsEvlTrprChcList.get(i).get("EVL_GROUP_TYPE_SE_CD").toString()); 
				dmOutcomeDetailMap.put("CONSTT_ID",  dsEvlTrprChcList.get(i).get("CONSTT_ID").toString());
				dmOutcomeDetailMap.put("APRAI_MNG_DTL_SN",  dsEvlTrprChcList.get(i).get("APRAI_MNG_DTL_SN").toString());
				
				// AYC240 MIN(DEL_YN) 조회
				Map<String, String> seqMap1 = new HashMap<>();
				Map<String, Object> valMap1 = new HashMap<>();
				seqMap1.put("CONSTT_ID",       dsEvlTrprChcList.get(i).get("CONSTT_ID").toString());
				valMap1 = dscsnAbilitMngMapper.selectMinDelYn(seqMap1);
				sMinDelYn = valMap1.get("DEL_YN").toString();
				LOGGER.debug("sMinDelYn	 ::::::::" + sMinDelYn);
				
				if("Y".equals(sMinDelYn)) {
					dscsnAbilitMngMapper.updateDelYn(dmOutcomeDetailMap); // AYC230
				}
				
				dscsnAbilitMngMapper.updateEvlTrprChcFalse(dmOutcomeDetailMap); // AYC230
			}
			if("true".equals(dsEvlTrprChcList.get(i).get("CHK").toString()) ) {
				
				LOGGER.debug("dsEvlTrprSlctnList 222 ::::::::::: " + dsEvlTrprChcList.toString());
				
				Integer evlSeq = dscsnAbilitMngMapper.selectEvlTrprChc(); // 
				dmOutcomeDetailMap.put("EVL_TRGT_SN",  evlSeq.toString()); 
				
				dmOutcomeDetailMap.put("EVL_GROUP_TYPE_SE_CD",  dsEvlTrprChcList.get(i).get("EVL_GROUP_TYPE_SE_CD").toString()); 
				dmOutcomeDetailMap.put("CONSTT_ID",  dsEvlTrprChcList.get(i).get("CONSTT_ID").toString());
				dmOutcomeDetailMap.put("CONSTT_NM_ENCPT",  dsEvlTrprChcList.get(i).get("FLNM_ENCPT").toString());
				
				dmOutcomeDetailMap.put("APRAI_MNG_DTL_SN",  dsEvlTrprChcList.get(i).get("APRAI_MNG_DTL_SN").toString());
							
				try {
					dscsnAbilitMngMapper.insertEvlTrprChc(dmOutcomeDetailMap); // AYC230
					dscsnAbilitMngMapper.updateEvlTrprChc(dmOutcomeDetailMap); // AYC230
					
				} catch (Exception e) {
					LOGGER.debug("AYC240 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsEvlTrprChcList.size(); i++) {	
		
		return null;	
	}
	
	/**
	 * @Method명   : selectEvlSeMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 : 평가서관리 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlSeMngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlSeMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlSeMngInqList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 06. 
	 * @Method설명 : 평가서관리 평가자목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlSeMngInqList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlSeMngInqList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명	 : selectEvfoAbilitUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 07. 
	 * @Method설명 : 역량수정 조회
	 */
	@Override
	public Map<String,Object> selectEvfoAbilitUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvfoAbilitUpdate(paramMap);
	}
	
	/**
	 * @Method명   : evfoAbilitUpdate
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 : 역량수정
	 */
	@Override
	public Map<String, String> evfoAbilitUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvfoAbilitUpdate");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateEvfoAbilit(dmOutcomeDetailMap); // AYC255
				
		return null;
	}
	
	/**
	 * @Method명   : evfoAbilitMngDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 : 역량삭제
	 */
	@Override
	public Map<String, String> evfoAbilitMngDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.DeleteEvfoAbilitMng(dmOutcomeDetailMap); // AYC255
				
		return null;
	}
	
	/**
	 * @Method명   : selectEvfoCrtrAddingIngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 : 평가기준 추가 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvfoCrtrAddingIngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvfoCrtrAddingIngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : evfoCrtrAdding
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 07. 
	 * @Method설명 : 평가기준추가 수정
	 */
	@Override
	public Map<String, String> evfoCrtrAdding(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		int iQuestNo     = 0;  // 질의번호
		String sQuestNo  = ""; // 질의번호
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvfoCrtrAdding");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		iQuestNo++;		
		sQuestNo = String.valueOf(iQuestNo);
		dmOutcomeDetailMap.put("QUEST_NO", sQuestNo);
		
		dmOutcomeDetailMap.put("CRT_ID", sUserId);
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		
		
		dscsnAbilitMngMapper.InsertEvfoCrtrAdding(dmOutcomeDetailMap); // AYC260
				
		return null;
	}
	
	/**
	 * @Method명	 : selectEvfoCrtrUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 08. 
	 * @Method설명 : 평가기준수정 조회
	 */
	@Override
	public Map<String,Object> selectEvfoCrtrUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvfoCrtrUpdate(paramMap);
	}
	
	/**
	 * @Method명   : evfoCrtr
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 평가기준수정
	 */
	@Override
	public Map<String, String> evfoCrtr(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEvfoCrtrUpdate");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
						
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateEvfoCrtr(dmOutcomeDetailMap); // AYC260
				
		return null;
	}
	
	/**
	 * @Method명   : evfoCrtrMngDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 역량삭제
	 */
	@Override
	public Map<String, String> evfoCrtrMngDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.DeleteEvfoCrtrMng(dmOutcomeDetailMap); // AYC260
				
		return null;
	}
	
	/**
	 * @Method명	 : selectMngrApraiMngBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 08. 
	 * @Method설명 : 평가지관리 기준관리 기본정보 조회
	 */
	@Override
	public Map<String,Object> selectMngrApraiMngBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectMngrApraiMngBassInfo(paramMap);		
	}
	
	/**
	 * @Method명   : selectMngrApraiMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 관리자평가자관리 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectMngrApraiMngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectMngrApraiMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectMngrApraiMngCoResultList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 동료상담원 결과 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectMngrApraiMngCoResultList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectMngrApraiMngCoResultList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectMngrApraiMngCoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 : 동료상담원 평가자관리 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectMngrApraiMngCoList(DataRequest dataRequest) throws Exception {

//		int iJcnt = 0;
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		List<Map<String, Object>> rtnList2 = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectMngrApraiMngCoList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		
		for(int i=0; i<rtn.toArray().length; i++) {
								
//			LOGGER.debug("나머지 000::::::::::::" + ( i+1)%5);
//			LOGGER.debug("나머지 111::::::::::::" + ( i+1));
//			LOGGER.debug("나머지 222::::::::::::" + i);
			
			if(( i+1)%5  == 1 ) {
				map = new HashMap<>();
				
//				LOGGER.debug("1111 ::::::::::::" + rtn.get(i).get("APRAI_MNG_DTL_SN"));
				
				map.put("CONSTT_ID1", rtn.get(i).get("CONSTT_ID"));
				map.put("CONSTT_NM1", rtn.get(i).get("CONSTT_NM"));
				map.put("EVL_CD1", rtn.get(i).get("EVL_CD"));
				
				map.put("APRAI_MNG_DTL_SN1", rtn.get(i).get("APRAI_MNG_DTL_SN"));
				
			}else if(( i+1)%5 ==2 || ( i+1)%5 ==3 || ( i+1)%5 ==4 ) {
				
//				LOGGER.debug("2222 ::::::::::::");
				
				map.put("CONSTT_ID" + ( i+1)%5, rtn.get(i).get("CONSTT_ID"));
				map.put("CONSTT_NM" + ( i+1)%5, rtn.get(i).get("CONSTT_NM"));
				map.put("EVL_CD" + ( i+1)%5, rtn.get(i).get("EVL_CD"));
				
				map.put("APRAI_MNG_DTL_SN" + ( i+1)%5, rtn.get(i).get("APRAI_MNG_DTL_SN"));
				
			}else { 
				
//				LOGGER.debug("3333 ::::::::::::");
				
				map.put("CONSTT_ID5" , rtn.get(i).get("CONSTT_ID"));
				map.put("CONSTT_NM5" , rtn.get(i).get("CONSTT_NM"));
				map.put("EVL_CD5" , rtn.get(i).get("EVL_CD"));
				
				map.put("APRAI_MNG_DTL_SN5" , rtn.get(i).get("APRAI_MNG_DTL_SN"));
				
				rtnList2.add(map);				
			}
				
			if((rtn.toArray().length == (i+1)) && ( ( i+1)%5  != 0)) {
				
				//map.put("CONSTT_ID" , rtn.get(i).get("CONSTT_ID"));
				//map.put("CONSTT_NM" , rtn.get(i).get("CONSTT_NM"));
				
				rtnList2.add(map);				
			}
			
//			if((( i+1) > 10) && ( ( i+1)%5  == 1)) {
//				LOGGER.debug("444 ::::::::::::" + rtn.get(i).get("CONSTT_NM"));
//				
//				map.put("CONSTT_ID1" , rtn.get(i).get("CONSTT_ID"));
//				map.put("CONSTT_NM1" , rtn.get(i).get("CONSTT_NM"));								
//				
//			}else if((( i+1) > 10) && ( ( i+1)%5  == 2)) {
//				LOGGER.debug("555 ::::::::::::" + rtn.get(i).get("CONSTT_NM"));
//				
//				map.put("CONSTT_ID2" , rtn.get(i).get("CONSTT_ID"));
//				map.put("CONSTT_NM2" , rtn.get(i).get("CONSTT_NM"));
//				
//				rtnList2.add(map);
//			}
			
			//iJcnt = 0;
//			rtnList2.set(i, map);
//			rtnList2.add(i, map);
//			map = rtn.get(i);
//			rtn.set(i, map);
		}
		
//		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		LOGGER.debug("rtnList2 ::::::::::::" + rtnList2.toString());
		
//		return rtn;
		return rtnList2;
	}
	
	/**
	 * @Method명   : selectApraiList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 평가자목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectApraiList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectApraiList(paramMap);
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : araiInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 평가자추가
	 */
	@Override
	public Map<String, String> araiInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmApraiAdding");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		LOGGER.debug("dmOutcomeDetailMap ::::::::::::" + dmOutcomeDetailMap.toString());
		
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		dscsnAbilitMngMapper.InsertArai(dmOutcomeDetailMap); // 평가자관리상세(AYC210)
				
		return null;
	}
	
	/**
	 * @Method명   : mngrApraiDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 관리자평가자 삭제
	 */
	@Override
	public Map<String, String> mngrApraiDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmDelete");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.DeleteMngrAprai(dmOutcomeDetailMap); // AYC210
				
		return null;
	}
	
	/**
	 * @Method명	 : selectMngrApraiUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 08. 
	 * @Method설명 : 관리자평가자수정 조회
	 */
	@Override
	public Map<String,Object> selectMngrApraiUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectMngrApraiUpdate(paramMap);
	}
	
	/**
	 * @Method명   : mngrApraiUpdate
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 08. 
	 * @Method설명 : 관리자평가자 수정
	 */
	@Override
	public Map<String, String> mngrApraiUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmMngrApraiUpdate");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
						
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateMngrAprai(dmOutcomeDetailMap); // AYC210
				
		return null;
	}
	
	/**
	 * @Method명	 : selectEvlMfcmmApraiMngBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 13. 
	 * @Method설명 : 본원평가위원평가자관리기본정보 조회
	 */
	@Override
	public Map<String,Object> selectEvlMfcmmApraiMngBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEvlMfcmmApraiMngBassInfo(paramMap);		
	}
	
	/**
	 * @Method명   : selectEvlMfcmmApraiMngBassInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 본원평가위원평가자관리기본정보 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlMfcmmApraiMngBassInfoList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlMfcmmApraiMngBassInfoList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlSeMngInq
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 평가서관리 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlSeMngInq(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlSeMngInq(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectEvlSeMngSeInq
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 평가서관리구분 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlSeMngSeInq(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
//		List<Map<String, Object>> rtnList = new ArrayList<>();
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEvlSeMngSeInq(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());		
		
		return rtn;
	}

	/**
	 * @Method명   : selectApraiIdnty
	 * @return	   : map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 상담원 - 평가대상 여부 조회
	 */
	@Override
	public Map<String, Object> selectApraiIdnty(HttpServletRequest request) throws Exception {

		String loginId;					// session 정보의 ID
		List<String> evfoMngSn;			// 평가지관리일련번호
		String apraiMngDtlSn;			// 평가자관리상세일련번호
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
			
		}
		
		// 현재 일자 기준으로 평가그룹이 '동료상담원' (EVL_TME_SE_CD = '02') 인 데이터중 평가시작일자와 평가종료일자 사이에 존재 여부 확인
		evfoMngSn = dscsnAbilitMngMapper.selectEvlExisteYn();
		LOGGER.debug("평가지관리일련번호 === " + evfoMngSn);
		LOGGER.debug("평가지관리일련번호 크기 === " + evfoMngSn.size());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// 조회된 평가지가 존재
		if (!evfoMngSn.isEmpty()) {
			
			if(evfoMngSn.size() == 1) {
				
				LOGGER.debug("평가지 한개");
				paramMap.put("EVFO_MNG_SN", evfoMngSn.get(0));
				paramMap.put("loginId", loginId); //
				
				// 조회된 평가지에 대해 평가자로 등록되어있는지 확인
				apraiMngDtlSn = dscsnAbilitMngMapper.selectApraiTrgtYn(paramMap);
				
				if (apraiMngDtlSn != null) {
					
					LOGGER.debug("정상");
					returnMap.put("RESULT_VAL", 1);
					returnMap.put("EVFO_MNG_SN", evfoMngSn);
					returnMap.put("APRAI_MNG_DTL_SN", apraiMngDtlSn);
					
				} else {
					
					LOGGER.debug("평가자가 아니다");
					returnMap.put("RESULT_VAL", 0);
					throw new AppWorksException("평가자가 아닙니다.", Alert.ERROR);
					
				}
				
			} else {
				
				LOGGER.debug("평가지 두개이상");
				returnMap.put("RESULT_VAL", 0);
				throw new AppWorksException("평가지가 2개이상 조회되었습니다.\n관리자한테 문의하세요.", Alert.ERROR);
				
			}
		
		// 조회된 평가지가 없다
		} else {
			
			LOGGER.debug("평가지 없다");
			returnMap.put("RESULT_VAL", 0);
			throw new AppWorksException("평가기간이 아닙니다.", Alert.ERROR);
			
		}
		
		return returnMap;
	}

	/**
	 * @Method명   : selectEvlWrtTrprList
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 평가서 작성 대상자 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectEvlWrtTrprList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		// 1. AYC200, AYC210 과 AYC230을 사용해서 평가항목 배열과 평가대상자 목록을 가져온다.
		List<Map<String, Object>> baseEvlWrtList = dscsnAbilitMngMapper.selectBassEvlWrtList(paramMap);
		LOGGER.debug("baseEvlWrtList === " + baseEvlWrtList);
		
		// 2. 위에서 조회한 List를 가지고 FOR문을 돌려 새로운 List 생성
		List<Map<String, Object>> chgEvlWrtList = new ArrayList<Map<String,Object>>(); 
		
		for (Map<String, Object> map : baseEvlWrtList) {
			
			String[] srvyPrvsonArr = ((String) map.get("PRVSON_NM")).split(",");
 			LOGGER.debug("srvyPrvsonArr 길이 === " + srvyPrvsonArr.length);
			
			for (int i = 1; i <= srvyPrvsonArr.length; i++) {
				Map<String, Object> basketMap = new HashMap<String, Object>();
				
				basketMap.putAll(map);
				basketMap.put("SRVY_KND_SE_CD", srvyPrvsonArr[(i - 1)]);
				LOGGER.debug("새로운 MAP === " + basketMap);
				
				// 3. 새로운 Map를 AYC280에 존재하는지 조회
				chgEvlWrtList.add(dscsnAbilitMngMapper.selectDtlEvlWrtList(basketMap));
				
			}
			
		}
		
		LOGGER.debug("새로운 List === " + chgEvlWrtList);
		
		// 4. return된 List를 화면에 던짐
		
		return chgEvlWrtList;
	}

	
	/**
	 * @Method명	 : selectRelevaEvlBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 9. 14. 
	 * @Method설명 : 수행 적절성평가표 기본정보 조회
	 */
	@Override
	public Map<String,Object> selectRelevaEvlBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		Map<String, Object> dscsnInfoMap = dscsnAbilitMngMapper.selectRelevaEvlBassInfo(paramMap);
		
		return dscsnInfoMap;
	}
	
	/**
	 * @Method명   : selectRelevaEvlList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 : 수행 적절성평가표 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectRelevaEvlList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectRelevaEvlList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : evlMngSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 : 평가관리 저장
	 */
	@Override
	public Map<String, String> evlMngSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmRelevaEvlBassInfo");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
						
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		dmOutcomeDetailMap.put("DSCSN_DT", dmOutcomeDetailMap.get("DSCSN_YMD") + dmOutcomeDetailMap.get("DSCSN_TIME"));
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateEvlMng(dmOutcomeDetailMap); // AYC280
				
		return null;
	}
	
	/**
	 * @Method명   : exclncCaseMmSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 우수사례양식 저장
	 */
	@Override
	public Map<String, String> exclncCaseMmSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
						
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("우수사례양식 저장 ::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateExclncCaseMm(dmOutcomeDetailMap); // AYC290
				
		return null;
	}

	/**
	 * @Method명   : exclncCaseMmDelete
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 : 우수사례 제출 자료 삭제
	 */
	@Override
	public int exclncCaseMmDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId = "";		// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramMap = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramMap.getSingleValueMap();
		
		searchMap.put("USER_ID", loginId);
		
		return dscsnAbilitMngMapper.exclncCaseMmDelete(searchMap);
		
	}
	
	/**
	 * @Method명   : superVisionMmSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 수퍼비전양식 저장
	 */
	@Override
	public Map<String, String> superVisionMmSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
						
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("수퍼비전양식 저장 ::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateSuperVisionMm(dmOutcomeDetailMap); // AYC295
				
		return null;
	}

	/**
	 * @Method명   : superVisionMmDelete
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 수퍼비전 제출 자료 삭제
	 */
	@Override
	public int superVisionMmDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";		// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramMap = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramMap.getSingleValueMap();
		
		searchMap.put("USER_ID", loginId);
		
		return dscsnAbilitMngMapper.superVisionMmDelete(searchMap);
		
	}

	/**
	 * @Method명   : evlMngListInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 : 평가관리목록 저장	
	 */	
	@Override
	public List<Map<String, String>> evlMngListInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dsRelevaEvlList");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		
		ParameterGroup params = dataRequest.getParameterGroup("dsRelevaEvlList");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 ::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsRelevaEvlList");
		List<Map<String, String>> dsRelevaEvlList = params.getAllRowList();
		
		LOGGER.debug("dsRelevaEvlList 111 ::::::::::: " + dsRelevaEvlList.toString());
		
		for(int i=0; i<dsRelevaEvlList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsRelevaEvlList.get(i).get("CHK").toString() );
			
			if("false".equals(dsRelevaEvlList.get(i).get("CHK").toString()) ) {
				
				LOGGER.debug("CHKLST_QUEST_SN 111::::::::::: " + dsRelevaEvlList.get(i).get("CHKLST_QUEST_SN").toString());
				LOGGER.debug("CHKLST_RSPNS_SN 222::::::::::: " + dsRelevaEvlList.get(i).get("CHKLST_RSPNS_SN").toString());
				
//				continue;
				dsRelevaEvlList.get(i).put("CHKLST_QUEST_SN", dsRelevaEvlList.get(i).get("CHKLST_QUEST_SN").toString());
				dsRelevaEvlList.get(i).put("CHKLST_RSPNS_SN", dsRelevaEvlList.get(i).get("CHKLST_RSPNS_SN").toString());
				
				dsRelevaEvlList.get(i).put("FRST_RGTR_ID", sUserId);			
				dsRelevaEvlList.get(i).put("LAST_MDFR_ID", sUserId);
				try {
					dscsnAbilitMngMapper.UpdateEvlMngListDelYn(dsRelevaEvlList.get(i)); // AYC265
				} catch (Exception e) {
					LOGGER.debug("AYC265 ::::::::::: " + e.getMessage());
				}
				
			}
			if("true".equals(dsRelevaEvlList.get(i).get("CHK").toString()) ) {
				
				LOGGER.debug("8888888888888888888::::::::::: " + dmOutcomeDetailMap.toString());
				LOGGER.debug("9999999999999999999::::::::::: " + dsRelevaEvlList.toString());
				
				LOGGER.debug("EVL_TRGT_SN 111::::::::::: " + dsRelevaEvlList.get(i).get("EVL_TRGT_SN").toString());
				LOGGER.debug("EVL_MNG_SN 111::::::::::: " + dsRelevaEvlList.get(i).get("EVL_MNG_SN").toString());
				dsRelevaEvlList.get(i).put("EVL_TRGT_SN", dsRelevaEvlList.get(i).get("EVL_TRGT_SN").toString());
				dsRelevaEvlList.get(i).put("EVL_MNG_SN", dsRelevaEvlList.get(i).get("EVL_MNG_SN").toString());
				dsRelevaEvlList.get(i).put("FRST_RGTR_ID", sUserId);			
				dsRelevaEvlList.get(i).put("LAST_MDFR_ID", sUserId);
				try {
					dscsnAbilitMngMapper.UpdateEvlMngList(dsRelevaEvlList.get(i)); // AYC265
				} catch (Exception e) {
					LOGGER.debug("AYC265 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsRelevaEvlList.size(); i++) {	
		
		return null;	
	}
	
	/**
	 * @Method명   : mngrApraiMngCoListInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 평가자 동료상담원 저장
	 */	
	@Override
	public List<Map<String, String>> mngrApraiMngCoListInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup params = dataRequest.getParameterGroup("dsMngrApraiMngCoList");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 ::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsMngrApraiMngCoList");
		List<Map<String, String>> dsMngrApraiMngCoList = params.getAllRowList();
		
		LOGGER.debug("dsMngrApraiMngCoList 111 ::::::::::: " + dsMngrApraiMngCoList.toString());
		LOGGER.debug("size ::::::::::: " + dsMngrApraiMngCoList.size());
		
		for(int i=0; i<dsMngrApraiMngCoList.size(); i++) {
			
			LOGGER.debug("8888888888888888888::::::::::: " + dmOutcomeDetailMap.toString());
			LOGGER.debug("123456789::::::::::: " + dsMngrApraiMngCoList.toString());
				
				if(!dsMngrApraiMngCoList.get(i).get("EVL_CD1").isEmpty()) {		
					
//					LOGGER.debug("11111::::::::::: " + dsMngrApraiMngCoList.get(i).get("EVL_CD1"));
					
					dsMngrApraiMngCoList.get(i).put("FRST_RGTR_ID", sUserId);			
					dsMngrApraiMngCoList.get(i).put("LAST_MDFR_ID", sUserId);
					dsMngrApraiMngCoList.get(i).put("EVFO_MNG_SN",  dmOutcomeDetailMap.get("EVFO_MNG_SN").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_ID",  dsMngrApraiMngCoList.get(i).get("CONSTT_ID1").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_NM_ENCPT",  dsMngrApraiMngCoList.get(i).get("CONSTT_NM1").toString());
					dsMngrApraiMngCoList.get(i).put("EVL_GROUP_TYPE_SE_CD",  dsMngrApraiMngCoList.get(i).get("EVL_CD1").toString());
					
					dsMngrApraiMngCoList.get(i).put("APRAI_MNG_DTL_SN",  dsMngrApraiMngCoList.get(i).get("APRAI_MNG_DTL_SN1").toString());
					
					dscsnAbilitMngMapper.UpdateMngrApraiMngCoList(dsMngrApraiMngCoList.get(i)); // AYC210
				}
				if(!dsMngrApraiMngCoList.get(i).get("EVL_CD2").isEmpty()) {
					
//					LOGGER.debug("22222::::::::::: " + dsMngrApraiMngCoList.get(i).get("EVL_CD2"));
					
					dsMngrApraiMngCoList.get(i).put("FRST_RGTR_ID", sUserId);			
					dsMngrApraiMngCoList.get(i).put("LAST_MDFR_ID", sUserId);
					dsMngrApraiMngCoList.get(i).put("EVFO_MNG_SN",  dmOutcomeDetailMap.get("EVFO_MNG_SN").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_ID",  dsMngrApraiMngCoList.get(i).get("CONSTT_ID2").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_NM_ENCPT",  dsMngrApraiMngCoList.get(i).get("CONSTT_NM2").toString());
					dsMngrApraiMngCoList.get(i).put("EVL_GROUP_TYPE_SE_CD",  dsMngrApraiMngCoList.get(i).get("EVL_CD2").toString());
					
					dsMngrApraiMngCoList.get(i).put("APRAI_MNG_DTL_SN",  dsMngrApraiMngCoList.get(i).get("APRAI_MNG_DTL_SN2").toString());
					
					dscsnAbilitMngMapper.UpdateMngrApraiMngCoList(dsMngrApraiMngCoList.get(i)); // AYC210
				}
				if(!dsMngrApraiMngCoList.get(i).get("EVL_CD3").isEmpty()) {
					
//					LOGGER.debug("33333::::::::::: " + dsMngrApraiMngCoList.get(i).get("EVL_CD3"));
					
					dsMngrApraiMngCoList.get(i).put("FRST_RGTR_ID", sUserId);			
					dsMngrApraiMngCoList.get(i).put("LAST_MDFR_ID", sUserId);
					dsMngrApraiMngCoList.get(i).put("EVFO_MNG_SN",  dmOutcomeDetailMap.get("EVFO_MNG_SN").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_ID",  dsMngrApraiMngCoList.get(i).get("CONSTT_ID3").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_NM_ENCPT",  dsMngrApraiMngCoList.get(i).get("CONSTT_NM3").toString());
					dsMngrApraiMngCoList.get(i).put("EVL_GROUP_TYPE_SE_CD",  dsMngrApraiMngCoList.get(i).get("EVL_CD3").toString());
					
					dsMngrApraiMngCoList.get(i).put("APRAI_MNG_DTL_SN",  dsMngrApraiMngCoList.get(i).get("APRAI_MNG_DTL_SN3").toString());
					
					dscsnAbilitMngMapper.UpdateMngrApraiMngCoList(dsMngrApraiMngCoList.get(i)); // AYC210
				}
				if(!dsMngrApraiMngCoList.get(i).get("EVL_CD4").isEmpty()) {
					
//					LOGGER.debug("44444::::::::::: " + dsMngrApraiMngCoList.get(i).get("EVL_CD4"));
					
					dsMngrApraiMngCoList.get(i).put("FRST_RGTR_ID", sUserId);			
					dsMngrApraiMngCoList.get(i).put("LAST_MDFR_ID", sUserId);
					dsMngrApraiMngCoList.get(i).put("EVFO_MNG_SN",  dmOutcomeDetailMap.get("EVFO_MNG_SN").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_ID",  dsMngrApraiMngCoList.get(i).get("CONSTT_ID4").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_NM_ENCPT",  dsMngrApraiMngCoList.get(i).get("CONSTT_NM4").toString());
					dsMngrApraiMngCoList.get(i).put("EVL_GROUP_TYPE_SE_CD",  dsMngrApraiMngCoList.get(i).get("EVL_CD4").toString());
					
					dsMngrApraiMngCoList.get(i).put("APRAI_MNG_DTL_SN",  dsMngrApraiMngCoList.get(i).get("APRAI_MNG_DTL_SN4").toString());
					
					dscsnAbilitMngMapper.UpdateMngrApraiMngCoList(dsMngrApraiMngCoList.get(i)); // AYC210
				}
				if(!dsMngrApraiMngCoList.get(i).get("EVL_CD5").isEmpty()) {
					
//					LOGGER.debug("55555::::::::::: " + dsMngrApraiMngCoList.get(i).get("EVL_CD5"));
					
					dsMngrApraiMngCoList.get(i).put("FRST_RGTR_ID", sUserId);			
					dsMngrApraiMngCoList.get(i).put("LAST_MDFR_ID", sUserId);
					dsMngrApraiMngCoList.get(i).put("EVFO_MNG_SN",  dmOutcomeDetailMap.get("EVFO_MNG_SN").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_ID",  dsMngrApraiMngCoList.get(i).get("CONSTT_ID5").toString());
					dsMngrApraiMngCoList.get(i).put("APRAI_NM_ENCPT",  dsMngrApraiMngCoList.get(i).get("CONSTT_NM5").toString());
					dsMngrApraiMngCoList.get(i).put("EVL_GROUP_TYPE_SE_CD",  dsMngrApraiMngCoList.get(i).get("EVL_CD5").toString());
					
					dsMngrApraiMngCoList.get(i).put("APRAI_MNG_DTL_SN",  dsMngrApraiMngCoList.get(i).get("APRAI_MNG_DTL_SN5").toString());
					
					dscsnAbilitMngMapper.UpdateMngrApraiMngCoList(dsMngrApraiMngCoList.get(i)); // AYC210
				}
		
		} // end for(int i=0; i<dsMngrApraiMngCoList.size(); i++) {	
		
		return null;	
	}
	
	/**
	 * @Method명   : selectEvlScoreMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 19. 
	 * @Method설명 : 평가점수관리 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectEvlScoreMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";					// session정보의 ID
		String userGroupAuthrtSeCd = "";		// session정보의 그룹권한구분코드
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getGroupAuthrtSeCd() != null && !"".equals(loginVO.getGroupAuthrtSeCd())) {
			
			userGroupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();
			
		} else {
			
			throw new AppWorksException("세션 정보(그룹권한구분코드)가 없습니다.", Alert.ERROR);
			
		}
		
		LOGGER.debug("종사자 역할 구분 코드 ===" + userGroupAuthrtSeCd);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보(ID)가 없습니다.", Alert.ERROR);
			
		}
		
		LOGGER.debug("SESSION ID ===" + loginId);
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("loginId", loginId);
		
		if ("340".equals(userGroupAuthrtSeCd)) {
			paramMap.put("IS_ADMIN", "N");
		} else {
			paramMap.put("IS_ADMIN", "Y");
		}
		
		rtn = dscsnAbilitMngMapper.selectEvlScoreMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectOgdpInstList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 : 소속기관 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectOgdpInstList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String instNo;		//	기관번호
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		
		// session정보에서 기관번호 가져오기 - Jeong.Won.Je
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getInstNo() != null) {
			
			instNo = loginVO.getInstNo().toString();
			LOGGER.debug("instNo === " + instNo);
			
		} else {
			throw new AppWorksException("소속된 기관이 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("INST_NO", instNo);
		
		rtn = dscsnAbilitMngMapper.selectOgdpInstList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectExclncCaseMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 : 우수사례관리 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectExclncCaseMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";				// 사용자 ID
		String userGroupAuthrtSeCd = "";	// 사용자 그룹권한구분코드
		
		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		// 사용자 ID 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("사용자 ID가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("loginId", loginId);
		
		userGroupAuthrtSeCd = paramMap.get("GROUP_AUTHRT_SE_CD");
		if ("340".equals(userGroupAuthrtSeCd)) {
			paramMap.put("IS_ADMIN", "N");
		} else {
			paramMap.put("IS_ADMIN", "Y");
		}
		
		rtn = dscsnAbilitMngMapper.selectExclncCaseMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSuperVisionMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 수퍼비전관리 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSuperVisionMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId;			// 사용자 ID
		String userGroupAuthrtSeCd = "";	// 사용자 그룹권한구분코드

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		// 사용자 정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("사용자 ID가 없습니다.", Alert.ERROR);
			
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("loginId", loginId);
		
		userGroupAuthrtSeCd = paramMap.get("GROUP_AUTHRT_SE_CD");
		if ("340".equals(userGroupAuthrtSeCd)) {
			paramMap.put("IS_ADMIN", "N");
		} else {
			paramMap.put("IS_ADMIN", "Y");
		}
		
		rtn = dscsnAbilitMngMapper.selectSuperVisionMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectExclncCaseConsttList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 : 우수사례관리 상담자 조회
	 */	
	@Override
	public List<Map<String, Object>> selectExclncCaseConsttList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectExclncCaseConsttList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSuperVisionConsttList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 수퍼비전관리 상담자 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSuperVisionConsttList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectSuperVisionConsttList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : exclncCaseConsttInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 : 우수사례관리 상담자 저장	
	 */	
	@Override
	public List<Map<String, String>> exclncCaseConsttInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 ::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsExclncCaseConsttList");
		
		List<Map<String, String>> dsExclncCaseConsttList = params.getAllRowList();
		
		LOGGER.debug("dsEvlTrprSlctnList 111 ::::::::::: " + dsExclncCaseConsttList.toString());
		
		for(int i=0; i<dsExclncCaseConsttList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsExclncCaseConsttList.get(i).get("CHK").toString() );
			
			if("".equals(dsExclncCaseConsttList.get(i).get("CHK").toString()) ) {
				continue;
			}
			if("true".equals(dsExclncCaseConsttList.get(i).get("CHK").toString()) ) {
				
				LOGGER.debug("dsEvlTrprSlctnList 222 ::::::::::: " + dsExclncCaseConsttList.toString());
				
				dmOutcomeDetailMap.put("CNSLTNT_ID",  dsExclncCaseConsttList.get(i).get("CONSTT_ID").toString());
				dmOutcomeDetailMap.put("DEPT_CD", dsExclncCaseConsttList.get(i).get("DEPT_CD").toString());
				dmOutcomeDetailMap.put("RLVT_YR",  dmOutcomeDetailMap.get("RLVT_YR"));
							
				try {
					dscsnAbilitMngMapper.insertExclncCaseConstt(dmOutcomeDetailMap); // AYC290
				} catch (Exception e) {
					LOGGER.debug("AYC290 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsExclncCaseConsttList.size(); i++) {	
		
		return null;	
	}
	
	/**
	 * @Method명   : superVisionConsttInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 21. 
	 * @Method설명 : 수퍼비전관리 상담자 저장
	 */	
	@Override
	public List<Map<String, String>> superVisionConsttInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 ::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsSuperVisionConsttList");
		List<Map<String, String>> dsSuperVisionConsttList = params.getAllRowList();
		
		LOGGER.debug("dsEvlTrprSlctnList 111 ::::::::::: " + dsSuperVisionConsttList.toString());
		
		for(int i=0; i<dsSuperVisionConsttList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsSuperVisionConsttList.get(i).get("CHK").toString() );
			
			if("".equals(dsSuperVisionConsttList.get(i).get("CHK").toString()) ) {
				continue;
			}
			if("true".equals(dsSuperVisionConsttList.get(i).get("CHK").toString()) ) {
				
				LOGGER.debug("dsEvlTrprSlctnList 222 ::::::::::: " + dsSuperVisionConsttList.toString());
				
				dmOutcomeDetailMap.put("CNSLTNT_ID",  dsSuperVisionConsttList.get(i).get("CONSTT_ID").toString()); 
				dmOutcomeDetailMap.put("DEPT_CD", dsSuperVisionConsttList.get(i).get("DEPT_CD").toString());
				dmOutcomeDetailMap.put("RLVT_YR",  dmOutcomeDetailMap.get("RLVT_YR"));
							
				try {
					dscsnAbilitMngMapper.insertSuperVisionConstt(dmOutcomeDetailMap); // AYC295
				} catch (Exception e) {
					LOGGER.debug("AYC295 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsSuperVisionConsttList.size(); i++) {	
		
		return null;	
	}
	
	/**
	 * @Method명   : processEnfoMngExcelUpload
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 27. 
	 * @Method설명 : 평자지관리 엑셀업로드
	 */
	@Override		
	public List<Map<String, String>> processEnfoMngExcelUpload(HttpServletRequest request, DataRequest dataRequest) throws Exception {	

		String sUserId       = "";	// 세션정보의 유저ID
//		Map<String, Object> subMap01 = new HashMap<>();
//		Map<String, Object> subMap02 = new HashMap<>();
		
//		BigDecimal bMngSn250 	= new BigDecimal("0"); 	// 체크리스트관리일련번호(AYC250)
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId  	 = loginVO.getId();			
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		params = dataRequest.getParameterGroup("dsExcelUpload");
		List<Map<String, String>> dsExcelList = params.getAllRowList();

		int iQuestNo      = 1;   // 질의번호
		String sMsnSn     = "";  // 평가지관리일련번호
		String sCol       = "";    // 항목명
		String currSeq    = "";
		String preSeq     = "";
		String sChklstMsnSn = ""; // 체크리스트관리일련번호
		String sChklstMsnSnResult = ""; // 체크리스트관리일련번호
//		String sqnceSeq    = "";
//		String preSqnceSeq = "";
		String iGroupSn = "";
		
		LOGGER.debug("size ::::::::::: " + dsExcelList.size());
		
		for(int i=0; i<dsExcelList.size(); i++) {
			
			LOGGER.debug("dsExcelList ::::::::::: " + dsExcelList.get(i).toString());
			
			sMsnSn = dsExcelList.get(i).get("EVFO_MNG_SN").toString();
			sCol = dsExcelList.get(i).get("PIVOT_COL").toString();
			
			LOGGER.debug("sMsnSn ::::::::::: " + sMsnSn);
			LOGGER.debug("sCol ::::::::::: " + sCol);
//			LOGGER.debug("EVFO_MNG_SN ::::::::::: " + dmSearch.get("EVFO_MNG_SN"));
			
			try {	
				
				currSeq = dsExcelList.get(i).get("SRTNG_SQNCE").toString();
				if(i != 0) {
					preSeq = dsExcelList.get(i-1).get("SRTNG_SQNCE").toString();
				}
				
				if(i==0) {
					LOGGER.debug("i==0 ::::::::::::: ");
					
					Map<String, String> seqMap2 = new HashMap<>();
					// 체크리스트관리일련번호(AYC250) 구하기
					seqMap2.put("EVFO_MNG_SN", sMsnSn);
					seqMap2.put("PRVSON_NM", sCol);
					sChklstMsnSn = dscsnAbilitMngMapper.selectSn250(seqMap2);
					LOGGER.debug("체크리스트관리일련번호 0000==>> " + sChklstMsnSn);
					
					// AYC255
					dsExcelList.get(i).put("CHKLST_MNG_SN",  sChklstMsnSn); // 체크리스트관리일련번호
					dsExcelList.get(i).put("CHKLST_SE_CD",  dsExcelList.get(i).get("CHKLST_SE_CD").toString()); // 체크리스트구분코드
					dsExcelList.get(i).put("ABILIT_CN",  dsExcelList.get(i).get("ABILIT_CN").toString()); // 역량내용
//					dsExcelList.get(i).put("ABILIT_ALLOT_SCORE",  ???); // 역량배점점수
					dsExcelList.get(i).put("CRT_ID",  sUserId); // 생성아이디
					dsExcelList.get(i).put("MDFR_ID",  sUserId); // 수정아이디
					dsExcelList.get(i).put("ADSBTR_SE_YN",  dsExcelList.get(i).get("ADSBTR_SE_YN").toString()); // 가감구분여부
					dsExcelList.get(i).put("SRTNG_SQNCE",  dsExcelList.get(i).get("SRTNG_SQNCE").toString()); // 정렬순서
					dsExcelList.get(i).put("FRST_RGTR_ID",  sUserId); // 생성아이디
					dsExcelList.get(i).put("LAST_MDFR_ID",  sUserId); // 생성아이디
					
					LOGGER.debug("결과255 111::::::::::: " + dsExcelList.get(i));				
					
					dscsnAbilitMngMapper.insertChklstGroup(dsExcelList.get(i));
//					iGroupSn = dscsnAbilitMngMapper.insertChklstGroup(dsExcelList.get(i)); // AYC255 체크리스트그룹저장
					iGroupSn = dsExcelList.get(i).get("CHKLST_GROUP_SN");
					
					LOGGER.debug("iGroupSn::::::::::: " + iGroupSn);
					LOGGER.debug("CHKLST_GROUP_SN :::::::::: " + dsExcelList.get(i).get("CHKLST_GROUP_SN"));
					
				}else if(currSeq.equals(preSeq)) {	
					LOGGER.debug("continue ::::::::::::: ");
//					continue;					
				}else if(!currSeq.equals(preSeq)) {		
					
					LOGGER.debug("AYC255 시작 ::::::::::::: " + currSeq + "::::::::::" + preSeq);
					LOGGER.debug("체크리스트관리일련번호 1111==>> " + sChklstMsnSn);
					LOGGER.debug("체크리스트관리일련번호 2222==>> " + sChklstMsnSnResult);
//					Map<String, String> seqMap2 = new HashMap<>();
//					// 체크리스트관리일련번호(AYC250) 구하기
//					seqMap2.put("EVFO_MNG_SN", sMsnSn);
//					seqMap2.put("PRVSON_NM", sCol);
//					String sChklstMsnSn = dscsnAbilitMngMapper.selectSn250(seqMap2);
//					LOGGER.debug("체크리스트관리일련번호 ==>> " + sChklstMsnSn);
					
					// AYC255
					dsExcelList.get(i).put("CHKLST_MNG_SN",  sChklstMsnSnResult); // 체크리스트관리일련번호
					dsExcelList.get(i).put("CHKLST_SE_CD",  dsExcelList.get(i).get("CHKLST_SE_CD").toString()); // 체크리스트구분코드
					dsExcelList.get(i).put("ABILIT_CN",  dsExcelList.get(i).get("ABILIT_CN").toString()); // 역량내용
//					dsExcelList.get(i).put("ABILIT_ALLOT_SCORE",  ???); // 역량배점점수
					dsExcelList.get(i).put("CRT_ID",  sUserId); // 생성아이디
					dsExcelList.get(i).put("MDFR_ID",  sUserId); // 수정아이디
					dsExcelList.get(i).put("ADSBTR_SE_YN",  dsExcelList.get(i).get("ADSBTR_SE_YN").toString()); // 가감구분여부
					dsExcelList.get(i).put("SRTNG_SQNCE",  dsExcelList.get(i).get("SRTNG_SQNCE").toString()); // 정렬순서
					dsExcelList.get(i).put("FRST_RGTR_ID",  sUserId); // 생성아이디
					dsExcelList.get(i).put("LAST_MDFR_ID",  sUserId); // 생성아이디
//					dsExcelList.get(i).put("CHKLST_GROUP_SN", "");
					
					LOGGER.debug("결과255 222::::::::::: " + dsExcelList.get(i));		
					
					dscsnAbilitMngMapper.insertChklstGroup(dsExcelList.get(i));
//					iGroupSn = dscsnAbilitMngMapper.insertChklstGroup(dsExcelList.get(i)); // AYC255 체크리스트그룹저장
					iGroupSn = dsExcelList.get(i).get("CHKLST_GROUP_SN");
					
					LOGGER.debug("iGroupSn 9999 ::::::::::: " + iGroupSn);
					LOGGER.debug("CHKLST_GROUP_SN :::::::::: " + dsExcelList.get(i).get("CHKLST_GROUP_SN"));
					
				}
				
				currSeq = dsExcelList.get(i).get("SRTNG_SQNCE").toString();
				if(i != 0) {
					preSeq = dsExcelList.get(i-1).get("SRTNG_SQNCE").toString();
				}
				
				LOGGER.debug("currSeq ::::::::::: " + currSeq);
				LOGGER.debug("preSeq ::::::::::: " + preSeq);
						
				if(i==0) {
					dsExcelList.get(i).put("QUEST_NO",  "1"); // 질의번호					
				}else if(currSeq.equals(preSeq)) {					
					iQuestNo++;
					dsExcelList.get(i).put("QUEST_NO",  String.valueOf(iQuestNo)); // 질의번호								
				}else if(!currSeq.equals(preSeq)) {					
					dsExcelList.get(i).put("QUEST_NO",  "1"); // 질의번호	
					iQuestNo = 1;   // 질의번호
				}
				
				LOGGER.debug("iGroupSn 11111111111 ::::::::::: " + iGroupSn);
				dsExcelList.get(i).put("CHKLST_GROUP_SN",  String.valueOf(iGroupSn)); // 체크리스트그룹일련번호
				
				dsExcelList.get(i).put("CHKLST_SCORE",  dsExcelList.get(i).get("CHKLST_SCORE").toString()); // 체크리스트점수
				dsExcelList.get(i).put("CHKLST_CN",  dsExcelList.get(i).get("CHKLST_CN").toString()); // 체크리스트내용
				dsExcelList.get(i).put("ADDTNG_SCORE_YN",  dsExcelList.get(i).get("ADDTNG_SCORE_YN").toString()); // 추가점수여부
				dsExcelList.get(i).put("CRT_ID",  sUserId); 
				dsExcelList.get(i).put("FRST_RGTR_ID",  sUserId); // 생성아이디
				dsExcelList.get(i).put("LAST_MDFR_ID",  sUserId); // 생성아이디
				
				LOGGER.debug("결과260 ::::::::::: " + dsExcelList.get(i));
				
				dscsnAbilitMngMapper.insertChklstQuest(dsExcelList.get(i)); // AYC260 체크리스트질의저장
				
				sChklstMsnSnResult = sChklstMsnSn;
				
			} catch (Exception e) {				
				LOGGER.debug("AKA255/AKA260 ::::::::::: " + e.getMessage());
			}
			
		} // end for(int i=0; i<dsExcelList.size(); i++) {	
		
		return null;	
	}

	/**
	 * @Method명   : selectExclncModeInfo
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectExclncModeInfo() throws Exception {
		
		Map<String, String> returnMap = new HashMap<String, String>();
		
		returnMap.put("ATFINO", dscsnAbilitMngMapper.selectExclncModeInfo());
		
		LOGGER.debug("우수사례 양식 첨부파일 번호 === " + returnMap);
		
		return returnMap;
		
	}
	

	/**
	 * @Method명   : selectSuperVisionModeInfo
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectSuperVisionModeInfo() throws Exception {
		
		Map<String, String> returnMap = new HashMap<String, String>();
		
		returnMap.put("ATFINO", dscsnAbilitMngMapper.selectSuperVisionModeInfo());
		
		LOGGER.debug("수퍼비전 양식 첨부파일 번호 === " + returnMap);
		
		return returnMap;
		
	}
	
//-------------------------------------------------------------------------------------------------------------------------------
// 관리자 - 상담원 역량관리 - 교육관리
//-------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @Method명   : selectEduHstrList
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 업무지원 - 교육이력 및 수료증 출력 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectEduHstrList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId = null;				// session 정보의 ID
		String userGroupAuthrtSeCd = "";	// session 정보의 그룹권한구분코드
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && !"".equals(loginVO.getId()) && loginVO.getId() != null
				&& !"".equals(loginVO.getGroupAuthrtSeCd()) && loginVO.getGroupAuthrtSeCd() != null) {
			
			loginId = loginVO.getId();
			userGroupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();
			
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramGroup.getSingleValueMap();
		
		searchMap.put("loginId", loginId);
		
		if ("340".equals(userGroupAuthrtSeCd)) {
			searchMap.put("IS_ADMIN", "N");
		} else {
			searchMap.put("IS_ADMIN", "Y");
		}
		
		LOGGER.debug("searchMap === " + searchMap);
		
		List<Map<String, Object>> returnList = dscsnAbilitMngMapper.selectEduHstrList(searchMap);
		
		for (Map<String, Object> map : returnList) {
			
			if (map.get("FLNM_ENCPT") != null) {
				
				map.put("NM_DECPT", map.get("FLNM_ENCPT"));
				
			}
			
		}
		
		return returnList;
	}

	/**
	 * @Method명   : updateEduCtcplNo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 :
	 */
	@Override
	public int updateEduCtcplNo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId;			// sesseion정보의 ID
		int rtnVal;				// update 성공 여부
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		// 교육개설일련번호를 넘겨 MAX 이수증출력번호 조회
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEduHstrInfo");
		Map<String, String> eduHstrInfoMap = paramGroup.getSingleValueMap();
		
		String eduEstblSn = eduHstrInfoMap.get("EDU_ESTBL_SN");
		
		int maxCtcplNo = dscsnAbilitMngMapper.selectMaxCtcplNo(eduEstblSn);
		LOGGER.debug("MAX 이수증출력번호 ==== " + maxCtcplNo);
		
		if (maxCtcplNo > 0) {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.putAll(eduHstrInfoMap);
			paramMap.put("CTCPL_OTPT_NO", maxCtcplNo);
			paramMap.put("loginId", loginId);
			
			rtnVal = dscsnAbilitMngMapper.updateEduCtcplNo(paramMap);
			
		} else {
			
			throw new AppWorksException("조회된 이수증출력번호가 잘못되었습니다.", Alert.ERROR);
			
		}
		
		return rtnVal;
	}

	/**
	 * @Method명   : selectEduMngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 19. 
	 * @Method설명 : 교육관리 조회
	 */	
	@Override
	public List<Map<String, Object>> selectEduMngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectEduMngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}

	/**
	 * @Method명   : selectCreateCyberDscsnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 : 사이버상담 교육인원 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCreateCyberDscsnList(DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> rtn = dscsnAbilitMngMapper.selectCyberDscsnList();
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCreateMblaDscsnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 : 모바일상담 교육인원 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCreateMblaDscsnList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = dscsnAbilitMngMapper.selectMblaDscsnList();
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : insertEduMngSave
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 :
	 */
	@Override
	public int insertEduMngSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId;			//	session정보의 ID
		String sEduEstblSn; 	// 교육개설일련번호(EDU_ESTBL_SN)
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEduMngBassInfo");
		Map<String, String> eduInfoMap = paramGroup.getSingleValueMap();
		
		eduInfoMap.put("FRST_RGTR_ID", loginId);
		eduInfoMap.put("LAST_MDFR_ID", loginId);
		
		dscsnAbilitMngMapper.insertEduInfo(eduInfoMap);
		
		sEduEstblSn = eduInfoMap.get("ESTBL_SN");
		LOGGER.debug("ESTBL_SN ==== " + sEduEstblSn);
		
		// 화면에서 받아온 파라미터 (사이버상담 참석 / 사이버상담 미참석 / 모바일상담 참석 / 모바일상담 미참석)
		ParameterGroup dsCyberNonAtndGroup = dataRequest.getParameterGroup("dsCyberNonAtndList");
		List<Map<String, String>> dsCyberNonAtndList = dsCyberNonAtndGroup.getAllRowList();
		
		LOGGER.debug("사이버 상담 미참석 인원수 === " + dsCyberNonAtndList.size());
		LOGGER.debug("사이버 상담 미참석 인원 리스트 === " + dsCyberNonAtndList);
		
		if (dsCyberNonAtndList.size() > 0 ) {
			
			for (Map<String, String> map : dsCyberNonAtndList) {
				map.replace("EDU_ESTBL_SN", sEduEstblSn);
				map.replace("EDU_ATND_YN", "N");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		ParameterGroup dsCyberAtndGroup = dataRequest.getParameterGroup("dsCyberAtndList");
		List<Map<String, String>> dsCyberAtndList = dsCyberAtndGroup.getAllRowList();
		
		LOGGER.debug("사이버 상담 참석 인원수 === " + dsCyberAtndList.size());
		LOGGER.debug("사이버 상담 참석 인원 리스트 === " + dsCyberAtndList);
		
		if (dsCyberAtndList.size() > 0) {
			
			for (Map<String, String> map : dsCyberAtndList) {
				map.replace("EDU_ESTBL_SN", sEduEstblSn);
				map.replace("EDU_ATND_YN", "Y");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		ParameterGroup dsMobileNonAtndGroup = dataRequest.getParameterGroup("dsMblaNonAtndList");
		List<Map<String, String>> dsMobileNonAtndList = dsMobileNonAtndGroup.getAllRowList();
		
		LOGGER.debug("사이버 상담 미참석 인원수 === " + dsMobileNonAtndList.size());
		LOGGER.debug("사이버 상담 미참석 인원 리스트 === " + dsMobileNonAtndList);
		
		if (dsMobileNonAtndList.size() > 0) {
			
			for (Map<String, String> map : dsMobileNonAtndList) {
				map.replace("EDU_ESTBL_SN", sEduEstblSn);
				map.replace("EDU_ATND_YN", "N");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		
		ParameterGroup dsMobileAtndGroup = dataRequest.getParameterGroup("dsMblaAtndList");
		List<Map<String, String>> dsMobileAtndList = dsMobileAtndGroup.getAllRowList();
		
		LOGGER.debug("모바일 상담 참석 인원수 === " + dsMobileAtndList.size());
		LOGGER.debug("모바일 상담 참석 인원 리스트 === " + dsMobileAtndList);
		
		if (dsMobileAtndList.size() > 0) {
			
			for (Map<String, String> map : dsMobileAtndList) {
				map.replace("EDU_ESTBL_SN", sEduEstblSn);
				map.replace("EDU_ATND_YN", "Y");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		return 0;
		
	}

	/**
	 * @Method명   : updateEduMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 :
	 */
	@Override
	public int updateEduMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId;					// session정보의 ID
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEduMngBassInfo");
		Map<String, String> eduInfoMap = paramGroup.getSingleValueMap();
		
		eduInfoMap.put("LAST_MDFR_ID", loginId);
		
		dscsnAbilitMngMapper.updateEduInfo(eduInfoMap);
		
		// 화면에서 받아온 파라미터 (사이버상담 참석 / 사이버상담 미참석 / 모바일상담 참석 / 모바일상담 미참석)
		ParameterGroup dsCyberNonAtndGroup = dataRequest.getParameterGroup("dsCyberNonAtndList");
		List<Map<String, String>> dsCyberNonAtndList = dsCyberNonAtndGroup.getAllRowList();
		
		LOGGER.debug("사이버 상담 미참석 인원수 === " + dsCyberNonAtndList.size());
		LOGGER.debug("사이버 상담 미참석 인원 리스트 === " + dsCyberNonAtndList);
		
		if (dsCyberNonAtndList.size() > 0 ) {
			
			for (Map<String, String> map : dsCyberNonAtndList) {
				map.replace("EDU_ATND_YN", "N");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		ParameterGroup dsCyberAtndGroup = dataRequest.getParameterGroup("dsCyberAtndList");
		List<Map<String, String>> dsCyberAtndList = dsCyberAtndGroup.getAllRowList();
		
		LOGGER.debug("사이버 상담 참석 인원수 === " + dsCyberAtndList.size());
		LOGGER.debug("사이버 상담 참석 인원 리스트 === " + dsCyberAtndList);
		
		if (dsCyberAtndList.size() > 0) {
			
			for (Map<String, String> map : dsCyberAtndList) {
				map.replace("EDU_ATND_YN", "Y");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		ParameterGroup dsMobileNonAtndGroup = dataRequest.getParameterGroup("dsMblaNonAtndList");
		List<Map<String, String>> dsMobileNonAtndList = dsMobileNonAtndGroup.getAllRowList();
		
		LOGGER.debug("사이버 상담 미참석 인원수 === " + dsMobileNonAtndList.size());
		LOGGER.debug("사이버 상담 미참석 인원 리스트 === " + dsMobileNonAtndList);
		
		if (dsMobileNonAtndList.size() > 0) {
			
			for (Map<String, String> map : dsMobileNonAtndList) {
				map.replace("EDU_ATND_YN", "N");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		
		ParameterGroup dsMobileAtndGroup = dataRequest.getParameterGroup("dsMblaAtndList");
		List<Map<String, String>> dsMobileAtndList = dsMobileAtndGroup.getAllRowList();
		
		LOGGER.debug("모바일 상담 참석 인원수 === " + dsMobileAtndList.size());
		LOGGER.debug("모바일 상담 참석 인원 리스트 === " + dsMobileAtndList);
		
		if (dsMobileAtndList.size() > 0) {
			
			for (Map<String, String> map : dsMobileAtndList) {
				map.replace("EDU_ATND_YN", "Y");
				map.put("FRST_RGTR_ID", loginId);
				map.put("LAST_MDFR_ID", loginId);
				
				dscsnAbilitMngMapper.UpdateEduMngSaveList(map);
			}
			
		} else {
			
		}
		
		return 0;
	}

	/**
	 * @Method명   : deleteEduMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 : 교육관리 & 교육참석자관리 Delete
	 */
	@Override
	public int deleteEduMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId;				// session정보의 ID
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = paramGroup.getSingleValueMap();
		
		dmSearch.put("LAST_MDFR_ID", loginId);
		
		dscsnAbilitMngMapper.deleteEduInfo(dmSearch);
		dscsnAbilitMngMapper.deleteEduAtndMng(dmSearch);
		
		return 0;
	}

	/**
	 * @Method명   : selectEduMngBassInfo
	 * @param	   : dataRequest
	 * @return	   : Map
	 * @throws 	   : Exception
	 * @작성자 	   : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 : 교육상세 - 교육정보 조회
	 */
	@Override
	public Map<String,Object> selectEduMngBassInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnAbilitMngMapper.selectEduMngBassInfo(paramMap);
	}

	/**
	 * @Method명   : selectCyberNonAtndList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육상세 - 사이버 미참석 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCyberNonAtndList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramGroup.getSingleValueMap();
		
		List<Map<String, Object>> resultList = dscsnAbilitMngMapper.selectCyberNonAtndList(searchMap);
		
		return resultList;
		
	}

	/**
	 * @Method명   : selectCyberAtndList
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육상세 - 사이버 참석 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCyberAtndList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramGroup.getSingleValueMap();
		
		List<Map<String, Object>> resultList = dscsnAbilitMngMapper.selectCyberAtndList(searchMap);
		
		return resultList;
		
	}

	/**
	 * @Method명   : selectMblaNonAtndList
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육상세 - 모바일 미참석 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectMblaNonAtndList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramGroup.getSingleValueMap();
		
		List<Map<String, Object>> resultList = dscsnAbilitMngMapper.selectMblaNonAtndList(searchMap);
		
		return resultList;
		
	}

	/**
	 * @Method명   : selectMblaAtndList
	 * @param 	   : dataRequest
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 교육상세 - 모바일 참석 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectMblaAtndList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = paramGroup.getSingleValueMap();
		
		List<Map<String, Object>> resultList = dscsnAbilitMngMapper.selectMblaAtndList(searchMap);
		
		return resultList;
		
	}

	/**
	 * @Method명   : eduMngSaveListInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 : 교육관리목록 저장	
	 */	
	@Override
	public List<Map<String, String>> eduMngSaveListInsert(HttpServletRequest request, DataRequest dataRequest, String sEduEstblSn) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dsCyberDscsnChcList");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup params = dataRequest.getParameterGroup("dsCyberDscsnChcList");
		
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("dmOutcomeDetailMap 111 목록::::::::::: " + dmOutcomeDetailMap.toString());
		
		params = dataRequest.getParameterGroup("dsCyberDscsnChcList");
		List<Map<String, String>> dsCyberDscsnChcList = params.getAllRowList();
		
		LOGGER.debug("dsCyberDscsnChcList 111 ::::::::::: " + dsCyberDscsnChcList.toString());
		
		for(int i=0; i<dsCyberDscsnChcList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsCyberDscsnChcList.get(i).get("CHK").toString() );
			
			if("false".equals(dsCyberDscsnChcList.get(i).get("CHK").toString()) ) {
				continue;
			}else if("true".equals(dsCyberDscsnChcList.get(i).get("CHK").toString()) ) {
 
				dsCyberDscsnChcList.get(i).put("FRST_RGTR_ID", sUserId);			
				dsCyberDscsnChcList.get(i).put("LAST_MDFR_ID", sUserId);
				dsCyberDscsnChcList.get(i).put("EDU_ESTBL_SN", sEduEstblSn);
				
				LOGGER.debug("777::::::::::: " + dsCyberDscsnChcList.toString());
				
				try {
					dscsnAbilitMngMapper.UpdateEduMngSaveList(dsCyberDscsnChcList.get(i)); // AYC195
				} catch (Exception e) {
					LOGGER.debug("AYC195 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsCyberDscsnChcList.size(); i++) {	

		params = dataRequest.getParameterGroup("dsMblaDscsnChcList");
		List<Map<String, String>> dsMblaDscsnChcList = params.getAllRowList();
		
		LOGGER.debug("dsMblaDscsnChcList 111 ::::::::::: " + dsMblaDscsnChcList.toString());
		
		for(int i=0; i<dsMblaDscsnChcList.size(); i++) {
			
			LOGGER.debug("chk ::::::::::: " + dsMblaDscsnChcList.get(i).get("CHK").toString() );
			
			if("false".equals(dsMblaDscsnChcList.get(i).get("CHK").toString()) ) {
				continue;
			}else if("true".equals(dsMblaDscsnChcList.get(i).get("CHK").toString()) ) {
 
				dsMblaDscsnChcList.get(i).put("FRST_RGTR_ID", sUserId);			
				dsMblaDscsnChcList.get(i).put("LAST_MDFR_ID", sUserId);
				dsMblaDscsnChcList.get(i).put("EDU_ESTBL_SN", sEduEstblSn);
				
				LOGGER.debug("777::::::::::: " + dsMblaDscsnChcList.toString());
				
				try {
					dscsnAbilitMngMapper.UpdateEduMngSaveList(dsMblaDscsnChcList.get(i)); // AYC195
				} catch (Exception e) {
					LOGGER.debug("AYC195 ::::::::::: " + e.getMessage());
				}
			}
			
		} // end for(int i=0; i<dsMblaDscsnChcList.size(); i++) {
		
		return null;	
	}
	
	/**
	 * @Method명   : eduMngSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 : 교육관리 저장
	 */
	@Override
	public Map<String, String> eduMngSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
//		 iEduEstblSn  = 0; // 교육개설일련번호
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEduMngBassInfo");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
						
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		dscsnAbilitMngMapper.UpdateEduMng(dmOutcomeDetailMap); // AYC190
		
		LOGGER.debug("dmOutcomeDetailMap 222::::::::::::" + dmOutcomeDetailMap.get("ESTBL_SN"));
		
		return dmOutcomeDetailMap;
	}
	
	/**
	 * @Method명   : selectMblaDscsnUpdateList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 : 모바일상담 교육인원 목록 조회
	 */	
	@Override
	public List<Map<String, Object>> selectMblaDscsnUpdateList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectMblaDscsnUpdateList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCyberDscsnUpdateList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 : 사이버상담 교육인원수정 목록조회
	 */	
	@Override
	public List<Map<String, Object>> selectCyberDscsnUpdateList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		rtn = dscsnAbilitMngMapper.selectCyberDscsnUpdateList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("사이버상담 교육인원수정 목록조회 ::::::::::::" + rtn.toString());
		
		return rtn;
	}

//-------------------------------------------------------------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------------------------------------------------------------
	
}



