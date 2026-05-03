/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.fdrmrptstats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : FdrmRptStatsMapper.java
 * @프로그램 설명 : 정기보고통계 매퍼 - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 7. 27.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 7. 27.
 * @수정내용 : - -
 */
@Mapper("fdrmRptStatsMapper")
public interface FdrmRptStatsMapper {

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
	 * @Method설명 : 발굴등록
	 */
	List<Map<String, Object>> selectUneartRegStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectCaseMngStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사례관리
	 */
	List<Map<String, Object>> selectCaseMngStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSprtSrvcStatsCaseReg
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 지원서비스 - 사례등록/사례미등록
	 */
	List<Map<String, Object>> selectSprtSrvcStatsCaseReg(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSprtSrvcStatsHlisk
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 지원서비스 - 사례등록만
	 */
	List<Map<String, Object>> selectSprtSrvcStatsHlisk(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSprtSrvcStatsSheltrType
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 지원서비스 - 쉼터유형별
	 */
	List<Map<String, Object>> selectSprtSrvcStatsSheltrType(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSprtSrvcStatsCaseUnregi
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 지원서비스 - 사례미등록별도
	 */
	List<Map<String, Object>> selectSprtSrvcStatsCaseUnregi(Map<String, Object> paramMap) throws Exception;

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
	 * @Method설명 : 1388 전화상담
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
	 * @Method설명 : 성과
	 */
	List<Map<String, Object>> selectOutcStats(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectOutrcActvt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 아웃리치 활동
	 */
	List<Map<String, Object>> selectOutrcActvt(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectOuthfaYngbgsActnPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 가정밖 청소년 조치현황
	 */
	List<Map<String, Object>> selectOuthfaYngbgsActnPrecon(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectEntrncLvngCaseMngSheltrType
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 입퇴소사례관리 - 쉼터유형별
	 */
	List<Map<String, Object>> selectEntrncLvngCaseMngSheltrType(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectEntrncLvngCaseMngSxdc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 입퇴소사례관리 - 성별
	 */
	List<Map<String, Object>> selectEntrncLvngCaseMngSxdc(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectPstrtrSrlstSheltrType
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소후 자립현황 - 쉼터유형별
	 */
	List<Map<String, Object>> selectPstrtrSrlstSheltrType(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectPstrtrSrlstSxdc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소후 자립현황 - 성별
	 */
	List<Map<String, Object>> selectPstrtrSrlstSxdc(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectAftfctSprtSrvcSheltrType
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사후지원서비스 - 쉼터유형별
	 */
	List<Map<String, Object>> selectAftfctSprtSrvcSheltrType(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectAftfctSprtSrvcSxdc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사후지원서비스 - 성별
	 */
	List<Map<String, Object>> selectAftfctSprtSrvcSxdc(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSrvcDgstfnSheltrType
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 서비스만족도 - 쉼터유형별
	 */
	List<Map<String, Object>> selectSrvcDgstfnSheltrType(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectSrvcDgstfnSxdc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 서비스만족도 - 성별
	 */
	List<Map<String, Object>> selectSrvcDgstfnSxdc(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectLvngCsPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소사유별 현황
	 */
	List<Map<String, Object>> selectLvngCsPrecon(Map<String, Object> paramMap) throws Exception;

}
