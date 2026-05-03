package isry.itgcms.itgBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("itgBrdCmnMapper")
public interface ItgBrdCmnMapper {

	/**
	 * @Method명 : getTotalCount
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	int getTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectItgCmnBrdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgCmnBrdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtgrySeCdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : insertItgBrdCmnList
	 * @param mapIns
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	void insertItgBrdCmnList(Map<String, String> mapIns) throws Exception;

	/**
	 * @Method명 : updateItgBrdCmnList
	 * @param mapUpd
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	void updateItgBrdCmnList(Map<String, String> mapUpd) throws Exception;

	/**
	 * @Method명 : deleteItgBrdCmnList
	 * @param mapDel
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	void deleteItgBrdCmnList(Map<String, String> mapDel) throws Exception;

	/**
	 * @Method명 : selectInstCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
//	List<Map<String, Object>> selectInstCodeList(String unitCode) throws Exception;

	/**
	 * @Method명 : selectInstCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectInstCode(Integer instNo) throws Exception;
	
	/**
	 * @Method명 : selectItgBrdDtlList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgBrdDtlList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : updateRdcntItgBrdDtlList
	 * @param mapParam
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
	void updateRdcntItgBrdDtlList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : deleteItgNtcBrd
	 * @param mapDel
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 6.
	 * @Method설명 :
	 */
	void deleteItgNtcBrd(Map<String, String> mapDel) throws Exception;

	/**
	 * @Method명 : insertItgBrdCmnTaskSysSeCdList
	 * @param mapIns
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	void insertItgBrdCmnTaskSysSeCdList(Map<String, String> mapIns) throws Exception;

	/**
	 * @Method명 : deleteItgBrdCmnTaskSysSeCdList
	 * @param mapReturn
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	void deleteItgBrdCmnTaskSysSeCdList(Map<String, Object> mapReturn) throws Exception;

	/**
	 * @Method명 : selectItgBrdDtlTaskSysCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgBrdDtlTaskSysCdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectDeptCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDeptCodeList() throws Exception;

	/**
	 * @Method명 : selectAllCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectAllCtgrySeCdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectCtgtybInstList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtgtybInstList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectItgCmnBrdImprtnList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgCmnBrdImprtnList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectCtpvSggCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtpvSggCodeList() throws Exception;

	/**
	 * @Method명 : selectCtpvCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtpvCodeList() throws Exception;

	/**
	 * @Method명 : selectSggCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSggCodeList() throws Exception;

	/**
	 * @Method명   : selectSysCtgtybInstList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSysCtgtybInstList(Map<String, Object> mapParam) throws Exception;

}
