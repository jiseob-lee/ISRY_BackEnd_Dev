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
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.uneart.mapper.RgnSoctyHnfTrnngMapper;
import isry.itgcm.casemng.uneart.service.RgnSoctyHnfTrnngService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;

/**
 * @파일명        : RgnSoctyMapperServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 5. 19. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 5. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("rgnSoctyHnfTrnngService")
public class RgnSoctyHnfTrnngServiceImpl implements RgnSoctyHnfTrnngService  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	
	
	@Resource(name = "rgnSoctyHnfTrnngMapper")
	private RgnSoctyHnfTrnngMapper rgnSoctyHnfTrnngMapper;
	
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명   : selectRgnSoctyHnfTrnngInqList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 지역사회 인력양성 목록
	 */
	@Override
	public Map<String, Object> selectRgnSoctyHnfTrnngInqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if("".equals(String.valueOf(paramGroup.getValue("REG_BGNG_YMD"))) && "".equals(String.valueOf(paramGroup.getValue("REG_BGNG_YMD")))) {
			throw new AppWorksException("등록일자를 입력바랍니다.", Alert.ERROR);
		}
		if("".equals(String.valueOf(paramGroup.getValue("REG_END_YMD"))) && "".equals(String.valueOf(paramGroup.getValue("REG_END_YMD")))) {
			throw new AppWorksException("등록일자를 입력바랍니다.", Alert.ERROR);
		}
		
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");		
		
		Map<String,Object> retMap = new HashMap<>();			
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		Map comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		
		int rgnCnt = rgnSoctyHnfTrnngMapper.rgnSoctyHnfTrnngInqListCount(paramMap2);
		paramMap2.put("TOT_CNT", rgnCnt);
		
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = rgnCnt;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));		
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);		
		
		retList = rgnSoctyHnfTrnngMapper.selectRgnSoctyHnfTrnngInqList(paramMap2);
		
		/* 페이징정보*/
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("totalCount"   , totCnt);
		pageMap.put("pageRowCount" , rowSize);
		pageMap.put("pageNo"       , pageIdx);		
		
		retMap.put("dsList", retList);
		retMap.put("dmPage", pageMap);		
		
		return retMap;		
	}

	/**
	 * @Method명   : selectRgnSoctyHnfTrnngDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 지역사회 인력양성 상세
	 */
	@Override
	public Map<String, Object> selectRgnSoctyHnfTrnngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		LOGGER.debug("=========== 지역사회 인력양성상세 : selectRgnSoctyHnfTrnngInqList() START ===========");		
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if("".equals(String.valueOf(paramGroup.getValue("RGN_SOCTY_HNF_TRNNG_MNG_NO"))) && "".equals(String.valueOf(paramGroup.getValue("RGN_SOCTY_HNF_TRNNG_MNG_NO")))) {
			throw new AppWorksException("조회할 지역사회 인력양성 관리번호가 없습니다.", Alert.ERROR);
		}
		
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> retMap = new HashMap<>();
		
		Map<String, Object> paramMap2 = new HashMap<>();
			paramGroup.getSingleValueMap().forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});	

		/* 지역사회인력양성 정보*/
		List<Map<String, Object>> retList1 = new ArrayList<>();
		retList1 = rgnSoctyHnfTrnngMapper.selectRgnSoctyHnfTrnngDetail(paramMap2);
		retMap.put("dsList", retList1);
		
		/* 지역사회인력양성 서비스사업 목록*/
		List<Map<String, Object>> retList2 = new ArrayList<>();
		retList2 = rgnSoctyHnfTrnngMapper.selectRgnSoctyHnfTrnngSrvcBiz(paramMap2);
		if(retList2.size() > 0) {
			retMap.put("dsBizReg", retList2);
		}
		
		/* 지역사회인력양성 실행서비스세부사업 목록*/
		List<Map<String, Object>> retList3 = new ArrayList<>();
		retList3 = rgnSoctyHnfTrnngMapper.selectRgnSoctyHnfTrnngExcnSrvcDetaiaBiz(paramMap2);
		if(retList3.size() > 0) {
			retMap.put("dsExcnSrvcBizClList", retList3);
		}
		
		LOGGER.debug("=========== 지역사회 인력양성상세 : selectRgnSoctyHnfTrnngInqList() END ===========");			
		
		return retMap;
	}


	/**
	 * @Method명   : processRgnSoctyHnfTrnng
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 지역사회 인력양성 등록, 수정 삭제
	 */
	@Override
	public Map<String, Object> processRgnSoctyHnfTrnng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		LOGGER.debug("=========== 지역사회 인력양성 등록, 수정 삭제  : processRgnSoctyHnfTrnng() START ===========");	
		
		ParameterGroup saveRgnSoctyHnfTrnngList = dataRequest.getParameterGroup("dsList");
		
		if(saveRgnSoctyHnfTrnngList == null) {
			throw new AppWorksException("저장할 지역사회 인력양성 정보가 없습니다..", Alert.ERROR);
		}

		Iterator<ParameterRow> insertedRows = saveRgnSoctyHnfTrnngList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveRgnSoctyHnfTrnngList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveRgnSoctyHnfTrnngList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		RowState rowSate = saveRgnSoctyHnfTrnngList.getRowState(0);
		
		LOGGER.debug("==========ROW_STATE==========");
		LOGGER.debug(rowSate.toString());
		
		Map<String, Object> retMap = new HashMap<>();

		while (insertedRows.hasNext()) {
			String sts = "I";

			Map<String, String> mapIns = insertedRows.next().toMap();
			
			String sRgnSoctyHnfTrnngMngNo = selectRenuNo(userId, "RG");
			
			mapIns.put("RGN_SOCTY_HNF_TRNNG_MNG_NO", sRgnSoctyHnfTrnngMngNo);			
			mapIns.put("USER_ID", userId);			
			mapIns.put("DATAA_CHG_SE_CD", sts);			
			
			rgnSoctyHnfTrnngMapper.insertSED200Data(mapIns);
			rgnSoctyHnfTrnngMapper.insertSED200History(mapIns);
			
			retMap.put("RGN_SOCTY_HNF_TRNNG_MNG_NO", mapIns.get("RGN_SOCTY_HNF_TRNNG_MNG_NO"));
		}
		while (updatedRows.hasNext()) {
			String sts = "U";

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("USER_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			rgnSoctyHnfTrnngMapper.updateSED200Data(mapUpd);
			rgnSoctyHnfTrnngMapper.insertSED200History(mapUpd);
			
			retMap.put("RGN_SOCTY_HNF_TRNNG_MNG_NO", mapUpd.get("RGN_SOCTY_HNF_TRNNG_MNG_NO"));
		}
		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("USER_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			retMap.put("RGN_SOCTY_HNF_TRNNG_MNG_NO", mapDel.get("RGN_SOCTY_HNF_TRNNG_MNG_NO"));
		}		
		LOGGER.debug("=========== 지역사회 인력양성 등록, 수정 삭제 : processRgnSoctyHnfTrnng() END ===========");			
		
		String sRowState = rowSate.toString().substring( (rowSate.toString().length() -2) , (rowSate.toString().length() -1));
		/* 지역사회 인력양성 수정없음*/
		if("e".equals(sRowState)) {
			request.setAttribute("RGN_SOCTY_HNF_TRNNG_MNG_NO", saveRgnSoctyHnfTrnngList.getValue(0, "RGN_SOCTY_HNF_TRNNG_MNG_NO"));	/* 지역사회인력양성관리번호*/
		}else {
			request.setAttribute("RGN_SOCTY_HNF_TRNNG_MNG_NO", retMap.get("RGN_SOCTY_HNF_TRNNG_MNG_NO"));	/* 지역사회인력양성관리번호*/
		}
		
		saveRgnSoctyHnfTrnngSrvcBiz(request, dataRequest);
		saveRgnSoctyHnfTrnngExcnSrvcDetaiaBiz(request, dataRequest);
		
		return retMap;
	}
	
	private void saveRgnSoctyHnfTrnngSrvcBiz(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		LOGGER.debug("=========== 지역사회 인력양성 서비스사업 등록, 수정 삭제 : saveRgnSoctyHnfTrnngSrvcBiz() START ===========");	
		
		ParameterGroup saveRgnSoctyHnfTrnngList = dataRequest.getParameterGroup("dsBizReg");

		Iterator<ParameterRow> insertedRows = saveRgnSoctyHnfTrnngList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveRgnSoctyHnfTrnngList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveRgnSoctyHnfTrnngList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		String sRgnSoctyHnfTrnngMngNo = String.valueOf(request.getAttribute("RGN_SOCTY_HNF_TRNNG_MNG_NO"));

		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			int iChkCnt = 0;
			
			mapIns.put("RGN_SOCTY_HNF_TRNNG_MNG_NO", sRgnSoctyHnfTrnngMngNo);			
			mapIns.put("USER_ID", userId);			
			
			iChkCnt = rgnSoctyHnfTrnngMapper.getSrvcExcnBizCnt(mapIns);
			if(iChkCnt >= 1) {
				mapIns.put("DEL_YN", "N");	/* 사용여부*/
				rgnSoctyHnfTrnngMapper.updateSrvcExcnBizYn(mapIns);
			}else {
				rgnSoctyHnfTrnngMapper.insertSED210Data(mapIns);
			}
		}
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("USER_ID", userId);

			rgnSoctyHnfTrnngMapper.updateSED210Data(mapUpd);
		}
		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("USER_ID", userId);
			
			rgnSoctyHnfTrnngMapper.deleteSED210Data(mapDel);
		}			
		
		LOGGER.debug("=========== 지역사회 인력양성 서비스사업 등록, 수정 삭제 : saveRgnSoctyHnfTrnngSrvcBiz() END ===========");			
	}
	private void saveRgnSoctyHnfTrnngExcnSrvcDetaiaBiz(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		LOGGER.debug("=========== 지역사회 인력양성 실행서비스세부사업 등록, 수정 삭제 : saveRgnSoctyHnfTrnngExcnSrvcDetaiaBiz() START ===========");
		
		ParameterGroup saveRgnSoctyHnfTrnngList = dataRequest.getParameterGroup("dsExcnSrvcBizClList");

		Iterator<ParameterRow> insertedRows = saveRgnSoctyHnfTrnngList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveRgnSoctyHnfTrnngList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveRgnSoctyHnfTrnngList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		String sRgnSoctyHnfTrnngMngNo = String.valueOf(request.getAttribute("RGN_SOCTY_HNF_TRNNG_MNG_NO"));		

		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			int iChkCnt = 0;			
			
			mapIns.put("RGN_SOCTY_HNF_TRNNG_MNG_NO", sRgnSoctyHnfTrnngMngNo);			
			mapIns.put("USER_ID", userId);			
			
			iChkCnt = rgnSoctyHnfTrnngMapper.getExcnSrvcDetaiaBizCnt(mapIns);
			if(iChkCnt >= 1) {
				mapIns.put("DEL_YN", "N");	/* 사용여부*/
				rgnSoctyHnfTrnngMapper.updateExcnSrvcDetaiaBizYn(mapIns);
			}else {
				rgnSoctyHnfTrnngMapper.insertSED220Data(mapIns);
			}			
		}
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("USER_ID", userId);

			rgnSoctyHnfTrnngMapper.updateSED220Data(mapUpd);
		}
		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("USER_ID", userId);
			
			rgnSoctyHnfTrnngMapper.deleteSED220Data(mapDel);
		}			
		LOGGER.debug("=========== 지역사회 인력양성 실행서비스세부사업 등록, 수정 삭제 : saveRgnSoctyHnfTrnngExcnSrvcDetaiaBiz() END ===========");			
	}
	
	
	/**
	 * @Method명 : selectRenuNo
	 * @param sessionUserId(세션정보), RenuNoSeCd(채번코드)
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2022. 9. 1.
	 * @Method설명 : 식별번호 채번
	 */
	private String selectRenuNo(String sessionUserId, String RenuNoSeCd) throws Exception {

		String sIdntfcNo = "";
		// 식별번호 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();

		seqMap.put("USER_ID", sessionUserId);
		seqMap.put("RENU_NO_SE_CD", RenuNoSeCd); // 채번코드
		seqMap.put("RENU_YMD", DateUtil.getToday()); // 현재일자

		// 채번서비스 호출
		valMap = renuNoMapper.selectCaseMngNoRenu(seqMap);

		sIdntfcNo = String.valueOf(valMap.get("RENU_NO")); // 식별번호 채번

		return sIdntfcNo;
	}	

}
