/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : RsfrInstMngService.java
 * @프로그램 설명 : 순수자원제공주체기관 관리
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
public interface RsfrInstMngService {
	
	/**
	 * @Method명   : selectRsfrInstMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 목록
	 */
	public List<Map<String, Object>> selectRsfrInstMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectRsfrInstDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 상세정보
	 */
	public Map<String, Object> selectRsfrInstDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processRsfrInst
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 자원제공기관 처리
	 */
	public Map<String, Object> processRsfrInst(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
