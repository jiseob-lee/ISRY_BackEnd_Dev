package isry.itgcms.syscmmn.noticepopup.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.syscmmn.noticepopup.service.NoticePopupService;

@Controller
@RequestMapping("/isry/itgcm/syscmmn/noticepopup")
public class NoticePopupController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "noticePopupService")
	private NoticePopupService noticePopupService;

	@RequestMapping(value = "/selectPopupSeq.do")
	public View selectPopupSeq(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Integer> map = new HashMap<>();
		map.put("seq", noticePopupService.selectPopupSeq());
		dataRequest.setResponse("dmSeq", map);

		log.debug("test");
		
		return new JSONDataView();
	}
	

	@RequestMapping(value = "/savePopup.do")
	public View savePopup(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		noticePopupService.savePopup(request, dataRequest);

		return new JSONDataView();
	}
	

	@RequestMapping(value = "/deletePopup.do")
	public View deletePopup(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		noticePopupService.deletePopup(request, dataRequest);

		return new JSONDataView();
	}
	

	@RequestMapping(value = "/savePopupDetail.do")
	public View savePopupDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		noticePopupService.savePopupDetail(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPopupList.do")
	public View selectPopupList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = noticePopupService.selectPopupCount(dmSearchMap);
		
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
		
		List<Map<String, Object>> listBoard = noticePopupService.selectPopupList(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectNoticePopupList.do")
	public View selectNoticePopupList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = noticePopupService.selectNoticePopupList();
		
		dataRequest.setResponse("dsPopupList", listBoard);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectNoticePopupListOuter.do")
	@ResponseBody
	public List<Map<String, Object>> selectNoticePopupListOuter(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = noticePopupService.selectNoticePopupListOuter();
		
		return listBoard;
	}

	@RequestMapping(value = "/selectNoticePopupListInner.do")
	public View selectNoticePopupListInner(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> listBoard = noticePopupService.selectNoticePopupListInner();
		
		dataRequest.setResponse("dsPopupList", listBoard);
		
		return new JSONDataView();
	}

}
