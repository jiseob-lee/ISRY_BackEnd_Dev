/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.actvt.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : ActvtSafetyMuaiasMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("actvtSafetyMuaiasMapper")
public interface ActvtSafetyMuaiasMapper {

	// 활동안전공제회현황 관리 조회
	public List<Map<String, Object>> selectActvtSafetyMuaiasList(Map<String, Object> map) throws Exception;
	
	// 활동안전공제회현황 관리 팝업 조회
	public List<Map<String, Object>> selectActvtSafetyMuaiasPopupList(Map<String, String> map) throws Exception;
	
	// 활동안전공제회현황 관리 팝업 조회2
	public List<Map<String, Object>> selectActvtSafetyMuaiasPopupList2(Map<String, String> map) throws Exception;
}
