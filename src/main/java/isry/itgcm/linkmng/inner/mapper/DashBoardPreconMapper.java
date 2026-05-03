/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.inner.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
* @Class Name  : DashBoardPreconMapper.java
* @Description : 메인 대시보드 현황  Mapper Class
*
* @author  : Lee.Seung.Yeon
* @since   : 2022. 11. 03.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 11. 03.  Lee.Seung.Yeon    최초작성
* </pre>
*/

@Mapper("dashBoardPreconMapper")
public interface DashBoardPreconMapper {

	/**
	 * @Method		: deleteSAB990
	 * @Method설명 	: 메인화면대시보드현황 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 11. 03. 
 	 */	
	public void deleteSAB990(Map<String, String> map) throws Exception;

	/**
	 * @Method		: insertSAB990
	 * @Method설명 	: 메인화면대시보드현황 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 11. 03. 
 	 */	
	public void insertSAB990(Map<String, Object> map) throws Exception;	
	
}
