/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casereg.service.impl;

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
import com.clipsoft.org.apache.commons.lang.StringUtils;

import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.casemng.casereg.mapper.CysnsRegMapper;
import isry.cysns.casemng.casereg.service.CysnsRegService;
import isry.cysns.casemng.casetrmn.mapper.CysnsTrmnMapper;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.syscmmn.survsht.mapper.SurvshtMmnMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtSaveService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.DateUtil;

/**
 * @파일명        : CysnsRegServiceImpl.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 7.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 7.
 * @수정내용      :
 * -
 * -
 */
@Service("cysnsRegService")
public class CysnsRegServiceImpl implements CysnsRegService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CysnsRegServiceImpl.class);

	@Resource(name = "cysnsRegMapper")
	private CysnsRegMapper cysnsRegMapper;

	@Resource(name = "cysnsTrmnMapper")
	private CysnsTrmnMapper cysnsTrmnMapper;

	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

	@Resource(name = "survshtSaveService")
	private SurvshtSaveService survshtSaveService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "survshtMmnMapper")
	private SurvshtMmnMapper survshtMmnMapper;

	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 7.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsRegMapper.selectReqById(paramMap);

		return result;
	}

	/**
	 * @Method명   : selectReqById2
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 7.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById2(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsRegMapper.selectReqById2(paramMap);

		return result;
	}

	@Override
	public List<Map<String, String>> selectReqById3(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsRegMapper.selectReqById3(paramMap);

		return result;
	}

	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 7.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		//1.업무공통영역 저장
		LOGGER.debug("================= 업무공통영역 저장 START =================");
		Map<String, Object> info = caseRegService.processData(request, dataRequest);
		LOGGER.debug("================= 업무공통영역 저장 END =================");

		String sCaseMngNo = String.valueOf(info.get("CASE_MNG_NO"));
		String sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

		ParameterGroup param = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsList = param.getAllRowList();
		if(dsList.size() > 0) {
			for(Map<String, String> paramMap : dsList) {
//				sCaseMngNo    = paramMap.get("CASE_MNG_NO");
//				sCaseMngOdrno = paramMap.get("CASE_MNG_ODRNO");

//				if(sCaseMngNo.isEmpty())	sCaseMngNo	  = String.valueOf(info.get("CASE_MNG_NO"));
//				if(sCaseMngOdrno.isEmpty())	sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

				paramMap.put("CASE_MNG_NO"   , sCaseMngNo);
				paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				paramMap.put("USER_ID"		 , userId);

				cysnsRegMapper.saveData(paramMap);  //위기스크리닝결과
			}
		}

		ParameterGroup param2 = dataRequest.getParameterGroup("dsList2");
		List<Map<String, String>> dsList2 = param2.getAllRowList();
		if(dsList2.size() > 0) {
			if(!StringUtils.isEmpty(dsList2.get(0).get("RISK_FCTR_SUM_SCORE"))) {
				Map<String, String> paramMap = dsList2.get(0);

				paramMap.put("CASE_MNG_NO"   , sCaseMngNo);
				paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				paramMap.put("USER_ID"		 , userId);

				cysnsRegMapper.saveData2(paramMap); //위기스크리닝평가점수(상세)
			}
		}

		ParameterGroup param3 = dataRequest.getParameterGroup("dsList3");
		List<Map<String, String>> dsList3 = param3.getAllRowList();
		if(dsList3.size() > 0) {

			Map<String, String> paramMap = dsList3.get(0);

//			if(sCaseMngNo.isEmpty())	sCaseMngNo	  = String.valueOf(info.get("CASE_MNG_NO"));
//			if(sCaseMngOdrno.isEmpty())	sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

			LOGGER.debug(" sCaseMngNo = {}", sCaseMngNo);

			paramMap.put("CASE_MNG_NO"   , sCaseMngNo);
			paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

			String trprInfoNo = paramMap.get("TRPR_INFO_NO");
			if("".equals(trprInfoNo) || trprInfoNo == null) {
				Map<String, String> trprInfoMap = cysnsRegMapper.getTrprInfoNo(paramMap);
				paramMap.put("TRPR_INFO_NO"     , trprInfoMap.get("TRPR_INFO_NO"));
				paramMap.put("DGNSS_EXMN_MNG_NO", trprInfoMap.get("DGNSS_EXMN_MNG_NO"));
			}

			if (StringUtils.isEmpty(paramMap.get("DGNSS_EXMN_MNG_NO"))) {
				paramMap.put("DGNSS_EXMN_MNG_NO", cysnsRegMapper.selectKeyValue(getKeyValue("DE", userId)));
				dataRequest.getParameterGroup("dsList3").get(0).setValue("DGNSS_EXMN_MNG_NO", cysnsRegMapper.selectKeyValue(getKeyValue("DE", userId)));
			}

			paramMap.put("USER_ID", userId);

			cysnsRegMapper.saveData3(paramMap); //인터넷중독대상자기본

			//인터넷,스마트폰 진단점수
			saveDgnssScoreData(dataRequest.getParameterGroup("dsList3"), dataRequest.getParameterGroup("dsDgnssScoreList"), userId, 15);
			//초1미디어
			saveDgnssScoreData(dataRequest.getParameterGroup("dsList3"), dataRequest.getParameterGroup("dsInfantChilList"), userId, 9);
			//도
			saveDgnssScoreData(dataRequest.getParameterGroup("dsList3"), dataRequest.getParameterGroup("dsCyberGambleList"), userId, 10);
		}

		//설문지등록
		saveSrvyData(request, dataRequest, sCaseMngNo, sCaseMngOdrno);

		// 실시 여부와 검사일자 추가로 인한 기능 추가. 20230509 Taesoo Song.
		ParameterGroup srvyInfo = dataRequest.getParameterGroup("dsSrvyRspnsInfo");
		List<Map<String, String>> infoList = srvyInfo.getAllRowList();
		if(infoList.size() > 0) {
			Map<String, String> paramMap = infoList.get(0);

			if(sCaseMngNo.isEmpty())	sCaseMngNo	  = String.valueOf(info.get("CASE_MNG_NO"));
			if(sCaseMngOdrno.isEmpty())	sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

			paramMap.put("CASE_MNG_NO", sCaseMngNo);
			paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			paramMap.put("USER_ID", userId);
			System.out.println(paramMap);
			cysnsRegMapper.saveRspnsInfoData(paramMap);
		}

		// 집중 심리클리닉 정보 저장.
		saveCnctrClinicData(request, dataRequest, sCaseMngNo, sCaseMngOdrno);

		Map<String, String> rtnMap = new HashMap<>();
		rtnMap.put("CASE_MNG_NO", sCaseMngNo);
		rtnMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

		return rtnMap;

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
			String dgnssExmnMngNo = dsList.get(0).get("DGNSS_EXMN_MNG_NO");

			//인터넷/스마트폰진단조사/초1미디어/도박
			List<Map<String, String>> dsDgnssScoreList = paramDgnssScore.getUpdatedRowList();
			if(dsDgnssScoreList.size() > 0) {
				for (Map<String, String> map : dsDgnssScoreList) {

					LOGGER.debug("DGNSS_EXMN_SE_CD == {}",  map.get("DGNSS_EXMN_SE_CD"));

					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);

					paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");
					paramMap.put("DGNSS_EXMN_SE_CD", map.get("DGNSS_EXMN_SE_CD"));

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
		//위기스크리닝 위험요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyRisk"), dataRequest.getParameterGroup("dsSrvyRelmRisk"), paramMap, "01");
		//위기스크리닝 위기요인
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyCrisis"), dataRequest.getParameterGroup("dsSrvyRelmCrisis"), paramMap, "02");
		//서비스평가
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvySrvc"), dataRequest.getParameterGroup("dsSrvyRelmSrvc"), paramMap, "03");

	}

	/**
	 * @Method명   : getKeyValue
	 * @param string
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 3. 29.
	 * @Method설명 :
	 */
	private Map<String, String> getKeyValue(String keyValue, String userId) {

		Map<String, String> keyMap = new HashMap<>();
		keyMap.put("SYS_CD", keyValue);
		keyMap.put("USER_ID", userId);

		return keyMap;
	}

	/**
	 * @Method명   : deleteData
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 7.
	 * @Method설명 :
	 */
	@Override
	public void deleteData(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		cysnsRegMapper.deleteData(paramMap);
	}

	/**
	 * @Method명   : selectDgnssByTrprInfoNo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 27.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDgnssByTrprInfoNo(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));

		List<Map<String, String>> result = cysnsRegMapper.selectDgnssByTrprInfoNo(paramMap);

		return result;
	}

	/**
	 * @Method명   : selectGradeSttsByTrprInfoNo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 27.
	 * @Method설명 :
	 */
	@Override
	public String selectGradeSttsByTrprInfoNo(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		String trprInfoNo = param.getValue("TRPR_INFO_NO");

		String result = cysnsRegMapper.selectGradeSttsByTrprInfoNo(trprInfoNo);

		return result;
	}



	/**
	 * @Method명   : selectSrvyTrprById
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 25.
	 * @Method설명 :
	 */
	@Override
	public String selectSrvyTrprById(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = new HashMap<>();

		paramMap.put("QUSTNB_SHAPE_SE_CD", param.getValue("QUSTNB_SHAPE_SE_CD")); //설문지형태구분코드
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));               //사례번호
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));         //사례번호차수

		return cysnsRegMapper.selectSrvyTrprById(paramMap);
	}

	@Override
	public List<Map<String, String>> selectExcnReqById(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsRegMapper.selectExcnReqById(paramMap);

		return result;
	}

	@Override
	public List<Map<String, String>> selectExcnReqById2(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsRegMapper.selectExcnReqById2(paramMap);

		return result;
	}

	/**
	 * @Method명   : saveExcnData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 24.
	 * @Method설명 :
	 */
	@Override
	public void saveExcnData(HttpServletRequest request, DataRequest dataRequest, List<Map<String, String>> params) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		//심리검사 저장
		saveTrlInsp(dataRequest, params, userId);

	}

	/**
	 * @Method명   : saveTrlInsp
	 * @param dataRequest
	 * @param params
	 * @param userId
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 22.
	 * @Method설명 :
	 */
	private void saveTrlInsp(DataRequest dataRequest, List<Map<String, String>> params, String userId)	throws Exception {

		ParameterGroup param3 = dataRequest.getParameterGroup("dsList3"); //심리검사
		List<Map<String, String>> dsList3 = param3.getAllRowList();
		if(dsList3.size() > 0) {
			for (Map<String, String> map : params) {
				LOGGER.info("1.서비스제공번호 ={}, 자원번호 ={} {}", map.get("SRVC_PVSN_NO"), map.get("RESRCE_NO"), map.get("DATAA_CHG_SE_CD"));

				for(Map<String, String> paramMap : dsList3) {

					if (StringUtils.isEmpty(paramMap.get("SRVC_PVSN_NO"))) { //서비스제공번호가 없으면
						LOGGER.info("2.서비스제공번호 ={}, 자원번호 ={}", paramMap.get("SRVC_PVSN_NO"), paramMap.get("RESRCE_NO"));
						if (map.get("RESRCE_NO").equals(paramMap.get("RESRCE_NO"))) {  //자원번호가 같으면,
							paramMap.put("SRVC_PVSN_NO", map.get("SRVC_PVSN_NO"));
						}
					}

					if (StringUtils.equals(map.get("SRVC_PVSN_NO"), paramMap.get("SRVC_PVSN_NO"))) {   //서비스제공번호가 없으면
						if (map.get("RESRCE_NO").equals(paramMap.get("RESRCE_NO"))) {  //자원번호가 같으면,
							paramMap.put("DATAA_CHG_SE_CD", map.get("DATAA_CHG_SE_CD"));
						}
					}

					LOGGER.info("3.서비스제공번호 ={}, 자원번호 ={} {}", paramMap.get("SRVC_PVSN_NO"), paramMap.get("RESRCE_NO"), map.get("DATAA_CHG_SE_CD"));
//					if (map.get("RESRCE_NO").equals(paramMap.get("RESRCE_NO"))) {  //자원번호가 같으면,
//						if (StringUtils.equals(map.get("SRVC_PVSN_NO"), paramMap.get("SRVC_PVSN_NO"))) {   //서비스제공번호가 없으면
//							paramMap.put("DATAA_CHG_SE_CD", map.get("DATAA_CHG_SE_CD"));
//						} else {
//							if (StringUtils.isEmpty(paramMap.get("SRVC_PVSN_NO"))) {
//								paramMap.put("SRVC_PVSN_NO", map.get("SRVC_PVSN_NO"));
//								paramMap.put("DATAA_CHG_SE_CD", map.get("DATAA_CHG_SE_CD"));
//							}
//						}
				}
			}

			for(Map<String, String> paramMap : dsList3) {
				paramMap.put("USER_ID", userId);

				paramMap.put("TRL_INSP_YMD", paramMap.get("TRL_INSP_YMD") == null
						? "" : paramMap.get("TRL_INSP_YMD").replaceAll("[^\\d]", ""));

				if (paramMap.get("DATAA_CHG_SE_CD").equals("D")) {
					cysnsRegMapper.deleteTrlInspData(paramMap);  //심리검사관리번호
					cysnsRegMapper.deleteTrlInspDtlData(paramMap); //심리검사관리번호
				} else {
					cysnsRegMapper.saveTrlInspData(paramMap);
				}
			}

			List<Map<String, String>> deletedRowList = param3.getDeletedRowList();
			for (Map<String, String> paramMap : deletedRowList) {
				cysnsRegMapper.deleteTrlInspData(paramMap); //심리검사관리번호
				cysnsRegMapper.deleteTrlInspData(paramMap); //심리검사관리번호
			}
		}
	}


	/**
	 * @Method명   : selectTrlInspByResrceNo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 17.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectTrlInspByResrceNo(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("RESRCE_NO", param.getValue("RESRCE_NO"));

		List<Map<String, String>> result = cysnsRegMapper.selectTrlInspByResrceNo(paramMap);

		return result;
	}

	/**
	 * @Method명   : selectTrlInspByList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 17.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectTrlInspByList(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, String>> result = cysnsRegMapper.selectTrlInspByList(paramMap);

		return result;
	}

	@Override
	public List<Map<String, Object>> selectReqBySrvyInfo(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, Object>> result = cysnsRegMapper.selectReqBySrvyInfo(paramMap);

		return result;
	}

	@Override
	public List<Map<String, Object>> selectSrvyAddtngInfo(DataRequest dataRequest) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));

		List<Map<String, Object>> result = cysnsRegMapper.selectSrvyAddtngInfo(paramMap);

		return result;
	}

	/**
	 * @Method명   : saveMdlrtSprtData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 18.
	 * @Method설명 :
	 */
	@Override
	public void saveMdlrtSprtData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		//인터넷중독
		saveIntnetAddc(dataRequest, userId);
		//인터넷중독치료
		saveIntnetMdlrt(dataRequest, userId);

	}

	/**
	 * @Method명   : saveIntnetAddc
	 * @param dataRequest
	 * @param userId
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 22.
	 * @Method설명 :
	 */
	private void saveIntnetAddc(DataRequest dataRequest, String userId) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dsList"); //인터넷중독대상자
		List<Map<String, String>> dsList = param.getAllRowList();
		if(dsList.size() > 0) {
			Map<String, String> paramMap = dsList.get(0);
			paramMap.put("USER_ID", userId);

			cysnsRegMapper.saveExcnData(paramMap);
		}
	}

	/**
	 * @Method명   : saveIntnetMdlrt
	 * @param dataRequest
	 * @param userId
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 12. 22.
	 * @Method설명 :
	 */
	private void saveIntnetMdlrt(DataRequest dataRequest, String userId) throws Exception {
		ParameterGroup param2 = dataRequest.getParameterGroup("dsList2"); //인터넷중독치료
		List<Map<String, String>> insertedRowList = param2.getInsertedRowList();
		for(Map<String, String> paramMap : insertedRowList) {
			paramMap.put("USER_ID", userId);
			cysnsRegMapper.saveExcnData2(paramMap);
		}

		List<Map<String, String>> updatedRowList = param2.getUpdatedRowList();
		for(Map<String, String> paramMap : updatedRowList) {
			paramMap.put("USER_ID", userId);
			cysnsRegMapper.saveExcnData2(paramMap);
		}

		List<Map<String, String>> deletedRowList = param2.getDeletedRowList();
		for(Map<String, String> paramMap : deletedRowList) {
			paramMap.put("USER_ID", userId);
			cysnsRegMapper.deleteExcnData2(paramMap);
		}
	}

	/**
	 *
	 * @Method명   : selectCnctrClinicInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Tae.Soo.Song
	 * @작성일     : 2023. 9. 1.
	 * @Method설명 :
	 */
	public Map<String, Object> selectCnctrClinicInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		Map<String, String> paramMap = new HashMap<String, String>();

		ParameterGroup param = dataRequest.getParameterGroup("dmCnctrClinicInfo");

		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		paramMap.put("BEFFAT_AFTFCT_SE_CD", param.getValue("BEFFAT_AFTFCT_SE_CD"));
		paramMap.put("CLINIC_INSP_SE_CD", param.getValue("CLINIC_INSP_SE_CD"));
		paramMap.put("USER_ID", userId);

		// 집중 심리클리닉 정보.
		String cnctrClinicMngNo = "";

		// ACA300 집중 심리클리닉에 대한 정보를 확인한다.
		List<Map<String, String>> cnctrClinic = cysnsRegMapper.selectCnctrClinicYsrInfo(paramMap);

		// 사전은 사전대로. 사후면 사후 대로 생성 해놓기.
		// 없으면 일단 다 때려 생성 처리.
		if (cnctrClinic.size() == 0) {
			Map<String, String> mngNoMap = new HashMap<String, String>();
			mngNoMap.put("USER_ID", userId);
			mngNoMap.put("SYS_CD", "CL");
			cnctrClinicMngNo = survshtMmnMapper.selectSysSeCd(mngNoMap);

			paramMap.put("CNCTR_CLINIC_MNG_NO", cnctrClinicMngNo);

			// 300번 생성
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

		List<Map<String, String>> cnctrClinicSrvy = new ArrayList<Map<String, String>>();
		if (cnctrClinic.size() > 0) {
			cnctrClinicMngNo = cnctrClinic.get(0).get("CNCTR_CLINIC_MNG_NO");

			paramMap.put("CNCTR_CLINIC_MNG_NO", cnctrClinicMngNo);

			// ACA310
			cnctrClinicSrvy = cysnsRegMapper.selectCnctrClinicSrvyInfo(paramMap);

		}

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("dsCnctrClinic", cnctrClinic);
		result.put("dsCnctrClinicSrvy", cnctrClinicSrvy);

		return result;
	}

	/**
	 * 집중 클리닉 정보 저장.
	 * @Method명   : saveCnctrClinicData
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception
	 * @작성자     : Tae.Soo.Song
	 * @작성일     : 2023. 8. 31.
	 * @Method설명 :
	 */
	public void saveCnctrClinicData(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request));

		Map<String, String> paramMap = new HashMap<String, String>();
		// 문제행동척도 프로파일(YSR)
		ParameterGroup dsCnctrClinic = dataRequest.getParameterGroup("dsCnctrClinic");

		List<Map<String, String>> cnctrClinic = dsCnctrClinic.getAllRowList();

		if (cnctrClinic.size()>0) {

			// System.out.println(cnctrClinic);
			paramMap =  cnctrClinic.get(0);
			paramMap.put("USER_ID", userId);
			// ACA300
			cysnsRegMapper.saveCnctrClinicBaseData(paramMap);


			ParameterGroup dsCnctrClinicSrvy = dataRequest.getParameterGroup("dsCnctrClinicSrvy");

			List<Map<String, String>> cnctrClinicSrvy = dsCnctrClinicSrvy.getAllRowList();

			System.out.println(cnctrClinicSrvy);

			if (cnctrClinicSrvy.size() > 0) {
				paramMap.put("CASE_MNG_NO", sCaseMngNo);
				paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
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
