package isry2.couns.mngr.atrzmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("vacAprvMng2Mapper")
public interface VacAprvMng2Mapper{
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboBoxVac(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectVacAprvMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectVacAprvMngDetail(Map<String, Object> mapParam) throws Exception;
	
	int updateVacAprvMngBatch(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectVacAprvBatchList
	 * @Method설명 : 일괄승인할 기간에 해당하는 List 조회
	 * @param      : mapParam
	 * @return	   : List
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 10. 25. 
	 */
	List<Map<String, Object>> selectVacAprvBatchList(Map<String, String> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateVacAprvBatch
	 * @Method설명 : selectVacAprvBatchList을 통해 조회된 휴가신청 데이터의 승인상태구분코드를 '대기' → '승인'으로 변경
	 * @param 	   : mapParam
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 10. 26. 
	 */
	int updateVacAprvBatch(Map<String, Object> mapParam) throws Exception;
	
	int deleteVacAprvMng(Map<String, Object> mapParam) throws Exception;
	
	int updateVacAprvMng(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectVacAprvMngSms(Map<String, String> mapParam) throws Exception;
	
	int insertVacAprvMngSms1(Map<String, Object> mapParam) throws Exception;
	
	int insertVacAprvMngSms2(Map<String, Object> mapParam) throws Exception;
	
	int insertVacAprvMngSms3(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectVacAprvMngGwCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 30. 
	 * @Method설명 : 휴가일정 건수 조회
	 * <pre>
	 * 	- 원본 테이블 : 청소년사이버상담 휴가승인정보 (CAC400)
	 * 	- 목적 테이블 : 사이버1388휴가 (AYC130)
	 * </pre>
	 */
	Integer selectVacAprvMngGwCount(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertVacAprvMngGw
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 27. 
	 * @Method설명 : 휴가일정 가져오기 일괄 등록
	 * <pre>
	 * 	- 원본 테이블 : 청소년사이버상담 휴가승인정보 (CAC400)
	 * 	- 목적 테이블 : 사이버1388휴가 (AYC130)
	 * </pre>
	 */
	int insertVacAprvMngGw(Map<String, Object> mapParam) throws Exception;
}
