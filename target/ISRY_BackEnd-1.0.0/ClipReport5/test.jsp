<%@page import="com.clipsoft.org.json.simple.JSONObject"%>
<%@page import="com.clipsoft.org.json.simple.parser.JSONParser"%>
<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Set"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@ page import="java.text.*,java.net.InetAddress,java.text.SimpleDateFormat" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
// 시스템 호스트 정보 
InetAddress localhost = InetAddress.getLocalHost();
InetAddress.getAllByName(localhost.getCanonicalHostName());
String hostName= localhost.getCanonicalHostName();

OOFDocument oof = OOFDocument.newOOF();
String filePath = request.getParameter("filePath");
String strParams = request.getParameter("params");


//[ test 예제 샘플 소스 주소 ]

//1.  C:\development\workspace\ISRY_FrontEnd\clx-src\app\exam\dev\clipReportSample.clx
//2.  C:\development\workspace\ISRY_FrontEnd\clx-src\udc\cmn\cmnReport.clx

System.out.println("파라미터 정보 : " + strParams);

OOFFile file = oof.addFile("crf.root", "%root%/crf/" + filePath);

if(strParams != null){
	JSONParser parser = new JSONParser();
	JSONObject jsonParams = (JSONObject) parser.parse(strParams);
	
	Set<String> keys = jsonParams.keySet();
	
	for(String key : keys){
		String value = (String) jsonParams.get(key);
		oof.addField(key, value);
	}
}
oof.addConnectionData("*", "tibero2");

%><%@include file="report_prop.jsp"%><%
//?�션???�용?�여 리포?�키?�을 관리하지 ?�는 ?�션
//request.getSession().setAttribute("ClipReport-SessionList-Allow", false);
String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);
//리포?�의 ?�정 ?�용??ID�?부?�합?�다.
//clipreport5.properties ??useuserid ?�션??true ???�만 ?�용?�니?? 
//clipreport5.properties ??useuserid ?�션??true ?�고 기본 ?�제[String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);] ?�용 ?�을 ???�션ID가 userID�??�용 ?�니??
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "userID");

//리포?�key???�용?�문?�열??추�??�니??(문자?�자�?가?�합?�다.)
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "", "usetKey");

//리포?��? ?�???�토리�?�?지?�하???�성?�니??
//String resultKey =  ReportUtil.createReportByStorage(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "rpt1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../resource/clip/css/clipreport5.css">
<link rel="stylesheet" type="text/css" href="../resource/clip/css/UserConfig5.css">
<link rel="stylesheet" type="text/css" href="../resource/clip/css/font.css">

<script type='text/javascript' src='../resource/clip/js/jquery-1.11.1.js'></script>
<script type='text/javascript' src='../resource/clip/js/clipreport5.js'></script>
<script type='text/javascript' src='../resource/clip/js/UserConfig5.js'></script>
<script type='text/javascript'>
	
function html2xml(divPath){	
    var reportkey = "<%=resultKey%>";
    
    var hostName = "<%=hostName%>";
    
    if("i-ry-bwas1" == hostName || "i-ry-bwas2" == hostName){
        // 운영서버(인터넷망)
        var report = createReport("./report_server_markany.jsp", reportkey, document.getElementById(divPath));
        
      } else {

        var report = createReport("./report_server.jsp", reportkey, document.getElementById(divPath));
    }
    
	//var report = createReport("./report_server_markany.jsp", reportkey, document.getElementById(divPath));
   
	//리포???�행
    report.view();
}
</script>
</head>
<body onload="html2xml('targetDiv1')">
<div id='targetDiv1' style='position:absolute;top:5px;left:5px;right:5px;bottom:5px;'>
	<span style="visibility: hidden; font-family:나눔고딕">.</span>
	<span style="visibility: hidden; font-family:NanumGothic">.</span>
</div>
</body>
</html>
