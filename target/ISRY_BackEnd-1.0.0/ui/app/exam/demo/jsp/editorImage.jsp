
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>image</title>
</head>
<body>
<%
	String fileNm = request.getParameter("fileNm");
	
	String realPath = session.getServletContext().getRealPath("/");	
	String imagePath = realPath + "temp" + File.separator+ fileNm;
		
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	
	java.io.File file = new java.io.File(imagePath);
	byte b[] = new byte[(int) file.length()];
	
	if (file.isFile()) {
		out.clear();
		out = pageContext.pushBody();
	
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
	
		int read = 0;
		while ((read = fin.read(b)) != -1) {
			outs.write(b, 0, read);
		}
		outs.close();
		fin.close();
	} 
%>
<script type="text/javascript">
console.log("test");
</script>
</body>
</html>