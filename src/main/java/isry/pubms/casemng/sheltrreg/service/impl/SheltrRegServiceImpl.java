/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrreg.service.impl;

import java.util.ArrayList;
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
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.StringUtil;
import isry.pubms.casemng.sheltrreg.mapper.SheltrRegMapper;
import isry.pubms.casemng.sheltrreg.service.SheltrRegService;
import isry.pubmt.casemng.slfrlreg.mapper.SlfrlRegMapper;

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
@Service("sheltrRegService")
public class SheltrRegServiceImpl implements SheltrRegService{
	
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

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

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

		List<Map<String, String>> result = sheltrRegMapper.selectReqById(paramMap);
		
		for (Map<String, String> map : result) {
			if(map.get("LVM_CAS_CN") != null) {
				map.put("LVM_CAS_HOUSEK", getLvmCasCd(map.get("LVM_CAS_CN"), "01"));
				map.put("LVM_CAS_SCHL", getLvmCasCd(map.get("LVM_CAS_CN"), "02"));
				map.put("LVM_CAS_FRIDA", getLvmCasCd(map.get("LVM_CAS_CN"), "03"));
				map.put("LVM_CAS_INDV", getLvmCasCd(map.get("LVM_CAS_CN"), "04"));
				map.put("LVM_CAS_ETC", getLvmCasCd(map.get("LVM_CAS_CN"), "99"));
			}
		}
		
		return result; 
	}

	/**
	 * @Method명   : selectAsessRcordById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectAsessRcordById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = sheltrRegMapper.selectAsessRcordById(paramMap);
		
		return result; 
	}

	/**
	 * @Method명   : selectSprtPensnById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSprtPensnById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = sheltrRegMapper.selectSprtPensnById(paramMap);
		
		return result; 
	}

	/**
	 * @Method명   : selectRthousSprtById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectRthousSprtById(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = sheltrRegMapper.selectRthousSprtById(paramMap);
		
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
		
		LOGGER.debug("================= 단위업무(쉼터) 저장 START =================");

		//사례정보
		saveCaseInfo(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		//사정기록지
		saveAsessRcord(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
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
	 * @작성일     : 2023. 07. 03. 
	 * @Method설명 :
	 */
	private void saveCaseInfo(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> allRowList = param.getAllRowList();
		
		for (Map<String, String> map : allRowList) {
			String lvmCasCn = getLvmCasCn(map);
			map.put("LVM_CAS_CN", lvmCasCn);
			//System.out.println("LVM_CAS_CN = " + lvmCasCn);
			map.put("CASE_MNG_NO", sCaseMngNo);
			map.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			map.put("USER_ID", userId);

			sheltrRegMapper.saveData(map);
		}
	}

	/**
	 * @Method명   : saveAsessRcord
	 * @param dataRequest
	 * @param userId
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @param paramMap
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 2. 
	 * @Method설명 :
	 */
	private void saveAsessRcord(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsAsessRcordList");
		List<Map<String, String>> dsAsessRcordList = param.getAllRowList();
		if (dsAsessRcordList.size() > 0) {
			Map<String, String> paramMap = dsAsessRcordList.get(0);
			if (!StringUtil.isEmpty(paramMap.get("EXPERT_OPIN_CN"))) {
				paramMap.put("CASE_MNG_NO", sCaseMngNo);
				paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				paramMap.put("USER_ID", userId);
				//3. 사정기록지(AEA120) 저장
				sheltrRegMapper.saveAsessRcordData(paramMap);
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

		if (allRowList != null && allRowList.size() > 0 
				&& !StringUtils.isEmpty(allRowList.get(0).get("ANXIET_DEPRES_SCORE"))) {
		
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
		//위기스크리닝 위험요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyRisk"), dataRequest.getParameterGroup("dsSrvyRelmRisk"), paramMap, "01");
		//위기스크리닝 위기요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyCrisis"), dataRequest.getParameterGroup("dsSrvyRelmCrisis"), paramMap, "02");
		//설문지 저장 - 심리정서환경척도
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyTrlEmt"), dataRequest.getParameterGroup("dsSrvyRelmTrlEmt"), paramMap, "18");

	}
	
	/**
	 * @Method명   : getLvmCasCn
	 * @param map
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	private String getLvmCasCn(Map<String, String> map) {
		
		StringBuffer sb = new StringBuffer();
		Boolean isComma = false;
		
		if (!StringUtils.isEmpty(map.get("LVM_CAS_HOUSEK"))) {
			sb.append(map.get("LVM_CAS_HOUSEK"));
			isComma = true;
		} 
		
		if (!StringUtils.isEmpty(map.get("LVM_CAS_SCHL"))) {
			if (isComma) sb.append(",");
			
			sb.append(map.get("LVM_CAS_SCHL"));
			isComma = true;
		} 
		
		if (!StringUtils.isEmpty(map.get("LVM_CAS_FRIDA"))) {
			if (isComma) sb.append(",");
			sb.append(map.get("LVM_CAS_FRIDA"));
			isComma = true;
		} 
		
		if (!StringUtils.isEmpty(map.get("LVM_CAS_INDV"))) {
			if (isComma) sb.append(",");
			sb.append(map.get("LVM_CAS_INDV"));
			isComma = true;
		} 
		
		if (!StringUtils.isEmpty(map.get("LVM_CAS_ETC"))) {
			if (isComma) sb.append(",");
			sb.append(map.get("LVM_CAS_ETC"));
		}
		
		return sb.toString();
	}

	/**
	 * @Method명   : getLvmCasCn
	 * @param map
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	private String getLvmCasCd(String lvmCasCn, String prefix) {

		StringBuffer sb = new StringBuffer();
		Boolean isComma = false;
		
		String[] lvmCasList = lvmCasCn.split(",");
		for (String lvmCas : lvmCasList) {
			if (lvmCas.startsWith(prefix)) {
				if (isComma) sb.append(",");
				sb.append(lvmCas);
				isComma = true;
			}
		}
		
		return sb.toString();
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

		sheltrRegMapper.deleteData(paramMap);
	}

	@Override
	public Map<String, Object> selectMainPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");
		
		Map<String, String> paramMap = param.getSingleValueMap();

		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		paramMap2.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		List<Map<String,Object>> list = new ArrayList<>();
		
		String cnt = "";
		int totCnt  = 0;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		if (StringUtils.equals(CommUtils.getUntTaskwk(userLoginService.getLoginSessionVO(request)), "U05")) {
			
			cnt = sheltrRegMapper.selectSLfrlCaseListCount(paramMap2);
			
			paramMap2.put("TOT_CNT", cnt);
			
			//페이지 인덱싱에 필요한 정보를 정제합니다.		
			totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
			
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			list = sheltrRegMapper.selectSlfrlCasePagingList(paramMap2); //자립지원
		} else {
			cnt = sheltrRegMapper.selectSheltrCaseListCount(paramMap2);
			
			paramMap2.put("TOT_CNT", cnt);
			
			//페이지 인덱싱에 필요한 정보를 정제합니다.		
			totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
			
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			list = sheltrRegMapper.selectSheltrCasePagingList(paramMap2); //쉼터, 회복시설
		}
		
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("dmPageInfo", resPage);
		
		return result;
	}

	/**
	 * @Method명   : saveExcnData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 :
	 */
	@Override
	public void saveExcnData(HttpServletRequest request, DataRequest dataRequest, List<Map<String, String>> params) throws Exception {
		
	}


}
