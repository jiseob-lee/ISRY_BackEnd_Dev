/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : DrmgsEduLinkService.java
 * @프로그램 설명 : 교육청 연계신청
 * @작성자        : Yoon.Hee.Sung
 * @작성일        : 2023. 8. 28. 
 * @수정자        : Yoon.Hee.Sung
 * @수정일        : 2023. 8. 28. 
 * @수정내용      : 교육청 연계신청
 */

public interface DrmgsEduLinkService {

	public Map<String, Object> selectEduLinkList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectEduDetInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
