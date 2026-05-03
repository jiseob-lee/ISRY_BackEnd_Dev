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
import isry.couns.constt.etcntabrd.service.BbsVocListService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

@Controller
@Api(value = "bbsVocListController Controller")
@RequestMapping("/constt/etcntabrd")
public class BbsVocListController extends IsryBaseController {

	@Autowired
    private BbsVocListService bbsVocListService;
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	
	@RequestMapping("/selectBbsVocList.do")
    public View bbsEpilogoOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
		
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");

//		mapParam.put("NTABRD_ESNTAL_NO", dtlParam.getValue("NTABRD_ESNTAL_NO"));
		mapParam.put("WRTR_NM_ENCPT", dtlParam.getValue("WRTR_NM_ENCPT"));
		mapParam.put("BBSCTT_TTL_NM", dtlParam.getValue("BBSCTT_TTL_NM"));
		mapParam.put("BBSCTT_ESNTAL_NO", dtlParam.getValue("BBSCTT_ESNTAL_NO"));

		
        List<Map<String, Object>> dsBoardList = bbsVocListService.selectBbsVocList(mapParam);

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
	
	@RequestMapping("/selectBbsVocDetail.do")
    public View selectBbsVocDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsBoardList = bbsVocListService.selectBbsVocDetail(mapParam);   
		
		dataRequest.setResponse("dsBoardList", dsBoardList);                
		return new JSONDataView();
    }
	
	@RequestMapping("/saveBbsVocProc.do")
    public View saveBbsVocProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsVocListService.saveBbsVocProc(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
        
		return new JSONDataView();
    }
	
//	-------------------------------------------------답글
	@RequestMapping("/saveBbsRespodVoc.do")//답글(추가(insertBbsRespodVoc), 수정(updateBbsRespodVoc), 삭제(deleteBbsRespodVoc))
    public View saveBbsRespodVoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 

		Map<String, Object> returnParam = bbsVocListService.saveBbsRespodVoc(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
 
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
       
//		System.out.println("답글번호값:"+returnParam.get("RETE_ESNTAL_NO"));
//		System.out.println("게시글번호값:"+returnParam.get("BBSCTT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }

	
	@RequestMapping("/selectRespodDetail.do")//답글 상세 조회
    public View selectRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		Map<String, Object> autoSndngInfo = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("RETE_ESNTAL_NO", Param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsRplyList = bbsVocListService.selectRespodDetail(mapParam);   
		dataRequest.setResponse("dsRplyList", dsRplyList);
		
		List<Map<String, Object>> boardDetail = bbsVocListService.selectBbsVocDetail(mapParam);
		
		for (Map<String, Object> map : boardDetail) {
			try {
				
				autoSndngInfo.put("BBSCTT_ESNTAL_NO", map.get("BBSCTT_ESNTAL_NO"));
				autoSndngInfo.put("BBSCTT_TYPE_SE_CD", map.get("BBSCTT_TYPE_SE_CD"));
				autoSndngInfo.put("RECEIVER_NM", map.get("WRTR_NM_ENCPT"));
				autoSndngInfo.put("RECEIVER_EML", map.get("EML_ADDR_ENCPT"));
				autoSndngInfo.put("RECEIVER_TELNO", map.get("MBL_TELNO_ENCPT"));
				autoSndngInfo.put("CHRCTR_YN", map.get("CHRCTR_YN"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		dataRequest.setResponse("dsBoardList", boardDetail);
		dataRequest.setResponse("dmAutoSndngInfo", autoSndngInfo);
		
		return new JSONDataView();
    }
}








