<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>TEST - Sample</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/egovframework/bootstrap.min.css">
        <style type="text/css">
            .errormsg {
                color: red;
            }
        </style>
		<script src="/js/egovframework/jquery-3.5.1.min.js"></script>         
		<script type="text/javaScript" defer="defer">
/* ********************************************************
 * 처리 함수
 ******************************************************** */
function f_loginTest() 
{		
		var params = $("#progrmForm").serialize();

	    $.ajax({
            type : "POST",
            url : "/coung/jsonTest.do",
            data : params,
            dataType : "json",
            async: false,
            success : function(data) {
            	//alert("통신데이터 값 : " + JSON.stringify(data));
				var result = data.result;
				var message = data.message;
				var flag = data.flag;
                alert("결과:"+result);
				alert("메세지:"+message);
				alert(flag);
            },
			error : function() {
	            alert('통신실패!!');
	        }
        });
		
	}	

</script>
       
    </head>
    <body>
        <div class="container">
            <h2 align="center" class="text-primary">MVC Form Example</h2>
            <hr />
            <div> </div>
 
            <form:form name="progrmForm" id="progrmForm" action="" method="POST" modelAttribute="memberTestVO">
                    
                 <div class="form-group">
                    <label>ID :</label><form:input path="id" size="30" cssClass="form-control" placeholder="Enter ID" />             
                    <small><form:errors path="id" cssClass="errormsg" /></small>
                 </div>
                 <div class="form-group">
                    <label>Password:</label><form:password path="pw" size="30" cssClass="form-control" placeholder="Enter password" />
                    <small><form:errors path="pw" cssClass="errormsg" /></small>
                 </div>
                 <div class="form-group">
                    <button type="button" onclick="javascript:f_loginTest();" class="btn btn-primary">확인</button>
                 </div>
                 
                 
                 <input type="hidden" id="csrfToken" name="csrfToken" value="<c:out value='${csrfToken}'/>">
                 
            </form:form>
        </div>
    </body>
</html>