/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.crisisyngbgscafemng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.constt.crisisyngbgscafemng.service.BbsCafeDataListService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;


@Controller
@Api(value = "bbsCafeDataListController Controller")
@RequestMapping("/constt/crisisyngbgscafemng")
public class BbsCafeDataListController extends IsryBaseController {

	@Autowired
    private BbsCafeDataListService bbsCafeDataListService;

//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/dscmb.do")
    public View dscmb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		dataRequest.setResponse("dsCmb", bbsCafeDataListService.BbscafedataCode("CRISIS_CAFE_CYBER_OUTRC_SE_CD"));
     
		return new JSONDataView();
    }
//	/constt/crisisyngbgscafemng/selectBbsCafeWorkList.do
	@RequestMapping("/selectInqBbscafedataList.do")
    public View selectInqBbscafedataList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
                   
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
//		String loginNm = loginVO.getUserName();
//		System.out.println("loginVO : "+loginVO.getUserName());
//		System.out.println("loginVO : "+loginVO.getIp());
//		System.out.println("loginVO : "+loginVO.getBirthdate());
//		System.out.println("loginVO : "+loginVO.getEmail());
//		System.out.println("loginVO : "+loginVO.getGender());
//		System.out.println("loginVO : "+loginVO.getId());
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		dataRequest.setResponse("dsCmb", bbsCafeDataListService.BbscafedataCode("CRISIS_CAFE_CYBER_OUTRC_SE_CD"));
        
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");//조회
		
		mapParam.put("CRISIS_CAFE_CYBER_OUTRC_SE_CD_COM", dtlParam.getValue("CRISIS_CAFE_CYBER_OUTRC_SE_CD_COM"));
		mapParam.put("BBSCTT_TTL_NM", dtlParam.getValue("BBSCTT_TTL_NM"));
		mapParam.put("HPGE_URL_NM", dtlParam.getValue("HPGE_URL_NM"));
		
	
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		List<Map<String, Object>> dsBoardList = bbsCafeDataListService.selectInqBbscafedataList(mapParam);
		
		if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}

		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", resPage);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
    }

	@RequestMapping("/selectBbscafedataDetail.do")
    public View selectBbscafedataDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> detailList = bbsCafeDataListService.selectBbscafedataDetail(mapParam);   
		
		dataRequest.setResponse("dsCmb", bbsCafeDataListService.BbscafedataCode("CRISIS_CAFE_CYBER_OUTRC_SE_CD"));
		
		dataRequest.setResponse("dsBoardList", detailList);                
		return new JSONDataView();
    }

	@RequestMapping("/saveBbscafedataProc.do")
    public View saveBbscafedataProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsCafeDataListService.saveBbscafedata(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();  
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	
}








