/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stdnt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : YouthLifeRecodeDetailService.java
 * @프로그램 설명 : 생활기록부 상세
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 14. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 14.
 * @수정내용      : 
 * -                
 * -                
 */
public interface YouthLifeRecodeDetailService {

	// 청소년정보 조회
	public Map<String, Object> selectYngbsInfo(DataRequest dataRequest) throws Exception;
	// 담당자 및 사진 조회
	public List<Map<String, String>> selectPicPhoto(DataRequest dataRequest) throws Exception;
	// 청소년인적사항 조회
	public Map<String, String> selectYngbsMatter(DataRequest dataRequest) throws Exception;
	// 출결 상황 조회
	public List<Map<String, String>> selectAtncSittn(DataRequest dataRequest) throws Exception;
	// 수상경력 조회
	public List<Map<String, String>> selectArprCareer(DataRequest dataRequest) throws Exception;
	// 청소년정보 저장
	public Map<String, String> yngbsInfoSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 자격증 및 인증 취득상황 정보 조회
	public List<Map<String, Object>> selectCertiList(DataRequest dataRequest) throws Exception;
		
	// 창의적 체험 활동 상황 정보 조회
	public List<Map<String, Object>> selectCreativeList(DataRequest dataRequest) throws Exception;
		
	// 학업 노력 상황 정보 조회
	public List<Map<String, Object>> selectSchulwList(DataRequest dataRequest) throws Exception;
	
	// 독서활동상황 조회
	public List<Map<String, Object>> selectRead(DataRequest dataRequest) throws Exception;
	
	// 봉사활동상황 조회
	public List<Map<String, Object>> selectSvcb(DataRequest dataRequest) throws Exception;
	
	// 행동특성 및 종합의견 조회
	public List<Map<String, Object>> selectOpnn(DataRequest dataRequest) throws Exception;
	
	// 자격증 및 인증 취득상황 정보 저장(등록/수정/삭제)
	public int saveCertiInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수상경력수정 조회
	public Map<String, Object> selectArprCareerUpdate(DataRequest dataRequest) throws Exception;
	
	// 수상경력 저장
	public Map<String, String> arprCareerSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수상경력 삭제
	public Map<String, String> deleteArprCareer(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 출결 상황 수정 조회
	public Map<String, Object> selectAtncSittnUpdate(DataRequest dataRequest) throws Exception;
	
	// 출결 상황 저장
	public Map<String, String> atncSittnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 독서활동상황 정보 저장(등록/수정/삭제)
	public int saveReadInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 창의적 체험활동 상황 정보 저장(등록/수정/삭제)
	public int saveCreativeInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
	// 학업 노력 상황 정보 저장(등록/수정/삭제)
	public int saveSchulwInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 출결 상황 삭제
	public Map<String, String> deleteAtncSittn(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 종사자 저장
	public Map<String, String> enfsnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 봉사활동상황 정보 저장(등록/수정/삭제)
	public int saveSvcbInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 행동특성 및 종합의견 정보 저장(등록/수정/삭제)
	public int saveOpnnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 담당자 및 사진 저장
	public Map<String, String> subPicPhotoSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 담당자 및 사진 저장 List
	public List<Map<String, String>> subPicPhotoPicSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 사진파일명(현재) 조회
	public Map<String, String> selectPicPhotoNmNow(DataRequest dataRequest) throws Exception;
	
	// 사진파일명(이전) 조회
	public Map<String, String> selectPicPhotoNmBf(DataRequest dataRequest) throws Exception;
	
	// 지원서비스 검색 조회
	public List<Map<String, Object>> selectSprtSrvcList(DataRequest dataRequest) throws Exception;

	// 출결상황 수정조회
	public List<Map<String, String>> selectAtncSittnModify(DataRequest dataRequest) throws Exception;
}
