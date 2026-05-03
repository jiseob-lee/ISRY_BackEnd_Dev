/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmt.indivsprt.service.impl;

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

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.pubmt.indivsprt.service.CertiService;
import isry.redis.service.RedisService;
import isry.pubmt.indivsprt.mapper.CertiMapper;


/**
 * @파일명 : CertiServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Kim.seong.gyu
 * @작성일 : 2022. 02. 24.
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */

@Service
public class CertiServiceImpl extends IsryBaseServiceImpl implements CertiService {

	@Resource(name = "certiMapper")
	private CertiMapper certiMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	// 디자인  설정 
	@Override
	public List<Map<String, Object>> selectCertiColList(   Map<String, String> mapParam   ) throws Exception {

		return certiMapper.selectCertiColList(mapParam);
		
	}
	
	/**
	 * @Method명   : selectCertiList
	 * @param mapParam
	 * @return
	 * @throws Exception
     * @작성자 : Kim.seong.gyu
     * @작성일 : 2022. 02. 24.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCertiList(Map<String, String> mapParam) throws Exception {
		
		return certiMapper.selectCertiList(mapParam);
	}
	
//	/**
//	 * @Method명 : selectBoardList
//	 * @return
//	 * @throws Exception
//	 * @작성자 : Song.Young.Il
//	 * @작성일 : 2021. 12. 20.
//	 * @Method설명 :
//	 */
//	@Override
//	public List<Map<String, Object>> selectCertiList(Map<String, String> mapParam) throws Exception {
//		
//		// 조합을 위한 Return 리스트 
//		List<Map<String, Object>> listReturn 	   = new ArrayList<Map<String,Object>>() ; 
//		
//		// 게시판 기본 데이터  
//		List<Map<String, Object>> listCerti  =  certiMapper.selectCertiList(mapParam);
//		
//		// 컬럼 기본 데이터 (현재 조회 기준 없이 전체 9건 조회)
//		List<Map<String, Object>> listCertiColData = certiMapper.selectCertiColDataList(mapParam);
//			
//		
//		for(int i=0; i<listCerti.size(); i++) {
//			
//			// 조합을 위한 맵 선언 
//			Map<String, String> mapReturn = new HashMap<String, String>();
//			
//			//게시판 항목을 만들기위한 Map선언
//			Map<String, String> mapRecord = new HashMap<String, String>();
//			
//			mapRecord = (Map) listCerti.get(i) ;
//			
//			// 게시판 테이블( CMN_TMP_BOARD ) 의 테이블 컬럼 순서 뒤의 맨 마지막에 붙인다. 
//			// BRD_SEQ,TIT, |------ 동적 컬럼 추가 -------------|, WRI_DT, WRI_NM
//			
//			// 순번 	
//            mapReturn.put("BOARD_ID",mapRecord.get("BOARD_ID") ) ;
//            
//						
//			// 순번- 넘버형  	
//			mapReturn.put("BRD_SEQ",String.valueOf(mapRecord.get("BRD_SEQ")) ) ;
//			
//			//제목	
//			mapReturn.put("TIT",mapRecord.get("TIT") ) ;
//
//			
//			//컬럼 데이터를 가져온다 
//			for(int j=0; j<listCertiColData.size(); j++) {
//
//				//게시판 항목을 만들기위한 Map선언
//				Map<String, String> mapCol = (Map) listCertiColData.get(j) ;
//				
//				System.out.println("mapCol.toString()?"+mapCol.toString());
//				
//				Object[] makeKey = mapCol.keySet().toArray();
//				Arrays.sort(makeKey);
//				for (String nKey : mapCol.keySet()  ) {
//					System.out.println("key.get("+nKey+")"+ mapCol.toString() ) ;
//				}
//				
//			}
//			
//			mapReturn.put("WRI_DT",mapRecord.get("WRI_DT") ) ;
//
//			mapReturn.put("WRI_NM",mapRecord.get("WRI_NM") ) ;
//			
//			
//		}
//
//		return certiMapper.selectCertiList(mapParam);
//	}
	
	
	// 동적 컬럼을 처리하는 메서드  
	@Override
	public List<Map<String, Object>> selectCertiColDataList(Map<String, String> mapParam) throws Exception {
	
		List<Map<String, Object>> listCertiColData = certiMapper.selectCertiColDataList(mapParam);
		
		return listCertiColData ;
	}

	
	
	//컬럼별 데이터 조회
	/*
	@Override
	public List<Map<String, Object>> selectCertiColDataList_OLD(Map<String, String> mapParam) throws Exception {
		
		
	
		List<Map<String, Object>> listCertiColData = certiMapper.selectCertiColDataList( mapParam);
		
		log.debug("listCertiColData.size()???"+listCertiColData.size() );
		
		//동적처리 리턴을 위한 Map 선언 처리 
		Map<String, String> mapReturn = new HashMap<String, String>(); 
		
		for(int i=0; i<listCertiColData.size(); i++) {
			
			Map<String, String> mapData = new HashMap<String, String>(); 
			
			mapData = (Map) listCertiColData.get(i) ;
			
			log.debug("mapData???"+mapData.toString() ) ;
		}
		
		return listCertiColData ;
	}
	*/


	
	/**
	 * @Method명 : saveCertiList
	 * @param dataRequest
     * @작성자 : Kim.seong.gyu
     * @작성일 : 2022. 02. 24.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveCertiList(HttpServletRequest request, DataRequest dataRequest) {

		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCertiList");
		
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

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

			mapIns.put("FRST_RGTR_ID", userId);
			certiMapper.insertCertiList(mapIns);
			
		}

		
		while (updatedRows.hasNext()) {
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			certiMapper.updateCertiList(mapUpd);
			
		}

		while (deletedRows.hasNext()) {
			
			certiMapper.deleteCertiList(deletedRows.next().toMap());
				
		}
		
		return mapReturn;
	}

	/**
	 * @Method명 : selectSysDate
	 * @return
     * @작성자 : Kim.seong.gyu
     * @작성일 : 2022. 02. 24.
	 * @Method설명 :
	 */
	public String selectSysDate() throws Exception {
		return selectSysDate("YYYY-MM-DD");
	}

	public String selectSysDate(String strFormat) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("STR_FORMAT", strFormat);

		return certiMapper.selectSysDate(mapParam);
	}
}
