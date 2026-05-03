/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.cysns.casemng.casereg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CysnsRegService.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 7.
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 7.
 * @수정내용      :
 * -
 * -
 */
public interface CysnsRegService {

	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectReqById2(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectReqById3(DataRequest dataRequest) throws Exception;

	public Map<String, String> saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public void deleteData(DataRequest dataRequest) throws Exception;

	public String selectSrvyTrprById(DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectExcnReqById(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectExcnReqById2(DataRequest dataRequest) throws Exception;

	public void saveExcnData(HttpServletRequest request, DataRequest dataRequest, List<Map<String, String>> params) throws Exception;

	public List<Map<String, String>> selectDgnssByTrprInfoNo(DataRequest dataRequest) throws Exception;
	public String selectGradeSttsByTrprInfoNo(DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectTrlInspByResrceNo(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectTrlInspByList(DataRequest dataRequest) throws Exception;

	/* 사전 설문 목록 실시 여부 추가 필요에 따른 추가.  */
	public List<Map<String, Object>> selectReqBySrvyInfo(DataRequest dataRequest) throws Exception;
	/* 종결 설문 목록 실시 여부 추가 필요에 따른 추가.  */
	public List<Map<String, Object>> selectSrvyAddtngInfo(DataRequest dataRequest) throws Exception;

	//치료지원
	public void saveMdlrtSprtData(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 집중 클리닉 정보 가져오기.
	public Map<String, Object> selectCnctrClinicInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
