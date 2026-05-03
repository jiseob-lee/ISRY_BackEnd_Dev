<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<jsp:include page="include/header.jsp"></jsp:include>
<jsp:include page="include/menu.jsp"></jsp:include>
<script type="text/javascript">

//18.07.10
//1. 서명 원문 추가해서 signedFormR 에 서명 원문 데이터 파라미터 추가 
//2. 서명 원문 내용 출력 추가

// TODO : 반영할땐 result 출력 없이 해야됨.
var isResultShown = false;

// 간편로그인
function openEZLogin(){
	MagicLineJSAPI.makeSimpleLogin("", function(callback) {
		if (callback == true) {
			document.EZreqForm.code.value = encodeURIComponent(callback);
			document.EZreqForm.submit();
		} else {
			/*
			    에러처리 추가
			*/
		}
	});
}

// 간편인증
function openEZAuth(){
	MagicLineJSAPI.makeSimpleAuth("", function(callback) {
		if (callback == true) {
			document.EZreqForm.code.value = encodeURIComponent(callback);
			document.EZreqForm.submit();
		} else {
			/*
			    에러처리 추가
			*/
		}
	});
}

// 간편전자서명
function openEZSign(){
	
	var signData = $("#signData").val();
	
	if(signData.length < 1){
		alert('폼 데이터를 입력하세요.');
		$("#signData").focus();
		return;
	}
	
	//document.reqForm.signOrigin.value = document.reqForm.signData.value;
	MagicLineJSAPI.makeSimpleSign("", function(callback) {
		if (callback == true) {
			document.EZreqForm.code.value = encodeURIComponent(callback);
			document.EZreqForm.submit();
		} else {
			/*
			    에러처리 추가
			*/
		}
	});
	
}

</script>
<div id="middle">
	<h2>MagicLineHub Login, Auth, Sign</h2>
	<div id="workArea"><!-- DIV START  -->
	
	<table style="width: 100%; height:100%"  class="styledLeft">
		<thead>
		<tr>		
			<th colspan="2">Description</th>		
		</tr>
		</thead>
		<tr>
			<td>&nbsp;&nbsp;간편인증 사업자 서비스를 통해 간편로그인, 간편인증, 간편전자서명을 실행합니다.</td>
		</tr>
	</table>
	
	<p>&nbsp;</p>
	<!-- 간편인증사업자 서비스 form -->
	<form id='EZreqForm' name='EZreqForm' method='post' action="./hubFormR.jsp">
		<!-- 결과 수신 메시지  -->
		<input type="hidden" id='code' name='code'/>
	
	<!-- 전자서명 데이터 입력 영역 -->
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
							<td>전자서명 원문 데이터 입력<font class="required">*</font></td>
							<td>
								<textarea id="signData" name="signData" rows="3" cols="60"></textarea>
								<!-- <input class="text-box-big" id="signData" name="signData" type="text" value="">-->
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="buttonRow" align="center">
					<input id="open_EZLogin" type="button" class="button" value="간편로그인" onclick="openEZLogin();">
					<input id="open_EZAuth" type="button" class="button" value="간편인증" onclick="openEZAuth();">
					<input id="open_EZSign" type="button" class="button" value="간편전자서명" onclick="openEZSign();">
			</tr>
		</tbody>
	</table>
	</form>
	</div>
</div><!-- DIV END  -->
<div id="selectCertContainer1" style="width:100%;margin-top:0; display:none;"></div>
<div id="startCs" style="width:100%;margin-top:0; display:none;"></div>


<jsp:include page="include/footer.jsp"></jsp:include>