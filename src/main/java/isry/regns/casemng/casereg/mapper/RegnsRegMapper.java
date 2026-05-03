/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.casemng.casereg.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : RegnsRegMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("regnsRegMapper")
public interface RegnsRegMapper {
	
	public List<Map<String, String>> selectReqById(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectEfectnById(Map<String, String> map) throws Exception;
	public void saveData(Map<String, String> map) throws Exception;
	public void saveEfectnData(Map<String, String> map) throws Exception;

}
