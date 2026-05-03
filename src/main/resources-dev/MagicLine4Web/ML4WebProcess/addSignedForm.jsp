<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<jsp:include page="include/header.jsp"></jsp:include>
<jsp:include page="include/menu.jsp"></jsp:include>
<script type="text/javascript">

//18.07.10
//1. 서명 원문 추가해서 signedFormR 에 서명 원문 데이터 파라미터 추가 
//2. 서명 원문 내용 출력 추가

// TODO : 반영할땐 result 출력 없이 해야됨.
var isResultShown = false;

function doSignData(){
	
	var signData = $("#signData").val();
	
	if(signData.length < 1){
		alert('폼 데이터를 입력하세요.');
		$("#signData").focus();
		return;
	}

	document.reqForm.signOrigin.value = document.reqForm.signData.value;
	magicline.uiapi.MakeAddSignData( document.reqForm, null, mlCallBack );
}

// 서명 결과 데이터를 화면에 출력해주고 전송 버튼을 노출시켜주는 함수
function signResultDrawler(message){
	
	var htmlStream = "";
	
	if(typeof(message) === "undefiend"){
		alert('No message for signature. \nPlease make sure sign data');
		return;
	}
	
	for(var key in message){
		/*
		if(typeof message === "object"){
			
			var inMessage = JSON.stringify(message);
			
			for(var inKey in inMessage){
				htmlStream += '<tr>';
				htmlStream += '<td></td>';
				htmlStream += '	<td>' + inKey + '</td>';
				htmlStream += '	<td>' + inMessage[inKey] + '</td>';
				htmlStream += '</tr>';
					
			}
			
		}else{
		}
		
		*/
		
		if(typeof message[key] === "object"){
			htmlStream += '<tr>';			
			htmlStream += '	<td colspan="2"><b>' + key + '</b></td>';
			htmlStream += '</tr>';
			for(var inKey in message[key]){			
				htmlStream += "<tr>";
				htmlStream += '	<td>&nbsp;&nbsp;&nbsp;&nbsp;' + inKey + '</td>';	
				htmlStream += '	<td>' + message[key][inKey] + '</td>';
				htmlStream += "</tr>";
				
			}
		}else{
			htmlStream += '<tr>';			
			htmlStream += '	<td><b>' + key + '</b></td>';
			htmlStream += '	<td>' + message[key] + '</td>';
			htmlStream += '</tr>';
		}
			
				
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
		document.reqForm.sign.value = encodeURIComponent( message.encMsg );
		//document.reqForm.submit();
		//결과값출력
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
			<td>&nbsp;&nbsp;전자서명 메세지에 추가 서명을한다.</td>
		</tr>
	</table>
	
	<p>&nbsp;</p>
	<form id='reqForm' name='reqForm' method='post' action="./signedFormR.jsp">
	<!-- 결과 수신 메시지  -->
	<input type="hidden" id="signOrigin" name="signOrigin" /> <!-- 180701 서명 원문 폼 추가 -->
	<input type="hidden" id='sign' name='sign'/>
	<input type="hidden" id='csCheckType' name='csCheckType' value="1"/>
	<input type="hidden" id="vidType" name="vidType" value="client"/>
	<input type="hidden" id="idn" name="idn" value="idn"/>
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
							<td>전자서명 데이터 입력<font class="required">*</font></td>
							<td>
								<textarea id="signData" name="signData" rows="3" cols="60">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</textarea>
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
<jsp:include page="include/footer.jsp"></jsp:include>