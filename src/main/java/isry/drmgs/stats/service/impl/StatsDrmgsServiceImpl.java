/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stats.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.drmgs.cnter.mapper.CnterPreconEnfsnMapper;
import isry.drmgs.cnter.service.CnterPreconEnfsnService;
import isry.drmgs.stats.mapper.StatsDrmgsMapper;
import isry.drmgs.stats.service.StatsDrmgsService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : StatsDrmgsServiceImpl.java
 * @프로그램 설명 : 학교밖지원센터 통계
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 12. 23. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2022. 12. 23. 
 * @수정내용      : 학교밖청소년지원센터 통계
*/

@Service("statsDrmgsService")
public class StatsDrmgsServiceImpl extends IsryBaseServiceImpl implements StatsDrmgsService {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="statsDrmgsMapper")
	private StatsDrmgsMapper statsDrmgsMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @프로그램 설명 : 직업역량강화프로그램 통계
	 * @작성자        : Hee Sung Yoon
	 * @작성일        : 2023. 01. 26. 
	 * @수정자        : 
	 * @수정일        :  
	 * @수정내용      : 
	*/
	@Override
	public List<Map<String, Object>> selectOccpAbilitStats(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = params.getSingleValueMap();	
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/
		
		/* 2023.05.03 청소년상태 조회조건 추가*/
		if(paramMap.get("YNGBGS_SE_NO") != null && !"".equals(paramMap.get("YNGBGS_SE_NO"))) {
			String yngbgsSeNo = paramMap.get("YNGBGS_SE_NO");
			String[] yngbgs = yngbgsSeNo.split(",");
			for(int i = 0; i < yngbgs.length; i++) {
				String paramKey = "YNGBGS_" + i;
				paramMap2.put(paramKey, yngbgs[i]);
			}
		}
		/* 2023.05.03 청소년상태 조회조건 추가 끝*/
		List<Map<String, Object>> enfsnList = statsDrmgsMapper.selectOccpAbilitStats(paramMap2);

		return enfsnList;
	}
	
	/**
	 * @프로그램 설명 : 학업중단숙려제 통계
	 * @작성자        : Hee Sung Yoon
	 * @작성일        : 2023. 01. 30. 
	 * @수정자        : 
	 * @수정일        :  
	 * @수정내용      : 
	*/
	@Override
	public List<Map<String, Object>> selectMeditationStats(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = params.getSingleValueMap();
		
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		/* 2023.05.03 청소년상태 조회조건 추가*/
		if(paramMap.get("YNGBGS_SE_NO") != null && !"".equals(paramMap.get("YNGBGS_SE_NO"))) {
			String yngbgsSeNo = paramMap.get("YNGBGS_SE_NO");
			String[] yngbgs = yngbgsSeNo.split(",");
			for(int i = 0; i < yngbgs.length; i++) {
				String paramKey = "YNGBGS_" + i;
				paramMap2.put(paramKey, yngbgs[i]);
			}
		}
		/* 2023.05.03 청소년상태 조회조건 추가 끝*/
		List<Map<String, Object>> enfsnList = statsDrmgsMapper.selectMeditationStats(paramMap2);
		
		return enfsnList;
	}
	
	/**
	 * @프로그램 설명 : 경기도사업 통계
	 * @작성자        : Hee Sung Yoon
	 * @작성일        : 2023. 01. 30. 
	 * @수정자        : 
	 * @수정일        :  
	 * @수정내용      : 
	*/
	@Override
	public List<Map<String, Object>> selectGgBizStats(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup params = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = params.getSingleValueMap();
		Map<String, Object> subMap = new HashMap<String, Object>(paramMap);
		String cmmnsCdId = "GG_BIZ_STATS_SCLAS_SE_CD";
		List<Map<String, Object>> ggCodeList = statsDrmgsMapper.selectGgCodeList(cmmnsCdId);
		for(int i = 0; i < ggCodeList.size(); i++) {
			List<String> list = new ArrayList<String>();
			Map<String, Object> map = ggCodeList.get(i);
			String value1 = map.get("ADDTNG_MNG_VALUE1").toString();
			String valueList[] = value1.split(",");
			for(int j = 0; j < valueList.length; j++) {
				list.add(valueList[j]);
			}
			String cmmsCdValue = "CODE_" + i;
			subMap.put(cmmsCdValue, list);
		}
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		subMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/
		
		// 20230417 경기도사업통계 기관번호 코드화
		cmmnsCdId = "GG_BIZ_INST_NO_SE_CD";
		List<Map<String, Object>> ggBizInstNoList = statsDrmgsMapper.selectGgCodeList(cmmnsCdId);
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		String filStr  = "";
		if("real1".equals(profile) || "real2".equals(profile)) { // gov
			filStr = "ADDTNG_MNG_VALUE2";
		} else {
			filStr = "ADDTNG_MNG_VALUE1";
		}
		
		String statsSe =  paramMap.get("STATS_SE"); // 조회 구분
		List<Integer> ggInstNos = new ArrayList<Integer>();
		
		for(Map<String, Object> map : ggBizInstNoList) {
			if(statsSe == null || "".equals(statsSe)) { // 전체 조회
				//instNos에 경기도사업과 경기도추가사업 inst_no를 담는디
				ggInstNos.add(Integer.parseInt(map.get(filStr).toString()));
			} else { // 전체 조회가 아닌 경우
				if(statsSe.equals(map.get("CMMNS_CD_VALUE").toString())) {
					// 해당하는 inst_no를 담는다
					ggInstNos.add(Integer.parseInt(map.get(filStr).toString()));
				}
			}
		}
		paramMap2.put("GG_INST_NO", ggInstNos);
		List<Map<String, Object>> enfsnList = statsDrmgsMapper.selectGgBizStats(paramMap2);
		List<Map<String, Object>> trprList = statsDrmgsMapper.selectGgBizStatsTrpr(paramMap2);
		for(Map<String, Object> enfsnMap : enfsnList) {
			for(Map<String, Object> trprMap : trprList) {
				if(enfsnMap.get("INST_NO").equals(trprMap.get("INST_NO"))) {
					enfsnMap.put("CASE_SUM", trprMap.get("TRPR_CNT"));
				}
			}
		}
		
		return enfsnList;
	}

}

