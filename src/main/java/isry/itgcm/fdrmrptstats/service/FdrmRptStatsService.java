/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.fdrmrptstats.service;

import java.util.List;
import java.util.Map;

/**
 * @파일명 : FdrmRptStatsService.java
 * @프로그램 설명 : 정기보고통계 서비스 - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 7. 27.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 7. 27.
 * @수정내용 : - -
 */
public interface FdrmRptStatsService {

	/**
	 * @Method명 : getLwprtInstList
	 * @param instNo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 3.
	 * @Method설명 : 하위기관목록 조회
	 */
	int[] getLwprtInstList(Integer instNo) throws Exception;

	/**
	 * @Method명 : selectUneartRegStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 발굴등록통계
	 */
	List<Map<String, Object>> selectUneartRegStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectCaseMngStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사례관리통계
	 */
	List<Map<String, Object>> selectCaseMngStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSprtSrvcStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 지원서비스통계
	 */
	List<Map<String, Object>> selectSprtSrvcStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectCrisisLevelPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 위기수준에 따른 지원현황
	 */
	List<Map<String, Object>> selectCrisisLevelPrecon(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : select1388TlphonDscsn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 1388전화상담통계
	 */
	List<Map<String, Object>> select1388TlphonDscsn(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectEfectnEvlSrvcDgstfn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 효과성 평가 및 서비스 만족도
	 */
	List<Map<String, Object>> selectEfectnEvlSrvcDgstfn(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectOutcStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 성과통계
	 */
	List<Map<String, Object>> selectOutcStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectOutrcActvt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 아웃리치활동
	 */
	List<Map<String, Object>> selectOutrcActvt(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectOuthfaYngbgsActnPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 가정밖청소년조치현황
	 */
	List<Map<String, Object>> selectOuthfaYngbgsActnPrecon(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectEntrncLvngCaseMng
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 입소퇴소사례관리
	 */
	List<Map<String, Object>> selectEntrncLvngCaseMng(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectPstrtrSrlst
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소후자립현황
	 */
	List<Map<String, Object>> selectPstrtrSrlst(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectAftfctSprtSrvc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사후지원서비스
	 */
	List<Map<String, Object>> selectAftfctSprtSrvc(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSrvcDgstfn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 서비스만족도
	 */
	List<Map<String, Object>> selectSrvcDgstfn(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectLvngCsPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소사유별현황
	 */
	List<Map<String, Object>> selectLvngCsPrecon(Map<String, Object> paramMap) throws Exception;

}
