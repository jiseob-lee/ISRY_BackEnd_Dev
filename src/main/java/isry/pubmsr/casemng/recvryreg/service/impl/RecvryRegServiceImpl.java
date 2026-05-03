/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmsr.casemng.recvryreg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import isry.pubmsr.casemng.recvryreg.mapper.RecvryRegMapper;
import isry.pubmsr.casemng.recvryreg.service.RecvryRegService;
import isry.pubmt.casemng.slfrlreg.mapper.SlfrlRegMapper;
import isry.redis.service.RedisService;

/**
 * @파일명        : EmrgActnServiceImpl.java
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
@Service("recvryRegService")
public class RecvryRegServiceImpl implements RecvryRegService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "recvryRegMapper")
	private RecvryRegMapper recvryRegMapper;
	
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
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {
		
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = recvryRegMapper.selectReqById(paramMap);
		
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
		
		LOGGER.debug("================= 단위업무(회복) 저장 START =================");
		
		//사례정보
		saveCaseInfo(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//위기스크리닝결과
		saveCrisisResult(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//위기스크리닝평가점수(상세)
		saveCrisisDetail(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//심리정서환경척도
		saveTrlEmt(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
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
	 * @작성일     : 2023. 07. 03. 
	 * @Method설명 :
	 */
	private void saveCaseInfo(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {

		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsList");
		
		List<Map<String, String>> insertedRowList = param.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("USER_ID", userId);
			recvryRegMapper.saveData(map);
		}

		List<Map<String, String>> updatedRowList = param.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("USER_ID", userId);
			recvryRegMapper.saveData(map);
		}

		List<Map<String, String>> deletedRowList = param.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("USER_ID", userId);
			recvryRegMapper.deleteData(map);
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
	 * @작성일     : 2023. 7. 3. 
	 * @Method설명 :
	 */
	private void saveCrisisDetail(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
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
	 * @Method명   : saveCrisisResult
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 7. 3. 
	 * @Method설명 :
	 */
	private void saveCrisisResult(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
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
		//위기스크리닝 위험요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyRisk"), dataRequest.getParameterGroup("dsSrvyRelmRisk"), paramMap, "01");
		//위기스크리닝 위기요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyCrisis"), dataRequest.getParameterGroup("dsSrvyRelmCrisis"), paramMap, "02");
		//설문지 저장 - 심리정서환경척도
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyTrlEmt"), dataRequest.getParameterGroup("dsSrvyRelmTrlEmt"), paramMap, "20");

	}
	
	/**
	 * @Method명   : deleteData
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 10. 
	 * @Method설명 :
	 */
	@Override
	public void deleteData(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		recvryRegMapper.deleteAllData(paramMap);
	}

}
