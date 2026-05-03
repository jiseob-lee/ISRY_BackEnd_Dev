<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.*,java.text.SimpleDateFormat" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.dreamsecurity.crypt.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%!
public String getBaseUrl(HttpServletRequest request) {
	String scheme = request.getScheme() + "://";
	String serverName = request.getServerName();
	String serverPort = (request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : ":" + request.getServerPort();
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
    
    String root = session.getServletContext().getRealPath("/");
    root += root.endsWith("/") || root.endsWith("\\") ? "" : "/";
    //     - 암호문자열 = msgEncrypt(암호화 시킬 값, 인증서 경로(운영 서버 경로));
    //String encReqInfo = mscr.msgEncrypt(reqInfo, "/app/ISRY_BackEnd.war/WEB-INF/mok/cert/youthsafeCert.der");
	String encReqInfo = "";  //mscr.msgEncrypt(reqInfo, root + "WEB-INF/mok/cert/youthsafeCert.der");
    
    // 3.3 암호화된 거래요청정보 URL 인코딩
    encReqInfo = URLEncoder.encode(encReqInfo);
    
    // 4. 휴대폰본인확인 요청정보 
    // 4.1 본인인증 결과수신 받을 회원사 URL 설정
    String rtn_url = getBaseUrl(request) + "/MOK/mok_webauth_result2.jsp";      // 본인인증 결과수신 받을 URL
    
    //System.out.println("#### rtn_url : " + rtn_url);
    
    // 4.2 휴대폰본인확인 요청 URL 생성
    //     - https://휴대폰본인확인 URL?cpid=<회원사ID>&rtn_url=<회원사결과수신URL>&req_info=<암호화된 거래요청정보1>
    //     - 운영 휴대폰본인확인 URL : https://www.mobile-ok.com/popup/common/hscert.jsp
    String request_url = "https://www.mobile-ok.com/popup/common/hscert.jsp?cpid=" + cpId +"&rtn_url=" + rtn_url +"&req_info=" + encReqInfo;      // 본인인증 요청 URL
    
    // 5. 휴대폰본인확인 요청
    //    - function openDRMOKWindow 함수 실행
	
%>

<%
LocalDateTime now = LocalDateTime.now();
String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
String refreshParam = "?p=" + currentTime;
%>

<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  
  <c:choose>
    
    <c:when test="${profile eq 'real1' or profile eq 'real2' }">
      <title>청소년안전망시스템 로그인</title>
    </c:when>
    
    <c:otherwise>
      <title>청소년안전망시스템 로그인 <c:out value="${profile }" /></title>
    </c:otherwise>
    
  </c:choose>
  
  <meta name="format-detection" content="telephone=no" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <!-- <meta name="referrer" content="no-referrer" /> -->

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/isry/itgcms/login/style2.css<%=refreshParam %>">

<%--
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/KDepense7/js/kos-ng.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/KDepense7/js/kos-ng.config.js<%=refreshParam %>"></script>
--%>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/jquery-3.5.1.min.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/common_ui.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/json2.js<%=refreshParam %>"></script>
  <!-- <script type="text/javascript" src="${pageContext.request.contextPath}/MagicLine4Web/ML4Web/js/ext/jquery-ui.min.js<%=refreshParam %>"></script> -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/MagicLine4Web/ML4Web/js/ext/jquery.blockUI.js<%=refreshParam %>"></script>

  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_exproto.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_install.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_daemon.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/nxWeb/js/nxweb_config.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/nxWeb/js/TouchEnNxWeb_Interface.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/nxWeb/js/TouchEnNxWeb.js<%=refreshParam %>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resource/raonnx/cmn/TouchEnNx_loader.js<%=refreshParam %>"></script>

  <script type="text/javascript" src="${pageContext.request.contextPath}/MagicLine4Web/ML4Web/js/ext/ML_Config.js<%=refreshParam %>"></script>
  
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/notify.min.js<%=refreshParam %>"></script>

  <c:choose>
    
    <c:when test="${ SERVER eq 'rybwas11' or SERVER eq 'rybwas21' }">
      
      <script type="text/javascript" src="https://cert.ez-iok.com/stdauth/ds_auth_ptb/asset/js/ptb_ezauth_proc.js<%=refreshParam %>"></script>
      
      <script type="text/javascript">
        var isInternet = true;
      </script>
    </c:when>
    <c:when test="${ SERVER eq 'ryewas11' or SERVER eq 'ryewas21' }">
      <script type="text/javascript" src="https://scert.ez-iok.com/stdauth/ds_auth_ptb/asset/js/ptb_ezauth_proc.js<%=refreshParam %>"></script>
      
      <script type="text/javascript">
        var isInternet = true;
      </script>
    </c:when>
    <c:otherwise>
      <script type="text/javascript">
        var isInternet = false;
      </script>
    </c:otherwise>
    
  </c:choose>

  <c:choose>
    
    <c:when test="${ SERVER eq 'ryewas11' or SERVER eq 'ryewas21' }">
      <script type="text/javascript">
        var isPre = true;
      </script>
    </c:when>
    
    <c:otherwise>
      <script type="text/javascript">
        var isPre = false;
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
  
  function popupCenter(url, title, w, h) {
    // Fixes dual-screen position                         Most browsers      Firefox
    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;
    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus();
    }
  }

  /*
  const popupCenter = ({url, title, w, h}) => {
	    // Fixes dual-screen position                             Most browsers      Firefox
	    const dualScreenLeft = window.screenLeft !==  undefined ? window.screenLeft : window.screenX;
	    const dualScreenTop = window.screenTop !==  undefined   ? window.screenTop  : window.screenY;

	    const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	    const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	    const systemZoom = width / window.screen.availWidth;
	    const left = (width - w) / 2 / systemZoom + dualScreenLeft;
	    const top = (height - h) / 2 / systemZoom + dualScreenTop;
	    const newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

	    if (window.focus) newWindow.focus();
	}
  */
  
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
      return "";
  }

  function deleteCookie(name) {
      var expireDate = new Date();
      expireDate.setDate(expireDate.getDate() - 1);
      document.cookie = name + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
  }

  function doSignData(num) {
      
      //console.log("num", num);
      if (num == 5 || num == 6) {
		if (!isInternet) {
			alert("인터넷망에서만 사용이 가능합니다.");
			return;
		}
      }
      
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
          document.reqForm.formUserId.value = $("#userId").val();
          document.reqForm.formUserPw.value = $("#userPw").val();
          
          //console.log("formUserId", document.reqForm.formUserId.value);
          //console.log("formUserPw", document.reqForm.formUserPw.value);
          
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
          document.reqForm.formUserId.value = $("#userId").val();
          document.reqForm.formUserPw.value = $("#userPw").val();
          
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
          
          if (typeof message[key] === "object") {
              htmlStream += '<tr>';           
              htmlStream += ' <td colspan="2"><b>' + key + '</b></td>';
              htmlStream += '</tr>';
              for (var inKey in message[key]) {      
                  htmlStream += "<tr>";
                  htmlStream += ' <td>&nbsp;&nbsp;&nbsp;&nbsp;' + inKey + '</td>';    
                  htmlStream += ' <td>' + message[key][inKey] + '</td>';
                  htmlStream += "</tr>";
              }
              
          } else {
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

    // 아이디, 비밀번호 체크
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/idpwCheck.do",
        data : { userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {
    });
    
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/userLogin2.do",
        //url : "${pageContext.request.contextPath}/loginProcess.do",
        data : {userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
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
                    data : {userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
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
            
        	if (msg.indexOf("인증서 로그인") == -1 && msg.indexOf("2차") == -1) {
	            alert(msg);
        	
        	} else {
        		//console.log("msg", msg);
        		var msgArr = msg.split(",");
        		var signList = $(".btn-sign .list-sign li");
        		//console.log("signList.length", signList.length);
        		
        		if (msgArr[1].charAt(0) == "Y") {
        			$(signList[1]).css("border-color", "#2972d7");
        		} else {
        			$(signList[1]).css("border-color", "#d4dfe9");
        		}
        		if (msgArr[1].charAt(1) == "Y") {
        			$(signList[0]).css("border-color", "#2972d7");
                } else {
                	$(signList[0]).css("border-color", "#d4dfe9");
                }
        		if (msgArr[1].charAt(2) == "Y") {
        			$(signList[2]).css("border-color", "#2972d7");
                } else {
                	$(signList[2]).css("border-color", "#d4dfe9");
                }
        		if (msgArr[1].charAt(3) == "Y") {
        			$(signList[3]).css("border-color", "#2972d7");
                } else {
                	$(signList[3]).css("border-color", "#d4dfe9");
                }
        		
                $.notify(msgArr[0], {
                    className: 'success',
                    globalPosition: 'bottom right'
                });
        	}
        
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

	// 금융인증서 로그인은 인터넷망에서만 이용이 가능함.
	if (num == 1 && !isInternet) {
	    alert("인터넷망에서만 사용이 가능합니다.");
	    return;
	}
	
	var userId = $("#userId").val();
	var userPw = $("#userPw").val();

    if (userId == null || userId.trim() == "") {
        alert("로그인 아이디를 입력해주시기 바랍니다.");
        return;
    }
    if (userPw == null || userPw.trim() == "") {
        alert("로그인 비밀번호를 입력해주시기 바랍니다.");
        return;
    }
    
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
	//window.open(url, 'loginNoticeView', 'width=1200, height=650');
	popupCenter(url, '공지사항 상세보기', 1200, 650);
}

//이미지 로딩이 안되었을 때 재로드 처리
function checkImageLoad(image, count) {
    var isLoaded = image.complete && image.naturalHeight !== 0;
    if (!isLoaded && count < 10) {

        var url = image.src.indexOf("?") == -1 
            ? image.src + "?t=" + new Date().getTime() 
            : image.src.substr(0, image.src.indexOf("?")) + "?t=" + new Date().getTime();
        
        //if (location.href.indexOf("gov.youthsafety.go.kr") > -1 
        		//&& url.indexOf("gov.youthsafety.go.kr") > -1) {
        	//console.log("123");
        	//url = url.substring("https://gov.youthsafety.go.kr".length);
        	//console.log("url", url);
        //}
        
        //console.log("length", "https://gov.youthsafety.go.kr".length);
        
        image.src = url;
        console.log(image.src);
        setTimeout(function() { checkImageLoad(image, count + 1); }, 200);
    }
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
                    htmlStr = "<div id='layerPopup" + json[i]["POPUP_NO"] + "' class='drag' style='height:" + json[i]["POPUP_VRTICL_SIZEA_SZ"] + "px; left:0; right:0; margin-left:auto; margin-right:auto; top:0; bottom:0; margin-top:auto; margin-bottom:auto; position:absolute; width:" + json[i]["POPUP_WDTH_SIZEA_SZ"] + "px; border:1px solid #2972d7; border-radius : 8px; background:#fff; font-size:12pt; line-height:150%;'>";
                } else if (json[i]["SRTNG_CRTR_VALUE"] == "ARBITRARY") {
                    htmlStr = "<div id='layerPopup" + json[i]["POPUP_NO"] + "' class='drag' style='height:" + json[i]["POPUP_VRTICL_SIZEA_SZ"] + "px; left:" + json[i]["LESI_LC_VALUE"] + "px; position:absolute; top:" + json[i]["UPDRC_LC_VALUE"] + "px; width:" + json[i]["POPUP_WDTH_SIZEA_SZ"] + "px; border:4px solid #ddd; background:#fff; font-size:12pt; line-height:150%;'>";
                }
                
                htmlStr += "<div class='header' style='color: white; background-color: #2972d7;border-radius : 5px 5px 0px 0px; padding:10px; font-size:15px; font-weight:bold;'>공지사항</div><div style='background:#fff; font-size:13px; color:#111111; padding:10px; height:" + (json[i]["POPUP_VRTICL_SIZEA_SZ"] - 90) + "px; overflow:auto;'>";
                htmlStr += "제목 : ";
                htmlStr += json[i]["POPUP_NM"];
                htmlStr += "<br/>내용 : <br/>";
                htmlStr += json[i]["POPUP_DTL_CN"];
                //htmlStr += "</div><div style='background:#fff; padding:10px; font-size:11pt; position:absolute; bottom:0; width:" + (Number(json[i]["POPUP_WDTH_SIZEA_SZ"]) - 10) + "px;'>";
                htmlStr += "</div><div style='background:#fff; padding:10px; font-size:11pt; border-radius : 0px 0px 10px 10px; position:absolute; bottom:0; width : 100%;'>";
                
                if (json[i]["THTDAY_UNFOLL_INDCT_YN"] == "Y") {
                    htmlStr += "<div style='float:left; font-size:13px;'><input type='checkbox' style='position:relative; top:2px;' class='notToday' id='notToday" + json[i]["POPUP_NO"] + "' onclick=\"setCookieNotToday('noPopup_" + json[i]["POPUP_NO"] + "', '1'); $('#layerPopup" + json[i]["POPUP_NO"] + "').hide();\"><label for='notToday" + json[i]["POPUP_NO"] + "'> 오늘 그만 보기</label></div>";
                }
                
                htmlStr += "<button type='button' onclick=\"$('#layerPopup" + json[i]["POPUP_NO"] + "').hide();\" style='float:right;'>닫기</button></div>";
                htmlStr += "</div>";
                
                htmlDiv.innerHTML = htmlStr;
                
                document.getElementById("noticePopup").append(htmlDiv);
            }

            //console.log("drag length", $('.drag').length);
            $('.drag').draggable({ handle : '.header' });
            

            // 이미지 로딩이 안되었을 때 재로드 처리
            //var images = document.querySelectorAll('img');
            //images.forEach((image) => {
                //checkImageLoad(image, 1);
            //});

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

                var htmlStr = "<a href=\"javascript:viewLoginNotice(" + bbscttEsntalNo + "); void(0);\" class='tit' title='" + bbscttTtlNm + "'>";
                htmlStr += "	<span class='noti-flag'>안내</span>";
                htmlStr += "	<span class='noti-tit ellipsis'>" + bbscttTtlNm + "</span>";
                htmlStr += "	<span class='noti-date'>" + frstRegDt1 + "</span>";
                htmlStr += "</a>";
				
                //var htmlStr = "<span class='preface'>안내</span>";
                //htmlStr += "<a href=\"javascript:viewLoginNotice(" + bbscttEsntalNo + "); void(0);\" class='tit' title='" + bbscttTtlNm + "'>" + bbscttTtlNm + "</a>";
                //htmlStr += "<time class='pubdate' datetime='" + frstRegDt1 + "'>" + frstRegDt2 + "</time>";
                
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
    
    
    //if (window.document.documentNode) {
    	//alert("Internet Explorer (IE) 브라우저는 지원되지 않습니다.");
    //}
    
    if (detectIE()) {
    	alert("Internet Explorer (IE) 브라우저는 지원되지 않습니다.");
    }
    
});


function detectIE() {
    var ua = window.navigator.userAgent;

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
    	return true;
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        return true;
    }

    return false;
}


var clickJoin = function() {
	//if (isPre) {
		//alert("PRE 사이트는 회원가입을 받지 않습니다.");
	//} else {
	    var url = "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userjoin/memberJoin.do";
	    var width = 1100;
	    var height = 880;    
	    popupCenter(url, 'MemberJoin', width, height);
	//}
}

</script>

<script language=javascript>
    // 휴대폰 본인 인증
    var DRMOK_window;
    var phoneType = "";
    
    function openDRMOKWindow(type) {

        // 휴대폰 인증은 로그인은 인터넷망에서만 이용이 가능함.
        if (!isInternet) {
            alert("인터넷망에서만 사용이 가능합니다.");
            return;
        }
        
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

    	if (type == "3") {
	        var userId = $("#userId").val();
	        var userPw = $("#userPw").val();
	
	        if (userId == null || userId.trim() == "") {
	            alert("로그인 아이디를 입력해주시기 바랍니다.");
	            return;
	        }
	        if (userPw == null || userPw.trim() == "") {
	            alert("로그인 비밀번호를 입력해주시기 바랍니다.");
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
            data : { birthday : birthday, userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
            method : "POST",
            dataType : "json"
        })
        .done(function(json) {

            //console.log("json", json);
            
	        var sessionCount = json["sessionCount"];
	        var msg = json["msg"];
	        //var msg = json["message"];
	        
	        if (sessionCount != null && sessionCount > 0) {
	            
	            // 기존에 같은 아이디로 로그인 된 세션이 있는 경우
	            
	            if (confirm("기존 접속을 끊고 로그인 하시겠습니까?")) {
	                
	                $.ajax({
	                    url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/userLogin4.do",
	                    data : {userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
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
	            
	            //$.notify('인증서 로그인을 진행해 주시기 바랍니다.', {
	                //className: 'success',
	                //globalPosition: 'bottom right'
	            //});
	            alert("오류가 발생했습니다.");
	        }
	        
            //if (json["msg"]) {
            	//alert(json["msg"]);
            //} else {
            	//location.href = "${pageContext.request.contextPath}/";
            //}

        })
        .fail(function(xhr, status, errorThrown) {
            alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
        });

    }

</script>

<script type="text/javascript">

var simpleAuthType = 0;

function init_popup_auth(num) {

    // 간편 인증은 로그인은 인터넷망에서만 이용이 가능함.
    if (!isInternet) {
        alert("인터넷망에서만 사용이 가능합니다.");
        return;
    }
    
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
    
    if (num == 1) {
        var userId = $("#userId").val();
        var userPw = $("#userPw").val();

        if (userId == null || userId.trim() == "") {
            alert("로그인 아이디를 입력해주시기 바랍니다.");
            return;
        }
        if (userPw == null || userPw.trim() == "") {
            alert("로그인 비밀번호를 입력해주시기 바랍니다.");
            return;
        }
    }
    
    /* 1. 간편인증 인증요청  */
    // eziok_std_process(간편인증 인증요청 생성 URL, 웹브라우져타입[WB:웹브라우져, MB:모바일웹, MWV:모바일웹View], callback함수명)
    if (isPre) {
        eziok_std_process("https://pre.youthsafety.go.kr/eziok/eziok_auth.jsp", "WB", "printResult");
    } else {
    	eziok_std_process("https://gov.youthsafety.go.kr/eziok/eziok_auth.jsp", "WB", "printResult");
    }
    
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
        data : { birthday : birthday, ci : ci , userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        //console.log("json", json);
                    
        var sessionCount = json["sessionCount"];
        var msg = json["msg"];
        //var msg = json["message"];
        
        if (sessionCount != null && sessionCount > 0) {
            
            // 기존에 같은 아이디로 로그인 된 세션이 있는 경우
            
            if (confirm("기존 접속을 끊고 로그인 하시겠습니까?")) {
                
                $.ajax({
                    url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/userLogin4.do",
                    data : {userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
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
            
            //$.notify('인증서 로그인을 진행해 주시기 바랍니다.', {
                //className: 'success',
                //globalPosition: 'bottom right'
            //});
            alert("오류가 발생했습니다.");
        }
        
        //if (json["msg"]) {
            //alert(json["msg"]);
        //} else {
            //location.href = "${pageContext.request.contextPath}/";
        //}

    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}


function idpwCheck(no) {

    if ($("#userId").val() == null || $("#userId").val().trim() == "") {
        alert("아이디를 입력해주시기 바랍니다.");
        return;
    }
    if ($("#userPw").val() == null || $("#userPw").val().trim() == "") {
        alert("비밀번호를 입력해주시기 바랍니다.");
        return;
    }

    var vcCbxRmbr = document.getElementById("cbxRmbr");
    if (vcCbxRmbr.checked == true) {
        setCookie("expuid", $("#userId").val(), 30);
    } else {
        deleteCookie("expuid");
    }

    // 아이디, 비밀번호 체크
    $.ajax({
        url : "${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/idpwCheck.do",
        data : { userId : $("#userId").val(), userPw : btoa($("#userPw").val()) },
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

        console.log("json", json);

        if (json["result"] != 2) {

            switch (json["result"]) {
            case 1 : 
                alert("회원가입 신청 상태입니다.");
                break;
            case 3 : 
                alert("회원가입 반려 상태입니다.");
                break;
            case 4 : 
                alert("사용자 사용중지 상태입니다.");
                break;
            case 5 : 
                alert("사용자 삭제 상태입니다.");
                break;
            case 6 : 
                alert("사용자 회원탈퇴 상태입니다.");
                break;
            case 7 : 
            	alert("로그인 차단 상태입니다.\n관리자에게 문의해주시기 바랍니다.");
                break;
            case 8 : 
            	alert("GOV 운영에서 로그인 할 수 없는 계정입니다.\nPRE 사이트에서 로그인 해주시기 바랍니다.");
                break;
            default :
            	if (json["message"] != null && json["message"] != "") {
            		alert(json["message"]);
            	} else {
            		alert("입력된 아이디, 비밀번호와 일치하는 회원정보가 없습니다.");
            	}
                break;
            }
        
        } else {

            switch (no) {
            case 1 : 
                certLogin(1);  // 금융인증서
                break;
            case 2 : 
                certLogin(2);  // 공동인증서
                break;
            case 3 : 
                openDRMOKWindow(3);  // 휴대폰인증
                break;
            case 4 : 
                init_popup_auth(1);  // 간편인증
                break;
            default :
                break;
            }        	
        }

    })
    .fail(function(xhr, status, errorThrown) {
        alert("오류가 발생했습니다.\n오류명 : " + errorThrown + "\n상태 : " + status);
    });
}

function checkCapsLock(event) {
    if (event.getModifierState("CapsLock")) {
        $.notify('Caps Lock 이 켜져 있습니다.', {
            className: 'success',
            globalPosition: 'bottom right'
        });
    }
}

</script>

</head>

<body>


		<div class="login-wrap">
			<!-- s: container -->
			<div class="login-cont">
				<h1 class="login-logo">
					<a href="javascript:;"><span class="blind">청소년안전망시스템</span></a>
				</h1>
				<div class="login-box">
					<h2 class="tit-login">로그인
						<span class="info">아이디와 비밀번호를 입력하고 2차 인증을 해주세요.</span>
					</h2>
					<div class="section-login">
						<div class="frm-login">
							<form action="" method="">
								<div class="input_form">
									<div class="input_box type-id">
										<input type="text" name="userId" id="userId" title="아이디 입력" placeholder="아이디" onkeypress="checkCapsLock(event); if (event.keyCode == 13) { login(0); }" />
									</div>
									<div class="input_box type-pw">
										<input type="password" name="userPw" id="userPw" title="비밀번호 입력" placeholder="비밀번호" onkeypress="checkCapsLock(event); if (event.keyCode == 13) { login(0); }" />
									</div>
								</div>
								<div class="chk_save">
									<span class="chk_form">
										<input type="checkbox" name="cbxRmbr" id="cbxRmbr" />
										<label for="cbxRmbr">아이디 저장</label>
									</span>
								</div>
								<ul class="login_util">
									<li><a href="javascript:layerPopup.open('find-id'); $('#find-id-name').val(''); $('#find-id-value').val(''); void(0);" id="find-id">아이디찾기</a></li>
									<li><a href="javascript:layerPopup.open('find-pw'); $('#find-pw-id').val(''); $('#find-pw-name').val(''); $('#find-pw-value').val(''); void(0);" id="find-pw">비밀번호찾기</a></li>
									<!-- <li><a href="javascript:window.open('${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userjoin/memberJoin.do', 'MemberJoin', 'left=100,top=100,width=1000,height=800'); void(0);">회원가입</a></li> -->
									<li><a href="#" onclick = "clickJoin();">회원가입</a></li>
									<li><a href="javascript:layerPopup.open('pass-pop'); $('#userId1').val(''); $('#userId2').val(''); void(0);">인증서관리</a></li>
								</ul>
							</form>
						</div>
						<div class="btn-sign">
							<ul class="list-sign">
								<li><a href="javascript:idpwCheck(1); void(0);"><span class="txt-sign bank">금융인증서</span></a></li>
								<li><a href="javascript:idpwCheck(2); void(0);"><span class="txt-sign comm">공동인증서</span><em class="txtsub-sign">구 공인인증서</em></a></li>
								<li><a href="javascript:idpwCheck(3); void(0);"><span class="txt-sign phone">휴대폰인증</span></a></li>
								<li class="sign-pass"><a href="javascript:idpwCheck(4); void(0);">
									<span class="sign-k"><em class="blind">카카오톡</em></span>
									<span class="sign-pc"><em class="blind">PAYCO</em></span>
									<span class="sign-ps"><em class="blind">PASS</em></span>
									<span class="sign-n"><em class="blind">NAVER</em></span>
									<span class="sign-sh"><em class="blind">신한은행</em></span>
									<span class="sign-kb"><em class="blind">국민은행</em></span>
									<span class="txt-sign">간편 인증</span>
								</a></li>
							</ul>
						</div>
					</div>
					<div class="section-cs">
						<article class="cs-notice">
							<h3 class="tit-sub">
								공지사항
								<!-- <a href="javascript:window.open('${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/loginNotice.do', 'loginNotice', 'width=900, height=650'); void(0);" class="more"><span class="blind">더보기</span></a> -->
								<a href="javascript:popupCenter('${pageContext.request.contextPath}/isry/itgcm/sysmgmt/userlogin/loginNotice.do', '공지사항 전체보기', 1200, 650); void(0);" class="more"><span class="blind">더보기</span></a>
							</h3>
							<ul id="loginNotice">
								<!--
								<li>
									<a href="javascript:;">
										<span class="noti-flag">안내</span>
										<span class="noti-tit ellipsis">2023년 청소년매체환경보호센터 운영사업 수행기관 재공모</span>
										<span class="noti-date">2022-03-29</span>
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<span class="noti-flag">안내</span>
										<span class="noti-tit ellipsis">2023년 청소년방과후아카데미 운영지원단 운영 사업 수행기관 선정결과 공고  운영 사업 수행기관 선정결과 공고</span>
										<span class="noti-date">2022-03-29</span>
									</a>
								</li>

								<li>
									<a href="javascript:;">
										<span class="noti-flag">안내</span>
										<span class="noti-tit ellipsis">공지사항은 누구나 알 수있도록 작성합니다.</span>
										<span class="noti-date">2022-03-29</span>
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<span class="noti-flag">안내</span>
										<span class="noti-tit ellipsis">등록테스트입니다.</span>
										<span class="noti-date">2022-03-29</span>
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<span class="noti-flag">안내</span>
										<span class="noti-tit ellipsis">시스템운영기간 안내 2022년12월7일부터 2022년 12월 23일까지</span>
										<span class="noti-date">2022-03-29</span>
									</a>
								</li>
								-->
							</ul>
						</article>
						<article class="cs-help">
							<h3 class="tit-sub">한국청소년상담복지개발원 콜센터</h3>
							<p class="help-call">051-662-3219~3220</p>
							<p class="call-name">지자체, 청소년상담복지센터, 내일이룸학교, 경찰청</p>
							<p class="help-call">051-662-3217</p>
							<p class="call-name">학교밖청소년지원센터, 교육청</p>
							<p class="help-call">051-662-3218</p>
                            <p class="call-name">청소년쉼터, 청소년자립지원관, 청소년회복지원시설</p>
							<!-- <p class="help-mail"><a href="mailto:cyber1388@kyci.or.kr" class="help_mail">cyber1388@kyci.or.kr</a></p> -->
							<a href="./html/setup_page.html" class="login-btn btn-install"><span>프로그램 설치안내</span></a>
						</article>
					</div>
				</div>
				
                <!-- 20230509 수정 start -->
                <!-- 이미지 수정 : \images\isry\itgcms\login2\logo_footer.png -->
                <div class="login-footer">
                  <div class="login-footer-wrap">
                    <div class="logo-area">
                      <p class="logo-footer"><span class="blind">청소년안전망시스템</span></p>
                    </div>
                    <div class="addr-area">
                      <div class="addr-mogef">
                        <span>(03171) 서울특별시 종로구 세종대로 209(세종로)</span>
                        <span>여성가족부</span>
                      </div>
                      <div class="addr-kyci">
                        <span>(48058) 부산광역시 해운대구 센텀중앙로 79 센텀사이언스파크(우동)</span>
                        <span>한국청소년상담복지개발원</span>
                      </div>
                      <div class="copyright-area">
                        <span class="ft-copy">Copyright 2023. 여성가족부 all rights reserved. (해상도는 1920 X 1080으로 최적화 되어 있습니다.)</span>
                      </div>
                    </div>
                    <div class="terms-area">
                      <a href="#" class="login-btn btn-pv" onclick="layerPopup.open('terms-pop'); void(0);"><span>개인정보처리방침</span></a>
                    </div>
                  </div>
                </div>
                <!--// 20230509 수정 end -->
                
            </div>
			<!-- e: container -->
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
              <input type="radio" id="find-id-B" name="find-id-method" value="phone" aria-hidden="true" onclick="$('#find-id-value').val(''); $('#find-id-value').attr('placeholder', '휴대전화 번호');" />
              <label class="rdo-custom" role="radio" for="find-id-B">휴대전화</label>

              <input type="radio" id="find-id-C" name="find-id-method" value="email" checked aria-hidden="true" onclick="$('#find-id-value').val(''); $('#find-id-value').attr('placeholder', '이메일 주소');" />
              <label class="rdo-custom" role="radio" for="find-id-C">이메일</label>
              
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
        <p class="para">임시비밀번호 발급을 위하여 등록하신 아이디와 성명, 
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
              <input type="radio" id="find-pw-D" name="find-pw-method" value="phone" onclick="$('#find-pw-value').val(''); $('#find-pw-value').attr('placeholder', '휴대전화 번호');" />
              <label class="rdo-custom" for="find-pw-D">휴대전화</label>
              <input type="radio" id="find-pw-E" name="find-pw-method" value="email" checked onclick="$('#find-pw-value').val(''); $('#find-pw-value').attr('placeholder', '이메일 주소');" />
              <label class="rdo-custom" for="find-pw-E">이메일</label>
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
        <h1 class="pop-tit" style="padding-top: 10px;">인증서 관리</h1>

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
              <input type="text" class="birthday_inp inp-txt" name="userBirthday1" id="userBirthday1" placeholder="생년월일 (예: 19000101)" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');" maxlength="8" />
            </div>
            <div class="financial_area">
              <ul class="list">
                <li class="apply"><a href="javascript:doSignData(5); void(0);" title="금융인증서 (재)등록, 새창">금융인증서 (재)등록</a></li>
                <!-- <li class="delete"><a href="javascript:doSignData(6); void(0);" title="금융인증서 삭제, 새창">금융인증서 삭제</a></li> -->
              </ul>
            </div>
            
            
            <div class="para">
              입력하신 생년월일이 회원정보의 생년월일과 일치해야 합니다.
              <!-- <button type="button" class="btn_note">인증서 로그인 안내</button> -->
            </div>
            
          </div>

          <div class="tab-panel certificate_panel" role="tabpanel" id="tab-panel2" aria-labelledby="tab-list2">
            <div class="inp_box">
              <input type="text" class="id_inp inp-txt" name="userId2" id="userId2" placeholder="로그인 아이디" />
              <input type="text" class="birthday_inp inp-txt" name="userBirthday2" id="userBirthday2" placeholder="생년월일 (예: 19000101)" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');" maxlength="8" />
            </div>
            <div class="financial_area">
              <ul class="list">
                <li class="apply"><a href="javascript:doSignData(2); void(0);" title="공동인증서 (재)등록, 새창">공동인증서 (재)등록</a></li>
                <!-- <li class="delete"><a href="javascript:doSignData(3); void(0);" title="공동인증서 삭제, 새창">공동인증서 삭제</a></li> -->
              </ul>
            </div>
            
            
            <div class="para">
            입력하신 생년월일이 회원정보의 생년월일과 일치해야 합니다.
              <!-- <button type="button" class="btn_note">인증서 로그인 안내</button> -->
            </div>
            
          </div>

          <div class="tab-panel phone_panel" role="tabpanel" id="tab-panel3" aria-labelledby="tab-list3">
            <div class="inp_box">
              <input type="text" class="id_inp inp-txt" name="userId3" id="userId3" placeholder="로그인 아이디" />
            </div>
            <div class="phone_area">
              <ul class="list">
                <li class="apply"><a href="javascript:openDRMOKWindow(1); void(0);" title="휴대폰 인증 (재)등록, 새창">휴대폰 인증 (재)등록</a></li>
                <!-- <li class="delete"><a href="javascript:openDRMOKWindow(2); void(0);" title="휴대폰 인증 삭제, 새창">휴대폰 인증 삭제</a></li> -->
              </ul>
            </div>
            
            
            <div class="para">
            휴대폰 인증에서 넘겨지는 생년월일이 회원정보의 생년월일과 일치해야 합니다.
              <!-- <button type="button" class="btn_note">&nbsp;</button> -->
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
                <li class="apply"><a href="javascript:init_popup_auth(2); void(0);" title="간편 인증 (재)등록, 새창">간편 인증 (재)등록</a></li>
                <!-- <li class="delete"><a href="javascript:init_popup_auth(3); void(0);" title="간편 인증 삭제, 새창">간편 인증 삭제</a></li> -->
              </ul>
            </div>
            
            
            <div class="para">
            간편 인증에서 넘겨지는 생년월일이 회원정보의 생년월일과 일치해야 합니다.
              <!-- <button type="button" class="btn_note">&nbsp;</button> -->
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

      <div class="head" style="position: fixed; width: 1000px; max-height: 52px; 
              left: 50%;
              top: 4%; z-index: 99999;
              transform: translate(-50%);">
        <button class="btn_close js-btn-close" title="팝업 닫기" style="float: right;"></button>
      </div>
      
    <div class="layer-wrap">
      <!--
      <div class="head">
        <button class="btn_close js-btn-close" title="팝업 닫기"></button>
      </div>
      -->
      <div class="body">
        <h1 class="pop-tit" style="margin-top: 35px;">개인정보처리방침</h1>
        
        <h3 class="term_tit" style="margin-top: 30px;">위기청소년 통합지원정보시스템 개인정보처리방침</h3>
        <!--
        <div style="text-align: right; margin-right: 65px; margin-top: 10px;">
            <b class="point_color">시행일 2022년 9월 1일</b>
        </div>
        -->
        
        <div class="notice_area" style="margin-top: 10px">
          <p class="para" style="margin-top: 5px"> &nbsp;
                        여성가족부·한국청소년상담복지개발원의 위기청소년 통합지원정보시스템(이하 청소년안전망시스템)은 
          &#12300;개인정보 보호법&#12301; 제30조에 따라 정보주체의 개인정보를 보호하고 
                        이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 다음과 같이 개인정보처리방침을 수립·공개합니다.</p>
        </div>
        
        <div class="temrs_type">
          <h3 class="term_tit">주요 개인정보 처리 표시(라벨링)</h3>

          <div class="list_wrap">
            <div class="item" title="">
              <dl >
                <dt style="padding-bottom: 32px;">
                <span class="txt" style="padding-bottom: 30px;">개인정보 수집항목</span>
                
                <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image01_1.png" alt="">
                <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image01_2.png" alt="">
                <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image01_3.png" alt="">
                </dt>
                <dd>
                  <div class="inner">
                    <div class="label_cont">
                      <ul class="list circle">
                        <li>성명, 성별, 생년월일, 연락처, 건강정보, 자격정보, 학력정보 등</li>
                      </ul>
                    </div>
                  </div>
                </dd>
              </dl>
            </div>
            
            <div class="item" title="">
              <dl>
                <dt>
                  <span class="txt">개인정보 보유기간</span>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image02.png" alt="">
                </dt>
                <dd>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>청소년안전망시스템 이용 종사자 정보 : 시스템 사용종료 후 5년</li>
                      <li>통합사례관리 정보 : 만24세까지</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </div>

            <div class="item" title="">
              <dl>
                <dt>
                  <span class="txt">개인정보의 처리 목적</span>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image03.png" alt="">
                </dt>
                <dd>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>통합지원체계의 효율적 운영</li>
                      <li>위기청소년 통합사례관리</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </div>
            <div class="item" title="">
              <dl>
                <dt style="padding-bottom: 0px;">
                  <span class="txt">개인정보의 위탁</span>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image04.png" alt="">
                </dt>
                <dd>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>시스템 구축 : 메타빌드</li>
                      <li>본인인증을 위한 개인정보 처리 : 드림시큐리티</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </div>
            <div class="item" title="">
              <dl>
                <dt>
                  <span class="txt">개인정보의 제3자 제공</span>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image05.png" alt="">
                </dt>
                <dd>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>보건복지부 : 의뢰자 정보확인 및 담당자 정보확인</li>
                      <li>병무청 : 담당자 정보확인</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </div>
            <div class="item" title="">
              <dl>
                <dt>
                  <span class="txt">고충사항 처리 부서</span>
                  <img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/image06.png" alt="">
                </dt>
                <dd>
                  <div class="label_cont">
                    <ul class="list circle">
                      <li>개인정보보호팀<br/>051-662-3123, 3102</li>
                    </ul>
                  </div>
                </dd>
              </dl>
            </div>
          </div>
        </div>          
        <p class="body_note">※ 자세한 내용은 아래의 개인정보처리방침 본문을 확인해주시기 바랍니다.</p>

        <hr class="term_line">
      </div>
      
      <div class="terms_cont">
        <h3 class="term_tit">목차</h3>
        
        <table>
        <colgroup>
          <col width="50px">
          <col width="">
          <col width="50px">
          <col width="">
        </colgroup>
        <tbody>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item01.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no1">제1조 (개인정보의 처리목적)</a></td>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item02.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no2">제2조 (개인정보 처리 및 보유기간)</a></td>
          </tr>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item03.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no3">제3조 (개인정보 영향평가 수행 결과)</a></td>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item04.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no4">제4조 (개인정보의 제3자 제공)</a></td>
          </tr>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item05.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no5">제5조 (개인정보 처리의 위탁)</a></td>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item06.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no6">제6조 (개인정보의 파기)</a></td>
          </tr>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item07.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no7">제7조 (정보주체와 법정대리인의 권리·의무 및 행사방법)</a></td>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item08.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no8">제8조 (개인정보의 안전성 확보 조치)</a></td>
          </tr>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item09.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no9">제9조 (개인정보 자동 수집 장치의 설치·운영 및 거부에 관한 사항)</a></td>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item10.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no10">제10조 (개인정보 보호책임자)</a></td>
          </tr>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item11.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no11">제11조 (개인정보 열람청구)</a></td>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item12.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no12">제12조 (권익침해 구제방법)</a></td>
          </tr>
          <tr>
            <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item13.png" alt="" width="45px" height="45px" /></td>
            <td><a href="#no13">제13조 (개인정보처리방침 변경)</a></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
        </table>
      </div>
      
      <div class="terms_cont">
        <section class="term_group" id="term-collector">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item01.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no1">제1조 (개인정보의 처리목적)</h1></td>
            </tr>
          </tbody>
          </table>
          
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 다음의 목적을 위하여 개인정보를 처리합니다.</li>
            <li>② 한국청소년상담복지개발원이 「개인정보 보호법」 제32조에 따라 등록·공개하는 개인정보파일의 처리목적은 다음과 같습니다.</li>
            <li>

                  <table class="term-tbl">
                    <caption>개인정보파일의 명칭, 운영근거/처리목적 으로 구성</caption>
                    <colgroup>
                      <col width="17%">
                      <col width="">
                    </colgroup>
                    <thead>
                      <tr>
                        <th scope="col">개인정보파일의 명칭</th>
                        <th scope="col">운영근거 / 처리목적</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>청소년안전망시스템 이용 종사자 정보</td>
                        <td>
                          <ul class="noBullet">
                            <li>근거 : 청소년복지지원법 제12조의2</li>
                            <li>목적 : 청소년안전망시스템 운영 및 사용자 관리</li>
                          </ul>                          
                        </td>
                      </tr>
                      <tr>
                        <td>통합사례관리 정보</td>
                        <td>
                          <ul class="noBullet">
                            <li>근거 : 청소년복지지원법 제12조의2</li>
                            <li>목적 : 위기청소년 통합사례관리, 사무처리, 신청진행상태, 예약정보알림 활용</li>
                          </ul>                          
                        </td>
                      </tr>
                    </tbody>
                  </table>             

            </li>
          </ul>
        </section>

        <section class="term_group" id="term-use">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item02.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no2">제2조 (개인정보의 처리 및 보유기간)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터의 개인정보를 수집 시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.</li>
            <li>② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.</li>
            
            <li>
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="110px">
                  <col >
                  <col width="180px">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">개인정보파일의 명칭</th>
                    <th scope="col">개인정보파일에 기록되는 개인정보의 항목</th>
                    <th scope="col">보유기간</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>청소년안전망시스템 이용 종사자 정보</td>
                    <td>성명, 성별, 생년월일, 연락처, 근무이력, 자격, 학력정보</td>
                    <td>시스템 사용종료일로부터 5년</td>
                  </tr>
                  <tr>
                    <td>통합사례관리 정보</td>
                    <td>성명, 사진, 성별, 생년월일, 연락처, 주소, 주거, 사회보장, 가족, 학력, 취업, 자격정보</td>
                    <td>만24세까지<br/>(잔여연령의 기간이 10년 미만인 경우는 서비스 지원 종료일로부터 10년)</td>
                  </tr>
                </tbody>
              </table>

            </li>
          </ul>
        </section>
        <section class="term_group" id="term-manage">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item03.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no3">제3조 (개인정보 영향평가 수행 결과)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 운영하고 있는 개인정보 처리시스템이 정보주체의 개인정보파일에 미칠 영향에 대해 조사, 분석, 평가하기 위해 「개인정보 보호법」 제33조에 따라 “개인정보 영향평가”를 받고 있습니다.</li>
            <li>② 한국청소년상담복지개발원은 다음 개인정보파일에 대해 영향평가를 수행하였습니다.</li>
            
            <li>
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="150px">
                  <col >
                  <col width="150px">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">개인정보파일의 명칭</th>
                    <th scope="col">개인정보파일에 기록되는 개인정보의 항목</th>
                    <th scope="col">영향평가 수행연도</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>통합사례관리 정보</td>
                    <td>사례정보, 성명, 성별, 생년월일, 학력정보 등</td>
                    <td>2022</td>
                  </tr>
                </tbody>
              </table>

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item04.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no4">제4조 (개인정보의 제3자 제공)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 개인정보를 제1조(개인정보의 처리목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 「개인정보 보호법」 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</li>
            <li>② 한국청소년상담복지개발원은 원활한 서비스 제공을 위해 다음의 경우 정보주체의 동의를 얻어 필요 최소한의 개인정보를 제3자에게 제공하고 있습니다.</li>
            
            <li>
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="90px">
                  <col >
                  <col width="160px">
                  <col width="180px">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">제공받는 자</th>
                    <th scope="col">제공목적</th>
                    <th scope="col">제공항목</th>
                    <th scope="col">보유기간</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td rowspan="2">보건복지부</td>
                    <td>서비스 연계의뢰시 의뢰자 정보확인  </td>
                    <td>성명, 생년월일, 성별, 연락처, 주소</td>
                    <td>만24세까지<br/>(잔여연령의 기간이 10년 미만인 경우는 서비스 지원 종료일로부터 10년)</td>
                  </tr>
                  <tr>
                    <td>서비스 연계의뢰시 담당자 정보확인</td>
                    <td>성명, 소속기간, 연락처</td>
                    <td>시스템사용 종료일로부터 5년</td>
                  </tr>
                  <tr>
                    <td>병무청</td>
                    <td>서비스 연계의뢰시 담당자 정보확인</td>
                    <td>성명, 소속기간, 연락처</td>
                    <td>시스템사용 종료일로부터 5년</td>
                  </tr>
                </tbody>
              </table>

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item05.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no5">제5조 (개인정보 처리의 위탁)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 여성가족부의 개인정보 처리업무를 다음과 같이 위탁받아 처리하고 있습니다.
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="90px">
                  <col >
                  <col >
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">위탁기관</th>
                    <th scope="col">위탁받는 기관(수탁기관)</th>
                    <th scope="col">위탁업무</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>여성가족부</td>
                    <td>한국청소년상담복지개발원</td>
                    <td>청소년안전망시스템 운영 및 유지관리</td>
                  </tr>
                </tbody>
              </table>
            
            </li>
            <li>② 한국청소년상담복지개발원은 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="90px">
                  <col >
                  <col >
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">위탁기관</th>
                    <th scope="col">위탁받는 자(수탁자)</th>
                    <th scope="col">위탁업무</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td rowspan="2">한국청소년상담복지개발원</td>
                    <td>㈜메타빌드</td>
                    <td>청소년안전망시스템 구축</td>
                  </tr>
                  <tr>
                    <td>㈜드림시큐리티</td>
                    <td>청소년안전망시스템 본인확인 서비스</td>
                  </tr>
                </tbody>
              </table>
            
            </li>
            
            <li>③ 여성가족부·한국청소년상담복지개발원은 위탁계약 체결시 「개인정보 보호법」 제26조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적·관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.

            </li>
            <li>④ 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보처리방침을 통하여 공개하도록 하겠습니다.

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item06.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no6">제6조 (개인정보의 파기)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.
            
            </li>
            <li>② 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보(또는 개인정보파일)를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.
            
            </li>
            
            <li>③ 개인정보 파기의 절차 및 방법은 다음과 같습니다.
              <ol class="list2 hangul">
                <li>파기절차 : 한국청소년상담복지개발원은 파기하여야 하는 개인정보(또는 개인정보파일)에 대해 개인정보 파기계획을 수립하여 파기합니다. 한국청소년상담복지개발원은 파기 사유가 발생한 개인정보(또는 개인정보파일)을 선정하고, 한국청소년상담복지개발원의 개인정보 보호책임자의 승인을 받아 개인정보(또는 개인정보파일)를 파기합니다.</li>
                <li>파기방법 : 한국청소년상담복지개발원은 전자적 파일 형태로 기록·저장된 개인정보는 기록을 재생할 수 없도록 파기하며, 종이 문서에 기록·저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.</li>
              </ol>
            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item07.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no7">제7조 (정보주체와 법정대리인의 권리·의무 및 그 행사방법)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 정보주체는 한국청소년상담복지개발원에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다. 만 14세 미만 아동의 법정대리인은 그 아동의 개인정보에 대한 열람, 정정, 삭제, 처리정지를 요구할 수 있으며, 만 14세 이상의 미성년자인 정보주체는 정보주체의 개인정보에 관하여 미성년자 본인이 권리를 행사하거나 법정대리인을 통하여 권리를 행사할 수도 있습니다.
            
            </li>
            <li>② 제1항에 따른 권리 행사는 한국청소년상담복지개발원에 대해 「개인정보 보호법」 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며, 한국청소년상담복지개발원은 이에 대해 지체 없이 조치하겠습니다.

            </li>
            <li>③ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수도 있습니다. 이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.

            </li>
            <li>④ 개인정보 열람 및 처리정지 요구는 「개인정보 보호법」 제35조제4항, 제37조제2항에 의하여 정보주체의 권리가 제한될 수 있습니다.

            </li>
            <li>⑤ 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.

            </li>
            <li>⑥ 한국청소년상담복지개발원은 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item08.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no8">제8조 (개인정보의 안전성 확보 조치)</h1></td>
            </tr>
          </tbody>
          </table>
          
          <p class="star_para">여성가족부·한국청소년상담복지개발원은 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.</p>
          
          <ul class="list noBullet">
            <li>① 관리적 조치 : 개인정보의 안전한 처리를 위한 내부관리계획 수립·시행, 개인정보취급자 지정의 최소화 및 정기적인 직원 개인정보보안 교육 등
            
            </li>
            <li>② 기술적 조치 : 개인정보처리시스템 등의 개인정보 접근권한 관리, 개인정보의 암호화, 보안프로그램 사용, 접속기록의 보관 및 위변조 방지

            </li>
            <li>③ 물리적 조치 : 전산실, 자료보관실 등 비인가자에 대한 출입 통제

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item09.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no9">제9조 (개인정보 자동 수집 장치의 설치·운영 및 그 거부에 관한 사항)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용합니다.
            
            </li>
            <li>② 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자들의 PC 컴퓨터내의 하드디스크에 저장되기도 합니다.
              <ol class="list2 hangul">
                <li>쿠키의 사용 목적 : 이용자가 방문한 각 서비스와 웹 사이트들에 대한 방문 및 이용형태, 인기 검색어, 보안접속 여부, 등을 파악하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.</li>
                <li>쿠키의 설치·운영 및 거부 : 웹브라우저 상단의 도구>인터넷 옵션>개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부 할 수 있습니다.</li>
                <li>쿠키 저장을 거부할 경우 맞춤형 서비스 이용에 어려움이 발생할 수 있습니다.</li>
              </ol>

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item10.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no10">제10조 (개인정보 보호책임자)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 한국청소년상담복지개발원은 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="20%">
                  <col width="20%">
                  <col width="20%">
                  <col width="20%">
                  <col width="20%">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">구분</th>
                    <th scope="col">직책</th>
                    <th scope="col">성명</th>
                    <th scope="col">연락처</th>
                    <th scope="col">이메일</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>개인정보 보호책임자</td>
                    <td>통합지원본부장</td>
                    <td>양미진</td>
                    <td rowspan="3">051-662-3123<br/>051-662-3102</td>
                    <td>yangmijin@kyci.or.kr</td>
                  </tr>
                  <tr>
                    <td>개인정보 보호담당자</td>
                    <td>개인정보보호팀 팀장</td>
                    <td>남용수</td>
                    <td>ynam@kyci.or.kr</td>
                  </tr>
                  <tr>
                    <td>개인정보 보호담당자</td>
                    <td>개인정보보호팀 팀원</td>
                    <td>심지원</td>
                    <td>sjw0112@kyci.or.kr</td>
                  </tr>
                </tbody>
              </table>
            
            </li>
            <li>② 정보주체는 한국청소년상담복지개발원의 서비스(또는 사업)를 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 한국청소년상담복지개발원은 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.

            </li>
          </ul>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item11.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no11">제11조 (개인정보의 열람청구)</h1></td>
            </tr>
          </tbody>
          </table>

          <p class="star_para">정보주체는 ｢개인정보 보호법｣ 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다. 한국청소년상담복지개발원은 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.</p>
        
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="20%">
                  <col width="20%">
                  <col width="20%">
                  <col width="20%">
                  <col width="20%">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">구분</th>
                    <th scope="col">부서명</th>
                    <th scope="col">성명</th>
                    <th scope="col">연락처</th>
                    <th scope="col">이메일</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>개인정보 보호담당자</td>
                    <td>개인정보보호팀 팀장</td>
                    <td>남용수</td>
                    <td>051-662-3123</td>
                    <td>ynam@kyci.or.kr</td>
                  </tr>
                  <tr>
                    <td>개인정보 보호담당자</td>
                    <td>개인정보보호팀 팀원</td>
                    <td>심지원</td>
                    <td>051-662-3102</td>
                    <td>sjw0112@kyci.or.kr</td>
                  </tr>
                </tbody>
              </table>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item12.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no12">제12조 (권익침해 구제방법)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.
            
            </li>
            <li>② 아래의 기관은 한국청소년상담복지개발원과는 별개의 기관으로서, 한국청소년상담복지개발원의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다.
              <table class="term-tbl">
                <caption>제공받는 자, 목적, 항목, 보유 및 이용기간, 관련 근거의 항목</caption>
                <colgroup>
                  <col width="33%">
                  <col width="33%">
                  <col >
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">기관명</th>
                    <th scope="col">홈페이지</th>
                    <th scope="col">대표번호</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>개인정보 침해신고센터</td>
                    <td><a href="http://privacy.kisa.or.kr" target="_blank">privacy.kisa.or.kr</a></td>
                    <td>(국번없이) 118</td>
                  </tr>
                  <tr>
                    <td>개인정보 분쟁조정위원회</td>
                    <td><a href="http://www.kopico.go.kr" target="_blank">www.kopico.go.kr</a></td>
                    <td>(국번없이) 1833-6972</td>
                  </tr>
                  <tr>
                    <td>대검찰청</td>
                    <td><a href="http://www.spo.go.kr" target="_blank">www.spo.go.kr</a></td>
                    <td>(국번없이) 1301</td>
                  </tr>
                  <tr>
                    <td>경찰청</td>
                    <td><a href="http://ecrm.cyber.go.kr" target="_blank">ecrm.cyber.go.kr</a></td>
                    <td>(국번없이) 182</td>
                  </tr>
                </tbody>
              </table>
            
            </li>
          </ul>
          
          <p class="star_para">※ 행정심판에 대해 자세한 사항은 중앙행정심판위원회 온라인행정심판(www.simpan.go.kr)의 홈페이지나 대표번호 (국번없이)110을 참고하시기 바랍니다.</p>

        </section>
        <section class="term_group">
        
          <table>
          <colgroup>
            <col width="50px">
            <col width="">
          </colgroup>
          <tbody>
            <tr>
              <td><img src="${pageContext.request.contextPath}/images/isry/itgcms/login3/item13.png" alt="" width="45px" height="45px" /></td>
              <td><h1 class="cont_tit" id="no13">제13조 (개인정보처리방침 변경)</h1></td>
            </tr>
          </tbody>
          </table>
        
          <ul class="list noBullet">
            <li>① 이 개인정보처리방침은 2023년 5월 8일부터 적용됩니다.
            </li>
            <li>② 이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다.<br/>
                                        
              <p class="star_para">※ 수정이력사항</p>
              <ul class="sub_list circle">
                <li><a href="/html/privacy/privacy_policy_20220901.html" target="_blank">청소년안전망시스템 개인정보처리방침(2022년 8월 31일) [새창]</a></li>
              </ul>
             
            </li>
          </ul>

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
    <input type="hidden" id='formUserId' name='formUserId' value="" />
    <input type="hidden" id='formUserPw' name='formUserPw' value="" />
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
