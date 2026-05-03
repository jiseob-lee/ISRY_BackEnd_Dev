/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.dashboard.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MainDashboardMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 11. 08. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 11. 08.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mainDashboardMapper")
public interface MainDashboardMapper {

	public List<Map<String, Object>> selectMainDashboard(Map<String, String> paramMap) throws Exception;
	
	public Map<String, Object> selectMenuId(String untTaskwkSeCd) throws Exception;
	
	public Map<String, Object> selectJbps(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectCaseCnt(Map<String, Object> paramMap) throws Exception;
	
	public Map<String, Object> selectDca010(Map<String, String> paramMap) throws Exception;
	
	public void saveDca010(Map<String, Object> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectLinkCnt(Map<String, Object> paramMap) throws Exception;
	/*2023.09.04 운영팀 정현진 maindashboard 복지부 연계 데이터*/
	public List<Map<String, Object>> selectLinkMohwCnt(Map<String, Object> paramMap) throws Exception;
	
}
