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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.itgcms.dyncBrd.mapper.DyncCreateBrdMapper;
import isry.itgcms.dyncBrd.service.DyncCreateBrdService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


/**
 * @파일명 : DyncCreateBrdServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2021. 12. 20.
 * @수정자 : You Minsang
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */

@Service
public class DyncCreateBrdServiceImpl implements DyncCreateBrdService {

	@Resource(name = "dyncCreateBrdMapper")
	private DyncCreateBrdMapper dyncCreateBrdMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectRootMenuList
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRootMenuList() throws Exception {
		
		return dyncCreateBrdMapper.selectRootMenuList();
	}
	
	/**
	 * @Method명   : selectCreateBoardList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCreateBoardList(Map<String, String> mapParam) throws Exception {
		
		return dyncCreateBrdMapper.selectCreateBoardList(mapParam);
	}
	

	/**
	 * @Method명   : selectBoardProgramInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectBoardProgramInfo(Map<String, String> mapParam) throws Exception {
		
		return dyncCreateBrdMapper.selectBoardProgramInfo(mapParam);
	}

	/**
	 * @Method명   : selectCreateBoardColList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 4. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCreateBoardColList(Map<String, String> mapParam) throws Exception {
		
		return dyncCreateBrdMapper.selectCreateBoardColList(mapParam);
	}
	
	/**
	 * @Method명   : saveColInfoCreateBoardList
	 * @param dataRequest
	 * @return 
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 : 동적 게시판 생성시 게시판 정보 및 컬럼 정보를 저장한다.
	 */
	@Override
	public Map<String, Object> saveCreateBoardList(HttpServletRequest request, DataRequest dataRequest) {
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
		/* 게시판 정보 CUD */
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		
		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();		
		Iterator<ParameterRow> updatedRows  = dsBoardList.getUpdatedRows();		
		Iterator<ParameterRow> deletedRows  = dsBoardList.getDeletedRows();		
		
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
			dyncCreateBrdMapper.insertCreateBoardList(mapIns);
			
			mapReturn.put("NTABRD_ESNTAL_NO",mapIns.get("NTABRD_ESNTAL_NO"));
		}
		
		while (updatedRows.hasNext()) {			
			Map<String, String> mapUpd = updatedRows.next().toMap();			
			mapUpd.put("LAST_MDFR_ID", userId);
			dyncCreateBrdMapper.updateCreateBoardList(mapUpd);
			mapReturn.put("NTABRD_ESNTAL_NO",mapUpd.get("NTABRD_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			dyncCreateBrdMapper.deleteCreateBoardList(mapDel);			
		}
		
		/* 게시판 컬럼 정보 CUD */
		ParameterGroup dsBoardCol = dataRequest.getParameterGroup("dsBoardColList");
		
		Iterator<ParameterRow> insertedColRows = dsBoardCol.getInsertedRows();		
		Iterator<ParameterRow> updatedColRows  = dsBoardCol.getUpdatedRows();		
		Iterator<ParameterRow> deletedColRows  = dsBoardCol.getDeletedRows();		

		while (insertedColRows.hasNext()) {			
			Map<String, String> mapIns = insertedColRows.next().toMap();
			
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			
			if(mapIns.get("NTABRD_ESNTAL_NO").equals("")) {
				mapIns.put("NTABRD_ESNTAL_NO", mapReturn.get("NTABRD_ESNTAL_NO").toString());
			}
			
			dyncCreateBrdMapper.insertColInfoCreateBoardList(mapIns);
			mapReturn.put("NTABRD_ESNTAL_NO",mapIns.get("NTABRD_ESNTAL_NO"));
		}
		
		while (updatedColRows.hasNext()) {			
			Map<String, String> mapUpd = updatedColRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			if(mapUpd.get("NTABRD_ESNTAL_NO").equals("")) {
				mapUpd.put("NTABRD_ESNTAL_NO", mapReturn.get("NTABRD_ESNTAL_NO").toString());
			}
			
			dyncCreateBrdMapper.updateColInfoCreateBoardList(mapUpd);
			mapReturn.put("NTABRD_ESNTAL_NO",mapUpd.get("NTABRD_ESNTAL_NO"));
		}

		while (deletedColRows.hasNext()) {			
			Map<String, String> mapDel = deletedColRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			dyncCreateBrdMapper.deleteColInfoCreateBoardList(mapDel);
			
			mapReturn.put("NTABRD_ESNTAL_NO",mapDel.get("NTABRD_ESNTAL_NO"));
		}
		
		
		/* 게시판 프로그램 정보 CUD */
		return mapReturn;	
		
	}

	/**
	 * @Method명   : getCmmnsCdTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	@Override
	public int getCmmnsCdTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return dyncCreateBrdMapper.getCmmnsCdTotalCount(mapParam);
	}

	/**
	 * @Method명   : selectCreateBoardcmmnsCdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCreateBoardcmmnsCdList(Map<String, Object> mapParam) throws Exception {
		
		return dyncCreateBrdMapper.selectCreateBoardcmmnsCdList(mapParam);
	}

}
