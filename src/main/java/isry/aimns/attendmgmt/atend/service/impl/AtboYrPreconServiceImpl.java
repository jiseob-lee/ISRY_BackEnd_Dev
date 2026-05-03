/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.atend.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.aimns.attendmgmt.atend.mapper.AtboYrPreconMapper;
import isry.aimns.attendmgmt.atend.service.AtboYrPreconService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : AtboYrPreconServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 7. 28.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 7. 28.
 * @수정내용 : - -
 */
@Service("atboYrPreconService")
public class AtboYrPreconServiceImpl implements AtboYrPreconService {

	@Resource(name = "atboYrPreconMapper")
	private AtboYrPreconMapper atboYrPreconMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectAtboPcList
	 * @param : Map : TRPR_NM_ENCPT(대상자명 암호화)
	 * @return : list
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 7. 28.
	 * @Method설명 : 출석부 연도별 현황 조회
	 */
	@Override
	public List<Map<String, Object>> selectAtboPcList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		if (dmSearch == null) {
			throw new AppWorksException("조회할 대상자가 없습니다", Alert.ERROR);
		}
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		// 대상자 목록 조회
		List<Map<String, Object>> result = atboYrPreconMapper.selectAtboPcList(paramMap);

		for (Map<String, Object> map : result) {

			String sFebRate = map.get("ATEND_TIMECNT") + "/" + map.get("CLASS_TIMECNT") + ", " + map.get("ATEND_RATE")
					+ "%";

			if (sFebRate != null)
				map.put("FEB_RATE", sFebRate);
		}
		return result;

	}
}
