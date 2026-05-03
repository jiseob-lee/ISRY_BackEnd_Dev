/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.service.RqstSrvcInqService;
import isry.itgcms.syscmmn.rest.service.RestService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : RqstSrvcInqServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("rqstSrvcInqService")
public class RqstSrvcInqServiceImpl extends EgovAbstractServiceImpl implements RqstSrvcInqService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	private static final String REQUEST_URL = "http://10.188.131.225:25000/WS/";
	
	@Resource(name="restService")
	private RestService restService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectRqstSrvcList
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Kwon.Min.Seo
	 * @작성일 	 : 2022. 10. 25.
	 * @Method설명 : 의뢰서비스 조회(복지부 연계)
	 */	
	@Override
	public Map<String, Object> selectRqstSrvcList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String userInstNo = "";
		String userInstTypeSeCd = "";
		
		//세션에서 가져온 유저ID 설정
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userInstNo = loginVO.getInstNo().toString();
			userInstTypeSeCd = loginVO.getInstTypeSeCd();
		}
		LOGGER.debug("기관번호 : " + userInstNo);
		LOGGER.debug("기관유형구분코드 : " + userInstTypeSeCd);
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		
		
//		Map<String, Object> SrvcMap = new HashMap<>();
//		SrvcMap.put("userInstNo", userInstNo);
//		SrvcMap.put("userInstTypeSeCd", userInstTypeSeCd);
		
		List<Map<String, Object>> cnrsResrceList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> reqMap    = new HashMap<>();
		
		
		reqMap.put("userInstNo", userInstNo);
		reqMap.put("userInstTypeSeCd", userInstTypeSeCd);
		
		
		
		
		
		
		return reqMap;
	}
	
	/**
	 * @Method명 : getCnrsResrceLink
	 * @param	 : rqstData
	 * @return	 : List<Map<String, Object>>
	 * @throws Exception
	 * @작성자 	 : Kwon.Min.Seo
	 * @작성일	 : 2022.10. 26. 
	 * @Method설명 : 공유자원 복지부 연계
	 */
	public List<Map<String, Object>> getCnrsResrceLink(Map<String, Object> reqMap) throws Exception {
		
		LOGGER.debug("=========== 공유자원 연계 START ===========");

		String intrfcID = "INFIF_IR_SSI_WS_08"; //청소년특별지원 연계

		List<Map<String, Object>> cnrsResrceList = new ArrayList<Map<String,Object>>();

//		Map<String, Object> headerMap = (HashMap)reqMap.get("Header");
//		headerMap.put("serviceName", "공유자원 연계");
//
//		reqMap.put("Header", headerMap);

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		json = mapper.writeValueAsString(reqMap);

		String resResult = restService.sendREST(REQUEST_URL + intrfcID, json);
		LOGGER.debug("공유자원 연계 응답 결과 : " + resResult);

		JSONParser jParser = new JSONParser(); 					   //JSON Parser 객체 생성. parser를 통해 파싱
		JSONObject jObj    = (JSONObject)jParser.parse(resResult); //Parser로 문자열 데이터를 JSON 데이터로 변환
		JSONArray  arrList = (JSONArray)jObj.get("SPSRV_DTLS");

		for(int i=0; i<arrList.size(); i++) {
			Map<String, Object> attMap = new HashMap<>();

			JSONObject jsObj = (JSONObject)arrList.get(i);
			Iterator iterator = jsObj.keySet().iterator();
			while(iterator.hasNext()) {
				String key = (String)iterator.next();
				attMap.put(key, jsObj.get(key));
			}

			cnrsResrceList.add(attMap);
		}

		LOGGER.debug("=========== 결과  → " + cnrsResrceList);
		LOGGER.debug("=========== 공유자원 연계 END ===========");

		return cnrsResrceList;

	}
	
}
