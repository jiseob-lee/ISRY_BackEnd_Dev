/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprdtlinfomng.service.impl;

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
import isry.csemd.mngrpage.aplcnttrprdtlinfomng.mapper.AplcntTrprDtlInfoMngMapper;
import isry.csemd.mngrpage.aplcnttrprdtlinfomng.service.AplcntTrprDtlInfoMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : AplcntTrprMngServiceImpl.java
 * @프로그램 설명 : 입교심사외_관리자 페이지
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 9. 16.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Service("csemdMngrPageAplcntTrprDtlInfoMngService")
public class AplcntTrprDtlInfoMngServiceImpl implements AplcntTrprDtlInfoMngService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	// 혜선님
	@Resource(name = "csemdMngrPageAplcntTrprDtlInfoMngMapper")
	private AplcntTrprDtlInfoMngMapper aplcntTrprDtlInfoMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectPtcptReqstdAplcntPop
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 참가신청서 관리자용 조회 (디딤)
	 */
	@Override
	public List<Map<String, Object>> selectPtcptReqstdAplcntPop(HttpServletRequest request, DataRequest dataRequest) {

		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmParam");
		Map<String, Object> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmDtlParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmDtlParam.getValue("APLY_RCPT_SN"));

		List<Map<String, Object>> retMap = aplcntTrprDtlInfoMngMapper.selectPtcptReqstdAplcntPop(dtlMap);
		
		if(retMap.size() == 0) {
			List<Map<String, Object>> retSrvtMap = aplcntTrprDtlInfoMngMapper.selectSrvyListPop(dtlMap);
			
			return retSrvtMap;

		}else {
			
			return retMap;
		}
		
	}

	/**
	 * @Method명 : selectEnstTrprList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 9. 28.
	 * @Method설명 : 팝업_대상자명 콤보
	 */
	@Override
	public void selectEnstTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		List<Map<String, Object>> result = aplcntTrprDtlInfoMngMapper.selectEnstTrprList(requestMap);

		for (Map<String, Object> map : result) {

			// 사례대상자번호
			String sCaseMngView = map.get("CASE_MNG_NO") + "-" + map.get("CASE_MNG_ODRNO");
			if (map.get("CASE_MNG_NO") != null)
				map.put("CASE_MNG_VIEW", sCaseMngView);

			// 교급/학년
			String sAcbgGrade = "";
			if(map.get("ACBG") == null && map.get("GRADE") == null && map.get("GRDTN")== null) {
				map.put("ACBG_GRADE", sAcbgGrade);
			}else {
				sAcbgGrade = map.get("ACBG") + " " + map.get("GRADE") + " " + map.get("GRDTN");
				map.put("ACBG_GRADE", sAcbgGrade);
			}
		}

		LOGGER.debug("대상자리스트 : [ " + result + " ] ");
		dataRequest.setResponse("dsTrprNmCmb", result);

	}
	
	/**
	 * @Method명 : selectRqcpInfoList
	 * @param resultMap
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 11.
	 * @작성일 : 2022. 10. 11.
	 * @Method설명 : 사례대상자목록(의뢰정보)
	 */
	@Override
	public List<Map<String, Object>> selectRqcpInfoList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> map = parameterGroup.getSingleValueMap();
		map.put("UNT_TASKWK_SE_CD", loginVo.getUntTaskwk());
		map.put("INST_NO", String.valueOf(loginVo.getInstNo()));
		map.put("INST_TYPE_SE_CD", loginVo.getInstTypeSeCd());

		// 사례대상자목록 조회
		return aplcntTrprDtlInfoMngMapper.selectRqcpInfoList(map);
	}
	
	/**
	 * @Method명 : selectMainList
	 * @param resultMap
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 11.
	 * @작성일 : 2022. 10. 11.
	 * @Method설명 : 사례대상자목록(입교접수정보)
	 */
	@Override
	public List<Map<String, Object>> selectMainList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> paraMap = parameterGroup.getSingleValueMap();
		paraMap.put("UNT_TASKWK_SE_CD", loginVo.getUntTaskwk());
		paraMap.put("INST_NO", loginVo.getInstNo().toString());
		paraMap.put("INST_TYPE_SE_CD", loginVo.getInstTypeSeCd());
		
		// 사례대상자목록 조회
		return aplcntTrprDtlInfoMngMapper.selectMainList(paraMap);
	}

	/**
	 * @Method명 : selectDeofstObservList
	 * @param resultMap
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 11.
	 * @작성일 : 2022. 10. 11.
	 * @Method설명 : 안정도관찰지 목록조회
	 */
	@Override
	public List<Map<String, Object>> selectDeofstObservList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> requestMap = parameterGroup.getSingleValueMap();
		requestMap.put("UNT_TASKWK_SE_CD", loginVo.getUntTaskwk());
		requestMap.put("INST_NO", loginVo.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVo.getInstTypeSeCd());
		
		String caseMngNoView = requestMap.get("CASE_MNG_NO_VIEW");
		if (caseMngNoView.contains("-")) {
			requestMap.put("CASE_MNG_NO", caseMngNoView.split("-")[0]);
			requestMap.put("CASE_MNG_ODRNO", caseMngNoView.split("-")[1]);
		} else {
			requestMap.put("CASE_MNG_NO", caseMngNoView);
			requestMap.put("CASE_MNG_ODRNO", "");
		}

		// 사례대상자목록 조회
		return aplcntTrprDtlInfoMngMapper.selectDeofstObservList(requestMap);
	}

	/**
	 * @Method명 : saveDeofstObserv
	 * @param paraMap
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 9. 27.
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : 안정도 관찰지 등록 및 수정
	 */
	@Override
	public void saveDeofstObserv(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> updateRowList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> insertRowList = parameterGroup.getInsertedRowList();

		if (!insertRowList.isEmpty()) {
			for (Map<String, String> paraMap : insertRowList) {
				paraMap.put("FRST_RGTR_ID", loginVO.getId());
				paraMap.put("LAST_MDFR_ID", loginVO.getId());
				int chk = aplcntTrprDtlInfoMngMapper.selectDeofstObservChk(paraMap);
				if (chk == 0)
					aplcntTrprDtlInfoMngMapper.insertDeofstObserv(paraMap);
				else {
					Map<String, Object> msgMap = new HashMap<String, Object>();
					msgMap.put("errorMsg", "이미 등록된 관찰지 입니다.");
					dataRequest.setMetadata(true, msgMap);
				}
			}
		}

		if (!updateRowList.isEmpty()) {
			for (Map<String, String> paraMap : updateRowList) {
				paraMap.put("LAST_MDFR_ID", loginVO.getId());

				aplcntTrprDtlInfoMngMapper.updateDeofstObserv(paraMap);
			}
		}
	}

	/**
	 * @Method명 : selectDeofstObservDtl
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 7.
	 * @작성일 : 2022. 10. 7.
	 * @Method설명 : 안정도 관찰지 상세조회
	 */
	@Override
	public List<Map<String, Object>> selectDeofstObservDtl(Map<String, String> requestMap) throws Exception {

		List<Map<String, Object>> resultMap = aplcntTrprDtlInfoMngMapper.selectDeofstObservDtl(requestMap);

		for (Map<String, Object> map : resultMap) {

			// 사례대상자번호
			String sCaseMngView = map.get("CASE_MNG_NO") + "-" + map.get("CASE_MNG_ODRNO");
			if (map.get("CASE_MNG_NO") != null)
				map.put("CASE_MNG_VIEW", sCaseMngView);

			// 교급/학년
			String sAcbgGrade = "";
			if(map.get("ACBG") == null && map.get("GRADE") == null && map.get("GRDTN")== null) {
				map.put("ACBG_GRADE", sAcbgGrade);
			}else {
				sAcbgGrade = map.get("ACBG") + " " + map.get("GRADE") + " " + map.get("GRDTN");
				map.put("ACBG_GRADE", sAcbgGrade);
			}

		}
		return resultMap;
	}

	/**
	 * @Method명 : selectEnstList
	 * @param resultMap
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 11.
	 * @작성일 : 2022. 10. 11.
	 * @Method설명 : 문제행동발생보고서 목록
	 */
	@Override
	public List<Map<String, Object>> selectProbmList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> requestMap = parameterGroup.getSingleValueMap();
		requestMap.put("UNT_TASKWK_SE_CD", loginVo.getUntTaskwk());
		requestMap.put("INST_NO", loginVo.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVo.getInstTypeSeCd());
		
		String caseMngNoView = requestMap.get("CASE_MNG_NO_VIEW");
		if (caseMngNoView.contains("-")) {
			requestMap.put("CASE_MNG_NO", caseMngNoView.split("-")[0]);
			requestMap.put("CASE_MNG_ODRNO", caseMngNoView.split("-")[1]);
		} else {
			requestMap.put("CASE_MNG_NO", caseMngNoView);
			requestMap.put("CASE_MNG_ODRNO", "");
		}
		// 사례대상자목록 조회
		return aplcntTrprDtlInfoMngMapper.selectProbmList(requestMap);
	}

	/**
	 * @Method명 : saveProbm
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 9. 30.
	 * @작성일 : 2022. 9. 30.
	 * @Method설명 : 문제행동발생 보고서 등록 및 수정
	 */
	@Override
	public void saveProbm(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> updateRowList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> insertRowList = parameterGroup.getInsertedRowList();

		if (!insertRowList.isEmpty()) {
			for (Map<String, String> paraMap : insertRowList) {
				paraMap.put("FRST_RGTR_ID", loginVo.getId());
				paraMap.put("LAST_MDFR_ID", loginVo.getId());
				int chk = aplcntTrprDtlInfoMngMapper.selectPromReportChk(paraMap);
				if (chk == 0)
					aplcntTrprDtlInfoMngMapper.insertProbmRowList(paraMap);
				else {
					Map<String, Object> msgMap = new HashMap<String, Object>();
					msgMap.put("errorMsg", "이미 등록된 보고서입니다.");
				}
			}
		}

		if (!updateRowList.isEmpty()) {
			for (Map<String, String> paraMap : updateRowList) {
				paraMap.put("LAST_MDFR_ID", loginVo.getId());
				aplcntTrprDtlInfoMngMapper.updateProbmRowList(paraMap);
			}
		}
	}

	/**
	 * @Method명 : selectDtlProbInfo
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 7.
	 * @작성일 : 2022. 10. 7.
	 * @Method설명 : 문제행동발생 보고서 상세조회
	 */
	@Override
	public List<Map<String, Object>> selectDtlProbInfo(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("CASE_MNG_NO", parameterGroup.getValue("CASE_MNG_NO"));
		resultMap.put("CASE_MNG_ODRNO", parameterGroup.getValue("CASE_MNG_ODRNO"));
		resultMap.put("WRT_YMD", parameterGroup.getValue("WRT_YMD"));
		resultMap.put("INCDNT_ACDNT_CL_SE_CD", parameterGroup.getValue("INCDNT_ACDNT_CL_SE_CD"));
		resultMap.put("MNG_SN", parameterGroup.getValue("MNG_SN"));

		List<Map<String, Object>> result = aplcntTrprDtlInfoMngMapper.selectDtlProbInfo(resultMap);

		for (Map<String, Object> map : result) {
			// 사례관리번호
			String sCaseMngView = map.get("CASE_MNG_NO") + "-" + map.get("CASE_MNG_ODRNO");
			if (map.get("CASE_MNG_NO") != null)
				map.put("CASE_MNG_VIEW", sCaseMngView);

			// 교급/학년
			String sAcbgGrade = "";
			if(map.get("ACBG") == null && map.get("GRADE") == null && map.get("GRDTN")== null) {
				map.put("ACBG_GRADE", sAcbgGrade);
			}else {
				sAcbgGrade = map.get("ACBG") + " " + map.get("GRADE") + " " + map.get("GRDTN");
				map.put("ACBG_GRADE", sAcbgGrade);
			}
		}
		return result;

	}

	/**
	 * @Method명 : saveDtlProbInfoConfirm
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 12.
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 문제행동발생 보고서 관리자확인
	 */
	@Override
	public void saveDtlProbInfoConfirm(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> updateRowList = parameterGroup.getUpdatedRowList();

		if (!updateRowList.isEmpty()) {
			for (Map<String, String> paraMap : updateRowList) {
				paraMap.put("LAST_MDFR_ID", loginVO.getId());
				paraMap.put("OCRN_DT", parameterGroup.getValue("OCRN_DT").substring(0, 7));
				aplcntTrprDtlInfoMngMapper.saveDtlProbInfoConfirm(paraMap);
			}
		}
	}

	/**
	 * @Method명 : selectPsycholRepoList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 12.
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 심리평가보고서 사례대상목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectPsycholRepoList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		paramMap.put("INST_NO", String.valueOf(loginVO.getInstNo()));
		paramMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		paramMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 사례대상자목록 조회
		return aplcntTrprDtlInfoMngMapper.selectPsycholRepoList(paramMap);
	}

	/**
	 * @Method명 : selectDtlPsycholInfo
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 12.
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 심리평가보고서 대상자정보 조회
	 */
	public List<Map<String, Object>> selectDtlPsycholInfo(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, String> requestMap = dmDtlParam.getSingleValueMap();

		List<Map<String, Object>> resultMap = aplcntTrprDtlInfoMngMapper.selectDtlPsycholInfo(requestMap);

		// 대상자목록 복호화 및 화면 출력항목 정제
		for (Map<String, Object> map : resultMap) {

			// 사례대상자번호-차수
			String caseMngView = map.get("CASE_MNG_NO") + "-" + map.get("CASE_MNG_ODRNO");
			map.put("CASE_MNG_VIEW", caseMngView);
			
		}

		dataRequest.setResponse("dsResultInfo", resultMap);
		return resultMap;
	}

	/**
	 * @Method명 : selectDtlPsycholList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : 2022. 10. 12.
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 심리평가보고서 개별심리검사 결과
	 */
	public List<Map<String, Object>> selectDtlPsycholList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();

		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> requestMap = dmDtlParam.getSingleValueMap();

		resultMap = aplcntTrprDtlInfoMngMapper.selectDtlPsycholList(requestMap);

		dataRequest.setResponse("dsReportList", resultMap);
		return resultMap;

	}

	/**
	 * @Method명   : selectdsFamInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectdsFamInfoImsy(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmParam");
		Map<String, Object> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmDtlParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmDtlParam.getValue("APLY_RCPT_SN"));

		return aplcntTrprDtlInfoMngMapper.selectdsFamInfoImsy(dtlMap);
	}

	/**
	 * @Method명   : selectTrprAtfino
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 : 대상자사진첨부파일번호 조회
	 */
	@Override
	public Map<String, String> selectTrprAtfino(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> dmParam = dataRequest.getParameterGroup("dmParam").getSingleValueMap();
		
		return aplcntTrprDtlInfoMngMapper.selectTrprAtfino(dmParam);
	}
}
