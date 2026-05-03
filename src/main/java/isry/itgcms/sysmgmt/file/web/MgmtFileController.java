/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.file.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.FileUtil;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
//import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.UserException;

/**
 * @파일명        : MgmtFileController.java
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
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/file")
public class MgmtFileController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String strWasFileBasePath = EgovProperties.getProperty("globals", "isry.globals.wasupload.file.folder");
	private String strWebFileBasePath = EgovProperties.getProperty("globals", "isry.globals.webupload.file.folder");
	
	@Resource(name = "mgmtFileService")
	private MgmtFileService mgmtFileService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	/**
	 * 
	 * <pre>
	 * 메소드명	: list
	 * 설	 명	: 첨부파일번호로 첨부된 파일 리스트 조회
	 * </pre>
	 *
	 * 이력사항 2021. 6. 23.
	 *
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @param authentication
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/list.do" , "/webList.do"})
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAtfino"));
		String strAtcmflClNm = StringUtil.fixNull(param.getValue("strAtcmflClNm"));
		String strFileStatRcd = StringUtil.fixNull(param.getValue("strFileStatRcd"));
		if ("".equals(strAtcmflClNm)) {
			strAtcmflClNm = "0";
		}
		
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("ATFINO", strAttcFileNo);
		mapParam.put("ATCMFL_CL_NM", strAtcmflClNm);
		mapParam.put("FILE_STAT_RCD", strFileStatRcd);
		
		String uriArr[] = request.getRequestURI().split("/");
		String uri = uriArr[uriArr.length-1];
		
		// 첨부파일 조회
		if (uri.equals("list.do")) {
			dataRequest.setResponse("dsFile", mgmtFileService.selectCmnFileList(mapParam));
		} else if (uri.equals("webList.do")) {
			dataRequest.setResponse("dsFile", mgmtFileService.selectWebCmnFileList(mapParam));
		}

		return new JSONDataView();
	}

	
	/**
	 * 파일을 업로드 처리한다.
	 * 
	 * <pre>
	 * 메소드명	: upload
	 * 설	 명	: 업로드 기능만 참고하시길 바랍니다.
	 * </pre>
	 *
	 * 이력사항
	 *
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload.do", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	public View upload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		//Enumeration<String> paramNames = request.getParameterNames();
		//while (paramNames.hasMoreElements()) {
			//String paramName = paramNames.nextElement();
			//log.debug("#### paramName : " + paramName);
		//}
		
		//String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		//Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				//? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		//log.debug("#### 1 authAppId : " + authAppId);
		//log.debug("#### 1 authMenuNo : " + authMenuNo);

		Map<String, String> fileInfo = mgmtFileService.uploadCmnFile(request, dataRequest, strWasFileBasePath);

		dataRequest.setResponse("dmUpload", fileInfo);

		return new JSONDataView();
	}

	
	/**
	 * 웹서버로 파일을 업로드 처리한다.
	 * 
	 * <pre>
	 * 메소드명	: upload
	 * 설	 명	: 업로드 기능만 참고하시길 바랍니다.
	 * </pre>
	 *
	 * 이력사항
	 *
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadWeb.do", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	public View uploadWeb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		//Enumeration<String> paramNames = request.getParameterNames();
		//while (paramNames.hasMoreElements()) {
			//String paramName = paramNames.nextElement();
			//log.debug("#### paramName : " + paramName);
		//}
		
		//String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		//Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				//? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		//log.debug("#### 1 authAppId : " + authAppId);
		//log.debug("#### 1 authMenuNo : " + authMenuNo);

		Map<String, String> fileInfo = mgmtFileService.uploadCmnFile(request, dataRequest, strWebFileBasePath);

		dataRequest.setResponse("dmUpload", fileInfo);

		return new JSONDataView();
	}

	/**
	 * <pre>
	 * 메소드명	: delete
	 * 설	 명	: 공통 첨부파일을 삭제한다.
	 * </pre>
	 *
	 * 이력사항
	 *
	 * @param request
	 * @param response
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public View delete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			log.debug("#### paramName : " + paramName);
		}
		
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAtfino"));
		String strAtcmflClNm = StringUtil.fixNull(param.getValue("strAtcmflClNm"));
		String strMngSn = StringUtil.fixNull(param.getValue("strMngSn"));

		ParameterGroup dsFile = dataRequest.getParameterGroup("dsFile");
		
		// 삭제할 데이터가 데이터셋으로 넘어온 경우
		if (dsFile != null) {
			mgmtFileService.deleteCmnFile(dsFile, strWasFileBasePath);
		
		} else {
			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("ATFINO", strAttcFileNo);
			mapParam.put("ATCMFL_CL_NM", strAtcmflClNm);
			mapParam.put("MNG_SN", strMngSn);

			mgmtFileService.deleteCmnFileByAttcFileNo(mapParam, strWasFileBasePath);
		}

		return new JSONDataView();
	}

	/**
	 * <pre>
	 * 메소드명	: delete
	 * 설	 명	: 웹서버의 공통 첨부파일을 삭제한다.
	 * </pre>
	 *
	 * 이력사항
	 *
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteWeb.do")
	public View deleteWeb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			log.debug("#### paramName : " + paramName);
		}
		
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAtfino"));
		String strAtcmflClNm = StringUtil.fixNull(param.getValue("strAtcmflClNm"));
		String strMngSn = StringUtil.fixNull(param.getValue("strMngSn"));

		ParameterGroup dsFile = dataRequest.getParameterGroup("dsFile");
		
		// 삭제할 데이터가 데이터셋으로 넘어온 경우
		if (dsFile != null) {
			mgmtFileService.deleteCmnFileWeb(dsFile, strWebFileBasePath);
		
		} else {
			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("ATFINO", strAttcFileNo);
			mapParam.put("ATCMFL_CL_NM", strAtcmflClNm);
			mapParam.put("MNG_SN", strMngSn);

			mgmtFileService.deleteCmnFileByAttcFileNoWeb(mapParam, strWebFileBasePath);
		}

		return new JSONDataView();
	}

	/**
	 * 파일 다운로드 하기전에... 해당 파일이 실제 존재하는지 체크한다.
	 * 
	 * @param request
	 * @param response
	 * @param requestData
	 * @param authentication
	 * @return
	 * @throws Exception
	 * @throws StdServiceException
	 */
	@RequestMapping("/checkFileExist.do")
	public View checkFileExist(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmDown");

		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");
		//String strFilePath = strWasFileBasePath + param.getValue("strFilePath");// 다운로드받을 파일의 경로
		String strFilePath = "";  // 다운로드받을 파일의 경로

		
		
		String atfino = StringUtil.fixNull(param.getValue("strAtfino")); // 첨부파일번호
		int mngSn = param.getValue("strMngSn") == null || "".equals(param.getValue("strMngSn")) ? 0 : Integer.parseInt(param.getValue("strMngSn")); // 관리일련번호
		String serverFilename = StringUtil.fixNull(param.getValue("strServerFileNm")); // 서버에 저장된 파일명
		
		Map<String, String> map = new HashMap<>();
		map.put("ATFINO", atfino);
		map.put("MNG_SN", String.valueOf(mngSn));
		
		List<Map<String, Object>> fileList = mgmtFileService.selectCmnFileList(map);
		
		Map<String, Object> fileMap = new HashMap<>();
		
		if (fileList == null || fileList.size() == 0) {
			throw new UserException("errrors.downloadError");
		} else {
			fileMap = fileList.get(0);
		}

		if (!fileMap.get("STRG_FILE_NM").equals(serverFilename)) {
			throw new AppWorksException("잘못된 접근입니다.", Alert.ERROR);
		}
			
		String serverPath = StringUtil.fixNull(fileMap.get("STRG_COURS_NM"));
		
		if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
			serverPath += File.separator;
		}
		
		strFilePath = strWasFileBasePath + serverPath
			+ StringUtil.fixNull(fileMap.get("STRG_FILE_NM"));  // 다운로드 받을 파일의 경로
		
		
		
		log.debug("#### strFilePath : " + strFilePath);
		
		File file = new File(strFilePath);
		
		if (file.exists()) {
			Map<String, Object> message = new HashMap<String, Object>();
			message.put("exist", "Y");
			dataRequest.setMetadata(true, message);
		
		} else {
			// 첨부파일이 존재하지 않아, 다운로드가 불가합니다.
			throw new AppWorksException("첨부파일이 존재하지 않아, 다운로드가 불가합니다.", Alert.ERROR);
		}
		
		return new JSONDataView();
	}

	/**
	 * 파일 다운로드 하기전에... 해당 파일이 실제 존재하는지 체크한다.
	 * 
	 * @param request
	 * @param response
	 * @param requestData
	 * @param authentication
	 * @return
	 * @throws Exception
	 * @throws StdServiceException
	 */
	@RequestMapping("/checkFileExistWeb.do")
	public View checkFileExistWeb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmDown");

		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");
		//String strFilePath = strWasFileBasePath + param.getValue("strFilePath");// 다운로드받을 파일의 경로
		String strFilePath = "";  // 다운로드받을 파일의 경로

		
		
		String atfino = StringUtil.fixNull(param.getValue("strAtfino")); // 첨부파일번호
		int mngSn = param.getValue("strMngSn") == null || "".equals(param.getValue("strMngSn")) ? 0 : Integer.parseInt(param.getValue("strMngSn")); // 관리일련번호
		String serverFilename = StringUtil.fixNull(param.getValue("strServerFileNm")); // 서버에 저장된 파일명
		
		Map<String, String> map = new HashMap<>();
		map.put("ATFINO", atfino);
		map.put("MNG_SN", String.valueOf(mngSn));
		
		List<Map<String, Object>> fileList = mgmtFileService.selectWebCmnFileList(map);
		
		Map<String, Object> fileMap = new HashMap<>();
		
		if (fileList == null || fileList.size() == 0) {
			throw new UserException("errrors.downloadError");
		} else {
			fileMap = fileList.get(0);
		}

		if (!fileMap.get("STRG_FILE_NM").equals(serverFilename)) {
			throw new AppWorksException("잘못된 접근입니다.", Alert.ERROR);
		}
			
		String serverPath = StringUtil.fixNull(fileMap.get("STRG_COURS_NM"));
		
		if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
			serverPath += File.separator;
		}
		
		strFilePath = strWebFileBasePath + serverPath
			+ StringUtil.fixNull(fileMap.get("STRG_FILE_NM"));  // 다운로드 받을 파일의 경로
		
		
		
		log.debug("#### strFilePath : " + strFilePath);
		
		File file = new File(strFilePath);
		
		if (file.exists()) {
			Map<String, Object> message = new HashMap<String, Object>();
			message.put("exist", "Y");
			dataRequest.setMetadata(true, message);
		
		} else {
			// 첨부파일이 존재하지 않아, 다운로드가 불가합니다.
			throw new AppWorksException("첨부파일이 존재하지 않아, 다운로드가 불가합니다.", Alert.ERROR);
		}
		
		return new JSONDataView();
	}

	/**
	 * 파일을 다운로드 한다.
	 * 
	 * <pre>
	 * 메소드명	: download
	 * 설	 명	:
	 * </pre>
	 *
	 * 이력사항
	 *
	 * @param request
	 * @param response
	 * @param requestData
	 * @param authentication
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download.do")
	//public View download(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	public void download(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmDown");

		//String strFilePath = strWasFileBasePath + StringUtil.fixNull(param.getValue("strFilePath"));// 다운로드받을 파일의 경로
		String strFilePath = "";  // 다운로드받을 파일의 경로
		String strFileNm = StringUtil.fixNull(param.getValue("strFileNm")); // 실제 파일명

		
		
		String atfino = StringUtil.fixNull(param.getValue("atfino")); // 첨부파일번호
		int mngSn = param.getValue("mngSn") == null || "".equals(param.getValue("mngSn")) ? 0 : Integer.parseInt(param.getValue("mngSn")); // 관리일련번호
		String serverFilename = StringUtil.fixNull(param.getValue("serverFilename")); // 서버에 저장된 파일명
		
		Map<String, String> map = new HashMap<>();
		map.put("ATFINO", atfino);
		map.put("MNG_SN", String.valueOf(mngSn));
		
		List<Map<String, Object>> fileList = mgmtFileService.selectCmnFileList(map);
		
		Map<String, Object> fileMap = new HashMap<>();
		
		if (fileList == null || fileList.size() == 0) {
			throw new UserException("errrors.downloadError");
		} else {
			fileMap = fileList.get(0);
		}

		if (!fileMap.get("STRG_FILE_NM").equals(serverFilename)) {
			throw new AppWorksException("잘못된 접근입니다.", Alert.ERROR);
		}
		
			
		String serverPath = StringUtil.fixNull(fileMap.get("STRG_COURS_NM"));
		
		if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
			serverPath += File.separator;
		}
		
		strFilePath = strWasFileBasePath + serverPath
			+ StringUtil.fixNull(fileMap.get("STRG_FILE_NM"));  // 다운로드 받을 파일의 경로
		

		
		//log.debug("#### path 1 : " + path);
		while (strFilePath.contains("..")) {
			strFilePath = strFilePath.replaceAll("\\.\\.", "");
		}
		//log.debug("#### path 2 : " + path);
		
		if (!(strFilePath == null || "".equals(strFilePath))) {
			if (!strFilePath.startsWith(strWasFileBasePath) && !strFilePath.startsWith(strWebFileBasePath)) {
				throw new UserException("errrors.downloadError");
			}
		}
		
		//Map<String, String> map = new HashMap<>();
		
		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if (strFileNm == null || "".equals(strFileNm)) {
			int index = strFilePath.lastIndexOf(File.separator);
			if (index == -1) {
				index = strFilePath.lastIndexOf("/");
			}
			strFileNm = strFilePath.substring(index + 1);
		}

		try {
			
			// 파일 다운로드 이력 기록
			mgmtFileService.saveFileDownloadHistory(request, fileMap, strWasFileBasePath);
			
			// String strGlobalFileStorePath =
			// EgovProperties.getProperty("Globals.fileStorePath");
			
			FileUtil.fileDownloadWrapper(strFilePath, request, response, strFileNm, "");
			
		} catch (Exception e) {
			// 파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다
			// throw new AppWorksException("CMN003.CMN@CMN017", Alert.ERROR);
			//response.setContentType("text/html;charset=utf-8");
			//response.setCharacterEncoding("utf-8");
			//ServletOutputStream outs = response.getOutputStream();
			//String errorMsg = "파일다운로드시 오류가 발생했습니다.\\n파일이 존재하지 않거나 네트워크가 불안정합니다.\\n관리자에게 문의바랍니다";
			//String msg = new String(errorMsg.getBytes(), "8859_1");
			//try {
				//outs.println("<html><script type='text/javascript'>");
				//outs.println("alert(\"" + msg + "\");");
				//outs.println("</script></html>");
				//outs.flush();
			//} finally {
				//outs.close();
			//}
			//map.put("msg", "파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
			throw new UserException("errrors.downloadError");
		}
		
		//dataRequest.setResponse("dmMessage", map);
		//return new JSONDataView();
	}


	/**
	 * 웹서버의 파일을 다운로드 한다.
	 * 
	 * <pre>
	 * 메소드명	: download
	 * 설	 명	:
	 * </pre>
	 *
	 * 이력사항
	 *
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadWeb.do")
	//public View download(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	public void downloadWeb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmDown");

		//String strFilePath = strWasFileBasePath + StringUtil.fixNull(param.getValue("strFilePath"));// 다운로드받을 파일의 경로
		String strFilePath = "";  // 다운로드받을 파일의 경로
		String strFileNm = StringUtil.fixNull(param.getValue("strFileNm")); // 실제 파일명

		
		
		String atfino = StringUtil.fixNull(param.getValue("atfino")); // 첨부파일번호
		int mngSn = param.getValue("mngSn") == null || "".equals(param.getValue("mngSn")) ? 0 : Integer.parseInt(param.getValue("mngSn")); // 관리일련번호
		String serverFilename = StringUtil.fixNull(param.getValue("serverFilename")); // 서버에 저장된 파일명
		
		Map<String, String> map = new HashMap<>();
		map.put("ATFINO", atfino);
		map.put("MNG_SN", String.valueOf(mngSn));
		
		List<Map<String, Object>> fileList = mgmtFileService.selectWebCmnFileList(map);
		
		Map<String, Object> fileMap = new HashMap<>();
		
		if (fileList == null || fileList.size() == 0) {
			throw new UserException("errrors.downloadError");
		} else {
			fileMap = fileList.get(0);
		}

		if (!fileMap.get("STRG_FILE_NM").equals(serverFilename)) {
			throw new AppWorksException("잘못된 접근입니다.", Alert.ERROR);
		}
		
			
		String serverPath = StringUtil.fixNull(fileMap.get("STRG_COURS_NM"));
		
		if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
			serverPath += File.separator;
		}
		
		strFilePath = strWebFileBasePath + serverPath
			+ StringUtil.fixNull(fileMap.get("STRG_FILE_NM"));  // 다운로드 받을 파일의 경로
		

		
		//log.debug("#### path 1 : " + path);
		while (strFilePath.contains("..")) {
			strFilePath = strFilePath.replaceAll("\\.\\.", "");
		}
		//log.debug("#### path 2 : " + path);
		
		if (!(strFilePath == null || "".equals(strFilePath))) {
			if (!strFilePath.startsWith(strWasFileBasePath) && !strFilePath.startsWith(strWebFileBasePath)) {
				throw new UserException("errrors.downloadError");
			}
		}
		
		//Map<String, String> map = new HashMap<>();
		
		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if (strFileNm == null || "".equals(strFileNm)) {
			int index = strFilePath.lastIndexOf(File.separator);
			if (index == -1) {
				index = strFilePath.lastIndexOf("/");
			}
			strFileNm = strFilePath.substring(index + 1);
		}

		try {
			
			// 파일 다운로드 이력 기록
			mgmtFileService.saveFileDownloadHistory(request, fileMap, strWebFileBasePath);
			
			// String strGlobalFileStorePath =
			// EgovProperties.getProperty("Globals.fileStorePath");
			
			FileUtil.fileDownloadWrapper(strFilePath, request, response, strFileNm, "");
			
		} catch (Exception e) {
			// 파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다
			// throw new AppWorksException("CMN003.CMN@CMN017", Alert.ERROR);
			//response.setContentType("text/html;charset=utf-8");
			//response.setCharacterEncoding("utf-8");
			//ServletOutputStream outs = response.getOutputStream();
			//String errorMsg = "파일다운로드시 오류가 발생했습니다.\\n파일이 존재하지 않거나 네트워크가 불안정합니다.\\n관리자에게 문의바랍니다";
			//String msg = new String(errorMsg.getBytes(), "8859_1");
			//try {
				//outs.println("<html><script type='text/javascript'>");
				//outs.println("alert(\"" + msg + "\");");
				//outs.println("</script></html>");
				//outs.flush();
			//} finally {
				//outs.close();
			//}
			//map.put("msg", "파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
			throw new UserException("errrors.downloadError");
		}
		
		//dataRequest.setResponse("dmMessage", map);
		//return new JSONDataView();
	}

	@RequestMapping("/downloadAll.do")
	public View downloadAll(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		//String strGlobalFileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		ParameterGroup param = dataRequest.getParameterGroup("dmDownAll");

		if (param == null) {
			throw new AppWorksException("파라메터 항목이 없습니다.", Alert.ERROR);
		}
		
		//String strAttcFileNo = StringUtil.fixNull(dataRequest.getParameter("strAttcFileNo"));
		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAttcFileNo"));
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("ATFINO", strAttcFileNo);

		List<Map<String, Object>> fileList = mgmtFileService.selectCmnAllFileList(mapParam);

		if (fileList != null && fileList.size() > 0) {
			
			for (int i = fileList.size() - 1; i >= 0 ; i--) {
				List<String> fileCheckList = new ArrayList<>();
				Map<String, Object> fileMap = fileList.get(i);
				String filePath = strWasFileBasePath + fileMap.get("STRG_COURS_NM") + fileMap.get("STRG_FILE_NM");
				log.info("#### filePath : " + filePath);
				File fileTest = new File(filePath);
				log.info("#### file exists : " + fileTest.exists());
				if (!fileTest.exists() || fileCheckList.contains(filePath)) {
					fileList.remove(i);
				} else {
					fileCheckList.add(filePath);
				}
			}
			
			log.info("#### list size : " + fileList.size());
			
			if (fileList == null || fileList.size() == 0) {
				throw new UserException("errrors.downloadError");
			}
			
			String fileName = (String) fileList.get(0).get("REAL_FILE_NM");
			
			//log.info("#### fileName : " + fileName);
			//log.info("#### fileName.lastIndexOf(\".\") : " + fileName.lastIndexOf("."));

			//log.info("#### fileName.substring 1 : " + fileName.substring(0, fileName.lastIndexOf(".") - 1));
			//log.info("#### fileName.substring 2 : " + fileName.substring(0, fileName.lastIndexOf(".")));
			
			//fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			
			// 파일명에서 특수 문자 제거
			fileName = fileName.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");
			
			if (fileList.size() == 1) {
				fileName += ".zip";
			} else {
				fileName += " 외(" + (fileList.size() - 1) + "개).zip";
			}
			
			// 파일 다운로드 수행
			FileUtil.downloadAsZip(request, response, strWasFileBasePath, fileName, fileList);

			// 파일 다운로드 이력 기록
			mgmtFileService.saveFileDownloadAllHistory(request, fileList, strWasFileBasePath);
		}

		return new JSONDataView();
	}


	@RequestMapping("/downloadAllWeb.do")
	public View downloadAllWeb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		//String strGlobalFileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		ParameterGroup param = dataRequest.getParameterGroup("dmDownAll");
		
		//String strAttcFileNo = StringUtil.fixNull(dataRequest.getParameter("strAttcFileNo"));
		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAttcFileNo"));
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("ATFINO", strAttcFileNo);

		List<Map<String, Object>> fileList = mgmtFileService.selectWebCmnAllFileList(mapParam);

		if (fileList != null && fileList.size() > 0) {
			String fileName = (String) fileList.get(0).get("REAL_FILE_NM");
			
			//log.info("#### fileName : " + fileName);
			//log.info("#### fileName.lastIndexOf(\".\") : " + fileName.lastIndexOf("."));

			//log.info("#### fileName.substring 1 : " + fileName.substring(0, fileName.lastIndexOf(".") - 1));
			//log.info("#### fileName.substring 2 : " + fileName.substring(0, fileName.lastIndexOf(".")));
			
			//fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			
			if (fileList.size() == 1) {
				fileName += ".zip";
			} else {
				fileName += " 외(" + (fileList.size() - 1) + "개).zip";
			}
			
			// 파일 다운로드 수행
			FileUtil.downloadAsZip(request, response, strWebFileBasePath, fileName, fileList);

			// 파일 다운로드 이력 기록
			mgmtFileService.saveFileDownloadAllHistory(request, fileList, strWebFileBasePath);
		}

		return new JSONDataView();
	}

	
	/**
	 * 
	 * Method Name : fileDownLoad<BR/>
	 * Description : 파일다운로드 <BR/>
	 *
	 * @author : Park. ju wan <BR/>
	 *         History <BR/>
	 *         2015. 10. 27. Park. ju wan 최초작성 <BR/>
	 *
	 * @param req
	 * @param resp
	 * @param dataView
	 * @param sqlClientAssists
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tmpDownload.do")
	public View tmpDownload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		String strTmpFilePath = dataRequest.getParameter("strTmpFilePath"); // 템프폴더파일풀경로
		String strOriFileNm = dataRequest.getParameter("strOriFileNm"); // 원(실)파일명
		// 템프폴더에 저장된 경우
		if (StringUtil.isNotNullEmpty(strTmpFilePath)) {
			try {
				FileUtil.fileDownloadWrapper(strTmpFilePath, request, response, strOriFileNm, "");
			} catch (IOException e) {
				log.debug(e.getMessage());
			} catch (Exception e) {
				log.debug(e.getMessage());
			} finally {
				File file = new File(strTmpFilePath);
				if (file.exists()) {
					file.delete();
				}
			}
		} else {
			String strFileNm = dataRequest.getParameter("strFileNm"); // 저장된 파일명(파일명변환)
			String strOriFileNm2 = dataRequest.getParameter("strOriFileNm"); // 원(실)파일명
			String strFileSubPath = dataRequest.getParameter("strFileSubPath");// 파일 서브경로
			// 파일다운로드
			try {
				String strGlobalFileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
				FileUtil.fileDownloadWrapper(strGlobalFileStorePath + File.pathSeparator + strFileSubPath
						+ File.pathSeparator + strOriFileNm2, request, response, strFileNm, "");

			} catch (IOException e) {
				// 파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("utf-8");
				ServletOutputStream outs = response.getOutputStream();
				String errorMsg = "파일다운로드시 오류가 발생했습니다.\\n파일이 존재하지 않거나 네트워크가 불안정합니다.\\n관리자에게 문의바랍니다";
				String msg = new String(errorMsg.getBytes(), "8859_1");
				try {
					outs.println("<html><script type='text/javascript'>");
					outs.println("alert(\"" + msg + "\");");
					outs.println("</script></html>");
					outs.flush();
				} finally {
					outs.close();
				}
			} catch (Exception e) {
				// 파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("utf-8");
				ServletOutputStream outs = response.getOutputStream();
				String errorMsg = "파일다운로드시 오류가 발생했습니다.\\n파일이 존재하지 않거나 네트워크가 불안정합니다.\\n관리자에게 문의바랍니다";
				String msg = new String(errorMsg.getBytes(), "8859_1");
				try {
					outs.println("<html><script type='text/javascript'>");
					outs.println("alert(\"" + msg + "\");");
					outs.println("</script></html>");
					outs.flush();
				} finally {
					outs.close();
				}
			}

		}
		return null;
	}

	@RequestMapping("/imageUpload.do")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		mgmtFileService.imageUploadCmnFile(request, response, dataRequest, strWebFileBasePath);

	}
	
	@RequestMapping("/offcsSgnngUpload.do")
	public View offcsSgnngUpload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		mgmtFileService.imageUploadCmnFile(request, response, dataRequest, strWebFileBasePath);
		
		return new JSONDataView();

	}


	@RequestMapping("/fileDown.do")
	public void fileDown(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		
		String path = "";
		String file = "";
		String filename = "";
		String type = "";
		
		String atfino = "";
		int mngSn = 0;
		String serverFilename = "";  // 서버에 저장된 파일명
		
		if (param != null) {
			//path = strWasFileBasePath + StringUtil.fixNull(param.getValue("path"));// 다운로드 받을 파일의 경로
			file = StringUtil.fixNull(param.getValue("file")); // 실제 파일명
			filename = StringUtil.fixNull(param.getValue("filename")); // 실제 파일명
			type = StringUtil.fixNull(param.getValue("type")); // 실제 파일명
			atfino = StringUtil.fixNull(param.getValue("atfino")); // 첨부파일번호
			mngSn = param.getValue("mngSn") == null || "".equals(param.getValue("mngSn")) ? 0 : Integer.parseInt(param.getValue("mngSn")); // 관리일련번호
			serverFilename = StringUtil.fixNull(param.getValue("serverFilename")); // 서버에 저장된 파일명
			
		} else {
			//path = strWasFileBasePath + request.getParameter("path");
			file = request.getParameter("file");
			filename = request.getParameter("filename");
			type = request.getParameter("type") == null ? "" : request.getParameter("type");
			atfino = StringUtil.fixNull(request.getParameter("atfino")); // 첨부파일번호
			mngSn = request.getParameter("mngSn") == null || "".equals(request.getParameter("mngSn")) ? 0 : Integer.parseInt(request.getParameter("mngSn")); // 관리일련번호
			serverFilename = StringUtil.fixNull(request.getParameter("serverFilename")); // 서버에 저장된 파일명
		}

		
		Map<String, String> map = new HashMap<>();
		map.put("ATFINO", atfino);
		map.put("MNG_SN", String.valueOf(mngSn));
		
		List<Map<String, Object>> fileList = mgmtFileService.selectCmnFileList(map);
		
		Map<String, Object> fileMap = new HashMap<>();
		
		if (fileList != null && fileList.size() > 0) {
			fileMap = fileList.get(0);

			if (!fileMap.get("STRG_FILE_NM").equals(serverFilename)) {
				throw new AppWorksException("잘못된 접근입니다.", Alert.ERROR);
			}
		}

		if (file == null || "".equals(file)) {
			
			String serverPath = StringUtil.fixNull(fileMap.get("STRG_COURS_NM"));
			
			if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
				serverPath += File.separator;
			}
			
			path = strWasFileBasePath + serverPath
				+ StringUtil.fixNull(fileMap.get("STRG_FILE_NM"));  // 다운로드 받을 파일의 경로
		}
		
		//file += "1";
		log.info("#### path : " + path);
		log.info("#### file : " + file);
		log.info("#### filename : " + filename);
		
		//Map<String, String> map = new HashMap<>();
		
		//log.debug("#### path 1 : " + path);
		while(path.contains("..")) {
			path = path.replaceAll("\\.\\.", "");
		}
		//log.debug("#### path 2 : " + path);
		
		if (!(path == null || "".equals(path))) {
			if (!path.startsWith(strWasFileBasePath) && !path.startsWith(strWebFileBasePath)) {
				throw new UserException("errrors.downloadError");
			}
		}
		
		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if ((path == null || "".equals(path)) && (file == null || "".equals(file))) {
			
			//map.put("msg", "파일이 없습니다.");
			//throw new UserException("errors.fileNotExists");
			throw new UserException("errrors.downloadError");
			
		} else {
			
			if (path == null || "".equals(path)) {
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String savePath = root + (root.endsWith("/") || root.endsWith("\\") ? "resource/file" : "/resource/file");
				path = savePath + "/" + file;
			}
			
			log.info("#### path : " + path);
			
			try {
				
				if (fileMap != null && fileMap.get("ATFINO") != null && !"".equals(fileMap.get("ATFINO"))) {
					if ("".equals(type)) {
						// 파일 다운로드 이력 기록
						mgmtFileService.saveFileDownloadHistory(request, fileMap, strWasFileBasePath);
					}
				}
				
				FileUtil.fileDownloadWrapper(path, request, response, filename, type);

			} catch (Exception e) {
				//log.info("파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//map.put("msg", "파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//throw new UserException("errors.fileDownError");
				throw new UserException("errrors.downloadError");
			}
		}
		
		//dataRequest.setResponse("dmMessage", map);
		//return new JSONDataView();
	}


	@RequestMapping("/fileDownWeb.do")
	public void fileDownWeb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		
		String path = "";
		String file = "";
		String filename = "";
		String type = "";
		
		String atfino = "";
		int mngSn = 0;
		String serverFilename = "";  // 서버에 저장된 파일명
		
		if (param != null) {
			//path = strWasFileBasePath + StringUtil.fixNull(param.getValue("path"));// 다운로드 받을 파일의 경로
			file = StringUtil.fixNull(param.getValue("file")); // 실제 파일명
			filename = StringUtil.fixNull(param.getValue("filename")); // 실제 파일명
			type = StringUtil.fixNull(param.getValue("type")); // 실제 파일명
			atfino = StringUtil.fixNull(param.getValue("atfino")); // 첨부파일번호
			mngSn = param.getValue("mngSn") == null || "".equals(param.getValue("mngSn")) ? 0 : Integer.parseInt(param.getValue("mngSn")); // 관리일련번호
			serverFilename = StringUtil.fixNull(param.getValue("serverFilename")); // 서버에 저장된 파일명
			
		} else {
			//path = strWasFileBasePath + request.getParameter("path");
			file = request.getParameter("file");
			filename = request.getParameter("filename");
			type = request.getParameter("type") == null ? "" : request.getParameter("type");
			atfino = StringUtil.fixNull(request.getParameter("atfino")); // 첨부파일번호
			mngSn = request.getParameter("mngSn") == null || "".equals(request.getParameter("mngSn")) ? 0 : Integer.parseInt(request.getParameter("mngSn")); // 관리일련번호
			serverFilename = StringUtil.fixNull(request.getParameter("serverFilename")); // 서버에 저장된 파일명
		}

		
		Map<String, String> map = new HashMap<>();
		map.put("ATFINO", atfino);
		map.put("MNG_SN", String.valueOf(mngSn));
		
		List<Map<String, Object>> fileList = mgmtFileService.selectWebCmnFileList(map);
		
		Map<String, Object> fileMap = new HashMap<>();
		
		if (fileList != null && fileList.size() > 0) {
			fileMap = fileList.get(0);

			if (!fileMap.get("STRG_FILE_NM").equals(serverFilename)) {
				throw new AppWorksException("잘못된 접근입니다.", Alert.ERROR);
			}
		}

		if (file == null || "".equals(file)) {
			
			String serverPath = StringUtil.fixNull(fileMap.get("STRG_COURS_NM"));
			
			if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
				serverPath += File.separator;
			}
			
			path = strWebFileBasePath + serverPath
				+ StringUtil.fixNull(fileMap.get("STRG_FILE_NM"));  // 다운로드 받을 파일의 경로
		}
		
		//file += "1";
		log.info("#### path : " + path);
		log.info("#### file : " + file);
		log.info("#### filename : " + filename);
		
		//Map<String, String> map = new HashMap<>();
		
		//log.debug("#### path 1 : " + path);
		while(path.contains("..")) {
			path = path.replaceAll("\\.\\.", "");
		}
		//log.debug("#### path 2 : " + path);
		
		if (!(path == null || "".equals(path))) {
			if (!path.startsWith(strWasFileBasePath) && !path.startsWith(strWebFileBasePath)) {
				throw new UserException("errrors.downloadError");
			}
		}
		
		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if ((path == null || "".equals(path)) && (file == null || "".equals(file))) {
			
			//map.put("msg", "파일이 없습니다.");
			//throw new UserException("errors.fileNotExists");
			throw new UserException("errrors.downloadError");
			
		} else {
			
			if (path == null || "".equals(path)) {
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String savePath = root + (root.endsWith("/") || root.endsWith("\\") ? "resource/file" : "/resource/file");
				path = savePath + "/" + file;
			}
			
			log.info("#### path : " + path);
			
			try {
				
				if (fileMap != null && fileMap.get("ATFINO") != null && !"".equals(fileMap.get("ATFINO"))) {
					if ("".equals(type)) {
						// 파일 다운로드 이력 기록
						mgmtFileService.saveFileDownloadHistory(request, fileMap, strWebFileBasePath);
					}
				}
				
				FileUtil.fileDownloadWrapper(path, request, response, filename, type);

			} catch (Exception e) {
				//log.info("파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//map.put("msg", "파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//throw new UserException("errors.fileDownError");
				throw new UserException("errrors.downloadError");
			}
		}
		
		//dataRequest.setResponse("dmMessage", map);
		//return new JSONDataView();
	}

	@RequestMapping("/fileDownHttp.do")
	public void fileDownHttp(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		
		String path = "";
		String filename = "";
		String type = "url";  // http:// 또는 https:// 로부터 다운로드
		
		if (param != null) {
			path = StringUtil.fixNull(param.getValue("file")); // 실제 파일명
			filename = StringUtil.fixNull(param.getValue("filename")); // 실제 파일명
			
		} else {
			path = request.getParameter("file");
			filename = request.getParameter("filename"); // 실제 파일명
		}

		log.info("#### path : " + path);
		log.info("#### filename : " + filename);
		
		if (!(path == null || "".equals(path.trim()))) {
			path = path.trim();
			if (!path.startsWith("http://") && !path.startsWith("https://")) {
				throw new UserException("errrors.downloadError");
			}
		}
		
		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if (path == null || "".equals(path.trim())) {
			
			//map.put("msg", "파일이 없습니다.");
			//throw new UserException("errors.fileNotExists");
			throw new UserException("errrors.downloadError");
			
		} else {
			
			while (path.contains("..")) {
				path = path.replaceAll("\\.\\.", "");
			}
			
			log.info("#### path : " + path);
			
			try {
				
				FileUtil.fileDownloadWrapper(path, request, response, filename, type);

			} catch (Exception e) {
				//log.info("파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//map.put("msg", "파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//throw new UserException("errors.fileDownError");
				throw new UserException("errrors.downloadError");
			}
		}
	}

	
	@RequestMapping("/fileDown2.do")
	public void fileDown2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		
		String path = "";
		String file = "";
		String filename = "";
		String type = "";
		
		if (param != null) {
			path = strWasFileBasePath + StringUtil.fixNull(param.getValue("path"));// 다운로드 받을 파일의 경로
			file = StringUtil.fixNull(param.getValue("file")); // 실제 파일명
			filename = StringUtil.fixNull(param.getValue("filename")); // 실제 파일명
			type = StringUtil.fixNull(param.getValue("type")); // 실제 파일명
		
		} else {
			path = strWasFileBasePath + request.getParameter("path");
			file = request.getParameter("file");
			filename = request.getParameter("filename");
			type = request.getParameter("type") == null ? "" : request.getParameter("type");
		}
		
		//file += "1";
		//log.info("#### path : " + path);
		//log.info("#### file : " + file);
		//log.info("#### filename : " + filename);
		
		//Map<String, String> map = new HashMap<>();
		
		//log.debug("#### path 1 : " + path);
		while(path.contains("..")) {
			path = path.replaceAll("\\.\\.", "");
		}
		//log.debug("#### path 2 : " + path);
		
		if (!(path == null || "".equals(path))) {
			if (!path.startsWith(strWasFileBasePath) && !path.startsWith(strWebFileBasePath)) {
				throw new UserException("errrors.downloadError");
			}
		}
		
		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if ((path == null || "".equals(path)) && (file == null || "".equals(file))) {
			
			//map.put("msg", "파일이 없습니다.");
			//throw new UserException("errors.fileNotExists");
			throw new UserException("errrors.downloadError");
			
		} else {
			
			if (path == null || "".equals(path)) {
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String savePath = root + (root.endsWith("/") || root.endsWith("\\") ? "resource/file" : "/resource/file");
				path = savePath + "/" + file;
			}
			
			//log.info("#### path : " + path);
			
			try {
				
				//FileUtil.fileDownloadWrapper(path, request, response, filename, type);

			} catch (Exception e) {
				//log.info("파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//map.put("msg", "파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다.");
				//throw new UserException("errors.fileDownError");
				throw new UserException("errrors.downloadError");
			}
		}
		
		//dataRequest.setResponse("dmMessage", map);
		//return new JSONDataView();
	}

	
	@RequestMapping("/fileDownloadHistory.do")
	public View fileDownloadHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());

		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = mgmtFileService.selectFileDownloadHistoryTotalCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("OFFSET_IDX", startIndex - 1);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);
		
		List<Map<String, Object>> listBoard = mgmtFileService.selectFileDownloadHistoryList(dmSearchMap);

		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		return new JSONDataView();
	}

	
	@RequestMapping("/onLoadFileDownloadHistory.do")
	public View onLoadFileDownloadHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		return new JSONDataView();
	}

}
