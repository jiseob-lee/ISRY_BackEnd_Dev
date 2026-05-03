/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.service.impl;

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
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.uneart.mapper.DscsnUneartMapper;
import isry.itgcm.casemng.uneart.service.DscsnUneartService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;


/**
* @Class Name  : DscsnUneartServiceImpl.java
* @Description : 발굴정보 ServiceImpl Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 23.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 23.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Service("dscsnUneartService")
public class DscsnUneartServiceImpl extends EgovAbstractServiceImpl implements DscsnUneartService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="dscsnUneartMapper")
    private DscsnUneartMapper dscsnUneartMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
	/**
	 * @Method     : selectDscsnUneartList
	 * @Method설명 : 발굴 목록조회(01:초기상담,02:아웃리치,03.긴급개입,04.연계)
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public List<Map<String, Object>> selectUneartDscsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String>       paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList  = new ArrayList<Map<String, Object>>();
		
		String sUneartTypeSeCd = "";
		sUneartTypeSeCd = paramGroup.getValue("UNEART_TYPE_SE_CD");  // 발굴유형구분코드(01:발굴,02:아웃리치,03.긴급개입,04.연계)
		if (sUneartTypeSeCd==null || sUneartTypeSeCd.equals("null") || sUneartTypeSeCd.equals("")) {
			throw new AppWorksException("발굴유형구분코드는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		// 세션정보
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/			
		
	    switch(sUneartTypeSeCd){
	        case "01" :          // 01:발굴
	        	retList = dscsnUneartMapper.selectUneartDscsnList(paramMap2);
	        	break;
	        case "02" :          // 02:아웃리치 
	        	retList = dscsnUneartMapper.selectOutrctList(paramMap2);
	        	break;
	        case "03" :          // 03:긴급개입 
	    		retList = dscsnUneartMapper.selectEmrgRescList(paramMap2);
	            break;         
	        default :
	            break;         
	    }
		
		for (int iCnt=0; iCnt < retList.size(); iCnt++) {
			
			String sNmDecpt      = String.valueOf(retList.get(iCnt).get("TRPR_NM"));
			String sTrprInfoNoCnt = String.valueOf(retList.get(iCnt).get("TRPR_INFO_NO_CNT"));
			int    iTrprInfoNoCnt = Integer.parseInt(sTrprInfoNoCnt);
			if(iTrprInfoNoCnt > 1) {
				int iTrprCnt = (iTrprInfoNoCnt - 1);
				retList.get(iCnt).put("TRPR_NM", (sNmDecpt.concat("외 ").concat(String.valueOf(iTrprCnt)).concat("명")));
			}else {
				
				// #2577 시스템문의사항 아웃리치 등록 시 대상자가 없는 경우 대상자 명에 'null'로 출력됩니다.
				if("03".equals(sUneartTypeSeCd)) {
					retList.get(iCnt).put("TRPR_NM", sNmDecpt);
				}else {
					/* 아웃리치는 대상자를 모두 삭제가능*/
					if(iTrprInfoNoCnt <= 0) {
						retList.get(iCnt).put("TRPR_NM", "");
					}else {
						retList.get(iCnt).put("TRPR_NM", sNmDecpt);
					}
				}
			}
		}
		
		return retList;
	}
	
	/**
	 * @Method     : selectDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세조회, 조치내역(대상자)조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public Map<String, Object> selectDscsnUneartDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, Object> retMap   = new HashMap<String, Object>();
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		String sEryyDscsnNo = "";
		
		sEryyDscsnNo = paramGroup.getValue("ERYY_DSCSN_NO");  // 초기상담번호(ER)
		if (sEryyDscsnNo==null || sEryyDscsnNo.equals("null") || sEryyDscsnNo.equals("")) {
			throw new AppWorksException("초기상담번호는 필수입력 항목입니다. 입력해 주세요.", Alert.ERROR);
		}

		// 발굴(초기상담) 상세조회
		Map<String, Object> dmDetail          = new HashMap<String, Object>();
		dmDetail  = dscsnUneartMapper.selectDscsnUneartDetail(paramMap);
		
		// 발굴(초기상담) 조치내역(대상자)조회
		List<Map<String, Object>> dsActnList  = new ArrayList<Map<String, Object>>();
		dsActnList = dscsnUneartMapper.selectDscsnUneartActnList(paramMap);
		
		retMap.put("dmDetail",   dmDetail);
		retMap.put("dsActnList", dsActnList);
        
        return retMap;        
	}
	
	/**
	 * @Method     : selectDscsnUneartHstrList
	 * @Method설명 : 발굴(초기상담) 이력조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public List< Map<String, Object>> selectDscsnUneartHstrList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return dscsnUneartMapper.selectDscsnUneartHstrList(paramMap);
	}
	
	/**
	 * @Method     : processDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public Map<String, Object> processDscsnUneartDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		int    itotCnt       = 0;	// 전체건수
		int    iIntCnt       = 0;	// 등록건수
		int    iUpdCnt       = 0;	// 수정건수
		int    iDelCnt       = 0;	// 삭제건수
		int    iHisCnt       = 0;	// 이력등록건수
		int    iCnt          = 0;	// 건수

		String sUserId       = "";	// 세션정보의 유저ID
		String sWprkSqn      = "";	// 채번번호
		String sStatus       = "s";	// 상태코드(s:조회, i:신규, u:변경, d:삭제)
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}


		/* ------------------------ */
		/* 발굴(초기상담) 상세 처리 */
		/* ------------------------ */
		// 발굴상세자료 DataSet
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmDetail");
		if (paramGroup == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("DscsnUneartServiceImpl.processDscsnUneartDetail.paramGroup=[" + paramGroup + "]");

		// 상태코드(s:조회, i:신규, u:변경, d:삭제) 설정
		sStatus = paramGroup.getValue("TYPE");
		if (sStatus == null || sStatus.equals("null") || sStatus.equals("")) {
			sStatus = "s";
		}
		LOGGER.debug("DscsnUneartServiceImpl.processDscsnUneartDetail.sStatus=[" + sStatus + "]");

		Map<String, String> saveMap = paramGroup.getSingleValueMap();

		// 필수항목 및 처리항목 체크
		saveMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
		saveMap.put("DATAA_CHG_SE_CD", sStatus);		// 데이터변경구분코드 셋팅
		
		// 등록처리
		if (sStatus.equals("i") || sStatus.equals("I")) {
			
			// 초기상담번호(ER) 채번
			Map<String, String> seqMap = new HashMap<>();
			Map<String, Object> valMap = new HashMap<>();
			
			seqMap.put("USER_ID",       sUserId);
			seqMap.put("RENU_NO_SE_CD", "ER");					// 초기상담번호 채번코드
			seqMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
			
			// 채번서비스 호출
			valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
			sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 초기상담번호(ER) 발번
			LOGGER.debug("DscsnUneartServiceImpl.processDscsnUneartDetail.sWprkSqn=[" + sWprkSqn + "]");
			
			saveMap.put("ERYY_DSCSN_NO",   sWprkSqn);
			
			// 발굴(초기상담) 상세등록 호출
			iIntCnt = dscsnUneartMapper.insertDscsnUneartDetail(saveMap);
			if (iIntCnt > 0) {
				
				LOGGER.debug("DscsnUneartServiceImpl.processDscsnUneartDetail.UNT_TASKWK_SE_CD=[" + saveMap.get("UNT_TASKWK_SE_CD") + "]");
				// 발굴(초기상담) 이력등록 호출
				iHisCnt = dscsnUneartMapper.insertDscsnUneartHistory(saveMap);
			}
			
		// 수정처리
		} else if (sStatus.equals("u") || sStatus.equals("U")) {

			sWprkSqn = String.valueOf(saveMap.get("ERYY_DSCSN_NO"));		// 초기상담번호(ER)

			String sModChk = "N";	// 항목변경여부
			sModChk = dscsnUneartMapper.selectDscsnUneartDetailModChk(saveMap);
			LOGGER.debug("DscsnOutrcServiceImpl.processTrprInqDetail.selectDscsnOutrcDetailModChk.sModChk=[" + sModChk + "]");

			// 항목이 변경된건이 있을경우
			if (sModChk.equals("Y") || sModChk.equals("y")) {
				// 발굴(초기상담) 상세수정 호출
				iUpdCnt = dscsnUneartMapper.updateDscsnUneartDetail(saveMap);
				if (iUpdCnt > 0) {
					
					// 발굴(초기상담) 이력등록 호출
					iHisCnt = dscsnUneartMapper.insertDscsnUneartHistory(saveMap);
				}
			}
			
		// 삭제처리
		} else if (sStatus.equals("d") || sStatus.equals("D")) {
			
			sWprkSqn = String.valueOf(saveMap.get("ERYY_DSCSN_NO"));		// 초기상담번호(ER)
			
			// 발굴(초기상담,아웃리치)상세 삭제 호출
			iDelCnt = dscsnUneartMapper.deleteDscsnUneartDetail(saveMap);
			if (iDelCnt > 0) {
				
				// 발굴(초기상담,아웃리치)이력 등록 호출
				iHisCnt = dscsnUneartMapper.insertDscsnUneartHistory(saveMap);
			}
		}

		
		/* -------------------------------- */
		/* 발굴(초기상담) 조치현황 처리 */
		/* -------------------------------- */
		// 조치현황 DataSet
//		int iActnSn = 0;
//		ParameterGroup paramDsActn = dataRequest.getParameterGroup("dsActnList");
//		
//		if (paramDsActn != null) {
//
//			List<Map<String, String>> prcDsActn = paramDsActn.getAllRowList();
//			iCnt = 0;
//			
//			for (Map<String, String> rowMap : prcDsActn) {
//				
//				String sStatusDtl  = String.valueOf(paramDsActn.getRowState(iCnt)).replace("RowState [state=", "").replace("]", "");
//				
//				// 필수항목 및 처리항목 체크
//				rowMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
//				rowMap.put("DATAA_CHG_SE_CD", sStatusDtl);	// 데이터변경구분코드 셋팅
//
//				// 등록처리
//				if (sStatusDtl.equals("i") || sStatusDtl.equals("I")) {
//					
//					rowMap.put("ERYY_DSCSN_NO",   sWprkSqn);
//					
//					// 발굴(아웃리치) 조치일련번호 발번
//					iActnSn = dscsnUneartMapper.selectDscsnUneartActnSn(rowMap);
//					rowMap.put("ACTN_SN", String.valueOf(iActnSn));
//					
//					// 발굴(초기상담후) 조치등록 호출
//					iIntCnt = dscsnUneartMapper.insertDscsnUneartActn(rowMap);
//					if (iIntCnt > 0) {
//						
//						// 발굴(초기상담후) 조치이력등록 호출
//						iHisCnt = dscsnUneartMapper.insertDscsnUneartActnHistory(rowMap);
//					}
//				// 수정처리
//				} else if (sStatusDtl.equals("u") || sStatusDtl.equals("U")) {
//
//					// 발굴(초기상담후) 조치수정 호출
//					iUpdCnt = dscsnUneartMapper.updateDscsnUneartActn(rowMap);
//					if (iUpdCnt > 0) {
//						
//						// 발굴(초기상담후) 조치이력등록 호출
//						iHisCnt = dscsnUneartMapper.insertDscsnUneartActnHistory(rowMap);
//					}
//				// 삭제처리
//				} else if (sStatusDtl.equals("d") || sStatusDtl.equals("D")) {
//
//					// 발굴(아웃리치) 조치현황 삭제호출
//					iDelCnt = dscsnUneartMapper.deleteDscsnUneartActn(rowMap);
//					if (iDelCnt > 0) {
//						
//						// 발굴(아웃리치) 조치현황 이력등록호출
//						iHisCnt = dscsnUneartMapper.insertDscsnUneartActnHistory(rowMap);
//					}
//				}
//		
//				iCnt ++;
//			}
//		}
		
		
		ParameterGroup paramDsActnList = dataRequest.getParameterGroup("dsActnList");
		
		LOGGER.debug("==========대상자조치 대상자목록정보조회=[" + paramDsActnList +"]");

		Iterator<ParameterRow> insertedRows = paramDsActnList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = paramDsActnList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = paramDsActnList.getDeletedRows();
		
		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();

			// 필수항목 및 처리항목 체크
			mapIns.put("ERYY_DSCSN_NO",   sWprkSqn);			
			mapIns.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
			mapIns.put("DATAA_CHG_SE_CD", sts);	        // 데이터변경구분코드 셋팅
			
			// 발굴(아웃리치) 조치일련번호 발번
			int iActnSn = dscsnUneartMapper.selectDscsnUneartActnSn(mapIns);
			mapIns.put("ACTN_SN", String.valueOf(iActnSn));
			
			// 발굴(초기상담후) 조치등록 호출
			iIntCnt = dscsnUneartMapper.insertDscsnUneartActn(mapIns);
			if (iIntCnt > 0) {
				// 발굴(초기상담후) 조치이력등록 호출
				iHisCnt = dscsnUneartMapper.insertDscsnUneartActnHistory(mapIns);
			}			
		}
		while (updatedRows.hasNext()) {
			String sts = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			// 필수항목 및 처리항목 체크
			mapUpd.put("SESS_USER_ID", sUserId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			// 발굴(초기상담후) 조치수정 호출
			iUpdCnt = dscsnUneartMapper.updateDscsnUneartActn(mapUpd);
			if (iUpdCnt > 0) {
				// 발굴(초기상담후) 조치이력등록 호출
				iHisCnt = dscsnUneartMapper.insertDscsnUneartActnHistory(mapUpd);
			}
		}
		while (deletedRows.hasNext()) {
			String sts = "D";
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			// 필수항목 및 처리항목 체크
			mapDel.put("SESS_USER_ID", sUserId);
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			// 발굴(아웃리치) 조치현황 삭제호출
			iDelCnt = dscsnUneartMapper.deleteDscsnUneartActn(mapDel);
			if (iDelCnt > 0) {
				/* 기존 이력삭제*/
				dscsnUneartMapper.deleteDscsnUneartActnHistory(mapDel);
				mapDel.put("DEL_YN", "Y");
				// 발굴(아웃리치) 조치현황 이력등록호출
				iHisCnt = dscsnUneartMapper.insertDscsnUneartActnHistory(mapDel);
			}
		}		
		
		
		// 초기상담번호(ER) key값 셋팅
		retMap.put("ERYY_DSCSN_NO", saveMap.get("ERYY_DSCSN_NO"));
		
		return retMap;
	}
	
}