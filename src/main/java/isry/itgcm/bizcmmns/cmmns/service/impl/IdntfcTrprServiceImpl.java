/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.IdntfcTrprMapper;
import isry.itgcm.bizcmmns.cmmns.service.IdntfcTrprService;
import isry.itgcm.casemng.uneart.mapper.TrprInqMapper;
import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명 : IdntfcTrprServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Yoo.Chi.Hoon
 * @작성일 : 2022. 8. 22.
 * @수정자 : Yoo.Chi.Hoon
 * @수정일 : 2022. 8. 22.
 * @수정내용 : - -
 */
@Service("idntfcTrprService")
public class IdntfcTrprServiceImpl implements IdntfcTrprService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "idntfcTrprMapper")
	private IdntfcTrprMapper idntfcTrprMapper;

	@Resource(name = "trprInqMapper")
	private TrprInqMapper trprInqMapper;

	@Resource(name = "personalInfoService")
	private PersonalInfoService personalInfoService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectIdntfcTrprList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 8. 22.
	 * @Method설명 : 개인식별 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectIdntfcTrprList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");

		if (searchParam == null) {
			throw new AppWorksException("조회할 대상자가 없습니다..", Alert.ERROR);
		}
		Map<String, String> paramMap = searchParam.getSingleValueMap();
		
		return idntfcTrprMapper.selectIdntfcTrprList(paramMap);

	}

	/**
	 * @Method명 : processIndvIdntfcReg
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 8. 23.
	 * @Method설명 : 개인식별 등록
	 */
	@Override
	public Map<String, Object> processIndvIdntfcReg(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		// 대상자식별번호자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsIdntfcListSave");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 대상자 식별번호자료가 없습니다.", Alert.ERROR);
		}
		// 식별대상 DataMap
		ParameterGroup dmParamGroup = dataRequest.getParameterGroup("dmPersonalInfo");
		if (dmParamGroup == null) {
			throw new AppWorksException("저장할 대상자 식별번호자료가 없습니다.", Alert.ERROR);
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		String sIndvIdntfcNoUpd = ""; // 개인식별번호 저장용
		String sUserId = ""; // 세션정보의 유저ID
		// 기존 개인식별번호 확인
		List<Map<String, String>> paramList = paramGroup.getAllRowList();
		if (paramList.size() <= 0) {
			throw new AppWorksException("저장할 대상자 식별번호자료가 없습니다.", Alert.ERROR);
		}

		// 세션정보 가져오기
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		// 개인식별번호 체크
		for (Map<String, String> rowMap : paramList) {
			// 개인식별번호
			String sIndvIdntfcNo = String.valueOf(rowMap.get("INDV_IDNTFC_NO"));

//			// 개인식별번호가 있을때
			if (!sIndvIdntfcNo.isEmpty()) {
				// 개인식별번호 변수 변경 sIndvIdntfcNoUpd
				sIndvIdntfcNoUpd = sIndvIdntfcNo;
				break;
			}
		}

		// 2002-09-01 추가 개인정보(SCA300)컬럼명으로 변경
		Map<String, String> paramMap = dmParamGroup.getSingleValueMap();
		String sTrprBrthYmd = String.valueOf(paramMap.get("TRPR_BRTH_YMD"));
		String sTrprTelNo = String.valueOf(paramMap.get("TRPR_TELNO"));

		dmParamGroup.addColumn("BRTH_YMD", sTrprBrthYmd);
		dmParamGroup.addColumn("WRD_TELNO", sTrprTelNo);
		dataRequest.putParameterGroup(dmParamGroup);

		// 식별대상 대상자번호를 개인정보(SCA300)에 insert
		// 개인식별번호가 없는경우( 개인식별번호를 채번한다 )
		if (paramList.size() > 0 && sIndvIdntfcNoUpd.isEmpty()) {
			// 개인식별번호 채번
			sIndvIdntfcNoUpd = personalInfoService.savePersonalInfo(request, dataRequest);
		}

		int iUpdCnt = 0;
		List<Map<String, String>> insertList = new ArrayList<>();
		for (int idx = 0; idx < paramList.size(); idx++) {
			Map<String, String> getMap = new HashMap<>();

			String sIndvIdntfcNoChk = String.valueOf(paramList.get(idx).get("INDV_IDNTFC_NO")); // 개인식별번호
			String sTrprInfoNo      = String.valueOf(paramList.get(idx).get("TRPR_INFO_NO")); // 대상자번호

			// 최종수정자아이디 SESS_USER_ID, 개인식별번호 INDV_IDNTFC_NO
			// getMap.put("INDV_IDNTFC_NO", sIndvIdntfcNoChk);
			getMap.put("TRPR_INFO_NO", sTrprInfoNo);
			getMap.put("SESS_USER_ID", sUserId);

			// 개인식별번호가 없는경우 채번한 개인식별번호 or 기존 리스트에 있는 개인식별번호로 update
			// 개인식별번호가 있어도 update로 수정(2023.08.17)
			//if (sIndvIdntfcNoChk.isEmpty()) {
				getMap.put("INDV_IDNTFC_NO", sIndvIdntfcNoUpd);
				iUpdCnt = trprInqMapper.updateIndvIdntfcNo(getMap);

				if (iUpdCnt > 0) {

					// 수정일시 MDFCN_DT
					// 데이터변경구분코드 DATAA_CHG_SE_CD
					getMap.put("MDFCN_DT", DateUtil.getToday());
					getMap.put("DATAA_CHG_SE_CD", "U");

					paramList.get(idx).put("DATAA_CHG_SE_CD", "U");
					paramList.get(idx).put("INDV_IDNTFC_NO" , sIndvIdntfcNoUpd);
					paramList.get(idx).put("LAST_MDFR_ID", sUserId);

					// 이력테이블 저장 List add
					insertList.add(paramList.get(idx));
				}
			//}
		}
		LOGGER.debug("insertList.SIZE()=[" + insertList.size());
		if (!insertList.isEmpty()) {
			// 대상자 이력테이블 SEA201 insert
			trprInqMapper.insertTrprInqHistory2(insertList);
		}
		retMap.put("INDV_IDNTFC_NO", sIndvIdntfcNoUpd); // 개인식별번호 return

		return retMap;
	}

	/**
	 * @Method명 : processIndvIdntfcDel
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 8. 17. 
	 * @Method설명 : 개인식별 해제
	 */
	@Override
	public void processIndvIdntfcDel(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsTrprInfoNoList");
		List<Map<String, String>> paramList = paramGroup.getAllRowList();
		for(Map<String, String> map : paramList) {
			// SEA200 개인식별번호 공백('')으로 update
			idntfcTrprMapper.updateIdntfc(map);
			// SEA201 이력테이블에 insert
			idntfcTrprMapper.insertIdntfcHis(map);
		}
	}
}
