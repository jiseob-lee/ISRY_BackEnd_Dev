/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprdtlinfomng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : AplcntTrprMngMapper.java
 * @프로그램 설명 : 입교심사외 관리자페이지 관련 매퍼
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 9. 16.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Mapper("csemdMngrPageAplcntTrprDtlInfoMngMapper")
public interface AplcntTrprDtlInfoMngMapper {

	/**
	 * @Method명 : selectPtcptReqstdAplcntPop
	 * @param dtlMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectPtcptReqstdAplcntPop(Map<String, Object> dtlMap);

	// 사례대상자목록(입교접수정보, 입소접수정보(드림))
	public List<Map<String, Object>> selectMainList(Map<String, String> map) throws Exception;

	// 사례대상자목록(의뢰정보)
	public List<Map<String, Object>> selectRqcpInfoList(Map<String, String> map) throws Exception;
	
	// 사례대상자목록(문제행동발생보고서)
	public List<Map<String, Object>> selectProbmList(Map<String, String> map) throws Exception;

	// 생활동콤보
	public List<Map<String, Object>> selectAltmntGrpCmb() throws Exception;

	// 안정도관찰지, 문제행동발생보고서 대상자명 콤보
	public List<Map<String, Object>> selectEnstTrprList(Map<String, Object> map) throws Exception;

	// 안정도관찰지 목록조회
	public List<Map<String, Object>> selectDeofstObservList(Map<String, String> map) throws Exception;

	// 안정도 관찰지 상세조회
	public List<Map<String, Object>> selectDeofstObservDtl(Map<String, String> paraMap) throws Exception;

	// 안정도 관찰지 확인
	public int selectDeofstObservChk(Map<String, String> paramMap) throws Exception;

	// 안정도관찰지 저장
	public void insertDeofstObserv(Map<String, String> paraMap) throws Exception;

	// 안정도관찰지 수정
	public void updateDeofstObserv(Map<String, String> paraMap) throws Exception;

	// 문제행동발생 보고서 확인
	public int selectPromReportChk(Map<String, String> paraMap) throws Exception;
	
	// 문제행동발생 보고서 대상자명콤보를 통한 기본정보 조회
	public List<Map<String, Object>> selectProblmTrprInfo(Map<String, Object> requestMap) throws Exception;

	// 문제행동발생 보고서 저장
	public void insertProbmRowList(Map<String, String> paraMap) throws Exception;

	// 문제행동발생 보고서 수정
	public void updateProbmRowList(Map<String, String> paraMap) throws Exception;

	// 문제행동발생보고서 상세조회
	public List<Map<String, Object>> selectDtlProbInfo(Map<String, Object> resultMap) throws Exception;

	// 문제행동발생보고서 관리자 확인
	public void saveDtlProbInfoConfirm(Map<String, String> resultMap) throws Exception;

	// 심리평가보고서 목록조회
	public List<Map<String, Object>> selectPsycholRepoList(Map<String, String> map) throws Exception;

	// 심리평가보고서 대상자정보조회
	public List<Map<String, Object>> selectDtlPsycholInfo(Map<String, String> requestMap) throws Exception;

	// 심리평가보고서 개별심리검사결과
	public List<Map<String, Object>> selectDtlPsycholList(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : selectSrvyListPop
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSrvyListPop(Map<String, Object> dtlMap);

	/**
	 * @Method명   : selectdsFamInfoImsy
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectdsFamInfoImsy(Map<String, Object> dtlMap);

	/**
	 * @Method명   : selectTrprAtfino
	 * @param dmParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 : 대상자사진첨부파일번호 조회
	 */
	Map<String, String> selectTrprAtfino(Map<String, String> dmParam) throws Exception;

	/**
	 * @Method명   : updatePtcptReqstdAplcntPop
	 * @param map
	 * @return 
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 디딤 참가신청서 수정
	 */
	int updatePtcptReqstdAplcntPop(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : insertPtcptReqstdAplcntPopHstr
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void insertPtcptReqstdAplcntPopHstr(Map<String, String> map) throws Exception;
}
