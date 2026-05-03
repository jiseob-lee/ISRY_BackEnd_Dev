/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.mngrpage.aplcnttrprdtlinfomng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : AplcntTrprDtlInfoMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 5. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 5.
 * @수정내용      : 
 * -                
 * -                
 */
public interface AplcntTrprDtlInfoMngService {

	/**
	 * @Method명   : selectPtcptReqstdAplcntPop
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 참가신청서_신청자용 조회(드림)
	 */
	List<Map<String, Object>> selectPtcptReqstdAplcntPop(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : savePtcptReqstdAplcntPop
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 참가신청서_신청자용 저장(드림)
	 */
	void savePtcptReqstdAplcntPop(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : saveAdhrncWrtcns
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 참가자동의서 저장(드림)
	 */
	void saveAdhrncWrtcns(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectAdhrncWrtcns
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 : 참가자동의서 조회(드림)
	 */
	List<Map<String, String>> selectAdhrncWrtcns(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	int selectAdhrncWrtcnsChck(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
