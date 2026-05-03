/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.fbdnwdreg.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : FbdnwdRegMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : 박찬호¸
 * @작성일        : 2022. 5. 19. 
 * @수정자        : 박찬호¸
 * @수정일        : 2022. 5. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("FbdnwdRegMapper")
public interface FbdnwdRegMapper {
	/**
	 * @Method명   : selectFbdnwdRegList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : 박찬호
	 * @작성일     : 2022. 5. 19. 
	 * @Method설명 : 금칙어 목록 조회
	 */
	List<Map<String, Object>> selectFbdnwdRegList(Map<String, Object> mapParam) throws Exception;	
	
	/**
	 * @Method명   : insertFbdnwdReg
	 * @param map
	 * @작성자     : 박찬호
	 * @작성일     : 2022. 5. 19. 
	 * @Method설명 :
	 */
	void insertFbdnwdReg(Map<String, String> map);	
	
	/**
	 * @Method명   : deleteFbdnwdReg
	 * @param map
	 * @작성자     : 박찬호
	 * @작성일     : 2022. 5. 19.
	 * @Method설명 :
	 */
	void deleteFbdnwdReg(Map<String, String> map);
	
	void updateFbdnwdReg(Map<String, String> map);
	
}
