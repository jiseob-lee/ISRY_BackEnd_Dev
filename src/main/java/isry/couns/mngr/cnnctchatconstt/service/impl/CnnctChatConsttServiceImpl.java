/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.cnnctchatconstt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.cnnctchatconstt.mapper.CnnctChatConsttMapper;
import isry.couns.mngr.cnnctchatconstt.service.CnnctChatConsttService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class CnnctChatConsttServiceImpl extends IsryBaseServiceImpl implements CnnctChatConsttService {
	
	@Resource(name = "cnnctChatConsttMapper")
	private CnnctChatConsttMapper cnnctChatConsttMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectCnnctChatConsttList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 13. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCnnctChatConsttList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("unitTaskWkCd", loginVO.getUntTaskwkSeCd());
		
		return cnnctChatConsttMapper.selectCnnctChatConsttList(mapParam);
	}
	
	/**
	 * @Method명   : processCnnctChatConstt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 16. 
	 * @Method설명 :
	 */
	@Override
	public int processCnnctChatConstt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        //System.out.println("DDD : "+ searchParam.toString());        
		mapParam.put("DEPT_CD"	, searchParam.getValue("DEPT_CD"));

		ParameterGroup param = dataRequest.getParameterGroup("dsList");
		int ret = 0;
		boolean hasCkbY = false;
		if (param != null) {
			
			ret = cnnctChatConsttMapper.deleteCnnctChatConstt(mapParam); //AYC470 잇는채팅 상담자 전부 삭제
			
			List<Map<String, String>> list = param.getAllRowList();
			
			//Map<String, String> mapParam = new HashMap<String, String>();
			HttpSession session = request.getSession();
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			String loginId = "";
			loginId = loginVO.getId();
			
			
			//log.info("before insertCnnctChatConstt" + ret + "size of list" +list.size());
			for (Map<String, String> map : list) {
				if(map.get("CKB_CONSTT_YN").equalsIgnoreCase("Y")
						&& !map.get("CNSLTNT_ID").isEmpty()
						&& !map.get("CNSLTNT_NM").isEmpty()) {
					ret = cnnctChatConsttMapper.insertCnnctChatConstt(list); // 상담자 선택 여부에 체크된 상담자만 insert
					break;
				}
			}
		}
		return ret>0 ? 1 : 0;
	}

}
