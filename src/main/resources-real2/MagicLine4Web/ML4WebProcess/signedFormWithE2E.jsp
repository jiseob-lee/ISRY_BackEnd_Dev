<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.dreamsecurity.magice2e.MagicE2E" %>
<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="No-Cache">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>Welcome to MagicLine4Web Page</title>
<link href="./css/global.css" rel="stylesheet" type="text/css" media="all" />
<!-- <link rel="icon" href="./images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="./images/favicon.ico"	type="image/x-icon" /> -->
<!-- 외부 JS -->
<script type="text/javascript" src="./js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./js/jquery.blockUI.js"></script>
<!-- <script type="text/javascript" src="./js/json2.js"></script> -->
<!-- ML4WEB JS -->
<script type="text/javascript" src="./js/ML_Config.js"></script>
<script src="../ML4Web/js/crypto/magicjs_1.2.1.0.min.js"></script>
<script src="../ML4Web/js/magic_e2e.js"></script>
<script src='./js/magicE2E.js'></script>
</head>
<body>
<jsp:include page="./include/menu.jsp"></jsp:include>

<script type="text/javascript">

<%
	String sessionString = "";
	int result = 0;
	StringBuffer sbCert = new StringBuffer();
	
	// 세션에 값이 있는지 확인
	MagicE2E temp = ( MagicE2E ) session.getAttribute("Magie2e");
	try{
		if( temp == null){
			MagicE2E ml = new MagicE2E(sbCert);
			sessionString = sbCert.toString();
			session.setAttribute("Magie2e", ml);
		}else{
			result = temp.open( sbCert );
			System.out.println("temp.getServerCert : " + result);
			System.out.println("temp.getServerCert : " + sbCert);
			sessionString = sbCert.toString();
		}
	}catch(Exception e){
		
	}
%>
//객체생성
var ml = new MagicE2E();
//초기화
ml.Init(<%=sessionString%>);

//18.07.10
//1. 서명 원문 추가해서 signedFormR 에 서명 원문 데이터 파라미터 추가 
//2. 서명 원문 내용 출력 추가

var isResultShown = false;

function doSignData(){
	
	var signData = $("#signData").val();
	if(signData.length < 1){
		alert('폼 데이터를 입력하세요.');
		$("#signData").focus();
		return;
	}
	document.reqForm.signOrigin.value = document.reqForm.signData.value;
	magicline.uiapi.MakeSignData($("#signData").val(), null, mlCallBack);
}

// 서명 결과 데이터를 화면에 출력해주고 전송 버튼을 노출시켜주는 함수
function signResultDrawler(message){
	
	var htmlStream = "";
	
	if(typeof(message) === "undefiend"){
		alert('No message for signature. \nPlease make sure sign data');
		return;
	}
	
	for(var key in message){
		htmlStream += '<tr>';
		htmlStream += '	<td>' + key + '</td>';
		htmlStream += '	<td>' + message[key] + '</td>';
		htmlStream += '</tr>';
	}
	
	htmlStream += '<tr>';
	htmlStream += '	<td colspan="2" class="buttonRow" align="center">';
	htmlStream += '		<input type="button" class="button" value="전송" name="Submit" onClick="this.form.submit();">';
	htmlStream += ' </td>';
	htmlStream += '</tr>';
	
	$("#signatureResultArea").html(htmlStream);
	
	if(!isResultShown){			
		isResultShown=true;			
		$("#signatureResultDiv").css("display", "");
	}
}

function mlCallBack(code, message){
	
	if(code==0){
		//message
		//alert(message.selectStorage);
		//alert(message.encMsg);
		
		document.reqForm.sign.value = ml.Encrypt( encodeURIComponent( message.encMsg ) );
		signResultDrawler(message);
		
	}else{
		alert("결과값 수신에 실패하였습니다.");
		return;
	}
}


</script>
<div id="middle">
	<h2>MagicLine Digital Signature</h2>
	<div id="workArea"><!-- DIV START  -->
	
	<table style="width: 100%; height:100%"  class="styledLeft">
		<thead>
		<tr>		
			<th colspan="2">Description</th>		
		</tr>
		</thead>
		<tr>
			<td>&nbsp;&nbsp;웹 구간 전달 메시지 전체에 대해 클라이언트에서 전자서명을 실행합니다.</td>
		</tr>
	</table>
	
	<p>&nbsp;</p>
	<form id='reqForm' name='reqForm' method='post' action="./signedFormRWithE2E.jsp">
	<!-- 결과 수신 메시지  -->
	<input type="hidden" id="signOrigin" name="signOrigin" /> <!-- 180701 서명 원문 폼 추가 -->
	<input type="hidden" id='sign' name='sign'/>
	<input type="hidden" id='csCheckType' name='csCheckType' value="1"/>
	
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
					<input id="aaa" type="button" class="button" value="전자서명" onclick="doSignData();">
				</td>
			</tr>
		</tbody>
	</table>
	<br>
	<!-- 전자서명 데이터 출력 영역 -->
	<div id="signatureResultDiv" style="display:none;" >
	<table style="width: 100%; height:100%"  class="styledLeft" >
		<thead>
			<tr>
				<th colspan="2">Signature Data Information</th>
			</tr>
		</thead>
		<tbody id="signatureResultArea">
		</tbody>
	
	</table>
		
	</div>
	</form>
	<p>&nbsp;</p>
	
	</div>
</div><!-- DIV END  -->
<div id="selectCertContainer1" style="width:100%;margin-top:0; display:none;"></div>
<div id="startCs" style="width:100%;margin-top:0; display:none;"></div>
<jsp:include page="./include/footer.jsp"></jsp:include>