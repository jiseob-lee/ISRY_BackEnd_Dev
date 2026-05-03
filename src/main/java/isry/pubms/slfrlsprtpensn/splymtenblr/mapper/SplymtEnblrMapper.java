/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.splymtenblr.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SplymtEnblrMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.07.26
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.07.26
 * @수정내용      :
 * - [2023-08-30, Gyu.Ha.Baek] PRE 반영
 * -
 */
@Mapper("splymtEnblrMapper")
public interface SplymtEnblrMapper {
	
	public int selectSplymtEnblrMtchngListCount(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectSplymtEnblrMtchngList(Map<String, Object> map) throws Exception;
	public int selectSplymtEnblrDtlListCount(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectSplymtEnblrDtlList(Map<String, Object> map) throws Exception;
	public int selectSplymtEnblrDtlSEB900ListCount(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectSplymtEnblrDtlSEB900List(Map<String, Object> map) throws Exception;
	public int selectMtchngListCount(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectMtchngList(Map<String, Object> map) throws Exception;
	public void updateMtchngReg(Map<String, Object> map) throws Exception;
	
}
