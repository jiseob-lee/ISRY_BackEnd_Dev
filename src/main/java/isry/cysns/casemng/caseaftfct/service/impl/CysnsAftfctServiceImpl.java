/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.casemng.caseaftfct.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.cysns.casemng.caseaftfct.mapper.CysnsAftfctMapper;
import isry.cysns.casemng.caseaftfct.service.CysnsAftfctService;
import isry.cysns.casemng.casereg.mapper.CysnsRegMapper;
import isry.cysns.casemng.casetrmn.mapper.CysnsTrmnMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.util.CommUtils;

/**
 * @파일명        : CysnsAftfctServiceImpl.java
 * @프로그램 설명 :
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("cysnsAftfctService")
public class CysnsAftfctServiceImpl implements CysnsAftfctService{

	@Resource(name = "cysnsAftfctMapper")
	private CysnsAftfctMapper cysnsAftfctMapper;

	//추가
	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Resource(name = "cysnsRegMapper")
	private CysnsRegMapper cysnsRegMapper;
	
	@Resource(name = "cysnsTrmnMapper")
	private CysnsTrmnMapper cysnsTrmnMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;


	/**
	 * @Method명   : selectReqById
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReqById(DataRequest dataRequest) throws Exception {

		Map<String, String> paramMap = new HashMap<>();
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		paramMap.put("CASE_MNG_NO", param.getValue("CASE_MNG_NO"));
		paramMap.put("CASE_MNG_ODRNO", param.getValue("CASE_MNG_ODRNO"));
		paramMap.put("QUSTNB_TMPT_MNG_NO", param.getValue("QUSTNB_TMPT_MNG_NO"));

		List<Map<String, String>> result = cysnsAftfctMapper.selectReqById(paramMap);
		
		return result;
	}

	/**
	 * @Method명   : saveData
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 :
	 */
	@Override
	public void saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		
		//미디어대상자
		saveMediaTrprData(dataRequest.getParameterGroup("dsList"), userId);
		//인터넷,스마트폰 진단점수
		saveDgnssScoreData(dataRequest.getParameterGroup("dsList"), dataRequest.getParameterGroup("dsDgnssScoreList"), userId, 15);
		
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
	 * @Method명   : saveMediaTrprData
	 * @param dataRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 6. 
	 * @Method설명 :
	 */
	private void saveMediaTrprData(ParameterGroup paramMediaTrpr, String userId) throws Exception {

		//인터넷대상자
		List<Map<String, String>> dsList = paramMediaTrpr.getAllRowList();
		if(dsList.size() > 0) {
			Map<String, String> paramMap = dsList.get(0);
			paramMap.put("USER_ID", userId);
			
			cysnsAftfctMapper.saveData(paramMap);
		}
	}

	/**
	 * @Method명   : saveDgnssScoreData
	 * @param dataRequest
	 * @param userId
	 * @param trprInfoNo
	 * @param caseMngNo
	 * @param caseMngOdrno
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 6. 
	 * @Method설명 :
	 */
	public void saveDgnssScoreData(ParameterGroup paramMediaTrpr, ParameterGroup paramDgnssScore, String userId, int paramCnt) throws Exception {

		List<Map<String, String>> dsList = paramMediaTrpr.getAllRowList();
		if(dsList.size() > 0) {
			String trprInfoNo = dsList.get(0).get("TRPR_INFO_NO");
			String caseMngNo = dsList.get(0).get("CASE_MNG_NO");
			String caseMngOdrno = dsList.get(0).get("CASE_MNG_ODRNO");
			String dgnssExmnMngNo = dsList.get(0).get("DGNSS_EXMN_MNG_NO");
			
			//인터넷/스마트폰진단조사/초1미디어/도박
			List<Map<String, String>> dsDgnssScoreList = paramDgnssScore.getUpdatedRowList();
			if(dsDgnssScoreList.size() > 0) {
				for (Map<String, String> map : dsDgnssScoreList) {
					
					Map<String, String> paramMap = new HashMap<>();

					paramMap.put("DGNSS_EXMN_MNG_NO", dgnssExmnMngNo);
									
					paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "03");
					paramMap.put("DGNSS_EXMN_SE_CD", map.get("DGNSS_EXMN_SE_CD"));
	
					paramMap.put("CASE_MNG_NO", caseMngNo);
					paramMap.put("CASE_MNG_ODRNO", caseMngOdrno);
					paramMap.put("TRPR_INFO_NO", trprInfoNo);
					
					paramMap.put("USER_ID", userId);
					
					for (int j = 0; j < paramCnt; j++) {
						paramMap.put("SCORE_SE_SE_CD", String.valueOf(j+1));
						String seq = String.format("%02d", j+1);
						paramMap.put("DGNSS_SCORE", map.get("DGNSS_SCORE".concat(seq)));
						
						cysnsTrmnMapper.saveDgnssScoreData(paramMap);
					}
				}
			}
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
		
		//설문지 저장
		//고위기 자살
		ParameterGroup param71 = dataRequest.getParameterGroup("dsSrvyHlisk");
		ParameterGroup param72 = dataRequest.getParameterGroup("dsSrvyRelmHlisk");
		
		List<Map<String, String>> dsList71 = param71.getAllRowList();
		List<Map<String, String>> dsList72 = param72.getAllRowList();
		
		if (dsList71.size() > 0 ) {
			Map<String, String> paramMap7 = new HashMap<>();
			paramMap7.put("QUSTNB_MNG_NO", dsList71.get(0).get("QUSTNB_MNG_NO"));
			paramMap7.put("QUSTNB_TMPT_MNG_NO", dsList72.get(0).get("QUSTNB_TMPT_MNG_NO"));
			paramMap7.put("CASE_MNG_NO", sCaseMngNo);
			paramMap7.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			paramMap7.put("ENFSN_NO", enfsnNo);
			paramMap7.put("QUSTNB_SHAPE_SE_CD", "07");
			paramMap7.put("USER_ID", userId);
			
			cysnsRegMapper.saveSrvyTrprData(paramMap7);
			survshtMmnService.savePreSurvshtBySurvsht(request, param71, param72); //SBB500, SBB220, SBB510
		}

		//고위기 자해
		ParameterGroup param81 = dataRequest.getParameterGroup("dsSrvyHlisk2");
		ParameterGroup param82 = dataRequest.getParameterGroup("dsSrvyRelmHlisk2");
		
		List<Map<String, String>> dsList81 = param81.getAllRowList();
		List<Map<String, String>> dsList82 = param82.getAllRowList();
		
		if (dsList81.size() > 0 ) {
			Map<String, String> paramMap8 = new HashMap<>();
			paramMap8.put("QUSTNB_MNG_NO", dsList81.get(0).get("QUSTNB_MNG_NO"));
			paramMap8.put("QUSTNB_TMPT_MNG_NO", dsList82.get(0).get("QUSTNB_TMPT_MNG_NO"));
			paramMap8.put("CASE_MNG_NO", sCaseMngNo);
			paramMap8.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			paramMap8.put("ENFSN_NO", enfsnNo);
			paramMap8.put("QUSTNB_SHAPE_SE_CD", "14");
			paramMap8.put("USER_ID", userId);
			
			cysnsRegMapper.saveSrvyTrprData(paramMap8);
			survshtMmnService.savePreSurvshtBySurvsht(request, param81, param82); //SBB500, SBB220, SBB510
		}

		//고위기 폭력
		ParameterGroup param91 = dataRequest.getParameterGroup("dsSrvyHlisk3");
		ParameterGroup param92 = dataRequest.getParameterGroup("dsSrvyRelmHlisk3");
		
		List<Map<String, String>> dsList91 = param91.getAllRowList();
		List<Map<String, String>> dsList92 = param92.getAllRowList();
		
		if (dsList91.size() > 0 ) {
			Map<String, String> paramMap9 = new HashMap<>();
			paramMap9.put("QUSTNB_MNG_NO", dsList91.get(0).get("QUSTNB_MNG_NO"));
			paramMap9.put("QUSTNB_TMPT_MNG_NO", dsList92.get(0).get("QUSTNB_TMPT_MNG_NO"));
			paramMap9.put("CASE_MNG_NO", sCaseMngNo);
			paramMap9.put("CASE_MNG_ODRNO", sCaseMngOdrno);
			paramMap9.put("ENFSN_NO", enfsnNo);
			paramMap9.put("QUSTNB_SHAPE_SE_CD", "16");
			paramMap9.put("USER_ID", userId);
			
			cysnsRegMapper.saveSrvyTrprData(paramMap9);
			survshtMmnService.savePreSurvshtBySurvsht(request, param91, param92); //SBB500, SBB220, SBB510
		}
		
	}
	
}
