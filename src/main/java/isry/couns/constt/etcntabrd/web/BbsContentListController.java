/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.web;

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
import isry.couns.constt.etcntabrd.service.BbsContentListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

@Controller
@Api(value = "bbsContentListController Controller")
@RequestMapping("/constt/etcntabrd")
///constt/etcntabrd/bbsContentListOnLoad.do
public class BbsContentListController extends IsryBaseController {

	@Autowired
    private BbsContentListService bbsContentListService;
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/selectBbsContentList.do")
    public View selectBbsContentList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
                   
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
//		String loginNm = loginVO.getUserName();

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
		
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");//조회
		
		mapParam.put("WRTR_NM_ENCPT", dtlParam.getValue("WRTR_NM_ENCPT"));
		mapParam.put("INST_NM", dtlParam.getValue("INST_NM"));
		mapParam.put("BBSCTT_ESNTAL_NO", dtlParam.getValue("BBSCTT_ESNTAL_NO"));
		
        List<Map<String, Object>> dsBoardList = bbsContentListService.selectInqBbsContentList(mapParam);
        
        if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
//			System.out.println("asdasdasd00::"+resPage.get("totalCount"));
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
//			System.out.println("!!asadasdasd::"+dsBoardList.get(0).get("TOTAL_COUNT"));
		}
        
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsBoardList", dsBoardList);
        
		return new JSONDataView();
    }

	@RequestMapping("/selectBbsContentDetail.do")
    public View selectBbsContentDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", Param.getValue("BBSCTT_TYPE_SE_CD"));

		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
//		dataRequest.setResponse("dsCheckBox", bbsContentListService.bbsContentList("CONTEN_PRCUSE_DMND_SE_CD"));
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsBoardList = bbsContentListService.selectBbsContentDetail(mapParam);
		
		for (Map<String, Object> map : dsBoardList) {
	        try {
	        	String etcYN = map.get("SRVC_DTL_CN").toString();
	        	
	        	if (etcYN != null && !"".equals(etcYN)) {
	        		map.put("ETC_YN", "Y");
	        	} else {
	        		map.put("ETC_YN", "N");
	        	}
	        	
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
		
		dataRequest.setResponse("dsBoardList", dsBoardList);           
		
		return new JSONDataView();
    }

//	/constt/etcntabrd/updateBbsContent.do
	@RequestMapping("/updateBbsContent.do")
    public View updateBbsContent(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

		Map<String, Object> returnParam = bbsContentListService.updateBbsContent(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
    }

	
	
}








