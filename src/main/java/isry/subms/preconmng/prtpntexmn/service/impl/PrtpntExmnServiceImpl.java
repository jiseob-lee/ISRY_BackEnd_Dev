/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.prtpntexmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.preconmng.prtpntexmn.mapper.PrtpntExmnMapper;
import isry.subms.preconmng.prtpntexmn.service.PrtpntExmnService;

/**
 * @파일명 : PrtpntExmnServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 5. 18.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 5. 18.
 * @수정내용 : - -
 */
@Service("prtpntExmnService")
public class PrtpntExmnServiceImpl implements PrtpntExmnService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "prtpntExmnMapper")
	private PrtpntExmnMapper prtpntExmnMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectExcnBizSemstr
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 8.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectExcnBizSemstr() throws Exception{

		return prtpntExmnMapper.selectExcnBizSemstr();
	}

	/**
	 * @Method명 : selectPrtpntExmnList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참여자 조사표 목록조회
	 */
	@Override
	public List<Map<String, String>> selectPrtpntExmnList(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearchParam");
		
		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> mapParam = parameterGroup.getSingleValueMap();
		mapParam.put("UNT_TASKWK_SE_CD", loginVo.getUntTaskwk());
		
		List<Map<String, String>> returnVal = prtpntExmnMapper.selectPrtpntExmnList(mapParam);
		
		for (Map<String, String> map : returnVal) {
			if(map.get("BFE_FNSH_YR") != null && !map.get("BFE_FNSH_YR").isEmpty()) {
				String[] bfeFnshYr = map.get("BFE_FNSH_YR").split(",");
				for (int i = 0; i < bfeFnshYr.length; i++) {
					map.put("BFE_FNSH_YR" + (i+1), bfeFnshYr[i]);
				}
			}
		}
		
		return returnVal;
	}

	/**
	 * @Method명 : savePrtpntExmn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 9.
	 * @Method설명 : 참여자조사표 등록/수정
	 */
	@Override
	public int savePrtpntExmn(HttpServletRequest request, DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		
		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);

		int count = 0;

		List<Map<String, String>> paramList = parameterGroup.getUpdatedRowList();

		if (parameterGroup != null) {
			for (Map<String, String> map : paramList) {
				map.put("USER_ID", loginVo.getId());
				prtpntExmnMapper.deleteBfeFnshYr(map);
				
				count += prtpntExmnMapper.savePrtpntExmn(map);
				
				for (int i = 1; i < 4; i++) {
					if(map.get("BFE_FNSH_YR"+ i) != null && !map.get("BFE_FNSH_YR"+ i).equals("") ) {
						map.put("BFE_FNSH_YR", map.get("BFE_FNSH_YR"+i));
						prtpntExmnMapper.insertBfeFnshYr(map);
						System.err.println("rfe : " + map.get("BFE_FNSH_YR"));
					}
				}
			}
		}
		return count;
	}
}
