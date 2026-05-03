/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcm.casemng.caseunity.mapper.CaseDdlnYnMapper;
import isry.itgcm.casemng.caseunity.service.CaseDdlnYnService;


/**
 * @파일명        : CaseDdlnYntServiceImpl.java
 * @프로그램 설명 : 마감여부
 * 
 * @작성자        : Choi.Doo.Il
 * @작성일        : 2022. 9. 05. 
 * @수정자        : 
 * @수정일        : 
 * @수정내용      : 
 * -
 */
@Service("caseDdlnYnService")
public class CaseDdlnYnServiceImpl implements CaseDdlnYnService {

	@Resource(name="caseDdlnYnMapper")
    private CaseDdlnYnMapper caseDdlnYnMapper;


	/**
	* @Method    : 마감여부
	* @param     : Map
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public Map<String, Object> caseDdlnYn(String sUntTaskwkSeCd, String sDdlnYm) throws Exception {
		
		// 마감여부 확인
		Map<String, String> ynMap  = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();
		ynMap.put("DDLN_YM"         , sDdlnYm);	        // 마감년월
		ynMap.put("UNT_TASKWK_SE_CD", sUntTaskwkSeCd);	// 단위업무구분코드
		
		valMap   = caseDdlnYnMapper.selectDdlnYn(ynMap);
		
		return valMap;
	}
}
