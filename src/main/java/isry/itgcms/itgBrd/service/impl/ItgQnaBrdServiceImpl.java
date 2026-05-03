/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.itgBrd.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.itgcms.itgBrd.mapper.ItgQnaBrdMapper;
import isry.itgcms.itgBrd.service.ItgQnaBrdService;

/**
 * @파일명 : itgBrdCmnServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2022. 6. 30.
 * @수정자 : You Minsang
 * @수정일 : 2022. 6. 30.
 * @수정내용 : - -
 */
@Service("itgQnaBrdService")
public class ItgQnaBrdServiceImpl implements ItgQnaBrdService {

	@Resource(name = "itgQnaBrdMapper")
	private ItgQnaBrdMapper itgQnaBrdMapper;

	/**
	 * @Method명   : selectItgQnaBrdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 15.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectItgQnaBrdList(Map<String, Object> mapParam) throws Exception {
		return itgQnaBrdMapper.selectItgQnaBrdList(mapParam);
	}

	/**
	 * @Method명   : selectSysItgQnaBrdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 21.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSysItgQnaBrdList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
