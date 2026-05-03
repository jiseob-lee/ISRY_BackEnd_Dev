/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : BannerMngMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 8. 25. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 8. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("bannerMngMapper")
public interface BannerMngMapper {
	
	List<Map<String, Object>> selectBannerMngList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBannerMngDetail(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method     	: insertBannerMng
	 * @Method설명 	: 배너 등록
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 8. 24.
	 * @상세       	: 
 	 */
	public int insertBannerMng(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     	: deleteBannerMng
	 * @Method설명 	: 배너 상태(삭제) 업데이트
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 04.
	 * @상세       	: 
 	 */
	public int deleteBannerMng(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     	: updateBannerMng
	 * @Method설명 	: 배너 수정
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 04.
	 * @상세       	: 
 	 */
	public int updateBannerMng(Map<String, String> paramMap) throws Exception;
	
}
