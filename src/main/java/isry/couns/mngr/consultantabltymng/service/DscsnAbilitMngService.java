/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consultantabltymng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : DscsnAbilitMngService.java
 * @프로그램 설명 : 상담원 역량관리
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 01. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 01. 
 * @수정내용      : 
 * -                
 * -                
 */

public interface DscsnAbilitMngService {

	// 평가구성 조회
	public List<Map<String, Object>> selectEvlCnsttnList(DataRequest dataRequest) throws Exception;
		
	// 평가구성 등록
	public Map<String, String> evlCnsttnInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가구성수정 조회
	public Map<String, Object> selectEvlCnsttnUpdate(DataRequest dataRequest) throws Exception;
	
	// 평가구성 수정
	public Map<String, String> evlCnsttnUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 확정 수정
	public Map<String, String> cfmtnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 우수사례로우삭제
	public Map<String, String> exclncCaseRowDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 우수사례전체삭제
	public Map<String, String> exclncCaseAllDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수퍼비전전체삭제
	public Map<String, String> superVisionAllDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수퍼비전로우삭제
	public Map<String, String> superVisionRowDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 확정취소 수정
	public Map<String, String> cfmtnRtrcnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가지관리 조회
	public List<Map<String, Object>> selectEvfoMngList(DataRequest dataRequest) throws Exception;
	
	// 평가지관리 기본정보 조회
	public Map<String, Object> selectEvfoMngBassInfo(DataRequest dataRequest) throws Exception;
	
	// 평가지관리 목록 조회
	public List<Map<String, Object>> selectEvfoMngInfoList(DataRequest dataRequest) throws Exception;
	
	// 평가지 추가 등록
	public Map<String, String> evfoAddingInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가지 추가 조회
	public Map<String, Object> selectEvfoAdding(DataRequest dataRequest) throws Exception;
	
	// 평가지관리 역량관리 기본정보 조회
	public Map<String, Object> selectEvfoAbilitMngBassInfo(DataRequest dataRequest) throws Exception;
	
	// 평가지관리 기준관리 기본정보 조회
	public Map<String, Object> selectEvfoCrtrMngBassInfo(DataRequest dataRequest) throws Exception;
	
	// 평가지관리 역량관리 목록 조회
	public List<Map<String, Object>> selectEvfoAbilitMngList(DataRequest dataRequest) throws Exception;
	
	// 평가지관리 기준관리 목록 조회
	public List<Map<String, Object>> selectEvfoCrtrMngList(DataRequest dataRequest) throws Exception;
	
	// 평가지 역량등록
	public Map<String, String> evfoAbilitMngInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가자 관리 조회
	public List<Map<String, Object>> selectApraiMngList(DataRequest dataRequest) throws Exception;
	
	// 평가 대상자 관리 조회
	public List<Map<String, Object>> selectEvlTrprMngList(DataRequest dataRequest) throws Exception;
	
	// 평가대상자 선정 조회
	public List<Map<String, Object>> selectEvlTrprSlctnList(DataRequest dataRequest) throws Exception;
	
	// 평가대상가 매칭 목록 조회
	public List<Map<String, Object>> selectEvlTrprMatchingList(DataRequest dataRequest) throws Exception;
	
	// 평가대상자선택 목록 조회
	public List<Map<String, Object>> selectEvlTrprChcList(DataRequest dataRequest) throws Exception;
	
	// 평가대상자선정 등록	
	public List<Map<String, String>> evlTrprSlctnInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가대상자선택 등록	
	public List<Map<String, String>> evlTrprChcInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가서관리 조회
	public List<Map<String, Object>> selectEvlSeMngList(DataRequest dataRequest) throws Exception;
	
	// 평가서관리 평가자목록 조회
	public List<Map<String, Object>> selectEvlSeMngInqList(DataRequest dataRequest) throws Exception;
	
	// 역량수정 조회
	public Map<String, Object> selectEvfoAbilitUpdate(DataRequest dataRequest) throws Exception;
	
	// 역량수정
	public Map<String, String> evfoAbilitUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 역량삭제
	public Map<String, String> evfoAbilitMngDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가기준 추가 조회
	public List<Map<String, Object>> selectEvfoCrtrAddingIngList(DataRequest dataRequest) throws Exception;
	
	// 평가기준추가 수정
	public Map<String, String> evfoCrtrAdding(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가기준수정 조회
	public Map<String, Object> selectEvfoCrtrUpdate(DataRequest dataRequest) throws Exception;
	
	// 평가기준수정
	public Map<String, String> evfoCrtr(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가지평가기준관리 삭제
	public Map<String, String> evfoCrtrMngDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가지관리 기준관리 기본정보 조회
	public Map<String, Object> selectMngrApraiMngBassInfo(DataRequest dataRequest) throws Exception;
	
	// 관리자평가자관리 목록 조회
	public List<Map<String, Object>> selectMngrApraiMngList(DataRequest dataRequest) throws Exception;
	
	// 동료상담원 평가자관리 목록 조회
	public List<Map<String, Object>> selectMngrApraiMngCoList(DataRequest dataRequest) throws Exception;
	
	// 동료상담원 결과 목록조회
	public List<Map<String, Object>> selectMngrApraiMngCoResultList(DataRequest dataRequest) throws Exception;
	
	// 평가자목록 조회
	public List<Map<String, Object>> selectApraiList(DataRequest dataRequest) throws Exception;
	
	// 평가자추가
	public Map<String, String> araiInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 관리자평가자 삭제
	public Map<String, String> mngrApraiDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 관리자평가자수정 조회
	public Map<String, Object> selectMngrApraiUpdate(DataRequest dataRequest) throws Exception;
	
	// 관리자평가자 수정
	public Map<String, String> mngrApraiUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 본원평가위원평가자관리기본정보 조회
	public Map<String, Object> selectEvlMfcmmApraiMngBassInfo(DataRequest dataRequest) throws Exception;
	
	// 본원평가위원평가자관리기본정보 목록 조회
	public List<Map<String, Object>> selectEvlMfcmmApraiMngBassInfoList(DataRequest dataRequest) throws Exception;
	
	// 평가서관리 조회
	public List<Map<String, Object>> selectEvlSeMngInq(DataRequest dataRequest) throws Exception;
	
	// 평가서관리구분 조회
	public List<Map<String, Object>> selectEvlSeMngSeInq(DataRequest dataRequest) throws Exception;
	
	// 상담원 - 평가대상 여부 조회
	public Map<String, Object> selectApraiIdnty(HttpServletRequest request) throws Exception;
	
	// 평가서 작성 대상자 목록 조회
	public List<Map<String, Object>> selectEvlWrtTrprList(DataRequest dataRequest) throws Exception;
	
	// 수행 적절성평가표 기본정보 조회
	public Map<String, Object> selectRelevaEvlBassInfo(DataRequest dataRequest) throws Exception;
	
	// 수행 적절성평가표 목록 조회
	public List<Map<String, Object>> selectRelevaEvlList(DataRequest dataRequest) throws Exception;
	
	// 평가관리 저장
	public Map<String, String> evlMngSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 우수사례양식 저장
	public Map<String, String> exclncCaseMmSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수퍼비전양식 저장
	public Map<String, String> superVisionMmSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가관리목록 저장
	public List<Map<String, String>> evlMngListInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가자 동료상담원 저장
	public List<Map<String, String>> mngrApraiMngCoListInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평가점수관리 목록조회
	public List<Map<String, Object>> selectEvlScoreMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 소속기관 목록조회
	public List<Map<String, Object>> selectOgdpInstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 우수사례관리 목록 조회
	public List<Map<String, Object>> selectExclncCaseMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수퍼비전관리 목록 조회
	public List<Map<String, Object>> selectSuperVisionMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
	// 우수사례관리 상담자 조회
	public List<Map<String, Object>> selectExclncCaseConsttList(DataRequest dataRequest) throws Exception;
	
	// 수퍼비전관리 상담자 조회
	public List<Map<String, Object>> selectSuperVisionConsttList(DataRequest dataRequest) throws Exception;
	
	// 우수사례관리 상담자 저장	
	public List<Map<String, String>> exclncCaseConsttInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 수퍼비전관리 상담자 저장	
	public List<Map<String, String>> superVisionConsttInsert(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 평자지관리 엑셀업로드
	public List<Map<String, String>> processEnfoMngExcelUpload(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
//-------------------------------------------------------------------------------------------------------------------------------
// 관리자 - 상담원 역량관리 - 우수사례관리
//-------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @Method명   : selectExclncModeInfo
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 양식관리에서 등록한 양식 파일 정보 조회
	 */
	public Map<String, String> selectExclncModeInfo() throws Exception;
	
	/**
	 * 우수사례 제출 자료 삭제
	 * @Method명   : exclncCaseMmDelete
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 : 해당 월에 제출한 자료(우수사례)를 삭제 처리
	 */
	public int exclncCaseMmDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
//-------------------------------------------------------------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------------------------------------------------------------
	
//-------------------------------------------------------------------------------------------------------------------------------
// 관리자 - 상담원 역량관리 - 수퍼비전관리
//-------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @Method명   : selectSuperVisionModeInfo
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 양식관리에서 등록한 양식 파일 정보 조회
	 */
	public Map<String, String> selectSuperVisionModeInfo() throws Exception;
	
	/**
	 * 수퍼비전 제출 자료 삭제
	 * @Method명   : superVisionMmDelete
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 해당 월에 제출한 자료(수퍼비전)를 삭제 처리
	 */
	public int superVisionMmDelete(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
//-------------------------------------------------------------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------------------------------------------------------------
	
//-------------------------------------------------------------------------------------------------------------------------------
// 관리자 - 상담원 역량관리 - 교육관리
//-------------------------------------------------------------------------------------------------------------------------------

	// 교육이력 및 수료증 출력 - 목록 조회
	public List<Map<String, Object>> selectEduHstrList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 교육 참석자 - 이수증출력번호 부여
	public int updateEduCtcplNo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 교육관리 - 목록 조회
	public List<Map<String, Object>> selectEduMngList(DataRequest dataRequest) throws Exception;
	
	// 교육등록 - 사이버상담 교육인원 목록 조회
	public List<Map<String, Object>> selectCreateCyberDscsnList(DataRequest dataRequest) throws Exception;
	
	// 교육등록 - 모바일상담 교육인원 목록 조회
	public List<Map<String, Object>> selectCreateMblaDscsnList(DataRequest dataRequest) throws Exception;
	
	// 교육관리 - 저장
	public int insertEduMngSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 교육관리 - 수정
	public int updateEduMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 교육관리 - 삭제
	public int deleteEduMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 교육상세 - 교육정보 조회
	public Map<String, Object> selectEduMngBassInfo(DataRequest dataRequest) throws Exception;
	
	// 교육상세 - 사이버 미참석 목록
	public List<Map<String, Object>> selectCyberNonAtndList(DataRequest dataRequest) throws Exception;
	
	// 교육상세 - 사이버 참석 목록
	public List<Map<String, Object>> selectCyberAtndList(DataRequest dataRequest) throws Exception;
	
	// 교육상세 - 모바일 미참석 목록
	public List<Map<String, Object>> selectMblaNonAtndList(DataRequest dataRequest) throws Exception;
	
	// 교육상세 - 모바일 참석 목록
	public List<Map<String, Object>> selectMblaAtndList(DataRequest dataRequest) throws Exception;
	
	// 교육관리 저장 ==> X
	public Map<String, String> eduMngSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 교육관리 목록 저장 ==> X
	public List<Map<String, String>> eduMngSaveListInsert(HttpServletRequest request, DataRequest dataRequest, String sEduEstblSn) throws Exception;
	
	// 모바일상담 교육인원수정 목록조회 ==> X
	public List<Map<String, Object>> selectMblaDscsnUpdateList(DataRequest dataRequest) throws Exception;
	
	// 사이버상담 교육인원수정 목록조회 ==> X
	public List<Map<String, Object>> selectCyberDscsnUpdateList(DataRequest dataRequest) throws Exception;
	
//-------------------------------------------------------------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------------------------------------------------------------
	
		
}
