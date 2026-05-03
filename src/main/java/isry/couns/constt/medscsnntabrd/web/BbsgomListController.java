/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.web;

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
import isry.couns.constt.medscsnntabrd.service.BbsgomListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;

/**
 * @파일명        : BbsonmController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@Api(value = "bbsgomListController Controller")
//@RequestMapping("/constt/medscsnntabrd")
@RequestMapping("/constt/bbsgomList")
public class BbsgomListController extends IsryBaseController{
	
	@Resource
	private BbsgomListService bbsgomListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/bbsGomOnLoad.do")
    public View bbsGomOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);


		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);

		
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbsgomList.do")
    public View selectBbsgomList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
				
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
//		int totalCount = 0;
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));				//작성자명
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));				//게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));			//게시글번호
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));		//위기유형구분코드
		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));		//문제상태대분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));		//문제원인대분류
		
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));		// 문제상태미등록
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜
		
		List<Map<String , Object>> dsBoardList = bbsgomListService.selectBbsgomList(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		//전체 게시글 수
		if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}

		//resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
		
    }
	
	@RequestMapping("/counselorBbsGom.do")
    public View counselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup workParam = dataRequest.getParameterGroup("dmWorkYmd");
		
		mapParam.put("WORKYMD", workParam.getValue("REG_DT"));
		
		List<Map<String, Object>> dsCounselor = bbsgomListService.counselorList(mapParam);
		
		dataRequest.setResponse("dsCounselor", dsCounselor);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbsgomDetail.do")
    public View selectInqBbsRplyListDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		
//		System.out.println("Param ::::::::::::::: " + Param.toString());
		
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("INDEX_SN", Param.getValue("INDEX_SN"));
		mapParam.put("CREATE_YN", Param.getValue("strCreateYn"));
		
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsList = bbsgomListService.selectBbsgomDetail(mapParam);
		
		List<Map<String, Object>> counselorBoardList = bbsgomListService.counselorBoardList(mapParam);

		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd::"+loginRoleCd);
		
		mapRoleCd.put("loginRoleCd", loginRoleCd);
		
		dataRequest.setResponse("dsList", dsList);               
		dataRequest.setResponse("dsCounselorList", counselorBoardList);
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/saveBbsgom.do")//게시글(추가(insertBbsgom), 수정(updateBbsgom), 삭제(deleteBbsgom))
    public View saveBbsgom(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
//		Map<String, Object> returnParam = noticeBoardService.saveNoticeBoardList(request, dataRequest);
		Map<String, Object> returnParam = bbsgomListService.saveBbsgom(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	
//	-----------------------------------------------------------댓글
	@RequestMapping("/selectRplyDetail.do")
    public View selectRplyDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup rplyParam = dataRequest.getParameterGroup("dmRplyParam");
		mapParam.put("BBSCTT_ESNTAL_NO", rplyParam.getValue("BBSCTT_ESNTAL_NO"));
		
        List<Map<String, Object>> dsRplyList = bbsgomListService.selectRplyDetail(mapParam);
        
        dataRequest.setResponse("dsRplyList", dsRplyList);      
        
		return new JSONDataView();
    }
	
	@RequestMapping("/saveRply.do")//댓글(추가(insertRply), 수정(updateRply), 삭제(deleteRply))
    public View saveRply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsgomListService.saveRply(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("CMNT_ESNTAL_NO", returnParam.get("CMNT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	@RequestMapping("/insertBbsgomCounselor.do")
    public View insertCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsgomListService.saveCounselor(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		   
		message.put("INDEX_SN", returnParam.get("INDEX_SN"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
//	/constt/bbsgomList/
	@RequestMapping("/insertGomMemo.do")
	public View insertGomMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmMemo = dataRequest.getParameterGroup("dmMemo");
//		System.out.println("dsdsdsdsmemoemo"+dmMemo.toString());
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmMemo.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CONSTT_ID", dmMemo.getValue("CONSTT_ID"));
		mapParam.put("MEMO_NM", dmMemo.getValue("MEMO_NM"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		bbsgomListService.insertMemo(mapParam);
		return new JSONDataView();
	}
	
}
