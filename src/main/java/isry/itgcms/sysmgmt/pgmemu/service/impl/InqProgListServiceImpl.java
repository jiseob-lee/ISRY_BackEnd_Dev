/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.pgmemu.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.cmmncode.mapper.MgmtCmmnCodeMapper;
import isry.itgcms.sysmgmt.file.service.MgmtFileService;
import isry.itgcms.sysmgmt.pgmemu.mapper.InqProgListMapper;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.pgmemu.service.InqProgListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * 
 * @파일명        : InqProgListServiceImpl.java
 * @프로그램 설명 : 프로그램 관리 서비스
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -
 */
@Service("inqProgListService")
public class InqProgListServiceImpl extends IsryBaseServiceImpl implements InqProgListService {

	private String strWasFileBasePath = EgovProperties.getProperty("globals", "isry.globals.wasupload.file.folder");
	private String strWebFileBasePath = EgovProperties.getProperty("globals", "isry.globals.webupload.file.folder");
	
	@Resource(name="inqProgListMapper")
    private InqProgListMapper inqProgListMapper;

	@Resource(name="mgmtCmmnCodeMapper")
    private MgmtCmmnCodeMapper mgmtCmmnCodeMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name="mgmtFileService")
	private MgmtFileService mgmtFileService;

	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;
	
	@Override
	public List<Map<String, Object>> selectProgram(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		String search = "";
		if (parameterGroup != null) {
			search = parameterGroup.getValue("search");
		}
		Map<String, String> map = new HashMap<>();
		if (search != null && !"".equals(search)) {
			map.put("SEARCH", search);
		}
		return inqProgListMapper.selectProgram(map);
	}

	@Override
	public List<Map<String, Object>> selectWorkUnit() throws Exception {
		//return inqProgListMapper.selectWorkUnit();
		return mgmtCmmnCodeMapper.selectCodeValue("UNT_SYS_SE_CD");
	}

	@Override
	public List<Map<String, Object>> selectProgramStatus() throws Exception {
		//return inqProgListMapper.selectProgramStatus();
		return mgmtCmmnCodeMapper.selectCodeValue("PROGRM_USE_SE_CD");
	}
	
	@Override
	public List<Map<String, Object>> saveProgram(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsEndPoints");
		
		Iterator<ParameterRow> insertedRows = parameterGroup.getInsertedRows();
		Iterator<ParameterRow> updatedRows = parameterGroup.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = parameterGroup.getDeletedRows();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		Map<String, String> map = null;

		while (insertedRows.hasNext()) {
			map = insertedRows.next().toMap();
			map.put("FRST_RGTR_ID", userId);
			map.put("LAST_MDFR_ID", userId);
			map.put("USER_ID", userId);
			map.put("DATAA_CHG_SE_CD", "I");	//데이터변경 구분코드 "신규"
			
			List<Map<String, String>> fileInfoList = mgmtFileService.gridFileUpload(request, dataRequest, map, strWebFileBasePath);

			for(Map<String, String> fileInfo : fileInfoList) {
				map.put(fileInfo.get("FILE_ID_COLUMN"), fileInfo.get("ATFINO"));
			}
			
			log.debug("inserted : " + map.get("endPoint"));
			inqProgListMapper.insertProgram(map);
			inqProgListMapper.insertProgramHistory(map);	//프로그램이력 기록	
		}

		while (updatedRows.hasNext()) {
			map = updatedRows.next().toMap();
			map.put("USER_ID", userId);
			map.put("DATAA_CHG_SE_CD", "U");	//데이터변경 구분코드 "변경"

			List<Map<String, String>> fileInfoList = mgmtFileService.gridFileUpload(request, dataRequest, map, strWebFileBasePath);

			if (fileInfoList == null || fileInfoList.size() == 0) {
				map.put("ATFINO", "");
				map.put("ATCMFL_NM", "");
				log.info("#### FILE DELETE");
			} else {
				for (Map<String, String> fileInfo : fileInfoList) {
					map.put(fileInfo.get("FILE_ID_COLUMN"), fileInfo.get("ATFINO"));
					log.info("#### FILE_ID_COLUMN : " + fileInfo.get("FILE_ID_COLUMN") + ", ATFINO : " + fileInfo.get("ATFINO"));
				}
			}
			
			log.debug("updated : " + map.get("endPoint"));
			inqProgListMapper.updateProgram(map);
			inqProgListMapper.insertProgramHistory(map);	//프로그램이력 기록
		}

		while (deletedRows.hasNext()) {
			map = deletedRows.next().toMap();
			map.put("USER_ID", userId);
			map.put("DATAA_CHG_SE_CD", "D");	//데이터변경 구분코드 "삭제"
			
			mgmtFileService.gridFileUpload(request, dataRequest, map, strWebFileBasePath);
			
			log.debug("deleted : " + map.get("endPoint"));
			inqProgListMapper.insertProgramHistory(map);	//프로그램이력 기록
			inqProgListMapper.deleteProgram(map);
		}
		
		// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
		mgmtMenuMapper.updateUserMenuUpdateCountIncreaseAll();
		
		return null;
	}

}
