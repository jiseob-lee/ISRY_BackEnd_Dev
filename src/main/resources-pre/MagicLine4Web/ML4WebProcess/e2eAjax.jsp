<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.dreamsecurity.magice2e.MagicE2E" %>
<%@ page import="com.dreamsecurity.magicline.util.Base64" %>
<jsp:include page="include/header.jsp"></jsp:include>
<jsp:include page="include/menu.jsp"></jsp:include>
<%
	String sessionString = "";
	int result = 0;
	StringBuffer sbCert = new StringBuffer();
	//MagicE2E.setConfPath("C:/Users/jung/eclipse-workspace/MagicLine4Web_Ratato/WebContent/WEB-INF/magicline/config/");
	// 세션에 값이 있는지 확인
	MagicE2E temp = ( MagicE2E ) session.getAttribute("Magie2e");
	if( temp == null ){
		MagicE2E ml = new MagicE2E( sbCert );
		sessionString = sbCert.toString();
		session.setAttribute( "Magie2e", ml );
	}else{
		result = temp.open( sbCert );
		if( result == 0 ){
			sessionString = sbCert.toString();	
		}else{
			temp.close();
			session.invalidate();
		}
	}
%>
<script src="../ML4Web/js/crypto/magicjs_1.2.1.0.min.js"></script>
<script src="../ML4Web/js/magic_e2e.js"></script>
<script type="text/javascript">
function sendE2E(){
	var ml = new MagicE2E(<%=sessionString%>);
	
	var encData = ml.Encrypt(queryString($('#reqForm').serialize(), "`"));
	
	$.ajax({ // 데이터 전송
		url:"./e2eAjaxR.jsp",
		type:"POST",
		dataType:"json",
		data:{encData : encData},
		success:function(data){
			$("#eText").val(JSON.stringify(data));
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function queryString(ars1, ars2){
	var lo_array1 = ars1.split("&");
	var lo_array2 = new Array;
	var lo_result = "";
	var division = ars2;
	
	for(i=0;i<lo_array1.length;i++){
		lo_array2 = lo_array1[i].split(",");
		
		if(i<lo_array1.length-1){
			lo_result += lo_array2 + division;
		}else{
			lo_result += lo_array2;
		}
	}
	
	return lo_result;
}
</script>
<table border="2">
	<thead>
		<tr>
			<th colspan="2">MagicE2E Result</th>
		</tr>
	</thead>
	<tbody>
			<td>sbCert</td>
			<td><%=sbCert.toString() %></td>
		</tr>
	</tbody>
</table>
<div id="middle">
	<h2>MagicLine Digital Signature</h2>
	<div id="workArea"><!-- DIV START  -->
	<table class="styledLeft">
		<tr>
			<td style="border: 0;"><nobr> Certificate Digital Signature Samples </nobr></td>
		</tr>
		<tr>
			<td style="border: 0;">&nbsp;</td>
		</tr>
		<tr>
			<td>
			<table style="border: 0;" >
				<tbody>
					<tr style="border: 0;">
						<td style="border: 0; ">
							<nobr>description :</nobr>
						</td>
						<td style="border: 0;">
							웹 구간 전달 메시지 전체에 대해 클라이언트에서 전자 서명을 실행합니다.<br>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<form id='reqForm' name='reqForm' method='post' >
	<table style="width: 100%; height:100%"  class="styledLeft">
		<thead>
			<tr>
				<th colspan="2">Client Digital Signature Information</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="formRow">
					<table class="normal" cellspacing="0" style="text-align: left;">
						<tr>
							<td>Name<font class="required">*</font></td>
							<td>
								<input type="text" name="name" id="name" value="홍길동"/>
							</td>
						</tr>
						<tr>
							<td>Address<font class="required">*</font></td>
							<td>
								<input type="text" name="address"  id="address" value="Seoul Korea 111-11 %11"/>
							</td>
						</tr>
						<tr>
							<td>Tel<font class="required">*</font></td>
							<td>
								<input type="text" name="tel"  id="tel" value="010-0000-1111"/>
							</td>
						</tr>
						<tr>
							<td>ETC<font class="required">*</font></td>
							<td>
								<input type="text" name="etc"  id="etc" value="MAN"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="buttonRow">
				<!-- 	Submit Type <select id="submitType">
									<option value="0" selected="selected">submit</option>
									<option value="1"> ajax</option>
								</select> -->
					<input name="Submit" type="button" class="button" value="전송" onclick="javascript:sendE2E();"/>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
	<form id='encFrm' name='encFrm' method='post'>
		<input type="hidden" id="encData" name="encData">
	</form>
	<p>&nbsp;</p>
	<table class="styledLeft" id="sgTable">
		<thead>
			<tr>
				<th colspan="3" >Server Send Signed Data.</th>
			</tr>
		</thead>
		<tbody>
			<tr bgcolor="white">
				<td width="10px" style="text-align: center;">1</td>
				<td>
					<nobr> e2e Text </nobr>
				</td>
				<td>
					<textarea id="eText" name="eText" rows="10" cols="66"></textarea> &nbsp; 구간암호화 데이터
				</td>
			</tr>
		</tbody>
	</table> 
	<p>&nbsp;</p>
	<table style="width: 100%" class="styledLeft">
		<thead>
			<tr>
				<th >Program Guide (Examples)</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="formRow">
				<ul type="disc">
				<li>
					<p>2.MagicLine 전자서명 기동 및 Encrypt 함수 정의.</p>
					<pre class="programlisting">
	&lt;script type="text/javascript" src="js/jquery-1.10.2.js"&gt;&lt;/script&gt;
	&lt;script type="text/javascript" src="js/jquery-ui.min.js"&gt;&lt;/script&gt;
	<!-- &lt;script type="text/javascript" src="js/json2.js"&gt;&lt;/script&gt; -->
	&lt;script type="text/javascript" src="js/ML_Config.js"&gt;&lt;/script&gt;
	&lt;script language='javascript'&gt;
	<span class="emphasis"><em>	// signedData callback </em></span>
		function mlCallBack(code, message){
		if(code==0){
			var data = encodeURIComponent(message);
			document.reqForm.sign.value = data;
			document.reqForm.submit();
		}else{
			alert("결과값 수신에 실패하였습니다.");
			return;
		}
	}
		
	&lt;/script&gt;
					</pre>
					<p>&nbsp;</p>
				</li>
				<li>
					<p>3.MagicLine Digital Signature Form 작성. </p>
					<pre class="programlisting">
					<span class="emphasis"><em>&lt;!-- Digital Signature Form --&gt; </em></span>
	&lt;form action="..(Response Url)" method="post" name="reqForm"&gt;
	&lt;input type="hidden" id="challenge" name="challenge" value="&lt;%=challenge%&gt;"/&gt;&lt;input type="hidden" id='sign' name='sign'&gt; <span class="emphasis"><em>&lt;!-- 생성한 challenge 값을 담는다 --&gt; </em></span>
	&lt;textarea name="signData" rows="10" cols="66" id="signData"&gt;&lt;/textarea&lt;&gt; <span class="emphasis"><em>&lt;!-- 전자서명을 실행할 원문 데이터를 입력한다. --&gt; </em></span>
	&lt;input type="button" value="전자서명" onclick="javascript:ML_CreateCipherMessage('makeSignData',this.form,'form', mlCallBack);" /&gt; <span class="emphasis"><em>&lt;!-- 전자서명 실행 및 데이터 전송 --&gt; </em></span>
	&lt;/form&gt;
					</pre>
					<p>&nbsp;</p>
				</li>
				</ul>
				</td>
			</tr>
		</tbody>
	</table>
	</div>
</div><!-- DIV END  -->
<!-- <script type="text/javascript">
	setCookie('current-breadcrumb', 'magicline_v40_menu');
	document.onload=setBreadcrumDiv();
	function setBreadcrumDiv () {
		var breadcrumbDiv = document.getElementById('breadcrumb-div');
		breadcrumbDiv.innerHTML = '<table cellspacing="0"><tr><td class="breadcrumb-link"><a href="index.jsp">Home</a></td><td class="breadcrumb-link">&nbsp;>&nbsp;MagicLine4</td><td class="breadcrumb-link">&nbsp;>&nbsp;Digital Signature</td>';
	}
</script> -->
<jsp:include page="include/footer.jsp"></jsp:include>