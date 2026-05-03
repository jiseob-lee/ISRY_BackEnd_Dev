/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.lvingmng.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : LvingMngMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Mapper("lvingMngMapper")
public interface LvingMngMapper {

	/**
	 * @param paramMap
	 * @Method명 : selectPic
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : 생활동 담당자 조회
	 */
	List<Map<String, String>> selectPic(Map<String, Object> paramMap);

	/**
	 * @Method명 : selectDormit
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 생활동 콤보데이터 조회
	 */
	List<Map<String, String>> selectDormit();

	/**
	 * @Method명 : selectSrvcExcnBiz
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 서비스실행사업 콤보조회
	 */
	List<Map<String, String>> selectSrvcExcnBiz(Map<String, Object> paramMap);

	/**
	 * @Method명 : selectBizYr
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 사업년도 콤보조회
	 */
	List<Map<String, String>> selectBizYr(Map<String, Object> paramMap);

	/**
	 * @Method명 : selectTakingEra
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 복용시기 리스트 조회
	 */
	List<Map<String, String>> selectTakingEra();

	/**
	 * @Method명 : selectInst
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 12.
	 * @Method설명 : 기관목록조회
	 */
	List<Map<String, String>> selectInst(Map<String, String> paramMap);

	/**
	 * @Method명 : selectDormitForSearch
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 17.
	 * @Method설명 : 생활동 콤보데이터 조회
	 */
	List<Map<String, String>> selectDormitForSearch();

	/**
	 * @Method명 : selectWorkDiaryList
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 23.
	 * @Method설명 : 근무일지 목록 조회
	 */
	List<Map<String, String>> selectWorkDiaryList(Map<String, String> paramMap);

	/**
	 * @Method명 : selectWorkCnList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 근무내용 조회
	 */
	List<Map<String, String>> selectWorkCnList(Map<String, String> mapParam);

	/**
	 * @Method명 : selectWorkDiary
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 근무일지 조회
	 */
	List<Map<String, String>> selectWorkDiary(Map<String, String> mapParam);

	/**
	 * @Method명 : selectDsYngbgsObservRcordList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 청소년관찰기록 조회
	 */
	List<Map<String, String>> selectYngbgsObservRcordList(Map<String, String> mapParam);

	/**
	 * @Method명 : selectDayChckList
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDayChckList(Map<String, String> paramMap);

	/**
	 * @Method명 : selectDayChck
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDayChck(Map<String, String> paramMap);

	/**
	 * @Method명 : insertDayChckList
	 * @param insertedDayChckList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 :
	 */
	void insertDayChckList(Map<String, String> insertedDayChckList);

	/**
	 * @Method명 : insertChckList
	 * @param insertedChckList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 :
	 */
	void insertChckList(List<Map<String, String>> insertedChckList);

	/**
	 * @Method명 : updateDayChckList
	 * @param updatedDayChckList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 :
	 */
	void updateDayChckList(Map<String, String> updatedDayChckList);

	/**
	 * @Method명 : updateChckList
	 * @param updatedChckList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 :
	 */
	void updateChckList(List<Map<String, String>> updatedChckList);

	/**
	 * @Method명 : insertWorkDiary
	 * @param map
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 근무일지 등록
	 */
	void insertWorkDiary(Map<String, String> map);

	/**
	 * @Method명 : insertWorkCnList
	 * @param insertedWorkCnList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 근무내용 등록
	 */
	void insertWorkCn(List<Map<String, String>> insertedWorkCnList);

	/**
	 * @Method명 : insertYngbgsObservRcordList
	 * @param insertedYngbgsObservRcordList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 청소년 관찰기록 등록
	 */
	void insertYngbgsObservRcord(List<Map<String, String>> insertedYngbgsObservRcordList);

	/**
	 * @Method명 : updateWorkDiary
	 * @param map
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 근무일지 수정
	 */
	void updateWorkDiary(Map<String, String> map);

	/**
	 * @Method명 : updateWorkCnList
	 * @param updatedWorkCnList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 시간별 근무일지 수정
	 */
	void updateWorkCn(List<Map<String, String>> updatedWorkCnList);

	/**
	 * @Method명 : updateYngbgsObservRcordList
	 * @param updatedYngbgsObservRcordList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 청소년 관찰기록 수정
	 */
	void updateYngbgsObservRcord(List<Map<String, String>> updatedYngbgsObservRcordList);

	/**
	 * @Method명 : selectDormitNowStrdcList
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 : 생활동 현재 거주현황
	 */
	List<Map<String, String>> selectDormitNowStrdcList(Map<String, String> paramMap);

	/**
	 * @Method명 : insertDormit
	 * @param insertedDormitList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 :
	 */
	void insertDormit(List<Map<String, String>> insertedDormitList);

	/**
	 * @Method명 : updateDormit
	 * @param updatedDormitList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 :
	 */
	void updateDormit(List<Map<String, String>> updatedDormitList);

	/**
	 * @Method명 : deleteDormit
	 * @param deletedDormitList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 26.
	 * @Method설명 :
	 */
	void deleteDormit(Map<String, String> deletedDormitList);

	/**
	 * @Method명 : selectYngbgsList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : 청소년 관찰기록 조회
	 */
	List<Map<String, String>> selectYngbgsList(Map<String, String> mapParam);

	/**
	 * @Method명 : selectWorkDiaryExistCheck
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : 근무일지 존재 여부 조회
	 */
	List<Map<String, String>> selectWorkDiaryExistCheck(Map<String, String> mapParam);

	/**
	 * @Method명 : insertCompnoChc
	 * @param compnoChc
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 28.
	 * @Method설명 : 복수선택유형 등록
	 */
	void insertCompnoChc(Map<String, String> compnoChc);

	/**
	 * @Method명 : insertCompnoChcDtl
	 * @param compList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 28.
	 * @Method설명 : 복수선택유형상세 등록
	 */
	void insertCompnoChcDtl(List<Map<String, String>> compList);

	/**
	 * @Method명 : deleteCompnoChcDtl
	 * @param map
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 복수선택유형상세 삭제
	 */
	void deleteCompnoChcDtl(Map<String, String> map);

	/**
	 * @Method명 : selectDayChckToInsert
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 1.
	 * @Method설명 : 일일점검표 등록시 시설점검표 조회
	 */
	List<Map<String, String>> selectDayChckToInsert();

	/**
	 * @Method명 : selectEnfsn
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 생활동 담당자 선택할 종사자 조회
	 */
	List<Map<String, String>> selectEnfsn(Map<String, Object> map);

	/**
	 * @Method명 : insertEarePic
	 * @param paramList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 영역별 종사자 등록
	 */
	void insertEarePic(List<Map<String, String>> paramList);

	/**
	 * @Method명 : selectDormitList
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 생활동 목록 조회
	 */
	List<Map<String, String>> selectDormitList(Map<String, String> paramMap);

	/**
	 * @Method명 : deleteEarePic
	 * @param deletedDormitList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 영역별 담당자 삭제
	 */
	void deleteEarePic(Map<String, String> deletedDormitList);

	/**
	 * @Method명 : updateEarePic
	 * @param paramList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 영역별 담당자 수정
	 */
	void updateEarePic(List<Map<String, String>> paramList);

	/**
	 * @Method명 : selectWorkDiaryExist
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 12. 5.
	 * @Method설명 :
	 */
	int selectWorkDiaryExist(Map<String, String> mapParam);

	/**
	 * @Method명 : deleteWorkCn
	 * @param deleteList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 12. 5.
	 * @Method설명 :
	 */
	void deleteWorkCn(List<Map<String, String>> deleteList);

	/**
	 * @Method명 : updateWorkDiaryAprv
	 * @param mapList
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 12. 5.
	 * @Method설명 :
	 */
	void updateWorkDiaryAprv(Map<String, String> mapList);

	/**
	 * @Method명 : selectCheckTkcgRelmNight
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 1. 18.
	 * @Method설명 : 해당 생활동, 서비스실행사업의 담당자 리스트
	 */
	List<Map<String, String>> selectCheckTkcgRelmNight(Map<String, String> mapParam);

	/**
	 * @Method명   : selectDormitAllList
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 1. 27. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDormitAllList();

}
