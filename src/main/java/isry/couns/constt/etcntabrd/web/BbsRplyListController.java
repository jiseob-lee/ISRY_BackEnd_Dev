/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import isry.couns.constt.etcntabrd.service.BbsRplyListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "bbsRplyListController Controller")
@RequestMapping("/constt/etcntabrd")
public class BbsRplyListController extends IsryBaseController {

	@Autowired
    private BbsRplyListService bbsRplyListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/selectInqBbsRplyList.do")
    public View subList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
//		totalCount = bbsRplyListService.getTotalCount(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
        Map<String, Object> resPage = new HashMap<String, Object>();
        
        // 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
 		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
     		
 		// 페이지 인덱싱에 필요한 정보를 정제합니다.
 		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
 		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
 		int startIndex = (pageIdx - 1) * rowSize;
		
 		mapParam.put("START_IDX", startIndex);
 		mapParam.put("ROW_COUNT", rowSize);
 		
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");//조회
		
//		mapParam.put("NTABRD_ESNTAL_NO", dtlParam.getValue("NTABRD_ESNTAL_NO"));
		mapParam.put("WRTR_NM_ENCPT", dtlParam.getValue("WRTR_NM_ENCPT"));
		mapParam.put("BBSCTT_TTL_NM", dtlParam.getValue("BBSCTT_TTL_NM"));
		mapParam.put("BBSCTT_ESNTAL_NO", dtlParam.getValue("BBSCTT_ESNTAL_NO"));
		
        List<Map<String, Object>> dsBoardList = bbsRplyListService.selectInqBbsRplyList(mapParam);    
        
        if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}
        
        resPage.put("pageNo", pageIdx);
        resPage.put("pageRowCount", rowSize);
        
        dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsBoardList", dsBoardList);
        
        Map<String, String> mapDate = new HashMap<String, String>();

        HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		mapDate.put("loginNm", loginVO.getUserName());
		mapDate.put("loginBir", loginVO.getBirthdate());
		mapDate.put("loginGen", loginVO.getGender());
		mapDate.put("loginIp", loginVO.getIp());
		mapDate.put("loginEmail", loginVO.getEmail());
		mapDate.put("loginEnfsn", loginVO.getEnfsnRoleSeCd());
		mapDate.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		
		dataRequest.setResponse("dmTime", mapDate);
        
        
		return new JSONDataView();
    }
	
	@RequestMapping("/selectInqBbsRplyListDetail.do")
    public View selectInqBbsRplyListDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup param = dataRequest.getParameterGroup("dmDtlParam");//상세
		
		mapParam.put("BBSCTT_ESNTAL_NO", param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CREATE_YN", param.getValue("strCreateYn"));
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> boardDetail = bbsRplyListService.selectInqBbsRplyListDetail(mapParam);    
		
		dataRequest.setResponse("dsBoardList", boardDetail);                
		return new JSONDataView();
    }

	@RequestMapping("/saveBbsRplyProc.do")
    public View saveClienaMnlaProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsRplyListService.saveBbsRplyProc(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();   
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	
//	-----------------------------------------------------------댓글
	@RequestMapping("/subRplyList.do")
    public View subRplyOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup rplyParam = dataRequest.getParameterGroup("dmRplyParam");
		mapParam.put("BBSCTT_ESNTAL_NO", rplyParam.getValue("BBSCTT_ESNTAL_NO"));
		
        List<Map<String, Object>> dsRplyList = bbsRplyListService.subRplyList(mapParam);
        
        dataRequest.setResponse("dsRplyList", dsRplyList);      
        
		return new JSONDataView();
    }
	
	@RequestMapping("/saveBbsDetailRplyProc.do")
    public View saveBbsDetailRplyProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsRplyListService.saveBbsDetailRply(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("CMNT_ESNTAL_NO", returnParam.get("CMNT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	
}








