/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrreg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.clipsoft.org.apache.commons.lang.StringUtils;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.ScpDb;
import isry.pubms.casemng.sheltrreg.mapper.SprtPrfmncMapper;
import isry.pubms.casemng.sheltrreg.service.SprtPrfmncService;

/**
 * @파일명        : SheltrRegServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("sprtPrfmncService")
public class SprtPrfmncServiceImpl implements SprtPrfmncService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "sprtPrfmncMapper")
	private SprtPrfmncMapper sprtPrfmncMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명   : selectSprtPfrmncPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 14. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectSprtPfrmncPagingList(HttpServletRequest request, DataRequest dataRequest)	throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");
		
		Map<String, String> paramMap = param.getSingleValueMap();

		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		paramMap2.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		List<Map<String, Object>> list = new ArrayList<>();

		String cnt = "";
		int totCnt  = 0;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;

		if (StringUtils.equals(CommUtils.getUntTaskwk(userLoginService.getLoginSessionVO(request)), "U05")) {
			
			cnt = sprtPrfmncMapper.selectSLfrlSprtPagingCount(paramMap2);

			paramMap2.put("TOT_CNT", cnt);
			
			//페이지 인덱싱에 필요한 정보를 정제합니다.		
			totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
			
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			list = sprtPrfmncMapper.selectSlfrlSprtPagingList(paramMap2); //자립지원
		} else {
			cnt = sprtPrfmncMapper.selectSheltrSprtPagingCount(paramMap2);
			
			paramMap2.put("TOT_CNT", cnt);
			
			//페이지 인덱싱에 필요한 정보를 정제합니다.		
			totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
			
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
			list = sprtPrfmncMapper.selectSheltrSprtPagingList(paramMap2); //쉼터, 회복시설
		}
		
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("dmPageInfo", resPage);
		
		return result;
		
	}
	
	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 4. 20. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup param = dataRequest.getParameterGroup("dsCaseInqList");

		LOGGER.info("saveData");
		
		
		List<Map<String, String>> updatedRowList = param.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			LOGGER.info("saveData11111");
			map.put("USER_ID", userId);
			sprtPrfmncMapper.saveData(map);
		}
	}


}
