/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.crtfmng.crtfissu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

//import isry.itgcm.casemng.caseunity.service.CaseRegService;
import isry.itgcms.crtfmng.crtfissu.service.CrtfiSsuService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명 : CrtfiSsuController.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2022. 8. 12.
 * @수정자 : Song.Young.Il
 * @수정일 : 2022. 8. 12.
 * @수정내용 : - -
 */

@Controller
@RequestMapping(value = "/isry/itgcms/crtfmng/crtfissu")
public class CrtfiSsuController {

	private final Logger log = LoggerFactory.getLogger(CrtfiSsuController.class);
	
	// 업무공통 서비스
	//@Resource(name = "caseRegService")
	//private CaseRegService caseRegService;

	// 공통코드 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	// 서비스
	@Resource(name = "crtfiSsuService")
	private CrtfiSsuService crtfiSsuService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * 
	 * @Method명 : selectTrprCombo
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 16.
	 * @Method설명 : 검색콤보
	 */
	@RequestMapping(value = "/selectCrtfiCombo.do")
	public View selectCrtfiCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsCrtfiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CRTF_SE_CD", userVo.getUntTaskwk()));

		return new JSONDataView();

	}

	/**
	 * 
	 * @Method명 : selectCrtfiNo
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 17.
	 * @Method설명 : 발급번호채번
	 */
	@RequestMapping(value = "/selectCrtfiNo.do")
	public View selectCrtfiNo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> info = crtfiSsuService.selectCrtfiNo(request, dataRequest);
		dataRequest.setResponse("dmRenu", info);

		return new JSONDataView();

	}

	/**
	 * 
	 * @Method명 : selectCrtfiToReg
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 17.
	 * @Method설명 : 증명서 등록 시 정보 조회
	 */
	@RequestMapping(value = "/selectCrtfiToReg.do")
	public View selectCrtfiToReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> info = crtfiSsuService.selectCrtfiToReg(request, dataRequest);
		dataRequest.setResponse("dsList", info);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : insertCrtfi
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 18.
	 * @Method설명 : 증명서 저장
	 */
	@RequestMapping(value = "/insertCrtfi.do")
	public View insertCrtfi(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		crtfiSsuService.insertCrtfi(request, dataRequest);

		return new JSONDataView();
	}
	
	@RequestMapping(value = "/updateCrtfi.do")
	public View updateCrtfi(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		crtfiSsuService.updateCrtfi(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : crtflssuList
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 16.
	 * @Method설명 : 증명서발급목록 조회
	 */

	@RequestMapping(value = "/selectCrtflssuList.do")
	public View selectCrtfssuList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> listBoard = crtfiSsuService.selectCrtfssuList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectListDtlSelected
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 증명서 발급목록에서의 상세조회
	 */

	@RequestMapping(value = "/selectListDtlsrvcCrtf.do")
	public View selectListDtlSelected(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = crtfiSsuService.selectListDtlSelected(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dsDetailInfo", listBoard);
		
		if (dataRequest.getParameterGroup("dmRenu").getValue("CRTF_SE_CD").equals("08")) {
			List<Map<String, Object>> atendSittn = crtfiSsuService.selectListAtendCrft2(dataRequest);
			dataRequest.setResponse("dsAtendSittn", atendSittn);
		}

		return new JSONDataView();
	}

	@RequestMapping(value = "/insertCrtfOtpt.do")
	@ResponseBody
	public View insertCrtfOtpt(HttpServletResponse response, HttpServletRequest request
			,@RequestParam Map<String, Object> param )
		throws Exception {
		
		log.debug("증명서 파라미터 정보 : " + param);
		crtfiSsuService.insertCrtfOtpt(request, param);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectCsemdPicList.do")
	public View selectCsemdPicList(HttpServletRequest request, DataRequest dataRequest)
		throws Exception {
		Map<String, String> returnMap = crtfiSsuService.selectCsemdPicList(request,dataRequest);
		dataRequest.setResponse("dmCsemdPic", returnMap);
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectListDtlSelected
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 직인 관련 내용 조회
	 */
	@RequestMapping(value = "/selectOffcs.do")
	public View selectOffcs(HttpServletRequest request, DataRequest dataRequest)
		throws Exception {
		crtfiSsuService.selectOffcs(request,dataRequest);
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectListDtlSelected
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 직인 관련 내용 조회
	 */
	@RequestMapping(value = "/selectWorker.do")
	public View selectWorker(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsAddress", crtfiSsuService.selectWorker(request, dataRequest));

		return new JSONDataView();
	}
}
