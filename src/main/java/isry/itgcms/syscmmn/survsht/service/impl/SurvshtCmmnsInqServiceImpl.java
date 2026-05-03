/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.syscmmn.survsht.mapper.SurvshtCmmnsInqMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtCmmnsInqService;

/**
 * @파일명        : SurvshtCmmnsInqServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 12. 7. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 12. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("survshtCmmnsInqService")
public class SurvshtCmmnsInqServiceImpl extends IsryBaseServiceImpl implements SurvshtCmmnsInqService {

	
	// 설문지 관리번호 생성 Service Class
//	@Resource(name = "survshtMmnService")
//	private SurvshtMmnService survshtMmnService;
		
	
	@Resource(name = "survshtCmmnsInqMapper")
	private SurvshtCmmnsInqMapper survshtCmmnsInqMapper;

	/**
	 * @Method명   : searchQustnbTmptUseYn
	 * @param dmSearchParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 7. 
	 * @Method설명 :사용할 설문지 템플릿관리번호가 사용중인지 미사용인지 여부 조회
	 */
	@Override
	public Map<String, Object> searchQustnbTmptUseYn(Map<String, Object> searchMap) throws Exception {
		// TODO Auto-generated method stub
		return survshtCmmnsInqMapper.searchQustnbTmptUseYn(searchMap);
	}

	/**
	 * @Method명   : getQustnbMngNo
	 * @param searchMap
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 8. 
	 * @Method설명 : 사용안함 각 업무단위별로 컨트롤러에서 처리 필요.
	 */
	@Override
	public Map<String, Object> getQustnbMngNo(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> searchMap) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> result = new HashMap<>();
		
//		// 설문지관리번호 복사시 재사용여부
//		String reusYn = searchMap.get("REUS_YN").toString();
//		
//		if(reusYn.equals("N")) {
//			
//			// 신규생성
//			result = survshtMmnService.copySurvshtTmptData(request, dataRequest, searchMap);
//			
//		}else if(reusYn.equals("Y")) {
//			
//			// 설문지템플릿관리번호 이미 생성되어있는 설문지관리번호 조회
//			// 만약 생성되어있는 설문지관리번호 조회시 다건인 경우 가장 최신설문지관리번호 리턴
//			
//			// 각 업무단위로 조회조건이 달라짐
//			// Ex. case 1 : 설문지템플릿관리번호, 대상자번호, case 2 : 설문지템플릿관리번호, 사례관리번호, 사례관리차수, 대상자번호
//			// 디딤 : U07, 드림: U08 AND 사례관리 .... 업무단위별로 확인 필요
//			
//		}
		
		return result;
	}
	
}
