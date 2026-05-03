<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<jsp:include page="include/header.jsp"></jsp:include>
<jsp:include page="include/menu.jsp"></jsp:include>
<script type="text/javascript">
function mlCallBack(code, message){
	if(code==0){
		// 정상메시지
		var data = encodeURIComponent(message.encMsg);

		document.reqForm.signedData.value = data;
		
		if(message.vidRandomHash != null){
			document.reqForm.vidRandomHash.value = encodeURIComponent(message.vidRandomHash);
		}
		document.reqForm.submit();
	}else{
		alert("결과값 수신에 실패하였습니다.");
		return;
	}
}
</script>
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
	<form id='reqForm' name='reqForm' method='post' action="./vidServerIDNR.jsp">
	<!-- 결과 수신 메시지  -->
	<!-- <input type="hidden" name="sIDN"  id="sIDN" value="ML4Web"/> -->
	<input type="hidden" id="signedData" name="signedData"/>
	<input type="hidden" id="vidRandomHash" name="vidRandomHash"/>
	<input type="hidden" id="vidType" name="vidType" value="server"/>
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
							<td>plain text<font class="required">*</font></td>
							<td>
								<textarea name="signData" rows="10" cols="66" id="signData"></textarea>
								<!--input class="text-box-big" id="plaintext" name="signData" type="text" value=""--> &nbsp; 전자서명 원문 데이터를 입력한다.
							</td>
						</tr>
						<tr>
							<td>주민번호<font class="required">*</font></td>
							<td>
								<input type="text" name="idn"  id="idn" value=""/>
								<!--input class="text-box-big" id="plaintext" name="signData" type="text" value=""--> &nbsp; 주민등록번호를 입력한다.
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
					<input name="Submit" type="button" class="button" value="전자서명" onclick="javascript:magicline.uiapi.MakeSignData(this.form, null, mlCallBack);"/>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
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
	&lt;input type="button" value="전자서명" onclick="javascript:magicline.uiapi.MakeSignData(this.form, null, mlCallBack);" /&gt; <span class="emphasis"><em>&lt;!-- 전자서명 실행 및 데이터 전송 --&gt; </em></span>
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