/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SurvshtCmmnsInqMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 12. 7. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 12. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("survshtCmmnsInqMapper")
public interface SurvshtCmmnsInqMapper {

	/**
	 * @Method명   : searchQustnbTmptUseYn
	 * @param searchMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 7. 
	 * @Method설명 :
	 */
	Map<String, Object> searchQustnbTmptUseYn(Map<String, Object> searchMap);

}
