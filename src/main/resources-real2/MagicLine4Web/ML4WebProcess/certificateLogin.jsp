<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>

<jsp:include page="include/header.jsp"></jsp:include>

<style type="text/css">
.outer {
    width: 100%;
    height: 100%;
    background: white;
}
.inner {
    width: 650px;
    height: 400px;
    background: white;
    color: black;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
}
.loginC {
    display: block;
    float: left;
    box-shadow: 0 0 10px 0px rgba(0,0,0,0.3);
    width: 350px;
    height: 400px;
}
.loginD {
    display: block;
    float: left;
    box-shadow: 0 0 10px 0px rgba(0,0,0,0.3);
    width: 300px;
    height: 400px;
}
.loginT td {
    text-align: center;
    padding: 6px;
}
.loginB {
    background: #002b51;
    color: white;
    border: 0;
}
.userInput {
    width: 250px; 
    height: 30px; 
    padding: 5px; 
    font-size: 15px; 
    border: 1px solid #DDDDDD;
}
</style>

<script type="text/javascript">

//18.07.10
//1. 서명 원문 추가해서 signedFormR 에 서명 원문 데이터 파라미터 추가 
//2. 서명 원문 내용 출력 추가

// TODO : 반영할땐 result 출력 없이 해야됨.
var isResultShown = false;

function doSignData(num) {
    
    var signData = $("#signData").val();
    
    if (signData.length < 1) {
        alert('폼 데이터를 입력하세요.');
        $("#signData").focus();
        return;
    }
    
    
    if (num == 1) {  // 인증서 (재)등록
    	
    	document.reqForm.signType.value = "1";
    	document.reqForm.loginId.value = $("#userId").val();
    	
    	if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
    		alert("인증서 (재)등록을 위하여 아이디를 입력해주시기 바랍니다.");
    		return;
    	}
    	
    } else if (num == 2) {  // 인증서 인증(로그인)
    	
        document.reqForm.signType.value = "2";
    }

    //console.log("num", num);
    
    document.reqForm.signOrigin.value = document.reqForm.signData.value;
    magicline.uiapi.MakeSignData( signData, null, mlCallBack);
}

// 서명 결과 데이터를 화면에 출력해주고 전송 버튼을 노출시켜주는 함수
function signResultDrawler(message) {
    
    var htmlStream = "";
    
    if (typeof(message) === "undefiend") {
        alert('No message for signature. \nPlease make sure sign data');
        return;
    }
    
    for (var key in message) {
    	//console.log(key);
        /*
        if(typeof message === "object"){
            
            var inMessage = JSON.stringify(message);
            
            for(var inKey in inMessage){
                htmlStream += '<tr>';
                htmlStream += '<td></td>';
                htmlStream += ' <td>' + inKey + '</td>';
                htmlStream += ' <td>' + inMessage[inKey] + '</td>';
                htmlStream += '</tr>';
                    
            }
            
        }else{
        }
        
        */
        
        if(typeof message[key] === "object"){
            htmlStream += '<tr>';           
            htmlStream += ' <td colspan="2"><b>' + key + '</b></td>';
            htmlStream += '</tr>';
            for(var inKey in message[key]){         
                htmlStream += "<tr>";
                htmlStream += ' <td>&nbsp;&nbsp;&nbsp;&nbsp;' + inKey + '</td>';    
                htmlStream += ' <td>' + message[key][inKey] + '</td>';
                htmlStream += "</tr>";
                
            }
        }else{
            htmlStream += '<tr>';           
            htmlStream += ' <td><b>' + key + '</b></td>';
            htmlStream += ' <td>' + message[key] + '</td>';
            htmlStream += '</tr>';
        }
            
                
    }
    
    htmlStream += '<tr>';
    htmlStream += ' <td colspan="2" class="buttonRow" align="center">';
    htmlStream += '     <input type="button" class="button" value="전송" name="Submit" onClick="this.form.submit();">';
    htmlStream += ' </td>';
    htmlStream += '</tr>';
    
    $("#signatureResultArea").html(htmlStream);
    
    if (!isResultShown) {         
        isResultShown=true;         
        $("#signatureResultDiv").css("display", "");
    }
}

function mlCallBack(code, message) {
    
	//console.log("mlCallBack, code : " + code);
    
    if (code == 0) {
        
        //message
        //alert(message.selectStorage);
        //alert(message.encMsg);
        document.reqForm.sign.value = encodeURIComponent( message.encMsg );
        document.reqForm.submit();
        //결과값출력
        //signResultDrawler(message);
        
    } else {
        alert("결과값 수신에 실패하였습니다.");
        return;
    }
}
</script>

<script type="text/javascript">
$(window).resize(function() {
	
	//if (document.body.clientWidth % 10 == 0) {
		//console.log(document.body.clientWidth);
	//}
	
	if (document.body.clientWidth < 1000) {
		document.getElementById("loginD").style.display = "none";
		document.getElementById("workArea").style.width = "350px";
	} else {
		document.getElementById("loginD").style.display = "block";
		document.getElementById("workArea").style.width = "650px";
	}
});

$(document).ready(function() {

    if (document.body.clientWidth < 1000) {
        document.getElementById("loginD").style.display = "none";
        document.getElementById("workArea").style.width = "350px";
    } else {
        document.getElementById("loginD").style.display = "block";
        document.getElementById("workArea").style.width = "650px";
    }
    
    if (getCookie( "userId" )) {
    	$("#userId").val(getCookie( "userId" ));
    	$("#saveId").prop('checked', true);
    }
    
    $("#userId").val("jslee");
    $("#userPw").val("12345");
});

function actLogin() {
	var userId = $("#userId").val();
	var userPw = $("#userPw").val();
	var saveId = $("#saveId").is(":checked");

    if (userId == null || userId == "") {
        alert("아이디를 입력해주시기 바랍니다.");
        return;
    }
    if (userPw == null || userPw == "") {
        alert("패스워드를 입력해주시기 바랍니다.");
        return;
    }
    
    $.ajax({
    	url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/userLogin2.do",
    	data : {userId : userId, userPw : userPw},
    	method : "POST",
    	dataType : "json"
    })
    .done(function(json) {
    	//console.log(json);
    	if (json["msg"] != null && json["msg"] != "") {
    		alert(json["msg"]);
    	} else {
    		if (saveId) {
    			setCookie("userId", userId, 30);
    		} else {
    			delCookie("userId", "/");
    		}
    		top.location.reload();
    	}
    })
    .fail(function(xhr, status, errorThrown) {
    	alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}

function delCookie( name, path, domain ) {
    if ( getCookie( name ) ) {
        document.cookie = name + "=" +
        ((path) ? ";path="+path:"")+
        ((domain)?";domain="+domain:"") +
        ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
    }
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i=0; i < ca.length; i++) {
    	var c = ca[i];
    	while (c.charAt(0) == ' ') {
    		c = c.substring(1, c.length);
    	}
    	if (c.indexOf(nameEQ) == 0) {
    		return c.substring(nameEQ.length, c.length);
    	}
    }
    return null;
}

function setCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

var findId;
function openFindId() {
	var windowFeatures = "left=100, top=100, width=620, height=450";
	findId = window.open("${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/findId.do", "findId", windowFeatures);
}

var findPw;
function openFindPw() {
	var windowFeatures = "left=100, top=100, width=620, height=450";
    findPw = window.open("${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/findPw.do", "findPw", windowFeatures);
}

var joining;
function openJoining() {
	var windowFeatures = "left=100, top=100, width=1020, height=760";
    joining = window.open("${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/joining.do", "joining", windowFeatures);
}

</script>

<div id="middle" class="outer">
    <div id="workArea" class="inner"><!-- DIV START  -->
    
    <div id="loginC" class="loginC"> <!-- 아이디 패스워드 로그인 -->
        <table class="loginT" style="margin-top: 25px;">
            <tr><td style="padding-bottom: 20px;">
                <img src="${pageContext.request.contextPath}/ui/theme/dark/images/com/main/logo_login.png" width="75%" height="auto" />
            </td></tr>
            <tr><td>
                <input type="text" name="userId" id="userId" class="userInput" placeholder="아이디" />
            </td></tr>
            <tr><td>
                <input type="password" name="userPw" id="userPw" class="userInput" placeholder="패스워드" />
            </td></tr>
            <tr><td style="text-align: left; padding-left: 37px;">
                <input type="checkbox" name="saveId" id="saveId" value="Y" /> <label for="saveId">아이디 저장</label>
            </td></tr>
            <tr><td>
                <input type="button" name="actLogin" id="actLogin" value="로그인" onclick="actLogin();" class="loginB" style="width: 250px; height: 30px;" />
            </td></tr>
            <tr><td>
                <a href="javascript:openFindId();">아이디 찾기</a> &nbsp; | &nbsp;
                <a href="javascript:openFindPw();">비밀번호 찾기</a> &nbsp; | &nbsp;
                <a href="javascript:openJoining();">회원가입</a>
            </td></tr>
            <tr><td>
                <span>회원가입 후 관리자의 승인이 필요합니다.</span>
            </td></tr>
        </table>
    </div>
    
    
    <div id="loginD" class="loginD"> <!-- 인증서 로그인 -->
    
    <form id='reqForm' name='reqForm' method='post' action="./signedFormR2.jsp" target="signedFormR">
    <!-- 결과 수신 메시지  -->
    <input type="hidden" id="signOrigin" name="signOrigin" /> <!-- 180701 서명 원문 폼 추가 -->
    <input type="hidden" id='sign' name='sign' />
    <input type="hidden" id='csCheckType' name='csCheckType' value="1" />
    <input type="hidden" id='signType' name='signType' />
    <input type="hidden" id='loginId' name='loginId' value="" />
    
    <input type="hidden" id='signData' name='signData' value="youthsafety" />
    
<div style="text-align: center; padding-top: 110px;">

<h1 style="border-bottom: 0;">인증서 로그인</h1>

<br/><br/>
<br/><br/>
<br/><br/>
<br/><br/>

<input type="button" id="btn1" onclick="doSignData('1');" tabindex="1" class="loginB" style="width: 120px; height: 30px;" value="인증서 (재)등록" />
<input type="button" id="btn2" onclick="doSignData('2');" tabindex="2" class="loginB" style="width: 120px; height: 30px;" value="인증서 로그인" />

<br/><br/>
<span style="font-size: 10pt;">인증서 (재)등록시 왼쪽에 아이디를<br/>입력해주시기 바랍니다.</span>
<!-- <input value="마지막 포커스 받을 요소" id="ipb2" tabindex="3" /> -->

</div>
    
                    <!-- <input id="aaa" type="button" class="button" value="전자서명" onclick="doSignData();"> -->
           
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
    
    </div>
    
    </div>
</div><!-- DIV END  -->

<div id="selectCertContainer1" style="width:100%;margin-top:0; display:none;"></div>
<div id="startCs" style="width:100%;margin-top:0; display:none;"></div>

<div id="dscertContainer">
    <iframe id="dscert" name="dscert" src="" scrolling="no" width="100%" height="100%" frameborder="0" allowTransparency="true" style="position:fixed;z-index:100010;top:0px;left:0px;width:100%;height:100%;"></iframe>
</div>

<iframe name="signedFormR" id="signedFormR" style="height:0; width:0; border:0; border:none; visibility:hidden;"></iframe>

</body>
</html>
