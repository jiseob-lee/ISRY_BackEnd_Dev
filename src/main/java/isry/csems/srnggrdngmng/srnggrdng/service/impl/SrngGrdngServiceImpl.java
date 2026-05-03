/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.srnggrdngmng.srnggrdng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csemd.mngrpage.aplcnttrprmng.mapper.AplcntTrprMngMapper;
import isry.csems.srnggrdngmng.srnggrdng.mapper.SrngGrdngMapper;
import isry.csems.srnggrdngmng.srnggrdng.service.SrngGrdngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : SrngGrdngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 10. 4.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 10. 4.
 * @수정내용 : - -
 */
@Service("csemsSrngGrdngService")
public class SrngGrdngServiceImpl implements SrngGrdngService {

	@Resource(name = "csemsSrngGrdngMapper")
	private SrngGrdngMapper srngGrdngMapper;

	// 디딤 서비스
	@Resource(name = "csemdSrngGrdngMapper")
	private isry.csemd.srnggrdngmng.srnggrdng.mapper.SrngGrdngMapper csemdSrngGrdngMapper;
	
	// 신청대상자관리[관리자페이지] 관련 매퍼
	@Resource(name = "aplcntTrprMngMapper__admin")
	private AplcntTrprMngMapper aplcntTrprMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectAplyRcpt
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectAplyRcptCd() {
		return srngGrdngMapper.selectAplyRcptCd();
	}

	/**
	 * @Method명 : selectMaapCd
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectMaapCd() {
		return srngGrdngMapper.selectMaapCd();
	}

	/**
	 * @Method명 : selectCampPrtcrCd
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCampPrtcrCd() {
		return srngGrdngMapper.selectCampPrtcrCd();
	}

	/**
	 * @Method명 : selectCampYngbgsCd
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCampYngbgsCd() {
		return srngGrdngMapper.selectCampYngbgsCd();
	}

	/**
	 * @Method명 : selectQustnbList
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 사전설문지(청소년 K-척도)
	 */
	@Override
	public List<Map<String, String>> selectQustnbList(HttpServletRequest request, DataRequest dataRequest) {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = srngGrdngMapper.selectQustnbList(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : selectdsQustnb2List
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 7.
	 * @Method설명 : 사전설문지(청소년 S-척도)
	 */
	@Override
	public List<Map<String, String>> selectdsQustnb2List(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = srngGrdngMapper.selectdsQustnb2List(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : selectdsQustnb3List
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 7.
	 * @Method설명 : 사전설문지(관찰자 K-척도)
	 */
	@Override
	public List<Map<String, String>> selectdsQustnb3List(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = srngGrdngMapper.selectdsQustnb3List(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : selectSrngCnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 면접심사채점표 (드림) 심사내용 조회
	 */
	@Override
	public List<Map<String, String>> selectSrngCnList(HttpServletRequest request, DataRequest dataRequest) {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = srngGrdngMapper.selectSrngCnList(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : selectGrdngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 면접심사채점표 (드림) 타당도및임상도척도 조회
	 */
	@Override
	public List<Map<String, String>> selectGrdngList(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = new ArrayList<Map<String, String>>();

		retMap = srngGrdngMapper.selectGrdngList(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : saveSrngGrdngPop
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 면접심사채점표 (드림) 수정(저장), 면접심사채점표(드림) 타당도및임상도척도 수정(저장)
	 */
	@Override
	public void saveSrngGrdngPop(HttpServletRequest request, DataRequest dataRequest) {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		String sTrprNo = dmParam.getValue("TRPR_INFO_NO");
		String sAplySn = dmParam.getValue("APLY_RCPT_SN");
		String sts = dmParam.getValue("STS");

		ParameterGroup dsSrngCn = dataRequest.getParameterGroup("dsSrngCn");
		ParameterGroup dsGrdng = dataRequest.getParameterGroup("dsGrdng");

		// getAllRowList
		List<Map<String, String>> getAllRowGrdng = dsGrdng.getAllRowList(); // 척도평가
		List<Map<String, String>> getAllSrngCn = dsSrngCn.getAllRowList(); // 심사내용
		// getUpdatedRowList
		List<Map<String, String>> getUpdateRowGrdng = dsGrdng.getUpdatedRowList(); // 척도평가
		
		
		// login 정보
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sUserId = loginVO.getId();

		for (Map<String, String> map : getAllSrngCn) {
			map.put("LAST_MDFR_ID", sUserId);
			map.put("TRPR_INFO_NO", sTrprNo);
			map.put("APLY_RCPT_SN", sAplySn);
			map.put("INTRVW_PTCPTN_SE_CD", "03");

			// AFA100 면접심사 관련 update
			srngGrdngMapper.updateSrngGrdngGrdng(map);
			
			map.put("FRST_RGTR_ID", sUserId);
			
			// 이력 저장을 위한 전체 select
			map = csemdSrngGrdngMapper.selectAFA100(map);
			//신청접수이력(AFA101) insert
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
			for (Map<String, String> map : getAllRowGrdng) {
				map.put("FRST_RGTR_ID", sUserId);
				map.put("LAST_MDFR_ID", sUserId);
				map.put("TRPR_INFO_NO", sTrprNo);
				map.put("APLY_RCPT_SN", sAplySn);

				// AFA140 신청접수심사채점표 insert
				srngGrdngMapper.insertSrngGrdngPopGrdng(map);
			}
		} else if (sts.equals("1")) {
			for (Map<String, String> map : getUpdateRowGrdng) {
				map.put("LAST_MDFR_ID", sUserId);
				map.put("TRPR_INFO_NO", sTrprNo);
				map.put("APLY_RCPT_SN", sAplySn);
				
				// AFA140 신청접수심사채점표 update
				srngGrdngMapper.updateSrngGrdngPopGrdng(map);
			}
		}

	}

	/**
	 * @Method명   : selectPtcptList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 면접심사채점표(드림) 대상자문제상태 조회)
	 */
	@Override
	public List<Map<String, String>> selectPtcptList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = srngGrdngMapper.selectPtcptList(dtlMap);

		return retMap;
	}

}
