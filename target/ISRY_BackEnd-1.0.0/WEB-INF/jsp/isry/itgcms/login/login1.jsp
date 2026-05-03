<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      
      console.log("num", num);
      
      var signData = $("#signData").val();
      
      if (signData.length < 1) {
          alert('폼 데이터를 입력하세요.');
          $("#signData").focus();
          return;
      }
      
      var ipbUserNm = $("#userId").val();
      
      
      if (num == 1) {  // 인증서 인증(로그인)
          
          document.reqForm.signType.value = "2";
          
      } else if (num == 2) {  // 인증서 (재)등록
          
          document.reqForm.signType.value = "1";
          document.reqForm.loginId.value = ipbUserNm;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 (재)등록을 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          
      } else if (num == 3) {  // 인증서 삭제
          
          document.reqForm.signType.value = "3";
          document.reqForm.loginId.value = ipbUserNm;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 삭제를 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          
      } else if (num == 4) {  // 금융 인증서 인증(로그인)
          
          document.reqForm.signType.value = "5";
          
      } else if (num == 5) {  // 금융 인증서 (재)등록
          
          document.reqForm.signType.value = "4";
          document.reqForm.loginId.value = ipbUserNm;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 (재)등록을 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          
      } else if (num == 6) {  // 금융 인증서 삭제
          
          document.reqForm.signType.value = "6";
          document.reqForm.loginId.value = ipbUserNm;
          
          if (document.reqForm.loginId.value == null || document.reqForm.loginId.value == "") {
              alert("인증서 삭제를 위하여 아이디를 입력해주시기 바랍니다.");
              return;
          }
          
      }

      //alert("loginId : " + document.reqForm.loginId.value);
      //console.log("num", num);
      
      if (num == 1 || num == 2 || num == 3) {
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
        data : {userId : $("#userId").val(), userPw : $("#userPw").val()},
        method : "POST",
        dataType : "json"
    })
    .done(function(json) {

    	console.log("json", json);
    	
    	var msg = json["msg"];
    	
        if (msg != null && msg != "2") {
            
            alert(msg);
            return;
        
        } else if (msg == "2") {  // 로컬 및 개발서버
            
            location.href = "${pageContext.request.contextPath}/";
        
        } else {
            console.log("인증서 로그인을 진행해 주시기 바랍니다.");
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

$(document).ready(function() {

	var vsCookieId = getCookie("expuid");
    //var dsParam = app.lookup("dsParam");
    //console.log("vsCookieId", vsCookieId);
 
	if (vsCookieId != null && vsCookieId != "") {
		$("#cbxRmbr").prop("checked", true);
		$("#userId").val(vsCookieId);
		$("#userPw").focus();
	} else {
		$("#userId").focus();
	}

});

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
                  <input type="text" class="id_inp" name="userId" id="userId" placeholder="아이디" onkeydown="if (window.event.keyCode == 13) { login(0); }" />
                </div>
                <div class="inp_box">
                  <input type="password" class="pw_inp" name="userPw" id="userPw" placeholder="비밀번호" onkeydown="if (window.event.keyCode == 13) { login(0); }" />
                </div>
              </div>

              <div class="save_id">
                <input type="checkbox" name="cbxRmbr" id="cbxRmbr">
                <label for="cbxRmbr"> 아이디 저장 </label>
              </div>

              <div class="pass_simple_box">
                <ul class="list">
                  <li class="financial_pass">
                    <a href="javascript:certLogin(1); void(0);" title="금융인증서 로그인하기">
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
                    <a href="#" title="휴대폰 인증 로그인하기">
                      <img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_pass03.svg" alt="">
                      <span class="txt">휴대폰 인증</span>
                    </a>
                  </li>
                  <li class="simple_pass">
                    <a href="#" title="간편 인증 로그인하기">
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
                    <a href="#">아이디찾기</a>
                  </li>
                  <li>
                    <a href="#">비밀번호찾기</a>
                  </li>
                  <li>
                    <a href="#">회원가입</a>
                  </li>
                  <li>
                    <a href="#">인증서관리</a>
                  </li>
                </ul>
              </div>
            </div>

            <div id="board" class="help_wrap">
              <div class="head">
                <h2 class="sec-tit">공지사항</h2>
                <a href="#" class="link_more" title="공지사항 전체보기로 이동"></a>
              </div>
              <div class="body">
                <ul class="board_simple">
                  <li>
                    <span class="preface">안내</span>
                    <a href="#" class="tit" title="청소년안전망시스템 사용 안내 바로가기">청소년안전망시스템 사용 안내</a>
                    <time class="pubdate" datetime="2022-10-31">2022.10.31</time>
                  </li>
                  <li>
                    <span class="preface">안내</span>
                    <a href="#" class="tit" title="청소년안전망시스템 사용 안내 바로가기">청소년안전망시스템 사용 안내</a>
                    <time class="pubdate" datetime="2022-10-31">2022.10.31</time>
                  </li>
                  <li>
                    <span class="preface">안내</span>
                    <a href="#" class="tit" title="청소년안전망시스템 사용 안내 바로가기">청소년안전망시스템 사용 안사용 안사용 안내</a>
                    <time class="pubdate" datetime="2022-10-31">2022.10.31</time>
                  </li>
                  <li>
                    <span class="preface">안내</span>
                    <a href="#" class="tit" title="청소년안전망시스템 사용 안내 바로가기">청소년안전망시스템 사용 안내</a>
                    <time class="pubdate" datetime="2022-10-31">2022.10.31</time>
                  </li>
                  <li>
                    <span class="preface">안내</span>
                    <a href="#" class="tit" title="청소년안전망시스템 사용 안내 바로가기">청소년안전망시스템 사용 안내</a>
                    <time class="pubdate" datetime="2022-10-31">2022.10.31</time>
                  </li>
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
                  <a href="#" class="btn" title="프로그램 설치 안내 바로가기">프로그램 설치 안내</a>
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
          
          <div class="law"><a href="#" title="개인정보처리방침으로 이동">개인정보처리방침</a></div>
        </div>
        </footer>

    <form id='reqForm' name='reqForm' method='post' action="../MagicLine4Web/ML4WebProcess/signedFormR2.jsp" target="signedFormR">
    <!-- 결과 수신 메시지  -->
    <input type="hidden" id="signOrigin" name="signOrigin" /> <!-- 180701 서명 원문 폼 추가 -->
    <input type="hidden" id='sign' name='sign' />
    <input type="hidden" id='csCheckType' name='csCheckType' value="1" />
    <input type="hidden" id='signType' name='signType' />
    <input type="hidden" id='loginId' name='loginId' value="" />
    
    <input type="hidden" id='signData' name='signData' value="youthsafety" />
    </form>
    
<div id="selectCertContainer1" style="width:100%;margin-top:0; display:none;"></div>
<div id="startCs" style="width:100%;margin-top:0; display:none;"></div>

<div id="dscertContainer">
    <iframe name="dscert" id="dscert" name="dscert" src="" scrolling="no" width="100%" height="100%" frameborder="0" allowTransparency="true" style="position:fixed;z-index:100010;top:0px;left:0px;width:100%;height:100%;"></iframe>
</div>

<iframe name="signedFormR" id="signedFormR" style="height:0; width:0; border:0; border:none; visibility:hidden;"></iframe>

    </div>
  </div>


  <section class="js-pop-area" aria-hidden="true">
    <div class="popUp pass-pop" role="dialog">
      <div class="layer-wrap">
        <div class="head">
          <button class="btn_close js-btn-close" title="팝업 닫기"></button>
        </div>
        <div class="body">
          <h1 class="pop-tit">인증서 관리</h1>
  
          <ul class="tab-list" role="tablist">
            <li role="none">
              <a href="#" id="tab-list1" role="tab" aria-selected="true" aria-controls="tab-panel1" class="tab_btn active"><span>금융인증서</span></a>
            </li>
            <li role="none">
              <a href="#" id="tab-list2" role="tab" aria-controls="tab-panel2" class="tab_btn"><span>공동인증서</span></a>
            </li>
            <li role="none">
              <a href="#" id="tab-list3" role="tab" aria-controls="tab-panel3" class="tab_btn"><span>SMS인증</span></a>
            </li>
            <li role="none">
              <a href="#" id="tab-list4" role="tab" aria-controls="tab-panel4" class="tab_btn"><span>간편 인증</span></a>
            </li>
          </ul>
  
          <div class="panel_area">
            <div class="tab-panel financial_panel active" role="tabpanel" id="tab-panel1" aria-labelledby="tab-list1">
              <div class="inp_box">
                <input type="text" class="id_inp inp-txt" placeholder="로그인 아이디">
              </div>
              <div class="financial_area">
                <ul class="list">
                  <li class="apply"><a href="#" title="금융인증서 등록, 새창">금융인증서 등록</a></li>
                  <li class="delete"><a href="#" title="금융인증서 삭제, 새창">금융인증서 삭제</a></li>
                </ul>
              </div>
              
              <div class="para">
                <button type="button" class="btn_note">인증서 로그인 안내</button>
              </div>
            </div>
  
            <div class="tab-panel" role="tabpanel" id="tab-panel2" aria-labelledby="tab-list2">
              공동인증서
            </div>
            <div class="tab-panel" role="tabpanel" id="tab-panel3" aria-labelledby="tab-list3">
              SMS인증
            </div>
            <div class="tab-panel simple_panel" role="tabpanel" id="tab-panel4" aria-labelledby="tab-list4">
              <div class="sns_area">
                <ul class="list">
                  <li><a href="#" title="카카오 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_kktlk.svg" alt="talk" role="img"></a></li>
                  <li><a href="#" title="네이버 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_nvr.svg" alt="N" role="img"></a></li>
                  <li><a href="#" title="페이스북 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_mt.svg" alt="f" role="img"></a></li>
                  <li><a href="#" title="구글 인증 요청, 새창"><img src="${pageContext.request.contextPath}/images/isry/itgcms/login/icon_ggl.svg" alt="G" role="img"></a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="foot">
          <button class="btn js-btn-close">닫<span class="space"></span>기</button>
        </div>
      </div>
    </div>
  </section>

</body>

</html>