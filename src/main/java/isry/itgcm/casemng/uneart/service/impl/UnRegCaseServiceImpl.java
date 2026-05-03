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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.RowState;

import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseExcnMapper;
import isry.itgcm.casemng.uneart.mapper.UnRegCaseMapper;
import isry.itgcm.casemng.uneart.service.UnRegCaseService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.DateUtil;

/**
* @Class Name  : UnRegCaseService.java
* @Description : 미등록사례지원 serviceImpl Class
*
* @author  : Hee Sung Yoon
* @since   : 2023. 01. 10.
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2023. 01. 10.  Hee Sung Yoon   최초작성
*/

@Service("unRegCaseService")
public class UnRegCaseServiceImpl implements UnRegCaseService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnRegCaseServiceImpl.class);
	
	@Resource(name = "unRegCaseMapper")
	private UnRegCaseMapper unRegCaseMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;		
	
	@Resource(name = "caseExcnMapper")
	private CaseExcnMapper caseExcnMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;
	
	/**
	 * @Method     : selectUnRegCaseList
	 * @Method설명 : 미등록사례지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> selectUnRegCaseList (HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");		
		
		Map<String,Object> retMap = new HashMap<>();		
		
		// 세션정보
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		int unRegCnt = unRegCaseMapper.unRegCaseListCount(paramMap2);
		paramMap2.put("TOT_CNT", unRegCnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = unRegCnt;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);		
		
		list = unRegCaseMapper.selectUnRegCaseList(paramMap2);
		
		for(Map<String, Object> map : list) {
			int totSum = Integer.parseInt(map.get("STDENT").toString())
					+ Integer.parseInt(map.get("OSCHL").toString()) 
					+ Integer.parseInt(map.get("AGRDHS").toString())
					+ Integer.parseInt(map.get("STANDARD").toString());
			map.put("TOT_SUM", (Object)totSum);
		}
		
		
		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);		
		
		retMap.put("dsList", list);
		retMap.put("dmPage", pageMap);
		
		return retMap;
	}	
	
	/**
	 * @Method     : insertUnRegCase
	 * @Method설명 : 미등록사례지원 등록
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> insertUnRegCase (HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		// 미등록사례지원 저장 SED100
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmUnRegCase");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("USER_ID", userId);
		String unregiCaseNo = "";
		String dataaChgSeCd = "";
		if("".equals(paramMap.get("UNREGI_CASE_NO")) || null == paramMap.get("UNREGI_CASE_NO")) { // 신규
			unregiCaseNo = unRegCaseMapper.newUnregiCaseNo();
			paramMap.put("UNREGI_CASE_NO", unregiCaseNo);
			dataaChgSeCd = "I";
		} else {
			unregiCaseNo = paramMap.get("UNREGI_CASE_NO");
			dataaChgSeCd = "U";
		}
		unRegCaseMapper.updategeUnRegCase(paramMap);
		
		// 미등록사례지원 이력 저장 SED101
		paramMap.put("DATAA_CHG_SE_CD", dataaChgSeCd);
		unRegCaseMapper.insertUnRegCaseHis(paramMap);
		
		// 사업등록 목록 저장 SED110
		ParameterGroup bisReg = dataRequest.getParameterGroup("dsBizReg");
		if(null != bisReg) {
			Iterator<ParameterRow> insertedRows = bisReg.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = bisReg.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = bisReg.getDeletedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("UNREGI_CASE_NO", unregiCaseNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertSrvcExcnBiz(mapIns);
			}
			
			while (updatedRows.hasNext()) {
				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("UNREGI_CASE_NO", unregiCaseNo);
				mapUpd.put("USER_ID", userId);
				unRegCaseMapper.updateSrvcExcnBiz(mapUpd);
			}
			
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("UNREGI_CASE_NO", unregiCaseNo);
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteSrvcExcnBiz(mapDel);
			}
		}
		
		// 실행서비스 세부사업 목록 저장 SED111
		ParameterGroup dsExcnSrvcBizClList = dataRequest.getParameterGroup("dsExcnSrvcBizClList");
		if(null != dsExcnSrvcBizClList) {
			Iterator<ParameterRow> insertedRows = dsExcnSrvcBizClList.getInsertedRows();
			Iterator<ParameterRow> deletedRows  = dsExcnSrvcBizClList.getDeletedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("UNREGI_CASE_NO", unregiCaseNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertUneartExcnDetaiaBiz(mapIns);
			}
			
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("UNREGI_CASE_NO", unregiCaseNo);
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteUneartExcnDetaiaBiz(mapDel);
			}
		}
		
		// 담당자 목록 저장 SED102
		ParameterGroup dsPic = dataRequest.getParameterGroup("dsPic");
		if(null != bisReg) {
			Iterator<ParameterRow> insertedRows = dsPic.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = dsPic.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = dsPic.getDeletedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("UNREGI_CASE_NO", unregiCaseNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertUnRegCasePic(mapIns);
			}
			
			while (updatedRows.hasNext()) {
				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("UNREGI_CASE_NO", unregiCaseNo);
				mapUpd.put("USER_ID", userId);
				unRegCaseMapper.updateUnRegCasePic(mapUpd);
			}
			
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("UNREGI_CASE_NO", unregiCaseNo);
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteUnRegCasePic(mapDel);
			}
		}
		
		// 사례대상자 목록 저장 SED103
		ParameterGroup dsCaseTrprList = dataRequest.getParameterGroup("dsCaseTrprList");
		if(null != dsCaseTrprList) {
			Iterator<ParameterRow> insertedRows = dsCaseTrprList.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = dsCaseTrprList.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = dsCaseTrprList.getDeletedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("UNREGI_CASE_NO", unregiCaseNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertUnRegCaseTrpr(mapIns);
			}
			
			while (updatedRows.hasNext()) {
				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("UNREGI_CASE_NO", unregiCaseNo);
				mapUpd.put("USER_ID", userId);
				unRegCaseMapper.updateUnRegCaseTrpr(mapUpd);
			}
			
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("UNREGI_CASE_NO", unregiCaseNo);
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteUnRegCaseTrpr(mapDel);
			}
		}
		
		// 사례대상자 사례실행 저장 SEB500
		String srvcPvsnNo = "";
		ParameterGroup resrceGroup = dataRequest.getParameterGroup("dmResrceNo");
		Map<String, String> resrceMap = resrceGroup.getSingleValueMap();
		String resrceNo = resrceMap.get("RESRCE_NO"); // 자원번호
		if(!"".equals(resrceNo) && null != resrceNo) { // 자원번호 존재
			Iterator<ParameterRow> allRows = dsCaseTrprList.getAllRows();
			if(allRows.hasNext()) {	// 사례대상자 목록 있음. SEB500 저장 해야 함
				paramMap.put("RESRCE_NO", resrceNo);
				srvcPvsnNo = paramMap.get("SRVC_PVSN_NO"); // 서비스제공번호
				if("".equals(srvcPvsnNo) || null == srvcPvsnNo) {
					// 서비스제공번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , userId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "SR");				  // 서비스제공번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					srvcPvsnNo = String.valueOf(valMap.get("RENU_NO"));	// 서비스제공번호 발번
					paramMap.put("SRVC_PVSN_NO", srvcPvsnNo);
					
				}
				Map<String, String> insertMap = new HashMap<String, String>(); 
				insertMap.put("SRVC_PVSN_NO"		, paramMap.get("SRVC_PVSN_NO"));
				insertMap.put("RESRCE_NO"			, paramMap.get("RESRCE_NO"));
				insertMap.put("SRVC_PVSN_MTHD_SE_CD", paramMap.get("SRVC_PVSN_MTHD_SE_CD"));
				insertMap.put("SRVC_PVSN_TTL_NM"	, paramMap.get("SPRT_TTL_NM"));
				insertMap.put("SRVC_PVSN_CN"		, paramMap.get("SPRT_CN"));
				insertMap.put("SRVC_PVSN_BGNG_YMD"	, paramMap.get("SPRT_BGNG_YMD"));
				insertMap.put("SRVC_PVSN_END_YMD"	, paramMap.get("SPRT_END_YMD"));
				insertMap.put("SRVC_PVSN_BGNG_HR"	, paramMap.get("SPRT_BGNG_HR"));
				insertMap.put("SRVC_PVSN_END_HR"	, paramMap.get("SPRT_END_HR"));
				insertMap.put("PIC_NO"				, paramMap.get("UNREGI_CASE_PIC_NO"));
				insertMap.put("TKCG_INST_NO"		, paramMap.get("PIC_INST_NO"));
				insertMap.put("SRVC_GR_PVSN_YN"		, "N");
				insertMap.put("UNREGI_CASE_YN"		, "Y");
				insertMap.put("USER_ID"	  			, userId);
				caseExcnMapper.saveSEB500Data(insertMap);
			}
			
			while (allRows.hasNext()) {
				ParameterRow row = allRows.next();
				Map<String, String> map = row.toMap();
				String delYn = "N";
				if(row.getState() == RowState.DELETED) {
					delYn = "Y";
				}
				Map<String, String> saveMap = new HashMap<>();
				saveMap.put("SRVC_PVSN_NO"			, paramMap.get("SRVC_PVSN_NO"));
				saveMap.put("CASE_MNG_NO"			, map.get("CASE_MNG_NO"));
				saveMap.put("CASE_MNG_ODRNO"		, map.get("CASE_MNG_ODRNO"));
				saveMap.put("RESRCE_NO"				, paramMap.get("RESRCE_NO"));
				saveMap.put("SRVC_PVSN_YN"			, "N");
				saveMap.put("SRVC_PVSN_RESULT_CN"	, paramMap.get("SRVC_PVSN_NO"));
				saveMap.put("DEL_YN"				, delYn);
				saveMap.put("USER_ID"	  			, userId);
				// 서비스제공대상자 저장 호출
				caseExcnMapper.saveSEB510Data(saveMap);
				
				if(null != dsExcnSrvcBizClList) {
					Iterator<ParameterRow> allBizRows = dsExcnSrvcBizClList.getAllRows();
					while (allBizRows.hasNext()) {
						ParameterRow bizRow = allBizRows.next();
						Map<String, String> bizMap = bizRow.toMap();
						delYn = "N";
						if(bizRow.getState() == RowState.DELETED) {
							delYn = "Y";
						}
						Map<String, String> saveBizMap = new HashMap<>();
						saveBizMap.put("SRVC_PVSN_NO"			, saveMap.get("SRVC_PVSN_NO"));
						saveBizMap.put("CASE_MNG_NO"			, saveMap.get("CASE_MNG_NO"));
						saveBizMap.put("CASE_MNG_ODRNO"			, saveMap.get("CASE_MNG_ODRNO"));
						saveBizMap.put("RESRCE_NO"				, saveMap.get("RESRCE_NO"));
						saveBizMap.put("EXCN_SRVC_BIZ_CL_NO"	, bizMap.get("EXCN_SRVC_BIZ_CL_NO"));
						saveBizMap.put("EXCN_SRVC_DETAIA_BIZ_NO", bizMap.get("EXCN_SRVC_DETAIA_BIZ_NO"));
						saveBizMap.put("GR_TRGT_SRVC_YN"		, "N");
						saveBizMap.put("DEL_YN"					, delYn);
						saveBizMap.put("USER_ID"	  			, userId);
						
						// SEB540 저장
						caseExcnMapper.insertExcnDetaiaBiz(saveBizMap);
					}
				}
			}
			
		}
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("UNREGI_CASE_NO", unregiCaseNo);
		rtnMap.put("SRVC_PVSN_NO", srvcPvsnNo);
		if(!"".equals(srvcPvsnNo) && null != srvcPvsnNo) {
			unRegCaseMapper.updateSED100SrvcPvsnNo(rtnMap);
		}
		
		return rtnMap;
	}
	
	/**
	 * @Method     : selectBizList
	 * @Method설명 : 사업목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectBizList (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		list = unRegCaseMapper.selectBizList(paramMap);
		return list;
	}
	
	/**
	 * @Method     : selectCnterSprtList
	 * @Method설명 : 시군구센터지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> selectCnterSprtList (HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		// 세션정보
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));		
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/			
		
		int totCnt = unRegCaseMapper.selectCnterSprtListCount(paramMap2);
		paramMap2.put("TOT_CNT", totCnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(totCnt > 0) {
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			list = unRegCaseMapper.selectCnterSprtList(paramMap2);
		}
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		result.put("dsList", list);
		result.put("dmPage", resPage);
		return result;
	}
	
	/**
	 * @Method     : selectInstList
	 * @Method설명 : 시군구센터 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectInstList (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		list = unRegCaseMapper.selectInstList(paramMap);
		return list;
	}
	
	/**
	 * @Method     : insertCnterSprt
	 * @Method설명 : 시군구센터지원 등록
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> insertCnterSprt (HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		// 시군구센터지원 저장
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmCnterSprt");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("USER_ID", userId);
		String ssgCnterSprtNo = "";
		String dataaChgSeCd = "";
		if("".equals(paramMap.get("SGG_CNTER_SPRT_NO")) || null == paramMap.get("SGG_CNTER_SPRT_NO")) {// 신규
			ssgCnterSprtNo = unRegCaseMapper.newSsgCnterSprtNo();
			paramMap.put("SGG_CNTER_SPRT_NO", ssgCnterSprtNo);
			dataaChgSeCd = "I";
		} else {
			ssgCnterSprtNo = paramMap.get("SGG_CNTER_SPRT_NO");
			dataaChgSeCd = "U";
		}
		unRegCaseMapper.updategeCnterSprt(paramMap);
		
		// 시군구센터지원 이력 저장
		paramMap.put("DATAA_CHG_SE_CD", dataaChgSeCd);
		unRegCaseMapper.insertCnterSprtHis(paramMap);
		
		// 사례등록 목록 저장
		ParameterGroup bisReg = dataRequest.getParameterGroup("dsBizReg");
		if(null != bisReg) {
			Iterator<ParameterRow> insertedRows = bisReg.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = bisReg.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = bisReg.getDeletedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("SGG_CNTER_SPRT_NO", ssgCnterSprtNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertCnterSrvcExcnBiz(mapIns);
			}
			
			while (updatedRows.hasNext()) {
				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("USER_ID", userId);
				unRegCaseMapper.updateCnterSrvcExcnBiz(mapUpd);
			}
			
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteCnterSrvcExcnBiz(mapDel);
			}
		}
		
		
		// 컨설팅 센터 목록 저장
		ParameterGroup cnterSprt = dataRequest.getParameterGroup("dsCnterSprtInst");
		if(null != cnterSprt) {
			Iterator<ParameterRow> insertedCnterRows = cnterSprt.getInsertedRows();
			//Iterator<ParameterRow> updatedCnterRows  = cnterSprt.getUpdatedRows();
			Iterator<ParameterRow> deletedCnterRows  = cnterSprt.getDeletedRows();
			
			while (insertedCnterRows.hasNext()) {
				Map<String, String> mapIns = insertedCnterRows.next().toMap();
				mapIns.put("SGG_CNTER_SPRT_NO", ssgCnterSprtNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertCnterSprt(mapIns);
			}
			/*
			while (updatedCnterRows.hasNext()) {
				Map<String, String> mapUpd = updatedCnterRows.next().toMap();
				mapUpd.put("USER_ID", userId);
				unRegCaseMapper.updateCnterSprt(mapUpd);
			}
			*/
			while (deletedCnterRows.hasNext()) {
				Map<String, String> mapDel = deletedCnterRows.next().toMap();
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteCnterSprt(mapDel);
			}
		}
		
		// 실행서비스 세부사업 목록 저장
		ParameterGroup excnBiz = dataRequest.getParameterGroup("dsExcnSrvcBizClList");
		if(null != excnBiz) {
			Iterator<ParameterRow> insertedRows = excnBiz.getInsertedRows();
			Iterator<ParameterRow> deletedRows  = excnBiz.getDeletedRows();
			
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("SGG_CNTER_SPRT_NO", ssgCnterSprtNo);
				mapIns.put("USER_ID", userId);
				unRegCaseMapper.insertCnterExcnDetaiaBiz(mapIns);
			}
			
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("USER_ID", userId);
				unRegCaseMapper.deleteCnterExcnDetaiaBiz(mapDel);
			}
		}
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("SGG_CNTER_SPRT_NO", ssgCnterSprtNo);
		
		return rtnMap;
	}
	
	/**
	 * @Method     : selectCnstnCnterInst
	 * @Method설명 : 미등록사례지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectCnstnCnterInst (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		list = unRegCaseMapper.selectCnstnCnterInst(paramMap);
		return list;
	}
	
	/**
	 * @Method     : selectCnterBizList
	 * @Method설명 : 시군구센터지원 사업목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectCnterBizList (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		list = unRegCaseMapper.selectCnterBizList(paramMap);
		return list;
	}
	
	/**
	 * @Method     : selectExcnDetaiaBizList
	 * @Method설명 : 미등록사례지원 실행서비스 사업분류 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectExcnDetaiaBizList (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		list = unRegCaseMapper.selectExcnDetaiaBizList(paramMap);
		return list;
	}
	
	/**
	 * @Method     : selectCnterExcnDetaiaBizList
	 * @Method설명 : 시군구센터지원 실행서비스 사업분류 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectCnterExcnDetaiaBizList (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		list = unRegCaseMapper.selectCnterExcnDetaiaBizList(paramMap);
		return list;
	}
	
	/**
	 * @Method     : selectUnRegCasePic
	 * @Method설명 : 미등록사례지원 담당자 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectUnRegCasePic (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return unRegCaseMapper.selectUnRegCasePic(paramMap);
	}
	
	/**
	 * @Method     : selectUnRegCaseTrpr
	 * @Method설명 : 미등록사례지원 사례대상자 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */	
	public List<Map<String, Object>> selectUnRegCaseTrpr (DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return unRegCaseMapper.selectUnRegCaseTrpr(paramMap);
	}
}
