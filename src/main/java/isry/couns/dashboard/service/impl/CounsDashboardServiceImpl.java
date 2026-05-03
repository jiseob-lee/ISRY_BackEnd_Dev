/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.dashboard.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.couns.dashboard.mapper.CounsDashboardMapper;
import isry.couns.dashboard.service.CounsDashboardService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService;

/**
 * @파일명        : CounsDashboardServiceImpl.java
 * @프로그램 설명	: 청소년상담 메인화면
 * - 
 * - 
 * @작성자        : Sin.Hyun.Jin
 * @작성일        : 2022. 12. 12. 
 * @수정자        : Sin.Hyun.Jin
 * @수정일        : 2022. 12. 12.
 * @수정내용		: 
 * -                
 * -                
 */
@Service("CounsDashboardService")
public class CounsDashboardServiceImpl extends EgovAbstractServiceImpl implements CounsDashboardService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "counsDashboardMapper")
	private CounsDashboardMapper counsDashboardMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	ScpDb scpDb = new ScpDb();
	
	/**
	 * @Method명 : selectDscsnPrfmncMainList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 20.
	 * @Method설명 : 금월배정및실적건수 조회
	 */
	@Override
	public List<Map<String, String>> selectDscsnPrfmncMainList(Map<String, String> mapParam) throws Exception {
		return counsDashboardMapper.selectDscsnPrfmncMainList(mapParam);
	}
	
	/**
	 * @Method명 : selectDscsnPrfmncDetailList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 20.
	 * @Method설명 : 금월배정및실적건수 조회상세
	 */
	@Override
	public List<Map<String, String>> selectDscsnPrfmncDetailList(Map<String, String> mapParam) throws Exception {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String consttId = StringUtil.nullConvert(mapParam.get("CONSTT_ID"));
		
		if (consttId != null && consttId != "") {
			paramMap = counsDashboardMapper.selectTodayWorkInfoByCnsltnt(mapParam);
			
			try {
				mapParam.putAll(paramMap);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		} 
		
		return counsDashboardMapper.selectDscsnPrfmncDetailList(mapParam);
	}
	
	/**
	 * @Method명 : selectMnthngSchdlcyberDscsnList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 29.
	 * @Method설명 : 오늘의일정 사이버상담
	 */
	@Override
	public List<Map<String, String>> selectMnthngSchdlcyberDscsnList(Map<String, String> mapParam) throws Exception {
		
		List<Map<String, String>> mnthngSchdlcyberDscsnList = counsDashboardMapper.selectMnthngSchdlcyberDscsnList(mapParam);		
		
		return mnthngSchdlcyberDscsnList;
	}
	
	/**
	 * @Method명 : selectMnthngSchdlcyberOutList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 29.
	 * @Method설명 : 오늘의일정 사이버아웃리치
	 */
	@Override
	public List<Map<String, String>> selectMnthngSchdlcyberOutList(Map<String, String> mapParam) throws Exception {
		
		List<Map<String, String>> mnthngSchdlcyberOutList = counsDashboardMapper.selectMnthngSchdlcyberOutList(mapParam);
		
		return mnthngSchdlcyberOutList;
	}
	
	/**
	 * @Method명 : selectMngrMntrgSchdlList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 29.
	 * @Method설명 : 오늘의일정 모니터링 담당자 조회
	 */
	@Override
	public List<Map<String, String>> selectMngrMntrgSchdlList(Map<String, String> mapParam) throws Exception {
		
		List<Map<String, String>> mngrMntrgSchdlList = counsDashboardMapper.selectMngrMntrgSchdlList(mapParam);	
		
		return mngrMntrgSchdlList;
	}
	
	/**
	 * @Method명 : selectBbsonmList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 29.
	 * @Method설명 : 실시간게시판 게시글목록
	 */
	@Override
	public List<Map<String, String>> selectBbsonmList(Map<String, String> mapParam) throws Exception {
		
		List<Map<String, String>> bbsonmList = counsDashboardMapper.selectBbsonmList(mapParam);
		
		return bbsonmList;
	}
	
	/**
	 * @Method명 : selectSpclaList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 12. 29.
	 * @Method설명 : 특별관리대상자 게시글목록
	 */
	@Override
	public List<Map<String, String>> selectSpclaList(Map<String, String> mapParam) throws Exception {
		return counsDashboardMapper.selectSpclaList(mapParam);
	}
	
	/**
	 * @Method명	 : selectLvffcPrcsBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Sin.Hyun.Jin
	 * @작성일  	 : 2022. 12. 29. 
	 * @Method설명 : 퇴근처리 기본정보 조회
	 */
	@Override
	public Map<String,Object> selectLvffcPrcsBassInfo(HttpServletRequest request,DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> rtn = new HashMap<>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("CONSTT_ID", sUserId);
		
		String workYmd = counsDashboardMapper.selectTodayWorkYmd(dmOutcomeDetailMap);
		
		if (workYmd != null && !"".equals(workYmd)) {
			dmOutcomeDetailMap.put("WORK_YMD", workYmd);
		}
		
		rtn = counsDashboardMapper.selectLvffcPrcsBassInfo(dmOutcomeDetailMap);		
		return rtn;
	}
	
	/**
	 * @Method명   : updatelvffcPrcs
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Sin.Hyun.Jin
	 * @작성일     : 2023. 01. 02. 
	 * @Method설명 : 퇴근처리 저장
	 */
	@Override
	public Map<String, String> updatelvffcPrcs(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> mapReturn = new HashMap<String, String>();
		
		String sUserId      = ""; // 세션정보의 유저ID
		String sUserIp      = ""; // 세션정보의 유저IP
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
			sUserIp = loginVO.getIp();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("CONSTT_ID"		, sUserId);
		dmOutcomeDetailMap.put("LVFFC_IP_ADDR"	, sUserIp);
		dmOutcomeDetailMap.put("FRST_RGTR_ID"	, sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID"	, sUserId);
		
		String workYmd = counsDashboardMapper.selectTodayWorkYmd(dmOutcomeDetailMap);
		
		dmOutcomeDetailMap.put("WORK_YMD", workYmd);
		
		LOGGER.debug("WORK_YMD ::: " + workYmd);
		
		int taskwkReprtsCount = counsDashboardMapper.selectCnsltntTaskwkReprtCount(dmOutcomeDetailMap);
		
		if (taskwkReprtsCount > 0) {
			counsDashboardMapper.UpdateLvffcPrcs(dmOutcomeDetailMap); // 상담원출퇴근관리(AYC495)
			mapReturn.put("CONSTT_ID", dmOutcomeDetailMap.get("CONSTT_ID"));
		} else {
			throw new AppWorksException("출근 기록이 없습니다. 업무보고서를 먼저 등록해주세요.", Alert.ERROR);
		}
		
		return mapReturn;
	}
	
	/**
	 * @Method명   : deleteLvffcPrcs
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Sin.Hyun.Jin
	 * @작성일     : 2023. 01. 02. 
	 * @Method설명 : 퇴근처리 취소
	 */
	@Override
	public Map<String, String> deleteLvffcPrcs(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> mapReturn = new HashMap<String, String>();
		
		String sUserId      = ""; // 세션정보의 유저ID
		String sUserIp      = ""; // 세션정보의 유저IP
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
			sUserIp = loginVO.getIp();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("CONSTT_ID"		, sUserId);
		dmOutcomeDetailMap.put("LVFFC_IP_ADDR"	, sUserIp);
		dmOutcomeDetailMap.put("FRST_RGTR_ID"	, sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID"	, sUserId);
		
		String workYmd = counsDashboardMapper.selectTodayWorkYmd(dmOutcomeDetailMap);
		
		dmOutcomeDetailMap.put("WORK_YMD", workYmd);
		
		LOGGER.debug("WORK_YMD ::: " + workYmd);
		
		counsDashboardMapper.deleteLvffcPrcs(dmOutcomeDetailMap); // 상담원출퇴근관리(AYC495)
		mapReturn.put("CONSTT_ID", dmOutcomeDetailMap.get("CONSTT_ID"));		 
		return mapReturn;
	}
	
	/**
	 * @Method명	 : selectLvffcPrcsBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Sin.Hyun.Jin
	 * @작성일  	 : 2022. 12. 29. 
	 * @Method설명 : 다음출근 시간 조회
	 */
	@Override
	public Map<String,Object> selectNextAtndb(HttpServletRequest request,DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> rtn = new HashMap<>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("CONSTT_ID", sUserId);
		
		rtn = counsDashboardMapper.selectNextAtndb(dmOutcomeDetailMap);		
		return rtn;
	}
	
	/**
	 * @Method명	 : selectLvffcPrcsBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Sin.Hyun.Jin
	 * @작성일  	 : 2022. 12. 29. 
	 * @Method설명 : 어제, 오늘출근 시간 조회
	 */
	@Override
	public Map<String,Object> selectAtndb(HttpServletRequest request,DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> rtn = new HashMap<>();
		Map<String, Object> thtdayAtendbDt = new HashMap<>();
		Map<String, Object> whdaAtendbDt = new HashMap<>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("CONSTT_ID", sUserId);
		
		String workYmd = counsDashboardMapper.selectTodayWorkYmd(dmOutcomeDetailMap);
		LOGGER.debug("workYmd ::: " + workYmd);
		
		if (workYmd != null && !"".equals(workYmd)) {
			dmOutcomeDetailMap.put("WORK_YMD", workYmd);
		}
		
//		dmOutcomeDetailMap.put("INQ_SE", "THTDAY");	// 당일		
		thtdayAtendbDt = counsDashboardMapper.selectAtndb(dmOutcomeDetailMap);
		
//		dmOutcomeDetailMap.put("INQ_SE", "WHDA");	// 전일
//		whdaAtendbDt = counsDashboardMapper.selectAtndb(dmOutcomeDetailMap);
		
		if (thtdayAtendbDt != null) {
			rtn.put("THTDAY_ATENDB_DT",	thtdayAtendbDt.get("ATENDB_DT"));	// 오늘 출근 일시
		}
		
		if (whdaAtendbDt != null) {
			rtn.put("WHDA_ATENDB_DT",	whdaAtendbDt.get("ATENDB_DT"));		// 어제 출근 일시
		}								
		
		return rtn;
	}
	
	/**
	 * @Method명 : selectMngrMntrgSchdlList
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Sin Hyun Jin
	 * @작성일 : 2022. 01. 03.
	 * @Method설명 : 공지사항 조회
	 */
	@Override
	public List<Map<String, String>> selectNoticeList(Map<String, String> mapParam) throws Exception {
		return counsDashboardMapper.selectNoticeList(mapParam);
	}

	/**
	 * @Method명   : selectWorkDateTimeByCnsltnt
	 * @param 	   : mapParam
	 * @return	   : Map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 14. 
	 * @Method설명 : 상담원 오늘 근무 정보 조회
	 */
	@Override
	public Map<String, Object> selectTodayWorkInfoByCnsltnt(Map<String, String> mapParam) throws Exception {
//		return counsDashboardMapper.selectTodayWorkInfoByCnsltnt(mapParam);
		return null;
	}
}		
