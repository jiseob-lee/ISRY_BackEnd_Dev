package com.tomatosystem.exbuilder6.core.exbuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cleopatra.export.CSVExporter;
import com.cleopatra.export.Exporter;
import com.cleopatra.export.ExporterFactory;
import com.cleopatra.export.ExporterFactory.EXPORTTYPE;
import com.cleopatra.export.source.DataSource;
import com.cleopatra.export.source.JSONDataSourceBuilder;
import com.cleopatra.export.target.HttpResponseOutputTarget;
import com.cleopatra.export.target.OutputTarget;
import com.cleopatra.protocol.data.DataRequest;

import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
public class CleopatraFileExportController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "personalInfoService")
	private PersonalInfoService personalInfoService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/export/{fileName}.csv")
	public void exportCSV(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("fileName") String fileName, DataRequest dataRequest) throws IOException {

		String saveFileName = dataRequest.getParameter("filename");
		
		log.debug("#### saveFileName : " + saveFileName);
		
		// 엑셀 파일명에 날짜 정보를 입력합니다. 		
		LocalDate now = LocalDate.now();
				
		String downloadFileName = saveFileName + "[" + now + "].csv";
		
		//String downloadFileName = fileName + ".csv";
		
		downloadFileName = this.encodingDownloadFileName(request, downloadFileName);

		//사용자 세션이 없으면.. 엑셀 다운로드 불가토록...
		HttpSession session = request.getSession(false);
		
		//		if(session == null) {
		//			//사용자 세션이 존재 하지 않습니다.
		//			throw new AppWorksException("CMN003.CMN@CMN003", Alert.ERROR);
		//		}
		
		response.setContentType("text/csv;charset=utf-8");

		response.addHeader("Content-Disposition", downloadFileName);

		Integer downloadCount = (Integer)session.getAttribute("downloadCount");
		if (!"단위 테스트 게시판 목록".equals(saveFileName) && (downloadCount == null || downloadCount != 0)) {
			return;
		}
		
		//if (!"단위 테스트 게시판 목록".equals(saveFileName) && !isExcelDownloadRegistered(request, saveFileName)) {
			//return;
		//}
		
		//if (!"단위 테스트 게시판 목록".equals(saveFileName) && isPersonalInfo(request, saveFileName)) {
			
			String smsToken1 = (String)session.getAttribute("smsToken1");
			String smsToken2 = (String)session.getAttribute("smsToken2");
			String downloadReason = (String)session.getAttribute("downloadReason");

			if (smsToken1 == null || "".equals(smsToken1) || !smsToken1.equals(smsToken2) || downloadReason == null || "".equals(downloadReason.trim())) {
				return;
			} //else {
				//recordPersonalInfoDownloadReason(request, fileName);
			//}
		//}
							
		session.setAttribute("downloadCount", 1);
		session.setAttribute("downloadReason", "");
		session.setAttribute("smsToken1", "");
		session.setAttribute("smsToken2", "");
				
		this.export(request, response, fileName, EXPORTTYPE.CSV);
		
	}
	
	@RequestMapping("/export/{fileName}.xls")
	public void exportXLS(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		
		String downloadFileName = fileName + ".xls";
		
		downloadFileName = this.encodingDownloadFileName(request, downloadFileName);
		
		//사용자 세션이 없으면.. 엑셀 다운로드 불가토록...
		HttpSession session = request.getSession(false);

		
//		if(session == null) {
			//사용자 세션이 존재 하지 않습니다.
//			throw new AppWorksException("CMN003.CMN@CMN003", Alert.ERROR);
//		}
		
		response.setContentType("application/vnd.ms-excel");
		
		response.addHeader("Content-Disposition", downloadFileName);
		
		this.export(request, response, fileName, EXPORTTYPE.XLS);
		
	}
	
	@RequestMapping("/export/{fileName}.xlsx")
	public void exportXLSX(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("fileName") String fileName, DataRequest dataRequest) throws IOException {
		
		String saveFileName = dataRequest.getParameter("filename");
		
		log.debug("#### saveFileName : " + saveFileName);
		
		// 엑셀 파일명에 날짜 정보를 입력합니다. 		
		LocalDate now = LocalDate.now();
				
		String downloadFileName = saveFileName + "[" + now + "].xlsx";
		
		downloadFileName = this.encodingDownloadFileName(request, downloadFileName);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		response.addHeader("Content-Disposition", downloadFileName);
		
		HttpSession session = request.getSession(false);

		//사용자 세션이 없으면.. 엑셀 다운로드 불가토록...
		//		if(session == null) {
		//사용자 세션이 존재 하지 않습니다.
		//			throw new AppWorksException("CMN003.CMN@CMN003", Alert.ERROR);
		//		}

		Integer downloadCount = (Integer)session.getAttribute("downloadCount");
		if (!"단위 테스트 게시판 목록".equals(saveFileName) && (downloadCount == null || downloadCount != 0)) {
			return;
		}
		
		//if (!"단위 테스트 게시판 목록".equals(saveFileName) && !isExcelDownloadRegistered(request, saveFileName)) {
			//return;
		//}
		
		//if (!"단위 테스트 게시판 목록".equals(saveFileName) && isPersonalInfo(request, saveFileName)) {
			
			String smsToken1 = (String)session.getAttribute("smsToken1");
			String smsToken2 = (String)session.getAttribute("smsToken2");
			String downloadReason = (String)session.getAttribute("downloadReason");

			if (smsToken1 == null || "".equals(smsToken1) || !smsToken1.equals(smsToken2) || downloadReason == null || "".equals(downloadReason.trim())) {
				return;
			} //else {
				//recordPersonalInfoDownloadReason(request, fileName);
			//}
		//}
							
		session.setAttribute("downloadCount", 1);
		session.setAttribute("downloadReason", "");
		session.setAttribute("smsToken1", "");
		session.setAttribute("smsToken2", "");
		
		this.export(request, response, saveFileName, EXPORTTYPE.SXLSX);
	}
	
	private void export(HttpServletRequest request, HttpServletResponse response, String fileName, EXPORTTYPE type) throws IOException {
		
		response.setCharacterEncoding("utf-8");
						
		String newFileName = URLDecoder.decode(fileName, "utf-8");
		
		DataSource dataSource = JSONDataSourceBuilder.build(request, newFileName);
		
		OutputTarget outputTarget = new HttpResponseOutputTarget(response);
		
		ExporterFactory exporterFactory = ExporterFactory.getInstance();
		
		Exporter exporter = exporterFactory.getExporter(type);
		
		if(type == EXPORTTYPE.CSV){
			//((CSVExporter)exporter).setAutoWrap(false);
			((CSVExporter)exporter).setAutoWrap(true);
		}
		
		exporter.export(dataSource, outputTarget);
		
		response.flushBuffer();
	}
	
	private String encodingDownloadFileName(HttpServletRequest request, String psDownloadFileName) throws UnsupportedEncodingException {
		
		String downloadFileName = psDownloadFileName;
		String userAgent = request.getHeader("User-Agent");

		downloadFileName = URLEncoder.encode(downloadFileName, "utf-8");
		downloadFileName = downloadFileName.replaceAll("\\+","%20");
		
		//if (userAgent.contains("MSIE") || userAgent.contains("Chrome") || (userAgent.contains("Windows") && userAgent.contains("Trident"))) {
		
        if (userAgent.contains("Firefox")) {
        	
        	downloadFileName = "attachment; filename*=\"" + downloadFileName + "\";";
        	
        } else {
        
        	downloadFileName = "attachment; filename=\"" + downloadFileName + "\";";
        }
		
		return downloadFileName;
	}
	
	/*
	private boolean isExcelDownloadRegistered(HttpServletRequest request, String fileName) {
		
		HttpSession session = request.getSession();
		Integer menuNo = (Integer)session.getAttribute("menuNo");
		String menuUrl = (String)session.getAttribute("menuUrl");
		
		Integer isExcelDownloadRegistered = 0;
		try {
			isExcelDownloadRegistered = personalInfoService.isExcelDownloadRegistered(menuNo, fileName, menuUrl);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return isExcelDownloadRegistered > 0 ? true : false;
	}
	
	private boolean isPersonalInfo(HttpServletRequest request, String fileName) {

		HttpSession session = request.getSession();
		Integer menuNo = (Integer)session.getAttribute("menuNo");
		String menuUrl = (String)session.getAttribute("menuUrl");

		boolean isPersonalInfo = false;
		try {
			isPersonalInfo = personalInfoService.isPersonalInfo(fileName, menuNo, menuUrl);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return isPersonalInfo;
	}
	
	private void recordPersonalInfoDownloadReason(HttpServletRequest request, String fileName) {
		
		HttpSession session = request.getSession();

		String menuUrl = (String)session.getAttribute("menuUrl");
		String downloadReason = (String)session.getAttribute("downloadReason");

		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		Map<String, String> map = new HashMap<>();
		
		String menuNm = "";
		try {
			menuNm = personalInfoService.getMenuNm(menuUrl);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		
		map.put("menuUrl", menuUrl);
		map.put("menuName", menuNm);
		map.put("downloadReason", downloadReason);
		map.put("userId", userId2);
		map.put("fileName", fileName);
		
		try {
			personalInfoService.recordPersonalInfoDownloadReason(map);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	*/
	
}
