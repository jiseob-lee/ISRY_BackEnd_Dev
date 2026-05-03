/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.recipireg.service.impl;

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
import com.dreamsecurity.magice2e.util.Log;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.pubms.slfrlsprtpensn.recipireg.mapper.RecipiRegMapper;
import isry.pubms.slfrlsprtpensn.recipireg.service.RecipiRegService;

/**
 * @파일명        : RecipiRegServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.07.10
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.07.27
 * @수정내용      : 
 * - Paging 처리 방식 변경 (강화영 수석 :기존 페이징 방식에 문제 있어서 사용 권유하지않는다고 함, 후속 작업자 참고 바람)
 * - [2023-08-30, Gyu.Ha.Baek] PRE 반영
 */
@Service("recipiRegService")
public class RecipiRegServiceImpl implements RecipiRegService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "recipiRegMapper")
	private RecipiRegMapper recipiRegMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @Method명   : selectRecipiList
	 * @param  HttpServletRequest request, DataRequest dataRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 * @작성자     : Baek.Gyu.Ha
	 * @작성일     : 2023.07.10 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectRecipiList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 조회 파라미터
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> paramMap = new HashMap<>(param.getSingleValueMap());
		
		// 결과 반환
		Map<String, Object> result = new HashMap<>();
		
		// 페이징 총 건수 처리
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		Map<String, Object> dmPageReq = new HashMap<>(reqPage.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) dmPageReq.get("pageNo"));
		int rowSize = Integer.parseInt((String) dmPageReq.get("pageRowCount"));
		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		dmPageReq.put("START_IDX", startIndex);
		dmPageReq.put("LAST_IDX", lastIndex);
		
		// 페이징과 기존 데이터맵 병합, 조회 결과 통일
		paramMap.putAll(dmPageReq);
		
		// 로그인 사용자의 정보를 전달
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			paramMap.put("LOGIN_INST_NO", loginVO.getInstNo());
			paramMap.put("USER_ID2", loginVO.getId());
			paramMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		}
		
		// 전체 카운트와 맵 조회
		int totCnt = recipiRegMapper.selectRecipiListCount(paramMap);
		List<Map<String, Object>> resultList = recipiRegMapper.selectRecipiList(paramMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		// 반환 결과 저장
		result.put("dsList", resultList);
		result.put("dmPage", resPage);
		
		return result; 		
	}
	
	/**
	 * @Method명   : selectTrprList
	 * @param  HttpServletRequest request, DataRequest dataRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 * @작성자     : Baek.Gyu.Ha
	 * @작성일     : 2023.07.10 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 조회 파라미터
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> paramMap = new HashMap<>(param.getSingleValueMap());
		
		// 결과 반환
		Map<String, Object> result = new HashMap<>();
		
		// 페이징 총 건수 처리
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		Map<String, Object> dmPageReq = new HashMap<>(reqPage.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) dmPageReq.get("pageNo"));
		int rowSize = Integer.parseInt((String) dmPageReq.get("pageRowCount"));
		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		dmPageReq.put("START_IDX", startIndex);
		dmPageReq.put("LAST_IDX", lastIndex);
		
		// 페이징과 기존 데이터맵 병합, 조회 결과 통일
		paramMap.putAll(dmPageReq);
		
		// 로그인 사용자의 정보를 전달
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			paramMap.put("LOGIN_INST_NO", loginVO.getInstNo());
			paramMap.put("USER_ID2", loginVO.getId());
			paramMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		}
		
		// 전체 카운트와 맵 조회
		int totCnt = recipiRegMapper.selectTrprListCount(paramMap);
		List<Map<String, Object>> resultList = recipiRegMapper.selectTrprList(paramMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		// 반환 결과 저장
		result.put("dsTrprList", resultList);
		result.put("dmPage", resPage);
		
		return result; 
		
	}
	
	@Override
	public void saveTrprReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmSaveParam = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSave = new HashMap<>(dmSaveParam.getSingleValueMap());
		dmSave.put("USER_ID2", userId2);
		recipiRegMapper.saveTrprReg(dmSave);
		
	}
	
	@Override
	public void deleteTrprReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmSaveParam = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSave = new HashMap<>(dmSaveParam.getSingleValueMap());
		dmSave.put("USER_ID2", userId2);
		recipiRegMapper.deleteTrprReg(dmSave);
		
	}
	
	/**
	 * @Method명   : selectTrprList
	 * @param  HttpServletRequest request, DataRequest dataRequest
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 * @작성자     : Baek.Gyu.Ha
	 * @작성일     : 2023.07.13 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectPensnGiveDcsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 조회 파라미터
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch2");
		Map<String, Object> paramMap = new HashMap<>(param.getSingleValueMap());
		
		// 결과 반환
		Map<String, Object> result = new HashMap<>();
		
		// 페이징 총 건수 처리
//		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage2");
//		Map<String, Object> dmPageReq = new HashMap<>(reqPage.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
//		int pageIdx = Integer.parseInt((String) dmPageReq.get("pageNo"));
//		int rowSize = Integer.parseInt((String) dmPageReq.get("pageRowCount"));
		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
//		int startIndex = (pageIdx - 1) * rowSize + 1;
//		int lastIndex = startIndex + rowSize - 1;
//		dmPageReq.put("START_IDX", startIndex);
//		dmPageReq.put("LAST_IDX", lastIndex);
		
		// 페이징과 기존 데이터맵 병합, 조회 결과 통일
//		paramMap.putAll(dmPageReq);
		
		// 로그인 사용자의 정보를 전달
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			paramMap.put("LOGIN_INST_NO", loginVO.getInstNo());
			paramMap.put("USER_ID2", loginVO.getId());
			paramMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		}
		
		// 전체 카운트와 맵 조회
		int totCnt = recipiRegMapper.selectPensnGiveDcsnListCount(paramMap);
		List<Map<String, Object>> resultList = recipiRegMapper.selectPensnGiveDcsnList(paramMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
//		resPage.put("pageNo", pageIdx);
//		resPage.put("pageRowCount", rowSize);
		
		// 반환 결과 저장
		result.put("dsPensnGiveDcsnList", resultList);
		result.put("dmPage2", resPage);
		
		return result; 
		
	}
	
	@Override
	public void savePensnGiveDcsnReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmSaveParam = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSave = new HashMap<>(dmSaveParam.getSingleValueMap());
		dmSave.put("USER_ID2", userId2);
		
		// 신규 버튼을 눌러서 저장하는 경우는, 채번 기본 값 또는 순번의 최대 값 + 1 을 부여한다. (삭제 후 재등록 등의 경우 + 1)
		String newYn = dmSave.get("NEW_YN").toString();
		if("Y".equals(newYn) && newYn != null) {
			int taskwkOcrnSqnce = recipiRegMapper.selectTaskwkOcrnSqnce(dmSave);
			dmSave.put("TASKWK_OCRN_SQNCE", taskwkOcrnSqnce);
		}
		
		recipiRegMapper.savePensnGiveDcsnReg(dmSave);
		
	}
	
	@Override
	public void deletePensnGiveDcsnReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmSaveParam = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSave = new HashMap<>(dmSaveParam.getSingleValueMap());
		dmSave.put("USER_ID2", userId2);
		recipiRegMapper.deletePensnGiveDcsnReg(dmSave);
		
	}
	
	@Override
	public void savePensnGiveDcsnSeq(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dsPensnGiveDcsnListParam = dataRequest.getParameterGroup("dsPensnGiveDcsnList");
		List<Map<String, String>> list = dsPensnGiveDcsnListParam.getUpdatedRowList();
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> rowData = new HashMap<>(list.get(i));
			// 목록에서 순번을 꺼내서 저장
			Map<String, Object> dmSave = new HashMap<>();
			dmSave.put("RECIPI_MNG_NO", rowData.get("RECIPI_MNG_NO"));
			dmSave.put("PENSN_GIVE_STTS_SE_CD", rowData.get("PENSN_GIVE_STTS_SE_CD"));
			dmSave.put("PENSN_GIVE_STTS_OCRN_YMD", rowData.get("PENSN_GIVE_STTS_OCRN_YMD"));
			dmSave.put("TASKWK_OCRN_SQNCE", rowData.get("TASKWK_OCRN_SQNCE"));
			dmSave.put("USER_ID2", userId2);
			recipiRegMapper.savePensnGiveDcsnReg(dmSave);

		}
		
	}

}
