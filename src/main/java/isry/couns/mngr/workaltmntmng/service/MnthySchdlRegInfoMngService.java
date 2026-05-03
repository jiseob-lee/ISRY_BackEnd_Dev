/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface MnthySchdlRegInfoMngService {
	
	//List<Map<String, Object>> selectCnnctChatReqstdList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthySchdlRegInfoMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthySchdlRegModAsgnNocs(Map<String, String> mapParam) throws Exception;	
	
	int processMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception;	
	
	int processMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception;
	
	int deleteMnthySchdlRegInfoMng(Map<String, Object> mapParam) throws Exception;
	
	int deleteMnthySchdlRegInfoMng2(Map<String, Object> mapParam) throws Exception;
	
//	int insertMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertMnthySchdlRegInfoMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 월별 일정등록정보 관리 - 최초 등록
	 */
	int insertMnthySchdlRegInfoMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectChcMnthySchdlList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 조회
	 */
	List<Map<String, Object>> selectChcMnthySchdlList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectChcMnthySchdlAsgnInfo
	 * @param 	   : dataRequest
	 * @return	   : Map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 17. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 할당량 조회
	 */
	Map<String, Object> selectChcMnthySchdlAsgnInfo(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processChcMnthySchdlMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 수정
	 */
	int processChcMnthySchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : deleteProcessChcMnthySchdlMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 6. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 전체 삭제
	 */
	public void deleteProcessChcMnthySchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	int insertMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception;
	
	Map<String, String> insertMnthySchdlRegInfoMngCopy(Map<String, String> mapParam) throws Exception;
	
}
