<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.io.*" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Base64.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="com.dreamsecurity.eziok.eziokKeyManager" %>
<%@ page import="com.dreamsecurity.eziok.EziokException" %>
<%@ page import="com.dreamsecurity.json.JSONObject" %>
<%@ page import="com.dreamsecurity.json.JSONException" %>


<%!

    BufferedReader bufferedReader = null;

    public String eziok_get_hubtoken(String keytoken, eziokKeyManager eziok, String sessionClientTxId) {

        // 간편인증 인증결과 keyToken API 요청 URL
            String targetUrl = "https://cert.ez-iok.com/agent/auth-verify";
        //  String targetUrl = "https://scert.ez-iok.com/agent/auth-verify";  // 개발
       
        // auth-verify로 keytoken 요청
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("keyToken", keytoken);
        String result = sendPost(targetUrl, jsonObject.toString());
        
        
        System.out.println("result 데이타 => " + result);
        
        if(result == null){
            return "-9|간편인증 keytoken 인증결과 응답이 없습니다.";
        }
        // 간편인증 인증결과 hubtoken 요청
        JSONObject resultObject = new JSONObject(result);
        String hubToken = resultObject.optString("hubToken", null);
        if (hubToken == null) {
            return "-1|간편인증 hubtoken 인증결과 응답이 없습니다";
        }
        
        System.out.println("hubToken 데이타 => " + hubToken);

        return eziok_std_result(hubToken, eziok, sessionClientTxId);
    }

    public String eziok_std_result(String hubToken, eziokKeyManager eziok, String sessionClientTxId) {
        String resultString;

        Encoder encoder = Base64.getEncoder();
        Decoder decoder = Base64.getDecoder();

        /* 2. 간편인증 HUBToken 처리 결과 복호화 */
        try {
            resultString = eziok.getResultJson(hubToken);
        } catch (Exception e) {
            return "-2|간편인증 결과 복호화 오류";
        }

        try {
            /* 3. 간편인증 결과 설정 */
            JSONObject jsonObject = new JSONObject(resultString);

            String clientTxId = jsonObject.getString("clientTxId");
            String serviceType = jsonObject.getString("serviceType");
            String encCi = jsonObject.optString("encCi", null);
            String encUsername = jsonObject.getString("encUsername");
            String encUserphone = jsonObject.getString("encUserphone");
            String encUserbirthday = jsonObject.getString("encUserbirthday");
            String encVid = jsonObject.optString("encVid", null);
            String certSerialNumber = jsonObject.optString("certSerialNumber", null);
            String providerId = jsonObject.getString("providerId");
            String txId = jsonObject.getString("txId");
            String issueDate = jsonObject.getString("issueDate");
            String issuer = jsonObject.getString("issuer");
            String signedData = jsonObject.optString("signedData", null);
            String plainData = null;

            /* 4. 세션 내 요청 clientTxId 와 수신한 clientTxId 가 동일한지 비교(권고) */
            if (!sessionClientTxId.equals(clientTxId))
                return "-4|세션값에 저장된 거래ID 비교 실패";

            /* 5. 입력 시간 검증 (검증결과 생성 후 5분 이내 검증 권고) */
            String dataFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);

            Date currentTime = formatter.parse(formatter.format(new Date()));
            Date targetTime = formatter.parse(issueDate);

            long diff = (currentTime.getTime() - targetTime.getTime()) / 1000;

            //if (diff > 300) {
                //return "-5|검증결과 토큰 생성 5분 경과 오류";
            //}

            /* 6. 인증사업자별 개인정보 결과 복호화 */
            String ci = null;
            String vid = null;
            String userName = null;
            String userphone = null;
            String userbirthday = null;

            userName = eziok.AESProviderDecrypt(providerId, encUsername);
            userphone = eziok.AESProviderDecrypt(providerId, encUserphone);
            userbirthday = eziok.AESProviderDecrypt(providerId, encUserbirthday);
            if (encCi != null) {
                ci = eziok.AESProviderDecrypt(providerId, encCi);
            }
            if (encVid != null) {
                vid = eziok.AESProviderDecrypt(providerId, encVid);
            }

            /* 7. 카카오 일 경우 VID 수신시 검증 */
            // - DB 또는 서버에 저장된 개인의 CI값을 획득해서 VID(CI 검증값) 확인
            // - SHA256(base64.decode(CI) + base64.decode(certSerialNumber)) == base64.decode(vid)
            if (encVid != null && ci != null && providerId.equals("kakao")) {
                // 카카오 vid 검증
                if (!eziok.verifyKakaoVid(ci, vid, certSerialNumber))
                    return "-7|카카오 VID(CI검증 정보) 확인 실패";
            }

            /* 8. 간편서명 요청시 전자서명 확인 */
            // - 간편서명 요청시 처리 (serviceType : "sign")
            // - 수신한 전자서명의 원문 획득, 전자서명 검증, 인증서 획득, 전자서명 검증이 필요할 경우 별도의 보안툴킷 처리 필요
            // - 간편인증 전자서명인증사업자는 인증서 검증 완료 후 결과 전달
            // if (jsonObject.getString("serviceType").equals("sign")) {
            // plainData = verifySignedData(signedData);
            //}

            /* 9. 가맹점 서비스 기능 처리 */
            // - 가맹점에서 수신받은 개인정보 검증 확인 처리
            // - 가맹점에서 수신받은 CI 확인 처리

            /* 10.응답결과 전달 */
            // - 간편인증 요청시 "retType": "callback" 일 경우 > callback function 전달
            // - 간편인증 요청시 "retType": "redirect" 는 결과 페이지로 이동(모바일 WebView 또는 iOS 등 팝업이 안되는 App일경우)
            JSONObject resultJson = new JSONObject();
            resultJson.put("errCode", "2000");
            resultJson.put("ci", ci);
            resultJson.put("vid", vid);
            resultJson.put("userName", userName);
            resultJson.put("userphone", userphone);
            resultJson.put("userbirthday", userbirthday);          
            return "0|" + resultJson.toString();
        } catch (EziokException e) {
            return e.getErrorCode() + "|" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "-999|서버 오류";
        }
    }

    public String sendPost(String dest, String jsonData) {
        HttpURLConnection connection = null;
        DataOutputStream dataOutputStream = null;
        try {
            URL url = new URL(dest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);

            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(jsonData.getBytes("UTF-8"));

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer responseData = new StringBuffer();
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                responseData.append(info);
            }

            return responseData.toString();
        } catch (FileNotFoundException e) {
            // Error Stream contains JSON that we can parse to a FB error
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }

                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getBody(HttpServletRequest request) {

            String body = null;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            body = stringBuilder.toString();
            return body;
        }


%>
<%
    /*1. 간편인증 비밀키 및 인증기관 비밀키 획득*/
    try {
        eziokKeyManager eziok = new eziokKeyManager();
        eziok.keyInit("/app/ISRY_BackEnd.war/WEB-INF/mok/eziok/eziok_keyInfo.dat", "dream");
        String requestBody = getBody(request).trim();
        if(requestBody == null){
            out.write("-8|간편인증 인증결과 응답이 없습니다. ");
        } else {
            // A. 간편인증 결과 처리 후 callback 또는 redirect 페이지 이동
            // - CASE 1 : 페이지 로딩시 간편인증 데이터 복호화 처리 및 callback 함수 전달
            //  간편인증 결과 타입, "keytoken"  사용시 이용 함수
            out.write(eziok_get_hubtoken(requestBody.trim(), eziok, (String) session.getAttribute("sessionClientTxId")));
            //  간편인증 결과 타입, "hubtoken"  사용시 이용 함수
            // out.write(eziok_std_result(requestBody.trim(), eziok, (String) session.getAttribute("sessionClientTxId")));
        }
    } catch (EziokException e) {
        out.write(e.getErrorCode() + "|" + e.getMessage());
    }

%>

<%-- //CASE 2 : redirect URL 사용시 간편인증 데이터 복호화 처리 및 URL 이동
<form method="post" action="/간편인증 처리 후 이동 URL/redirect.jsp" name="frm">
    <input type="text" name="data" value=<%=eziok_std_result(request.getParameter("hubToken").trim(), eziok, (String)session.getAttribute("sessionClientTxId"))%>>
</form>
<script language="javascript">
    document.frm.submit();
</script>
--%>