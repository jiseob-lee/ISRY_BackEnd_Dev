/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.uneart.notregcasedscsn.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.subms.uneart.notregcasedscsn.service.NotRegCaseDscsnService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : TlphonDscsnController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller("notRegCaseDscsnController")
@RequestMapping("/isry/subms/uneart/notregcasedscsn")
public class NotRegCaseDscsnController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "notRegCaseDscsnService")
	private NotRegCaseDscsnService tlphonDscsnService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectKeyValue.do")
	public View selectKeyValue(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmKey", tlphonDscsnService.selectKeyValue(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadReqList.do")
	public View onLoadReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsSxdcSeCd", mgmtCmmnCodeService.selectCommonCode("SXDC_SE_CD"));
		dataRequest.setResponse("dsSnsSeCd", mgmtCmmnCodeService.selectCommonCode("SNS_SE_CD"));
		dataRequest.setResponse("dsSrvcResrceMlsfcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_RESRCE_MLSFC_SE_CD", loginVo.getUntTaskwk()));
		dataRequest.setResponse("dsSrvcPvsnMthdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_PVSN_MTHD_SE_CD", loginVo.getUntTaskwk()));
		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectReqList.do")
	public View selectReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = tlphonDscsnService.selectReqList(request, dataRequest);
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", tlphonDscsnService.selectReqById(dataRequest));
		dataRequest.setResponse("dsCaseYngbgs", tlphonDscsnService.selectCo13DtlById(dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		tlphonDscsnService.saveData(request, dataRequest);

	    return new JSONDataView();
	}

	@RequestMapping("/deleteData.do")
	public View deleteData(DataRequest dataRequest) throws Exception {
		
		tlphonDscsnService.deleteData(dataRequest);
		
		return new JSONDataView();
	}

	
	@RequestMapping("/saveUneart.do")
	public View saveUneart(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		tlphonDscsnService.saveUneart(request, dataRequest);

	    return new JSONDataView();
	}

}
