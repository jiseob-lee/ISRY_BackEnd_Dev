/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.email.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.syscmmn.email.service.EmailService;
import isry.itgcms.syscmmn.email.vo.EmailMessageVO;
import isry.itgcms.sysmgmt.file.mapper.MgmtFileMapper;
import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.StringUtil;
import isry2.itgcms.syscmmn.email.mapper.EmailMapper;

/**
 * @파일명        : EmailServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 7. 18. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 7. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String strWasFileBasePath = EgovProperties.getProperty("globals", "isry.globals.wasupload.file.folder");
	
	@Resource(name = "emailMapper")
	private EmailMapper emailMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "mgmtFileService")
	private MgmtFileService mgmtFileService;

	@Resource(name = "mgmtFileMapper")
	private MgmtFileMapper mgmtFileMapper;
	
	@Override
	public void insertEmail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmParam");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsReceiver");

		Map<String, String> paramMap1 = paramGroup1.getSingleValueMap();
		
		if (userId == null || "".equals(userId)) {
			userId = paramMap1.get("userId");
		}
		
		String atfino = paramMap1.get("atfino");
		List<Map<String, Object>> fileList = null;
		
		if (atfino != null && !"".equals(atfino)) {
			Map<String, String> mapParam = new HashMap<>();
			mapParam.put("ATFINO", atfino);
			fileList = mgmtFileService.selectCmnFileList(mapParam);
		}
		
		int atcSet = 0;
		if (fileList != null && fileList.size() > 0) {
			atcSet = 1;
		}
		
		String contents = paramMap1.get("contents");
		String sender = paramMap1.get("sender");
		String senderName = paramMap1.get("senderName");
		String title = paramMap1.get("title");
		String sender1 = "";
		
		log.info("#### sender : " + sender);
		log.info("#### senderName : " + senderName);
		
		if (sender != null && sender.indexOf("<") > -1 && sender.indexOf(">") > -1) {
			sender1 = sender.substring(sender.indexOf("<") + 1, sender.indexOf(">"));
		} else {
			sender1 = sender;
		}
		
		//if (senderName != null && !"".equals(senderName.trim()) && sender1 != null && !"".equals(sender1.trim())) {
			//sender1 = "\"" + senderName.trim() + "\"<" + sender1 + ">";
		//} else if (sender1 == null || "".equals(sender1.trim())) {
			//sender1 = "\"관리자\"<no-reply@1388.kr>";
		//} else {
			//sender1 = "<" + sender1 + ">";
		//}
		
		if (sender1 == null || "".equals(sender1.trim())) {
			sender1 = "<no-reply@1388.kr>";
		} else {
			sender1 = "<" + sender1 + ">";
		}
		
		log.info("#### sender1 : " + sender1);
		
		//sender = "\"관리자\"<no-reply@1388.kr>";
		
		String reserveYN = paramMap1.get("reserveYN");
		String reserveTime = paramMap1.get("reserveTime");
		String reserveTime1 = "";
		
		if (sender1 == null || "".equals(sender1) || contents == null || "".equals(contents)) {
			return;
		}
		
		List<Map<String, String>> listReceiver = paramGroup2.getAllRowList();
		
		if (listReceiver == null) {
			log.info("#### listReceiver is null.");
			return;
		} else {
			log.info("#### listReceiver size : " + listReceiver.size());
		}
		
		Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("SUBJECT", title);
		paramMap.put("CONTENT", contents);
		paramMap.put("MAILFROM", sender1);
		paramMap.put("REPLYTO", sender1);
		paramMap.put("ERRORSTO", sender1);
		paramMap.put("ATCSET", atcSet);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String currentTime = dtf.format(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(currentTime));
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		cal.add(Calendar.DAY_OF_MONTH, 10);
		String currentTime1 = sdf.format(cal.getTime());
		
		if ("R".equals(reserveYN)) {
			try {
				cal.setTime(sdf.parse(reserveTime));
			} catch (ParseException e) {
				log.debug(e.getMessage());
			}
			cal.add(Calendar.DAY_OF_MONTH, 10);
			reserveTime1 = sdf.format(cal.getTime());
		}
		
		if ("R".equals(reserveYN)) {
			paramMap.put("SDATE", reserveTime);
			paramMap.put("TDATE", reserveTime1);
		} else {
			paramMap.put("SDATE", currentTime);
			paramMap.put("TDATE", currentTime1);
		}
		
		
		for (int i=0; i < listReceiver.size(); i++) {
			Map<String, String> mapReceiver = listReceiver.get(i);
			String emailReceiver = mapReceiver.get("EML_ADDR");
			String emailReceiverName = mapReceiver.get("FLNM");
			String receiver = "";
			
			Integer mailIdx = emailMapper.selectMailIdx();
			
			log.info("#### emailReceiver : " + emailReceiver);
			log.info("#### emailReceiverName : " + emailReceiverName);
			
			if (emailReceiver == null || "".equals(emailReceiver)) {
				continue;
			}
			
			if (emailReceiverName != null && !"".equals(emailReceiverName)) {
				receiver = "\"" + emailReceiverName + "\"<" + emailReceiver + ">";
			} else {
				receiver = "<" + emailReceiver + ">";
			}
			
			log.info("#### email receiver : " + receiver);
			
			paramMap.put("MAILIDX", mailIdx);
			paramMap.put("QRY", "SSV:" + emailReceiver);
			paramMap.put("MAILTO", receiver);
			
			paramMap.put("userId", userId);

			if (atcSet > 0) {  // 첨부파일이 있는 경우
				for (int j=0; j < fileList.size(); j++) {
					
					Map<String, Object> fileMap = fileList.get(j);
					
					fileMap.put("MAILIDX", mailIdx);
					
					String filename = String.valueOf(fileMap.get("REAL_FILE_NM"));
					String filename1 = filename.substring(0, filename.lastIndexOf("."));
					String ext = filename.substring(filename.lastIndexOf("."));
					int len = 63;
					len -= ext.length();
					filename1 = StringUtil.truncateWhenUTF8(filename1, len);
					fileMap.put("FILENAME", filename1 + ext);
					
					String strFilePath = "";  // 다운로드받을 파일의 경로
					String serverPath = String.valueOf(fileMap.get("STRG_COURS_NM"));
					if (!serverPath.endsWith("/") && !serverPath.endsWith("\\")) {
						serverPath += File.separator;
					}
					
					strFilePath = "file:///nas_data/was/" + serverPath 
							+ fileMap.get("STRG_FILE_NM");  // 다운로드 받을 파일의 경로
					
					while (strFilePath.contains("..")) {
						strFilePath = strFilePath.replaceAll("\\.\\.", "");
					}
					
					//String filecontent = encodeFileToBase64Binary(strFilePath);
					fileMap.put("FILECONTENT", strFilePath);
					
					emailMapper.insertEmailFile(fileMap);
				}
			}
			
			emailMapper.insertEmail(paramMap);
		}
	}
	
	//private String encodeFileToBase64Binary(String fileName) throws IOException {
	    //File file = new File(fileName);
	    //byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
	    //return new String(encoded, StandardCharsets.UTF_8);
	//}
	
	@Override
	public void insertEmailVO(HttpServletRequest request, EmailMessageVO emailMessageVO) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		if (userId == null || "".equals(userId)) {
			userId = emailMessageVO.getUserId();
		}
		
		String contents = emailMessageVO.getContents();
		String sender = emailMessageVO.getSender();
		String senderName = emailMessageVO.getSenderName();
		String title = emailMessageVO.getTitle();
		String sender1 = "";
		
		log.info("#### sender : " + sender);
		log.info("#### senderName : " + senderName);
		
		if (sender != null && sender.indexOf("<") > -1 && sender.indexOf(">") > -1) {
			sender1 = sender.substring(sender.indexOf("<") + 1, sender.indexOf(">"));
		} else {
			sender1 = sender;
		}
		
		//if (senderName != null && !"".equals(senderName.trim()) && sender1 != null && !"".equals(sender1.trim())) {
			//sender1 = "\"" + senderName.trim() + "\"<" + sender1 + ">";
		//} else if (sender1 == null || "".equals(sender1.trim())) {
			//sender1 = "\"관리자\"<no-reply@1388.kr>";
		//} else {
			//sender1 = "<" + sender1 + ">";
		//}
		
		if (sender1 == null || "".equals(sender1.trim())) {
			sender1 = "<no-reply@1388.kr>";
		} else {
			sender1 = "<" + sender1 + ">";
		}
		
		log.info("#### sender1 : " + sender1);
		
		//sender = "\"관리자\"<no-reply@1388.kr>";
		
		String reserveYN = emailMessageVO.getReserveYN();
		String reserveTime = emailMessageVO.getReserveTime();
		String reserveTime1 = "";
		
		if (sender1 == null || "".equals(sender1) || contents == null || "".equals(contents)) {
			return;
		}
		
		List<Map<String, String>> listReceiver = emailMessageVO.getListReceiver();
		
		if (listReceiver == null) {
			log.info("#### listReceiver is null.");
			return;
		} else {
			log.info("#### listReceiver size : " + listReceiver.size());
		}
		
		Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("SUBJECT", title);
		paramMap.put("CONTENT", contents);
		paramMap.put("MAILFROM", sender1);
		paramMap.put("REPLYTO", sender1);
		paramMap.put("ERRORSTO", sender1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String currentTime = dtf.format(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(currentTime));
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		cal.add(Calendar.DAY_OF_MONTH, 10);
		String currentTime1 = sdf.format(cal.getTime());
		
		if ("R".equals(reserveYN)) {
			try {
				cal.setTime(sdf.parse(reserveTime));
			} catch (ParseException e) {
				log.debug(e.getMessage());
			}
			cal.add(Calendar.DAY_OF_MONTH, 10);
			reserveTime1 = sdf.format(cal.getTime());
		}
		
		if ("R".equals(reserveYN)) {
			paramMap.put("SDATE", reserveTime);
			paramMap.put("TDATE", reserveTime1);
		} else {
			paramMap.put("SDATE", currentTime);
			paramMap.put("TDATE", currentTime1);
		}
		
		
		for (int i=0; i < listReceiver.size(); i++) {
			Map<String, String> mapReceiver = listReceiver.get(i);
			String emailReceiver = mapReceiver.get("EML_ADDR");
			String emailReceiverName = mapReceiver.get("FLNM");
			String receiver = "";
			
			log.info("#### emailReceiver : " + emailReceiver);
			log.info("#### emailReceiverName : " + emailReceiverName);
			
			if (emailReceiver == null || "".equals(emailReceiver)) {
				continue;
			}
			
			if (emailReceiverName != null && !"".equals(emailReceiverName)) {
				receiver = "\"" + emailReceiverName + "\"<" + emailReceiver + ">";
			} else {
				receiver = "<" + emailReceiver + ">";
			}
			
			log.info("#### email receiver : " + receiver);
			
			paramMap.put("QRY", "SSV:" + emailReceiver);
			paramMap.put("MAILTO", receiver);
			
			paramMap.put("userId", userId);

			emailMapper.insertEmail(paramMap);
		}
	}

	@Override
	public Integer selectEmailHistoryCount(HttpServletRequest request, Map<String, Object> map) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		map.put("SESSION_USER_ID", userId);
		
		map.put("GROUP_AUTHRT_SE_CD", loginVO == null ? "" : loginVO.getGroupAuthrtSeCd());
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("USER_NAME_ENCPT", scpDb.scpEncB64((String)map.get("USER_NAME")));
		
		return emailMapper.selectEmailHistoryCount(map);
	}
	
	@Override
	public List<Map<String, Object>> selectEmailHistory(HttpServletRequest request, Map<String, Object> map) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		map.put("SESSION_USER_ID", userId);
		
		map.put("GROUP_AUTHRT_SE_CD", loginVO == null ? "" : loginVO.getGroupAuthrtSeCd());
		
		//map.put("USER_NAME_ENCPT", scpDb.scpEncB64((String)map.get("USER_NAME")));
		
		List<Map<String, Object>> list1 = emailMapper.selectEmailHistory(map);
		List<Map<String, Object>> list2 = new ArrayList<>();
		
		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				
				Map<String, String> mailTo = parseEmailAddr((String)map1.get("MAILTO"));
				Map<String, String> mailFrom = parseEmailAddr((String)map1.get("MAILFROM"));
				
				//map1.put("USER_NAME", scpDb.scpDecB64((String)map1.get("USER_NAME")));
				map1.put("USER_NAME_MASKING", Masking.nameMasking((String)map1.get("USER_NAME")));

				map1.put("USER_NAME_TO", mailTo.get("mailTo"));
				map1.put("USER_NAME_TO_MASKING", Masking.nameMasking(mailTo.get("mailTo")));

				map1.put("MAILTO_ONLY", mailTo.get("mailAddr"));
				map1.put("MAILFROM_ONLY", mailFrom.get("mailAddr"));
				
				map1.put("MAILTO_MASKING", Masking.emailMasking(mailTo.get("mailAddr")));
				map1.put("MAILFROM_MASKING", Masking.emailMasking(mailFrom.get("mailAddr")));
				
				list2.add(map1);
			}
		}
		
		return list2;
	}
	
	private Map<String, String> parseEmailAddr(String emailAddr) {

		Map<String, String> map = new HashMap<>();
		
		if (emailAddr == null || "".equals(emailAddr)) {
			return map;
		}
		
		String c = emailAddr;
		String mailAddr = "";
		String mailTo = "";
		
		if (c.indexOf("<") > -1) {
			mailAddr = c.substring(c.indexOf("<") + 1, c.indexOf(">"));
			mailTo = c.indexOf("<") == 0 ? "" : c.substring(1, c.indexOf("<") - 1);
		} else {
			mailAddr = c;
		}
		
		map.put("mailAddr", mailAddr);
		map.put("mailTo", mailTo);
		
		return map;
	}
	
	/*
	public static void main(String[] args) {
		String email = "<master@iwnetworks.co.kr>";
		EmailServiceImpl s = new EmailServiceImpl();
		s.parseEmailAddr(email);
	}
	*/
	
	@Override
	public List<Map<String, Object>> selectEmailDetailAttachList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmData");
		
		Map<String, String> paramMap1 = paramGroup1 == null ? null : paramGroup1.getSingleValueMap();
		
		String seqIdx = paramMap1 == null ? null : paramMap1.get("SEQIDX");
		
		if (seqIdx == null || "".equals(seqIdx)) {
			return null;
		}
		
		List<Map<String, Object>> encList = emailMapper.selectEncList(Integer.parseInt(seqIdx));
		
		if (encList == null || encList.size() == 0) {
			return null;
		}
		
		List<String> contentList = new ArrayList<>();
		
		for (int i=0; i < encList.size(); i++) {
			String content = String.valueOf(encList.get(i).get("CONTENT"));
			if (content.length() > 21) {
				content = content.substring(21);
				contentList.add(content);
			}
		}
		
		List<Map<String, Object>> attachList = new ArrayList<>();
		if (contentList != null && contentList.size() > 0) {
			attachList = mgmtFileMapper.selectEmailAttachList(contentList);
		}
		
		if (attachList == null || attachList.size() == 0) {
			return null;
		}
		
		List<Map<String, Object>> attachList2 = new ArrayList<>();
		
		for (int i=0; i < attachList.size(); i++) {
			
			Map<String, Object> map = attachList.get(i);

			String fileinfo = "";
			
			String strgFileNm = String.valueOf(map.get("STRG_FILE_NM"));
			
			for (int j=0; j < encList.size(); j++) {
				String content = String.valueOf(encList.get(j).get("CONTENT"));
				if (content.endsWith(strgFileNm)) {
					fileinfo = String.valueOf(encList.get(j).get("FILEINFO"));
					break;
				}
			}
			
			map.put("FILEINFO", fileinfo);
			
			attachList2.add(map);
		}
		
		return attachList2;
	}
	
	// 이메일 예약 발송 취소
	@Override
	public void processEmailCancel(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmData");
		
		Map<String, String> paramMap1 = paramGroup1 == null 
				? null : paramGroup1.getSingleValueMap();
		
		String seqIdx = paramMap1 == null ? null : paramMap1.get("SEQIDX");
		
		if (seqIdx == null || "".equals(seqIdx)) {
			return;
		}
		
		int count = emailMapper.deleteDmailInfo(Integer.parseInt(seqIdx));
		if (count > 0) {
			emailMapper.deleteEncDmail(Integer.parseInt(seqIdx));
		}
	}

}
