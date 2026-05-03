/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : AplcntTrprMngService.java
 * @프로그램 설명 : 신청대상자 관리[관리자페이지] Service interface - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
public interface AplcntTrprMngService {

	public void selectAplyList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void savePrgrsSttsListUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : saveAplyTrprDtlInfo
	 * @param request
	 * @param dataRequest
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 6.
	 * @Method설명 :신청 대상자 정보 등록, 수정
	 */
	void saveAplyTrprDtlInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectAplyTrprDtlInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 6.
	 * @Method설명 :신청 대상자 정보 상세
	 */
	Map<String, String> selectAplyTrprDtlInfo(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : updateAplyCnMdfcnPsbltyYn
	 * @param request
	 * @param dataRequest
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 13.
	 * @Method설명 :수정권한 (부여, 회수)
	 */
	void updateAplyCnMdfcnPsbltyYn(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : prgrsSttsStageUpdate
	 * @param request
	 * @param dataRequest
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 13.
	 * @Method설명 :단계별 진행 상태 업데이트
	 */
	void prgrsSttsStageUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectTrprProbmSttsHistb
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 20.
	 * @Method설명 :대상자문제상태내역 > AFA120 체크
	 */
	int selectTrprProbmSttsHistb(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectAplyTrprDtlInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 :설문응답여부
	 */
	List<Map<String, Object>> selectSrvyRspns(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : reSndngQustnb
	 * @param request
	 * @param dataRequest
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 :
	 */
	void reSndngQustnb(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectAplyPapersCount
	 * @param request
	 * @param dataRequest
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 :
	 */
	void selectAplyPapersCount(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectPapersScrennList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 2.
	 * @Method설명 : 서류스크리닝 조회
	 */
	public List<Map<String, Object>> selectPapersScrennList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명 : updateMultiFileUpload
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 : 멀티파일 업로드
	 */
	public void updateMultiFileUpload(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명 : updateTrprPhotoAtfino
	 * @param requestMap
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 : 대상자사진첨부파일번호 저장
	 */
	public void updateTrprPhotoAtfino(Map<String, String> requestMap);

	/**
	 * @Method명 : getIntrvwInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 :
	 */
	Map<String, String> getIntrvwInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
