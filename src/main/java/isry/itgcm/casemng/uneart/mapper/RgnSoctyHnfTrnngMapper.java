/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : RgnSoctyHnfTrnngMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 5. 19. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 5. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("rgnSoctyHnfTrnngMapper")
public interface RgnSoctyHnfTrnngMapper {
	
	/**
	 * @Method명   : selectRgnSoctyHnfTrnngInqList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 지역사회 인력양성 목록
	 */
	List<Map<String, Object>> selectRgnSoctyHnfTrnngInqList(Map<String, Object> paramMap) throws Exception;
	Integer rgnSoctyHnfTrnngInqListCount(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectRgnSoctyHnfTrnngDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 지역사회 인력양성 상세
	 */
	List<Map<String, Object>> selectRgnSoctyHnfTrnngDetail(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectRgnSoctyHnfTrnngSrvcBiz
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 23. 
	 * @Method설명 : 지역사회 인력양성 서비스사업 
	 */
	List<Map<String, Object>> selectRgnSoctyHnfTrnngSrvcBiz(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : getSrvcExcnBizCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 서비스사업 중복조회
	 */
	Integer getSrvcExcnBizCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectRgnSoctyHnfTrnngExcnSrvcDetaiaBiz
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 23. 
	 * @Method설명 : 지역사회 인력양성 실행서비스 세부사업
	 */
	List<Map<String, Object>> selectRgnSoctyHnfTrnngExcnSrvcDetaiaBiz(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : getExcnSrvcDetaiaBizCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 실행서비스 세부사업 중복조회
	 */
	Integer getExcnSrvcDetaiaBizCnt(Map<String, String> paramMap) throws Exception;	
	
	/**
	 * @Method명   : processRgnSoctyHnfTrnng
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 지역사회 인력양성 등록, 수정, 삭제
	 */
	Map<String, Object> processRgnSoctyHnfTrnng(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertSED200Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 23. 
	 * @Method설명 : 지역사회 인력양성 등록
	 */
	Integer insertSED200Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSED200Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 수정
	 */
	Integer updateSED200Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertSED200History
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 삭제
	 */
	Integer insertSED200History(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertSED210Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 서비스사업 등록
	 */
	Integer insertSED210Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSED210Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 서비스사업 수정
	 */
	Integer updateSED210Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteSED210Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 서비스사업 삭제
	 */
	Integer deleteSED210Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSrvcExcnBizYn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 서비스사업 등록수정
	 */
	Integer updateSrvcExcnBizYn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method명   : insertSED220Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 실행서비스세부사업 등록
	 */
	Integer insertSED220Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSED220Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 실행서비스세부사업 수정
	 */
	Integer updateSED220Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteSED220Data
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 24. 
	 * @Method설명 : 지역사회 인력양성 실행서비스세부사업 삭제
	 */
	Integer deleteSED220Data(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateExcnSrvcDetaiaBizYn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 실행서비스세부사업 사용 수정
	 */
	Integer updateExcnSrvcDetaiaBizYn(Map<String, String> paramMap) throws Exception;
	
	
	
	
	

}
