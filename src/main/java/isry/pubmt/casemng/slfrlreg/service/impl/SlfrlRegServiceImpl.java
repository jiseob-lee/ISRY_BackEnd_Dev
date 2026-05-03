/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmt.casemng.slfrlreg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.clipsoft.org.apache.commons.lang.StringUtils;

import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.casemng.casereg.mapper.CysnsRegMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.syscmmn.survsht.service.SurvshtSaveService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.pubms.casemng.sheltrreg.mapper.SheltrRegMapper;
import isry.pubmt.casemng.slfrlreg.mapper.SlfrlRegMapper;
//import lombok.extern.slf4j.Slf4j;
import isry.pubmt.casemng.slfrlreg.service.SlfrlRegService;

/**
 * @파일명        : SheltrRegServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("slfrlRegService")
public class SlfrlRegServiceImpl implements SlfrlRegService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "sheltrRegMapper")
	private SheltrRegMapper sheltrRegMapper;

	@Resource(name = "slfrlRegMapper")
	private SlfrlRegMapper slfrlRegMapper;

	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

	@Resource(name = "cysnsRegMapper")
	private CysnsRegMapper cysnsRegMapper;
	
	@Resource(name = "survshtSaveService")
	private SurvshtSaveService survshtSaveService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	
	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = slfrlRegMapper.selectReqById(paramMap);
		
		return result; 
	}
	
	
	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//1.업무공통영역 저장
		LOGGER.debug("================= 업무공통영역 저장 START =================");
		Map<String, Object> info = caseRegService.processData(request, dataRequest);
		LOGGER.debug("================= 업무공통영역 저장 END =================");

		String sCaseMngNo = String.valueOf(info.get("CASE_MNG_NO"));
		String sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));
		
		LOGGER.debug("================= 단위업무(자립) 저장 START =================");
		
		//사례정보
		saveCaseInfo(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//사정도구
		saveCaseJgmt(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//자립준비척도
		saveSlfrlPrpare(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//위기스크리닝결과
		saveCrisisResult(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//위기스크리닝평가점수(상세)
		saveCrisisDetail(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//심리정서환경척도
		saveTrlEmt(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//자립지원수당
		saveSprtPensn(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//임대주택지원
		saveRthousSprt(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//설문지등록
		saveSrvyData(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		
		Map<String, String> rtnMap = new HashMap<>();
		rtnMap.put("CASE_MNG_NO"   , sCaseMngNo);
		rtnMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

		return rtnMap;

	}

	/**
	 * @Method명   : saveCaseInfo
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 :
	 */
	private void saveCaseInfo(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> allRowList = param.getAllRowList();
		
		for (Map<String, String> map : allRowList) {
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("USER_ID", userId);
			
			slfrlRegMapper.saveData(map);
		}
	}

	/**
	 * @Method명   : saveCaseJgmt
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 29. 
	 * @Method설명 :
	 */
	private void saveCaseJgmt(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request));

		ParameterGroup param = dataRequest.getParameterGroup("dsCaseJgmtList");
		List<Map<String, String>> allRowList = param.getAllRowList();
		
		if (allRowList != null && allRowList.size() > 0 && 
				!StringUtils.isEmpty(allRowList.get(0).get("SLFRL_PRPARE_EVL_SCORE"))) {
			for (Map<String, String> map : allRowList) {
				map.put("CASE_MNG_NO", sCaseMngNo);
				map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				map.put("INST_NO", instNo);
				map.put("USER_ID", userId);

				slfrlRegMapper.saveCaseJgmtData(map);
			}
		}
		
	}

	/**
	 * @Method명   : saveSlfrlPrpare
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 29. 
	 * @Method설명 :
	 */
	private void saveSlfrlPrpare(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request));

		ParameterGroup param = dataRequest.getParameterGroup("dsSlfrlPrpareList");
		List<Map<String, String>> allRowList = param.getAllRowList();
		
		if (!StringUtils.isEmpty(allRowList.get(0).get("FCTR_RELM_AVRG_SCORE"))) {
			for (Map<String, String> map : allRowList) {
				map.put("CASE_MNG_NO", sCaseMngNo);
				map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				map.put("CASE_PRGRS_STTS_SE_CD", "01");
				map.put("INST_NO", instNo);
				map.put("USER_ID", userId);

				slfrlRegMapper.saveSlfrlPrpareData(map);
			}
		}
		
	}

	/**
	 * @Method명   : saveCrisisResult
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 30. 
	 * @Method설명 :
	 */
	private void saveCrisisResult(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dsCrisisScoreList");
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		List<Map<String, String>> dsList = param.getAllRowList();
		if(dsList.size() > 0) {
			for(Map<String, String> paramMap : dsList) {
				paramMap.put("CASE_MNG_NO"   , sCaseMngNo);
				paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				paramMap.put("USER_ID", userId);
				
				cysnsRegMapper.saveData(paramMap);  //위기스크리닝결과
			}
		}
		
	}
	
	/**
	 * @Method명   : saveCrisisDetail
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 30. 
	 * @Method설명 :
	 */
	private void saveCrisisDetail(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		ParameterGroup param2 = dataRequest.getParameterGroup("dsCrisisResultList");
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		List<Map<String, String>> dsCrisisResultList = param2.getAllRowList();
		if(dsCrisisResultList.size() > 0) {
			if(!StringUtils.isEmpty(dsCrisisResultList.get(0).get("RISK_FCTR_SUM_SCORE"))) {
				Map<String, String> paramMap = dsCrisisResultList.get(0);
	
				paramMap.put("CASE_MNG_NO"   , sCaseMngNo);
				paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				paramMap.put("USER_ID"		 , userId);
				
				cysnsRegMapper.saveData2(paramMap); //위기스크리닝평가점수(상세)
			}	
		}
		
	}

	/**
	 * @Method명   : saveTrlEmt
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 4. 27. 
	 * @Method설명 :
	 */
	private void saveTrlEmt(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {

		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request));

		ParameterGroup param = dataRequest.getParameterGroup("dsTrlEmtList");
		List<Map<String, String>> allRowList = param.getAllRowList();

		if (!StringUtils.isEmpty(allRowList.get(0).get("ANXIET_DEPRES_SCORE"))) {
		
			for (Map<String, String> map : allRowList) {
				map.put("CASE_MNG_NO", sCaseMngNo);
				map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				map.put("INST_NO", instNo);
				map.put("USER_ID", userId);
	
				slfrlRegMapper.saveTrlEmtData(map);
			}	
		}

	}
	
	/**
	 * @Method명   : saveSprtPensn
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	private void saveSprtPensn(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)); //
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)); //
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsSprtPensnList");
		
		List<Map<String, String>> insertedRowList = param.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("INST_NO", instNo);
			map.put("PIC_NO", enfsnNo);
			map.put("USER_ID", userId);
			
			sheltrRegMapper.saveSprtPensnData(map);
		}
		
		List<Map<String, String>> updatedRowList = param.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("INST_NO", instNo);
			map.put("PIC_NO", enfsnNo);
			map.put("USER_ID", userId);
			
			sheltrRegMapper.saveSprtPensnData(map);
		}
		
		List<Map<String, String>> deletedRowList = param.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			sheltrRegMapper.deleteSprtPensnData(map);
		}

	}

	/**
	 * @Method명   : saveRthousSprt
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	private void saveRthousSprt(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)); //
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)); //
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsRthousSprtList");
		
		List<Map<String, String>> insertedRowList = param.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("INST_NO", instNo);
			map.put("PIC_NO", enfsnNo);
			map.put("USER_ID", userId);
			
			sheltrRegMapper.saveRthousSprtData(map);
		}
		
		List<Map<String, String>> updatedRowList = param.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("INST_NO", instNo);
			map.put("PIC_NO", enfsnNo);
			map.put("USER_ID", userId);

			sheltrRegMapper.saveRthousSprtData(map);
		}
		
		List<Map<String, String>> deletedRowList = param.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			sheltrRegMapper.deleteRthousSprtData(map);
		}

	}
	
	/**
	 * @Method명   : saveSrvyData
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 30. 
	 * @Method설명 :
	 */
	private void saveSrvyData(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request));
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("CASE_MNG_NO", sCaseMngNo);
		paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
		paramMap.put("ENFSN_NO", enfsnNo);
		paramMap.put("USER_ID", userId);

		//설문지 저장
		//설문지 저장 - 자립준비사정도구
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyAsess"), dataRequest.getParameterGroup("dsSrvyRelmAsess"), paramMap, "09");
		//설문지 저장 - 자립준비척도
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyCrtron"), dataRequest.getParameterGroup("dsSrvyRelmCrtron"), paramMap, "10");
		//설문지 저장 - 심리정서환경척도
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyTrlEmt"), dataRequest.getParameterGroup("dsSrvyRelmTrlEmt"), paramMap, "19");
		//위기스크리닝 위험요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyRisk"), dataRequest.getParameterGroup("dsSrvyRelmRisk"), paramMap, "01");
		//위기스크리닝 위기요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyCrisis"), dataRequest.getParameterGroup("dsSrvyRelmCrisis"), paramMap, "02");
	}
	
	/**
	 * @Method명   : selectCaseJgmtById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 29. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCaseJgmtById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = slfrlRegMapper.selectCaseJgmtById(paramMap);
		
		return result; 
	}

	/**
	 * @Method명   : selectTrlEmtById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 29. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectTrlEmtById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		
		List<Map<String, String>> result = slfrlRegMapper.selectTrlEmtById(paramMap);
		
		return result; 
	}


	/**
	 * @Method명   : selectSlfrlPrpareById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 29. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSlfrlPrpareById(DataRequest dataRequest, String casePrgrsStts) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		paramMap.put("CASE_PRGRS_STTS_SE_CD", casePrgrsStts);
		
		List<Map<String, String>> result = slfrlRegMapper.selectSlfrlPrpareById(paramMap);
		
		return result; 
	}

}
