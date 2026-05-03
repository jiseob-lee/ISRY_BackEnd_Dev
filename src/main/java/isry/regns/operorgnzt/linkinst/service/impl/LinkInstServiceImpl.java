/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.operorgnzt.linkinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.Formatter;
import isry.regns.common.authrt.service.LcgovInstAuthService;
import isry.regns.operorgnzt.linkinst.mapper.LinkInstMapper;
import isry.regns.operorgnzt.linkinst.service.LinkInstService;

/**
 * @파일명        : LinkInstServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("linkInstService")
public class LinkInstServiceImpl implements LinkInstService{
	
	@Resource(name = "linkInstMapper")
	private LinkInstMapper linkInstMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	@Resource(name = "lcgovInstAuthService")
	private LcgovInstAuthService lcgovInstAuthService;	
	
	/**
	 * @Method명   : selectKeyValue
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 7. 4. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectKeyValue(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> mngNoMap = new HashMap<>();
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		mngNoMap.put("ESNTL_LINK_MNG_NO", linkInstMapper.selectKeyValue(userId));

		return mngNoMap;

	}

	/**
	 * @Method명   : selectReqList
	 * @param request, dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INST_SE", param.getValue("INST_SE"));
		paramMap.put("ACTVT_YN", param.getValue("ACTVT_YN"));
		paramMap.put("INST_NM", param.getValue("INST_NM"));
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> comMap = new HashMap<>();
		Map<String, Object> paramMap2 = new HashMap<>();

		if (loginVO.getUntTaskwk().equals("U02") &&	
			(loginVO.getGroupAuthrtSeCd().equals("310")       //기관 - 총괄관리자
			|| loginVO.getGroupAuthrtSeCd().equals("320")     //기관 - 기관관리자
			|| loginVO.getGroupAuthrtSeCd().equals("330"))) { //기관 - 사업담당자 청소년상담복지센터는 상위코드 세팅
				comMap = lcgovInstAuthService.createInstSrchParams(request);
				paramMap2.put("GROUP_AUTHRT_SE_CD", "310");  //허수 입력
		} else {
			comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
			paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		}

		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());

		if (loginVO.getUntTaskwk().equals("U02")) {  //청소년상담복지센터는 상위코드 세팅  
		} else {
		}
		/*20230126_강화영_권한 적용_종료*/	
		
		return linkInstMapper.selectReqList(paramMap2);
		
	}

	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {
	
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("ESNTL_LINK_MNG_NO");

		List<Map<String, String>> result = linkInstMapper.selectReqById(mngNo);
		
		for (Map<String, String> map : result) {
			if (!StringUtils.isEmpty(map.get("RPRS_TELNO"))) {
				map.put("RPRS_TELNO", Formatter.phoneFormat(map.get("RPRS_TELNO"), 1));
			}
		}
		
		return result; 
	}

	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));      //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", userId);
			linkInstMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("PIC_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));      //담당자번호
			map.put("INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));     //기관번호
			map.put("USER_ID", userId);
			linkInstMapper.saveData(map);
		}

		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			String mngNo = map.get("ESNTL_LINK_MNG_NO");

			linkInstMapper.deleteData(mngNo);
		}

	}
	
	/**
	 * @Method명   : deleteData
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 10. 
	 * @Method설명 :
	 */
	@Override
	public void deleteData(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("ESNTL_LINK_MNG_NO");

		linkInstMapper.deleteData(mngNo);
	}

	/**
	 * @Method명   : selectYngbgsSurgList
	 * @param request, dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectYngbgsSurgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INST_SE", param.getValue("INST_SE"));
		paramMap.put("ACTVT_YN", param.getValue("ACTVT_YN"));
		paramMap.put("INST_NM", param.getValue("INST_NM"));
		paramMap.put("BGNG_YMD", param.getValue("BGNG_YMD"));
		paramMap.put("END_YMD", param.getValue("END_YMD"));

		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		List<Map<String, String>> resultList = linkInstMapper.selectYngbgsSurgList(paramMap2);
		
		return resultList;
		
	}

}
