package isry.couns.mngr.consultantabltymng.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DscsnAbilitMngMapper.java
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
@Mapper("dscsnAbilitMngMapper")
public interface DscsnAbilitMngMapper{
	
	// 평가구성 조회
	public List<Map<String, Object>> selectEvlCnsttnList(Map<String, String> paramMap) throws Exception;
	// 평가구성 입력
	public int InsertEvlCnsttn(Map<String, String> map) throws Exception;	
	// 평가지관리상세 입력 
	public int InsertEvlCnsttnDtl(Map<String, String> map) throws Exception;
	// 종사자명 구하기
	public Map<String, Object> selectEnfsnNm(String string);	
	// 평가구성수정 조회
	public Map<String, Object> selectEvlCnsttnUpdate(Map<String, String> map) throws Exception;
	// 평가구성 수정
	public int UpdateEvlCnsttn(Map<String, String> map) throws Exception;	
//	// 평가지관리상세 수정 
//	public int UpdateEvlCnsttnDtl(Map<String, String> map) throws Exception;
	// 확정 수정 
	public int UpdateCfmtnYn(Map<String, String> map) throws Exception;	
	// 우수사례로우삭제
	public int UpdateExclncCaseRow(Map<String, String> map) throws Exception;	
	// 우수사례전체삭제
	public int UpdateExclncCaseAll(Map<String, String> map) throws Exception;	
	// 수퍼비전전체삭제
	public int UpdateSuperVisionAll(Map<String, String> map) throws Exception;
	// 수퍼비전로우삭제
	public int UpdateSuperVisionRow(Map<String, String> map) throws Exception;	
	// 확정취소 수정 
	public int UpdateCfmtnRtrcnYn(Map<String, String> map) throws Exception;	
	// 평가지관리 조회
	public List<Map<String, Object>> selectEvfoMngList(Map<String, String> paramMap) throws Exception;	
	// 평가지관리 기본정보 조회
	public Map<String, Object> selectEvfoMngBassInfo(Map<String, String> map) throws Exception;
	// 평가지관리 목록 조회
	public List<Map<String, Object>> selectEvfoMngInfoList(Map<String, String> map) throws Exception;
	// 평가지 추가
	public int UpdateEvfoAdding(Map<String, String> map) throws Exception;	
	// 평가지 추가 조회
	public Map<String, Object> selectEvfoAdding(Map<String, String> map) throws Exception;
	// 평가지관리 역량관리 기본정보 조회
	public Map<String, Object> selectEvfoAbilitMngBassInfo(Map<String, String> map) throws Exception;
	// 평가지관리 기준관리 기본정보 조회
	public Map<String, Object> selectEvfoCrtrMngBassInfo(Map<String, String> map) throws Exception;
	// 평가지관리 역량관리 목록 조회
	public List<Map<String, Object>> selectEvfoAbilitMngList(Map<String, String> map) throws Exception;	
	// 평가지관리 기준관리 목록 조회
	public List<Map<String, Object>> selectEvfoCrtrMngList(Map<String, String> map) throws Exception;	
	// 평가지 역량등록
	public int InsertEvfoAbilitMng(Map<String, String> map) throws Exception;	
	// 평가지 역량등록_그룹
	public int InsertEvfoAbilitMngGroup(Map<String, String> map) throws Exception;
	// 평가자 관리 조회
	public List<Map<String, Object>> selectApraiMngList(Map<String, String> paramMap) throws Exception;
	// 평가 대상자 관리 조회
	public List<Map<String, Object>> selectEvlTrprMngList(Map<String, String> paramMap) throws Exception;
	// 평가대상자 선정 조회
	public List<Map<String, Object>> selectEvlTrprSlctnList(Map<String, String> paramMap) throws Exception;
	// 평가대상자 매칭 목록 조회
	public List<Map<String, Object>> selectEvlTrprMatchingList(Map<String, String> paramMap) throws Exception;
	// 평가대상자선택 목록 조회
	public List<Map<String, Object>> selectEvlTrprChcList(Map<String, String> paramMap) throws Exception;
	// 평가대상자선정 등록
	public int insertEvlTrprSlctn(Map<String, String> map) throws Exception;
	// 평가대상자선정 일련번호
	public Integer selectEvlSeq() throws Exception;
	// 평가대상자선정 수정
	public int updateTrprSlctn(Map<String, String> map) throws Exception;
	// 평가대상자선정 수정(False)
	public int updateTrprSlctnFalse(Map<String, String> map) throws Exception;
	// 평가대상자선택 등록
	public int insertEvlTrprChc(Map<String, String> map) throws Exception;
	// 평가서관리 조회
	public List<Map<String, Object>> selectEvlSeMngList(Map<String, String> paramMap) throws Exception;
	// 평가서관리 평가자목록 조회
	public List<Map<String, Object>> selectEvlSeMngInqList(Map<String, String> paramMap) throws Exception;
	// 역량수정 조회
	public Map<String, Object> selectEvfoAbilitUpdate(Map<String, String> map) throws Exception;	
	// 역량 수정
	public int UpdateEvfoAbilit(Map<String, String> map) throws Exception;	
	// 역량 삭제
	public int DeleteEvfoAbilitMng(Map<String, String> map) throws Exception;	
	// 평가기준 추가 조회
	public List<Map<String, Object>> selectEvfoCrtrAddingIngList(Map<String, String> paramMap) throws Exception;
	// 평가기준추가 수정
	public int InsertEvfoCrtrAdding(Map<String, String> map) throws Exception;		
	// 평가기준수정 조회
	public Map<String, Object> selectEvfoCrtrUpdate(Map<String, String> map) throws Exception;	
	// 평가기준수정
	public int UpdateEvfoCrtr(Map<String, String> map) throws Exception;	
	// 평가지평가기준관리 삭제
	public int DeleteEvfoCrtrMng(Map<String, String> map) throws Exception;	
	// 평가지관리 기준관리 기본정보 조회
	public Map<String, Object> selectMngrApraiMngBassInfo(Map<String, String> map) throws Exception;
	// 관리자평가자관리 목록 조회
	public List<Map<String, Object>> selectMngrApraiMngList(Map<String, String> paramMap) throws Exception;
	// 동료상담원 결과 목록조회
	public List<Map<String, Object>> selectMngrApraiMngCoResultList(Map<String, String> paramMap) throws Exception;
	// 동료상담원 평가자관리 목록 조회
	public List<Map<String, Object>> selectMngrApraiMngCoList(Map<String, String> paramMap) throws Exception;
	// 평가자목록 조회
	public List<Map<String, Object>> selectApraiList(Map<String, String> paramMap) throws Exception;
	// 평가자추가
	public int InsertArai(Map<String, String> map) throws Exception;
	// 관리자평가자 삭제
	public int DeleteMngrAprai(Map<String, String> map) throws Exception;
	// 관리자평가자수정 조회
	public Map<String, Object> selectMngrApraiUpdate(Map<String, String> map) throws Exception;
	// 관리자평가자수정
	public int UpdateMngrAprai(Map<String, String> map) throws Exception;	
	// 본원평가위원평가자관리기본정보 조회
	public Map<String, Object> selectEvlMfcmmApraiMngBassInfo(Map<String, String> map) throws Exception;
	// 본원평가위원평가자관리기본정보 목록 조회
	public List<Map<String, Object>> selectEvlMfcmmApraiMngBassInfoList(Map<String, String> paramMap) throws Exception;
	// 평가서관리 조회
	public List<Map<String, Object>> selectEvlSeMngInq(Map<String, String> paramMap) throws Exception;
	// 평가서관리구분 조회
	public List<Map<String, Object>> selectEvlSeMngSeInq(Map<String, String> paramMap) throws Exception;
	
	// 상담원 - 평가 존재 여부 조회
	public List<String> selectEvlExisteYn() throws Exception;
	
	// 상담원 - 평가자 대상 여부 조회
	public String selectApraiTrgtYn(Map<String, Object> paramMap) throws Exception;
	
	// 평가 대상자 건수 조회
	public int selectEvlTrgtCnt(Map<String, String> paramMap) throws Exception;
	
	// 평가서 작성 대상 기본 조회
	public List<Map<String, Object>> selectBassEvlWrtList(Map<String, String> paramMap) throws Exception;
	
	// 평가서 작성 대상 상세 조회
	public Map<String, Object> selectDtlEvlWrtList(Map<String, Object> paramMap) throws Exception;
	
	// 수행 적절성평가표 기본정보 조회
	public Map<String, Object> selectRelevaEvlBassInfo(Map<String, String> map) throws Exception;
	// 수행 적절성평가표 목록 조회
	public List<Map<String, Object>> selectRelevaEvlList(Map<String, String> paramMap) throws Exception;
	// 평가관리 저장
	public int UpdateEvlMng(Map<String, String> map) throws Exception;	
	// 우수사례양식 저장
	public int UpdateExclncCaseMm(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectExclncModeInfo
	 * @return	   : String
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 양식관리에 등록된 우수사례 양식 조회
	 */
	public String selectExclncModeInfo() throws Exception;
	
	/**
	 * 우수사례 제출 자료 삭제
	 * @Method명   : exclncCaseMmDelete
	 * @param 	   : map
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 : 해당 월에 제출한 자료(우수사례)를 삭제 처리
	 */
	public int exclncCaseMmDelete(Map<String, String> map) throws Exception;
	
	// 수퍼비전양식 저장
	public int UpdateSuperVisionMm(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectSuperVisionModeInfo
	 * @return	   : String
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 양식관리에 등록된 수퍼비전 양식 조회
	 */
	public String selectSuperVisionModeInfo() throws Exception;
	
	/**
	 * 수퍼비전 제출 자료 삭제
	 * @Method명   : superVisionMmDelete
	 * @param 	   : map
	 * @return
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 해당 월에 제출한 자료(수퍼비전)를 삭제 처리
	 */
	public int superVisionMmDelete(Map<String, String> map) throws Exception;
	
//	// 평가관리목록 저장	
	public int UpdateEvlMngList(Map<String, String> dmOutcomeDetailMap) throws Exception;
	// 평가자 동료상담원 저장
	public int UpdateMngrApraiMngCoList(Map<String, String> dmOutcomeDetailMap) throws Exception;
	// 평가관리목록 저장(DEL_YN)	
	public int UpdateEvlMngListDelYn(Map<String, String> dmOutcomeDetailMap) throws Exception;
	// 평가점수관리 목록조회
	public List<Map<String, Object>> selectEvlScoreMngList(Map<String, String> paramMap) throws Exception;	
	// 확정정보 조회	
	public List<Map<String, Object>> selectCfmtnInfo(String sEvfoMngSn) throws Exception;
	// AYC270(평가점수관리)테이블 저장
	public int insertEvlScoreMng(Map<String, Object> map);
	// AYC270(평가점수관리)테이블 삭제여부	
	public int UpdateEvlScoreMng(Map<String, String> map) throws Exception;
	// 소속기관 목록조회
	public List<Map<String, Object>> selectOgdpInstList(Map<String, Object> paramMap) throws Exception;	
	// 우수사례관리 목록 조회
	public List<Map<String, Object>> selectExclncCaseMngList(Map<String, String> paramMap) throws Exception;	
	// 수퍼비전관리 목록 조회
	public List<Map<String, Object>> selectSuperVisionMngList(Map<String, String> paramMap) throws Exception;
	// 우수사례관리 상담자 조회
	public List<Map<String, Object>> selectExclncCaseConsttList(Map<String, String> paramMap) throws Exception;
	// 수퍼비전관리 상담자 조회
	public List<Map<String, Object>> selectSuperVisionConsttList(Map<String, String> paramMap) throws Exception;
	// 우수사례관리 상담자 저장
	public int insertExclncCaseConstt(Map<String, String> map) throws Exception;
	// 수퍼비전관리 상담자 저장
	public int insertSuperVisionConstt(Map<String, String> map) throws Exception;
	// 체크리스트그룹저장
	public int insertChklstGroup(Map<String, String> map) throws Exception;		
	// 체크리스트질의저장
	public int insertChklstQuest(Map<String, String> map) throws Exception;
	// 체크리스트관리일련번호(AYC250) 구하기
	public String selectSn250(Map<String, String> map) throws Exception;
	// 평가대상자선택수정(False)
	public int updateEvlTrprChcFalse(Map<String, String> map) throws Exception;
	// 평가대상자선정 일련번호
	public Integer selectEvlTrprChc() throws Exception;
	// 평가대상관리 수정
	public int updateEvlTrprChc(Map<String, String> map) throws Exception;
	// AYC240 MIN(DEL_YN) 조회
	public Map<String, Object> selectMinDelYn(Map<String, String> map) throws Exception;
	// DEL_YN 수정
	public int updateDelYn(Map<String, String> map) throws Exception;

//-------------------------------------------------------------------------------------------------------------------------------
// 관리자 - 상담원 역량관리 - 교육관리
//-------------------------------------------------------------------------------------------------------------------------------
	
	// 교육이력 및 수료증 출력 목록 조회
	public List<Map<String, Object>> selectEduHstrList(Map<String, String> paramMap) throws Exception;
	
	// 교육이수증출력번호 MAX값 조회
	public int selectMaxCtcplNo(String param) throws Exception;
	
	// 교육이수증출력번호 Update
	public int updateEduCtcplNo(Map<String, Object> paramMap) throws Exception;
	
	// 교육관리 조회
	public List<Map<String, Object>> selectEduMngList(Map<String, String> paramMap) throws Exception;
	
	// 사이버상담 교육인원 목록 조회
	public List<Map<String, Object>> selectCyberDscsnList() throws Exception;
	
	// 모바일상담 교육인원 목록조회
	public List<Map<String, Object>> selectMblaDscsnList() throws Exception;
	
	// 교육관리 - 교육 정보(AYC190) 저장
	public int insertEduInfo(Map<String, String> map) throws Exception;
	
	// 교육관리 - 교육 정보(AYC190) 수정
	public int updateEduInfo(Map<String, String> map) throws Exception;
	
	// 교육관리 - 교육 정보(AYC190) 삭제
	public int deleteEduInfo(Map<String, String> map) throws Exception;
	
	// 교육관리 - 교육 참석자 관리(AYC195) 삭제
	public int deleteEduAtndMng(Map<String, String> map) throws Exception;

	// 교육상세 - 교육정보 조회
	public Map<String, Object> selectEduMngBassInfo(Map<String, String> map) throws Exception;
	
	// 교육상세 - 사이버 미참석 목록
	public List<Map<String, Object>> selectCyberNonAtndList(Map<String, String> map) throws Exception;
		
	// 교육상세 - 사이버 참석 목록 
	public List<Map<String, Object>> selectCyberAtndList(Map<String, String> map) throws Exception;
	
	// 교육상세 - 모바일 미참석 목록 
	public List<Map<String, Object>> selectMblaNonAtndList(Map<String, String> map) throws Exception;
	
	// 교육상세 - 모바일 참석 목록 
	public List<Map<String, Object>> selectMblaAtndList(Map<String, String> map) throws Exception;
	
	// 교육관리 저장
	public int UpdateEduMng(Map<String, String> map) throws Exception;	
	
	// 교육관리목록 저장	
	public int UpdateEduMngSaveList(Map<String, String> map) throws Exception;	
	
	// 사이버상담 교육인원수정 목록조회
	public List<Map<String, Object>> selectCyberDscsnUpdateList(Map<String, String> paramMap) throws Exception;
	
	// 모바일상담 교육인원수정 목록 조회
	public List<Map<String, Object>> selectMblaDscsnUpdateList(Map<String, String> paramMap) throws Exception;
	
}
