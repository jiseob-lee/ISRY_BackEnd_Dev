<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>    
<%@page import="kr.co.kings.e2e.filters.E2EConfig"%>
<%@page import="kr.co.kings.e2e.filters.E2EDecrypter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		font-size:22px;
		margin-bottom:20px;
	}
	
	#table2, #table2 tr, #table2 td{
		border:1px solid black;
	}
	#table2 .key{
		width:200px;
	}
	#table2 .value{
		width:450px;
	}
	#table2 td{
		padding-left:8px;
	}
</style>
</head>
<body>

	<%
	Enumeration<String> paramEnum = request.getParameterNames();

	String id_E2E = null;
	String pwd_E2E = null;
	String value = null;
	String userid = request.getParameter("userId");
	String userpw = request.getParameter("userPw");
	while(paramEnum.hasMoreElements()){
		String param = paramEnum.nextElement().toString();
		System.out.println(param +" : "+request.getParameter(param));
			
		if(param.contains("_KDFEXT_")){		
			value = request.getParameter(param);
			param.substring(8);
				
			String seed = request.getParameter(E2EConfig.getSeedPrefix());
			E2EDecrypter decrypter = new E2EDecrypter(seed);
			if(value!=null && value !=""){
				if(param.contains("Id")){
					System.out.println("value : "+value);
					id_E2E = decrypter.decryptExt(value);
				}else{
					pwd_E2E = decrypter.decryptExt(value);
				}
			}
		}
	}
	%>
	<h1>Kings Online Security React Test Page</h1>
	<table>
		<tr>
			<td>ID: </td>
			<td><% out.print(userid); %></td>
		</tr>
		<tr>
			<td>PW: </td>
			<td><% out.print(userpw); %></td>
		</tr>
		<tr>
			<td>ID (E2E): </td>
			<td><%if(!(id_E2E == null)) out.print(id_E2E+"<span style='color:green;'> (복호화성공)</span>"); %></td>
		</tr>
		<tr>
			<td>PW (E2E): </td>
			<td><%if(!(pwd_E2E == null)) out.print(pwd_E2E+"<span style='color:green;'> (복호화성공)</span>"); %></td>
		</tr>
	</table>
	
	<table id="table2">
		<tr>
			<td class="key">Key</td>
			<td class="value">Value</td>
		</tr>
		<tr>
			<td>UserId</td>
			<td><% out.print(userid); %></td>
		</tr>
		<tr>
			<td>UserPw</td>
			<td><% out.print(userpw); %></td>
		</tr>
		<tr>
			<td>UserId(E2E)</td>
			<td><% if(!(id_E2E == null)) out.print(id_E2E); %></td>
		</tr>
		<tr>
			<td>UserPw(E2E)</td>
			<td><% if(!(pwd_E2E == null))out.print(pwd_E2E); %></td>
		</tr>
	</table>
</body>
</html>