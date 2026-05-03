/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.export.ExporterFactory.EXPORTTYPE;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.base.IsryBaseServiceImpl;

import isry.itgcm.bizcmmns.cmmns.mapper.SrvcResrceClMapper;
import isry.itgcm.bizcmmns.cmmns.service.SrvcResrceClService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;


/**
* @Class Name  : SrvcResrceClServiceImpl.java
* @Description : 자원제공서비스분류(팝업) ServiceImpl Class
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
@Service("srvcResrceClService")
public class SrvcResrceClServiceImpl extends EgovAbstractServiceImpl implements SrvcResrceClService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="srvcResrceClMapper")
    private SrvcResrceClMapper srvcResrceClMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	/**
	 * @Method     : selectSrvcResrceClList
	 * @Method설명 : 자원제공서비스분류 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 11. 
 	 */	
	@Override
	public List<Map<String, Object>> selectSrvcResrceClList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("selectSrvcResrceClList.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUntTaskwkSeCd = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUntTaskwkSeCd = loginVO.getUntTaskwk();
		}		
		
		
		String sLclasSeCd = "";
		String sMclasSeCd = "";
		String sSclasSeCd = "";
		String sDtlSeCd   = "";
		
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
			//paramMap.put("SRVC_RESRCE_DTL_SE_CD",   sDtlSeCd);
		}
		// 단위업무구분코드
		sUntTaskwkSeCd = paramGroup.getValue("UNT_TASKWK_SE_CD");
		if (sUntTaskwkSeCd == null || "null".equals(sUntTaskwkSeCd) || "".equals(sUntTaskwkSeCd)) {
			paramMap.put("UNT_TASKWK_SE_CD",   sUntTaskwkSeCd);
		}
		
		return srvcResrceClMapper.selectSrvcResrceClList(paramMap);
	}
	
}