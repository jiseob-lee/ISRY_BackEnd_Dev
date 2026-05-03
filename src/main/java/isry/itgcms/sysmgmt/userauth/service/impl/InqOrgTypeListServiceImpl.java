/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.InqOrgTypeListMapper;
import isry.itgcms.sysmgmt.userauth.service.InqOrgTypeListService;

/**
 * @파일명        : InqOrgTypeListServiceImpl.java
 * @프로그램 설명 : 기관 유형 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("inqOrgTypeListService")
public class InqOrgTypeListServiceImpl extends IsryBaseServiceImpl implements InqOrgTypeListService {

	@Resource(name="inqOrgTypeListMapper")
    private InqOrgTypeListMapper inqOrgTypeListMapper;

	/**
	 * @Method명   : selectOrgType
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2021. 12. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOrgType() throws Exception {
		return inqOrgTypeListMapper.selectOrgType();
	}

}
