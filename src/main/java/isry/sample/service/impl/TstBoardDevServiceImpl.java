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

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.sample.mapper.TstBoardDevMapper;
import isry.sample.service.TstBoardDevService;

/**
 * @파일명        : TstBoardDevServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2021. 12. 20. 
 * @수정자        : You Minsang
 * @수정일        : 2021. 12. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service
public class TstBoardDevServiceImpl extends IsryBaseServiceImpl implements TstBoardDevService  {
	
	@Resource(name = "tstBoardDevMapper")
	private TstBoardDevMapper tstBoardDevMapper;
	
	/**
	 * @Method명   : selectBoardList
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> mapParam) throws Exception {
		
		return tstBoardDevMapper.selectBoardList(mapParam);
	}

	/**
	 * @Method명   : saveBoardList
	 * @param dataRequest
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 21. 
	 * @Method설명 :
	 */
	@Override
	public String saveBoardList(DataRequest dataRequest) {
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsBoardList");
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();
		
		String strFindRowKey = "";

		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			tstBoardDevMapper.insertBoardList(mapIns);
			
			// 게시글 번호 키값 셋팅			
			strFindRowKey = mapIns.get("BRD_SEQ");
		}

		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			tstBoardDevMapper.updateBoardList(mapUpd);
			
			// 게시글 번호 키값 셋팅
			strFindRowKey = mapUpd.get("BRD_SEQ");
		}
		
		while (deletedRows.hasNext()) {
			tstBoardDevMapper.deleteBoardList(deletedRows.next().toMap());
		}
		
		return strFindRowKey;
	}
	
	/**
	 * @Method명   : selectSysDate
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 21. 
	 * @Method설명 :
	 */
	public String selectSysDate() throws Exception{
		return selectSysDate("YYYY-MM-DD");
	}
	
	public String selectSysDate(String strFormat) throws Exception	{
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("STR_FORMAT", strFormat);
		
		return tstBoardDevMapper.selectSysDate(mapParam);
	}

	/**
	 * @Method명   : getTotalCount
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 30. 
	 * @Method설명 :
	 */
	@Override
	public String getTotalCount() throws Exception {
		
		return tstBoardDevMapper.getTotalCount();
	}

}
