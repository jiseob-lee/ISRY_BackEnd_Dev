/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.lvingmng.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csemd.mdlrtrehabcrsemng.lvingmng.mapper.LvingMngMapper;
import isry.csemd.mdlrtrehabcrsemng.lvingmng.service.LvingMngService;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;

/**
 * @파일명 : LvingMngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Service("lvingMngService")
public class LvingMngServiceImpl implements LvingMngService {

	@Resource(name = "lvingMngMapper")
	private LvingMngMapper lvingMngMapper;

	// 채번
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectRenuNo
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 28.
	 * @Method설명 : 발급번호채번
	 */
	@Override
	public String selectRenuNo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String sUserId = loginVO.getId();

		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();

		seqMap.put("USER_ID", sUserId);
		seqMap.put("RENU_NO_SE_CD", "CH");
		seqMap.put("RENU_YMD", DateUtil.getToday());

		// 채번서비스 호출
		valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
		String sRenuNo = String.valueOf(valMap.get("RENU_NO")); // 발번
		// 채번완료

		return sRenuNo;
	}

	/**
	 * @Method명 : selectPic
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : 생활동 담당자 콤보데이터
	 */
	@Override
	public List<Map<String, String>> selectPic(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		paramMap.put("ENFSN_NO", userVo.getEnfsnNo());
		paramMap.put("GROUP_AUTHRT_SE_CD", userVo.getGroupAuthrtSeCd());
		paramMap.put("INST_NO", userVo.getInstNo());
		paramMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());

		return lvingMngMapper.selectPic(paramMap);
	}

	/**
	 * @Method명 : selectSrvcExcnBiz
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 서비스실행사업 콤보 조회
	 */
	@Override
	public List<Map<String, String>> selectSrvcExcnBiz(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		return lvingMngMapper.selectSrvcExcnBiz(paramMap);
	}

	/**
	 * @Method명 : selectBizYr
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 사업년도 콤보조회
	 */
	@Override
	public List<Map<String, String>> selectBizYr(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		return lvingMngMapper.selectBizYr(paramMap);
	}

	/**
	 * @Method명 : selectEnfsn
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 생활동 담당자를 지정하기 위한 종사자 조회
	 */
	@Override
	public List<Map<String, String>> selectEnfsn(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		paramMap.put("INST_NO", userVo.getInstNo());
		paramMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());
		
		return lvingMngMapper.selectEnfsn(paramMap);
	}

	/**
	 * @Method명 : selectDormitForSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 17.
	 * @Method설명 : 저장된 생활동 목록 조회
	 */
	@Override
	public List<Map<String, String>> selectDormitForSearch(HttpServletRequest request) throws Exception {
		return lvingMngMapper.selectDormitForSearch();
	}

	/**
	 * @Method명 : selectInst
	 * @param request
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 기관 목록 조회
	 */
	@Override
	public List<Map<String, String>> selectInst(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		paramMap.put("INST_NO", String.valueOf(userVo.getInstNo()));

		return lvingMngMapper.selectInst(paramMap);
	}

	/**
	 * @Method명 : selectTakingEra
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 복용시기 체크박스 리스트 조회
	 */
	@Override
	public List<Map<String, String>> selectTakingEra() throws Exception {

		return lvingMngMapper.selectTakingEra();
	}

	/**
	 * @Method명 : selectWorkDiaryList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 근무일지 목록 조회
	 */
	@Override
	public void selectWorkDiaryList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);

		List<Map<String, String>> returnData = lvingMngMapper.selectWorkDiaryList(paramMap);
		if (!returnData.isEmpty()) {
			for (Map<String, String> map : returnData) {
				if (map.containsKey("FLNM_ENCPT") && map.get("FLNM_ENCPT") != null
						&& !map.get("FLNM_ENCPT").equals("")) {
					String[] arr1 = map.get("FLNM_ENCPT").split(",");
					for (int i = 0; i < arr1.length; i++) {
						arr1[i] = Masking.nameMasking(arr1[i]);
					}
					Arrays.sort(arr1);
					map.put("FLNM_ENCPT", String.join(",", arr1));
				}
			}
		}
		dataRequest.setResponse("dsList", returnData);
	}

	/**
	 * @Method명 : selectWorkDiary
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 23.
	 * @Method설명 : 근무일지 상세조회
	 */
	@Override
	public void selectWorkDiary(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = parameterGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		// 근무일지
		List<Map<String, String>> dsWorkDiary = lvingMngMapper.selectWorkDiary(mapParam);
		// 시간대별근무내용
		List<Map<String, String>> dsWorkCnList = lvingMngMapper.selectWorkCnList(mapParam);
		// 청소년관찰기록
		List<Map<String, String>> dsYngbgsObservRcordList = lvingMngMapper.selectYngbgsObservRcordList(mapParam);

		if (dsWorkCnList != null) {
			for (Map<String, String> map : dsWorkCnList) {
				map.replace("FLNM_ENCPT", Masking.nameMasking(map.get("FLNM_ENCPT")));
			}
		}
		if (dsYngbgsObservRcordList != null) {
			for (Map<String, String> map : dsYngbgsObservRcordList) {
				map.replace("TRPR_NM_ENCPT", Masking.nameMasking(map.get("TRPR_NM_ENCPT")));
			}
		}

		List<Map<String, String>> dsTkcgRelmPicList = lvingMngMapper.selectCheckTkcgRelmNight(mapParam);

		dataRequest.setResponse("dsWorkDiary", dsWorkDiary);
		dataRequest.setResponse("dsWorkCnList", dsWorkCnList);
		dataRequest.setResponse("dsYngbgsObservRcordList", dsYngbgsObservRcordList);
		dataRequest.setResponse("dsTkcgRelmPicList", dsTkcgRelmPicList);
	}

	/**
	 * @Method명 : selectYngbgsObservRcord
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : (등록 시) 근무일지 존재 유무 체크, 청소년 관찰기록 목록 조회
	 */
	@Override
	public void selectYngbgsObservRcord(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> mapParam = dataRequest.getParameterGroup("dsWorkDiary").getSingleValueMap();
		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> dsYngbgsObservRcordList = lvingMngMapper.selectYngbgsList(mapParam);

		if (dsYngbgsObservRcordList != null) {
			for (Map<String, String> map : dsYngbgsObservRcordList) {
				map.replace("TRPR_NM_ENCPT", Masking.nameMasking(map.get("TRPR_NM_ENCPT")));
			}
		}

		dataRequest.setResponse("dsYngbgsObservRcordList", dsYngbgsObservRcordList);
	}

	/**
	 * @Method명 : saveWorkDiary
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 23.
	 * @Method설명 : 근무일지 수정/저장
	 */
	@Override
	public void saveWorkDiary(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsWorkDiary");
		ParameterGroup parameterGroup2 = dataRequest.getParameterGroup("dsWorkCnList");
		ParameterGroup parameterGroup3 = dataRequest.getParameterGroup("dsYngbgsObservRcordList");

		List<Map<String, String>> insertedWorkDiary = parameterGroup.getInsertedRowList();
		List<Map<String, String>> insertedWorkCnList = parameterGroup2.getInsertedRowList();
		List<Map<String, String>> insertedYngbgsObservRcordList = parameterGroup3.getInsertedRowList();
		List<Map<String, String>> updatedWorkDiary = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> updatedWorkCnList = parameterGroup2.getUpdatedRowList();
		List<Map<String, String>> updatedYngbgsObservRcordList = parameterGroup3.getUpdatedRowList();
		List<Map<String, String>> deletedWorkCnList = parameterGroup2.getDeletedRowList();

		int chk = 0;
		if (!insertedWorkDiary.isEmpty()) 
			chk = lvingMngMapper.selectWorkDiaryExist(insertedWorkDiary.get(0));

		if (chk > 0) {

			Map<String, Object> msg = new HashMap<String, Object>();
			msg.put("msg", "해당날짜에 이미 저장된 근무일지가 있습니다.");

			dataRequest.setMetadata(true, msg);
		} else {

			// 근무일지 추가
			for (Map<String, String> map : insertedWorkDiary) {

				map.put("USER_ID", userVo.getId());

				lvingMngMapper.insertWorkDiary(map);
			}

			// 시간별근무내용 추가
			if (!insertedWorkCnList.isEmpty()) {

				for (Map<String, String> map : insertedWorkCnList) {

					map.put("INST_NO", String.valueOf(userVo.getInstNo()));
					map.put("USER_ID", userVo.getId());
				}

				lvingMngMapper.insertWorkCn(insertedWorkCnList);
			}

			// 청소년관찰기록 추가
			if (!insertedYngbgsObservRcordList.isEmpty()) {

				for (Map<String, String> map : insertedYngbgsObservRcordList) {

					// 채번 후 복수선택유형 데이터 삽입
					String renuNo = selectRenuNo(request, dataRequest);
					Map<String, String> CompnoChc = new HashMap<String, String>();
					CompnoChc.put("COMPNO_CHC_TYPE_MNG_NO", renuNo);
					CompnoChc.put("USER_ID", userVo.getId());

					lvingMngMapper.insertCompnoChc(CompnoChc);

					List<Map<String, String>> compList = new ArrayList<Map<String, String>>();
					String arr[] = map.get("PERIOD_MEDCIN_TAKNG").split(",");
					if (!arr[0].isEmpty()) {
						for (int i = 0; i < arr.length; i++) {
							Map<String, String> compMap = new HashMap<String, String>();
							compMap.put("COMPNO_CHC_TYPE_MNG_NO", renuNo);
							compMap.put("COMPNO_TYPE_SCLAS_SE_CD", arr[i]);
							compMap.put("USER_ID", userVo.getId());
							compList.add(compMap);
						}

						lvingMngMapper.insertCompnoChcDtl(compList);
					}

					map.put("COMPNO_CHC_TYPE_MNG_NO", renuNo);
					map.put("USER_ID", userVo.getId());
				}

				lvingMngMapper.insertYngbgsObservRcord(insertedYngbgsObservRcordList);
			}

			// 근무일지 수정(관리자확인)
			for (Map<String, String> map : updatedWorkDiary) {

				map.put("USER_ID", userVo.getId());
				lvingMngMapper.updateWorkDiary(map);
			}

			// 근무내용 수정
			if (!updatedWorkCnList.isEmpty()) {

				for (Map<String, String> map : updatedWorkCnList) {
					map.put("INST_NO", String.valueOf(userVo.getInstNo()));
					map.put("USER_ID", userVo.getId());
				}

				lvingMngMapper.updateWorkCn(updatedWorkCnList);
			}

			if (!deletedWorkCnList.isEmpty())
				lvingMngMapper.deleteWorkCn(deletedWorkCnList);

			// 청소년관찰기록 수정
			if (!updatedYngbgsObservRcordList.isEmpty()) {
				for (Map<String, String> map : updatedYngbgsObservRcordList) {

					// 복수선택유형상세 초기화
					lvingMngMapper.deleteCompnoChcDtl(map);

					List<Map<String, String>> compList = new ArrayList<Map<String, String>>();
					String arr[] = map.get("PERIOD_MEDCIN_TAKNG").split(",");
					if (!arr[0].isEmpty()) {
						for (int i = 0; i < arr.length; i++) {
							Map<String, String> compMap = new HashMap<String, String>();
							compMap.put("COMPNO_CHC_TYPE_MNG_NO", map.get("COMPNO_CHC_TYPE_MNG_NO"));
							compMap.put("COMPNO_TYPE_SCLAS_SE_CD", arr[i]);
							compMap.put("USER_ID", userVo.getId());
							compList.add(compMap);
						}

						lvingMngMapper.insertCompnoChcDtl(compList);
					}

					map.put("USER_ID", userVo.getId());
				}
				lvingMngMapper.updateYngbgsObservRcord(updatedYngbgsObservRcordList);
			}
		}
	}

	/**
	 * @Method명 : updateWorkDiaryAprv
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 12. 5.
	 * @Method설명 :
	 */
	@Override
	public void updateWorkDiaryAprv(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsWorkDiary");
		Map<String, String> mapList = parameterGroup.getAllRowList().get(0);

		lvingMngMapper.updateWorkDiaryAprv(mapList);

	}

	/**
	 * @Method명 : selectDayChckList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 일일점검표 목록 조회
	 */
	@Override
	public void selectDayChckList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);

		List<Map<String, String>> returnData = lvingMngMapper.selectDayChckList(paramMap);
		for (Map<String, String> map : returnData) {
			map.replace("NIGHT_PIC_NO1_FLNM_ENCPT", Masking.nameMasking(map.get("NIGHT_PIC_NO1_FLNM_ENCPT")));
			map.replace("NIGHT_PIC_NO2_FLNM_ENCPT", Masking.nameMasking(map.get("NIGHT_PIC_NO2_FLNM_ENCPT")));
		}
		dataRequest.setResponse("dsList", returnData);
	}

	/**
	 * @Method명 : selectDayChck
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 일일점검표 상세 조회
	 */
	@Override
	public void selectDayChck(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);

		List<Map<String, String>> chckList = lvingMngMapper.selectDayChck(paramMap);
		List<Map<String, String>> dayChckList = new ArrayList<Map<String, String>>();

		if (chckList.isEmpty()) {
			chckList = lvingMngMapper.selectDayChckToInsert();
		} else {
			dayChckList.add(chckList.get(0));
		}

		List<Map<String, String>> fcltyChckList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> fireChckList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> snitatMngList = new ArrayList<Map<String, String>>();

		for (Map<String, String> map : chckList) {
			if (!map.isEmpty()) {
				if (map.get("UP_CMMNS_CD_VALUE").equals("01")) {
					fcltyChckList.add(map);
				} else if (map.get("UP_CMMNS_CD_VALUE").equals("02")) {
					fireChckList.add(map);
				} else if (map.get("UP_CMMNS_CD_VALUE").equals("03")) {
					snitatMngList.add(map);
				}
			}
		}

		List<Map<String, String>> dsTkcgRelmPicList = lvingMngMapper.selectCheckTkcgRelmNight(paramMap);

		dataRequest.setResponse("dsDayChckList", dayChckList);
		dataRequest.setResponse("dsFcltyChckList", fcltyChckList);
		dataRequest.setResponse("dsFireChckList", fireChckList);
		dataRequest.setResponse("dsSnitatMngList", snitatMngList);
		dataRequest.setResponse("dsTkcgRelmPicList", dsTkcgRelmPicList);
	}

	/**
	 * @Method명 : selectDayChckExist
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 1.
	 * @Method설명 : 일일점검표가 존재하는지 조회
	 */
	@Override
	public void selectDayChckExist(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);

		List<Map<String, String>> dayChckList = lvingMngMapper.selectDayChckList(paramMap);
		dataRequest.setResponse("dsDayChckList", dayChckList);
	}

	/**
	 * @Method명 : saveDayChck
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 일일점검표 저장/수정/삭제(삭제는 아직 미작성)
	 */
	@Override
	public void saveDayChck(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsDayChckList");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsFcltyChckList");
		ParameterGroup paramGroup3 = dataRequest.getParameterGroup("dsFireChckList");
		ParameterGroup paramGroup4 = dataRequest.getParameterGroup("dsSnitatMngList");
		String createYn = dataRequest.getParameter("createYn");

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		// 점검표를 하나로 합치기 위한 리스트
		List<Map<String, String>> insertedChckList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> updatedChckList = new ArrayList<Map<String, String>>();

		List<Map<String, String>> insertedDayChckList = paramGroup.getInsertedRowList();
		List<Map<String, String>> updatedDayChckList = paramGroup.getUpdatedRowList();

		insertedChckList.addAll(paramGroup2.getInsertedRowList());
		insertedChckList.addAll(paramGroup3.getInsertedRowList());
		insertedChckList.addAll(paramGroup4.getInsertedRowList());

		updatedChckList.addAll(paramGroup2.getUpdatedRowList());
		updatedChckList.addAll(paramGroup3.getUpdatedRowList());
		updatedChckList.addAll(paramGroup4.getUpdatedRowList());

		if (!insertedDayChckList.isEmpty()) {
			insertedDayChckList.get(0).put("USER_ID", userVo.getId());
			insertedDayChckList.get(0).put("INST_NO", String.valueOf(userVo.getInstNo()));
			lvingMngMapper.insertDayChckList(insertedDayChckList.get(0));
		}

		if (!insertedChckList.isEmpty()) {
			for (Map<String, String> map : insertedChckList) {
				map.put("USER_ID", userVo.getId());
				map.put("SRVC_EXCN_BIZ_NO", insertedDayChckList.get(0).get("SRVC_EXCN_BIZ_NO"));
				map.put("ALTMNT_GROUP_SCLAS_SE_CD", insertedDayChckList.get(0).get("ALTMNT_GROUP_SCLAS_SE_CD"));
				map.put("WRT_YMD", insertedDayChckList.get(0).get("WRT_YMD"));
			}
			lvingMngMapper.insertChckList(insertedChckList);
		}

		if (!updatedDayChckList.isEmpty()) {
			updatedDayChckList.get(0).put("USER_ID", userVo.getId());
			if (createYn != null && createYn != "") {
				updatedDayChckList.get(0).put("WRT_YMD__origin", createYn);
			}
			lvingMngMapper.updateDayChckList(updatedDayChckList.get(0));
		}

		if (!updatedChckList.isEmpty()) {
			for (Map<String, String> map : updatedChckList) {
				map.put("USER_ID", userVo.getId());
				map.put("SRVC_EXCN_BIZ_NO", updatedDayChckList.get(0).get("SRVC_EXCN_BIZ_NO"));
				map.put("ALTMNT_GROUP_SCLAS_SE_CD", updatedDayChckList.get(0).get("ALTMNT_GROUP_SCLAS_SE_CD"));
				map.put("WRT_YMD", updatedDayChckList.get(0).get("WRT_YMD"));
			}
			lvingMngMapper.updateChckList(updatedChckList);
		}
	}

	/**
	 * @Method명 : selectDormitList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 생활동 목록 조회
	 */
	@Override
	public void selectDormitList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);

		dataRequest.setResponse("dsDormitList", lvingMngMapper.selectDormitList(paramMap));
	}

	/**
	 * @Method명 : selectDormitNowStrdcList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 생활동 현재 거주현황 목록 조회
	 */
	@Override
	public void selectDormitNowStrdcList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch2");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> returnData = lvingMngMapper.selectDormitNowStrdcList(paramMap);
		for (Map<String, String> map : returnData) {
			map.replace("TRPR_NM_ENCPT", Masking.nameMasking(map.get("TRPR_NM_ENCPT")));
		}
		dataRequest.setResponse("dsDormitNowStrdcList", returnData);
	}

	/**
	 * @Method명 : saveDormit
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 생활동 저장/수정/삭제
	 */
	@Override
	public void saveDormit(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsDormitList");
		List<Map<String, String>> insertedDormitList = paramGroup.getInsertedRowList();
		List<Map<String, String>> updatedDormitList = paramGroup.getUpdatedRowList();
		List<Map<String, String>> deletedDormitList = paramGroup.getDeletedRowList();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (!insertedDormitList.isEmpty()) {
			List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
			for (Map<String, String> map : insertedDormitList) {
				map.put("USER_ID", loginVO.getId());
				if (!map.get("INDIV_PIC_NO").isEmpty() && map.get("INDIV_PIC_NO") != null
						&& !map.get("INDIV_PIC_NO").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "01");
					paramMap.put("PIC_NO", map.get("INDIV_PIC_NO"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
				if (!map.get("WIK_PIC_NO").isEmpty() && map.get("WIK_PIC_NO") != null
						&& !map.get("WIK_PIC_NO").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "05");
					paramMap.put("PIC_NO", map.get("WIK_PIC_NO"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
				if (!map.get("NITHT_PIC_NO_A").isEmpty() && map.get("NITHT_PIC_NO_A") != null
						&& !map.get("NITHT_PIC_NO_A").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "07");
					paramMap.put("PIC_NO", map.get("NITHT_PIC_NO_A"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
				if (!map.get("NITHT_PIC_NO_B").isEmpty() && map.get("NITHT_PIC_NO_B") != null
						&& !map.get("NITHT_PIC_NO_B").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "08");
					paramMap.put("PIC_NO", map.get("NITHT_PIC_NO_B"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
			}
			lvingMngMapper.insertDormit(insertedDormitList);
			if (!paramList.isEmpty()) {
				lvingMngMapper.insertEarePic(paramList);
			}
		}
		System.err.println("구구 : " + updatedDormitList);
		if (!updatedDormitList.isEmpty()) {
			List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
			for (Map<String, String> map : updatedDormitList) {
				map.put("USER_ID", loginVO.getId());
				if (!map.get("INDIV_PIC_NO").isEmpty() && map.get("INDIV_PIC_NO") != null
						&& !map.get("INDIV_PIC_NO").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "01");
					paramMap.put("PIC_NO", map.get("INDIV_PIC_NO"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
				if (!map.get("WIK_PIC_NO").isEmpty() && map.get("WIK_PIC_NO") != null
						&& !map.get("WIK_PIC_NO").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "05");
					paramMap.put("PIC_NO", map.get("WIK_PIC_NO"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
				if (!map.get("NITHT_PIC_NO_A").isEmpty() && map.get("NITHT_PIC_NO_A") != null
						&& !map.get("NITHT_PIC_NO_A").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "07");
					paramMap.put("PIC_NO", map.get("NITHT_PIC_NO_A"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
				if (!map.get("NITHT_PIC_NO_B").isEmpty() && map.get("NITHT_PIC_NO_B") != null
						&& !map.get("NITHT_PIC_NO_B").equals("")) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.putAll(map);
					paramMap.put("TKCG_RELM_SE_CD", "08");
					paramMap.put("PIC_NO", map.get("NITHT_PIC_NO_B"));
					paramMap.put("USER_ID", loginVO.getId());
					paramMap.put("INST_NO", map.get("INST_NO"));
					paramList.add(paramMap);
				}
			}
			if (!paramList.isEmpty()) {
				for (Map<String, String> map : updatedDormitList) {
					lvingMngMapper.deleteEarePic(map);
				}
			}else {
				lvingMngMapper.deleteEarePic(updatedDormitList.get(0));
			}
			
			for (Map<String, String> map : updatedDormitList) {
				lvingMngMapper.deleteDormit(map);
			}
			
			lvingMngMapper.insertDormit(updatedDormitList);
			if (!paramList.isEmpty()) {
				lvingMngMapper.insertEarePic(paramList);
			}
		}

		if (!deletedDormitList.isEmpty()) {
			for (Map<String, String> map : deletedDormitList) {
				lvingMngMapper.deleteDormit(map);
				lvingMngMapper.deleteEarePic(map);
			}
			
		}
	}

	/**
	 * @Method명   : selectDormitListForCheck
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 1. 27. 
	 * @Method설명 : 저장된 모든 생활동 리스트
	 */
	@Override
	public void selectDormitAllList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, String>> returnMap = lvingMngMapper.selectDormitAllList();
		dataRequest.setResponse("dsDormitAllList", returnMap);
	}

}
