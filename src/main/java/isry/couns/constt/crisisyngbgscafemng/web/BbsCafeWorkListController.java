/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.crisisyngbgscafemng.web;

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
import isry.couns.constt.crisisyngbgscafemng.service.BbsCafeWorkListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "bbsCafeWorkListController Controller")
@RequestMapping("/constt/crisisyngbgscafemng")
public class BbsCafeWorkListController extends IsryBaseController {

	@Autowired
    private BbsCafeWorkListService bbsCafeWorkListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/selectBbsCafeWorkInit.do")
    public View selectBbsCafeWorkInit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
                   
		List<Map<String, Object>> dsList = bbsCafeWorkListService.selectBbsCafeWorkInit(mapParam);
		
		dataRequest.setResponse("dsCombo1", dsList);

		return new JSONDataView();
    }

	@RequestMapping("/selectBbsCafeWorkList.do")
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
        
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");//조회
		
		mapParam.put("WRTR_NM_ENCPT", dtlParam.getValue("WRTR_NM_ENCPT"));
		mapParam.put("FRST_RGTR_ID", dtlParam.getValue("FRST_RGTR_ID"));
		mapParam.put("BBSCTT_TTL_NM", dtlParam.getValue("BBSCTT_TTL_NM"));			
	
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		List<Map<String, Object>> dsBoardList = bbsCafeWorkListService.selectBbsCafeWorkList(mapParam);
		
		if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}
		
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		Map<String, String> mapLogin = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);	
		mapLogin.put("loginId", loginVO.getId());
		mapLogin.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		
		dataRequest.setResponse("dmLogin", mapLogin);

		dataRequest.setResponse("dmPage", resPage);
		dataRequest.setResponse("dsBoardList", dsBoardList);
        
		return new JSONDataView();
    }
	
	@RequestMapping("/selectOnLoad.do")
	public View selectOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd 000000000 ::::::::::::::"+loginRoleCd);
		
		Map<String, Object> oUserID = new HashMap<String, Object>();
		oUserID.put("oUserRoleCd", loginRoleCd);
		oUserID.put("oUserNM", loginVO.getUserName());
		oUserID.put("oUserId", loginVO.getId());
		dataRequest.setResponse("dmUser", oUserID);
		
		return new JSONDataView();
	}

	@RequestMapping("/selectBbsCafeWorkDetail.do")
    public View selectBbsCafeWorkDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> detailList = bbsCafeWorkListService.selectBbsCafeWorkDetail(mapParam);   
		
		dataRequest.setResponse("dsBoardList", detailList);                
		return new JSONDataView();
    }

	@RequestMapping("/saveBbsCafeWorkProc.do")
    public View saveBbsCafeWorkProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsCafeWorkListService.saveBbsCafeWork(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();  
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
//        System.out.println("dasdadasd::"+returnParam.get("BBSCTT_ESNTAL_NO"));
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	
}








