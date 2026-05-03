/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.hlngcmp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csemd.mdlrtrehabcrsemng.hlngcmp.mapper.HlngCmpMapper;
import isry.csemd.mdlrtrehabcrsemng.hlngcmp.service.HlngCmpService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : HlngCmpServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Service("hlngCmpService")
public class HlngCmpServiceImpl implements HlngCmpService {

	@Resource(name = "hlngCmpMapper")
	private HlngCmpMapper hlngCmpMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectMentorList
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 19.
	 * @Method설명 : 멘토목록조회
	 */
	@Override
	public List<Map<String, Object>> selectMentorList(Map<String, String> requestMap) throws Exception {
		
		return hlngCmpMapper.selectMentorList(requestMap);
	}

	/**
	 * @Method명 : selectMenteeList
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 19.
	 * @Method설명 : 멘티목록 조회(멘토멘티매칭 용)
	 */
	@Override
	public List<Map<String, Object>> selectMenteeList(Map<String, String> requestMap) throws Exception {

		return hlngCmpMapper.selectMenteeList(requestMap);
	}

	/**
	 * @Method명 : selectMentorMenteeMatchingList
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 멘토멘티매칭 목록조회 & 멘티목록 조회(일일관찰일지 용)
	 */
	@Override
	public List<Map<String, Object>> selectMentorMenteeMatchingList(Map<String, String> requestMap) throws Exception {

		List<Map<String, Object>> result = hlngCmpMapper.selectMmMatchingList(requestMap);
		for (Map<String, Object> map : result) {
			String acbgGrade = map.get("SCHL") + "/" + map.get("MENTEE_GRADE");
			map.put("ACBG_GRADE", acbgGrade);
		}

		return result;
	}

	/**
	 * @Method명 : saveMmMatchingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 멘토 멘티 매칭 저장
	 */
	@Override
	public void saveMmMatchingList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();

		UserDetailsVO userVo = null;
		try {
			userVo = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Map<String, String> map : deletedRowList) {
			map.put("LGN_USER_ID", userVo.getId());

			hlngCmpMapper.deleteMmMatchingList(map);
		}
		for (Map<String, String> map : insertedRowList) {
			map.put("LGN_USER_ID", userVo.getId());

			hlngCmpMapper.insertMmMatchingList(map);
		}
		for (Map<String, String> map : updatedRowList) {
			map.put("LGN_USER_ID", userVo.getId());

			hlngCmpMapper.updateMmMatchingList(map);
		}
	}

	/**
	 * @Method명 : selectDayObservDiaryList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 일일관찰일지 목록 조회
	 */
	@Override
	public void selectDayObservDiaryList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> paramMap = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		paramMap.put("INST_NO", userVo.getInstNo().toString());
		paramMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());
		if (paramMap.get("MENTEE_NO") != null && !paramMap.get("MENTEE_NO").equals("")) {
			paramMap.put("CASE_MNG_NO", paramMap.get("MENTEE_NO").split("-")[0]);
			paramMap.put("CASE_MNG_ODRNO", paramMap.get("MENTEE_NO").split("-")[1]);
		}

		dataRequest.setResponse("dsList", hlngCmpMapper.selectDayObservDiaryList(paramMap));
	}

	/**
	 * @Method명 : selectDayObservDiary
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 일일관찰일지 상세조회
	 */
	@Override
	public void selectDayObservDiary(DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		paramMap.put("CASE_MNG_NO", paramMap.get("MENTEE_NO").split("-")[0]);
		paramMap.put("CASE_MNG_ODRNO", paramMap.get("MENTEE_NO").split("-")[1]);

		List<Map<String, String>> returnData = hlngCmpMapper.selectDayObservDiary(paramMap);

		for (Map<String, String> map : returnData) {

			String acbgGrade = "";
			String acbg = map.get("ACBG");
			String grade = map.get("GRADE");
			if (acbg != null) {
				acbgGrade += acbg;
				if (grade != null) {
					acbgGrade += "/" + grade;
				}
			}
			map.put("ACBG_GRADE", acbgGrade);
		}

		dataRequest.setResponse("dsList", returnData);

	}

	/**
	 * @Method명 : saveDayObservDiary
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 일일관찰일지 등록/수정
	 */
	@Override
	public void saveDayObservDiary(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsInsertedList = parameterGroup.getInsertedRowList();
		List<Map<String, String>> dsUpdatedList = parameterGroup.getUpdatedRowList();

		UserDetailsVO loginUser = userLoginService.getLoginSessionVO(request);

		for (Map<String, String> map : dsInsertedList) {
			map.put("LGN_USER_ID", loginUser.getId());
			map.put("CASE_MNG_NO", map.get("MENTEE_NO").split("-")[0]);
			map.put("CASE_MNG_ODRNO", map.get("MENTEE_NO").split("-")[1]);

			int check = hlngCmpMapper.selectDayObservDiaryCheck(map);

			if (check == 0)
				hlngCmpMapper.insertDayObservDiary(map);
			else
				hlngCmpMapper.updateDayObservDiary(map);
			hlngCmpMapper.insertDayObservDiaryDtl(map);
		}

		for (Map<String, String> map : dsUpdatedList) {
			map.put("LGN_USER_ID", loginUser.getId());
			map.put("CASE_MNG_NO", map.get("MENTEE_NO").split("-")[0]);
			map.put("CASE_MNG_ODRNO", map.get("MENTEE_NO").split("-")[1]);

			hlngCmpMapper.updateDayObservDiary(map);
			hlngCmpMapper.updateDayObservDiaryDtl(map);
		}
	}

	/**
	 * @Method명 : selectDayObservDiaryCheck
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 일일관찰일지 등록여부 확인
	 */
	@Override
	public void selectDayObservDiaryCheck(DataRequest dataRequest) throws Exception {
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsInsertedRowList = dsList.getInsertedRowList();
		List<Map<String, String>> dsUpdatedRowList = dsList.getUpdatedRowList();
		Map<String, Object> returnMsg = new HashMap<String, Object>();

		for (Map<String, String> map : dsInsertedRowList) {
			map.put("CASE_MNG_NO", map.get("MENTEE_NO").split("-")[0]);
			map.put("CASE_MNG_ODRNO", map.get("MENTEE_NO").split("-")[1]);
			List<Map<String, String>> result = hlngCmpMapper.selectDayObservDiary(map);
			if (!result.isEmpty()) {
				returnMsg.put("msg", "해당 날짜의 일지가 이미 존재합니다. 새로 작성한 내용으로 업데이트 하시겠습니까?");
			}
		}

		for (Map<String, String> map : dsUpdatedRowList) {
			map.put("CASE_MNG_NO", map.get("MENTEE_NO").split("-")[0]);
			map.put("CASE_MNG_ODRNO", map.get("MENTEE_NO").split("-")[1]);
			List<Map<String, String>> result = hlngCmpMapper.selectDayObservDiary(map);
			for (Map<String, String> map2 : result) {
				if (!map2.get("WRT_YMD").equals(map.get("WRT_YMD__origin")))
					returnMsg.put("msg", "해당 날짜의 일지가 이미 존재합니다.");
			}
		}

		dataRequest.setMetadata(true, returnMsg);
	}
}
