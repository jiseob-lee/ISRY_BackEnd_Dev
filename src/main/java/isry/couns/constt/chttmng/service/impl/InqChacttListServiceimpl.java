/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.couns.constt.chttmng.mapper.InqChacttListMapper;
import isry.couns.constt.chttmng.service.InqChacttListService;
import isry.gitple.mapper.GitpleEventMapper;
import isry.gitple.service.impl.GitpleEventServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : BbsonmServicelmpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Service("InqChacttListService")
public class InqChacttListServiceimpl implements InqChacttListService{
	
	@Resource(name = "InqChacttListMapper")
	private InqChacttListMapper inqChacttListMapper;

	@Resource(name="gitpleEventMapper")
    public GitpleEventMapper gitpleEventMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InqChacttListServiceimpl.class);
	
	@Override
	public List<Map<String, Object>> selectInqchacttList(Map<String, Object> mapParam) {
		
		return inqChacttListMapper.selectInqchacttList(mapParam);
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return inqChacttListMapper.getTotalCount(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectInqchacttDetail(Map<String, Object> mapParam) {
		return inqChacttListMapper.selectInqchacttDetail(mapParam);
	}

	@Override
	public Map<String, Object> saveChacttList(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
//		ParameterGroup dsSpclaList = dataRequest.getParameterGroup("dsSpclaList");
		Iterator<ParameterRow> updatedRows		= dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows		= dsBoardList.getDeletedRows();
//		Iterator<ParameterRow> updatedSpclaRows	= dsSpclaList.getUpdatedRows();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("CLIENA_NM_ENCPT", dsBoardList.getValue("CLIENA_NM_ENCPT"));
			mapUpd.put("CNSLTNT_NM_ENCPT", dsBoardList.getValue("CNSLTNT_NM_ENCPT"));
			inqChacttListMapper.updateChactt(mapUpd);
			
			mapReturn.put("CHRO_NO", mapUpd.get("CHRO_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			inqChacttListMapper.deleteChactt(mapDel);			
			
		}

//		while (updatedSpclaRows.hasNext()) {
//			Map<String, String> mapSpclaUpd = updatedSpclaRows.next().toMap();
//			String spclaMngTrprYn	= mapSpclaUpd.get("SPCLA_MNG_TRPR_YN"); // 특별관리대상자여부
//			
//			// 특별관리대상여부 Y인경우 테이블 insert
//			if ("Y".equals( spclaMngTrprYn)) {
//				String clienaNmEnc = mapSpclaUpd.get("CLIENA_NM_ENCPT");
//				mapSpclaUpd.put("CLIENA_ID"			, mapSpclaUpd.get("CLIENA_ID"));	// 내담자아이디
//				mapSpclaUpd.put("CLIENA_NM_ENCPT"	, clienaNmEnc);						// 내담자명암호화
//				mapSpclaUpd.put("CNSLTNT_ID"		, userId);							// 상담사아이디
//				mapSpclaUpd.put("FRST_RGTR_ID"		, userId);							// 최초등록자아이디			
//				mapSpclaUpd.put("LAST_MDFR_ID"		, userId);							// 최종수정자아이디
//				
//				inqChacttListMapper.insertSpcla(mapSpclaUpd);
//				mapReturn.put("CHRO_NO", mapSpclaUpd.get("CHRO_NO"));
//			}
//		}				
		
		return mapReturn;
	}
	
	@Override
	public void updateChacttMemo(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		inqChacttListMapper.updateChacttMemo(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return inqChacttListMapper.selectSulmun(mapParam);
	}

	@Override
	public void insertCrisis(HttpServletRequest request, DataRequest dataRequest) {
		
		Map<String, Object> mapParam = new HashMap<>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
//		System.out.println("DDD dsBoardList -===========================......... : \n"+dsBoardList.toString());
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		mapParam.put("CHRO_NO", dsBoardList.getValue("CHRO_NO"));
		mapParam.put("CHTT_TYPE_SE_CD", dsBoardList.getValue("CHTT_TYPE_SE_NM"));
		mapParam.put("CRISIS_TYPE_SE_CD", dsBoardList.getValue("CRISIS_TYPE_SE_CD"));
		mapParam.put("CLIENA_ID", dsBoardList.getValue("CLIENA_ID"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		mapParam.put("CNSLTNT_ID", dsBoardList.getValue("CNSLTNT_ID"));
//		System.out.println("DDD insertCrisis ......... : \n"+mapParam.toString());
		int crisBrdCnt = inqChacttListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = inqChacttListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			inqChacttListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			inqChacttListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			inqChacttListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			inqChacttListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
	}

	@Override
	public void updateCase(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		inqChacttListMapper.updateCase(mapParam);
	}
	
	@Override
	public String getChtt(DataRequest dataRequest) {
		// TODO Auto-generated method stub
		ParameterGroup dmChroNo = dataRequest.getParameterGroup("dmChroNo");
		Map<String, String> paramMap = dmChroNo.getSingleValueMap();
		String sessionId = paramMap.get("CHRO_NO");
		String gitpleAppCode = paramMap.get("APP_CODE");
		String strGitpleSecret = paramMap.get("SECRET_CODT");
		String chttType = paramMap.get("CHTT_TYPE");
		String httpUrl = "";
		String response = "";
		String assigneeId = ""; // 상담자 ID
		String mogefId = "";	// 청소년안전망시스템(여가부)ID
		String mogefNm = "";	// 청소년안전망시스템(여가부)회원명
		String rtnMsg = "";
		try {
			Map<String, String> cntMap = gitpleEventMapper.selectGitpleCnt(sessionId);
			String sCnt = String.valueOf(cntMap.get("CNT"));
			int aybCnt = Integer.parseInt(sCnt);
			if(aybCnt > 0) {
				rtnMsg = "입력하신 채팅방 번호는 이미 정상 연계되었습니다.";
				return rtnMsg;
			}
			///////////////////////////////////////////////////////////세션 정보 가져오기
			httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/sessions/"+sessionId;
			String sessionResponse = gitpleUrlProcess(httpUrl, gitpleAppCode, strGitpleSecret);
			JSONObject json = stringToJson(sessionResponse);
			
			if(!"".equals(json.get("assigneeId")) && json.get("assigneeId") != null) {
				assigneeId = json.get("assigneeId").toString(); // 상담자 ID
			}
			
			if(null != json.get("inprogressTime")) {
				
				String reason = json.get("reason").toString(); // 세션 종료 사유
				
				JSONObject sessionJson = new JSONObject();
				String ipAddr = "";
				
				String userId = json.get("userId").toString(); // 내담자 ID
				String userNm = ""; //내담자명
				String inorigressTime = json.get("inprogressTime").toString(); // 상담시작 시간
				String openTime = json.get("openTime").toString(); // 상담대기 시간
				String closeTime = json.get("closeTime").toString(); // 상담종료 시간
				
				if(!"".equals(json.get("system")) && json.get("system") != null) {
					sessionJson = (JSONObject)json.get("system");
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
				//String assigneeEncNm = assigneeNm; // 상담사 명 암호화
				String agentIdentifier = jsonAgent.get("identifier").toString(); // 상담사 로그인ID
				// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@상담사 명 가져오기 끝
				
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!내담자 정보 가져오기
				httpUrl = "https://api-cyber1388.gitple.biz/v1/exports/users/"+userId;
				String jsonUserInfo = gitpleUrlProcess(httpUrl, gitpleAppCode, strGitpleSecret); // 내담자정보 jsonString
				JSONObject jsonUser = stringToJson(jsonUserInfo);
				String userPhone = "";
				String userEncEmail = "";
				String sxdcSeCd = ""; 
				if(!"".equals(jsonUser.get("name")) && jsonUser.get("name") != null) { // name은 optional. "name"이라는 key가 없을 수도 있다.
					userNm = jsonUser.get("name").toString();
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
					mogefNm = map.get("FLNM_ENCPT"); // gitpleEventMapper.getMogefId(agentIdentifier)을 통해 가져온 암호화된 성명입니다.
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
				ayb200Map.put("CLIENA_NM_ENCPT", userNm); // 내담자명
				ayb200Map.put("CLIENA_TELNO", userPhone); // 내담자 전화번호
				ayb200Map.put("CLIENA_EML_ADDR_ENCPT", userEncEmail); // 내담자 이메일 암호화
				ayb200Map.put("CNTN_IP_ADDR", ipAddr); // 접속아이피주소 수정 요망
				
				
				ayb200Map.put("END_CS_DTL_CN", reason); // 세션 종료 사유(상담 종료 사유)
				ayb200Map.put("CHTT_END_SE_CD", reason); // 채팅종료구분코드
				ayb200Map.put("CHTT_DSCSN_BGNG_DT", convertDate(inorigressTime, "P")); // 상담시작 시간
				ayb200Map.put("SXDC_SE_CD", sxdcSeCd); // 성별
				gitpleEventMapper.insertAyb200Data(ayb200Map); // AYB200 테이블 저장
				
				Map<String, String> ayb201Map = new HashMap<>();
				ayb201Map.put("GITPLE_EVENTA_RCPTN_CN"   , sessionResponse);
				ayb201Map.put("GITPLE_EVENTA_TYPE_NM", "session_state_close");
				ayb201Map.put("USER_ID", assigneeId);
				gitpleEventMapper.insertAyb201Data(ayb201Map); // 이벤트 정보 AYB201 테이블 저장
				rtnMsg = "채팅 내역 호출에 성공하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnMsg = "채팅 내역 호출에 실패하였습니다.";
		}
		return rtnMsg;
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
}
