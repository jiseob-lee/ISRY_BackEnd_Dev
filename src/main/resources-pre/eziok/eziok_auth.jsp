<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.io.*" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Base64.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dreamsecurity.eziok.eziokKeyManager" %>
<%@ page import="com.dreamsecurity.eziok.EziokException" %>
<%@ page import="com.dreamsecurity.json.JSONObject" %>
<%@ page import="com.dreamsecurity.json.JSONException" %>

<%!
    public String eziok_std_request(eziokKeyManager eziok, HttpSession session) {
    
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            Base64.Decoder decoder = Base64.getDecoder();

            /* 2. 간편인증 거래정보 생성 */
            // - 요청 생성 시간 + "|" + 거래ID(유일한 거래정보)

            // 2.1. 간편인증 요청시간 생성
            // - 간편인증 요청정보 생성날짜 5분이 초과한 경우 거래정보 유효시간 오류 발생
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String regDate = format.format(new Date());

            // 2.2 클라이언트 거래ID 생성(거래 고유 정보로 이용기관별 중복되지않는 유일한(unique) 값으로 설정)
            // - 간편인증 거래ID 는 유일한 값이어야 하며 기 사용한 거래ID가 있는 경우 오류 발생 (가맹점 Prefix + UUID 사용 등 권고)
            String clientTxId = UUID.randomUUID().toString();
            clientTxId = "EZIOK" + clientTxId.replaceAll("-", "");

            // 2.3 클라이언트 ID 세션 저장 (권고, 동일한 세션내 요청과 결과가 동일한지 확인을 통해 재사용 방지처리, "hubtoken" 이용시 필수 구현)
            session.setAttribute("sessionClientTxId", clientTxId);

            // 2.4. 간편인증 거래정보 생성
            // - 간편인증 인증요청 정보 생성날짜 5분이 초과한 경우 거래정보 유효시간 오류 발생
            clientTxId = regDate + "|" + clientTxId;

            /* 3. 간편인증 거래정보 암호화 */
            //3.1 비밀키 획득
            String encClientTxId = eziok.RSAEncrypt(clientTxId);

            // 4. 간편 인증 인증요청 정보 생성
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("serviceId", eziok.getServiceId()); // 간편인증 이용기관 서비스 ID
            jsonObject.put("encClientTxId", encClientTxId); // 암호화된 거래정보
            jsonObject.put("serviceType", "auth"); // 간편인증 : auth, 전자서명 : sign
            // jsonObject.put("plainData", "전자서명 원문 데이터 입력 TEST123!@#$"); // 전자서명시 원문 입력, { serviceType : "sign" } 일 경우 필수 입력
            jsonObject.put("retTransferType", "keytoken"); // 간편인증 결과 타입, "keytoken" : 개인정보 응답결과를 이용기관 서버에서 간편인증서버에 요청하여 수신 후 처리
            //jsonObject.put("retTransferType", "hubtoken"); // 간편인증 결과 타입, "hubtoken" : 개인정보 응답결과를 이용자 브라우져로 수신 후 처리 (이용시 반드시 재사용 방지처리 개발)
            
            //jsonObject.put("resultUrl", "https://gov.youthsafety.go.kr/eziok/eziok_std_result.jsp"); // 결과 수신 후 전달 URL 설정
            if ("ryewas11".equals(System.getProperty("SERVER")) || "ryewas21".equals(System.getProperty("SERVER"))) {
            	jsonObject.put("resultUrl", "https://pre.youthsafety.go.kr/eziok/eziok_std_result.jsp"); // 결과 수신 후 전달 URL 설정
            } else {
            	jsonObject.put("resultUrl", "https://gov.youthsafety.go.kr/eziok/eziok_std_result.jsp"); // 결과 수신 후 전달 URL 설정
            }
            
            jsonObject.put("retType", "callback"); // 콜백함수 사용 : "callback" , 모바일 redirect 사용(모바일 WebView 또는 iOS 이용시) : "redirect"

            // 5. 간편인증 인증요청 JSON 결과 리턴
            return jsonObject.toString();
            
        } catch (EziokException e) {
            return e.getErrorCode()+"|";
        }
    }
%>

<%
    /* 1. 간편인증 비밀키 및 인증기관 비밀키 설정 */
    try {
        eziokKeyManager eziok = new eziokKeyManager();
        eziok.keyInit("/app/ISRY_BackEnd.war/WEB-INF/mok/eziok/eziok_keyInfo.dat", "dream");
        session.setAttribute("actSimple", "processing");
        out.write(eziok_std_request(eziok, session));
    } catch (EziokException e) {
        out.write(e.getErrorCode() + ":" + e.getMessage());
    }
%>
