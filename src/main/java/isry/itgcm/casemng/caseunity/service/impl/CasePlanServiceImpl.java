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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CasePlanMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseTrmnMapper;
import isry.itgcm.casemng.caseunity.service.CasePlanService;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;


/**
 * @파일명        : CasePlanServiceImpl.java
 * @프로그램 설명 	: 사례계획 ServicImpl Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 13. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("casePlanService")
public class CasePlanServiceImpl implements CasePlanService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;
	
	@Resource(name="casePlanMapper")
    private CasePlanMapper casePlanMapper;
	
	@Resource(name="caseTrmnMapper")
	private CaseTrmnMapper caseTrmnMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name="caseRegService")
	private CaseRegService caseRegService;
	
	String sUserId = "";
	ScpDb  scpDb   = new ScpDb();
	Masking mask   = new Masking();

	/**
	* @Method    : 사례계획 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCasePlanList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;

		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수

		if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 확인해 주세요!", Alert.ERROR);
		}

		return casePlanMapper.selectCasePlanList(paramMap);
	}
	
	/**
	* @Method    : 사례계획 목표계획내용 조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectGoalPlanCn(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;

		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수

		if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 확인해 주세요!", Alert.ERROR);
		}
		
		rtn = casePlanMapper.selectGoalPlanCn(paramMap);
		
		return rtn;
	}

	/**
	* @Method    : 사례계획 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectProgramList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup  = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		return casePlanMapper.selectProgramList(paramMap);
	}
	
	/**
	* @Method    : 사례계획 프로그램 목록조회2
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectPlanProgramList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		return casePlanMapper.selectPlanProgramList(paramMap);
	}
	
	/**
	* @Method    : 사례계획이력 목록조회
	* @param     : Map  : PVSN_PRNMNT_BGNG_YMD(제공예정시작일자), PVSN_PRNMNT_END_YMD(제공예정종료일자), RESRCE_NO(자원명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCasePlanHstrList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return casePlanMapper.selectCasePlanHstrList(paramMap);
	}

	@Override
	public Map<String, Object> processCasePlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> rtnMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		}
		
		//목표계획내용 저장
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsGoalPlanCn");

		if (paramGroup != null) {
			Iterator<ParameterRow> allRows = paramGroup.getAllRows();
			while (allRows.hasNext()) {
				Map<String, String> mapAll = allRows.next().toMap();
				caseRegService.selectPrgrsStts(mapAll);
			}
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();

				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅

				// 목표계획내용 저장 호출
				casePlanMapper.saveGoalPlanCn(mapIns);
				// 목표계획내용 이력등록 호출
				caseRegMapper.insertSEB101Data(mapIns);
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();

				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅
				
				// 목표계획내용 저장 호출
				casePlanMapper.saveGoalPlanCn(mapUpd);
				// 목표계획내용 이력등록 호출
				caseRegMapper.insertSEB101Data(mapUpd);
			}			
		}
		
		// 사후계획상세자료 DataSet
		paramGroup = dataRequest.getParameterGroup("dsCasePlanList");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn = null;	// 채번번호
		String sPlanNo  = null; // 서비스제공계획번호

		List<Map<String, String>> progrmMapngList = new ArrayList<Map<String,String>>();		

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();

				sPlanNo = "";
				sPlanNo = mapIns.get("SRVC_PVSN_PLAN_NO");

				mapIns.put("USER_ID"			  , sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD"	  , "I");     // 데이터변경구분코드 셋팅
//				mapIns.put("CASE_PRGRS_STTS_SE_CD", "02");

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

				// 서비스제공계획대상자 저장 호출
				casePlanMapper.saveSEB310Data(mapIns);
				// 서비스제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB311Data(mapIns);

				List<Map<String, Object>> caseBass = caseRegMapper.selectCaseBassDetail(mapIns);
				if(caseBass.size() > 0) {
					String sCasePrgrsSttsSeCd = String.valueOf(caseBass.get(0).get("CASE_PRGRS_STTS_SE_CD"));
					if(!"12".equals(sCasePrgrsSttsSeCd) && !"02".equals(sCasePrgrsSttsSeCd)) { //사례진행상태구분코드(12:종결신청, 02:서비스계획)
						mapIns.put("CASE_PRGRS_STTS_SE_CD", "02");

						// 사례기본 사례진행상태구분 수정 호출
						caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
						// 사례기본 이력 등록
//						caseRegMapper.insertSEB101Data(mapIns);

						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapIns);
					}
				}

				if("04".equals(mapIns.get("SRVC_TYPE_SE_CD"))) {
					Map<String, String> progrmMapnMap = new HashMap<String, String>();

					progrmMapnMap.put("SRVC_PVSN_PLAN_NO"  , sPlanNo);
					progrmMapnMap.put("RESRCE_NO"          , mapIns.get("RESRCE_NO"));
					progrmMapnMap.put("CASE_PLAN_ROW_INDEX", mapIns.get("CASE_PLAN_ROW_INDEX"));

					progrmMapngList.add(progrmMapnMap);
				}
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {
				Map<String, String> mapUpd = updatedRows.next().toMap();

				sPlanNo = "";
				sPlanNo = mapUpd.get("SRVC_PVSN_PLAN_NO");

				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅

				// 서비스제공계획 저장 호출
				casePlanMapper.saveSEB300Data(mapUpd);
				// 서비스제공계획 이력등록 호출
				casePlanMapper.insertSEB301Data(mapUpd);

				// 서비스제공계획대상자 저장 호출
				casePlanMapper.saveSEB310Data(mapUpd);
				// 서비스제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB311Data(mapUpd);

				request.setAttribute("SRVC_PVSN_PLAN_NO", sPlanNo);

				if("04".equals(mapUpd.get("SRVC_TYPE_SE_CD"))) {
					Map<String, String> progrmMapnMap = new HashMap<String, String>();

					progrmMapnMap.put("SRVC_PVSN_PLAN_NO", sPlanNo);
					progrmMapnMap.put("RESRCE_NO"        , mapUpd.get("RESRCE_NO"));

					progrmMapngList.add(progrmMapnMap);
				}
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();

				sPlanNo = "";
				sPlanNo = mapDel.get("SRVC_PVSN_PLAN_NO");

				// 삭제여부 셋팅
				mapDel.put("DEL_YN"			, "Y");
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 서비스제공계획 이력등록 호출
				casePlanMapper.insertSEB301Data(mapDel);
				// 서비스제공계획 삭제 호출
				casePlanMapper.deleteSEB300Data(mapDel);

				// 서비스제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB311Data(mapDel);
				// 서비스제공계획대상자 삭제 호출
				casePlanMapper.deleteSEB310Data(mapDel);

				if("04".equals(mapDel.get("SRVC_TYPE_SE_CD"))) {
					Map<String, String> progrmMapnMap = new HashMap<String, String>();

					progrmMapnMap.put("SRVC_PVSN_PLAN_NO", sPlanNo);
					progrmMapnMap.put("RESRCE_NO"        , mapDel.get("RESRCE_NO"));

					progrmMapngList.add(progrmMapnMap);
				}
			}
		}

		//자원프로그램제공계획대상자 저장
		saveResrceProgrm(dataRequest, progrmMapngList);

		rtnMap.put("SRVC_PVSN_PLAN_NO", sPlanNo);
		return rtnMap;

	}

	//자원프로그램제공계획대상자 저장
	private void saveResrceProgrm(DataRequest dataRequest, List<Map<String, String>> list) throws Exception {

		//자원프로그램제공계획대상자 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsProgramList");
		String sPlanNo  = null; // 서비스제공계획번호
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();				
				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅

				sPlanNo = mapIns.get("SRVC_PVSN_PLAN_NO");
				if(sPlanNo.isEmpty()) {
					for(Map<String, String> map : list) {
						if(mapIns.get("RESRCE_NO")          .equals(map.get("RESRCE_NO")) &&  
						   mapIns.get("CASE_PLAN_ROW_INDEX").equals(map.get("CASE_PLAN_ROW_INDEX"))) {
							sPlanNo = map.get("SRVC_PVSN_PLAN_NO");
							break;
						}
					}

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
				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U");     // 데이터변경구분코드 셋팅

				// 자원프로그램제공계획대상자 저장 호출
				casePlanMapper.saveSEB320Data(mapUpd);
				// 자원프로그램제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB321Data(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("DEL_YN"			, "Y");     // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 자원프로그램제공계획대상자 이력등록 호출
				casePlanMapper.insertSEB321Data(mapDel);
				// 자원프로그램제공계획대상자 삭제 호출
				casePlanMapper.deleteSEB320Data(mapDel);
			}
		}
	}

	/**
	* @Method    : 서비스실행사업대상 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectSrvcExcnBizTrgtList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;

		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수

		if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 확인해 주세요!", Alert.ERROR);
		}
		
		rtn = casePlanMapper.selectSrvcExcnBizTrgtList(paramMap);
		
		return rtn;
	}

}
