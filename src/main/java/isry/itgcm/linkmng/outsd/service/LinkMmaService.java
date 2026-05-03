/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : LinkMmaService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Choi.Doo.Il
 * @작성일        : 2022. 10. 4. 
 * @수정자        : Choi.Doo.Il
 * @수정일        : 2022. 10. 4.
 * @수정내용      : 
 * -                
 * -                
 */
public interface LinkMmaService {
	
	
	/* 병무청 병역의무자 상담지원의뢰 목록 */
	public List<Map<String, Object>> selectMmaRqstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	//병무청 연계접수
	public List<Map<String, Object>> selectLinkMmaList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	//병무청 의뢰접수처리
	public Map<String, String> saveLinkMma(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	final String SND_CD     = "MOG";  					/* 14. 송신기관코드ID SND_CD*/
	final String RCV_CD     = "MMA";  					/* 15. 수신기관코드ID RCV_CD*/
	
	/**
	 * @Method명   : selectMmaRqstInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 19. 
	 * @Method설명 :  병무청 병역의무자 상담의뢰지원 정보 조회
	 */
	public List<Map<String, Object>> selectMmaRqstInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectMmaRqstInfoResult
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 19. 
	 * @Method설명 : 병무청 병역의무자 상담의뢰지원 결과정보 조회
	 */
	public List<Map<String, Object>> selectMmaRqstInfoResult(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processMmaRqstResult
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 20. 
	 * @Method설명 :
	 */
	public Map<String, Object> processMmaRqstResult(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void linkMmaFilesDown(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception;
}
