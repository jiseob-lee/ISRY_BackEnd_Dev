/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.bgtprfmnc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.aimns.opermgmt.bgtprfmnc.mapper.BgtPrfmncMapper;
import isry.aimns.opermgmt.bgtprfmnc.service.BgtPrfmncService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

/**
 * @파일명 : BgtPrfmncServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 6. 27.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 6. 27.
 * @수정내용 : - -
 */
@Service("bgtPrfmncService")
public class BgtPrfmncServiceImpl implements BgtPrfmncService {

	@Resource(name = "bgtPrfmncMapper")
	private BgtPrfmncMapper bgtPrfmncMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectBgtPrfmncList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 6.
	 * @Method설명 : 예산실적 리스트 조회
	 */
	@Override
	public List<Map<String, String>> selectBgtPrfmncList(DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("BIZ_YR", parameterGroup.getValue("BIZ_YR"));
		mapParam.put("INST_NO", parameterGroup.getValue("INST_NO"));
		mapParam.put("RESRCE_NO", parameterGroup.getValue("RESRCE_NO"));
		mapParam.put("BGT_IMPL_CL_SE_CD", parameterGroup.getValue("BGT_IMPL_CL_SE_CD"));
		mapParam.put("APRV_STTS_SE_CD", parameterGroup.getValue("APRV_STTS_SE_CD"));

		List<Map<String, String>> mapList = bgtPrfmncMapper.selectBgtPrfmncList(mapParam);
		if (mapList != null) {
			for (Map<String, String> map : mapList) {
				map.replace("APLCNT_NM", Masking.nameMasking(map.get("APLCNT_NM")));
				map.replace("APRV_PIC_NM", Masking.nameMasking(map.get("APRV_PIC_NM")));
			}
		}
		return mapList;
	}

	/**
	 * 
	 * @Method명 : selectBgtPrfmnc
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 상세조회
	 */
	@Override
	public void selectBgtPrfmnc(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("BIZ_YR", parameterGroup.getValue("BIZ_YR"));
		mapParam.put("INST_NO", parameterGroup.getValue("INST_NO"));
		mapParam.put("RESRCE_NO", parameterGroup.getValue("RESRCE_NO"));
		mapParam.put("BGT_IMPL_CL_SE_CD", parameterGroup.getValue("BGT_IMPL_CL_SE_CD"));
		mapParam.put("APRV_STTS_SE_CD", parameterGroup.getValue("APRV_STTS_SE_CD"));
		mapParam.put("POPHOST", parameterGroup.getValue("POPHOST"));

		List<Map<String, String>> mapList = bgtPrfmncMapper.selectBgtPrfmnc(mapParam);

		for (Map<String, String> map : mapList) {
			map.replace("APLCNT_NM", map.get("APLCNT_NM"));
			map.replace("APRV_PIC_NM", map.get("APRV_PIC_NM"));
		}

		dataRequest.setResponse("dsBgtImplInfo", mapList);
		dataRequest.setResponse("dsBgtImplList", mapList);
	}

	/**
	 * @Method명 : selectBgtPrfmncOnLoad
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 14.
	 * @Method설명 : 예산실적 등록/수정 콤보 조회
	 */
	@Override
	public List<Map<String, Object>> selectBgtPrfmncOnLoad(HttpServletRequest request) throws Exception {

		List<Map<String, Object>> returnList = bgtPrfmncMapper.selectBgtImplCmmnCode();

		return returnList;
	}

	/**
	 * @Method명 : saveBgtPrfmnc
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 등록/수정/삭제
	 */
	@Override
	public void saveBgtPrfmnc(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dsBgtImplInfo = dataRequest.getParameterGroup("dsBgtImplInfo");
		ParameterGroup dsBgtImplList = dataRequest.getParameterGroup("dsBgtImplList");
		List<Map<String, String>> insertedBgtImplInfo = dsBgtImplInfo.getInsertedRowList();
		List<Map<String, String>> insertedDsBgtImplList = dsBgtImplList.getInsertedRowList();
		List<Map<String, String>> updatedDsBgtInfo = dsBgtImplInfo.getUpdatedRowList();
		List<Map<String, String>> updatedDsBgtImplList = dsBgtImplList.getUpdatedRowList();
		List<Map<String, String>> deletedBgtImplInfo = dsBgtImplInfo.getDeletedRowList();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = loginVO.getId(); // 유저아이디

		// 등록/수정 후 리턴받을 데이터 저장
		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 예산실적 삭제
		if (!deletedBgtImplInfo.isEmpty()) {
			deletedBgtImplInfo.get(0).put("USER_ID", userId);
			deletedBgtImplInfo.get(0).put("DEL_YN", "Y");
			
			bgtPrfmncMapper.insertBgtPrfmncHstr(deletedBgtImplInfo.get(0)); // 예산실적 이력 입력
			bgtPrfmncMapper.deleteBgtPrfmnc(deletedBgtImplInfo.get(0)); //예산실적 삭제
			bgtPrfmncMapper.insertBgtPrfmncAmountHstrDel(deletedBgtImplInfo.get(0));//예산실적상세 이력 입력
			bgtPrfmncMapper.deleteBgtPrfmncAmount(deletedBgtImplInfo.get(0)); //예산실적상세 삭제
			
		}

		// 예산실적 수정
		if (!updatedDsBgtInfo.isEmpty()) {
			updatedDsBgtInfo.get(0).put("USER_ID", loginVO.getId());
			bgtPrfmncMapper.updateBgtPrfmncInfo(updatedDsBgtInfo.get(0));
			bgtPrfmncMapper.insertBgtPrfmncHstr(updatedDsBgtInfo.get(0));

			if (!updatedDsBgtInfo.get(0).get("RESRCE_NO").equals(updatedDsBgtInfo.get(0).get("RESRCE_NO__origin"))
					|| !updatedDsBgtInfo.get(0).get("BGT_IMPL_CL_SE_CD").equals(updatedDsBgtInfo.get(0).get("BGT_IMPL_CL_SE_CD__origin"))) {
				bgtPrfmncMapper.updateBgtPrfmncAmoutInfo(updatedDsBgtInfo.get(0));
				bgtPrfmncMapper.insertBgtPrfmncAmountHstrUpdate(updatedDsBgtInfo.get(0));
			}

			returnMap.put("RESRCE_NO", updatedDsBgtInfo.get(0).get("RESRCE_NO"));
			returnMap.put("BGT_IMPL_CL_SE_CD", updatedDsBgtInfo.get(0).get("BGT_IMPL_CL_SE_CD"));
		}
		if (!updatedDsBgtImplList.isEmpty()) {
			for (Map<String, String> map : updatedDsBgtImplList) {
				map.put("USER_ID", userId);
			}
			bgtPrfmncMapper.insertBgtPrfmncAmountHstr(updatedDsBgtImplList);
			bgtPrfmncMapper.updateBgtPrfmncAmount(updatedDsBgtImplList);
			
			returnMap.put("RESRCE_NO", updatedDsBgtImplList.get(0).get("RESRCE_NO"));
			returnMap.put("BGT_IMPL_CL_SE_CD", updatedDsBgtImplList.get(0).get("BGT_IMPL_CL_SE_CD"));
		}

		// 예산실적 입력

		if (!insertedBgtImplInfo.isEmpty()) {
			insertedBgtImplInfo.get(0).put("USER_ID", userId);
			bgtPrfmncMapper.insertBgtPrfmnc(insertedBgtImplInfo.get(0));
			bgtPrfmncMapper.insertBgtPrfmncHstr(insertedBgtImplInfo.get(0));
			
			returnMap.put("BIZ_YR", insertedBgtImplInfo.get(0).get("BIZ_YR"));
			returnMap.put("INST_NO", insertedBgtImplInfo.get(0).get("INST_NO"));
			returnMap.put("RESRCE_NO", insertedBgtImplInfo.get(0).get("RESRCE_NO"));
			returnMap.put("BGT_IMPL_CL_SE_CD", insertedBgtImplInfo.get(0).get("BGT_IMPL_CL_SE_CD"));
			returnMap.put("APRV_STTS_SE_CD", insertedBgtImplInfo.get(0).get("APRV_STTS_SE_CD"));
		}

		if (!insertedDsBgtImplList.isEmpty()) {
			for (Map<String, String> map : insertedDsBgtImplList) {
				map.put("RESRCE_NO", insertedBgtImplInfo.get(0).get("RESRCE_NO"));
				map.put("BGT_IMPL_CL_SE_CD", insertedBgtImplInfo.get(0).get("BGT_IMPL_CL_SE_CD"));
				map.put("USER_ID", userId);
			}
			bgtPrfmncMapper.insertBgtPrfmncAmount(insertedDsBgtImplList);
			bgtPrfmncMapper.insertBgtPrfmncAmountHstr(insertedDsBgtImplList);
		}

		dataRequest.setResponse("dmParam", returnMap);
	}

	/**
	 * @Method명 : selectBgtPrfmncStatusList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 18.
	 * @Method설명 : 예산실적 일괄조회
	 */
	@Override
	public void selectBgtPrfmncStatusList(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("BIZ_YR", parameterGroup.getValue("BIZ_YR"));
		mapParam.put("INST_NO", parameterGroup.getValue("INST_NO"));
		mapParam.put("RESRCE_NO", parameterGroup.getValue("RESRCE_NO"));

		dataRequest.setResponse("dsList", bgtPrfmncMapper.selectBgtPrfmncStatusList(mapParam));

	}

	/**
	 * @Method명   : selectBgtPrfmncExist
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 24. 
	 * @Method설명 : 예산실적 중복 체크
	 */
	@Override
	public void selectBgtPrfmncExist(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmCheckExistParam");

		Map<String, String> mapParam = parameterGroup.getSingleValueMap();
		Map<String, Object> returnMap = bgtPrfmncMapper.selectBgtPrfmncExist(mapParam);
		dataRequest.setResponse("dmCheckExist", returnMap);
		
	}
}
