package isry.couns.taskwksprt.taskwkandatdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("taskwkReprtsMapper")
public interface TaskwkReprtsMapper{
	
	/**
	 * @Method명   : selectTaskwkReprtsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 업무보고서 목록 조회
	 */
	List<Map<String, Object>> selectTaskwkReprtsList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectTaskwkReprtsDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 업무보고서 상세 조회
	 */
	List<Map<String, Object>> selectTaskwkReprtsDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectTaskwkReprtsDetailByMblaDscsn
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 : 업무보고서 상세 조회 (모바일상담 - 수정시 조회)
	 */
	List<Map<String, Object>> selectTaskwkReprtsDetailByMblaDscsn(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectLatenTimeByReprtsDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 : 업무보고서 상세 지각시간(분) 조회
	 */
	Integer selectLatenTimeByReprtsDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectOvrTimeByReprtsDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 업무보고서 상세 시간외근무시간(분) 조회
	 */
	Integer selectOvrTimeByReprtsDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectBreakTimeByReprtsDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 : 업무보고서 상세 휴게시간 조회
	 */
	List<Map<String, Object>> selectBreakTimeByReprtsDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChkListQuestList
	 * @param params
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 18. 
	 * @Method설명 : 체크리스트질의 목록 조회
	 */
	List<Map<String, Object>> selectChkListQuestList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectReprtsDetailPopupBaseInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 업무보고서 상세팝업 기본정보 조회
	 * <pre>
	 * 	- 근무시작일시 (WORK_BGNG_DT) 결과값 : 원본근무시작일시 (ORG_WORK_BGNG_DT) 2시간 전
	 * 	- 근무종료일시 (WORK_END_DT) 결과값 : 원본근무종료일시 (ORG_WORK_END_DT) 8시간 후
	 * </pre>
	 */
	Map<String, Object> selectReprtsDetailPopupBaseInfo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectOvtimeAplyHistbDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 시간외근무신청 목록 조회
	 */
	List<Map<String, Object>> selectOvtimeAplyHistbDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChttDscsnListByDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 채팅상담목록 조회
	 */
	List<Map<String, Object>> selectChttDscsnListByDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNtabrdDscsnListByDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 게시판상담목록 조회
	 */
	List<Map<String, Object>> selectNtabrdDscsnListByDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCmntNtabrdDscsnListByDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 게시판상담목록 조회 (댓글게시판)
	 */
	List<Map<String, Object>> selectCmntNtabrdDscsnListByDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectOutrcDscsnListByDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 30. 
	 * @Method설명 : 업무보고서 상세 > 아웃리치상담목록 조회
	 */
	List<Map<String, Object>> selectOutrcDscsnListByDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChttLogUserInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 : 채팅로그사용자 정보조회
	 */
	List<Map<String, Object>> selectChttLogUserInfo(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectBfeChttDscsnHistbInqDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 : 이전채팅상담내역 조회
	 */
	List<Map<String, Object>> selectBfeChttDscsnHistbInqDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChttDscsnHistbInqDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 : 채팅상담내역 조회
	 */
	List<Map<String, Object>> selectChttDscsnHistbInqDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNtabrdDscsnHistbInqDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 28. 
	 * @Method설명 : 게시판상담내역 조회
	 */
	List<Map<String, Object>> selectNtabrdDscsnHistbInqDetail(Map<String, Object> mapParam) throws Exception;

	/**
	 * 
	 * @Method명   : selectWorkDateTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 : 근무 시작시간, 종료시간 구하기
	 */
	Map<String, Object> selectWorkDateTime(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectEvalutaionOpt
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 : 자기평가표작성 옵션 조회
	 */
	Map<String, Object> selectEvalutaionOpt(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectChatMemo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 : 채팅내역의 메모 가져오기
	 */
	List<Map<String, Object>> selectChatMemo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectMobileCrisisNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 : 모바일 위기 및 연계 건수, 사후관리 카운팅
	 */
	Map<String, Object> selectMobileCrisisNocs(Map<String, Object> mapParam) throws Exception;

	/**
	 * 
	 * @Method명   : selectChttDscsnHistbList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 : 업무보고서 등록 > 채팅상담목록
	 */
	List<Map<String, Object>> selectChttDscsnHistbList(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectAYC260List(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNtabrdDscsnHistList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectExistTaskwkReprts
	 * @param taskSchdlSn
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 업무보고서 중복체크
	 * <pre>
	 * 	- 기존에 같은 날짜에 등록이 된게 있는지 체크 
	 * </pre>
	 */
	Integer selectExistTaskwkReprts(String taskSchdlSn) throws Exception;
	
	/**
	 * @Method명   : selectWorkDateTimeByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 : 근무 시작시간, 종료시간 구하기 (등록)
	 */
	Map<String, Object> selectWorkDateTimeByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectOutrcInfoByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 : 사이버아웃리치 정보 조회 (등록)
	 */
	Map<String, Object> selectOutrcInfoByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChatOpenTimeByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 채팅방 개설시간 조회 (등록)
	 */
	String selectChatOpenTimeByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChatDscsnCntByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 채팅상담 건수 조회 (등록)
	 * <pre>
	 * 	- 채팅시작일시 (CHTT_BGNG_DT) 조회조건: 근무 시작 30분 전부터 근무 종료 후 1시간까지
	 * </pre>
	 */
	Integer selectChatDscsnCntByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNtbrdDscsnCntsByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 상담게시판 건수 조회 (등록)
	 * <pre>
	 * 	- 작성일시 (FRST_REG_DT) 조회조건: 근무 시작 2시간 전부터 근무 종료 후 8시간까지
	 * </pre> 
	 */
	Map<String, Object> selectNtbrdDscsnCntsByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChatDscsnCrisisByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 채팅 위기내역 조회 (등록)
	 * <pre>
	 * 	- 채팅시작일시 (CHTT_BGNG_DT) 조회조건: 근무 시작 30분 전부터 근무 종료 후 1시간까지
	 * </pre>
	 */
	Map<String, Object> selectChatDscsnCrisisByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNtbrdDscsnCrisisByReg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 상담게시판 유형별 등록된 위기내역 조회 (등록)
	 * <pre>
	 * 	- 작성일시 (FRST_REG_DT) 조회조건: 근무 시작 2시간 전부터 근무 종료 후 1시간까지
	 * </pre>
	 */
	Map<String, Object> selectNtbrdDscsnCrisisByReg(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCnsltntCommuteInfoCnt
	 * @param 	   : mapParam
	 * @return	   : Integer
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 6. 
	 * @Method설명 : 상담원출퇴근관리(AYC495) 업무보고서 등록 근무일자에 대한 데이터 확인
	 */
	Integer selectCnsltntCommuteInfoCnt(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectFrstAtenDbInfo
	 * @param 	   : mapParam
	 * @return	   : String
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : AYB202(깃플챗상담원상태관리)에서 상담상태구분코드 = '10'인 시간 순으로 첫번째 데이터 조회
	 * @출처	   : counsDashboardMapper - selectAtndb (오늘출근 시간 조회)
	 */
	String selectFrstAtenDbInfo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateCnsltntCommute
	 * @param 	   : mapParam
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : AYC495(상담원출퇴근관리) 출근일시 등록 및 수정 처리
	 */
	int updateCnsltntCommute(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertTaskwkReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 14. 
	 * @Method설명 : 업무보고서 등록처리
	 */
	int insertTaskwkReprts(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateTaskwkReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 1. 
	 * @Method설명 : 업무보고서 수정처리
	 */
	int updateTaskwkReprts(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteTaskwkReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 26.
	 * @Method설명 : 업무보고서 삭제
	 */
	int deleteTaskwkReprts(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateTaskwkReprtsByMobile
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 : 업무보고서 수정처리 (모바일상담 및 시간이외근무신청)
	 */
	int updateTaskwkReprtsByMobile(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateOvtimeAplyHistbDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 시간외근무신청 수정
	 */
	int updateOvtimeAplyHistbDetail(Map<String, String> mapParam) throws Exception;
}
