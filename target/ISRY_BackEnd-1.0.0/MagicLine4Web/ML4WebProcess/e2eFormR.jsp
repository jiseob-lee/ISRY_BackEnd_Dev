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
		System.out.println("- Decrypt java.net.URLDecoder.decode Result1 ["+ java.net.URLDecoder.decode((String)sbPlain.toString(),"utf-8") +"]");
		System.out.println("- Decrypt sbPlain.toString Result2 ["+ sbPlain.toString() +"]");
		System.out.println("- Decrypt Result3 ["+ java.net.URLDecoder.decode((String)sbPlain.toString(),"euc-kr") +"]");
		System.out.println("- Decrypt Result4 ["+ java.net.URLDecoder.decode((String)sbPlain.toString(),"euc-kr") +"]");
		System.out.println("- Decrypt Result5 ["+ new String(sbPlain.toString().getBytes(),"utf-8") +"]");
		// 복호화 데이터
		String[] parts = java.net.URLDecoder.decode((String)sbPlain.toString(),"utf-8").split(division);
		
		for (String pair : parts) {
			String[] kv = pair.split("=");
			map.put(kv[0], kv[1]);
		}
		
	} else {
		System.out.println("error");
	}
%>
<jsp:include page="include/header.jsp"></jsp:include>
<jsp:include page="include/menu.jsp"></jsp:include>
<script type="text/javascript">
</script>

<div id="middle">
	<h2>MagicLine Digital Signature Result</h2>
	<div id="workArea"><!-- DIV START  -->
		<table class="styledLeft">
			<tr>
				<td style="border: 0;"><nobr> Digital Signature Result </nobr></td>
			</tr>
			<tr>
				<td style="border: 0;">&nbsp;</td>
			</tr>
			<tr>
				<td>
				<table style="border: 0;">
					<tbody>
						<tr style="border: 0;">
							<td style="border: 0; ">
							<nobr> description  :
							 				</nobr></td>
							<td style="border: 0;">
	사용자가 선택한 인증서를 이용하여 원문데이터에 전자서명값을 추출하여 서버에서 전자서명 검증을 실행하며<br/>
	서버는 사용자 인증서의 유효성 여부를 확인한다.<br/>
							</td>
						</tr>
					</tbody>
				</table>
				</td>
			</tr>
		</table>
		<p>&nbsp;</p>
<table style="width: 100%; height:100%"  class="styledLeft">
		<thead>
			<tr>
				<th colspan="2">Client Digital Signature Information</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="formRow">
					<table class="normal" cellspacing="0" style="text-align: left;">
						<tr>
							<td>Name<font class="required">*</font></td>
							<td>
								<input type="text" name="name" id="name" value="<%=map.get("name")%>" />
							</td>
						</tr>
						<tr>
							<td>Address<font class="required">*</font></td>
							<td>
								<input type="text" name="address" id="address" value="<%=map.get("address")%>" />
							</td>
						</tr>
						<tr>
							<td>Tel<font class="required">*</font></td>
							<td>
								<input type="text" name="tel"  id="tel" value="<%=map.get("tel")%>" />
							</td>
						</tr>
						<tr>
							<td>ETC<font class="required">*</font></td>
							<td>
								<input type="text" name="etc"  id="etc" value="<%=map.get("etc")%>" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
		<p>&nbsp;</p>
		<table style="width: 100%" class="styledLeft">
			<thead>
				<tr>
					<th >Program Guide (Examples)</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="formRow">
						<ul type="disc">
							<li>
								<p>1.MagicLine class include </p>
								<pre class="programlisting">
	<!-- &lt;%@ page import="com.dreamsecurity.magicline.servlet.DSHttpServletResponse" %&gt;
	&lt;%@ page import="com.dreamsecurity.magicline.servlet.DSHttpServletRequest" %&gt;
	&lt;%@ page import="com.dreamsecurity.java.net.URLEncoder" %&gt;
	&lt;%@ page import="com.dreamsecurity.jcaos.x509.X509Certificate" %&gt;
	&lt;%@ page import="com.dreamsecurity.magicline.MessageConstants"%&gt;
	&lt;%@ page import="com.dreamsecurity.magicline.config.Logger"%&gt; -->
								</pre>
								<p>&nbsp;</p>
							</li>
							<li>
								<p>2.MagicLine Server Digital Signature result </p>
							<pre class="programlisting">
							<!-- <span class="emphasis">
								<em>
	&lt;!-- 다음과 같이 선언하여 MagicLine Server에서 전자서명을 수행하도록 한다. --&gt; </em></span>
	&lt;%
		DSHttpServletResponse dRes = null;
		DSHttpServletRequest dReq = null;
	
		X509Certificate cert = null;
		byte[] privatekeyRandom = null;
		String signType = "";
		String subDN = "";
		java.math.BigInteger serialNumber = null;
		int messageType;
	
	
		try {
			dRes = new DSHttpServletResponse(response);<span class="emphasis"><em>// HttpResponse -&gt; DSHttpResponse </em></span>
			dReq = new DSHttpServletRequest(request);<span class="emphasis"><em>// HttpRequest -&gt; DSHttpRequest </em></span>
			dRes.setRequest(dReq);
	
			<span class="emphasis"><em>//Infomation (기타 정보는 java doc 참조.) </em></span>
			cert = dReq.getSignerCert();<span class="emphasis"><em>//사용자의 인증서 정보 (X509Certificate)  </em></span>
			subDN = cert.getSubjectDN().getName();<span class="emphasis"><em>//사용자 인증서 DN  </em></span>
			serialNumber = cert.getSerialNumber();<span class="emphasis"><em>//사용자 인증서 SerialNumber  </em></span>
			messageType = dReq.getRequestMessageType();<span class="emphasis"><em>// Client Message Type  </em></span>
			signType = dReq.getSignType();<span class="emphasis"><em>//사용자 인증서 타입 </em></span>
	
		} catch (Exception e) {
			out.println("[전자서명 실패] error message:"+e.getMessage());
			return;
		}
	
					<span class="emphasis"><em>//서명 검증 성공.
										</em>
									</span>
		%&gt; -->
								</pre>
								<p>&nbsp;</p>
							</li>
						</ul>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div><!-- DIV END  -->
<jsp:include page="include/footer.jsp"></jsp:include>