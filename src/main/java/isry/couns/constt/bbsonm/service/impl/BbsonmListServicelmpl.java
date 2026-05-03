/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsonm.service.impl;

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

import isry.couns.constt.bbsonm.mapper.BbsonmListMapper;
import isry.couns.constt.bbsonm.service.BbsonmListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : BbsonmServicelmpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Service("BbsonmListService")
public class BbsonmListServicelmpl implements BbsonmListService{
	
	@Resource(name = "BbsonmListMapper")
	private BbsonmListMapper bbsonmListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbsonmList(Map<String, Object> mapParam) {
		
		return bbsonmListMapper.selectBbsonmList(mapParam);
	}

	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return bbsonmListMapper.getTotalCount(mapParam);
	}

	
	@Override
	public Map<String, Object> saveBbsonmList(HttpServletRequest request, DataRequest dataRequest) {

		Map<String, Object> mapReturn = new HashMap<String, Object>();
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");
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
//		System.out.println("DDDD dsList = "+dsList);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("BBSCTT_TYPE_SE_CD", dmSearchParam.getValue("BBSCTT_TYPE_SE_CD"));
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("WRTR_NM_ENCPT", dsList.getValue("WRTR_NM_ENCPT"));
//			System.out.println("DDDD insert = "+mapIns.toString());
			bbsonmListMapper.insertBbsonm(mapIns);
			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("BBSCTT_TYPE_SE_CD", dmSearchParam.getValue("BBSCTT_TYPE_SE_CD"));
			mapUpd.put("LAST_MDFR_ID", userId);
//			System.out.println("DDDD update = "+mapUpd.toString());
			bbsonmListMapper.updateBbsonm(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("BBSCTT_TYPE_SE_CD", dmSearchParam.getValue("BBSCTT_TYPE_SE_CD"));
			mapDel.put("LAST_MDFR_ID", userId);
//			System.out.println("DDDD delete = "+mapDel.toString());
			bbsonmListMapper.deleteBbsonm(mapDel);		
			
		}
		return mapReturn;
	}

	/**
	 * @Method명   : selectU11LastArticleNo
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 :업무구분코드 U11 청소년상담 위기사례 마지막 글번호 조회
	 */
	@Override
	public Map<String, Object> selectU11DangerLastArticleNo() throws Exception {
		return bbsonmListMapper.selectU11DangerLastArticleNo();
	}
}
