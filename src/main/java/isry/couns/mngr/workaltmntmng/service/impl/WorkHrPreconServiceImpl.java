/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.workaltmntmng.mapper.WorkHrPreconMapper;
import isry.couns.mngr.workaltmntmng.service.WorkHrPreconService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class WorkHrPreconServiceImpl extends IsryBaseServiceImpl implements WorkHrPreconService {

	@Resource(name = "workHrPreconMapper")
	private WorkHrPreconMapper workHrPreconMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectWorkHrPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkHrPreconList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("unitTaskWkCd", loginVO.getUntTaskwkSeCd());
		
		return workHrPreconMapper.selectWorkHrPreconList(mapParam);
	}
	
	/**
	 * @Method명   : selectWorkHrPreconDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkHrPreconDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workHrPreconMapper.selectWorkHrPreconDetail(mapParam);
	}

	/**
	 * @Method명   : selectAllWorkHrPrecon
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 : 엑셀 다운로드할 데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectAllWorkHrPrecon(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workHrPreconMapper.selectAllWorkHrPrecon(mapParam);
	}
	
}
