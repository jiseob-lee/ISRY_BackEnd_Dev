/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.service.impl;

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

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.sample.mapper.NoticeBoardMapper;
import isry.sample.service.NoticeBoardService;

/**
 * @파일명 : TstBoardDevServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2021. 12. 20.
 * @수정자 : Song.Young.Il
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

	@Resource(name = "noticeBoardMapper")
	private NoticeBoardMapper noticeBoardMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : getTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return noticeBoardMapper.getTotalCount(mapParam);
	}
	
	
	/**
	 * @Method명   : selectNoticeBoardList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectNoticeBoardList(Map<String, Object> mapParam) throws Exception {

		return noticeBoardMapper.selectNoticeBoardList(mapParam);
	}
	
	/**
	 * @Method명   : selectNoticeBoardDtlList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectNoticeBoardDtlList(Map<String, Object> mapParam) throws Exception {
		
		return noticeBoardMapper.selectNoticeBoardDtlList(mapParam);
	}
	
	/**
	 * @Method명   : updateNoticeBoardDtlList
	 * @param mapParam
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 :
	 */
	@Override
	public void updateNoticeBoardDtlList(Map<String, Object> mapParam) throws Exception {
		noticeBoardMapper.updateNoticeBoardDtlList(mapParam);
	}
	
	/**
	 * @Method명   : saveNoticeBoardList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveNoticeBoardList(HttpServletRequest request, DataRequest dataRequest) {

		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
				
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
			noticeBoardMapper.insertNoticeBoardList(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("NTABRD_ESNTAL_NO", mapIns.get("NTABRD_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			noticeBoardMapper.updateNoticeBoardList(mapUpd);
			
			mapReturn.put("NTABRD_ESNTAL_NO", mapUpd.get("NTABRD_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			noticeBoardMapper.deleteNoticeBoardList(mapDel);			
			
		}

		return mapReturn;
	}

}
