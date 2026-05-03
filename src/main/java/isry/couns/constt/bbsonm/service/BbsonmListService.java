/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsonm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : BbsonmService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */
public interface BbsonmListService {
	
	
	List<Map<String , Object>> selectBbsonmList(Map<String, Object> mapParam);
	
	Map<String , Object> saveBbsonmList(HttpServletRequest request, DataRequest dataRequest);
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectU11LastArticleNo
	 * @return
	 * @작성자     : Lee.In.Sung
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 : 업무구분코드 U11 청소년상담 위기사례 마지막 글번호 조회
	 */
	Map<String, Object> selectU11DangerLastArticleNo() throws Exception;

}
