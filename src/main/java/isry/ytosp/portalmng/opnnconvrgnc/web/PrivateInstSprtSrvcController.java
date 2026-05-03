/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.opnnconvrgnc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.ytosp.portalmng.opnnconvrgnc.service.PrivateInstSprtSrvcService;

/**
 * @파일명        : PrivateInstSprtSrvcController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2023. 8. 26. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2023. 8. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/opnnconvrgnc")
public class PrivateInstSprtSrvcController extends IsryBaseController{
	
	@Autowired
    private PrivateInstSprtSrvcService privateInstSprtSrvcService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@RequestMapping("/selectPrivateInstSprtSrvcList.do")
	public View selectPrivateInstSprtSrvcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("FIRST_RECORD_INDEX", startIndex);
		mapParam.put("PAGE_ROW_COUNT", rowSize);
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("SEARCH_ITEM_CD", searchParam.getValue("SEARCH_ITEM_CD"));		//검색항목
		mapParam.put("SEARCH_ITEM_NM", searchParam.getValue("SEARCH_ITEM_NM"));		//검색항목명
		mapParam.put("INST_NO", searchParam.getValue("INST_NO"));					//답신기관
		mapParam.put("PRGRS_STTS_SE_CD", searchParam.getValue("PRGRS_STTS_SE_CD"));	//상태
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchtime.getValue("startDate"));				//신청시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));					//신청끝날짜
		
		List<Map<String , Object>> dsBoardList = privateInstSprtSrvcService.selectPrivateInstSprtSrvcList(mapParam);
		//if (dsBoardList.size() > 0) totalCount = Integer.parseInt(dsBoardList.get(0).get("TOTAL_COUNT").toString());
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectPrivateInstSprtSrvcListDetail.do")
    public View selectPrivateInstSprtSrvcListDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup param = dataRequest.getParameterGroup("dmDtlParam");//상세
		
		mapParam.put("INDEX_SN", param.getValue("INDEX_SN"));
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> boardDetail = privateInstSprtSrvcService.selectPrivateInstSprtSrvcListDetail(mapParam);    
		
		dataRequest.setResponse("dsBoardList", boardDetail);
		return new JSONDataView();
    }
	
	@RequestMapping("/onLoadPrivateInstSprtSrvc.do")
	public View onLoadPrivateInstSprtSrvc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		/* 진행상태구분코드 */
		List<Map<String, Object>> dsSttsCd = mgmtCmmnCodeService.selectCommonCodeUnit("PRGRS_STTS_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> filterData = new ArrayList<>();
		/* 
		 * 진행상태구분코드 4:완료 값 필터링한 데이터
		 * */
		for(Map<String, Object> data : dsSttsCd) {
			
			Object prgrsSttsCd = data.get("CMMNS_CD_VALUE").toString();
			
			if(prgrsSttsCd != null && (prgrsSttsCd.equals("1")) ||(prgrsSttsCd.equals("2"))||(prgrsSttsCd.equals("3"))) {
				filterData.add(data);
			}
		}
		
		dataRequest.setResponse("dsSttsCd", filterData);
		return new JSONDataView();
	}
	
	@RequestMapping("/savePrivateInstSprtSrvcList.do")
	public View savePrivateInstSprtSrvcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		
		mapParam.put("ANS_INST_NO", dsBoardList.getValue("ANS_INST_NO"));
		mapParam.put("ANS_ENFSN_NO", dsBoardList.getValue("ANS_ENFSN_NO"));
		mapParam.put("ANS_SNDNG_DT", dsBoardList.getValue("ANS_SNDNG_DT"));
		mapParam.put("PRGRS_STTS_SE_CD", dsBoardList.getValue("PRGRS_STTS_SE_CD"));
		mapParam.put("ANS_CN", dsBoardList.getValue("ANS_CN"));
		mapParam.put("INDEX_SN", dsBoardList.getValue("INDEX_SN"));
		privateInstSprtSrvcService.savePrivateInstSprtSrvcList(mapParam);
		return new JSONDataView();
	}
}