/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.hpgemng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.couns.mngr.hpgemng.service.CounselorMngService;

/**
 * @파일명        : CounselorMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 3. 24. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 3. 24.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/couns/mngr/hpgemng")
public class CounselorMngController extends IsryBaseController{
	
	@Resource(name = "CounselorMngServiceImpl")
	private CounselorMngService CounselorMngService;
	
	/**
	 * @Method명   : counselMngList
	 * @param request
	 * @param response
	 * @param datarequeest
	 * @return
	 * @throws Exeption
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 4. 4. 
	 * @Method설명 :
	 */
	
	@RequestMapping("/counselMngList.do")
	public View counselMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
//		Logger log = LoggerFactory.getLogger(this.getClass());
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
//		log.debug("DDD : " + searchParam.toString());
		
		String indctYn = searchParam.getValue("INDCT_YN");
		String cnsltntNm = searchParam.getValue("CNSLTNT_NM");
		String cnsltntId = searchParam.getValue("CNSLTNT_ID");
		String cnsltntNckn = searchParam.getValue("CNSLTNT_NCKN");
		
		mapParam.put("INDCT_YN", indctYn);
		mapParam.put("CNSLTNT_NM", cnsltntNm);
		mapParam.put("CNSLTNT_ID", cnsltntId);
		mapParam.put("CNSLTNT_NCKN", cnsltntNckn);
		
		List<Map<String, Object>> getCounselorList = CounselorMngService.counselMngList(request, mapParam);
		
		dataRequest.setResponse("dsCounselorList", getCounselorList);
		
		return new JSONDataView();
		
	}
	
	@RequestMapping("/processCounselMng.do")
	public void processCounselMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		CounselorMngService.updateCounselMngList(request, dataRequest);
	}
	
}
