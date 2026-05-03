/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.service.impl;

import java.util.ArrayList;
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

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.mapper.CscAltmntMapper;
import isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.service.CscAltmntService;

/**
 * @파일명        : CscAltmntServiceImpl.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 7. 22.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 7. 22.
 * @수정내용      :
 * -
 * -
 */
@Service("cscAltmntService")
public class CscAltmntServiceImpl extends IsryBaseServiceImpl implements CscAltmntService {


	@Resource(name = "cscAltmntMapper")
	private CscAltmntMapper cscAltmntMapper;

	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 22.
	 * @Method설명 :
	 */
	@Override
	public String selectTaskwkSeCd(Map<String, Object> requestMap) throws Exception {
		// TODO Auto-generated method stub
		return cscAltmntMapper.selectTaskwkSeCd(requestMap);
	}

	/**
	 * @Method명   : getCscListTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25.
	 * @Method설명 :
	 */
	@Override
	public int getCscListTotalCount(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cscAltmntMapper.getCscListTotalCount(mapParam);
	}

	/**
	 * @Method명   : selectCscList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCscList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cscAltmntMapper.selectCscList(mapParam);
	}

	/**
	 * @Method명   : selectRsvctmList
	 * @param mapDate
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectRsvctmList(Map<String, Object> mapDate) throws Exception {
		// TODO Auto-generated method stub

		List<Map<String, String>> mapList = cscAltmntMapper.selectRsvctmList(mapDate);


		for (Map<String, String> map : mapList) {

			// 화면용 사용자 이름 암복호화 > 예약자명
			map.put("RSVCTM", map.get("FLNM_ENCPT"));
		}


		return mapList;
	}

	/**
	 * @Method명   : saveCscDetail
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 27.
	 * @Method설명 :
	 */
	@Override
	public void saveCscDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		// 화면 상태코드(s:상세, i:등록, u:수정, d:삭제)
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");
		ParameterGroup dmInitInfoParam = dataRequest.getParameterGroup("dmInitInfo");

		Iterator<ParameterRow> insertedDsList = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedDsList  = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedDsList  = dsList.getDeletedRows();



		while (insertedDsList.hasNext()) {

			Map<String, String> mapIns = insertedDsList.next().toMap();

//			log.debug("insertedDsList INST_NO=[" + mapIns.get("INST_NO") + "]");
//			log.debug("insertedDsList USER_ID=[" + mapIns.get("USER_ID") + "]");
//			log.debug("insertedDsList TASKWK_SYS_SE_CD=[" + mapIns.get("TASKWK_SYS_SE_CD") + "]");
//			log.debug("insertedDsList CSC_ESNTAL_NO=[" + mapIns.get("CSC_ESNTAL_NO") + "]");
//			log.debug("insertedDsList CSC_NM=[" + mapIns.get("CSC_NM") + "]");
//			log.debug("insertedDsList USE_YN=[" + mapIns.get("USE_YN") + "]");

			// 상담실 정보 등록 > 일부 쿼리 처리
			mapIns.put("INST_NO",   mapIns.get("INST_NO"));												// 기관번호
//			mapIns.put("CSC_ESNTAL_NO", mapIns.get("CSC_ESNTAL_NO"));             						// 상담실고유번호 > 쿼리 처리
			mapIns.put("TASKWK_SYS_SE_CD",   mapIns.get("TASKWK_SYS_SE_CD"));							// TASKWK_SYS_SE_CD 업무시스템구분코드
			mapIns.put("USER_ID",   mapIns.get("USER_ID"));												// 사용자 아이디
			mapIns.put("CSC_NM",   mapIns.get("CSC_NM"));												// 상담실명
			mapIns.put("USE_YN",   mapIns.get("USE_YN"));												// 사용여부
			mapIns.put("FRST_RGTR_ID",   mapIns.get("USER_ID"));										// 최초등록자아이디
//			mapIns.put("FRST_REG_DT",   mapIns.get("FRST_REG_DT"));										// 최초등록일시 > 쿼리 처리
			mapIns.put("LAST_MDFR_ID",   mapIns.get("USER_ID"));										// 최종수정자아이디
//			mapIns.put("LAST_MDFCN_DT",   mapIns.get("FRST_REG_DT"));									// 최종수정일시 > 쿼리 처리

			cscAltmntMapper.insertCscDetail(mapIns);
		}

		while (updatedDsList.hasNext()) {
			Map<String, String> mapUpd = updatedDsList.next().toMap();

//			log.debug("updatedDsList INST_NO=[" + mapUpd.get("INST_NO") + "]");
//			log.debug("updatedDsList USER_ID=[" + mapUpd.get("USER_ID") + "]");
//			log.debug("updatedDsList CSC_ESNTAL_NO=[" + mapUpd.get("CSC_ESNTAL_NO") + "]");
//			log.debug("updatedDsList CSC_ESNTAL_NO=[" + mapUpd.get("CSC_NM") + "]");
//			log.debug("updatedDsList USE_YN=[" + mapUpd.get("USE_YN") + "]");
//			log.debug("updatedDsList LAST_MDFR_ID=[" + mapUpd.get("LAST_MDFR_ID") + "]");

			// update 경우 로그인 사용자 아이디 적용
			mapUpd.put("LAST_MDFR_ID",   dmInitInfoParam.getValue("USER_ID"));

			cscAltmntMapper.updateCscDetail(mapUpd);

		}


		while (deletedDsList.hasNext()) {
			Map<String, String> mapDel = deletedDsList.next().toMap();

//			log.debug("deletedDsList INST_NO=[" + mapDel.get("INST_NO") + "]");
//			log.debug("deletedDsList USER_ID=[" + mapDel.get("USER_ID") + "]");
//			log.debug("deletedDsList CSC_ESNTAL_NO=[" + mapDel.get("CSC_ESNTAL_NO") + "]");
//			log.debug("deletedDsList CSC_ESNTAL_NO=[" + mapDel.get("CSC_NM") + "]");
//			log.debug("deletedDsList USE_YN=[" + mapDel.get("USE_YN") + "]");
//			log.debug("deletedDsList LAST_MDFR_ID=[" + mapDel.get("LAST_MDFR_ID") + "]");

			// update 경우 로그인 사용자 아이디 적용
			mapDel.put("LAST_MDFR_ID",   dmInitInfoParam.getValue("USER_ID"));
			cscAltmntMapper.deleteCscDetail(mapDel);

		}



	}

	/**
	 * @Method명   : selectCscDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCscDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 사용자 정보
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");
		mapParam.put("INST_NO", dmDetailParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDetailParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", dmDetailParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("CSC_ESNTAL_NO", dmDetailParam.getValue("CSC_ESNTAL_NO"));


		// 상담실 상세
		List<Map<String, Object>> mapList = cscAltmntMapper.selectCscDetail(mapParam);


		return mapList;
	}

	/**
	 * @Method명   : selectCscListUseY
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCscListUseY(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cscAltmntMapper.selectCscListUseY(mapParam);
	}

	/**
	 * @Method명   : saveCscAltmntDetail
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29.
	 * @Method설명 :
	 */
	@Override
	public void saveCscAltmntDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		ParameterGroup dsList = dataRequest.getParameterGroup("dsFrom");
		// 화면 상태코드(s:상세, i:등록, u:수정, d:삭제)
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");
		ParameterGroup dmInitInfoParam = dataRequest.getParameterGroup("dmInitInfo");

		Iterator<ParameterRow> insertedDsList = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedDsList  = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedDsList  = dsList.getDeletedRows();



		while (insertedDsList.hasNext()) {

			Map<String, String> mapIns = insertedDsList.next().toMap();

//			log.debug("insertedDsList INST_NO=[" + mapIns.get("INST_NO") + "]");
//			log.debug("insertedDsList CSC_ESNTAL_NO=[" + mapIns.get("CSC_ESNTAL_NO") + "]");
//			log.debug("insertedDsList INDEX_SN=[" + mapIns.get("INDEX_SN") + "]");
//			log.debug("insertedDsList TASKWK_SYS_SE_CD=[" + mapIns.get("TASKWK_SYS_SE_CD") + "]");
//			log.debug("insertedDsList USER_ID=[" + mapIns.get("USER_ID") + "]");
//			log.debug("insertedDsList CSC_NM=[" + mapIns.get("CSC_NM") + "]");
//			log.debug("insertedDsList RSVT_YMD=[" + mapIns.get("RSVT_YMD") + "]");
//			log.debug("insertedDsList RSVT_BGNG_HR=[" + mapIns.get("RSVT_BGNG_HR") + "]");
//			log.debug("insertedDsList RSVT_END_HR=[" + mapIns.get("RSVT_END_HR") + "]");
//			log.debug("insertedDsList RSVCTM_ID=[" + mapIns.get("RSVCTM_ID") + "]");
//			log.debug("insertedDsList RSVCTM_NM_ENCPT=[" + mapIns.get("RSVCTM_NM_ENCPT") + "]");
//			log.debug("insertedDsList RSVCTM=[" + mapIns.get("RSVCTM") + "]");
//			log.debug("insertedDsList RM_CN=[" + mapIns.get("RM_CN") + "]");
//			log.debug("insertedDsList REG_YMD=[" + mapIns.get("REG_YMD") + "]");
			// 상담실 배정 등록 > 일부 쿼리 처리
			mapIns.put("INST_NO",   mapIns.get("INST_NO"));												// 기관번호
			mapIns.put("CSC_ESNTAL_NO", mapIns.get("CSC_ESNTAL_NO"));             						// 상담실고유번호
			mapIns.put("INDEX_SN", mapIns.get("INDEX_SN"));             								// 색인일련번호(예약관리번호) > 쿼리 처리
			mapIns.put("TASKWK_SYS_SE_CD",   mapIns.get("TASKWK_SYS_SE_CD"));							// TASKWK_SYS_SE_CD 업무시스템구분코드
			mapIns.put("USER_ID",   mapIns.get("USER_ID"));												// 사용자 아이디
			mapIns.put("CSC_NM",   mapIns.get("CSC_NM"));												// 상담실명
			mapIns.put("RSVT_YMD",   mapIns.get("RSVT_YMD"));											// 예약일자
			mapIns.put("RSVT_BGNG_HR",   mapIns.get("RSVT_BGNG_HR"));									// 예약시작시간
			mapIns.put("RSVT_END_HR",   mapIns.get("RSVT_END_HR"));										// 예약종료시간
			mapIns.put("RSVCTM_ID",   mapIns.get("RSVCTM_ID"));											// 예약자 아이디
//			mapIns.put("RSVCTM_NM",   mapIns.get("RSVCTM_NM"));											// 예약자명
			mapIns.put("RSVCTM_NM_ENCPT",   mapIns.get("RSVCTM"));					// 예약자명암호화
			mapIns.put("RM_CN",   mapIns.get("RM_CN"));													// 비고
			mapIns.put("REG_YMD",   mapIns.get("REG_YMD"));												// 등록일자 > 쿼리 처리
//			mapIns.put("DEL_YN",   mapIns.get("DEL_YN"));												// 삭제여부 > 쿼리 처리
			mapIns.put("FRST_RGTR_ID",   mapIns.get("USER_ID"));										// 최초등록자아이디
//			mapIns.put("FRST_REG_DT",   mapIns.get("FRST_REG_DT"));										// 최초등록일시 > 쿼리 처리
			mapIns.put("LAST_MDFR_ID",   mapIns.get("USER_ID"));										// 최종수정자아이디
//			mapIns.put("LAST_MDFCN_DT",   mapIns.get("FRST_REG_DT"));									// 최종수정일시 > 쿼리 처리

			cscAltmntMapper.insertCscAltmntDetail(mapIns);
		}

		while (updatedDsList.hasNext()) {
			Map<String, String> mapUpd = updatedDsList.next().toMap();

//			log.debug("updatedDsList INST_NO=[" + mapUpd.get("INST_NO") + "]");
//			log.debug("updatedDsList CSC_ESNTAL_NO=[" + mapUpd.get("CSC_ESNTAL_NO") + "]");
//			log.debug("updatedDsList INDEX_SN=[" + mapUpd.get("INDEX_SN") + "]");
//			log.debug("updatedDsList TASKWK_SYS_SE_CD=[" + mapUpd.get("TASKWK_SYS_SE_CD") + "]");
//			log.debug("updatedDsList USER_ID=[" + mapUpd.get("USER_ID") + "]");
//			log.debug("updatedDsList CSC_NM=[" + mapUpd.get("CSC_NM") + "]");
//			log.debug("updatedDsList RSVT_YMD=[" + mapUpd.get("RSVT_YMD") + "]");
//			log.debug("updatedDsList RSVT_BGNG_HR=[" + mapUpd.get("RSVT_BGNG_HR") + "]");
//			log.debug("updatedDsList RSVT_END_HR=[" + mapUpd.get("RSVT_END_HR") + "]");
//			log.debug("updatedDsList RSVCTM_ID=[" + mapUpd.get("RSVCTM_ID") + "]");
//			log.debug("updatedDsList RSVCTM_NM_ENCPT=[" + mapUpd.get("RSVCTM_NM_ENCPT") + "]");
//			log.debug("updatedDsList RSVCTM=[" + mapUpd.get("RSVCTM") + "]");
//			log.debug("updatedDsList RM_CN=[" + mapUpd.get("RM_CN") + "]");
//			log.debug("updatedDsList REG_YMD=[" + mapUpd.get("REG_YMD") + "]");
//			log.debug("updatedDsList DPCN_CHECK_START_TIME=[" + mapUpd.get("DPCN_CHECK_START_TIME") + "]");


			// 상담실 배정 수정 > 일부 쿼리 처리

			mapUpd.put("RSVCTM_NM_ENCPT",   mapUpd.get("RSVCTM"));					// 예약자명암호화

			// update 경우 로그인 사용자 아이디 적용
			mapUpd.put("LAST_MDFR_ID",   dmInitInfoParam.getValue("USER_ID"));

			cscAltmntMapper.updateCscAltmntDetail(mapUpd);

		}


		while (deletedDsList.hasNext()) {
			Map<String, String> mapDel = deletedDsList.next().toMap();

//			log.debug("deletedDsList INST_NO=[" + mapDel.get("INST_NO") + "]");
//			log.debug("deletedDsList CSC_ESNTAL_NO=[" + mapDel.get("CSC_ESNTAL_NO") + "]");
//			log.debug("deletedDsList INDEX_SN=[" + mapDel.get("INDEX_SN") + "]");
//			log.debug("deletedDsList TASKWK_SYS_SE_CD=[" + mapDel.get("TASKWK_SYS_SE_CD") + "]");
//			log.debug("deletedDsList USER_ID=[" + mapDel.get("USER_ID") + "]");
//			log.debug("deletedDsList CSC_NM=[" + mapDel.get("CSC_NM") + "]");
//			log.debug("deletedDsList RSVT_YMD=[" + mapDel.get("RSVT_YMD") + "]");
//			log.debug("deletedDsList RSVT_BGNG_HR=[" + mapDel.get("RSVT_BGNG_HR") + "]");
//			log.debug("deletedDsList RSVT_END_HR=[" + mapDel.get("RSVT_END_HR") + "]");
//			log.debug("deletedDsList RSVCTM_ID=[" + mapDel.get("RSVCTM_ID") + "]");
//			log.debug("deletedDsList RSVCTM_NM_ENCPT=[" + mapDel.get("RSVCTM_NM_ENCPT") + "]");
//			log.debug("deletedDsList RSVCTM=[" + mapDel.get("RSVCTM") + "]");
//			log.debug("deletedDsList RM_CN=[" + mapDel.get("RM_CN") + "]");
//			log.debug("deletedDsList DPCN_CHECK_START_TIME=[" + mapDel.get("DPCN_CHECK_START_TIME") + "]");

			// update 경우 로그인 사용자 아이디 적용
			mapDel.put("LAST_MDFR_ID",   dmInitInfoParam.getValue("USER_ID"));


			cscAltmntMapper.deleteCscAltmntDetail(mapDel);

		}

	}




	/**
	 * @Method명   : getCscAltmntRsvtListTotalCount
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29.
	 * @Method설명 :
	 */
	@Override
	public int getCscAltmntRsvtListTotalCount(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup dsFromParam = dataRequest.getParameterGroup("dsFrom");
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("INST_NO", dsFromParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dsFromParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", dsFromParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("CSC_ESNTAL_NO", dsFromParam.getValue("CSC_ESNTAL_NO"));
//		mapParam.put("RSVT_YMD", dsFromParam.getValue("RSVT_YMD"));
		mapParam.put("SEARCH_DATE", dmSearchParam.getValue("SEARCH_DATE"));


//		log.debug("getCscAltmntRsvtListTotalCount INST_NO=[" + dsFromParam.getValue("INST_NO") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount USER_ID=[" + dsFromParam.getValue("USER_ID") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount TASKWK_SYS_SE_CD=[" + dsFromParam.getValue("TASKWK_SYS_SE_CD") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount CSC_ESNTAL_NO=[" + dsFromParam.getValue("CSC_ESNTAL_NO") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount SEARCH_DATE=[" + dmSearchParam.getValue("SEARCH_DATE") + "]");


		return cscAltmntMapper.getCscAltmntRsvtListTotalCount(mapParam);
	}

	/**
	 * @Method명   : checkRsvtHrDpcn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29.
	 * @Method설명 :
	 */
	@Override
	public String checkRsvtHrDpcn(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub



		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup dsFromParam = dataRequest.getParameterGroup("dsFrom");
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("INST_NO", dsFromParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dsFromParam.getValue("USER_ID"));
		mapParam.put("CSC_ESNTAL_NO", dsFromParam.getValue("CSC_ESNTAL_NO"));
		mapParam.put("RSVT_BGNG_HR", dsFromParam.getValue("RSVT_BGNG_HR"));
		mapParam.put("RSVT_END_HR", dsFromParam.getValue("RSVT_END_HR"));

		mapParam.put("SEARCH_DATE", dmSearchParam.getValue("SEARCH_DATE"));
//		mapParam.put("SEARCH_DATE", "20220801");

//		log.debug("checkRsvtHrDpcn INST_NO=[" + dsFromParam.getValue("INST_NO") + "]");
//		log.debug("checkRsvtHrDpcn USER_ID=[" + dsFromParam.getValue("USER_ID") + "]");
//		log.debug("checkRsvtHrDpcn CSC_ESNTAL_NO=[" + dsFromParam.getValue("CSC_ESNTAL_NO") + "]");
//		log.debug("checkRsvtHrDpcn SEARCH_DATE=[" + dmSearchParam.getValue("SEARCH_DATE") + "]");
//		log.debug("checkRsvtHrDpcn DPCN_CHECK_START_TIME=[" + dsFromParam.getValue("DPCN_CHECK_START_TIME") + "]");
//		log.debug("checkRsvtHrDpcn dmSearchParam=[" + dmSearchParam.getValue("DPCN_CHECK_TYPE") + "]");

		// 상담실배정 예약 현황 조회
		List<Map<String, Object>> dpcnList = new ArrayList<Map<String,Object>>();

		String str = dmSearchParam.getValue("DPCN_CHECK_TYPE");

		if(str.equals("I")) {

			dpcnList = cscAltmntMapper.getAltmntRsvtDpcnItypeList(mapParam);
		}else if(str.equals("U")) {

			mapParam.put("DPCN_CHECK_START_TIME", dsFromParam.getValue("DPCN_CHECK_START_TIME"));
			dpcnList = cscAltmntMapper.getAltmntRsvtDpcnUtypeList(mapParam);
		}


		String resultDpcnYn = "";

		if(dpcnList.size() == 0) {
			resultDpcnYn = "N";
		}else if(dpcnList.size() > 0) {

			int startTime = Integer.parseInt(dsFromParam.getValue("RSVT_BGNG_HR"));
			int endTime = Integer.parseInt(dsFromParam.getValue("RSVT_END_HR"));


			for (int i = 0; i < dpcnList.size(); i ++) {

				int chkStartTime = Integer.parseInt(dpcnList.get(i).get("RSVT_BGNG_HR").toString());
				int chkEndTime = Integer.parseInt(dpcnList.get(i).get("RSVT_END_HR").toString());


				// 체크 케이스
				// 1. 시작 혹은 종료시간이 이미 예약되어있는 시간범위에 둘중하나라도 겹침
				// 2. 시작 혹은 종료 시간이 이미 예약되어있는 시간범위 안에 포함 겹침
				// 3. 시작 혹은 종료 시간이 이미 예약되어있는 시간범위를 감싼 포함 겹침
				if(startTime <= chkEndTime && chkStartTime <= endTime) {

					// 중복
					resultDpcnYn = "Y";
					break;

				}else {

					// 중복 아님
					resultDpcnYn = "N";

				}


			}



		}


		return resultDpcnYn;
	}

	/**
	 * @Method명   : selectCscAltmntPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 2.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCscAltmntPreconList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub


		// 상담실 데이터 호출(select box 용)
		List<Map<String, Object>> cscList = cscAltmntMapper.selectCscListUseY(mapParam);

		// 상담실별 예약 현황 조회
		List<Map<String, Object>> cscAltmntRsvtList = cscAltmntMapper.selectCscAltmntRsvtList(mapParam);


		// 상담실배정현황 데이터 리스트 생성
		List<Map<String, Object>> cscAltmntPreconList = new ArrayList<Map<String,Object>>();

		for(int i = 0; i < cscList.size(); i ++) {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("CSC_ESNTAL_NO", cscList.get(i).get("CSC_ESNTAL_NO"));
			map.put("CSC_NM", cscList.get(i).get("CSC_NM"));


			for(int h = 9; h <=21; h ++) {
//
				String sh = "";

				if(h < 10) {
//					map.put("0" + h + "00", "0" + h + "00");
					map.put("C" + "0" + h + "00", "C" + "0" + h + "00");
					sh = "C" + "0" + Integer.toString(h);
				}else {
//					map.put(h + "00", h + "00");
					map.put("C" + h + "00", "C" + h + "00");
					sh = "C" + Integer.toString(h);
				}


				for(int m = 0; m < 6; m ++) {
//					minute[m] = m * 10;

					if(m == 0) {
//						map.put(sh + "0" + m * 10, sh + "0" + m * 10);
						map.put(sh + "0" + m * 10, sh + "0" + m * 10);
					} else {
//						map.put(sh + m * 10, sh + m * 10);
						if(h < 21) {
							map.put(sh + m * 10, sh + m * 10);
						}

					}

				}

			}


			cscAltmntPreconList.add(map);

		}


		for(int c = 0; c < cscAltmntRsvtList.size(); c ++) {

			String c1 = cscAltmntRsvtList.get(c).get("CSC_ESNTAL_NO").toString();

			for(int r = 0; r < cscAltmntPreconList.size(); r ++) {

				String c2 = cscAltmntPreconList.get(r).get("CSC_ESNTAL_NO").toString();

				if(c1.equals(c2) ) {

					cscAltmntPreconList.get(r).put("C" + cscAltmntRsvtList.get(c).get("RSVT_BGNG_HR").toString(), "S" + cscAltmntRsvtList.get(c).get("RSVT_BGNG_HR").toString());
					cscAltmntPreconList.get(r).put("C" + cscAltmntRsvtList.get(c).get("RSVT_END_HR").toString(), "E" + cscAltmntRsvtList.get(c).get("RSVT_END_HR").toString());



				}

			}

		}


		return cscAltmntPreconList;
	}

	/**
	 * @Method명   : selectCcAltmntRsvtList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 3.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCscAltmntRsvtList(Map<String, Object> mapParam) throws Exception {
		return cscAltmntMapper.selectCscAltmntRsvtList(mapParam);
	}

	/**
	 * @Method명   : selectedCscAltmntRsvtSearchList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectedCscAltmntRsvtSearchList(Map<String, Object> mapParam) throws Exception {
		return cscAltmntMapper.selectedCscAltmntRsvtSearchList(mapParam);
	}

	/**
	 * @Method명   : selectedCscAltmntPreconSearchList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectedCscAltmntPreconSearchList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub



		// 상담실별 예약 현황 조회
		List<Map<String, Object>> cscAltmntRsvtList = cscAltmntMapper.selectedCscAltmntRsvtSearchList(mapParam);



		// 상담실배정현황 데이터 리스트 생성
		List<Map<String, Object>> cscAltmntPreconList = new ArrayList<Map<String,Object>>();

		//for(int i = 0; i < cscAltmntRsvtList.size(); i ++) {

			Map<String, Object> map = new HashMap<String, Object>();


			if(cscAltmntRsvtList.size() == 0) {
				map.put("CSC_ESNTAL_NO", mapParam.get("CSC_ESNTAL_NO"));
				map.put("CSC_NM", mapParam.get("CSC_NM"));
			}else {
				map.put("CSC_ESNTAL_NO", cscAltmntRsvtList.get(0).get("CSC_ESNTAL_NO"));
				map.put("CSC_NM", cscAltmntRsvtList.get(0).get("CSC_NM"));
			}



			for(int h = 9; h <=21; h ++) {
//
				String sh = "";

				if(h < 10) {
//							map.put("0" + h + "00", "0" + h + "00");
					map.put("C" + "0" + h + "00", "C" + "0" + h + "00");
					sh = "C" + "0" + Integer.toString(h);
				}else {
//							map.put(h + "00", h + "00");
					map.put("C" + h + "00", "C" + h + "00");
					sh = "C" + Integer.toString(h);
				}


				for(int m = 0; m < 6; m ++) {
//							minute[m] = m * 10;

					if(m == 0) {
//								map.put(sh + "0" + m * 10, sh + "0" + m * 10);
						map.put(sh + "0" + m * 10, sh + "0" + m * 10);
					} else {
//								map.put(sh + m * 10, sh + m * 10);

						if(h < 21) {
							map.put(sh + m * 10, sh + m * 10);
						}

					}

				}



			}

			cscAltmntPreconList.add(map);


		//}



		if(cscAltmntRsvtList.size() > 0) {
			for(int c = 0; c < cscAltmntRsvtList.size(); c ++) {

				String c1 = cscAltmntRsvtList.get(c).get("CSC_ESNTAL_NO").toString();

				for(int r = 0; r < cscAltmntPreconList.size(); r ++) {

					String c2 = cscAltmntPreconList.get(r).get("CSC_ESNTAL_NO").toString();

					if(c1.equals(c2) ) {

						cscAltmntPreconList.get(r).put("C" + cscAltmntRsvtList.get(c).get("RSVT_BGNG_HR").toString(), "S" + cscAltmntRsvtList.get(c).get("RSVT_BGNG_HR").toString());
						cscAltmntPreconList.get(r).put("C" + cscAltmntRsvtList.get(c).get("RSVT_END_HR").toString(), "E" + cscAltmntRsvtList.get(c).get("RSVT_END_HR").toString());



					}

				}

			}
		}



		return cscAltmntPreconList;


	}



	/**
	 * @Method명   : selectDateWeeklyList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 8.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDateWeeklyList(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub



		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		ParameterGroup userInfoParam = dataRequest.getParameterGroup("dmInitInfo");
		mapParam.put("INST_NO", userInfoParam.getValue("INST_NO"));
		mapParam.put("USER_ID", userInfoParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", userInfoParam.getValue("TASKWK_SYS_SE_CD"));



		mapParam.put("SEARCH_DATE", searchParam.getValue("SEARCH_DATE"));

		int dayOfWeekNum = cscAltmntMapper.selectDayOfWeek(mapParam);
		mapParam.put("DAYOFWEEK", dayOfWeekNum);


		int prevDay = 0;
		int nextDay = 0;

		if(dayOfWeekNum == 1) {
			//일요일
			prevDay = 0;
			nextDay = 6;
		}else if(dayOfWeekNum == 2) {
			//월요일
			prevDay = 1;
			nextDay = 5;
		}else if(dayOfWeekNum == 3) {
			//화요일
			prevDay = 2;
			nextDay = 4;
		}else if(dayOfWeekNum == 4) {
			//수요일
			prevDay = 3;
			nextDay = 3;
		}else if(dayOfWeekNum == 5) {
			//목요일
			prevDay = 4;
			nextDay = 2;
		}else if(dayOfWeekNum == 6) {
			//금요일
			prevDay = 5;
			nextDay = 1;
		}else if(dayOfWeekNum == 7) {
			//토요일
			prevDay = 6;
			nextDay = 0;
		}


		mapParam.put("PREV_DAY", String.valueOf(prevDay));
		mapParam.put("NEXT_DAY", String.valueOf(nextDay));

		return cscAltmntMapper.selectDateWeeklyList(mapParam);
	}

	/**
	 * @Method명   : selectCscAltmntDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 9.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCscAltmntDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");
		mapParam.put("INST_NO", dmDetailParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDetailParam.getValue("USER_ID"));
		mapParam.put("TASKWK_SYS_SE_CD", dmDetailParam.getValue("TASKWK_SYS_SE_CD"));
		mapParam.put("CSC_ESNTAL_NO", dmDetailParam.getValue("CSC_ESNTAL_NO"));
		mapParam.put("INDEX_SN", dmDetailParam.getValue("INDEX_SN"));
		mapParam.put("SEARCH_DATE", searchParam.getValue("SEARCH_DATE"));

		return cscAltmntMapper.selectCscAltmntDetail(mapParam);
	}




}
