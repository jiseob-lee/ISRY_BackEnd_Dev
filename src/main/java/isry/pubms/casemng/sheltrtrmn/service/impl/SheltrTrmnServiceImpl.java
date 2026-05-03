/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrtrmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.clipsoft.org.apache.commons.lang.StringUtils;

import isry.itgcms.syscmmn.survsht.service.SurvshtSaveService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;
import isry.pubms.casemng.sheltrtrmn.mapper.SheltrTrmnMapper;
import isry.pubms.casemng.sheltrtrmn.service.SheltrTrmnService;
import isry.pubmt.casemng.slfrlreg.mapper.SlfrlRegMapper;

/**
 * @파일명        : EmrgActnServiceImpl.java
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
@Service("sheltrTrmnService")
public class SheltrTrmnServiceImpl implements SheltrTrmnService{
	
	@Resource(name = "sheltrTrmnMapper")
	private SheltrTrmnMapper sheltrTrmnMapper;

	@Resource(name = "slfrlRegMapper")
	private SlfrlRegMapper slfrlRegMapper;
	
	//추가
	@Resource(name = "survshtSaveService")
	private SurvshtSaveService survshtSaveService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
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
		Map<String, String> map = param.getSingleValueMap();

		List<Map<String, String>> result = sheltrTrmnMapper.selectReqById(map);
		
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
		
		//사례종결(퇴소정보)
		savePstrtrInfoData(request, dataRequest);

		//자립준비척도
		saveSlfrlPrpare(request, dataRequest);

		//입소연장등록
		//saveEntrncXtndData(request, dataRequest);
		
		//설문지등록
		ParameterGroup param2 = dataRequest.getParameterGroup("dsList2");
		List<Map<String, String>> dsList2 = param2.getAllRowList();
		if (dsList2.size() > 0) {
			String sCaseMngNo = dsList2.get(0).get("CASE_MNG_NO");
			String sCaseMngOdrno = dsList2.get(0).get("CASE_MNG_ODRNO");
			saveSrvyData(request, dataRequest, sCaseMngNo, sCaseMngOdrno);
		}	
	}

	/**
	 * @Method명   : saveSlfrlPrpare
	 * @param request
	 * @param dataRequest
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 :
	 */
	private void saveSlfrlPrpare(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request));

		ParameterGroup param = dataRequest.getParameterGroup("dsSlfrlPrpareList");
		List<Map<String, String>> allRowList = param.getAllRowList();
		
		if (allRowList != null && allRowList.size() > 0 
				&& !StringUtils.isEmpty(allRowList.get(0).get("FCTR_RELM_AVRG_SCORE"))) {

			for (Map<String, String> map : allRowList) {
				map.put("CASE_PRGRS_STTS_SE_CD", "04");
				map.put("INST_NO", instNo);
				map.put("USER_ID", userId);

				slfrlRegMapper.saveSlfrlPrpareData(map);
			}
		}
		
	}

	/**
	 * @Method명   : savePstrtrInfoData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 18. 
	 * @Method설명 :
	 */
	private void savePstrtrInfoData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("USER_ID", userId);
			sheltrTrmnMapper.saveData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("USER_ID", userId);
			sheltrTrmnMapper.saveData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			
			sheltrTrmnMapper.deleteData(map);
		}
	}


	/**
	 * @Method명   : saveSrvyData
	 * @param request
	 * @param dataRequest
	 * @param sCaseMngNo
	 * @param sCaseMngOdrno
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	private void saveSrvyData(HttpServletRequest request, DataRequest dataRequest, String sCaseMngNo, String sCaseMngOdrno) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request));
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("CASE_MNG_NO", sCaseMngNo);
		paramMap.put("CASE_MNG_ODRNO", sCaseMngOdrno);
		paramMap.put("ENFSN_NO", enfsnNo);
		paramMap.put("USER_ID", userId);

		//설문지 저장
		//서비스만족도
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyDgstfn"), dataRequest.getParameterGroup("dsSrvyRelmDgstfn"), paramMap, "08");
		survshtSaveService.saveSrvy(request, dataRequest.getParameterGroup("dsSrvyCrtron"), dataRequest.getParameterGroup("dsSrvyRelmCrtron"), paramMap, "23");
		
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

		Map<String, String> paramMap = new HashMap<>();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		
		sheltrTrmnMapper.deleteData(paramMap);
	}

	/**
	 * @Method명   : selectEntrncXtndById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 18. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEntrncXtndById(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = param.getSingleValueMap();

		List<Map<String, String>> result = sheltrTrmnMapper.selectEntrncXtndById(paramMap);
		
		return result; 
	}

	/**
	 * @Method명   : saveEntrncXtndData
	 * @param request
	 * @param dataRequest
	 * @throws Exception 
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 1. 18. 
	 * @Method설명 :
	 */
	public void saveEntrncXtndData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String picNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)); //담당자ID 가져오기
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)); //기관코드 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsEntrncXtnd");

		List<Map<String, String>> insertedRowList = dsList.getInsertedRowList();
		for (Map<String, String> map : insertedRowList) {
			map.put("PIC_NO", picNo);
			map.put("INST_NO", instNo);
			map.put("USER_ID", userId);
			sheltrTrmnMapper.saveEntrncXtndData(map);
		}
		
		List<Map<String, String>> updatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : updatedRowList) {
			map.put("PIC_NO", picNo);
			map.put("INST_NO", instNo);
			map.put("USER_ID", userId);
			sheltrTrmnMapper.saveEntrncXtndData(map);
		}
		
		List<Map<String, String>> deletedRowList = dsList.getDeletedRowList();
		for (Map<String, String> map : deletedRowList) {
			sheltrTrmnMapper.deleteEntrncXtndData(map);
		}
		
	}

	
}
