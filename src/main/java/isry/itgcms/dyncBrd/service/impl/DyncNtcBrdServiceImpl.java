/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.dyncBrd.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.itgcms.dyncBrd.mapper.DyncBrdCmnMapper;
import isry.itgcms.dyncBrd.mapper.DyncNtcBrdMapper;
import isry.itgcms.dyncBrd.service.DyncNtcBrdService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.UserException;
import isry.itgcms.util.service.ArticleCheckService;

/**
 * @파일명 : DyncNtcBrdServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2021. 12. 20.
 * @수정자 : You Minsang
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */

@Service
public class DyncNtcBrdServiceImpl implements DyncNtcBrdService {

	@Resource(name = "dyncNtcBrdMapper")
	private DyncNtcBrdMapper dyncNtcBrdMapper;

	@Resource(name = "dyncBrdCmnMapper")
	private DyncBrdCmnMapper dyncBrdCmnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Autowired
	private ApplicationContext context;

	/**
	 * @Method명 : selectSampleBoardList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 1. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSampleBoardList(Map<String, Object> mapParam) throws Exception {

		List<Map<String, Object>> list = dyncNtcBrdMapper.selectSampleBoardList(mapParam);

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String FRST_RGTR_NM = (String) map.get("FRST_RGTR_NM");

			if (FRST_RGTR_NM != null && !"".equals(FRST_RGTR_NM)) {
				FRST_RGTR_NM = Masking.nameMasking(FRST_RGTR_NM);
			}
			map.put("FRST_RGTR_NM", FRST_RGTR_NM);

			list.set(i, map);
		}

		return list;

	}

	/**
	 * @Method명 : deleteSampleBoardList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 6. 13.
	 * @Method설명 :
	 */
	@Override
	public void deleteMstSampleBoardList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);

			// 게시판 추가 컬럼 데이터 삭제
			dyncBrdCmnMapper.deleteDyncBrdCmnColDataList(mapDel);

			// 게시판 기본 데이터 삭제
			dyncBrdCmnMapper.deleteDyncBrdCmnDtlList(mapDel);

		}

	}

	/**
	 * @Method명 : saveBoardList
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2021. 12. 29.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveDyncNtcBrdList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardDtlList");

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();

		if (insertedRows.hasNext()) {
			ArticleCheckService articleCheckService = (ArticleCheckService) context.getBean("articleCheckService");
			if (articleCheckService.checkDuplicateArticleRegist("saveDyncNtcBrdList", request) > 0) {
				throw new UserException("errors.preventDuplicateArticleRegist");
			}
		}

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			dyncBrdCmnMapper.insertDyncBrdCmnDtlList(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("NTABRD_ESNTAL_NO", mapIns.get("NTABRD_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			dyncBrdCmnMapper.updateDyncBrdCmnDtlList(mapUpd);

			mapReturn.put("NTABRD_ESNTAL_NO", mapUpd.get("NTABRD_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			dyncBrdCmnMapper.deleteDyncBrdCmnDtlList(mapDel);

		}

		// 동적 처리를 하는 Col Data의 데이터를 저장한다.
		ParameterGroup dsBoardColDataList = dataRequest.getParameterGroup("dsBoardDtlColDataList");

		Iterator<ParameterRow> insertedColDataRows = dsBoardColDataList.getInsertedRows();
		Iterator<ParameterRow> updatedColDataRows = dsBoardColDataList.getUpdatedRows();
		Iterator<ParameterRow> deletedColDataRows = dsBoardColDataList.getDeletedRows();

		while (insertedColDataRows.hasNext()) {

			// 게시판-컬럼별 내용 추가
			Map<String, String> mapIns = insertedColDataRows.next().toMap();

			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);

			// 신규 게시글 생성 컬럼 데이터 추가시
			if (mapIns.get("BBSCTT_ESNTAL_NO").equals("")) {
				String brdSeq = mapReturn.get("BBSCTT_ESNTAL_NO").toString();
				mapIns.put("BBSCTT_ESNTAL_NO", brdSeq);
			}

			dyncBrdCmnMapper.insertDyncBrdCmnColDataList(mapIns);

			mapReturn.put("NTABRD_ESNTAL_NO", mapIns.get("NTABRD_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedColDataRows.hasNext()) {

			// 게시판-컬럼별 내용 추가
			Map<String, String> mapUpd = updatedColDataRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			dyncBrdCmnMapper.updateDyncBrdCmnColDataList(mapUpd);

			mapReturn.put("NTABRD_ESNTAL_NO", mapUpd.get("NTABRD_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedColDataRows.hasNext()) {

			Map<String, String> mapDel = deletedColDataRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);

			// 게시판 추가 컬럼 데이터 삭제
			dyncBrdCmnMapper.deleteDyncBrdCmnColDataList(mapDel);
		}

		return mapReturn;
	}

}
