/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.uneartmng.policelinkaply.web;

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
import isry.uneartmng.policelinkaply.service.PicMngService;

/**
 * @파일명        : PicMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 6. 27. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 6. 27.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/uneartmng/policelinkaply")
public class PicMngController extends IsryBaseController {

	@Resource(name = "picMngService")
	private PicMngService picMngService;
		
	@RequestMapping(value="/selectRegion.do")
	public View selectRegion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsRegion", picMngService.selectRegion());
		dataRequest.setResponse("dsRegion2", picMngService.selectRegion2());
		dataRequest.setResponse("dsPicAgency", picMngService.selectPicAgency());
		dataRequest.setResponse("dsPicStation", picMngService.selectPicStation());
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectPicList.do")
	public View selectPicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
       	
       	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
       	String ctpvCd = searchParam.getValue("CTPV_CD");
       	String sggCd = searchParam.getValue("SGG_CD");
       	String onstNo = searchParam.getValue("INST_NO");
       	String sxdcSeCd = searchParam.getValue("SXDC_SE_CD");
       	String picEduCd = searchParam.getValue("PIC_EDU_CD");
       	String rgnCd = searchParam.getValue("RGN_CD");
       	
       	if(!"".equals(ctpvCd) || !"".equals(sggCd) || !"".equals(onstNo)) { // 
       		rgnCd = "";
       	}
       	
       	mapParam.put("CTPV_CD", ctpvCd);
       	mapParam.put("SGG_CD", sggCd);
       	mapParam.put("INST_NO", onstNo);
       	mapParam.put("SXDC_SE_CD", sxdcSeCd);
       	mapParam.put("PIC_EDU_CD", picEduCd);
       	mapParam.put("RGN_CD", rgnCd);
       	
       	List<Map<String, String>> dsList = picMngService.selectPicList(mapParam);
       	dataRequest.setResponse("dsList", dsList);
       	
       	return new JSONDataView();
	}
	
	@RequestMapping(value="/selectUserHisList.do")
	public View selectUserHisList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
       	ParameterGroup searchParam = dataRequest.getParameterGroup("dmList");
       	String instNo = searchParam.getValue("INST_NO");
       	List<Map<String, String>> dsList = picMngService.selectUserHisList(instNo);
       	dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectOfcdcPicList.do")
	public View selectOfcdcPicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
       	List<Map<String, Object>> dsList = picMngService.selectOfcdcPicList(dataRequest);		
		dataRequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
	}	
	@RequestMapping(value="/selectPolicePicList.do")
	public View selectPolicePicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> dsList = picMngService.selectPolicePicList(dataRequest);		
		dataRequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
	}	
}
