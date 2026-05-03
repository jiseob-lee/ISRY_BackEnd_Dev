/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.trpr.service.impl;

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

import isry.aimns.attendmgmt.trpr.mapper.TrprMapper;
import isry.aimns.attendmgmt.trpr.service.TrprService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.Masking;

/**
 * @파일명 : TrprServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 6. 7.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 6. 7.
 * @수정내용 : - -
 */
@Service("trprService")
public class TrprServiceImpl implements TrprService {

	@Resource(name = "trprMapper")
	private TrprMapper trprMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 수료자 명단 */
	@Override
	public List<Map<String, String>> selectTrprFnsh(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = paraGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> result = trprMapper.selectTrprFnsh(mapParam);

		for (Map<String, String> map : result) {
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
		}

		return result;
	}

	/** 조기취업자 명단 */
	@Override
	public List<Map<String, String>> selectTrprEmpymn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = paraGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> result = trprMapper.selectTrprEmpymn(mapParam);

		for (Map<String, String> map : result) {
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
		}

		return result;
	}

	/** 기타자 명단 */
	@Override
	public List<Map<String, String>> selectTrprEtc(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = paraGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> result = trprMapper.selectTrprEtc(mapParam);

		for (Map<String, String> map : result) {
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
		}

		return result;
	}

	/** 중도탈락자 명단 */
	@Override
	public List<Map<String, String>> selectTrprMdstrmFailr(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = paraGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> result = trprMapper.selectTrprMdstrmFailr(mapParam);

		for (Map<String, String> map : result) {
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
		}

		return result;
	}

	/** 자격증취득자 명단 */
	@Override
	public List<Map<String, String>> selectTrprCertiAcqs(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = paraGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> result = trprMapper.selectTrprCertiAcqs(mapParam);

		for (Map<String, String> map : result) {
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
		}

		return result;
	}

	/** 학력취득자명단 */
	@Override
	public List<Map<String, String>> selectTrprAcbgAcqs(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup paraGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = paraGroup.getAllRowList().get(0);

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		List<Map<String, String>> result = trprMapper.selectTrprAcbgAcqs(mapParam);

		for (Map<String, String> map : result) {
			map.replace("TRPR_NM", Masking.nameMasking(map.get("TRPR_NM")));
		}

		return result;
	}

	/** 학력취득자명단 상세조회 */
	@Override
	public Map<String, List<Map<String, String>>> selectTrprAcbgAcqsDtl(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		Map<String, List<Map<String, String>>> mapList = new HashMap<String, List<Map<String, String>>>();

		mapParam.put("TRPR_INFO_NO", paramGroup.getValue("TRPR_INFO_NO"));
		mapParam.put("CASE_MNG_NO", paramGroup.getValue("CASE_MNG_NO"));
		mapParam.put("CASE_MNG_ODRNO", paramGroup.getValue("CASE_MNG_ODRNO"));
		mapParam.put("RESRCE_NO", paramGroup.getValue("RESRCE_NO"));

		mapList.put("dsLast", trprMapper.selectTrprLastAcbg(mapParam));
		mapList.put("dsEdu", trprMapper.selectTrprEduInfo(mapParam));

		return mapList;
	}

	/**
	 * @Method명 : selectCaseTrprList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 12. 16.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTrprHstrList(HttpServletRequest request, Map<String, String> paramMap)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		 List<Map<String, Object>> result = trprMapper.selectTrprHstrList(paramMap);

		for (Map<String, Object> map : result) {
			// 대상자명
			String trprNmEncpt = String.valueOf(map.get("TRPR_NM_ENCPT"));
			map.put("TRPR_NM", trprNmEncpt);
			
			// 주민등록번호 마스킹
			String rrnoEncpt = String.valueOf(map.get("RRNO_ENCPT"));
			map.put("RRNO", Masking.rrnoMasking(rrnoEncpt));
			
			// 휴대전화번호 포메팅
			String mblTelnoEncpt = String.valueOf(map.get("MBL_TELNO_ENCPT"));
			map.put("MBL_TELNO", Formatter.phoneFormat(mblTelnoEncpt,1));
			
			// 사례종결일자 포메팅
			String caseTrmnYmd = String.valueOf(map.get("CASE_TRMN_YMD"));
			map.put("CASE_TRMN_YMD", Formatter.dateFormat(caseTrmnYmd));
		}

		return result;
	}

}
