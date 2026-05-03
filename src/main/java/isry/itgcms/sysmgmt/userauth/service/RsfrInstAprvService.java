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
 * @파일명        : RsfrInstAprvService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 9. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 9.
 * @수정내용      : 
 * -                
 * -                
 */
public interface RsfrInstAprvService {
	
	/**
	 * @Method명   : selectRsfrInstAprvList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 승인목록
	 */
	public List<Map<String, Object>> selectRsfrInstAprvList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : saveRsfrInstAprv
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 승인
	 */
	public void saveRsfrInstAprv (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : saveRsfrInstRjct
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 반려
	 */
	public void saveRsfrInstRjct (HttpServletRequest request, DataRequest dataRequest) throws Exception;

}
