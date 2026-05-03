/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DrmgsEduLinkMapper.java
 * @프로그램 설명 : 교육청 연계신청
 * @작성자        : Yoon.Hee.Sung
 * @작성일        : 2023. 8. 28. 
 * @수정자        : Yoon.Hee.Sung
 * @수정일        : 2023. 8. 28. 
 * @수정내용      : 교육청 연계신청
 */

@Mapper("drmgsEduLinkMapper")
public interface DrmgsEduLinkMapper {
	
	public String selectEduLinkListCnt(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectEduLinkList(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectEduDetInfo(Map<String, String> map) throws Exception;
}
