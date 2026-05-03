/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.rsvtmng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.syscmmn.rsvtmng.mapper.RsvtMngMapper;
import isry.itgcms.syscmmn.rsvtmng.service.RsvtMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
//import isry.itgcms.util.Masking;
//import isry.itgcms.util.ScpDb;
//import isry.itgcms.util.StringUtil;
import isry2.itgcms.syscmmn.rsvtmng.mapper.RsvtMng2Mapper;

/**
 * @파일명        : RsvtMngServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 8. 29. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 8. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("rsvtMngService")
public class RsvtMngServiceImpl extends IsryBaseServiceImpl implements RsvtMngService{
	
	@Resource(name = "rsvtMngMapper")
	private RsvtMngMapper rsvtMngMapper;
	
	@Resource(name = "rsvtMng2Mapper")
	private RsvtMng2Mapper rsvtMng2Mapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	// 암복호화 관련 모듈
	//private final ScpDb scpDb = new ScpDb();
	
	
	// 채번
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;
	
	
	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 : 단위업무구분 코드 조회 UNT_TASKWK_SE_CD	
	 */
	@Override
	public String selectTaskwkSeCd(Map<String, Object> requestMap) throws Exception {
		return rsvtMngMapper.selectTaskwkSeCd(requestMap);
	}

	/**
	 * @Method명   : getResrceClMngListTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 :
	 */
	@Override
	public int getResrceClMngListTotalCount(Map<String, Object> mapParam) throws Exception {
		return rsvtMngMapper.getResrceClMngListTotalCount(mapParam);
	}

	/**
	 * @Method명   : selectResrceClMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectResrceClMngList(Map<String, Object> mapParam) throws Exception {
		return rsvtMngMapper.selectResrceClMngList(mapParam);
	}

	/**
	 * @Method명   : saveResrceClMngDtl
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 :
	 */
	@Override
	public void saveResrceClMngDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		
		Iterator<ParameterRow> insertedDsList = dsList.getInsertedRows();		
		Iterator<ParameterRow> updatedDsList  = dsList.getUpdatedRows();		
		Iterator<ParameterRow> deletedDsList  = dsList.getDeletedRows();	
		
		
		// INSERT
		while (insertedDsList.hasNext()) {
			
			String sWprkSqn      = "";	// 채번번호
			
			// 초기 집단연계지원관리번호 채번
			Map<String, String> seqMap = new HashMap<>();
			Map<String, Object> valMap = new HashMap<>();
			
			seqMap.put("USER_ID",       dmDtlParam.getValue("USER_ID"));
			seqMap.put("RENU_NO_SE_CD", "RC");					// 자원분류관리고유번호 채번코드
			seqMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
			
			// 채번서비스 호출
			valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
			sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 자원분류관리고유번호 채번 발번
//			log.debug("RsvtMngServiceImpl.saveResrceClMngDtl.sWprkSqn=[" + sWprkSqn + "]");
			
			
			
			Map<String, String> mapIns = insertedDsList.next().toMap();
			
//			log.debug("insertedDsList INST_NO=[" + mapIns.get("INST_NO") + "]");
//			log.debug("insertedDsList UNT_TASKWK_SE_CD=[" + mapIns.get("UNT_TASKWK_SE_CD") + "]");
//			log.debug("insertedDsList USER_ID=[" + mapIns.get("USER_ID") + "]");
//			log.debug("insertedDsList RESRCE_CL_MNG_LCLAS_SE_CD=[" + mapIns.get("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
//			log.debug("insertedDsList RESRCE_NM=[" + mapIns.get("RESRCE_NM") + "]");
//			log.debug("insertedDsList USE_YN=[" + mapIns.get("USE_YN") + "]");
//			log.debug("insertedDsList SCHDL_RSVT_CNCTN_USE_YN=[" + mapIns.get("SCHDL_RSVT_CNCTN_USE_YN") + "]");
			
			// 자원분류관리 자원 등록 -> 일부 쿼리 처리
			mapIns.put("RESRCE_CL_MNG_ESNTAL_NO", sWprkSqn);             								// 자원분류관리고유번호
			
			rsvtMngMapper.insertResrceClMngDtl(mapIns);
		}
		
		
		// UPDATE
		while (updatedDsList.hasNext()) {			
			Map<String, String> mapUpd = updatedDsList.next().toMap();	
			
//			log.debug("updatedDsList RESRCE_CL_MNG_ESNTAL_NO=[" + mapUpd.get("RESRCE_CL_MNG_ESNTAL_NO") + "]");
//			log.debug("updatedDsList INST_NO=[" + mapUpd.get("INST_NO") + "]");
//			log.debug("updatedDsList UNT_TASKWK_SE_CD=[" + mapUpd.get("UNT_TASKWK_SE_CD") + "]");
//			log.debug("updatedDsList USER_ID=[" + mapUpd.get("USER_ID") + "]");
//			log.debug("updatedDsList RESRCE_CL_MNG_LCLAS_SE_CD=[" + mapUpd.get("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
//			log.debug("updatedDsList RESRCE_NM=[" + mapUpd.get("RESRCE_NM") + "]");
//			log.debug("updatedDsList USE_YN=[" + mapUpd.get("USE_YN") + "]");
//			log.debug("updatedDsList SCHDL_RSVT_CNCTN_USE_YN=[" + mapUpd.get("SCHDL_RSVT_CNCTN_USE_YN") + "]");
			
			// update 경우 로그인 사용자 아이디 적용
			mapUpd.put("LAST_MDFR_ID",   dmDtlParam.getValue("USER_ID"));	
			
			rsvtMngMapper.updateResrceClMngDtl(mapUpd);
			
		}

		
		// DELETE
		while (deletedDsList.hasNext()) {			
			Map<String, String> mapDel = deletedDsList.next().toMap();
			
//			log.debug("deletedDsList RESRCE_CL_MNG_ESNTAL_NO=[" + mapDel.get("RESRCE_CL_MNG_ESNTAL_NO") + "]");
//			log.debug("deletedDsList INST_NO=[" + mapDel.get("INST_NO") + "]");
//			log.debug("deletedDsList UNT_TASKWK_SE_CD=[" + mapDel.get("UNT_TASKWK_SE_CD") + "]");
//			log.debug("deletedDsList USER_ID=[" + mapDel.get("USER_ID") + "]");
//			log.debug("deletedDsList RESRCE_CL_MNG_LCLAS_SE_CD=[" + mapDel.get("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
//			log.debug("deletedDsList RESRCE_NM=[" + mapDel.get("RESRCE_NM") + "]");
//			log.debug("deletedDsList USE_YN=[" + mapDel.get("USE_YN") + "]");
//			log.debug("deletedDsList SCHDL_RSVT_CNCTN_USE_YN=[" + mapDel.get("SCHDL_RSVT_CNCTN_USE_YN") + "]");
			
			// update 경우 로그인 사용자 아이디 적용
			mapDel.put("LAST_MDFR_ID",   dmDtlParam.getValue("USER_ID"));	
			
			rsvtMngMapper.deleteResrceClMngDtl(mapDel);
						
		}
	}

	/**
	 * @Method명   : selectResrceClMngDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectResrceClMngDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dmDetailParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("INST_NO", dmDetailParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDetailParam.getValue("USER_ID"));
		mapParam.put("ENFSN_NO", dmDetailParam.getValue("ENFSN_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDetailParam.getValue("UNT_TASKWK_SE_CD"));
		
		return rsvtMngMapper.selectResrceClMngDtl(mapParam);
	}

	/**
	 * @Method명   : selectResrceClMngUseYlist
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectResrceClMngUseYlist(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dmDetailParam == null ? "" : dmDetailParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("INST_NO", dmDetailParam == null ? "" : dmDetailParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDetailParam == null ? "" : dmDetailParam.getValue("USER_ID"));
		mapParam.put("ENFSN_NO", dmDetailParam == null ? "" : dmDetailParam.getValue("ENFSN_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDetailParam == null ? "" : dmDetailParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dmDetailParam == null ? "" : dmDetailParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		
		return rsvtMngMapper.selectResrceClMngUseYlist(mapParam);
	}

	/**
	 * @Method명   : selectResrceNmDpcnChkList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 : 등록, 수정 시 중복 체크 용 리스트
	 */
	@Override
	public List<Map<String, Object>> selectResrceNmDpcnChkList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dsListParam = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("ENFSN_NO", dmDtlParam.getValue("ENFSN_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dmDtlParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dsListParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		
		
//		log.debug("getCscAltmntRsvtListTotalCount INST_NO=[" + dsFromParam.getValue("INST_NO") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount USER_ID=[" + dsFromParam.getValue("USER_ID") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount TASKWK_SYS_SE_CD=[" + dsFromParam.getValue("TASKWK_SYS_SE_CD") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount CSC_ESNTAL_NO=[" + dsFromParam.getValue("CSC_ESNTAL_NO") + "]");
//		log.debug("getCscAltmntRsvtListTotalCount SEARCH_DATE=[" + dmSearchParam.getValue("SEARCH_DATE") + "]");
		
		
		return rsvtMngMapper.selectResrceNmDpcnChkList(mapParam);
	}

	/**
	 * @Method명   : resrceNmDpcnChk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> resrceNmDpcnChk(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dsListParam = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("ENFSN_NO", dmDtlParam.getValue("ENFSN_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dmDtlParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dsListParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		mapParam.put("RESRCE_NM", dsListParam.getValue("RESRCE_NM"));
		
		
		//String useYn = dsListParam.getValue("USE_YN").toString();
		
		// 자원등록 현황 조회
		List<Map<String, Object>> dpcnList = new ArrayList<Map<String,Object>>();
				
		String dpcnChkType = dmDtlParam.getValue("SAVE_TYPE").toString();
		
		//String resultDpcnYn = "";
		
		
		if(dpcnChkType.equals("I")) {
			dpcnList = rsvtMngMapper.selectResrceNmDpcnInsertTypeChkList(mapParam);
			
		}else if(dpcnChkType.equals("U")){
			dpcnList = rsvtMngMapper.selectResrceNmDpcnUpdateTypeChkList(mapParam);
		}
		
		
		Map<String, Object> resrceNmDpcnChkMap = new HashMap<String, Object>();
		
		if(dpcnList.size() == 0) {
			
			resrceNmDpcnChkMap.put("DPCN_YN", "N");
			resrceNmDpcnChkMap.put("USE_YN", "");
			//resultDpcnYn = "N";
			
		}else if(dpcnList.size() > 0) {
			
			String resrceNm = dsListParam.getValue("RESRCE_NM").toString();
			
			for (int i = 0; i < dpcnList.size(); i ++) {

				String chkResrceNm = dpcnList.get(i).get("RESRCE_NM").toString();
				String useYn = dpcnList.get(i).get("USE_YN").toString();
				
				if(resrceNm.equals(chkResrceNm)) {
					
					// 중복
					//resultDpcnYn = "Y";
					resrceNmDpcnChkMap.put("DPCN_YN", "Y");
					resrceNmDpcnChkMap.put("USE_YN", useYn);
					break;
					
				}else {
					
					// 중복 아님
					//resultDpcnYn = "N";
					resrceNmDpcnChkMap.put("DPCN_YN", "N");
					resrceNmDpcnChkMap.put("USE_YN", "");
					
				}
			}
		}
		
		
//		if(useYn.equals("N")) {
//			
//			resultDpcnYn = "N";
//			
//		}else{
//			
//			if(dpcnChkType.equals("I")) {
//				dpcnList = rsvtMngMapper.selectResrceNmDpcnInsertTypeChkList(mapParam);
//				
//			}else if(dpcnChkType.equals("U")){
//				dpcnList = rsvtMngMapper.selectResrceNmDpcnUpdateTypeChkList(mapParam);
//			}
//			
//			if(dpcnList.size() == 0) {
//				
//				resultDpcnYn = "N";
//				
//			}else if(dpcnList.size() > 0) {
//				
//				String resrceNm = dsListParam.getValue("RESRCE_NM").toString();
//				
//				for (int i = 0; i < dpcnList.size(); i ++) {
//
//					String chkResrceNm = dpcnList.get(i).get("RESRCE_NM").toString();
//					
//					if(resrceNm.equals(chkResrceNm)) {
//						
//						// 중복
//						resultDpcnYn = "Y";
//						break;
//						
//					}else {
//						
//						// 중복 아님
//						resultDpcnYn = "N";
//						
//					}
//					
//					
//				}
//				
//				
//				
//			}
//			
//			
//		}
		
		return resrceNmDpcnChkMap;
	}

	
	

	/**
	 * @Method명   : resrceRsvtDpcnChk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	@Override
	public String resrceRsvtDpcnChk(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dsListParam = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("ENFSN_NO", dmDtlParam.getValue("ENFSN_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RSVT_APLY_ENFSN_NO", dmDtlParam.getValue("RSVT_APLY_ENFSN_NO"));
		
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dsListParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dsListParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		mapParam.put("RSVT_SN", dsListParam.getValue("RSVT_SN"));
		mapParam.put("RSVT_BGNG_YMD", DateUtil.validChkDate(dsListParam.getValue("RSVT_BGNG_YMD")));
		mapParam.put("RSVT_END_YMD", DateUtil.validChkDate(dsListParam.getValue("RSVT_BGNG_YMD")));
		mapParam.put("RSVT_BGNG_HR", dsListParam.getValue("RSVT_BGNG_HR"));
		mapParam.put("RSVT_END_HR", dsListParam.getValue("RSVT_END_HR"));
		mapParam.put("DPCN_CHECK_START_TIME", dsListParam.getValue("DPCN_CHECK_START_TIME"));
		mapParam.put("RSVT_SE_CD", dsListParam.getValue("RSVT_SE_CD"));
		
		List<Map<String, Object>> dpcnList = new ArrayList<Map<String,Object>>();
		
		String str = dmDtlParam.getValue("SAVE_TYPE");
				
		if(str.equals("I")) {
			
			dpcnList = rsvtMngMapper.selectResrceRsvtDpcnInsertTypeCheckList(mapParam);
		}else if(str.equals("U")) {
			
			//mapParam.put("DPCN_CHECK_START_TIME", dsListParam.getValue("DPCN_CHECK_START_TIME"));
			dpcnList = rsvtMngMapper.selectResrceRsvtDpcnUpdateTypeCheckList(mapParam);
		}
		
		
		String resultDpcnYn = "";
		
		if(dpcnList.size() == 0) {
			resultDpcnYn = "N";
		}else if(dpcnList.size() > 0) {
			resultDpcnYn = "Y";
		}
			
		
		return resultDpcnYn;
	}

	/**
	 * @Method명   : saveResrceRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 : 자원예약 저장(등록, 수정, 취소(삭제))
	 */
	@Override
	public void saveResrceRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		
		Iterator<ParameterRow> insertedDsList = dsList.getInsertedRows();		
		Iterator<ParameterRow> updatedDsList  = dsList.getUpdatedRows();		
		Iterator<ParameterRow> deletedDsList  = dsList.getDeletedRows();	
		log.debug("saveResrceRsvtDtl > call");
		
		// INSERT
		while (insertedDsList.hasNext()) {
			
			log.debug("saveResrceRsvtDtl > insertedDsList");
			Map<String, String> mapIns = insertedDsList.next().toMap();
			
			mapIns.put("RSVT_BGNG_YMD", DateUtil.validChkDate(mapIns.get("RSVT_BGNG_YMD")));
			mapIns.put("RSVT_END_YMD", DateUtil.validChkDate(mapIns.get("RSVT_END_YMD")));
			
			//log.debug("insertedDsList INST_NO=[" + mapIns.get("INST_NO") + "]");
//			log.debug("insertedDsList UNT_TASKWK_SE_CD=[" + mapIns.get("UNT_TASKWK_SE_CD") + "]");
//			log.debug("insertedDsList USER_ID=[" + mapIns.get("USER_ID") + "]");
//			log.debug("insertedDsList RESRCE_CL_MNG_LCLAS_SE_CD=[" + mapIns.get("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
//			log.debug("insertedDsList RESRCE_NM=[" + mapIns.get("RESRCE_NM") + "]");
//			log.debug("insertedDsList USE_YN=[" + mapIns.get("USE_YN") + "]");
//			log.debug("insertedDsList SCHDL_RSVT_CNCTN_USE_YN=[" + mapIns.get("SCHDL_RSVT_CNCTN_USE_YN") + "]");
			
			
			// 자원예약 등록
			rsvtMngMapper.insertResrceRsvtDtl(mapIns);
		}
		
		
		// UPDATE
		while (updatedDsList.hasNext()) {	
			log.debug("saveResrceRsvtDtl > updateedDsList");
			Map<String, String> mapUpd = updatedDsList.next().toMap();	
			
			mapUpd.put("RSVT_BGNG_YMD", DateUtil.validChkDate(mapUpd.get("RSVT_BGNG_YMD")));
			mapUpd.put("RSVT_END_YMD", DateUtil.validChkDate(mapUpd.get("RSVT_END_YMD")));
			
			log.debug("updatedDsList 자원관리번호=[" + mapUpd.get("RESRCE_CL_MNG_ESNTAL_NO") + "]");
			log.debug("updatedDsList 예약일렬번호=[" + mapUpd.get("RSVT_SN") + "]");
			log.debug("updatedDsList 예약시작일=[" + mapUpd.get("RSVT_BGNG_YMD") + "]");
			log.debug("updatedDsList 예약종료일=[" + mapUpd.get("RSVT_END_YMD") + "]");
			log.debug("updatedDsList 예약시작시간=[" + mapUpd.get("RSVT_BGNG_HR") + "]");
			log.debug("updatedDsList 예약종료시간=[" + mapUpd.get("RSVT_END_HR") + "]");
			log.debug("updatedDsList 사용목적내용=[" + mapUpd.get("USE_PURPS_CN") + "]");
			log.debug("updatedDsList 비고내용=[" + mapUpd.get("RM_CN") + "]");
			log.debug("updatedDsList 예약신청일=[" + mapUpd.get("RSVT_APLY_YMD") + "]");
			log.debug("updatedDsList 예약상태구분코드=[" + mapUpd.get("RSVT_STTS_SE_CD") + "]");
			
			// update 경우 로그인 사용자 아이디 적용
			mapUpd.put("LAST_MDFR_ID",   dmDtlParam.getValue("USER_ID"));	
			
			rsvtMngMapper.updateResrceRsvtDtl(mapUpd);
			
		}

		
		// DELETE
		while (deletedDsList.hasNext()) {	
			log.debug("saveResrceRsvtDtl > deleteedDsList");
			Map<String, String> mapDel = deletedDsList.next().toMap();
			
//			log.debug("deletedDsList RESRCE_CL_MNG_ESNTAL_NO=[" + mapDel.get("RESRCE_CL_MNG_ESNTAL_NO") + "]");
//			log.debug("deletedDsList INST_NO=[" + mapDel.get("INST_NO") + "]");
//			log.debug("deletedDsList UNT_TASKWK_SE_CD=[" + mapDel.get("UNT_TASKWK_SE_CD") + "]");
//			log.debug("deletedDsList USER_ID=[" + mapDel.get("USER_ID") + "]");
//			log.debug("deletedDsList RESRCE_CL_MNG_LCLAS_SE_CD=[" + mapDel.get("RESRCE_CL_MNG_LCLAS_SE_CD") + "]");
//			log.debug("deletedDsList RESRCE_NM=[" + mapDel.get("RESRCE_NM") + "]");
//			log.debug("deletedDsList USE_YN=[" + mapDel.get("USE_YN") + "]");
//			log.debug("deletedDsList SCHDL_RSVT_CNCTN_USE_YN=[" + mapDel.get("SCHDL_RSVT_CNCTN_USE_YN") + "]");
			
			// update 경우 로그인 사용자 아이디 적용
			mapDel.put("LAST_MDFR_ID",   dmDtlParam.getValue("USER_ID"));	
			
			rsvtMngMapper.deleteResrceRsvtDtl(mapDel);
						
		}
		
	}

	/**
	 * @Method명   : selectDailyRsvtPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDailyRsvtPreconList(Map<String, Object> mapParam) throws Exception {
		
		// 일별 예약 현황 조회
		List<Map<String, Object>> rsvtPreconList = rsvtMngMapper.selectDailyRsvtPreconList(mapParam);
		
		for (Map<String, Object> map : rsvtPreconList) {
			
			//map.put("RSVCTM", scpDb.scpDecB64(map.get("RSVCTM").toString()));
			
			int startDate = Integer.parseInt(map.get("RSVT_BGNG_YMD").toString());
			int endDate = Integer.parseInt(map.get("RSVT_END_YMD").toString());
			
			if(startDate == endDate) {
				
				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4) );
				
			}else if(startDate < endDate) {
				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + DateUtil.formatDate(map.get("RSVT_END_YMD").toString(), "-") + "(" + map.get("END_DAYOFWEEK").toString() + ")" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4) );
			}

//			if(startDate == endDate) {
//				
//				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + DateUtil.formatTime(map.get("RSVT_BGNG_HR").toString(), ":") + "~" + DateUtil.formatTime(map.get("RSVT_END_HR").toString(), ":") );
//				
//			}else if(startDate < endDate) {
//				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + DateUtil.formatTime(map.get("RSVT_BGNG_HR").toString(), ":") + "~" + DateUtil.formatDate(map.get("RSVT_END_YMD").toString(), "-") + "(" + map.get("END_DAYOFWEEK").toString() + ")" + DateUtil.formatTime(map.get("RSVT_END_HR").toString(), ":") );
//			}
		}
		
		return rsvtPreconList;
	}

	/**
	 * @Method명   : selectWeeklyRsvtPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWeeklyRsvtPreconList(Map<String, Object> mapParam) throws Exception {
		
		int dayOfWeekNum = rsvtMngMapper.selectDayOfWeek(mapParam);
		mapParam.put("DAYOFWEEK", dayOfWeekNum);
		
		int searchDate = Integer.parseInt(mapParam.get("SEARCH_DATE").toString());
		int weeklyStartDate = 0;
		int weeklyEndDate = 0;
		
		int prevDay = 0;
		int nextDay = 0;
		
		if(dayOfWeekNum == 1) {
			//일요일
			prevDay = 0;
			nextDay = 6;
		}else if(dayOfWeekNum == 2) {
			//월요일
			prevDay = 1;
			nextDay = 5;
		}else if(dayOfWeekNum == 3) {
			//화요일
			prevDay = 2;
			nextDay = 4;
		}else if(dayOfWeekNum == 4) {
			//수요일
			prevDay = 3;
			nextDay = 3;
		}else if(dayOfWeekNum == 5) {
			//목요일
			prevDay = 4;
			nextDay = 2;
		}else if(dayOfWeekNum == 6) {
			//금요일
			prevDay = 5;
			nextDay = 1;
		}else if(dayOfWeekNum == 7) {
			//토요일
			prevDay = 6;
			nextDay = 0;
		}
		
		
		if(dayOfWeekNum == 1) {
			weeklyStartDate = (searchDate - nextDay);
			weeklyEndDate = (searchDate + prevDay);
		}else {
			weeklyStartDate = (searchDate - prevDay) + 1;
			weeklyEndDate = (searchDate + nextDay) + 1;
		}
		
		
		log.debug("weekly weeklyStartDate=[" + weeklyStartDate + "]");
		log.debug("weekly weeklyEndDate=[" + weeklyEndDate + "]");
		
		mapParam.put("WEEKLY_START_DATE", String.valueOf(weeklyStartDate));
		mapParam.put("WEEKLY_END_DATE", String.valueOf(weeklyEndDate));
		
		mapParam.put("PREV_DAY", String.valueOf(prevDay));
		mapParam.put("NEXT_DAY", String.valueOf(nextDay));
		
		
		
		List<Map<String, Object>> weeklyList = rsvtMngMapper.selectWeeklyRsvtPreconList(mapParam);
				
		for (Map<String, Object> map : weeklyList) {
			
			//map.put("RSVCTM", scpDb.scpDecB64(map.get("RSVCTM").toString()));
			
			
			int startDate = Integer.parseInt(map.get("RSVT_BGNG_YMD").toString());
			int endDate = Integer.parseInt(map.get("RSVT_END_YMD").toString());
			
			if(startDate == endDate) {
				
				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4) );
				
			}else if(startDate < endDate) {
				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + DateUtil.formatDate(map.get("RSVT_END_YMD").toString(), "-") + "(" + map.get("END_DAYOFWEEK").toString() + ")" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4) );
			}
			
			
		}
		
		return weeklyList;
	}

	/**
	 * @Method명   : selectRsvtPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 : 선택한 자원별 예약 현황
	 */
	@Override
	public List<Map<String, Object>> selectRsvtPreconList(Map<String, Object> mapParam) throws Exception {

		List<Map<String, Object>> selectRsvtPreconList = rsvtMngMapper.selectRsvtPreconList(mapParam);
		
		for (Map<String, Object> map : selectRsvtPreconList) {
			
			//map.replace("RSVCTM", Masking.nameMasking(scpDb.scpDecB64(map.get("RSVCTM").toString())));
			
			int startDate = Integer.parseInt(map.get("RSVT_BGNG_YMD").toString());
			int endDate = Integer.parseInt(map.get("RSVT_END_YMD").toString());
			
			log.debug("startDate :::::::::::::::::" + startDate);
			log.debug("endDate :::::::::::::::::" + endDate);
			
			if(startDate == endDate) {
				
				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4) );
				
			}else if(startDate < endDate) {
				map.put("RSVT_YMD" , DateUtil.formatDate(map.get("RSVT_BGNG_YMD").toString(), "-") + "(" + map.get("START_DAYOFWEEK").toString() + ")" + map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + DateUtil.formatDate(map.get("RSVT_END_YMD").toString(), "-") + "(" + map.get("END_DAYOFWEEK").toString() + ")" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4) );
			}
		}
		log.debug("selectRsvtPreconList :::::::::::::::::" + selectRsvtPreconList.toString());
		
		return selectRsvtPreconList;
		
	}

	/**
	 * @Method명   : selectRsvtAltmntPreconList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 : 선택한 자원별 예약 현황 > 예약자별 예약 시간 디테일 그리드용
	 */
	@Override
	public List<Map<String, Object>> selectRsvtAltmntPreconList(Map<String, Object> mapParam) throws Exception {

		// 선택한 자원별 예약 현황
		List<Map<String, Object>> selectRsvtPreconList = rsvtMngMapper.selectRsvtPreconList(mapParam);
		
		log.debug("selectRsvtPreconList impl :::::::::::: " + selectRsvtPreconList.size());
		
		// 자원예약배정현황 데이터 리스트 생성
		List<Map<String, Object>> rsvtAltmntPreconList = new ArrayList<Map<String,Object>>();
		
		for(int i = 0; i < selectRsvtPreconList.size(); i ++) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			// 2022.11.04 이태호_RSVT_SN 조건 추가
//			if(selectRsvtPreconList.size() == 0) {
//				map.put("RESRCE_CL_MNG_ESNTAL_NO", mapParam.get("RESRCE_CL_MNG_ESNTAL_NO"));
//				map.put("RESRCE_NM", mapParam.get("RESRCE_NM"));
//			}else {
				map.put("RESRCE_CL_MNG_ESNTAL_NO", selectRsvtPreconList.get(i).get("RESRCE_CL_MNG_ESNTAL_NO"));
				map.put("RESRCE_NM", selectRsvtPreconList.get(i).get("RESRCE_NM"));
				map.put("RSVT_SN", selectRsvtPreconList.get(i).get("RSVT_SN"));
				// 2022.11.04 이태호_예약자명 추가
				map.put("RSVCTM", selectRsvtPreconList.get(i).get("RSVCTM").toString());
				map.put("USER_ID", selectRsvtPreconList.get(i).get("USER_ID"));
//			}
			
			
			for(int h = 7; h <=21; h ++) {
//								
				String sh = "";
				
				if(h < 10) {
//									map.put("0" + h + "00", "0" + h + "00");
					map.put("C" + "0" + h + "00", "C" + "0" + h + "00");
					sh = "C" + "0" + Integer.toString(h);
				}else {
//									map.put(h + "00", h + "00");
					map.put("C" + h + "00", "C" + h + "00");
					sh = "C" + Integer.toString(h);
				}
				
				
				for(int m = 0; m < 6; m ++) {
//									minute[m] = m * 10;
					
					if(m == 0) {
//										map.put(sh + "0" + m * 10, sh + "0" + m * 10);
						map.put(sh + "0" + m * 10, sh + "0" + m * 10);
					} else {
//										map.put(sh + m * 10, sh + m * 10);
						
						if(h < 21) {
							map.put(sh + m * 10, sh + m * 10); 
						}
						
					}
					
				}
				
				
				
			}
			
			rsvtAltmntPreconList.add(map);
			
		}
		
		
		//log.debug("11111 impl :::::::::::: " + selectRsvtPreconList.size());	

		if(selectRsvtPreconList.size() > 0) {
			for(int c = 0; c < selectRsvtPreconList.size(); c ++) {
						
				String c1 = selectRsvtPreconList.get(c).get("RESRCE_CL_MNG_ESNTAL_NO").toString();
				
				//log.debug("22222 impl :::::::::::: " + rsvtAltmntPreconList.size());
				
				for(int r = 0; r < rsvtAltmntPreconList.size(); r ++) {
					
					String c2 = rsvtAltmntPreconList.get(r).get("RESRCE_CL_MNG_ESNTAL_NO").toString();
					
					//log.debug("33333 impl :::::::::::: " + c1);
					//log.debug("44444 impl :::::::::::: " + c2);
					
					if (c1.equals(c2) ) {
						
						rsvtAltmntPreconList.get(r).put("C" + selectRsvtPreconList.get(c).get("RSVT_BGNG_HR").toString(), "S" + selectRsvtPreconList.get(c).get("RSVT_BGNG_HR").toString());
						rsvtAltmntPreconList.get(r).put("C" + selectRsvtPreconList.get(c).get("RSVT_END_HR").toString(), "E" + selectRsvtPreconList.get(c).get("RSVT_END_HR").toString());
												
					}
					
				}
				
			}
		}
			
		
		//log.debug("rsvtAltmntPreconList impl :::::::::::: " + rsvtAltmntPreconList.toString());
		return rsvtAltmntPreconList;
		
	}

	/**
	 * @Method명   : selectResrceRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectResrceRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("INST_NO", dmDtlParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDtlParam.getValue("USER_ID"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDtlParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RESRCE_CL_MNG_ESNTAL_NO", dmDtlParam.getValue("RESRCE_CL_MNG_ESNTAL_NO"));
		mapParam.put("RSVT_SN", dmDtlParam.getValue("RSVT_SN"));
		
		return rsvtMngMapper.selectResrceRsvtDtl(mapParam);
		
	}

	/**
	 * @Method명   : selectTrprPtcptnPsbltyYlist
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTrprPtcptnPsbltyYlist(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("INST_NO", dmDetailParam.getValue("INST_NO"));
		mapParam.put("USER_ID", dmDetailParam.getValue("USER_ID"));
		mapParam.put("UNT_TASKWK_SE_CD", dmDetailParam.getValue("UNT_TASKWK_SE_CD"));
		mapParam.put("RESRCE_CL_MNG_LCLAS_SE_CD", dmDetailParam.getValue("RESRCE_CL_MNG_LCLAS_SE_CD"));
		
		return rsvtMngMapper.selectTrprPtcptnPsbltyYlist(mapParam);
	}
	
	/**
	 * @Method명   : selectFcltyThngList
	 * @param stiring
	 * @return 
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 03. 
	 * @Method설명 : 시설 및 물품목록조회
	 */
	@Override
	public List<Map<String, Object>> selectFcltyThngList(String codeId,String userId,String instNo) throws Exception {
		if (codeId == null) {
			return null;
		}
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("INST_NO", instNo);
		mapParam.put("USER_ID", userId);
		
		log.debug("codeId impl:::::::::::" + codeId);
		log.debug("userId impl:::::::::::" + userId);
		log.debug("instNo impl:::::::::::" + instNo);
		
		return rsvtMngMapper.selectFcltyThngList(mapParam);
	}

	/**
	 * @Method명   : saveSchdlRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 :
	 */
	@Override
	public void saveSchdlRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		//로그인한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId();						//아이디
		String sUserNm = loginVO.getUserName();					//이름
		String sWrdTelno = loginVO.getWrdTelno();				//기관전화번호
		String sEnfsnNo = loginVO.getEnfsnNo();					//종사자번호
		Integer sInstNo = loginVO.getInstNo();					//기관번호 
		String sRprsTelno = "";				//기관대표전화번호
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");								// 일정예약정보
		List<Map<String, String>> listRow = dsList.getAllRowList();
		
		ParameterGroup dsTrprInfo = dataRequest.getParameterGroup("dsTrprInfo");						// 대상자정보
		List<Map<String, String>> trprInfoRowList = dsTrprInfo.getAllRowList();
		
		ParameterGroup dsPicList = dataRequest.getParameterGroup("dsPicList");							// 담당자정보
		List<Map<String, String>> picRowList = dsPicList.getAllRowList();
		
		List<Map<String, String>> insertTrprRow = new ArrayList<Map<String,String>>();
				
		//insert할 문자발송대상 대상자
		for (int i = 0; i < trprInfoRowList.size(); i++) {
			insertTrprRow.add(trprInfoRowList.get(i));
		}
		
		List<Map<String, String>> insertPicRow = new ArrayList<Map<String,String>>();
				
		//insert할 문자발송대상 담당자
		for (int i = 0; i < picRowList.size(); i++) {
			insertPicRow.add(picRowList.get(i));
		}
		
		
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");						// 사용자 기본 및 기타 정보
		Map<String, String> dmDtlParamMap = dmDtlParam.getSingleValueMap();
		sRprsTelno = rsvtMngMapper.selectRprsTelno(dmDtlParamMap);
				
		String saveType = dmDtlParam.getSingleValueMap().get("SAVE_TYPE").toString();	//저장 타입(INSERT / UPDATE / DELETE)
		
		String rsvtChrctrSndngYn = listRow.get(0).get("RSVT_CHRCTR_SNDNG_YN").toString();	// 문자발송여부
		
		String sendMsg = listRow.get(0).get("RSVT_CHRCTR_SNDNG_CN").toString();				// 문자발송 내용
		
		String resrceClMngEsntalNo = listRow.get(0).get("RESRCE_CL_MNG_ESNTAL_NO").toString();	// 자원분류관리고유번호
		
		
		Map<String, String> mapRsvtInfo = listRow.get(0);
		
		// ### 02. 예약정보 insert
		
		if(rsvtChrctrSndngYn.equals("N")) {
			
			mapRsvtInfo.put("RSVT_CHRCTR_SNDNG_YMD", "");					// 예약문자발송일
			mapRsvtInfo.put("RSVT_CHRCTR_SNDNG_HR", "");					// 예약문자발송시간
		}
		
		mapRsvtInfo.put("FRST_RGTR_ID", sUserId);										// 최초등록자아이디
		//map.put("FRST_REG_DT", dsList.getValue("FRST_REG_DT"));									// 최초등록일시
		mapRsvtInfo.put("LAST_MDFR_ID", sUserId);										// 최종수정자아이디
		//map.put("LAST_MDFCN_DT", dsList.getValue("LAST_MDFCN_DT"));								// 최종수정일시
		
		
		// 일정예약 등록
		rsvtMngMapper.insertSchdlRsvtDtl(mapRsvtInfo);
		
		
		// 일정예약 등록 채번 예약일련번호
		String rsvtSn = String.valueOf(mapRsvtInfo.get("RSVT_SN"));
		
		// ### 02. 문자발송
		for (Map<String, String> mapTrpr : insertTrprRow) {
			log.debug("### 대상자번호 = [" + mapTrpr.get("TRPR_INFO_NO") + "]" );
			log.debug("### 수신대상자명 = [" + mapTrpr.get("TRPR_NM") + "]" );
			log.debug("### 수신대상자휴대폰번호 = [" + mapTrpr.get("MBL_TELNO").replace("-", "") + "]" );
			
			mapTrpr.put("RCPTN_MBL_TELNO", mapTrpr.get("MBL_TELNO").replace("-", ""));
			//mapTrpr.put("RCPTN_MBL_TELNO", "01100000000");	// test 용
			log.debug("### 수신대상자휴대폰번호 RCPTN_MBL_TELNO = [" + mapTrpr.get("RCPTN_MBL_TELNO") + "]" );
			
			mapTrpr.put("RSVT_CHRCTR_CN", sendMsg);
			
			rsvtMng2Mapper.insertMmsContentsInfo(mapTrpr);
			
			mapTrpr.put("CONT_SEQ", mapTrpr.get("CONT_SEQ"));							//MMS 컨텐츠 키
			mapTrpr.put("FRST_RGTR_ID", sUserId);
			mapTrpr.put("LAST_MDFR_ID", sUserId);
			//mapTrpr.put("DSPTCH_TRPR_NM_ENCPT", scpDb.scpEncB64(sUserNm));			//발신대상자명암호화
			mapTrpr.put("DSPTCH_TRPR_NM_ENCPT", sUserNm);			//발신대상자명암호화
			//mapTrpr.put("CALL_FROM", scpDb.scpDecB64(sRprsTelno).replace("-", ""));					//발신휴대전화번호
			mapTrpr.put("CALL_FROM", sRprsTelno);					//발신휴대전화번호
			//mapTrpr.put("DSPTCH_MBL_TELNO_ENCPT", scpDb.scpEncB64(sRprsTelno));						//발신휴대전화번호암호화
			mapTrpr.put("DSPTCH_MBL_TELNO_ENCPT", sRprsTelno);						//발신휴대전화번호암호화
			mapTrpr.put("PIC_NO", sEnfsnNo);														//담당자번호
			mapTrpr.put("TRNSMI_INST_NO", String.valueOf(sInstNo));									//송신기관번호
			mapTrpr.put("RSVT_CHRCTR_SNDNG_YMD", mapRsvtInfo.get("RSVT_CHRCTR_SNDNG_YMD"));			//예약날짜
			mapTrpr.put("RSVT_CHRCTR_SNDNG_HR", mapRsvtInfo.get("RSVT_CHRCTR_SNDNG_HR"));			//예약시간
			
			rsvtMng2Mapper.insertMsgData(mapTrpr);
			
			mapTrpr.put("MSG_SEQ", mapTrpr.get("MSG_SEQ"));								//메세지 고유번호
			mapTrpr.put("RESRCE_CL_MNG_ESNTAL_NO", resrceClMngEsntalNo);	
			mapTrpr.put("RSVT_SN", rsvtSn);	
			
			
			mapTrpr.put("FRST_RGTR_ID", sUserId);										// 최초등록자아이디
			mapTrpr.put("LAST_MDFR_ID", sUserId);										// 최종수정자아이디
			
			// ### 03. 예약대상자정보 insert
			rsvtMngMapper.insertSchdlRsvtTrpr(mapTrpr);
		}
		
		  
		for (Map<String, String> mapPic : insertPicRow) {
			//log.debug("### 담당자번호 = [" + mapPic.get("PIC_NO") + "]" );
			//log.debug("### 주담당자여부 = [" + mapPic.get("PCHPRS_YN") + "]" );
			//log.debug("### 수신담당자명 = [" + mapPic.get("PIC_NM") + "]" );
			//log.debug("### 수신담당자휴대폰번호 = [" + mapPic.get("PIC_MBL_TELNO").replace("-", "") + "]" );

			mapPic.put("RCPTN_MBL_TELNO", mapPic.get("PIC_MBL_TELNO").replace("-", ""));
			//mapPic.put("RCPTN_MBL_TELNO", "01100000000");	// test 용
			//log.debug("### 수신담당자휴대폰번호 RCPTN_MBL_TELNO = [" + mapPic.get("RCPTN_MBL_TELNO") + "]" );
			
			mapPic.put("RSVT_CHRCTR_CN", sendMsg);
			
			rsvtMng2Mapper.insertMmsContentsInfo(mapPic);
			
			mapPic.put("CONT_SEQ", mapPic.get("CONT_SEQ"));							//MMS 컨텐츠 키
			mapPic.put("FRST_RGTR_ID", sUserId);
			mapPic.put("LAST_MDFR_ID", sUserId);
			//mapPic.put("DSPTCH_TRPR_NM_ENCPT", scpDb.scpEncB64(sUserNm));			//발신대상자명암호화
			mapPic.put("DSPTCH_TRPR_NM_ENCPT", sUserNm);			//발신대상자명암호화
			//mapPic.put("CALL_FROM", scpDb.scpDecB64(sRprsTelno).replace("-", ""));					//발신휴대전화번호
			mapPic.put("CALL_FROM", sRprsTelno);					//발신휴대전화번호
			//mapPic.put("DSPTCH_MBL_TELNO_ENCPT", scpDb.scpEncB64(sRprsTelno));		//발신휴대전화번호암호화
			mapPic.put("DSPTCH_MBL_TELNO_ENCPT", sRprsTelno);		//발신휴대전화번호암호화
			mapPic.put("PIC_NO", sEnfsnNo);										//담당자번호
			mapPic.put("TRNSMI_INST_NO", String.valueOf(sInstNo));					//송신기관번호
			mapPic.put("RSVT_CHRCTR_SNDNG_YMD", mapRsvtInfo.get("RSVT_CHRCTR_SNDNG_YMD"));			//예약날짜
			mapPic.put("RSVT_CHRCTR_SNDNG_HR", mapRsvtInfo.get("RSVT_CHRCTR_SNDNG_HR"));			//예약시간
			
			rsvtMng2Mapper.insertMsgData(mapPic);
			
			mapPic.put("MSG_SEQ", mapPic.get("MSG_SEQ"));								//메세지 고유번호
			mapPic.put("RESRCE_CL_MNG_ESNTAL_NO", resrceClMngEsntalNo);	
			mapPic.put("RSVT_SN", rsvtSn);	
			
			
			mapPic.put("FRST_RGTR_ID", sUserId);										// 최초등록자아이디
			mapPic.put("LAST_MDFR_ID", sUserId);										// 최종수정자아이디
			
			// ### 04. 담당자정보 insert
			rsvtMngMapper.insertSchdlRsvtPic(mapPic);
		}
	}


	/**
	 * @Method명   : selectSchdlRsvtList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSchdlRsvtList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearchParam");
		Map<String, String> mapParam = dmSearchParam.getSingleValueMap();
		
		//String searchKeyStr = mapParam.get("SEARCH_KEY").toString();
		//if(searchKeyStr.equals("01") || searchKeyStr.equals("02") || searchKeyStr.equals("04")) {
			//mapParam.replace("SEARCH_DATA", scpDb.scpEncB64(mapParam.get("SEARCH_DATA").toString()));
		//}
		
		
		List<Map<String, Object>> selectRsvtPreconList = rsvtMngMapper.selectSchdlRsvtList(mapParam);
		
		
		for (Map<String, Object> map : selectRsvtPreconList) {
			
			//if (map.containsKey("PCHPRS_Y_PIC_NM")) {
				//map.replace("PCHPRS_Y_PIC_NM", Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map.get("PCHPRS_Y_PIC_NM")))));
			//}
			
			//if (map.containsKey("USER_NM")) {
				//map.replace("USER_NM", Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map.get("USER_NM")))));
			//}
			
			//if (map.containsKey("TRPR_NM_ENCPT")) {
				//map.put("TRPR_NM", Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map.get("TRPR_NM_ENCPT")))));
			//}
			
//			map.put("PCHPRS_Y_PIC_NM", scpDb.scpDecB64(map.get("PCHPRS_Y_PIC_NM").toString()));
//			map.put("USER_NM", scpDb.scpDecB64(map.get("USER_NM").toString()));
//			map.put("TRPR_NM", scpDb.scpDecB64(map.get("TRPR_NM_ENCPT").toString()));
			
			int cnt = NumberUtils.toInt(map.get("PCHPRS_N_PIC_ID_CNT").toString());
			
			if (cnt == 0) {
				map.replace("PCHPRS_N_PIC_ID_CNT", "-");
				
			} else if(cnt > 0) {
				String strCnt = String.format("%s 외 %s명", map.get("PCHPRS_Y_PIC_NM").toString(), cnt);
				map.replace("PCHPRS_N_PIC_ID_CNT", strCnt);
			}
		}

		return selectRsvtPreconList;
	}

	
	/**
	 * @Method명   : selectDailyPopUpList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 4. 26. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDailyPopUpList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = dmSearchParam.getSingleValueMap();
		
		List<Map<String, Object>> selectRsvtPreconList = rsvtMngMapper.selectDailyPopUpList(mapParam);
		
		
		for (Map<String, Object> map : selectRsvtPreconList) {
			
			String sTime = map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4);
			
			map.put("RSVT_TIME", sTime);
			
			//if (map.containsKey("PCHPRS_Y_PIC_NM")) {
				//map.replace("PCHPRS_Y_PIC_NM", Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map.get("PCHPRS_Y_PIC_NM")))));
			//}
			
			//if (map.containsKey("USER_NM")) {
				//map.replace("USER_NM", Masking.nameMasking(scpDb.scpDecB64(StringUtil.nullConvert(map.get("USER_NM")))));
			//}
			
			int cnt = NumberUtils.toInt(map.get("PCHPRS_N_PIC_ID_CNT").toString());
			
			if (cnt == 0) {
				map.replace("PCHPRS_N_PIC_ID_CNT", "-");
				
			} else if(cnt > 0) {
				String strCnt = String.format("%s 외 %s명", map.get("PCHPRS_Y_PIC_NM").toString(), cnt);
				map.replace("PCHPRS_N_PIC_ID_CNT", strCnt);
			}
		}

		return selectRsvtPreconList;
	}
	
	/**
	 * @Method명   : selectSchdlRsvtTrprDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSchdlRsvtTrprDtl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = dmDtlParam.getSingleValueMap();
		
		
		List<Map<String, Object>> selectSchdlRsvtTrprDtlList = rsvtMngMapper.selectSchdlRsvtTrprDtlList(mapParam);
		
		/*
		for (Map<String, Object> map : selectSchdlRsvtTrprDtlList) {
			
			if (map.containsKey("EML_ADDR")) {
				map.replace("EML_ADDR", scpDb.scpDecB64(StringUtil.nullConvert(map.get("EML_ADDR"))));
			}
			
			if (map.containsKey("MSNGR_ID")) {
				map.replace("MSNGR_ID", scpDb.scpDecB64(StringUtil.nullConvert(map.get("MSNGR_ID"))));
			}
			
			if (map.containsKey("TRPR_NM")) {
				map.replace("TRPR_NM", scpDb.scpDecB64(StringUtil.nullConvert(map.get("TRPR_NM"))));
			}
			
			if (map.containsKey("MBL_TELNO")) {
				map.replace("MBL_TELNO", scpDb.scpDecB64(StringUtil.nullConvert(map.get("MBL_TELNO"))));
			}
		}
		*/

		return selectSchdlRsvtTrprDtlList;
	}

	/**
	 * @Method명   : selectSchdlRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSchdlRsvtDtl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = dmDtlParam.getSingleValueMap();
		
		return rsvtMngMapper.selectSchdlRsvtDtl(mapParam);
	}

	/**
	 * @Method명   : selectSchdlRsvtPicDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSchdlRsvtPicDtl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = dmDtlParam.getSingleValueMap();
		
		
		List<Map<String, Object>> selectSchdlRsvtPicDtlList = rsvtMngMapper.selectSchdlRsvtPicDtl(mapParam);
		
		/*
		for (Map<String, Object> map : selectSchdlRsvtPicDtlList) {
			if (map.containsKey("PIC_NM")) {
				map.replace("PIC_NM", scpDb.scpDecB64(StringUtil.nullConvert(map.get("PIC_NM"))));
			}
		}
		*/

		return selectSchdlRsvtPicDtlList;
		
	}

	/**
	 * @Method명   : cancleSchdlRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 :
	 */
	@Override
	public void cancleSchdlRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		//로그인한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId();						//아이디
		String sUserNm = loginVO.getUserName();					//이름
		String sWrdTelno = loginVO.getWrdTelno();				//기관전화번호
		String sEnfsnNo = loginVO.getEnfsnNo();					//종사자번호
		Integer sInstNo = loginVO.getInstNo();					//기관번호 
		String sRprsTelno = "";				//기관대표전화번호
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");								// 일정예약정보
		List<Map<String, String>> listRow = dsList.getAllRowList();
		
		ParameterGroup dsTrprInfo = dataRequest.getParameterGroup("dsTrprInfo");						// 대상자정보
		List<Map<String, String>> trprInfoRowList = dsTrprInfo.getAllRowList();
		
		ParameterGroup dsPicList = dataRequest.getParameterGroup("dsPicList");							// 담당자정보
		List<Map<String, String>> picRowList = dsPicList.getAllRowList();
		
//				ParameterGroup dsSmsSndngTrprList = dataRequest.getParameterGroup("dsSmsSndngTrprList");		// 문자발송대상 대상자 정보
//				List<Map<String, String>> smsSndngTrprRowList = dsSmsSndngTrprList.getAllRowList();
		
		List<Map<String, String>> insertTrprRow = new ArrayList<Map<String,String>>();
				
		//insert할 문자발송대상 대상자
		for (int i = 0; i < trprInfoRowList.size(); i++) {
			insertTrprRow.add(trprInfoRowList.get(i));
		}
		
		
//				ParameterGroup dsSmsSndngPicList = dataRequest.getParameterGroup("dsSmsSndngPicList");		// 문자발송대상 대상자 정보
//				List<Map<String, String>> smsSndngPicRowList = dsSmsSndngPicList.getAllRowList();
		
		List<Map<String, String>> insertPicRow = new ArrayList<Map<String,String>>();
				
		//insert할 문자발송대상 담당자
		for (int i = 0; i < picRowList.size(); i++) {
			insertPicRow.add(picRowList.get(i));
		}
		
		
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");						// 사용자 기본 및 기타 정보
		Map<String, String> dmDtlParamMap = dmDtlParam.getSingleValueMap();
		sRprsTelno = rsvtMngMapper.selectRprsTelno(dmDtlParamMap);
		
		//String saveType = dmDtlParam.getSingleValueMap().get("SAVE_TYPE").toString();	//저장 타입(INSERT / UPDATE / DELETE)
		
		String rsvtChrctrSndngYn = listRow.get(0).get("RSVT_CHRCTR_SNDNG_YN").toString();	// 문자발송여부
		
		//String sendMsg = listRow.get(0).get("RSVT_CHRCTR_SNDNG_CN").toString();				// 문자발송 내용
		
		String resrceClMngEsntalNo = listRow.get(0).get("RESRCE_CL_MNG_ESNTAL_NO").toString();	// 자원분류관리고유번호
		
		
		Map<String, String> mapRsvtInfo = listRow.get(0);
		
		
		// ### 기존 예약건 취소 처리
		mapRsvtInfo.put("RSVT_STTS_SE_CD", "02");					// 예약상태 취소로 변경
		
		mapRsvtInfo.put("LAST_MDFR_ID", sUserId);					// 최종수정자아이디
		
		// 일정예약 취소 상태로 업데이트
		rsvtMngMapper.updateCancleSchdlRsvtDtl(mapRsvtInfo);
		
		
		String cancleSendMsg = "";
		cancleSendMsg = "[예약취소] " 
		+ mapRsvtInfo.get("RSVT_BGNG_YMD").toString() 
		+ mapRsvtInfo.get("RSVT_BGNG_HR").toString() + "~" + mapRsvtInfo.get("RSVT_BGNG_HR").toString() + " 예약된 "
		+ mapRsvtInfo.get("RESRCE_NM").toString() + "예약이 취소되었습니다.";
		
		// ### 취소 문자발송
		for (Map<String, String> mapTrpr : insertTrprRow) {
			log.debug("### 대상자번호 = [" + mapTrpr.get("TRPR_INFO_NO") + "]" );
			log.debug("### 수신대상자명 = [" + mapTrpr.get("TRPR_NM") + "]" );
			log.debug("### 수신대상자휴대폰번호 = [" + mapTrpr.get("MBL_TELNO").replace("-", "") + "]" );
			
			mapTrpr.put("RCPTN_MBL_TELNO", mapTrpr.get("MBL_TELNO").replace("-", ""));
			//mapTrpr.put("RCPTN_MBL_TELNO", "01100000000");	// test 용
			log.debug("### 수신대상자휴대폰번호 RCPTN_MBL_TELNO = [" + mapTrpr.get("RCPTN_MBL_TELNO") + "]" );
			
			mapTrpr.put("RSVT_CHRCTR_CN", cancleSendMsg);
			
			rsvtMngMapper.insertMmsContentsInfo(mapTrpr);
			
			mapTrpr.put("CONT_SEQ", mapTrpr.get("CONT_SEQ"));							//MMS 컨텐츠 키
			mapTrpr.put("FRST_RGTR_ID", sUserId);
			mapTrpr.put("LAST_MDFR_ID", sUserId);
			//mapTrpr.put("DSPTCH_TRPR_NM_ENCPT", scpDb.scpEncB64(sUserNm));			//발신대상자명암호화
			mapTrpr.put("DSPTCH_TRPR_NM_ENCPT", sUserNm);			//발신대상자명암호화
			//mapTrpr.put("CALL_FROM", scpDb.scpDecB64(sRprsTelno).replace("-", ""));					//발신휴대전화번호
			mapTrpr.put("CALL_FROM", sRprsTelno);					//발신휴대전화번호
			//mapTrpr.put("DSPTCH_MBL_TELNO_ENCPT", scpDb.scpEncB64(sRprsTelno));		//발신휴대전화번호암호화
			mapTrpr.put("DSPTCH_MBL_TELNO_ENCPT", sRprsTelno);		//발신휴대전화번호암호화
			mapTrpr.put("PIC_NO", sEnfsnNo);										//담당자번호
			mapTrpr.put("TRNSMI_INST_NO", String.valueOf(sInstNo));					//송신기관번호
			
			rsvtMng2Mapper.insertMsgData(mapTrpr);
			
			mapTrpr.put("MSG_SEQ", mapTrpr.get("MSG_SEQ"));								//메세지 고유번호
			//mapTrpr.put("RESRCE_CL_MNG_ESNTAL_NO", resrceClMngEsntalNo);	
			//mapTrpr.put("RSVT_SN", rsvtSn);	
			
			
			//mapTrpr.put("FRST_RGTR_ID", sUserId);										// 최초등록자아이디
			mapTrpr.put("LAST_MDFR_ID", sUserId);										// 최종수정자아이디
			
			// ### 03. 예약대상자정보 insert
			rsvtMngMapper.updateSchdlRsvtTrpr(mapTrpr);
			
		}
		
		 
		for (Map<String, String> mapPic : insertPicRow) {
			//log.debug("### 담당자번호 = [" + mapPic.get("PIC_NO") + "]" );
			//log.debug("### 주담당자여부 = [" + mapPic.get("PCHPRS_YN") + "]" );
			//log.debug("### 수신담당자명 = [" + mapPic.get("PIC_NM") + "]" );
			//log.debug("### 수신담당자휴대폰번호 = [" + mapPic.get("PIC_MBL_TELNO").replace("-", "") + "]" );

			mapPic.put("RCPTN_MBL_TELNO", mapPic.get("PIC_MBL_TELNO").replace("-", ""));
			//mapPic.put("RCPTN_MBL_TELNO", "01100000000");	// test 용
			//log.debug("### 수신담당자휴대폰번호 RCPTN_MBL_TELNO = [" + mapPic.get("RCPTN_MBL_TELNO") + "]" );
			
			mapPic.put("RSVT_CHRCTR_CN", cancleSendMsg);
			
			rsvtMngMapper.insertMmsContentsInfo(mapPic);
			
			mapPic.put("CONT_SEQ", mapPic.get("CONT_SEQ"));							//MMS 컨텐츠 키
			mapPic.put("FRST_RGTR_ID", sUserId);
			mapPic.put("LAST_MDFR_ID", sUserId);
			//mapPic.put("DSPTCH_TRPR_NM_ENCPT", scpDb.scpEncB64(sUserNm));			//발신대상자명암호화
			mapPic.put("DSPTCH_TRPR_NM_ENCPT", sUserNm);			//발신대상자명암호화
			//mapPic.put("CALL_FROM", scpDb.scpDecB64(sRprsTelno).replace("-", ""));					//발신휴대전화번호
			mapPic.put("CALL_FROM", sRprsTelno);					//발신휴대전화번호
			//mapPic.put("DSPTCH_MBL_TELNO_ENCPT", scpDb.scpEncB64(sRprsTelno));		//발신휴대전화번호암호화
			mapPic.put("DSPTCH_MBL_TELNO_ENCPT", sRprsTelno);		//발신휴대전화번호암호화
			mapPic.put("PIC_NO", sEnfsnNo);										//담당자번호
			mapPic.put("TRNSMI_INST_NO", String.valueOf(sInstNo));					//송신기관번호
			
			rsvtMng2Mapper.insertMsgData(mapPic);
			
			mapPic.put("MSG_SEQ", mapPic.get("MSG_SEQ"));								//메세지 고유번호
			//mapPic.put("RESRCE_CL_MNG_ESNTAL_NO", resrceClMngEsntalNo);	
			//mapPic.put("RSVT_SN", rsvtSn);	
			
			
			//mapPic.put("FRST_RGTR_ID", sUserId);										// 최초등록자아이디
			mapPic.put("LAST_MDFR_ID", sUserId);										// 최종수정자아이디
			
			// ### 04. 담당자정보 insert
			rsvtMngMapper.updateSchdlRsvtPic(mapPic);
		}
	}

	/**
	 * @Method명   : selectDailyList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDailyList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> mapParam = dmDtlParam.getSingleValueMap();
		
		
		List<Map<String, Object>> selectDailyList = rsvtMngMapper.selectDailyList(mapParam);
		
		
		for (Map<String, Object> map : selectDailyList) {
			
			
			String sTime = map.get("RSVT_BGNG_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_BGNG_HR").toString().substring(2, 4) + "~" + map.get("RSVT_END_HR").toString().substring(0, 2) +  ":" + map.get("RSVT_END_HR").toString().substring(2, 4);
			
			map.put("RSVT_TIME", sTime);
			
			
			//if (map.containsKey("PCHPRS_Y_PIC_NM")) {
				//map.replace("PCHPRS_Y_PIC_NM", scpDb.scpDecB64(StringUtil.nullConvert(map.get("PCHPRS_Y_PIC_NM"))));
			//}
			
			//if (map.containsKey("USER_NM")) {
				//map.replace("USER_NM", scpDb.scpDecB64(StringUtil.nullConvert(map.get("USER_NM"))));
			//}
			
			//if (map.containsKey("TRPR_NM_ENCPT")) {
				//map.put("TRPR_NM", scpDb.scpDecB64(StringUtil.nullConvert(map.get("TRPR_NM_ENCPT"))));
			//}
			
//			map.put("PCHPRS_Y_PIC_NM", scpDb.scpDecB64(map.get("PCHPRS_Y_PIC_NM").toString()));
//			map.put("USER_NM", scpDb.scpDecB64(map.get("USER_NM").toString()));
//			map.put("TRPR_NM", scpDb.scpDecB64(map.get("TRPR_NM_ENCPT").toString()));
			
			int cnt = NumberUtils.toInt(map.get("PCHPRS_N_PIC_ID_CNT").toString());
			
			if (cnt == 0) {
				map.replace("PCHPRS_N_PIC_ID_CNT", "-");
				
			} else if(cnt > 0) {
				String strCnt = String.format("%s 외 %s명", map.get("PCHPRS_Y_PIC_NM").toString(), cnt);
				map.replace("PCHPRS_N_PIC_ID_CNT", strCnt);
			}
		}

		return selectDailyList;
	}

	/**
	 * @Method명   : selectedMonthsRsvtCnt
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectedMonthsRsvtCnt(Map<String, Object> mapParam) throws Exception {
		return rsvtMngMapper.selectedMonthsRsvtCnt(mapParam);
	}


}
