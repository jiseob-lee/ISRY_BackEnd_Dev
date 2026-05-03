/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.edumng.semstrmng.service.impl;

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
import isry.subms.edumng.semstrmng.mapper.SemstrMngMapper;
import isry.subms.edumng.semstrmng.service.SemstrMngService;

/**
 * 
 * @파일명 : SemstrMngServiceImpl.java
 * @프로그램 설명 : 학기관리 서비스임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 12.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 12.
 * @수정내용 : - -
 */
@Service("semstrMngService")
public class SemstrMngServiceImpl implements SemstrMngService {

	@Resource(name = "semstrMngMapper")
	private SemstrMngMapper semstrMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	private static final Logger logger = LoggerFactory.getLogger(SemstrMngServiceImpl.class);
	/**
	 * @Method명 : selectSemstrMngList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 학기관리 목록 조회
	 */
	@Override
	public List<Map<String, String>> selectSemstrMngList(DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> dmSearchMap = dmSearch.getAllRowList().get(0);

		return semstrMngMapper.selectSemstrMngList(dmSearchMap);
	}

	/**
	 * @Method명   : selectSemstrMng
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 : 학기관리 상세 조회
	 */
	@Override
	public List<Map<String, String>> selectSemstrMng(DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, String> dmSearchMap = dmSearch.getAllRowList().get(0);
		
		return semstrMngMapper.selectSemstrMng(dmSearchMap);
	}

	/**
	 * @Method명 : saveSemstrMng
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 13.
	 * @Method설명 : 학기등록
	 */
	@Override
	public Map<String, Object> saveSemstrMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		returnMap.put("msg", "INF-M006");
		for (Map<String, String> map : insertedRowList) {
			
			// 이미 등록된 학기인지 여부 판단
			List<Map<String, String>> semsMap = semstrMngMapper.selectSemstrMng(map);
			for (@SuppressWarnings("unused") Map<String, String> map2 : semsMap) {
				returnMap.replace("msg", "이미 등록된 학기입니다.");
				return returnMap;
			}

			map.put("FRST_RGTR_ID", userId);
			map.put("LAST_MDFR_ID", userId);
			semstrMngMapper.insertSemstrMng(map);
			returnMap.replace("msg", "저장되었습니다.");
		}
		
		for (Map<String,String> map : updatedRowList) {

			// 이미 등록된 학기인지 여부 판단
			List<Map<String, String>> semsMap = semstrMngMapper.selectSemstrMng(map);
			for (Map<String, String> map2 : semsMap) {
				if (!(map2.get("SRVC_EXCN_BIZ_NO").equals(map.get("SRVC_EXCN_BIZ_NO__origin"))
						&& map2.get("SEMSTR_SE_CD").equals(map.get("SEMSTR_SE_CD__origin")))) {
					returnMap.replace("msg", "이미 등록된 학기입니다.");

					return returnMap;
				}
			}

			map.put("LAST_MDFR_ID", userId);
			semstrMngMapper.updateSemstrMng(map);
			returnMap.replace("msg", "수정되었습니다.");
		}

		return returnMap;
	}
	
}
