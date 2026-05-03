/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.casemng.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.csemd.casemng.mapper.CsemdCaseMngMapper;
import isry.csems.casemng.service.CsemsCaseMngService;
import isry.csems.mngrpage.aplcnttrprdtlinfomng.mapper.AplcntTrprDtlInfoMngMapper;
import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.syscmmn.survsht.service.SurvshtCmmnsInqService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

/**
 * @파일명 : CsemsCaseMngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seung.Yeon
 * @작성일 : 2022. 10. 4.
 * @수정자 : Lee.Seung.Yeon
 * @수정일 : 2022. 10. 4.
 * @수정내용 : - -
 */
@Service("csemsCaseMngService")
public class CsemsCaseMngServiceImpl extends IsryBaseServiceImpl implements CsemsCaseMngService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "caseRegService")
	private CaseRegService caseRegService;

	@Resource(name = "csemdCaseMngMapper")
	private CsemdCaseMngMapper csemdCaseMngMapper;

	@Resource(name = "csemsMngrPageAplcntTrprDtlInfoMngMapper")
	private AplcntTrprDtlInfoMngMapper csemsAplcntTrprDtlInfoMngMapper;

	// 설문지템플릿관리번호 사용여부
	@Resource(name = "survshtCmmnsInqService")
	private SurvshtCmmnsInqService survshtCmmnsInqService;

	// 설문지 관리번호 생성 Service Class
	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	Masking mask = new Masking();

	/**
	 * 사례관리_등록 상세정보 저장
	 * 
	 * @Method명 : saveCaseMngRegDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seung.Yeon
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveCaseMngRegDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		// 1.업무공통영역 저장
		LOGGER.debug("================= 업무공통영역 저장 START =================");
		Map<String, Object> info = caseRegService.processData(request, dataRequest);
		LOGGER.debug("================= 업무공통영역 저장 END =================");

		String sCaseMngNo = "";
		String sCaseMngOdrno = "";

		ParameterGroup dataParam = dataRequest.getParameterGroup("dsDrfstfTakngInfo");
//		List<Map<String, String>> dsDetailInfo = dataParam.getAllRowList();
//		if (dsDetailInfo.size() > 0) {
//			Map<String, String> mapDetail = dsDetailInfo.get(0);
//			sTrprInfoNo = mapDetail.get("TRPR_INFO_NO");
//		}

		if (sCaseMngNo.isEmpty())
			sCaseMngNo = String.valueOf(info.get("CASE_MNG_NO"));
		if (sCaseMngOdrno.isEmpty())
			sCaseMngOdrno = String.valueOf(info.get("CASE_MNG_ODRNO"));

		// 2.대상자문제상태내역(AFA120)
		LOGGER.debug("================= 대상자문제상태내역(AFA120) 저장 START =================");
		Iterator<ParameterRow> allRowList = dataParam.getAllRows();
		while (allRowList.hasNext()) {
			ParameterRow row = allRowList.next();
			Map<String, String> mapUpd = row.toMap();

			if (mapUpd.get("CASE_MNG_NO").equals("") || row.getState() == RowState.UPDATED) {
				mapUpd.put("CASE_MNG_NO", sCaseMngNo);
				mapUpd.put("CASE_MNG_ODRNO", sCaseMngOdrno);
				mapUpd.put("FRST_RGTR_ID", sUserId);
				mapUpd.put("LAST_MDFR_ID", sUserId);

				// 2.1. AFA120 수정
				csemsAplcntTrprDtlInfoMngMapper.updatePtcptReqstdAplcntPop(mapUpd);
				// 2.2. AFA121 등록
				csemsAplcntTrprDtlInfoMngMapper.insertPtcptReqstdAplcntPopHstr(mapUpd);
			}
		}

		LOGGER.debug("================= 대상자문제상태내역(AFA120) 저장 END =================");

		// 3.설문지발송이력(SBB600)
		LOGGER.debug("================= 설문지발송이력(SBB600) 수정 START =================");
		ParameterGroup detailInfo = dataRequest.getParameterGroup("dsDetailInfo");

		Map<String, String> mapAll = detailInfo.getSingleValueMap();
		List<Map<String, Object>> qustnbList = csemdCaseMngMapper.selectQustnbSndngHstr(mapAll);
		for (Map<String, Object> reqmap : qustnbList) {

			reqmap.put("CASE_MNG_NO", sCaseMngNo);
			reqmap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			reqmap.put("LAST_MDFR_ID", sUserId);

			csemdCaseMngMapper.updateSBB600(reqmap);
		}
		LOGGER.debug("================= 설문지발송이력(SBB600) 수정 END =================");

		Map<String, String> rtnMap = new HashMap<>();
		rtnMap.put("CASE_MNG_NO", sCaseMngNo);
		rtnMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);

		return rtnMap;
	}

	/**
	 * @Method명 : chkCreateQustnbMngNoYn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 12. 2.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> chkCreateQustnbMngNoYn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		// TODO Auto-generated method stub
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> map = dmSearch.getSingleValueMap();

		return csemsAplcntTrprDtlInfoMngMapper.chkCreateQustnbMngNoYn(map);
	}

	/**
	 * @Method명 : createQustnb
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 12. 2.
	 * @Method설명 :
	 */
	public void createQustnb(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {
		// TODO Auto-generated method stub

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");
		// ParameterGroup dmAplCntDtlParam =
		// dataRequest.getParameterGroup("dmAplCntDtl");

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsQustnbSndngList");
		List<Map<String, String>> insertRow = paramGroup.getAllRowList();

		Map<String, Object> userParam = new HashMap<String, Object>();
		userParam.put("USER_ID", dmSearchParam.getSingleValueMap().get("USER_ID")); // 사용자아이디
		userParam.put("INST_NO", dmSearchParam.getSingleValueMap().get("INST_NO")); // 기관번호
		// userParam.put("UNT_TASKWK_SE_CD",
		// dmSearchParam.getSingleValueMap().get("UNT_TASKWK_SE_CD")); // 단위업무구분코드
		userParam.put("TRPR_INFO_NO", dmSearchParam.getSingleValueMap().get("TRPR_INFO_NO")); // 대상자정보번호
		userParam.put("TRPR_NM", dmSearchParam.getSingleValueMap().get("TRPR_NM")); // 대상자명
		userParam.put("SXDC_SE_CD", dmSearchParam.getSingleValueMap().get("SXDC_SE_CD")); // 설문수신방법구분코드
		userParam.put("PRTCR_NM", dmSearchParam.getSingleValueMap().get("PRTCR_NM")); // 보호자명
		userParam.put("SRVY_RCPTN_MTHD_SE_CD", dmSearchParam.getSingleValueMap().get("SRVY_RCPTN_MTHD_SE_CD")); // 설문수신방법구분코드
		userParam.put("CASE_MNG_NO", dmSearchParam.getSingleValueMap().get("CASE_MNG_NO")); // 사례관리번호
		userParam.put("CASE_MNG_ODRNO", dmSearchParam.getSingleValueMap().get("CASE_MNG_ODRNO")); // 사례관리차수
		userParam.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getSingleValueMap().get("QUSTNB_TMPT_MNG_NO")); // 대상자관계구분코드

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getSingleValueMap().get("QUSTNB_TMPT_MNG_NO"));
		String untTaskwkSeCd = csemsAplcntTrprDtlInfoMngMapper.selectUntTaskwkSeCd(map2);
		userParam.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);

		// ### 로그인 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId(); // 아이디
		// String sWrdTelno = loginVO.getMblTelno(); // 휴대전화번호

		int srvyType = Integer.parseInt(dmSearchParam.getSingleValueMap().get("SRVY_TYPE").toString());

		// 1. 설문지템플릿관리번호(하드코딩)
		Map<String, Object> param = new HashMap<String, Object>();

		// 2. 전달 받은 복사된 설문지 관리번호
		Map<String, Object> result = resultMap;
		param = new HashMap<String, Object>();

		// ### SBB110 설문대상자
		param.put("QUSTNB_MNG_NO", result.get("QUSTNB_MNG_NO")); // 설문지관리번호
		param.put("QUSTNB_TMPT_MNG_NO", result.get("QUSTNB_TMPT_MNG_NO")); // 설문지템플릿관리번호

		log.debug("createQustnb > QUSTNB_MNG_NO = " + param.get("QUSTNB_MNG_NO"));
		log.debug("createQustnb > QUSTNB_TMPT_MNG_NO = " + param.get("QUSTNB_TMPT_MNG_NO"));

		param.put("CASE_MNG_NO", userParam.get("CASE_MNG_NO")); // 사례관리번호
		param.put("CASE_MNG_ODRNO", userParam.get("CASE_MNG_ODRNO")); // 사례관리차수

		param.put("TRPR_INFO_NO", userParam.get("TRPR_INFO_NO")); // 대상자정보번호
		// 신청접수일련번호

		// 사례대상자명암호화
		if (!userParam.get("TRPR_NM").equals(""))
			param.put("CASE_TRPR_NM_ENCPT", userParam.get("TRPR_NM").toString());

		param.put("ENFSN_NO", loginVO.getEnfsnNo()); // 종사자번호
		param.put("TRPR_REL_SE_CD", insertRow.get(srvyType).get("TRPR_REL_SE_CD")); // 대상자관계구분코드

		param.put("FRST_RGTR_ID", sUserId); // 최초등록자아이디
		param.put("LAST_MDFR_ID", sUserId); // 최종수정자아이디

		// 3. SBB110 설문대상자 insert
		csemsAplcntTrprDtlInfoMngMapper.insertQustnbTrprInfo(param);

		// ### SBB600 설문발송이력 param 추가
		param.put("INST_NO", userParam.get("INST_NO")); // 기관번호
		param.put("UNT_TASKWK_SE_CD", userParam.get("UNT_TASKWK_SE_CD")); // 단위업무구분코드
		param.put("SRVY_ERA_SE_CD", "02"); // 설문시기구분코드(사전:01, 사후:02)
		param.put("NO_INPT_SNDNG_YN", "N"); // 번호입력발송여부

		if (!insertRow.get(srvyType).get("RCPTN_NM").equals(""))
			param.put("SRVY_RSPDNT_NM_ENCPT", insertRow.get(srvyType).get("RCPTN_NM").toString()); // 설문응답자명암호화

		param.put("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT",
				insertRow.get(srvyType).get("RCPTN_MBL_TELNO").toString()); // 설문응답수신휴대전화번호암호화

		param.put("SRVY_RCPTN_MTHD_SE_CD", "1"); // 설문수신방법구분코드(PC:1, 메일:2, 문자:3)

		// 5. SBB600 설문발송이력 insert
		csemsAplcntTrprDtlInfoMngMapper.insertQustnbSndngHstr(param);

	}

	/**
	 * @Method명 : srvyWrtStts
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 12. 2.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> srvyWrtStts(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> map = dmSearch.getSingleValueMap();

		String untTaskwkSeCd = csemsAplcntTrprDtlInfoMngMapper.selectUntTaskwkSeCd(map);
		map.replace("UNT_TASKWK_SE_CD", untTaskwkSeCd);

		return csemsAplcntTrprDtlInfoMngMapper.srvyWrtStts(map);
	}

	/**
	 * @Method명 : chkQustnbTmptUseYn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 12. 9.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> chkQustnbTmptUseYn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearchParamGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearchParam = dmSearchParamGroup.getSingleValueMap();

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsQustnbSndngList");
		List<Map<String, String>> insertRow = paramGroup.getAllRowList();

		// 각 업무에 맞게 UI에선 전달된 dmSearch, dsQustnbSndngList 정보 기준 각 업무별로 로직 처리해야함.
		// case1 : 서로 다른 타입 리스트 > Ex. 청소년, 보호자, 담당자 서로 다른 타입 형태
		// case2 : 단일 타입 리스트 > Ex. 청소년 혹은 보호자 혹은 담당자 단일 형태
		// case3 : 단건 타입 리스트 > Ex. 청소년, 보호자, 담당자 등 버튼 클릭시 단건씩 형태

		// 각 업무단에 따라 단건식 요청하는 경우 사용, 다건인경우는 dmSearchParam으로 전달하지안고, for loop 0 ~ n 로
		// 처리하면됨.
		int srvyType = Integer.parseInt(dmSearchParam.get("SRVY_TYPE").toString());
		log.debug("createQustnb > srvyType = " + srvyType);

		Map<String, Object> message = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> searchMap = new HashMap<>();

		Map<String, String> map2 = new HashMap<>();

		// ### 1. 설문지템플릿 / SBB000 조회 (QUSTNB_TMPT_MNG_NO, UNT_TASKWK_SE_CD) > USE_YN = Y
		// 인건만 처리 가능 하게

		searchMap.put("QUSTNB_TMPT_MNG_NO", insertRow.get(srvyType).get("QUSTNB_TMPT_MNG_NO")); // 설문지템플릿관리번호
		map2.put("QUSTNB_TMPT_MNG_NO", insertRow.get(srvyType).get("QUSTNB_TMPT_MNG_NO"));

		String untTaskwkSeCd = csemsAplcntTrprDtlInfoMngMapper.selectUntTaskwkSeCd(map2);

		searchMap.put("UNT_TASKWK_SE_CD", untTaskwkSeCd); // 설문지템플릿관리번호

		resultMap = survshtCmmnsInqService.searchQustnbTmptUseYn(searchMap);

		String useYn = resultMap.get("USE_YN").toString();

		if (useYn.equals("N")) {
			log.debug("createQustnb > useYn = " + useYn);
			// 조회된 설문지템플릿관리번호가 미사용 상태 > 리턴 UI 알림처리
			message.put("USE_YN", "N");

		} else {

			resultMap = new HashMap<>();

			// 조회된 설문지관리번호가 사용중 상태 > 설문지관리번호 복사(생성)

			// 설문지템플릿관리번호로 설문지관리번호 복사시 재사용 여부(채번 생성 : Y , 재사용 : N) default:Y
			// 2022.12.07 이충수매니저님(강화영매니저님)과 협의 모든 설문지관리번호는 신규 생성으로
			// 단 추후 무조건 신규 생성이 아닌 이미 생성되어있는 설문지고나리번호 재사용 할 수 있음.
			// 재사용시 설문지템플릿관리번호로 설문지관리번호가 여러건이 생성되어있는 경우 가장 최근것으로 리턴
			searchMap.put("REUS_YN", dmSearchParam.get("REUS_YN")); // 설문지템플릿관리번호로 설문지관리번호 복사시 신규생성 혹은 재사용 여부

			// 설문지관리번호 복사시 재사용여부
			String reusYn = searchMap.get("REUS_YN").toString();

			if (reusYn.equals("N")) {

				// 신규생성
				resultMap = survshtMmnService.processSurvshtTmptData(request, dataRequest, searchMap);

			} else if (reusYn.equals("Y")) {

				// 설문지템플릿관리번호 이미 생성되어있는 설문지관리번호 조회
				// 만약 생성되어있는 설문지관리번호 조회시 다건인 경우 가장 최신(Max) 설문지관리번호 리턴

				// 각 업무단위로 조회조건이 달라질 수 있음.
				// Ex. case 1 : 설문지템플릿관리번호, 대상자번호, case 2 : 설문지템플릿관리번호, 사례관리번호, 사례관리차수, 대상자번호
				// 디딤 : U07, 드림: U08 AND 사례관리 ....
				// 재사용선택시 각 업무단위로 조회 처리 필요.
				// ex) resultMap = 각업무서비스.getQustnbMngNo(request, dataRequest, searchMap);
			}

			// 설문지 생성 정보 저장(insert or update)
			createQustnb(request, dataRequest, resultMap);
			// csemsCaseMngService.createQustnb(request, dataRequest, resultMap);

			message.put("USE_YN", "Y");

		}

		return message;
	}

}
