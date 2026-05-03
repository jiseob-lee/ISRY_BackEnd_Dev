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
</style>

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
    magicline.uiapi.MakeSignData( signData, null, mlCallBack);
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
        document.reqForm.submit();
        //결과값출력
        //signResultDrawler(message);
        
    }else{
        alert("결과값 수신에 실패하였습니다.");
        return;
    }
}
</script>

    <form id='reqForm' name='reqForm' method='post' action="./signedFormR.jsp">
    <!-- 결과 수신 메시지  -->
    <input type="hidden" id="signOrigin" name="signOrigin" /> <!-- 180701 서명 원문 폼 추가 -->
    <input type="hidden" id='sign' name='sign' />
    <input type="hidden" id='csCheckType' name='csCheckType' value="1" />
    
    <input type="hidden" id='signData' name='signData' value="youthsafety" />
    
<div style="text-align: center; padding-top: 110px;">

<h1 style="border-bottom: 0;">인증서 로그인</h1>

<br/><br/>
<br/><br/>
<br/><br/>
<br/><br/>

<input type="button" id="btn1" onclick="doSignData();" tabindex="1" class="loginB" style="width: 120px; height: 30px;" value="인증서 (재)등록" />
<input type="button" id="btn2" onclick="doSignData();" tabindex="2" class="loginB" style="width: 120px; height: 30px;" value="인증서 로그인" />

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
    

<div id="selectCertContainer1" style="width:100%;margin-top:0; display:none;"></div>
<div id="startCs" style="width:100%;margin-top:0; display:none;"></div>

<div id="dscertContainer">
    <iframe id="dscert" name="dscert" src="" scrolling="no" width="100%" height="100%" frameborder="0" allowTransparency="true" style="position:fixed;z-index:100010;top:0px;left:0px;width:100%;height:100%;"></iframe>
</div>

</body>
</html>
