/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.file.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtFileMapper.java
 * @프로그램 설명 : 첨부 파일 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 30. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 30.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mgmtFileMapper")
public interface MgmtFileMapper {

	public List<Map<String, Object>> selectCmnFileList(Map<String, String> mapParam) throws Exception;

	public List<Map<String, Object>> selectCmnAllFileList(Map<String, String> mapParam) throws Exception;

	public List<Map<String, Object>> selectWebCmnAllFileList(Map<String, String> mapParam) throws Exception;

	public int deleteCmnAttcFile(Map<String, Object> file) throws Exception;

	public int deleteCmnAttcFileWeb(Map<String, Object> file) throws Exception;

	public int insertCmnFile1(Map<String, String> mapParam) throws Exception;
	public int insertCmnFile2(Map<String, String> mapParam) throws Exception;
	public int insertCmnFile3(Map<String, String> mapParam) throws Exception;
	
	public Integer selectAttcFileNo() throws Exception;
	
	public String selectUnitTaskWork(Integer menuNo) throws Exception;
	
	// 웹서버 첨부파일 시퀀스 구하기
	public Integer selectAttcFileNoImage() throws Exception;

	// 파일 다운로드 이력 기록
	public void saveFileDownloadHistory(Map<String, Object> fileMap) throws Exception;

	/**
	 * @Method명   : selectWebCmnFileList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectWebCmnFileList(Map<String, String> mapParam);

	// 이메일 첨부파일 정보를 가져온다.
	public List<Map<String, Object>> selectEmailAttachList(List<String> contentList) throws Exception;

	public Integer selectFileDownloadHistoryTotalCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectFileDownloadHistoryList(Map<String, Object> dmSearchMap) throws Exception;
	
}
