/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseExcnMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseTrmnMapper;
import isry.itgcm.casemng.caseunity.mapper.SrvcGrPvsnMapper;
import isry.itgcm.casemng.caseunity.service.SrvcGrPvsnService;
import isry.itgcm.ddnl.monthDdln.mapper.MonthDdlnMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;

/**
 * @파일명        : SrvcGrPvsnServiceImpl.java
 * @프로그램 설명 	: 사례계획 ServicImpl Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 30. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 30.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("srvcGrPvsnService")
public class SrvcGrPvsnServiceImpl implements SrvcGrPvsnService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;

	@Resource(name="srvcGrPvsnMapper")
    private SrvcGrPvsnMapper srvcGrPvsnMapper;
	
	@Resource(name="caseExcnMapper")
    private CaseExcnMapper caseExcnMapper;
	
	@Resource(name="caseTrmnMapper")
	private CaseTrmnMapper caseTrmnMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;
	
	@Resource(name="monthDdlnMapper")
    private MonthDdlnMapper monthDdlnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	String sUserId = "";

	/**
	* @Method    : 서비스집단제공 목록조회
	* @param     : Map  : START_DATE(등록일자 시작), END_DATE(등록일자 종료), PVSN_RESRCE_NM(자원명), RSFR_INST_NO(자원제공주체번호), RSFR_INST_NM(자원제공주체명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public Map<String, Object> selectSrvcGrPvsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");			

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		// 수정 요청으로 인해 재 주석 처리 20230619 송태수 
		// String instNo = String.valueOf(loginVO.getInstNo());
		// paramMap.put("TKCG_INST_NO", instNo);
		
		Map<String,Object> retMap = new HashMap<>();		
		
		Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/
		
		
		int srvcGrPvsnCnt = srvcGrPvsnMapper.getSrvcGrPvsnList(paramMap2);
		paramMap2.put("TOT_CNT", srvcGrPvsnCnt);
		
		int totCnt  = srvcGrPvsnCnt; /* 전체ROW*/
		int pageIdx = Integer.parseInt(reqPage.getValue("pageNo")); /* page번호*/
		int rowSize = Integer.parseInt(reqPage.getValue("pageRowCount")); /* pageRow수*/
		
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex  = startIndex + rowSize - 1;
		
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);			
		
		rtn = srvcGrPvsnMapper.selectSrvcGrPvsnList(paramMap2);

		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);	
		
		/* 리턴 map 정보*/
		retMap.put("dsList", rtn);
		retMap.put("dmPage", pageMap);			

		return retMap;
	}

	/**
	* @Method    : 서비스집단제공 상세조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseGrExcnDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnMapper.selectCaseGrExcnDetail(paramMap);
	}

	/**
	* @Method    : 서비스집단제공 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectGrExcnProgramList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		return srvcGrPvsnMapper.selectGrExcnProgramList(paramMap);
	}
	
	/**
	* @Method    : 서비스집단제공 사례대상자 목록조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectExcnCaseTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		Map<String, String> paramMap = paramGroup.getSingleValueMap();		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		paramMap.put("PIC_INST_NO", String.valueOf(loginVO.getInstNo()));

		rtn = srvcGrPvsnMapper.selectExcnCaseTrprList(paramMap);

		Map<String, Object> map    = new HashMap<>();
		Map<String, String> reqMap = new HashMap<>();

		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);

			//사례대상자별 마감일자 조회
			reqMap.clear();
			reqMap.put("CASE_MNG_NO"   	 , String.valueOf(map.get("CASE_MNG_NO")));
			reqMap.put("CASE_MNG_ODRNO"	 , String.valueOf(map.get("CASE_MNG_ODRNO")));
			reqMap.put("UNT_TASKWK_SE_CD", String.valueOf(map.get("UNT_TASKWK_SE_CD")));
			/*
			List<Map<String, Object>> ddlnRtn = monthDdlnMapper.selectCaseMngDdlnCrtrInfo(reqMap);
			if(ddlnRtn.size() > 0) {
				Map<String, Object> ddlnMap = new HashMap<>();
				ddlnMap = ddlnRtn.get(0);

				map.put("DDLN_CRTR_YMD"		   , ddlnMap.get("DDLN_CRTR_YMD"));			//마감기준년월
				map.put("INST_NO"			   , ddlnMap.get("INST_NO"));				//기관번호
				map.put("UNT_TASKWK_SE_CD"	   , ddlnMap.get("UNT_TASKWK_SE_CD"));		//단위업무구분코드
				map.put("DDLN_YM"			   , ddlnMap.get("DDLN_YM"));				//마감년월
				map.put("DDLN_YN"			   , ddlnMap.get("DDLN_YN"));				//마감여부
				map.put("DDLN_APLCN_CRTR_SE_CD", ddlnMap.get("DDLN_APLCN_CRTR_SE_CD"));	//마감적용기준구분코드
			}
			*/
		}

		return rtn;
	}

	/**
	* @Method    : 서비스집단제공 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public Map<String, Object> processSrvcGrPvsnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> rtnMap = new HashMap<>();
		// 서비스집단제공 상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsDetailInfo");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn = null;	// 채번번호
		String sPvsnNo  = null; // 서비스제공번호
		String sStatus  = "U";	// 상태값

		String sResrceNo    = null; // 자원번호
		String sNewResrceNo = null; // NEW_자원번호

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		}

		List<Map<String, String>> bizRows = new ArrayList<>();
		Map<String, String> saveMap = new HashMap<>();

		if (paramGroup != null) {
			bizRows = paramGroup.getAllRowList();
			saveMap = bizRows.get(0);

			sPvsnNo      = saveMap.get("SRVC_PVSN_NO");
			sResrceNo    = saveMap.get("ORG_RESRCE_NO");
			sNewResrceNo = saveMap.get("RESRCE_NO");

			saveMap.put("USER_ID"			   , sUserId); // 세션 사용자ID 셋팅
			saveMap.put("CASE_PRGRS_STTS_SE_CD", "03");

			//기본정보 저장
			if(sPvsnNo.isEmpty()) {
				// 서비스제공번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();

				seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
				seqMap.put("RENU_NO_SE_CD", "SR");				  // 서비스제공번호 채번코드
				seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 서비스제공번호 발번
				saveMap.put("SRVC_PVSN_NO", sWprkSqn);

				//서비스제공번호 설정
				sPvsnNo = sWprkSqn;
				// 상태값 설정(신규)
				sStatus = "I";

			} else {
				if(!sResrceNo.equals(sNewResrceNo)) {
					saveMap.put("RESRCE_NO"      , sResrceNo);
					saveMap.put("DATAA_CHG_SE_CD", "D");
					saveMap.put("DEL_YN"         , "Y");

					//1. 기존 자원번호 정보 관련 데이터 삭제
					//1.1. 자원프로그램제공대상자 삭제
					caseExcnMapper.deleteSEB520Data(saveMap);
					caseExcnMapper.insertSEB521Data(saveMap);

					//1.2. 서비스집단제공 사례대상자 삭제
					caseExcnMapper.deleteSEB510Data(saveMap);
					caseExcnMapper.insertSEB511Data(saveMap);

					//1.3. 서비스제공 삭제
					caseExcnMapper.updateSEB500DelYN(saveMap);
					caseExcnMapper.insertSEB501Data (saveMap);

					// 서비스제공번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "SR");				  // 서비스제공번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 서비스제공번호 발번

					saveMap.put("RESRCE_NO"   	   , sNewResrceNo);
					saveMap.put("SRVC_PVSN_NO"     , sWprkSqn);
					saveMap.put("DEL_YN"           , "N");
					saveMap.put("DATAA_CHG_SE_CD"  , "I");

					//서비스제공번호 설정
					sPvsnNo = sWprkSqn;
					// 상태값 설정(신규)
					sStatus = "I";
				} 
			}

			request.setAttribute("SRVC_PVSN_NO", sPvsnNo);

			// 데이터변경구분코드 셋팅
			saveMap.put("DATAA_CHG_SE_CD", sStatus);

			// 서비스제공 저장 호출
			caseExcnMapper.saveSEB500Data(saveMap);
			// 서비스제공 이력등록 호출
			caseExcnMapper.insertSEB501Data(saveMap);

		}

		//서비스집단제공 사례대상자 저장
		savePvsnCaseTrpr(request, dataRequest);

		rtnMap.put("SRVC_PVSN_NO", sPvsnNo);

		return rtnMap;
	}
	
	//서비스집단제공 사례대상자 저장
	private void savePvsnCaseTrpr(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//서비스집단제공 사례대상자 DataSet
		ParameterGroup paramGroup  = dataRequest.getParameterGroup("dsCaseTrprList");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsProgramList");
		ParameterGroup paramGroup3 = dataRequest.getParameterGroup("dsExcnSrvcBizClList");

		String sPvsnNo  		  = null; // 서비스제공번호
		String sCasePrgrsSttsSeCd = null; // 사례진행상태구분코드

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				sPvsnNo            = mapIns.get("SRVC_PVSN_NO");
				sCasePrgrsSttsSeCd = mapIns.get("CASE_PRGRS_STTS_SE_CD");

				if(sPvsnNo == null || sPvsnNo.isEmpty()) {
					sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
					mapIns.put("SRVC_PVSN_NO", sPvsnNo);
				}

				mapIns.put("USER_ID"			  , sUserId); // 세션 사용자ID 셋팅
				mapIns.put("DATAA_CHG_SE_CD"	  , "I");     // 데이터변경구분코드 셋팅

				// 서비스제공대상자 저장 호출
				caseExcnMapper.saveSEB510Data(mapIns);
				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapIns);

				if(!"12".equals(sCasePrgrsSttsSeCd)) {
					mapIns.put("CASE_PRGRS_STTS_SE_CD", "03");

					// 사례기본 사례진행상태구분 수정 호출
					caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
					// 사례관리이력 등록 호출
					caseRegMapper.insertSEB110Data(mapIns);
				}

				request.setAttribute("CASE_MNG_NO"   , mapIns.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO", mapIns.get("CASE_MNG_ODRNO"));

				if(paramGroup2 != null) {
					for(int i=0; i<paramGroup2.rowSize(); i++) {
						RowState rs =  paramGroup2.getRowState(i);

						if(rs != RowState.INSERTED && rs != RowState.UPDATED  && rs != RowState.DELETED) {
							Map<String, String> map = paramGroup2.get(i).toMap();
							
							sPvsnNo = map.get("SRVC_PVSN_NO");
							if(sPvsnNo == null || sPvsnNo.isEmpty()) {
								sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
								map.put("SRVC_PVSN_NO", sPvsnNo);
							}

							map.put("CASE_MNG_NO"    , mapIns.get("CASE_MNG_NO"));
							map.put("CASE_MNG_ODRNO" , mapIns.get("CASE_MNG_ODRNO"));
							map.put("DEL_YN"		 , "N");
							map.put("DATAA_CHG_SE_CD", "I");			
							map.put("USER_ID"		 , sUserId);

							// 자원프로그램제공대상자 저장 호출
							caseExcnMapper.saveSEB520Data(map);
							// 자원프로그램제공대상자 이력등록 호출
							caseExcnMapper.insertSEB521Data(map);
						}
					}
				}
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U"); // 데이터변경구분코드 셋팅

				// 서비스제공대상자 저장 호출
				caseExcnMapper.saveSEB510Data(mapUpd);
				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapUpd);

				request.setAttribute("CASE_MNG_NO"   , mapUpd.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO", mapUpd.get("CASE_MNG_ODRNO"));

				//자원프로그램제공대상자 저장
				saveResrceProgrm(request, dataRequest);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();			
				mapDel.put("DEL_YN"			, "Y"); // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapDel);
				// 서비스제공대상자 삭제 호출
				caseExcnMapper.deleteSEB510Data(mapDel);

				request.setAttribute("CASE_MNG_NO"   , mapDel.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO", mapDel.get("CASE_MNG_ODRNO"));
				
				if(paramGroup2 != null) {
					for(int i=0; i<paramGroup2.rowSize(); i++) {
						RowState rs =  paramGroup2.getRowState(i);

						if(rs != RowState.INSERTED && rs != RowState.UPDATED  && rs != RowState.DELETED) {
							Map<String, String> map = paramGroup2.get(i).toMap();

							sPvsnNo = map.get("SRVC_PVSN_NO");
							if(sPvsnNo == null || sPvsnNo.isEmpty()) {
								sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
								map.put("SRVC_PVSN_NO", sPvsnNo);
							}

							map.put("CASE_MNG_NO"    , mapDel.get("CASE_MNG_NO"));
							map.put("CASE_MNG_ODRNO" , mapDel.get("CASE_MNG_ODRNO"));
							map.put("DEL_YN"		 , "Y");     // 삭제여부 셋팅
							map.put("DATAA_CHG_SE_CD", "D");			
							map.put("USER_ID"		 , sUserId);

							// 자원프로그램제공대상자 이력등록 호출
							caseExcnMapper.insertSEB521Data(map);
							// 자원프로그램제공대상자 삭제 호출
							caseExcnMapper.deleteSEB520Data(map);
						}
					}
				}
			}
			
			Iterator<ParameterRow> allRows = paramGroup.getAllRows();
			while (allRows.hasNext()) {					
				Map<String, String> map = allRows.next().toMap();

				request.setAttribute("CASE_MNG_NO"   , map.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO", map.get("CASE_MNG_ODRNO"));

				//자원프로그램제공대상자 저장
				saveResrceProgrm(request, dataRequest);
			}
		}
		if(paramGroup3 != null) {	// 실행세부사업 목록 있음
			if (paramGroup != null) {	// 사례대상자 목록 있음
				if(sPvsnNo == null || sPvsnNo.isEmpty()) {
					sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
				}
				for(int i = 0; i < paramGroup.rowSize(); i++) {
					Iterator<ParameterRow> insertedRows = paramGroup3.getInsertedRows();
					Iterator<ParameterRow> deletedRows  = paramGroup3.getDeletedRows();
					RowState rs =  paramGroup.getRowState(i);
					Map<String, String> map = paramGroup.get(i).toMap();
					if(rs != RowState.DELETED) {
						// 실행서비스세부사업제공대상자 저장
						while (insertedRows.hasNext()) {
							Map<String, String> mapIns = insertedRows.next().toMap();
							mapIns.put("SRVC_PVSN_NO"   , sPvsnNo);
							mapIns.put("CASE_MNG_NO"	, map.get("CASE_MNG_NO"));
							mapIns.put("CASE_MNG_ODRNO" , map.get("CASE_MNG_ODRNO"));
							mapIns.put("GR_TRGT_SRVC_YN", "Y");
							mapIns.put("USER_ID"		, sUserId);
							caseExcnMapper.insertExcnDetaiaBiz(mapIns);
						}
						
					} else {
						// 실행서비스세부사업제공대상자 삭제
						while (insertedRows.hasNext()) {
							Map<String, String> mapIns = insertedRows.next().toMap();
							mapIns.put("SRVC_PVSN_NO"   , sPvsnNo);
							mapIns.put("CASE_MNG_NO"	, map.get("CASE_MNG_NO"));
							mapIns.put("CASE_MNG_ODRNO" , map.get("CASE_MNG_ODRNO"));
							mapIns.put("USER_ID"		, sUserId);
							caseExcnMapper.deleteExcnDetaiaBiz(mapIns);
						}
					}
					// 실행서비스세부사업제공대상자 삭제
					while (deletedRows.hasNext()) {
						Map<String, String> mapDel = deletedRows.next().toMap();
						mapDel.put("SRVC_PVSN_NO"   , sPvsnNo);
						mapDel.put("CASE_MNG_NO"	, map.get("CASE_MNG_NO"));
						mapDel.put("CASE_MNG_ODRNO" , map.get("CASE_MNG_ODRNO"));
						mapDel.put("USER_ID"		, sUserId);
						caseExcnMapper.deleteExcnDetaiaBiz(mapDel);
					}
				}
			}
		}
	}

	//자원프로그램제공대상자 저장
	private void saveResrceProgrm(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//자원프로그램제공대상자 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsProgramList");

		String sPvsnNo = null; // 서비스제공번호
//		String sStatus = null;	// 상태값

		//사례관리번호/사례관리차수 설정
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;
		
		int nCnt = 0;
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				sPvsnNo = mapIns.get("SRVC_PVSN_NO");

				//전달받은 사례관리번호/사례관리차수로 설정
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO")   .toString();
				sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				mapIns.put("CASE_MNG_NO"   , sCaseMngNo);
				mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);

				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅				
				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅

				if(sPvsnNo.isEmpty()) {
					sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
					mapIns.put("SRVC_PVSN_NO", sPvsnNo);
				}

				// 자원프로그램제공대상자 저장 호출
				nCnt = nCnt + caseExcnMapper.saveSEB520Data(mapIns);
				// 자원프로그램제공대상자 이력등록 호출
				caseExcnMapper.insertSEB521Data(mapIns);
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				sPvsnNo = mapUpd.get("SRVC_PVSN_NO");

				//전달받은 사례관리번호/사례관리차수로 설정
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO")   .toString();
				sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				mapUpd.put("CASE_MNG_NO"    , sCaseMngNo);
				mapUpd.put("CASE_MNG_ODRNO" , sCaseMngOdrno);

				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅
//				mapUpd.put("DATAA_CHG_SE_CD", sStatus); // 데이터변경구분코드 셋팅
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅
				
				if(sPvsnNo.isEmpty()) {
					sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
					mapUpd.put("SRVC_PVSN_NO", sPvsnNo);
				}

				// 자원프로그램제공대상자 저장 호출
				nCnt = nCnt + caseExcnMapper.saveSEB520Data(mapUpd);
				// 자원프로그램제공대상자 이력등록 호출
				caseExcnMapper.insertSEB521Data(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				sPvsnNo = mapDel.get("SRVC_PVSN_NO");

				//전달받은 사례관리번호/사례관리차수로 설정
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO")   .toString();
				sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				mapDel.put("CASE_MNG_NO"    , sCaseMngNo);
				mapDel.put("CASE_MNG_ODRNO" , sCaseMngOdrno);
				
				mapDel.put("DEL_YN"			, "Y");     // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");
				
				if(sPvsnNo.isEmpty()) {
					sPvsnNo = request.getAttribute("SRVC_PVSN_NO").toString();
					mapDel.put("SRVC_PVSN_NO", sPvsnNo);
				}

				// 자원프로그램제공대상자 이력등록 호출
				caseExcnMapper.insertSEB521Data(mapDel);
				// 자원프로그램제공대상자 삭제 호출
				nCnt = nCnt + caseExcnMapper.deleteSEB520Data(mapDel);
			}
		}
	}

	/**
	* @Method    : 서비스집단제공 상세삭제
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void deleteSrvcGrPvsnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 서비스집단제공 상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsDetailInfo");
		if (paramGroup == null) {
			throw new AppWorksException("삭제할 자료가 없습니다.", Alert.ERROR);
		}

		String sPvsnNo  = null; // 서비스제공번호

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		}

		Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

		//삭제 이벤트
		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			sPvsnNo = mapDel.get("SRVC_PVSN_NO");

			mapDel.put("DEL_YN"				  , "Y");     // 삭제여부 셋팅			
			mapDel.put("DATAA_CHG_SE_CD"	  , "D");     // 데이터변경구분코드 셋팅
			mapDel.put("USER_ID"			  , sUserId);
			mapDel.put("CASE_PRGRS_STTS_SE_CD", "03");
			
			// 서비스제공 이력등록 호출
			caseExcnMapper.insertSEB501Data(mapDel);
			// 서비스제공 삭제 호출
			caseExcnMapper.updateSEB500DelYN(mapDel);
		}

		//서비스집단제공 사례대상자 DataSet
		paramGroup = dataRequest.getParameterGroup("dsCaseTrprList");
		Iterator<ParameterRow> deletedTrprRows  = paramGroup.getDeletedRows();
		
		//삭제 이벤트
		while (deletedTrprRows.hasNext()) {

			Map<String, String> mapDel = deletedTrprRows.next().toMap();
			sPvsnNo = mapDel.get("SRVC_PVSN_NO");

			if(!sPvsnNo.isEmpty()) {
				
				mapDel.put("DEL_YN"			, "Y"); 	// 삭제여부 셋팅
				mapDel.put("DATAA_CHG_SE_CD", "D");     // 데이터변경구분코드 셋팅
				mapDel.put("USER_ID"		, sUserId);

				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapDel);
				// 서비스제공대상자 삭제 호출
				caseExcnMapper.deleteSEB510Data(mapDel);

			}
		}
	}		
	
	/**
	* @Method    : 서비스집단제공 이력 불러오기
	* @param     : Map  : SRVC_PVSN_BGNG_YMD(서비스제공시작일자), SRVC_PVSN_END_YMD(서비스제공종료일자), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NM(자원멍)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectSrvcGrPvsnHstrList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);	
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("TKCG_INST_NO",String.valueOf(loginVO.getInstNo()));


		return srvcGrPvsnMapper.selectSrvcGrPvsnHstrList(paramMap);
	}
	
	/**
	* @Method    : 서비스집단제공 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectBizNoExcnCaseTrprList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmBizNoCaseTrpr");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnMapper.selectBizNoExcnCaseTrprList(paramMap);
	}
	
	/**
	* @Method    : 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectBizNoCaseTrprList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmBizNoCaseTrpr");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnMapper.selectBizNoCaseTrprList(paramMap);
	}
	
	/**
	* @Method    : 서비스집단제공 실행서비스 목록조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectGrExcnSrvcBizClList(DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnMapper.selectGrExcnSrvcBizClList(paramMap);
	}
}
