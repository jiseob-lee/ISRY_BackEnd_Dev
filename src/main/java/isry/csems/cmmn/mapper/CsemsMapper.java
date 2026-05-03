/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.cmmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CsemsMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 9. 29. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 9. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("csemsMapper")
public interface CsemsMapper {

	/**
	 * @Method명   : selectLgsltn
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectLgsltn();

	/**
	 * @Method명   : selectDscsn
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDscsn();

	/**
	 * @Method명   : selectDiss
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDiss();

	/**
	 * @Method명   : selectPrtcr
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectPrtcr();

	/**
	 * @Method명   : selectProbmRelm
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectProbmRelm();

	/**
	 * @Method명   : selectSmkng
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSmkng();

	/**
	 * @Method명   : selectDrnkg
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDrnkg();

	/**
	 * @Method명   : selectTeachr
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectTeachr();

	/**
	 * @Method명   : selectFrid
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectFrid();

	/**
	 * @Method명   : selectSocty
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSocty();

	/**
	 * @Method명   : selectFridCnt
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectFridCnt();

	/**
	 * @Method명   : selectDevlpa
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectDevlpa();

	/**
	 * @Method명   : selectViolnc
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectViolnc();

	/**
	 * @Method명   : selectSlfijr
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSlfijr();

	/**
	 * @Method명   : selectSucde
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectSucde();

	/**
	 * @Method명   : selectNowTakng
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectNowTakng();

	/**
	 * @Method명   : selectTrl
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectTrl();

	/**
	 * @Method명   : selectRprsMaap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectRprsMaap();

}
