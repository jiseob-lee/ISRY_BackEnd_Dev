/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.base.IsryBaseServiceImpl;

import isry.itgcm.bizcmmns.cmmns.mapper.ComCodeMapper;
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcms.sysmgmt.cmmncode.mapper.MgmtCmmnCodeMapper;


/**
* @Class Name  : ComCodeServiceImpl.java
* @Description : 공통코드조회 ServiceImpl Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 12.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 12.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Service("comCodeService")
public class ComCodeServiceImpl extends EgovAbstractServiceImpl implements ComCodeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="comCodeMapper")
    private ComCodeMapper comCodeMapper;
	
	@Resource(name="mgmtCmmnCodeMapper")
    private MgmtCmmnCodeMapper mgmtCmmnCodeMapper;
	
	/**
	 * @Method     : selectTrprIdntfcList
	 * @Method설명 : 대상자식별 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 12. 
 	 */	
	@Override
	public List<Map<String, Object>> selectComCodeList(Map<String, String> paramMap) throws Exception {
		
		if (paramMap == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("selectComCodeList.paramMap=[" + paramMap + "]");
		
		String sCmmnsCdId = "";
		
		// 공통코드아이디
		sCmmnsCdId = paramMap.get("CMMNS_CD_ID");  
		if (sCmmnsCdId==null || sCmmnsCdId.equals("null") || sCmmnsCdId.equals("")) {
			sCmmnsCdId = "X";
		}
		
		return comCodeMapper.selectComCodeList(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> selectCommonCodeUnit(Map<String, String> paramMap) throws Exception {
		
		String codeId = paramMap.get("CMMNS_CD_ID");  
		if (codeId==null || codeId.equals("null") || codeId.equals("")) {
			codeId = "X";
		}
		String unitCode = paramMap.get("unitCode");
		
		Map<String, Object> map  = new HashMap<>();
		map.put("codeId", codeId);
		map.put("unitCode", unitCode);
		
		if("".equals(unitCode) || unitCode == null) {
			return mgmtCmmnCodeMapper.selectCommonCode(codeId);
		}else {
			return mgmtCmmnCodeMapper.selectCommonCodeUnit(map);	
		}
		
		
	}
	
	/**
	 * @Method     : selectTrprIdntfcList
	 * @Method설명 : 공통코드 목록조회
	 * @param      : paramCode
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 12. 
 	 */	
	@Override
	public List<Map<String, Object>> selectComCodeList(String paramCode) throws Exception {
		
		if (paramCode == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		
		String sCmmnsCdId = paramCode;
		
		// 공통코드아이디
		if (sCmmnsCdId==null || sCmmnsCdId.equals("null") || sCmmnsCdId.equals("")) {
			sCmmnsCdId = "X";
		}
		LOGGER.debug("selectComCodeList.CMMNS_CD_ID=[" + sCmmnsCdId + "]");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("CMMNS_CD_ID",  sCmmnsCdId);
		
		return comCodeMapper.selectComCodeList(paramMap);
	}
	

	
}