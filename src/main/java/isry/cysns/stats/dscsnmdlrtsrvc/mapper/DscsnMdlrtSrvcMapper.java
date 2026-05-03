/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.dscsnmdlrtsrvc.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : DscsnMdlrtSrvcMapper.java
 * @프로그램 설명 : 상담치료서비스통계 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 5. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 5. 11.
 * @수정내용 : - -
 */
@Mapper("dscsnMdlrtSrvcMapper")
public interface DscsnMdlrtSrvcMapper {

	/**
	 * @Method명 : selectDscsnMdlrtSrvcList
	 * @param dmParam
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 5. 11.
	 * @Method설명 : 상담치료서비스통계 조회
	 */
	public List<Map<String, Object>> selectDscsnMdlrtSrvcList(Map<String, Object> dmParam) throws Exception;

}
