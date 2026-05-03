/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendprecon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.preconmng.atendprecon.mapper.AtendPreconMapper;
import isry.subms.preconmng.atendprecon.service.AtendPreconService;

/**
 * @파일명 : AtendPreconServiceImpl.java
 * @프로그램 설명 : 출석현황 서비스임플 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 13.
 * @수정내용 : - -
 */
@Service("atendPreconService")
public class AtendPreconServiceImpl implements AtendPreconService {

	// 출석현황 관련 매퍼
	@Resource(name = "atendPreconMapper")
	private AtendPreconMapper atendPreconMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectAtendPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 21.
	 * @Method설명 : 출결현황 조회
	 */
	@Override
	public List<Map<String, String>> selectAtendPreconList(DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> InMap = dmSearch.getAllRowList().get(0);

		List<Map<String, String>> result = atendPreconMapper.selectAtendPreconList(InMap);

		return result;
	}

	/**
	 * @Method명 : selectAtendList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 13.
	 * @Method설명 : 출석조회(일자별)
	 */
	@Override
	public List<Map<String, String>> selectAtendList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		Map<String, String> InMap = dmParam.getAllRowList().get(0);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		InMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		List<Map<String, String>> result = atendPreconMapper.selectAtendList(InMap);

		return result;
	}

	/**
	 * @Method명 : saveAtend
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 출석 등록/수정/삭제
	 */
	@Override
	public void saveAtend(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		for (Map<String, String> map : updatedRowList) {
			map.put("FRST_RGTR_ID", userId);
			map.put("LAST_MDFR_ID", userId);

			atendPreconMapper.insertAtend(map);
			atendPreconMapper.insertAtendHstr(map);
			if (map.get("ATNC_SE_CD") != "01")
				atendPreconMapper.deleteFile(map);
		}

		for (Map<String, String> map : deletedRowList) {
			map.put("DATAA_CHG_SE_CD", "D");

			atendPreconMapper.insertAtendHstr(map);
			atendPreconMapper.deleteAtend(map);
		}
	}

	/**
	 * @Method명 : selectAtend
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 9.
	 * @Method설명 : 상세 출석 조회
	 */
	@Override
	public Map<String, String> selectAtend(DataRequest dataRequest) throws Exception {

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		Map<String, String> InMap = dmParam.getAllRowList().get(0);

		return atendPreconMapper.selectAtend(InMap);
	}

	/**
	 * @Method명 : selectAtendByTrpr
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 9.
	 * @Method설명 : 대상자별 출석 통계조회
	 */
	@Override
	public Map<String, String> selectAtendByTrpr(DataRequest dataRequest) throws Exception {

		Map<String, String> result = new HashMap<String, String>();

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> InMap = dmSearch.getAllRowList().get(0);

		result = atendPreconMapper.selectAtendByTrpr(InMap);

		String rmCn = "";
		for (Map<String, String> map : atendPreconMapper.selectAtendRmCnByTrpr(InMap)) {
			if (map.get("RM_CN") != null) {
				if (!rmCn.equals(""))
					rmCn = rmCn + "\r\n";
				rmCn = rmCn + "[" + map.get("PTCPTN_YMD").substring(0, 4) + "/" + map.get("PTCPTN_YMD").substring(4, 6)
						+ "/" + map.get("PTCPTN_YMD").substring(6, 8) + "] " + map.get("RM_CN");
			}
		}

		if (rmCn != null && !rmCn.equals(""))
			result.put("RM_CN", rmCn);

		return result;
	}
}
