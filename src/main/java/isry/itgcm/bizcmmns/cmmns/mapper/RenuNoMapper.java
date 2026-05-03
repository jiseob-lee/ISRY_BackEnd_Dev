/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
* @Class Name  : TrprInfoService.java
* @Description : 대상자정보조회 팝업 Mapper Class
*
* @author  : Lee.Jun.Yeong
* @since   : 2022. 05. 11.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 11.  Lee.Jun.Yeong    최초작성
* </pre>
*/

@Mapper("renuNoMapper")
public interface RenuNoMapper {

	//채번
	public Map<String, Object> selectCaseMngNoRenu(Map<String, String> map) throws Exception;

}
