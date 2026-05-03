/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbserr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface BbsErrListService {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	void bbserrDtlCnt(Map<String, Object> mapParam);

	void bbserrResCnt(Map<String, Object> mapParam);
	
	List<Map<String, Object>> BbsErrListCmbErr(String codeId) throws Exception;
	
	List<Map<String, Object>> BbsErrListCmbPrgrs(String codeId) throws Exception;

	List<Map<String, Object>> BbsErrListCmbSxdc(String codeId) throws Exception;
	
	List<Map<String, Object>> selectBbserrList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbserrDetail(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveBbserr(HttpServletRequest request, DataRequest dataRequest);
	
	//------------------------------------------------
	//답글(추가(insertRespod), 수정(updateRespod), 삭제(deleteRespod))
	Map<String, Object> saveBbserrRply(HttpServletRequest request, DataRequest dataRequest);
	
	//답글상세
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;

	
}
