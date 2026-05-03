<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>nxweb 데모</title>

<%String TouchEnNxpath = "/raonnx";%>
<!-- TouchEnNx Start-->
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/cmn/json2.js'></script>
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/cmn/TouchEnNx.js'></script>
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/cmn/TouchEnNx_exproto.js'></script>
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/cmn/TouchEnNx_install.js'></script>
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/cmn/TouchEnNx_daemon.js'></script>
<!-- TouchEnNx End-->

<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/nxWeb/js/nxweb_config.js'></script>
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/nxWeb/js/TouchEnNxWeb_Interface.js'></script>
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/nxWeb/js/TouchEnNxWeb.js'></script>

<!-- TouchEnNx Start-->
<script type='text/javascript' charset='utf-8' src='http://10.33.2.59:8880/ISRY_BackEnd/resource<%=TouchEnNxpath%>/cmn/TouchEnNx_loader.js'></script>
<!-- TouchEnNx End-->

</head>
<body>
	id : <input type="text"><br/>
	pwd1 : <input type="password" id="pwd" name="pwd" /><br/>

</body>
</html>