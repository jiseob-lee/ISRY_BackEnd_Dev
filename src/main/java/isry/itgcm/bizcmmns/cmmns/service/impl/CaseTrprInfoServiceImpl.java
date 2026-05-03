/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

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

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.CaseTrprInfoMapper;
import isry.itgcm.bizcmmns.cmmns.service.CaseTrprInfoService;
import isry.itgcm.ddnl.monthDdln.mapper.MonthDdlnMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;

/**
* @Class Name  : CaseTrprInfoService.java
* @Description : 사례대상자정보조회 팝업 ServiceImpl Class
*
* @author  : Lee.Jun.Yeong
* @since   : 2022. 06. 29.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 29.  Lee.Jun.Yeong    최초작성
* </pre>
*/
@Service("caseTrprInfoService")
public class CaseTrprInfoServiceImpl extends IsryBaseServiceImpl implements CaseTrprInfoService {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="caseTrprInfoMapper")
    private CaseTrprInfoMapper caseTrprInfoMapper;

	@Resource(name="monthDdlnMapper")
    private MonthDdlnMapper monthDdlnMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	ScpDb   scpDb = new ScpDb();
	Masking mask  = new Masking();

	/**
	* 사례대상자정보 목록조회
	* @param     : Map  : TRPR_NM_ENCPT(대상자명암호화), CASE_TRPR_SLCTN_YMD(사례대상자선정일자)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectCaseTrprInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
//		String sUntTaskwkSeCd = null;	//단위업무구분코드
		String sTrprNm		  = null;	//대상자명
		String targetInst	  = null;	//권한 적용 없이 대상 기관만 가져옴
		
		if (searchParam != null) {
			sTrprNm = searchParam.getValue("TRPR_NM"); //대상자성명
			targetInst = searchParam.getValue("TARGET_INST");
		}
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		

		Map<String, String> paramMap = searchParam.getSingleValueMap();
		if (targetInst != null && !"".equals(targetInst)) paramMap.put("PIC_INST_NO", String.valueOf(loginVO.getInstNo()));

		Map<String, Object> map    = new HashMap<>();
		Map<String, String> reqMap = new HashMap<>();
		
		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());	
		
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		
		rtn = caseTrprInfoMapper.selectCaseTrprInfoList(paramMap2);
//		for(int i=0; i<rtn.toArray().length; i++) {
//			map = rtn.get(i);
//
//			//사례대상자별 마감일자 조회
//			reqMap.clear();
//			reqMap.put("CASE_MNG_NO"   	 , String.valueOf(map.get("CASE_MNG_NO")));
//			reqMap.put("CASE_MNG_ODRNO"	 , String.valueOf(map.get("CASE_MNG_ODRNO")));
//			reqMap.put("UNT_TASKWK_SE_CD", String.valueOf(map.get("UNT_TASKWK_SE_CD")));
//
//			List<Map<String, Object>> ddlnRtn = monthDdlnMapper.selectCaseMngDdlnCrtrInfo(reqMap);
//			if(ddlnRtn.size() > 0) {
//				Map<String, Object> ddlnMap = new HashMap<>();
//				ddlnMap = ddlnRtn.get(0);
//
//				map.put("DDLN_CRTR_YMD"		   , ddlnMap.get("DDLN_CRTR_YMD"));			//마감기준년월
//				map.put("INST_NO"			   , ddlnMap.get("INST_NO"));				//기관번호
//				map.put("UNT_TASKWK_SE_CD"	   , ddlnMap.get("UNT_TASKWK_SE_CD"));		//단위업무구분코드
//				map.put("DDLN_YM"			   , ddlnMap.get("DDLN_YM"));				//마감년월
//				map.put("DDLN_YN"			   , ddlnMap.get("DDLN_YN"));				//마감여부
//				map.put("DDLN_APLCN_CRTR_SE_CD", ddlnMap.get("DDLN_APLCN_CRTR_SE_CD"));	//마감적용기준구분코드
//			}
//
//			//개인정보 복호화
//			if (map.get("TRPR_NM")	     != null) map.put("TRPR_NM"	 	 , mask.nameMasking(scpDb.scpDecB64(map.get("TRPR_NM").toString())));	  //사례대상자명
//			if (map.get("CASE_PIC_NM")   != null) map.put("CASE_PIC_NM"  , mask.nameMasking(scpDb.scpDecB64(map.get("CASE_PIC_NM").toString()))); //사례담당자명
//			if (map.get("TRPR_BRTH_YMD") != null) map.put("TRPR_BRTH_YMD", mask.birthMaskingDay(map.get("TRPR_BRTH_YMD").toString())); 			  //대상자출생일자
//
//			if (map.get("EML_ADDR_ENCPT") != null) map.put("EML_ADDR_ENCPT" , mask.emailMasking(scpDb.scpDecB64(map.get("EML_ADDR_ENCPT").toString())));   //이메일주소
//			if (map.get("MSNGR_ID_ENCPT") != null) map.put("MSNGR_ID_ENCPT" , mask.msngrIdMasking(scpDb.scpDecB64(map.get("MSNGR_ID_ENCPT").toString()))); //메신저아이디
//			
//			if (map.get("MBL_TELNO_ENCPT") != null) map.put("MBL_TELNO_ENCPT", mask.phoneMasking(Formatter.phoneFormat(scpDb.scpDecB64(map.get("MBL_TELNO_ENCPT").toString()), 1))); //휴대전화번호
//			if (map.get("TRPR_TELNO") 	   != null) map.put("TRPR_TELNO"     , Formatter.phoneFormat(map.get("TRPR_TELNO").toString(), 1));											 //대상자전화번호
//			
//			rtn.set(i, map);
//		}

		return rtn;
	}

}
