/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.gitple.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import isry.gitple.mapper.GitpleEventMapper;
import isry.gitple.service.GitpleEventService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명        : GitpleEventMapper.java
 * @프로그램 설명 	: 깃플챗 이벤트를 저장한다.
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("gitpleEventService")
public class GitpleEventServiceImpl implements GitpleEventService {

	@Resource(name="gitpleEventMapper")
    public GitpleEventMapper gitpleEventMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	private static final Logger LOGGER = LoggerFactory.getLogger(GitpleEventServiceImpl.class);
	
	String gitpleEventaRcptnCn = ""; // 이벤트에서 받아온 전체 내용
	String gitpleEventaTypeNm = ""; // 이벤트 유형
	String gitpleAppCode = ""; // app_code
	String strGitpleSecret = ""; // 요청 변수 설정 (secret)
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void insertAYBData(JSONObject body) throws Exception {
		//System.out.println("body = " + body);
		gitpleEventaRcptnCn    = body.toJSONString(); // 이벤트에서 받아온 전체 내용
		gitpleEventaTypeNm = body.get("type").toString(); // 이벤트 유형
		gitpleAppCode = body.get("appCode").toString(); // app_code
		
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		LOGGER.info("gitple log start");
		LOGGER.info("gitpleEventaRcptnCn = " + gitpleEventaRcptnCn);
		LOGGER.info("gitpleEventaTypeNm = " + gitpleEventaTypeNm);
		LOGGER.info("gitpleAppCode = " + gitpleAppCode);
		LOGGER.info("gitple log end");
		
		
		String chttType = "";
		switch (gitpleAppCode) {
		case "zYCJ9uRrO3LJ54xLEisjGUPPrrlX41":
			chttType = "01"; // admin, 채팅앱
			break;
		case "M7WtinMVjnV58owXx6LbI4olKcVn51":
			chttType = "02"; // 청소년상담1388, 카카오
			break;
		case "AebgBZtazhzvjGcciqrSsE5rlBo461":
			chttType = "03"; // 잇는채팅 테스트, 카카오
			break;
		case "nDQIbmrFeGhOEiGmAd8cmGU2j4yl81":
			chttType = "04"; // #00001388, 비즈챗(SMS)
			break;
		case "ERDC8rvrmDXl7CHVfzGkyDW2GtTIb1":
			chttType = "05"; // 1388, KT(SMS)
			break;
		case "JzuRaBmcdn4KZi3u3wiUXrAHOKqTd1":
			chttType = "06"; // 페이스북, 페이스북
			break;
		case "40IFzvlMaDnrOhf6ezeMoVG5qfS8e1":
			chttType = "07"; // 청소년상담_잇는채팅, 카카오
			break;
		case "XjN5Fc9Phj1sypFCZ6iiSHyfoGVyg1":
			chttType = "08"; // 웹채팅테스트, 채팅앱
			break;
		default :
			chttType = "00"; // 알수없음 오류
			break;
		}
		String secret = "isry.gitple.secret";
		strGitpleSecret = EgovProperties.getProperty("globals", secret+chttType);
		String httpUrl = "";
		String response = "";
		String assigneeId = ""; // 상담자 ID
		String mogefId = "";	// 청소년안전망시스템(여가부)ID
		String mogefNm = "";	// 청소년안전망시스템(여가부)회원명
		if("send_external_message".equals(gitpleEventaTypeNm)) { // 메세지 전송인 경우
			sendExternalMessage(body);
			return;
		}
		
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap = (Map)body.get("session");
		Gson gson = new GsonBuilder().create();
		String strSession = gson.toJson(jsonMap);
		JSONObject json = stringToJson(strSession);
		if(!"session_state_open".equals(gitpleEventaTypeNm)) {
			if(!"".equals(json.get("assigneeId")) && json.get("assigneeId") != null) {
				assigneeId = json.get("assigneeId").toString(); // 상담자 ID
			}
		} else {
			assigneeId = "open"; // 세션이 생성되는 session_state_open에서는 상담자를 알 수 없다. session_state_open이벤트도 DB에 저장 해야 하는지 확인.
		}
		
		if("session_state_close".equals(gitpleEventaTypeNm)) { // session 종료 이벤트
			if(null != json.get("inprogressTime")) {
				ScpDb scpDb = new ScpDb(); // 암호화 util
				
				String sessionId = json.get("id").toString(); // session id
				String reason = json.get("reason").toString(); // 세션 종료 사유
				//agent:상담사 종료, bot:봇 종료, sys:시스템 종료 , transfer:전달 시 , agentTimeout:상담사 타임아웃 
				//userTimeout:고객 타임아웃, botTimeout:봇 타임아웃, userCancel:고객대기취소, userCancelSubmit:고객 메시지 접수 취소
				//submit:메시지 접수, webhookMessage:웹훅 메시지, missed:부재중 전화
				
				JSONObject sessionJson = new JSONObject();
				String ipAddr = "";
				
				String userId = json.get("userId").toString(); // 내담자 ID
				String userNm = ""; //내담자명
				String inorigressTime = json.get("inprogressTime").toString(); // 상담시작 시간
				String openTime = json.get("openTime").toString(); // 상담대기 시간
				String closeTime = json.get("closeTime").toString(); // 상담종료 시간
				
				///////////////////////////////////////////////////////////세션 정보 가져오기(ip주소)
				httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/sessions/"+sessionId;
				String sessionResponse = gitpleUrlProcess(httpUrl, gitpleAppCode, strGitpleSecret);
				JSONObject jsonSession = stringToJson(sessionResponse);
				if(!"".equals(jsonSession.get("system")) && jsonSession.get("system") != null) {
					sessionJson = (JSONObject)jsonSession.get("system");
					if(!"".equals(sessionJson.get("ip")) && sessionJson.get("ip") != null) {
						ipAddr = sessionJson.get("ip").toString();
					} else {
						ipAddr = "ipNull";
					}
				} else {
					ipAddr = "systemNull";
				}
				///////////////////////////////////////////////////////////세션 정보 가져오기(ip주소) 끝 
				
				String assigneeNm = ""; // 상담자명
				// ########################################################채팅 내용 가져오기
				httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/sessions/"+sessionId+"/messages";
				response = gitpleUrlProcess(httpUrl, gitpleAppCode, strGitpleSecret);
				// ########################################################채팅 내용 가져오기 끝
				
				// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@상담사 명 가져오기
				httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/agents/"+assigneeId;
				String jsonAssigneeInfo = gitpleUrlProcess(httpUrl, gitpleAppCode, strGitpleSecret); // 상담사정보 jsonString
				JSONObject jsonAgent = stringToJson(jsonAssigneeInfo);
				assigneeNm = jsonAgent.get("name").toString();
				//String assigneeEncNm = scpDb.scpEncB64(assigneeNm); // 상담사 명 암호화
				String agentIdentifier = jsonAgent.get("identifier").toString(); // 상담사 로그인ID
				// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@상담사 명 가져오기 끝
				
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!내담자 정보 가져오기
				httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/users/"+userId;
				String jsonUserInfo = gitpleUrlProcess(httpUrl, gitpleAppCode, strGitpleSecret); // 내담자정보 jsonString
				JSONObject jsonUser = stringToJson(jsonUserInfo);
				String userEncNm = "";
				String userPhone = "";
				String userEncEmail = "";
				String sxdcSeCd = ""; 
				if(!"".equals(jsonUser.get("name")) && jsonUser.get("name") != null) { // name은 optional. "name"이라는 key가 없을 수도 있다.
					userNm = jsonUser.get("name").toString();
//					userEncNm = scpDb.scpEncB64(userNm); // 내담자 명 암호화
				}
				if(!"".equals(jsonUser.get("phone")) && jsonUser.get("phone") != null) { // phone은 optional. "phone"이라는 key가 없을 수도 있다.
					userPhone = jsonUser.get("phone").toString();
				}
				if(!"".equals(jsonUser.get("email")) && jsonUser.get("email") != null) { // email은 optional. "email"이라는 key가 없을 수도 있다.
					userEncEmail = jsonUser.get("email").toString();
				}
				if(!"".equals(jsonUser.get("userFields")) && jsonUser.get("userFields") != null) { // userFields은 optional. "userFields"이라는 key가 없을 수도 있다.
					JSONObject jsonUserFields = stringToJson(jsonUser.get("userFields").toString());
					if(!"".equals(jsonUserFields.get("sex")) && jsonUserFields.get("sex") != null) {
						String sex = jsonUserFields.get("sex").toString();
						if("남".equals(sex)) {
							sxdcSeCd = "M";
						}
						if("여".equals(sex)) {
							sxdcSeCd = "F";
						}
					}
				}
				String userIdentifier = jsonUser.get("identifier").toString(); // 내담자 로그인ID
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!내담자 정보 가져오기 끝
				
				// ######################################################## 깃플ID로 사용자ID 가져오기
				Map<String, String> map = gitpleEventMapper.getMogefId(agentIdentifier);
				if(map != null && !"".equals(map.get("USER_ID")) && null != map.get("USER_ID")) {
					mogefId = map.get("USER_ID");
					mogefNm = map.get("FLNM_ENCPT");
				}
				if("".equals(mogefId) || null == mogefId) {
					mogefId = agentIdentifier; // 사용자ID가 없는 경우 깃플ID를 넣는다.
				}
				if("".equals(mogefNm) || null == mogefNm) {
					mogefNm = agentIdentifier; // 사용자명이 없는 경우 깃플사용자 명을 넣는다.
				}
				// ######################################################## 깃플ID로 사용자ID 가져오기 끝
				
				String chttDscsnCn = chatContents(assigneeNm, userNm, response);

				Map<String, String> ayb200Map = new HashMap<>(); // AYB200 map 생성
				ayb200Map.put("CHRO_NO", sessionId); // 채팅방번호
				ayb200Map.put("CHRO_NM", "채팅상담실"); // 채팅방명
				ayb200Map.put("CNSLTNT_ID", mogefId); // 상담사ID(청소년안전망 ID)
				ayb200Map.put("EMPLA_NO", agentIdentifier); // EMP_ID(깃플 ID)
				ayb200Map.put("CLIENA_ID", userIdentifier); // 내담자ID
				ayb200Map.put("CHTT_BGNG_DT", convertDate(openTime, "P")); // 상담시작 시간
				ayb200Map.put("CHTT_END_DT", convertDate(closeTime, "P")); // 상담종료 시간
				ayb200Map.put("CHTT_DSCSN_CN", chttDscsnCn); // 채팅상담내용
				ayb200Map.put("NCKN_NM", "재택사이버상담"); // 닉네임명
				ayb200Map.put("CHTT_TYPE_SE_CD", chttType); // 채팅유형구분명
				ayb200Map.put("DEL_YN", "N"); // 삭제여부
				
				ayb200Map.put("CNSLTNT_NM_ENCPT", mogefNm); // 상담사명 암호화
				ayb200Map.put("CLIENA_NM_ENCPT", userNm); // 내담자명 암호화
				ayb200Map.put("CLIENA_TELNO", userPhone); // 내담자 전화번호
				ayb200Map.put("CLIENA_EML_ADDR_ENCPT", userEncEmail); // 내담자 이메일 암호화
				ayb200Map.put("CNTN_IP_ADDR", ipAddr); // 접속아이피주소 수정 요망
				
				
				ayb200Map.put("END_CS_DTL_CN", reason); // 세션 종료 사유(상담 종료 사유)
				ayb200Map.put("CHTT_END_SE_CD", reason); // 채팅종료구분코드
				ayb200Map.put("CHTT_DSCSN_BGNG_DT", convertDate(inorigressTime, "P")); // 상담시작 시간
				ayb200Map.put("SXDC_SE_CD", sxdcSeCd); // 성별
				
/*				ayb200, 202테이블을 제외하고 사용하지 않음. 2023.01.06 반재정 부장님과 협의
				Map<String, String> ayb230Map = new HashMap<>(); // AYB230 map 생성
				ayb230Map.put("CNSLTNT_ID", mogefId); // 상담사ID
				ayb230Map.put("CNSLTNT_NM_ENCPT", mogefNm); // 상담사명 암호화
				ayb230Map.put("CHRO_NO", sessionId); // 채팅방번호
				ayb230Map.put("BGNG_DT", convertDate(inorigressTime, "P")); // 시작일시
				ayb230Map.put("END_DT", convertDate(closeTime, "P")); // 종료일시
				ayb230Map.put("WAIT_DT", convertDate(openTime, "P")); // 대기일시
				ayb230Map.put("CLIENA_EXIT_DT", convertDate(closeTime, "P")); // 내담자퇴장일시
				
				Map<String, String> ayb240Map = new HashMap<>(); // AYB240 map 생성
				ayb240Map.put("CHRO_NO", sessionId); // 채팅방번호
				ayb240Map.put("CNSLTNT_ID", mogefId); // 상담사ID
				ayb240Map.put("CHRO_NM", "채팅상담실"); // 채팅방명
				ayb240Map.put("BGNG_DT", convertDate(inorigressTime, "P")); // 시작일시
				ayb240Map.put("END_DT", convertDate(closeTime, "P")); // 종료일시
				
				Map<String, String> ayb250AgentMap = new HashMap<>(); // AYB250 agent map 생성
				ayb250AgentMap.put("CHRO_NO", sessionId); // 채팅방번호
				ayb250AgentMap.put("USER_ID", mogefId); // 사용자 아이디
				ayb250AgentMap.put("USER_NM_ENCPT", mogefNm); // 사용자명 암호화
				ayb250AgentMap.put("CNTN_DT", convertDate(inorigressTime, "P")); // 접속 일시
				ayb250AgentMap.put("CHTT_END_DT", convertDate(closeTime, "P")); // 종료 일시
				ayb250AgentMap.put("CNTN_IP_ADDR", ipAddr); // 접속아이피주소 수정 요망
				ayb250AgentMap.put("FRST_RGTR_ID", agentIdentifier); // 사용자 아이디
				
				Map<String, String> ayb250UserMap = new HashMap<>(); // AYB250 user map 생성
				ayb250UserMap.put("CHRO_NO", sessionId); // 채팅방번호
				ayb250UserMap.put("USER_ID", userId); // 사용자 아이디
				ayb250UserMap.put("USER_NM_ENCPT", userEncNm); // 사용자명 암호화
				ayb250UserMap.put("CNTN_DT", convertDate(openTime, "P")); // 접속 일시
				ayb250UserMap.put("CHTT_END_DT", convertDate(closeTime, "P")); // 종료 일시
				ayb250UserMap.put("CNTN_IP_ADDR", ipAddr); // 접속아이피주소 수정 요망
				ayb250UserMap.put("FRST_RGTR_ID", userId); // 사용자 아이디
*/				
				gitpleEventMapper.insertAyb200Data(ayb200Map); // AYB200 테이블 저장
//				gitpleEventMapper.insertAyb230Data(ayb230Map); // AYB230 테이블 저장
//				gitpleEventMapper.insertAyb240Data(ayb240Map); // AYB240 테이블 저장
//				gitpleEventMapper.insertAyb250Data(ayb250AgentMap); // AYB250 테이블 상담사 저장
//				gitpleEventMapper.insertAyb250Data(ayb250UserMap); // AYB250 테이블 사용자 저장
			}
		} 
		insertAyb201Data(assigneeId); // 이벤트 정보 AYB201 테이블 저장
	}
	
	/**
	 * @Method명   : sendExternalMessage
	 * @param JSONObject
	 * @return void
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 23
	 * @Method설명 : send_external_message인 경우  
	 */
	public void sendExternalMessage(JSONObject json) throws Exception {
		JSONObject fromJson = objectToJson(json, "from");
		String agentId = fromJson.get("id").toString(); // 상담사id
		
		JSONObject toJson = objectToJson(json, "to");
		String userId = toJson.get("id").toString(); // 내담자id
		
		String cTime = json.get("ctime").toString(); // 전송시간 YYYY-MM-DDTHH:MI:SS.SSSZ형식
		String regDt = convertDate(cTime, "P"); // db에 저장될 전송 시간
		
		JSONObject messageJson = objectToJson(json, "message");
		String sMessage = messageJson.get("content").toString(); // 메모내용
		
		Map<String, String> ayb260Map = new HashMap<>();
		ayb260Map.put("REG_DT", regDt);
		ayb260Map.put("CLIENA_ID", userId);
		ayb260Map.put("CNSLTNT_ID", agentId);
		ayb260Map.put("CNSLTNT_NCKN_NM", "상담사닉네임");
		ayb260Map.put("CHTT_MEMO_CN", sMessage);
		
		gitpleEventMapper.insertAyb260Data(ayb260Map); // AYB260 테이블 저장
		
		insertAyb201Data(agentId);
	}
	
	/**
	 * @Method명   : convertDate
	 * @param YYYY-MM-DDTHH:MI:SS.SSSZ
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 21
	 * @Method설명 : 깃플챗에서 넘어오는 시간 FORM YYYY-MM-DDTHH:MI:SS.SSSZ형식 (ISO 8601)을 DB에 넣기 위해 CONVERT. 시간도 약 9시간 정도 차이가 남. 
	 */
	public String convertDate(String gitpleDate, String cType) throws Exception { //YYYY-MM-DDTHH:MI:SS.SSSZ
		String sTime = "";
		gitpleDate = gitpleDate.substring(0, 19); //YYYY-MM-DDTHH:MI:SS		
		int iHour = 9; // 9시간 차이남
		LocalDateTime time = LocalDateTime.parse(gitpleDate);
		if("P".equals(cType)) {
			sTime = time.plusHours(iHour).toString();
			sTime = sTime.replace("-", "");
			sTime = sTime.replace("T", " ");
		} else if("M".equals(cType)) {
			sTime = time.minusHours(iHour).toString();
		}
		return sTime;
		
	}
	
	/**
	 * @Method명   : gitpleUrlProcess
	 * @param curl
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 21 
	 * @Method설명 : 깃플챗으로 curl을 호출하여 결과를 받아온다. java에서 curl 호출 실패. HttpURLConnection 사용
	 */
	public String gitpleUrlProcess(String command, String appCode, String secret) throws Exception {
		URL url = new URL(command);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("app-code", appCode);
		conn.setRequestProperty("secret", secret);
		
		conn.setDoOutput(true);
		
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		br.close();
		LOGGER.info("curl = " + command);
		LOGGER.info("gitpleUrlProcess = " + sb.toString());
		return sb.toString();		
	}
	
	
	/**
	 * @Method명   : stringToJson
	 * @param String
	 * @return JSON
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 21
	 * @Method설명 : String을 받아 JSON으로 parsing return
	 */
	public JSONObject stringToJson(String str) throws Exception {
		JSONObject json = new JSONObject();
		JSONParser parser = new JSONParser();
		json = (JSONObject)parser.parse(str);
		return json;
	}
	
	/**
	 * @Method명   : chatContents
	 * @param String, String
	 * @return list
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 21
	 * @Method설명 : 대화내용을 기존 데이터와 맞춤
	 */
	public String chatContents(String agent, String user, String responseArr) throws Exception {
		String strList = "";
		
		JSONParser parser = new JSONParser();
		JSONArray jsonArr = (JSONArray)parser.parse(responseArr);
		
		for(int i = jsonArr.size()-1; i >= 0; i--) {
			JSONObject json = (JSONObject)jsonArr.get(i);
			String senderType = json.get("senderType").toString(); // 상담사:agent, 내담자:user, 시스템:sys
			String senderTypeNm = "";
			String chtContents = json.get("content").toString();
			if("sys".equals(senderType)) {
				senderTypeNm = "시스템";
			} else if("agent".equals(senderType)) { // 상담사
				senderTypeNm = agent;
			} else if("user".equals(senderType)) { // 내담자
				senderTypeNm = user;
			}
			String senderTime = json.get("ctime").toString();// 깃플 시간
			String chtTime = convertDate(senderTime, "P"); // 대한민국 시간
			strList = strList + "[" +senderTypeNm + " * " + chtTime + "]" + " : " + chtContents + "\n";
		}
		return strList;
	}
	
	/**
	 * @Method명   : insertAyb201Data
	 * @param String
	 * @return void
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 21
	 * @Method설명 : 이벤트 정보 AYB201 테이블 저장
	 */
	public void insertAyb201Data(String assigneeId) throws Exception {
		if("".equals(assigneeId) || null == assigneeId) {
			assigneeId = "SYSTEM";
		}
		Map<String, String> ayb201Map = new HashMap<>();
		ayb201Map.put("GITPLE_EVENTA_RCPTN_CN"   , gitpleEventaRcptnCn);
		ayb201Map.put("GITPLE_EVENTA_TYPE_NM", gitpleEventaTypeNm);
		ayb201Map.put("USER_ID", assigneeId);
		
		gitpleEventMapper.insertAyb201Data(ayb201Map); // 이벤트 정보 AYB201 테이블 저장
	}
	
	/**
	 * @Method명   : objectToJson
	 * @param JSONObject
	 * @return JSONObject
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 27
	 * @Method설명 : JSONObject를 받아 JSONObject으로 return  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject objectToJson(JSONObject obj, String objKey) throws Exception {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<>();
		map = (Map)obj.get(objKey);
		Gson gson = new GsonBuilder().create();
		String strMessage = gson.toJson(map);
		json = stringToJson(strMessage);
		return json;
	}
	
	/**
	 * @Method명   : gitpleSearch
	 * @param 
	 * @return void
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 7. 11
	 * @Method설명 : 배치를 돌려 매일 새벽 1시에 AYB202 테이블 저장
	 */
	@Override
	public void gitpleSearch() throws Exception  {
		LOGGER.info("gitpleSearch gogo");
		String httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/agentStateLogs";
		/*
		 * 배치시간은 하루 한번 01:00 -> 매시간 5분에 돌림
		LocalDate localDate = LocalDate.now(); // YYYY-MM-DDTHH:MI:SS.MIS
		String yesterDay = localDate.minusDays(1).toString(); // 어제 날짜
		String sStartTime = yesterDay + "T09:00:00.000Z"; // UTC(깃플 시간)와 KST는 9시간 차이남. 
		String sEndTime = localDate + "T08:59:59.000Z"; // UTC(깃플 시간)와 KST는 9시간 차이남. 
		//String appCode = "XjN5Fc9Phj1sypFCZ6iiSHyfoGVyg1"; // 웹채팅테스트, 채팅앱 app_code
		 */
		String lastBatchTime = gitpleEventMapper.getLastBatch(); // 마지막 배치 파라미터(2023-01-06T00:00:00)
		lastBatchTime = lastBatchTime.substring(0, 16) + ":00";
		LOGGER.info("lastBatchTime = " + lastBatchTime);
		LocalDateTime localDateTime = LocalDateTime.now();
		String nowTime = localDateTime.minusMinutes(1).toString().substring(0, 19);
		LOGGER.info("nowTime = " + nowTime);
		String sStartTime = LocalDateTime.parse(lastBatchTime).plusSeconds(1).toString(); // 깃플 시간
		LOGGER.info("sStartTime = " + sStartTime);
		String sEndTime   = nowTime; // 현재시간 -1분
		
		// SAB710 테이블에 저장할 데이터(대한민국 시간) YYYY-MM-DDTHH:MI:SS
		Map<String, String> TimeMap = new HashMap<String, String>();
		
		TimeMap.put("STNG_PARA_VALUE3", LocalDateTime.parse(sStartTime).plusHours(9).toString());
		TimeMap.put("STNG_PARA_VALUE4", sEndTime);
		
		// 깃플에 요청할 데이터. 9시간 차이 남
		sStartTime = sStartTime + ".000Z"; // 시작시간
		//sEndTime = convertDate(sEndTime, "M") + ".999Z"; // 종료시간
		LOGGER.info("########################################################################");
		LOGGER.info("sEndTime = " + LocalDateTime.parse(sEndTime).minusHours(9).toString() + ".999Z");
		sEndTime = LocalDateTime.parse(sEndTime).minusHours(9).toString() + ":00.999Z"; // 종료시간
		TimeMap.put("STNG_PARA_VALUE1", sStartTime);
		TimeMap.put("STNG_PARA_VALUE2", sEndTime);
		
		String appCode = "";
		String secretCode = "";
		String gitpleBatch = "";
		httpUrl = httpUrl+"?startTime="+sStartTime+"&endTime="+sEndTime;
		List<Map<String, String>> list = gitpleEventMapper.selectChttType();
		for(Map<String, String> map : list) {
			appCode = map.get("ADDTNG_MNG_VALUE2");
			secretCode = map.get("ADDTNG_MNG_VALUE4");
			gitpleBatch = gitpleBatchUrlProcess(httpUrl, appCode, secretCode);
			
			if(!"".equals(gitpleBatch) && gitpleBatch != null) { // quartz 데이터 있음
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject)parser.parse(gitpleBatch);
				if(!"".equals(json.get("items")) && json.get("items") != null) {
					String items = json.get("items").toString();
					JSONArray jsonArr = (JSONArray)parser.parse(items);
					
					for(Object obj : jsonArr) {
						JSONObject forJson = (JSONObject)obj;
						String agentId = forJson.get("agentId").toString();
						String stateId = forJson.get("stateId").toString();
						String modTime = forJson.get("time").toString();
						String httpStateUrl = "https://api-cyber1388.gitple.biz/v1/exports/agentStates/"+stateId;
						String stateJsonStr = gitpleUrlProcess(httpStateUrl, appCode, secretCode);
						JSONObject stateJson = (JSONObject)parser.parse(stateJsonStr);
						String stateNm = stateJson.get("state").toString();
						
						// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 상담사 ID 가져오기
						httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/agents/"+agentId;
						String jsonAssigneeInfo = gitpleUrlProcess(httpUrl, appCode, secretCode); // 상담사정보 jsonString
						JSONObject jsonAgent = stringToJson(jsonAssigneeInfo);
						String agentIdentifier = jsonAgent.get("identifier").toString(); // 상담사 로그인ID
						// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 상담사 ID 가져오기 끝
						
						// ######################################################## 깃플ID로 사용자ID 가져오기
						String mogefId = "";	// 청소년안전망시스템(여가부)ID
						Map<String, String> mogefMap = gitpleEventMapper.getMogefId(agentIdentifier);
						LOGGER.info("gitple_mogefMap = " + mogefMap);
						//mogefId = gitpleEventMapper.getMogefId(agentIdentifier);
						if(mogefMap != null && !"".equals(mogefMap.get("USER_ID")) && null != mogefMap.get("USER_ID")) {
							mogefId = mogefMap.get("USER_ID");
						}
						// ######################################################## 깃플ID로 사용자ID 가져오기 끝
						
						String chgDt = convertDate(modTime, "P"); // 상타값 변경 시간
						Map<String, String> ayb202Map = new HashMap<String, String>();
						ayb202Map.put("CNSLTNT_ID", mogefId);
						ayb202Map.put("STTS_NM", stateNm);
						ayb202Map.put("CHG_DT", chgDt);
						ayb202Map.put("DSCSN_STTS_SE_CD", stateId);
						gitpleEventMapper.insertAyb202Data(ayb202Map);  // AYB202 테이블 저장
						/* 2023-01-13 AYC495테이블 저장 삭제
						if(!"".equals(mogefId) && null != mogefId) {
							// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ AYC495테이블 저장 시작
							if("online".equals(stateNm) || "busy".equals(stateNm)) {
								// 근퇴 정보. commuteMap이 null이면 출근 정보가 없다
								Map<String, String> commuteMap = gitpleEventMapper.getCommute(mogefId);
								LOGGER.info("########################################################################");
								LOGGER.info("gitple_commuteMap = " + commuteMap);
								if(commuteMap.get("CONSTT_ID") == null) {
									Map<String, String> ayc495Map = new HashMap<String, String>();
									ayc495Map.put("CONSTT_ID", mogefId);
									ayc495Map.put("ATENDB_DT", chgDt);
									gitpleEventMapper.insertAyc495Data(ayc495Map);  // AYB202 테이블 저장
								}
							}
							// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ AYC495테이블 저장 끝
						}
						*/
					}
					TimeMap.put("STNG_PARA_VALUE5", "" + jsonArr.size());
				} else {
					LOGGER.info("json.get(items) is null");
					LOGGER.info("json = " + json);
				}
			} else {
				LOGGER.info("gitple quartz 결과가 없습니다!");
				TimeMap.put("STNG_PARA_VALUE5", "0");
			}
		}
		gitpleEventMapper.updateBatchTime(TimeMap);
		// 이력에 저장. 개발 완료 후 삭제 예정
		//gitpleEventMapper.insertBatchTime(TimeMap);
	}
	
	/**
	 * @Method명   : gitpleBatchUrlProcess
	 * @param curl
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 7. 11 
	 * @Method설명 : 배치로 깃플챗에서 데이터를 받아온다.. java에서 curl 호출 실패. HttpURLConnection 사용
	 */
	public String gitpleBatchUrlProcess(String command, String appCode, String secret) throws Exception {
		
		String serverName = System.getProperty("SERVER"); // 서버명
		if (!"rybwas11".equals(serverName)) {
			return "";
		}
		URL url = new URL(command);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("app-code", appCode);
		conn.setRequestProperty("secret", secret);
		
		conn.setDoOutput(true);
		
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		br.close();
		LOGGER.info("curl = " + command);
		LOGGER.info("gitpleUrlProcess = " + sb.toString());
		return sb.toString();		
	}
	
	/**
	 * @Method명   : existsGitpleID
	 * @param request
	 * @return String
	 * @throws Exception
	 * @작성자     : Hai.Ryong.Kim
	 * @작성일     : 2023. 07. 24 
	 * @Method설명 : 로그인 ID로 gitple ID조회. 
	 */
	public String existsGitpleID(HttpServletRequest request) throws Exception{
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		Map<String, String> gitpleMap = gitpleEventMapper.gitpleManager(userId);
		String gitpleId = gitpleMap == null ? "" : gitpleMap.get("GITPLE_ID");
		
		return gitpleId;
	}
	
	
	/**
	 * @Method명   : gitpleManager
	 * @param request
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 05 
	 * @Method설명 : 로그인 ID로 gitple ID조회. token생성 gitple URL호출
	 */
	public String gitpleManager(HttpServletRequest request) throws Exception{
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		Map<String, String> gitpleMap = gitpleEventMapper.gitpleManager(userId);
		String gitpleId = gitpleMap == null ? "" : gitpleMap.get("GITPLE_ID");
		String sUrl = "";
		if("".equals(gitpleId) || gitpleId == null) {
			// gitple ID가 없음. 자동로그인 불가
			throw new AppWorksException("등록된 깃플챗 ID가 없습니다.", Alert.ERROR);
		} else {
			String gitpleToken = UUID.randomUUID().toString();
			if("jslee".equals(userId)) { // 테스트 ID
				gitpleToken = "87fcdd4d-891b-4972-bf40-e4a938a21793";
			}
			String enfsnNo = gitpleMap.get("ENFSN_NO");
			Map<String, String> map = new HashMap<String, String>();
			map.put("ENFSN_NO", enfsnNo);
			map.put("GITPLE_ID", gitpleId);
			map.put("UNIVER_ESNTAL_IDFR_NO", gitpleToken);
			gitpleEventMapper.updateUniverEsntalIdfrNo(map);
			sUrl = "https://cyber1388.gitple.biz/#/login?qtoken=" + gitpleToken;
			//URL url = new URL(sUrl);
			//HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//conn.setConnectTimeout(10000);
			//conn.setRequestMethod("GET");
		}
		return sUrl;
	}
	
	/**
	 * @Method명   : gitpleId
	 * @param token
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 10. 05 
	 * @Method설명 : token으로 gitple ID조회
	 */
	public String gitpleId(String token) throws Exception{
		String gitpleId = gitpleEventMapper.getGitpleIdToken(token);
		return gitpleId;
	}
	
	/**
	 * @Method명   : gitpleLogout
	 * @param token
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 12. 15 
	 * @Method설명 : 깃플챗 로그아웃
	 */
	public String gitpleLogout(HttpServletRequest request) throws Exception{
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String gitpleId = gitpleEventMapper.getGitpleIdUserId(userId);
		strGitpleSecret = EgovProperties.getProperty("globals", "isry.gitple.logout");
		String httpStateUrl = "https://api-cyber1388.gitple.biz/v1/requests/agents/logout";
		String param = "{\"identifier\" :\""+ gitpleId+"\", \"state\" : \"offline\"}";
		String rtnMsg = gitpleLogoutProcess(httpStateUrl, strGitpleSecret, param);
		return rtnMsg;
	}
	
	/**
	 * @Method명   : gitpleUrlProcess
	 * @param curl
	 * @return String
	 * @throws Exception
	 * @작성자     : Hee.Sung.Yoon
	 * @작성일     : 2022. 6. 21 
	 * @Method설명 : 깃플 로그아웃 프로세스
	 */
	public String gitpleLogoutProcess(String command, String secret, String param) {
		try {
			URL url = new URL(command);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("secret", secret);
			conn.setRequestProperty("Content-type", "application/json");
			conn.setDoOutput(true);
			
			OutputStream os = conn.getOutputStream();
			byte paramData[] = param.getBytes("utf-8");
			os.write(paramData);
			//conn.connect();
			StringBuilder sb = new StringBuilder();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			os.close();
			br.close();
			return sb.toString();		
		} catch (Exception e) {
			throw new AppWorksException("퇴근 처리가 정상적으로 동작 하지 않았습니다.\n깃플에서 상태 값을 변경해 주세요.", Alert.ERROR);
		}
		
	}
}