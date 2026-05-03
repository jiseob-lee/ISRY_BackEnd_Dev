<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import = "java.util.*" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.*,java.text.SimpleDateFormat" %>
<%@ page import = "java.security.SecureRandom"%>
<%@ page import = "java.net.URLEncoder"%>
<%@ page import = "com.dreamsecurity.crypt.*"%>

<%!
public String getBaseUrl(HttpServletRequest request) {
	String scheme = request.getScheme() + "://";
	String serverName = request.getServerName();
	String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
	String contextPath = request.getContextPath();
	return scheme + serverName + serverPort + contextPath;
}
%>

<%
    // 1. 거래요청번호  생성 
    //   - 최대 40byte 이내 사용 가능, 중복되지 않은 유일한값으로 설정
    //   - 예시 : 회원사 PREFIX + 날짜 + 랜덤6자리
    // 1.1 날짜 생성
    Calendar today = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    String reqTime = sdf.format(today.getTime());
    // 1.2 거래요청ID 유일한값 생성을 위한 랜덤 생성
    SecureRandom rand = null;
    String randomStr = "";
    try {
        rand = SecureRandom.getInstance("SHA1PRNG");
        for(int i=0; i < 6; i++)  {
            randomStr += rand.nextInt(10);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    // 1.3 거래요청번호  생성
    String reqNum  = "MOK" + reqTime + randomStr;
    
    // 1.4 거래요청번호  세션 저장
    //     - 응답결과에서 세션정보 확인 권고
    session.setAttribute("sessionReqNum ", reqNum );
    
    // 2. 회원사 등록 정보 설정
    String urlCode  = "01005";         // 회원사 등록 코드
    String cpId     = "youthsafe";     // 회원사ID
    String reqdate  = reqTime;         // 요청일시
    
    /*
    # url code(서비스 코드)
    - 01001 회원가입
    - 01002 정보변경
    - 01003 아이디 분실시 조회페이지
    - 01004 패스워드 분실시 조회페이지
    - 01005 본인확인용
    - 01999 기타
    */
    
    // 3. 거래요청정보 암호화
    MsgCrypto mscr = new MsgCrypto();
    // 3.1 거래요청정보 : 서비스코드/거래요청번호/요청일시
    //     -  /'로 구분
    String reqInfo = urlCode + "/" + reqNum + "/" + reqdate;
    // 3.2 거래요청정보 암호화
    
    //     - 암호문자열 = msgEncrypt(암호화 시킬 값, 인증서 경로(개발  서버 경로));
    //String encReqInfo = mscr.msgEncrypt(reqInfo,"/app/ISRY_BackEnd/MOK/cert/youthsafeCert.der");
    
    //     - 암호문자열 = msgEncrypt(암호화 시킬 값, 인증서 경로(운영 서버 경로));
    String encReqInfo = mscr.msgEncrypt(reqInfo, "/app/ISRY_BackEnd.war/WEB-INF/mok/cert/youthsafeCert.der");
	//String encReqInfo = mscr.msgEncrypt(reqInfo, root + "WEB-INF/mok/cert/youthsafeCert.der");
    
    // 3.3 암호화된 거래요청정보 URL 인코딩
    encReqInfo = URLEncoder.encode(encReqInfo);
    
    // 4. 휴대폰본인확인 요청정보 
    // 4.1 본인인증 결과수신 받을 회원사 URL 설정
    String rtn_url = getBaseUrl(request) + "/MOK/mok_webauth_result2.jsp";      // 본인인증 결과수신 받을 URL
    
    System.out.println("#### rtn_url : " + rtn_url);
    
    // 4.2 휴대폰본인확인 요청 URL 생성
    //     - https://휴대폰본인확인 URL?cpid=<회원사ID>&rtn_url=<회원사결과수신URL>&req_info=<암호화된 거래요청정보1>
    //     - 운영 휴대폰본인확인 URL : https://www.mobile-ok.com/popup/common/hscert.jsp
    String request_url = "https://www.mobile-ok.com/popup/common/hscert.jsp?cpid=" + cpId +"&rtn_url=" + rtn_url +"&req_info=" + encReqInfo;      // 본인인증 요청 URL
    
    System.out.println("#### request_url : " + request_url);
    
    // 5. 휴대폰본인확인 요청
    //    - function openDRMOKWindow 함수 실행
	
%>

<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <title>여성가족부</title>
  <meta name="format-detection" content="telephone=no">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/isry/itgcms/login/style.css">

  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/KDepense7/js/kos-ng.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/KDepense7/js/kos-ng.config.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/jquery-3.5.1.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/common_ui.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/json2.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_exproto.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_install.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_daemon.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/MagicLine4Web/ML4Web/js/ext/jquery-ui.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/MagicLine4Web/ML4Web/js/ext/jquery.blockUI.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/nxWeb/js/nxweb_config.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/nxWeb/js/TouchEnNxWeb_Interface.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/nxWeb/js/TouchEnNxWeb.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/MagicLine4Web/ML4Web/js/ext/ML_Config.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_loader.js"></script>
  
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/notify.min.js"></script>

  <c:choose>
    
    <c:when test="${SERVER eq 'rybwas11' or SERVER eq 'rybwas21' }">
      
      <script type="text/javascript" src="https://cert.ez-iok.com/stdauth/ds_auth_ptb/asset/js/ptb_ezauth_proc.js"></script>
      
      <script type="text/javascript">
        var isInternet = false;
      </script>
    </c:when>
    
    <c:otherwise>
      <script type="text/javascript">
        var isInternet = true;
      </script>
    </c:otherwise>
    
  </c:choose>
  
  <script type="text/javascript">

  var mnKeySize = 128;
  var mnIterationCount = 10000;
  var msSalt = "0c68efe9e3c3a02b3f9f69a08987e4ab";
  var msIv = "18b8e16db963ae9bfe9fccbe37d452e0";
  var msPassPhrase = "exb6Frame";

  /**
   * 쿠키를 설정합니다.
   * 
   * @param {String} psName
   * @param {Strubg} psValue
   * @param {Number} pnExpireDate
   */
  function setCookie(psName, psValue, pnExpireDate) {
      var voToday = new Date();
      voToday.setDate(voToday.getDate() + parseInt(pnExpireDate));
      document.cookie = psName + "=" + escape(psValue) + ";path=/;expires=" + voToday.toGMTString() + ";";
  }

  function setCookieNotToday(psName, psValue) {
       
    var voToday = new Date();
       
    voToday.setDate(voToday.getDate());
    voToday.setHours(23, 59, 59);
       
    //console.log("voToday", voToday);
       
    document.cookie = psName + "=" + escape(psValue) + ";path=/;expires=" + voToday.toGMTString() + ";";
  }


  /**
   * 쿠키를 가져옵니다.
   * @param {String} psName
   */
  function getCookie(psName) {
      var vsCookie = document.cookie + ";";

      var vaItems = vsCookie.split(";");
      var vnItemLen = vaItems.length;
      var item = null;
      var voItemInfo = null;
      for (var i = 0; i < vnItemLen; i++) {
          item = vaItems[i];
          voItemInfo = item.split("=");
          if (psName == voItemInfo[0].trim()) {
              return unescape(voItemInfo[1]);
          }
      }
  }

  function deleteCookie(name) {
      var expireDate = new Date();
      expireDate.setDate(expireDate.getDate() - 1);
      document.cookie = name + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
  }

  function doSignData(num) {
      
      //console.log("num", num);
      
      var signData = $("#signData").val();
      
      if (signData.length < 1) {
          alert('폼 데이터를 입력하세요.');
          $("#signData").focus();
          return;
      }

      var userId1 = $("#userId1").val();
      var userId2 = $("#userId2").val();
      
      var userBirthday1 = $("#userBirthday1").val();
      var userBirthday2 = $("#userBirthday2").val();

      if (num == 7) {  // 공동 인증서 본인인증
          
          document.reqForm.signType.value = "7";
          
      } else if (num == 1) {  // 인증서 인증(로그인)
          
          document.reqForm.signType.value = "2";
          
      } else if (num == 2) {  // 인증서 (재)등록
          
          document.reqForm.signType.value = "1";
          document.reqForm.loginId.value = userId2;
          document.reqForm.birthday.value = userBirthday2;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 (재)등록을 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          if (document.reqForm.birthday.value == null || document.reqForm.birthday.value == "") {
              alert("생년월일을 입력해주시기 바랍니다.");
              return;
          } else if (document.reqForm.birthday.value.length != 8) {
        	  alert("생년월일은 8자리 숫자로 입력해주시기 바랍니다.");
              return;
          }
          
      } else if (num == 3) {  // 인증서 삭제
          
          document.reqForm.signType.value = "3";
          document.reqForm.loginId.value = userId2;
          document.reqForm.birthday.value = userBirthday2;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 삭제를 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          if (document.reqForm.birthday.value == null || document.reqForm.birthday.value == "") {
              alert("생년월일을 입력해주시기 바랍니다.");
              return;
          } else if (document.reqForm.birthday.value.length != 8) {
              alert("생년월일은 8자리 숫자로 입력해주시기 바랍니다.");
              return;
          }
          
      } else if (num == 4) {  // 금융 인증서 인증(로그인)
          
          document.reqForm.signType.value = "5";
          
      } else if (num == 5) {  // 금융 인증서 (재)등록
          
          document.reqForm.signType.value = "4";
          document.reqForm.loginId.value = userId1;
          document.reqForm.birthday.value = userBirthday1;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 (재)등록을 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          if (document.reqForm.birthday.value == null || document.reqForm.birthday.value == "") {
              alert("생년월일을 입력해주시기 바랍니다.");
              return;
          } else if (document.reqForm.birthday.value.length != 8) {
              alert("생년월일은 8자리 숫자로 입력해주시기 바랍니다.");
              return;
          }
          
      } else if (num == 6) {  // 금융 인증서 삭제
          
          document.reqForm.signType.value = "6";
          document.reqForm.loginId.value = userId1;
          document.reqForm.birthday.value = userBirthday1;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 삭제를 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          if (document.reqForm.birthday.value == null || document.reqForm.birthday.value == "") {
              alert("생년월일을 입력해주시기 바랍니다.");
              return;
          } else if (document.reqForm.birthday.value.length != 8) {
              alert("생년월일은 8자리 숫자로 입력해주시기 바랍니다.");
              return;
          }
      }

      //alert("loginId : " + document.reqForm.loginId.value);
      //console.log("num", num);
      
      if (num == 1 || num == 2 || num == 3 || num == 7) {
          document.reqForm.signOrigin.value = document.reqForm.signData.value;
          magicline.uiapi.MakeSignData( signData, null, mlCallBack);
          
      } else if (num == 4 || num == 5 || num == 6) { // 금융 인증서 로그인
          document.reqForm.signOrigin.value = document.reqForm.signData.value;
          magicline.uiapi.MakeSignData( signData, "fincert", mlCallBack);
      }
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
          isResultShown = true;
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

//For todays date;
Date.prototype.today = function () { 
   return this.getFullYear() +""+(((this.getMonth()+1) < 10)?"0":"") + (this.getMonth()+1) +""+ ((this.getDate() < 10)?"0":"") + this.getDate();
}

//For the time now
Date.prototype.timeNow = function () {
    return ((this.getHours() < 10)?"0":"") + this.getHours() +""+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +""+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds();
}

function login(num) {

    if (num == 1 && ($("#userId").val() == null || $("#userId").val().trim() == "")) {
        alert("아이디를 입력해주시기 바랍니다.");
        return;
    }
    if (num == 1 && ($("#userPw").val() == null || $("#userPw").val().trim() == "")) {
        alert("비밀번호를 입력해주시기 바랍니다.");
        return;
    }

    if (($("#userId").val() == null || $("#userId").val().trim() == "")
            || ($("#userPw").val() == null || $("#userPw").val().trim() == "")) {
        return;
    }

    var vcCbxRmbr = document.getElementById("cbxRmbr");
    if (vcCbxRmbr.checked == true) {
        setCookie("expuid", $("#userId").val(), 30);
    } else {
        deleteCookie("expuid");
    }
    
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/userLogin2.do",
        //url : "${pageContext.request.contextPath}/loginProcess.do",
        data : {userId : $("#userId").val(), userPw : $("#userPw").val()},
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        console.log("json", json);
        
        var sessionCount = json["sessionCount"];
        var msg = json["msg"];
        //var msg = json["message"];
        
        if (sessionCount != null && sessionCount > 0) {
        	
        	// 기존에 같은 아이디로 로그인 된 세션이 있는 경우
        	
        	if (confirm("기존 접속을 끊고 로그인 하시겠습니까?")) {
        	    
        	    $.ajax({
        	        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/userLogin4.do",
        	        data : {userId : $("#userId").val(), userPw : $("#userPw").val()},
        	        method : "POST",
        	        dataType : "json"
        	    })
        	    .done(function(json1) {
        	    	
        	    	var msg1 = json1["msg"];
        	    	
        	    	if (msg1 != null && msg1 != "2") {
        	            alert(msg1);
        	            return;
        	        
        	        } else if (msg1 == "2") {  // 로컬 및 개발서버
        	            location.href = "${pageContext.request.contextPath}/";
        	        }
        	    })
        	    .fail(function(xhr, status, errorThrown) {
        	        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
        	    });

        	}
        	
        } else if (msg != null && msg != "2") {
            
            alert(msg);
            return;
        
        } else if (msg == "2") {  // 로컬 및 개발서버
            
            location.href = "${pageContext.request.contextPath}/";
        
        } else {
            //console.log("인증서 로그인을 진행해 주시기 바랍니다.");
            // 인증서 로그인을 진행해 주시기 바랍니다.
            //util.Msg.notify(app, "INF-M013");
            //util.Msg.notify(app, "INF-M005");
            //$.notify("인증서 로그인을 진행해 주시기 바랍니다.", "success");
            
            $.notify('인증서 로그인을 진행해 주시기 바랍니다.', {
                className: 'success',
                globalPosition: 'bottom right'
            });
            
        }
        
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });

}

function certLogin(num) {
    
    // 금융 인증서 로그인
    if (num == 1) {
        
        doSignData(4);
    
    // 구 공인 인증서 로그인
    } else if (num == 2) {

        var idPwLogin = getCookie("currentTime");
        
        //console.log("idPwLogin", idPwLogin);
        
        var currentdate = new Date(); 
        var datetime = currentdate.today() + currentdate.timeNow();
        
        //console.log("datetime", datetime);
        
        if (idPwLogin == null || idPwLogin == "" || idPwLogin < datetime) {
            //alert("먼저 아이디/패스워드 로그인을 실행해주시기 바랍니다.");
            //return;
        }
        
        doSignData(1);
    }
}

function findId() {
	
	var findIdName = $("#find-id-name").val();
	var findIdMethod = $("input[name='find-id-method']:checked").val();
	var findIdPhone = "";
	var findIdEmail = "";
	
	var findIdValue = $("#find-id-value").val();
	
	if (findIdMethod == "phone") {
		findIdPhone = findIdValue;
	} else if (findIdMethod == "email") {
		findIdEmail = findIdValue;
	}

    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/findId2.do",
        data : {name : findIdName, method : findIdMethod, phone : findIdPhone, email : findIdEmail},
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        var msg = json["msg"];
        alert(msg);
                
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });

}

function findPw() {
    
    var findPwId = $("#find-pw-id").val();
    var findPwName = $("#find-pw-name").val();
    var findPwMethod = $("input[name='find-pw-method']:checked").val();
    var findPwPhone = "";
    var findPwEmail = "";
    
    var findPwValue = $("#find-pw-value").val();
    
    if (findPwMethod == "phone") {
        findPwPhone = findPwValue;
    } else if (findPwMethod == "email") {
        findPwEmail = findPwValue;
    }

    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/findPw2.do",
        data : {userId : findPwId, name : findPwName, method : findPwMethod, phone : findPwPhone, email : findPwEmail},
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        var msg = json["msg"];
        alert(msg);
                
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}

function viewLoginNotice(bbscttEsntalNo) {
	var url = '${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/loginNoticeDetail.do?BBSCTT_ESNTAL_NO=' + bbscttEsntalNo;
	window.open(url, 'loginNoticeView', 'width=1200, height=650');
}

$(document).ready(function() {

    var vsCookieId = getCookie("expuid");
 
    if (vsCookieId != null && vsCookieId != "") {
        $("#cbxRmbr").prop("checked", true);
        $("#userId").val(vsCookieId);
        $("#userPw").focus();
    } else {
        $("#userId").focus();
    }

    // 알림 팝업 바깥쪽
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/syscmmn/noticepopup/selectNoticePopupListOuter.do",
        data : {},
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        if (json && json.length > 0) {

        	for (var i=0; i < json.length; i++) {

                var c = getCookie("noPopup_" + json[i]["POPUP_NO"]);
                if (c != null && c == "1") {
                    continue;
                }
                
        		var htmlDiv = document.createElement("div");
                
	            var htmlStr = "";
	            
	            if (json[i]["SRTNG_CRTR_VALUE"] == "CENTER") {
	            	htmlStr = "<div id='layerPopup" + json[i]["POPUP_NO"] + "' style='height:" + json[i]["POPUP_VRTICL_SIZEA_SZ"] + "px; left:0; right:0; margin-left:auto; margin-right:auto; top:0; bottom:0; margin-top:auto; margin-bottom:auto; position:absolute; width:" + json[i]["POPUP_WDTH_SIZEA_SZ"] + "px; border:4px solid #ddd; background:#fff; font-size:12pt; line-height:150%;'>";
	            } else if (json[i]["SRTNG_CRTR_VALUE"] == "ARBITRARY") {
	            	htmlStr = "<div id='layerPopup" + json[i]["POPUP_NO"] + "' style='height:" + json[i]["POPUP_VRTICL_SIZEA_SZ"] + "px; left:" + json[i]["LESI_LC_VALUE"] + "px; position:absolute; top:" + json[i]["UPDRC_LC_VALUE"] + "px; width:" + json[i]["POPUP_WDTH_SIZEA_SZ"] + "px; border:4px solid #ddd; background:#fff; font-size:12pt; line-height:150%;'>";
	            }
	            
	            htmlStr += "<div style='color: white; background-color: #01427A; padding:10px;'>공지사항</div><div style='background:#fff; padding:10px; overflow:auto;'>";
	            htmlStr += "제목 : ";
	            htmlStr += json[i]["POPUP_NM"];
	            htmlStr += "<br/>내용 :<br/>";
	            htmlStr += json[i]["POPUP_DTL_CN"];
	            htmlStr += "</div><div style='background:#fff; padding:10px; font-size:11pt; position:absolute; bottom:0; width:" + (Number(json[i]["POPUP_WDTH_SIZEA_SZ"]) - 10) + "px;'>";
	            
	            if (json[i]["THTDAY_UNFOLL_INDCT_YN"] == "Y") {
	            	htmlStr += "<div style='float:left;'><input type='checkbox' id='notToday" + json[i]["POPUP_NO"] + "' onclick=\"setCookieNotToday('noPopup_" + json[i]["POPUP_NO"] + "', '1'); $('#layerPopup" + json[i]["POPUP_NO"] + "').hide();\"><label for='notToday" + json[i]["POPUP_NO"] + "'> 오늘 그만 보기</label></div>";
	            }
	            
	            htmlStr += "<button type='button' onclick=\"$('#layerPopup" + json[i]["POPUP_NO"] + "').hide();\" style='float:right;'>닫기</button></div>";
	            htmlStr += "</div>";
	            
	            htmlDiv.innerHTML = htmlStr;
	            
	            document.getElementById("noticePopup").append(htmlDiv);
            }
        }        
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });


    // 로그인 공지사항
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/loginNoticeList.do",
        data : {},
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        if (json && json.length > 0) {

            for (var i=0; i < json.length; i++) {

                var htmlLi = document.createElement("li");

                var bbscttEsntalNo = json[i]["BBSCTT_ESNTAL_NO"];
                var bbscttTtlNm = json[i]["BBSCTT_TTL_NM"];
                var frstRegDt1 = json[i]["FRST_REG_DT2"];
                var frstRegDt2 = json[i]["FRST_REG_DT2"];
                if (frstRegDt2) {
                	frstRegDt2 = String(frstRegDt2).replace("-", ".").replace("-", ".");
                }
                
                var htmlStr = "<span class='preface'>안내</span>";
                htmlStr += "<a href=\"javascript:viewLoginNotice(" + bbscttEsntalNo + "); void(0);\" class='tit' title='" + bbscttTtlNm + "'>" + bbscttTtlNm + "</a>";
                htmlStr += "<time class='pubdate' datetime='" + frstRegDt1 + "'>" + frstRegDt2 + "</time>";
                
                htmlLi.innerHTML = htmlStr;
                
                document.getElementById("loginNotice").append(htmlLi);
            }
        }        
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });


    // 세션 종료 메시지 처리
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/selectSessionExpireMessage.do",
        data : {},
        method : "GET",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        if (json && json["message"]) {
        	alert(json["message"]);
        }        
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });

    
    window.addEventListener("message", receivePostMsg, false);
    
});

  </script>

<script language=javascript>
    // 휴대폰 본인 인증
    var DRMOK_window;
    var phoneType = "";
    
    function openDRMOKWindow(type) {
    	
    	var id = $("#userId3").val();
    	
    	if (!id) {
    		if (type == "1") {
    			alert("휴대폰 인증 등록을 위하여 아이디를 입력해주시기 바랍니다.");
    			return;
    		} else if (type == "2") {
    			alert("휴대폰 인증 삭제을 위하여 아이디를 입력해주시기 바랍니다.");
    			return;
    		}
    	}
    	
    	phoneType = type;
        window.name = 'sendJsp';
        DRMOK_window = window.open("<%=request_url%>", 'DRMOKWindow', 'width=630,height=700,scrollbars=no,toolbar=no,location=no,directories=no,status=no' );
        DRMOK_window.focus();
        if (DRMOK_window == null) {
            alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
        }
    }

    function receivePostMsg(event) {

        //console.log("event.origin", event.origin);
        //console.log("event.data", event.data);
        
        if (event.data["birthday"]) {
            if (phoneType == "1") {  // 휴대폰 인증 등록
                check1(event.data);
            } else if (phoneType == "2") {  // 휴대폰 인증 삭제
                check2(event.data);
            } else if (phoneType == "3") {  // 휴대폰 인증 로그인
                check3(event.data);
            }
        }
        
        phoneType = "";
    }
    
    function check1(data) {
    	
    	var id = $("#userId3").val();
    	var birthday = data["birthday"];
    	var gender = data["gender"];
    	var name = data["name"];
    	var telnum = data["telnum"];

        // 휴대폰 인증 등록
        $.ajax({
            url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/phoneRegist.do",
            data : { id : id, birthday : birthday },
            method : "POST",
            dataType : "json"
        })
        .done(function(json) {

            //console.log("json", json);

            if (json["result"] == 1) {
            	alert("등록되었습니다.");
            } else {
            	alert(json["msg"]);
            }
            
        })
        .fail(function(xhr, status, errorThrown) {
            alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
        });
    }

    function check2(data) {
    	
    	var id = $("#userId3").val();
    	var birthday = data["birthday"];
    	var gender = data["gender"];
    	var name = data["name"];
    	var telnum = data["telnum"];

        // 휴대폰 인증 삭제
        $.ajax({
            url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/phoneDelete.do",
            data : { id : id, birthday : birthday },
            method : "POST",
            dataType : "json"
        })
        .done(function(json) {

            //console.log("json", json);
            
            if (json["result"] == 1) {
            	alert("삭제되었습니다.");
            } else {
            	alert(json["msg"]);
            }

        })
        .fail(function(xhr, status, errorThrown) {
            alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
        });

    }

    function check3(data) {
    	
    	var birthday = data["birthday"];
    	var gender = data["gender"];
    	var name = data["name"];
    	var telnum = data["telnum"];

        // 휴대폰 인증 로그인
        $.ajax({
            url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/phoneLogin.do",
            data : { birthday : birthday },
            method : "POST",
            dataType : "json"
        })
        .done(function(json) {

            //console.log("json", json);
            
            if (json["msg"]) {
            	alert(json["msg"]);
            } else {
            	location.href = "${pageContext.request.contextPath}/";
            }

        })
        .fail(function(xhr, status, errorThrown) {
            alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
        });

    }

</script>

<script type="text/javascript">

var simpleAuthType = 0;

function init_popup_auth(num) {
	
	var id = $("#userId4").val();
	
	simpleAuthType = num;
	
	if (!id) {
		if (num == 2) {
			alert("간편인증 등록을 위하여 아이디를 입력해주시기 바랍니다.");
			return;
		} else if (num == 3) {
			alert("간편인증 삭제을 위하여 아이디를 입력해주시기 바랍니다.");
            return;
		}
	}
	
    /* 1. 간편인증 인증요청  */
    // eziok_std_process(간편인증 인증요청 생성 URL, 웹브라우져타입[WB:웹브라우져, MB:모바일웹, MWV:모바일웹View], callback함수명)
    eziok_std_process("https://gov.youthsafety.go.kr/eziok/eziok_auth.jsp", "WB", "printResult");
}

/* 2. 간편인증 결과 수신 callback 함수 예시  */
function printResult(data) {
    
	var resultCode = data.split("|")[0];
    var resultMsg = data.split("|")[1];
    
    if (resultCode == 0) {
        
    	// 간편인증 성공 완료시 처리 부분
        //document.querySelector("#result").textContent = resultMsg;
        
    	var result = JSON.parse(resultMsg);
        console.log("resultMsg", result);
        
        if (simpleAuthType == 1) {  // 로그인
        	checkSimple1(result);
        } else if (simpleAuthType == 2) {  // 등록
        	checkSimple2(result);
        } else if (simpleAuthType == 3) {  // 삭제
        	checkSimple3(result);
        }
        
    } else {
        // 간편인증 실패 완료시 처리 부분
        alert("Error : " + resultMsg);
    }
}

function checkSimple2(data) {
    
    var id = $("#userId4").val();
    var birthday = data["userbirthday"];
    var name = data["userName"];
    var telnum = data["userphone"];
    var ci = data["ci"];

    // 간편 인증 등록
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/simpleRegist.do",
        data : { id : id, birthday : birthday, ci : ci },
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);

        if (json["result"] == 1) {
            alert("등록되었습니다.");
        } else {
            alert(json["msg"]);
        }
        
    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}

function checkSimple3(data) {
    
    var id = $("#userId4").val();
    var birthday = data["userbirthday"];
    var name = data["userName"];
    var telnum = data["userphone"];
    var ci = data["ci"];
    
    // 간편 인증 삭제
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/simpleDelete.do",
        data : { id : id, birthday : birthday, ci : ci },
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        if (json["result"] == 1) {
            alert("삭제되었습니다.");
        } else {
            alert(json["msg"]);
        }

    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}

function checkSimple1(data) {
    
    var birthday = data["userbirthday"];
    var name = data["userName"];
    var telnum = data["userphone"];
    var ci = data["ci"];
    
    // 간편 인증 로그인
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/simpleLogin.do",
        data : { birthday : birthday, ci : ci },
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
        
        if (json["msg"]) {
            alert(json["msg"]);
        } else {
            location.href = "${pageContext.request.contextPath}/";
        }

    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}
</script>

</head>

<body>
  <div class="teen-wrapper">
    <div class="skip" role="navigation">
      <a href="#login">로그인 바로가기</a>
      <a href="#board">공지사항 바로가기</a>
      <a href="#footer">하단 바로가기</a>
    </div>

    <div class="page-container">
      <main class="contents" role="main">
        <div class="inner">
          <h1 class="logo"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/logo2.svg" alt="청소년안전망시스템"></h1>
          <div class="content_box">
            <div id="login" class="login_wrap">
              <h2 class="sec-tit">로그인</h2>
              <div class="inp_area">
                <div class="inp_box">
                  <input type="text" class="id_inp inp-txt" name="userId" id="userId" placeholder="아이디" onkeydown="if (window.event.keyCode == 13) { login(0); }" />
                </div>
                <div class="inp_box">
                  <input type="password" class="pw_inp inp-txt" name="userPw" id="userPw" placeholder="비밀번호" onkeydown="if (window.event.keyCode == 13) { login(0); }" />
                </div>
              </div>

              <div class="save_id">
                <label class="chk-custom" for="cbxRmbr">아이디 저장
                  <input type="checkbox" name="cbxRmbr" id="cbxRmbr">
                  <span class="checkmark"></span>
                </label>                
              </div>

              <div class="pass_simple_box">
                <ul class="list">
                  <li class="financial_pass">
                    <a href="javascript:certLogin(1); void(0);" id="financial-pass" title="금융인증서 로그인하기">
                      <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_pass01.svg" alt="">
                      <span class="txt">금융인증서</span>
                    </a>
                  </li>
                  <li class="joint_pass">
                    <a href="javascript:certLogin(2); void(0);" title="공동(공인)인증서 로그인하기">
                      <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_pass02.svg" alt="">
                      <span class="txt">공동인증서<br><span class="subtxt">(구 공인인증서)</span>
                    </span>
                    </a>
                  </li>
                  <li class="phone_pass">
                    <a href="javascript:openDRMOKWindow(3); void(0);" title="휴대폰 인증 로그인하기">
                      <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_pass03.svg" alt="">
                      <span class="txt">휴대폰 인증</span>
                    </a>
                  </li>
                  <li class="simple_pass">
                    <a href="javascript:init_popup_auth(1); void(0);" title="간편 인증 로그인하기">
                      <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_pass04.svg" alt="">
                      <span class="txt">간편 인증</span>
                    </a>
                  </li>
                </ul>
              </div>
              <div class="btn_area">
                <div class="login_box">
                  <a href="javascript:login(1); void(0);" class="btn" title="로그인">로그인</a>
                </div>
                <ul class="other_box">
                  <li>
                    <a href="javascript:layerPopup.open('find-id'); $('#find-id-name').val(''); $('#find-id-value').val(''); void(0);" id="find-id">아이디찾기</a>
                  </li>
                  <li>
                    <a href="javascript:layerPopup.open('find-pw'); $('#find-pw-id').val(''); $('#find-pw-name').val(''); $('#find-pw-value').val(''); void(0);" id="find-pw">비밀번호찾기</a>
                  </li>
                  <li>
                    <a href="javascript:window.open('${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userjoin/memberJoin.do', 'MemberJoin', 'left=100,top=100,width=1000,height=800'); void(0);">회원가입</a>
                  </li>
                  <li>
                    <a href="javascript:layerPopup.open('pass-pop'); $('#userId1').val(''); $('#userId2').val(''); void(0);">인증서관리</a>
                  </li>
                </ul>
              </div>
            </div>

            <div id="board" class="help_wrap">
              <div class="head">
                <h2 class="sec-tit">공지사항</h2>
                <a href="javascript:window.open('${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/loginNotice.do', 'loginNotice', 'width=1200, height=650'); void(0);" class="link_more" title="공지사항 전체보기로 이동"></a>
              </div>
              <div class="body">
                <ul class="board_simple" id="loginNotice">
                  <!--
                  <li>
                    <span class="preface">안내</span>
                    <a href="#" class="tit" title="청소년안전망시스템 사용 안내 바로가기">청소년안전망시스템 사용 안내</a>
                    <time class="pubdate" datetime="2022-10-31">2022.10.31</time>
                  </li>
                  -->
                </ul>
              </div>
              <div class="foot">
                <div class="help_desk">
                  <div class="infor">
                    <h3 class="tit">헬프데스크</h3>
                    <p class="help_number">02-2100-6000</p>
                    <p class="email">
                      <span class="txt">이메일</span>
                      <a href="mailto:cyber1388@kyci.or.kr" class="help_mail">cyber1388@kyci.or.kr</a>
                    </p>
                  </div>
                </div>
                <div class="install_program">
                  <!-- a태그는 페이지 이동일 때 사용 -->
                  <a href="./html/setup_page.html" class="btn" title="프로그램 설치 안내 바로가기">프로그램 설치 안내</a>
                  <!-- button 태그는 팝업이 뜰 때 사용 button type="button" class="btn">프로그램 설치 안내</button-->
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>

      <footer id="footer" class="footer" aria-role="footer">
        <div class="inner">
          <address class="adrs">(03171) 서울특별시 종로구 세종대로 209(세종로) 여성가족부</address>
          <p class="copyright"> Copyright@여성가족부 All Rights Reserved </p>
          
          <div class="law"><a href="javascript:layerPopup.open('terms-pop'); void(0);" title="개인정보처리방침으로 이동">개인정보처리방침</a></div>
        </div>
        </footer>
    </div>
  </div>


<!-- 아이디 찾기 -->
<section class="js-pop-area" aria-hidden="true">
  <div class="popUp find-id" role="dialog">
    <div class="layer-wrap">
      <div class="head">
        <button class="btn_close js-btn-close" title="팝업 닫기" onclick="$('div').find('.find-id').removeClass('active');"></button>
      </div>
      <div class="body">
        <h1 class="pop-tit">아이디 찾기</h1>
        <p class="para">아이디를 찾기 위해 등록한 휴대전화번호 또는<br/> 
          이메일을 입력하여 주세요.</p>

          <div class="form_area">
            <div class="inp_box">
              <input type="text" class="name_inp inp-txt" name="find-id-name" id="find-id-name" placeholder="성명" />
            </div>

            <div class="radio_area">
              <label class="rdo-custom" role="radio" for="find-id-B">휴대전화
                <input type="radio" id="find-id-B" name="find-id-method" value="phone" aria-hidden="true" onclick="$('#find-id-value').val(''); $('#find-id-value').attr('placeholder', '휴대전화 번호');" />
                <span class="rdomark"></span>
              </label>
              <label class="rdo-custom" role="radio" for="find-id-C">이메일
                <input type="radio" id="find-id-C" name="find-id-method" value="email" checked aria-hidden="true" onclick="$('#find-id-value').val(''); $('#find-id-value').attr('placeholder', '이메일 주소');" />
                <span class="rdomark"></span>
              </label>
            </div>
            
            <div class="inp_box">
              <input type="text" class="contact_inp inp-txt" id="find-id-value" name="find-id-value" placeholder="이메일 주소" />
            </div>
          </div>
      </div>
      <div class="foot">
        <button class="btn js-btn-close" onclick="findId();">확<span class="space"></span>인</button>
      </div>
    </div>
  </div>
</section>

<!-- 비밀번호 찾기 -->
<section class="js-pop-area" aria-hidden="true">
  <div class="popUp find-pw" role="dialog">
    <div class="layer-wrap">
      <div class="head">
        <button class="btn_close js-btn-close" title="팝업 닫기" onclick="$('div').find('.find-pw').removeClass('active');"></button>
      </div>
      <div class="body">
        <h1 class="pop-tit">비밀번호 찾기</h1>
        <p class="para">임시비밀전호 발급을 위하여 등록하신 아이디와 성명, 
          휴대전화번호 또는 이메일을 입력하여 주세요.</p>

          <div class="form_area">
            <div class="inp_area">
              <div class="inp_box">
                <input type="text" class="id_inp inp-txt" name="find-pw-id" id="find-pw-id" placeholder="아이디" />
              </div>
              <div class="inp_box">
                <input type="text" class="name_inp inp-txt" name="find-pw-name" id="find-pw-name" placeholder="성명" />
              </div>
            </div>

            <div class="radio_area">
              <label class="rdo-custom" for="find-pw-D">휴대전화
                <input type="radio" id="find-pw-D" name="find-pw-method" value="phone" onclick="$('#find-pw-value').val(''); $('#find-pw-value').attr('placeholder', '휴대전화 번호');" />
                <span class="rdomark"></span>
              </label>
              <label class="rdo-custom" for="find-pw-E">이메일
                <input type="radio" id="find-pw-E" name="find-pw-method" value="email" checked onclick="$('#find-pw-value').val(''); $('#find-pw-value').attr('placeholder', '이메일 주소');" />
                <span class="rdomark"></span>
              </label>
            </div>
            
            <div class="inp_box">
              <input type="text" class="contact_inp inp-txt" id="find-pw-value" name="find-pw-value" placeholder="이메일 주소" />
            </div>
          </div>
      </div>
      <div class="foot">
        <button class="btn js-btn-close" onclick="findPw();">확<span class="space"></span>인</button>
      </div>
    </div>
  </div>
</section>

<!-- 회원 가입 -->
<section class="js-pop-area" aria-hidden="true">
</section>

<!-- 인증서 관리 -->
<section class="js-pop-area" aria-hidden="true">
  <div class="popUp pass-pop" role="dialog">
    <div class="layer-wrap">
      <div class="head">
        <button class="btn_close js-btn-close" title="팝업 닫기" onclick="$('div').find('.pass-pop').removeClass('active');"></button>
      </div>
      <div class="body">
        <h1 class="pop-tit">인증서 관리</h1>

        <ul class="tab-list" role="tablist">
          <li role="none">
            <a href="javascript:$('div').find('.tab-panel').removeClass('active'); $('div').find('.financial_panel').addClass('active'); $('div').find('.tab_btn').removeClass('active'); $('#tab-list1').addClass('active'); void(0);" id="tab-list1" role="tab" aria-selected="true" aria-controls="tab-panel1" class="tab_btn active"><span>금융인증서</span></a>
          </li>
          <li role="none">
            <a href="javascript:$('div').find('.tab-panel').removeClass('active'); $('div').find('.certificate_panel').addClass('active'); $('div').find('.tab_btn').removeClass('active'); $('#tab-list2').addClass('active'); void(0);" id="tab-list2" role="tab" aria-controls="tab-panel2" class="tab_btn"><span>공동인증서</span></a>
          </li>
          <li role="none">
            <a href="javascript:$('div').find('.tab-panel').removeClass('active'); $('div').find('.phone_panel').addClass('active'); $('div').find('.tab_btn').removeClass('active'); $('#tab-list3').addClass('active'); void(0);" id="tab-list3" role="tab" aria-controls="tab-panel3" class="tab_btn"><span>휴대폰인증</span></a>
          </li>
          <li role="none">
            <a href="javascript:$('div').find('.tab-panel').removeClass('active'); $('div').find('.simple_panel').addClass('active'); $('div').find('.tab_btn').removeClass('active'); $('#tab-list4').addClass('active'); void(0);" id="tab-list4" role="tab" aria-controls="tab-panel4" class="tab_btn"><span>간편 인증</span></a>
          </li>
        </ul>

        <div class="panel_area">
          <div class="tab-panel financial_panel active" role="tabpanel" id="tab-panel1" aria-labelledby="tab-list1">
            <div class="inp_box">
              <input type="text" class="id_inp inp-txt" name="userId1" id="userId1" placeholder="로그인 아이디" />
              <input type="text" class="birthday_inp inp-txt" name="userBirthday1" id="userBirthday1" placeholder="생년월일" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');" maxlength="8" />
            </div>
            <div class="financial_area">
              <ul class="list">
                <li class="apply"><a href="javascript:doSignData(5); void(0);" title="금융인증서 등록, 새창">금융인증서 등록</a></li>
                <li class="delete"><a href="javascript:doSignData(6); void(0);" title="금융인증서 삭제, 새창">금융인증서 삭제</a></li>
              </ul>
            </div>
            
            <!--
            <div class="para">
              <button type="button" class="btn_note">인증서 로그인 안내</button>
            </div>
            -->
            
          </div>

          <div class="tab-panel certificate_panel" role="tabpanel" id="tab-panel2" aria-labelledby="tab-list2">
            <div class="inp_box">
              <input type="text" class="id_inp inp-txt" name="userId2" id="userId2" placeholder="로그인 아이디" />
              <input type="text" class="birthday_inp inp-txt" name="userBirthday2" id="userBirthday2" placeholder="생년월일" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');" maxlength="8" />
            </div>
            <div class="financial_area">
              <ul class="list">
                <li class="apply"><a href="javascript:doSignData(2); void(0);" title="공동인증서 등록, 새창">공동인증서 등록</a></li>
                <li class="delete"><a href="javascript:doSignData(3); void(0);" title="공동인증서 삭제, 새창">공동인증서 삭제</a></li>
              </ul>
            </div>
            
            <!--
            <div class="para">
              <button type="button" class="btn_note">인증서 로그인 안내</button>
            </div>
            -->
            
          </div>

          <div class="tab-panel phone_panel" role="tabpanel" id="tab-panel3" aria-labelledby="tab-list3">
            <div class="inp_box">
              <input type="text" class="id_inp inp-txt" name="userId3" id="userId3" placeholder="로그인 아이디" />
            </div>
            <div class="phone_area">
              <ul class="list">
                <li class="apply"><a href="javascript:openDRMOKWindow(1); void(0);" title="휴대폰 인증 등록, 새창">휴대폰 인증 등록</a></li>
                <li class="delete"><a href="javascript:openDRMOKWindow(2); void(0);" title="휴대폰 인증 삭제, 새창">휴대폰 인증 삭제</a></li>
              </ul>
            </div>
            
            <div class="para">
              <button type="button" class="btn_note">&nbsp;</button>
            </div>
            
            <!--
            <div class="phone_area">
              <ul class="list">
                <li class="apply"><a href="javascript:openDRMOKWindow(); void(0);" title="휴대폰 인증하기, 새창">휴대폰 인증하기</a></li>
              </ul>
            </div>
            -->
          </div>

          <div class="tab-panel simple_panel" role="tabpanel" id="tab-panel4" aria-labelledby="tab-list4">
            <div class="inp_box">
              <input type="text" class="id_inp inp-txt" name="userId4" id="userId4" placeholder="로그인 아이디" />
            </div>
            <div class="sns_area">
              <ul class="list">
                <li class="apply"><a href="javascript:init_popup_auth(2); void(0);" title="간편 인증 등록, 새창">간편 인증 등록</a></li>
                <li class="delete"><a href="javascript:init_popup_auth(3); void(0);" title="간편 인증 삭제, 새창">간편 인증 삭제</a></li>
              </ul>
            </div>
            
            <div class="para">
              <button type="button" class="btn_note">&nbsp;</button>
            </div>

            <!--
            <div class="sns_area">
              <ul class="list">
                <li><a href="#" title="카카오 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_kktlk.svg" alt="talk" role="img"></a></li>
                <li><a href="#" title="네이버 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_nvr.svg" alt="N" role="img"></a></li>
                <li><a href="#" title="페이스북 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_mt.svg" alt="f" role="img"></a></li>
                <li><a href="#" title="구글 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_ggl.svg" alt="G" role="img"></a></li>
              </ul>
            </div>
            -->
            
          </div>
        </div>
      </div>
      <div class="foot">
        <button class="btn js-btn-close">닫<span class="space"></span>기</button>
      </div>
    </div>
  </div>
</section>

<!-- 개인정보 처리방침 -->
<section class="popUp terms-pop" role="modal">
    <div class="layer-wrap">
      <div class="head">
        <button class="btn_close js-btn-close" title="팝업 닫기"></button>
      </div>
      <div class="body">
        <h1 class="pop-tit">개인정보처리방침</h1>
        <div class="notice_area">
          <h2 class="pop-sub-tit">개인정보보호위원회 &lt;개인정보보호 포털&gt; 개인정보 처리방침</h2>
          <p class="para">개인정보보호위원회는 정보주체의 자유와 권리 보호를 위해 &#12300;개인정보 보호법&#12301; 및 관계 법령이 정한 바를 준수하여, <br>
            적법하게 개인정보를 처리하고 안전하게 관리하고 있습니다.<br>
            이에 &#12300;개인정보 보호법&#12301; 제30조에 따라 정보주체에게 개인정보 처리에 관한 절차 및 기준을 안내하고, 이와 관련한 고충을 신속하고<br>
            원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립·공개합니다.</p>
        </div>
        
        <div class="temrs_type">
          <h3 class="term_tit">주요 개인정보 처리 표시(라벨링)</h3>

          <div class="list_wrap">
            <a href="#term-collector" class="item" title="일반 개인정보 수집 약관 상세보기로 이동합니다">
              <dl >
                <dt><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_term6.svg" alt="">
                  <span class="txt">일반 개인정보 수집</span>
                </dt>
                <dd class="label_area">
                  <div class="inner">
                    <p class="label_head">처리하는 개인정보 항목은 다음과 같습니다.</p>
                    <div class="label_cont">
                      <div class="cont_tit">&#9702; 사용자 정보 관리</div>
                      <ul class="list hypen">
                        <li> 필수정보 : 아이디, 비밀번호, 이름, 이메일, 직장주소, 지역구분, 역할구분, 단위시스템, 소속기관, 소속부서</li>
                        <li> 선택정보 : 성별, 전화번호, 
                          생일, 휴대전화번호, SNS구분,  SNS 아이디, 자격구분, 직위명, 기관장 여부, 입사일, 퇴사일, 서명 사진, 증명 사진</li>
                      </ul>
                    </div>
                  </div>                
                </dd>
              </dl>
            </a>
            
            <a href="#term-collector" class="item" title="개인정보의 처리 목적 상세보기로 이동합니다">
              <dl>
                <dt>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_term5.svg" alt="">
                  <span class="txt">개인정보의 처리 목적</span>
                </dt>
                <dd class="label_area">
                  <p class="label_head">개인정보 처리 목적은 다음과 같습니다.</p>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>&#12300;위기청소년 통합지원정보시스템&#12301; 구축･운영</li>
                      <li>&#12300;위기청소년 통합지원정보시스템&#12301; 사용자 정보 관리</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </a>

            <a href="#term-collector" class="item" title="개인정보의 보유기간 상세보기로 이동합니다">
              <dl>
                <dt>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_term2.svg" alt="">
                  <span class="txt">개인정보의 보유기간</span>
                </dt>
                <dd class="label_area">
                  <p class="label_head">개인정보 보유기간은 다음과 같습니다.</p>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>사용자 정보 관리 : 사용자 탈퇴(또는 퇴직 및 업무 변경) 후 30일</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </a>
            <a href="#term-manage" class="item" title="개인정보 처리위탁 상세보기로 이동합니다">
              <dl>
                <dt>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_term1.svg" alt="">
                  <span class="txt">개인정보 처리위탁</span>
                </dt>
                <dd class="label_area">
                  <p class="label_head">개인정보 처리 위탁 현황은 다음과 같습니다.</p>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>위탁받는자 : ㈜메타빌드</li>
                      <li>위탁업무 : &#12300;위기청소년 통합지원정보시스템&#12301; 구축･운영</li>
                    </ul>
                    <p class="star_para">※ 보다 상세한 내역은 연결된 세부 사항을 참조하시기 바랍니다.</p>
                  </div>
                </dd>
              </dl>
            </a>
            <a href="#term-use" class="item" title="개인정보 제공 상세보기로 이동합니다">
              <dl>
                <dt>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_term4.svg" alt="">
                  <span class="txt">개인정보의 제공</span>
                </dt>
                <dd class="label_area">
                  <p class="label_head">&#12300;사용자 정보&#12301;는 제3자 제공하지 않습니다. </p>
                  <p class="star_para">※ &#12300;위기청소년 통합지원정보시스템&#12301; 구축･운영을 위해 제3자에게 개인정보를 제공･연계하는 경우 별도 안내를 통해 동의를 받도록 하겠습니다.</p>
                </dd>
              </dl>
            </a>
            <a href="#term-read" class="item" title="개인정보 열람 창구 상세보기로 이동합니다">
              <dl>
                <dt>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_term3.svg" alt="">
                  <span class="txt">개인정보 열람 창구</span>
                </dt>
                <dd class="label_area">
                  <p class="label_head">개인정보 처리 관련 문의･ 불만처리 및 피해구제, 개인정보 자기결정권 행사를 원하시는 경우 다음의 연락처로 연락 바랍니다.</p>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>분야별 개인정보 보호 담당자 : 배찬수 사무관</li>
                      <li>연락처 : 02-2100-6603, prvicacy_tf@korea.kr</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </a>
          </div>
        </div>          
        <p class="body_note">※ 기호에 마우스 커서를 대시면 세부 사항을 확인할 수 있으며, 자세한 내용은 아래의 개인정보 처리방침을 확인하시기 바랍니다.</p>

        <hr class="term_line">
      </div>
      <div class="terms_cont">
        <section class="term_group" id="term-collector">
          <h1 class="cont_tit">제1조 개인정보의 처리목적, 개인정보의 처리 및 보유기간, 처리하는 개인정보의 항목</h1>
          <ol class="list" type="1">
            <li><b>개인정보의 처리 목적</b>
              <div class="txt">여성가족부 &#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F는 개인정보를 다음의 목적 이외의 용도로는 이용하지 않으며 이용 목적이 변경될 경우에는 동의를 받아 처리하겠습니다.</div>
              <ol class="sub_list hangul">
                <li>위기청소년 통합지원정보시스템 구축·운영 및 사용자관리</li>
              </ol>
            </li>
            <li><b>개인정보의 수집 및 보유</b>
              <ol class="sub_list hangul">
                <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;</b>에서는 서비스 이용에 필요한 최소한의 개인정보를 동의 아래 수집하고 있으며, 수집하는 개인정보 항목은 다음과 같습니다.
                  <table class="term-tbl">
                    <caption>개인정보파일의 명칭, 운영근거/처리목적, 보유 이용기간, 항목으로 구성</caption>
                    <colgroup>
                      <col width="17%">
                      <col >
                      <col width="20%">
                      <col >
                    </colgroup>
                    <thead>
                      <tr>
                        <th scope="col">개인정보파일의 명칭</th>
                        <th scope="col">운영근거 / 처리목적</th>
                        <th scope="col">개인정보의 보유·이용 기간</th>
                        <th scope="col">처리하는 개인정보 항목</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>행정업무지원 및 통합정보시스템 사용자 정보</td>
                        <td>&#12302;청소년복지 지원법&#12303; 제12조의2(위기청소년통합지원정보시스템의 구축 및 운영 등) / &#12300;위기청소년 통합지원정보시스템&#12301; 구축･운영 및 사용자 관리</td>
                        <td>사용자 탈퇴(또는 퇴직 및 업무 변경) 후 30일</td>
                        <td>
                          <ul>
                            <li>1) 행정업무지원정보<br>
                              아이디, 비밀번호, 이름, 이메일, 직장주소, 지역구분, 역할구분, 단위시스템, 소속기관, 소속부서<br>
                              선택정보 : -
                            </li>
                            <li>
                              2) 사용자 정보<br>
                              - 필수정보 : 아이디, 비밀번호, 이름, 이메일, 직장주소, 지역구분, 역할구분, 단위시스템, 소속기관, 소속부서<br>
                              - 선택정보 : 성별, 전화번호, 
                              생일, 휴대전화번호, SNS구분,  SNS 아이디, 자격구분, 직위명, 기관장 여부, 입사일, 퇴사일, 서명 사진, 증명 사진
                            </li>
                          </ul>                          
                        </td>
                      </tr>
                    </tbody>
                  </table>             
                </li>
                <li id="term-read">개인정보의 열람청구를 접수·처리하는 부서
                  <div class="sub_list">
                    <p class="txt_point">&#9702; &#12300;청소년안전망 시스템&#12301;구축 T/F</p>
                    <p class="txt_point">&#9702; 연락처 : 02-2100-6603</p>
                    <p class="txt_point">&#9702; 이메일 : <a href="mailto:prvicacy_tf@korea.kr" title="클릭하시면 메일 기본앱이 열립니다.">prvicacy_tf@korea.kr</a></p>
                  </div>
                </li>
                <li>개인정보보호 종합지원포털(www.privacy.go.kr) &gt; <b>민원마당</b> &gt; 개인정보의 열람 등 요구 &gt; 개인정보파일 목록 검색 &gt; 기관명에 “여성가족부”, 파일명에 “위기청소년”을 입력하면 세부 내용을 확인 할 수 있습니다. (<a href="https://www.privacy.go.kr/wcp/dcl/per/personalInfoFileList.do" title="인정보보호 종합지원포털 사이트로 이동, 새창" target="_blank" class="link">바로가기</a>)
                </li>
              </ol>
            </li>
          </ol>
        </section>
        <section class="term_group" id="term-use">
          <h1 class="cont_tit">제2조 개인정보의 제3자 제공에 관한 사항</h1>
          <ol class="list hangul">
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;</b>은 원칙적으로 정보주체의 개인정보를 수집·이용 목적으로 명시한 범위 내에서 처리하며, 다음의 경우를 제외하고는 정보주체의 사전 동의 없이는 본래의 목적 범위를 초과하여 처리하거나 제3자에게 제공하지 않습니다.</li>
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 &#12302;청소년지원 복지법&#12303; 제12조의2(위기청소년통합지원정보시스템의 구축 및 운영 등)제4항 및 제5항 등에 따라 고유식별정보(이름, 휴대전화번호)가 포함된 개인정보를 제3자에게 제공할 수 있습니다. 
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="90px">
                  <col >
                  <col width="120px">
                  <col width="125px">
                  <col >
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">제공받는자</th>
                    <th scope="col">제공 목적</th>
                    <th scope="col">제공 항목</th>
                    <th scope="col">보유 및 이용기간</th>
                    <th scope="col">관련 근거</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>경찰청</td>
                    <td class="point_weight">&#12300;위기청소년통합지원정보시스템&#12301; 구축･운영 및 서비스 연계 제공</td>
                    <td>이름, 휴대전화번호</td>
                    <td class="point_weight">만24세 까지</td>
                    <td>&#12302;청소년복지 지원법&#12303; 제12조의2</td>
                  </tr>
                </tbody>
              </table>
              <p class="star_para">※ 목적 외 개인정보 제3자 제공 내역은 다음의 <a href="#" title="개인정보의 목적 외 제 3자 제공 게시판 바로가기" class="link">링크</a>를 클릭하여 참조하시기 바랍니다.</p>
            </li>
            <li>제3자에게 정보 제공 시, 개인정보를 제공 받는 자에게 이용 목적, 이용 방법, 이용 기간, 이용 형태 등을 제한하거나 제공한 개인정보 파일에 대한 안정성 확보 조치를 담보하기 위해 파일 암호화, 주요 개인정보의 마스킹 처리 등을 보호조치를 적용합니다. </li>
          </ol>
        </section>
        <section class="term_group" id="term-manage">
          <h1 class="cont_tit">제3조 개인정보 처리 위탁에 관한 사항</h1>
          <ol class="list">
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 개인정보의 처리업무를 위탁하는 경우 다음의 내용이 포함된 문서에 의하고, 수탁자가 개인정보를 안전하게 처리하는 지 관리·감독 하고 있습니다.
              <div class="list-box">
                <ol class="list hangul">
                  <li>위탁업무 수행 목적 외 개인정보의 처리 금지에 관한 사항</li>
                  <li>개인정보의 관리적·기술적 보호조치에 관한 사항</li>
                  <li>개인정보의 안전관리에 관한 사항</li>
                </ol>
                <div class="txt">
                  위탁업무의 목적 및 범위, 재위탁 제한에 관한 사항, 개인정보 안전성 확보 조치에 관한 사항, 위탁업무와 관련하여 보유하고 있는 개인정보의 관리현황점검 등 감독에 관한 사항, 수탁자가 준수하여야할 의무를 위반한 경우의 손해배상책임에 관한 사항<br>
                    또한, 위탁하는 업무의 내용과 개인정보 처리업무를 위탁받아 처리하는 자(“수탁자”)에 대하여 해당 홈페이지에 공개하고 있습니다.
                </div>
              </div>
            </li>
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 다음의 개인정보 처리 업무를 위탁하고 있습니다.
              <table class="term-tbl">
                <caption>수탁업체명, 위탁업무 내용, 보유 및 이용기간</caption>
                <colgroup>
                  <col width="15%">
                  <col >
                  <col width="20%">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">수탁업체명</th>
                    <th scope="col">위탁업무 내용</th>
                    <th scope="col">보유 및 이용기간</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>㈜메타빌드</td>
                    <td>위기청소년 통합지원정보시스템 구축 및 운영</td>
                    <td>2024년 06월 까지</td>
                  </tr>
                </tbody>
              </table> 
              <p class="star_para">※ 위탁업무 내용이나 수탁자가 변경될 경우 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</p>
            </li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제4조 정보주체와 법정대리인의 권리·의무 및 그 행사 방법</h1>
          <ol class="list">
            <li>정보주체는 언제든지 다음과 같은 권리를 행사 할 수 있으며, 만14세 미만 아동의 법정대리인은 그 아동의 개인정보에 대한 열람, 정정·삭제, 처리정지를 요구할 수 있습니다.
              <div class="txt">※ “개인정보 처리 방법에 관한 고시(제2020-7호)” <a href="#" download="파일명이들어갑니다.txt" title="서식을 다운로드합니다." class="link">별지 제8호 서식 (열람 요구서)</a></div>
            </li>
            <li>위 권리 행사는 개인정보보호법 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며, 이에 대해 지체없이 조치하겠습니다.</li>
            <li>위 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” <a href="#" download="파일명.hwp" title="서식을 다운로드합니다." class="link">별지 제11호 서식</a>
              에 따른 위임장을 제출하셔야 합니다.</li>
            <li>개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제4항, 제37조 제2항등 다음의 경우에는 정보주체의 권리가 제한 될 수 있습니다.
              <div class="list-box">
                <ol class="list hangul">
                  <li>법률에 따라 열람이 금지되거나 제한되는 경우</li>
                  <li>다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</li>
                  <li>공공기관이 다음 각 목의 어느 하나에 해당하는 업무를 수행할 때 중대한 지장을 초래하는 경우
                    <ul class="sub_list hypen">
                      <li> 조세의 부과·징수 또는 환급에 관한 업무</li>
                      <li> &#12300;초·중등교육법&#12301; 및 &#12300;고등교육법&#12301;에 따른 각급 학교,&#12300;평생교육법&#12301;에 따른 평생교육시설, 그 밖의 다른 법률에 따라 설치 된 고등교육기관에서의 성적 평가 또는 입학자 선발에 관한 업무</li>
                      <li> 학력·기능 및 채용에 관한 시험, 자격 심사에 관한 업무</li>
                      <li> 보상금·급부금 산정 등에 대하여 진행 중인 평가 또는 판단에 관한 업무</li>
                      <li> 다른 법률에 따라 진행 중인 감사 및 조사에 관한 업무</li>
                    </ul>
                  </li>
                </ol>
              </div>
            </li>
            <li>개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상 으로 명시되어 있는 다음의 경우에는 그 삭제를 요구할 수 없습니다.
              <div class="list-box">
                <ol class="list hangul">
                  <li>법률에 특별한 규정이 있거나 법령상 의무를 준수하기 위하여 불가피한 경우</li>
                  <li>다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</li>
                  <li>공공기관이 개인정보를 처리하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우</li>
                  <li>개인정보를 처리하지 아니하면 정보주체와 약정한 서비스를 제공하지 못하는 등 계약의 이행이 곤란한 경우로서 정보주체가 그 계약의 해지 의사를 명확하게 밝히지 아니한 경우</li>
                  <li>개인정보의 열람, 정정·삭제, 처리정지 요구에 대해서는 10일 이내에 해당 사항에 대한 여성가족부의 조치를 통지 합니다.<br>개인정보의 열람, 정정·삭제, 처리정지 요구는 해당 부서를 통해서 가능합니다.</li>
                </ol>
              </div>
            </li>
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다. 이때 본인 또는 법정대리인의 신분증 사본 등을 요청할 수 있습니다.</li>
            <li>정보주체는 개인정보의 처리에 관한 동의 여부, 동의 범위 등을 선택하고 결정할 권리,  개인정보의 처리로 인하여 발생한 피해를 신속하고 공정한 절차에 따라 구제받을 권리 등을 행사할 수 있습니다.</li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제5조 개인정보의 파기</h1>
          <ol class="list">
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 원칙적으로 개인정보 처리목적이 달성된 개인정보는 지체없이 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.
              <div class="list-box">
                <ol class="list hangul">
                  <li>파기 절차
                    <div class="txt">
                      개인정보는 목적 달성 후 즉시 또는 별도의 공간에 옮겨져 내부 방침 및 기타 관련법령에 따라 일정기간 저장된 후 파기됩니다. 별도의 공간으로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.
                    </div>
                  </li>
                  <li>파기 기한 및 파기 방법
                    <div class="txt">                      
                      보유기간이 만료되었거나 개인정보의 처리목적달성, 해당 업무의 폐지 등 그 개인정보가 불필요하게 되었을 때에는 지체없이 파기합니다. 전자적 파일형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다. 종이에 출력된 대인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.
                    </div>
                  </li>
                </ol>
              </div>
            </li>
            <li>여성가족부 &#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F는 『공공기록물 관리에 관한 법률』등에 따라 보유기간이 경과한 개인정보를 보존해야 하는 경우 별도의 데이터베이스 테이블 등에 분리하여 보관하며, 보존기간이 경과 후에는 지체없이 파기합니다.</li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제6조 개인정보 자동수집 장치의 설치ㆍ운영 및 거부에 관한 사항</h1>
          <ol class="list">
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;</b>은 이용자에게 개인형 서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 '쿠키(cookie)'를 사용합니다.</li>
            <li>쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자들의 PC 컴퓨터내의 하드디스크에 저장되기도 합니다.
              <ol class="sub_list hangul">
                <li>쿠키의 사용 목적 : 자주 찾는 서비스를 설정할 수 있도록 하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.</li>
                <li>쿠키의 설치·운영 및 거부
                  <ul class="sub_list circle">
                    <li>인터넷 익스플로러(Internet Explorer) : 웹브라우저 상단의 도구 &gt; 인터넷 옵션 &gt; 개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부할 수 있습니다.</li>
                    <li>크롬(Chrome) : 웹브라우저 상단의 설정 &gt; 개인정보 및 보안 &gt; 쿠키 및 기타 사이트 데이터를 클릭하여 옵션 설정을 통해 모든 쿠키 차단을 설정할 수  있습니다.</li>
                    <li>파이어폭스(FireFox) : 웹브라우저 메뉴를 클릭하여 설정 &gt; 개인 정보 및 보안 패널에서 향상된 추적 방지기능(Enhanced Tracking Protection)의 옵션 설정을 통해 쿠키 차단을 설정할 수  있습니다.</li>
                    <li>엣지(Edge) : 설정 메뉴 &gt; 쿠키 및 사이트 권한 클릭 &gt; 사이트에서 쿠키 데이터를 저장하고 읽도록 허용 옵션 설정을 통해 모든 쿠키 차단을 설정할 수  있습니다.</li>
                  </ul>
                </li>
                <li>쿠키 저장을 거부할 경우 개인형 서비스 이용에 어려움이 발생할 수 있습니다.</li>
              </ol>
            </li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제7조 개인정보의 안전성 확보 조치</h1>
          <ol class="list">
            <li>여성가족부 <b class="point_color">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적, 관리적, 물리적 조치를 하고 있습니다.
              <div class="list-box">
                <ol class="list">
                  <li>'개인정보의 안전성 확보조치 기준에 의거하여 내부관리계획을 수립 및 시행합니다.</li>
                  <li>개인정보취급자 지정의 최소화 및 교육개인정보취급자의 지정을 최소화하고 정기적인 교육을 시행하고 있습니다.</li>
                  <li>개인정보에 대한 접근 제한개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여, 변경, 말소를 통하여 개인정보에 대한 접근을 통제하고, 침입차단시스템과 탐지시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있으며 권한 부여, 변경 또는 말소에 대한 내역을 기록하고, 그 기록을 최소 3년간 보관하고 있습니다.</li>
                  <li>접속기록의 보관 및 위변조 방지개인정보처리시스템에 접속한 기록(웹 로그, 요약정보 등)을 최소 2년 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 관리하고 있습니다.</li>
                  <li>개인정보의 암호화이용자의 개인정보는 암호화 되어 저장 및 관리되고 있습니다. 또한 중요한 데이터는 저장 및 전송 시 암호화하여 사용하는 등의 별도 보안기능을 사용하고 있습니다.</li>
                  <li>해킹 등에 대비한 기술적 대책여성가족부는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신‧점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적, 물리적으로 감시 및 차단하고 있습니다.</li>
                  <li>비인가자에 대한 출입 통제개인정보를 보관하고 있는 개인정보시스템의 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.</li>
                  <li>개인정보 침해사고 발생에 대응하기 위한 접속기록의 보관 및 위조‧변조 방지를 위한 조치</li>
                  <li>개인정보의 안전한 보관을 위한 보관시설의 마련 또는 잠금 장치의 설치 등 물리적 조치 등</li>
                </ol>
              </div>
            </li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제8조 권익침해 구제 방법</h1>
          <ol class="list">
            <li>정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.
              <ul class="sub_list circle">
                <li>개인정보 침해신고센터(한국인터넷진흥원 운영) : (국번없이) 118 (<a href="https://privacy.kisa.or.kr/" title="개인정보 침해신고센터 홈페이지 바로가기, 새창" target="_blank" class="link">privacy.kisa.or.kr</a>)</li>
                <li>개인정보 분쟁조정위원회 : (국번없이) 1833-6972 (<a href="https://www.kopico.go.kr/" title="개인정보 분쟁조정위원회 홈페이지 바로가기, 새창" target="_blank" class="link">www.kopico.go.kr</a>)</li>
                <li>대검찰청 사이버범죄수사단 : (국번없이) 1301 (<a href="https://www.spo.go.kr" title="대검찰청 사이버범죄수사단 홈페이지 바로가기, 새창" target="_blank" class="link">www.spo.go.kr</a>)</li>
                <li>경찰청 사이버안전국 : (국번없이) 182 (<a href="https://cyberbureau.police.go.kr/" title="경찰청 사이버안전국 홈페이지 바로가기, 새창" target="_blank" class="link">cyberbureau.police.go.kr</a>)</li>
              </ul>
            </li>
            <li><b class="point_color">위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 정보주체의 개인정보 자기결정권을 보장하고, 개인정보침해로 인한 상담 및 피해 구제를 위해 노력하고 있으며, 신고나 상담이 필요한 경우 아래의 담당부서로 연락해 주시기 바랍니다.
              <ul class="sub_list hypen">
                <li> 부서명 : <b class="point_color">&#12300;청소년안전망 시스템&#12301;개인정보 분야별 보호담당자</b></li>
                <li> 담당자 : <b class="point_color">배찬수 사무관</b></li>
                <li> 연락처 : &lt;<b class="point_color">02-2100-6603</b>&gt;, &lt;<b class="point_color">privacy_tf@korea.kr</b>&gt;</li>
              </ul>
            </li>
            <li>&#12300;개인정보보호법&#12301;제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.
              <p class="star_para">※ 중앙행정심판위원회 : (국번없이) 110 (<a href="https://www.simpan.go.kr/" title="중앙행정심판위원회 홈페이지 바로가기, 새창" target="_blank" class="link">www.simpan.go.kr</a>)</p>
            </li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제9조 개인정보 보호책임자에 관한 사항</h1>
          <ol class="list">
            <li>개인정보보호법 제31조 제1항에 따라 지정한 개인정보 보호책임자는 다음과 같습니다.
              <div class="list-box">
                <ul class="list">
                  <li>여성가족부 개인정보보호책임자 : 정책기획관 황윤정                
                    <p class="sub_list">- 연락처 : <b class="point_color">02-2100-6122, youngocho@korea.kr</b></p>
                    <p class="sub_list">※ 개인정보 분야별 보호담당자로 연결됩니다.</p>
                  </li>
                  <li>위기청소년 통합지원정보시스템&#12301;개인정보 분야별 보호책임자 : 박정식 <b class="point_color">
                    서기관</b>
                    <p class="sub_list"> - 연락처 : <b class="point_color">02-2100-6572, p777@korea.kr</b></p>
                    <p class="sub_list">※ 개인정보 분야별 보호담당자로 연결됩니다.</p>
                  </li>
                  <li>위기청소년 통합지원정보시스템&#12301;개인정보 분야별 보호담당자 :  배찬수 <b class="point_color">사무관</b>
                    <p class="sub_list">- 담당자 연락처 : <b class="point_color">02-2100-6603, privacy_tf@korea.kr</b></p>
                  </li>
                </ul>
              </div>
            </li>
            <li>정보주체는 여성가족부 <b class="point_color point_weight">&#12300;위기청소년 통합지원정보시스템&#12301;</b>의 서비스(또는 사업)를 이용하시면서 발생한 모든 개인정보보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의할 수 있습니다. 여성가족부 <b class="point_color point_weight">&#12300;위기청소년 통합지원정보시스템&#12301;구축 T/F</b>는 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.</li>
          </ol>
        </section>
        <section class="term_group">
          <h1 class="cont_tit">제10조 개인정보 처리방침 변경</h1>
          <ol class="list">
            <li>이 개인정보 처리방침은 2022. 9 1.부터 적용됩니다.</li>
            <li>이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다.
              <div class="txt"> - <b class="point_color">2022. 8 31. 적용</b> (<a href="#" title="2022년 8월 31일의 개인정보처리방침 바로가기">클릭</a>)</div>
            </li>
          </ol>
        </section>
      </div>
    </div>
</section>


<!-- 인증서 로그인 -->

    <form id='reqForm' name='reqForm' method='post' action="${pageContext.request.contextPath}/MagicLine4Web/ML4WebProcess/signedFormR2.jsp" target="signedFormR">
    <!-- 결과 수신 메시지  -->
    <input type="hidden" id="signOrigin" name="signOrigin" /> <!-- 180701 서명 원문 폼 추가 -->
    <input type="hidden" id='sign' name='sign' />
    <input type="hidden" id='csCheckType' name='csCheckType' value="1" />
    <input type="hidden" id='signType' name='signType' />
    <input type="hidden" id='loginId' name='loginId' value="" />
    <input type="hidden" id='birthday' name='birthday' value="" />
    <input type="hidden" id='signData' name='signData' value="youthsafety" />
    </form>

<div id="selectCertContainer1" style="width:100%;margin-top:0; display:none;"></div>
<div id="startCs" style="width:100%;margin-top:0; display:none;"></div>

<div id="dscertContainer">
    <iframe name="dscert" id="dscert" name="dscert" src="" scrolling="no" width="100%" height="100%" frameborder="0" allowTransparency="true" style="display: block; position:fixed;z-index:100010;top:0px;left:0px;width:100%;height:100%;"></iframe>
</div>

<iframe name="signedFormR" id="signedFormR" style="display: block; height:0; width:0; border:0; border:none; visibility:hidden;"></iframe>

<!-- 인증서 로그인 끝 -->


<!-- 본인인증서비스 요청 form --------------------------->
<form name="reqDRMOKForm" method="post" action="">
    <input type="hidden" name="req_info"      value = "<%=encReqInfo%>">
    <input type="hidden" name="rtn_url"       value = "<%=rtn_url%>">
    <input type="hidden" name="cpid"          value = "<%=cpId%>">
    <input type="hidden" name="newpop"        value = "Y">
</form>
<!--End 본인인증서비스 요청 form ----------------------->


<div id="noticePopup"></div>


</body>
</html>
