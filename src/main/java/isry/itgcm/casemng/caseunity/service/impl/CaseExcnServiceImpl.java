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

import isry.cysns.casemng.casereg.service.CysnsRegService;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseExcnMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseTrmnMapper;
import isry.itgcm.casemng.caseunity.service.CaseExcnService;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.pubms.casemng.sheltrreg.service.SheltrRegService;


/**
 * @파일명        : CaseExcnServiceImpl.java
 * @프로그램 설명 	: 사례제공 ServicImpl Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 13. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 13.
 * @수정내용      : 
 * -
 * -
 */
@Service("caseExcnService")
public class CaseExcnServiceImpl implements CaseExcnService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="cysnsRegService")
	private CysnsRegService cysnsRegService;
	
	@Resource(name="sheltrRegService")
	private SheltrRegService sheltrRegService;
	
	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;
	
	@Resource(name="caseExcnMapper")
    private CaseExcnMapper caseExcnMapper;
	
	@Resource(name="caseTrmnMapper")
	private CaseTrmnMapper caseTrmnMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name="caseRegService")
	private CaseRegService caseRegService;
	
	String sUserId = "";

	/**
	* @Method    : 사례제공 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseExcnList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
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
		
		return caseExcnMapper.selectCaseExcnList(paramMap);
	}

	/**
	* @Method    : 사례제공 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectExcnProgramList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return caseExcnMapper.selectExcnProgramList(paramMap);
	}
	
	/**
	* @Method    : 사례제공 프로그램 목록조회2
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectExcnProgramList2(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return caseExcnMapper.selectExcnProgramList2(paramMap);
	}

	/**
	* @Method    : 사례제공이력 목록조회
	* @param     : Map  : SRVC_PVSN_BGNG_YMD(서비스제공시작일자), SRVC_PVSN_END_YMD(서비스제공종료일자), RESRCE_NO(자원명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseExcnHstrList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		return caseExcnMapper.selectCaseExcnHstrList(paramMap);
	}

	@Override
	public Map<String, Object> processCaseExcnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> rtnMap = new HashMap<>();
		// 사례제공상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCaseExcnList");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn = null;	// 채번번호
		String sPvsnNo  = null; // 서비스제공번호
		String sEnfsnNo = null; //
		String sInstNo  = null;

		String sUntTaskwk  = ""; //단위업무구분코드
		String sPvsnRqstNo = ""; // 서비스제공의뢰번호
		String trprInfoNo  = ""; // 대상자번호

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId    = loginVO.getId();
			sEnfsnNo   = loginVO.getEnfsnNo();
			sInstNo    = String.valueOf(loginVO.getInstNo());	//2022.06.24시점 Null로 넘어옴(로그인시 조회안됨)
			sUntTaskwk = loginVO.getUntTaskwk();
		}

		List<Map<String, String>> progrmMapngList = new ArrayList<Map<String,String>>();	

//		     Map<String, String>  sendMap  = new HashMap<String, String>();
		List<Map<String, String>> sendList = new ArrayList<Map<String,String>>();
		
		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();
			
			// 2023-04-12 실행 저장 시 SRVC_PVSN_RQST_NO(서비스제공의뢰번호) 조회 후 저장
			Iterator<ParameterRow> allRows      = paramGroup.getAllRows();
			while (allRows.hasNext()) {
				Map<String, String> mapAll = allRows.next().toMap();
				caseRegService.selectPrgrsStts(mapAll);
				trprInfoNo = mapAll.get("TRPR_INFO_NO");
				if(!"".equals(trprInfoNo)) {
					break;
				}
			}
			
			sPvsnRqstNo = caseExcnMapper.selectRqstNo(trprInfoNo);
			
			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();

				sPvsnNo = "";
				sPvsnNo = mapIns.get("SRVC_PVSN_NO");
				
				mapIns.put("USER_ID"			  , sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD"	  , "I"); 	  // 데이터변경구분코드 셋팅
				mapIns.put("SRVC_PVSN_YN"   	  , "Y");
//				mapIns.put("CASE_PRGRS_STTS_SE_CD", "03");
				mapIns.put("SRVC_PVSN_RQST_NO", sPvsnRqstNo);

				//서비스제공정보 저장
				if(sPvsnNo.isEmpty()) {
					// 서비스제공번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID"	  , sUserId);			  // 세션 사용자ID 셋팅
					seqMap.put("RENU_NO_SE_CD", "SR");				  // 서비스제공번호 채번코드
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 서비스제공번호 발번

					mapIns.put("SRVC_PVSN_NO", sWprkSqn);
					mapIns.put("PIC_NO"		 , sEnfsnNo);
					mapIns.put("TKCG_INST_NO", sInstNo);

					//서비스제공번호 설정
					sPvsnNo = sWprkSqn;
				}
				request.setAttribute("SRVC_PVSN_NO", sPvsnNo);				

				// 서비스제공 저장 호출
				caseExcnMapper.saveSEB500Data(mapIns);
				// 서비스제공 이력등록 호출
				caseExcnMapper.insertSEB501Data(mapIns);

				// 서비스제공대상자 저장 호출
				caseExcnMapper.saveSEB510Data(mapIns);
				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapIns);

				List<Map<String, Object>> caseBass = caseRegMapper.selectCaseBassDetail(mapIns);
				if(caseBass.size() > 0) {
					String sCasePrgrsSttsSeCd = String.valueOf(caseBass.get(0).get("CASE_PRGRS_STTS_SE_CD"));
					if(!"12".equals(sCasePrgrsSttsSeCd) && !"03".equals(sCasePrgrsSttsSeCd)) { //사례진행상태구분코드(12:종결신청, 03:서비스실행)
						mapIns.put("CASE_PRGRS_STTS_SE_CD", "03");

						// 사례기본 사례진행상태구분 수정 호출
						caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
						// 사례기본 이력 등록
						caseRegMapper.insertSEB101Data(mapIns);

						// 사례관리이력 등록 호출
						caseRegMapper.insertSEB110Data(mapIns);
					}
				}

				if("04".equals(mapIns.get("SRVC_TYPE_SE_CD"))) {
					Map<String, String> progrmMapnMap = new HashMap<String, String>();

					progrmMapnMap.put("SRVC_PVSN_NO"	   , sPvsnNo);
					progrmMapnMap.put("RESRCE_NO"   	   , mapIns.get("RESRCE_NO"));
					progrmMapnMap.put("CASE_EXCN_ROW_INDEX", mapIns.get("CASE_EXCN_ROW_INDEX"));

					progrmMapngList.add(progrmMapnMap);
				}

//				sendMap.clear();
			     Map<String, String>  sendMap  = new HashMap<String, String>();
				sendMap.put("SRVC_PVSN_NO"	 , sPvsnNo);				 //서비스제공번호
				sendMap.put("RESRCE_NO"   	 , mapIns.get("RESRCE_NO")); //자원번호
				sendMap.put("DATAA_CHG_SE_CD", "I");					 //데이터변경(이력)구분코드(I:신규 U:변경 D:삭제)

				sendList.add(sendMap);
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();

				sPvsnNo = "";
				sPvsnNo = mapUpd.get("SRVC_PVSN_NO");

				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅

				// 서비스제공 저장 호출
				caseExcnMapper.saveSEB500Data(mapUpd);
				// 서비스제공 이력등록 호출
				caseExcnMapper.insertSEB501Data(mapUpd);

				// 서비스제공대상자 저장 호출
				caseExcnMapper.saveSEB510Data(mapUpd);
				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapUpd);

				request.setAttribute("SRVC_PVSN_NO", sPvsnNo);

				if("04".equals(mapUpd.get("SRVC_TYPE_SE_CD"))) {
					Map<String, String> progrmMapnMap = new HashMap<String, String>();
					
					progrmMapnMap.put("SRVC_PVSN_NO", sPvsnNo);
					progrmMapnMap.put("RESRCE_NO"   , mapUpd.get("RESRCE_NO"));

					progrmMapngList.add(progrmMapnMap);
				}
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();

				sPvsnNo = "";
				sPvsnNo = mapDel.get("SRVC_PVSN_NO");

				// 삭제여부 셋팅
				mapDel.put("DEL_YN"			, "Y");
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				// 서비스제공 이력등록 호출
				caseExcnMapper.insertSEB501Data(mapDel);
				// 서비스제공 삭제 호출
//				caseExcnMapper.deleteSEB500Data(mapDel);
				caseExcnMapper.updateSEB500DelYN(mapDel);

				// 서비스제공대상자 이력등록 호출
				caseExcnMapper.insertSEB511Data(mapDel);
				// 서비스제공대상자 삭제 호출
				caseExcnMapper.deleteSEB510Data(mapDel);

				if("04".equals(mapDel.get("SRVC_TYPE_SE_CD"))) {
					Map<String, String> progrmMapnMap = new HashMap<String, String>();

					progrmMapnMap.put("SRVC_PVSN_PLAN_NO", sPvsnNo);
					progrmMapnMap.put("RESRCE_NO"        , mapDel.get("RESRCE_NO"));

					progrmMapngList.add(progrmMapnMap);
				}

//				sendMap.clear();
			     Map<String, String>  sendMap  = new HashMap<String, String>();
				sendMap.put("SRVC_PVSN_NO"	 , sPvsnNo);				 //서비스제공번호
				sendMap.put("RESRCE_NO"   	 , mapDel.get("RESRCE_NO")); //자원번호
				sendMap.put("DATAA_CHG_SE_CD", "D");					 //데이터변경(이력)구분코드(I:신규 U:변경 D:삭제)

				sendList.add(sendMap);
			}
		}

		//자원프로그램제공대상자 저장
		saveResrceProgrm(dataRequest, progrmMapngList);
		
		/* 2022.11.21 
		 *  - U02(청소년상담복지센터), U04(청소년쉼터)의 경우 업무공통 서비스제공 처리 후 업무영역 Service호출
		 */
		if("U02".equals(sUntTaskwk)) {
			cysnsRegService.saveExcnData(request, dataRequest, sendList);
		} else if("U04".equals(sUntTaskwk)) {
			sheltrRegService.saveExcnData(request, dataRequest, sendList);
		}

		/*
		 * 2023-05-10 실행서비스 세부사업 저장
		 */
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsExcnSrvcBizClList");
		if (paramGroup2 != null) {
			Iterator<ParameterRow> insertedRows = paramGroup2.getInsertedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup2.getDeletedRows();
			//등록 이벤트
			while (insertedRows.hasNext()) {
				Map<String, String> mapIns = insertedRows.next().toMap();
				/*
				 * 2023.07.17 윤희성
				 * 간헐적 오류로 dsExcnSrvcBizClList 데이터 row가 비어서 넘어온다.
				 * 비어있을 수 없는 데이터로 일단 분기처리 한다.
				 */
				String excnSrvcBizClNo = mapIns.get("EXCN_SRVC_BIZ_CL_NO");
				String excnSrvcBizDetaiaNo = mapIns.get("EXCN_SRVC_DETAIA_BIZ_NO");
				if(!"".equals(excnSrvcBizClNo) && null != excnSrvcBizClNo && !"".equals(excnSrvcBizDetaiaNo) && null != excnSrvcBizDetaiaNo) {
					mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅
					mapIns.put("GR_TRGT_SRVC_YN", "N");		// 집단서비스여부
					caseExcnMapper.insertExcnDetaiaBiz(mapIns);
				}
			}
			
			//삭제 이벤트
			while (deletedRows.hasNext()) {
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("USER_ID"		, sUserId);
				caseExcnMapper.deleteExcnDetaiaBiz(mapDel);
			}
		}			
		
		rtnMap.put("SRVC_PVSN_NO", sPvsnNo);
		return rtnMap;

	}

	//자원프로그램제공대상자 저장
	private void saveResrceProgrm(DataRequest dataRequest, List<Map<String, String>> list) throws Exception {

		//자원프로그램제공대상자 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsProgramList");
		String sPvsnNo  = null; // 서비스제공번호

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅

				sPvsnNo = mapIns.get("SRVC_PVSN_NO");
				if(sPvsnNo.isEmpty()) {
					for(Map<String, String> map : list) {
						if(mapIns.get("RESRCE_NO")          .equals(map.get("RESRCE_NO")) &&  
						   mapIns.get("CASE_EXCN_ROW_INDEX").equals(map.get("CASE_EXCN_ROW_INDEX"))) {
							sPvsnNo = map.get("SRVC_PVSN_NO");
							break;
						}
					}

					mapIns.put("SRVC_PVSN_NO", sPvsnNo);
				}

				// 자원프로그램제공대상자 저장 호출
				caseExcnMapper.saveSEB520Data(mapIns);
				// 자원프로그램제공대상자 이력등록 호출
				caseExcnMapper.insertSEB521Data(mapIns);
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();				
				mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapUpd.put("DATAA_CHG_SE_CD", "U"); 	// 데이터변경구분코드 셋팅

				// 자원프로그램제공대상자 저장 호출
				caseExcnMapper.saveSEB520Data(mapUpd);
				// 자원프로그램제공대상자 이력등록 호출
				caseExcnMapper.insertSEB521Data(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();				
				mapDel.put("DEL_YN"			, "Y");     // 삭제여부 셋팅
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");
				
				// 자원프로그램제공대상자 이력등록 호출
				caseExcnMapper.insertSEB521Data(mapDel);
				// 자원프로그램제공대상자 삭제 호출
				caseExcnMapper.deleteSEB520Data(mapDel);
			}
		}
	}
	
	/**
	* @Method    : 실행서비스 사업분류 목록 조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseExcnSrvcBizClList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return caseExcnMapper.selectCaseExcnSrvcBizClList(paramMap);
	}
}
