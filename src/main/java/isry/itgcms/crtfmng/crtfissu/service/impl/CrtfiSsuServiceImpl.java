/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.crtfmng.crtfissu.service.impl;

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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcms.crtfmng.crtfissu.mapper.CrtfiSsuMapper;
import isry.itgcms.crtfmng.crtfissu.service.CrtfiSsuService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.Masking;

/**
 * @파일명 : CrtfiSsuServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2022. 8. 12.
 * @수정자 : Song.Young.Il
 * @수정일 : 2022. 8. 12.
 * @수정내용 : - -
 */
@Service("crtfiSsuService")
public class CrtfiSsuServiceImpl implements CrtfiSsuService {

	private final Logger LOGGER = LoggerFactory.getLogger(CrtfiSsuServiceImpl.class);

	// 채번
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name = "crtfiSsuMapper")
	private CrtfiSsuMapper crtfiSsuMapper;

	@Resource(name = "caseRegMapper")
	private CaseRegMapper caseRegMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @Method명 : selectCrtfiNo
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 16.
	 * @Method설명 : 발급번호채번
	 */
	@Override
	public Map<String, Object> selectCrtfiNo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String sUserId = loginVO.getId();

		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();

		seqMap.put("USER_ID", sUserId);
		seqMap.put("RENU_NO_SE_CD", "CI");
		seqMap.put("RENU_YMD", DateUtil.getToday());

		// 채번서비스 호출
		valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
		String sRenuNo = String.valueOf(valMap.get("RENU_NO")); // 발번
		// 채번완료

		returnMap.put("CRTF_ISSU_MNG_NO", sRenuNo);

		return returnMap;
	}

	/**
	 * @Method명 : selectCrtfiToReg
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 17.
	 * @Method설명 : 증명서 조회(등록시)
	 */
	@Override
	public List<Map<String, Object>> selectCrtfiToReg(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();
		ParameterGroup dsDtlList = dataRequest.getParameterGroup("dsDetailInfo");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Integer sInstNo = loginVO.getInstNo();

		Map<String, Object> instMap = new HashMap<>();

		instMap.put("INST_NO", sInstNo);
		instMap.put("UNT_TASKWK_SE_CD", CommUtils.getUntTaskwk(userLoginService.getLoginSessionVO(request)));
		instMap.put("TRPR_INFO_NO", dsDtlList.getValue(0, "TRPR_INFO_NO"));
		instMap.put("CASE_MNG_NO", dsDtlList.getValue(0, "CASE_MNG_NO"));
		instMap.put("CASE_MNG_ODRNO", dsDtlList.getValue(0, "CASE_MNG_ODRNO"));
		instMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		// 종사자번호
		if (dsDtlList.hasColumn("MEMBER_NO")) {
			instMap.put("ENFSN_NO", dsDtlList.getValue(0, "MEMBER_NO"));
		}

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmRenu");
		String sType = paramGroup.getValue("CRTF_SE_CD");

		if (instMap.get("TRPR_INFO_NO") != null || instMap.get("TRPR_INFO_NO") != "") {
			switch (sType) {
			case "01": // 서비스이용확인서(기간별) 등록
				returnMap = crtfiSsuMapper.selectsrvcCrtf(instMap);
				break;

			case "02": // 서비스이용상세확인서(건별) 등록
				returnMap = crtfiSsuMapper.selectsrvcDtlCrtf(instMap);
				break;

			case "03": // 입퇴소확인서 등록

				returnMap = crtfiSsuMapper.selectlvngCrtf(instMap);
				dataRequest.setResponse("dsAsisSysHstrList", selectSEB900List(request, dataRequest));
				break;

			case "04": // 청소년쉼터 입소기간 확인서(임대주택 신청용) 등록
				returnMap = crtfiSsuMapper.selectrthousCrtf(instMap);
				for (Map<String, Object> map : returnMap) {
					map.put("TRPR_TELNO", CommUtils.decToPhoneFormat(String.valueOf(map.get("MBL_TELNO_ENCPT"))));
					map.put("RRNO_ENCPT", CommUtils.decToMaskRrno(String.valueOf(map.get("RRNO_ENCPT"))));
				}
				dataRequest.setResponse("dsAsisSysHstrList", selectSEB900List(request, dataRequest));
				break;

			case "05": // 청소년쉼터 입소기간 확인서(자립지원수당 신청용) 등록

				List<Map<String, String>> result = crtfiSsuMapper.selectLastTrmnYmd(instMap);
				instMap.put("LASTA_TRMN_YMD", (String) result.get(0).get("LASTA_TRMN_YMD"));

				// 3년이내 2년이상 6개월연속
				List<Map<String, String>> selectcTtnutnEntrnc = crtfiSsuMapper.selectcTtnutnEntrnc(instMap);
				Integer succYear = 0;
				Integer succMonth = 0;
				Integer succDay = 0;
				Integer sumYear = 0;
				Integer sumMonth = 0;
				Integer sumDay = 0;
				String succOneYear = "N";

				for (Map<String, String> map : selectcTtnutnEntrnc) {

					String succYn = map.get("SUCC_YN");
					if (StringUtils.equals(succYn, "N")) {
						succMonth = 0;
						succDay = 0;
					}

					if (map.get("MONTH") != null) {
						sumMonth += Integer.valueOf(String.valueOf(map.get("MONTH")));
						succMonth += Integer.valueOf(String.valueOf(map.get("MONTH")));
					}

					if (map.get("DAY") != null) {
						sumDay += Integer.valueOf(String.valueOf(map.get("DAY")));
						succDay += Integer.valueOf(String.valueOf(map.get("DAY")));
					}
				}

				if (sumDay != 0) {
					sumMonth += sumDay / 30;
					sumDay = sumDay % 30;
				}

				if (sumMonth != 0) {
					sumYear += sumMonth / 12;
					sumMonth = sumMonth % 12;
				}

				if (succDay != 0) {
					succMonth += succDay / 30;
					succDay = succDay % 30;
				}

				if (succMonth != 0) {
					succYear += succMonth / 12;
				}

//				if (succYear >= 1 && sumYear >= 2)
//					succOneYear = "Y";

				// 2년이상, 6개월
				if (succMonth >= 6 && sumYear >= 2)
					succOneYear = "Y";

				returnMap = crtfiSsuMapper.selectpensnCrtf(instMap);
				for (Map<String, Object> map : returnMap) {
					map.put("TRPR_TELNO", CommUtils.decToPhoneFormat(String.valueOf(map.get("MBL_TELNO_ENCPT"))));
					map.put("RRNO_ENCPT", CommUtils.decToMaskRrno(String.valueOf(map.get("RRNO_ENCPT"))));

					map.put("RECENT_PRD_YR", sumYear);
					map.put("RECENT_PRD_MM", sumMonth);
					map.put("RECENT_PRD_DD", sumDay);
					map.put("CTNUTN_ENTRNC_YN", succOneYear);
				}

				dataRequest.setResponse("dsAsisSysHstrList", selectSEB900List(request, dataRequest));
				break;

			case "06": // 입교대상자 선정 통지서 등록
				returnMap = crtfiSsuMapper.selectslctnCrtf(instMap);
				break;

			case "07": // 입교․수료 확인서 등록
				if (loginVO.getUntTaskwk().equals("U07") || loginVO.getUntTaskwk().equals("U08")) {
					returnMap = crtfiSsuMapper.selectfnshCrtf2(instMap);
				} else {
					returnMap = crtfiSsuMapper.selectfnshCrtf(instMap);
				}
				break;

			case "08": // 교육 참가자 출석 확인서 등록
				// TODO 컨트롤러에서 info 조회 안하고 2붙은것만 조회하는거 둘다 조회하도록 수정
				returnMap = crtfiSsuMapper.selectatendCrft(instMap);
				List<Map<String, Object>> atend = selectatendCrft2(request, dataRequest);
				dataRequest.setResponse("dsAtendSittn", atend);
				break;

			case "13": // 교육 참가자 출석 확인서(건별) 등록
				returnMap = crtfiSsuMapper.selectatendDtlCrft(instMap);
				break;

			case "09": // 종사자 상담경력 확인서 등록
				// TODO 종사자 번호 지우고 종사자는 대상자에서 성명으로 검색할 수 있게 변경해야함.
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("INST_NO", loginVO.getInstNo());
				reqMap.put("ENFSN_NO", instMap.get("ENFSN_NO"));
				returnMap = crtfiSsuMapper.selectcareerCrtf(reqMap);
				break;

			case "10": // 청소년쉼터 자원봉사활동 확인서 등록

				break;

			case "11": // 학업중단 숙려제 실시 결과서 등록
				returnMap = crtfiSsuMapper.selectcrdlsCrtf(instMap);
				break;

//			case "17": // 개별화(사례)관리 계획 등록
//				returnMap = crtfiSsuMapper.selectCasemngPlan(instMap);
//				break;
//
//			case "18": // 개별화(사례)관리 종결보고서 등록
//				returnMap = crtfiSsuMapper.selectCasemngTrmn(instMap);
//				break;
//
//			case "19": // 심리평가 결과 보고서 등록
//				returnMap = crtfiSsuMapper.selectTrlEvlReprts(instMap);
//				break;
//
//			case "20": // 문제행동 발생보고서 등록
//				returnMap = crtfiSsuMapper.selectProbmGhvr(instMap);
//				break;

			case "12": // 청소년 1388 상담확인서 등록
				returnMap = crtfiSsuMapper.selectdscsnCrtf(instMap);
				break;
			default:
				break;
			}
		}

		for (Map<String, Object> map : returnMap) {
			map.put("RPRS_TELNO", Formatter.phoneFormat(String.valueOf(map.get("RPRS_TELNO")), 1));
		}

		return returnMap;
	}

	/**
	 * @Method명 : selectatendCrft2
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 3.
	 * @Method설명 : 교육 참가자 출석 확인서 등록조회 - 출석상황
	 */
	public List<Map<String, Object>> selectatendCrft2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dsDtlList = dataRequest.getParameterGroup("dsDetailInfo");
		ParameterGroup dmRenu = dataRequest.getParameterGroup("dmRenu");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String type = dmRenu.getValue("CRTF_SE_CD");

		if (type.equals("08")) {

			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

			Integer sInstNo = loginVO.getInstNo();

			Map<String, Object> instMap = new HashMap<>();

			instMap.put("INST_NO", sInstNo);
			instMap.put("TRPR_INFO_NO", dsDtlList.getValue(0, "TRPR_INFO_NO"));
			instMap.put("CRTF_ISSU_MNG_NO", dmRenu.getValue("CRTF_ISSU_MNG_NO"));

			result = crtfiSsuMapper.selectatendCrft2(instMap);
		}

		return result;
	};

	/**
	 * @Method명 : insertCrtfi
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 18.
	 * @Method설명 : 증명서 저장
	 */
	@Override
	public void insertCrtfi(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> insertRow = dsList.getAllRowList();

		if (insertRow.size() == 0) {
			insertRow.clear();

			ParameterGroup dsAtendSittn = dataRequest.getParameterGroup("dsAtendSittn");
			insertRow = dsAtendSittn.getInsertedRowList();
		}

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		String sUserId = loginVo.getId();

		int cnt = 0;

		for (Map<String, String> map : insertRow) {

			map.put("FRST_RGTR_ID", sUserId);
			map.put("LAST_MDFR_ID", sUserId);

			if (cnt == 0) {
				crtfiSsuMapper.insertCrtfi(map);
				cnt++;
			}

			crtfiSsuMapper.insertCrtfiDtl(map);

		}

	}

	/**
	 * @Method명 : updateCrtfi
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 1. 2.
	 * @Method설명 :
	 */
	@Override
	public void updateCrtfi(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dsDetail = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmRenu = dataRequest.getParameterGroup("dmRenu");

		List<Map<String, String>> updateRow = dsDetail.getUpdatedRowList();
		List<Map<String, String>> insertRow = dsDetail.getInsertedRowList();
		List<Map<String, String>> deleteRow = dsDetail.getDeletedRowList();

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		String sUserId = loginVo.getId();

		for (Map<String, String> map : updateRow) {
			map.put("CRTF_ISSU_MNG_NO", dmRenu.getValue("CRTF_ISSU_MNG_NO"));
			map.put("CRTF_SE_CD", dmRenu.getValue("CRTF_SE_CD"));
			map.put("USE_FSBTY_NM", map.get("USE_FSBTY_NM"));
			map.put("RCPDSK_NM", map.get("RCPDSK_NM"));
			map.put("LAST_MDFR_ID", sUserId);

			crtfiSsuMapper.updateCrtfi(map);
		}

		if (dmRenu.getValue("CRTF_SE_CD").equals("03") || dmRenu.getValue("CRTF_SE_CD").equals("04")) {
			for (Map<String, String> map : insertRow) {

				map.put("CRTF_ISSU_MNG_NO", dmRenu.getValue("CRTF_ISSU_MNG_NO"));
				map.put("CASE_MNG_NO", map.get("CASE_MNG_NO"));
				map.put("SHELTR_NM", map.get("SHELTR_NM"));
				map.put("CASE_BGNG_YMD", map.get("CASE_BGNG_YMD"));
				map.put("CASE_TRMN_YMD", map.get("CASE_TRMN_YMD"));
				map.put("USER_ID", sUserId);

				crtfiSsuMapper.insertUpdatedLvngCrtfi(map);
			}

			for (Map<String, String> map : deleteRow) {
				crtfiSsuMapper.deleteLvngCrtf(map);
			}
		}

		if (dmRenu.getValue("CRTF_SE_CD").equals("05")) {
			for (Map<String, String> map : insertRow) {
				map.put("CRTF_ISSU_MNG_NO", dmRenu.getValue("CRTF_ISSU_MNG_NO"));
				map.put("INST_NM", map.get("PENSN_INST_NM"));
				map.put("CASE_BGNG_YMD", map.get("CASE_BGNG_YMD"));
				map.put("CASE_TRMN_YMD", map.get("CASE_TRMN_YMD"));
				map.put("USER_ID", sUserId);
				crtfiSsuMapper.insertUpdatedPensnCrtfi(map);
			}

			for (Map<String, String> map : deleteRow) {
				crtfiSsuMapper.deleteLvngCrtf(map);
			}
		}
	}

	/**
	 * @Method명 : selectCrtflssuList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 16..
	 * @Method설명 : 증명서발급 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCrtfssuList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		List<Map<String, Object>> result = new ArrayList<>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		if (dmSearch == null) {
			throw new AppWorksException("조회할 대상자가 없습니다", Alert.ERROR);
		}
		Map<String, String> paramMap = dmSearch.getSingleValueMap();

		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		paramMap.put("INST_NO", String.valueOf(loginVO.getInstNo()));

		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */

		// 대상자 목록 조회
		result = crtfiSsuMapper.selectCrtfssuList(paramMap2);

		return result;
	}

	/**
	 * @Method명 : selectListDtlSelected
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 증명서 리스트_상세조회
	 */
	@Override
	public List<Map<String, Object>> selectListDtlSelected(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ParameterGroup dmRenu = dataRequest.getParameterGroup("dmRenu");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> dmRenuMap = new HashMap<>();

		dmRenuMap.put("CRTF_ISSU_MNG_NO", dmRenu.getValue("CRTF_ISSU_MNG_NO"));
		dmRenuMap.put("CRTF_SE_CD", dmRenu.getValue("CRTF_SE_CD"));
		dmRenuMap.put("NM_DECPT", dmRenu.getValue("NM_DECPT"));

		String sCrtfSeCd = dmRenu.getValue("CRTF_SE_CD");
		if (dmRenuMap.get("CRTF_SE_CD") != null) {
			switch (sCrtfSeCd) {
			case "01": // 서비스이용확인서(기간별) 리스트_상세조회 O
				result = crtfiSsuMapper.selectListDtlsrvcCrtf(dmRenuMap);
				break;
			case "02": // 서비스이용확인서(건별) 리스트 상세조회 O
				result = crtfiSsuMapper.selectListDtlsrvcDtlCrtf(dmRenuMap);
				break;
			case "03": // 입퇴소 확인서 리스트 상세조회 O
				result = crtfiSsuMapper.selectListlvngCrtf(dmRenuMap);
//				result = crtfiSsuMapper.selectListProbmGhvrCrtf(dmRenuMap);
				break;
			case "04": // 청소년쉼터 입소기간 확인서(임대주택 신청용) O
				result = crtfiSsuMapper.selectListDtlRthouseCrtf(dmRenuMap);

				for (Map<String, Object> map : result) {
					map.put("TRPR_TELNO", Formatter.phoneFormat(String.valueOf(map.get("MBL_TELNO_ENCPT")), 1));
//					map.put("RRNO_ENCPT", Masking.rrnoMasking(String.valueOf(map.get("RRNO_ENCPT"))));
				}
				break;
			case "05": // 청소년 입소기간 확인서(자립지원수당 신청용) o
				result = crtfiSsuMapper.selectListDtlpensnCrtf(dmRenuMap);

				for (Map<String, Object> map : result) {
					map.put("TRPR_TELNO", Formatter.phoneFormat(String.valueOf(map.get("MBL_TELNO_ENCPT")), 1));
//					map.put("RRNO_ENCPT", Masking.rrnoMasking(String.valueOf(map.get("RRNO_ENCPT"))));
				}

				break;
			case "06": // 입교대상자 선정 통지서 O
				result = crtfiSsuMapper.selectListDtlslctnCrtf(dmRenuMap);
				break;
			case "07": // 입교, 수료 확인서 O
				if (loginVO.getUntTaskwk().equals("U07") || loginVO.getUntTaskwk().equals("U08")) {
					result = crtfiSsuMapper.selectListDtfnshCrtf2(dmRenuMap);
				} else {
					result = crtfiSsuMapper.selectListDtfnshCrtf(dmRenuMap);
				}
				break;
			case "08": // 교육 참가자 출석 확인서
				result = crtfiSsuMapper.selectListAtendCrft(dmRenuMap);
				break;
			case "13": // 교육 참가자 출석 확인서(건별)
				result = crtfiSsuMapper.selectDtlAtendCrft(dmRenuMap);
				break;
			case "09": // 종사자 상담경력 확인서 o
				result = crtfiSsuMapper.selectListDtlcareerCrtf(dmRenuMap);
				break;
			case "10": // 청소년쉼터 자원봉사활동 확인서

				break;
			case "11": // 학업중단 숙려제 실시 결과서 o
				result = crtfiSsuMapper.selectListDtlcrdlsCrtf(dmRenuMap);
				break;
//			case "17": // 개별화(사례)관리 계획O
//				result = crtfiSsuMapper.selectListlvngCrtf(dmRenuMap);
//				break;
//			case "18": // 개별화(사례)관리 종결보고서 O
//				result = crtfiSsuMapper.selectListlvngCrtf(dmRenuMap);
//				for (Map<String, Object> map : result) {
//					map.put("TRPR_TELNO", CommUtils.decToPhoneFormat(String.valueOf(map.get("MBL_TELNO_ENCPT"))));
//					map.put("RRNO_ENCPT", CommUtils.decToMaskRrno(String.valueOf(map.get("RRNO_ENCPT"))));
//				}
//				break;
//			case "19": // 심리평가 결과 보고서 O
//				result = crtfiSsuMapper.selectListlvngCrtf(dmRenuMap);
//				break;
//			case "20": // 문제행동 발생보고서 O
//				result = crtfiSsuMapper.selectListProbmGhvrCrtf(dmRenuMap);
//				break;
			case "12": // 청소년상담 1388확인서
				result = crtfiSsuMapper.selectListDtldscsnCrtf(dmRenuMap);
				break;
			default:
				break;
			}
		}

		for (Map<String, Object> map : result) {
			map.put("RPRS_TELNO", Formatter.phoneFormat(String.valueOf(map.get("RPRS_TELNO")), 1));
		}
		return result;

	}

	/**
	 * @Method명 : selectListDtl2Selected
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 증명서 리스트_상세조회
	 */
	@Override
	public List<Map<String, Object>> selectListDtl2Selected(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ParameterGroup dmRenu = dataRequest.getParameterGroup("dmRenu");

		Map<String, Object> dmRenuMap = new HashMap<>();

		dmRenuMap.put("CRTF_ISSU_MNG_NO", dmRenu.getValue("CRTF_ISSU_MNG_NO"));
		dmRenuMap.put("CRTF_SE_CD", dmRenu.getValue("CRTF_SE_CD"));
		dmRenuMap.put("NM_DECPT", dmRenu.getValue("NM_DECPT"));

		String sCrtfSeCd = dmRenu.getValue("CRTF_SE_CD");
		if (dmRenuMap.get("CRTF_SE_CD") != null) {
			switch (sCrtfSeCd) {
			case "01": // 서비스이용확인서(기간별) 리스트_상세조회 O
				result = crtfiSsuMapper.selectListDtlsrvcCrtf(dmRenuMap);
				break;
			case "02": // 서비스이용확인서(건별) 리스트 상세조회 O
				result = crtfiSsuMapper.selectListDtlsrvcDtlCrtf(dmRenuMap);
				break;
			case "03": // 입퇴소 확인서 리스트 상세조회 O
				result = crtfiSsuMapper.selectListlvngCrtf(dmRenuMap);
				break;
			case "04": // 청소년쉼터 입소기간 확인서(임대주택 신청용) O
				result = crtfiSsuMapper.selectListDtlRthouseCrtf(dmRenuMap);
				break;
			case "05": // 청소년 입소기간 확인서(자립지원수당 신청용) o
				result = crtfiSsuMapper.selectListDtlpensnCrtf(dmRenuMap);
				break;
			case "06": // 입교대상자 선정 통지서 O
				result = crtfiSsuMapper.selectListDtlslctnCrtf(dmRenuMap);
				break;
			case "07": // 입교, 수료 확인서 O
				result = crtfiSsuMapper.selectListDtfnshCrtf(dmRenuMap);
				break;
			case "08": // 교육 참가자 출석 확인서 등록
				result = crtfiSsuMapper.selectListAtendCrft(dmRenuMap);
				break;
			case "13": // 교육 참가자 출석 확인서(건별) 등록
				result = crtfiSsuMapper.selectDtlAtendCrft(dmRenuMap);
				break;
			case "09": // 종사자 상담경력 확인서 o
				result = crtfiSsuMapper.selectListDtlcareerCrtf(dmRenuMap);
				break;
			case "10": // 청소년쉼터 자원봉사활동 확인서

				break;
			case "11": // 학업중단 숙려제 실시 결과서 o
				result = crtfiSsuMapper.selectListDtlcrdlsCrtf(dmRenuMap);
				break;
			default:
				break;
			}
		}

		// 대상자 목록
		for (int i = 0; i < result.size(); i++) {
			// 대상자명
			String sTrprNmEncpt = String.valueOf(result.get(i).get("TRPR_NM_ENCPT"));
			result.get(i).put("NM_DECPT", sTrprNmEncpt);
		}
		return result;

	}

	/**
	 * @Method명 : selectListAtendCrft2
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 5.
	 * @Method설명 : 교육 참가자 출석 확인서 상세조회 - 출석상황
	 */
	@Override
	public List<Map<String, Object>> selectListAtendCrft2(DataRequest dataRequest) throws Exception {
		ParameterGroup dmRenu = dataRequest.getParameterGroup("dmRenu");
		Map<String, String> dmReNuMap = dmRenu.getAllRowList().get(0);

		return crtfiSsuMapper.selectListAtendCrft2(dmReNuMap);
	}

	/**
	 * @Method명 : insertCrtfOtpt
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 7.
	 * @Method설명 : 증명서출력이력저장
	 */
	@Override
	public void insertCrtfOtpt(HttpServletRequest request, Map<String, Object> param) throws Exception {

		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		String sUserId = loginVo.getId();

		Map<String, Object> map = new HashMap<>();

		map.put("FRST_RGTR_ID", sUserId);
		map.put("LAST_MDFR_ID", sUserId);
		map.put("CRTF_ISSU_MNG_NO", param.get("CRTF_ISSU_MNG_NO"));

		LOGGER.debug("파람파람파람 " + param.get("CRTF_ISSU_MNG_NO"));

		crtfiSsuMapper.insertCrtfOtpt(map);
	}

	/**
	 * @Method명 : selectCsemdPicList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 12. 23.
	 * @Method설명 : 디딤 사례계획 증명서 출력 시 생활동 담당자명 조회
	 */
	@Override
	public Map<String, String> selectCsemdPicList(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmRenu");
		Map<String, String> map = paramGroup.getSingleValueMap();
		Map<String, String> returnMap = crtfiSsuMapper.selectCsemdPicList(map);
		if (returnMap != null) {
			map.put("INDIVID_NM", returnMap.get("INDIVID_NM"));
			map.put("WEEKLY_NM", returnMap.get("WEEKLY_NM"));
			map.put("ANIGHT_NM", returnMap.get("ANIGHT_NM"));
			map.put("BNIGHT_NM", returnMap.get("BNIGHT_NM"));
		}

		return map;
	}

	/**
	 * @Method명 : selectOffcs
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 직인 관련 내용 조회
	 */
	@Override
	public void selectOffcs(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> map = paramGroup.getSingleValueMap();
		Map<String, String> returnMap = new HashMap<String, String>();

		if (map.containsKey("OFFCS_PIC_NO") && !map.get("OFFCS_PIC_NO").equals("") && !map.get("OFFCS_PIC_NO").isEmpty()
				&& map.get("OFFCS_PIC_NO") != null) { // 증명서 찍히는 서명의 담당자
			returnMap = crtfiSsuMapper.selectOffcsPic(map);

		} else { // 증명서 찍히는 직인의 기관
			returnMap = crtfiSsuMapper.selectOffcs(map);
		}

		dataRequest.setResponse("dmOffcs", returnMap);
	}

	/**
	 * @Method명 : selectSEB900List
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Myeong.Sang
	 * @작성일 : 2023. 3. 2.
	 * @Method설명 :
	 */
	private List<Map<String, Object>> selectSEB900List(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dsDetailInfo = dataRequest.getParameterGroup("dsDetailInfo");

		Map<String, String> paramMap = new HashMap<>();
//		paramMap.put("FLNM_ENCPT", scpDb.scpEncB64(dsDetailInfo.getValue(0, "TRPR_NM_ENCPT")));
		paramMap.put("FLNM_ENCPT", dsDetailInfo.getValue(0, "TRPR_NM_ENCPT"));
		paramMap.put("BRTH_YMD", dsDetailInfo.getValue(0, "TRPR_BRTH_YMD"));
		paramMap.put("SXDC_SE_CD", dsDetailInfo.getValue(0, "SXDC_SE_CD"));

		return caseRegMapper.selectSEB900List(paramMap);

	}

	/**
	 * @Method명 : selectWorker
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 6. 14.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorker(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String flnm = "";
		String mblTelno = "";
		String userId = "";
		String untTaskwkSeCd = "";
		String instNm = "";
		String wrdTelno = "";

		if (parameterGroup != null) {
			flnm = parameterGroup.getValue("FLNM_ENCPT");
			mblTelno = parameterGroup.getValue("MBL_TELNO_ENCPT");
			userId = parameterGroup.getValue("USER_ID");
			untTaskwkSeCd = parameterGroup.getValue("UNT_TASKWK_SE_CD");
			instNm = parameterGroup.getValue("INST_NM");
			wrdTelno = parameterGroup.getValue("WRD_TELNO");
		}

		Map<String, Object> map = new HashMap<>();

		if (flnm != null && !"".equals(flnm)) {
			map.put("flnm", flnm);
		}
		if (mblTelno != null && !"".equals(mblTelno)) {
			map.put("mblTelno", mblTelno);
		}
		if (userId != null && !"".equals(userId)) {
			map.put("userId", userId);
		}
		if (untTaskwkSeCd != null && !"".equals(untTaskwkSeCd)) {
			map.put("untTaskwkSeCd", untTaskwkSeCd);
		}
		if (instNm != null && !"".equals(instNm)) {
			map.put("instNm", instNm);
		}
		if (wrdTelno != null && !"".equals(wrdTelno)) {
			map.put("wrdTelno", wrdTelno);
		}
		map.put("INST_NO", loginVO.getUserInstNo());

		/*
		 * 2023-04-26 pre시스템문의사항 184번 * 담당자 검색에 전국 쉼터 종사자가 나타남 접속한 해당 종사자의 기관의 담당자만 나오도록
		 * 추가
		 */
		Integer menuNo = request.getParameter("_AUTH_MENU_NO") == null
				|| "".equals(request.getParameter("_AUTH_MENU_NO")) ? 0
						: Integer.valueOf(request.getParameter("_AUTH_MENU_NO"));
		String menuUrl = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		if (!"".equals(menuUrl)) {
			menuUrl = menuUrl.replace(".clx", "");
			menuUrl = menuUrl.substring(menuUrl.lastIndexOf("/"), menuUrl.length());
		}

		// log.debug("#### untTaskwkSeCd : " + map.get("untTaskwkSeCd"));

		map.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());

		List<Map<String, Object>> list = crtfiSsuMapper.selectWorker(map);
		List<Map<String, Object>> list2 = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {

			Map<String, Object> map1 = list.get(i);

			map1.put("FLNM_MASKING", Masking.nameMasking((String) map1.get("FLNM")));

			String mblTN = Formatter.phoneFormat((String) map1.get("MBL_TELNO"),1);
			map1.put("MBL_TELNO_MASKING", mblTN);

			map1.put("EML_ADDR_MASKING", Masking.emailMasking((String) map1.get("EML_ADDR")));
			map1.put("BRTH_YMD_MASKING", Masking.birthMasking((String) map1.get("BRTH_YMD")));

			// map1.put("MSNGR_ID_MASKING", map1.get("MSNGR_ID_ENCPT"));

			list2.add(map1);
		}

		return list2;
	}

}
