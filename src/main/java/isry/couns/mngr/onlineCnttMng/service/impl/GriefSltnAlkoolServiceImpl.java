/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.onlineCnttMng.service.impl;

import java.util.ArrayList;
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
import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.onlineCnttMng.mapper.GriefSltnAlkoolMapper;
import isry.couns.mngr.onlineCnttMng.service.GriefSltnAlkoolService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;


@Service
public class GriefSltnAlkoolServiceImpl extends IsryBaseServiceImpl implements GriefSltnAlkoolService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "griefSltnAlkoolMapper")
	private GriefSltnAlkoolMapper griefSltnAlkoolMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	ScpDb  scpDb  = new ScpDb();
	Masking mask  = new Masking();
	
	/**
	 * @Method명   : selectGriefSltnAlkoolThemaList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 21. 
	 * @Method설명 : 고민해결백과 주제 조회
	 */	
	@Override
	public List<Map<String, Object>> selectGriefSltnAlkoolThemaList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("FLNM_ENCPT", paramMap.get("FLNM_ENCPT"));
		rtn = griefSltnAlkoolMapper.selectGriefSltnAlkoolThemaList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : griefSltnAlkoolThemaInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 21. 
	 * @Method설명 : 고민해결백과 주제 등록
	 */
	@Override
	public Map<String, String> griefSltnAlkoolThemaInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
//		Map<String, Object> subMap01 = new HashMap<>();
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmGriefSltnAlkoolThemaReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
				
		dmOutcomeDetailMap.put("TTL_NM", dmOutcomeDetailMap.get("TTL_NM").toString()); // 제목명
		dmOutcomeDetailMap.put("SBTLE_NM", dmOutcomeDetailMap.get("SBTLE_NM").toString()); // 소제목명
		dmOutcomeDetailMap.put("ATFINO", dmOutcomeDetailMap.get("ATFINO").toString()); // 첨부파일
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		griefSltnAlkoolMapper.InsertGriefSltnAlkoolThema(dmOutcomeDetailMap); // 고민해결백과주제(AYE400)
		
		return null;
	}
	
	/**
	 * @Method명   : griefSltnAlkoolInsert
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 등록
	 */
	@Override
	public Map<String, String> griefSltnAlkoolInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmGriefSltnAlkoolReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
				
		dmOutcomeDetailMap.put("GRIEF_SLTN_ALKIOL_THMA_SN", dmOutcomeDetailMap.get("GRIEF_SLTN_ALKIOL_THMA_SN").toString()); // 고민주제
		dmOutcomeDetailMap.put("SUPRV_NM", dmOutcomeDetailMap.get("SUPRV_NM").toString()); // 감수자
		dmOutcomeDetailMap.put("TTL_NM", dmOutcomeDetailMap.get("TTL_NM").toString()); // 제목
		dmOutcomeDetailMap.put("SQNCE_NO", dmOutcomeDetailMap.get("SQNCE_NO").toString()); // 편
		dmOutcomeDetailMap.put("OPEN_RSVT_DT", dmOutcomeDetailMap.get("OPEN_RSVT_DT").toString()); // 오픈예약시간
		dmOutcomeDetailMap.put("ATFINO", dmOutcomeDetailMap.get("ATFINO").toString()); // 첨부파일
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		griefSltnAlkoolMapper.InsertGriefSltnAlkool(dmOutcomeDetailMap); // 고민해결백과(AYE410)
		
		return null;
	}
	
	/**
	 * @Method명	 : selectGriefSltnAlkoolThemaUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 주제 수정 조회
	 */
	@Override
	public Map<String,Object> selectGriefSltnAlkoolThemaUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return griefSltnAlkoolMapper.selectGriefSltnAlkoolThemaUpdate(paramMap);
	}
	
	/**
	 * @Method명	 : selectGriefSltnAlkoolUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 수정 조회
	 */
	@Override
	public Map<String,Object> selectGriefSltnAlkoolUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		Map<String, Object> returnMap = griefSltnAlkoolMapper.selectGriefSltnAlkoolUpdate(paramMap);
		
		return returnMap;
	}
	
	/**
	 * @Method명   : griefSltnAlkoolThemaDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 주제 삭제
	 */
	@Override
	public Map<String, String> griefSltnAlkoolThemaDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmGriefSltnAlkoolThemaReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		griefSltnAlkoolMapper.DeleteGriefSltnAlkoolThema(dmOutcomeDetailMap); 
				
		return null;
	}
	
	/**
	 * @Method명   : griefSltnAlkoolDelete
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 삭제
	 */
	@Override
	public Map<String, String> griefSltnAlkoolDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmGriefSltnAlkoolReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		LOGGER.debug("griefSltnAlkoolDelete 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		griefSltnAlkoolMapper.DeleteGriefSltnAlkool(dmOutcomeDetailMap); 
				
		return null;
	}
	
	/**
	 * @Method명   : griefSltnAlkoolThemaUpdate
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 주제 수정
	 */
	@Override
	public Map<String, String> griefSltnAlkoolThemaUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmGriefSltnAlkoolThemaReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		griefSltnAlkoolMapper.UpdateGriefSltnAlkoolThema(dmOutcomeDetailMap); 
				
		return null;
	}
	
	/**
	 * @Method명   : griefSltnAlkoolUpdate
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 수정
	 */
	@Override
	public Map<String, String> griefSltnAlkoolUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmGriefSltnAlkoolReg");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		griefSltnAlkoolMapper.UpdateGriefSltnAlkool(dmOutcomeDetailMap); 
				
		return null;
	}
	
	/**
	 * @Method명   : selectGriefSltnAlkool
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 onload 조회
	 */	
	@Override
	public List<Map<String, Object>> selectGriefSltnAlkool(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		sUserId = loginVO.getId();

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("USER_ID", sUserId); // 유저아이디
		
		rtn = griefSltnAlkoolMapper.selectGriefSltnAlkool(paramMap);
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectGriefSltnAlkoolList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 : 고민해결백과 조회
	 */	
	@Override
	public List<Map<String, Object>> selectGriefSltnAlkoolList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("FLNM_ENCPT", paramMap.get("FLNM_ENCPT"));
		rtn = griefSltnAlkoolMapper.selectGriefSltnAlkoolList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	
}
