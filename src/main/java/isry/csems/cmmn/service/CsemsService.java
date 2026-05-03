/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.cmmn.service;

import java.util.List;
import java.util.Map;

/**
 * @파일명        : CsemsService.java
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
public interface CsemsService {

	List<Map<String, String>> selectLgsltn();

	List<Map<String, String>> selectDscsn();

	List<Map<String, String>> selectDiss();

	List<Map<String, String>> selectPrtcr();

	List<Map<String, String>> selectProbmRelm();

	List<Map<String, String>> selectSmkng();

	List<Map<String, String>> selectDrnkg();

	List<Map<String, String>> selectTeachr();

	List<Map<String, String>> selectFrid();

	List<Map<String, String>> selectSocty();

	List<Map<String, String>> selectFridCnt();
	
	List<Map<String, String>> selectDevlpa();

	List<Map<String, String>> selectViolnc();

	List<Map<String, String>> selectSlfijr();

	List<Map<String, String>> selectSucde();

	List<Map<String, String>> selectNowTakng();

	List<Map<String, String>> selectTrl();

	List<Map<String, String>> selectRprsMaap();



}
