/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.wiktaskwkrpt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : WikTaskWorkRptMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 6. 10. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 6. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("wikTaskwkRptMapper")
public interface WikTaskwkRptMapper {
	public List<Map<String, String>> selectWikTaskwkList(Map<String, String> mapParam);
	
	/**
	 * 
	 * @Method명   : insertWikTaskwkRpt
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 : 주간보고 등록
	 */
	public int insertWikTaskwkRpt(Map<String, String> map) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateWikTaskwkRpt
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 : 주간보고 수정
	 */
	public int updateWikTaskwkRpt(Map<String, String> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : deleteWikTaskwkRpt
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 : 주간보고 삭제
	 */
	public int deleteWikTaskwkRpt(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectWikTaskwk
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 : 상세조회 - 조회조건,평가 및 특이사항, 활동사진 조회
	 */
	public List<Map<String, Object>> selectWikTaskwkInqCnd(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectWikTaskwkInqCnd
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 : 상세조회 - 주간운영시수 현황 조회
	 */
	public List<Map<String, Object>> selectWikTaskwkWikOper(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectWikTaskwkWikOper
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 : 상세조회 - 프로그램 참여현황
	 */
	public List<Map<String, Object>> selectWikTaskwkProgrmPtcptn(Map<String, Object> mapParam) throws Exception;

	/**
	 * 
	 * @Method명   : selectWikTaskwkProgrmPrtptn
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 : 상세조회 - 연계현황 조회
	 */
	public List<Map<String, Object>> selectWikTaskwkLinkPrecon(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectResrceNo
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	public String selectResrceNo(Map<String, String> map) throws Exception;

}
