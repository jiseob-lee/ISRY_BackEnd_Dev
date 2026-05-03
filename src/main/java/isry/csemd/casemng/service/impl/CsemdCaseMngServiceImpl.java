/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.casemng.service.impl;

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
import isry.csemd.casemng.mapper.CsemdCaseMngMapper;
import isry.csemd.casemng.service.CsemdCaseMngService;
import isry.csemd.mngrpage.aplcnttrprdtlinfomng.mapper.AplcntTrprDtlInfoMngMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

/**
 * @파일명 : CsemdCaseMngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seung.Yeon
 * @작성일 : 2022. 9. 13.
 * @수정자 : Lee.Seung.Yeon
 * @수정일 : 2022. 9. 13.
 * @수정내용 : - -
 */
@Service("csemdCaseMngService")
public class CsemdCaseMngServiceImpl extends IsryBaseServiceImpl implements CsemdCaseMngService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

	@Resource(name = "csemdCaseMngMapper")
	private CsemdCaseMngMapper csemdCaseMngMapper;

	@Resource(name = "csemdMngrPageAplcntTrprDtlInfoMngMapper")
	private AplcntTrprDtlInfoMngMapper csemdMngrPageAplcntTrprDtlInfoMngMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * 사례관리_계획 상세조회
	 * 
	 * @Method명 : selectCaseMngPlanDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 9. 13.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCaseMngPlanDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> rtn = csemdCaseMngMapper.selectCaseMngPlanDetail(paramMap);

		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < rtn.toArray().length; i++) {
			map = rtn.get(i);

			if (map.get("APRV_PIC_NM") != null) {
				map.put("APRV_PIC_NM", Masking.nameMasking(map.get("APRV_PIC_NM").toString()));
			}
			if (map.get("PIC_NM") != null) {
				map.put("PIC_NM", Masking.nameMasking(map.get("PIC_NM").toString()));
			}

			rtn.set(i, map);
		}

		return rtn;

	}

	/**
	 * 사례관리_계획 상세정보 저장
	 * 
	 * @Method명 : saveCaseMngPlanDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 9. 13.
	 * @Method설명 :
	 */
	@Override
	public int saveCaseMngPlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup dataParam = dataRequest.getParameterGroup("dsIndivPlan");

		int rslt = 0;

		// 1. 등록 이벤트
		Iterator<ParameterRow> insertedRows = dataParam.getInsertedRows();
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("USER_ID", sUserId);

			rslt = csemdCaseMngMapper.insertAFA410(mapIns);
		}

		// 2. 수정 이벤트
		Iterator<ParameterRow> updatedRowList = dataParam.getUpdatedRows();
		while (updatedRowList.hasNext()) {

			Map<String, String> mapUpd = updatedRowList.next().toMap();
			mapUpd.put("USER_ID", sUserId);

			rslt = csemdCaseMngMapper.updateAFA410(mapUpd);
		}

		return rslt;
	}

	/**
	 * 약물복용 정보 조회
	 * 
	 * @Method명 : selectDrfstfTakngInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDrfstfTakngInfo(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> rtn = csemdCaseMngMapper.selectDrfstfTakngInfo(paramMap);

		return rtn;
	}

	/**
	 * 사례관리_등록 상세정보 저장
	 * 
	 * @Method명 : saveCaseMngRegDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveCaseMngRegDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

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

		ParameterGroup dataParam = dataRequest.getParameterGroup("dsDrfstfTakngInfo");
//		List<Map<String, String>> dsDrfstfTakngInfo = dataParam.getAllRowList();
//		if(dsDrfstfTakngInfo.size() > 0) {
//			Map<String, String> mapDrfstfTakng = dsDrfstfTakngInfo.get(0);
//			sCaseMngNo	  = mapDrfstfTakng.get("CASE_MNG_NO");
//			sCaseMngOdrno = mapDrfstfTakng.get("CASE_MNG_ODRNO");
//		}

		if (sCaseMngNo.isEmpty())
			sCaseMngNo = String.valueOf(info.get("CASE_MNG_NO"));
		if (sCaseMngOdrno.isEmpty())
			sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

		// 2.대상자문제상태내역(AFA120)
		LOGGER.debug("================= 대상자문제상태내역(AFA120) 저장 START =================");
		Iterator<ParameterRow> allRowList = dataParam.getAllRows();
		while (allRowList.hasNext()) {
			ParameterRow row = allRowList.next();
			Map<String, String> mapUpd = row.toMap();

			if (mapUpd.get("CASE_MNG_NO").equals("") || row.getState() == RowState.UPDATED) {
				mapUpd.put("CASE_MNG_NO", sCaseMngNo);
				mapUpd.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				mapUpd.put("FRST_RGTR_ID", sUserId);
				mapUpd.put("LAST_MDFR_ID", sUserId);

				// 2.1. AFA120 수정
				csemdMngrPageAplcntTrprDtlInfoMngMapper.updatePtcptReqstdAplcntPop(mapUpd);
				// 2.2. AFA121 등록
				csemdMngrPageAplcntTrprDtlInfoMngMapper.insertPtcptReqstdAplcntPopHstr(mapUpd);
			}
		}
		LOGGER.debug("================= 대상자문제상태내역(AFA120) 저장 END =================");

		// 3.설문지발송이력(SBB600)
		LOGGER.debug("================= 설문지발송이력(SBB600) 수정 START =================");
		ParameterGroup detailInfo = dataRequest.getParameterGroup("dsDetailInfo");
		
		Map<String, String> mapAll = detailInfo.getSingleValueMap();
		List<Map<String, Object>> qustnbList = csemdCaseMngMapper.selectQustnbSndngHstr(mapAll);
		for (Map<String, Object> map : qustnbList) {
			
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("LAST_MDFR_ID", sUserId);
			
			csemdCaseMngMapper.updateSBB600(map);
		}
		LOGGER.debug("================= 설문지발송이력(SBB600) 수정 END =================");

		Map<String, String> rtnMap = new HashMap<>();
		rtnMap.put("CASE_MNG_NO", sCaseMngNo);
		rtnMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

		return rtnMap;
	}
}
