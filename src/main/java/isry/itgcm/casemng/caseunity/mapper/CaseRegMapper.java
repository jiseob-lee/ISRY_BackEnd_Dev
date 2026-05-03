/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CaseRegMapper.java
 * @프로그램 설명 	: 사례관리 대상자에 대한 내역을 관리한다.
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 4. 29. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 4. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("caseRegMapper")
public interface CaseRegMapper {

	//사례관리 건수 조회
	public List<Map<String, Object>> selectCaseMngNocs(Map<String, String> map) throws Exception;
	//단위업무별 화면URL 조회
	public List<Map<String, Object>> selectUrlValue(Map<String, String> map) throws Exception;
	
	//사례대상자 목록 조회
	public List<Map<String, Object>> selectCaseinqList(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectCaseinqPagingList(Map<String, Object> map) throws Exception;

	
	public String caseinqListCount(Map<String, Object> map) throws Exception;
	//기본정보 조회
	public List<Map<String, Object>> selectCaseBassDetail(Map<String, String> map) throws Exception;
	//문제상태 목록 조회
	public List<Map<String, Object>> selectCaseYngbgsList(Map<String, String> map) throws Exception;
	//가족정보 목록 조회
	public List<Map<String, Object>> selectFamInfoList(Map<String, String> map) throws Exception;
	//학력상태 목록 조회
	public List<Map<String, Object>> selectAcbgSttsList(Map<String, String> map) throws Exception;
	//학업중단 목록 조회
	public List<Map<String, Object>> selectSchulwDscntcList(Map<String, String> map) throws Exception;
	//취업정보 목록 조회
	public List<Map<String, Object>> selectEmpymnInfoList(Map<String, String> map) throws Exception;
	//자격정보 목록 조회
	public List<Map<String, Object>> selectCertiInfoList(Map<String, String> map) throws Exception;
	//사례담당자 목록 조회
	public List<Map<String, Object>> selectCasePicList(Map<String, String> map) throws Exception;
	//서비스사업 목록 조회
	public List<Map<String, Object>> selectBizRegList(Map<String, String> map) throws Exception;

	//사례기본 저장
	public void saveSEB100Data(Map<String, String> map) throws Exception;
	//사례기본 이력 등록
	public void insertSEB101Data(Map<String, String> map) throws Exception;

	/* 2022.08.11 추가 - Lee SeungYeon */
	//사례기본 사례진행상태구분코드 수정
	public void updateCasePrgrsSttsSeCd(Map<String, String> map) throws Exception;
	//사례기본 등록
	public void insertSEB100Data(Map<String, String> map) throws Exception;
	//사례기본 수정
	public void updateSEB100Data(Map<String, String> map) throws Exception;	
	
	//대상자정보 저장
	public void saveSEA200Data(Map<String, String> map) throws Exception;
	//대상자정보 이력 등록
	public void insertSEA201Data(Map<String, String> map) throws Exception;
	
	/* 2022.08.11 추가 - Lee SeungYeon */
	//대상자정보 등록
	public void insertSEA200Data(Map<String, String> map) throws Exception;
	//대상자정보 수정
	public void updateSEA200Data(Map<String, String> map) throws Exception;
	
	//문제상태및원인 저장
	public void saveCaseYngbgs(Map<String, String> map) throws Exception;
	//문제상태및원인이력 저장
	public void insertCaseYngbgsHstr(Map<String, String> map) throws Exception;
	//문제상태및원인이력 삭제
	public void deleteCaseYngbgs(Map<String, String> map) throws Exception;
	
	/* 2022.08.11 추가 - Lee SeungYeon */
	//문제상태및원인 등록
	public void insertCaseYngbgs(Map<String, String> map) throws Exception;
	//문제상태및원인 수정
	public void updateCaseYngbgs(Map<String, String> map) throws Exception;	
	
	//사례대상자가족 저장
	public void saveSEA210Data(Map<String, String> map) throws Exception;
	//사례대상자가족 이력 등록
	public void insertSEA211Data(Map<String, String> map) throws Exception;
	//사례대상자가족 삭제
	public void deleteSEA210Data(Map<String, String> map) throws Exception;	
	
	/* 2022.08.11 추가 - Lee SeungYeon */
	//사례대상자가족 등록
	public void insertSEA210Data(Map<String, String> map) throws Exception;
	//사례대상자가족 수정
	public void updateSEA210Data(Map<String, String> map) throws Exception;
	
	//학력상태 저장
	public void saveSEA230Data(Map<String, String> map) throws Exception;
	//학력상태이력 저장
	public void insertSEA231Data(Map<String, String> map) throws Exception;
	//학력상태 삭제
	public void deleteSEA230Data(Map<String, String> map) throws Exception;
	
	/* 2022.08.11 추가 - Lee SeungYeon */
	//학력상태 등록
	public void insertSEA230Data(Map<String, String> map) throws Exception;
	//학력상태 수정
	public void updateSEA230Data(Map<String, String> map) throws Exception;
	
	//학업중단 저장
	public void saveSEA240Data(Map<String, String> map) throws Exception;
	//학업중단이력 저장
	public void insertSEA241Data(Map<String, String> map) throws Exception;
	//학업중단 삭제
	public void deleteSEA240Data(Map<String, String> map) throws Exception;

	/* 2022.08.11 추가 - Lee SeungYeon */
	//학업중단 등록
	public void insertSEA240Data(Map<String, String> map) throws Exception;
	//학업중단 수정
	public void updateSEA240Data(Map<String, String> map) throws Exception;

	//취업정보 저장
	public void saveSEA250Data(Map<String, String> map) throws Exception;
	//취업정보이력 저장
	public void insertSEA251Data(Map<String, String> map) throws Exception;
	//취업정보 삭제
	public void deleteSEA250Data(Map<String, String> map) throws Exception;

	/* 2022.08.11 추가 - Lee SeungYeon */
	//취업정보 등록
	public void insertSEA250Data(Map<String, String> map) throws Exception;
	//취업정보 수정
	public void updateSEA250Data(Map<String, String> map) throws Exception;

	//자격정보
//	public void insertSCA110Data(Map<String, String> map) throws Exception;
//	public void saveSCA111Data(Map<String, String> map) throws Exception;

	//사례담당자 저장
	public void saveSEB150Data(Map<String, String> map) throws Exception;
	//사례담당자이력 저장
	public void insertSEB151Data(Map<String, String> map) throws Exception;
	//사례담당자 삭제
	public void deleteSEB150Data(Map<String, String> map) throws Exception;

	/* 2022.08.11 추가 - Lee SeungYeon */
	//사례담당자 등록
	public void insertSEB150Data(Map<String, String> map) throws Exception;
	//사례담당자 수정
	public void updateSEB150Data(Map<String, String> map) throws Exception;

	//서비스사업대상자  등록
	public void insertSEB120Data(Map<String, String> map) throws Exception;
	//서비스사업대상자 삭제
	public void deleteSEB120Data(Map<String, String> map) throws Exception;

	//학력상태 관리일련번호 채번
	public String selectSEA230MngSn(String sTrprInfoNo) throws Exception;
	//학업중단 관리일련번호 채번
	public String selectSEA240MngSn(String sTrprInfoNo) throws Exception;
	//취업정보 관리일련번호 채번
	public String selectSEA250MngSn(String sTrprInfoNo) throws Exception;

	//상담이력 목록 조회
	public List<Map<String, Object>> selectDscsnHstrList(String trprInfoNo) throws Exception;
	//사례관리이력 목록 조회
	public List<Map<String, Object>> selectCaseMngHstrList(String trprInfoNo) throws Exception;
	//서비스제공 이력 목록 조회
	public List<Map<String, Object>> selectSrvcPvsnHstrList(String trprInfoNo) throws Exception;
	//사업 이력 목록 조회 - 20230608 이승재 - 사업이력 추가
	public List<Map<String, Object>> selectBizHstrList(String trprInfoNo) throws Exception;

	//대상자 가족특성/졸업상태(01:재학) 조회
	public List<Map<String, Object>> selectTrprFamBeischList(String trprInfoNo) throws Exception;

	//사례관리 최종이력 조회
	public Map<String, Object> selectCaseMngLastHstr(Map<String, String> map) throws Exception;	
	//사례관리이력 등록
	public void insertSEB110Data(Map<String, String> map) throws Exception;

	//메인 대시보드 현황 집계 조회
	public List<Map<String, Object>> selectDashBoardPreconTot(Map<String, String> map) throws Exception;

	//원스크린(SEB900)목록 조회
	public List<Map<String, Object>> selectSEB900List(Map<String, String> map) throws Exception;

	public Map<String, String> selectCasePrgrsStts(Map<String, String> map) throws Exception;
	
	// 주 담당자 카운트
	public int chkPchprsPic(Map<String, String> map) throws Exception;
	
	// 주 담당자 update
	public void updatePchprsPic(Map<String, String> map) throws Exception;
}
