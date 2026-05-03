/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.eryycose.service.impl;

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

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.stats.eryycose.mapper.EryyCoseMapper;
import isry.subms.stats.eryycose.service.EryyCoseService;

/**
 * @파일명 : EryyCoseServiceImpl.java
 * @프로그램 설명 : 초기진로 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 30.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 30.
 * @수정내용 : - -
 */
@Service("eryyCoseService")
public class EryyCoseServiceImpl extends IsryBaseServiceImpl implements EryyCoseService {

	// 초기진로 관련 매퍼
	@Resource(name = "eryyCoseMapper")
	EryyCoseMapper eryyCoseMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	/**
	 * @Method명 : selectSemstrNm
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 : 학기명 조회
	 */
	@Override
	public Map<String, Object> selectSemstrNm(List<Map<String, Object>> map) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		for (int idx = 0; idx < map.size(); idx++) {
			result.put("SEMSTR" + (idx + 1), map.get(idx).get("CMMNS_CD_VALUE_NM"));
		}
		return result;
	}

	/**
	 * @Method명 : selectExcnBizSemstr
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 : 서비스실행사업에 해당하는 학기 조회
	 */
	@Override
	public List<Map<String, Object>> selectExcnBizSemstr() throws Exception {
		return eryyCoseMapper.selectExcnBizSemstr();
	}

	/**
	 * @Method명 : selectInstPrgrsPrfmncList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 30.
	 * @Method설명 : 기관별 추진실적 통계목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectInstPrgrsPrfmncList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectInstPrgrsPrfmncList(inMap);
	}

	/**
	 * @Method명 : selectCharPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 30.
	 * @Method설명 : 특성별 현황 통계목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectAgePreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectAgePreconList(inMap);
	}

	/**
	 * @Method명 : selectLinkPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 연계현황 통계목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectLinkPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectLinkPreconList(inMap);
	}

	/**
	 * @Method명 : selectBrthNtnPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 : 출생국가별 현황 통계목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectBrthNtnPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectBrthNtnPreconList(inMap);
	}

	/**
	 * @Method명 : selectVisaTypePreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 비자유형별 현황 통계목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectVisaTypePreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectVisaTypePreconList(inMap);
	}

	/**
	 * @Method명 : selectTrprTypePreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTrprTypePreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectTrprTypePreconList(inMap);
	}

	/**
	 * @Method명   : selectBrthNtnPreconList2
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 출생국가별현황2
	 */
	@Override
	public List<Map<String, Object>> selectBrthNtnPreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectBrthNtnPreconList2(inMap);

	}

	/**
	 * @Method명   : selectGrowthNtnPreconList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 성장국가별현황
	 */
	@Override
	public List<Map<String, Object>> selectGrowthNtnPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectGrowthNtnPreconList(inMap);
		
	}

	/**
	 * @Method명   : selectNowNltyPreconList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectNowNltyPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectNowNltyPreconList(inMap);
	}

	/**
	 * @Method명   : selectAcbgPreconList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 학력별현황
	 */
	@Override
	public List<Map<String, Object>> selectAcbgPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectAcbgPreconList(inMap);
	}

	/**
	 * @Method명   : selectAgePreconList2
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 2차통계 연령별현황2
	 */
	@Override
	public List<Map<String, Object>> selectAgePreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectAcbgPreconList2(inMap);
	}

	/**
	 * @Method명   : selectSxdcPreconList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 성별현황
	 */
	@Override
	public List<Map<String, Object>> selectSxdcPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectSxdcPreconList(inMap);
	}

	/**
	 * @Method명   : selectLinkPreconList2
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 연계현황2
	 */
	@Override
	public List<Map<String, Object>> selectLinkPreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectLinkPreconList2(inMap);
	}

	/**
	 * @Method명   : selectVisaTypePreconList2
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 비자유형현황2
	 */
	@Override
	public List<Map<String, Object>> selectVisaTypePreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectVisaTypePreconList2(inMap);
	}

	/**
	 * @Method명   : selectTrprTypePreconList2
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 대상자유형별현황2
	 */
	@Override
	public List<Map<String, Object>> selectTrprTypePreconList2(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectTrprTypePreconList2(inMap);
	}

	/**
	 * @Method명   : selectTrlSoctyAdaptInspYnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 9. 5. 
	 * @Method설명 : 2차통계 심리사회적응검사여부
	 */
	@Override
	public List<Map<String, Object>> selectTrlSoctyAdaptInspYnList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		
		inMap.putAll(dmSearch.getAllRowList().get(0));
		inMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		inMap.put("INST_NOS", comMap.get("INST_NOS"));

		return eryyCoseMapper.selectTrlSoctyAdaptInspYnList(inMap);
	}
	
	/**
	 * @Method명 : selectKlangLevelEvl
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 9. 04.
	 * @Method설명 : 한국어평가(레벨테스트)
	 */
	@Override
	public List<Map<String, Object>> selectKlangLevelEvl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmSearch = dmParam.getAllRowList().get(0);
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		return eryyCoseMapper.selectKlangLevelEvl(paramMap2);
	}

	/**
	 * @Method명   : selectKlangMiddleEvl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 한국어평가(중간테스트)
	 */
	@Override
	public List<Map<String, Object>> selectKlangMiddleEvl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
				
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmSearch = dmParam.getAllRowList().get(0);
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		return eryyCoseMapper.selectKlangMiddleEvl(paramMap2);
	}

	/**
	 * @Method명   : selectKlangSccesdEvl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 9. 4. 
	 * @Method설명 : 한국어평가(성취도평가)
	 */
	@Override
	public List<Map<String, Object>> selectKlangSccesdEvl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> dmSearch = dmParam.getAllRowList().get(0);
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		return eryyCoseMapper.selectKlangSccesdEvl(paramMap2);
	}
}
