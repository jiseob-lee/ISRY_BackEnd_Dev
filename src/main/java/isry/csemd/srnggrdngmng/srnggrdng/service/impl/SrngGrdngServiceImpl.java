/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.srnggrdngmng.srnggrdng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csemd.mngrpage.aplcnttrprmng.mapper.AplcntTrprMngMapper;
import isry.csemd.srnggrdngmng.srnggrdng.mapper.SrngGrdngMapper;
import isry.csemd.srnggrdngmng.srnggrdng.service.SrngGrdngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;

/**
 * @파일명 : SrngGrdngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 10. 4.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 10. 4.
 * @수정내용 : - -
 */
@Service("csemdSrngGrdngService")
public class SrngGrdngServiceImpl implements SrngGrdngService {

	// 서비스
	@Resource(name = "csemdSrngGrdngMapper")
	private SrngGrdngMapper srngGrdngMapper;

	// 신청대상자관리[관리자페이지] 관련 매퍼
	@Resource(name = "aplcntTrprMngMapper__admin")
	private AplcntTrprMngMapper aplcntTrprMngMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectAplyRcptCd
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectAplyRcptCd() {

		return srngGrdngMapper.selectAplyRcptCd();
	}

	/**
	 * @Method명 : selectScrennList
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 : 면접심사채점표 dsList 조회
	 */
	@Override
	public List<Map<String, String>> selectScrennList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		return srngGrdngMapper.selectScrennList(dtlMap);
	}

	/**
	 * @Method명 : selectGrdngList
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 : 면접심사채점표 dsGrdng 조회
	 */
	@Override
	public List<Map<String, String>> selectGrdngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = srngGrdngMapper.selectGrdngList(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : saveSrngGrdngPop
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 : 면접심사채점표 저장, 수정
	 */
	@Override
	public void saveSrngGrdngPop(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		String sTrprNo = dmParam.getValue("TRPR_INFO_NO");
		String sAplySn = dmParam.getValue("APLY_RCPT_SN");
		String sts = dmParam.getValue("STS");

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dsGrdng = dataRequest.getParameterGroup("dsGrdng");

		// getAllRowList
		List<Map<String, String>> getAllRowdsList = dsList.getAllRowList();
		List<Map<String, String>> getAllRowdsGrdng = dsGrdng.getAllRowList();

		// getUpdatedRowList
		List<Map<String, String>> getUpdateddsGrdng = dsGrdng.getUpdatedRowList();

		// login 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId();

		for (Map<String, String> map : getAllRowdsList) {
			map.put("LAST_MDFR_ID", sUserId);
			map.put("TRPR_INFO_NO", sTrprNo);
			map.put("APLY_RCPT_SN", sAplySn);
			map.put("INTRVW_PTCPTN_SE_CD", "03");

			// 면접스크리닝총평
			if (map.get("INTRVW_SRNG_GENRVW_CN") == null || map.get("INTRVW_SRNG_GENRVW_CN").equals("")) {
				String intrvwSrngGenrvwCn = "면접관총평 : ";
				if (map.get("INTERV_GENRVW_CN") != null) {
					intrvwSrngGenrvwCn = intrvwSrngGenrvwCn + map.get("INTERV_GENRVW_CN");
				}
				intrvwSrngGenrvwCn = intrvwSrngGenrvwCn + "\n심층논의사항 : ";
				if (map.get("DEPTHS_ARGMT_MATTER_CN") != null) {
					intrvwSrngGenrvwCn = intrvwSrngGenrvwCn + map.get("DEPTHS_ARGMT_MATTER_CN");
				}
				map.put("INTRVW_SRNG_GENRVW_CN", intrvwSrngGenrvwCn);
				System.err.println("intrvwSrngGenrvwCn : " + map.get("intrvwSrngGenrvwCn"));
			}
			System.err.println("INTRVW_SRNG_GENRVW_CN : " + map.get("INTRVW_SRNG_GENRVW_CN"));

			// AFA100 면접심사 관련 update
			srngGrdngMapper.updateSrngGrdngPopScrenn(map);

			map.put("FRST_RGTR_ID", sUserId);

			// 이력 저장을 위한 전체 select
			map = srngGrdngMapper.selectAFA100(map);
			// 신청접수이력(AFA101) : insert
			aplcntTrprMngMapper.insertAplyRcptHstr(map);

			// 상태 대분류 코드 조회
			map.put("APLY_RCPT_SRNG_SE_CD", "05"); // 심사상태대분류코드(05)

			int chkCount = aplcntTrprMngMapper.chkAplyRcptSrngPrgrsStts(map);

			if (chkCount == 0) {
				// AFA150 insert
				aplcntTrprMngMapper.insertAplyRcptSrngPrgrsSttsInfoHstr(map);
			} else if (chkCount > 0) {
				// AFA150 update
				aplcntTrprMngMapper.updateAplyRcptSrngPrgrsSttsInfoHstr(map);
			}

		}

		if (sts.equals("0")) {
			for (Map<String, String> map : getAllRowdsGrdng) {
				map.put("FRST_RGTR_ID", sUserId);
				map.put("LAST_MDFR_ID", sUserId);
				map.put("TRPR_INFO_NO", sTrprNo);
				map.put("APLY_RCPT_SN", sAplySn);

				// AFA140 신청접수심사채점표 insert
				srngGrdngMapper.insertSrngGrdngPopGrdng(map);
			}
		} else if (sts.equals("1")) {
			for (Map<String, String> map : getUpdateddsGrdng) {
				map.put("LAST_MDFR_ID", sUserId);
				map.put("TRPR_INFO_NO", sTrprNo);
				map.put("APLY_RCPT_SN", sAplySn);

				// AFA140 신청접수심사채점표 update
				srngGrdngMapper.updateSrngGrdngPopGrdng(map);
			}
		}

	}

	/**
	 * @Method명 : selectScrenn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 4.
	 * @Method설명 : 면접심사채점표 스크리닝 폼 조회
	 */
	@Override
	public List<Map<String, Object>> selectScrenn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> paraMap = dmParam.getSingleValueMap();

		List<Map<String, Object>> resultMap = srngGrdngMapper.selectScrenn(paraMap);

		for (Map<String, Object> map : resultMap) {

			// 법률 문제
			String lgsltnProbm = "";
			// 보호관찰
			if (map.get("PRTCTN_OBSERV_YN").equals("Y")) {
				lgsltnProbm = lgsltnProbm + "보호관찰";
			}
			// 재판진행
			if (map.get("TRIAL_PRGRS_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "재판진행";
				else
					lgsltnProbm = lgsltnProbm + "\n재판진행";
			}
			// 학폭위회부
			if (map.get("SCHL_VIOLNC_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "학폭위회부";
				else
					lgsltnProbm = lgsltnProbm + "\n학폭위회부";
			}
			// 경찰조사중
			if (map.get("POLC_EXMN_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "경찰조사중";
				else
					lgsltnProbm = lgsltnProbm + "\n경찰조사중";
			}
			// 해당없음
			if (map.get("RLVT_NAPC_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "해당없음";
				else
					lgsltnProbm = lgsltnProbm + "\n해당없음";
			}

			map.put("LGSLTN_PROBM", lgsltnProbm);

			// 공동체 문제
			String collabGrpProbm = "";
			// 알러지
			if (map.get("ALLERGY_YN").equals("Y")) {
				collabGrpProbm = collabGrpProbm + "알러지";
			}
			// 약물복용
			if (map.get("DRFSTF_TAKNG_YN").equals("Y")) {
				if (collabGrpProbm.equals(""))
					collabGrpProbm = collabGrpProbm + "약물복용";
				else
					collabGrpProbm = collabGrpProbm + "\n약물복용";
			}
			// 도움필요
			if (map.get("HELP_NEED_YN").equals("N")) {
				if (collabGrpProbm.equals(""))
					collabGrpProbm = collabGrpProbm + "도움필요";
				else
					collabGrpProbm = collabGrpProbm + "\n도움필요";
			}

			map.put("COLLAB_GRP_PROBM", collabGrpProbm);

		}

		return resultMap;
	}

	/**
	 * @Method명 : selectIntrvwSchdlList
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectIntrvwSchdlList(Map<String, String> requestMap) throws Exception {

		List<Map<String, Object>> result = srngGrdngMapper.selectIntrvwSchdlList(requestMap); // SBD400 : select

		for (Map<String, Object> map : result) {

			// 날짜 포메팅
			map.put("INTRVW_YMD_VIEW", Formatter.dateFormat(String.valueOf(map.get("INTRVW_YMD"))));
		}

		return result;
	}

	/**
	 * @Method명 : insertIntrvwSchdlMng
	 * @param dmSearch
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 등록
	 */
	@Override
	public void insertIntrvwSchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		dmSearch.put("FRST_RGTR_ID", loginVO.getId());
		dmSearch.put("LAST_MDFR_ID", loginVO.getId());

		int chk = srngGrdngMapper.chkIntrvwSchdlMng(dmSearch); // SBD400 : select

		if (chk == 0) {

			srngGrdngMapper.insertIntrvwSchdlMng(dmSearch); // SBD400 : insert

		} else {

			Map<String, Object> msg = new HashMap<String, Object>();

			msg.put("alert", "동일시간대 예약건이 존재하여 저장할 수 없습니다.");
			dataRequest.setMetadata(true, msg);

		}

	}

	/**
	 * @Method명 : updateIntrvwSchdlMng
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 5.
	 * @Method설명 : 면접일정관리 수정
	 */
	@Override
	public void updateIntrvwSchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> updatedDsList = dataRequest.getParameterGroup("dsList").getSingleValueMap();
		updatedDsList.put("LAST_MDFR_ID", loginVO.getId());

		int chk = srngGrdngMapper.chkIntrvwSchdlMng(updatedDsList); // SBD400 : select
		int maxNope = Integer.valueOf(updatedDsList.get("MXMM_NOPE"));
		int aplyNope = srngGrdngMapper.chkSavedIntrvwSchdl(updatedDsList);

		Map<String, Object> msg = new HashMap<String, Object>();
		
		if (chk != 0) {

			msg.put("alert", "동일시간대 예약건이 존재하여 저장할 수 없습니다.");
			dataRequest.setMetadata(true, msg);

		} else if (maxNope < aplyNope) {

			msg.put("alert", "최대인원이 신청인원보다 작아 저장할 수 없습니다.");
			dataRequest.setMetadata(true, msg);
			
		} else {

			srngGrdngMapper.updateIntrvwSchdlMng(updatedDsList); // SBD400 : update

			// 면접일자 혹은 시간이 변경되었을시 해당 타임의 신청자들의 예약정보를 clear한다.
			if (!updatedDsList.get("INTRVW_YMD").equals(updatedDsList.get("INTRVW_YMD__origin"))
					|| !updatedDsList.get("INTRVW_HR").equals(updatedDsList.get("INTRVW_HR__origin")))
				srngGrdngMapper.updateAplyRcptIntrvwSchdl(updatedDsList); // AFA100 : update

		}
	}

	/**
	 * @Method명 : deleteIntrvwSchdlMng
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 5.
	 * @Method설명 : 면접일정관리 삭제
	 */
	@Override
	public void deleteIntrvwSchdlMng(DataRequest dataRequest) throws Exception {

		Map<String, String> deletedDsList = dataRequest.getParameterGroup("dsList").getSingleValueMap();

		int chk = srngGrdngMapper.chkSavedIntrvwSchdl(deletedDsList); // AFA100 : select 신청인원 수

		if (chk == 0) {

			srngGrdngMapper.deleteIntrvwSchdlMng(deletedDsList); // SBD400 : delete

		} else {

			Map<String, Object> msg = new HashMap<String, Object>();

			msg.put("alert", "동일시간대 예약건이 존재하여 삭제할 수 없습니다.");
			dataRequest.setMetadata(true, msg);

		}
	}

	/**
	 * @Method명 : selectIntrvwAplcntList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 5.
	 * @Method설명 : 면접참여자 조회
	 */
	@Override
	public List<Map<String, Object>> selectIntrvwAplcntList(DataRequest dataRequest) throws Exception {

		Map<String, String> dmTmpParam = dataRequest.getParameterGroup("dmTmpParam").getSingleValueMap();

		List<Map<String, Object>> result = srngGrdngMapper.selectIntrvwAplcntList(dmTmpParam); // AFA100 : select

		for (Map<String, Object> map : result) {

			// 날짜 포메팅
			map.put("BRDT", Formatter.dateFormat(String.valueOf(map.get("TRPR_BRTH_YMD"))));
		}

		return result;
	}

}
