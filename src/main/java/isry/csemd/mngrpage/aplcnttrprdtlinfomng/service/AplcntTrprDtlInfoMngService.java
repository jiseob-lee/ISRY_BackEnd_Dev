/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprdtlinfomng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : AplcntTrprDtlInfoMngService.java
 * @프로그램 설명 : - -
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 9. 16.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */

public interface AplcntTrprDtlInfoMngService {

	/**
	 * @Method명   : selectPtcptReqstdAplcntPop
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectPtcptReqstdAplcntPop(HttpServletRequest request, DataRequest dataRequest);
	
	// 사례대상자 목록(입교접수정보)
	public List<Map<String, Object>> selectMainList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	// 사례대상자 목록(의뢰정보)
	public List<Map<String, Object>> selectRqcpInfoList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;
	
	// 안정도관찰지 목록 조회
	public List<Map<String, Object>> selectDeofstObservList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	// 문제행동발생보고서 목록 조회
	public List<Map<String, Object>> selectProbmList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;
		
	// 대상자명콤보_안정도관찰지, 문제행동발생보고서
	public void selectEnstTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 안정도관찰지 등록 및 수정
	public void saveDeofstObserv(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 안정도관찰지 상세조회
	public List<Map<String, Object>> selectDeofstObservDtl(Map<String, String> map)
			throws Exception;

	// 문제행동발생보고서 등록 및 수정
	public void saveProbm(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 문제행동발생보고서 상세조회
	public List<Map<String, Object>> selectDtlProbInfo(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	// 문제행동발생보고서 관리자확인
	public void saveDtlProbInfoConfirm(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 심리평가보고서 목록
	public List<Map<String, Object>> selectPsycholRepoList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	// 심리평가보고서 기본정보
	public List<Map<String, Object>> selectDtlPsycholInfo(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;
	
	// 심리평가보고서 개별심리검사 결과
	public List<Map<String, Object>> selectDtlPsycholList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	// 가족사항 조회
	List<Map<String, String>> selectdsFamInfoImsy(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : selectTrprAtfino
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 3. 30. 
	 * @Method설명 : 대상자사진첨부파일번호 조회
	 */
	Map<String, String> selectTrprAtfino(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
