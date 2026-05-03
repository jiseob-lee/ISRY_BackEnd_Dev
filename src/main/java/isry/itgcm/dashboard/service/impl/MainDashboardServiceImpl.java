/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.dashboard.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import com.cleopatra.protocol.data.ParameterRow;
import com.dreamsecurity.magice2e.util.Log;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcm.crtrinfo.resrce.mapper.SrvcBizMapper;
import isry.itgcm.crtrinfo.resrce.service.SrvcBizService;
import isry.itgcm.dashboard.mapper.MainDashboardMapper;
import isry.itgcm.dashboard.service.MainDashboardService;
import isry.itgcm.linkmng.outsd.mapper.LinkMohwJobMapper;
import isry.itgcm.linkmng.outsd.mapper.LinkMohwSrvcRqstMapper;
import isry.itgcm.linkmng.outsd.mapper.LinkTrprRqstMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userjoin.mapper.ReqUserJoinMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;
import isry.itgcms.wrksupt.docsr.mapper.DocsrMapper;
import isry.redis.service.RedisService;

/**
 * @파일명        : MainDashboardServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 11. 08. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 11. 08.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mainDashboardService")
public class MainDashboardServiceImpl extends EgovAbstractServiceImpl implements MainDashboardService {
	
	@Resource(name = "mainDashboardMapper")
	private MainDashboardMapper mainDashboardMapper;
	
	@Resource(name = "docsrMapper")
	private DocsrMapper docsrMapper;
	
	@Resource(name = "linkTrprRqstMapper")
	private LinkTrprRqstMapper linkTrprRqstMapper;
	
	@Resource(name = "linkMohwSrvcRqstMapper")
	public LinkMohwSrvcRqstMapper linkMohwSrvcRqstMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	@Resource(name="caseRegMapper")
    private CaseRegMapper caseRegMapper;
	
	ScpDb scpDb = new ScpDb();
	String userId = "";

	@Override
	public List<Map<String, Object>> selectMainDashboard(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		List<Map<String, Object>> list = mainDashboardMapper.selectMainDashboard(paramMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> selectDocsCommonList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmMap = paramGroup.getSingleValueMap();
		Map<String, Object> paramMap = new HashMap<String, Object>(dmMap);
		paramMap.put("DOCS_TYPE_CD", "R");
		paramMap.put("LOGIN_ID", loginVO.getId());
		paramMap.put("INST_NO", loginVO.getInstNo());
		paramMap.put("IOBX_SE_CD", "O");
		
		Date dt = new Date();
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		String nowDate = simpleDateFormat.format(dt);
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(dt);
		cal.add(Calendar.MONTH, -3); // 날짜 조회조건이 3개월 전부터
		String strDate = simpleDateFormat.format(cal.getTime());
		paramMap.put("START_DATE", strDate);
		paramMap.put("END_DATE", nowDate);
		
		paramMap.put("FIRST_RECORD_INDEX", 0);
		paramMap.put("PAGE_ROW_COUNT", 5);
		
		List<Map<String, Object>> list = docsrMapper.selectDocsRcvrCommonList(paramMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> selectLinkList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		Map<String,String> param = dmBase.getSingleValueMap();
		
		//의뢰대상기관 - 로그인한 사용자 기관
		String INST_TYPE_SE_CD = loginVO.getInstTypeSeCd(); //기관유형
		int USER_INST_NO = loginVO == null || loginVO.getUserInstNo() == null ? 0 : loginVO.getUserInstNo(); //사용자기관번호
		
		param.put("INST_TYPE_SE_CD", INST_TYPE_SE_CD);
		param.put("USER_INST_NO", String.valueOf(USER_INST_NO));
		param.put("RCPT_SE_CD", "11");
		
		Log.info("#### loginVO, UserName : " + loginVO.getUserName() + ", ID : " + loginVO.getId() + ", UntTaskwk : " + loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		param.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		//List<Map<String, Object>> Linkist  = linkTrprRqstMapper.selectLinkTrprRcptList(paramMap2);
		List<Map<String, Object>> Linkist  = mainDashboardMapper.selectLinkCnt(paramMap2);
		return Linkist;
	}
	
	@Override
	public List<Map<String, Object>> selectWlfarLinkList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("RQST_START_YMD", paramMap.get("PRNMNT_USE_BGNG_YMD"));
		paramMap.put("RQST_END_YMD", paramMap.get("PRNMNT_USE_END_YMD"));
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		/*20230904_운영팀 정현진 복지부 연계 수정*/
//		List<Map<String, Object>> list = linkMohwSrvcRqstMapper.selectMohwSrvcRqstRcptList(paramMap2);
		List<Map<String, Object>> list = mainDashboardMapper.selectLinkMohwCnt(paramMap2);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> selectInnerEmlList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("DOCS_TYPE_CD", "R");
		paramMap.put("LOGIN_ID", loginVO.getId());
		paramMap.put("INST_NO", loginVO.getInstNo());
		paramMap.put("IOBX_SE_CD", "B");
		
		Date dt = new Date();
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		String nowDate = simpleDateFormat.format(dt);
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(dt);
		cal.add(Calendar.MONTH, -3); // 날짜 조회조건이 3개월 전부터
		String strDate = simpleDateFormat.format(cal.getTime());
		paramMap.put("START_DATE", strDate);
		paramMap.put("END_DATE", nowDate);
		paramMap.put("FIRST_RECORD_INDEX", 0);
		paramMap.put("PAGE_ROW_COUNT", 5);
		
		List<Map<String, Object>> list = docsrMapper.selectInnerEmlRcptnList(paramMap);
		for (Map<String, Object> rowMap : list) {
//			if (rowMap.containsKey("SNDPTY_NM")) {
//				rowMap.replace("SNDPTY_NM", scpDb.scpDecB64(StringUtil.nullConvert(rowMap.get("SNDPTY_NM"))));
//			}	
			if (rowMap.containsKey("RCVR_ID")) {
				String[] rcvrIds = StringUtil.nullConvert(rowMap.get("RCVR_ID")).split(",");
				String rcvrId = "";
				if(rcvrIds.length == 1) {
					rcvrId = StringUtil.nullConvert(rowMap.get("RCVR_ID"));
					rowMap.replace("RCVR_ID", rcvrId);
				}else if(rcvrIds.length > 1) {
					rcvrId = rcvrIds[0];
					rowMap.replace("RCVR_ID", rcvrId + " 외 " + (rcvrIds.length - 1) + "명");
				}
			}
			if (rowMap.containsKey("RCVR_NM_ENCPT")) {
				String[] rcvrNms = StringUtil.nullConvert(rowMap.get("RCVR_NM_ENCPT")).split(",");
				String rcvrNm = "";
				if(rcvrNms.length == 1) {
					rcvrNm = StringUtil.nullConvert(rowMap.get("RCVR_NM_ENCPT"));
					rowMap.replace("RCVR_NM_ENCPT", rcvrNm);
				}else if(rcvrNms.length > 1) {
					rcvrNm = rcvrNms[0];
					rowMap.replace("RCVR_NM_ENCPT", rcvrNm + " 외 " + (rcvrNms.length - 1) + "명");
				}
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Object> selectCaseList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("START_DATE", paramMap.get("PRNMNT_USE_BGNG_YMD"));
		paramMap.put("END_DATE", paramMap.get("PRNMNT_USE_END_YMD"));
		
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>(paramMap);
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		/* 2023.06.14 메인화면 사례관련 SQL변경 윤희성 */
		List<Map<String, Object>> list = mainDashboardMapper.selectCaseCnt(paramMap2);
		
		int reg = 0;			// 등록	01
		int plan = 0;			// 계획	02
		int excn = 0;			// 실행	03
		int trmnAply = 0;		// 종결신청	12
		int trmn = 0;			// 사례종결	04
		int aftfctMng = 0;		// 사후관리	06
		int aftfctMngTrmn = 0;	// 사후관리종결	08
		int trmnSum = 0;		// 종결 합계
		
		String modyDate = "";
		
		for(Map<String, Object> listMap : list) {
			if("".equals(modyDate)) {
				modyDate = listMap.get("MODY_DATE").toString();
			}
			String casePrgrsSttsSeCd = listMap.get("CASE_PRGRS_STTS_SE_CD").toString();
			switch (casePrgrsSttsSeCd) {
			case "01":
				reg = Integer.parseInt(listMap.get("CASE_CNT").toString());
				break;
			case "02":
				plan = Integer.parseInt(listMap.get("CASE_CNT").toString());
				break;
			case "03":
				excn = Integer.parseInt(listMap.get("CASE_CNT").toString());
				break;
			case "12":
				trmnAply = Integer.parseInt(listMap.get("CASE_CNT").toString());
				break;
			case "04":
				trmn = Integer.parseInt(listMap.get("CASE_CNT").toString());
				trmnSum += trmn;
				break;
			case "06":
				aftfctMng = Integer.parseInt(listMap.get("CASE_CNT").toString());
				break;
			case "08":
				aftfctMngTrmn = Integer.parseInt(listMap.get("CASE_CNT").toString());
				trmnSum += aftfctMngTrmn;
				break;
			}
		}
		
		Map<String, Object> caseMap = new HashMap<String, Object>();
		caseMap.put("CASE_REG_NOCS", reg);
		caseMap.put("CASE_PLAN_NOCS", plan);
		caseMap.put("CASE_EXCN_NOCS", excn);
		caseMap.put("CASE_TRMN_APLY_NOCS", trmnAply);
		caseMap.put("CASE_TRMN_NOCS", trmn);
		caseMap.put("CASE_AFTFCT_MNG_NOCS", aftfctMng);
		caseMap.put("CASE_AFTFCT_MNG_TRMN_NOCS", aftfctMngTrmn);
		caseMap.put("TRMN_NOCS", trmnSum);
		caseMap.put("LAST_MDFCN_DT", modyDate);
		return caseMap;
	}
	
	
	@Override
	public Map<String, Object> selectMenuId(String untTaskwkSeCd) throws Exception {
		Map<String, Object> caseMap = mainDashboardMapper.selectMenuId(untTaskwkSeCd);
		
		return caseMap;
	}
	
	@Override
	public Map<String, Object> selectJbps(HttpServletRequest request ,DataRequest dataRequest) throws Exception {
		Map<String, Object> caseMap = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		paramMap.put("ENFSN_NO", loginVO.getEnfsnNo());
		caseMap = mainDashboardMapper.selectJbps(paramMap);
		return caseMap;
	}
	
	@Override
	public Map<String, Object> selectDca010(HttpServletRequest request) throws Exception {
		Map<String, Object> caseMap = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		paramMap.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		caseMap = mainDashboardMapper.selectDca010(paramMap);
		return caseMap;
	}
	
	@Override
	public void saveDca010(HttpServletRequest request, Map<String, Object> map) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		map.put("ENFSN_NO", loginVO.getEnfsnNo());
		map.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		map.put("USER_ID", loginVO.getId());
		mainDashboardMapper.saveDca010(map);
	}
}		
