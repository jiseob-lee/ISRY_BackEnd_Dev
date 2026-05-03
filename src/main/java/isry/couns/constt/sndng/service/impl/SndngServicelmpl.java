/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.sndng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.constt.sndng.mapper.SndngMapper;
import isry.couns.constt.sndng.service.SndngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;
import isry2.itgcms.syscmmn.sms.mapper.SmsMapper;

/**
 * @파일명        : SndngServicelmpl.java
 * @프로그램 설명 : 이음-e 발송
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 10. 05. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 10. 05. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("sndngService")
public class SndngServicelmpl extends IsryBaseServiceImpl implements SndngService {	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="sndngMapper")
	private SndngMapper sndngMapper;

	@Resource(name = "smsMapper")
	private SmsMapper smsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	Masking mask  = new Masking();
	
	/**
	 * @Method명   : selectChrctrSndngList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 : 독려문자발송 조회
	 */	
	@Override
	public List<Map<String, Object>> selectChrctrSndngList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = sndngMapper.selectChrctrSndngList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			if(map.get("WRTR_NM_ENCPT") != null) {
				map.replace("WRTR_NM_ENCPT", Masking.nameMasking(map.get("WRTR_NM_ENCPT").toString()));
			}
			
			if(map.get("EML_ADDR_ENCPT") != null) {
				map.replace("EML_ADDR_ENCPT", Masking.emailMasking(map.get("EML_ADDR_ENCPT").toString()));
			}
			
			if(map.get("MBL_TELNO_ENCPT") != null && map.get("MBL_TELNO_ENCPT2") == null) {
				map.put("MBL_TELNO_ENCPT", Masking.phoneMasking(map.get("MBL_TELNO_ENCPT").toString()));
			}else if(map.get("MBL_TELNO_ENCPT") == null && map.get("MBL_TELNO_ENCPT2") != null) {
				map.put("MBL_TELNO_ENCPT", Masking.phoneMasking(map.get("MBL_TELNO_ENCPT2").toString()));
			}else if(map.get("MBL_TELNO_ENCPT") == null && map.get("MBL_TELNO_ENCPT2") == null) {
				LOGGER.debug("333 ::::::::::::" );
				map.put("MBL_TELNO_ENCPT", "-");
			}
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectSndngHistbList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 : 발송내역 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSndngHistbList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("paramMap ::::::::::::" + paramMap.toString());
		rtn = sndngMapper.selectSndngHistbList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	/**
	 * @Method명   : saveSms
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 07. 
	 * @Method설명 : SMS 보내기
	 */
	public void saveSms(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup paramGroup1 = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsChrctrSndngList");
		
		LOGGER.debug("paramGroup1 ::::::::::::" + paramGroup1.toString());
		LOGGER.debug("paramGroup2 ::::::::::::" + paramGroup2.toString());
				
//		Map<String, String> paramMap1 = paramGroup1.getSingleValueMap();
//		String contents = paramMap1.get("contents");
//		String sender = paramMap1.get("sender");
//		sender = sender.replaceAll("[^\\d]", "");
//		
//		String reserveYN = paramMap1.get("reserveYN");
//		String reserveTime = paramMap1.get("reserveTime");
		
//		if (sender == null || "".equals(sender) || contents == null || "".equals(contents)) {
//			return;
//		}
		
		List<Map<String, String>> listReceiver = paramGroup2.getAllRowList();
		
		for (int i=0; i < listReceiver.size(); i++) {
			Map<String, String> mapReceiver = listReceiver.get(i);
			
			if("true".equals(mapReceiver.get("CHK").toString())) {
				
				String mblTelno =  mapReceiver.get("MBL_TELNO_ENCPT");
				mblTelno = mblTelno.replaceAll("[^\\d]", "");
	
				if (mblTelno == null || "".equals(mblTelno)) {
					continue;
				}
				
				Integer mmsContentsInfoSeq = smsMapper.selectMmsContentsInfoSeq();
				
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("mmsContentsInfoSeq", mmsContentsInfoSeq);				
				paramMap.put("title", mapReceiver.get("BBSCTT_TTL_NM").substring(0, 8) + "..");
				paramMap.put("contents", "온라인부모교육 이음e 이용안내 \n 안녕하세요. \n 온라인부모교육 프로그램 이용 이후 \n 다음 회기에 참여하지 않은지 1개월이 지났습니다. \n 청소년사이버상담센터(www.cyber1388.kr)에 접속하여 \n 이후 과정을 끝까지 완료해주세요. \n 프로그램 이용에 어려움이 있는 경우 청소년사이버상담센터 \n 고객의 소리 로 문의해 주시면 도와드리겠습니다. ");
				
				paramMap.put("reserveTime", DateUtil.getToday());				
				paramMap.put("receiver", mblTelno);
				paramMap.put("sender", "0516623229");
				paramMap.put("userId", userId);
				
				smsMapper.insertLMS1(paramMap); // ISRY_SMS.MMS_CONTENTS_INFO 
				smsMapper.insertLMS2(paramMap); // ISRY_SMS.MSG_DATA 
				
				paramMap.put("BBSCTT_ESNTAL_NO", mapReceiver.get("BBSCTT_ESNTAL_NO").toString());
				paramMap.put("BBSCTT_TYPE_SE_CD", mapReceiver.get("BBSCTT_TYPE_SE_CD").toString());
				sndngMapper.updateAYE100(paramMap); // AYE100
				
			}else {
				continue;
			}
		} // end for (int i=0; i < listReceiver.size(); i++) {
	}
	
	
	
}



