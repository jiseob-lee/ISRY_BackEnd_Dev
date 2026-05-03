/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.casereg.service.impl;

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
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.drmgs.casereg.mapper.DrmgsCaseRegMapper;
import isry.drmgs.casereg.service.DrmgsCaseRegService;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseExcnMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcm.casemng.uneart.mapper.TrprInqMapper;
import isry.itgcms.syscmmn.survsht.mapper.SurvshtMmnMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;

/**
 * @파일명 : DrmgsCaseRegServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Kang.Hwa.Young
 * @작성일 : 2022. 7. 8.
 * @수정자 : Kang.Hwa.Young
 * @수정일 : 2022. 7. 8.
 * @수정내용 : - -
 */
@Service("drmgsCaseRegService")
public class DrmgsCaseRegServiceImpl extends IsryBaseServiceImpl implements DrmgsCaseRegService {

	private final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	private static final String SRVC_PVSN_MTHD_SE_CD = "02"; // 서비스제공방법구분코드(02:오프라인)
	private static final String SRVC_PVSN_CN = "건강검진"; // 서비스제공내용
	private static final String SRVC_PVSN_BGNG_HR = "0900"; // 서비스제공시작시간
	private static final String SRVC_PVSN_END_HR = "1700"; // 서비스제공종료시간

	@Resource(name = "drmgsCaseRegMapper")
	private DrmgsCaseRegMapper drmgsCaseRegMapper;

	@Resource(name = "survshtMmnMapper")
	private SurvshtMmnMapper survshtMmnMapper;

	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

//	@Resource(name="trprInqService")
//    private TrprInqService trprInqService;

	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name = "caseExcnMapper")
	private CaseExcnMapper caseExcnMapper;

	@Resource(name = "trprInqMapper")
	private TrprInqMapper trprInqMapper;

	@Resource(name = "caseRegMapper")
	private CaseRegMapper caseRegMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @Method명 : outcomeDetail
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> outcomeDetail(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return drmgsCaseRegMapper.outcomeDetail(searchParamMap);
	}

	/**
	 * @Method명 : outcomeList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> outcomeList(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return drmgsCaseRegMapper.outcomeList(searchParamMap);
	}

	/**
	 * @Method명 : outcomeList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> dsOccpOutList(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return drmgsCaseRegMapper.dsOccpOutList(searchParamMap);
	}

	@Override
	public List<Map<String, String>> dsProgrmList(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return drmgsCaseRegMapper.dsProgrmList(searchParamMap);
	}

	/**
	 * @Method명 : onOutcSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> onOutcSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> dmOutcomeDetailMap = new HashMap<String, String>();
		ParameterGroup params = dataRequest.getParameterGroup("dmOutcomeDetail");
		
		if (params != null) {
			dmOutcomeDetailMap = params.getSingleValueMap();
		
		} else {
			params = dataRequest.getParameterGroup("dsOutcomeDetail");
			
			if (params != null && params.getAllRowList() != null 
					&& params.getAllRowList().size() > 0) {

				dmOutcomeDetailMap = params.getAllRowList().get(0);
			}
		}
		
		if (dmOutcomeDetailMap == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}

		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		Map<String, String> dmSearch = search.getSingleValueMap();
		caseRegService.selectPrgrsStts(dmSearch);
		dmOutcomeDetailMap.putAll(dmSearch);
		Map<String, String> ck1 = drmgsCaseRegMapper.outcomeDetail(dmOutcomeDetailMap);
		if (ck1 == null) {
			// insert
//			drmgsCaseRegMapper.insertOutcomeDetail(dmOutcomeDetailMap);
			drmgsCaseRegMapper.insertOutcomeAllDetail(dmOutcomeDetailMap);
		} else {
			// update
//			drmgsCaseRegMapper.updateOutcomeDetail(dmOutcomeDetailMap);
			drmgsCaseRegMapper.updateOutcomeAllDetail(dmOutcomeDetailMap);
		}

//		params = dataRequest.getParameterGroup("dsSchulwList");
//		List<Map<String, String>> dsSchulwList = params.getAllRowList();
//		for(int i=0;i<dsSchulwList.size();i++) {
//			dsSchulwList.get(i).put("FRST_RGTR_ID", sUserId);
//			dsSchulwList.get(i).put("LAST_MDFR_ID", sUserId);
//			dsSchulwList.get(i).putAll(dmSearch);
//			if(!"".equals(dsSchulwList.get(i).get("RESULT_END_YMD"))) {
//				Map result = drmgsCaseRegMapper.dsSchulwDetail(dsSchulwList.get(i));
//				if(result == null) {
//					drmgsCaseRegMapper.dsSchulwInsert(dsSchulwList.get(i));
//				}else {
//					drmgsCaseRegMapper.dsSchulwUpdate(dsSchulwList.get(i));
//				}
//			}
//		}		

		/* AKA130(성과관리-성과내용) */
		params = dataRequest.getParameterGroup("dsOutcomeList");

		// INSERT
		List<Map<String, String>> insertL = params.getInsertedRowList();
		for (int i = 0; i < insertL.size(); i++) {
			insertL.get(i).putAll(dmSearch);
			insertL.get(i).put("FRST_RGTR_ID", sUserId);
			insertL.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.insertOutcomeCnDetail(insertL.get(i));

			String sOutcCnLclasSeCd = insertL.get(i).get("OUTC_CN_LCLAS_SE_CD");
			String sOutcCnMlsfcSeCd = insertL.get(i).get("OUTC_CN_MLSFC_SE_CD");

			// 성과내용대분류구분코드(06:자격취득), 성과내용중분류구분코드(0202:자격취득)
			if ("06".equals(sOutcCnLclasSeCd) || "0202".equals(sOutcCnMlsfcSeCd)) {
//				saveTrprQlfcInfo(insertL.get(i), sUserId); //임시주석처리
			}
		}

		// UPDATE
		List<Map<String, String>> updateL = params.getUpdatedRowList();
		for (int i = 0; i < updateL.size(); i++) {
			updateL.get(i).put("FRST_RGTR_ID", sUserId);
			updateL.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.updateOutcomeCnDetail(updateL.get(i));

			String sOutcCnLclasSeCd = updateL.get(i).get("OUTC_CN_LCLAS_SE_CD");
			String sOutcCnMlsfcSeCd = updateL.get(i).get("OUTC_CN_MLSFC_SE_CD");

			// 성과내용대분류구분코드(06:자격취득), 성과내용중분류구분코드(0202:자격취득)
			if ("06".equals(sOutcCnLclasSeCd) || "0202".equals(sOutcCnMlsfcSeCd)) {
//				saveTrprQlfcInfo(updateL.get(i), sUserId); //임시주석처리
			}
		}

		// DELETE
		List<Map<String, String>> deleteL = params.getDeletedRowList();
		for (int i = 0; i < deleteL.size(); i++) {
			deleteL.get(i).put("FRST_RGTR_ID", sUserId);
			deleteL.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.deleteOutcomeCnDetail(deleteL.get(i));
		}

//		params = dataRequest.getParameterGroup("dsProgrmList");
//		List<Map<String, String>> insertList = params.getInsertedRowList();
//		for(int i=0;i<insertList.size();i++) {
//			insertList.get(i).putAll(dmSearch);
//			insertList.get(i).put("FRST_RGTR_ID", sUserId);
//			insertList.get(i).put("LAST_MDFR_ID", sUserId);
//			drmgsCaseRegMapper.dsProgrmInsert(insertList.get(i));
//		}
//		List<Map<String, String>> updateList = params.getUpdatedRowList();
//		for(int i=0;i<updateList.size();i++) {
//			updateList.get(i).put("FRST_RGTR_ID", sUserId);
//			updateList.get(i).put("LAST_MDFR_ID", sUserId);
//			drmgsCaseRegMapper.dsProgrmUpdate(updateList.get(i));
//		}
//		List<Map<String, String>> deleteList = params.getDeletedRowList();
//		for(int i=0;i<deleteList.size();i++) {
//			deleteList.get(i).put("FRST_RGTR_ID", sUserId);
//			deleteList.get(i).put("LAST_MDFR_ID", sUserId);
//			drmgsCaseRegMapper.dsProgrmDelete(deleteList.get(i));
//		}
//		params = dataRequest.getParameterGroup("dsOccpOutList");
//		List<Map<String, String>> insert2List = params.getInsertedRowList();
//		for(int i=0;i<insert2List.size();i++) {
//			insert2List.get(i).putAll(dmSearch);
//			insert2List.get(i).put("FRST_RGTR_ID", sUserId);
//			insert2List.get(i).put("LAST_MDFR_ID", sUserId);
//			drmgsCaseRegMapper.dsOccpOutInsert(insert2List.get(i));
//		}
//		List<Map<String, String>> update2List = params.getUpdatedRowList();
//		for(int i=0;i<update2List.size();i++) {
//			update2List.get(i).putAll(dmSearch);
//			update2List.get(i).put("FRST_RGTR_ID", sUserId);
//			update2List.get(i).put("LAST_MDFR_ID", sUserId);
//			drmgsCaseRegMapper.dsOccpOutUpdate(update2List.get(i));
//		}
//		List<Map<String, String>> delete2List = params.getDeletedRowList();
//		for(int i=0;i<delete2List.size();i++) {
//			delete2List.get(i).putAll(dmSearch);
//			delete2List.get(i).put("FRST_RGTR_ID", sUserId);
//			delete2List.get(i).put("LAST_MDFR_ID", sUserId);
//			drmgsCaseRegMapper.dsOccpOutDelete(delete2List.get(i));
//		}	

		return null;
	}

	/**
	 * @Method명 : onOutcExcnSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> onOutcExcnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없읍니다.", Alert.ERROR);
		}

		ParameterGroup params = dataRequest.getParameterGroup("dsOutcomeDetail");
		Map<String, String> dmOutcomeDetailMap = params.getAllRowList().get(0);
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		Map<String, String> dmSearch = search.getSingleValueMap();

		dmOutcomeDetailMap.putAll(dmSearch);
		Map<String, String> ck1 = drmgsCaseRegMapper.outcomeDetail(dmOutcomeDetailMap);
		if (ck1 == null) {
			// insert
			drmgsCaseRegMapper.insertOccpDetail(dmOutcomeDetailMap);
		} else {
			// update
			drmgsCaseRegMapper.updateOccpDetail(dmOutcomeDetailMap);
		}

		params = dataRequest.getParameterGroup("dsSchulwList");
		List<Map<String, String>> dsSchulwList = params.getAllRowList();
		for (int i = 0; i < dsSchulwList.size(); i++) {
			dsSchulwList.get(i).put("FRST_RGTR_ID", sUserId);
			dsSchulwList.get(i).put("LAST_MDFR_ID", sUserId);
			dsSchulwList.get(i).putAll(dmSearch);
			if (!"".equals(dsSchulwList.get(i).get("RESULT_END_YMD"))) {
				Map<String, String> result = drmgsCaseRegMapper.dsSchulwDetail(dsSchulwList.get(i));
				if (result == null) {
					drmgsCaseRegMapper.dsSchulwInsert(dsSchulwList.get(i));
				} else {
					drmgsCaseRegMapper.dsSchulwUpdate(dsSchulwList.get(i));
				}
			}
		}

		params = dataRequest.getParameterGroup("dsProgrmList");
		List<Map<String, String>> insertList = params.getInsertedRowList();
		for (int i = 0; i < insertList.size(); i++) {
			insertList.get(i).putAll(dmSearch);
			insertList.get(i).put("FRST_RGTR_ID", sUserId);
			insertList.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsProgrmInsert(insertList.get(i));
		}
		List<Map<String, String>> updateList = params.getUpdatedRowList();
		for (int i = 0; i < updateList.size(); i++) {
			updateList.get(i).put("FRST_RGTR_ID", sUserId);
			updateList.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsProgrmUpdate(updateList.get(i));
		}
		List<Map<String, String>> deleteList = params.getDeletedRowList();
		for (int i = 0; i < deleteList.size(); i++) {
			deleteList.get(i).put("FRST_RGTR_ID", sUserId);
			deleteList.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsProgrmDelete(deleteList.get(i));
		}
		params = dataRequest.getParameterGroup("dsOccpOutList");
		List<Map<String, String>> insert2List = params.getInsertedRowList();
		for (int i = 0; i < insert2List.size(); i++) {
			insert2List.get(i).putAll(dmSearch);
			insert2List.get(i).put("FRST_RGTR_ID", sUserId);
			insert2List.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsOccpOutInsert(insert2List.get(i));
		}
		List<Map<String, String>> update2List = params.getUpdatedRowList();
		for (int i = 0; i < update2List.size(); i++) {
			update2List.get(i).putAll(dmSearch);
			update2List.get(i).put("FRST_RGTR_ID", sUserId);
			update2List.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsOccpOutUpdate(update2List.get(i));
		}
		List<Map<String, String>> delete2List = params.getDeletedRowList();
		for (int i = 0; i < delete2List.size(); i++) {
			delete2List.get(i).putAll(dmSearch);
			delete2List.get(i).put("FRST_RGTR_ID", sUserId);
			delete2List.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsOccpOutDelete(delete2List.get(i));
		}

		return null;
	}

	/**
	 * @Method명 : onOutcAllSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 : 성과 일괄 등록
	 */
	@Override
	public Map<String, String> onOutcAllSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOutcomeDetail");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		searchParam = dataRequest.getParameterGroup("dsSearch");
		List<Map<String, String>> dsSearch = searchParam.getAllRowList();

		ParameterGroup searchParam2 = dataRequest.getParameterGroup("dsOutcomeList");

		for (int i = 0; i < dsSearch.size(); i++) {
			dmOutcomeDetailMap.putAll(dsSearch.get(i));

			Map<String, String> ck1 = drmgsCaseRegMapper.outcomeDetail(dmOutcomeDetailMap);
			if (ck1 == null) {
				// insert
				drmgsCaseRegMapper.insertOutcomeAllDetail(dmOutcomeDetailMap);
			} else {
				// update
				drmgsCaseRegMapper.updateOutcomeAllDetail(dmOutcomeDetailMap);
			}
			/*
			 * //성과 일괄 등록이므로 전체 삭제 뒤 다시 insert 한다.
			 * drmgsCaseRegMapper.deleteOutcomeCnDetailAll(dsSearch.get(i));
			 * 
			 * while (allRows.hasNext()) { ParameterRow paramRow = allRows.next();
			 * if(!RowState.DELETED.equals(paramRow.getState())) { Map<String, String>
			 * dsOutcome = paramRow.toMap(); dsOutcome.putAll(dsSearch.get(i));
			 * dsOutcome.put("FRST_RGTR_ID", sUserId); dsOutcome.put("LAST_MDFR_ID",
			 * sUserId);
			 * 
			 * drmgsCaseRegMapper.insertOutcomeCnDetail(dsOutcome);
			 * 
			 * String sOutcCnLclasSeCd = dsOutcome.get("OUTC_CN_LCLAS_SE_CD"); String
			 * sOutcCnMlsfcSeCd = dsOutcome.get("OUTC_CN_MLSFC_SE_CD");
			 * 
			 * // 성과내용대분류구분코드(06:자격취득), 성과내용중분류구분코드(0202:자격취득)
			 * if("06".equals(sOutcCnLclasSeCd) || "0202".equals(sOutcCnMlsfcSeCd)) {
			 * saveTrprQlfcInfo(dsOutcome, sUserId); } } }
			 */
			// 로직 변경에 따라 delete insert를 insert, update로 변경. 기 등록된 성과는 삭제 안됨
			Iterator<ParameterRow> allRows = searchParam2.getAllRows();
			while (allRows.hasNext()) {
				ParameterRow paramRow = allRows.next();
				if (!RowState.DELETED.equals(paramRow.getState())) {
					Map<String, String> dsOutcome = paramRow.toMap();
					dsOutcome.putAll(dsSearch.get(i));
					dsOutcome.put("FRST_RGTR_ID", sUserId);
					dsOutcome.put("LAST_MDFR_ID", sUserId);
					if (RowState.INSERTED.equals(paramRow.getState())) {
						drmgsCaseRegMapper.insertOutcomeCnDetail(dsOutcome);
					}
					if (RowState.UPDATED.equals(paramRow.getState())) {
						drmgsCaseRegMapper.updateOutcomeCnDetail(dsOutcome);
					}

					String sOutcCnLclasSeCd = dsOutcome.get("OUTC_CN_LCLAS_SE_CD");
					String sOutcCnMlsfcSeCd = dsOutcome.get("OUTC_CN_MLSFC_SE_CD");

					// 성과내용대분류구분코드(06:자격취득), 성과내용중분류구분코드(0202:자격취득)
					if ("06".equals(sOutcCnLclasSeCd) || "0202".equals(sOutcCnMlsfcSeCd)) {
//						saveTrprQlfcInfo(dsOutcome, sUserId); //임시주석처리
					}
				}
			}
		}

		return null;
	}

	/**
	 * @Method명 : onOccpAbilitSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> onOccpAbilitSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOutcomeDetail");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);

		searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = searchParam.getSingleValueMap();

		dmOutcomeDetailMap.putAll(dmSearch);

		Map<String, String> ck1 = drmgsCaseRegMapper.outcomeDetail(dmOutcomeDetailMap);
		if (ck1 == null) {
			// insert
			drmgsCaseRegMapper.insertOccpDetail(dmOutcomeDetailMap);
		} else {
			// update
			drmgsCaseRegMapper.updateOccpDetail(dmOutcomeDetailMap);
		}

		searchParam = dataRequest.getParameterGroup("dsProgrmList");
//		dsProgrmList = searchParam.getAllRowList();
		List<Map<String, String>> insertList = searchParam.getInsertedRowList();
		for (int i = 0; i < insertList.size(); i++) {
			insertList.get(i).putAll(dmSearch);
			insertList.get(i).put("FRST_RGTR_ID", sUserId);
			insertList.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsProgrmInsert(insertList.get(i));
		}
		List<Map<String, String>> updateList = searchParam.getUpdatedRowList();
		for (int i = 0; i < updateList.size(); i++) {
			updateList.get(i).put("FRST_RGTR_ID", sUserId);
			updateList.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsProgrmUpdate(updateList.get(i));
		}
		List<Map<String, String>> deleteList = searchParam.getDeletedRowList();
		for (int i = 0; i < deleteList.size(); i++) {
			deleteList.get(i).put("FRST_RGTR_ID", sUserId);
			deleteList.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsProgrmDelete(deleteList.get(i));
		}

		searchParam = dataRequest.getParameterGroup("dsOccpOutList");
		List<Map<String, String>> insert2List = searchParam.getInsertedRowList();
		for (int i = 0; i < insert2List.size(); i++) {
			insert2List.get(i).putAll(dmSearch);
			insert2List.get(i).put("FRST_RGTR_ID", sUserId);
			insert2List.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsOccpOutInsert(insert2List.get(i));
		}
		List<Map<String, String>> update2List = searchParam.getUpdatedRowList();
		for (int i = 0; i < update2List.size(); i++) {
			update2List.get(i).putAll(dmSearch);
			update2List.get(i).put("FRST_RGTR_ID", sUserId);
			update2List.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsOccpOutUpdate(update2List.get(i));
		}
		List<Map<String, String>> delete2List = searchParam.getDeletedRowList();
		for (int i = 0; i < delete2List.size(); i++) {
			delete2List.get(i).putAll(dmSearch);
			delete2List.get(i).put("FRST_RGTR_ID", sUserId);
			delete2List.get(i).put("LAST_MDFR_ID", sUserId);
			drmgsCaseRegMapper.dsOccpOutDelete(delete2List.get(i));
		}

		return null;
	}

	@Override
	public List<Map<String, String>> dsSchulwList(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return drmgsCaseRegMapper.dsSchulwList(searchParamMap);
	}

	/**
	 * @Method명 : onSchulwDscntcSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> onSchulwDscntcSave(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

//		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//		Map<String, String> dmSearch = searchParam.getSingleValueMap();

		ParameterGroup searchParam = dataRequest.getParameterGroup("dsSchulwList");
		List<Map<String, String>> dsSchulwList = searchParam.getAllRowList();
		for (int i = 0; i < dsSchulwList.size(); i++) {
			dsSchulwList.get(i).put("FRST_RGTR_ID", sUserId);
			dsSchulwList.get(i).put("LAST_MDFR_ID", sUserId);
			if (!"".equals(dsSchulwList.get(i).get("RESULT_END_YMD"))) {
				Map<String, String> result = drmgsCaseRegMapper.dsSchulwDetail(dsSchulwList.get(i));
				if (result == null) {
					drmgsCaseRegMapper.dsSchulwInsert(dsSchulwList.get(i));
				} else {
					drmgsCaseRegMapper.dsSchulwUpdate(dsSchulwList.get(i));
				}
			}
		}

		return null;
	}

	@Override
	public List<Map<String, String>> onSchulwDscntcList(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		return drmgsCaseRegMapper.onSchulwDscntcList(searchParamMap);
	}

	@Override
	public List<Map<String, Object>> selectOccpAbilitInsertList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		searchParamMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		return drmgsCaseRegMapper.selectOccpAbilitInsertList(paramMap2);
	}

	/**
	 * @Method명 : onOccpSurvshtDtl
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 16.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> onOccpSurvshtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		param.put("USER_ID", userId);
		param.put("QUSTNB_MNG_NO", dmSearch.getValue("QUSTNB_MNG_NO"));// 설문지관리번호
		param.put("SRVY_RSPNS_MNG_NO", dmSearch.getValue("SRVY_RSPNS_MNG_NO"));// 설문응답관리번호
		Map<String, Object> dmQustnbMngInfo = survshtMmnMapper.selectQustnbMngInfo(param);
		int qesitmCnt = survshtMmnMapper.selectQesitmCnt(param);
		dmQustnbMngInfo.put("QESITM_CNT", qesitmCnt);

		List<Map<String, Object>> qustnbList = survshtMmnMapper.selectQesitmQustnbCheckList(param);

		List<Map<String, Object>> dsList = drmgsCaseRegMapper.selectPreSurvshtList(param);
		List<Map<String, Object>> ds3 = survshtMmnMapper.selectPreSurvshtDtlList(param);

		result.put("dmQustnbMngInfo", dmQustnbMngInfo);
		result.put("dsList", dsList); // 설문지문항정보
		result.put("ds3", ds3);
		result.put("qustnbList", qustnbList); // 영역설정 가져오기

		return result;
	}

	/**
	 * @Method명 : onSchulwDscntcSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> onOccpSurvshtSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID
		String userIp = request.getRemoteAddr();

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = searchParam.getSingleValueMap();
		dmSearch.put("FRST_RGTR_ID", sUserId);
		dmSearch.put("LAST_MDFR_ID", sUserId);
		dmSearch.put("CNTN_IP_ADDR", userIp);

		String SRVY_RSPNS_MNG_NO = dmSearch.get("SRVY_RSPNS_MNG_NO"); // 설문응답관리번호
		String qustnbTmptMngNo = dmSearch.get("QUSTNB_TMPT_MNG_NO");  // 설문템플릿관리번호
		if (SRVY_RSPNS_MNG_NO == "" || "".equals(SRVY_RSPNS_MNG_NO)) {
			Map<String, String> mngNoMap = new HashMap<String, String>();
			mngNoMap.put("USER_ID", sUserId);
			mngNoMap.put("SYS_CD", "AS");
			SRVY_RSPNS_MNG_NO = survshtMmnMapper.selectSysSeCd(mngNoMap); // 없으면 새로 채번한다.
			dmSearch.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
		}

		searchParam = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsList = searchParam.getAllRowList();

		// 설문대상자 저장 - SBB100
		drmgsCaseRegMapper.mergeSBB100(dmSearch);

		// 설문응답 저장 - SBB500
		drmgsCaseRegMapper.mergeSBB500(dmSearch);

		// 항목별응답내용 저장 - SBB220
		drmgsCaseRegMapper.deleteSBB220(dmSearch);// 전체 삭제
		// 채점결과 저장 - SBB510
		int tot = 0;
		int tot2 = 0;
		for (int i = 0; i < dsList.size(); i++) {
			Map<String, String> obj = dsList.get(i);
			obj.putAll(dmSearch);
			String QUSTNB_GRDNG_RELM_MNG_NO = obj.get("QUSTNB_GRDNG_RELM_MNG_NO");
			drmgsCaseRegMapper.insertSBB220(obj);
			String CHTY_QESITM_RSPNS_VALUE = obj.get("CHTY_QESITM_RSPNS_VALUE");
			if (CHTY_QESITM_RSPNS_VALUE == "" || "".contentEquals(CHTY_QESITM_RSPNS_VALUE))
				CHTY_QESITM_RSPNS_VALUE = "0";
			tot += Integer.parseInt(CHTY_QESITM_RSPNS_VALUE);
			tot2 += Integer.parseInt(CHTY_QESITM_RSPNS_VALUE);

			if ((i + 1) < dsList.size()) {
				if (!QUSTNB_GRDNG_RELM_MNG_NO.equals(dsList.get(i + 1).get("QUSTNB_GRDNG_RELM_MNG_NO"))) {
					obj.put("GRDNG_SCORE", String.valueOf(tot));
					drmgsCaseRegMapper.mergeSBB510(obj);
					tot = 0;
				}

			} else if ((i + 1) == dsList.size()) {
				obj.put("GRDNG_SCORE", String.valueOf(tot));
				drmgsCaseRegMapper.mergeSBB510(obj);
			}
		}
		
		// 설문응답 확인항목저장 - AKA011
		if("TM2023021000001".equals(qustnbTmptMngNo) || "TM2023021000002".equals(qustnbTmptMngNo)
				|| "TM2023021000003".equals(qustnbTmptMngNo) || "TM2023021000004".equals(qustnbTmptMngNo)) {
			searchParam = dataRequest.getParameterGroup("dsIdntyQesitm");
			List<Map<String, String>> dsIdntyQesitm = searchParam.getAllRowList();
			drmgsCaseRegMapper.deleteAKA011(dmSearch);
			for (Map<String, String> map : dsIdntyQesitm) {
				map.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
				map.put("USER_ID", sUserId);
				drmgsCaseRegMapper.insertAKA011(map);
			}
		}
		
		dmSearch.put("GRDNG_SCORE", String.valueOf(tot2));

		return dmSearch;
	}

	/**
	 * @Method명 : selectChupList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 16.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectChupList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> dsChupInfo = drmgsCaseRegMapper.selectChupInfo(paramMap);
		List<Map<String, Object>> dsChupList = drmgsCaseRegMapper.selectChupList(paramMap);

		result.put("dsChupInfo", dsChupInfo); // 건강검진수검대상자
		result.put("dsChupList", dsChupList); // 건강검진이력

		return result;
	}

	@Override
	public List<Map<String, String>> outcomeAllList(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dsSearch");
		List<Map<String, String>> dsSearch = searchParam.getAllRowList();
		if (dsSearch.size() == 1) {
			List<Map<String, String>> result = drmgsCaseRegMapper.outcomeList(dsSearch.get(0));
			if (result.size() == 0)
				return null;
			else
				return result;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, String> outcomeAllDetail(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dsSearch");
		List<Map<String, String>> dsSearch = searchParam.getAllRowList();
		if (dsSearch.size() == 1) {
			return drmgsCaseRegMapper.outcomeDetail(dsSearch.get(0));
		} else {
			return null;
		}

	}

	@Override
	public List<Map<String, String>> selectQusList(DataRequest dataRequest) throws Exception {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		List<Map<String, String>> result = drmgsCaseRegMapper.selectQusList(paramMap);
		return result;
	}

	@Override
	public List<Map<String, String>> selectInspSrvyList(DataRequest dataRequest) throws Exception {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		List<Map<String, String>> result = drmgsCaseRegMapper.selectInspSrvyList(paramMap);
		return result;
	}

	/**
	 * 학교밖 사례관리 상세조회
	 * 
	 * @Method명 : selectCaseMngDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 8. 12.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectCaseMngDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();

		/*
		 * //정서.행동특성검사 paramMap.put("INSP_SRVY_TYPE_CL_SE_CD", "01"); List<Map<String,
		 * Object>> dsEmtGhvrCharInspList =
		 * drmgsCaseRegMapper.selectCaseInspSrvyList(paramMap);
		 * 
		 * //인터넷.스마트폰진단 paramMap.put("INSP_SRVY_TYPE_CL_SE_CD", "02"); List<Map<String,
		 * Object>> dsIntnetSmrtpDgnssList =
		 * drmgsCaseRegMapper.selectCaseInspSrvyList(paramMap);
		 * 
		 * //전문 프로그램 만족도 관리 paramMap.put("INSP_SRVY_TYPE_CL_SE_CD", "03");
		 * List<Map<String, Object>> dsSpcltyProgrmDgstfnList =
		 * drmgsCaseRegMapper.selectCaseInspSrvyList(paramMap);
		 */
		// 검사/설문
		List<Map<String, Object>> dsInspSrvyList = drmgsCaseRegMapper.selectCaseInspSrvyList(paramMap);
		List<Map<String, Object>> dsCaseChupList = drmgsCaseRegMapper.selectCaseChupList(paramMap);
		List<Map<String, Object>> dsChupList = drmgsCaseRegMapper.selectChupList(paramMap);
		List<Map<String, Object>> dsActvtSafetyMuaiasList = drmgsCaseRegMapper.selectActvtSafetyMuaiasList(paramMap);
		List<Map<String, Object>> dsCrisisScrenn = drmgsCaseRegMapper.selectCrisisScrenn(paramMap);
		List<Map<String, Object>> dsHpeSrvc = drmgsCaseRegMapper.selectHpeSrvc(paramMap);
//		result.put("dsEmtGhvrCharInspList"   , dsEmtGhvrCharInspList);	  //검사/설문(정서.행동특성검사)
//		result.put("dsIntnetSmrtpDgnssList"  , dsIntnetSmrtpDgnssList);	  //검사/설문(인터넷.스마트폰진단)
//		result.put("dsSpcltyProgrmDgstfnList", dsSpcltyProgrmDgstfnList); //검사/설문(전문 프로그램 만족도 관리)	
		result.put("dsInspSrvyList", dsInspSrvyList); // 검사/설문
		result.put("dsCaseChupList", dsCaseChupList); // 사례건강검진(AKA000)
		result.put("dsChupList", dsChupList); // 건강검진(AKA001)
		result.put("dsActvtSafetyMuaiasList", dsActvtSafetyMuaiasList); // 활동안전공제회(AKA002)
		result.put("dsCrisisScrenn", dsCrisisScrenn); // 위기스크리닝(AKA000)
		result.put("dsHpeSrvc", dsHpeSrvc); // 위기스크리닝(AKA000)

		return result;

	}

	/**
	 * 사례관리 상세정보 저장
	 * 
	 * @Method명 : saveCaseRegDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveCaseRegDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		Map<String, String> rtnMap = new HashMap<>();

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		// 1.업무공통영역 저장
		LOGGER.debug("================= 업무공통영역 저장 START =================");
		Map<String, Object> info = caseRegService.processData(request, dataRequest);
		LOGGER.debug("================= 업무공통영역 저장 END =================");

		String sCaseMngNo = "";
		String sCaseMngOdrno = "";

		ParameterGroup dataParam = dataRequest.getParameterGroup("dsInspSrvyList");
		List<Map<String, String>> dsInspSrvyList = dataParam.getAllRowList();

		if (dsInspSrvyList.size() > 0) {
			Map<String, String> mapInspSrvy = dsInspSrvyList.get(0);
			sCaseMngNo = mapInspSrvy.get("CASE_MNG_NO");
			sCaseMngOdrno = mapInspSrvy.get("CASE_MNG_ODRNO");
		}

		if (sCaseMngNo.isEmpty())
			sCaseMngNo = String.valueOf(info.get("CASE_MNG_NO"));
		if (sCaseMngOdrno.isEmpty())
			sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

		LOGGER.debug("================= 검사설문 저장(AKA010) START =================");

		// 2. 검사설문 저장(AKA010)
		// 2.1. 등록 이벤트
		Iterator<ParameterRow> insertedRows = dataParam.getInsertedRows();
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("CASE_MNG_NO", sCaseMngNo);
			mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			mapIns.put("DEL_YN", "N");
			mapIns.put("USER_ID", sUserId);

			if (!mapIns.get("QUSTNB_TMPT_MNG_NO2").isEmpty() && !mapIns.get("QUSTNB_MNG_NO2").isEmpty()
					&& !mapIns.get("SRVY_RSPNS_MNG_NO2").isEmpty()) {
				mapIns.put("SUCDE_ITRVW_RCORDP_WRT_YN", "Y");
			} else {
				mapIns.put("SUCDE_ITRVW_RCORDP_WRT_YN", "N");
			}

			drmgsCaseRegMapper.insertAKA010(mapIns);
		}

		// 2.2.수정 이벤트
		Iterator<ParameterRow> updatedRowList = dataParam.getUpdatedRows();
		while (updatedRowList.hasNext()) {

			Map<String, String> mapUpd = updatedRowList.next().toMap();
			mapUpd.put("USER_ID", sUserId);

			drmgsCaseRegMapper.updateAKA010(mapUpd);
		}

		// 2.3.삭제 이벤트
		Iterator<ParameterRow> deletedRowList = dataParam.getDeletedRows();
		while (deletedRowList.hasNext()) {

			Map<String, String> mapDel = deletedRowList.next().toMap();
			mapDel.put("USER_ID", sUserId);
			drmgsCaseRegMapper.deleteAKA010(mapDel);
		}

		LOGGER.debug("================= 검사설문 저장(AKA010) END =================");

		LOGGER.debug("================= 건강검진 저장(AKA001) START =================");

		dataParam = dataRequest.getParameterGroup("dsChupList");

		// 3. 건강검진 저장(AKA001)
		// 3.1. 등록 이벤트
		insertedRows = dataParam.getInsertedRows();
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("CASE_MNG_NO", sCaseMngNo);
			mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			mapIns.put("DEL_YN", "N");
			mapIns.put("USER_ID", sUserId);

			// 건강검진자원번호구분코드에 자원번호가 매핑되어 있을 경우 서비스제공(SEB500), 서비스제공대상자(SEB510) 등록
			String sResrceNo = mapIns.get("RESRCE_NO");
			String sSrvcPvsnNo = null;
			if (sResrceNo != null && !"".equals(sResrceNo)) {
				sSrvcPvsnNo = saveSrcvPvsn(request, "INSERT", mapIns);
			}

			mapIns.put("SRVC_PVSN_NO", sSrvcPvsnNo);

			drmgsCaseRegMapper.insertAKA001(mapIns);
		}

		// 3.2.수정 이벤트
		updatedRowList = dataParam.getUpdatedRows();
		while (updatedRowList.hasNext()) {

			Map<String, String> mapUpd = updatedRowList.next().toMap();

			String sSrvcPvsnNo = null;
			sSrvcPvsnNo = saveSrcvPvsn(request, "UPDATE", mapUpd);

			mapUpd.put("SRVC_PVSN_NO", sSrvcPvsnNo);
			mapUpd.put("USER_ID", sUserId);

			drmgsCaseRegMapper.updateAKA001(mapUpd);
		}

		// 3.3.삭제 이벤트
		deletedRowList = dataParam.getDeletedRows();
		while (deletedRowList.hasNext()) {

			Map<String, String> mapDel = deletedRowList.next().toMap();

			String sSrvcPvsnNo = null;
			sSrvcPvsnNo = saveSrcvPvsn(request, "DELETE", mapDel);

			mapDel.put("SRVC_PVSN_NO", sSrvcPvsnNo);
			mapDel.put("USER_ID", sUserId);

			drmgsCaseRegMapper.deleteAKA001(mapDel);
		}

		LOGGER.debug("================= 건강검진 저장(AKA001) END =================");

		LOGGER.debug("================= 활동안전공제회 저장(AKA002) START =================");

		dataParam = dataRequest.getParameterGroup("dsActvtSafetyMuaiasList");

		// 4. 활동안전공제회 저장(AKA002)
		// 4.1. 등록 이벤트
		insertedRows = dataParam.getInsertedRows();
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("CASE_MNG_NO", sCaseMngNo);
			mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			mapIns.put("DEL_YN", "N");
			mapIns.put("USER_ID", sUserId);

			drmgsCaseRegMapper.insertAKA002(mapIns);
		}

		// 4.2.수정 이벤트
		updatedRowList = dataParam.getUpdatedRows();
		while (updatedRowList.hasNext()) {

			Map<String, String> mapUpd = updatedRowList.next().toMap();
			mapUpd.put("USER_ID", sUserId);

			drmgsCaseRegMapper.updateAKA002(mapUpd);
		}

		// 4.3.삭제 이벤트
		deletedRowList = dataParam.getDeletedRows();
		while (deletedRowList.hasNext()) {

			Map<String, String> mapDel = deletedRowList.next().toMap();
			mapDel.put("USER_ID", sUserId);
			drmgsCaseRegMapper.deleteAKA002(mapDel);
		}

		LOGGER.debug("================= 활동안전공제회 저장(AKA002) END =================");
		
		LOGGER.debug("================= 사례건강검진 저장(AKA000) START =================");

		dataParam = dataRequest.getParameterGroup("dsCaseChupList");

		// 5. 사례건강검진 저장(AKA000)
		// 5.1. 등록 이벤트
		insertedRows = dataParam.getInsertedRows();
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("CASE_MNG_NO", sCaseMngNo);
			mapIns.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			mapIns.put("DEL_YN", "N");
			mapIns.put("USER_ID", sUserId);

			drmgsCaseRegMapper.insertAKA000(mapIns);
		}

		// 5.2.수정 이벤트
		updatedRowList = dataParam.getUpdatedRows();
		while (updatedRowList.hasNext()) {

			Map<String, String> mapUpd = updatedRowList.next().toMap();
			mapUpd.put("USER_ID", sUserId);

			drmgsCaseRegMapper.updateAKA000(mapUpd);
		}

		// 5.3.삭제 이벤트
		// 확인필요

		LOGGER.debug("================= 사례건강검진 저장(AKA000) END =================");

		// 6. 위기스크리닝 저장(AKA000)
		LOGGER.debug("================= 위기스크리닝 저장(AKA000) START =================");

		dataParam = dataRequest.getParameterGroup("dsCrisisScrenn");
		insertedRows = dataParam.getAllRows();
		while (insertedRows.hasNext()) {
			Map<String, String> paramMap = insertedRows.next().toMap();
			paramMap.put("CASE_MNG_NO", sCaseMngNo);
			paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			paramMap.put("USER_ID", sUserId);
			drmgsCaseRegMapper.updateCrisisScrenn(paramMap);
		}

		LOGGER.debug("================= 위기스크리닝 저장(AKA000) END =================");

		rtnMap.put("CASE_MNG_NO", sCaseMngNo);
		rtnMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

		LOGGER.debug("================= 희망서비스 조사표 저장(AKA012) START =================");
		
		dataParam = dataRequest.getParameterGroup("dsHpeSrvc");
		insertedRows = dataParam.getAllRows();
		
		while (insertedRows.hasNext()) {
			Map<String, String> hpeSrvcMap = insertedRows.next().toMap();
			hpeSrvcMap.put("CASE_MNG_NO", sCaseMngNo);
			hpeSrvcMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			hpeSrvcMap.put("USER_ID", sUserId);
			drmgsCaseRegMapper.mergeHpeSrvc(hpeSrvcMap);
		}
		
		LOGGER.debug("================= 희망서비스 조사표 저장(AKA012) END =================");
		return rtnMap;
	}

	/**
	 * @Method명 : onOutcSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveCaseTrmnDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		ParameterGroup search = dataRequest.getParameterGroup("dsSearch");
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없읍니다.", Alert.ERROR);
		}

		ParameterGroup params = dataRequest.getParameterGroup("dsOutcomeDetail");

		Map<String, String> dmSearch = search.getAllRowList() == null || search.getAllRowList().size() == 0 
				? new HashMap<>() : search.getAllRowList().get(0);
		String YNGBGS_SE_NO = dmSearch.get("YNGBGS_SE_NO");
		if (YNGBGS_SE_NO != null && !"".equals(YNGBGS_SE_NO) && YNGBGS_SE_NO.indexOf("02") < 0) {
			return dmSearch;
		}

		Map<String, String> dmOutcomeDetailMap = params.getAllRowList().get(0);
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		dmOutcomeDetailMap.putAll(dmSearch);
		drmgsCaseRegMapper.updateAKA100(dmOutcomeDetailMap);

//		if(params.getInsertedRowList() != null && params.getInsertedRowList().size()>0) {
//			Map<String, String> dmOutcomeDetailMap = params.getInsertedRowList().get(0);	
//			dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
//			dmOutcomeDetailMap.putAll(dmSearch);
//			drmgsCaseRegMapper.outcomeTrmninsert(dmOutcomeDetailMap);
//		}
//		if(params.getUpdatedRowList() != null && params.getUpdatedRowList().size()>0) {
//			Map<String, String> dmOutcomeDetailMap = params.getUpdatedRowList().get(0);	
//			dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
//			dmOutcomeDetailMap.putAll(dmSearch);
//			drmgsCaseRegMapper.outcomeTrmnupdate(dmOutcomeDetailMap);
//		}
//
		params = dataRequest.getParameterGroup("dsCaseExcnList");
		List<Map<String, String>> dsCaseExcnList = params.getAllRowList();
		if (dsCaseExcnList != null && dsCaseExcnList.size() > 0) {
			for (int i = 0; i < dsCaseExcnList.size(); i++) {
				dsCaseExcnList.get(i).put("LAST_MDFR_ID", sUserId);
				drmgsCaseRegMapper.updateSEB510(dsCaseExcnList.get(i));
			}
		}

		return dmSearch;
	}

	public String saveSrcvPvsn(HttpServletRequest request, String sPrcsSe, Map<String, String> map) throws Exception {

		LOGGER.debug("================ 건강검진 서비스제공 처리 START ================");

		String sUserId = ""; // 세션정보의 유저ID
		String sEnfsnNo = ""; // 세션정보의 종사자번호
		String sInstNo = ""; // 세션정보의 기관번호

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
			sEnfsnNo = loginVO.getEnfsnNo();
			sInstNo = loginVO.getInstNo().toString();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();
		Map<String, String> saveMap = new HashMap<>();

		String sWprkSqn = null;

		String sSrvcPvsnNo = map.get("SRVC_PVSN_NO"); // 서비스제공번호
		String sResrceNo = map.get("RESRCE_NO"); // 자원번호
		String sResrceNoOrg = map.get("RESRCE_NO_ORG"); // ORG_자원번호
		String sInsptnYmd = map.get("INSPTN_YMD"); // 수검일자

		/* 신규자원번호가 비어있지 않을 경우 생성 */
		if (!"".equals(sResrceNo) && sResrceNo != null) {
			if (!"".equals(sInsptnYmd) && sInsptnYmd != null) {
				seqMap.clear();
				seqMap.put("USER_ID", sUserId); // 세션 사용자ID 셋팅
				seqMap.put("RENU_NO_SE_CD", "SR"); // 서비스제공번호 채번코드
				seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자

				// 채번서비스 호출
				valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sWprkSqn = String.valueOf(valMap.get("RENU_NO")); // 서비스제공번호 발번

				saveMap.clear();
				saveMap.put("SRVC_PVSN_NO", sWprkSqn); // 서비스제공번호
				saveMap.put("RESRCE_NO", sResrceNo); // 자원번호(사용자가 선택한 코드에 등록되어 있는 자원번호)
				saveMap.put("SRVC_PVSN_MTHD_SE_CD", SRVC_PVSN_MTHD_SE_CD); // 서비스제공방법구분코드
				saveMap.put("SRVC_PVSN_CN", SRVC_PVSN_CN); // 서비스제공내용
				saveMap.put("SRVC_PVSN_BGNG_YMD", sInsptnYmd); // 서비스제공시작일자(수검일자로 셋팅)
				saveMap.put("SRVC_PVSN_END_YMD", sInsptnYmd); // 서비스제공종료일자(수검일자로 셋팅)
				saveMap.put("SRVC_PVSN_BGNG_HR", SRVC_PVSN_BGNG_HR); // 서비스제공시작시간
				saveMap.put("SRVC_PVSN_END_HR", SRVC_PVSN_END_HR); // 서비스제공종료시간
				saveMap.put("SRVC_PVSN_WHDA_YN", "Y"); // 서비스제공전일여부
				saveMap.put("PIC_NO", sEnfsnNo); // 담당자번호(로그인사용자 종사자번호)
				saveMap.put("TKCG_INST_NO", sInstNo); // 담당기관번호(로그인사용자 기관번호)
				saveMap.put("USER_ID", sUserId);
				saveMap.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드

				// 서비스제공 저장 호출
				caseExcnMapper.saveSEB500Data(saveMap);
				// 서비스제공 이력등록 호출
				caseExcnMapper.insertSEB501Data(saveMap);

				saveMap.clear();
				saveMap.put("SRVC_PVSN_NO", sWprkSqn); // 서비스제공번호
				saveMap.put("CASE_MNG_NO", map.get("CASE_MNG_NO")); // 사례관리번호
				saveMap.put("CASE_MNG_ODRNO", map.get("CASE_MNG_ODRNO")); // 사례관리차수
				saveMap.put("RESRCE_NO", sResrceNo); // 자원번호(사용자가 선택한 코드에 등록되어 있는 자원번호)
				saveMap.put("SRVC_PVSN_YN", "Y"); // 서비스제공여부
				saveMap.put("SRVC_PVSN_RESULT_CN", SRVC_PVSN_CN); // 서비스제공결과내용
				saveMap.put("DEL_YN", "N"); // 삭제여부
				saveMap.put("USER_ID", sUserId);
				saveMap.put("DATAA_CHG_SE_CD", "I"); // 데이터변경구분코드

				// 서비스제공대상자 저장 호출
				caseExcnMapper.saveSEB510Data(saveMap);
				// 서비스제공대상자 이력등록호출
				caseExcnMapper.insertSEB511Data(saveMap);

				/*
				 * ORG_자원번호가 비어있지 않고 서비스제공번호가 비어있지 않을경우 ORG_자원번호로 생성된 대상 DEL_YN = 'Y' 처리
				 */
				if ((!"".equals(sResrceNoOrg) && sResrceNoOrg != null)
						&& (!"".equals(sSrvcPvsnNo) && sSrvcPvsnNo != null)) {
					map.put("RESRCE_NO", sResrceNoOrg);
					map.put("DEL_YN", "Y");
					map.put("DATAA_CHG_SE_CD", "D");

					// 서비스제공 삭제여부 수정
					caseExcnMapper.updateSEB500DelYN(map);
					// 서비스제공 이력등록 호출
					caseExcnMapper.insertSEB501Data(map);

					// 서비스제공대상자 삭제 호출
					caseExcnMapper.deleteSEB510Data(map);
					// 서비스제공대상자 이력등록 호출
					caseExcnMapper.insertSEB511Data(map);
				}
			}
		}

		/*
		 * 신규자원번호가 비어있고 ORG_자원번호가 비어있지 않을 경우 ORG_자원번호로 생성된 대상 DEL_YN = 'Y' 처리
		 */
		else if (("".equals(sResrceNo) || sResrceNo == null) && (!"".equals(sResrceNoOrg) && sResrceNoOrg != null)) {
			map.put("RESRCE_NO", sResrceNoOrg);
			map.put("DEL_YN", "Y");
			map.put("DATAA_CHG_SE_CD", "D");

			// 서비스제공 삭제여부 수정
			caseExcnMapper.updateSEB500DelYN(map);
			// 서비스제공 이력등록 호출
			caseExcnMapper.insertSEB501Data(map);

			// 서비스제공대상자 삭제 호출
			caseExcnMapper.deleteSEB510Data(map);
			// 서비스제공대상자 이력등록 호출
			caseExcnMapper.insertSEB511Data(map);
		}

		/*
		 * 화면의 행삭제 대상이고 ORG_자원번호가 비어있지 않을 경우 DEL_YN = 'Y' 처리
		 */
//		else if(sPrcsSe == "DELETE" && 
//				(!"".equals(sResrceNo) && sResrceNo != null) &&
//				(sResrceNo.equals(sResrceNoOrg))) {
		else if (sPrcsSe == "DELETE" && (!"".equals(sResrceNoOrg) && sResrceNoOrg != null)) {
			map.put("DEL_YN", "Y");
			map.put("DATAA_CHG_SE_CD", "D");

			// 서비스제공 삭제여부 수정
			caseExcnMapper.updateSEB500DelYN(map);
			// 서비스제공 이력등록 호출
			caseExcnMapper.insertSEB501Data(map);

			// 서비스제공대상자 삭제 호출
			caseExcnMapper.deleteSEB510Data(map);
			// 서비스제공대상자 이력등록 호출
			caseExcnMapper.insertSEB511Data(map);
		}

		LOGGER.debug("================ 건강검진 서비스제공 처리 END ================");

		return sWprkSqn;
	}

	/**
	 * @Method명 : outStgHis
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 2.
	 * @Method설명 : 직업역량강화 최근 이력 조회
	 */
	public Map<String, String> outStgHis(DataRequest dataRequest) throws Exception {
		ParameterGroup dataParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dataParam.getSingleValueMap();
		String sTrprInfoNo = drmgsCaseRegMapper.selectTrprInfoNo(paramMap); // 대상자번호
		String sIndvIdntfcNo = drmgsCaseRegMapper.selectIndvIdntfcNo(sTrprInfoNo); // 개인식별번호
		if (!"".equals(sIndvIdntfcNo) && sIndvIdntfcNo != null) {
			paramMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo);
		} else {
			paramMap.put("INDV_IDNTFC_NO", "");
			paramMap.put("TRPR_INFO_NO", sTrprInfoNo);
		}
		return drmgsCaseRegMapper.selectStgHis(paramMap);
	}

	/**
	 * @Method명 : saveTrprQlfcInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee Seung Yeon
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 대상자자격정보(이력) 저장
	 */
	public void saveTrprQlfcInfo(Map<String, String> map, String sUserId) throws Exception {

		String sTrprInfoNo = map.get("TRPR_INFO_NO");
		String sIndvIdntfcNo = map.get("INDV_IDNTFC_NO");
		if ((sTrprInfoNo == null || "".equals(sTrprInfoNo) || "null".equals(sTrprInfoNo))
				|| (sIndvIdntfcNo == null || "".equals(sIndvIdntfcNo) || "null".equals(sIndvIdntfcNo))) {

			List<Map<String, Object>> rtn = caseRegMapper.selectCaseBassDetail(map);
			if (rtn.size() > 0) {
				Map<String, Object> rtnMap = rtn.get(0);
				if (rtnMap.get("TRPR_INFO_NO") != null && !"".equals(rtnMap.get("TRPR_INFO_NO"))
						&& !"null".equals(rtnMap.get("TRPR_INFO_NO"))) {
					sTrprInfoNo = rtnMap.get("TRPR_INFO_NO").toString();
				}
				if (rtnMap.get("INDV_IDNTFC_NO") != null && !"".equals(rtnMap.get("INDV_IDNTFC_NO"))
						&& !"null".equals(rtnMap.get("INDV_IDNTFC_NO"))) {
					sIndvIdntfcNo = rtnMap.get("INDV_IDNTFC_NO").toString();
				}
			}
		}

		if (sTrprInfoNo == null || "".equals(sTrprInfoNo) || "null".equals(sTrprInfoNo)) {
			throw new AppWorksException("대상자정보번호가 없습니다.", Alert.ERROR);
		}

		String sOutcCnMlsfcSeCd = map.get("OUTC_CN_MLSFC_SE_CD"); // 성과내용중분류
		String OutcCnSclasSeCd = map.get("OUTC_CN_SCLAS_SE_CD"); // 성과내용소분류

		// SEA260 저장 전 존재여부 확인
		List<Map<String, String>> rtnList = drmgsCaseRegMapper.selectCertiTrprQlfcInfo(map);

		// 존재하지 않을 경우 등록
		if (rtnList.size() == 0) {

			// 공인민간자격구분코드 설정
			String sOfapPrvateQlfcSeCd = "";
			if ("0601".equals(sOutcCnMlsfcSeCd) || "0602".equals(sOutcCnMlsfcSeCd) || "0603".equals(sOutcCnMlsfcSeCd)
					|| "020201".equals(OutcCnSclasSeCd) || "020202".equals(OutcCnSclasSeCd)
					|| "020203".equals(OutcCnSclasSeCd)) {
				sOfapPrvateQlfcSeCd = "01";
			} else if ("0604".equals(sOutcCnMlsfcSeCd) || "020204".equals(OutcCnSclasSeCd)) {
				sOfapPrvateQlfcSeCd = "02";
			} else if ("0605".equals(sOutcCnMlsfcSeCd) || "020205".equals(OutcCnSclasSeCd)) {
				sOfapPrvateQlfcSeCd = "03";
			}

			Map<String, String> insertMap = new HashMap<String, String>();
			insertMap.put("TRPR_INFO_NO", sTrprInfoNo); // 대상자정보번호
			insertMap.put("INDV_IDNTFC_NO", sIndvIdntfcNo); // 개인식별번호
			insertMap.put("MNG_SN", trprInqMapper.selectTrprQlfcInfoMngSn(sTrprInfoNo)); // 관리일련번호
			insertMap.put("OFAP_PRVATE_QLFC_SE_CD", sOfapPrvateQlfcSeCd); // 공인민간자격구분코드
			insertMap.put("CERTI_SE_CD", map.get("CERTI_SE_CD")); // 자격증구분코드
			insertMap.put("QLFC_GRAD_SE_CD", map.get("QLFC_GRAD_SE_CD")); // 자격등급구분코드
			insertMap.put("ACQS_YMD", map.get("OUTC_YMD")); // 취득일자
			insertMap.put("CERTI_NM", map.get("CERTI_NM").replaceAll(" ", "")); // 자격증명
			insertMap.put("PBLCN_INST_NM", map.get("MAIN_ENFC_INST_NM").replaceAll(" ", "")); // 발행기관명
			insertMap.put("OUTC_SE_CD", "2"); // 성과구분코드(2:성과)
			insertMap.put("DEL_YN", "N"); // 삭제여부
			insertMap.put("FRST_RGTR_ID", sUserId);
			insertMap.put("LAST_MDFR_ID", sUserId);

			// SEA260 INSERT
			trprInqMapper.insertTrprQlfcInfo(insertMap);

			insertMap.put("DATAA_CHG_SE_CD", "I");

			// SEA261 INSERT
			trprInqMapper.insertTrprQlfcInfoHistory(insertMap);
		}
	}

	@Override
	public List<Map<String, Object>> selectOutcMainList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		String trprNm = null;
		String picNm = null;
		String taskwkSeCd = null;
		String usrId = null;

		if (parameterGroup != null) {
			trprNm = parameterGroup.getValue("TRPR_NM_ENCPT"); // 대상자성명
			picNm = parameterGroup.getValue("PIC_NM_ENCPT"); // 담당자성명
			taskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD").replaceAll(",", ""); // 단위업무구분코드
			usrId = parameterGroup.getValue("USER_ID"); // 접수담당자

		}

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		if (taskwkSeCd != null)
			paramMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		if (usrId != null)
			paramMap.put("USER_ID", usrId);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		String cnt = drmgsCaseRegMapper.selectOutcMainListCount(paramMap2);
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		if(totCnt > 0) {
			rtnMap = drmgsCaseRegMapper.selectOutcMainList(paramMap2);
		}
		return rtnMap;
	}

	/**
	 * @Method명 : outcomeList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kang.Hwa.Young
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> dsSEC330(DataRequest dataRequest) throws Exception {
		ParameterGroup searchParam = dataRequest.getParameterGroup("dsSearch");
		List<Map<String, String>> searchInstNoList = searchParam.getAllRowList();
		List<String> list = new ArrayList<String>();
		String untTaskwkSeCd = "";
		for (Map<String, String> map : searchInstNoList) {
			list.add(map.get("PIC_INST_NO").toString());
			untTaskwkSeCd = map.get("UNT_TASKWK_SE_CD").toString();
		}
		Map<String, Object> searchParamMap = new HashMap<String, Object>();
		searchParamMap.put("INST_NO_LIST", list);
		searchParamMap.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
		return drmgsCaseRegMapper.selectSec330(searchParamMap);
	}
	
	@Override
	public Map<String, Object> selectOutcPagingList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		String trprNm = null;
		String picNm = null;
		String taskwkSeCd = null;
		String usrId = null;

		if (parameterGroup != null) {
			trprNm = parameterGroup.getValue("TRPR_NM_ENCPT"); // 대상자성명
			picNm = parameterGroup.getValue("PIC_NM_ENCPT"); // 담당자성명
			taskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD").replaceAll(",", ""); // 단위업무구분코드
			usrId = parameterGroup.getValue("USER_ID"); // 접수담당자

		}

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		if (taskwkSeCd != null)
			paramMap.put("UNT_TASKWK_SE_CD", taskwkSeCd);
		if (usrId != null)
			paramMap.put("USER_ID", usrId);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		ParameterGroup trprList = dataRequest.getParameterGroup("dsTrprList");
		List<Map<String, String>> trprInfoList = trprList.getAllRowList();
		List<String> list = new ArrayList<String>();
		for(Map<String, String> map : trprInfoList) {
			list.add(map.get("TRPR_INFO_NO"));
		}
		System.out.println("list = " + list);
		paramMap2.put("TRPR_NOS", list);
		
		String cnt = drmgsCaseRegMapper.selectOutcMainListCount(paramMap2);
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
			
			rtnMap = drmgsCaseRegMapper.selectOutcMainList(paramMap2);
		}
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		result.put("dsCaseInqList", rtnMap);
		result.put("dmPage", resPage);
		
		
		// 심사담당자명 복호화(2022.06.21 적용)처리
//		Map<String, Object> map = new HashMap<>();
//		for (int i = 0; i < rtnMap.toArray().length; i++) {
//			map = rtnMap.get(i);
//			map.put("TOT_CNT", cnt);
//
//			if (map.get("TRPR_NM_ENCPT") != null)
//				map.put("TRPR_NM_ENCPT", Masking.nameMasking(scpDb.scpDecB64(map.get("TRPR_NM_ENCPT").toString())));
//			if (map.get("TRPR_BRTH_YMD") != null)
//				map.put("TRPR_BRTH_YMD", Masking.birthMaskingDay(map.get("TRPR_BRTH_YMD").toString()));
//			if (map.get("PIC_NM_ENCPT") != null)
//				map.put("PIC_NM_ENCPT", Masking.nameMasking(scpDb.scpDecB64(map.get("PIC_NM_ENCPT").toString())));
//			rtnMap.set(i, map);
//		}

//		for (int iCnt = 0; iCnt < rtnMap.size(); iCnt++) {
//
//			// 승인자명암복호화
//			String sNmEncpt3 = String.valueOf(rtnMap.get(iCnt).get("AUTZR_NM_ENCPT"));
//			if (sNmEncpt3 == null || sNmEncpt3.equals("null") || sNmEncpt3.equals(""))
//				sNmEncpt3 = "";
//
//			if ((sNmEncpt3.length() >= 24) && sNmEncpt3 != null) {
//				String sNmDecpt = Masking.nameMasking(scpDb.scpDecB64(sNmEncpt3));
//				rtnMap.get(iCnt).put("AUTZR_NM", sNmDecpt);
//			} else {
//				if (sNmEncpt3.length() > 0) {
//					rtnMap.get(iCnt).put("AUTZR_NM", sNmEncpt3);
//				}
//			}
//		}

		return result;
	}
	
	@Override
	public Map<String, Object> selectOutcTrprList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParam = parameterGroup.getSingleValueMap();
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>(searchParam);

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		/* 페이징 건수 조회*/
		int trprCnt = trprInqMapper.selectTrprInqListCount(paramMap2);
		paramMap2.put("TOT_CNT", trprCnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = trprCnt;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(trprCnt > 0) {
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			// 대상자목록 returnList
			rtnMap = trprInqMapper.selectTrprInqList(paramMap2);
		}
		
		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);	
		
		result.put("dsAddress", rtnMap);
		result.put("dmPage", pageMap);
		return result;
	}
}
