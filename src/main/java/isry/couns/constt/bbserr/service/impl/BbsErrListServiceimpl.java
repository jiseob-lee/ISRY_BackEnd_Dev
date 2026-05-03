/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbserr.service.impl;

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

import isry.couns.constt.bbserr.mapper.BbsErrListMapper;
import isry.couns.constt.bbserr.service.BbsErrListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsErrListServiceimpl implements BbsErrListService {


	@Resource(name = "bbsErrListMapper")
	private BbsErrListMapper bbsErrListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : subOnLoad
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.seong.gen
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return bbsErrListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> BbsErrListCmbErr(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return bbsErrListMapper.BbsErrListCmbErr(codeId);
	}
	
	@Override
	public List<Map<String, Object>> BbsErrListCmbPrgrs(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return bbsErrListMapper.BbsErrListCmbPrgrs(codeId);
	}
	
	public List<Map<String, Object>> selectBbserrList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsErrListMapper.selectBbserrList(mapParam);
	}
	
	
	@Override
	public List<Map<String, Object>> selectBbserrDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsErrListMapper.selectBbserrDetail(mapParam);
	}
	
	@Override
	public Map<String, Object> saveBbserr(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsList");
		
//		System.out.println("dsBoardList = "+dsBoardList);
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
			dsBoardList.setValue(0, "WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));

			
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			bbsErrListMapper.insertBbserr(mapIns);

			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsErrListMapper.updateBbserr(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsErrListMapper.deleteBbserr(mapDel);				
		}
		
		return mapReturn;
	}	
	
	//-------------------------------------------------------답글
	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsErrListMapper.selectRespodDetail(mapParam);
	}
	
	
	@Override//답글(추가(insertRespod), 수정(updateRespod), 삭제(deleteRespod))
	public Map<String, Object> saveBbserrRply(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsRplyList");
		

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
			dsBoardList.setValue(0, "WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			bbsErrListMapper.insertBbserrRply(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsErrListMapper.updateBbserrRply(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsErrListMapper.deleteBbserrRply(mapDel);
		}
		
		return mapReturn;
	}

	@Override
	public void bbserrDtlCnt(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbsErrListMapper.bbserrDtlCnt(mapParam);
	}

	@Override
	public void bbserrResCnt(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbsErrListMapper.bbserrResCnt(mapParam);
	}

	/**
	 * @Method명   : BbsErrListCmbSxdc
	 * @param codeId
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> BbsErrListCmbSxdc(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return bbsErrListMapper.BbsErrListCmbSxdc(codeId);
	}
}
