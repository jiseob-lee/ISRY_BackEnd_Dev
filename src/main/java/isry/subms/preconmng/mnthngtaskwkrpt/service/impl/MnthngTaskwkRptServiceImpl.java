/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.mnthngtaskwkrpt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Formatter;
import isry.subms.preconmng.mnthngtaskwkrpt.mapper.MnthngTaskwkRptMapper;
import isry.subms.preconmng.mnthngtaskwkrpt.service.MnthngTaskwkRptService;

/**
 * @파일명 : MnthngTaskWorkRptServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 6. 10.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 6. 10.
 * @수정내용 : - -
 */
@Service("mnthngTaskwkRptService")
public class MnthngTaskwkRptServiceImpl implements MnthngTaskwkRptService {

	@Resource(name = "mnthngTaskwkRptMapper")
	private MnthngTaskwkRptMapper mnthngTaskwkRptMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectMnthngTaskwkList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 10.
	 * @Method설명 : 월간업무보고 리스트 조회
	 */
	@Override
	public List<Map<String, String>> selectMnthngTaskwkList(DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> mapParam = parameterGroup.getSingleValueMap();

		List<Map<String, String>> resultList = mnthngTaskwkRptMapper.selectMnthngTaskwkList(mapParam);

		return resultList;
	}

	/**
	 * @Method명 : saveMnthngTaskwkRpt
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 월간업무보고 등록/수정/삭제
	 */
	@Override
	public Map<String, Object> saveMnthngTaskwkRpt(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsMnthngOperRpt");

		List<Map<String, String>> insertedRowList = parameterGroup.getInsertedRowList();
		List<Map<String, String>> updatedRowList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> deletedRowList = parameterGroup.getDeletedRowList();

		Map<String, Object> returnMap = new HashMap<String, Object>();

		UserDetailsVO loginUser = userLoginService.getLoginSessionVO(request);

		if (loginUser != null && loginUser.getId() != null && !loginUser.getId().equals("")) {
			if (!deletedRowList.isEmpty()) {
				for (Map<String, String> map : deletedRowList) {
					map.put("LAST_MDFR_ID", loginUser.getId());
					mnthngTaskwkRptMapper.deleteMnthngTaskwkRpt(map);
				}
			}

			if (!insertedRowList.isEmpty()) {
				for (Map<String, String> map : insertedRowList) {
					map.put("RPT_SE_CD", "02");
					map.put("INST_NO", Integer.toString(loginUser.getInstNo()));
					map.put("FRST_RGTR_ID", loginUser.getId());
					map.put("LAST_MDFR_ID", loginUser.getId());
					map.put("APLY_PIC_NO", loginUser.getEnfsnNo());

					mnthngTaskwkRptMapper.saveMnthngTaskwkRpt(map);

					returnMap.put("SRVC_EXCN_BIZ_NO", map.get("SRVC_EXCN_BIZ_NO"));
					returnMap.put("RESRCE_NO", map.get("RESRCE_NO"));
					returnMap.put("INST_NO", map.get("INST_NO"));
					returnMap.put("RPT_SE_CD", map.get("RPT_SE_CD"));
					returnMap.put("TASKWK_RPT_SN", map.get("TASKWK_RPT_SN"));
					returnMap.put("SEMSTR_SE_CD", map.get("SEMSTR_SE_CD"));
					returnMap.put("RPT_MM", map.get("RPT_MM"));
				}
			}

			if (!updatedRowList.isEmpty()) {
				for (Map<String, String> map : updatedRowList) {
					map.put("RPT_SE_CD", "02");
					map.put("INST_NO", Integer.toString(loginUser.getInstNo()));
					map.put("LAST_MDFR_ID", loginUser.getId());

					mnthngTaskwkRptMapper.saveMnthngTaskwkRpt(map);

					returnMap.put("SRVC_EXCN_BIZ_NO", map.get("SRVC_EXCN_BIZ_NO"));
					returnMap.put("RESRCE_NO", map.get("RESRCE_NO"));
					returnMap.put("INST_NO", map.get("INST_NO"));
					returnMap.put("RPT_SE_CD", map.get("RPT_SE_CD"));
					returnMap.put("TASKWK_RPT_SN", map.get("TASKWK_RPT_SN"));
					returnMap.put("SEMSTR_SE_CD", map.get("SEMSTR_SE_CD"));
					returnMap.put("RPT_MM", map.get("RPT_MM"));
				}
			}
		}
		return returnMap;
	}

	/**
	 * @Method명 : selectMnthngTaskwk
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 월간업무보고 상세조회
	 */
	@Override
	public Map<String, List<Map<String, Object>>> selectMnthngTaskwk(HttpServletRequest request, DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = parameterGroup.getSingleValueMap();
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		mapList.put("dsMnthngOperRpt", mnthngTaskwkRptMapper.selectMnthngTaskwkInqCnd(mapParam));
		
		mapList.put("dsMnthngOperHrPrecon", mnthngTaskwkRptMapper.selectMnthngOperHrPrecon(mapParam));
		
		List<Map<String, Object>> resultList = mnthngTaskwkRptMapper.selectMnthngAtendLinkPrecon(mapParam);
		
		for (Map<String, Object> map : resultList) {
			map.put("COMPL_PRD", Formatter.dateFormat(String.valueOf(map.get("BGNG_YMD"))) 
					+ " ~ " + Formatter.dateFormat(String.valueOf(map.get("END_YMD"))));
		}
		
		mapList.put("dsMnthngAtendLinkPrecon", resultList);
		
		return mapList;
	}

	/**
	 * @Method명 : selectMnthngTaskwkSearch
	 * @param mapParam
	 * @return
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 월간업무보고 상세에서 조건으로 검색
	 */
	@Override
	public Map<String, List<Map<String, Object>>> selectMnthngTaskwkSearch(HttpServletRequest request,
			DataRequest dataRequest) throws Exception{

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> mapParam = parameterGroup.getSingleValueMap();
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		mapParam.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

//		mapList.put("dsWeekMng", mnthngTaskwkRptMapper.selectMnthngTaskwkMnthngOper(mapParam));
//		mapList.put("dsPgmInfo", mnthngTaskwkRptMapper.selectMnthngTaskwkProgrmPtcptn(mapParam));
//		mapList.put("dsLinkData", mnthngTaskwkRptMapper.selectMnthngTaskwkLinkPrecon(mapParam));
		mapList.put("dsMnthngOperHrPrecon", mnthngTaskwkRptMapper.selectMnthngOperHrPrecon(mapParam));
		mapList.put("dsMnthngAtendLinkPrecon", mnthngTaskwkRptMapper.selectMnthngAtendLinkPrecon(mapParam));
		return mapList;
	}

	/**
	 * @Method명   : selectCheckResrce
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 4. 
	 * @Method설명 : 등록/수정 전 선택한 자원, 서비스 실행사업 등이 각각 맞물려 있는지 체크
	 */
	@Override
	public void selectCheckResrce(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("CHECK1", mnthngTaskwkRptMapper.selectCheckResrce(paramMap));
		Map<String, Object> dummyMap = mnthngTaskwkRptMapper.selectCheckMnthngTaskwk(paramMap);
		if(!(dummyMap == null)) {
			returnMap.put("CHECK2", dummyMap.get("TASKWK_RPT_SN"));
		}
		dataRequest.setMetadata(true, returnMap);
	}

}
