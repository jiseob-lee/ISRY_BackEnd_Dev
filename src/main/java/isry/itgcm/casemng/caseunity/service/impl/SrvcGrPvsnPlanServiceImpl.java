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
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CasePlanMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseTrmnMapper;
import isry.itgcm.casemng.caseunity.mapper.SrvcGrPvsnPlanMapper;
import isry.itgcm.casemng.caseunity.service.SrvcGrPvsnPlanService;
import isry.itgcm.ddnl.monthDdln.mapper.MonthDdlnMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;


/**
 * @파일명        : SrvcGrPvsnPlanServiceImpl.java
 * @프로그램 설명 	: 서비스집단제공계획 ServicImpl Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 13. 
 * @수정자        : Lee.Jun.Yeong	
 * @수정일        : 2022. 6. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("srvcGrPvsnPlanService")
public class SrvcGrPvsnPlanServiceImpl implements SrvcGrPvsnPlanService {

	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;
	
	@Resource(name="srvcGrPvsnPlanMapper")
    private SrvcGrPvsnPlanMapper srvcGrPvsnPlanMapper;
	
	@Resource(name="casePlanMapper")
    private CasePlanMapper casePlanMapper;
	
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
	* @Method    : 서비스집단제공계획 목록조회
	* @param     : Map  : PLAN_FNDNG_BGNG_YMD(계획수립일자 시작), PLAN_FNDNG_END_YMD(계획수립일자 종료), PVSN_RESRCE_NM(자원명), RSFR_INST_NO(자원제공주체번호), RSFR_INST_NM(자원제공주체명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectSrvcGrPvsnPlanList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/

		return srvcGrPvsnPlanMapper.selectSrvcGrPvsnPlanList(paramMap2);
	}

	/**
	* @Method    : 서비스집단제공계획 상세조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호)
	* @return    : map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseGrPlanDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnPlanMapper.selectCaseGrPlanDetail(paramMap);
	}

	/**
	* @Method    : 서비스집단제공계획 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectGrPlanProgramList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		return srvcGrPvsnPlanMapper.selectGrPlanProgramList(paramMap);
	}
	
	/**
	* @Method    : 서비스집단제공계획 사례대상자 목록조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseTrprList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnPlanMapper.selectCaseTrprList(paramMap);
	}

	/**
	* @Method    : 서비스집단제공계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public Map<String, Object> processSrvcGrPvsnPlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> rtnMap = new HashMap<>();

		// 서비스집단제공계획 상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsDetailInfo");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn  = null; // 채번번호
		String sPlanNo   = null; // 서비스제공계획번호

		String sResrceNo     = null; // 자원번호
		String sNewResrceNo  = null; // NEW_자원번호

		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		}

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				sPlanNo = mapIns.get("SRVC_PVSN_PLAN_NO");

				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I");     // 데이터변경구분코드 셋팅

				//기본정보 저장
				if(sPlanNo.isEmpty()) {
					// 서비스제공계획번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "SP");				  // 서비스제공계획번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 서비스제공계획번호 발번
					mapIns.put("SRVC_PVSN_PLAN_NO", sWprkSqn);

					//서비스제공계획번호 설정
					sPlanNo = sWprkSqn;
				}
				request.setAttribute("SRVC_PVSN_PLAN_NO", sPlanNo);

				// 서비스제공계획 저장 호출
				casePlanMapper.saveSEB300Data(mapIns);
				// 서비스제공계획 이력등록 호출
				casePlanMapper.insertSEB301Data(mapIns);				
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				sPlanNo      = mapUpd.get("SRVC_PVSN_PLAN_NO");
				sResrceNo    = mapUpd.get("ORG_RESRCE_NO");
				sNewResrceNo = mapUpd.get("RESRCE_NO");

				mapUpd.put("USER_ID", sUserId);

				if(!sResrceNo.equals(sNewResrceNo)) {

					mapUpd.put("RESRCE_NO"      , sResrceNo);
					mapUpd.put("DATAA_CHG_SE_CD", "D");
					mapUpd.put("DEL_YN"         , "Y");

					//1. 기존 자원번호 정보 관련 데이터 삭제
					//1.1. 자원프로그램제공계획대상자 삭제
					casePlanMapper.deleteSEB320Data(mapUpd);
					casePlanMapper.insertSEB321Data(mapUpd);

					//1.2. 서비스집단제공계획 사례대상자 삭제
					casePlanMapper.deleteSEB310Data(mapUpd);
					casePlanMapper.insertSEB311Data(mapUpd);

					//1.3. 서비스제공계획 삭제
					casePlanMapper.updateDelYnSEB300(mapUpd);
					casePlanMapper.insertSEB301Data(mapUpd);

					// 서비스제공계획번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "SP");				  // 서비스제공계획번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 서비스제공계획번호 발번

					mapUpd.put("RESRCE_NO"        , sNewResrceNo);
					mapUpd.put("SRVC_PVSN_PLAN_NO", sWprkSqn);
					mapUpd.put("DEL_YN"           , "N");
					mapUpd.put("DATAA_CHG_SE_CD"  , "I");

					//서비스제공계획번호 설정
					sPlanNo = sWprkSqn;

				} else {
					mapUpd.put("DATAA_CHG_SE_CD", "U");
				}

				request.setAttribute("SRVC_PVSN_PLAN_NO", sPlanNo);

				// 서비스제공계획 저장 호출
				casePlanMapper.saveSEB300Data(mapUpd);
				// 서비스제공계획 이력등록 호출
				casePlanMapper.insertSEB301Data(mapUpd);
			}
			
			if("".equals(sPlanNo) || sPlanNo == null) {
				Iterator<ParameterRow> allRows = paramGroup.getAllRows();
				while (allRows.hasNext()) {
					Map<String, String> map = allRows.next().toMap();
					sPlanNo = map.get("SRVC_PVSN_PLAN_NO");

					request.setAttribute("SRVC_PVSN_PLAN_NO", sPlanNo);
					
				}
			}

			//서비스집단제공계획 사례대상자 저장
			savePvsnPlanCaseTrpr(request, dataRequest);
		}

		rtnMap.put("SRVC_PVSN_PLAN_NO", sPlanNo);

		return rtnMap;
	}

	//서비스집단제공계획 사례대상자 저장
	private void savePvsnPlanCaseTrpr(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//서비스집단제공계획 사례대상자 DataSet
		ParameterGroup paramGroup  = dataRequest.getParameterGroup("dsCaseTrprList");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsProgramList");

		String sPlanNo  = null; // 서비스제공계획번호
//		String sStatus  = null;	// 상태값

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			int nInsertedCnt = 0;
			int nUpdatedCnt  = 0;
			
			String sCasePrgrsSttsSeCd = "";

			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				sPlanNo = mapIns.get("SRVC_PVSN_PLAN_NO");
				if(sPlanNo == null || sPlanNo.isEmpty()) {
					sPlanNo = request.getAttribute("SRVC_PVSN_PLAN_NO").toString();
					mapIns.put("SRVC_PVSN_PLAN_NO", sPlanNo);
				}

				mapIns.put("USER_ID"			  , sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD"	  , "I");     // 데이터변경구분코드 셋팅
				mapIns.put("CASE_PRGRS_STTS_SE_CD", "02");

				// 서비스제공계획대상자 저장 호출
				casePlanMapper.saveSEB310Data(mapIns);
				// 서비스제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB311Data(mapIns);

				sCasePrgrsSttsSeCd = "";
				sCasePrgrsSttsSeCd = mapIns.get("CASE_PRGRS_STTS_SE_CD");
				if(!"12".equals(sCasePrgrsSttsSeCd)) {
					// 사례기본 사례진행상태구분 수정 호출
					caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
					// 사례관리이력 등록 호출
					caseRegMapper.insertSEB110Data(mapIns);
				}

				request.setAttribute("SRVC_PVSN_PLAN_NO", sPlanNo);
				request.setAttribute("CASE_MNG_NO"   	, mapIns.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO"	, mapIns.get("CASE_MNG_ODRNO"));

				if(paramGroup2 != null) {
					for(int i=0; i<paramGroup2.rowSize(); i++) {
						RowState rs =  paramGroup2.getRowState(i);

						if(rs != RowState.INSERTED && rs != RowState.UPDATED  && rs != RowState.DELETED) {
							Map<String, String> map = paramGroup2.get(i).toMap();

							sPlanNo = map.get("SRVC_PVSN_PLAN_NO");
							if(sPlanNo == null || sPlanNo.isEmpty()) {
								sPlanNo = request.getAttribute("SRVC_PVSN_PLAN_NO").toString();
								map.put("SRVC_PVSN_PLAN_NO", sPlanNo);
							}

							map.put("CASE_MNG_NO"    , mapIns.get("CASE_MNG_NO"));
							map.put("CASE_MNG_ODRNO" , mapIns.get("CASE_MNG_ODRNO"));
							map.put("DEL_YN"		 , "N");
							map.put("DATAA_CHG_SE_CD", "I");			
							map.put("USER_ID"		 , sUserId);
							
							// 자원프로그램제공계획대상자 저장 호출
							casePlanMapper.saveSEB320Data(map);
							// 자원프로그램제공계획대상자 이력등록 호출
							casePlanMapper.insertSEB321Data(map);
						}
					}
				}

				//자원프로그램제공계획대상자 저장
//				saveResrceProgrm(request, dataRequest);

				nInsertedCnt++;
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {
				
				Map<String, String> mapUpd = updatedRows.next().toMap();
				
				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U");     // 데이터변경구분코드 셋팅

				// 서비스제공계획대상자 저장 호출
				casePlanMapper.saveSEB310Data(mapUpd);
				// 서비스제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB311Data(mapUpd);

				request.setAttribute("SRVC_PVSN_PLAN_NO", mapUpd.get("SRVC_PVSN_PLAN_NO"));				
				request.setAttribute("CASE_MNG_NO"      , mapUpd.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO"   , mapUpd.get("CASE_MNG_ODRNO"));

				//자원프로그램제공계획대상자 저장
				saveResrceProgrm(request, dataRequest);

				nUpdatedCnt++;
			}
			
			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();

				mapDel.put("DEL_YN"			, "Y");     // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 서비스제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB311Data(mapDel);
				// 서비스제공계획대상자 삭제 호출
				casePlanMapper.deleteSEB310Data(mapDel);

				request.setAttribute("SRVC_PVSN_PLAN_NO", mapDel.get("SRVC_PVSN_PLAN_NO"));
				request.setAttribute("CASE_MNG_NO"      , mapDel.get("CASE_MNG_NO"));
				request.setAttribute("CASE_MNG_ODRNO"   , mapDel.get("CASE_MNG_ODRNO"));

				if(paramGroup2 != null) {
					for(int i=0; i<paramGroup2.rowSize(); i++) {
						RowState rs =  paramGroup2.getRowState(i);

						if(rs != RowState.INSERTED && rs != RowState.UPDATED  && rs != RowState.DELETED) {
							Map<String, String> map = paramGroup2.get(i).toMap();

							sPlanNo = map.get("SRVC_PVSN_PLAN_NO");
							if(sPlanNo == null || sPlanNo.isEmpty()) {
								sPlanNo = request.getAttribute("SRVC_PVSN_PLAN_NO").toString();
								map.put("SRVC_PVSN_PLAN_NO", sPlanNo);
							}

							map.put("CASE_MNG_NO"    , mapDel.get("CASE_MNG_NO"));
							map.put("CASE_MNG_ODRNO" , mapDel.get("CASE_MNG_ODRNO"));
							map.put("DEL_YN"		 , "Y");     // 삭제여부 셋팅
							map.put("DATAA_CHG_SE_CD", "D");			
							map.put("USER_ID"		 , sUserId);

							// 자원프로그램제공계획대상자 이력등록 호출
							casePlanMapper.insertSEB321Data(map);
							// 자원프로그램제공계획대상자 삭제 호출
							casePlanMapper.deleteSEB320Data(map);
						}
					}
				}
			}

//			if(nInsertedCnt + nUpdatedCnt == 0) {
				Iterator<ParameterRow> allRows = paramGroup.getAllRows();
				while (allRows.hasNext()) {
					Map<String, String> map = allRows.next().toMap();

//					request.setAttribute("SRVC_PVSN_PLAN_NO", map.get("SRVC_PVSN_PLAN_NO"));
					request.setAttribute("CASE_MNG_NO"   	, map.get("CASE_MNG_NO"));
					request.setAttribute("CASE_MNG_ODRNO"	, map.get("CASE_MNG_ODRNO"));

					//자원프로그램제공대상자 저장
					saveResrceProgrm(request, dataRequest);
				}
			}
//		}
	}

	//자원프로그램제공계획대상자 저장
	private void saveResrceProgrm(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//자원프로그램제공계획대상자 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsProgramList");

		String sPlanNo  = null; // 서비스제공계획번호
//		String sStatus  = null;	// 상태값 
		
		//사례관리번호/사례관리차수 설정
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				sPlanNo = mapIns.get("SRVC_PVSN_PLAN_NO");
				
				//전달받은 사례관리번호/사례관리차수로 설정
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO")   .toString();
				sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				mapIns.put("CASE_MNG_NO"    , sCaseMngNo);
				mapIns.put("CASE_MNG_ODRNO" , sCaseMngOdrno);

				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅

				if(sPlanNo.isEmpty()) {
					sPlanNo = request.getAttribute("SRVC_PVSN_PLAN_NO").toString();
					mapIns.put("SRVC_PVSN_PLAN_NO", sPlanNo);
				}

				// 자원프로그램제공계획대상자 저장 호출
				casePlanMapper.saveSEB320Data(mapIns);
				// 자원프로그램제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB321Data(mapIns);
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				sPlanNo = mapUpd.get("SRVC_PVSN_PLAN_NO");

				//전달받은 사례관리번호/사례관리차수로 설정
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO")   .toString();
				sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				mapUpd.put("CASE_MNG_NO"    , sCaseMngNo);
				mapUpd.put("CASE_MNG_ODRNO" , sCaseMngOdrno);

				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅

				if(sPlanNo.isEmpty()) {
					sPlanNo = request.getAttribute("SRVC_PVSN_PLAN_NO").toString();
					mapUpd.put("SRVC_PVSN_PLAN_NO", sPlanNo);
				}

				// 자원프로그램제공계획대상자 저장 호출
				casePlanMapper.saveSEB320Data(mapUpd);
				// 자원프로그램제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB321Data(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				sPlanNo = mapDel.get("SRVC_PVSN_PLAN_NO");

				//전달받은 사례관리번호/사례관리차수로 설정
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO")   .toString();
				sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				mapDel.put("CASE_MNG_NO"    , sCaseMngNo);
				mapDel.put("CASE_MNG_ODRNO" , sCaseMngOdrno);

				mapDel.put("DEL_YN"			, "Y");     // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				if(sPlanNo.isEmpty()) {
					sPlanNo = request.getAttribute("SRVC_PVSN_PLAN_NO").toString();
					mapDel.put("SRVC_PVSN_PLAN_NO", sPlanNo);
				}

				// 자원프로그램제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB321Data(mapDel);
				// 자원프로그램제공계획대상자 삭제 호출
				casePlanMapper.deleteSEB320Data(mapDel);
			}
		}
	}

	/**
	* @Method    : 서비스집단제공계획 상세삭제
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void deleteSrvcGrPvsnPlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 서비스집단제공계획 상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsDetailInfo");
		if (paramGroup == null) {
			throw new AppWorksException("삭제할 자료가 없습니다.", Alert.ERROR);
		}

		String sPlanNo = null; // 서비스제공계획번호
		
		Iterator<ParameterRow> deletedRows = paramGroup.getDeletedRows();

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		}

		//삭제 이벤트
		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			sPlanNo = mapDel.get("SRVC_PVSN_PLAN_NO");
			
			mapDel.put("DEL_YN"				  , "Y"); 	  // 삭제여부 셋팅			
			mapDel.put("USER_ID"			  , sUserId); // 세션 사용자ID 셋팅
			mapDel.put("DATAA_CHG_SE_CD"	  , "D");
			mapDel.put("CASE_PRGRS_STTS_SE_CD", "02");
			
			// 서비스제공계획 이력등록 호출
			casePlanMapper.insertSEB301Data(mapDel);
			// 서비스제공계획 삭제 호출
			casePlanMapper.deleteSEB300Data(mapDel);
		}

		//서비스집단제공계획 사례대상자 DataSet
		paramGroup = dataRequest.getParameterGroup("dsCaseTrprList");

		if (paramGroup != null) {
			Iterator<ParameterRow> deletedTrprRows  = paramGroup.getDeletedRows();
			//삭제 이벤트
			while (deletedTrprRows.hasNext()) {

				Map<String, String> mapDel = deletedTrprRows.next().toMap();
				sPlanNo = mapDel.get("SRVC_PVSN_PLAN_NO");

				if(!sPlanNo.isEmpty()) {

					mapDel.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅					
					mapDel.put("DATAA_CHG_SE_CD", "D"); 	// 데이터변경구분코드 셋팅					
					mapDel.put("DEL_YN"			, "Y"); 	// 삭제여부 셋팅

					// 서비스제공계획대상자 이력등록 호출
					casePlanMapper.insertSEB311Data(mapDel);
					// 서비스제공계획대상자 삭제 호출
					casePlanMapper.deleteSEB310Data(mapDel);
				}
			}
		}
		
		//자원프로그램제공계획대상자 DataSet
		paramGroup = dataRequest.getParameterGroup("dsProgramList");

		if (paramGroup != null) {
			Iterator<ParameterRow> deletedProgRows  = paramGroup.getDeletedRows();
			//삭제 이벤트
			while (deletedProgRows.hasNext()) {

				Map<String, String> mapDel = deletedProgRows.next().toMap();
				sPlanNo = mapDel.get("SRVC_PVSN_PLAN_NO");

				if(!sPlanNo.isEmpty()) {

					mapDel.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅					
					mapDel.put("DATAA_CHG_SE_CD", "D"); 	// 데이터변경구분코드 셋팅					
					mapDel.put("DEL_YN"			, "Y"); 	// 삭제여부 셋팅

					// 서비스제공계획대상자 이력등록 호출
					casePlanMapper.insertSEB321Data(mapDel);
					// 서비스제공계획대상자 삭제 호출
					casePlanMapper.deleteSEB320Data(mapDel);
				}
			}
		}
	}		

	/**
	* @Method    : 서비스집단제공계획 이력 불러오기
	* @param     : Map  : PVSN_PRNMNT_BGNG_YMD(제공예정시작일자), PVSN_PRNMNT_END_YMD(제공예정종료일자), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NM(자원멍)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectSrvcGrPvsnPlanHstrList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	

		return srvcGrPvsnPlanMapper.selectSrvcGrPvsnPlanHstrList(paramMap2);
	}
	
	/**
	* @Method    : 서비스집단제공계획 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectBizNoPlanCaseTrprList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmBizNoCaseTrpr");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcGrPvsnPlanMapper.selectBizNoPlanCaseTrprList(paramMap);
	}
}
