/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmt.casemng.slfrlreg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : EmrgActnService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SlfrlRegService {

	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception;
	public Map<String, String> saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectCaseJgmtById(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectTrlEmtById(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectSlfrlPrpareById(DataRequest dataRequest, String casePrgrsStts) throws Exception;

}
