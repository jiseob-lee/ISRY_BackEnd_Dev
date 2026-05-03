/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stdnt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.drmgs.stdnt.mapper.YouthLifeRecodeDetailMapper;
import isry.drmgs.stdnt.service.YouthLifeRecodeDetailService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명        : YouthLifeRecodeDetailServiceImpl.java
 * @프로그램 설명 : 생활기록부 상세
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 14. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("youthLifeRecodeDetailService")
public class YouthLifeRecodeDetailServiceImpl extends IsryBaseServiceImpl implements YouthLifeRecodeDetailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="youthLifeRecodeDetailMapper")
	private YouthLifeRecodeDetailMapper youthLifeRecodeDetailMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	
	/**
	 * @Method명   : selectYngbsInfo
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 : 청소년정보 조회
	 */
	@Override
	public Map<String, Object> selectYngbsInfo(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		
		LOGGER.debug("paramGroup ==>> " + paramGroup);

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectYngbsInfo(paramMap);
	}
	
	
	/**
	 * @Method명   : selectPicPhoto
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 담당자 및 사진 조회
	 */
	public List<Map<String, String>> selectPicPhoto(DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectPicPhoto(searchParamMap);
	}
	
	/**
	 * @Method명   : selectYngbsMatter
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 청소년 인적사항조회
	 */
	@Override
	public Map<String, String> selectYngbsMatter(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectYngbsMatter(paramMap);
	}
	
	/**
	 * @Method명   : selectAtncSittn
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 출결 상황 조회
	 */
	public List<Map<String, String>> selectAtncSittn(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectAtncSittn(searchParamMap);
	}
	
	/**
	 * @Method명   : selectArprCareer
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 : 수상경력 조회
	 */
	public List<Map<String, String>> selectArprCareer(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectArprCareer(searchParamMap);
	}

	/**
	 * @Method명   : yngbsInfoSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 8. 
	 * @Method설명 : 청소년정보 저장
	 */
	@Override
	public Map<String, String> yngbsInfoSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmYngbsInfo");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();	
//		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		//searchParam = dataRequest.getParameterGroup("dmSearch");
		//Map<String, String> dmSearch = searchParam.getSingleValueMap();
		
		//dmOutcomeDetailMap.putAll(dmSearch);		
		
		youthLifeRecodeDetailMapper.UpdateYngbsInfo(dmOutcomeDetailMap);
		
		return null;
	}
	
	/**
	 * @Method명	 : selectCertiList
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 7. 14. 
	 * @Method설명 : 자격증 및 인증 취득상황 목록 조회
	 */
	@Override
	public List<Map<String,Object>> selectCertiList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("paramGroup ==>> " + paramGroup);

		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectCertiList(paramMap);
	}

	/**
	 * @Method명	 : selectExprnActvtList
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 7. 14. 
	 * @Method설명 : 창의적 체험 활동 상황 목록 조회
	 */
	@Override
	public List<Map<String,Object>> selectCreativeList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}		
		LOGGER.debug("paramGroup ==>> " + paramGroup);

		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
	return youthLifeRecodeDetailMapper.selectCreativeList(paramMap);
	}
	
	/**
	 * @Method명	 : selectSchulwList
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 7. 14. 
	 * @Method설명 : 학업 노력 상황 목록 조회
	 */
	@Override
	public List<Map<String,Object>> selectSchulwList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}		
		LOGGER.debug("paramGroup ==>> " + paramGroup);

		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectSchulwList(paramMap);
	}

 	/**
	 * @Method명   : selectRead
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 독서활동상황 조회
	 */
	public List<Map<String, Object>> selectRead(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectRead(searchParamMap);
	}

 	/**
	 * @Method명   : selectSvcb
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 봉사활동상황 조회
	 */
	public List<Map<String, Object>> selectSvcb(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectSvcb(searchParamMap);
	}

 	/**
	 * @Method명   : selectOpnn
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 행동특성 및 종합의견 조회
	 */
	public List<Map<String, Object>> selectOpnn(DataRequest dataRequest) throws Exception {

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectOpnn(searchParamMap);
	}	
	
	/**
	 * @Method명	 : saveCertiInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 7. 18. 
	 * @Method설명 : 자격증 및 인증 취득상황 정보 저장(등록/수정/삭제)
	 */
	@Override
	public int saveCertiInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmCertiInfo");
		Map<String, String> dmCertiInfoMap = infoParam.getSingleValueMap();
		dmCertiInfoMap.put("FRST_RGTR_ID", sUserId);		
		dmCertiInfoMap.put("LAST_MDFR_ID", sUserId);

		String type = dmCertiInfoMap.get("TYPE");
	
		if("CREATE".equals(type)) {
			youthLifeRecodeDetailMapper.insertCertiInfo(dmCertiInfoMap);
		} else if("MODIFY".equals(type)) {
			youthLifeRecodeDetailMapper.updateCertiInfo(dmCertiInfoMap);
		} else {
			youthLifeRecodeDetailMapper.deleteCertiInfo(dmCertiInfoMap);
		}

		return 0;
	}

	/**
	 * @Method명	 : saveCreativeInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 7. 19. 
	 * @Method설명 : 창의적 체험활동 상황 정보 저장(등록/수정/삭제)
	 */
	@Override
	public int saveCreativeInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmCreativeInfo");
		Map<String, String> dmCreativeInfoMap = infoParam.getSingleValueMap();
		dmCreativeInfoMap.put("FRST_RGTR_ID", sUserId);		
		dmCreativeInfoMap.put("LAST_MDFR_ID", sUserId);

		String type = dmCreativeInfoMap.get("TYPE");

		if("CREATE".equals(type)) {
			youthLifeRecodeDetailMapper.insertCreativeInfo(dmCreativeInfoMap);
		} else if("MODIFY".equals(type)) {
			youthLifeRecodeDetailMapper.updateCreativeInfo(dmCreativeInfoMap);
		} else {
			youthLifeRecodeDetailMapper.deleteCreativeInfo(dmCreativeInfoMap);
		}

		return 0;
	}
	
	/**
	 * @Method명	 : saveSchulwInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Seung.Yeon
	 * @작성일  	 : 2022. 7. 19. 
	 * @Method설명 : 학업 노력 상황 정보 저장(등록/수정/삭제)
	 */
	@Override
	public int saveSchulwInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmSchulwInfo");
		Map<String, String> dmSchulwInfoMap = infoParam.getSingleValueMap();
		dmSchulwInfoMap.put("FRST_RGTR_ID", sUserId);		
		dmSchulwInfoMap.put("LAST_MDFR_ID", sUserId);

		String type = dmSchulwInfoMap.get("TYPE");

		if("CREATE".equals(type)) {
			youthLifeRecodeDetailMapper.insertSchulwInfo(dmSchulwInfoMap);
		} else if("MODIFY".equals(type)) {
			youthLifeRecodeDetailMapper.updateSchulwInfo(dmSchulwInfoMap);
		} else {
			youthLifeRecodeDetailMapper.deleteSchulwInfo(dmSchulwInfoMap);
		}

		return 0;
	}
	
	/**
	 * @Method명	 : selectSchulwList
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 7. 18. 
	 * @Method설명 : 수상경력수정 조회
	 */
	@Override
	public Map<String,Object> selectArprCareerUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectArprCareerUpdate(paramMap);
	}
	
	/**
	 * @Method명   : arprCareerSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 수상경력 저장
	 */
	@Override
	public Map<String, String> arprCareerSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = "";	// 세션정보의 유저ID
//		int oIndexSn 		= 0;	// 색인일련번호
//		String sIndexSn 	= "";	// 색인일련번호
//		String indexSnRlt 	= "";	// 색인일련번호
		String sArprYmd		= "";   // 수상일자
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmArprCareer");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();			
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
//		searchParam = dataRequest.getParameterGroup("dmSearch");		
//		Map<String, String> dmSearch = searchParam.getSingleValueMap();		
//		dmOutcomeDetailMap.putAll(dmSearch);
		
		LOGGER.debug("수상경력 저장 input pram ==>> " + dmOutcomeDetailMap.toString());
		
		if("CREATE".equals(dmOutcomeDetailMap.get("TYPE"))) {	

//			// 색인일련번호 + 1 가져오기
//			sIndexSn = youthLifeRecodeDetailMapper.selectIndexSn(dmOutcomeDetailMap);
//			oIndexSn = Integer.valueOf(sIndexSn) + 1;
//			indexSnRlt = String.valueOf(oIndexSn);
//			LOGGER.debug("sIndexSn input pram ==>> " + indexSnRlt);
//			
//			dmOutcomeDetailMap.put("INDEX_SN", indexSnRlt);
			
			sArprYmd	= dmOutcomeDetailMap.get("ARPR_YMD");	
			dmOutcomeDetailMap.put("ARPR_YMD", sArprYmd.replace(".", ""));
			
			youthLifeRecodeDetailMapper.InsertArprCareer(dmOutcomeDetailMap); // 수상경력 insert
		}else {			
			
			sArprYmd	= dmOutcomeDetailMap.get("ARPR_YMD");	
			dmOutcomeDetailMap.put("ARPR_YMD", sArprYmd.replace(".", ""));
			
			youthLifeRecodeDetailMapper.UpdateArprCareer(dmOutcomeDetailMap); // 수상경력 update	
		}
				
		return null;
	}
	
	/**
	 * @Method명   : deleteArprCareer
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 수상경력 삭제
	 */
	@Override
	public Map<String, String> deleteArprCareer(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmArprCareer");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();	
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.putAll(dmSearch);		
		
		youthLifeRecodeDetailMapper.DeleteArprCareer(dmOutcomeDetailMap);
		
		return null;
	}
	
	/**
	 * @Method명	 : selectAtncSittnUpdate
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 7. 18. 
	 * @Method설명 : 출결 상황 수정 조회
	 */
	@Override
	public Map<String,Object> selectAtncSittnUpdate(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectAtncSittnUpdate(paramMap);
	}
	
	/**
	 * @Method명   : atncSittnSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 : 출결 상황 저장
	 */
	@Override
	public Map<String, String> atncSittnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      	= "";	// 세션정보의 유저ID
		String sAtendBgngYmd	= "";	// 출석시작일자
		String sAtendEndYmd 	= "";	// 출석종료일자
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmAtncSittn");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();			
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		LOGGER.debug("출결 상황 저장 input pram ==>> " + dmOutcomeDetailMap.toString());
		
		BigDecimal bIndexSn 	= new BigDecimal("0"); // 색인일련번호

		Map<String, Object> subMap = new HashMap<>();
		
		if("CREATE".equals(dmOutcomeDetailMap.get("TYPE"))) {	
			
			youthLifeRecodeDetailMapper.InsertAtncSittn(dmOutcomeDetailMap); // 출결 상황 insert
			
			searchParam = dataRequest.getParameterGroup("dsPrdList");
			List<Map<String, String>> dsPrdList = searchParam.getAllRowList();		
			for(int i=0; i<dsPrdList.size(); i++) {	
				
				dsPrdList.get(i).put("SRVC_PVSN_NO", dsPrdList.get(i).get("SRVC_PVSN_NO").toString()); 		// 서비스제공번호				
				dsPrdList.get(i).put("RESRCE_NO", dsPrdList.get(i).get("RESRCE_NO").toString()); 			// 자원번호								
				dsPrdList.get(i).put("CASE_MNG_NO", dsPrdList.get(i).get("CASE_MNG_NO").toString()); 		// 사례관리번호
				dsPrdList.get(i).put("CASE_MNG_ODRNO", dsPrdList.get(i).get("CASE_MNG_ODRNO").toString()); // 사례관리차수
				dsPrdList.get(i).put("FRST_RGTR_ID", sUserId);
				dsPrdList.get(i).put("LAST_MDFR_ID", sUserId);
				
				// 색인일련번호 구하기
				subMap = youthLifeRecodeDetailMapper.selectIndexSn370(dsPrdList.get(i));
				bIndexSn = (BigDecimal) subMap.get("INDEX_SN");
				dsPrdList.get(i).put("INDEX_SN", bIndexSn.toString()); 										// 색인일련번호				
				LOGGER.debug("색인일련번호 ==>> " + bIndexSn.toString());
				
				LOGGER.debug("dsPrdList ==>> " + dsPrdList.toString());
				
				youthLifeRecodeDetailMapper.InsertAtncSittnPvsnSrvc(dsPrdList.get(i)); // 출결 상황_제공서비스 insert
				
			}
			
		}else {
			
			sAtendBgngYmd	= dmOutcomeDetailMap.get("ATEND_BGNG_YMD");	// 출석시작일자
			sAtendEndYmd 	= dmOutcomeDetailMap.get("ATEND_END_YMD");	// 출석종료일자

			LOGGER.debug("sAtendBgngYmd update결과 ==>> " + sAtendBgngYmd );
			LOGGER.debug("sAtendEndYmd update결과 ==>> " + sAtendEndYmd);
			
			dmOutcomeDetailMap.put("ATEND_BGNG_YMD", sAtendBgngYmd.replace("-", ""));
			dmOutcomeDetailMap.put("ATEND_END_YMD", sAtendEndYmd.replace("-", ""));
			
			youthLifeRecodeDetailMapper.UpdateAtncSittn(dmOutcomeDetailMap); // 출결 상황 update	
		
			searchParam = dataRequest.getParameterGroup("dsPrdList");
			
			List<Map<String, String>> dsPrdList = searchParam.getAllRowList();
			
			LOGGER.debug("update size ==>> " + dsPrdList.size());
			
			youthLifeRecodeDetailMapper.DeleteAtncSittnPvsnSrvc(dmOutcomeDetailMap); // 출결 상황_제공서비스 delete
			
			for(int i=0; i<dsPrdList.size(); i++) {	
				
				dsPrdList.get(i).put("SRVC_PVSN_NO", dsPrdList.get(i).get("SRVC_PVSN_NO").toString()); 		// 서비스제공번호				
				dsPrdList.get(i).put("RESRCE_NO", dsPrdList.get(i).get("RESRCE_NO").toString()); 			// 자원번호								
				dsPrdList.get(i).put("CASE_MNG_NO", dsPrdList.get(i).get("CASE_MNG_NO").toString()); 		// 사례관리번호
				dsPrdList.get(i).put("CASE_MNG_ODRNO", dsPrdList.get(i).get("CASE_MNG_ODRNO").toString()); // 사례관리차수
				dsPrdList.get(i).put("FRST_RGTR_ID", sUserId);
				dsPrdList.get(i).put("LAST_MDFR_ID", sUserId);
				dsPrdList.get(i).put("INDEX_SN", dmOutcomeDetailMap.get("INDEX_SN")); 				// 색인일련번호
				
				LOGGER.debug("dsPrdList update==>> " + dsPrdList.toString());
				
				youthLifeRecodeDetailMapper.InsertAtncSittnPvsnSrvc(dsPrdList.get(i)); // 출결 상황_제공서비스 insert
//				youthLifeRecodeDetailMapper.UpdateAtncSittnPvsnSrvc(dsPrdList.get(i)); // 출결 상황_제공서비스 update
			}
		}
				
		return null;
	}
	
	/**
	 * @Method명	 : saveEtciInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Choi.Doo.Il
	 * @작성일  	 : 2022. 7. 19. 
	 * @Method설명 : 독서 활동 상황 정보 저장(등록/수정/삭제)
	 */
	@Override
	public int saveReadInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmSave");
		Map<String, String> dmSaveMap = infoParam.getSingleValueMap();
		dmSaveMap.put("FRST_RGTR_ID", sUserId);		
		dmSaveMap.put("LAST_MDFR_ID", sUserId);

		String type = dmSaveMap.get("TYPE");
		LOGGER.debug("type : " + type);

		if("CREATE".equals(type)) {
			youthLifeRecodeDetailMapper.insertRead(dmSaveMap);
		} else if("MODIFY".equals(type)) {
			youthLifeRecodeDetailMapper.updateRead(dmSaveMap);
		} else {
			youthLifeRecodeDetailMapper.deleteRead(dmSaveMap);
		}

		return 0;
	}

	/**
	 * @Method명   : deleteAtncSittn
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 : 출결 상황 삭제
	 */
	@Override
	public Map<String, String> deleteAtncSittn(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmAtncSittn");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();	
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		searchParam = dataRequest.getParameterGroup("dmSearch");
		
		LOGGER.debug("ATEND_YR input param ==>> " + dmOutcomeDetailMap.get("ATEND_YR") );
		
		Map<String, String> dmSearch = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.putAll(dmSearch);		
		
		youthLifeRecodeDetailMapper.DeleteAtncSittn(dmOutcomeDetailMap);
		
		return null;
	}
	
	/**
	 * @Method명   : enfsnSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 : 종사자 저장
	 */
	@Override
	public Map<String, String> enfsnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmEnfsn");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();	
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.putAll(dmSearch);		
		
		youthLifeRecodeDetailMapper.InsertEnfsn(dmOutcomeDetailMap);
		
		return null;
	}

	/**
	 * @Method명	 : saveEtciInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Choi.Doo.Il
	 * @작성일  	 : 2022. 7. 20. 
	 * @Method설명 : 봉사 활동 상황 정보 저장(등록/수정/삭제)
	 */
	@Override
	public int saveSvcbInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmSaveSvcb");
		Map<String, String> dmSaveMap = infoParam.getSingleValueMap();
		dmSaveMap.put("FRST_RGTR_ID", sUserId);		
		dmSaveMap.put("LAST_MDFR_ID", sUserId);

		String type = dmSaveMap.get("TYPE");
		LOGGER.debug("type : " + type);

		if("CREATE".equals(type)) {
			youthLifeRecodeDetailMapper.insertSvcb(dmSaveMap);
		} else if("MODIFY".equals(type)) {
			youthLifeRecodeDetailMapper.updateSvcb(dmSaveMap);
		} else {
			youthLifeRecodeDetailMapper.deleteSvcb(dmSaveMap);
		}

		return 0;
	}
 
 	/**
	 * @Method명	 : saveEtciInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Choi.Doo.Il
	 * @작성일  	 : 2022. 7. 20. 
	 * @Method설명 : 종합의견 및 행동특성 정보 저장(등록/수정/삭제)
	 */
	@Override
	public int saveOpnnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmSaveOpnn");
		Map<String, String> dmSaveMap = infoParam.getSingleValueMap();
		dmSaveMap.put("FRST_RGTR_ID", sUserId);		
		dmSaveMap.put("LAST_MDFR_ID", sUserId);

		String type = dmSaveMap.get("TYPE");
		LOGGER.debug("type : " + type);

		if("CREATE".equals(type)) {
			youthLifeRecodeDetailMapper.insertOpnn(dmSaveMap);
		} else if("MODIFY".equals(type)) {
			youthLifeRecodeDetailMapper.updateOpnn(dmSaveMap);
		} else {
			youthLifeRecodeDetailMapper.deleteOpnn(dmSaveMap);
		}

		return 0;
	}	
	
	/**
	 * @Method명   : subPicPhotoSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 20. 
	 * @Method설명 : 청소년정보 저장
	 */
	@Override
	public Map<String, String> subPicPhotoSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId       	= "";	// 세션정보의 유저ID
//		String sRealFileNmNow   = "";  // 청소년사진 파일명(현재)
//		String sRealFileNmBF   	= "";  // 청소년사진 파일명(과거)
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmPicPhoto");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();	
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		ParameterGroup searchYngbsInfo = dataRequest.getParameterGroup("dmYngbsInfo");
		Map<String, String> map = searchYngbsInfo.getSingleValueMap();
		
		searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmSearch = searchParam.getSingleValueMap();
		dmSearch.put("CNSTN_YN", map.get("CNSTN_YN"));
		
		dmOutcomeDetailMap.putAll(dmSearch);		

//		// 청소년사진 파일명(현재) 가져오기
//		sRealFileNmNow = youthLifeRecodeDetailMapper.selectRealFileNmNow(dmOutcomeDetailMap);
//		// 청소년사진 파일명(과거) 가져오기
//		sRealFileNmBF = youthLifeRecodeDetailMapper.selectRealFileNmBf(dmOutcomeDetailMap);
//				
//		LOGGER.debug("청소년사진 파일명(현재) 가져오기 ::::::::::: " + sRealFileNmNow);
//		LOGGER.debug("청소년사진 파일명(과거) 가져오기 ::::::::::: " + sRealFileNmBF);
//		
//		if(!sRealFileNmNow.isEmpty() && !sRealFileNmBF.isEmpty()) {
//			if(sRealFileNmNow.equals(sRealFileNmBF)) {
//				throw new AppWorksException("파일명이 같습니다. 파일명을 확인해주세요.", Alert.ERROR);
//			}
//		}
		youthLifeRecodeDetailMapper.UpdatePicPhoto(dmOutcomeDetailMap);
		
		return null;
	}
	
	/**
	 * @Method명   : subPicPhotoPicSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 20. 
	 * @Method설명 : 청소년정보 저장 List
	 */
	@Override	
	public List<Map<String, String>> subPicPhotoPicSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId       = "";	// 세션정보의 유저ID
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup search = dataRequest.getParameterGroup("dmSearch");
		
		if (search == null) {
			throw new AppWorksException("저장할 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup params = dataRequest.getParameterGroup("dmPicPhoto");
		Map<String, String> dmOutcomeDetailMap = params.getSingleValueMap();	
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		Map<String, String> dmSearch = search.getSingleValueMap();
		String indvDscsnReqstdNo = dmSearch.get("INDV_DSCSN_REQSTD_NO"); // 사례번호
		dmOutcomeDetailMap.putAll(dmSearch);
		
		params = dataRequest.getParameterGroup("dsPicPhoto");
		
		List<Map<String, String>> dsPicPhotoInsList = params.getInsertedRowList();
		List<Map<String, String>> dsPicPhotoUpdList = params.getUpdatedRowList();
		List<Map<String, String>> dsPicPhotoDelList = params.getDeletedRowList();
		
		for (Map<String, String> map : dsPicPhotoInsList) {
			map.put("FRST_RGTR_ID", sUserId);
			map.put("LAST_MDFR_ID", sUserId);
			map.put("INDV_DSCSN_REQSTD_NO", indvDscsnReqstdNo);
			youthLifeRecodeDetailMapper.InsertEnfsn(map);
		}
		
		for (Map<String, String> map : dsPicPhotoUpdList) {
			map.put("LAST_MDFR_ID", sUserId);
			map.put("INDV_DSCSN_REQSTD_NO", indvDscsnReqstdNo);
			youthLifeRecodeDetailMapper.UpdatePicPhotoPic(map);
		}
		
		for(Map<String, String> map : dsPicPhotoDelList) {
			map.put("LAST_MDFR_ID", sUserId);
			map.put("INDV_DSCSN_REQSTD_NO", indvDscsnReqstdNo);
			// 일련번호가 없으면 삭제 안함
			if(!"".equals(map.get("INDEX_SN")) && map.get("INDEX_SN") != null) {
				youthLifeRecodeDetailMapper.DeletePicPhotoPic(map);
			}
		}
		
		return null;
	}
	
	/**
	 * @Method명   : selectPicPhotoNmNow
	 * @param dataRequest
	 * @return 
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 : 사진파일명(현재) 조회
	 */
	@Override
	public Map<String, String> selectPicPhotoNmNow(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmPicPhoto");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectPicPhotoNmNow(paramMap);
	}
	
	/**
	 * @Method명   : selectPicPhotoNmBf
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 : 사진파일명(이전) 조회
	 */
	@Override
	public Map<String, String> selectPicPhotoNmBf(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmPicPhoto");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return youthLifeRecodeDetailMapper.selectPicPhotoNmBf(paramMap);
	}
	
	/**
	 * @Method명   : selectSprtSrvcList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 08. 
	 * @Method설명 : 지원서비스 검색 조회
	 */	
	@Override
	public List<Map<String, Object>> selectSprtSrvcList(DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String sCaseMngNo    = null;
		String sCaseMngOdrno = null;
		
		Map<String, String> subParamMap = new HashMap<>();	
		
		String sAtendDayCnt = ""; // 출석일수
		int    iAtendDayCnt = 0; // 출석일수
		String sUseYmdCnt   = ""; // 사용일자COUNT
//		String sSrvcPvsnNo  = "";  // 서비스제공번호	
//		String sResrceNo    = "";  // 자원번호 
		
		sCaseMngNo    = paramGroup.getValue("CASE_MNG_NO");		//사례관리번호
		sCaseMngOdrno = paramGroup.getValue("CASE_MNG_ODRNO");	//사례관리차수
		
		if (sCaseMngNo==null || sCaseMngNo.equals("null") || sCaseMngNo.equals("")) {
			throw new AppWorksException("사례관리번호는 필수입력 항목입니다. 입력해 주세요!", Alert.ERROR);
		}
		
		rtn = youthLifeRecodeDetailMapper.selectSprtSrvcList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			sUseYmdCnt = map.get("ATEND_DAYCNT").toString();
			
			if(sUseYmdCnt == null || "".equals(sUseYmdCnt) || "null".equals(sUseYmdCnt)) {
				
				// 서비스제공종료일자 - 서비스제공시작일자
				iAtendDayCnt = DateUtil.getDaysDiff(map.get("SRVC_PVSN_BGNG_YMD").toString(), map.get("SRVC_PVSN_END_YMD").toString());
				sAtendDayCnt = String.valueOf(iAtendDayCnt);
				
				LOGGER.debug("출석일수111 ::::::::::: " + sAtendDayCnt);
				
			}else {
				
				sAtendDayCnt = sUseYmdCnt;
				
				LOGGER.debug("출석일수222 ::::::::::: " + sAtendDayCnt);
			}
			
			map.put("ATEND_DAYCNT", sAtendDayCnt); // 출석일수
			
			rtn.set(i, map);
		}
		
		return rtn;
	}
	
	
	/**
	 * @Method명   : selectAtncSittnModify
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 09. 
	 * @Method설명 : 출결상황 수정조회
	 */
	public List<Map<String, String>> selectAtncSittnModify(DataRequest dataRequest) throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> searchParamMap = searchParam.getSingleValueMap();
		
		LOGGER.debug("searchParamMap ::::::::::: " + searchParamMap.toString());
		
		// 출결상황 수정조회
		List<Map<String, String>> rtnMap = youthLifeRecodeDetailMapper.selectAtncSittnModify(searchParamMap);
		
		LOGGER.debug("rtnMap 111::::::::::: " + rtnMap.toString());
		
		Map<String, String> map = new HashMap<>();
		for(int i=0; i < rtnMap.toArray().length; i++) {
			map = rtnMap.get(i);	

			rtnMap.set(i, map);
		}
		
		LOGGER.debug("rtnMap 222::::::::::: " + rtnMap.toString());
		
		return rtnMap;
	}


}

