/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.hlngcmp.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : HlngCmpMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Mapper("hlngCmpMapper")
public interface HlngCmpMapper {

	public List<Map<String, Object>> selectMentorList(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectMenteeList(Map<String, String> paramMap) throws Exception;

	public List<Map<String, Object>> selectMmMatchingList(Map<String, String> paramMap) throws Exception;

	public void insertMmMatchingList(Map<String, String> map) throws Exception;

	public void updateMmMatchingList(Map<String, String> map) throws Exception;

	public void deleteMmMatchingList(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectDayObservDiaryList(Map<String, String> map) throws Exception;

	public void insertDayObservDiary(Map<String, String> map) throws Exception;

	public void updateDayObservDiary(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectDayObservDiary(Map<String, String> map) throws Exception;

	public int selectDayObservDiaryCheck(Map<String, String> map) throws Exception;

	public void insertDayObservDiaryDtl(Map<String, String> map) throws Exception;

	public void updateDayObservDiaryDtl(Map<String, String> map) throws Exception;

}
