/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.linkmng.linkmedia.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : TlphonDscsnMapper.java
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
@Mapper("linkMediaMapper")
public interface LinkMediaMapper {

	//key 생성 - 대상자정보번호, 서비스제공의뢰번호, 진단조사관리번호
	public String selectKeyValue(Map<String, String> paramMap) throws Exception;

	//복지센터정보 저장
	public List<Map<String, String>> selectWlfarCnterList() throws Exception;
	public int saveWlfarCnterData(Map<String, String> paramMap) throws Exception;
	public int updateWlfarCnterData(Map<String, String> paramMap) throws Exception;

	//설문답변 저장
	public List<Map<String, String>> selectSchlScoreList() throws Exception;
	public int updateSchlScoreData(Map<String, String> paramMap) throws Exception;

	//기관설문답변 저장
	public List<Map<String, String>> selectInstScoreList() throws Exception;
	public int updateInstScoreData(Map<String, String> paramMap) throws Exception;

	public int saveDgnssScoreData(Map<String, String> paramMap) throws Exception;

	//학교진단 저장
	public List<Map<String, String>> selectSchlDgnssList() throws Exception;
	public int updateSchlDgnssData(Map<String, String> paramMap) throws Exception;
	public int saveSchlDgnssData(Map<String, String> paramMap) throws Exception;

	//기관진단 저장
	public List<Map<String, String>> selectInstDgnssList() throws Exception;
	public int updateInstDgnssData(Map<String, String> paramMap) throws Exception;
	public int saveInstDgnssData(Map<String, String> paramMap) throws Exception;
	
	//서비스제공의뢰
	public void insertSrvcPvsnRqst(Map<String, String> paramMap) throws Exception;
	public void insertSrvcPvsnRqstHistory(Map<String, String> paramMap) throws Exception;
	
	//서비스제공의뢰접수
	public int insertSrvcPvsnRqstRcpt(Map<String, String> paramMap) throws Exception;
	public void insertSrvcPvsnRqstRcptHistory(Map<String, String> paramMap) throws Exception;
	
	//대상자정보
	public void insertTrprInfoData(Map<String, String> paramMap) throws Exception;
	public void insertTrprInfoHistory(Map<String, String> paramMap) throws Exception;
	public int insertAcbgSttsData(Map<String, String> paramMap) throws Exception;
	public void insertAcbgSttsHistory(Map<String, String> paramMap) throws Exception;
	
}
