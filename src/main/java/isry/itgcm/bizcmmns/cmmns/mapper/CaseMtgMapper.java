/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CaseMtgMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 13. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("caseMtgMapper")
public interface CaseMtgMapper {
	
	/**
	 * @Method명   : selectCaseMtgList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 목록 조회
	 */
	public List<Map<String, Object>> selectCaseMtgList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseMtgAtdrnlList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 참석자 목록 조회
	 */
	public List<Map<String, Object>> selectCaseMtgAtdrnlList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseMtgAtdrnlList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 담당자 목록 조회
	 */
	public List<Map<String, Object>> selectCaseMtgPiclList (Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : insertCaseMtg
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 등록
	 */
	public Integer insertCaseMtg (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertCaseMtgHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의이력 등록
	 */
	public Integer insertCaseMtgHistory	(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateCaseMtg
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 수정
	 */
	public Integer updateCaseMtg (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteCaseMtg
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 삭제
	 */
	public Integer deleteCaseMtg (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertCaseMtgAtdrn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의참석자 등록
	 */
	public Integer insertCaseMtgAtdrn (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertCaseMtgAtdrnHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의참석자 이력 등록
	 */
	public Integer insertCaseMtgAtdrnHistory (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateCaseMtgAtdrn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의참석자 수정
	 */
	public Integer updateCaseMtgAtdrn (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteCaseMtgAtdrn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의참석자 삭제
	 */
	public Integer deleteCaseMtgAtdrn	(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateCaseMtgAtdrnYn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 31. 
	 * @Method설명 : 사례회의참석자 삭제여부 변경
	 */
	public Integer updateCaseMtgAtdrnYn	(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertCaseMtgPic
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의담당자 등록
	 */
	public Integer insertCaseMtgPic	(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertCaseMtgPicHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의담당자 이력 등록
	 */
	public Integer insertCaseMtgPicHistory (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateCaseMtgPic
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의담당자 수정
	 */
	public Integer updateCaseMtgPic (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteCaseMtgPic
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의담당자 삭제
	 */
	public Integer deleteCaseMtgPic (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateCaseMtgPicYn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 31. 
	 * @Method설명 : 사례회의담당자 삭제여부 변경
	 */
	public Integer updateCaseMtgPicYn (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectGrCaseMtgList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 목록
	 */
	public List<Map<String, Object>> selectGrCaseMtgList (Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectGrCaseMtgDtlList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 1. 
	 * @Method설명 : 집단사례회의 상세
	 */
	public List<Map<String, Object>> selectGrCaseMtgDtlList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseGrMtgAtdrnlList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 참석자조회
	 */
	public List<Map<String, Object>> selectCaseGrMtgAtdrnlList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectGrCaseMtgPiclList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 11. 
	 * @Method설명 : 집단사례회의 담당자조회
	 */
	public List<Map<String, Object>> selectGrCaseMtgPiclList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectGrCaseMtgTrprList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 11. 
	 * @Method설명 : 집단사례회의 사례대상자조회
	 */
	public List<Map<String, Object>> selectGrCaseMtgTrprList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : processGrCaseMtg
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 CRUD
	 */
	public Integer processGrCaseMtg (Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectSEB140List (Map<String, String> paramMap) throws Exception;	
	public List<Map<String, Object>> selectSEB160List (Map<String, String> paramMap) throws Exception;	
	public List<Map<String, Object>> selectSEB170List (Map<String, String> paramMap) throws Exception;	
	

}
