/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.DidimTrprInfoMapper;
import isry.itgcm.bizcmmns.cmmns.service.DidimTrprInfoService;

/**
* @Class Name  : DidimTrprInfoService.java
* @Description : 대상자정보조회 팝업 ServiceImpl Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 09. 15.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 09. 15.  Kwon.Min.Seo    최초작성
* </pre>
*/
@Service("didimTrprInfoService")
public class DidimTrprInfoServiceImpl extends IsryBaseServiceImpl implements DidimTrprInfoService{
	
	@Resource(name = "didimTrprInfoMapper")
	private DidimTrprInfoMapper didimTrprInfoMapper;
	
	/**
	* 대상자정보 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectDidimTrprInfoList(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		if (searchParam == null) {
			throw new AppWorksException("조회할 대상자가 없습니다..", Alert.ERROR);
		}
		Map<String, String> paramMap  = searchParam.getSingleValueMap();
		
		return didimTrprInfoMapper.selectDidimTrprInfoList(paramMap);		
		
	}

}
