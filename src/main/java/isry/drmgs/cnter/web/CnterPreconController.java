/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.drmgs.cnter.service.CnterPreconService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.uneartmng.policelinkaply.service.PicMngService;

/**
 * @파일명        : CnterPreconController.java
 * @프로그램 설명 : 센터별 현황
 * - 
 * - CnterPreconController
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 29. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 29. 
 * @수정내용      : 센터별 현황
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/cnter")
public class CnterPreconController extends IsryBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "cnterPreconService")
	private CnterPreconService cnterPreconService;
	
	@Resource(name = "picMngService")
	private PicMngService picMngService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@RequestMapping(value = "/subCtpvCnterPreconList.do")
	public View selectCtpvCnterPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도센터 센터현황 조회
		dataRequest.setResponse("dsCtpvCnterPrecon", cnterPreconService.selectCtpvCnterPreconList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subCtpvOperInfoList.do")
	public View selectCtpvOperInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도센터_운영정보 조회
		dataRequest.setResponse("dsCtpvCnterOperInfo", cnterPreconService.selectCtpvOperInfoList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subCtpvFcltyInfoList.do")
	public View selectCtpvFcltyInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도센터_시설정보 조회
		dataRequest.setResponse("dsCtpvCnterFcltyInfo", cnterPreconService.selectCtpvFcltyInfoList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subCtpvInstlCnsgnInfoList.do")
	public View selectCtpvInstlCnsgnInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도센터_설치 및 위탁정보 조회
		dataRequest.setResponse("dsCtpvCnterInstlCnsgnInfo", cnterPreconService.selectCtpvInstlCnsgnInfoList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subCtpvAddingBassInfoList.do")
	public View selectCtpvAddingBassInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도센터_추가 기본정보 조회
		dataRequest.setResponse("dsCtpvCnterAddingBassInfo", cnterPreconService.selectCtpvAddingBassInfoList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subCtpvYngbsDscsnTlphon1388List.do")
	public View selectCtpvYngbsDscsnTlphon1388List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도센터_청소년상담전화1388 조회
		dataRequest.setResponse("dsCtpvCnterYngbsDscsnTlphon1388", cnterPreconService.selectCtpvYngbsDscsnTlphon1388List(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectRegion.do")
	public View selectRegion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		LOGGER.debug("시도,시군구 조회 시작 ::::::::::::::: " );
		dataRequest.setResponse("dsRegion", picMngService.selectRegion());			// 시도
		dataRequest.setResponse("dsRegion2", picMngService.selectRegion2());		// 시군구
		
		//HttpSession session = request.getSession();
		//UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//dataRequest.setResponse("dsUntSysSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CNTER_PRECON_UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 센터유형구분코드	
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subSggCnterPreconList.do")
	public View selectSggCnterPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시군구 센터현황 조회
		dataRequest.setResponse("dsSggCnterPrecon", cnterPreconService.selectSggCnterPreconList(request,dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subSggOperInfoList.do")
	public View selectSggOperInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시군구_운영정보 조회
		dataRequest.setResponse("dsSggCnterOperInfo", cnterPreconService.selectSggOperInfoList(request,dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subSggFcltyInfoList.do")
	public View selectSggFcltyInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시군구_시설정보 조회
		dataRequest.setResponse("dsSggCnterFcltyInfo", cnterPreconService.selectSggFcltyInfoList(request,dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subSggInstlCnsgnInfoList.do")
	public View selectSggInstlCnsgnInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시군구_설치 및 위탁정보 조회
		dataRequest.setResponse("dsSggCnterInstlCnsgnInfo", cnterPreconService.selectSggInstlCnsgnInfoList(request,dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subSggAddingBassInfoList.do")
	public View selectSggAddingBassInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시군구_추가 기본정보 조회
		dataRequest.setResponse("dsSggCnterAddingBassInfo", cnterPreconService.selectSggAddingBassInfoList(request,dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/subSggYngbsDscsnTlphon1388List.do")
	public View selectSggYngbsDscsnTlphon1388List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시군구_청소년상담전화1388 조회
		dataRequest.setResponse("dsSggCnterYngbsDscsnTlphon", cnterPreconService.selectSggYngbsDscsnTlphon1388List(request,dataRequest));
		
		return new JSONDataView();
	}
	
	
	
	
}
