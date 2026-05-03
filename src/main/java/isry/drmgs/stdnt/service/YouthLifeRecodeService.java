/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stdnt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : YouthLifeRecodeService.java
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
public interface YouthLifeRecodeService {

	// 청소년생활기록부 관리 조회
	public List<Map<String, Object>> selectYouthLifeRecodeMainList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 청소년생활기록부 관리 삭제
	public int deleteYouthLifeRecode(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 청소년생활기록부 출력 이력 조회
	public List<Map<String, Object>> selectOtptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
