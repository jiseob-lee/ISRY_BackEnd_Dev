/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.workaltmntmng.mapper.MnthySchdlRegInfoMngMapper;
import isry.couns.mngr.workaltmntmng.service.MnthySchdlRegInfoMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


@Service
public class MnthySchdlRegInfoMngServiceImpl extends IsryBaseServiceImpl implements MnthySchdlRegInfoMngService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "mnthySchdlRegInfoMngMapper")
	private MnthySchdlRegInfoMngMapper mnthySchdlRegInfoMngMapper;
	
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectMnthySchdlRegInfoMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 : 월별 일정등록정보 관리 조회
	 */
	@Override
	public List<Map<String, Object>> selectMnthySchdlRegInfoMngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mnthySchdlRegInfoMngMapper.selectMnthySchdlRegInfoMngList(mapParam);
	}
	
	/**
	 * @Method명   : selectMnthySchdlRegModAsgnNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMnthySchdlRegModAsgnNocs(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mnthySchdlRegInfoMngMapper.selectMnthySchdlRegModAsgnNocs(mapParam);
	}
	
	/**
	 * @Method명   : processMnthySchdlRegInfoMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	@Override
	public int processMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mnthySchdlRegInfoMngMapper.processMnthySchdlRegInfoMng(mapParam);
	}
	
	/**
	 * @Method명   : processMnthySchdlRegInfoMng2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	@Override
	public int processMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mnthySchdlRegInfoMngMapper.processMnthySchdlRegInfoMng2(mapParam);
	}
	
	/**
	 * @Method명   : deleteMnthySchdlRegInfoMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 :
	 */
	@Override
	public int deleteMnthySchdlRegInfoMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
//		return mnthySchdlRegInfoMngMapper.deleteMnthySchdlRegInfoMng(mapParam);
		return 0;
	}
	
	/**
	 * @Method명   : deleteMnthySchdlRegInfoMng2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public int deleteMnthySchdlRegInfoMng2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
//		return mnthySchdlRegInfoMngMapper.deleteMnthySchdlRegInfoMng2(mapParam);
		return 0;
	}
	
//	/**
//	 * @Method명   : insertMnthySchdlRegInfoMng
//	 * @param mapParam
//	 * @return
//	 * @throws Exception
//	 * @작성자     : Youngtae Yoo
//	 * @작성일     : 2022. 7. 7. 
//	 * @Method설명 :
//	 */
//	@Override
//	public int insertMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception {
//		// TODO Auto-generated method stub
//		return mnthySchdlRegInfoMngMapper.insertMnthySchdlRegInfoMng(mapParam);
//	}

	/**
	 * @Method명   : insertMnthySchdlRegInfoMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 11. 
	 * @Method설명 : 월별 일정등록정보 관리 - 최초 등록
	 */
	@Override
	public int insertMnthySchdlRegInfoMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";			// session ID
		int insertCount = 0;
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			
			loginId = loginVO.getId();
			
		} else {
			
			throw new AppWorksException("세션정보가 없습니다.", Alert.ERROR);
			
		}
		
		// 입력된 월별 일정등록정보 데이터 받기
		ParameterGroup monthWorkTimeGroup = dataRequest.getParameterGroup("dsMonthWorkTime");
		List<Map<String, String>> insertRowList = monthWorkTimeGroup.getAllRowList();
		
		ParameterGroup searchGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = searchGroup.getSingleValueMap();
		searchMap.put("loginId", loginId);
		
		for (Map<String, String> map : insertRowList) {
			if (map.get("MBR_NOCS") != null && !"".equals(map.get("MBR_NOCS"))) {
				
				map.put("BGNG_TIME", map.get("BGNG_HR") + ":" + map.get("BGNG_MIN"));
				map.put("END_TIME", map.get("END_HR") + ":" + map.get("END_MIN"));
				map.putAll(searchMap);
				LOGGER.debug("저장될 데이터 ::: " + map);
				
				int result = mnthySchdlRegInfoMngMapper.insertMnthySchdlRegInfoMng(map);
				
				if (result == 1) {
					insertCount++;
				}
				
			}
		}
		
		LOGGER.debug("insertRowList.size ::: " + insertRowList.size() + ", insertCount ::: " + insertCount);
		if (insertRowList.size() == insertCount) {
			
			mnthySchdlRegInfoMngMapper.insertMnthySchdlRegInfoMng2(searchMap);
			
		}
		
		
		
		return 0;
	}

	/**
	 * @Method명   : selectChcMnthySchdlList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 조회
	 */
	@Override
	public List<Map<String, Object>> selectChcMnthySchdlList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		if (paramMap.get("DEPT_CD") == null || "".equals(paramMap.get("DEPT_CD"))) {
			throw new AppWorksException("선택된 부서가 없습니다.", Alert.ERROR);
		}
		if (paramMap.get("YEAR_MONTH") == null || "".equals(paramMap.get("YEAR_MONTH"))) {
			throw new AppWorksException("조회할 월이 없습니다.", Alert.ERROR);
		}
		
		List<Map<String, Object>> resultList = mnthySchdlRegInfoMngMapper.selectChcMnthySchdlList(paramMap);
		
		LOGGER.debug("조회 결과 ::: " + resultList);
		
		return resultList;
	}

	/**
	 * @Method명   : selectChcMnthySchdlAsgnInfo
	 * @param 	   : dataRequest
	 * @return	   : Map
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 17. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 할당량 조회
	 */
	@Override
	public Map<String, Object> selectChcMnthySchdlAsgnInfo(DataRequest dataRequest) throws Exception {

		ParameterGroup searchGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchMap = searchGroup.getSingleValueMap();
		
		if (searchMap.get("DEPT_CD") == null || "".equals(searchMap.get("DEPT_CD"))) {
			throw new AppWorksException("선택된 부서가 없습니다.", Alert.ERROR);
		}
		if (searchMap.get("YEAR_MONTH") == null || "".equals(searchMap.get("YEAR_MONTH"))) {
			throw new AppWorksException("조회할 월이 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> resultMap = mnthySchdlRegInfoMngMapper.selectChcMnthySchdlAsgnInfo(searchMap);
		
		if (resultMap == null) {
			LOGGER.debug("월별 할당량의 데이터가 존재하지 않는다.");
			resultMap = new HashMap<String, Object>();
			resultMap.putAll(searchMap);
		} 
		
		LOGGER.debug("resultMap ::: " + resultMap);
		
		return resultMap;
	}

	/**
	 * @Method명   : processChcMnthySchdlMng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : int
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 월별 일정등록정보 관리 - 선택월 수정
	 */
	@Override
	public int processChcMnthySchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId = "";				//	세션 정보의 ID
		int insResult = 0;
		int updResult = 0;
		int delResult = 0;
		
		// 세션 정보에서 ID 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}

		ParameterGroup chcMnthySchdlList = dataRequest.getParameterGroup("dsMonthWorkTime");
		ParameterGroup searchGroup = dataRequest.getParameterGroup("dmSearch");
		
		Iterator<ParameterRow> insertedRows = chcMnthySchdlList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = chcMnthySchdlList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = chcMnthySchdlList.getDeletedRows();
		
		List<Map<String, String>> insertedRows2 = chcMnthySchdlList.getInsertedRowList();
		List<Map<String, String>> updatedRows2 = chcMnthySchdlList.getUpdatedRowList();
		List<Map<String, String>> deletedRows2 = chcMnthySchdlList.getDeletedRowList();
		
		LOGGER.debug("신규 Rows ::: " + insertedRows2);
		LOGGER.debug("수정 Rows ::: " + updatedRows2);
		LOGGER.debug("삭제 Rows ::: " + deletedRows2);
		
		Map<String, String> searchMap = searchGroup.getSingleValueMap();
		searchMap.put("loginId", loginId);
		
		LOGGER.debug("searchMap ::: " + searchMap);
		
		while(insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			
			mapIns.put("BGNG_TIME", mapIns.get("BGNG_HR") + ":" + mapIns.get("BGNG_MIN"));
			mapIns.put("END_TIME", mapIns.get("END_HR") + ":" + mapIns.get("END_MIN"));
			mapIns.putAll(searchMap);
			
			insResult += mnthySchdlRegInfoMngMapper.insertMnthySchdlRegInfoMng(mapIns);
		}
		
		while(updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			mapUpd.put("BGNG_TIME", mapUpd.get("BGNG_HR") + ":" + mapUpd.get("BGNG_MIN"));
			mapUpd.put("END_TIME", mapUpd.get("END_HR") + ":" + mapUpd.get("END_MIN"));
			mapUpd.put("loginId", loginId);
			
			updResult += mnthySchdlRegInfoMngMapper.updateChcMnthySchdlList(mapUpd);
		}
		
		while(deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			
			mapDel.put("loginId", loginId);
			
			delResult += mnthySchdlRegInfoMngMapper.deleteChcMnthySchdlList(mapDel);
		}
		
		LOGGER.debug("진행해야하는 Row 수 ::: " + (insertedRows2.size() + updatedRows2.size() + deletedRows2.size()));
		LOGGER.debug("진행된 Row 수 ::: " + (insResult + updResult + delResult));
		
		if ((insertedRows2.size() + updatedRows2.size() + deletedRows2.size()) == (insResult + updResult + delResult)) {
			
			mnthySchdlRegInfoMngMapper.processMnthyAsgnInfo(searchMap);
			
		} else {
			
			throw new AppWorksException("시스템 오류가 발생했습니다. 관리자에게 문의하세요.", Alert.ERROR);
			
		}
		
		return 0;
	}

	/**
	 * @Method명   : deleteProcessChcMnthySchdlMng
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 6. 
	 * @Method설명 :
	 */
	@Override
	public void deleteProcessChcMnthySchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		int ayc100Cnt = 0;
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("parameter ::: " + paramMap);
		
		// 1. 자식테이블(AYC100)에 삭제할 부모테이블(AYC110)의 PK를 FK로 가지고 있는 데이터 COUNT
		ayc100Cnt = mnthySchdlRegInfoMngMapper.selectChcMnthyDayCnt(paramMap);
		LOGGER.debug("test ::: " + ayc100Cnt);
		
		// 2. 존재하면 자식테이블에 존재하는 데이터 삭제
		if (ayc100Cnt > 0) {
			try {
				mnthySchdlRegInfoMngMapper.deleteChcMnthyDayAll(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		// 3. 부모테이블(AYC110) 데이터 삭제
		int ayc100Result  = 0;
		try {
			ayc100Result  = mnthySchdlRegInfoMngMapper.deleteMnthySchdlRegInfoMng(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.debug("결과값 ::: " + ayc100Result);

		// 4. 3번 성공시 연관된 월별게시판댓글할당(AYC170) 데이터 삭제
		if (ayc100Result > 0) {
			try {
				mnthySchdlRegInfoMngMapper.deleteMnthySchdlRegInfoMng2(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @Method명   : insertMnthySchdlRegInfoMng2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	@Override
	public int insertMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mnthySchdlRegInfoMngMapper.insertMnthySchdlRegInfoMng2(mapParam);
		
	}
	
	/**
	 * @Method명   : insertMnthySchdlRegInfoMngCopy
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> insertMnthySchdlRegInfoMngCopy(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		mnthySchdlRegInfoMngMapper.insertMnthySchdlRegInfoMngCopy(mapParam);
		return mapParam;
	}

}
