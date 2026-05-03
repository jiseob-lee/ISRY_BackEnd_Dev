/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.transportation.web;

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

import isry.aimns.opermgmt.transportation.service.TransPortationService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명        : TransPortationController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 6. 9. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 6. 9.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/aimns/opermgmt/transportation")
public class TransPortationController {

		@Resource(name = "submsService")
		private SubmsService aimnsService;
		
		@Resource(name = "transPortationService")
		private TransPortationService transPortationService;
		
		/**
		 * 
		 * @Method명   : selectTransCombo
		 * @param request
		 * @param response
		 * @param dataRequest
		 * @return
		 * @throws Exception
		 * @작성자     : Lee.Hye.Sun
		 * @작성일     : 2022. 6. 9. 
		 * @Method설명 : 교통비및식대 콤보
		 */
		@RequestMapping("/selectTransCombo.do")
		public View selectTransCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
			
			List<Map<String, Object>> listBizYrCombo = aimnsService.selectBizYrCombo(request);
			List<Map<String, Object>> listInstCombo = aimnsService.selectInstNmCombo(request);
			List<Map<String, Object>> listdsResrceCombo = aimnsService.selectResrceNmCombo(request);
			
			dataRequest.setResponse("dsBizYr", listBizYrCombo);
			dataRequest.setResponse("dsInst", listInstCombo);
			dataRequest.setResponse("dsResrce", listdsResrceCombo);
			
			return new JSONDataView();
		}
		
		/**
		 * 
		 * @Method명   : selectTransFoodList
		 * @param request
		 * @param response
		 * @param dataRequest
		 * @return
		 * @throws Exception
		 * @작성자     : Lee.Hye.Sun
		 * @작성일     : 2022. 6. 20. 
		 * @Method설명 : 교통비 및 식대 그리드
		 */
		@RequestMapping("/selectTransFoodList.do")
		public View selectTransFoodList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
			
			ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
			Map<String, String> dmSearchMap = new HashMap<>();
			dmSearchMap.put("BIZ_YR", dmSearch.getValue("BIZ_YR"));
			dmSearchMap.put("INST_NO", dmSearch.getValue("INST_NO"));
			dmSearchMap.put("RESRCE_NO", dmSearch.getValue("RESRCE_NO"));
			
			List<Map<String, String>> listBoard = transPortationService.selectTransFoodList(request, dmSearchMap);
			dataRequest.setResponse("dsList", listBoard);
			
			return new JSONDataView();
			
		}
}
