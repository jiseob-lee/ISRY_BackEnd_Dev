package isry.itgcms.sysmgmt.userjoin.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.syscmmn.sms.service.SmsMessageVO;
import isry.itgcms.syscmmn.sms.service.SmsService;
import isry.itgcms.sysmgmt.history.mapper.PersonalHistoryMapper;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtAuthGrpService;
import isry.itgcms.sysmgmt.userauth.service.MgmtUserAuthService;
import isry.itgcms.sysmgmt.userjoin.mapper.ApproveUserJoinMapper;
import isry.itgcms.sysmgmt.userjoin.service.ApproveUserJoinService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;

@Service("approveUserJoinService")
public class ApproveUserJoinServiceImpl extends IsryBaseServiceImpl implements ApproveUserJoinService {

	@Resource(name="approveUserJoinMapper")
    private ApproveUserJoinMapper approveUserJoinMapper;

	@Resource(name="personalInfoMapper")
    private PersonalInfoMapper personalInfoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "personalHistoryMapper")
	private PersonalHistoryMapper personalHistoryMapper;
	
	@Resource(name = "mgmtAuthGrpService")
	private MgmtAuthGrpService mgmtAuthGrpService; 
	
	@Resource(name = "smsService")
	private SmsService smsService; 
	
	@Resource(name = "mgmtUserAuthService")
	private MgmtUserAuthService mgmtUserAuthService;
	
	@Override
	public List<Map<String, Object>> selectUserJoin(Map<String, Object> dmSearchMap) throws Exception {
		
		//ScpDb scpDb = new ScpDb();
		
		//dmSearchMap.put("SEARCH_VALUE_ENCPT", scpDb.scpEncB64((String)dmSearchMap.get("SEARCH_VALUE")));
		
		List<Map<String, Object>> list = approveUserJoinMapper.selectUserJoin(dmSearchMap);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//for (int i=0; i < list.size(); i++) {
			//Map<String, Object> map = list.get(i);
			//map.put("USER_NM", scpDb.scpDecB64((String)map.get("USER_NM")));
			//list2.add(map);
		//}
		return list;
	}

	@Override
	public Integer selectUserJoinCount(Map<String, Object> dmSearchMap) throws Exception {
		Integer count = approveUserJoinMapper.selectUserJoinCount(dmSearchMap);
		return count;
	}
	
	@Override
	public void saveGiveBackUserJoin(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmGiveBack");
		Map<String, String> map = param.getSingleValueMap();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String ymd = LocalDate.now().format(formatter);
		
		map.put("USER_ID_USE_SE_CD", "3");
		
		map.put("JOIN_APRV_YMD", ymd);
		
		map.put("JOIN_APRV_YN", "N");
		
		map.put("JOIN_AUTZR_ID", userId);
		map.put("FRST_RGTR_ID", userId);
		map.put("LAST_MDFR_ID", userId);
		map.put("USER_ID2", userId);
		
		approveUserJoinMapper.saveGiveBackUserJoin(map);
		
		map.put("DATAA_CHG_SE_CD", "U");
		personalInfoMapper.insertUserInfoHistory(new HashMap<String, Object>(map));
		
		// 안내 SMS 발송
		Map<String, String> joinMap = approveUserJoinMapper.selectUserJoinInfo(map.get("USER_ID"));
		
		SmsMessageVO smsMessage = new SmsMessageVO();
		
		String senderTelNo = "0516623229";
		
		//ScpDb scpDb = new ScpDb();
		//String mblTelno = scpDb.scpDecB64(joinMap.get("MBL_TELNO_ENCPT"));
		String mblTelno = joinMap.get("MBL_TELNO_ENCPT");
		
		List<String> recvTelNo = new ArrayList<>();
		recvTelNo.add(mblTelno);
		
		String contents = "[청소년 안전망 시스템]\n";
		contents += map.get("USER_NAME") +" 님\n";
		contents += joinMap.get("JOIN_APLY_DT") + "에\n";
		contents += joinMap.get("USER_ID") + " 아이디로\n";
		contents += "회원 가입 신청하신 건이\n";
		contents += "반려되었습니다.\n";
		contents += "반려사유 : " + joinMap.get("JOIN_RJCT_CS_CN") + "\n";
		
		smsMessage.setSenderTelNo(senderTelNo);
		smsMessage.setRecvTelNo(recvTelNo);
		smsMessage.setContents(contents);
		smsMessage.setUserId(userId);
		
		// SMS 발송
		try {
			smsService.sendSMS(smsMessage);
		} catch (Exception e) {
			log.info("#### sendSMS JoinApprove 2 : " + e.getMessage());
		}
		
	}
	
	@Override
	public void saveUserJoin(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> resultMessage = new LinkedHashMap<String, Object>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmUserJoin");
		Map<String, String> map = param.getSingleValueMap();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String ymd = LocalDate.now().format(formatter);
		
		map.put("JOIN_APRV_YMD", ymd);
		
		map.put("JOIN_APRV_YN", "Y");
		
		map.put("JOIN_AUTZR_ID", userId);
		map.put("FRST_RGTR_ID", userId);
		map.put("LAST_MDFR_ID", userId);
		map.put("USER_ID2", userId);
		
		approveUserJoinMapper.saveUserJoin(map);

		map.put("DATAA_CHG_SE_CD", "U");
		personalInfoMapper.insertUserInfoHistory(new HashMap<String, Object>(map));
		
		String smsContents = "";
		
		// 삭제인 경우에는 종사자 정보와 개인정보까지 삭제 처리한다.
		if ("5".equals(map.get("USER_ID_USE_SE_CD"))) {

			Map<String, Object> map1 = new HashMap<>();
			
			map1.put("USER_ID", map.get("USER_ID"));
			
			Map<String, String> userInfoMap = personalInfoMapper.selectUserInfo(map1);
			
			userInfoMap.put("enfsnNo", userInfoMap.get("ENFSN_NO"));
			
			userInfoMap.put("USER_ID2", userId);

			userInfoMap.put("DATAA_CHG_SE_CD", "D");
			userInfoMap.put("DEL_YN", "Y");
			
			if (userInfoMap.get("enfsnNo") != null && !"".equals(userInfoMap.get("enfsnNo")) && !"null".equals(userInfoMap.get("enfsnNo")) ) {
				personalInfoMapper.deleteWorkerInfo(userInfoMap);
				personalHistoryMapper.insertWorkerInfoHistory(userInfoMap);
			}
			
			if (userInfoMap.get("INDV_IDNTFC_NO") != null && !"".equals(userInfoMap.get("INDV_IDNTFC_NO")) && !"null".equals(userInfoMap.get("INDV_IDNTFC_NO")) ) {
				personalInfoMapper.deletePersonalInfo(userInfoMap);
				personalHistoryMapper.insertPersonalInfoHistory(userInfoMap);
			}
			
			
			// 사용자 삭제할 때 권한도 함께 삭제
			if (map.get("USER_ID") != null && !"".equals(map.get("USER_ID").trim())) {
				
				// 사용자별 기관 권한 (SAB230) 삭제
				approveUserJoinMapper.deleteSAB230(map.get("USER_ID").trim());
				
				// 사용자별 메뉴 권한 (SAB250) 삭제
				approveUserJoinMapper.deleteSAB250(map.get("USER_ID").trim());
			}
			
			smsContents = "삭제 처리되었습니다.";
			
		} else if ("2".equals(map.get("USER_ID_USE_SE_CD"))) {  // 승인
			// 2023-02-17 (Myeong.Jae.Cheol) : 회원 승인시 기관 권한 정보 업데이트 (소속기관)
			Map<String, Object> updateParam = new HashMap<>();
			// 1) 검색조건 설정
			updateParam.put("USER_ID", map.get("USER_ID"));
			updateParam.put("OGDP_INST_NO", map.get("OGDP_INST_NO"));
			
			// 2) 수정할 정보 설정
			updateParam.put("GROUP_AUTHRT_SE_CD", map.get("GROUP_AUTHRT_SE_CD"));
			updateParam.put("AUTHRT_SE_CD", map.get("AUTHRT_SE_CD"));
			updateParam.put("LAST_MDFR_ID", userId);
			
			// 3) 기관 권한 정보 업데이트 처리
			approveUserJoinMapper.updateUserInstAuthrtByUserJoin(updateParam);
			
			// 4) 사용자별 메뉴 권한 등록
			
			// 2023.03.24 (Myeong.Jae.Cheol) : 특수한 그룹권한구분코드 체크하여 무시처리 (이인성 PL 요청)
			// 여성가족부-기관관리자(120), 여성가족부-담당자(140), 
			// 중앙관리기관-기관관리자(220), 중앙관리기관-사업담당자(230), 중앙관리기관-담당자(240)
			String groupAuthrtSeCd = StringUtil.nullConvert(map.get("GROUP_AUTHRT_SE_CD"));
			
			char instTypeDiv = groupAuthrtSeCd.charAt(0);		// 기관유형구분
			char roleDiv = groupAuthrtSeCd.charAt(1);			// 역할구분
			
			boolean existMenuTemplate = true;					// 메뉴템플릿 존재여부
			if (instTypeDiv == '1' || instTypeDiv == '2') {		// 여성가족부, 중앙관리기관(개발원)
				if (roleDiv == '2' || roleDiv == '3' || roleDiv == '4') {	// 기관관리자, 사업담당자, 담당자
					List<Map<String, Object>> menuAuthrtList = mgmtAuthGrpService.selectMenuAuthTemplateList(request, map);
					existMenuTemplate = !ObjectUtils.isEmpty(menuAuthrtList);
				}
			}
			
			if (existMenuTemplate) {
				Map<String, Object> regResult = mgmtAuthGrpService.saveMenuAuthMapping(request, dataRequest, "dmUserJoin");
				resultMessage.put("RESULT_OK", regResult.get("RESULT_OK"));
				
				Long resultCnt = (Long) regResult.get("RESULT_CNT");
				if (resultCnt == 0) {
					//throw new AppWorksException("요청하신 권한에 대한 메뉴 권한이 누락되었습니다. '시스템관리-업무메뉴관리-권한관리-메뉴별권한관리'에서 등록하시기 바랍니다.", Alert.ERROR);
					resultMessage.put("RESULT_OK", "N");
					resultMessage.put("RESULT_MSG", "회원 승인중 해당 권한 유형의 메뉴 권한 항목이 누락되었습니다.\n시스템 관리자에게 문의하세요.");
					
					// 결과 메시지 전달
					dataRequest.setMetadata(true, resultMessage);
					return;
				} else {
					resultMessage.put("RESULT_MSG", "회원 가입 승인이 완료되었습니다.");
				}
			} else {
				resultMessage.put("RESULT_OK", "Y");
				resultMessage.put("RESULT_MSG", "회원 가입 승인이 완료되었습니다.");
			}
			
			smsContents = "승인되었습니다.\n필요한 업무 및 권한은 로그인 후 \"마이페이지 > 업무및메뉴권한신청\"에서 신청바랍니다.";
		
		} else if ("4".equals(map.get("USER_ID_USE_SE_CD"))) {  // 사용중지
			
			smsContents = "사용중지되었습니다.";
			
		}
		

		// 안내 SMS 발송
		Map<String, String> joinMap = approveUserJoinMapper.selectUserJoinInfo(map.get("USER_ID"));
		
		SmsMessageVO smsMessage = new SmsMessageVO();
		
		String senderTelNo = "0516623229";
		
		//ScpDb scpDb = new ScpDb();
		//String mblTelno = scpDb.scpDecB64(joinMap.get("MBL_TELNO_ENCPT"));
		String mblTelno = joinMap.get("MBL_TELNO_ENCPT");
		
		List<String> recvTelNo = new ArrayList<>();
		recvTelNo.add(mblTelno);
		
		String contents = "[청소년 안전망 시스템]\n";
		contents += map.get("USER_NAME") +" 님\n";
		contents += joinMap.get("JOIN_APLY_DT") + "에\n";
		contents += joinMap.get("USER_ID") + " 아이디로\n";
		contents += "회원 가입 신청하신 건이\n";
		contents += smsContents;
		
		smsMessage.setSenderTelNo(senderTelNo);
		smsMessage.setRecvTelNo(recvTelNo);
		smsMessage.setContents(contents);
		smsMessage.setUserId(userId);
		
		// SMS 발송
		try {
			smsService.sendSMS(smsMessage);
		} catch (Exception e) {
			log.info("#### sendSMS JoinApprove 1 : " + e.getMessage());
		}
		
		// 결과 메시지 전달
		dataRequest.setMetadata(true, resultMessage);
	}
	
	/**
	 * @Method명   : selectUserAuthInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 25. 
	 * @Method설명 : 사용자 권한 정보 조회 (회원가입승인 팝업)
	 */
	@Override
	public List<Map<String, Object>> selectUserAuthInfo(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		// 검색조건 Parameter 설정
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> mapParam = new LinkedHashMap<>();
		searchParam.getSingleValueMap().forEach(mapParam::put);
		
		//ScpDb scpDb = new ScpDb();
		
		// 사용자 권한 정보 조회
		List<Map<String, Object>> userAuthInfo = approveUserJoinMapper.selectUserAuthInfo(mapParam);
		
		//for (Map<String, Object> userAuthMap : userAuthInfo) {
			//userAuthMap.forEach((key, value) -> {
				//String plainText = new String();
				
				// 성명암호화 복호화 처리
				//if ("USER_FLNM".equals(key)) {
					//plainText = StringUtil.nullConvert(value);
					//String decodeText = scpDb.scpDecB64(plainText);
					//userAuthMap.replace(key, decodeText);
				//}
			//});
		//}
		
		return userAuthInfo;
	}
	
}
