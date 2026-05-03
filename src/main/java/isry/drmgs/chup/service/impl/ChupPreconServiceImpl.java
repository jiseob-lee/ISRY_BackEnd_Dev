/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.chup.service.impl;

import java.util.ArrayList;
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
import isry.drmgs.casereg.mapper.DrmgsCaseRegMapper;
import isry.drmgs.chup.mapper.ChupPreconMapper;
import isry.drmgs.chup.service.ChupPreconService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : ChupPreconServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 14. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("chupPreconService")
public class ChupPreconServiceImpl implements ChupPreconService {
	
	@Resource(name="chupPreconMapper")
	private ChupPreconMapper chupPreconMapper;

	@Resource(name="drmgsCaseRegMapper")
	private DrmgsCaseRegMapper drmgsCaseRegMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	public List<Map<String, Object>> selectChupPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();

		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        /* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>(paramMap);

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		
		return chupPreconMapper.selectChupPreconList(paramMap2);
	}	
	
	public List<Map<String, Object>> selectChupPopupList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		rtnMap = chupPreconMapper.selectChupPopupList(paramMap);
		return rtnMap;
	}
	
	public Map<String, Object> selectChilIltrtCrisisScrennList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        /* 20230126_강화영_권한 적용_시작 */
        Map<String, Object> paramMap2 = new HashMap<>(paramMap);
        String auth = loginVO.getGroupAuthrtSeCd();
        if(!"1".equals(auth.substring(0, 1)) && !"2".equals(auth.substring(0, 1))) { // 여가부 또는 중앙관리기관
        	Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
        	paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
        }
        
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		
		String cnt = chupPreconMapper.selectChilIltrtCrisisScrennCnt(paramMap2);
		paramMap2.put("TOT_CNT", cnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(totCnt > 0) {
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			rtnMap = chupPreconMapper.selectChilIltrtCrisisScrennList(paramMap2);
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
	
	public Map<String, Object> selectChilIltrtCrisisScrennInfo(DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		list = drmgsCaseRegMapper.selectCrisisScrenn(paramMap);
		for(Map<String, Object> map : list) {
			rtnMap = map;
		}

		return rtnMap;
	}
}
