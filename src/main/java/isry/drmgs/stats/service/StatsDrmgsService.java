/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stats.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : StatsDrmgsService.java
 * @프로그램 설명 : 학교밖지원센터 통계
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 12. 23. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2022. 12. 23. 
 * @수정내용      : 학교밖청소년지원센터 통계
*/

public interface StatsDrmgsService {
	// 직업역량강화 통계
	public List<Map<String, Object>> selectOccpAbilitStats(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 학업중단숙려제 통계
	public List<Map<String, Object>> selectMeditationStats(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 경기도사업 통계
	public List<Map<String, Object>> selectGgBizStats(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
