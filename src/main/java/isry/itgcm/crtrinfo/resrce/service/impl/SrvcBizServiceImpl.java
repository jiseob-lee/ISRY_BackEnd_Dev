/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.service.impl;

import java.util.ArrayList;
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

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.crtrinfo.resrce.mapper.SrvcBizMapper;
import isry.itgcm.crtrinfo.resrce.service.SrvcBizService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.redis.service.RedisService;

/**
* @Class Name  : SrvcBizServiceImpl.java
* @Description : 서비스사업 ServiceImpl Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 07. 18.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
*
* </pre>
*/
@Service("srvcBizService")
public class SrvcBizServiceImpl extends EgovAbstractServiceImpl implements SrvcBizService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "srvcBizMapper")
	private SrvcBizMapper srvcBizMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	//@Resource(name="reqUserJoinMapper")
    //private ReqUserJoinMapper reqUserJoinMapper;
	
	//@Resource(name="trprInqService")
    //private TrprInqService trprInqService;
	
	String userId = "";

	
	/**
	 * @Method     : selectSrvcBizList
	 * @Method설명 : 서비스사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@Override
	public Map<String, Object> selectSrvcBizList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> result  = new HashMap<String, Object>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
//		String taskwkSeCd = "";
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
//		LOGGER.debug("SrvcBizServiceImpl 탐");
		LOGGER.debug("SrvcBizServiceImpl.selectSrvcBizList.paramGroup=[" + paramGroup + "]");
		
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        //페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
      	ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
        String sUntTaskwkSeCd = "";
//		
//		// 세션의 단위업무구분코드 가져오기
		if (loginVO != null && loginVO.getUntTaskwk() != null && !"".equals(loginVO.getUntTaskwk())) {
			sUntTaskwkSeCd = loginVO.getUntTaskwk();	
		}
        
        Map<String, String> paramMap = paramGroup.getSingleValueMap();
		if (sUntTaskwkSeCd != null && !"".equals(sUntTaskwkSeCd)) paramMap.put("UNT_TASKWK_SE_CD", sUntTaskwkSeCd);
       
		// 형변환
		Map<String, Object> paramMap2 = new HashMap<>(paramMap);
		
		String cnt = srvcBizMapper.selectSrvcBizCnt(paramMap2);
		paramMap2.put("TOT_CNT", cnt);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		if(totCnt > 0) {
			//Map<String, Object> mapParam = new HashMap<String, Object>();
			paramMap2.put("START_IDX", startIndex);
			paramMap2.put("LAST_IDX", lastIndex);
			
	        rtnMap = srvcBizMapper.selectSrvcBizList(paramMap2);
		}
		
        //데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		result.put("dsServiceMainList", rtnMap);
		result.put("dmPage", resPage);
		
		return result;
	}
	
	/**
	 * @Method     : processSrvcBizDetail
	 * @Method설명 : 서비스사업 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@Override
	public Map<String, Object> processSrvcBizDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		// 서비스사업 상세저장 호출
		retMap = saveSrvcBizDetail(request, dataRequest);
		LOGGER.debug("서비스사업 상세저장 호출");
		
		return retMap;
	}
	
	
	/**
	 * @Method     : selectSrvcBizDetail
	 * @Method설명 : 서비스사업 상세조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@Override
	public List<Map<String, Object>> selectSrvcBizDetail(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 서비스사업 상세자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("SrvcBizServiceImpl.selectSrvcBizDetail.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList   = new ArrayList<Map<String, Object>>();
		
		
		String sSrvcExcnBizNo = "";
		sSrvcExcnBizNo = paramGroup.getValue("SRVC_BIZ_NO");  // 서비스사업번호
//		if (sSrvcExcnBizNo==null || sSrvcExcnBizNo.equals("null") || sSrvcExcnBizNo.equals("")) {
//			//paramMap.put("SRVC_EXCN_BIZ_NO", sSrvcExcnBizNo);
//			throw new AppWorksException("서비스사업번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
//		}
		
//		// 서비스사업 상세조회
//		List<Map<String,Object>> dsDetail   = new ArrayList<Map<String, Object>>();
		retList = srvcBizMapper.selectSrvcBizDetail(paramMap);
				
		return retList;
	}
	
	/**
	 * @Method     : saveSrvcBizDetail
	 * @Method설명 : 서비스사업 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
		
	//서비스사업 상세저장
	private Map<String, Object> saveSrvcBizDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		ParameterGroup saveSrvcBizDetail = dataRequest.getParameterGroup("dsDetail");
		Iterator<ParameterRow> insertedRows = saveSrvcBizDetail.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveSrvcBizDetail.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveSrvcBizDetail.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			
		}
		
		String sSrvcBizNo = "";
		String sUntTaskwkSeCd = "";
		
		// 세션의 단위업무구분코드 가져오기
		if (loginVO != null && loginVO.getUntTaskwkSeCd() != null && !"".equals(loginVO.getUntTaskwkSeCd())) {
			sUntTaskwkSeCd = loginVO.getUntTaskwk();
		}
		
		
//			String sUntTaskwkSeCd 	 = String.valueOf(retMap.get("UNT_TASKWK_SE_CD"));
		LOGGER.debug("SrvcBizServiceImpl.selectSrvcBizDetail.sUntTaskwkSeCd=[" + sUntTaskwkSeCd + "]");
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			sSrvcBizNo = mapIns.get("SRVC_BIZ_NO");
					
			//서비스사업번호가 없으면
			if(sSrvcBizNo == null || sSrvcBizNo.equals("null") || sSrvcBizNo.equals("")) {
				
				// 서비스사업번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();
				seqMap.put("USER_ID",       userId);
				seqMap.put("RENU_NO_SE_CD", "BZ");							// 서비스사업 채번코드
				seqMap.put("RENU_YMD",       DateUtil.getToday());		    // 현재일자
				
				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sSrvcBizNo = String.valueOf(valMap.get("RENU_NO"));		// 서비스사업번호 발번
			}
			mapIns.put("SRVC_BIZ_NO", sSrvcBizNo);
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("UNT_TASKWK_SE_CD", sUntTaskwkSeCd);
			mapIns.put("DATAA_CHG_SE_CD", "I");
			
			// 서비스사업번호 채번 후 서비스사업 테이블 INSERT (SDA400)
			srvcBizMapper.insertSrvcBizDetail(mapIns);
			// 서비스사업번호 채번 후 서비스사업이력 테이블 INSERT (SDA401)
			srvcBizMapper.insertSrvcBizHistory(mapIns);
			LOGGER.debug("SrvcBizServiceImpl.selectSrvcBizDetail.mapIns=[" + mapIns + "]");
		}

		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("FRST_RGTR_ID", userId);
			mapUpd.put("UNT_TASKWK_SE_CD", sUntTaskwkSeCd);
			mapUpd.put("DATAA_CHG_SE_CD", "U");
			
			srvcBizMapper.updateSrvcBizDetail(mapUpd);
			// 서비스사업번호 채번 후 서비스사업이력 테이블 INSERT (SDA401)
			srvcBizMapper.insertSrvcBizHistory(mapUpd);
			
			LOGGER.debug("SrvcBizServiceImpl.selectSrvcBizDetail.mapUpd=[" + mapUpd + "]");
		}

		while (deletedRows.hasNext()) {
			
			// 2022.12.16 최두일 - 서비스사업 삭제 조건
			ParameterGroup serviceSub = dataRequest.getParameterGroup("dsServiceSubList");
			Iterator<ParameterRow> allRows = serviceSub.getAllRows();
			
			while (allRows.hasNext()) {
				Map<String, String> allMap = allRows.next().toMap();
				String sExcnBizNo = allMap.get("SRVC_EXCN_BIZ_NO");
				LOGGER.debug("서비스사업실행사업번호 = [" + sExcnBizNo + "]");
				if(sExcnBizNo != null && !"".equals(sExcnBizNo) && !"null".equals(sExcnBizNo)) {

					int cnt = srvcBizMapper.selectSrvcExcnBizNoCnt(sExcnBizNo);
					if(cnt > 0 ) {
						throw new AppWorksException("서비스실행사업이 등록되어 있습니다. 서비스 사업을 삭제 할 수 없습니다.", Alert.INFO);
					}
				}
			}				

			Map<String, String> mapDel = deletedRows.next().toMap();
			
			String sts = "D";

			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("FRST_RGTR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", "D");
			mapDel.put("UNT_TASKWK_SE_CD", sUntTaskwkSeCd);
							
			// 서비스사업번호 채번 후 서비스사업이력 테이블 INSERT (SDA401)
			srvcBizMapper.insertSrvcBizHistory(mapDel);
			
			srvcBizMapper.deleteSrvcBizDetail(mapDel);
			
		}
		if(sSrvcBizNo.isEmpty()) sSrvcBizNo = saveSrvcBizDetail.getAllRowList().get(0).get("SRVC_BIZ_NO");
		request.setAttribute("SRVC_BIZ_NO", sSrvcBizNo);
		LOGGER.debug("SRVC_BIZ_NO" + sSrvcBizNo );
		
		// 서비스실행사업 상세저장 호출
		saveSrvcExcnBizDetail(request, dataRequest);
		LOGGER.debug("서비스실행사업 상세저장 호출");
		
		retMap.put("SRVC_BIZ_NO", sSrvcBizNo);
		LOGGER.debug("SrvcBizServiceImpl.selectSrvcBizDetail.retMap2=[" + retMap + "]");
		return retMap;
	}

	/**
	 * @Method     : selectSrvcExcnBizList
	 * @Method설명 : 서비스실행사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@Override
	public List<Map<String, Object>> selectSrvcExcnBizList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}

		LOGGER.debug("SrvcBizServiceImpl.selectSrvcExcnBizList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
	
		
		String sSrvcExcnBizNo = paramGroup.getValue("SRVC_BIZ_NO");  // 서비스사업번호
		
//		if (sSrvcExcnBizNo==null || sSrvcExcnBizNo.equals("null") || sSrvcExcnBizNo.equals("")) {
//			//paramMap.put("RESRCE_NO", sResrceNo);
//			throw new AppWorksException("서비스실행사업번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
//		}
		
		return srvcBizMapper.selectSrvcExcnBizList(paramMap);
	}
	
	/**
	 * @Method     : saveSrvcExcnBizList
	 * @Method설명 : 서비스실행사업 목록 저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
		
	private void saveSrvcExcnBizDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup saveSrvcExcnBizList = dataRequest.getParameterGroup("dsServiceSubList");
		
		Iterator<ParameterRow> insertedRows = saveSrvcExcnBizList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveSrvcExcnBizList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveSrvcExcnBizList.getDeletedRows();
		
		String userId = "";
		int iInstNo   = 0;
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			iInstNo = loginVO.getInstNo();
		}
		
		String sSrvcBizNo = "";
		String sSrvcExcnBizNo = "";
		
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			sSrvcBizNo = mapIns.get("SRVC_BIZ_NO");
			sSrvcExcnBizNo = mapIns.get("SRVC_EXCN_BIZ_NO");
			LOGGER.debug("sSrvcBizNo1 = " + sSrvcBizNo);	//빈 값인 상태
			LOGGER.debug("request.getAttribute = " + request.getAttribute("SRVC_BIZ_NO")); //받아온 값
			if(sSrvcBizNo == null || "".equals(sSrvcBizNo)) sSrvcBizNo = String.valueOf(request.getAttribute("SRVC_BIZ_NO"));
			LOGGER.debug("sSrvcBizNo2 = " + sSrvcBizNo);	//null 체크 후 받아온 상태값
			if(sSrvcExcnBizNo == null || sSrvcExcnBizNo.equals("null") || sSrvcExcnBizNo.equals("")) {
				
				// 서비스실행사업번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();
				seqMap.put("USER_ID",       userId);
				seqMap.put("RENU_NO_SE_CD", "BX");					// 서비스실행사업번호 채번코드
				seqMap.put("RENU_YMD",       DateUtil.getToday());		    // 현재일자
				
				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				sSrvcExcnBizNo = String.valueOf(valMap.get("RENU_NO"));		// 서비스실행사업번호 발번
			}
			mapIns.put("SRVC_BIZ_NO"		, sSrvcBizNo);
			mapIns.put("SRVC_EXCN_BIZ_NO"	, sSrvcExcnBizNo);
			mapIns.put("FRST_RGTR_ID"		, userId);
			mapIns.put("LAST_MDFR_ID"		, userId);
			mapIns.put("DATAA_CHG_SE_CD"	, "I");
			mapIns.put("INST_NO"			, String.valueOf(iInstNo));
			
			// 서비스실행사업번호 채번 후 서비스실행사업 테이블 INSERT (SDA420)
			srvcBizMapper.insertSrvcExcnBizDetail(mapIns);
			// 서비스실행사업번호 채번 후 서비스실행사업 테이블 INSERT (SDA421)
			srvcBizMapper.insertSrvcExcnBizHistory(mapIns);
		}

		while (updatedRows.hasNext()) {
			String sts = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("FRST_RGTR_ID"	, userId);
			mapUpd.put("LAST_MDFR_ID"	, userId);
			mapUpd.put("DATAA_CHG_SE_CD", "U");
			
			srvcBizMapper.updateSrvcExcnBizDetail(mapUpd);
			// 서비스실행사업번호 채번 후 서비스실행사업 테이블 INSERT (SDA421)
			srvcBizMapper.insertSrvcExcnBizHistory(mapUpd);
		}

		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			
			//서비스실행사업번호 사용건수체크 - 2022.10.13 최두일 
			String sExcnBizNo = mapDel.get("SRVC_EXCN_BIZ_NO");
			if(sExcnBizNo != null && !"".equals(sExcnBizNo) && !"null".equals(sExcnBizNo)) {

				int cnt = srvcBizMapper.selectSrvcExcnBizNoCnt(sExcnBizNo);
				if(cnt > 0 ) {
					throw new AppWorksException("해당 서비스실행사업은 등록되어 있습니다. 삭제 할 수 없습니다.", Alert.INFO);
				}
			}
			LOGGER.debug("SrvcBizServiceImpl.saveSrvcBizDetail.sSrvcExcnBizNo=[" + sSrvcExcnBizNo + "]");
			String sts = "D";
			
//				Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("FRST_RGTR_ID", userId);
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", "D");
			// 서비스실행사업번호 채번 후 서비스실행사업 테이블 INSERT (SDA421)
			srvcBizMapper.insertSrvcExcnBizHistory(mapDel);
			srvcBizMapper.deleteSrvcExcnBizDetail(mapDel);
		}
	
		
	}
	
	/**
	 * @Method     : selectExcnSrvcBizList
	 * @Method설명 : 실행서비스사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@Override
	public List<Map<String, Object>> selectExcnSrvcBizList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}

		LOGGER.debug("SrvcBizServiceImpl.selectExcnSrvcBizList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return srvcBizMapper.selectExcnSrvcBizList(paramMap);
	}
	
	/**
	 * @Method     : selectExcnSrvcDetList
	 * @Method설명 : 실행서비스세부사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@Override
	public List<Map<String, Object>> selectExcnSrvcDetList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}

		LOGGER.debug("SrvcBizServiceImpl.selectExcnSrvcDetList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return srvcBizMapper.selectExcnSrvcDetList(paramMap);
	} 
	
	/**
	 * @Method     : saveExcnSrvcBiz
	 * @Method설명 : 실행서비스세부사업 저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	@Override
	public Map<String, Object> saveExcnSrvcBiz(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmExcnSrvcBizClList");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String excnSrvcBizClNo = paramMap.get("EXCN_SRVC_BIZ_CL_NO"); //실행서비스사업분류번호
		String userId = "";
		String instNo = "";
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			if(null != loginVO.getInstNo() && !"".equals(loginVO.getInstNo())) {
				instNo = loginVO.getInstNo().toString();
			}
		}
		paramMap.put("USER_ID", userId);
		String chgSeCd = ""; // 데이터변경구분코드
		//실행서비스사업분류번호가 없으면
		if(excnSrvcBizClNo == null || "null".equals(excnSrvcBizClNo) || "".equals(excnSrvcBizClNo)) {
			chgSeCd = "I";
			// 서비스사업번호 채번
			Map<String, String> seqMap = new HashMap<>();
			Map<String, Object> valMap = new HashMap<>();
			seqMap.put("USER_ID",       userId);
			seqMap.put("RENU_NO_SE_CD", "EB");							// 실행서비스사업분류번호 채번코드
			seqMap.put("RENU_YMD",       DateUtil.getToday());		    // 현재일자
			
			// 채번서비스 호출
			valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
			excnSrvcBizClNo = String.valueOf(valMap.get("RENU_NO"));		// 서비스사업번호 발번
			paramMap.put("EXCN_SRVC_BIZ_CL_NO", excnSrvcBizClNo);
		} else {
			chgSeCd = "U";
		}
		srvcBizMapper.saveExcnSrvcBiz(paramMap); // SDA430 저장
		
		paramMap.put("DATAA_CHG_SE_CD", chgSeCd);
		srvcBizMapper.saveExcnSrvcBizHis(paramMap); // SDA431 저장
		
		ParameterGroup saveSrvcBizDetail = dataRequest.getParameterGroup("dsExcnSrvcDetaiaBizList");
		Iterator<ParameterRow> insertedRows = saveSrvcBizDetail.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveSrvcBizDetail.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveSrvcBizDetail.getDeletedRows();

		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("EXCN_SRVC_BIZ_CL_NO", excnSrvcBizClNo);
			String excnSrvcDetaiaBizNo = mapIns.get("EXCN_SRVC_DETAIA_BIZ_NO");
			if(null == excnSrvcDetaiaBizNo || "".equals(excnSrvcDetaiaBizNo)) {
				// 실행서비스세부사업번호 채번
				Map<String, String> seqMap = new HashMap<>();
				Map<String, Object> valMap = new HashMap<>();
				seqMap.put("USER_ID",       userId);
				seqMap.put("RENU_NO_SE_CD", "ED");							// 실행서비스세부사업번호 채번코드
				seqMap.put("RENU_YMD",       DateUtil.getToday());		    // 현재일자
				
				// 채번서비스 호출
				valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
				excnSrvcDetaiaBizNo = String.valueOf(valMap.get("RENU_NO"));		// 서비스사업번호 발번
				mapIns.put("EXCN_SRVC_DETAIA_BIZ_NO", excnSrvcDetaiaBizNo);
			}
			mapIns.put("USER_ID", userId);
			mapIns.put("INST_NO", instNo);
			mapIns.put("DATAA_CHG_SE_CD", "I");
			srvcBizMapper.insertExecSrvcDetaiaBiz(mapIns);
			srvcBizMapper.insertExecSrvcDetaiaBizHis(mapIns);
		}
		
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("USER_ID", userId);
			mapUpd.put("INST_NO", instNo);
			mapUpd.put("DATAA_CHG_SE_CD", "U");
			srvcBizMapper.updateExecSrvcDetaiaBiz(mapUpd);
			srvcBizMapper.insertExecSrvcDetaiaBizHis(mapUpd);
		}
		
		//삭제는 없다. USE_YN 사용
		// 2023.08.08 삭제 기능 추가
		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			String sExcnSrvcDetBizNo = mapDel.get("EXCN_SRVC_DETAIA_BIZ_NO");
			if(!"".equals(sExcnSrvcDetBizNo) && null != sExcnSrvcDetBizNo) {
				int cnt = srvcBizMapper.selectExcnSrvcDetBizNoCnt(sExcnSrvcDetBizNo);
				if(cnt > 0 ) {
					throw new AppWorksException("서비스실행사업이 등록되어 있습니다.\n서비스 사업을 삭제 할 수 없습니다.", Alert.INFO);
				} else {
					mapDel.put("USER_ID", userId);
					mapDel.put("DATAA_CHG_SE_CD", "D");
					srvcBizMapper.deleteExecSrvcDetaiaBiz(mapDel);
					srvcBizMapper.insertExecSrvcDetaiaBizHis(mapDel);
				}
			}
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("SRVC_BIZ_NO", excnSrvcBizClNo);
		
		return retMap;
	}
	
	/**
	 * @Method     : selectExcnDetaiaList
	 * @Method설명 : 실행서비스 세부사업 목록 팝업 조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	@Override
	public List<Map<String, Object>> selectExcnDetaiaList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}

		LOGGER.debug("SrvcBizServiceImpl.selectExcnSrvcDetList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return srvcBizMapper.selectExcnDetaiaList(paramMap);
	} 
	
	/**
	 * @Method     : deleteExcnSrvcBiz
	 * @Method설명 : 실행서비스사업 삭제
	 * @param      : dataRequest
	 * @return     : void 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 08. 
 	 */	
	@Override
	public void deleteExcnSrvcBiz(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmExcnSrvcBizClList");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		srvcBizMapper.deleteExcnSrvcBiz(paramMap);
		
		paramMap.put("DATAA_CHG_SE_CD", "D");
		srvcBizMapper.saveExcnSrvcBizHis(paramMap); // SDA431 저장
	}
}		
