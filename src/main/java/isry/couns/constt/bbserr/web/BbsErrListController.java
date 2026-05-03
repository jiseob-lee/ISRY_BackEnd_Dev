/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbserr.web;

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
import isry.couns.constt.bbserr.service.BbsErrListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "bbsErrListController Controller")
@RequestMapping("/constt/bbserr")
public class BbsErrListController extends IsryBaseController {

	@Autowired
    private BbsErrListService bbsErrListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/dscmb.do")
    public View dscmb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		dataRequest.setResponse("dsCmbPrgrs", bbsErrListService.BbsErrListCmbPrgrs("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD"));
		dataRequest.setResponse("dsCmbErr", bbsErrListService.BbsErrListCmbPrgrs("DTPRSN_ERR_DCLR_ERR_SE_CD"));
		dataRequest.setResponse("dsCmbSxdc", bbsErrListService.BbsErrListCmbSxdc("SXDC_SE_CD"));
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> login = new HashMap<String, Object>();

		login.put("loginNm", loginVO.getUserName());
		login.put("loginBir", loginVO.getBirthdate());
		login.put("loginGen", loginVO.getGender());
		login.put("loginIp", loginVO.getIp());
		login.put("loginEmail", loginVO.getEmail());
		login.put("loginEnfsn", loginVO.getEnfsnRoleSeCd());
		login.put("strToday", mgmtCmmnCodeService.getSysDate("YYYY-MM-DD"));
		
		dataRequest.setResponse("dmLogin", login);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbserrList.do")
    public View selectBbserrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		//totalCount = bbsErrListService.getTotalCount(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();


//		dataRequest.setResponse("dsCmb", bbsErrListService.BbscafedataCode("CRISIS_CAFE_CYBER_OUTRC_SE_CD"));
        
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");//조회
		
		mapParam.put("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD", dtlParam.getValue("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD"));
		mapParam.put("DTPRSN_ERR_DCLR_ERR_SE_CD", dtlParam.getValue("DTPRSN_ERR_DCLR_ERR_SE_CD"));
		mapParam.put("BOX", dtlParam.getValue("BOX"));		
		
		if(dtlParam.getValue("BOX").equals("WRTR_NM_ENCPT")) {
			//검색어 조건이 작성자명일때 작성자명 암호화
			mapParam.put("KEYWORD", dtlParam.getValue("KEYWORD"));			
		}else {
			mapParam.put("KEYWORD", dtlParam.getValue("KEYWORD"));			
		}
		
		ParameterGroup searchTime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchTime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchTime.getValue("endDate"));							//조회끝날짜		
		
		List<Map<String, Object>> dsBoardList = bbsErrListService.selectBbserrList(mapParam);

		if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}		
		
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsList", dsBoardList);
        
		return new JSONDataView();
    }

	@RequestMapping("/selectBbserrDetail.do")
    public View selectBbserrDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		
		// 조회수 추가
		bbsErrListService.bbserrDtlCnt(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> detailList = bbsErrListService.selectBbserrDetail(mapParam);
		
		dataRequest.setResponse("dsCmbPrgrs", bbsErrListService.BbsErrListCmbPrgrs("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD"));
		dataRequest.setResponse("dsCmbErr", bbsErrListService.BbsErrListCmbPrgrs("DTPRSN_ERR_DCLR_ERR_SE_CD"));
		dataRequest.setResponse("dsCmbSxdc", bbsErrListService.BbsErrListCmbSxdc("SXDC_SE_CD"));
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> login = new HashMap<String, Object>();

		login.put("loginNm", loginVO.getUserName());
		login.put("loginBir", loginVO.getBirthdate());
		login.put("loginGen", loginVO.getGender());
		login.put("loginIp", loginVO.getIp());
		login.put("loginEmail", loginVO.getEmail());
		login.put("loginEnfsn", loginVO.getEnfsnRoleSeCd());
		login.put("strToday", mgmtCmmnCodeService.getSysDate("YYYY-MM-DD"));
		
		dataRequest.setResponse("dmLogin", login);
		
		dataRequest.setResponse("dsList", detailList);                
		return new JSONDataView();
    }

	@RequestMapping("/saveBbserr.do")
    public View saveBbserr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
//		Map<String, Object> returnParam = noticeBoardService.saveNoticeBoardList(request, dataRequest);
		Map<String, Object> returnParam = bbsErrListService.saveBbserr(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		//bbsRplyListService.updateBbsRplyProc(request, dataRequest);   
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
//	-------------------------------------------------------답글
	@RequestMapping("/selectRespodDetail.do")//답글 상세 조회
    public View selectRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("RETE_ESNTAL_NO", Param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsRplyList = bbsErrListService.selectRespodDetail(mapParam);   
        dataRequest.setResponse("dsRplyList", dsRplyList);  
        
        // 조회수 추가
        bbsErrListService.bbserrDtlCnt(mapParam);
        // 조회수 추가
        bbsErrListService.bbserrResCnt(mapParam);
        
        // 게시판 기본 데이터 호출
 		List<Map<String, Object>> dsBoardList = bbsErrListService.selectBbserrDetail(mapParam);   
		dataRequest.setResponse("dsList", dsBoardList);
		
		dataRequest.setResponse("dsCmbPrgrs", bbsErrListService.BbsErrListCmbPrgrs("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD"));
		dataRequest.setResponse("dsCmbErr", bbsErrListService.BbsErrListCmbPrgrs("DTPRSN_ERR_DCLR_ERR_SE_CD"));
		
		return new JSONDataView();
    }
	
	@RequestMapping("/saveBbserrRply.do")//답글(추가(insertRespod), 수정(updateRespod), 삭제(deleteRespod))
    public View saveBbserrRply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
//		Map<String, Object> returnParam = noticeBoardService.saveNoticeBoardList(request, dataRequest);
		Map<String, Object> returnParam = bbsErrListService.saveBbserrRply(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		//bbsRplyListService.updateBbsRplyProc(request, dataRequest);   
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
}








