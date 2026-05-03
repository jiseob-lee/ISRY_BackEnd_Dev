<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO" %>

<!-- jsp:forward page="/egovSampleList.do"/> -->

<%@ include file="/resource/raonnx/jsp/raonnx.jsp" %>

<%
UserDetailsVO loginVO = (UserDetailsVO)session.getAttribute("loginVO");
if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
    response.sendRedirect(request.getContextPath() + "/init.do");
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>청소년 안정망 시스템 로그인</title>

<script type="text/javascript" src="resource/KDepense7/js/kos-ng.js"></script>
<script type="text/javascript" src="resource/KDepense7/js/kos-ng.config.js"></script>
<script type="text/javascript" src="resource/KDepense7/js/jquery-1.11.3.min.js"></script>

<script type="text/javascript" src="<c:url value='/js/egovframework/slick.js' />"></script>

<script type="text/javascript">
function loginSunmit() {
    //alert("userId : " + $("#userId").val() + ", userPass : " + $("#userPass").val());
    //return;
    $.ajax({
        method: "POST",
        url: "<c:url value='/isry/itgcm/sysmgmt/userlogin/userLogin.do' />",
        data: { csrfToken : $("#csrfToken").val(), userId : $("#userId").val(), userPass : $("#userPass").val() },
        dataType: "json"
    })
    .done(function( msg ) {
        console.log("msg", msg);
        if (msg.loginResult == 1) {
            location.reload();
        } else {
            alert("로그인에 실패하였습니다.");
        }
        //alert( "Data Saved: " + msg );
    });
}
</script>

</head>

<body>

<div style="margin-top: 120px; margin-left: 150px;">
<table cellspacing="5" cellpadding="7">
<input type="text" id="csrfToken" name="csrfToken" value="<c:out value='${csrfToken}'/>">
<tr>
    <td colspan="3">청소년 안전망 시스템 통합 로그인</td>
</tr>
<tr>
    <td>아이디</td>
    <td><input type="text" name="userId" id="userId" onkeydown="if (event.keyCode == 13) { loginSunmit(); }" value="123" /></td>
    <td></td>
</tr>
<tr>
    <td>패스워드</td>
    <td><input type="password" name="userPass" id="userPass" onkeydown="if (event.keyCode == 13) { loginSunmit(); }" value="123" /></td>
    <td><input type="button" value="로그인" onclick="loginSunmit()" /></td>
</tr>
</table>
</div>

</body>
</html>