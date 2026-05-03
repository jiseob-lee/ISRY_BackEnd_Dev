/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.unitystats.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcm.unitystats.mapper.UnitystatsMapper;
import isry.itgcm.unitystats.service.UnitystatsService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : UnitystatsServiceImpl.java
 * @프로그램 설명 : 공통통계 Service Impl - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 1. 9.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 1. 9.
 * @수정내용 : - -
 */
@Service("unitystatsService")
public class UnitystatsServiceImpl implements UnitystatsService {

	@Resource(name = "UnitystatsMapper")
	UnitystatsMapper unitystatsMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;

	/**
	 * @Method명 : selectUneartMngStatsList
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 9.
	 * @Method설명 : 1.발굴관리통계
	 */
	@Override
	public List<Map<String, Object>> selectUneartMngStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	

		return unitystatsMapper.selectUneartMngStatsList(paramMap2);
	}

	/**
	 * @Method명 : selectYngbgsCaseMngStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 9.
	 * @Method설명 : 3.청소년구분별사례관리통계
	 */
	@Override
	public List<Map<String, Object>> selectYngbgsCaseMngStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		return unitystatsMapper.selectYngbgsCaseMngStatsList(paramMap2);
	}

	/**
	 * @Method명 : selectProbmSttsCaseMsgStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 4.문제상태별사례관리통계
	 */
	@Override
	public List<Map<String, Object>> selectProbmSttsCaseMsgStatsList(HttpServletRequest request,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	

		return unitystatsMapper.selectProbmSttsCaseMsgStatsList(paramMap2);
	}

	/**
	 * @Method명   : selectSprtSrvcStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 : 5.지원서비스통계
	 */
	@Override
	public List<Map<String, Object>> selectSprtSrvcStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	

		return unitystatsMapper.selectSprtSrvcStatsList(paramMap2);
		
	}
	
	/**
	 * @Method명 : selectOutStatsPubmsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2023. 1. 11.
	 * @Method설명 : 6-1.성과통계(학교밖) / 6-2.성과통계(쉼터)
	 */
	@Override
	public List<Map<String, Object>> selectOutStatsPubmsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		return unitystatsMapper.selectOutStatsPubmsList(paramMap2);
	}
	
	/**
	 * @Method명 : selectOutStatsPubmtList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 6-3.성과통계(자립지원관)
	 */
	@Override
	public List<Map<String, Object>> selectOutStatsPubmtList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		return unitystatsMapper.selectOutStatsPubmtList(paramMap2);
	}

	/**
	 * @Method명 : selectDscsnOutrcMngStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 9.아웃리치통계(쉼터)
	 */
	@Override
	public List<Map<String, Object>> selectDscsnOutrcMngStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		return unitystatsMapper.selectDscsnOutrcMngStatsList(paramMap2);
	}

	/**
	 * @Method명 : selectTlphonDscsnMngStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 11.
	 * @Method설명 : 10.1388전화상담통계(상담복지센터)
	 */
	@Override
	public List<Map<String, Object>> selectTlphonDscsnMngStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		return unitystatsMapper.selectTlphonDscsnMngStatsList(paramMap2);
	}

	/**
	 * @Method명 : selectEmrgIntrvnMngStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 10.
	 * @Method설명 : 11.긴급구조통계(지자체안정망,상담복지센터)
	 */
	@Override
	public List<Map<String, Object>> selectEmrgIntrvnMngStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/
		return unitystatsMapper.selectEmrgIntrvnMngStatsList(paramMap2);
	}

	/**
	 * @Method명   : selectCaseMngBassStatsList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCaseMngBassStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		dmSearch.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		return unitystatsMapper.selectCaseMngBassStatsList(paramMap2);
	}
}
