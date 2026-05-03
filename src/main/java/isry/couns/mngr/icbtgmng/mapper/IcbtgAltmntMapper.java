package isry.couns.mngr.icbtgmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("icbtgAltmntMapper")
public interface IcbtgAltmntMapper{
	
	List<Map<String, Object>> selectIcbtgAltmntList(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method     : insertIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 배정표 등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01.
	 * @상세	   : IcbtgConsttChcMapper(작성자 : 유영태) → IcbtgAltmntMapper로 복사 
 	 */
	public int insertIcbtgAltmnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 배정표 상세 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
 	 */
	public int updateIcbtgAltmnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 배정표 상세 삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
 	 */
	public int deleteIcbtgAltmnt(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method명   : selectIcbtgAltmntYmdCheckList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Min.Seong
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 : 인큐베이팅 관리 중복된 날짜를 조회
	 */
	List<Map<String, Object>> selectIcbtgAltmntYmdCheckList(Map<String, String> mapParam) throws Exception;
	
	//int insertIcbtgConsttChc(Map<String, Object> mapParam) throws Exception;
	
}
