/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.clipsoft.clipreport.common.util.DateUtils;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.workaltmntmng.mapper.AttdneMngMapper;
import isry.couns.mngr.workaltmntmng.service.AttdneMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService;

@Service
public class AttdneMngServiceImpl extends IsryBaseServiceImpl implements AttdneMngService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttdneMngServiceImpl.class);
	
	private final DateTimeFormatter ymFormatter = DateTimeFormatter.ofPattern("yyyyMM");

	@Resource(name = "attdneMngMapper")
	private AttdneMngMapper attdneMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	/**
	 * @Method명   : selectAttdneMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 2.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectAttdneMngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return attdneMngMapper.selectAttdneMngList(mapParam);
	}
	
	/**
	 * @Method명   : processAttdneMng
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 14. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> processAttdneMng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
//System.out.println("dsList = "+dsList.toString());
		Iterator<ParameterRow> insertedRows = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();
				
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		try {
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				
				mapIns.put("workYr", mapIns.get("WORK_YMD").substring(0, 4));
				mapIns.put("workMm", Integer.parseInt(mapIns.get("WORK_YMD").substring(4, 6))+"");
				mapIns.put("loginId", userId);
				if(mapIns.get("workYr") != null 
					&&	mapIns.get("workMm") != null
					&&	mapIns.get("CNSLTNT_ID") != null ) {
					attdneMngMapper.insertAttdneMng(mapIns);
				}
				// 게시글 번호 키값 셋팅
				//mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));

			}
		} catch (Exception e) {
			// TODO: handle exception
			Map<String, Object> msg = new HashMap<String, Object>();
			msg.put("msg", "-1");
			dataRequest.setMetadata(true, msg);
		}
		

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			//mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("workYr", mapUpd.get("WORK_YMD").substring(0, 4));
			mapUpd.put("workMm", Integer.parseInt(mapUpd.get("WORK_YMD").substring(4, 6))+"");
			mapUpd.put("loginId", userId);
			if(mapUpd.get("workYr") != null 
				&&	mapUpd.get("workMm") != null
				&&	mapUpd.get("CNSLTNT_ID") != null ) {
				attdneMngMapper.updateAttdneMng(mapUpd);
			}
			
			//mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));
		}
		
		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
//System.out.println("deletedRows = "+mapDel.toString());
			
			mapDel.put("workYr", mapDel.get("WORK_YMD").substring(0, 4));
			mapDel.put("workMm", Integer.parseInt(mapDel.get("WORK_YMD").substring(4, 6))+"");
			mapDel.put("loginId", userId);
			if(mapDel.get("workYr") != null 
				&&	mapDel.get("workMm") != null
				&&	mapDel.get("CNSLTNT_ID") != null ) {
				attdneMngMapper.deleteAttdneMng(mapDel);
			}
			
		}
		return mapReturn;
	}
	
	/**
	 * @Method명   : processAttdneMngMonth
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 14. 
	 * @수정자     : Myeong.Jae.Cheol
	 * @수정일     : 2023. 1. 03.
	 * @Method설명 :
	 */
	@Override
	public void processAttdneMngMonth(HttpServletRequest request, DataRequest dataRequest) throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
		
    	HttpSession session = request.getSession();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	LOGGER.debug("### processAttdneMngMonth :: {}", searchParam);
		
    	// 생성년월 구하기
		String crtrYm = searchParam.getValue("CRTR_YM");
		
		LocalDate today = LocalDate.now();
		
		if (StringUtil.isEmpty(crtrYm)) {
			// 현재년월을 구함
			crtrYm = today.format(ymFormatter);
		} else {
			crtrYm = crtrYm.replaceAll("-", "");
		}
		
		// 근무일자의 범위 구하기
		Map<String, String> defaultDayMap = this.getDefaultDayBetweenData(today, crtrYm);
		defaultDayMap.forEach(mapParam::put);
		
		// 사용자 세션 정보 조회 및 등록자 아이디 설정
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		mapParam.put("RGTR_ID", loginVO.getId());
		
		// 근태관리 월 데이터 일괄 등록 및 수정 처리
		attdneMngMapper.processAttdneMngMonth(mapParam);
	}
	
	/**
	 * @Method명   : getDefaultDayBetweenData
	 * @param crtrYm	조회일자(연월)
	 * @return
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 3. 
	 * @Method설명 : 근무배정관리 > 근태관리 > 일괄생성 시 날짜 범위 기본값 설정
	 */
	private Map<String, String> getDefaultDayBetweenData(LocalDate today, String crtrYm) {
		Map<String, String> resultMap = new LinkedHashMap<>();
		
		String resultYN = "N";
		
		// LocalDate 형으로 변환
		YearMonth month = YearMonth.parse(crtrYm, ymFormatter);
		LocalDate startDt = month.atDay(1);
		LocalDate endDt = month.atEndOfMonth();
		
		// 일정일자 범위 설정
		String beginYmd = startDt.format(DateTimeFormatter.BASIC_ISO_DATE);
		String endYmd = endDt.format(DateTimeFormatter.BASIC_ISO_DATE);
		
		LOGGER.debug("endDt.isAfter(today) ::: " + endDt.isAfter(today));
		
		if (endDt.isAfter(today)) {
			resultYN = "Y";
		} else {
			resultYN = "N";
		}
		
		LOGGER.debug("### beginYmd: {} / endYmd: {}", beginYmd, endYmd);
		
		resultMap.put("RESULT_YN", resultYN);
		resultMap.put("SCHDL_BGNG_YMD", beginYmd);
		resultMap.put("SCHDL_END_YMD", endYmd);
		
		return resultMap;
	}
	
}
