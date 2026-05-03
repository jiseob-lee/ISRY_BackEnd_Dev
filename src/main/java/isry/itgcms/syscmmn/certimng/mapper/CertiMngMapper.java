/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.certimng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명      	: CertiMngMapper.java
 * @프로그램 설명 	: 자격증에 대한 내역을 관리한다.
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 9. 16. 
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 9. 16.
 * @수정내용    	: 
 * -                
 * -                
 */
@Mapper("certiMngMapper")
public interface CertiMngMapper {

	//자격증 목록 조회
	public List<Map<String, Object>> selectCertiList(Map<String, String> map) throws Exception;
	

}
