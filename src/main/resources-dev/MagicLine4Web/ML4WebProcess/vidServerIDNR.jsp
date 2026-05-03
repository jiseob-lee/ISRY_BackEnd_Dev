<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dreamsecurity.magicline.JCaosCheckCert"%>
<%@ page import="com.dreamsecurity.jcaos.x509.X509Certificate"%>
<%@ page import="com.dreamsecurity.jcaos.x509.X509GeneralName"%>
<%@ page import="com.dreamsecurity.jcaos.x509.X509OtherName"%>
<%@ page import="com.dreamsecurity.jcaos.util.encoders.Base64"%>
<%@ page import="com.dreamsecurity.jcaos.util.encoders.Hex"%>
<%@ page import="com.dreamsecurity.jcaos.vid.VID"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.ArrayList"%>
<%
	// 서명 검증 셈플
	// 클라이언트에서 받은 서명 데이타를 검증
	String sResult = "";
	String sSignData = null;
	String sVIDRandomHash = null;
	JCaosCheckCert jcaosCheck = null;
	//String sIDN = null;
	String sSourceText = null;
	String textCheck = "";
	String sPolicy = "";
	String sidentifyData = "";
	
	// 서명 데이타를  가져옴 
	// 본 셈플에서는 서명 값을 Post Data 의 SignData 에 넣어서 보낸다고 간주 코딩 한다
	sSignData = request.getParameter("signedData");
	sSignData = URLDecoder.decode(sSignData, "utf-8");
	sVIDRandomHash = request.getParameter("vidRandomHash");
	sVIDRandomHash = URLDecoder.decode(sVIDRandomHash, "utf-8");
	
	sResult = sResult+"- SignData ["+sSignData+"]<br>\n"+"- VIDData ["+sVIDRandomHash+"]<br>\n";

	// 서명 데이타가 있을때 서명 검증
	if (sSignData != null && sSignData.length() > 0){
		try{
			jcaosCheck = new JCaosCheckCert();
			
			// 서버가 알고 잇는 주민등록 번호를 등록한다 
			jcaosCheck.setVIDRandomHash(sVIDRandomHash);
			
			sResult = sResult+"<br>\n- 인증서 검증 시작<br>\n";
			
			// 서명 검증
			// 검증후 원문이 리턴됨
			int iResult = jcaosCheck.checkCert(sSignData);
			/*
			- JCaosCheckCert.checkCert 의 에러코드는 하기와 같습니다.
			JCaosCheckCert.STAT_OK										// 성공
			JCaosCheckCert.STAT_ERR_WRONGCERT							// 정상적인 인증서가 아님
			JCaosCheckCert.STAT_ERR_ETC									// 기타 오류
			JCaosCheckCert.STAT_ERR_VerifyException 					// 서명 검증 실패
			JCaosCheckCert.STAT_ERR_CertificateNotYetValidException 	// 인증서 유효기간 검증 오류
			JCaosCheckCert.STAT_ERR_CertificateExpiredException 		// 인증서 만료
	 		JCaosCheckCert.STAT_ERR_ObtainCertPathException				// 인증서 경로 구축 실패
			JCaosCheckCert.STAT_ERR_BuildCertPathException 				// 인증서 경로 구축 실패
			JCaosCheckCert.STAT_ERR_TrustRootException 					// 신뢰할수 없는 최상위 인증서
			JCaosCheckCert.STAT_ERR_ValidateCertPathException			// 인증서 경로 검증 실패
			JCaosCheckCert.STAT_ERR_RevokedCertException				// 폐지된 인증서
			JCaosCheckCert.STAT_ERR_RevocationCheckException			// CRL 검증 실패
			JCaosCheckCert.STAT_ERR_NotExistSignerCertException			// 서명자 인증서 누락
			JCaosCheckCert.STAT_ERR_IOException							// IOException
			JCaosCheckCert.STAT_ERR_FileNotFoundException				// FileNotFoundException
			JCaosCheckCert.STAT_ERR_NoSuchAlgorithmException			// NoSuchAlgorithmException
			JCaosCheckCert.STAT_ERR_NoSuchProviderException 			// NoSuchProviderException 
			JCaosCheckCert.STAT_ERR_ParsingException        			// ParsingException        
			JCaosCheckCert.STAT_ERR_IdentifyException       			// 본인확인 실패
			*/
			
			if (iResult != 0){
				// 오류 발생시 오류를 구분
				String sCertResult = null;
				switch(iResult){
					case JCaosCheckCert.STAT_ERR_WRONGCERT							:	// 정상적인 인증서가 아님
						sCertResult = "서명에 사용된 인증서가 정상적인 인증서가 아닙니다.";
						break;
					case JCaosCheckCert.STAT_ERR_RevocationCheckException			:	// CRL 검증 실패
					case JCaosCheckCert.STAT_ERR_NotExistSignerCertException		:	// 서명자 인증서 누락
					case JCaosCheckCert.STAT_ERR_IOException						:	// IOException
					case JCaosCheckCert.STAT_ERR_FileNotFoundException				:	// FileNotFoundException
					case JCaosCheckCert.STAT_ERR_ETC								:	// 기타 오류
					case JCaosCheckCert.STAT_ERR_BuildCertPathException 			:	// 인증서 경로 구축 실패
					case JCaosCheckCert.STAT_ERR_ObtainCertPathException			:	// 인증서 경로 구축 실패
					case JCaosCheckCert.STAT_ERR_ValidateCertPathException			:	// 인증서 경로 검증 실패
					case JCaosCheckCert.STAT_ERR_TrustRootException 				:	// 신뢰할수 없는 최상위 인증서
						sCertResult = "서명 인증서 검증 오류 ["+iResult+"].";
						break;
					case JCaosCheckCert.STAT_ERR_VerifyException 					:	// 서명 검증 실패
						sCertResult = "서명 검증 실패";
						break;
					case JCaosCheckCert.STAT_ERR_CertificateNotYetValidException	: 	// 인증서 유효기간 검증 오류
						sCertResult = "서명 인증서 유효기간 검증 오류";
						break;
					case JCaosCheckCert.STAT_ERR_CertificateExpiredException 		:	// 인증서 만료
						sCertResult = "만료된 인증서 ";
						break;
					case JCaosCheckCert.STAT_ERR_RevokedCertException				:	// 폐지된 인증서
						sCertResult = "폐지된 인증서";
						break;
					default:
						sCertResult = "기타오류 ["+iResult+"]";
						break;
				}
				sResult = "<br>\n- "+sCertResult+" \n[" + jcaosCheck.getLastErr() +"]<br>\n\n"; 
			}else{
				// 서명 검증에 성공
				sResult = sResult+ "- 인증서 검증 성공<br>\n";
				
				// 서명에 사용된 인증서를 가져온다
				X509Certificate cert = jcaosCheck.getUserCert();
				String signerDN = cert.getSubjectDN().getName();	// 인증서 DN
				BigInteger serialNumber = cert.getSerialNumber();	// 인증서 시리얼
				
				// 본인확인 
				switch (jcaosCheck.getVIDCheck()){
					case JCaosCheckCert.STAT_VID_NOTCHECK:
						sResult = sResult+"- 본인 확인 하지 않음<br>\n";
						break;
					case JCaosCheckCert.STAT_VID_CHECK_OK:
						sResult = sResult+"- 본인 확인 성공<br>\n";
						break;
					case JCaosCheckCert.STAT_VID_CHECK_FAIL:
						sResult = sResult+"- 본인 확인 실패<br>\n";
						break;
				}
				
				// 서명 값 (Base64)
				String base64SignData = sSignData;
				
				// 서명 원문을 가져온다
				//sSourceText = jcaosCheck.getSrcStr("UTF-8");		// 원문의 인코딩을 알고 있어어야 한다 생략시 UTF-8 로 인코딩한다
				textCheck = new String(jcaosCheck.getSrcByte());
				byte[] textByte = Base64.decode(textCheck);
				sSourceText = new String(textByte, "utf-8");
				
				Properties props = new Properties();
				props.load(getServletContext().getResourceAsStream("/MagicLine4Web/ML4Web/js/message/Messages.properties"));
				
				sPolicy = props.getProperty("OID_" + cert.getCertificatePolicies().getPolicyIdentifier(0).replace(".", "_"));
				
				ArrayList generalNames = cert.getSubjectAlternativeName();
				if (generalNames != null && generalNames.size() > 0)
				{
					X509GeneralName genName;
					for (int i=0; i<generalNames.size(); i++) {
						genName = (X509GeneralName)generalNames.get(i);
						if (genName.getType() == X509GeneralName.TYPE_OTHER_NAME) {
							String identifyData = genName.getStringName();
							
							X509OtherName otherName = X509OtherName.getInstance(((X509GeneralName)generalNames.get(i)).getOtherName());
							VID vid = VID.getInstance(otherName.getIdentifyData().getVid());
							sidentifyData = new String(Hex.encode(vid.getVirtualID()));
						}
					}
				}
				
				// 화면 출력값 생성
				sResult = sResult+  "<br>\n- 사용자 DN ["+signerDN+"]<br>\n"+"<br>\n";
				sResult = sResult+  "- 발급자 DN ["+cert.getIssuerDN().getName()+"]<br>\n"+"<br>\n";
				sResult = sResult+  "- 인증서 SN ["+cert.getSerialNumber().toString(16)+"]<br>\n"+"<br>\n";
				sResult = sResult+  "- 인증서 정책 ["+cert.getCertificatePolicies().getPolicyIdentifier(0)+"]<br>\n"+"<br>\n";
				sResult = sResult+  "- 인증서 구분 ["+sPolicy+"]<br>\n"+"<br>\n";
				sResult = sResult+  "- 본인확인 식별값 ["+sidentifyData+"]<br>\n"+"<br>\n";
			}
		}catch(Exception e){
			// 인증서 검증중 오류가 난 경우
			// 처리를 편하게 하기 위해
			// 상용중에는 사용자의 인증서의 유효성의 문제가 잇는 경우가 대부분 입니다.
			e.printStackTrace();
			sResult = "서명 검증에 실패 하였습니다.\n [" + e.getMessage()+"]\");";
		}
	} else
	{
		sResult=" - 서명 데이타가 존재하지 않습니다..<br>\n";
	}

	//out.print(sResult);
	//System.out.println(sResult);
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
		<form action="login_renewR.jsp" method="post" name="popForm">
		<table style="width: 100%" class="styledLeft">
			<thead>
				<tr>
					<th colspan="2">Client Digital Signature Request Data</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="formRow">
					<table class="normal" cellspacing="0">
						<tr>
							<td>서명원문</td>
							<td><%=sSourceText %></td>
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
					<th colspan="2">Client Certificate Information</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="formRow">
					<table class="normal" cellspacing="0">
						<tr>
							<td>sResult:</td>
							<td><%=sResult%></td>
						</tr>
						<tr>
							<td>Serial Number:</td>
							<%-- <td><%=serialNumber.toString() %></td> --%>
						</tr>
						<tr>
							<td>Message Type:</td>
							<%-- <td><%=messageType %></td> --%>
						</tr>
						<tr>
							<td>Sign Type:</td>
							<%-- <td><%=signType %></td> --%>
						</tr>
					</table>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
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
<!-- <script type="text/javascript">
	setCookie('current-breadcrumb', 'magicline_v40_menu');
	document.onload = setBreadcrumDiv();
	function setBreadcrumDiv() {
		var breadcrumbDiv = document.getElementById('breadcrumb-div');
		breadcrumbDiv.innerHTML = '<table cellspacing="0"><tr><td class="breadcrumb-link"><a href="index.jsp">Home</a></td><td class="breadcrumb-link">&nbsp;>&nbsp;MagicLine4</td><td class="breadcrumb-link">&nbsp;>&nbsp;Digital Signature</td>';
	}
</script> -->
<jsp:include page="include/footer.jsp"></jsp:include>