/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.bizcmmns.cmmns.service.CaseMtgService;

/**
 * @파일명        : CaseMtgController.java
 * @프로그램 설명 : 사례회의 
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 13. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/itgcm/bizcmmns/cmmns")
public class CaseMtgController {
	
	@Resource(name = "caseMtgService")
	private CaseMtgService caseMtgService;	
	
	//@Resource(name = "mgmtCmmnCodeService")
	//private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	/**
	 * @Method명   : selectCaseMtgList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 : 사례회의 조회
	 */
	@RequestMapping(value = "/selectCaseMtgList.do")
	public View selectCaseMtgList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList1 = caseMtgService.selectCaseMtgList(request, dataRequest);

		dataRequest.setResponse("dsCaseMtg", retList1);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectCaseMtgAtdrnlList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 : 사례회의참석자 조회
	 */
	@RequestMapping(value = "/selectCaseMtgAtdrnlList.do")
	public View selectCaseMtgAtdrnlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList2 = caseMtgService.selectCaseMtgAtdrnlList(request, dataRequest);
		dataRequest.setResponse("dsCaseMtgAtdrn", retList2);
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : selectCaseMtgPiclList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 : 사례회의담당자 조회
	 */
	@RequestMapping(value = "/selectCaseMtgPiclList.do")
	public View selectCaseMtgPiclList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList3 = caseMtgService.selectCaseMtgPiclList(request, dataRequest);
		dataRequest.setResponse("dsCaseMtgPic", retList3);		
		
		return new JSONDataView();
	}
	/**
	 * @Method명   : processCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 : 사례회의 저장,수정,삭제
	 */
	@RequestMapping(value = "/processCaseMtgList.do")
	public View processCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		caseMtgService.processCaseMtgList(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method명   : selectGrCaseMtgList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 목록
	 */
	@RequestMapping(value = "/selectGrCaseMtgList.do")
	public View selectGrCaseMtgList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList = caseMtgService.selectGrCaseMtgList(request, dataRequest);

			dataRequest.setResponse("dsList", retList);
		
		return new JSONDataView();
	}	

	/**
	 * @Method명   : selectGrCaseMtgDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 상세
	 */
	@RequestMapping(value = "/selectGrCaseMtgDetail.do")
	public View selectGrCaseMtgDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = caseMtgService.selectGrCaseMtgDetail(request, dataRequest);
		
		dataRequest.setResponse("dsCaseMtg"        , retMap.get("caseMtgList"));		    /* 사례회의*/
		dataRequest.setResponse("dsCaseMtgAtdrn"   , retMap.get("caseMtgAtdrnList"));	    /* 참석자*/
		dataRequest.setResponse("dsCaseMtgPic"     , retMap.get("caseMtgPicList"));	        /* 담당자*/
		dataRequest.setResponse("dsCaseTrprList"   , retMap.get("caseMtgTrprList"));	    /* 사례회의 사례대상자*/
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : processGrCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 8. 
	 * @Method설명 : 집단사례회의 등록
	 */
	@RequestMapping(value = "/processGrCaseMtgList.do")
	public View processGrCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = caseMtgService.processGrCaseMtgList(request, dataRequest);
		
		dataRequest.setResponse("dmSearch"        , retMap);		    /* 재조회*/
		
		return new JSONDataView();
	}	
}
