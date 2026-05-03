/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.service.impl;

import java.util.Iterator;
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
import com.cleopatra.protocol.data.ParameterRow;
import com.clipsoft.clipreport.server.service.ExePrintInfo;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.ytosp.portalmng.menuandprogrm.mapper.BannerMngMapper;
import isry.ytosp.portalmng.menuandprogrm.service.BannerMngService;

/**
 * @파일명        : BannerMngServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 8. 25. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 8. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("bannerMngService")
public class BannerMngServiceImpl implements BannerMngService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "bannerMngMapper")
	private BannerMngMapper bannerMngMapper;
	
	/**
	 * @Method명   : selectBannerMngList
	 * @Method설명  : 배너 목록
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 8. 30. 
	 */
	@Override
	public List<Map<String, Object>> selectBannerMngList(Map<String, String> mapParam) throws Exception{
		return bannerMngMapper.selectBannerMngList(mapParam);
	}
	
	/**
	 * @Method명   : selectBannerMngDetail
	 * @Method설명  : 배너 상세조회
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 8. 30. 
	 */
	@Override
	public List<Map<String, Object>> selectBannerMngDetail(Map<String, String> mapParam) throws Exception{
		return bannerMngMapper.selectBannerMngDetail(mapParam);
	}
	
	
	/**
	 * @Method     	: insertBannerMng
	 * @Method설명 	: 배너 등록
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 8. 24.
	 * @상세       	: 
 	 */
	@Override
	public int insertBannerMng(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		
		String loginId = "";
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		
		List<Map<String, String>> dsListMap = paramGroup.getAllRowList();
		
//		LOGGER.debug("size ::: " + dsListMap.size());
		
		Map<String, String> paramMap = dsListMap.get(0);
		paramMap.put("USER_ID", loginId);
		
		int retVal = 0;
		
		LOGGER.debug("insertMap ::: " + paramMap);
		
		retVal = bannerMngMapper.insertBannerMng(paramMap);
		
		return retVal;
	}
	
	/**
	 * @Method     	: deleteBannerMng
	 * @Method설명 	: 배너 상태(삭제) 업데이트
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 04.
	 * @상세       	: 
 	 */
	
	@Override
	public int deleteBannerMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		List<Map<String, String>> dmDtlParam = paramGroup.getAllRowList();
		
		System.out.println("dsListMap = [ " + dmDtlParam.get(0) + " ] ");
		
		Map<String, String> paramMap = dmDtlParam.get(0);
		
		bannerMngMapper.deleteBannerMng(paramMap);
		
		return 0;
		
	}
	
	/**
	 * @Method     	: updateBannerMng
	 * @Method설명 	: 배너 수정
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 5.
	 * @상세       	: 
 	 */
	@Override
	public int updateBannerMng(HttpServletRequest request, DataRequest dataRequest) throws Exception{

		String loginId = "";
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		
		List<Map<String, String>> dsListMap = paramGroup.getAllRowList();
		
		Map<String, String> paramMap = dsListMap.get(0);
		paramMap.put("USER_ID", loginId);
		
		int retVal = 0;
		
		retVal = bannerMngMapper.updateBannerMng(paramMap);
		
		return retVal;
		
	}
	
}
