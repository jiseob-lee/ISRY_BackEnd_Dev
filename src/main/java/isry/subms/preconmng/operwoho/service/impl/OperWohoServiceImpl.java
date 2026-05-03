/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operwoho.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;
import isry.subms.preconmng.operwoho.mapper.OperWohoMapper;
import isry.subms.preconmng.operwoho.service.OperWohoService;

/**
 * @파일명 : OperWohoServiceImpl.java
 * @프로그램 설명 : 운영시수 service implement - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 6. 29.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 6. 29.
 * @수정내용 : - -
 */
@Service("operWohoService")
public class OperWohoServiceImpl implements OperWohoService {

	@Resource(name = "operWohoMapper")
	private OperWohoMapper operWohoMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectOperWohoList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영시수 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectOperWohoList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearchMap = dmSearch.getSingleValueMap();

		Map<String, Object> paraMap = new HashMap<String, Object>();
		dmSearchMap.forEach((StrKey, StrValue) -> {
			paraMap.put(StrKey, StrValue);
		});
		paraMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		// 운영시수목록(AFA600) 조회
		List<Map<String, Object>> operWohoList = operWohoMapper.selectOperWohoList(paraMap);

		for (Map<String, Object> map : operWohoList) {
			// 교육기간
			String eduPrd = Formatter.dateFormat(String.valueOf(map.get("PVSN_BGNG_YMD"))) + "~"
					+ Formatter.dateFormat(String.valueOf(map.get("PVSN_END_YMD")));
			map.put("EDU_PRD", eduPrd);
			// 운영시수
			String avgOper = String.valueOf(map.get("OPER_HR_CNT"));
			String avgGoal = String.valueOf(map.get("GOAL_HR_CNT"));
			String minOper = String.valueOf(map.get("MIN_HR_CNT"));
			if ("0".equals(map.get("GOAL_OPER_SE"))) {
				map.put("OPER_HR_CNT", avgOper + "\n" + minOper);
			} else {
				String prgrsRate = "0";
				if (!avgGoal.equals("0")) {
					// 진도율
					prgrsRate = String
							.valueOf(Math.round(Float.valueOf(avgOper) / Float.valueOf(avgGoal) * 10000) / 100.0f);
				}
				map.put("OPER_HR_CNT", avgOper + "\n(" + prgrsRate + "%)\n" + minOper);
			}
			// 담당자연락처
			map.put("PIC_TELNO", Formatter.phoneFormat(String.valueOf(map.get("PIC_TELNO")), 1));
		}

		return operWohoList;
	}

	/**
	 * @Method명 : selectOperWohoMng
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 6.
	 * @Method설명 : 운영시수관리 조회
	 */
	@Override
	public List<Map<String, Object>> selectOperWohoMng(DataRequest dataRequest) throws Exception {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> dmDtlParamMap = dmDtlParam.getSingleValueMap();

		Map<String, Object> paraMap = new HashMap<String, Object>();
		dmDtlParamMap.forEach((StrKey, StrValue) -> {
			paraMap.put(StrKey, StrValue);
		});

		// 목표시수관리&운영시수관리(AFA600) 조회
		List<Map<String, Object>> operWohoMngList = operWohoMapper.selectOperWohoMng(paraMap);

		for (Map<String, Object> map : operWohoMngList) {
			// 진도율
			String avgOper = String.valueOf(map.get("ACCMLT_OPER_WOHO"));
			String avgGoal = String.valueOf(map.get("YR_GOAL_WOHO"));
			String prgrsRate = "0";
			if (!avgGoal.equals("0")) {
				prgrsRate = String
						.valueOf(Math.round(Float.valueOf(avgOper) / Float.valueOf(avgGoal) * 10000) / 100.0f);
			}
			map.put("PRGRS_RATE", prgrsRate);
			// 담당자연락처
			map.put("PIC_TELNO", Formatter.phoneFormat(String.valueOf(map.get("PIC_TELNO")), 1));
		}

		return operWohoMngList;
	}

	/**
	 * @Method명 : saveOperWohoMng
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 7. 4.
	 * @Method설명 : 운영시수 삽입/수정/삭제
	 */
	@Override
	public void saveOperWohoMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();

		for (Map<String, String> map : insertedRowList) {
			// 기존데이터 존재 유무체크
			Map<String, String> chkMap = operWohoMapper.chkOperWohoMng(map);
			if (chkMap != null) {
				if (chkMap.get("RPT_MM").equals("00")) {
					throw new AppWorksException(chkMap.get("PVSN_RESRCE_NM") + "과정의 " + chkMap.get("CLRO_SE_CD_NM")
							+ "반 " + chkMap.get("EDU_PROGRM_SE_CD_NM") + " 목표시수가 이미 존재합니다.");
				} else {
					throw new AppWorksException(chkMap.get("PVSN_RESRCE_NM") + "과정의 " + chkMap.get("CLRO_SE_CD_NM")
							+ "반 " + chkMap.get("EDU_PROGRM_SE_CD_NM") + " " + chkMap.get("SEMSTR_SE_CD_NM") + " "
							+ chkMap.get("RPT_MM") + "월 운영시수가 이미 존재합니다.");
				}
			}

			map.put("PIC_NO", userVo.getEnfsnNo());
			map.put("LGN_USER_ID", userVo.getId());

			// 운영시수(AFA600) 삽입
			operWohoMapper.insertOperWohoMng(map);
		}

		for (Map<String, String> map : updatedRowList) {
			// 기존데이터 존재 유무체크
			Map<String, String> chkMap = operWohoMapper.chkOperWohoMng(map);
			if (chkMap != null) {
				if (chkMap.get("RPT_MM").equals("00")) {
					throw new AppWorksException(chkMap.get("PVSN_RESRCE_NM") + "과정의 " + chkMap.get("CLRO_SE_CD_NM")
							+ "반 " + chkMap.get("EDU_PROGRM_SE_CD_NM") + " 목표시수가 이미 존재합니다");
				} else {
					throw new AppWorksException(chkMap.get("PVSN_RESRCE_NM") + "과정의 " + chkMap.get("CLRO_SE_CD_NM")
							+ "반 " + chkMap.get("EDU_PROGRM_SE_CD_NM") + " " + chkMap.get("SEMSTR_SE_CD_NM") + " "
							+ chkMap.get("RPT_MM") + "월 운영시수가 이미 존재합니다.");
				}
			}

			map.put("PIC_NO", userVo.getEnfsnNo());
			map.put("LGN_USER_ID", userVo.getId());

			// 운영시수(AFA600) 수정
			operWohoMapper.updateOperWohoMng(map);
		}

		for (Map<String, String> map : deletedRowList) {
			// 운영시수(AFA600) 삭제
			operWohoMapper.deleteOperWohoMng(map);
		}
	}

}
