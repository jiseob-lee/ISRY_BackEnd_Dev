package isry.sample.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.ExcelImporter;
import com.tomatosystem.exbuilder6.core.util.FileUtil;
import com.tomatosystem.exbuilder6.core.util.StringUtil;
import com.tomatosystem.exbuilder6.core.vo.ExcelVO;
import isry.sample.service.CmnFileService;

/**
 * <pre>
 * 시  스  템  : 공통
 * 단위시스템  : 공통시스템
 * 프로그램명  : 파일업로드/다운로드
 * 설      명    : 첨부파일 업로드 및 파일 다운로드에 대한 요건은 프로젝트별 상이하므로 
 *                   해당 샘플 파일의 업로드 및 다운로드 기능만 참고하시길 바랍니다.
 * </pre>
 * 
 * 이력사항
 * 
 */
@Controller
@RequestMapping("/CmnFile")
public class CmnFileController {
	@Autowired
	private CmnFileService cmnFileService;

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
	@RequestMapping("/list.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAttcFileNo"));
		String strFileStatRcd = StringUtil.fixNull(param.getValue("strFileStatRcd"));

		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("ATTC_FILE_NO", strAttcFileNo);
		mapParam.put("FILE_STAT_RCD", strFileStatRcd);

		// 첨부파일 조회
		dataRequest.setResponse("dsFile", cmnFileService.selectCmnFileList(mapParam));
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
	 * @param requestData
	 * @param authentication
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload.do")
	public View upload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, String> fileInfo = cmnFileService.uploadCmnFile(request, dataRequest);

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

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		String strAttcFileNo = StringUtil.fixNull(param.getValue("strAttcFileNo"));
		String strFileSeq = StringUtil.fixNull(param.getValue("strFileSeq"));

		ParameterGroup dsFile = dataRequest.getParameterGroup("dsFile");
		// 삭제할 데이터가 데이터셋으로 넘어온 경우
		if (dsFile != null) {
			cmnFileService.deleteCmnFile(dsFile);
		} else {
			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("ATTC_FILE_NO", strAttcFileNo);
			mapParam.put("ATTC_SEQ", strFileSeq);

			cmnFileService.deleteCmnFileByAttcFileNo(mapParam);
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
	public View checkExist(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmDown");

		// String strGlobalFileStorePath =
		// EgovProperties.getProperty("Globals.fileStorePath");

		String strFilePath = param.getValue("strFilePath");// 다운로드받을 파일의 경로

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
	public View download(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmDown");

		String strFilePath = StringUtil.fixNull(param.getValue("strFilePath"));// 다운로드받을 파일의 경로
		String strFileNm = StringUtil.fixNull(param.getValue("strFileNm")); // 실제 파일명

		// 파일명이 없는 경우... 파일경로에서 파일명을 추출한다.
		if (strFileNm == null || "".equals(strFileNm)) {
			int index = strFilePath.lastIndexOf(File.separator);
			if (index == -1) {
				index = strFilePath.lastIndexOf("/");
			}
			strFileNm = strFilePath.substring(index + 1);
		}

		try {
			// String strGlobalFileStorePath =
			// EgovProperties.getProperty("Globals.fileStorePath");
			FileUtil.fileDownloadWrapper(strFilePath, request, response, strFileNm, "");
		} catch (IOException e) {
			// 파일다운로드시 오류가 발생했습니다.\n파일이 존재하지 않거나 네트워크가 불안정합니다.\n관리자에게 문의바랍니다
			// throw new AppWorksException("CMN003.CMN@CMN017", Alert.ERROR);
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
			// throw new AppWorksException("CMN003.CMN@CMN017", Alert.ERROR);
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
		return new JSONDataView();
	}

	@RequestMapping("/downloadAll.do")
	public View downloadAll(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		String strGlobalFileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		String strAttcFileNo = StringUtil.fixNull(dataRequest.getParameter("strAttcFileNo"));
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("ATTC_FILE_NO", strAttcFileNo);

		List<Map<String, Object>> fileList = cmnFileService.selectCmnFileList(mapParam);
		if (fileList != null && fileList.size() > 0) {
			String fileName = (String) fileList.get(0).get("FILE_NM");
			fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
			if (fileList.size() == 1) {
				fileName += ".zip";
			} else {
				fileName += " 외(" + (fileList.size() - 1) + "개).zip";
			}

			// 파일 다운로드 수행
			FileUtil.downloadAsZip(request, response, strGlobalFileStorePath, fileName, fileList);

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
				//System.out.println(e.getMessage());
			} catch (Exception e) {
				//System.out.println(e.getMessage());
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

		cmnFileService.imageUploadCmnFile(request, response, dataRequest);

	}
}
