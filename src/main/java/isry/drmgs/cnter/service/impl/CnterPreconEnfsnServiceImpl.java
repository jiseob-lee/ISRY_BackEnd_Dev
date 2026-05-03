/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.service.impl;

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
import isry.drmgs.cnter.mapper.CnterPreconEnfsnMapper;
import isry.drmgs.cnter.service.CnterPreconEnfsnService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : CnterPreconEnfsnServiceImpl.java
 * @프로그램 설명 : 센터별 종사자 현황
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 8. 3o. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 3o. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("cnterPreconEnfsnService")
public class CnterPreconEnfsnServiceImpl extends IsryBaseServiceImpl implements CnterPreconEnfsnService {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="cnterPreconEnfsnMapper")
	private CnterPreconEnfsnMapper cnterPreconEnfsnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	List<String> list = new ArrayList<String>(); 
	/**
	 * @Method명   : selectEnfsnInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 8. 30. 
	 * @Method설명 : 센터별 종사자 현황
	 */	
	@Override
	public List<Map<String, Object>> selectEnfsnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> enfsnList = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("FLNM_ENCPT", paramMap.get("FLNM"));
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		/* 20230313_강화영_권한 적용_시작(윤희성 적용) */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		enfsnList = cnterPreconEnfsnMapper.selectEnfsnInfo(paramMap2);
		Map<String, Object> map = new HashMap<>();
		list.clear();
		for(int i = 0; i < enfsnList.toArray().length; i++) {
			map = enfsnList.get(i);
			
			String enfsnNo = map.get("ENFSN_NO").toString();
			list.add(enfsnNo);
			
			if(!"".equals(map.get("BRTH_YMD")) && map.get("BRTH_YMD")!= null ) {
				String birth = map.get("BRTH_YMD").toString();
       			if(birth.length() == 8) {
       				birth = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6, 8);
       			} else {
       				birth = map.get("BRTH_YMD").toString();
       			}
       			map.put("BRTH_YMD", birth);
			}
			enfsnList.set(i, map);
		}
		
		return enfsnList;
	}
	
	/**
	 * @Method명   : selectEnfsnCerti
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 8. 30. 
	 * @Method설명 : 센터별 종사자 국가자격증 현황
	 */	
	public List<Map<String, Object>> selectEnfsnCerti(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> enfsnCertiList = new ArrayList<>();
		if(list.size() > 0) {
			ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
			Map<String, String> paramMap = paramGroup.getSingleValueMap();
			Map<String, Object> subMap = new HashMap<String, Object>(paramMap);
			subMap.put("ENFSN_LIST", list);
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			
			/* 20230313_강화영_권한 적용_시작(윤희성 적용) */
			Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());

			subMap.put("INST_NOS", comMap.get("INST_NOS"));
			subMap.put("ENFSN_NO", loginVO.getEnfsnNo());
			subMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
			/* 20230126_강화영_권한 적용_종료 */
			enfsnCertiList = cnterPreconEnfsnMapper.selectEnfsnCerti(subMap);
		}
		return enfsnCertiList;
	}
	
	/**
	 * @Method명   : selectEnfsnPrvateCerti
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 8. 30. 
	 * @Method설명 : 센터별 종사자 청소년민간자격증 현황
	 */	
	public List<Map<String, Object>> selectEnfsnPrvateCerti(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> enfsnPrvateCertiList = new ArrayList<>();
		if(list.size() > 0) {
			ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
			Map<String, String> paramMap = paramGroup.getSingleValueMap();
			Map<String, Object> subMap = new HashMap<String, Object>(paramMap);
			subMap.put("ENFSN_LIST", list);
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			
			/* 20230313_강화영_권한 적용_시작(윤희성 적용) */
			Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());

			subMap.put("INST_NOS", comMap.get("INST_NOS"));
			subMap.put("ENFSN_NO", loginVO.getEnfsnNo());
			subMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
			/* 20230126_강화영_권한 적용_종료 */
			enfsnPrvateCertiList = cnterPreconEnfsnMapper.selectEnfsnPrvateCerti(subMap);
		}
		return enfsnPrvateCertiList;
	}

	/**
	 * @Method명   : selectTrnngEdu
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 8. 30. 
	 * @Method설명 : 센터별 종사자 전문인력양성교육 현황
	 */	
	public List<Map<String, Object>> selectTrnngEdu(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> enfsnTrnngEduList = new ArrayList<>();
		if(list.size() > 0) {
			ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
			Map<String, String> paramMap = paramGroup.getSingleValueMap();
			Map<String, Object> subMap = new HashMap<String, Object>(paramMap);
			subMap.put("ENFSN_LIST", list);
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			
			/* 20230313_강화영_권한 적용_시작(윤희성 적용) */
			Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());

			subMap.put("INST_NOS", comMap.get("INST_NOS"));
			subMap.put("ENFSN_NO", loginVO.getEnfsnNo());
			subMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
			/* 20230126_강화영_권한 적용_종료 */
			enfsnTrnngEduList = cnterPreconEnfsnMapper.selectTrnngEdu(subMap);
		}
		return enfsnTrnngEduList;
	}
}

