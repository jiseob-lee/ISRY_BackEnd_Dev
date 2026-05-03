/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CnterPreconService.java
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
public interface CnterPreconService {

	// 시도센터_센터현황 조회
	public List<Map<String, Object>> selectCtpvCnterPreconList(HttpServletRequest request,DataRequest dataRequest) throws Exception;
	
	// 시도센터_운영정보 조회
	public List<Map<String, Object>> selectCtpvOperInfoList(HttpServletRequest request,DataRequest dataRequest) throws Exception;
	
	// 시도센터_시설정보 조회
	public List<Map<String, Object>> selectCtpvFcltyInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시도센터_설치 및 위탁정보 조회
	public List<Map<String, Object>> selectCtpvInstlCnsgnInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시도센터_추가 기본정보 조회
	public List<Map<String, Object>> selectCtpvAddingBassInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시도센터_청소년상담전화1388 조회
	public List<Map<String, Object>> selectCtpvYngbsDscsnTlphon1388List(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시군구센터_센터현황 조회
	public List<Map<String, Object>> selectSggCnterPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시군구센터_운영정보 조회
	public List<Map<String, Object>> selectSggOperInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시군구센터_시설정보 조회
	public List<Map<String, Object>> selectSggFcltyInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시군구센터_설치 및 위탁정보 조회
	public List<Map<String, Object>> selectSggInstlCnsgnInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시군구센터_추가 기본정보 조회
	public List<Map<String, Object>> selectSggAddingBassInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 시군구센터_청소년상담전화1388 조회
	public List<Map<String, Object>> selectSggYngbsDscsnTlphon1388List(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
