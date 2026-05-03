/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.mapper.TrlInspRsvtMngMapper;
import isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.service.TrlInspRsvtMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;

/**
 * @파일명        : TrlInspRsvtMngServiceImpl.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 7. 6.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 7. 6.
 * @수정내용      :
 * -
 * -
 */

@Service("trlInspRsvtMngService")
public class TrlInspRsvtMngServiceImpl extends IsryBaseServiceImpl implements TrlInspRsvtMngService {

	@Resource(name = "trlInspRsvtMngMapper")
	private TrlInspRsvtMngMapper trlInspRsvtMngMapper;

	@Resource(name="renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	// 테스트용 로그인 사용자 아이디
	//private String testUserId = "SUBMS01";


	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 6.
	 * @Method설명 :
	 */
	@Override
	public String selectTaskwkSeCd(Map<String, Object> requestMap) throws Exception {
		// TODO Auto-generated method stub
		return trlInspRsvtMngMapper.selectTaskwkSeCd(requestMap);
	}




	/**
	 * @Method명   : trlInspRsvtMngDetail
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 11.
	 * @Method설명 :
	 */
	@Override
	public void trlInspRsvtMngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		// 화면 상태코드(s:상세, i:등록, u:수정, d:삭제)
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");

		Iterator<ParameterRow> insertedDsList = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedDsList  = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedDsList  = dsList.getDeletedRows();


		String sWprkSqn      = "";	// 채번번호
		String sUserId		= "";	// 사용자 아이디

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);

			loginMap.put("USER_ID", loginVO.getId());

			sUserId = loginVO.getId();

		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}


		// 사용자 정보 > 기관정보 추출
		Map<String, String> userInfoMap = trlInspRsvtMngMapper.selectUserInfo(loginMap);


		while (insertedDsList.hasNext()) {

			Map<String, String> mapIns = insertedDsList.next().toMap();


			// 업무구분(해당업무 진입 메뉴 구분) > 변경될 수 있음. 정책 최종 확인 필요.
	        // 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04)
			Integer authMenuNo = 0;
			authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

			// 초기 집단연계지원관리번호 채번
			Map<String, String> seqMap = new HashMap<>();
			Map<String, Object> valMap = new HashMap<>();

			seqMap.put("USER_ID",       sUserId);
			seqMap.put("RENU_NO_SE_CD", "TI");					// 초기 집단연계지원관리번호 채번코드
			seqMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자

			// 채번서비스 호출
			valMap	= renuNoMapper.selectCaseMngNoRenu(seqMap);
			sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 초기 집단연계지원관리번호 채번 발번
			log.debug("TrlInspRsvtMngServiceImpl.trlInspRsvtMngDetail.sWprkSqn=[" + sWprkSqn + "]");


			// 심리검사예약관리 기본 정보 등록 > 일부 쿼리 처리
			mapIns.put("TRL_INSP_RSVT_MNG_NO",   sWprkSqn);															// 심리검사예약관리번호
			mapIns.put("MENU_NO", Integer.toString(authMenuNo));             										// TASKWK_SYS_SE_CD 업무시스템구분코드 	> 쿼리처리
			mapIns.put("INST_NO",   userInfoMap.get("INST_NO"));													// 기관번호
			mapIns.put("USER_ID",   sUserId);																		// 사용자 아이디
			mapIns.put("CLR_NM_ENCPT",   mapIns.get("CLR_NM"));								    // 접수자(상담사)명암호화
			mapIns.put("RSVCTM_NM_ENCPT",   mapIns.get("RSVCTM_NM"));								// 예약자명암호화
			mapIns.put("TELNO_ENCPT",   mapIns.get("TELNO"));										// 전화번호암호화
			mapIns.put("MBL_TELNO_ENCPT",   mapIns.get("MBL_TELNO"));								// 휴대전화번호암호화
			mapIns.put("FRST_RGTR_ID",   sUserId);																	// 최초등록자아이디
			mapIns.put("LAST_MDFR_ID",   sUserId);																	// 최종수정자아이디


			trlInspRsvtMngMapper.insertTrlInspRsvtMngDetail(mapIns);
		}

		while (updatedDsList.hasNext()) {
			Map<String, String> mapUpd = updatedDsList.next().toMap();

			log.debug("메인 updatedDsList TRL_INSP_RSVT_MNG_NO=[" + mapUpd.get("TRL_INSP_RSVT_MNG_NO") + "]");

			mapUpd.put("CLR_NM_ENCPT",   mapUpd.get("CLR_NM"));								    // 접수자(상담사)명암호화
			mapUpd.put("RSVCTM_NM_ENCPT",   mapUpd.get("RSVCTM_NM"));								// 예약자명암호화
			mapUpd.put("TELNO_ENCPT",   mapUpd.get("TELNO"));										// 전화번호암호화
			mapUpd.put("MBL_TELNO_ENCPT",   mapUpd.get("MBL_TELNO"));								// 휴대전화번호암호화

			mapUpd.put("LAST_MDFR_ID",   sUserId);																	// 최종수정자아이디



			trlInspRsvtMngMapper.updateTrlInspRsvtMngDetail(mapUpd);

		}


		while (deletedDsList.hasNext()) {
			Map<String, String> mapDel = deletedDsList.next().toMap();

			log.debug("메인 deletedDsList TRL_INSP_RSVT_MNG_NO=[" + mapDel.get("TRL_INSP_RSVT_MNG_NO") + "]");

			mapDel.put("LAST_MDFR_ID",   sUserId);
			trlInspRsvtMngMapper.deleteTrlInspRsvtMngDetail(mapDel);

		}



		Map<String, Object> mapParam = new HashMap<String, Object>();


		String subViewType       = "";	// 화면 상태코드(s:상세, i:등록, u:수정, d:삭제)

		subViewType = dmDetailParam.getValue("VIEW_TYPE");


		mapParam.put("TRL_INSP_RSVT_MNG_NO", dmDetailParam.getValue("TRL_INSP_RSVT_MNG_NO")); // 심리검사예약관리번호


		// 심리검사예약관리 심리검사 선택 항목 등록
		ParameterGroup paramDsTrlInspList = dataRequest.getParameterGroup("dsTrlInspList");

		Iterator<ParameterRow> insertedDsTrlInspList = paramDsTrlInspList.getInsertedRows();



		if (subViewType.equals("u") || subViewType.equals("U")) {

			// 수정일 경우 이전 등록되어있던 심리검사 선택 항목 모두 삭제 후 재등록
			trlInspRsvtMngMapper.deleteChcTrlInsp(mapParam);
		}


		while (insertedDsTrlInspList.hasNext()) {


			Map<String, String> mapIns = insertedDsTrlInspList.next().toMap();

			// 심리검사 선택 항목 리스트
			if(subViewType.equals("i") || subViewType.equals("I")) {
				// 등록일 경우 채번한 심리검사예약관리번호 셋팅
				// 수정인 경우는 dsTrlInspList 값으로 처리됨.
				mapIns.put("TRL_INSP_RSVT_MNG_NO",   sWprkSqn);															// 심리검사예약관리번호
			}


			mapIns.put("FRST_RGTR_ID",   sUserId);																	// 최초등록자아이디
			mapIns.put("LAST_MDFR_ID",   sUserId);																	// 최종수정자아이디

			log.debug("서브 insertedDsTrlInspList index=[" + mapIns.get("INDEX_SN") + "]");
			log.debug("서브 insertedDsTrlInspList 대분류=[" + mapIns.get("CMMNS_TRL_INSP_LCLAS_SE_CD") + "]");
			log.debug("서브 insertedDsTrlInspList 소분류=[" + mapIns.get("CMMNS_TRL_INSP_SCLAS_SE_CD") + "]");


			trlInspRsvtMngMapper.insertChcTrlInsp(mapIns);
		}




	}




	/**
	 * @Method명   : getTrlInspRsvtMngListTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13.
	 * @Method설명 :
	 */
	@Override
	public int getTrlInspRsvtMngListTotalCount(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return trlInspRsvtMngMapper.getTrlInspRsvtMngListTotalCount(mapParam);
	}




	/**
	 * @Method명   : selectTrlInspRsvtMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTrlInspRsvtMngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub


		// 심리검사 예약 관리
		List<Map<String, Object>> mapList = trlInspRsvtMngMapper.selectTrlInspRsvtMngList(mapParam);

		for (Map<String, Object> map : mapList) {

			// 화면용 예약자, 상담사(사용자) 이름 암복호화 추가
			map.put("RSVCTM_NM", map.get("RSVCTM_NM_ENCPT").toString());
			map.put("CLR_NM", map.get("CLR_NM_ENCPT").toString());


		}

		return mapList;
	}




	/**
	 * @Method명   : selectTrlInspRsvtMngDetail
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 14.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTrlInspRsvtMngDetail(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub


		String sUserId		= "";	// 사용자 아이디

		// 세션정보 가져오기
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);

			loginMap.put("USER_ID", loginVO.getId());

			sUserId = loginVO.getId();

		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}


		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 상세 데이터  조회조건
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");

		mapParam.put("TRL_INSP_RSVT_MNG_NO", dmDetailParam.getValue("TRL_INSP_RSVT_MNG_NO")); // 심리검사예약관리번호
		mapParam.put("USER_ID", sUserId); // 사용자 아이디


		// 심리검사 예약 관리
		List<Map<String, Object>> mapList = trlInspRsvtMngMapper.selectTrlInspRsvtMngDetail(mapParam);

		for (Map<String, Object> map : mapList) {

			// 화면용 예약자, 상담사(사용자) 이름 암복호화 추가
			map.put("RSVCTM_NM", map.get("RSVCTM_NM_ENCPT").toString());
			map.put("CLR_NM", map.get("CLR_NM_ENCPT").toString());

			map.put("TELNO", map.get("TELNO_ENCPT").toString());
			map.put("MBL_TELNO", map.get("MBL_TELNO_ENCPT").toString());


		}

		return mapList;

	}




	/**
	 * @Method명   : selectChcTrlInsp
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 14.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectChcTrlInsp(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 상세 데이터  조회조건
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");

		mapParam.put("TRL_INSP_RSVT_MNG_NO", dmDetailParam.getValue("TRL_INSP_RSVT_MNG_NO")); // 심리검사예약관리번호


		// 심리검사 예약 관리 > 심리검사 대분류, 소분류 선택 그룹 리스트
		List<Map<String, Object>> mapList = trlInspRsvtMngMapper.selectChcTrlInsp(mapParam);

		return mapList;
	}




	/**
	 * @Method명   : getSelectDateTrlInspRsvtMngListCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15.
	 * @Method설명 :
	 */
	@Override
	public int getSelectDateTrlInspRsvtMngListCount(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}




	/**
	 * @Method명   : selectDateTrlInspRsvtMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDateTrlInspRsvtMngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	/**
	 * @Method명   : getTrlInspRsvtMngDailyListTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 19.
	 * @Method설명 :
	 */
	@Override
	public int getTrlInspRsvtMngDailyListTotalCount(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return trlInspRsvtMngMapper.getTrlInspRsvtMngDailyListTotalCount(mapParam);
	}




	/**
	 * @Method명   : selectTrlInspRsvtMngDailyList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 19.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTrlInspRsvtMngDailyList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub

		// 심리검사 예약 관리
		List<Map<String, Object>> mapList = trlInspRsvtMngMapper.selectTrlInspRsvtMngDailyList(mapParam);

		for (Map<String, Object> map : mapList) {

			// 화면용 예약자, 상담사(사용자) 이름 암복호화 추가
			map.put("RSVCTM_NM", map.get("RSVCTM_NM_ENCPT").toString());
			map.put("CLR_NM", map.get("CLR_NM_ENCPT").toString());

			map.put("TELNO", map.get("TELNO_ENCPT").toString());
			map.put("MBL_TELNO", map.get("MBL_TELNO_ENCPT").toString());

		}

		return mapList;
	}



}
