/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CaseRegService.java
 * @프로그램 설명 	: 사례관리 대상자에 대한 내역을 관리한다
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 4. 29.
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 4. 29.
 * @수정내용      :
 * -
 * -
 */
public interface CaseRegService {

	//사례관리 건수 조회
	public List<Map<String, Object>> selectCaseMngNocs(DataRequest dataRequest) throws Exception;
	//단위업무상세페이지경로 조회
	public List<Map<String, Object>> selectUrlValue(DataRequest dataRequest) throws Exception;
	//사례목록조회
	public List<Map<String, Object>> selectMainList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public Map<String, Object> selectCaseinqPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	//사례등록 기본정보 조회
	public List<Map<String, Object>> selectCaseBassDetail(DataRequest dataRequest) throws Exception;
	//사례등록 문제상태및원인 조회
	public List<Map<String, Object>> selectCaseYngbgsList(DataRequest dataRequest) throws Exception;
	//사례등록 가족정보 조회
	public List<Map<String, Object>> selectFamInfoList(DataRequest dataRequest) throws Exception;
	//사례등록 학력상태 조회
	public List<Map<String, Object>> selectAcbgSttsList(DataRequest dataRequest) throws Exception;
	//사례등록 학업중단 조회
	public List<Map<String, Object>> selectSchulwDscntcList(DataRequest dataRequest) throws Exception;
	//사례등록 취업정보 조회
	public List<Map<String, Object>> selectEmpymnInfoList(DataRequest dataRequest) throws Exception;
	//사례등록 담당자 조회
	public List<Map<String, Object>> selectCasePicList(DataRequest dataRequest) throws Exception;
	//사례등록 서비스실행사업 조회
	public List<Map<String, Object>> selectBizRegList(DataRequest dataRequest) throws Exception;
	//사례등록 저장
	public Map<String, Object> processData(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	//원스크린 정보 조회
	public Map<String, Object> selectOneScreenInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	//청소년상태대분류 정합성 체크를 위한 대상자 가족특성/졸업상태 조회
	public List<Map<String, Object>> selectTrprFamBeischList(DataRequest dataRequest) throws Exception;

	//가족구성정보 조회
	public Map<String, Object> selectFamCnsttnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 사례 상태에 따라 저장 금지
	public void selectPrgrsStts(Map<String, String> map) throws Exception;

}
