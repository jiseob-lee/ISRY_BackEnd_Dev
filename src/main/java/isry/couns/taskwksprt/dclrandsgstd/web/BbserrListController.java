/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.dclrandsgstd.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.taskwksprt.dclrandsgstd.service.BbserrListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.sample.service.NoticeBoardService;

/**
 * @파일명        : BbserrListController.java
 * @프로그램 설명 :
 * - 
 * @작성자        : Park Chan Ho
 * @작성일        : 2022. 5. 23. 
 * @수정자        : Park Chan Ho
 * @수정일        : 2022. 5. 23.
 * @수정내용      :                 
 */

@Controller
@Api(value = "BbserrList Controller")
@RequestMapping("/constt")
public class BbserrListController {
	
	@Autowired
	private BbserrListService bbserrListService;
	
	@Autowired
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/onLoadBbserrList.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
	
		// 게시판 검색조건 코드 조회
		// public List<Map<String, Object>> selectCommonCode(String codeId) throws Exception;
		// CMMNS_CD_VALUE_NM : 이름
		// CMMNS_CD_VALUE : 값
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsCmbSearch", mgmtCmmnCodeService.selectCommonCodeUnit("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD", userVo.getUntTaskwk()));// 첫번째
		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("DTPRSN_ERR_DCLR_ERR_SE_CD", userVo.getUntTaskwk()));// 두번째
		return new JSONDataView();
		
	}
	
	// 전산오류 신고 등록에 있는 onLoadBody() 함수 호출 URL이다.
	///constt/		  onLoadInsertBbserrList.do
	@RequestMapping("/onLoadInsertBbserrList.do")
	public View onLoadInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		// 게시글 작성 코드 조회
		// CMMNS_CD_VALUE_NM : 이름
		// CMMNS_CD_VALUE : 값
		dataRequest.setResponse("dsCmbSearch", mgmtCmmnCodeService.selectCommonCodeUnit("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD", loginVO.getUntTaskwk()));// 첫번째
		dataRequest.setResponse("dsCmbSearchCd", mgmtCmmnCodeService.selectCommonCodeUnit("DTPRSN_ERR_DCLR_ERR_SE_CD", loginVO.getUntTaskwk()));// 두번째
		// 작성자 아이디, 작성자 이름, 아이피 주소 가지고 오기
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId(); // 작성자 아이디 가지고 오기
			//System.out.println("userId: "+userId);
		}
		
		UserDetailsVO userNameVO = userLoginService.getLoginSessionVO(request);
		String userName = "";
		if (userNameVO != null && userNameVO.getUserName() != null && !"".equals(userNameVO.getUserName())) {
			userName = userNameVO.getUserName(); // 작성자 아이디 가지고 오기
			/*loginVO: jslee
			userName: 이지섭 */
		} 
		
		UserDetailsVO userIpVO = userLoginService.getLoginSessionVO(request);
		String userIp = "";
		if (userIpVO != null && userIpVO.getIp() != null && !"".equals(userIpVO.getIp())) {
			userIp = userIpVO.getIp(); // 작성자 IP 가지고 오기
			/*loginVO: jslee
			userName: 이지섭 */
		} 
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("userId", userId);
		userInfo.put("userName", userName);
		userInfo.put("userIp", userIp);
		
		
		dataRequest.setResponse("dmResult", userInfo);
		
		return new JSONDataView();
		
	}
	
	// 전산오류 신고 조회
	@RequestMapping("/selectBbserrList.do")
	public View selectBbserrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request); 
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId(); // 사용자 아이디 가지고 오기
		}
		
		// 검색 조회조건
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		mapParam.put("strId", searchParam.getValue("strId")); // 검색어
		mapParam.put("PRGRSsTTSsEcD", searchParam.getValue("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD")); // 전산오류신고진행상태구분코드(상태)
		mapParam.put("DTPRSNeRRdCLReRRsEcD", searchParam.getValue("DTPRSN_ERR_DCLR_ERR_SE_CD")); // 전산오류신고오류구분코드(분류)
		mapParam.put("selectBox", searchParam.getValue("selectBox"));	// 검색어 조건
		mapParam.put("startorNot", 1);	// 검색조건인지 아니면 onBodyLoad() 인지 구별하기
		mapParam.put("userId",userId);	// 로그인한 사용자 아이디 가지고 오기
		
		if(searchParam.getValue("selectBox").equals("WRTR_NM_ENCPT")) {
			
		}
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo")); //1
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount")); // 15
		int startIndex = (pageIdx - 1) * rowSize;	// 
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		ParameterGroup searchTime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchTime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchTime.getValue("endDate"));							//조회끝날짜
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> listNoticeBoard = bbserrListService.selectBbserrList(mapParam);
		
		// 조회된 전체 데이터 갯수를 가져옵니다.
		totalCount = bbserrListService.getTotalCount(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listNoticeBoard);
		dataRequest.setResponse("dmPage", resPage);
	 
		return new JSONDataView();

	}

	// 전산오류 저장
	@RequestMapping("/insertBbserrList.do")
	public View insertBbserrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmResult");
		
		mapParam.put("dtprsnErrDclrPrgrsSttsSeCd", searchParam.getValue("DTPRSN_ERR_DCLR_PRGRS_STTS_SE_CD")); // 진행상태(값이 사라짐)
		mapParam.put("dtprsnErrDclrErrSeCd", searchParam.getValue("DTPRSN_ERR_DCLR_ERR_SE_CD")); // 분류
		mapParam.put("userId",searchParam.getValue("userId"));	// 유저 아이디
		mapParam.put("userIp",searchParam.getValue("userIp"));	// 유저 아이피
		mapParam.put("emlAddr", searchParam.getValue("EML_ADDR")); // E-mail 주소
		mapParam.put("hpgeUrlAddr", searchParam.getValue("HPGE_URL_ADDR")); // 홈페이지 URL 주소
		mapParam.put("bbscttTtlNm", searchParam.getValue("BBSCTT_TTL_NM")); // 게시글 제목
		mapParam.put("bbscttCn", searchParam.getValue("BBSCTTcn")); // 게시글 내용
		mapParam.put("atfino", searchParam.getValue("ATFINO")); // 파일전송
		mapParam.put("userName", searchParam.getValue("userName")); // 사용자 이름
	
//		System.out.println("분류: "+searchParam.getValue("DTPRSN_ERR_DCLR_ERR_SE_CD"));
//		System.out.println("userName: "+searchParam.getValue("userName"));
//		System.out.println("userIp: "+searchParam.getValue("userIp"));
//		System.out.println("emlAddr: "+searchParam.getValue("EML_ADDR"));
//		System.out.println("hpgeUrlAddr: "+searchParam.getValue("HPGE_URL_ADDR"));
//		System.out.println("bbscttTtlNm: "+searchParam.getValue("BBSCTT_TTL_NM"));
//		System.out.println("bbscttCn: "+searchParam.getValue("BBSCTTcn"));
//		System.out.println("atfino: "+searchParam.getValue("ATFINO"));
		
		
		bbserrListService.insertBbserrList(mapParam);
		//bbserrListService.insertuserName(mapParam);
		return new JSONDataView();

	}
	
	// 전산오류 신고 게시글 상세조회
	@RequestMapping("/selectBbserrDetail.do")
	public View selectBbserrDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		/*@d1#: dmSearch
		  @d1#tp: dm */
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		mapParam.put("bbscttEsntalNo", searchParam.getValue("BBSCTT_ESNTAL_NO")); // 게시글 고유 번호
		bbserrListService.plusRdcntNocs(mapParam); // 게시글 조회수 증가
		List<Map<String, Object>> listNoticeBoardDetail = bbserrListService.selectBbserrDetail(mapParam); // 게시글 상세보기
		
		dataRequest.setResponse("dsList", listNoticeBoardDetail);
		return new JSONDataView();
	}
	
	// 전산오류 신고 게시글 상세(수정 및 삭제)
	@RequestMapping("/saveBbserrDetail.do")
	public View saveBbserrDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		/*@d1#: dmSearch
		  @d1#tp: dm */
		/*
		 * Map<String, Object> mapParam = new HashMap<String, Object>(); 
		 * ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		 * 
		 * mapParam.put("bbscttEsntalNo", searchParam.getValue("BBSCTT_ESNTAL_NO")); // 게시글 고유 번호
		 */		
		
		bbserrListService.saveBbserrDetail(request, dataRequest); // 수정 및 삭제하기
		
		return new JSONDataView();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
