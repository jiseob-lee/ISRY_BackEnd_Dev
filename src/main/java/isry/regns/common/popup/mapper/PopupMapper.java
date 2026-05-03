/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.common.popup.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LinkInstMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("popupMapper")
public interface PopupMapper {
	
	public List<Map<String, String>> selectCmitMtgList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectLinkInstList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectEmrgRptList(Map<String, String> paramMap) throws Exception;
	public List<Map<String, String>> selectEmrgActnList(Map<String, String> paramMap) throws Exception;

}
