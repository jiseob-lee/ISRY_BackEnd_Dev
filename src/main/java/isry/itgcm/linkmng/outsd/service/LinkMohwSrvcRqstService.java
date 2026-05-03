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
 * @파일명        : LinkMohwSrvcRqstService.java
 * @프로그램 설명 : 복지부 연계서비스 의뢰
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 29. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 29.
 * @수정내용      : 
 * -                
 * -                
 */
public interface LinkMohwSrvcRqstService {
	
	public static final String SND_CD     = "MOG";  					/* 53. 송신기관코드ID SND_CD*/
	public static final String RCV_CD     = "SSI";  					/* 54. 수신기관코드ID RCV_CD*/	
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 : 복지부 서비스의뢰접수 목록 
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수정보 조회
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfo (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfoResultList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰접수정보결과 목록
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfoResultList (HttpServletRequest request, DataRequest dataRequest) throws Exception;

	
	/**
	 * @Method명   : selectMohwSrvcRqstRcptInfoResultInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 7. 
	 * @Method설명 : 복지부 서비스의뢰접수결과정보 조회
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstRcptInfoResultInfo (HttpServletRequest request, DataRequest dataRequest) throws Exception;	
	
	/**
	 * @Method명   : processMohwSrvcRqstRcpt
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 30. 
	 * @Method설명 : 복지부 서비스의뢰 접수 처리
	 */
	public Map<String, Object> processMohwSrvcRqstRcpt (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 목록
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstDmndList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndInfoResultList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청결과정보 목록
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstDmndInfoResultList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectMohwSrvcRqstDmndInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청정보 조회
	 */
	public List<Map<String, Object>> selectMohwSrvcRqstDmndInfo (HttpServletRequest request, DataRequest dataRequest) throws Exception;	
	
	/**
	 * @Method명   : processMohwSrvcRqstDmnd
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 복지부 서비스의뢰요청 처리
	 */
	public Map<String, Object> processMohwSrvcRqstDmnd (HttpServletRequest request, DataRequest dataRequest) throws Exception;	
	
	/**
	 * @Method명   : linkMohwCaseReg
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 복지부 서비스의뢰 접수 후 연계성공후 사례등록
	 */
	public void linkMohwCaseReg() throws Exception;

	/**
	 * @Method명   : selectMohwWlfarResrce
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 11. 15. 
	 * @Method설명 : 복지부 실시간 연계_복지자원조회
	 */
	public Map<String, Object> selectMohwWlfarResrce (HttpServletRequest request, DataRequest dataRequest) throws Exception;

	
	public void linkMohwJobTest() throws Exception;
	
}
