/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.cmmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : AimnsMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 6. 7.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 6. 7.
 * @수정내용 : - -
 */
@Mapper("aimnsMapper")
public interface AimnsMapper {

	/**
	 * @Method명 : selectBizYrCombo
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 7.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectBizYrCombo() throws Exception;

	/**
	 * @Method명 : selectInstCombo
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 7.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectInstCombo() throws Exception;

	/**
	 * @Method명 : selectProgrmCombo
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 7.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectProgrmCombo() throws Exception;

	/**
	 * @Method명 : selectResrceCombo
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 6. 30.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectResrceCombo() throws Exception;
	
	
	/**
	 * @Method명   : SelectSrngTrprDetail
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 발굴대상자등록화면 심사대상자 상세정보 조회
	 */
	public Map<String, Object> SelectSrngTrprDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @Method명   : insertSrngTrpr
	 * @param map
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : AFA400 심사대상자 insert
	 */
	public int insertSrngTrpr(Map<String, String> map) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateSrngTrpr
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : AFA400 심사대상자 update
	 */
	public int updateSrngTrpr(Map<String, String> map) throws Exception;
}
