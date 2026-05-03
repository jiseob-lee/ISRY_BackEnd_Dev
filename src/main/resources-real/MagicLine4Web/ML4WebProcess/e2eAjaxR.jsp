<%@ page import="com.dreamsecurity.magicline.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dreamsecurity.magice2e.MagicE2E" %>
<%@ page import="com.dreamsecurity.jcaos.exception.ConfirmPasswordException" %>
<%@ page import="java.util.HashMap" %>
<%
	MagicE2E ml = (MagicE2E)session.getAttribute("Magie2e");
	String sEncData = request.getParameter("encData");
	String division = "`"; // 구분자
	HashMap<String, String> map = new HashMap<String, String>();
	
	StringBuffer sbPlain = new StringBuffer(); // 출력 버퍼

	// 암호 스트링이 있을때만 복호화
	if (sEncData != null && sEncData.length() > 0 && ml != null){
		int sDecryptResult = ml.decrypt(sEncData, sbPlain);
		
		System.out.println("- Encrypt Result ["+ sEncData +"]");
		System.out.println("- sDecryptResult ["+ sDecryptResult +"]");
		System.out.println("- Decrypt Result ["+ java.net.URLDecoder.decode((String)sbPlain.toString(),"utf-8") +"]");
		
		// 복호화 데이터
		String[] parts = java.net.URLDecoder.decode((String)sbPlain.toString(),"utf-8").split(division);
		
		for (String pair : parts) {
			String[] kv = pair.split("=");
			map.put(kv[0], kv[1]);
		}
		JSONObject result  = new JSONObject(map);
		out.println(result.toJSONString());
	} else {
		System.out.println("error");
	}
%>


