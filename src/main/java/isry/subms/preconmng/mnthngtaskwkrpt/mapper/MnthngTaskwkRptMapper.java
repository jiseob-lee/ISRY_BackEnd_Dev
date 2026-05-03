/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.mnthngtaskwkrpt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MnthngTaskWorkRptMapper.java
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
@Mapper("mnthngTaskwkRptMapper")
public interface MnthngTaskwkRptMapper {
	public List<Map<String, String>> selectMnthngTaskwkList(Map<String, String> mapParam);
	
	/**
	 * 
	 * @Method명   : insertMnthngTaskwkRpt
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 : 월간보고 등록
	 */
	public int insertMnthngTaskwkRpt(Map<String, String> map) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateMnthngTaskwkRpt
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 : 월간보고 수정
	 */
	public int updateMnthngTaskwkRpt(Map<String, String> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : deleteMnthngTaskwkRpt
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 : 월간보고 삭제
	 */
	public int deleteMnthngTaskwkRpt(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectMnthngTaskwk
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 : 상세조회 - 조회조건,평가 및 특이사항, 활동사진 조회
	 */
	public List<Map<String, Object>> selectMnthngTaskwkInqCnd(Map<String, String> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectMnthngTaskwkInqCnd
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 : 상세조회 - 월간운영시수 현황 조회
	 */
	public List<Map<String, Object>> selectMnthngOperHrPrecon(Map<String, String> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectMnthngTaskwkMnthngOper
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 : 상세조회 - 프로그램 참여현황
	 */
	public List<Map<String, Object>> selectMnthngTaskwkProgrmPtcptn(Map<String, Object> mapParam) throws Exception;

	/**
	 * 
	 * @Method명   : selectMnthngTaskwkProgrmPrtptn
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 : 상세조회 - 연계현황 조회
	 */
	public List<Map<String, Object>> selectMnthngAtendLinkPrecon(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : saveMnthngTaskwkRpt
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 :
	 */
	public int saveMnthngTaskwkRpt(Map<String, String> map);

	/**
	 * @Method명   : selectCheckResrce
	 * @param paramMap
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 : 등록/수정 전 선택한 자원, 서비스 실행사업 등이 각각 맞물려 있는지 체크
	 */
	public int selectCheckResrce(Map<String, String> paramMap);

	/**
	 * @Method명   : selectCheckMnthngTaskwk
	 * @param paramMap
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 :
	 */
	public Map<String, Object> selectCheckMnthngTaskwk(Map<String, String> paramMap);

}
