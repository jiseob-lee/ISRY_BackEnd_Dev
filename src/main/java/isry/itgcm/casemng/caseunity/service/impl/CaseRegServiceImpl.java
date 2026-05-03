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

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcm.bizcmmns.cmmns.mapper.ComCodeMapper;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.bizcmmns.cmmns.mapper.TrprInfoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcm.casemng.uneart.mapper.TrprInqMapper;
import isry.itgcms.syscmmn.rest.service.RestService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;

/**
 * @파일명      	: CaseRegServiceImpl.java
 * @프로그램 설명	: 사례관리 대상자에 대한 내역을 관리한다
 * @작성자      	: Lee.Jun.Yeong
 * @작성일      	: 2022. 4. 29. 
 * @수정자      	: Lee.Jun.Yeong
 * @수정일      	: 2022. 4. 29.
 * @수정내용    	: 
 * -                
 * -                
 */
@Service("caseRegService")
public class CaseRegServiceImpl implements CaseRegService {

	private final Logger LOGGER = LoggerFactory.getLogger(CaseRegServiceImpl.class);
	
//	private final String REQUEST_URL = "http://10.188.131.225:25000/WS/";
	private final String REQUEST_URL = "http://10.188.131.156:25000/WS/"; //연계 L4 IP

	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;

	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="trprInqMapper")
    private TrprInqMapper trprInqMapper;
	
	@Resource(name="trprInfoMapper")
    private TrprInfoMapper trprInfoMapper;

	@Resource(name="comCodeMapper")
    private ComCodeMapper comCodeMapper;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	@Resource(name="restService")
	private RestService restService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	String userId = "";	

	@Override
	public List<Map<String, Object>> selectCaseMngNocs(DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		Map<String, String> map = new HashMap<>();
		map.put("CASE_MNG_NO"   , caseMngNo);
		map.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례관리 건수 조회
		return caseRegMapper.selectCaseMngNocs(map);
	}
	
	@Override
	public List<Map<String, Object>> selectUrlValue(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmUrlParam");
		String sCmmns  = "";
		String sTaskwk = "";
		String sStep   = "";
		sCmmns  = parameterGroup.getValue("CMMNS_CD");
		sTaskwk = parameterGroup.getValue("TASKWK_SE_CD");
		sStep   = parameterGroup.getValue("STEP");

		Map<String, String> map = new HashMap<>();
		map.put("CMMNS_CD"    , sCmmns);
		map.put("TASKWK_SE_CD", sTaskwk);

		if(sStep != null) map.put("STEP", sStep);

		//단위업무별 페이지경로 조회
		return caseRegMapper.selectUrlValue(map);
	}

	@Override
	public List<Map<String, Object>> selectMainList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		String trprNm     = null;
		String picNm      = null;
		String taskwkSeCd = null;
		String usrId	  = null;

		if (parameterGroup != null) {
			trprNm     = parameterGroup.getValue("TRPR_NM_ENCPT");			 //대상자성명	
			picNm      = parameterGroup.getValue("PIC_NM_ENCPT");			 //담당자성명	
			taskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD").replaceAll(",", ""); //단위업무구분코드
			usrId	   = parameterGroup.getValue("USER_ID");				 //접수담당자
			
		}

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		if (taskwkSeCd != null) paramMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		if (usrId      != null) paramMap.put("USER_ID"         , usrId);

        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();

		paramMap2.put("SEARCH_GUBN",paramMap.get("SEARCH_GUBN"));
		paramMap2.put("END_DATE",paramMap.get("END_DATE"));
		paramMap2.put("START_DATE",paramMap.get("START_DATE"));
		paramMap2.put("CASE_PRGRS_STTS_TYPE_SE_CD",paramMap.get("CASE_PRGRS_STTS_TYPE_SE_CD"));
		paramMap2.put("CASE_PRGRS_STTS_SE_CD",paramMap.get("CASE_PRGRS_STTS_SE_CD"));
		paramMap2.put("UNT_TASKWK_SE_CD",paramMap.get("UNT_TASKWK_SE_CD"));
		paramMap2.put("YNGBGS_STTS_LCLAS_SE_CD",paramMap.get("YNGBGS_STTS_LCLAS_SE_CD"));
		paramMap2.put("YNGBGS_STTS_SCLAS_SE_CD",paramMap.get("YNGBGS_STTS_SCLAS_SE_CD"));
		paramMap2.put("CASE_TRPR_TYPE_SE_CD",paramMap.get("CASE_TRPR_TYPE_SE_CD"));
		paramMap2.put("ENFSN_ROLE_SE_CD",paramMap.get("ENFSN_ROLE_SE_CD"));
		paramMap2.put("USER_ID",paramMap.get("USER_ID"));
		paramMap2.put("SRVC_EXCN_BIZ_NO",paramMap.get("SRVC_EXCN_BIZ_NO"));
		paramMap2.put("APRV_YN",paramMap.get("APRV_YN"));
		paramMap2.put("PIC_NM_ENCPT",paramMap.get("PIC_NM_ENCPT"));
		paramMap2.put("TRPR_NM_ENCPT",paramMap.get("TRPR_NM_ENCPT"));
		paramMap2.put("INST_NM",paramMap.get("INST_NM"));
		paramMap2.put("INST_NO",paramMap.get("INST_NO"));
		paramMap2.put("JBPS_SE_CD",paramMap.get("JBPS_SE_CD"));
		paramMap2.put("ORGNL_CASE_MNG_NO",paramMap.get("ORGNL_CASE_MNG_NO"));
		paramMap2.put("CASE_MNG_NO",paramMap.get("CASE_MNG_NO"));
		paramMap2.put("CASE_MNG_ODRNO",paramMap.get("CASE_MNG_ODRNO"));
		
		// paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		String cnt = caseRegMapper.caseinqListCount(paramMap2);
		paramMap2.put("TOT_CNT", cnt);
		
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		
		if(totCnt > 0) {
			rtnMap = caseRegMapper.selectCaseinqList(paramMap2);
		}

		return rtnMap;
	}
	
	/**
	 * @Method명   : selectCaseinqPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2023. 6. 6. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectCaseinqPagingList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		String taskwkSeCd = null;
		String usrId	  = null;

		if (parameterGroup != null) {
			taskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD").replaceAll(",", ""); //단위업무구분코드
			usrId	   = parameterGroup.getValue("USER_ID");				 //접수담당자
		}

		Map<String, String> paramMap = parameterGroup == null ? new HashMap<>() : parameterGroup.getSingleValueMap();
		if (taskwkSeCd != null) paramMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		if (usrId      != null) paramMap.put("USER_ID"         , usrId);

        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);


		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		
		paramMap2.put("SEARCH_GUBN",paramMap.get("SEARCH_GUBN"));
		paramMap2.put("END_DATE",paramMap.get("END_DATE"));
		paramMap2.put("START_DATE",paramMap.get("START_DATE"));
		paramMap2.put("CASE_PRGRS_STTS_TYPE_SE_CD",paramMap.get("CASE_PRGRS_STTS_TYPE_SE_CD"));
		paramMap2.put("CASE_PRGRS_STTS_SE_CD",paramMap.get("CASE_PRGRS_STTS_SE_CD"));
		paramMap2.put("UNT_TASKWK_SE_CD",paramMap.get("UNT_TASKWK_SE_CD"));
		paramMap2.put("YNGBGS_STTS_LCLAS_SE_CD",paramMap.get("YNGBGS_STTS_LCLAS_SE_CD"));
		paramMap2.put("YNGBGS_STTS_SCLAS_SE_CD",paramMap.get("YNGBGS_STTS_SCLAS_SE_CD"));
		paramMap2.put("CASE_TRPR_TYPE_SE_CD",paramMap.get("CASE_TRPR_TYPE_SE_CD"));
		paramMap2.put("ENFSN_ROLE_SE_CD",paramMap.get("ENFSN_ROLE_SE_CD"));
		paramMap2.put("USER_ID",paramMap.get("USER_ID"));
		paramMap2.put("SRVC_EXCN_BIZ_NO",paramMap.get("SRVC_EXCN_BIZ_NO"));
		paramMap2.put("APRV_YN",paramMap.get("APRV_YN"));
		paramMap2.put("PIC_NM_ENCPT",paramMap.get("PIC_NM_ENCPT"));
		paramMap2.put("TRPR_NM_ENCPT",paramMap.get("TRPR_NM_ENCPT"));
		paramMap2.put("INST_NM",paramMap.get("INST_NM"));
		paramMap2.put("INST_NO",paramMap.get("INST_NO"));
		paramMap2.put("JBPS_SE_CD",paramMap.get("JBPS_SE_CD"));
		paramMap2.put("ORGNL_CASE_MNG_NO",paramMap.get("ORGNL_CASE_MNG_NO"));
		paramMap2.put("CASE_MNG_NO",paramMap.get("CASE_MNG_NO"));
		paramMap2.put("CASE_MNG_ODRNO",paramMap.get("CASE_MNG_ODRNO"));
		
		// 현재 방식
		// paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		String cnt = caseRegMapper.caseinqListCount(paramMap2);
		paramMap2.put("TOT_CNT", cnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(totCnt > 0) {
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
	        rtnMap = caseRegMapper.selectCaseinqPagingList(paramMap2);
		}
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		result.put("dsCaseInqList", rtnMap);
		result.put("dmPage", resPage);
		return result;
	}

	@Override
	public List<Map<String, Object>> selectCaseBassDetail(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
				
		//사례등록 기본정보 조회
		rtn = caseRegMapper.selectCaseBassDetail(paramMap);
		
		return rtn;
	}
	
	@Override
	public List<Map<String, Object>> selectCaseYngbgsList(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		Map<String, String> map = new HashMap<>();
		map.put("CASE_MNG_NO"   , caseMngNo);
		map.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 문제상태및원인 조회
		return caseRegMapper.selectCaseYngbgsList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectFamInfoList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		
		if (parameterGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		
		paramMap.put("CASE_MNG_NO"   , caseMngNo);
		paramMap.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 가족정보 조회
		rtn = caseRegMapper.selectFamInfoList(paramMap);
		
		return rtn;
	}
	
	@Override
	public List<Map<String, Object>> selectAcbgSttsList(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		Map<String, String> map = new HashMap<>();
		map.put("CASE_MNG_NO"   , caseMngNo);
		map.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 학력상태 조회
		return caseRegMapper.selectAcbgSttsList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectSchulwDscntcList(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("CASE_MNG_NO"   , caseMngNo);
		map.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 학업중단 조회
		return caseRegMapper.selectSchulwDscntcList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectEmpymnInfoList(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("CASE_MNG_NO"   , caseMngNo);
		map.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 취업정보 조회
		return caseRegMapper.selectEmpymnInfoList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectCasePicList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = null;
		String caseMngOdrno = null;
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("CASE_MNG_NO"   , caseMngNo);
		paramMap.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 담당자 조회
		rtn = caseRegMapper.selectCasePicList(paramMap);

		return rtn;
		
	}
	
	@Override
	public List<Map<String, Object>> selectBizRegList(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String caseMngNo    = "";
		String caseMngOdrno = "";
		
		if (parameterGroup != null) {
			caseMngNo    = parameterGroup.getValue("CASE_MNG_NO");
			caseMngOdrno = parameterGroup.getValue("CASE_MNG_ODRNO");
		}
		Map<String, String> map = new HashMap<>();
		map.put("CASE_MNG_NO"   , caseMngNo);
		map.put("CASE_MNG_ODRNO", caseMngOdrno);

		//사례등록 서비스실행사업 조회
		return caseRegMapper.selectBizRegList(map);
	}

	@Override
	public Map<String, Object> processData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> rtnMap = new HashMap<>();

		String sCaseMngNo    = "";
		String sCaseMngOdrno = "";

//		String sDdlnYm		  = ""; //마감년월
		String sUntTaskwkSeCd = ""; //단위업무구분코드

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId 		   = loginVO.getId();
			sUntTaskwkSeCd = loginVO.getUntTaskwk();
		}

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsDetailInfo");

		String sPrcsSe = ""; //SEB100(사례기본) 처리구분(I:INSERT, U:UPDATE)

		if (parameterGroup != null) {
			Iterator<ParameterRow> allRows = parameterGroup.getAllRows();
			while (allRows.hasNext()) {
				Map<String, String> mapAll = allRows.next().toMap();
				selectPrgrsStts(mapAll);
			}
			
			Iterator<ParameterRow> insertedRows = parameterGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = parameterGroup.getUpdatedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("DATAA_CHG_SE_CD", "I");
				mapIns.put("USER_ID"		, userId);

				//사례관리번호/사례관리차수 설정
				sCaseMngNo    = mapIns.get("CASE_MNG_NO");
				sCaseMngOdrno = mapIns.get("CASE_MNG_ODRNO");

				/* 2022.10.05 추가
				 *  - 사례등록 가능한 대상자인지 체크
				 */
				String sCaseMngSeCd = "";

				Map<String, Object> trprInqMap = trprInqMapper.selectTrprInqDetail(mapIns);
				if(trprInqMap != null) {
					if(mapIns.get("UNT_TASKWK_SE_CD").equals(trprInqMap.get("UNT_TASKWK_SE_CD"))) {
						if(trprInqMap.get("CASE_MNG_SE_CD") != null) {
							sCaseMngSeCd = trprInqMap.get("CASE_MNG_SE_CD").toString();
						}
						if(!"02".equals(sCaseMngSeCd) && !"04".equals(sCaseMngSeCd)) {
							throw new AppWorksException("사례등록이 불가한 대상자입니다. 대상자의 사례관리구분을 확인해 주세요.");
						}

						if("04".equals(sCaseMngSeCd)) {
							String sCasePrgrsSttsSeCd = "";

							//대상자로 접수된 사례기본의 사례진행상태구분 확인 
							//2023.06.15 trprInfoMapper.selectTrprDetail(mapIns)가 2row 이상 나올 수 있음. 수정
							List<Map<String, Object>> trprInfoList = trprInfoMapper.selectTrprDetail(mapIns);
							boolean trprCase = true;
							for(Map<String, Object> trprInfoMap : trprInfoList) {
								if(trprInfoMap != null) {
									if(trprInfoMap.get("CASE_PRGRS_STTS_SE_CD") != null) {
										sCasePrgrsSttsSeCd = trprInfoMap.get("CASE_PRGRS_STTS_SE_CD").toString();
									}
									if(!"04".equals(sCasePrgrsSttsSeCd) && !"08".equals(sCasePrgrsSttsSeCd)) {
										//throw new AppWorksException("사례등록이 불가한 대상자입니다. 사례진행상태를 확인해 주세요.");
										trprCase = false;
									}

								} else {
									//throw new AppWorksException("사례등록이 불가한 대상자입니다. 사례진행상태를 확인해 주세요.");
									trprCase = false;
								}
							}
							if(!trprCase) {
								throw new AppWorksException("사례등록이 불가한 대상자입니다. 사례진행상태를 확인해 주세요.");
							}
						}

					} else {
						throw new AppWorksException("사례등록이 불가한 대상자입니다. 대상자의 단위업무구분을 확인해 주세요.");
					}
				} else {
					throw new AppWorksException("등록되어 있는 대상자가 아닙니다.");
				}

				//기본정보 저장
				if(sCaseMngNo.isEmpty()) {

					//사례관리번호 채번
					Map<String, String> seqMap = new HashMap<>();					
					seqMap.put("RENU_NO_SE_CD", "CS");
					seqMap.put("RENU_YMD"	  , DateUtil.getToday());	// 현재일자
					seqMap.put("USER_ID"	  , userId);

					Map<String, Object> valMap = new HashMap<>();
					valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);					

					//사례관리번호 설정
					sCaseMngNo = String.valueOf(valMap.get("RENU_NO"));
					mapIns.put("CASE_MNG_NO", sCaseMngNo);

					sCaseMngOdrno = "1";

				} else {

					/* 2022.08.11 추가
					 *  - 사례가 1건 이상 등록되어 있는 대상자의 경우 신규 사례 등록시
					 *    기존 MAX 사례관리차수의 사례대상에 대해 사례진행상태구분코드를 '11'(사례재등록)으로 UPDATE
					 */
					int nowCaseMngOdrno = Integer.parseInt(mapIns.get("CASE_MNG_ODRNO")); //현재 등록되어 있는 MAX 사례관리차수

					Map<String, String> cndMap = new HashMap<>();
					cndMap.put("CASE_MNG_NO"   		  , sCaseMngNo);					  //사례관리번호
					cndMap.put("CASE_MNG_ODRNO"		  , String.valueOf(nowCaseMngOdrno)); //사례관리차수
					cndMap.put("CASE_PRGRS_STTS_SE_CD", "11");	   						  //사례진행상태구분코드(11:사례재등록)
					cndMap.put("USER_ID"			  , userId);
					cndMap.put("DATAA_CHG_SE_CD"	  , "U");    						  //데이터변경구분코드

					//1. 사례기본 사례진행상태구분코드 수정
					caseRegMapper.updateCasePrgrsSttsSeCd(cndMap);
					//2. 사례기본 이력 등록
					caseRegMapper.insertSEB101Data(cndMap);

					sCaseMngOdrno = String.valueOf(nowCaseMngOdrno + 1);
				}
				
				mapIns.put("CASE_MNG_SE_CD"		, "04");						//사례관리구분코드(04:사례대상자선정)
				mapIns.put("CASE_TRPR_SLCTN_YMD", mapIns.get("CASE_BGNG_YMD")); //사례대상자선정일자
				mapIns.put("CASE_MNG_ODRNO"     , sCaseMngOdrno); 				//사례관리차수
				mapIns.put("TRMN_CASE_MNG_NO"   , "");			  				//종결사례관리번호
				mapIns.put("TRMN_CASE_MNG_ODRNO", "");			  				//종결사례관리차수

				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				//대상자정보 수정
				caseRegMapper.updateSEA200Data(mapIns);
				//대상자정보 이력 등록
				caseRegMapper.insertSEA201Data(mapIns);

				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				//사례기본 등록
				caseRegMapper.insertSEB100Data(mapIns);
				//사례기본 이력 등록
				caseRegMapper.insertSEB101Data(mapIns);

				sPrcsSe = "I";
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();

				mapUpd.put("CASE_MNG_SE_CD"		, "04");						//사례관리구분코드(04:사례대상자선정)
				mapUpd.put("CASE_TRPR_SLCTN_YMD", mapUpd.get("CASE_BGNG_YMD")); //사례대상자선정일자
				mapUpd.put("DATAA_CHG_SE_CD"	, "U");
				mapUpd.put("USER_ID"			, userId);

				//사례관리번호/사례관리차수 설정
				sCaseMngNo    = mapUpd.get("CASE_MNG_NO");
				sCaseMngOdrno = mapUpd.get("CASE_MNG_ODRNO");

				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				//대상자정보 수정
				caseRegMapper.updateSEA200Data(mapUpd);
				//대상자정보 이력 등록
				caseRegMapper.insertSEA201Data(mapUpd);

				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				//사례기본 수정
				caseRegMapper.updateSEB100Data(mapUpd);
				//사례기본 이력 등록
				caseRegMapper.insertSEB101Data(mapUpd);

				sPrcsSe = "U";
			}
		}

		//사례정보 미수정시 해당 데이터셋의 모든 상태의 데이터를 가져와서 사례관리번호, 사례관리차수를 설정
		if(sCaseMngNo.isEmpty())	sCaseMngNo	  = parameterGroup.getAllRowList().get(0).get("CASE_MNG_NO");
		if(sCaseMngOdrno.isEmpty())	sCaseMngOdrno = parameterGroup.getAllRowList().get(0).get("CASE_MNG_ODRNO");

		//기본정보 사례관리번호, 사례관리차수 전달
		request.setAttribute("CASE_MNG_NO"   , sCaseMngNo);
		request.setAttribute("CASE_MNG_ODRNO", sCaseMngOdrno);

		//문제상태 및 원인 저장
		saveCaseYngbgs(request, dataRequest);

		//사례담당자 저장
		savePic(request, dataRequest);

		//서비스사업등록 저장
		saveBizReg(request, dataRequest);

		rtnMap.put("CASE_MNG_NO"   , sCaseMngNo);
		rtnMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

		if("I".equals(sPrcsSe)) {

			/* 2022.10.06 추가
			 *  - 사례관리이력 등록
			 */
			Map<String, String> caseMngHstrmap = new HashMap<String, String>();
			caseMngHstrmap.put("CASE_MNG_NO"   , sCaseMngNo);
			caseMngHstrmap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			caseMngHstrmap.put("USER_ID"	   , userId);	

			caseRegMapper.insertSEB110Data(caseMngHstrmap);		
		}

		return rtnMap;
	}

	//문제상태 및 원인 저장
	private void saveCaseYngbgs(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsCaseYngbgs");

		if (parameterGroup != null) {
			Iterator<ParameterRow> insertedRows = parameterGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = parameterGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = parameterGroup.getDeletedRows();

			//등록 이벤트
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				mapIns.put("DATAA_CHG_SE_CD", "I");
				mapIns.put("USER_ID"		, userId);

				//사례관리번호/사례관리차수 설정
				String sCaseMngNo    = mapIns.get("CASE_MNG_NO");
				String sCaseMngOdrno = mapIns.get("CASE_MNG_ODRNO");

				if(sCaseMngNo.isEmpty()) {
					//전달받은 //사례관리번호/사례관리차수로 설정
					sCaseMngNo    = request.getAttribute("CASE_MNG_NO").toString();
					sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
					mapIns.put("CASE_MNG_NO"   , sCaseMngNo);
					mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				}

				//화면에서 전달받은 문제상태원인번호 설정
				String sProbmSttsCasNo = mapIns.get("PROBM_STTS_CAS_NO");

				if(sProbmSttsCasNo.isEmpty()) {
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();
					seqMap.put("RENU_NO_SE_CD", "PR");
					seqMap.put("RENU_YMD"	  , DateUtil.getToday()); // 현재일자
					seqMap.put("USER_ID"	  , userId);

					//문제상태원인번호 채번
					valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);

					//문제상태원인번호 설정
					sProbmSttsCasNo = String.valueOf(valMap.get("RENU_NO"));
					mapIns.put("PROBM_STTS_CAS_NO", sProbmSttsCasNo);
				}

				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				//문제상태원인 등록
				caseRegMapper.insertCaseYngbgs(mapIns);
				//문제상태원인이력 저장
				caseRegMapper.insertCaseYngbgsHstr(mapIns);				
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();

				mapUpd.put("DATAA_CHG_SE_CD", "U");
				mapUpd.put("USER_ID"		, userId);

				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				caseRegMapper.updateCaseYngbgs(mapUpd);
				//문제상태원인이력 저장
				caseRegMapper.insertCaseYngbgsHstr(mapUpd);
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();

				mapDel.put("DATAA_CHG_SE_CD", "D");
				mapDel.put("USER_ID"		, userId);

				//문제상태원인이력 저장
				caseRegMapper.insertCaseYngbgsHstr(mapDel);
				//문제상태원인 삭제
				caseRegMapper.deleteCaseYngbgs(mapDel);				
			}			
		}

		return;
	}

	//사례담당자 저장
	private void savePic(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsPic");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		String pchprsPicNo = ""; // 주담당자 번호
		String sCaseMngNo    = "";
		String sCaseMngOdrno = "";
		
		if (parameterGroup != null) {
			Iterator<ParameterRow> allRows      = parameterGroup.getAllRows();
			Iterator<ParameterRow> insertedRows = parameterGroup.getInsertedRows();
			Iterator<ParameterRow> updatedRows  = parameterGroup.getUpdatedRows();
			Iterator<ParameterRow> deletedRows  = parameterGroup.getDeletedRows();
			
			while (allRows.hasNext()) {
				ParameterRow paramRow = allRows.next();
				Map<String, String> mapAll = paramRow.toMap();
				if("Y".equals(mapAll.get("PCHPRS_YN"))) {	// 주 담당자
					if(!RowState.DELETED.equals(paramRow.getState())) {	// 삭제 row가 아님
						if("".equals(pchprsPicNo)) {	// 주 담당자번호가 비어있음
							pchprsPicNo = mapAll.get("CASE_PIC_NO");
						}
					}
				}
			}
			
			//등록 이벤트
			while (insertedRows.hasNext()) {
				
				Map<String, String> mapIns = insertedRows.next().toMap();
				//사례관리번호/사례관리차수 설정
				sCaseMngNo    = mapIns.get("CASE_MNG_NO");
				sCaseMngOdrno = mapIns.get("CASE_MNG_ODRNO");
				
				if(sCaseMngNo.isEmpty()) {
					//전달받은 사례관리번호/사례관리차수로 설정
					sCaseMngNo    = request.getAttribute("CASE_MNG_NO").toString();
					sCaseMngOdrno = request.getAttribute("CASE_MNG_ODRNO").toString();
				}

				mapIns.put("CASE_MNG_NO"    , sCaseMngNo);
				mapIns.put("CASE_MNG_ODRNO" , sCaseMngOdrno);
				mapIns.put("DATAA_CHG_SE_CD", "I");
				mapIns.put("USER_ID"		, userId);
				
				/* 2022.08.12 수정
				 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
				 */
				//사례담당자 등록
				caseRegMapper.saveSEB150Data(mapIns);
				//사례담당자이력 저장
				caseRegMapper.insertSEB151Data(mapIns);				
			}

			//수정 이벤트
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				//String oldPicNo = mapUpd.get("CASE_PIC_NO");
				//String newPicNo = mapUpd.get("NEW_CASE_PIC_NO");

				mapUpd.put("USER_ID"		, userId);
				mapUpd.put("DATAA_CHG_SE_CD", "U");

				//if(oldPicNo.equals(newPicNo)) {				

					/* 2022.08.12 수정
					 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
					 */
					//사례담당자 수정
					caseRegMapper.updateSEB150Data(mapUpd);
					//사례담당자이력 저장
					caseRegMapper.insertSEB151Data(mapUpd);

				//} else {
					//사례담당자이력 저장
				//	caseRegMapper.insertSEB151Data(mapUpd);
					//사례담당자 삭제
				//	caseRegMapper.deleteSEB150Data(mapUpd);

				//	mapUpd.put("CASE_PIC_NO", newPicNo);

					/* 2022.08.12 수정
					 * 저장을 위해 MERGE문으로 되어 있던 방식을 INSERT/UPDATE 쿼리로 분리하여 처리되도록 변경
					 */
					//사례담당자 등록
				//	caseRegMapper.insertSEB150Data(mapUpd);
					//사례담당자이력 저장
				//	caseRegMapper.insertSEB151Data(mapUpd);
				//}
			}

			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				// 삭제여부 셋팅
				mapDel.put("DEL_YN"			, "Y");
				mapDel.put("USER_ID"		, userId);
				mapDel.put("DATAA_CHG_SE_CD", "D");

				//사례담당자이력 저장
				caseRegMapper.insertSEB151Data(mapDel);
				//사례담당자 삭제
				caseRegMapper.deleteSEB150Data(mapDel);
			}
			
			// 주 담당자 누락 방지 수정
			if("".equals(sCaseMngNo) || null != sCaseMngNo ) {
				sCaseMngNo    = request.getAttribute("CASE_MNG_NO").toString();
			}
			if("".equals(sCaseMngOdrno) || null != sCaseMngOdrno ) {
				sCaseMngOdrno    = request.getAttribute("CASE_MNG_ODRNO").toString();
			}
			Map<String, String> mapPchprs = new HashMap<String, String>();
			mapPchprs.put("CASE_MNG_NO", sCaseMngNo);
			mapPchprs.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			int pchprsCnt = caseRegMapper.chkPchprsPic(mapPchprs);
			if(pchprsCnt == 0) {
				mapPchprs.put("CASE_PIC_NO", pchprsPicNo);
				caseRegMapper.updatePchprsPic(mapPchprs);
			}
		}
	}

	//서비스사업등록 저장
	private void saveBizReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsBizReg");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		if (parameterGroup != null) {
			Iterator<ParameterRow> insertedRows = parameterGroup.getInsertedRows();
			Iterator<ParameterRow> deletedRows  = parameterGroup.getDeletedRows();

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
				
				mapIns.put("CASE_MNG_NO"   , sCaseMngNo);
				mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				mapIns.put("USER_ID"	   , userId);

				//서비스사업대상자  등록
				caseRegMapper.insertSEB120Data(mapIns);
			}
			
			//삭제 이벤트
			while (deletedRows.hasNext()) {

				Map<String, String> mapDel = deletedRows.next().toMap();
				// 삭제여부 셋팅
				mapDel.put("DEL_YN" , "Y");
				mapDel.put("USER_ID", userId);

				//서비스사업대상자  삭제
				caseRegMapper.deleteSEB120Data(mapDel);
			}
		}
	}
	
	/**
	 * @Method명	 : selectOneScreenInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 8. 05. 
	 * @Method설명 : 원스크린 정보 조회
	 */	
	@Override
	public Map<String, Object> selectOneScreenInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String userDeptCd = "";
		String userNm     = "";
		String orgCd      = "";

		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userDeptCd = loginVO.getDeptCd();
			userNm     = loginVO.getUserName();
			orgCd      = String.valueOf(loginVO.getOrgCode());
		}

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		String trprInfoNo = ""; //대상자정보번호

		if (parameterGroup != null) {
			trprInfoNo = parameterGroup.getValue("TRPR_INFO_NO");			
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("TRPR_INFO_NO", trprInfoNo);

		//1. 기본정보 : 대상자정보
		LOGGER.debug("=========== 기본정보 : 대상자정보 START ===========");

		List<Map<String, Object>> trprInfoList = new ArrayList<Map<String,Object>>();

		Map<String, Object> trprInfoMap = new HashMap<>();
		trprInfoMap = trprInqMapper.selectTrprInqDetail(map);

		String trprRrno = ""; //대상자주민등록번호
		String trprFlnm = ""; //대상자성명

		String brthYmd 	= ""; //생년월일
		String sxdcSeCd = ""; //성별구분코드
		String telno 	= ""; //전화번호
		String mblTelNo = ""; //휴대전화번호
		String emlAddr  = ""; //이메일주소
		
		if(trprInfoMap != null) {

			//복호화 및 MASKING 처리
			//1.1.주민등록번호
			if(trprInfoMap.get("RRNO") != null && !"".equals(trprInfoMap.get("RRNO")) && !"null".equals(trprInfoMap.get("RRNO"))) {
				trprRrno = trprInfoMap.get("RRNO").toString();
				trprInfoMap.put("RRNO_ENCPT", trprRrno);
			}
	
			//1.2. 대상자명
			if(trprInfoMap.get("TRPR_NM") != null && !"".equals(trprInfoMap.get("TRPR_NM")) && !"null".equals(trprInfoMap.get("TRPR_NM"))) {
				trprFlnm = trprInfoMap.get("TRPR_NM").toString();
				trprInfoMap.put("TRPR_NM_ENCPT", trprFlnm);
			}
	
			//1.3.휴대전화번호
			if(trprInfoMap.get("MBL_TELNO") != null && !"".equals(trprInfoMap.get("MBL_TELNO")) && !"null".equals(trprInfoMap.get("MBL_TELNO"))) {
				mblTelNo = trprInfoMap.get("MBL_TELNO").toString();
				trprInfoMap.put("MBL_TELNO_ENCPT", mblTelNo);
			}
	
			//1.4.이메일주소
			if(trprInfoMap.get("EML_ADDR") != null && !"".equals(trprInfoMap.get("EML_ADDR")) && !"null".equals(trprInfoMap.get("EML_ADDR"))) {
				emlAddr = trprInfoMap.get("EML_ADDR").toString();
				trprInfoMap.put("EML_ADDR_ENCPT", emlAddr);
			}
	
			//1.5.주소
			if(trprInfoMap.get("TRPR_ADDR") != null && !"".equals(trprInfoMap.get("TRPR_ADDR")) && !"null".equals(trprInfoMap.get("TRPR_ADDR"))) {
				trprInfoMap.put("TRPR_ADDR", trprInfoMap.get("TRPR_ADDR").toString());
			}
	
			//1.6. 성별구분코드명 조회
			if(trprInfoMap.get("SXDC_SE_CD") != null && !"".equals(trprInfoMap.get("SXDC_SE_CD")) && !"null".equals(trprInfoMap.get("SXDC_SE_CD"))) {
				sxdcSeCd = trprInfoMap.get("SXDC_SE_CD").toString();

				Map<String, String> codeMap = new HashMap<>();

				codeMap.put("CMMNS_CD_ID"   , "SXDC_SE_CD");
				codeMap.put("CMMNS_CD_VALUE", sxdcSeCd);
				codeMap.put("USE_YN"        , "Y");

				List<Map<String, Object>> rtnList = comCodeMapper.selectComCodeList(codeMap);
				if(rtnList.size() > 0) {
					Map<String, Object> rtnMap = rtnList.get(0);
					trprInfoMap.put("SXDC_SE_CD_NM", rtnMap.get("CMMNS_CD_VALUE_NM").toString());					
				}				
			}
	
			//1.7 나이
			if(trprInfoMap.get("TRPR_BRTH_YMD") != null && !"".equals(trprInfoMap.get("TRPR_BRTH_YMD")) && !"null".equals(trprInfoMap.get("TRPR_BRTH_YMD"))) {
				brthYmd = trprInfoMap.get("TRPR_BRTH_YMD").toString();

				int age = Math.floorDiv(DateUtil.getDaysDiff(brthYmd, DateUtil.getToday()), 365);
				trprInfoMap.put("AGE", age);
			}
			
			//1.8 전화번호
			if(trprInfoMap.get("TRPR_TELNO") != null && !"".equals(trprInfoMap.get("TRPR_TELNO")) && !"null".equals(trprInfoMap.get("TRPR_TELNO"))) {
				telno = trprInfoMap.get("TRPR_TELNO").toString();
			}
		}

		trprInfoList.add(trprInfoMap);

		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("trprInfoList", trprInfoList);

		LOGGER.debug("=========== 기본정보 : 대상자정보 END ===========");

		Map<String, Object> etcInfoMap = new HashMap<String, Object>();
		List<Map<String, Object>> yngbgsSpclaSprtList = new ArrayList<Map<String,Object>>();

		Map<String, Object> reqMap = new HashMap<>();

		reqMap.put("rrno"    , trprRrno);
		reqMap.put("trprFlnm", trprFlnm);

		if(trprRrno != null && !"".equals(trprRrno) && !"null".equals(trprRrno)) {

			/* 2023-03-06 청소년특별지원은 복지부 개발이 되지 않아 데이터를 확인 할 수 없어 원스크린 팝업 오픈시 load않되도록함*/
			//2. 기본정보 - 청소년특별지원 연계
			yngbgsSpclaSprtList = getYngbgsSpclaSprt(reqMap);
			
			//3. 기본정보 - 기초생활수급대상/차상위계층대상/한무보수급대상 연계
			reqMap.put("reqOrgCd", orgCd);

			etcInfoMap = getEtcInfoLink(reqMap);
			
			if(yngbgsSpclaSprtList.size() > 0) {		
				etcInfoMap.put("YNGBGS_SPCLA_SPRT_YN", "Y");
			} else {
				etcInfoMap.put("YNGBGS_SPCLA_SPRT_YN", "N");
			}
		}

		List<Map<String, Object>> etcInfoList = new ArrayList<Map<String,Object>>();
		etcInfoList.add(etcInfoMap);

		resultMap.put("etcInfoList"		   , etcInfoList);
		resultMap.put("yngbgsSpclaSprtList", yngbgsSpclaSprtList);
		
		reqMap.clear();
		reqMap.put("trprRrno", trprRrno);
		reqMap.put("trprFlnm", trprFlnm);

		//4. 상담(발굴)이력
		List<Map<String, Object>> dscsnUneartHstrList = getDscsnUneartHstr(reqMap, trprInfoNo);
		resultMap.put("dscsnUneartHstrList", dscsnUneartHstrList);

		//5. 사례관리이력
		List<Map<String, Object>> caseMngHstrList = getCaseMngHstr(reqMap, trprInfoNo);
		resultMap.put("caseMngHstrList", caseMngHstrList);

		//6. 서비스제공이력
		List<Map<String, Object>> srvcPvsnHstrList = getSrvcPvsnHstr(reqMap, trprInfoNo);
		resultMap.put("srvcPvsnHstrList", srvcPvsnHstrList);

		//7. (구)시스템 이력(SEB900)
		Map<String, String> asisMap = new HashMap<String, String>();
		asisMap.put("FLNM_ENCPT"	, trprFlnm);
		asisMap.put("BRTH_YMD"		, brthYmd);
		asisMap.put("SXDC_SE_CD"	, sxdcSeCd);
		asisMap.put("TRPR_TELNO"	, telno);
		asisMap.put("MBL_TELNO"		, mblTelNo);
		asisMap.put("EML_ADDR_ENCPT", emlAddr);

		List<Map<String, Object>> asisSysHstrList = caseRegMapper.selectSEB900List(asisMap);
		resultMap.put("asisSysHstrList", asisSysHstrList);
		
		//8. 사업이력 - 2023-06-08 이승재 추가
		List<Map<String, Object>> bizHstrList = getBizHstr(reqMap, trprInfoNo);
		resultMap.put("bizHstrList", bizHstrList);

		return resultMap;
	}

	/**
	 * @Method명	 : getEtcInfoLink
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일	 : 2022. 8. 05. 
	 * @Method설명 : 기초생활수급대상/차상위계층대상/저소득대상지원 복지부 연계
	 *  - 2022.12.27 기준 기초생활수급대상 - Response 항목 중 mdslCrtrYmd(의료급여기준일자) 누락되어 응답받고 있음
	 *					 차상위계층자격정보 - Response 항목 중 사실여부(fctYn), 차상위자활대상자취득일자(nxhssLaborTrprAcqsYmd), 차상위자활대상자가구원여부(nxhssLaborTrprFmmbrYn) 누락되어 응답받고 있음
	 */
	private Map<String, Object> getEtcInfoLink(Map<String, Object> reqMap) throws Exception {

		LOGGER.debug("=========== 기초생활수급대상/차상위계층대상/저소득 한부모가족 연계 START ===========");

		String intrfcID1 = "INFIF_IR_SSI_WS_01"; //기초생활수급대상정보 조회 연계
		String intrfcID2 = "INFIF_IR_SSI_WS_02"; //차상위계층 자격정보 제공 연계
		String intrfcID3 = "INFIF_IR_SSI_WS_03"; //저소득 한부모가족 연계

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		Map<String, Object> map = new HashMap<>();

		//1.기초생활수급대상정보 조회 연계
		reqMap.put("reqBizCd", "ERGEF808SSI638W21759");
		
		Map<String, Object> sendMap    = new HashMap<>();
		Map<String, Object> messageMap = new HashMap<>();
		Map<String, Object> bodyMap    = new HashMap<>();

		bodyMap   .put("body"   , reqMap);
		messageMap.put("message", bodyMap);
		sendMap   .put("body"   , messageMap);

		json = mapper.writeValueAsString(messageMap);
		LOGGER.debug("기초생활수급대상정보 조회 연계 전달 json : " + json);
		
		String resResult = "";
		try {
			resResult = restService.sendREST(REQUEST_URL + intrfcID1, json);
		}catch(Exception e){
			throw new AppWorksException("서버와 연결이 되지 않았습니다.\n확인 후 다시 연결바랍니다.", Alert.ERROR);
		}
		LOGGER.debug("기초생활수급대상정보 조회 연계 응답 결과 : " + resResult);
		
		JSONParser parser   = new JSONParser(); //JSON Parser 객체 생성. parser를 통해 파싱
		JSONObject jsonObj  = null;				//Parser로 문자열 데이터를 JSON 데이터로 변환
		JSONObject jsonbody = null;

		Iterator iter = null;

		if(resResult != null && !"".equals(resResult)) {
			jsonObj  = (JSONObject)parser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환

			if(jsonObj.get("message") != null) {
				if(((JSONObject)jsonObj.get("message")).get("body") != null) {
					jsonbody = (JSONObject)((JSONObject)jsonObj.get("message")).get("body");

					iter = jsonbody.keySet().iterator();
					while(iter.hasNext()) {
						String key   = (String)iter.next();
						String value = "";
						if(jsonbody.get(key) != null) {
							value = jsonbody.get(key).toString();
						}

						//생계급여사실여부
						if("lvsryQlfcYn".equals(key)) {			
							key = "LVSRY_FCT_YN";

						//생계급여기준일자
						} else if("lvsryCrtrYmd".equals(key)) {
							key = "LVSRY_CRTR_YMD";

						//의료급여사실여부
						} else if("mdslQlfcYn".equals(key)) {
							key = "MDSL_FCT_YN";

						//의료급여기준일자 ← 데이터 안들어옴
						} else if("mdslAcqsYmd".equals(key)) {
							key = "MDSL_CRTR_YMD";

						//주거급여사실여부
						} else if("hsslQlfcYn".equals(key)) {
							key = "HSSL_FCT_YN";

						//주거급여기준일자
						} else if("hsslCrtrYmd".equals(key)) {
							key = "HSSL_CRTR_YMD";

						//교육급여사실여부
						} else if("eduslQlfcYn".equals(key)) {
							key = "EDUSL_FCT_YN";

						//교육급여기준일자
						} else if("eduslCrtrYmd".equals(key)) {
							key = "EDUSL_CRTR_YMD";
						}

						map.put(key, value);
					}
				}
			}
		}

		//2.차상위계층 자격정보 제공 연계
		reqMap.put("reqBizCd", "ERGEF808SSI638W21760");

		sendMap	  .clear();
		messageMap.clear();
		bodyMap   .clear();

		bodyMap   .put("body"   , reqMap);
		messageMap.put("message", bodyMap);
		sendMap   .put("body"   , messageMap);

		json = mapper.writeValueAsString(messageMap);
		LOGGER.debug("차상위계층 자격정보 제공 연계 전달 json : " + json);

		try {
			resResult = null;
			resResult = restService.sendREST(REQUEST_URL + intrfcID2, json);
			LOGGER.debug("차상위계층 자격정보 제공 연계 응답 결과 : " + resResult);
		}catch(Exception e) {
			throw new AppWorksException("서버와 연결이 되지 않았습니다.\n확인 후 다시 연결바랍니다.", Alert.ERROR);
		}

		if(jsonObj != null) {
			jsonObj .clear();
		}
		if(jsonbody != null) {
			jsonbody.clear();
		}

		iter = null;

		if(resResult != null && !"".equals(resResult)) {
			jsonObj = (JSONObject)parser.parse(resResult);
			if(jsonObj.get("message") != null) {
				if(((JSONObject)jsonObj.get("message")).get("body") != null) {
					jsonbody = (JSONObject)((JSONObject)jsonObj.get("message")).get("body");

					iter = jsonbody.keySet().iterator();
					while(iter.hasNext()) {
						String key = (String)iter.next();
						String value = "";
						if(jsonbody.get(key) != null) {
							value = jsonbody.get(key).toString();
						}

						//사실여부
						if("qlcfYn".equals(key)) {			
							key = "FCT_YN";

						//차상위장애인사실여부
						} else if("nxhgDspsnFctYn".equals(key)) {
							key = "NXHG_DSPSN_FCT_YN";

						//차상위장애인취득일자
						} else if("nxhgDspsnAcqsYmd".equals(key)) {
							key = "NXHG_DSPSN_ACQS_YMD";

						//차상위장애인가구원여부
						} else if("nxhgDspsnFmmbrYn".equals(key)) {
							key = "NXHG_DSPSN_FMMBR_YN";

						//차상위자활대상자사실여부
						} else if("nxhssLaborTrprFctYn".equals(key)) {
							key = "NXHG_SLFSPT_TRPR_FCT_YN";

						//차상위자활대상자취득일자
						} else if("nxhgsLaborTrprAcqsYmd".equals(key)) {
							key = "NXHG_SLFSPT_TRPR_ACQS_YMD";

						//차상위자활대상자가구원여부
						} else if("nxhgsLaborTrprFmmbrYn".equals(key)) {
							key = "NXHG_SLFSPT_TRPR_FMMBR_YN";

						//차상위본인부담대상자사실여부
						} else if("nxsbrdTrprFctYn".equals(key)) {
							key = "NXSBRD_TRPR_FCT_YN";

						//차상위본인부담대상자취득일자
						} else if("nxsbrdTrprAcqsYmd".equals(key)) {
							key = "NXSBRD_TRPR_ACQS_YMD";

						//차상위본인부담대상자가구원여부
						} else if("nxsbrdTrprFmmbrYn".equals(key)) {
							key = "NXSBRD_TRPR_FMMBR_YN";

						//차상위계층확인서발급취득일자
						} else if("nxhgCfdocIssuAcqsYmd".equals(key)) {
							key = "NXHGCL_IDNTY_ACQS_YMD";
						}

						map.put(key, value);
					}
				}
			}
		}

		//3.저소득 한부모가족 연계
		reqMap.put("reqBizCd", "ERGEF808SSI638W21761");

		sendMap	  .clear();
		messageMap.clear();
		bodyMap   .clear();

		bodyMap   .put("body"   , reqMap);
		messageMap.put("message", bodyMap);
		sendMap   .put("body"   , messageMap);

		json = mapper.writeValueAsString(messageMap);
		LOGGER.debug("저소득 한부모가족 연계 전달 json : " + json);

		try {
			resResult = null;
			resResult = restService.sendREST(REQUEST_URL + intrfcID3, json);
			LOGGER.debug("저소득 한부모가족 연계 응답 결과 : " + resResult);
		}catch(Exception e){
			throw new AppWorksException("서버와 연결이 되지 않았습니다.\n확인 후 다시 연결바랍니다", Alert.ERROR);
		}

		if(jsonObj != null) {
			jsonObj .clear();
		}
		if(jsonbody != null) {
			jsonbody.clear();
		}

		iter = null;

		if(resResult != null && !"".equals(resResult)) {
			jsonObj = (JSONObject)parser.parse(resResult);
			if(jsonObj.get("message") != null) {
				if(((JSONObject)jsonObj.get("message")).get("body") != null) {
					jsonbody = (JSONObject)((JSONObject)jsonObj.get("message")).get("body");

					iter = jsonbody.keySet().iterator();
					while(iter.hasNext()) {
						String key = (String)iter.next();
						String value = "";
						if(jsonbody.get(key) != null) {
							value = jsonbody.get(key).toString();
						}

						//한부모사실여부
						if("oprnQlfcYn".equals(key)) {			
							key = "OPRN_FCT_YN";

						//한부모취득일자
						} else if("oprnAcqsYmd".equals(key)) {
							key = "OPRN_ACQS_YMD";
						}

						map.put(key, value);
					}
				}
			}
		}		

		LOGGER.debug("=========== 결과  → " + map);
		LOGGER.debug("=========== 기초생활수급대상/차상위계층대상/저소득 한부모가족 연계 END ===========");

		return map;
	}

	/**
	 * @Method명	 : getYngbgsSpclaSprt
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일	 : 2022. 8. 05. 
	 * @Method설명 : 청소년특별지원 복지부 연계
	 *  - 2022.12.27 기준 우리쪽 개발은 되었지만 복지부쪽 담당자 부재로 테스트 진행하지 못함.
	 */
	private List<Map<String, Object>> getYngbgsSpclaSprt(Map<String, Object> reqMap) throws Exception {

		LOGGER.debug("=========== 청소년특별지원 연계 START ===========");

		String intrfcID = "INFIF_IR_SSI_WS_04"; //청소년특별지원 연계
		String resResult = "";
		
		List<Map<String, Object>> yngbgsSpclaSprtList = new ArrayList<Map<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		Map<String, Object> bodyMap    = new HashMap<>();
		Map<String, Object> messageMap = new HashMap<>();

		messageMap.put("message", reqMap);
		bodyMap   .put("body"   , messageMap);

		json = mapper.writeValueAsString(messageMap);
		LOGGER.debug("청소년특별지원 연계 전달 json : " + json);
		
		try {
			resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
		}catch(Exception e){
			throw new AppWorksException("서버와 연결이 되지 않았습니다.\n확인 후 다시 연결바랍니다", Alert.ERROR);
		}
		LOGGER.debug("청소년특별지원 연계 응답 결과 : " + resResult);

		JSONParser jParser  = new JSONParser(); //JSON Parser 객체 생성. parser를 통해 파싱

		JSONObject jObj     = null;
		JSONObject jsonbody = null;
		JSONArray  arrList  = null;

		if(resResult != null && !"".equals(resResult)) {
			jObj = (JSONObject)jParser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환			
			if(jObj.get("message") != null) {
				jsonbody = (JSONObject)jObj.get("message");
				if(jsonbody.get("list") != null) {
					arrList = (JSONArray)jsonbody.get("list");

					for(int i=0; i<arrList.size(); i++) {
						Map<String, Object> attMap = new HashMap<>();

						JSONObject jsObj = (JSONObject)arrList.get(i);
						Iterator iterator = jsObj.keySet().iterator();
						while(iterator.hasNext()) {
							String key   = (String)iterator.next();
							String value = "";
							if(jsObj.get(key) != null) {
								value = jsObj.get(key).toString();
							}

							//지원서비스ID
							if("spsrvId".equals(key)) {
								key = "SPSRV_ID";					
							}
							//지원서비스명
							else if("spsrvNm".equals(key)) {
								key = "SPSRV_NM";					
							}				
							//지원시작일자
							else if("sprtBgngYmd".equals(key)) {
								key = "SPRT_BGNG_YMD";					
							}				
							//지원종료일자
							else if("sprtEndYmd".equals(key)) {
								key = "SPRT_END_YMD";					
							}

							attMap.put(key, value);
						}

						yngbgsSpclaSprtList.add(attMap);
					}
				}
			}
		}

		LOGGER.debug("=========== 결과  → " + yngbgsSpclaSprtList);
		LOGGER.debug("=========== 청소년특별지원 연계 END ===========");

		return yngbgsSpclaSprtList;

	}

	/**
	 * @Method명	 : getDscsnUneartHstr
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일	 : 2022. 8. 05. 
	 * @Method설명 : 상담(발굴)이력 복지부 연계 + 여가부 상담(발굴)이력
	 */
	private List<Map<String, Object>> getDscsnUneartHstr(Map<String, Object> reqMap, String trprInfoNo) throws Exception {

		LOGGER.debug("=========== 상담(발굴)이력 START ===========");

		String intrfcID = "INFIF_IR_SSI_WS_06"; //상담이력 사보정 조회 연계

		List<Map<String, Object>> dscsnUneartHstrList = new ArrayList<Map<String,Object>>();

		String trprRrno = "";
		if(reqMap.get("trprRrno") != null) {
			trprRrno = reqMap.get("trprRrno").toString();
		}

		if(trprRrno != null && !"".equals(trprRrno) && !"null".equals(trprRrno)) {

			ObjectMapper mapper = new ObjectMapper();
			String json = null;

			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("body", reqMap);

			json = mapper.writeValueAsString(reqMap);
			LOGGER.debug("상담(발굴)이력 복지부 연계 전달 json : " + json);

			String resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
			LOGGER.debug("상담(발굴)이력 복지부 연계 연계 응답 결과 : " + resResult);

			JSONParser parser  = new JSONParser(); 				 	      //JSON Parser 객체 생성. parser를 통해 파싱

			if(resResult != null && !"".equals(resResult)) {
				JSONObject jsonObj = (JSONObject)parser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환
				if(jsonObj.get("list") != null) {
					JSONArray  arrList  = (JSONArray)jsonObj.get("list");

					for(int i=0; i<arrList.size(); i++) {
						Map<String, Object> map = new HashMap<>();

						JSONObject jsObj  = (JSONObject)arrList.get(i);
						Iterator iterator = jsObj.keySet().iterator();
						while(iterator.hasNext()) {
							String key   = (String)iterator.next();
							String value = "";
							if(jsObj.get(key) != null) {
								value = jsObj.get(key).toString();
							}

							//대상자주민등록번호
							if("trprRrno".equals(key)) {
								key = "TRPR_RRNO";
								if(value != null && !"".equals(value)) {
									value = Masking.rrnoMasking(value);
								}
							}
							//상담일시
							else if("dscsnDt".equals(key)) {
								key = "DSCSN_YMD";
								if(value != null && !"".equals(value)) {
									value = value.substring(0,8);
								}
							}
							//통합상담업무구분코드
							else if("intgDscsnTaskDcd".equals(key)) {
								key = "DSCSN_TASKWK_SE_CD";

								String sDscsnTaskwkSeNm = "";
								if("01".equals(value)) {
									sDscsnTaskwkSeNm = "초기상담";
								} else if("09".equals(value)) {
									sDscsnTaskwkSeNm = "모니터상담";
								} else if("18".equals(value)) {
									sDscsnTaskwkSeNm = "희망콜상담";
								} else if("99".equals(value)) {
									sDscsnTaskwkSeNm = "기타";
								}

								map.put("DSCSN_TASKWK_SE_NM", sDscsnTaskwkSeNm);
							}
							//상담접수경로코드
							else if("dscsnRcptRoutCd".equals(key)) {
								key = "DSCSN_COURS_SE_CD";
							}
							//서비스기관명
							else if("srvInstNm".equals(key)) {
								key = "DSCSN_INST_NM";
							}
							//사례관리유형ID
							else if("csmgTypId".equals(key)) {
								key = "DSCSN_TYPE_SE_CD";
							}
							//상담원성명
							else if("cnslrFlnm".equals(key)) {
								key = "CONSTT_NM";
								if(value != null && !"".equals(value)) {
									value = Masking.nameMasking(value);
								}
							}
							//연락처
							else if("ctadr".equals(key)) {
								key = "CONSTT_CTTPC";
								if(value != null && !"".equals(value)) {
									value = Masking.phoneMasking(value);
								}
							}
							//상담채널구분코드
							else if("dscsnChnlDcd".equals(key)) {
								key = "DSCSN_CHNNEL_SE_CD";
							}
							//상담내용
							else if("dscsnCn".equals(key)) {
								key = "DSCSN_CN";
							}

							map.put(key, value);
						}

						map.put("TRPR_FLNM", Masking.nameMasking(reqMap.get("trprFlnm").toString())); //대상자성명
						map.put("MOHW_LINK", "연계"); 				  							   //복지부연계

						dscsnUneartHstrList.add(map);
					}				
				}
			}
		}

		//여가부 상담(발굴)이력
		List<Map<String, Object>> mogefDscsnHstrList = caseRegMapper.selectDscsnHstrList(trprInfoNo);
		for(Map<String, Object> mogefMap : mogefDscsnHstrList) {
			//대상자명 복호화
			if(mogefMap.get("TRPR_FLNM") != null && !"".equals(mogefMap.get("TRPR_FLNM")) && !"null".equals(mogefMap.get("TRPR_FLNM"))) {
				mogefMap.put("TRPR_FLNM", mogefMap.get("TRPR_FLNM").toString());
			}

			//대상자주민등록번호 복호화
			if(mogefMap.get("TRPR_RRNO") != null && !"".equals(mogefMap.get("TRPR_RRNO")) && !"null".equals(mogefMap.get("TRPR_RRNO"))) {
				mogefMap.put("TRPR_RRNO", mogefMap.get("TRPR_RRNO").toString());
			}

			//상담원명 복호화
			if(mogefMap.get("CONSTT_NM") != null && !"".equals(mogefMap.get("CONSTT_NM")) && !"null".equals(mogefMap.get("CONSTT_NM"))) {
				mogefMap.put("CONSTT_NM", mogefMap.get("CONSTT_NM").toString());
			}

			//상담자휴대전화번호 복호화
			if(mogefMap.get("MBL_TELNO") != null && !"".equals(mogefMap.get("MBL_TELNO")) && !"null".equals(mogefMap.get("MBL_TELNO"))) {
				mogefMap.put("MBL_TELNO", mogefMap.get("MBL_TELNO").toString());
			}
			
			/* 일감그룹#450 담당자 연락처가 휴대전화번호로 나오는데 직정전화번호가 나오도록 수정*/
			mogefMap.put("CONSTT_CTTPC", (mogefMap.get("WRD_TELNO") != null ? mogefMap.get("WRD_TELNO") : "") );			

			
//			if(mogefMap.get("MBL_TELNO") != null && !"".equals(mogefMap.get("MBL_TELNO")) && !"null".equals(mogefMap.get("MBL_TELNO"))) {
//				mogefMap.put("CONSTT_CTTPC", mogefMap.get("MBL_TELNO"));
//			} else {
//				mogefMap.put("CONSTT_CTTPC", mogefMap.get("WRD_TELNO"));
//			}

			dscsnUneartHstrList.add(mogefMap);
		}

		LOGGER.debug("=========== 결과  → " + dscsnUneartHstrList);
		LOGGER.debug("=========== 상담(발굴)이력 END ===========");

		return dscsnUneartHstrList;

	}

	/**
	 * @Method명	 : getCaseMngHstr
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일	 : 2022. 8. 05.
	 * @Method설명 : 사례관리 이력 복지부 연계 + 여가부 사례관리 이력
	 */
	private List<Map<String, Object>> getCaseMngHstr(Map<String, Object> reqMap, String trprInfoNo) throws Exception {

		LOGGER.debug("=========== 사례관리이력 START ===========");

		String intrfcID = "INFIF_IR_SSI_WS_05"; //사례관리 사보정 조회 연계

		List<Map<String, Object>> caseMngHstrList = new ArrayList<Map<String,Object>>();

		String trprRrno = "";
		if(reqMap.get("trprRrno") != null) {
			trprRrno = reqMap.get("trprRrno").toString();
		}

		if(trprRrno != null && !"".equals(trprRrno) && !"null".equals(trprRrno)) {

			ObjectMapper mapper = new ObjectMapper();
			String json = null;

			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("body", reqMap);

			json = mapper.writeValueAsString(reqMap);
			LOGGER.debug("사례관리이력 복지부 연계 전달 json : " + json);

			String resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
			LOGGER.debug("사례관리 이력 복지부 연계 연계 응답 결과 : " + resResult);

			JSONParser parser  = new JSONParser(); 				 	   	  //JSON Parser 객체 생성. parser를 통해 파싱

			if(resResult != null && !"".equals(resResult)) {
				JSONObject jsonObj = (JSONObject)parser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환
				if(jsonObj.get("list") != null) {
					JSONArray  arrList  = (JSONArray)jsonObj.get("list");

					for(int i=0; i<arrList.size(); i++) {
						Map<String, Object> map = new HashMap<>();

						JSONObject jsObj  = (JSONObject)arrList.get(i);
						Iterator iterator = jsObj.keySet().iterator();
						while(iterator.hasNext()) {
							String key   = (String)iterator.next();
							String value = "";
							if(jsObj.get(key) != null) {
								value = jsObj.get(key).toString();
							}

							//대상자주민등록번호
							if("trprRrno".equals(key)) {
								key = "TRPR_RRNO";
								if(value != null && !"".equals(value)) {
									value = Masking.rrnoMasking(value);
								}
							}
							//사례관리번호
							else if("csmgNo".equals(key)) {
								key = "CASE_MNG_NO";
							}
							//사례관리차수
							else if("csmgDgr".equals(key)) {
								key = "CASE_MNG_ODRNO";
							}
							//사례관리대상구분코드
							else if("csmgTrgtDcd".equals(key)) {
								key = "TRPR_TYPE_SE_CD";

								String sTrprTypeSeNm = "";
								if("01".equals(value)) {
									sTrprTypeSeNm = "후보자";							
								} else if("02".equals(value)) {
									sTrprTypeSeNm = "사례관리대상자";							
								} else if("03".equals(value)) {
									sTrprTypeSeNm = "서비스연계대상자";							
								} else if("04".equals(value)) {
									sTrprTypeSeNm = "미선정대상자";							
								} else if("05".equals(value)) {
									sTrprTypeSeNm = "사례관리거부자";							
								} else if("06".equals(value)) {
									sTrprTypeSeNm = "사례관리종결자";							
								} else if("07".equals(value)) {
									sTrprTypeSeNm = "사후관리대상자";							
								} else if("99".equals(value)) {
									sTrprTypeSeNm = "기타";							
								}

								map.put("TRPR_TYPE_SE_NM", sTrprTypeSeNm);
							}
							//서비스기관명
							else if("srvInstNm".equals(key)) {
								key = "TKCG_INST_NM";
							}
							//연락처
							else if("ctadr".equals(key)) {
								key = "PIC_CTTPC";
								if(value != null && !"".equals(value)) {
									value = Masking.phoneMasking(value);
								}
							}
							//사례관리담당자성명
							else if("csmgPicFlnm".equals(key)) {
								key = "PIC_NM";
								if(value != null && !"".equals(value)) {
									value = Masking.nameMasking(value);
								}
							}
							//사례관리시작일자
							else if("csmgBgngYmd".equals(key)) {
								key = "CASE_MNG_BGNG_YMD";
							}
							//사례관리종료일자
							else if("csmgEndYmd".equals(key)) {
								key = "CASE_MNG_TRMN_YMD";
							}
							//사례관리유형ID
							else if("csmgTypId".equals(key)) {
								key = "CASE_MNG_TYPE_ID";
							}
							//사례관리표준업무처리ID
							else if("csmgStdTskprcId".equals(key)) {
								key = "CASE_MNG_STD_TASKWK_PRCS_ID";
							}
							//사례관리상세구분코드
							else if("csmgDtlDcd".equals(key)) {
								key = "CASE_MNG_DTL_SE_CD";
							}

							map.put(key, value);
						}

						map.put("TRPR_FLNM", Masking.nameMasking(reqMap.get("trprFlnm").toString())); //대상자성명
						map.put("MOHW_LINK", "연계"); 				 	 						   //복지부연계

						caseMngHstrList.add(map);
					}
				}
			}
		}

		// 여가부 사례관리 이력
		List<Map<String, Object>> mogefCaseMngHstrList = caseRegMapper.selectCaseMngHstrList(trprInfoNo);
		for(Map<String, Object> mogefMap : mogefCaseMngHstrList) {
			//대상자명 복호화
			if(mogefMap.get("TRPR_FLNM") != null && !"".equals(mogefMap.get("TRPR_FLNM")) && !"null".equals(mogefMap.get("TRPR_FLNM"))) {
				mogefMap.put("TRPR_FLNM", mogefMap.get("TRPR_FLNM").toString());
			}

			//대상자주민등록번호 복호화
			if(mogefMap.get("TRPR_RRNO") != null && !"".equals(mogefMap.get("TRPR_RRNO")) && !"null".equals(mogefMap.get("TRPR_RRNO"))) {
				mogefMap.put("TRPR_RRNO", mogefMap.get("TRPR_RRNO").toString());
			}

			//담당자명 복호화
			if(mogefMap.get("PIC_NM") != null && !"".equals(mogefMap.get("PIC_NM")) && !"null".equals(mogefMap.get("PIC_NM"))) {
				mogefMap.put("PIC_NM", mogefMap.get("PIC_NM").toString());
			}

			//담당자휴대전화번호 복호화
			if(mogefMap.get("MBL_TELNO") != null && !"".equals(mogefMap.get("MBL_TELNO")) && !"null".equals(mogefMap.get("MBL_TELNO"))) {
				mogefMap.put("MBL_TELNO", mogefMap.get("MBL_TELNO").toString());
			}
			
			/* 일감그룹#450 담당자 연락처가 휴대전화번호로 나오는데 직정전화번호가 나오도록 수정*/			
			mogefMap.put("PIC_CTTPC", (mogefMap.get("WRD_TELNO") != null ? mogefMap.get("WRD_TELNO") : "") );			

//			if(mogefMap.get("MBL_TELNO") != null && !"".equals(mogefMap.get("MBL_TELNO")) && !"null".equals(mogefMap.get("MBL_TELNO"))) {
//				mogefMap.put("PIC_CTTPC", mogefMap.get("MBL_TELNO"));
//			} else {
//				mogefMap.put("PIC_CTTPC", mogefMap.get("WRD_TELNO"));
//			}

			caseMngHstrList.add(mogefMap);
		}

		LOGGER.debug("=========== 결과  → " + caseMngHstrList);
		LOGGER.debug("=========== 사례관리이력 END ===========");

		return caseMngHstrList;

	}

	/**
	 * @Method명	 : getSrvcPvsnHstr
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일	 : 2022. 8. 05. 
	 * @Method설명 : 서비스제공 이력 복지부 연계 + 여가부 서비스제공 이력
	 */
	private List<Map<String, Object>> getSrvcPvsnHstr(Map<String, Object> reqMap, String trprInfoNo) throws Exception {

		LOGGER.debug("=========== 서비스제공이력 START ===========");

		String intrfcID = "INFIF_IR_SSI_WS_07"; //서비스이력 사서정 조회 연계

		List<Map<String, Object>> srvcPvsnHstrList = new ArrayList<Map<String,Object>>();

		String trprRrno = "";
		if(reqMap.get("trprRrno") != null) {
			trprRrno = reqMap.get("trprRrno").toString();
		}
		
		String[][] keyArray = {  {"trprFlnm"	   ,"TRPR_FLNM"}			 //대상자성명
								,{"trprRrno"	   ,"TRPR_RRNO"}			 //대상자주민등록번호
								,{"trprSpclCn"	   ,"TRPR_SPCL_INFO_CN"}	 //대상자특화내용
								,{"rscPrvsvId"	   ,"PVSN_SRVC_ID"}			 //자원제공서비스ID
								,{"prvsvNm"		   ,"PVSN_SRVC_NM"}			 //제공서비스명
								,{"rscPvsnMbdId"   ,"PVSN_MBD_ID"}			 //자원제공주체ID
								,{"rscPvsnMbdNm"   ,"PVSN_MBD_NM"}			 //자원제공주체명
								,{"srvBgngYmd"	   ,"SRVC_BGNG_YMD"}		 //서비스시작일자
								,{"srvEndYmd"	   ,"SRVC_END_YMD"}			 //서비스종료일자
								,{"cvlPubDcd"	   ,"SRVC_PVSN_SE_CD"}		 //서비스제공구분코드
								,{"srvPvsnCycCd"   ,"SRVC_PVSN_CYCL_SE_CD"}	 //서비스제공주기코드
								,{"srvPvsnCycNm"   ,"SRVC_PVSN_CYCL_SE_NM"}	 //서비스제공주기명
								,{"srvBnfCn"	   ,"SRVC_BNF_CN"}			 //서비스수혜내용
								,{"srvPvsnCn"	   ,"SRVC_PVSN_CN"}			 //서비스제공내용
								,{"srvPvsnPicFlnm" ,"SRVC_PVSN_PIC_NM"}		 //서비스제공담당자성명
								,{"srvPvsnPicTelno","SRVC_PVSN_PIC_CTTPC"}}; //서비스제공담당자전화번호

		if(trprRrno != null && !"".equals(trprRrno) && !"null".equals(trprRrno)) {

			ObjectMapper mapper = new ObjectMapper();
			String json = null;

			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("body", reqMap);

			json = mapper.writeValueAsString(reqMap);
			LOGGER.debug("서비스제공 이력 복지부 연계 전달 json : " + json);

			String resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
			LOGGER.debug("서비스제공 이력 복지부 연계 연계 응답 결과 : " + resResult);

			JSONParser parser  = new JSONParser(); 				 	  //JSON Parser 객체 생성. parser를 통해 파싱
			
			if(resResult != null && !"".equals(resResult)) {
				JSONObject jsonObj = (JSONObject)parser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환
				if(jsonObj.get("list") != null) {
					JSONArray  arrList  = (JSONArray)jsonObj.get("list");

					for(int i=0; i<arrList.size(); i++) {
						Map<String, Object> map = new HashMap<>();
						
						JSONObject jsObj  = (JSONObject)arrList.get(i);
						Iterator iterator = jsObj.keySet().iterator();
						while(iterator.hasNext()) {
							String key   = (String)iterator.next();
							String value = "";
							if(jsObj.get(key) != null) {
								value = jsObj.get(key).toString();
							}
							
							/* 2023-04-04 지자체는 나머지 null이고 수혜이력만 보내기때문에 수혜이력을 자원서비스명으로 매핑 추가*/
							if(jsObj.get("prvsvNm") == null) {
								map.put("PVSN_SRVC_NM", jsObj.get("srvBnfCn"));
							}

							for(int j=0; j<keyArray.length; j++) {
								if(key.equals(keyArray[j][0])) {
									if("trprFlnm".equals(key) || "srvPvsnPicFlnm".equals(key)) {
										if(value != null && !"".equals(value)) {
											value = Masking.nameMasking(value);
										}
									} else if("trprRrno".equals(key)) {
										if(value != null && !"".equals(value)) {
											value = Masking.rrnoMasking(value);
										}
									} else if("srvPvsnPicTelno".equals(key)) {
										if(value != null && !"".equals(value)) {
											value = Masking.phoneMasking(value);
										}
									}
									
									map.put(keyArray[j][1], value);
								}
							}
						}

						map.put("MOHW_LINK", "연계"); //복지부연계

						srvcPvsnHstrList.add(map);

					}
				}
			}
		}

		// 여가부 서비스제공 이력
		List<Map<String, Object>> mogefSrvcPvsnHstrList = caseRegMapper.selectSrvcPvsnHstrList(trprInfoNo);
		for(Map<String, Object> mogefMap : mogefSrvcPvsnHstrList) {

			
			/* 일감그룹#450 담당자 연락처가 휴대전화번호로 나오는데 직정전화번호가 나오도록 수정*/			
			mogefMap.put("SRVC_PVSN_PIC_CTTPC", (mogefMap.get("WRD_TELNO") != null ? mogefMap.get("WRD_TELNO") : "") );			

//			if(mogefMap.get("MBL_TELNO") != null && !"".equals(mogefMap.get("MBL_TELNO")) && !"null".equals(mogefMap.get("MBL_TELNO"))) {
//				mogefMap.put("SRVC_PVSN_PIC_CTTPC", Masking.phoneMasking(mogefMap.get("MBL_TELNO").toString()));
//			} else if(mogefMap.get("WRD_TELNO") != null && !"".equals(mogefMap.get("WRD_TELNO")) && !"null".equals(mogefMap.get("WRD_TELNO"))) {
//				mogefMap.put("SRVC_PVSN_PIC_CTTPC", Masking.phoneMasking(mogefMap.get("WRD_TELNO").toString()));
//			}

			srvcPvsnHstrList.add(mogefMap);
		}

		LOGGER.debug("=========== 결과  → " + srvcPvsnHstrList);
		LOGGER.debug("=========== 서비스제공이력 END ===========");

		return srvcPvsnHstrList;

	}
	
	/**
	 * @Method명	 : getSrvcPvsnHstr
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Lee.Seoung.Jae
	 * @작성일	 : 2023. 6. 08. 
	 * @Method설명 : 여가부 사업 이력
	 */
	private List<Map<String, Object>> getBizHstr(Map<String, Object> reqMap, String trprInfoNo) throws Exception {

		LOGGER.debug("=========== 사업이력 START ===========");
		
		List<Map<String, Object>> bizHstrList = caseRegMapper.selectBizHstrList(trprInfoNo);
		
		LOGGER.debug("=========== 결과  → " + bizHstrList);
		LOGGER.debug("=========== 서비스제공이력 END ===========");

		return bizHstrList;

	}

	/**
	 * @Method명	 : selectTrprFamBeischList
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 8. 24. 
	 * @Method설명 : 청소년상태대분류 정합성 체크를 위한 대상자 가족특성/졸업상태 조회
	 */
	@Override
	public List<Map<String, Object>> selectTrprFamBeischList(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");		

		String tprpInfoNo = "";

		if (parameterGroup != null) {
			tprpInfoNo = parameterGroup.getValue("TRPR_INFO_NO");			
		}

		//대상자 가족특성/졸업상태 조회
		return caseRegMapper.selectTrprFamBeischList(tprpInfoNo);
	}
	
	/**
	 * @Method명	 : selectFamCnsttnInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 10. 25.
	 * @Method설명 : 가족구성정보 조회(행정안전부 실시간 연계)
	 */	
	@Override
	public Map<String, Object> selectFamCnsttnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String userDeptCd = "";
		String userName   = "";
		String orgCd      = "";

		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userDeptCd = loginVO.getDeptCd();
			userName   = loginVO.getUserName();
			if(loginVO.getUserInstNo() != null) {
//				orgCd  = loginVO.getUserInstNo().toString();
				orgCd  = "10D0000001";
			}
		}

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		String trprInfoNo = ""; //대상자정보번호
		if (parameterGroup != null) {
			trprInfoNo = parameterGroup.getValue("TRPR_INFO_NO");			
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("TRPR_INFO_NO", trprInfoNo);

		//대상자정보조회
		Map<String, Object> trprInfoMap = new HashMap<>();
		trprInfoMap = trprInqMapper.selectTrprInqDetail(map);

		String trprRrno = ""; //대상자주민등록번호
		String trprFlnm = ""; //대상자성명

		//복호화 처리
		if(trprInfoMap != null) {

			//주민등록번호
			if(trprInfoMap.get("RRNO_ENCPT") != null && !"".equals(trprInfoMap.get("RRNO_ENCPT")) && !"null".equals(trprInfoMap.get("RRNO_ENCPT"))) {
				trprRrno = trprInfoMap.get("RRNO_ENCPT").toString();
			}

			//대상자명
			if(trprInfoMap.get("TRPR_NM_ENCPT") != null && !"".equals(trprInfoMap.get("TRPR_NM_ENCPT")) && !"null".equals(trprInfoMap.get("TRPR_NM_ENCPT"))) {
				trprFlnm = trprInfoMap.get("TRPR_NM_ENCPT").toString();				
			}
		}

//		LOGGER.debug("주민등록번호 : " + trprRrno);
//		LOGGER.debug("대상자명 : " + trprFlnm);

		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("userDeptCode", userDeptCd);
		reqMap.put("userName"    , userName);
		reqMap.put("orgCode"     , orgCd);
		reqMap.put("id"    		 , trprRrno);
		reqMap.put("name"        , trprFlnm);

		Map<String, Object> resultMap = new HashMap<>();

		List<Map<String, Object>> famCnsttnList = new ArrayList<Map<String,Object>>();

		if(trprRrno != null && !"".equals(trprRrno) && !"null".equals(trprRrno)) {

			String intrfcID = "INFIF_IR_MOI_WS_01"; //세대주소정보확인서비스

			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			json  = mapper.writeValueAsString(reqMap);

			String resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
			LOGGER.debug("세대주소정보확인서비스 행안부 연계 응답 결과 : " + resResult);

			JSONParser parser  = new JSONParser(); 				 	  	  //JSON Parser 객체 생성. parser를 통해 파싱
			
			if(resResult != null && !"".equals(resResult)) {
				JSONObject jsonObj = (JSONObject)parser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환
//				LOGGER.debug("jsonObj : " + jsonObj.toString());

				JSONObject jsonBody = (JSONObject)((JSONObject)((JSONObject)jsonObj.get("soap:Envelope")).get("soap:Body")).get("getJmnHouseAddressInfoResponse");
//				LOGGER.debug("jsonBody : " + jsonBody.toString());

				String serviceResult = jsonBody.get("serviceResult").toString();
//				LOGGER.debug("세대주소정보확인서비스결과 : " + serviceResult);
//				LOGGER.debug("세대주소정보확인서비스결과1111 : " + jsonBody.get("sedaewonList"));

				JSONArray arrList = null;
				if("1".equals(serviceResult)) { //1:성공
					
					String sedaewonArrChk = jsonBody.get("sedaewonList").toString().substring(0, 1);
					/* 2023-03-28 세대정보가 1건인경우는 array 타입이 아닌 json 타입으로 들어옴*/
					
//					LOGGER.debug("세대주소정보확인서비스결과2222=[ " + sedaewonArrChk + "]");
					
					if("{".equals(sedaewonArrChk)) {
						JSONArray req_arr = new JSONArray();
						req_arr.add(jsonBody.get("sedaewonList"));
						
						arrList = req_arr;
					}else if("[".equals(sedaewonArrChk)){
						arrList = (JSONArray)jsonBody.get("sedaewonList");				
					}

					for(int i=0; i<arrList.size(); i++) {
						Map<String, Object> attMap = new HashMap<>();

						JSONObject jsObj = (JSONObject)arrList.get(i);
						Iterator iterator = jsObj.keySet().iterator();
						while(iterator.hasNext()) {
							String key = (String)iterator.next();
							String value = "";
							if(jsObj.get(key) != null) {
								value = jsObj.get(key).toString();
							}

							//세대원 주민등록번호
							if("S_ID".equals(key)) {
								key   = "FAM_RRNO";
								if(value != null && !"".equals(value)) {
									value = Masking.rrnoMasking(value);
								}
							}
							//세대원 성명
							else if("S_NM".equals(key)) {
								key = "FAM_FLNM";
								if(value != null && !"".equals(value)) {
									value = Masking.nameMasking(value);
								}
							}
							//세대원 세대주와의관계
							else if("S_Sedaejukwan".equals(key)) {
								key = "FAM_REL";
							}
							//세대원 세대주와의관계 한글명
							else if("S_SedaejukwanNm".equals(key)) {
								key = "FAM_REL_NM";
							}

							attMap.put(key, value);
						}

						famCnsttnList.add(attMap);
					}								
				}
			}
		}

		resultMap.put("famCnsttnList", famCnsttnList);

		return resultMap;
	}

	/**
	 * @Method명	 : selectPrgrsStts
	 * @param	 : map
	 * @return
	 * @throws Exception
	 * @작성자 	 : Hee Sung Yoon
	 * @작성일  	 : 2023. 07. 04.
	 * @Method설명 : 사례 상태에 따라 저장 금지
	 */	
	public void selectPrgrsStts(Map<String, String> map) throws Exception {
		Map<String, String> prgrsSttsMap = caseRegMapper.selectCasePrgrsStts(map);
		String prgrsSttsSeCd = "";
		if(null != prgrsSttsMap) {	// 신규가 아니면
			if(!"".equals(prgrsSttsMap.get("CASE_PRGRS_STTS_SE_CD")) && null != prgrsSttsMap.get("CASE_PRGRS_STTS_SE_CD")) {
				prgrsSttsSeCd = prgrsSttsMap.get("CASE_PRGRS_STTS_SE_CD");
			}
			// 등록 저장 시 사례진행상태구분코드는 01, 02, 03, 06(등록, 계획, 실행, 사후) 중 하나여야 한다
			if(!"01".equals(prgrsSttsSeCd) && !"02".equals(prgrsSttsSeCd)
			&& !"03".equals(prgrsSttsSeCd) && !"06".equals(prgrsSttsSeCd)) {
				String exceptionMsg = "사례 상태가 '사례등록', '서비스계획', '서비스실행'\n";
				exceptionMsg += "또는 '성과관리' 중 하나여야 합니다.\n";
				exceptionMsg += "현재 사례 상태는 '" + prgrsSttsMap.get("CASE_PRGRS_STTS_SE_NM") + "'입니다.";
				throw new AppWorksException(exceptionMsg);
			}
		}
		
	}
}
