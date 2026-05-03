/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.lcgovbiz.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LcgovBizSprtPreconMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2022. 7. 20. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2022. 7. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("lcgovBizSprtPreconMapper")
public interface LcgovBizSprtPreconMapper {

	public List<Map<String, Object>> selectLcgovBizSprtPreconList(Map<String, String> map) throws Exception;
	
	// 내담자 기본정보 및 경기도 사업 등록 유무 조회
	public Map<String, Object> selectClienaInfo(Map<String, String> map) throws Exception;
	
	// 개별 서비스 목록 조회
	public List<Map<String, String>> selectIndivSprvtList(Map<String, String> map) throws Exception;
	
	// 집단 서비스 목록 조회
	public List<Map<String, String>> selectGrDscsnList(Map<String, String> map) throws Exception;
}
