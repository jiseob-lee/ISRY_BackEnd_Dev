<%@ page contentType = "text/html;charset=utf-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>

        <script src="https://cert.ez-iok.com/stdauth/ds_auth_ptb/asset/js/ptb_ezauth_proc.js"></script>
        <script>
            function init_popup_auth() {
                /* 1. 간편인증 인증요청  */
                // eziok_std_process(간편인증 인증요청 생성 URL, 웹브라우져타입[WB:웹브라우져, MB:모바일웹, MWV:모바일웹View], callback함수명)
                eziok_std_process("https://gov.youthsafety.go.kr/eziok/eziok_auth.jsp", "WB", "printResult");
            }

            /* 2. 간편인증 결과 수신 callback 함수 예시  */
            function printResult(data) {
                var resultCode = data.split("|")[0];
                var resultMsg = data.split("|")[1];
                if(resultCode == 0) {
                        // 간편인증 성공 완료시 처리 부분
                        document.querySelector("#result").textContent = resultMsg;
                } else {
                        // 간편인증 실패 완료시 처리 부분
                        alert("Error : " + resultMsg);
                }
            }
        </script>
    </head>
    <body>
        <button onclick="init_popup_auth();">간편인증_팝업</button>
        <textarea cols="100" rows="20" id="result"></textarea>
    </body>
</html>
