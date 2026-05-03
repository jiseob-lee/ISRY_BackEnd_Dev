/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.parntsEduSrvc.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : ParntsEduSrvcMapper.java
 * @프로그램 설명 : 부모교육 서비스 통계 매퍼 인터페이스 - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2023. 5. 15.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2023. 5. 15.
 * @수정내용 : - -
 */
@Mapper("parntsEduSrvcMapper")
public interface ParntsEduSrvcMapper {

	/**
	 * @Method명   : selectParntsEduSrvcList
	 * @param dmParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 5. 15. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectParntsEduSrvcList(Map<String, Object> dmParam) throws Exception;

}
