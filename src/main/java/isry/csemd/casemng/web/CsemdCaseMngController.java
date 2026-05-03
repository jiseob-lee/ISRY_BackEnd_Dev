/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.casemng.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.csemd.casemng.service.CsemdCaseMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;

/**
 * @파일명     	: CsemdCaseMngController.java
 * @프로그램 설명 	: 사례관리 내 국립청소년디딤센터 고유 영역
 * - 
 * - 
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 9. 13.
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 9. 13.
 * @수정내용    	: 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/csemd/casemng")
public class CsemdCaseMngController extends IsryBaseController {
	
//	@Resource(name = "mgmtCmmnCodeService")
//	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "csemdCaseMngService")
	private CsemdCaseMngService csemdCaseMngService;

	/**
	 * 사례관리_계획 상세조회
	 * @Method명   : selectCsemdCaseMngPlanDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/selectCsemdCaseMngPlanDetail.do")
	@ResponseBody
	public View selectCsemdCaseMngPlanDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		List<Map<String, Object>> resultList = csemdCaseMngService.selectCaseMngPlanDetail(request, dataRequest);

		dataRequest.setResponse("dsIndivPlan" , resultList);

		return new JSONDataView();
	}

	/**
	 * 사례관리_계획 상세정보 저장
	 * @Method명   : processCsemdCaseMngPlanDetailSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/processCsemdCaseMngPlanDetailSave.do")
	@ResponseBody
	public View processCsemdCaseMngPlanDetailSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
		throws Exception {

		csemdCaseMngService.saveCaseMngPlanDetail(request, dataRequest);

		return new JSONDataView();
	}
	
	/**
	 * 약물복용 정보 조회
	 * @Method명   : selectDrfstfTakngInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/selectDrfstfTakngInfo.do")
	@ResponseBody
	public View selectDrfstfTakngInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		List<Map<String, Object>> resultList = csemdCaseMngService.selectDrfstfTakngInfo(request, dataRequest);

		dataRequest.setResponse("dsDrfstfTakngInfo" , resultList);

		return new JSONDataView();
	}

	/**
	 * 사례관리_등록 상세정보 저장
	 * @Method명   : processCsemdCaseMngRegDetailSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/processCsemdCaseMngRegDetailSave.do")
	@ResponseBody
	public View processCsemdCaseMngRegDetailSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
		throws Exception {

		Map<String, String> dmParam = csemdCaseMngService.saveCaseMngRegDetail(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}

}
