/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operrpt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : OperRptService.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
public interface OperRptService {

	public List<Map<String, String>> selectOperRptList(DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectOperRpt(DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveOperRpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
