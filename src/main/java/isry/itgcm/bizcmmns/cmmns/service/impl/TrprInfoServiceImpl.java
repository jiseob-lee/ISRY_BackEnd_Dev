/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.TrprInfoMapper;
import isry.itgcm.bizcmmns.cmmns.service.TrprInfoService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
* @Class Name  : TrprInfoService.java
* @Description : 대상자정보조회 팝업 ServiceImpl Class
*
* @author  : Yoo.Chi.Hoon
* @since   : 2022. 05. 11.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 11.  Yoo.Chi.Hoon    최초작성
* </pre>
*/
@Service("trprInfoService")
public class TrprInfoServiceImpl extends IsryBaseServiceImpl implements TrprInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="trprInfoMapper")
    private TrprInfoMapper trprInfoMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	/**
	* 대상자정보 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public Map<String, Object> selectTrprInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> retList = new ArrayList<>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("조회할 대상자가 없습니다..", Alert.ERROR);
		}
		
		Map<String, Object> retMap = new HashMap<>();
		
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");		
		
		Map<String, String> paramMap  = searchParam.getSingleValueMap();

		// 세션정보
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		
		String sUntTaskwkSeCd = searchParam.getValue("UNT_TASKWK_SE_CD"); // 단위업무구분코드(담당자의 단위업무)
		String sUntTaskwk = searchParam.getValue("UNT_TASKWK"); // 단위업무구분코드(좌측상단체크박스)
		
		// 화면상의 단위업무 구분값 (동일)
		LOGGER.debug("sUntTaskwkSeCd =[" + sUntTaskwkSeCd+ "]");
		LOGGER.debug("sUntTaskwk     =[" + sUntTaskwk + "]");
		
		/* 단위업무동일여부*/
		if(sUntTaskwkSeCd.equals(sUntTaskwk)) {
			paramMap.put("unitEquals", "true");
		}else if(! sUntTaskwkSeCd.equals(sUntTaskwk)) {
			paramMap.put("unitEquals", "false");
		}
		LOGGER.debug("searchParam.paramMap.unitEquals=[" + paramMap.get("unitEquals") + "]");
		
		// 화면(script)에 직접 설정한 상태코드(CHK_CD) 값 확인 Log
		LOGGER.debug("searchParam.paramMap.CHK_CD = [" + paramMap.get("CHK_CD") + "]");
		paramMap.put("UNT_TASKWK_SE_CD", sUntTaskwk);
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		
		int trprInfoCnt = trprInfoMapper.getTrprInfoList(paramMap2);
		paramMap2.put("TOT_CNT", trprInfoCnt);	
		
		int totCnt  = trprInfoCnt; /* 전체ROW*/
		int pageIdx = Integer.parseInt(reqPage.getValue("pageNo")); /* page번호*/
		int rowSize = Integer.parseInt(reqPage.getValue("pageRowCount")); /* pageRow수*/
		
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex  = startIndex + rowSize - 1;
		
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);			
		
		//20230629 이승재 - 증명서 발급 시 팝업 띄울 경우 종사자 권한별 가시 여부 체크 시작
		if(paramMap2.containsKey("CRTF_CODE")) {
			if(paramMap2.get("CRTF_CODE").equals("Y")) {
				paramMap2.put("CRTF_CODE", "Y");
			}
			else {
				paramMap2.put("CRTF_CODE", "N");
			}
		}else {
			paramMap2.put("CRTF_CODE", "N");
		}
		//20230629 이승재 - 증명서 발급 시 팝업 띄울 경우 종사자 권한별 가시 여부 체크 시작
		
		// 대상자 목록 조회
		retList = trprInfoMapper.selectTrprInfoList(paramMap2);
		
		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);		
		
		retMap.put("dsTrprInfo", retList);
		retMap.put("dmPage", pageMap);			
		
		return retMap;		
	}
	
	/**
	 * @Method명   : selectTrprInfoInqList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectTrprInfoInqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");
		if (searchParam == null) {
			throw new AppWorksException("조회할 대상자가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectTrprInfoInqList.searchParam=[" + searchParam +"]");
		Map<String, String> paramMap  = searchParam.getSingleValueMap();
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		
		String cnt = "";
		int totCnt  = 0;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		cnt = trprInfoMapper.selectTrprInfoInqCount(paramMap2);
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> retList = trprInfoMapper.selectTrprInfoInqPagingList(paramMap2);
		
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		Map<String, Object> result = new HashMap<>();
		result.put("list", retList);
		result.put("dmPageInfo", resPage);

		return result;
	}

}
