/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : RqstSrvcInqService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
public interface RqstSrvcInqService {
	
	// 의뢰 서비스조회(복지부 연계)
		public Map<String, Object> selectRqstSrvcList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
