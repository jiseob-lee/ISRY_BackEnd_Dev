/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.mngrpage.aplcnttrprdtlinfomng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csems.mngrpage.aplcnttrprdtlinfomng.mapper.AplcntTrprDtlInfoMngMapper;
import isry.csems.mngrpage.aplcnttrprdtlinfomng.service.AplcntTrprDtlInfoMngService;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;

/**
 * @파일명        : AplcntTrprDtlInfoMngServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 5. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("csemsMngrPageAplcntTrprDtlInfoMngService")
public class AplcntTrprDtlInfoMngServiceImpl implements AplcntTrprDtlInfoMngService{

	@Resource(name = "csemsMngrPageAplcntTrprDtlInfoMngMapper")
	private AplcntTrprDtlInfoMngMapper aplcntTrprDtlInfoMngMapper;

	// 채번
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectPtcptReqstdAplcntPop
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 2.
	 * @Method설명 : 참가신청서_신청자용 조회(드림)
	 */
	@Override
	public List<Map<String, Object>> selectPtcptReqstdAplcntPop(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmParam");
		Map<String, Object> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmDtlParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmDtlParam.getValue("APLY_RCPT_SN"));

		List<Map<String, Object>> retMap = aplcntTrprDtlInfoMngMapper.selectPtcptReqstdAplcntPop(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : savePtcptReqstdAplcntPop
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 2.
	 * @Method설명 : 참가신청서_신청자용 저장(드림)
	 */
	@Override
	public void savePtcptReqstdAplcntPop(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		String sts = dmParam.getValue("STS");

		// getAllRowList
		List<Map<String, String>> getAllRowList = dsList.getAllRowList();
		// getUpdateRowList
		List<Map<String, String>> getUpdatedRowList = dsList.getUpdatedRowList();

		HttpSession session = request.getSession();
		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVo.getId();

		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> pkMap = new HashMap<>();

		seqMap.put("USER_ID", sUserId);
		seqMap.put("RENU_NO_SE_CD", "CH");
		seqMap.put("RENU_YMD", DateUtil.getToday());

		pkMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
		String renuNo = String.valueOf(pkMap.get("RENU_NO")); // 발번

		// 복수선택유형관리번호가 없을 경우 사용할 PK
		Map<String, String> sCompnoPK = new HashMap<>();
		sCompnoPK.put("COMPNO_CHC_TYPE_MNG_NO", renuNo);
		sCompnoPK.put("FRST_RGTR_ID", sUserId);
		sCompnoPK.put("LAST_MDFR_ID", sUserId);

		// 복수선택유형관리번호 체크
		String compnoChc = String.valueOf(dsList.getValue(0, "COMPNO_CHC_TYPE_MNG_NO"));
		// 등록되어있는 복수선택유형관리번호
		String dsListPk = dsList.getValue(0, "COMPNO_CHC_TYPE_MNG_NO");

		if (sts.equals("0")) {
			// 복수선택유형관리번호가 없을 경우 복수선택유형 PK insert
			aplcntTrprDtlInfoMngMapper.insertCompnoChcTypeMngNo(sCompnoPK); // AFA130

			// AFA120, AFA121 insert
			for (Map<String, String> map : getAllRowList) {
				map.put("FRST_RGTR_ID", sUserId);
				map.put("LAST_MDFR_ID", sUserId);
				map.put("COMPNO_CHC_TYPE_MNG_NO", sCompnoPK.get("COMPNO_CHC_TYPE_MNG_NO"));

				aplcntTrprDtlInfoMngMapper.insertPtcptReqstdAplcntPop(map);// AFA120
				aplcntTrprDtlInfoMngMapper.insertPtcptReqstdAplcntPopHstr(map);// AFA121
			}
		} else if (sts.equals("1")) {
			// 복수선택유형관리번호가 없는 경우
			if (compnoChc == null || compnoChc.equals("null") || compnoChc.equals("")) {

				aplcntTrprDtlInfoMngMapper.insertCompnoChcTypeMngNo(sCompnoPK); // AFA130

				// AFA120 update, AFA121 insert
				for (Map<String, String> map : getUpdatedRowList) {
					map.put("LAST_MDFR_ID", sUserId);
					map.put("COMPNO_CHC_TYPE_MNG_NO", sCompnoPK.get("COMPNO_CHC_TYPE_MNG_NO"));

					aplcntTrprDtlInfoMngMapper.updatePtcptReqstdAplcntPop(map);
					map.put("FRST_RGTR_ID", sUserId);
					aplcntTrprDtlInfoMngMapper.insertPtcptReqstdAplcntPopHstr(map);
				}

				// 복수선택유형관리번호가 있는 경우
			} else if (compnoChc != null || !compnoChc.equals("null") || !compnoChc.equals("")) {
				Map<String, String> delMap = new HashMap<>();
				delMap.put("COMPNO_CHC_TYPE_MNG_NO", dsList.getValue("COMPNO_CHC_TYPE_MNG_NO"));

				// 복수선택유형상세 delete
				aplcntTrprDtlInfoMngMapper.deleteCompnoChcTypeMngNoAFA131(delMap); // AFA131

				// AFA120 update, AFA121 insert
				for (Map<String, String> map : getUpdatedRowList) {
					map.put("LAST_MDFR_ID", sUserId);
					map.put("COMPNO_CHC_TYPE_MNG_NO", dsListPk);

					aplcntTrprDtlInfoMngMapper.updatePtcptReqstdAplcntPop(map);
					map.put("FRST_RGTR_ID", sUserId);
					aplcntTrprDtlInfoMngMapper.insertPtcptReqstdAplcntPopHstr(map);
				}
			}

		}

		Map<String, String> compnoKey = new HashMap<String, String>();

		// 법률문제처분 LGSLTN
		String[] lgsltn = getAllRowList.get(0).get("LGSLTN").split(",");

		if (!getAllRowList.get(0).get("LGSLTN").isEmpty() && !getAllRowList.get(0).get("LGSLTN").equals("")) {
			for (String str : lgsltn) {
				compnoKey.put("COMPNO_TYPE_SCLAS_SE_CD", str);
				compnoKey.put("FRST_RGTR_ID", sUserId);
				compnoKey.put("LAST_MDFR_ID", sUserId);

				if (dsListPk != null && !dsListPk.equals("")) {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", dsListPk);
				} else {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", sCompnoPK.get("COMPNO_CHC_TYPE_MNG_NO"));
				}
				aplcntTrprDtlInfoMngMapper.insertCompnoChcTypeDtl(compnoKey);
			}
		}

		// 상담경험
		String[] dscsn = getAllRowList.get(0).get("DSCSN").split(",");

		if (!getAllRowList.get(0).get("DSCSN").isEmpty() && !getAllRowList.get(0).get("DSCSN").equals("")) {
			for (String str : dscsn) {
				compnoKey.put("COMPNO_TYPE_SCLAS_SE_CD", str);
				compnoKey.put("FRST_RGTR_ID", sUserId);
				compnoKey.put("LAST_MDFR_ID", sUserId);

				if (dsListPk != null && !dsListPk.equals("")) {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", dsListPk);
				} else {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", sCompnoPK.get("COMPNO_CHC_TYPE_MNG_NO"));
				}
				aplcntTrprDtlInfoMngMapper.insertCompnoChcTypeDtl(compnoKey);
			}
		}

		// 질병상태
		String[] diss = getAllRowList.get(0).get("DISS").split(",");
		String dissEtc = getAllRowList.get(0).get("DISS_ETC");

		if (!getAllRowList.get(0).get("DISS").isEmpty() && !getAllRowList.get(0).get("DISS").equals("")) {
			for (String str : diss) {
				compnoKey.put("COMPNO_TYPE_SCLAS_SE_CD", str);
				compnoKey.put("FRST_RGTR_ID", sUserId);
				compnoKey.put("LAST_MDFR_ID", sUserId);

				if (dsListPk != null && !dsListPk.equals("")) {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", dsListPk);
				} else {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", sCompnoPK.get("COMPNO_CHC_TYPE_MNG_NO"));
				}

				if (str.substring(4).equals("99")) {
					compnoKey.put("COMPNO_CHC_TYPE_ETC_NM", dissEtc);
				} else {
					compnoKey.put("COMPNO_CHC_TYPE_ETC_NM", "");
				}

				aplcntTrprDtlInfoMngMapper.insertCompnoChcTypeDtl(compnoKey);
			}
		}

		// 보호자참석여부
		String[] prtcr = getAllRowList.get(0).get("PRTCR").split(",");

		if (!getAllRowList.get(0).get("PRTCR").isEmpty() && !getAllRowList.get(0).get("PRTCR").equals("")) {
			for (String str : prtcr) {
				compnoKey.put("COMPNO_TYPE_SCLAS_SE_CD", str);
				compnoKey.put("FRST_RGTR_ID", sUserId);
				compnoKey.put("LAST_MDFR_ID", sUserId);

				if (dsListPk != null && !dsListPk.equals("")) {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", dsListPk);
				} else {
					compnoKey.put("COMPNO_CHC_TYPE_MNG_NO", sCompnoPK.get("COMPNO_CHC_TYPE_MNG_NO"));
				}
				aplcntTrprDtlInfoMngMapper.insertCompnoChcTypeDtl(compnoKey);
			}
		}

	}

	/**
	 * @Method명 : selectAdhrncWrtcns
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 2.
	 * @Method설명 : 참가자동의서 조회 (드림)
	 */
	@Override
	public List<Map<String, String>> selectAdhrncWrtcns(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmaParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dtlMap = new HashMap<>();

		dtlMap.put("TRPR_INFO_NO", dmaParam.getValue("TRPR_INFO_NO"));
		dtlMap.put("APLY_RCPT_SN", dmaParam.getValue("APLY_RCPT_SN"));

		List<Map<String, String>> retMap = aplcntTrprDtlInfoMngMapper.selectAdhrncWrtcns(dtlMap);

		return retMap;
	}

	/**
	 * @Method명 : saveAdhrncWrtcns
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 2.
	 * @Method설명 : 참가자동의서 저장 (드림)
	 */
	@Override
	public void saveAdhrncWrtcns(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsWrtcns = dataRequest.getParameterGroup("dsWrtcns");
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");

		// getAllRowList
		List<Map<String, String>> getinsertedRowList = dsWrtcns.getInsertedRowList();
		// getUpdatedRowList
		List<Map<String, String>> getupdatedRowList = dsWrtcns.getUpdatedRowList();

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId();

		String STS = dmParam.getValue("STS");

		if (STS.equals("0")) {
			for (Map<String, String> map : getinsertedRowList) {
				map.put("FRST_RGTR_ID", sUserId);
				map.put("LAST_MDFR_ID", sUserId);
				map.put("TRPR_INFO_NO", dmParam.getValue("TRPR_INFO_NO"));
				map.put("APLY_RCPT_SN", dmParam.getValue("APLY_RCPT_SN"));
				aplcntTrprDtlInfoMngMapper.insertAdhrncWrtcns(map);
			}

		} else if (STS.equals("1")) {
			for (Map<String, String> map : getupdatedRowList) {
				map.put("LAST_MDFR_ID", sUserId);
				map.put("TRPR_INFO_NO", dsWrtcns.getValue(0, "TRPR_INFO_NO"));
				map.put("APLY_RCPT_SN", dsWrtcns.getValue(0, "APLY_RCPT_SN"));
				aplcntTrprDtlInfoMngMapper.updateAdhrncWrtcns(map);
			}
		}

	}

	/**
	 * @Method명   : selectAdhrncWrtcnsChck
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 참가자동의서 작성여부
	 */
	@Override
	public int selectAdhrncWrtcnsChck(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getAllRowList().get(0);
		
		return aplcntTrprDtlInfoMngMapper.selectAdhrncWrtcnsChck(paramMap);
	}
	
}
