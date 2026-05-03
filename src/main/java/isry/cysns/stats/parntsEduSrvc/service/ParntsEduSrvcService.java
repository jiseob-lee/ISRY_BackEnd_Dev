/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.parntsEduSrvc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : ParntsEduSrvcService.java
 * @프로그램 설명 : 부모교육서비스 통계
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2023. 5. 15. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2023. 5. 15.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ParntsEduSrvcService {

	/**
	 * @Method명   : selectParntsEduSrvcList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 5. 15. 
	 * @Method설명 : 부모교육 서비스 통계 조회
	 */
	public List<Map<String, Object>> selectParntsEduSrvcList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
