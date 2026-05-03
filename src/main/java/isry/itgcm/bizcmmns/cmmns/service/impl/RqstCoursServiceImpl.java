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

import isry.itgcm.bizcmmns.cmmns.mapper.RqstCoursMapper;
import isry.itgcm.bizcmmns.cmmns.service.RqstCoursService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;


/**
* @Class Name  : RqstCoursServiceImpl.java
* @Description : 의뢰경로조회(팝업) ServiceImpl Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 17.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 17.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Service("rqstCoursService")
public class RqstCoursServiceImpl extends EgovAbstractServiceImpl implements RqstCoursService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="rqstCoursMapper")
    private RqstCoursMapper rqstCoursMapper;
	
	/**
	 * @Method     : selectRqstCoursList
	 * @Method설명 : 의뢰경로 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 17. 
 	 */	
	@Override
	public List<Map<String, Object>> selectRqstCoursList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("selectRqstCoursList.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		//Map<String, String> paramMap = new HashMap<>();
		
		String sLclasSeCd = "";
		String sMclasSeCd = "";
		String sSclasSeCd = "";
		String sDtlSeCd   = "";
		
		// 접수의뢰경로대분류구분코드
		sLclasSeCd = paramGroup.getValue("RCPT_RQST_COURS_LCLAS_SE_CD");  
		if (sLclasSeCd==null || sLclasSeCd.equals("null") || sLclasSeCd.equals("")) {
			sLclasSeCd = "";
			//paramMap.put("RCPT_RQST_COURS_LCLAS_SE_CD", sLclasSeCd);
		}
		
		// 접수의뢰경로중분류구분코드
		sMclasSeCd = paramGroup.getValue("RCPT_RQST_COURS_MLSFC_SE_CD");  
		if (sMclasSeCd==null || sMclasSeCd.equals("null") || sMclasSeCd.equals("")) {
			sMclasSeCd = "";
			//paramMap.put("RCPT_RQST_COURS_MLSFC_SE_CD", sMclasSeCd);
		}
		
		// 접수의뢰경로소분류구분코드
		sSclasSeCd = paramGroup.getValue("RCPT_RQST_COURS_SCLAS_SE_CD");
		if (sSclasSeCd==null || sSclasSeCd.equals("null") || sSclasSeCd.equals("")) {
			sSclasSeCd = "";
			//paramMap.put("RCPT_RQST_COURS_SCLAS_SE_CD", sSclasSeCd);
		}
		
		// 접수의뢰경로구분코드
		sDtlSeCd = paramGroup.getValue("RCPT_RQST_COURS_SE_CD");
		if (sDtlSeCd==null || sDtlSeCd.equals("null") || sDtlSeCd.equals("")) {
			sDtlSeCd = "";
			//paramMap.put("RCPT_RQST_COURS_SE_CD",   sDtlSeCd);
		}
	
		return rqstCoursMapper.selectRqstCoursList(paramMap);
	}
	
}