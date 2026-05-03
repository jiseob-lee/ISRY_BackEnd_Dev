/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casetrmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CysnsTrmnMapper.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 25.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      :
 */
@Mapper("cysnsTrmnMapper")
public interface CysnsTrmnMapper {

	public void saveData(Map<String, String> map) throws Exception;

	public void updateSchlDgnssData(Map<String, String> map) throws Exception;
	public void updateInstDgnssData(Map<String, String> map) throws Exception;

	public void saveDgnssScoreData(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectProbmSttsById(Map<String, String> map) throws Exception; //문제상태

	public void saveSrvyAddtngInfo(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectCnctrClinicYsrInfo(Map<String, String> map) throws Exception; //문제상태
}
