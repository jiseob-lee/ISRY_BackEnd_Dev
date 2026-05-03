/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.crtrinfo.resrce.mapper.SrvcResrceMapper;
import isry.itgcm.crtrinfo.resrce.service.SrvcResrceService;
import isry.itgcms.sysmgmt.file.mapper.MgmtFileMapper;
import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;

/**
 * @Class Name : SrvcResrceServiceImpl.java
 * @Description : 자원정보 ServiceImpl Class
 *
 * @author : Kwon.Min.Seo
 * @since : 2022. 06. 24.
 * @version : 1.0
 * @see
 * 
 *      <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 24.  Kwon.Min.Seo    최초작성
 *      </pre>
 */
@Service("srvcResrceService")
public class SrvcResrceServiceImpl extends EgovAbstractServiceImpl implements SrvcResrceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "srvcResrceMapper")
	private SrvcResrceMapper srvcResrceMapper;

	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper; /* 채번 Mapper */

	@Resource(name = "mgmtFileService")
	private MgmtFileService mgmtFileService; /* 첨부파일 Mapper */

	@Resource(name = "mgmtFileMapper")
	private MgmtFileMapper mgmtFileMapper; /* 첨부파일 Mapper */

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 공통코드 관련 서비스
	// @Resource(name = "mgmtCmmnCodeService")
	// private MgmtCmmnCodeService mgmtCmmnCodeService;

	/**
	 * @Method : selectResrceList
	 * @Method설명 : 자원 목록조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public List<Map<String, Object>> selectResrceList(DataRequest dataRequest, HttpServletRequest request)
			throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrceList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		String sSrvcYr = paramGroup.getValue("SRVC_YR"); // 서비스년도
		if (sSrvcYr == null || sSrvcYr.equals("null") || sSrvcYr.equals("")) {
			throw new AppWorksException("서비스년도는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		String oUntTaskwk = ""; // 현재 선택된 단위 시스템 코드

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		oUntTaskwk = loginVO.getUntTaskwk();

		paramMap.put("UNT_TASKWK_CD", oUntTaskwk);

		// 우리기관이 제공주체로 등록한 자원만 보기.
		if ("Y".equals(paramMap.get("RSFR_INST_YN"))) {
			paramMap.put("RSFR_INST_NO", String.valueOf(loginVO.getInstNo()));
		} else { // 초기화 처리.
			paramMap.put("RSFR_INST_NO", null);
		}

		// 우리기관에서 등록한 자원만 보기.
		if ("Y".equals(paramMap.get("PIC_INST_YN"))) {
			paramMap.put("PIC_INST_NO", String.valueOf(loginVO.getInstNo()));
		} else {
			paramMap.put("PIC_INST_NO", null);
		}

		return srvcResrceMapper.selectResrceList(paramMap);
	}

	@Override
	public Map<String, Object> selectResrcePagingList(DataRequest dataRequest, HttpServletRequest request)
			throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		String oUntTaskwk = ""; // 현재 선택된 단위 시스템 코드

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		oUntTaskwk = loginVO.getUntTaskwk();

		paramMap.put("UNT_TASKWK_CD", oUntTaskwk);

		// 우리기관이 제공주체로 등록한 자원만 보기.
		if ("Y".equals(paramMap.get("RSFR_INST_YN"))) {
			paramMap.put("RSFR_INST_NO", String.valueOf(loginVO.getInstNo()));
		} else { // 초기화 처리.
			paramMap.put("RSFR_INST_NO", null);
		}

		// 우리기관에서 등록한 자원만 보기.
		if ("Y".equals(paramMap.get("PIC_INST_YN"))) {
			paramMap.put("PIC_INST_NO", String.valueOf(loginVO.getInstNo()));
		} else {
			paramMap.put("PIC_INST_NO", null);
		}

		Map<String, Object> paramMap2 = new HashMap<>();

		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		String TOT_CNT = srvcResrceMapper.selectResrceCount(paramMap2);
		paramMap2.put("TOT_CNT", TOT_CNT);

		int totCnt = (TOT_CNT == null || TOT_CNT.trim().isEmpty()) ? 0 : Integer.valueOf(TOT_CNT);
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));

		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;

		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);

		// 자원 목록조회
		if(totCnt > 0) {
			retList = srvcResrceMapper.selectResrcePagingList(paramMap2);	
		}

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		Map<String, Object> result = new HashMap<>();
		result.put("dsList", retList);
		result.put("dmPageInfo", resPage);

		return result;
	}

	/**
	 * @Method : selectResrceDetail
	 * @Method설명 : 자원 상세조회
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public Map<String, Object> selectResrceDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrceDetail.paramGroup=[" + paramGroup + "]");

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		// 자원 상세조회
		List<Map<String, Object>> dsDetail = new ArrayList<Map<String, Object>>();
		dsDetail = srvcResrceMapper.selectResrceDetail(paramMap);

		// 자원 프로그램조회
		List<Map<String, Object>> dsProgrmList = new ArrayList<Map<String, Object>>();
		dsProgrmList = srvcResrceMapper.selectResrceProgrmList(paramMap);

		// 자원 프로그램상세일정
		List<Map<String, Object>> dsSchdlList = new ArrayList<Map<String, Object>>();
		dsSchdlList = srvcResrceMapper.selectResrceProgrmSchdlList(paramMap);

		if (dsSchdlList.size() > 0) {
			// 자원 프로그램강사
			paramMap.put("LCTRE_SN", String.valueOf(dsSchdlList.get(0).get("LCTRE_SN")));
			List<Map<String, Object>> dsInstrList = new ArrayList<Map<String, Object>>();
			dsInstrList = srvcResrceMapper.selectResrceProgrmInstrList(paramMap);

			retMap.put("dsInstrList", dsInstrList);
		}

		// 자원 담당자조회
		List<Map<String, Object>> dsPicList = new ArrayList<Map<String, Object>>();
		dsPicList = srvcResrceMapper.selectResrcePicList(paramMap);

		// 자원 변경이력
		List<Map<String, Object>> dsChgHstrList = new ArrayList<Map<String, Object>>();
		dsChgHstrList = srvcResrceMapper.selectResrceChgHstrList(paramMap);

		// MDFR_NM
		// 입교일자 조회 0811
//		List<Map<String, Object>> dsEntscList = new ArrayList<Map<String, Object>>();
//		dsEntscList = srvcResrceMapper.selectEntsc(paramMap);	

		retMap.put("dsDetail", dsDetail);
		retMap.put("dsProgrmList", dsProgrmList);
		retMap.put("dsSchdlList", dsSchdlList);
		retMap.put("dsPicList", dsPicList);
		retMap.put("dsChgHstrList", dsChgHstrList);
//		retMap.put("dsEntscList"	, dsEntscList);

		return retMap;
	}

	/**
	 * @Method : selectResrceProgrmList
	 * @Method설명 : 자원 프로그램조회
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public List<Map<String, Object>> selectResrceProgrmList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
//		LOGGER.debug("SrvcResrceServiceImpl.selectResrceProgrmList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcResrceMapper.selectResrceProgrmList(paramMap);
	}

	/**
	 * @Method : selectResrceProgrmSchdlList
	 * @Method설명 : 자원 프로그램상세일정
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public List<Map<String, Object>> selectResrceProgrmSchdlList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrceProgrmSchdlList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcResrceMapper.selectResrceProgrmSchdlList(paramMap);
	}

	/**
	 * @Method : selectResrceProgrmInstrList
	 * @Method설명 : 자원 프로그램강사
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public List<Map<String, Object>> selectResrceProgrmInstrList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrceProgrmInstrList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		String sResrceNo = paramGroup.getValue("RESRCE_NO"); // 자원번호
		String sProgrmNo = paramGroup.getValue("PROGRM_NO"); // 프로그램번호

		if (sResrceNo == null || sResrceNo.equals("null") || sResrceNo.equals("")) {
			throw new AppWorksException("자원번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		} else if (sProgrmNo == null || sProgrmNo.equals("null") || sProgrmNo.equals("")) {
			throw new AppWorksException("프로그램번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		return srvcResrceMapper.selectResrceProgrmInstrList(paramMap);
	}

	/**
	 * @Method : selectResrcePicList
	 * @Method설명 : 자원 담당자조회
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public List<Map<String, Object>> selectResrcePicList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrcePicList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		String sResrceNo = paramGroup.getValue("RESRCE_NO"); // 자원번호
		if (sResrceNo == null || sResrceNo.equals("null") || sResrceNo.equals("")) {
			throw new AppWorksException("자원번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		return srvcResrceMapper.selectResrcePicList(paramMap);
	}

	/**
	 * @Method : selectResrceChgHstrList
	 * @Method설명 : 자원 변경이력
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public List<Map<String, Object>> selectResrceChgHstrList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrceChgHstrList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		String sResrceNo = paramGroup.getValue("RESRCE_NO"); // 자원번호
		if (sResrceNo == null || sResrceNo.equals("null") || sResrceNo.equals("")) {
			// paramMap.put("RESRCE_NO", sResrceNo);
			throw new AppWorksException("자원번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		return srvcResrceMapper.selectResrceChgHstrList(paramMap);
	}

	/**
	 * @Method : processResrceDetail
	 * @Method설명 : 자원 상세저장(등록,수정,삭제,이력)
	 * @param : request
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public Map<String, Object> processResrceDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();

		String sUserId = ""; // 세션정보의 유저ID
		String sWprkSqn = ""; // 채번번호
		String sWprkSqn1 = ""; // 채번번호
		String sWprkSqn2 = ""; // 강의번호

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		/* -------------- */
		/* 자원 상세 처리 */
		/* -------------- */
		// 자원상세자료 DataSet
		ParameterGroup paramDmDetail = dataRequest.getParameterGroup("dsDetail");
		
		if (paramDmDetail == null || paramDmDetail.rowSize() == 0) {
			throw new AppWorksException("저장할 자료가 없읍니다.", Alert.ERROR);
		}
		
		// System.out.println(paramDmDetail.get(0).getValue("RSFR_INST_YN"));
		// 일괄등록 선택 안하였을 경우. 수정시에도 적용될 수 있도록 Y가 아닐경우로 적용.
		if (!"Y".equals(paramDmDetail.get(0).getValue("RSFR_INST_YN"))) {
			LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.paramDmDetail=[" + paramDmDetail + "]");

			if (paramDmDetail != null) {
				Iterator<ParameterRow> insertedRows = paramDmDetail.getInsertedRows();
				Iterator<ParameterRow> updatedRows = paramDmDetail.getUpdatedRows();
				Iterator<ParameterRow> deletedRows = paramDmDetail.getDeletedRows();

				// 등록 이벤트
				while (insertedRows.hasNext()) {

					Map<String, String> mapIns = insertedRows.next().toMap();
					String sPvsnResrceNm = String.valueOf(mapIns.get("PVSN_RESRCE_NM")); // 제공자원명
					if (sPvsnResrceNm == null || sPvsnResrceNm.equals("null") || sPvsnResrceNm.equals("")) {
						throw new AppWorksException("제공자원명은 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sSrvcTypeSeCd = String.valueOf(mapIns.get("SRVC_TYPE_SE_CD")); // 서비스유형구분코드
					if (sSrvcTypeSeCd == null || sSrvcTypeSeCd.equals("null") || sSrvcTypeSeCd.equals("")) {
						throw new AppWorksException("서비스유형구분코드는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sRsfrInstNo = String.valueOf(mapIns.get("RSFR_INST_NO")); // 자원제공주체번호
					if (sRsfrInstNo == null || sRsfrInstNo.equals("null") || sRsfrInstNo.equals("")) {
						throw new AppWorksException("자원제공주체는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sOtmYn = String.valueOf(mapIns.get("OTM_YN")); // 일회성여부
					if (sOtmYn == null || sOtmYn.equals("null") || sOtmYn.equals("")) {
						throw new AppWorksException("일회성여부는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sPvsnBgngYmd = String.valueOf(mapIns.get("PVSN_BGNG_YMD")); // 제공시작일자
					if (sPvsnBgngYmd == null || sPvsnBgngYmd.equals("null") || sPvsnBgngYmd.equals("")) {
						throw new AppWorksException("제공시작일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sPvsnEndYmd = String.valueOf(mapIns.get("PVSN_END_YMD")); // 제공종료일자
					if (sPvsnEndYmd == null || sPvsnEndYmd.equals("null") || sPvsnEndYmd.equals("")) {
						throw new AppWorksException("제공종료일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sUseYn = String.valueOf(mapIns.get("USE_YN")); // 사용여부
					if (sUseYn == null || sUseYn.equals("null") || sUseYn.equals("")) {
						throw new AppWorksException("사용여부는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					// 필수항목 및 처리항목 체크
					mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
					mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

					// 자원번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();
					seqMap.put("USER_ID", sUserId);
					seqMap.put("RENU_NO_SE_CD", "RS"); // 자원번호 채번코드
					seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자
					// seqMap.put("RENU_YMD", "99999999"); // 현재일자

					// 채번서비스 호출
					valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 자원번호 발번
					LOGGER.debug("채번: " + valMap.get("RENU_NO").toString());
					// valMap.get("RENU_NO")
					mapIns.put("RESRCE_NO", sWprkSqn);

					// 자원 상세등록 호출
					srvcResrceMapper.insertResrceDetail(mapIns);
					// 자원 이력등록 호출
					srvcResrceMapper.insertResrceHistory(mapIns);

					/* 2023-01-23 3팀요청 자원이력확인시 첨부파일 확인위해 첨부파일번호 check */
					Map<String, Object> infoFileMap = new HashMap<>();
					List<Map<String, Object>> dtlFileList = new ArrayList<>();

					final String ATCMFL_CL_NM = "자원제공서비스";
					String sAtfino = String.valueOf(mapIns.get("ATFINO")); /* 첨부파일번호 */

					/* 첨부파일번호 */
					if (!"".equals(sAtfino) && !"null".equals(sAtfino)) {
						Map<String, String> paramMap = new HashMap<>();

						/* 첨부파일번호, 첨부파일분류명 */
						paramMap.put("ATFINO", sAtfino);
						paramMap.put("ATCMFL_CL_NM", ATCMFL_CL_NM);
						paramMap.put("HSTR_REG_YN", "N");

						int iNewAtfino = 0;
						int iaddCnt = 0;

						/* 첨부파일정보 조회 */
						infoFileMap = srvcResrceMapper.selectFileInfo(paramMap);
						/* 첨부파일상세목록 조회 */
						dtlFileList = mgmtFileService.selectCmnAllFileList(paramMap);
						/* 신규채번 */
						LOGGER.debug("***** 기존 첨부파일번호 확인=[" + sAtfino + "]");
						iNewAtfino = mgmtFileMapper.selectAttcFileNo();

						LOGGER.debug("***** 신규 첨부파일번호 채번=[" + iNewAtfino + "]");
						mapIns.put("ATFINO", String.valueOf(iNewAtfino));
						srvcResrceMapper.updateResrceHistoryFileNo(mapIns);

						List<Map<String, String>> dtlIrtList = new ArrayList<>();
						Map<String, String> infoIrtMap = new HashMap<>();
						for (String keys : infoFileMap.keySet()) {
							infoIrtMap.put(keys, String.valueOf(infoFileMap.get("keys")));
						}
						/* 첨부파일번호 신규채번후 후 저장(이력첨부파일관리용) */
						infoIrtMap.put("ATFINO", String.valueOf(iNewAtfino));
						infoIrtMap.put("USER_ID", sUserId);
						/* 첨부파일insert SAB820 */
						mgmtFileMapper.insertCmnFile3(infoIrtMap);

						if (dtlFileList.size() > 0) {

							for (int idx = 0; idx < dtlFileList.size(); idx++) {

								/* 기존첨부파일번호 이력등록여부 update ('Y') */
								String sMngSn = String.valueOf(dtlFileList.get(idx).get("MNG_SN"));
								paramMap.put("MNG_SN", sMngSn);
								srvcResrceMapper.updateFileHstrRegYn(paramMap);
								LOGGER.debug("***** 기존 첨부파일번호=[" + dtlFileList.get(idx).get("ATFINO") + "]");
								LOGGER.debug("***** 기존     일련번호=[" + dtlFileList.get(idx).get("MNG_SN") + "]");
								LOGGER.debug("***** 기존 이력등록여부=[" + dtlFileList.get(idx).get("HSTR_REG_YN") + "]");

								/* 이력등록여부 */
								dtlFileList.get(idx).put("ATFINO", iNewAtfino); /* 첨부파일번호 신규부여 */
								dtlFileList.get(idx).put("HSTR_REG_YN", "Y"); /* 첨부파일번호 이력등록여부 */
								LOGGER.debug("***** 신규 첨부파일번호=[" + dtlFileList.get(idx).get("ATFINO") + "]");
								LOGGER.debug("***** 신규 이력등록여부=[" + dtlFileList.get(idx).get("HSTR_REG_YN") + "]");
							}

							/* 첨부파일상세 insert SAB821 */
							for (int idx = 0; idx < dtlFileList.size(); idx++) {
								Map<String, String> getValMap = new HashMap<>();

								for (String keys : dtlFileList.get(idx).keySet()) {
									getValMap.put(keys, String.valueOf(dtlFileList.get(idx).get(keys)));
								}
								getValMap.put("USER_ID", sUserId);
								mgmtFileMapper.insertCmnFile2(getValMap);
								iaddCnt++;
							}
						}

						LOGGER.debug("***** 첨부파일상세 등록건수 ****** " + iaddCnt + "");
					}

					// 자원번호 key값 셋팅
					retMap.put("RESRCE_NO", sWprkSqn);
				}

				// 수정 이벤트
				while (updatedRows.hasNext()) {

					Map<String, String> mapUpd = updatedRows.next().toMap();

					// 세션 사용자ID 셋팅
					mapUpd.put("USER_ID", sUserId);
					mapUpd.put("SESS_USER_ID", sUserId);
					// 데이터변경구분코드 셋팅
					mapUpd.put("DATAA_CHG_SE_CD", "U");

					sWprkSqn = String.valueOf(mapUpd.get("RESRCE_NO")); // 자원번호

					// 자원 상세수정 호출
					srvcResrceMapper.updateResrceDetail(mapUpd);
					// 자원 이력등록 호출
					srvcResrceMapper.insertResrceHistory(mapUpd);

					/* 2023-01-23 3팀요청 자원이력확인시 첨부파일 확인위해 첨부파일번호 check */
					Map<String, Object> infoFileMap = new HashMap<>();
					List<Map<String, Object>> dtlFileList = new ArrayList<>();

					final String ATCMFL_CL_NM = "자원제공서비스";
					String sAtfino = String.valueOf(mapUpd.get("ATFINO")); /* 첨부파일번호 */

					/* 첨부파일번호 */
					if (!"".equals(sAtfino) && !"null".equals(sAtfino)) {
						Map<String, String> paramMap = new HashMap<>();

						/* 첨부파일번호, 첨부파일분류명 */
						paramMap.put("ATFINO", sAtfino);
						paramMap.put("ATCMFL_CL_NM", ATCMFL_CL_NM);
						paramMap.put("HSTR_REG_YN", "N");

						int iNewAtfino = 0;
						int iaddCnt = 0;

						/* 첨부파일정보 조회 */
						infoFileMap = srvcResrceMapper.selectFileInfo(paramMap);
						/* 첨부파일상세목록 조회 */
						dtlFileList = mgmtFileService.selectCmnAllFileList(paramMap);
						/* 신규채번 */
						LOGGER.debug("***** 기존 첨부파일번호 확인=[" + sAtfino + "]");
						iNewAtfino = mgmtFileMapper.selectAttcFileNo();

						LOGGER.debug("***** 신규 첨부파일번호 채번=[" + iNewAtfino + "]");
						mapUpd.put("ATFINO", String.valueOf(iNewAtfino));
						srvcResrceMapper.updateResrceHistoryFileNo(mapUpd);

						List<Map<String, String>> dtlIrtList = new ArrayList<>();
						Map<String, String> infoIrtMap = new HashMap<>();
						for (String keys : infoFileMap.keySet()) {
							infoIrtMap.put(keys, String.valueOf(infoFileMap.get("keys")));
						}
						/* 첨부파일번호 신규채번후 후 저장(이력첨부파일관리용) */
						infoIrtMap.put("ATFINO", String.valueOf(iNewAtfino));
						infoIrtMap.put("USER_ID", sUserId);
						/* 첨부파일insert SAB820 */
						mgmtFileMapper.insertCmnFile3(infoIrtMap);

						if (dtlFileList.size() > 0) {

							for (int idx = 0; idx < dtlFileList.size(); idx++) {

								/* 기존첨부파일번호 이력등록여부 update ('Y') */
								String sMngSn = String.valueOf(dtlFileList.get(idx).get("MNG_SN"));
								paramMap.put("MNG_SN", sMngSn);
								srvcResrceMapper.updateFileHstrRegYn(paramMap);
								LOGGER.debug("***** 기존 첨부파일번호=[" + dtlFileList.get(idx).get("ATFINO") + "]");
								LOGGER.debug("***** 기존     일련번호=[" + dtlFileList.get(idx).get("MNG_SN") + "]");
								LOGGER.debug("***** 기존 이력등록여부=[" + dtlFileList.get(idx).get("HSTR_REG_YN") + "]");

								/* 이력등록여부 */
								dtlFileList.get(idx).put("ATFINO", iNewAtfino); /* 첨부파일번호 신규부여 */
								dtlFileList.get(idx).put("HSTR_REG_YN", "Y"); /* 첨부파일번호 이력등록여부 */
								LOGGER.debug("***** 신규 첨부파일번호=[" + dtlFileList.get(idx).get("ATFINO") + "]");
								LOGGER.debug("***** 신규 이력등록여부=[" + dtlFileList.get(idx).get("HSTR_REG_YN") + "]");
							}

							/* 첨부파일상세 insert SAB821 */
							for (int idx = 0; idx < dtlFileList.size(); idx++) {
								Map<String, String> getValMap = new HashMap<>();

								for (String keys : dtlFileList.get(idx).keySet()) {
									getValMap.put(keys, String.valueOf(dtlFileList.get(idx).get(keys)));
								}
								getValMap.put("USER_ID", sUserId);
								mgmtFileMapper.insertCmnFile2(getValMap);
								iaddCnt++;
							}
						}

						LOGGER.debug("***** 첨부파일상세 등록건수 ****** " + iaddCnt + "");
					}

					// 자원번호 key값 셋팅
					retMap.put("RESRCE_NO", mapUpd.get("RESRCE_NO"));
				}

				// 삭제 이벤트
				while (deletedRows.hasNext()) {

					Map<String, String> mapDel = deletedRows.next().toMap();
					sWprkSqn = String.valueOf(mapDel.get("RESRCE_NO")); // 자원번호
					// 삭제여부 셋팅
					mapDel.put("DEL_YN", "Y");
					mapDel.put("USER_ID", sUserId);
					mapDel.put("SESS_USER_ID", sUserId);
					mapDel.put("DATAA_CHG_SE_CD", "D");

					// 자원 상세삭제 호출
					srvcResrceMapper.deleteResrceDetail(mapDel);
					// 자원 이력등록 호출
					srvcResrceMapper.insertResrceHistory(mapDel);
					// 자원번호 key값 셋팅
					retMap.put("RESRCE_NO", mapDel.get("RESRCE_NO"));
				}
			}

			/* ---------------- */
			/* 자원 담당자 처리 */
			/* ---------------- */
			// 담당자 DataSet
			ParameterGroup paramDsPic = dataRequest.getParameterGroup("dsPicList");

			if (paramDsPic != null) {
				Iterator<ParameterRow> insertedRows = paramDsPic.getInsertedRows();
				Iterator<ParameterRow> updatedRows = paramDsPic.getUpdatedRows();
				Iterator<ParameterRow> deletedRows = paramDsPic.getDeletedRows();

				// 등록 이벤트
				while (insertedRows.hasNext()) {

					Map<String, String> mapIns = insertedRows.next().toMap();
					String sPicNo = String.valueOf(mapIns.get("PIC_NO")); // 담당자번호
					if (sPicNo == null || sPicNo.equals("null") || sPicNo.equals("")) {
						throw new AppWorksException("담당자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sPchprsYn = String.valueOf(mapIns.get("PCHPRS_YN")); // 주담당자여부
					if (sPchprsYn == null || sPchprsYn.equals("null") || sPchprsYn.equals("")) {
						throw new AppWorksException("주담당자여부는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					String sPicDsgnYmd = String.valueOf(mapIns.get("PIC_DSGN_YMD")); // 담당자지정일자
					if (sPicDsgnYmd == null || sPicDsgnYmd.equals("null") || sPicDsgnYmd.equals("")) {
						throw new AppWorksException("담당자지정일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}

					// 필수항목 및 처리항목 체크
					mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
					mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

					if (mapIns.get("RESRCE_NO").isEmpty())
						mapIns.put("RESRCE_NO", sWprkSqn);

					// 자원 담당자 등록호출
					srvcResrceMapper.insertResrcePic(mapIns);
					// 자원 담당자 이력등록호출
					srvcResrceMapper.insertResrcePicHistory(mapIns);
				}

				// 수정 이벤트
				while (updatedRows.hasNext()) {

					Map<String, String> mapUpd = updatedRows.next().toMap();
					// 세션 사용자ID 셋팅
					mapUpd.put("SESS_USER_ID", sUserId);
					// 데이터변경구분코드 셋팅
					mapUpd.put("DATAA_CHG_SE_CD", "U");

					// 자원 담당자 수정호출
					srvcResrceMapper.updateResrcePic(mapUpd);
					// 자원 담당자 이력등록호출
					srvcResrceMapper.insertResrcePicHistory(mapUpd);
				}

				// 삭제 이벤트
				while (deletedRows.hasNext()) {

					Map<String, String> mapDel = deletedRows.next().toMap();
					// 삭제여부 셋팅
					mapDel.put("DEL_YN", "Y");
					mapDel.put("SESS_USER_ID", sUserId);
					mapDel.put("DATAA_CHG_SE_CD", "D");

					// 자원 담당자 삭제호출
					srvcResrceMapper.deleteResrcePic(mapDel);
					// 자원 담당자 이력등록호출
					srvcResrceMapper.insertResrcePicHistory(mapDel);
				}
			}

			/* ------------------ */
			/* 자원 프로그램 처리 */
			/* ------------------ */
			// 프로그램 DataSet
			ParameterGroup paramDsProgrm = dataRequest.getParameterGroup("dsProgrmList");
			LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.paramDsProgrm=[" + paramDsProgrm + "]");

			if (paramDsProgrm != null) {
				Iterator<ParameterRow> insertedRows = paramDsProgrm.getInsertedRows();
				Iterator<ParameterRow> updatedRows = paramDsProgrm.getUpdatedRows();
				Iterator<ParameterRow> deletedRows = paramDsProgrm.getDeletedRows();

				// 등록 이벤트
				while (insertedRows.hasNext()) {

					Map<String, String> mapIns = insertedRows.next().toMap();
					// 필수항목 및 처리항목 체크
					mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
					mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

					// 프로그램번호 채번
					Map<String, String> seqMap = new HashMap<>();
					Map<String, Object> valMap = new HashMap<>();

					seqMap.put("USER_ID", sUserId);
					seqMap.put("RENU_NO_SE_CD", "PG"); // 프로그램번호 채번코드
					seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자

					// 채번서비스 호출
					valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
					sWprkSqn1 = String.valueOf(valMap.get("RENU_NO")); // 프로그램번호 발번
					LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.sWprkSqn1=[" + sWprkSqn1 + "]");

					if (mapIns.get("RESRCE_NO").isEmpty())
						mapIns.put("RESRCE_NO", sWprkSqn);
					mapIns.put("PROGRM_NO", sWprkSqn1);

					// 자원 프로그램 등록호출
					srvcResrceMapper.insertResrceProgrm(mapIns);
					// 자원 프로그램 이력등록호출
					srvcResrceMapper.insertResrceProgrmHistory(mapIns);

				}

				// 수정 이벤트
				while (updatedRows.hasNext()) {

					Map<String, String> mapUpd = updatedRows.next().toMap();
					sWprkSqn1 = String.valueOf(mapUpd.get("PROGRM_NO")); // 프로그램번호
					// 세션 사용자ID 셋팅
					mapUpd.put("SESS_USER_ID", sUserId);
					// 데이터변경구분코드 셋팅
					mapUpd.put("DATAA_CHG_SE_CD", "U");

					// 자원 프로그램 수정호출
					srvcResrceMapper.updateResrceProgrm(mapUpd);
					// 자원 프로그램 이력등록호출
					srvcResrceMapper.insertResrceProgrmHistory(mapUpd);
				}

				// 삭제 이벤트
				while (deletedRows.hasNext()) {

					Map<String, String> mapDel = deletedRows.next().toMap();
					sWprkSqn1 = String.valueOf(mapDel.get("PROGRM_NO")); // 프로그램번호
					// 삭제여부 셋팅
					mapDel.put("DEL_YN", "Y");
					mapDel.put("SESS_USER_ID", sUserId);
					mapDel.put("DATAA_CHG_SE_CD", "D");

					// 자원 프로그램 삭제호출
					srvcResrceMapper.deleteResrceProgrm(mapDel);
					// 자원 프로그램 이력등록호출
					srvcResrceMapper.insertResrceProgrmHistory(mapDel);
				}

			}

			/* -------------------------- */
			/* 자원 프로그램상세일정 처리 */
			/* -------------------------- */
			ParameterGroup paramDsSchdl = dataRequest.getParameterGroup("dsSchdlList");
			LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.paramDsSchdl=[" + paramDsSchdl + "]");

			if (paramDsSchdl != null) {
				Iterator<ParameterRow> insertedRows = paramDsSchdl.getInsertedRows();
				Iterator<ParameterRow> updatedRows = paramDsSchdl.getUpdatedRows();
				Iterator<ParameterRow> deletedRows = paramDsSchdl.getDeletedRows();

				// 등록 이벤트
				while (insertedRows.hasNext()) {

					Map<String, String> mapIns = insertedRows.next().toMap();
					// 필수항목 및 처리항목 체크
					mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
					mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

					if (mapIns.get("RESRCE_NO").isEmpty())
						mapIns.put("RESRCE_NO", sWprkSqn);
					if (mapIns.get("PROGRM_NO").isEmpty())
						mapIns.put("PROGRM_NO", sWprkSqn1);

					int iLctreSn = srvcResrceMapper.selectResrceProgrmLctreSn(mapIns);
					mapIns.put("LCTRE_SN", String.valueOf(iLctreSn));
					sWprkSqn2 = String.valueOf(iLctreSn);

					// 자원 프로그램상세일정 등록호출
					srvcResrceMapper.insertResrceProgrmSchdl(mapIns);
					// 자원 프로그램상세일정 이력등록호출
					srvcResrceMapper.insertResrceProgrmSchdlHistory(mapIns);
				}

				// 수정 이벤트
				while (updatedRows.hasNext()) {

					Map<String, String> mapUpd = updatedRows.next().toMap();
					// 세션 사용자ID 셋팅
					mapUpd.put("SESS_USER_ID", sUserId);
					// 데이터변경구분코드 셋팅
					mapUpd.put("DATAA_CHG_SE_CD", "U");

					// 자원 프로그램상세일정 수정호출
					srvcResrceMapper.updateResrceProgrmSchdl(mapUpd);
					// 자원 프로그램상세일정 이력등록호출
					srvcResrceMapper.insertResrceProgrmSchdlHistory(mapUpd);
				}

				// 삭제 이벤트
				while (deletedRows.hasNext()) {

					Map<String, String> mapDel = deletedRows.next().toMap();
					// 삭제여부 셋팅
					mapDel.put("DEL_YN", "Y");
					mapDel.put("SESS_USER_ID", sUserId);
					mapDel.put("DATAA_CHG_SE_CD", "D");

					// 자원 프로그램상세일정 삭제호출
					srvcResrceMapper.deleteResrceProgrmSchdl(mapDel);
					// 자원 프로그램상세일정 이력등록호출
					srvcResrceMapper.insertResrceProgrmSchdlHistory(mapDel);
				}
			}

			/* ---------------------- */
			/* 자원 프로그램강사 처리 */
			/* ---------------------- */
			ParameterGroup paramDsInstr = dataRequest.getParameterGroup("dsInstrList");
			LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.paramDsInstr=[" + paramDsInstr + "]");

			if (paramDsInstr != null) {

				Iterator<ParameterRow> insertedRows = paramDsInstr.getInsertedRows();
				Iterator<ParameterRow> updatedRows = paramDsInstr.getUpdatedRows();
				Iterator<ParameterRow> deletedRows = paramDsInstr.getDeletedRows();

				// 등록 이벤트
				while (insertedRows.hasNext()) {

					Map<String, String> mapIns = insertedRows.next().toMap();
					// 필수항목 및 처리항목 체크
					mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅

					if (mapIns.get("RESRCE_NO").isEmpty())
						mapIns.put("RESRCE_NO", sWprkSqn);
					if (mapIns.get("PROGRM_NO").isEmpty())
						mapIns.put("PROGRM_NO", sWprkSqn1);
					if (mapIns.get("LCTRE_SN").isEmpty())
						mapIns.put("LCTRE_SN", sWprkSqn2);

					// 자원 프로그램상세일정 등록호출
					srvcResrceMapper.insertResrceProgrmInstr(mapIns);

					// 초기집단상담번호(EG) key값 셋팅
					retMap.put("RESRCE_NO", mapIns.get("RESRCE_NO"));
				}

				// 수정 이벤트
				while (updatedRows.hasNext()) {

					Map<String, String> mapUpd = updatedRows.next().toMap();
					// 세션 사용자ID 셋팅
					// mapUpd.put("USER_ID", sUserId);
					mapUpd.put("SESS_USER_ID", sUserId); // 2022.08.18최두일

					// 자원 프로그램상세일정 수정호출
					srvcResrceMapper.updateResrceProgrmInstr(mapUpd);

					// 초기집단상담번호(EG) key값 셋팅
					retMap.put("RESRCE_NO", mapUpd.get("RESRCE_NO"));
				}

				// 삭제 이벤트
				while (deletedRows.hasNext()) {

					Map<String, String> mapDel = deletedRows.next().toMap();
					// 삭제여부 셋팅
					mapDel.put("DEL_YN", "Y");
					mapDel.put("SESS_USER_ID", sUserId);

					// 자원 프로그램상세일정 삭제호출
					srvcResrceMapper.deleteResrceProgrmInstr(mapDel);
				}

			}

			/* ---------------- */
			/* 입교일자 처리 */
			/* ---------------- */
			ParameterGroup paramDsEntsc = dataRequest.getParameterGroup("dsEntscList");

			if (paramDsEntsc != null) {
				Iterator<ParameterRow> insertedRows = paramDsEntsc.getInsertedRows();
				Iterator<ParameterRow> updatedRows = paramDsEntsc.getUpdatedRows();
				// Iterator<ParameterRow> deletedRows = paramDsEntsc.getDeletedRows();

				// 등록 이벤트
				while (insertedRows.hasNext()) {
					Map<String, String> mapIns = insertedRows.next().toMap();

					String sEntscbgng = String.valueOf(mapIns.get("ENTSC_APLY_BGNG_YMD")); // 입교신청시작일자
					if (sEntscbgng == null || sEntscbgng.equals("null") || sEntscbgng.equals("")) {
						throw new AppWorksException("입교신청일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}
					String sEntscend = String.valueOf(mapIns.get("ENTSC_APLY_END_YMD")); // 입교신청종료일자
					if (sEntscend == null || sEntscend.equals("null") || sEntscend.equals("")) {
						throw new AppWorksException("입교신청일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}
					String sEntscpenu = String.valueOf(mapIns.get("ENTSC_PENU_NO")); // 입교기수
					if (sEntscpenu == null || sEntscpenu.equals("null") || sEntscpenu.equals("")) {
						throw new AppWorksException("입교기수는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
					}
					// 필수항목 및 처리항목 체크
					mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅

					if (mapIns.get("RESRCE_NO").isEmpty())
						mapIns.put("RESRCE_NO", sWprkSqn);

					// 입교일정 등록호출
					srvcResrceMapper.insertEntsc(mapIns);

				}

				// 수정 이벤트
				while (updatedRows.hasNext()) {

					Map<String, String> mapUpd = updatedRows.next().toMap();
					// 세션 사용자ID 셋팅
					mapUpd.put("SESS_USER_ID", sUserId);

					// 자원 담당자 수정호출
					srvcResrceMapper.updateEntsc(mapUpd);
				}
			}
		} else {
			// 일괄 등록 로직 처리.
			retMap = autoRsfrInstInsert(request, dataRequest);
		}
		return retMap;
	}

	/**
	 * @Method : processDscsnOutrcDetail
	 * @Method설명 : 자원 승인(반려)처리
	 * @param : request
	 * @param : dataRequest
	 * @return : Map
	 * @exception : Exception
	 * @작성자 : Kwon.Min.Seo
	 * @작성일 : 2022. 06. 24.
	 */
	@Override
	public void processAprvPrcs(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();

		int iCnt = 0; // 건수
		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		/* ------------------ */
		/* 자원 승인 처리 */
		/* ------------------ */
		// 승인처리 DataSet
		ParameterGroup paramDsAprvPrcs = dataRequest.getParameterGroup("dsAprvPrcs");
		LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.paramDsAprvPrcs=[" + paramDsAprvPrcs + "]");

		if (paramDsAprvPrcs != null) {

			List<Map<String, String>> prcDsAprvPrcs = paramDsAprvPrcs.getAllRowList();
			iCnt = 0;

			for (Map<String, String> rowMap : prcDsAprvPrcs) {
				LOGGER.debug(
						"SrvcResrceServiceImpl.processResrceDetail.paramDsProgrm.iCnt=[" + iCnt + "," + rowMap + "]");

				// 필수항목 및 처리항목 체크
				rowMap.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
				rowMap.put("DATAA_CHG_SE_CD", "U"); // 데이터변경구분코드 셋팅

				// 자원 프로그램 수정호출
				srvcResrceMapper.updateAprvPrcs(rowMap);
				// 자원 프로그램 이력등록호출
				srvcResrceMapper.insertResrceHistory(rowMap);

			}
		}
	}

	/**
	 * @Method : selectBizYrCombo
	 * @Method설명 : 사업연도 콤보 데이터 조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 07. 28.
	 */
	@Override
	public List<Map<String, Object>> selectBizYrCombo(HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		String untTaskwk = userVo.getUntTaskwk();
		if (untTaskwk == null || "".equals(untTaskwk) || "null".equals(untTaskwk)) {
			untTaskwk = userVo.getUntTaskwkSeCd();
		}

		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("UNT_TASKWK_SE_CD", untTaskwk);

		String[] agencyContacts = userVo.getAgencyContacts().split("/");
		if (agencyContacts.length > 3) {
			if (!"4".contentEquals(agencyContacts[3])) {
				userInfoMap.put("INST_NO", Integer.toString(userVo.getInstNo()));
			}
		}

		return srvcResrceMapper.selectBizYrCombo(userInfoMap);
	}

	/**
	 * @Method : selectResrceNmCombo
	 * @Method설명 : 교육과정 콤보 데이터 조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 07. 25.
	 */
	@Override
	public List<Map<String, Object>> selectResrceNmCombo(HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();

		String instNo = null;
		String[] agencyContacts = userVo.getAgencyContacts().split("/");
		if (agencyContacts.length != 0 && agencyContacts.length > 2) {
			instNo = userVo.getAgencyContacts().split("/")[2];
		}
		LOGGER.debug("agencyContacts → " + userVo.getAgencyContacts());
		LOGGER.debug("length → " + userVo.getAgencyContacts().split("/").length);

		userInfoMap.put("INST_NO", instNo);
		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		userInfoMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());

		return srvcResrceMapper.selectResrceNmCombo(userInfoMap);
	}

	/**
	 * @Method : selectInstNmCombo
	 * @Method설명 : 교육기관 콤보 데이터 조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 07. 25.
	 */
	@Override
	public List<Map<String, Object>> selectInstNmCombo(HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();

		String instNo = null;
		String[] agencyContacts = userVo.getAgencyContacts().split("/");
		if (agencyContacts.length != 0 && agencyContacts.length > 2) {
			instNo = userVo.getAgencyContacts().split("/")[2];
		}

		userInfoMap.put("INST_NO", instNo);

		return srvcResrceMapper.selectInstNmCombo(userInfoMap);
	}

	/**
	 * @Method : selectInstNmCombo1
	 * @Method설명 : 교육기관 콤보 데이터 조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 07. 25.
	 */
	@Override
	public List<Map<String, Object>> selectInstNmCombo1(HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();

		String instNo = null;
		String[] agencyContacts = userVo.getAgencyContacts().split("/");
		if (agencyContacts.length != 0 && agencyContacts.length > 2) {
			instNo = userVo.getAgencyContacts().split("/")[2];
		}

		userInfoMap.put("INST_NO", instNo);

		return srvcResrceMapper.selectInstNmCombo1(userInfoMap);
	}

	/**
	 * @Method : selectInstNmCombo2
	 * @Method설명 : 교육기관 콤보 데이터 조회2
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 11. 04.
	 */
	@Override
	public List<Map<String, Object>> selectInstNmCombo2(HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();

		String instNo = null;
		String[] agencyContacts = userVo.getAgencyContacts().split("/");
		if (agencyContacts.length != 0 && agencyContacts.length > 2) {
			instNo = userVo.getAgencyContacts().split("/")[2];
		}

		userInfoMap.put("INST_NO", instNo);
		userInfoMap.put("untTaskwk", userVo.getUntTaskwk()); // 현재 선택된 단위 시스템 코드

		return srvcResrceMapper.selectInstNmCombo2(userInfoMap);
	}

	/**
	 * @Method : selectInstNmCombo3
	 * @Method설명 : 교육기관 콤보 데이터 조회3
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 11. 04.
	 */
	@Override
	public List<Map<String, Object>> selectInstNmCombo3(HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();

		String instNo = null;
		String[] agencyContacts = userVo.getAgencyContacts().split("/");
		if (agencyContacts.length != 0 && agencyContacts.length > 2) {
			instNo = userVo.getAgencyContacts().split("/")[2];
		}

		userInfoMap.put("INST_NO", instNo);
		userInfoMap.put("untTaskwkSeCd", userVo.getUntTaskwkSeCd()); // 단위 업무 코드

		return srvcResrceMapper.selectInstNmCombo3(userInfoMap);
	}

	/**
	 * @Method : selectResrceChgHstrList
	 * @Method설명 : 교육시간표 상세 목록 조회
	 * @param : dataRequest
	 * @return : ListMap
	 * @exception : Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 07. 25.
	 */
	@Override
	public List<Map<String, Object>> selectEduSchdlDtlList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcResrceMapper.selectEduSchdlDtlList(paramMap);
	}

	/**
	 * @Method명 : selectEduHrDt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Choi.Doo.Il
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 교육시간표 상세 일괄등록 조회
	 */
	public List<Map<String, String>> selectEduHrDt(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = searchParam.getSingleValueMap();

		return srvcResrceMapper.selectEduHrDt(paramMap);
	}

	/**
	 * @Method명 : processEduHrDtlRegExcelUpload
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 교육시간표상세 일괄등록 엑셀업로드
	 */
	@Override
	public List<Map<String, String>> processEduHrDtlRegExcelUpload(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//		// TODO 단위업무구분코드_추후 데이터가 생성 시 로직 넣어야함
////		sCmmnsCd 와 dsEduHrDtlRegExcelUploadList.get(i).get("EDU_PROGRM_SE_CD")); // 교육프로그램구분코드 같아야함
//		String sCmmnsCd = ""; // 공통코드값
//		List<Map<String, Object>> listEduProgrmSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", loginVO.getUntTaskwkSeCd());
//		if(listEduProgrmSeCd.size() == 0) {
//			listEduProgrmSeCd = mgmtCmmnCodeService.selectCommonCode("EDU_PROGRM_SE_CD");			
//		}
//		for(int i=0; i<listEduProgrmSeCd.size(); i++) {			
//			sCmmnsCd = (String) listEduProgrmSeCd.get(i).get("CMMNS_CD_VALUE");			
//		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);

		params = dataRequest.getParameterGroup("dsEduHrDtlRegExcelUpload");
		List<Map<String, String>> dsEduHrDtlRegExcelUploadList = params.getAllRowList();

		LOGGER.debug("dsEduHrDtlRegExcelUploadList ::::::::::: " + dsEduHrDtlRegExcelUploadList.size());

//		교육기관/교육과정 필수체크
		String sInstNo = dmOutcomeDetailMap.get("INST_NO"); // 교육기관_기관번호
		String sResrceNo = dmOutcomeDetailMap.get("RESRCE_NO"); // 교육과정_자원번호

		LOGGER.debug("교육기관 ::::::::::: " + sInstNo);
		LOGGER.debug("교육과정 ::::::::::: " + sResrceNo);

		if ((sResrceNo == null || sResrceNo.equals("") || sResrceNo.isEmpty())
				|| (sInstNo == null || sInstNo.equals("") || sInstNo.isEmpty())) {
			throw new AppWorksException("교육기관과 교육과정 모두 선택되어야 합니다.", Alert.ERROR);
		}

		// 엑셀업로드 전 SDA510/SDA511 데이터 삭제
		Map<String, String> map = new HashMap<String, String>();
		map.put("RESRCE_NO", sResrceNo);
		try {
			List<Map<String, Object>> paramMapList = srvcResrceMapper.selectPK(map);
			for (Map<String, Object> paramMap : paramMapList) {
				srvcResrceMapper.deleteInStrAllDel(paramMap); // SDA511
				srvcResrceMapper.deleteAllDel(paramMap); // SDA510
			}

		} catch (Exception e) {
			LOGGER.debug("교육시간표상세 일괄등록 전체삭제 ::::::::::: " + e.getMessage());
		}

		for (int i = 0; i < dsEduHrDtlRegExcelUploadList.size(); i++) {

			LOGGER.debug("프로그램일자 ::::::::::: " + dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_YMD"));
			LOGGER.debug("프로그램시작시간 111::::::::::: " + dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_BGNG_HR"));

			dmOutcomeDetailMap.put("JOB_YMD", DateUtil.getToday()); // 작업일자
			dmOutcomeDetailMap.put("OPRTR_ID", sUserId); // 작업자아이디
			dmOutcomeDetailMap.put("INST_NO", sInstNo); // 기관번호
			dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
			dmOutcomeDetailMap.put("PROGRM_YMD",
					dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_YMD").replace("-", "")); // 프로그램일자
			dmOutcomeDetailMap.put("PROGRM_NM", dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_NM")); // 프로그램명
			dmOutcomeDetailMap.put("EDU_PROGRM_SE_CD", dsEduHrDtlRegExcelUploadList.get(i).get("EDU_PROGRM_SE_CD")); // 교육프로그램구분코드
			dmOutcomeDetailMap.put("PROGRM_BGNG_HR",
					dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_BGNG_HR").replace(":", "")); // 프로그램시작시간
			dmOutcomeDetailMap.put("PROGRM_END_HR",
					dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_END_HR").replace(":", "")); // 프로그램종료시간
			dmOutcomeDetailMap.put("LCTRE_HR", dsEduHrDtlRegExcelUploadList.get(i).get("LCTRE_HR")); // 강의시간
			dmOutcomeDetailMap.put("PROGRM_DTL_CN", dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_DTL_CN")); // 프로그램상세내용

			LOGGER.debug("프로그램시작시간 222::::::::::: " + dsEduHrDtlRegExcelUploadList.get(i).get("PROGRM_BGNG_HR"));

			if ("Y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))
					|| "O".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))
					|| "y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))
					|| "o".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))) {
				try {
					srvcResrceMapper.insertEduHrDtlRegExcelUpload(dmOutcomeDetailMap); // SDA510
				} catch (Exception e) {
					LOGGER.debug("SDA510 ::::::::::: " + e.getMessage());
				}

			}
			dmOutcomeDetailMap.put("INSTR_ENFSN_NO", dsEduHrDtlRegExcelUploadList.get(i).get("INSTR_ENFSN_NO")); // 강사종사자번호

//			if("Y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN")) || "O".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN")) ||
//					"y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN")) || "o".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"))) {
//				dmOutcomeDetailMap.put("COMPNO_INSTR_YN",  "Y"); // 복수강사여부
//			}else {
//				dmOutcomeDetailMap.put("COMPNO_INSTR_YN",  dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN")); // 복수강사여부
//			}

			if ("Y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"))
					|| "O".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"))
					|| "y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"))
					|| "o".equals(dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"))) {
				LOGGER.debug("복수강사여부 111::::::::::: " + dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"));
				dmOutcomeDetailMap.put("COMPNO_INSTR_YN", "Y"); // 복수강사여부
			} else {
				LOGGER.debug("복수강사여부 222::::::::::: " + dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN"));
				LOGGER.debug("강사종사자번호 222::::::::::: " + dsEduHrDtlRegExcelUploadList.get(i).get("INSTR_ENFSN_NO"));
				dmOutcomeDetailMap.put("COMPNO_INSTR_YN", dsEduHrDtlRegExcelUploadList.get(i).get("COMPNO_INSTR_YN")); // 복수강사여부
			}

			if ("Y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))
					|| "O".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))
					|| "y".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))
					|| "o".equals(dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN"))) {
				dmOutcomeDetailMap.put("PCHPRS_YN", "Y"); // 주담당자여부
			} else {
				dmOutcomeDetailMap.put("PCHPRS_YN", dsEduHrDtlRegExcelUploadList.get(i).get("PCHPRS_YN")); // 주담당자여부
			}

			try {
				srvcResrceMapper.insertEduHrDtlRegInStrExcelUpload(dmOutcomeDetailMap); // SDA511
			} catch (Exception e) {
				LOGGER.debug("SDA511 ::::::::::: " + e.getMessage());
			}

		}

		return null;
	}

	/**
	 * @Method명 : processAllDel
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 교육시간표상세 일괄등록 전체삭제
	 */
	@Override
	public void processAllDel(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, String>> dsEduHrDtList = dataRequest.getParameterGroup("dsEduHrDtList").getAllRowList();
		Map<String, String> map = new HashMap<String, String>();
		map.put("RESRCE_NO", dsEduHrDtList.get(0).get("RESRCE_NO"));
		try {
			List<Map<String, Object>> paramMapList = srvcResrceMapper.selectPK(map);
			for (Map<String, Object> paramMap : paramMapList) {
				srvcResrceMapper.deleteInStrAllDel(paramMap); // SDA511
				srvcResrceMapper.deleteAllDel(paramMap); // SDA510
			}

		} catch (Exception e) {
			LOGGER.debug("교육시간표상세 일괄등록 전체삭제 ::::::::::: " + e.getMessage());
		}

		return;
	}

	/**
	 * @Method명 : processAplcn
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 7. 25.
	 * @Method설명 : 스케쥴 적용
	 */
	@Override
	public List<Map<String, String>> processAplcn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sProgrmNo = ""; // 프로그램번호
		String sUserId = ""; // 세션정보의 유저ID
		String sAprvSttsSeCd = ""; // 승인상태구분코드
		String sProgrmNo1 = ""; // 프로그램번호

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

		LOGGER.debug("sUserId ::::::::::: " + sUserId);

		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);

		params = dataRequest.getParameterGroup("dsEduHrDtlRegExcelUpload");
		List<Map<String, String>> dsEduHrDtlRegExcelUploadList = params.getAllRowList();

		LOGGER.debug("dsEduHrDtlRegExcelUploadList ::::::::::: " + dsEduHrDtlRegExcelUploadList.size());

//		교육기관/교육과정 필수체크
//		String sInstNo = dmOutcomeDetailMap.get("INST_NO");  		// 교육기관_기관번호
		String sResrceNo = dmOutcomeDetailMap.get("RESRCE_NO"); // 교육과정_자원번호

		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();
		Map<String, String> seqMap1 = new HashMap<>();
		Map<String, Object> valMap1 = new HashMap<>();
		Map<String, String> seqMap2 = new HashMap<>();

		List<Map<String, Object>> resultDtMap = new ArrayList<Map<String, Object>>();
		// 스케쥴적용 자원프로그램 조회
		dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
		resultDtMap = srvcResrceMapper.selectEduSchdlResourceList(dmOutcomeDetailMap);

		for (Map<String, Object> map : resultDtMap) {
//			 SDA100_자원기본에서 승인상태구분코드(APRV_STTS_SE_CD) 가져오기
			seqMap.put("RESRCE_NO", sResrceNo); // 자원번호

			// 프로그램번호 채번
			seqMap.put("RENU_NO_SE_CD", "PG");
			seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자
			seqMap.put("USER_ID", sUserId);
			valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
			sProgrmNo = String.valueOf(valMap.get("RENU_NO"));
			LOGGER.debug("프로그램번호 채번 ::::::::::: " + sProgrmNo);

			dmOutcomeDetailMap.put("PROGRM_NO", sProgrmNo); // 프로그램번호
			dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
			dmOutcomeDetailMap.put("PROGRM_TTL_NM", (String) map.get("PROGRM_NM")); // 프로그램제목명_교육과목명
			dmOutcomeDetailMap.put("EDU_PROGRM_SE_CD", (String) map.get("EDU_PROGRM_SE_CD")); // 교육프로그램구분코드
			dmOutcomeDetailMap.put("PROGRM_BGNG_YMD", (String) map.get("MIN_DT")); // 프로그램시작일자
			dmOutcomeDetailMap.put("PROGRM_END_YMD", (String) map.get("MAX_DT")); // 프로그램종료일자
			dmOutcomeDetailMap.put("PROGRM_SUM_HR", map.get("SUM_HR").toString()); // 프로그램합계시간
			dmOutcomeDetailMap.put("DEL_YN", "N"); // 삭제여부
			dmOutcomeDetailMap.put("SESS_USER_ID", sUserId);

			try {
				srvcResrceMapper.insertResrceProgrm(dmOutcomeDetailMap); // SDA300_자원프로그램
			} catch (Exception e) {
				LOGGER.debug("SDA300 ::::::::::: " + e.getMessage());
			}
			try {
				dmOutcomeDetailMap.put("DATAA_CHG_SE_CD", "i"); // 데이터변경구분코드
				srvcResrceMapper.insertResrceProgrmHistory(dmOutcomeDetailMap); // SDA301_자원프로그램이력
			} catch (Exception e) {
				LOGGER.debug("SDA301 ::::::::::: " + e.getMessage());
			}

		} // end for(Map<String, Object> map : resultDtMap) {

		List<Map<String, Object>> resultDtlDtMap = new ArrayList<Map<String, Object>>();
		// 스케쥴적용 자원프로그램상세 조회
		dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
		resultDtlDtMap = srvcResrceMapper.selectEduSchdlResourceDtlList(dmOutcomeDetailMap);

		for (Map<String, Object> map1 : resultDtlDtMap) {
			// 프로그램번호1 가져오기
			seqMap1.put("RESRCE_NO", sResrceNo); // 자원번호
			seqMap1.put("PROGRM_TTL_NM", (String) map1.get("PROGRM_NM")); // 프로그램제목명
			seqMap1.put("EDU_PROGRM_SE_CD", (String) map1.get("EDU_PROGRM_SE_CD")); // 교육프로그램구분코드
			valMap1 = srvcResrceMapper.selectProgrmNo(seqMap1);
			sProgrmNo1 = String.valueOf(valMap1.get("PROGRM_NO"));
			LOGGER.debug("프로그램번호 조회 111::::::::::: " + sProgrmNo1);

			dmOutcomeDetailMap.put("PROGRM_NO", sProgrmNo1); // 프로그램번호
			dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호

			// 강의일련번호 채번
			seqMap2.put("PROGRM_NO", sProgrmNo1); // 프로그램번호
			seqMap2.put("RESRCE_NO", sResrceNo); // 자원번호
			int iLctreSn = srvcResrceMapper.selectResrceProgrmLctreSn(seqMap2);
			LOGGER.debug("강의일련번호 채번::::::::::: " + iLctreSn);

			dmOutcomeDetailMap.put("LCTRE_SN", String.valueOf(iLctreSn)); // 강의일련번호
			dmOutcomeDetailMap.put("LCTRE_BGNG_YMD", (String) map1.get("MIN_DT")); // 강의시작일자
			dmOutcomeDetailMap.put("LCTRE_END_YMD", (String) map1.get("MAX_DT")); // 강의종료일자
			dmOutcomeDetailMap.put("PROGRM_BGNG_HR", (String) map1.get("PROGRM_BGNG_HR")); // 프로그램시작시간
			dmOutcomeDetailMap.put("PROGRM_END_HR", (String) map1.get("PROGRM_END_HR")); // 프로그램종료시간

			// 2022.11.17 이태호 수정_SDA510 insert 시 :없에지만 추가로 없앰
			dmOutcomeDetailMap.put("PROGRM_BGNG_HR", (String) map1.get("PROGRM_BGNG_HR").toString().replace(":", "")); // 프로그램시작시간
			dmOutcomeDetailMap.put("PROGRM_END_HR", (String) map1.get("PROGRM_END_HR").toString().replace(":", "")); // 프로그램종료시간

			dmOutcomeDetailMap.put("LCTRE_SUM_HR", map1.get("SUM_HR").toString()); // 강의합계시간
			dmOutcomeDetailMap.put("COMPNO_INSTR_YN", map1.get("COMPNO_INSTR_YN").toString()); // 복수강사여부
			dmOutcomeDetailMap.put("DEL_YN", "N"); // 삭제여부
			dmOutcomeDetailMap.put("SESS_USER_ID", sUserId);

			try {
				srvcResrceMapper.insertResrceProgrmSchdl(dmOutcomeDetailMap); // SDA340_자원프로그램상세일정
			} catch (Exception e) {
				LOGGER.debug("SDA340_ ::::::::::: " + e.getMessage());
			}
			try {
				dmOutcomeDetailMap.put("DATAA_CHG_SE_CD", "i"); // 데이터변경구분코드
				srvcResrceMapper.insertResrceProgrmSchdlHistory(dmOutcomeDetailMap); // SDA341_자원프로그램상세일정이력
			} catch (Exception e) {
				LOGGER.debug("SDA341_ ::::::::::: " + e.getMessage());
			}

		} // end for(Map<String, Object> map : resultDtlDtMap) {

		List<Map<String, Object>> resultIntrMap = new ArrayList<Map<String, Object>>();
		// 스케쥴적용 강사 조회
		dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
		resultIntrMap = srvcResrceMapper.selectIntrList(dmOutcomeDetailMap);

		for (Map<String, Object> map2 : resultIntrMap) {

			dmOutcomeDetailMap.put("PROGRM_NO", (String) map2.get("PROGRM_NO")); // 프로그램번호
			dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
			dmOutcomeDetailMap.put("LCTRE_SN", map2.get("LCTRE_SN").toString()); // 강의일련번호
			dmOutcomeDetailMap.put("INSTR_ENFSN_NO", (String) map2.get("INSTR_ENFSN_NO")); // 강의종사자번호
			dmOutcomeDetailMap.put("PCHPRS_YN", (String) map2.get("PCHPRS_YN")); // 주담당자여부
			dmOutcomeDetailMap.put("COMPNO_INSTR_YN", (String) map2.get("COMPNO_INSTR_YN")); // 복수강사여부
			dmOutcomeDetailMap.put("DEL_YN", "N"); // 삭제여부
			dmOutcomeDetailMap.put("SESS_USER_ID", sUserId);

			try {
				srvcResrceMapper.insertResrceProgrmInstr(dmOutcomeDetailMap); // SDA342_자원프로그램강사
			} catch (Exception e) {
				LOGGER.debug("SDA342_ ::::::::::: " + e.getMessage());
			}

		} // end for(Map<String, Object> map2 : resultIntrMap) {

		List<Map<String, Object>> resultExcvHrMap = new ArrayList<Map<String, Object>>();
		// 스케쥴적용 실행시간 조회
		dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
		resultExcvHrMap = srvcResrceMapper.selectEduSchdlExcvHrList(dmOutcomeDetailMap);

		for (Map<String, Object> map3 : resultExcvHrMap) {
			dmOutcomeDetailMap.put("PROGRM_NO", (String) map3.get("PROGRM_NO")); // 프로그램번호
			dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo); // 자원번호
			dmOutcomeDetailMap.put("LCTRE_SN", map3.get("LCTRE_SN").toString()); // 강의일련번호
			dmOutcomeDetailMap.put("LCTRE_YMD", (String) map3.get("PROGRM_YMD")); // 강의일자
			dmOutcomeDetailMap.put("LCTRE_HR", (String) map3.get("LCTRE_HR")); // 강의시간
			dmOutcomeDetailMap.put("PROGRM_DTL_CN", (String) map3.get("PROGRM_DTL_CN")); // 프로그램상세내용
			dmOutcomeDetailMap.put("DEL_YN", "N"); // 삭제여부
			dmOutcomeDetailMap.put("SESS_USER_ID", sUserId);

			try {
				srvcResrceMapper.insertExcvHr(dmOutcomeDetailMap); // SDA320_자원프로그램실행시간
			} catch (Exception e) {
				LOGGER.debug("SDA320 ::::::::::: " + e.getMessage());
			}

		} // end for(Map<String, Object> map3 : resultExcvHrMap) {

		// SDA510/SDA511 데이터 삭제
		Map<String, String> map = new HashMap<String, String>();
		map.put("RESRCE_NO", sResrceNo);
		try {
			List<Map<String, Object>> paramMapList = srvcResrceMapper.selectPK(map);
			for (Map<String, Object> paramMap : paramMapList) {
				srvcResrceMapper.deleteInStrAllDel(paramMap); // SDA511
				srvcResrceMapper.deleteAllDel(paramMap); // SDA510
			}
		} catch (Exception e) {
			LOGGER.debug("교육시간표상세 일괄등록 전체삭제 ::::::::::: " + e.getMessage());
		}

		return null;
	}

	/**
	 * @Method명 : selectInstr
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Choi.Doo.Il.
	 * @작성일 : 2022. 7. 28.
	 * @Method설명 : 강사조회
	 */
	public List<Map<String, String>> selectInstr(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return srvcResrceMapper.selectInstr(searchParamMap);
	}

	/**
	 * @Method명 : processAplcnDel
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 7. 28.
	 * @Method설명 : 스케쥴 적용 삭제
	 */
	@Override
	public List<Map<String, String>> processAplcnDel(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sUserId = ""; // 세션정보의 유저ID
		String sAprvSttsSeCd = ""; // 승인상태구분코드

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
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		Map<String, String> dmSearch = search.getSingleValueMap();
		dmOutcomeDetailMap.putAll(dmSearch);

		String sResrceNo = dmOutcomeDetailMap.get("RESRCE_NO"); // 교육과정_자원번호
		LOGGER.debug("교육과정_자원번호 ::::::::::: " + sResrceNo);

		dmOutcomeDetailMap.put("RESRCE_NO", sResrceNo);

		Map<String, String> seqMap = new HashMap<>();

		seqMap.put("RESRCE_NO", sResrceNo); // 자원번호

		try {
			srvcResrceMapper.updateAplcnResrceProgrm(dmOutcomeDetailMap); // SDA300 삭제
			srvcResrceMapper.updateAplcnResrceProgrmSchdl(dmOutcomeDetailMap); // SDA340 삭제
			srvcResrceMapper.updateAplcnResrceProgrmInstr(dmOutcomeDetailMap); // SDA342 삭제
			srvcResrceMapper.updateAplcnExcvHr(dmOutcomeDetailMap); // SDA320 삭제
			srvcResrceMapper.updateAplcnResrceProgrmHstr(dmOutcomeDetailMap); // SDA301 삭제
			srvcResrceMapper.updateAplcnResrceProgrmSchdlHstr(dmOutcomeDetailMap); // SDA341 삭제
		} catch (Exception e) {
			LOGGER.debug("update Exception ::::::::::: " + e.getMessage());
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> selectCommonCodeUnit(String codeId, String unitCode) throws Exception {
		if (codeId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("codeId", codeId);
		map.put("unitCode", unitCode);

		return srvcResrceMapper.selectCommonCodeUnit(map);
	}

	@Override
	public Map<String, Object> selectResrceNmChk(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> map = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmChkNm");

		if (param != null) {

			String nm = param.getValue("nm");

			if (nm != null && !"".equals(nm)) {
				int existCount = srvcResrceMapper.selectResrceNmChk(nm);

				if (existCount > 0) {
					map.put("nmExists", 1);
				} else {
					map.put("nmExists", 0);
				}
			}
		}

		return map;
	}

	/**
	 * @Method명 : selectRsfrMbyInstChk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2023. 4. 28.
	 * @Method설명 : 자원제공주체 확인
	 */
	@Override
	public Map<String, Object> selectRsfrMbyInstChk(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> map = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmChkNm");

		if (param != null) {

			String sInstNo = param.getValue("instNo");

			if (sInstNo != null && !"".equals(sInstNo)) {

				int iInstNo = Integer.parseInt(sInstNo);

				int existCount = srvcResrceMapper.selectRsfrMbyInstChk(iInstNo);

				if (existCount > 0) {
					map.put("nmExists", 1);
				} else {
					map.put("nmExists", 0);
				}
			}
		}

		return map;
	}

	/**
	 * @Method명 : selectEduCrseChk
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 11. 18.
	 * @Method설명 : 교육과정확인 조회
	 */
	@Override
	public Map<String, Object> selectEduCrseChk(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEduCrseChk");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcResrceMapper.selectEduCrseChk(paramMap);
	}

	/**
	 * @Method명 : selectEduCrseChk1
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 11. 18.
	 * @Method설명 : 교육과정확인 조회1
	 */
	@Override
	public Map<String, Object> selectEduCrseChk1(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEduCrseChk");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcResrceMapper.selectEduCrseChk1(paramMap);
	}

	/**
	 * @Method명 : selectEduCrseChk2
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Tae.Ho
	 * @작성일 : 2022. 11. 18.
	 * @Method설명 : 교육과정확인 조회2
	 */
	@Override
	public Map<String, Object> selectEduCrseChk2(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmEduCrseChk");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return srvcResrceMapper.selectEduCrseChk2(paramMap);
	}

	/**
	 * @Method명 : selectResrceHistory
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2023. 1. 16.
	 * @Method설명 : 자원 이력조회
	 */
	@Override
	public Map<String, Object> selectResrceHistory(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcResrceServiceImpl.selectResrceHistory.paramGroup=[" + paramGroup + "]");

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		// 자원 이력조회
		List<Map<String, Object>> dsDetail = new ArrayList<Map<String, Object>>();
		dsDetail = srvcResrceMapper.selectResrceChgHstrList(paramMap);

		// 자원 프로그램조회
		List<Map<String, Object>> dsProgrmList = new ArrayList<Map<String, Object>>();
		dsProgrmList = srvcResrceMapper.selectResrceProgrmHistoryList(paramMap);

		// 자원 프로그램상세일정
		List<Map<String, Object>> dsSchdlList = new ArrayList<Map<String, Object>>();
		dsSchdlList = srvcResrceMapper.selectResrceProgrmSchdlHistoryList(paramMap);

		if (dsSchdlList.size() > 0) {
			// 자원 프로그램강사
			paramMap.put("LCTRE_SN", String.valueOf(dsSchdlList.get(0).get("LCTRE_SN")));
			List<Map<String, Object>> dsInstrList = new ArrayList<Map<String, Object>>();
			dsInstrList = srvcResrceMapper.selectResrceProgrmInstrList(paramMap); /* 이력없음 */

			retMap.put("dsInstrList", dsInstrList);
		}

		// 자원 담당자조회
		List<Map<String, Object>> dsPicList = new ArrayList<Map<String, Object>>();
		dsPicList = srvcResrceMapper.selectResrcePicHistoryList(paramMap);

		// 자원 변경이력
//		List<Map<String, Object>> dsChgHstrList = new ArrayList<Map<String, Object>>();
//		dsChgHstrList = srvcResrceMapper.selectResrceChgHstrList(paramMap);

		// 입교일자 조회 0811
//		List<Map<String, Object>> dsEntscList = new ArrayList<Map<String, Object>>();
//		dsEntscList = srvcResrceMapper.selectEntsc(paramMap);	

		// 파일 이력
		List<Map<String, Object>> dsValue = new ArrayList<Map<String, Object>>();
		dsValue = srvcResrceMapper.selectFilesChgHstrList(dsDetail.get(0));

		retMap.put("dsDetail", dsDetail);
		retMap.put("dsProgrmList", dsProgrmList);
		retMap.put("dsSchdlList", dsSchdlList);
		retMap.put("dsPicList", dsPicList);
		retMap.put("dsValue", dsValue);
//		retMap.put("dsChgHstrList"	, dsChgHstrList);
//		retMap.put("dsEntscList"	, dsEntscList);

		return retMap;

	}

	/**
	 * @Method명 : selectInstNmCombo4
	 * @param request
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 2. 27.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectInstNmCombo4(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();

		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		userInfoMap.put("INST_NO", Integer.toString(userVo.getInstNo()));
		userInfoMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());

		return srvcResrceMapper.selectInstNmCombo4(userInfoMap);
	}

	void chkedValidation(Map<String, String> mapIns, String type) throws Exception {
		if ("paramDmDetail".equals(type)) {
			String sPvsnResrceNm = String.valueOf(mapIns.get("PVSN_RESRCE_NM")); // 제공자원명
			if (sPvsnResrceNm == null || sPvsnResrceNm.equals("null") || sPvsnResrceNm.equals("")) {
				throw new AppWorksException("제공자원명은 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}

			String sGrTrgtSrvcYn = String.valueOf(mapIns.get("GR_TRGT_SRVC_YN")); // 집단대상서비스여부
			if (sGrTrgtSrvcYn == null || sGrTrgtSrvcYn.equals("null") || sGrTrgtSrvcYn.equals("")) {
				throw new AppWorksException("집단대상서비스여부는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}

			String sSrvcTypeSeCd = String.valueOf(mapIns.get("SRVC_TYPE_SE_CD")); // 서비스유형구분코드
			if (sSrvcTypeSeCd == null || sSrvcTypeSeCd.equals("null") || sSrvcTypeSeCd.equals("")) {
				throw new AppWorksException("서비스유형구분코드는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}

			String sOtmYn = String.valueOf(mapIns.get("OTM_YN")); // 일회성여부
			if (sOtmYn == null || sOtmYn.equals("null") || sOtmYn.equals("")) {
				throw new AppWorksException("일회성여부는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}

			String sPvsnBgngYmd = String.valueOf(mapIns.get("PVSN_BGNG_YMD")); // 제공시작일자
			if (sPvsnBgngYmd == null || sPvsnBgngYmd.equals("null") || sPvsnBgngYmd.equals("")) {
				throw new AppWorksException("제공시작일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}

			String sPvsnEndYmd = String.valueOf(mapIns.get("PVSN_END_YMD")); // 제공종료일자
			if (sPvsnEndYmd == null || sPvsnEndYmd.equals("null") || sPvsnEndYmd.equals("")) {
				throw new AppWorksException("제공종료일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}

			String sUseYn = String.valueOf(mapIns.get("USE_YN")); // 사용여부
			if (sUseYn == null || sUseYn.equals("null") || sUseYn.equals("")) {
				throw new AppWorksException("사용여부는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			}
		}
	}

	Map<String, Object> autoRsfrInstInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// 초기 값 셋팅
		Map<String, Object> pMap = new HashMap<String, Object>();
		String sUserId = "";
		String sUntTaskwk = "";
		String sWprkSqn = ""; // 채번번호
		String sWprkSqn1 = ""; // 채번번호
		String sWprkSqn2 = ""; // 강의번호

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
			sUntTaskwk = loginVO.getUntTaskwk();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		pMap.put("UNT_TASKWK_SE_CD", sUntTaskwk);

		// 단위.업무 소속기관자원제공 주체로 설정된 모든 기관 정보 가져오기.
		List<Map<String, Object>> instList = srvcResrceMapper.getRsfrUntTaskwkInstList(pMap);
		System.out.println(instList.size());
		System.out.println(instList.size());
		System.out.println(instList.size());
		/* -------------- */
		/* 자원 상세 처리 */
		/* -------------- */
		ParameterGroup paramDmDetail = dataRequest.getParameterGroup("dsDetail");
		/* ---------------- */
		/* 자원 담당자 처리 */
		/* ---------------- */
		ParameterGroup paramDsPic = dataRequest.getParameterGroup("dsPicList");
		/* ------------------ */
		/* 자원 프로그램 처리 */
		/* ------------------ */
		ParameterGroup paramDsProgrm = dataRequest.getParameterGroup("dsProgrmList");
		/* -------------------------- */
		/* 자원 프로그램상세일정 처리 */
		/* -------------------------- */
		ParameterGroup paramDsSchdl = dataRequest.getParameterGroup("dsSchdlList");
		/* ---------------------- */
		/* 자원 프로그램강사 처리 */
		/* ---------------------- */
		ParameterGroup paramDsInstr = dataRequest.getParameterGroup("dsInstrList");
		/* ---------------- */
		/* 입교일자 처리 */
		/* ---------------- */
		ParameterGroup paramDsEntsc = dataRequest.getParameterGroup("dsEntscList");
		if (instList == null) {
			throw new AppWorksException("저장할 자원주체가 없읍니다.", Alert.ERROR);
		} else {
			String RSFR_INST_NO = "";
			for (int i = 0; i < instList.size(); i++) {

				RSFR_INST_NO = String.valueOf(instList.get(i).get("RSFR_INST_NO"));// 자원제공주체

				if (paramDmDetail == null) {
					throw new AppWorksException("저장할 자료가 없읍니다.", Alert.ERROR);
				}

				if (paramDmDetail != null) {
					Iterator<ParameterRow> insertedRows = paramDmDetail.getInsertedRows();
					// 등록 이벤트
					while (insertedRows.hasNext()) {

						Map<String, String> mapIns = insertedRows.next().toMap();

						// validation 체크 분리.
						chkedValidation(mapIns, "paramDmDetail");

						// 기관 값 치환.
						mapIns.put("RSFR_INST_NO", RSFR_INST_NO);

						// 필수항목 및 처리항목 체크
						mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
						mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

						// 자원번호 채번
						Map<String, String> seqMap = new HashMap<>();
						Map<String, Object> valMap = new HashMap<>();
						seqMap.put("USER_ID", sUserId);
						seqMap.put("RENU_NO_SE_CD", "RS"); // 자원번호 채번코드
						seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자
						// seqMap.put("RENU_YMD", "99999999"); // 현재일자

						// 채번서비스 호출
						valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
						sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 자원번호 발번
						LOGGER.debug("채번: " + valMap.get("RENU_NO").toString());
						// valMap.get("RENU_NO")
						mapIns.put("RESRCE_NO", sWprkSqn);

						// 자원 상세등록 호출
						srvcResrceMapper.insertResrceDetail(mapIns);
						// 자원 이력등록 호출
						srvcResrceMapper.insertResrceHistory(mapIns);

						/* 2023-01-23 3팀요청 자원이력확인시 첨부파일 확인위해 첨부파일번호 check */
						Map<String, Object> infoFileMap = new HashMap<>();
						List<Map<String, Object>> dtlFileList = new ArrayList<>();

						final String ATCMFL_CL_NM = "자원제공서비스";
						String sAtfino = String.valueOf(mapIns.get("ATFINO")); /* 첨부파일번호 */

						/* 첨부파일번호 */
						if (!"".equals(sAtfino) && !"null".equals(sAtfino)) {
							Map<String, String> paramMap = new HashMap<>();

							/* 첨부파일번호, 첨부파일분류명 */
							paramMap.put("ATFINO", sAtfino);
							paramMap.put("ATCMFL_CL_NM", ATCMFL_CL_NM);
							paramMap.put("HSTR_REG_YN", "N");

							int iNewAtfino = 0;
							int iaddCnt = 0;

							/* 첨부파일정보 조회 */
							infoFileMap = srvcResrceMapper.selectFileInfo(paramMap);
							/* 첨부파일상세목록 조회 */
							dtlFileList = mgmtFileService.selectCmnAllFileList(paramMap);
							/* 신규채번 */
							LOGGER.debug("***** 기존 첨부파일번호 확인=[" + sAtfino + "]");
							iNewAtfino = mgmtFileMapper.selectAttcFileNo();

							LOGGER.debug("***** 신규 첨부파일번호 채번=[" + iNewAtfino + "]");
							mapIns.put("ATFINO", String.valueOf(iNewAtfino));
							srvcResrceMapper.updateResrceHistoryFileNo(mapIns);

							List<Map<String, String>> dtlIrtList = new ArrayList<>();
							Map<String, String> infoIrtMap = new HashMap<>();
							for (String keys : infoFileMap.keySet()) {
								infoIrtMap.put(keys, String.valueOf(infoFileMap.get("keys")));
							}
							/* 첨부파일번호 신규채번후 후 저장(이력첨부파일관리용) */
							infoIrtMap.put("ATFINO", String.valueOf(iNewAtfino));
							infoIrtMap.put("USER_ID", sUserId);
							/* 첨부파일insert SAB820 */
							mgmtFileMapper.insertCmnFile3(infoIrtMap);

							if (dtlFileList.size() > 0) {

								for (int idx = 0; idx < dtlFileList.size(); idx++) {

									/* 기존첨부파일번호 이력등록여부 update ('Y') */
									String sMngSn = String.valueOf(dtlFileList.get(idx).get("MNG_SN"));
									paramMap.put("MNG_SN", sMngSn);
									srvcResrceMapper.updateFileHstrRegYn(paramMap);
									LOGGER.debug("***** 기존 첨부파일번호=[" + dtlFileList.get(idx).get("ATFINO") + "]");
									LOGGER.debug("***** 기존     일련번호=[" + dtlFileList.get(idx).get("MNG_SN") + "]");
									LOGGER.debug("***** 기존 이력등록여부=[" + dtlFileList.get(idx).get("HSTR_REG_YN") + "]");

									/* 이력등록여부 */
									dtlFileList.get(idx).put("ATFINO", iNewAtfino); /* 첨부파일번호 신규부여 */
									dtlFileList.get(idx).put("HSTR_REG_YN", "Y"); /* 첨부파일번호 이력등록여부 */
									LOGGER.debug("***** 신규 첨부파일번호=[" + dtlFileList.get(idx).get("ATFINO") + "]");
									LOGGER.debug("***** 신규 이력등록여부=[" + dtlFileList.get(idx).get("HSTR_REG_YN") + "]");
								}

								/* 첨부파일상세 insert SAB821 */
								for (int idx = 0; idx < dtlFileList.size(); idx++) {
									Map<String, String> getValMap = new HashMap<>();

									for (String keys : dtlFileList.get(idx).keySet()) {
										getValMap.put(keys, String.valueOf(dtlFileList.get(idx).get(keys)));
									}
									getValMap.put("USER_ID", sUserId);
									mgmtFileMapper.insertCmnFile2(getValMap);
									iaddCnt++;
								}
							}

							LOGGER.debug("***** 첨부파일상세 등록건수 ****** " + iaddCnt + "");
						}

						// 자원번호 key값 셋팅
						pMap.put("RESRCE_NO", sWprkSqn);
					} // while 종료
				} // if 자원 상세 처리 종료

				/* 자원 담당자 처리 */
				if (paramDsPic != null) {
					Iterator<ParameterRow> insertedRows = paramDsPic.getInsertedRows();

					// 등록 이벤트
					while (insertedRows.hasNext()) {

						Map<String, String> mapIns = insertedRows.next().toMap();

						// validation 체크 별도 관리 필요.
						// 기관별 대상자 추출
						pMap.put("RSFR_INST_NO", RSFR_INST_NO);

						List<Map<String, Object>> picList = srvcResrceMapper.getRsfrInstPicMemberList(pMap);

						if (picList != null) {
							List<Map<String, Object>> picInstList = new ArrayList<Map<String, Object>>();
							// 관리자 추가 여부 체크
							boolean adminYn = false; // 총괄관리자
							boolean masterYn = false; // 기관관리자
							boolean memberYn = false; // 종사자

							for (int j = 0; j < picList.size(); j++) {
								Map<String, Object> defMap = picList.get(j);

								// 기관담당자가 없을 경우 총괄관리자 전원, 기관관리자 전원 종사자 1명 넣기.
								if (picList.get(0).get("PIC_ENFSN_NO") == null) {
									// 310 : 총괄관리자, 320 : 기관관리자, 340 : 기관 종사자
									String groupAuth = String.valueOf(defMap.get("GROUP_AUTHRT_SE_CD"));
									if ("310".equals(groupAuth) || "320".equals(groupAuth) || "340".equals(groupAuth)) {

										if ("310".equals(groupAuth)) {
											picInstList.add(defMap);
											adminYn = true;
										}
										if ("320".equals(groupAuth)) {
											picInstList.add(defMap);
											masterYn = true;
										}
										if (!memberYn) {
											if ("340".equals(groupAuth)) {
												picInstList.add(defMap);
												memberYn = true;
											}
										}
									}

								} else {// if문 종료
									if (j == 0) {
										Map<String, Object> defMap2 = new HashMap<String, Object>();
										defMap2.put("PIC_NO", picList.get(0).get("PIC_ENFSN_NO"));
										defMap2.put("PIC_NM_ENCPT", picList.get(0).get("PIC_NM_ENCPT"));

										picInstList.add(defMap2);
									}
								}
							} // for문 종료

							// 인서트 처리.
							for (int j = 0; j < picInstList.size(); j++) {
								Map<String, Object> defMap = picInstList.get(j);
								// Insert 용.
								Map<String, String> defIns = new HashMap<String, String>();

								if (j == 0) {
									defIns.put("PCHPRS_YN", "Y"); // 주담당자 여부
									// 주담당자지정일자.
									defIns.put("PCHPRS_DSGN_YMD", String.valueOf(mapIns.get("PCHPRS_DSGN_YMD")));
									// 주담당자 해지일자.
									defIns.put("PCHPRS_CNCLTN_YMD", String.valueOf(mapIns.get("PCHPRS_CNCLTN_YMD")));
								} else {
									defIns.put("PCHPRS_YN", "N"); // 주담당자 여부
								}
								// 담당자 번호
								defIns.put("PIC_NO", String.valueOf(defMap.get("PIC_NO")));
								// 담당자 명
								defIns.put("PIC_NM_ENCPT", String.valueOf(defMap.get("FLNM_ENCPT")));

								// 담당자지정일자
								defIns.put("PIC_DSGN_YMD", String.valueOf(mapIns.get("PIC_DSGN_YMD")));
								// 담당자해지일자
								defIns.put("PIC_CNCLTN_PRCS_YMD", mapIns.get("PIC_CNCLTN_PRCS_YMD")); // 주담당자해지일자.

								// 필수항목 및 처리항목 체크
								defIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
								defIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

								defIns.put("RESRCE_NO", sWprkSqn);

								defIns.put("PIC_INST_NO", RSFR_INST_NO); // 담당자기관번호.
								// 자원 담당자 등록호출
								srvcResrceMapper.insertResrcePic(defIns);
								// 자원 담당자 이력등록호출
								srvcResrceMapper.insertResrcePicHistory(defIns);
							} // 인서트 for문 종료.
						} // picList if문 종료 담당자 처리 종료.

					} // 담당자 등록 while 문 종료

				} // 담당자 등록 if 문 종료

				/* 자원 프로그램 처리 */
				if (paramDsProgrm != null) {
					Iterator<ParameterRow> insertedRows = paramDsProgrm.getInsertedRows();

					// 등록 이벤트
					while (insertedRows.hasNext()) {

						Map<String, String> mapIns = insertedRows.next().toMap();
						// 필수항목 및 처리항목 체크
						mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
						mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

						// 프로그램번호 채번
						Map<String, String> seqMap = new HashMap<>();
						Map<String, Object> valMap = new HashMap<>();

						seqMap.put("USER_ID", sUserId);
						seqMap.put("RENU_NO_SE_CD", "PG"); // 프로그램번호 채번코드
						seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자

						// 채번서비스 호출
						valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
						sWprkSqn1 = String.valueOf(valMap.get("RENU_NO")); // 프로그램번호 발번
						LOGGER.debug("SrvcResrceServiceImpl.processResrceDetail.sWprkSqn1=[" + sWprkSqn1 + "]");

						if (mapIns.get("RESRCE_NO").isEmpty())
							mapIns.put("RESRCE_NO", sWprkSqn);
						mapIns.put("PROGRM_NO", sWprkSqn1);

						// 자원 프로그램 등록호출
						srvcResrceMapper.insertResrceProgrm(mapIns);
						// 자원 프로그램 이력등록호출
						srvcResrceMapper.insertResrceProgrmHistory(mapIns);

					} // while문 종료
				} // if문 자원프로그램 처리 종료

				/* 자원 프로그램상세일정 처리 */
				if (paramDsSchdl != null) {
					Iterator<ParameterRow> insertedRows = paramDsSchdl.getInsertedRows();

					// 등록 이벤트
					while (insertedRows.hasNext()) {

						Map<String, String> mapIns = insertedRows.next().toMap();
						// 필수항목 및 처리항목 체크
						mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅
						mapIns.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드 셋팅

						if (mapIns.get("RESRCE_NO").isEmpty())
							mapIns.put("RESRCE_NO", sWprkSqn);
						if (mapIns.get("PROGRM_NO").isEmpty())
							mapIns.put("PROGRM_NO", sWprkSqn1);

						int iLctreSn = srvcResrceMapper.selectResrceProgrmLctreSn(mapIns);
						mapIns.put("LCTRE_SN", String.valueOf(iLctreSn));
						sWprkSqn2 = String.valueOf(iLctreSn);

						// 자원 프로그램상세일정 등록호출
						srvcResrceMapper.insertResrceProgrmSchdl(mapIns);
						// 자원 프로그램상세일정 이력등록호출
						srvcResrceMapper.insertResrceProgrmSchdlHistory(mapIns);
					} // 등록 while문 종료.
				} // 자원프로그램상세일정 처리 if문 종료

				/* 자원 프로그램강사 처리 */
				if (paramDsInstr != null) {

					Iterator<ParameterRow> insertedRows = paramDsInstr.getInsertedRows();

					// 등록 이벤트
					while (insertedRows.hasNext()) {

						Map<String, String> mapIns = insertedRows.next().toMap();
						// 필수항목 및 처리항목 체크
						mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅

						if (mapIns.get("RESRCE_NO").isEmpty())
							mapIns.put("RESRCE_NO", sWprkSqn);
						if (mapIns.get("PROGRM_NO").isEmpty())
							mapIns.put("PROGRM_NO", sWprkSqn1);
						if (mapIns.get("LCTRE_SN").isEmpty())
							mapIns.put("LCTRE_SN", sWprkSqn2);

						// 자원 프로그램상세일정 등록호출
						srvcResrceMapper.insertResrceProgrmInstr(mapIns);

						// 초기집단상담번호(EG) key값 셋팅
						pMap.put("RESRCE_NO", mapIns.get("RESRCE_NO"));
					} // 자원 프로그램강사 처리 while문 종료.
				} // 자원프로그램강사 처리 if문 종료

				/* 입교일자 처리 */
				if (paramDsEntsc != null) {
					Iterator<ParameterRow> insertedRows = paramDsEntsc.getInsertedRows();

					// 등록 이벤트
					while (insertedRows.hasNext()) {
						Map<String, String> mapIns = insertedRows.next().toMap();

						// 필수항목 및 처리항목 체크
						mapIns.put("SESS_USER_ID", sUserId); // 세션 사용자ID 셋팅

						if (mapIns.get("RESRCE_NO").isEmpty())
							mapIns.put("RESRCE_NO", sWprkSqn);

						// 입교일정 등록호출
						srvcResrceMapper.insertEntsc(mapIns);

					} // 입교일자 처리 while문 종료.
				} // 입교일자 처리 if 문 종료.

			} // instList for문 종료.
		} // instList if문 종료

		return pMap;
	}

}