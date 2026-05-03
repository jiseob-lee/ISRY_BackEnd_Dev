<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>개인정보 처리방침</title>
<style type="text/css">
div.main {
  width : 750px;
  line-height: 150%;
  margin-left: 10px;
}
table { 
	width: 100%; 
	border: 1px solid #444444; 
	border-collapse: collapse;
} 
th, td {
	border: 1px solid #444444;
}
p.c {
    text-indent: -1.5em;
	margin-left: 1.5em;
}
p.d {
    text-indent: -1em;
	margin-left: 1.5em;
}
p.e {
	margin-left: 2em;
}
p.f {
    text-indent: -1em;
	margin-left: 2.5em;
}
li {
    margin-bottom: 1em;
}
table#img, table#img td {
	border: none !important;
}

.label_box {
    width: 30%;
    height: 250px;
    box-sizing: border-box;
    padding: 20px 30px;
    border-radius: 10px;
    box-shadow: 0 0.2rem 0.8rem rgb(22 71 170 / 20%);
    display: flex;
    flex-flow: column;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    margin-bottom: 20px;
    background-color: #fff;
    border: 1px solid #fff;
    position: relative;
}

.label_wrap {
    width: 100%;
    box-sizing: border-box;
    padding: 0px 10px;
    display: flex;
    flex-flow: row wrap;
    justify-content: space-around;
    position: relative;
}

.label_layer {
    display: none;
    position: absolute;
    box-sizing: border-box;
    border-radius: 10px;
    background-color: #fff;
    border: 1px solid #eee;
    width: 300px;
    z-index: 999;
	color: black;
	font-size: 10pt;
}

.label_desc_top {
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
    background-color: #1647aa;
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
	color: white;
}

.label_desc_btm {
	padding: 7px;
}

</style>

</head>
<body>

<div class="main">

<br/>

<h2 style="text-align: center;">개인정보 처리방침</h2>

<br/><br/>

<h2 style="text-align: center;">여성가족부 위기청소년 통합지원정보시스템 개인정보 처리방침</h2>

<br/>

<p style="text-align: right: color: blue; text-align: right;">
<span style="color: blue;">시행일 2022년 9월 1일</span>
</p>

<table>
<tr><td style="padding: 1em;">
여성가족부는 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리 방침을 두고 있습니다.<br/>
<span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F 개인정보처리방침</span>은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.
</td></tr>
</table>

<br/><br/><br/>


<div class="label_wrap mgt30">
<a href="#1" class="label_box" style="border: 1px solid rgb(255, 255, 255);" 
	onmouseover="document.getElementById('label_layer_0').style.display = 'block'; this.style.border = '1px solid rgb(22, 71, 170)';"
	onmouseout="document.getElementById('label_layer_0').style.display = 'none'; this.style.border = '1px solid rgb(255, 255, 255)';">
	<img src="${pageContext.request.contextPath}/html/privacy/images/20220901/image01.png" alt="일반 개인정보 수집">
	<p class="label_cnt">일반 개인정보 수집</p>
	<div id="label_layer_0" class="label_layer num0" style="display: none; top: auto; left: 150px;">
		<div class="label_desc_top">
			일반 개인정보 수집
		</div>
		<div class="label_desc_btm">
			<p>처리하는 개인정보 항목은 다음과 같습니다.</p>
			◦ 사용자 정보 관리<br/>
			- 필수정보 : 아이디, 비밀번호, 이름, 이메일, 직장주소, 지역구분, 역할구분, 단위시스템, 소속기관, 소속부서<br/>
			- 선택정보 : 성별, 전화번호, 생일, 휴대전화번호, SNS구분,  SNS 아이디, 자격구분, 직위명, 기관장 여부, 입사일, 퇴사일, 서명 사진, 증명 사진
		</div>
	</div>
</a>

<a href="#1" class="label_box" style="border: 1px solid rgb(255, 255, 255);"
	onmouseover="document.getElementById('label_layer_1').style.display = 'block'; this.style.border = '1px solid rgb(22, 71, 170)';"
	onmouseout="document.getElementById('label_layer_1').style.display = 'none'; this.style.border = '1px solid rgb(255, 255, 255)';">
	<img src="${pageContext.request.contextPath}/html/privacy/images/20220901/image02.png" alt="개인정보 처리목적">
	<p class="label_cnt">개인정보 처리목적</p>
	<div id="label_layer_1" class="label_layer num1" style="display: none; top: auto; left: 150px;">
		<div class="label_desc_top">
			개인정보 처리목적
		</div>
		<div class="label_desc_btm">
			<p>개인정보 처리 목적은 다음과 같습니다.</p>
			◦ 「위기청소년 통합지원정보시스템」 구축·운영<br/>
			◦ 「위기청소년 통합지원정보시스템」 사용자 정보 관리
		</div>
	</div>
</a>

<a href="#1" class="label_box" style="border: 1px solid rgb(255, 255, 255);"
	onmouseover="document.getElementById('label_layer_2').style.display = 'block'; this.style.border = '1px solid rgb(22, 71, 170)';"
	onmouseout="document.getElementById('label_layer_2').style.display = 'none'; this.style.border = '1px solid rgb(255, 255, 255)';">
	<img src="${pageContext.request.contextPath}/html/privacy/images/20220901/image03.png" alt="보유기간">
	<p class="label_cnt">보유기간</p>
	<div id="label_layer_2" class="label_layer num1" style="display: none; top: auto; left: 150px;">
		<div class="label_desc_top">
			보유기간
		</div>
		<div class="label_desc_btm">
			<p>개인정보 보유기간은 다음과 같습니다.</p>
			◦ 사용자 정보 관리 : 사용자 탈퇴(또는 퇴직 및 업무 변경) 후 30일
		</div>
	</div>
</a>

<a href="#2" class="label_box" style="border: 1px solid rgb(255, 255, 255);"
	onmouseover="document.getElementById('label_layer_3').style.display = 'block'; this.style.border = '1px solid rgb(22, 71, 170)';"
	onmouseout="document.getElementById('label_layer_3').style.display = 'none'; this.style.border = '1px solid rgb(255, 255, 255)';">
	<img src="${pageContext.request.contextPath}/html/privacy/images/20220901/image04.png" alt="개인정보 제3자 제공">
	<p class="label_cnt">개인정보 제3자 제공</p>
	<div id="label_layer_3" class="label_layer num1" style="display: none; top: auto; left: 150px;">
		<div class="label_desc_top">
			개인정보 제3자 제공
		</div>
		<div class="label_desc_btm">
			<p>「사용자 정보」는 제3자 제공하지 않습니다.</p>
			※ 「위기청소년 통합지원정보시스템」 구축·운영을 위해 제3자에게 개인정보를 제공･연계하는 경우 별도 안내를 통해 동의를 받도록 하겠습니다.
		</div>
	</div>
</a>

<a href="#3" class="label_box" style="border: 1px solid rgb(255, 255, 255);"
	onmouseover="document.getElementById('label_layer_4').style.display = 'block'; this.style.border = '1px solid rgb(22, 71, 170)';"
	onmouseout="document.getElementById('label_layer_4').style.display = 'none'; this.style.border = '1px solid rgb(255, 255, 255)';">
	<img src="${pageContext.request.contextPath}/html/privacy/images/20220901/image05.png" alt="개인정보 처리 위탁">
	<p class="label_cnt">개인정보 처리 위탁</p>
	<div id="label_layer_4" class="label_layer num1" style="display: none; top: auto; left: 150px;">
		<div class="label_desc_top">
			개인정보 처리 위탁
		</div>
		<div class="label_desc_btm">
			<p>개인정보 처리 위탁 현황은 다음과 같습니다.</p> 
			◦ 위탁받는자 : ㈜메타빌드<br/>
			◦ 위탁업무 : 「위기청소년 통합지원정보시스템」 구축·운영<br/>
			<br/>
			※ 보다 상세한 내역은 연결된 세부 사항을 참조하시기 바랍니다.
		</div>
	</div>
</a>

<a href="#1-2" class="label_box" style="border: 1px solid rgb(255, 255, 255);"
	onmouseover="document.getElementById('label_layer_5').style.display = 'block'; this.style.border = '1px solid rgb(22, 71, 170)';"
	onmouseout="document.getElementById('label_layer_5').style.display = 'none'; this.style.border = '1px solid rgb(255, 255, 255)';">
	<img src="${pageContext.request.contextPath}/html/privacy/images/20220901/image06.png" alt="고충처리부서">
	<p class="label_cnt">고충처리부서</p>
	<div id="label_layer_5" class="label_layer num1" style="display: none; top: auto; left: 150px;">
		<div class="label_desc_top">
			고충처리부서
		</div>
		<div class="label_desc_btm">
			<p>개인정보 처리 관련 문의･ 불만처리 및 피해구제, 개인정보 자기결정권 행사를 원하시는 경우 다음의 연락처로 연락 바랍니다.</p>
			◦ 분야별 개인정보 보호 담당자 : 배찬수 사무관<br/>
			◦ 연락처 : 02-2100-6603,<br/>
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; prvicacy_tf@korea.kr
		</div>
	</div>
</a>

</div>

<!--
<table id="img">
<tr align="center" valign="top">
	<td><img src="image01.png" /></br>일반 개인정보 수집</td>
	<td><img src="image02.png" /></br>개인정보 처리목적</td>
	<td><img src="image03.png" /></br>보유기간</td>
</tr>
<tr align="center" valign="top">
	<td><img src="image04.png" /></br>개인정보 제3자 제공</td>
	<td><img src="image05.png" /></br>개인정보 처리 위탁</td>
	<td><img src="image06.png" /></br>고충처리부서</td>
</tr>
</table>
-->

<br/><br/>


<h3 id="1">제1조 개인정보의 처리목적, 개인정보의 처리 및 보유기간, 처리하는 개인정보의 항목</h3>

<ol>


<li>
개인정보의 처리 목적
<p>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 개인정보를 다음의 목적 이외의 용도로는 이용하지 않으며 이용 목적이 변경될 경우에는 동의를 받아 처리하겠습니다.</p>

<p class="c">가. 위기청소년 통합지원정보시스템 구축·운영 및 사용자관리</p>


</li>

<li>개인정보의 수집 및 보유
<p>가. 여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」</span>에서는 서비스 이용에 필요한 최소한의 개인정보를 동의 아래 수집하고 있으며, 수집하는 개인정보 항목은 다음과 같습니다.</p>

<table>
<colgroup>
    <col width="200px" />
	<col width="150px" />
	<col width="150px" />
	<col width="*" />
</colgroup>
<tr style="text-align: center; background-color: #EEE7DF;">
    <th>개인정보파일의 명칭</th>
    <th>운영근거 /<br/>처리목적</th>
    <th>개인정보의<br/>보유·이용 기간</th>
    <th>처리하는 개인정보 항목</th>
</tr>
<tr style="text-align: center; font-weight: bold; color: blue;">
    <td>행정업무지원 및 통합정보시스템 사용자 정보</td>
    <td style="font-style: italic;">『청소년복지 지원법』 제12조의2(위기청소년통합지원정보시스템의 구축 및 운영 등) / 「위기청소년 통합지원정보시스템」 구축･운영 및 사용자 관리</td>
    <td style="font-style: italic;">사용자 탈퇴(또는 퇴직 및 업무 변경) 후<br/>30일</td>
    <td style="text-align: left;"><p>1) 행정업무지원정보<br/>
아이디, 비밀번호, 이름, 이메일, 직장주소, 지역구분, 역할구분, 단위시스템, 소속기관, 소속부서<br/>
선택정보 : -</p>
<p>2) 사용자 정보<br/>
- 필수정보 : 아이디, 비밀번호, 이름, 이메일, 직장주소, 지역구분, 역할구분, 단위시스템, 소속기관, 소속부서<br/>
- 선택정보 : 성별, 전화번호, 
생일, 휴대전화번호, SNS구분,  SNS 아이디, 자격구분, 직위명, 기관장 여부, 입사일, 퇴사일, 서명 사진, 증명 사진</p>
</tr>
</table>

<p>
<span id="1-2">나. 개인정보의 열람청구를 접수·처리하는 부서</span>
  <ul style="color: blue;">
  <li>청소년안전망 시스템」구축 T/F</li>
  <li style="font-style: italic;">연락처 : 02-2100-6603</li>
  <li style="font-style: italic;">이메일 : <a href="mailto:prvicacy_tf@korea.kr">prvicacy_tf@korea.kr</a></li>
  </ul>
</p>
<p>다. 개인정보보호 종합지원포털(<a href="https://www.privacy.go.kr/" target="_blank">www.privacy.go.kr</a>) > 민원마당 > 개인정보의 열람 등 요구 > 개인정보파일 목록 검색 > 기관명에 “여성가족부”, 파일명에 “위기청소년”을 입력하면 세부 내용을 확인 할 수 있습니다.</p>
</li>

</ol>



<h3 id="2">제2조 개인정보의 제3자 제공에 관한 사항</h3>


<p class="f">
가. 여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」</span>은 원칙적으로 정보주체의 개인정보를 수집·이용 목적으로 명시한 범위 내에서 처리하며, 다음의 경우를 제외하고는 정보주체의 사전 동의 없이는 본래의 목적 범위를 초과하여 처리하거나 제3자에게 제공하지 않습니다.
</p>

<p class="f">
나. 여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 『청소년지원 복지법』제12조의2(위기청소년통합지원정보시스템의 구축 및 운영 등)제4항 및 제5항 등에 따라 고유식별정보(이름, 휴대전화번호)가 포함된 개인정보를 제3자에게 제공할 수 있습니다. 

<table style="margin-left: 2em; width: 95%;">
<colgroup>
    <col width="*" />
	<col width="150px" />
	<col width="150px" />
	<col width="150px" />
	<col width="150px" />
</colgroup>
<tr style="text-align: center; background-color: #EEE7DF;">
    <th>제공받는자</th>
    <th>제공 목적</th>
    <th>제공 항목</th>
    <th>보유 및 이용기간</th>
	<th>관련 근거</th>
</tr>
<tr style="text-align: center; color: blue;">
    <td>경찰청</td>
    <td><span style="font-weight: bold; text-decoration: underline;">「위기청소년통합지원정보시스템」 구축･운영 및 서비스 연계 제공</span></td>
    <td>이름, 휴대전화번호</td>
    <td><span style="font-weight: bold; text-decoration: underline;">만24세 까지</span></td>
	<td>『청소년복지 지원법』 제12조의2</td>
</tr>
</table>

<p class="e">
※ 목적 외 개인정보 제3자 제공 내역은 다음의 링크를 클릭하여 참조하시기 바랍니다.<br/>
< 클릭 시 이전 개인정보의 목적외 제3자 제공 게시판으로 링크 되어야 함 >
</p>
</p>

<p class="f">
다. 제3자에게 정보 제공 시, 개인정보를 제공 받는 자에게 이용 목적, 이용 방법, 이용 기간, 이용 형태 등을 제한하거나 제공한 개인정보 파일에 대한 안정성 확보 조치를 담보하기 위해 파일 암호화, 주요 개인정보의 마스킹 처리 등을 보호조치를 적용합니다.
</p>



<h3 id="3">제3조 개인정보 처리 위탁에 관한 사항</h3>

<ol>

<li>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 개인정보의 처리업무를 위탁하는 경우 다음의 내용이 포함된 문서에 의하고, 수탁자가 개인정보를 안전하게 처리하는 지 관리·감독 하고 있습니다.

<table style="margin-top: 1em;">
<tr>
<td style="padding: 0 1em 0;">
<p>가. 위탁업무 수행 목적 외 개인정보의 처리 금지에 관한 사항</p>
<p>나. 개인정보의 관리적·기술적 보호조치에 관한 사항</p>
<p>다. 개인정보의 안전관리에 관한 사항</p>

위탁업무의 목적 및 범위, 재위탁 제한에 관한 사항, 개인정보 안전성 확보 조치에 관한 사항, 위탁업무와 관련하여 보유하고 있는 개인정보의 관리현황점검 등 감독에 관한 사항, 수탁자가 준수하여야할 의무를 위반한 경우의 손해배상책임에 관한 사항<br/>
또한, 위탁하는 업무의 내용과 개인정보 처리업무를 위탁받아 처리하는 자(“수탁자”)에 대하여 해당 홈페이지에 공개하고 있습니다.<br/>&nbsp;
</td>
</tr>
</table>

</li>


<li>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 다음의 개인정보 처리 업무를 위탁하고 있습니다.

<table style="margin-top: 1em; margin-bottom: 1em;">
<colgroup>
	<col width="150px" />
	<col width="*" />
	<col width="160px" />
</colgroup>
<tr style="text-align: center; background-color: #EEE7DF;">
    <th style="padding: 8px;">수탁업체명</th>
    <th style="padding: 8px;">위탁업무 내용</th>
    <th style="padding: 8px;">보유 및 이용기간</th>
</tr>
<tr style="text-align: center; color: blue; font-style: italic;">
    <td style="padding: 8px;">㈜메타빌드</td>
    <td style="padding: 8px;">위기청소년 통합지원정보시스템 구축 및 운영</td>
    <td style="padding: 8px;">2024년 06월 까지</td>
</tr>
</table>

※ 위탁업무 내용이나 수탁자가 변경될 경우 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.
</li>

</ol>



<h3>제4조 정보주체와 법정대리인의 권리·의무 및 그 행사 방법</h3>
<ol>
<li>정보주체는 언제든지 다음과 같은 권리를 행사 할 수 있으며, 만14세 미만 아동의 법정대리인은 그 아동의 개인정보에 대한 열람, 정정·삭제, 처리정지를 요구할 수 있습니다.<br/>
   ※ “개인정보 처리 방법에 관한 고시(제2020-7호)” <a href="${pageContext.request.contextPath}/isry/itgcm/sysmgmt/file/fileDown.do?file=inspectionRequest.hwp&filename=(별지 8) 개인정보 열람 요구서.hwp">별지 제8호 서식</a> (열람 요구서)
</li>

<li>위 권리 행사는 개인정보보호법 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며, 이에 대해 지체없이 조치하겠습니다.</li>

<li>위 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” 
<a href="${pageContext.request.contextPath}/isry/itgcm/sysmgmt/file/fileDown.do?file=attorney.hwp&filename=(별지 11) 위임장.hwp">별지 제11호 서식</a>에 따른 위임장을 제출하셔야 합니다.
</li>

<li>개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제4항, 제37조 제2항등 다음의 경우에는 정보주체의 권리가 제한 될 수 있습니다.

<table style="margin-top: 1em;">
<tr>
<td style="padding: 0 1em 0;">
<p class="c">가. 법률에 따라 열람이 금지되거나 제한되는 경우</p>
<p class="c">나. 다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</p>
<p class="c">다. 공공기관이 다음 각 목의 어느 하나에 해당하는 업무를 수행할 때 중대한 지장을 초래하는 경우
  <ul>
  <li>조세의 부과·징수 또는 환급에 관한 업무</li>
  <li>「초·중등교육법」및「고등교육법」에 따른 각급 학교,「평생교육법」에 따른 평생교육시설, 그 밖의 다른 법률에 따라 설치 된 고등교육기관에서의 성적 평가 또는 입학자 선발에 관한 업무</li>
  <li>학력·기능 및 채용에 관한 시험, 자격 심사에 관한 업무</li>
  <li>보상금·급부금 산정 등에 대하여 진행 중인 평가 또는 판단에 관한 업무</li>
  <li>다른 법률에 따라 진행 중인 감사 및 조사에 관한 업무</li>
  </ul>
</p>
</td>
</tr>
</table>
</li>

<li>개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상 으로 명시되어 있는 다음의 경우에는 그 삭제를 요구할 수 없습니다.

<table style="margin-top: 1em;">
<tr>
<td style="padding: 0 1em 0;">
<p class="c">가. 법률에 특별한 규정이 있거나 법령상 의무를 준수하기 위하여 불가피한 경우</p>
<p class="c">나. 다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</p>
<p class="c">다. 공공기관이 개인정보를 처리하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우</p>
<p class="c">라. 개인정보를 처리하지 아니하면 정보주체와 약정한 서비스를 제공하지 못하는 등 계약의 이행이 곤란한 경우로서 정보주체가 그 계약의 해지 의사를 명확하게 밝히지 아니한 경우</p>
<p class="c">마. 개인정보의 열람, 정정·삭제, 처리정지 요구에 대해서는 10일 이내에 해당 사항에 대한 여성가족부의 조치를 통지 합니다.<br/>개인정보의 열람, 정정·삭제, 처리정지 요구는 해당 부서를 통해서 가능합니다.</p>
</td>
</tr>
</table>
</li>

<li>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다. 이때 본인 또는 법정대리인의 신분증 사본 등을 요청할 수 있습니다.</li>

<li>정보주체는 개인정보의 처리에 관한 동의 여부, 동의 범위 등을 선택하고 결정할 권리,  개인정보의 처리로 인하여 발생한 피해를 신속하고 공정한 절차에 따라 구제받을 권리 등을 행사할 수 있습니다.</li>

</ol>



<h3>제5조 개인정보의 파기</h3>

<ol>
<li>
여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 원칙적으로 개인정보 처리목적이 달성된 개인정보는 지체없이 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.

<table style="margin-top: 1em;">
<tr>
<td style="padding: 0 1em 0;">
<p>
가. 파기 절차<br/>
개인정보는 목적 달성 후 즉시 또는 별도의 공간에 옮겨져 내부 방침 및 기타 관련법령에 따라 일정기간 저장된 후 파기됩니다. 별도의 공간으로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.
</p>
<p>
나. 파기 기한 및 파기 방법<br/>
보유기간이 만료되었거나 개인정보의 처리목적달성, 해당 업무의 폐지 등 그 개인정보가 불필요하게 되었을 때에는 지체없이 파기합니다. 전자적 파일형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다. 종이에 출력된 대인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.
</p>
</td>
</tr>
</table>
</li>
<li>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 『공공기록물 관리에 관한 법률』등에 따라 보유기간이 경과한 개인정보를 보존해야 하는 경우 별도의 데이터베이스 테이블 등에 분리하여 보관하며, 보존기간이 경과 후에는 지체없이 파기합니다.
</li>
</ol>


<h3>제6조 개인정보 자동수집 장치의 설치ㆍ운영 및 거부에 관한 사항</h3>

<ol>
<li>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」</span>은 이용자에게 개인형 서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 '쿠키(cookie)'를 사용합니다.</li>
<li>
쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자들의 PC 컴퓨터내의 하드디스크에 저장되기도 합니다.
<p>가. 쿠키의 사용 목적 : 자주 찾는 서비스를 설정할 수 있도록 하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.</p>
<p>나. 쿠키의 설치·운영 및 거부
    <p class="d">◦ 인터넷 익스플로러(Internet Explorer) : 웹브라우저 상단의 도구 > 인터넷 옵션 > 개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부할 수 있습니다.</p>
    <p class="d">◦ 크롬(Chrome) : 웹브라우저 상단의 설정 > 개인정보 및 보안 > 쿠키 및 기타 사이트 데이터를 클릭하여 옵션 설정을 통해 모든 쿠키 차단을 설정할 수  있습니다.</p>
    <p class="d">◦ 파이어폭스(FireFox) : 웹브라우저 메뉴를 클릭하여 설정 > 개인 정보 및 보안 패널에서 향상된 추적 방지기능(Enhanced Tracking Protection)의 옵션 설정을 통해 쿠키 차단을 설정할 수  있습니다.</p>
    <p class="d">◦ 엣지(Edge) : 설정 메뉴 > 쿠키 및 사이트 권한 클릭 > 사이트에서 쿠키 데이터를 저장하고 읽도록 허용 옵션 설정을 통해 모든 쿠키 차단을 설정할 수  있습니다.</p></p>
<p>다. 쿠키 저장을 거부할 경우 개인형 서비스 이용에 어려움이 발생할 수 있습니다.</p>
</li>
</ol>



<h3>제7조 개인정보의 안전성 확보 조치</h3>

<ol>
<li>여성가족부 <span style="color: blue;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적, 관리적, 물리적 조치를 하고 있습니다.

<table style="margin-top: 1em;">
<tr>
<td style="padding: 1em 1em 0 0;">
<ol>
<li>'개인정보의 안전성 확보조치 기준에 의거하여 내부관리계획을 수립 및 시행합니다.</li>
<li>개인정보취급자 지정의 최소화 및 교육<br/>개인정보취급자의 지정을 최소화하고 정기적인 교육을 시행하고 있습니다.</li>
<li>개인정보에 대한 접근 제한<br/>개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여, 변경, 말소를 통하여 개인정보에 대한 접근을 통제하고, 침입차단시스템과 탐지시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있으며 권한 부여, 변경 또는 말소에 대한 내역을 기록하고, 그 기록을 최소 3년간 보관하고 있습니다.</li>
<li>접속기록의 보관 및 위변조 방지<br/>개인정보처리시스템에 접속한 기록(웹 로그, 요약정보 등)을 최소 2년 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 관리하고 있습니다.</li>
<li>개인정보의 암호화<br/>이용자의 개인정보는 암호화 되어 저장 및 관리되고 있습니다. 또한 중요한 데이터는 저장 및 전송 시 암호화하여 사용하는 등의 별도 보안기능을 사용하고 있습니다.</li>
<li>해킹 등에 대비한 기술적 대책<br/>여성가족부는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신‧점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적, 물리적으로 감시 및 차단하고 있습니다.</li>
<li>비인가자에 대한 출입 통제<br/>개인정보를 보관하고 있는 개인정보시스템의 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.</li>
<li>개인정보 침해사고 발생에 대응하기 위한 접속기록의 보관 및 위조‧변조 방지를 위한 조치</li>
<li>개인정보의 안전한 보관을 위한 보관시설의 마련 또는 잠금 장치의 설치 등 물리적 조치 등</li>
</ol>
</td>
</tr>
</table>

</li>
</ol>



<h3>제8조 권익침해 구제 방법</h3>
<ol>
<li>
정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.<br/>
  ◦ 개인정보 침해신고센터(한국인터넷진흥원 운영) : (국번없이) 118 (<a href="https://privacy.kisa.or.kr/" target="_blank">privacy.kisa.or.kr</a>)<br/>
  ◦ 개인정보 분쟁조정위원회 : (국번없이) 1833-6972 (<a href="https://www.kopico.go.kr/" target="_blank">www.kopico.go.kr</a>)<br/>
  ◦ 대검찰청 사이버범죄수사단 : (국번없이) 1301 (<a href="https://www.spo.go.kr/" target="_blank">www.spo.go.kr</a>)<br/>
  ◦ 경찰청 사이버안전국 : (국번없이) 182 (<a href="https://cyberbureau.police.go.kr/" target="_blank">cyberbureau.police.go.kr</a>)
</li>
<li>
<span style="color: blue;">위기청소년 통합지원정보시스템」구축 T/F</span>는 정보주체의 개인정보 자기결정권을 보장하고, 개인정보침해로 인한 상담 및 피해 구제를 위해 노력하고 있으며, 신고나 상담이 필요한 경우 아래의 담당부서로 연락해 주시기 바랍니다.<br/>
    - 부서명 :<span style="color: blue; font-style: italic;">「청소년안전망 시스템」개인정보 분야별 보호담당자</span><br/>
    - 담당자 : 배찬수 <span style="color: blue; font-style: italic;">사무관</span><br/>
    - 연락처 : &lt;<span style="color: blue; font-style: italic;">02-2100-6603</span>&gt;, &lt;<span style="color: blue; font-style: italic;"><a href="mailto:privacy_tf@korea.kr">privacy_tf@korea.kr</a></span>&gt;
</li>
<li>
「개인정보보호법」제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.<br/>
※ 중앙행정심판위원회 : (국번없이) 110 (<a href="https://www.simpan.go.kr/" target="_blank">www.simpan.go.kr</a>)
</li>
</ol>



<h3>제9조 개인정보 보호책임자에 관한 사항</h3>

<ol>

<li>
개인정보보호법 제31조 제1항에 따라 지정한 개인정보 보호책임자는 다음과 같습니다.

<table style="width: 95%;">
<tr>
<td style="padding: 1em; line-height: 190%;">
여성가족부 개인정보보호책임자 : 정책기획관 황윤정<br/>
- 연락처 : <span style="color: blue; font-style: italic;">02-2100-6122</span>, <span style="color: blue; font-style: italic;"><a href="mailto:youngocho@korea.kr">youngocho@korea.kr</a></span><br/>
※ 개인정보 분야별 보호담당자로 연결됩니다.<br/>
「위기청소년 통합지원정보시스템」개인정보 분야별 보호책임자 :  박정식 <span style="color: blue; font-style: italic;">서기관</span><br/>
- 연락처 : <span style="color: blue; font-style: italic;">02-2100-6572</span>, <span style="color: blue; font-style: italic;"><a href="mailto:p777@korea.kr">p777@korea.kr</a></span><br/>
※ 개인정보 분야별 보호담당자로 연결됩니다.<br/>
「위기청소년 통합지원정보시스템」개인정보 분야별 보호담당자 :  배찬수 <span style="color: blue; font-style: italic;">사무관</span><br/>
- 담당자 연락처 : <span style="color: blue; font-style: italic;">02-2100-6603</span>, <span style="color: blue; font-style: italic;"><a href="mailto:privacy_tf@korea.kr">privacy_tf@korea.kr</a></span>
</td>
</tr>
</table>
</li>

<li>
정보주체는 여성가족부 <span style="color: blue; font-weight: bold;">「위기청소년 통합지원정보시스템」</span>의 서비스(또는 사업)를 이용하시면서 발생한 모든 개인정보보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의할 수 있습니다. 여성가족부 <span style="color: blue; font-weight: bold;">「위기청소년 통합지원정보시스템」구축 T/F</span>는 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.
</li>

</ol>


<h3>제10조 개인정보 처리방침 변경</h3>

<ol>
<li>이 개인정보 처리방침은 2022. 9. 1.부터 적용됩니다.</li>
<!--
<li>이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다.<br/>
- <span style="color: blue; font-style: italic;">2022. 8 31. 적용</span> (클릭) < 클릭 시 이전 개인정보처리방침으로 링크 되어야 함 >
</li>
-->
</ol>

<br/><br/><br/>
&nbsp;

</div>

</body>
</html>
