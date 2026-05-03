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

import isry.couns.constt.chttmng.mapper.InqSpclaMapper;
import isry.couns.constt.chttmng.service.InqSpclaService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Service("InqSpclaService")
public class InqSpclaServiceImpl implements InqSpclaService{
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

	@Resource(name = "InqSpclaMapper")
	private InqSpclaMapper inqSpclaMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectSpclaList(Map<String, Object> mapParam) {
		
		return inqSpclaMapper.selectSpclaList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectSpclaDetail(Map<String, Object> mapParam) {

		return inqSpclaMapper.selectSpclaDetail(mapParam);
	}

	@Override
	public Map<String, Object> saveSpclaBoardList(HttpServletRequest request, DataRequest dataRequest) {

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
			mapIns.put("CLIENA_NM_ENCPT", dsBoardList.getValue("CLIENA_NM_ENCPT"));
			inqSpclaMapper.insertSpcla(mapIns);
			// 게시글 번호 키값 셋팅
			mapReturn.put("SPCLA_MNG_TRPR_SN", mapIns.get("SPCLA_MNG_TRPR_SN"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("CLIENA_NM_ENCPT", dsBoardList.getValue("CLIENA_NM_ENCPT"));
			inqSpclaMapper.updateSpcla(mapUpd);
			
			mapReturn.put("SPCLA_MNG_TRPR_SN", mapUpd.get("SPCLA_MNG_TRPR_SN"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			inqSpclaMapper.deleteSpcla(mapDel);			
			
		}

		return mapReturn;
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return inqSpclaMapper.getTotalCount(mapParam);
	}

	/**
	 * @Method명   : saveClientName
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 3. 13. 
	 * @Method설명 :
	 */
	@Override
	public void saveClientName(HttpServletRequest request, DataRequest dataRequest) {
		
		String clientNm = "";
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		if (paramMap.get("CLIENA_NM") != null && !"".equals(paramMap.get("CLIENA_NM"))) {
			clientNm = paramMap.get("CLIENA_NM");
			paramMap.put("CLIENA_NM", clientNm);
		}
		
		inqSpclaMapper.saveClientName(paramMap);
		
	}

}
