/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.lvingmng.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.csemd.cmmn.service.CsemdService;
import isry.csemd.mdlrtrehabcrsemng.lvingmng.service.LvingMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : LvingMngController.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/csemd/mdlrtrehabcrsemng/lvingmng")
public class LvingMngController {

	@Resource(name = "lvingMngService")
	private LvingMngService lvingMngService;
	
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	//공통콤보데이터 관련 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectLvingCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 콤보조회
	 */
	@RequestMapping("/selectLvingCombo.do")
	public View selectLvingCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		
		dataRequest.setResponse("dsDormitForSearch", lvingMngService.selectDormitForSearch(request));
		dataRequest.setResponse("dsPic", lvingMngService.selectPic(request));
		dataRequest.setResponse("dsPeriodMedcinTakng", mgmtCmmnCodeService.selectCommonCodeUnit("PERIOD_MEDCIN_TAKNG_SE_CD", loginVO.getUntTaskwk()));
		dataRequest.setResponse("dsTakngEra", lvingMngService.selectTakingEra());
		dataRequest.setResponse("dsTpriChckResultCd", mgmtCmmnCodeService.selectCommonCodeUnit("TPRI_CHCK_RESULT_SE_CD", loginVO.getUntTaskwk()));
		dataRequest.setResponse("dsScdChckResultCd", mgmtCmmnCodeService.selectCommonCodeUnit("SCD_CHCK_RESULT_SE_CD", loginVO.getUntTaskwk()));
		dataRequest.setResponse("dsChckList", mgmtCmmnCodeService.selectCommonCodeUnit("DD_DD_CHECKL_DTL_SE_CD", loginVO.getUntTaskwk()));
		dataRequest.setResponse("dsAltmntGroupSclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk()));
		dataRequest.setResponse("dsEnfsn", lvingMngService.selectEnfsn(request));
		dataRequest.setResponse("dsInstCmb", csemdService.selectInstCmb(requestMap));
		dataRequest.setResponse("dsSrvcExcnBizCmb", csemdService.selectSrvcExcnBizCmb(requestMap));
		dataRequest.setResponse("dsBizYr", csemdService.selectBizYrCmb(requestMap));
		
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWorkDiaryList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 근무일지 목록조회
	 */
	@RequestMapping("/selectWorkDiaryList.do")
	public View selectWorkDiaryList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectWorkDiaryList(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWorkDiary
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 근무일지 상세조회
	 */
	@RequestMapping("/selectWorkDiary.do")
	public View selectWorkDiary(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectWorkDiary(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : saveWorkDiary
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 근무일지 등록/수정
	 */
	@RequestMapping("/saveWorkDiary.do")
	public View saveWorkDiary(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.saveWorkDiary(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectYngbgsObservRcord
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 청소년 관찰기록 조회
	 */
	@RequestMapping("/selectYngbgsObservRcord.do")
	public View selectYngbgsObservRcord(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectYngbgsObservRcord(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectYngbgsObservRcord
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 근무일지 승인처리
	 */
	@RequestMapping("/updateWorkDiaryAprv.do")
	public View updateWorkDiaryAprv(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.updateWorkDiaryAprv(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDayChckList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 일일점검표 목록조회
	 */
	@RequestMapping("/selectDayChckList.do")
	public View selectDayChckList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectDayChckList(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDayChck
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 일일점검표 상세조회
	 */
	@RequestMapping("/selectDayChck.do")
	public View selectDayChck(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectDayChck(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDayChckExist
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 1. 
	 * @Method설명 : 일일점검표 등록/수정시 존재 유무 체크
	 */
	@RequestMapping("/selectDayChckExist.do")
	public View selectDayChckExist(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectDayChckExist(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : saveDayChck
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 : 일일점검표 등록/수정/삭제
	 */
	@RequestMapping("/saveDayChck.do")
	public View saveDayChck(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.saveDayChck(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDormitList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 생활동관리 목록 조회
	 */
	@RequestMapping("/selectDormitList.do")
	public View selectDormitList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectDormitList(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDormitList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 생활동 현재 거주현황 목록 조회
	 */
	@RequestMapping("/selectDormitNowStrdcList.do")
	public View selectDormitNowStrdcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectDormitNowStrdcList(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDormitList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 생활동 저장/수정/삭제
	 */
	@RequestMapping("/saveDormit.do")
	public View saveDormit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.saveDormit(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectDormitAllList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 : 저장된 모든 생활동 리스트 조회
	 */
	@RequestMapping("/selectDormitAllList.do")
	public View selectDormitAllList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		lvingMngService.selectDormitAllList(request, dataRequest);
		
		return new JSONDataView();
	}
}
