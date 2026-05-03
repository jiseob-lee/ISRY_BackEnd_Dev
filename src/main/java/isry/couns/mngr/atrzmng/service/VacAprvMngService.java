/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface VacAprvMngService {
	
	List<Map<String, Object>> searchComboBoxAply() throws Exception;
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboBoxVac(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectVacAprvMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectVacAprvMngDetail(Map<String, Object> mapParam) throws Exception;
	
	int updateVacAprvMngBatch(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : processVacAprvBatch
	 * @Method설명 : 휴가승인관리 - 일괄승인
	 * @param      : dataRequest
	 * @return	   : int
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 10. 25. 
	 */
	int processVacAprvBatch(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processVacAprvMngSms
	 * @Method설명 : 휴가승인관리 - 문자 일괄 전송
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 2. 
	 */
	Map<String, Object> processVacAprvMngSms(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : updateVacAprvMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 21.
	 * @Method설명 : 휴가승인관리 수정 로직
	 */
	public void updateVacAprvMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	int deleteVacAprvMng(Map<String, Object> mapParam) throws Exception;
	
	int insertVacAprvMngSms1(Map<String, Object> mapParam) throws Exception;
	
	int insertVacAprvMngSms2(Map<String, Object> mapParam) throws Exception;
	
	int insertVacAprvMngSms3(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertVacAprvMngGw
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 27. 
	 * @Method설명 : 휴가일정 가져오기 일괄 등록
	 */
	Map<String, Object> insertVacAprvMngGw(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
