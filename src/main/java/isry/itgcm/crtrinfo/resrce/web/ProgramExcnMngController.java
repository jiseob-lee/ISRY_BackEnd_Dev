/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.crtrinfo.resrce.service.ProgramExcnMngService;

/**
 * @파일명        : ProgramExcnMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 8. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 8. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/crtrinfo/resrce")
public class ProgramExcnMngController {
	
	@Resource(name = "programExcnMngService")
	private ProgramExcnMngService programExcnMngService;
	
	/**
	 * @Method명   : selectProgramExcnMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 8. 
	 * @Method설명 : 실행 프로그램 목록 조회
	 */
	@RequestMapping("/selectProgramExcnMngList.do")
	public View selectProgramExcnMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> list = programExcnMngService.selectProgramExcnMngList(request, dataRequest);
		dataRequest.setResponse("dsProgramExcnMng", list);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : saveResrceProgrmExcnHrList.do
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 : 프로그램실행 저장
	 */
	@RequestMapping("/saveResrceProgrmExcnHrList.do")
	public View saveResrceProgrmExcnHrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		programExcnMngService.saveResrceProgrmExcnHrList(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	

}
