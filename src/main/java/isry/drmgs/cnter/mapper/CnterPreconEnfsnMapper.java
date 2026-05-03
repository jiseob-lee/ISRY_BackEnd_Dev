/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CnterPreconEnfsnMapper.java
 * @프로그램 설명 : 센터별 종사자 현황
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 8. 3o. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 3o. 
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("cnterPreconEnfsnMapper")
public interface CnterPreconEnfsnMapper {

	// 센터 종사자 현황 조회
	public List<Map<String, Object>> selectEnfsnInfo(Map<String, ?> paramMap) throws Exception;
	// 센터 종사자 국가자격증현황 조회
	public List<Map<String, Object>> selectEnfsnCerti(Map<String, ?> paramMap) throws Exception;
	// 센터 종사자 청소년민간자격증현황 조회
	public List<Map<String, Object>> selectEnfsnPrvateCerti(Map<String, ?> paramMap) throws Exception;
	// 센터 종사자 전문인력양성교육현황 조회
	public List<Map<String, Object>> selectTrnngEdu(Map<String, ?> paramMap) throws Exception;
}
