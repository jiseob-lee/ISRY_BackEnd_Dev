/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.couns.constt.chttmng.mapper.InqCnsltntListMapper;
import isry.couns.constt.chttmng.service.InqCnsltntListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Service("InqCnsltntListService")
public class InqCnsltntListServiceImpl implements InqCnsltntListService{
	/**
	 * @파일명        : SpclaServiceImpl.java
	 * @프로그램 설명 :
	 * - 
	 * - 
	 * @작성자        : Song.Young.Il
	 * @작성일        : 2022. 5. 4. 
	 * @수정자        : Song.Young.Il
	 * @수정일        : 2022. 5. 4.
	 * @수정내용      : 
	 * -                
	 * -                
	 */

	@Resource(name = "InqCnsltntListMapper")
	private InqCnsltntListMapper inqCnsltntListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectInqCnsltntList(Map<String, Object> mapParam) {
		
		return inqCnsltntListMapper.selectInqCnsltntList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectInqCnsltntDetail(Map<String, Object> mapParam) {

		return inqCnsltntListMapper.selectInqCnsltntDetail(mapParam);
	}

	@Override
	public Map<String, Object> saveInqCnsltntList(HttpServletRequest request, DataRequest dataRequest) {

		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
//		System.out.println("dsList = "+dsList);
		Iterator<ParameterRow> insertedRows = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			inqCnsltntListMapper.insertCnsltnt(mapIns);
			// 게시글 번호 키값 셋팅
			mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			inqCnsltntListMapper.updateCnsltnt(mapUpd);
			
			mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			inqCnsltntListMapper.deleteCnsltnt(mapDel);			
			
		}

//		// 동적 처리를 하는 Col Data의 데이터를 저장한다.
//		ParameterGroup dsBoardColDataList = dataRequest.getParameterGroup("dsBoardColDataList");
//
//		Iterator<ParameterRow> insertedColDataRows = dsBoardColDataList.getInsertedRows();
//		Iterator<ParameterRow> updatedColDataRows = dsBoardColDataList.getUpdatedRows();
//
//		while (insertedColDataRows.hasNext()) {
//
//			// 게시판-컬럼별 내용 추가
//			Map<String, String> mapIns = insertedColDataRows.next().toMap();
//			
//			mapIns.put("FRST_RGTR_ID", userId);
//			mapIns.put("LAST_MDFR_ID", userId);
//			
//			// 신규 게시글 생성 컬럼 데이터 추가시
//			if(mapIns.get("BBSCTT_ESNTAL_NO").equals("")) {
//				String brdSeq = mapReturn.get("BBSCTT_ESNTAL_NO").toString();
//				mapIns.put("BBSCTT_ESNTAL_NO", brdSeq);
//			}
//			
//			//sampleBoardMapper.insertSampleBoardColDataList(mapIns);
//			
//			mapReturn.put("NTABRD_ESNTAL_NO", mapIns.get("NTABRD_ESNTAL_NO"));
//			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
//		}
//
//		while (updatedColDataRows.hasNext()) {
//
//			// 게시판-컬럼별 내용 추가
//			Map<String, String> mapUpd = updatedColDataRows.next().toMap();
//			mapUpd.put("LAST_MDFR_ID", userId);
//			//sampleBoardMapper.updateSampleBoardColDataList(mapUpd);
//			
//			mapReturn.put("NTABRD_ESNTAL_NO", mapUpd.get("NTABRD_ESNTAL_NO"));
//			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
//		}

		return mapReturn;
	}

}
