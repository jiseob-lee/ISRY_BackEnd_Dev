/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

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
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.SrvcResrcePopMapper;
import isry.itgcm.bizcmmns.cmmns.service.SrvcResrcePopService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


/**
* @Class Name  : SrvcResrcePopServiceImpl.java
* @Description : 자원제공서비스목록(팝업) ServiceImpl Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 11.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 11.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Service("srvcResrcePopService")
public class SrvcResrcePopServiceImpl extends EgovAbstractServiceImpl implements SrvcResrcePopService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="srvcResrcePopMapper")
    private SrvcResrcePopMapper srvcResrcePopMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method     : selectSrvcResrceList
	 * @Method설명 : 자원제공서비스 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 11. 
 	 */	
	@Override
	public List<Map<String, Object>> selectSrvcResrceList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		// oUntTaskwk = loginVO.getUntTaskwk();
		LOGGER.debug("selectSrvcResrceList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		//Map<String, String> paramMap = new HashMap<>();
		
		String sSrvcYr		 = "";
		String sResrcdNo     = "";
		String sPvsnResrceNm = "";
		String sRsfrInstNm   = "";
		String sRsfrInstNo   = "";
		String sLclasSeCd    = "";
		String sMclasSeCd    = "";
		String sSclasSeCd    = "";
		String sDtlSeCd      = "";
		
		// 서비스연도	
		sSrvcYr = paramGroup.getValue("SRVC_YR");  
		if (sSrvcYr==null || sSrvcYr.equals("null") || sSrvcYr.equals("")) {
			throw new AppWorksException("서비스연도는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
			//sSrvcYr = "2022";
			//paramMap.put("SRVC_YR",                 sSrvcYr);
		}
		
		// 자원번호
		sResrcdNo = paramGroup.getValue("RESRCE_NO");  
		if (sResrcdNo==null || sResrcdNo.equals("null") || sResrcdNo.equals("")) {
			sResrcdNo = "";
			//paramMap.put("RESRCE_NO",               sResrcdNo);
		}
		
		// 제공자원명
		sPvsnResrceNm = paramGroup.getValue("PVSN_RESRCE_NM");  
		if (sPvsnResrceNm==null || sPvsnResrceNm.equals("null") || sPvsnResrceNm.equals("")) {
			sPvsnResrceNm = "";
			//paramMap.put("PVSN_RESRCE_NM",          sPvsnResrceNm);
		}
		
		// 자원제공주체명
		sRsfrInstNm = paramGroup.getValue("RSFR_INST_NM");  
		if (sRsfrInstNm==null || sRsfrInstNm.equals("null") || sRsfrInstNm.equals("")) {
			sRsfrInstNm = "";
			//paramMap.put("RSFR_INST_NM",          sRsfrInstNm);
		}
				
		// 자원제공주체번호
		sRsfrInstNo = paramGroup.getValue("RSFR_INST_NO");  
		if (sRsfrInstNo==null || sRsfrInstNo.equals("null") || sRsfrInstNo.equals("")) {
			sRsfrInstNo = "";
			//paramMap.put("RSFR_INST_NO",            sRsfrInstNo);
		}
		
		// 자원제공서비스대분류구분코드
		sLclasSeCd = paramGroup.getValue("SRVC_RESRCE_LCLAS_SE_CD");  
		if (sLclasSeCd==null || sLclasSeCd.equals("null") || sLclasSeCd.equals("")) {
			sLclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_LCLAS_SE_CD", sLclasSeCd);
		}
		
		// 자원제공서비스중분류구분코드
		sMclasSeCd = paramGroup.getValue("SRVC_RESRCE_MLSFC_SE_CD");  
		if (sMclasSeCd==null || sMclasSeCd.equals("null") || sMclasSeCd.equals("")) {
			sMclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_MLSFC_SE_CD", sMclasSeCd);
		}
		
		// 자원제공서비스소분류구분코드
		sSclasSeCd = paramGroup.getValue("SRVC_RESRCE_SCLAS_SE_CD");
		if (sSclasSeCd==null || sSclasSeCd.equals("null") || sSclasSeCd.equals("")) {
			sSclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_SCLAS_SE_CD", sSclasSeCd);
		}
		
		// 자원제공서비스상세분류구분코드
		sDtlSeCd = paramGroup.getValue("SRVC_RESRCE_DTL_SE_CD");
		if (sDtlSeCd==null || sDtlSeCd.equals("null") || sDtlSeCd.equals("")) {
			sDtlSeCd = "";
			//paramMap.put("SRVC_RESRCE_DTL_SE_CD",    sDtlSeCd);
		}
		
		if ("Y".equals(paramMap.get("RSFR_INST_YN"))) {
			paramMap.put("RSFR_INST_NO", String.valueOf(loginVO.getInstNo()));
		} 
		if ("Y".equals(paramMap.get("PIC_INST_YN"))) {
			paramMap.put("PIC_INST_NO", String.valueOf(loginVO.getInstNo()));
		} 
		return srvcResrcePopMapper.selectSrvcResrceList(paramMap);
	}
	
	@Override
	public Map<String, Object> selectSrvcResrcePagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> result = new HashMap<>();
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");
				
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		// oUntTaskwk = loginVO.getUntTaskwk();
		LOGGER.debug("selectSrvcResrceList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		//Map<String, String> paramMap = new HashMap<>();
		
		//String sSrvcYr		 = "";
		
		// 서비스연도	
//		sSrvcYr = paramGroup.getValue("SRVC_YR");  
//		if (sSrvcYr==null || sSrvcYr.equals("null") || sSrvcYr.equals("")) {
//			throw new AppWorksException("서비스연도는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
//		}
		
		Map<String, Object> paramMap2 = new HashMap<>();
		
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		if ("Y".equals(paramMap.get("RSFR_INST_YN"))) {
			paramMap2.put("RSFR_INST_NO", String.valueOf(loginVO.getInstNo()));
		} 
		if ("Y".equals(paramMap.get("PIC_INST_YN"))) {
			paramMap2.put("PIC_INST_NO", String.valueOf(loginVO.getInstNo()));
		}
		
		String TOT_CNT = srvcResrcePopMapper.selectSrvcResrceCount(paramMap2);
		
		int totCnt  = (TOT_CNT == null|| TOT_CNT.trim().isEmpty())?0:Integer.valueOf(TOT_CNT);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> list = srvcResrcePopMapper.selectSrvcResrcePagingList(paramMap2);
		
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		result.put("list", list);
		result.put("dmPageInfo", resPage);
		
		return result;
	}
	
}