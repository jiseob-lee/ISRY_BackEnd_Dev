/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.couns.constt.etcntabrd.mapper.BbsContentlistMapper;
import isry.couns.constt.etcntabrd.service.BbsContentListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


@Service
public class BbsContentListServiceimpl implements BbsContentListService {


	@Resource(name = "bbsContentlistMapper")
	private BbsContentlistMapper bbsContentlistMapper;
	
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
		
		return bbsContentlistMapper.getTotalCount(mapParam);
	}
	
	public List<Map<String, Object>> selectInqBbsContentList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsContentlistMapper.selectInqBbsContentList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> bbsContentList(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return bbsContentlistMapper.bbsContentList(codeId);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsContentDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsContentlistMapper.selectBbsContentDetail(mapParam);
	}
	
	@Override
	public Map<String, Object> updateBbsContent(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";		// Session ID
		String loginIp = "";		// Session IP
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && (loginVO.getId() != null && !"".equals(loginVO.getId())) && (loginVO.getIp() != null && !"".equals(loginVO.getIp()))) {
			loginId = loginVO.getId();
			loginIp = loginVO.getIp();
		} else {
			throw new AppWorksException("세션정보가 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
//		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();		

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			String resultGuideCn = mapUpd.get("RETE_CN");
			
			mapUpd.put("loginId", loginId);
			mapUpd.put("loginIp", loginIp);
			
			int result = bbsContentlistMapper.updateBbsContent(mapUpd);
			
			if (result > 0) {
				if (resultGuideCn != null && !"".equals(resultGuideCn)) {
					bbsContentlistMapper.insertBbsContentReply(mapUpd);
				}
			}
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}
		
		return mapReturn;
	}	
	
}
