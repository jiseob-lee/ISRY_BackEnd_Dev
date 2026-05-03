<%@page contentType="text/html" pageEncoding="UTF-8"  import="java.util.*" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<style type="text/css">
.my-box { border:1px solid; padding:10px; }
.mytable { border-collapse:collapse;width:100% }  
.mytable th, .mytable td { border:1px solid black; }
</style>
</head>

<br/>

<br/>

<!-- 
    ##############################################################################
    ############ 이 jsp 화일은  WAS Container의 자원을 조사 하기 위하여 만든
    ############ 모듈이며  최종적으로는 삭제 되어야 할 모듈입니다.
    ############ 시범운영 단계 까지  개발 진행을 지원하기 위한 모듈이며
    ############ 시범 운영 완료 시 삭제 처리 해야 합니다. !!!!
    ############ 송영일  2021.12.13  
    ##############################################################################

 -->

<%--

<div  class="my-box" >

  <center><h2>[ ISRY시스템 WAS 서버 정보 조회 ]</h2></center>

</div>

<br/>

<table  class="mytable">

<center><h2><span style="color:red">접속 컨테이너:::[<%=System.getProperty("Container.Name")%>]</span> </h2></center>


<tr>
<td>Real App Path</td><td><%= application.getRealPath("/") %></td>
</tr>

<%
Properties p = System.getProperties();
Enumeration keys = p.keys();
while (keys.hasMoreElements()) {
    String key = (String)keys.nextElement();
    String value = (String)p.get(key);
%>  

<tr>
<%  
    out.println("<td>"+key +"</td>"+ "<td>" + value+"</td>");
%>
    
</tr>
    
<%  
}%>

</table>

<br/>
<br/>



<table  class="mytable">
<center><h2><span style="color:red">쿠키 목록 </span> </h2></center>
<tr>
<%

   Cookie[] cookies = request.getCookies(); //쿠키 목록 받아오기

   for(Cookie cookie: cookies) {

     //모든 쿠키의 이름과 값 출력
%>
<td>
  <%=cookie.getName()%>
</td>
<td><%=cookie.getValue()%></td> 
<%
   }
%>    
</tr>
</table>




<br/>
<br/>
 


<table  class="mytable">
<center><h2><span style="color:red">세션 목록</span> </h2></center>
<tr>
<%

     String key;
     Object value;
     for (Enumeration e = session.getAttributeNames() ; e.hasMoreElements() ;) {
         key = (String) e.nextElement();
         value = session.getAttribute(key);
%>
<td>
  <%=key%>
</td>
<td><%=value%></td> 
<%
   }
%>    
</tr>
</table>

--%>

<br/>
<br/>
<br/>
<br/>
    
</body>
</html>
