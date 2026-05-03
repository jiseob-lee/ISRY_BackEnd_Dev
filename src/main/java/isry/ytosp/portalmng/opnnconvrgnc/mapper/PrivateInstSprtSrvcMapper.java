/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.opnnconvrgnc.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : PrivateInstSprtSrvcMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2023. 8. 26. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2023. 8. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("PrivateInstSprtSrvcMapper")
public interface PrivateInstSprtSrvcMapper {
	
	List<Map<String , Object>> selectPrivateInstSprtSrvcList(Map<String, Object> mapParam);
	List<Map<String, Object>> selectPrivateInstSprtSrvcListDetail(Map<String, Object> mapParam) throws Exception;
	void updatePrivateInstSprtSrvcList(Map<String, Object> mapParam);
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
}