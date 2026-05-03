/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.cmmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : CsemdMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 9. 29.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 9. 29.
 * @수정내용 : - -
 */

@Mapper("csemdMapper")
public interface CsemdMapper {

	public List<Map<String, Object>> selectBizYr(Map<String, String> requestMap) throws Exception;

	public List<Map<String, Object>> selectInstCmb(Map<String, String> requestMap) throws Exception;

	public List<Map<String, Object>> selectSrvcExcnBizCmb(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명 : selectViolnc
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectViolnc();

	/**
	 * @Method명 : selectDiss
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDiss();

	/**
	 * @Method명 : selectLgsltn
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectLgsltn();

	/**
	 * @Method명 : selectDscsn
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDscsn();

	/**
	 * @Method명 : selectMaap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectMaap();

	/**
	 * @Method명 : selectEtc
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectEtc();

	/**
	 * @Method명 : selectGhvrLatent
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectGhvrLatent();

	/**
	 * @Method명 : selectOmen
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectOmen();

	/**
	 * @Method명 : selectProbmGhvr
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectProbmGhvr();

	/**
	 * @Method명 : selectdsViolncYn
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectViolncYn();

	/**
	 * @Method명 : selectdsSlfijr
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSlfijr();

	/**
	 * @Method명 : selectSucde
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSucde();

	/**
	 * @Method명 : selectBrhs
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectBrhs();

	/**
	 * @Method명 : selectNowTakng
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectNowTakng();

	/**
	 * @Method명 : selectMece
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectMece();

	/**
	 * @Method명 : selectRprsMaap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectRprsMaap();

	/**
	 * @Method명   : selectPblast
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectPblast();

	/**
	 * @Method명   : selectReside
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectReside();

	/**
	 * @Method명   : selectFam
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectFam();

}
