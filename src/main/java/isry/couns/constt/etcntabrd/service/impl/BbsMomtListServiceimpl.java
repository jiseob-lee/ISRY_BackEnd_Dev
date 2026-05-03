/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.couns.constt.etcntabrd.mapper.BbsMomtListMapper;
import isry.couns.constt.etcntabrd.service.BbsMomtListService;
import isry.couns.taskwksprt.taskwkandatdmng.service.impl.TaskwkReprtsServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsMomtListServiceimpl implements BbsMomtListService {


	@Resource(name = "bbsMomtListMapper")
	private BbsMomtListMapper bbsMomtListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskwkReprtsServiceImpl.class);
	
	/**
	 * @Method명   : subOnLoad
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.seong.gen
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return bbsMomtListMapper.getTotalCount(mapParam);
	}
	
	public List<Map<String, Object>> selectBbsMomtList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsMomtListMapper.selectBbsMomtList(mapParam);
	}
	
	@Override
	public Map<String, Object> selectBbsMomtDetail(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		Map<String, Object> returnMap = bbsMomtListMapper.selectBbsMomtDetail(paramMap);
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> saveBbsMomtProc(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();		
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			
//			System.out.println("ddddddd::"+mapIns.get("ATFINO"));
//			if(mapIns.get("ATFINO")==null&&mapIns.get("ATFINO").isEmpty()) {
//			}else {
//				
//			}
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			bbsMomtListMapper.insertBbsMomtReg(mapIns);
			
			mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));				
			
			
			Map<String, Object> mapParam = new HashMap<String, Object>();
			mapParam.put("INDEX_SN", mapIns.get("INDEX_SN"));
			mapParam.put("FRST_REG_DT", mapIns.get("FRST_REG_DT"));
			mapParam.put("LAST_MDFCN_DT", mapIns.get("LAST_MDFCN_DT"));
			mapParam.put("ATFINO", mapIns.get("ATFINO"));
			mapParam.put("FRST_RGTR_ID", userId);
			mapParam.put("LAST_MDFR_ID", userId);
			
//			bbsMomtListMapper.insertBbsMomtFileReg(mapParam);
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsMomtListMapper.updateBbsMomt(mapUpd);
			
			mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));
			
//			System.out.println("ddididiid::"+dsBoardList.get(0).getValue("ATFINO"));
			if(dsBoardList.get(0).getValue("ATFINO") != null) {
//				System.out.println("ddididiid::"+dsBoardList.get(0).getValue("ATFINO"));
				Map<String, Object> mapParam = new HashMap<String, Object>();
				mapParam.put("INDEX_SN", mapUpd.get("INDEX_SN"));
				mapParam.put("ATFINO", mapUpd.get("ATFINO"));
				
				bbsMomtListMapper.updateBbsMomtFile(mapParam);
				
			}
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsMomtListMapper.deleteBbsMomt(mapDel);
			
		}
		
		return mapReturn;
	}

	/**
	 * @Method명   : insertChmtDtl
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 자녀맘터치업로드 등록
	 */
	@Override
	public int insertChmtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId;				// sessionID
		int returnVal = 0;			// 결과값
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmChmtDtl");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		paramMap.put("loginId", loginId);
		
		// 1.AYA300(카운셀링카드정보) 테이블 INSERT
		bbsMomtListMapper.insertBbsMomtReg(paramMap);
		
		if (paramMap.get("INDEX_SN") != null) {
			
			returnVal = bbsMomtListMapper.insertBbsMomtFileReg(paramMap);
			
		} else {
			
			throw new AppWorksException("현재 등록이 안됩니다.\n관리자한테 문의하세요", Alert.ERROR);
			
		}
		
		return returnVal;
	}

	/**
	 * @Method명   : updateChmtDtl
	 * @param 	   : requset
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 자녀맘터치업로드 수정
	 */
	@Override
	public int updateChmtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId;				// sessionID
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmChmtDtl");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("loginId", loginId);
		
		int resultVal = bbsMomtListMapper.updateChmtBass(paramMap);
		
		if (resultVal == 0) {
			
			throw new AppWorksException("관리자에게 문의하세요.", Alert.ERROR);
			
		}
			
		return resultVal;
	}	
	
	/**
	 * @Method명   : deleteChmtDtl
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 12. 16. 
	 * @Method설명 : 자녀맘터치업로드 삭제
	 */
	@Override
	public int deleteChmtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId;				// sessionID
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
			
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmChmtDtl");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("loginId", loginId);
		
		int resultVal = bbsMomtListMapper.deleteChmtBass(paramMap);
		
		if (resultVal == 1) {
			
			resultVal = bbsMomtListMapper.deleteChmtDetail(paramMap);
			
			if (resultVal == 0) {
				
				throw new AppWorksException("관리자에게 문의하세요.", Alert.ERROR);
				
			}
			
		} else {
			
			throw new AppWorksException("관리자에게 문의하세요.", Alert.ERROR);
			
		}
		
		return resultVal;
	}

}
