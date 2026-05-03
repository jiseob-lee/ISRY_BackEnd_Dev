/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stdnt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : YouthLifeRecodeDetailMapper.java
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
@Mapper("youthLifeRecodeDetailMapper")
public interface YouthLifeRecodeDetailMapper {

	// 청소년정보 조회
	public Map<String, Object> selectYngbsInfo(Map<String, String> map) throws Exception;
	// 담당자 및 사진 조회
	public List<Map<String, String>> selectPicPhoto(Map<String, String> map) throws Exception;
	// 청소년 인적사항조회
	public Map<String, String> selectYngbsMatter(Map<String, String> map) throws Exception;
	// 출결 상황 조회	
	public List<Map<String, String>> selectAtncSittn(Map<String, String> map) throws Exception;
	// 수상경력 조회	
	public List<Map<String, String>> selectArprCareer(Map<String, String> map) throws Exception;
	// 청소년정보 저장
	public int UpdateYngbsInfo(Map<String, String> map) throws Exception;

	// 자격증 및 인증 취득상황 정보 조회
	public List<Map<String, Object>> selectCertiList(Map<String, String> map) throws Exception;

	// 창의적 체험 활동 상황 정보 조회
	public List<Map<String, Object>> selectCreativeList(Map<String, String> map) throws Exception;

	// 학업 노력 상황 정보 조회
	public List<Map<String, Object>> selectSchulwList(Map<String, String> map) throws Exception;
	
	//독서 활동 사항 조회
	public List<Map<String, Object>> selectRead(Map<String, String> map) throws Exception;
	
	//봉사 활동 사항 조회
	public List<Map<String, Object>> selectSvcb(Map<String, String> map) throws Exception;
	
	//독서 활동 사항 조회
	public List<Map<String, Object>> selectOpnn(Map<String, String> map) throws Exception;
	
	// 자격증 및 인증 취득상황 정보 등록
	public int insertCertiInfo(Map<String, String> map) throws Exception;
		
	// 자격증 및 인증 취득상황 정보 수정
	public int updateCertiInfo(Map<String, String> map) throws Exception;
		
	// 자격증 및 인증 취득상황 정보 삭제
	public int deleteCertiInfo(Map<String, String> map) throws Exception;
	
	// 수상경력수정 조회	
	public Map<String, Object> selectArprCareerUpdate(Map<String, String> map) throws Exception;
	
	// 수상경력 수정
	public int UpdateArprCareer(Map<String, String> map) throws Exception;
	
	// 수상경력 입력
	public int InsertArprCareer(Map<String, String> map) throws Exception;
	
	// 색인일련번호 + 1 가져오기
	public String selectIndexSn(Map<String, String> map) throws Exception;
	
	// 수상경력 삭제
	public int DeleteArprCareer(Map<String, String> map) throws Exception;
	
	// 수상경력수정 조회	
	public Map<String, Object> selectAtncSittnUpdate(Map<String, String> map) throws Exception;
	
	// 출결 상황 수정
	public int UpdateAtncSittn(Map<String, String> map) throws Exception;
	
	// 출결 상황 입력
	public int InsertAtncSittn(Map<String, String> map) throws Exception;
	
	// 독서 활동 사항 정보 등록
	public int insertRead(Map<String, String> map) throws Exception;
		
	// 독서 활동 사항 정보 수정
	public int updateRead(Map<String, String> map) throws Exception;
		
	// 독서 활동 사항 정보 삭제
	public int deleteRead(Map<String, String> map) throws Exception;

	// 창의적 체험활동 상황 정보 등록
	public int insertCreativeInfo(Map<String, String> map) throws Exception;
			
	// 창의적 체험활동 상황 정보 수정
	public int updateCreativeInfo(Map<String, String> map) throws Exception;
			
	// 창의적 체험활동 상황 정보 삭제
	public int deleteCreativeInfo(Map<String, String> map) throws Exception;
		
	// 학업 노력 상황 정보 등록
	public int insertSchulwInfo(Map<String, String> map) throws Exception;
			
	// 학업 노력 상황 정보 수정
	public int updateSchulwInfo(Map<String, String> map) throws Exception;
			
	// 학업 노력 상황 정보 삭제
	public int deleteSchulwInfo(Map<String, String> map) throws Exception;

	// 출결 상황 삭제
	public int DeleteAtncSittn(Map<String, String> map) throws Exception;
	
	// 종사자 저장
	public int InsertEnfsn(Map<String, String> map) throws Exception;
	
	// 봉사 활동 사항 정보 등록
	public int insertSvcb(Map<String, String> map) throws Exception;
		
	// 봉사 활동 사항 정보 수정
	public int updateSvcb(Map<String, String> map) throws Exception;
		
	// 봉사 활동 사항 정보 삭제
	public int deleteSvcb(Map<String, String> map) throws Exception;
	
	// 행동특성 및 종합의견 정보 등록
	public int insertOpnn(Map<String, String> map) throws Exception;
		
	// 행동특성 및 종합의견 정보 등록
	public int updateOpnn(Map<String, String> map) throws Exception;
		
	// 행동특성 및 종합의견 정보 등록
	public int deleteOpnn(Map<String, String> map) throws Exception;
	
	// 담당자 및 사진 저장
	public int UpdatePicPhoto(Map<String, String> map) throws Exception;
	
	// 담당자 및 사진 저장 List
	public int UpdatePicPhotoPic(Map<String, String> map) throws Exception;
	
	// 담당자 삭제
	public int DeletePicPhotoPic(Map<String, String> map) throws Exception;
	
	// 청소년사진 파일명(현재) 가져오기	
	public Map<String, String> selectPicPhotoNmNow(Map<String, String> map) throws Exception;
	
	// 청소년사진 파일명(과거) 가져오기	
	public Map<String, String> selectPicPhotoNmBf(Map<String, String> map) throws Exception;
	
	// 지원서비스 검색 조회
	public List<Map<String, Object>> selectSprtSrvcList(Map<String, String> paramMap) throws Exception;
	
	// 사용일자COUNT 조회
	public String selectUseYmdCnt(Map<String, String> map) throws Exception;
	
	// 출결 상황_제공서비스 입력
	public int InsertAtncSittnPvsnSrvc(Map<String, String> dsPrdList) throws Exception;
	
	// 색인일련번호 구하기
	public Map<String, Object> selectIndexSn370(Map<String, String> map) throws Exception;
	
	// 출결상황 수정조회
	public List<Map<String, String>> selectAtncSittnModify(Map<String, String> map) throws Exception;
	
	// 출결 상황_제공서비스 수정
	public int UpdateAtncSittnPvsnSrvc(Map<String, String> dsPrdList) throws Exception;
	
	// 출결 상황_제공서비스 삭제
	public int DeleteAtncSittnPvsnSrvc(Map<String, String> dsPrdList) throws Exception;
}
