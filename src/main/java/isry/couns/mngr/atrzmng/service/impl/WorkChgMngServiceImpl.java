/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.atrzmng.mapper.WorkChgMngMapper;
import isry.couns.mngr.atrzmng.service.WorkChgMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry2.couns.mngr.atrzmng.mapper.WorkChgMng2Mapper;


@Service
public class WorkChgMngServiceImpl extends IsryBaseServiceImpl implements WorkChgMngService {

	@Resource(name = "workChgMngMapper")
	private WorkChgMngMapper workChgMngMapper;

	@Resource(name = "workChgMng2Mapper")
	private WorkChgMng2Mapper workChgMng2Mapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectWorkChgMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkChgMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {

		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("untTaskwkSeCd", loginVO.getUntTaskwkSeCd());
		
		return workChgMngMapper.selectWorkChgMngList(mapParam);
	}
		
	/**
	 * @Method명   : selectWorkChgMngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkChgMngDetail(Map<String, Object> mapParam) throws Exception {
		return workChgMngMapper.selectWorkChgMngDetail(mapParam);
	}
	
	/**
	 * @Method명   : searchComboBoxAprv
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception {
		return workChgMngMapper.searchComboBoxAprv(mapParam);
	}
	
	/**
	 * @Method명   : processWorkChgMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	@Override
	public int processWorkChgMng(Map<String, Object> mapParam) throws Exception {
		//AYC140 근무변경 허가원 업데이트
		workChgMngMapper.processWorkChgMng1(mapParam);
		if("0".equals(mapParam.get("prePrcsSttsSeCd")) && "2".equals(mapParam.get("prcsSttsSeCd"))) { // 대기 → 승인 상태인 경우
			//System.out.println("대기 → 승인 상태인 경우");
			//AYC100 근무배정표 일별 업데이트
			workChgMngMapper.processWorkChgMng2(mapParam);
			workChgMngMapper.processWorkChgMng3(mapParam);
			if( "325".equals(mapParam.get("targetInstNo")) //업무유형구분코드 값이 325(아웃리치)인 경우
					&& "325".equals(mapParam.get("demandInstNo")) ) {
				//사이버 아웃리치인 경우 추가적으로 AYC120에 업데이트
				workChgMngMapper.processWorkChgMng4(mapParam);
				workChgMngMapper.processWorkChgMng5(mapParam);
			}
		} else if ("2".equals(mapParam.get("prePrcsSttsSeCd")) && "0".equals(mapParam.get("prcsSttsSeCd"))) { // 승인 → 대기 상태인 경우
			//System.out.println("승인 → 대기 상태인 경우");
	    	
	    	// AYC100 근무배정표 일별 업데이트
	    	workChgMngMapper.processWorkChgMng2(mapParam);
			workChgMngMapper.processWorkChgMng3(mapParam);
			if("325".equals(mapParam.get("targetInstNo")) && "325".equals(mapParam.get("demandInstNo"))) {
				// 사이버 아웃리치인 경우 추가적으로 AYC120에 업데이트
				workChgMngMapper.processWorkChgMng4(mapParam);
				workChgMngMapper.processWorkChgMng5(mapParam);
			}
		} else if ("2".equals(mapParam.get("prePrcsSttsSeCd")) && "3".equals(mapParam.get("prcsSttsSeCd"))) { // 승인 → 반려 상태인 경우
			//System.out.println("승인 → 반려 상태인 경우");
			
			// AYC100 근무배정표 일별 업데이트
	    	workChgMngMapper.processWorkChgMng2(mapParam);
			workChgMngMapper.processWorkChgMng3(mapParam);
			if("325".equals(mapParam.get("targetInstNo")) && "325".equals(mapParam.get("demandInstNo"))) {
				// 사이버 아웃리치인 경우 추가적으로 AYC120에 업데이트
				workChgMngMapper.processWorkChgMng4(mapParam);
				workChgMngMapper.processWorkChgMng5(mapParam);
			}
		} else if ("3".equals(mapParam.get("prePrcsSttsSeCd")) && "2".equals(mapParam.get("prcsSttsSeCd"))) { // 반려 → 승인 상태인 경우
			//System.out.println("반려 → 승인 상태인 경우");
			
			// AYC100 근무배정표 일별 업데이트
	    	workChgMngMapper.processWorkChgMng2(mapParam);
			workChgMngMapper.processWorkChgMng3(mapParam);
			if("325".equals(mapParam.get("targetInstNo")) && "325".equals(mapParam.get("demandInstNo"))) {
				// 사이버 아웃리치인 경우 추가적으로 AYC120에 업데이트
				workChgMngMapper.processWorkChgMng4(mapParam);
				workChgMngMapper.processWorkChgMng5(mapParam);
			}
		}
		return 0;
	}
	
	/**
	 * @Method명   : processWorkChgMngBatch
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public String processWorkChgMngBatch(Map<String, Object> mapParam) throws Exception {
		
		String VAR_PRC_SE_CD	= "2";		/** 승인 **/
		String VAR_DEPT_CD		= "325";		/** 아웃리치 코드번호 **/
		String retStrr = "1";
	   	List<Map<String, Object>> list1 = workChgMngMapper.processWorkChgMngBatchList(mapParam);
	   	
	   	//System.out.println("일괄 승인 목록 갯수 === " + list1.size());
	   	if(list1.size() == 0) {
	   		retStrr = "0";
	   	} else {
	   		try {
			   	for (Map<String, Object> map : list1) {
					map.put("VAR_PRC_SE_CD", VAR_PRC_SE_CD);
					map.put("VAR_DEPT_CD", VAR_DEPT_CD);
					map.put("VAR_P_IN_LOGINID", mapParam.get("loginId"));
					workChgMngMapper.processWorkChgMngBatchUpdate1(map);
					//System.out.println("dd1");
					workChgMngMapper.processWorkChgMngBatchUpdate2(map);
					workChgMngMapper.processWorkChgMngBatchUpdate3(map);
					
					if ( map.get("TARGET_DMND_DEPT_CD") == VAR_DEPT_CD ) {
						workChgMngMapper.processWorkChgMngBatchUpdate4(map);
						workChgMngMapper.processWorkChgMngBatchUpdate5(map);
					}
				}
			} catch (Exception e) {
				retStrr = "-1";
			}
	   	}
		
		return retStrr;
	}
	
	/**
	 * @Method명   : processWorkChgMngSms1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public int processWorkChgMngSms1(Map<String, Object> mapParam) throws Exception {
		return workChgMng2Mapper.processWorkChgMngSms1(mapParam);
	}
	
	/**
	 * @Method명   : processWorkChgMngSms2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public int processWorkChgMngSms2(Map<String, Object> mapParam) throws Exception {
		return workChgMngMapper.processWorkChgMngSms2(mapParam);
	}
	
	/**
	 * @Method명   : processWorkChgMngSms3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public int processWorkChgMngSms3(Map<String, Object> mapParam) throws Exception {
		return workChgMngMapper.processWorkChgMngSms3(mapParam);
	}
	
	
	/**
	 * @Method명   : selectWorkChgMngSms
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkChgMngSms(Map<String, Object> mapParam) throws Exception {
		return workChgMngMapper.selectWorkChgMngSms(mapParam);
	}
	
}
