/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.uneart.mapper.DscsnOutrcMapper;
import isry.itgcm.casemng.uneart.service.DscsnOutrcService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;


/**
* @Class Name  : DscsnOutrcServiceImpl.java
* @Description : 아웃리치정보 ServiceImpl Class
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
@Service("dscsnOutrcService")
public class DscsnOutrcServiceImpl extends EgovAbstractServiceImpl implements DscsnOutrcService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="dscsnOutrcMapper")
    private DscsnOutrcMapper dscsnOutrcMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method     : selectDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세조회, 연합거리상담조회, 쉼터자체활동조회, 지원서비스실적, 조치현황
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public Map<String, Object> selectDscsnOutrcDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("DscsnOutrcServiceImpl.selectDscsnOutrcDetail.paramGroup=[" + paramGroup + "]");

		Map<String, Object> retMap   = new HashMap<String, Object>();
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		// 발굴(아웃리치) 상세조회
		Map<String, Object> dmDetail           = new HashMap<String, Object>();
		dmDetail  = dscsnOutrcMapper.selectDscsnOutrcDetail(paramMap);
		
		// 발굴(아웃리치) 연합거리상담조회
		List<Map<String, Object>> dsUniteList  = new ArrayList<Map<String, Object>>();
		dsUniteList = dscsnOutrcMapper.selectDscsnOutrcUniteList(paramMap);
		
		// 발굴(아웃리치) 쉼터자체활동조회
		List<Map<String, Object>> dsSheltrList = new ArrayList<Map<String, Object>>();
		dsSheltrList = dscsnOutrcMapper.selectDscsnOutrcSheltrList(paramMap);
		
		
		Map<String, Object> uneartlMap = new HashMap<>();
		uneartlMap = dscsnOutrcMapper.selectUneartActbtClList(paramMap);
		
		LOGGER.debug("UNEART_ACTVT_CL_SE_CD" + uneartlMap+ "]");
 		if(uneartlMap != null) {
			
			uneartlMap.put("UNEART_ACTVT_CL_SE_CD"    , uneartlMap.get("UNEART_ACTVT_CL_SE_CD"));
			uneartlMap.put("UNEART_ACTVT_SE_CD"       , uneartlMap.get("UNEART_ACTVT_SE_CD"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT1"    , (uneartlMap.get("'101'") == null) ? 0 : uneartlMap.get("'101'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT2"    , (uneartlMap.get("'102'") == null) ? 0 : uneartlMap.get("'102'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT3"    , (uneartlMap.get("'103'") == null) ? 0 : uneartlMap.get("'103'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT4"    , (uneartlMap.get("'104'") == null) ? 0 : uneartlMap.get("'104'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT5"    , (uneartlMap.get("'105'") == null) ? 0 : uneartlMap.get("'105'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT6"    , (uneartlMap.get("'106'") == null) ? 0 : uneartlMap.get("'106'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT7"    , (uneartlMap.get("'107'") == null) ? 0 : uneartlMap.get("'107'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT8"    , (uneartlMap.get("'108'") == null) ? 0 : uneartlMap.get("'108'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT9"    , (uneartlMap.get("'109'") == null) ? 0 : uneartlMap.get("'109'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT10"   , (uneartlMap.get("'199'") == null) ? 0 : uneartlMap.get("'199'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT11"   , (uneartlMap.get("'201'") == null) ? 0 : uneartlMap.get("'201'"));
			uneartlMap.put("UNEART_ACTVT_PIC_CNT12"   , (uneartlMap.get("'202'") == null) ? 0 : uneartlMap.get("'202'"));
				
			if("1".equals(String.valueOf(uneartlMap.get("UNEART_ACTVT_CL_SE_CD")))) {
				uneartlMap.put("SUM_UNEART_ACTVT_PIC_CNT1", uneartlMap.get("SUM_UNEART_ACTVT_PIC_CNT"));
				uneartlMap.put("SUM_UNEART_ACTVT_PIC_CNT2", "0");
			}else {
				uneartlMap.put("SUM_UNEART_ACTVT_PIC_CNT2", uneartlMap.get("SUM_UNEART_ACTVT_PIC_CNT"));
				uneartlMap.put("SUM_UNEART_ACTVT_PIC_CNT1", "0");
			}
		}
 		
		// 발굴(아웃리치) 지원서비스실적
		Map<String, Object> dmPrfmncDetail     = new HashMap<String, Object>();
		dmPrfmncDetail  = dscsnOutrcMapper.selectDscsnOutrcPrfmncDetail(paramMap);
		
		// 발굴(아웃리치) 조치현황
		List<Map<String, Object>> dsActnList   = new ArrayList<Map<String, Object>>();
		dsActnList = dscsnOutrcMapper.selectDscsnOutrcActnList(paramMap);
		
		retMap.put("dmDetail", dmDetail);
		retMap.put("dsUnite",  dsUniteList);
		retMap.put("dsSheltr", dsSheltrList);
		retMap.put("dmPrfmnc", dmPrfmncDetail);
		retMap.put("dsActn",   dsActnList);
		retMap.put("dmUneartActbtCl",   uneartlMap);
		
        
        return retMap;        
	}
	
	/**
	 * @Method     : selectDscsnOutrcUniteList
	 * @Method설명 : 발굴(아웃리치) 연합거리상담조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public List< Map<String, Object>> selectDscsnOutrcUniteList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		
		String sEryyGrDscsnNo = "";
		
		sEryyGrDscsnNo = paramGroup.getValue("ERYY_GR_DSCSN_NO");  // 초기집단상담번호(EG)
		if (sEryyGrDscsnNo==null || sEryyGrDscsnNo.equals("null") || sEryyGrDscsnNo.equals("")) {
			paramMap.put("ERYY_GR_DSCSN_NO", "");
			//throw new AppWorksException("초기집단상담번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return dscsnOutrcMapper.selectDscsnOutrcUniteList(paramMap);
	}
	
	/**
	 * @Method     : selectDscsnOutrcSheltrList
	 * @Method설명 : 발굴(아웃리치) 쉼터자체활동조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public List< Map<String, Object>> selectDscsnOutrcSheltrList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("DscsnOutrcServiceImpl.selectDscsnOutrcSheltrList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		
		String sEryyGrDscsnNo = "";
		
		sEryyGrDscsnNo = paramGroup.getValue("ERYY_GR_DSCSN_NO");  // 초기집단상담번호(EG)
		if (sEryyGrDscsnNo==null || sEryyGrDscsnNo.equals("null") || sEryyGrDscsnNo.equals("")) {
			paramMap.put("ERYY_GR_DSCSN_NO", "");
			//throw new AppWorksException("초기집단상담번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		return dscsnOutrcMapper.selectDscsnOutrcSheltrList(paramMap);
	}
	
	/**
	 * @Method     : selectDscsnOutrcPrfmncDetail
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public Map<String, Object> selectDscsnOutrcPrfmncDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("DscsnOutrcServiceImpl.selectDscsnOutrcPrfmncDetail.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return dscsnOutrcMapper.selectDscsnOutrcPrfmncDetail(paramMap);
	}
	
	/**
	 * @Method     : selectDscsnOutrcActnList
	 * @Method설명 : 발굴(아웃리치) 조치현황
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public List< Map<String, Object>> selectDscsnOutrcActnList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("DscsnOutrcServiceImpl.selectDscsnOutrcActnList.paramGroup=[" + paramGroup + "]");

		Map<String, String> paramMap = paramGroup.getSingleValueMap();

		return dscsnOutrcMapper.selectDscsnOutrcActnList(paramMap);
	}
	
	/**
	 * @Method     : processDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@Override
	public Map<String, Object> processDscsnOutrcDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();

		int    iTotCnt       = 0;	// 전체건수
		int    iIntCnt       = 0;	// 등록건수
		int    iUpdCnt       = 0;	// 수정건수
		int    iDelCnt       = 0;	// 삭제건수
		int    iHisCnt       = 0;	// 이력등록건수
		int    iCnt          = 0;	// 건수

		String sUserId       = "";	// 세션정보의 유저ID
		String sWprkSqn      = "";	// 채번번호
		String sStatus       = "";	// 상태코드(s:조회, i:신규, u:변경, d:삭제)
		String sStatus1      = "";	// 상태코드(s:조회, i:신규, u:변경, d:삭제)
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}


		/* ------------------------ */
		/* 발굴(아웃리치) 상세 처리 */
		/* ------------------------ */
		// 발굴상세자료 DataSet
		ParameterGroup paramDmDetail = dataRequest.getParameterGroup("dmDetail");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDmDetail=[" + paramDmDetail + "]");
		if (paramDmDetail == null) {
			throw new AppWorksException("저장할 자료가 없읍니다.", Alert.ERROR);
		}
		
		// 상태코드(s:조회, i:신규, u:변경, d:삭제) 설정
		sStatus = paramDmDetail.getValue("TYPE");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDmDetail.sStatus=[" + sStatus + "]");
		if (sStatus == null || sStatus.equals("null") || sStatus.equals("")) {
			sStatus = "s";
		}
		Map<String, String> saveMap = paramDmDetail.getSingleValueMap();

		String sDscsnYmd = String.valueOf(saveMap.get("DSCSN_YMD"));		// 상담일자
		if (sDscsnYmd == null || sDscsnYmd.equals("null") || sDscsnYmd.equals("")) {
			throw new AppWorksException("상담일자는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		String sConsttNo = String.valueOf(saveMap.get("CONSTT_NO"));		// 상담자번호
		if (sConsttNo == null || sConsttNo.equals("null") || sConsttNo.equals("")) {
			throw new AppWorksException("상담자번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		String sUneartActvtMthdSeCd = String.valueOf(saveMap.get("UNEART_ACTVT_MTHD_SE_CD"));		// 발굴활동방법구분코드
		if (sUneartActvtMthdSeCd == null || sUneartActvtMthdSeCd.equals("null") || sUneartActvtMthdSeCd.equals("")) {
			throw new AppWorksException("발굴활동방법구분코드는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		String sDscsnTtlNm = String.valueOf(saveMap.get("DSCSN_TTL_NM"));		// 상담제목명
		if (sDscsnTtlNm == null || sDscsnTtlNm.equals("null") || sDscsnTtlNm.equals("")) {
			throw new AppWorksException("상담제목은 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		String sDscsnCn = String.valueOf(saveMap.get("DSCSN_CN"));		// 상담내용
		if (sDscsnCn == null || sDscsnCn.equals("null") || sDscsnCn.equals("")) {
			throw new AppWorksException("상담내용은 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}

		String sRgtrInstNo = String.valueOf(saveMap.get("RGTR_INST_NO"));		// 등록자기관번호
		if (sRgtrInstNo == null || sRgtrInstNo.equals("null") || sRgtrInstNo.equals("")) {
			throw new AppWorksException("등록자기관번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		// 필수항목 및 처리항목 체크
		saveMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
		saveMap.put("DATAA_CHG_SE_CD", sStatus);		// 데이터변경구분코드 셋팅
		
		// 등록처리
		if (sStatus.equals("i") || sStatus.equals("I")) {
			
			// 초기상담번호(ER) 채번
			Map<String, String> seqMap = new HashMap<>();
			Map<String, Object> valMap = new HashMap<>();
			
			seqMap.put("USER_ID",       sUserId);
			seqMap.put("RENU_NO_SE_CD", "EG");					// 초기집단상담번호 채번코드
			seqMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자
			
			// 채번서비스 호출
			valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
			sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 초기상담번호(ER) 발번
			LOGGER.debug("DscsnOutrcServiceImpl.processTrprInqDetail.sWprkSqn=[" + sWprkSqn + "]");

			saveMap.put("ERYY_GR_DSCSN_NO",   sWprkSqn);
			
			// 발굴(아웃리치) 상세등록 호출
			iIntCnt = dscsnOutrcMapper.insertDscsnOutrcDetail(saveMap);
			if (iIntCnt > 0) {
				// 발굴(아웃리치) 이력등록 호출
				iHisCnt = dscsnOutrcMapper.insertDscsnOutrcHistory(saveMap);
			}
		// 수정처리
		} else if (sStatus.equals("u") || sStatus.equals("U")) {

			sWprkSqn = String.valueOf(saveMap.get("ERYY_GR_DSCSN_NO"));		// 초기집단상담번호(EG)

			String sModChk = "N";	// 항목변경여부
			sModChk = dscsnOutrcMapper.selectDscsnOutrcDetailModChk(saveMap);
			LOGGER.debug("DscsnOutrcServiceImpl.processTrprInqDetail.selectDscsnOutrcDetailModChk.sModChk=[" + sModChk + "]");

			// 항목이 변경된건이 있을경우
			if (sModChk.equals("Y") || sModChk.equals("y")) {
			
				// 발굴(아웃리치) 상세수정 호출
				iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcDetail(saveMap);
				if (iUpdCnt > 0) {
					// 발굴(아웃리치) 이력등록 호출
					iHisCnt = dscsnOutrcMapper.insertDscsnOutrcHistory(saveMap);
				}
			}
		// 삭제처리
		} else if (sStatus.equals("d") || sStatus.equals("D")) {
			
			sWprkSqn = String.valueOf(saveMap.get("ERYY_GR_DSCSN_NO"));		// 초기집단상담번호(EG)

			// 발굴(아웃리치) 상세삭제 호출
			iDelCnt = dscsnOutrcMapper.deleteDscsnOutrcDetail(saveMap);
			if (iDelCnt > 0) {
				// 발굴(아웃리치) 이력등록 호출
				iHisCnt = dscsnOutrcMapper.insertDscsnOutrcHistory(saveMap);
			}
		}

		
		/* -------------------------------- */
		/* 발굴(아웃리치) 조치현황 처리 */
		/* -------------------------------- */
		// 쉼터자체활동자료 DataSet
		int iActnSn = 0;
		ParameterGroup paramDsActn = dataRequest.getParameterGroup("dsActn");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDsActn=[" + paramDsActn + "]");
		
		if (paramDsActn != null) {

			List<Map<String, String>> prcDsActn = paramDsActn.getAllRowList();
			iCnt = 0;
			
			for (Map<String, String> rowMap : prcDsActn) {

//				String sActnTypeSeCd = String.valueOf(rowMap.get("ACTN_TYPE_SE_CD"));		// 조치유형구분코드
//				if (sActnTypeSeCd == null || sActnTypeSeCd.equals("null") || sActnTypeSeCd.equals("")) {
//					throw new AppWorksException("조치유형구분코드는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
//				}
				
				String sStatusDtl  = String.valueOf(paramDsActn.getRowState(iCnt)).replace("RowState [state=", "").replace("]", "");
				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.paramDsActn.sStatusDtl=[" + sStatusDtl + "], getRowState=[" + String.valueOf(paramDsActn.getRowState(iCnt)) + "]");
				
				// 필수항목 및 처리항목 체크
				rowMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
				rowMap.put("DATAA_CHG_SE_CD", sStatusDtl);	// 데이터변경구분코드 셋팅

				// 등록처리
				if (sStatusDtl.equals("i") || sStatusDtl.equals("I")) {
					
					rowMap.put("ERYY_GR_DSCSN_NO",   sWprkSqn);
					
					// 발굴(아웃리치) 조치일련번호 발번
					iActnSn = dscsnOutrcMapper.selectDscsnOutrcActnSn(rowMap);
					LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.selectDscsnOutrcActnSn.iActnSn=[" + iActnSn + "]");
					rowMap.put("ACTN_SN", String.valueOf(iActnSn));
					
					// 발굴(아웃리치) 조치현황 등록호출
					iIntCnt = dscsnOutrcMapper.insertDscsnOutrcActn(rowMap);
					if (iIntCnt > 0) {
						// 발굴(아웃리치)조치현황 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActnHistory(rowMap);
					}
				// 수정처리
				} else if (sStatusDtl.equals("u") || sStatusDtl.equals("U")) {

					// 발굴(아웃리치) 조치현황 수정호출
					iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcActn(rowMap);
					if (iUpdCnt > 0) {
						// 발굴(아웃리치) 조치현황 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActnHistory(rowMap);
					}
				// 삭제처리
				} else if (sStatusDtl.equals("d") || sStatusDtl.equals("D")) {

					// 발굴(아웃리치) 조치현황 삭제호출
					iDelCnt = dscsnOutrcMapper.deleteDscsnOutrcActn(rowMap);
					if (iDelCnt > 0) {
						// 발굴(아웃리치) 조치현황 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActnHistory(rowMap);
					}
				}
		
				iCnt ++;
			}
		}

		
		/* -------------------------------- */
		/* 발굴(아웃리치) 연합거리상담 처리 */
		/* -------------------------------- */
		// 연합거리상담자료 DataSet
		ParameterGroup paramDsUnite = dataRequest.getParameterGroup("dsUnite");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDsUnite=[" + paramDsUnite + "]");
		
//		if (paramDsUnite != null) {
//
//			List<Map<String, String>> prcDsUnite = paramDsUnite.getAllRowList();
//			iCnt = 0;
//			
//			for (Map<String, String> rowMap : prcDsUnite) {
//				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.paramDsUnite.iCnt=[" + iCnt + "," + rowMap + "]");
//				
//				String sStatusDtl  = String.valueOf(paramDsUnite.getRowState(iCnt)).replace("RowState [state=", "").replace("]", "");
//				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.paramDsUnite.sStatusDtl=[" + sStatusDtl + "], getRowState=[" + String.valueOf(paramDsUnite.getRowState(iCnt)) + "]");
//				
//				String sEryyGrDscsnNoDsUnite = String.valueOf(rowMap.get("ERYY_GR_DSCSN_NO"));		// 초기집단상담번호(EG)
//				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDsUnite.sEryyGrDscsnNoDsUnite=[" + sEryyGrDscsnNoDsUnite + "]");
//				if (sEryyGrDscsnNoDsUnite == null || sEryyGrDscsnNoDsUnite.equals("null") || sEryyGrDscsnNoDsUnite.equals("")) {
//					sStatusDtl = "I";
//				} else {
//					sStatusDtl = "U";
//				}
//				
//				// 필수항목 및 처리항목 체크
//				rowMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
//				rowMap.put("DATAA_CHG_SE_CD", sStatusDtl);	// 데이터변경구분코드 셋팅
//
//				// 등록처리
//				if (sStatusDtl.equals("i") || sStatusDtl.equals("I")) {
//					
//					rowMap.put("ERYY_GR_DSCSN_NO",   sWprkSqn);
//					
//					// 발굴(아웃리치) 연합거리상담 등록호출
//					iIntCnt = dscsnOutrcMapper.insertDscsnOutrcActvt(rowMap);
//					if (iIntCnt > 0) {
//						// 발굴(아웃리치)연합거리상담 이력등록호출
//						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
//					}
//				// 수정처리
//				} else if (sStatusDtl.equals("u") || sStatusDtl.equals("U")) {
//
//					// 발굴(아웃리치) 연합거리상담 수정호출
//					iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcActvt(rowMap);
//					if (iUpdCnt > 0) {
//						// 발굴(아웃리치) 연합거리상담 이력등록호출
//						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
//					}
//				// 삭제처리
//				} else if (sStatusDtl.equals("d") || sStatusDtl.equals("D")) {
//
//					// 발굴(아웃리치) 연합거리상담 삭제호출
//					iDelCnt = dscsnOutrcMapper.deleteDscsnOutrcActvt(rowMap);
//					if (iDelCnt > 0) {
//						// 발굴(아웃리치) 연합거리상담 이력등록호출
//						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
//					}
//				}
//				
//				iCnt ++;
//			}
//		}

		
		/* -------------------------------- */
		/* 발굴(아웃리치) 쉼터자체활동 처리 */
		/* -------------------------------- */
		// 쉼터자체활동자료 DataSet
		ParameterGroup paramDsSheltr = dataRequest.getParameterGroup("dsSheltr");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDsSheltr=[" + paramDsSheltr + "]");
		
//		if (paramDsSheltr != null) {
//
//			List<Map<String, String>> prcDsSheltr = paramDsSheltr.getAllRowList();
//			iCnt = 0;
//			
//			for (Map<String, String> rowMap : prcDsSheltr) {
//				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.paramDsSheltr.iCnt=[" + iCnt + "," + rowMap + "]");
//				
//				String sStatusDtl  = String.valueOf(paramDsSheltr.getRowState(iCnt)).replace("RowState [state=", "").replace("]", "");
//				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.paramDsSheltr.sStatus1=[" + sStatusDtl + "], a=[" + String.valueOf(paramDsSheltr.getRowState(iCnt)) + "]");
//				
//				String sEryyGrDscsnNoDsSheltr = String.valueOf(rowMap.get("ERYY_GR_DSCSN_NO"));		// 초기집단상담번호(EG)
//				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDsUnite.sEryyGrDscsnNoDsUnite=[" + sEryyGrDscsnNoDsSheltr + "]");
//				if (sEryyGrDscsnNoDsSheltr == null || sEryyGrDscsnNoDsSheltr.equals("null") || sEryyGrDscsnNoDsSheltr.equals("")) {
//					sStatusDtl = "I";
//				} else {
//					sStatusDtl = "U";
//				}
//				
//				// 필수항목 및 처리항목 체크
//				rowMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
//				rowMap.put("DATAA_CHG_SE_CD", sStatusDtl);	// 데이터변경구분코드 셋팅
//
//				// 등록처리
//				if (sStatusDtl.equals("i") || sStatusDtl.equals("I")) {
//					
//					rowMap.put("ERYY_GR_DSCSN_NO",   sWprkSqn);
//					
//					// 발굴(아웃리치) 쉼터자체활동 등록호출
//					iIntCnt = dscsnOutrcMapper.insertDscsnOutrcActvt(rowMap);
//					if (iIntCnt > 0) {
//						// 발굴(아웃리치)쉼터자체활동 이력등록호출
//						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
//					}
//				// 수정처리
//				} else if (sStatusDtl.equals("u") || sStatusDtl.equals("U")) {
//
//					// 발굴(아웃리치) 쉼터자체활동 수정호출
//					iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcActvt(rowMap);
//					if (iUpdCnt > 0) {
//						// 발굴(아웃리치) 쉼터자체활동 이력등록호출
//						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
//					}
//				// 삭제처리
//				} else if (sStatusDtl.equals("d") || sStatusDtl.equals("D")) {
//
//					// 발굴(아웃리치) 쉼터자체활동 삭제호출
//					iDelCnt = dscsnOutrcMapper.deleteDscsnOutrcActvt(rowMap);
//					if (iDelCnt > 0) {
//						// 발굴(아웃리치) 쉼터자체활동 이력등록호출
//						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
//					}
//				}
//		
//				iCnt ++;
//			}
//		}
		
		/* 2022-12-02 YOO.CHI.HOON 여가부요청 아웃리치(연합거리상담, 쉼터자체활동 UI 변경으로 인한 저장변경*/
		ParameterGroup paramDmUneartActbtCl = dataRequest.getParameterGroup("dmUneartActbtCl");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDmUneartActbtCl=[" + paramDmUneartActbtCl + "]");
		
		if (paramDmUneartActbtCl != null) {

			// 상태코드(s:조회, i:신규, u:변경, d:삭제) 설정
			sStatus1 = paramDmUneartActbtCl.getValue("TYPE");
			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDmUneartActbtCl.sStatus1=[" + sStatus1 + "]");
			if (sStatus1 == null || sStatus1.equals("null") || sStatus1.equals("")) {
				sStatus1 = "s";
			}
			
			String sUneartActvtClSeCd = paramDmUneartActbtCl.getValue("UNEART_ACTVT_CL_SE_CD"); // 발굴활동분류구분코드
			String sEryyGrDscsnNo     = paramDmUneartActbtCl.getValue("ERYY_GR_DSCSN_NO"); // 발굴번호

			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.UNEART_ACTVT_CL_SE_CD=[" + sUneartActvtClSeCd + "]");
			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.ERYY_GR_DSCSN_NO=[" + sEryyGrDscsnNo + "]");
			
			Map<String, String> saveUneartActbtCl = paramDmUneartActbtCl.getSingleValueMap();
			
			// 연합거리상담, 쉼터자체활동
			List<Map<String, String>> saveUneartActbtClList   = dscsnOutrcMapper.selectUneartActvtSeCd(saveUneartActbtCl);
			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.saveUneartActbtClList=[" + saveUneartActbtClList + "]");
			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.saveUneartActbtClList.size()=[" + saveUneartActbtClList.size() + "]");
			iCnt = 0;
			
			for (Map<String, String> rowMap : saveUneartActbtClList) {
				
				rowMap.put("ERYY_GR_DSCSN_NO", sEryyGrDscsnNo);
				
				String sStatusDtl  = String.valueOf(rowMap.get("TYPE"));
				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.saveUneartActbtClList.sStatusDtl=[" + sStatusDtl + "]");
				
				// 필수항목 및 처리항목 체크
				rowMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
				rowMap.put("DATAA_CHG_SE_CD", sStatusDtl);	// 데이터변경구분코드 셋팅
				
				// 등록처리
				if (sStatusDtl.equals("i") || sStatusDtl.equals("I")) {
					
					rowMap.put("ERYY_GR_DSCSN_NO",   sWprkSqn);
					
					LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.등록.");
					LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.saveUneartActbtClList.ERYY_GR_DSCSN_NO=[" + sWprkSqn + "]");
					// 발굴(아웃리치) 쉼터자체활동 등록호출
					iIntCnt = dscsnOutrcMapper.insertDscsnOutrcActvt(rowMap);
					if (iIntCnt > 0) {
						// 발굴(아웃리치)쉼터자체활동 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
					}
				// 수정처리
				} else if (sStatusDtl.equals("u") || sStatusDtl.equals("U")) {
					
					List<Map<String, Object>> getList1 = new ArrayList<>();
					List<Map<String, Object>> getList2 = new ArrayList<>();
					
					
					// 연합거리상담사
					Map<String, String> mapDel = new HashMap<>();
					int chk1 = 0;
					int chk2 = 0;
					
					// selectDscsnOutrcSheltrList 발굴(아웃리치) 쉼터자체활동조회
					getList1 = dscsnOutrcMapper.selectDscsnOutrcSheltrList(saveUneartActbtCl);
					// selectDscsnOutrcUniteList 발굴(아웃리치) 연합거리상담조회
					getList2 = dscsnOutrcMapper.selectDscsnOutrcUniteList(saveUneartActbtCl);
					
					chk1 = getList1.size();
					chk2 = getList2.size();
					LOGGER.debug("체크1=[" + chk1 + "]");
					LOGGER.debug("체크2=[" + chk2 + "]");
					
					rowMap.put("SESS_USER_ID"        , sUserId);											
					rowMap.put("DATAA_CHG_SE_CD"     , sStatusDtl);				// 데이터변경구분코드 셋팅
					
					LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.sUneartActvtClSeCd.mapUp=[" + sUneartActvtClSeCd + "]");
					if("1".equals(rowMap.get("UNEART_ACTVT_CL_SE_CD"))) {
						LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.sUneartActvtClSeCd.mapUp=[ 1 ]");
						if(chk1 > 0) {
							mapDel.put("ERYY_GR_DSCSN_NO", sEryyGrDscsnNo);
							mapDel.put("UNEART_ACTVT_CL_SE_CD", "2");
							dscsnOutrcMapper.deleteUneartActvt(mapDel);
						}
						
						if(chk2 == 10) {
							// 발굴(아웃리치) 쉼터자체활동 수정호출
							iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcActvt(rowMap);
							if (iUpdCnt > 0) {
								// 발굴(아웃리치) 쉼터자체활동 이력등록호출
								iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
							}
						}else if(chk2 < 10){
							iIntCnt = dscsnOutrcMapper.insertDscsnOutrcActvt(rowMap);
							if (iIntCnt > 0) {
								// 발굴(아웃리치)쉼터자체활동 이력등록호출
								iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
							}
						}
					}else if("2".equals(rowMap.get("UNEART_ACTVT_CL_SE_CD"))){
						
						LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.sUneartActvtClSeCd.mapUp=[ 2 ]");
						if(chk2 > 0) {
							mapDel.put("ERYY_GR_DSCSN_NO", sEryyGrDscsnNo);
							mapDel.put("UNEART_ACTVT_CL_SE_CD", "1");
							dscsnOutrcMapper.deleteUneartActvt(mapDel);
						}
						
						if(chk1 == 2) {
							// 발굴(아웃리치) 쉼터자체활동 수정호출
							iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcActvt(rowMap);
							if (iUpdCnt > 0) {
								// 발굴(아웃리치) 쉼터자체활동 이력등록호출
								iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
							}
						}else if(chk1 < 2) {
							iIntCnt = dscsnOutrcMapper.insertDscsnOutrcActvt(rowMap);
							if (iIntCnt > 0) {
								// 발굴(아웃리치)쉼터자체활동 이력등록호출
								iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
							}
						}
					}
					
				// 삭제처리
				} else if (sStatusDtl.equals("d") || sStatusDtl.equals("D")) {

					LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.삭제.");
					// 발굴(아웃리치) 쉼터자체활동 삭제호출
					iDelCnt = dscsnOutrcMapper.deleteDscsnOutrcActvt(rowMap);
					if (iDelCnt > 0) {
						// 발굴(아웃리치) 쉼터자체활동 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcActvtHistory(rowMap);
					}
				}
				iCnt ++;
			}
		}		
		
		
		/* -------------------------------- */
		/* 발굴(아웃리치) 지원서비스실적 처리 */
		/* -------------------------------- */
		// 쉼터자체활동자료 DataSet
		ParameterGroup paramDmPrfmnc = dataRequest.getParameterGroup("dmPrfmnc");
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDmPrfmnc=[" + paramDmPrfmnc + "]");
		
		if (paramDmPrfmnc != null) {
			
			// 상태코드(s:조회, i:신규, u:변경, d:삭제) 설정
			sStatus1 = paramDmPrfmnc.getValue("TYPE");
			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.paramDmPrfmnc.sStatus1=[" + sStatus1 + "]");
			if (sStatus1 == null || sStatus1.equals("null") || sStatus1.equals("")) {
				sStatus1 = "s";
			}

			Map<String, String> savePrfmnc = paramDmPrfmnc.getSingleValueMap();
			
			// 필수항목 및 처리항목 체크
			savePrfmnc.put("SESS_USER_ID",     sUserId);		// 세션 사용자ID 셋팅
			savePrfmnc.put("DATAA_CHG_SE_CD",  sStatus1);		// 데이터변경구분코드 셋팅
			savePrfmnc.put("UNT_TASKWK_SE_CD", String.valueOf(saveMap.get("UNT_TASKWK_SE_CD")));		// 단위업무구분코드 셋팅

//			// 인원수 저장
//			String sNoprModChk = "N";	// 인원수항목변경여부
//			sNoprModChk = dscsnOutrcMapper.selectDscsnOutrcNopeModChk(savePrfmnc);
//			LOGGER.debug("DscsnOutrcServiceImpl.processTrprInqDetail.selectDscsnOutrcNopeModChk.sNoprModChk=[" + sNoprModChk + "]");
//
//			// 항목이 변경된건이 있을경우
//			if (sNoprModChk.equals("Y") || sNoprModChk.equals("y")) {
//			
//				// 발굴(아웃리치) 상세인원수수정 호출
//				iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcDetailNope(savePrfmnc);
//				if (iUpdCnt > 0) {
//					// 발굴(아웃리치) 이력등록 호출
//					iHisCnt = dscsnOutrcMapper.insertDscsnOutrcHistory(savePrfmnc);
//				}
//			}

			// 서비스지원횟수 저장데이터 조회
			List<Map<String, String>> savePrfmncList   = dscsnOutrcMapper.selectDscsnOutrcPrfmncList(savePrfmnc);
			LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.savePrfmncList=[" + savePrfmncList + "]");
			iCnt = 0;

			//if (savePrfmncList.size() > 0) {
			for (Map<String, String> rowMap : savePrfmncList) {
				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.rowMap.iCnt=[" + iCnt + "," + rowMap + "]");
				
				String sStatusDtl  = String.valueOf(rowMap.get("TYPE"));
				LOGGER.debug("DscsnOutrcServiceImpl.processDscsnUneartDetail.savePrfmncList.sStatusDtl=[" + sStatusDtl + "]");
				
				// 필수항목 및 처리항목 체크
				rowMap.put("SESS_USER_ID",    sUserId);		// 세션 사용자ID 셋팅
				rowMap.put("DATAA_CHG_SE_CD", sStatusDtl);	// 데이터변경구분코드 셋팅

				// 등록처리
				if (sStatusDtl.equals("i") || sStatusDtl.equals("I")) {
					
					rowMap.put("ERYY_GR_DSCSN_NO",   sWprkSqn);
					
					// 발굴(아웃리치) 서비스지원횟수 등록호출
					iIntCnt = dscsnOutrcMapper.insertDscsnOutrcPrfmnc(rowMap);
					if (iIntCnt > 0) {
						// 발굴(아웃리치)서비스지원횟수 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcPrfmncHistory(rowMap);
					}
				// 수정처리
				} else if (sStatusDtl.equals("u") || sStatusDtl.equals("U")) {

					// 발굴(아웃리치) 서비스지원횟수 수정호출
					iUpdCnt = dscsnOutrcMapper.updateDscsnOutrcPrfmnc(rowMap);
					if (iUpdCnt > 0) {
						// 발굴(아웃리치) 서비스지원횟수 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcPrfmncHistory(rowMap);
					}
				// 삭제처리
				} else if (sStatusDtl.equals("d") || sStatusDtl.equals("D")) {

					// 발굴(아웃리치) 서비스지원횟수 삭제호출
					iDelCnt = dscsnOutrcMapper.deleteDscsnOutrcPrfmnc(rowMap);
					if (iDelCnt > 0) {
						// 발굴(아웃리치) 서비스지원횟수 이력등록호출
						iHisCnt = dscsnOutrcMapper.insertDscsnOutrcPrfmncHistory(rowMap);
					}
				}
		
				iCnt ++;
			}
		}
		
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.iIntCnt=[" + iIntCnt + "], iUpdCnt=[" + iUpdCnt + "], iDelCnt=[" + iDelCnt + "]");
		
		LOGGER.debug("DscsnOutrcServiceImpl.processDscsnOutrcDetail.saveMap.ERYY_GR_DSCSN_NO=[" + saveMap.get("ERYY_GR_DSCSN_NO") + "]");
		// 초기집단상담번호(EG) key값 셋팅
		retMap.put("ERYY_GR_DSCSN_NO", saveMap.get("ERYY_GR_DSCSN_NO"));
		
		return retMap;
	}
	
}