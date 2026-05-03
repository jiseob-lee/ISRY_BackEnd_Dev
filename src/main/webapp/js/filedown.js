
function filedown(strFileNm, strAtfino, strMngSn, strServerFileNm) {

    var locArr = location.href.split("/");
    var contextPath = "";
    if (locArr[3] != null && locArr[3] != "" && locArr[3] == "ISRY_BackEnd") {
        contextPath = "/" + locArr[3];
    }
    
    var loc = contextPath + "/isry/itgcm/sysmgmt/file/fileDown.do" +  
            "?file=" + 
            "&filename=" + encodeURIComponent(strFileNm == null ? "" : strFileNm) +
            //"&path=" + encodeURIComponent(dmDown.getValue("strFilePath") == null ? "" : dmDown.getValue("strFilePath")) +
            "&atfino=" + encodeURIComponent(strAtfino == null ? "" : strAtfino) +
            "&mngSn=" + encodeURIComponent(strMngSn == null ? "" : strMngSn) +
            "&serverFilename=" + encodeURIComponent(strServerFileNm == null ? "" : strServerFileNm);

    $.ajax({
        url: loc,
        data: {},
        method: "GET",
        //dataType: "json",
        cache: false,
        xhrFields: {
            responseType: "blob"
        }
    })
    .done(function (data, message, xhr) { 
        //hideAjaxImage(); 
        if (xhr.readyState == 4 && xhr.status == 200) {
            // 성공했을때만 파일 다운로드 처리하고
            var disposition = xhr.getResponseHeader('Content-Disposition'); 
            var filename; 
            if (disposition && disposition.indexOf('attachment') !== -1) { 
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/; 
                var matches = filenameRegex.exec(disposition); 
                if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, ''); 
            } 
            var blob = new Blob([data]); 
            var link = document.createElement('a'); 
            link.href = window.URL.createObjectURL(blob); 
            link.download = decodeURIComponent(filename); 
            link.click(); 
        } else {
            //실패했을때는 alert 메시지 출력
            alert("다운로드에 실패하였습니다."); 
        }
    })
    .fail(function(xhr, status, errorThrown) {
        alert("다운로드에 실패하였습니다.");
        //console.log("xhr", xhr);
        //console.log("errorThrown", errorThrown);
        //console.log("status", status);
    });
}
