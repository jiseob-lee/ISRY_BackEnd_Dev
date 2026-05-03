/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.srnggrdngmng.srnggrdng.web;

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

import isry.csems.srnggrdngmng.srnggrdng.service.SrngGrdngService;

/**
 * @파일명        : SrngGrdngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 4. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 4.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller("csemsSrngGrdngController")
@RequestMapping(value = "/isry/csems/srnggrdngmng/srnggrdng")
public class SrngGrdngController {
	
	@Resource(name = "csemsSrngGrdngService")
	private SrngGrdngService srngGrdngService;

	/**
	 * 
	 * @Method명   : selectAplyRcptTypeCd
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 : 코드조회
	 */
	@RequestMapping(value = "/selectAplyRcptTypeCd.do")
	public View selectAplyRcptTypeCd(DataRequest dataRequest) throws Exception{
		
		dataRequest.setResponse("dsAplyRcptCd", srngGrdngService.selectAplyRcptCd());
		dataRequest.setResponse("dsMaapCd", srngGrdngService.selectMaapCd());
		dataRequest.setResponse("dsCampPrtcrCd", srngGrdngService.selectCampPrtcrCd());
		dataRequest.setResponse("dsCampYngbgsCd", srngGrdngService.selectCampYngbgsCd());
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectSrngGrdngList
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 : 면접심사채점표 (드림) 조회
	 */
	@RequestMapping(value = "/selectSrngGrdngList.do")
	public View selectSrngGrdngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{

		List<Map<String,String>> dsQustnb = srngGrdngService.selectQustnbList(request, dataRequest);
		List<Map<String,String>> dsQustnb2 = srngGrdngService.selectdsQustnb2List(request, dataRequest);
		List<Map<String,String>> dsQustnb3 = srngGrdngService.selectdsQustnb3List(request, dataRequest);
		List<Map<String,String>> dsSrngCn = srngGrdngService.selectSrngCnList(request, dataRequest);
		List<Map<String,String>> dsGrdng = srngGrdngService.selectGrdngList(request, dataRequest);
		List<Map<String,String>> dsPtcpt = srngGrdngService.selectPtcptList(request, dataRequest);
		
		dataRequest.setResponse("dsQustnb", dsQustnb);
		dataRequest.setResponse("dsQustnb2", dsQustnb2);
		dataRequest.setResponse("dsQustnb3", dsQustnb3);
		dataRequest.setResponse("dsSrngCn", dsSrngCn);
		dataRequest.setResponse("dsGrdng", dsGrdng);
		dataRequest.setResponse("dsPtcpt", dsPtcpt);
		
		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명   : saveSrngGrdngPop
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 : 면접심사채점표 (드림) 저장
	 */
	@RequestMapping(value = "/saveSrngGrdngPop.do")
	public View saveSrngGrdngPop(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		
		srngGrdngService.saveSrngGrdngPop(request, dataRequest);
		
		return new JSONDataView();
	}
	
}
