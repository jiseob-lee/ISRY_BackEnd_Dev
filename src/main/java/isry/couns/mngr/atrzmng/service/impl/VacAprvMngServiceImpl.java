/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.atrzmng.mapper.VacAprvMngMapper;
import isry.couns.mngr.atrzmng.service.VacAprvMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry2.couns.mngr.atrzmng.mapper.VacAprvMng2Mapper;

@Service
public class VacAprvMngServiceImpl extends IsryBaseServiceImpl implements VacAprvMngService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VacAprvMngServiceImpl.class);
	
	@Resource(name = "vacAprvMngMapper")
	private VacAprvMngMapper vacAprvMngMapper;
	
	@Resource(name = "vacAprvMng2Mapper")
	private VacAprvMng2Mapper vacAprvMng2Mapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : searchComboBoxAply
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 20. 
	 * @Method설명 : 신청구분코드(APLY_SE_CD) 조회
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxAply() throws Exception {
		return vacAprvMngMapper.searchComboBoxAply();
	}
	
	/**
	 * @Method명   : searchComboBoxAprv
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception {
		return vacAprvMngMapper.searchComboBoxAprv(mapParam);
	}

	/**
	 * @Method명   : searchComboBoxVac
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxVac(Map<String, Object> mapParam) throws Exception {
		return vacAprvMngMapper.searchComboBoxVac(mapParam);
	}
	
	/**
	 * @Method명   : selectVacAprvMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectVacAprvMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("untTaskwkSeCd", loginVO.getUntTaskwkSeCd());
		
		return vacAprvMngMapper.selectVacAprvMngList(mapParam);
	}
	
	/**
	 * @Method명   : selectVacAprvMngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectVacAprvMngDetail(Map<String, Object> mapParam) throws Exception {
		return vacAprvMngMapper.selectVacAprvMngDetail(mapParam);
	}
	
	/**
	 * @Method명   : updateVacAprvMngBatch
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	@Override
	public int updateVacAprvMngBatch(Map<String, Object> mapParam) throws Exception {
		return vacAprvMngMapper.updateVacAprvMngBatch(mapParam);
	}
	
	/**
	 * 
	 * @Method명   : processVacAprvBatch
	 * @Method설명 : 휴가승인관리 - 일괄 승인
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 10. 25. 
	 */
	@Override
	public int processVacAprvBatch(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";			// session정보의 유저ID
		String aprvCdValue = "2";		// '승인' 코드 값
		int resultVal = 0;			// 일괄 승인에 대한 결과값을 담을 변수
		
    	// dmSearch에서 선택된 기간 꺼내기
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearchMap = paramGroup.getSingleValueMap();
		
		//System.out.println("dmSearchMap == " + dmSearchMap);
		
		// session의 로그인 정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		List<Map<String, Object>> vacAprvList = vacAprvMngMapper.selectVacAprvBatchList(dmSearchMap);
		
		if(vacAprvList.size() == 0) {
			//System.out.println("일괄승인할 내역이 없습니다.");
			resultVal = 0;
		} else {
			try {
				//System.out.println("vacAprvList === " + vacAprvList);
				
				// 조회한 목록을 한건씩 승인 처리
				for (Map<String, Object> map : vacAprvList) {
					
					// 변경한 승인구분코드 값과 session 아이디
					map.put("aprvCdValue", aprvCdValue);
					map.put("loginId", loginId);
					
					resultVal = vacAprvMngMapper.updateVacAprvBatch(map);
				}
			} catch (Exception e) {
				resultVal = -1;
			}
		}
		
		return resultVal;
	}
	
	/**
	 * @Method명   : processVacAprvMngSms
	 * @Method설명 : 휴가승인관리 - 문자 일괄 전송
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 11. 2. 
	 */
	@Override
	public Map<String, Object> processVacAprvMngSms(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		String loginId = "";		// 세션 정보 ID
		int resultVal = 0;			// 일괄 승인에 대한 결과값을 담을 변수
		int resultMmsInfoSeq = 0 ;	// ISRY_SMS.MMS_CONTENTS_INFO 테이블에 INSERT 성공 여부를 담을 변수
		int resultMsgData = 0;		// ISRY_SMS.MSG_DATA 테이블에 INSERT 성공 여부를 담을 변수
		int resultUpdVacAprv = 0;	// AYC130 테이블의 UPDATE 성공 여부를 담을 변수
		int successCnt = 0;			// 성공 건수
		int falseCnt = 0;			// 실패 건수
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// dmSearch에서 선택된 기간 꺼내기
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearchMap = paramGroup.getSingleValueMap();
		
		dmSearchMap.put("bgngYmd", dmSearchMap.get("BGNG_YMD").replace("-", ""));
		dmSearchMap.put("endYmd", dmSearchMap.get("END_YMD").replace("-", ""));
		
		//System.out.println("dmSearchMap === " + dmSearchMap);
		
		// session의 로그인 정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if(loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		List<Map<String, Object>> vacAprvList = vacAprvMngMapper.selectVacAprvMngSms(dmSearchMap);
		//System.out.println("일괄문자발송 목록 건수 === " + vacAprvList.size());
		
		if(vacAprvList.size() > 0) {
			
			for (Map<String, Object> map : vacAprvList) {
				try {
					map.put("loginId", loginId);
					
					if (map.get("CALL_TO") != null || !"".equals(map.get("CALL_TO")) || map.get("CALL_TO").toString().isEmpty()) {
						map.replace("CALL_TO", map.get("CALL_TO"));
						
						if(map.get("CALL_TO").toString().contains("-")) {
							map.replace("CALL_TO", map.get("CALL_TO").toString().replace("-", ""));
						}
						
						resultMmsInfoSeq = vacAprvMng2Mapper.insertVacAprvMngSms1(map);
						//System.out.println("resultMmsInfoSeq === " + map.get("CONT_SEQ"));
						
						vacAprvMng2Mapper.insertVacAprvMngSms2(map);
						vacAprvMngMapper.insertVacAprvMngSms3(map);
						
						successCnt++;
						
//						if (resultMmsInfoSeq == 1) {
//							resultMsgData = vacAprvMngMapper.insertVacAprvMngSms2(map);
//							
//							if (resultMsgData == 1) {
//								resultUpdVacAprv = vacAprvMngMapper.insertVacAprvMngSms3(map);
//								if (resultUpdVacAprv == 1) {
//									successCnt++;
//								} else {
//									falseCnt++;
//									continue;
//								}
//							} else {
//								falseCnt++;
//								continue;
//							}
//						} else {
//							falseCnt++;
//							continue;
//						}
						
					} else {
						falseCnt++;
						continue;
					}
				} catch (Exception e) {
					resultVal = -1;
				}
			}
		} else {
			resultVal = 0;
		}
		
		if (vacAprvList.size() == (successCnt + falseCnt)) {
			resultVal = 1;
		} else {
			resultVal = -1;
		}
		
		resultMap.put("successCnt", successCnt);
		resultMap.put("falseCnt", falseCnt);
		resultMap.put("resultVal", resultVal);
		
		return resultMap;
	}
	
	/**
	 * @Method명   : deleteVacAprvMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	@Override
	public int deleteVacAprvMng(Map<String, Object> mapParam) throws Exception {
		return vacAprvMngMapper.deleteVacAprvMng(mapParam);
	}
	
	/**
	 * @Method명   : updateVacAprvMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 21. 
	 * @Method설명 : 휴가승인관리 수정 로직
	 */
	@Override
	public void updateVacAprvMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId = "";			// Session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보가 없습니다.");
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("loginId", loginId);
		
		LOGGER.debug("paramMap ::: " + paramMap);
		
		String aplySeCd = paramMap.get("APLY_SE_CD");
		String aprvSttsSeCd = paramMap.get("APRV_STTS_SE_CD");

		// 1. APLY_SE_CD = '02'인 경우
		if ("02".equals(aplySeCd)) {
			
			String copaIndexSn = "";				// 동일한 휴가신청건의 일련번호
			Map<String, Object> updateMap = new HashMap<String, Object>();
			
			// 1-1. APRV_STTS_SE_CD = '0'인 경우
			if ("0".equals(aprvSttsSeCd)) {
				
				// 1-1-1. 1-2-1. 휴가종류, 휴가시작일자, 휴가종류일자 일치하는 APLY_SE_CD = '01'이고 DEL_YN = 'Y'인 MAX(COPA_INDEX_SN) 데이터 찾기
				paramMap.put("DEL_YN", "Y");
				
				copaIndexSn = vacAprvMngMapper.selectBfeVcatnAplyMaxIndex(paramMap);
				
				// 1-2-2. 찾은 데이터 DEL_YN = 'N" 처리
				if (copaIndexSn != null && !"".equals(copaIndexSn)) {
					updateMap.put("copaIndexSn", copaIndexSn);
					updateMap.put("DEL_YN", "N");
					updateMap.put("loginId", loginId);
					
					vacAprvMngMapper.updateRtrcnRelataDelRpcs(updateMap);
				}
				
			// 1-2. APRV_STTS_SE_CD = '2'인 경우	
			} else if ("2".equals(aprvSttsSeCd)) {
				
				// 1-2-1. 휴가종류, 휴가시작일자, 휴가종류일자 일치하는 APLY_SE_CD = '01'이고 DEL_YN = 'N'인 MAX(COPA_INDEX_SN) 데이터 찾기
				paramMap.put("DEL_YN", "N");
				
				copaIndexSn = vacAprvMngMapper.selectBfeVcatnAplyMaxIndex(paramMap);
				
				// 1-2-2. 찾은 데이터 DEL_YN = 'Y" 처리
				if (copaIndexSn != null && !"".equals(copaIndexSn)) {
					updateMap.put("copaIndexSn", copaIndexSn);
					updateMap.put("DEL_YN", "Y");
					updateMap.put("loginId", loginId);
					
					vacAprvMngMapper.updateRtrcnRelataDelRpcs(updateMap);
				}
			}
		}
		
		// 2. 현재 데이터 상태변경 값에 따른 UPDATE
		vacAprvMngMapper.updateVacAprvMng(paramMap);
		
	}
	
	/**
	 * @Method명   : insertVacAprvMngSms1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public int insertVacAprvMngSms1(Map<String, Object> mapParam) throws Exception {
		return vacAprvMng2Mapper.insertVacAprvMngSms1(mapParam);
	}
	
	/**
	 * @Method명   : insertVacAprvMngSms2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public int insertVacAprvMngSms2(Map<String, Object> mapParam) throws Exception {
		return vacAprvMng2Mapper.insertVacAprvMngSms2(mapParam);
	}
	
	/**
	 * @Method명   : insertVacAprvMngSms3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public int insertVacAprvMngSms3(Map<String, Object> mapParam) throws Exception {
		return vacAprvMngMapper.insertVacAprvMngSms3(mapParam);
	}
	
	/**
	 * @Method명   : insertVacAprvMngGw
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 27. 
	 * @Method설명 : 휴가일정 가져오기 일괄 등록
	 */
	@Override
	public Map<String, Object> insertVacAprvMngGw(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		// 결과값 map
		Map<String, Object> mapResult = new LinkedHashMap<String, Object>();
		
		// 요청 Parameter map
		Map<String, Object> reqParam = new LinkedHashMap<String, Object>();
		
		// 등록 Parameter map
		Map<String, Object> insertParam = new HashMap<String, Object>();
		
		// 등록자 정보 설정
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		insertParam.put("RGTR_ID", loginVO.getId());
		
		// 휴가승인 건수 조회	
		Integer dataAppendCnt = vacAprvMngMapper.selectVacAprvMngGwCount(reqParam);
		
		// 휴가일정 일괄 등록처리
		if (dataAppendCnt > 0) {
			insertParam.putAll(reqParam);
			int result = vacAprvMngMapper.insertVacAprvMngGw(insertParam);
			LOGGER.debug("insertVacAprvMngGw :: insert data cnt={}", result);
		}
		
		// 등록 결과 설정
		mapResult.put("RESULT_OK", "Y");
		mapResult.put("RESULT_MSG", "휴가일정 가져오기 일괄 등록 성공!");
		
		return mapResult;
	}
}
