/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.casemng.caseaftfct.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CysnsTrmnService.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
public interface CysnsAftfctService {

	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception;
	
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
