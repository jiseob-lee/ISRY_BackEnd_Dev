/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.drmgs.cnter.mapper.CnterPreconMapper;
import isry.drmgs.cnter.service.CnterPreconService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;

/**
 * @파일명        : CnterPreconServiceImpl.java
 * @프로그램 설명 : 센터별 현황
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 29.
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("cnterPreconService")
public class CnterPreconServiceImpl extends IsryBaseServiceImpl implements CnterPreconService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="cnterPreconMapper")
	private CnterPreconMapper cnterPreconMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명   : selectCtpvCnterPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 : 시도센터_센터현황 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCtpvCnterPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		
		rtn = cnterPreconMapper.selectCtpvCnterPreconList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCtpvOperInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시도센터_운영정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCtpvOperInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectCtpvCnterOperInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCtpvFcltyInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시도센터_시설정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCtpvFcltyInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectCtpvCnterFcltyInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCtpvInstlCnsgnInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시도센터_설치 및 위탁정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCtpvInstlCnsgnInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectCtpvCnterInstlCnsgnInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCtpvAddingBassInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시도센터_추가 기본정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCtpvAddingBassInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectCtpvCnterAddingBassInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectCtpvYngbsDscsnTlphon1388List
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시도센터_청소년상담전화1388 조회
	 */	
	@Override
	public List<Map<String, Object>> selectCtpvYngbsDscsnTlphon1388List(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectCtpvCnterYngbsDscsnTlphon1388List(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSggCnterPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시군구센터_센터현황 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSggCnterPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectSggCnterPreconList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSggOperInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시군구센터_운영정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSggOperInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectSggOperInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSggFcltyInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시군구센터_시설정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSggFcltyInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectSggFcltyInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSggInstlCnsgnInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시군구센터_설치 및 위탁정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSggInstlCnsgnInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectSggInstlCnsgnInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSggAddingBassInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시군구센터_추가 기본정보 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSggAddingBassInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectSggAddingBassInfoList(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSggYngbsDscsnTlphon1388List
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 시군구센터_청소년상담전화1388 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSggYngbsDscsnTlphon1388List(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		rtn = cnterPreconMapper.selectSggYngbsDscsnTlphon1388List(paramMap2);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			// 전화번호 포멧팅
			String sTrprTelno = String.valueOf(map.get("PIC_TELNO"));
			if (sTrprTelno.length() >= 9 && ! "".equals(sTrprTelno)) {
				map.put("PIC_TELNO", Formatter.phoneFormat(sTrprTelno, 1));
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}


}

