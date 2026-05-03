/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.dashboard.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CounsDashboardService.java
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
public interface CounsDashboardService {
	List<Map<String, String>> selectDscsnPrfmncMainList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, String>> selectDscsnPrfmncDetailList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, String>> selectMnthngSchdlcyberDscsnList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, String>> selectMnthngSchdlcyberOutList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, String>> selectMngrMntrgSchdlList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, String>> selectBbsonmList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, String>> selectSpclaList(Map<String, String> mapParam) throws Exception;
	// 퇴근처리 기본정보 조회
	Map<String, Object> selectLvffcPrcsBassInfo(HttpServletRequest request,DataRequest dataRequest) throws Exception;
	// 퇴근처리 저장
	Map<String, String> updatelvffcPrcs(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 상담원 퇴근하기 취소
	Map<String, String> deleteLvffcPrcs(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 다음출근 시간 조회
	Map<String, Object> selectNextAtndb(HttpServletRequest request, DataRequest dataRequest) throws Exception;		
	// 오늘출근 시간 조회
	Map<String, Object> selectAtndb(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 공지사항
	List<Map<String, String>> selectNoticeList(Map<String, String> mapParam) throws Exception;
	// 상담원 오늘 근무 정보 조회
	Map<String, Object> selectTodayWorkInfoByCnsltnt(Map<String, String> mapParam) throws Exception;
}
