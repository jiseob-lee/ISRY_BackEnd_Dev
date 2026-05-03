/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.drmgs.link.mapper.DrmgsEduLinkMapper;
import isry.drmgs.link.service.DrmgsEduLinkService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : DrmgsEduLinkServiceImpl.java
 * @프로그램 설명 : 교육청 연계신청 목록
 * @작성자        : Yoon.Hee.Sung
 * @작성일        : 2023. 8. 28. 
 * @수정자        : Yoon.Hee.Sung
 * @수정일        : 2023. 8. 28. 
 * @수정내용      : 교육청 연계신청 목록
 */

@Service("drmgsEduLinkService")
public class DrmgsEduLinkServiceImpl extends IsryBaseServiceImpl implements DrmgsEduLinkService {
	
	@Resource(name="drmgsEduLinkMapper")
	private DrmgsEduLinkMapper drmgsEduLinkMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명   : selectEduLinkList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 28.
	 * @Method설명 : 교육청 연계신청 목록
	 */
	public Map<String, Object> selectEduLinkList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		Map<String,String> param = dmBase.getSingleValueMap();
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		String userId = "";
		Integer authMenuNo = 128;
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
					? 128 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		}
		//의뢰대상기관 - 로그인한 사용자 기관
		String INST_TYPE_SE_CD = loginVO.getInstTypeSeCd(); //기관유형
		String UNT_TASKWK_SE_CD = loginVO.getUntTaskwk(); //단위업무구분코드
		Integer USER_INST_NO = loginVO.getUserInstNo(); //사용자기관번호
		
		param.put("INST_TYPE_SE_CD", INST_TYPE_SE_CD);
		if(param.get("UNT_TASKWK_SE_CD") == null || "".equals(param.get("UNT_TASKWK_SE_CD"))) {
			param.put("UNT_TASKWK_SE_CD", UNT_TASKWK_SE_CD);
		}
		//param.put("UNT_TASKWK_SE_CD", "U02");
		param.put("USER_INST_NO", String.valueOf(USER_INST_NO));
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>(param);	/* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		String cnt = drmgsEduLinkMapper.selectEduLinkListCnt(paramMap2);
		paramMap2.put("TOT_CNT", cnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(totCnt > 0) {
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
	        rtnMap = drmgsEduLinkMapper.selectEduLinkList(paramMap2);
		}
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		result.put("dsList", rtnMap);
		result.put("dmPage", resPage);
		
		return result;
		
	}
	
	public List<Map<String, Object>> selectEduDetInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> param = dmBase.getSingleValueMap();
		
		list = drmgsEduLinkMapper.selectEduDetInfo(param);
		return list;
	}
}

