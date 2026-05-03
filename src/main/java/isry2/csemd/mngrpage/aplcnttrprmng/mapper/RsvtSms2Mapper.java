/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry2.csemd.mngrpage.aplcnttrprmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : RsvtSmsMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 14. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("csemdRsvtSms2Mapper")
public interface RsvtSms2Mapper {

	/**
	 * @param map 
	 * @Method명   : selectRcptnTrprList
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectRcptnTrprList(Map<String, String> map) throws Exception;


	/**
	 * @param contSeq 
	 * @Method명   : insertMsgData
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 :
	 */
	void insertMsgData(Map<String, String> contSeq);

	/**
	 * @Method명   : insertNtcnSnsSndng
	 * @param insertKey
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 17. 
	 * @Method설명 :
	 */
	void insertNtcnSnsSndng(Map<String, String> insertKey);


	/**
	 * @Method명   : insertMmsContentsInfo
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 :
	 */
	void insertMmsContentsInfo(Map<String, String> map);



	
}
