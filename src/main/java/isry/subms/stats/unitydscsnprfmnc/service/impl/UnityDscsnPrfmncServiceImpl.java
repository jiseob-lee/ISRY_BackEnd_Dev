/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.unitydscsnprfmnc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.stats.unitydscsnprfmnc.mapper.UnityDscsnPrfmncMapper;
import isry.subms.stats.unitydscsnprfmnc.service.UnityDscsnPrfmncService;

/**
 * @파일명        : UnityDscsnPrfmncServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 7. 7. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 7. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("unityDscsnPrfmncService")
public class UnityDscsnPrfmncServiceImpl implements UnityDscsnPrfmncService {

	@Resource(name = "unityDscsnPrfmncMapper")
	private UnityDscsnPrfmncMapper unityDscsnPrfmncMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명   : selectprobmTypePrfmncList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 상담통계 - 성별상담통계
	 */
	@Override
	public List<Map<String, String>> selectSxdcDscsnStatsList(HttpServletRequest request,DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = parameterGroup.getAllRowList().get(0);
		
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		List<Map<String, String>> resultList = unityDscsnPrfmncMapper.selectSxdcDscsnStatsList(paramMap2);
		
		return resultList;
	}
	
	/**
	 * @Method명   : selectTrprTypeDscsnStatsList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 상담통계 - 대상자유형별 상담통계
	 */
	@Override
	public List<Map<String, String>> selectTrprTypeDscsnStatsList(HttpServletRequest request,DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = parameterGroup.getAllRowList().get(0);
		
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		List<Map<String, String>> resultList = unityDscsnPrfmncMapper.selectTrprTypeDscsnStatsList(paramMap2);
		
		return resultList;
	}
	
	/**
	 * @Method명   : selectPvsnMtdAndTrprTypeDscsnStatsList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 상담통계 - 제공방식, 대상자유형별 상담통계
	 */
	@Override
	public List<Map<String, String>> selectPvsnMtdAndTrprTypeDscsnStatsList(HttpServletRequest request,DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = parameterGroup.getAllRowList().get(0);
		
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		List<Map<String, String>> resultList = unityDscsnPrfmncMapper.selectPvsnMtdAndTrprTypeDscsnStatsList(paramMap2);
		
		return resultList;
	}
	
	/**
	 * @Method명   : selectAgeDscsnStatsList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 상담통계 - 연령별 상담실적
	 */
	@Override
	public List<Map<String, String>> selectAgeDscsnStatsList(HttpServletRequest request,DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = parameterGroup.getAllRowList().get(0);
		
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		List<Map<String, String>> resultList = unityDscsnPrfmncMapper.selectAgeDscsnStatsList(paramMap2);
		
		return resultList;
	}
	
	/**
	 * @Method명   : selectYngbgsSttsDscsnStatsList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 : 상담통계 - 청소년상태별 상담통계
	 */
	@Override
	public List<Map<String, String>> selectYngbgsSttsDscsnStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = parameterGroup.getAllRowList().get(0);
		
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		List<Map<String, String>> resultList = unityDscsnPrfmncMapper.selectYngbgsSttsDscsnStatsList(paramMap2);
		
		return resultList;
	}

	/**
	 * @Method명   : selectPvsnMtdDscsnList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 제공방식별 상담통계
	 */
	@Override
	public List<Map<String, String>> selectPvsnMtdDscsnStatsList(HttpServletRequest request,DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = parameterGroup.getAllRowList().get(0);
		
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		List<Map<String, String>> resultList = unityDscsnPrfmncMapper.selectPvsnMtdDscsnStatsList(paramMap2);
		
		return resultList;
	}

	/**
	 * @Method명   : selectRegion
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 시도 구분코드
	 */
	@Override
	public List<Map<String, Object>> selectRegion() throws Exception {
		return unityDscsnPrfmncMapper.selectRegion();
	}

	/**
	 * @Method명   : selectRegion2
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 시군구 구분코드
	 */
	@Override
	public List<Map<String, Object>> selectRegion2() throws Exception {
		return unityDscsnPrfmncMapper.selectRegion2();
	}

}
