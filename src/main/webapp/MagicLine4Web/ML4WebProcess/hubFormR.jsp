<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>

<%
	String sResult = "";
	
	//간편인증
	String code = "";
	String msg = "";
	String txId = "";
	String userToken = "";
	
	//간편인증 결과값 가져옴
	code = URLDecoder.decode(request.getParameter("code"), "utf-8");

	if(code != null && code.length() > 0) {
		sResult = sResult+"- ResultCode ["+code+"]";
	}else{
		sResult=" - 데이타가 존재하지 않습니다..<br>\n";
	}
	//out.print(sResult);
	//System.out.println(sResult); 
%>
<jsp:include page="include/header.jsp"></jsp:include>
<jsp:include page="include/menu.jsp"></jsp:include>

<div id="middle">
	<h2>MagicLineHub Result</h2>
	<div id="workArea"><!-- DIV START  -->
		<table style="width: 100%; height:100%"  class="styledLeft">
		<thead>
		<tr>		
			<th colspan="2">Description</th>		
		</tr>
		</thead>
		<tr>
			<td>간편인증 사업자 서비스를 통해 얻은 결과값을 보여준다.</td>
		</tr>
		</table>
		
		<p>&nbsp;</p>
		
		<table style="width: 100%" class="styledLeft">
			<thead>
				<tr>
					<th colspan="2">Result</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="formRow">
					<table class="normal" cellspacing="0">
						<tr>
							<td>sResult:</td>
							<td><%=sResult%></td>
						</tr>						
					</table>
					</td>
				</tr>
			</tbody>
		</table>
		<p>&nbsp;</p>
	</div>
</div>
<script type="text/javascript">

</script>
<jsp:include page="include/footer.jsp"></jsp:include>