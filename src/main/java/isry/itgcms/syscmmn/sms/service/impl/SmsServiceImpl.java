/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.sms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.syscmmn.sms.service.SmsMessageVO;
import isry.itgcms.syscmmn.sms.service.SmsService;
import isry.itgcms.sysmgmt.userauth.mapper.InqOrgListMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry2.itgcms.syscmmn.sms.mapper.SmsMapper;

/**
 * @파일명        : SmsServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 6. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 6. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "smsMapper")
	private SmsMapper smsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	@Resource(name = "inqOrgListMapper")
	private InqOrgListMapper inqOrgListMapper;
	
	public void insertSMS(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmParam");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsReceiver2");
		
		Map<String, String> paramMap1 = paramGroup1.getSingleValueMap();

		if (userId == null || "".equals(userId)) {
			userId = paramMap1.get("userId");
		}
		
		String contents = paramMap1.get("contents");
		String sender = paramMap1.get("sender");
		sender = sender.replaceAll("[^\\d]", "");
		
		log.debug("#### sender : " + sender);

		String reserveYN = paramMap1.get("reserveYN");
		String reserveTime = paramMap1.get("reserveTime");
		
		if (sender == null || "".equals(sender) || contents == null || "".equals(contents)) {
			return;
		}
		
		List<Map<String, String>> listReceiver = paramGroup2.getAllRowList();
		
		if (listReceiver == null) {
			log.debug("#### listReceiver is null.");
			return;
		} else {
			log.debug("#### listReceiver size : " + listReceiver.size());
		}
		
		List<String> recvList = new ArrayList<>();
		List<String> recvNameList = new ArrayList<>();
		
		for (int i=0; i < listReceiver.size(); i++) {
			if (recvList.contains(listReceiver.get(i).get("MBL_TELNO"))) {
				continue;
			}
			recvList.add(listReceiver.get(i).get("MBL_TELNO"));
			recvNameList.add(listReceiver.get(i).get("FLNM"));
		}
		
		//Set<String> set = new HashSet<>(recvList);
		//recvList.clear();
		//recvList.addAll(set);
		
		for (int i=0; i < recvList.size(); i++) {
			String mblTelno = recvList.get(i);
			mblTelno = mblTelno.replaceAll("[^\\d]", "");

			log.debug("#### mblTelno : " + mblTelno);
			
			if (mblTelno == null || "".equals(mblTelno)) {
				continue;
			}
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("receiver", mblTelno);
			paramMap.put("sender", sender);
			paramMap.put("contents", contents);
			paramMap.put("userId", userId);
			paramMap.put("receiverName", recvNameList.get(i));

			if ("R".equals(reserveYN)) {
				paramMap.put("reserveTime", reserveTime);
			}
			
			smsMapper.insertSMS(paramMap);
		}
	}
	

	public void sendSMS(SmsMessageVO smsMessage) throws Exception {
		
		log.debug("#### Sender Tel No : " + smsMessage.getSenderTelNo());

		if (smsMessage.getSenderTelNo() == null || "".equals(smsMessage.getSenderTelNo()) ||
				smsMessage.getContents() == null || "".equals(smsMessage.getContents())) {
			return;
		}

		String sender = smsMessage.getSenderTelNo().replaceAll("[^\\d]", "");
		smsMessage.setSenderTelNo(sender);

		if (smsMessage.getRecvTelNo() == null) {
			log.info("#### listReceiver is null.");
			return;
		} else {
			log.info("#### listReceiver size : " + smsMessage.getRecvTelNo().size() + ", UserId : " + smsMessage.getUserId());
		}
		
		List<String> recvList = new ArrayList<>();
		List<String> receiverList = smsMessage.getRecvTelNo();
		List<String> receiverNameList = new ArrayList<>();
		List<String> recvNameList = smsMessage.getReceiverName();
		
		for (int i=0; i < receiverList.size(); i++) {
			if (recvList.contains(receiverList.get(i))) {
				continue;
			}
			recvList.add(receiverList.get(i));
			receiverNameList.add(recvNameList != null && recvNameList.size() > i 
					? recvNameList.get(i) : "");
		}
		
		//Set<String> set = new HashSet<>(recvList);
		//recvList.clear();
		//recvList.addAll(set);
		
		for (int i=0; i < recvList.size(); i++) {
			String receiver = recvList.get(i);
			String receiverName = receiverNameList != null && receiverNameList.size() > i ? receiverNameList.get(i) : "";
			receiver = receiver.replaceAll("[^\\d]", "");

			log.debug("#### Receiver Tel No : " + receiver);
			
			if (receiver == null || "".equals(receiver)) {
				continue;
			}
			
			Map<String, Object> paramMap = new HashMap<>();

			if ("R".equals(smsMessage.getReserveYN())) {
				paramMap.put("reserveTime", smsMessage.getReserveTime());
			}

			if (smsMessage.getContents().length() <= 45) {
				paramMap.put("receiver", receiver);
				paramMap.put("receiverName", receiverName);
				paramMap.put("sender", smsMessage.getSenderTelNo());
				paramMap.put("contents", smsMessage.getContents());
				paramMap.put("userId", smsMessage.getUserId());
				smsMapper.insertSMS(paramMap);
			} else {
				Integer mmsContentsInfoSeq = smsMapper.selectMmsContentsInfoSeq();
				paramMap.put("receiver", receiver);
				paramMap.put("receiverName", receiverName);
				paramMap.put("sender", smsMessage.getSenderTelNo());
				paramMap.put("contents", smsMessage.getContents());
				paramMap.put("userId", smsMessage.getUserId());
				paramMap.put("title", smsMessage.getContents().substring(0, 8) + "..");
				paramMap.put("mmsContentsInfoSeq", mmsContentsInfoSeq);

				smsMapper.insertLMS1(paramMap);
				smsMapper.insertLMS2(paramMap);
			}
		}
	}
	
	public void insertLMS(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmParam");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsReceiver2");
		
		Map<String, String> paramMap1 = paramGroup1.getSingleValueMap();
		String contents = paramMap1.get("contents");
		String sender = paramMap1.get("sender");
		sender = sender.replaceAll("[^\\d]", "");
		
		String reserveYN = paramMap1.get("reserveYN");
		String reserveTime = paramMap1.get("reserveTime");
		
		if (sender == null || "".equals(sender) || contents == null || "".equals(contents)) {
			return;
		}

		if (paramGroup2 == null) {
			return;
		}
		
		List<Map<String, String>> listReceiver = paramGroup2.getAllRowList();

		List<String> recvList = new ArrayList<>();
		List<String> recvNameList = new ArrayList<>();
		
		for (int i=0; i < listReceiver.size(); i++) {
			if (recvList.contains(listReceiver.get(i).get("MBL_TELNO"))) {
				continue;
			}
			recvList.add(listReceiver.get(i).get("MBL_TELNO"));
			recvNameList.add(listReceiver.get(i).get("FLNM"));
		}
		
		//Set<String> set = new HashSet<>(recvList);
		//recvList.clear();
		//recvList.addAll(set);
		
		for (int i=0; i < recvList.size(); i++) {
			String mblTelno = recvList.get(i);
			mblTelno = mblTelno.replaceAll("[^\\d]", "");

			if (mblTelno == null || "".equals(mblTelno)) {
				continue;
			}
			
			Integer mmsContentsInfoSeq = smsMapper.selectMmsContentsInfoSeq();
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("receiver", mblTelno);
			paramMap.put("sender", sender);
			paramMap.put("contents", contents);
			paramMap.put("title", contents.substring(0, 8) + "..");
			paramMap.put("userId", userId);
			paramMap.put("mmsContentsInfoSeq", mmsContentsInfoSeq);
			paramMap.put("receiverName", recvNameList.get(i));

			if ("R".equals(reserveYN)) {
				paramMap.put("reserveTime", reserveTime);
			}
			
			smsMapper.insertLMS1(paramMap);
			smsMapper.insertLMS2(paramMap);
		}
	}

	@Override
	public Integer selectSmsHistoryCount(HttpServletRequest request, Map<String, Object> map) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Object instNos = getInstNos(request, loginVO);
		
		map.put("INST_NOS", instNos);
		
		map.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("USER_NAME_ENCPT", scpDb.scpEncB64((String)map.get("USER_NAME")));
		
		return smsMapper.selectSmsHistoryCount(map);
	}
	
	@Override
	public List<Map<String, Object>> selectSmsHistory(HttpServletRequest request, Map<String, Object> map) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Object instNos = getInstNos(request, loginVO);
		
		map.put("INST_NOS", instNos);
		
		map.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		
		//ScpDb scpDb = new ScpDb();
		
		//map.put("USER_NAME_ENCPT", scpDb.scpEncB64((String)map.get("USER_NAME")));
		
		List<Map<String, Object>> list1 = smsMapper.selectSmsHistory(map);
		List<Map<String, Object>> list2 = new ArrayList<>();
		
		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				//map1.put("USER_NAME", scpDb.scpDecB64((String)map1.get("USER_NAME")));
				map1.put("USER_NAME_MASKING", Masking.nameMasking((String)map1.get("USER_NAME")));
				map1.put("CALL_TO_MASKING", Masking.phoneMasking((String)map1.get("CALL_TO")));
				list2.add(map1);
			}
		}
		
		return list2;
	}

	private Object getInstNos(HttpServletRequest request, UserDetailsVO loginVO) throws Exception {

		Map<String, String> paramMap = new HashMap<>();

		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, paramMap);

		@SuppressWarnings("unchecked")
		List<Integer> instNoList1 = (List<Integer>)comMap.get("INST_NOS");
		
		List<Integer> instNoList = new ArrayList<>();
		
		if (instNoList1 != null && instNoList1.size() > 0) {
			for (int i=0; i < instNoList1.size(); i++) {
				instNoList.add(instNoList1.get(i));
			}
		}
		
		List<UserInstAuthVO> instAuthList = loginVO.getInstAuthList();
		
		if (instAuthList != null && instAuthList.size() > 0) {
			for (int i=0; i < instAuthList.size(); i++) {
				UserInstAuthVO instAuth = instAuthList.get(i);
				if ("Y".equals(instAuth.getMaistYn())) {
					if (!instNoList.contains(instAuth.getInstNo())) {
						instNoList.add(instAuth.getInstNo());
					}
				}
			}
		}
		
		//return comMap.get("INST_NOS");
		return instNoList;
	}
	
	// 사용자 소속 기관의 대표 전화번호 구하기
	@Override
	public Map<String, String> selectRepresentativePhone(HttpServletRequest request) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		if (userId == null) {
			return null;
		
		} else {
			
			String phone = inqOrgListMapper.selectRepresentativePhone(userId);
			
			//log.debug("#### phone : " + phone);
			
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("sender", phone == null || "".equals(phone) ? "0516623229" : phone);
			
			return paramMap;
		}
	}
	

	// SMS 발송 예약 취소
	@Override
	public void processSmsCancel(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmData");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();		
		
		String msgSeq = paramMap.get("MSG_SEQ");
		String contSeq = paramMap.get("CONT_SEQ");

		log.debug("#### msgSeq : " + msgSeq);
		log.debug("#### contSeq : " + contSeq);
		
		if (contSeq != null && !"".equals(contSeq)) {
			smsMapper.deleteMmsContentsInfo(Integer.parseInt(contSeq));
		}
		
		if (msgSeq != null && !"".equals(msgSeq)) {
			smsMapper.deleteMsgData(Integer.parseInt(msgSeq));
		}
	}
}
