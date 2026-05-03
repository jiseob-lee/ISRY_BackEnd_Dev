package isry.itgcms.sysmgmt.userjoin.web;

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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userjoin.service.ApproveUserJoinService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userjoin")
public class ApproveUserJoinController extends IsryBaseController {

	@Resource(name = "approveUserJoinService")
	private ApproveUserJoinService approveUserJoinService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	@RequestMapping(value = "/selectUserJoin.do")
	public View selectUserJoin(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		if (userVo == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 승인 기관번호 파라메터 설정
		Map<String, Object> aprvInstNoInfo = userInstAuthService.getAprvInstNoInfo(request, dataRequest, null);
		
		if (aprvInstNoInfo.containsKey("SYS_MNGR_YN")) {
			String sysMngrYn = StringUtil.nullConvert(aprvInstNoInfo.get("SYS_MNGR_YN"));
			if ("N".equals(sysMngrYn)) {
				throw new AppWorksException("시스템관리자 권한이 없습니다!", Alert.INFO);
			}
		}
		
		dmSearchMap.putAll(aprvInstNoInfo);
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = approveUserJoinService.selectUserJoinCount(dmSearchMap);
		
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
		
		List<Map<String, Object>> listBoard = approveUserJoinService.selectUserJoin(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		
		//dataRequest.setResponse("dsList", approveUserJoinService.selectUserJoin());
		dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo == null ? "" : userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		dataRequest.setResponse("dsUserType", mgmtCmmnCodeService.selectCommonCodeUnit("USER_TYPE", userVo == null ? "" : userVo.getUntTaskwk()));  // 회원 종류 : 종사자, 기업, 청소년, 학무보
		dataRequest.setResponse("dsUserSearchType", mgmtCmmnCodeService.selectCommonCodeUnit("USER_SEARCH_TYPE", userVo == null ? "" : userVo.getUntTaskwk()));  // 회원 검색 구분 : 아이디, 성명
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo == null ? "" : userVo.getUntTaskwk()));  // 단위업무구분코드
		// 기관유형 추가
		dataRequest.setResponse("dsInstTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo == null ? "" : userVo.getInstTypeSeCd()));
		return new JSONDataView();
	}

	
	@RequestMapping(value = "/selectUserJoinList.do")
	public View selectUserJoinList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//dataRequest.setResponse("dsList", approveUserJoinService.selectUserJoin());
		
		return new JSONDataView();
	}

		
	@RequestMapping(value = "/giveBackUserJoin.do")
	public View giveBackUserJoin(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		approveUserJoinService.saveGiveBackUserJoin(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/saveUserJoin.do")
	public View saveUserJoin(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		approveUserJoinService.saveUserJoin(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 회원가입승인 확인 (PopUp) OnLoad
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/onLoadUserJoinApproveConfirm.do")
	public View onLoadUserJoinApproveConfirm(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 사용자 권한 정보 조회 및 데이터 셋 설정
		List<Map<String, Object>> userAuthInfo = approveUserJoinService.selectUserAuthInfo(request, dataRequest);
		dataRequest.setResponse("dsUserAuthInfo", userAuthInfo);
		
		return new JSONDataView();
	}

}
