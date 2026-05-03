<%@ page contentType = "text/html;charset=utf-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.*,java.text.SimpleDateFormat" %>
<%@ page import = "java.security.SecureRandom"%>
<%@ page import = "java.net.URLEncoder"%>
<%@ page import = "com.dreamsecurity.crypt.*"%>
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
 
    
    // 3.3 암호화된 거래요청정보 URL 인코딩
    encReqInfo = URLEncoder.encode(encReqInfo);
    
    // 4. 휴대폰본인확인 요청정보 
    // 4.1 본인인증 결과수신 받을 회원사 URL 설정
    String rtn_url = "https://gov.youthsafety.go.kr/MOK/mok_webauth_result.jsp";      // 본인인증 결과수신 받을 URL
    
    if ("ryewas11".equals(System.getProperty("SERVER")) || "ryewas21".equals(System.getProperty("SERVER"))) {
    	rtn_url = "https://pre.youthsafety.go.kr/MOK/mok_webauth_result.jsp";      // 본인인증 결과수신 받을 URL
    }
    
    // 4.2 휴대폰본인확인 요청 URL 생성
    //     - https://휴대폰본인확인 URL?cpid=<회원사ID>&rtn_url=<회원사결과수신URL>&req_info=<암호화된 거래요청정보1>
    //     - 운영 휴대폰본인확인 URL : https://www.mobile-ok.com/popup/common/hscert.jsp
    String request_url = "https://www.mobile-ok.com/popup/common/hscert.jsp?cpid=" + cpId +"&rtn_url=" + rtn_url +"&req_info=" + encReqInfo;      // 본인인증 요청 URL
    
    // 5. 휴대폰본인확인 요청
    //    - function openDRMOKWindow 함수 실행
%>

<html>
<head>
<title>본인인증서비스 Sample 화면</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
<!--
   body,p,ol,ul,td
   {
   font-family: 굴림;
   font-size: 12px;
   }

   a:link { size:9px;color:#000000;text-decoration: none; line-height: 12px}
   a:visited { size:9px;color:#555555;text-decoration: none; line-height: 12px}
   a:hover { color:#ff9900;text-decoration: none; line-height: 12px}

   .style1 {
    color: #6b902a;
    font-weight: bold;
  }
  .style2 {
      color: #666666
  }
  .style3 {
    color: #3b5d00;
    font-weight: bold;
  }
-->
</style>


<script language=javascript>
  
    var DRMOK_window;
    function openDRMOKWindow(){
        window.name = 'sendJsp';
        DRMOK_window = window.open("<%=request_url%>", 'DRMOKWindow', 'width=425,height=550,scrollbars=no,toolbar=no,location=no,directories=no,status=no' );
        DRMOK_window.focus();
        if(DRMOK_window == null){
            alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
        }
    }
</script>


</head>

<body bgcolor="#FFFFFF" topmargin=0 leftmargin=0 marginheight=0 marginwidth=0>

<center>
<br><br><br><br><br><br>
<span class="style1">본인인증서비스 요청화면 Sample입니다.</span><br>
<br><br>
<table cellpadding=1 cellspacing=1>
    <tr>
        <td align=center>회원사ID</td>
        <td align=left><%=cpId%></td>
    </tr>
    <tr>
        <td align=center>URL코드</td>
        <td align=left><%=urlCode%></td>
    </tr>

    <tr>
        <td align=center>요청일시</td>
        <td align=left><%=reqdate%></td>
    </tr>

    <tr>
        <td align=center>&nbsp</td>
        <td align=left>&nbsp</td>
    </tr>

    <tr>
        <td align=center>결과수신URL</td>
        <td align=left><%=rtn_url%></td>
    </tr>
</table>

<!-- 본인인증서비스 요청 form --------------------------->
<form name="reqDRMOKForm" method="post" action="">
    <input type="hidden" name="req_info"      value = "<%=encReqInfo%>">
    <input type="hidden" name="rtn_url"       value = "<%=rtn_url%>">
    <input type="hidden" name="cpid"          value = "<%=cpId%>">
    <input type="hidden" name="newpop"        value = "Y">
    <input type="submit" value="본인인증서비스 요청"  onclick= "javascript:openDRMOKWindow();">
</form>
<BR>
<BR>
<!--End 본인인증서비스 요청 form ----------------------->

<br>
<br>
  이 Sample화면은 본인인증서비스 요청화면 개발시 참고가 되도록 제공하고 있는 화면입니다.<br>
<br>
</center>
</BODY>
</HTML>
