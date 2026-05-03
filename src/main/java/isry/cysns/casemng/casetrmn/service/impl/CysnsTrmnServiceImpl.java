/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casetrmn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.casemng.casereg.mapper.CysnsRegMapper;
import isry.cysns.casemng.casetrmn.mapper.CysnsTrmnMapper;
import isry.cysns.casemng.casetrmn.service.CysnsTrmnService;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtSaveService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;

/**
 * @파일명        : CysnsTrmnServiceImpl.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 25.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      :
 * -
 * -
 */
@Service("cysnsTrmnService")
public class CysnsTrmnServiceImpl implements CysnsTrmnService{

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "cysnsTrmnMapper")
	private CysnsTrmnMapper cysnsTrmnMapper;

	//추가
	@Resource(name = "survshtSaveService")
	private SurvshtSaveService survshtSaveService;

	@Resource(name = "cysnsRegMapper")
	private CysnsRegMapper cysnsRegMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;


	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 25.
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		//미디어대상자
		saveMediaTrprData(dataRequest.getParameterGroup("dsList"), userId);
		//학교진단/기관진단
		saveDgnssData(dataRequest.getParameterGroup("dsList"), userId);
		//인터넷,스마트폰 진단점수
		saveDgnssScoreData(dataRequest.getParameterGroup("dsList"), dataRequest.getParameterGroup("dsDgnssScoreList"), userId, 15);
		//초1미디어,스마트폰 진단점수
		saveDgnssScoreData(dataRequest.getParameterGroup("dsList"), dataRequest.getParameterGroup("dsInfantChilList"), userId, 9);
		//초1미디어,스마트폰 진단점수
		saveDgnssScoreData(dataRequest.getParameterGroup("dsList"), dataRequest.getParameterGroup("dsCyberGambleList"), userId, 10);

		//설문지등록
		ParameterGroup param2 = dataRequest.getParameterGroup("dsList2");
		List<Map<String, String>> dsList2 = param2.getAllRowList();
		if (dsList2.size() > 0) {
			String caseMngNo = dsList2.get(0).get("CASE_MNG_NO");
			String caseMngOdrno = dsList2.get(0).get("CASE_MNG_ODRNO");
			saveSrvyData(request, dataRequest, caseMngNo, caseMngOdrno);
		}

		ParameterGroup param3 = dataRequest.getParameterGroup("dsSrvyAddtngInfo");
		List<Map<String, String>> dsSrvyAddtngList = param3.getAllRowList();
		if(dsSrvyAddtngList.size() > 0) {
			Map<String, String> dMap = dsSrvyAddtngList.get(0);
			dMap.put("USER_ID", userId);
			cysnsTrmnMapper.saveSrvyAddtngInfo(dMap);
		}

		saveCnctrClinicData(request, dataRequest);
	}

	/**
	 * @Method명   : saveMediaTrprData
	 * @param dataRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 6.
	 * @Method설명 :
	 */
	private void saveMediaTrprData(ParameterGroup parameterGroup, String userId) throws Exception {

		//인터넷대상자
		List<Map<String, String>> dsList = parameterGroup.getAllRowList();
		if(dsList.size() > 0) {
			Map<String, String> paramMap = dsList.get(0);
			paramMap.put("USER_ID", userId);

			cysnsTrmnMapper.saveData(paramMap);
		}
	}

	/**
	 * @Method명   : saveDgnssData
	 * @param parameterGroup
	 * @param userId
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 15.
	 * @Method설명 :
	 */
	private void saveDgnssData(ParameterGroup parameterGroup, String userId) throws Exception {
		//인터넷대상자
		List<Map<String, String>> dsList = parameterGroup.getAllRowList();
		if(dsList.size() > 0) {
			Map<String, String> paramMap = dsList.get(0);

			paramMap.put("CASE_PRGRS_STTS_SE_CD", "04");
			paramMap.put("USER_ID", userId);

			cysnsTrmnMapper.updateSchlDgnssData(paramMap);
			cysnsTrmnMapper.updateInstDgnssData(paramMap);
		}

	}


	/**
	 * @Method명   : saveDgnssScoreData
	 * @param dataRequest
	 * @param userId
	 * @param trprInfoNo
	 * @param caseMngNo
	 * @param caseMngOdrno
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 6.
	 * @Method설명 :
	 */
	private void saveDgnssScoreData(ParameterGroup paramMediaTrpr, ParameterGroup paramDgnssScore, String userId, int paramCnt) throws Exception {

		List<Map<String, String>> dsList = paramMediaTrpr.getAllRowList();
		if(dsList.size() > 0) {
			String trprInfoNo = dsList.get(0).get("TRPR_INFO_NO");
			String caseMngNo = dsList.get(0).get("CASE_MNG_NO");
			String caseMngOdrno = dsList.get(0).get("CASE_MNG_ODRNO");
			String dgnssExmnMngNo = dsList.get(0).get("DGNSS_EXMN_MNG_NO");

			//인터넷/스마트폰진단조사/초1미디어/도박
			List<Map<String, String>> dsDgnssScoreList = paramDgnssScore.getUpdatedRowList();
			if(dsDgnssScoreList.size() > 0) {
				for (Map<String, String> map : dsDgnssScoreList) {

					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);

					paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "02");
					paramMap.put("DGNSS_EXMN_SE_CD", map.get("DGNSS_EXMN_SE_CD"));

					paramMap.put("CASE_MNG_NO", caseMngNo);
					paramMap.put("CASE_MNG_ODRNO", caseMngOdrno);
					paramMap.put("TRPR_INFO_NO", trprInfoNo);

					paramMap.put("USER_ID", userId);

					for (int j = 0; j < paramCnt; j++) {
						paramMap.put("SCORE_SE_SE_CD", String.valueOf(j+1));
						String seq = String.format("%02d", j+1);
						paramMap.put("DGNSS_SCORE", map.get("DGNSS_SCORE".concat(seq)));

						cysnsTrmnMapper.saveDgnssScoreData(paramMap);
					}
				}
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
	 * @작성일     : 2022. 10. 24.
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
		//서비스평가
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvySrvc"), dataRequest.getParameterGroup("dsSrvyRelmSrvc"), paramMap, "04");
		//서비스만족도
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyDgstfn"), dataRequest.getParameterGroup("dsSrvyRelmDgstfn"), paramMap, "05");
		//고위기 사전(자살)
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyHlisk"), dataRequest.getParameterGroup("dsSrvyRelmHlisk"), paramMap, "06");
		//고위기 사전(자해)
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyHlisk2"), dataRequest.getParameterGroup("dsSrvyRelmHlisk2"), paramMap, "13");
		//고위기 사전(폭력)
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyHlisk3"), dataRequest.getParameterGroup("dsSrvyRelmHlisk3"), paramMap, "15");

	}

	/**
	 * @Method명   : selectProbmSttsById
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 2. 13.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectProbmSttsById(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsTrmnMapper.selectProbmSttsById(paramMap);

		return result;

	}


	@Override
	public Map<String, Object> selectCnctrClinicInfo(DataRequest dataRequest) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();

		ParameterGroup param = dataRequest.getParameterGroup("dmCnctrClinicInfo");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		paramMap.put("BEFFAT_AFTFCT_SE_CD", "01"); // 임시로 01 사전으로 박아 넣는다.
		paramMap.put("CLINIC_INSP_SE_CD", "");
		paramMap.put("USER_ID", param.getValue("USER_ID"));

		// ACA300 집중 심리클리닉 등록 탭에 존재 여부를 정보를 확인한다.
		List<Map<String, String>> cnctrClinic = cysnsRegMapper.selectCnctrClinicYsrInfo(paramMap);
		List<Map<String, String>> cnctrClinicSrvy = new ArrayList<Map<String, String>>();

		String cnctrClinicMngNo = "";
		boolean type1 = false;
		boolean type2 = false;
		boolean type3 = false;
		String  type = "";

		if (cnctrClinic.size() > 0) {
			List<Map<String, Object>> caseYngbgsList = caseRegMapper.selectCaseYngbgsList(paramMap);

			for (int i=0;i<caseYngbgsList.size();i++) {
				if ("0601".equals(caseYngbgsList.get(i).get("PROBM_STTS_MLSFC_SE_CD"))) {
					type1 = true;
					type = "01";
				}
				if ("0602".equals(caseYngbgsList.get(i).get("PROBM_STTS_MLSFC_SE_CD"))) {
					type2 = true;
					type = "02";
				}
			}

			if (type1 && type2) {
				type3 = true;
				type = "03";
			}

			paramMap.put("CLINIC_INSP_SE_CD", type);

			cnctrClinicMngNo = cnctrClinic.get(0).get("CNCTR_CLINIC_MNG_NO");

			paramMap.put("BEFFAT_AFTFCT_SE_CD", "02");

			cnctrClinic = cysnsRegMapper.selectCnctrClinicYsrInfo(paramMap);

			if (cnctrClinic.size() == 0) {
				paramMap.put("CNCTR_CLINIC_MNG_NO", cnctrClinicMngNo);

				cysnsRegMapper.saveCnctrClinicBaseData(paramMap);

				// 기초 데이터 셋팅.
				paramMap.put("QUSTNB_TMPT_MNG_NO", "TM2023083000001");
				paramMap.put("CLINIC_INSP_SE_CD", "01"); //자살

				// 310번 생성
				cysnsRegMapper.saveCnctrClinicSrvyData(paramMap);

				paramMap.put("QUSTNB_TMPT_MNG_NO", "TM2023083000002");
				paramMap.put("CLINIC_INSP_SE_CD", "02"); // 비자살적 자해

				// 310번 생성
				cysnsRegMapper.saveCnctrClinicSrvyData(paramMap);

				// 신규로 생성된 데이터를 셋팅한다.
				cnctrClinic = cysnsRegMapper.selectCnctrClinicYsrInfo(paramMap);
			}



			if (cnctrClinic.size() > 0) {
				cnctrClinicMngNo = cnctrClinic.get(0).get("CNCTR_CLINIC_MNG_NO");

				paramMap.put("CNCTR_CLINIC_MNG_NO", cnctrClinicMngNo);

				// ACA310
				cnctrClinicSrvy = cysnsRegMapper.selectCnctrClinicSrvyInfo(paramMap);

			}

			result.put("dsCnctrClinic", cnctrClinic);
			result.put("dsCnctrClinicSrvy", cnctrClinicSrvy);

			result.put("dmCnctrClinicInfo", paramMap);
		}

		return result;
	}


	public void saveCnctrClinicData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request));

		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, String> param = new HashMap<String, String>();
		// 문제행동척도 프로파일(YSR)
		ParameterGroup dsCnctrClinic = dataRequest.getParameterGroup("dsCnctrClinic");

		List<Map<String, String>> cnctrClinic = dsCnctrClinic.getAllRowList();

		// System.out.println(cnctrClinic);
		if (cnctrClinic.size() > 0) {

			paramMap =  cnctrClinic.get(0);
			paramMap.put("USER_ID", userId);

			String caseMngNo = paramMap.get("CASE_MNG_NO");
			String caseMngOdrno = paramMap.get("CASE_MNG_ODRNO");

			// ACA300
			cysnsRegMapper.saveCnctrClinicBaseData(paramMap);


			ParameterGroup dsCnctrClinicSrvy = dataRequest.getParameterGroup("dsCnctrClinicSrvy");

			List<Map<String, String>> cnctrClinicSrvy = dsCnctrClinicSrvy.getAllRowList();

			System.out.println(cnctrClinicSrvy);

			if (cnctrClinicSrvy.size() > 0) {
				paramMap.put("CASE_MNG_NO", caseMngNo);
				paramMap.put("CASE_MNG_ODRNO", caseMngOdrno);
				paramMap.put("ENFSN_NO", enfsnNo);
				paramMap.put("USER_ID", userId);
				// 자살
				ParameterGroup dsCnctrSucClinic = dataRequest.getParameterGroup("dsCnctrSucClinic");
				List<Map<String, String>> cnctrSucClinic = dsCnctrSucClinic.getAllRowList();
				if (cnctrSucClinic.size()> 0) {
					cnctrClinicSrvy.get(0).put("QUSTNB_MNG_NO", cnctrSucClinic.get(0).get("QUSTNB_MNG_NO"));

					//집중 심리클리닉 - 자살위험성
					survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsCnctrSucClinic"), dataRequest.getParameterGroup("dsCnctrSucRelmClinic"), paramMap, "94");
				}
				// 자해
				ParameterGroup dsCnctrInjClinic = dataRequest.getParameterGroup("dsCnctrInjClinic");
				List<Map<String, String>> cnctrInjClinic = dsCnctrInjClinic.getAllRowList();
				if (cnctrInjClinic.size()> 0) {
					cnctrClinicSrvy.get(1).put("QUSTNB_MNG_NO", cnctrInjClinic.get(0).get("QUSTNB_MNG_NO"));

					//집중 심리클리닉 - 비자살적 자해 척도
					survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsCnctrInjClinic"), dataRequest.getParameterGroup("dsCnctrInjRelmClinic"), paramMap, "95");
				}

				for (int i=0; i< cnctrClinicSrvy.size(); i++ ) {
					Map<String, String> map  = cnctrClinicSrvy.get(i);

					map.put("USER_ID", userId);

					cysnsRegMapper.saveCnctrClinicSrvyData(map);
				}
			}
		}
	}

}
