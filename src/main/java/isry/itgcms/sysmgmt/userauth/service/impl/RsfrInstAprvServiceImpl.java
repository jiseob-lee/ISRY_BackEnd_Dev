/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.RsfrInstAprvMapper;
import isry.itgcms.sysmgmt.userauth.mapper.RsfrInstMngMapper;
import isry.itgcms.sysmgmt.userauth.service.RsfrInstAprvService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : RsfrInstAprvServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 9. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 9.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("rsfrInstAprvService")
public class RsfrInstAprvServiceImpl extends IsryBaseServiceImpl implements RsfrInstAprvService {
	
	@Resource(name="rsfrInstAprvMapper")
    private RsfrInstAprvMapper rsfrInstAprvMapper;
	
	@Resource(name="rsfrInstMngMapper")
    private RsfrInstMngMapper rsfrInstMngMapper;	
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : selectRsfrInstAprvList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 자원제공주체기관승인 목록
	 */
	@Override 
	public List<Map<String, Object>> selectRsfrInstAprvList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		log.info("자원제공주체기관승인.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		
		// 암호화 Class
		//ScpDb scDb = new ScpDb();

		// 대표자명, 담당자명
		//paramMap.put("RPRSV_NM_ENCPT",	((paramMap.get("RPRSV_NM") != null) ? scDb.scpEncB64(String.valueOf(paramMap.get("RPRSV_NM"))) : ""));
		//paramMap.put("PIC_NM_ENCPT"  ,  ((paramMap.get("PIC_NM")   != null) ? scDb.scpEncB64(String.valueOf(paramMap.get("PIC_NM")))   : ""));
		
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        /* 권한 구분 추가 리스트 양식 변경 2023.04.13 Taesoo Song */
        String groupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();
        
		char instTypeDiv = groupAuthrtSeCd.charAt(0);
		char roleDiv = groupAuthrtSeCd.charAt(1);
		
		// 접속한 유저의 기관정보. 
		String sUntTaskwk = loginVO.getUntTaskwk();
		
		//여가부, 개발원은 모두 볼 수 있어야 한다. Taesoo Song 2023.04.13
		if (instTypeDiv == '1' || instTypeDiv == '2') {
        //if(!"U15".equals(loginVO.getUntTaskwk())) {
        	paramMap.put("UNT_TASKWK_SE_CD", null);
        } else if (instTypeDiv == '3'){
        	// 업무 담당자 중 총괄관리자, 기관관리자 이면 목록 확인 가능. 
        	if (roleDiv == '1' || roleDiv == '2') {
        		//U09,U10
        		if (sUntTaskwk == "U09" || sUntTaskwk == "U10") {
        			// 이주, 내일 자신 하위(1단계) 기관에서 등록한 자원제공만 승인
        			paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
        			paramMap.put("OGDP_UP_INST_NO", String.valueOf(loginVO.getInstNo()));
        		} else { // 나머지 업무 단에서는 등록한 기관내의 총괄, 기관관리자만 승인 가능.
        			paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
        			paramMap.put("OGDP_INST_NO", String.valueOf(loginVO.getInstNo()));
        		}
        	} else { // 담당자일경우. 목록 자체를 안 보이게 처리.
        		// 메뉴가 실수로 추가되어도 목록 자체를 차단하기 위해 처리
        		paramMap.put("UNT_TASKWK_SE_CD", "U999");
        	}
        }
		
		// 순수자원제공기관 미승인 returnList
		retList = rsfrInstAprvMapper.selectRsfrInstAprvList(paramMap);
		
		return retList;
	}

	/**
	 * @Method명   : saveRsfrInstAprv
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 승인
	 */
	@Override
	public void saveRsfrInstAprv(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId())) {
			userId = userDetailsVO.getId();
		}

		Integer orgCode = 0;
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsAprvInstSave");
		List<Map<String, String>> paramList = parameterGroup.getAllRowList();
		
		for(int i = 0; i < paramList.size(); i++) {
			Map<String, Object> paramMap = new HashMap<>();
			
			if (paramList.get(i).get("INST_NO") != null && !"".equals(paramList.get(i).get("INST_NO"))) {
				orgCode = Integer.valueOf(paramList.get(i).get("INST_NO"));
			}
			
			paramMap.put("INST_NO"     , orgCode);
			paramMap.put("APLCNT_ID"   , userId);
			paramMap.put("AUTZR_ID"    , userId);
			paramMap.put("LAST_MDFR_ID", userId);
			paramMap.put("APRV_STTS_SE_CD", "2"); // 승인
			
			rsfrInstAprvMapper.updateRsfrInstAprv(paramMap);	
			
			Map<String, String> historyMap = new HashMap<>();
			
			historyMap.put("INST_NO", String.valueOf(orgCode));
			historyMap.put("DATAA_CHG_SE_CD", "U");
			rsfrInstMngMapper.insertRsfrInstHistory(historyMap);		
		}
	}

	/**
	 * @Method명   : saveRsfrInstRjct
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 반려
	 */
	@Override
	public void saveRsfrInstRjct(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (userDetailsVO != null && userDetailsVO.getId() != null && !"".equals(userDetailsVO.getId())) {
			userId = userDetailsVO.getId();
		}
		
		Integer orgCode = 0;
		String rejectReason = "";
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsGiveBack");
		List<Map<String, String>> paramList = parameterGroup.getAllRowList();		
		
		for(int i = 0; i < paramList.size(); i++) {
			Map<String, Object> paramMap = new HashMap<>();
			
			if (paramList.get(i).get("INST_NO") != null && !"".equals(paramList.get(i).get("INST_NO"))) {
				orgCode = Integer.valueOf(paramList.get(i).get("INST_NO"));
			}
			rejectReason = paramList.get(i).get("RJCT_CS_CN");
			
			paramMap.put("INST_NO"     , orgCode);
			paramMap.put("RJCT_CS_CN"  , rejectReason);			
			paramMap.put("APLCNT_ID"   , userId);
			paramMap.put("AUTZR_ID"    , userId);
			paramMap.put("LAST_MDFR_ID", userId);
			paramMap.put("APRV_STTS_SE_CD", "3"); // 반려
			
			rsfrInstAprvMapper.updateRsfrInstRjct(paramMap);
			
			Map<String, String> historyMap = new HashMap<>();
			
			historyMap.put("INST_NO", String.valueOf(orgCode));
			historyMap.put("DATAA_CHG_SE_CD", "U");
			/*이력 내용 추가.*/
			historyMap.put("RJCT_CS_CN", rejectReason);
			historyMap.put("APRV_STTS_SE_CD", "3"); // 반려
			rsfrInstMngMapper.insertRsfrInstHistory(historyMap);		
		}		
	}
}
