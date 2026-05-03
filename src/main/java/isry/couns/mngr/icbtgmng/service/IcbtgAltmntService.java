/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.icbtgmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface IcbtgAltmntService {
	
	List<Map<String, Object>> selectIcbtgAltmntList(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method     : insertIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 배정표 등록
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
	 * @상세	   : IcbtgConsttChcService(작성자 : 유영태) → IcbtgAltmntService로 복사
 	 */
	public int insertIcbtgAltmnt(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : updateIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 배정표 상세 수정
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
 	 */
	public int updateIcbtgAltmnt(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : deleteIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 배정표 상세 삭제
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
 	 */
	public int deleteIcbtgAltmnt(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	//int insertIcbtgConsttChc(Map<String, Object> mapParam) throws Exception;
	

}
