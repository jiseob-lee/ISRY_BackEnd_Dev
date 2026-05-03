/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.protocol.data.UploadFile;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.FileUtil;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import egovframework.com.cmm.fileupload.FileUploadPolicy;
import egovframework.com.cmm.privacy.UploadAPICaller;
import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.syscmmn.nas.service.NasSyncService;
import isry.itgcms.sysmgmt.file.mapper.MgmtFileMapper;
import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : MgmtFileServiceImpl.java
 * @프로그램 설명 : 첨부 파일 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 30. 
 * @수정자        : HAN CHANG HUN
 * @수정일        : 2022. 06. 28.
 * @수정내용      : 개인정보 API 호출. 파일명 확장자 저장.
 * -                
 * -                
 */
@Service("mgmtFileService")
public class MgmtFileServiceImpl extends IsryBaseServiceImpl implements MgmtFileService {

	private String strServiceNamePath = EgovProperties.getProperty("globals", "isry.globals.service.name");

	private String strWasFileBasePath = EgovProperties.getProperty("globals", "isry.globals.wasupload.file.folder");
	private String strWebFileBasePath = EgovProperties.getProperty("globals", "isry.globals.webupload.file.folder");
	
	@Resource(name = "mgmtFileMapper")
	private MgmtFileMapper mgmtFileMapper;
	
    @Resource(name = "fileUploadPolicy")
    protected FileUploadPolicy fup;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name="nasSyncService")
	private NasSyncService nasSyncService;
	
	@Override
	public List<Map<String, Object>> selectCmnFileList(Map<String, String> mapParam) throws Exception {
		return mgmtFileMapper.selectCmnFileList(mapParam);
	}
	
	/**
	 * 
	 * @Method명   : selectWebCmnFileList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 직인관리용
	 */
	@Override
	public List<Map<String, Object>> selectWebCmnFileList(Map<String, String> mapParam) throws Exception {
		return mgmtFileMapper.selectWebCmnFileList(mapParam);
	}

	@Override
	public Map<String, Object> selectCmnFile(Map<String, String> mapParam) throws Exception {
		return (Map<String, Object>) mgmtFileMapper.selectCmnFileList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectCmnAllFileList(Map<String, String> mapParam) throws Exception {
		return mgmtFileMapper.selectCmnAllFileList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectWebCmnAllFileList(Map<String, String> mapParam) throws Exception {
		return mgmtFileMapper.selectWebCmnAllFileList(mapParam);
	}

	@Override
	public Map<String, String> uploadCmnFile(HttpServletRequest request, DataRequest dataRequest, String strFileBasePath) throws Exception {

		ParameterGroup dsFile = dataRequest.getParameterGroup("dsFile");
		
		Iterator<ParameterRow> deletedRows = dsFile == null ? null : dsFile.getDeletedRows();

		//log.debug("#### deleteCmnFile.");
		
		Map<String, String> param1 = null;
		if (deletedRows != null) {
			while (deletedRows.hasNext()) {
				param1 = deletedRows.next().toMap();
				//log.debug("#### ATFINO : " + param1.get("ATFINO"));
				//log.debug("#### MNG_SN : " + param1.get("MNG_SN"));
				deleteCmnFileByAttcFileNo(param1, strFileBasePath);
			}
		}

		//String strPgmId = dataRequest.getString(ProcessConstants.INTER_PGM_ID); //프로그램ID
		//String strMenuId = dataRequest.getString(ProcessConstants.INTER_MENU_ID); //메뉴ID
		//String strUserDefinePgmId = StringUtil.fixNull(dataRequest.getString("strUserDefinePgmId"));	// 부모 프로그램ID 가 아닌 사용자 정의한 프로그램ID
		//String strAppUserId = authentication.getUserId();	//사용자ID
		
		//String[] params = dataRequest.getParameterNames();
		//for (int i=0; i < params.length; i++) {
			//log.debug("#### " + i + " : " + params[i]);
		//}
		
		//log.debug("#### _AUTH_APP_ID : " + dataRequest.getParameter("_AUTH_APP_ID"));
		//log.debug("#### _AUTH_MENU_NO : " + dataRequest.getParameter("_AUTH_MENU_NO"));
		
		String authAppId = dataRequest.getParameter("_AUTH_APP_ID") == null ? "" : dataRequest.getParameter("_AUTH_APP_ID");
		Integer authMenuNo = dataRequest.getParameter("_AUTH_MENU_NO") == null || "".equals(dataRequest.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(dataRequest.getParameter("_AUTH_MENU_NO"));
		
		//log.debug("#### authAppId : " + authAppId);
		//log.debug("#### authMenuNo : " + authMenuNo);
		
		//String strPgmId = "TMP";
		//strPgmId = appId;
		
		String strAppUserId = "";

		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			strAppUserId = userDetailsVO.getId();
		}
		
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		// 프로그램별 파일업로드 경로 (uri 및 메뉴별 파일 저장도 별도 지정 필요)
		if ("".equals(StringUtil.fixNull(strFileBasePath))) {
			
			// 첨부파일을 저장할 저장소 경로가 존재하지 않습니다.
			throw new AppWorksException("첨부파일을 저장할 저장소 경로가 존재하지 않습니다.");
			
		}
		
		String unitTaskWork = mgmtFileMapper.selectUnitTaskWork(authMenuNo);
		if (unitTaskWork == null || "".equals(unitTaskWork)) {
			unitTaskWork = "ETC";
		}
		
		String subFolder = "MAIN";
		if ("app/exam/demo/itgBrd/itgNtcBrd/itgNtcBrdList.clx".equals(authAppId)) {
			subFolder = "NOTICE";  // 공지사항
		} else if ("app/exam/demo/itgBrd/itgLibBrd/itgLibBrdList.clx".equals(authAppId)) {
			subFolder = "REFERENCE";  // 자료실
		} else if ("app/exam/demo/itgBrd/itgRgnNtcBrd/itgRgnNtcBrdList.clx".equals(authAppId)) {
			subFolder = "REGION_SHARE";  // 지역별 공지 게시판
		} else if ("app/exam/demo/itgBrd/itgRgnLibBrd/itgRgnLibBrdList.clx".equals(authAppId)) {
			subFolder = "REGION_DATA";  // 지역별 자료 게시판
		} else if ("app/exam/demo/itgBrd/itgFaqBrd/itgFaqBrdList.clx".equals(authAppId)) {
			subFolder = "FAQ";  // FAQ
		} else if ("app/exam/demo/itgBrd/itgQnaBrd/itgQnaBrdList.clx".equals(authAppId)) {
			subFolder = "QNA";  // QnA
		}

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String strFileUploadPath = strServiceNamePath + File.separator + unitTaskWork + File.separator + subFolder 
				+ File.separator + Integer.valueOf(year).toString() 
				+ File.separator + Integer.valueOf(month).toString() 
				+ File.separator + Integer.valueOf(day).toString() + File.separator;
		String strFileStoreFullPath = strFileBasePath + strFileUploadPath;
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = dmParam == null ? null : StringUtil.fixNull(dmParam.getValue("strAtfino"));  // 첨부파일번호
		String strAtcmflClNm = dmParam == null ? null : StringUtil.fixNull(dmParam.getValue("strAtcmflClNm"));  // 첨부파일구분코드
		
		if (strAtcmflClNm == null || "".equals(strAtcmflClNm)) {
			strAtcmflClNm = "0";
		}
		
		// 첨부번호 없을시 신규 업로드 처리
		//String strFileStatRcd = "CMN101.SAVE";
		String strFileStatRcd = "N";
		
		if (strAttcFileNo == null || "".equals(strAttcFileNo) || "0".equals(strAttcFileNo)) {
			// 랜덤 함수를 이용하여 업로드 처리
			strAttcFileNo = getRandomString(20);
			log.info("#### strAttcFileNo created.");
		}
		log.info("#### strAttcFileNo : " + strAttcFileNo);
		
		
		Map<String, String> param = new HashMap<String, String>();
		
		Map<String, String> fileInfo = new HashMap<String, String>();
		
		Map<String, UploadFile[]> uploadFiles = dataRequest.getUploadFiles();


		int iFileCnt = 0;
		
		if (uploadFiles != null && uploadFiles.size() > 0) {

			Set<Entry<String, UploadFile[]>> entries = uploadFiles.entrySet();

			// 받은 화일 갯수 만큼 업로드를 실행 한다.
			for (Entry<String, UploadFile[]> entry : entries) {
				
				UploadFile[] uFiles = entry.getValue();
				
				for (UploadFile uFile : uFiles) {

					File file = uFile.getFile();

					String strFileName = uFile.getFileName(); // 파일명

					String strFileSize = Long.toString(file.length()); // 파일 사이즈
					
					String strFileExt = FileUtil.getFileExtNm(strFileName); // 확장자명
					
					String strTempPath = file.getPath(); // 임시 파일업로드 경로
					
			    	//업로드 대상 파일의 정책을 체크하기 위한 Policy Class 생성
			    	fup.init();
			    	
					/* 파일 확장자 등록 여부 검증*/
				    if (fup.accept(strFileName) ) {
				    } else {
					  throw new AppWorksException("파일을 등록 할 수 없습니다.등록가능한 파일인지 확인바랍니다.", Alert.ERROR, FileUtil.getFileExtNm(strFileName));
				    }

					// 보안에 위배되는 파일 확장자 유형인 경우...
//					if(!SecurityWebUtil.securedFileType(strFileName)){
//						//{0} 확장자는 업로드 할 수 없습니다.
//						throw new AppWorksException("CMN003.CMN@CMN021", Alert.ERROR, FileUtil.getFileExtNm(strFileName));
//					}

					
					// 파일명에 대한 암호화
					String strSaveNameFileNm = FileUtil.getEncryptFileNm();
					
					// 파일명은 확장자 추가해서 저장(2022-06-27 수정)
					//strSaveNameFileNm = FileUtil.uploadFile(strFileStorePath, strTempPath, strSaveNameFileNm, true);
					strSaveNameFileNm = FileUtil.uploadFile(strFileStoreFullPath, strTempPath, strSaveNameFileNm+"."+strFileExt, true);
					
					String filePath = strFileStoreFullPath+strSaveNameFileNm;
					log.info("파일명=>"+ strFileStoreFullPath+strSaveNameFileNm);
					
					nasSyncService.uploadFile(strSaveNameFileNm, strFileStoreFullPath);
					/*
					// # 개인정보 필터링(PRIVACY) API 호출
					UploadAPICaller caller = new UploadAPICaller();
					caller.prevacyFile(filePath);
					*/
					// 공통 첨부파일 DB테이블에 저장
					param.clear();

					param.put("ATFINO", strAttcFileNo); // 첨부파일 번호
					param.put("ATCMFL_CL_NM", strAtcmflClNm); // 첨부파일 구분 코드
					param.put("MENU_NO", String.valueOf(authMenuNo)); // 메뉴NO
					param.put("USER_ID", strAppUserId); // 사용자ID
					param.put("REAL_FILE_NM", strFileName); // 업로드 파일명
					param.put("STRG_FILE_NM", strSaveNameFileNm); // 업로드 저장 파일명(암호화된 파일)
					param.put("STRG_COURS_NM", strFileUploadPath); // 업로드 파일 저장경로
					param.put("FILE_EXTN_NM", strFileExt); // 파일 확장자
					param.put("FILE_SZ", strFileSize); // 파일 사이즈
					param.put("FILE_STTS_SE_CD", strFileStatRcd); // 파일 저장상태[CMN101]
					
					log.info("#### strFileBasePath : " + strFileBasePath);
					log.info("#### strWebFileBasePath : " + strWebFileBasePath);
					
					if (strFileBasePath != null && strFileBasePath.equals(strWebFileBasePath)) {
						param.put("FILE_TYPE", "WEB_FILE"); // 웹 서버 첨부파일 지정
					}
					
					// 데이터 저장 SQL문을 실행 한다.
					mgmtFileMapper.insertCmnFile1(param);
					mgmtFileMapper.insertCmnFile2(param);

					iFileCnt++;
					fileInfo.put("strAtfino", strAttcFileNo);
					fileInfo.put("strAtcmflClNm", strAtcmflClNm);
					fileInfo.put("strFileNm", strFileName);
					fileInfo.put("strFileSize", strFileSize);
				}
			}
		}

		fileInfo.put("fileCnt", Integer.toString(iFileCnt));

		return fileInfo;
	}
	

	@Override
	public List<Map<String, String>> uploadCmnFileSeperate(HttpServletRequest request, DataRequest dataRequest, String strFileBasePath) throws Exception {

		//String strPgmId = dataRequest.getString(ProcessConstants.INTER_PGM_ID); //프로그램ID
		//String strMenuId = dataRequest.getString(ProcessConstants.INTER_MENU_ID); //메뉴ID
		//String strUserDefinePgmId = StringUtil.fixNull(dataRequest.getString("strUserDefinePgmId"));	// 부모 프로그램ID 가 아닌 사용자 정의한 프로그램ID
		//String strAppUserId = authentication.getUserId();	//사용자ID

		
		String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		log.debug("#### authMenuNo : " + authMenuNo);
		
		//String strPgmId = "TMP";
		//strPgmId = appId;
		
		String strAppUserId = "";
		
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			strAppUserId = userDetailsVO.getId();
		}
		
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		// 프로그램별 파일업로드 경로 (uri 및 메뉴별 파일 저장도 별도 지정 필요)

		log.debug("#### strFileStorePath : " + strFileBasePath);
		
		if ("".equals(StringUtil.fixNull(strFileBasePath))) {
			
			// 첨부파일을 저장할 저장소 경로가 존재하지 않습니다.
			throw new AppWorksException("첨부파일을 저장할 저장소 경로가 존재하지 않습니다.");
			
		}

		String unitTaskWork = mgmtFileMapper.selectUnitTaskWork(authMenuNo);
		if (unitTaskWork == null || "".equals(unitTaskWork)) {
			unitTaskWork = "ETC";
		}
		
		String subFolder = "MAIN";
		if ("app/exam/demo/itgBrd/itgNtcBrd/itgNtcBrdList.clx".equals(authAppId)) {
			subFolder = "NOTICE";  // 공지사항
		} else if ("app/exam/demo/itgBrd/itgLibBrd/itgLibBrdList.clx".equals(authAppId)) {
			subFolder = "REFERENCE";  // 자료실
		} else if ("app/exam/demo/itgBrd/itgRgnNtcBrd/itgRgnNtcBrdList.clx".equals(authAppId)) {
			subFolder = "REGION_SHARE";  // 지역별 공지 게시판
		} else if ("app/exam/demo/itgBrd/itgRgnLibBrd/itgRgnLibBrdList.clx".equals(authAppId)) {
			subFolder = "REGION_DATA";  // 지역별 자료 게시판
		} else if ("app/exam/demo/itgBrd/itgFaqBrd/itgFaqBrdList.clx".equals(authAppId)) {
			subFolder = "FAQ";  // FAQ
		} else if ("app/exam/demo/itgBrd/itgQnaBrd/itgQnaBrdList.clx".equals(authAppId)) {
			subFolder = "QNA";  // QnA
		}

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String strFileUploadPath = strServiceNamePath + File.separator + unitTaskWork + File.separator + subFolder 
				+ File.separator + Integer.valueOf(year).toString() 
				+ File.separator + Integer.valueOf(month).toString() 
				+ File.separator + Integer.valueOf(day).toString() + File.separator;
		
		String strFileStoreFullPath = strFileBasePath + strFileUploadPath;

		//int year = Calendar.getInstance().get(Calendar.YEAR);
		//strFileStorePath = strFileStorePath + String.valueOf(year) + File.separator;
		
		ParameterGroup dsParam = dataRequest.getParameterGroup("dsAttcFileNo");
		List<Map<String, String>> dsParamList = null;
		if (dsParam != null) {
			dsParamList = dsParam.getAllRowList();
		}
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = dmParam == null ? "" : StringUtil.fixNull(dmParam.getValue("strAttcFileNo"));  // 첨부파일번호
		String strAtcmflClNm = dmParam == null ? "" : StringUtil.fixNull(dmParam.getValue("strAtcmflClNm"));  // 첨부파일구분코드
		if ("".equals(strAtcmflClNm)) {
			strAtcmflClNm = "0";
		}
		
		// 첨부번호 없을시 신규 업로드 처리
		//String strFileStatRcd = "CMN101.SAVE";
		String strFileStatRcd = "N";
		
		Map<String, String> param = new HashMap<String, String>();
		
		List<Map<String, String>> fileInfoList = new ArrayList<>();
		
		Map<String, String> fileInfo = new HashMap<String, String>();
		
		Map<String, UploadFile[]> uploadFiles = dataRequest.getUploadFiles();
		
		if ("".equals(strAttcFileNo) && uploadFiles != null && uploadFiles.size() > 0) {

			// 랜덤 함수를 이용하여 업로드 처리
			strAttcFileNo = getRandomString(20); 
		}



		int iFileCnt = 0;

		
		if (uploadFiles != null && uploadFiles.size() > 0) {

			Set<Entry<String, UploadFile[]>> entries = uploadFiles.entrySet();

			// 받은 화일 갯수 만큼 업로드를 실행 한다.
			for (Entry<String, UploadFile[]> entry : entries) {
				
				
				UploadFile[] uFiles = entry.getValue();
				
				
				for (UploadFile uFile : uFiles) {

				
					if (dsParamList != null) {
						strAttcFileNo = dsParamList.get(iFileCnt).get("attcFileNo");
						if (strAttcFileNo == null || "".equals(strAttcFileNo)) {
							strAttcFileNo = getRandomString(20);
						}
					}
					
					
					File file = uFile.getFile();

					String strFileName = uFile.getFileName(); // 파일명

					String strFileSize = Long.toString(file.length()); // 파일 사이즈
					
					String strFileExt = FileUtil.getFileExtNm(strFileName); // 확장자명
					
					String strTempPath = file.getPath(); // 임시 파일업로드 경로

					// 보안에 위배되는 파일 확장자 유형인 경우...
//					if(!SecurityWebUtil.securedFileType(strFileName)){
//						//{0} 확장자는 업로드 할 수 없습니다.
//						throw new AppWorksException("CMN003.CMN@CMN021", Alert.ERROR, FileUtil.getFileExtNm(strFileName));
//					}

					
					// 파일명에 대한 암호화
					String strSaveNameFileNm = FileUtil.getEncryptFileNm();
					
					log.debug("#### strFileStoreFullPath : " + strFileStoreFullPath);
					log.debug("#### strTempPath : " + strTempPath);
					log.debug("#### strSaveNameFileNm : " + strSaveNameFileNm);
					
					// 파일명은 확장자 추가해서 저장(2022-06-27 수정)
					//strSaveNameFileNm = FileUtil.uploadFile(strFileStorePath, strTempPath, strSaveNameFileNm, true);
					strSaveNameFileNm = FileUtil.uploadFile(strFileStoreFullPath, strTempPath, strSaveNameFileNm+"."+strFileExt, true);
					
					String filePath = strFileStoreFullPath+strSaveNameFileNm;
					log.info("파일명=>"+ strFileStoreFullPath+strSaveNameFileNm);
					/*
					// # 개인정보 필터링(PRIVACY) API 호출
					UploadAPICaller caller = new UploadAPICaller();
					caller.prevacyFile(filePath);
					*/
					nasSyncService.uploadFile(strSaveNameFileNm, strFileStoreFullPath);
					
					// 공통 첨부파일 DB테이블에 저장
					param.clear();

					param.put("ATFINO", strAttcFileNo); // 첨부파일 번호
					param.put("ATCMFL_CL_NM", strAtcmflClNm); // 첨부파일 구분 코드
					param.put("MENU_NO", String.valueOf(authMenuNo)); // 메뉴NO
					param.put("USER_ID", strAppUserId); // 사용자ID
					param.put("REAL_FILE_NM", strFileName); // 업로드 파일명
					param.put("STRG_FILE_NM", strSaveNameFileNm); // 업로드 저장 파일명(암호화된 파일)
					param.put("STRG_COURS_NM", strFileUploadPath); // 업로드 파일 저장경로
					param.put("FILE_EXTN_NM", strFileExt); // 파일 확장자
					param.put("FILE_SZ", strFileSize); // 파일 사이즈
					param.put("FILE_STTS_SE_CD", strFileStatRcd); // 파일 저장상태[CMN101]

					// 데이터 저장 SQL문을 실행 한다.
					mgmtFileMapper.insertCmnFile1(param);
					mgmtFileMapper.insertCmnFile2(param);

					iFileCnt++;
					fileInfo.put("strAtfino", strAttcFileNo);
					fileInfo.put("strAtcmflClNm", strAtcmflClNm);
					fileInfo.put("strFileNm", strFileName);
					fileInfo.put("strFileSize", strFileSize);
					
					fileInfoList.add(fileInfo);
				}
			}
		}

		fileInfo.put("fileCnt", Integer.toString(iFileCnt));

		//return fileInfo;
		return fileInfoList;
	}


	@Override
	public int deleteCmnFile(ParameterGroup dsFile, String strFileBasePath) throws Exception {
		
		Iterator<ParameterRow> deletedRows = dsFile.getDeletedRows();

		log.debug("#### deleteCmnFile.");
		
		int result = 0;
		Map<String, String> param = null;
		while (deletedRows.hasNext()) {
			param = deletedRows.next().toMap();
			log.debug("#### ATFINO : " + param.get("ATFINO"));
			log.debug("#### MNG_SN : " + param.get("MNG_SN"));
			result += deleteCmnFileByAttcFileNo(param, strFileBasePath);
		}

		return result;
	}


	@Override
	public int deleteCmnFileWeb(ParameterGroup dsFile, String strFileBasePath) throws Exception {
		
		Iterator<ParameterRow> deletedRows = dsFile.getDeletedRows();

		log.debug("#### deleteCmnFile.");
		
		int result = 0;
		Map<String, String> param = null;
		while (deletedRows.hasNext()) {
			param = deletedRows.next().toMap();
			log.debug("#### ATFINO : " + param.get("ATFINO"));
			log.debug("#### MNG_SN : " + param.get("MNG_SN"));
			result += deleteCmnFileByAttcFileNoWeb(param, strFileBasePath);
		}

		return result;
	}
	
	@Override
	public int deleteCmnFileByAttcFileNo(Map<String, String> mapParam, String strFileBasePath) throws Exception {
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		int result = 0;

		// 1. 첨부파일 정보 조회
		List<Map<String, Object>> fileList = selectCmnFileList(mapParam);
		
		if (fileList != null) {
			for (Map<String, Object> file : fileList) {
				if (file != null && file.size() > 0) {

					file.put("ATFINO__origin", file.get("ATFINO"));
					
					file.put("ATCMFL_CL_NM__origin", file.get("ATCMFL_CL_NM"));
					
					file.put("MNG_SN__origin", file.get("MNG_SN"));

					// 2. DB 첨부파일 정보 삭제
					if (strFileBasePath.equals(strWasFileBasePath)) {
						result += mgmtFileMapper.deleteCmnAttcFile(file);
					} else if (strFileBasePath.equals(strWebFileBasePath)) {
						result += mgmtFileMapper.deleteCmnAttcFileWeb(file);
					}

					// 3. 스토리지에 있는 실제 파일 삭제
					if (file != null && file.size() > 0) {

						// String strDeleteFilePath = strGlobalFileStorePath;
						String strDeleteFilePath = strFileBasePath;
						strDeleteFilePath += file.get("STRG_COURS_NM");

						if (strDeleteFilePath.indexOf("../") != -1) {
							// 잘못된 첨부파일 경로입니다.(보안상의 이유로 상위폴더에 대한 접근은 불가합니다
							throw new AppWorksException("CMN003.CMN@CMN031", Alert.WARN);
						}

						FileUtil.deleteFile(strDeleteFilePath + file.get("STRG_FILE_NM"));
						
						nasSyncService.deleteFile(strDeleteFilePath, (String) file.get("STRG_FILE_NM"));
					}
				}
			}
		}

		return result;
	}


	@Override
	public int deleteCmnFileByAttcFileNoWeb(Map<String, String> mapParam, String strFileBasePath) throws Exception {
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		int result = 0;

		// 1. 첨부파일 정보 조회
		List<Map<String, Object>> fileList = selectWebCmnFileList(mapParam);
		
		if (fileList != null) {
			for (Map<String, Object> file : fileList) {
				if (file != null && file.size() > 0) {

					file.put("ATFINO__origin", file.get("ATFINO"));
					
					file.put("ATCMFL_CL_NM__origin", file.get("ATCMFL_CL_NM"));
					
					file.put("MNG_SN__origin", file.get("MNG_SN"));

					// 2. DB 첨부파일 정보 삭제
					result += mgmtFileMapper.deleteCmnAttcFileWeb(file);

					// 3. 스토리지에 있는 실제 파일 삭제
					if (file != null && file.size() > 0) {

						// String strDeleteFilePath = strGlobalFileStorePath;
						String strDeleteFilePath = strFileBasePath;
						strDeleteFilePath += file.get("STRG_COURS_NM");

						if (strDeleteFilePath.indexOf("../") != -1) {
							// 잘못된 첨부파일 경로입니다.(보안상의 이유로 상위폴더에 대한 접근은 불가합니다
							throw new AppWorksException("CMN003.CMN@CMN031", Alert.WARN);
						}

						FileUtil.deleteFile(strDeleteFilePath + file.get("STRG_FILE_NM"));
						
						nasSyncService.deleteFile(strDeleteFilePath, (String) file.get("STRG_FILE_NM"));
					}
				}
			}
		}

		return result;
	}
	
	//private static String dummyString = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
	//private static Random random = new Random();

	public String getRandomString(int loopCount) throws Exception {

		//StringBuilder tempBuilder = new StringBuilder(100);
		//int randomInt;
		//char tempChar;

		//for (int loop = 0; loop < loopCount; loop++) {
			//randomInt = random.nextInt(61);
			//tempChar = dummyString.charAt(randomInt);
			//tempBuilder.append(tempChar);
		//}
		//return tempBuilder.toString();

		Integer attchFileNo = null;
		
		attchFileNo = mgmtFileMapper.selectAttcFileNo();
		
		return String.valueOf(attchFileNo);
	}

	/**
	 * @Method명 : image
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @throws IOException
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2021. 12. 27.
	 * @Method설명 :
	 */
	@Override
	public void imageUploadCmnFile(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, String strFileBasePath)
			throws IOException, Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, UploadFile[]> uploadFiles = dataRequest.getUploadFiles();
		//String strBoardStorePath = path + "temp";

		if ("".equals(StringUtil.fixNull(strFileBasePath))) {
			// 첨부파일을 저장할 저장소 경로가 존재하지 않습니다.
			throw new AppWorksException("첨부파일을 저장할 저장소 경로가 존재하지 않습니다.");
			
		}

		Map<String, String> param = new HashMap<String, String>();
		
		String strFileStatRcd = "N";

		String strAppUserId = "";

		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			strAppUserId = userDetailsVO.getId();
		}
		
		String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

		log.debug("#### 2 authAppId : " + authAppId);
		log.debug("#### 2 authMenuNo : " + authMenuNo);
		
		String unitTaskWork = mgmtFileMapper.selectUnitTaskWork(authMenuNo);
		if (unitTaskWork == null || "".equals(unitTaskWork)) {
			unitTaskWork = "ETC";
		}
		
		String subFolder = "MAIN";
		if ("app/exam/demo/itgBrd/itgNtcBrd/itgNtcBrdList.clx".equals(authAppId)) {
			subFolder = "NOTICE";  // 공지사항
		} else if ("app/exam/demo/itgBrd/itgLibBrd/itgLibBrdList.clx".equals(authAppId)) {
			subFolder = "REFERENCE";  // 자료실
		} else if ("app/exam/demo/itgBrd/itgRgnNtcBrd/itgRgnNtcBrdList.clx".equals(authAppId)) {
			subFolder = "REGION_SHARE";  // 지역별 공지 게시판
		} else if ("app/exam/demo/itgBrd/itgRgnLibBrd/itgRgnLibBrdList.clx".equals(authAppId)) {
			subFolder = "REGION_DATA";  // 지역별 자료 게시판
		} else if ("app/exam/demo/itgBrd/itgFaqBrd/itgFaqBrdList.clx".equals(authAppId)) {
			subFolder = "FAQ";  // FAQ
		} else if ("app/exam/demo/itgBrd/itgQnaBrd/itgQnaBrdList.clx".equals(authAppId)) {
			subFolder = "QNA";  // QnA
		}

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String strFileUploadPath = strServiceNamePath + File.separator + unitTaskWork + File.separator + subFolder 
				+ File.separator + Integer.valueOf(year).toString() 
				+ File.separator + Integer.valueOf(month).toString() 
				+ File.separator + Integer.valueOf(day).toString() + File.separator;

		String strFileStoreFullPath = strFileBasePath + strFileUploadPath;
		
		//직인관리일경우 설정을 조금 다르게 함 - 이승재(20230302)
		String uriArr[] = request.getRequestURI().split("/");
		String uri = uriArr[uriArr.length-1];
		
		String strAtcmflClNm = "board_image";
		if(uri.equals("offcsSgnngUpload.do")) {
			ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

			strAtcmflClNm = StringUtil.fixNull(dmParam.getValue("strAtcmflClNm"));  // 첨부파일구분코드
		}
		
		if (uploadFiles != null && uploadFiles.size() > 0) {
			
			Set<Entry<String, UploadFile[]>> entries = uploadFiles.entrySet();
			
			for (Entry<String, UploadFile[]> entry : entries) {
				
				UploadFile[] uFiles = entry.getValue();
				
				for (UploadFile uFile : uFiles) {
					
					File file = uFile.getFile();
					
					String fileName = uFile.getFileName(); // 파일명
					
					String strFileSize = Long.toString(file.length()); // 파일 사이즈
					
					//log.debug("#### fileName : " + fileName);
					
					String strFileExt = FileUtil.getFileExtNm(fileName); // 확장자명
					
					// 파일을 바이트 배열로 변환
					String strTempPath = file.getPath();
					// 이미지를 업로드할 디렉토리를 정해준다
					String strSaveNameFileNm = FileUtil.getEncryptFileNm();
					
					strSaveNameFileNm = FileUtil.uploadFile(strFileStoreFullPath, strTempPath, strSaveNameFileNm + "." + strFileExt, true);
					
					nasSyncService.uploadFile(strSaveNameFileNm, strFileStoreFullPath);
					
					// 실제 파일명, 저장 파일명이 아님.
					//String filename1 = URLEncoder.encode(fileName, "UTF-8");
					//String fileUrl = strFileUploadPath + filename1;
					String fileUrl = strFileUploadPath + strSaveNameFileNm;
					//String contextPath = request.getContextPath();
					
					//if (contextPath.startsWith("/ISRY_BackEnd")) {
						//fileUrl = "/ISRY_BackEnd/upfile/" + fileUrl;
					//} else {
						//fileUrl = "/upfile/" + fileUrl;
					//}
					
					fileUrl = getBaseUrl(request) + "/upfile/" + fileUrl;
					
					map.put("uploaded", 1);
					map.put("filename", fileName);
					map.put("url", fileUrl);

					
					
					// 웹서버 첨부파일 DB 테이블에 저장
					param.clear();

					String strAttcFileNo = String.valueOf(mgmtFileMapper.selectAttcFileNoImage());
					
					param.put("ATFINO", strAttcFileNo); // 첨부파일 번호
					param.put("ATCMFL_CL_NM", strAtcmflClNm); // 첨부파일 구분 코드
					param.put("MENU_NO", String.valueOf(authMenuNo)); // 메뉴NO
					param.put("USER_ID", strAppUserId); // 사용자ID
					param.put("REAL_FILE_NM", fileName); // 업로드 파일명
					param.put("STRG_FILE_NM", strSaveNameFileNm); // 업로드 저장 파일명(암호화된 파일)
					param.put("STRG_COURS_NM", strFileUploadPath); // 업로드 파일 저장경로
					param.put("FILE_EXTN_NM", strFileExt); // 파일 확장자
					param.put("FILE_SZ", strFileSize); // 파일 사이즈
					param.put("FILE_STTS_SE_CD", strFileStatRcd); // 파일 저장상태[CMN101]
					
					param.put("FILE_TYPE", "BOARD_IMAGE"); // 파일 타입
					
					// 데이터 저장 SQL문을 실행 한다.
					mgmtFileMapper.insertCmnFile1(param);
					mgmtFileMapper.insertCmnFile2(param);
				}
			}
			
		}
		if(uri.equals("offcsSgnngUpload.do")) {
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("strAtfino", param.get("ATFINO"));
			returnMap.put("strAtcmflClNm", param.get("ATCMFL_CL_NM"));
			returnMap.put("strFileNm", param.get("REAL_FILE_NM"));
			returnMap.put("strFileSize", param.get("FILE_SZ"));
			
			dataRequest.setResponse("dmUpload", returnMap);
			
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writeValue(response.getWriter(), map);
			} catch (IOException ex) {
				log.debug(ex.getMessage());
			}
		}
	}
	
	/**
	 * @Method명 : gridFileUpload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @throws IOException
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2023. 01. 30.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> gridFileUpload(HttpServletRequest request, DataRequest dataRequest, Map<String, String> rowMap, String strFileBasePath)
			throws IOException, Exception {
		ParameterGroup dmFile = dataRequest.getParameterGroup("dmFile");
		
		String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		String strAppUserId = "";
		
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			strAppUserId = userDetailsVO.getId();
		}
		
		if ("".equals(StringUtil.fixNull(strFileBasePath))) {			
			// 첨부파일을 저장할 저장소 경로가 존재하지 않습니다.
			throw new AppWorksException("첨부파일을 저장할 저장소 경로가 존재하지 않습니다.");
			
		}

		String unitTaskWork = mgmtFileMapper.selectUnitTaskWork(authMenuNo);
		if (unitTaskWork == null || "".equals(unitTaskWork)) {
			unitTaskWork = "ETC";
		}
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		//String strFileUploadPath = strServiceNamePath + File.separator + unitTaskWork + File.separator + "MAIN"
		String strFileUploadPath = "doc" + File.separator + "manual"  
				+ File.separator + Integer.valueOf(year).toString() 
				+ File.separator + Integer.valueOf(month).toString() 
				+ File.separator + Integer.valueOf(day).toString() + File.separator;
		String strFileStoreFullPath = strFileBasePath + strFileUploadPath;
		
		List<String> fileIdCols = new ArrayList<String>();
		List<Map<String, String>> fileInfoList = new ArrayList<Map<String, String>>();

		for(String name : dataRequest.getParameterNames()) {
			if(name.contains("c@")) {
				fileIdCols.add(name.replaceAll("c@", ""));
			}
		}

		for(String fileIdCol : fileIdCols) {			
			String keyCol = dmFile.getValue("KEY_COLUMN");		 // 테이블의 유니크 키값
			String keyVal = "f"+fileIdCol+"@" + (String) rowMap.get(keyCol);	 // 화면에서 올린 파일명
			String fileId = (String) rowMap.get(fileIdCol);		 // 파일 ID 값
			
			if(!"undefined".equals(dataRequest.getParameter(keyVal))) {
				
				Map<String, String> fileInfo = new HashMap<String, String>();
				fileInfo.put("FILE_ID_COLUMN", fileIdCol);
				fileInfo.put("ATFINO", fileId);

				/* 파일 삭제 로직(우선 변경된 사항에 대해 우선삭제) */
				String[] deleteFileParam = dataRequest.getParameterNames();
				List<String> delParamList = new ArrayList<>(Arrays.asList(deleteFileParam));

				if (delParamList.contains(keyVal)) {
					Map<String, String> mapParam = new HashMap<String, String>();
					mapParam.put("ATFINO", fileId);

					List<Map<String, Object>> list = selectWebCmnFileList(mapParam);

					if (list.size() > 0) {						
						deleteCmnFileByAttcFileNo(mapParam, strFileBasePath);
					}
				}

				/* 파일 저장, 수정 로직 */
				Map<String, UploadFile[]> uploadFiles = dataRequest.getUploadFiles();

				if (uploadFiles != null && uploadFiles.size() > 0) {

					UploadFile[] uFiles = uploadFiles.get(keyVal);

					if (uFiles != null) {

						Map<String, String> mapParam = new HashMap<String, String>();
						
						mapParam.put("FILE_TYPE", "WEB_FILE");
						
						// file Id 여부 확인
						if (StringUtil.isBlank(fileId)) {
							// 첨부번호 없을시 신규 업로드 처리														
							String strAttcFileNo = getRandomString(20);
							mapParam.put("ATFINO", strAttcFileNo);	
														
							mgmtFileMapper.insertCmnFile1(mapParam);
							
						} else {
							mapParam.put("ATFINO", fileId);
														
							List<Map<String, Object>> list = selectCmnFileList(mapParam);

							if (list.size() > 0) {								
								deleteCmnFileByAttcFileNo(mapParam, strFileBasePath);
							}
						}

						for (UploadFile uFile : uFiles) {

							File file = uFile.getFile();
							
							String strFileName = uFile.getFileName(); // 파일명
							String strFileSize = Long.toString(file.length()); // 파일 사이즈							
							String strFileExt = FileUtil.getFileExtNm(strFileName); // 확장자명							
							String strTempPath = file.getPath(); // 임시 파일업로드 경로		
							
							// 파일명에 대한 암호화
							String strSaveNameFileNm = FileUtil.getEncryptFileNm();
							
							// 파일명은 확장자 추가해서 저장(2022-06-27 수정)							
							strSaveNameFileNm = FileUtil.uploadFile(strFileStoreFullPath, strTempPath, strSaveNameFileNm+"."+strFileExt, true);
							
							String filePath = strFileStoreFullPath+strSaveNameFileNm;
							/*
							// # 개인정보 필터링(PRIVACY) API 호출
							UploadAPICaller caller = new UploadAPICaller();
							caller.prevacyFile(filePath);
							*/
							nasSyncService.uploadFile(strSaveNameFileNm, strFileStoreFullPath);
							
							mapParam.put("ATCMFL_CL_NM", "0"); // 첨부파일 구분 코드
							mapParam.put("MENU_NO", String.valueOf(authMenuNo)); // 메뉴NO
							mapParam.put("USER_ID", strAppUserId); // 사용자ID
							mapParam.put("REAL_FILE_NM", strFileName); // 업로드 파일명
							mapParam.put("STRG_FILE_NM", strSaveNameFileNm); // 업로드 저장 파일명(암호화된 파일)
							mapParam.put("STRG_COURS_NM", strFileUploadPath); // 업로드 파일 저장경로
							mapParam.put("FILE_EXTN_NM", strFileExt); // 파일 확장자
							mapParam.put("FILE_SZ", strFileSize); // 파일 사이즈
							mapParam.put("FILE_STTS_SE_CD", "N"); // 파일 저장상태[CMN101]
							
							// 데이터 저장 SQL문을 실행 한다.							
							mgmtFileMapper.insertCmnFile2(mapParam);
														
							fileInfo.put("ATFINO", mapParam.get("ATFINO"));							
						}
					}
				}
				fileInfoList.add(fileInfo);
			}
		}

		return fileInfoList;
	}

	// 파일 다운로드 이력 기록
	@Override
	public void saveFileDownloadHistory(HttpServletRequest request, Map<String, Object> fileMap, String basePath) throws Exception {
		
		String userId = "none";
		
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId().trim())) {
			userId = userDetailsVO.getId();
		}
		
		fileMap.put("ATCMFL_CL_NM", fileMap.get("ATCMFL_CL_NM") == null || "".equals(fileMap.get("ATCMFL_CL_NM")) ? "-" : fileMap.get("ATCMFL_CL_NM"));
		fileMap.put("USER_ID", userId);
		
		fileMap.put("STRG_COURS_NM", basePath + fileMap.get("STRG_COURS_NM"));

		String vsAppId = "";
		String vsMenuNo = "";
		String vsUpMenuId = "";

		vsAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		vsMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? "" : request.getParameter("_AUTH_MENU_NO");
		vsUpMenuId = request.getParameter("_AUTH_UP_MENU_ID") == null ? "" : request.getParameter("_AUTH_UP_MENU_ID");
		
		fileMap.put("_AUTH_APP_ID", vsAppId);
		fileMap.put("_AUTH_MENU_NO", vsMenuNo);
		fileMap.put("_AUTH_UP_MENU_ID", vsUpMenuId);
		
		mgmtFileMapper.saveFileDownloadHistory(fileMap);
	}

	// 파일 다운로드 이력 기록
	@Override
	public void saveFileDownloadAllHistory(HttpServletRequest request, List<Map<String, Object>> fileList, String basePath) throws Exception {

		String userId = "none";
		
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId().trim())) {
			userId = userDetailsVO.getId();
		}
		
		Map<String, Object> fileMap = fileList.get(0);
		
		fileMap.put("ATCMFL_CL_NM", fileMap.get("ATCMFL_CL_NM") == null || "".equals(fileMap.get("ATCMFL_CL_NM")) ? "-" : fileMap.get("ATCMFL_CL_NM"));
		
		if (fileList.size() > 1) {
			fileMap.put("MNG_SN", 0);
			fileMap.put("REAL_FILE_NM", fileMap.get("REAL_FILE_NM") + " 외 " + (fileList.size() - 1) + "개");
			fileMap.put("STRG_FILE_NM", fileMap.get("STRG_FILE_NM") + " 외 " + (fileList.size() - 1) + "개");
			fileMap.put("STRG_COURS_NM", basePath + fileMap.get("STRG_COURS_NM") + " 외 " + (fileList.size() - 1) + "개");
		
		} else if (fileList.size() == 1) {
			fileMap.put("STRG_COURS_NM", basePath + fileMap.get("STRG_COURS_NM"));
		}
		
		int fileTotalSize = 0;
		for (int i=0; i < fileList.size(); i++) {
			Map<String, Object> map = fileList.get(i);
			fileTotalSize += map.get("FILE_SZ") == null ? 0 : Integer.parseInt(String.valueOf(map.get("FILE_SZ")));
		}
		fileMap.put("FILE_SZ", fileTotalSize);
		fileMap.put("USER_ID", userId);

		String vsAppId = "";
		String vsMenuNo = "";
		String vsUpMenuId = "";

		vsAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		vsMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? "" : request.getParameter("_AUTH_MENU_NO");
		vsUpMenuId = request.getParameter("_AUTH_UP_MENU_ID") == null ? "" : request.getParameter("_AUTH_UP_MENU_ID");
		
		fileMap.put("_AUTH_APP_ID", vsAppId);
		fileMap.put("_AUTH_MENU_NO", vsMenuNo);
		fileMap.put("_AUTH_UP_MENU_ID", vsUpMenuId);
		
		mgmtFileMapper.saveFileDownloadHistory(fileMap);
	}
	
	public String getBaseUrl(HttpServletRequest request) {
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}
	
	@Override
	public Integer selectFileDownloadHistoryTotalCount(Map<String, Object> dmSearchMap) throws Exception {
		Integer count = mgmtFileMapper.selectFileDownloadHistoryTotalCount(dmSearchMap);
		return count;
	}
	
	@Override
	public List<Map<String, Object>> selectFileDownloadHistoryList(Map<String, Object> dmSearchMap) throws Exception {
		List<Map<String, Object>> list = mgmtFileMapper.selectFileDownloadHistoryList(dmSearchMap);
		return list;
	}
	
}
