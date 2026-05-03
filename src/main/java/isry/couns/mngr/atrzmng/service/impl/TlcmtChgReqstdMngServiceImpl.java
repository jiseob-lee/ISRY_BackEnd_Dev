/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.atrzmng.mapper.TlcmtChgReqstdMngMapper;
import isry.couns.mngr.atrzmng.service.TlcmtChgReqstdMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class TlcmtChgReqstdMngServiceImpl extends IsryBaseServiceImpl implements TlcmtChgReqstdMngService {

	@Resource(name = "tlcmtChgReqstdMngMapper")
	private TlcmtChgReqstdMngMapper tlcmtChgReqstdMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectTlcmtChgReqstdMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTlcmtChgReqstdMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("untTaskwkSeCd", loginVO.getUntTaskwkSeCd());
		
		return tlcmtChgReqstdMngMapper.selectTlcmtChgReqstdMngList(mapParam);
	}
	
	/**
	 * @Method명   : selectTlcmtChgReqstdMngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTlcmtChgReqstdMngDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMngMapper.selectTlcmtChgReqstdMngDetail(mapParam);
	}
	
	/**
	 * @Method명   : searchComboBoxAprv
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMngMapper.searchComboBoxAprv(mapParam);
	}
	
	/**
	 * @Method명   : updateTlcmtChgReqstdMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	@Override
	public int updateTlcmtChgReqstdMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMngMapper.updateTlcmtChgReqstdMng(mapParam);
	}
	
	/**
	 * @Method명   : deleteTlcmtChgReqstdMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	@Override
	public int deleteTlcmtChgReqstdMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMngMapper.deleteTlcmtChgReqstdMng(mapParam);
	}
	
	
	
	
	
	
}
