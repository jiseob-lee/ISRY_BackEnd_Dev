/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
* @Class Name  : EmrgIntrvnMapper.java
* @Description : 긴급개입 Mapper Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 14.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 14.  Kwon.Min.Seo    최초작성
* </pre>
*/
@Mapper("emrgIntrvnMapper")
public interface EmrgIntrvnMapper {

	/**
	 * @Method명   : selectEmrgIntrvnDetail
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입 상세조회
	 */
	public List<Map<String, Object>> selectEmrgIntrvnDetail(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertEmrgIntrvn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입 등록
	 */
	public Integer insertEmrgIntrvn(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertEmrgIntrvnHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입이력 등록
	 */
	public Integer insertEmrgIntrvnHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateEmrgIntrvn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입수정
	 */
	public Integer updateEmrgIntrvn(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteEmrgIntrvn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입 삭제
	 */
	public Integer deleteEmrgIntrvn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method명   : selectEmrgIntrvnActnMatter
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 목록
	 */
	public List<Map<String, Object>> selectEmrgIntrvnActnMatter(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertEmrgIntrvnActnMatter
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 등록
	 */
	public Integer insertEmrgIntrvnActnMatter(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertEmrgIntrvnActnMatterHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 이력 등록
	 */
	public Integer insertEmrgIntrvnActnMatterHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateEmrgIntrvnActnMatter
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 수정
	 */
	public Integer updateEmrgIntrvnActnMatter(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteEmrgIntrvnActnMatter
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 삭제
	 */
	public Integer deleteEmrgIntrvnActnMatter(Map<String, String> paramMap) throws Exception;
}
