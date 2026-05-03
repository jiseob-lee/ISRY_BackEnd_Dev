/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.gitple.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : GitpleEventMapper.java
 * @프로그램 설명 	: 깃플챗 이벤트를 저장한다.
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("gitpleEventMapper")
public interface GitpleEventMapper {
	
	//이벤트 저장
	public void insertAyb201Data(Map<String, String> map) throws Exception;
	
	public void insertAyb200Data(Map<String, String> map) throws Exception;
	
	public void insertAyb230Data(Map<String, String> map) throws Exception;
	
	public void insertAyb240Data(Map<String, String> map) throws Exception;
	
	public void insertAyb250Data(Map<String, String> map) throws Exception;
	
	public void insertAyb260Data(Map<String, String> map) throws Exception;
	
	public int selectAyb202Data(Map<String, String> map) throws Exception;
	
	public void insertAyb202Data(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> selectChttType() throws Exception;
	
	public Map<String, String> gitpleManager(String id) throws Exception;
	
	public void updateUniverEsntalIdfrNo(Map<String, String> map) throws Exception;
	
	public String getGitpleIdToken(String token) throws Exception;
	
	public String getGitpleIdUserId(String id) throws Exception;
	
	public Map<String, String> getMogefId(String id) throws Exception;
	
	public String getLastBatch() throws Exception;
	
	public void updateBatchTime(Map<String, String> map) throws Exception;
	
	public void insertBatchTime(Map<String, String> map) throws Exception;
	
	public Map<String, String> getCommute(String id) throws Exception;
	
	public void insertAyc495Data(Map<String, String> map) throws Exception;
	
	public Map<String, String> selectGitpleCnt(String id) throws Exception;
}
