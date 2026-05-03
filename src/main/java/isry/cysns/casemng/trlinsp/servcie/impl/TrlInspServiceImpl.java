/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.casemng.trlinsp.servcie.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.cysns.casemng.trlinsp.mapper.TrlInspMapper;
import isry.cysns.casemng.trlinsp.servcie.TrlInspService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : TrlInspServiceImpl.java
 * @프로그램 설명 : 회기보고등록 서비스임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 11. 15.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 11. 15.
 * @수정내용 : - -
 */
@Service("trlInspService")
public class TrlInspServiceImpl implements TrlInspService {

	@Resource(name = "trlInspMapper")
	TrlInspMapper trlInspMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectTrlInsp
	 * @param requestMap
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 15.
	 * @Method설명 : 심리검사 조회
	 */
	@Override
	public List<Map<String, Object>> selectTrlInsp(Map<String, String> requestMap) throws Exception {

		List<Map<String, Object>> resultMap = trlInspMapper.selectTrlInsp(requestMap);

		return resultMap;
	}

	/**
	 * @Method명 : saveTrlInsp
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 15.
	 * @Method설명 : 심리검사 등록/수정/삭제
	 */
	@Override
	public void saveTrlInsp(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedMap = dsList.getInsertedRowList();
		List<Map<String, String>> updatedMap = dsList.getUpdatedRowList();

		Map<String, Object> metaData = new HashMap<String, Object>();

		int iterationCnt = 0;
		String trlInspMngNo = "";

		for (Map<String, String> map : insertedMap) {

			// 심리검사관리번호 채번 및 반환값 설정
			if (iterationCnt == 0) {
				map.put("LGN_USER_ID", loginVO.getId());
				trlInspMngNo = trlInspMapper.selectTrlInspMngNo(map);
				metaData.put("state", "I");
				metaData.put("trlInspMngNo", trlInspMngNo);
			}

			// 삽입시 필요한 파라미터 추가
			map.put("TRL_INSP_MNG_NO", trlInspMngNo);
			map.put("FRST_RGTR_ID", loginVO.getId());
			map.put("LAST_MDFR_ID", loginVO.getId());

			// 심리검사 삽입
			trlInspMapper.insertTrlInsp(map);

			iterationCnt++;
		}

		for (Map<String, String> map : updatedMap) {

			// 반환값 설정
			if (iterationCnt == 0) {
				trlInspMngNo = map.get("TRL_INSP_MNG_NO__origin");
				metaData.put("state", "U");
				metaData.put("trlInspMngNo", trlInspMngNo);
			}

			// 수정시 필요한 파라미터 추가
			map.put("LAST_MDFR_ID", loginVO.getId());

			// 심리검사 수정
			trlInspMapper.updateTrlInsp(map);

			iterationCnt++;
		}

		// 반환
		dataRequest.setMetadata(true, metaData);
	}
}
