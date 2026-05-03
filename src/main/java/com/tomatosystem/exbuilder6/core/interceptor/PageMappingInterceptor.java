package com.tomatosystem.exbuilder6.core.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ResponseModel;
import com.tomatosystem.exbuilder6.core.constants.PageConst;
import com.tomatosystem.exbuilder6.core.paging.CommonPagination;
import com.tomatosystem.exbuilder6.core.paging.PaginationInfo;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

public class PageMappingInterceptor extends HandlerInterceptorAdapter {

	private PaginationInfo paginationInfo = null;

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		
		boolean success = false;
		if (request.getAttribute("_PAGE_INFO_") == null)
			return;

		paginationInfo = (PaginationInfo) request.getAttribute("_PAGE_INFO_");

		DataRequest dataRequest = null;

		if (modelAndView.getModel().get(dataRequest.DATAREQUEST_ATTRNM) != null) {
			dataRequest = (DataRequest) modelAndView.getModel().get(dataRequest.DATAREQUEST_ATTRNM);
		}

		if (dataRequest != null) {
			ResponseModel resModel = dataRequest.getResponse();

			Map<String, Object> mapModel = resModel.getModel();
			Iterator iterData = mapModel.entrySet().iterator();
			while (iterData.hasNext()) {
				Entry entry = (Entry) iterData.next();
				if (entry.getKey() != null) {
					if (entry.getValue() instanceof List) {
						List listResult = (List) entry.getValue();
						if (listResult.size() > 0) {
							if (((Map) listResult.get(0)).get(PageConst.TOTAL_COUNT) != null) {
								CommonPagination paging = (CommonPagination) this.paginationInfo; // 전체
								paging.setTotalRecordCount(listResult);

								Map<String, Object> pageInfo = new HashMap<String, Object>();
								pageInfo.put(PageConst.KEY_RECORD_TOTAL_CNT, paging.getTotalRecordCount());
								pageInfo.put(PageConst.KEY_RECORD_CNT_PER_PAGE, paging.getRecordCountPerPage());
								pageInfo.put(PageConst.KEY_PAGE_NO, paging.getCurrentPageNo());
								pageInfo.put(PageConst.KEY_RECORD_REAL_TOTAL_CNT, paging.getRealTotalRecordCount());
								pageInfo.put("pagingYn", "Y");
									
								if (StringUtil.isNotNullEmpty(paging.getPageResultKey())) {
									pageInfo.put("pageResultKey", paging.getPageResultKey());
									dataRequest.setResponse(paging.getPageResultKey(), pageInfo); //
								} else {
									dataRequest.setResponse("dmPageInfo", pageInfo); //
								}
								
								success = true;
							}						
						} else {
							
							if(success) return;
							
							CommonPagination paging = (CommonPagination) this.paginationInfo; // 전체
							paging.setTotalRecordCount(listResult);

							Map<String, Object> pageInfo = new HashMap<String, Object>();
							pageInfo.put(PageConst.KEY_RECORD_TOTAL_CNT, paging.getTotalRecordCount());
							pageInfo.put(PageConst.KEY_RECORD_CNT_PER_PAGE, paging.getRecordCountPerPage());
							pageInfo.put(PageConst.KEY_RECORD_REAL_TOTAL_CNT, paging.getRealTotalRecordCount());
							pageInfo.put(PageConst.KEY_PAGE_NO, 1);
							pageInfo.put("pagingYn", "Y");

							dataRequest.setResponse("dmPageInfo", pageInfo); //

						}
					}
				}
			}
		}
	}
}
