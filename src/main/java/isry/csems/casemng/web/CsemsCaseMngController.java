/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.casemng.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.csems.casemng.service.CsemsCaseMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명     	: CsemdCaseMngController.java
 * @프로그램 설명 	: 사례관리 내 국립청소년인터넷드림마을 고유 영역
 * - 
 * - 
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 10. 4.
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 10. 4.
 * @수정내용    	: 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/csems/casemng")
public class CsemsCaseMngController extends IsryBaseController {
	
//	@Resource(name = "mgmtCmmnCodeService")
//	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "csemsCaseMngService")
	private CsemsCaseMngService csemsCaseMngService;
	
	// 상하위분류가 있는 공통 코드 조회 서비스
//	@Resource(name = "comCodeService")
//	private ComCodeService comCodeService;
	
	// 단일 공통 코드 조회 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	
	// 설문지템플릿관리번호 사용여부
//	@Resource(name = "survshtCmmnsInqService")
//	private SurvshtCmmnsInqService survshtCmmnsInqService;
	
	// 설문지 관리번호 생성 Service Class
//	@Resource(name = "survshtMmnService")
//	private SurvshtMmnService survshtMmnService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 사례관리_등록 상세정보 저장
	 * @Method명   : processCsemdCaseMngRegDetailSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/processCsemsCaseMngRegDetailSave.do")
	@ResponseBody
	public View processCsemsCaseMngRegDetailSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
		throws Exception {

		Map<String, String> dmParam = csemsCaseMngService.saveCaseMngRegDetail(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method : onLoadCsemsAfterForm
	 * @Method설명 : 신청자 등록/상세 OnLoad
	 * @param : request
	 * @param : response
	 * @return : dataRequest
	 * @exception : Exception
	 * @작성자 :
	 * @작성일 : ****************************** 공통코드 조회 조건 (dsCodeParam) 1.CMMNS_CD_ID
	 *      : 공통코드아이디 (필수) - ex) SRVC_RESRCE_LCLAS_SE_CD 2.DS_SET_NM : RETURN 데이터셋
	 *      (필수) - ex) dsSrvcResrceLclasSeCd 3.CMMNS_CD_VALUE : 공통코드값
	 *      4.CMMNS_CD_VALUE_NM : 공통코드값명 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 *      6.ADDTNG_MNG_VALUE2 : 추가관리값2 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 *      8.ADDTNG_MNG_VALUE4 : 추가관리값4 9.ADDTNG_MNG_VALUE5 : 추가관리값5 10.USE_YN :
	 *      사용여부
	 */
	@RequestMapping(value = "/onLoadCsemsAfterForm.do")
	public View onLoadCsemsAfterForm(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		String codeId = ""; // 공통코드 아이디
		String dataSetNm = ""; // 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		log.debug("selectAplCntInfoOnLoad.paraGroup=[" + paramGroup + "]");

		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			for (Map<String, String> rowMap : paramList) {

				dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); // 응답 데이터셋
				codeId = String.valueOf(rowMap.get("CMMNS_CD_ID")); // 요청 공통코드 아이디
				log.debug("selectAplCntInfoOnLoad.dataSetNm=[" + dataSetNm + "]");
				log.debug("selectAplCntInfoOnLoad.codeId=[" + codeId + "]");
				// 시스템 공통코드 조회 서비스 요청
				List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, userVo.getUntTaskwk());
				// 응답객체 셋팅
				dataRequest.setResponse(dataSetNm, list);
			}
		}

		

		return new JSONDataView();
	}
	
	
	
	/**
	 * @Method명 : chkTrprCreateQustnbMngNoYn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 청소년설문지관리번호 생성 여부 체크
	 */
	@RequestMapping(value = "/chkTrprCreateQustnbMngNoYn.do")
	public View chkTrprCreateQustnbMngNoYn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		List<Map<String, String>> chkCreateQustnbMngNoYn = csemsCaseMngService.chkCreateQustnbMngNoYn(request, dataRequest);
		dataRequest.setResponse("dsQustnbMngNoCreateTrprList", chkCreateQustnbMngNoYn);

		return new JSONDataView();
	}
	
	
	
	
	/**
	 * @Method명 : selectCreateQustnbMngNoPrtcrList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 보호자설문지관리번호 생성 여부 체크
	 */
	@RequestMapping(value = "/chkPrtcrCreateQustnbMngNoYn.do")
	public View selectCreateQustnbMngNoPrtcrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		List<Map<String, String>> chkCreateQustnbMngNoYn = csemsCaseMngService.chkCreateQustnbMngNoYn(request, dataRequest);
		dataRequest.setResponse("dsQustnbMngNoCreatePrtcrList", chkCreateQustnbMngNoYn);

		return new JSONDataView();
	}
	
	
	/**
	 * @Method명 : createQustnb
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 설문지생성
	 */
	@RequestMapping(value = "/createQustnb.do")
	public View createQustnb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		
		Map<String, Object> message = csemsCaseMngService.chkQustnbTmptUseYn(request, dataRequest);
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
		
	}
	
	
	
	/**
	 * @Method명 : trprSrvyWrtStts
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 청소년 설문작성 상태
	 */
	@RequestMapping(value = "/trprSrvyWrtStts.do")
	public View trprSrvyWrtStts(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, String> map = csemsCaseMngService.srvyWrtStts(request, dataRequest);
		dataRequest.setResponse("dmTrprSrvyRspns", map);
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * @Method명 : prtcrSrvyWrtStts
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 청소년 설문작성 상태
	 */
	@RequestMapping(value = "/prtcrSrvyWrtStts.do")
	public View prtcrSrvyWrtStts(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, String> map = csemsCaseMngService.srvyWrtStts(request, dataRequest);
		dataRequest.setResponse("dmPrtcrSrvyRspns", map);
		

		return new JSONDataView();
	}

}
