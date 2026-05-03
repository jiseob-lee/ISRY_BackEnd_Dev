/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SurvshtMmnMapper.java
 * @프로그램 설명 : 설문지 작성을 관리하는 Mapper
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 :
 * @수정일 :
 * @수정내용 : - -
 */
@Mapper("survshtMmnMapper")
public interface SurvshtMmnMapper {

	/**
	 * @Method명 : selectSurvshtListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 목록 totalCount조회
	 */
	public Integer selectSurvshtListTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectSurvshtList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 목록 조회
	 */
	public List<Map<String, Object>> selectSurvshtList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : insertSurvsht
	 * @param dmSaveMap
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송 (문서수발신내용 SBA200)
	 */
	public int insertSurvsht(Map<String, Object> dmSaveMap) throws Exception;

	/**
	 * @Method명 : insertSurvsht2
	 * @param dmSaveMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송 (문서수발신 SBA210)
	 */
	public void insertSurvsht2(Map<String, Object> dmSaveMap) throws Exception;

	/**
	 * @Method명 : deleteSurvsht
	 * @param mapDel
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(삭제)
	 */
	public void deleteSurvsht(Map<String, String> mapDel) throws Exception;

	/**
	 * @Method명 : updateSurvsht
	 * @param INO_DOC_ESNTAL_NO
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	public void updateSurvsht(Map<String, Object> dmUpdateMap) throws Exception;

	/**
	 * @Method명 : selectQesitmListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 목록 totalCount조회
	 */
	public Integer selectQesitmListTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectQesitmList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 목록 조회
	 */
	public List<Map<String, Object>> selectQesitmList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectQesitm
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항내용 조회
	 */
	public List<Map<String, Object>> selectQesitm(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectQesitmExmplList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항보기 목록 조회
	 */
	public List<Map<String, Object>> selectQesitmExmplList(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectSysSeCd
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 관리번호 조회
	 */
	public String selectSysSeCd(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : insertQesitm
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문 문항 등록
	 */
	public void insertQesitm(Map<String, String> dmMap) throws Exception;

	/**
	 * @Method명 : updateQesitm
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문 문항 수정
	 */
	public void updateQesitm(Map<String, String> dmMap) throws Exception;

	/**
	 * @Method명 : insertQesitmExmpl
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항 보기 등록
	 */
	public void insertQesitmExmpl(Map<String, String> dmMap) throws Exception;

	/**
	 * @Method명 : updateQesitmExmpl
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항 보기 수정
	 */
	public void updateQesitmExmpl(Map<String, String> dmMap) throws Exception;

	/**
	 * @Method명 : updateQesitmExmpl
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항 보기 수정
	 */
	public void deleteQesitmExmpl(Map<String, String> dmMap) throws Exception;

	/**
	 * @Method명 : selectQesitmMngUseCnt
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : Song. Tae.soo
	 * @작성일 : 2022. 5. 30.
	 * @Method설명 : 문항 사용 여부 체크
	 */
	public int selectQesitmMngUseCnt(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectQesitmMngUseCnt
	 * @param dmMap
	 * @throws Exception
	 * @작성자 : Song. Tae.soo
	 * @작성일 : 2022. 5. 30.
	 * @Method설명 : 문항 사용여부 삭제 처리
	 */
	public void deleteQesitmMng(Map<String, Object> map) throws Exception;

	public void insertQustnbMng(Map<String, Object> map) throws Exception;

	public void insertQustnbRelmMng(Map<String, Object> map) throws Exception;

	public void insertQustnbMngList(Map<String, Object> map) throws Exception;

	public int selectQesitmQustnbMngListTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQesitmQustnbMngList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQesitmQustnbCheckList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQuestnbRelmList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQuestnbRelmBaseList(Map<String, Object> map) throws Exception;

	public int selectQuestnbRelmCnt(Map<String, Object> map) throws Exception;

	public void deleteQuestnbRelmList(Map<String, Object> map) throws Exception;

	public void insertQustnbGrdngList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectResultCrtrList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectResultQustnbRelm(Map<String, Object> map) throws Exception;

	public void insertResultCrtrList(Map<String, String> map) throws Exception;

	public Map<String, Object> selectQustnbMngInfo(Map<String, Object> map) throws Exception;

	public int selectQesitmCnt(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectPreSurvshtList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectPreSurvshtDtlList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectPreSurvshtDtlRelmList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSrvyTrprList() throws Exception;

	public void updateQustnbMng(Map<String, Object> map) throws Exception;

	public void deleteQustnbRelmMng(Map<String, Object> map) throws Exception;

	public void deleteQustnbMng(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectTrprInfoDtl(Map<String, String> map) throws Exception;

	public void insertCaseMngTrprInfo(Map<String, Object> map) throws Exception;

	public void deleteCaseMngTrprInfo(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectStatusQustnbMng(Map<String, Object> map) throws Exception;

	public void processStatusQustnbMng(Map<String, Object> map) throws Exception;

	public void deleteResultCrtr(Map<String, Object> map) throws Exception;

	public Integer selectSurvshtTmptListTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSurvshtTmptList(Map<String, Object> map) throws Exception;

	public Integer selectQesitmQustnbMngListTmptTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQesitmQustnbTmptMngList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQesitmQustnbTmptCheckList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectResultCrtrTmptList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQuestnbRelmBaseTmptList(Map<String, Object> map) throws Exception;

	public void insertQustnbTmptMng(Map<String, Object> map) throws Exception;

	public void insertQustnbRelmTmptMng(Map<String, Object> map) throws Exception;

	public void insertQustnbMngTmptList(Map<String, Object> map) throws Exception;

	public int selectQuestnbRelmTmptCnt(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectQuestnbRelmTmptList(Map<String, Object> map) throws Exception;

	public void deleteQuestnbRelmTmptList(Map<String, Object> map) throws Exception;

	public void insertQustnbGrdngTmptList(Map<String, Object> map) throws Exception;

	public void deleteResultCrtrTmpt(Map<String, Object> map) throws Exception;

	public void insertResultCrtrTmptList(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectResultQustnbRelmTmpt(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectQustnbMngTmptInfo(Map<String, Object> map) throws Exception;

	public int selectQesitmTmptCnt(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectPreSurvshtTmptList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectPreSurvshtDtlTmptList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectPreSurvshtDtlTmptRelmList(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectQustnbTmptMngNoInfo(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSurvshtQesitmTmptList(Map<String, Object> map) throws Exception;

	public void insertResultCrtrObjectList(Map<String, Object> map) throws Exception;

	public void updateQustnbTmptMng(Map<String, Object> map) throws Exception;

	public void deleteQustnbRelmTmptMng(Map<String, Object> map) throws Exception;

	public void deleteQustnbTmptMng(Map<String, Object> map) throws Exception;

	public void updateQustnbTmptStatusMng(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSurvshtTmptCpList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectMySurvshtList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSurvshtTmptRelmMarkList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectDidimGrdngRelmList(Map<String, Object> map) throws Exception;

	public void deleteDidimGrdngRelmMark(Map<String, Object> map) throws Exception;

	public void insertDidimGrdngRelmMark(Map<String, String> map) throws Exception;

	public Map<String, String> getQustnbRelmData(Map<String, String> map) throws Exception;

	public void insertQesitmSrvyRspns(Map<String, Object> map) throws Exception;

	public void insertSrvyRspnsMngInfo(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectRelmNomfrmList(Map<String, Object> map) throws Exception;

	public void insertGrdngResult(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectRelmGrdngCrtrList(Map<String, Object> map) throws Exception;

	public String selectSurvshtSrvyRspnsMngNo(Map<String, Object> map) throws Exception;

	public List<Map<String, String>> selectSurvshtSrvyRspnsMngNo2(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectSurvshtSrvyRspnsMngInfo(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectChkSurvshtList(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectSrvyDtlData(Map<String, Object> map) throws Exception;

	public void saveQesitmSrvyRspns(Map<String, Object> map) throws Exception; 	//항목별응답내용  -수정적용

	public void saveSrvyRspnsMngInfo(Map<String, Object> map) throws Exception; //설문응답 - 수정적용

	public void saveGrdngResult(Map<String, Object> map) throws Exception;  //채점결과 - 수정적용

	public int selectUseTmptCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectUntTaskwkSeCd
	 * @param param
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 2. 23.
	 * @Method설명 :
	 */
	public String selectUntTaskwkSeCd(Map<String, Object> param) throws Exception;

	/**
	 * @Method명   : updateSrvyPrgrs
	 * @param param
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 3. 13.
	 * @Method설명 : [SBB100]설문진행상태구분코드 update
	 */
	public int updateSrvyPrgrs(Map<String, Object> param) throws Exception;

	public void insertQustnbMmsContentsInfo(Map<String, Object> param) throws Exception;

	public void insertQustnbMsgData(Map<String, Object> param) throws Exception;

	public String selectRprsTelno(Map<String, String> dmSearchParamMap) throws Exception;

	public Map<String, String> getQustnbTmptNoInfo(Map<String, String> map) throws Exception;
}
