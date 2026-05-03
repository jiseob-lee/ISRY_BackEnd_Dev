/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendcomplprecon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.preconmng.atendcomplprecon.mapper.AtendComplPreconMapper;
import isry.subms.preconmng.atendcomplprecon.service.AtendComplPreconService;

/**
 * @파일명 : AtendComplPreconServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2023. 6. 26.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2023. 6. 26.
 * @수정내용 : - -
 */
@Service("atendComplPreconService")
public class AtendComplPreconServiceImpl implements AtendComplPreconService {

	@Resource(name = "atendComplPreconMapper")
	private AtendComplPreconMapper atendComplPreconMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	/**
	 * @Method명 : selectAtendComplPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 26.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectAtendComplPreconList(DataRequest dataRequest, HttpServletRequest request)
			throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> map = new HashMap<String, Object>();
		// login 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwk = loginVO.getUntTaskwk();
		
		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
				
		map.putAll(paramGroup.getSingleValueMap());
		map.put("UNT_TASKWK_SE_CD", untTaskwk);
		map.put("INST_NOS", comMap.get("INST_NOS"));

		return atendComplPreconMapper.selectAtendComplPreconList(map);
	}

	/**
	 * @Method명 : selectAtendComplPreconMngList
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectAtendComplPreconMngList(DataRequest dataRequest) {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");

		Map<String, Object> map = new HashMap<String, Object>();

		map.putAll(paramGroup.getSingleValueMap());

		return atendComplPreconMapper.selectAtendComplPreconMngList(map);
	}

	/**
	 * @Method명 : saveAtendComplPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 7. 3.
	 * @Method설명 :
	 */
	@Override
	public void saveAtendComplPreconList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		ParameterGroup List = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> rowList = List.getAllRowList();

		for (Map<String, String> map : rowList) {
			map.put("USER_ID", paramGroup.getValue("USER_ID"));

			atendComplPreconMapper.saveAtendComplPreconList(map); // AFA320 참여자조사표
			atendComplPreconMapper.saveAtendComplPreconAFA330(map); // AFA330 교육이수일정내역
		}

	}

	/**
	 * @Method명 : saveAtendComplPreconMng
	 * @param dataRequest
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 30.
	 * @Method설명 :
	 */
	@Override
	public void saveAtendComplPreconMng(DataRequest dataRequest) {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmParam");
		ParameterGroup List = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> rowList = List.getAllRowList();
		List<Map<String, String>> deletedList = List.getDeletedRowList();

		for (Map<String, String> map : rowList) {

			map.put("CASE_MNG_NO", paramGroup.getValue("CASE_MNG_NO"));
			map.put("CASE_MNG_ODRNO", paramGroup.getValue("CASE_MNG_ODRNO"));
			map.put("PIC_NO", paramGroup.getValue("PIC_NO"));
			map.put("USER_ID", paramGroup.getValue("USER_ID"));
			map.put("RESRCE_NO", paramGroup.getValue("RESRCE_NO"));

			atendComplPreconMapper.saveAtendComplPreconMng(map);
		}
		for (Map<String, String> map : deletedList) {

			map.put("CASE_MNG_NO", paramGroup.getValue("CASE_MNG_NO"));
			map.put("CASE_MNG_ODRNO", paramGroup.getValue("CASE_MNG_ODRNO"));
			map.put("PIC_NO", paramGroup.getValue("PIC_NO"));
			map.put("USER_ID", paramGroup.getValue("USER_ID"));
			map.put("RESRCE_NO", paramGroup.getValue("RESRCE_NO"));

			atendComplPreconMapper.deleteComplPreconMng(map);
		}
	}

}
