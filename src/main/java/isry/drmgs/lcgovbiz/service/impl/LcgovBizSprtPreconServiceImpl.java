/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.lcgovbiz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.drmgs.lcgovbiz.mapper.LcgovBizSprtPreconMapper;
import isry.drmgs.lcgovbiz.service.LcgovBizSprtPreconService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : LcgovBizSprtPreconServiceImpl.java
 * @프로그램 설명 : 지자체 사업 지원 현황 ServiceImpl
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2022. 7. 20. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2022. 7. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("lcgovBizSprtPreconService")
public class LcgovBizSprtPreconServiceImpl implements LcgovBizSprtPreconService{

	//@Resource(name="trprInqService")
	//private TrprInqService trprInqService;
	
	@Resource(name = "lcgovBizSprtPreconMapper")
	private LcgovBizSprtPreconMapper lcgovBizSprtPreconMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	// 삭제 예정
	
	/**
	 * @Method명   : selectLcgovBizSprtPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 7. 20. 
	 * @Method설명 : 지자체 사업 지원 현황 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectLcgovBizSprtPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String SRCH_NM = null;
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearchParam");
		
		SRCH_NM = paramGroup.getValue("SRCH_VALUE");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		// 2022.09.23최두일 조회권한
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        List<Map<String, Object>> rtnMap = lcgovBizSprtPreconMapper.selectLcgovBizSprtPreconList(paramMap);
		
		for (Map<String, Object> map : rtnMap) {
			
			if (map.get("FRST_REG_DT") != null && !"".equals(map.get("FRST_REG_DT"))) {
				String frstRegDt = map.get("FRST_REG_DT").toString();
				//System.out.println("frstRegDt = " + frstRegDt);
				//frstRegDt = frstRegDt.substring(0, 4) + "-" + frstRegDt.substring(4, 6) + "-" + frstRegDt.substring(6, 8);
				map.put("FRST_REG_DT", frstRegDt);
			}
			
		}
		return rtnMap;
	}

	/**
	 * @Method명   : selectClienaInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 8. 9. 
	 * @Method설명 : 내담자 기본정보 및 경기도 사업 등록 유무 조회
	 */
	@Override
	public Map<String, Object> selectClienaInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		Map<String, Object> resultMap = lcgovBizSprtPreconMapper.selectClienaInfo(paramMap);
		
		return resultMap;
	}

	/**
	 * @Method명   : selectIndivSprvtList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 8. 10. 
	 * @Method설명 : 개별지원 목록 조회
	 */
	@Override
	public List<Map<String, String>> selectIndivSprvtList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		List<Map<String, String>> resultList = lcgovBizSprtPreconMapper.selectIndivSprvtList(paramMap);
		
		return resultList;
	}

	/**
	 * @Method명   : selectGrDscsnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 8. 11. 
	 * @Method설명 : 집단 서비스 목록 조회
	 */
	@Override
	public List<Map<String, String>> selectGrDscsnList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		List<Map<String, String>> resultList = lcgovBizSprtPreconMapper.selectGrDscsnList(paramMap);
		
		return resultList;
	}

}
