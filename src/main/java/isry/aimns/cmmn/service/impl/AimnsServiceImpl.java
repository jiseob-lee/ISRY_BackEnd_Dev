/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.cmmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import isry.aimns.cmmn.mapper.AimnsMapper;
import isry.aimns.cmmn.service.AimnsService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : AimnsServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 6. 7. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 6. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("aimnsService")
public class AimnsServiceImpl implements AimnsService{

	@Resource(name = "aimnsMapper")
	private AimnsMapper aimnsMapper;

	/**
	 * @Method명   : selectBizYrCombo
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectBizYrCombo() throws Exception {
		return aimnsMapper.selectBizYrCombo();
	}

	/**
	 * @Method명   : selectInstCombo
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectInstCombo() throws Exception {
		return aimnsMapper.selectInstCombo();
	}

	/**
	 * @Method명   : selectProgrmCombo
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 7. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectProgrmCombo() throws Exception {
		return aimnsMapper.selectProgrmCombo();
	}

	/**
	 * @Method명   : selectResrceCombo
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 6. 30. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectResrceCombo() throws Exception {
		return aimnsMapper.selectResrceCombo();
	}

	
}
