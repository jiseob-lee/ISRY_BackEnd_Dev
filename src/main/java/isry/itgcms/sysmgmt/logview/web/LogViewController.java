package isry.itgcms.sysmgmt.logview.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.logview.service.LogViewService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/logview")
public class LogViewController extends IsryBaseController {
	
	@Resource(name = "logViewService")
	private LogViewService logViewService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/selectSystemLog.do")
	public View selectSystemLog(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = logViewService.selectSystemLogTotalCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("OFFSET_IDX", startIndex - 1);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);
		
		List<Map<String, Object>> listBoard = logViewService.selectSystemLog(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		dataRequest.setResponse("dsProcess", mgmtCmmnCodeService.selectCommonCodeUnit("SYS_PRCS_SE_CD", userVo.getUntTaskwk()));  // 시스템 로그 프로세스 구분	
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectLogInOutLog.do")
	public View selectLogInOutLog(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//ScpDb scpDb = new ScpDb();
		//dmSearchMap.put("FRST_RGTR_NM", scpDb.scpEncB64((String)dmSearchMap.get("FRST_RGTR_NM")));
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = logViewService.selectLogInOutLogTotalCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> listBoard = logViewService.selectLogInOutLog(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		
		dataRequest.setResponse("dsLoginLogout", mgmtCmmnCodeService.selectCommonCodeUnit("LGN_LGT_SE_CD", userVo.getUntTaskwk()));  // 로그인, 로그아웃 구분	
		// 기관유형 및 단위업무구분 추가
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분
		dataRequest.setResponse("dsInstTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getInstTypeSeCd())); // 기관유형
		return new JSONDataView();
	}

	// 시스템관리 접속이력 조회
	@RequestMapping(value = "/selectLogInOutLog2.do")
	public View selectLogInOutLog2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//ScpDb scpDb = new ScpDb();
		//dmSearchMap.put("FRST_RGTR_NM", scpDb.scpEncB64((String)dmSearchMap.get("FRST_RGTR_NM")));
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = logViewService.selectLogInOutLogTotalCount2(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> listBoard = logViewService.selectLogInOutLog2(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		
		dataRequest.setResponse("dsLoginLogout", mgmtCmmnCodeService.selectCommonCodeUnit("LGN_LGT_SE_CD", userVo.getUntTaskwk()));  // 로그인, 로그아웃 구분	
		// 기관유형 및 단위업무구분 추가
		dataRequest.setResponse("dsInstTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getInstTypeSeCd())); // 기관유형
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업구분코드
		dataRequest.setResponse("dsAuthrtSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("AUTHRT_SE_CD", userVo.getUntTaskwk())); // 사용자권한
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectErrorLog.do")
	public View selectErrorLog(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = logViewService.selectErrorLogTotalCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> listBoard = logViewService.selectErrorLog(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		
		dataRequest.setResponse("dsProcess", mgmtCmmnCodeService.selectCommonCodeUnit("SYS_PRCS_SE_CD", userVo.getUntTaskwk()));  // 시스템 로그 프로세스 구분	
				
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectErrorLogDetail.do")
	public View selectErrorLogDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmData", logViewService.selectErrorDetail(dataRequest));
		
		return new JSONDataView();
	}
}
