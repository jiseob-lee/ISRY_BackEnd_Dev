/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LinkMohwSrvcRqstMapper.java
 * @프로그램 설명 : 복지부 연계서비스 의뢰
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 29. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("linkMohwSrvcRqstMapper")
public interface LinkMohwSrvcRqstMapper {
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 : 복지부 서비스의뢰접수 목록 
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptList(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수정보 조회
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfoCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 : 복지부 서비스의뢰접수결과 정보 건수
	 */
	public Integer selectMohwSrvcRqstRcptInfoCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTrprInfoNo
	 * @param sEsbSeq	(연계시퀀스)
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 : 서비스접수결과 연계시퀀스번호로 대상자번호 조회(대상자 연계추가데이터값수정시)
	 */
	public Map<String, String> selectTrprInfoNo(String sEsbSeq) throws Exception;	
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfoResultInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 : 복지부 서비스의뢰접수결과 정보 조회
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfoResultInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfoResultList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수정보결과 목록
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfoResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateMohwSrvcRqstRcpt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수 수정
	 */
	public Integer updateMohwSrvcRqstRcpt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertMohwSrvcRqstRcptResult
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수결과 등록
	 */
	public Integer insertMohwSrvcRqstRcptResult(Map<String, String> paramMap) throws Exception;
	

	
	/**
	 * @Method명   : updateMohwSrvcRqstRcptResult
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 : 복지부 서비스의뢰접수결과 수정
	 */
	public Integer updateMohwSrvcRqstRcptResult(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 목록
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstDmndList (Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청정보 조회
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstDmndInfo (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndInfoResultList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청결과 목록
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstDmndInfoResultList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertMohwSrvcRqstDmnd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 등록
	 */
	public Integer insertMohwSrvcRqstDmnd(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateMohwSrvcRqstDmnd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 수정
	 */
	public Integer updateMohwSrvcRqstDmnd(Map<String, String> paramMap) throws Exception;	

	
	/**
	 * @Method명   : searchLinkTrprInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 복지부 서비스의뢰 접수 후 연계상태 대상자정보 조회
	 */
	public List<Map<String, String>> searchLinkTrprInfo() throws Exception;
	
	/**
	 * @Method명   : searchLinkTrprCaseInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 12. 
	 * @Method설명 : 복지부 서비스의뢰접수 연계상태 'S' 사례등록 확인 
	 */
	public Map<String, Object> searchLinkTrprCaseInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertSEB110DataTEST
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 6. 
	 * @Method설명 : 사례관리이력 등록
	 */
	public Integer insertSEB110DataTEST(Map<String, String> paramMap) throws Exception;	
	
	/**
	 * @Method명   : updateLinkTrprCaseMngSeCd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 12. 
	 * @Method설명 : 대상자 사례관리구분코드 사례대사자선정 수정
	 */
	public Integer updateLinkTrprCaseMngSeCd(Map<String, String> paramMap) throws Exception;		
	
	

}
