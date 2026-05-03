package isry.sample.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

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

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.sample.mapper.CmnFileMapper;
import isry.sample.service.CmnFileService;

/**
 * @Class Name : CmnFileServiceImpl.java
 * @Description : 파일업/다운로드 샘플 Business Implement Class
 * @Modification Information
 * @ @ 수정일 수정자 수정내용 @ --------- --------- -------------------------------
 *
 * @author tomatosystem
 * @since
 * @version
 * @see
 *
 */

@Service
public class CmnFileServiceImpl extends IsryBaseServiceImpl implements CmnFileService {

	@Resource(name = "cmnFileMapper")
	private CmnFileMapper cmnFileMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectCmnFileList(Map<String, String> mapParam) {
		return cmnFileMapper.selectCmnFileList(mapParam);
	}

	@Override
	public Map<String, String> uploadCmnFile(HttpServletRequest request, DataRequest dataRequest) throws Exception {

//		String strPgmId = dataRequest.getString(ProcessConstants.INTER_PGM_ID); //프로그램ID
//		String strMenuId = dataRequest.getString(ProcessConstants.INTER_MENU_ID); //메뉴ID
//		String strUserDefinePgmId = StringUtil.fixNull(dataRequest.getString("strUserDefinePgmId"));	// 부모 프로그램ID 가 아닌 사용자 정의한 프로그램ID
//		String strAppUserId = authentication.getUserId();	//사용자ID
		
		String appId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
				
		String strPgmId = "TMP";
		strPgmId = appId;
		
		String strAppUserId = "";

		HttpSession session = request.getSession();
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			strAppUserId = userDetailsVO.getId();
		}
		
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		// 프로그램별 파일업로드 경로 (uri 및 메뉴별 파일 저장도 별도 지정 필요)
		String strFileStorePath = prop.getProperty("globals", "isry.globals.upload.file.folder");

		if ("".equals(StringUtil.fixNull(strFileStorePath))) {
			
			// 첨부파일을 저장할 저장소 경로가 존재하지 않습니다.
			throw new AppWorksException("첨부파일을 저장할 저장소 경로가 존재하지 않습니다.");
			
		}
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		strFileStorePath = strFileStorePath + Integer.valueOf(year).toString() + File.separator;
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = StringUtil.fixNull(dmParam.getValue("strAttcFileNo"));// 첨부파일번호

		// 첨부번호 없을시 신규 업로드 처리
		String strFileStatRcd = "CMN101.SAVE";
		
		if ("".equals(strAttcFileNo)) {

			// 랜덤 함수를 이용하여 업로드 처리
			strAttcFileNo = getRandomString(20); 
		}

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

					// 보안에 위배되는 파일 확장자 유형인 경우...
//					if(!SecurityWebUtil.securedFileType(strFileName)){
//						//{0} 확장자는 업로드 할 수 없습니다.
//						throw new AppWorksException("CMN003.CMN@CMN021", Alert.ERROR, FileUtil.getFileExtNm(strFileName));
//					}

					
					// 파일명에 대한 암호화
					String strSaveNameFileNm = FileUtil.getEncryptFileNm();
					
					strSaveNameFileNm = FileUtil.uploadFile(strFileStorePath, strTempPath, strSaveNameFileNm, true);
					
					// 공통 첨부파일 DB테이블에 저장
					param.clear();

					param.put("ATTC_FILE_NO", strAttcFileNo); // 첨부파일 번호
					param.put("PGM_ID", strPgmId); // 메뉴ID
					param.put("USER_ID", strAppUserId); // 사용자ID
					param.put("FILE_NM", strFileName); // 업로드 파일명
					param.put("SAVE_FILE_NM", strSaveNameFileNm); // 업로드 저장 파일명(암호화된 파일)
					param.put("FILE_PATH", strFileStorePath); // 업로드 파일 저장경로
					param.put("FILE_EXT", strFileExt); // 파일 확장자
					param.put("FILE_SIZE", strFileSize); // 파일 사이즈
					param.put("FILE_STAT_RCD", strFileStatRcd); // 파일 저장상태[CMN101]

					// 데이터 저장 SQL문을 실행 한다.
					this.insertCmnFile(param);

					iFileCnt++;
					fileInfo.put("strAttcFileNo", strAttcFileNo);
					fileInfo.put("strFileNm", strFileName);
					fileInfo.put("strFileSize", strFileSize);
				}
			}
		}

		fileInfo.put("fileCnt", Integer.toString(iFileCnt));

		return fileInfo;
	}
	

	@Override
	public List<Map<String, String>> uploadCmnFileSeperate(HttpServletRequest request, DataRequest dataRequest) throws Exception {

//		String strPgmId = dataRequest.getString(ProcessConstants.INTER_PGM_ID); //프로그램ID
//		String strMenuId = dataRequest.getString(ProcessConstants.INTER_MENU_ID); //메뉴ID
//		String strUserDefinePgmId = StringUtil.fixNull(dataRequest.getString("strUserDefinePgmId"));	// 부모 프로그램ID 가 아닌 사용자 정의한 프로그램ID
//		String strAppUserId = authentication.getUserId();	//사용자ID

		String appId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		
		String strPgmId = "TMP";
		strPgmId = appId;
		
		String strAppUserId = "";
		
		HttpSession session = request.getSession();
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO != null) {
			strAppUserId = userDetailsVO.getId();
		}
		
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		// 프로그램별 파일업로드 경로 (uri 및 메뉴별 파일 저장도 별도 지정 필요)
		String strFileStorePath = prop.getProperty("globals", "isry.globals.upload.file.folder");

		log.debug("#### strFileStorePath : " + strFileStorePath);
		
		if ("".equals(StringUtil.fixNull(strFileStorePath))) {
			
			// 첨부파일을 저장할 저장소 경로가 존재하지 않습니다.
			throw new AppWorksException("첨부파일을 저장할 저장소 경로가 존재하지 않습니다.");
			
		}

		int year = Calendar.getInstance().get(Calendar.YEAR);
		strFileStorePath = strFileStorePath + Integer.valueOf(year).toString() + File.separator;
		
		ParameterGroup dsParam = dataRequest.getParameterGroup("dsAttcFileNo");
		List<Map<String, String>> dsParamList = null;
		if (dsParam != null) {
			dsParamList = dsParam.getAllRowList();
		}
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = dmParam == null ? "" : StringUtil.fixNull(dmParam.getValue("strAttcFileNo"));// 첨부파일번호

		// 첨부번호 없을시 신규 업로드 처리
		String strFileStatRcd = "CMN101.SAVE";

		
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
					
					log.debug("#### strFileStorePath : " + strFileStorePath);
					log.debug("#### strTempPath : " + strTempPath);
					log.debug("#### strSaveNameFileNm : " + strSaveNameFileNm);
					
					strSaveNameFileNm = FileUtil.uploadFile(strFileStorePath, strTempPath, strSaveNameFileNm, true);
					
					// 공통 첨부파일 DB테이블에 저장
					param.clear();

					param.put("ATTC_FILE_NO", strAttcFileNo); // 첨부파일 번호
					param.put("PGM_ID", strPgmId); // 메뉴ID
					param.put("USER_ID", strAppUserId); // 사용자ID
					param.put("FILE_NM", strFileName); // 업로드 파일명
					param.put("SAVE_FILE_NM", strSaveNameFileNm); // 업로드 저장 파일명(암호화된 파일)
					param.put("FILE_PATH", strFileStorePath); // 업로드 파일 저장경로
					param.put("FILE_EXT", strFileExt); // 파일 확장자
					param.put("FILE_SIZE", strFileSize); // 파일 사이즈
					param.put("FILE_STAT_RCD", strFileStatRcd); // 파일 저장상태[CMN101]

					// 데이터 저장 SQL문을 실행 한다.
					this.insertCmnFile(param);

					iFileCnt++;
					fileInfo.put("strAttcFileNo", strAttcFileNo);
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


	public int insertCmnFile(Map<String, String> mapParam) throws Exception {

		return cmnFileMapper.insertCmnFile(mapParam);
	}

	@Override
	public int deleteCmnFile(ParameterGroup dsFile) {
		Iterator<ParameterRow> deletedRows = dsFile.getDeletedRows();

		int result = 0;
		Map<String, String> param = null;
		while (deletedRows.hasNext()) {
			param = deletedRows.next().toMap();
			result += deleteCmnFileByAttcFileNo(param);

		}

		return result;
	}

	@Override
	public int deleteCmnFileByAttcFileNo(Map<String, String> mapParam) {
		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		List<Map<String, Object>> fileList = null;
		int result = 0;

		// 1. 첨부파일 정보 조회
		fileList = selectCmnFileList(mapParam);
		if (fileList != null) {
			for (Map<String, Object> file : fileList) {
				if (file != null && file.size() > 0) {

					file.put("ATTC_FILE_NO__origin", file.get("ATTC_FILE_NO"));

					file.put("ATTC_SEQ__origin", file.get("ATTC_SEQ"));

					// 2. DB 첨부파일 정보 삭제
					result += cmnFileMapper.deleteCmnAttcFile(file);

					// 3. 스토리지에 있는 실제 파일 삭제
					if (file != null && file.size() > 0) {

						// String strDeleteFilePath = strGlobalFileStorePath;
						String strDeleteFilePath = "";
						strDeleteFilePath += file.get("FILE_PATH");

						if (strDeleteFilePath.indexOf("../") != -1) {
							// 잘못된 첨부파일 경로입니다.(보안상의 이유로 상위폴더에 대한 접근은 불가합니다
							throw new AppWorksException("CMN003.CMN@CMN031", Alert.WARN);
						}

						FileUtil.deleteFile(strDeleteFilePath + "/" + file.get("SAVE_FILE_NM"));
					}
				}
			}
		}

		return result;

	}

	//private static String dummyString = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
	//private static Random random = new Random();

	public String getRandomString(int loopCount) {

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
		
		try {
			attchFileNo = cmnFileMapper.selectAttcFileNo();
		} catch (IOException e) {
			//System.out.println(e.getMessage());
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
		
		return String.valueOf(attchFileNo);
	}

	/**
	 * @Method명 : imageUploadCmnFile
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
	public void imageUploadCmnFile(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws IOException, Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, UploadFile[]> uploadFiles = dataRequest.getUploadFiles();

		String strBoardStorePath = "C:/IsryFile/image/";

		if (uploadFiles != null && uploadFiles.size() > 0) {
			Set<Entry<String, UploadFile[]>> entries = uploadFiles.entrySet();
			for (Entry<String, UploadFile[]> entry : entries) {
				UploadFile[] uFiles = entry.getValue();
				for (UploadFile uFile : uFiles) {
					File file = uFile.getFile();
					String fileName = uFile.getFileName(); // 파일명
					// 파일을 바이트 배열로 변환
					String strTempPath = file.getPath();
					// 이미지를 업로드할 디렉토리를 정해준다
					String uploadPath = strBoardStorePath;
					String strSaveNameFileNm = FileUtil.getEncryptFileNm();
					strSaveNameFileNm = FileUtil.uploadFile(uploadPath, strTempPath, strSaveNameFileNm, true);
					String fileUrl = request.getContextPath() + "/ui/app/temp/jsp/editorImage.jsp?fileNm="
							+ strSaveNameFileNm;
					map.put("uploaded", 1);
					map.put("filename", fileName);
					map.put("url", fileUrl);

				}
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), map);
		} catch (IOException ex) {
			//System.out.println(ex.getMessage());
		}
	}

}
