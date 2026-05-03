/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.wrksupt.docsr.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : DocsrMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 20.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 20.
 * @수정내용 : - -
 */
@Mapper("docsrMapper")
public interface DocsrMapper {

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
	 * @Method명 : selectRcvrInqDocListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : TAESOO. Song
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
	 * @Method명 : selectInqDocList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 목록 조회
	 */
	public List<Map<String, Object>> selectRcvrInqDocList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : insertInqDoc
	 * @param dmSaveMap
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송 (문서수발신내용 SBA200)
	 */
	public int insertInqDoc(Map<String, Object> dmSaveMap) throws Exception;

	/**
	 * @Method명 : insertInqDoc2
	 * @param dmSaveMap
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송 (문서수발신 SBA210)
	 */
	public void insertInqDoc2(Map<String, Object> dmSaveMap) throws Exception;

	/**
	 * @Method명 : deleteInqDoc
	 * @param mapDel
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @수정자 : TAESOO SONG
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(삭제)
	 */
	public void deleteInqDoc(Map<String, String> mapDel) throws Exception;

	/**
	 * @Method명 : updateInqDoc
	 * @param INO_DOC_ESNTAL_NO
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	public void updateInqDoc(Map<String, Object> dmUpdateMap) throws Exception;

	/**
	 * @Method명 : insertInqDocCabinet
	 * @param INO_DOC_ESNTAL_NO
	 * @throws Exception
	 * @작성자 : Taesoo Song
	 * @작성일 : 2022. 5. 19.
	 * @Method설명 : 보관함 저장 처리를 진행한다.
	 */
	public void insertInqDocCabinet(Map<String, Object> dmUpdateMap) throws Exception;

	/**
	 * @Method명 : updatePrslInqDoc
	 * @param INO_DOC_ESNTAL_NO
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	public void updatePrslInqDoc(Map<String, Object> dmUpdateMap) throws Exception;
	
	/**
	 * @Method명   : onLoadselectDsgDocRcvr
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 :
	 */
	public void onLoadselectDsgDocRcvr() throws Exception;
	
	/**
	 * @Method명   : selectOrgDept
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectOrgDept(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectOrgDept
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : TAESOO Song
	 * @작성일     : 2022. 5. 13. 
	 * @Method설명 :
	 */
	public int selectDsptchInstNo(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method명   : selectOrgDept
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : TAESOO Song
	 * @작성일     : 2022. 5. 13. 
	 * @Method설명 :
	 */
	public int selectRcvrDocumentMatch(Map<String, Object> map) throws Exception;
	
	public void deleteInqDocumet(Map<String, Object> map) throws Exception;
	
	public Integer selectDocsCommonListTotalCount(Map<String, Object> map) throws Exception;

	public Integer selectDocsRcvrCommonListTotalCount(Map<String, Object> map) throws Exception;
	
	public Integer selectDocFileCabinetListTotalCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectDocsCommonList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectDocsRcvrCommonList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectDocFileCabinetList(Map<String, Object> map) throws Exception;
	
	public void insertCommonDoc(Map<String, Object> map) throws Exception;
	
	public void updateDocsCount(Map<String, Object> map) throws Exception;

	public void insertCommonDocDetail(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectDocsDetail(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectDocsOrigineDetail(Map<String, Object> map) throws Exception;
	
	public void updateCommonDoc(Map<String, Object> map) throws Exception;
	
	public void deleteRcvrId(Map<String, Object> map) throws Exception;
	
	public int selectDocsNocsCnt(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectDocsCstdyDetail(Map<String, Object> map) throws Exception;
	
	public int selectListDocsRcvrUsrListTotalCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectListDocsRcvrUsrList(Map<String, Object> map) throws Exception;
	
	public void deleteCstdyDoc(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectBizList(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectBizExcuteList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectBizUsrList(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getUserInstInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectEnfsnInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectSndptInfo(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectSndptyUserInfo(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectDocsInstInfo(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectDocsDsptchInstInfo(Map<String, Object> map) throws Exception;
	
	public void insertInstInfo(Map<String, Object> map) throws Exception;
	
	public void updateInstInfo(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectInnerEmlRcptnList
	 * @param dmSearchMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectInnerEmlRcptnList(Map<String, Object> dmSearchMap) throws Exception;

	/**
	 * @Method명   : selectInnerEmlDsptchList
	 * @param dmSearchMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectInnerEmlDsptchList(Map<String, Object> dmSearchMap) throws Exception;

	/**
	 * @Method명   : insertInqDoc3
	 * @param dmSaveMap3
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	public void insertInqDoc3(Map<String, Object> dmSaveMap3) throws Exception;

	/**
	 * @Method명   : selectListDocsRcvrInstListTotalCount
	 * @param paramMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	public Integer selectListDocsRcvrInstListTotalCount(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명   : selectListDocsRcvrInstList
	 * @param paramMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectListDocsRcvrInstList(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명   : selectRcptnInstRcvrDocumentMatch
	 * @param dmUpdateMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 :
	 */
	public int selectRcptnInstRcvrDocumentMatch(Map<String, Object> dmUpdateMap) throws Exception;

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
	 * @param offcsInfoMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 12. 
	 * @Method설명 :
	 */
	public Map<String, Object> selectOffcsAtfinoInfo(Map<String, Object> offcsInfoMap) throws Exception;

	/**
	 * @Method명   : selectOffcsAtfinoPath
	 * @param param
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2023. 1. 12. 
	 * @Method설명 :
	 */
	public Map<String, Object> selectOffcsAtfinoPath(Map<String, Object> param) throws Exception;
}
