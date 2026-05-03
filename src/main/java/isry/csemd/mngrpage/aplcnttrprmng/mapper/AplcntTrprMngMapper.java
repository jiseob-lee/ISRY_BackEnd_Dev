/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : AplcntTrprMngMapper.java
 * @프로그램 설명 : 신청대상자 관리[관리자페이지] Mapper Interface - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Mapper("aplcntTrprMngMapper__admin")
public interface AplcntTrprMngMapper {

	public List<Map<String, Object>> selectAplyList(Map<String, String> map) throws Exception;

	public void updatePrgrsStts(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : updateAplyTrprDtlInfo
	 * @param saveMap
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :신청자 정보(AFA100) : update
	 */
	public void updateAplyTrprDtlInfo(Map<String, String> saveMap);

	/**
	 * @Method명 : insertAplyRcptHstr
	 * @param saveMap
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :신청접수이력(AFA101) : insert
	 */
	public void insertAplyRcptHstr(Map<String, String> saveMap);

	/**
	 * @Method명 : selectAplyTrprDtlInfo
	 * @param reqMap
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :신청 대상자 정보 상세
	 */
	public Map<String, String> selectAplyTrprDtlInfo(Map<String, String> reqMap);

	/**
	 * @Method명 : selectQustnbScoreEvlSttsInfo
	 * @param reqMap
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :### 입교 신청, 접수 설문지 점수 입력 폼 > 평가 > 라디오 버튼 활성/비활성 용 선정, 미선정, 반송 상태 값
	 */
	public Map<String, Object> selectQustnbScoreEvlSttsInfo(Map<String, String> reqMap);

	/**
	 * @Method명 : updateAplyCnMdfcnPsbltyYn
	 * @param updateMap
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :신청자 정보(AFA100) : update 수정권한(부여,회수) 업데이트
	 */
	public void updateAplyCnMdfcnPsbltyYn(Map<String, String> updateMap);

	/**
	 * @Method명 : chkAplyRcptSrngPrgrsStts
	 * @param saveMap
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :APLY_RCPT_SRNG_SE_CD(신청접수심사구분코드) Key > 존재 여부 조회
	 */
	public int chkAplyRcptSrngPrgrsStts(Map<String, String> saveMap);

	/**
	 * @Method명 : insertAplyRcptSrngPrgrsSttsInfoHstr
	 * @param saveMap
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :단계별 진행 상태 이력 insert
	 */
	public void insertAplyRcptSrngPrgrsSttsInfoHstr(Map<String, String> saveMap);

	/**
	 * @Method명 : updateAplyRcptSrngPrgrsSttsInfoHstr
	 * @param saveMap
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :단계별 진행 상태 이력 update
	 */
	public void updateAplyRcptSrngPrgrsSttsInfoHstr(Map<String, String> saveMap);

	/**
	 * @Method명 : selectTrprProbmSttsHistb
	 * @param paramMap
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 20.
	 * @Method설명 :대상자문제상태내역 > AFA120 체크
	 */
	int selectTrprProbmSttsHistb(Map<String, String> paramMap);

	/**
	 * @Method명 : selectSrvyRspnsList
	 * @param userParam
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSrvyRspnsList(Map<String, Object> userParam);

	/**
	 * @Method명 : insertQustnbMmsContentsInfo
	 * @param map
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 24.
	 * @Method설명 :
	 */
	void insertQustnbMmsContentsInfo(Map<String, String> map);

	/**
	 * @Method명 : insertQustnbMsgData
	 * @param map
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 24.
	 * @Method설명 :
	 */
	void insertQustnbMsgData(Map<String, String> map);

	/**
	 * @Method명 : insertQustnbNtcnSnsSndng
	 * @param map
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 24.
	 * @Method설명 :
	 */
	void insertQustnbNtcnSnsSndng(Map<String, String> map);

	/**
	 * @Method명 : insertQustnbSndngHstr
	 * @param param
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 24.
	 * @Method설명 :
	 */
	void insertQustnbSndngHstr(Map<String, Object> param);

	/**
	 * @Method명 : selectAplyPapersCount
	 * @param paramMap
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 21.
	 * @Method설명 : 신청서류첨부파일 갯수 리턴
	 */
	Map<String, Object> selectAplyPapersCount(Map<String, String> paramMap);

	/**
	 * @Method명 : selectPapersScrennList
	 * @param paraMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 2.
	 * @Method설명 : 서류스크리닝 조회
	 */
	public List<Map<String, Object>> selectPapersScrennList(Map<String, String> paraMap);

	/**
	 * @Method명 : updateMultiFileUpload
	 * @param paramMap
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 : 멀티파일 업로드
	 */
	public void updateMultiFileUpload(Map<String, String> paramMap);

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
	 * @param mapParam
	 * @return
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 :
	 */
	Map<String, String> getIntrvwInfo(Map<String, Object> mapParam);

	/**
	 * @Method명 : updateCaseStts
	 * @param singleValueMap
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 12. 14.
	 * @Method설명 : 진행단계에 따른 사례관리구분코드/사례대상자미신청사유구분코드 업데이트
	 */
	public void updateCaseStts(Map<String, String> singleValueMap);

	/**
	 * @Method명 : insertTrprInqHistory
	 * @param updateMap
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 12. 15.
	 * @Method설명 :
	 */
	public void insertTrprInqHistory(Map<String, String> updateMap);

	/**
	 * @Method명   : selectQustnbUntTaskwkSeCd
	 * @param param2
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 :
	 */
	public String selectQustnbUntTaskwkSeCd(Map<String, Object> param2);

	/**
	 * @Method명   : selectRprsTelno
	 * @param dmSearchParamMap
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 :
	 */
	public String selectRprsTelno(Map<String, String> dmSearchParamMap);
	
	public String selectCaseHstrCnt(Map<String, String> param) throws Exception;

	/**
	 * @Method명   : insertQustnbTrprInfo
	 * @param param
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 6. 19. 
	 * @Method설명 :
	 */
	public void insertQustnbTrprInfo(Map<String, String> param);

}
