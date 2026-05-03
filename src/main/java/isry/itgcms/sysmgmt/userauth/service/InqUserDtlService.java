/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : InqUserDtl.java
 * @프로그램 설명 : 사용자 상세 정보 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 10. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 10.
 * @수정내용      : 
 * -                
 * -                
 */
public interface InqUserDtlService {
	
	public Map<String, String> selectUserDetail(DataRequest dataRequest) throws Exception;
	
	// 개인의 기관 권한 목록을 구한다.
	public List<Map<String, String>> selectUserInstituteAuthList(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectUserUnitSystem(String id) throws Exception;
}
