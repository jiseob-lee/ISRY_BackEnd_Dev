/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.nowenstprecon.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : NowEnstPreconMapper.java
 * @프로그램 설명 : 현재입교생현황 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 6.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 6.
 * @수정내용 : - -
 */
@Mapper("nowEnstPreconMapper")
public interface NowEnstPreconMapper {

	/**
	 * 
	 * @Method명   : selectNowEnstPrecon
	 * @param dmSearch
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectNowEnstPrecon(Map<String, String> dmSearch);
}
