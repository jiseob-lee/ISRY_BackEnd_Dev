/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtOrgDtlMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgDtlService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : MgmtOrgDtlServiceImpl.java
 * @프로그램 설명 : 기관 상세 정보 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mgmtOrgDtlService")
public class MgmtOrgDtlServiceImpl extends IsryBaseServiceImpl implements MgmtOrgDtlService {

	@Resource(name="mgmtOrgDtlMapper")
    private MgmtOrgDtlMapper mgmtOrgDtlMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : saveOrgDtl
	 * @param map
	 * @return 
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2021. 12. 3. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> saveOrgDtl(HttpServletRequest request, DataRequest dataRequest, List<Map<String, String>> fileInfoList) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmOrgDetail");
		ParameterGroup paramRestArea = dataRequest.getParameterGroup("dmRestArea");
		
//		String oInstNo = ""; // 기관번호 
		Map<String, String> oInstNoMap = new HashMap<>();
		
		if (param != null) {
			
			Map<String, String> map = param.getSingleValueMap();
			
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			String userId = "";
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId = loginVO.getId();
			}
			
			map.put("USER_ID", userId);
			
			//ScpDb scpDb = new ScpDb();
			
			String beforeRprsvNm = map.get("RPRSV_NM_ENCPT");
			
			//map.put("RPRSV_NM_ENCPT", scpDb.scpEncB64(map.get("RPRSV_NM_ENCPT")));
			//map.put("RPRS_MBL_TELNO_ENCPT", scpDb.scpEncB64(map.get("RPRS_MBL_TELNO_ENCPT")));
			//map.put("RPRS_EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("RPRS_EML_ADDR_ENCPT")));
			//map.put("PIC_NM_ENCPT", scpDb.scpEncB64(map.get("PIC_NM_ENCPT")));
			//map.put("PIC_TELNO_ENCPT", scpDb.scpEncB64(map.get("PIC_TELNO_ENCPT")));
			//map.put("PIC_MBL_TELNO_ENCPT", scpDb.scpEncB64(map.get("PIC_MBL_TELNO_ENCPT")));
			//map.put("PIC_EML_ADDR_ENCPT", scpDb.scpEncB64(map.get("PIC_EML_ADDR_ENCPT")));

			if (map.get("APRV_YN") == null || "".equals(map.get("APRV_YN"))) { 
				map.put("APRV_YN", "N");
			}
			if (map.get("DEL_YN") == null || "".equals(map.get("DEL_YN"))) {
				map.put("DEL_YN", "N");
			}
			
			// 2022.08.19_추가기본정보 TAP으로 뻬서 주석으로 막음
//			map.put("INST_BRNO", map.get("INST_BRNO").replaceAll("[^0-9]", ""));
//			map.put("INST_CRNO", map.get("INST_CRNO").replaceAll("[^0-9]", ""));
			
			int INST_NO_CNT = mgmtOrgDtlMapper.selectInstNoCnt(map);	//통합기관이력 INST_NO 중복값 확인
			if (INST_NO_CNT > 0) {
				map.put("DATAA_CHG_SE_CD", "U");	//데이터변경 구분코드 "변경"
			} else {
				map.put("DATAA_CHG_SE_CD", "I");	//데이터변경 구분코드 "신규"
				// 기관 신규등록은 승인 상태로 저장처리 SAA010 통합기관신청으로 관리.
				map.put("APRV_YN", "Y");
			}
			
			log.debug("map111::::::::::::::::::" + map);
			// 기관명, 단위시스템, 상위기관, 상위승인기관, 시도, 시군구, 대표자명 
			
			
			// 저장 시점 이전에 상위 정보중 하나 이상 수정되었는지 체크.
			boolean modifyYn = false;
			
			if (INST_NO_CNT > 0) {
				// 수정 이전 정보 가져오기.
				Map<String, Object> existingMap = mgmtOrgDtlMapper.selectExistingOrgData(map);
				// 통합기관신청 관련 요청 상태 변경
				if (existingMap != null) {
					// INST_NM, UNT_TASKWK_SE_CD, UP_INST_NO, UP_APRV_INST_NO, CTPV_CD, SGG_CD , RPRSV_NM_ENCPT
					if (!existingMap.get("INST_NM").equals(map.get("INST_NM"))) {
						modifyYn = true;
					}
					if (!existingMap.get("UNT_TASKWK_SE_CD").equals(map.get("UNT_TASKWK_SE_CD"))) {
						modifyYn = true;
					}
					if (!(String.valueOf(existingMap.get("UP_INST_NO")).equals(String.valueOf(map.get("UP_INST_NO"))))) {
						modifyYn = true;
					}
					if (!(String.valueOf(existingMap.get("UP_APRV_INST_NO")).equals(String.valueOf(map.get("UP_APRV_INST_NO"))))) {
						modifyYn = true;
					}
					if (!existingMap.get("CTPV_CD").equals(map.get("CTPV_CD"))) {
						modifyYn = true;
					}
					if (!existingMap.get("SGG_CD").equals(map.get("SGG_CD"))) {
						modifyYn = true;
					}
					//String rprsvNm = scpDb.scpDecB64(String.valueOf(existingMap.get("RPRSV_NM_ENCPT")));
					String rprsvNm = String.valueOf(existingMap.get("RPRSV_NM_ENCPT"));
					if (!rprsvNm.equals(beforeRprsvNm)) {
						modifyYn = true;
					}
				}
				if (modifyYn) {
					mgmtOrgDtlMapper.insertAprvOrgDtl(map);
				}
			} 
			mgmtOrgDtlMapper.saveOrgDtl(map);
			mgmtOrgDtlMapper.saveOrgDtlHistory(map);
			//mgmtOrgDtlMapper.updateOrgSequence();
			
			
			if (paramRestArea != null) {
				Map<String, String> mapRestArea = paramRestArea.getSingleValueMap();
				
				if (mapRestArea.get("RPRSV_NM_ENCPT") != null && !"".equals(mapRestArea.get("RPRSV_NM_ENCPT")) 
						&& mapRestArea.get("JIKIMI_MBR_YN") != null && !"".equals(mapRestArea.get("JIKIMI_MBR_YN"))) {
					mapRestArea.put("USER_ID", userId);
					mgmtOrgDtlMapper.saveOrgRestArea(mapRestArea);
				}
			}
			oInstNoMap.put("orgCode", map.get("INST_NO").toString());
			log.debug("기관번호111::::::::::::::::::" + oInstNoMap.toString());
		}
		
		log.debug("기관번호222::::::::::::::::::" + oInstNoMap);
		
		return oInstNoMap;
	}

	@Override
	public void deleteOrganization(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmOrgCode");
		
		if (param != null) {
			
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			String userId = "";
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId = loginVO.getId();
			}
			
			Integer instNo = param.getValue("orgCode") == null || "".equals(param.getValue("orgCode")) 
					? 0 : Integer.parseInt(param.getValue("orgCode"));
			
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			map.put("instNo", instNo);
			
			mgmtOrgDtlMapper.deleteOrganization(map);
		}
	}
	
	
	/**
	 * @Method명	 : saveAddtngBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 8. 19. 
	 * @Method설명 : 추가기본정보TAP 저장
	 */
	@Override
	public int saveAddtngBassInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup param = dataRequest.getParameterGroup("dmAddtngBassInfo");
		Map<String, String> dmAddtngBassInfoMap = param.getSingleValueMap();
		
		if (param != null) {
			Map<String, String> map = param.getSingleValueMap();
			
			log.debug("기관번호::::::::::::::::::" + map.get("oInstNo").toString());
			log.debug("기관사업자등록번호::::::::::::::::::" + map.get("INST_BRNO").toString());
			log.debug("센터현황단위업무구분코드::::::::::::::::::" + map.get("CNTER_PRECON_UNT_TASKWK_SE_CD").toString());
			
			dmAddtngBassInfoMap.put("INST_NO", map.get("oInstNo").toString()); 				// 기관번호
			dmAddtngBassInfoMap.put("INST_BRNO", map.get("INST_BRNO").toString()); 			// 기관사업자등록번호
			dmAddtngBassInfoMap.put("INST_CRNO", map.get("INST_CRNO").toString()); 			// 기관법인등록번호	
		
			dmAddtngBassInfoMap.put("ESOFRE_YN", map.get("ESOFRE_YN").toString()); 			// 조례제정여부	
			//dmAddtngBassInfoMap.put("OSCHL_YNGBGS_PRVUSE_SPCE_INSTL_YN", "N"); 				// 학교밖청소년전용공간설치여부
			//dmAddtngBassInfoMap.put("PRVUSE_WIRE_YN", "Y"); 								// 전용회선여부
			//dmAddtngBassInfoMap.put("PBADMS_WIRE_CNTN_YN", "Y"); 							// 행정회선접속여부
			//dmAddtngBassInfoMap.put("DSPTCH_NO_IDNTY_YN", "Y"); 							// 발신번호확인여부	
					
			dmAddtngBassInfoMap.put("CO13_TELNO", map.get("CO13_TELNO").toString());		// 1388상담전화번호
			dmAddtngBassInfoMap.put("MCLNC_ENFC_YMD", map.get("MCLNC_ENFC_YMD").toString());// 조례시행일자
			dmAddtngBassInfoMap.put("USER_ID", sUserId);		
			dmAddtngBassInfoMap.put("USER_ID", sUserId);
			
			dmAddtngBassInfoMap.put("CNTER_PRECON_UNT_TASKWK_SE_CD", map.get("CNTER_PRECON_UNT_TASKWK_SE_CD").toString());// 센터현황단위업무구분코드
			
			mgmtOrgDtlMapper.saveCnterPreconAddingBassInfo(dmAddtngBassInfoMap); // 센터현황T(AKA500)_추가기본정보
		}
		
		return 0;
	}
	
	/**
	 * @Method명	 : saveInstlCnsgnInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws 설치및위탁정보TAP 저장
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 8. 19. 
	 * @Method설명 : 설치및위탁정보TAP 저장
	 */
	@Override
	public int saveInstlCnsgnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup param = dataRequest.getParameterGroup("dmInstlCnsgnInfo");
		Map<String, String> dmInstlCnsgnInfoMap = param.getSingleValueMap();
		
		if (param != null) {
			Map<String, String> map = param.getSingleValueMap();
			
			dmInstlCnsgnInfoMap.put("INST_NO", map.get("oInstNo").toString()); 										// 기관번호
			
			//dmInstlCnsgnInfoMap.put("ESOFRE_YN", "Y"); 																// 조례제정여부
			//dmInstlCnsgnInfoMap.put("OSCHL_YNGBGS_PRVUSE_SPCE_INSTL_YN", "N"); 										// 학교밖청소년전용공간설치여부
			//dmInstlCnsgnInfoMap.put("PRVUSE_WIRE_YN", "Y"); 														// 전용회선여부
			//dmInstlCnsgnInfoMap.put("PBADMS_WIRE_CNTN_YN", "Y"); 													// 행정회선접속여부
			//dmInstlCnsgnInfoMap.put("DSPTCH_NO_IDNTY_YN", "Y"); 													// 발신번호확인여부		
			
			dmInstlCnsgnInfoMap.put("FCLTY_TYPE_SE_CD", map.get("FCLTY_TYPE_SE_CD").toString()); 					// 시설유형구분코드
			dmInstlCnsgnInfoMap.put("PRTCTN_PSNCPA_CNT", map.get("PRTCTN_PSNCPA_CNT").toString()); 					// 보호정원수
			dmInstlCnsgnInfoMap.put("CASE_MNG_PSNCPA_CNT", map.get("CASE_MNG_PSNCPA_CNT").toString()); 				// 사례관리정원수
			dmInstlCnsgnInfoMap.put("PRTCTN_YNGBGS_TYPE_SE_CD", map.get("PRTCTN_YNGBGS_TYPE_SE_CD").toString()); 	// 보호청소년유형구분코드
			dmInstlCnsgnInfoMap.put("CNTER_TYPE_SE_CD", map.get("CNTER_TYPE_SE_CD").toString()); 					// 센터유형구분코드
			dmInstlCnsgnInfoMap.put("INSTL_OPER_MTHD_SE_CD", map.get("INSTL_OPER_MTHD_SE_CD").toString()); 			// 설치운영방법구분코드
			dmInstlCnsgnInfoMap.put("INSTL_YMD", map.get("INSTL_YMD").toString()); 									// 설치일자
			dmInstlCnsgnInfoMap.put("INSTL_END_YMD", map.get("INSTL_END_YMD").toString()); 							// 설치종료일자
			dmInstlCnsgnInfoMap.put("INSTL_INST_NM", map.get("INSTL_INST_NM").toString()); 							// 설치기관명
			dmInstlCnsgnInfoMap.put("OPER_MTHD_SE_CD", map.get("OPER_MTHD_SE_CD").toString()); 						// 운영방법구분코드
			dmInstlCnsgnInfoMap.put("OPER_INST_TYPE_SE_CD", map.get("OPER_INST_TYPE_SE_CD").toString()); 			// 운영기관유형구분코드
			dmInstlCnsgnInfoMap.put("CNSGN_INST_NM", map.get("CNSGN_INST_NM").toString()); 							// 위탁기관명
			dmInstlCnsgnInfoMap.put("CNSGN_BGNG_YMD", map.get("CNSGN_BGNG_YMD").toString()); 						// 위탁시작일자
			dmInstlCnsgnInfoMap.put("CNSGN_END_YMD", map.get("CNSGN_END_YMD").toString()); 							// 위탁종료일자
			
			dmInstlCnsgnInfoMap.put("FRST_DSGN_YMD", map.get("FRST_DSGN_YMD").toString()); 							// 최초지정일자
			dmInstlCnsgnInfoMap.put("RECENT_DSGN_YMD", map.get("RECENT_DSGN_YMD").toString()); 						// 최근지정일자
			dmInstlCnsgnInfoMap.put("DSGN_END_YMD", map.get("DSGN_END_YMD").toString()); 							// 지정종료일자
			dmInstlCnsgnInfoMap.put("DSGN_MTHD_SE_CD", map.get("DSGN_MTHD_SE_CD").toString()); 						// 지정방법구분코드
			
			dmInstlCnsgnInfoMap.put("USER_ID", sUserId);		
			
			mgmtOrgDtlMapper.saveCnterPreconInstlCnsgnInfo(dmInstlCnsgnInfoMap); // 센터현황T(AKA500)_설치및위탁정보
			
		}
		
		return 0;
	}
	
	/**
	 * @Method명	 : saveOperInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws 운영정보TAP 저장
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 8. 19. 
	 * @Method설명 : 운영정보TAP 저장
	 */
	@Override
	public int saveOperInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup param = dataRequest.getParameterGroup("dmAddtngBassInfo");
		Map<String, String> dmInstNoMap = param.getSingleValueMap();
		
		if (param != null) {
			
			param = dataRequest.getParameterGroup("dsOperHour"); // 운영시간DS
			
			List<Map<String, String>> dsOperHourInsert  = param.getInsertedRowList();
			List<Map<String, String>> dsOperHourUpdate = param.getUpdatedRowList();
			
			for(int i=0; i<dsOperHourInsert.size(); i++) {	
				
				log.debug("기관번호000::::::::::::::::::" + dmInstNoMap.get("oInstNo"));						
				log.debug("요일구분코드::::::::::::::::::" + dsOperHourInsert.get(i).get("DAOFTH_SE_CD").toString());
				log.debug("근무시간선택제여부::::::::::::::::::" + dsOperHourInsert.get(i).get("WORK_HR_OPSY_YN").toString());
				log.debug("근무시작시간::::::::::::::::::" + dsOperHourInsert.get(i).get("WORK_BGNG_HR").toString());
				
				dsOperHourInsert.get(i).put("INST_NO", dmInstNoMap.get("oInstNo")); 										// 기관번호
				dsOperHourInsert.get(i).put("DAOFTH_SE_CD", dsOperHourInsert.get(i).get("DAOFTH_SE_CD").toString()); 		// 요일구분코드
				dsOperHourInsert.get(i).put("WORK_HR_OPSY_YN", dsOperHourInsert.get(i).get("WORK_HR_OPSY_YN").toString()); 	// 근무시간선택제여부
				dsOperHourInsert.get(i).put("WORK_BGNG_HR", dsOperHourInsert.get(i).get("WORK_BGNG_HR").toString()); 		// 근무시작시간
				
				dsOperHourInsert.get(i).put("USER_ID", sUserId);		
				dsOperHourInsert.get(i).put("USER_ID", sUserId);
				
				mgmtOrgDtlMapper.insertCnterPreconOperHour(dsOperHourInsert.get(i)); // 센터현황-운영시간T(AKA510)
			}
			
			for(int i=0; i<dsOperHourUpdate.size(); i++) {	
								
				dsOperHourUpdate.get(i).put("INST_NO", dmInstNoMap.get("oInstNo")); 										// 기관번호
				dsOperHourUpdate.get(i).put("INDEX_SN", dsOperHourUpdate.get(i).get("INDEX_SN").toString()); 				// 색인일련번호
				dsOperHourUpdate.get(i).put("DAOFTH_SE_CD", dsOperHourUpdate.get(i).get("DAOFTH_SE_CD").toString()); 		// 요일구분코드
				dsOperHourUpdate.get(i).put("WORK_HR_OPSY_YN", dsOperHourUpdate.get(i).get("WORK_HR_OPSY_YN").toString());	// 근무시간선택제여부
				dsOperHourUpdate.get(i).put("WORK_BGNG_HR", dsOperHourUpdate.get(i).get("WORK_BGNG_HR").toString()); 		// 근무시작시간
				
				dsOperHourUpdate.get(i).put("USER_ID", sUserId);		
				dsOperHourUpdate.get(i).put("USER_ID", sUserId);
				
				mgmtOrgDtlMapper.updateCnterPreconOperHour(dsOperHourUpdate.get(i)); // 센터현황-운영시간T(AKA510)
			}
			
			
			param = dataRequest.getParameterGroup("dsBrofaOper"); // 분소운영DS
			
			List<Map<String, String>> dsBrofaOperInsert  = param.getInsertedRowList();
			List<Map<String, String>> dsBrofaOperUpdate  = param.getUpdatedRowList();
			
			for(int i=0; i<dsBrofaOperInsert.size(); i++) {	
				
				dsBrofaOperInsert.get(i).put("INST_NO", dmInstNoMap.get("oInstNo")); 										// 기관번호
				dsBrofaOperInsert.get(i).put("BROFA_NM", dsBrofaOperInsert.get(i).get("BROFA_NM").toString()); 					// 분소명
				dsBrofaOperInsert.get(i).put("RPRS_ADDR", dsBrofaOperInsert.get(i).get("RPRS_ADDR").toString()); 				// 대표주소
				dsBrofaOperInsert.get(i).put("ZIP", dsBrofaOperInsert.get(i).get("ZIP").toString()); 							// 우편번호				
				dsBrofaOperInsert.get(i).put("RPRS_TELNO", dsBrofaOperInsert.get(i).get("RPRS_TELNO").toString()); 				// 대표전화번호
				dsBrofaOperInsert.get(i).put("IAGRTE_YMD", dsBrofaOperInsert.get(i).get("IAGRTE_YMD").toString()); 				// 개소일자
				dsBrofaOperInsert.get(i).put("ALL_AR_VALUE", dsBrofaOperInsert.get(i).get("ALL_AR_VALUE").toString()); 			// 전체면적값
				dsBrofaOperInsert.get(i).put("ENFSN_CNT", dsBrofaOperInsert.get(i).get("ENFSN_CNT").toString()); 				// 종사자수
				dsBrofaOperInsert.get(i).put("BROFA_OPER_CS_CN", dsBrofaOperInsert.get(i).get("BROFA_OPER_CS_CN").toString());	// 분소운영사유내용
				
				dsBrofaOperInsert.get(i).put("USER_ID", sUserId);		
				dsBrofaOperInsert.get(i).put("USER_ID", sUserId);
				
				mgmtOrgDtlMapper.insertBrofaOper(dsBrofaOperInsert.get(i)); // 센터현황-분소운영T(AKA520)
			}
			
			for(int i=0; i<dsBrofaOperUpdate.size(); i++) {	
				
				dsBrofaOperUpdate.get(i).put("INST_NO", dmInstNoMap.get("oInstNo")); 											// 기관번호
				dsBrofaOperUpdate.get(i).put("INDEX_SN", dsBrofaOperUpdate.get(i).get("INDEX_SN").toString()); 					// 색인일련번호
				dsBrofaOperUpdate.get(i).put("BROFA_NM", dsBrofaOperUpdate.get(i).get("BROFA_NM").toString()); 					// 분소명
				dsBrofaOperUpdate.get(i).put("RPRS_ADDR", dsBrofaOperUpdate.get(i).get("RPRS_ADDR").toString()); 				// 대표주소
				dsBrofaOperUpdate.get(i).put("ZIP", dsBrofaOperUpdate.get(i).get("ZIP").toString()); 							// 우편번호				
				dsBrofaOperUpdate.get(i).put("RPRS_TELNO", dsBrofaOperUpdate.get(i).get("RPRS_TELNO").toString()); 				// 대표전화번호
				dsBrofaOperUpdate.get(i).put("IAGRTE_YMD", dsBrofaOperUpdate.get(i).get("IAGRTE_YMD").toString()); 				// 개소일자
				dsBrofaOperUpdate.get(i).put("ALL_AR_VALUE", dsBrofaOperUpdate.get(i).get("ALL_AR_VALUE").toString()); 			// 전체면적값
				dsBrofaOperUpdate.get(i).put("ENFSN_CNT", dsBrofaOperUpdate.get(i).get("ENFSN_CNT").toString()); 				// 종사자수
				dsBrofaOperUpdate.get(i).put("BROFA_OPER_CS_CN", dsBrofaOperUpdate.get(i).get("BROFA_OPER_CS_CN").toString());	// 분소운영사유내용
				
				dsBrofaOperUpdate.get(i).put("USER_ID", sUserId);		
				dsBrofaOperUpdate.get(i).put("USER_ID", sUserId);
				
				mgmtOrgDtlMapper.updateBrofaOper(dsBrofaOperUpdate.get(i)); // 센터현황-분소운영T(AKA520)
			}
		}
		
		return 0;
	}
	
	/**
	 * @Method명	 : saveYngbgs1388
	 * @param	 : dataRequest
	 * @return
	 * @throws 청소년상담전화1388TAP 저장
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 8. 19. 
	 * @Method설명 : 청소년상담전화1388TAP 저장
	 */
	@Override
	public int saveYngbgs1388(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup param = dataRequest.getParameterGroup("dmYngbgs1388");
		Map<String, String> dmYngbgs1388Map = param.getSingleValueMap();
		
		if (param != null) {
			
			Map<String, String> map = param.getSingleValueMap();
			
			log.debug("11111 ::::::::::::::::::" + map.get("oInstNo").toString());
			
			dmYngbgs1388Map.put("INST_NO", map.get("oInstNo").toString()); 				 						// 기관번호
			dmYngbgs1388Map.put("PRVUSE_WIRE_YN", map.get("PRVUSE_WIRE_YN").toString()); 						// 전용회선여부
			dmYngbgs1388Map.put("PRVUSE_WIRE_NO", map.get("PRVUSE_WIRE_NO").toString()); 						// 전용회선번호
			dmYngbgs1388Map.put("PRVUSE_WIRE_CNT", map.get("PRVUSE_WIRE_CNT").toString()); 						// 전용회선수
			dmYngbgs1388Map.put("PBADMS_WIRE_CNTN_YN", map.get("PBADMS_WIRE_CNTN_YN").toString()); 				// 행정회선접속여부
			dmYngbgs1388Map.put("DSCSN_TLPHON_RCORDG_SE_CD", map.get("DSCSN_TLPHON_RCORDG_SE_CD").toString()); 	// 상담전화녹음구분코드
			dmYngbgs1388Map.put("DSPTCH_NO_IDNTY_YN", map.get("DSPTCH_NO_IDNTY_YN").toString()); 				// 발신번호확인여부
			dmYngbgs1388Map.put("TLPHON_CSC_OPER_SE_CD", map.get("TLPHON_CSC_OPER_SE_CD").toString()); 			// 전화상담실운영구분코드
			dmYngbgs1388Map.put("CMMNS_LINGO_USE_SE_CD", map.get("CMMNS_LINGO_USE_SE_CD").toString()); 			// 공통링고사용구분코드
			dmYngbgs1388Map.put("ECSHG_STAFF_WORK_SUM_NOPE", map.get("ECSHG_STAFF_WORK_SUM_NOPE").toString());	// 전담요원근무합계인원수
			
			// 센터현황T(AKA500) _NOT NULL
			//dmYngbgs1388Map.put("ESOFRE_YN", "Y"); 									// 조례제정여부	
			//dmYngbgs1388Map.put("OSCHL_YNGBGS_PRVUSE_SPCE_INSTL_YN", "N"); 			// 학교밖청소년전용공간설치여부
//			dmYngbgs1388Map.put("PRVUSE_WIRE_YN", "Y"); 							// 전용회선여부
//			dmYngbgs1388Map.put("PBADMS_WIRE_CNTN_YN", "Y"); 						// 행정회선접속여부
//			dmYngbgs1388Map.put("DSPTCH_NO_IDNTY_YN", "Y"); 						// 발신번호확인여부
			
			dmYngbgs1388Map.put("USER_ID", sUserId);		
			dmYngbgs1388Map.put("USER_ID", sUserId);
			
			log.debug("dmYngbgs1388Map 111 ::::::::::::::::::" + dmYngbgs1388Map.toString());				
			mgmtOrgDtlMapper.saveCnterPreconYngbgsDscsnTlphon1388(dmYngbgs1388Map); // 센터현황T(AKA500)_청소년상담전화1388
			
			param = dataRequest.getParameterGroup("dsOperHour1388"); // 운영시간1388 DS
			List<Map<String, String>> dsOperHour1388Insert = param.getInsertedRowList();
			List<Map<String, String>> dsOperHour1388Update = param.getUpdatedRowList();
			for(int i=0; i<dsOperHour1388Insert.size(); i++) {	
				
				log.debug("AKA530 DAOFTH_SE_CD 111 ::::::::::::::::::" + dsOperHour1388Insert.get(i).get("DAOFTH_SE_CD").toString());	
				
				dsOperHour1388Insert.get(i).put("INST_NO", map.get("oInstNo").toString()); 											// 기관번호
				dsOperHour1388Insert.get(i).put("DAOFTH_SE_CD", dsOperHour1388Insert.get(i).get("DAOFTH_SE_CD").toString()); 		// 요일구분코드
				dsOperHour1388Insert.get(i).put("WORK_HR_OPSY_YN", dsOperHour1388Insert.get(i).get("WORK_HR_OPSY_YN").toString());	// 근무시간선택제여부
				dsOperHour1388Insert.get(i).put("WORK_BGNG_HR", dsOperHour1388Insert.get(i).get("WORK_BGNG_HR").toString()); 		// 근무시작시간
				dsOperHour1388Insert.get(i).put("WORK_END_HR", dsOperHour1388Insert.get(i).get("WORK_END_HR").toString()); 			// 근무종료시간
				
				dsOperHour1388Insert.get(i).put("USER_ID", sUserId);		
				dsOperHour1388Insert.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.insertCnterPrecon1388(dsOperHour1388Insert.get(i)); // 센터현황-1388전화운영시간T(AKA530)
			}
			
			for(int i=0; i<dsOperHour1388Update.size(); i++) {	
				
				log.debug("AKA530 DAOFTH_SE_CD 222 ::::::::::::::::::" + dsOperHour1388Update.get(i).get("DAOFTH_SE_CD").toString());		
				
				dsOperHour1388Update.get(i).put("INST_NO", map.get("oInstNo").toString()); 											// 기관번호
				dsOperHour1388Update.get(i).put("INDEX_SN", dsOperHour1388Update.get(i).get("INDEX_SN").toString()); 				// 색인일련번호
				dsOperHour1388Update.get(i).put("DAOFTH_SE_CD", dsOperHour1388Update.get(i).get("DAOFTH_SE_CD").toString()); 		// 요일구분코드
				dsOperHour1388Update.get(i).put("WORK_HR_OPSY_YN", dsOperHour1388Update.get(i).get("WORK_HR_OPSY_YN").toString());	// 근무시간선택제여부
				dsOperHour1388Update.get(i).put("WORK_BGNG_HR", dsOperHour1388Update.get(i).get("WORK_BGNG_HR").toString()); 		// 근무시작시간
				dsOperHour1388Update.get(i).put("WORK_END_HR", dsOperHour1388Update.get(i).get("WORK_END_HR").toString()); 			// 근무종료시간
				
				dsOperHour1388Update.get(i).put("USER_ID", sUserId);		
				dsOperHour1388Update.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.updateCnterPrecon1388(dsOperHour1388Update.get(i)); // 센터현황-1388전화운영시간T(AKA530)
			}
			
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			
			param = dataRequest.getParameterGroup("dsTpriRcvr1388"); // 청소년전화13881차수신자1388 DS
			List<Map<String, String>> dsTpriRcvr1388Insert = param.getInsertedRowList();
			List<Map<String, String>> dsTpriRcvr1388Update = param.getUpdatedRowList();
			
			log.debug("dsTpriRcvr1388Insert.size() ::::::::::::::::::" + dsTpriRcvr1388Insert.size());		
			
			for(int i=0; i<dsTpriRcvr1388Insert.size(); i++) {	
				
				dsTpriRcvr1388Insert.get(i).put("INST_NO", map.get("oInstNo").toString()); 																// 기관번호
				dsTpriRcvr1388Insert.get(i).put("TPRI_RCVR_WORK_TMZON_SE_CD", dsTpriRcvr1388Insert.get(i).get("TPRI_RCVR_WORK_TMZON_SE_CD").toString());// 1차수신자근무시간대구분코드
				dsTpriRcvr1388Insert.get(i).put("TPRI_RCPTN_STAFF_SE_CD", dsTpriRcvr1388Insert.get(i).get("TPRI_RCPTN_STAFF_SE_CD").toString()); 		// 1차수신요원구분코드
				dsTpriRcvr1388Insert.get(i).put("TPRI_RCPTN_STAFF_ETC_CN", dsTpriRcvr1388Insert.get(i).get("TPRI_RCPTN_STAFF_ETC_CN").toString()); 		// 1차수신요원기타내용
				
				dsTpriRcvr1388Insert.get(i).put("USER_ID", sUserId);		
				dsTpriRcvr1388Insert.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.insertCnterPreconTelephone1388(dsTpriRcvr1388Insert.get(i)); // 센터현황-1388전화근무현황T(AKA540)
			}
			
			log.debug("dsTpriRcvr1388Update.size() ::::::::::::::::::" + dsTpriRcvr1388Update.size());	
			
			for(int i=0; i<dsTpriRcvr1388Update.size(); i++) {	
				
				dsTpriRcvr1388Update.get(i).put("INST_NO", map.get("oInstNo").toString()); 																// 기관번호
				dsTpriRcvr1388Update.get(i).put("INDEX_SN", dsTpriRcvr1388Update.get(i).get("INDEX_SN").toString()); 									// 색인일련번호
				dsTpriRcvr1388Update.get(i).put("TPRI_RCVR_WORK_TMZON_SE_CD", dsTpriRcvr1388Update.get(i).get("TPRI_RCVR_WORK_TMZON_SE_CD").toString());// 1차수신자근무시간대구분코드
				dsTpriRcvr1388Update.get(i).put("TPRI_RCPTN_STAFF_SE_CD", dsTpriRcvr1388Update.get(i).get("TPRI_RCPTN_STAFF_SE_CD").toString()); 		// 1차수신요원구분코드
				dsTpriRcvr1388Update.get(i).put("TPRI_RCPTN_STAFF_ETC_CN", dsTpriRcvr1388Update.get(i).get("TPRI_RCPTN_STAFF_ETC_CN").toString()); 		// 1차수신요원기타내용
				
				dsTpriRcvr1388Update.get(i).put("USER_ID", sUserId);		
				dsTpriRcvr1388Update.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.updateCnterPreconTelephone1388(dsTpriRcvr1388Update.get(i)); // 센터현황-1388전화근무현황T(AKA540)
			}
			
			param = dataRequest.getParameterGroup("dsEcshgStaff1388"); // 1388전담요원근무인원수1388 DS
			List<Map<String, String>> dsEcshgStaff1388Insert = param.getInsertedRowList();		
			List<Map<String, String>> dsEcshgStaff1388Update = param.getUpdatedRowList();		
			
			for(int i=0; i<dsEcshgStaff1388Insert.size(); i++) {	
				
				dsEcshgStaff1388Insert.get(i).put("INST_NO", map.get("oInstNo").toString()); 																	// 기관번호				
				dsEcshgStaff1388Insert.get(i).put("TPRI_RCVR_WORK_TMZON_SE_CD", dsEcshgStaff1388Insert.get(i).get("TPRI_RCVR_WORK_TMZON_SE_CD").toString());// 전담요원근무시간대구분코드
				dsEcshgStaff1388Insert.get(i).put("ECSHG_STAFF_NOPE", dsEcshgStaff1388Insert.get(i).get("ECSHG_STAFF_NOPE").toString()); 						// 전담요원인원수
				
				dsEcshgStaff1388Insert.get(i).put("USER_ID", sUserId);		
				dsEcshgStaff1388Insert.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.insertCnterPreconTelephoneStaff1388(dsEcshgStaff1388Insert.get(i)); // 센터현황-1388전담요원현황T(AKA630)
			}
			
			for(int i=0; i<dsEcshgStaff1388Update.size(); i++) {	
				
				dsEcshgStaff1388Update.get(i).put("INST_NO", map.get("oInstNo").toString()); 																	// 기관번호
				dsEcshgStaff1388Update.get(i).put("TPRI_RCVR_WORK_TMZON_SE_CD", dsEcshgStaff1388Update.get(i).get("TPRI_RCVR_WORK_TMZON_SE_CD").toString());// 전담요원근무시간대구분코드
				dsEcshgStaff1388Update.get(i).put("ECSHG_STAFF_NOPE", dsEcshgStaff1388Update.get(i).get("ECSHG_STAFF_NOPE").toString()); 					// 전담요원인원수									
				dsEcshgStaff1388Update.get(i).put("USER_ID", sUserId);		
				dsEcshgStaff1388Update.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.updateCnterPreconTelephoneStaff1388(dsEcshgStaff1388Update.get(i)); // 센터현황-1388전담요원현황T(AKA630)
			}
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			param = dataRequest.getParameterGroup("dsOperHnf1388"); // 운영인력1388 DS
			List<Map<String, String>> dsOperHnf1388Insert = param.getInsertedRowList();
			List<Map<String, String>> dsOperHnf1388Update = param.getUpdatedRowList();
			
			log.debug("dsOperHnf1388Insert.size() ::::::::::::::::::" + dsOperHnf1388Insert.size());	
			
			for(int i=0; i<dsOperHnf1388Insert.size(); i++) {	
				
				dsOperHnf1388Insert.get(i).put("INST_NO", map.get("oInstNo").toString()); 													// 기관번호
				dsOperHnf1388Insert.get(i).put("OPER_HNF_LCLAS_SE_CD", dsOperHnf1388Insert.get(i).get("OPER_HNF_LCLAS_SE_CD").toString());	// 운영인력대분류구분코드
				dsOperHnf1388Insert.get(i).put("OPER_HNF_SCLAS_SE_CD", dsOperHnf1388Insert.get(i).get("OPER_HNF_SCLAS_SE_CD").toString());	// 운영인력소분류구분코드
				dsOperHnf1388Insert.get(i).put("OUTSD_HNF_ETC_CN", dsOperHnf1388Insert.get(i).get("OUTSD_HNF_ETC_CN").toString()); 			// 외부인력기타내용				
				dsOperHnf1388Insert.get(i).put("TRGT_NOPE", dsOperHnf1388Insert.get(i).get("TRGT_NOPE").toString()); 						// 대상인원수				
				
				dsOperHnf1388Insert.get(i).put("USER_ID", sUserId);		
				dsOperHnf1388Insert.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.insertCnterPreconOperHnf1388(dsOperHnf1388Insert.get(i)); // 센터현황-1388운영인력T(AKA550)
			}
			
			log.debug("dsOperHnf1388Update.size() ::::::::::::::::::" + dsOperHnf1388Update.size());	
			
			for(int i=0; i<dsOperHnf1388Update.size(); i++) {	
				
				dsOperHnf1388Update.get(i).put("INST_NO", map.get("oInstNo").toString()); 													// 기관번호
				dsOperHnf1388Update.get(i).put("INDEX_SN", dsOperHnf1388Update.get(i).get("INDEX_SN").toString()); 							// 색인일련번호
				dsOperHnf1388Update.get(i).put("OPER_HNF_LCLAS_SE_CD", dsOperHnf1388Update.get(i).get("OPER_HNF_LCLAS_SE_CD").toString());	// 운영인력대분류구분코드
				dsOperHnf1388Update.get(i).put("OPER_HNF_SCLAS_SE_CD", dsOperHnf1388Update.get(i).get("OPER_HNF_SCLAS_SE_CD").toString());	// 운영인력소분류구분코드
				dsOperHnf1388Update.get(i).put("OUTSD_HNF_ETC_CN", dsOperHnf1388Update.get(i).get("OUTSD_HNF_ETC_CN").toString()); 			// 외부인력기타내용				
				dsOperHnf1388Update.get(i).put("TRGT_NOPE", dsOperHnf1388Update.get(i).get("TRGT_NOPE").toString()); 						// 대상인원수				
				
				dsOperHnf1388Update.get(i).put("USER_ID", sUserId);		
				dsOperHnf1388Update.get(i).put("USER_ID", sUserId);

				mgmtOrgDtlMapper.updateCnterPreconOperHnf1388(dsOperHnf1388Update.get(i)); // 센터현황-1388운영인력T(AKA550)
			}
			
		}
		
		return 0;
	}
	
	/**
	 * @Method명	 : saveFcltyInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws 시설정보TAP 저장
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 8. 23. 
	 * @Method설명 : 시설정보TAP 저장
	 */
	@Override
	public int saveFcltyInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup param = dataRequest.getParameterGroup("dmFcltyInfo"); // 시설정보 DM
		Map<String, String> dmFcltyInfo = param.getSingleValueMap();
		
		if (param != null) {
			
			Map<String, String> map = param.getSingleValueMap();
			
			dmFcltyInfo.put("INST_NO", map.get("oInstNo").toString()); 				 								// 기관번호
			
			dmFcltyInfo.put("ESOFRE_YN", "Y"); 																		// 조례제정여부
			dmFcltyInfo.put("OSCHL_YNGBGS_PRVUSE_SPCE_INSTL_YN", map.get("OSCHL_YNGBGS_PRVUSE_SPCE_INSTL_YN").toString()); // 학교밖청소년전용공간설치여부
			dmFcltyInfo.put("PRVUSE_WIRE_YN", "Y"); 																// 전용회선여부
			dmFcltyInfo.put("PBADMS_WIRE_CNTN_YN", "Y"); 															// 행정회선접속여부
			dmFcltyInfo.put("DSPTCH_NO_IDNTY_YN", "Y"); 															// 발신번호확인여부
			
			dmFcltyInfo.put("BLDG_POSESN_SHAPE_LCLAS_SE_CD", map.get("BLDG_POSESN_SHAPE_LCLAS_SE_CD").toString());	// 건물소유형태대분류구분코드
			dmFcltyInfo.put("BLDG_POSESN_BHVIOR_SCLAS_SE_CD", map.get("BLDG_POSESN_BHVIOR_SCLAS_SE_CD").toString());// 건물소유행태소분류구분코드
			dmFcltyInfo.put("BLDG_OWNR_NM_ENCPT", map.get("BLDG_OWNR_NM_ENCPT").toString());						// 건물소유자명암호화
			dmFcltyInfo.put("FNDS_SRC_NXNDR_AMT", map.get("FNDS_SRC_NXNDR_AMT").toString());						// 자금원천국비금액
			dmFcltyInfo.put("FNDS_SRC_GOEX_AMT", map.get("FNDS_SRC_GOEX_AMT").toString());							// 자금원천지방비금액
			dmFcltyInfo.put("FNDS_SRC_PHW_AMT", map.get("FNDS_SRC_PHW_AMT").toString());							// 자금원천자부담금액
			dmFcltyInfo.put("INSTL_MVN_SHAPE_SE_CD", map.get("INSTL_MVN_SHAPE_SE_CD").toString());					// 설치입주형태구분코드
			dmFcltyInfo.put("INSTL_MVN_SHAPE_ETC_CN", map.get("INSTL_MVN_SHAPE_ETC_CN").toString());				// 설치입주형태기타내용
			dmFcltyInfo.put("BLDG_NM", map.get("BLDG_NM").toString());												// 건물명
			dmFcltyInfo.put("BLDG_MNG_REGSTR_MAPU_SE_CD", map.get("BLDG_MNG_REGSTR_MAPU_SE_CD").toString());		// 건물관리대장주용도구분코드
			dmFcltyInfo.put("BLDG_MNG_REGSTR_MAPU_ETC_CN", map.get("BLDG_MNG_REGSTR_MAPU_ETC_CN").toString());		// 건물관리대장주용도기타내용
			dmFcltyInfo.put("BLDG_CMCN_YR", map.get("BLDG_CMCN_YR").toString());									// 건물준공연도
			dmFcltyInfo.put("TOT_USE_CUNT", map.get("TOT_USE_CUNT").toString());									// 집계사용개수
			dmFcltyInfo.put("TOT_USE_AR_VALUE", map.get("TOT_USE_AR_VALUE").toString());							// 집계사용면적값
			dmFcltyInfo.put("SNGA_USE_CUNT", map.get("SNGA_USE_CUNT").toString());									// 단독사용개수
			dmFcltyInfo.put("SNGA_USE_AR_VALUE", map.get("SNGA_USE_AR_VALUE").toString());							// 단독사용면적값
			dmFcltyInfo.put("COLLAB_USE_CUNT", map.get("COLLAB_USE_CUNT").toString());								// 공동사용개수
			dmFcltyInfo.put("COLLAB_USE_AR_VALUE", map.get("COLLAB_USE_AR_VALUE").toString());						// 공동사용면적값
			dmFcltyInfo.put("FCLTY_ETC_CN", map.get("FCLTY_ETC_CN").toString());									// 시설기타내용
			dmFcltyInfo.put("SNGA_BLDG_YN", map.get("SNGA_BLDG_YN").toString());									// 단독건물여부
			dmFcltyInfo.put("USER_ID", sUserId);		
							
			mgmtOrgDtlMapper.saveCnterPreconFcltyInfo(dmFcltyInfo); // 센터현황T(AKA500)_시설정보			
			
			// 2022.09.20 청소년시설정보가 dm -> ds 바뀜
			ParameterGroup param1 = dataRequest.getParameterGroup("dsYngbgsFclty"); // 청소년시설 ds
			List<Map<String, String>> dsYngbgsFcltyIns = param1.getInsertedRowList();
			List<Map<String, String>> dsYngbgsFcltyUpd = param1.getUpdatedRowList();
			List<Map<String, String>> dsYngbgsFcltyDel = param1.getDeletedRowList();
			if (param1 != null) {
				for(Map<String, String> insMap : dsYngbgsFcltyIns) {
					insMap.put("INST_NO", map.get("oInstNo").toString()); 	
					insMap.put("USER_ID", sUserId);
					mgmtOrgDtlMapper.saveCnterPreconYngbgsFclty(insMap); // 센터현황t(AKA560)-청소년시설 insert
				}
				for(Map<String, String> updMap : dsYngbgsFcltyUpd) {
					updMap.put("USER_ID", sUserId);
					mgmtOrgDtlMapper.updateCnterPreconYngbgsFclty(updMap); // 센터현황t(AKA560)-청소년시설 update
				}
				for(Map<String, String> delMap : dsYngbgsFcltyDel) {
					delMap.put("USER_ID", sUserId);
					mgmtOrgDtlMapper.updateCnterPreconYngbgsFclty(delMap); // 센터현황t(AKA560)-청소년시설 delete
				}
				
				/*
				Map<String, String> map1 = param1.getSingleValueMap();
				dmYngbgsFclty.put("INST_NO", map.get("oInstNo").toString()); 				 						// 기관번호				
				dmYngbgsFclty.put("SAMENS_INOFAB_YNGBGS_FCLTY_SE_CD", map1.get("SAMENS_INOFAB_YNGBGS_FCLTY_SE_CD").toString());					// 청소년시설구분코드
				dmYngbgsFclty.put("ETC_YNGBGS_FCLTY_CN", map1.get("ETC_YNGBGS_FCLTY_CN").toString());				// 기타청소년시설내용
				dmYngbgsFclty.put("YNGBGS_FCLTY_BLDG_NM", map1.get("YNGBGS_FCLTY_BLDG_NM").toString());				// 청소년시설건물명
				dmYngbgsFclty.put("USER_ID", sUserId);
				*/
			}
			
			
			param = dataRequest.getParameterGroup("dsUseSpce"); // 사용공간세부 DS
			List<Map<String, String>> dsUseSpceInsert = param.getInsertedRowList();
			List<Map<String, String>> dsUseSpceUpdate = param.getUpdatedRowList();	
			
			log.debug("dsUseSpceInsert.size() ::::::::::::::::::" + dsUseSpceInsert.size());
			
			for(int i=0; i<dsUseSpceInsert.size(); i++) {	
				
				dsUseSpceInsert.get(i).put("INST_NO", map.get("oInstNo").toString()); 												// 기관번호
				dsUseSpceInsert.get(i).put("USE_SPCE_INFO_SE_CD", dsUseSpceInsert.get(i).get("USE_SPCE_INFO_SE_CD").toString()); 	// 사용공간정보구분코드
				dsUseSpceInsert.get(i).put("USE_SPCE_INFO_ETC_CN", dsUseSpceInsert.get(i).get("USE_SPCE_INFO_ETC_CN").toString());	// 사용공간정보기타내용
				dsUseSpceInsert.get(i).put("SNGA_USE_YN", dsUseSpceInsert.get(i).get("SNGA_USE_YN").toString()); 					// 단독사용여부
				dsUseSpceInsert.get(i).put("USE_SPCE_CUNT", dsUseSpceInsert.get(i).get("USE_SPCE_CUNT").toString()); 				// 사용공간개수
				dsUseSpceInsert.get(i).put("USE_SPCE_AR_VALUE", dsUseSpceInsert.get(i).get("USE_SPCE_AR_VALUE").toString()); 		// 사용공간면적값
				dsUseSpceInsert.get(i).put("BRM_AVRG_ACPTNC_NOPE", dsUseSpceInsert.get(i).get("BRM_AVRG_ACPTNC_NOPE").toString()); 	// 침실평균수용인원수
				dsUseSpceInsert.get(i).put("USER_ID", sUserId);		

				mgmtOrgDtlMapper.insertCnterPreconUseSpce(dsUseSpceInsert.get(i)); // 센터현황-사용공간세부T(AKA570)
			}
			
			log.debug("dsUseSpceUpdate.size() ::::::::::::::::::" + dsUseSpceUpdate.size());
			
			for(int i=0; i<dsUseSpceUpdate.size(); i++) {	
				
				dsUseSpceUpdate.get(i).put("INST_NO", map.get("oInstNo").toString()); 												// 기관번호
				dsUseSpceUpdate.get(i).put("INDEX_SN", dsUseSpceUpdate.get(i).get("INDEX_SN").toString()); 							// 색인일련번호
				dsUseSpceUpdate.get(i).put("USE_SPCE_INFO_SE_CD", dsUseSpceUpdate.get(i).get("USE_SPCE_INFO_SE_CD").toString()); 	// 사용공간정보구분코드
				dsUseSpceUpdate.get(i).put("USE_SPCE_INFO_ETC_CN", dsUseSpceUpdate.get(i).get("USE_SPCE_INFO_ETC_CN").toString());	// 사용공간정보기타내용
				dsUseSpceUpdate.get(i).put("SNGA_USE_YN", dsUseSpceUpdate.get(i).get("SNGA_USE_YN").toString()); 					// 단독사용여부
				dsUseSpceUpdate.get(i).put("USE_SPCE_CUNT", dsUseSpceUpdate.get(i).get("USE_SPCE_CUNT").toString()); 				// 사용공간개수
				dsUseSpceUpdate.get(i).put("USE_SPCE_AR_VALUE", dsUseSpceUpdate.get(i).get("USE_SPCE_AR_VALUE").toString()); 		// 사용공간면적값
				dsUseSpceUpdate.get(i).put("BRM_AVRG_ACPTNC_NOPE", dsUseSpceUpdate.get(i).get("BRM_AVRG_ACPTNC_NOPE").toString()); 	// 침실평균수용인원수
				dsUseSpceUpdate.get(i).put("USER_ID", sUserId);		

				mgmtOrgDtlMapper.updateCnterPreconUseSpce(dsUseSpceUpdate.get(i)); // 센터현황-사용공간세부T(AKA570)
			}
			
			param = dataRequest.getParameterGroup("dsUseSpceInfo"); // 사용공간정보세부 DS
			List<Map<String, String>> dsUseSpceInfoInsert = param.getInsertedRowList();
			List<Map<String, String>> dsUseSpceInfoUpdate = param.getUpdatedRowList();

			for(int i=0; i<dsUseSpceInfoInsert.size(); i++) {	

				dsUseSpceInfoInsert.get(i).put("INST_NO", map.get("oInstNo").toString()); 										      			// 기관번호
				dsUseSpceInfoInsert.get(i).put("CAR_INFO_SE_CD", dsUseSpceInfoInsert.get(i).get("CAR_INFO_SE_CD").toString()); 				  	// 차량정보구분코드
				dsUseSpceInfoInsert.get(i).put("CAR_INFO_ETC_CN", dsUseSpceInfoInsert.get(i).get("CAR_INFO_ETC_CN").toString());			  	// 차량정보기타내용
				dsUseSpceInfoInsert.get(i).put("BRDING_NOPE", dsUseSpceInfoInsert.get(i).get("BRDING_NOPE").toString()); 					  	// 탑승인원수
				dsUseSpceInfoInsert.get(i).put("CAR_YRIDNW_NO", dsUseSpceInfoInsert.get(i).get("CAR_YRIDNW_NO").toString()); 				  	// 차량연식번호
				dsUseSpceInfoInsert.get(i).put("WIK_AVRG_OPRAT_DAYCNT", dsUseSpceInfoInsert.get(i).get("WIK_AVRG_OPRAT_DAYCNT").toString());  	// 주간평균운행일수

				dsUseSpceInfoInsert.get(i).put("USER_ID", sUserId);		
				
				mgmtOrgDtlMapper.insertCnterPreconMvmnSheltrCar(dsUseSpceInfoInsert.get(i)); // 센터현황-이동형일시쉼터용차량T(AKA580)
			}
			
			for(int i=0; i<dsUseSpceInfoUpdate.size(); i++) {	

				dsUseSpceInfoUpdate.get(i).put("INST_NO", map.get("oInstNo").toString()); 										      			// 기관번호
				dsUseSpceInfoUpdate.get(i).put("INDEX_SN", dsUseSpceInfoUpdate.get(i).get("INDEX_SN").toString()); 								// 색인일련번호
				dsUseSpceInfoUpdate.get(i).put("CAR_INFO_SE_CD", dsUseSpceInfoUpdate.get(i).get("CAR_INFO_SE_CD").toString()); 				  	// 차량정보구분코드
				dsUseSpceInfoUpdate.get(i).put("CAR_INFO_ETC_CN", dsUseSpceInfoUpdate.get(i).get("CAR_INFO_ETC_CN").toString());			  	// 차량정보기타내용
				dsUseSpceInfoUpdate.get(i).put("BRDING_NOPE", dsUseSpceInfoUpdate.get(i).get("BRDING_NOPE").toString()); 					  	// 탑승인원수
				dsUseSpceInfoUpdate.get(i).put("CAR_YRIDNW_NO", dsUseSpceInfoUpdate.get(i).get("CAR_YRIDNW_NO").toString()); 				  	// 차량연식번호
				dsUseSpceInfoUpdate.get(i).put("WIK_AVRG_OPRAT_DAYCNT", dsUseSpceInfoUpdate.get(i).get("WIK_AVRG_OPRAT_DAYCNT").toString());  	// 주간평균운행일수

				dsUseSpceInfoUpdate.get(i).put("USER_ID", sUserId);		
				
				mgmtOrgDtlMapper.updateCnterPreconMvmnSheltrCar(dsUseSpceInfoUpdate.get(i)); // 센터현황-이동형일시쉼터용차량T(AKA580)
			}
			
			param = dataRequest.getParameterGroup("dsOschlYngbgsPrvuseSpace"); // 학교밖청소년전용공간 DS
			List<Map<String, String>> dsOschlYngbgsPrvuseSpaceInsert = param.getInsertedRowList();
			List<Map<String, String>> dsOschlYngbgsPrvuseSpaceUpdate = param.getUpdatedRowList();
			
			for(int i=0; i<dsOschlYngbgsPrvuseSpaceInsert.size(); i++) {	
				
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("INST_NO", map.get("oInstNo").toString()); 										      								// 기관번호
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("IAGRTE_YMD", dsOschlYngbgsPrvuseSpaceInsert.get(i).get("IAGRTE_YMD").toString()); 				  					// 개소일자
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("PRVUSE_SPCE_INSTL_LC_SE_CD", dsOschlYngbgsPrvuseSpaceInsert.get(i).get("PRVUSE_SPCE_INSTL_LC_SE_CD").toString());	// 전용공간설치위치구분코드
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("PRVUSE_SPCE_MU_PURPS_CN", dsOschlYngbgsPrvuseSpaceInsert.get(i).get("PRVUSE_SPCE_MU_PURPS_CN").toString()); 			// 전용공간주활용목적내용
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("PRVUSE_SPCE_SPRT_SE_CD", dsOschlYngbgsPrvuseSpaceInsert.get(i).get("PRVUSE_SPCE_SPRT_SE_CD").toString()); 			// 전용공간지원구분코드
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("PRVUSE_SPCE_SPRT_ETC_CN", dsOschlYngbgsPrvuseSpaceInsert.get(i).get("PRVUSE_SPCE_SPRT_ETC_CN").toString());  		// 전용공간지원기타내용
				
				dsOschlYngbgsPrvuseSpaceInsert.get(i).put("USER_ID", sUserId);		

				mgmtOrgDtlMapper.insertCnterPreconOschlYngbgsPrvuseSpace(dsOschlYngbgsPrvuseSpaceInsert.get(i)); // 센터현황-학교밖청소년전용공간T(AKA590)
			}
			
			for(int i=0; i<dsOschlYngbgsPrvuseSpaceUpdate.size(); i++) {	
				
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("INST_NO", map.get("oInstNo").toString()); 										      								// 기관번호
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("INDEX_SN", dsOschlYngbgsPrvuseSpaceUpdate.get(i).get("INDEX_SN").toString()); 										// 색인일련번호
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("IAGRTE_YMD", dsOschlYngbgsPrvuseSpaceUpdate.get(i).get("IAGRTE_YMD").toString()); 				  					// 개소일자
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("PRVUSE_SPCE_INSTL_LC_SE_CD", dsOschlYngbgsPrvuseSpaceUpdate.get(i).get("PRVUSE_SPCE_INSTL_LC_SE_CD").toString());	// 전용공간설치위치구분코드
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("PRVUSE_SPCE_MU_PURPS_CN", dsOschlYngbgsPrvuseSpaceUpdate.get(i).get("PRVUSE_SPCE_MU_PURPS_CN").toString()); 			// 전용공간주활용목적내용
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("PRVUSE_SPCE_SPRT_SE_CD", dsOschlYngbgsPrvuseSpaceUpdate.get(i).get("PRVUSE_SPCE_SPRT_SE_CD").toString()); 			// 전용공간지원구분코드
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("PRVUSE_SPCE_SPRT_ETC_CN", dsOschlYngbgsPrvuseSpaceUpdate.get(i).get("PRVUSE_SPCE_SPRT_ETC_CN").toString());  		// 전용공간지원기타내용
				
				dsOschlYngbgsPrvuseSpaceUpdate.get(i).put("USER_ID", sUserId);		

				mgmtOrgDtlMapper.updateCnterPreconOschlYngbgsPrvuseSpace(dsOschlYngbgsPrvuseSpaceUpdate.get(i)); // 센터현황-학교밖청소년전용공간T(AKA590)
			}
			
		}
		
		return 0;
	}
	
	/**
	 * @Method명   : selectYngbsInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 추가정보 조회
	 */
	@Override
	public Map<String, Object> selectYngbgsSheltr(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmOrgCode");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		log.debug("paramMap ::::::::::::::::::" + paramMap.toString());
		
		return mgmtOrgDtlMapper.selectYngbgsSheltr(paramMap);
	}
	
	/**
	 * @Method명   : selectOperHour
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 운영정보 조회_AKA510_센터현황-운영시간
	 */
	public List<Map<String, String>> selectOperHour(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectOperHour(searchParamMap);
	}
	
	/**
	 * @Method명   : selectBrofaOper
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 운영정보 조회_AKA520_센터현황-분소운영
	 */
	public List<Map<String, String>> selectBrofaOper(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectBrofaOper(searchParamMap);
	}
	
	/**
	 * @Method명   : selectYngbgs1388
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 청소년상담전화1388 조회
	 */
	@Override
	public Map<String, Object> selectYngbgs1388(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectYngbgs1388(paramMap);
	}
	
	/**
	 * @Method명   : selectOperHour1388
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA530_센터현황-1388전화운영시간
	 */
	public List<Map<String, String>> selectOperHour1388(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectOperHour1388(searchParamMap);
	}
	
	/**
	 * @Method명   : selectTpriRcvr1388
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA540_센터현황-1388전화근무현황
	 */
	public List<Map<String, String>> selectTpriRcvr1388(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectTpriRcvr1388(searchParamMap);
	}
	
	/**
	 * @Method명   : selectEcshgStaff1388
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA540_센터현황-1388전화근무현황
	 */
	public List<Map<String, String>> selectEcshgStaff1388(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectEcshgStaff1388(searchParamMap);
	}
	
	/**
	 * @Method명   : selectOperHnf1388
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA550_센터현황-1388운영인력
	 */
	public List<Map<String, String>> selectOperHnf1388(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectOperHnf1388(searchParamMap);
	}
	
	/**
	 * @Method명   : selectFcltyInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 시설정보 조회
	 */
	@Override
	public Map<String, Object> selectFcltyInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectFcltyInfo(paramMap);
	}
	
	/**
	 * @Method명   : selectUseSpce
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA570_센터현황-사용공간세부
	 */
	public List<Map<String, String>> selectUseSpce(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectUseSpce(searchParamMap);
	}
	
	/**
	 * @Method명   : selectUseSpceInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA580_센터현황-이동형일시쉼터용차량
	 */
	public List<Map<String, String>> selectUseSpceInfo(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectUseSpceInfo(searchParamMap);
	}
	
	/**
	 * @Method명   : selectOschlYngbgsPrvuseSpace
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : AKA590_센터현황-학교밖청소년전용공간
	 */
	public List<Map<String, String>> selectOschlYngbgsPrvuseSpace(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectOschlYngbgsPrvuseSpace(searchParamMap);
	}
	
	/**
	 * @Method명   : selectYngbgsFclty
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 청소년시설 조회
	 */
	@Override
	public List<Map<String, String>> selectYngbgsFclty(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectYngbgsFclty(paramMap);
	}
	

	/**
	 * @Method명   : selectInstlCnsgnInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 24. 
	 * @Method설명 : 설치및위탁정보 조회
	 */
	@Override
	public Map<String, Object> selectInstlCnsgnInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmOrgCode");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return mgmtOrgDtlMapper.selectInstlCnsgnInfo(paramMap);
	}
	
	
	
}

