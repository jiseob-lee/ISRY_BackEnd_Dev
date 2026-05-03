/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.sample.mapper.TstBoardDevMapper2;
import isry.sample.service.TstBoardDevService2;

/**
 * @파일명        : TstBoardDevServiceImpl2.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Jeong.Tae.Young
 * @작성일        : 2022. 3. 23. 
 * @수정자        : Jeong.Tae.Young
 * @수정일        : 2022. 3. 23.
 * @수정내용      : 
 * -                
 * -                
 */
@Service
public class TstBoardDevServiceImpl2 extends IsryBaseServiceImpl implements TstBoardDevService2 {

	@Resource(name = "tstBoardDevMapper2")
	private TstBoardDevMapper2 tstBoardDevMapper2;
	
	/**
	 * @Method명   : selectSysDate
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Tae.Young
	 * @작성일     : 2022. 3. 23. 
	 * @Method설명 :
	 */
	public String selectSysDate() throws Exception {

		return selectSysDate("YYYY-MM-DD");
	}
	
	public String selectSysDate(String strFormat) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("STR_FORMAT", strFormat);
		
		return tstBoardDevMapper2.selectSysDate(mapParam);
	}
	
	/**
	 * @Method명   : getTotalCount
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 30. 
	 * @Method설명 :
	 */
	@Override
	public String getTotalCount() throws Exception {
		
		return tstBoardDevMapper2.getTotalCount();
	}
	
	/**
	 * @Method명   : selectBoardList
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> mapParam) throws Exception {
		
		return tstBoardDevMapper2.selectBoardList(mapParam);
	}

}
