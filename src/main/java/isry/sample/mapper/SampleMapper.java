/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.sample.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : SampleMapper.java
 * @프로그램 설명 :
 * - Sample 서비스를 위한 Mapper 입니다.  
 * @작성자        : Song.Young.Il
 * @작성일        : 2021. 11. 11. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2021. 11. 11.
 * @수정내용      : 
 * -                
 * -
 */
@Mapper("sampleMapper")
public interface SampleMapper  {

	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSample(Map<String, String> mapParam) ;

	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	void insertSample(Map<String, String> map);
	
	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	void updateSample(Map<String, String> map);
	
	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	void deleteSample(Map<String, String> map);
	
	
	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSampleRegFee(Map<String, String> mapParam);

	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	void insertSampleRegFee(Map<String, String> map);

	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	void updateSampleRegFee(Map<String, String> map);
	
	/**
	 *  
	 * @Method명   : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2021. 11. 11. 
	 * @Method설명 :
	 */
	void deleteSampleRegFee(Map<String, String> map);


}
