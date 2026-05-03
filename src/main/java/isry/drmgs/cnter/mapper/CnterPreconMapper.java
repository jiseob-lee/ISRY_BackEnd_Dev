/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CnterPreconMapper.java
 * @프로그램 설명 : 센터별 현황
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 29. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : Lee.Tae.Ho
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("cnterPreconMapper")
public interface CnterPreconMapper {

	// 시도센터_센터현황 조회
	public List<Map<String, Object>> selectCtpvCnterPreconList(Map<String, Object> paramMap) throws Exception;
	// 시도센터_운영정보 조회
	public List<Map<String, Object>> selectCtpvCnterOperInfoList(Map<String, Object> paramMap) throws Exception;
	// 시도센터_시설정보 조회
	public List<Map<String, Object>> selectCtpvCnterFcltyInfoList(Map<String, Object> paramMap) throws Exception;
	// 시도센터_설치 및 위탁정보 조회
	public List<Map<String, Object>> selectCtpvCnterInstlCnsgnInfoList(Map<String, Object> paramMap) throws Exception;
	// 시도센터_추가 기본정보 조회
	public List<Map<String, Object>> selectCtpvCnterAddingBassInfoList(Map<String, Object> paramMap) throws Exception;
	// 시도센터_청소년상담전화1388 조회
	public List<Map<String, Object>> selectCtpvCnterYngbsDscsnTlphon1388List(Map<String, Object> paramMap) throws Exception;
	
	// 시군구센터_센터현황 조회
	public List<Map<String, Object>> selectSggCnterPreconList(Map<String, Object> paramMap) throws Exception;
	// 시군구센터_운영정보 조회
	public List<Map<String, Object>> selectSggOperInfoList(Map<String, Object> paramMap) throws Exception;
	// 시군구센터_시설정보 조회
	public List<Map<String, Object>> selectSggFcltyInfoList(Map<String, Object> paramMap) throws Exception;
	// 시군구센터_설치 및 위탁정보 조회
	public List<Map<String, Object>> selectSggInstlCnsgnInfoList(Map<String, Object> paramMap) throws Exception;
	// 시군구센터_추가 기본정보 조회
	public List<Map<String, Object>> selectSggAddingBassInfoList(Map<String, Object> paramMap) throws Exception;
	// 시군구센터_청소년상담전화1388 조회
	public List<Map<String, Object>> selectSggYngbsDscsnTlphon1388List(Map<String, Object> paramMap) throws Exception;
	
}
