
/* 2. 간편인증 결과 수신 callback 함수 예시  */
function printResult(data) {
    
    var resultCode = data.split("|")[0];
    var resultMsg = data.split("|")[1];
    
    if (resultCode == 0) {
        
        // 간편인증 성공 완료시 처리 부분
        //document.querySelector("#result").textContent = resultMsg;
        
        var result = JSON.parse(resultMsg);
        //console.log("resultMsg", result);

        result["type"] = "simple";
        
        window.parent.postMessage(result, "*");
        
    } else {
        // 간편인증 실패 완료시 처리 부분
        alert("Error : " + resultMsg);
    }
}
