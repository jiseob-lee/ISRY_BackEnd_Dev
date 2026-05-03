<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat" %>
<%@ page import="com.dreamsecurity.crypt.*"%>
<%
	// 1. 휴대폰본인확인 결과수신
	String encPriInfo = request.getParameter("priinfo");

	// 2. 휴대폰본인확인 결과 복호화
	//     - 원문문자열 = msgDecrypt(암호화데이터, 인증키 경로);
	MsgCrypto mscr = new MsgCrypto();
	

	String root = session.getServletContext().getRealPath("/");
	
	root += root.endsWith("/") || root.endsWith("\\") ? "" : "/";
	
	String rstInfo = mscr.msgDecrypt(encPriInfo, root + "WEB-INF/mok/cert/youthsafePri.key","goodyouth","EUC-KR");
	
	//String rstInfo = mscr.msgDecrypt(encPriInfo, root + "/WEB-INF/mok/cert/youthsafePri.key","goodyouth","EUC-KR");

	String resultcd = ""; 	// 결과 코드
	String resultMsg = ""; 	// 결과 오류내용
	String ci = ""; 		// 연계정보(CI, Connection Information)
	String di = ""; 		// 중복가입확인정보(DI Duplication Information) 
	String telnum = ""; 	// 휴대폰번호
	String telco = ""; 		// 가입 통신사
	String birthday = ""; 	// 생년월일
	String gender = ""; 	// 성별 정보
	String nation = ""; 	// 내국, 외국인 정보
	String name = ""; 		// 이름
	String reqNum = ""; 	// 거래요청번호 
	String reqdate = ""; 	// 거래요청시간

	// 3. 휴대폰본인확인 결과 파싱 및 결과 확인
	//    - 첫번째 인자 값이 "00" 일 경우 성공, 이외 오류
	//    - 휴대폰본인확인 결과인자가 2개 이하일 경우 오류코드 및 오류내용 
	//    - 휴대폰본인확인 결과인자가 3개 이상일 경우 결과 성공 및 처리 
	String[] rstInfoArray = rstInfo.split("\\$");
	resultcd = rstInfoArray[0];
	if (rstInfoArray.length < 3 || !resultcd.equals("00")) {
		// 본인확인 오류 처리
		resultMsg = rstInfoArray[1];
		out.println("결과코드 : " + resultcd);
		out.println("오류내용 : " + resultMsg);
	} else {
		// 4. 휴대폰본인확인 결과 확인
		ci = rstInfoArray[1];
		di = rstInfoArray[2];
		telnum = rstInfoArray[3];
		telco = rstInfoArray[4];
		birthday = rstInfoArray[5];
		if (rstInfoArray[6].equals("1")) {
			gender = "남자";
		} else {
			gender = "여자";
		}
		if (rstInfoArray[7].equals("0")) {
			nation = "내국인";
		} else {
			nation = "외국인";
		}
		name = rstInfoArray[8];
		reqNum = rstInfoArray[9];
		reqdate = rstInfoArray[10];
					
		// 5. 휴대폰본인확인 결과 검증
		// 5.1 세션에 저장된 세션확인용 랜덤값 reqNum 획득
		//String sessionReqNum = session.getAttribute("sessionReqNum").toString();

		// 5.2 세션에 저장된 요청점검용 랜덤값(nonce)값과 수신한 nonce값이 일치하는지 확인 후 일치하지 않으면 오류 발생
		//if(sessionReqNum == null || reqNum == null || !reqNum.equals(sessionReqNum)){
		//	System.out.println("세션에 저장된 거래요청ID : session - " + sessionReqNum + ", resultReqNum -" +reqNum);
		//  세션 오류처리
		//}

		//6 응답유효시간 검증 : 요청일시 5분이내 요청정보만 점검 (권고)
		//6.1 현재시간 획득
		Calendar today = Calendar.getInstance();
		Date nowDate = today.getTime(); // 응답일시
		String reqDate = rstInfoArray[10]; // 요청일시
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		Date beforeDate = sdf.parse(reqdate); // 요청일시 타입 변환
		
		out.println((nowDate.getTime() - beforeDate.getTime()) / 60000 );
		out.println(nowDate.getTime() - beforeDate.getTime());
		
		//6.2 응답 유효시간 체크(5분 이내 권고)
		long diffMin = (nowDate.getTime() - beforeDate.getTime()) / 60000; // 요청일시 - 응답일시 (분단위)
		long min = 5; // 설정한 유효시간
		//if(diffMin > min){
			// 6.3 회원사가 정의한 유효시간이 지나면 오류 (sample은 5분으로 정의)
			//out.println("결과값 유효시간 5분 초과 검증 에러");
		//} else {

			// 7. 회원사 DB에서 중복사용(재사용) 했는지 확인 (권고)
			
			// 8. 획득한 개인정보 처리  
			System.out.println("결과코드 : " + resultcd);
			System.out.println("ci : " + ci);
			System.out.println("di : " + di);
			System.out.println("전화번호 : " + telnum );
			System.out.println("통신사 : " + telco );
			System.out.println("생년월일 : " + birthday);
			System.out.println("성별 : " + gender);
			System.out.println("내외국인 : " + nation);
			System.out.println("이름 : " + name);
			System.out.println("거래요청번호 : " + reqNum);
			System.out.println("요청시간 : "+reqdate);
			
			session.setAttribute("ci", ci);
			session.setAttribute("actPhone", "processing");
			
			//session.setAttribute("telnum", telnum);
			//session.setAttribute("birthday", birthday);
			//session.setAttribute("gender", gender);
			//session.setAttribute("name", name);
			
%>
<script type="text/javascript">
	var data = {telnum : '<%=telnum%>', birthday : '<%=birthday%>', gender : '<%=gender%>', name : '<%=name%>'};
	window.opener.postMessage(data, "*");
	self.close();
</script>
<%
		//}

	} 

%>
