/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.common.commcode.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : CommCodeController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcms/common/commcode")
public class CommCodeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectCommCdList.do")
	public @ResponseBody View selectCommCdList(DataRequest dataRequest, HttpServletRequest request) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, String>> allRowList = dataRequest.getParameterGroup("dsCommParam").getAllRowList();

		log.info("allRowList size={}", allRowList.size() );
		
		for (Map<String, String> map : allRowList) {
			log.info("[for]  CMMNS_ID={}, DS_NAME={}", map.get("CMMNS_ID"), map.get("DS_NAME") );
			dataRequest.setResponse(map.get("DS_NAME"), mgmtCmmnCodeService.selectCommonCodeUnit(map.get("CMMNS_ID"),userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectCommCdAllList
	 * @param dataRequest
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 12. 11. 
	 * @Method설명 : 공통코드 전체 가져오기 구성을 위한 url
	 */
	@RequestMapping("/selectCommCdAllList.do")
	public @ResponseBody View selectCommCdAllList(DataRequest dataRequest, HttpServletRequest request) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		if (dataRequest.getParameterGroup("dsCommParam") == null) {
			return new JSONDataView();
		}
		
		List<Map<String, String>> allRowList = dataRequest.getParameterGroup("dsCommParam").getAllRowList();

		log.info("allRowList size={}", allRowList.size() );
		
		for (Map<String, String> map : allRowList) {
			log.info("[for]  CMMNS_ID={}, DS_NAME={}", map.get("CMMNS_ID"), map.get("DS_NAME") );
			dataRequest.setResponse(map.get("DS_NAME"), mgmtCmmnCodeService.selectCommonCodeUnit(map.get("CMMNS_ID"),userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}

}
