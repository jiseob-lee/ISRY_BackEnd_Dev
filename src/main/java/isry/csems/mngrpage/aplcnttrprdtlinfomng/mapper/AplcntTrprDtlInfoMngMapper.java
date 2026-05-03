/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.mngrpage.aplcnttrprdtlinfomng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : AplcntTrprDtlInfoMngMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 5. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("csemsMngrPageAplcntTrprDtlInfoMngMapper")
public interface AplcntTrprDtlInfoMngMapper {

	/**
	 * @Method명   : selectPtcptReqstdAplcntPop
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectPtcptReqstdAplcntPop(Map<String, Object> dtlMap) throws Exception;

	/**
	 * @Method명   : insertCompnoChcTypeMngNo
	 * @param sCompnoPK
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void insertCompnoChcTypeMngNo(Map<String, String> sCompnoPK) throws Exception;

	/**
	 * @Method명   : deleteCompnoChcTypeMngNoAFA131
	 * @param delMap
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void deleteCompnoChcTypeMngNoAFA131(Map<String, String> delMap) throws Exception;

	/**
	 * @Method명   : insertCompnoChcTypeDtl
	 * @param compnoKey
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void insertCompnoChcTypeDtl(Map<String, String> compnoKey) throws Exception;

	/**
	 * @Method명   : updatePtcptReqstdAplcntPop
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void updatePtcptReqstdAplcntPop(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : insertPtcptReqstdAplcntPop
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void insertPtcptReqstdAplcntPop(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : insertPtcptReqstdAplcntPopHstr
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void insertPtcptReqstdAplcntPopHstr(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : selectAdhrncWrtcns
	 * @param dtlMap
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAdhrncWrtcns(Map<String, String> dtlMap) throws Exception;

	/**
	 * @Method명   : insertAdhrncWrtcns
	 * @param retMap
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void insertAdhrncWrtcns(Map<String, String> retMap) throws Exception;

	/**
	 * @Method명   : updateAdhrncWrtcns
	 * @param retMap
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 2. 
	 * @Method설명 :
	 */
	void updateAdhrncWrtcns(Map<String, String> retMap) throws Exception;

	/**
	 * @Method명   : selectAdhrncWrtcnsChck
	 * @param paramMap
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 :
	 */
	int selectAdhrncWrtcnsChck(Map<String, String> paramMap);

	
	/**
	 * @Method명   : chkCreateQustnbMngNoYn
	 * @param map
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	List<Map<String, String>> chkCreateQustnbMngNoYn(Map<String, String> map);

	/**
	 * @Method명   : insertQustnbTrprInfo
	 * @param param
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	void insertQustnbTrprInfo(Map<String, Object> param);

	/**
	 * @Method명   : insertQustnbSndngHstr
	 * @param param
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	void insertQustnbSndngHstr(Map<String, Object> param);

	/**
	 * @Method명   : srvyWrtStts
	 * @param map
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	Map<String, String> srvyWrtStts(Map<String, String> map);

	/**
	 * @Method명   : selectUntTaskwkSeCd
	 * @param map
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 :
	 */
	String selectUntTaskwkSeCd(Map<String, String> map);

}
