/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.outsdsrvyptcptn.service.OutsdSrvyPtcptnService;
import isry.itgcms.syscmmn.survsht.mapper.SurvshtMmnMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;

/**
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 :
 * @수정일 :
 * @수정내용 : - -
 */
@Service("survshtMmnService")
public class SurvshtMmnServiceImpl extends IsryBaseServiceImpl implements SurvshtMmnService {

	@Resource(name = "survshtMmnMapper")
	private SurvshtMmnMapper survshtMmnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	// 설문지 문자 SHOT CUT URL 생성 및 문자 내용 조합 Service Class
	@Resource(name = "outsdSrvyPtcptnService")
	private OutsdSrvyPtcptnService outsdSrvyPtcptnService;

	ScpDb  scpDb   = new ScpDb();
	//Masking mask   = new Masking();

	/**
	 * @Method명 : selectSurvshtListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 목록 totalCount조회
	 */
	@Override
	public Integer selectSurvshtListTotalCount(Map<String, Object> map) throws Exception {

		return survshtMmnMapper.selectSurvshtListTotalCount(map);
	}

	/**
	 * @Method명 : selectSurvshtList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectSurvshtList(Map<String, Object> map) throws Exception {

		return survshtMmnMapper.selectSurvshtList(map);
	}

	/**
	 * @Method명 : saveSurvsht
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(CUD)
	 */
	@Override
	public void saveSurvsht(DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();

		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			survshtMmnMapper.deleteSurvsht(mapDel);
		}
	}

	/**
	 * @Method명 : insertSurvsht
	 * @param dmSaveMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	@Override
	public void insertSurvsht(Map<String, Object> dmSaveMap) throws Exception {

		survshtMmnMapper.insertSurvsht(dmSaveMap);
		survshtMmnMapper.insertSurvsht2(dmSaveMap);

	}

	/**
	 * @Method명 : updateSurvsht
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@Override
	public void updateSurvsht(Map<String, Object> dmUpdateMap) throws Exception {

		survshtMmnMapper.updateSurvsht(dmUpdateMap);
	}

	/**
	 * @Method명 : selectQesitmListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 목록 totalCount조회
	 */
	@Override
	public Integer selectQesitmListTotalCount(Map<String, Object> map) throws Exception {

		return survshtMmnMapper.selectQesitmListTotalCount(map);
	}

	/**
	 * @Method명 : selectQesitmList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectQesitmList(Map<String, Object> map) throws Exception {

		return survshtMmnMapper.selectQesitmList(map);
	}

	/**
	 * @Method명 : selectQesitm
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항내용 조회
	 */
	@Override
	public List<Map<String, Object>> selectQesitm(Map<String, String> map) throws Exception {

		return survshtMmnMapper.selectQesitm(map);
	}

	/**
	 * @Method명 : selectQesitmExmplList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항보기 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectQesitmExmplList(Map<String, String> map) throws Exception {

		return survshtMmnMapper.selectQesitmExmplList(map);
	}

	/**
	 * @Method명 : saveQesitmMng
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 저장
	 */
	@Override
	public void saveQesitmMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapReturn = new HashMap<String, Object>();

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String qesitmNo = "";
		Map<String, String> mngNoMap = new HashMap<String, String>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		// 문항내용 저장
		ParameterGroup ds1 = dataRequest.getParameterGroup("ds1");

		ParameterRow pRow = ds1.get(0);

		if (pRow.getValue("QESITM_MNG_NO").equals("")) {

			//설문문항 관리번호생성
			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "QN");
			qesitmNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

			//설문문항 등록
			Map<String, String> mapIns = pRow.toMap();
			mapIns.put("QESITM_MNG_NO", qesitmNo);
			mapIns.put("USER_ID", userId);
			survshtMmnMapper.insertQesitm(mapIns);
		} else {
			//설문문항 수정
			Map<String, String> mapUpd = pRow.toMap();
			mapUpd.put("USER_ID", userId);
			survshtMmnMapper.updateQesitm(mapUpd);
			qesitmNo = pRow.getValue("QESITM_MNG_NO");
		}

		// 문항보기 목록 저장
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		Iterator<ParameterRow> insertedDsRows = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedDsRows = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedDsRows = dsList.getDeletedRows();

		while (insertedDsRows.hasNext()) {

			Map<String, String> mapIns = insertedDsRows.next().toMap();
			mapIns.put("QESITM_MNG_NO", qesitmNo);
			mapIns.put("USER_ID", userId);
			survshtMmnMapper.insertQesitmExmpl(mapIns);

			// 자원번호 key값 셋팅
			mapReturn.put("RESRCE_NO", mapIns.get("RESRCE_NO"));
		}

		while (updatedDsRows.hasNext()) {

			Map<String, String> mapUpd = updatedDsRows.next().toMap();
			mapUpd.put("QESITM_MNG_NO", qesitmNo);
			mapUpd.put("USER_ID", userId);
			survshtMmnMapper.updateQesitmExmpl(mapUpd);

			// 자원번호 key값 셋팅
			mapReturn.put("RESRCE_NO", mapUpd.get("RESRCE_NO"));
		}

		// delete 동작 필요시 구현
		while (deletedDsRows.hasNext()) {

			Map<String, String> mapDel = deletedDsRows.next().toMap();
			mapDel.put("QESITM_MNG_NO", qesitmNo);
			mapDel.put("USER_ID", userId);
			survshtMmnMapper.deleteQesitmExmpl(mapDel);

		}

	}

	@Override
	public Map<String, Object> deleteQesitmMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		ParameterGroup ds1 = dataRequest.getParameterGroup("dmSearch");

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			paramMap.put("LOGIN_ID", loginVO.getId());
		}

		paramMap.put("QESITM_MNG_NO", ds1.getValue("QESITM_MNG_NO"));

		// 기 사용 여부 체크 (맵핑 여부 확인)
		int cnt = survshtMmnMapper.selectQesitmMngUseCnt(paramMap);

		if(cnt > 0 ) { // 사용중인 문항일 경우 로직 종료
			result.put("RETURN_VALUE", "F"); // 이미 사용중인 데이터
		} else { // 미사용중일 경우 삭제 처리
			survshtMmnMapper.deleteQesitmMng(paramMap);

			result.put("QESITM_MNG_NO", ds1.getValue("QESITM_MNG_NO"));
			result.put("RETURN_VALUE", "P");
		}
		return result;
	}

	@Override
	public Map<String, Object> saveQustnbMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		ParameterGroup dmDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String userId = "";
		String userNm = "";
		String taskwkSeCd = "";// TASKWK_SYS_SE_CD
		String qustnbNo = "";
		String qustnbTmptNo = "";
		String groupNo = ""; // 설문 그룹
		Integer authMenuNo = 0;

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			paramMap.put("LOGIN_ID", loginVO.getId());
			userId = loginVO.getId();
			userNm = loginVO.getUserName();

			//Task
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		String[] trprArr = dmDtl.getValue("TRPR_INFO_NO").split("_");
		Map<String, String> trprMap = new HashMap<String, String>();
		// CASE_MNG_NO || '_' || A.CASE_MNG_ODRNO || '_' || B.TRPR_NM

		trprMap.put("CASE_MNG_NO", trprArr[0]);
		trprMap.put("CASE_MNG_ODRNO", trprArr[1]);
		trprMap.put("CASE_TRPR_NM_ENCPT", trprArr[2]);

		if ("INSERT".equals(dmDtl.getValue("TYPE"))) {
			Map<String, String> mngNoMap = new HashMap<String, String>();

			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "SN");
			qustnbNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "TM");
			qustnbTmptNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

			//설문지 등록(정보등록)
			Map<String, Object> survPram = new HashMap<String, Object>();

			survPram.put("QUSTNB_MNG_NO", qustnbNo);
			survPram.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo);
			survPram.put("QUSTNB_NM", dmDtl.getValue("QUSTNB_NM")); // 설문지명
			survPram.put("SRVY_PURPS_CN", dmDtl.getValue("SRVY_PURPS_CN")); // 설문목적내용
			survPram.put("SRVY_WRT_GUIDAN_CN", dmDtl.getValue("SRVY_WRT_GUIDAN_CN")); // 설문작성안내내용
			survPram.put("SRVY_BGNG_YMD", dmDtl.getValue("SRVY_BGNG_YMD")); // 설문시작일자
			survPram.put("SRVY_END_YMD", dmDtl.getValue("SRVY_END_YMD")); // 설문종료일자
			survPram.put("WRTR_ID", userId); // 작성자 아이디
			survPram.put("WRTR_NM", userNm); // 작성자 명
			// 정의 안된거
			Map<String, Object> trprInfo = survshtMmnMapper.selectTrprInfoDtl(trprMap);
			survPram.put("SRVY_TRGT_SE_CD", trprInfo.get("CASE_TRPR_TYPE_SE_CD")); // 설문대상 구분 코드 SRVY_TRGT_SE_CD
			survPram.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.
			survPram.put("SRVY_PRGRS_STTS_SE_CD", trprInfo.get("CASE_PRGRS_STTS_SE_CD")); // 설문진행상태구분코드 SRVY_PRGRS_STTS_SE_CD 명

			// 설문지 등록
			survshtMmnMapper.insertQustnbMng(survPram);

			trprInfo.put("QUSTNB_MNG_NO", qustnbNo);
			trprInfo.put("USER_ID", userId);
			trprInfo.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo); // 삭제 필요.
			survshtMmnMapper.insertCaseMngTrprInfo(trprInfo);

			// 그룹관련 정보 가지고 있기.
			// 설문지 관리 번호 (공용), 설문지 영역관리번호 QUSTNB_RELM_MNG_NO
			// 영역고유명, 시작문항번호, 종료문항번호
			List<Map<String, Object>> relmList = new ArrayList<Map<String, Object>>();
			int strNo = 0;
			int endNo = 0;
			for (int i=0;i<dsList.rowSize();i++) {
				String relmNo = dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"); //
				String nextNo = "";
				if (i != dsList.rowSize()-1) {
					nextNo = dsList.get(i+1).getValue("QUSTNB_RELM_MNG_NO");
					// nextNo 가 다를 경우 리스트에 담는다 종결한다.
					if(!relmNo.equals(nextNo)) {
						Map<String, Object> relm = new HashMap<String, Object>();
						endNo = i;
						relm.put("QUSTNB_RELM_MNG_NO", dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"));
						relm.put("QUSTNB_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_RELM_ESNTAL_NM"));
						relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
						relm.put("QUSTNB_END_QESITM_NO", endNo);
						relm.put("USER_ID", userId);
						relmList.add(relm);
						strNo = i+1;
					}
				}else { // i 의 마지막이면
					nextNo = dsList.get(i).getValue("QUSTNB_RELM_MNG_NO");
					Map<String, Object> relm = new HashMap<String, Object>();
					endNo = i;
					relm.put("QUSTNB_RELM_MNG_NO", dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"));
					relm.put("QUSTNB_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_RELM_ESNTAL_NM"));
					relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
					relm.put("QUSTNB_END_QESITM_NO", endNo);
					relm.put("USER_ID", userId);
					relmList.add(relm);
					// strNo = strNo+1;
				}
			}
			// 설문영역성정 등록
			for (int i=0;i<relmList.size();i++) {
				Map<String, String> groupMap = new HashMap<String, String>();

				groupMap.put("USER_ID", userId);
				groupMap.put("SYS_CD", "SA");
				groupNo = survshtMmnMapper.selectSysSeCd(groupMap);
				Map<String, Object> relm = new HashMap<String, Object>();
				relm = relmList.get(i);
				relm.put("QUSTNB_MNG_NO", qustnbNo);
				relm.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo);
				relm.put("QUSTNB_RELM_MNG_NO", groupNo);
				survshtMmnMapper.insertQustnbRelmMng(relm);
			}

			// 설문지 문항 등록 SBB300
			for (int i=0;i<dsList.rowSize();i++) {
				Map<String, Object> survDtlParam = new HashMap<String, Object>();

				survDtlParam.put("QUSTNB_MNG_NO", qustnbNo);
				survDtlParam.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo);
				survDtlParam.put("USER_ID", userId);
				survDtlParam.put("QESITM_SQNCE", i);
				survDtlParam.put("QESITM_MNG_NO", dsList.get(i).getValue("QESITM_MNG_NO"));
				survDtlParam.put("CHTY_QESITM_YN",  dsList.get(i).getValue("CHTY_QESITM_YN"));
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  dsList.get(i).getValue("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택에 연결될 문항 키값.
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  dsList.get(i).getValue("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택형 부모 KEY

				// 설문지 문항 등록
				survshtMmnMapper.insertQustnbMngList(survDtlParam);
			}
			result.put("dmSrvyDtl", survPram);

		} else if ("MODIFY".equals(dmDtl.getValue("TYPE"))) {
			//설문지 수정(정보수정)
			Map<String, Object> survPram = new HashMap<String, Object>();
			qustnbNo = dmDtl.getValue("QUSTNB_MNG_NO");
			qustnbTmptNo = dmDtl.getValue("QUSTNB_TMPT_MNG_NO");
			survPram.put("QUSTNB_MNG_NO", qustnbNo);
			survPram.put("QUSTNB_NM", dmDtl.getValue("QUSTNB_NM")); // 설문지명
			survPram.put("SRVY_PURPS_CN", dmDtl.getValue("SRVY_PURPS_CN")); // 설문목적내용
			survPram.put("SRVY_WRT_GUIDAN_CN", dmDtl.getValue("SRVY_WRT_GUIDAN_CN")); // 설문작성안내내용
			survPram.put("SRVY_BGNG_YMD", dmDtl.getValue("SRVY_BGNG_YMD")); // 설문시작일자
			survPram.put("SRVY_END_YMD", dmDtl.getValue("SRVY_END_YMD")); // 설문종료일자
			survPram.put("USER_ID", userId); // 작성자 아이디
			survPram.put("TRPR_INFO_NO", dmDtl.getValue("TRPR_INFO_NO")); // 작성자 아이디
//			survPram.put("WRTR_NM", userNm); // 작성자 명
			// 정의 안된거

			Map<String, Object> trprInfo = survshtMmnMapper.selectTrprInfoDtl(trprMap);
			survPram.put("SRVY_TRGT_SE_CD", trprInfo.get("CASE_TRPR_TYPE_SE_CD")); // 설문대상 구분 코드 SRVY_TRGT_SE_CD
			survPram.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.
			survPram.put("SRVY_PRGRS_STTS_SE_CD", trprInfo.get("CASE_PRGRS_STTS_SE_CD")); // 설문진행상태구분코드 SRVY_PRGRS_STTS_SE_CD 명

			// 설문지 수정
			survshtMmnMapper.updateQustnbMng(survPram);

			trprInfo.put("QUSTNB_MNG_NO", qustnbNo);
			survshtMmnMapper.deleteCaseMngTrprInfo(trprInfo);
			trprInfo.put("USER_ID", userId);
			trprInfo.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo);

			survshtMmnMapper.insertCaseMngTrprInfo(trprInfo);

			// 그룹관련 정보 가지고 있기.
			// 설문지 관리 번호 (공용), 설문지 영역관리번호 QUSTNB_RELM_MNG_NO
			// 영역고유명, 시작문항번호, 종료문항번호
			List<Map<String, Object>> relmList = new ArrayList<Map<String, Object>>();
			int strNo = 0;
			int endNo = 0;
			for (int i=0;i<dsList.rowSize();i++) {
				String relmNo = dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"); //
				String nextNo = "";
				if (i != dsList.rowSize()-1) {
					nextNo = dsList.get(i+1).getValue("QUSTNB_RELM_MNG_NO");
					// nextNo 가 다를 경우 리스트에 담는다 종결한다.
					if(!relmNo.equals(nextNo)) {
						Map<String, Object> relm = new HashMap<String, Object>();
						endNo = i;
						relm.put("QUSTNB_RELM_MNG_NO", dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"));
						relm.put("QUSTNB_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_RELM_ESNTAL_NM"));
						relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
						relm.put("QUSTNB_END_QESITM_NO", endNo);
						relm.put("USER_ID", userId);
						relmList.add(relm);
						strNo = i+1;
					}
				}else { // i 의 마지막이면
					nextNo = dsList.get(i).getValue("QUSTNB_RELM_MNG_NO");
					Map<String, Object> relm = new HashMap<String, Object>();
					endNo = i;
					relm.put("QUSTNB_RELM_MNG_NO", dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"));
					relm.put("QUSTNB_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_RELM_ESNTAL_NM"));
					relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
					relm.put("QUSTNB_END_QESITM_NO", endNo);
					relm.put("USER_ID", userId);
					relmList.add(relm);
					// strNo = strNo+1;
				}
			}
			// 기존 그룹 정보 삭제
			survshtMmnMapper.deleteQustnbRelmMng(survPram);

			// 설문영역성정 등록
			for (int i=0;i<relmList.size();i++) {

				Map<String, String> groupMap = new HashMap<String, String>();
				groupMap.put("USER_ID", userId);
				groupMap.put("SYS_CD", "SA");
				groupNo = survshtMmnMapper.selectSysSeCd(groupMap);

				Map<String, Object> relm = new HashMap<String, Object>();
				relm = relmList.get(i);
				relm.put("QUSTNB_MNG_NO", qustnbNo);
				relm.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo);
				relm.put("QUSTNB_RELM_MNG_NO", groupNo);
				survshtMmnMapper.insertQustnbRelmMng(relm);
			}
			// 기존 설문지 문항 삭제
			survshtMmnMapper.deleteQustnbMng(survPram);

			// 설문지 문항 등록 SBB300
			for (int i=0;i<dsList.rowSize();i++) {
				Map<String, Object> survDtlParam = new HashMap<String, Object>();

				survDtlParam.put("QUSTNB_MNG_NO", qustnbNo);
				survDtlParam.put("QUSTNB_TMPT_MNG_NO", qustnbTmptNo);
				survDtlParam.put("USER_ID", userId);
				survDtlParam.put("QESITM_SQNCE", i);
				survDtlParam.put("QESITM_MNG_NO", dsList.get(i).getValue("QESITM_MNG_NO"));
				survDtlParam.put("CHTY_QESITM_YN",  dsList.get(i).getValue("CHTY_QESITM_YN"));
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  dsList.get(i).getValue("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택에 연결될 문항 키값.
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  dsList.get(i).getValue("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택형 부모 KEY

				// 설문지 문항 등록
				survshtMmnMapper.insertQustnbMngList(survDtlParam);
			}
			result.put("dmSrvyDtl", survPram);
		}
		Map<String, Object> returnParam = new HashMap<String, Object>();
		returnParam.put("QUSTNB_MNG_NO", qustnbNo);
		// result.put("dmSrvyDtl", returnParam);
		return result;
	}

	public Map<String, Object> selectQesitmQustnbMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSrvyDtl");
		int pageIdx = 1;
		int rowSize = 9999;
		int startIndex = 0;
		Integer totalCount = 0;
		Map<String, Object> dmSearchMap = new HashMap<>();

		dmSearchMap.put("QUSTNB_MNG_NO", dmSearchParam.getValue("QUSTNB_MNG_NO"));

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		totalCount = survshtMmnMapper.selectQesitmQustnbMngListTotalCount(dmSearchMap);

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = survshtMmnMapper.selectQesitmQustnbMngList(dmSearchMap);

		// 영역설정 가져오기
		List<Map<String, Object>> qustnbList = survshtMmnMapper.selectQesitmQustnbCheckList(dmSearchMap);

		int strCnt = 0;
		int endCnt = 0;
		int targetCnt = 0;
		//가공된 데이터 넣기
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		if (qustnbList != null && listBoard != null) {
			for (int i=0;i<listBoard.size();i++) {
				Map<String, Object> listMap = listBoard.get(i);
				targetCnt = Integer.valueOf(String.valueOf(listMap.get("QESITM_SQNCE")));
				for (int j=0;j<qustnbList.size();j++) {
					Map<String, Object> qustnbMap = qustnbList.get(j);

					strCnt = Integer.valueOf(String.valueOf( qustnbMap.get("QUSTNB_BGNG_QESITM_NO")));
					endCnt = Integer.valueOf(String.valueOf( qustnbMap.get("QUSTNB_END_QESITM_NO")));

					if ((strCnt <= targetCnt) && (targetCnt <= endCnt)) {
						listMap.put("QUSTNB_RELM_MNG_NO", (String) qustnbMap.get("QUSTNB_RELM_MNG_NO"));
						listMap.put("QUSTNB_RELM_ESNTAL_NM", (String) qustnbMap.get("QUSTNB_RELM_ESNTAL_NM"));
						listMap.put("QUSTNB_BGNG_QESITM_NO", String.valueOf(qustnbMap.get("QUSTNB_BGNG_QESITM_NO")));
						listMap.put("QUSTNB_END_QESITM_NO", String.valueOf(qustnbMap.get("QUSTNB_END_QESITM_NO")));
						list.add(listMap);
					}
				}
			}
		}
		List<Map<String, Object>> dsResultCrtrList = survshtMmnMapper.selectResultCrtrList(dmSearchMap);
		List<Map<String, Object>> dsGrdngRelmList = survshtMmnMapper.selectQuestnbRelmBaseList(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dsList2", qustnbList);
		result.put("dsResultCrtrList", dsResultCrtrList);
		result.put("dsGrdngRelmList", dsGrdngRelmList);
		result.put("dmPage", resPage);
		return result;
	}

	public Map<String, Object> selectQuestnbRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> dmMap = new HashMap<String, Object>();

		dmMap.put("QUSTNB_MNG_NO", dmSearchParam.getValue("QUSTNB_MNG_NO"));

		int questnbRelmCnt = survshtMmnMapper.selectQuestnbRelmCnt(dmMap);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (0 == questnbRelmCnt) {
			// 설정을 저장하지 않았을 경우 설문 등록시 저장한 영역을 기준을 베이스로 기본 영역을 설정해준다.
			list = survshtMmnMapper.selectQuestnbRelmBaseList(dmMap);
		} else {
			// 설정이 저장 되어있다면 저장된 설정을 기준으로 데이터를 가져온다.
			list = survshtMmnMapper.selectQuestnbRelmList(dmMap);
		}
		int pageIdx = 1;
		int rowSize = 9999;
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);

		return result;
	}

	public Map<String, Object> saveQuestnbRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> saveMap = new HashMap<String, Object>();
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		saveMap.put("USER_ID", userId);
		saveMap.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));
		saveMap.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));

		// 저장의 기준은 일괄 삭제 후 재 등록
		// 기존 채점영역 설정 값을 일괄 삭제한다.
		survshtMmnMapper.deleteQuestnbRelmList(saveMap);
		String qustnbNo = "";

		if(dsList != null) {
			for (int i=0;i<dsList.rowSize();i++) {
				Map<String, String> mngNoMap = new HashMap<String, String>();

				mngNoMap.put("USER_ID", userId);
				mngNoMap.put("SYS_CD", "SG");
				qustnbNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

				Map<String, Object> param = new HashMap<String, Object>();

				param.put("QUSTNB_GRDNG_RELM_MNG_NO", qustnbNo);
				param.put("QUSTNB_MNG_NO", dsList.get(i).getValue("QUSTNB_MNG_NO"));
				param.put("QUSTNB_TMPT_MNG_NO", dsList.get(i).getValue("QUSTNB_TMPT_MNG_NO"));
				param.put("QUSTNB_GRDNG_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_GRDNG_RELM_ESNTAL_NM"));
				param.put("QUSTNB_BGNG_QESITM_NO", dsList.get(i).getValue("QUSTNB_BGNG_QESITM_NO"));
				param.put("QUSTNB_END_QESITM_NO", dsList.get(i).getValue("QUSTNB_END_QESITM_NO"));
				param.put("GRDNG_CRTR_SE_CD", dsList.get(i).getValue("GRDNG_CRTR_SE_CD"));
				param.put("USER_ID", userId);

				survshtMmnMapper.insertQustnbGrdngList(param);
			}

		}

		return result;
	}

	public Map<String, Object> selectResultCrtrList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));

		//채점기준 리스트 가져오기
		List<Map<String, Object>> list = survshtMmnMapper.selectResultCrtrList(param);

		int pageIdx = 1;
		int rowSize = 9999;
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);
		return result;
	}

	public Map<String, Object> selectResultQustnbRelm(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));

		//채점기준 리스트 가져오기
		List<Map<String, Object>> list = survshtMmnMapper.selectResultQustnbRelm(param);

		int pageIdx = 1;
		int rowSize = 9999;
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);

		return result;
	}

	public Map<String, Object> saveResultCrtrList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		Map<String, Object> param = new HashMap<String, Object>();

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String grdngNo = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		param.put("USER_ID", userId);
		param.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));
		param.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));
		log.debug("rowSize : " + dsList.rowSize());
		log.debug("colSize : " + dsList.colSize());

		//기존 목록 삭제
		survshtMmnMapper.deleteResultCrtr(param);
		for (int i=0;i<dsList.rowSize();i++) {
			Map<String, String> rowMap = dsList.get(i).toMap();
			rowMap.put("USER_ID", userId);
			rowMap.put("SYS_CD", "AE"); //채점영역 기준 설정
			grdngNo = survshtMmnMapper.selectSysSeCd(rowMap);

			rowMap.put("GRDNG_RESULT_CRTR_MNG_NO", grdngNo);

			log.debug(rowMap.toString());
			survshtMmnMapper.insertResultCrtrList(rowMap);
		}
//		Map<String, String> mngNoMap = new HashMap<String, String>();
//		QUSTNB_RELM_MNG_NO
//		mngNoMap.put("USER_ID", userId);
//		mngNoMap.put("SYS_CD", "AE"); 채점영역 기준 설정
//		grdngNo = survshtMmnMapper.selectSysSeCd(mngNoMap);


		return result;
	}


	public Map<String, Object> selectPreSurvshtInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
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


		Map<String, Object> dmQustnbMngInfo = new HashMap<String, Object>();
		int qesitmCnt = 0;
		String tmptYn = dmSearch.getValue("TMPT_YN");

		log.debug("dmSearch.getValue(\"TMPT_YN\") = " + dmSearch.getValue("TMPT_YN"));
		log.debug("tmptYn = " + tmptYn);

		if(tmptYn.equals("Y")) {

			param.put("QUSTNB_TMPT_MNG_NO", dmSearch.getValue("QUSTNB_TMPT_MNG_NO"));

			dmQustnbMngInfo = survshtMmnMapper.selectQustnbMngTmptInfo(param);
			qesitmCnt = survshtMmnMapper.selectQesitmTmptCnt(param);
			dmQustnbMngInfo.put("QESITM_CNT", qesitmCnt);


			result.put("dmQustnbMngInfo", dmQustnbMngInfo);

		}else{

			param.put("QUSTNB_MNG_NO", dmSearch.getValue("QUSTNB_MNG_NO"));

			dmQustnbMngInfo = survshtMmnMapper.selectQustnbMngInfo(param);
			qesitmCnt = survshtMmnMapper.selectQesitmCnt(param);

			dmQustnbMngInfo.put("QESITM_CNT", qesitmCnt);
			result.put("dmQustnbMngInfo", dmQustnbMngInfo);

		}


		return result;
	}

	public Map<String, Object> selectPreSurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		ParameterGroup dmQustnbMngInfo = dataRequest.getParameterGroup("dmQustnbMngInfo");
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		param.put("USER_ID", userId);


		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();

		String tmptPrevewYn = dmSearch.getValue("TMPT_YN");

		if(tmptPrevewYn.equals("Y")) {

			log.debug("##### 설문지 템플릿 미리보기 데이터 요청");

			param.put("QUSTNB_TMPT_MNG_NO", dmSearch.getValue("QUSTNB_TMPT_MNG_NO"));

			// 관리자에서 템플릿 미리보기인 경우

			list = survshtMmnMapper.selectPreSurvshtTmptList(param);

			list2 = survshtMmnMapper.selectPreSurvshtDtlTmptList(param);

			list3 = survshtMmnMapper.selectPreSurvshtDtlTmptRelmList(param);

			result.put("dsList", list);

		}else {

			log.debug("##### 설문지 미리보기 및 리얼 설문지 데이터 요청");

			param.put("QUSTNB_MNG_NO", dmQustnbMngInfo.getValue("QUSTNB_MNG_NO"));

			// 관리자에서 설문지 미리 보기 및 미리보기가 아닌 실제 발송된 설문지인 경우
			list = survshtMmnMapper.selectPreSurvshtList(param);

			list2 = survshtMmnMapper.selectPreSurvshtDtlList(param);

			list3 = survshtMmnMapper.selectPreSurvshtDtlRelmList(param);

			// 응답 여부 체크 누락   2022-10-25
			//String SRVY_RSPNS_MNG_NO = survshtMmnMapper.selectSurvshtSrvyRspnsMngNo(param);

			// 지속적인 오류 발생으로 인해 임시 조치 20230620 송태수
			String SRVY_RSPNS_MNG_NO = null;
			List<Map<String, String>> srvyRspnsMngNo = survshtMmnMapper.selectSurvshtSrvyRspnsMngNo2(param);
			if(srvyRspnsMngNo.size() != 0) {
				SRVY_RSPNS_MNG_NO = srvyRspnsMngNo.get(0).get("SRVY_RSPNS_MNG_NO");
			}

			param.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
			// 응답 정보 가져오기 추가.
			Map<String, Object> srvyRspnsInfo = survshtMmnMapper.selectSurvshtSrvyRspnsMngInfo(param);

			log.debug(SRVY_RSPNS_MNG_NO);
			List<Map<String, Object>> dsList = new ArrayList<Map<String, Object>>();

			if(null != SRVY_RSPNS_MNG_NO && !"".equals(SRVY_RSPNS_MNG_NO)) {
				param.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
				List<Map<String, Object>> chkList = survshtMmnMapper.selectChkSurvshtList(param);

				for(int i=0;i<list.size();i++) {
					Map<String, Object> listMap = list.get(i);

					String QESITM_MNG_NO = (String) listMap.get("QESITM_MNG_NO");
					for (int j=0;j<chkList.size();j++) {
						Map<String, Object> temp = chkList.get(j);
						String CHK_QESITM_MNG_NO = (String) temp.get("QESITM_MNG_NO");
						if (QESITM_MNG_NO.equals(CHK_QESITM_MNG_NO)) {
							listMap.put("QESITM_SINGL_RSPNS_VALUE", temp.get("QESITM_SINGL_RSPNS_VALUE"));
							listMap.put("QESITM_COMPNO_RSPNS_VALUE", temp.get("QESITM_COMPNO_RSPNS_VALUE"));
							listMap.put("QESITM_RSPNS_CN", temp.get("QESITM_RSPNS_CN"));
							listMap.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
							listMap.put("RSPNS_DT", srvyRspnsInfo.get("RSPNS_DT"));
							dsList.add(listMap);
						}
					}
				}
			} else {// 응답정보가 없을 경우.
				dsList = list;
			}

			result.put("srvyRspnsInfo", srvyRspnsInfo);
			result.put("dsList", dsList);
		}



		result.put("ds1", list2);
		result.put("ds2", list3);

		return result;
	}

	public List<Map<String, Object>> selectSrvyTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> result = survshtMmnMapper.selectSrvyTrprList();
//		for (int i=0;i<result.size();i++) {
//			String str = (String) result.get(i).get("TRPR_NM");
//			String arr[] = str.split("_");
//			if (arr.length > 1) {
//				String trprNm = Masking.nameMasking( scpDb.scpDecB64(arr[2]));
//				trprNm = arr[0]+"_"+arr[1]+"_"+trprNm;
//				result.get(i).put("TRPR_NM", trprNm);
//			}
//		}

		return result;
	}

	public Map<String, Object> processStatusQustnbMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> param = new HashMap<String, Object>();

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String grdngNo = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		param.put("USER_ID", userId);
		param.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));

		Map<String, Object> validation = survshtMmnMapper.selectStatusQustnbMng(param);

		Map<String, Object> dmReturn = new HashMap<String, Object>();

		if ("START".equals(dmSrvyDtl.getValue("TYPE"))) {
			// 시작일자 체크  END_DATE_CHK
			String dateChk = (String) validation.get("END_DATE_CHK");
			if ("PASS".equals(dateChk)) {
				param.put("SRVY_PRGRS_STTS_SE_CD", "02"); // 설문중 코드
				survshtMmnMapper.processStatusQustnbMng(param);
				// RESULT_TYPE 01: PASS 02: 종료일자 이슈 , 03: 응답 여부.
				dmReturn.put("RESULT_TYPE", "01");
			} else {
				// 종료일자가 시작을 누른 시점보다 이전일 일 경우 실패.
				dmReturn.put("RESULT_TYPE", "02");
			}
		} else {// 중지 여부 체크
			// 응답자 여부 체크 RSPNS_CHK
			String rspnsChk = (String) validation.get("RSPNS_CHK");
			if ("PASS".equals(rspnsChk)) {
				param.put("SRVY_PRGRS_STTS_SE_CD", "04"); // 중지 코드
				survshtMmnMapper.processStatusQustnbMng(param);
				// RESULT_TYPE 01: PASS 02: 종료일자 이슈 , 03: 대상자 응답 여부.
				dmReturn.put("RESULT_TYPE", "01");
			} else {
				dmReturn.put("RESULT_TYPE", "03");
			}
		}

		result.put("dmReturn", dmReturn);

		return result;
	}

	public Integer selectSurvshtTmptListTotalCount(Map<String, Object> map) throws Exception {
		return survshtMmnMapper.selectSurvshtTmptListTotalCount(map);
	}

	public List<Map<String, Object>> selectSurvshtTmptList(Map<String, Object> map) throws Exception {
		return survshtMmnMapper.selectSurvshtTmptList(map);
	}

	public Map<String, Object> selectQesitmQustnbTmptMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSrvyDtl");
		int pageIdx = 1;
		int rowSize = 9999;
		int startIndex = 0;
		Integer totalCount = 0;
		Map<String, Object> dmSearchMap = new HashMap<>();

		dmSearchMap.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getValue("QUSTNB_TMPT_MNG_NO"));
		Map<String, Object> dmSrvyDtl = survshtMmnMapper.selectSrvyDtlData(dmSearchMap);
		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		totalCount = survshtMmnMapper.selectQesitmQustnbMngListTmptTotalCount(dmSearchMap);

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = survshtMmnMapper.selectQesitmQustnbTmptMngList(dmSearchMap);

		// 영역설정 가져오기
		List<Map<String, Object>> qustnbList = survshtMmnMapper.selectQesitmQustnbTmptCheckList(dmSearchMap);

		int strCnt = 0;
		int endCnt = 0;
		int targetCnt = 0;
		//가공된 데이터 넣기
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (qustnbList != null && listBoard != null) {
			for (int i=0;i<listBoard.size();i++) {
				Map<String, Object> listMap = listBoard.get(i);
				String test = String.valueOf(listMap.get("QESITM_SQNCE"));
				targetCnt = Integer.parseInt(String.valueOf(listMap.get("QESITM_SQNCE")));
				for (int j=0;j<qustnbList.size();j++) {
					Map<String, Object> qustnbMap = qustnbList.get(j);

					strCnt = Integer.parseInt(String.valueOf(qustnbMap.get("QUSTNB_BGNG_QESITM_NO")));
					endCnt = Integer.parseInt(String.valueOf(qustnbMap.get("QUSTNB_END_QESITM_NO")));

					if ((strCnt <= targetCnt) && (targetCnt <= endCnt)) {
						listMap.put("QUSTNB_RELM_MNG_NO", (String) qustnbMap.get("QUSTNB_RELM_MNG_NO"));
						listMap.put("QUSTNB_RELM_ESNTAL_NM", (String) qustnbMap.get("QUSTNB_RELM_ESNTAL_NM"));
						listMap.put("QUSTNB_BGNG_QESITM_NO", String.valueOf(qustnbMap.get("QUSTNB_BGNG_QESITM_NO")));
						listMap.put("QUSTNB_END_QESITM_NO", String.valueOf(qustnbMap.get("QUSTNB_END_QESITM_NO")));
						listMap.put("MNGR_MNG_PRVSON_YN", String.valueOf(qustnbMap.get("MNGR_MNG_PRVSON_YN")));

						list.add(listMap);
					}
				}
			}
		}
		List<Map<String, Object>> dsResultCrtrList = survshtMmnMapper.selectResultCrtrTmptList(dmSearchMap);
		//List<Map<String, Object>> dsGrdngRelmList = survshtMmnMapper.selectQuestnbRelmBaseTmptList(dmSearchMap);
		List<Map<String, Object>> dsGrdngRelmList = survshtMmnMapper.selectQuestnbRelmTmptList(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dmSrvyDtl", dmSrvyDtl);

		result.put("dsList", list);
		result.put("dsList2", qustnbList);
		result.put("dsResultCrtrList", dsResultCrtrList);
		result.put("dsGrdngRelmList", dsGrdngRelmList);
		result.put("dmPage", resPage);
		return result;
	}

	public Map<String, Object> saveQustnbTmptMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		ParameterGroup dmDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String userId = "";
		String userNm = "";
		String taskwkSeCd = "";// TASKWK_SYS_SE_CD
		String qustnbNo = "";
		String groupNo = ""; // 설문 그룹
		Integer authMenuNo = 0;

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			paramMap.put("LOGIN_ID", loginVO.getId());
			userId = loginVO.getId();
			userNm = loginVO.getUserName();

			//Task
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
//		String[] trprArr = dmDtl.getValue("TRPR_INFO_NO").split("_");
//		Map<String, String> trprMap = new HashMap<String, String>();
//		// CASE_MNG_NO || '_' || A.CASE_MNG_ODRNO || '_' || B.TRPR_NM
//
//		trprMap.put("CASE_MNG_NO", trprArr[0]);
//		trprMap.put("CASE_MNG_ODRNO", trprArr[1]);
//		trprMap.put("CASE_TRPR_NM_ENCPT", trprArr[2]);

		if ("INSERT".equals(dmDtl.getValue("TYPE"))) {
			Map<String, String> mngNoMap = new HashMap<String, String>();

			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "TM");
			qustnbNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

			//설문지 등록(정보등록)
			Map<String, Object> survPram = new HashMap<String, Object>();

			survPram.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
			survPram.put("QUSTNB_TMPT_NM", dmDtl.getValue("QUSTNB_TMPT_NM")); // 설문지명
			survPram.put("QUSTNB_NM", dmDtl.getValue("QUSTNB_NM")); // 설문지명
			survPram.put("USE_YN", "N"); // 사용여부
			survPram.put("SRVY_PURPS_CN", dmDtl.getValue("SRVY_PURPS_CN")); // 설문목적내용
			survPram.put("SRVY_WRT_GUIDAN_CN", dmDtl.getValue("SRVY_WRT_GUIDAN_CN")); // 설문작성안내내용
			survPram.put("SRVY_BGNG_YMD", dmDtl.getValue("SRVY_BGNG_YMD")); // 설문시작일자
			survPram.put("SRVY_END_YMD", dmDtl.getValue("SRVY_END_YMD")); // 설문종료일자
			survPram.put("WRTR_ID", userId); // 작성자 아이디
			survPram.put("WRTR_NM", userNm); // 작성자 명

			// 템플릿에선 해당 데이터를 셋팅 하지 않는다.
			// 복사 시점 이 후 나 템플릿을 가져다 쓸때 사용.
//			Map<String, Object> trprInfo = survshtMmnMapper.selectTrprInfoDtl(trprMap);
			// survPram.put("SRVY_TRGT_SE_CD", trprInfo.get("CASE_TRPR_TYPE_SE_CD")); // 설문대상 구분 코드 SRVY_TRGT_SE_CD
			// survPram.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.
			// survPram.put("SRVY_PRGRS_STTS_SE_CD", trprInfo.get("CASE_PRGRS_STTS_SE_CD")); // 설문진행상태구분코드 SRVY_PRGRS_STTS_SE_CD 명
			survPram.put("SRVY_TRGT_SE_CD", ""); // 설문대상 구분 코드 SRVY_TRGT_SE_CD
			//survPram.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.
			survPram.put("UNT_TASKWK_SE_CD", dmDtl.getValue("UNT_TASKWK")); // 단위업무구분 코드 UNT_TASKWK_SE_CD (선택한 단위 업무 메뉴 구분 코드)

			survPram.put("SRVY_PRGRS_STTS_SE_CD", ""); // 설문진행상태구분코드 SRVY_PRGRS_STTS_SE_CD 명

			// 설문지 템플릿 등록 SBB000
			survshtMmnMapper.insertQustnbTmptMng(survPram);

//			trprInfo.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
//			trprInfo.put("USER_ID", userId);
			// 템플릿에선 대상을 삽입하지 않는다.
			//survshtMmnMapper.insertCaseMngTrprInfo(trprInfo);

			// 그룹관련 정보 가지고 있기.
			// 설문지 관리 번호 (공용), 설문지 영역관리번호 QUSTNB_RELM_MNG_NO
			// 영역고유명, 시작문항번호, 종료문항번호
			List<Map<String, Object>> relmList = new ArrayList<Map<String, Object>>();
			int strNo = 0;
			int endNo = 0;
			for (int i=0;i<dsList.rowSize();i++) {
				String relmNo = dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"); //
				String nextNo = "";
				String mngrMngPrvsonYn = dsList.get(i).getValue("MNGR_MNG_PRVSON_YN");
				if ("".equals(mngrMngPrvsonYn) || null == mngrMngPrvsonYn) {
					mngrMngPrvsonYn = "N";
				}
				if (i != dsList.rowSize()-1) {
					nextNo = dsList.get(i+1).getValue("QUSTNB_RELM_MNG_NO");
					// nextNo 가 다를 경우 리스트에 담는다 종결한다.
					if(!relmNo.equals(nextNo)) {
						Map<String, Object> relm = new HashMap<String, Object>();
						endNo = i;
						relm.put("QUSTNB_RELM_MNG_NO", dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"));
						relm.put("QUSTNB_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_RELM_ESNTAL_NM"));
						relm.put("MNGR_MNG_PRVSON_YN", mngrMngPrvsonYn);
						relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
						relm.put("QUSTNB_END_QESITM_NO", endNo);
						relm.put("USER_ID", userId);
						relmList.add(relm);
						strNo = i+1;
					}
				}else { // i 의 마지막이면
					nextNo = dsList.get(i).getValue("QUSTNB_RELM_MNG_NO");
					Map<String, Object> relm = new HashMap<String, Object>();
					endNo = i;
					relm.put("QUSTNB_RELM_MNG_NO", dsList.get(i).getValue("QUSTNB_RELM_MNG_NO"));
					relm.put("QUSTNB_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_RELM_ESNTAL_NM"));
					log.debug("null 이냐? : " + mngrMngPrvsonYn);
					relm.put("MNGR_MNG_PRVSON_YN", mngrMngPrvsonYn);
					relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
					relm.put("QUSTNB_END_QESITM_NO", endNo);
					relm.put("USER_ID", userId);
					relmList.add(relm);
					// strNo = strNo+1;
				}
			}
			// 설문템플릿 영역성정 등록 SBB020
			for (int i=0;i<relmList.size();i++) {
				Map<String, String> groupMap = new HashMap<String, String>();

				groupMap.put("USER_ID", userId);
				groupMap.put("SYS_CD", "SA");
				groupNo = survshtMmnMapper.selectSysSeCd(groupMap);
				Map<String, Object> relm = new HashMap<String, Object>();
				relm = relmList.get(i);
				relm.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
				relm.put("QUSTNB_RELM_MNG_NO", groupNo);
				survshtMmnMapper.insertQustnbRelmTmptMng(relm);
			}

			// 설문지 문항 등록 SBB010
			for (int i=0;i<dsList.rowSize();i++) {
				Map<String, Object> survDtlParam = new HashMap<String, Object>();

				survDtlParam.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
				survDtlParam.put("USER_ID", userId);
				survDtlParam.put("QESITM_SQNCE", i);
				survDtlParam.put("QESITM_MNG_NO", dsList.get(i).getValue("QESITM_MNG_NO"));
				survDtlParam.put("CHTY_QESITM_YN",  dsList.get(i).getValue("CHTY_QESITM_YN"));
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  dsList.get(i).getValue("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택에 연결될 문항 키값.
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  dsList.get(i).getValue("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택형 부모 KEY

				// 설문지 문항 등록
				survshtMmnMapper.insertQustnbMngTmptList(survDtlParam);
			}
			result.put("dmSrvyDtl", survPram);

		} else if ("MODIFY".equals(dmDtl.getValue("TYPE"))) {
			//설문지 수정(정보수정)
			Map<String, Object> survPram = new HashMap<String, Object>();
			qustnbNo = dmDtl.getValue("QUSTNB_TMPT_MNG_NO");

			Map<String, Object> saveMap = new HashMap<String, Object>();
			saveMap.put("QUSTNB_TMPT_MNG_NO", dmDtl.getValue("QUSTNB_TMPT_MNG_NO"));
			// 기존 채점영역 설정 값을 일괄 삭제한다. SBB030
			survshtMmnMapper.deleteQuestnbRelmTmptList(saveMap);

			survPram.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
			survPram.put("QUSTNB_TMPT_NM", dmDtl.getValue("QUSTNB_TMPT_NM")); // 설문지템플릿명
			survPram.put("QUSTNB_NM", dmDtl.getValue("QUSTNB_NM")); // 설문지명
			survPram.put("SRVY_PURPS_CN", dmDtl.getValue("SRVY_PURPS_CN")); // 설문목적내용
			survPram.put("SRVY_WRT_GUIDAN_CN", dmDtl.getValue("SRVY_WRT_GUIDAN_CN")); // 설문작성안내내용
			survPram.put("SRVY_BGNG_YMD", dmDtl.getValue("SRVY_BGNG_YMD")); // 설문시작일자
			survPram.put("SRVY_END_YMD", dmDtl.getValue("SRVY_END_YMD")); // 설문종료일자
			survPram.put("USER_ID", userId); // 작성자 아이디
//			survPram.put("WRTR_NM", userNm); // 작성자 명
			// 정의 안된거

			//Map<String, Object> trprInfo = survshtMmnMapper.selectTrprInfoDtl(trprMap);
			survPram.put("SRVY_TRGT_SE_CD", ""); // 설문대상 구분 코드 SRVY_TRGT_SE_CD
			//survPram.put("MENU_NO", authMenuNo); // 업무구분 코드 TASKWK_SYS_SE_CD 서브 쿼리로 조회 할 예정.
			survPram.put("UNT_TASKWK_SE_CD", dmDtl.getValue("UNT_TASKWK")); // 단위업무구분 코드 UNT_TASKWK_SE_CD (선택한 단위 업무 메뉴 구분 코드)
			survPram.put("SRVY_PRGRS_STTS_SE_CD", ""); // 설문진행상태구분코드 SRVY_PRGRS_STTS_SE_CD 명

			// 설문지 수정
			survshtMmnMapper.updateQustnbTmptMng(survPram);

//			trprInfo.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
//			survshtMmnMapper.deleteCaseMngTrprInfo(trprInfo);
//			trprInfo.put("USER_ID", userId);
//			survshtMmnMapper.insertCaseMngTrprInfo(trprInfo);

			// 그룹관련 정보 가지고 있기.
			// 설문지 관리 번호 (공용), 설문지 영역관리번호 QUSTNB_RELM_MNG_NO
			// 영역고유명, 시작문항번호, 종료문항번호
			List<Map<String, String>> dsModyList = dsList.getInsertedRowList();
			List<Map<String, Object>> relmList = new ArrayList<Map<String, Object>>();
			int strNo = 0;
			int endNo = 0;
			for (int i=0;i<dsModyList.size();i++) {
				String relmNo = dsModyList.get(i).get("QUSTNB_RELM_MNG_NO"); //
				String nextNo = "";
				if (i != dsModyList.size()-1) {
					nextNo = dsModyList.get(i+1).get("QUSTNB_RELM_MNG_NO");
					// nextNo 가 다를 경우 리스트에 담는다 종결한다.
					if(!relmNo.equals(nextNo)) {
						Map<String, Object> relm = new HashMap<String, Object>();
						endNo = i;
						relm.put("QUSTNB_RELM_MNG_NO", dsModyList.get(i).get("QUSTNB_RELM_MNG_NO"));
						relm.put("QUSTNB_RELM_ESNTAL_NM", dsModyList.get(i).get("QUSTNB_RELM_ESNTAL_NM"));
						relm.put("MNGR_MNG_PRVSON_YN", dsModyList.get(i).get("MNGR_MNG_PRVSON_YN"));
						relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
						relm.put("QUSTNB_END_QESITM_NO", endNo);
						relm.put("USER_ID", userId);
						relmList.add(relm);
						strNo = i+1;
					}
				}else { // i 의 마지막이면
					nextNo = dsModyList.get(i).get("QUSTNB_RELM_MNG_NO");
					Map<String, Object> relm = new HashMap<String, Object>();
					endNo = i;
					relm.put("QUSTNB_RELM_MNG_NO", dsModyList.get(i).get("QUSTNB_RELM_MNG_NO"));
					relm.put("QUSTNB_RELM_ESNTAL_NM", dsModyList.get(i).get("QUSTNB_RELM_ESNTAL_NM"));
					relm.put("MNGR_MNG_PRVSON_YN", dsModyList.get(i).get("MNGR_MNG_PRVSON_YN"));
					relm.put("QUSTNB_BGNG_QESITM_NO", strNo);
					relm.put("QUSTNB_END_QESITM_NO", endNo);
					relm.put("USER_ID", userId);
					relmList.add(relm);
					// strNo = strNo+1;
				}
			}
			// 기존 그룹 정보 삭제
			survshtMmnMapper.deleteQustnbRelmTmptMng(survPram);

			// 설문영역성정 등록
			for (int i=0;i<relmList.size();i++) {

				Map<String, String> groupMap = new HashMap<String, String>();
				groupMap.put("USER_ID", userId);
				groupMap.put("SYS_CD", "SA");
				groupNo = survshtMmnMapper.selectSysSeCd(groupMap);

				Map<String, Object> relm = new HashMap<String, Object>();
				relm = relmList.get(i);
				relm.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
				relm.put("QUSTNB_RELM_MNG_NO", groupNo);
				survshtMmnMapper.insertQustnbRelmTmptMng(relm);
			}
			// 기존 설문지 문항 삭제  SBB010
			survshtMmnMapper.deleteQustnbTmptMng(survPram);

			List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
			List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
			List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();

			log.debug(String.valueOf(insertedRowList.size()));
			log.debug(String.valueOf(deletedRowList.size()));
			log.debug(String.valueOf(updatedRowList.size()));
			// 설문지 문항 등록 SBB010
			for (int i=0;i<insertedRowList.size();i++) {
				Map<String, String> temp = insertedRowList.get(i);
				Map<String, Object> survDtlParam = new HashMap<String, Object>();

				survDtlParam.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
				survDtlParam.put("USER_ID", userId);
				survDtlParam.put("QESITM_SQNCE", String.valueOf(i));
				survDtlParam.put("QESITM_MNG_NO", temp.get("QESITM_MNG_NO"));
				survDtlParam.put("CHTY_QESITM_YN",  temp.get("CHTY_QESITM_YN"));
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  temp.get("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택에 연결될 문항 키값.
				survDtlParam.put("CHTY_QESITM_ESNTAL_NO",  temp.get("CHTY_QESITM_ESNTAL_NO")); // 선택된 선택형 부모 KEY

				log.debug(survDtlParam.toString());
				// 설문지 문항 등록
				survshtMmnMapper.insertQustnbMngTmptList(survDtlParam);
			}

			result.put("dmSrvyDtl", survPram);
		}
		Map<String, Object> returnParam = new HashMap<String, Object>();
		returnParam.put("QUSTNB_TMPT_MNG_NO", qustnbNo);
		result.put("dmSrvyDtl", returnParam);
		return result;

	}

	public Map<String, Object> selectQuestnbRelmTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> dmMap = new HashMap<String, Object>();

		dmMap.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getValue("QUSTNB_TMPT_MNG_NO"));

		int questnbRelmCnt = survshtMmnMapper.selectQuestnbRelmTmptCnt(dmMap);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (0 == questnbRelmCnt) {
			// 설정을 저장하지 않았을 경우 설문 등록시 저장한 영역을 기준을 베이스로 기본 영역을 설정해준다.
			list = survshtMmnMapper.selectQuestnbRelmBaseTmptList(dmMap);
		} else {
			// 설정이 저장 되어있다면 저장된 설정을 기준으로 데이터를 가져온다.
			list = survshtMmnMapper.selectQuestnbRelmTmptList(dmMap);
		}
		int pageIdx = 1;
		int rowSize = 9999;
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);

		return result;
	}

	public Map<String, Object> saveQuestnbRelmTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> saveMap = new HashMap<String, Object>();
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		saveMap.put("USER_ID", userId);
		// saveMap.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));
		saveMap.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));

		// 저장의 기준은 일괄 삭제 후 재 등록
		// 기존 채점영역 설정 값을 일괄 삭제한다. SBB030
		survshtMmnMapper.deleteQuestnbRelmTmptList(saveMap);
		String qustnbNo = "";

		if(dsList != null) {
			for (int i=0;i<dsList.rowSize();i++) {
				Map<String, String> mngNoMap = new HashMap<String, String>();

				mngNoMap.put("USER_ID", userId);
				mngNoMap.put("SYS_CD", "SG");
				qustnbNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

				Map<String, Object> param = new HashMap<String, Object>();

				param.put("QUSTNB_GRDNG_RELM_MNG_NO", qustnbNo);
				//param.put("QUSTNB_MNG_NO", dsList.get(i).getValue("QUSTNB_MNG_NO"));
				param.put("QUSTNB_TMPT_MNG_NO", saveMap.get("QUSTNB_TMPT_MNG_NO"));
				param.put("QUSTNB_GRDNG_RELM_ESNTAL_NM", dsList.get(i).getValue("QUSTNB_GRDNG_RELM_ESNTAL_NM"));
				param.put("QUSTNB_BGNG_QESITM_NO", dsList.get(i).getValue("QUSTNB_BGNG_QESITM_NO"));
				param.put("QUSTNB_END_QESITM_NO", dsList.get(i).getValue("QUSTNB_END_QESITM_NO"));
				param.put("GRDNG_CRTR_SE_CD", dsList.get(i).getValue("GRDNG_CRTR_SE_CD"));
				param.put("USER_ID", userId);
				param.put("USER_SRVY_RELM_INDCT_YN", dsList.get(i).getValue("USER_SRVY_RELM_INDCT_YN"));
				param.put("MNGR_SRVY_RELM_INDCT_YN", dsList.get(i).getValue("MNGR_SRVY_RELM_INDCT_YN"));

				survshtMmnMapper.insertQustnbGrdngTmptList(param);
			}
		}
		result.put("dmSrvyDtl", saveMap);
		return result;
	}

	public Map<String, Object> selectResultCrtrTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));

		//채점기준 리스트 가져오기
		List<Map<String, Object>> list = survshtMmnMapper.selectResultCrtrTmptList(param);

		int pageIdx = 1;
		int rowSize = 9999;
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);
		return result;
	}

	public Map<String, Object> saveResultCrtrTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		Map<String, Object> param = new HashMap<String, Object>();

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String grdngNo = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		param.put("USER_ID", userId);
		param.put("QUSTNB_MNG_NO", dmSrvyDtl.getValue("QUSTNB_MNG_NO"));
		param.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));
		log.debug("rowSize : " + dsList.rowSize());
		log.debug("colSize : " + dsList.colSize());

		//기존 목록 삭제
		survshtMmnMapper.deleteResultCrtrTmpt(param);
		for (int i=0;i<dsList.rowSize();i++) {
			Map<String, String> rowMap = dsList.get(i).toMap();
			rowMap.put("USER_ID", userId);
			rowMap.put("SYS_CD", "AE"); //채점영역 기준 설정
			grdngNo = survshtMmnMapper.selectSysSeCd(rowMap);

			rowMap.put("GRDNG_RESULT_CRTR_MNG_NO", grdngNo);

			log.debug(rowMap.toString());
			survshtMmnMapper.insertResultCrtrTmptList(rowMap);
		}
		return result;
	}

	public Map<String, Object> selectResultQustnbRelmTmpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));

		//채점기준 리스트 가져오기
		List<Map<String, Object>> list = survshtMmnMapper.selectResultQustnbRelmTmpt(param);

		int pageIdx = 1;
		int rowSize = 9999;
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);

		return result;
	}

	public Map<String, Object> selectPreSurvshtTmptInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
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
		param.put("QUSTNB_TMPT_MNG_NO", dmSearch.getValue("QUSTNB_TMPT_MNG_NO"));
		Map<String, Object> dmQustnbMngInfo = survshtMmnMapper.selectQustnbMngTmptInfo(param);
		int qesitmCnt = survshtMmnMapper.selectQesitmTmptCnt(param);
		dmQustnbMngInfo.put("QESITM_CNT", qesitmCnt);
		result.put("dmQustnbMngInfo", dmQustnbMngInfo);
		return result;
	}

	public Map<String, Object> selectPreSurvshtTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmQustnbMngInfo");
		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		param.put("USER_ID", userId);
		param.put("QUSTNB_TMPT_MNG_NO", dmSearch.getValue("QUSTNB_TMPT_MNG_NO"));

		List<Map<String, Object>> list = survshtMmnMapper.selectPreSurvshtTmptList(param);

		List<Map<String, Object>> list2 = survshtMmnMapper.selectPreSurvshtDtlTmptList(param);

		List<Map<String, Object>> list3 = survshtMmnMapper.selectPreSurvshtDtlTmptRelmList(param);

		result.put("dsList", list);
		result.put("ds1", list2);
		result.put("ds2", list3);
		return result;
	}

	public Map<String, Object> selectSurvshtCpTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");

		Map<String, Object> dmSearchMap = new HashMap<>();
		// 01 템플릿명 , 02 설문지명

//		if (dmSearchParam.getValue("strSearchKey").equals("00")) {
//			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
//		} else if (dmSearchParam.getValue("strSearchKey").equals("01")) {
//			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
//		} else if (dmSearchParam.getValue("strSearchKey").equals("02")) {
//			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
//		} else if (dmSearchParam.getValue("strSearchKey").equals("03")) {
//			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
//		}

		dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));

		log.debug("### SEARCH_KEY === " + dmSearchMap.get("SEARCH_KEY").toString());
		log.debug("### SEARCH_DATA === " + dmSearchMap.get("SEARCH_DATA").toString());

		dmSearchMap.put("UNT_TASKWK_SE_CD", dmSearchParam.getValue("UNT_TASKWK_SE_CD"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		// Integer totalCount = survshtMmnService.selectSurvshtListTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = survshtMmnMapper.selectSurvshtTmptCpList(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", listBoard.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", listBoard);
		result.put("dmPage", resPage);

		return result;
	}

	public Map<String, Object> processSurvshtCpTmpt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> ds1 = new ArrayList<Map<String, Object>>();


		ParameterGroup dsList = dataRequest.getParameterGroup("ds1");
		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String userNm = "";
		String grdngNo = "";
		Integer authMenuNo = 0;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			userNm = loginVO.getUserName();
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}

		// 선택된 체크 박스 갯수 만큼 복사한다.
		if(dsList.rowSize() > 0) {
			for(int i=0;i<dsList.rowSize();i++) {
				Map<String, String> rowMap = dsList.get(i).toMap();
				String qustnbTmptMngNo = rowMap.get("QUSTNB_TMPT_MNG_NO");

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("QUSTNB_TMPT_MNG_NO", qustnbTmptMngNo);
				// SBB000 설문지 정보 가져오기.
				Map<String, Object> qustnbMap = survshtMmnMapper.selectQustnbTmptMngNoInfo(param);

				// QUSTNB_MNG_NO 번호 채번
				Map<String, String> mngNoMap = new HashMap<String, String>();
				mngNoMap.put("USER_ID", userId);
				mngNoMap.put("SYS_CD", "SN");
				String qustnbNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

				qustnbMap.put("QUSTNB_MNG_NO", qustnbNo);
				qustnbMap.put("WRTR_ID", userId);
				qustnbMap.put("WRTR_NM", userNm);
				qustnbMap.put("SRVY_PRGRS_STTS_SE_CD", "05"); // 복사완료 상태
				qustnbMap.put("MENU_NO", String.valueOf(authMenuNo));

				// 리턴해줄 값 담아두기.
				Map<String, Object> ds = new HashMap<String, Object>();
				ds.put("QUSTNB_TMPT_MNG_NO", qustnbTmptMngNo);
				ds.put("QUSTNB_MNG_NO", qustnbNo);
				//SBB100 등록.
				log.debug(qustnbMap.toString());
				survshtMmnMapper.insertQustnbMng(qustnbMap);

				// SBB010 설문지문항템플릿 정보 가져오기.
				List<Map<String, Object>> qesitmList = survshtMmnMapper.selectSurvshtQesitmTmptList(qustnbMap);

				for(int j=0;j<qesitmList.size();j++) {
					Map<String, Object> qesitmMap = qesitmList.get(j);
					qesitmMap.put("USER_ID", userId);
					qesitmMap.put("QUSTNB_MNG_NO", qustnbNo);
					// SBB300 설문지 문항 등록
					log.debug(qesitmMap.toString());
					survshtMmnMapper.insertQustnbMngList(qesitmMap);
				}

				// SBB020 설문영역설정템플릿 정보 가져오기.
				List<Map<String, Object>> qesitmRelmList = survshtMmnMapper.selectQesitmQustnbTmptCheckList(qustnbMap);

				for(int j=0;j<qesitmRelmList.size();j++) {
					Map<String, Object> qesitmRelmMap = qesitmRelmList.get(j);
					qesitmRelmMap.put("QUSTNB_MNG_NO", qustnbNo);
					qesitmRelmMap.put("USER_ID", userId);
					// SBB120 설문영역설정 등록
					log.debug(qesitmRelmMap.toString());
					survshtMmnMapper.insertQustnbRelmMng(qesitmRelmMap);
				}

				// SBB030 채점영역설정템플릿 정보 가져오기.
				List<Map<String, Object>> qesitmGrdngRelmList = survshtMmnMapper.selectQuestnbRelmTmptList(qustnbMap);

				for(int j=0;j<qesitmGrdngRelmList.size();j++) {
					Map<String, Object> qesitmGrdngRelmMap = qesitmGrdngRelmList.get(j);
					qesitmGrdngRelmMap.put("QUSTNB_MNG_NO", qustnbNo);
					qesitmGrdngRelmMap.put("USER_ID", userId);
					// SBB130 채점영역설정 등록.
					log.debug(qesitmGrdngRelmMap.toString());
					survshtMmnMapper.insertQustnbGrdngList(qesitmGrdngRelmMap);
				}

				// SBB040 영역별배점기준템플릿 정보 가져오기
				List<Map<String, Object>> grdngResultList = survshtMmnMapper.selectResultCrtrTmptList(qustnbMap);
				for(int j=0;j<grdngResultList.size();j++) {
					Map<String, Object> grdngResultMap = grdngResultList.get(j);
					grdngResultMap.put("QUSTNB_MNG_NO", qustnbNo);
					grdngResultMap.put("USER_ID", userId);
					// SBB400 영영별배점기준 등록
					log.debug(grdngResultMap.toString());
					survshtMmnMapper.insertResultCrtrObjectList(grdngResultMap);
				}

				ds1.add(ds);
				// 사례 대상자 정보 가져와 넣어주기.
				// 해당 영역은 단위 업무 단위에서 처리 해주셔야 합니다.
				// 타깃 테이블은 SBB110
			}
		}

		result.put("ds1", ds1);

		return result;
	}

	public Map<String, Object> processQustnbTmptMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> dmSrvy = new HashMap<String, Object>();

		ParameterGroup dmSrvyDtl = dataRequest.getParameterGroup("dmSrvyDtl");

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		dmSrvy.put("QUSTNB_TMPT_MNG_NO", dmSrvyDtl.getValue("QUSTNB_TMPT_MNG_NO"));
		dmSrvy.put("TASKWK_SYS_SE_CD", dmSrvyDtl.getValue("TASKWK_SYS_SE_CD"));
		dmSrvy.put("QUSTNB_TMPT_NM", dmSrvyDtl.getValue("QUSTNB_TMPT_NM"));
		dmSrvy.put("QUSTNB_NM", dmSrvyDtl.getValue("QUSTNB_NM"));
		dmSrvy.put("SRVY_PURPS_CN", dmSrvyDtl.getValue("SRVY_PURPS_CN"));
		dmSrvy.put("SRVY_WRT_GUIDAN_CN", dmSrvyDtl.getValue("SRVY_WRT_GUIDAN_CN"));
		dmSrvy.put("SRVY_BGNG_YMD", dmSrvyDtl.getValue("SRVY_BGNG_YMD"));
		dmSrvy.put("SRVY_END_YMD", dmSrvyDtl.getValue("SRVY_END_YMD"));
		dmSrvy.put("SRVY_PRGRS_STTS_SE_CD", dmSrvyDtl.getValue("SRVY_PRGRS_STTS_SE_CD"));
		dmSrvy.put("WRTR_ID", dmSrvyDtl.getValue("WRTR_ID"));
		dmSrvy.put("WRTR_NM_ENCPT", dmSrvyDtl.getValue("WRTR_NM_ENCPT"));
		dmSrvy.put("USER_ID", userId);

		String useYn = dmSrvyDtl.getValue("USE_YN");
		int useCnt = 0;
		if ("Y".equals(useYn)) {
			useCnt = survshtMmnMapper.selectUseTmptCount(dmSrvy);

			if(0 < useCnt) {
				// 사용 된 이력이 1회 라도 있을 경우.
				// 미사용 상태로 변경 할 수 없고 수정 불가.
				dmSrvy.put("TMPT_USED_YN", "Y");
				dmSrvy.put("USE_YN", "Y");
				dmSrvy.put("USE_YN_NM", "사용");
			} else {
				dmSrvy.put("USE_YN", "N");
				dmSrvy.put("USE_YN_NM", "미사용");
			}

		} else {
			dmSrvy.put("USE_YN", "Y");
			dmSrvy.put("USE_YN_NM", "사용");
		}
		if (useCnt == 0) {
			survshtMmnMapper.updateQustnbTmptStatusMng(dmSrvy);
		}

		result.put("dmSrvyDtl", dmSrvy);

		return result;
	}

	public Map<String, Object> selectMySurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		param.put("START_IDX", startIndex);
		param.put("ROW_COUNT", rowSize);

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		List<Map<String, Object>> list = survshtMmnMapper.selectMySurvshtList(param);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", list.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("dsList", list);
		result.put("dmPage", resPage);

		return result;
	}

	public Map<String, Object> selectSurvshtTmptRelmMarkList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dsList");
		ParameterGroup ds2 = dataRequest.getParameterGroup("ds2");
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearchParam");
		String userId = "";
		String gender = "";
		String untTaskwkSeCd = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			gender = loginVO.getGender();
			untTaskwkSeCd = loginVO.getUntTaskwkSeCd();
		}
		if (dmSearch.getValue("strSearchKey").equals("tit")) {
			param.put("QUSTNB_TMPT_NM", dmSearch.getValue("strSearchData"));
		}
		param.put("START_DATE", dmSearch.getValue("startDate"));
		param.put("END_DATE", dmSearch.getValue("endDate"));
		// 테스트용으로 디딤센터로 강제 해서 노출 되도록 처리
		untTaskwkSeCd = "U07";
		param.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);

		List<Map<String, Object>> list = survshtMmnMapper.selectSurvshtTmptRelmMarkList(param);

		result.put("dsList", list);


		return result;
	}

	public Map<String, Object> selectDidimGrdngRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSrvyDtl");

		param.put("QUSTNB_TMPT_MNG_NO", dmBase.getValue("QUSTNB_TMPT_MNG_NO"));

		List<Map<String, Object>> list = survshtMmnMapper.selectDidimGrdngRelmList(param);

		result.put("dsList", list);
		return result;
	}

	public Map<String, Object> saveDidimGrdngRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		ParameterGroup dmBase = dataRequest.getParameterGroup("dsList");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		// List<Map<String, String>> dsList = dmBase.getAllRowList();
		List<Map<String, String>> dsList = dmBase.getInsertedRowList();

		param.put("QUSTNB_TMPT_MNG_NO", dsList.get(0).get("QUSTNB_TMPT_MNG_NO"));
		// 기존 데이터 전체 삭제;
		survshtMmnMapper.deleteDidimGrdngRelmMark(param);
		for(int i=0;i<dsList.size();i++) {
			Map<String, String> temp = dsList.get(i);
			temp.put("USER_ID", userId);
			// 추가를 통해 등록한 정보는 기존 영역 테이블에서 영역명을 비교해 영역구분코드를 가져온다.
			if("".equals(temp.get("QUSTNB_RELM_MNG_NO"))) {
				temp.put("QUSTNB_TMPT_MNG_NO", (String) param.get("QUSTNB_TMPT_MNG_NO"));
				Map<String, String> relm = survshtMmnMapper.getQustnbRelmData(temp);

				if (!"".equals(relm.get("QUSTNB_RELM_MNG_NO"))) {
					temp.put("QUSTNB_RELM_MNG_NO", relm.get("QUSTNB_RELM_MNG_NO"));
				}
			}
			log.debug(temp.toString());
			survshtMmnMapper.insertDidimGrdngRelmMark(temp);
		}
		return result;
	}

	public Map<String, Object> savePreSurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dsList");
		ParameterGroup ds2 = dataRequest.getParameterGroup("ds2");
		String userId = "";
		String gender = "";
		String untTaskwkSeCd = "";
		String userIp = request.getRemoteAddr();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			gender = loginVO.getGender();

			untTaskwkSeCd = loginVO.getUntTaskwk(); // 해당 데이터는 현재 접속해 있는 단위업무 코드입니다.
			if ("".equals(untTaskwkSeCd) || null == untTaskwkSeCd) {
				// 로그인한 사용자의 단위업무 코드입니다.
				untTaskwkSeCd = loginVO.getUntTaskwkSeCd();
			}
		}
		log.debug(dmBase.toString());
		List<Map<String, String>> dsList = dmBase.getAllRowList(); // 문항 정보
		List<Map<String, String>> dsRelmList = ds2.getAllRowList(); // 영역 정보
		// 데이터 저장 로직 처리.
		Map<String, Object> param = new HashMap<String, Object>();

		// 미리 보기 말고 실제 적용 화면에선 해당 정보를 가져와야한다.
//		String SRVY_RSPNS_MNG_NO = ""; //설문응답관리번호
		String SRVY_RSPNS_MNG_NO = dsList.get(0).get("SRVY_RSPNS_MNG_NO"); //설문응답관리번호
		if (StringUtils.isEmpty(SRVY_RSPNS_MNG_NO)) {
			Map<String, String> mngNoMap = new HashMap<String, String>();
			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "AS");
			SRVY_RSPNS_MNG_NO = survshtMmnMapper.selectSysSeCd(mngNoMap);	//없으면 새로 채번한다.
		}



		param.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);

		// 설문응답 (SBB500) insert
		param.put("QUSTNB_TMPT_MNG_NO", dsRelmList.get(0).get("QUSTNB_TMPT_MNG_NO")); // 템플릿 관리번호
		param.put("QUSTNB_MNG_NO", dsList.get(0).get("QUSTNB_MNG_NO")); // 설문지 관리번호

		untTaskwkSeCd = survshtMmnMapper.selectUntTaskwkSeCd(param);

		param.put("UNT_TASKWK_SE_CD", untTaskwkSeCd); // 단위업무구분코드
		param.put("USER_ID", userId); // 사용자 아이디
		param.put("CNTN_IP_ADDR", userIp); // 접속아이피주소
		// 사례관리번호, 사례관리차수
//		survshtMmnMapper.insertSrvyRspnsMngInfo(param);
		survshtMmnMapper.saveSrvyRspnsMngInfo(param);

		// [SBB100] 설문진행상태구분코드 06(설문완료)으로 update
		param.put("SRVY_PRGRS_STTS_SE_CD", "06");
		survshtMmnMapper.updateSrvyPrgrs(param);

		// 영역 정보 가져오기.
		for (int i=0;i<dsRelmList.size();i++) {
			Map<String, String> relm = dsRelmList.get(i);

			log.debug("dsRelmList.index : "+i);
			log.debug("QUSTNB_TMPT_MNG_NO : " + relm.get("QUSTNB_TMPT_MNG_NO"));
			log.debug("QUSTNB_RELM_MNG_NO : " + relm.get("QUSTNB_RELM_MNG_NO"));
			log.debug("QUSTNB_RELM_ESNTAL_NM : " + relm.get("QUSTNB_RELM_ESNTAL_NM"));
			log.debug("QUSTNB_BGNG_QESITM_NO : " + relm.get("QUSTNB_BGNG_QESITM_NO"));
			log.debug("QUSTNB_END_QESITM_NO : " + relm.get("QUSTNB_END_QESITM_NO"));
			log.debug("MNGR_MNG_PRVSON_YN : " + relm.get("MNGR_MNG_PRVSON_YN"));
			log.debug("GRDNG_CRTR_SE_CD : " + relm.get("GRDNG_CRTR_SE_CD"));

			// 영역별 점수  점수 및 정보 가져오기.
			Map<String, Object> calSuport = dsCalculateData(request, dsList, relm, SRVY_RSPNS_MNG_NO);

//			result.put("GRDNG_SCORE", tot); // 총점.
//			result.put("QUSTNB_TMPT_MNG_NO", QUSTNB_TMPT_MNG_NO); // 설문지 템플릿 번호.
//			result.put("QUSTNB_MNG_NO", QUSTNB_MNG_NO); // 설문지 번호.
//			result.put("SRVY_RSPNS_MNG_NO", srvyRspnsMngNo); // 설문응답관리번호

			CalculateResultSet(request, calSuport, relm, param);
		}

		return result;
	}

	/**
	 * @Method명   : savePreSurvshtBySurvsht
	 * @param request, dataRequest, dmBase(설문지문항정보), ds2(영역설정정보)
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 7.
	 * @Method설명 : /설문지번호별 저장
	 */
	@Override
	public Map<String, Object> savePreSurvshtBySurvsht(HttpServletRequest request, ParameterGroup dmBase, ParameterGroup ds2) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//		ParameterGroup dmBase = dataRequest.getParameterGroup("dsList");
//		ParameterGroup ds2 = dataRequest.getParameterGroup("ds2");
		String userId = "";
		String gender = "";
		String untTaskwkSeCd = "";
		String userIp = request.getRemoteAddr();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			gender = loginVO.getGender();

			untTaskwkSeCd = loginVO.getUntTaskwk(); // 해당 데이터는 현재 접속해 있는 단위업무 코드입니다.
			if ("".equals(untTaskwkSeCd) || null == untTaskwkSeCd) {
				// 로그인한 사용자의 단위업무 코드입니다.
				untTaskwkSeCd = loginVO.getUntTaskwkSeCd();
			}
		}
		log.debug(dmBase.toString());
		List<Map<String, String>> dsList = dmBase.getAllRowList(); // 문항 정보
		List<Map<String, String>> dsRelmList = ds2.getAllRowList(); // 영역 정보
		// 데이터 저장 로직 처리.
		Map<String, Object> param = new HashMap<String, Object>();

		// 미리 보기 말고 실제 적용 화면에선 해당 정보를 가져와야한다.
//		String SRVY_RSPNS_MNG_NO = ""; //설문응답관리번호
		String SRVY_RSPNS_MNG_NO = dsList.get(0).get("SRVY_RSPNS_MNG_NO"); //설문응답관리번호
		if (StringUtils.isEmpty(SRVY_RSPNS_MNG_NO)) {
			Map<String, String> mngNoMap = new HashMap<String, String>();
			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "AS");
			SRVY_RSPNS_MNG_NO = survshtMmnMapper.selectSysSeCd(mngNoMap);	//없으면 새로 채번한다.
		}

		param.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);

		// 설문응답 (SBB500) insert
		param.put("QUSTNB_TMPT_MNG_NO", dsRelmList.get(0).get("QUSTNB_TMPT_MNG_NO")); // 템플릿 관리번호
		param.put("QUSTNB_MNG_NO", dsList.get(0).get("QUSTNB_MNG_NO")); // 설문지 관리번호

		untTaskwkSeCd = survshtMmnMapper.selectUntTaskwkSeCd(param);

		param.put("UNT_TASKWK_SE_CD", untTaskwkSeCd); // 단위업무구분코드
		param.put("USER_ID", userId); // 사용자 아이디
		param.put("CNTN_IP_ADDR", userIp); // 접속아이피주소
		// 사례관리번호, 사례관리차수
		//survshtMmnMapper.insertSrvyRspnsMngInfo(param);
		survshtMmnMapper.saveSrvyRspnsMngInfo(param);

		// 영역 정보 가져오기.
		for (int i=0;i<dsRelmList.size();i++) {
			Map<String, String> relm = dsRelmList.get(i);

			// 영역별 점수  점수 및 정보 가져오기.
			Map<String, Object> calSuport = dsCalculateData(request, dsList, relm, SRVY_RSPNS_MNG_NO);

//			result.put("GRDNG_SCORE", tot); // 총점.
//			result.put("QUSTNB_TMPT_MNG_NO", QUSTNB_TMPT_MNG_NO); // 설문지 템플릿 번호.
//			result.put("QUSTNB_MNG_NO", QUSTNB_MNG_NO); // 설문지 번호.
//			result.put("SRVY_RSPNS_MNG_NO", srvyRspnsMngNo); // 설문응답관리번호


			CalculateResultSet(request, calSuport, relm, param);
		}

		return result;
	}

	/**
	 * INFO : 영역 단위로 점수 합산, 총점 리턴.
	 * dsList : 문항정보, relm : 영역정보, srvyRspnsMngNo : 설문응답관리번호
	 * return
	 * GRDNG_SCORE : 총점, QUSTNB_TMPT_MNG_NO : 설문지 템플릿 번호
	 * QUSTNB_MNG_NO : 설문지 번호, SRVY_RSPNS_MNG_NO : 설문응답관리번호
	 */
	Map<String, Object> dsCalculateData(HttpServletRequest request, List<Map<String, String>> dsList, Map<String, String> relm, String srvyRspnsMngNo) throws Exception {
		// dsList 문항 정보, relm 영역 정보, 성별
		// return 정보 GRDNG_SCROE : 총점 ,
		// 총점, 영역 정보,
		// 항목별 응답 정보 넣기.

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String gender = "";
		String untTaskSeCd = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			gender = loginVO.getGender();
			untTaskSeCd = loginVO.getUntTaskwkSeCd();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		int BGNG_QESITM_NO = Integer.parseInt(relm.get("QUSTNB_BGNG_QESITM_NO"));
		int END_QESITM_NO = Integer.parseInt(relm.get("QUSTNB_END_QESITM_NO"));
		String QUSTNB_TMPT_MNG_NO = relm.get("QUSTNB_TMPT_MNG_NO"); //설문지 템플릿 관리번호
		String QUSTNB_MNG_NO = dsList.get(0).get("QUSTNB_MNG_NO"); // 설문지 관리번호

		param.put("QUSTNB_TMPT_MNG_NO", QUSTNB_TMPT_MNG_NO);
		param.put("QUSTNB_MNG_NO", QUSTNB_MNG_NO);

		untTaskSeCd = survshtMmnMapper.selectUntTaskwkSeCd(param);

		// 영역 별 점수
		int tot = 0;
		int cnt = 0;
		int qesitmCnt = 0;
		for (int i=0;i<dsList.size();i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> temp = dsList.get(i);
			log.debug(temp.toString());
			int QESITM_SQNCE = Integer.parseInt(temp.get("QESITM_SQNCE"));
			String QESITM_TYPE = temp.get("QESITM_TYPE_SE_CD");
			// 영역내의 문항 만 적용 한다.
			if ((BGNG_QESITM_NO <= QESITM_SQNCE) && (QESITM_SQNCE <= END_QESITM_NO)) {
				qesitmCnt++;
				// 주관식의 경우 점수가 적용 될 수 없어 제외한다.
				// 단일 선택형 || 척도형
				if ("01".equals(QESITM_TYPE) || "03".equals(QESITM_TYPE)) {
					tot += Integer.parseInt(StringNvlReturn(temp.get("QESITM_SINGL_RSPNS_VALUE")));
					cnt++;
					log.debug("##### 개별 단일,척도 총점 == " + Integer.parseInt(StringNvlReturn(temp.get("QESITM_SINGL_RSPNS_VALUE"))));
					log.debug("##### tot 단일,척도 총점 == " + tot);
					log.debug("##### cnt 단일,척도 총점 == " + cnt);
				// 다중 선택 형
				} else if ("02".equals(QESITM_TYPE)) {
					log.debug(temp.get("QESITM_COMPNO_RSPNS_VALUE"));
					String chkCnt = temp.get("QESITM_COMPNO_RSPNS_VALUE");
					// 체크를 한건도 안 했을 경우
					if(chkCnt == "") {
						tot += 0;
						cnt++;
					} else { // 체크를 한개 이상 한 경우.
						String chk[] = chkCnt.split(",");
						tot += chk.length;
						cnt++;
					}
					log.debug("##### chkCnt 단일,척도 총점 == " + chkCnt);
					log.debug("##### tot 단일,척도 총점 == " + tot);
					log.debug("##### cnt 단일,척도 총점 == " + cnt);
				//주관식
				} else if ("04".equals(QESITM_TYPE)) {
					String ans = temp.get("QESITM_RSPNS_CN");
				}

				// 항목별 응답내용 Insert
				map.put("SRVY_RSPNS_MNG_NO", srvyRspnsMngNo); // 설문응답관리번호
				map.put("QESITM_MNG_NO", temp.get("QESITM_MNG_NO")); // 문항관리번호
				map.put("QESITM_SINGL_RSPNS_VALUE", temp.get("QESITM_SINGL_RSPNS_VALUE")); // 문항단일응답값
				map.put("QESITM_COMPNO_RSPNS_VALUE", temp.get("QESITM_COMPNO_RSPNS_VALUE")); // 문항복수응답값
				map.put("QESITM_RSPNS_CN", temp.get("QESITM_RSPNS_CN")); // 문항응답내용
				map.put("USER_ID", userId); // 로그인 아이디

				// 항목별응답내용 (SBB220)
//				survshtMmnMapper.insertQesitmSrvyRspns(map);
				survshtMmnMapper.saveQesitmSrvyRspns(map);
			}
		}

		result.put("GRDNG_CNT", cnt); // 총점.
		result.put("GRDNG_SCORE", tot); // 총점.
		result.put("QESITM_RELM_CNT", qesitmCnt);
		result.put("QUSTNB_RELM_MNG_NO", relm.get("QUSTNB_RELM_MNG_NO"));
		result.put("QUSTNB_TMPT_MNG_NO", QUSTNB_TMPT_MNG_NO); // 설문지 템플릿 번호.
		result.put("QUSTNB_MNG_NO", QUSTNB_MNG_NO); // 설문지 번호.
		result.put("SRVY_RSPNS_MNG_NO", srvyRspnsMngNo); // 설문응답관리번호
		result.put("BGNG_QESITM_NO", BGNG_QESITM_NO); // 영역시작번호
		result.put("END_QESITM_NO", END_QESITM_NO); // 영역종료번호
		result.put("SXDC_SE_CD", gender);
		result.put("USER_ID", userId);
		result.put("UNT_TASKWK_SE_CD", untTaskSeCd);

		log.debug("영역별 총점 ###################### : " + tot);
		return result;
	}

	/**
	 *
	 * @Method명   : CalculateResultSet
	 * @param request
	 * @param calSuport : 합산 정보
	 * @param relm : 영역정보
	 * @param param : 기본 정보
	 * @throws Exception
	 * @작성자     : TaesooSong
	 * @작성일     : 2022. 10. 6.
	 * @Method설명 :
	 */
	void CalculateResultSet(HttpServletRequest request, Map<String, Object> calSuport, Map<String, String> relm, Map<String, Object> defaultMap) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("QUSTNB_TMPT_MNG_NO", defaultMap.get("QUSTNB_TMPT_MNG_NO")); // 설문지관리번호
		param.put("QUSTNB_MNG_NO", defaultMap.get("QUSTNB_MNG_NO")); // 설문지관리번호
		param.put("SRVY_RSPNS_MNG_NO", defaultMap.get("SRVY_RSPNS_MNG_NO")); // 설문응답번호
		String bgngNo = String.valueOf(calSuport.get("BGNG_QESITM_NO"));
		String endNo = String.valueOf(calSuport.get("END_QESITM_NO"));
		// 영역 채점 정보.
		List<Map<String, Object>> grdngRelmList = survshtMmnMapper.selectQuestnbRelmList(param);
		for (int i=0;i<grdngRelmList.size();i++) {
			Map<String, Object> temp = grdngRelmList.get(i);
			String tempBgngNo = String.valueOf(temp.get("QUSTNB_BGNG_QESITM_NO"));
			String tempEndNo = String.valueOf(temp.get("QUSTNB_END_QESITM_NO"));
			if (bgngNo.equals(tempBgngNo) && endNo.equals(tempEndNo)) {
				// 채점영역관리번호
				calSuport.put("QUSTNB_GRDNG_RELM_MNG_NO", temp.get("QUSTNB_GRDNG_RELM_MNG_NO")); // 채점영역관리번호
				calSuport.put("QUSTNB_GRDNG_RELM_ESNTAL_NM", temp.get("QUSTNB_GRDNG_RELM_ESNTAL_NM"));// 채점영역고유명

				//System.out.println(temp.get("QUSTNB_GRDNG_RELM_MNG_NO"));
			}

		}
		log.debug("calSuport.GRDNG_CNT == " + calSuport.get("GRDNG_CNT").toString());
		log.debug("calSuport.GRDNG_SCORE == " + calSuport.get("GRDNG_SCORE").toString());
		log.debug("relm.MNGR_MNG_PRVSON_YN == " + relm.get("MNGR_MNG_PRVSON_YN"));
		log.debug("relm.GRDNG_CRTR_SE_CD == " + relm.get("GRDNG_CRTR_SE_CD"));

		//디딤센터 계산식 적용.
		if ("U07".equals(defaultMap.get("UNT_TASKWK_SE_CD"))) {
			didimResultSet(calSuport, relm, defaultMap);
		} else { // 일반적인 경우.
			usualResultSet(calSuport, relm, defaultMap, grdngRelmList);
		}
	}

	/**
	 * 디딤센터 전용 총점 계산식 처리방안.
	 * 특정 계산식 등록이 안된것은 그냥 쇼잉만 하기 때문에 합산 값만 넣어줌.
	 * @Method명   : didimResultSet
	 * @param calSuport
	 * @param relm : 영역정보.
	 * @param defaultMap : 기본정보.
	 * @param grdngRelmList : 채점영역설정 정보. << 채점결과 등록용.
	 * @throws Exception
	 * @작성자     : TaesooSong
	 * @작성일     : 2022. 10. 12.
	 * @Method설명 :
	 */
	void didimResultSet(Map<String, Object> calSuport, Map<String, String> relm, Map<String, Object> defaultMap) throws Exception {
		// QUSTNB_TMPT_MNG_NO : TM2022092800001 : 디딤센터 입교선발 평가 심리검사지(내제화_청소년용)
		Map<String, Object> param = new HashMap<String, Object>();

		// 디딤전용 계산식 데이터 가져오기.(설문 템플릿 기준 등록 계산식 가져오기), 남여 기준 구분 없이 가져옴.
		List<Map<String, Object>> calList = survshtMmnMapper.selectRelmNomfrmList(calSuport);
		log.debug("##########  디딤 calList.size() = " + calList.size());
		double GRDNG_SCORE = 0;
		String relmMngNo = (String) calSuport.get("QUSTNB_RELM_MNG_NO");
		log.debug("##########  디딤 relmMngNo = " + relmMngNo);
		// 설문지영역산출수식
		// 계산식 데이터가 등록되어있을 경우.
		if (0 < calList.size()) {
			for(int i=0;i<calList.size();i++) {
				Map<String, Object> temp = calList.get(i);
				String tempRelmMngNo = (String) temp.get("QUSTNB_RELM_MNG_NO");
				log.debug("##########  0 < 디딤 tempRelmMngNo = " + tempRelmMngNo);
				// 영역번호 체크
				if (relmMngNo.equals(tempRelmMngNo)) {
					// 성별 여부 체크
					log.debug("##########  0 < 디딤 calSuport.get(\"SXDC_SE_CD\") = " + calSuport.get("SXDC_SE_CD"));
					log.debug("##########  0 < 디딤 temp.get(\"SXDC_SE_CD\") = " + temp.get("SXDC_SE_CD"));
					if(calSuport.get("SXDC_SE_CD").equals(temp.get("SXDC_SE_CD"))) {
						int RELM_SCORE = (int) calSuport.get("GRDNG_SCORE");

						// 총점 = 합산점수 * 수식값1 + 수식값2
						GRDNG_SCORE = RELM_SCORE * Double.parseDouble((String) temp.get("NOMFRM_VALUE1")) + Double.parseDouble((String) temp.get("NOMFRM_VALUE2"));
						log.debug("##########  0 < 디딤 GRDNG_SCORE = " + GRDNG_SCORE);
						param.put("QUSTNB_MNG_NO", calSuport.get("QUSTNB_MNG_NO"));
						param.put("QUSTNB_TMPT_MNG_NO", calSuport.get("QUSTNB_TMPT_MNG_NO"));
						param.put("SRVY_RSPNS_MNG_NO", calSuport.get("SRVY_RSPNS_MNG_NO"));
						param.put("QUSTNB_GRDNG_RELM_MNG_NO", calSuport.get("QUSTNB_GRDNG_RELM_MNG_NO"));
						param.put("UNT_TASKWK_SE_CD", calSuport.get("UNT_TASKWK_SE_CD"));
						param.put("GRDNG_SCORE", GRDNG_SCORE); // 총점.
						param.put("USER_ID", calSuport.get("USER_ID"));

//						survshtMmnMapper.insertGrdngResult(param);
						survshtMmnMapper.saveGrdngResult(param);
					}
				}

			}
		} else {
			// 등록이 안되어 있는 경우.
			int RELM_SCORE = (int) calSuport.get("GRDNG_SCORE");
			log.debug("########## 디딤 RELM_SCORE = " + RELM_SCORE);
			log.debug("########## 디딤 설문지관리번호 = " + calSuport.get("QUSTNB_MNG_NO"));
			log.debug("########## 디딤 설문지템플릿관리번호 = " + calSuport.get("QUSTNB_TMPT_MNG_NO"));
			int GRDNG_CNT = (int) calSuport.get("GRDNG_CNT");
			log.debug("########## 디딤 GRDNG_CNT = " + calSuport.get("GRDNG_CNT"));
			log.debug("########## 디딤 relm.get(\"GRDNG_CRTR_SE_CD\") = " + relm.get("GRDNG_CRTR_SE_CD"));
			if (null != relm.get("GRDNG_CRTR_SE_CD") && !"".equals(relm.get("GRDNG_CRTR_SE_CD"))) {
				if ("01".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 합계
					GRDNG_SCORE = RELM_SCORE;
				} else if ("02".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 개수
					GRDNG_SCORE = GRDNG_CNT;
				} else if ("03".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 평균
					GRDNG_SCORE = RELM_SCORE / (Integer.parseInt(String.valueOf(calSuport.get("END_QESITM_NO"))) - Integer.parseInt(String.valueOf(calSuport.get("BGNG_QESITM_NO"))));
				} else if ("04".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 특수
					// 특수건인데 상단의 계산 식값 등록이 안되어있는 경우. 합계로 노출한다.
					GRDNG_SCORE = RELM_SCORE;
				}
			}
			param.put("QUSTNB_MNG_NO", calSuport.get("QUSTNB_MNG_NO"));
			param.put("QUSTNB_TMPT_MNG_NO", calSuport.get("QUSTNB_TMPT_MNG_NO"));
			param.put("SRVY_RSPNS_MNG_NO", calSuport.get("SRVY_RSPNS_MNG_NO"));
			param.put("QUSTNB_GRDNG_RELM_MNG_NO", calSuport.get("QUSTNB_GRDNG_RELM_MNG_NO"));
			param.put("UNT_TASKWK_SE_CD", calSuport.get("UNT_TASKWK_SE_CD"));
			param.put("GRDNG_SCORE", GRDNG_SCORE); // 총점.
			param.put("USER_ID", calSuport.get("USER_ID"));

//			survshtMmnMapper.insertGrdngResult(param);
			survshtMmnMapper.saveGrdngResult(param);
		}

	}

	/**
	 * 일반적인 업무 구분 케이스
	 * @Method명   : usualResultSet
	 * @param calSuport
	 * @param relm
	 * @param grdngRelmList
	 * @throws Exception
	 * @작성자     : TaesooSong
	 * @작성일     : 2022. 10. 13.
	 * @Method설명 :
	 */
	void usualResultSet(Map<String, Object> calSuport, Map<String, String> relm, Map<String, Object> defaultMap, List<Map<String, Object>> grdngRelmList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("QUSTNB_TMPT_MNG_NO", defaultMap.get("QUSTNB_TMPT_MNG_NO")); // 설문지관리번호
		param.put("QUSTNB_MNG_NO", defaultMap.get("QUSTNB_MNG_NO")); // 설문지관리번호
		String relmMngNo = relm.get("QUSTNB_RELM_MNG_NO");
//		int RELM_SCORE = (int) calSuport.get("GRDNG_SCORE");
		int RELM_SCORE = Integer.parseInt(String.valueOf(calSuport.get("GRDNG_SCORE")));
		log.debug("########## 디딤 이외 RELM_SCORE = " + RELM_SCORE);
		double GRDNG_SCORE = 0;
		// 영역 채점 정보. SBB400 기준으로 수정
		List<Map<String, Object>> calList = survshtMmnMapper.selectRelmGrdngCrtrList(calSuport);
		log.debug("##########  디딤 이외 calList.size() = " + calList.size());
		if (0 < calList.size()) {
			for (int i=0;i<calList.size();i++) {
				Map<String, Object> temp = calList.get(i);
				String calRelmMngNo = (String) temp.get("QUSTNB_RELM_MNG_NO");
//				int BGNG_SCORE = (int) temp.get("BGNG_SCORE");
//				int END_SCORE = (int) temp.get("END_SCORE");

				// null 이여서 오류가 떨어짐 없으면 SBB400에 시작 점수와 종료점수가 없다면. 0으로 치환
				int BGNG_SCORE = Integer.parseInt(StringNvlReturn(String.valueOf(temp.get("BGNG_SCORE")))); //java.math.BigDecimal cannot be cast to java.lang.Integer 대응
				int END_SCORE = Integer.parseInt(StringNvlReturn(String.valueOf(temp.get("END_SCORE"))));
				// 영역명이 같다면.
				if (relmMngNo.equals(calRelmMngNo)) {
					if ((BGNG_SCORE <= RELM_SCORE) && (RELM_SCORE <= END_SCORE)) {
						// 결과
						param.put("RESULT_CRTR_SE_CD", temp.get("GRDNG_RESULT_SE_CD"));
					}

				}

//				int GRDNG_CNT = (int) calSuport.get("GRDNG_CNT");
				int GRDNG_CNT = Integer.parseInt(String.valueOf(calSuport.get("GRDNG_CNT")));
				if (null != relm.get("GRDNG_CRTR_SE_CD") && !"".equals(relm.get("GRDNG_CRTR_SE_CD"))) {
					if ("01".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 합계
						GRDNG_SCORE = RELM_SCORE;
					} else if ("02".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 개수
						GRDNG_SCORE = GRDNG_CNT;
					} else if ("03".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 평균
//						GRDNG_SCORE = RELM_SCORE / ((int)calSuport.get("END_QESITM_NO") - (int) calSuport.get("BGNG_QESITM_NO"));
						GRDNG_SCORE = RELM_SCORE / Integer.parseInt(String.valueOf(calSuport.get("END_QESITM_NO"))) - Integer.parseInt(String.valueOf(calSuport.get("BGNG_QESITM_NO")));
					} else if ("04".equals(relm.get("GRDNG_CRTR_SE_CD"))) { // 특수
						// 특수건인데 상단의 계산 식값 등록이 안되어있는 경우. 합계로 노출한다.
						GRDNG_SCORE = RELM_SCORE;
					}
				}

				param.put("QUSTNB_MNG_NO", calSuport.get("QUSTNB_MNG_NO"));
				param.put("QUSTNB_TMPT_MNG_NO", calSuport.get("QUSTNB_TMPT_MNG_NO"));
				param.put("SRVY_RSPNS_MNG_NO", calSuport.get("SRVY_RSPNS_MNG_NO"));
				param.put("QUSTNB_GRDNG_RELM_MNG_NO", calSuport.get("QUSTNB_GRDNG_RELM_MNG_NO"));
				param.put("UNT_TASKWK_SE_CD", calSuport.get("UNT_TASKWK_SE_CD"));
				param.put("GRDNG_SCORE", GRDNG_SCORE); // 총점.
				param.put("USER_ID", calSuport.get("USER_ID"));

//				survshtMmnMapper.insertGrdngResult(param);
				survshtMmnMapper.saveGrdngResult(param);

			}
		} else {
			// 등록이 안되어 있는 경우.

			param.put("QUSTNB_MNG_NO", calSuport.get("QUSTNB_MNG_NO"));
			param.put("QUSTNB_TMPT_MNG_NO", calSuport.get("QUSTNB_TMPT_MNG_NO"));
			param.put("SRVY_RSPNS_MNG_NO", calSuport.get("SRVY_RSPNS_MNG_NO"));
			param.put("QUSTNB_GRDNG_RELM_MNG_NO", calSuport.get("QUSTNB_GRDNG_RELM_MNG_NO"));
			param.put("UNT_TASKWK_SE_CD", calSuport.get("UNT_TASKWK_SE_CD"));
			param.put("GRDNG_SCORE", RELM_SCORE); // 총점.
			param.put("USER_ID", calSuport.get("USER_ID"));

//			survshtMmnMapper.insertGrdngResult(param);
			survshtMmnMapper.saveGrdngResult(param);
		}
	}

	/**
	 *
	 * @Method명   : copySurvshtTmptData
	 * @param request
	 * @param map
	 * @return map
	 * @작성자     : TaesooSong
	 * @작성일     : 2022. 10. 14.
	 * @Method설명 : 설문지 템플릿을 복사해 생성된 설문지 ID를 리턴 해준다.
	 * 				QUSTNB_TMPT_MNG_NO (설문템플릿번호), QUSTNB_MNG_NO (설문지번호)
	 */
	public Map<String, Object> processSurvshtTmptData(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();


		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String userNm = "";
		String grdngNo = "";
		Integer authMenuNo = 0;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			userNm = loginVO.getUserName();
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}

		// 선택된 체크 박스 갯수 만큼 복사한다.
		// Map<String, String> rowMap = dsList.get(i).toMap();
		String qustnbTmptMngNo = (String) map.get("QUSTNB_TMPT_MNG_NO");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("QUSTNB_TMPT_MNG_NO", qustnbTmptMngNo);
		// SBB000 설문지 정보 가져오기.
		Map<String, Object> qustnbMap = survshtMmnMapper.selectQustnbTmptMngNoInfo(param);

		// QUSTNB_MNG_NO 번호 채번
		Map<String, String> mngNoMap = new HashMap<String, String>();
		mngNoMap.put("USER_ID", userId);
		mngNoMap.put("SYS_CD", "SN");
		String qustnbNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

		qustnbMap.put("QUSTNB_MNG_NO", qustnbNo);
		qustnbMap.put("WRTR_ID", userId);
		qustnbMap.put("WRTR_NM", userNm);
		qustnbMap.put("SRVY_PRGRS_STTS_SE_CD", "05"); // 복사완료 상태
		qustnbMap.put("MENU_NO", String.valueOf(authMenuNo));


		//SBB100 등록.
		log.debug(qustnbMap.toString());
		survshtMmnMapper.insertQustnbMng(qustnbMap);

		// SBB010 설문지문항템플릿 정보 가져오기.
		List<Map<String, Object>> qesitmList = survshtMmnMapper.selectSurvshtQesitmTmptList(qustnbMap);

		for(int j=0;j<qesitmList.size();j++) {
			Map<String, Object> qesitmMap = qesitmList.get(j);
			qesitmMap.put("USER_ID", userId);
			qesitmMap.put("QUSTNB_MNG_NO", qustnbNo);
			// SBB300 설문지 문항 등록
			log.debug(qesitmMap.toString());
			survshtMmnMapper.insertQustnbMngList(qesitmMap);
		}

		// SBB020 설문영역설정템플릿 정보 가져오기.
		List<Map<String, Object>> qesitmRelmList = survshtMmnMapper.selectQesitmQustnbTmptCheckList(qustnbMap);

		for(int j=0;j<qesitmRelmList.size();j++) {
			Map<String, Object> qesitmRelmMap = qesitmRelmList.get(j);
			qesitmRelmMap.put("QUSTNB_MNG_NO", qustnbNo);
			qesitmRelmMap.put("USER_ID", userId);
			// SBB120 설문영역설정 등록
			log.debug(qesitmRelmMap.toString());
			survshtMmnMapper.insertQustnbRelmMng(qesitmRelmMap);
		}

		// SBB030 채점영역설정템플릿 정보 가져오기.
		List<Map<String, Object>> qesitmGrdngRelmList = survshtMmnMapper.selectQuestnbRelmTmptList(qustnbMap);

		for(int j=0;j<qesitmGrdngRelmList.size();j++) {
			Map<String, Object> qesitmGrdngRelmMap = qesitmGrdngRelmList.get(j);
			qesitmGrdngRelmMap.put("QUSTNB_MNG_NO", qustnbNo);
			qesitmGrdngRelmMap.put("USER_ID", userId);
			// SBB130 채점영역설정 등록.
			log.debug(qesitmGrdngRelmMap.toString());
			survshtMmnMapper.insertQustnbGrdngList(qesitmGrdngRelmMap);
		}

		// SBB040 영역별배점기준템플릿 정보 가져오기
		List<Map<String, Object>> grdngResultList = survshtMmnMapper.selectResultCrtrTmptList(qustnbMap);
		for(int j=0;j<grdngResultList.size();j++) {
			Map<String, Object> grdngResultMap = grdngResultList.get(j);
			grdngResultMap.put("QUSTNB_MNG_NO", qustnbNo);
			grdngResultMap.put("USER_ID", userId);
			// SBB400 영영별배점기준 등록
			log.debug(grdngResultMap.toString());
			survshtMmnMapper.insertResultCrtrObjectList(grdngResultMap);
		}

		// // 리턴해줄 값 담아두기.
		result.put("QUSTNB_TMPT_MNG_NO", qustnbTmptMngNo);
		result.put("QUSTNB_MNG_NO", qustnbNo);

		return result;
	}

	String StringNvlReturn(String val) {
		String returnVal = val;
		log.debug(returnVal);
		if (returnVal == null || returnVal.isEmpty() || "null".equals(returnVal)) {
			returnVal = "0";
		}
		log.debug(returnVal);
		return returnVal;
	}

	public Map<String, Object> sendSrvyMsg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String userNm = "";
		String grdngNo = "";
		String instNo = "";
		String untTaskwkSeCd = "";
		String esntalParam = "";
		Integer authMenuNo = 0;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			userNm = loginVO.getUserName();
			instNo = loginVO.getInstNo().toString();
			untTaskwkSeCd = loginVO.getUntTaskwkSeCd();
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}

		ParameterGroup dmSearchParamGroup = dataRequest.getParameterGroup("dmSendSrvyMsgInfo");
		Map<String, String> dmParam = dmSearchParamGroup.getSingleValueMap();


		dmParam.put("INST_NO", instNo);
		Map<String, String> temp = new HashMap<String, String>();
		if ("".equals(dmParam.get("QUSTNB_TMPT_MNG_NO")) || null == dmParam.get("QUSTNB_TMPT_MNG_NO")) {
			if (!("".equals(dmParam.get("QUSNTB_MNG_NO"))) || null != dmParam.get("QUSNTB_MNG_NO")) {
				temp = survshtMmnMapper.getQustnbTmptNoInfo(dmParam);

				dmParam.put("QUSTNB_TMPT_MNG_NO", temp.get("QUSTNB_TMPT_MNG_NO"));
			}
		}

		String sRprsTelno = survshtMmnMapper.selectRprsTelno(dmParam);
		param.put("QUSTNB_TMPT_MNG_NO", dmParam.get("QUSTNB_TMPT_MNG_NO"));
		Map<String, Object> trprInfo = survshtMmnMapper.selectTrprInfoDtl(dmParam);
		// MBL_TELNO
		// 대상자 휴대전화 번호가 존재 할 경우에만 발송.
		if (!("".equals(trprInfo.get("MBL_TELNO"))) && null != trprInfo.get("MBL_TELNO")) {
			//////////////////////////////////////////////////////////////////////////////
			////                        설문지 정보 셋팅                              ////
			//////////////////////////////////////////////////////////////////////////////
			// 설문지 복사여부 체크.
			if ("".equals(dmParam.get("QUSNTB_MNG_NO")) || null == dmParam.get("QUSNTB_MNG_NO")) {
				// 설문지 복사
				result = survshtMmnService.processSurvshtTmptData(request, dataRequest, param);
			}

			trprInfo.put("QUSTNB_TMPT_MNG_NO", result.get("QUSTNB_TMPT_MNG_NO"));
			trprInfo.put("QUSTNB_MNG_NO", result.get("QUSTNB_MNG_NO"));
			trprInfo.put("USER_ID", userId);

			//////////////////////////////////////////////////////////////////////////////
			////                        고유 정보 셋팅                              ////
			//////////////////////////////////////////////////////////////////////////////
			if ("U01".equals(untTaskwkSeCd)) {
				if (!"".equals(dmParam.get("ADD_PARAM1"))) {
					trprInfo.put("QUSTNB_SHAPE_SE_CD", dmParam.get("ADD_PARAM1"));
				}
			} else if ("U02".equals(untTaskwkSeCd)) { // 청소년상담복지센터
				if ("03".equals(dmParam.get("ADD_PARAM1"))) { // 청소년안전망효과성 평가
					trprInfo.put("QUSTNB_SHAPE_SE_CD", dmParam.get("ADD_PARAM1"));
				}
			}

			// SBB110 설문대상자 정보 저장
			survshtMmnMapper.insertCaseMngTrprInfo(trprInfo);

			//////////////////////////////////////////////////////////////////////////////
			////                        문자발송 정보 셋팅                            ////
			//////////////////////////////////////////////////////////////////////////////

			Map<String, String> outsdSrvyPtcptnParam = new HashMap<String, String>();

			outsdSrvyPtcptnParam.put("MSG_TEMP", "[설문지작성]\n설문지 참여를 해주세요.\n");
			outsdSrvyPtcptnParam.put("PATH", "/isry/itgcm/outsdsrvyptcptn/outsdSrvyCmmnsPtcptnWrite.do");
			outsdSrvyPtcptnParam.put("CASE_MNG_NO", dmParam.get("CASE_MNG_NO").toString());
			outsdSrvyPtcptnParam.put("CASE_MNG_ODRNO", dmParam.get("CASE_MNG_ODRNO").toString());
			outsdSrvyPtcptnParam.put("QUSTNB_TMPT_MNG_NO", param.get("QUSTNB_TMPT_MNG_NO").toString());
			outsdSrvyPtcptnParam.put("QUSTNB_MNG_NO", result.get("QUSTNB_MNG_NO").toString());
			outsdSrvyPtcptnParam.put("TRPR_INFO_NO", trprInfo.get("TRPR_INFO_NO").toString());
			outsdSrvyPtcptnParam.put("ADD_PARAM1", dmParam.get("ADD_PARAM1").toString());
			outsdSrvyPtcptnParam.put("ADD_PARAM2", dmParam.get("ADD_PARAM2").toString());
			outsdSrvyPtcptnParam.put("ADD_PARAM3", dmParam.get("ADD_PARAM3").toString());
			outsdSrvyPtcptnParam.put("UNT_TASKWK_SE_CD", trprInfo.get("TASKWK_SYS_SE_CD").toString());
			outsdSrvyPtcptnParam.put("USER_ID", userId);
			outsdSrvyPtcptnParam.put("SXDC_SE_CD", trprInfo.get("SXDC_SE_CD").toString());
			// outsdSrvyPtcptnParam.put("MNGR_YN", userParam.get("MNGR_YN").toString());

			String sendMsg = outsdSrvyPtcptnService.getSrvySendMsg(outsdSrvyPtcptnParam);

			//////////////////////////////////////////////////////////////////////////////
			////                               문자 발송                              ////
			//////////////////////////////////////////////////////////////////////////////
			//CASE_TRPR_NM_ENCPT

			param.put("RSVT_CHRCTR_CN", sendMsg); // 문자발송 내용

			survshtMmnMapper.insertQustnbMmsContentsInfo(param);
			String mblTelno = trprInfo.get("MBL_TELNO").toString();
			mblTelno = mblTelno.replace("-", "");
			param.put("CONT_SEQ", param.get("CONT_SEQ")); // MMS 컨텐츠 키
			param.put("FRST_RGTR_ID", userId);
			param.put("LAST_MDFR_ID", userId);
			param.put("DSPTCH_TRPR_NM_ENCPT", trprInfo.get("CASE_TRPR_NM_ENCPT")); // 발신대상자명암호화
			param.put("CALL_FROM", mblTelno); // 발신휴대전화번호
			param.put("DSPTCH_MBL_TELNO_ENCPT", sRprsTelno); // 발신휴대전화번호암호화
			param.put("TRNSMI_INST_NO", instNo); // 송신기관번호

			survshtMmnMapper.insertQustnbMsgData(param);

		} else {
			result.put("SEND_YN", "N");
		}

		return result;
	}

	public Map<String, Object> selectPreSurvshtResponseList(String QUSTNB_MNG_NO) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("QUSTNB_MNG_NO", QUSTNB_MNG_NO);

		// 관리자에서 설문지 미리 보기 및 미리보기가 아닌 실제 발송된 설문지인 경우
		List<Map<String, Object>> list = survshtMmnMapper.selectPreSurvshtList(param);

		List<Map<String, Object>> list2 = survshtMmnMapper.selectPreSurvshtDtlList(param);

		List<Map<String, Object>> list3 = survshtMmnMapper.selectPreSurvshtDtlRelmList(param);

		// 응답 여부 체크 누락   2022-10-25
		//String SRVY_RSPNS_MNG_NO = survshtMmnMapper.selectSurvshtSrvyRspnsMngNo(param);

		// 지속적인 오류 발생으로 인해 임시 조치 20230620 송태수
		String SRVY_RSPNS_MNG_NO = null;
		List<Map<String, String>> srvyRspnsMngNo = survshtMmnMapper.selectSurvshtSrvyRspnsMngNo2(param);
		if(srvyRspnsMngNo.size() != 0) {
			SRVY_RSPNS_MNG_NO = srvyRspnsMngNo.get(0).get("SRVY_RSPNS_MNG_NO");
		}

		param.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
		// 응답 정보 가져오기 추가.
		Map<String, Object> srvyRspnsInfo = survshtMmnMapper.selectSurvshtSrvyRspnsMngInfo(param);

		log.debug(SRVY_RSPNS_MNG_NO);
		List<Map<String, Object>> dsList = new ArrayList<Map<String, Object>>();

		if(null != SRVY_RSPNS_MNG_NO && !"".equals(SRVY_RSPNS_MNG_NO)) {
			param.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
			List<Map<String, Object>> chkList = survshtMmnMapper.selectChkSurvshtList(param);

			for(int i=0;i<list.size();i++) {
				Map<String, Object> listMap = list.get(i);

				String QESITM_MNG_NO = (String) listMap.get("QESITM_MNG_NO");
				for (int j=0;j<chkList.size();j++) {
					Map<String, Object> temp = chkList.get(j);
					String CHK_QESITM_MNG_NO = (String) temp.get("QESITM_MNG_NO");
					if (QESITM_MNG_NO.equals(CHK_QESITM_MNG_NO)) {
						listMap.put("QESITM_SINGL_RSPNS_VALUE", temp.get("QESITM_SINGL_RSPNS_VALUE"));
						listMap.put("QESITM_COMPNO_RSPNS_VALUE", temp.get("QESITM_COMPNO_RSPNS_VALUE"));
						listMap.put("QESITM_RSPNS_CN", temp.get("QESITM_RSPNS_CN"));
						listMap.put("SRVY_RSPNS_MNG_NO", SRVY_RSPNS_MNG_NO);
						listMap.put("RSPNS_DT", srvyRspnsInfo.get("RSPNS_DT"));
						dsList.add(listMap);
					}
				}
			}
		} else {// 응답정보가 없을 경우.
			dsList = list;
		}

		result.put("srvyRspnsInfo", srvyRspnsInfo);
		result.put("dsList", dsList);

		result.put("ds1", list2);
		result.put("ds2", list3);

		return result;
	}

}
