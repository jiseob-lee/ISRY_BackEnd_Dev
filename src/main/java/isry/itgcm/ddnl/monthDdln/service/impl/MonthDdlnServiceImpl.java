/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.ddnl.monthDdln.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.ddnl.monthDdln.mapper.MonthDdlnMapper;
import isry.itgcm.ddnl.monthDdln.service.MonthDdlnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : MonthDdlnServiceImpl.java
 * @프로그램 설명 : 월마감 관리 - -
 * @작성자 : Yoo.Chi.Hoon
 * @작성일 : 2022. 10. 25.
 * @수정자 : Yoo.Chi.Hoon
 * @수정일 : 2022. 10. 25.
 * @수정내용 : - -
 */
@Service("monthDdlnService")
public class MonthDdlnServiceImpl extends EgovAbstractServiceImpl implements MonthDdlnService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="monthDdlnMapper")
    private MonthDdlnMapper monthDdlnMapper;	

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectUntTaskwkInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 단위업무구분 시도수행기관, 시군구수행기관 조회
	 */
	@Override
	public List<Map<String, Object>> selectUntTaskwkInstList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("단위업구분이 입력되지 않았습니다.");
		}		
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap(); 
		
		List<Map<String, Object>> retList = new ArrayList<>();
		retList = monthDdlnMapper.selectUntTaskwkInstList(paramMap);		/* 시도수행기관, 시군구수행기관 조회 Mapper*/
		
		return retList;
	}
	
	/**
	 * @Method명   : selectMonthDdlnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 월마감 목록조회
	 */
	@Override
	public List<Map<String, Object>> selectMonthDdlnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("마감연도가 입력되지 않았습니다.\n마감연도를 입력 바랍니다.");
		}
		LOGGER.debug("selectMonthDdlnList.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap(); 
		
		return monthDdlnMapper.selectMonthDdlnList(paramMap);	//월마감 관리 목록 Mapper
	}

	/**
	 * @Method명   : selectBfeMonthDdlnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 전월마감 조회
	 */
	@Override
	public List<Map<String, Object>> selectBfeMonthDdlnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("단위업구분이 입력되지 않았습니다.");
		}
		LOGGER.debug("selectBfeMonthDdlnList.paramGroup=[" + paramGroup + "]");
		Map<String, String> paramMap = paramGroup.getSingleValueMap(); 
		
		LOGGER.debug("selectBfeMonthDdlnList.paramGroup=[" + paramMap + "]");
		List<Map<String, Object>> retList = new ArrayList<>();
		retList = monthDdlnMapper.selectBfeMonthDdlnList(paramMap);	//월마감 관리 목록 Mapper
		
		if(retList.size() < 1) {
			
			retList = monthDdlnMapper.selectUntTaskwkInstList(paramMap);		/* 시도수행기관, 시군구수행기관 조회 Mapper*/			
			
		}
		
		return retList;
	}
	
	/**
	 * @Method명   : selectMonthDdlnPrd
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 : 
	 */
	@Override
	public Map<String, Object> selectMonthDdlnPrd(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		return monthDdlnMapper.selectMonthDdlnPrd();
	}
	
	/**
	 * @Method명   : processMonthDddln
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 27. 
	 * @Method설명 : 월마감 처리
	 */
	@Override
	public Map<String, Object> processMonthDddln(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup processMonthDddln = dataRequest.getParameterGroup("dsSEC330");
		if (processMonthDddln == null) {
			throw new AppWorksException("월마감 저장할 데이터가 없습니다.");
		}
		LOGGER.debug("processMonthDddln.processMonthDddln=[" + processMonthDddln + "]");
		
		Iterator<ParameterRow> insertedRows = processMonthDddln.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = processMonthDddln.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = processMonthDddln.getDeletedRows();		
		
		String sUserId = "";
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		List<String> dupInstNmArr = new ArrayList<>();
		Map<String, Object> retMap = new HashMap<>();	
		while(insertedRows.hasNext()) {
			
			Map<String, String> manIns = insertedRows.next().toMap();
			
			/* 중복체크(중복데이터는 저장 하지 않음 & 알럿*/
			
			int iCnt = 0;
			Map<String, Object> chkMap = new HashMap<>();
			chkMap.put("INST_NO"         , manIns.get("INST_NO"));
//			chkMap.put("INST_NM"         , manIns.get("INST_NM"));
			chkMap.put("UNT_TASKWK_SE_CD", manIns.get("UNT_TASKWK_SE_CD"));
			chkMap.put("DDLN_YM"         , manIns.get("DDLN_YM"));
			
			iCnt = monthDdlnMapper.selectMonthDdlnCnt(chkMap);
			
			manIns.put("FRST_RGTR_ID", sUserId);
			manIns.put("LAST_MDFR_ID", sUserId);
			
			if(iCnt > 0) {
				/* 중복데이터 화면에 알럿*/
				chkMap.put("INST_NM"         , monthDdlnMapper.getSAA000InstNm(chkMap));
				
				String sDuplnstNm          = String.valueOf(chkMap.get("INST_NM"));
				String sDuplDdlnYm 		   = String.valueOf(chkMap.get("DDLN_YM"));				
				
				String duplMsg = (sDuplnstNm + "/" + sDuplDdlnYm.substring(4, 6) + "월");
				dupInstNmArr.add(duplMsg);
				
			}else {
				/* 중복되지 않은 데이터는 등록*/
				monthDdlnMapper.insertMonthDdln(manIns);
			}
		}
		while(updatedRows.hasNext()) {
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			mapUpd.put("LAST_MDFR_ID", sUserId);
			
			monthDdlnMapper.updateMonthDdln(mapUpd);			
		}
		while(deletedRows.hasNext()) {
			
//			Map<String, String> mapDel = deletedRows.next().toMap();
		}		
		
		retMap.put("MSG", dupInstNmArr);
		
		return retMap;
	}
	
	/**
	 * @Method명	 : selectCaseMngDdlnCrtrInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 11. 01.
	 * @Method설명 : 사례관리 마감기준정보 조회
	 */
	@Override
	public List<Map<String, Object>> selectCaseMngDdlnCrtrInfo(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		List<Map<String, Object>> rtn = monthDdlnMapper.selectCaseMngDdlnCrtrInfo(paramMap);

		return rtn;
	}




}
