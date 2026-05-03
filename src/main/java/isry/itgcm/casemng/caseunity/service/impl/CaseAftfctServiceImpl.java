/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseAftfctMapper;
import isry.itgcm.casemng.caseunity.mapper.CasePlanMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseTrmnMapper;
import isry.itgcm.casemng.caseunity.service.CaseAftfctService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.pubms.casemng.sheltraftfct.service.SheltrAftfctService;
import isry.pubmsr.casemng.recvryaftfct.service.RecvryAftfctService;
import isry.pubmt.casemng.slfrlaftfct.service.SlfrlAftfctService;


/**
 * @파일명        : CaseAftfctServiceImpl.java
 * @프로그램 설명 	: 사후관리 ServicImpl Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 31. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 * -
 */
@Service("caseAftfctService")
public class CaseAftfctServiceImpl implements CaseAftfctService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;
	
	@Resource(name="casePlanMapper")
    private CasePlanMapper casePlanMapper;

	@Resource(name="caseTrmnMapper")
    private CaseTrmnMapper caseTrmnMapper;
	
	@Resource(name="caseAftfctMapper")
    private CaseAftfctMapper caseAftfctMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;
	
	@Resource(name="sheltrAftfctService")
    private SheltrAftfctService sheltrAftfctService;
	
	@Resource(name="slfrlAftfctService")
    private SlfrlAftfctService slfrlAftfctService;
	
	@Resource(name="recvryAftfctService")
    private RecvryAftfctService recvryAftfctService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	String sUserId  = "";
	String sEnfsnNo = "";
	String sInstNo  = "";	

	/**
	* @Method    : 사후계획 목록조회
	* @param     : Map
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseAftfctPlanList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = "";
		String sCaseMngOdrno = "";
		
		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수
		
		if (sCaseMngNo==null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return caseAftfctMapper.selectCaseAftfctPlanList(paramMap);
	}

	/**
	* @Method    : 사후계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void processCaseAftfctPlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사후계획상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsAftfctPlanList");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn      = "";	// 채번번호
		String sCaseMngNo    = "";	// 사례관리번호
		String sCaseMngOdrno = "";	// 사례관리차수

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();
			
			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				String sPlanNo = mapIns.get("AFTFCT_PLAN_NO");
				sCaseMngNo     = mapIns.get("CASE_MNG_NO");
				sCaseMngOdrno  = mapIns.get("CASE_MNG_ODRNO");

				// 사례관리번호 체크
				if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
					throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}
				// 사례관리차수 체크
				if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
					throw new AppWorksException("사례관리차수는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}

				if(sPlanNo.isEmpty()) {

					// 사례종결 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "AP");				  // 사후계획번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 사례종결번호 발번
					mapIns.put("AFTFCT_PLAN_NO",   sWprkSqn);
				}

				// 세션 사용자ID 셋팅
				mapIns.put("USER_ID"			  , sUserId);
				// 데이터변경구분코드 셋팅
				mapIns.put("DATAA_CHG_SE_CD"	  , "I");
				// 사례진행상태구분코드 셋팅(06:사후관리(계획))
				mapIns.put("CASE_PRGRS_STTS_SE_CD", "06");

				// 사후계획 상세저장 호출
				caseAftfctMapper.saveCaseAftfctPlanDetail(mapIns);
				// 사후계획 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPlanHistory(mapIns);

				// 사례기본 사례진행상태구분 수정 호출
				caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);				
				// 사례관리이력
				Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapIns);
				if(hstrMap != null) {
					String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
					if(!"06".equals(nowStts)) {
						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapIns);
					}
				}
			}
			
			//수정 이벤트
			while (updatedRows.hasNext()) {
				
				Map<String, String> mapUpd = updatedRows.next().toMap();
				// 세션 사용자ID 셋팅
				mapUpd.put("USER_ID"		, sUserId);
				// 데이터변경구분코드 셋팅
				mapUpd.put("DATAA_CHG_SE_CD", "U");

				// 사후계획 상세저장 호출
				caseAftfctMapper.saveCaseAftfctPlanDetail(mapUpd);
				// 사후계획 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPlanHistory(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				// 삭제여부 셋팅
				mapDel.put("DEL_YN"			, "Y");
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 사후계획 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPlanHistory(mapDel);
				// 사후계획 삭제 호출
				caseAftfctMapper.deleteCaseAftfctPlanDetail(mapDel);

			}
		}
	}

	/**
	* @Method    : 사후관리 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseAftfctMngList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = "";
		String sCaseMngOdrno = "";
		
		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수
		
		if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수 항목입니다. 확인해 주세요!", Alert.ERROR);
		}
		// 사례관리차수 체크
		if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
			throw new AppWorksException("사례관리차수는 필수 항목입니다. 확인해 주세요!", Alert.ERROR);
		}
		
		List<Map<String, Object>> rtn = caseAftfctMapper.selectCaseAftfctMngList(paramMap);

		return rtn;
	}

	/**
	* @Method    : 사후관리 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void processCaseAftfctMngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사후관리상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsAftfctMngList");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn      = "";	// 채번번호
		String sCaseMngNo    = "";	// 사례관리번호
		String sCaseMngOdrno = "";	// 사례관리차수

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				String sMngNo  = mapIns.get("AFTFCT_MNG_NO");
				sCaseMngNo     = mapIns.get("CASE_MNG_NO");
				sCaseMngOdrno  = mapIns.get("CASE_MNG_ODRNO");

				// 사례관리번호 체크
				if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
					throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}
				// 사례관리차수 체크
				if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
					throw new AppWorksException("사례관리차수는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}

				if(sMngNo.isEmpty()) {
					// 사후관리번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "AM");				  // 사후관리번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 사후관리번호 발번
					mapIns.put("AFTFCT_MNG_NO",   sWprkSqn);
				}

				mapIns.put("USER_ID"			  , sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD"	  , "I"); 	  // 데이터변경구분코드 셋팅				
//				mapIns.put("CASE_PRGRS_STTS_SE_CD", "07"); 	  // 사례진행상태구분코드 셋팅(07:사후관리(실행))

				// 사후관리 상세저장 호출
				caseAftfctMapper.saveCaseAftfctMngDetail(mapIns);
				// 사후관리 이력등록 호출
				caseAftfctMapper.insertCaseAftfctMngHistory(mapIns);

				// 사후담당자 저장 호출
				caseAftfctMapper.saveCaseAftfctPicDetail(mapIns);
				// 사후담당자 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPicHistory(mapIns);

			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("USER_ID"		, sUserId);
				mapUpd.put("DATAA_CHG_SE_CD", "U");

				// 사후관리 상세저장 호출
				caseAftfctMapper.saveCaseAftfctMngDetail(mapUpd);
				// 사후관리 이력등록 호출
				caseAftfctMapper.insertCaseAftfctMngHistory(mapUpd);

				// 사후담당자 저장 호출
				caseAftfctMapper.saveCaseAftfctPicDetail(mapUpd);
				// 사후담당자 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPicHistory(mapUpd);
			}
			
			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				// 삭제여부 셋팅
				mapDel.put("DEL_YN"			, "Y");
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 사후관리 이력등록 호출
				caseAftfctMapper.insertCaseAftfctMngHistory(mapDel);
				// 사후관리 삭제 호출
				caseAftfctMapper.deleteCaseAftfctMngDetail(mapDel);

				//사후담당자 삭제
				caseAftfctMapper.deleteCaseAftfctPicDetail(mapDel);
				// 사후담당자 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPicHistory(mapDel);
			}
		}
	}

	/**
	* @Method    : 사후종료 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseAftfctTrmnList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = "";
		String sCaseMngOdrno = "";
		
		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수
		
		// 사례관리번호 체크
		if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		// 사례관리차수 체크
		if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
			throw new AppWorksException("사례관리차수는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		return caseAftfctMapper.selectCaseAftfctTrmnList(paramMap);
	}

	/**
	* @Method    : 사후종료 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void processCaseAftfctTrmnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사후종료상세자료 DataSet
//		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsAftfctTrmn");
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsAftfctTrmnList");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn       = "";	// 채번번호
		String sCaseMngNo     = "";	// 사례관리번호
		String sCaseMngOdrno  = "";	// 사례관리차수
		String sAftfctTrmnYmd = "";	// 사후종결일자
		
		String sUntTaskwkSeCd = ""; // 단위업무구분코드

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId  	   = loginVO.getId();
			sEnfsnNo 	   = loginVO.getEnfsnNo();
			sInstNo  	   = String.valueOf(loginVO.getInstNo());
			sUntTaskwkSeCd = loginVO.getUntTaskwk();

		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 사후종결 저장 전 사례 상태 확인(SEC320에 데이터가 있는 경우 ERROR 발생)
		if (paramGroup != null) {
			Iterator<ParameterRow> allRows = paramGroup.getAllRows();
			while (allRows.hasNext()) {
				Map<String, String> mapAll = allRows.next().toMap();
				int aftfctCnt = caseAftfctMapper.selectAftfctCnt(mapAll);
				if(aftfctCnt > 0 ) {
					throw new AppWorksException("이미 사후종결된 사례입니다.", Alert.ERROR);
				}
			}
		}
		
		// 사후종결 저장 전 사례 상태 확인(SEC320에 데이터가 있는 경우 ERROR 발생) 끝
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				String sTrmnNo = mapIns.get("AFTFCT_TRMN_NO");
				sCaseMngNo     = mapIns.get("CASE_MNG_NO");
				sCaseMngOdrno  = mapIns.get("CASE_MNG_ODRNO");
				sAftfctTrmnYmd = mapIns.get("AFTFCT_TRMN_YMD");

				// 사례관리번호 체크
				if(sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
					throw new AppWorksException("사례관리번호는 필수 항목입니다. 확인해 주세요!", Alert.ERROR);
				}
				// 사례관리차수 체크
				if(sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
					throw new AppWorksException("사례관리차수는 필수 항목입니다. 확인해 주세요!", Alert.ERROR);
				}

				// 사후종결번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();

				seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
				seqMap.put("RENU_NO_SE_CD", "AD");				  // 사후종결번호 채번코드
				seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 사후종결번호 발번
				mapIns.put("AFTFCT_TRMN_NO" , sWprkSqn);
				mapIns.put("USER_ID"	    , sUserId); // 세션 사용자ID 셋팅
				mapIns.put("DATAA_CHG_SE_CD", "I");	    // 데이터변경구분코드 셋팅

				// 사후종료 상세저장 호출
				caseAftfctMapper.saveCaseAftfctTrmnDetail(mapIns);
				// 사후종료 이력등록 호출
				caseAftfctMapper.insertCaseAftfctTrmnHistory(mapIns);

				String sCasePrgrsSttsSeCd = "";
				String sAftfctTrmnSeCd    = mapIns.get("AFTFCT_TRMN_SE_CD");

				//사후종결구분코드(05:사후종결)
				if("05".equals(sAftfctTrmnSeCd)) { 		 
					sCasePrgrsSttsSeCd = "08"; //사례진행상태구분코드(08:사례(사후)관리종결)
				}
				//사후종결구분코드(06:재개입)
				else if("06".equals(sAftfctTrmnSeCd)){
					sCasePrgrsSttsSeCd = "10"; //사례진행상태구분코드(10:재개입)
				}
				mapIns.put("CASE_PRGRS_STTS_SE_CD", sCasePrgrsSttsSeCd);
				mapIns.put("DATAA_CHG_SE_CD"	  , "U");

				// 사례기본 사례진행상태구분 수정 호출
				caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
				// 사례기본이력 생성(SEB101)
				caseRegMapper.insertSEB101Data(mapIns);

				// 사례관리이력 생성
				Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapIns);
				if(hstrMap != null) {
					String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
					if(!nowStts.equals(mapIns.get("CASE_PRGRS_STTS_SE_CD"))) {
						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapIns);
					}
				}

				//대상자T 종결사례관리번호/종결사례관리차수 UPDATE
				mapIns.put("TRMN_CASE_MNG_NO"   , sCaseMngNo);
				mapIns.put("TRMN_CASE_MNG_ODRNO", sCaseMngOdrno);

				//대상자정보 수정
				caseRegMapper.updateSEA200Data(mapIns);
				//대상자정보 이력 등록
				caseRegMapper.insertSEA201Data(mapIns);

				if("10".equals(sCasePrgrsSttsSeCd)) {
					String sNewCaseMngOdrno = processCaseReg(mapIns);
					LOGGER.debug("신규_사례관리차수 : " + sNewCaseMngOdrno);

					try {
						//청소년쉼터
						if("U04".equals(sUntTaskwkSeCd)) {
							sheltrAftfctService.processCaseEsntalRegString(request, sCaseMngNo, sCaseMngOdrno, sNewCaseMngOdrno);

						//청소년자립지원
						} else if("U05".equals(sUntTaskwkSeCd)) {
							slfrlAftfctService.processCaseEsntalRegString(request, sCaseMngNo, sCaseMngOdrno, sNewCaseMngOdrno);

						//청소년회복지원	
						} else if("U06".equals(sUntTaskwkSeCd)) {
							recvryAftfctService.processCaseEsntalRegString(request, sCaseMngNo, sCaseMngOdrno, sNewCaseMngOdrno);
						}
						
					} catch(Exception e) {
						throw new AppWorksException("단위업무(" + sUntTaskwkSeCd + ")영역 재개입 처리 중 오류가 발생하였습니다.", Alert.ERROR);
					}
				}
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				sCaseMngNo    = mapUpd.get("CASE_MNG_NO");
				sCaseMngOdrno = mapUpd.get("CASE_MNG_ODRNO");

				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅
				mapUpd.put("DATAA_CHG_SE_CD", "U");

				// 사후종료 상세저장 호출
				caseAftfctMapper.saveCaseAftfctTrmnDetail(mapUpd);
				// 사후종료 이력등록 호출
				caseAftfctMapper.insertCaseAftfctTrmnHistory(mapUpd);

				String sCasePrgrsSttsSeCd = "";
				String sAftfctTrmnSeCd    = mapUpd.get("AFTFCT_TRMN_SE_CD");

				//사후종결구분코드(05:사후종결)
				if("05".equals(sAftfctTrmnSeCd)) {
					sCasePrgrsSttsSeCd = "08"; //사례진행상태구분코드(08:사례(사후)관리종결)
				}
				//사후종결구분코드(06:재개입)
				else if("06".equals(sAftfctTrmnSeCd)){
					sCasePrgrsSttsSeCd = "10"; //사례진행상태구분코드(10:재개입)
				}
				mapUpd.put("CASE_PRGRS_STTS_SE_CD", sCasePrgrsSttsSeCd);
				mapUpd.put("DATAA_CHG_SE_CD"	  , "U");

				// 사례기본 사례진행상태구분 수정 호출
				caseTrmnMapper.updateCasePrgrsSttsSeCd(mapUpd);
				// 사례기본이력 생성(SEB101)
				caseRegMapper.insertSEB101Data(mapUpd);

				// 사례기본 사례진행상태구분 수정 호출
				caseTrmnMapper.updateCasePrgrsSttsSeCd(mapUpd);
				// 사례관리이력
				Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapUpd);
				if(hstrMap != null) {
					String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
					if(!nowStts.equals(mapUpd.get("CASE_PRGRS_STTS_SE_CD"))) {
						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapUpd);
					}
				}

				//대상자T 종결사례관리번호/종결사례관리차수 UPDATE
				mapUpd.put("TRMN_CASE_MNG_NO"   , sCaseMngNo);
				mapUpd.put("TRMN_CASE_MNG_ODRNO", sCaseMngOdrno);

				//대상자정보 수정
				caseRegMapper.updateSEA200Data(mapUpd);
				//대상자정보 이력 등록
				caseRegMapper.insertSEA201Data(mapUpd);

				if("10".equals(sCasePrgrsSttsSeCd)) {
					String sNewCaseMngOdrno = processCaseReg(mapUpd);
					LOGGER.debug("신규_사례관리차수 : " + sNewCaseMngOdrno);

					try {
						//청소년쉼터
						if("U04".equals(sUntTaskwkSeCd)) {
							sheltrAftfctService.processCaseEsntalRegString(request, sCaseMngNo, sCaseMngOdrno, sNewCaseMngOdrno);

						//청소년자립지원
						} else if("U05".equals(sUntTaskwkSeCd)) {
							slfrlAftfctService.processCaseEsntalRegString(request, sCaseMngNo, sCaseMngOdrno, sNewCaseMngOdrno);

						//청소년회복지원	
						} else if("U06".equals(sUntTaskwkSeCd)) {
							recvryAftfctService.processCaseEsntalRegString(request, sCaseMngNo, sCaseMngOdrno, sNewCaseMngOdrno);
						}

					} catch(Exception e) {
						throw new AppWorksException("단위업무(" + sUntTaskwkSeCd + ")영역 재개입 처리 중 오류가 발생하였습니다.", Alert.ERROR);
					}
				}
			}
		}

		request.setAttribute("CASE_MNG_NO"   , sCaseMngNo);
		request.setAttribute("CASE_MNG_ODRNO", sCaseMngOdrno);

		// 사후종료/재사정 심사담당자 저장
//		processCaseReasseSrngPicDetail(request, dataRequest, "T");

	}

	/**
	* @Method    : 재사정 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseReasseList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = "";
		String sCaseMngOdrno = "";
		
		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수
		
		if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return caseAftfctMapper.selectCaseReasseList(paramMap);
	}

	/**
	* @Method    : 재사정 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void processCaseReasseDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 재사정상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCaseReasse");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn      = "";	// 채번번호
		String sCaseMngNo    = "";	// 사례관리번호
		String sCaseMngOdrno = "";	// 사례관리차수

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			
			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				String sReasseNo = mapIns.get("REASSE_NO");			// 재사정번호
				String sSrngYmd  = mapIns.get("REASSE_SRNG_YMD");	// 재사정심사일자
				String sBgngYmd  = mapIns.get("CASE_MNG_BGNG_YMD");	// 사례관리시작일자
				sCaseMngNo       = mapIns.get("CASE_MNG_NO");
				sCaseMngOdrno    = mapIns.get("CASE_MNG_ODRNO");

				// 사례관리번호 체크
				if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
					throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}
				// 사례관리차수 체크
				if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
					throw new AppWorksException("사례관리차수는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}

				// 사후관리번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();

				seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
				seqMap.put("RENU_NO_SE_CD", "RA");				  // 사후관리번호 채번코드
				seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 사후관리번호 발번
				mapIns.put("REASSE_NO",   sWprkSqn);

				// 세션 사용자ID 셋팅
				mapIns.put("USER_ID"		, sUserId);
				mapIns.put("DATAA_CHG_SE_CD", "I");

				String sCasePrgrsSttsSeCd = "";
				// 사례진행상태구분코드 셋팅
				if(!sSrngYmd.isEmpty()) {
					mapIns.put("CASE_PRGRS_STTS_SE_CD", "10");	   // 사례진행상태구분코드(10:재사정승인)
					sCasePrgrsSttsSeCd = "10";

					if(!sBgngYmd.isEmpty()) {
						mapIns.put("CASE_PRGRS_STTS_SE_CD", "11"); // 사례진행상태구분코드(11:사례재등록)
						sCasePrgrsSttsSeCd = "11";
					}
				} else {
					mapIns.put("CASE_PRGRS_STTS_SE_CD", "09");	   // 사례진행상태구분코드(09:재사정신청)
					sCasePrgrsSttsSeCd = "09";
				}

				// 재사정 상세저장 호출
				caseAftfctMapper.saveCaseReasseDetail(mapIns);
				// 재사정 이력등록 호출
				caseAftfctMapper.insertCaseReasseHistory(mapIns);
				
				// 사례기본 사례진행상태구분 수정 호출
				caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
				// 사례관리이력
				Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapIns);
				if(hstrMap != null) {
					String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
					if(!nowStts.equals(sCasePrgrsSttsSeCd)) {
						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapIns);
					}
				}
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {
				
				Map<String, String> mapUpd = updatedRows.next().toMap();
				sCaseMngNo    = mapUpd.get("CASE_MNG_NO");
				sCaseMngOdrno = mapUpd.get("CASE_MNG_ODRNO");

				mapUpd.put("USER_ID"		, sUserId);
				mapUpd.put("DATAA_CHG_SE_CD", "U");

				// 재사정 상세저장 호출
				caseAftfctMapper.saveCaseReasseDetail(mapUpd);
				// 재사정 이력등록 호출
				caseAftfctMapper.insertCaseReasseHistory(mapUpd);

				// 사례기본 사례진행상태구분 수정 호출
				caseTrmnMapper.updateCasePrgrsSttsSeCd(mapUpd);
				// 사례관리이력
				Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapUpd);
				if(hstrMap != null) {
					String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
					if(!nowStts.equals(mapUpd.get("CASE_PRGRS_STTS_SE_CD"))) {
						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapUpd);
					}
				}
			}

			request.setAttribute("CASE_MNG_NO"   , sCaseMngNo);
			request.setAttribute("CASE_MNG_ODRNO", sCaseMngOdrno);

			// 사후종료/재사정 심사담당자 저장
			processCaseReasseSrngPicDetail(request, dataRequest, "R");
		}
	}

	/**
	* @Method    : 사후담당자 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseAftfctPicList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;
		
		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수
		
		if (sCaseMngNo==null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return caseAftfctMapper.selectCaseAftfctPicList(paramMap);
	}

	/**
	* @Method    : 사후담당자 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public void processCaseAftfctPicDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCaseAftfctPicList");
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();
			
			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				//사례관리번호/사례관리차수 설정
				String sCaseMngNo    = mapIns.get("CASE_MNG_NO");
				String sCaseMngOdrno = mapIns.get("CASE_MNG_ODRNO");

				// 사례관리번호 체크
				if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
					throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}
				// 사례관리차수 체크
				if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
					throw new AppWorksException("사례관리차수는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
				}
				
				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅				

				// 사후담당자 저장 호출
				caseAftfctMapper.saveCaseAftfctPicDetail(mapIns);
				// 사후담당자 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPicHistory(mapIns);
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {
				
				Map<String, String> mapUpd = updatedRows.next().toMap();
				
				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅

				// 사후담당자 저장 호출
				caseAftfctMapper.saveCaseAftfctPicDetail(mapUpd);
				// 사후담당자 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPicHistory(mapUpd);
			}
			
			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();				
				mapDel.put("DEL_YN"			, "Y");     // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");
				
				// 사후담당자 이력등록 호출
				caseAftfctMapper.insertCaseAftfctPicHistory(mapDel);
				// 사후담당자 삭제 호출
				caseAftfctMapper.deleteCaseAftfctPicDetail(mapDel);

			}
		}
	}

	/**
	* @Method    : 사후종결 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectAftfctTrmnSrngPicList(DataRequest dataRequest) throws Exception {

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
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return caseAftfctMapper.selectCaseReasseSrngPicList(paramMap);
	}

	/**
	* @Method    : 재사정 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseReasseSrngPicList(DataRequest dataRequest) throws Exception {

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
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return caseAftfctMapper.selectCaseReasseSrngPicList(paramMap);
	}
	
	/**
	* @Method    : 재사정 심사담당자 저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	private void processCaseReasseSrngPicDetail(HttpServletRequest request, DataRequest dataRequest, String sFlag) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCaseReasseSrngPicList");
		if("T".equals(sFlag)) paramGroup = dataRequest.getParameterGroup("dsAftfctTrmnSrngPicList");
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();
			
			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				//사례관리번호/사례관리차수 설정
				String sCaseMngNo    = mapIns.get("CASE_MNG_NO");
				String sCaseMngOdrno = mapIns.get("CASE_MNG_ODRNO");
				
				if(sCaseMngNo.isEmpty()) {
					//전달받은 사례관리번호/사례관리차수로 설정
					sCaseMngNo    = request.getAttribute("CASE_MNG_NO").toString();
					sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				}

				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅
				
				// 사후종료 심사자 저장 호출
				caseAftfctMapper.saveCaseReasseSrngDetail(mapIns);
				// 사후종료 심사자 이력등록 호출
				caseAftfctMapper.insertCaseReasseSrngHistory(mapIns);
			}
			
			//수정 이벤트
			while (updatedRows.hasNext()) {
				
				Map<String, String> mapUpd = updatedRows.next().toMap();				
				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅
				
				// 사후종료 심사자 저장 호출
				caseAftfctMapper.saveCaseReasseSrngDetail(mapUpd);
				// 사후종료 심사자 이력등록 호출
				caseAftfctMapper.insertCaseReasseSrngHistory(mapUpd);
			}
			
			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();				
				mapDel.put("DEL_YN"			, "Y"); 	// 삭제여부 셋팅				
				mapDel.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapDel.put("DATAA_CHG_SE_CD", "D"); 	// 데이터변경구분코드 셋팅
				
				// 사후종료 심사자 이력등록 호출
				caseAftfctMapper.insertCaseReasseSrngHistory(mapDel);
				// 사후종료 심사자 삭제 호출
				caseAftfctMapper.deleteCaseReasseSrngDetail(mapDel);

			}
		}
	}

	/**
	 * @Method명 : processCaseReg
	 * @param paramMap
	 * @return sNewCaseMngOdrno : 신규_사례관리차수
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 12. 16.
	 * @Method설명 : 사후_재개입 처리시 사례관리차수 증가 후 사례관리_등록 관련 TABLE COPY(SEB100:사례기본, SEB120:서비스사업대상자, SEB130:문제상태및원인, SEB150:사례담당자)
	 */
	public String processCaseReg(Map<String, String> map) throws Exception {

		LOGGER.debug("============ 재개입 후 신규 사례관리_등록 생성 START ============");

		if(map == null) {
			throw new AppWorksException("처리대상이 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}

		String sCaseMngNo    	= null;
		String sCaseMngOdrno 	= null;
		String sNewCaseMngOdrno = null;

		sCaseMngNo    = map.get("CASE_MNG_NO");    //사례관리번호
		sCaseMngOdrno = map.get("CASE_MNG_ODRNO"); //사례관리차수

		if(sCaseMngNo == null || "".equals(sCaseMngNo) || "null".equals(sCaseMngNo)) {
			throw new AppWorksException("사례관리번호가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}
		if(sCaseMngOdrno == null || "".equals(sCaseMngOdrno) || "null".equals(sCaseMngOdrno)) {
			throw new AppWorksException("사례관리차수가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);
		String sSysdate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(cal.getTime());

		Map<String, String> cndMap = new HashMap<String, String>();
		cndMap.put("CASE_MNG_NO"   , sCaseMngNo);
		cndMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
		cndMap.put("USER_ID" 	   , sUserId);
		cndMap.put("MDFCN_DT"      , sSysdate);

		List<Map<String, Object>> caseList = caseRegMapper.selectCaseBassDetail(cndMap);
		if(caseList.size() > 0) {
			Map<String, String> caseMap = new HashMap<String, String>();

			Iterator iter = caseList.get(0).keySet().iterator();
			while(iter.hasNext()) {
				String key   = (String)iter.next();
				String value = "";
				if(caseList.get(0).get(key) != null) {
					value = caseList.get(0).get(key).toString();
				}

				caseMap.put(key, value);
			}

			sNewCaseMngOdrno = String.valueOf(Integer.parseInt(sCaseMngOdrno)+1);

			caseMap.put("CASE_MNG_ODRNO"		, sNewCaseMngOdrno); 	//사례관리차수
			caseMap.put("CASE_BGNG_YMD"		    , DateUtil.getToday());	//사례시작일자
			caseMap.put("CASE_TRMN_YMD" 		, "");					//사례종결일자
			caseMap.put("AFTFCT_MNG_YN" 		, "N");					//사후관리여부
			caseMap.put("CASE_PRGRS_STTS_SE_CD" , "01");				//사례진행상태구분코드(01:사례등록)
			caseMap.put("CASE_BGNG_HR" 			, ""); 					//사례시작시간
			caseMap.put("CASE_TRMN_HR" 			, "");					//사례종결시간
			caseMap.put("USER_ID" 				, sUserId);				//USER ID
			caseMap.put("DATAA_CHG_SE_CD"		, "I");					//데이터변경(이력) 구분(I:신규)

			//1.사례기본(SEB100)
			//1.1.사례기본 생성
			caseRegMapper.insertSEB100Data(caseMap);
			//1.2.사례기본이력 생성(SEB101)
			caseRegMapper.insertSEB101Data(caseMap);

			/* 2.사례담당자(SEB150)
			 *  - 사후관리 재개입 처리한 종사자만 주담당자로 등록
			*/
			Map<String, String> casePicMap = new HashMap<String, String>();
			casePicMap.put("CASE_PIC_NO"	    , sEnfsnNo);			//사례담당자번호
			casePicMap.put("CASE_MNG_NO"	  	, sCaseMngNo);			//사례관리번호
			casePicMap.put("CASE_MNG_ODRNO"	  	, sNewCaseMngOdrno);	//사례관리차수
			casePicMap.put("PCHPRS_YN"	  	    , "Y");					//주담당자여부
			casePicMap.put("PCHPRS_DSGN_YMD"  	, DateUtil.getToday()); //주담당자지정일자
			casePicMap.put("PCHPRS_CNCLTN_YMD"	, ""); 				    //주담당자해지일자
			casePicMap.put("PIC_DSGN_YMD"     	, DateUtil.getToday()); //담당자지정일자
			casePicMap.put("PIC_CNCLTN_PRCS_YMD", ""); 					//담당자해지처리일자
			casePicMap.put("PIC_INST_NO"		, sInstNo);				//담당자기관번호
			casePicMap.put("REG_AUTHRT_YN"		, "Y");					//등록권한여부
			casePicMap.put("PLAN_AUTHRT_YN"		, "Y");					//계획권한여부
			casePicMap.put("EXCN_AUTHRT_YN"		, "Y");					//실행권한여부
			casePicMap.put("OUTC_AUTHRT_YN"		, "Y");					//성과권한여부
			casePicMap.put("TRMN_AUTHRT_YN"		, "Y");					//종결권한여부
			casePicMap.put("AFTFCT_AUTHRT_YN"	, "Y");					//사후권한여부
			casePicMap.put("USER_ID" 		  	, sUserId);			  	//USER ID
			casePicMap.put("DATAA_CHG_SE_CD"  	, "I");				  	//데이터변경(이력) 구분(I:신규)

			//2.1.사례담당자 생성
			caseRegMapper.insertSEB150Data(casePicMap);					
			//2.2.사례담당자이력 생성
			caseRegMapper.insertSEB151Data(casePicMap);
			
			//3.사업등록 목록 - 서비스사업대상자(SEB120)
			List<Map<String, Object>> srvcBizTrprList = caseRegMapper.selectBizRegList(cndMap);
			for(Map<String, Object> bizMap : srvcBizTrprList) {
				Map<String, String> caseBizMap = new HashMap<String, String>();
				caseBizMap.put("NEW_SRVC_EXCN_BIZ_NO", String.valueOf(bizMap.get("SRVC_EXCN_BIZ_NO")));
				caseBizMap.put("CASE_MNG_NO"		 , sCaseMngNo);
				caseBizMap.put("CASE_MNG_ODRNO"		 , sNewCaseMngOdrno);
				caseBizMap.put("DEL_YN"		   		 , "N");
				caseBizMap.put("USER_ID" 		  	 , sUserId);

				//3.1.서비스사업대상자 생성
				caseRegMapper.insertSEB120Data(caseBizMap);
			}

			//4.문제상태및원인(SEB130)
			List<Map<String, Object>> caseYngbgsList = caseRegMapper.selectCaseYngbgsList(cndMap);
			for(Map<String, Object> yngbgsMap : caseYngbgsList) {

				//4.1.문제상태원인번호 신규 채번
				String sProbmSttsCasNo = "";

				Map<String, String> seqMap = new HashMap<>();
				seqMap.put("RENU_NO_SE_CD", "PR");
				seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자
				seqMap.put("USER_ID"	  , sUserId);

				Map<String, Object> valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);

				//문제상태원인번호 설정
				sProbmSttsCasNo = String.valueOf(valMap.get("RENU_NO"));

				Map<String, String> caseYngbgsMap = new HashMap<String, String>();

				iter = null;
				iter = yngbgsMap.keySet().iterator();
				while(iter.hasNext()) {
					String key   = (String)iter.next();
					String value = "";
					if(yngbgsMap.get(key) != null) {
						value = yngbgsMap.get(key).toString();
					}

					caseYngbgsMap.put(key, value);
				}

				caseYngbgsMap.put("PROBM_STTS_CAS_NO", sProbmSttsCasNo);
				caseYngbgsMap.put("CASE_MNG_ODRNO"	 , sNewCaseMngOdrno);
				caseYngbgsMap.put("USER_ID" 		 , sUserId);
				caseYngbgsMap.put("DATAA_CHG_SE_CD"  , "I");

				//4.2.문제상태원인 등록
				caseRegMapper.insertCaseYngbgs(caseYngbgsMap);
				//4.3.문제상태원인이력 저장
				caseRegMapper.insertCaseYngbgsHstr(caseYngbgsMap);				
			}

			//5.사례관리이력 생성
			caseRegMapper.insertSEB110Data(caseMap);

			//6.대상자T 종결사례관리번호/종결사례관리차수 UPDATE
			caseMap.put("TRMN_CASE_MNG_NO"   , "");
			caseMap.put("TRMN_CASE_MNG_ODRNO", "");
			caseMap.put("MDFCN_DT"	         , sSysdate);
			caseMap.put("DATAA_CHG_SE_CD"	 , "U");

			//6.1.대상자정보 수정
			caseRegMapper.updateSEA200Data(caseMap);
			//6.2.대상자정보 이력 등록
			caseRegMapper.insertSEA201Data(caseMap);

			//7.사례_계획 공통정보 생성
			map.put("NEW_CASE_MNG_ODRNO", sNewCaseMngOdrno);

			processCasePlan(map);			
		}

		LOGGER.debug("============ 재개입 후 신규 사례관리_등록 생성 END ============");
		
		return sNewCaseMngOdrno;
	}

	/**
	 * @Method명 : processCasePlan
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 12. 16.
	 * @Method설명 : 사후_재개입 처리시 사례관리차수 증가 후 사례관리_계획 관련 TABLE COPY(SEB300:서비스제공계획, SEB310:서비스제공계획대상자, SEB320:자원프로그램제공계획대상자)
	 */
	public void processCasePlan(Map<String, String> map) throws Exception {

		LOGGER.debug("============ 재개입 후 신규 사례관리_계획 생성 START ============");

		if(map == null) {
			throw new AppWorksException("처리대상이 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}

		String sCaseMngNo    	= null;
		String sCaseMngOdrno 	= null;
		String sNewCaseMngOdrno = null;

		sCaseMngNo    	 = map.get("CASE_MNG_NO");    	  //사례관리번호
		sCaseMngOdrno 	 = map.get("CASE_MNG_ODRNO"); 	  //사례관리차수
		sNewCaseMngOdrno = map.get("NEW_CASE_MNG_ODRNO"); //NEW_사례관리차수

		if(sCaseMngNo == null || "".equals(sCaseMngNo) || "null".equals(sCaseMngNo)) {
			throw new AppWorksException("사례관리번호가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}
		if(sCaseMngOdrno == null || "".equals(sCaseMngOdrno) || "null".equals(sCaseMngOdrno)) {
			throw new AppWorksException("사례관리차수가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}
		if(sNewCaseMngOdrno == null || "".equals(sNewCaseMngOdrno) || "null".equals(sNewCaseMngOdrno)) {
			throw new AppWorksException("신규 사례관리차수가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);
		String sSysdate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(cal.getTime());

		Map<String, String> cndMap = new HashMap<String, String>();
		cndMap.put("CASE_MNG_NO"   , sCaseMngNo);
		cndMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
		cndMap.put("USER_ID" 	   , sUserId);
		cndMap.put("MDFCN_DT"      , sSysdate);

		//1.서비스제공계획대상자(SEB310) 목록 조회
		List<Map<String, Object>> srvcPvsnPlanTrprList = casePlanMapper.selectSrvcPvsnPlanTrprList(cndMap);
		for(Map<String, Object> srvcPvsnPlanTrprMap : srvcPvsnPlanTrprList) {

			Map<String, String> srvcPvsnCndMap = new HashMap<String, String>();
			srvcPvsnCndMap.put("SRVC_PVSN_PLAN_NO", String.valueOf(srvcPvsnPlanTrprMap.get("SRVC_PVSN_PLAN_NO")));
			srvcPvsnCndMap.put("RESRCE_NO"		  , String.valueOf(srvcPvsnPlanTrprMap.get("RESRCE_NO")));

			//2.사례관리번호/차수 기준으로 조회된 서비스제공계획번호,자원번호로 서비스제공계획(SEB300) 목록 조회
			List<Map<String, Object>> srvcPvsnPlanList = casePlanMapper.selectSrvcPvsnPlanList(srvcPvsnCndMap);
			for(Map<String, Object> srvcPvsnPlanMap : srvcPvsnPlanList) {
				Map<String, String> casePlanMap = new HashMap<String, String>();

				Iterator iter = srvcPvsnPlanMap.keySet().iterator();
				while(iter.hasNext()) {
					String key   = (String)iter.next();
					String value = "";
					if(srvcPvsnPlanMap.get(key) != null) {
						value = srvcPvsnPlanMap.get(key).toString();
					}

					casePlanMap.put(key, value);
				}

				//3.서비스제공계획번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();

				seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
				seqMap.put("RENU_NO_SE_CD", "SP");				  // 서비스제공계획번호 채번코드
				seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

				//채번서비스 호출
				valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);

				String sSrvcPvsnPlanNo = "";
					   sSrvcPvsnPlanNo = String.valueOf(valMap.get("RENU_NO"));

				casePlanMap.put("SRVC_PVSN_PLAN_NO", sSrvcPvsnPlanNo);	   //서비스제공계획번호
				casePlanMap.put("PLAN_FNDNG_YMD"   , DateUtil.getToday()); //계획수립일자
				casePlanMap.put("PIC_NO"   		   , sEnfsnNo); 		   //담당자번호
				casePlanMap.put("TKCG_INST_NO"     , sInstNo);			   //담당기관번호
				casePlanMap.put("SRVC_GR_PVSN_YN"  , "N"); 				   //서비스집단제공여부
				casePlanMap.put("DEL_YN"  		   , "N"); 				   //삭제여부
				casePlanMap.put("USER_ID" 	   	   , sUserId);
				casePlanMap.put("DATAA_CHG_SE_CD"  , "I");

				//4.서비스제공계획(SEB300)
				//4.1.서비스제공계획 저장 호출
				casePlanMapper.saveSEB300Data(casePlanMap);
				//4.2.서비스제공계획 이력 등록 호출
				casePlanMapper.insertSEB301Data(casePlanMap);

				//5.서비스제공계획대상자(SEB310)
				Map<String, String> casePlanTrprMap = new HashMap<String, String>();
				casePlanTrprMap.put("SRVC_PVSN_PLAN_NO", sSrvcPvsnPlanNo);										//서비스제공계획번호
				casePlanTrprMap.put("CASE_MNG_NO"	   , sCaseMngNo);	    									//사례관리번호
				casePlanTrprMap.put("CASE_MNG_ODRNO"   , sNewCaseMngOdrno); 									//사례관리차수
				casePlanTrprMap.put("RESRCE_NO"   	   , String.valueOf(srvcPvsnPlanTrprMap.get("RESRCE_NO"))); //자원번호
				casePlanTrprMap.put("DEL_YN"  		   , "N"); 				   									//삭제여부
				casePlanTrprMap.put("USER_ID" 	   	   , sUserId);
				casePlanTrprMap.put("DATAA_CHG_SE_CD"  , "I");

				//5.1.서비스제공계획대상자 저장 호출
				casePlanMapper.saveSEB310Data(casePlanTrprMap);
				//5.2.서비스제공계획대상자 이력 등록 호출
				casePlanMapper.insertSEB311Data(casePlanTrprMap);

				//6.자원프로그램제공계획대상자(SEB320)
				Map<String, String> progrmPlanCndMap = new HashMap<String, String>();
				progrmPlanCndMap.put("SRVC_PVSN_PLAN_NO", String.valueOf(srvcPvsnPlanTrprMap.get("SRVC_PVSN_PLAN_NO"))); //서비스제공계획번호
				progrmPlanCndMap.put("RESRCE_NO"		, String.valueOf(srvcPvsnPlanTrprMap.get("RESRCE_NO")));		 //자원번호
				progrmPlanCndMap.put("CASE_MNG_NO"	    , sCaseMngNo);	    											 //사례관리번호
				progrmPlanCndMap.put("CASE_MNG_ODRNO"   , sCaseMngOdrno); 												 //사례관리차수

				List<Map<String, Object>> progrmPlanList = casePlanMapper.selectProgramList(progrmPlanCndMap);
				for(Map<String, Object> progrmPlanMap : progrmPlanList) {
					Map<String, String> casePlanProgrm = new HashMap<String, String>();
					casePlanProgrm.put("PROGRM_NO"		  , String.valueOf(progrmPlanMap.get("PROGRM_NO")));
					casePlanProgrm.put("SRVC_PVSN_PLAN_NO", sSrvcPvsnPlanNo);
					casePlanProgrm.put("CASE_MNG_NO"	  , sCaseMngNo);
					casePlanProgrm.put("CASE_MNG_ODRNO"	  , sNewCaseMngOdrno);
					casePlanProgrm.put("RESRCE_NO"	  	  , String.valueOf(progrmPlanMap.get("RESRCE_NO")));
					casePlanProgrm.put("DEL_YN"  		  , "N");
					casePlanProgrm.put("USER_ID" 	   	  , sUserId);
					casePlanProgrm.put("DATAA_CHG_SE_CD"  , "I");

					//6.1.자원프로그램제공계획대상자 저장 호출
					casePlanMapper.saveSEB320Data(casePlanProgrm);
					//6.2.자원프로그램제공계획대상자 이력 등록 호출
					casePlanMapper.insertSEB321Data(casePlanProgrm);
				}
			}
		}

		//7.사례기본 사례진행상태구분 수정
		Map<String, String> caseBassMap = new HashMap<String, String>();
		caseBassMap.put("CASE_MNG_NO"   	   , sCaseMngNo);
		caseBassMap.put("CASE_MNG_ODRNO"	   , sNewCaseMngOdrno);
		caseBassMap.put("CASE_PRGRS_STTS_SE_CD", "02");
		caseBassMap.put("MDFCN_DT" 	   	 	   , sSysdate);
		caseBassMap.put("USER_ID" 	   	 	   , sUserId);
		caseBassMap.put("DATAA_CHG_SE_CD"  	   , "U");

		//7.1.사례기본 사례진행상태구분 수정 호출
		caseTrmnMapper.updateCasePrgrsSttsSeCd(caseBassMap);
		//7.2.사례기본 이력 등록
		caseRegMapper.insertSEB101Data(caseBassMap);
		//7.3.사례관리이력 등록 호출
		caseRegMapper.insertSEB110Data(caseBassMap);

		LOGGER.debug("============ 재개입 후 신규 사례관리_계획 생성 END ============");
	}
}
