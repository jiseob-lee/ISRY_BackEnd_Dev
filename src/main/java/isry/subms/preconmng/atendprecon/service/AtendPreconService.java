/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendprecon.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : AtendPreconService.java
 * @프로그램 설명 : 출석현황 서비스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 13.
 * @수정내용 : - -
 */
public interface AtendPreconService {

	public List<Map<String, String>> selectAtendPreconList(DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectAtendList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	public void saveAtend(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, String> selectAtend(DataRequest dataRequest) throws Exception;

	public Map<String, String> selectAtendByTrpr(DataRequest dataRequest) throws Exception;
}
