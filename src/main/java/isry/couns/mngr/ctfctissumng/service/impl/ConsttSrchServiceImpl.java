/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.ctfctissumng.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.google.common.collect.Maps;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.ctfctissumng.mapper.ConsttSrchMapper;
import isry.couns.mngr.ctfctissumng.service.ConsttSrchService;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : ConsttSrchServiceImpl.java
 * @프로그램 설명 : 상담사 검색 Service 구현체
 * - 
 * - s
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 10. 31. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 10. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("consttSrchService")
public class ConsttSrchServiceImpl extends IsryBaseServiceImpl implements ConsttSrchService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsttSrchServiceImpl.class);
	
	/**
	 * DB 암복호화 모듈
	 */
	private final ScpDb scpDb = new ScpDb();
	
	@Resource(name = "consttSrchMapper")
	private ConsttSrchMapper mapper;
	
	/**
	 * @Method명   : selectConsttList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 31. 
	 * @Method설명 : 상담사 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectConsttList(DataRequest dataRequest, 
			Map<String, Object> resPage) throws Exception {
		
		// 검색조건 Parameter
		Map<String, Object> mapParam = Maps.newHashMap();
		
		// 화면에서 넘어온 파라미터
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		LOGGER.debug("resultMap ::: " + searchParam.getSingleValueMap());
		
		// 파라미터 값 조회 및 설정
		mapParam.put("unitTaskWkCd", searchParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("deptCd", searchParam.getValue("OGDP_DEPT_CD"));
		mapParam.put("WORK_YMD", searchParam.getValue("WORK_YMD"));
		mapParam.put("MENU_ROUTE", searchParam.getValue("MENU_ROUTE"));
		
		// 사원번호 (사용자아이디로 임시 대체)
		String empNo = searchParam.getValue("EMP_NO");
		mapParam.put("userId", empNo);
		
		// 이름 (평문)
		String flnm = searchParam.getValue("FLNM");
		
		// 조회조건에 이름이 있는 경우 이름 암호화
		if (StringUtils.hasText(flnm)) {
			String flnmEncpt = flnm;
			mapParam.put("flnmEncpt", flnmEncpt);
		} else {
			mapParam.put("flnmEncpt", "");
		}
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = mapper.selectConsttCount(mapParam);
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		
		// 쿼리에서 사용할 파라미터를 지정해줍니다.
//		int startIndex = (pageIdx - 1) * rowSize + 1;
//		int lastIndex = startIndex + rowSize - 1;
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
//		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
//		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
//		int startIndex = (pageIdx - 1) * rowSize;
//		int totalCount = 0;
//		mapParam.put("startIdx", startIndex); mapParam.put("rowCount", rowSize);
		
		// 목록 조회
		List<Map<String, Object>> consttList = mapper.selectConsttList(mapParam);
		
		// 암호화된 칼럼 복호화 처리
		String[] keys = new String[] { 
				"MBL_TELNO"		// 휴대전화번호
				, "EML_ADDR"	// 이메일주소
				, "FLNM"		// 성명
		};
//		CounsUtils.decodeColumns(consttList, keys);
		
		if (consttList != null) {
			consttList.forEach(map -> {
				// 생년월일 포맷 변환
				if (map.containsKey("BRTH_YMD")) {
					String birthDay = DateUtil.formatDate(map.get("BRTH_YMD").toString(), "-");
					map.replace("BRTH_YMD", birthDay);
					
					// 생년월일 마스킹
//					try {
//						String birth = Masking.birthMaskingDay(map.get("BRTH_YMD").toString());
//						map.replace("BRTH_YMD", birth);
//					} catch (Exception e) {}
				}
			});
			
			// 성명 필터링 (성명이 DB 암호화되어 있어서 로직으로 필터링 처리)
//			if (StringUtils.hasText(flnm)) {
//				consttList = consttList.stream()
//						.filter(map -> map.get("FLNM").toString().contains(flnm))
//						.collect(Collectors.toList());	
//			}
		}
		
		// 데이터맵에 저장할 데이터를 지정해줍니다.
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		return consttList;
	}
}
