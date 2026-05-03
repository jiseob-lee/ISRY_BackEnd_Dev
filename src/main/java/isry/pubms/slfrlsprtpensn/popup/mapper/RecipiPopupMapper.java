/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.popup.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : RecipiRegMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.08.31
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.08.31
 * @수정내용      : 
 * - [2023-08-31, Gyu.Ha.Baek] PRE 반영
 */
@Mapper("recipiPopupMapper")
public interface RecipiPopupMapper {
	
	public int selectOrganizationViewListCount(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectOrganizationViewList(Map<String, Object> map) throws Exception;
	
}
