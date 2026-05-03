/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.wrksupt.docsr.service.impl;

import java.util.ArrayList;
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

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
//import isry.itgcms.util.Masking;
//import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;
import isry.itgcms.wrksupt.docsr.mapper.DocsrMapper;
import isry.itgcms.wrksupt.docsr.service.DocsrService;

/**
 * @파일명 : DocsrServiceImpl.java
 * @프로그램 설명 : 문서수발신 조회 및 발송을 관리하는 ServiceImpl
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 20.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 20.
 * @수정내용 : - -
 */
@Service("docsrService")
public class DocsrServiceImpl extends IsryBaseServiceImpl implements DocsrService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "docsrMapper")
	private DocsrMapper docsrMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	//ScpDb scpDb = new ScpDb();
	//Masking mask = new Masking();

	/**
	 * @Method명 : selectInqDocListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 목록 totalCount조회
	 */
	@Override
	public Integer selectInqDocListTotalCount(Map<String, Object> map) throws Exception {

		return docsrMapper.selectInqDocListTotalCount(map);
	}

	/**
	 * @Method명 : selectRecvInqDocListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : TAESOO. SONG
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 문서 수신함 목록 totalCount조회
	 */
	@Override
	public Integer selectRcvrInqDocListTotalCount(Map<String, Object> map) throws Exception {

		return docsrMapper.selectRcvrInqDocListTotalCount(map);
	}

	/**
	 * @Method명 : selectInqDocList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectInqDocList(Map<String, Object> map) throws Exception {

		return docsrMapper.selectInqDocList(map);
	}

	/**
	 * @Method명 : selectRcvrInqDocList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : TAESOO SONG
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectRcvrInqDocList(Map<String, Object> map) throws Exception {

		return docsrMapper.selectRcvrInqDocList(map);
	}

	/**
	 * @Method명 : saveInqDoc
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(CUD)
	 */
	@Override
	public void saveInqDoc(DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();

		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			docsrMapper.deleteInqDoc(mapDel);
		}
	}

	/**
	 * @Method명 : insertInqDoc
	 * @param dmSaveMap
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	@Override
	public void insertInqDoc(Map<String, Object> dmSaveMap) throws Exception {
		
		log.debug(dmSaveMap.toString());

		// 암호화 들어가야 할 영역
		//dmSaveMap.put("RCVR_NM_ENCPT", scpDb.scpEncB64((String) dmSaveMap.get("RCVR_NM_ENCPT")));
		//dmSaveMap.put("SPPRTR_NM_ENCPT", scpDb.scpEncB64((String) dmSaveMap.get("SPPRTR_NM_ENCPT")));
		
		if (null == dmSaveMap.get("RLS_YN") || "".equals(dmSaveMap.get("RLS_YN"))) {
			dmSaveMap.put("RLS_YN", "Y");
		}
		
		log.debug("문서 발신 IOBX_SE_CD == " + dmSaveMap.get("IOBX_SE_CD"));
		log.debug("문서 발신 OFFCS_SGNNG_NO == " + dmSaveMap.get("OFFCS_SGNNG_NO"));
		
		if ("O".equals(dmSaveMap.get("IOBX_SE_CD"))) {
			
			String offcsStr = dmSaveMap.get("OFFCS_SGNNG_NO").toString();
			
			if (!offcsStr.equals("")) {
				// OFFCS_SGNNG_NO:직인서명번호로 등록된 직인 첨부파일 일련번호 및 관리번호(식별)정보 조회
				
				// 직인서명상세:SAA230 조회 > 첨부파일상세:SAB821 조회
				Map<String, Object> offcsAtfinoInfoMap = docsrMapper.selectOffcsAtfinoInfo(dmSaveMap);
				// ATFINO, MNG_SN
				
				dmSaveMap.put("OFFCS_SGNNG_ATFINO", offcsAtfinoInfoMap == null ? "" : offcsAtfinoInfoMap.get("ATFINO"));
				dmSaveMap.put("OFFCS_SGNNG_ATCMFL_MNG_NO", offcsAtfinoInfoMap == null ? "" : offcsAtfinoInfoMap.get("MNG_SN"));
				
				docsrMapper.insertInqDoc(dmSaveMap);
				
			} else {
				// OFFCS_SGNNG_NO:직인서명번호가 없는 경우 등록된 직인이 없음 빈값처리
				dmSaveMap.put("OFFCS_SGNNG_ATFINO", "");
				dmSaveMap.put("OFFCS_SGNNG_ATCMFL_MNG_NO", "");
				
				docsrMapper.insertInqDoc(dmSaveMap);
			}
			
		} else {
			
			// 내부메일(B:개인, G:사업 은 직인 사용 안함.)
			dmSaveMap.put("OFFCS_SGNNG_ATFINO", "");
			dmSaveMap.put("OFFCS_SGNNG_ATCMFL_MNG_NO", "");
			
			docsrMapper.insertInqDoc(dmSaveMap);
		}
		
		
		
		// Default 기본 정보
		Map<String, Object> enfsnInfo = docsrMapper.selectEnfsnInfo(dmSaveMap); // 사용안함

		// 문서수발신연락처 정보
		Map<String, Object> sndptInfo = docsrMapper.selectSndptInfo(dmSaveMap); // 사용안함
//		if(sndptInfo != null) {
//			sndptInfo.get("ENFSN_NO");
//		}
		// 1:N 구조를 가짐. 수신함 목록에서 RCVR_ID로 본인 포함 여부를 체킹하게 됨.
		// AS-IS 기준 기관 + 하위 기관 여부 체크 -> TO-BE 로그인 ID 로 변경 처리 이충수 매니저 확인 사항
		// 수정자 : Taesoo Song
		
		
		log.debug("%%%% INO_DOC_ESNTAL_NO == " + dmSaveMap.get("INO_DOC_ESNTAL_NO").toString());
		
		if("O".equals(dmSaveMap.get("IOBX_SE_CD").toString())) {
			
			String rcvrInstNoArr = (String) dmSaveMap.get("RCPTN_INST_NO");
			String[] splitRcvrInstNoArr = rcvrInstNoArr.split(",");
			
			// 문서수발신 공문
			for (int i = 0; i < splitRcvrInstNoArr.length; i++) {
				Map<String, Object> dmSaveMap3 = new HashMap<>();

				dmSaveMap3.put("INO_DOC_ESNTAL_NO", dmSaveMap.get("INO_DOC_ESNTAL_NO"));
				dmSaveMap3.put("SNDPTY_ID", dmSaveMap.get("SNDPTY_ID"));
				dmSaveMap3.put("RCPTN_INST_NO", splitRcvrInstNoArr[i]);
				dmSaveMap3.put("INO_DOC_TTL_NM", dmSaveMap.get("INO_DOC_TTL_NM"));
				dmSaveMap3.put("SNDPTY_ID", dmSaveMap.get("SNDPTY_ID"));

				docsrMapper.insertInqDoc3(dmSaveMap3);
			}
			
		}else {
			
			// 수신자 아이디
			String rcvrIdArr = (String) dmSaveMap.get("RCVR_ID");
			String[] splitRcvrIdArr = rcvrIdArr.split(",");
			
			// 수신기관
			String rcptnInstNoArr = (String) dmSaveMap.get("INST_NO");
			String[] splitRcptnInstNo = rcptnInstNoArr.split(",");
			
			// 내부메일 개인, 사업
			for (int i = 0; i < splitRcvrIdArr.length; i++) {
				Map<String, Object> dmSaveMap2 = new HashMap<>();

				dmSaveMap2.put("INO_DOC_ESNTAL_NO", dmSaveMap.get("INO_DOC_ESNTAL_NO"));
				dmSaveMap2.put("SNDPTY_ID", dmSaveMap.get("SNDPTY_ID"));
				dmSaveMap2.put("RCVR_ID", splitRcvrIdArr[i]);
				dmSaveMap2.put("INST_NO", splitRcptnInstNo[i]);
				dmSaveMap2.put("INO_DOC_TTL_NM", dmSaveMap.get("INO_DOC_TTL_NM"));
				dmSaveMap2.put("SNDPTY_ID", dmSaveMap.get("SNDPTY_ID"));
				

				docsrMapper.insertInqDoc2(dmSaveMap2);
			}
		}
		
	}

	/**
	 * @Method명 : updateInqDoc
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@Override
	public void updateInqDoc(Map<String, Object> dmUpdateMap) throws Exception {

		// 암호화 들어가야 할 영역
		//dmUpdateMap.put("RCVR_NM_ENCPT", scpDb.scpEncB64((String) dmUpdateMap.get("RCVR_NM_ENCPT")));
		//dmUpdateMap.put("SPPRTR_NM_ENCPT", scpDb.scpEncB64((String) dmUpdateMap.get("SPPRTR_NM_ENCPT")));

		docsrMapper.updateInqDoc(dmUpdateMap);

		String rcvrIdArr = (String) dmUpdateMap.get("RCVR_ID");
		String[] splitRcvrIdArr = rcvrIdArr.split(",");

		// 모두 삭제 한 뒤 재 삽입.
		docsrMapper.deleteRcvrId(dmUpdateMap);
		// 1:N 구조를 가짐. 수신함 목록에서 RCVR_ID로 본인 포함 여부를 체킹하게 됨.
		// AS-IS 기준 기관 + 하위 기관 여부 체크 -> TO-BE 로그인 ID 로 변경 처리 이충수 매니저 확인 사항
		// 수정자 : Taesoo Song
		for (int i = 0; i < splitRcvrIdArr.length; i++) {
			Map<String, Object> dmSaveMap2 = new HashMap<>();

			dmSaveMap2.put("INO_DOC_ESNTAL_NO", dmUpdateMap.get("INO_DOC_ESNTAL_NO"));
			dmSaveMap2.put("SNDPTY_ID", dmUpdateMap.get("SNDPTY_ID"));
			dmSaveMap2.put("RCVR_ID", splitRcvrIdArr[i]);
			dmSaveMap2.put("INO_DOC_TTL_NM", dmUpdateMap.get("INO_DOC_TTL_NM"));
			dmSaveMap2.put("SNDPTY_ID", dmUpdateMap.get("SNDPTY_ID"));

			docsrMapper.insertInqDoc2(dmSaveMap2);
		}
	}

	/**
	 * @Method명 : updatePrslInqDoc
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	@Override
	public void updatePrslInqDoc(Map<String, Object> dmUpdateMap) throws Exception {
		// 수신자 목록 존재 여부 확인
		int val = docsrMapper.selectRcvrDocumentMatch(dmUpdateMap);

		if (val > 0) {
			// 수신 당사자 일 경우 조회 일자와 count 갯수 추가.
			docsrMapper.updatePrslInqDoc(dmUpdateMap);
		}
	}

	/**
	 * @Method명 : onLoadselectDsgDocRcvr
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 :
	 */
	@Override
	public void onLoadselectDsgDocRcvr() throws Exception {

		docsrMapper.onLoadselectDsgDocRcvr();
	}

	/**
	 * @Method명 : selectOrgDept
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 3.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOrgDept(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmOrg");

		if (param != null) {
			String orgCode = param.getValue("orgCode");
			if (orgCode != null && !"".equals(orgCode)) {
				Map<String, String> map = new HashMap<>();
				map.put("INST_CD", orgCode);
				return docsrMapper.selectOrgDept(map);
			}
		}

		return null;
	}

	/**
	 * @Method명 : selectOrgDept
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 3.
	 * @Method설명 :
	 */
	@Override
	public void deleteInqDoc(Map<String, Object> dmUpdateMap) throws Exception {

		docsrMapper.deleteInqDocumet(dmUpdateMap);
	}

	@Override
	public Map<String, Object> selectDocsCommonList(HttpServletRequest request, DataRequest dataRequest,
			Map<String, Object> dmSearchMap) throws Exception {
		Map<String, Object> dmResultMap = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (dmSearchParam.getValue("strSearchKey").equals("00")) {								// 전체
			dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("01")) {						// 제목
			dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
			//dmSearchMap.put("INO_DOC_TTL_NM", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("02")) {						// 발신기관
			dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("03")) {						// 발신자
			dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("04")) {						// 수신기관
			dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("05")) {						// 수신자
			dmSearchMap.put("SEARCH_KEY", dmSearchParam.getValue("strSearchKey"));
			dmSearchMap.put("SEARCH_DATA", dmSearchParam.getValue("strSearchData"));
		} else if (dmSearchParam.getValue("strSearchKey").equals("rcvrNm")) {					// 사용안함
			dmSearchMap.put("RCVR_ID", dmSearchParam.getValue("strSearchData"));
		}
		
		log.debug("#### SEARCH_KEY = " + dmSearchMap.get("SEARCH_KEY").toString());
		log.debug("#### DOCS_TYPE_CD = " + dmSearchMap.get("DOCS_TYPE_CD"));
		
		if (!"".equals(dmSearchMap.get("DOCS_TYPE_CD"))) {
			if ("P".equals(dmSearchMap.get("DOCS_TYPE_CD"))) { // 발신함.
				dmSearchMap.put("DOCS_TYPE_CD", "P");
			} else if ("R".equals(dmSearchMap.get("DOCS_TYPE_CD"))) { // 수신함.
				dmSearchMap.put("DOCS_TYPE_CD", "R");
			} else if ("S".equals(dmSearchMap.get("DOCS_TYPE_CD"))) { // 내게 쓴 문서함.
				dmSearchMap.put("DOCS_TYPE_CD", "S");
			} else {
				dmSearchMap.put("DOCS_TYPE_CD", "D"); // 보관함.
			}
		} else {
			dmSearchMap.put("DOCS_TYPE_CD", "");
		}

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			dmSearchMap.put("LOGIN_ID", loginVO.getId());
			
			
			String untTaskwkSeCd = loginVO.getUntTaskwk().toString();
			if(!untTaskwkSeCd.equals("U15")) {
				dmSearchMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
			}else {
				dmSearchMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwkSeCd());
			}
			
			dmSearchMap.put("INST_NO", loginVO.getInstNo());
			
			
			log.debug("사용자아이디 = " + loginVO.getId());
			log.debug("종사자번호 = " + loginVO.getEnfsnNo());
			log.debug("현재 선택된 단위 시스템 코드 = " + loginVO.getUntTaskwk());
			log.debug("사용자 단위 시스템 코드 = " + loginVO.getUntTaskwkSeCd());
			log.debug("기관명 = " + loginVO.getInstNm());
			log.debug("기관번호 = " + loginVO.getInstNo());
			log.debug("사용자기관번호 = " + loginVO.getUserInstNo());
			
		}
		
		List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.selectDocsCommonList
		if ("P".equals(dmSearchMap.get("DOCS_TYPE_CD"))) { // 발신함.

			//if(dmSearchParam.getValue("strSearchKey").equals("05")) {
				//dmSearchMap.put("SEARCH_DATA", scpDb.scpEncB64(dmSearchParam.getValue("strSearchData").toString()));
			//}
			
			//dmSearchMap.put("DSPTCH_INST_NM", dmSearchParam.getValue("DSPTCH_INST_NM"));
			
			dmSearchMap.put("START_DATE", dmSearchParam.getValue("START_DATE"));
			dmSearchMap.put("END_DATE", dmSearchParam.getValue("END_DATE"));
			
			dmSearchMap.put("IOBX_SE_CD", dmSearchParam.getValue("IOBX_SE_CD"));
			log.debug("##### IOBX_SE_CD = " + dmSearchMap.get("IOBX_SE_CD").toString());
			if(dmSearchParam.getValue("IOBX_SE_CD").equals("O")) {
				// 문서수발신 메뉴 용 O(공문)
				totalList = docsrMapper.selectDocsCommonList(dmSearchMap);
			}else {
				
				dmSearchMap.put("IOBX_SE_CD_G_YN", dmSearchParam.getValue("IOBX_SE_CD_G_YN"));
				
				// 내부메일 메뉴 용 B(개인), G(사업) 2가지 UI 에서 전체인 경우 "", 개인:B, 사업:G 으로 넘겨 구분함.
				totalList = docsrMapper.selectInnerEmlDsptchList(dmSearchMap);
			}
			
			
			
		} else if ("R".equals(dmSearchMap.get("DOCS_TYPE_CD"))) { // 수신함.
			
			//if(dmSearchParam.getValue("strSearchKey").equals("03")) {
				//dmSearchMap.put("SEARCH_DATA", scpDb.scpEncB64(dmSearchParam.getValue("strSearchData").toString()));
			//}
			
			//dmSearchMap.put("DSPTCH_INST_NM", dmSearchParam.getValue("DSPTCH_INST_NM"));
			
			dmSearchMap.put("START_DATE", dmSearchParam.getValue("START_DATE"));
			dmSearchMap.put("END_DATE", dmSearchParam.getValue("END_DATE"));
			
			dmSearchMap.put("IOBX_SE_CD", dmSearchParam.getValue("IOBX_SE_CD"));
			log.debug("##### IOBX_SE_CD = " + dmSearchMap.get("IOBX_SE_CD").toString());
			if(dmSearchParam.getValue("IOBX_SE_CD").equals("O")) {
				// 문서수발신 메뉴 용 O(공문)
				totalList = docsrMapper.selectDocsRcvrCommonList(dmSearchMap);
			}else {
				
				dmSearchMap.put("IOBX_SE_CD_G_YN", dmSearchParam.getValue("IOBX_SE_CD_G_YN"));
				// 내부메일 메뉴 용 B(개인), G(사업) 2가지 UI 에서 전체인 경우 "", 개인:B, 사업:G 으로 넘겨 구분함.
				totalList = docsrMapper.selectInnerEmlRcptnList(dmSearchMap);
			}
			
			
		} else if ("D".equals(dmSearchMap.get("DOCS_TYPE_CD"))) { // 보관함. - 테이블 다름.			
			totalList = docsrMapper.selectDocFileCabinetList(dmSearchMap);
		} else { // 내게 쓴 문서함.			
			totalList = docsrMapper.selectDocsRcvrCommonList(dmSearchMap);
		}
				
		for (Map<String, Object> rowMap : totalList) {
			
			//if (rowMap.containsKey("SNDPTY_NM")) {
				//rowMap.replace("SNDPTY_NM", scpDb.scpDecB64(StringUtil.nullConvert(rowMap.get("SNDPTY_NM"))));
			//}
			
			if (rowMap.containsKey("RCVR_ID")) {
				
				String[] rcvrIds = StringUtil.nullConvert(rowMap.get("RCVR_ID")).split(",");
				String rcvrId = "";
				
				if(rcvrIds.length == 1) {
					rcvrId = StringUtil.nullConvert(rowMap.get("RCVR_ID"));
					rowMap.replace("RCVR_ID", rcvrId);
				}else if(rcvrIds.length > 1) {
					rcvrId = rcvrIds[0];
					rowMap.replace("RCVR_ID", rcvrId + " 외 " + (rcvrIds.length - 1) + "명");
				}
			}
			
			if (rowMap.containsKey("RCVR_NM_ENCPT")) {
				
				String[] rcvrNms = StringUtil.nullConvert(rowMap.get("RCVR_NM_ENCPT")).split(",");
				String rcvrNm = "";
				
				if (rcvrNms.length == 1) {
					//rcvrNm = StringUtil.nullConvert(rowMap.get("RCVR_NM_ENCPT"));
					//rowMap.replace("RCVR_NM_ENCPT", scpDb.scpDecB64(rcvrNm));

				} else if (rcvrNms.length > 1) {
					rcvrNm = rcvrNms[0];
					//rowMap.replace("RCVR_NM_ENCPT", scpDb.scpDecB64(rcvrNm) + " 외 " + (rcvrNms.length - 1) + "명");
					rowMap.replace("RCVR_NM_ENCPT", rcvrNm + " 외 " + (rcvrNms.length - 1) + "명");
				}
			}
		}
				
		dmResultMap.put("totalList", totalList);		

		return dmResultMap;
	}

	@Override
	public void executeDocMySelf(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> dmSearchMap)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dsList");
		Map<String, Object> dmSaveMap = new HashMap<>();

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			dmSaveMap.put("SNDPTY_ID", loginVO.getId());
			// 단위 업무 적용 시 업무구분코드는 단건만 가지고 있을 거라고 하셔서 2개 이상일 경우 제일 먼저 선택된 업무 구분코드 삽입.
//			String taskwkSeCd = loginVO.getUntTaskwkSeCd().replaceAll(",", "");
//			if (taskwkSeCd.length() > 3) {
//				taskwkSeCd = taskwkSeCd.substring(0, 3);
//			}
//			dmSaveMap.put("TASKWK_SE_CD", taskwkSeCd);
			String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? ""
					: dataRequest.getParameter("_AUTH_APP_ID");
			Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null
					|| "".equals(dataRequest.getParameter("_AUTH_MENU_NO")) ? 0
							: Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
			dmSaveMap.put("MENU_NO", authMenuNo);
		}
		if ("S".equals(dmSave.getValue("DOCS_TYPE_CD"))) {
			dmSaveMap.put("IOBX_SE_CD", "S");
		} else {
			dmSaveMap.put("IOBX_SE_CD", dmSave.getValue("IOBX_SE_CD"));
		}
		dmSaveMap.put("DOC_INO_CN", dmSave.getValue("DOC_INO_CN"));
		dmSaveMap.put("RCVR_ID", dmSave.getValue("RCVR_ID"));
		dmSaveMap.put("INO_DOC_TTL_NM", dmSave.getValue("INO_DOC_TTL_NM"));
		dmSaveMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));

		// 임시로 강제 셋팅
		if (dmSave.getValue("ATFINO") == "" || dmSave.getValue("ATFINO") == null) {
			dmSaveMap.put("ATFINO", "");
		} else {
			dmSaveMap.put("ATFINO", dmSave.getValue("ATFINO"));
		}

		if ("SAVE".equals(dmSave.getValue("TYPE"))) {
			// 세션에서 값을 못 받아와 임의로 가져옴
			// 로그인 한 유저의 기관번호
			int instNo = docsrMapper.selectDsptchInstNo(dmSaveMap);
			dmSaveMap.put("DSPTCH_INST_NO", Integer.valueOf(instNo));

			docsrMapper.insertCommonDoc(dmSaveMap);

			dmSaveMap.put("INO_DOC_ESNTAL_NO", dmSaveMap.get("INO_DOC_ESNTAL_NO"));
			dmSaveMap.put("SNDPTY_ID", dmSaveMap.get("SNDPTY_ID"));
			dmSaveMap.put("RCVR_ID", loginVO.getId());
			dmSaveMap.put("INO_DOC_TTL_NM", dmSaveMap.get("INO_DOC_TTL_NM"));

			docsrMapper.insertCommonDocDetail(dmSaveMap);

		} else if ("UPDATE".equals(dmSave.getValue("TYPE"))) {
			docsrMapper.updateCommonDoc(dmSaveMap);
		} else { // 삭제
			dmSaveMap.put("LAST_MDFR_ID", loginVO.getId());
			docsrMapper.deleteInqDocumet(dmSaveMap);
		}
	}

	@Override
	public Map<String, Object> selectListDocsDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			param.put("LAST_MDFR_ID", loginVO.getId());
		}

		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmDtlParam");

		param.put("INO_DOC_ESNTAL_NO", dtlParam.getValue("INO_DOC_ESNTAL_NO"));

		// 조회 카운트 증가. 수정 필요.
		int nocsCnt = docsrMapper.selectDocsNocsCnt(param);
		if (nocsCnt > 0) { // 이미 조회 한번 했으면 수신 확인일은 업데이트 처리 하면 안됨.
			param.put("NOCS_YN", "Y");
		} else { // 조회 이력이 있다면 해당 조회 Count만 업데이트
			param.put("NOCS_YN", "N");
		}

		docsrMapper.updateDocsCount(param);
		// 수발신함 상세 데이터 호출
		List<Map<String, Object>> DocsDetail = docsrMapper.selectDocsDetail(param);

		result.put("dsList", DocsDetail);

		return result;
	}

	@Override
	public Map<String, Object> excuteCabinetDoc(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSave = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmUpdateMap = new HashMap<>();
		dmUpdateMap.put("INO_DOC_ESNTAL_NO", dmSave.getValue("INO_DOC_ESNTAL_NO"));
		Map<String, Object> targetMap = docsrMapper.selectDocsOrigineDetail(dmUpdateMap);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			targetMap.put("LOGIN_ID", loginVO.getId());
			targetMap.put("CUSTOD_ID", loginVO.getId());
		}

		// 수정 필요함. 타 테이블로 이동 해야함.
		// 내용 그대로 복사 필요.시점 복사,
		if (null == dmSave.getValue("ATFINO")) {
			targetMap.put("ATFINO", "");
		} else {
			targetMap.put("ATFINO", dmSave.getValue("ATFINO"));
		}
		docsrMapper.insertInqDocCabinet(targetMap);

		String rcvrIdArr = (String) targetMap.get("RCVR_ID");
		String[] splitRcvrIdArr = rcvrIdArr.split(",");

		// 보관함 저장일 경우 삭제 하면 안됨.
		// docsrMapper.deleteRcvrId(dmUpdateMap);

		for (int i = 0; i < splitRcvrIdArr.length; i++) {
			Map<String, Object> dmSaveMap2 = new HashMap<>();

			dmSaveMap2.put("INO_DOC_ESNTAL_NO", dmUpdateMap.get("INO_DOC_ESNTAL_NO"));
			dmSaveMap2.put("SNDPTY_ID", dmUpdateMap.get("SNDPTY_ID"));
			dmSaveMap2.put("RCVR_ID", splitRcvrIdArr[i]);
			dmSaveMap2.put("INO_DOC_TTL_NM", dmUpdateMap.get("INO_DOC_TTL_NM"));
			dmSaveMap2.put("SNDPTY_ID", dmUpdateMap.get("SNDPTY_ID"));

			// docsrMapper.insertInqDoc2(dmSaveMap2);
		}
		return result;
	}

	@Override
	public Map<String, Object> selectListDocsCstdyDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			param.put("LAST_MDFR_ID", loginVO.getId());
		}

		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmDtlParam");

		param.put("INO_CSTDY_ESNTAL_NO", dtlParam.getValue("INO_CSTDY_ESNTAL_NO"));
		param.put("INO_DOC_ESNTAL_NO", dtlParam.getValue("INO_DOC_ESNTAL_NO"));

		// 조회 카운트 증가. 수정 필요.
//		int nocsCnt = docsrMapper.selectDocsNocsCnt(param);
//		if (nocsCnt > 0) { //이미 조회 한번 했으면 수신 확인일은 업데이트 처리 하면 안됨.
//			param.put("NOCS_YN", "Y");
//		} else { // 조회 이력이 있다면 해당 조회 Count만 업데이트
//			param.put("NOCS_YN", "N");
//		}
//		
//		docsrMapper.updateDocsCount(param);
		// 수발신함 상세 데이터 호출
		List<Map<String, Object>> DocsDetail = docsrMapper.selectDocsCstdyDetail(param);

		result.put("dsList", DocsDetail);

		return result;
	}

	@Override
	public Map<String, Object> selectListDocsRcvrUsrList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmParam");
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String[] docNo = dmSearchParam.getValues("INO_DOC_ESNTAL_NO");
		paramMap.put("INO_DOC_ESNTAL_NO", docNo[0]);
		paramMap.put("RCPTN_INST_NO", dmSearchParam.getValue("RCPTN_INST_NO"));

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			paramMap.put("LOGIN_ID", loginVO.getId());
		}

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		Integer totalCount = 0;
		List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		paramMap.put("START_IDX", startIndex);
		paramMap.put("ROW_COUNT", rowSize);

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.selectDocsCommonList
		totalCount = docsrMapper.selectListDocsRcvrUsrListTotalCount(paramMap);
		totalList = docsrMapper.selectListDocsRcvrUsrList(paramMap);
		
		//for (Map<String, Object> rowMap : totalList) {
			//if (rowMap.containsKey("RCVR_NM_ENCPT")) {
				//rowMap.replace("RCVR_NM_ENCPT", scpDb.scpDecB64(StringUtil.nullConvert(rowMap.get("RCVR_NM_ENCPT"))));
			//}
		//}


		Map<String, Object> resPage = new HashMap<String, Object>();
		
		resPage.put("totalCount", totalList.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("totalList", totalList);
		result.put("resPage", resPage);

		return result;
	}

	public Map<String, Object> deleteDocsData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> paramMap = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmDtlParam");
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			paramMap.put("LOGIN_ID", loginVO.getId());
		}

		paramMap.put("INO_CSTDY_ESNTAL_NO", dmSearchParam.getValue("INO_CSTDY_ESNTAL_NO"));
		paramMap.put("INO_DOC_ESNTAL_NO", dmSearchParam.getValue("INO_DOC_ESNTAL_NO"));

		// 수신자 목록 지울 필요없어보임. 이력용.
//		docsrMapper.deleteCstdyDocDetail(paramMap);
		docsrMapper.deleteCstdyDoc(paramMap);

		return result;
	}

	public List<Map<String, Object>> selectBizList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			String untTaskwkSeCd = loginVO.getUntTaskwk().toString();
			if (!untTaskwkSeCd.equals("U15")) {
				paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
			} else {
				paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwkSeCd());
			}
			
			//paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		}
		
		log.debug("메뉴단위업무 = " + paramMap.get("UNT_TASKWK_SE_CD"));
		
		return docsrMapper.selectBizList(paramMap);
	}

	public List<Map<String, Object>> selectBizExcuteList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		param.put("SRVC_BIZ_NO", dmSearch.getValue("SRVC_BIZ_NO"));

		return docsrMapper.selectBizExcuteList(param);
	}

	public List<Map<String, Object>> selectBizUsrList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			param.put("INST_NO", loginVO.getInstNo());
		}
		
		param.put("SRVC_BIZ_NO", dmSearch.getValue("SRVC_BIZ_NO"));
		param.put("SRVC_EXCN_BIZ_NO", dmSearch.getValue("SRVC_EXCN_BIZ_NO"));
		//param.put("PIC_NM_ENCPT", scpDb.scpEncB64(dmSearch.getValue("PIC_NM_ENCPT")));
//		param.put("USER_ID", scpDb.scpEncB64(dmSearch.getValue("USER_ID")));
		param.put("USER_ID", dmSearch.getValue("USER_ID"));
		List<Map<String, Object>> list = docsrMapper.selectBizUsrList(param);
		List<Map<String, Object>> list2 = new ArrayList<>();
		//System.out.println(" list ~~~~~~~~~~~~~~~~~:::::: "+ list);
		for (int i = 0; i < list.size(); i++) {

			Map<String, Object> map1 = list.get(i);
			
//			USER_ID
//			FLNM_ENCPT
//			SXDC_SE_CD
//			MBL_TELNO_ENCPT
//			WRD_TELNO
//			EML_ADDR_ENCPT
//			MEMBER_TYPE
//			INST_NM
			
			//if (map1.containsKey("FLNM_ENCPT")) {
				//map1.put("FLNM", Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map1.get("FLNM_ENCPT")))));
			//}
			
//			log.debug("이름 노마스킹 복호화 = " + scpDb.scpDecB64(StringUtil.nullConvert(map1.get("FLNM_ENCPT"))));
//			log.debug("이름 마스킹 복호화 = " + Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map1.get("FLNM_ENCPT")))));
//			log.debug("이름 복호화 = " + map1.get("FLNM"));
			
			//if (map1.containsKey("MBL_TELNO_ENCPT")) {
				//map1.put("MBL_TELNO", Masking.phoneMasking(scpDb.scpDecB64(StringUtil.nullConvert(map1.get("MBL_TELNO_ENCPT")))));
			//}
			
			//if (map1.containsKey("EML_ADDR_ENCPT")) {
				//map1.put("EML_ADDR", Masking.emailMasking(scpDb.scpDecB64(StringUtil.nullConvert(map1.get("EML_ADDR_ENCPT")))));
			//}
			
//			map1.put("FLNM", scpDb.scpDecB64((String) map1.get("FLNM_ENCPT")));
//			map1.put("MBL_TELNO", scpDb.scpDecB64((String) map1.get("MBL_TELNO_ENCPT")));
//			map1.put("EML_ADDR", scpDb.scpDecB64((String) map1.get("EML_ADDR_ENCPT")));
//			map1.put("MSNGR_ID", scpDb.scpDecB64((String) map1.get("MSNGR_ID_ENCPT")));
//			
//			map1.put("SNS_SE_CD", scpDb.scpDecB64((String) map1.get("SNS_SE_CD")));
//			map1.put("SRVC_BIZ_NM", scpDb.scpDecB64((String) map1.get("SRVC_BIZ_NM")));
//			
//			
//			map1.put("INST_NM", scpDb.scpDecB64((String) map1.get("INST_NM")));
//			
//			
//			map1.put("FLNM_MASKING", Masking.nameMasking((String) map1.get("FLNM")));
			
			
			
			
//			String mblTN=   Masking.phoneMasking((String) map1.get("MBL_TELNO"));
//			if (mblTN.equals("") || mblTN.equals("null") || mblTN == null) {
//				mblTN = "";
//			} else {
//				if(mblTN.length() >= 2 ) {
//					if (mblTN.substring(0, 2).contains("02") && mblTN.length() == 9) {
//						
//						mblTN = mblTN.substring(0, 2) + "-" + mblTN.substring(2, 5) + "-" + mblTN.substring(5,9);
//					}else if(mblTN.substring(0, 2).contains("02") && mblTN.length() == 10) {
//						mblTN = mblTN.substring(0, 2) + "-" + mblTN.substring(2, 6) + "-" + mblTN.substring(6,10);
//					}else if(mblTN.length() == 10) {
//						mblTN = mblTN.substring(0, 3) + "-" + mblTN.substring(3, 6) + "-" + mblTN.substring(6,10);
//					}else if(mblTN.length() == 11) {
//						mblTN = mblTN.substring(0, 3) + "-" + mblTN.substring(3, 7) + "-" + mblTN.substring(7,11);
//					}
//				}
//			}
//		
//			
//			map1.put("MBL_TELNO_MASKING", mblTN);
//			
//			
//			String wrdTN=   Masking.phoneMasking((String) map1.get("WRD_TELNO"));
//			if (wrdTN.equals("") || wrdTN.equals("null") || wrdTN == null) {
//				wrdTN = "";
//			} else {
//				if(wrdTN.length() >= 2 ) {
//					if (wrdTN.substring(0, 2).contains("02") && wrdTN.length() == 9) {
//						
//						wrdTN = wrdTN.substring(0, 2) + "-" + wrdTN.substring(2, 5) + "-" + wrdTN.substring(5,9);
//					}else if(wrdTN.substring(0, 2).contains("02") && wrdTN.length() == 10) {
//						wrdTN = wrdTN.substring(0, 2) + "-" + wrdTN.substring(2, 6) + "-" + wrdTN.substring(6,10);
//					}else if(wrdTN.length() == 10) {
//						wrdTN = wrdTN.substring(0, 3) + "-" + wrdTN.substring(3, 6) + "-" + wrdTN.substring(6,10);
//					}else if(wrdTN.length() == 11) {
//						wrdTN = wrdTN.substring(0, 3) + "-" + wrdTN.substring(3, 7) + "-" + wrdTN.substring(7,11);
//					}
//				}
//			}
//		
//			
//			map1.put("WRD_TELNO_MASKING", wrdTN);
//			
//			
//			
//			map1.put("EML_ADDR_MASKING", Masking.emailMasking((String) map1.get("EML_ADDR")));
//			map1.put("BRTH_YMD_MASKING", Masking.birthMasking((String) map1.get("BRTH_YMD")));
////			map1.put("WRD_TELNO_MASKING", Masking.phoneMasking((String) map1.get("WRD_TELNO")));
//			 map1.put("MSNGR_ID_MASKING", map1.get("MSNGR_ID_ENCPT"));
			 
			

			list2.add(map1);
		}

		return list2;
	}

	public Map<String, Object> getUserInstInfo(Map<String, Object> map) throws Exception {

		return docsrMapper.getUserInstInfo(map);
	}

	public Map<String, Object> selectSndptyUserInfo(Map<String, Object> map) throws Exception {

		return docsrMapper.selectSndptyUserInfo(map);
	}

	public Map<String, Object> selectDocsInstInfo(Map<String, Object> map) throws Exception {

		return docsrMapper.selectDocsInstInfo(map);
	}

	public Map<String, Object> selectDocsDsptchInstInfo(Map<String, Object> map) throws Exception {
		
		return docsrMapper.selectDocsDsptchInstInfo(map);
	}

	public void insertInstInfo(Map<String, Object> map) throws Exception {
		docsrMapper.insertInstInfo(map);
	}

	public void updateInstInfo(Map<String, Object> map) throws Exception {
		docsrMapper.updateInstInfo(map);
	}

	/**
	 * @Method명   : selectListDocsRcvrInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectListDocsRcvrInstList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmParam");
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String[] docNo = dmSearchParam.getValues("INO_DOC_ESNTAL_NO");
		paramMap.put("INO_DOC_ESNTAL_NO", docNo[0]);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 수발신자 입력 ID로 변경 NM은 쿼리로 추출.
			paramMap.put("LOGIN_ID", loginVO.getId());
		}
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		Integer totalCount = 0;
		List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		paramMap.put("START_IDX", startIndex);
		paramMap.put("ROW_COUNT", rowSize);

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.selectDocsCommonList
		totalCount = docsrMapper.selectListDocsRcvrInstListTotalCount(paramMap);
		totalList = docsrMapper.selectListDocsRcvrInstList(paramMap);
		
//		for (Map<String, Object> rowMap : totalList) {
//			
//			if (rowMap.containsKey("RCVR_NM_ENCPT")) {
//				rowMap.replace("RCVR_NM_ENCPT", scpDb.scpDecB64(StringUtil.nullConvert(rowMap.get("RCVR_NM_ENCPT"))));
//			}	
//			
//		}


		Map<String, Object> resPage = new HashMap<String, Object>();
		
		resPage.put("totalCount", totalList.size());
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		result.put("totalList", totalList);
		result.put("resPage", resPage);

		return result;
			
	}

	/**
	 * @Method명   : updateRcptnInstPrslInqDoc
	 * @param dmUpdateMap
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	@Override
	public void updateRcptnInstPrslInqDoc(Map<String, Object> dmUpdateMap) throws Exception {
		// 수신자 목록 존재 여부 확인
		int val = docsrMapper.selectRcptnInstRcvrDocumentMatch(dmUpdateMap);

		if (val > 0) {
			// 수신 당사자 일 경우 조회 일자와 count 갯수 추가.
			docsrMapper.updateRcptnInstPrslInqDoc(dmUpdateMap);
			docsrMapper.updatePrslInqDoc(dmUpdateMap);
		}
		
	}

	/**
	 * @Method명   : selectOffcsAtfinoInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 12. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectOffcsAtfinoInfo(HttpServletRequest request, DataRequest dataRequest, Map<String, String> dmDocDtl) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDocDtl");
		//dmSave.equals(dmDocDtl);
		
		param.put("OFFCS_SGNNG_ATFINO", dmDocDtl.get("OFFCS_SGNNG_ATFINO"));
		param.put("OFFCS_SGNNG_ATCMFL_MNG_NO", dmDocDtl.get("OFFCS_SGNNG_ATCMFL_MNG_NO"));
		
		//return docsrMapper.selectOffcsAtfinoPath(param);
		return docsrMapper.selectOffcsAtfinoPath(param);
	}
}
