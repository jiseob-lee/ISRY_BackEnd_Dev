/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.dashboard.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CounsDashboardMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Sin.Hyun.Jin
 * @작성일        : 2022. 12. 12. 
 * @수정자        : Sin.Hyun.Jin
 * @수정일        : 2022. 12. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("counsDashboardMapper")
public interface CounsDashboardMapper {
	
	List<Map<String, String>> selectDscsnPrfmncMainList(Map<String, String> mapParam);
	
	List<Map<String, String>> selectDscsnPrfmncDetailList(Map<String, String> mapParam);
	
	List<Map<String, String>> selectMnthngSchdlcyberDscsnList(Map<String, String> mapParam);
	
	List<Map<String, String>> selectMnthngSchdlcyberOutList(Map<String, String> mapParam);
	
	List<Map<String, String>> selectMngrMntrgSchdlList(Map<String, String> mapParam);
	
	List<Map<String, String>> selectBbsonmList(Map<String, String> mapParam);
	
	List<Map<String, String>> selectSpclaList(Map<String, String> mapParam);
	
	// 상담원 퇴근시간 조회
	Map<String, Object> selectLvffcPrcsBassInfo(Map<String, String> map) throws Exception;
	
	// 퇴근처리 저장
	int UpdateLvffcPrcs(Map<String, String> map) throws Exception;
	
	// 상담원 퇴근하기 취소
	void deleteLvffcPrcs(Map<String, String> mapParam);
	
	// 다음출근 시간 조회
	Map<String, Object> selectNextAtndb(Map<String, String> map) throws Exception;
	
	// 오늘출근 시간 조회
	Map<String, Object> selectAtndb(Map<String, String> map) throws Exception;	
	
	// 공지사항
	List<Map<String, String>> selectNoticeList(Map<String, String> mapParam);
	
	// 상담원 오늘 근무일자 조회
	String selectTodayWorkYmd(Map<String, String> mapParam);
	
	// AYC495(상담원출퇴근관리)데이터 존재 여부
	int selectCnsltntTaskwkReprtCount(Map<String, String> mapParm);
	
	// 상담원 오늘 근무 정보 조회
	Map<String, String> selectTodayWorkInfoByCnsltnt(Map<String, String> mapParam);
}
