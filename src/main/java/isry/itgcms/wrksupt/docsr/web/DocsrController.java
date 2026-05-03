/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.wrksupt.docsr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
//import isry.itgcms.util.ScpDb;
//import isry.itgcms.util.StringUtil;
import isry.itgcms.wrksupt.docsr.service.DocsrService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;

/**
 * @파일명 : DocsrController.java
 * @프로그램 설명 : 문서수발신 조회 및 발송을 관리하는 Controller
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 20.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 20.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/itgcms/wrksupt/docsr")
public class DocsrController extends IsryBaseController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "docsrService")
	private DocsrService docsrService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	//ScpDb  scpDb   = new ScpDb();


	/**
	 * @Method명 : onInnerEmlSeCd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 내부메일함 공통코드 조회
	 */
	@RequestMapping(value="onInnerEmlSeCd.do")
	public View onInnerEmlSeCd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {


		String codeId = ""; // 공통코드 아이디
		String dataSetNm = ""; // 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> param = new HashMap<String, Object>();
		String userId = "";
		String instNo = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			instNo = loginVO.getInstNo().toString();
		}

		log.debug("loginVO.getUntTaskwk() === " + loginVO.getUntTaskwk());
		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			for (Map<String, String> rowMap : paramList) {

				dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); // 응답 데이터셋
				codeId = String.valueOf(rowMap.get("CMMNS_CD_ID")); // 요청 공통코드 아이디
				log.debug("selectAplCntInfoOnLoad.dataSetNm=[" + dataSetNm + "]");
				log.debug("selectAplCntInfoOnLoad.codeId=[" + codeId + "]");
				// 시스템 공통코드 조회 서비스 요청
				List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, loginVO.getUntTaskwk());
				//List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, "U09");
				// 응답객체 셋팅
				dataRequest.setResponse(dataSetNm, list);
			}
		}


		return new JSONDataView();
	}


	/**
	 * @Method명 : selectInqDocList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 발신함 목록 조회
	 */
	@RequestMapping(value = "/selectInqDocList.do")
	public View selectInqDocList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> param = new HashMap<String, Object>();
		String userId = "";
		String instNo = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			instNo = loginVO.getInstNo().toString();
		}



		Map<String, Object> dmSearchMap = new HashMap<>();

		dmSearchMap.put("DOCS_TYPE_CD", "P"); // 문서 발신함 구분 값 자바에서만 P: 발신, R: 수신, S: 내게쓴 문서함.

		Map<String, Object> result = docsrService.selectDocsCommonList(request, dataRequest, dmSearchMap);

		dataRequest.setResponse("dsList", result.get("totalList"));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectRecvInqDocList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @수정자 : Taesoo. Song
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수신함 목록 조회
	 */
	@RequestMapping(value = "/selectRecvInqDocList.do")
	public View selectRecvInqDocList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> dmSearchMap = new HashMap<>();

		dmSearchMap.put("DOCS_TYPE_CD", "R"); // 문서 발신함 구분 값 자바에서만 P: 발신, R: 수신, S: 내게쓴 문서함.

		Map<String, Object> result = docsrService.selectDocsCommonList(request, dataRequest, dmSearchMap);

		dataRequest.setResponse("dsList", result.get("totalList"));

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveInqDoc
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(CUD)
	 */
	@RequestMapping(value = "/saveInqDoc.do")
	public View saveInqDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		docsrService.saveInqDoc(dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value="onLoadInqDocInfo.do")
	public View onLoadInqDocInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String codeId = ""; // 공통코드 아이디
		String dataSetNm = ""; // 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> param = new HashMap<String, Object>();
		String userId = "";
		String instNo = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			instNo = loginVO.getInstNo().toString();
		}

		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			for (Map<String, String> rowMap : paramList) {

				dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); // 응답 데이터셋
				codeId = String.valueOf(rowMap.get("CMMNS_CD_ID")); // 요청 공통코드 아이디
				log.debug("selectAplCntInfoOnLoad.dataSetNm=[" + dataSetNm + "]");
				log.debug("selectAplCntInfoOnLoad.codeId=[" + codeId + "]");
				// 시스템 공통코드 조회 서비스 요청
				List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, loginVO.getUntTaskwk());
				//List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, "U09");
				// 응답객체 셋팅
				dataRequest.setResponse(dataSetNm, list);
			}
		}


		param.put("USER_ID", userId);
		param.put("INST_NO", instNo);
		Map<String, Object> result = docsrService.getUserInstInfo(param);
		dataRequest.setResponse("dmUsrInfo", result);

		// 사용안하는것 같음. SBA270에 insert, update 처리하는
		// excuteDocInstInfo.do > insertInstInfo / updateInstInfo 부분 호출 clx는
		// DocInstInfo.clx 이지만 현) 사용안하는것 같음. 일단 주석 처리함.
//		Map<String, Object> instInfo = docsrService.selectDocsInstInfo(param);
//		if (result != null) {
//			if (instInfo != null) {
//				result.put("EXIST_YN", "Y");
//			} else {
//				result.put("EXIST_YN", "N");
//			}
//			dataRequest.setResponse("dmUsrInfo", result);
//		} else {
//			Map<String, Object> result1 = new HashMap<String, Object>();
//			result1.put("INST_NM", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("OFFCS_ADR", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("HPGE_URL_ADDR", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("BPLC_ZIP", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("BPLC_PST_ADDR", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("BPLC_DADDR", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("RPRS_TELNO", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("RPRS_FXNO", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("BPLC_ADDR", "마이그레이션 되어야 정상 노출 됩니다.");
//			result1.put("EXIST_YN", "N");
//			dataRequest.setResponse("dmUsrInfo", result1);
//		}


		return new JSONDataView();
	}
	/**
	 * @Method명 : insertInqDoc
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	@RequestMapping(value = "/insertInqDoc.do")
	public View insertInqDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSaveMap = new HashMap<>();
		String instNo = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			dmSaveMap.put("SNDPTY_ID", loginVO.getId());
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			dmSaveMap.put("MENU_NO", authMenuNo);
			instNo = loginVO.getInstNo().toString();
//			if (!"////".equals(loginVO.getAgencyContacts())) {
//				instNo = loginVO.getAgencyContacts().split("/")[2];
//			}
//			String taskwkSeCd = loginVO.getUntTaskwkSeCd().replaceAll(",", "");
//			if (taskwkSeCd.length() > 3) {
//				taskwkSeCd = taskwkSeCd.substring(0, 3);
//			}
//			dmSaveMap.put("TASKWK_SE_CD", taskwkSeCd);
			// dmSaveMap.put("DSPTCH_INST_NO", loginVO.getOrgCode());
		}


		String untTaskwkSeCd = loginVO.getUntTaskwk().toString();
		if (!untTaskwkSeCd.equals("U15")) {
			dmSaveMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		} else {
			dmSaveMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwkSeCd());
		}


		dmSaveMap.put("ENFSN_NO", loginVO.getEnfsnNo());
		dmSaveMap.put("DSPTCH_INST_NO", instNo);
		dmSaveMap.put("IOBX_SE_CD", dmSave.getValue("IOBX_SE_CD"));
		dmSaveMap.put("DOC_INO_CN", dmSave.getValue("DOC_INO_CN"));
		dmSaveMap.put("RCVR_ID", dmSave.getValue("RCVR_ID"));
		dmSaveMap.put("INST_NO", dmSave.getValue("INST_NO"));
		dmSaveMap.put("INO_DOC_TTL_NM", dmSave.getValue("INO_DOC_TTL_NM"));
		dmSaveMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));
		dmSaveMap.put("ATFINO", dmSave.getValue("ATFINO"));

		log.debug("SNDPTY_ID = 발신자 아이디 = " + loginVO.getId());
		log.debug("ENFSN_NO = 발신자 종사자번호 = " + loginVO.getEnfsnNo());
		log.debug("DSPTCH_INST_NO instNo = 발신자 기관번호 = " + instNo);
		log.debug("DSPTCH_INST_NO loginVO.getInstNo() = 발신자 기관번호 = " + loginVO.getInstNo());
		log.debug("UNT_TASKWK_SE_CD = 발신자 단위업무구분코드 = " + loginVO.getUntTaskwkSeCd());

		/* 공문 타입 추가 된 영역 */
		// dmSaveMap.put("RCVR_NM_ENCPT", dmSave.getValue("RCVR_NM_ENCPT")); // 수신자명 사용안함
		dmSaveMap.put("RCPTN_INST_NO", dmSave.getValue("RCPTN_INST_NO")); // 수신기관번호
		dmSaveMap.put("THRGH_NM", dmSave.getValue("THRGH_NM")); // 경유
		dmSaveMap.put("APPA_NM", dmSave.getValue("APPA_NM")); // 결제라인명
		dmSaveMap.put("OFFCS_SGNNG_NO", dmSave.getValue("OFFCS_SGNNG_NO")); // 직인서명번호

		dmSaveMap.put("SPPRTR_NM_ENCPT", dmSave.getValue("SPPRTR_NM_ENCPT")); //협조자 암호화
//		if (dmSave.getValue("SPPRTR_NM_ENCPT") != null || !dmSave.getValue("SPPRTR_NM_ENCPT").equals("")) {
//			dmSaveMap.replace("PCHPRS_Y_PIC_NM", scpDb.scpDecB64(StringUtil.nullConvert(dmSave.getValue("SPPRTR_NM_ENCPT"))));//협조자 암호화
//		}

		dmSaveMap.put("ENFC_NM", dmSave.getValue("ENFC_NM")); // 시행명
		dmSaveMap.put("RCPT_NM", dmSave.getValue("RCPT_NM")); // 접수명
		// dmSaveMap.put("RLS_YN", dmSave.getValue("RLS_YN")); // 공개여부 사용안함

		/* 발신자 연락처 정보 관리 */
		dmSaveMap.put("BPLC_ADDR", dmSave.getValue("BPLC_ADDR"));
		dmSaveMap.put("RPRS_TELNO", dmSave.getValue("RPRS_TELNO"));
		dmSaveMap.put("RPRS_FXNO", dmSave.getValue("RPRS_FXNO"));


		docsrService.insertInqDoc(dmSaveMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updateInqDoc
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/updateInqDoc.do")
	public View updateInqDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmUpdateMap = new HashMap<>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			dmUpdateMap.put("LAST_MDFR_ID", loginVO.getId());
			dmUpdateMap.put("SNDPTY_ID", loginVO.getId());
//			String taskwkSeCd = loginVO.getUntTaskwkSeCd().replaceAll(",", "");
//			if (taskwkSeCd.length() > 3) {
//				taskwkSeCd = taskwkSeCd.substring(0, 3);
//			}
//			dmUpdateMap.put("TASKWK_SE_CD", taskwkSeCd);
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			dmUpdateMap.put("MENU_NO", authMenuNo);

		}

		dmUpdateMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));
		dmUpdateMap.put("IOBX_SE_CD", dmSave.getValue("IOBX_SE_CD"));
		dmUpdateMap.put("DOC_INO_CN", dmSave.getValue("DOC_INO_CN"));
		dmUpdateMap.put("INO_DOC_TTL_NM", dmSave.getValue("INO_DOC_TTL_NM"));
		dmUpdateMap.put("RCVR_ID", dmSave.getValue("RCVR_ID"));
		dmUpdateMap.put("ATFINO", dmSave.getValue("ATFINO"));
		/* 공문 타입 추가 된 영역 */
		//dmUpdateMap.put("RCVR_NM_ENCPT", scpDb.scpEncB64(dmSave.getValue("RCVR_NM_ENCPT"))); // 수신자명 암호화
		dmUpdateMap.put("RCVR_NM_ENCPT", dmSave.getValue("RCVR_NM_ENCPT")); // 수신자명
		dmUpdateMap.put("THRGH_NM", dmSave.getValue("THRGH_NM")); // 경유
		dmUpdateMap.put("APPA_NM", dmSave.getValue("APPA_NM")); // 결제라인명
		//dmUpdateMap.put("SPPRTR_NM_ENCPT", scpDb.scpEncB64(dmSave.getValue("SPPRTR_NM_ENCPT"))); //협조자 암호화
		dmUpdateMap.put("SPPRTR_NM_ENCPT", dmSave.getValue("SPPRTR_NM_ENCPT")); //협조자 암호화
		dmUpdateMap.put("ENFC_NM", dmSave.getValue("ENFC_NM")); // 시행명
		dmUpdateMap.put("RCPT_NM", dmSave.getValue("RCPT_NM")); // 접수명
		dmUpdateMap.put("RLS_YN", dmSave.getValue("RLS_YN")); // 공개여부

		/* 발신자 연락처 정보 관리 */
		dmUpdateMap.put("BPLC_ADDR", dmSave.getValue("BPLC_ADDR"));
		dmUpdateMap.put("RPRS_TELNO", dmSave.getValue("RPRS_TELNO"));
		dmUpdateMap.put("RPRS_FXNO", dmSave.getValue("RPRS_FXNO"));

		docsrService.updateInqDoc(dmUpdateMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updatePrslInqDoc.do
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/updatePrslInqDoc.do")
	public View updatePrslInqDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmUpdateMap = new HashMap<>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			dmUpdateMap.put("LOGIN_ID", loginVO.getId());
			dmUpdateMap.put("LAST_MDFR_ID", loginVO.getId());

			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			dmUpdateMap.put("MENU_NO", authMenuNo);
//			String taskwkSeCd = loginVO.getUntTaskwkSeCd().replaceAll(",", "");
//			if (taskwkSeCd.length() > 3) {
//				taskwkSeCd = taskwkSeCd.substring(0, 3);
//			}
//			dmUpdateMap.put("TASKWK_SE_CD", taskwkSeCd);
		}

		dmUpdateMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));
		dmUpdateMap.put("IOBX_SE_CD", dmSave.getValue("IOBX_SE_CD"));
		dmUpdateMap.put("DOC_INO_CN", dmSave.getValue("DOC_INO_CN"));
		dmUpdateMap.put("INO_DOC_TTL_NM", dmSave.getValue("INO_DOC_TTL_NM"));

		docsrService.updatePrslInqDoc(dmUpdateMap);

		return new JSONDataView();
	}




	/**
	 * @Method명 : updateRcptnInstPrslInqDoc.do
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@RequestMapping(value = "/updateRcptnInstPrslInqDoc.do")
	public View updateRcptnInstPrslInqDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmUpdateMap = new HashMap<>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			dmUpdateMap.put("LOGIN_ID", loginVO.getId());
			dmUpdateMap.put("LAST_MDFR_ID", loginVO.getId());
			dmUpdateMap.put("INST_NO", loginVO.getInstNo());
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			dmUpdateMap.put("MENU_NO", authMenuNo);
//			String taskwkSeCd = loginVO.getUntTaskwkSeCd().replaceAll(",", "");
//			if (taskwkSeCd.length() > 3) {
//				taskwkSeCd = taskwkSeCd.substring(0, 3);
//			}
//			dmUpdateMap.put("TASKWK_SE_CD", taskwkSeCd);
		}

		dmUpdateMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));
		dmUpdateMap.put("IOBX_SE_CD", dmSave.getValue("IOBX_SE_CD"));
		dmUpdateMap.put("DOC_INO_CN", dmSave.getValue("DOC_INO_CN"));
		dmUpdateMap.put("INO_DOC_TTL_NM", dmSave.getValue("INO_DOC_TTL_NM"));

		docsrService.updateRcptnInstPrslInqDoc(dmUpdateMap);

		return new JSONDataView();
	}



	@RequestMapping(value = "/onLoadselectDsgDocRcvr.do")
	public View onLoadselectDsgDocRcvr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		docsrService.onLoadselectDsgDocRcvr();

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectOrgDept.do")
	public View selectOrgDept(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsOrgDept", docsrService.selectOrgDept(dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/deleteInqDoc.do")
	public View deleteInqDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmUpdateMap = new HashMap<>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			dmUpdateMap.put("LOGIN_ID", loginVO.getId());
			dmUpdateMap.put("LAST_MDFR_ID", loginVO.getId());
//			String taskwkSeCd = loginVO.getUntTaskwkSeCd().replaceAll(",", "");
//			if (taskwkSeCd.length() > 3) {
//				taskwkSeCd = taskwkSeCd.substring(0, 3);
//			}
//			dmUpdateMap.put("TASKWK_SE_CD", taskwkSeCd);
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
			Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			dmUpdateMap.put("MENU_NO", authMenuNo);
		}

		dmUpdateMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));

		docsrService.deleteInqDoc(dmUpdateMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDocMyselfList.do
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : TAESOO. SONG
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 내게 쓴 문서함 목록 가져오기.
	 */

	@RequestMapping(value = "selectDocMyselfList.do")
	public View selectDocMyselfList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> dmSearchMap = new HashMap<>();

		dmSearchMap.put("DOCS_TYPE_CD", "S"); // 내게 쓴 문서함 값 셋팅

		Map<String, Object> result = docsrService.selectDocsCommonList(request, dataRequest, dmSearchMap);

		dataRequest.setResponse("dsList", result.get("totalList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}

	/**
	 * @Method명 : executeDocMySelf
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Taesoo Song
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 내게 쓴 문서 저장, 수정, 삭제
	 */
	@RequestMapping(value = "executeDocMySelf.do")
	public View executeDocMySelf(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> dmExcuteMap = new HashMap<>();

		docsrService.executeDocMySelf(request, dataRequest, dmExcuteMap);

		return new JSONDataView();
	}

	@RequestMapping(value = "listDocsDetail.do")
	public View listDocsDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = docsrService.selectListDocsDetail(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("dsList"));

		return new JSONDataView();
	}

	@RequestMapping(value = "selectDocFileCabinetList.do")
	public View selectDocFileCabinetList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> dmSearchMap = new HashMap<>();

		dmSearchMap.put("DOCS_TYPE_CD", "D"); // 보관함 값 셋팅
		Map<String, Object> result = docsrService.selectDocsCommonList(request, dataRequest, dmSearchMap);

		dataRequest.setResponse("dsList", result.get("totalList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		return new JSONDataView();
	}

	@RequestMapping(value = "excuteCabinetDoc.do")
	public View excuteCabinetDoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = docsrService.excuteCabinetDoc(request, dataRequest);
		return new JSONDataView();
	}

	@RequestMapping(value = "selectDocsCstdyDetail.do")
	public View selectDocsCstdyDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = docsrService.selectListDocsCstdyDetail(request, dataRequest);

		log.debug(result.get("dsList").toString());
		dataRequest.setResponse("dsList", result.get("dsList"));

		return new JSONDataView();
	}

	@RequestMapping(value = "selectDocRcvrUsrList.do")
	public View selectDocRcvrUsrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = docsrService.selectListDocsRcvrUsrList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("totalList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}


	@RequestMapping(value = "selectDocRcvrInstList.do")
	public View selectDocRcvrInstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = docsrService.selectListDocsRcvrInstList(request, dataRequest);

		dataRequest.setResponse("dsList", result.get("totalList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));

		return new JSONDataView();
	}


	@RequestMapping(value = "deleteDocsData.do")
	public View deleteDocsData(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = docsrService.deleteDocsData(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "onLoadBizUsr.do")
	public View onLoadBizUsr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsBiz", docsrService.selectBizList(request, dataRequest));

		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별

		dataRequest.setResponse("dsSns", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));
		//System.out.println("dsSns :::::: "+mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));
		return new JSONDataView();
	}

	@RequestMapping(value = "onLoadBizUsr2.do")
	public View onLoadBizUsr2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsBizExcute", docsrService.selectBizExcuteList(request, dataRequest));
		return new JSONDataView();
	}

	@RequestMapping(value = "selectBizUsrList.do")
	public View selectBizUsrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsAddress", docsrService.selectBizUsrList(request, dataRequest));
		return new JSONDataView();
	}

	@RequestMapping(value = "onLoadInqDocDtl.do")
	public View onLoadInqDocDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, String> dmDocDtl = new HashMap<String, String>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmDocDtl");
		//dmSave.equals(dmDocDtl);

		dmDocDtl = dmSave.getSingleValueMap();
		//dmDocDtl = (Map<String, Object>) dmSave;
		String instNo = "";
		log.debug(dmDocDtl.toString());
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// instNo = loginVO.getAgencyContacts().split("/")[2];
		}
		param.put("USER_ID",dmSave.getValue("SNDPTY_ID"));
		param.put("INST_NO",dmSave.getValue("DSPTCH_INST_NO"));
		param.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));
		Map<String, Object> result = docsrService.selectSndptyUserInfo(param);

//		param.put("INST_NO", result.get("INST_NO"));
		// 원본
		// Map<String, Object> instInfo = docsrService.selectDocsInstInfo(param);
		Map<String, Object> instInfo = docsrService.selectDocsDsptchInstInfo(param);
		if (instInfo != null) {
//			instInfo.get("")
			result.put("ATFINO", instInfo.get("ATFINO"));
			//result.put("OFFCS_SGNNG_NO", instInfo.get("OFFCS_SGNNG_NO"));
			result.put("REAL_FILE_NM", instInfo.get("REAL_FILE_NM"));
			result.put("STRG_FILE_NM", instInfo.get("STRG_FILE_NM"));
			result.put("STRG_COURS_NM", instInfo.get("STRG_COURS_NM"));
			result.put("FILE_SZ", instInfo.get("FILE_SZ"));

			dmDocDtl.put("OFFCS_SGNNG_NO", instInfo.get("OFFCS_SGNNG_NO").toString());
			dmDocDtl.put("OFFCS_SGNNG_ATFINO", instInfo.get("ATFINO").toString());
			dmDocDtl.put("OFFCS_SGNNG_ATCMFL_MNG_NO", instInfo.get("MNG_SN").toString());
		}else {
			dmDocDtl.put("OFFCS_SGNNG_NO", "");
		}

		//dmDocDtl.put("RCVR_NM_ENCPT", scpDb.scpDecB64((String) dmSave.getValue("RCVR_NM_ENCPT")));
		//dmDocDtl.put("SPPRTR_NM_ENCPT", scpDb.scpDecB64((String) dmSave.getValue("SPPRTR_NM_ENCPT")));
		//result.put("RCVR_NM_ENCPT", scpDb.scpDecB64((String) result.get("RCVR_NM_ENCPT")));


		Map<String, Object> dsRcptnInstList = docsrService.selectListDocsRcvrInstList(request, dataRequest);

		Map<String, Object> offcsAtfinoInfo = new HashMap<String, Object>();
		Map<String, Object> offcsAtfinoInfoResult = new HashMap<String, Object>();

		String offcsSgnngAtfino = dmDocDtl.get("OFFCS_SGNNG_ATFINO").toString();
		String offcsSgnngAtcmflMngNo = dmDocDtl.get("OFFCS_SGNNG_ATCMFL_MNG_NO").toString();

		if(offcsSgnngAtfino.equals("") && offcsSgnngAtcmflMngNo.equals("")) {
			offcsAtfinoInfoResult.put("ATFINO", "");
			offcsAtfinoInfoResult.put("MNG_SN", "");
			offcsAtfinoInfoResult.put("STRG_COURS_NM", "");
			offcsAtfinoInfoResult.put("STRG_FILE_NM", "");
		}else {

			offcsAtfinoInfo = docsrService.selectOffcsAtfinoInfo(request, dataRequest, dmDocDtl);

			offcsAtfinoInfoResult.put("ATFINO", offcsAtfinoInfo.get("ATFINO"));
			offcsAtfinoInfoResult.put("MNG_SN", offcsAtfinoInfo.get("MNG_SN"));
			offcsAtfinoInfoResult.put("STRG_COURS_NM", offcsAtfinoInfo.get("STRG_COURS_NM"));
			offcsAtfinoInfoResult.put("STRG_FILE_NM", offcsAtfinoInfo.get("STRG_FILE_NM"));
		}

		dataRequest.setResponse("dmAtfinoInfo", offcsAtfinoInfoResult);

		dataRequest.setResponse("dsRcptnInstList", dsRcptnInstList.get("totalList"));
		dataRequest.setResponse("dmPage", dsRcptnInstList.get("dmPage"));



		dataRequest.setResponse("dmDocDtl", dmDocDtl);
		dataRequest.setResponse("dmUsrInfo", result);

		return new JSONDataView();
	}



	@RequestMapping(value = "onLoadInnerEmlDetailDtl.do")
	public View onLoadInnerEmlDetailDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, String> dmDocDtl = new HashMap<String, String>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmDocDtl");
		//dmSave.equals(dmDocDtl);

		dmDocDtl = dmSave.getSingleValueMap();
		//dmDocDtl = (Map<String, Object>) dmSave;
		String instNo = "";
		log.debug(dmDocDtl.toString());
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// instNo = loginVO.getAgencyContacts().split("/")[2];
		}
		param.put("USER_ID",dmSave.getValue("SNDPTY_ID"));
		param.put("INST_NO",dmSave.getValue("DSPTCH_INST_NO"));
		Map<String, Object> result = docsrService.selectSndptyUserInfo(param);

//		param.put("INST_NO", result.get("INST_NO"));
		Map<String, Object> instInfo = docsrService.selectDocsInstInfo(param);
		if (instInfo != null) {
//			instInfo.get("")
			result.put("BPLC_ADDR", instInfo.get("RPRS_ADDR"));
			result.put("RPRS_TELNO", instInfo.get("RPRS_TELNO"));
			result.put("RPRS_FXNO", instInfo.get("RPRS_FXNO"));
			result.put("HPGE_URL_ADDR", instInfo.get("HPGE_ADDR"));
			result.put("ATFINO", instInfo.get("ATFINO"));
			//result.put("OFFCS_SGNNG_NO", instInfo.get("OFFCS_SGNNG_NO"));
			result.put("REAL_FILE_NM", instInfo.get("REAL_FILE_NM"));
			result.put("STRG_FILE_NM", instInfo.get("STRG_FILE_NM"));
			result.put("STRG_COURS_NM", instInfo.get("STRG_COURS_NM"));
			result.put("FILE_SZ", instInfo.get("FILE_SZ"));

			dmDocDtl.put("OFFCS_SGNNG_NO", instInfo.get("OFFCS_SGNNG_NO").toString());
		}else {
			dmDocDtl.put("OFFCS_SGNNG_NO", "");
		}

		//dmDocDtl.put("RCVR_NM_ENCPT", scpDb.scpDecB64((String) dmSave.getValue("RCVR_NM_ENCPT")));
		//dmDocDtl.put("SPPRTR_NM_ENCPT", scpDb.scpDecB64((String) dmSave.getValue("SPPRTR_NM_ENCPT")));
		//result.put("RCVR_NM_ENCPT", scpDb.scpDecB64((String) result.get("RCVR_NM_ENCPT")));


		Map<String, Object> dsRcptnUsrList = docsrService.selectListDocsRcvrUsrList(request, dataRequest);

		dataRequest.setResponse("dsRcptnUsrList", dsRcptnUsrList.get("totalList"));
		dataRequest.setResponse("dmPage", dsRcptnUsrList.get("dmPage"));


		dataRequest.setResponse("dmDocDtl", dmDocDtl);
		dataRequest.setResponse("dmUsrInfo", result);

		return new JSONDataView();
	}



	@RequestMapping(value = "onloadDocsCstdyDetail.do")
	public View onloadDocsCstdyDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, String> dmDocDtl = new HashMap<String, String>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmDtlParam");
		//dmSave.equals(dmDocDtl);

		dmDocDtl = dmSave.getSingleValueMap();
		//dmDocDtl = (Map<String, Object>) dmSave;
		log.debug(dmDocDtl.toString());
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
		}
//		param.put("USER_ID",dmSave.getValue("SNDPTY_ID"));
//		Map<String, Object> result = docsrService.selectSndptyUserInfo(param);
		//dmDocDtl.put("RCVR_NM_ENCPT", scpDb.scpDecB64((String) dmSave.getValue("RCVR_NM_ENCPT")));
		//dmDocDtl.put("SPPRTR_NM_ENCPT", scpDb.scpDecB64((String) dmSave.getValue("SPPRTR_NM_ENCPT")));
		//result.put("RCVR_NM_ENCPT", scpDb.scpDecB64((String) result.get("RCVR_NM_ENCPT")));
		dataRequest.setResponse("dmDtlParam", dmDocDtl);
//		dataRequest.setResponse("dmUsrInfo", result);

		return new JSONDataView();
	}

	@RequestMapping(value = "onloadDocInstInfo.do")
	public View onloadDocInstInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", userId);
		param.put("INST_NO", loginVO.getInstNo());

		Map<String, Object> data = docsrService.selectSndptyUserInfo(param);
//		param.put("INST_NO", data.get("INST_NO"));

		Map<String, Object> instInfo = docsrService.selectDocsInstInfo(param);

		if(instInfo != null) {
			data.put("RPRS_ADDR", instInfo.get("RPRS_ADDR"));
			data.put("RPRS_TELNO", instInfo.get("RPRS_TELNO"));
			data.put("RPRS_FXNO", instInfo.get("RPRS_FXNO"));
			data.put("HPGE_ADDR", instInfo.get("HPGE_ADDR"));
			data.put("ATFINO", instInfo.get("ATFINO"));
			data.put("RPRS_ADDR", instInfo.get("RPRS_ADDR"));
		}

		dataRequest.setResponse("dmInstInfo", data);

		return new JSONDataView();
	}

	@RequestMapping(value = "excuteDocInstInfo.do")
	public View excuteDocInstInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		String instNo = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			//instNo = loginVO.getAgencyContacts().split("/")[2];
			instNo = loginVO.getInstNo().toString();
		}

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmInstInfo");

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("USER_ID", userId);
		param.put("INST_NO", instNo);
		param.put("RPRS_ADDR", dmSave.getValue("RPRS_ADDR"));
		param.put("RPRS_TELNO", dmSave.getValue("RPRS_TELNO"));
		param.put("RPRS_FXNO", dmSave.getValue("RPRS_FXNO"));
		param.put("HPGE_ADDR", dmSave.getValue("HPGE_ADDR"));
		param.put("ATFINO", dmSave.getValue("ATFINO"));

		Map<String, Object> instInfo = docsrService.selectDocsInstInfo(param);

		if (instInfo != null) {
			docsrService.updateInstInfo(param);
		} else {
			docsrService.insertInstInfo(param);
		}

		return new JSONDataView();
	}
}
