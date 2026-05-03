/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.uneart.notregcasedscsn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.clipsoft.org.apache.commons.lang.StringUtils;

import isry.subms.uneart.notregcasedscsn.mapper.NotRegCaseDscsnMapper;
import isry.subms.uneart.notregcasedscsn.service.NotRegCaseDscsnService;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.DateUtil;

/**
 * @파일명        : TlphonDscsnServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("notRegCaseDscsnService")
public class NotRegCaseDscsnServiceImpl implements NotRegCaseDscsnService {
	
	@Resource(name = "notRegCaseDscsnMapper")
	private NotRegCaseDscsnMapper tlphonDscsnMapper; 
	
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명   : selectKeyValue
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectKeyValue(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, String> mngNoMap = new HashMap<>();
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기

		mngNoMap.put("CO13_MNG_NO", tlphonDscsnMapper.selectKeyValue(userId));

		return mngNoMap;
	}

	/**
	 * @Method명   : selectReqList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		//Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		Map<String, String> paramMap = param.getSingleValueMap();

		/*20230126_강화영_권한 적용_시작*/
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		paramMap2.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		List<Map<String, Object>> result = tlphonDscsnMapper.selectReqList(paramMap2);
		
	    //조건 - 대상자명, 담당자명
		// 쿼리에서 수정하는걸로 변경 
	    // List<Map<String, String>> finalResult = getTrprAndPic(paramMap, result);
		
		return result;
	}

	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("CO13_MNG_NO");

		List<Map<String, String>> result = tlphonDscsnMapper.selectReqById(mngNo);

		return result; 

	}
	
	/**
	 * @Method명   : selectCo13DtlById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCo13DtlById(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("CO13_MNG_NO");

		List<Map<String, String>> result = tlphonDscsnMapper.selectCo13DtlById(mngNo);
		
		return result; 
	}
	
	
	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		//1388전화상담
		saveCo13Data(request, dataRequest);
		//1388전화상담 문제유형
		saveCo13DtlData(request, dataRequest);
		
	}

	/**
	 * @Method명   : saveCo13Data
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 :
	 */
	private void saveCo13Data(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
//			map.put("PIC_NO", enfsnNo);      //담당자번호
//			map.put("INST_NO", instNo);     //기관번호
			map.put("USER_ID", userId);
			map.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
			map.put("SRVC_RESRCE_LCLAS_SE_CD", "01");
			tlphonDscsnMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
//			map.put("PIC_NO", enfsnNo);      //담당자번호
//			map.put("INST_NO", instNo);     //기관번호
			map.put("USER_ID", userId);
			tlphonDscsnMapper.saveData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			String mngNo = map.get("CO13_MNG_NO");
			tlphonDscsnMapper.deleteData(mngNo);
			tlphonDscsnMapper.deleteCo13DtlAllData(mngNo);
		}
	}
	
	/**
	 * @Method명   : saveCo13Dtl
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 :
	 */
	private void saveCo13DtlData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsCaseYngbgs");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("USER_ID", userId);
			tlphonDscsnMapper.saveCo13DtlData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("USER_ID", userId);
			tlphonDscsnMapper.saveCo13DtlData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			tlphonDscsnMapper.deleteCo13DtlData(map);
		}
	}


	/**
	 * @Method명   : deleteData
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public void deleteData(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String mngNo = param.getValue("CO13_MNG_NO");

		tlphonDscsnMapper.deleteData(mngNo);

	}

	/**
	 * @Method명   : saveUneart
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 :
	 */
	@Override
	public void saveUneart(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		
//		public int insertDscsnUneartDetail(Map<String, String> paramMap) throws Exception;
		
		// 초기상담번호(ER) 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();
		
		seqMap.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));
		seqMap.put("RENU_NO_SE_CD", "ER");				// 초기상담번호 채번코드
		seqMap.put("RENU_YMD", DateUtil.getToday());	// 현재일자
		
		// 채번서비스 호출
		valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);
		String eryyDscsnNo = String.valueOf(valMap.get("RENU_NO"));	// 초기상담번호(ER) 발번
		Integer actnSn = tlphonDscsnMapper.selectDscsnUneartActnSn(eryyDscsnNo);  //조치일련번호
		
		List<Map<String, String>> allRowList = dsList.getAllRowList();
		
		//log.info("allRowList.size={}", allRowList.size());
		
		for (Map<String, String> map : allRowList) {
			
			map.put("ERYY_DSCSN_NO", eryyDscsnNo);                      //초기상담번호
			map.put("ACTN_SN", String.valueOf(actnSn));                 //조치일련번호
			map.put("UNT_TASKWK_SE_CD", CommUtils.getUntTaskwk(userLoginService.getLoginSessionVO(request))); //단위업무구분코드
			map.put("CONSTT_NO", CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)));          //상담자번호
			map.put("DSCSN_COURS_SE_CD", "03");                           //상담경로구분코드-전화상담
			map.put("UNEART_MKCTT_MTHD_SE_CD", "03");                     //발굴접촉방법구분코드-전화
			map.put("CONSTT_INST_NO", CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)));      //상담자기관번호
			map.put("USER_ID", CommUtils.getUserId(userLoginService.getLoginSessionVO(request)));             //최초등록자
			
			if (StringUtils.isEmpty(map.get("DSCSN_CN"))) {
				map.put("DSCSN_CN", "1388전화상담");                       //상담내용 필요 컬럼
			}

			tlphonDscsnMapper.insertDscsnUneartDetail(map);  //초기상담
			tlphonDscsnMapper.insertDscsnUneartActn(map);    //조치등록
			tlphonDscsnMapper.updateUneartData(map.get("CO13_MNG_NO"));   //1388전화상담 발굴등록일자
			
		}
		
	}

}
