/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.hpgemng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.hpgemng.mapper.CounselorMngMapper;
import isry.couns.mngr.hpgemng.service.CounselorMngService;

/**
 * @파일명        : CounselorMngServiceImpl.java
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

@Service("CounselorMngServiceImpl")
public class CounselorMngServiceImpl extends IsryBaseServiceImpl implements CounselorMngService{

	@Resource (name="CounselorMngMapper")
	private CounselorMngMapper counselorMngMapper;
	
	/**
	 * @Method명   : counselMngList
	 * @param request
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 4. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> counselMngList(HttpServletRequest request, Map<String, Object> mapParam)
			throws Exception {
		// TODO Auto-generated method stub
		return counselorMngMapper.selectCounselorMngList(mapParam);
	}
	
	/**
	 * @Method명   : updateCounselMngList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 4. 5. 
	 * @Method설명 :
	 */
	@Override
	public void updateCounselMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		
		ParameterGroup Param = dataRequest.getParameterGroup("dsCounselorList");

		if (Param != null) {
			List<Map<String, String>> CounselorList = Param.getAllRowList();
			
			for (Map<String, String> map : CounselorList) {
		
				counselorMngMapper.updateCounselorIndct(map);
				
			}
		}
	}
	
}
