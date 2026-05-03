/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.outsdsrvyptcptn.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.outsdsrvyptcptn.service.OutsdSrvyPtcptnService;
import isry.itgcms.syscmmn.url.service.ShortUrlService;
import isry.itgcms.util.EgovHttpRequestHelper;

/**
 * @파일명        : OutsdSrvyPtcptnServiceImpl.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 11. 21.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 11. 21.
 * @수정내용      :
 * -
 * -
 */
@Service("outsdSrvyPtcptnService")
public class OutsdSrvyPtcptnServiceImpl implements OutsdSrvyPtcptnService {


	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private final String domain = EgovProperties.getProperty("globals" , "srvy.recv.url");

	/* 모바일 설문참여 메시지 map */
	private final Map<String, String> messageTmplMap = new HashMap<>();

	private static String TMPL_MSG_FMT = "%s\n%s";

	@Resource(name = "shortUrlService")
	private ShortUrlService shortUrlService;

	/**
	 * @Method명   : getSendMsg
	 * @param outsdSrvyPtcptnParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 21.
	 * @Method설명 :
	 */
	@Override
	public String getSendMsg(Map<String, String> outsdSrvyPtcptnParam) {
		//String msgTemplate = "[설문지작성]\n설문지 참여를 해주세요.\n";

		//String linkUrl = "/isry/csemd/aplcntpage/aplcnttrprmng/mblaSrvyPtcptn.do";

		log.debug("outsdSrvyPtcptnService.getSendMsg");

		String msgTemp = outsdSrvyPtcptnParam.get("MSG_TEMP");
		messageTmplMap.put("MSG_CONTENT", msgTemp);
		messageTmplMap.put("PATH", outsdSrvyPtcptnParam.get("PATH"));


		HttpServletRequest request = EgovHttpRequestHelper.getCurrentRequest();

		String msgContent = messageTmplMap.get("MSG_CONTENT");
    	String urlPath = messageTmplMap.get("PATH");
		//String domain = "";
		//int serverPort = request.getServerPort();

		// 운영url: https://gov.youthsafety.go.kr
//		if (request.getServerName().startsWith("gov.youthsafety.go.kr")) {
//			domain = "https://" + request.getServerName() + request.getContextPath();
//		} else {
//			if (serverPort > 0 && (serverPort != 80 || serverPort != 443)) {
//				domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//			} else {
//				domain = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
//			}
//		}


		String param = "userId=" + outsdSrvyPtcptnParam.get("USER_ID").toString() + "&qustnbMngNo=" + outsdSrvyPtcptnParam.get("QUSTNB_MNG_NO").toString() + "&untTaskwkSeCd=" + outsdSrvyPtcptnParam.get("UNT_TASKWK_SE_CD").toString() + "&sxdcSeCd=" + outsdSrvyPtcptnParam.get("SXDC_SE_CD").toString() + "&mngrYn=N" + "&cntnCours=M";

		log.debug("### Request domain : " + domain);

    	String linkUrl = shortUrlService.createShortUrl(domain, urlPath, "POST", param);

    	log.debug("### Request ServerURL : " + linkUrl);

    	//linkUrl = // get shot cut url

    	String sendMsg = String.format(TMPL_MSG_FMT, msgContent, linkUrl);
    	int length = sendMsg.getBytes().length;

    	log.debug("getSendMsg() length [{}] ===\n{}" , length, sendMsg);

    	return sendMsg;
	}

	/**
	 *
	 * @Method명   : getSrvySendMsg
	 * @param paramMap
	 * @return
	 * @작성자     : Tae.Soo.Song
	 * @작성일     : 2023. 8. 16.
	 * @Method설명 : 문자 발송 및 수신 정보를 입력한다.
	 */
	@Override
	public String getSrvySendMsg(Map<String, String> paramMap) {
		String sendMsg = "";

		//String msgTemplate = "[설문지작성]\n설문지 참여를 해주세요.\n";

		//String linkUrl = "/isry/csemd/aplcntpage/aplcnttrprmng/mblaSrvyPtcptn.do";

		log.debug("outsdSrvyPtcptnService.getSendMsg");

		String msgTemp = paramMap.get("MSG_TEMP");
		messageTmplMap.put("MSG_CONTENT", msgTemp);
		messageTmplMap.put("PATH", paramMap.get("PATH"));


		HttpServletRequest request = EgovHttpRequestHelper.getCurrentRequest();

		String msgContent = messageTmplMap.get("MSG_CONTENT");
    	String urlPath = messageTmplMap.get("PATH");

		String param = "userId=" + paramMap.get("USER_ID").toString()
				+ "&qustnbMngNo=" + paramMap.get("QUSTNB_MNG_NO").toString()
				+ "&qustnbTmptMngNo=" + paramMap.get("QUSTNB_TMPT_MNG_NO").toString()
				+ "&caseMngNo=" + paramMap.get("CASE_MNG_NO").toString()
				+ "&caseMngOdrno=" + paramMap.get("CASE_MNG_ODRNO").toString()
				+ "&trprInfoNo=" + paramMap.get("TRPR_INFO_NO").toString()
				+ "&addParam1=" + paramMap.get("ADD_PARAM1").toString()
				+ "&addParam2=" + paramMap.get("ADD_PARAM2").toString()
				+ "&addParam3=" + paramMap.get("ADD_PARAM3").toString()
				+ "&untTaskwkSeCd=" + paramMap.get("UNT_TASKWK_SE_CD").toString()
				+ "&sxdcSeCd=" + paramMap.get("SXDC_SE_CD").toString()
				+ "&mngrYn=N" + "&cntnCours=M";

		log.debug("### Request domain : " + domain);

    	String linkUrl = shortUrlService.createShortUrl(domain, urlPath, "POST", param);

    	log.debug("### Request ServerURL : " + linkUrl);

    	//linkUrl = // get shot cut url

    	sendMsg = String.format(TMPL_MSG_FMT, msgContent, linkUrl);
    	int length = sendMsg.getBytes().length;

    	log.debug("getSendMsg() length [{}] ===\n{}" , length, sendMsg);

		return sendMsg;
	}

}
