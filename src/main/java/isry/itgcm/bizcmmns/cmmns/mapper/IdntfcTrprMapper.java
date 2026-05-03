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
 * @파일명        : IdntfcTrprMapper.java
 * @프로그램 설명 : 개인식별등록 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 8. 22. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 8. 22.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("idntfcTrprMapper")
public interface IdntfcTrprMapper {
	
	
	/**
	 * @Method명   : selectIdntfcTrprList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 22. 
	 * @Method설명 : 개인식별 등록 팝업
	 */
	public List<Map<String, Object>> selectIdntfcTrprList (Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertIndvIdntfcReg
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 개인식별 등록
	 */
	public int insertIndvIdntfcReg(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : getIdntfcTrprList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 30. 
	 * @Method설명 : 개인식별등록 건수
	 */
	public int getIdntfcTrprList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method명   : deleteIdntfc
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 8. 17. 
	 * @Method설명 : 개인식별 해제
	 */
	public void updateIdntfc(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertIdntfcHis
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 8. 17. 
	 * @Method설명 : 개인식별 해제 이력등록
	 */
	public void insertIdntfcHis(Map<String, String> paramMap) throws Exception;
}
