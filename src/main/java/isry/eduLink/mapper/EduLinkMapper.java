/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.eduLink.mapper;

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
@Mapper("eduLinkMapper")
public interface EduLinkMapper {
	
	//이벤트 저장
	public List<Map<String, String>> selectCad100() throws Exception;

	public List<String> moeSchulmCodeList() throws Exception;
	
	public String selectDupChk(Map<String, Object> map) throws Exception;
	
	// 학생나이구하기
	public String selectStdntAge(String string);
	
	public List<Map<String, String>> selectSac100(String cmmnsCdId) throws Exception;
	
	public List<Map<String, Object>> selectInstEnfsnNo(Map<String, String> map) throws Exception;
	
	public int insertSrvcPvsnRqst(Map<String, String> map) throws Exception;
	
	public int insertSrvcPvsnRqstHstr(Map<String, String> map) throws Exception;
	
	public int insertSrvcPvsnRcpt(Map<String, String> map) throws Exception;
	
	public int insertSrvcPvsnRcptHstr(Map<String, String> map) throws Exception;
	
	public int insertTrprInfo(Map<String, String> map) throws Exception;
	
	public int insertTrprInfoHstr(Map<String, String> map) throws Exception;
	
	public int insertSchulwDscntc(Map<String, String> map) throws Exception;
	
	public int insertSchulwDscntcHstr(Map<String, String> map) throws Exception;
	
	public int insertAcbgStts(Map<String, String> map) throws Exception;
	
	public int insertAcbgSttsHstr(Map<String, String> map) throws Exception;
	
	public void updateEsbStatus(String esbSeq) throws Exception;
	
	public void insertOfcdcGrFileUldDtl(Map<String, String> map) throws Exception;
}
