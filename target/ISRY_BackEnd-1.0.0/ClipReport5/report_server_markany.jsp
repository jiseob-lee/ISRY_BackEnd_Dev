<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="com.clipsoft.clipreport.server.service.ExportInfo"%>
<%@page import="com.clipsoft.clipreport.server.service.viewer.*"%>
<%@page import="com.clipsoft.clipreport.server.service.DeleteReport"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="com.markany.fps.*"%>
<%@ page import="com.markany.pdf.MaMake2DInPdf" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.text.*,java.net.InetAddress,java.text.SimpleDateFormat" %>
<%@include file="report_prop.jsp"%><%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//response.setHeader("Cache-Control", "max-age=0");
out.clear();
out=pageContext.pushBody();

String ClipType = request.getParameter("ClipType");
if(null != ClipType){
	if("newReport".equals(ClipType)){
		OOFToNewReport newReport = new OOFToNewReport();
		String responseValue = newReport.doPost(request, propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다.
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 이고 기본 예제[newReport.doPost(request, response, propertyPath);] 사용 했을 때 세션ID가 userID로 사용 됩니다.
		//newReport.doPost(request, response, propertyPath, "userID");
		
		//리포트key의 사용자문자열을 추가합니다.(문자숫자만 가능합니다.)
		//newReport.doPost(request, response, propertyPath, "userID", "userKey");
		newReport.setOutPutText(response, responseValue);
	}
	else if("pageCheck".equals(ClipType)){
		PageCountCheck pageCountCheck = new PageCountCheck();
		String responseValue = pageCountCheck.doPost(request,  propertyPath);
		pageCountCheck.setOutPutText(response, responseValue);
	}
	else if("makePage".equals(ClipType)){
		PageMaker pageMake = new PageMaker();
		String responseValue = pageMake.doPost(request,  propertyPath);
		
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//pageMake.doPost(request, propertyPath, "userID");
		pageMake.setOutPutText(response, responseValue);
	}
	else if("thumbnailPage".equals(ClipType)){
		ThumbnailPage thumbnailPage = new ThumbnailPage();
		String responseValue = thumbnailPage.doPost(request,  propertyPath);
		
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//thumbnailPage.doPost(request, propertyPath, "userID");
		thumbnailPage.setOutPutText(response, responseValue);
	}
	else if("TOCReport".equals(ClipType)){
		TOCReport tocReport = new TOCReport();
		String responseValue = tocReport.doPost(request,  propertyPath);
		
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//tocReport.doPost(request, propertyPath, "userID");
		tocReport.setOutPutText(response, responseValue);
	}
	else if("makeImagePage".equals(ClipType)){
		ImagePageMaker imagePageMake = new ImagePageMaker();
		imagePageMake.doPost(request, response,  propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//imagePageMake.doPost(request, response, propertyPath, "userID");
	}
	else if("exportViewImage".equals(ClipType)){
		response.setHeader("Cache-Control", "max-age=1800");
		ExportViewImage viewImage = new ExportViewImage();
		viewImage.doPost(request, response,  propertyPath);
	}
	else if("exportViewImageBase64".equals(ClipType)){
		ExportViewImage viewImage = new ExportViewImage();
		viewImage.doPostBase64(request, response,  propertyPath);
	}
	else if ("exportBarcodeImage".equals(ClipType)){
		ExportBarcodeImage barcodeImage = new ExportBarcodeImage();
		barcodeImage.doPost(request, response, propertyPath);
	}
	else if ("exportChartImage".equals(ClipType)){
		ExportChartImage chartImage = new ExportChartImage();
		chartImage.doPost(request, response, propertyPath);
	}
	else if ("exportDocumentCheckboxAndRadioImage".equals(ClipType)){
		ExportDocumentCheckAndRadioImage image = new ExportDocumentCheckAndRadioImage();
		image.doPost(request, response, propertyPath);
	}
	else if("deleteReport".equals(ClipType)){
		DeleteReport deleteReport = new DeleteReport();
		deleteReport.doPost(request);
	}
	else if("drilingUpdate".equals(ClipType)){
		DrilingUpdate drilingUpdate = new DrilingUpdate();
		String responseValue = drilingUpdate.doPost(request,  propertyPath);
		drilingUpdate.setOutPutText(response, responseValue);
	}
	
	else if("editableUpdate".equals(ClipType)){
		EditableUpdate editableUpdate = new EditableUpdate();
		String responseValue = editableUpdate.doPost(request,  propertyPath);
		editableUpdate.setOutPutText(response, responseValue);
	}
	else if("editableDocUpdate".equals(ClipType)){
		EditableDocUpdate editableDocUpdate = new EditableDocUpdate();
		String responseValue = editableDocUpdate.doPost(request,  propertyPath);
		editableDocUpdate.setOutPutText(response, responseValue);
	}
	else if("memoUpdate".equals(ClipType)){
		MemoUpdate memoUpdate = new MemoUpdate();
		String responseValue = memoUpdate.doPost(request,  propertyPath);
		memoUpdate.setOutPutText(response, responseValue);
	}
	
	else if("fileDownloadCheck".equals(ClipType)){
		FileDownLoadCheck fileCheck = new FileDownLoadCheck();
		String responseValue = fileCheck.doPostToString(request, propertyPath);
		fileCheck.setOutPutText(response, responseValue);
	}
	else if("PDFPrint".equals(ClipType)){
		PDFPrint pdfPrint = new PDFPrint();
		//pdfPrint.doPostMarkAny(request, response,  propertyPath);
		//전체 페이지 모두 바코드 사용
		pdfPrint.doPostMarkAnyAllPage(request, response,  propertyPath);
	}
	else if("PDFPrintDownload".equals(ClipType)){
		PDFPrintDownload pdfPrintDownload = new PDFPrintDownload();
		ExportInfo exportInfo = pdfPrintDownload.doPostMarkAny(request, response,  propertyPath);

	    
        //**********MarkAny print start 연동부분*******************
        
        InetAddress localhost = InetAddress.getLocalHost();
        InetAddress.getAllByName(localhost.getCanonicalHostName()); 
        
        //System.out.println("ip명:"+InetAddress.getLocalHost().getHostAddress());
        //System.out.println("host명"+localhost.getCanonicalHostName());
        
        String hostName= localhost.getCanonicalHostName();
        String strCurrentPath ="";
        String strServerIp = "";  
        
        if("i-ry-bwas1".equals(hostName) || "i-ry-bwas2".equals(hostName)){
            // 운영서버(인터넷망)
            strCurrentPath  = "/app/report.war/markany/pdf/";
            // MarkAny 서버모듈 => 운영서버 설치 IP
            strServerIp     = "10.188.91.187";
        } else if("g-ry-bwas1".equals(hostName) || "g-ry-bwas2".equals(hostName)){
            // 운영서버(인터넷망)
            strCurrentPath  = "/app/report.war/markany/pdf/";
            // MarkAny 서버모듈 => 운영서버 설치 IP
            strServerIp     = "116.67.91.187";
        } else {
          // 개발서버
          strCurrentPath  = "/mw/reportwas/webapps/ClipReport5/markany/pdf/";
          // MarkAny 서버모듈 => 개발서버 설치 IP
          strServerIp     = "10.33.2.59";
        }

        //String      strPdfFilePath          = "/app/report.war/markany/pdf/reportpdf.pdf";
        //String      strHtmlPath             = "/app/report.war/markany/pdf/reportpdf.dat";

        String      strPdfFilePath          = exportInfo.getExportfilePath();
        String      strHtmlPath             = exportInfo.getExportDataFilePath();
 
	
	   	/*
		System.out.println(exportInfo.getExportfilePath());
		System.out.println(exportInfo.getExportDataFilePath());
		System.out.println("pageCount:" + exportInfo.getPageCount());
		System.out.println("left:" +exportInfo.getLeft()+" right:" + exportInfo.getRight() + " top:" + exportInfo.getTop() + " bottom:" + exportInfo.getBottom());
		System.out.println("PaperOrientation:" + exportInfo.getPaperOrientation());
		*/

		String  strPdfDataType      = "0";
		String  strFunctionFlag     = "BMP";
		//String  strServerIp     = "127.0.0.1";  // MarkAny 서버모듈 설치 IP
		//String  strServerIp     = "10.33.2.59";  // MarkAny 서버모듈 => 개발서버 설치 IP
	    //String  strServerIp     = "10.188.91.187";  // MarkAny 서버모듈 => 운영서버 설치 IP
		int    	iServerPort     = 18320;        // MarkAny 서버모듈 PORT
		int		i2DCellCount    = 350;
		int		i2DCellRow      = 110;
		int		iType           = 0;
		int		i2DLandCellCount = 480; //25
		int		i2DLandCellRow = 90; //2
		
		
		String	strMobileCodeData = "0";
		String	strSignCompany = "INIT";
		String	strReportCompany = "RP4#";
		//30  30 500 55 (바코드 복사방지마크(원본))
		int i2DPosX = 40; //105; 40 
		int i2DPosY = 40;  //35; 60
		int iCDPosX = -200;  //25; -200
		int iCDPosY = -200;  //35; -200
		int        iRetError = 0;
		String     strCDFilePath                    = strCurrentPath + "copy_detector.bmp"; //복사방지마크가 저장되어있는 경로 ( 경로 수정이 필요할 수 있음 )
		String     str2DFilePath                    = strHtmlPath + "_ma_2d"; //생성된 2D 바코드가 저장될 경로 ( 경로 수정이 필요할 수 있음 )
		String     strResultPdfFilePath             = strHtmlPath + "_ma.pdf"; // 2D바코드가 포함된 PDF가 생성될 경로 ( 경로 수정이 필요할 수 있음 )
		String     strPdfErrorFileTemplet           = strCurrentPath + "maerr_"; // 에러pdf파일
		String     strPdfErrorFilePath = new String();
		//maerr_pdf_input.pdf    : pdf 원문 생성 오류(rex에서 전달한 원문 pdf가 존재 하지 않음)
		//maerr_pdf_output.pdf   : pdf 변환 오류
		String  dstFile    = new String();
		// Get Html Data
		File FileHtml = new File(strHtmlPath); 
		byte byteHtml[] = new byte[(int)FileHtml.length()]; 

		if ( FileHtml.isFile() ) 
		{

			try
			{
				BufferedInputStream  fin = new BufferedInputStream(new FileInputStream(FileHtml)); 
				fin.read(byteHtml);
				fin.close();
			}
			catch( Exception e )
			{
			}
			 
			MaMake2DInPdf clMaMake2DInPdf = new MaMake2DInPdf();
			String	strLandscapeInfo = clMaMake2DInPdf.strPageLandscapeInfo( 	strPdfFilePath,
																				i2DCellCount,
																				i2DCellRow,
																				i2DLandCellCount,
																				i2DLandCellRow );
			//System.out.println("strLandscapeInfo = " + strLandscapeInfo);
			if( strLandscapeInfo.equals( clMaMake2DInPdf.DEF_ALL_PAGE_PORTRAIT ) ) {					// All page has portrait
				//System.out.println("All page has portrait....");
				strLandscapeInfo = "";
			} else if( strLandscapeInfo.equals( clMaMake2DInPdf.DEF_ALL_PAGE_LANDSCAPE ) ) {		// All page has landscape
				//System.out.println("All page has landscape....");
				strLandscapeInfo = "";
				i2DCellCount = i2DLandCellCount;
				i2DCellRow = i2DLandCellRow;
			} else {	// Some page has portrait, Some page has landscape.....			
				//System.out.println("Some page has portrait...., Some page has landscape");
			}
		
			// create instance
			MaFpsMake2DCode clMaFpsMake2DCode = new MaFpsMake2DCode();
			int iRet = 0;
			String	strRet = new String();
			try
			{

				
				strRet = clMaFpsMake2DCode.iGen2DCodeNonActive(
								strServerIp,
								iServerPort,
								"0",
								i2DCellCount,
								i2DCellRow,
								byteHtml,
								strSignCompany,
								strReportCompany,
								0, 
								"0",
								"0",
								"",
								"0",
								"0",
								strMobileCodeData.getBytes(),
								"",
								"",
								strFunctionFlag,
								"",
								str2DFilePath,
								strMobileCodeData.getBytes(),
								strMobileCodeData.getBytes(),
								"",
								strLandscapeInfo );
								
				String strRetCode = new String( strRet.substring( 0, 4 ) );
				int      iRetCode = Integer.parseInt( strRetCode );

				if( iRetCode == 0 ) {	
					clMaMake2DInPdf.iInsertImageToPdfEx(   
								strPdfFilePath,
								1,						// pi2DUse
								str2DFilePath,		// pstr2DFilePath
								450,					// pi2DDpi
								i2DPosX,
								i2DPosY,
								0, 						// piVCUse
								"",			
								0,
								0,
								0,
								"",
								0,
								0,
								0,
								0,
								1, 						// piCDUse
								strCDFilePath,
								300,					// piCDDpi
								iCDPosX,
								iCDPosY,
								strResultPdfFilePath,
								"",	// password
								"", // pdf make company
								0, // source pdf encrypt flag  -> pdf가 암호화되어서 넘어오는 경우 : 특정 사이트에서 사용됨
								0, //Integer.parseInt(clientOptionMap.get("AUTOPRINT") + ""), // pdf auto-print flag -> pdf를 열면, 바로 출력 다이얼로그가 나오도록...
								0, // pdf has different barcode size per page -> 페이지별로 2D 바코드의 크기가 다를 경우
								1); // make pdf release
					 File MAPdfFilePath = new File(strResultPdfFilePath); 
					if (MAPdfFilePath.isFile()) {
					  dstFile =    strResultPdfFilePath;
					}
					else {
					  strPdfErrorFilePath = strPdfErrorFileTemplet + "output.pdf";
					  dstFile = strPdfErrorFilePath;
					}
				}
				else {
					strPdfErrorFilePath = strPdfErrorFileTemplet + "server.pdf";
					dstFile = strPdfErrorFilePath;
				}
			}
			catch( UnsatisfiedLinkError e )
			{
				System.err.println("error while binding method");
				System.err.println("\t:"+e.toString());
				System.exit(1);
			}
		}
		else
		{
			//[에러처리] 리포트 pdf파일이 생성되지 않을경우
			strPdfErrorFilePath = strPdfErrorFileTemplet + "output.pdf";
			dstFile =    strResultPdfFilePath;
		}

		String contentType = "pdf";
		String contentDisposition = "inline";
		if( request.getHeader("User-Agent").indexOf("Firefox") != -1 ){
			contentType = "octet-stream";
			contentDisposition = "attachment";
		}

		//변환된 pdf 클라이언트로 파일 내리기
			File downloadFile = new File(dstFile);
			FileInputStream inStream = new FileInputStream(downloadFile);
			OutputStream outStream = response.getOutputStream();
		
			
			out.clear();
			out = pageContext.pushBody();
			
			response.reset();
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Pragma","no-cache;");
			response.setHeader("Expires", "-1;");
			response.setContentType("application/" + contentType);
			response.setHeader("Content-Disposition", contentDisposition + "; filename=" + UUID.randomUUID().toString() + ".pdf");
			  
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			
			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inStream.close();
			outStream.close();     
		
		//생성한 파일 삭제
		pdfPrintDownload.deleteFile(request, exportInfo, propertyPath);	
		//마크애니 생성한 파일 지우기
		if( dstFile.equals(strPdfErrorFilePath) )
		{
		  System.out.println("Markany ERRR....");
		}
		else
		{
		  //File    filePDFFile = new File( dstFile );
		  //if( filePDFFile.exists() ) filePDFFile.delete();
		}
	//**********MarkAny print end 연동부분*******************
	}
	else if("PDFPrintFrame".equals(ClipType)){
		PDFPrintFrame pdfPrintFrame = new PDFPrintFrame();
		pdfPrintFrame.doPost(request, response,  propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//pdfPrintDownload.doPost(request, response, propertyPath, "userID");
	}
	else if("HTMLPrint".equals(ClipType)){
		HTMLPrint htmlPrint = new HTMLPrint();
		htmlPrint.doPost(request, response,  propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//htmlPrint.doPost(request, response, propertyPath, "userID");
	}
	else if("HTMLPrintDownload".equals(ClipType)){
		HTMLPrintDownload htmlPrintDownload = new HTMLPrintDownload();
		htmlPrintDownload.doPost(request, response,  propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//htmlPrintDownload.doPost(request, response, propertyPath, "userID");
	}
	else if("saveExport".equals(ClipType)){
		SaveExport saveExport = new SaveExport();
		saveExport.doPost(request, response,  propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//saveExport.doPost(request, response, propertyPath, "userID");
	}
	else if("saveExportDownload".equals(ClipType)){
		SaveExportDownload saveExportDownload = new SaveExportDownload();
		saveExportDownload.doPost(request, response,  propertyPath);
		//리포트의 특정 사용자 ID를 부여합니다. 
		//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
		//saveExportDownload.doPost(request, response, propertyPath, "userID");
	}
	else if("makeDocumentPage".equals(ClipType)){
		MageDocumentPage documentPage = new MageDocumentPage();
		documentPage.doPost(request, response,  propertyPath);
	}
	else if("DocumentPageView".equals(ClipType)){
		MakeDocumentJSON documentPage = new MakeDocumentJSON();
		documentPage.doPost(request, response,  propertyPath);
	}
	else if("DocumentPagePrint".equals(ClipType)){
		MakeDocumentJSON documentPage = new MakeDocumentJSON(12);
		documentPage.doPost(request, response,  propertyPath);
	}
	else if("DocumentPageThumbnail".equals(ClipType)){
		MakeDocumentJSON documentPage = new MakeDocumentJSON(13);
		documentPage.doPost(request, response,  propertyPath);
	}
	else if("DocumentPageOnly".equals(ClipType)){
		MakeDocumentJSON documentPage = new MakeDocumentJSON(14);
		documentPage.doPost(request, response,  propertyPath);
	}
	else if("docDownload".equals(ClipType)){
		DocDownload downloadFile = new DocDownload();
		downloadFile.doPost(request, response, propertyPath);
	}
	else if("PageSizeInfo".equals(ClipType)){
		PageSizeInfo pageSizeInfo = new PageSizeInfo();
		String responseValue = pageSizeInfo.doPost(request, propertyPath);
		pageSizeInfo.setOutPutText(response, responseValue);
	}
	else if ("currentCookie".equals(ClipType)){
		CurrentCookie currentCookie = new CurrentCookie();
		String outPutText = currentCookie.doPost(request);
		currentCookie.setOutPutText(response, outPutText);
	}
}
%>