/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.uneartmng.cnterinfo.web;

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
import isry.itgcms.util.ScpDb;
import isry.uneartmng.cnterinfo.service.CnterInfoService;

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
@RequestMapping(value = "/isry/uneartmng/cnterInfo")
public class CnterInfoController extends IsryBaseController {

	@Resource(name = "cnterInfoService")
	private CnterInfoService cnterInfoService;
		
	@RequestMapping(value="/selectCode.do")
	public View selectRegion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		ParameterGroup dsCodeList = dataRequest.getParameterGroup("dsCodeList");
		List<Map<String, String>> insertedRowList = dsCodeList.getInsertedRowList();
		for(Map<String, String> map : insertedRowList) {
			String cmmnsCdId = map.get("CMMNS_CD_ID");
			String dsName = map.get("DS_NAME");
			dataRequest.setResponse(dsName, cnterInfoService.selectCode(cmmnsCdId));
		}
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectCnterInfo.do")
	public View selectCnterInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();       	
       	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
       	String instNo = searchParam.getValue("INST_NO"); // 기관번호
       	String crtrYr = searchParam.getValue("CRTR_YR"); // 기준년도
       	mapParam.put("INST_NO", instNo);
       	mapParam.put("CRTR_YR", crtrYr);
       	dataRequest.setResponse("dmCnterInfo", cnterInfoService.selectCnterInfo(mapParam));
		return new JSONDataView();
	}
}
