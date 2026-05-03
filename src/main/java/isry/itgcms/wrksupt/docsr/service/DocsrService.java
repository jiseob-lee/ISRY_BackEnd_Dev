/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.wrksupt.docsr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : DocsrService.java
 * @프로그램 설명 : 문서수발신 조회 및 발송을 관리하는 Service
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 20.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 20.
 * @수정내용 : - -
 */
public interface DocsrService {

	/**
	 * @Method명 : selectInqDocListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 목록 totalCount조회
	 */
	public Integer selectInqDocListTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectInqDocListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : TAESOO. SONG
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수신함 목록 totalCount조회
	 */
	public Integer selectRcvrInqDocListTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectInqDocList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 목록 조회
	 */
	public List<Map<String, Object>> selectInqDocList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectRcvrInqDocList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Taesoo Song
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 문서 수발신함 목록 조회
	 */
	public List<Map<String, Object>> selectRcvrInqDocList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : saveInqDoc
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(CUD)
	 */
	public void saveInqDoc(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : insertInqDoc
	 * @param dmSaveMap
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	public void insertInqDoc(Map<String, Object> dmSaveMap) throws Exception;

	/**
	 * @Method명 : updateInqDoc
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	public void updateInqDoc(Map<String, Object> dmUpdateMap) throws Exception;

	/**
	 * @Method명 : updatePrslInqDoc
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	public void updatePrslInqDoc(Map<String, Object> dmUpdateMap) throws Exception;
	
	public void onLoadselectDsgDocRcvr() throws Exception;
	
	public List<Map<String, Object>> selectOrgDept(DataRequest dataRequest) throws Exception;
	
	public void deleteInqDoc(Map<String, Object> dmUpdateMap) throws Exception;
	
	public Map<String, Object> selectDocsCommonList(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> dmSearchMap) throws Exception;
	
	public void executeDocMySelf(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> dmSearchMap) throws Exception;
	
	public Map<String, Object> selectListDocsDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> excuteCabinetDoc(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectListDocsCstdyDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectListDocsRcvrUsrList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> deleteDocsData(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectBizList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectBizExcuteList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectBizUsrList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> getUserInstInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectSndptyUserInfo(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectDocsInstInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectDocsDsptchInstInfo(Map<String, Object> map) throws Exception;
	
	public void insertInstInfo(Map<String, Object> map) throws Exception;
	
	public void updateInstInfo(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectListDocsRcvrInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9.  throws Exception;
	 * @Method설명 :
	 */
	public Map<String, Object> selectListDocsRcvrInstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : updateRcptnInstPrslInqDoc
	 * @param dmUpdateMap
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	public void updateRcptnInstPrslInqDoc(Map<String, Object> dmUpdateMap) throws Exception;

	/**
	 * @Method명   : selectOffcsAtfinoInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 12. 
	 * @Method설명 :
	 */
	public Map<String, Object> selectOffcsAtfinoInfo(HttpServletRequest request, DataRequest dataRequest, Map<String, String> dmDocDtl) throws Exception;
}
