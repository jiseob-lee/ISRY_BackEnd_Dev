/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.dyncBrd.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.itgcms.dyncBrd.mapper.DyncBrdCmnMapper;
import isry.itgcms.dyncBrd.service.DyncBrdCmnService;
import isry.itgcms.util.ScpDb;

/**
 * @파일명 : DyncNtcBrdServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2021. 12. 20.
 * @수정자 : You Minsang
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */

@Service
public class DyncBrdCmnServiceImpl implements DyncBrdCmnService {

	@Resource(name = "dyncBrdCmnMapper")
	private DyncBrdCmnMapper dyncBrdCmnMapper;

	/**
	 * @Method명 : selectDyncBrdCmnInfoList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 26.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectDyncBrdCmnInfoList(Map<String, String> mapParam) throws Exception {

		return dyncBrdCmnMapper.selectDyncBrdCmnInfoList(mapParam);
	}

	/**
	 * @Method명 : selectDyncBrdCmnColList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 26.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDyncBrdCmnColList(Map<String, String> mapParam) throws Exception {

		return dyncBrdCmnMapper.selectDyncBrdCmnColList(mapParam);
	}

	/**
	 * @Method명 : selectDyncBrdCmnColDataList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDyncBrdCmnColDataList(Map<String, Object> mapParam) throws Exception {

		return dyncBrdCmnMapper.selectDyncBrdCmnColDataList(mapParam);
	}

	/**
	 * @Method명 : selectDyncBrdCmnDtlList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDyncBrdCmnDtlList(Map<String, Object> mapParam) throws Exception {

		String strCreateYn = (String) mapParam.get("CREATE_YN");

		if (!strCreateYn.equals("Y")) {
			// 조회수 추가
			dyncBrdCmnMapper.updateDyncBrdCmnRdcntList(mapParam);
		}

		return dyncBrdCmnMapper.selectDyncBrdCmnDtlList(mapParam);
	}

}
