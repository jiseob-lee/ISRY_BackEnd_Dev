/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.file.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

/**
 * @파일명        : MgmtFileService.java
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
public interface MgmtFileService {

	public List<Map<String, Object>> selectCmnFileList(Map<String, String> mapParam) throws Exception;

	public Map<String, Object> selectCmnFile(Map<String, String> mapParam) throws Exception;

	public List<Map<String, Object>> selectCmnAllFileList(Map<String, String> mapParam) throws Exception;

	public List<Map<String, Object>> selectWebCmnAllFileList(Map<String, String> mapParam) throws Exception;

	public Map<String, String> uploadCmnFile(HttpServletRequest request, DataRequest dataRequest, String strFileBasePath) throws IOException, Exception;
	
	public List<Map<String, String>> uploadCmnFileSeperate(HttpServletRequest request, DataRequest dataRequest, String strFileBasePath) throws IOException, Exception;

	public int deleteCmnFile(ParameterGroup dsFile, String strFileBasePath) throws Exception;

	public int deleteCmnFileWeb(ParameterGroup dsFile, String strFileBasePath) throws Exception;

	public int deleteCmnFileByAttcFileNo(Map<String, String> mapParam, String strFileBasePath) throws Exception;

	public int deleteCmnFileByAttcFileNoWeb(Map<String, String> mapParam, String strFileBasePath) throws Exception;
	
	public void imageUploadCmnFile(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, String strFileBasePath) throws IOException, Exception;
	
	public List<Map<String, String>> gridFileUpload(HttpServletRequest request, DataRequest dataRequest, Map<String, String> map, String strFileBasePath) throws IOException, Exception;

	// 파일 다운로드 이력 기록
	public void saveFileDownloadHistory(HttpServletRequest request, Map<String, Object> fileMap, String basePath) throws Exception;

	// 파일 다운로드 이력 기록
	public void saveFileDownloadAllHistory(HttpServletRequest request, List<Map<String, Object>> fileList, String basePath) throws Exception;

	/**
	 * @Method명   : selectWebCmnFileList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 직인관리용
	 */
	public List<Map<String, Object>> selectWebCmnFileList(Map<String, String> mapParam) throws Exception;
	
	public Integer selectFileDownloadHistoryTotalCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectFileDownloadHistoryList(Map<String, Object> dmSearchMap) throws Exception;
	
}
