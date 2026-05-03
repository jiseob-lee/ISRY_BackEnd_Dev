/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casereg.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CysnsRegMapper.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 7.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 7.
 * @수정내용      :
 * -
 * -
 */
@Mapper("cysnsRegMapper")
public interface CysnsRegMapper {

	//key 생성 - 진단조사관리번호
	public String selectKeyValue(Map<String, String> paramMap) throws Exception;

	public List<Map<String, String>> selectReqById(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectReqById2(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectReqById3(Map<String, String> map) throws Exception;
	public Map<String, String> selectReqById4(Map<String, String> map) throws Exception;

	public void saveData(Map<String, String> map) throws Exception;
	public void saveData2(Map<String, String> map) throws Exception;
	public void saveData3(Map<String, String> map) throws Exception;
	public void deleteData(Map<String, String> map) throws Exception;

	public Map<String, String> getTrprInfoNo(Map<String, String> map) throws Exception;
	//진단조사
	public List<Map<String, String>> selectDgnssByTrprInfoNo(Map<String, String> map) throws Exception;
	//학교학년
	public String selectGradeSttsByTrprInfoNo(String param) throws Exception;

	public String selectSrvyTrprById(Map<String, String> map) throws Exception; //설문등록 여부

	public void saveSrvyTrprData(Map<String, String> map) throws Exception; //설문대상자

	public List<Map<String, String>> selectExcnReqById(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectExcnReqById2(Map<String, String> map) throws Exception;

	public void saveExcnData(Map<String, String> map) throws Exception;
	public void saveExcnData2(Map<String, String> map) throws Exception;
	public void deleteExcnData2(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectTrlInspByResrceNo(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectTrlInspByList(Map<String, String> map) throws Exception;
	public void saveTrlInspData(Map<String, String> map) throws Exception;
	public void deleteTrlInspData(Map<String, String> map) throws Exception;
	public void deleteTrlInspDtlData(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectReqBySrvyInfo(Map<String, String> map) throws Exception;

	public void saveRspnsInfoData(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectSrvyAddtngInfo(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectCnctrClinicYsrInfo(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectCnctrClinicSrvyInfo(Map<String, String> map) throws Exception;

	//집중 클리닉 추가
	public void saveCnctrClinicBaseData(Map<String, String> map) throws Exception;

	public void saveCnctrClinicSrvyData(Map<String, String> map) throws Exception;
}
