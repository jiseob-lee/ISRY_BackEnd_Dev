/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.uneartmng.dgnssrcpt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : TlphonDscsnService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
public interface DgnssRcptService {
	
	public Map<String, Object> selectLinkRcptPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> selectRelaInstById(DataRequest dataRequest) throws Exception;
	public void updateRelaInstData(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> selectSchlDgnssById(DataRequest dataRequest) throws Exception;
	public void updateSchlDgnssData(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectDgnssScoreList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectInfantChilList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectCyberGambleList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectAddInspList(DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectDgnssScoreTrmnList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectInfantChilTrmnList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectCyberGambleTrmnList(DataRequest dataRequest) throws Exception;

	public List<Map<String, String>> selectDgnssScoreAftfctList(DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectLinkTrprRcptHistory(DataRequest dataRequest) throws Exception;

}
