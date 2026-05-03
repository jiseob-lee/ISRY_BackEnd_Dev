/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : ProgramExcnMng.java
 * @프로그램 설명 : 프로그램 실행관리 
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 8. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 8. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("programExcnMngMapper")
public interface ProgramExcnMngMapper {
	
	/**
	 * @Method명   : selectSrvcExcnBizList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 검색 서비스실행사업명 조회
	 */
	public Map<String, String> selectSrvcExcnBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUnityInstList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 검색 자원제공주체 조회
	 */
	public Map<String, String> selectUnityInstList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResrceBassList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 자원기본 목록 조회
	 */
	public List<Map<String, Object>> selectResrceBassList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectProgramExcnHrList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 자원프로그램실행시간 목록 조회
	 */
	public List<Map<String, Object>> selectProgramExcnHrList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectProgramExcnHrCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 자원프로그램실행시간 중복조회
	 */
	public Integer selectProgramExcnHrCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectProgramExcnMngList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 프로그램실행 목록 조회
	 */
	public List<Map<String, Object>> selectProgramExcnMngList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResrcePvsnPrdList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 : 자원제공기간 조회
	 */
	public List<Map<String, Object>> selectResrcePvsnPrdList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResrceProgrmDtlSchdlLctreSn
	 * @param LctreSn
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 자원프로그램 강의일련번호 조회
	 */
	public String selectResrceProgrmDtlSchdlLctreSn(String LctreSn) throws Exception;
	
	/**
	 * @Method명   : insertResrceProgrmDtlSchdlHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 자원프로그램 상세일정 이력 등록
	 */
	public Integer insertResrceProgrmDtlSchdlHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertResrceProgrmExcnHr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 자원프로그램 실행시간 등록
	 */
	public Integer insertResrceProgrmExcnHr(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method명   : updateResrceProgrmExcnHr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 자원프로그램 실행 시간 수정
	 */
	public Integer updateResrceProgrmExcnHr(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteResrceProgrmExcnHr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 자원프로그램 실행 시간 삭제
	 */
	public Integer deleteResrceProgrmExcnHr(Map<String, String> paramMap) throws Exception;
	

	

}
