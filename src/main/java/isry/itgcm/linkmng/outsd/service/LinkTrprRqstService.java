/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : LinkTrprRqstService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : TaesooSong
 * @작성일        : 2022. 8. 2. 
 * @수정자        : TaesooSong
 * @수정일        : 2022. 8. 2.
 * @수정내용      : 
 * -                
 * -                
 */
public interface LinkTrprRqstService {
	
	public Map<String, Object> onLoadLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectLinkTrprRqstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> executeLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectLinkTrprRcptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectLinkTrprRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveLinkTrprRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> executeLinkTrprRcpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	//병무청 연계접수
//	public List<Map<String, Object>> selectLinkMmatList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void deleteLinkTrprRqst(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 페이징 처리를 위해 생성 2023.07.05 
	public Map<String, Object> selectLinkTrprRcptPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
