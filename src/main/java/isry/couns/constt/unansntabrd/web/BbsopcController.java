/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.unansntabrd.web;

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
import isry.couns.constt.unansntabrd.service.BbsopcListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "bbsopcController Controller")
@RequestMapping("/constt/unansntabrd")
///constt/unansntabrd/selectBbsopcList.do
public class BbsopcController extends IsryBaseController {

	@Autowired
    private BbsopcListService bbsopcListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	@Autowired		/constt/unansntabrd/selectBbsopcList.do
//	private NoticeBoardService noticeBoardService;
	
	@RequestMapping("/bbsOpcOnLoad.do")
    public View bbsOpcOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = userVo.getEnfsnRoleSeCd();
		//System.out.println("loginRoleCd::"+loginRoleCd);
		
		mapRoleCd.put("loginRoleCd", loginRoleCd);
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		
		//문제상태중분류코드
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		
		//문제상태소분류코드
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		
		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		//문제원인소분류코드
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
		
		
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbsopcList.do")
    public View selectBbsopcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
//		totalCount = bbsopcListService.getTotalCount(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		Map<String, String> mapDate = new HashMap<String, String>();
		
		ParameterGroup dmRoleCd = dataRequest.getParameterGroup("dmRoleCd");
		
		if(dmRoleCd.getValue("loginRoleCd").equals("3")) {
			//System.out.println("상담원~~~~");
			HttpSession session = request.getSession();
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
			String loginId = loginVO.getId();			
			
			mapParam.put("LOGIN_ID", loginId);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		mapParam.put("STARTDATE", searchParam.getValue("STARTDATE"));
		mapParam.put("ENDDATE", searchParam.getValue("ENDDATE"));
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));   
		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));
		mapParam.put("PROBM_STTS_MLSFC_SE_CD", searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));
		mapParam.put("PROBM_STTS_SCLAS_SE_CD", searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));
		mapParam.put("PROBM_CAS_SCLAS_SE_CD", searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));
		mapParam.put("ETC_CN", searchParam.getValue("ETC_CN"));

		String bbsSort = searchParam.getValue("BBS_SORT");
		if(bbsSort.equals("Y")) {

			List<Map<String, Object>> dsList = bbsopcListService.selectBbsopcListY(mapParam);
			dataRequest.setResponse("dsBoardList", dsList);
			
			if(dsList.size() == 0) {
				resPage.put("totalCount", 0);
			} else {
				resPage.put("totalCount", dsList.get(0).get("TOTAL_COUNT"));
			}
			
			resPage.put("pageNo", pageIdx);
			resPage.put("pageRowCount", rowSize);
			
			dataRequest.setResponse("dmPage", resPage);
			
		}else if(bbsSort.equals("N")) {

			List<Map<String, Object>> dsList = bbsopcListService.selectBbsopcListN(mapParam);
			dataRequest.setResponse("dsBoardList", dsList);
			
			if(dsList.size() == 0) {
				resPage.put("totalCount", 0);
			} else {
				resPage.put("totalCount", dsList.get(0).get("TOTAL_COUNT"));
			}
			
			resPage.put("pageNo", pageIdx);
			resPage.put("pageRowCount", rowSize);
			
			dataRequest.setResponse("dmPage", resPage);
			
		}else {
			List<Map<String, Object>> dsList = bbsopcListService.selectBbsopcList(mapParam);
			dataRequest.setResponse("dsBoardList", dsList);
			
			if(dsList.size() == 0) {
				resPage.put("totalCount", 0);
			} else {
				resPage.put("totalCount", dsList.get(0).get("TOTAL_COUNT"));
			}
			
			resPage.put("pageNo", pageIdx);
			resPage.put("pageRowCount", rowSize);
			
			dataRequest.setResponse("dmPage", resPage);
		}
			
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);					
		mapDate.put("loginNm", loginVO.getUserName());
		mapDate.put("loginBir", loginVO.getBirthdate());
		mapDate.put("loginGen", loginVO.getGender());
		mapDate.put("loginIp", loginVO.getIp());
		mapDate.put("loginEmail", loginVO.getEmail());
		mapDate.put("loginAge", loginVO.getAge());
		mapDate.put("loginEnfsn", loginVO.getEnfsnRoleSeCd());
		
		mapDate.put("strToday", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));
		dataRequest.setResponse("dmTime", mapDate);

		return new JSONDataView();
    }
	
	@RequestMapping("/counselor.do")
    public View counselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup workParam = dataRequest.getParameterGroup("dmWorkYmd");
		
		mapParam.put("WORKYMD", workParam.getValue("REG_DT"));
		
		List<Map<String, Object>> dsCounselor = bbsopcListService.counselorList(mapParam);
		
		dataRequest.setResponse("dsCounselor", dsCounselor);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/insertCrisis.do")
	public View insertCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		bbsopcListService.insertCrisis(request, dataRequest);		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbsopcDetail.do")  
  public View selectBbsopcDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
          throws Exception {
   
		Map<String, Object> mapParam = new HashMap<String, Object>();
      
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		

		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("INDEX_SN", Param.getValue("INDEX_SN"));
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsBoardList = bbsopcListService.selectBbsopcDetail(mapParam);    
		List<Map<String, Object>> counselorBoardList = bbsopcListService.counselorBoardList(mapParam);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);  
		dataRequest.setResponse("dsCounselorList", counselorBoardList);
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> login = new HashMap<String, Object>();
		
		login.put("TRPR_AGE", userVo.getAge());
		login.put("WRTR_NM_ENCPT", userVo.getUserName());
		login.put("loginRoleCd", userVo.getEnfsnRoleSeCd());
		login.put("FRST_REG_DT", mgmtCmmnCodeService.getSysDate("YYYYMMDDHHMMSS"));
		
		//위기유형구분코드
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		dataRequest.setResponse("dmLogin", login);
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		
		//문제상태중분류코드
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		
		//문제상태소분류코드
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		
		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		//문제원인소분류코드
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
		
		
		return new JSONDataView();
  }
	
	@RequestMapping("/saveBbsopc.do")
  public View saveBbsopc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
          throws Exception {
               
		Map<String, Object> returnParam = bbsopcListService.saveBbsopc(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
  
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));

      
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
  }
	
	
//	----------------------------------------------------답글
	@RequestMapping("/saveRespod.do")
    public View saveRespod(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

		Map<String, Object> returnParam = bbsopcListService.saveRespod(request, dataRequest);
//		Map<String, Object> returnParam = bbsopcListService.saveRespod(request, dataRequest);
		
//		Map<String, Object> message = new HashMap<String, Object>();
//
//		message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
//		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
//        
//		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	@RequestMapping("/selectRespodDetail.do")
    public View selectRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		
		mapParam.put("RETE_ESNTAL_NO", Param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		List<Map<String, Object>> dsRplyList = bbsopcListService.selectRespodDetail(mapParam);   
		dataRequest.setResponse("dsRplyList", dsRplyList);                
		
		List<Map<String, Object>> dsBoardList = bbsopcListService.selectBbsopcDetail(mapParam);   
		dataRequest.setResponse("dsBoardList", dsBoardList);
		List<Map<String, Object>> counselorBoardList = bbsopcListService.counselorBoardList(mapParam);
		
		dataRequest.setResponse("dsCounselorList", counselorBoardList);
		return new JSONDataView();
    }
	
	@RequestMapping("/insertCounselor.do")
    public View insertCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsopcListService.saveCounselor(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		   
		message.put("INDEX_SN", returnParam.get("INDEX_SN"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }

}








