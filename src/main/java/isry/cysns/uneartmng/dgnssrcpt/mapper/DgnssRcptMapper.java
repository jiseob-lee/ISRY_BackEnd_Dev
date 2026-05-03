/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.uneartmng.dgnssrcpt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DgnssRcptMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("dgnssRcptMapper")
public interface DgnssRcptMapper {

	//목록
	public String selectLinkRcptCount(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectLinkRcptPagingList(Map<String, Object> map) throws Exception;
	
	//학교진단
	public List<Map<String, String>> selectSchlDgnssById(String param) throws Exception;
	public void updateSchlDgnssData(Map<String, String> paramMap) throws Exception;

	//유관기관
	public List<Map<String, String>> selectRelaInstById(String param) throws Exception;
	public void updateRelaInstData(Map<String, String> paramMap) throws Exception; 

	//진단조사 점수
	public List<Map<String, String>> selectDgnssScoreList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectInfantChilList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectCyberGambleList(Map<String, String> paramMap) throws Exception;

	public List<Map<String, String>> selectDgnssScoreTrmnList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectInfantChilTrmnList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectCyberGambleTrmnList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectAddInspList(Map<String, String> paramMap) throws Exception;

	public List<Map<String, String>> selectDgnssScoreAftfctList(Map<String, String> paramMap) throws Exception;

	//대상자, 서비스접수
	public void updateTrprCaseMngInfo(Map<String, String> paramMap) throws Exception;
	public void updateLinkTrprRcptSeCd(Map<String, String> paramMap) throws Exception;
	
}
