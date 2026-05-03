/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.splymtenblr.service.impl;

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

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.pubms.slfrlsprtpensn.splymtenblr.mapper.SplymtEnblrMapper;
import isry.pubms.slfrlsprtpensn.splymtenblr.service.SplymtEnblrService;

/**
 * @파일명        : SplymtEnblrServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.07.26
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.07.26
 * @수정내용      : 
 * - [2023-08-30, Gyu.Ha.Baek] PRE 반영       
 * -                
 */
@Service("splymtEnblrService")
public class SplymtEnblrServiceImpl implements SplymtEnblrService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "splymtEnblrMapper")
	private SplymtEnblrMapper splymtEnblrMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명   : selectSplymtEnblrMtchngList
	 * @param  HttpServletRequest request, DataRequest dataRequest
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 * @작성자     : Baek.Gyu.Ha
	 * @작성일     : 2023.07.26
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectSplymtEnblrMtchngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
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
		int totCnt = splymtEnblrMapper.selectSplymtEnblrMtchngListCount(paramMap);			
		List<Map<String, Object>> resultList = splymtEnblrMapper.selectSplymtEnblrMtchngList(paramMap);
		
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
	 * @Method명   : selectSplymtEnblrDtlList
	 * @param  HttpServletRequest request, DataRequest dataRequest
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 * @작성자     : Baek.Gyu.Ha
	 * @작성일     : 2023.08.01
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectSplymtEnblrDtlList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 조회 파라미터
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> paramMap = new HashMap<>(param.getSingleValueMap());
		
		// 결과 반환
		Map<String, Object> result = new HashMap<>();
		
		/*
		 * // 페이징 총 건수 처리 ParameterGroup reqPage =
		 * dataRequest.getParameterGroup("dmPage"); Map<String, Object> dmPageReq = new
		 * HashMap<>(reqPage.getSingleValueMap());
		 * 
		 * //페이지 인덱싱에 필요한 정보를 정제합니다. int pageIdx = Integer.parseInt((String)
		 * dmPageReq.get("pageNo")); int rowSize = Integer.parseInt((String)
		 * dmPageReq.get("pageRowCount"));
		 * 
		 * //쿼리에서 사용할 파라미터를 지정해줍니다. int startIndex = (pageIdx - 1) * rowSize + 1; int
		 * lastIndex = startIndex + rowSize - 1; dmPageReq.put("START_IDX", startIndex);
		 * dmPageReq.put("LAST_IDX", lastIndex);
		 * 
		 * // 페이징과 기존 데이터맵 병합, 조회 결과 통일 paramMap.putAll(dmPageReq);
		 */
		
		// 전체 카운트와 맵 조회
		int totCnt = splymtEnblrMapper.selectSplymtEnblrDtlListCount(paramMap);			
		List<Map<String, Object>> resultList = splymtEnblrMapper.selectSplymtEnblrDtlList(paramMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt); 
//		  resPage.put("pageNo", pageIdx);
//		  resPage.put("pageRowCount", rowSize);
		 
		
		// 반환 결과 저장
		result.put("dsList", resultList);
		result.put("dmPage", resPage);
						
		/*
		 * // 페이징 총 건수 처리 ParameterGroup reqPage2 =
		 * dataRequest.getParameterGroup("dmPage2"); Map<String, Object> dmPageReq2 =
		 * new HashMap<>(reqPage2.getSingleValueMap());
		 * 
		 * //페이지 인덱싱에 필요한 정보를 정제합니다. int pageIdx2 = Integer.parseInt((String)
		 * dmPageReq2.get("pageNo")); int rowSize2 = Integer.parseInt((String)
		 * dmPageReq2.get("pageRowCount"));
		 * 
		 * //쿼리에서 사용할 파라미터를 지정해줍니다. int startIndex2 = (pageIdx2 - 1) * rowSize2 + 1; int
		 * lastIndex2 = startIndex2 + rowSize2 - 1; dmPageReq2.put("START_IDX2",
		 * startIndex2); dmPageReq2.put("LAST_IDX2", lastIndex2);
		 * 
		 * // 페이징과 기존 데이터맵 병합, 조회 결과 통일 paramMap.putAll(dmPageReq2);
		 */
		
		// 전체 카운트와 맵 조회
		int totCnt2 = splymtEnblrMapper.selectSplymtEnblrDtlSEB900ListCount(paramMap);
		List<Map<String, Object>> resultList2 = splymtEnblrMapper.selectSplymtEnblrDtlSEB900List(paramMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage2 = new HashMap<String, Object>();
		resPage2.put("totalCount", totCnt2);		
//		resPage2.put("pageNo", pageIdx2); 
//		resPage2.put("pageRowCount", rowSize2);		 
		
		// 반환 결과 저장
		result.put("dsSebList", resultList2);
		result.put("dmPage2", resPage2);
		
		return result; 		
	}
	
	/**
	 * @Method명   : selectMtchngList
	 * @param  HttpServletRequest request, DataRequest dataRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 * @작성자     : Baek.Gyu.Ha
	 * @작성일     : 2023.08.03 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectMtchngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
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
		
		// 전체 카운트와 맵 조회
		int totCnt = splymtEnblrMapper.selectMtchngListCount(paramMap);
		List<Map<String, Object>> resultList = splymtEnblrMapper.selectMtchngList(paramMap);
		
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
	
	@Override
	public void updateMtchngReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		ParameterGroup dmSaveParam = dataRequest.getParameterGroup("dmSave");
		Map<String, Object> dmSave = new HashMap<>(dmSaveParam.getSingleValueMap());
		dmSave.put("USER_ID2", userId2);
		splymtEnblrMapper.updateMtchngReg(dmSave);
		
	}
	
}
