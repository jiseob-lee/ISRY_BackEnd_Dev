/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service.impl;

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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseAftfctMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseTrmnMapper;
import isry.itgcm.casemng.caseunity.service.CaseTrmnService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;


/**
* @Class Name  : CaseTrmnServicImpl.java
* @Description : 사례종결 ServicImpl Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 09.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 09.  Seo.Hae.Seok    최초작성
* </pre>
*/

@Service("caseTrmnService")
public class CaseTrmnServiceImpl extends IsryBaseServiceImpl implements CaseTrmnService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;

	@Resource(name="caseTrmnMapper")
    private CaseTrmnMapper caseTrmnMapper;
	
	@Resource(name="caseAftfctMapper")
    private CaseAftfctMapper caseAftfctMapper;	
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	String sUserId = "";	// 세션정보의 유저ID
	ScpDb  scpDb   = new ScpDb();
	Masking mask   = new Masking();
	
	/**
	* @Method    : 사례종결 목록조회
	* @param     : Map
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseTrmnList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return caseTrmnMapper.selectCaseTrmnList(paramMap);
	}
	
	/**
	* @Method    : 사례종결 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public Map<String, Object> processCaseTrmnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 사례종결상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCaseTrmn");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		String sWprkSqn = "";	// 채번번호
		
		String sCaseMngNo          = ""; // 사례관리번호
		String sCaseMngOdrno       = ""; // 사례관리차수
		String sTrmnSrngResultSeCd = ""; // 종결심사결과구분코드(01:신청, 02:심사(승인), 03:심사(반려), 04:재개입)

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		if (paramGroup != null) {
			Iterator<ParameterRow> insertedRows = paramGroup.getAllRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				
				// 사례관리번호 체크
				sCaseMngNo = mapIns.get("CASE_MNG_NO");
				if (sCaseMngNo == null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
					throw new AppWorksException("사례관리번호가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
				}
				
				// 사례관리차수 체크
				sCaseMngOdrno = mapIns.get("CASE_MNG_ODRNO");
				if (sCaseMngOdrno == null || sCaseMngOdrno.equals("null") || sCaseMngOdrno.equals("")) {
					throw new AppWorksException("사례관리차수가 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
				}
				
				// 종결심사결과구분코드(01:신청, 02:심사(승인), 03:심사(반려)) 체크
				sTrmnSrngResultSeCd = mapIns.get("TRMN_SRNG_RESULT_SE_CD");
//				if (sTrmnSrngResultSeCd == null || sTrmnSrngResultSeCd.equals("null") || sTrmnSrngResultSeCd.equals("")) {
////					throw new AppWorksException("종결심사결과구분코드는 필수입력 항목입니다. 입력해 주세요", Alert.ERROR);
//					throw new AppWorksException("종결구분이 존재하지 않습니다. 확인해 주세요.", Alert.ERROR);
//				}

				String sCode = caseTrmnMapper.selectTrmnSrngResultSeCd(mapIns); // 2022.09.06 최두일

				if("01".equals(sTrmnSrngResultSeCd)) {
					//조회쿼리에서 코드 리턴받아서
					if("01".equals(sCode) || "02".equals(sCode)) {
						throw new AppWorksException("종결신청을 할 수 없습니다. 현재 진행상태를 확인해 주세요!", Alert.ERROR);
					}
				} else if("02".equals(sTrmnSrngResultSeCd) || "03".equals(sTrmnSrngResultSeCd)) {
					if(!"01".equals(sCode))  {
						throw new AppWorksException("종결처리를 할 수 없습니다. 현재 진행상태를 확인해 주세요!", Alert.ERROR);
					}
				}

				if (sTrmnSrngResultSeCd == "01" || sTrmnSrngResultSeCd == "02") {
					
				}

				mapIns.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
				mapIns.put("DATAA_CHG_SE_CD", "I"); 	// 데이터변경구분코드 셋팅
				
				LOGGER.debug("mapIns : " + mapIns);

				String sCasePrgrsSttsSeCd = "";

				/* --------------------------------------------------------------
				 * 사례기본 사례진행상태구분코드 저장
				 * --------------------------------------------------------------
				 * 종결심사결과구분코드(01:신청)							→ (12:종결신청)
				 *               	(02:심사(승인)) && 사후관리여부 = 'N'	→ (04:종결-종결승인(사후관리안함))
				 *               	(02:심사(승인)) && 사후관리여부 = 'Y' 	→ (06:사후관리-종결승인(사후관리))
				 *               	(03:심사(반려))   					→ (03:서비스실행)
				 ---------------------------------------------------------------- */

				// 종결심사결과구분코드(01:신청)
				if (sTrmnSrngResultSeCd.equals("01")) {
					mapIns.put("CASE_PRGRS_STTS_SE_CD", "12"); // 사례진행상태구분코드(12:종결신청)
					sCasePrgrsSttsSeCd = "12";

					//사후관리여부 수정
					caseTrmnMapper.updateAftfctMngYn(mapIns);

				// 종결심사결과구분코드(02:심사(승인))
				} else if (sTrmnSrngResultSeCd.equals("02")) {
					String sAftfctMngYn = mapIns.get("AFTFCT_MNG_YN");
					if("".equals(sAftfctMngYn)) {
						List<Map<String, Object>> caseMngmap = caseRegMapper.selectCaseMngNocs(mapIns);
						if(caseMngmap.size() > 0) {
							sAftfctMngYn = String.valueOf(caseMngmap.get(0).get("AFTFCT_MNG_YN"));
						}
					}

					if("Y".equals(sAftfctMngYn)) {
						sCasePrgrsSttsSeCd = "06"; // 사례진행상태구분코드(06:사후관리-종결승인(사후관리))
					} else if("N".equals(sAftfctMngYn)) {
						sCasePrgrsSttsSeCd = "04"; // 사례진행상태구분코드(04:종결-종결승인(사후관리안함))
					} else {
						throw new AppWorksException("사후관리여부를 확인해 주세요!", Alert.ERROR);
					}
					
					/* 2023-05-23 병역의무자 상담지원의뢰 종결처리 현황 송신시 목표달성성과(서비스성과내용) 송신(화면필수입력컬럼으로변경)*/
					Map<String, Object> getMap = new HashMap<>();
					getMap = caseTrmnMapper.selectCaseTrmnMmaInfo(mapIns);
					if(getMap != null) {
						mapIns.put("GOAL_ACHIV_OUTC_CN", String.valueOf(getMap.get("GOAL_ACHIV_OUTC_CN")));
					}

					mapIns.put("CASE_PRGRS_STTS_SE_CD", sCasePrgrsSttsSeCd);

				// 종결심사결과구분코드(03:심사(반려))
				} else if (sTrmnSrngResultSeCd.equals("03")) {
					mapIns.put("CASE_PRGRS_STTS_SE_CD", "03"); // 사례진행상태구분코드(03:서비스실행)
					sCasePrgrsSttsSeCd = "03";
				}

				// 사례종결 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();
				seqMap.put("USER_ID"	  , sUserId);
				seqMap.put("RENU_NO_SE_CD", "CT");				  // 사례종결 채번코드
				seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자

				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 사례종결번호 발번
				mapIns.put("CASE_TRMN_NO", sWprkSqn);

				// 사례종결상세 등록 호출
				caseTrmnMapper.insertCaseTrmnDetail(mapIns);
				// 사례종결 이력등록 호출
				caseTrmnMapper.insertCaseTrmnHistory(mapIns);
				// 심사담당자 등록 호출
				caseTrmnMapper.insertCaseTrmnPic(mapIns);
				// 심사담당자이력 등록 호출
				caseTrmnMapper.insertCaseTrmnPicHistory(mapIns);

				// 종결심사결과구분코드(01:신청, 02:심사(승인), 03:심사(반려)) 일경우
				if (sTrmnSrngResultSeCd.equals("01") ||
		            sTrmnSrngResultSeCd.equals("02") ||
		            sTrmnSrngResultSeCd.equals("03")) {

					// 사례기본 사례진행상태구분/사례종결일자 수정 호출
					caseTrmnMapper.updateCasePrgrsSttsSeCd(mapIns);
					// 사례기본 이력 등록
					caseRegMapper.insertSEB101Data(mapIns);

					// 사례관리이력
					Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapIns);
					if(hstrMap != null) {
						String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
						if(!nowStts.equals(sCasePrgrsSttsSeCd)) {
							// 사례관리이력 등록 호출
							caseRegMapper.insertSEB110Data(mapIns);
						}
					}

					//대상자T 종결사례관리번호/종결사례관리차수 UPDATE
					if("02".equals(sTrmnSrngResultSeCd) && "04".equals(sCasePrgrsSttsSeCd)) {
						mapIns.put("TRMN_CASE_MNG_NO"   , sCaseMngNo);
						mapIns.put("TRMN_CASE_MNG_ODRNO", sCaseMngOdrno);

						//대상자정보 수정
						caseRegMapper.updateSEA200Data(mapIns);
						//대상자정보 이력 등록
						caseRegMapper.insertSEA201Data(mapIns);
					}
				}
			}
		}

		Map<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("CASE_TRMN_NO", sWprkSqn);

		return rtnMap;

	}
	
	/**
	 * @Method명   : selectCaseTrmnAprvList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 22. 
	 * @Method설명 : 종결승인 목록
	 */
	@Override
	public Map<String, Object> selectCaseTrmnAprvList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");	
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		
		Map<String,Object> retMap = new HashMap<>();		
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());		
		
		Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());		
		
		int trmnAprvnCnt = caseTrmnMapper.getCaseTrmnAprvList(paramMap2);
		paramMap2.put("TOT_CNT", trmnAprvnCnt);	
		
		int totCnt  = trmnAprvnCnt; /* 전체ROW*/
		int pageIdx = Integer.parseInt(reqPage.getValue("pageNo")); /* page번호*/
		int rowSize = Integer.parseInt(reqPage.getValue("pageRowCount")); /* pageRow수*/
		
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex  = startIndex + rowSize - 1;
		
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);		
		
		List<Map<String, Object>> rtn = caseTrmnMapper.selectCaseTrmnAprvList(paramMap2);
		
		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);
		
		/* 리턴 map 정보*/
		retMap.put("dsCaseTrmnList", rtn);
		retMap.put("dmPage", pageMap);		
		
		return retMap;
	}
	
	/**
	 * @Method명   : selectCaseTrmnAply
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 23. 
	 * @Method설명 : 종결신청 정보조회
	 */
	@Override
	public Map<String, Object> selectCaseTrmnAply(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}		
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		Map<String, Object> paramMap2 = new HashMap<>(paramMap);
		
		List<Map<String, Object>> retList = caseTrmnMapper.selectCaseTrmnAply(paramMap2);
		List<Map<String, Object>> ymdList = caseTrmnMapper.selectCaseTrmnYmd(paramMap2);
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("dsCaseTrmnList", retList);
		rtnMap.put("dsCaseTrmnYmd", ymdList);
		
		return rtnMap;
	}
	
	/**
	 * @Method명   : selectUpperInst
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 25. 
	 * @Method설명 : 종결수정 접속한종사자 상위기관조회
	 */
	@Override
	public List<Map<String, Object>> selectUpperInst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParamInstNo");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();	
		
		
		return caseTrmnMapper.selectUpperInst(paramMap);
	}	
	
	/**
	 * @Method명   : updateCaseTrmnAprv
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 26. 
	 * @Method설명 : 사례종결 수정 및 권한담당자 변경
	 */
	@Override
	public Map<String, Object> updateCaseTrmnAprv(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCaseTrmnList");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		LOGGER.debug("========== 사례종결수정.paramGroup=[" + paramGroup +"]");		
		
		Iterator<ParameterRow> insertedRows = paramGroup.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = paramGroup.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = paramGroup.getDeletedRows();	
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, Object> rtnMap = new HashMap<>();		
		
		rtnMap.put("CASE_TRMN_NO"   , dmSearch.getValue("CASE_TRMN_NO"));		
		rtnMap.put("CASE_MNG_NO"    , dmSearch.getValue("CASE_MNG_NO"));		
		rtnMap.put("CASE_MNG_ODRNO" , dmSearch.getValue("CASE_MNG_ODRNO"));	
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}		
		
		
		
		
		
		String sCasePrgrsSttsSeCd = "";	/* 사례진행상태구분*/
		
		while (updatedRows.hasNext()) {
			String sts = "U";		
		
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			mapUpd.put("USER_ID"		, sUserId); // 세션 사용자ID 셋팅				
			mapUpd.put("DATAA_CHG_SE_CD", sts); 	// 데이터변경구분코드 셋팅		
			
			// 사례종결상세 등록 호출
			caseTrmnMapper.updateCaseTrmnDetail(mapUpd);
//			// 사례종결 이력등록 호출
			caseTrmnMapper.insertCaseTrmnHistory(mapUpd);
//			/* 사후관리여부*/
//			caseTrmnMapper.updateAftfctMngYn(mapUpd);
//			// 심사담당자 등록 호출
//			caseTrmnMapper.insertCaseTrmnPic(mapUpd);
//			// 심사담당자이력 등록 호출
//			caseTrmnMapper.insertCaseTrmnPicHistory(mapUpd);	
			
			
			/* 사례진행상태 변경*/
			String sAftfctMngYn = mapUpd.get("AFTFCT_MNG_YN");
			if("Y".equals(sAftfctMngYn)) {
				sCasePrgrsSttsSeCd = "06"; // 사례진행상태구분코드(06:사후관리-종결승인(사후관리))
			} else if("N".equals(sAftfctMngYn)) {
				sCasePrgrsSttsSeCd = "04"; // 사례진행상태구분코드(04:종결-종결승인(사후관리안함))
			} else {
				throw new AppWorksException("사후관리여부를 확인해 주세요", Alert.ERROR);
			}
			
			/* 사후관리여부 'N' 사후관리여부 예->아니오 변경 불가*/
			if("N".equals(sAftfctMngYn)) {
				List<Map<String, Object>> caseAftfcList = caseAftfctMapper.selectCaseAftfctMngList(mapUpd);
				
				Map<String, Object> paramMap = new HashMap<>();
				mapUpd.forEach((strKey, strVal)->{
					paramMap.put(strKey, strVal);
				});
				
				List<Map<String, Object>> retList = caseTrmnMapper.selectCaseTrmnAply(paramMap);
				String sCasePrgrs = retList.get(0).get("CASE_PRGRS_STTS_SE_CD").toString();
				
				/* 1.사후관리등록시 */
				if("06".equals(sCasePrgrs)) {
					int listSize = caseAftfcList.size();

					if(listSize > 0) {
						sCasePrgrsSttsSeCd = "06"; // 사례진행상태구분코드(06:사후관리)
						throw new AppWorksException("사후관리가" + listSize + "건\n등록되어 있어 사후관리여부를 아니오로 수정 불가합니다.", Alert.ERROR);
					}
				}else if("08".equals(sCasePrgrs)) {
					sCasePrgrsSttsSeCd = "08"; // 사례진행상태구분코드(08:사후종결)
					throw new AppWorksException("사후종결이된 사례로 사후관리여부를 아니오로 수정 불가합니다.", Alert.ERROR);
				}else if("10".equals(sCasePrgrs)) {
					sCasePrgrsSttsSeCd = "10"; // 사례진행상태구분코드(10:재개입)
					throw new AppWorksException("재개입진행중인 사례로 사후관리여부를 아니오로 수정 불가합니다.", Alert.ERROR);
					
				}
			}
			
			mapUpd.put("CASE_PRGRS_STTS_SE_CD", sCasePrgrsSttsSeCd);
			
//			/* 사후관리여부*/
			caseTrmnMapper.updateAftfctMngYn(mapUpd);			
			// 사례기본 사례진행상태구분/사례종결일자 수정 호출
			caseTrmnMapper.updateCasePrgrsSttsSeCd(mapUpd);
			// 사례기본 이력 등록
			caseRegMapper.insertSEB101Data(mapUpd);

			// 사례관리이력
			Map<String, Object> hstrMap = caseRegMapper.selectCaseMngLastHstr(mapUpd);
			if(hstrMap != null) {
				String nowStts = hstrMap.get("CASE_PRGRS_STTS_SE_CD").toString();
				if(!nowStts.equals(sCasePrgrsSttsSeCd)) {
					// 사례관리이력 등록 호출
					caseRegMapper.insertSEB110Data(mapUpd);
				}
			}			
		}
		
		ParameterGroup parameterGroup2 = dataRequest.getParameterGroup("dsPic");
		
		if (parameterGroup2 != null) {
			Iterator<ParameterRow> insertedRows2 = parameterGroup2.getInsertedRows();
			Iterator<ParameterRow> updatedRows2  = parameterGroup2.getUpdatedRows();
			Iterator<ParameterRow> deletedRows2  = parameterGroup2.getDeletedRows();
			
			LOGGER.debug("========== 사례종결수정.parameterGroup2=[" + parameterGroup2 +"]");	
			
			//등록 이벤트
			while (insertedRows2.hasNext()) {
				
				Map<String, String> mapIns = insertedRows2.next().toMap();

				mapIns.put("DATAA_CHG_SE_CD", "I");
				mapIns.put("USER_ID"		, sUserId);
				
				//사례담당자 등록
				caseRegMapper.saveSEB150Data(mapIns);
				//사례담당자이력 저장
				caseRegMapper.insertSEB151Data(mapIns);				
			}

			//수정 이벤트
			while (updatedRows2.hasNext()) {

				Map<String, String> mapUpd = updatedRows2.next().toMap();
				mapUpd.put("USER_ID"		, sUserId);
				mapUpd.put("DATAA_CHG_SE_CD", "U");

				//사례담당자 수정
				caseRegMapper.updateSEB150Data(mapUpd);
				//사례담당자이력 저장
				caseRegMapper.insertSEB151Data(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows2.hasNext()) {

				Map<String, String> mapDel = deletedRows2.next().toMap();
				// 삭제여부 셋팅
				mapDel.put("DEL_YN"			, "Y");
				mapDel.put("USER_ID"		, sUserId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				//사례담당자이력 저장
				caseRegMapper.insertSEB151Data(mapDel);
				//사례담당자 삭제
				caseRegMapper.deleteSEB150Data(mapDel);
			}			
		}	
		
		LOGGER.debug("========== 사례종결수정.rtnMap=[" + rtnMap +"]");		
		
		// 사례종결일시 수정
		ParameterGroup parameterGroup3 = dataRequest.getParameterGroup("dsCaseTrmnYmd");
		Iterator<ParameterRow> allRow = parameterGroup3.getAllRows();
		while (allRow.hasNext()) {
			Map<String, String> mapAll = allRow.next().toMap();
			mapAll.put("CASE_TRMN_NO"   , dmSearch.getValue("CASE_TRMN_NO"));
			caseTrmnMapper.updateCaseTrmnYmd(mapAll);
		}
		
		return rtnMap;
	}

	
}
