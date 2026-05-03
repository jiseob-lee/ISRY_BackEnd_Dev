/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.fdrmrptstats.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.itgcm.fdrmrptstats.mapper.FdrmRptStatsMapper;
import isry.itgcm.fdrmrptstats.service.FdrmRptStatsService;

/**
 * @파일명 : FdrmRptStatsServiceImpl.java
 * @프로그램 설명 : 정기보고통계 서비스임플 - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 7. 27.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 7. 27.
 * @수정내용 : - -
 */
@Service("fdrmRptStatsService")
public class FdrmRptStatsServiceImpl implements FdrmRptStatsService {

	@Resource(name = "fdrmRptStatsMapper")
	FdrmRptStatsMapper fdrmRptStatsMapper;

	/**
	 * @Method명 : getLwprtInstList
	 * @param instNo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 3.
	 * @Method설명 : 하위기관목록 조회
	 */
	@Override
	public int[] getLwprtInstList(Integer instNo) throws Exception {
		return fdrmRptStatsMapper.getLwprtInstList(instNo);
	}

	/**
	 * @Method명 : selectUneartRegStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 발굴등록
	 */
	@Override
	public List<Map<String, Object>> selectUneartRegStats(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U01": // 지자체
		case "U03": // 학교밖
			return fdrmRptStatsMapper.selectUneartRegStats(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectCaseMngStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사례관리
	 */
	@Override
	public List<Map<String, Object>> selectCaseMngStats(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U01": // 지자체
		case "U02": // 청상복
		case "U03": // 학교밖
			return fdrmRptStatsMapper.selectCaseMngStats(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectSprtSrvcStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 지원서비스
	 */
	@Override
	public List<Map<String, Object>> selectSprtSrvcStats(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U02": // 청상복
			if (paramMap.get("MODE").equals("01")) { // 고위기청소년지원
				return fdrmRptStatsMapper.selectSprtSrvcStatsHlisk(paramMap);
			}
		case "U01": // 지자체
		case "U03": // 학교밖
		case "U05": // 자립지원관
		case "U06": // 회복지원
			return fdrmRptStatsMapper.selectSprtSrvcStatsCaseReg(paramMap);
		case "U04": // 쉼터
			if (paramMap.get("MODE").equals("04")) { // 사례미등록 별도 통계
				return fdrmRptStatsMapper.selectSprtSrvcStatsCaseUnregi(paramMap);
			}
			return fdrmRptStatsMapper.selectSprtSrvcStatsSheltrType(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectCrisisLevelPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 위기수준에 따른 지원현황
	 */
	@Override
	public List<Map<String, Object>> selectCrisisLevelPrecon(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.selectCrisisLevelPrecon(paramMap);
	}

	/**
	 * @Method명 : select1388TlphonDscsn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 1388전화상담
	 */
	@Override
	public List<Map<String, Object>> select1388TlphonDscsn(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.select1388TlphonDscsn(paramMap);
	}

	/**
	 * @Method명 : selectEfectnEvlSrvcDgstfn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 효과성 평가 및 서비스 만족도
	 */
	@Override
	public List<Map<String, Object>> selectEfectnEvlSrvcDgstfn(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.selectEfectnEvlSrvcDgstfn(paramMap);
	}

	/**
	 * @Method명 : selectOutcStats
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 성과
	 */
	@Override
	public List<Map<String, Object>> selectOutcStats(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.selectOutcStats(paramMap);
	}

	/**
	 * @Method명 : selectOutrcActvt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 아웃리치 활동
	 */
	@Override
	public List<Map<String, Object>> selectOutrcActvt(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.selectOutrcActvt(paramMap);
	}

	/**
	 * @Method명 : selectOuthfaYngbgsActnPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 가정밖청소년 조치현황
	 */
	@Override
	public List<Map<String, Object>> selectOuthfaYngbgsActnPrecon(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.selectOuthfaYngbgsActnPrecon(paramMap);
	}

	/**
	 * @Method명 : selectEntrncLvngCaseMng
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 입소퇴소사례관리
	 */
	@Override
	public List<Map<String, Object>> selectEntrncLvngCaseMng(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U04": // 쉼터
			return fdrmRptStatsMapper.selectEntrncLvngCaseMngSheltrType(paramMap);
		case "U05": // 자립지원관
		case "U06": // 회복지원시설
			return fdrmRptStatsMapper.selectEntrncLvngCaseMngSxdc(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectPstrtrSrlst
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소후자립현황
	 */
	@Override
	public List<Map<String, Object>> selectPstrtrSrlst(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U04": // 쉼터
			return fdrmRptStatsMapper.selectPstrtrSrlstSheltrType(paramMap);
		case "U05": // 자립지원관
			return fdrmRptStatsMapper.selectPstrtrSrlstSxdc(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectAftfctSprtSrvc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 사후지원서비스
	 */
	@Override
	public List<Map<String, Object>> selectAftfctSprtSrvc(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U04": // 쉼터
			return fdrmRptStatsMapper.selectAftfctSprtSrvcSheltrType(paramMap);
		case "U05": // 자립지원관
		case "U06": // 회복지원시설
			return fdrmRptStatsMapper.selectAftfctSprtSrvcSxdc(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectSrvcDgstfn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 서비스 만족도
	 */
	@Override
	public List<Map<String, Object>> selectSrvcDgstfn(Map<String, Object> paramMap) throws Exception {
		String untTaskwk = String.valueOf(paramMap.get("UNT_TASKWK_SE_CD"));

		switch (untTaskwk) {
		case "U04": // 쉼터
			return fdrmRptStatsMapper.selectSrvcDgstfnSheltrType(paramMap);
		case "U05": // 자립지원관
			return fdrmRptStatsMapper.selectSrvcDgstfnSxdc(paramMap);
		}
		return null;
	}

	/**
	 * @Method명 : selectLvngCsPrecon
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 28.
	 * @Method설명 : 퇴소사유별 현황
	 */
	@Override
	public List<Map<String, Object>> selectLvngCsPrecon(Map<String, Object> paramMap) throws Exception {
		return fdrmRptStatsMapper.selectLvngCsPrecon(paramMap);
	}
}
