<!DOCTYPE>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<title>Home</title>
	<script type="text/javascript" src="../resource/KDepense7/js/kos-ng.js"></script>
	<script type="text/javascript" src="../resource/KDepense7/js/kos-ng.config.js"></script>
	<script type="text/javascript" src="../resource/KDepense7/js/jquery-1.11.3.min.js"></script>
</head>
<body>
	<form name="loginForm" method="post" action="E2EProc.jsp">
		<table>
				<tr>
					<td>ID: </td>
					<td><input name="userId" type="text"></td>
				</tr>
				<tr>
					<td>PW: </td>
					<td><input name="userPw" type="password"></td>
				</tr>
				<tr>
					<td>ID (E2E): </td>
					<td><input name="inputIdE2E" type="text"></td>
				</tr>
				<tr>
					<td>PW (E2E): </td>
					<td><input name="inputPwE2E" type="password"></td>
				</tr>
		</table>
	</form>
	<button id="procBtn">제출</button>
	<script type="text/javascript">	
	
	$(document).ready(function () {
		regFormEle_K("loginForm.inputIdE2E","none");
		regFormEle_K("loginForm.inputPwE2E","none");
	});
	
	$("#procBtn").click(function(){
		KOS.prepareSubmit(loginForm);
		loginForm.submit();
	});
	</script>
</body>
</html>
