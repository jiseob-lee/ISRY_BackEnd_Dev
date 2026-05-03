/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cmmn.service.impl;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.google.common.collect.ImmutableMap;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.mapper.CounsMapper;
import isry.couns.cmmn.service.CounsService;
import isry.couns.cmmn.util.CounsUtils;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService;
import isry2.itgcms.syscmmn.email.mapper.EmailMapper;
import isry2.itgcms.syscmmn.sms.mapper.SmsMapper;

/**
 * @파일명        : CounsServiceImpl.java
 * @프로그램 설명 : 청소년상담 공통 서비스 (구현체)
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 12. 28. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 12. 28.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("counsService")
public class CounsServiceImpl extends IsryBaseServiceImpl implements CounsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CounsServiceImpl.class);
	
	@Resource(name = "counsMapper")
	private CounsMapper mapper;
	
	@Resource(name = "emailMapper")
	private EmailMapper emailMapper;
	
	@Resource(name = "smsMapper")
	private SmsMapper smsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/** 
	 * @Method명   : selectUnitTaskWorkSeCode
	 * @param deptCd	부서코드 값
	 * @return		UNT_TASKWK_SE_CD (단위업무구분코드)
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 단위업무구분코드 조회
	 */
	@Override
	public String selectUnitTaskWorkSeCode(String deptCd) throws Exception {
		return mapper.selectUnitTaskWorkSeCode(ImmutableMap.of("deptCd", deptCd));
	}
	
	/**
	 * @Method명   : selectOrgDeptCombo
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 기관별 부서 목록 조회 (콤보박스)
	 */
	@Override
	public List<Map<String, Object>> selectOrgDeptCombo(HttpServletRequest request) throws Exception {
		
		String selectedCmbUntTaskwk = "";		// session의 선택한단위업무구분코드
//		String userGroupAuthrtSeCd = "";		// session의 그룹권한구분코드
//		int userInstNO = 0;						// session의 기관번호
		
		// 요청 Parameter map
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && !"".equals(loginVO.getUntTaskwk()) && !"".equals(loginVO.getGroupAuthrtSeCd()) && !"".equals(loginVO.getInstNo())) {
			selectedCmbUntTaskwk = loginVO.getUntTaskwk();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		LOGGER.debug("선택된 단위 업무 구분코드 ::: " + selectedCmbUntTaskwk);
		
		if ("U11".equals(selectedCmbUntTaskwk)) {
			
			// '청소년사이버상담' 기관 번호 Fix!! ▶ 기관이 변경되면 꼭 수정 필요!!
			mapParam.put("instNo", "1000000947");
			mapParam.put("unitCode", selectedCmbUntTaskwk);
			
//			mapParam.put("unitCode", selectedCmbUntTaskwk);
//			
//			// 1xx : 여성가족부, 2xx : 중앙관리기관
//	        if (userGroupAuthrtSeCd.charAt(0) == '1' || userGroupAuthrtSeCd.charAt(0) == '2') {
//	        	mapParam.put("instNo", "1000000947");
//	        // 3xx : 기관
//	        } else if (userGroupAuthrtSeCd.charAt(0) == '3'){
//	        		mapParam.put("instNo", userInstNO);
//	        // 이외
//	        } else {
//	        	throw new AppWorksException("접근 권한이 없습니다. 권한 신청을 해주세요.", Alert.ERROR);
//	        }
			
		} else {
			throw new AppWorksException("잘못된 접근입니다. 다시 접속해주세요.", Alert.ERROR);
		}
		
		return mapper.selectOrgDeptCombo(mapParam);
	}
	
	/**
	 * @Method명   : deleteCnsltntAsgn
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 2. 
	 * @Method설명 : 비밀게시판 상담자 할당 Delete
	 */
	@Override
	public void deleteCnsltntAsgn(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::: " + paramMap);
		
		try {
			mapper.deleteCnsltntAsgn(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method명   : processAnsCmptnAutoSndng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @param 	   : mapParam
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 5. 
	 * @Method설명 : 각 게시글 답변 완료 자동 발송
	 */
	@Override
	public void processAnsCmptnAutoSndng(HttpServletRequest request, DataRequest dataRequest, Map<String, String> mapParam) throws Exception {
		String loginId = "";			// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}

		// 1. 답변 완료 자동 발송 정보
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmAutoSndngInfo");
		Map<String, String> dmAutoSndngInfo = paramGroup.getSingleValueMap();
		dmAutoSndngInfo.putAll(mapParam);
		dmAutoSndngInfo.put("loginId", loginId);
		
		LOGGER.debug("dmAutoSndngInfo ::: " + dmAutoSndngInfo);
		
		String bbscttTypeSeCd = dmAutoSndngInfo.get("BBSCTT_TYPE_SE_CD");
		
		String chrctr_yn = dmAutoSndngInfo.get("CHRCTR_YN");
		String email_addr = dmAutoSndngInfo.get("RECEIVER_EML");
		String mbl_telno = dmAutoSndngInfo.get("RECEIVER_TELNO");
		
		// 2. 고객의소리 or 사이버상담후기인 경우
		if ("09".equals(bbscttTypeSeCd) || "12".equals(bbscttTypeSeCd)) {
			// 2-1. 자동 메일 발송
//			2023.06.12 - 하이퍼링크 오류 미해결로 인한 주석처리
			if (email_addr != null && !"".equals(email_addr)) {
				insertAutoEmailSndng(dmAutoSndngInfo);
			}

			// 2-2. 희망여부 값에 따른 자동 문자 발송
			if ("Y".equals(chrctr_yn)) {

				if (mbl_telno != null && !"".equals(mbl_telno)) {
					insertAutoLmsSndng(dmAutoSndngInfo);
				}
			}
		// 3. 이외의 경우 (비밀게시판, 솔로봇게시판, 솔로봇댓글, 고민해결백과, 웹심리검사댓글)
		} else {
			// 3-1. 문자 희망 여부 확인
			if ("Y".equals(chrctr_yn)) {
				// 3-1-1. 자동 메일 발송
//				2023.06.12 - 하이퍼링크 오류 미해결로 인한 주석처리
				if (email_addr != null && !"".equals(email_addr)) {
					insertAutoEmailSndng(dmAutoSndngInfo);
				}

				// 3-1-2. 자동 문자 발송
				if (mbl_telno != null && !"".equals(mbl_telno)) {
					insertAutoLmsSndng(dmAutoSndngInfo);
				}
			}
		}
	}

	/**
	 * @Method명   : insertAutoEmailSndng
	 * @param 	   : mapParam
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 5. 
	 * @Method설명 : 자동 메일 발송
	 */
	public void insertAutoEmailSndng(Map<String, String> mapParam) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String bbscttTypeSeCd = mapParam.get("BBSCTT_TYPE_SE_CD");
		String loginId = mapParam.get("loginId");
		
		String sender = "<no-reply@1388.kr>";
		String senderName = "";
		String title = "";
		String contents = "";
		
		// 사이버상담후기(게시글유형구분코드 = "12")인 경우 StringUtil.nullConvert(mapParam.get("RETE_CN"));
		if ("12".equals(bbscttTypeSeCd)) {
			title = "사이버상담센터 상담후기 답변이 도착하였습니다(한국청소년상담복지개발원)";
			contents = mapParam.get("RETE_CN");
		// 고객의소리(게시글유형구분코드 = "09") 인 경우
		} else if ("09".equals(bbscttTypeSeCd)) {
			title = "사이버상담센터 고객의 소리 답변이 도착하였습니다(한국청소년상담복지개발원)";
			contents = mapParam.get("CMNT_CN");
		} else {
			title = "게시판 상담글에 상담자의 답변이 도착했습니다.";
			contents = "<div style='width:650px; text-align:center; font-size:0.9rem; letter-spacing:-0.05em; line-height:1.5em; color:#414042; font-family:'Nanum Gothic',맑은 고딕,sans-serif; '>"   
                     + "<h2 class='logo'>"
                     + "<div style='padding:40px 20px; text-align:left; border:5px solid #f2f5fd; border-radius:20px; background:#f2f5fd url(http://cyberm.cyber1388.kr/admin/bbs/images/mail/eummail_bg.png) 96% 26px no-repeat; background-size:140px auto;'>"
                     + "<h4 style='margin-top:0; line-height:3rem; font-weight:normal; font-size:2rem;'><strong style='color:#2049b3;'>상담자로부터</strong>의<br>답변이 도착했습니다.</h4>"
                     + "<span style='display:block; width:40px; height:3px; margin-bottom:30px; background:#2049b3;'></span>"
                     + "청소년사이버상담센터(<a href=\"https://www.cyber1388.kr:447\" target=\"_blank\">http://www.cyber1388.kr</a>)에서 확인해 주세요.<br><br>"
//                     + "청소년사이버상담센터(http://www.cyber1388.kr)에서 확인해 주세요.<br><br>"
                     + "<table cellpadding='10' cellspacing='0' style='width:600px; padding:10px; font-size:0.8rem; background:#fff; color:#414042;'>"
                     + "   <tr>"
                     + "       <td>"
                     + "마이페이지 또는 해당게시판에서 확인하실 수 있습니다.<br>"
                     + "       </td>"
                     + "   </tr>"
                     + "</table><br/>"
                     + "<p style='text-align:center;'><a href=\"https://www.cyber1388.kr:447\" style='box-sizing:border-box; display:inline-block; min-width:200px; height:46px; padding:0 20px; margin:20px auto 30px auto; line-height:40px; text-align:center; font-weight:bold; font-size:1rem;color:#2049b3; border:3px solid #2049b3; border-radius:30px; text-decoration:none;' target=\"_blank\">홈페이지 바로가기</a></p>"
//                     + "<p style='text-align:center;'><a style='box-sizing:border-box; display:inline-block; min-width:200px; height:46px; padding:0 20px; margin:20px auto 30px auto; line-height:40px; text-align:center; font-weight:bold; font-size:1rem;color:#2049b3; border:3px solid #2049b3; border-radius:30px; text-decoration:none;'>홈페이지 바로가기</a></p>"
                     + "<p style='margin-bottom:0;text-align:center; font-size:0.8rem;'>이 알림메일은 자동으로 발송되는 메일입니다.</p>"
                     + "<p style='margin-bottom:0;text-align:center; font-size:0.8rem;'>문의사항은 청소년사이버상단센터 고객의소리 게시판을 이용해 주시면 감사하겠습니다.</p>"
                     + "</div>"
                     + "<h6 class='logo'>"
                     + "</div>";
		}
		
		paramMap.put("MAILFROM", sender);
		paramMap.put("REPLYTO", sender);
		paramMap.put("ERRORSTO", sender);
		
		paramMap.put("SUBJECT", title);
		paramMap.put("CONTENT", contents);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String currentTime = dtf.format(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
		Calendar cal = Calendar.getInstance();
		
		try {
			cal.setTime(sdf.parse(currentTime));
		} catch (ParseException e) {
			LOGGER.debug(e.getMessage());
		}
		
		cal.add(Calendar.DAY_OF_MONTH, 10);
		String currentTime1 = sdf.format(cal.getTime());
		
		paramMap.put("SDATE", currentTime);
		paramMap.put("TDATE", currentTime1);
		
		String emailReceiver = mapParam.get("RECEIVER_EML");
		String emailReceiverName = mapParam.get("RECEIVER_NM");
		String receiver = "";
		
		if (emailReceiverName != null && !"".equals(emailReceiverName)) {
			receiver = "\"" + emailReceiverName + "\"<" + emailReceiver + ">";
		} else {
			receiver = "<" + emailReceiver + ">";
		}
		
		paramMap.put("QRY", "SSV:" + emailReceiver);
		paramMap.put("MAILTO", receiver);
		
		paramMap.put("userId", loginId);
		
		LOGGER.debug("paramMap ::: " + paramMap);
		
		emailMapper.insertEmail(paramMap);
	}
	
	/**
	 * @Method명   : insertAutoLmsSndng
	 * @param 	   : mapParam
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 5. 
	 * @Method설명 : 자동 문자 발송
	 */
	public void insertAutoLmsSndng(Map<String, String> mapParam) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String bbscttTypeSeCd = mapParam.get("BBSCTT_TYPE_SE_CD");
		String loginId = mapParam.get("loginId");
		
		String sender = "0516623229";
		String contents = "";
		
		sender = sender.replaceAll("[^\\d]", "");
		
		// 비밀게시판(게시글유형구분코드 = "05") 인 경우
		if ("05".equals(bbscttTypeSeCd)) {
			contents = "게시판 상담글에 대한 상담자의 답변이 도착하였습니다.\n-청소년사이버상담센터-";
		// 솔로봇게시판(게시글유형구분코드 = "01") 인 경우
		} else if ("01".equals(bbscttTypeSeCd)) {
			contents = "솔로봇상담 고민글에 대한 상담자의 답변이 도착하였습니다.\n"
					+ "청소년사이버상담센터 홈페이지의 솔로봇상담게시판에서 답변글을 확인할 수 있습니다.\n-청소년사이버상담센터-";
		// 솔로봇 댓글(게시글유형구분코드 = "02") 인 경우
		} else if ("02".equals(bbscttTypeSeCd)) {
			contents = "솔로봇상담 이용에 대한 도담쌤의 응원 메시지가 도착했습니다.\n"
					+ "청소년사이버상담센터 홈페이지의 솔로봇상담-도담쌤의 응원 메시지에서 메시지를 확인할 수 있습니다.\n-청소년사이버상담센터-";
		// 웹심리검사 댓글(게시글유형구분코드 = "04") 인 경우
		} else if ("04".equals(bbscttTypeSeCd)) {
			contents = "웹심리검사 댓글상담 고민글에 대한 상담자의 답변이 도착하였습니다.\n"
					+ "청소년사이버상담센터 홈페이지의 웹심리검사 상담자답변에서 답변글을 확인할 수 있습니다.\n-청소년사이버상담센터-";
		// 고민해결백과 댓글(게시글유형구분코드 = "14") 인 경우
		} else if ("14".equals(bbscttTypeSeCd)) {
			contents = "고민해결백과 댓글상담 고민글에 대한 상담자의 답변이 도착하였습니다.\n"
					+ "청소년사이버상담센터 홈페이지에서 고민해결백과 상담자답변에서 답변글을 확인할 수 있습니다.\n-청소년사이버상담센터-";
		// 사이버상담후기(게시글유형구분코드 = "12") 인 경우
		} else if ("12".equals(bbscttTypeSeCd)) {
			contents = "사이버상담후기에 대한 상담자의 답변이 도착하였습니다.\n-청소년사이버상담센터-";
		// 고객의소리(게시글유형구분코드 = "09") 인 경우
		} else if ("09".equals(bbscttTypeSeCd)) {
			contents = "고객의 소리 답변이 메일로 발송되었습니다.\n-청소년사이버상담센터-";
		// 이외
		} else {
			contents = "작성한 상담글에 대한 상담자의 답변이 도착하였습니다.\n-청소년사이버상담센터-";
		}
		
		String receiverTelno = mapParam.get("RECEIVER_TELNO");
		String receiverName = mapParam.get("RECEIVER_NM");
		
		receiverTelno = receiverTelno.replaceAll("[^\\d]", "");
		
		Integer mmsContentsInfoSeq = smsMapper.selectMmsContentsInfoSeq();
		
		paramMap.put("mmsContentsInfoSeq", mmsContentsInfoSeq);
		paramMap.put("sender", sender);
		paramMap.put("title", contents.substring(0, 8) + "..");
		paramMap.put("contents", contents);
		paramMap.put("receiver", receiverTelno);
		paramMap.put("receiverName", receiverName);
		paramMap.put("userId", loginId);
		
		LOGGER.debug("paramMap ::: " + paramMap);
		
		smsMapper.insertLMS1(paramMap);
		smsMapper.insertLMS2(paramMap);
	}
}
