<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String linkUrl = request.getParameter("linkurl");
	response.sendRedirect(linkUrl);
%>
