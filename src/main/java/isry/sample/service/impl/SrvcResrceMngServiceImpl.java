/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.sample.mapper.SrvcResrceMngMapper;
import isry.sample.service.SrvcResrceMngService;

/**
 * @파일명 : SrvcResrceMngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2022. 4. 29.
 * @수정자 : You Minsang
 * @수정일 : 2022. 4. 29.
 * @수정내용 : - -
 */
@Service
public class SrvcResrceMngServiceImpl implements SrvcResrceMngService {

	@Autowired
	private SrvcResrceMngMapper srvcResrceMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectSrvcResrceMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 4. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSrvcResrceMngList(Map<String, Object> mapParam) throws Exception {

		return srvcResrceMngMapper.selectSrvcResrceMngList(mapParam);
	}

	/**
	 * @Method명 : selectSrvcResrceDtlMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 5. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSrvcResrceDtlMngList(Map<String, Object> mapParam) throws Exception {

		return srvcResrceMngMapper.selectSrvcResrceDtlMngList(mapParam);
	}

	/**
	 * @Method명 : saveSrvcResrceMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 5. 4.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveSrvcResrceMngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapReturn = new HashMap<String, Object>();

		// 로그인 사용자 정보취득, 작성자, 수정자 데이터 등록
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		// 자원제공서비스 목록 저장
		ParameterGroup dsServiceList = dataRequest.getParameterGroup("dsSrvcResrceMngList");

		Iterator<ParameterRow> insertedRows = dsServiceList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsServiceList.getUpdatedRows();		

		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			srvcResrceMngMapper.insertSrvcResrceMngList(mapIns);

			// 자원번호 key값 셋팅
			mapReturn.put("RESRCE_NO", mapIns.get("RESRCE_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			srvcResrceMngMapper.updateSrvcResrceMngList(mapUpd);

			// 자원번호 key값 셋팅
			mapReturn.put("RESRCE_NO", mapUpd.get("RESRCE_NO"));

		}

		// 프로그램 목록 저장
		ParameterGroup dsProgramList = dataRequest.getParameterGroup("dsProgramList");

		Iterator<ParameterRow> insertedProgramRows = dsProgramList.getInsertedRows();
		Iterator<ParameterRow> updatedProgramRows = dsProgramList.getUpdatedRows();
		// Iterator<ParameterRow> deletedProgramRows = dsProgramList.getDeletedRows();

		while (insertedProgramRows.hasNext()) {

			Map<String, String> mapIns = insertedProgramRows.next().toMap();

			// RESRCE_NO값 존재시 자원정보는 존재 > 프로그램 추가
			// RESRCE_NO값 미존재시 신규 자원정보 등록으로 RESRCE_NO값 반환받아 처리
			if (mapIns.get("RESRCE_NO").equals("")) {
				String resrceNo = mapReturn.get("RESRCE_NO").toString();
				mapIns.put("RESRCE_NO", resrceNo);
			}

			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
								
			srvcResrceMngMapper.insertSrvcResrceProgramList(mapIns);

			// 자원번호 key값 셋팅
			mapReturn.put("RESRCE_NO", mapIns.get("RESRCE_NO"));
		}

		while (updatedProgramRows.hasNext()) {

			Map<String, String> mapUpd = updatedProgramRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			srvcResrceMngMapper.updateSrvcResrceProgramList(mapUpd);

			// 자원번호 key값 셋팅
			mapReturn.put("RESRCE_NO", mapUpd.get("RESRCE_NO"));
		}

//		// delete 동작 필요시 구현
//		while (deletedRows.hasNext()) {
//			
//			Map<String, String> mapDel = deletedProgramRows.next().toMap();
//			mapDel.put("LAST_MDFR_ID", userId);
//			srvcResrceMngMapper.deleteSrvcResrceProgramList(mapDel);
//
//		}

		return mapReturn;
	}

	/**
	 * @Method명 : selectSrvcResrceDtlProgramList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 5. 9.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSrvcResrceDtlProgramList(Map<String, Object> mapParam) throws Exception {

		return srvcResrceMngMapper.selectSrvcResrceDtlProgramList(mapParam);
	}

}
