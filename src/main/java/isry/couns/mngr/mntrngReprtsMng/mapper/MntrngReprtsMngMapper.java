package isry.couns.mngr.mntrngReprtsMng.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MntrngReprtsMngMapper.java
 * @프로그램 설명 : 모니터링 보고서
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 28. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 28. 
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mntrngReprtsMngMapper")
public interface MntrngReprtsMngMapper{
	
	// 모니터링 보고서 목록 조회
	public List<Map<String, Object>> selectMntrngReprtsList(Map<String, Object> mapParam) throws Exception;

	// 사이버상담 목록조회
	public List<Map<String, Object>> selectCyberDscsnList(Map<String, String> paramMap) throws Exception;
	// 사이버아웃리치 목록조회
	public List<Map<String, Object>> selectOutreachList(Map<String, String> paramMap) throws Exception;
	// 모바일상담 목록조회
	public List<Map<String, Object>> selectMobileList(Map<String, String> paramMap) throws Exception;

	// 위기및연계 게시글
	public List<Map<String, Object>> selectCrisisLinkBbsctt(Map<String, String> paramMap) throws Exception;
	// 위기및연계 유형별건수
	public List<Map<String, Object>> selectCrisisLinkTypeNocs(Map<String, String> paramMap) throws Exception;
	
	// 업무보고서색인일련번호 목록조회
	public List<Map<String, String>> selectTASKWKREPRTSINDEXSNList(Map<String, String> map) throws Exception;
	// 업무보고서색인일련번호
	public int UpdateTASKWKREPRTSINDEXSN(Map<String, String> map) throws Exception;

	//모니터링 보고서 목록 날짜 중복 조회
	public List<Map<String, Object>> selectWorkAltMntCrtYmdCheckList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectMntrngReprtsDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 : 모니터링보고서 상세 조회
	 */
	List<Map<String, Object>> selectMntrngReprtsDetail(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectOpenChroNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 오픈채팅방 답글 및 고민글 등록 건수 조회 (모바일상담)
	 */
	Map<String, Object> selectOpenChroNocs(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectDclzMngSttsInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 근태관리 목록 조회
	 */
	List<Map<String, Object>> selectDclzMngSttsList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCrisisLinkNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 위기 및 연계 건수 조회 (통합)
	 */
	Integer selectCrisisLinkNocs(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectThptyPvsnHistbNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 제3자 제공내역 건수 조회 (모바일상담)
	 */
	Map<String, BigDecimal> selectThptyPvsnHistbNocs(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectTaskwkReprtsWrtrList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 업무보고서 작성자 목록
	 */
	List<Map<String, Object>> selectTaskwkReprtsWrtrList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCyberDscsnEpilgNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 26. 
	 * @Method설명 : 사이버상담후기 건수 조회
	 */
	Integer selectCyberDscsnEpilgNocs(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectVocNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 26. 
	 * @Method설명 : 고객의 소리 건수 조회
	 */
	Integer selectVocNocs(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCnsltntWorkSchdlList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 상담사 근무일정 및 휴가 조회
	 */
	List<Map<String, Object>> selectCnsltntWorkSchdlList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectMntrngReprtsDataList
	 * @param 	   : mapParam
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 일간 모니터링 보고서 데이터 및 상담사 근무일정 및 휴가 목록 조회
	 */
	List<Map<String, Object>> selectMntrngReprtsDataList(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNotExistsByOutrcCnTnLog
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 접속이력여부 확인 (사이버아웃리치)
	 * <pre>
	 * 	- 접속이력 정보를 통한 분기처리 (AS-IS: work_in ??)
	 * </pre>
	 */
	String selectNotExistsByOutrcCnTnLog(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChatOpenTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 채팅 오픈시간 조회
	 */
	Map<String, Object> selectChatOpenTime(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChatCloseTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 채팅 종료시간 조회
	 */
	Map<String, Object> selectChatCloseTime(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectChatUnestaTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 채팅 미개설시각 조회
	 * <pre>
	 * 	- 채팅방 들어온 시간과 채팅방 나간 시간 사이의(근무시간에 가장 근접한 시간) 근무시간에서<BR>
	 * 	채팅방을 유지한 시간을 뺀 나머지 시간 구하기
	 * </pre>
	 */
	Map<String, Object> selectChatUnestaTime(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectRestTime
	 * @param 	   : mapParam
	 * @return	   : Map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 23. 
	 * @Method설명 : 휴게시간 조회
	 */
	Map<String, Object> selectRestTime(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCnsltntLeaveTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 상담사 퇴근시간 조회
	 * <pre>
	 * 	- AS-IS 에서는 wrdLeaveTime (채팅미개설시간??) 값을 시:분 (HH24:MI) 형식으로 등록했으나,<BR>
	 * 	AS-IS 화면에서 표시하는 부분 없음!<BR>
	 * 	- TO-BE 에서는 LVFFC_PRCS_DT (퇴근처리일시) 로 대체하여 DATE 형으로 등록처리
	 * </pre>
	 */
	Map<String, Object> selectCnsltntLeaveTime(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertMntrngReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 모니터링 보고서 추가
	 */
	int insertMntrngReprts(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertMntrngReprtsData
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 모니터링 보고서 데이터 추가
	 */
	int insertMntrngReprtsData(Map<String, Object> mapParam) throws Exception;
	
	
	/**
	 * @Method명   : deleteMntrngReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 : 모니터링 보고서 삭제
	 */
	int deleteMntrngReprts(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteMntrngReprtsData
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 : 모니터링 보고서 데이터 삭제
	 */
	int deleteMntrngReprtsData(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateMntrngReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 26. 
	 * @Method설명 : 모니터링 보고서 수정
	 */
	int updateMntrngReprts(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateMntrngReprtsData
	 * @param 	   : mapParam
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 모니터링 보고서 데이터 수정
	 */
	int updateMntrngReprtsData(Map<String, Object> mapParam) throws Exception;
}
