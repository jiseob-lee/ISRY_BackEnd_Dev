/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.uneartmng.dgnssrcpt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.cysns.uneartmng.dgnssrcpt.mapper.DgnssRcptMapper;
import isry.cysns.uneartmng.dgnssrcpt.service.DgnssRcptService;
import isry.itgcm.linkmng.outsd.mapper.LinkTrprRqstMapper;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : DgnssRcptServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("dgnssRcptService")
public class DgnssRcptServiceImpl implements DgnssRcptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	
	
	@Resource(name = "dgnssRcptMapper")
	private DgnssRcptMapper dgnssRcptMapper; 

	@Resource(name = "linkTrprRqstMapper")
	private LinkTrprRqstMapper linkTrprRqstMapper;

	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : selectLinkRcptPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 6. 13. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectLinkRcptPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String,Object> param = new HashMap<String, Object>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmBase = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPageInfo");
		
		Map<String,String> param = dmBase.getSingleValueMap();

		//의뢰대상기관 - 로그인한 사용자 기관
		String INST_TYPE_SE_CD = loginVO.getInstTypeSeCd(); //기관유형
		String UNT_TASKWK_SE_CD = loginVO.getUntTaskwk(); //단위업무구분코드
		Integer USER_INST_NO = loginVO.getUserInstNo(); //사용자기관번호
		
		param.put("INST_TYPE_SE_CD", INST_TYPE_SE_CD);
		if(param.get("UNT_TASKWK_SE_CD") == null || "".equals(param.get("UNT_TASKWK_SE_CD"))) {
			param.put("UNT_TASKWK_SE_CD", UNT_TASKWK_SE_CD);
		}
		//param.put("UNT_TASKWK_SE_CD", "U02");
		param.put("USER_INST_NO", String.valueOf(USER_INST_NO));
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		param.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		String cnt = "";
		int totCnt  = 0;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;

		cnt = dgnssRcptMapper.selectLinkRcptCount(paramMap2);

		paramMap2.put("TOT_CNT", cnt);

		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		totCnt  = (cnt == null|| cnt.trim().isEmpty())?0:Integer.valueOf(cnt);
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> list  = dgnssRcptMapper.selectLinkRcptPagingList(paramMap2);
		
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		result.put("dsList", list);
		result.put("dmPageInfo", resPage);
		
		return result;
	}
	
	/**
	 * @Method명   : selectSchlDgnssById(학교진단)
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 11. 
	 * @Method설명 :
	 */
	
	@Override
	public List<Map<String, String>> selectSchlDgnssById(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String trprInfoNo = param.getValue("TRPR_INFO_NO");
		
		List<Map<String, String>> result = dgnssRcptMapper.selectSchlDgnssById(trprInfoNo);

		return result;
	}
	
	/**
	 * @Method명   : updateSchlDgnssData(학교진단)
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public void updateSchlDgnssData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)); //담당자 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmSrvcPvsnRcpt = dataRequest.getParameterGroup("dmSrvcPvsnRcpt");
		
		List<Map<String, String>> allRowList = dsList.getAllRowList();
		for (Map<String, String> map : allRowList) {
			map.put("USER_ID", userId);
			dgnssRcptMapper.updateSchlDgnssData(map);
			
			if (map.get("RCPT_SE_CD").equals("00")) continue;  //저장 skip
				
			if (map.get("RCPT_SE_CD").equals("21")) {    //접수승인 
				map.put("CASE_MNG_SE_CD", "02");         //사례대상자신청(대기상태) 
				map.put("CASE_TRPR_NOAP_CS_SE_CD", "");  //사례대상자미신청사유구분코드
				if (!dmSrvcPvsnRcpt.getValue("RRQST_RCPT_INST_NO").isEmpty()) { 
				    map.put("RCPT_INST_NO", dmSrvcPvsnRcpt.getValue("RRQST_RCPT_INST_NO"));  //타기관이송 기관번호
			    }
				map.put("RCPT_PIC_NO", enfsnNo);         //접수담당자번호
			} else if (map.get("RCPT_SE_CD").equals("22")) { //접수승인취소
				map.put("CASE_MNG_SE_CD", "01");          //사례대상자미신청
				map.put("CASE_TRPR_NOAP_CS_SE_CD", "99");           
				map.put("CASE_TRPR_UNSL_CS_CN", "미디어접수승인취소");       
			} else {
				map.put("CASE_MNG_SE_CD", "01");          //사례대상자미신청
				map.put("CASE_TRPR_NOAP_CS_SE_CD", "99");           
				map.put("CASE_TRPR_UNSL_CS_CN", "미디어접수반려");       
			}

			dgnssRcptMapper.updateTrprCaseMngInfo(map);
			dgnssRcptMapper.updateLinkTrprRcptSeCd(map);
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("SRVC_PVSN_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));
			paramMap.put("RCPT_SN", map.get("RCPT_SN"));
			
			Map<String, Object> rcptHist = linkTrprRqstMapper.selectLinkTrprRcptHist(paramMap);
			// SEB421 테이블 이력 등록
			rcptHist.put("DATAA_CHG_SE_CD", "U"); 
			rcptHist.put("FRST_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));
			rcptHist.put("RCPT_SE_CD", map.get("RCPT_SE_CD"));
			rcptHist.put("USER_ID", userId); 
			linkTrprRqstMapper.insertSrvcPvsnRqstRcptHistory(rcptHist);
		}
	}
	
	/**
	 * @Method명   : selectRelaInstById(유관기관)
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectRelaInstById(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		String trprInfoNo = param.getValue("TRPR_INFO_NO");

		List<Map<String, String>> result = dgnssRcptMapper.selectRelaInstById(trprInfoNo);

		return result; 

	}
	
	/**
	 * @Method명   : updateRelaInstData(유관기관)
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@Override
	public void updateRelaInstData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		String enfsnNo = CommUtils.getEnfsnNo(userLoginService.getLoginSessionVO(request)); //담당자 가져오기
		String instNo = CommUtils.getInstNo(userLoginService.getLoginSessionVO(request)); //기관코드 가져오기
		
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> allRowList = dsList.getAllRowList();
		for (Map<String, String> map : allRowList) {
			map.put("USER_ID", userId);
			dgnssRcptMapper.updateRelaInstData(map);
			
			if (map.get("RCPT_SE_CD").equals("00")) continue;  //저장 skip
			
			if (map.get("RCPT_SE_CD").equals("21")) {  //접수승인 
				map.put("CASE_MNG_SE_CD", "02");         //사례대상자신청(대기상태) 
				map.put("CASE_TRPR_NOAP_CS_SE_CD", "");  //사례대상자미신청사유구분코드
				map.put("RCPT_PIC_NO", enfsnNo);         //접수담당자번호
				map.put("RCPT_INST_NO", instNo);        //접수 기관
			} else if (map.get("RCPT_SE_CD").equals("22")) { //접수승인취소
				map.put("CASE_MNG_SE_CD", "01");          //사례대상자미신청
				map.put("CASE_TRPR_NOAP_CS_SE_CD", "99");           
				map.put("CASE_TRPR_UNSL_CS_CN", "미디어접수승인취소");       
				map.put("RCPT_PIC_NO", enfsnNo);         //접수담당자번호
			} else {
				map.put("CASE_MNG_SE_CD", "01");          //사례대상자미신청
				map.put("CASE_TRPR_NOAP_CS_SE_CD", "99");           
				map.put("CASE_TRPR_UNSL_CS_CN", "미디어접수반려");       
				map.put("RCPT_PIC_NO", enfsnNo);         //접수담당자번호
			}

			dgnssRcptMapper.updateTrprCaseMngInfo(map);
			dgnssRcptMapper.updateLinkTrprRcptSeCd(map);

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("SRVC_PVSN_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));
			paramMap.put("RCPT_SN", map.get("RCPT_SN"));
			
			Map<String, Object> rcptHist = linkTrprRqstMapper.selectLinkTrprRcptHist(paramMap);
			// SEB421 테이블 이력 등록
			rcptHist.put("DATAA_CHG_SE_CD", "U"); 
			rcptHist.put("FRST_RQST_NO", map.get("SRVC_PVSN_RQST_NO"));
			rcptHist.put("RCPT_SE_CD", map.get("RCPT_SE_CD"));
			rcptHist.put("USER_ID", userId); 
			linkTrprRqstMapper.insertSrvcPvsnRqstRcptHistory(rcptHist);

		}
	}

	/**
	 * @Method명   : selectDgnssScoreList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 10. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDgnssScoreList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));
		paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");
		
		List<Map<String, String>> result = dgnssRcptMapper.selectDgnssScoreList(paramMap);

		return result;
	}

	/**
	 * @Method명   : selectInfantChilList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 2. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectInfantChilList(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));
		paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");

		List<Map<String, String>> result = dgnssRcptMapper.selectInfantChilList(paramMap);
		return result;
	}

	/**
	 * @Method명   : selectCyberGambleList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 2. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCyberGambleList(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));
		paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");

		List<Map<String, String>> result = dgnssRcptMapper.selectCyberGambleList(paramMap);
		return result;
	}

	/**
	 * @Method명   : selectAddInspList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectAddInspList(DataRequest dataRequest) throws Exception {
		ScpDb scpDb = new ScpDb();
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("CASE_PRGRS_STTS_TYPE_SE_CD", "01");

		List<Map<String, String>> result = dgnssRcptMapper.selectAddInspList(paramMap);

		for (Map<String, String> map : result) {
			
			map.put("STDNT_NM", scpDb.scpDecB64(map.get("STDNT_NM_ENCPT")));
		}

		
		return result;
	}

	/**
	 * @Method명   : selectLinkTrprRcptHistory
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 2. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectLinkTrprRcptHistory(DataRequest dataRequest) throws Exception {
		ScpDb scpDb = new ScpDb();

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
	
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("SRVC_PVSN_RQST_NO", param.getValue("SRVC_PVSN_RQST_NO"));

		List<Map<String, Object>> rcptList = linkTrprRqstMapper.selectLinkTrprRcptHistory(paramMap);
		
		for (int i=0;i<rcptList.size();i++) {
			Map<String, Object> temp = rcptList.get(i);
			temp.put("RRQST_CLR_NM", scpDb.scpDecB64((String) temp.get("RRQST_CLR_NM")));
			temp.put("RCPT_TRPR_INFO_NM", scpDb.scpDecB64((String) temp.get("RCPT_TRPR_INFO_NM")));
			temp.put("CLR_NM", scpDb.scpDecB64((String) temp.get("CLR_NM")));
			if (i == 0) {
				temp.put("FRST_YN", "Y");
			} else {
				temp.put("FRST_YN", "N");
			}
			
			rcptList.set(i, temp);
		}
		return rcptList;
	}

	/**
	 * @Method명   : selectDgnssScoreTrmnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDgnssScoreTrmnList(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));
		
		List<Map<String, String>> result = dgnssRcptMapper.selectDgnssScoreTrmnList(paramMap);

		return result;
	}

	/**
	 * @Method명   : selectInfantChilTrmnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectInfantChilTrmnList(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));

		List<Map<String, String>> result = dgnssRcptMapper.selectInfantChilTrmnList(paramMap);
		return result;
	}

	/**
	 * @Method명   : selectCyberGambleTrmnList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectCyberGambleTrmnList(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));

		List<Map<String, String>> result = dgnssRcptMapper.selectCyberGambleTrmnList(paramMap);
		return result;
	}

	/**
	 * @Method명   : selectDgnssScoreAftfctList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 5. 6. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDgnssScoreAftfctList(DataRequest dataRequest) throws Exception {
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("TRPR_INFO_NO", param.getValue("TRPR_INFO_NO"));
		paramMap.put("DGNSS_EXMN_MNG_NO", param.getValue("DGNSS_EXMN_MNG_NO"));
		
		List<Map<String, String>> result = dgnssRcptMapper.selectDgnssScoreAftfctList(paramMap);

		return result;
	}

}
