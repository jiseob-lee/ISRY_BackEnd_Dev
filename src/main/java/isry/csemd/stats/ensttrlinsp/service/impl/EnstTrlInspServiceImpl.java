/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.ensttrlinsp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.csemd.stats.ensttrlinsp.mapper.EnstTrlInspMapper;
import isry.csemd.stats.ensttrlinsp.service.EnstTrlInspService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : EnstTrlInspServiceImpl.java
 * @프로그램 설명 : 입교생심리검사 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 7.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 7.
 * @수정내용 : - -
 */
@Service("enstTrlInspService")
public class EnstTrlInspServiceImpl implements EnstTrlInspService {

	@Resource(name = "enstTrlInspMapper")
	private EnstTrlInspMapper enstTrlInspMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectQesitm
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 문항정보 조회
	 */
	public List<Map<String, Object>> selectQesitm(DataRequest dataRequest) throws Exception {
		String qustnbKndSeCd = dataRequest.getParameter("QUSTNB_KND_SE_CD");

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("QUSTNB_KND_SE_CD", qustnbKndSeCd);

		List<Map<String, Object>> resultMap = enstTrlInspMapper.selectQesitm(reqMap);
		for (Map<String, Object> map : resultMap) {
			Integer QesitmNo = Integer.valueOf(String.valueOf(map.get("QESITM_SQNCE"))) + 1;
			String QesitmCn = String.valueOf(map.get("QESITM_CN"));
			map.replace("QESITM_CN", QesitmNo + ". " + QesitmCn);
		}

		return resultMap;
	}

	/**
	 * @Method명 : selectAwarExmn
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 인지도조사 조회
	 */
	public void selectAwarExmn(DataRequest dataRequest) throws Exception {

		List<Map<String, String>> dsQesitmInfo = dataRequest.getParameterGroup("dsQesitmInfo").getAllRowList();
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		for (int idx = 0; idx < dsQesitmInfo.size(); idx++) {
			Map<String, String> map = dsQesitmInfo.get(idx);
			for (String key : dmSearch.keySet()) {
				map.put(key, dmSearch.get(key));
			}

			dataRequest.setResponse("dsAwarExmnQesitm" + idx, enstTrlInspMapper.selectAwarExmn(map));
		}
	}

	/**
	 * @Method명   : selectEmtGhvr
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 8. 
	 * @Method설명 : 정서행동검사통계
	 */
	@Override
	public void selectEmtGhvr(DataRequest dataRequest) throws Exception {
		
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		
		//정서행동검사통계 - 내재화증상
		if (dmSearch.get("QUSTNB_CD").equals("3639")) {
			dmSearch.put("QUSTNB_CD", "012");
		}	
		else {	//정서행동검사통계 - 외현화증상
			dmSearch.put("QUSTNB_CD", "015");
			dmSearch.put("QUSTNB_CD2", "018");
		}
		
		dataRequest.setResponse("dsList", enstTrlInspMapper.selectEmtGhvr(dmSearch));
		
	}
	
	/**
	 * @Method명   : selectPopulStatsInfo
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 인구통계학적정보 통계
	 */
	@Override
	public void selectPopulStatsInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		// 보호자구분 및 기타
		dataRequest.setResponse("dsPopulStatsInfoPrtcrSeEtc", enstTrlInspMapper.selectPopulStatsInfoPrtcrSeEtc(dmSearch));
		
		// 성별 구분
		dataRequest.setResponse("dsPopulStatsInfoSxdc", enstTrlInspMapper.selectPopulStatsInfoSxdc(dmSearch));
				
		// 학력 구분
		dataRequest.setResponse("dsPopulStatsInfoAcbg", enstTrlInspMapper.selectPopulStatsInfoAcbg(dmSearch));
				
		// 거주지역 구분
		dataRequest.setResponse("dsPopulStatsInfoResdnRgn", enstTrlInspMapper.selectPopulStatsInfoResdnRgn(dmSearch));
				
		// 주거지 구분
		dataRequest.setResponse("dsPopulStatsInfoReside", enstTrlInspMapper.selectPopulStatsInfoReside(dmSearch));
		
	}

	/**
	 * @Method명   : selectTrlEmtInsp
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 심리정서검사
	 */
	@Override
	public void selectTrlEmtInsp(DataRequest dataRequest) throws Exception {
Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		
		//심리정서검사-대상자용
		if (dmSearch.get("QUSTNB_CD").equals("4763")) {
			dmSearch.put("QUSTNB_CD", "020");
		}	
		else {	//심리정서검사-관찰자용
			dmSearch.put("QUSTNB_CD", "023");
			dmSearch.put("QUSTNB_CD2", "026");
		}
		
		dataRequest.setResponse("dsList", enstTrlInspMapper.selectTrlEmtInsp(dmSearch));
	}
}
